package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.StockWithdrawalReport;

public interface StockwithdrawalReportService {

	public String generateStockwithdrawalReport(ReportBean bean,List<StockWithdrawalReport> lst,String company,String empId) throws Exception;
}
