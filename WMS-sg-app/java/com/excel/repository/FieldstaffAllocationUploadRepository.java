package com.excel.repository;

import com.excel.model.FieldStaff;

public interface FieldstaffAllocationUploadRepository {

	FieldStaff getFieldStaffByMapCode2(String mapcode2,Long divId)throws Exception;
	
	
}
