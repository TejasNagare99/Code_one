package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.dispatch_register_report2;
import com.excel.model.dispatch_register_report2_with_parameters;


public interface DispatchRegisterReport2 {

	String generateDispatchRegisterReport2Excel(List<dispatch_register_report2> list, ReportBean bean,
			String companyname,String empId) throws Exception;
	
	String generateDispatchRegisterRevisedReport2ExcelDoctorSupply(List<dispatch_register_report2_with_parameters> list,
			ReportBean bean, String companyname,String empId) throws Exception;

}
