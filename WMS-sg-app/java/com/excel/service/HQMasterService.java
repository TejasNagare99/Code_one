package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excel.bean.HQBean;
import com.excel.model.HQ_Master;
import com.excel.model.V_HQ_Master;

public interface HQMasterService{
	
	Boolean checkUniqueHQName(String hqName,String divId);

	Object saveHQDetail(HQBean hqBean, String empId, HttpServletRequest request) throws Exception;

	List<HQ_Master> getHqListForFieldStaffEntry(String divId);

	List<V_HQ_Master> getAllActiveHqMasterDetail();

	Object updateHQMaster(String hq_id, HQBean hqBean, String empId, HttpServletRequest request) throws Exception;

	List<V_HQ_Master> getHqTextParameterDetail(String name, String parameter, String text);

	List<HQ_Master> getHqDetailById(String hqId);

	Boolean checkUniqueHQNameForModify(String hqName, String divId, String hqId);

}
