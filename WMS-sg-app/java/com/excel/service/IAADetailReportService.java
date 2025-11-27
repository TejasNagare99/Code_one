package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.IAADetailReport;
import com.excel.model.IAADetailReport_SG;

public interface IAADetailReportService {
	
	String GenerateIAADetailReport(List<IAADetailReport> lst, String startDate, String endDate, String company,String empId,String gprmInd) throws Exception;
	String GenerateIAADetailReport_SG(List<IAADetailReport_SG> lstSG, String startdate, String enddate, String company,
			String empId, String gprmInd, ReportBean bean) throws Exception;
	

}
