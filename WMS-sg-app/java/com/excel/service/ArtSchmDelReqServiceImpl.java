package com.excel.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.ApprovalBean;
import com.excel.bean.ArtSchemeBeanForApprNewLogic;
import com.excel.bean.ArticleSchemeBeanForSave;
import com.excel.bean.ArticleSchemeDetailsBean;
import com.excel.bean.ArticleSchemeDetailsBean.ArticleSchemeTrdDetails;
import com.excel.bean.ArticleSchemeEditBean;
import com.excel.bean.MailBean;
import com.excel.bean.PolicyHeaderBean;
import com.excel.bean.PushAlcToCurrMonth;
import com.excel.bean.SapSalesInvoiceData;
import com.excel.exception.MedicoException;
import com.excel.model.ArticleSchemeRequestDetail;
import com.excel.model.ArticleSchemeRequestHeader;
import com.excel.model.ArticleSchemeRequestProduct;
import com.excel.model.Company;
import com.excel.model.CustLock;
import com.excel.model.CustomerMaster;
import com.excel.model.Location;
import com.excel.model.Sub_Company;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.repository.AllocationRepository;
import com.excel.repository.ArticleSchemeDeliveryRepo;
import com.excel.repository.CompanyMasterRepository;
import com.excel.repository.CustomerMasterRepo;
import com.excel.repository.TransactionalRepository;
import com.excel.restcontroller.ApprovalController;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;

@Primary
@Service
public class ArtSchmDelReqServiceImpl implements ArticleSchemeDeliveryReqService, MedicoConstants {

	@Autowired
	private CompanyMasterRepository compMastRepo;
	@Autowired
	private CustomerMasterRepo custMastRepo;
	@Autowired
	private ArticleSchemeDeliveryRepo artSchmDelRepo;
	@Autowired
	private TransactionalRepository transactionalRepo;
	@Autowired
	private LocationService locService;
	@Autowired
	private TaskMasterService taskMasterService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private CompanyMasterService compMasterService;
	@Autowired
	private ApprovalMailValidationService appr_mail_service;
	@Autowired
	private SendMail sendMail;
	@Autowired
	private ParameterService parameterService;

	private SimpleDateFormat sdf_yMd = new SimpleDateFormat("yyyy/MM/dd");

	private SimpleDateFormat formatTo = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private ApprovalController apprController;
	@Autowired
	private TaskMasterService taskMstServi;
	@Autowired
	private AllocationRepository allocRepo;

	@Override
	public Sub_Company getSubCompanyById(Long sub_comp_id) throws Exception {
		return compMastRepo.getSubCompanyObjectById(sub_comp_id);
	}

	@Override
	public List<CustomerMaster> getCustomersForLocation(Long loc_id, String filter) throws Exception {
		// return custMastRepo.getCustomersForLocation(loc_id);
		return custMastRepo.getCustomersForLocationNotInCustLock(loc_id, filter);
	}

	@Override
	public List<ArticleSchemeDetailsBean> getArticleSchemeDetailsForEntry(String sub_comp_code, String invoiceDate,
			Long loc_id) throws Exception {
		return artSchmDelRepo.getArticleSchemeDetailsForEntry(sub_comp_code, invoiceDate, loc_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ArticleSchemeRequestHeader saveArticleSchemeDelivery(ArticleSchemeBeanForSave bean) throws Exception {
		ArticleSchemeRequestHeader scmHeader = null;
		if (bean.getArticleRequestId() == null || bean.getArticleRequestId().compareTo(0L) == 0) {
			// save
			scmHeader = this.saveArticleSchemeHeader(bean);
		} else if (bean.getArticleRequestId().compareTo(0L) > 0) {
			// modify - get from table
			scmHeader = this.artSchmDelRepo.getHeaderById(bean.getArticleRequestId());
			if (scmHeader != null) {
				scmHeader.setInvoice_no(bean.getSapInvNo().trim());
				scmHeader.setInvoice_date(sdf_yMd.parse(bean.getSapInvDt()));
				scmHeader.setSap_po_no(bean.getSapPoNo());
				if (bean.getSapLrNo() != null && !bean.getSapLrNo().isEmpty()) {
					scmHeader.setLr_number(bean.getSapLrNo().trim());
					scmHeader.setLr_date(sdf_yMd.parse(bean.getSapLrDt().trim()));
				}
				scmHeader.setTransporter(bean.getTransId());
				scmHeader.setTrans_gst_reg_no(bean.getTransGstRegNo());
				scmHeader.setDistance(bean.getDistance());
				scmHeader.setVehicle_no(bean.getVehicle_no());
				// update the mod columns
				scmHeader.setArtsch_mod_user_id(bean.getUserId());
				scmHeader.setArtsch_mod_ip_add(bean.getIpAddr());
				scmHeader.setArtsch_mod_dt(new Date());
				// delete the existing details and products for this header
				this.artSchmDelRepo.deleteDetailsById(scmHeader.getArticle_req_id());
				this.artSchmDelRepo.deleteProductsById(scmHeader.getArticle_req_id());
			} else {
				throw new Exception("Data Not Found");
			}
		}
		List<ArticleSchemeDetailsBean> filter_out_zero_article_quantities = bean.getDataSource().stream()
				.filter(ds -> ds.getArticle_prod_qty_tot() > 0).collect(Collectors.toList());

		if (filter_out_zero_article_quantities != null && filter_out_zero_article_quantities.size() > 0) {
			this.saveArtSchemDetailsAndUpdateHeaderTotals(filter_out_zero_article_quantities, scmHeader);
		} else {
			throw new Exception("No Articles To Save!!!");
		}
		return scmHeader;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private ArticleSchemeRequestHeader saveArticleSchemeHeader(ArticleSchemeBeanForSave bean) throws Exception {

		Location location = this.locService.getObjectById(bean.getUser_loc_id());

		ArticleSchemeRequestHeader scmHeader = new ArticleSchemeRequestHeader();
		scmHeader.setArticle_req_date(new Date());
		scmHeader.setFin_year(bean.getFinYear().trim());
		scmHeader.setPeriod_code(bean.getSalesPeriod().trim());
		scmHeader.setCompany(bean.getCompanycd().trim());
		scmHeader.setSub_comp_cd(bean.getSubcompanycd().trim());
		scmHeader.setLoc_id(bean.getUser_loc_id());
		scmHeader.setFs_id(location.getRequestor_fstaff_id());
		scmHeader.setRequest_no("0000000000");
		scmHeader.setReq_remarks("");
		scmHeader.setAlloc_type("S");
		scmHeader.setRequest_type("Article Scheme Supply");
		scmHeader.setSupply_date(new Date());
		scmHeader.setFreight_type("");
		scmHeader.setFreight_value(BigDecimal.valueOf(0L));
		scmHeader.setCust_id(bean.getSelectedCustomer());
		scmHeader.setSap_po_no(bean.getSapPoNo());
		scmHeader.setInvoice_no(bean.getSapInvNo().trim());
		scmHeader.setInvoice_date(sdf_yMd.parse(bean.getSapInvDt()));
		if (bean.getSapLrNo() != null && !bean.getSapLrNo().isEmpty()) {
			scmHeader.setLr_number(bean.getSapLrNo().trim());
			scmHeader.setLr_date(sdf_yMd.parse(bean.getSapLrDt().trim()));
		}
		scmHeader.setTransporter(bean.getTransId());
		scmHeader.setTrans_gst_reg_no(bean.getTransGstRegNo());
		scmHeader.setDistance(bean.getDistance());
		scmHeader.setVehicle_no(bean.getVehicle_no());
		scmHeader.setArtsch_total_value(BigDecimal.ZERO);
		scmHeader.setArtsch_appr_status("E");
		scmHeader.setArtsch_ins_dt(new Date());
		scmHeader.setArtsch_ins_ip_add(bean.getIpAddr());
		scmHeader.setArtsch_ins_user_id(bean.getUserId());
		scmHeader.setArtsch_status("A");
		System.out.println("scmHeader::" + scmHeader.toString());
		this.transactionalRepo.persist(scmHeader);

		// update the scheme no
		StringBuilder sb = new StringBuilder();
		sb.append(bean.getSubcompanycd().trim()).append("/").append(bean.getFinYear().trim().substring(2)).append("/");
		String art_req_id = scmHeader.getArticle_req_id().toString().trim();
		for (int i = 0; i < (5 - art_req_id.length()); i++) {
			sb.append("0");
		}
		sb.append(art_req_id);
		scmHeader.setRequest_no(sb.toString());
		this.transactionalRepo.update(scmHeader);

		return scmHeader;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveArtSchemDetailsAndUpdateHeaderTotals(List<ArticleSchemeDetailsBean> dataSource,
			ArticleSchemeRequestHeader scmHeader) throws Exception {
		BigDecimal art_sch_hdr_total = BigDecimal.ZERO;
		for (ArticleSchemeDetailsBean itemRow : dataSource) {
			ArticleSchemeRequestDetail artScmDetail = new ArticleSchemeRequestDetail();
			artScmDetail.setArticle_req_id(scmHeader.getArticle_req_id());
			artScmDetail.setArticle_req_date(scmHeader.getArticle_req_date());
			artScmDetail.setFin_year(scmHeader.getFin_year());
			artScmDetail.setPeriod_code(scmHeader.getPeriod_code());
			artScmDetail.setCompany(scmHeader.getCompany());
			artScmDetail.setLoc_id(scmHeader.getLoc_id());
			artScmDetail.setFs_id(scmHeader.getFs_id());
			artScmDetail.setCust_id(scmHeader.getCust_id());
			artScmDetail.setTrd_sch_slno(itemRow.getTrd_scheme_id().longValue());
			artScmDetail.setScheme_applied_to(itemRow.getApply_to());

			// enter only in case of v else 0
			if (artScmDetail.getScheme_applied_to().equalsIgnoreCase("V")) {
				artScmDetail.setBilling_value(itemRow.getTot_billed_value_entered());
			} else if (artScmDetail.getScheme_applied_to().equalsIgnoreCase("Q")) {
				artScmDetail.setBilling_value(BigDecimal.ZERO);
			}

			artScmDetail.setSale_prod_id(0L);
			artScmDetail.setSale_prod_code_sg("");
			artScmDetail.setSale_prod_qty_billed(0L);
			artScmDetail.setSale_prod_qty_free(0L);
			artScmDetail.setSale_prod_qty_tot(0L);
			artScmDetail.setArticle_prod_id(itemRow.getArticle_prod_id().longValue());
			artScmDetail.setArticle_prod_cd(itemRow.getArticle_prod_cd());
			artScmDetail.setArticle_prod_qty(itemRow.getArticle_prod_qty_tot().longValue());
			artScmDetail.setArticle_prod_rate(itemRow.getArticle_prod_rate());
			artScmDetail.setArticle_prod_value(itemRow.getArticle_prod_value());
			artScmDetail.setArtsch_ins_user_id(scmHeader.getArtsch_ins_user_id());
			artScmDetail.setArtsch_ins_ip_add(scmHeader.getArtsch_ins_ip_add());
			artScmDetail.setArtsch_ins_dt(scmHeader.getArtsch_ins_dt());
			artScmDetail.setArtsch_dt_status("A");

			art_sch_hdr_total = art_sch_hdr_total.add(itemRow.getArticle_prod_value());

			this.transactionalRepo.persist(artScmDetail);
			// save art scheme product and update the details in this header

			List<ArticleSchemeTrdDetails> filter_greater_than_zero = null;
			if (artScmDetail.getScheme_applied_to().equalsIgnoreCase("V")) {
				filter_greater_than_zero = itemRow.getDetailList().stream()
						.filter(prd -> prd.getPer_billed_value_entered().compareTo(BigDecimal.ZERO) > 0)
						.collect(Collectors.toList());
			} else if (artScmDetail.getScheme_applied_to().equalsIgnoreCase("Q")) {
				filter_greater_than_zero = itemRow.getDetailList().stream()
						.filter(prd -> prd.getPer_article_prod_qty_updated() > 0).collect(Collectors.toList());
			}

			if (filter_greater_than_zero != null && filter_greater_than_zero.size() > 0) {
				this.saveArtSchemProducts(filter_greater_than_zero, artScmDetail);
			} else {
				throw new Exception("No Products To Save!!!");
			}
		}
		scmHeader.setArtsch_total_value(art_sch_hdr_total);
		this.transactionalRepo.update(scmHeader);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveArtSchemProducts(List<ArticleSchemeTrdDetails> detailList, ArticleSchemeRequestDetail artScmDetail)
			throws Exception {
		for (ArticleSchemeTrdDetails trdDetails : detailList) {
			ArticleSchemeRequestProduct art_sch_req_prod = new ArticleSchemeRequestProduct();
			art_sch_req_prod.setArticle_req_dtl_id(artScmDetail.getArticle_req_dtl_id());
			art_sch_req_prod.setArticle_req_id(artScmDetail.getArticle_req_id());
			art_sch_req_prod.setArticle_req_date(artScmDetail.getArticle_req_date());
			art_sch_req_prod.setFin_year(artScmDetail.getFin_year());
			art_sch_req_prod.setPeriod_code(artScmDetail.getPeriod_code());
			art_sch_req_prod.setCompany(artScmDetail.getCompany());
			art_sch_req_prod.setLoc_id(artScmDetail.getLoc_id());
			art_sch_req_prod.setFs_id(artScmDetail.getFs_id());
			art_sch_req_prod.setCust_id(artScmDetail.getCust_id());
			art_sch_req_prod.setTrd_sch_slno(artScmDetail.getTrd_sch_slno());
			art_sch_req_prod.setTrd_scheme_dtl_id(trdDetails.getTrd_scheme_dtl_id().longValue());

			art_sch_req_prod.setSale_prod_id_sg(trdDetails.getSale_prod_id().longValue());

			art_sch_req_prod.setSale_prod_code_sg(trdDetails.getSale_prod_code_erp());
			if (artScmDetail.getScheme_applied_to().equalsIgnoreCase("V")) {
				art_sch_req_prod.setSale_prod_qty_billed(0L);
				art_sch_req_prod.setSale_prod_qty_free(0L);
			} else if (artScmDetail.getScheme_applied_to().equalsIgnoreCase("Q")) {
				art_sch_req_prod.setSale_prod_qty_billed(trdDetails.getPer_sale_qty_billed_entered().longValue());
				art_sch_req_prod.setSale_prod_qty_free(trdDetails.getPer_sale_qty_free().longValue());
			}

			art_sch_req_prod.setSale_prod_qty_tot(
					art_sch_req_prod.getSale_prod_qty_billed() + art_sch_req_prod.getSale_prod_qty_free());
			art_sch_req_prod.setSale_prod_qty_tot_a(0L);
			art_sch_req_prod.setArticle_qty(trdDetails.getPer_article_prod_qty_updated().longValue());

			// enter only in case of v else 0
			if (artScmDetail.getScheme_applied_to().equalsIgnoreCase("V")) {
				art_sch_req_prod.setSale_billed_value(trdDetails.getPer_billed_value_entered());
			} else if (artScmDetail.getScheme_applied_to().equalsIgnoreCase("Q")) {
				art_sch_req_prod.setSale_billed_value(BigDecimal.ZERO);
			}

			art_sch_req_prod.setArtprd_ins_user_id(artScmDetail.getArtsch_ins_user_id());
			art_sch_req_prod.setArtprd_mod_user_id(artScmDetail.getArtsch_mod_user_id());
			art_sch_req_prod.setArtprd_ins_dt(artScmDetail.getArtsch_ins_dt());
			art_sch_req_prod.setArtprd_mod_dt(artScmDetail.getArtsch_mod_dt());
			art_sch_req_prod.setArtprd_ins_ip_add(artScmDetail.getArtsch_ins_ip_add());
			art_sch_req_prod.setArtprd_mod_ip_add(artScmDetail.getArtsch_mod_ip_add());
			art_sch_req_prod.setArtsch_prd_status("A");

			this.transactionalRepo.persist(art_sch_req_prod);
		}
	}

	@Override
	public List<ArticleSchemeRequestHeader> getUnConfirmedReqHdrs(Long loc_id, String userId) throws Exception {
		return this.artSchmDelRepo.getUnConfirmedReqHdrs(loc_id, userId);
	}

	@Override
	public List<ArticleSchemeDetailsBean> getAllSchemeDetailsForEntry(String sub_comp_code, Long loc_id, String finyear)
			throws Exception {
		return this.artSchmDelRepo.getAllSchemeDetailsForEntry(sub_comp_code, loc_id, finyear);
	}

	@Override
	public List<ArticleSchemeEditBean> getArticleSchemeDetailsForModifyByArtReqId(Long art_req_id, String sub_comp_code,
			Long loc_id) throws Exception {
		return this.artSchmDelRepo.getArticleSchemeDetailsForModifyByArtReqId(art_req_id, sub_comp_code, loc_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Long art_req_id) throws Exception {
		// do not delete just update the records to discarded and status to I
		this.artSchmDelRepo.deleteProductsById(art_req_id);
		this.artSchmDelRepo.deleteDetailsById(art_req_id);
		this.artSchmDelRepo.deleteHeaderById(art_req_id);
	}

	@Override
	public Boolean checkIfInvoiceExists(String invoice_no, Long loc_id) throws Exception {
		return this.artSchmDelRepo.checkIfInvoiceExists(invoice_no, loc_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalApproval(Long art_req_id, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception {
		ArticleSchemeRequestHeader scmHeader = null;
		System.out.println("In Final Approval Of Article Scheme Request");

		System.out.println("art_req_id : " + art_req_id);

		scmHeader = this.artSchmDelRepo.getHeaderById(art_req_id);

		Location location = locService.getLocationNameByEmployeeId(scmHeader.getArtsch_ins_user_id().trim());
		String tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(),
				ART_DEL_REQUEST, scmHeader.getCompany().trim());

		if (isapproved.equalsIgnoreCase("A")) {
			scmHeader.setArtsch_appr_status("A");
			scmHeader.setArtsch_status("A");
			scmHeader.setArtsch_mod_user_id(last_approved_by);
			scmHeader.setArtsch_mod_dt(new Date());
			this.transactionalRepo.update(scmHeader);
			artSchmDelRepo.callFinalApprovalProcedure(scmHeader.getArticle_req_id(), last_approved_by, "0");

//			try {
//				this.sendMailForArticleSchemeRequest(art_req_id, last_approved_by, "finalApproval", null,isapproved,null,
//						Long.valueOf(scmHeader.getFin_year()), tranType, comp_cd,"",scmHeader);
//			}
//			catch(Exception e) {
//				System.out.println("Error while sending mail :::::");e.printStackTrace();
//			}

		} else {
			scmHeader.setArtsch_appr_status("E");
			scmHeader.setArtsch_status("A");
			scmHeader.setArtsch_mod_user_id(last_approved_by);
			scmHeader.setArtsch_mod_dt(new Date());
			this.transactionalRepo.update(scmHeader);

//			//following is ued for lambda expression
//			Gson gson = new Gson();
//	        String json = gson.toJson(scmHeader);
//	        ArticleSchemeRequestHeader scmHeaderForLambda =  gson.fromJson(json, ArticleSchemeRequestHeader.class);
//			Runnable sendMailTask = () -> {
//		        try {
//		            this.sendMailForArticleSchemeRequest(art_req_id, last_approved_by, "finalApproval", null, isapproved, null,
//		                    Long.valueOf(scmHeaderForLambda.getFin_year()), tranType, comp_cd, "", scmHeaderForLambda);
//		        } catch (Exception e) {
//		            System.out.println("Error while sending mail");
//		            e.printStackTrace();
//		        }
//		    };
//
//		    // Submit the task to ExecutorService for asynchronous execution
//		    ExecutorService executorService = Executors.newCachedThreadPool();
//		    executorService.submit(sendMailTask);
//		    // Shutdown the executor service after tasks are done
//		    executorService.shutdown();
		}
	}

	@Override
	public Map<String, String> approveArticelSchemes(List<Long> artReqIdsList, String isapproved,
			String last_approved_by, String comp_cd, String ipAddr) {
		Map<String, String> map = new HashMap<String, String>();
		for (Long req_id : artReqIdsList) {
			try {
				ArticleSchemeRequestHeader scmHeader = this.artSchmDelRepo.getHeaderById(req_id);
				map.put(scmHeader.getRequest_no(), "Failed to process");
				this.finalApproval(req_id, last_approved_by, comp_cd, isapproved, ipAddr);
				if (isapproved.equalsIgnoreCase("A")) {
					map.put(scmHeader.getRequest_no(), "Approve Success!");

				} else {
					map.put(scmHeader.getRequest_no(), "Discard Success !");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@Override
	public List<ArticleSchemeRequestHeader> getArtReqNosForAutoDispatch(String finYear, String period_code, Long div_id,
			Long loc_id, String empId) throws Exception {
		return artSchmDelRepo.getArtReqNosForAutoDispatch(finYear, period_code, div_id, loc_id, empId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendArtRequestForApproval(String AUTO_APPR_IND, Long art_req_hdr_id, String user, String approver_email,
			String remark, HttpServletRequest request, HttpSession session) throws Exception {

		// String ip_addr = request.getRemoteAddr();
		System.out.println("user " + user);
		String tranType = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		ArticleSchemeRequestHeader scmHeader = null;

		scmHeader = this.artSchmDelRepo.getHeaderById(art_req_hdr_id);

		List<TaskMaster> masters = null;
		TaskMaster taskMaster = null;
		List<Task_Master_Dtl> task_Master_Dtls = null;

		if (scmHeader.getArtsch_status().equalsIgnoreCase("A")
				&& (scmHeader.getArtsch_appr_status().equals("E") || scmHeader.getArtsch_appr_status().equals("D"))) {
			Location location = locService.getLocationNameByEmployeeId(user);

			tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(), ART_DEL_REQUEST,
					scmHeader.getCompany().trim());
			if (tranType == null || tranType.isEmpty()) {
				throw new MedicoException("Could not find Approval Data !");
			}

			String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
			System.out.println("tranType " + tranType);
			masters = taskMasterService.getTask(null, null, null, tranType, null, null, TASK_FLOW, null, null);

			if (masters.size() == 0) {
				throw new MedicoException("Task master record not found");
			}
			taskMaster = masters.get(0);
			task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
			if (task_Master_Dtls.size() == 0) {
				throw new MedicoException("Task master detail record not found");
			}

			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("initiator", user);
			variables.put("initiator_desc", user);
			variables.put("initiator_email", "");
			variables.put("tran_ref_id", scmHeader.getArticle_req_id());
			variables.put("tran_type", tranType);
			variables.put("inv_group", null);
			variables.put("loc_id", scmHeader.getLoc_id());
			variables.put("cust_id", 0L);
			variables.put("tran_no", scmHeader.getRequest_no());
			variables.put("company_code", companyCode);
			variables.put("totaltask", masters.size());
			variables.put("amount", BigDecimal.ZERO);
			variables.put("escalatetime", null);
			variables.put("fin_year_id", scmHeader.getFin_year());
			variables.put("stock_point_id", 0);
			variables.put("doc_type", ART_DEL_REQUEST);
			variables.put("task_flow", TASK_FLOW);
			variables.put("remark", remark);
			variables.put("fs_id", 0L);
			variables.put("approval_type", null);

			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("articleDeliveryApproval",
					variables);
			// changing approval status
			scmHeader.setArtsch_appr_status("F");
			scmHeader.setArtsch_mod_user_id(user);
			scmHeader.setArtsch_mod_ip_add(request.getRemoteAddr());
			scmHeader.setArtsch_mod_dt(new Date());
			this.transactionalRepo.update(scmHeader);

			// send mail logic
//			try {
//				this.sendMailForArticleSchemeRequest(art_req_hdr_id, user, "selfApprove", null, "F", "nextApproval",
//						Long.valueOf(scmHeader.getFin_year()), tranType, companyCode, request.getRemoteAddr(),scmHeader);
//			}
//			catch(Exception e) {
//				System.out.println("Error while sending mail :::::");
//				e.printStackTrace();
//			}

			// based on indicator do final approval here
			if (AUTO_APPR_IND.equalsIgnoreCase("Y")) {
				ApprovalBean bean = new ApprovalBean();
				// get act_taskid by tran_id and tran_type
				bean.setTran_id(scmHeader.getArticle_req_id());
				bean.setApprover(task_Master_Dtls.get(0).getIdentitylinktype_val());
				bean.setApproved("A");
				bean.setTran_type(Long.valueOf(tranType));
				bean.setTaskid(this.taskMstServi.getActTaskIdByTranTypeAndId(scmHeader.getArticle_req_id(), tranType)
						.toString());
				bean.setInv_grp(null);
				this.apprController.simpleProcessTestData(request, null, bean);
			}

		}

	}

	@Override
	public Map<String, String> sendMultipeForApproval(List<String> artReqNosList, String user, String remark,
			HttpServletRequest request, HttpSession session) throws Exception {
		String AUTO_APPR_IND = this.parameterService.articleAutoApproval();
		Map<String, String> map = new ConcurrentHashMap<>(); // ConcurrentHashMap for thread safety
//		long startTime = System.currentTimeMillis();
//		ExecutorService executorService = Executors.newCachedThreadPool(); // Use cached thread pool for dynamic
//		// scaling
//		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (String req_no : artReqNosList) {
			// CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			try {
				ArticleSchemeRequestHeader scmHeader = this.artSchmDelRepo.getHeaderByReqNo(req_no.trim());
				// get approvers email by using loc id and company code and tran full name
				String approver_email = this.taskMasterService.getEmailIdOfApprover(scmHeader.getLoc_id(),
						scmHeader.getCompany().trim(), "ASR");
				System.out.println("approver_email::::" + approver_email);
				this.sendArtRequestForApproval(AUTO_APPR_IND, scmHeader.getArticle_req_id(), user, approver_email,
						remark, request, session);
				if (AUTO_APPR_IND.equalsIgnoreCase("Y")) {
					map.put(req_no.trim(), "Approved ");
				} else {
					map.put(req_no.trim(), "Sent for Approval ");
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (AUTO_APPR_IND.equalsIgnoreCase("Y")) {
					map.put(req_no.trim(), "Failed To Approve");
				} else {
					map.put(req_no.trim(), "Failed To Send For Approval");
				}
			}
			// }, executorService);
			// futures.add(future);
		}

		// Wait for all CompletableFuture to complete
//		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//		executorService.shutdown();
//		long endTime = System.currentTimeMillis();
//		long elapsedTime = endTime - startTime;
//		System.out.println("Elapsed time in milliseconds: " + elapsedTime);

		return map;

	}

	@Override
	public List<ArticleSchemeDetailsBean> getDataForApprovals(Long art_sch_req_hdr, Long loc_id, String sub_comp_cd,
			String status) throws Exception {
		return artSchmDelRepo.getAllEnteredDetails(loc_id, sub_comp_cd, art_sch_req_hdr, status);
		// return artSchmDelRepo.getDataForApprovals(art_sch_req_hdr, finYear,
		// companyCode, status);
	}

	@Override
	public void sendMailForArticleSchemeRequest(Long art_sch_req_hdr, String user, String apptovalType, String taskId,
			String status, String nextApprovar, Long year, String tranType, String companyCode, String ipaddr,
			ArticleSchemeRequestHeader hdr_for_notifi) throws Exception {
		Company company = this.compMasterService.getCompanyDetails();
		System.out.println("IN APPROVAL-ART-REQ");
		List<ArtSchemeBeanForApprNewLogic> details = null;
		Map<String, String> email_empId_map = new HashMap<String, String>();
		List<String> mailList = new ArrayList<>();
		List<MailBean> approvalDetails = null;
		Long process_id = 0L;
		String userId = "";

		if (nextApprovar != null)
			details = this.artSchmDelRepo.getArticleSchemeForApprovalData(art_sch_req_hdr, year.toString().trim(),
					companyCode, "F");
//		else 
//		details = this.artSchmDelRepo.getDataForApprovals(art_sch_req_hdr, year.toString().trim(), companyCode,status);

		MailBean mail = null;
		String link = null;
		String discardLink = null;
		List<MailBean> list = taskMasterService.getMailSendDetails(art_sch_req_hdr, nextApprovar, tranType);
		mail = list.get(0);
//		if (nextApprovar == null) {
//			taskId = mail.getAct_taskid();
//		}
		if (taskId != null) {
			approvalDetails = taskMasterService.getApprovalDetails(Long.valueOf(taskId));
			process_id = taskMasterService.getCustomTaskByTaskId(taskId);
		}

		System.out.println("Emp id :: " + mail.getEmp_id() + "Mail :: " + mail.getEmp_email());

		if (nextApprovar == null) {
			mailList.add(mail.getEmp_email());
		} else {
			email_empId_map.put(mail.getEmp_id(), mail.getEmp_email());
		}

		if (nextApprovar == null) {
			String subject = "Article Scheme Request Approval ";
			sendMail.sendApprovalMailForArticleSchemRequest(details, mailList, status, user, subject, link, mail,
					nextApprovar, approvalDetails, discardLink, companyCode, hdr_for_notifi);
		} else {
			StringBuilder sb = new StringBuilder();
			if (companyCode.trim().equals("PFZ")) {
				Iterator<Map.Entry<String, String>> itr = email_empId_map.entrySet().iterator();

				while (itr.hasNext()) {
					Map.Entry<String, String> entry = itr.next();
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
					System.out.println(entry.getKey());
					String token = Base64.getEncoder().encodeToString(entry.getKey().getBytes(StandardCharsets.UTF_8));
					System.out.println("toekn->" + token);
					System.out.println("decoded->" + new String(Base64.getDecoder().decode(token)));
					link = "rest/saveArticleSchemeApprovalViaMail?task_id=" + mail.getAct_taskid()
							+ "&decision=approve&userName=" + mail.getAssignee_() + "&remark=" + mail.getAct_taskid()
							+ "&tran_id=" + art_sch_req_hdr + "&token=" + token;

					discardLink = "rest/saveArticleSchemeApprovalViaMail?task_id=" + mail.getAct_taskid()
							+ "&decision=discard&userName=" + mail.getAssignee_() + "&remark=" + mail.getAct_taskid()
							+ "&tran_id=" + art_sch_req_hdr + "&token=" + token;
					System.out.println("Task Id :::::" + mail.getAct_taskid());

					sb.append("Article Scheme Req:- ");
					sb.append(hdr_for_notifi.getRequest_no());
					sb.append(" Inv No:");
					sb.append(hdr_for_notifi.getInvoice_no());
					sb.append(" Inv Dt:");
					sb.append(hdr_for_notifi.getInvoice_date());

					sendMail.sendApprovalMailForArticleSchemRequest(details, Arrays.asList(entry.getValue()), status,
							user, sb.toString(), link, mail, nextApprovar, approvalDetails, discardLink, companyCode,
							hdr_for_notifi);
					if (mail.getAct_taskid() != null)
						this.appr_mail_service.saveAfterMailSend(Long.valueOf(mail.getAct_taskid()), art_sch_req_hdr,
								tranType, process_id, entry.getValue(), entry.getKey(), user, ipaddr);
				}
			}
		}
	}

	@Override
	public boolean lockCustForArticleReqEntry(Long loc_id, Long cust_id, String userId) throws Exception {
		// check if customer is already locked by another user and give a message
		// accordingly
		CustLock custLock = this.artSchmDelRepo.getLockedCustomer(cust_id, loc_id);
		if (custLock == null || custLock.getUser_id().equalsIgnoreCase(userId)) {
			// this customer is locked by ourselves before
			// we can unlock and then lock it
			this.artSchmDelRepo.unlockAllCustByUserId(userId);
			this.artSchmDelRepo.lockCustForArticleReqEntry(loc_id, cust_id, userId);
			return true;
		} else {
			// this customer is locked by someone else
			// we need to show user the message that this is locked
			return false;
		}

	}

	@Override
	public void releaseLockedCustomer(Long loc_id, Long cust_id, String userId) throws Exception {
		this.artSchmDelRepo.releaseLockedCustomer(loc_id, cust_id, userId);
	}

	@Override
	public void unlockAllCustByUserId(String userId) throws Exception {
		this.artSchmDelRepo.unlockAllCustByUserId(userId);
	}

	@Override
	public ArticleSchemeRequestHeader getHeaderById(Long article_req_id) throws Exception {
		return this.artSchmDelRepo.getHeaderById(article_req_id);
	}

	@Override
	public List<ArticleSchemeDetailsBean> getAllEnteredDetailsForModify(Long loc_id, String sub_comp_code,
			Long article_req_id) throws Exception {
		return this.artSchmDelRepo.getAllEnteredDetails(loc_id, sub_comp_code, article_req_id, "E");
	}

	@Override
	public CustomerMaster getCustomerById(Long cust_id) throws Exception {
		return this.custMastRepo.getCustomerById(cust_id);
	}

	@Override
	public List<ArtSchemeBeanForApprNewLogic> getArticleSchemeForApprovalData(Long art_sch_req_hdr, String finYear,
			String companyCode, String status) throws Exception {
		return this.artSchmDelRepo.getArticleSchemeForApprovalData(art_sch_req_hdr, finYear, companyCode, status);
	}

	@Override
	public void dispatch_to_194R_push() throws Exception {
		this.artSchmDelRepo.dispatch_to_194R_push();
	}

	@Override
	public Boolean checkIfInvoiceExistsInSalesData(Long loc_id, String entered_invoice_no) throws Exception {
		return this.artSchmDelRepo.checkIfInvoiceExistsInSalesData(loc_id, entered_invoice_no);
	}

	@Override
	public List<SapSalesInvoiceData> getSalesProductDataFromInvoice(String sap_invoice_no) throws Exception {
		return this.artSchmDelRepo.getSalesProductDataFromInvoice(sap_invoice_no);
	}

	@Override
	public BigDecimal getDistanceForChallan(Long dsp_id) throws Exception {
		return this.artSchmDelRepo.getDistanceForChallan(dsp_id);
	}

	@Override
	public void updateArtReqDistance(Long dsp_id, BigDecimal distance) throws Exception {
		this.artSchmDelRepo.updateArtReqDistance(dsp_id, distance);
	}

	@Override
	public void unlockAllCustomers() throws Exception {
		this.artSchmDelRepo.unlockAllCustomers();
	}

	@Override
	public List<CustomerMaster> getCustomersByShipToCodeAndNameNotInCustLock(boolean isAllLoc, Long loc_id,
			String filter, String company_cd) throws Exception {
		return this.custMastRepo.getCustomersByShipToCodeAndNameNotInCustLock(isAllLoc, loc_id, filter, company_cd);
	}

	@Override
	public List<ArticleSchemeDetailsBean> get_duplecate_invoice(String articleRequestId, String sapInvNo) {
		return this.artSchmDelRepo.get_duplecate_invoice(articleRequestId, sapInvNo);
	}

	@Override
	public List<ArticleSchemeDetailsBean> get_Scheme_detail(String invoice_no) {
		return this.artSchmDelRepo.get_Scheme_detail(invoice_no);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void pushAlcDatFromPrevToCurr(PushAlcToCurrMonth bean) throws Exception {
		// get curr sampl_polic_header object
		PolicyHeaderBean policyHeaderBean = this.allocRepo.getCurrSamplePolHdData();
		for (Long sel_art_req_id : bean.getPending_req_nos()) {
			this.updateAlcForMonthChange(policyHeaderBean.getFin_year(), policyHeaderBean.getPeriod_code(),
					policyHeaderBean.getSmp_id().longValue(), sel_art_req_id);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAlcForMonthChange(String finyear, String period_cd, Long alloc_smp_id, Long article_req_id)
			throws Exception {
		this.artSchmDelRepo.updateAlcHdMonthChange(finyear, period_cd, alloc_smp_id, article_req_id);
		this.artSchmDelRepo.updateAlcDetailsMonthChange(finyear, period_cd, alloc_smp_id, article_req_id);
	}

}
