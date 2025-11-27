package com.excel.service;

import java.io.IOException;
import java.util.List;

import com.excel.model.Audit_log;
import com.excel.model.ViewDownloadIaaAuditTrail;

public interface IaaAuditTrail_Excel_Service {
	public String generateIaaAuditExcel(List<ViewDownloadIaaAuditTrail> lst,String companyname,String usernname,
			String frm_date,String to_date,String empId) throws Exception;
	public String generateAuditLogExcel(List<Audit_log> list,String companyname,String fromDate,String toDate) throws IOException;
}
