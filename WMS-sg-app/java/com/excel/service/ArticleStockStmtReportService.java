package com.excel.service;

import java.util.Date;
import java.util.List;

import com.excel.model.Article_Stock_Statement;
import com.excel.model.Article_Stock_Statement_Itemwise;
import com.excel.model.SmpItem;

public interface ArticleStockStmtReportService {

	
	String generateArticleStockStmtReport(List<SmpItem> lst, List<Article_Stock_Statement> list, List<Article_Stock_Statement_Itemwise> articleStockStatementItemwiseList, String companyname,String companycode,String empId,Date fin) throws Exception;
}
