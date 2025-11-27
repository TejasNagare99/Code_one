package com.excel.service;

import java.util.List;

import com.excel.model.BatchMasterAuditTrailModel;

public interface Batch_Master_AuditTrail_Report_Service {
	
	String GenerateBatch_Master_Audit_Trail_Report(List<BatchMasterAuditTrailModel>list,String fromdate,String todate,String username,String empId);
	
	
	
}
