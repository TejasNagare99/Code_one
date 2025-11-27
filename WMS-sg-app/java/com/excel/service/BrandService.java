package com.excel.service;

import java.util.List;
import java.util.Map;

import com.excel.bean.UserBean;

public interface BrandService {
	
	Map<String, Object> saveBrand(String name , String emp_Id) throws Exception;
	List<Object> getBrandList(String prefix) throws Exception;

}
