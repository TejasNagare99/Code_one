package com.excel.repository;

import java.util.List;
import java.util.Set;

import com.excel.model.AllocReqDet;
import com.excel.model.AllocReqHeader;
import com.excel.model.Alloc_Header;
import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_gen_dt;
import com.excel.model.Alloc_gen_hd;

public interface AllocHeaderRepository {
	
	Long setMainDetail(Set<Long> set, List<Alloc_gen_dt> detailList, String stk_cfa_ind, Alloc_gen_hd header)throws Exception;

	void setAllocDetailsForSpecialAlloc(AllocReqHeader header, List<AllocReqDet> alloc_req_detail, Long fsId,
			Long smpId) throws Exception;

	Long setDetailsForRulesBasedAllocation(Set<Long> set, List<Alloc_Temp> list, String app_remark, String stk_cfa_ind)
			throws Exception;

	Alloc_Header getAlloc_HeaderObj(String period_code, String company, String fin_year, Long fstaff_id,
			String alloc_type);

}
