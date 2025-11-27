package com.excel.service;

import java.util.List;
import java.util.Map;

import com.excel.bean.ReportBean;
import com.excel.model.Stock_Statement_Non_Sale;
import com.excel.model.Stock_Statement_Non_Sale_VET;
import com.excel.model.Stock_statement_pfz;


public interface StockStmentReportService {
	
	String generateStockStatmentReport(List<Stock_statement_pfz> list, String companyname,String companycode,String empId, String  cfa_to_stk_ind) throws Exception;
	Map<String, Object> stockstatmentNonsal(List<Stock_Statement_Non_Sale> list,ReportBean bean,String companyname,String empId) throws Exception;
	Map<String, Object> stockstatmentNonsal_VET(List<Stock_Statement_Non_Sale_VET> stkstmt, ReportBean bean,
			String companyname, String empId) throws Exception;

}
