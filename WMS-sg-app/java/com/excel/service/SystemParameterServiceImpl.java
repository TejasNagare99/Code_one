package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Am_m_System_Parameter;
import com.excel.repository.SystemParameterRepository;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {
	
	@Autowired private SystemParameterRepository systemParameterRepository;
	
	@Override
	public List<Am_m_System_Parameter> getSpValueBySpName(String spName) throws Exception {
		return systemParameterRepository.getSpValueBySpName(spName);
	}

}
