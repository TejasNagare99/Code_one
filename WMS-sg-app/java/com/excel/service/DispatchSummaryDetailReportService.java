package com.excel.service;

import java.io.IOException;
import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.BlkChallansGeneratedLog;
import com.excel.model.DispatchDetailGstReport;
import com.excel.model.DispatchDetailReport;
import com.excel.model.DispatchDetailReportRbmdm;
import com.excel.model.DispatchDetailReportRevised;
import com.excel.model.DispatchDetailReportRevisedMdmNo;
import com.excel.model.DispatchDetailReportRevised_SG;
import com.excel.model.DispatchDetailReportRevised_VET;
import com.excel.model.DispatchSummaryDetailReport;
import com.excel.model.DispatchSummaryDetailReport_Revised;
import com.excel.model.SummaryChallanReport;
import com.excel.model.SummaryChallanReportEInvoice;
import com.excel.model.Ter_mst_hierarchy_report_bean;


public interface DispatchSummaryDetailReportService {
	
	String generateDispatchSumDetReport(List<DispatchSummaryDetailReport> list,ReportBean bean, String companyname) throws Exception;
	String generateDispatchDetReport(List<DispatchDetailReport> list,ReportBean bean,String companyname) throws Exception;
	String generateSummaryChallanReport(List<SummaryChallanReport> list,ReportBean bean,String companyname,String empId) throws Exception;

	String generateDispatchSumDetReportRevised(List<DispatchSummaryDetailReport_Revised> list,ReportBean bean, String companyname,String empId) throws Exception;
	String generateDispatchDetReportRevised(List<DispatchDetailReportRevised> list,ReportBean bean,String companyname,String empId, String stk_ind) throws Exception;

	String generateDispatchDetReportRbmdm(List<DispatchDetailReportRbmdm> list,ReportBean bean,String companyname) throws Exception;

	String generateDispatchDetailGstReport(List<DispatchDetailGstReport> list,ReportBean bean,String companyname,String empId) throws Exception;
	String generateSummaryChallanReportEInv(List<SummaryChallanReportEInvoice> list,ReportBean bean,String companyname) throws Exception;
	String getBlkChallasnsGeneratedLogFilename(List<BlkChallansGeneratedLog> list)throws Exception;
	String generateDispatchDetReportRevised_SG(List<DispatchDetailReportRevised_SG> list, ReportBean bean,
			String companyname, String empIdd) throws Exception;
	String generateDispatchDetReportRevisedMdmNo(List<DispatchDetailReportRevisedMdmNo> list,ReportBean bean,String companyname,String empId) throws Exception;
	String generateDispatchDetReportRevised_VET(List<DispatchDetailReportRevised_VET> list, ReportBean bean,
			String companyname, String empIdd) throws IOException;
	String genarate_ter_hyrarchy_Report(List<Ter_mst_hierarchy_report_bean> list, String companyname);

}
