package com.excel.repository;

import java.util.List;

import com.excel.model.HQ_Master;
import com.excel.model.TerrMaster;
import com.excel.model.V_HQ_Master;

public interface HQMasterRepository {

	Boolean checkUniqueHQName(String hqName, String divId);
	List<HQ_Master> getHqListForFieldStaffEntry(String divId);
	List<V_HQ_Master> getAllActiveHqMasterDetail();
	HQ_Master getByObjectId(Long hq_id) throws Exception;
	List<V_HQ_Master> getHqTextParameterDetail(String name, String parameter, String text);
	List<HQ_Master> getHqDetailById(String hqId);
	Boolean checkUniqueHQNameForModify(String hqName, String divId, String hqId);
	
}
