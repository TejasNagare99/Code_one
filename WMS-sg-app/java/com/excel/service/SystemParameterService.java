package com.excel.service;

import java.util.List;

import com.excel.model.Am_m_System_Parameter;

public interface SystemParameterService {
	
	public List<Am_m_System_Parameter> getSpValueBySpName(String spName)throws Exception;
}
