package com.excel.repository;

import com.excel.model.Alloc_Detail;

public interface AllocDetailRepository {

	Long getCycle(Long division_id, String period_code, String company, String fin_year) throws Exception;

	Long getExistCount(Long division_id, String period_code, String company, String fin_year);

	Alloc_Detail getAlloc_DetailObj(Long division_id, String period_code, String company, String fin_year,
			Long fstaff_id, Long prod_id, String alloc_type);

}
