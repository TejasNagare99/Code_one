package com.excel.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.excel.bean.RuleBasedAllocationBean;
import com.excel.model.Alloc_Remark;
import com.excel.model.Alloc_Temp;
import com.excel.model.Period;

public interface RuleBasedAllocationApprovalService {

	List<RuleBasedAllocationBean> approvalDetails(long division, long period, String up_status, String ucycle,Long allocTempId)throws Exception;
	
	Map<String, Object> getProductWiseStock(long division, long period, String up_status, String ucycle,Long alloctempHdId) throws Exception;

	Map<String, Object> selfApprovalRuleBasedAllocation(Long alloc_temp_hd_id, String status,String remark) throws Exception;

	List<Alloc_Temp> saveAndMoveToArchieve(Long division, Period period, String isapproved, String upload_status,
			String ucycle, Long alloc_temp_hd_id, String app_remark, String stk_cfa_ind,String empId,String ipAdd) throws Exception;

	List<RuleBasedAllocationBean> approvalListForSelfAppr(String empId, String user_role, String locId,String compcd);
	List<Alloc_Temp> saveAndMoveToArchieveByProcedure(Long division, Period period, String isapproved,
			String upload_status, String ucycle, Long alloc_temp_hd_id, String app_remark, String stk_cfa_ind,
			String empId, String ipAdd) throws Exception;

	String getAllocRemark(Long allocTempId)throws Exception;
}
