package com.excel.repository;

import java.util.List;
import java.util.Map;

import com.excel.bean.RuleBasedAllocationBean;

public interface RuleBasedAllocationApprovalRepository {

	List<RuleBasedAllocationBean> approvalDetails(long division, long period, String up_status, String ucycle,Long allocTempId)throws Exception;

	Map<String, Object> getProductWiseStock(long division, long period, String up_status, String ucycle,Long alloctempHdId) throws Exception;

	List<RuleBasedAllocationBean> approvalListForSelfAppr(String empId, String user_role, String locId);

	void ApproveMonthlyAllocation(Long allocTempId, String userId, String userIp) throws Exception;

	List<RuleBasedAllocationBean> approvalListAllocConForSelfAppr(String empId, String user_role, String locId,String Compcd);

}
