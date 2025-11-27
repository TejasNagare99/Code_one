package com.excel.service;

import java.util.Map;

import com.excel.bean.AllocationUploadBean;
import com.excel.bean.FieldStaffAllocationUploadBean;

public interface FieldStaffAllocationUploadService {
	Map<String, Object> saveuploadalFeldstaffAllocationXlsx(FieldStaffAllocationUploadBean bean)throws Exception;
}
