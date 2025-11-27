package com.excel.service;

import java.util.List;

import com.excel.bean.articleScheme;
import com.excel.model.Scheme_Summary;

public interface SchemeSummaryService {
	
	String generateSchemeSummaryReport (List<Scheme_Summary> listSummary,String companyname) throws Exception;

	

	String getarticleScheme(List<articleScheme> listSummary); 


}
