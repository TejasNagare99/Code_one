package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excel.bean.DivMapBean;

public interface DivMapService {
	
	void saveDivisionMapping(DivMapBean bean, HttpServletRequest request) throws Exception;
	
	List<Long> getDivMapByFsDivision(Long fsDivId) throws Exception;
}
