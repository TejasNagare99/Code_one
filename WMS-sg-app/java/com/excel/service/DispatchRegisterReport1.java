package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.DispatchIntimationEmail;
import com.excel.model.dispatch_register_report1;
import com.excel.model.dispatch_register_report1_with_parameters;

public interface DispatchRegisterReport1 {

	String generateDispatchRegisterReport1Excel(List<dispatch_register_report1> list, ReportBean bean,
			String companyname,String empId) throws Exception;
	
	String generateDispatchRegisterRevisedReport1Excel(List<dispatch_register_report1_with_parameters> list, ReportBean bean,
			String companyname,String empId) throws Exception;
	String generateDispatchIntimationEmail(List<DispatchIntimationEmail> list,String stdate, String endate) throws Exception;
}
