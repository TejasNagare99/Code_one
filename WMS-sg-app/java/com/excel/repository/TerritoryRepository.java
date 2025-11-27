package com.excel.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.excel.model.Abolish_Terr_Log;
import com.excel.model.DG_TERRMAST_UPLOAD_FOR_GCO;
import com.excel.model.Dg_AbolishTerr_Codes;
import com.excel.model.DivField;
import com.excel.model.FieldStaff;
import com.excel.model.Location;
import com.excel.model.TERR_MASTER_UPLOAD_TEMPLATE;
import com.excel.model.Team;
import com.excel.model.TerrMaster;
import com.excel.model.V_Terr_Master;


public interface TerritoryRepository {

	List<DivField> getLevelForTerritory(String divId);

	Boolean checkUniqueTerrCode(String divId, String terrCode);

	//List<FieldStaff> getRbm(String levelCode, String trainingInd);

	List<TerrMaster> getMgr(String levelCode, String trainingInd, String divId);

	List<TerrMaster> getMgrTwo(String mgrId);

	List<TerrMaster> getTerrCodeList(String level, String divId, String trainingInd);

	List<V_Terr_Master> getAllTerrCodeList(String company);

	List<TerrMaster> getTerrCodeListForModify(String level, String divId, String trainingInd);

	List<V_Terr_Master> getTextParameterTerrDetail(String company,String name, String parameter, String text);

	List<TerrMaster> getTerrDetailById(String terrId);

	TerrMaster getObjectByTerrId(Long terr_id) throws Exception;

	List<DivField> getLevelForTerritoryModify(String divId, String fsCategory);

	List<TerrMaster> getTerrDetailByTerrCode(String terr_code);

	//List<DivField> getLevelForTerritoryModify();

	TerrMaster getTerrDetailByTerrCode(String terr_code,Long terr_div_id);
	
	Team getTeamCodeByTeamId(String teamId)throws Exception;
	Long getTeamIdByFsId(String fsid)throws Exception;
	Long getTeamIdByHqId(String hqid)throws Exception;
	
	List<Dg_AbolishTerr_Codes> getAbolishTerrCsvCount(String file) throws Exception;
	boolean isFileExist(String file) throws Exception;
	List<Abolish_Terr_Log> getReport(String file, String ind);
	Long errCount(String file, String ind) throws Exception;
	void truncate_TempTable() throws Exception;

	List<String> Active_getTerrCodeList_with_div_id(String divId, String terrCodes);

	boolean checkFileExist_on_terr_master_upload_template_table(String originalFilename);

	List<String> getValid_State_id();

	List<DG_TERRMAST_UPLOAD_FOR_GCO> get_Uploaded_list(String divId, Boolean eight_char_level_code);
}
