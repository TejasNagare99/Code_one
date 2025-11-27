package com.excel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.AllocationConsolidationBean;
import com.excel.bean.AnnualPlanConsolidationBean;
import com.excel.bean.MonthlyAllocConsolidationBean;
import com.excel.model.AllocConHd;
import com.excel.model.DivMaster;

public interface AllocationConsolidationService {
	public List<Object> getAnnualPlanEnteredManagerByDivision(String empId,Long divId, String year, String status,String compcode) throws Exception;

	public String getAnnualPlanEnteredBrandsByHeader(Long aspId) throws Exception;

	AllocConHd getAllocConHdForAnnualPlan(Long divId, String fin_year, String allocConType);
	
	Map<String, Object>  saveAnnualPlanTeamConsolidation(AnnualPlanConsolidationBean bean) throws Exception;
	
	List<Object> getConsolatedAnnualPlan(Long LocId, String year) throws Exception;

	Map<String, Object> saveAnnualPlanCompanyConsolidation(AnnualPlanConsolidationBean bean) throws Exception;

	void sendAnnualAllocationCompany(Long allocConId, String user, String email, String remark,
			HttpServletRequest request, HttpSession session) throws Exception;

	void finalAnnualTeamApproval(Long allocCOnId, String last_approved_by, String comp_cd, String isapproved,String motivation) throws Exception;

	Map<String, Object> saveMonthlyAlloConsolidation(MonthlyAllocConsolidationBean bean) throws Exception;

	List<Object> getMonthlyAllocationEnteredManagerByDivision(Long divId, String year, String allocMonth,String allocType,String monthOfUse,String status,String compcode) throws Exception;

	void sendMonthlyAllocationTeam(Long allocConId, String user, String email, String remark,HttpServletRequest request, HttpSession session) throws Exception;

	void finalMonthlyTeamApproval(Long allocCOnId, String last_approved_by, String comp_cd, String isapproved,String motivation) throws Exception;

	void updateDownload(Long alloc_id) throws Exception;
	
	List<DivMaster> getDivisionsEnteredForMonthly(String allocMonth, String year, String allocType) throws Exception;

	List<DivMaster> getDivisionsEnteredForAnnualPlan(String year, String empId) throws Exception;
	
	List<AllocationConsolidationBean> getAllocConHeaderByType(String alloc_con_type) throws Exception;

}