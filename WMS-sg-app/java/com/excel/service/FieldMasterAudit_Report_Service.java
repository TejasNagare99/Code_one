package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.AuditTrailBean;
import com.excel.model.FieldMasterAttrib;

public interface FieldMasterAudit_Report_Service {

	String getFieldMasterReport(List<FieldMasterAttrib> list,String companyname,String username,String empId)throws Exception;
	
	String getFieldMasterPrint(List<FieldMasterAttrib> list,String companyname,String username,AuditTrailBean bean,HttpSession session,HttpServletRequest request)throws Exception;
	
}
