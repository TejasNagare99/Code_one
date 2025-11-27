package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Menu;
import com.excel.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired private MenuRepository menuRepository;
	
	@Override
	public List<Menu> getRoles(String companyCode) throws Exception {
		return menuRepository.getRoles(companyCode);
	}

}
