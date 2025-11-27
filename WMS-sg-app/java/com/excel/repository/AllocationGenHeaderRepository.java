package com.excel.repository;

import com.excel.model.Alloc_gen_hd;

public interface AllocationGenHeaderRepository {

	Alloc_gen_hd getObjectById(Long headerId) throws Exception;
	

}
