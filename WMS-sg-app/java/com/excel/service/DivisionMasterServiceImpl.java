package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.excel.model.DivMaster;
import com.excel.model.Usermaster;
import com.excel.repository.DivisionMasterRepository;

@Service
public class DivisionMasterServiceImpl implements DivisionMasterService {
	 
	@Autowired DivisionMasterRepository divisionmasterrepository;

	@Override
	public List<DivMaster> getStandardDivisionList() throws Exception {
		return this.divisionmasterrepository.getStandardDivisionList();
	}

	@Override
	public List<DivMaster> getDivAccessedByUser(String userId) throws Exception {
		return divisionmasterrepository.getDivAccessedByUser(userId);
	}

	@Override
	public String getDivNameByDivID(Long div_id) throws Exception {
		
		return divisionmasterrepository.getDivNameByDivID(div_id);
	}
	
	@Override
	public List<DivMaster> getActiveList() throws Exception {
		// TODO Auto-generated method stub
		return divisionmasterrepository.getActiveList();
	}

	@Override
	public DivMaster getObjectById(Long divId) throws Exception {
		// TODO Auto-generated method stub
		return divisionmasterrepository.getObjectById(divId);
	}

	@Override
	public List<DivMaster> getDivreport(String userId) throws Exception {
		// TODO Auto-generated method stub
		return divisionmasterrepository.getDivreport(userId);
	}

	@Override
	public List<DivMaster> getDivIdList() throws Exception {
		// TODO Auto-generated method stub
		return divisionmasterrepository.getDivIdList();
	}

	@Override
	public List<DivMaster> getAllDivAccessedByUser(String userId) throws Exception {
		// TODO Auto-generated method stub
		return divisionmasterrepository.getAllDivAccessedByUser(userId);
	}

	@Override
	public List<DivMaster> getAllStandardDivisionList() throws Exception {
		// TODO Auto-generated method stub
		return divisionmasterrepository.getAllStandardDivisionList();
	}

	
	
	
	
	 
	@Override
	public List<Usermaster> getDecryptedPassword(String userId) throws Exception {
		// TODO Auto-generated method stub
		return divisionmasterrepository.getDecryptedPassword(userId);
	}

	@Override
//	@Cacheable(value="divIdByName",key="#divName")
	public Long getDivIdByName(String divName) throws Exception {
		// TODO Auto-generated method stub
		return divisionmasterrepository.getDivIdByName(divName);
	}
}
