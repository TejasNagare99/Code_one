package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.BatchMasterAuditTrailModel;

public interface Batch_Master_AuditTrail_PDF_Service {

	
	String GenerateBatch_Master_Audit_Trail_PDF(List<BatchMasterAuditTrailModel>list,String fromdate,String todate,String username,String companyname,HttpSession session,HttpServletRequest request)throws Exception;
}
