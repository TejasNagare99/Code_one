package com.excel.service;

import java.util.List;

import com.excel.bean.UploadMsrAllocBean;
import com.excel.model.Alloc_gen_dt;

public interface AllocTempService  {


	void saveAlloc_Gen_data(List<Alloc_gen_dt> alloc_gen_dts, char alloc_mode, Long upload_cycle, String ipAddress,
			int level, String userid) throws Exception;


	void updateApprovalStatus(Long alloc_temp_hd_id, String status) throws Exception;
	
	void updateApprovalStatusWithRemark(Long alloc_temp_hd_id, String status,String remark) throws Exception;

	void saveUploadData(List<UploadMsrAllocBean> msr_Allocs, String includestock, String team_code) throws Exception;

	void saveAllocationRemark(String Remark,Long alloctemp_id)throws Exception;
}
