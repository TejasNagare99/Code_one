package com.excel.service;

import java.util.List;

import com.excel.bean.FstaffMas_PrxBean;
import com.excel.bean.ReportBean;
import com.excel.model.Dg_veeva_emp;
import com.excel.model.Dg_veeva_pos;
import com.excel.model.Dg_veeva_pos_emp;
import com.excel.model.Temp_fstaffmas_prx_error;

public interface PforceRxReportGeneration {
	String GeneratePforceRxFieldstaffInsertErrorReport(List<Temp_fstaffmas_prx_error> lst,ReportBean bean,String company,String empId)throws Exception;

	String GenerateDgVeevaPosEmpReport(List<Dg_veeva_pos_emp> lst,ReportBean bean,String company,String empId)throws Exception;
	
	String GenerateDgVeevaPosReport(List<Dg_veeva_pos> lst,ReportBean bean,String company,String empId)throws Exception;
	
	String GeneratePrxFieldstaffReport(List<FstaffMas_PrxBean>newlst,List<FstaffMas_PrxBean>updatelst,List<FstaffMas_PrxBean>terrlst,ReportBean bean,String company,String empId)throws Exception;
	
	String GenerateDgVeevaEmpReport(List<Dg_veeva_emp> lst, ReportBean bean, String company,String empId) throws Exception;
	
}
