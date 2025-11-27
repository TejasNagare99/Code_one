package com.excel.repository;

import java.util.List;

import com.excel.model.Alloc_Remark;
import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_Temp_Header;

public interface AllocTempRepository {

	Long getMaxCycle(String division_id, String period_code, String company, String fin_year) throws Exception;

	List<Alloc_Temp> getDataByDiv(Long division_id, String period_code, String upload_status, String ucycle,Long alloc_temp_hd_id);

	void deleteRecords(Long division, String period, String upload_status, String ucycle, long alloc_temp_hd_id) throws Exception;

	Alloc_Temp_Header getObjectAllocTempHeader(Long headerId) throws Exception;

	Alloc_Remark getAlloc_Remark(Long AllocTempId)throws Exception;
}
