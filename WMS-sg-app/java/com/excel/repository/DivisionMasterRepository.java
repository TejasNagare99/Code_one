package com.excel.repository;

import java.util.List;

import com.excel.model.DivMaster;
import com.excel.model.Usermaster;

public interface DivisionMasterRepository {

	List<DivMaster> getStandardDivisionList() throws Exception;
	List<DivMaster> getDivAccessedByUser(String userId) throws Exception;
	String getDivNameByDivID(Long div_id) throws Exception;
	
	List<DivMaster> getActiveList() throws Exception;
	DivMaster getObjectById(Long divId) throws Exception;
	List<DivMaster> getDivreport(String userId)throws Exception;
	List<DivMaster> getDivIdList()throws Exception;
	List<DivMaster> getAllDivAccessedByUser(String userId) throws Exception;
	List<DivMaster> getAllStandardDivisionList() throws Exception;
	List<Usermaster> getDecryptedPassword(String userId) throws Exception;
	public Long getDivIdByName(String divName) throws Exception;
	
}
