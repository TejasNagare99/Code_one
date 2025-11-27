package com.excel.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

import com.excel.bean.Alloc_Gen_Param;
import com.excel.bean.AllocationBean;
import com.excel.bean.AllocationUploadBean;
import com.excel.model.ActivityNotification;
import com.excel.model.AllocConHd;
import com.excel.model.Alloc_Temp_Header;
import com.excel.model.Alloc_gen_ent;
import com.excel.model.Alloc_gen_hd;
import com.excel.model.AnnualAllocationPreviousYearData;
import com.excel.model.AnnualPlanPreviousYearCount;
import com.excel.model.AspDetail;
import com.excel.model.AspHeader;
import com.excel.model.AspHeaderArc;
import com.excel.model.FieldStaff;
import com.excel.model.GrnHeader;
import com.excel.model.Period;
import com.excel.model.UserMgrAsst;

public interface AllocationService {

	List<Object> getTeamWithCount(String empId) throws Exception;
	List<Object> getSampleProductList(String divId, List<String> prodGroup, String prodType) throws Exception;
	List<Object> getPlanningType(String empId) throws Exception;
	List<AllocationBean> getProductDetails(Long prod_id, String year);
	List<Object> getBrandsForTeam(String empId, String teamId, String planType,String subTeamId) throws Exception;
	AspHeader getAspHeaderByManagerAndUserId(Long userId,Long teamId,String fin_year,String sub_team_code) throws Exception;
	List<Object> getPreviousYearPeriodWiseDetails(String prodId, String divId, String prodGroup, String prodType, String year) throws Exception;
	Map<String, Object> saveAnnualAllocation(AllocationBean bean) throws Exception;
	AspHeader saveAspHeader(AllocationBean bean) throws Exception;
	AspDetail saveAspDetail(AllocationBean bean, AspDetail detail) throws Exception;
	List<AnnualPlanPreviousYearCount> getPreviousYearCount(Long prod_id, Long divId,String year) throws Exception;
	AspHeaderArc getPreviousYearSamplePlan(Long userId, Long mgrId, String fin_year) throws Exception;
	List<Object> getPlanningTypeMonthly() throws Exception;
	Period getAllocationMonthofUse();
	List<Period> getPeriodListGreaterThanPeriodCode(String periodCode);
	List<Object> getProductListForMonthlyAllocation(String prod_sub_type_id, List<String> smp_sa_prod_group,String year) throws Exception;
	Map<String, Object> getStaffCount(String div_id,String sub_team_code);
	Long getAllocationByCount(String divId, String staff) throws Exception;
	Map<String,Object>  save(AllocationBean bean) throws Exception;
	void setAllocGenTrg(Alloc_gen_hd gen_hd, Alloc_gen_ent alloc_gen_ent, String tmqty, String cmqty, String prodtype) throws Exception;
	Map<String, Object> byUserStaffNames(String divId, String allocationMonthPeriodCode, String saveMode,
			String planType, String allocationId, String allocationCycle, String allocatioinbProductType, String year,
			String allocationMode, String fieldType, String coreTeamId,String sub_team_code);
	List<AllocationBean> getManagerListForAssistant(String asst_user_id) throws Exception;
	public List<AllocationBean> getAnnualPlanEnteredProductDetail(Long divId,Long brandId,String year,String userId);
	Map<String, Object> getAllocationEnt(Long allocId) throws Exception;
	void setAllocEntFstaff(Alloc_gen_hd gen_hd, List<String> prodList, List<String> msrqty, List<String> abmqty,
			List<String> rbmqty, List<String> tmprd, List<String> cmprd, Alloc_Gen_Param param, String fieldtype,
			String prodtype, String tmqty, String cmqty, String alloc_id, String period_code, String alloc_cycle,
			String fin_year, String company, String savemode, List<String> brandIds,String sub_team_code,String alloc_type) throws Exception;

	void setAllocDtFstaff(Alloc_gen_hd gen_hd, Alloc_gen_ent ent, List<FieldStaff> fStaffs, String prodList,
			String alloqtyList, String levString, Alloc_Gen_Param param, String fieldtype, String prodtype,
			String tmqty, String cmqty, String prod, String alloqty) throws Exception;
	List<Object> getEnteredAllocationById(String allocationId, String allocationMode,String alloc_smp_sa_prod_group_id);

	void setAllocDetailFstaff(Alloc_gen_hd gen_hd, Alloc_Gen_Param param, String fieldtype, String prodtype,
			String tmqty, String cmqty, String alloc_id, String period_code, String alloc_cycle, String fin_year,
			String company, String savemode, List<String> brandIds, List<Alloc_gen_ent> agdList,String sub_team_code,String alloc_type) throws Exception;
	void sendForApproval(Long allocationId, String user, String email, String remark) throws Exception;
	void finalMonthlyApproval(Long allocationId, String last_approved_by, String comp_cd, String isapproved,String motivation) throws Exception;
	List<Object> getSelfApprovalDataMonthly(String user_id,String ind);
	public List<Alloc_gen_hd> getAllocGenratedDetails(String user_id) throws Exception;
	public List<AllocationBean> getAllocEnteredBrands(String alloc_gen_id) throws Exception;
	Alloc_gen_hd getObjectById(Long alloc_gen_id) throws Exception;
	List<AllocationBean> getAllocEnteredProducts(String prod_sub_type_id,String prodType) throws Exception;
	void  updateStaffDetailsMonthly(AllocationBean bean) throws Exception;
	public List<ActivityNotification> getNotification(String startedBy) throws Exception;
	List<AllocationBean> getAllocationDetail(Long allocationId, String year);
	void discardMonthlyAllocatonByBrand(Long allocationId, Long brandId, String finYear) throws Exception;
	void discardMonthlyAllocaton(Long allocationId, String year) throws Exception;
	void finalAnnualApproval(Long allocationId, String last_approved_by, String comp_cd, String isapproved,String motivation) throws Exception;
	AspDetail getObjectAspDetail(Long headerId) throws Exception;
	void sendAnnualAllocationForApproval(Long aspId, String user, String email, String remark,HttpServletRequest request, HttpSession session) throws Exception;
	List<AllocationBean> getSummaryDetailsAnnualPlan(Long aspId, String year, String status) throws Exception;
	void discardAnnualAllocatio(Long aspId) throws Exception;
	Long getAllocationCycleNumber(String div_id, String period_code, String fin_year, String mgrEmpId) throws Exception;
	void deleteProductFromAnnualPlan(Long alloc_detail_id) throws Exception;
	boolean checkProductPresent(Long prodId, Long divId, String fin_year);
	List<AnnualAllocationPreviousYearData> getPreviousYearDataAnnualPlan(String divId, String prodId, String year)
			throws Exception;
	List<Object> getSampleProductListForAnnualPlan(String divId, List<String> prodGroup, String prodType,
			String allocNumber) throws Exception;
	List<AllocationBean> getEnteredStaffDetails(List<String> productId, String allocationId);
	boolean checkEnetedBrandByProductType(String prod_sub_type_id, List<String> smp_sa_prod_group, String year,
			Long alloc_gen_id) throws Exception;
	void changeStatusForAssistant(Long aspId, String status) throws Exception;
	Map<String, Object> saveuploadallocation(AllocationUploadBean bean) throws Exception;
	List<Alloc_gen_hd> getAllocGenHdUserId(String user_id);
	void updateDownload(Long alloc_id) throws Exception;
	void annualAllocationSelfApproval(Long aspId, String user) throws Exception;
	List<AllocationBean> getSummaryDetailsAnnualPlanByDivision(Long aspId, String year, String status) throws Exception;
	Alloc_gen_hd regenerateAllocation(Long allocGen_id) throws Exception;
	List<Object> getProductListForMonthlyAllocationPriviousYear(String prodType, List<String> smp_sa_prod_group,
			String discProd, String zeroStock, String div_id, String pmtTeam, String period_code, String core,
			String fin_yea, String isPrivious) throws Exception;
	List<Object> getProductListForMonthlyAllocation(String prodType, List<String> smp_sa_prod_group, String discProd,
			String zeroStock, String div_id, String pmtTeam, String period_code, String core, String fin_year,
			String isPrivious, String selectedPeriodCode, String selectedYear, String allocType, String empId)
			throws Exception;
	List<AllocConHd> getAllocConHdUserId(String user_id);
	List<AllocationBean> getTeamWithPriviousYearCount(String empId, String divId, String year) throws Exception;
	List<Object> getTeamForMonthly(String empId) throws Exception;

	Map<String, Object> saveuploadallocationWithoutSRT(AllocationUploadBean bean) throws Exception;
	
	//P&G
	Map<String, Object> getStaffCount(String div_id,String sub_team_code,String alloc_type);
	Map<String, Object> byUserStaffNames(String divId, String allocationMonthPeriodCode, String saveMode,
			String planType, String allocationId, String allocationCycle, String allocatioinbProductType, String year,
			String allocationMode, String fieldType, String coreTeamId,String sub_team_code,String alloc_type);
	Object getallocgenrateddetails_upload_modify(String empId);
	Alloc_Temp_Header getHeaderDetailsBeforeApprove(Long valueOf);
	List<AllocationBean> getAllocEnteredProducts_before_approve(String allocId, String prodType);
	Object getEnteredStaffDetails_for_modify(String prodId, String allocationId);
	boolean updateStaffDetailsBeforeApproval(AllocationBean bean) throws Exception;
	List<AllocationBean> getAllocation_modified_Log(Long header_id);
	String genarateAllocation_modified_Log_report(List<AllocationBean> list) throws IOException;
}
