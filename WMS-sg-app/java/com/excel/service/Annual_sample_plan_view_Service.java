package com.excel.service;

import java.util.List;

import com.excel.model.Annual_sample_plan_view_report;
import com.excel.model.Annual_sample_plan_view_report_batchwise;
import com.excel.model.Annual_sample_plan_view_report_cons;
import com.excel.model.Annual_sample_plan_view_report_itemwise;

public interface Annual_sample_plan_view_Service {
	
	String generate_annual_sample_plan_view_report(List<Annual_sample_plan_view_report>list,String companyname,String finyr,String companyCode,String empId)throws Exception;
	
	String generate_annual_sample_plan_view_Batchwise_report(List<Annual_sample_plan_view_report_batchwise> list,String companyname)throws Exception;

	String generate_annual_sample_plan_view_Itemwise_report(List<Annual_sample_plan_view_report_itemwise> list,String companyname,String empId)throws Exception;
	
	String generate_annual_sample_plan_con_report(List<Annual_sample_plan_view_report_cons>list,String companyname,String finyr)throws Exception;
}
