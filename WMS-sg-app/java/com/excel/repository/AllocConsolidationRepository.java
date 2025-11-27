package com.excel.repository;

import java.util.List;

import com.excel.bean.AllocationConsolidationBean;
import com.excel.model.AllocConHd;
import com.excel.model.AspHeader;
import com.excel.model.DivMaster;

public interface AllocConsolidationRepository {

	List<Object> getAnnualPlanEnteredManagerByDivision(String empId,Long divId, String year, String status,String compcode) throws Exception;

	String getAnnualPlanEnteredBrandsByHeader(Long aspId) throws Exception;

	String getAnnualPlanEnteredPlanType(Long aspId) throws Exception;

	AllocConHd getAllocConHdForAnnualPlan(Long divId, String fin_year, String allocConType);

	String getAnnualPlanEnteredBrands(Long alloc_con_id) throws Exception;

	String getAnnualPlanEnteredPlanTypeByAspHeader(Long aspId) throws Exception;

	List<Object> getConsolatedAnnualPlan(Long LocId, String year) throws Exception;

	AllocConHd getObjectAllocConHeader(Long headerId) throws Exception;

	String getMonthlyEnteredBrands(Long allocGenId) throws Exception;

	AllocConHd getAllocConHdForMonthlyAllocation(Long divId, String fin_year, String allocConType, String allocMonth,String allocType);

	String getMonthlyEnteredPlanTypeByAspHeader(Long allocGenId) throws Exception;

	List<Object> getMonthlyAllocationEnteredManagerByDivision(Long divId, String year, String allocMonth,
			String allocType, String monthOfUse, String status,String compcode) throws Exception;

	List<DivMaster> getDivisionsEnteredForMonthly(String allocMonth, String year, String allocType) throws Exception;

	List<DivMaster> getDivisionsEnteredForAnnualPlan(String year, String empId) throws Exception;

	List<AllocationConsolidationBean> getAllocConHeaderByType(String alloc_con_type) throws Exception;
}
