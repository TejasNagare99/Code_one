package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.BatchwiseStockStmtReport;
import com.excel.model.BatchwiseStockStmtReport_SG;
import com.excel.model.Scheme_Desription_Bean;

public interface BatchWiseStkStmtReportService {
	
	String generateBatchwiseStockStmtReportReport(List<BatchwiseStockStmtReport> list,ReportBean bean, String companyname,String empId, List<Scheme_Desription_Bean> listOfSchemDescription) throws Exception;

	String generateBatchwiseStockStmtReportReport_SG(List<BatchwiseStockStmtReport_SG> list, ReportBean bean,
			String companyname, String empId) throws Exception;


}
