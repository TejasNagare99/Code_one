package com.excel.repository;

import java.util.List;

import com.excel.model.Menu;

public interface MenuRepository {

	List<Menu> getRoles(String companyCode) throws Exception;
	
}
