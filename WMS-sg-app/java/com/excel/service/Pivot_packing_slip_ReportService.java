package com.excel.service;

import java.util.List;

public interface Pivot_packing_slip_ReportService {

	String Generate_Pivot_packing_slip_Report(List<Object> list,List<Object> summaryList,List<Object> headerList
			,List<Object> batchList,List<Object>productList,List<Object> countList,
			String divText,int tsoCount,String empId)throws Exception;
	
}