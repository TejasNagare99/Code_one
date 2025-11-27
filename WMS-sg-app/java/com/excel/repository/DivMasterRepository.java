package com.excel.repository;

import java.util.List;

import com.excel.model.DivMaster;
import com.excel.model.MonthList;
import com.excel.model.V_Emp_Div_Access;

public interface DivMasterRepository {
	
	public List<V_Emp_Div_Access> getDivListByEmpId(String emp_id, String comp_cd)throws Exception;

	List<MonthList> getMonthList(String companyCode,String year)throws Exception;

	Long getDivIDbyCode(String div_code_nm);

	List<MonthList> getMonthListAutoDispatch(String companyCode, String year) throws Exception;

	List<V_Emp_Div_Access> getActiveDivListByEmpId(String emp_id, String comp_cd) throws Exception;

	List<DivMaster> getEntireDivMasterList() throws Exception;
	
	DivMaster getById(Long divId) throws Exception;
	
}
