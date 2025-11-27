package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.HqMasterAuditTrailModel;

public interface HqMasteraudit_trail_PrintPdf_Service {

	String generateHq_Master_audit_trail_print_pdf(List<HqMasterAuditTrailModel> list, String username,String companyname,HttpSession session,HttpServletRequest request)throws Exception;
}
