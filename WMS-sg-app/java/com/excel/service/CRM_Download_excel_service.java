package com.excel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.excel.model.TdsApplicableProduct;
import com.excel.model.TdsApplicableStatementReport;

@Service
public interface CRM_Download_excel_service {

	 Map<String, Object> generateTdsSummaryReport(List<TdsApplicableStatementReport> list,HttpSession session) throws Exception;
	String generateTdsDetailReport(List<TdsApplicableProduct> list,TdsApplicableStatementReport bean,HttpSession session) throws Exception;
	
}
