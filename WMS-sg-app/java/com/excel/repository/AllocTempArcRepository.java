package com.excel.repository;

public interface AllocTempArcRepository {

	boolean isFileExist(String file) throws Exception;

	void saveArchieve(String isapproved, Long division, String period, String upload_status, String ucycle,
			Long alloc_temp_hd_id) throws Exception;

	void saveArchieveArchive(String isapproved, Long division, String period, String upload_status, String ucycle,
			Long alloc_temp_hd_id,String empId,String ipAdd) throws Exception;
	boolean checkSGPRMDET_TEXT1() throws Exception;
}
