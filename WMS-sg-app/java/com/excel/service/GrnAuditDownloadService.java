package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.AuditTrailBean;
import com.excel.model.GrnAuditTrailBean;

public interface GrnAuditDownloadService {

	String GenerateAuditDownloadReport(AuditTrailBean bean,List<GrnAuditTrailBean> list,String companyname,String username,
			String fromdate,String todate,String empId) throws Exception;
    String GenerateAuditDownloadPdf(AuditTrailBean bean, List<GrnAuditTrailBean> DataList,String companyname,String username,String date, String frm_date, String to_date,HttpSession session,HttpServletRequest request) throws Exception;
}
