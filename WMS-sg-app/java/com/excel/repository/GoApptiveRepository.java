package com.excel.repository;

import java.util.Date;
import java.util.List;

import com.excel.bean.GoApptiveTerrMasterBean;
import com.excel.bean.GoapptiveTeamBrandbean;
import com.excel.model.GoApptiveDspDetails;

public interface GoApptiveRepository {

	List<GoApptiveTerrMasterBean> getgoaptiveterrmasterdata(Long terrid)throws Exception;
	
	void insertgoaptivedata(List<String> terrCodes)throws Exception;
	
	List<GoApptiveDspDetails> pushDispToGoapptive(Date stdt,Date endt)throws Exception;

	void pushDataToMainFiedldstaff() throws Exception;

	List<GoapptiveTeamBrandbean> getgoaptiveteambranddata() throws Exception;

	void insertTeamBrandData(List<String> terrCodes) throws Exception;

	void deleteGoapptiveTerritory(String terr_code, Long div_id) throws Exception;

	void updateRecords();

	void insertDspData(List<Long> dspIds) throws Exception;
}
