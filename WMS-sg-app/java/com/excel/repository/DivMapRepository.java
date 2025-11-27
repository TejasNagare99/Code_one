package com.excel.repository;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excel.bean.DivMapBean;

public interface DivMapRepository {
	
	List<Long> getDivMapByFsDivision(Long fsDivId) throws Exception;
	
	void updateCoreIndToN(DivMapBean bean, HttpServletRequest request, String coreInd) throws Exception;
	
	void updateCoreIndToA(DivMapBean bean, Long prodDivId, HttpServletRequest request, String coreInd) throws Exception;
	
	String coreInd(Long fsDivId, Long prodDivId) throws Exception;
}
