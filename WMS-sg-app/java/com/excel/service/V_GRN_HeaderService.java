package com.excel.service;

import java.util.List;

import com.excel.model.V_GRN_Header;

public interface V_GRN_HeaderService {
	
	List<V_GRN_Header> getGrnListByEmpIdAndStatus(String grnStatus, String grnApprStatus, String empId,
			String companyCode, String finYear) throws Exception;

	List<V_GRN_Header> getGrnListByLocId(Long locId, String comapnyCode, String empId, String fromDate, String toDate)
			throws Exception;
	
	V_GRN_Header getObjectById(Long grnId) throws Exception;
}
