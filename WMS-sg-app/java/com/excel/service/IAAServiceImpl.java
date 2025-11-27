package com.excel.service;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.IAABean;
import com.excel.exception.MedicoException;
import com.excel.model.Am_m_System_Parameter;
import com.excel.model.BatchWithholdReleaseDTL;
import com.excel.model.BatchWithholdReleaseHDR;
import com.excel.model.Company;
import com.excel.model.EmailFrom;
import com.excel.model.IaaDetailForApprovalMail;
import com.excel.model.IaaDetailForMail;
import com.excel.model.Iaa_Map;
import com.excel.model.Stock_Adj_Details;
import com.excel.model.Stock_Adj_Header;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.model.Trans_key_Master_batch_wthrel;
import com.excel.model.Usermaster;
import com.excel.repository.IAARepository;
import com.excel.repository.SystemParameterRepository;
import com.excel.repository.TransKeyMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;

@Service
public class IAAServiceImpl implements IAAService, MedicoConstants {
	@Autowired EmailTranwiseHelpService emailHelpService;
	@Autowired
	IAARepository iAARepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	SystemParameterRepository systemParameterRepository;
	@Autowired
	TransKeyMasterRepository transKeyMasterRepository;
	@Autowired
	TaskMasterService taskMasterService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired EmailTranWiseService emailtranwiseservice;
	@Autowired EmailService emailService;
	@Autowired UserMasterService usermasterservice;
	@Autowired CompanyMasterService compService;
	@Autowired private EntityManagerFactory emf;
	
	@Override
	public List<Iaa_Map> getReasonsFromIaaMap() throws Exception {
		return iAARepository.getReasonsFromIaaMap();
	}

	@Override
	public Object getIaaDetailById(Long reasonId) throws Exception {
		return iAARepository.getIaaDetailById(reasonId);
	}

	@Override
	public List<Object> getProductListWithPack(String comp_cd, long sub_comp_id, String prefix, String prod_type,
			long prod_grp, long div_id) throws Exception {
		return iAARepository.getProductListWithPack(comp_cd, sub_comp_id, prefix, prod_type, prod_grp, div_id);
	}

	@Override
	public Object getQtyAndValueNS(Long prod_id, String flag, Long loc_id, Long batch_id, String stock_type)
			throws Exception {
		return iAARepository.getQtyAndValueNS(prod_id, flag, loc_id, batch_id, stock_type);
	}

	@Override
	public Object getQtyAndValue(Long prod_id, String flag, Long loc_id, Long batch_id) throws Exception {
		return iAARepository.getQtyAndValue(prod_id, flag, loc_id, batch_id);
	}

	@Override
	public Object getQuarantineQtyAndValue(Long prod_id, String flag, Long loc_id, Long batch_id) throws Exception {
		return iAARepository.getQuarantineQtyAndValue(prod_id, flag, loc_id, batch_id);
	}

	@Override
	public List<Object> getBatchListForIAA(Long loc_id, Long prod_id, String stock_type,String outstock_type, String status)
			throws Exception {
		return iAARepository.getBatchListForIAA(loc_id, prod_id, stock_type,outstock_type, status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveIaaEntry(IAABean bean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<IaaDetailForMail> list = new ArrayList<IaaDetailForMail>();
		Object res_obj = null;
		String appr_req = "";
		
		
		List<Am_m_System_Parameter> sysParaList = this.systemParameterRepository.getSpValueBySpName("STKADJ_APPR_REQ");
		if (sysParaList != null) {
			appr_req = sysParaList.get(0).getSp_value();
			bean.setAppr_req(appr_req);
		}

		if (bean.getHeaderId().compareTo(0l) == 0) {
			res_obj = this.iAARepository.saveStockAdjHeader(bean);
			Object[] rObj = (Object[]) res_obj;
			bean.setHeaderId(Long.valueOf(rObj[0].toString()));
			bean.setStkAdjNo(rObj[1].toString());

			Trans_key_Master_batch_wthrel trans_key_master = this.transKeyMasterRepository
					.getTranLastNo(bean.getSendLocId(), bean.getSendLocId(), STK_TRF_NS, bean.getCurrFinYear(), BWT);
			if (trans_key_master == null)
				throw new MedicoException("Trans Key Master not mapped");

			long currno = Long.parseLong(trans_key_master.getLast_num());
			String trans_no = trans_key_master.getPrefix().trim() + bean.getCurrFinYear().substring(2)
					+ String.format("%06d", currno);
			bean.setTrans_no(trans_no);

			trans_key_master.setLast_num(String.format("%015d", currno + 1l));
			transactionalRepository.update(trans_key_master);
			bean.setWh_tran_id(this.saveBatchWithHoleHeader(bean));

			if(bean.getEmailreqInd().equals(Y)) {
				emailtranwiseservice.DeleteEmailTranwiseByTranId(IAA, bean.getHeaderId(), bean.getCurrFinYear());
				emailtranwiseservice.saveEmailTranWise(bean.getEmailList(), IAA, bean.getHeaderId(), bean.getCurrFinYear());
				this.emailHelpService.saveMailsToHelpTable(bean.getEmailList(), IAA, bean.getCurrFinYear());
			}
			
			map.put("headerId", bean.getHeaderId());
			map.put("stkAdjNo", bean.getStkAdjNo());
			map.put("wh_tran_id", bean.getWh_tran_id());
			map.put("trans_no", bean.getTrans_no());
		}
		bean.setStock_cfa(true);
		bean.setAction("");
		bean.setAdjDtlId(0l);
		bean.setBatchInd("N");
		bean.setExpInd("N");
		Long adjDtlId = this.iAARepository.saveStockAdjDetail(bean);
		bean.setAdjDtlId(adjDtlId);
		this.saveBatchWithHodlDtl(bean);

		//IAA Mail
		list = this.getIaaDetailForMail(bean.getSendLocId(), bean.getHeaderId(), bean.getHeaderId());
		
		SendMail sm = new SendMail();
		Usermaster um = usermasterservice.getUserByEmpId(bean.getEmpId());
		String mailContent= sm.SendIAACreationMail(list,um.getLd_lgnid());
		EmailFrom mailconfig = emailService.getEmailObject("EMAILFROM");
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(() -> {
			
			try {
				SendMail.sendHtmlMail(bean.getEmailList(), "IAA Created", mailContent,
					//	user.getLd_email(), PasswordManager.decrypt(PasswordManager.decrypt(user.getLd_pass_ang())),
						mailconfig.getEmail(),mailconfig.getMail_password(),mailconfig.isAuth(),
						mailconfig.isTls_sls(),mailconfig.getHost(),mailconfig.getPort());
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		});
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long saveBatchWithHoleHeader(IAABean bean) throws Exception {
		BatchWithholdReleaseHDR withholdHdr = new BatchWithholdReleaseHDR();
		withholdHdr.setLoc_id(bean.getSendLocId());
		withholdHdr.setFin_year_id(bean.getCurrFinYear());
		withholdHdr.setCompany_cd(bean.getCompanyCode());
		withholdHdr.setTran_no(bean.getTrans_no());
		withholdHdr.setTran_date(new Date());
		withholdHdr.setTran_type(STK_TRF_NS);
		withholdHdr.setStock_point_id(bean.getSendLocId());
		withholdHdr.setGrn_quarantine("N");
		withholdHdr.setRef_tran_id(bean.getHeaderId());
		withholdHdr.setRef_tran_no(bean.getStkAdjNo());
		withholdHdr.setRef_tran_type(IAA);
		withholdHdr.setBwthrel_hdr_ins_usr_id(bean.getEmpId());
		withholdHdr.setBwthrel_hdr_ins_dt(new Date());
		withholdHdr.setBwthrel_hdr_ins_ip_add(bean.getIpAddress());
		withholdHdr.setBwthrel_hdr_status("A");
		this.transactionalRepository.persist(withholdHdr);
		return withholdHdr.getTran_id();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveBatchWithHodlDtl(IAABean bean) throws Exception {
		BatchWithholdReleaseDTL withholdDetail = new BatchWithholdReleaseDTL();
		withholdDetail.setTran_id(bean.getWh_tran_id());
		withholdDetail.setLoc_id(bean.getSendLocId());
		withholdDetail.setFin_year_id(Long.valueOf(bean.getCurrFinYear()));
		withholdDetail.setCompany_cd(bean.getCompanyCode());
		withholdDetail.setTran_date(new Date());
		withholdDetail.setProd_id(bean.getSetRemoveProdId());
		withholdDetail.setBatch_id(bean.getSetRemoveBatchId());
		withholdDetail.setRate_nr(bean.getRemoveRate());
		withholdDetail.setQty(bean.getRemoveQty());
		withholdDetail.setReason_id(0L);
		withholdDetail.setTran_type(STK_TRF_NS);
		withholdDetail.setTran_no(bean.getTrans_no());
		withholdDetail.setStock_point_id(bean.getSendLocId());
		withholdDetail.setRef_tran_dtl_id(bean.getAdjDtlId());
		withholdDetail.setBwthrel_dtl_ins_usr_id(bean.getEmpId());
		withholdDetail.setBwthrel_dtl_ins_dt(new Date());
		withholdDetail.setBwthrel_dtl_status("A");
		withholdDetail.setBwthrel_dtl_ins_ip_add(bean.getIpAddress());
		this.transactionalRepository.persist(withholdDetail);
	}

	@Override
	public List<Object> getStockAdjDetailsByHeaderId(Long header_id) throws Exception {
		return iAARepository.getStockAdjDetailsByHeaderId(header_id);
	}

	@Override
	public List<Object> getIAAHeaderList(String comp_cd, Long fin_yr, String period_cd,String empId) throws Exception {
		return iAARepository.getIAAHeaderList(comp_cd, fin_yr, period_cd,empId);
	}

	@Override
	public List<Object> getStockAdjDtlByHdrForApproval(Long header_id, int appr_level, String appr_status)
			throws Exception {
		return iAARepository.getStockAdjDtlByHdrForApproval(header_id, appr_level, appr_status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void iaaSelfApproval(IAABean bean) throws Exception {
		// int
		// stk_appr_req=this.iAARepository.getSTKADJ_APPR_REQ(bean.getStkAdjHeaderId());
		Stock_Adj_Header header = this.iAARepository.getObjectById(bean.getStkAdjHeaderId());
		List<Object> stkDetails = this.iAARepository.getStockAdjDetailsByHeaderId(bean.getStkAdjHeaderId());
		Company cmp = this.compService.getCompanyDetails();
		header.setStkadj_appr_status(bean.getStatus());
		this.transactionalRepository.update(header);

		for (Object oo : stkDetails) {
			Object[] obj = (Object[]) oo;
			if(!((Character) obj[10]).equals('A')) {
				Stock_Adj_Details detail = this.iAARepository.getDetailById(Long.valueOf((Integer) obj[0]));
				detail.setStkadj_appr_status(bean.getStatus());
				this.transactionalRepository.update(detail);
			}
		}

		if (bean.getStatus().equalsIgnoreCase("F")) {
			String iaaApproval = "";
			if(cmp.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				iaaApproval = this.taskMasterService.getTranTypeByLocIdAndApprovalType(header.getStkadj_loc_id(),
						IAA,header.getStkadj_company());
			}
			else {
				if (MedicoConstants.PFZ_LOC.contains(header.getStkadj_loc_id())) {
					iaaApproval = IAA_APPR_PFZ;
				} else {
					iaaApproval = IAA_APPR_PIPL;
				}
			}
			
			if (iaaApproval == null || iaaApproval.isEmpty()) {
				throw new MedicoException("Could not find Approval Data !");
			}
			
			String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
			List<TaskMaster> masters = taskMasterService.getTask(null, null, null, iaaApproval, null, null, TASK_FLOW,null, null);

			if (masters.size() == 0) {
				throw new MedicoException("Task master record not found");
			}
			TaskMaster taskMaster = masters.get(0);
			List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
			if (task_Master_Dtls.size() == 0) {
				throw new MedicoException("Task master detail record not found");
			}

			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("initiator", bean.getEmpId());
			variables.put("initiator_desc", bean.getEmpId());
			variables.put("initiator_email", bean.getEmail());
			variables.put("tran_ref_id", bean.getStkAdjHeaderId());
			variables.put("tran_type", iaaApproval);
			variables.put("inv_group", null);
			variables.put("loc_id", header.getStkadj_loc_id());
			variables.put("cust_id", 0L);
			variables.put("fs_id", 0L);
			variables.put("tran_no", header.getStkadj_no());
			variables.put("company_code", bean.getCompanyCode());
			variables.put("totaltask", masters.size());
			variables.put("amount", BigDecimal.ZERO);
			variables.put("escalatetime", null);
			variables.put("fin_year_id", bean.getCurrFinYear());
			variables.put("stock_point_id", header.getStkadj_loc_id());
			variables.put("doc_type", "SA");
			variables.put("task_flow", TASK_FLOW);
			variables.put("remark", bean.getHeaderRemarkAppr());
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("iaaApproval", variables);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BatchWithholdReleaseHDR saveBatchWithHoldHeaderForIAAAppr(IAABean bean, Stock_Adj_Header header)
			throws Exception {
		BatchWithholdReleaseHDR withholdHdr = new BatchWithholdReleaseHDR();
		withholdHdr.setLoc_id(header.getStkadj_loc_id());
		withholdHdr.setFin_year_id(header.getStkadj_fin_year());
		withholdHdr.setCompany_cd(header.getStkadj_company());
		withholdHdr.setTran_no(bean.getTrans_no());
		withholdHdr.setTran_date(new Date());
		withholdHdr.setTran_type("87");
		withholdHdr.setStock_point_id(header.getStkadj_loc_id());
		withholdHdr.setGrn_quarantine("N");
		withholdHdr.setRef_tran_id(header.getStkadj_id());
		withholdHdr.setRef_tran_no(header.getStkadj_no());
		withholdHdr.setRef_tran_type(IAA);
		withholdHdr.setBwthrel_hdr_ins_usr_id(bean.getEmpId());
		withholdHdr.setBwthrel_hdr_ins_dt(new Date());
		withholdHdr.setBwthrel_hdr_ins_ip_add(bean.getIpAddress());
		withholdHdr.setBwthrel_hdr_status("A");
		this.transactionalRepository.persist(withholdHdr);
		return withholdHdr;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveBatchWithHodlDtlForIAAAppr(IAABean bean, Object[] stkDtl, BatchWithholdReleaseHDR withholdHdr)
			throws Exception {
		BatchWithholdReleaseDTL withholdDetail = new BatchWithholdReleaseDTL();
		withholdDetail.setTran_id(withholdHdr.getTran_id());
		withholdDetail.setLoc_id(withholdHdr.getLoc_id());
		withholdDetail.setFin_year_id(Long.valueOf(bean.getCurrFinYear()));
		withholdDetail.setCompany_cd(bean.getCompanyCode());
		withholdDetail.setTran_date(new Date());
		withholdDetail.setProd_id(new Long(stkDtl[0].toString()));
		withholdDetail.setBatch_id(new Long(stkDtl[1].toString()));
		withholdDetail.setRate_nr(new BigDecimal(stkDtl[2].toString()));
		withholdDetail.setQty(new BigDecimal(stkDtl[3].toString()).negate());
		withholdDetail.setReason_id(0L);
		withholdDetail.setTran_type("87");
		withholdDetail.setTran_no(withholdHdr.getTran_no());
		withholdDetail.setStock_point_id(withholdHdr.getLoc_id());
		withholdDetail.setRef_tran_dtl_id(bean.getDetailId());
		withholdDetail.setBwthrel_dtl_ins_usr_id(bean.getEmpId());
		withholdDetail.setBwthrel_dtl_ins_dt(new Date());
		withholdDetail.setBwthrel_dtl_status("A");
		withholdDetail.setBwthrel_dtl_ins_ip_add(bean.getIpAddress());
		this.transactionalRepository.persist(withholdDetail);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void iaaFinalApproval(IAABean bean) throws Exception {
		for (int i = 0; i < bean.getAdjDtlIds().size(); i++) {
			Stock_Adj_Details detail = this.iAARepository.getDetailById(Long.valueOf(bean.getAdjDtlIds().get(i)));
			detail.setStkadj_appr_status(bean.getAdjDtlStatus().get(i));
			this.transactionalRepository.update(detail);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,noRollbackForClassName = {"MedicoException"})
	public void finalApproval(Long stkAdjHeaderId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception {
		IAABean bean = new IAABean();
		Integer outpara=2;
		Stock_Adj_Header header=null;
		List<Object> stkDetails =null;
		try {
		bean.setStkAdjHeaderId(stkAdjHeaderId);
		bean.setEmpId(last_approved_by);
		bean.setCompanyCode(comp_cd);
		header = this.iAARepository.getObjectById(bean.getStkAdjHeaderId());
		stkDetails = this.iAARepository.getStockAdjDetailsByHeaderId(bean.getStkAdjHeaderId());
		bean.setCurrFinYear(header.getStkadj_fin_year());
		
		System.out.println("Header Appr Status : "+isapproved);
		bean.setIpAddress(header.getStkadj_ins_ip_add());
		for (Object oo : stkDetails) {
			Object[] obj = (Object[]) oo;
			if(!((Character) obj[10]).equals('A')) {
				outpara = this.iAARepository.saveStockAdjFinalApproval(Long.valueOf((Integer) obj[0]),isapproved, bean.getEmpId(),
						bean.getIpAddress());
			//important Note Don't Remove
			//As Discussed with Uday Sir IAA will be full Approve Or Full Discard.(Changed From Detail Level because it was not implemented correctly and required activiti to change so they suggested to keep it All or Nothing) --6 Jun 2023 -- Samruddha
			System.out.println("OutPara ::: "+outpara);
			
			if(outpara==0) {
				break;
			}
			}
		}
		
		if(outpara==0) {
			throw new MedicoException("Please Contact Administrator. Approval Not Completed, Error Occured.");
		}
		header = this.iAARepository.getObjectById(bean.getStkAdjHeaderId());
		header.setStkadj_mod_dt(new Date());
		header.setStkadj_mod_user_id(last_approved_by);
		header.setStkadj_appr_status(isapproved);
		this.transactionalRepository.update(header);

		//for (Object oo : stkDetails) {
		//	Object[] obj = (Object[]) oo;
			// detail = this.iAARepository.getDetailById(Long.valueOf((Integer) obj[0]));
			//detail.setStkadj_appr_status(isapproved);
			//this.transactionalRepository.update(detail);
		//}
		Trans_key_Master_batch_wthrel trans_key_master = this.transKeyMasterRepository
				.getTranLastNo(header.getStkadj_loc_id(), header.getStkadj_loc_id(), "87", bean.getCurrFinYear(), BWT);
		if (trans_key_master == null)
			throw new MedicoException("Trans Key Master not mapped");

		long currno = Long.parseLong(trans_key_master.getLast_num());
		String trans_no = trans_key_master.getPrefix().trim() + bean.getCurrFinYear().substring(2)
				+ String.format("%06d", currno);
		bean.setTrans_no(trans_no);

		trans_key_master.setLast_num(String.format("%015d", currno + 1l));
		transactionalRepository.update(trans_key_master);
		BatchWithholdReleaseHDR wtHoldHeader = this.saveBatchWithHoldHeaderForIAAAppr(bean, header);

		for (Object oo : stkDetails) {
			Object[] obj = (Object[]) oo;
			if(!((Character) obj[10]).equals('A')) {
				bean.setDetailId(Long.valueOf((Integer) obj[0]));
				Object ob = this.iAARepository.getStockAdjDetailsByDetId(bean.getDetailId());
				Object[] stkDtl = (Object[]) ob;
				this.saveBatchWithHodlDtlForIAAAppr(bean, stkDtl, wtHoldHeader);
			}
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			EntityManager em =  emf.createEntityManager();
			EntityTransaction tx =em.getTransaction();
			tx.begin();
			header.setStkadj_appr_status("E");
			em.merge(header);
			for (Object oo : stkDetails) {
				Object[] obj = (Object[]) oo;
				
				Stock_Adj_Details detail = this.iAARepository.getDetailById(Long.valueOf((Integer) obj[0]));
				if(!detail.getStkadj_appr_status().equals("A")) {
					detail.setStkadj_appr_status("E");
					em.merge(detail);
				}
			}
			tx.commit();
			
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void iaaApprUpload(List<MultipartFile> file, String headerId) throws Exception {
		Stock_Adj_Header header = this.iAARepository.getObjectById(Long.valueOf(headerId));
		for(int i=0;i<file.size();i++) {
			MultipartFile filedetails=file.get(i);
			if(filedetails.getSize()!=0l) {
			String path=MedicoConstants.IAA_FILE_PATH;
			File f=new File(path);
			if(!f.exists()) {
				if(f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			String extension = filedetails.getOriginalFilename().substring(filedetails.getOriginalFilename().lastIndexOf("."), filedetails.getOriginalFilename().length());
			String starting = filedetails.getOriginalFilename().substring(0, filedetails.getOriginalFilename().lastIndexOf("."));
			path=path+starting+"_"+headerId+i+extension;
			byte[] bytes=filedetails.getBytes();
			Path path1 = Paths.get(path);
			Files.write(path1, bytes);
			System.out.println("File uploaded successfully");
			header.setIaa_img(starting+"_"+headerId+i+extension);
			}
		} 
		this.transactionalRepository.update(header);
		
	}

	@Override
	public Stock_Adj_Header getObjectById(Long headerId) throws Exception {
		return iAARepository.getObjectById(headerId);
	}

	@Override
	public List<IaaDetailForMail> getIaaDetailForMail(Long locid, Long frmid, Long toid) throws Exception {
		// TODO Auto-generated method stub
		return iAARepository.getIaaDetailForMail(locid, frmid, toid);
	}

	@Override
	public List<IaaDetailForApprovalMail> getIaaDetailForApprovalMail(Long frmid, Long toid) throws Exception {
		// TODO Auto-generated method stub
		return iAARepository.getIaaDetailForApprovalMail(frmid, toid);
	}

	

}
