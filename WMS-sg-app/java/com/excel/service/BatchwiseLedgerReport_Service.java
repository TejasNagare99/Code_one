package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.BatchWiseStockLedgerModel;

public interface BatchwiseLedgerReport_Service {

	String GenerateBatchwiseLedgerReport(List<BatchWiseStockLedgerModel> lst, ReportBean bean ,String Companyname,String empId)throws Exception;
}
