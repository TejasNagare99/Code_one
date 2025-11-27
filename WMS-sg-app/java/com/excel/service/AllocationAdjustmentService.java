package com.excel.service;

import java.util.List;
import java.util.Map;

import com.excel.bean.AllocationAdjustmentBean;
import com.excel.bean.Parameter;
import com.excel.model.DivMaster;
import com.excel.model.SmpItem;

public interface AllocationAdjustmentService {
	
	List<DivMaster> getDivForAllocAdj(String allocid,String compCode,String empId);
	List<SmpItem> getProdList(String allocid, String divId);
	List<Parameter> getFSForAllocAdj(String allocid, String divId, String prodId, String alMode, String compcode);
	List<Parameter> getAllocList(String compcode);
	Map<String,Object> saveAdjustment(AllocationAdjustmentBean bean) throws Exception;

}
