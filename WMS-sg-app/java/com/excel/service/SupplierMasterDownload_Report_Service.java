package com.excel.service;

import java.util.List;

import com.excel.bean.AuditTrailBean;
import com.excel.bean.ReportBean;
import com.excel.model.SupplierMasterModel;

public interface SupplierMasterDownload_Report_Service {

	String GenerateSupplierMasterDowloadReport(List<SupplierMasterModel> list,AuditTrailBean bean,String username,String empId)throws Exception;
}
