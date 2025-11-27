package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.AuditTrailBean;
import com.excel.model.DispatchAuditTrailBean;

public interface DispatchAuditDownloadService {
	String GenerateDispatchAuditDownload_Report(AuditTrailBean bean,List<DispatchAuditTrailBean> list,String companyname,
			String username,String fromdate,String todate,String empId) throws Exception;
    String GenerateDispatchAuditDownload_Pdf(AuditTrailBean bean, List<DispatchAuditTrailBean> DataList,String companyname,String username,String date, String frm_date, String to_date,HttpSession session,HttpServletRequest request) throws Exception;
	

}
