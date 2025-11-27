package com.excel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.excel.bean.GoApptiveFieldstaffBean;
import com.excel.model.FieldStaff;
import com.excel.model.GoApptiveDspDetails;
import com.excel.model.TerrMaster;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.TransactionalRepository;

public interface GoApptiveService {
	

	public void addGoApptiveFieldstaff(GoApptiveFieldstaffBean fieldstaffDetails) throws Exception;

	public int getGoaptiveTerrMaster();

	public int getDispatchDetailsTerrMaster();
	
	List<GoApptiveDspDetails> pushDispToGoapptive(Date stdt,Date endt)throws Exception;

	void pushDataToMainFiedldstaff() throws Exception;

	int getTeamBrandDetails(String year);

	void updateRecords();

	//List<TerrMaster> getTerrDetailByTerrCode(String terr_code);
}
