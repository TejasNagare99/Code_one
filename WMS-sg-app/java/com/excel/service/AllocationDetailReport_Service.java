package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.AllocationDetailReportModel;


public interface AllocationDetailReport_Service {
	
	String GenerateAllocationDetailReport(ReportBean bean,List<AllocationDetailReportModel> list,String Companyname,String companyCode,String empId);

}
