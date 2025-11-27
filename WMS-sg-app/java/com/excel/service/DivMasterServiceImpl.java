package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.DivMaster;
import com.excel.model.V_Emp_Div_Access;
import com.excel.repository.DivMasterRepository;

@Service
public class DivMasterServiceImpl implements DivMasterService {
	
	@Autowired private DivMasterRepository divMasterRepository;
	
	@Override
	public List<V_Emp_Div_Access> getDivListByEmpId(String emp_id, String comp_cd) throws Exception {
		return divMasterRepository.getDivListByEmpId(emp_id, comp_cd);
	}

	@Override
	public List<DivMaster> getEntireDivMasterList() throws Exception {
		
		return divMasterRepository.getEntireDivMasterList();
	}
	
	
}
