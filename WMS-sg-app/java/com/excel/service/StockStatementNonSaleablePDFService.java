package com.excel.service;

import java.util.List;

import com.excel.model.Stock_Statement_Non_Sale;
import com.excel.model.Stock_Statement_Non_Sale_VET;

public interface StockStatementNonSaleablePDFService {
	
	String GenerateStockStatementPDF(List<Stock_Statement_Non_Sale> list,String from_date,String to_date,String location_name,int div_id,int depot_id,String zero_ind,
			String prodType_id,String prod_name,String stockType_id,String userid,String companyname,String username,String empId)throws Exception;

	String GenerateStockStatementPDF_VET(List<Stock_Statement_Non_Sale_VET> list,String from_date,String to_date,String location_name,int div_id,int depot_id,String zero_ind,
			String prodType_id,String prod_name,String stockType_id,String userid,String companyname,String username,String empId)throws Exception;

}
