package com.excel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.ApprovalBean;
import com.excel.bean.DispatchBean;
import com.excel.bean.ReportBean;
import com.excel.bean.TaxCalculationBean;
import com.excel.bean.TaxParamBean;
import com.excel.exception.MedicoException;
import com.excel.model.AllocationDetailList;
import com.excel.model.Am_m_System_Parameter;
import com.excel.model.AutoDispatchDropdown;
import com.excel.model.Company;
import com.excel.model.Depot_Locations;
import com.excel.model.Dispatch_Detail;
import com.excel.model.Dispatch_Header;
import com.excel.model.Dispatch_Header_Addl;
import com.excel.model.Dispatch_Header_arc;
import com.excel.model.Erp_status_log;
import com.excel.model.FieldStaff;
import com.excel.model.GenerateDispatchData;
import com.excel.model.GenerateDispatchDataCfaStock;
import com.excel.model.GenerateDispatchData_AllocType;
import com.excel.model.Location;
import com.excel.model.LrCsvUpload;
import com.excel.model.ManualDispatchItemList;
import com.excel.model.ManualDispatchList;
import com.excel.model.ManulDispatchProdListData;
import com.excel.model.MonthList;
import com.excel.model.P_iu_dispatch_java;
import com.excel.model.P_v_dispatch2_java;
import com.excel.model.Sum_Disp_Detail;
import com.excel.model.Sum_Disp_Header;
import com.excel.model.Sum_Disp_Header_Addl;
import com.excel.model.Sum_Disp_Header_arc;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.model.Transporter_master;
import com.excel.repository.CalculateGstRepository;
import com.excel.repository.DepotLocationRepository;
import com.excel.repository.DispatchRepository;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.LocationRepository;
import com.excel.repository.ParameterRepository;
import com.excel.repository.SystemParameterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.restcontroller.ApprovalController;
import com.excel.utility.EmailToMgr;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.Utility;

@Primary
@Service
public class DispatchServiceImpl implements DispatchService, MedicoConstants {

	@Autowired
	private CompanyMasterService companyMasterService;
	@Autowired
	private DispatchRepository dispatchRepository;
	@Autowired
	private SystemParameterRepository systemParameterRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	CalculateGstRepository calculateGstRepository;
	@Autowired
	CalculateGstService calculateGstService;
	@Autowired
	DepotLocationRepository depotLocationRepository;
	@Autowired
	FieldStaffRepository fieldStaffRepository;
	@Autowired
	TaskService taskService;
	@Autowired
	TaskMasterService taskMasterService;
	@Autowired
	private LocationService locationService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	ParameterRepository parameterRepository;
	@Autowired
	private TaskMasterService taskMstServi;
	@Autowired
	private ApprovalController apprController;

	@Override
	public AllocationDetailList getAllocationDetailList(String emp_id) throws Exception {
		return dispatchRepository.getAllocationDetailList(emp_id);
	}

	@Override
	public List<MonthList> getMonthList(String tablename, String fieldlist, String search, String orderby)
			throws Exception {
		return dispatchRepository.getMonthList(tablename, fieldlist, search, orderby);
	}

	@Override
	public List<AutoDispatchDropdown> getAutoDispatchDropdown(String alloc_smp_id, String dispatchToLabel, String divId,
			String allocType, String subTeamCodeList, String req_Id, boolean isCfaToStockist) throws Exception {
		return dispatchRepository.getAutoDispatchDropdown(alloc_smp_id, dispatchToLabel, divId, allocType,
				subTeamCodeList, req_Id, isCfaToStockist);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<GenerateDispatchData> generateDispatchData(DispatchBean bean) throws Exception {
		List<GenerateDispatchData> dispatchList = null;
		Am_m_System_Parameter parameter = systemParameterRepository.getSpValueBySpName("Dsp_APPR_REQ").get(0);
		String pend_alloc = "N";
		String sample_flag = "";
		String direct_to_pso_ind = "N";
		boolean stock_at_cfa_ind = false;
		// long loc_link_dptloc_id = 0;

		if (bean.getDispatchType().equalsIgnoreCase("stkcfa")) {
			stock_at_cfa_ind = true;
		}
		if (bean.getDispatchType().equalsIgnoreCase("dtp")) {
			direct_to_pso_ind = "Y";
			// loc_link_dptloc_id =
			// locationRepository.getObjectById(bean.getLocId()).getLoc_link_dptloc_id();
		}
		System.err.println("bean.getDispatchTo()::" + bean.getDispatchTo());
		switch (bean.getDispatchTo()) {
		case "E":// Every One
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l, 0l, pend_alloc,
					bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, 0l, sample_flag,
					bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l,
					direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "L":// Location
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(),
					Long.parseLong(bean.getSelect()), 0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "R":// Specified RBM
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l,
					Long.parseLong(bean.getSelect()), 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "S":// Specified State
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l,
					Long.parseLong(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "A":// Specified ABM
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
					Long.parseLong(bean.getSelect()), 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "M":// Specified MSR
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l,
					Long.parseLong(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			// 0l,0l,0l,4372L,pend_alloc,bean.getAction(),bean.getEmpId(),0l,bean.getDivision(),0l,0l,sample_flag,bean.getChallanMsg(),Long.parseLong(bean.getMonth()),parameter.getSp_value(),0l,direct_to_pso_ind,stock_at_cfa_ind);
			break;
		case "SR":// Sample To RBM
			sample_flag = "SR";
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l, 0l, pend_alloc,
					bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), Long.parseLong(bean.getSelect()), 0l,
					sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l,
					direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "SA":// Sample To ABM
			sample_flag = "SA";
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l, 0l, pend_alloc,
					bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, Long.parseLong(bean.getSelect()),
					sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l,
					direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "Z":// specified Zone
			dispatchList = dispatchRepository.genAutoDispatch(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l, 0l, pend_alloc,
					bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, 0l, sample_flag,
					bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(),
					Long.parseLong(bean.getSelect()), direct_to_pso_ind, stock_at_cfa_ind);
			break;
		}
		return dispatchList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<GenerateDispatchDataCfaStock> generateDispatchDataCfaStock(DispatchBean bean) throws Exception {
		List<GenerateDispatchDataCfaStock> dispatchList = null;
		Am_m_System_Parameter parameter = systemParameterRepository.getSpValueBySpName("Dsp_APPR_REQ").get(0);
		String pend_alloc = "N";
		String sample_flag = "";
		String direct_to_pso_ind = "N";
		boolean stock_at_cfa_ind = false;
		// long loc_link_dptloc_id = 0;

		if (bean.getDispatchType().equalsIgnoreCase("stkcfa")) {
			stock_at_cfa_ind = true;
		}
		if (bean.getDispatchType().equalsIgnoreCase("dtp")) {
			direct_to_pso_ind = "Y";
			// loc_link_dptloc_id =
			// locationRepository.getObjectById(bean.getLocId()).getLoc_link_dptloc_id();
		}

		switch (bean.getDispatchTo()) {
		case "E":// Every One
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l, 0l, pend_alloc,
					bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, 0l, sample_flag,
					bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l,
					direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "L":// Location
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(),
					Long.parseLong(bean.getSelect()), 0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "R":// Specified RBM
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l,
					Long.parseLong(bean.getSelect()), 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "S":// Specified State
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l,
					Long.parseLong(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "A":// Specified ABM
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
					Long.parseLong(bean.getSelect()), 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "M":// Specified MSR
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l,
					Long.parseLong(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
					bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
					parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "SR":// Sample To RBM
			sample_flag = "SR";
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l, 0l, pend_alloc,
					bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), Long.parseLong(bean.getSelect()), 0l,
					sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l,
					direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "SA":// Sample To ABM
			sample_flag = "SA";
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l, 0l, pend_alloc,
					bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, Long.parseLong(bean.getSelect()),
					sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l,
					direct_to_pso_ind, stock_at_cfa_ind);
			break;
		case "Z":// specified Zone
			dispatchList = dispatchRepository.genAutoDispatchCfaStock(bean.getCompanyCode(), bean.getAllocSmpYear(),
					bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l, 0l, 0l, 0l, 0l, pend_alloc,
					bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, 0l, sample_flag,
					bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(),
					Long.parseLong(bean.getSelect()), direct_to_pso_ind, stock_at_cfa_ind);
			break;
		}
		return dispatchList;
	}

	@Override
	public List<String> getAutoDispatchDetailsUpdated(Long smp_id, Long disp_cycle, Long loc_id, Long div_id,
			boolean stock_at_cfa_ind) throws Exception {
		return dispatchRepository.getAutoDispatchDetailsUpdated(smp_id, disp_cycle, loc_id, div_id, stock_at_cfa_ind);
	}

	@Override
	public List<ManualDispatchItemList> getdispatchProd_list(Long staff_id, Long smp_id, String allocType)
			throws Exception {
		return dispatchRepository.getdispatchProd_list(staff_id, smp_id, allocType);
	}

	@Override
	public List<ManulDispatchProdListData> getdispatchProdDetails(String comp_cd, String dsp_year, String dsp_period_cd,
			Long dsp_loc, Long smp_id, Long prod_id, Long msr_id, String pend_alloc, String allocType)
			throws Exception {
		return dispatchRepository.getdispatchProdDetails(comp_cd, dsp_year, dsp_period_cd, dsp_loc, smp_id, prod_id,
				msr_id, pend_alloc, allocType);
	}

	@Override
	public List<P_iu_dispatch_java> saveManualDispatch(String comp_cd, String dsp_year, String dsp_period_cd,
			Long dsp_loc, Long dsp_alloc_id, Long smp_id, Long fstaff_id, Long div_id, Long recv_loc, Long dsp_state_id,
			String pend_alloc, String emp_id, String ip_addr, String dsp_status, Long dsp_cycle, Long dsp_id,
			Long prod_id, Long dsp_batch_id, BigDecimal disp_qty, BigDecimal disp_rate, Long dsp_dtl_alloc_id,
			Long dsp_alt_divid, Long sumdsp_id, String action, String status_flag, BigDecimal prv_smpqty,
			Long prv_allocid, Long prv_allocdtlid, String challan_msg, Long challan_period_id, String req_lvl,
			String direct_to_pso_ind) throws Exception {
		return dispatchRepository.saveManualDispatch(comp_cd, dsp_year, dsp_period_cd, dsp_loc, dsp_alloc_id, smp_id,
				fstaff_id, div_id, recv_loc, dsp_state_id, pend_alloc, emp_id, ip_addr, dsp_status, dsp_cycle, dsp_id,
				prod_id, dsp_batch_id, disp_qty, disp_rate, dsp_dtl_alloc_id, dsp_alt_divid, sumdsp_id, action,
				status_flag, prv_smpqty, prv_allocid, prv_allocdtlid, challan_msg, challan_period_id, req_lvl,
				direct_to_pso_ind);
	}

	@Override
	public List<P_v_dispatch2_java> getDispatchedProdData(Long dsp_id, Long loc_id) throws Exception {
		return dispatchRepository.getDispatchedProdData(dsp_id, loc_id);
	}

	@Override
	public List<ManualDispatchList> getManualDispatchList(Long loc_SubComp_id, String user_divisions, String user_id,
			String user_type, long sum_dsp_id) throws Exception {
		return dispatchRepository.getManualDispatchList(loc_SubComp_id, user_divisions, user_id, user_type, sum_dsp_id);
	}

	@Override
	public List<Sum_Disp_Header> getSummaryChallanListByDiv(String user_id, Long div_id, Long loc_subComp_id,
			int appr_level) throws Exception {
		return dispatchRepository.getSummaryChallanListByDiv(div_id, user_id, loc_subComp_id, appr_level);
	}

	@Override
	public Dispatch_Header getDispatchHeaderById(Long dsp_id) throws Exception {
		return dispatchRepository.getDispatchHeaderById(dsp_id);
	}

	@Override
	public void updateHeaderForApproval(String status, Long tran_id, String user_name, Date appr_date)
			throws Exception {
		dispatchRepository.updateHeaderForApproval(status, tran_id, user_name, appr_date);

	}

	@Override
	public List<ManualDispatchList> getManualDispatchListApproval(String user_id, String user_type, Long sum_dsp_id)
			throws Exception {
		return this.dispatchRepository.getManualDispatchListApproval(user_id, user_type, sum_dsp_id);
	}

	@Override
	public List<ReportBean> getProducts(long div_Id, int locfrmId, String stdt, String endt, String fstaff_id,
			String desg_id, String fstaffId_o, String deleted_inv, String emp_id, String emp_id1, String finyrflag,
			String year) throws Exception {
		return dispatchRepository.getProducts(div_Id, locfrmId, stdt, endt, fstaff_id, desg_id, fstaffId_o, deleted_inv,
				emp_id, emp_id1, finyrflag, year);
	}

	// called from BPM after task complete - final approval
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalDispatchApproval(Long tran_id, String user_name, String company_cd, String task_status, String ip)
			throws Exception {
		// logic after final save
		System.out.println("Called from finalDispatchApproval:::::tran_id=" + tran_id
				+ " task_status:::::::::::task_status=" + task_status + "user_name :: " + user_name + "ip :: " + ip);

		String gst_ind = "Y";
		String gst_type = null;
		String gst_doc_type = null;

		BigDecimal[] detail_sum = new BigDecimal[3];
		Dispatch_Header dispatch_header_obj = null;
		Boolean isAllApproved = true;

		Arrays.fill(detail_sum, BigDecimal.ZERO);

		/// newly added for GST for INDIVIDUAL CHALLAN
		dispatch_header_obj = this.dispatchRepository.getDispatchHeaderById(tran_id);// to
		// get
		// sending
		// and
		// recv.
		// loc
		if (gst_ind.equalsIgnoreCase("Y") && task_status.equalsIgnoreCase("A")) {
			String gst_type_ind = "";
			Boolean applyZeroGST = false;
			Location location_obj = null;
			Depot_Locations depot_locations_obj = null;
			String directToPso = "N";

			if (dispatch_header_obj != null && !dispatch_header_obj.getDsp_appr_status().equalsIgnoreCase("A")
					&& !dispatch_header_obj.getDsp_status().equalsIgnoreCase("I")) {

				directToPso = dispatch_header_obj.getDirect_to_pso_ind();

				/*
				 * As per new logic given by Uday sir on 05-08-2017 incase of individual challan
				 * Sending state --> CFA link state and Receiving state --> PSO State (Field
				 * Staff state)
				 * 
				 */

				if (directToPso.equalsIgnoreCase("Y")) {
					System.out.println("Loc ID " + dispatch_header_obj.getDsp_loc_id());
					/// if dispatch made by direct to pso
					BigDecimal tot_goods_val = dispatch_header_obj.getDsp_total_goods_val().setScale(2,
							RoundingMode.HALF_EVEN);
					if (dispatch_header_obj.getDsp_loc_id() != null)
						location_obj = this.locationRepository.getObjectById(dispatch_header_obj.getDsp_loc_id());// sending
					// loc's
					// state
					else
						throw new MedicoException("Sending location not found.");
					if (dispatch_header_obj.getDsp_recvg_loc_id() != null) {
						depot_locations_obj = depotLocationRepository
								.getObjectById(location_obj.getLoc_link_dptloc_id());
						// receiving
						// loc's
						// state
					} else
						throw new MedicoException("Receiving location not found.");
					System.out.println("DISPATCH_HEADER FSTAFF_ID = " + dispatch_header_obj.getDsp_fstaff_id());
					FieldStaff fstaff = fieldStaffRepository.getObjectByStaffId(dispatch_header_obj.getDsp_fstaff_id());

					System.out.println(
							"F_STAFF_ID = " + fstaff.getFstaff_id() + " AND STATE_ID = " + fstaff.getFstaff_state_id());
					if (location_obj.getGst_on_indv_chln().equalsIgnoreCase("Y")
							&& depot_locations_obj.getGst_on_indv_chln().equalsIgnoreCase("Y")) {
						if (depot_locations_obj.getDptstate_id()
								.compareTo(dispatch_header_obj.getDsp_state_id()) == 0) {// FstaffIdStateId
							gst_type_ind = "G";
							gst_type = "SR"; /// intra state no
							/// GSTIN
							// Added condition for Novartis For Bill
							// Of Supply Case on 2 MAY 2018 by rahul
							if (company_cd.equalsIgnoreCase(NIL) || company_cd.equalsIgnoreCase(NHP)) {
								gst_type = "BS"; // Bill of Supply
								applyZeroGST = true;
							}
						} else {
							gst_type_ind = "I";
							gst_type = "IR"; /// inter state no
							/// GSTIN
						}
						gst_doc_type = this.calculateGstRepository.getGST_Doc_type_Para_code(MedicoConstants.DISPATCH,
								gst_type);
						// System.out.println("PRINTING GST DOC
						// TYPE======================" +
						// gst_doc_type);
						if (gst_doc_type == null || gst_doc_type.equals(""))
							throw new MedicoException("GST DOC TYPE not found!!");

						BigDecimal cgst_rate = BigDecimal.ZERO;
						BigDecimal sgst_rate = BigDecimal.ZERO;
						BigDecimal igst_rate = BigDecimal.ZERO;
						List<Dispatch_Detail> dtl_list = dispatchRepository.getDtlListByHdrId(tran_id, "A");
						int counter = 0;

						BigDecimal total_value_after_tax = BigDecimal.ZERO;
						total_value_after_tax = BigDecimal.ZERO;
						BigDecimal rounded_value = BigDecimal.ZERO;
						BigDecimal round_of_val = BigDecimal.ZERO;
						Object[] obj = new Object[12];
						if (!applyZeroGST) {
							System.out.println(":::::::::::::::::::::::" + dispatch_header_obj.getDsp_fstaff_id()
									+ "fstaff.getFstaff_state_id()=============================="
									+ fstaff.getFstaff_state_id());
							for (Dispatch_Detail details : dtl_list) {

								TaxParamBean taxparam = calculateGstRepository.getTaxParamsByStateIdAndProdId(
										fstaff.getFstaff_state_id(), details.getDspdtl_prod_id());
								obj[0] = taxparam.getProd_code();
								obj[1] = taxparam.getOutput_tax_param();
								obj[2] = taxparam.getSt_vat();
								obj[3] = taxparam.getCst_rt();
								obj[4] = taxparam.getSurch();
								obj[5] = taxparam.getIc_chgs();
								obj[6] = taxparam.getCess();
								obj[7] = taxparam.getTo_tax();
								obj[8] = taxparam.getProd_disc();
								obj[9] = taxparam.getCgst();
								obj[10] = taxparam.getIgst();
								obj[11] = taxparam.getSgst();

								cgst_rate = obj[9] != null ? new BigDecimal(obj[9].toString()) : BigDecimal.ZERO;
								igst_rate = obj[10] != null ? new BigDecimal(obj[10].toString()) : BigDecimal.ZERO;
								sgst_rate = obj[11] != null ? new BigDecimal(obj[11].toString()) : BigDecimal.ZERO;

								TaxCalculationBean taxBean = this.calculateGstService.generateGstValues(
										MedicoConstants.DISPATCH, // TODO need to confirm this
										gst_type_ind, // gst_type_ind
										details.getDspdtl_disp_qty(), // billedQty
										BigDecimal.ZERO, // freeQty
										BigDecimal.ZERO, // replQty
										details.getDspdtl_rate(), // rate
										BigDecimal.ZERO, // party discount
										obj[1].toString(), // Out_Tax_Param,
										null, // In_Tax_Param
										cgst_rate, // cgst_rate
										igst_rate, // igst_rate
										sgst_rate, // sgst_rate
										BigDecimal.ZERO, // Surch_Rate
										BigDecimal.ZERO, // Cess_Rate
										BigDecimal.ZERO, // TOT_Rate
										BigDecimal.ZERO, // Prod_Disc
										BigDecimal.ZERO, // Octroi_Rate
										"000", // Cust_Type
										BigDecimal.ZERO, // mrp_diff
										BigDecimal.ZERO, // trade discount
										company_cd, // Comp cd
										BigDecimal.ZERO, // mrp rate
										BigDecimal.ZERO, BigDecimal.ZERO); // SS RATE

								details.setSGST_BILL_AMT(taxBean.getSgst_bill_amount());
								details.setSGST_RATE(taxBean.getSgst_rate());
								details.setCGST_BILL_AMT(taxBean.getCgst_bill_amount());
								details.setCGST_RATE(taxBean.getCgst_rate());
								details.setIGST_BILL_AMT(taxBean.getIgst_bill_amount());
								details.setIGST_RATE(taxBean.getIgst_rate());
								details.setGst_doc_type(gst_doc_type);

								detail_sum[0] = detail_sum[0]
										.add(details.getSGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));
								detail_sum[1] = detail_sum[1]
										.add(details.getCGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));
								detail_sum[2] = detail_sum[2]
										.add(details.getIGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));

								this.transactionalRepository.update(details);

							}

							total_value_after_tax = tot_goods_val.add(detail_sum[0]).add(detail_sum[1])
									.add(detail_sum[2]).setScale(2, RoundingMode.HALF_EVEN);
							total_value_after_tax = total_value_after_tax.setScale(2, RoundingMode.HALF_EVEN);
							rounded_value = total_value_after_tax.setScale(0, RoundingMode.HALF_EVEN);
							round_of_val = rounded_value.subtract(total_value_after_tax);

							this.updateHdrGstVal(detail_sum[0], detail_sum[1], detail_sum[2], counter, gst_doc_type,
									tran_id, round_of_val, dispatch_header_obj, fstaff.getTeam_code());
						} else {
							for (Dispatch_Detail details : dtl_list) {
								// System.out.println("INSIDE
								// CALLING GST CALCULATION FOR BILL
								// OF
								// SUPPLY^^^^^^^^^"+dtl_list.size());

								details.setSGST_BILL_AMT(BigDecimal.ZERO);
								details.setSGST_RATE(BigDecimal.ZERO);
								details.setCGST_BILL_AMT(BigDecimal.ZERO);
								details.setCGST_RATE(BigDecimal.ZERO);
								details.setIGST_BILL_AMT(BigDecimal.ZERO);
								details.setIGST_RATE(BigDecimal.ZERO);
								details.setGst_doc_type(gst_doc_type);

								detail_sum[0] = BigDecimal.ZERO;
								detail_sum[1] = BigDecimal.ZERO;
								detail_sum[2] = BigDecimal.ZERO;

								this.transactionalRepository.update(details);

							}
							this.updateHdrGstVal(detail_sum[0], detail_sum[1], detail_sum[2], counter, gst_doc_type,
									tran_id, BigDecimal.ZERO, dispatch_header_obj, fstaff.getTeam_code());
						}

					} else {
						// TODO update GST columns with Zero values
						// if GST CALCULATION on INDIVIDUAL CHALLAN
						// INDICATOR is off.
					}
				} else { /// if dispatch made by through C&F
					BigDecimal tot_goods_val = dispatch_header_obj.getDsp_total_goods_val().setScale(2,
							RoundingMode.HALF_EVEN);
					System.out.println("Loc ID " + dispatch_header_obj.getDsp_loc_id());
					if (dispatch_header_obj.getDsp_loc_id() != null)
						location_obj = this.locationRepository.getObjectById(dispatch_header_obj.getDsp_loc_id());// sending
					// loc's
					// state
					else
						throw new MedicoException("Sending location not found.");
					if (dispatch_header_obj.getDsp_recvg_loc_id() != null) {
						depot_locations_obj = depotLocationRepository
								.getObjectById(location_obj.getLoc_link_dptloc_id());
						// receiving
						// loc's
						// state
					} else
						throw new MedicoException("Receiving location not found.");
					System.out.println("DISPATCH_HEADER FSTAFF_ID = " + dispatch_header_obj.getDsp_fstaff_id());

					if (location_obj.getGst_on_indv_chln().equalsIgnoreCase("Y")
							&& depot_locations_obj.getGst_on_indv_chln().equalsIgnoreCase("Y")) {

						FieldStaff fstaff = fieldStaffRepository
								.getObjectByStaffId(dispatch_header_obj.getDsp_fstaff_id());
						/*
						 * if (location_obj.getLoc_state_id().compareTo
						 * (depot_locations_obj.getDptstate_id()) == 0) {
						 */
						if (fstaff.getFstaff_state_id().compareTo(depot_locations_obj.getDptstate_id()) == 0) {
							gst_type_ind = "G";
							if (depot_locations_obj.getGst_reg_no() != null
									&& depot_locations_obj.getGst_reg_no() != "")
								gst_type = "SN"; /// intra state
							/// normal
							else if (location_obj.getGst_reg_no().trim()
									.equalsIgnoreCase(depot_locations_obj.getGst_reg_no().trim())
									&& location_obj.getGst_reg_no().trim().equals("")
									&& depot_locations_obj.getGst_reg_no().trim().equals("")) {
								gst_type = "BS"; /// Bill of supply
								applyZeroGST = true;
								// Previous value was false, changed
								// on 2 MAY 2018 by rahul
							} else
								gst_type = "SR"; /// intra state no
							/// GSTIN
						} else {
							gst_type_ind = "I";
							if (depot_locations_obj.getGst_reg_no() != null
									&& depot_locations_obj.getGst_reg_no() != "")
								gst_type = "IN"; /// inter state
							/// normal
							else
								gst_type = "IR"; /// inter state no
							/// GSTIN
						}
						gst_doc_type = this.calculateGstRepository.getGST_Doc_type_Para_code(MedicoConstants.DISPATCH,
								gst_type);
						// System.out.println("PRINTING GST DOC
						// TYPE======================" +
						// gst_doc_type);
						if (gst_doc_type == null || gst_doc_type.equals(""))
							throw new MedicoException("GST DOC TYPE not found!!");

						BigDecimal cgst_rate = BigDecimal.ZERO;
						BigDecimal sgst_rate = BigDecimal.ZERO;
						BigDecimal igst_rate = BigDecimal.ZERO;
						List<Dispatch_Detail> dtl_list = dispatchRepository.getDtlListByHdrId(tran_id, "A");
						int counter = 0;

						BigDecimal total_value_after_tax = BigDecimal.ZERO;
						total_value_after_tax = BigDecimal.ZERO;
						BigDecimal rounded_value = BigDecimal.ZERO;
						BigDecimal round_of_val = BigDecimal.ZERO;
						Object[] obj = new Object[12];
						if (!applyZeroGST) {
							for (Dispatch_Detail details : dtl_list) {

								TaxParamBean taxparam = calculateGstRepository.getTaxParamsByStateIdAndProdId(
										fstaff.getFstaff_state_id(), details.getDspdtl_prod_id());
								obj[0] = taxparam.getProd_code();
								obj[1] = taxparam.getOutput_tax_param();
								obj[2] = taxparam.getSt_vat();
								obj[3] = taxparam.getCst_rt();
								obj[4] = taxparam.getSurch();
								obj[5] = taxparam.getIc_chgs();
								obj[6] = taxparam.getCess();
								obj[7] = taxparam.getTo_tax();
								obj[8] = taxparam.getProd_disc();
								obj[9] = taxparam.getCgst();
								obj[10] = taxparam.getIgst();
								obj[11] = taxparam.getSgst();

								cgst_rate = obj[9] != null ? new BigDecimal(obj[9].toString()) : BigDecimal.ZERO;
								igst_rate = obj[10] != null ? new BigDecimal(obj[10].toString()) : BigDecimal.ZERO;
								sgst_rate = obj[11] != null ? new BigDecimal(obj[11].toString()) : BigDecimal.ZERO;

								TaxCalculationBean taxBean = this.calculateGstService.generateGstValues(
										MedicoConstants.DISPATCH, // TODO need to confirm this
										gst_type_ind, // gst_type_ind
										details.getDspdtl_disp_qty(), // billedQty
										BigDecimal.ZERO, // freeQty
										BigDecimal.ZERO, // replQty
										details.getDspdtl_rate(), // rate
										BigDecimal.ZERO, // party discount
										obj[1].toString(), // Out_Tax_Param,
										null, // In_Tax_Param
										cgst_rate, // cgst_rate
										igst_rate, // igst_rate
										sgst_rate, // sgst_rate
										BigDecimal.ZERO, // Surch_Rate
										BigDecimal.ZERO, // Cess_Rate
										BigDecimal.ZERO, // TOT_Rate
										BigDecimal.ZERO, // Prod_Disc
										BigDecimal.ZERO, // Octroi_Rate
										"000", // Cust_Type
										BigDecimal.ZERO, // mrp_diff
										BigDecimal.ZERO, // trade discount
										company_cd, // Comp cd
										BigDecimal.ZERO, // mrp rate
										BigDecimal.ZERO, BigDecimal.ZERO); // SS RATE

								details.setSGST_BILL_AMT(taxBean.getSgst_bill_amount());
								details.setSGST_RATE(taxBean.getSgst_rate());
								details.setCGST_BILL_AMT(taxBean.getCgst_bill_amount());
								details.setCGST_RATE(taxBean.getCgst_rate());
								details.setIGST_BILL_AMT(taxBean.getIgst_bill_amount());
								details.setIGST_RATE(taxBean.getIgst_rate());
								details.setGst_doc_type(gst_doc_type);

								detail_sum[0] = detail_sum[0]
										.add(details.getSGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));
								detail_sum[1] = detail_sum[1]
										.add(details.getCGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));
								detail_sum[2] = detail_sum[2]
										.add(details.getIGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));

								this.transactionalRepository.update(details);

							}

							total_value_after_tax = tot_goods_val.add(detail_sum[0]).add(detail_sum[1])
									.add(detail_sum[2]).setScale(2, RoundingMode.HALF_EVEN);
							total_value_after_tax = total_value_after_tax.setScale(2, RoundingMode.HALF_EVEN);
							rounded_value = total_value_after_tax.setScale(0, RoundingMode.HALF_EVEN);
							round_of_val = rounded_value.subtract(total_value_after_tax);

							this.updateHdrGstVal(detail_sum[0], detail_sum[1], detail_sum[2], counter, gst_doc_type,
									tran_id, round_of_val, dispatch_header_obj, fstaff.getTeam_code());
						} else {
							for (Dispatch_Detail details : dtl_list) {
								// System.out.println("INSIDE
								// CALLING GST CALCULATION FOR BILL
								// OF
								// SUPPLY^^^^^^^^^"+dtl_list.size());

								details.setSGST_BILL_AMT(BigDecimal.ZERO);
								details.setSGST_RATE(BigDecimal.ZERO);
								details.setCGST_BILL_AMT(BigDecimal.ZERO);
								details.setCGST_RATE(BigDecimal.ZERO);
								details.setIGST_BILL_AMT(BigDecimal.ZERO);
								details.setIGST_RATE(BigDecimal.ZERO);
								details.setGst_doc_type(gst_doc_type);

								detail_sum[0] = BigDecimal.ZERO;
								detail_sum[1] = BigDecimal.ZERO;
								detail_sum[2] = BigDecimal.ZERO;

								this.transactionalRepository.update(details);

							}

							this.updateHdrGstVal(detail_sum[0], detail_sum[1], detail_sum[2], counter, gst_doc_type,
									tran_id, BigDecimal.ZERO, dispatch_header_obj, fstaff.getTeam_code());
						}
					} else {
						// TODO update GST columns with Zero values
						// if GST CALCULATION on INDIVIDUAL CHALLAN
						// INDICATOR is off.
					}
				}
			} else {
				if (!dispatch_header_obj.getDsp_status().equalsIgnoreCase("I")
						&& dispatch_header_obj.getDsp_appr_status().equalsIgnoreCase("A")) {
					return;
				} else {
					Erp_status_log log = null;
					LocalTime startTime = java.time.LocalTime.now();
					LocalTime endTime = java.time.LocalTime.now();
					String sendDate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date());
					log = new Erp_status_log();
					log.setSend_date(MedicoResources.convert_DD_MM_YYYY_toDate(sendDate));
					log.setStart_time(startTime.toString());
					log.setEnd_time(endTime.toString());
					log.setCycle(Integer.valueOf("1"));
					log.setGrn(dispatch_header_obj.getDsp_id().intValue());
					log.setGrn_confirm(0);
					log.setIaa_grn(0);
					log.setIaa_normal(0);
					log.setIaa_wms(0);
					log.setProduct(0);
					log.setBatch(0);
					log.setDsp(0);
					log.setDsp_cancel(0);
					this.transactionalRepository.persist(log);
					// return;
					throw new MedicoException("Dispatch header record not found!!");
				}
			}
		}

		///////////

		// appr_level = new
		// Approval_MatrixDao().getApprLevelByUserId(user_id,
		// Constants.Dispatch_Approval, div_id.get(i));
		dispatch_header_obj.setDsp_mod_usr_id(user_name);
		dispatch_header_obj.setDsp_mod_dt(new Date());
		dispatch_header_obj.setDsp_appr_acq(1l);
		dispatch_header_obj.setDsp_mod_ip_add(ip);
		if (task_status.equalsIgnoreCase("A")) {
			dispatch_header_obj.setDsp_appr_status("A");
		} else {
			dispatch_header_obj.setDsp_appr_status("D");
		}
		this.transactionalRepository.update(dispatch_header_obj);
		if (task_status.equalsIgnoreCase("D")) {
			dispatchRepository.deleteDispatchRecord(tran_id, user_name, dispatch_header_obj.getDsp_ins_ip_add(), "A");

		}

		List<Dispatch_Header> app_status_list = dispatchRepository
				.getApprStatusBySummChlnId(dispatch_header_obj.getDsp_sum_dsp_id());
		for (Dispatch_Header hdr : app_status_list) {
			if (!(hdr.getDsp_appr_status().trim().equalsIgnoreCase("A")
					|| hdr.getDsp_appr_status().trim().equalsIgnoreCase("D"))) {
				isAllApproved = false;
			}
		}

		if (isAllApproved && gst_ind.equalsIgnoreCase("Y")) {
			updateGSTinSummDispForCFA(dispatch_header_obj.getDsp_sum_dsp_id(), company_cd);
		}

	}

	@Override
	public Object getHeaderForDispatchApproval(String ChallanNo) {
		return this.dispatchRepository.getHeaderForDispatchApproval(ChallanNo);
	}

	@Override
	public Object getDetailForDispatchApproval(int dspId) {
		return this.dispatchRepository.getDetailForDispatchApproval(dspId);
	}

	@Override
	public void updateDispatchHeaderForDispatchSelfApproval(String empId, List<String> challanNumberList,
			HttpServletRequest request) {
		this.dispatchRepository.updateDispatchHeaderForDispatchSelfApproval(empId, challanNumberList, request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDispatchHeaderForDispatchSelfDiscard(String empId, List<String> challanNumberList,
			HttpServletRequest request) {
		this.dispatchRepository.updateDispatchHeaderForDispatchSelfDiscard(empId, challanNumberList, request);
	}

	@Override
	public List<GenerateDispatchData_AllocType> generateDispatchDataAllocType(DispatchBean bean)
			throws Exception, SQLException, PersistenceException {
		List<GenerateDispatchData_AllocType> dispatchList = null;
		Am_m_System_Parameter parameter = systemParameterRepository.getSpValueBySpName("Dsp_APPR_REQ").get(0);
		String pend_alloc = "N";
		String sample_flag = "";
		String direct_to_pso_ind = "N";
		boolean stock_at_cfa_ind = false;
		// long loc_link_dptloc_id = 0;

		if (bean.getDispatchType().equalsIgnoreCase("stkcfa")) {
			stock_at_cfa_ind = true;
		}
		if (bean.getDispatchType().equalsIgnoreCase("dtp")) {
			direct_to_pso_ind = "Y";
			// loc_link_dptloc_id =
			// locationRepository.getObjectById(bean.getLocId()).getLoc_link_dptloc_id();
		}
		System.err.println("bean.getDispatchTo()::" + bean.getDispatchTo() + " :  " + bean.getSelect());
		if (!bean.getAllocType().trim().equals(D) && !bean.getAllocType().trim().equals("S")
				&& !bean.getAllocType().trim().equals("T")) {
			switch (bean.getDispatchTo()) {
			case "E":// Every One
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, Long.valueOf(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code());
				break;
			case "L":// Location
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(),
						Long.parseLong(bean.getSelect()), 0l, 0l, Long.valueOf(bean.getSelect()), 0l, pend_alloc,
						bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, 0l, sample_flag,
						bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l,
						direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(),
						bean.getProductTypes(), bean.getSub_team_code());
				break;
			case "R":// Specified RBM
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, Long.parseLong(bean.getSelect()), 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code());
				break;
			case "S":// Specified State
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, Long.parseLong(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code());
				break;
			case "A":// Specified ABM
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						Long.parseLong(bean.getSelect()), 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code());
				break;
			case "M":// Specified MSR
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, Long.parseLong(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code());
				// 0l,0l,0l,4372L,pend_alloc,bean.getAction(),bean.getEmpId(),0l,bean.getDivision(),0l,0l,sample_flag,bean.getChallanMsg(),Long.parseLong(bean.getMonth()),parameter.getSp_value(),0l,direct_to_pso_ind,stock_at_cfa_ind);
				break;
			case "SR":// Sample To RBM
				sample_flag = "SR";
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, Long.valueOf(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), Long.parseLong(bean.getSelect()), 0l, sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code());
				break;
			case "SA":// Sample To ABM
				sample_flag = "SA";
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, Long.valueOf(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, Long.parseLong(bean.getSelect()), sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code());
				break;
			case "Z":// specified Zone
				dispatchList = dispatchRepository.genAutoDispatchAllocType(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, Long.valueOf(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), Long.parseLong(bean.getSelect()), direct_to_pso_ind, stock_at_cfa_ind,
						bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code());
				break;
			}
		} else if (bean.getAllocType().trim().equals(D)) {// For Bulk Upload Alloc_type = "D"
			switch (bean.getDispatchTo()) {
			case "E":// Every One
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, Long.valueOf(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "L":// Location
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(),
						Long.parseLong(bean.getSelect()), 0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(),
						0l, bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "R":// Specified RBM
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, Long.parseLong(bean.getSelect()), 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "S":// Specified State
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, Long.parseLong(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "A":// Specified ABM
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						Long.parseLong(bean.getSelect()), 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "M":// Specified MSR
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, Long.parseLong(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				// 0l,0l,0l,4372L,pend_alloc,bean.getAction(),bean.getEmpId(),0l,bean.getDivision(),0l,0l,sample_flag,bean.getChallanMsg(),Long.parseLong(bean.getMonth()),parameter.getSp_value(),0l,direct_to_pso_ind,stock_at_cfa_ind);
				break;
			case "SR":// Sample To RBM
				sample_flag = "SR";
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(),
						Long.parseLong(bean.getSelect()), 0l, sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "SA":// Sample To ABM
				sample_flag = "SA";
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l,
						Long.parseLong(bean.getSelect()), sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "Z":// specified Zone
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUpload(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, 0l,
						sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(),
						Long.parseLong(bean.getSelect()), direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			}
		} else if (bean.getAllocType().trim().equals("T")) {
			// For Bulk Upload Alloc_type = "S"
			switch (bean.getDispatchTo()) {
			case "E":// Every One
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, Long.valueOf(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "L":// Location
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(),
						Long.parseLong(bean.getSelect()), 0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(),
						0l, bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "R":// Specified RBM
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, Long.parseLong(bean.getSelect()), 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "S":// Specified State
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, Long.parseLong(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "A":// Specified ABM
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						Long.parseLong(bean.getSelect()), 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "M":// Specified MSR
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, Long.parseLong(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				// 0l,0l,0l,4372L,pend_alloc,bean.getAction(),bean.getEmpId(),0l,bean.getDivision(),0l,0l,sample_flag,bean.getChallanMsg(),Long.parseLong(bean.getMonth()),parameter.getSp_value(),0l,direct_to_pso_ind,stock_at_cfa_ind);
				break;
			case "SR":// Sample To RBM
				sample_flag = "SR";
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(),
						Long.parseLong(bean.getSelect()), 0l, sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "SA":// Sample To ABM
				sample_flag = "SA";
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l,
						Long.parseLong(bean.getSelect()), sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			case "Z":// specified Zone
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeBulkUploadStockist(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, 0l,
						sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(),
						Long.parseLong(bean.getSelect()), direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(), bean.getAllocReqNo());
				break;
			}
		} else if (bean.getAllocType().trim().equals("S")) {// For single Stockist Upload = "T"
			switch (bean.getDispatchTo()) {
			case "E":// Every One
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, Long.valueOf(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(),
						bean.getMultipleAllocString());
				break;
			case "L":// Location
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(),
						Long.parseLong(bean.getSelect()), 0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(),
						0l, bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getMultipleAllocString());
				break;
			case "R":// Specified RBM
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, Long.parseLong(bean.getSelect()), 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(),
						bean.getMultipleAllocString());
				break;
			case "S":// Specified State
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, Long.parseLong(bean.getSelect()), 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(),
						bean.getMultipleAllocString());
				break;
			case "A":// Specified ABM
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						Long.parseLong(bean.getSelect()), 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(),
						bean.getMultipleAllocString());
				break;
			case "M":// Specified MSR
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, Long.parseLong(bean.getSelect()), pend_alloc, bean.getAction(), bean.getEmpId(), 0l,
						bean.getDivision(), 0l, 0l, sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
						parameter.getSp_value(), 0l, direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(),
						bean.getMultipleAllocString());
				// 0l,0l,0l,4372L,pend_alloc,bean.getAction(),bean.getEmpId(),0l,bean.getDivision(),0l,0l,sample_flag,bean.getChallanMsg(),Long.parseLong(bean.getMonth()),parameter.getSp_value(),0l,direct_to_pso_ind,stock_at_cfa_ind);
				break;
			case "SR":// Sample To RBM
				sample_flag = "SR";
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(),
						Long.parseLong(bean.getSelect()), 0l, sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getMultipleAllocString());
				break;
			case "SA":// Sample To ABM
				sample_flag = "SA";
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l,
						Long.parseLong(bean.getSelect()), sample_flag, bean.getChallanMsg(),
						Long.parseLong(bean.getMonth()), parameter.getSp_value(), 0l, direct_to_pso_ind,
						stock_at_cfa_ind, bean.getAllocType(), bean.getProdIds(), bean.getProductTypes(),
						bean.getSub_team_code(), bean.getMultipleAllocString());
				break;
			case "Z":// specified Zone
				dispatchList = dispatchRepository.genAutoDispatchAllocTypeSingleStockists(bean.getCompanyCode(),
						bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(), 0l,
						0l, 0l, 0l, 0l, pend_alloc, bean.getAction(), bean.getEmpId(), 0l, bean.getDivision(), 0l, 0l,
						sample_flag, bean.getChallanMsg(), Long.parseLong(bean.getMonth()), parameter.getSp_value(),
						Long.parseLong(bean.getSelect()), direct_to_pso_ind, stock_at_cfa_ind, bean.getAllocType(),
						bean.getProdIds(), bean.getProductTypes(), bean.getSub_team_code(),
						bean.getMultipleAllocString());
				break;
			}
		}
		return dispatchList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateHdrGstVal(BigDecimal sgst_sum, BigDecimal cgst_sum, BigDecimal igst_sum, int counter,
			String gst_doc_type, Long dsp_id, BigDecimal round_of_val, Dispatch_Header obj, String team_code)
			throws Exception {
		obj.setSgst_bill_amt(sgst_sum);
		obj.setCgst_bill_amt(cgst_sum);
		obj.setIgst_bill_amt(igst_sum);
		obj.setGst_doc_type(gst_doc_type);
		obj.setRoundoff(round_of_val);
		obj.setTeam_code(team_code);
		this.transactionalRepository.update(obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDispatchDetail(String comp_cd, String year, String dsp_period_cd, Long dsp_id, Long prod_id,
			String emp_id, String ip_add) throws Exception {
		dispatchRepository.deleteDispatchDetail(comp_cd, year, dsp_period_cd, dsp_id, prod_id, emp_id, ip_add);
	}

	@Override
	public List<ManulDispatchProdListData> getdispatchProdDetailsEdit(Long dsp_id, Long dspdtl_id, String pend_alloc)
			throws Exception {
		return dispatchRepository.getdispatchProdDetailsEdit(dsp_id, dspdtl_id, pend_alloc);
	}

	@Override
	public List<Object> getFieldstaffDetail(Long fsId, Long locId) throws Exception {
		return dispatchRepository.getFieldstaffDetail(fsId, locId);
	}

	@Override
	public String getDispatchType(Long dsp_id) throws Exception {
		// TODO Auto-generated method stub
		return dispatchRepository.getDispatchType(dsp_id);
	}

	@Override
	public List<ReportBean> getEmpDetails(String startdt, String endate, Long cfaLoc, String username, String role) {
		// TODO Auto-generated method stub
		return dispatchRepository.getEmpDetails(startdt, endate, cfaLoc, username, role);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public long dpLrUpload(DispatchBean bean) throws Exception {
		BufferedReader br = null;
		long j = 0l;
		try {

			if (bean.getStockAtCfa() == null) {
				bean.setStockAtCfa("N");
			}
			if (bean.getStockAtCfa().equalsIgnoreCase("Y")) {
				bean.setStockAtCfa("stock_at_cfa");
			} else {
				bean.setStockAtCfa("Upload");
			}
			if (bean.getTmpOrActl() == null) {
				bean.setTmpOrActl("A");
			}

			File convFile = new File(
					System.getProperty("java.io.tmpdir") + "/" + bean.getDpLrUploadFile().getOriginalFilename());
			bean.getDpLrUploadFile().transferTo(convFile);
			br = new BufferedReader(new FileReader(convFile));
			String[] header = br.readLine().split(",");
			String record = "";
			Set<String> setlr = new TreeSet<String>();
			String oldmst_stn_no = "", newmst_stn_no = "";
			List<Sum_Disp_Header> datasum = new ArrayList<Sum_Disp_Header>();
			List<Dispatch_Header> datadispt = new ArrayList<Dispatch_Header>();
			List<Sum_Disp_Header_Addl> datasum_adl = new ArrayList<Sum_Disp_Header_Addl>();
			List<Dispatch_Header_Addl> datadispt_adl = new ArrayList<Dispatch_Header_Addl>();
			// br.readLine();
			List<LrCsvUpload> lrcsvDwnList = new ArrayList<LrCsvUpload>();
			String lrNoString = "";
			String finyr = null;
			long i = 0l;
			final String DATE_FORMAT = "dd/MM/yyyy";
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			while ((record = br.readLine()) != null) {
				j++;
				LrCsvUpload lrcsvDownload = new LrCsvUpload();
				System.out.println("record:" + record);
				String[] arr = record.split(",");
				System.out.println("arr:" + arr.length);
				if (arr.length != 31) {
					throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
				}
				if (arr[0].trim().length() > 0) {
					newmst_stn_no = arr[0];
					java.util.Date date = null;
					java.util.Date date1 = new Date();
					if (!newmst_stn_no.equalsIgnoreCase(oldmst_stn_no)) {
						if (bean.getTmpOrActl().trim().equals("A")) {
							Sum_Disp_Header sumobj = new Sum_Disp_Header();
							sumobj.setSumdsp_id(
									(arr[0] != null && arr[0].trim().length() > 0) ? Long.parseLong(arr[0]) : 0);
							sumobj.setSumdsp_challan_no(arr[3]);
							sumobj.setSumdsp_lr_no(arr[7]);
							// System.out.println("arr[8]:"+arr[8]);
							if (arr[8].trim().length() > 0) {
								if (!Utility.isThisDateValid(arr[8].trim(), DATE_FORMAT))
									throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
								date = sdf1.parse(arr[8]);
								// System.out.println(date+"comparer:"+date1.compareTo(date));
								if (date1.compareTo(date) > 0) {
									sumobj.setSumdsp_lr_dt(new java.sql.Date(date.getTime()));
									lrNoString = arr[7];
								} else {
									throw new MedicoException(
											" Entered Date is Greater than Current Date Row No.=" + (j + 1));
								}
							} else {
								sumobj.setSumdsp_lr_dt(null);
							}
							sumobj.setSumdsp_transporter(arr[9]);
							sumobj.setSumdsp_wt(
									arr[10].trim().length() > 0 ? new BigDecimal(arr[10]) : BigDecimal.ZERO);
							sumobj.setSumdsp_totwt(
									arr[11].trim().length() > 0 ? new BigDecimal(arr[11]) : BigDecimal.ZERO);
							sumobj.setSumdsp_cases(arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);
							sumobj.setSumdsp_totcases(arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);

							// Add new Columns
							if (arr[24].trim().length() > 0) {
								if (!Utility.isThisDateValid(arr[24].trim(), DATE_FORMAT))
									throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
								date = sdf1.parse(arr[24]);
								if (date1.compareTo(date) > 0) {
									sumobj.setSumdsp_delivery_date(new java.sql.Date(date.getTime()));
								} else {
									throw new MedicoException(
											" Entered Date is Greater than Current Date Row No.=" + (j + 1));
								}
							} else {
								sumobj.setSumdsp_delivery_date(null);
							}
							sumobj.setSumdsp_recd_by(arr[25]);
							sumobj.setSumdsp_remark(arr[26]);
							datasum.add(sumobj);
							oldmst_stn_no = newmst_stn_no;
						} else {
							Sum_Disp_Header_Addl sumobj_adl = new Sum_Disp_Header_Addl();
							sumobj_adl.setSumdsp_addl_id(arr[0] != null ? Long.parseLong(arr[0]) : 0);
							sumobj_adl.setSumdsp_addl_challan_no(arr[3]);
							sumobj_adl.setSumdsp_lr_no(arr[7]);
							if (arr[8].trim().length() > 0) {

								if (!Utility.isThisDateValid(arr[8].trim(), DATE_FORMAT))
									throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
								date = sdf1.parse(arr[8]);

								if (date1.compareTo(date) > 0) {
									sumobj_adl.setSumdsp_lr_dt(new java.sql.Date(date.getTime()));
								} else {
									throw new MedicoException(
											" Entered Date is Greater than Current Date Row No.=" + (j + 1));
								}
							} else {
								sumobj_adl.setSumdsp_lr_dt(null);
							}
							sumobj_adl.setSumdsp_transporter(arr[9]);
							sumobj_adl.setSumdsp_wt(
									arr[10].trim().length() > 0 ? new BigDecimal(arr[10]) : BigDecimal.ZERO);
							sumobj_adl.setSumdsp_totwt(
									arr[11].trim().length() > 0 ? new BigDecimal(arr[11]) : BigDecimal.ZERO);
							sumobj_adl.setSumdsp_cases(arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);
							sumobj_adl.setSumdsp_totcases(arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);

							// Add new Columns
							if (arr[24].trim().length() > 0) {
								if (!Utility.isThisDateValid(arr[24].trim(), DATE_FORMAT))
									throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
								date = sdf1.parse(arr[24]);

								if (date1.compareTo(date) > 0) {
									sumobj_adl.setSumdsp_delivery_date(new java.sql.Date(date.getTime()));
								} else {
									throw new MedicoException(
											" Entered Date is Greater than Current Date Row No.=" + (j + 1));
								}
							} else {
								sumobj_adl.setSumdsp_delivery_date(null);
							}
							sumobj_adl.setSumdsp_recd_by(arr[25]);
							sumobj_adl.setSumdsp_remark(arr[26]);

							// System.out.println("sumobj_adl.getSumdsp_lr_no():"+sumobj_adl.getSumdsp_lr_no());
							datasum_adl.add(sumobj_adl);
							oldmst_stn_no = newmst_stn_no;
						}

					}

					if (bean.getTmpOrActl().trim().equals("A")) {
						Dispatch_Header dspthdr = new Dispatch_Header();
						dspthdr.setDspChallanNo(arr[15]);
						dspthdr.setDsp_sum_dsp_id(Long.parseLong(arr[0]));
						dspthdr.setDsp_transporter(arr[18]);
						dspthdr.setDsp_lr_no(arr[19]);
						if (arr[20].trim().length() > 0) {
							if (!Utility.isThisDateValid(arr[20].trim(), DATE_FORMAT))
								throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
							date = sdf1.parse(arr[20]);
							if (date1.compareTo(date) > 0) {
								dspthdr.setDsp_lr_dt(new java.sql.Date(date.getTime()));
							} else {
								throw new MedicoException(
										" Entered Date is Greater than Current Date Row No.=" + (j + 1));
							}
						} else {
							dspthdr.setDsp_lr_dt(null);
						}
						dspthdr.setDsp_wt(arr[21].trim().length() > 0 ? new BigDecimal(arr[21]) : BigDecimal.ZERO);
						dspthdr.setDsp_billable_wt(
								arr[22].trim().length() > 0 ? new BigDecimal(arr[22]) : BigDecimal.ZERO);
						dspthdr.setDsp_cases(arr[23].trim().length() > 0 ? Integer.parseInt(arr[23]) : 0);
						// dspthdr.setDsp_delivery_date(dsp_delivery_date);
						// Add new COlumns
						if (arr[27].trim().length() > 0) {
							if (!Utility.isThisDateValid(arr[27].trim(), DATE_FORMAT))
								throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
							date = sdf1.parse(arr[27]);

							if (date1.compareTo(date) > 0) {
								dspthdr.setDsp_delivery_date(new java.sql.Date(date.getTime()));
							} else {
								throw new MedicoException(
										" Entered Date is Greater than Current Date Row No.=" + (j + 1));
							}
						} else {
							dspthdr.setDsp_delivery_date(null);
						}
						dspthdr.setDsp_recd_by(arr[28]);
						dspthdr.setDsp_remark(arr[30]);
						dspthdr.setWay_bill_no(arr[29]);
						datadispt.add(dspthdr);
					} else {
						Dispatch_Header_Addl dspthdr_adl = new Dispatch_Header_Addl();
						dspthdr_adl.setDsp_challan_no(arr[15]);
						dspthdr_adl.setDsp_sum_dsp_addl_id(Long.parseLong(arr[0]));
						dspthdr_adl.setDsp_transporter(arr[18]);
						dspthdr_adl.setDsp_lr_no(arr[19]);
						if (arr[20].trim().length() > 0) {
							if (!Utility.isThisDateValid(arr[20].trim(), DATE_FORMAT))
								throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
							date = sdf1.parse(arr[20]);
							if (date1.compareTo(date) > 0) {
								dspthdr_adl.setDsp_lr_dt(new java.sql.Date(date.getTime()));
								// System.out.println("Date
								// 1--:"+dspthdr_adl.getDsp_lr_dt());
							} else {
								throw new MedicoException(
										" Entered Date is Greater than Current Date Row No.=" + (j + 1));
							}
						} else {
							dspthdr_adl.setDsp_lr_dt(null);
						}
						dspthdr_adl.setDsp_wt(arr[21].trim().length() > 0 ? new BigDecimal(arr[21]) : BigDecimal.ZERO);
						dspthdr_adl.setDsp_billable_wt(
								arr[22].trim().length() > 0 ? new BigDecimal(arr[22]) : BigDecimal.ZERO);
						dspthdr_adl.setDsp_cases(arr[23].trim().length() > 0 ? Integer.parseInt(arr[23]) : 0);

						// Add new COlumns
						if (arr[27].trim().length() > 0) {
							if (!Utility.isThisDateValid(arr[27].trim(), DATE_FORMAT))
								throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
							date = sdf1.parse(arr[27]);

							if (date1.compareTo(date) > 0) {
								dspthdr_adl.setDsp_delivery_date(new java.sql.Date(date.getTime()));
							} else {
								throw new MedicoException(
										" Entered Date is Greater than Current Date Row No.=" + (j + 1));
							}
						} else {
							dspthdr_adl.setDsp_delivery_date(null);
						}
						dspthdr_adl.setDsp_recd_by(arr[28]);
						dspthdr_adl.setDsp_remark(arr[30]);
						dspthdr_adl.setWay_bill_no(arr[29]);
						datadispt_adl.add(dspthdr_adl);
					}
					lrcsvDownload.setSum_hd_id(arr[0]);
					lrcsvDownload.setTeam_name(arr[1]);
					lrcsvDownload.setTeam_code(arr[2]);
					lrcsvDownload.setMst_stn_no(arr[3]);
					System.out.println("arr[4]:" + arr[4]);
					lrcsvDownload.setMst_stn_dt(
							(arr[4].trim().length() > 0) ? new java.sql.Date(sdf1.parse(arr[4]).getTime()) : null);
					lrcsvDownload.setMst_destination(arr[5]);
					lrcsvDownload.setStn_value(arr[6]);
					lrcsvDownload.setMst_lr_no(lrNoString);
					lrcsvDownload.setMst_lr_dt(
							arr[8].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[8]).getTime()) : null);
					lrcsvDownload.setMst_transporter(arr[9]);
					lrcsvDownload.setMst_grs_wght(arr[10]);
					lrcsvDownload.setMst_billwght(arr[11]);
					lrcsvDownload.setMst_tot_case(arr[12]);
					lrcsvDownload.setDspfstaff_employeeno(arr[13]);
					lrcsvDownload.setDspfstaff_displayname(arr[14]);
					lrcsvDownload.setDtl_chln_no(arr[15]);
					lrcsvDownload.setDtl_chln_dt(
							arr[16].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[16]).getTime()) : null);
					lrcsvDownload.setDtl_city(arr[17]);
					lrcsvDownload.setDtl_transporter(arr[18]);
					lrcsvDownload.setDtl_lr_no(arr[19]);
					lrcsvDownload.setDtl_lr_dt(
							arr[20].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[20]).getTime()) : null);
					lrcsvDownload.setDtl_stn_grswght(arr[21]);
					lrcsvDownload.setDtl_stn_billwght(arr[22]);
					lrcsvDownload.setDtl_tot_case(arr[23]);
					lrcsvDownload.setSr_no(++i);

					lrcsvDownload.setSumdsp_delivery_date(
							arr[24].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[24]).getTime()) : null);
					lrcsvDownload.setSumdsp_recd_by(arr[25]);
					lrcsvDownload.setSumdsp_remark(arr[26]);

					lrcsvDownload.setDsp_delivery_date(
							arr[27].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[27]).getTime()) : null);
					lrcsvDownload.setDsp_recd_by(arr[28]);
					lrcsvDownload.setWay_bill_no(arr[29]);
					lrcsvDownload.setDsp_remark(arr[30]);
					lrcsvDwnList.add(lrcsvDownload);
				} else {
					bean.setMsg("Please check file properly column data is exceeds than no. of records.");
				}
			}
			if (bean.getTmpOrActl().trim().equals("A")) {
				this.dispatchRepository.uploadlrCsvData(datasum, datadispt, finyr, bean);
			} else {
				this.dispatchRepository.uploadlrCsvDataInAddl(datasum_adl, datadispt_adl);
			}
			this.dispatchRepository.truncateLrCSVUpload();
			this.dispatchRepository.saveLrCSVUpload(lrcsvDwnList);
			bean.setMsg("File Uploaded Successfully");
//	    if (stock_at_cfa.equalsIgnoreCase("Y")) {
//			challansForStockAtCfaList = new ArrayList<>();
//			challansForStockAtCfaList = getChallansForStockAtCfa();
//		    }

		} catch (MedicoException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(+j + " :" + e.getMessage());
		}
		return j;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void updateGSTinSummDispForCFA(Long sum_challan_no, String comp_cd) throws Exception {

		String gst_type = null;
		String gst_doc_type = null;
		Sum_Disp_Header sum_dsp_hdr_obj = null;
		String gst_type_ind = "";
		Boolean applyZeroGST = false;
		String gst_req_ind = null;
		Location location_obj = null;
		Depot_Locations depot_locations_obj = null;
		BigDecimal[] detail_sum = new BigDecimal[3];
		Arrays.fill(detail_sum, BigDecimal.ZERO);
		sum_dsp_hdr_obj = dispatchRepository.getObjectById(sum_challan_no);
		// get
		// sending,recv.
		// loc,goods
		// Value
		if (sum_dsp_hdr_obj != null) {
			BigDecimal tot_goods_val = sum_dsp_hdr_obj.getSumdsp_total_goods_val().setScale(2, RoundingMode.HALF_EVEN);
			if (sum_dsp_hdr_obj.getSumdsp_loc_id() != null) {

				location_obj = this.locationRepository.getObjectById(sum_dsp_hdr_obj.getSumdsp_loc_id().getLoc_id());
				// sending loc's state
				/*
				 * System.err.println("Sending _loc_id()::::::::"
				 * +sum_dsp_hdr_obj.getSumdsp_loc_id() + "Receiving _loc_id():::::::"
				 * +sum_dsp_hdr_obj.getSumdsp_recvg_loc_id());
				 */
			} else
				throw new MedicoException("Sending location not found.");
			if (sum_dsp_hdr_obj.getSumdsp_recvg_loc_id() != null) {

				depot_locations_obj = depotLocationRepository.getObjectById(sum_dsp_hdr_obj.getSumdsp_recvg_loc_id());
				// receiving
				// loc's
				// state
				gst_req_ind = depot_locations_obj.getGst_req_ind();
			} else
				throw new MedicoException("Receiving location not found.");
			if (gst_req_ind.trim().equalsIgnoreCase("Y")) {
				if (location_obj.getLoc_state_id().compareTo(depot_locations_obj.getDptstate_id()) == 0) {
					gst_type_ind = "G";
					if (location_obj.getGst_reg_no() != null && depot_locations_obj.getGst_reg_no() != null
							&& location_obj.getGst_reg_no().trim()
									.equalsIgnoreCase(depot_locations_obj.getGst_reg_no().trim())) {
						// System.err.println("BILL OF
						// SUPPLY:::::::::::::::::::::");
						gst_type = "BS"; /// Bill of supply
						applyZeroGST = true;
					} else if (depot_locations_obj.getGst_reg_no() != null
							&& depot_locations_obj.getGst_reg_no() != "") {
						gst_type = "SN"; /// intra state normal
					} else {
						gst_type = "SR"; /// intra state no GSTIN
					}
				} else {
					gst_type_ind = "I";
					if (depot_locations_obj.getGst_reg_no() != null && depot_locations_obj.getGst_reg_no() != "") {
						gst_type = "IN"; /// inter state normal
					} else {
						gst_type = "IR"; /// inter state no GSTIN
					}
				}
				// System.out.println("CALLING GST_DOC_TYPE
				// gst_type:::::::::::"+gst_type);
				gst_doc_type = this.calculateGstRepository.getGST_Doc_type_Para_code(MedicoConstants.DISPATCH,
						gst_type);

				// System.out.println("PRINTING GST DOC
				// TYPE======================" + gst_doc_type);
				if (gst_doc_type == null || gst_doc_type.equals(""))
					throw new MedicoException("GST DOC TYPE not found!!");

				BigDecimal cgst_rate = BigDecimal.ZERO;
				BigDecimal sgst_rate = BigDecimal.ZERO;
				BigDecimal igst_rate = BigDecimal.ZERO;

				// System.out.println("BEFORE CALLING GST TAX
				// CAL::::::applyZeroGST"+applyZeroGST);
				List<Sum_Disp_Detail> dtl_list = dispatchRepository.getSumDtlListByHdrId(sum_challan_no);
				BigDecimal total_value_after_tax = BigDecimal.ZERO;
				total_value_after_tax = BigDecimal.ZERO;
				BigDecimal rounded_value = BigDecimal.ZERO;
				BigDecimal round_of_val = BigDecimal.ZERO;
				int counter = 0;
				Object[] obj = new Object[12];
				if (!applyZeroGST) {
					for (Sum_Disp_Detail details : dtl_list) {

						TaxParamBean taxparam = calculateGstRepository.getTaxParamsByStateIdAndProdId(
								depot_locations_obj.getDptstate_id(), details.getSumdspdtl_prod_id());
						obj[0] = taxparam.getProd_code();
						obj[1] = taxparam.getOutput_tax_param();
						obj[2] = taxparam.getSt_vat();
						obj[3] = taxparam.getCst_rt();
						obj[4] = taxparam.getSurch();
						obj[5] = taxparam.getIc_chgs();
						obj[6] = taxparam.getCess();
						obj[7] = taxparam.getTo_tax();
						obj[8] = taxparam.getProd_disc();
						obj[9] = taxparam.getCgst();
						obj[10] = taxparam.getIgst();
						obj[11] = taxparam.getSgst();

						cgst_rate = obj[9] != null ? new BigDecimal(obj[9].toString()) : BigDecimal.ZERO;
						igst_rate = obj[10] != null ? new BigDecimal(obj[10].toString()) : BigDecimal.ZERO;
						sgst_rate = obj[11] != null ? new BigDecimal(obj[11].toString()) : BigDecimal.ZERO;

						TaxCalculationBean taxBean = this.calculateGstService.generateGstValues(
								MedicoConstants.DISPATCH, // TODO need to confirm this
								gst_type_ind, // gst_type_ind
								details.getSumdspdtl_disp_qty(), // billedQty
								BigDecimal.ZERO, // freeQty
								BigDecimal.ZERO, // replQty
								details.getSumdspdtl_rate(), // rate
								BigDecimal.ZERO, // party discount
								obj[1].toString(), // Out_Tax_Param,
								null, // In_Tax_Param
								cgst_rate, // cgst_rate
								igst_rate, // igst_rate
								sgst_rate, // sgst_rate
								BigDecimal.ZERO, // Surch_Rate
								BigDecimal.ZERO, // Cess_Rate
								BigDecimal.ZERO, // TOT_Rate
								BigDecimal.ZERO, // Prod_Disc
								BigDecimal.ZERO, // Octroi_Rate
								"000", // Cust_Type
								BigDecimal.ZERO, // mrp_diff
								BigDecimal.ZERO, // trade discount
								comp_cd, // Comp cd
								BigDecimal.ZERO, // mrp rate
								BigDecimal.ZERO, BigDecimal.ZERO); // SS RATE

						details.setSGST_BILL_AMT(taxBean.getSgst_bill_amount());
						details.setSGST_RATE(taxBean.getSgst_rate());
						details.setCGST_BILL_AMT(taxBean.getCgst_bill_amount());
						details.setCGST_RATE(taxBean.getCgst_rate());
						details.setIGST_BILL_AMT(taxBean.getIgst_bill_amount());
						details.setIGST_RATE(taxBean.getIgst_rate());
						details.setGst_doc_type(gst_doc_type);

						detail_sum[0] = detail_sum[0]
								.add(details.getSGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));
						detail_sum[1] = detail_sum[1]
								.add(details.getCGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));
						detail_sum[2] = detail_sum[2]
								.add(details.getIGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));

						dispatchRepository.updateGstVal(details, counter);

					}

					total_value_after_tax = tot_goods_val.add(detail_sum[0]).add(detail_sum[1]).add(detail_sum[2])
							.setScale(2, RoundingMode.HALF_EVEN);
					total_value_after_tax = total_value_after_tax.setScale(2, RoundingMode.HALF_EVEN);
					rounded_value = total_value_after_tax.setScale(0, RoundingMode.HALF_EVEN);
					round_of_val = rounded_value.subtract(total_value_after_tax);

					/*
					 * System.out.println("=====tot_goods_val======="+
					 * tot_goods_val+"=====total_value_after_tax======"+ total_value_after_tax
					 * +"=====rounded_value======"+rounded_value+
					 * "======roundof_value======"+round_of_val);
					 */
					dispatchRepository.updateSum_disp_hdrGstVal(detail_sum[0], detail_sum[1], detail_sum[2],
							gst_doc_type, sum_challan_no, round_of_val);
				} else {
					for (Sum_Disp_Detail details : dtl_list) {
						// System.out.println("INSIDE CALLING GST CALCULATION
						// FOR BILL OF SUPPLY^^^^^^^^^"+dtl_list.size());

						details.setSGST_BILL_AMT(BigDecimal.ZERO);
						details.setSGST_RATE(BigDecimal.ZERO);
						details.setCGST_BILL_AMT(BigDecimal.ZERO);
						details.setCGST_RATE(BigDecimal.ZERO);
						details.setIGST_BILL_AMT(BigDecimal.ZERO);
						details.setIGST_RATE(BigDecimal.ZERO);
						details.setGst_doc_type(gst_doc_type);

						detail_sum[0] = BigDecimal.ZERO;
						detail_sum[1] = BigDecimal.ZERO;
						detail_sum[2] = BigDecimal.ZERO;

						dispatchRepository.updateGstVal(details, counter);

					}
					round_of_val = BigDecimal.ZERO;

					dispatchRepository.updateSum_disp_hdrGstVal(detail_sum[0], detail_sum[1], detail_sum[2],
							gst_doc_type, sum_challan_no, round_of_val);
				}

			}
		} else {
			throw new MedicoException("Summary Dispatch header record not found!!");
		}

	}

	@Override
	public List<Dispatch_Header> getDspHeaderForDelete(String finYr, String currentPeriod, Long divId, String from_dt,
			String to_dt, Long locId) throws Exception {
		return dispatchRepository.getDspHeaderForDelete(finYr, currentPeriod, divId, from_dt, to_dt, locId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void submitdeleteChallan(DispatchBean bean) throws Exception {

		for (int i = 0; i < bean.getDspIds().size(); i++) {
			bean.setDsp_id(bean.getDspIds().get(i));
			System.out.println("dspid:" + bean.getDsp_id());
			dispatchRepository.submitdeleteChallan(bean);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public long dpLrUploadRevised(DispatchBean bean) throws Exception {
		BufferedReader br = null;
		long j = 0l;
		try {

			Company company = companyMasterService.getCompanyDetails();

			if (bean.getStockAtCfa() == null) {
				bean.setStockAtCfa("N");
			}
			if (bean.getStockAtCfa().equalsIgnoreCase("Y")) {
				bean.setStockAtCfa("stock_at_cfa");
			} else {
				bean.setStockAtCfa("Upload");
			}
			if (bean.getTmpOrActl() == null) {
				bean.setTmpOrActl("A");
			}

			String gprmInd = parameterRepository.getGprmIndicator();

			File convFile = new File(
					System.getProperty("java.io.tmpdir") + "/" + bean.getDpLrUploadFile().getOriginalFilename());
			bean.getDpLrUploadFile().transferTo(convFile);
			br = new BufferedReader(new FileReader(convFile));
			String[] header = br.readLine().split(",");
			String record = "";
			Set<String> setlr = new TreeSet<String>();
			String oldmst_stn_no = "", newmst_stn_no = "";
			List<Sum_Disp_Header> datasum = new ArrayList<Sum_Disp_Header>();
			List<Dispatch_Header> datadispt = new ArrayList<Dispatch_Header>();
			List<Sum_Disp_Header_Addl> datasum_adl = new ArrayList<Sum_Disp_Header_Addl>();
			List<Dispatch_Header_Addl> datadispt_adl = new ArrayList<Dispatch_Header_Addl>();

			List<Sum_Disp_Header_arc> datasum_arc = new ArrayList<Sum_Disp_Header_arc>();
			List<Dispatch_Header_arc> datadispt_arc = new ArrayList<Dispatch_Header_arc>();
			Transporter_master transporter = null;
			// br.readLine();
			List<LrCsvUpload> lrcsvDwnList = new ArrayList<LrCsvUpload>();
			String lrNoString = "";
			long i = 0l;
			final String DATE_FORMAT = "dd/MM/yyyy";
			// final String DATE_FORMAT1 = "dd-MM-yyyy";
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

			String fin_year_flag = null;
			String fin_year = null;

			while ((record = br.readLine()) != null) {
				j++;
				LrCsvUpload lrcsvDownload = new LrCsvUpload();
				System.out.println("record:" + record);
				String[] arr = record.split(",");
				System.out.println("arr:" + arr.length);
//				if (bean.getCompanyCode().trim().equals(VET)) {
//					if (arr.length != 41) {
//						throw new MedicoException("Please fill Detail Remark of Row No2.=" + (j + 1));
//					}
//				} else  {
//					
//					if (gprmInd.equalsIgnoreCase(Y) && company.getCfa_to_stk_ind().equals("Y") ) { // changed ABHISHEK 16 dec
//						if (arr.length != 39) {//change by yash 15Jul
//							throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
//						}
//						if (arr[38]==null) {//change by yash 15Jul
//							throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
//						}
//					} else {
//						if (arr.length != 38) {//change by yash 15Jul
//							throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
//						}
//						if (arr[36]==null) {//change by yash 15Jul
//							throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
//						}
//					}
//				}

//				 added by nilesh 15 jan (new condition)

				if (bean.getCompanyCode().trim().equals(VET)) {
					if (arr.length != 41) {
						throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
					}
				} else if (bean.getCompanyCode().trim().equals(PFZ)) {
					if (company.getCfa_to_stk_ind().equals("Y")) { // for pfz if its stkcfa
						if (gprmInd.equalsIgnoreCase("Y")) {
							if (arr.length != 40) {
								throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
							}
						} else {
							if (arr.length != 39) {
								throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
							}
						}
					} else {
						if (gprmInd.equalsIgnoreCase("Y")) { // for pfz if its sg
							if (arr.length != 40) {
								throw new MedicoException("Please fill Transport Mode of Row No.=" + (j + 1));
							}
						} else {
							if (arr.length != 38) {
								throw new MedicoException("Please fill Transport Mode of Row No.=" + (j + 1));
							}
						}
					}
				} else {
					if (company.getCfa_to_stk_ind().equals("Y")) { // other company if its stkcfa
						if (gprmInd.equalsIgnoreCase("Y")) {
							if (arr.length != 39) {
								throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
							}
						} else {
							if (arr.length != 38) {
								throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
							}
						}
					} else { // other company if its sg
						if (gprmInd.equalsIgnoreCase("Y")) {
							if (arr.length != 38) {
								throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
							}
						} else {
							if (arr.length != 37) {
								throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
							}
						}
					}
				}

				if (!bean.getCompanyCode().trim().equals(VET) && arr[36] == null) {
					throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
				}

				fin_year = arr[36];
				if (bean.getFinyr().equals(arr[36])) {
					fin_year_flag = "CURRENT";
				} else {
					fin_year_flag = "PREVIOUS";
				}

				if (arr[0].trim().length() > 0) {
					//
					newmst_stn_no = arr[0];
					java.util.Date date = null;
					java.util.Date date1 = new Date();
					java.util.Date date2 = null;
					java.util.Date date3 = null;
					java.util.Date dtl_lr_date3 = null;
					java.util.Date mst_lr_date3 = null;
					if (!newmst_stn_no.equalsIgnoreCase(oldmst_stn_no)) {

						if (fin_year_flag.equals(MedicoConstants.CURRENT)) {

							if (bean.getTmpOrActl().trim().equals("A")) {
								transporter = dispatchRepository.getTransporterMaster(arr[24].trim().toUpperCase());

								if (arr[7].trim().length() > 0 && arr[8].trim().length() > 0
										&& arr[9].trim().length() > 0
										|| arr[9].trim().length() > 0 && transporter != null
										|| bean.getCompanyCode().trim().equals(VET)) { /// If Transporter , LR dt,Lr no
																						/// not blank
									Sum_Disp_Header sumobj = new Sum_Disp_Header();
									sumobj.setSumdsp_id(
											(arr[0] != null && arr[0].trim().length() > 0) ? Long.parseLong(arr[0])
													: 0);
									sumobj.setSumdsp_challan_no(arr[3]);
									sumobj.setSumdsp_lr_no(arr[7]);
									// System.out.println("arr[8]:"+arr[8]);
									if (arr[8].trim().length() > 0) {
										/////////
										if (arr[8].trim().contains("-")) {
											arr[8] = arr[8].replace("-", "/");
											System.out.println("Arr[8] :: " + arr[8]);
										}
										/////////
										if (!Utility.isThisDateValid(arr[8].trim(), DATE_FORMAT))
											throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
										date = sdf1.parse(arr[8]);
										// System.out.println(date+"comparer:"+date1.compareTo(date));
										if (date1.compareTo(date) > 0) {
											sumobj.setSumdsp_lr_dt(new java.sql.Date(date.getTime()));
											lrNoString = arr[7];
										} else {
											throw new MedicoException(
													" Entered Date is Greater than Current Date Row No.=" + (j + 1)
															+ " " + arr[8]);
										}
									} else {
										sumobj.setSumdsp_lr_dt(null);
									}

									// Transporter
									if (arr[7].trim().length() > 0 && arr[8].trim().length() > 0
											&& arr[9].trim().length() > 0) {
										if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
											sumobj.setSumdsp_transporter(bean.getUsername().toUpperCase());
										} else {
											sumobj.setSumdsp_transporter(arr[9].toUpperCase());
										}
									} else {
										sumobj.setSumdsp_transporter(arr[9]);
									}
									sumobj.setSumdsp_wt(
											arr[10].trim().length() > 0 ? new BigDecimal(arr[10]) : BigDecimal.ZERO);
									sumobj.setSumdsp_totwt(
											arr[11].trim().length() > 0 ? new BigDecimal(arr[11]) : BigDecimal.ZERO);
									sumobj.setSumdsp_cases(arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);
									sumobj.setSumdsp_totcases(
											arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);

									// Add new Columns
									if (arr[13].trim().length() > 0) {
										///////
										if (arr[13].trim().contains("-")) {
											arr[13] = arr[13].replace("-", "/");
										}
										///////
										if (!Utility.isThisDateValid(arr[13].trim(), DATE_FORMAT))
											throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
										date = sdf1.parse(arr[13]);
										mst_lr_date3 = sdf1.parse(arr[8]);
										if (mst_lr_date3.compareTo(date) <= 0) {
											sumobj.setSumdsp_delivery_date(new java.sql.Date(date.getTime()));
										} else {
											throw new MedicoException(
													"Summary Tenetative Date is Smaller than LR Date Row No.="
															+ (j + 1));
										}
									} else {
										sumobj.setSumdsp_delivery_date(null);
									}
									sumobj.setSumdsp_recd_by(arr[14]);

									if (arr[15].trim().length() > 0) { // Actual Date
										///////
										if (arr[15].trim().contains("-")) {
											arr[15] = arr[15].replace("-", "/");
										}
										///////
										if (!Utility.isThisDateValid(arr[15].trim(), DATE_FORMAT))
											throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1)
													+ "   " + arr[15].trim());
										date2 = sdf1.parse(arr[15]);
										// if (date1.compareTo(date2) < 0) {
										sumobj.setActual_delivery_date(new java.sql.Date(date2.getTime()));
										// } else {
										// throw new MedicoException(
										// " Entered Date is Smaller than Current Date Row No.=" + (j + 1)+"
										// "+arr[15].trim());
										// }
									} else {
										sumobj.setActual_delivery_date(null);
									}
									sumobj.setWay_bill_no(arr[16]);
									sumobj.setCourier_expenses(
											arr[17].trim().length() > 0 ? new BigDecimal(arr[17]) : BigDecimal.ZERO);
									sumobj.setSumdsp_remark(arr[18]);
									if (bean.getCompanyCode().trim().equals(VET)) {
										sumobj.setTrans_gst_reg_no(arr[37]);
										sumobj.setSumdsp_lorry_no(arr[38]);
									}

									// change by nilesh 15Jan
									if (bean.getCompanyCode().trim().equals(PFZ)) {
										if (company.getCfa_to_stk_ind().equals(N)) {
											if (gprmInd.equalsIgnoreCase(Y)) {
												sumobj.setTransport_mode(arr[38]);
											} else {
												sumobj.setTransport_mode(arr[37]);
											}
										} else {
											if (gprmInd.equalsIgnoreCase(Y)) {
												sumobj.setTransport_mode(arr[39]);
											} else {
												sumobj.setTransport_mode(arr[38]);
											}

										}
									}

									datasum.add(sumobj);

								}
								oldmst_stn_no = newmst_stn_no;
							} else {

								Sum_Disp_Header_Addl sumobj_adl = new Sum_Disp_Header_Addl();
								sumobj_adl.setSumdsp_addl_id(arr[0] != null ? Long.parseLong(arr[0]) : 0);
								sumobj_adl.setSumdsp_addl_challan_no(arr[3]);
								sumobj_adl.setSumdsp_lr_no(arr[7]);
								if (arr[8].trim().length() > 0) {
									///////////
									if (arr[8].trim().contains("-")) {
										arr[8] = arr[8].replace("-", "/");
									}
									//////////
									if (!Utility.isThisDateValid(arr[8].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
									date = sdf1.parse(arr[8]);

									if (date1.compareTo(date) > 0) {
										sumobj_adl.setSumdsp_lr_dt(new java.sql.Date(date.getTime()));
									} else {
										throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
												+ (j + 1) + " " + arr[8]);
									}
								} else {
									sumobj_adl.setSumdsp_lr_dt(null);
								}

								if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
									sumobj_adl.setSumdsp_transporter(bean.getUsername());
								} else {
									sumobj_adl.setSumdsp_transporter(arr[9]);
								}
								sumobj_adl.setSumdsp_wt(
										arr[10].trim().length() > 0 ? new BigDecimal(arr[10]) : BigDecimal.ZERO);
								sumobj_adl.setSumdsp_totwt(
										arr[11].trim().length() > 0 ? new BigDecimal(arr[11]) : BigDecimal.ZERO);
								sumobj_adl.setSumdsp_cases(arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);
								sumobj_adl.setSumdsp_totcases(
										arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);

								// Add new Columns
								if (arr[13].trim().length() > 0) {
									//////////
									if (arr[13].trim().contains("-")) {
										arr[13] = arr[13].replace("-", "/");
									}
									//////////
									if (!Utility.isThisDateValid(arr[13].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
									date = sdf1.parse(arr[13]);

									if (date1.compareTo(date) > 0) {
										sumobj_adl.setSumdsp_delivery_date(new java.sql.Date(date.getTime()));
									} else {
										throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
												+ (j + 1) + " " + arr[13]);
									}
								} else {
									sumobj_adl.setSumdsp_delivery_date(null);
								}

								sumobj_adl.setSumdsp_recd_by(arr[14]);
								sumobj_adl.setSumdsp_remark(arr[18]);

								// System.out.println("sumobj_adl.getSumdsp_lr_no():"+sumobj_adl.getSumdsp_lr_no());
								datasum_adl.add(sumobj_adl);
								oldmst_stn_no = newmst_stn_no;
							}

						} else {
							//////// prev year

							if (bean.getTmpOrActl().trim().equals("A")) {
								transporter = dispatchRepository.getTransporterMaster(arr[24].trim().toUpperCase());

								if (arr[7].trim().length() > 0 && arr[8].trim().length() > 0
										&& arr[9].trim().length() > 0
										|| arr[9].trim().length() > 0 && transporter != null
										|| bean.getCompanyCode().trim().equals(VET)) { /// If Transporter , LR dt,Lr no
																						/// not blank
									Sum_Disp_Header_arc sumobj = new Sum_Disp_Header_arc();
									sumobj.setSumdsp_id(
											(arr[0] != null && arr[0].trim().length() > 0) ? Long.parseLong(arr[0])
													: 0);
									sumobj.setSumdsp_challan_no(arr[3]);
									sumobj.setSumdsp_lr_no(arr[7]);
									// System.out.println("arr[8]:"+arr[8]);
									if (arr[8].trim().length() > 0) {
										/////////
										if (arr[8].trim().contains("-")) {
											arr[8] = arr[8].replace("-", "/");
										}
										////////
										if (!Utility.isThisDateValid(arr[8].trim(), DATE_FORMAT))
											throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
										date = sdf1.parse(arr[8]);
										// System.out.println(date+"comparer:"+date1.compareTo(date));
										if (date1.compareTo(date) > 0) {
											sumobj.setSumdsp_lr_dt(new java.sql.Date(date.getTime()));
											lrNoString = arr[7];
										} else {
											throw new MedicoException(
													" Entered Date is Greater than Current Date Row No.=" + (j + 1)
															+ " " + arr[8]);
										}
									} else {
										sumobj.setSumdsp_lr_dt(null);
									}

									// Transporter
									if (arr[7].trim().length() > 0 && arr[8].trim().length() > 0
											&& arr[9].trim().length() > 0) {
										if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
											sumobj.setSumdsp_transporter(bean.getUsername().toUpperCase());
										} else {
											sumobj.setSumdsp_transporter(arr[9].toUpperCase());
										}
									} else {
										sumobj.setSumdsp_transporter(arr[9]);
									}
									sumobj.setSumdsp_wt(
											arr[10].trim().length() > 0 ? new BigDecimal(arr[10]) : BigDecimal.ZERO);
									sumobj.setSumdsp_totwt(
											arr[11].trim().length() > 0 ? new BigDecimal(arr[11]) : BigDecimal.ZERO);
									sumobj.setSumdsp_cases(arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);
									sumobj.setSumdsp_totcases(
											arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);

									// Add new Columns
									if (arr[13].trim().length() > 0) {
										////////////
										if (arr[13].trim().contains("-")) {
											arr[13] = arr[13].replace("-", "/");
										}
										////////////
										if (!Utility.isThisDateValid(arr[13].trim(), DATE_FORMAT))
											throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
										date = sdf1.parse(arr[13]);
										mst_lr_date3 = sdf1.parse(arr[8]);
										if (mst_lr_date3.compareTo(date) <= 0) {
											sumobj.setSumdsp_delivery_date(new java.sql.Date(date.getTime()));
										} else {
											throw new MedicoException(
													"Summary Tenetative Date is Smaller than LR Date Row No.="
															+ (j + 1));
										}
									} else {
										sumobj.setSumdsp_delivery_date(null);
									}
									sumobj.setSumdsp_recd_by(arr[14]);

									if (arr[15].trim().length() > 0) { // Actual Date
										///////////
										if (arr[15].trim().contains("-")) {
											arr[15] = arr[15].replace("-", "/");
										}
										///////////
										if (!Utility.isThisDateValid(arr[15].trim(), DATE_FORMAT))
											throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1)
													+ "   " + arr[15].trim());
										date2 = sdf1.parse(arr[15]);
										// if (date1.compareTo(date2) < 0) {
										sumobj.setActual_delivery_date(new java.sql.Date(date2.getTime()));
										// } else {
										// throw new MedicoException(
										// " Entered Date is Smaller than Current Date Row No.=" + (j + 1)+"
										// "+arr[15].trim());
										// }
									} else {
										sumobj.setActual_delivery_date(null);
									}
									sumobj.setWay_bill_no(arr[16]);
									sumobj.setCourier_expenses(
											arr[17].trim().length() > 0 ? new BigDecimal(arr[17]) : BigDecimal.ZERO);
									sumobj.setSumdsp_remark(arr[18]);
									if (bean.getCompanyCode().trim().equals(VET)) {
										sumobj.setTrans_gst_reg_no(arr[37]);
										sumobj.setSumdsp_lorry_no(arr[38]);
									}

									// change by nilesh 15Jan
									if (bean.getCompanyCode().trim().equals(PFZ)) {
										if (company.getCfa_to_stk_ind().equals(N)) {
											if (gprmInd.equalsIgnoreCase(Y)) {
												sumobj.setTransport_mode(arr[38]);
											} else {
												sumobj.setTransport_mode(arr[37]);
											}
										} else {
											if (gprmInd.equalsIgnoreCase(Y)) {
												sumobj.setTransport_mode(arr[39]);
											} else {
												sumobj.setTransport_mode(arr[38]);
											}

										}
									}

									datasum_arc.add(sumobj);

								}
								oldmst_stn_no = newmst_stn_no;
							} else {

								Sum_Disp_Header_Addl sumobj_adl = new Sum_Disp_Header_Addl();
								sumobj_adl.setSumdsp_addl_id(arr[0] != null ? Long.parseLong(arr[0]) : 0);
								sumobj_adl.setSumdsp_addl_challan_no(arr[3]);
								sumobj_adl.setSumdsp_lr_no(arr[7]);
								if (arr[8].trim().length() > 0) {
									//////////////
									if (arr[8].trim().contains("-")) {
										arr[8] = arr[8].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[8].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
									date = sdf1.parse(arr[8]);

									if (date1.compareTo(date) > 0) {
										sumobj_adl.setSumdsp_lr_dt(new java.sql.Date(date.getTime()));
									} else {
										throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
												+ (j + 1) + " " + arr[8]);
									}
								} else {
									sumobj_adl.setSumdsp_lr_dt(null);
								}

								if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
									sumobj_adl.setSumdsp_transporter(bean.getUsername());
								} else {
									sumobj_adl.setSumdsp_transporter(arr[9]);
								}
								sumobj_adl.setSumdsp_wt(
										arr[10].trim().length() > 0 ? new BigDecimal(arr[10]) : BigDecimal.ZERO);
								sumobj_adl.setSumdsp_totwt(
										arr[11].trim().length() > 0 ? new BigDecimal(arr[11]) : BigDecimal.ZERO);
								sumobj_adl.setSumdsp_cases(arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);
								sumobj_adl.setSumdsp_totcases(
										arr[12].trim().length() > 0 ? Integer.parseInt(arr[12]) : 0);

								// Add new Columns
								if (arr[13].trim().length() > 0) {
									//////////////
									if (arr[13].trim().contains("-")) {
										arr[13] = arr[13].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[13].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
									date = sdf1.parse(arr[13]);

									if (date1.compareTo(date) > 0) {
										sumobj_adl.setSumdsp_delivery_date(new java.sql.Date(date.getTime()));
									} else {
										throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
												+ (j + 1) + " " + arr[13]);
									}
								} else {
									sumobj_adl.setSumdsp_delivery_date(null);
								}

								sumobj_adl.setSumdsp_recd_by(arr[14]);
								sumobj_adl.setSumdsp_remark(arr[18]);

								// System.out.println("sumobj_adl.getSumdsp_lr_no():"+sumobj_adl.getSumdsp_lr_no());
								datasum_adl.add(sumobj_adl);
								oldmst_stn_no = newmst_stn_no;
							}

						}
					}

					if (fin_year_flag.equals(MedicoConstants.CURRENT)) {
						if (bean.getTmpOrActl().trim().equals("A")) {
							transporter = dispatchRepository.getTransporterMaster(arr[24].trim().toUpperCase());

							if (arr[24].trim().length() > 0 && arr[25].trim().length() > 0
									&& arr[26].trim().length() > 0
									|| arr[24].trim().length() > 0 && transporter != null) { /// If Transporter , LR
																								/// dt,Lr no not blank
								Dispatch_Header dspthdr = new Dispatch_Header();
								dspthdr.setDspChallanNo(arr[21]);
								dspthdr.setDsp_sum_dsp_id(Long.parseLong(arr[0]));

								// Transporter
								if (arr[24].trim().length() > 0 && arr[25].trim().length() > 0
										&& arr[26].trim().length() > 0) {
									if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
										dspthdr.setDsp_transporter(bean.getUsername().toUpperCase());
									} else {
										dspthdr.setDsp_transporter(arr[24].toUpperCase());
									}
								} else {
									dspthdr.setDsp_transporter(arr[24]);
								}
								dspthdr.setDsp_lr_no(arr[25]);
								if (arr[26].trim().length() > 0) {
									//////////////
									if (arr[26].trim().contains("-")) {
										arr[26] = arr[26].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[26].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
									date = sdf1.parse(arr[26]);
									if (date1.compareTo(date) > 0) {
										dspthdr.setDsp_lr_dt(new java.sql.Date(date.getTime()));
									} else {
										throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
												+ (j + 1) + " " + arr[26]);
									}
								} else {
									dspthdr.setDsp_lr_dt(null);
								}

								if (arr[27].trim().length() > 0) { // Tentative
									//////////////
									if (arr[27].trim().contains("-")) {
										arr[27] = arr[27].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[27].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
									date = sdf1.parse(arr[27]);
									dtl_lr_date3 = sdf1.parse(arr[26]);
									if (dtl_lr_date3.compareTo(date) <= 0) {
										dspthdr.setDsp_delivery_date(new java.sql.Date(date.getTime()));
									} else {
										throw new MedicoException(
												"Tentative Date is Smaller than LR Date Row No.=" + (j + 1));
									}
								} else {
									dspthdr.setDsp_delivery_date(null);
								}

								dspthdr.setDsp_wt(
										arr[28].trim().length() > 0 ? new BigDecimal(arr[28]) : BigDecimal.ZERO);
								dspthdr.setDsp_billable_wt(
										arr[29].trim().length() > 0 ? new BigDecimal(arr[29]) : BigDecimal.ZERO);
								dspthdr.setDsp_cases(arr[30].trim().length() > 0 ? Integer.parseInt(arr[30]) : 0);
								// dspthdr.setDsp_delivery_date(dsp_delivery_date);
								// Add new COlumns

								if (arr[31].trim().length() > 0) { // Actual
									//////////////
									if (arr[31].trim().contains("-")) {
										arr[31] = arr[31].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[31].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1) + "   "
												+ arr[31].trim());
									date3 = sdf1.parse(arr[31]);

									// if (date1.compareTo(date3) < 0) {
									dspthdr.setActual_delivery_date(new java.sql.Date(date3.getTime()));
									// } else {
									// throw new MedicoException(
									// " Entered Date is Smaller than Current Date Row No.=" + (j + 1)+"
									// "+arr[31].trim());
									// }
								} else {
									dspthdr.setActual_delivery_date(null);
								}

								dspthdr.setDsp_recd_by(arr[32]);
								dspthdr.setWay_bill_no(arr[33]);
								dspthdr.setCourier_expenses(
										arr[34].trim().length() > 0 ? new BigDecimal(arr[34]) : BigDecimal.ZERO);
								dspthdr.setDsp_remark(arr[35]);

								if (bean.getCompanyCode().trim().equals(VET)) {
									dspthdr.setDsp_lorry_no(arr[39]);
									dspthdr.setDsp_trans_gst_reg_no(arr[40]);
								} else {
									if (gprmInd.equalsIgnoreCase(Y) && company.getStock_at_cfa().equals("Y")) { // abhishek
																												// 16
																												// dec
										// change by yash 15Jul
										dspthdr.setDsp_trans_gst_reg_no(arr[38]);

										// change by nilesh 15Jan
										if (bean.getCompanyCode().trim().equals(PFZ)) {
											dspthdr.setTransport_mode(arr[39]);
										}
									} else {
										// change by yash 15Jul
//										dspthdr.setDsp_trans_gst_reg_no(arr[37]);

										// change by nilesh 15Jan
										if (bean.getCompanyCode().trim().equals(PFZ)) {
											dspthdr.setTransport_mode(arr[38]);
										}
									}
								}
								// change by nilesh 15Jan
								if (bean.getCompanyCode().trim().equals(PFZ)) {
									if (company.getCfa_to_stk_ind().equals(N)) {
										if (gprmInd.equalsIgnoreCase(Y)) {
											dspthdr.setTransport_mode(arr[38]);
										} else {
											dspthdr.setTransport_mode(arr[37]);
										}
									}
								}
								datadispt.add(dspthdr);
							}
						} else {
							Dispatch_Header_Addl dspthdr_adl = new Dispatch_Header_Addl();
							dspthdr_adl.setDsp_challan_no(arr[21]);
							dspthdr_adl.setDsp_sum_dsp_addl_id(Long.parseLong(arr[0]));

							if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
								dspthdr_adl.setDsp_transporter(bean.getUsername());
							} else {
								dspthdr_adl.setDsp_transporter(arr[24]);
							}

							dspthdr_adl.setDsp_lr_no(arr[25]);
							if (arr[26].trim().length() > 0) {
								//////////////
								if (arr[26].trim().contains("-")) {
									arr[26] = arr[26].replace("-", "/");
								}
								/////////////

								if (!Utility.isThisDateValid(arr[22].trim(), DATE_FORMAT))
									throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
								date = sdf1.parse(arr[26]);
								if (date1.compareTo(date) > 0) {
									dspthdr_adl.setDsp_lr_dt(new java.sql.Date(date.getTime()));
									// System.out.println("Date
									// 1--:"+dspthdr_adl.getDsp_lr_dt());
								} else {
									throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
											+ (j + 1) + " " + arr[26]);
								}
							} else {
								dspthdr_adl.setDsp_lr_dt(null);
							}
							dspthdr_adl
									.setDsp_wt(arr[28].trim().length() > 0 ? new BigDecimal(arr[25]) : BigDecimal.ZERO);
							dspthdr_adl.setDsp_billable_wt(
									arr[29].trim().length() > 0 ? new BigDecimal(arr[29]) : BigDecimal.ZERO);
							dspthdr_adl.setDsp_cases(arr[30].trim().length() > 0 ? Integer.parseInt(arr[30]) : 0);

							// Add new COlumns
							if (arr[27].trim().length() > 0) {
								//////////////
								if (arr[27].trim().contains("-")) {
									arr[27] = arr[27].replace("-", "/");
								}
								/////////////
								if (!Utility.isThisDateValid(arr[27].trim(), DATE_FORMAT))
									throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
								date = sdf1.parse(arr[27]);

								if (date1.compareTo(date) > 0) {
									dspthdr_adl.setDsp_delivery_date(new java.sql.Date(date.getTime()));
								} else {
									throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
											+ (j + 1) + " " + arr[27]);
								}
							} else {
								dspthdr_adl.setDsp_delivery_date(null);
							}
							dspthdr_adl.setDsp_recd_by(arr[32]);
							dspthdr_adl.setDsp_remark(arr[35]);
							dspthdr_adl.setWay_bill_no(arr[33]);
							datadispt_adl.add(dspthdr_adl);
						}
					} else {
						///// for prev

						if (bean.getTmpOrActl().trim().equals("A")) {
							transporter = dispatchRepository.getTransporterMaster(arr[24].trim().toUpperCase());
							if (arr[24].trim().length() > 0 && arr[25].trim().length() > 0
									&& arr[26].trim().length() > 0
									|| arr[24].trim().length() > 0 && transporter != null) { /// If Transporter , LR
																								/// dt,Lr no not blank
								Dispatch_Header_arc dspthdr = new Dispatch_Header_arc();
								dspthdr.setDspChallanNo(arr[21]);
								dspthdr.setDsp_sum_dsp_id(Long.parseLong(arr[0]));

								// Transporter
								if (arr[24].trim().length() > 0 && arr[25].trim().length() > 0
										&& arr[26].trim().length() > 0) {
									if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
										dspthdr.setDsp_transporter(bean.getUsername().toUpperCase());
									} else {
										dspthdr.setDsp_transporter(arr[24].toUpperCase());
									}
								} else {
									dspthdr.setDsp_transporter(arr[24]);
								}
								dspthdr.setDsp_lr_no(arr[25]);
								if (arr[26].trim().length() > 0) {
									//////////////
									if (arr[26].trim().contains("-")) {
										arr[26] = arr[26].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[26].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
									date = sdf1.parse(arr[26]);
									if (date1.compareTo(date) > 0) {
										dspthdr.setDsp_lr_dt(new java.sql.Date(date.getTime()));
									} else {
										throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
												+ (j + 1) + " " + arr[26]);
									}
								} else {
									dspthdr.setDsp_lr_dt(null);
								}

								if (arr[27].trim().length() > 0) { // Tentative
									//////////////
									if (arr[27].trim().contains("-")) {
										arr[27] = arr[27].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[27].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
									date = sdf1.parse(arr[27]);
									dtl_lr_date3 = sdf1.parse(arr[26]);
									if (dtl_lr_date3.compareTo(date) <= 0) {
										dspthdr.setDsp_delivery_date(new java.sql.Date(date.getTime()));
									} else {
										throw new MedicoException(
												"Tentative Date is Smaller than LR Date Row No.=" + (j + 1));
									}
								} else {
									dspthdr.setDsp_delivery_date(null);
								}

								dspthdr.setDsp_wt(
										arr[28].trim().length() > 0 ? new BigDecimal(arr[28]) : BigDecimal.ZERO);
								dspthdr.setDsp_billable_wt(
										arr[29].trim().length() > 0 ? new BigDecimal(arr[29]) : BigDecimal.ZERO);
								dspthdr.setDsp_cases(arr[30].trim().length() > 0 ? Integer.parseInt(arr[30]) : 0);
								// dspthdr.setDsp_delivery_date(dsp_delivery_date);
								// Add new COlumns

								if (arr[31].trim().length() > 0) { // Actual
									//////////////
									if (arr[31].trim().contains("-")) {
										arr[31] = arr[31].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[31].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1) + "   "
												+ arr[31].trim());
									date3 = sdf1.parse(arr[31]);

									// if (date1.compareTo(date3) < 0) {
									dspthdr.setActual_delivery_date(new java.sql.Date(date3.getTime()));
									// } else {
									// throw new MedicoException(
									// " Entered Date is Smaller than Current Date Row No.=" + (j + 1)+"
									// "+arr[31].trim());
									// }
								} else {
									dspthdr.setActual_delivery_date(null);
								}

								dspthdr.setDsp_recd_by(arr[32]);
								dspthdr.setWay_bill_no(arr[33]);
								dspthdr.setCourier_expenses(
										arr[34].trim().length() > 0 ? new BigDecimal(arr[34]) : BigDecimal.ZERO);
								dspthdr.setDsp_remark(arr[35]);

								if (bean.getCompanyCode().trim().equals(VET)) {
									dspthdr.setDsp_lorry_no(arr[39]);
									dspthdr.setDsp_trans_gst_reg_no(arr[40]);
								} else {
									if (gprmInd.equalsIgnoreCase(Y)) {
										// change by yash 15Jul
										dspthdr.setDsp_trans_gst_reg_no(arr[38]);

										// change by nilesh 14Jan
										if (bean.getCompanyCode().trim().equals(PFZ)) {
											dspthdr.setTransport_mode(arr[39]);
										}
									} else {
										// change by yash 15Jul
//										dspthdr.setDsp_trans_gst_reg_no(arr[37]);

										// change by nilesh 14Jan
										if (bean.getCompanyCode().trim().equals(PFZ)) {
											dspthdr.setTransport_mode(arr[38]);
										}
									}
								}
								// change by nilesh 15Jan
								if (bean.getCompanyCode().trim().equals(PFZ)) {
									if (company.getCfa_to_stk_ind().equals(N)) {
										if (gprmInd.equalsIgnoreCase(Y)) {
											dspthdr.setTransport_mode(arr[38]);
										} else {
											dspthdr.setTransport_mode(arr[37]);
										}
									}
								}
								datadispt_arc.add(dspthdr);
							}
						} else {
							Dispatch_Header_Addl dspthdr_adl = new Dispatch_Header_Addl();
							dspthdr_adl.setDsp_challan_no(arr[21]);
							dspthdr_adl.setDsp_sum_dsp_addl_id(Long.parseLong(arr[0]));

							if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
								dspthdr_adl.setDsp_transporter(bean.getUsername());
							} else {
								dspthdr_adl.setDsp_transporter(arr[24]);
							}

							dspthdr_adl.setDsp_lr_no(arr[25]);
							if (arr[26].trim().length() > 0) {
								//////////////
								if (arr[26].trim().contains("-")) {
									arr[26] = arr[26].replace("-", "/");
								}
								/////////////
								if (!Utility.isThisDateValid(arr[22].trim(), DATE_FORMAT))
									throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
								date = sdf1.parse(arr[26]);
								if (date1.compareTo(date) > 0) {
									dspthdr_adl.setDsp_lr_dt(new java.sql.Date(date.getTime()));
									// System.out.println("Date
									// 1--:"+dspthdr_adl.getDsp_lr_dt());
								} else {
									throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
											+ (j + 1) + " " + arr[26]);
								}
							} else {
								dspthdr_adl.setDsp_lr_dt(null);
							}
							dspthdr_adl
									.setDsp_wt(arr[28].trim().length() > 0 ? new BigDecimal(arr[25]) : BigDecimal.ZERO);
							dspthdr_adl.setDsp_billable_wt(
									arr[29].trim().length() > 0 ? new BigDecimal(arr[29]) : BigDecimal.ZERO);
							dspthdr_adl.setDsp_cases(arr[30].trim().length() > 0 ? Integer.parseInt(arr[30]) : 0);

							// Add new COlumns
							if (arr[27].trim().length() > 0) {
								//////////////
								if (arr[27].trim().contains("-")) {
									arr[27] = arr[27].replace("-", "/");
								}
								/////////////
								if (!Utility.isThisDateValid(arr[27].trim(), DATE_FORMAT))
									throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1));
								date = sdf1.parse(arr[27]);

								if (date1.compareTo(date) > 0) {
									dspthdr_adl.setDsp_delivery_date(new java.sql.Date(date.getTime()));
								} else {
									throw new MedicoException(" Entered Date is Greater than Current Date Row No.="
											+ (j + 1) + " " + arr[27]);
								}
							} else {
								dspthdr_adl.setDsp_delivery_date(null);
							}
							dspthdr_adl.setDsp_recd_by(arr[32]);
							dspthdr_adl.setDsp_remark(arr[35]);
							dspthdr_adl.setWay_bill_no(arr[33]);
							datadispt_adl.add(dspthdr_adl);
						}

					}

					lrcsvDownload.setSum_hd_id(arr[0]);
					lrcsvDownload.setTeam_name(arr[1]);
					lrcsvDownload.setTeam_code(arr[2]);
					lrcsvDownload.setMst_stn_no(arr[3]);
					System.out.println("arr[4]:" + arr[4]);
					//////////////
					if (arr[4].trim().contains("-")) {
						arr[4] = arr[4].replace("-", "/");
					}
					/////////////
					lrcsvDownload.setMst_stn_dt(
							(arr[4].trim().length() > 0) ? new java.sql.Date(sdf1.parse(arr[4]).getTime()) : null);
					lrcsvDownload.setMst_destination(arr[5]);
					lrcsvDownload.setStn_value(arr[6]);
					lrcsvDownload.setMst_lr_no(lrNoString);
					//////////////
					if (arr[8].trim().contains("-")) {
						arr[8] = arr[8].replace("-", "/");
					}
					/////////////
					lrcsvDownload.setMst_lr_dt(
							arr[8].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[8]).getTime()) : null);

					if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
						lrcsvDownload.setMst_transporter(bean.getUsername());
					} else {
						lrcsvDownload.setMst_transporter(arr[9]);
					}

					lrcsvDownload.setMst_grs_wght(arr[10]);
					lrcsvDownload.setMst_billwght(arr[11]);
					lrcsvDownload.setMst_tot_case(arr[12]);
					lrcsvDownload.setDspfstaff_employeeno(arr[19]);
					lrcsvDownload.setDspfstaff_displayname(arr[20]);
					lrcsvDownload.setDtl_chln_no(arr[21]);
					//////////////
					if (arr[22].trim().contains("-")) {
						arr[22] = arr[22].replace("-", "/");
					}
					/////////////
					lrcsvDownload.setDtl_chln_dt(
							arr[22].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[22]).getTime()) : null);
					lrcsvDownload.setDtl_city(arr[23]);

					if (bean.getRole().trim().equals(MedicoConstants.ROLE_COUR)) {
						lrcsvDownload.setDtl_transporter(bean.getUsername());
					} else {
						lrcsvDownload.setDtl_transporter(arr[24]);
					}

					lrcsvDownload.setDtl_lr_no(arr[25]);
					//////////////
					if (arr[26].trim().contains("-")) {
						arr[26] = arr[26].replace("-", "/");
					}
					/////////////
					lrcsvDownload.setDtl_lr_dt(
							arr[26].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[26]).getTime()) : null);
					lrcsvDownload.setDtl_stn_grswght(arr[28]);
					lrcsvDownload.setDtl_stn_billwght(arr[29]);
					lrcsvDownload.setDtl_tot_case(arr[30]);
					lrcsvDownload.setSr_no(++i);
					//////////////
					if (arr[13].trim().contains("-")) {
						arr[13] = arr[13].replace("-", "/");
					}
					/////////////
					lrcsvDownload.setSumdsp_delivery_date(
							arr[13].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[13]).getTime()) : null);
					lrcsvDownload.setSumdsp_recd_by(arr[14]);
					lrcsvDownload.setSumdsp_remark(arr[18]);
					//////////////
					if (arr[27].trim().contains("-")) {
						arr[27] = arr[27].replace("-", "/");
					}
					/////////////
					lrcsvDownload.setDsp_delivery_date(
							arr[27].trim().length() > 0 ? new java.sql.Date(sdf1.parse(arr[27]).getTime()) : null);
					lrcsvDownload.setDsp_recd_by(arr[32]);
					lrcsvDownload.setWay_bill_no(arr[33]);
					lrcsvDownload.setDsp_remark(arr[35]);
					lrcsvDwnList.add(lrcsvDownload);
				} else {
					bean.setMsg("Please check file properly column data is exceeds than no. of records.");

				}

			}
			System.out.println("datasum :: " + datasum.size());
			System.out.println("datadispt :: " + datadispt.size());

			if (fin_year_flag.equals(MedicoConstants.CURRENT)) {
				if (bean.getTmpOrActl().trim().equals("A")) {
					this.dispatchRepository.uploadlrCsvData(datasum, datadispt, fin_year, bean);
				} else {
					this.dispatchRepository.uploadlrCsvDataInAddl(datasum_adl, datadispt_adl);
				}
				this.dispatchRepository.truncateLrCSVUpload();
				this.dispatchRepository.saveLrCSVUpload(lrcsvDwnList);
				bean.setMsg("File Uploaded Successfully");
			} else {
				// for Prev
				if (bean.getTmpOrActl().trim().equals("A")) {
					this.dispatchRepository.uploadlrCsvData_arc(datasum_arc, datadispt_arc, fin_year, bean);
				} else {
					this.dispatchRepository.uploadlrCsvDataInAddl(datasum_adl, datadispt_adl);
				}
				this.dispatchRepository.truncateLrCSVUpload();
				this.dispatchRepository.saveLrCSVUpload(lrcsvDwnList);
				bean.setMsg("File Uploaded Successfully");
			}
//	    if (stock_at_cfa.equalsIgnoreCase("Y")) {
//			challansForStockAtCfaList = new ArrayList<>();
//			challansForStockAtCfaList = getChallansForStockAtCfa();
//		    }

			if (bean.getCompanyCode().trim().equals(VET)) {
				// List<Object> list =
				// dispatchRepository.getdispatch_details_EmailToMgr(bean.getEmpId(),
				// bean.getDivision(), bean.getLocId());

//			this.getdispatch_details_EmailToMgr(bean.getEmpId(), 0l,
//					0l, bean.getCompanyName());
			}
		} catch (MedicoException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(j + "");
		}
		return j;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public long dpLrUploadModifyDetailRevised(DispatchBean bean) throws Exception {
		BufferedReader br = null;
		String gprmInd = parameterRepository.getGprmIndicator();
		long j = 0l;
		try {

			Company company = companyMasterService.getCompanyDetails();

			if (bean.getStockAtCfa() == null) {
				bean.setStockAtCfa("N");
			}
			if (bean.getStockAtCfa().equalsIgnoreCase("Y")) {
				bean.setStockAtCfa("stock_at_cfa");
			} else {
				bean.setStockAtCfa("Upload");
			}
			if (bean.getTmpOrActl() == null) {
				bean.setTmpOrActl("A");
			}

			File convFile = new File(
					System.getProperty("java.io.tmpdir") + "/" + bean.getDpLrUploadFile().getOriginalFilename());
			bean.getDpLrUploadFile().transferTo(convFile);
			br = new BufferedReader(new FileReader(convFile));
			String[] header = br.readLine().split(",");
			String record = "";
			Set<String> setlr = new TreeSet<String>();
			String oldmst_stn_no = "", newmst_stn_no = "";
			List<Sum_Disp_Header> datasum = new ArrayList<Sum_Disp_Header>();
			List<Dispatch_Header> datadispt = new ArrayList<Dispatch_Header>();
			List<Sum_Disp_Header_Addl> datasum_adl = new ArrayList<Sum_Disp_Header_Addl>();
			List<Dispatch_Header_Addl> datadispt_adl = new ArrayList<Dispatch_Header_Addl>();

			List<Sum_Disp_Header_arc> datasum_arc = new ArrayList<Sum_Disp_Header_arc>();
			List<Dispatch_Header_arc> datadispt_arc = new ArrayList<Dispatch_Header_arc>();
			// br.readLine();
			List<LrCsvUpload> lrcsvDwnList = new ArrayList<LrCsvUpload>();
			String lrNoString = "";
			long i = 0l;
			final String DATE_FORMAT = "dd/MM/yyyy";
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

			String finyr_flag = null;
			String finyr = null;

			while ((record = br.readLine()) != null) {

				j++;

				LrCsvUpload lrcsvDownload = new LrCsvUpload();
				System.out.println("record:" + record);
				String[] arr = record.split(",");
				System.out.println("arr:" + arr.length);
				
				

//				
//				if (bean.getCompanyCode().trim().equals(VET)) {
//					if (arr.length != 41) {
//						throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
//					}
//				} else {
//					
//					
//					if (gprmInd.equalsIgnoreCase(Y)) {
//						if (arr.length != 38) {
//							throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
//						}
//					} else {
//						if (arr.length != 37) {
//							throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
//						}
//
//					}	
//
//				}

				if (bean.getCompanyCode().trim().equals(VET)) {
					if (arr.length != 41) {
						throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
					}
				} else if (bean.getCompanyCode().trim().equals(PFZ)) {
					if (company.getCfa_to_stk_ind().equals("Y")) { // for pfz if its stkcfa
						if (gprmInd.equalsIgnoreCase("Y")) {
							if (arr.length != 40) {
								throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
							}
						} else {
							if (arr.length != 39) {
								throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
							}
						}
					} else {
						if (gprmInd.equalsIgnoreCase("Y")) { // for pfz if its sg
							if (arr.length != 39) {
								throw new MedicoException("Please fill Transport Mode of Row No.=" + (j + 1));
							}
						} else {
							if (arr.length != 38) {
								throw new MedicoException("Please fill Transport Mode of Row No.=" + (j + 1));
							}
						}
					}
				} else {
					if (company.getCfa_to_stk_ind().equals("Y")) { // other company if its stkcfa
						if (gprmInd.equalsIgnoreCase("Y")) {
							if (arr.length != 39) {
								throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
							}
						} else {
							if (arr.length != 38) {
								throw new MedicoException("Please fill Transporter GST of Row No.=" + (j + 1));
							}
						}
					} else { // other company if its sg
						if (gprmInd.equalsIgnoreCase("Y")) {
							if (arr.length != 38) {
								throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
							}
						} else {
							if (arr.length != 37) {
								throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
							}
						}
					}
				}

				if (!bean.getCompanyCode().trim().equals(VET) && arr[36] == null) {
					throw new MedicoException("Please fill Detail Remark of Row No.=" + (j + 1));
				}

				if (arr[0].trim().length() > 0) {
					newmst_stn_no = arr[0];
					java.util.Date date = null;
					java.util.Date date1 = new Date();
					java.util.Date date2 = null;
					java.util.Date date3 = null;

					finyr = arr[36];

					if (bean.getFinyr().equals(arr[36])) {
						finyr_flag = MedicoConstants.CURRENT;
					} else {
						finyr_flag = MedicoConstants.PREVIOUS;
					}

					
					
					if (!newmst_stn_no.equalsIgnoreCase(oldmst_stn_no)) {
						if (finyr_flag.equals(MedicoConstants.CURRENT)) {
							if (bean.getTmpOrActl().trim().equals("A")) {

								if (arr[15].trim().length() > 0 && arr[14].trim().length() > 0) {
									Sum_Disp_Header sumobj = new Sum_Disp_Header();
									sumobj.setSumdsp_id(
											(arr[0] != null && arr[0].trim().length() > 0) ? Long.parseLong(arr[0])
													: 0);
									sumobj.setSumdsp_challan_no(arr[3]);

									if (arr[15].trim().length() > 0) { // Actual Date
										//////////////
										if (arr[15].trim().contains("-")) {
											arr[15] = arr[15].replace("-", "/");
										}
										/////////////
										if (!Utility.isThisDateValid(arr[15].trim(), DATE_FORMAT))
											throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1)
													+ "   " + arr[15].trim());
										date2 = sdf1.parse(arr[15]);

										sumobj.setActual_delivery_date(new java.sql.Date(date2.getTime()));

									} else {
										sumobj.setActual_delivery_date(null);
									}

									sumobj.setCourier_expenses(
											arr[17].trim().length() > 0 ? new BigDecimal(arr[17]) : BigDecimal.ZERO);
									sumobj.setSumdsp_remark(arr[18]);
									sumobj.setSumdsp_mod_ip_add(bean.getIpAddress());
									sumobj.setSumdsp_recd_by(arr[14]);

//										// changes 18-02-2025
									if (bean.getCompanyCode().trim().equals(PFZ)) {
										if (company.getCfa_to_stk_ind().equals(N)) {
											if (gprmInd.equalsIgnoreCase(Y)) {
												sumobj.setTransport_mode(arr[38]);
											} else {
												sumobj.setTransport_mode(arr[37]);
											}
										} else {
											if (gprmInd.equalsIgnoreCase(Y)) {
												sumobj.setTransport_mode(arr[39]);
											} else {
												sumobj.setTransport_mode(arr[38]);
											}

										}
									}

									datasum.add(sumobj);
								}
								oldmst_stn_no = newmst_stn_no;
							}
						} else {
							/// for Prev

							if (bean.getTmpOrActl().trim().equals("A")) {
								if (arr[15].trim().length() > 0 && arr[14].trim().length() > 0) {
									Sum_Disp_Header_arc sumobj = new Sum_Disp_Header_arc();
									sumobj.setSumdsp_id(
											(arr[0] != null && arr[0].trim().length() > 0) ? Long.parseLong(arr[0])
													: 0);
									sumobj.setSumdsp_challan_no(arr[3]);

									if (arr[15].trim().length() > 0) { // Actual Date
										//////////////
										if (arr[15].trim().contains("-")) {
											arr[15] = arr[15].replace("-", "/");
										}
										/////////////
										if (!Utility.isThisDateValid(arr[15].trim(), DATE_FORMAT))
											throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1)
													+ "   " + arr[15].trim());
										date2 = sdf1.parse(arr[15]);

										sumobj.setActual_delivery_date(new java.sql.Date(date2.getTime()));

									} else {
										sumobj.setActual_delivery_date(null);
									}
									sumobj.setCourier_expenses(
											arr[17].trim().length() > 0 ? new BigDecimal(arr[17]) : BigDecimal.ZERO);
									sumobj.setSumdsp_remark(arr[18]);
									sumobj.setSumdsp_mod_ip_add(bean.getIpAddress());
									sumobj.setSumdsp_recd_by(arr[14]);

									// changes 18-02-2025
									if (bean.getCompanyCode().trim().equals(PFZ)) {
										if (company.getCfa_to_stk_ind().equals(N)) {
											if (gprmInd.equalsIgnoreCase(Y)) {
												sumobj.setTransport_mode(arr[38]);
											} else {
												sumobj.setTransport_mode(arr[37]);
											}
										} else {
											if (gprmInd.equalsIgnoreCase(Y)) {
												sumobj.setTransport_mode(arr[39]);
											} else {
												sumobj.setTransport_mode(arr[38]);
											}

										}
									}

									datasum_arc.add(sumobj);

								}
								oldmst_stn_no = newmst_stn_no;
							}

						}
					}

					if (finyr_flag.equals(MedicoConstants.CURRENT)) {
						if (bean.getTmpOrActl().trim().equals("A")) {
							if (arr[31].trim().length() > 0 && arr[32].trim().length() > 0) {
								Dispatch_Header dspthdr = new Dispatch_Header();
								dspthdr.setDspChallanNo(arr[21]);
								dspthdr.setDsp_sum_dsp_id(Long.parseLong(arr[0]));

								if (arr[31].trim().length() > 0) { // Actual
									//////////////
									if (arr[31].trim().contains("-")) {
										arr[31] = arr[31].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[31].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1) + "   "
												+ arr[31].trim());
									date3 = sdf1.parse(arr[31]);

									dspthdr.setActual_delivery_date(new java.sql.Date(date3.getTime()));

								} else {
									dspthdr.setActual_delivery_date(null);
								}

								dspthdr.setDsp_recd_by(arr[32]);

								dspthdr.setWay_bill_no(arr[33]);

								dspthdr.setCourier_expenses(
										arr[34].trim().length() > 0 ? new BigDecimal(arr[34]) : BigDecimal.ZERO);
								dspthdr.setDsp_remark(arr[35]);
								dspthdr.setDsp_mod_ip_add(bean.getIpAddress());

								/// changes 18-02-25
								if (gprmInd.equalsIgnoreCase(Y) && company.getStock_at_cfa().equals("Y")) {
									dspthdr.setDsp_trans_gst_reg_no(arr[38]);
									if (bean.getCompanyCode().trim().equals(PFZ)) {
										dspthdr.setTransport_mode(arr[39]);
									}
								} else {
							
//									dspthdr.setDsp_trans_gst_reg_no(arr[37]);
									
									if (bean.getCompanyCode().trim().equals(PFZ)) {
										dspthdr.setTransport_mode(arr[38]);
									}
								}

								if (bean.getCompanyCode().trim().equals(PFZ)) {
									if (company.getCfa_to_stk_ind().equals(N)) {
										if (gprmInd.equalsIgnoreCase(Y)) {
											dspthdr.setTransport_mode(arr[38]);
										} else {
											dspthdr.setTransport_mode(arr[37]);
										}
									}
								}

								datadispt.add(dspthdr);
							}
						}
					} else {
						//// for prev

						if (bean.getTmpOrActl().trim().equals("A")) {
							if (arr[31].trim().length() > 0 && arr[32].trim().length() > 0) {
								Dispatch_Header_arc dspthdr = new Dispatch_Header_arc();
								dspthdr.setDspChallanNo(arr[21]);
								dspthdr.setDsp_sum_dsp_id(Long.parseLong(arr[0]));

								if (arr[31].trim().length() > 0) { // Actual
									//////////////
									if (arr[31].trim().contains("-")) {
										arr[31] = arr[31].replace("-", "/");
									}
									/////////////
									if (!Utility.isThisDateValid(arr[31].trim(), DATE_FORMAT))
										throw new MedicoException(" Entered Date is Wrong of Row No.=" + (j + 1) + "   "
												+ arr[31].trim());
									date3 = sdf1.parse(arr[31]);

									dspthdr.setActual_delivery_date(new java.sql.Date(date3.getTime()));

								} else {
									dspthdr.setActual_delivery_date(null);
								}

								dspthdr.setDsp_recd_by(arr[32]);

								dspthdr.setWay_bill_no(arr[33]);

								dspthdr.setCourier_expenses(
										arr[34].trim().length() > 0 ? new BigDecimal(arr[34]) : BigDecimal.ZERO);
								dspthdr.setDsp_remark(arr[35]);
								dspthdr.setDsp_mod_ip_add(bean.getIpAddress());

								if (bean.getCompanyCode().trim().equals(VET)) {
									dspthdr.setDsp_lorry_no(arr[39]);
									dspthdr.setDsp_trans_gst_reg_no(arr[40]);
								} else {
									if (gprmInd.equalsIgnoreCase(Y)) {
										// change by yash 15Jul
//										dspthdr.setDsp_trans_gst_reg_no(arr[38]);

										// change by nilesh 14Jan
										if (bean.getCompanyCode().trim().equals(PFZ)) {
											dspthdr.setTransport_mode(arr[38]);
										}
									} else {
										// change by yash 15Jul
//										dspthdr.setDsp_trans_gst_reg_no(arr[37]);

										// change by nilesh 14Jan
										if (bean.getCompanyCode().trim().equals(PFZ)) {
											dspthdr.setTransport_mode(arr[37]);
										}
									}
								}
								// change by nilesh 15Jan
								if (bean.getCompanyCode().trim().equals(PFZ)) {
									if (company.getCfa_to_stk_ind().equals(N)) {
										if (gprmInd.equalsIgnoreCase(Y)) {
											dspthdr.setTransport_mode(arr[38]);
										} else {
											dspthdr.setTransport_mode(arr[37]);
										}
									}
								}

								datadispt_arc.add(dspthdr);
							}
						}

					}

				}
			}

			System.out.println("datasum :: " + datasum.size());
			System.out.println("datadispt :: " + datadispt.size());

			if (finyr_flag.equals(MedicoConstants.CURRENT)) {
				if (bean.getTmpOrActl().trim().equals("A")) {
					this.dispatchRepository.uploadlrCsvModifydetailData(datasum, datadispt, bean);
				}
				bean.setMsg("File Uploaded Successfully");
			} else {
				if (bean.getTmpOrActl().trim().equals("A")) {
					this.dispatchRepository.uploadlrCsvModifydetailData_arc(datasum_arc, datadispt_arc, finyr, bean);
				}
				bean.setMsg("File Uploaded Successfully");
			}
		} catch (MedicoException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw new Exception(+j + " :" + e.getMessage());
		}
		return j;
	}

	@Override
	public void sendForDispatchApproval(String empId, List<String> challanNumberList, HttpServletRequest request)
			throws Exception {
		String AUTO_APPR_IND = this.parameterRepository.dispatchAutoApproval();
		for (int i = 0; i < challanNumberList.size(); i++) {
			String challanNumber = challanNumberList.get(i);
			Dispatch_Header header = null;
			System.out.println(challanNumber);
			try {
				header = dispatchRepository.getDispatchHeaderByChallanNo(challanNumber);
				if (header.getDsp_appr_status().equals("E")) {

					System.out.println("header.getDsp_id() " + header.getDsp_id());
					String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
					Company companyMaster = (Company) request.getServletContext().getAttribute(COMPANY_DATA);
					String tranType = null;
					String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
					Location location = locationService.getLocationNameByEmployeeId(empId);

					if (companyMaster.getCfa_to_stk_ind().trim().equalsIgnoreCase("Y")) {
						// new alternate logic
						tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(),
								DISPATCH_FULL_TRAN_NAME, companyCode);
					} else {
						if (MedicoConstants.PFZ_LOC.contains(header.getDsp_loc_id())) {
							tranType = MedicoConstants.DSP_APPR_PFZ;
						} else {
							tranType = MedicoConstants.DSP_APPR_PIPL;
						}
					}

					System.out.println("tranType:::" + tranType);
					if (tranType == null || tranType.isEmpty()) {
						throw new Exception("No approval data found for this location.");
					}
					// if (!companyMaster.getWms_is_live().trim().equals("Y")) {
					System.out.println("PROD_TYPE_ID :: " + header.getSmp_prod_type_id());
					List<TaskMaster> masters = taskMasterService.getTask(null, null, null, tranType, null, null,
							TASK_FLOW, Long.parseLong(header.getSmp_prod_type_id()), null);

					if (masters.size() == 0) {
						throw new MedicoException("Task master record not found");
					}
					TaskMaster taskMaster = masters.get(0);
					List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
					if (task_Master_Dtls.size() == 0) {
						throw new MedicoException("Task master detail record not found");
					}

					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("initiator", empId);
					variables.put("initiator_desc", empId);
					variables.put("initiator_email", "demo");
					variables.put("tran_ref_id", header.getDsp_id());
					variables.put("tran_type", tranType);
					variables.put("inv_group", null);
					variables.put("loc_id", header.getDsp_loc_id());
					variables.put("cust_id", 0L);
					variables.put("tran_no", header.getDspChallanNo());
					variables.put("company_code", companyCode);
					variables.put("totaltask", masters.size());
					variables.put("amount", BigDecimal.ZERO);
					variables.put("escalatetime", null);
					variables.put("fin_year_id", header.getDsp_fin_year());
					variables.put("stock_point_id", header.getDsp_state_id());
					variables.put("doc_type", "NS");
					variables.put("task_flow", TASK_FLOW);
					variables.put("fs_id", Long.parseLong(header.getSmp_prod_type_id()));
					ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("dispatchApproval",
							variables);
					// Changing Status
					String ip_addr = request.getRemoteAddr();
					dispatchRepository.updateDispatchHeaderForSelfApproval(challanNumber, new Date(), ip_addr, empId);

					// send for auto approval based on indicator
					// based on indicator do final approval here
					if (AUTO_APPR_IND != null && AUTO_APPR_IND.equalsIgnoreCase("Y")) {
						ApprovalBean bean = new ApprovalBean();
						// get act_taskid by tran_id and tran_type
						bean.setTran_id(header.getDsp_id());
						bean.setApprover(task_Master_Dtls.get(0).getIdentitylinktype_val());
						bean.setApproved("A");
						bean.setTran_type(Long.valueOf(tranType));
						bean.setTaskid(
								this.taskMstServi.getActTaskIdByTranTypeAndId(header.getDsp_id(), tranType).toString());
						bean.setInv_grp(null);
						this.apprController.simpleProcessTestData(request, null, bean);
					}
					// }

				}
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
		}
	}

	@Override
	public ArrayList<Object> getdispatch_details_EmailToMgr(String empId, Long divId, Long locId, String CompanyName)
			throws Exception {
		ArrayList<Object> list = dispatchRepository.getdispatch_details_EmailToMgr(empId, divId, locId);
		EmailToMgr em = new EmailToMgr();
		String msg = em.EmailToMgr(empId, divId, locId, CompanyName, list);
		return list;
	}

	@Override
	public List<Long> getDispatchCyclesForBulkHCP(Long bulk_hcp_req_id, Long dspCycle, Long alloc_smp_id)
			throws Exception {
		return dispatchRepository.getDispatchCyclesForBulkHCP(bulk_hcp_req_id, dspCycle, alloc_smp_id);
	}

	@Override
	public List<Dispatch_Header> getListOfSumDispForEInvoice(Long locid, String date) throws Exception {
		return dispatchRepository.getListOfSumDispForEInvoice(locid, date);
	}
}
