package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.AllocDispatchTracker;
import com.excel.model.Allocation_download;

public interface Allocation_Download_excel_service {

	
	String Generate_Allocation_Download_excel(List<Allocation_download> list, ReportBean bean,String companyname,String empId)
			throws Exception;;
			
	 String Generate_Dispatch_Allocation_Download_excel(List<AllocDispatchTracker> list,String companyname,String startDate,String endDate,String empId)
						throws Exception;;

}
