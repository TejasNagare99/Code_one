package com.excel.service;

import java.util.List;

import javax.validation.Valid;

import com.excel.bean.ReportBean;
import com.excel.model.ViewGRNDetail;
import com.excel.model.ViewGRNDetail_GST;
import com.excel.model.ViewGRNSummary;
import com.excel.model.ViewGRNSummary_GST;
import com.excel.model.ViewGRNSummary_GST_VET;
import com.excel.model.ViewGRNSummary_VET;

public interface GRNReportService {
	
	String generateGRNSummaryReport(List<ViewGRNSummary_GST> list,ReportBean bean, String companyname,String empId) throws Exception;
	String generateGRNDetailReport(List<ViewGRNDetail_GST> list,ReportBean bean,String companyname,String empId,boolean isCfaToStockist) throws Exception;
	
	// for without GST report
	String generateGRNSummary(List<ViewGRNSummary> list,ReportBean bean,String cfa_to_stk,String companyCode,String companyname,String empId)throws Exception;
	String generateGRNDetail(List<ViewGRNDetail> list, ReportBean bean, String compcode,String companyname,String empId) throws Exception;
	String generateGRNSummaryReport_VET(List<ViewGRNSummary_GST_VET> grnSummary, @Valid ReportBean bean,
			String companyname, String empId) throws Exception;
	String generateGRNSummary_VET(List<ViewGRNSummary_VET> grnSummary, ReportBean bean, String cfa_to_stk,
			String companyCode, String companyname, String empId) throws Exception;
	String generateGRNDetailReport_SG(List<ViewGRNDetail_GST> grnDetail, @Valid ReportBean bean, String companyname,
			String empId, boolean b) throws Exception;
	String generateGRNSummaryReport_VETO(List<ViewGRNSummary_GST> grnSummary, @Valid ReportBean bean,
			String companyname, String empId) throws Exception;

}
