package com.excel.service;

import java.util.List;

import com.excel.model.Productwise_batchwise_locationwise_stock;

public interface Productwise_batchwise_locationwise_stock_Report_Service {
	
	String generateProductwise_batchwise_locationwise_stock_Report(List<Productwise_batchwise_locationwise_stock> list,String Companyname,String empId) throws Exception;
	
}
