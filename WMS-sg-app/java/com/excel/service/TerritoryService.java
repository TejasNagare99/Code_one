package com.excel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.TerritoryBean;
import com.excel.model.DivField;
import com.excel.model.FieldStaff;
import com.excel.model.Location;
import com.excel.model.Team;
import com.excel.model.TerrMaster;
import com.excel.model.V_Terr_Master;
import com.excel.repository.TerritoryRepository;

public interface TerritoryService{

	List<DivField> getLevelForTerritory(String divId);

	void saveTerritory(TerritoryBean tb, HttpServletRequest request) throws Exception;

	Boolean checkUniqueTerrCode(String divId, String terrCode);

	List<TerrMaster> getMgrTwo(String mgrId);

	List<TerrMaster> getTerrCodeList(String level, String divId, String trainingInd);

	List<V_Terr_Master> getAllTerrCodeList(String company);

	List<TerrMaster> getMgr(String levelCode, String trainingInd, String divId);

	List<TerrMaster> getTerrCodeListForModify(String level, String divId, String trainingInd);

	List<V_Terr_Master> getTextParameterTerrDetail(String company,String name, String parameter, String text);

	List<TerrMaster> getTerrDetailById(String terrId);

	void updateTerritory(String terrId, TerritoryBean tb, HttpServletRequest request) throws Exception;

	List<DivField> getLevelForTerritoryModify(String divId, String fsCategory);

	TerrMaster getTerrDetailByTerrCode(String terr_code,Long terr_div_id);

	//List<DivField> getLevelForTerritoryModify();
	
	Team getTeamCodeByTeamId(String teamId)throws Exception;
	
	Long getTeamIdByFsId(String fsid)throws Exception;
	
	Long getTeamIdByHqId(String hqid)throws Exception;
	
	public Map<String, Object> validateCsvFile(MultipartFile file, String username) throws Exception;

	Map<String, Object> saveTerritoy_master_csv(MultipartFile file, String username, String divId, String ip_addres) throws Exception;

	Boolean checkFileExist_on_terr_master_upload_template_table(String originalFilename);

	
}
