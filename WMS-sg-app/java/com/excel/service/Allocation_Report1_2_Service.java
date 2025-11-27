package com.excel.service;

import java.util.List;

import com.excel.bean.FinalApprovalLogBean;
import com.excel.model.Allocation_report_1;
import com.excel.model.Allocation_report_2;
import com.excel.model.Stock_Reco_With_sfa;

public interface Allocation_Report1_2_Service {

	String generate_Angular_allocation1_report(List<Allocation_report_1> lst , String companyname,String empId)throws Exception;
	
	String generate_Angular_allocation2_report(List<Allocation_report_2> lst , String companyname,String empId)throws Exception;
	
	String generate_Stock_Reco_SFA_report(List<Stock_Reco_With_sfa> lst)throws Exception;
	
	public String generate_final_approval_log_report(List<FinalApprovalLogBean> lst);
}
