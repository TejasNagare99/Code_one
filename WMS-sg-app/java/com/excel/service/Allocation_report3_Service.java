package com.excel.service;

import java.util.List;

import com.excel.model.Allocation_report_3;

public interface Allocation_report3_Service {
	
	String generate_Allocation_Report3_Report(List<Allocation_report_3> list,String empId)throws Exception;

}
