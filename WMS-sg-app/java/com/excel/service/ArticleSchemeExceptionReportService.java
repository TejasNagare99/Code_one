package com.excel.service;

import java.util.List;

import com.excel.model.ArticleSchemeExceptionReport;

public interface ArticleSchemeExceptionReportService {

	String generateArticleExceptionSchemeReport (List<ArticleSchemeExceptionReport> list, String companyname, String fromDate, String toDate, String is_correction) throws Exception; 
}
