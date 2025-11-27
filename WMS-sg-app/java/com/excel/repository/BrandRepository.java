package com.excel.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository {
	
	String saveBrand(String brandName, String emp_Id) throws Exception;
	List<Object> getBrandList(String prefix) throws Exception;

}
