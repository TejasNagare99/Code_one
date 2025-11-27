package com.excel.repository;

import java.util.List;

import com.excel.model.Am_m_System_Parameter;

public interface SystemParameterRepository {

	List<Am_m_System_Parameter> getSpValueBySpName(String spName) throws Exception;

}
