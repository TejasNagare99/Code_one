package com.excel.service;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.StockWithdrawalBean;
import com.excel.exception.MedicoException;
import com.excel.model.Am_m_System_Parameter;
import com.excel.model.Company;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SWV_Header;
import com.excel.model.SmpItem;
import com.excel.model.Supplier;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.model.V_SWV_Detail;
import com.excel.model.V_SWV_Header;
import com.excel.repository.StockWithdrawalRepository;
import com.excel.repository.SystemParameterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Service
public class StockWithdrawalServiceImpl implements StockWithdrawalService, MedicoConstants {

	@Autowired
	StockWithdrawalRepository stockWithdrawalRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	SystemParameterRepository systemParameterRepository;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskMasterService taskMasterService;
	@Autowired CompanyMasterService compService;

	@Override
	public List<SmpItem> getProdListForSWV(Long loc_id) throws Exception {
		return stockWithdrawalRepository.getProdListForSWV(loc_id);
	}

	@Override
	public List<Supplier> getSuppliersBySupplierType(String supplierType, Long subCompId) throws Exception {
		return stockWithdrawalRepository.getSuppliersBySupplierType(supplierType, subCompId);
	}

	@Override
	public Supplier supplierAddrById(Long sup_id) throws Exception {
		return stockWithdrawalRepository.supplierAddrById(sup_id);
	}

	@Override
	public List<StockWithdrawalBean> getBatchDetailsByProdStkType(Long loc_id, String stk_type, Long prod_id)
			throws Exception {
		return stockWithdrawalRepository.getBatchDetailsByProdStkType(loc_id, stk_type, prod_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveStkWth(StockWithdrawalBean bean) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String appr_req = "";
		List<Am_m_System_Parameter> sysParaList = this.systemParameterRepository.getSpValueBySpName("SWV_APPR_REQ");
		if (sysParaList != null) {
			appr_req = sysParaList.get(0).getSp_value();
			bean.setAppr_req(appr_req);
		}

		for (int i = 0; i < bean.getBatchIds().size(); i++) {
			if (bean.getBatchWthQtys().get(i).compareTo(BigDecimal.ZERO) > 0) {
				bean.setpBatchId(bean.getBatchIds().get(i));
				bean.setpBatchQty(bean.getBatchWthQtys().get(i));
				bean.setpBatchRate(bean.getRates().get(i));
				bean.setpBatchCases(bean.getBatchCases().get(i));
				bean = this.stockWithdrawalRepository.saveSWV(bean);
			}
		}
		map.put("headerId", bean.getHeaderId());
		map.put("stkWithDrawalNo", bean.getStkWithdrawalNo());
		return map;
	}

	@Override
	public List<V_SWV_Detail> getSWVDetailsBySWVId(Long swv_id) throws Exception {
		return stockWithdrawalRepository.getSWVDetailsBySWVId(swv_id);
	}

	@Override
	public List<V_SWV_Header> getSWVListForModify(Long loc_id, String comp_cd, String emp_id, String user_type,
			String from_dt, String to_dt) throws Exception {
		return stockWithdrawalRepository.getSWVListForModify(loc_id, comp_cd, emp_id, user_type, from_dt, to_dt);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteStkWth(StockWithdrawalBean bean) throws Exception {
		boolean flag = false;
		SWV_Header swv_Header = this.stockWithdrawalRepository.getSWVHeaderById(bean.getHeaderId());
		List<V_SWV_Detail> swvDetails = stockWithdrawalRepository.getSWVDetailsBySWVId(bean.getHeaderId());
		for (V_SWV_Detail swv_Detail : swvDetails) {
			this.stockWithdrawalRepository.deleteSWVDetail(swv_Detail.getSwvdtl_company(),
					swv_Detail.getSwvdtl_fin_year(), swv_Detail.getSwvdtl_period_code(), bean.getHeaderId(),
					swv_Detail.getSwvdtl_prod_id(), swv_Detail.getSwvdtl_batch_id(), swv_Detail.getSwvdtl_stock_type(),
					bean.getEmpId(), bean.getIpAddress());
		}
		swv_Header.setSwv_status("I");
		swv_Header.setSwv_mod_usr_id(bean.getEmpId());
		swv_Header.setSwv_mod_ip_add(bean.getIpAddress());
		swv_Header.setSwv_mod_dt(new Date());
		this.transactionalRepository.update(swv_Header);
		flag = true;
		return flag;
	}

	@Override
	public V_SWV_Header getSWVHeaderDetailsById(Long swv_id) throws Exception {
		return stockWithdrawalRepository.getSWVHeaderDetailsById(swv_id);
	}

	@Override
	public List<StockWithdrawalBean> getModBatchDetailsByProdStkType(Long loc_id, String stk_type, Long prod_id,
			Long swv_id) throws Exception {
		return stockWithdrawalRepository.getModBatchDetailsByProdStkType(loc_id, stk_type, prod_id, swv_id);
	}

	@Override
	public List<SmpItem> getModProdListForSWV(Long loc_id, Long swv_id) throws Exception {
		return stockWithdrawalRepository.getModProdListForSWV(loc_id, swv_id);
	}

	@Override
	public List<SG_d_parameters_details> getStockTypeListByProd(Long swv_id, Long prod_id) throws Exception {
		return stockWithdrawalRepository.getStockTypeListByProd(swv_id, prod_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteSWVDetail(StockWithdrawalBean bean) throws Exception {
		List<V_SWV_Detail> swvDetails = stockWithdrawalRepository.getSWVDetailsBySWVId(bean.getHeaderId());
		swvDetails = swvDetails.stream().filter(value -> bean.getProductId().compareTo(value.getSwvdtl_prod_id()) == 0)
				.collect(Collectors.toList());
		System.out.println("swvDetails::" + swvDetails.size());
		int count = 0;
		for (V_SWV_Detail swv_Detail : swvDetails) {
			if (bean.getProductId().compareTo(swv_Detail.getSwvdtl_prod_id()) == 0) {
				this.stockWithdrawalRepository.deleteSWVDetail(swv_Detail.getSwvdtl_company(),
						swv_Detail.getSwvdtl_fin_year(), swv_Detail.getSwvdtl_period_code(), bean.getHeaderId(),
						bean.getProductId(), bean.getBatchIds().get(count), bean.getStockType(), bean.getEmpId(),
						bean.getIpAddress());
			}
			count++;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void stkWthSelfApproval(StockWithdrawalBean bean) throws Exception {
		Company cmp = this.compService.getCompanyDetails();
		for (int i = 0; i < bean.getHeaderIds().size(); i++) {
			SWV_Header swvHeader = this.stockWithdrawalRepository.getSWVHeaderById(bean.getHeaderIds().get(i));
			swvHeader.setSwv_appr_acq(0);
			swvHeader.setSwv_mod_usr_id(bean.getEmpId());
			swvHeader.setSwv_mod_ip_add(bean.getIpAddress());
			swvHeader.setSwv_mod_dt(new Date());
			swvHeader.setSwv_appr_status("F");
			this.transactionalRepository.update(swvHeader);

			// Activiti BPM Approval

			String stkWthApproval = "";
			if (cmp.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				stkWthApproval = this.taskMasterService.getTranTypeByLocIdAndApprovalType(swvHeader.getSwv_loc_id(),
						STOCK_WITHDRAW, swvHeader.getSwv_company().trim());
			} else {
				if (MedicoConstants.PFZ_LOC.contains(swvHeader.getSwv_loc_id())) {
					stkWthApproval = MedicoConstants.STK_WTH_APPR_PFZ;
				} else {
					stkWthApproval = MedicoConstants.STK_WTH_APPR_PIPL;
				}
			}
			if (stkWthApproval == null || stkWthApproval.isEmpty()) {
				throw new MedicoException("Could not find Approval Data !");
			}

			String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
			List<TaskMaster> masters = taskMasterService.getTask(null, null, null, stkWthApproval, null, null,
					TASK_FLOW, null, null);

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
			variables.put("tran_ref_id", bean.getHeaderIds().get(i));
			variables.put("tran_type", stkWthApproval);
			variables.put("inv_group", null);
			variables.put("loc_id", swvHeader.getSwv_loc_id());
			variables.put("cust_id", 0L);
			variables.put("fs_id", 0L);
			variables.put("tran_no", swvHeader.getSwv_challan_no());
			variables.put("company_code", bean.getCompanyCode());
			variables.put("totaltask", masters.size());
			variables.put("amount", BigDecimal.ZERO);
			variables.put("escalatetime", null);
			variables.put("fin_year_id", bean.getCurrFinYear());
			variables.put("stock_point_id", bean.getHeaderIds().get(i));
			variables.put("doc_type", "SA");
			variables.put("task_flow", TASK_FLOW);
			variables.put("remark", bean.getRemarkss().get(i));
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("stockWithdrawalApproval",
					variables);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalDispatchApproval(Long headerId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception {
		System.out.println("isapproved::" + isapproved);
		System.out.println("motivation::" + motivation);
		SWV_Header swvHeader = this.stockWithdrawalRepository.getSWVHeaderById(headerId);
		swvHeader.setSwv_appr_acq(1);
		swvHeader.setSwv_mod_usr_id(last_approved_by);
		swvHeader.setSwv_mod_dt(new Date());
		if (isapproved.equalsIgnoreCase("A")) {
			swvHeader.setSwv_appr_status("A");
		} else if (isapproved.equalsIgnoreCase("D")) {
			List<V_SWV_Detail> swvDetails = stockWithdrawalRepository.getSWVDetailsBySWVId(headerId);
			for (V_SWV_Detail swv_Detail : swvDetails) {
				this.stockWithdrawalRepository.deleteSWVDetail(swv_Detail.getSwvdtl_company(),
						swv_Detail.getSwvdtl_fin_year(), swv_Detail.getSwvdtl_period_code(), headerId,
						swv_Detail.getSwvdtl_prod_id(), swv_Detail.getSwvdtl_batch_id(),
						swv_Detail.getSwvdtl_stock_type(), last_approved_by, swvHeader.getSwv_ins_ip_add());
			}
			swvHeader.setSwv_status("I");
			swvHeader.setSwv_appr_status("D");
		}
		this.transactionalRepository.update(swvHeader);
	}

	@Override
	public List<Object> getChallanForLr(Long loc_id) throws Exception {
		return stockWithdrawalRepository.getChallanForLr(loc_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void stkWthLr(StockWithdrawalBean bean) throws Exception {
		for (int i = 0; i < bean.getHeaderIds().size(); i++) {
			SWV_Header swvHeader = this.stockWithdrawalRepository.getSWVHeaderById(bean.getHeaderIds().get(i));
			swvHeader.setSwv_lr_no(bean.getLrnumber());
			swvHeader.setSwv_lr_dt(Utility.convertStringtoDate(bean.getLrdate()));
			swvHeader.setSwv_transporter(bean.getTransporter());
			swvHeader.setSwv_cases(
					bean.getCasess() == null ? BigDecimal.ZERO.intValue() : bean.getCasess().get(i).intValue());
			swvHeader.setSwv_wt(bean.getWeights() == null ? BigDecimal.ZERO : bean.getWeights().get(i));
			swvHeader.setSwv_totwt(bean.getNetWeight() == null ? BigDecimal.ZERO : bean.getNetWeight());
			swvHeader.setSwv_mod_usr_id(bean.getEmpId());
			swvHeader.setSwv_mod_dt(new Date());
			swvHeader.setSwv_mod_ip_add(bean.getIpAddress());
			swvHeader.setSwv_delivery_date(Utility.convertStringtoDate(bean.getLrdate()));
			this.transactionalRepository.update(swvHeader);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void stkWthFileUpload(List<MultipartFile> file, String headerId) throws Exception {
		SWV_Header swvHeader = this.stockWithdrawalRepository.getSWVHeaderById(Long.valueOf(headerId));

		for (int i = 0; i < file.size(); i++) {
			MultipartFile filedetails = file.get(i);
			if (filedetails.getSize() != 0l) {
				String path = MedicoConstants.STK_WTH_FILE_PATH;
				File f = new File(path);
				if (!f.exists()) {
					if (f.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
				String extension = filedetails.getOriginalFilename().substring(
						filedetails.getOriginalFilename().lastIndexOf("."), filedetails.getOriginalFilename().length());
				String starting = filedetails.getOriginalFilename().substring(0,
						filedetails.getOriginalFilename().lastIndexOf("."));
				path = path + starting + "_" + headerId + i + extension;
				byte[] bytes = filedetails.getBytes();
				Path path1 = Paths.get(path);
				Files.write(path1, bytes);
				System.out.println("File uploaded successfully");
				if (i == 0) {
					swvHeader.setSwv_img1(starting + "_" + headerId + i + extension);
				} else {
					swvHeader.setSwv_img2(starting + "_" + headerId + i + extension);
				}
			}
		}
		this.transactionalRepository.update(swvHeader);

	}

	@Override
	public SWV_Header getSWVHeaderById(Long swv_id) throws Exception {
		return stockWithdrawalRepository.getSWVHeaderById(swv_id);
	}

}
