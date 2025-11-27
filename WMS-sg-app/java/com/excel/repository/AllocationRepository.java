package com.excel.repository;

import java.util.List;
import java.util.Map;

import com.excel.bean.AllocationBean;
import com.excel.bean.PolicyHeaderBean;
import com.excel.model.AllocConHd;
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
import com.excel.model.V_Dispatch_Period;
public interface AllocationRepository {

	List<Object> getTeamWithCount(String empId) throws Exception;
	List<Object> getSampleProductList(String divId, List<String> prodGroup, String prodType) throws Exception;
	List<Object> getPlanningType(String empId) throws Exception;
	List<AllocationBean> getProductDetails(Long prod_id, String year);
	List<Object> getBrandsForTeam(String empId, String teamId, String planType,String subTeamcode) throws Exception;
	List<Object> getPreviousYearPeriodWiseDetails(String prodId, String divId, String prodGroup, String prodType, String year)
			throws Exception;
	List<AllocationBean> getPreviousYearCount(Long prod_id, Long divId,String year);
	boolean checkProductPresent(Long prodId, Long divId, String prodGroup);
	AspHeader getAspHeaderByManagerAndUserId(Long userId, Long mgrId, String fin_year,String sub_team_code);
	AspHeaderArc getPreviousYearSamplePlan(Long userId, Long mgrId, String fin_year);
	List<Object> getPlanningTypeMonthly() throws Exception;
	V_Dispatch_Period getDispatchPeriod();
	List<Object> getProductListForMonthlyAllocation(String prod_sub_type_id, List<String> smp_sa_prod_group,String year)
			throws Exception;
	Long getAllocationByCount(String divId, String staff) throws Exception;
	List<AllocationBean> getManagerListForAssistant(String asst_user_id) throws Exception;
	Long getAllocationCycleNumber(String div_id, String period_code, String fin_year, String mgrId) throws Exception;
	Map<String, Object> getAllocationEnt(Long alloc_gen_id) throws Exception;
	Alloc_gen_ent getObjectById(Long entId) throws Exception;
	Alloc_gen_hd getAllocationHeader(String div_id, String period_code, String fin_year, String frequency, String empId)
			throws Exception;
	List<Object> getEnteredAllocationById(String allocationId,String allocationMode,String column,String alloc_smp_sa_prod_group_id);
	List<Object> getSelfApprovalDataMonthly(String user_id,String ind);
	List<Alloc_gen_hd> getAllocGenratedDetails(String user_id);
	List<AllocationBean> getAllocEnteredBrands(String alloc_gen_id);
	List<AllocationBean> getAllocEnteredProducts(String alloc_gen_id,String prodType);
	Alloc_gen_dt getObjectByAllocDtlId(Long dtlId) throws Exception;
	List<AllocationBean> getAllocationDetail(Long allocationId, String year);
	List<Alloc_gen_dt> getAllocationDetail(Long allocId);
	void deleteAllocationByBrandId(Long allocId, Long brandId, String year) throws Exception;
	List<AllocationBean> getAnnualPlanEnteredProductDetail(Long divId, Long brandId, String year, String userId);
	AspHeader getObjectAspHeader(Long headerId) throws Exception;
	AspDetail getObjectAspDetail(Long headerId) throws Exception;
	List<AllocationBean> getSummaryDetailsAnnualPlan(Long aspId, String year, String status) throws Exception;
	void discardAnnualAllocation(String status, Long aspId) throws Exception;
	Long getSmpId() throws Exception;
	void approveAnnualAllocation(String status, Long aspId) throws Exception;
	void deleteProductFromAnnualPlan(Long headerId) throws Exception;
	List<AnnualPlanPreviousYearCount> getPreviousYearCountProcedure(Long divId, Long prodId, String year)
			throws Exception;
	List<AnnualAllocationPreviousYearData> getPreviousYearDataAnnualPlan(String divId, String prodId, String year)
			throws Exception;
	void changeApprovalStatusForAssistand(Long allocId, String year, String status) throws Exception;
	List<Object> getSampleProductListForAnnualPlan(String divId, List<String> prodGroup, String prodType,
			String alloc_number) throws Exception;
	List<AllocationBean> getEnteredStaffDetails(List<String> prodId, String allocationId);
	boolean checkEnetedBrandByProductType(String prod_sub_type_id, List<String> smp_sa_prod_group, String year,
			Long alloc_gen_id) throws Exception;
	List<Alloc_gen_hd> getAllocGenHdUserId(String user_id);
	List<AllocationBean> getSummaryDetailsAnnualPlanByDivision(Long aspId, String year, String status) throws Exception;
	void updateAllocDt(AllocConHd header, Long alloc_con_dtl_id, Alloc_gen_hd alloc_gen_id);
	void deleteAllocationDetails(Long allocId) throws Exception;
	void changeApprovalStatus(Long allocId, String status) throws Exception;
	List<Object> getProductListForMonthlyAllocationPriviousYear(String prodType, List<String> smp_sa_prod_group,
			String discProd, String zeroStock, String div_id, String pmtTeam, String period_code, String core,
			String fin_year, String isPrevious) throws Exception;
	List<Object> getProductListForMonthlyAllocation(String prodType, List<String> smp_sa_prod_group, String discProd,
			String zeroStock, String div_id, String pmtTeam, String period_code, String core, String fin_year,
			String isPrevious, String selectedPeriodCode, String selectedYear, String allocType, String userEmpId)
			throws Exception;
	void discardAllocation(Long allocId, Alloc_gen_hd hd) throws Exception;
	void lockProduct(Long loc_id, Long prod_id, String user_id, String log_time, Long div_id) throws Exception;
	List<AllocConHd> getAllocConHdUserId(String user_id);
	List<AllocationBean> getTeamWithPriviousYearCount(String empId, String divId, String year) throws Exception;
	List<Object> getTeamForMonthly(String empId) throws Exception;
	
	PolicyHeaderBean getCurrSamplePolHdData() throws Exception;
	Object getallocgenrateddetails_upload_modify(String empId);
	Alloc_Temp_Header getHeaderDetailsBeforeApprove(Long valueOf);
	List<AllocationBean> getAllocEnteredProducts_before_approve(String allocId, String prodType);
	Object getEnteredStaffDetails_for_modify(String prodId, String allocationId);
	Boolean saveLogFor_Alloc_temp(Long alloc_temp_header_id, String alloc_tem_prod_id, Long long1) throws Exception;
	Alloc_Temp getObjectByAllocTemp_detail(Long long1);
	List<AllocationBean> getAllocation_modified_Log(Long header_id);
}
