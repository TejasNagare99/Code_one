package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.StkTrfDetailRepotModel;
import com.excel.model.StkTrfSummaryRepotModel;
import com.excel.model.ViewGRNDetail;
import com.excel.model.ViewGRNSummary;

public interface StkTrfReportService {

	String generateStkTrfSummaryReport(List<StkTrfSummaryRepotModel> list,ReportBean bean, String companyname,String empId) throws Exception;
	String generateStkTrfDetailReport(List<StkTrfDetailRepotModel> list,ReportBean bean,String companyname,String empId) throws Exception;

}
