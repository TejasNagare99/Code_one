package com.excel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.Alloc_Gen_Param;
import com.excel.bean.AllocationBean;
import com.excel.bean.AllocationUploadBean;
import com.excel.bean.Prod_MasterBean;
import com.excel.bean.UploadMsrAllocBean;
import com.excel.exception.MedicoException;
import com.excel.model.ActivityNotification;
import com.excel.model.AllocConHd;
import com.excel.model.Alloc_Gen_Trg;
import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_Temp_Header;
import com.excel.model.Alloc_gen_dt;
import com.excel.model.Alloc_gen_ent;
import com.excel.model.Alloc_gen_hd;
import com.excel.model.AnnualAllocationPreviousYearData;
import com.excel.model.AnnualPlanPreviousYearCount;
import com.excel.model.AspDetail;
import com.excel.model.AspHeader;
import com.excel.model.AspHeaderArc;
import com.excel.model.Company;
import com.excel.model.DispatchDetailReportRevisedMdmNo;
import com.excel.model.DivMaster;
import com.excel.model.FieldStaff;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SmpItem;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.model.Team;
import com.excel.model.V_Dispatch_Period;
import com.excel.repository.AllocConsolidationRepository;
import com.excel.repository.AllocDetailRepository;
import com.excel.repository.AllocHeaderRepository;
import com.excel.repository.AllocTempArcRepository;
import com.excel.repository.AllocTempRepository;
import com.excel.repository.AllocationGenHeaderRepository;
import com.excel.repository.AllocationRepository;
import com.excel.repository.DashBoardRepository;
import com.excel.repository.DivMasterRepository;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.ParameterRepository;
import com.excel.repository.PeriodMasterRepository;
import com.excel.repository.ProductMasterRepository;
import com.excel.repository.TeamRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;
import com.excel.utility.SendMail;

@Service
public class AllocationServiceImpl implements AllocationService, MedicoConstants {

	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	AllocationRepository allocationRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	PeriodMasterRepository periodMasterRepository;
	@Autowired
	FieldStaffRepository fieldStaffRepository;
	@Autowired
	AllocationGenHeaderRepository allocationGenHeaderRepository;
	@Autowired
	TaskMasterService taskMasterService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	SendMail sendMail;
	@Autowired
	DashBoardRepository dashBoardRepository;
	@Autowired
	AllocHeaderRepository allocHeaderRepository;
	@Autowired
	LocationService locationService;
	@Autowired
	DivisionMasterService divisionMasterService;
	@Autowired
	AllocTempRepository allocTempRepository;
	@Autowired
	AllocTempService allocTempService;
	@Autowired
	ParameterRepository parameterRepository;
	@Autowired
	ProductMasterRepository productMasterRepository;
	@Autowired
	AllocDetailRepository allocDetailRepository;
	@Autowired
	DivMasterRepository divMasterRepository;
	@Autowired
	AllocTempArcRepository allocTempArcRepository;
	@Autowired
	AllocConsolidationRepository allocConsolidationRepository;
	@Autowired
	UserMasterRepository userMasterRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	CompanyMasterService companyMasterService;
	
	@Autowired ReportService reportService;


	@Override
	public AspDetail getObjectAspDetail(Long headerId) throws Exception {

		return this.allocationRepository.getObjectAspDetail(headerId);
	}

	@Override
	public List<Object> getTeamWithCount(String empId) throws Exception {
		// TODO Auto-generated method stub
		return allocationRepository.getTeamWithCount(empId);
	}

	@Override
	public List<Object> getPlanningType(String empId) throws Exception {
		// TODO Auto-generated method stub
		return allocationRepository.getPlanningType(empId);
	}

	@Override
	public boolean checkProductPresent(Long prodId, Long divId, String fin_year) {

		return allocationRepository.checkProductPresent(prodId, divId, fin_year);

	}

	@Override
	public List<Object> getSampleProductList(String divId, List<String> prodGroup, String prodType) throws Exception {
		// TODO Auto-generated method stub
		return allocationRepository.getSampleProductList(divId, prodGroup, prodType);
	}

	@Override
	public List<Object> getBrandsForTeam(String empId, String teamId, String planType,String subTeamId) throws Exception {
		// TODO Auto-generated method stub
		return allocationRepository.getBrandsForTeam(empId, teamId, planType,subTeamId);
	}

	@Override
	public List<AllocationBean> getSummaryDetailsAnnualPlan(Long aspId, String year, String status) throws Exception {

		return allocationRepository.getSummaryDetailsAnnualPlan(aspId, year, status);
	}

	@Override
	public List<Object> getPreviousYearPeriodWiseDetails(String prodId, String divId, String prodGroup, String prodType,
			String year) throws Exception {

		return allocationRepository.getPreviousYearPeriodWiseDetails(prodId, divId, prodGroup, prodType, year);
	}

	@Override
	public List<AllocationBean> getProductDetails(Long prod_id, String year) {

		return allocationRepository.getProductDetails(prod_id, year);
	}

	@Override
	public List<Object> getSampleProductListForAnnualPlan(String divId, List<String> prodGroup, String prodType,
			String allocNumber) throws Exception {

		return allocationRepository.getSampleProductListForAnnualPlan(divId, prodGroup, prodType, allocNumber);
	}

	@Override
	public List<AnnualPlanPreviousYearCount> getPreviousYearCount(Long prod_id, Long divId, String year)
			throws Exception {
		// TODO Auto-generated method stub
		return allocationRepository.getPreviousYearCountProcedure(divId, prod_id, year);
	}

	@Override
	public List<AllocationBean> getManagerListForAssistant(String asst_user_id) throws Exception {

		return allocationRepository.getManagerListForAssistant(asst_user_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteProductFromAnnualPlan(Long alloc_detail_id) throws Exception {

		this.allocationRepository.deleteProductFromAnnualPlan(alloc_detail_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveAnnualAllocation(AllocationBean bean) throws Exception {

		Map<String, Object> map = new HashMap<>();
		AspHeader aspHeader = null;
		System.out.println("Asp_id " + bean.getAsp_id() + bean.getAsp_dtl_id());
		if (bean.getAsp_dtl_id() == 0 || bean.getAsp_id() == 0) {
			boolean productExist = false;
			// =allocationRepository.checkProductPresent(bean.getProductId(),
			// bean.getTeamId(), bean.getAsp_fin_year());
			if (!productExist) {
				aspHeader = allocationRepository.getAspHeaderByManagerAndUserId(bean.getMgr_id(), bean.getTeamId(),bean.getAsp_fin_year(),bean.getSub_team_code());
				if (aspHeader == null) {
					aspHeader = this.saveAspHeader(bean);

					DivMaster div=this.divisionMasterService.getObjectById(aspHeader.getAsp_div_id());
					aspHeader.setAsp_alloc_number("AN-" + bean.getAsp_fin_year() + "-" + bean.getMgrEmpId() + "-"
							+ div.getDiv_code_nm() + "-" + aspHeader.getAsp_id());
					this.transactionalRepository.update(aspHeader);
					bean.setAsp_id(aspHeader.getAsp_id());
				} else {
					aspHeader.setAsp_mod_ip_add(bean.getIpAddress());
					aspHeader.setASP_mod_user_id(bean.getEmpId());
					aspHeader.setAsp_mod_dt(new Date());
					this.transactionalRepository.update(aspHeader);
					bean.setAsp_id(aspHeader.getAsp_id());
				}
				AspDetail detail = this.saveAspDetail(bean, null);
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE, "Saved Successfully");
			} else {
				map.put(DATA_SAVED, false);
				map.put(RESPONSE_MESSAGE, "Product is Already Entered");
			}
		} else {
			AspDetail detail = this.allocationRepository.getObjectAspDetail(bean.getAsp_dtl_id());
			this.saveAspDetail(bean, detail);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Saved Successfully");
		}
		map.put("aspHeader", aspHeader);
		map.put("aspHeader", aspHeader);

		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AspHeader saveAspHeader(AllocationBean bean) throws Exception {
		AspHeader header = null;
		header = new AspHeader();

		header.setAsp_brand_mgrId(bean.getMgr_id());
		header.setAsp_company_code(bean.getCompanyCode());
		header.setAsp_fin_year(bean.getAsp_fin_year());
		header.setAsp_userId(Long.valueOf(bean.getUserId()));
		header.setAsp_ins_user_id(bean.getEmpId());
		header.setASP_mod_user_id(null);
		header.setAsp_ins_dt(new Date());
		header.setAsp_mod_dt(null);
		header.setAsp_ins_ip_add(bean.getIpAddress());
		header.setAsp_mod_ip_add(null);
		header.setAsp_status("A");
		header.setAsp_appr_status("E");
		header.setAsp_div_id(bean.getTeamId());
		header.setAssis_appr_status("E");
		header.setMgr_emp_id(bean.getMgrEmpId());
		header.setTeam_code(bean.getSub_team_code());
		this.transactionalRepository.persist(header);

		return header;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AspDetail saveAspDetail(AllocationBean bean, AspDetail detail) throws Exception {
		if (detail == null) {
			detail = new AspDetail();
			detail.setAsp_cog(bean.getCog());
			detail.setAsp_div_id(bean.getTeamId());
			detail.setAsp_brand_id(bean.getBrandId());
			detail.setAsp_prod_type(bean.getPlanType());
			detail.setAsp_dtl_fin_year(bean.getAsp_fin_year());
			detail.setAsp_id(bean.getAsp_id());
			detail.setAsp_team_size(bean.getTeamSize());
			detail.setAsp_prod_id(bean.getProductId());
			detail.setAsp_prdqty01(bean.getCurrPerPso().get(0));
			detail.setAsp_prdqty02(bean.getCurrPerPso().get(1));
			detail.setAsp_prdqty03(bean.getCurrPerPso().get(2));
			detail.setAsp_prdqty04(bean.getCurrPerPso().get(3));
			detail.setAsp_prdqty05(bean.getCurrPerPso().get(4));
			detail.setAsp_prdqty06(bean.getCurrPerPso().get(5));
			detail.setAsp_prdqty07(bean.getCurrPerPso().get(6));
			detail.setAsp_prdqty08(bean.getCurrPerPso().get(7));
			detail.setAsp_prdqty09(bean.getCurrPerPso().get(8));
			detail.setAsp_prdqty10(bean.getCurrPerPso().get(9));
			detail.setAsp_prdqty11(bean.getCurrPerPso().get(10));
			detail.setAsp_prdqty12(bean.getCurrPerPso().get(11));
			detail.setAsp_ins_user_id(bean.getEmpId());
			detail.setASP_mod_user_id(null);
			detail.setAsp_ins_dt(new Date());
			detail.setAsp_mod_dt(null);
			detail.setAsp_ins_ip_add(bean.getIpAddress());
			detail.setAsp_mod_ip_add(null);
			detail.setAsp_status("A");
			detail.setAsp_appr_status("E");
			detail.setAsp_frequency(bean.getFrequency());
			this.transactionalRepository.persist(detail);
		} else {
			detail.setAsp_appr_status("E");
			detail.setAsp_prdqty01(bean.getCurrPerPso().get(0));
			detail.setAsp_prdqty02(bean.getCurrPerPso().get(1));
			detail.setAsp_prdqty03(bean.getCurrPerPso().get(2));
			detail.setAsp_prdqty04(bean.getCurrPerPso().get(3));
			detail.setAsp_prdqty05(bean.getCurrPerPso().get(4));
			detail.setAsp_prdqty06(bean.getCurrPerPso().get(5));
			detail.setAsp_prdqty07(bean.getCurrPerPso().get(6));
			detail.setAsp_prdqty08(bean.getCurrPerPso().get(7));
			detail.setAsp_prdqty09(bean.getCurrPerPso().get(8));
			detail.setAsp_prdqty10(bean.getCurrPerPso().get(9));
			detail.setAsp_prdqty11(bean.getCurrPerPso().get(10));
			detail.setAsp_prdqty12(bean.getCurrPerPso().get(11));
			detail.setASP_mod_user_id(bean.getEmpId());
			detail.setAsp_mod_dt(new Date());
			detail.setAsp_mod_ip_add(bean.getIpAddress());
			detail.setAsp_frequency(bean.getFrequency());
			this.transactionalRepository.update(detail);
		}

		return detail;

	}

	@Override
	public List<AllocationBean> getAnnualPlanEnteredProductDetail(Long divId, Long brandId, String year,
			String userId) {
		// TODO Auto-generated method stub
		return this.allocationRepository.getAnnualPlanEnteredProductDetail(divId, brandId, year, userId);
	}

	@Override
	public AspHeader getAspHeaderByManagerAndUserId(Long userId, Long teamId, String fin_year,String sub_team_code) throws Exception {
		// TODO Auto-generated method stub
		return allocationRepository.getAspHeaderByManagerAndUserId(userId, teamId, fin_year,sub_team_code);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void annualAllocationSelfApproval(Long aspId, String user) throws Exception {
		AspHeader header = this.allocationRepository.getObjectAspHeader(aspId);
		header.setAsp_appr_status("F");
		header.setAssis_appr_status("A");
		this.transactionalRepository.update(header);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendAnnualAllocationForApproval(Long aspId, String user, String email,
			String remark,HttpServletRequest request, HttpSession session) throws Exception {

		// String ip_addr = request.getRemoteAddr();
		System.out.println("user " + user);
		String tranType = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		AspHeader header =null;
		AllocConHd allocConHd =null;
		try {
			header=this.allocationRepository.getObjectAspHeader(aspId);
			if (header.getAsp_appr_status().equals("E") || header.getAsp_appr_status().equals("A")) {
				Location location = locationService.getLocationNameByEmployeeId(user);
//				if (MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
//					tranType = MedicoConstants.ANNUAL_APPR_PIPL;
//				} else {
//					tranType = MedicoConstants.ANNUAL_APPR_PFZ;
//				}
				// new alternate logic
				tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(),ANN_SAMPLE_PLAN,
						companyCode);
				if (tranType == null || tranType.isEmpty()) {
					throw new MedicoException("Could not find Approval Data !");
				}
				String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
				System.out.println("tranType " + tranType);
				List<TaskMaster> masters = taskMasterService.getTask(header.getAsp_div_id(), null, null, tranType, null,null, TASK_FLOW, null, null);

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
				variables.put("initiator_email", email);
				variables.put("tran_ref_id", header.getAsp_id());
				variables.put("tran_type", tranType);
				variables.put("inv_group", header.getAsp_div_id());
				variables.put("loc_id", 0L);
				variables.put("cust_id", 0L);
				variables.put("tran_no", header.getAsp_alloc_number());
				variables.put("company_code", companyCode);
				variables.put("totaltask", masters.size());
				variables.put("amount", BigDecimal.ZERO);
				variables.put("escalatetime", null);
				variables.put("fin_year_id", header.getAsp_fin_year());
				variables.put("stock_point_id", 0L);
				variables.put("doc_type",ANNUAL_ALLOCATION);
				variables.put("task_flow", TASK_FLOW);
				variables.put("remark", remark);
				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("annualAllocationApproval",variables);
				// changing approval status
				header.setAsp_appr_status("F");
				header.setAssis_appr_status("A");
				this.transactionalRepository.update(header);
				this.allocationRepository.approveAnnualAllocation("F", header.getAsp_id());
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		// sending mail after self Approval
		this.sendMail.sendMail(header.getAsp_id(), user, "selfApprove", null, "", "nextApproval",
				Long.valueOf(header.getAsp_fin_year()), tranType,companyCode,ANN_SAMPLE_PLAN);
	}

	// Called from Activity after final Approval
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalAnnualApproval(Long aspId, String last_approved_by, String comp_cd, String isapproved,String motivation) throws Exception {
		System.out.println("In final Approval");
		try {

			if (isapproved.equalsIgnoreCase("A")) {
				AspHeader header = this.allocationRepository.getObjectAspHeader(aspId);
				header.setAsp_appr_status("A");
				this.transactionalRepository.update(header);
				this.allocationRepository.approveAnnualAllocation("A", header.getAsp_id());
			} else if (isapproved.equalsIgnoreCase("D")) {
				AspHeader header = this.allocationRepository.getObjectAspHeader(aspId);
				header.setAsp_appr_status("E");
				this.transactionalRepository.update(header);
				this.allocationRepository.approveAnnualAllocation("E", header.getAsp_id());
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void discardAnnualAllocatio(Long aspId) throws Exception {

		AspHeader header = this.allocationRepository.getObjectAspHeader(aspId);
		header.setAsp_status("D");
		this.transactionalRepository.update(header);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void changeStatusForAssistant(Long aspId, String status) throws Exception {

		AspHeader header = this.allocationRepository.getObjectAspHeader(aspId);
		header.setAssis_appr_status(status);
		;
		this.transactionalRepository.update(header);
	}

	@Override
	public List<AnnualAllocationPreviousYearData> getPreviousYearDataAnnualPlan(String divId, String prodId,
			String year) throws Exception {

		return this.allocationRepository.getPreviousYearDataAnnualPlan(divId, prodId, year);
	}

	// ----------------------------------------------------------Monthly--------------------------------------
	@Override
	public AspHeaderArc getPreviousYearSamplePlan(Long userId, Long mgrId, String fin_year) throws Exception {

		return this.allocationRepository.getPreviousYearSamplePlan(userId, mgrId, fin_year);
	}

	@Override
	public List<Object> getPlanningTypeMonthly() throws Exception {

		return this.allocationRepository.getPlanningTypeMonthly();
	}

	@Override
	public List<Object> getProductListForMonthlyAllocation(String prod_sub_type_id, List<String> smp_sa_prod_group,
			String year) throws Exception {
		// TODO Auto-generated method stub
		return this.allocationRepository.getProductListForMonthlyAllocation(prod_sub_type_id, smp_sa_prod_group, year);
	}
	
	@Override
	public List<Object> getProductListForMonthlyAllocationPriviousYear(String prodType,List<String> smp_sa_prod_group,String discProd, String zeroStock, String div_id, String pmtTeam,
			 String period_code, String core, String fin_year,String isPrivious) throws Exception{
		return this.allocationRepository.getProductListForMonthlyAllocationPriviousYear(prodType, smp_sa_prod_group, discProd, zeroStock, div_id, pmtTeam, period_code, core, fin_year,isPrivious);
	}
	
	@Override
	public boolean checkEnetedBrandByProductType(String prod_sub_type_id, List<String> smp_sa_prod_group, String year,
			Long alloc_gen_id) throws Exception {
		// TODO Auto-generated method stub
		return this.allocationRepository.checkEnetedBrandByProductType(prod_sub_type_id, smp_sa_prod_group, year,
				alloc_gen_id);
	}
	
	@Override
	public void updateDownload(Long alloc_id) throws Exception {
		Alloc_gen_hd header = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(alloc_id));
		header.setFile_download("Y");
		this.transactionalRepository.update(header);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Object> getProductListForMonthlyAllocation(String prodType, List<String> smp_sa_prod_group,
			String discProd, String zeroStock, String div_id, String pmtTeam, String period_code, String core,
			String fin_year,String isPrivious,String selectedPeriodCode,String selectedYear,String allocType,String empId) throws Exception {
				
		return this.allocationRepository.getProductListForMonthlyAllocation(prodType, smp_sa_prod_group, discProd,
				zeroStock, div_id, pmtTeam, period_code, core, fin_year,isPrivious,selectedPeriodCode,selectedYear,allocType,empId);
	}

	@Override
	public Period getAllocationMonthofUse() {
		Period period = null;
		try {
			String periodCode = "";// periodFinYear="";
			System.out.println("Query For List ***************************");

			V_Dispatch_Period dispatchPeriod = this.allocationRepository.getDispatchPeriod();
			periodCode = dispatchPeriod.getPeriod_code();
//			System.out.println("V_DISPATCH_PERIOD_MASTER.getAllocationMonthofUse()&&&&&&" + periodCode);
			if (periodCode.equalsIgnoreCase("12")) {
				periodCode = "01";
			} else {
				int pid = Integer.parseInt(periodCode) + 1;
				if (pid < 10)
					periodCode = "0" + String.valueOf(pid);
				else
					periodCode = String.valueOf(pid);

			}
			period = this.periodMasterRepository.getPeriodByPeriodCode(periodCode,dispatchPeriod.getPeriod_fin_year());

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error Occurred :"+new CodifyErrors().getErrorCode(ex));//uncomment asneeded -- System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;

		}
		System.out.println("Period " + period);
		return period;

	}

	@Override
	public Map<String, Object> getStaffCount(String div_id,String sub_team_code) {

		System.out.println("FieldStaffAjax.staffNames()  callll");
		Map<String, Object> map = new HashMap<>();
		int smTotal = 0, rmtotal = 0, tmtotal = 0, abmtotal = 0;
		try {

			List<Object> objList = this.fieldStaffRepository.getNoOfStaffByStaffwiseTraining("001", div_id, null, "-1",
					"0", "fstaff_id", "A", null, null, null, null, null, null,sub_team_code);

			for (Object obj : objList) {
				Object[] objArr = (Object[]) obj;

				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("F")) {
					map.put("msr", objArr[0].toString());
				}

			}

			objList = this.fieldStaffRepository.getNoOfStaffByStaffwiseTraining("002", div_id, null, "-1", "0",
					"fstaff_mgr1_id", "A", null, null, null, null, null, null,sub_team_code);

			for (Object obj : objList) {
				Object[] objArr = (Object[]) obj;
				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("T")) {
					map.put("tm", objArr[0].toString());
				}

				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("F")) {
					map.put("abm", objArr[0].toString());
				}
			}

			if (map.containsKey("tm"))
				if (map.get("tm") != null)
					tmtotal = Integer.parseInt(map.get("tm").toString());
			if (map.containsKey("abm"))
				if (map.get("abm") != null)
					abmtotal = Integer.parseInt(map.get("abm").toString());

			map.put("Totalabm", tmtotal + abmtotal);

			objList = this.fieldStaffRepository.getNoOfStaffByStaffwiseTraining("003", div_id, null, "-1", "0",
					"fstaff_mgr2_id", "A", null, null, null, null, null, null,sub_team_code);

			for (Object obj : objList) {
				Object[] objArr = (Object[]) obj;
				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("T")) {
					map.put("sm", objArr[0].toString());
				}

				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("F")) {
					map.put("rbm", objArr[0].toString());
				}
			}
			if (map.containsKey("sm"))
				if (map.get("sm") != null)
					smTotal = Integer.parseInt(map.get("sm").toString());
			if (map.containsKey("rbm"))
				if (map.get("rbm") != null)
					rmtotal = Integer.parseInt(map.get("rbm").toString());
			map.put("Totalrbm", smTotal + rmtotal);

			System.out.println("getStaffCount " + map);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@Override
	public List<Period> getPeriodListGreaterThanPeriodCode(String periodCode) {
		// TODO Auto-generated method stub
		return this.periodMasterRepository.getPeriodListGreaterThanPeriodCode(periodCode);
	}

	@Override
	public Long getAllocationByCount(String divId, String staff) throws Exception {
		return this.allocationRepository.getAllocationByCount(divId, staff);
	}

	@Override
	public Map<String, Object> byUserStaffNames(String divId, String allocationMonthPeriodCode, String saveMode,
			String planType, String allocationId, String allocationCycle, String allocatioinbProductType, String year,
			String allocationMode, String fieldtype, String coreTeamId,String sub_team_code) {
		Map<String, Object> jb = new HashMap<>();
		try {
			String divisionid = divId;
			String allocMode = allocationMode;
			String cfalocId = allocationId;
			String alloc_id = "0";
			String periodcode = allocationMonthPeriodCode;
			String alloc_cycle = "01";
			String prodtype = planType;
			String fin_year = year;
			String company = "PFZ";

			System.out.println("coreTeamId" + coreTeamId);
			System.out.println("divisionid" + divisionid);
			System.out.println("allocMode" + allocMode);
			System.out.println("cfalocId" + cfalocId);
			int a_mode = Integer.parseInt(allocMode);

			System.out.println("FieldStaffAjax.byUserStaffNames()" + a_mode);
			if (a_mode == 0) {

				jb.put("msr", fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode,
						cfalocId, "fstaff_id", "F", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code));
				jb.put("abm",fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr1_id", "F", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company, sub_team_code));
				jb.put("rbm",fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr2_id", "F", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code));

				// asked by pawaskar 9-Aug-2017 do not consider level code
				jb.put("tm",fieldStaffRepository.getNoOfStaffByStaffwise(null, divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr1_id", "T", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code));

				// asked by pawaskar 9-Aug-2017 do not consider level code
				jb.put("cm",fieldStaffRepository.getNoOfStaffByStaffwise(null, divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr2_id", "T", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code));

			} else if (a_mode > 0 && a_mode <= 3) {
				System.out.println("mode 1 to 3  " + alloc_id + "  " + periodcode + "   " + alloc_cycle + ":  " + ":  "
						+ prodtype + ":  " + fin_year + ":: " + company);
				jb.put("msr", fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode,
						cfalocId, "fstaff_id", "F", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code));
				jb.put("abm",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr1_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code));
				jb.put("rbm",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr2_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code));
			} else if (a_mode == 4) {
				jb.put("msr",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code));
				jb.put("abm",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr1_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code));
				jb.put("rbm", 0);
			} else if (a_mode == 5) {
				jb.put("msr",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code));
				jb.put("abm", 0);
				jb.put("rbm", 0);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return jb;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> save(AllocationBean bean) throws Exception {

		List<String> msrprd = bean.getTeModel();
		List<String> abmprd = bean.getDmModel();
		List<String> rbmprd = bean.getRmbModel();
		List<String> tmprd = bean.getTmModel();
		List<String> cmprd = bean.getSmModel();
		List<String> prodid = bean.getProductIds();
		String fieldtype = bean.getFieldtype();
		String savemode = bean.getAllocationSaveMode();
		String prodtype = bean.getPlanTypeDesc();
		String tmqty = bean.getTmqty().toString();
		String cmqty = bean.getSmqty().toString();
		String alloc_id = bean.getAllocationId();
		String period_code = bean.getAllocationMonthPeriodCode();
		String alloc_cycle = bean.getAllocationCycle();
		String fin_year = bean.getFinYearId();
		String company = bean.getCompanyCode();
		String inputType = bean.getPlanTypeDesc();
		String disp_advice = bean.getFrequency();
		String mgrEmpId = bean.getMgrEmpId();
		String alloc_doc_no=null;
		
		Alloc_Gen_Param param = new Alloc_Gen_Param();
		String division=this.divisionMasterService.getDivNameByDivID(bean.getTeamId());
		param.setAlloc_id(bean.getAllocationId());
		param.setAllocmonth(bean.getAllocationMonth());
		param.setAlloccycle(bean.getAllocationCycle());
		param.setMonthofuseid(bean.getAllocationMonthPeriodCode());
		param.setUserid(bean.getEmpId());
		param.setCompcode(bean.getCompanyCode());
		param.setDivisionid(bean.getTeamId().toString());
		param.setDivname(division);
		param.setAlloctype(bean.getFrequency());
		param.setFinyearid(bean.getFinYearId());
		param.setUserid(bean.getEmpId());
		param.setCurrDate(null);
		param.setAlloccycle(bean.getAllocationCycle());
		param.setAllocmodeid(bean.getAllocationMode());
		if (bean.getAllocationById() != null)
			param.setCfalocid(bean.getAllocationById().toString());
		param.setPeriodcode(bean.getAllocationMonthPeriodCode());
		param.setCoreteamid(bean.getCoreTeamId());
		param.setIpAddress(bean.getIpAddress());
		param.setSrtno(bean.getSrtno());
		param.setSrtdate(bean.getSrtdate()==""?null:bean.getSrtdate());
		
		Alloc_gen_hd agh = null;
		Map<String, Object> map = new HashMap<>();
		try {
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

			if (param.getAlloc_id().equals("0")) {
				DivMaster div=this.divisionMasterService.getObjectById(Long.parseLong(param.getDivisionid()));
				Team team=null;
				if(!bean.getSub_team_code().equals("All")) {
					team=teamRepository.getTeamByDivisionAndTeamCode(Long.parseLong(param.getDivisionid()), bean.getSub_team_code());
				}
				alloc_doc_no =div.getDiv_code_nm()+"_"+bean.getSub_team_code()+"_"+ bean.getAllocationMonth().substring(0, 3).toUpperCase()+fin_year.substring(2) + "_" + bean.getFrequency()+"_"+ bean.getMgrEmpId();
				agh = new Alloc_gen_hd();
				agh.setAlloc_cycle(Long.parseLong(param.getAlloccycle()));
				agh.setAlloc_gen_date(sqlDate);
				agh.setAlloc_month(param.getAllocmonth());
				System.out.println("Alloc_Gen_HeaderDao.save()" + param.getMonthofuseid());
				agh.setAlloc_use_month(param.getMonthofuseid());
				agh.setStatus("A");
				agh.setUpd_date(sqlDate);
				agh.setUpd_ip_add(param.getIpAddress());
				agh.setUser_id(param.getUserid());
				agh.setFin_year(param.getFinyearid());
				agh.setCompany(param.getCompcode());
				agh.setDivision(param.getDivname());
				agh.setDiv_id(Long.parseLong(param.getDivisionid()));
				agh.setAlloc_type(param.getAlloctype());
				agh.setFile_upload("N");
				agh.setFile_download("N");
				agh.setInputType(inputType.split("_")[0]);
				agh.setAlloc_doc_no(alloc_doc_no);
				agh.setDisp_advice(disp_advice);
				agh.setMgr_id(mgrEmpId);
				agh.setAlloc_approval_status("G");
				if (param.getCurrDate() == null) {
					param.setCurrDate(new Date().getTime());
				}
				agh.setEffective_date(new java.sql.Date(param.getCurrDate()));
				agh.setAssistant_appr_status("E");
				agh.setTeam_code(bean.getSub_team_code().equals("All")?" ":bean.getSub_team_code());
				agh.setTeam_name(team==null?"0":team.getTeam_name());
				transactionalRepository.persist(agh);
				agh = allocationGenHeaderRepository.getObjectById(agh.getAlloc_gen_id());
				agh.setAlloc_doc_no(agh.getAlloc_doc_no() + "_" + agh.getAlloc_gen_id());
				agh.setAlloc_mode(bean.getAllocationMode().toString());
				agh.setUser_role(bean.getUser_role());
				agh.setSrt_number(bean.getSrtno());
				agh.setSrt_date(bean.getSrtdate().equals("Invalid date")?null:MedicoResources.convert_DD_MM_YYYY_toDate(bean.getSrtdate()));
				agh.setSrt_remark(bean.getSrtremark());
				transactionalRepository.update(agh);

			} else if (savemode.equals("M")) {
				int i = 0;
				Alloc_gen_ent agd = null;
				agh = allocationGenHeaderRepository.getObjectById(Long.parseLong(param.getAlloc_id()));
				for (Alloc_gen_ent alloc_gen_ent : agh.getAlloc_gen_ets()) {

					/*
					 * for (Alloc_gen_dt alloc_gen_dt : alloc_gen_ent.getAlloc_gen_dts()) {
					 * transactionalRepository.delete(alloc_gen_dt); }
					 * transactionalRepository.delete(alloc_gen_ent);
					 */
					int msrqty_local = 0, abmqty_local = 0, rbmqty_local = 0, totalqty_local = 0, tmqty_local = 0,
							cmqty_local = 0, traningTot = 0;
					agd = allocationRepository.getObjectById(bean.getAlloc_gen_ent_ids().get(i));
					if (!msrprd.get(i).equals("0"))
						msrqty_local = Integer.parseInt(msrprd.get(i).toString());

					if (!abmprd.get(i).equals("0"))
						abmqty_local = (Integer.parseInt(abmprd.get(i).toString()));

					if (!rbmprd.get(i).equals("0"))
						rbmqty_local = (Integer.parseInt(rbmprd.get(i).toString()));

					System.out.println("Traning "+fieldtype);
					if (fieldtype.equals("T")) {
						if (!tmprd.get(i).equals("0"))
							tmqty_local = Integer.parseInt(tmprd.get(i).toString());

						if (!cmprd.get(i).equals("0"))
							cmqty_local = Integer.parseInt(cmprd.get(i).toString());

						traningTot = tmqty_local + cmqty_local;
					}
					totalqty_local = msrqty_local + abmqty_local + rbmqty_local + traningTot;

					agd.setAlloc_qty_total(totalqty_local);
					agd.setAlloc_qty_msr(msrqty_local);
					agd.setAlloc_qty_abm(abmqty_local);
					agd.setAlloc_qty_rbm(rbmqty_local);
					agd.setAlloc_qty_tm(tmqty_local);
					agd.setAlloc_qty_cm(cmqty_local);
					System.out.println("total_qty====Modify==============" + totalqty_local);
					transactionalRepository.update(agd);
					i++;
				}
				map.put("allocId", agh.getAlloc_gen_id());

				return map;
			} else if ((savemode.equals("S")) || (savemode.equals("E"))) {
				agh = allocationGenHeaderRepository.getObjectById(Long.parseLong(param.getAlloc_id()));
			}
			
			this.setAllocEntFstaff(agh, prodid, msrprd, abmprd, rbmprd, tmprd, cmprd, param, fieldtype, prodtype, tmqty,
					cmqty, alloc_id, period_code, alloc_cycle, fin_year, company, savemode, bean.getBrandIds(),bean.getSub_team_code(),param.getAlloctype());

			
			
			map.put("allocId", agh.getAlloc_gen_id());
			map.put("allocDocNo", agh.getAlloc_doc_no());
			System.out.println("Alloc_Gen_HeaderDao.save() " + param.getDivname());
			System.out.println("Alloc_Gen_HeaderDao.save() " + param.getFinyearid());
			System.out.println("Alloc_Gen_HeaderDao.save() " + param.getAllocmonth());
			System.out.println("Alloc_Gen_HeaderDao.save() " + param.getAlloctype());
			System.out.println("Alloc_Gen_HeaderDao.save() " + param.getAlloccycle());

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {
		}
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setAllocEntFstaff(Alloc_gen_hd gen_hd, List<String> prodList, List<String> msrqty, List<String> abmqty,
			List<String> rbmqty, List<String> tmprd, List<String> cmprd, Alloc_Gen_Param param, String fieldtype,
			String prodtype, String tmqty, String cmqty, String alloc_id, String period_code, String alloc_cycle,
			String fin_year, String company, String savemode, List<String> brandIds,String sub_team_code,String alloc_type) throws Exception {

		int i = 0;
		System.out.println("Alloc Mode Id " + param.getAllocmodeid());
		System.out.println("prodList " + prodList.size());
		if (savemode.equals("S")) {

			gen_hd.setAlloc_approval_status("E");
			transactionalRepository.update(gen_hd);

			List<Alloc_gen_ent> agdList = gen_hd.getAlloc_gen_ets().stream().collect(Collectors.toList());
			setAllocDetailFstaff(gen_hd, param, fieldtype, prodtype, tmqty, cmqty, alloc_id, period_code, alloc_cycle,
					fin_year, company, savemode, brandIds, agdList,sub_team_code,alloc_type);
		} else {
			if (param.getCfalocid() != null) {
				for (String prod : prodList) {
					int msrqty_local = 0, abmqty_local = 0, rbmqty_local = 0, totalqty_local = 0, tmqty_local = 0,
							cmqty_local = 0, traningTot = 0;
					if(gen_hd.getAlloc_type().equals("A") || (!msrqty.get(i).equals("0") || !abmqty.get(i).equals("0") || !rbmqty.get(i).equals("0") || !tmprd.get(i).equals("0") || !cmprd.get(i).equals("0"))) {
						Alloc_gen_ent agd = new Alloc_gen_ent();
						agd.setAlloc_cycle(Long.parseLong(param.getAlloccycle()));
						agd.setAlloc_gen_date(new Date());
						agd.setAlloc_gen_id(gen_hd);
						agd.setAlloc_gen_dtl_id(0l);
						agd.setAlloc_mode(param.getAllocmodeid());
						agd.setAlloc_rate(0l);// need to ask
						agd.setFstaff_training(fieldtype);
						agd.setAlt_div_id(Long.parseLong(gen_hd.getDiv_id().toString()));
						agd.setCompany(gen_hd.getCompany());
						agd.setDiv_id(Long.parseLong(gen_hd.getDiv_id().toString()));
						agd.setDivision(gen_hd.getDivision());
						agd.setFin_year(gen_hd.getFin_year());
						agd.setPeriod_code(param.getPeriodcode());
						agd.setProd_id(Long.parseLong(prod));
						agd.setStatus("A");
						agd.setProduct_type(prodtype.split("_")[1]);
						agd.setUpd_date(new Date());
						agd.setUpd_ip_add(param.getIpAddress());
						agd.setUser_id(gen_hd.getUser_id());
						agd.setAlloc_qty_tm(Integer.parseInt(tmqty));
						agd.setAlloc_qty_cm(Integer.parseInt(cmqty));
						agd.setAlloc_type(gen_hd.getAlloc_type());
						if (param.getAllocmodeid().equals("0")) {
							agd.setCfa_destination_id(Long.parseLong(param.getCfalocid()));
							agd.setState_id(0l);
							agd.setZone_id(0l);
							agd.setRbm_id(0l);
							agd.setAbm_id(0l);
							agd.setMsr_id(0l);
						} else if (param.getAllocmodeid().equals("1")) {
							agd.setCfa_destination_id(0l);
							agd.setState_id(0l);
							agd.setZone_id(Long.parseLong(param.getCfalocid()));
							agd.setRbm_id(0l);
							agd.setAbm_id(0l);
							agd.setMsr_id(0l);
						} else if (param.getAllocmodeid().equals("2")) {
							agd.setCfa_destination_id(0l);
							agd.setState_id(Long.parseLong(param.getCfalocid()));
							agd.setZone_id(0l);
							agd.setRbm_id(0l);
							agd.setAbm_id(0l);
							agd.setMsr_id(0l);
						} else if (param.getAllocmodeid().equals("3")) {
							agd.setCfa_destination_id(0l);
							agd.setState_id(0l);
							agd.setZone_id(0l);
							agd.setRbm_id(Long.parseLong(param.getCfalocid()));
							agd.setAbm_id(0l);
							agd.setMsr_id(0l);
						} else if (param.getAllocmodeid().equals("4")) {
							agd.setCfa_destination_id(0l);
							agd.setState_id(0l);
							agd.setZone_id(0l);
							agd.setRbm_id(0l);
							agd.setAbm_id(Long.parseLong(param.getCfalocid()));
							agd.setMsr_id(0l);
						} else if (param.getAllocmodeid().equals("5")) {
							agd.setCfa_destination_id(0l);
							agd.setState_id(0l);
							agd.setZone_id(0l);
							agd.setRbm_id(0l);
							agd.setAbm_id(0l);
							agd.setMsr_id(Long.parseLong(param.getCfalocid()));
						}
	
						/*
						 * System.out.println("MSR QTY" + msrqty.get(i)); System.out.println("ABM QTY" +
						 * abmqty.get(i)); System.out.println("RBM QTY" + rbmqty.get(i));
						 */
						if (!msrqty.get(i).equals("0"))
							msrqty_local = Integer.parseInt(msrqty.get(i).toString());
	
						if (!abmqty.get(i).equals("0"))
							abmqty_local = (Integer.parseInt(abmqty.get(i).toString()));
	
						if (!rbmqty.get(i).equals("0"))
							rbmqty_local = (Integer.parseInt(rbmqty.get(i).toString()));
	
						if (fieldtype.equals("T")) {
							if (!tmprd.get(i).equals("0"))
								tmqty_local = Integer.parseInt(tmprd.get(i).toString());
	
							if (!cmprd.get(i).equals("0"))
								cmqty_local = Integer.parseInt(cmprd.get(i).toString());
	
							traningTot = tmqty_local + cmqty_local;
						}
						totalqty_local = msrqty_local + abmqty_local + rbmqty_local + traningTot;
	
						agd.setAlloc_qty_total(totalqty_local);
						agd.setAlloc_qty_msr(msrqty_local);
						agd.setAlloc_qty_abm(abmqty_local);
						agd.setAlloc_qty_rbm(rbmqty_local);
						agd.setAlloc_qty_tm(tmqty_local);
						agd.setAlloc_qty_cm(cmqty_local);
						agd.setAlloc_smp_sa_prod_group_id(brandIds.get(i));
						System.out.println("total_qty==================" + totalqty_local);
						transactionalRepository.persist(agd);
					}
					i++;
				}
			}

		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setAllocDetailFstaff(Alloc_gen_hd gen_hd, Alloc_Gen_Param param, String fieldtype, String prodtype,
			String tmqty, String cmqty, String alloc_id, String period_code, String alloc_cycle, String fin_year,
			String company, String savemode, List<String> brandIds, List<Alloc_gen_ent> agdList,String sub_team_code,String alloc_type) throws Exception {
		
		int i = 0;
		System.out.println("Alloc Mode Id " + param.getAllocmodeid());
		for (Alloc_gen_ent agd : agdList) {
			prodtype=gen_hd.getAlloc_gen_id()+"_"+agd.getProduct_type();
			if(agd.getAlloc_qty_abm()!=0 || agd.getAlloc_qty_rbm()!=0 || agd.getAlloc_qty_tm()!=0 || agd.getAlloc_qty_msr()!=0 || agd.getAlloc_qty_cm()!=0){
				if (!tmqty.equals("0") || !cmqty.equals("0"))
					setAllocGenTrg(gen_hd, agd, agd.getAlloc_qty_tm().toString(), agd.getAlloc_qty_cm().toString(),
							prodtype);
	
				/**********************************/
				List<FieldStaff> fsAbmList = null;
				List<FieldStaff> fsMsrList = null;
				List<FieldStaff> fsRbmList = null;
				int a_mode = Integer.parseInt(agd.getAlloc_mode());
				System.out.println("Alloc_Gen_Detail_Dao.setAllocEntFstaff()" + a_mode);
				if (a_mode == 0) {
					System.out.println(" alloc mode");
					fsAbmList = new ArrayList<FieldStaff>();
					fsMsrList = new ArrayList<FieldStaff>();
					fsRbmList = new ArrayList<FieldStaff>();
	
					List<Object> list = fieldStaffRepository.getFstaffDataFoDes("001", agd.getDiv_id().toString(),
							param.getCoreteamid(), agd.getAlloc_mode().toString(), agd.getCfa_destination_id().toString(),
							"fstaff_id", fieldtype, alloc_id, period_code, alloc_cycle, prodtype, fin_year, company, "F",sub_team_code,alloc_type);
					Iterator<Object> iterator = list.iterator();
					while (iterator.hasNext()) {
						Object[] objects = (Object[]) iterator.next();
						if (objects[2] == null)
							throw new MedicoException("Field/training indicator is null for selected division");
						FieldStaff field_Staff = new FieldStaff();
						field_Staff.setFstaff_id(Long.parseLong(objects[0].toString()));
						field_Staff.setLevel_code(objects[1].toString());
	
						field_Staff.setFstaff_training(objects[2].toString());
						fsMsrList.add(field_Staff);
	
					}
	
					list = fieldStaffRepository.getFstaffDataFoDes("001", agd.getDiv_id().toString(), param.getCoreteamid(),
							agd.getAlloc_mode().toString(), agd.getCfa_destination_id().toString(), "fstaff_mgr1_id",
							fieldtype, alloc_id, period_code, alloc_cycle, prodtype, fin_year, company, "F",sub_team_code,alloc_type);
	
					iterator = list.iterator();
					while (iterator.hasNext()) {
						Object[] objects = (Object[]) iterator.next();
						FieldStaff field_Staff = new FieldStaff();
						field_Staff.setFstaff_id(Long.parseLong(objects[0].toString()));
						/*
						 * field_Staff.setFstaff_mgr1_id(Integer //Rosha nChange
						 * .parseInt(objects[0].toString()));
						 */
						field_Staff.setLevel_code(objects[1].toString());
	
						field_Staff.setFstaff_training(objects[2].toString());
						fsAbmList.add(field_Staff);
	
					}
					list = fieldStaffRepository.getFstaffDataFoDes("001", agd.getDiv_id().toString(), param.getCoreteamid(),
							agd.getAlloc_mode().toString(), agd.getCfa_destination_id().toString(), "fstaff_mgr2_id",
							fieldtype, alloc_id, period_code, alloc_cycle, prodtype, fin_year, company, "F",sub_team_code,alloc_type);
					iterator = list.iterator();
					while (iterator.hasNext()) {
						Object[] objects = (Object[]) iterator.next();
						FieldStaff field_Staff = new FieldStaff();
						field_Staff.setFstaff_id(Long.parseLong(objects[0].toString()));
						/*
						 * field_Staff.setFstaff_mgr2_id(Integer //Roshan Change
						 * .parseInt(objects[0].toString()));
						 */
						field_Staff.setLevel_code(objects[1].toString());
	
						field_Staff.setFstaff_training(objects[2].toString());
						fsRbmList.add(field_Staff);
	
					}
					if (fieldtype.equalsIgnoreCase("T")) {
						list = fieldStaffRepository.getFstaffDataFoDes("002", agd.getDiv_id().toString(),
								param.getCoreteamid(), agd.getAlloc_mode().toString(),
								agd.getCfa_destination_id().toString(), "fstaff_mgr1_id", fieldtype, alloc_id, period_code,
								alloc_cycle, prodtype, fin_year, company, "T",sub_team_code,alloc_type);
	
						iterator = list.iterator();
						while (iterator.hasNext()) {
							Object[] objects = (Object[]) iterator.next();
							FieldStaff field_Staff = new FieldStaff();
							field_Staff.setFstaff_id(Long.parseLong(objects[0].toString()));
							/*
							 * field_Staff.setFstaff_mgr1_id(Integer //Rosha nChange
							 * .parseInt(objects[0].toString()));
							 */
							field_Staff.setLevel_code(objects[1].toString());
	
							field_Staff.setFstaff_training(objects[2].toString());
							fsAbmList.add(field_Staff);
	
						}
						list = fieldStaffRepository.getFstaffDataFoDes("003", agd.getDiv_id().toString(),
								param.getCoreteamid(), agd.getAlloc_mode().toString(),
								agd.getCfa_destination_id().toString(), "fstaff_mgr2_id", fieldtype, alloc_id, period_code,
								alloc_cycle, prodtype, fin_year, company, "T",sub_team_code,alloc_type);
						iterator = list.iterator();
						while (iterator.hasNext()) {
							Object[] objects = (Object[]) iterator.next();
							FieldStaff field_Staff = new FieldStaff();
							field_Staff.setFstaff_id(Long.parseLong(objects[0].toString()));
							/*
							 * field_Staff.setFstaff_mgr2_id(Integer //Roshan Change
							 * .parseInt(objects[0].toString()));
							 */
							field_Staff.setLevel_code(objects[1].toString());
	
							field_Staff.setFstaff_training(objects[2].toString());
							fsRbmList.add(field_Staff);
	
						}
					}
	
					setAllocDtFstaff(gen_hd, agd, fsRbmList, agd.getProd_id().toString(), agd.getAlloc_qty_rbm().toString(),
							"003", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_rbm().toString());
					setAllocDtFstaff(gen_hd, agd, fsAbmList, agd.getProd_id().toString(), agd.getAlloc_qty_abm().toString(),
							"002", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_abm().toString());
					setAllocDtFstaff(gen_hd, agd, fsMsrList, agd.getProd_id().toString(), agd.getAlloc_qty_msr().toString(),
							"001", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_msr().toString());
				} else if (a_mode >= 1 && a_mode <= 3) {
					String allocationById = "";
					if (a_mode == 1)
						allocationById = agd.getZone_id().toString();
					else if (a_mode == 2)
						allocationById = agd.getState_id().toString();
					else
						allocationById = agd.getRbm_id().toString();
	
					fsRbmList = fieldStaffRepository.getFstaffData("001", agd.getDiv_id().toString(), param.getCoreteamid(),
							agd.getAlloc_mode().toString(), allocationById, "fstaff_mgr2_id", fieldtype, alloc_id,
							alloc_cycle, fin_year, period_code, prodtype, company,sub_team_code);
					fsAbmList = fieldStaffRepository.getFstaffData("001", agd.getDiv_id().toString(), param.getCoreteamid(),
							agd.getAlloc_mode().toString(), allocationById, "fstaff_mgr1_id", fieldtype, alloc_id,
							alloc_cycle, fin_year, period_code, prodtype, company,sub_team_code);
					fsMsrList = fieldStaffRepository.getFstaffData("001", agd.getDiv_id().toString(), param.getCoreteamid(),
							agd.getAlloc_mode().toString(), allocationById, "fstaff_id", fieldtype, alloc_id, alloc_cycle,
							fin_year, period_code, prodtype, company,sub_team_code);
	
					System.out.println("fsRbmList " + fsRbmList.size());
					System.out.println("fsAbmList " + fsAbmList.size());
					System.out.println("fsMsrList " + fsMsrList.size());
					setAllocDtFstaff(gen_hd, agd, fsRbmList, agd.getProd_id().toString(), agd.getAlloc_qty_rbm().toString(),
							"003", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_rbm().toString());
					setAllocDtFstaff(gen_hd, agd, fsAbmList, agd.getProd_id().toString(), agd.getAlloc_qty_abm().toString(),
							"002", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_abm().toString());
					setAllocDtFstaff(gen_hd, agd, fsMsrList, agd.getProd_id().toString(), agd.getAlloc_qty_msr().toString(),
							"001", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_msr().toString());
	
				} else if (a_mode == 4) {
					fsAbmList = fieldStaffRepository.getFstaffData("001", agd.getDiv_id().toString(), param.getCoreteamid(),
							agd.getAlloc_mode().toString(), agd.getAbm_id().toString(), "fstaff_mgr1_id", fieldtype,
							alloc_id, alloc_cycle, fin_year, period_code, prodtype, company,sub_team_code);
					setAllocDtFstaff(gen_hd, agd, fsAbmList, agd.getProd_id().toString(), agd.getAlloc_qty_abm().toString(),
							"002", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_abm().toString());
	
					fsMsrList = fieldStaffRepository.getFstaffData("001", agd.getDiv_id().toString(), param.getCoreteamid(),
							agd.getAlloc_mode().toString(), agd.getAbm_id().toString(), "fstaff_id", fieldtype, alloc_id,
							alloc_cycle, fin_year, period_code, prodtype, company,sub_team_code);
					setAllocDtFstaff(gen_hd, agd, fsMsrList, agd.getProd_id().toString(), agd.getAlloc_qty_msr().toString(),
							"001", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_msr().toString());
	
				} else if (a_mode == 5) {
					fsMsrList = fieldStaffRepository.getFstaffData("001", agd.getDiv_id().toString(), param.getCoreteamid(),
							agd.getAlloc_mode().toString(), agd.getMsr_id().toString(), "fstaff_id", fieldtype, alloc_id,
							alloc_cycle, fin_year, period_code, prodtype, company,sub_team_code);
					setAllocDtFstaff(gen_hd, agd, fsMsrList, agd.getProd_id().toString(), agd.getAlloc_qty_msr().toString(),
							"001", param, fieldtype, prodtype, agd.getAlloc_qty_tm().toString(),
							agd.getAlloc_qty_cm().toString(), agd.getProd_id().toString(),
							agd.getAlloc_qty_msr().toString());
	
				}
			}
			i++;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setAllocGenTrg(Alloc_gen_hd gen_hd, Alloc_gen_ent alloc_gen_ent, String tmqty, String cmqty,
			String prodtype) throws Exception {

		try {
			Alloc_Gen_Trg alloc_Gen_Trg = new Alloc_Gen_Trg();
			alloc_Gen_Trg.setAlloc_gen_id(gen_hd);
			alloc_Gen_Trg.setAlloc_ent_id(alloc_gen_ent);
			alloc_Gen_Trg.setProduct_type(prodtype);
			alloc_Gen_Trg.setAlloc_qty1(new BigDecimal(tmqty));
			alloc_Gen_Trg.setAlloc_qty2(new BigDecimal(cmqty));
			alloc_Gen_Trg.setAlloc_qty3(new BigDecimal(0));
			alloc_Gen_Trg.setAlloc_qty4(new BigDecimal(0));

			transactionalRepository.persist(alloc_Gen_Trg);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void sendForApproval(Long allocationId, String user, String email, String remark) throws Exception {

		// String ip_addr = request.getRemoteAddr();
		String tranType = null;
		Alloc_gen_hd header = allocationGenHeaderRepository.getObjectById(allocationId);
		if (header.getAlloc_approval_status().equals("E")) {
			try {
				Location location = locationService.getLocationNameByEmployeeId(user);
//				if (MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
//					tranType = MedicoConstants.ALLOC_APPR_ALL_PIPL;
//				} else {
//					tranType = MedicoConstants.ALLOC_APPR_ALL_PFZ;
//				}
				// new alternate logic
				tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(),ALLOC_TRAN_NAME,
						header.getCompany().trim());
				if (tranType == null || tranType.isEmpty()) {
					throw new MedicoException("Could not find Approval Data !");
				}
				String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
				List<TaskMaster> masters = taskMasterService.getTask(null, null, null, tranType, null, null, TASK_FLOW,null, null);
				if (masters.size() == 0) {
					throw new MedicoException("Task master record not found");
				}
				TaskMaster taskMaster = masters.get(0);
				List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
				if (task_Master_Dtls.size() == 0) {
					throw new MedicoException("Task master detail record not found");
				}

				System.out.println(" header.getInputType() " + header.getInputType());
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("initiator", user);
				variables.put("initiator_desc", user);
				variables.put("initiator_email", email);
				variables.put("tran_ref_id", header.getAlloc_gen_id());
				variables.put("tran_type", tranType);
				variables.put("inv_group", null);
				variables.put("loc_id", 0L);
				variables.put("cust_id", 0L);
				variables.put("tran_no", header.getAlloc_doc_no());
				variables.put("company_code", "PFZ");
				variables.put("totaltask", masters.size());
				variables.put("amount", BigDecimal.ZERO);
				variables.put("escalatetime", null);
				variables.put("fin_year_id", header.getFin_year());
				variables.put("stock_point_id", 0L);
				variables.put("doc_type",header.getAlloc_type());
				variables.put("task_flow", TASK_FLOW);
				variables.put("remark", remark);
				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("monthlyAllocationApproval",
						variables);
				// changing approval status
				header.setAlloc_approval_status("F");
				header.setAssistant_appr_status("A");
				transactionalRepository.update(header);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}

		Company company = companyMasterService.getCompanyDetails();
		// sending mail after self Approval
		this.sendMail.sendMail(Long.valueOf(allocationId), user, "selfApprove", null, "", "nextApproval",
				Long.valueOf(header.getFin_year()), tranType,company.getCompany(),ALLOC_TRAN_NAME);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setAllocDtFstaff(Alloc_gen_hd gen_hd, Alloc_gen_ent ent, List<FieldStaff> fStaffs, String prodList,
			String alloqtyList, String levString, Alloc_Gen_Param param, String fieldtype, String prodtype,
			String tmqty, String cmqty, String prod, String alloqty) throws Exception {
		// Roshan Changes added getFstaff_id
		int a_mode = Integer.parseInt(ent.getAlloc_mode());
		int count =0 ;
		for (FieldStaff staff : fStaffs) {
			
			int i = 1;
			// for (String prod : prodList) {

			if (!alloqty.equals("0") || !cmqty.equals("0") || !tmqty.equals("0")) {
				count++;
				System.out.println("Count : "+count);
				Alloc_gen_dt agd = new Alloc_gen_dt();

				agd.setAlloc_cycle(Long.parseLong(param.getAlloccycle()));
				agd.setAlloc_gen_date(new Date());
				agd.setAlloc_gen_id(gen_hd);
				agd.setAlloc_ent_id(ent);
				agd.setAlloc_mode(ent.getAlloc_mode());

				if (levString.equals("001")) {
					FieldStaff field_Staff = fieldStaffRepository.getObjectByStaffId(staff.getFstaff_id().longValue());
					if (field_Staff != null) {
						agd.setZone_id(field_Staff.getFstaff_zone_id().longValue());
						agd.setState_id(field_Staff.getFstaff_state_id().longValue());
					} else {
						agd.setZone_id(0L);
						agd.setState_id(0L);
					}

					agd.setMsr_id(staff.getFstaff_id());
					agd.setAlloc_qty_msr(Integer.parseInt(alloqty));
					agd.setRbm_id(0l);
					agd.setAlloc_qty_rbm(0);
					agd.setAbm_id(0l);
					agd.setAlloc_qty_abm(0);
				} else if (levString.equals("002")) {

					FieldStaff field_Staff = fieldStaffRepository.getObjectByStaffId(staff.getFstaff_id().longValue());

					if (field_Staff != null) {
						agd.setZone_id(field_Staff.getFstaff_zone_id().longValue());
						agd.setState_id(field_Staff.getFstaff_state_id().longValue());

					} else {
						agd.setZone_id(0L);
						agd.setState_id(0L);
					}

					agd.setAbm_id(staff.getFstaff_id().longValue());// Roshan changes added
					if (staff.getFstaff_training() != null && staff.getFstaff_training().equals("T"))
						agd.setAlloc_qty_abm(Integer.parseInt(tmqty));
					else
						agd.setAlloc_qty_abm(Integer.parseInt(alloqty));
					agd.setRbm_id(0l);
					agd.setAlloc_qty_rbm(0);
					agd.setMsr_id(0l);
					agd.setAlloc_qty_msr(0);
				} else if (levString.equals("003")) {
					FieldStaff field_Staff = fieldStaffRepository.getObjectByStaffId(staff.getFstaff_id().longValue());
					if (field_Staff != null) {
						agd.setZone_id(field_Staff.getFstaff_zone_id().longValue());
						agd.setState_id(field_Staff.getFstaff_state_id().longValue());
					} else {
						agd.setZone_id(0L);
						agd.setState_id(0L);
					}
					agd.setAbm_id(0l);
					agd.setAlloc_qty_abm(0);
					agd.setRbm_id(staff.getFstaff_id().longValue());// Roshan changes added

					if (staff.getFstaff_training() != null && staff.getFstaff_training().equals("T"))
						agd.setAlloc_qty_rbm(Integer.parseInt(cmqty));
					else
						agd.setAlloc_qty_rbm(Integer.parseInt(alloqty));
					agd.setMsr_id(0l);
					agd.setAlloc_qty_msr(0);

				}
				if (staff.getFstaff_training() != null && staff.getFstaff_training().equals("T"))
					agd.setFstaff_training("T");
				else
					agd.setFstaff_training("F");

				agd.setAlloc_rate(0l);// need to ask
				agd.setAlt_div_id(Long.parseLong(gen_hd.getDiv_id().toString()));// need
																					// to
																					// ask

				agd.setCompany(gen_hd.getCompany());

				agd.setDiv_id(Long.parseLong(gen_hd.getDiv_id().toString()));
				agd.setDivision(gen_hd.getDivision());
				agd.setFin_year(gen_hd.getFin_year());
				agd.setPeriod_code(param.getPeriodcode());
				agd.setProd_id(Long.parseLong(prod));
				if (a_mode == 0)
					agd.setCfa_destination_id(ent.getCfa_destination_id());
				else if (a_mode == 1)
					agd.setCfa_destination_id(ent.getZone_id());
				else if (a_mode == 2)
					agd.setCfa_destination_id(ent.getState_id());
				else if (a_mode == 3)
					agd.setCfa_destination_id(ent.getRbm_id());
				else if (a_mode == 4)
					agd.setCfa_destination_id(ent.getAbm_id());
				else if (a_mode == 5)
					agd.setCfa_destination_id(ent.getMsr_id());
				agd.setStatus("A");
				agd.setUpd_date(new Date());
				agd.setUpd_ip_add(param.getIpAddress());
				agd.setUser_id(gen_hd.getUser_id());
				agd.setProduct_type(ent.getProduct_type());

				transactionalRepository.persist(agd);
				System.out.println("record added" + i);
				i++;

			}
			// i++;
			// }
		}

	}

	@Override
	public Map<String, Object> getAllocationEnt(Long allocId) throws Exception {

		return this.allocationRepository.getAllocationEnt(allocId);
	}

	@Override
	public List<Object> getEnteredAllocationById(String allocationId, String allocationMode,
			String alloc_smp_sa_prod_group_id) {
		// TODO Auto-generated method stub
		String column = "";
		if (allocationMode.equals("0"))
			column = "CFA_DESTINATION_ID";
		else if (allocationMode.equals("1"))
			column = "ZONE_ID";
		else if (allocationMode.equals("2"))
			column = "STATE_ID";
		else if (allocationMode.equals("3"))
			column = "RBM_ID";
		else if (allocationMode.equals("4"))
			column = "ABM_ID";
		else if (allocationMode.equals("5"))
			column = "MSR_ID";

		return this.allocationRepository.getEnteredAllocationById(allocationId, allocationMode, column,
				alloc_smp_sa_prod_group_id);
	}

	@Override
	public List<Object> getSelfApprovalDataMonthly(String user_id, String ind) {
		// TODO Auto-generated method stub
		return this.allocationRepository.getSelfApprovalDataMonthly(user_id, ind);
	}

	@Override
	public List<Alloc_gen_hd> getAllocGenratedDetails(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return allocationRepository.getAllocGenratedDetails(user_id);
	}

	@Override
	public List<AllocationBean> getAllocEnteredBrands(String alloc_gen_id) throws Exception {
		// TODO Auto-generated method stub
		return allocationRepository.getAllocEnteredBrands(alloc_gen_id);
	}

	@Override
	public List<AllocationBean> getAllocEnteredProducts(String allocId,String prodType)throws Exception {
		// TODO Auto-generated method stub
		return this.allocationRepository.getAllocEnteredProducts(allocId,prodType);
	}

	@Override
	public Alloc_gen_hd getObjectById(Long alloc_gen_id) throws Exception {
		// TODO Auto-generated method stub
		return allocationGenHeaderRepository.getObjectById(alloc_gen_id);
	}

	@Override
	public List<AllocationBean> getEnteredStaffDetails(List<String> productId, String allocationId) {
		// TODO Auto-generated method stub
		return allocationRepository.getEnteredStaffDetails(productId, allocationId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateStaffDetailsMonthly(AllocationBean bean) throws Exception {
		try {
			Alloc_gen_dt dtl = null;
			
			
			for (int i = 0; i < bean.getAllocGenDtlIdModel().size(); i++) {
//				System.out.println("IndexModel " + bean.getChangedIndexModel().get(0).toString() + " Index" + i);
				if (bean.getChangedIndexModel().contains(Long.valueOf(i))) {
					
					
					dtl = allocationRepository.getObjectByAllocDtlId(bean.getAllocGenDtlIdModel().get(i));
					System.out.println("Qty " + bean.getStaffAllocQtyModel().get(i).toString());
					System.out.println("Level " + bean.getStaffLevelModel().get(i).toString());

					if (bean.getStaffLevelModel().get(i).equals("001")) {
						dtl.setAlloc_qty_msr(Integer.valueOf(bean.getStaffAllocQtyModel().get(i).toString()));
					} else if (bean.getStaffLevelModel().get(i).equals("002")) {
						dtl.setAlloc_qty_abm(Integer.valueOf(bean.getStaffAllocQtyModel().get(i).toString()));
					} else if (bean.getStaffLevelModel().get(i).equals("003")) {
						dtl.setAlloc_qty_rbm(Integer.valueOf(bean.getStaffAllocQtyModel().get(i).toString()));
					}
					dtl.setMod_upd_ip_add(bean.getIpAddress());
					dtl.setMod_user_id(bean.getEmpId());
					dtl.setMod_upd_date(new Date());

					transactionalRepository.update(dtl);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void discardMonthlyAllocaton(Long allocationId, String year) throws Exception {
		Alloc_gen_hd agh = allocationGenHeaderRepository.getObjectById(allocationId);
		this.allocationRepository.discardAllocation(allocationId, agh);
	}

	@Override
	public List<ActivityNotification> getNotification(String startedBy) throws Exception {
		// TODO Auto-generated method stub
		return dashBoardRepository.getNotification(startedBy);
	}

	@Override
	public List<AllocationBean> getAllocationDetail(Long allocationId, String year) {
		// TODO Auto-generated method stub
		return allocationRepository.getAllocationDetail(allocationId, year);
	}

	// Called from Activity after final Approval
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalMonthlyApproval(Long allocationId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception {

		System.out.println("In final Approval");
		Alloc_gen_hd header = allocationGenHeaderRepository.getObjectById(allocationId);
		try {
			if (isapproved.equals("A")) {
//				List<Alloc_gen_dt> detailList = null;
//				boolean flag = false;
//				String stk_cfa_ind = "";
//				Alloc_Detail mainDetail = new Alloc_Detail();
//				detailList = allocationRepository.getAllocationDetail(allocationId);
//
//				Set<Long> set = new TreeSet<Long>();
//				for (Alloc_gen_dt detail : detailList) {
//					set.add(detail.getMsr_id());
//				}
//				allocHeaderRepository.setMainDetail(set, detailList, stk_cfa_ind, header);

				// Change Status
				header.setAlloc_approval_status("A");
				header.setAssistant_appr_status("A");
				this.transactionalRepository.update(header);
			} else if (isapproved.equals("D")) {
				header.setAlloc_approval_status("D");
				header.setAssistant_appr_status("F");
				header.setStatus("D");
				this.transactionalRepository.update(header);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}

	@Override
	public void discardMonthlyAllocatonByBrand(Long allocationId, Long brandId, String finYear) throws Exception {

		allocationRepository.deleteAllocationByBrandId(allocationId, brandId, finYear);

	}

	@Override
	public Long getAllocationCycleNumber(String div_id, String period_code, String fin_year,String mgrEmpId) throws Exception {

		return this.allocationRepository.getAllocationCycleNumber(div_id, period_code, fin_year,mgrEmpId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> saveuploadallocation(AllocationUploadBean bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		int indexes_i = 0;
		int ex_fin_year_i = 5;
		int ex_period_code_i = 6;
		int div_code_i = 7;
		int field_Staff_i = 10;
		int msr_Alloc_i =11;
		int alloc_heading_i = 15;
		Boolean StateNameRequired=reportService.check_StateNameRequired_or_not();
	
		
		
		int M=0;
		if(StateNameRequired) {
			M=13;
		}else {
			M=12;
		}
		
		if(allocTempArcRepository.checkSGPRMDET_TEXT1()) {
			indexes_i = indexes_i+1;        
			ex_fin_year_i =ex_fin_year_i + 1;    
			ex_period_code_i = ex_period_code_i + 1; 
			div_code_i = div_code_i + 1;       
			field_Staff_i = field_Staff_i + 1;   
			msr_Alloc_i = msr_Alloc_i + 1;      
			alloc_heading_i = alloc_heading_i + 1; 
		}
		
		try {
			String message = "";
			String warning = "";
			String ex_fin_year = bean.getFinYear();
			String ex_company = bean.getComp_code();
			String samp_disp_ind = bean.getSamp_disp_ind();// ServletActionContext.getServletContext().getAttribute("samp_disp_ind").toString();

			Long upload_cycle = this.allocTempRepository.getMaxCycle(bean.getDivision_id(), bean.getPeriod_id(),bean.getCompcode(), bean.getFinYear());
			upload_cycle++;
			int level = 1;
			if (level == 0) {
				message = "Approval matrix not set properly for selected division ";
			}
			List<SG_d_parameters_details> storage_list = this.parameterRepository.getParameteDetails("storage_type");
			SG_d_parameters_details sg_d_parameters_detail = this.parameterRepository.getObjectById(Long.valueOf(bean.getInputType()));
			SG_d_parameters_details CC_parameters_details = null;
			SG_d_parameters_details NC_parameters_details = null;
			for (SG_d_parameters_details details : storage_list) {
				if (details.getSgprmdet_nm().equalsIgnoreCase("CC")) {
					CC_parameters_details = details;
				} else if (details.getSgprmdet_nm().equalsIgnoreCase("NC")) {
					NC_parameters_details = details;
				}
			}
			if (bean.getCsvuploaded().equals("Y")) {
				HashSet<String> div_set = new HashSet<String>();

				String ex_period_code = null;
				long fstaff_id = 0;
				StringBuffer buffer = new StringBuffer(
						"File not uploaded successfully because of the following errors :- <br><br>");
				boolean isFileWrong = false;
				boolean isWarning = false;
				File csvFile = new File(System.getProperty("java.io.tmpdir")+"/"+bean.getFileUpload().getOriginalFilename());
				bean.getFileUpload().transferTo(csvFile);


				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";
				StringBuffer wrn = new StringBuffer(
						"Warning : Selected division and product division not matched for following products :-<br>");
				Set<String> set = new HashSet<String>();
				int alloc_header_id = 0;
				try {
					if (this.allocTempArcRepository.isFileExist(bean.getFileUploadFileName())) {
						map.put(DATA_SAVED, false);
						map.put(RESPONSE_MESSAGE, "  file already uploaded ");
						return map;
					}

					br = new BufferedReader(new FileReader(csvFile));
					List<UploadMsrAllocBean> msr_Allocs = new ArrayList<UploadMsrAllocBean>();
					line = br.readLine();
					System.out.println("line "+line);
					String[] strings = line.split(cvsSplitBy);
					List<Prod_MasterBean> upload_Products_Allocs_heading = new ArrayList<Prod_MasterBean>();

					Long cycle = 1L;
					if (bean.getAlloc_mode() == 'A') {
						Long integer = this.allocDetailRepository.getCycle(Long.valueOf(bean.getDivision_id()),
								bean.getPeriod_id(), ex_company, ex_fin_year);
						if (integer != null) {
							cycle = integer + 1;
						} else {
							cycle = 0L;
						}
					}
					
					
					 Map<String, Integer> maps = new HashMap<>();
						for (int i = M+4; i < strings.length; i++) {

							String prod = strings[i].substring(strings[i].lastIndexOf("(") + 1, strings[i].lastIndexOf(")"));
							 if (maps.containsKey(prod)) {
						            
						            System.out.println("Duplicate found " + prod);
						            map.put(DATA_SAVED, false);
						            map.put(RESPONSE_MESSAGE, "Duplicate Product Found:\n" + strings[i] + "\n Remove And Reupload");
						            return map;
						        } else {
						            
						            maps.put(prod, 1); 
						            System.out.println("Product added: " + prod);
						        }
						}
					
					
					System.err.println("len::" + strings.length);
					for (int i = M+4; i < strings.length; i++) {
						System.out.println(strings[i]);
						SmpItem item = this.productMasterRepository.getProductMasterObjByCode(
								strings[i].substring(strings[i].lastIndexOf("(") + 1, strings[i].lastIndexOf(")")));
						if (item != null) {
							boolean isStorageTypeCorrect = false;
							if (item.getStorage_type_id() == null) {
								map.put(DATA_SAVED, false);
								map.put(RESPONSE_MESSAGE, " Storage type for Product "+strings[i]+" not set ");
								isFileWrong = true;
								return map;
							}
//							if (item.getStorage_type_id().intValue() == NC_parameters_details.getSg_prmdet_id()) {
//								if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("All")) {
//									if ((item.getSmp_prod_type().equalsIgnoreCase("Promotional Input")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Sample"))
//											|| item.getSmp_prod_type().equalsIgnoreCase("Paking")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Lbp")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Others")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Others-RETAIL-PUSH")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Stationary")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Gift") 
//											|| item.getSmp_prod_type().equalsIgnoreCase("Regular PS")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Regular PI")){
//										isStorageTypeCorrect = true;
//									}
//								} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("PS")) {
//									if ((item.getSmp_prod_type().equalsIgnoreCase("Sample")) || (item.getSmp_prod_type().equalsIgnoreCase("Regular PS"))) {
//										isStorageTypeCorrect = true;
//									}
//								} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("PI")) {
//									if ((item.getSmp_prod_type().equalsIgnoreCase("Promotional Input")) || (item.getSmp_prod_type().equalsIgnoreCase("Sample")) ||(item.getSmp_prod_type().equalsIgnoreCase("Regular PI")) ||  item.getSmp_prod_type().equalsIgnoreCase("Cold Chain PI")) {
//										isStorageTypeCorrect = true;
//									}
//								}
//							} else if (item.getStorage_type_id().intValue() == CC_parameters_details.getSg_prmdet_id()) {
//								if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("CC")) {
//									if ((item.getSmp_prod_type().equalsIgnoreCase("Sample")) || item.getSmp_prod_type().equalsIgnoreCase("Promotional Input") || (item.getSmp_prod_type().equalsIgnoreCase("Cold Chain PS"))  || (item.getSmp_prod_type().equalsIgnoreCase("Regular PS")) ) {
//										isStorageTypeCorrect = true;
//									}
//								}
//							} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("PK")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Paking")) {
//									isStorageTypeCorrect = true;
//								}
//
//							} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("OT")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Others")
//										|| item.getSmp_prod_type().equalsIgnoreCase("Others-RETAIL-PUSH")) {
//									isStorageTypeCorrect = true;
//								}
//
//							} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("LB")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Lbp")) {
//									isStorageTypeCorrect = true;
//								}
//
//							} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("ST")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Stationary")) {
//									isStorageTypeCorrect = true;
//								}
//
//							}
//
//							else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("GF")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Gift")) {
//									isStorageTypeCorrect = true;
//								}
//
//							}

//							if (isStorageTypeCorrect) {
								if (item.getSmp_status() != null && item.getSmp_status().equalsIgnoreCase("A")) {
									Prod_MasterBean products_Alloc = new Prod_MasterBean();
									products_Alloc.setAlt_div_id(item.getSmp_std_div_id());
									products_Alloc.setInv_grp(item.getInv_grp_id());
									products_Alloc.setProduct_id(item.getSmp_prod_id());
									products_Alloc.setProduct_name(item.getSmp_prod_name());
									products_Alloc.setProduct_rate(item.getSmp_rate());
									products_Alloc.setProduct_code(strings[i].substring(strings[i].lastIndexOf("(") + 1,
											strings[i].lastIndexOf(")")));
									products_Alloc.setMin_alloc_qty(item.getMin_alloc_qty());
									upload_Products_Allocs_heading.add(products_Alloc);
								} else {
									map.put(DATA_SAVED, false);
									map.put(RESPONSE_MESSAGE, "Product "+strings[i]+" is Deactivated ");
									isFileWrong = true;
									return map;
								}
//							} else {
//								map.put(DATA_SAVED, false);
//								map.put(RESPONSE_MESSAGE, "Product "+strings[i]+" type not as selected input type ");
//								isFileWrong = true;
//								return map;
//							}

						} else {
							map.put(DATA_SAVED, false);
							map.put(RESPONSE_MESSAGE, " Product not found for Product Code  "+strings[i]);
							isFileWrong = true;
							return map;
						}
						// throw new Exception();
					}
					Alloc_gen_hd alloc_gen_header_obj = null;
					
					
					
					outer: while ((line = br.readLine()) != null) {
						strings = line.split(cvsSplitBy);
						if(alloc_gen_header_obj == null) 
							alloc_gen_header_obj = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(strings[0]));
						UploadMsrAllocBean msr_Alloc = new UploadMsrAllocBean();
						msr_Alloc.setUpload_status(alloc_gen_header_obj.getAlloc_type().charAt(0));
						msr_Alloc.setInput_type(Integer.parseInt(bean.getInputType()));
						
						List<Prod_MasterBean> upload_Products_Allocs = new ArrayList<Prod_MasterBean>();
						int k = 0;
						//get alloc gen hd object here by using strings[0]
						
						for (int i = 0; i < strings.length; i++) {
							 System.out.println("SRT_NO" + strings[M+1] + "SRT_DATE " + strings[M+2]);
				              if (strings[M+1].equals("")) {
				                System.out.println("empty srt number");
				              } else {
				                System.out.println("Not Empty srt number");
				              } 
				              System.out.println(("srtno " + strings[14] == "") ? 0 : 1);
				              System.out.println("String::" + strings[i]);
				              System.out.println("Length" + strings.length);
				              msr_Alloc.setSrtno(strings[M+1]);
				              msr_Alloc.setSrtdate(strings[M+2]);
				              msr_Alloc.setSrtremark(strings[M+3]);
				              
							System.out.println("String::"+strings[i]);
							 System.out.println("Length"+strings.length);
							if (strings[M].equalsIgnoreCase("Final")) {
								String emp_id = bean.getEmp_id();
								msr_Alloc.setCreated_by(emp_id);
								String ipAddress = bean.getIp_address();
								if (ipAddress == null) {
									ipAddress = bean.getIp_address();
								}
								msr_Alloc.setCreated_by_ip(ipAddress);

								if (i == 0) {
									int index = bean.getFileUploadFileName().lastIndexOf("_");
									if (index > 0) {
										String u_status = alloc_gen_header_obj.getAlloc_type();
										System.out.println("u_status"+u_status);
										if(bean.getRole().equals(MedicoConstants.ROLE_WAREH)) {
											u_status= alloc_gen_header_obj.getAlloc_type();
										}
										else {
											u_status=String.valueOf(bean.getFileUploadFileName().charAt(1));
										}
										//if (u_status.equals("A") || u_status.equals("M")) {
											msr_Alloc.setAlloc_header_id(Integer.parseInt(strings[0]));
											
											msr_Alloc.setAlloc_xgen_header_id(0);
											
//										} else {
//											msr_Alloc.setAlloc_header_id(0);
//											msr_Alloc.setAlloc_xgen_header_id(Integer.parseInt(strings[0]));
//										}
										System.out.println("Role "+bean.getRole()+" Mode "+bean.getAlloc_mode()+" u_status.charAt(0) "+u_status.charAt(0));
										if(bean.getRole().equals(MedicoConstants.ROLE_WAREH)) {
											msr_Alloc.setUpload_status(u_status.charAt(0));
										}else {
											msr_Alloc.setUpload_status(u_status.charAt(0));	
										}
										
									} else {
										msr_Alloc.setAlloc_header_id(0);
										msr_Alloc.setAlloc_xgen_header_id(0);
									}
									
									// msr_Alloc.setAlloc_header_id(Integer.parseInt(strings[0]));
									System.out.println("File "+bean.getFileUploadFileName().substring(0, 2)+" role "+bean.getRole());
									if (msr_Alloc.getAlloc_header_id() > 0) {
										alloc_header_id = msr_Alloc.getAlloc_header_id();
										if(bean.getRole().equals(MedicoConstants.ROLE_WAREH)) {
											Alloc_gen_hd alloc_gen_hd = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(msr_Alloc.getAlloc_header_id()));
											if(bean.getFileUploadFileName().substring(0,2).trim().equals("XM") || bean.getFileUploadFileName().substring(0,2).trim().equals("XA")){
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE, "Entered by is not correct");
												isFileWrong = true;
												break outer;
											}
											if (alloc_gen_hd != null) {
												System.out.println("ALLOC_GEN_ID "+alloc_gen_hd.getAlloc_gen_id());
												System.out.println("FILE UPLLOAD "+alloc_gen_hd.getFile_upload());
												if (alloc_gen_hd.getFile_upload().trim().equals("Y")) {
													map.put(DATA_SAVED, false);
													map.put(RESPONSE_MESSAGE, "File already uploaded");
													isFileWrong = true;
													break outer;
												}
											} else {
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE, "Generation not found with header ");
												isFileWrong = true;
												break outer;
											}
										}
										else {
											AllocConHd alloc_gen_hd = this.allocConsolidationRepository.getObjectAllocConHeader(Long.valueOf(msr_Alloc.getAlloc_header_id()));
											if(bean.getFileUploadFileName().substring(0,2).trim().equals("XM") || bean.getFileUploadFileName().trim().substring(0,2).equals("XA")){
												
												if (alloc_gen_hd != null) {
													if (alloc_gen_hd.getFile_upload()=='Y') {
														map.put(DATA_SAVED, false);
														map.put(RESPONSE_MESSAGE, "File already uploaded");
														isFileWrong = true;
														break outer;
													}
												} else {
													map.put(DATA_SAVED, false);
													map.put(RESPONSE_MESSAGE, "Generation not found with header ");
													isFileWrong = true;
													break outer;
												}
												
											}
											else {
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE, "Entered by is not correct");
												isFileWrong = true;
												break outer;
											}
											
										}
										
									}

								} else if (i == ex_fin_year_i) {
									ex_fin_year = strings[i];
									msr_Alloc.setYear(Long.valueOf(strings[i]));
								} else if (i == ex_period_code_i) {

									ex_period_code = strings[i].substring(strings[i].lastIndexOf("(") + 1,
											strings[i].lastIndexOf(")"));
									System.out.println("ex_period_code "+ex_period_code+"bean.getPeriod_id()"+bean.getPeriod_id());
									if (!ex_period_code.equals(bean.getPeriod_id())) {
										map.put(DATA_SAVED, false);
										map.put(RESPONSE_MESSAGE, "You must upload the file for next period.");
										isFileWrong = true;
										break outer;
									}
									msr_Alloc.setPeriod(bean.getPeriod_id());
								} else if (i == div_code_i) {
									String div_code = strings[i].substring(strings[i].lastIndexOf("(") + 1,
											strings[i].lastIndexOf(")"));
									msr_Alloc.setDivision_cd(div_code.trim());
									div_set.add(div_code.trim());
									// System.out
									// .println(" roshan UploadAllocationAction.saveuploadallocation()"+div_code);
								} else if (i == field_Staff_i) {
									FieldStaff field_Staff = this.fieldStaffRepository.getObjectByStaffId(Long.valueOf(strings[i]));

									if (field_Staff != null) {
										// System.out
										// .println("msr_Alloc.getDivision_cd()"+msr_Alloc
										// .getDivision_cd());

										// check here sample disp ind for sagar
										// start here
										if (samp_disp_ind != null && samp_disp_ind.equalsIgnoreCase("Y")) {
											if (field_Staff.getFstaff_samp_disp_ind() != null) {
												if (!field_Staff.getFstaff_samp_disp_ind().equalsIgnoreCase("Y")) {
													map.put(DATA_SAVED, false);
													map.put(RESPONSE_MESSAGE,field_Staff.getFstaff_code()+ " is Deactive");
													isFileWrong = true;
													return map;
												}
											} else {
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE,"Sample dispatch indicator for  "+field_Staff.getFstaff_code()+ " is not set");
												isFileWrong = true;
												return map;
											}
										}
										// end here
										if (field_Staff.getLeaving_date() != null) {
											map.put(DATA_SAVED, false);
											map.put(RESPONSE_MESSAGE,"Fieldstaff "+field_Staff.getFstaff_code()+" is Deactivated");
											isFileWrong = true;
										}
										if (Integer.parseInt(bean.getDivision_id()) > 0) {
											System.out.println("DIV "+bean.getDivision_id()+" Team  "+bean.getSub_team_code());
											System.out.println("MSR DIV "+msr_Alloc.getDivision_cd());
											if (Integer.parseInt(bean.getDivision_id()) != Long.parseLong(msr_Alloc.getDivision_cd())) {
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE,"Selected division is not correct : "+bean.getDivision_id());
												isFileWrong = true;
												return map;
											}
										}
										if(field_Staff.getLevel_code().equals("001") && !field_Staff.getTeam_code().trim().equals(bean.getSub_team_code().trim())) {
											map.put(DATA_SAVED, false);
											map.put(RESPONSE_MESSAGE,"Team Selected and field staff team not match: Name :"+field_Staff.getFstaff_display_name());
											isFileWrong = true;
											return map;
										}
										if (field_Staff.getFs_div_id().longValue() != Long.parseLong(msr_Alloc.getDivision_cd())) {
											map.put(DATA_SAVED, false);
											map.put(RESPONSE_MESSAGE,"Division Selected and field staff division not match: Name :"+field_Staff.getFstaff_display_name());
											isFileWrong = true;
											return map;
										} else {
											ex_company = field_Staff.getCompany();
											msr_Alloc.setFstaff_id(field_Staff.getFstaff_id());
											msr_Alloc.setCompany(field_Staff.getCompany());
											msr_Alloc.setFs_code(strings[i]);
											msr_Alloc.setDivision(field_Staff.getFs_div_id());
											msr_Alloc.setTeam_code(field_Staff.getTeam_code());
											fstaff_id = field_Staff.getFstaff_id();

										}
									} else {
										map.put(DATA_SAVED, false);
										map.put(RESPONSE_MESSAGE," Field Staff not found for FS_Code "+strings[i - 1]);
										isFileWrong = true;
										return map;
									}
								} else if (i == msr_Alloc_i) {
									msr_Alloc.setFs_name(strings[i]);
								} else if (i > alloc_heading_i) {
									if (!isFileWrong) {
										Prod_MasterBean alloc_heading = upload_Products_Allocs_heading.get(k);
										if (alloc_heading.getAlt_div_id().longValue() != msr_Alloc.getDivision()
												.longValue()) {
											isWarning = true;
											set.add(alloc_heading.getProduct_name());
										}
										msr_Alloc.setCycle(Integer.valueOf(cycle.toString()));

										Prod_MasterBean products_Alloc = new Prod_MasterBean();
										products_Alloc.setProduct_id(alloc_heading.getProduct_id());
										products_Alloc.setProduct_code(alloc_heading.getProduct_code());
										products_Alloc.setAlt_div_id(alloc_heading.getAlt_div_id());
										products_Alloc.setInv_grp(alloc_heading.getInv_grp());
										products_Alloc.setProduct_name(alloc_heading.getProduct_name());
										products_Alloc.setProduct_rate(alloc_heading.getProduct_rate());
										products_Alloc.setMin_alloc_qty(alloc_heading.getMin_alloc_qty());
										
										products_Alloc.setProduct_qty(
												Long.valueOf(strings[i].trim().length() > 0 ? strings[i].trim() : "0"));
										upload_Products_Allocs.add(products_Alloc);
										k++;
									}

								}
							}
						}
						msr_Alloc.setProduct_Masters(upload_Products_Allocs);
						msr_Alloc.setUpload_cycle(Integer.valueOf(upload_cycle.toString()));
						msr_Alloc.setFileUploadFileName(bean.getFileUploadFileName());
						msr_Alloc.setLevel(level);
						msr_Allocs.add(msr_Alloc);

					}
					for (String div_code : div_set) {
						Long div_id = this.divMasterRepository.getDivIDbyCode(div_code);
						if (div_id == 0) {
							map.put(DATA_SAVED, false);
							map.put(RESPONSE_MESSAGE,"Division does not exist for Div code "+div_code);
							isFileWrong = true;
							break;
						}
						if (bean.getAlloc_mode() == 'M') {
							Long allocDetailCount = this.allocDetailRepository.getExistCount(div_id, ex_period_code,
									ex_company, ex_fin_year);
							if (allocDetailCount > 0) {
								map.put(DATA_SAVED, false);
								map.put(RESPONSE_MESSAGE,"Allocation of product for divison "+div_code+" is already done.");
								isFileWrong = true;
								return map;
							}
						}

					}
					if (isWarning) {
						for (String s : set) {
							wrn.append("<br>").append(s);
						}
						warning = wrn.toString();
					}
					if (isFileWrong) {
						message = buffer.toString();
					} else {
						this.allocTempService.saveUploadData(msr_Allocs, bean.getIncludestock(),bean.getSub_team_code());
						if (alloc_header_id > 0) {
							if(bean.getRole().equals(MedicoConstants.ROLE_WAREH)) {
								Alloc_gen_hd header = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(alloc_header_id));
								header.setFile_download("Y");
								header.setFile_upload("Y");
								this.transactionalRepository.update(header);
							}
							else {
								AllocConHd header = this.allocConsolidationRepository.getObjectAllocConHeader(Long.valueOf(alloc_header_id));
								header.setFile_download('Y');
								header.setFile_upload('Y');
								this.transactionalRepository.update(header);
							}
							
						}
						File dir = new File(new File(MedicoConstants.UPLOADED_CSV)+ "//Uploaded CSV");
						if (!dir.exists()) {
						dir.mkdir();
					}
						File fileToCreate = new File(dir, bean.getFileUploadFileName());

						FileUtils.copyFile(csvFile, fileToCreate);
						map.put(DATA_SAVED, true);
						map.put(RESPONSE_MESSAGE,"File Uploaded Successfully");
					}

				} catch (Exception e) {
					map.put(DATA_SAVED, true);
					map.put(RESPONSE_MESSAGE,"File not uploaded successfully");
					e.printStackTrace();
				} finally {
					if (br != null)
						br.close();

				}
			} else {
				try {
					boolean flag = true;

					if (flag) {
						String ipAddress = bean.getIp_address();
						if (ipAddress == null) {
							ipAddress = bean.getIp_address();
						}
						String alloc_id = bean.getAlloc_gen_id().substring(0, bean.getAlloc_gen_id().indexOf("_"));
						List<Alloc_gen_dt> alloc_gen_dts = allocationRepository.getAllocationDetail(Long.valueOf(alloc_id));
						if (alloc_gen_dts.size() > 0) {
							Alloc_gen_hd header = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(alloc_id));
							
							bean.setAlloc_mode(header.getAlloc_mode().trim().charAt(0));
							System.out.println("header.getAlloc_mode().trim().charAt(0)"+header.getAlloc_mode().trim().charAt(0));
							this.allocTempService.saveAlloc_Gen_data(alloc_gen_dts, bean.getAlloc_mode(), Long.valueOf(upload_cycle.toString()),ipAddress, level,bean.getUserid());
							
							header.setFile_download("Y");
							header.setFile_upload("Y");
							this.transactionalRepository.update(header);
							message = "Allocation generated successfully";
						} else {
							message = "Allocation Generation not found for selected divison and Period ";
						}
					}

				} catch (Exception e) {
					map.put(DATA_SAVED, true);
					map.put(RESPONSE_MESSAGE,"not successfully saved");
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
//  @Override
//  public void sendMailForMonthlyAllocation(Long alloc_gen_id,String user,String selfApprove,String taskId,String status,String nextApprovar,Long finYear,String tranType) throws Exception {
//  	List<AllocationBean> allocationList = allocationRepository.getAllocationDetail(alloc_gen_id,finYear.toString());
//  	MailBean mail=null;
//  	String link="";
//
//  	List<MailBean> list=taskMasterService.getMailSendDetails(alloc_gen_id,nextApprovar,tranType);
//  	if(list!=null) {
//  		 mail=list.get(0);
//  	     link="rest/saveApprovalDataList?task_id="+mail.getAct_taskid()+"&decision=approve&userName="+mail.getAssignee_()+"&remark="+mail.getAct_taskid()+"&tran_id="+alloc_gen_id;
//  	}
//  	mail.setLink(link);
//  	System.out.println("Link "+link);
//  	if(mail!=null)
//  	   System.out.println("mailID "+mail.getEmp_email());
//  	sendMail.sendApprovalMailForMonthlyAllocation(allocationList, Arrays.asList("sachin.lokhande@excelsof.com"),status, user,"Allocation Approved ",link,mail,nextApprovar);
//  }

	@Override
	public List<AllocConHd> getAllocConHdUserId(String user_id) {
		// TODO Auto-generated method stub
		return this.allocationRepository.getAllocConHdUserId(user_id);
	}

	@Override
	public List<Alloc_gen_hd> getAllocGenHdUserId(String user_id) {
		// TODO Auto-generated method stub
		return this.allocationRepository.getAllocGenHdUserId(user_id);
	}
	@Override
	public List<AllocationBean> getSummaryDetailsAnnualPlanByDivision(Long alloc_con_id, String year, String status)
			throws Exception {
		// TODO Auto-generated method stub
		return this.allocationRepository.getSummaryDetailsAnnualPlanByDivision(alloc_con_id,year,status);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Alloc_gen_hd regenerateAllocation(Long allocGen_id) throws Exception {
		Alloc_gen_hd agh=this.getObjectById(allocGen_id);
		try {
			agh.setAlloc_approval_status("E");
			this.allocationRepository.changeApprovalStatus(allocGen_id,"G");
			this.allocationRepository.deleteAllocationDetails(allocGen_id);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return agh;
		
	}

	@Override
	public List<AllocationBean> getTeamWithPriviousYearCount(String empId,String divId, String year) throws Exception {
		// TODO Auto-generated method stub
		return this.allocationRepository.getTeamWithPriviousYearCount(empId,divId,year);
	}

	@Override
	public List<Object> getTeamForMonthly(String empId) throws Exception {
		// TODO Auto-generated method stub
		return this.allocationRepository.getTeamForMonthly(empId);
	}

	//P&G
	@Override
	public Map<String, Object> getStaffCount(String div_id, String sub_team_code, String alloc_type) {

		System.out.println("FieldStaffAjax.staffNames()  callll");
		Map<String, Object> map = new HashMap<>();
		int smTotal = 0, rmtotal = 0, tmtotal = 0, abmtotal = 0;
		try {

			List<Object> objList = this.fieldStaffRepository.getNoOfStaffByStaffwiseTraining("001", div_id, null, "-1",
					"0", "fstaff_id", "A", null, null, null, null, null, null,sub_team_code,alloc_type);

			for (Object obj : objList) {
				Object[] objArr = (Object[]) obj;

				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("F")) {
					map.put("msr", objArr[0].toString());
				}

			}

			objList = this.fieldStaffRepository.getNoOfStaffByStaffwiseTraining("002", div_id, null, "-1", "0",
					"fstaff_mgr1_id", "A", null, null, null, null, null, null,sub_team_code,alloc_type);

			for (Object obj : objList) {
				Object[] objArr = (Object[]) obj;
				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("T")) {
					map.put("tm", objArr[0].toString());
				}

				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("F")) {
					map.put("abm", objArr[0].toString());
				}
			}

			if (map.containsKey("tm"))
				if (map.get("tm") != null)
					tmtotal = Integer.parseInt(map.get("tm").toString());
			if (map.containsKey("abm"))
				if (map.get("abm") != null)
					abmtotal = Integer.parseInt(map.get("abm").toString());

			map.put("Totalabm", tmtotal + abmtotal);

			objList = this.fieldStaffRepository.getNoOfStaffByStaffwiseTraining("003", div_id, null, "-1", "0",
					"fstaff_mgr2_id", "A", null, null, null, null, null, null,sub_team_code,alloc_type);

			for (Object obj : objList) {
				Object[] objArr = (Object[]) obj;
				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("T")) {
					map.put("sm", objArr[0].toString());
				}

				if (objArr[1] != null && objArr[1].toString().equalsIgnoreCase("F")) {
					map.put("rbm", objArr[0].toString());
				}
			}
			if (map.containsKey("sm"))
				if (map.get("sm") != null)
					smTotal = Integer.parseInt(map.get("sm").toString());
			if (map.containsKey("rbm"))
				if (map.get("rbm") != null)
					rmtotal = Integer.parseInt(map.get("rbm").toString());
			map.put("Totalrbm", smTotal + rmtotal);

			System.out.println("getStaffCount " + map);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@Override
	public Map<String, Object> byUserStaffNames(String divId, String allocationMonthPeriodCode, String saveMode,
			String planType, String allocationId, String allocationCycle, String allocatioinbProductType, String year,
			String allocationMode, String fieldtype, String coreTeamId, String sub_team_code, String alloc_type) {
		Map<String, Object> jb = new HashMap<>();
		try {
			String divisionid = divId;
			String allocMode = allocationMode;
			String cfalocId = allocationId;
			String alloc_id = "0";
			String periodcode = allocationMonthPeriodCode;
			String alloc_cycle = "01";
			String prodtype = planType;
			String fin_year = year;
			String company = "PFZ";

			System.out.println("coreTeamId" + coreTeamId);
			System.out.println("divisionid" + divisionid);
			System.out.println("allocMode" + allocMode);
			System.out.println("cfalocId" + cfalocId);
			int a_mode = Integer.parseInt(allocMode);

			System.out.println("FieldStaffAjax.byUserStaffNames()" + a_mode);
			if (a_mode == 0) {
				jb.put("msr", fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode,
						cfalocId, "fstaff_id", "F", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code,alloc_type));
				jb.put("abm",fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr1_id", "F", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company, sub_team_code,alloc_type));
				jb.put("rbm",fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr2_id", "F", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code,alloc_type));

				// asked by pawaskar 9-Aug-2017 do not consider level code
				jb.put("tm",fieldStaffRepository.getNoOfStaffByStaffwise(null, divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr1_id", "T", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code,alloc_type));

				// asked by pawaskar 9-Aug-2017 do not consider level code
				jb.put("cm",fieldStaffRepository.getNoOfStaffByStaffwise(null, divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr2_id", "T", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code,alloc_type));

			} else if (a_mode > 0 && a_mode <= 3) {
				System.out.println("mode 1 to 3  " + alloc_id + "  " + periodcode + "   " + alloc_cycle + ":  " + ":  "
						+ prodtype + ":  " + fin_year + ":: " + company);
				jb.put("msr", fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode,
						cfalocId, "fstaff_id", "F", alloc_id, periodcode, alloc_cycle, prodtype, fin_year, company,sub_team_code,alloc_type));
				jb.put("abm",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr1_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code,alloc_type));
				jb.put("rbm",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr2_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code,alloc_type));
			} else if (a_mode == 4) {
				jb.put("msr",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code,alloc_type));
				jb.put("abm",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_mgr1_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code,alloc_type));
				jb.put("rbm", 0);
			} else if (a_mode == 5) {
				jb.put("msr",
						fieldStaffRepository.getNoOfStaffByStaffwise("001", divisionid, coreTeamId, allocMode, cfalocId,
								"fstaff_id", fieldtype, alloc_id, periodcode, alloc_cycle, prodtype, fin_year,
								company,sub_team_code,alloc_type));
				jb.put("abm", 0);
				jb.put("rbm", 0);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}

		return jb;

	}

	@Override
	public Map<String, Object> saveuploadallocationWithoutSRT(AllocationUploadBean bean) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		int indexes_i = 0;
		int ex_fin_year_i = 5;
		int ex_period_code_i = 6;
		int div_code_i = 7;
		int field_Staff_i = 10;
		int msr_Alloc_i =11;
		int alloc_heading_i = 12;
		Boolean StateNameRequired=reportService.check_StateNameRequired_or_not();
		
		
		int M=0;
		if(StateNameRequired) {
			M=13;
		}else {
			M=12;
		}
		
		if(allocTempArcRepository.checkSGPRMDET_TEXT1()) {
			indexes_i = indexes_i+1;        
			ex_fin_year_i =ex_fin_year_i + 1;    
			ex_period_code_i = ex_period_code_i + 1; 
			div_code_i = div_code_i + 1;       
			field_Staff_i = field_Staff_i + 1;   
			msr_Alloc_i = msr_Alloc_i + 1;      
			alloc_heading_i = alloc_heading_i + 1; 
		}
		
		try {
			String message = "";
			String warning = "";
			String ex_fin_year = bean.getFinYear();
			String ex_company = bean.getComp_code();
			String samp_disp_ind = bean.getSamp_disp_ind();// ServletActionContext.getServletContext().getAttribute("samp_disp_ind").toString();

			Long upload_cycle = this.allocTempRepository.getMaxCycle(bean.getDivision_id(), bean.getPeriod_id(),bean.getCompcode(), bean.getFinYear());
			upload_cycle++;
			int level = 1;
			if (level == 0) {
				message = "Approval matrix not set properly for selected division ";
			}
			List<SG_d_parameters_details> storage_list = this.parameterRepository.getParameteDetails("storage_type");
			SG_d_parameters_details sg_d_parameters_detail = this.parameterRepository.getObjectById(Long.valueOf(bean.getInputType()));
			SG_d_parameters_details CC_parameters_details = null;
			SG_d_parameters_details NC_parameters_details = null;
			for (SG_d_parameters_details details : storage_list) {
				if (details.getSgprmdet_nm().equalsIgnoreCase("CC")) {
					CC_parameters_details = details;
				} else if (details.getSgprmdet_nm().equalsIgnoreCase("NC")) {
					NC_parameters_details = details;
				}
			}
			if (bean.getCsvuploaded().equals("Y")) {
				HashSet<String> div_set = new HashSet<String>();

				String ex_period_code = null;
				long fstaff_id = 0;
				StringBuffer buffer = new StringBuffer(
						"File not uploaded successfully because of the following errors :- <br><br>");
				boolean isFileWrong = false;
				boolean isWarning = false;
				File csvFile = new File(System.getProperty("java.io.tmpdir")+"/"+bean.getFileUpload().getOriginalFilename());
				bean.getFileUpload().transferTo(csvFile);


				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";
				StringBuffer wrn = new StringBuffer(
						"Warning : Selected division and product division not matched for following products :-<br>");
				Set<String> set = new HashSet<String>();
				int alloc_header_id = 0;
				try {
					if (this.allocTempArcRepository.isFileExist(bean.getFileUploadFileName())) {
						map.put(DATA_SAVED, false);
						map.put(RESPONSE_MESSAGE, "  file already uploaded ");
						return map;
					}

					br = new BufferedReader(new FileReader(csvFile));
					List<UploadMsrAllocBean> msr_Allocs = new ArrayList<UploadMsrAllocBean>();
					line = br.readLine();
					System.out.println("line "+line);
					String[] strings = line.split(cvsSplitBy);
					List<Prod_MasterBean> upload_Products_Allocs_heading = new ArrayList<Prod_MasterBean>();

					Long cycle = 1L;
					if (bean.getAlloc_mode() == 'A') {
						Long integer = this.allocDetailRepository.getCycle(Long.valueOf(bean.getDivision_id()),
								bean.getPeriod_id(), ex_company, ex_fin_year);
						if (integer != null) {
							cycle = integer + 1;
						} else {
							cycle = 0L;
						}
					}
					
					  Map<String, Integer> maps = new HashMap<>();
					for (int i = M+1; i < strings.length; i++) {

						String prod = strings[i].substring(strings[i].lastIndexOf("(") + 1, strings[i].lastIndexOf(")"));
						 if (maps.containsKey(prod)) {
					            
					            System.out.println("Duplicate found " + prod);
					            map.put(DATA_SAVED, false);
					            map.put(RESPONSE_MESSAGE, "Duplicate Product Found : \n" + strings[i] + "\n Remove And Upload ");
					            return map;
					        } else {
					            
					            maps.put(prod, 1); 
					            System.out.println("Product added: " + prod);
					        }
					}
					

					System.err.println("len::" + strings.length);
					for (int i = M+1; i < strings.length; i++) {
						System.out.println(strings[i]);
						SmpItem item = this.productMasterRepository.getProductMasterObjByCode(
								strings[i].substring(strings[i].lastIndexOf("(") + 1, strings[i].lastIndexOf(")")));
						if (item != null) {
							boolean isStorageTypeCorrect = false;
							if (item.getStorage_type_id() == null) {
								map.put(DATA_SAVED, false);
								map.put(RESPONSE_MESSAGE, " Storage type for Product "+strings[i]+" not set ");
								isFileWrong = true;
								return map;
							}
//							if (item.getStorage_type_id().intValue() == NC_parameters_details.getSg_prmdet_id()) {
//								if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("All")) {
//									if ((item.getSmp_prod_type().equalsIgnoreCase("Promotional Input")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Sample"))
//											|| item.getSmp_prod_type().equalsIgnoreCase("Paking")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Lbp")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Others")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Others-RETAIL-PUSH")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Stationary")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Gift") 
//											|| item.getSmp_prod_type().equalsIgnoreCase("Regular PS")
//											|| item.getSmp_prod_type().equalsIgnoreCase("Regular PI")){
//										isStorageTypeCorrect = true;
//									}
//								} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("PS")) {
//									if ((item.getSmp_prod_type().equalsIgnoreCase("Sample")) || (item.getSmp_prod_type().equalsIgnoreCase("Regular PS"))) {
//										isStorageTypeCorrect = true;
//									}
//								} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("PI")) {
//									if ((item.getSmp_prod_type().equalsIgnoreCase("Promotional Input")) || (item.getSmp_prod_type().equalsIgnoreCase("Sample")) ||(item.getSmp_prod_type().equalsIgnoreCase("Regular PI")) ||  item.getSmp_prod_type().equalsIgnoreCase("Cold Chain PI")) {
//										isStorageTypeCorrect = true;
//									}
//								}
//							} else if (item.getStorage_type_id().intValue() == CC_parameters_details.getSg_prmdet_id()) {
//								if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("CC")) {
//									if ((item.getSmp_prod_type().equalsIgnoreCase("Sample")) || item.getSmp_prod_type().equalsIgnoreCase("Promotional Input") || (item.getSmp_prod_type().equalsIgnoreCase("Cold Chain PS"))  || (item.getSmp_prod_type().equalsIgnoreCase("Regular PS")) ) {
//										isStorageTypeCorrect = true;
//									}
//								}
//							} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("PK")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Paking")) {
//									isStorageTypeCorrect = true;
//								}
//
//							} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("OT")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Others")
//										|| item.getSmp_prod_type().equalsIgnoreCase("Others-RETAIL-PUSH")) {
//									isStorageTypeCorrect = true;
//								}
//
//							} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("LB")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Lbp")) {
//									isStorageTypeCorrect = true;
//								}
//
//							} else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("ST")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Stationary")) {
//									isStorageTypeCorrect = true;
//								}
//
//							}
//
//							else if (sg_d_parameters_detail.getSgprmdet_nm().equalsIgnoreCase("GF")) {
//								if (item.getSmp_prod_type().equalsIgnoreCase("Gift")) {
//									isStorageTypeCorrect = true;
//								}
//
//							}

//							if (isStorageTypeCorrect) {
								if (item.getSmp_status() != null && item.getSmp_status().equalsIgnoreCase("A")) {
									Prod_MasterBean products_Alloc = new Prod_MasterBean();
									products_Alloc.setAlt_div_id(item.getSmp_std_div_id());
									products_Alloc.setInv_grp(item.getInv_grp_id());
									products_Alloc.setProduct_id(item.getSmp_prod_id());
									products_Alloc.setProduct_name(item.getSmp_prod_name());
									products_Alloc.setProduct_rate(item.getSmp_rate());
									products_Alloc.setProduct_code(strings[i].substring(strings[i].lastIndexOf("(") + 1,
											strings[i].lastIndexOf(")")));
									products_Alloc.setMin_alloc_qty(item.getMin_alloc_qty());
									upload_Products_Allocs_heading.add(products_Alloc);
								} else {
									map.put(DATA_SAVED, false);
									map.put(RESPONSE_MESSAGE, "Product "+strings[i]+" is Deactivated ");
									isFileWrong = true;
									return map;
								}
//							} else {
//								map.put(DATA_SAVED, false);
//								map.put(RESPONSE_MESSAGE, "Product "+strings[i]+" type not as selected input type ");
//								isFileWrong = true;
//								return map;
//							}

						} else {
							map.put(DATA_SAVED, false);
							map.put(RESPONSE_MESSAGE, " Product not found for Product Code  "+strings[i]);
							isFileWrong = true;
							return map;
						}
						// throw new Exception();
					}
					Alloc_gen_hd alloc_gen_header_obj = null;
					
			
					
					outer: while ((line = br.readLine()) != null) {
						strings = line.split(cvsSplitBy);
						if(alloc_gen_header_obj == null) 
							alloc_gen_header_obj = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(strings[0]));
						UploadMsrAllocBean msr_Alloc = new UploadMsrAllocBean();
						msr_Alloc.setUpload_status(alloc_gen_header_obj.getAlloc_type().charAt(0));
						msr_Alloc.setInput_type(Integer.parseInt(bean.getInputType()));
						
						List<Prod_MasterBean> upload_Products_Allocs = new ArrayList<Prod_MasterBean>();
						int k = 0;
						//get alloc gen hd object here by using strings[0]
						
						
						
						for (int i = 0; i < strings.length; i++) {
							System.out.println("String::"+strings[i]);
							 System.out.println("Length"+strings.length);
							 
							if (strings[M].equalsIgnoreCase("Final")) {
								
								String emp_id = bean.getEmp_id();
								msr_Alloc.setCreated_by(emp_id);
								String ipAddress = bean.getIp_address();
								if (ipAddress == null) {
									ipAddress = bean.getIp_address();
								}
								msr_Alloc.setCreated_by_ip(ipAddress);

								if (i == indexes_i) {
									int index = bean.getFileUploadFileName().lastIndexOf("_");
									if (index > 0) {
										String u_status = alloc_gen_header_obj.getAlloc_type();
										System.out.println("u_status"+u_status);
										if(bean.getRole().equals(MedicoConstants.ROLE_WAREH)) {
											u_status= alloc_gen_header_obj.getAlloc_type();
										}
										else {
											u_status=String.valueOf(bean.getFileUploadFileName().charAt(1));
										}
										//if (u_status.equals("A") || u_status.equals("M")) {
											msr_Alloc.setAlloc_header_id(Integer.parseInt(strings[0]));
											
											msr_Alloc.setAlloc_xgen_header_id(0);
											
//										} else {
//											msr_Alloc.setAlloc_header_id(0);
//											msr_Alloc.setAlloc_xgen_header_id(Integer.parseInt(strings[0]));
//										}
										System.out.println("Role "+bean.getRole()+" Mode "+bean.getAlloc_mode()+" u_status.charAt(0) "+u_status.charAt(0));
										if(bean.getRole().equals(MedicoConstants.ROLE_WAREH)) {
											msr_Alloc.setUpload_status(u_status.charAt(0));
										}else {
											msr_Alloc.setUpload_status(u_status.charAt(0));	
										}
										
									} else {
										msr_Alloc.setAlloc_header_id(0);
										msr_Alloc.setAlloc_xgen_header_id(0);
									}
									
									// msr_Alloc.setAlloc_header_id(Integer.parseInt(strings[0]));
									System.out.println("File "+bean.getFileUploadFileName().substring(0, 2)+" role "+bean.getRole());
									if (msr_Alloc.getAlloc_header_id() > 0) {
										alloc_header_id = msr_Alloc.getAlloc_header_id();
										if(bean.getRole().equals(MedicoConstants.ROLE_WAREH)) {
											Alloc_gen_hd alloc_gen_hd = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(msr_Alloc.getAlloc_header_id()));
											if(bean.getFileUploadFileName().substring(0,2).trim().equals("XM") || bean.getFileUploadFileName().substring(0,2).trim().equals("XA")){
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE, "Entered by is not correct");
												isFileWrong = true;
												break outer;
											}
											if (alloc_gen_hd != null) {
												System.out.println("ALLOC_GEN_ID "+alloc_gen_hd.getAlloc_gen_id());
												System.out.println("FILE UPLLOAD "+alloc_gen_hd.getFile_upload());
												if (alloc_gen_hd.getFile_upload().trim().equals("Y")) {
													map.put(DATA_SAVED, false);
													map.put(RESPONSE_MESSAGE, "File already uploaded");
													isFileWrong = true;
													break outer;
												}
											} else {
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE, "Generation not found with header ");
												isFileWrong = true;
												break outer;
											}
										}
										else {
											AllocConHd alloc_gen_hd = this.allocConsolidationRepository.getObjectAllocConHeader(Long.valueOf(msr_Alloc.getAlloc_header_id()));
											if(bean.getFileUploadFileName().substring(0,2).trim().equals("XM") || bean.getFileUploadFileName().trim().substring(0,2).equals("XA")){
												
												if (alloc_gen_hd != null) {
													if (alloc_gen_hd.getFile_upload()=='Y') {
														map.put(DATA_SAVED, false);
														map.put(RESPONSE_MESSAGE, "File already uploaded");
														isFileWrong = true;
														break outer;
													}
												} else {
													map.put(DATA_SAVED, false);
													map.put(RESPONSE_MESSAGE, "Generation not found with header ");
													isFileWrong = true;
													break outer;
												}
												
											}
											else {
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE, "Entered by is not correct");
												isFileWrong = true;
												break outer;
											}
											
										}
										
									}

								} else if (i == ex_fin_year_i) {
									ex_fin_year = strings[i];
									msr_Alloc.setYear(Long.valueOf(strings[i]));
								} else if (i == ex_period_code_i) {

									ex_period_code = strings[i].substring(strings[i].lastIndexOf("(") + 1,
											strings[i].lastIndexOf(")"));
									System.out.println("ex_period_code "+ex_period_code+"bean.getPeriod_id()"+bean.getPeriod_id());
									if (!ex_period_code.equals(bean.getPeriod_id())) {
										map.put(DATA_SAVED, false);
										map.put(RESPONSE_MESSAGE, "You must upload the file for next period.");
										isFileWrong = true;
										break outer;
									}
									msr_Alloc.setPeriod(bean.getPeriod_id());
								} else if (i == div_code_i) {
									String div_code = strings[i].substring(strings[i].lastIndexOf("(") + 1,
											strings[i].lastIndexOf(")"));
									msr_Alloc.setDivision_cd(div_code.trim());
									div_set.add(div_code);
									// System.out
									// .println(" roshan UploadAllocationAction.saveuploadallocation()"+div_code);
								} else if (i == field_Staff_i) {
									FieldStaff field_Staff = this.fieldStaffRepository.getObjectByStaffId(Long.valueOf(strings[i]));

									if (field_Staff != null) {
										// System.out
										// .println("msr_Alloc.getDivision_cd()"+msr_Alloc
										// .getDivision_cd());

										// check here sample disp ind for sagar
										// start here
										if (samp_disp_ind != null && samp_disp_ind.equalsIgnoreCase("Y")) {
											if (field_Staff.getFstaff_samp_disp_ind() != null) {
												if (!field_Staff.getFstaff_samp_disp_ind().equalsIgnoreCase("Y")) {
													map.put(DATA_SAVED, false);
													map.put(RESPONSE_MESSAGE,field_Staff.getFstaff_code()+ " is Deactive");
													isFileWrong = true;
													return map;
												}
											} else {
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE,"Sample dispatch indicator for  "+field_Staff.getFstaff_code()+ " is not set");
												isFileWrong = true;
												return map;
											}
										}
										// end here
										if (field_Staff.getLeaving_date() != null) {
											map.put(DATA_SAVED, false);
											map.put(RESPONSE_MESSAGE,"Fieldstaff "+field_Staff.getFstaff_code()+" is Deactivated");
											isFileWrong = true;
										}
										if (Integer.parseInt(bean.getDivision_id()) > 0) {
											System.out.println("DIV "+bean.getDivision_id()+" Team  "+bean.getSub_team_code());
											System.out.println("MSR DIV "+msr_Alloc.getDivision_cd());
											if (Integer.parseInt(bean.getDivision_id()) != Long.parseLong(msr_Alloc.getDivision_cd())) {
												map.put(DATA_SAVED, false);
												map.put(RESPONSE_MESSAGE,"Selected division is not correct : "+bean.getDivision_id());
												isFileWrong = true;
												return map;
											}
										}
										if(field_Staff.getLevel_code().equals("001") && !field_Staff.getTeam_code().trim().equals(bean.getSub_team_code().trim())) {
											map.put(DATA_SAVED, false);
											map.put(RESPONSE_MESSAGE,"Team Selected and field staff team not match: Name :"+field_Staff.getFstaff_display_name());
											isFileWrong = true;
											return map;
										}
										if (field_Staff.getFs_div_id().longValue() != Long.parseLong(msr_Alloc.getDivision_cd())) {
											map.put(DATA_SAVED, false);
											map.put(RESPONSE_MESSAGE,"Division Selected and field staff division not match: Name :"+field_Staff.getFstaff_display_name());
											isFileWrong = true;
											return map;
										} else {
											ex_company = field_Staff.getCompany();
											msr_Alloc.setFstaff_id(field_Staff.getFstaff_id());
											msr_Alloc.setCompany(field_Staff.getCompany());
											msr_Alloc.setFs_code(strings[i]);
											msr_Alloc.setDivision(field_Staff.getFs_div_id());
											msr_Alloc.setTeam_code(field_Staff.getTeam_code());
											fstaff_id = field_Staff.getFstaff_id();

										}
									} else {
										map.put(DATA_SAVED, false);
										map.put(RESPONSE_MESSAGE," Field Staff not found for FS_Code "+strings[i - 1]);
										isFileWrong = true;
										return map;
									}
								} else if (i == msr_Alloc_i) {
									msr_Alloc.setFs_name(strings[i]);
								} else if (i > alloc_heading_i) {
									if (!isFileWrong) {
										Prod_MasterBean alloc_heading = upload_Products_Allocs_heading.get(k);
										if (alloc_heading.getAlt_div_id().longValue() != msr_Alloc.getDivision()
												.longValue()) {
											isWarning = true;
											set.add(alloc_heading.getProduct_name());
										}
										msr_Alloc.setCycle(Integer.valueOf(cycle.toString()));

										Prod_MasterBean products_Alloc = new Prod_MasterBean();
										products_Alloc.setProduct_id(alloc_heading.getProduct_id());
										products_Alloc.setProduct_code(alloc_heading.getProduct_code());
										products_Alloc.setAlt_div_id(alloc_heading.getAlt_div_id());
										products_Alloc.setInv_grp(alloc_heading.getInv_grp());
										products_Alloc.setProduct_name(alloc_heading.getProduct_name());
										products_Alloc.setProduct_rate(alloc_heading.getProduct_rate());
										products_Alloc.setMin_alloc_qty(alloc_heading.getMin_alloc_qty());
										
										products_Alloc.setProduct_qty(
												Long.valueOf(strings[i].trim().length() > 0 ? strings[i].trim() : "0"));
										upload_Products_Allocs.add(products_Alloc);
										k++;
									}

								}
							}
						}
						msr_Alloc.setProduct_Masters(upload_Products_Allocs);
						msr_Alloc.setUpload_cycle(Integer.valueOf(upload_cycle.toString()));
						msr_Alloc.setFileUploadFileName(bean.getFileUploadFileName());
						msr_Alloc.setLevel(level);
						msr_Allocs.add(msr_Alloc);

					}
					for (String div_code : div_set) {
						Long div_id = this.divMasterRepository.getDivIDbyCode(div_code);
						if (div_id == 0) {
							map.put(DATA_SAVED, false);
							map.put(RESPONSE_MESSAGE,"Division does not exist for Div code "+div_code);
							isFileWrong = true;
							break;
						}
						if (bean.getAlloc_mode() == 'M') {
							Long allocDetailCount = this.allocDetailRepository.getExistCount(div_id, ex_period_code,
									ex_company, ex_fin_year);
							if (allocDetailCount > 0) {
								map.put(DATA_SAVED, false);
								map.put(RESPONSE_MESSAGE,"Allocation of product for divison "+div_code+" is already done.");
								isFileWrong = true;
								return map;
							}
						}

					}
					if (isWarning) {
						for (String s : set) {
							wrn.append("<br>").append(s);
						}
						warning = wrn.toString();
					}
					if (isFileWrong) {
						message = buffer.toString();
					} else {
						this.allocTempService.saveUploadData(msr_Allocs, bean.getIncludestock(),bean.getSub_team_code());
						if (alloc_header_id > 0) {
							if(bean.getRole().equals(MedicoConstants.ROLE_WAREH)) {
								Alloc_gen_hd header = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(alloc_header_id));
								header.setFile_download("Y");
								header.setFile_upload("Y");
								this.transactionalRepository.update(header);
							}
							else {
								AllocConHd header = this.allocConsolidationRepository.getObjectAllocConHeader(Long.valueOf(alloc_header_id));
								header.setFile_download('Y');
								header.setFile_upload('Y');
								this.transactionalRepository.update(header);
							}
							
						}
						File dir = new File(new File(MedicoConstants.UPLOADED_CSV)+ "//Uploaded CSV");
						if (!dir.exists()) {
						dir.mkdir();
					}
						File fileToCreate = new File(dir, bean.getFileUploadFileName());

						FileUtils.copyFile(csvFile, fileToCreate);
						map.put(DATA_SAVED, true);
						map.put(RESPONSE_MESSAGE,"File Uploaded Successfully");
					}

				} catch (Exception e) {
					map.put(DATA_SAVED, true);
					map.put(RESPONSE_MESSAGE,"File not uploaded successfully");
					e.printStackTrace();
				} finally {
					if (br != null)
						br.close();

				}
			} else {
				try {
					boolean flag = true;

					if (flag) {
						String ipAddress = bean.getIp_address();
						if (ipAddress == null) {
							ipAddress = bean.getIp_address();
						}
						String alloc_id = bean.getAlloc_gen_id().substring(0, bean.getAlloc_gen_id().indexOf("_"));
						List<Alloc_gen_dt> alloc_gen_dts = allocationRepository.getAllocationDetail(Long.valueOf(alloc_id));
						if (alloc_gen_dts.size() > 0) {
							Alloc_gen_hd header = this.allocationGenHeaderRepository.getObjectById(Long.valueOf(alloc_id));
							
							bean.setAlloc_mode(header.getAlloc_mode().trim().charAt(0));
							System.out.println("header.getAlloc_mode().trim().charAt(0)"+header.getAlloc_mode().trim().charAt(0));
							this.allocTempService.saveAlloc_Gen_data(alloc_gen_dts, bean.getAlloc_mode(), Long.valueOf(upload_cycle.toString()),ipAddress, level,bean.getUserid());
							
							header.setFile_download("Y");
							header.setFile_upload("Y");
							this.transactionalRepository.update(header);
							message = "Allocation generated successfully";
						} else {
							message = "Allocation Generation not found for selected divison and Period ";
						}
					}

				} catch (Exception e) {
					map.put(DATA_SAVED, true);
					map.put(RESPONSE_MESSAGE,"not successfully saved");
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Object getallocgenrateddetails_upload_modify(String empId) {
		// TODO Auto-generated method stub
		return allocationRepository.getallocgenrateddetails_upload_modify(empId);
	}

	@Override
	public Alloc_Temp_Header getHeaderDetailsBeforeApprove(Long valueOf) {
		// TODO Auto-generated method stubs
		return allocationRepository.getHeaderDetailsBeforeApprove(valueOf);
	}

	@Override
	public List<AllocationBean> getAllocEnteredProducts_before_approve(String allocId, String prodType) {
		// TODO Auto-generated method stub
		return allocationRepository.getAllocEnteredProducts_before_approve(allocId,prodType);
	}

	@Override
	public Object getEnteredStaffDetails_for_modify(String prodId, String allocationId) {
		
		return allocationRepository.getEnteredStaffDetails_for_modify(prodId,allocationId);	}

	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateStaffDetailsBeforeApproval(AllocationBean bean) throws Exception {
		try {
			Alloc_Temp dtl = null;
			Boolean logged=false;
			Long temp_id=0L;
			
			
				for (int i = 0; i < bean.getAllocGenDtlIdModel().size(); i++) {
					System.out.println("IndexModel " + bean.getChangedIndexModel().get(0).toString() + " Index" + i);
					if (bean.getChangedIndexModel().contains(Long.valueOf(i))) {
			
						dtl = allocationRepository.getObjectByAllocTemp_detail(bean.getAllocGenDtlIdModel().get(i));
						
						if(!dtl.getAlloctmp_id().equals(temp_id)) {
							logged=	this.allocationRepository.saveLogFor_Alloc_temp(Long.valueOf( bean.getAllocationId()),dtl.getProd_id().toString(),dtl.getAlloctmp_id());
							temp_id=dtl.getAlloctmp_id();
						}
				
						dtl.setFinal_allocqty(new BigDecimal(bean.getStaffAllocQtyModel().get(i).toString()));
//						dtl.setIns_ip_add  (bean.getIpAddress());
//						dtl.setIns_usr_id(bean.getEmpId());
						
						dtl.setMod_ip_add(bean.getIpAddress());
						dtl.setMod_usr_id(bean.getEmpId());
						dtl.setMod_dt(new Date());
						transactionalRepository.update(dtl);

				}
			}

				
				return true;
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			return false;
		}
	}

	@Override
	public List<AllocationBean> getAllocation_modified_Log(Long header_id) {
	return	this.allocationRepository.getAllocation_modified_Log(header_id);
		
	}

	@Override
	public String genarateAllocation_modified_Log_report(List<AllocationBean> list) throws IOException {

		String filename = "AllocationModifyReport" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		SXSSFWorkbook workbook = null;
		try {
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			SXSSFSheet sheet = workbook.createSheet("Allocation-Modify-Report");

			Row row = null;
			Cell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String headings[] = { "Sr. No.", "Gen HD Id","Field Staff Name", "Field Staff Code", "State ","Product Name", "Current Alloc.Qty", " Modified Date","Previous Modified Alloc.Qty", "Modified Date ", "Modified Username " ,"Insert Username"};

			CellStyle headingCellStyle = ReportFormats.heading(workbook);
			CellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			CellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
			cellAlignment.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

			CellStyle cellAlignment2 = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);


			
			
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("Allocation Modify before self Allocation Summary Report");
			cell.setCellStyle(headingCellStyle);
			
			rowCount++;
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
			cell.setCellValue("File Name : " +list.get(0).getFileName());
			cell.setCellStyle(headingCellStyle);

			rowCount += 1;
			row = sheet.createRow(rowCount);

			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}

			colCount = 0;

			rowCount += 1;
			int count = 1;
			Iterator<AllocationBean> dataiterator = list.iterator();
			AllocationBean data = null;
			// for (DispatchDetailReportRevised data : list) {
			
			Long fs_staff_id=0L;
			Long prod_id=0L;
//			while (dataiterator.hasNext()) {
				
				
				for (int i=0;i<list.size();i++) {

				
				colCount = 0;
				data = list.get(i);
				

				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, String.valueOf(count), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, String.valueOf(data.getGen_hd_id()), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,data.getStaffName(),null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFscode(), null);
				colCount++;
				
				
				cell = ReportFormats.cell(row, colCount, data.getState(), null);
				colCount++;
				
				
				cell = ReportFormats.cell(row, colCount, data.getProd_name(), null);
				colCount++;
				
				
				// if  next  fieledstaff and prod id is same add current alloc qry blank else add currnt alloc qty 
			
				System.err.println(data.getStaffId()+"    "+data.getProductId());
						
					if(list.get(i).getStaffId().equals(fs_staff_id)&&(list.get(i).getProductId().equals(prod_id)) ) {
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
						cell = ReportFormats.cell(row, colCount, "", cellAlignment);
						colCount++;
					}else {
		
						cell = ReportFormats.cellNum(row, colCount, data.getCurrent_final_qty().doubleValue(), cellAlignment);
						colCount++;
						
			
						cell = ReportFormats.cell(row, colCount, data.getCurrent_modified_date().toString(), null);
						colCount++;
						
					}

			
				
				cell = ReportFormats.cellNum(row, colCount, data.getModified_final_qty().doubleValue(), cellAlignment);
				colCount++;
				
				
				cell = ReportFormats.cell(row, colCount, data.getModified_date()==null?"":data.getModified_date().toString(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount,  data.getModified_user_name()==null?"":data.getModified_user_name().toString() , null);
				colCount++;
				
				
				
				cell = ReportFormats.cell(row, colCount,  data.getModified_user_name()==null?"":data.getInsertUserName().toString() , null);
				colCount++;
				
				
				fs_staff_id	=data.getStaffId();
				prod_id=data.getProductId();

				
				rowCount++;
				count++;

			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
//			 System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode((Exception) e));// uncomment asneeded --;
		} finally {
			if (workbook != null) {
				workbook.close();
				workbook.dispose();
			}
		}
		return filename;
	}
}
