package com.excel.service;

import java.util.List;

import com.excel.model.DivMaster;
import com.excel.model.V_Emp_Div_Access;

public interface DivMasterService {
	
	public List<V_Emp_Div_Access> getDivListByEmpId(String emp_id, String comp_cd)throws Exception;
	
	List<DivMaster> getEntireDivMasterList() throws Exception;
}
