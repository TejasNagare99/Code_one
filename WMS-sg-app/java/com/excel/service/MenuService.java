package com.excel.service;

import java.util.List;

import com.excel.model.Menu;

public interface MenuService {
	
	List<Menu> getRoles(String companyCode) throws Exception;
	
}
