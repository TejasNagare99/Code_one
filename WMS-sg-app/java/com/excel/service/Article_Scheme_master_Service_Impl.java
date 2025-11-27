package com.excel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.Articel_Scheme_Approved_Data_Bean;
import com.excel.bean.Articele_Scheme_Approved_Data;
import com.excel.bean.ArticleDocsBean;
import com.excel.bean.ArticleSchemMasterCompanyData;
import com.excel.bean.ArticleSchemeMasterSALEPRODData;
import com.excel.bean.ArticleSchemeReportBean;
import com.excel.bean.Article_Scheme_Extent_Bean;
import com.excel.bean.Article_bean_for_modify;
import com.excel.bean.Article_save_as_bean;
import com.excel.bean.Article_save_as_bean.SalesProdDetails;
import com.excel.bean.MailBean;
import com.excel.bean.SchemeDetailDataBean;
import com.excel.bean.SchemeMasterSampleProductBean;
import com.excel.bean.SpecificLocationBean;
import com.excel.bean.Validity_ext_bean;
import com.excel.exception.MedicoException;
import com.excel.model.ARTICLE_SCH_VALID_EXT_DOCS;
import com.excel.model.Art_Sch_Vld_Ext;
import com.excel.model.Artreq_To_Dispatch_Report_With_Param;
import com.excel.model.Company;
import com.excel.model.DivMaster;
import com.excel.model.EmailFrom;
import com.excel.model.FinYear;
import com.excel.model.Location;
import com.excel.model.New_Stockist_wiseScheme_request_model;
import com.excel.model.New_stockist_wise_scheme_request_supply_Detail_model;
import com.excel.model.SCM_MST_PROD_LOCK;
import com.excel.model.SamplProdGroup;
import com.excel.model.Scheme_Desription_Bean;
import com.excel.model.Scheme_Summary;
import com.excel.model.TRD_SCH_MST_DOCS;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.model.trd_scheme_mst_dtl;
import com.excel.model.trd_scheme_mst_hdr;
import com.excel.repository.Article_Scheme_master_Repository;
import com.excel.repository.EmailRepository;
import com.excel.repository.ReportRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;
import com.excel.utility.SendMail;

@Primary
@Service
public class Article_Scheme_master_Service_Impl implements Article_Scheme_master_Service, MedicoConstants {

	@Autowired
	private SendMail sendMail;
	@Autowired
	private CompanyMasterService companyMasterService;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private ApprovalMailValidationService appr_mail_service;

	@Autowired
	private EmailRepository emailrepository;

	@Autowired
	private Article_Scheme_master_Repository article_Scheme_master_Repository;

	@Autowired
	private LocationService locService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	TransactionalRepository transactionalRepository;

	@Autowired
	private TaskMasterService taskMasterService;
	@Autowired
	private CompanyMasterService compMasterService;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ArticleSchemMasterCompanyData> getCompanyDataforSchemeMaster() throws Exception {

		return article_Scheme_master_Repository.getCompanyDataForArticleSchemeMaster();

	}

	@Override
	public List<ArticleSchemeMasterSALEPRODData> get_SALEPROD_DataforSchemeMaster() {
		return article_Scheme_master_Repository.getSaleProductDataForArticleSchemeMaster();

	}

	@Override
	public List<SpecificLocationBean> getSpecificLocation(String subCompanyId) throws Exception {

		return article_Scheme_master_Repository.getSpecificLocation(subCompanyId);
	}

	@Override
	public List<SchemeMasterSampleProductBean> get_sample_item(String subCompanyId) throws Exception {

		return article_Scheme_master_Repository.get_sample_item(subCompanyId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveArticle_Scheme_master(Article_save_as_bean bean, HttpServletRequest request,
			List<MultipartFile> filesList) throws Exception {
//		Date Apply_Invoice_From=MedicoResources.convert_DD_MM_YYYY_toDate(bean.getApply_invoice_from());
		Boolean Saved = false;
		System.out.println(bean);
//		bean.setFinalyear(Year.now().getValue() + "");
		String ip_addr = request.getRemoteAddr();
//		String saleProd_Code = article_Scheme_master_Repository.get_SaleProd_Code(bean.getSale_product_id());
		String article_Prod_Code = article_Scheme_master_Repository.get_Article_Prod_Code(bean.getArticle_id());
		String subcompany_Code = article_Scheme_master_Repository.get_Subcompany_Code(bean.getSubcompany());
		if (bean.getRemarks() == null || bean.getRemarks().equals("null")) {
			bean.setRemarks("");
		}
		System.err.println(bean);
		List<trd_scheme_mst_hdr> scheme_mst_hdrs = new ArrayList<>();
		long sl_no = 0L;
		String schemeCode = "SCM";
		List<String> scheme_code_List = new ArrayList<>();
		Path copyfrom = null;
		Path target = null;
//		String [] locationslist = bean.getLocation_Specific().stream().distinct().collect(Collectors.toList());
//		bean.setLocation_Specific(locationslist);
		List<String> arrayof_trd_scheme_mst_hdr_slno = new ArrayList<>();
		
		if (bean.getSpecific().equals("A")) {
			schemeCode += "000";
			sl_no = article_Scheme_master_Repository.getId()+1;
			System.out.println(sl_no);
			String strSl_no = Long.toString(sl_no);
			int length = strSl_no.length();
			int addZeros = 5 - length;
			for (int i = 0; i < addZeros; i++) {
				schemeCode += "0";
			}
			schemeCode += sl_no ;
			trd_scheme_mst_hdr Header = new trd_scheme_mst_hdr();
			Header.setSub_comp_cd(subcompany_Code);
			Header.setCompany_cd(bean.getCompany_code());
			Header.setSub_comp_id(Long.valueOf(bean.getSubcompany()));
			Header.setTrd_scheme_code(schemeCode);
			Header.setTrd_scheme_name(bean.getScheme_Name());
			Header.setTrd_scheme_descr(bean.getScheme_Description());
			Header.setScheme_apptype(bean.getSpecific());
			Header.setValid_from_dt(bean.getValid_From());
			Header.setApply_inv_from(bean.getApply_invoice_from());
			Header.setValid_to_dt(bean.getValid_To());
			Header.setApply_to(bean.getApply_Scheme_to());
			Header.setTrd_sch_type(bean.getArticle_Type());
			Header.setInvoice_type(bean.getApply_to_Individual_Invoice());
			Header.setArticle_prod_cd(article_Prod_Code);
			Header.setArticle_prod_id(Long.valueOf(bean.getArticle_id()));
			Header.setClosure_date(bean.getValid_To());
			if (bean.getApply_Scheme_to().equals("V")) {
				Header.setArticle_qty_per_tot_val(Long.valueOf(bean.getQty()));
				Header.setBilled_value(Long.valueOf(bean.getValue_article()));
			} else {
				Header.setArticle_qty_per_tot_val(0L);
			}
			Header.setArticle_prod_rate(bean.getRate());
			Header.setCreated_by(bean.getCreated_by());
			Header.setCreated_date(bean.getCreated_date());
//			Header.setLast_mod_by(bean.getCreated_by());
//			Header.setLast_mod_date(bean.getCreated_date());
			Header.setTrd_sch_status("E");
			Header.setRemarks(bean.getRemarks());
			Header.setTrd_scheme_id("0");
			Header.setFinyear(bean.getFinalyear());
			Header.setScheme_div_id(bean.getScheme_division());
			Header.setSpecific_brand_scheme(bean.getSales_product_division());
			Header.setCycle_no(0);
			Header.setGrn__tot_qty(bean.getGrn_qty());
			this.transactionalRepository.persist(Header);
			
			SalesProdDetails[] beans = bean.getSales_prod_details();
			for (SalesProdDetails b : beans) {
				trd_scheme_mst_dtl dtl = new trd_scheme_mst_dtl();
				dtl.setCompany_cd(bean.getCompany_code());
				dtl.setScheme_apptype(bean.getArticle_Type());
				dtl.setTrd_sch_slno(Header.getTrd_sch_slno());
				dtl.setTrd_scheme_code(Header.getTrd_scheme_code());
				dtl.setValid_from_dt(bean.getValid_From());
				dtl.setValid_to_dt(bean.getValid_To());
				dtl.setApply_to(bean.getApply_Scheme_to());
				dtl.setSale_prod_id(Long.valueOf(b.getSale_PROD_ID()));
				dtl.setSale_prod_code_erp(b.getSales_product_erpcode());
				dtl.setSale_prod_code_sg(b.getSales_product_code());
				
				if (bean.getApply_Scheme_to().equals("V")) {
					dtl.setPer_sale_qty_billed(0L);
					dtl.setPer_sale_qty_free(0L);
					dtl.setPer_sale_qty_tot((0L));
					dtl.setArticle_qty(0L);
				} else if (bean.getApply_Scheme_to().equals("Q")) {
					dtl.setPer_sale_qty_billed(Long.valueOf(b.getBilled()));
					
					if (b.getFree() == null) {
						b.setFree(0L);
					}
					dtl.setPer_sale_qty_free(Long.valueOf(b.getFree()));
					dtl.setPer_sale_qty_tot(Long.valueOf(b.getBilled()) + Long.valueOf(b.getFree()));
					dtl.setArticle_qty(Long.valueOf(b.getArticle_Qty()));
				}
				dtl.setCreated_by(bean.getCreated_by());
				dtl.setCreated_date(bean.getCreated_date());
				this.transactionalRepository.persist(dtl);
			}
			List<MultipartFile> files = filesList;
			for (MultipartFile m : files) {
				String file_name = m.getOriginalFilename().replace(" ", "_");
				byte[] bytes = m.getBytes();
				Path path = java.nio.file.Paths.get(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file_name);
				Files.write(path, bytes);
				TRD_SCH_MST_DOCS masterBean = new TRD_SCH_MST_DOCS();
				masterBean.setDoc_ins_dt(bean.getCreated_date());
				masterBean.setDoc_ins_ip_addr(ip_addr);
				masterBean.setDoc_ins_usr_id(bean.getCreated_by());
				masterBean.setFilename(file_name);
				masterBean.setFilepath(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file_name);
				if (m.getContentType().equals("image/png") || m.getContentType().equals("image/jpeg")) {
					masterBean.setFiletype("I");
				} else {
					masterBean.setFiletype("D");
				}
				masterBean.setTrd_sch_slno(Long.valueOf(Header.getTrd_sch_slno()));
				System.out.println(masterBean);
				this.transactionalRepository.persist(masterBean);
			}
			scheme_code_List.add(Header.getTrd_scheme_code());
			scheme_mst_hdrs.add(Header);
			System.out.println("Trd_sch_slno:::::" + Header);
			System.err.println(scheme_mst_hdrs);

			// else for location specific
		} else {
////			String [] list = bean.getLocation_Specific();
			String[] list = bean.getLocation_Specific();
			scheme_mst_hdrs = new ArrayList<>();
			for (String i : list) {
				schemeCode = "SCM";
				int location_length = i.toString().length();
				int zeros_for_Locations = 3 - location_length;
				for (int k = 0; k < zeros_for_Locations; k++) {
					schemeCode += 0;
				}
				System.out.println(schemeCode + " 1st");
				schemeCode += i;
				System.out.println(schemeCode + " 2st");
				sl_no = article_Scheme_master_Repository.getId();
				String strSl_no = Long.toString(sl_no);
				int length = strSl_no.length();
				int addZeros = 5 - length;
				for (int j = 0; j < addZeros; j++) {
					schemeCode += "0";
				}
				System.out.println(schemeCode + " 3st");
				schemeCode += sl_no + 1;
				System.out.println(schemeCode + " 4st");
				trd_scheme_mst_hdr Header = new trd_scheme_mst_hdr();
				Header.setSub_comp_cd(subcompany_Code);
				Header.setCompany_cd(bean.getCompany_code());
				Header.setSub_comp_id(Long.valueOf(bean.getSubcompany()));
				Header.setScheme_apptype(bean.getSpecific());
				Header.setLoc_id(Long.valueOf(i));
				Header.setTrd_scheme_code(schemeCode);
				Header.setTrd_scheme_name(bean.getScheme_Name());
				Header.setTrd_scheme_descr(bean.getScheme_Description());
				Header.setValid_from_dt(bean.getValid_From());
				Header.setValid_to_dt(bean.getValid_To());
				Header.setApply_inv_from(bean.getApply_invoice_from());
				Header.setApply_to(bean.getApply_Scheme_to());
				Header.setTrd_sch_type(bean.getArticle_Type());
				Header.setInvoice_type(bean.getApply_to_Individual_Invoice());
				Header.setArticle_prod_cd(article_Prod_Code);
				Header.setArticle_prod_id(Long.valueOf(bean.getArticle_id()));
				Header.setArticle_qty_per_tot_val(bean.getQty());
				Header.setArticle_prod_rate(bean.getRate());
				Header.setCreated_by(bean.getCreated_by());
				Header.setCreated_date(bean.getCreated_date());
				Header.setClosure_date(bean.getValid_To());
//				if (bean.getApply_Scheme_to().equals("V")) {
//					Header.setArticle_qty_per_tot_val(Long.valueOf(bean.getQty()));
//					Header.setBilled_value(Long.valueOf(bean.getValue_article()));
//				} else {
//					Header.setArticle_qty_per_tot_val(0L);
				Header.setTrd_sch_status("E");
				Header.setRemarks(bean.getRemarks());
				Header.setTrd_scheme_id("0");
				Header.setFinyear(bean.getFinalyear());
				Header.setScheme_div_id(bean.getScheme_division());
				Header.setSpecific_brand_scheme(bean.getSales_product_division());
				Header.setCycle_no(0);
				Header.setGrn__tot_qty(bean.getGrn_qty());
				System.out.println(Header);
				this.transactionalRepository.persist(Header);
				
				SalesProdDetails[] beans = bean.getSales_prod_details();
				for (SalesProdDetails b : beans) {
					trd_scheme_mst_dtl dtl = new trd_scheme_mst_dtl();
					dtl.setCompany_cd(bean.getCompany_code());
					dtl.setScheme_apptype(bean.getSpecific());
					dtl.setTrd_sch_slno(Header.getTrd_sch_slno());
					dtl.setTrd_scheme_code(Header.getTrd_scheme_code());
					dtl.setValid_from_dt(bean.getValid_From());
					dtl.setValid_to_dt(bean.getValid_To());
					dtl.setApply_to(bean.getApply_Scheme_to());
					dtl.setSale_prod_id(Long.valueOf(b.getSale_PROD_ID()));
					dtl.setSale_prod_code_erp(b.getSales_product_erpcode());
					dtl.setSale_prod_code_sg(b.getSales_product_code());
					if (bean.getApply_Scheme_to().equals("Q")) {
						if (b.getFree() == null) {
							b.setFree(0L);
						}
						dtl.setPer_sale_qty_billed(Long.valueOf(b.getBilled()));
						dtl.setPer_sale_qty_free(Long.valueOf(b.getFree()));
						dtl.setPer_sale_qty_tot(Long.valueOf(b.getBilled()) + Long.valueOf(b.getFree()));
						dtl.setArticle_qty(Long.valueOf(b.getArticle_Qty()));
					} else {
						dtl.setPer_sale_qty_billed(0L);
						dtl.setPer_sale_qty_free(0L);
						dtl.setPer_sale_qty_tot(0L);
						dtl.setArticle_qty(0L);
					}
					dtl.setCreated_by(bean.getCreated_by());
					dtl.setCreated_date(bean.getCreated_date());
					this.transactionalRepository.persist(dtl);
				}
				this.transactionalRepository.persist(Header);
				scheme_code_List.add(Header.getTrd_scheme_code());
				scheme_mst_hdrs.add(Header);
				System.out.println(scheme_mst_hdrs);
				System.out.println("Trd_sch_slno:::::" + Header.getTrd_sch_slno());
				arrayof_trd_scheme_mst_hdr_slno.add(Header.getTrd_sch_slno() + "");
			}
			List<MultipartFile> files = filesList;
			for (MultipartFile m : files) {
				String file_name = m.getOriginalFilename().replace(" ", "_");
				byte[] bytes = m.getBytes();
				Path path = java.nio.file.Paths.get(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file_name);
				Files.write(path, bytes);
				for (String slNo : arrayof_trd_scheme_mst_hdr_slno) {
					System.out.println(slNo + ":::::::" + m.getContentType());
					System.out.println(slNo + ":::::::" + m.getOriginalFilename());
					TRD_SCH_MST_DOCS masterBean = new TRD_SCH_MST_DOCS();
					masterBean.setDoc_ins_dt(bean.getCreated_date());
					masterBean.setDoc_ins_ip_addr(ip_addr);
					masterBean.setDoc_ins_usr_id(bean.getCreated_by());
					masterBean.setFilename(file_name);
					masterBean.setFilepath(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file_name);
					if (m.getContentType().equals("image/png") || m.getContentType().equals("image/jpeg")) {
						masterBean.setFiletype("I");
					} else {
						masterBean.setFiletype("D");
					}
					masterBean.setTrd_sch_slno(Long.valueOf(slNo));
					System.out.println(masterBean);
					this.transactionalRepository.persist(masterBean);
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		String SavedSchemeCode = "";
		Saved = true;
		map.put("scheme_code_List", scheme_code_List);
		map.put("scheme_mst_hdrs", scheme_mst_hdrs);
		map.put("Saved", Saved);
		String SavedSchemeCodeString = "";
		for (int j = 0; j < scheme_code_List.size(); j++) {
			SavedSchemeCodeString += scheme_code_List.get(j) + ",";
		}
		System.out.println(SavedSchemeCodeString);
		map.put("SavedSchemeCodeString", SavedSchemeCodeString.subSequence(0, SavedSchemeCodeString.length() - 1));
		return map;
	}

	private void setDetails_Data(Article_save_as_bean bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Article_bean_for_modify> get_approval_data(String employeeId) throws Exception {

		return article_Scheme_master_Repository.get_approval_data(employeeId);
	}

	@Override
	public Boolean deleteScheme(String trs_sl_no) throws Exception {

		return article_Scheme_master_Repository.deleteScheme(trs_sl_no);

	}

	@Override
	public Map<String, Object> update(Article_save_as_bean bean, HttpServletRequest request, List<MultipartFile> files)
			throws Exception {
//		String saleProd_Code = article_Scheme_master_Repository.get_SaleProd_Code(bean.getSale_product_id());
		String article_Prod_Code = article_Scheme_master_Repository.get_Article_Prod_Code(bean.getArticle_id());
		return article_Scheme_master_Repository.update(bean, article_Prod_Code, request, files);

	}

	@Override
	public Map<String, Object> confirm_approval(List<Long> arrayOfslNumber, HttpSession session,
			HttpServletRequest request, String userId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		for (long l : arrayOfslNumber) {

			trd_scheme_mst_hdr hdr = article_Scheme_master_Repository.getTrdSchemeMasterByIdForApproval(l);
			this.sendArtRequestForApproval(hdr, userId, "", "", request, session);

			map.put(l + "", "Sent for Approval ");

		}

		return map;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendArtRequestForApproval(trd_scheme_mst_hdr hdr, String user, String approver_email, String remark,
			HttpServletRequest request, HttpSession session) throws Exception {

		// String ip_addr = request.getRemoteAddr();

		System.out.println("user " + user);
		String tranType = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();

		if (hdr.getTrd_sch_status().equalsIgnoreCase("E")) {

			tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(0L, SCM_MST_APPR,
					hdr.getCompany_cd().trim());

			if (tranType == null || tranType.isEmpty()) {
				throw new MedicoException("Could not find Approval Data !");
			}

			String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
			System.out.println("tranType " + tranType);

			List<TaskMaster> masters = taskMasterService.getTask(hdr.getScheme_div_id(), null, null, tranType, null,
					null, TASK_FLOW, null, null);

			if (masters.size() == 0) {
				throw new MedicoException("Task master record not found");
			}

			TaskMaster taskMaster = masters.get(0);
			List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
			if (task_Master_Dtls.size() == 0) {
				throw new MedicoException("Task master detail record not found");
			}

			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("initiator", user);
			variables.put("initiator_desc", user);
			variables.put("initiator_email", "");
			variables.put("tran_ref_id", hdr.getTrd_sch_slno());// sl no
			variables.put("tran_type", tranType);
			variables.put("inv_group", hdr.getScheme_div_id());
			variables.put("loc_id", 0L);// oL
			variables.put("cust_id", 0L);
			variables.put("tran_no", hdr.getTrd_scheme_code());// schme code
			variables.put("company_code", companyCode);
			variables.put("totaltask", masters.size());
			variables.put("amount", BigDecimal.ZERO);
			variables.put("escalatetime", null);
			variables.put("fin_year_id", hdr.getFinyear());// fin year
			variables.put("stock_point_id", 0);
			variables.put("doc_type", "SCM");
			variables.put("task_flow", TASK_FLOW);
			variables.put("remark", remark);
			variables.put("fs_id", 0L);
			variables.put("approval_type", null);

			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("schemeMasterApproval",
					variables);
			// changing approval status
			hdr.setTrd_sch_status("F");
			hdr.setLast_mod_date(new Date());
			hdr.setLast_mod_by(user);
			transactionalRepository.update(hdr);

			// send mail logic

			try {
//				this.sendMailForArticleSchemeRequest(hdr.getTrd_sch_slno(), user, "selfApprove", null, "F", "nextApproval",Long.valueOf(hdr.getFinyear()), 
//						tranType, companyCode, request.getRemoteAddr(),hdr);

				this.sendmails(hdr, tranType, null, "nextApproval", companyCode, user, request.getRemoteAddr(), false,
						null);

			} catch (Exception e) {
				System.out.println("Error while sending mail :::::");
				e.printStackTrace();
			}
		}

	}

	@Override
	public void sendmails(trd_scheme_mst_hdr hdr, String tranType, String taskId, String nextApprovar,
			String companyCode, String user, String ipaddr, boolean isValidityExtension, Art_Sch_Vld_Ext artSchVldExt)
			throws Exception {

		System.err.println("itsgdsvdshv");
		Map<String, String> email_empId_map = new HashMap<String, String>();
		List<String> mailList = new ArrayList<>();
		List<MailBean> approvalDetails = null;
		Long process_id = 0L;
		String userId = "";

		MailBean mail = null;
		String link = null;
		String discardLink = null;

		Company company = this.compMasterService.getCompanyDetails();

		String scheme_master_appr_tran_type = null;
		if (isValidityExtension) {
			scheme_master_appr_tran_type = "300";
		} else {
			scheme_master_appr_tran_type = "32";
		}

		List<MailBean> list = null;

		if (isValidityExtension) {
			list = taskMasterService.getMailSendDetails(artSchVldExt.getSlno(), "nextApproval",
					scheme_master_appr_tran_type);
		} else {
			list = taskMasterService.getMailSendDetails(hdr.getTrd_sch_slno(), "nextApproval",
					scheme_master_appr_tran_type);
		}

		mail = list.get(0);
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

		String subject = "";
		if (nextApprovar == null) {
			if (isValidityExtension) {
				subject = "Scheme Validity Extension ";
			} else {
				subject = "Article Scheme Master Approval ";
			}

			List<String> testTo = new ArrayList<>();
			this.send_Mail_for_cfa_location(hdr.getTrd_sch_slno() + "", subject, subject, testTo, isValidityExtension,
					artSchVldExt, false, "", false);
		} else {
			if (companyCode.trim().equals("PFZ")) {
				Iterator<Map.Entry<String, String>> itr = email_empId_map.entrySet().iterator();

				while (itr.hasNext()) {
					Map.Entry<String, String> entry = itr.next();
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
					System.out.println(entry.getKey());

					String token = Base64.getEncoder().encodeToString(entry.getKey().getBytes(StandardCharsets.UTF_8));
					System.out.println("toekn->" + token);
					System.out.println("decoded->" + new String(Base64.getDecoder().decode(token)));
					if (isValidityExtension) {
						link = "rest/saveSchemeValidityExtensionApprovalViaMail?task_id=";
						discardLink = "rest/saveSchemeValidityExtensionApprovalViaMail?task_id=";

					} else {
						link = "rest/saveArticleSchemeMasterApprovalViaMail?task_id=";
						discardLink = "rest/saveArticleSchemeMasterApprovalViaMail?task_id=";
					}
					link = link + mail.getAct_taskid() + "&decision=approve&userName=" + mail.getAssignee_()
							+ "&remark=" + mail.getAct_taskid() + "&tran_id="
							+ (isValidityExtension ? artSchVldExt.getSlno() : hdr.getTrd_sch_slno()) + "&token=" + token
							+ "&tranType=" + tranType;

					discardLink = discardLink + mail.getAct_taskid() + "&decision=discard&userName="
							+ mail.getAssignee_() + "&remark=" + mail.getAct_taskid() + "&tran_id="
							+ (isValidityExtension ? artSchVldExt.getSlno() : hdr.getTrd_sch_slno()) + "&token=" + token
							+ "&tranType=" + tranType;

					System.out.println("Task Id :::::" + mail.getAct_taskid());

					this.send_Mail_for_cfa_location(hdr.getTrd_sch_slno().toString(), link, discardLink,
							Arrays.asList(entry.getValue()), isValidityExtension, artSchVldExt, false, "", false);

					if (mail.getAct_taskid() != null)
						this.appr_mail_service.saveAfterMailSend(Long.valueOf(mail.getAct_taskid()),
								(isValidityExtension ? artSchVldExt.getSlno() : hdr.getTrd_sch_slno()), tranType,
								process_id, entry.getValue(), entry.getKey(), user, ipaddr);

				}
			}
		}
	}

	@Override
	public String downloadAsZipFile(List<String> arrayOfFilesToZip) throws Exception {

		File zippedFile = new File("D://sg//ZipFiles//" + new Date().getTime() + "compressed.zip");
		String FilePath = zippedFile.getName();

		FileOutputStream fos = new FileOutputStream(zippedFile);

		ZipOutputStream zipOut = new ZipOutputStream(fos);

		for (String srcFile : arrayOfFilesToZip) {
			File fileToZip = new File("D://sg//files//" + srcFile.trim());
			FileInputStream fis = new FileInputStream(fileToZip);
			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zipOut.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			fis.close();
		}

		System.out.println("Created::" + fos);
		zipOut.close();
		fos.close();

		System.out.println("Created::");

		return FilePath;

	}

//	@Override
//	public Map<String, Object> get_Docs_Details(String schemeslno, String trantype) throws Exception {
//
//		System.err.println(schemeslno);
//		// TODO Auto-generated method stub
//
//		List<Articele_Scheme_Approved_Data> approved_Data = new ArrayList<>();
//		Map<String, Object> map = new HashMap<>();
//		List<SchemeDetailDataBean> trdSchemeDetail = article_Scheme_master_Repository
//				.getTrdSchemeDetailById(Long.valueOf(schemeslno));
//		map.put("trdSchemeDetail", trdSchemeDetail);
//
//		System.err.println("trdSchemeDetail::::::::" + trdSchemeDetail.get(0).getSCHEME_DIV_ID());
//
//		List<ArticleDocsBean> articleDocsBeans = article_Scheme_master_Repository.get_Docs_Details(schemeslno);
//		SimpleDateFormat outputFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
//		if (trantype != null) {
//			approved_Data = this.getApprovedData(trdSchemeDetail.get(0).getSCHEME_DIV_ID(), trantype,
//					Long.valueOf(schemeslno));
//			for (Articele_Scheme_Approved_Data a : approved_Data) {
//				a.setNew_mod_datestring(outputFormat.format(a.getNEW_MOD_DATE()));
//			}
//		}
//
//		Boolean showFileButton = false;
//		if (articleDocsBeans.size() > 0) {
//			showFileButton = true;
//		}
//
//		System.err.println(approved_Data);
//		map.put("articleDocsBeans", articleDocsBeans);
//		map.put("showFileButton", showFileButton);
//		map.put("approved_Data", approved_Data);
//		System.out.println(showFileButton);
//
//		return map;
//	}

	@Override
	public Map<String, Object> get_Docs_Details(String schemeslno, String trantype, String dataMode) throws Exception {

		System.err.println(schemeslno);
		// TODO Auto-generated method stub
		List<ArticleDocsBean> articleDocsBeans = new ArrayList<>();
		List<SchemeDetailDataBean> trdSchemeDetail = new ArrayList<>();

		SimpleDateFormat outputFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
		List<Articele_Scheme_Approved_Data> approved_Data = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		if (dataMode.equalsIgnoreCase("V") || dataMode.equalsIgnoreCase("X")) {

			articleDocsBeans = article_Scheme_master_Repository.get_Docs_Details_For_EXT(schemeslno);

			trdSchemeDetail = article_Scheme_master_Repository.getTrdSchemeDetailById(
					this.article_Scheme_master_Repository.getSchemeCodeBySchemeExId(schemeslno));
		} else {
			articleDocsBeans = article_Scheme_master_Repository.get_Docs_Details(schemeslno);
			trdSchemeDetail = article_Scheme_master_Repository.getTrdSchemeDetailById(Long.valueOf(schemeslno));
		}

		if (trantype != null) {
			approved_Data = this.getApprovedData(trdSchemeDetail.get(0).getSCHEME_DIV_ID(), trantype,
					Long.valueOf(schemeslno));
			for (Articele_Scheme_Approved_Data a : approved_Data) {
				a.setNew_mod_datestring(outputFormat.format(a.getNEW_MOD_DATE()));
			}
		}

		map.put("trdSchemeDetail", trdSchemeDetail);

//		if (trantype != null) {
//			approved_Data = this.getApprovedData(trdSchemeDetail.get(0).getSCHEME_DIV_ID(), trantype,
//					Long.valueOf(articleDocsBeans.size()>0 ?articleDocsBeans.get(0).getScheme_slno():schemeslno));
//			for (Articele_Scheme_Approved_Data a : approved_Data) {
//				a.setNew_mod_datestring(outputFormat.format(a.getNEW_MOD_DATE()));
//			}
//		}

		Boolean showFileButton = false;
		if (articleDocsBeans.size() > 0) {
			showFileButton = true;
		}

		System.err.println(approved_Data);
		map.put("articleDocsBeans", articleDocsBeans);
		map.put("showFileButton", showFileButton);
		map.put("approved_Data", approved_Data);
		System.out.println(showFileButton);

		return map;
	}

	@Override
	public Boolean deleteDoc(String docId, String dataMode) throws Exception {
		// TODO Auto-generated method stub
		return article_Scheme_master_Repository.deleteDoc(docId, dataMode);
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void finalApproval(Long scheme_slno, String last_approved_by, String comp_cd, String isapproved,
//			String motivation) throws Exception {
//
//		trd_scheme_mst_hdr scmHeader = null;
//		List<trd_scheme_mst_hdr> dtllst = null;
////		System.out.println("In Final Approval Of Article Scheme Request");
//		scmHeader = this.article_Scheme_master_Repository.getTrdSchemeMasterByIdForApproval(scheme_slno);
//
//		if (isapproved.equalsIgnoreCase("A")) {
//			scmHeader.setTrd_sch_status("A");
//			scmHeader.setLast_mod_by(last_approved_by);
//			scmHeader.setLast_mod_date(new Date());
//			transactionalRepository.update(scmHeader);
//		} else {
//			scmHeader.setTrd_sch_status("D");
//			scmHeader.setLast_mod_by(last_approved_by);
//			scmHeader.setLast_mod_date(new Date());
//			transactionalRepository.update(scmHeader);
//		}
//	}
//

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalApproval(Long scheme_slno, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception {

		trd_scheme_mst_hdr scmHeader = null;
		List<trd_scheme_mst_hdr> dtllst = null;
		System.out.println("In Final Approval Of Article Scheme Request" + scheme_slno);
		scmHeader = this.article_Scheme_master_Repository.getTrdSchemeMasterByIdForApproval(scheme_slno);
		List<TRD_SCH_MST_DOCS> docList = this.article_Scheme_master_Repository.getDocsBySchemeSlno(scheme_slno);

		if (isapproved.equalsIgnoreCase("A")) {
			scmHeader.setTrd_sch_status("A");
			scmHeader.setLast_mod_by(last_approved_by);
			scmHeader.setLast_mod_date(new Date());
			scmHeader.setCycle_no(1);
			transactionalRepository.update(scmHeader);

			for (TRD_SCH_MST_DOCS d : docList) {

				d.setCycle_no("1");
				transactionalRepository.update(d);
			}

//			
//			try {
			send_Mail_for_cfa_locations(scmHeader.getTrd_sch_slno().toString(), false, null);
////				 send_Mail_for_cfa_location(String hdr_sl_no, String link, String discardLink, List<String> tolist)
////				System.err.println("final approvaal");
//			} catch (Exception e) {
//				e.printStackTrace();
//				// TODO: handle exception
//			}
//			

		} else {
			scmHeader.setTrd_sch_status("E");
			scmHeader.setLast_mod_by(last_approved_by);
			scmHeader.setLast_mod_date(new Date());
			transactionalRepository.update(scmHeader);
		}
	}
//
//	private void send_Mail_for_cfa_locations(String hdr_sl_no) throws Exception {
//

//	private void send_Mail_for_cfa_locations(String hdr_sl_no, boolean isValidityException, Long extVldSlno)
//			throws Exception {
//
//		// getting header data
//		List<trd_scheme_mst_hdr> headerData = this.article_Scheme_master_Repository.get_HDR_Data_For_Edit(hdr_sl_no);
//
//		// getting detail data
//		List<trd_scheme_mst_dtl> detailData = this.article_Scheme_master_Repository.get_Detail_Data_For_Edit(hdr_sl_no);
//
//		// getting files data
//		List<ArticleDocsBean> docsData = this.article_Scheme_master_Repository.get_Docs_Details(hdr_sl_no);
//
//		Art_Sch_Vld_Ext scmvldExt = null;
//		if (isValidityException) {
//			scmvldExt = this.getVldExtenById(extVldSlno);
//		}
//
//		String tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(0L,
//				(isValidityException ? SCM_MST_VLD_EXT : SCM_MST_APPR), headerData.get(0).getCompany_cd().trim());
//
//		System.out.println(headerData);
//		System.out.println(detailData);
//		System.out.println(docsData);
//
//		System.out.println(" trans type::::::::::::" + tranType);
//
//		EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
//
//		String subject = "";
//		if (isValidityException) {
//			subject = "Scheme Validity Extended :" + headerData.get(0).getTrd_scheme_name().trim();
//		} else {
//			subject = "New Scheme :" + headerData.get(0).getTrd_scheme_name().trim();
//		}
//		List<String> to_list = new ArrayList<String>();
//		List<String> ccList = new ArrayList<>();
//
//		Map<String, String> mapInlineImages = new HashMap<>();
//		int i = 0;
//		StringBuffer body = this.getArticleDataBodyForMail(hdr_sl_no, headerData.get(0), "", "", isValidityException,
//				scmvldExt, true, false, "", false);
//
//		if (docsData.size() > 0) {
//			List<String> filesPath = new ArrayList<>();
//			for (ArticleDocsBean bean : docsData) {
//
//				filesPath.add(bean.getFileName().trim());
//
//			}
//
//			String zipFileName = this.downloadAsZipFile(filesPath);
//			String zipFilePath = "D://sg//ZipFiles//" + zipFileName;
//			mapInlineImages.put("Attachment" + 1, zipFilePath.trim());
//
//		}
//		// to get cc list
//		to_list = this.article_Scheme_master_Repository.get_ToList(headerData); 
//		ccList = this.article_Scheme_master_Repository.get_Cclist(headerData);
//
//		// to get to list
//		System.out.println("cc::::::::" + ccList);
//		System.out.println("to_list::::::::" + to_list);
//
//		sendMail.sendHtmlMailWithCCAndFile(to_list, subject, body.toString(), mapInlineImages, mailconfig.getEmail(),
//				mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(),
//				mailconfig.getPort(), ccList);
//		// }
//
//	}

//	private void send_Mail_for_cfa_locations(String hdr_sl_no, boolean isValidityException, Long extVldSlno)
//			throws Exception {
//
//		// getting header data
//		List<trd_scheme_mst_hdr> headerData = this.article_Scheme_master_Repository.get_HDR_Data_For_Edit(hdr_sl_no);
//
//		// getting detail data
//		List<trd_scheme_mst_dtl> detailData = this.article_Scheme_master_Repository.get_Detail_Data_For_Edit(hdr_sl_no);
//
//		// getting files data
//		List<ArticleDocsBean> docsData = this.article_Scheme_master_Repository.get_Docs_Details(hdr_sl_no);
//
//		Art_Sch_Vld_Ext scmvldExt = null;
//		if (isValidityException) {
//			scmvldExt = this.getVldExtenById(extVldSlno);
//		}
//
//		String tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(0L,
//				(isValidityException ? SCM_MST_VLD_EXT : SCM_MST_APPR), headerData.get(0).getCompany_cd().trim());
//
//		System.out.println(headerData);
//		System.out.println(detailData);
//		System.out.println(docsData);
//
//		System.out.println(" trans type::::::::::::" + tranType);
//
//		EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
//
//		String subject = "";
//		if (isValidityException) {
//			subject = "Scheme Validity Extended :" + headerData.get(0).getTrd_scheme_name().trim();
//		} else {
//			subject = "New Scheme :" + headerData.get(0).getTrd_scheme_name().trim();
//		}
//		List<String> to_list = new ArrayList<String>();
//		List<String> ccList = new ArrayList<>();
//
//		Map<String, String> mapInlineImages = new HashMap<>();
//		int i = 0;
//		StringBuffer body = this.getArticleDataBodyForMail(hdr_sl_no, headerData.get(0), "", "", isValidityException,
//				scmvldExt, true, false, "", false);
//
//		if (docsData.size() > 0) {
//			List<String> filesPath = new ArrayList<>();
//			int j = 1;
//			for (ArticleDocsBean bean : docsData) {
//
//				filesPath.add(bean.getFileName().trim());
//				String zipFileName = this.downloadAsZipFile(filesPath);
//				String zipFilePath = "D://sg//ZipFiles//" + zipFileName;
//				mapInlineImages.put("Attachment" + j, zipFilePath.trim());
//				j++;
//
//			}
//
//		}
//		// to get cc list
//		to_list = this.article_Scheme_master_Repository.get_ToList(headerData);
//		ccList = this.article_Scheme_master_Repository.get_Cclist(headerData);
//
//		// to get to list
//		System.out.println("mapInlineImages::::::::" + mapInlineImages);
//		System.out.println("to_list::::::::" + to_list);
//		System.out.println("to_list::::::::" + to_list);
//
//		sendMail.sendHtmlMailWithCCAndFile(to_list, subject, body.toString(), mapInlineImages, mailconfig.getEmail(),
//				mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(),
//				mailconfig.getPort(), ccList);
//		// }
//
//	}

	private void send_Mail_for_cfa_locations(String hdr_sl_no, boolean isValidityException, Long extVldSlno)
			throws Exception {

		// getting header data
		List<trd_scheme_mst_hdr> headerData = this.article_Scheme_master_Repository.get_HDR_Data_For_Edit(hdr_sl_no);

		// getting detail data
		List<trd_scheme_mst_dtl> detailData = this.article_Scheme_master_Repository.get_Detail_Data_For_Edit(hdr_sl_no);

		// getting files data
		List<ArticleDocsBean> docsData = this.article_Scheme_master_Repository.get_Docs_Details(hdr_sl_no);

		Art_Sch_Vld_Ext scmvldExt = null;
		if (isValidityException) {
			scmvldExt = this.getVldExtenById(extVldSlno);
		}

		String tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(0L,
				(isValidityException ? SCM_MST_VLD_EXT : SCM_MST_APPR), headerData.get(0).getCompany_cd().trim());

		System.out.println(headerData);
		System.out.println(detailData);
		System.out.println(docsData);

		System.out.println(" trans type::::::::::::" + tranType);

		EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");

		String subject = "";
		if (isValidityException) {
			subject = "Scheme Validity Extended :" + headerData.get(0).getTrd_scheme_name().trim();
		} else {
			subject = "New Scheme :" + headerData.get(0).getTrd_scheme_name().trim();
		}
		List<String> to_list = new ArrayList<String>();
		List<String> ccList = new ArrayList<>();

		Map<String, String> mapInlineImages = new HashMap<>();
		int i = 0;
		StringBuffer body = this.getArticleDataBodyForMail(hdr_sl_no, headerData.get(0), "", "", isValidityException,
				scmvldExt, true, false, "", false);

		if (docsData.size() > 0) {

			String zipFilePath = "";
			List<String> filesPath = new ArrayList<>();
			int j = 1;
			for (ArticleDocsBean bean : docsData) {

				filesPath.add(bean.getFileName().trim());
				String zipFileName = this.downloadAsZipFile(filesPath);
				zipFilePath = "D://sg//ZipFiles//" + zipFileName;

				j++;

			}

			mapInlineImages.put("Attachment" + 1, zipFilePath.trim());

		}
		// to get cc list
		to_list = this.article_Scheme_master_Repository.get_ToList(headerData);
		ccList = this.article_Scheme_master_Repository.get_Cclist(headerData);

		// to get to list
//		System.out.println("mapInlineImages::::::::" + mapInlineImages);
		System.out.println("to_list::::::::" + to_list);
		System.out.println("to_list::::::::" + to_list);

		sendMail.sendHtmlMailWithCCAndFile(to_list, subject, body.toString(), mapInlineImages, mailconfig.getEmail(),
				mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(),
				mailconfig.getPort(), ccList);
		// }

	}

//		// getting header data
//
//		List<trd_scheme_mst_hdr> headerData = this.article_Scheme_master_Repository.get_HDR_Data_For_Edit(hdr_sl_no);
//
//		// getting detail data
//		List<trd_scheme_mst_dtl> detailData = this.article_Scheme_master_Repository.get_Detail_Data_For_Edit(hdr_sl_no);
//
//		// getting files data
//
//		List<ArticleDocsBean> docsData = this.article_Scheme_master_Repository.get_Docs_Details(hdr_sl_no);
//
//		String tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(0L, "SCM",
//				headerData.get(0).getCompany_cd().trim());
//
//		System.out.println(headerData);
//		System.out.println(detailData);
//		System.out.println(docsData);
//
//		System.out.println(" trans type::::::::::::" + tranType);
//
//		EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
//		String subject = "New Scheme Applicable :" + headerData.get(0).getTrd_scheme_code().trim();
//
//		List<String> to_list = new ArrayList<String>();
//		List<String> ccList = new ArrayList<>();
//
//		Map<String, String> mapInlineImages = new HashMap<>();
//		int i = 0;
//		StringBuffer body = this.getArticleDataBodyForMail(hdr_sl_no, headerData.get(0), "", "");
//
//		if (docsData.size() > 0) {
//			List<String> filesPath = new ArrayList<>();
//			for (ArticleDocsBean bean : docsData) {
//
//				filesPath.add(bean.getFileName().trim());
//
//			}
//
//			String zipFileName = this.downloadAsZipFile(filesPath);
//			String zipFilePath = "D://sg//ZipFiles//" + zipFileName;
//			mapInlineImages.put("Attachment" + 1, zipFilePath.trim());
//
//		}
//		// to get cc list
//		ccList = this.article_Scheme_master_Repository.get_Cclist(headerData);
//
//		System.out.println(headerData);
//
//		// to get to list
//		List<MailBean> list = taskMasterService.getMailSendDetails(headerData.get(0).getTrd_sch_slno(), null, tranType);
//		MailBean mail = list.get(0);
//		to_list.add(mail.getEmp_email());
//
//		sendMail.sendHtmlMailWithCCAndFile(to_list, subject, body.toString(), mapInlineImages, mailconfig.getEmail(),
//				mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(),
//				mailconfig.getPort(), ccList);
//		// }
//
//	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void lock_article_product(String articleId, String userId) throws Exception {
//
//		Boolean deleted = this.article_Scheme_master_Repository.releasedLockedProduct(userId);
//
//		if (deleted) {
//
//			SCM_MST_PROD_LOCK productLock = new SCM_MST_PROD_LOCK();
//
//			productLock.setLog_time(new Date());
//			productLock.setProd_id(articleId);
//			productLock.setUser_id(userId);
//
//			this.transactionalRepository.persist(productLock);
//		}
//
//	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> lock_article_product(String articleId, String userId) throws Exception {

		Boolean deleted = this.article_Scheme_master_Repository.releasedLockedProduct(userId);

		Boolean productLocked = article_Scheme_master_Repository.check_If_The_Product_Already_Loaked(userId, articleId);
		Map<String, Object> map = new HashMap<>();

		if (!productLocked && deleted) {

			SCM_MST_PROD_LOCK productLock = new SCM_MST_PROD_LOCK();

			productLock.setLog_time(new Date());
			productLock.setProd_id(articleId);
			productLock.setUser_id(userId);

			this.transactionalRepository.persist(productLock);
			map.put("productLocked", productLocked);
		} else {

			System.out.println("its locked by another user");
			map.put("productLocked", productLocked);
		}

		return map;
	}

	@Override
	public List<SchemeMasterSampleProductBean> search_Article_Product_By_Name(String search_value, String subcompanyId)
			throws Exception {

		return this.article_Scheme_master_Repository.search_Article_Product_By_Name(search_value, subcompanyId);

	}

	@Override
	public void unlock_article_product(String userId) {

		this.article_Scheme_master_Repository.unlock_article_product(userId);

	}

	@Override
	public List<SchemeMasterSampleProductBean> get_top_Article_product(String subCompanyId) throws Exception {

		return article_Scheme_master_Repository.get_top_Article_product(subCompanyId);
	}

	@Override
	public List<ArticleSchemeMasterSALEPRODData> get_top_sale_product() {

		return article_Scheme_master_Repository.get_top_sale_product();

	}

	@Override
	public List<SamplProdGroup> get_brands(String sales_product_division, String scheme_division) {

		return article_Scheme_master_Repository.get_brands(sales_product_division, scheme_division);
	}

	@Override
	public List<ArticleSchemeMasterSALEPRODData> get_saleProduct(List<Long> arrayOfbrandSelectedValues,
			long subcompanyId) {
		// TODO Auto-generated method stub
		String brandValues = "";

		for (long l : arrayOfbrandSelectedValues) {

			brandValues += l + ",";
		}

		brandValues = brandValues.substring(0, brandValues.length() - 1);

		return article_Scheme_master_Repository.get_saleProduct(brandValues, subcompanyId);

	}

	@Override
	public Map<String, Object> get_Detail_Data_For_Edit(String trd_SCH_SLNO) throws Exception {

		Map<String, Object> map = new HashMap<>();

		List<trd_scheme_mst_hdr> hdr = this.article_Scheme_master_Repository.get_HDR_Data_For_Edit(trd_SCH_SLNO);

		List<trd_scheme_mst_dtl> dtl = this.article_Scheme_master_Repository.get_Detail_Data_For_Edit(trd_SCH_SLNO);

		String Sales_prod_id = "";
		for (trd_scheme_mst_dtl d : dtl) {

			Sales_prod_id += d.getSale_prod_id() + ",";
		}

		Sales_prod_id = Sales_prod_id.substring(0, Sales_prod_id.length() - 1);

		List<ArticleSchemeMasterSALEPRODData> saleProducts = this.article_Scheme_master_Repository
				.getSaleProductById(Sales_prod_id);

		long subCompanyId = 0;

		for (trd_scheme_mst_hdr h : hdr) {

			subCompanyId = h.getSub_comp_id();
		}

		List<SchemeMasterSampleProductBean> article_product = article_Scheme_master_Repository
				.get_sample_item(subCompanyId + "");
		List<SamplProdGroup> brands = article_Scheme_master_Repository.get_brands(hdr.get(0).getSpecific_brand_scheme(),
				hdr.get(0).getScheme_div_id() + "");
		List<ArticleDocsBean> docs = article_Scheme_master_Repository.get_Docs_Details(trd_SCH_SLNO);

		List<DivMaster> scheme_divisions = article_Scheme_master_Repository.get_Scheme_Division();
//		 get_Scheme_Division

		map.put("trd_scheme_mst_dtl", dtl);
		map.put("trd_scheme_mst_hdr", hdr);
		map.put("article_product", article_product);
		map.put("saleProducts", saleProducts);
		map.put("brands", brands);
		map.put("docs", docs);
		map.put("scheme_divisions", scheme_divisions);
		map.put("slnumber", trd_SCH_SLNO);
		Boolean showFileButton = false;

		if (docs.size() > 0) {
			showFileButton = true;
		}

		map.put("showFileButton", showFileButton);

		return map;

	}

	@Override
	public void delete_Article_Product_On_The_Product_Locking_Table(String userId) {
		this.article_Scheme_master_Repository.unlock_article_product(userId);
	}

//	@Override
//	public void send_Mail_for_cfa_location(String hdr_sl_no, String link, String discardLink, List<String> tolist,
//			boolean isValidityExtension, Art_Sch_Vld_Ext artSchVldExt, Boolean apprAction, String Approver_name,
//			boolean status) throws Exception {
//
//		// getting header data
//		List<trd_scheme_mst_hdr> headerData = this.article_Scheme_master_Repository.get_HDR_Data_For_Edit(hdr_sl_no);
//
//		// getting detail data
//		List<trd_scheme_mst_dtl> detailData = this.article_Scheme_master_Repository.get_Detail_Data_For_Edit(hdr_sl_no);
//
//		// getting files data
//		List<ArticleDocsBean> docsData = this.article_Scheme_master_Repository.get_Docs_Details(hdr_sl_no);
//
//		String tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(0L,
//				(isValidityExtension ? SCM_MST_VLD_EXT : SCM_MST_APPR), headerData.get(0).getCompany_cd().trim());
//
//		System.out.println(headerData);
//		System.out.println(detailData);
//		System.out.println(docsData);
//
//		System.out.println(" trans type::::::::::::" + tranType);
//
//		EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");
//
//		String subject = "New Scheme :" + headerData.get(0).getTrd_scheme_name().trim();
//		if (isValidityExtension) {
//			if (apprAction) {
//				if (status) {
//					subject = "Approved Scheme Validity Extension  :" + headerData.get(0).getTrd_scheme_name().trim();
//				} else {
//					subject = "Discard Scheme Validity Extension  :" + headerData.get(0).getTrd_scheme_name().trim();
//				}
//			} else {
//				subject = "Validity Extended for Scheme : " + headerData.get(0).getTrd_scheme_name().trim();
//			}
//
//		} else {
//			if (apprAction) {
//				if (status) {
//					subject = "Scheme Approved :" + headerData.get(0).getTrd_scheme_name().trim();
//				} else {
//					subject = "Scheme Discarded :" + headerData.get(0).getTrd_scheme_name().trim();
//				}
//
//			} else {
//				subject = "New Scheme :" + headerData.get(0).getTrd_scheme_name().trim();
//			}
//		}
//
//		Map<String, String> mapInlineImages = new HashMap<>();
//		int i = 0;
//		StringBuffer body = this.getArticleDataBodyForMail(hdr_sl_no, headerData.get(0), discardLink, link,
//				isValidityExtension, artSchVldExt, false, apprAction, Approver_name, status);
//		if (isValidityExtension) {
//			if (artSchVldExt != null)
//				mapInlineImages.put("Attachment" + 1, artSchVldExt.getFile_path().trim());
//		} else {
//			if (docsData.size() > 0) {
//				List<String> filesPath = new ArrayList<>();
//				for (ArticleDocsBean bean : docsData) {
//
//					filesPath.add(bean.getFileName().trim());
//
//				}
//				String zipFileName = this.downloadAsZipFile(filesPath);
//				String zipFilePath = "D://sg//ZipFiles//" + zipFileName;
//				mapInlineImages.put("Attachment" + 1, zipFilePath.trim());
//			}
//		}
//
//		System.out.println(headerData);
//
//		// to get to list
//		List<MailBean> list = taskMasterService.getMailSendDetails(
//				(isValidityExtension ? artSchVldExt.getSlno() : headerData.get(0).getTrd_sch_slno()), null, tranType);
//
//		List<String> cc = new ArrayList<>();
//
//		sendMail.sendHtmlMailWithCCAndFile(tolist, subject, body.toString(), mapInlineImages, mailconfig.getEmail(),
//				mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(),
//				mailconfig.getPort(), cc);
//	}

	@Override
	public void send_Mail_for_cfa_location(String hdr_sl_no, String link, String discardLink, List<String> tolist,
			boolean isValidityExtension, Art_Sch_Vld_Ext artSchVldExt, Boolean apprAction, String Approver_name,
			boolean status) throws Exception {

		List<ArticleDocsBean> docsData = new ArrayList<>();
		// getting header data
		List<trd_scheme_mst_hdr> headerData = this.article_Scheme_master_Repository.get_HDR_Data_For_Edit(hdr_sl_no);

		// getting detail data
		List<trd_scheme_mst_dtl> detailData = this.article_Scheme_master_Repository.get_Detail_Data_For_Edit(hdr_sl_no);

		// getting files data

		if (isValidityExtension) {
			docsData = this.article_Scheme_master_Repository
					.get_Docs_Details_For_EXT(artSchVldExt.getSlno().toString());
		} else {
			docsData = this.article_Scheme_master_Repository.get_Docs_Details(hdr_sl_no);
		}

		String tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(0L,
				(isValidityExtension ? SCM_MST_VLD_EXT : SCM_MST_APPR), headerData.get(0).getCompany_cd().trim());

		System.out.println(headerData);
		System.out.println(detailData);
		System.out.println(docsData);

		System.out.println(" trans type::::::::::::" + tranType);

		EmailFrom mailconfig = emailrepository.getEmailObject("EMAILFROM");

		String subject = "New Scheme :" + headerData.get(0).getTrd_scheme_name().trim();
		if (isValidityExtension) {
			if (apprAction) {
				if (status) {
					subject = "Approved Scheme Validity Extension  :" + headerData.get(0).getTrd_scheme_name().trim();
				} else {
					subject = "Discard Scheme Validity Extension  :" + headerData.get(0).getTrd_scheme_name().trim();
				}
			} else {
				subject = "Validity Extended for Scheme : " + headerData.get(0).getTrd_scheme_name().trim();
			}

		} else {
			if (apprAction) {
				if (status) {
					subject = "Scheme Approved :" + headerData.get(0).getTrd_scheme_name().trim();
				} else {
					subject = "Scheme Discarded :" + headerData.get(0).getTrd_scheme_name().trim();
				}

			} else {
				subject = "New Scheme :" + headerData.get(0).getTrd_scheme_name().trim();
			}
		}

		Map<String, String> mapInlineImages = new HashMap<>();
		int i = 0;
		StringBuffer body = this.getArticleDataBodyForMail(hdr_sl_no, headerData.get(0), discardLink, link,
				isValidityExtension, artSchVldExt, false, apprAction, Approver_name, status);
		if (isValidityExtension) {
			int j = 1;
			if (artSchVldExt != null)

				for (ArticleDocsBean bean : docsData) {
					mapInlineImages.put("Attachment" + j, bean.getFilePath().trim());
					j++;
				}

		} else {
			if (docsData.size() > 0) {
				List<String> filesPath = new ArrayList<>();
				for (ArticleDocsBean bean : docsData) {

					filesPath.add(bean.getFileName().trim());

				}
				String zipFileName = this.downloadAsZipFile(filesPath);
				String zipFilePath = "D://sg//ZipFiles//" + zipFileName;
				mapInlineImages.put("Attachment" + 1, zipFilePath.trim());
			}
		}

		System.out.println(headerData);

		// to get to list
		List<MailBean> list = taskMasterService.getMailSendDetails(
				(isValidityExtension ? artSchVldExt.getSlno() : headerData.get(0).getTrd_sch_slno()), null, tranType);

		List<String> cc = new ArrayList<>();

		sendMail.sendHtmlMailWithCCAndFile(tolist, subject, body.toString(), mapInlineImages, mailconfig.getEmail(),
				mailconfig.getMail_password(), mailconfig.isAuth(), mailconfig.isTls_sls(), mailconfig.getHost(),
				mailconfig.getPort(), cc);
	}

//	private StringBuffer getArticleDataBodyForMail(String hdr_sl_no,trd_scheme_mst_hdr hdr, String discardLink, String link) throws Exception {
//		final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
//		
//		
//
//		DecimalFormat df = new DecimalFormat("#.##");
//
//		List<SchemeDetailDataBean> beans = this.article_Scheme_master_Repository
//				.getTrdSchemeDetailById(Long.valueOf(hdr_sl_no));
//
//		SchemeDetailDataBean header = beans.get(0);
//		
//		System.err.println(header);
//
//		StringBuffer body = new StringBuffer();
//		body.append("<html><body>");
//
//		body.append("Scheme Name : ");
//		body.append("<b>");
//		body.append(header.getSCHEME_NAME());
//		body.append("</b>");
//
//		body.append(" Valid From :");
//		body.append("<b>");
//		body.append(header.getValidFromDateString());
//		body.append("</b>");
//
//		body.append(" Valid To : ");
//		body.append("<b>");
//		body.append(header.getValidToDateString());
//		body.append("</b>");
//		body.append("<br>");
//
//		body.append("Scheme Code : ");
//		body.append("<b>");
//		body.append(header.getTRD_SCHEME_CODE());
//		body.append("</b>");
//		
//		
//		body.append(" and Apply Invoice From : ");
//
//		body.append("<b>");
//		body.append(header.getApply_inv_fromstring());
//		body.append("</b>");
//		
//		body.append("<table style=\"font-family: arial, sans-serif;\r\n" + "  border-collapse: collapse;\r\n"
//				+ "  width: 100%;\">");
//
//		body.append("  \r\n" + "  <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n"
//				+ "  padding: 8px;\">");
//
//		body.append(
//				" <th style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">ARTICLE PRODUCT NAME</th>");
//
//		body.append("  <th  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">PRODUCT CODE</th>");
//		body.append("	<th  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">	APPLY TO</th>");
//		
//		// condition
//		
//		
//		if(header.getAPPLY_TO().equals("V") ) {
//			
//			body.append(
//					" 	<th style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">SALES BILLED VALUE</th>");
//			body.append(
//					" 	 <th  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">ARTICLE QUANTITY</th>");
//		}
//		
//
//		
//		
//		
//		body.append(" <th  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">ARTICLE RATE</th>");
//		body.append("  </tr>");
//
//		// loops start here for header data
//
//		body.append(" <tr>");
//		body.append("	<td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//		body.append(header.getSMP_PROD_NAME() + "</td>");
//
//		body.append("	<td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//		body.append(header.getARTICLE_PROD_CD() + "</td>");
//
//		body.append("	<td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//		body.append((header.getAPPLY_TO().equals("Q") ? "QUANTITY" : "VALUE") + "</td>");
//
//		String formattedBilled = df.format(header.getBILLED_VALUE());
//		String formattedArticleQty = df.format(header.getARTICLE_QTY_PER_TOT_VAL());
//		String formattedArticleRate = df.format(header.getARTICLE_PROD_RATE());
//		
////        String formattedValue = String.format("%.2f", value);
//
//		
//		// condition
//        
//        
//		if(header.getAPPLY_TO().equals("V") ) {
//			
//			body.append("	<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");
//			body.append(String.format("%.2f", header.getBILLED_VALUE())+"</td>");
//			body.append("<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");
//			body.append( header.getARTICLE_QTY_PER_TOT_VAL()+"</td>");
//		}
//        
//		body.append("<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");
//		body.append(String.format("%.2f", header.getARTICLE_PROD_RATE())+"</td>");
//
//		body.append("  </tr>");
//
//		body.append("	</table>");
//
//		body.append("<br>");
//		
//		
//		///  detail table
//
//		body.append("<table style=\"font-family: arial, sans-serif;\r\n" + "  border-collapse: collapse;\r\n"
//				+ "  width: 100%; margin-top:.5rem;\">\r\n" + "  ");
//
//		body.append(" <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n" + "  padding: 8px;\">");
//
//		body.append(" <th style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">SALES PRODUCT NAME </th>");
//		body.append(
//				"  <th  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">SALES PRODUCT CODE </th>");
//		
//
//		SchemeDetailDataBean deatail_bean=beans.get(0);
//		
//		
//		if(deatail_bean.getAPPLY_TO().equals("Q")) {
//			body.append("<th  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">	SALES BILLED QTY</th>");
//			body.append(" <th style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">SALES FREE QTY </th>");
//			body.append("  <th  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">	ARTICLE QTY</th>");
//			
//		}
//		
//		
//		body.append("  </tr>");
//
//		for (SchemeDetailDataBean bean : beans) {
//			body.append("  <tr>");
//			body.append(" <td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//			body.append(bean.getSALE_PROD_NAME() + "</td>");
//
//			body.append(" <td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//			body.append(bean.getSALE_PROD_CODE_ERP() + "</td>");
//
//			String formattedsalesBilled = df.format(bean.getPER_SALE_QTY_BILLED());
//			String formattedsalesFree = df.format(bean.getPER_SALE_QTY_FREE());
//			String formattedsalesArticelQty = df.format(bean.getARTICLE_QTY());
//			System.out.println(formattedsalesBilled);
//
//			
//			if(bean.getAPPLY_TO().equals("Q")) {
//				
//				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
//				body.append(  bean.getPER_SALE_QTY_BILLED() + "</td>");
//				
//				
//				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
//				body.append(  bean.getPER_SALE_QTY_FREE() + "</td>");
//				
//				
//				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
//				body.append(  bean.getARTICLE_QTY() + "</td>");
//			}
//			
//	
//
//			body.append("  </tr>");
//
//		}
//		body.append("	</table>");
//
//		body.append("<br>");
//		
//		
//		body.append("<div style='margin:10px'>Actions : "+hdr.getTrd_sch_status());
//		body.append("</div>");
//		
//		 if (hdr.getTrd_sch_status().equals("F")) {
//			 
//				body.append("<div style='margin:10px'>Actions : ");
//				body.append("<button style='margin-right:7px' type='button'><a href=http://localhost:8100/stk-cfa"+link+">Approve</a></button>");
//				body.append("<button style='margin-left:7px;' type='button'><a href=http://localhost:8100/stk-cfa"+discardLink+">Discard</a></button>");
//				body.append("</div>");
//		 }
//		 
//		 
//		body.append("\n\n This is a system generated mail. Please do not reply.");
//		body.append("</body></html>");
//
//
//		return body;
//
//	}
//
//	private StringBuffer getArticleDataBodyForMail(String hdr_sl_no, trd_scheme_mst_hdr hdr, String discardLink,
//			String link, boolean isValidityExtension, Art_Sch_Vld_Ext artSchVldExt, boolean isFinal,
//			Boolean isApprAction, String approvername, boolean status) throws Exception {
//		final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		DecimalFormat df = new DecimalFormat("#.##");
//
//		List<SchemeDetailDataBean> beans = this.article_Scheme_master_Repository.getTrdSchemeDetailById(
//				(isValidityExtension ? artSchVldExt.getScheme_slno() : Long.valueOf(hdr_sl_no)));
//
//		SchemeDetailDataBean header = beans.get(0);
//
//		System.err.println(header);
//
//		String tranType = "";
//		String schemeDivId = "";
//		if (!isValidityExtension) {
//			tranType = "32";
//			schemeDivId = header.getSCHEME_DIV_ID();
//		} else {
//			tranType = "300";
//			schemeDivId = artSchVldExt.getScheme_div_id().toString();
//		}
//
//		List<Articele_Scheme_Approved_Data> sortedArrayList = this.getApprovedData(schemeDivId, tranType,
//				(isValidityExtension ? artSchVldExt.getSlno() : header.getTRD_SCH_SLNO()));
//
////		List<Articele_Scheme_Approved_Data> sortedArrayList = new ArrayList<>();
////		System.err.println(list_of_approved_data.size());
////
////		int skip = 0;
////		if (list_of_approved_data.size() % 3 == 0) {
////			// 6
////			skip = 3;
////
////		}
////
////		if (list_of_approved_data.size() + 1 % 4 == 0) {
////			skip = 4;
////		}
////
////		if (list_of_approved_data.size() + 1 % 5 == 0) {
////			skip = 5;
////		}
////
////		int i = 1;
////
////		while (list_of_approved_data.size() > i) {
////
////			if (i == 1) {
////				sortedArrayList.add(list_of_approved_data.get(0));
////			}
////			i = i + skip;
////
////			if (list_of_approved_data.size() > i) {
////				sortedArrayList.add(list_of_approved_data.get(i));
////			}
////
////		}
////		System.err.println("sortedarray::::" + sortedArrayList);
//
//		StringBuffer body = new StringBuffer();
//		body.append("<html><body>");
//
//		if (isApprAction) {
//			if (status) {
//				body.append(" Approved");
//				body.append((isValidityExtension ? "Validity Extension" : "Scheme"));
//				body.append(" Name");
//			} else {
//				body.append(" Discarded");
//				body.append((isValidityExtension ? "Validity Extension" : "Scheme"));
//				body.append(" Name");
//			}
//
//		} else {
//			body.append(" Scheme Name : ");
//		}
//
//		body.append("<b>");
//		body.append(header.getSCHEME_NAME());
//		body.append("</b>");
//
//		body.append("   ");
//		body.append(" &nbsp; &nbsp; &nbsp; Scheme Code : ");
//		body.append("<b>");
//		body.append(header.getTRD_SCHEME_CODE());
//		body.append("</b>");
//
//		if (isApprAction) {
//			body.append("<br>");
//
//			body.append((status ? " Approved By :" : " Discarded By :"));
//
//			body.append("<b>");
//			body.append(approvername);
//			body.append("</b>");
//
//		} else {
//
//			body.append("<br>");
//
//			body.append("Apply Invoice From : ");
//			body.append("<b>");
//			body.append(header.getApply_inv_fromstring());
//			body.append("</b>");
//
//			body.append("   &nbsp; &nbsp;and  &nbsp;  Valid From :");
//			body.append("<b>");
//			body.append(header.getValidFromDateString());
//			body.append("</b>");
//
//			body.append(" &nbsp; &nbsp;    Valid To : ");
//			body.append("<b>");
//			body.append(header.getValidToDateString());
//			body.append("</b>");
//
//			if (isValidityExtension) {
//				body.append("<span style=\"color:red\"> &nbsp; &nbsp;    Validity Extended To : ");
//				body.append("<b>");
//				body.append(sdf.format(artSchVldExt.getValidity_ext_dt()));
//				body.append("</span></b>");
//			}
//
//			body.append("<br>");
//
//			body.append(" Scheme Type : ");
//
//			body.append("<b>");
//			body.append((header.getAPPLY_TO().equals("Q") ? "Quantity" : "Value") + "</td>");
//			body.append("</b>");
//
//			body.append("<table style=\"font-family: arial, sans-serif;\r\n" + "  border-collapse: collapse;\r\n"
//					+ "  width: 100%;\">");
//
//			body.append("  \r\n" + "  <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n"
//					+ "  padding: 8px;\">");
//
//			body.append(
//					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px ;background: #0093d0;color: #ffffff;   text-align: center;' colspan='5' >Article Product </th>");
//
////		 style='border: 1px solid #dddddd;padding: 8px; background: #163e87;color: #ffffff;   text-align: center;' 
//
//			body.append("  </tr>");
//
//			body.append("  \r\n" + "  <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n"
//					+ "  padding: 8px;\">");
//			body.append(
//					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:40%;'>Product Name</th>");
//
//			body.append(
//					"  <th  style=\'border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%; '\">Product Code</th>");
//
//			body.append(
//					" 	 <th  style=\'border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%; '\">Scheme Value</th>");
//			body.append(
//					"  <th  style=\'border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%; '\"> Quantity</th>");
//
//			body.append(
//					"  <th  style=\'border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%; '\"> Rate</th>");
//			body.append("  </tr>");
//
//			// loops start here for header data
//
//			body.append(" <tr>");
//			body.append("	<td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//			body.append(header.getARTICLE_PROD_NAME() + "</td>");
//
//			body.append("	<td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//			body.append(header.getARTICLE_PROD_CD() + "</td>");
//
//			String formattedBilled = df.format(header.getBILLED_VALUE());
//			String formattedArticleQty = df.format(header.getARTICLE_QTY_PER_TOT_VAL());
//			String formattedArticleRate = df.format(header.getARTICLE_PROD_RATE());
//
////        String formattedValue = String.format("%.2f", value);
//
//			// condition
//
////		if(header.getAPPLY_TO().equals("V") ) {
//
//			body.append("	<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");
//
//			body.append((header.getAPPLY_TO().equals("Q")) ? NA
//					: String.format("%.2f", header.getBILLED_VALUE()) + "</td>");
//			body.append("<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");
//			body.append((header.getAPPLY_TO().equals("Q")) ? NA : header.getARTICLE_QTY_PER_TOT_VAL() + "</td>");
////		}
//
//			body.append("<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");
//			body.append(String.format("%.2f", header.getARTICLE_PROD_RATE()) + "</td>");
//
//			body.append("  </tr>");
//
//			body.append("	</table>");
//
//			body.append("<br>");
//
//			/// detail table
//
//			body.append("<table style=\"font-family: arial, sans-serif;\r\n" + "  border-collapse: collapse;\r\n"
//					+ "  width: 100%; margin-top:.5rem;\">\r\n" + "  ");
//
//			body.append("  \r\n" + "  <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n"
//					+ "  padding: 8px;\">");
//
//			body.append(
//					" <th style='border: 1px solid #dddddd;padding: 8px; background: #163e87;color: #ffffff;   text-align: center;' colspan='5' >Sales Product </th>");
//
//			body.append("  </tr>");
//
//			body.append("  \r\n" + "  <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n"
//					+ "  padding: 8px;\">");
//
//			body.append(
//					" <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n" + "  padding: 8px;\">");
//
//			body.append(
//					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:40%;'> Product Name </th>");
//			body.append(
//					"  <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%;'> Product Code </th>");
//
//			SchemeDetailDataBean deatail_bean = beans.get(0);
//
////		if(deatail_bean.getAPPLY_TO().equals("Q")) {
//			body.append(
//					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%;'>Billed Qty</th>");
//			body.append(
//					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%;'>Free Qty </th>");
//			body.append(
//					"<th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%;'>Article Qty</th>");
//
////		}
//
//			body.append("  </tr>");
//
//			for (SchemeDetailDataBean bean : beans) {
//				body.append("  <tr>");
//				body.append(" <td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//				body.append(bean.getSALE_PROD_NAME() + "</td>");
//
//				body.append(" <td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
//				body.append(bean.getSALE_PROD_CODE_ERP() + "</td>");
//
//				String formattedsalesBilled = df.format(bean.getPER_SALE_QTY_BILLED());
//				String formattedsalesFree = df.format(bean.getPER_SALE_QTY_FREE());
//				String formattedsalesArticelQty = df.format(bean.getARTICLE_QTY());
//				System.out.println(formattedsalesBilled);
//
////			if(bean.getAPPLY_TO().equals("Q")) {
//
//				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
//				body.append((bean.getAPPLY_TO().equals("Q")) ? bean.getPER_SALE_QTY_BILLED() : "NA</td>");
//
//				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
//				body.append((bean.getAPPLY_TO().equals("Q")) ? bean.getPER_SALE_QTY_FREE() : "NA</td>");
//
//				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
//				body.append((bean.getAPPLY_TO().equals("Q")) ? bean.getARTICLE_QTY() : "NA</td>");
////			}
//
//				body.append("  </tr>");
//
//			}
//			body.append("	</table>");
//
//			body.append("<br>");
//
//			if (isValidityExtension) {
//				body.append("<span style='color:red'>");
//				body.append(" Remarks :");
//				body.append("<b>");
//				body.append(artSchVldExt.getRemarks());
//				body.append("</span></b>");
//			} else {
//				body.append(" Remarks :");
//				body.append("<b>");
//				body.append(header.getRemarks());
//				body.append("</b>");
//			}
//			body.append("<br>");
//
//			if (isValidityExtension) {
//				if (artSchVldExt.getExt_appr_status().equalsIgnoreCase("F")) {
//
//					if (sortedArrayList != null && sortedArrayList.size() > 0) {
//						body.append(
//								" <table style='font-family: arial, sans-serif;   border-collapse: collapse; width: 100%;   border: 1px solid #dddddd;'> ");
//
//						body.append(
//								"  <tr style='background:#0a7247  ; border: 1px solid #dddddd;text-align: left; padding: 8px; color:white;   border: 1px solid #dddddd '> ");
//						body.append(
//								" <th style=' padding: 8px;'>Approver Name</th><th>Date & Time</th> <th>Approver Level</th> </tr>");
////						for (Articele_Scheme_Approved_Data l : sortedArrayList) {
////							body.append(" <tr> <td>" + l.getAPPR_NM() + "</td> <td>" + l.getNEW_MOD_DATE() + "</td> <td>"
////									+ l.getAPPR_LEVEL() + "</td>  </tr>");
////						}
//						for (int m = sortedArrayList.size() - 1; m >= 0; m--) {
//							body.append(" <tr> <td>" + sortedArrayList.get(m).getAPPR_NM() + "</td> <td>"
//									+ this.getdmyHmszFormat(sortedArrayList.get(m).getNEW_MOD_DATE()) + "</td> <td>"
//									+ sortedArrayList.get(m).getAPPR_LEVEL() + "</td>  </tr>");
//						}
//
//						body.append("	</table>");
//					}
//
//					body.append("<div style='margin:10px 0px'>Actions : ");
//					body.append("<button type='button'> <a href=" + approvalLink + link + ">Approve</a></button>");
//
//					body.append(
//							"<button type='button'><a href=" + approvalLink + discardLink + ">Discard</a></button>");
//					body.append("</div>");
//				}
//			} else if (hdr.getTrd_sch_status().equals("F")) {
//
//				if (sortedArrayList != null && sortedArrayList.size() > 0) {
//
////					
////					body.append(
////							" <table style='font-family: arial, sans-serif;border-collapse: collapse; width: 100%;'>   <tr>  <th>Approver Name</th> <th>Date & Time</th> <th>Approver Level </th>  </tr> ");
////					
////					
//					body.append(
//							" <table style='font-family: arial, sans-serif;   border-collapse: collapse; width: 100%;   border: 1px solid #dddddd;'> ");
//
//					body.append(
//							"  <tr style='background:#0a7247  ; border: 1px solid #dddddd;text-align: left; padding: 8px; color:white;   border: 1px solid #dddddd '> ");
//					body.append(
//							" <th style=' padding: 8px;'>Approver Name</th><th>Date & Time</th> <th>Approver Level</th> </tr>");
//
////					for (Articele_Scheme_Approved_Data l : sortedArrayList) {
////						body.append(" <tr> <td style=' padding: 8px; '>" + l.getAPPR_NM() + "</td> <td>"
////								+ l.getNEW_MOD_DATE() + "</td> <td>" + l.getAPPR_LEVEL() + "</td>  </tr>");
////					}
//					
//					for (int m = sortedArrayList.size() - 1; m >= 0; m--) {
//						body.append(" <tr> <td>" + sortedArrayList.get(m).getAPPR_NM() + "</td> <td>"
//								+ this.getdmyHmszFormat(sortedArrayList.get(m).getNEW_MOD_DATE()) + "</td> <td>"
//								+ sortedArrayList.get(m).getAPPR_LEVEL() + "</td>  </tr>");
//					}
//					
//					body.append("	</table>");
//				}
//
//				body.append("<div style='margin:10px 0px'>Actions : ");
//				body.append("<button type='button'> <a href=" + approvalLink + link + ">Approve</a></button>");
//
//				body.append("<button type='button'><a href=" + approvalLink + discardLink + ">Discard</a></button>");
//				body.append("</div>");
//			}
//
//		}
//		body.append("<br>");
//		body.append("\n\n This is a system generated mail. Please do not reply.");
//		body.append("</body></html>");
//
//		return body;
//
//	}

	private StringBuffer getArticleDataBodyForMail(String hdr_sl_no, trd_scheme_mst_hdr hdr, String discardLink,
			String link, boolean isValidityExtension, Art_Sch_Vld_Ext artSchVldExt, boolean isFinal,
			Boolean isApprAction, String approvername, boolean status) throws Exception {
		final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat df = new DecimalFormat("#.##");

		List<SchemeDetailDataBean> beans = this.article_Scheme_master_Repository.getTrdSchemeDetailById(
				(isValidityExtension ? artSchVldExt.getScheme_slno() : Long.valueOf(hdr_sl_no)));

		SchemeDetailDataBean header = beans.get(0);
		String creator_Name = this.article_Scheme_master_Repository
				.getEmployeeDetailsByEmployeeID(header.getCreatedBy());

		System.err.println(header);

		String tranType = "";
		String schemeDivId = "";
		if (!isValidityExtension) {
			tranType = "32";
			schemeDivId = header.getSCHEME_DIV_ID();
		} else {
			tranType = "300";
			schemeDivId = artSchVldExt.getScheme_div_id().toString();
		}

		List<Articele_Scheme_Approved_Data> sortedArrayList = this.getApprovedData(schemeDivId, tranType,
				(isValidityExtension ? artSchVldExt.getSlno() : header.getTRD_SCH_SLNO()));

//		List<Articele_Scheme_Approved_Data> sortedArrayList = new ArrayList<>();
//		System.err.println(list_of_approved_data.size());
//
//		int skip = 0;
//		if (list_of_approved_data.size() % 3 == 0) {
//			// 6
//			skip = 3;
//
//		}
//
//		if (list_of_approved_data.size() + 1 % 4 == 0) {
//			skip = 4;
//		}
//
//		if (list_of_approved_data.size() + 1 % 5 == 0) {
//			skip = 5;
//		}
//
//		int i = 1;
//
//		while (list_of_approved_data.size() > i) {
//
//			if (i == 1) {
//				sortedArrayList.add(list_of_approved_data.get(0));
//			}
//			i = i + skip;
//
//			if (list_of_approved_data.size() > i) {
//				sortedArrayList.add(list_of_approved_data.get(i));
//			}
//
//		}
//		System.err.println("sortedarray::::" + sortedArrayList);

		StringBuffer body = new StringBuffer();
		body.append("<html><body>");

		if (isApprAction) {
			if (status) {
				body.append(" Approved");
				body.append((isValidityExtension ? "Validity Extension" : "Scheme"));
				body.append(" Name");
			} else {
				body.append(" Discarded");
				body.append((isValidityExtension ? "Validity Extension" : "Scheme"));
				body.append(" Name");
			}

		} else {
			body.append(" Scheme Name : ");
		}

		body.append("<b>");
		body.append(header.getSCHEME_NAME());
		body.append("</b>");

		body.append("   ");
		body.append(" &nbsp; &nbsp; &nbsp; Scheme Code : ");
		body.append("<b>");
		body.append(header.getTRD_SCHEME_CODE());
		body.append("</b>");

		if (isApprAction) {
			body.append("<br>");

			body.append((status ? " Approved By :" : " Discarded By :"));

			body.append("<b>");
			body.append(approvername);
			body.append("</b>");

		} else {

			body.append("<br>");

			body.append("Apply Invoice From : ");
			body.append("<b>");
			body.append(header.getApply_inv_fromstring());
			body.append("</b>");

			body.append("   &nbsp; &nbsp;and  &nbsp;  Valid From :");
			body.append("<b>");
			body.append(header.getValidFromDateString());
			body.append("</b>");

			body.append(" &nbsp; &nbsp;    Valid To : ");
			body.append("<b>");
			body.append(header.getValidToDateString());
			body.append("</b>");

			if (isValidityExtension) {
				body.append("<span style=\"color:red\"> &nbsp; &nbsp;    Validity Extended To : ");
				body.append("<b>");
				body.append(sdf.format(artSchVldExt.getValidity_ext_dt()));
				body.append("</span></b>");
			}

			body.append("<br>");

			body.append(" Scheme Type : ");

			body.append("<b>");
			body.append((header.getAPPLY_TO().equals("Q") ? "Quantity" : "Value") + "</td>");
			body.append("</b>");

			body.append("<table style=\"font-family: arial, sans-serif;\r\n" + "  border-collapse: collapse;\r\n"
					+ "  width: 100%;\">");

			body.append("  \r\n" + "  <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n"
					+ "  padding: 8px;\">");

			body.append(
					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px ;background: #0093d0;color: #ffffff;   text-align: center;' colspan='5' >Article Product </th>");

//		 style='border: 1px solid #dddddd;padding: 8px; background: #163e87;color: #ffffff;   text-align: center;' 

			body.append("  </tr>");

			body.append("  \r\n" + "  <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n"
					+ "  padding: 8px;\">");
			body.append(
					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:40%;'>Product Name</th>");

			body.append(
					"  <th  style=\'border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%; '\">Product Code</th>");

			body.append(
					" 	 <th  style=\'border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%; '\">Scheme Value</th>");
			body.append(
					"  <th  style=\'border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%; '\"> Quantity</th>");

			body.append(
					"  <th  style=\'border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%; '\"> Rate</th>");
			body.append("  </tr>");

			// loops start here for header data

			body.append(" <tr>");
			body.append("	<td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
			body.append(header.getARTICLE_PROD_NAME() + "</td>");

			body.append("	<td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
			body.append(header.getARTICLE_PROD_CD() + "</td>");

			String formattedBilled = df.format(header.getBILLED_VALUE());
			String formattedArticleQty = df.format(header.getARTICLE_QTY_PER_TOT_VAL());
			String formattedArticleRate = df.format(header.getARTICLE_PROD_RATE());

//        String formattedValue = String.format("%.2f", value);

			// condition

//		if(header.getAPPLY_TO().equals("V") ) {

			body.append("	<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");

			body.append((header.getAPPLY_TO().equals("Q")) ? NA
					: String.format("%.2f", header.getBILLED_VALUE()) + "</td>");
			body.append("<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");
			body.append((header.getAPPLY_TO().equals("Q")) ? NA : header.getARTICLE_QTY_PER_TOT_VAL() + "</td>");
//		}

			body.append("<td  style=\"border: 1px solid #dddddd;text-align: right;padding: 8px;\">");
			body.append(String.format("%.2f", header.getARTICLE_PROD_RATE()) + "</td>");

			body.append("  </tr>");

			body.append("	</table>");

			body.append("<br>");

			/// detail table

			body.append("<table style=\"font-family: arial, sans-serif;\r\n" + "  border-collapse: collapse;\r\n"
					+ "  width: 100%;\">\r\n" + "  ");

			body.append("  \r\n" + "  <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n"
					+ "  padding: 8px;\">");

			body.append(
					" <th style='border: 1px solid #dddddd;padding: 8px; background: #163e87;color: #ffffff;   text-align: center;' colspan='5' >Sales Product </th>");

			body.append("  </tr>");

			body.append(
					" <tr style=\"border: 1px solid #dddddd;\r\n" + "  text-align: left;\r\n" + "  padding: 8px;\">");

			body.append(
					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:40%;'> Product Name </th>");
			body.append(
					"  <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%;'> Product Code </th>");

			SchemeDetailDataBean deatail_bean = beans.get(0);

//		if(deatail_bean.getAPPLY_TO().equals("Q")) {
			body.append(
					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%;'>Billed Qty</th>");
			body.append(
					" <th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%;'>Free Qty </th>");
			body.append(
					"<th style='border: 1px solid #dddddd;text-align: left;padding: 8px;width:15%;'>Article Qty</th>");

//		}

			body.append("  </tr>");

			for (SchemeDetailDataBean bean : beans) {
				body.append("  <tr>");
				body.append(" <td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
				body.append(bean.getSALE_PROD_NAME() + "</td>");

				body.append(" <td  style=\"border: 1px solid #dddddd;text-align: left;padding: 8px;\">");
				body.append(bean.getSALE_PROD_CODE_ERP() + "</td>");

				String formattedsalesBilled = df.format(bean.getPER_SALE_QTY_BILLED());
				String formattedsalesFree = df.format(bean.getPER_SALE_QTY_FREE());
				String formattedsalesArticelQty = df.format(bean.getARTICLE_QTY());
				System.out.println(formattedsalesBilled);

//			if(bean.getAPPLY_TO().equals("Q")) {

				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
				body.append((bean.getAPPLY_TO().equals("Q")) ? bean.getPER_SALE_QTY_BILLED() : "NA</td>");

				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
				body.append((bean.getAPPLY_TO().equals("Q")) ? bean.getPER_SALE_QTY_FREE() : "NA</td>");

				body.append("	<td  style=\"border: 1px solid #dddddd;text-align:right;padding: 8px;\">");
				body.append((bean.getAPPLY_TO().equals("Q")) ? bean.getARTICLE_QTY() : "NA</td>");
//			}

				body.append("  </tr>");

			}
			body.append("	</table>");

			body.append("<br>");

			if (isValidityExtension) {
				body.append("<span style='color:red'>");
				body.append(" Remarks :");
				body.append("<b>");
				body.append(artSchVldExt.getRemarks());
				body.append("</span></b>");
			} else {
				body.append(" Remarks :");
				body.append("<b>");
				body.append(header.getRemarks());
				body.append("</b>");
			}
			body.append("<br>");

			if (isValidityExtension) {
				if (artSchVldExt.getExt_appr_status().equalsIgnoreCase("F")) {

					body.append("Created By : ");
					body.append("<b>" + creator_Name);

					body.append("<b>");

					body.append("<br>");

					if (sortedArrayList != null && sortedArrayList.size() > 0) {
						body.append(
								" <table style='font-family: arial, sans-serif;   border-collapse: collapse; width: 100%;   border: 1px solid #dddddd;'> ");

						body.append(
								"  <tr style='background:#0a7247  ; border: 1px solid #dddddd;text-align: left; padding: 8px; color:white;   border: 1px solid #dddddd '> ");
						body.append(
								" <th style=' padding: 8px;'>Approver Name</th><th>Date & Time</th> <th>Approver Level</th> </tr>");
//						for (Articele_Scheme_Approved_Data l : sortedArrayList) {
//							body.append(" <tr> <td>" + l.getAPPR_NM() + "</td> <td>" + l.getNEW_MOD_DATE() + "</td> <td>"
//									+ l.getAPPR_LEVEL() + "</td>  </tr>");
//						}
						for (int m = sortedArrayList.size() - 1; m >= 0; m--) {
							body.append(" <tr> <td>" + sortedArrayList.get(m).getAPPR_NM() + "</td> <td>"
									+ this.getdmyHmszFormat(sortedArrayList.get(m).getNEW_MOD_DATE()) + "</td> <td>"
									+ sortedArrayList.get(m).getAPPR_LEVEL() + "</td>  </tr>");
						}

						body.append("	</table>");
					}

					body.append("<div style='margin:10px 0px'>Actions : ");
					body.append("<button type='button'> <a href=" + approvalLink + link + ">Approve</a></button>");

					body.append(
							"<button type='button'><a href=" + approvalLink + discardLink + ">Discard</a></button>");
					body.append("</div>");
				}
			} else if (hdr.getTrd_sch_status().equals("F")) {

				body.append("Created By : ");
				body.append("<b>" + creator_Name);
				body.append("<b>");
				body.append("<br>");

				if (sortedArrayList != null && sortedArrayList.size() > 0) {

//					
//					body.append(
//							" <table style='font-family: arial, sans-serif;border-collapse: collapse; width: 100%;'>   <tr>  <th>Approver Name</th> <th>Date & Time</th> <th>Approver Level </th>  </tr> ");
//					
//					
					body.append(
							" <table style='font-family: arial, sans-serif;   border-collapse: collapse; width: 100%;   border: 1px solid #dddddd;'> ");

					body.append(
							"  <tr style='background:#0a7247  ; border: 1px solid #dddddd;text-align: left; padding: 8px; color:white;   border: 1px solid #dddddd '> ");
					body.append(
							" <th style=' padding: 8px;'>Approver Name</th><th>Date & Time</th> <th>Approver Level</th> </tr>");

//					for (Articele_Scheme_Approved_Data l : sortedArrayList) {
//						body.append(" <tr> <td style=' padding: 8px; '>" + l.getAPPR_NM() + "</td> <td>"
//								+ l.getNEW_MOD_DATE() + "</td> <td>" + l.getAPPR_LEVEL() + "</td>  </tr>");
//					}

					for (int m = sortedArrayList.size() - 1; m >= 0; m--) {
						body.append(" <tr> <td>" + sortedArrayList.get(m).getAPPR_NM() + "</td> <td>"
								+ this.getdmyHmszFormat(sortedArrayList.get(m).getNEW_MOD_DATE()) + "</td> <td>"
								+ sortedArrayList.get(m).getAPPR_LEVEL() + "</td>  </tr>");
					}

					body.append("	</table>");
				}

				body.append("<div style='margin:10px 0px'>Actions : ");
				body.append(
						"<a style='color: white;text-decoration: none;background: green; border-radius: 10%;border: 5px solid green;' href="
								+ approvalLink + link + " >Approve</a>");

				body.append(
						"  &nbsp; &nbsp; <a  style='color: white;background:red; text-decoration: none;  border-radius: 10%;border: 5px solid red; '  href="
								+ approvalLink + discardLink + ">Discard </a> &nbsp; &nbsp;  ");
				body.append("</div>");

//				body.append("<div style='margin:10px 0px'>Actions : ");
//				body.append(" <button   type='button'  > <a style='color: white;text-decoration: none;background: green; border-radius: 10%;border: 5px solid red;' href=" + approvalLink + link + " >Approve</a></button>");
//
//				body.append("  &nbsp; &nbsp; <button   type='button'><a   style='color: white;background:red; text-decoration: none;  border-radius: 10%;border: 5px solid red; '  href=" + approvalLink + discardLink + ">Discard</a></button>");
//				body.append("</div>");
//				

			}

		}
		body.append("<br>");
		body.append("\n\n This is a system generated mail. Please do not reply.");
		body.append("</body></html>");

		return body;

	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
	SimpleDateFormat req_fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SS");

	private String getdmyHmszFormat(Date newModDate) throws ParseException {
		return req_fmt.format(newModDate);
	}

	@Override
	public List<Artreq_To_Dispatch_Report_With_Param> getArticleSchemeReport(ArticleSchemeReportBean bean)
			throws Exception {

		return article_Scheme_master_Repository.getArticleSchemeReport(bean);
	}

//	@Override
//	public String generateArticleSchemeReport(List<Artreq_To_Dispatch_Report_With_Param> list,
//			ArticleSchemeReportBean bean, HttpSession session, List<Scheme_Desription_Bean> listOfSchemDescription)
//			throws Exception {
//
//		String filename = "article_scheme_request_and_delivery_report" + new Date().getTime() + ".xlsx";
//		String filepath = REPORT_FILE_PATH + filename;
//		System.out.println("file path :::" + filepath);
//		XSSFWorkbook workbook = null;
////		List<String> header = null;
//		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
//
//		try {
//			workbook = new XSSFWorkbook();
//			File f = new File(REPORT_FILE_PATH);
//			if (!f.exists()) {
//				if (f.mkdirs()) {
//				} else {
//					throw new RuntimeException("Could not create directory");
//				}
//			}
//			XSSFSheet sheet = workbook.createSheet("ARTICLE_SCHEME_REQUEST_AND_DELIVERY_REPORT");
//
//			List<String> headings = new ArrayList<>();
//			headings.add("Code");
//			headings.add("SKU Name");
//			headings.add("Billed Qty");
//			headings.add("Free Qty");
//			headings.add("Bill Value");
//			headings.add("Code");
//			headings.add("Article Name");
//			headings.add("Qty Req");
//			headings.add("Qty Supplied");
//			headings.add("Pending Qty");
//			headings.add("Challan No.");
//			headings.add("Challan Date");
//			headings.add("LR No.");
//			headings.add("LR Date");
//			headings.add("E-invoice No.");
//			headings.add("E-invoice Date");
//			headings.add("Eway Bill No.");
//			headings.add("Eway Bill Date");
//
//			XSSFRow row = null;
//			XSSFCell cell = null;
//			int rowCount = 0;
//			int colCount = 0;
//
//			Font font = workbook.createFont();
//			font.setBold(true);
//
//			XSSFCellStyle headingCellStyle = workbook.createCellStyle();
//			headingCellStyle.setAlignment(HorizontalAlignment.CENTER);
//
//			XSSFFont headerFont = workbook.createFont();
//			headerFont.setFontHeightInPoints((short) 14);
//			headerFont.setBold(true);
//			headingCellStyle.setFont(headerFont);
//
//			XSSFCellStyle columnStyle = ReportFormats.cellBold2(workbook);
//			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeadingBlue(workbook);
//			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignmentForNum(workbook, ALIGN_RIGHT);
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() + 6));
//			cell.setCellValue(companyname);
//			cell.setCellStyle(headingCellStyle);
//
//			rowCount += 1;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() + 6));
//			cell.setCellValue("Article Scheme Request and Delivery");
//			cell.setCellStyle(headingCellStyle);
//			rowCount += 1;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() + 6));
//			cell.setCellValue("Period:  " + bean.getStDt() + " To " + bean.getEnDt());
//			cell.setCellStyle(headingCellStyle);
//			rowCount += 1;
//
////			row = sheet.createRow(rowCount);
////			cell = row.createCell(colCount);
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() + 5));
////			cell.setCellValue("");
////			cell.setCellStyle(headingCellStyle);
////			rowCount += 1;
//
//			colCount = 0;
//
//			row = sheet.createRow(rowCount);
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Sl. No.");
//			cell.setCellStyle(columnStyle);
//			colCount += 1;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("SAP Invoice No.");
//			cell.setCellStyle(columnStyle);
//			colCount += 1;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Invoice Date");
//			cell.setCellStyle(columnStyle);
//
//			colCount += 1;
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Customer");
//			cell.setCellStyle(columnStyle);
//			colCount += 1;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("City");
//			cell.setCellStyle(columnStyle);
//			colCount += 1;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 4));
//			cell.setCellValue("Sales Product (SKU)");
//			cell.setCellStyle(columnStyle);
//			colCount += 5;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Article Scheme Name");
//			cell.setCellStyle(columnStyle);
//			colCount += 1;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
//			cell.setCellValue("Articles Requested");
//			cell.setCellStyle(columnStyle);
//			colCount += 3;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
//			cell.setCellValue("Articles Supplied");
//			cell.setCellStyle(columnStyle);
//			colCount += 2;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 7));
//			cell.setCellValue("Dispatch Details");
//			cell.setCellStyle(columnStyle);
//			colCount += 8;
//
//			System.out.println("::::::::::::" + colCount);
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Location Name");
//			cell.setCellStyle(columnStyle);
//			
//			
//			
//			
//			
//
//			rowCount += 1;
//			row = sheet.createRow(rowCount);
//			colCount = 5;
//
////			for (int i = 0; i < 5; i++) {
////				cell = row.createCell(colCount);
////				cell.setCellStyle(columnStyle);
////				colCount++;
////			}
//
//			for (String heading : headings) {
//
//				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
//
//				if (heading.equals("Bill Value")) {
//					colCount += 2;
//				} else {
//					colCount++;
//				}
//			}
//
//			sheet.groupRow(0, 2);
//			sheet.createFreezePane(0, 5);
//
//			rowCount += 1;
//
//			Long oldReq = 0l;
//			Long newReq = 0l;
//			String oldProdCd = null;
//			String newProdCd = null;
//			String oldInv = list.get(0).getInvoice_no();
//			String newInv = null;
//			int rowCountForCondition = 1;
//
//			for (Artreq_To_Dispatch_Report_With_Param obj : list) {
//
//				newReq = obj.getArticle_req_id();
//				newProdCd = obj.getArticle_prod_cd();
//				newInv = obj.getInvoice_no();
//
//				if ((bean.getPending_qty().equals("Y") && obj.getPending_qty().longValue() > 0)) {
//
//					if (!oldInv.equals(newInv)) {
//						for (int i = 1; i <= 23; i++) {
//
//							row = sheet.createRow(rowCount);
//							colCount = 0;
//							cell = ReportFormats.cell(row, colCount, "", null);
//							colCount++;
//						}
//						rowCount++;
//					}
//
//					row = sheet.createRow(rowCount);
//					colCount = 0;
//
//					cell = ReportFormats.cell(row, colCount, rowCountForCondition + "",
//							ReportFormats.setCellAlignment(workbook, ALIGN_CENTER));
//
//					rowCountForCondition++;
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getInvoice_no(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getInvoice_date(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getCust_name_shipto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDestination().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getSale_prod_code_sg().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getSale_prod_name().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount, obj.getSale_prod_qty_billed(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount, obj.getSale_prod_qty_free(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
//					cell = ReportFormats.cellNum(row, colCount, Double.valueOf(obj.getSale_billed_value().toString()),
//							cellAlignment);
//					colCount++;
//
//					if (oldReq.compareTo(newReq) == 0 && oldProdCd.equals(newProdCd)) {
//
//						cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
//						colCount++;
//
////						cell = ReportFormats.cell(row, colCount, "", null);
////						colCount++;
//						//
////						cell = ReportFormats.cell(row, colCount, "", null);
////						colCount++;
//						//
////						cell = ReportFormats.cell(row, colCount, "", null);
////						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//					} else {
//
//						cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_reqd(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_supplied().longValue(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getPending_qty().longValue(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//					}
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_challan_no(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_dt(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_lr_no(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_lr_dt(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getE_inv_no(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getE_inv_date(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getTrn_ewaybillno(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getTrn_ewaybilldt(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getLoc_nm(), null);
//					colCount++;
//
//					oldReq = newReq;
//					oldProdCd = newProdCd;
//					oldInv = newInv;
//
//					rowCount++;
//
//				} else if ((bean.getPending_qty().equals("N"))) {
//
//					if (!oldInv.equals(newInv)) {
//						for (int i = 1; i <= 23; i++) {
//
//							row = sheet.createRow(rowCount);
//							colCount = 0;
//							cell = ReportFormats.cell(row, colCount, "", null);
//							colCount++;
//						}
//						rowCount++;
//					}
//
//					row = sheet.createRow(rowCount);
//					colCount = 0;
//
//					cell = ReportFormats.cell(row, colCount, obj.getRow().toString(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_CENTER));
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getInvoice_no(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getInvoice_date(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getCust_name_shipto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDestination().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getSale_prod_code_sg().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getSale_prod_name().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount, obj.getSale_prod_qty_billed(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount, obj.getSale_prod_qty_free(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
//					cell = ReportFormats.cellNum(row, colCount, Double.valueOf(obj.getSale_billed_value().toString()),
//							cellAlignment);
//					colCount++;
//
//					if (oldReq.compareTo(newReq) == 0 && oldProdCd.equals(newProdCd)) {
//
//						cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//					} else {
//
//						cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_reqd(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_supplied().longValue(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getPending_qty().longValue(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//					}
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_challan_no(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_dt(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_lr_no(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_lr_dt(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getE_inv_no(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getE_inv_date(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getTrn_ewaybillno(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getTrn_ewaybilldt(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getLoc_nm(), null);
//					colCount++;
//
//					oldReq = newReq;
//					oldProdCd = newProdCd;
//					oldInv = newInv;
//
//					rowCount++;
//
//				}
//
//			}
//
//			List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
//			int count = 0;
//			for (CellRangeAddress rangeAddress : mergedRegions) {
////				System.out.println("rangeAddress :: " + rangeAddress.toString());
//
//				if (count > 3) {
//					RegionUtil.setBorderTop(BorderStyle.THIN, rangeAddress, sheet);
//					RegionUtil.setBorderLeft(BorderStyle.THIN, rangeAddress, sheet);
//					RegionUtil.setBorderRight(BorderStyle.THIN, rangeAddress, sheet);
//					RegionUtil.setBorderBottom(BorderStyle.THIN, rangeAddress, sheet);
//				}
//				count++;
//
//			}
//
//			/// Scheme description sheet starts here
//
//			XSSFSheet sheet2 = workbook.createSheet("SCHEME DESCRIPTION");
//
//			XSSFRow description_row = null;
//			XSSFCell description_cell = null;
//			int description_rowCount = 0;
//			int description_colCount = 0;
//
//			List<String> description_headings = new ArrayList<>();
//
//			// creating heading
//			description_headings.add("Sl No");
//			description_headings.add("Location Name");
//			description_headings.add("Sub Company Code");
//			description_headings.add("Scheme Code");
//			description_headings.add("Scheme Name");
//			description_headings.add("Valid From Date");
//			description_headings.add("Valid To Date");
//			description_headings.add("Article Product Code");
//			description_headings.add("Article Product Name");
//			description_headings.add("Sale Product Code");
//			description_headings.add("Sale Product Name");
//			description_headings.add("Scheme Apply To");
//
//			description_headings.add("Billed Value");
//			description_headings.add("Article Qty Per Total Value");
//			description_headings.add("Per Sale Qty Total");
//			description_headings.add("Article Qty");
//
//			description_row = sheet2.createRow(description_rowCount);
//			description_cell = description_row.createCell(description_colCount);
//
//			sheet2.addMergedRegion(
//					new CellRangeAddress(description_rowCount, description_rowCount, description_colCount, 15));
//			description_cell.setCellValue("SCHEME DESCRIPTION");
//			description_cell.setCellStyle(headingCellStyle);
//			description_rowCount += 1;
//
//			description_colCount = 0;
//
//			description_row = sheet2.createRow(description_rowCount);
//			for (String heading : description_headings) {
//
//				description_cell = description_row.createCell(description_colCount);
//
//				description_cell = ReportFormats.cell(description_row, description_colCount, heading,
//						columnHeadingStyle);
//				description_colCount++;
//			}
//
//			description_rowCount += 1;
//			description_colCount = 0;
//
//			String applay_To_Value_Pervious = "";
//
//			for (Scheme_Desription_Bean description_bean : listOfSchemDescription) {
//
//				description_row = sheet2.createRow(description_rowCount);
//				description_colCount = 0;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getRow().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getLoc_nm().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getSub_comp_cd().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getTrd_scheme_code().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getTrd_scheme_name().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getValid_from_dt().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getValid_to_dt().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getArticle_prod_cd().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getArticle_name().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getSale_prod_code_sg().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getSale_prod_name().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getApply_to().toString(), null);
//				description_colCount++;
//
//				// only showing one time value and article qty and
//				if (!applay_To_Value_Pervious.equals(description_bean.getBilled_value().toString())
//						&& "V".equals(description_bean.getApply_to())) {
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getBilled_value().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getArticle_qty_per_tot_val().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getPer_sale_qty_tot().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getArticle_qty().toString()),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
//				}
//
//				// if value and article qty same adding blank row
//				else if (applay_To_Value_Pervious.equals(description_bean.getBilled_value().toString())
//						&& "V".equals(description_bean.getApply_to())) {
//
//					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
//					description_colCount++;
//
//					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
//					description_colCount++;
//
//					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
//					description_colCount++;
//
//					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
//
//					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
//				}
//
//				// scheme apply to works normal
//				else if ("Q".equals(description_bean.getApply_to())) {
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getBilled_value().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getArticle_qty_per_tot_val().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getPer_sale_qty_tot().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getArticle_qty().toString()),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
//				}
//
//				description_rowCount++;
//
//			}
//
//			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
//			workbook.write(fileOutputStream);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (workbook != null) {
//				workbook.close();
//			}
//		}
//
//		return filename;
//	}
//
//
//	@Override
//	public String generateArticleSchemeReport(List<Artreq_To_Dispatch_Report_With_Param> list,
//			ArticleSchemeReportBean bean, HttpSession session, List<Scheme_Desription_Bean> listOfSchemDescription)
//			throws Exception {
//
//		String filename = "article_scheme_request_and_delivery_report" + new Date().getTime() + ".xlsx";
//		String filepath = REPORT_FILE_PATH + filename;
//		System.out.println("file path :::" + filepath);
//		XSSFWorkbook workbook = null;
////		List<String> header = null;
//		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
//
//		try {
//			workbook = new XSSFWorkbook();
//			File f = new File(REPORT_FILE_PATH);
//			if (!f.exists()) {
//				if (f.mkdirs()) {
//				} else {
//					throw new RuntimeException("Could not create directory");
//				}
//			}
//			XSSFSheet sheet = workbook.createSheet("ARTICLE_SCHEME_REQUEST_AND_DELIVERY_REPORT");
//
//			List<String> headings = new ArrayList<>();
//
//			headings.add("Sl. No.");
//			headings.add("Location Name");
//			headings.add("SAP Invoice No.");
//			headings.add("Invoice Date");
//			headings.add("Article Request No");
//			headings.add("Article Request Date");
//			headings.add("Ship To name");
//			headings.add("Ship To Code");
//			headings.add("Ship To City");
//			headings.add("Ship To State");
//			headings.add("Bill To name");
//			headings.add("Bill To Code");
//			headings.add("Bill To City");
//			headings.add("Code");
//			headings.add("SKU Name");
//			headings.add("Billed Qty");
//			headings.add("Bill Val(for Value Schemes) ");
//			headings.add("Article Scheme Name");
//			headings.add("Article Scheme Code");
//			headings.add("Code");
//			headings.add("Article Name");
//			headings.add("Qty Req");
//			headings.add("Qty Supplied");
//			headings.add("Pending Qty");
//			headings.add("Challan No.");
//			headings.add("Challan Date");
//			headings.add("LR No.");
//			headings.add("LR Date");
//			headings.add("E-invoice No.");
//			headings.add("E-invoice Date");
//			headings.add("Eway Bill No.");
//			headings.add("Eway Bill Date");
//
//			XSSFRow row = null;
//			XSSFCell cell = null;
//			int rowCount = 0;
//			int colCount = 0;
//
//			Font font = workbook.createFont();
//			font.setBold(true);
//
//			XSSFCellStyle headingCellStyle = workbook.createCellStyle();
//			headingCellStyle.setAlignment(HorizontalAlignment.LEFT);
//
//			XSSFFont headerFont = workbook.createFont();
//			headerFont.setFontHeightInPoints((short) 14);
//			headerFont.setBold(true);
//			headingCellStyle.setFont(headerFont);
//
//			XSSFCellStyle columnStyle = ReportFormats.cellBold2(workbook);
//			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeadingBlue(workbook);
//			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignmentForNum(workbook, ALIGN_RIGHT);
//
//			XSSFCellStyle dateStyle = workbook.createCellStyle();
//			CreationHelper creationHelper = workbook.getCreationHelper();
//			dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
////			cell.setCellValue(companyname);
//			cell.setCellValue("Pfizer India Ltd.");
//			cell.setCellStyle(headingCellStyle);
//
//			rowCount += 1;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
//			cell.setCellValue("Article Scheme Request and Delivery");
//			cell.setCellStyle(headingCellStyle);
//			rowCount += 1;
//
//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
//			cell.setCellValue("Period:  " + bean.getStDt() + " To " + bean.getEnDt());
//			cell.setCellStyle(headingCellStyle);
//			rowCount += 1;
//
////			row = sheet.createRow(rowCount);
////			cell = row.createCell(colCount);
////			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() + 5));
////			cell.setCellValue("");
////			cell.setCellStyle(headingCellStyle);
////			rowCount += 1;
//
//			colCount = 0;
//
//			row = sheet.createRow(rowCount);
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 10));
//			cell.setCellValue("");
//			cell.setCellStyle(columnStyle);
////			colCount += 10;		
//
//			for (int i = 1; i <= 11; i++) {
//				cell = ReportFormats.cell(row, i, "", columnStyle);
//				colCount++;
//			}
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
//			cell.setCellValue("Sales Product (SKU)");
//			cell.setCellStyle(columnStyle);
//			colCount += 4;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
//			cell.setCellValue("");
//			cell.setCellStyle(columnStyle);
//			colCount++;
//
//			cell = ReportFormats.cell(row, colCount, "", columnStyle);
//			colCount++;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
//			cell.setCellValue("Articles Requested");
//			cell.setCellStyle(columnStyle);
//			colCount += 3;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
//			cell.setCellValue("Articles Supplied");
//			cell.setCellStyle(columnStyle);
//			colCount += 2;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 7));
//			cell.setCellValue("Dispatch Details");
//			cell.setCellStyle(columnStyle);
//			colCount += 8;
//
//			System.out.println("::::::::::::" + colCount);
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
//			cell.setCellValue("");
//			cell.setCellStyle(columnStyle);
//
//			cell = ReportFormats.cell(row, colCount + 1, "", columnStyle);
//
//			rowCount += 1;
//			row = sheet.createRow(rowCount);
//			colCount = 0;
//
//			for (String heading : headings) {
//
//				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
//				colCount++;
//			}
//
//			sheet.groupRow(0, 2);
//			sheet.createFreezePane(0, 5);
//
//			rowCount += 1;
//
//			Long oldReq = 0l;
//			Long newReq = 0l;
//			String oldProdCd = null;
//			String newProdCd = null;
//			String oldInv = list.get(0).getInvoice_no();
//			String newInv = null;
//			int rowCountForCondition = 1;
//
//			for (Artreq_To_Dispatch_Report_With_Param obj : list) {
//
//				newReq = obj.getArticle_req_id();
//				newProdCd = obj.getArticle_prod_cd();
//				newInv = obj.getInvoice_no();
//
//				if ((bean.getPending_qty().equals("Y") && obj.getPending_qty().longValue() > 0)) {
//
////					if (!oldInv.equals(newInv)) {
////						for (int i = 1; i <= 23; i++) {
////
////							row = sheet.createRow(rowCount);
////							colCount = 0;
////							cell = ReportFormats.cell(row, colCount, "", null);
////							colCount++;
////						}
////						rowCount++;
////					}
//
//					row = sheet.createRow(rowCount);
//					colCount = 0;
//
//					cell = ReportFormats.cell(row, colCount, rowCountForCondition + "",
//							ReportFormats.setCellAlignment(workbook, ALIGN_CENTER));
//
//					rowCountForCondition++;
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getInvoice_no(), null);
//					colCount++;
//
//					if (obj.getInvoice_date().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getInvoice_date()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getRequest_no(), null);
//					colCount++;
//
//					if (obj.getArticle_req_date().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYMD(obj.getArticle_req_date()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getCust_name_shipto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getCust_code_shipto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDestination(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getCust_name_billto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getErp_cust_cd(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDestination_billto(), null);
//					colCount++;
//
////					cell = ReportFormats.cell(row, colCount, obj.getCust_name_shipto(), null);
////					colCount++;
////
////					cell = ReportFormats.cell(row, colCount, obj.getDestination().toString(), null);
////					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getSale_prod_code_sg().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getSale_prod_name().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount, obj.getSale_prod_qty_billed(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
////					cell = ReportFormats.cellNumLong(row, colCount, obj.getSale_prod_qty_free(),
////							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
////					colCount++;
//
//					cell = ReportFormats.cellNum(row, colCount, Double.valueOf(obj.getSale_billed_value().toString()),
//							cellAlignment);
//					colCount++;
//
//					if (oldReq.compareTo(newReq) == 0 && oldProdCd.equals(newProdCd)) {
//
//						cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_code(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
//						colCount++;
//
////						cell = ReportFormats.cell(row, colCount, "", null);
////						colCount++;
//						//
////						cell = ReportFormats.cell(row, colCount, "", null);
////						colCount++;
//						//
////						cell = ReportFormats.cell(row, colCount, "", null);
////						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, "", null);
//						colCount++;
//
//					} else {
//
//						cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_code(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
//						colCount++;
//
//						cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_reqd(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_supplied().longValue(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//						cell = ReportFormats.cellNumLong(row, colCount, obj.getPending_qty().longValue(),
//								ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//						colCount++;
//
//					}
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_challan_no(), null);
//					colCount++;
//
//					if (obj.getDsp_dt().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getDsp_dt().trim()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_lr_no(), null);
//					colCount++;
//
//					if (obj.getDsp_lr_dt().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getDsp_lr_dt().trim()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getE_inv_no(), null);
//					colCount++;
//
//					if (obj.getE_inv_date().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getE_inv_date()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getTrn_ewaybillno(), null);
//					colCount++;
//
//					if (obj.getTrn_ewaybilldt().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getTrn_ewaybilldt()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getLoc_nm(), null);
//					colCount++;
//					cell = ReportFormats.cell(row, colCount, obj.getShipto_state_name(), null);
//					colCount++;
//
//					oldReq = newReq;
//					oldProdCd = newProdCd;
//					oldInv = newInv;
//
//					rowCount++;
//
//				} else if ((bean.getPending_qty().equals("N"))) {
//
//					row = sheet.createRow(rowCount);
//					colCount = 0;
//
//					cell = ReportFormats.cell(row, colCount, obj.getRow().toString(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_CENTER));
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getInvoice_no(), null);
//					colCount++;
//
//					if (obj.getInvoice_date().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getInvoice_date()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getRequest_no(), null);
//					colCount++;
//
//					if (obj.getArticle_req_date().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYMD(obj.getArticle_req_date()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getCust_name_shipto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getCust_code_shipto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDestination(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getCust_name_billto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getErp_cust_cd(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDestination_billto(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount,
//							obj.getSale_prod_code_sg() == null ? "" : obj.getSale_prod_code_sg().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount,
//							obj.getSale_prod_name() == null ? "" : obj.getSale_prod_name().toString(), null);
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount,
//							obj.getSale_prod_qty_billed() == null ? 0 : obj.getSale_prod_qty_billed(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
//					cell = ReportFormats.cellNum(row, colCount, Double.valueOf(obj.getSale_billed_value().toString()),
//							cellAlignment);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_code(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_reqd(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_supplied().longValue(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
//					cell = ReportFormats.cellNumLong(row, colCount, obj.getPending_qty().longValue(),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_challan_no(), null);
//					colCount++;
//
//					if (obj.getDsp_dt().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getDsp_dt().trim()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getDsp_lr_no(), null);
//					colCount++;
//
//					if (obj.getDsp_lr_dt().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getDsp_lr_dt().trim()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getE_inv_no(), null);
//					colCount++;
//
//					if (obj.getE_inv_date().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getE_inv_date()), dateStyle);
//					}
//
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getTrn_ewaybillno(), null);
//					colCount++;
//
//					if (obj.getTrn_ewaybilldt().trim().equals("")) {
//						cell = ReportFormats.cell(row, colCount, "", null);
//					} else {
//						cell = ReportFormats.cellDate(row, colCount,
//								MedicoResources.getDateformatYYYYDDMMHH(obj.getTrn_ewaybilldt()), dateStyle);
//					}
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getLoc_nm(), null);
//					colCount++;
//
//					cell = ReportFormats.cell(row, colCount, obj.getShipto_state_name(), null);
//					colCount++;
//					oldReq = newReq;
//					oldProdCd = newProdCd;
//					oldInv = newInv;
//
//					rowCount++;
//
//				}
//
//			}
//
//			List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
//			int count = 0;
//			for (CellRangeAddress rangeAddress : mergedRegions) {
////				System.out.println("rangeAddress :: " + rangeAddress.toString());
//
//				if (count > 3) {
//					RegionUtil.setBorderTop(BorderStyle.THIN, rangeAddress, sheet);
//					RegionUtil.setBorderLeft(BorderStyle.THIN, rangeAddress, sheet);
//					RegionUtil.setBorderRight(BorderStyle.THIN, rangeAddress, sheet);
//					RegionUtil.setBorderBottom(BorderStyle.THIN, rangeAddress, sheet);
//				}
//				count++;
//
//			}
//
//			/// Scheme description sheet starts here
//
//			XSSFSheet sheet2 = workbook.createSheet("SCHEME DESCRIPTION");
//
//			XSSFRow description_row = null;
//			XSSFCell description_cell = null;
//			int description_rowCount = 0;
//			int description_colCount = 0;
//
//			List<String> description_headings = new ArrayList<>();
//
//			// creating heading
//			description_headings.add("Sl No");
//			description_headings.add("Location Name");
//			description_headings.add("Sub Company Code");
//			description_headings.add("Scheme Code");
//			description_headings.add("Scheme Name");
//			description_headings.add("Valid From Date");
//			description_headings.add("Valid To Date");
//			description_headings.add("Article Product Code");
//			description_headings.add("Article Product Name");
//			description_headings.add("Sale Product Code");
//			description_headings.add("Sale Product Name");
//			description_headings.add("Scheme Apply To");
//
//			description_headings.add("Billed Value");
//			description_headings.add("Article Qty Per Total Value");
//			description_headings.add("Per Sale Qty Total");
//			description_headings.add("Article Qty");
//
//			description_row = sheet2.createRow(description_rowCount);
//			description_cell = description_row.createCell(description_colCount);
//
//			sheet2.addMergedRegion(
//					new CellRangeAddress(description_rowCount, description_rowCount, description_colCount, 15));
//			description_cell.setCellValue("SCHEME DESCRIPTION");
//			description_cell.setCellStyle(headingCellStyle);
//			description_rowCount += 1;
//
//			description_colCount = 0;
//
//			description_row = sheet2.createRow(description_rowCount);
//			for (String heading : description_headings) {
//
//				description_cell = description_row.createCell(description_colCount);
//
//				description_cell = ReportFormats.cell(description_row, description_colCount, heading,
//						columnHeadingStyle);
//				description_colCount++;
//			}
//
//			description_rowCount += 1;
//			description_colCount = 0;
//
//			String applay_To_Value_Pervious = "";
//			Long rowcount = 1L;
//			for (Scheme_Desription_Bean description_bean : listOfSchemDescription) {
//
//				description_row = sheet2.createRow(description_rowCount);
//				description_colCount = 0;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount, rowcount.toString(), null);
//				rowcount++;
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getLoc_nm().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getSub_comp_cd().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getTrd_scheme_code().toString(), null);
//				description_colCount++;
//
////				description_cell = ReportFormats.cell(description_row, description_colCount,
////						description_bean.getTrd_scheme_name().toString(), null);
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						MedicoConstants.toCamelCase(description_bean.getTrd_scheme_name().toString()), null);
//
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getValid_from_dt().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getValid_to_dt().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getArticle_prod_cd().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getArticle_name().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getSale_prod_code_sg().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getSale_prod_name().toString(), null);
//				description_colCount++;
//
//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getApply_to().toString(), null);
//				description_colCount++;
//
//				// only showing one time value and article qty and
//				if (!applay_To_Value_Pervious.equals(description_bean.getBilled_value().toString())
//						&& "V".equals(description_bean.getApply_to())) {
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getBilled_value().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getArticle_qty_per_tot_val().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getPer_sale_qty_tot().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getArticle_qty().toString()),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
//				}
//
//				// if value and article qty same adding blank row
//				else if (applay_To_Value_Pervious.equals(description_bean.getBilled_value().toString())
//						&& "V".equals(description_bean.getApply_to())) {
//
//					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
//					description_colCount++;
//
//					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
//					description_colCount++;
//
//					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
//					description_colCount++;
//
//					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
//
//					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
//				}
//
//				// scheme apply to works normal
//				else if ("Q".equals(description_bean.getApply_to())) {
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getBilled_value().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getArticle_qty_per_tot_val().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getPer_sale_qty_tot().toString()),
//							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
//					description_colCount++;
//
//					description_cell = ReportFormats.cellNum(description_row, description_colCount,
//							Double.valueOf(description_bean.getArticle_qty().toString()),
//							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
//					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
//				}
//
//				description_rowCount++;
//
//			}
//
//			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
//			workbook.write(fileOutputStream);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (workbook != null) {
//				workbook.close();
//			}
//		}
//
//		return filename;
//	}

	@Override
	public List<Artreq_To_Dispatch_Report_With_Param> getArticleSchemeDirectToStockistReport(
			ArticleSchemeReportBean bean) {
		return article_Scheme_master_Repository.getArticleSchemeDirectToStokistReport(bean);
	}

	@Override
	public List<Scheme_Desription_Bean> getSchemeDescription(ArticleSchemeReportBean bean) {
		return article_Scheme_master_Repository.getArticleSchemeDescriptionReport(bean);
	}

	@Override
	public Map<String, Long> getArticleGraphData(String emp_id, String emp_loc, String subcom_id) throws Exception {

		String Sub_company_code = this.article_Scheme_master_Repository.get_Subcompany_Code(subcom_id);

		return this.article_Scheme_master_Repository.getArticleGraphData(emp_id, emp_loc, Sub_company_code);

	}

	@Override
	public List<DivMaster> get_Scheme_Division() throws Exception {

		return this.article_Scheme_master_Repository.get_Scheme_Division();
	}

	@Override
	public List<Scheme_Summary> get_article_Scheme_Summary_Home(String formattedString) throws Exception {

		List<FinYear> finyearBean = this.article_Scheme_master_Repository.getfinyear();

		if (finyearBean != null) {

			ArticleSchemeReportBean bean = new ArticleSchemeReportBean();
			String pattern = "yyyy-MM-dd";
			String dateInString_startDate = new SimpleDateFormat(pattern)
					.format(finyearBean.get(0).getFin_start_date());
			String dateInString_EndDate = new SimpleDateFormat(pattern).format(finyearBean.get(0).getFin_end_date());

			System.err.println("dateInString_startDate" + dateInString_startDate);
			System.err.println("dateInString_EndDate" + dateInString_EndDate);

			bean.setStDt(dateInString_startDate);
			bean.setEnDt(dateInString_EndDate);

			return this.reportRepository.generateSchemeSummary(formattedString,finyearBean.get(0).getFin_year(),"CURRENT");

		}
		return null;
	}

	@Override
	public List<trd_scheme_mst_hdr> get_HDR_Data_For_Validity_Extension(String empId) {
		return this.article_Scheme_master_Repository.get_HDR_Data_For_Validity_Extension(empId);
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void saveValidityExtension(Validity_ext_bean bean, MultipartFile file, HttpServletRequest request,
//			HttpSession session) throws Exception {
//
//		
//		trd_scheme_mst_hdr scheme_header = null;
//		Art_Sch_Vld_Ext art_sch_vld_ext = null;
//		if (bean.getEntryModify().trim().equalsIgnoreCase("Entry")) {
//			scheme_header = entityManager.find(trd_scheme_mst_hdr.class, Long.valueOf(bean.getSchemeId()));
//			art_sch_vld_ext = new Art_Sch_Vld_Ext();
//			art_sch_vld_ext.setExt_appr_status("E");
//			art_sch_vld_ext.setExt_ins_dt(new Date());
//			art_sch_vld_ext.setExt_ins_usr_id(bean.getEmpId());
//			art_sch_vld_ext.setExt_status("A");
//			art_sch_vld_ext.setScheme_slno(bean.getSchemeId());
//			art_sch_vld_ext.setFin_year(scheme_header.getFinyear());
//			art_sch_vld_ext.setCompany_cd(scheme_header.getCompany_cd());
//			art_sch_vld_ext.setScheme_div_id(scheme_header.getScheme_div_id());
//		} else if (bean.getEntryModify().trim().equalsIgnoreCase("Modify")) {
//			art_sch_vld_ext = this.getVldExtenById(Long.valueOf(bean.getVld_ext_no_for_modify()));
//			scheme_header = entityManager.find(trd_scheme_mst_hdr.class,
//					Long.valueOf(art_sch_vld_ext.getScheme_slno()));
//			art_sch_vld_ext.setExt_mod_usr_id(bean.getEmpId());
//			art_sch_vld_ext.setExt_mod_usr_dt(new Date());
//		}
//
//		art_sch_vld_ext.setDoc_to_replace(bean.getDocToRemove());
//		art_sch_vld_ext.setFilename(file.getOriginalFilename());
//		byte[] bytes = file.getBytes();
//		Path path = Paths.get(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file.getOriginalFilename());
//		Files.write(path, bytes);
//		art_sch_vld_ext.setFile_path(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file.getOriginalFilename());
//		if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
//			art_sch_vld_ext.setFile_type("I");
//		} else {
//			art_sch_vld_ext.setFile_type("D");
//		}
//		art_sch_vld_ext.setRemarks(bean.getRemarks());
//		art_sch_vld_ext.setValidity_ext_dt(bean.getValid_ext_dt());
//
//		if (bean.getEntryModify().trim().equalsIgnoreCase("Entry")) {
//			this.transactionalRepository.persist(art_sch_vld_ext);
//		} else if (bean.getEntryModify().trim().equalsIgnoreCase("Modify")) {
//			this.transactionalRepository.update(art_sch_vld_ext);
//		}
//
//		this.sendScmVldExtensionForApproval(scheme_header, art_sch_vld_ext, art_sch_vld_ext.getExt_ins_usr_id(), null,
//				"", request, session);
//	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveValidityExtension(Validity_ext_bean bean, List<MultipartFile> files, HttpServletRequest request,
			HttpSession session) throws Exception {

		String ip_addr = request.getRemoteAddr();

		trd_scheme_mst_hdr scheme_header = null;
		Art_Sch_Vld_Ext art_sch_vld_ext = null;
		if (bean.getEntryModify().trim().equalsIgnoreCase("Entry")) {
			scheme_header = entityManager.find(trd_scheme_mst_hdr.class, Long.valueOf(bean.getSchemeId()));
			art_sch_vld_ext = new Art_Sch_Vld_Ext();
			art_sch_vld_ext.setExt_appr_status("E");
			art_sch_vld_ext.setExt_ins_dt(new Date());
			art_sch_vld_ext.setExt_ins_usr_id(bean.getEmpId());
			art_sch_vld_ext.setExt_status("A");
			art_sch_vld_ext.setScheme_slno(bean.getSchemeId());
			art_sch_vld_ext.setFin_year(scheme_header.getFinyear());
			art_sch_vld_ext.setCompany_cd(scheme_header.getCompany_cd());
			art_sch_vld_ext.setScheme_div_id(scheme_header.getScheme_div_id());
		} else if (bean.getEntryModify().trim().equalsIgnoreCase("Modify")) {
			art_sch_vld_ext = this.getVldExtenById(Long.valueOf(bean.getVld_ext_no_for_modify()));
			scheme_header = entityManager.find(trd_scheme_mst_hdr.class,
					Long.valueOf(art_sch_vld_ext.getScheme_slno()));
			art_sch_vld_ext.setExt_mod_usr_id(bean.getEmpId());
			art_sch_vld_ext.setExt_mod_usr_dt(new Date());
		}

//		art_sch_vld_ext.setDoc_to_replace(bean.getDocToRemove());
//		art_sch_vld_ext.setFilename(file.getOriginalFilename());
//		byte[] bytes = file.getBytes();
//		Path path = Paths.get(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file.getOriginalFilename());
//		Files.write(path, bytes);
//		art_sch_vld_ext.setFile_path(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file.getOriginalFilename());
//		if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
//			art_sch_vld_ext.setFile_type("I");
//		} else {
//			art_sch_vld_ext.setFile_type("D");
//		}
//		

		art_sch_vld_ext.setRemarks(bean.getRemarks());
		art_sch_vld_ext.setValidity_ext_dt(bean.getValid_ext_dt());

		if (bean.getEntryModify().trim().equalsIgnoreCase("Entry")) {
			this.transactionalRepository.persist(art_sch_vld_ext);
		} else if (bean.getEntryModify().trim().equalsIgnoreCase("Modify")) {
			this.transactionalRepository.update(art_sch_vld_ext);
		}

		// docs saveing

		if (files.size() > 0) {

			for (MultipartFile f : files) {

				String file_name = f.getOriginalFilename().replace(" ", "_");
				byte[] bytes = f.getBytes();
				Path path = java.nio.file.Paths.get(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file_name);
				Files.write(path, bytes);

				ARTICLE_SCH_VALID_EXT_DOCS docsBean = new ARTICLE_SCH_VALID_EXT_DOCS();
				docsBean.setScheme_slno(art_sch_vld_ext.getScheme_slno().toString());
				docsBean.setScheme_exc_id(art_sch_vld_ext.getSlno().toString());
				docsBean.setFilename(file_name);

				if (f.getContentType().equals("image/png") || f.getContentType().equals("image/jpeg")) {
					docsBean.setFiletype("I");
				} else {
					docsBean.setFiletype("D");
				}

				docsBean.setFile_path(MedicoConstants.ARTICLE_SCHEME_MASTER_UPLOADED_FILES + file_name);
				docsBean.setScheme_slno(art_sch_vld_ext.getScheme_slno().toString());
				docsBean.setIns_ip_addr(request.getLocalAddr());

				if (bean.getEntryModify().trim().equalsIgnoreCase("Entry")) {

					docsBean.setDoc_ins_usr_id(bean.getEmpId());
					docsBean.setDoc_ins_dt(new Date());
					this.transactionalRepository.persist(docsBean);
				} else if (bean.getEntryModify().trim().equalsIgnoreCase("Modify")) {
					docsBean.setDoc_ins_usr_id(bean.getEmpId());
					docsBean.setDoc_ins_dt(new Date());
					docsBean.setDoc_mod_usr_id(bean.getEmpId());
					docsBean.setDoc_mod_usr_dt(new Date());
					this.transactionalRepository.update(docsBean);
				}

			}
		}

		this.sendScmVldExtensionForApproval(scheme_header, art_sch_vld_ext, art_sch_vld_ext.getExt_ins_usr_id(), null,
				"", request, session);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendScmVldExtensionForApproval(trd_scheme_mst_hdr hdr, Art_Sch_Vld_Ext artSchVldExt, String user,
			String approver_email, String remark, HttpServletRequest request, HttpSession session) throws Exception {

		// String ip_addr = request.getRemoteAddr();

		System.out.println("user " + user);
		String tranType = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();

		if (artSchVldExt.getExt_appr_status().equalsIgnoreCase("E")) {

			tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(0L, SCM_MST_VLD_EXT,
					artSchVldExt.getCompany_cd().trim());

			if (tranType == null || tranType.isEmpty()) {
				throw new MedicoException("Could not find Approval Data !");
			}

			String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
			System.out.println("tranType " + tranType);

			List<TaskMaster> masters = taskMasterService.getTask(artSchVldExt.getScheme_div_id(), null, null, tranType,
					null, null, TASK_FLOW, null, null);

			if (masters.size() == 0) {
				throw new MedicoException("Task master record not found");
			}

			TaskMaster taskMaster = masters.get(0);
			List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
			if (task_Master_Dtls.size() == 0) {
				throw new MedicoException("Task master detail record not found");
			}

			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("initiator", user);
			variables.put("initiator_desc", user);
			variables.put("initiator_email", "");
			variables.put("tran_ref_id", artSchVldExt.getSlno());// sl no
			variables.put("tran_type", tranType);
			variables.put("inv_group", artSchVldExt.getScheme_div_id());
			variables.put("loc_id", 0L);// oL
			variables.put("cust_id", 0L);
			variables.put("tran_no", artSchVldExt.getScheme_slno());// schme code
			variables.put("company_code", companyCode);
			variables.put("totaltask", masters.size());
			variables.put("amount", BigDecimal.ZERO);
			variables.put("escalatetime", null);
			variables.put("fin_year_id", artSchVldExt.getFin_year());// fin year
			variables.put("stock_point_id", 0);
			variables.put("doc_type", SCM_MST_VLD_EXT);
			variables.put("task_flow", TASK_FLOW);
			variables.put("remark", remark);
			variables.put("fs_id", 0L);
			variables.put("approval_type", null);

			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("scmMasterValidityExtension",
					variables);
			// changing approval status
			artSchVldExt.setExt_appr_status("F");
			artSchVldExt.setExt_mod_usr_dt(new Date());
			artSchVldExt.setExt_ins_usr_id(user);
			transactionalRepository.update(artSchVldExt);

			// send mail logic
			try {
				this.sendmails(hdr, tranType, null, "nextApproval", companyCode, user, request.getRemoteAddr(), true,
						artSchVldExt);
			} catch (Exception e) {
				System.out.println("Error while sending mail :::::");
				e.printStackTrace();
			}
		}

	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void finalApproval_SchemeExtVld(Long scm_extvld_slno, String last_approved_by, String comp_cd,
//			String isapproved, String motivation) throws Exception {
//		Art_Sch_Vld_Ext art_scm_vld_ext = this.getVldExtenById(scm_extvld_slno);
//		if (isapproved.equalsIgnoreCase("A")) {
//			art_scm_vld_ext.setExt_appr_status("A");
//			art_scm_vld_ext.setExt_mod_usr_id(last_approved_by);
//			art_scm_vld_ext.setExt_mod_usr_dt(new Date());
//			this.transactionalRepository.update(art_scm_vld_ext);
//
//			trd_scheme_mst_hdr scheme_header = entityManager.find(trd_scheme_mst_hdr.class,
//					Long.valueOf(art_scm_vld_ext.getScheme_slno()));
//			scheme_header.setValid_to_dt(art_scm_vld_ext.getValidity_ext_dt());
//			scheme_header.setLast_mod_by(last_approved_by);
//			scheme_header.setLast_mod_date(new Date());
//			this.transactionalRepository.update(scheme_header);
//			// DELETE OLDER DOC
//			if (art_scm_vld_ext.getDoc_to_replace() != null) {
//				this.article_Scheme_master_Repository.deleteDocBySchAndSlno(scheme_header.getTrd_sch_slno(),
//						art_scm_vld_ext.getDoc_to_replace());
//			}
//			// save the doc slno
//			TRD_SCH_MST_DOCS ext_docs = new TRD_SCH_MST_DOCS();
//			ext_docs.setTrd_sch_slno(scheme_header.getTrd_sch_slno());
//			ext_docs.setFilename(art_scm_vld_ext.getFilename());
//			ext_docs.setFilepath(art_scm_vld_ext.getFile_path());
//			ext_docs.setFiletype(art_scm_vld_ext.getFile_type());
//			ext_docs.setDoc_ins_usr_id(last_approved_by);
//			ext_docs.setDoc_ins_dt(new Date());
//			ext_docs.setDoc_ins_ip_addr("");
//			this.transactionalRepository.persist(ext_docs);
//			// send final approval mail
//			send_Mail_for_cfa_locations(scheme_header.getTrd_sch_slno().toString(), true, art_scm_vld_ext.getSlno());
//		} else {
//			art_scm_vld_ext.setExt_appr_status("E");
//			art_scm_vld_ext.setExt_mod_usr_id(last_approved_by);
//			art_scm_vld_ext.setExt_mod_usr_dt(new Date());
//			this.transactionalRepository.update(art_scm_vld_ext);
//		}
//	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void finalApproval_SchemeExtVld(Long scm_extvld_slno, String last_approved_by, String comp_cd,
//			String isapproved, String motivation) throws Exception {
//		Art_Sch_Vld_Ext art_scm_vld_ext = this.getVldExtenById(scm_extvld_slno);
//		if (isapproved.equalsIgnoreCase("A")) {
//			art_scm_vld_ext.setExt_appr_status("A");
//			art_scm_vld_ext.setExt_mod_usr_id(last_approved_by);
//			art_scm_vld_ext.setExt_mod_usr_dt(new Date());
//			this.transactionalRepository.update(art_scm_vld_ext);
//
//			trd_scheme_mst_hdr scheme_header = entityManager.find(trd_scheme_mst_hdr.class,
//					Long.valueOf(art_scm_vld_ext.getScheme_slno()));
//			scheme_header.setValid_to_dt(art_scm_vld_ext.getValidity_ext_dt());
//			scheme_header.setLast_mod_by(last_approved_by);
//			scheme_header.setLast_mod_date(new Date());
//			this.transactionalRepository.update(scheme_header);
//			// DELETE OLDER DOC
//			if (art_scm_vld_ext.getDoc_to_replace() != null) {
//				this.article_Scheme_master_Repository.deleteDocBySchAndSlno(scheme_header.getTrd_sch_slno(),
//						art_scm_vld_ext.getDoc_to_replace());
//			}
//
//			List<ArticleDocsBean> schemeMasterDocs = this.article_Scheme_master_Repository
//					.get_Docs_Details(art_scm_vld_ext.getScheme_slno().toString());
//
//			if (schemeMasterDocs.size() > 0) {
//				this.article_Scheme_master_Repository.deleteDocBySchemeSlnumber(art_scm_vld_ext.getScheme_slno());
//			}
//
//			List<ARTICLE_SCH_VALID_EXT_DOCS> docs = this.article_Scheme_master_Repository
//					.getarticleEsxTendedSchemeDocsData(art_scm_vld_ext.getSlno());
//
//			System.err.println(docs.size());
//			for (ARTICLE_SCH_VALID_EXT_DOCS t : docs) {
//
//				TRD_SCH_MST_DOCS masterBean = new TRD_SCH_MST_DOCS();
//
//				masterBean.setDoc_ins_dt(new Date());
//				masterBean.setDoc_ins_ip_addr(t.getIns_ip_addr());
//				masterBean.setDoc_ins_usr_id(t.getDoc_ins_usr_id());
//				masterBean.setFilename(t.getFilename());
//				masterBean.setFilepath(t.getFile_path());
//				masterBean.setFiletype(t.getFiletype());
//				masterBean.setTrd_sch_slno(Long.valueOf(t.getScheme_slno()));
//				System.out.println(masterBean);
//				this.transactionalRepository.persist(masterBean);
//
//			}
//
//			send_Mail_for_cfa_locations(scheme_header.getTrd_sch_slno().toString(), true, art_scm_vld_ext.getSlno());
//		} else {
//			art_scm_vld_ext.setExt_appr_status("E");
//			art_scm_vld_ext.setExt_mod_usr_id(last_approved_by);
//			art_scm_vld_ext.setExt_mod_usr_dt(new Date());
//			this.transactionalRepository.update(art_scm_vld_ext);
//		}
//	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalApproval_SchemeExtVld(Long scm_extvld_slno, String last_approved_by, String comp_cd,
			String isapproved, String motivation) throws Exception {
		Art_Sch_Vld_Ext art_scm_vld_ext = this.getVldExtenById(scm_extvld_slno);
		if (isapproved.equalsIgnoreCase("A")) {
			art_scm_vld_ext.setExt_appr_status("A");
			art_scm_vld_ext.setExt_mod_usr_id(last_approved_by);
			art_scm_vld_ext.setExt_mod_usr_dt(new Date());
			this.transactionalRepository.update(art_scm_vld_ext);

			trd_scheme_mst_hdr scheme_header = entityManager.find(trd_scheme_mst_hdr.class,
					Long.valueOf(art_scm_vld_ext.getScheme_slno()));
			scheme_header.setValid_to_dt(art_scm_vld_ext.getValidity_ext_dt());
			scheme_header.setLast_mod_by(last_approved_by);
			scheme_header.setLast_mod_date(new Date());
			scheme_header.setCycle_no(scheme_header.getCycle_no() + 1);
			this.transactionalRepository.update(scheme_header);

			List<TRD_SCH_MST_DOCS> docs = this.article_Scheme_master_Repository
					.getDocsBySchemeSlno(scheme_header.getTrd_sch_slno());

			for (TRD_SCH_MST_DOCS d : docs) {
				this.transactionalRepository.delete(d);
			}

			// DELETE OLDER DOC
//			if (art_scm_vld_ext.getDoc_to_replace() != null) {
//				this.article_Scheme_master_Repository.deleteDocBySchAndSlno(scheme_header.getTrd_sch_slno(),
//						art_scm_vld_ext.getDoc_to_replace());
//			}

//			List<ArticleDocsBean> schemeMasterDocs = this.article_Scheme_master_Repository
//					.get_Docs_Details(art_scm_vld_ext.getScheme_slno().toString());
//
//			if (schemeMasterDocs.size() > 0) {
//				this.article_Scheme_master_Repository.deleteDocBySchemeSlnumber(art_scm_vld_ext.getScheme_slno());
//			}

			List<ARTICLE_SCH_VALID_EXT_DOCS> Ext_docs = this.article_Scheme_master_Repository
					.getarticleEsxTendedSchemeDocsData(art_scm_vld_ext.getSlno());

			System.err.println(docs.size());

			for (ARTICLE_SCH_VALID_EXT_DOCS t : Ext_docs) {

				TRD_SCH_MST_DOCS masterBean = new TRD_SCH_MST_DOCS();

				masterBean.setDoc_ins_dt(new Date());
				masterBean.setDoc_ins_ip_addr(t.getIns_ip_addr());
				masterBean.setDoc_ins_usr_id(t.getDoc_ins_usr_id());
				masterBean.setFilename(t.getFilename());
				masterBean.setFilepath(t.getFile_path());
				masterBean.setFiletype(t.getFiletype());
				masterBean.setTrd_sch_slno(Long.valueOf(t.getScheme_slno()));
				masterBean.setCycle_no(String.valueOf(scheme_header.getCycle_no()));
				System.out.println(masterBean);
				this.transactionalRepository.persist(masterBean);

			}

			send_Mail_for_cfa_locations(scheme_header.getTrd_sch_slno().toString(), true, art_scm_vld_ext.getSlno());
		} else {
			art_scm_vld_ext.setExt_appr_status("E");
			art_scm_vld_ext.setExt_mod_usr_id(last_approved_by);
			art_scm_vld_ext.setExt_mod_usr_dt(new Date());
			this.transactionalRepository.update(art_scm_vld_ext);
		}
	}

	@Override
	public List<TRD_SCH_MST_DOCS> getDocsBySchemeSlno(Long trd_sch_slno) throws Exception {
		return this.article_Scheme_master_Repository.getDocsBySchemeSlno(trd_sch_slno);
	}

	@Override
	public Art_Sch_Vld_Ext getVldExtenById(Long slno) throws Exception {
		return this.entityManager.find(Art_Sch_Vld_Ext.class, slno);
	}

	@Override
	public List<Art_Sch_Vld_Ext> getEnteredExtensions(String empId) throws Exception {
		return this.article_Scheme_master_Repository.getEnteredExtensions(empId);
	}

	public void send_Mailfor_After_Discard_Creator(String created_by, String last_mod_by, String approver, Long slno,
			boolean isValidityExtension, boolean status, Long extVldSlno) throws Exception {

		List<String> to_list = new ArrayList<>();
		String approver_name = this.article_Scheme_master_Repository.getEmployeeDetailsByEmployeeID(approver);

		to_list.add(created_by);
		to_list.add(last_mod_by);
		String emp_id = "";
		Art_Sch_Vld_Ext sch_vld_ext = null;
		if (isValidityExtension) {
			sch_vld_ext = this.getVldExtenById(extVldSlno);
		}
		to_list = this.article_Scheme_master_Repository.getEmployeeEmail(created_by);
		this.send_Mail_for_cfa_location(slno.toString(), "", "", to_list, isValidityExtension, sch_vld_ext, true,
				approver_name, status);

	}

	@Override
	public List<Articele_Scheme_Approved_Data> getApprovedData(String schemeDivId, String tranType, Long trd_SCH_SLNO)
			throws Exception {
		List<Articele_Scheme_Approved_Data> list_of_approved_data = this.article_Scheme_master_Repository
				.getApprovedData(schemeDivId, tranType, trd_SCH_SLNO);
		List<Articele_Scheme_Approved_Data> sortedArrayList = new ArrayList<Articele_Scheme_Approved_Data>();
		;
		if (list_of_approved_data != null && list_of_approved_data.size() > 0) {
			System.err.println(list_of_approved_data.size());

			// get no of approvers
			Integer max_appr_level = this.article_Scheme_master_Repository
					.getMaxApprLevelByTranTypeAndInvGrpId(tranType, Long.valueOf(schemeDivId));

//			if (list_of_approved_data.size() % 2 == 0 && list_of_approved_data.size() % 4 != 0) {
//				skip = 3;
//			}
//			if (list_of_approved_data.size() % 3 == 0) {
//				skip = 4;
//			}
//			if (list_of_approved_data.size() % 4 == 0) {
//				skip = 5;
//			}
//			if (list_of_approved_data.size() % 5 == 0) {
//				skip = 6;
//			}

			int i = 1;

			while (list_of_approved_data.size() > i) {
				if (i == 1) {
					sortedArrayList.add(list_of_approved_data.get(0));
				}
				i = i + max_appr_level + 1;
				if (list_of_approved_data.size() > i) {
					sortedArrayList.add(list_of_approved_data.get(i - 1));
				}
			}
			System.err.println("sortedarray::::" + sortedArrayList);
		}
		return sortedArrayList;
	}

	@Override
	public List<Artreq_To_Dispatch_Report_With_Param> 	 sortList(List<New_Stockist_wiseScheme_request_model> list, List<New_stockist_wise_scheme_request_supply_Detail_model> detaillist, ArticleSchemeReportBean bean) throws ParseException {
		List<Artreq_To_Dispatch_Report_With_Param> sortedList = new ArrayList<>();

		
		String previousalloc_Id="";
		Long previousProd_id=0L;
		for (New_Stockist_wiseScheme_request_model a : list) {

			if ((bean.getPending_qty().equals("N"))) {

				Artreq_To_Dispatch_Report_With_Param s = new Artreq_To_Dispatch_Report_With_Param();
				s.setRow(a.getROW());
				s.setLoc_nm(a.getLoc_nm());
				s.setArticle_req_id(a.getArticle_req_id().toString());
				s.setRequest_no(a.getRequest_no());
			
				s.setInvoice_no(a.getInvoice_no());
				s.setInvoice_date(a.getInvoice_date());
				s.setArticle_req_date(MedicoResources.convert_DD_MM_YYYY2(a.getArticle_req_date()));
				s.setCust_name_billto(a.getCust_name_billto());
				s.setCust_name_shipto(a.getCust_name_shipto());
				s.setCust_code_shipto(a.getErp_cust_cd());
				s.setDestination(a.getDestination());
				s.setSale_prod_code_sg(a.getSale_prod_code_sg());
				s.setSale_prod_name(a.getSale_prod_name());
				s.setSale_prod_qty_billed(a.getSale_prod_qty_billed().toString());
				s.setSale_prod_qty_free(a.getSale_prod_qty_free().toString());
				s.setSale_billed_value(a.getSale_billed_value().toString());
				s.setTrd_scheme_name(a.getTrd_scheme_name());
				s.setTrd_scheme_code(a.getTrd_scheme_code());
				s.setArticle_prod_cd(a.getArticle_prod_cd());
				s.setArticle_name(a.getArticle_name());
				s.setQty_reqd(a.getQty_reqd().toString());
				
				s.setShipto_state_name(a.getShipto_state_name());
				s.setDestination_billto(a.getDestination_billto());
				
				System.err.println(a.getShipto_state_name() +""+a.getDestination_billto());
				
				
				
				
				if( ! a.getAlloc_id().equals( previousalloc_Id)  ||   Long.valueOf(a.getArticle_prod_id()) != previousProd_id) {
					
				       List<New_stockist_wise_scheme_request_supply_Detail_model> filteredData = detaillist.stream()
                              .filter(data ->data.getAllocdtl_alloc_id().equals( a.getAlloc_id()) 
                           		    && (data.getProd_id()==null ||  data.getProd_id().equals(a.getArticle_prod_id()))
                           		    )
                              .collect(Collectors.toList());
	       
				       System.err.println(filteredData.size());
				       
				       for(int i=0;i<filteredData.size();i++) {
				    	   
				    	   if(i>0) {
				    		   // create new bean    
				    		    		 s = new Artreq_To_Dispatch_Report_With_Param();
				    					s.setLoc_nm("");
				    					s.setArticle_req_id("");
				    					s.setInvoice_no("");
				    					s.setInvoice_date("");
				    					s.setArticle_req_date("");
				    					s.setCust_name_billto("");
				    					s.setCust_name_shipto("");
				    					s.setCust_code_shipto("");
				    					s.setDestination("");
				    					s.setSale_prod_code_sg("");
				    					s.setSale_prod_name("");
				    					s.setSale_prod_qty_billed("");
				    					s.setSale_prod_qty_free("");
				    					s.setSale_billed_value("");
				    					s.setTrd_scheme_code("");
				    					s.setArticle_prod_cd("");
				    					s.setArticle_name("");
				    					s.setQty_reqd("");  
				    	   }
							s.setQty_supplied(filteredData.get(i).getQty_supplied().toString());
							s.setPending_qty(filteredData.get(i).getPending_qty().toString());
							s.setDsp_challan_no(filteredData.get(i).getDsp_challan_no());
							s.setDsp_dt(filteredData.get(i).getDsp_dt());
							s.setDsp_lr_no(filteredData.get(i).getDsp_lr_no());
							s.setDsp_lr_dt(filteredData.get(i).getDsp_lr_dt());
							s.setE_inv_no(filteredData.get(i).getE_inv_no());
							s.setE_inv_date(filteredData.get(i).getE_inv_date());
							s.setTrn_ewaybillno(filteredData.get(i).getTrn_ewaybillno());
							s.setTrn_ewaybilldt(filteredData.get(i).getTrn_ewaybilldt());
							
							// addd the values  
							sortedList.add(s);
								   
				       }
				       
				       previousalloc_Id=  a.getAlloc_id();
				       previousProd_id=Long.valueOf(a.getArticle_prod_id());   
				}
				
				
				
			
			} else if ((bean.getPending_qty().equals("Y"))) {

				Artreq_To_Dispatch_Report_With_Param s = new Artreq_To_Dispatch_Report_With_Param();

			}
		}

		
//		System.err.println(sortedList);
		return sortedList;
	}

	@Override
	public void unlock_all_article_product() {
		this.article_Scheme_master_Repository.unlock_all_article_product();
	}

	@Override
	public List<trd_scheme_mst_hdr> get_all_scheme() throws Exception {

		return this.article_Scheme_master_Repository.get_all_scheme();
	}

	@Override
	public Map<String, Object> generateArticleSchemeReportAndExtention(Long trd_slno, String website) throws Exception {
		// TODO Auto-generated method stub

		String hdrTableName = "";
		String detailTableName = "";
		String docTableName = "";
		Map<String, Object> map = new HashMap<>();
		Long cycle = this.article_Scheme_master_Repository.get_Cycle_for_Scheme_Master(trd_slno);

		// header
		List<Articel_Scheme_Approved_Data_Bean> header_articel_scheme_approved_data_bean = this.article_Scheme_master_Repository
				.generateArticleSchemeDataHeadr(trd_slno, cycle);

		// detail
		List<Articel_Scheme_Approved_Data_Bean> dtl_articel_scheme_approved_data_bean = this.article_Scheme_master_Repository
				.generateArticleSchemeData_Detail(trd_slno);

		// docs
		List<Articel_Scheme_Approved_Data_Bean> docs_articel_scheme_approved_data_bean = this.article_Scheme_master_Repository
				.generateArticleSchemeData_Docs(trd_slno, cycle);

		// header approved data
		List<Articele_Scheme_Approved_Data> approved_Data = this.getApprovedData_For_Log_Report(
				header_articel_scheme_approved_data_bean.get(0).getSCHEME_DIV_ID(), "32", Long.valueOf(trd_slno));

		// ext header
		List<Article_Scheme_Extent_Bean> header_article_scheme_extent_bean = this.article_Scheme_master_Repository
				.generateArticleSchemeEXTHeader(trd_slno);

		// ext docs
		List<Article_Scheme_Extent_Bean> doc_article_scheme_extent_bean = this.article_Scheme_master_Repository
				.generateArticleSchemeEXT_Doc(trd_slno);

		Location location = null;
		String locationName = "";
		if (header_articel_scheme_approved_data_bean.get(0).getLOC_ID() != null
				&& !header_articel_scheme_approved_data_bean.get(0).getLOC_ID().equals("0")) {
			location = this.locService
					.getObjectById(Long.valueOf(header_articel_scheme_approved_data_bean.get(0).getLOC_ID()));
			locationName = location.getLoc_nm();
		}

		String article_prod_name = this.article_Scheme_master_Repository
				.get_Article_Prod_Name(header_articel_scheme_approved_data_bean.get(0).getARTICLE_PROD_ID());
		String sale_prod_name = this.article_Scheme_master_Repository
				.get_sale_prod_name(dtl_articel_scheme_approved_data_bean.get(0).getSALE_PROD_ID());

		String fileName = this.generateArticleScheme_Master_Report(header_articel_scheme_approved_data_bean,
				dtl_articel_scheme_approved_data_bean, docs_articel_scheme_approved_data_bean, article_prod_name,
				sale_prod_name, locationName, header_article_scheme_extent_bean, doc_article_scheme_extent_bean,
				website, approved_Data);

		map.put("articel_scheme_header", header_article_scheme_extent_bean);
		map.put("articel_scheme_dtl", dtl_articel_scheme_approved_data_bean);
		map.put("article_scheme_header_doc", docs_articel_scheme_approved_data_bean);
		map.put("article_scheme_extent_header", header_article_scheme_extent_bean);
		map.put("article_scheme_extent_doc", doc_article_scheme_extent_bean);

		map.put("article_prod_name", article_prod_name);
		map.put("sale_prod_name", sale_prod_name);
		map.put("locationName", locationName);
		map.put("fileName", fileName);

		return map;
	}

	public String generateArticleScheme_Master_Report(
			List<Articel_Scheme_Approved_Data_Bean> header_articel_scheme_approved_data_bean,
			List<Articel_Scheme_Approved_Data_Bean> dtl_articel_scheme_approved_data_bean,
			List<Articel_Scheme_Approved_Data_Bean> docs_articel_scheme_approved_data_bean, String article_prod_name,
			String sale_prod_name, String locationName,
			List<Article_Scheme_Extent_Bean> header_article_scheme_extent_bean,
			List<Article_Scheme_Extent_Bean> doc_article_scheme_extent_bean, String website,
			List<Articele_Scheme_Approved_Data> approved_Data) throws Exception {

		String filename = "Article_Scheme_Entry_And_Extention_Report" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("Article_Scheme_Entry_And_Extention_Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "SR NO", "LOCATION", "SCHEME CODE", "SCHEME NAME", "VALID FROM", "VALID TO",
					"APPLY TO", "ARTICLE PRODUCT NAME", "PRODUCT CODE", "SALES BILLED VALUE", "ARTICLE QUANTITY",
					"ARTICLE RATE", "REMARKS" };
			String detailHeading[] = { "SR NO", "SALES PRODUCT CODE", "SALES PRODUCT NAME", "SALES BILLED",
					"SALES FREE", "ARTICLE QTY" };
			String docsHeading[] = { "SR NO", "FILE NAME", "FILE TYPE", "FILE" };
			String approverHeading[] = { "Approver Name", "Date & Time", "Approver Level" };

			String headingsExt[] = { "SR NO", "LOCATION", "SCHEME CODE", "SCHEME NAME", " VALID FROM",
					"EXTENDED VALID TO", "APPLY TO", "ARTICLE PRODUCT NAME", "PRODUCT CODE", "SALES BILLED VALUE",
					"ARTICLE QUANTITY", "ARTICLE RATE", "REMARKS" };

//				XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
//				XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeadingBlue(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);

			XSSFCellStyle dateStyle = ReportFormats.dateStyle(workbook);
			XSSFCellStyle blankStyle = ReportFormats.heading3_(workbook);

			XSSFCellStyle dateStylewithRed = workbook.createCellStyle();
			CreationHelper creationHelper = workbook.getCreationHelper();
			dateStylewithRed.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

			Font defaultFontRed = workbook.createFont();
			defaultFontRed.setFontName("Calibri");
			defaultFontRed.setFontHeightInPoints((short) 12);
			defaultFontRed.setBold(true);
			defaultFontRed.setColor(IndexedColors.RED.getIndex());
			dateStylewithRed.setFont(defaultFontRed);

			XSSFCellStyle headingCellStyle = workbook.createCellStyle();
			headingCellStyle.setAlignment(HorizontalAlignment.LEFT);

			XSSFFont headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setBold(true);
			headingCellStyle.setFont(headerFont);

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Pfizer India.");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue(
					"Article Scheme Master : " + header_articel_scheme_approved_data_bean.get(0).getTRD_SCHEME_NAME());
			cell.setCellStyle(headingCellStyle);
			rowCount++;

//	        org.apache.poi.ss.usermodel.Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.FILE);

			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			int count = 1;
			rowCount++;

			for (Articel_Scheme_Approved_Data_Bean data : header_articel_scheme_approved_data_bean) {

				row = sheet.createRow(rowCount);
				colCount = 0;
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLOC_ID().equals("0") ? "ALL" : locationName, null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTRD_SCHEME_CODE(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTRD_SCHEME_NAME(), null);
				colCount++;

				cell = ReportFormats.cellDate(row, colCount, data.getVALID_FROM_DT(), dateStyle);
				colCount++;

				cell = ReportFormats.cellDate(row, colCount, data.getVALID_TO_DT(), dateStyle);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getAPPLY_TO(), null);
				colCount++;

				// product Name
				cell = ReportFormats.cell(row, colCount, article_prod_name, null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getARTICLE_PROD_CD(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getBILLED_VALUE() == null ? 0l : data.getBILLED_VALUE().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getARTICLE_QTY_PER_TOT_VAL().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellBigDecimal(row, colCount, data.getARTICLE_PROD_RATE(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getREMARKS(), null);
				colCount++;

				rowCount++;
				count++;

			}

			row = sheet.createRow(rowCount);
			cell = row.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, detailHeading.length - 1));
			cell.setCellValue("Article Scheme Master Detail");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			colCount = 0;

			for (String heading : detailHeading) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			colCount = 0;
			for (int i = detailHeading.length; i < headings.length; i++) {
				cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
				colCount++;
			}

			rowCount++;

			count = 1;
			for (Articel_Scheme_Approved_Data_Bean data : dtl_articel_scheme_approved_data_bean) {
				colCount = 0;
				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getSALE_PROD_CODE_SG(), null);
				colCount++;

				// sales prod name
				cell = ReportFormats.cell(row, colCount, sale_prod_name, cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getPER_SALE_QTY_BILLED().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getPER_SALE_QTY_FREE().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getARTICLE_QTY().doubleValue(), null);
				colCount++;
				rowCount++;
				count++;

			}

			row = sheet.createRow(rowCount);
			cell = row.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, docsHeading.length - 1));
			cell.setCellValue("Article Scheme Master Documents");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			colCount = 0;
			for (String heading : docsHeading) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			colCount = 0;
			for (int i = docsHeading.length; i < headings.length; i++) {
				cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
				colCount++;
			}

			rowCount++;

			count = 1;
			for (Articel_Scheme_Approved_Data_Bean data : docs_articel_scheme_approved_data_bean) {
				row = sheet.createRow(rowCount);
				colCount = 0;

				cell = ReportFormats.cell(row, colCount, String.valueOf(data.getCycle_no()), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getFILENAME(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getFILETYPE().equals("I") ? "Image" : "Document", null);
				colCount++;

				org.apache.poi.ss.usermodel.Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.FILE);
				hyperlink.setAddress(website + "show-article-docs/" + data.getFILENAME());

//				cell = ReportFormats.cell(row, colCount,cell.setHyperlink(hyperlink) , null);
//				colCount++;

				cell = row.createCell(colCount);
				cell.setHyperlink(hyperlink);
				cell.setCellValue("Click Here to Open File");
				XSSFCellStyle cellStyle = workbook.createCellStyle();
				Font font = workbook.createFont();
				font.setColor(IndexedColors.BLUE.getIndex());
				font.setUnderline(Font.U_SINGLE);
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);

				rowCount++;
				count++;

			}

			/// approved data header

			XSSFCellStyle dateCellStylefortime = workbook.createCellStyle();
			CreationHelper creationHelperfortime = workbook.getCreationHelper();
			dateCellStylefortime.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));

			row = sheet.createRow(rowCount);
			cell = row.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, docsHeading.length - 1));
			cell.setCellValue("Article Scheme Master Approval Log");
			cell.setCellStyle(headingCellStyle);
			rowCount++;

			row = sheet.createRow(rowCount);
			colCount = 0;
			for (String heading : approverHeading) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			colCount = 0;
			for (int i = approverHeading.length; i < headings.length; i++) {
				cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
				colCount++;
			}

			rowCount++;

			count = 1;
			for (Articele_Scheme_Approved_Data data : approved_Data) {
				row = sheet.createRow(rowCount);
				colCount = 0;

				cell = ReportFormats.cell(row, colCount, data.getAPPR_NM(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellDate(row, colCount, data.getNEW_MOD_DATE(), dateCellStylefortime);
				colCount++;
//
				cell = ReportFormats.cell(row, colCount, data.getAPPR_LEVEL(), cellAlignment);
				colCount++;

				rowCount++;
				count++;

			}

			rowCount++;

			Articel_Scheme_Approved_Data_Bean data = header_articel_scheme_approved_data_bean.get(0);

			count = 1;
			int extcount = 1;
			for (Article_Scheme_Extent_Bean ext : header_article_scheme_extent_bean) {

				row = sheet.createRow(rowCount);
				colCount = 0;
				for (String heading : headings) {
					cell = ReportFormats.cell(row, colCount, "", blankStyle);
					colCount++;
				}

				rowCount++;

				row = sheet.createRow(rowCount);
				cell = row.createCell(0);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, headingsExt.length - 1));
				cell.setCellValue("Article Scheme Master Extention -" + extcount);
				cell.setCellStyle(headingCellStyle);
				rowCount++;

				row = sheet.createRow(rowCount);
				colCount = 0;
				for (String heading : headingsExt) {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					colCount++;
				}

				rowCount++;

				colCount = 0;
				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, String.valueOf(extcount), cellAlignment);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getLOC_ID().equals("0") ? "ALL" : locationName, null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTRD_SCHEME_CODE(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getTRD_SCHEME_NAME(), null);
				colCount++;

				cell = ReportFormats.cellDate(row, colCount, data.getVALID_FROM_DT(), dateStyle);
				colCount++;

				cell = ReportFormats.cellDate(row, colCount, ext.getVALIDITY_EXT_DT(), dateStylewithRed);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getAPPLY_TO(), null);
				colCount++;

				// product Name
				cell = ReportFormats.cell(row, colCount, article_prod_name, null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, data.getARTICLE_PROD_CD(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount,
						data.getBILLED_VALUE() == null ? 0l : data.getBILLED_VALUE().doubleValue(), cellAlignment);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getARTICLE_QTY_PER_TOT_VAL().doubleValue(), null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, data.getARTICLE_PROD_RATE().doubleValue(), null);
				colCount++;
				cell = ReportFormats.cell(row, colCount, ext.getREMARKS(), null);
				colCount++;

				rowCount++;
				count++;

				row = sheet.createRow(rowCount);
				cell = row.createCell(0);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, docsHeading.length - 1));
				cell.setCellValue("Article Scheme Extention Documents");
				cell.setCellStyle(headingCellStyle);

				rowCount++;

				row = sheet.createRow(rowCount);
				colCount = 0;

				for (String heading : docsHeading) {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					colCount++;
				}

				colCount = 0;
				for (int i = docsHeading.length; i < headings.length; i++) {
					cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
					colCount++;
				}

				rowCount++;

				count = 1;

				List<ARTICLE_SCH_VALID_EXT_DOCS> ex_docs = this.article_Scheme_master_Repository
						.getarticleEsxTendedSchemeDocsData(ext.getSLNO());
				for (ARTICLE_SCH_VALID_EXT_DOCS d : ex_docs) {
					colCount = 0;

					row = sheet.createRow(rowCount);
					colCount = 0;

					cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
					colCount++;

					cell = ReportFormats.cell(row, colCount, d.getFilename(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, d.getFiletype().equals("I") ? "Image" : "Document", null);
					colCount++;

					org.apache.poi.ss.usermodel.Hyperlink hyperlink = creationHelper
							.createHyperlink(HyperlinkType.FILE);
					
					
//					hyperlink.setAddress(website + "show-article-docs/" + d.getFilename());
					hyperlink.setAddress(website + "show-article-docs/" );

					cell = row.createCell(colCount);
					cell.setHyperlink(hyperlink);
					cell.setCellValue("Click Here to Open File");
					XSSFCellStyle cellStyle = workbook.createCellStyle();
					Font font = workbook.createFont();
					font.setColor(IndexedColors.BLUE.getIndex());
					font.setUnderline(Font.U_SINGLE);
					cellStyle.setFont(font);
					cell.setCellStyle(cellStyle);
					rowCount++;
					count++;
				}

				/// approved data extention

				List<Articele_Scheme_Approved_Data> ext_approved_Data = this.getApprovedData_For_Log_Report(
						header_articel_scheme_approved_data_bean.get(0).getSCHEME_DIV_ID(), "300",
						Long.valueOf(ext.getSLNO()));
				
				
				
				row = sheet.createRow(rowCount);
				cell = row.createCell(0);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, docsHeading.length - 1));
				cell.setCellValue("Article Scheme Master Approval Log");
				cell.setCellStyle(headingCellStyle);
				rowCount++;

				row = sheet.createRow(rowCount);
				colCount = 0;
				for (String heading : approverHeading) {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					colCount++;
				}

				colCount = 0;
				for (int i = approverHeading.length; i < headings.length; i++) {
					cell = ReportFormats.cell(row, i, "", columnHeadingStyle);
					colCount++;
				}

				rowCount++;

				count = 1;
				for (Articele_Scheme_Approved_Data ap_data : ext_approved_Data) {
					row = sheet.createRow(rowCount);
					colCount = 0;

					cell = ReportFormats.cell(row, colCount, ap_data.getAPPR_NM(), cellAlignment);
					colCount++;

					cell = ReportFormats.cellDate(row, colCount, ap_data.getNEW_MOD_DATE(), dateCellStylefortime);
					colCount++;
//	//
					cell = ReportFormats.cell(row, colCount, ap_data.getAPPR_LEVEL(), cellAlignment);
					colCount++;

					rowCount++;
					count++;

				}

				rowCount++;

				extcount++;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

			System.out.println("filepath : " + filepath);

		}

		finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return filename;

	}

	@Override
	public List<trd_scheme_mst_hdr> get_scheme_for_closure(String empId) {

		return this.article_Scheme_master_Repository.get_scheme_for_closure(empId);
	}

	@Override
	public void saveClosure_Date(String trs_sl_no, String closure_date) throws Exception {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(closure_date);

		trd_scheme_mst_hdr hdr = this.entityManager.find(trd_scheme_mst_hdr.class, Long.valueOf(trs_sl_no));

		hdr.setClosure_date(date);
		this.transactionalRepository.update(hdr);

	}

	@Override
	public List<trd_scheme_mst_hdr> getAssociatedSchemsByEmpId(String empId) throws Exception {
		return this.article_Scheme_master_Repository.getAssociatedSchemsByEmpId(empId);
	}

	@Override
	public List<Articele_Scheme_Approved_Data> getApprovedData_For_Log_Report(String schemeDivId, String tranType,
			Long trd_SCH_SLNO) throws Exception {

		List<Articele_Scheme_Approved_Data> list_of_approved_data = this.article_Scheme_master_Repository
				.getApprovedData(schemeDivId, tranType, trd_SCH_SLNO);
		List<Articele_Scheme_Approved_Data> sortedArrayList = new ArrayList<Articele_Scheme_Approved_Data>();
		;
		if (list_of_approved_data != null && list_of_approved_data.size() > 0) {
			System.err.println(list_of_approved_data.size());

			// get no of approvers
			Integer max_appr_level = this.article_Scheme_master_Repository
					.getMaxApprLevelByTranTypeAndInvGrpId(tranType, Long.valueOf(schemeDivId));

//			if (list_of_approved_data.size() % 2 == 0 && list_of_approved_data.size() % 4 != 0) {
//				skip = 3;
//			}
//			if (list_of_approved_data.size() % 3 == 0) {
//				skip = 4;
//			}
//			if (list_of_approved_data.size() % 4 == 0) {
//				skip = 5;
//			}
//			if (list_of_approved_data.size() % 5 == 0) {
//				skip = 6;
//			}

			int i = 1;

			while (list_of_approved_data.size() > i) {
				if (i == 1) {
					sortedArrayList.add(list_of_approved_data.get(0));
				}
				i = i + max_appr_level + 1;
				if (list_of_approved_data.size() >= (i - 1)) {
					sortedArrayList.add(list_of_approved_data.get(i - 1));
				}
			}
			System.err.println("sortedarray::::" + sortedArrayList);
		}
		return sortedArrayList;
	}

	@Override
	public List<New_Stockist_wiseScheme_request_model> getArticleSchemeDirectToStockistReportNewHeaderData(
			ArticleSchemeReportBean bean) throws Exception {

		return this.article_Scheme_master_Repository.getArticleSchemeDirectToStockistReportNewHeaderData(bean);
	}

	@Override
	public String generateArticleSchemeReportHeadernew(List<New_Stockist_wiseScheme_request_model> list,
			ArticleSchemeReportBean bean, HttpSession session, List<Scheme_Desription_Bean> listOfSchemDescription, List<New_stockist_wise_scheme_request_supply_Detail_model> detaillist)
			throws IOException {


		

		String filename = "article_scheme_request_and_delivery_report" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("ARTICLE_SCHEME_REQUEST_AND_DELIVERY_REPORT");

			List<String> headings = new ArrayList<>();
			headings.add("Sl. No.");
			headings.add("Location Name");
			headings.add("SAP Invoice No.");
			headings.add("Invoice Date");
			headings.add("Article Request No");
			headings.add("Article Request Date");
			headings.add("Ship To name");
			headings.add("Ship To Code");
			headings.add("Ship To City");
			headings.add("Ship To State");
			headings.add("Bill To name");
			headings.add("Bill To Code");
			headings.add("Bill To City");
			headings.add("Code");
			headings.add("SKU Name");
			headings.add("Billed Qty");
			headings.add("Bill Val(for Value Schemes)");
			headings.add("Article Scheme Name");
			headings.add("Article Scheme Code");
			headings.add("Code");
			headings.add("Article Name");
			headings.add("Qty Req");
			headings.add("Qty Supplied");
			headings.add("Pending Qty");
			headings.add("Challan No.");
			headings.add("Challan Date");
			headings.add("LR No.");
			headings.add("LR Date");
			headings.add("E-invoice No.");
			headings.add("E-invoice Date");
			headings.add("Eway Bill No.");
			headings.add("Eway Bill Date");

			String  previousalloc_Id = "";

			Long previousProd_id = 0L;

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			Font font = workbook.createFont();
			font.setBold(true);

			XSSFCellStyle headingCellStyle = workbook.createCellStyle();
			headingCellStyle.setAlignment(HorizontalAlignment.LEFT);

			XSSFFont headerFont = workbook.createFont();
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setBold(true);
			headingCellStyle.setFont(headerFont);

			XSSFCellStyle columnStyle = ReportFormats.cellBold2(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeadingBlue(workbook);
			XSSFCellStyle cellAlignment = ReportFormats.setCellAlignmentForNum(workbook, ALIGN_RIGHT);

			XSSFCellStyle dateStyle = workbook.createCellStyle();
			CreationHelper creationHelper = workbook.getCreationHelper();
			dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
//			cell.setCellValue(companyname);
			cell.setCellValue("Pfizer India Ltd.");
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);

			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue("Article Scheme Request and Delivery");
			cell.setCellStyle(headingCellStyle);
			rowCount += 1;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, headings.size() - 1));
			cell.setCellValue("Period:  " + bean.getStDt() + " To " + bean.getEnDt());
			cell.setCellStyle(headingCellStyle);
			rowCount += 1;

//			row = sheet.createRow(rowCount);
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.size() + 5));
//			cell.setCellValue("");
//			cell.setCellStyle(headingCellStyle);
//			rowCount += 1;

			colCount = 0;
			row = sheet.createRow(rowCount);

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 10));
			cell.setCellValue("");
			cell.setCellStyle(columnStyle);
//			colCount += 10;		

			for (int i = 1; i <= 11; i++) {
				cell = ReportFormats.cell(row, i, "", columnStyle);
				colCount++;
			}

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 3));
			cell.setCellValue("Sales Product (SKU)");
			cell.setCellStyle(columnStyle);
			colCount += 4;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("");
			cell.setCellStyle(columnStyle);
			colCount++;

			cell = ReportFormats.cell(row, colCount, "", columnStyle);
			colCount++;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 2));
			cell.setCellValue("Articles Requested");
			cell.setCellStyle(columnStyle);
			colCount += 3;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("Articles Supplied");
			cell.setCellStyle(columnStyle);
			colCount += 2;

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 7));
			cell.setCellValue("Dispatch Details");
			cell.setCellStyle(columnStyle);
			colCount += 8;

			System.out.println("::::::::::::" + colCount);

			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 1));
			cell.setCellValue("");
			cell.setCellStyle(columnStyle);
			cell = ReportFormats.cell(row, colCount + 1, "", columnStyle);
//			colCount++;
//
//			cell = row.createCell(colCount);
//			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, colCount, colCount));
//			cell.setCellValue("Ship To " + "" + "" + "State");
//			cell.setCellStyle(columnStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);
			colCount = 0;

//			for (int i = 0; i < 5; i++) {
//				cell = row.createCell(colCount);
//				cell.setCellStyle(columnStyle);
//				colCount++;
//			}

			for (String heading : headings) {

				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);

//				if (heading.equals("Bill Value")) {
//					colCount += 2;
//				} else {
				colCount++;
//				}
			}

			sheet.groupRow(0, 2);
			sheet.createFreezePane(0, 5);

			rowCount += 1;

			Long oldReq = 0l;
			Long newReq = 0l;
			String oldProdCd = null;
			String newProdCd = null;
			String oldInv = list.get(0).getInvoice_no();
			String newInv = null;
			int rowCountForCondition = 1;

			for (New_Stockist_wiseScheme_request_model obj : list) {

				newReq = obj.getArticle_req_id();
				newProdCd = obj.getArticle_prod_cd();
				newInv = obj.getInvoice_no();

				if (bean.getPending_qty().equals("Y") ) {

					row = sheet.createRow(rowCount);
					colCount = 0;

					cell = ReportFormats.cell(row, colCount, obj.getROW().toString(),
							ReportFormats.setCellAlignment(workbook, ALIGN_CENTER));
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getLoc_nm(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getInvoice_no(), null);
					colCount++;

					if (obj.getInvoice_date().trim().equals("")) {
						cell = ReportFormats.cell(row, colCount, "", null);
					} else {
						cell = ReportFormats.cellDate(row, colCount,
								MedicoResources.getDateformatYYYYDDMMHH(obj.getInvoice_date()), dateStyle);
					}
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getRequest_no(), null);
					colCount++;

					if (obj.getArticle_req_date().trim().equals("")) {
						cell = ReportFormats.cell(row, colCount, "", null);
					} else {
						cell = ReportFormats.cellDate(row, colCount,
								MedicoResources.getDateformatYMD(obj.getArticle_req_date()), dateStyle);
					}
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getCust_name_shipto(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getCust_code_shipto(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getDestination(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getShipto_state_name(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getCust_name_billto(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getErp_cust_cd(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getDestination_billto(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount,
							obj.getSale_prod_code_sg() != null ? obj.getSale_prod_code_sg().toString() : "", null);
					colCount++;

					cell = ReportFormats.cell(row, colCount,
							obj.getSale_prod_name() != null ? obj.getSale_prod_name().toString() : "", null);
					colCount++;

					cell = ReportFormats.cellNumLong(row, colCount, obj.getSale_prod_qty_billed(),
							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
					colCount++;

					cell = ReportFormats.cellNum(row, colCount, Double.valueOf(obj.getSale_billed_value().toString()),
							cellAlignment);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_code(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
					colCount++;

					cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_reqd(),
							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
					colCount++;
					
					
			
					System.err.println("hhhhhhhh   "+obj.getAlloc_id());
	
					

					if( ! obj.getAlloc_id().equals( previousalloc_Id)  ||  
							
							 Long.valueOf(obj.getArticle_prod_id()) != previousProd_id) {
						
						
					
		
		
					       List<New_stockist_wise_scheme_request_supply_Detail_model> filteredData = detaillist.stream()
                                   .filter(data ->data.getAllocdtl_alloc_id().equals( obj.getAlloc_id()) 
                                		    && (data.getProd_id()==null ||  data.getProd_id().equals(obj.getArticle_prod_id()))
                                		    )
                                   .collect(Collectors.toList());
					       				    
					       
					       
					       System.err.println(filteredData.size());
					       
					       for(int i=0;i<filteredData.size();i++) {		
					    	   
					    	   
					    	   if(filteredData.get(i).getPending_qty().longValue()>0) {
					    		   
					   
					    	   if(i>0) {
					    		   
					    		   rowCount++;
					    		   row = sheet.createRow(rowCount);
					    		   
					    		   
					    		   int j=0;
					    		      for( j=0;j<21;j++) {
								    		   cell = ReportFormats.cell(row, j, "",
														null);
								    		   
					    		      }
					    		      colCount=j+1;
					    		      
					    	   }

					    	 
					    	   cell = ReportFormats.cellNumLong(row, colCount, filteredData.get(i).getQty_supplied(),
										ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
								colCount++;

								cell = ReportFormats.cellNumLong(row, colCount, filteredData.get(i).getPending_qty(),
										ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getDsp_challan_no(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getDsp_dt(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getDsp_lr_no(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getDsp_lr_dt(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getE_inv_no(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getE_inv_date(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getTrn_ewaybillno(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getTrn_ewaybilldt(), null);
								colCount++;
								
								
					       }
					      	   
					       }
					       
					
					       previousalloc_Id=  obj.getAlloc_id();
					       previousProd_id=Long.valueOf(obj.getArticle_prod_id());
					       
					       
					}
					
//					
//					cell = ReportFormats.cell(row, 33, obj.getAlloc_id(), null);	
//					cell = ReportFormats.cell(row, 34, obj.getArticle_prod_id(), null);	

					rowCount++;

					colCount++;

					oldReq = newReq;
					oldProdCd = newProdCd;
					oldInv = newInv;

				} else if ((bean.getPending_qty().equals("N"))) {

					row = sheet.createRow(rowCount);
					colCount = 0;

					cell = ReportFormats.cell(row, colCount, obj.getROW().toString(),
							ReportFormats.setCellAlignment(workbook, ALIGN_CENTER));
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getLoc_nm(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getInvoice_no(), null);
					colCount++;

					if (obj.getInvoice_date().trim().equals("")) {
						cell = ReportFormats.cell(row, colCount, "", null);
					} else {
						cell = ReportFormats.cellDate(row, colCount,
								MedicoResources.getDateformatYYYYDDMMHH(obj.getInvoice_date()), dateStyle);
					}
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getRequest_no(), null);
					colCount++;

					if (obj.getArticle_req_date().trim().equals("")) {
						cell = ReportFormats.cell(row, colCount, "", null);
					} else {
						cell = ReportFormats.cellDate(row, colCount,
								MedicoResources.getDateformatYMD(obj.getArticle_req_date()), dateStyle);
					}
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getCust_name_shipto(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getCust_code_shipto(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getDestination(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getShipto_state_name(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getCust_name_billto(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getErp_cust_cd(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getDestination_billto(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount,
							obj.getSale_prod_code_sg() != null ? obj.getSale_prod_code_sg().toString() : "", null);
					colCount++;

					cell = ReportFormats.cell(row, colCount,
							obj.getSale_prod_name() != null ? obj.getSale_prod_name().toString() : "", null);
					colCount++;

					cell = ReportFormats.cellNumLong(row, colCount, obj.getSale_prod_qty_billed(),
							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
					colCount++;

					cell = ReportFormats.cellNum(row, colCount, Double.valueOf(obj.getSale_billed_value().toString()),
							cellAlignment);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_name(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getTrd_scheme_code(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getArticle_prod_cd(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, obj.getArticle_name(), null);
					colCount++;

					cell = ReportFormats.cellNumLong(row, colCount, obj.getQty_reqd(),
							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
					colCount++;
					
					
			
					System.err.println("hhhhhhhh   "+obj.getAlloc_id());
	
					

					if( ! obj.getAlloc_id().equals( previousalloc_Id)  ||  
							
							 Long.valueOf(obj.getArticle_prod_id()) != previousProd_id) {
						
						
					
		
		
					       List<New_stockist_wise_scheme_request_supply_Detail_model> filteredData = detaillist.stream()
                                   .filter(data ->data.getAllocdtl_alloc_id().equals( obj.getAlloc_id()) 
                                		    && (data.getProd_id()==null ||  data.getProd_id().equals(obj.getArticle_prod_id()))
                                		    )
                                   .collect(Collectors.toList());
					       				    
					       
					       
					       System.err.println(filteredData.size());
					       
					       for(int i=0;i<filteredData.size();i++) {
					    	   
					    	
					    	   if(i>0) {
					    		   
					    		   rowCount++;
					    		   row = sheet.createRow(rowCount);
					    		   
					    		   
					    		   int j=0;
					    		      for( j=0;j<21;j++) {
								    		   cell = ReportFormats.cell(row, j, "",
														null);
								    		   
					    		      }
					    		      colCount=j+1;
					    		      
					    	   }
				
					    	   
					    	   
					    	   cell = ReportFormats.cellNumLong(row, colCount, filteredData.get(i).getQty_supplied(),
										ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
								colCount++;

								cell = ReportFormats.cellNumLong(row, colCount, filteredData.get(i).getPending_qty(),
										ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getDsp_challan_no(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getDsp_dt(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getDsp_lr_no(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getDsp_lr_dt(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getE_inv_no(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getE_inv_date(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getTrn_ewaybillno(), null);
								colCount++;

								cell = ReportFormats.cell(row, colCount, filteredData.get(i).getTrn_ewaybilldt(), null);
								colCount++;
									   
					       }
					       
					       
					       
					       
					       
					       
					       
					       
						
					
					       previousalloc_Id=  obj.getAlloc_id();
					       previousProd_id=Long.valueOf(obj.getArticle_prod_id());
					       
					       
					}
					
//					
//					cell = ReportFormats.cell(row, 33, obj.getAlloc_id(), null);	
//					cell = ReportFormats.cell(row, 34, obj.getArticle_prod_id(), null);	

					rowCount++;

					colCount++;

					oldReq = newReq;
					oldProdCd = newProdCd;
					oldInv = newInv;

				}

			}

			List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
			int count = 0;
			for (CellRangeAddress rangeAddress : mergedRegions) {
//				System.out.println("rangeAddress :: " + rangeAddress.toString());

				if (count > 3) {
					RegionUtil.setBorderTop(BorderStyle.THIN, rangeAddress, sheet);
					RegionUtil.setBorderLeft(BorderStyle.THIN, rangeAddress, sheet);
					RegionUtil.setBorderRight(BorderStyle.THIN, rangeAddress, sheet);
					RegionUtil.setBorderBottom(BorderStyle.THIN, rangeAddress, sheet);
				}
				count++;

			}

			/// Scheme description sheet starts here

			XSSFSheet sheet2 = workbook.createSheet("SCHEME DESCRIPTION");

			XSSFRow description_row = null;
			XSSFCell description_cell = null;
			int description_rowCount = 0;
			int description_colCount = 0;

			List<String> description_headings = new ArrayList<>();

			// creating heading
			description_headings.add("Sl No");
			description_headings.add("Location Name");
			description_headings.add("Sub Company Code");
			description_headings.add("Scheme Code");
			description_headings.add("Scheme Name");
			description_headings.add("Valid From Date");
			description_headings.add("Valid To Date");
			description_headings.add("Article Product Code");
			description_headings.add("Article Product Name");
			description_headings.add("Sale Product Code");
			description_headings.add("Sale Product Name");
			description_headings.add("Scheme Apply To");

			description_headings.add("Billed Value");
			description_headings.add("Article Qty Per Total Value");
			description_headings.add("Per Sale Qty Total");
			description_headings.add("Article Qty");

			description_row = sheet2.createRow(description_rowCount);
			description_cell = description_row.createCell(description_colCount);

			sheet2.addMergedRegion(
					new CellRangeAddress(description_rowCount, description_rowCount, description_colCount, 15));
			description_cell.setCellValue("SCHEME DESCRIPTION");
			description_cell.setCellStyle(headingCellStyle);
			description_rowCount += 1;

			description_colCount = 0;

			description_row = sheet2.createRow(description_rowCount);
			for (String heading : description_headings) {

				description_cell = description_row.createCell(description_colCount);

				description_cell = ReportFormats.cell(description_row, description_colCount, heading,
						columnHeadingStyle);
				description_colCount++;
			}

			description_rowCount += 1;
			description_colCount = 0;

			String applay_To_Value_Pervious = "";
			Long rowcount = 1L;
			for (Scheme_Desription_Bean description_bean : listOfSchemDescription) {

				description_row = sheet2.createRow(description_rowCount);
				description_colCount = 0;

				description_cell = ReportFormats.cell(description_row, description_colCount, rowcount.toString(), null);
				rowcount++;
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getLoc_nm().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getSub_comp_cd().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getTrd_scheme_code().toString(), null);
				description_colCount++;

//				description_cell = ReportFormats.cell(description_row, description_colCount,
//						description_bean.getTrd_scheme_name().toString(), null);

				description_cell = ReportFormats.cell(description_row, description_colCount,
						MedicoConstants.toCamelCase(description_bean.getTrd_scheme_name().toString()), null);

				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getValid_from_dt().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getValid_to_dt().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getArticle_prod_cd().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getArticle_name().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getSale_prod_code_sg().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getSale_prod_name().toString(), null);
				description_colCount++;

				description_cell = ReportFormats.cell(description_row, description_colCount,
						description_bean.getApply_to().toString(), null);
				description_colCount++;

				// only showing one time value and article qty and
				if (!applay_To_Value_Pervious.equals(description_bean.getBilled_value().toString())
						&& "V".equals(description_bean.getApply_to())) {

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getBilled_value().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getArticle_qty_per_tot_val().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));

					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getPer_sale_qty_tot().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getArticle_qty().toString()),
							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
				}

				// if value and article qty same adding blank row
				else if (applay_To_Value_Pervious.equals(description_bean.getBilled_value().toString())
						&& "V".equals(description_bean.getApply_to())) {

					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
					description_colCount++;

					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
					description_colCount++;

					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);
					description_colCount++;

					description_cell = ReportFormats.cell(description_row, description_colCount, "", null);

					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
				}

				// scheme apply to works normal
				else if ("Q".equals(description_bean.getApply_to())) {

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getBilled_value().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));

					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getArticle_qty_per_tot_val().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));

					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getPer_sale_qty_tot().toString()),
							ReportFormats.setCellAlignmentWithTwoDecimal(workbook, ALIGN_RIGHT));
					description_colCount++;

					description_cell = ReportFormats.cellNum(description_row, description_colCount,
							Double.valueOf(description_bean.getArticle_qty().toString()),
							ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT));
					applay_To_Value_Pervious = description_bean.getBilled_value().toString();
				}

				description_rowCount++;

			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return filename;
	}




	@Override
	public List<New_stockist_wise_scheme_request_supply_Detail_model> getDispatchDetailData() {
		// TODO Auto-generated method stub
		
		return article_Scheme_master_Repository.getDispatchDetailData();
	}

	@Override
	public String generateArticleSchemeReport(List<Artreq_To_Dispatch_Report_With_Param> list,
			ArticleSchemeReportBean bean, HttpSession session, List<Scheme_Desription_Bean> listOfSchemDescription)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
