package com.excel.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.excel.bean.FieldStaffBean;
import com.excel.bean.FinalApprovalLogBean;
import com.excel.bean.FstaffMas_PrxBean;
import com.excel.bean.ReportBean;
import com.excel.bean.articleScheme;
import com.excel.model.AllocationDetailReportModel;
import com.excel.model.Allocation_download;
import com.excel.model.Allocation_report_1;
import com.excel.model.Allocation_report_2;
import com.excel.model.Allocation_report_3;
import com.excel.model.Annual_sample_plan_view_report;
import com.excel.model.Annual_sample_plan_view_report_batchwise;
import com.excel.model.Annual_sample_plan_view_report_cons;
import com.excel.model.Annual_sample_plan_view_report_itemwise;
import com.excel.model.ArticleSchemeExceptionReport;
import com.excel.model.ArticleSchemeSummaryReportCfa;
import com.excel.model.Article_Stock_Statement;
import com.excel.model.Article_Stock_Statement_Itemwise;
import com.excel.model.BatchWiseStockLedgerModel;
import com.excel.model.BatchwiseStockStmtReport;
import com.excel.model.BatchwiseStockStmtReport_SG;
import com.excel.model.BlkChallansGeneratedLog;
import com.excel.model.Csv_File_Consolidated_List;
import com.excel.model.Csv_File_List;
import com.excel.model.Csv_File_List_Without_state_name;
import com.excel.model.Dg_veeva_emp;
import com.excel.model.Dg_veeva_pos;
import com.excel.model.Dg_veeva_pos_emp;
import com.excel.model.DispatchDetailGstReport;
import com.excel.model.DispatchDetailReport;
import com.excel.model.DispatchDetailReportRbmdm;
import com.excel.model.DispatchDetailReportRevised;
import com.excel.model.DispatchDetailReportRevisedMdmNo;
import com.excel.model.DispatchDetailReportRevised_SG;
import com.excel.model.DispatchDetailReportRevised_VET;
import com.excel.model.DispatchIntimationEmail;
import com.excel.model.DispatchSummaryDetailReport;
import com.excel.model.DispatchSummaryDetailReport_Revised;
import com.excel.model.Dispatch_Header;
import com.excel.model.Dispatch_alloc_monthwise_report;
import com.excel.model.DivMaster;
import com.excel.model.FieldMasterDownload;
import com.excel.model.FieldStaffCsvDownload;
import com.excel.model.Fieldstaff_After_mobile_No_Update_CheckList;
import com.excel.model.Grn_quarantine_prod_summary;
import com.excel.model.GrnquarantineProd_Detail;
import com.excel.model.IAADetailReport;
import com.excel.model.IAADetailReport_SG;
import com.excel.model.ItemWiseStockLedgerModel;
import com.excel.model.Location;
import com.excel.model.Lr_Expenses_Report;
import com.excel.model.LrcsvDownload;
import com.excel.model.LrcsvDownloadReport;
import com.excel.model.LrcsvDownloadReportRevised;
import com.excel.model.Lrcsv_RevisedDownload;
import com.excel.model.Lrcsv_RevisedDownload_SG;
import com.excel.model.Period;
import com.excel.model.ProductMasterAttrib;
import com.excel.model.Productwise_batchwise_locationwise_stock;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sap_Dispatch_Download;
import com.excel.model.Scheme_Summary;
import com.excel.model.SmpItem;
import com.excel.model.StkTrfDetailRepotModel;
import com.excel.model.StkTrfSummaryRepotModel;
import com.excel.model.StockAgeingGrnReportView;
import com.excel.model.StockAgeingReportView;
import com.excel.model.StockWithdrawalReport;
import com.excel.model.Stock_Reco_With_sfa;
import com.excel.model.Stock_Statement_Non_Sale;
import com.excel.model.Stock_Statement_Non_Sale_VET;
import com.excel.model.Stock_statement_pfz;
import com.excel.model.Sub_Company;
import com.excel.model.SummaryChallanReport;
import com.excel.model.SummaryChallanReportEInvoice;
import com.excel.model.Temp_fstaffmas_prx_error;
import com.excel.model.Ter_mst_hierarchy_report_bean;
import com.excel.model.ViewGRNDetail;
import com.excel.model.ViewGRNDetail_GST;
import com.excel.model.ViewGRNSummary;
import com.excel.model.ViewGRNSummary_GST;
import com.excel.model.ViewGRNSummary_GST_VET;
import com.excel.model.ViewGRNSummary_VET;
import com.excel.model.dispatch_register_report1;
import com.excel.model.dispatch_register_report1_with_parameters;
import com.excel.model.dispatch_register_report2;
import com.excel.model.dispatch_register_report2_with_parameters;
import com.excel.model.trd_scheme_mst_hdr;

public interface ReportService {
	List<DispatchSummaryDetailReport> callDisptachSumDetReport(ReportBean bean) throws Exception;
	List<DispatchDetailReport> callDisptachDetReport(ReportBean bean) throws Exception;
	List<SummaryChallanReport> callSummaryChallanReport(ReportBean bean) throws Exception;
	List<ViewGRNSummary_GST> getGRNSummaryReportData(ReportBean bean) throws Exception;
	List<ViewGRNDetail_GST> getGRNDetailReportData(ReportBean bean) throws Exception;
	List<ViewGRNSummary> getGRNSummaryData(ReportBean bean) throws Exception;
	List<ViewGRNDetail> getGRNDetailData(ReportBean bean) throws Exception;
	List<BatchwiseStockStmtReport> getBatchwiseStockStmtData(ReportBean bean, String stk_to_cfa_ind) throws Exception;
	List<StkTrfSummaryRepotModel> getStkTrfSummaryReportData(ReportBean bean) throws Exception;
	List<StkTrfDetailRepotModel> getStktrfDetailReportData(ReportBean bean) throws Exception;
	List<Stock_statement_pfz> getstockStatmentData(ReportBean bean) throws Exception;
	List<Stock_Statement_Non_Sale> getStockStatmentNonsalable(ReportBean bean) throws Exception;
	List<Stock_Reco_With_sfa> getStockReconcilation()throws Exception;
	List<ItemWiseStockLedgerModel> getItemWise_StockledgerData(ReportBean bean)throws Exception;
	List<BatchWiseStockLedgerModel> getBatchwise_StockledgerData(ReportBean bean)throws Exception;
	List<AllocationDetailReportModel> getAllocationDetailReportData(ReportBean bean)throws Exception;
	List<Allocation_report_3> getAllocation_Report_3_data(ReportBean bean)throws Exception;
	List<Period> getAllActiveFinYearForAllocationDetailReport();
	List<FieldMasterDownload> getFieldMasterDownloadData(ReportBean bean, String comp_code) throws Exception;
	List<ProductMasterAttrib> getProductMasterDownloadData(ReportBean bean) throws Exception;
	List<Sap_Dispatch_Download> getSapDispatchDownloadData(ReportBean bean, String startDate, String endDate)
			throws Exception;
	List<LrcsvDownload> getLrcsvdownload_data(String strdate, String enddate, String tbl_ind, String cfa,
		    String fsid, String stock_at_cfa_ind,String stk_at_cfa_option,String temp,String deptloc)throws Exception;
	List<LrcsvDownloadReport> get_Lrcsv_data(ReportBean bean,String tabl_ind,String cfa,String fsid,String fstype,String deptloc,String temp)throws Exception;
	List<Csv_File_List> get_allocation_downloadcsvdata(String id)throws Exception ;
	//Employee list for dispatch detailed report
	List<Object[]> get_employee_list(long div, int loc, String stdt, String endt,String fstaff_id, int cfaLoc, String desg_id,String fstaffId_o,
			String deleted_inv,String emp_id,String deptloc,String finyearflag,String year)throws Exception;
	List<Object[]> getDetailsByFstaffDesg(long div, int loc, String stdt, String endt, int cfaLoc, String desg_id,String deleted_inv,String emp_id,String deptloc) throws Exception ;
	Map<Integer, List<Object>> getPivotData(String div, String loc, String recLoc, String stdt, String endt);


	List<SmpItem> getActiveItemBy_divId(String divid)throws Exception;
	List<Productwise_batchwise_locationwise_stock> getproductwisebatchwiselocationwisereportdata(ReportBean bean)throws Exception;
	List<Annual_sample_plan_view_report> getDataforannualplanview(ReportBean bean)throws Exception;
	List<Object> getBrandsForTeam(String empId,List<String> teamId,String planType) throws Exception;
	List<SmpItem> getprodlistbybrands(List<String> teamId,String planType,List<String> brand_Id)throws Exception;
	List<Allocation_download> get_allocation_downloadexceldata(String pidiv_id, String pvuserid, String pfin_year,
			String pperiod_code) throws Exception;
	
	
	List<StockAgeingReportView> getstockageingreportData(ReportBean bean)throws Exception;
	List<StockAgeingReportView> getstockageingreportbyExpirydaysData(ReportBean bean)throws Exception;
	
	List<DivMaster> getdivforstockageing(String userid)throws Exception;
	List<Location> getlocationforstockageing()throws Exception;
	List<SG_d_parameters_details> getslabdetails(String para_type)throws Exception;
	List<SmpItem> getprodbydivforageing(String divid)throws Exception;
	
	List<Allocation_report_1> getdatafor_allocation_report1(ReportBean bean)throws Exception;
	List<Allocation_report_2> getdatafor_allocation_report2(ReportBean bean)throws Exception;
	List<Csv_File_Consolidated_List> get_Csv_File_Consolidated_List(String id) throws Exception;
	
	List<Annual_sample_plan_view_report_batchwise> getDataforannualplanviewbatchwise(ReportBean bean)throws Exception;
	List<Annual_sample_plan_view_report_itemwise> getDataforannualplanviewitemwise(ReportBean bean)throws Exception;
	List<Annual_sample_plan_view_report_cons> getDataforannualplanviewcon(Character asp_type,String asp_id)throws Exception;
	
	List<Dispatch_alloc_monthwise_report> getDispatch_alloc_monthwise_report_data(ReportBean bean);
	public List<SmpItem> getStockType(String empId) throws Exception ;
	List<Object> getBrandsForTeam(String empId, String divId) throws Exception;
	
	List<Grn_quarantine_prod_summary> getGren_quarantine_summary_data(ReportBean bean)throws Exception;
	List<GrnquarantineProd_Detail> getGRNQuarantineDetailData(ReportBean bean) throws Exception;
	
	List<Lr_Expenses_Report> getLrExpensesReportdata(ReportBean bean)throws Exception;
	List<Sub_Company> getCompanyName(String sub_comp_id)throws Exception;
	List<Dispatch_Header> gettransporterlist(String StartDate,String EndDate)throws Exception;
	
	List<LrcsvDownloadReportRevised> get_Lrcsv_revised_data(ReportBean bean,String tabl_ind,String cfa,String fsid,String fstype,
			String deptloc,String temp,String role,String user)throws Exception;
	List<Lrcsv_RevisedDownload> getLrcsvdownload_Revised_data(String strdate, String enddate, String tbl_ind, String cfa,
		    String fsid, String stock_at_cfa_ind,String stk_at_cfa_option,String temp,String deptloc,String role,String username,
		    List<String> loc,List<String> div,List<String> dest,String cfa_to_stockist_ind)throws Exception;
	
	List<DispatchSummaryDetailReport_Revised> callDisptachSumDetReportRevised(ReportBean bean) throws Exception;
	List<DispatchDetailReportRevised> callDisptachDetReportRevised(ReportBean bean) throws Exception;

	List<Lrcsv_RevisedDownload> getLrDeliveryRecieptcsvdata(String strdate, String enddate, String tbl_ind, String cfa,
		    String fsid, String stock_at_cfa_ind,String stk_at_cfa_option,String temp,String deptloc,String role,String username,
		    List<String> loc,List<String> div,List<String> dest,String cfa_to_stockist_ind)throws Exception;
	List<DispatchDetailReportRbmdm> callDisptachDetReportRmdm(ReportBean bean) throws Exception;
	
	List<StockWithdrawalReport> getStockwithdrawalReport(ReportBean bean)throws Exception;
	
	List<Temp_fstaffmas_prx_error>getpforcerxfstafferrordata(Date startdt,Date enddt)throws Exception;
	List<Dg_veeva_pos_emp>getDgveevaposempdata(Date startdt,Date enddt)throws Exception;
	List<Dg_veeva_pos>getDgveevaposdata(Date startdt,Date enddt)throws Exception;
	List<FstaffMas_PrxBean>getPrxFieldStaffStatusdata(Date startdt,Date enddt,Character Ind)throws Exception;
	List<Dg_veeva_emp> getpforcerxDgVeevaEmpData(Date startDate, Date endDate) throws Exception;
	List<FstaffMas_PrxBean>getPrxFieldTerr_data(Date startdt,Date enddt,Character Ind)throws Exception;
	List<Allocation_report_2> getdatafor_allocation_report2_current(ReportBean bean) throws Exception;
	List<Allocation_report_2> getdatafor_allocation_report2_previous(ReportBean bean) throws Exception;
	List<Allocation_report_1> getdatafor_allocation_report1_current(ReportBean bean) throws Exception;
	List<Allocation_report_1> getdatafor_allocation_report1_previous(ReportBean bean) throws Exception;
	List<IAADetailReport> getIAADetailReportData(String loc_id,String frm_iaa_id, String to_iaa_id, String[] strings) throws Exception;
	List<IAADetailReport> getIAADetailReportPreviousData(String loc_id,String startDate, String endDate, String[] strings) throws Exception;
	
	List<StockAgeingGrnReportView> getstockageinggrnreportData(ReportBean bean)throws Exception;
	List<dispatch_register_report1> getDispatchRegisterReport1(ReportBean bean) throws Exception;
	List<dispatch_register_report2> getDispatchRegisterReport2(ReportBean bean) throws Exception;
	List<DivMaster> getAllDivisionList() throws Exception;
	List<Period> getAllPeriodList() throws Exception;
	//List<Dispatch_Header> getcNameList(String divId,String startDate,String endDate) throws Exception;
	List<SmpItem> getProductList(ReportBean bean) throws Exception;
	List<SG_d_parameters_details> getProductTypeList() throws Exception;
	List<Dispatch_Header> getTeamList(String divId) throws Exception;
	List<Dispatch_Header> getDocNameList(ReportBean bean) throws Exception;
	List<ReportBean> getEmpNameList(ReportBean bean) throws Exception;
	List<ReportBean> getRegNameList(ReportBean bean) throws Exception;
	List<ReportBean> getDmNameList(ReportBean bean) throws Exception;
	List<ReportBean> getTerrCodeList(ReportBean bean) throws Exception;
	List<Dispatch_Header> getLrNumList(ReportBean bean) throws Exception;
	List<Dispatch_Header> getLrDateList(ReportBean bean) throws Exception;
	List<Dispatch_Header> getChallanDateList(ReportBean bean) throws Exception;
	List<Dispatch_Header> getChallanNumList(ReportBean bean) throws Exception;
	List<dispatch_register_report1_with_parameters> getDispatchRegisterRevisedReport1(ReportBean bean) throws Exception;
	List<Dispatch_Header> getcNameList(ReportBean bean)  throws Exception;
	List<dispatch_register_report2_with_parameters> getDispatchRegisterRevisedReport2DoctorSupply(ReportBean bean) throws Exception;
	
	List<DispatchDetailGstReport> callDisptachDetailGstReport(ReportBean bean) throws Exception;

	List<SummaryChallanReportEInvoice> callSummaryChallanReportEInv(ReportBean bean) throws Exception;
	List<BlkChallansGeneratedLog> getBlkChallasnsGeneratedLogReport(String finyr,String periodcd,Long reqId)throws Exception;
	List<DispatchIntimationEmail> getDispatchIntimationEmail(String stdate, String endate)throws Exception;//report by prathmesh

	List<Object> getBrandsForTeamStmtReport(String empId, String divId) throws Exception;
	String getSubCompanyNameByLocId(String loc_id) throws Exception;
	public List<FinalApprovalLogBean> getFinalApprovalLog() throws Exception;
	List<AllocationDetailReportModel> getAllocationDetailReportDataDiv(ReportBean bean)throws Exception;
	List<Article_Stock_Statement> getArticleInventoryReport(ReportBean bean) throws Exception;
	List<Scheme_Summary> getSchemeSummary(List<String> schemeIds, String finyear, String finyear_flag) throws Exception;
	List<ArticleSchemeExceptionReport> articleExceptionReport(ReportBean bean) throws Exception;
	List<articleScheme> getarticleScheme();
	List<SmpItem> getSmpItemList(Long locId) throws Exception;

	List<trd_scheme_mst_hdr> getExtendedValidities(List<String> schemeIds) throws Exception;
	
	List<ArticleSchemeSummaryReportCfa> articleSchemeSummaryReportCfa(ReportBean bean) throws Exception;
	List<Article_Stock_Statement_Itemwise> getArticleInventoryItemwiseReport(ReportBean bean) throws Exception;
	List<Lrcsv_RevisedDownload_SG> getLrcsvdownload_Revised_data_sg(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String string, String deptloc,
			String role, String username, List<String> loc, List<String> div, List<String> dest, String cfa_to_stk_ind) throws Exception;
	
	
	List<Lrcsv_RevisedDownload_SG> getLrDeliveryRecieptcsvdata_SG(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String string, String deptloc,
			String role, String username, List<String> loc, List<String> div, List<String> dest, String cfa_to_stk_ind) throws Exception;
	List<IAADetailReport_SG> getIAADetailReportData_for_sg(String slId, String startDate, String endDate) throws Exception ;
	List<IAADetailReport_SG> getIAADetailReportPreviousData_for_sg(String slId, String startDate, String endDate);
	List<DispatchDetailReportRevised_SG> callDisptachDetReportRevised_SG(ReportBean bean) throws Exception;
	List<BatchwiseStockStmtReport_SG> getBatchwiseStockStmtData_SG(ReportBean bean, String stk_to_cfa_ind);
	
	List<DispatchDetailReportRevisedMdmNo> callDisptachDetReportRevisedMdmNo(ReportBean bean) throws Exception;
	Boolean check_StateNameRequired_or_not();
	List<Csv_File_List_Without_state_name> get_Csv_File_Consolidated_List_Without_State_Name(String string);
	List<DispatchDetailReportRevised_VET> callDisptachDetReportRevised_VET(ReportBean bean);
	List<ViewGRNSummary_GST_VET> getGRNSummaryReportData_VET(@Valid ReportBean bean);
	List<ViewGRNSummary_VET> getGRNSummaryData_VET(ReportBean bean) throws Exception;
	List<Stock_Statement_Non_Sale_VET> getStockStatmentNonsalable_VET(ReportBean bean);
	List<Scheme_Summary> getSchemeSummary_drill_down_list(List<String> schemeIds, String finyear, String finyear_flag) throws Exception;
	List<Ter_mst_hierarchy_report_bean> download_ter_hyrarchy_Report(String div_id) throws Exception;
	List<FieldStaffCsvDownload> getFstaffDownloadCsvData(String divId)throws Exception;
	List<String> getCsvFileList()throws Exception;
	List<Fieldstaff_After_mobile_No_Update_CheckList> getUpdatedFstaffMobileNo(String csvFileName) throws Exception;
	List<FieldStaffBean> getFieldstaffMobileOdsdCount(Date date) throws Exception;


}
