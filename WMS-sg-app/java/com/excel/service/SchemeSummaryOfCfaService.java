package com.excel.service;

import java.io.IOException;
import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.ArticleSchemeSummaryReportCfa;

public interface SchemeSummaryOfCfaService {
	//String generateSchemeSummaryCfaLocationWise(List<ArticleSchemeSummaryReportCfa> list) throws IOException;

	String generateSchemeSummaryCfaLocationWise(List<ArticleSchemeSummaryReportCfa> list, String companyname, ReportBean bean)
			throws IOException;
}
