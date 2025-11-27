package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.bean.AuditTrailBean;
import com.excel.model.SupplierMasterModel;

public interface SupplierMasterDownload_PDF_Service {
	String GenerateSupplierMasterDowloadPDF(List<SupplierMasterModel> list,AuditTrailBean bean,String username,String companyname,HttpSession session,HttpServletRequest request)throws Exception;

}
