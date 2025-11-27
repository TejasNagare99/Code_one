package com.excel.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.excel.repository.ReportRepository;
import com.excel.utility.MedicoConstants;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private LocationService locationService;

	@Override
	public List<DispatchSummaryDetailReport> callDisptachSumDetReport(ReportBean bean) throws Exception {
		return reportRepository.callDisptachSumDetReport(bean);
	}

	@Override
	public List<DispatchDetailReport> callDisptachDetReport(ReportBean bean) throws Exception {
		return reportRepository.callDisptachDetReport(bean);
	}

	@Override
	public List<SummaryChallanReport> callSummaryChallanReport(ReportBean bean) throws Exception {
		return reportRepository.callSummaryChallanReport(bean);
	}

	@Override
	public List<ViewGRNSummary_GST> getGRNSummaryReportData(ReportBean bean) throws Exception {
		return reportRepository.getGRNSummaryReportData(bean);
	}

	@Override
	public List<ViewGRNDetail_GST> getGRNDetailReportData(ReportBean bean) throws Exception {
		return reportRepository.getGRNDetailReportData(bean);
	}

	@Override
	public List<ViewGRNSummary> getGRNSummaryData(ReportBean bean) throws Exception {
		return reportRepository.getGRNSummaryData(bean);
	}

	@Override
	public List<ViewGRNDetail> getGRNDetailData(ReportBean bean) throws Exception {
		return reportRepository.getGRNDetailData(bean);
	}

	@Override
	public List<BatchwiseStockStmtReport> getBatchwiseStockStmtData(ReportBean bean, String stk_ind) throws Exception {
		return reportRepository.getBatchwiseStockStmtData(bean, stk_ind);
	}

	@Override
	public List<StkTrfSummaryRepotModel> getStkTrfSummaryReportData(ReportBean bean) throws Exception {
		return reportRepository.getStkTrfSummaryReportData(bean);
	}

	@Override
	public List<StkTrfDetailRepotModel> getStktrfDetailReportData(ReportBean bean) throws Exception {
		return reportRepository.getStktrfDetailReportData(bean);
	}

	@Override
	public List<Stock_statement_pfz> getstockStatmentData(ReportBean bean) throws Exception {
		return reportRepository.getstockStatmentData(bean);
	}

	@Override
	public List<Stock_Statement_Non_Sale> getStockStatmentNonsalable(ReportBean bean) throws Exception {
		return reportRepository.getDataStkNonsalable(bean);
	}

	@Override
	public List<Stock_Reco_With_sfa> getStockReconcilation() throws Exception {
		return reportRepository.getStockrecowithsfa();
	}

	@Override
	public List<ItemWiseStockLedgerModel> getItemWise_StockledgerData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getItemWise_StockledgerData(bean);
	}

	@Override
	public List<BatchWiseStockLedgerModel> getBatchwise_StockledgerData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getBatchwise_StockledgerData(bean);
	}

	@Override
	public List<AllocationDetailReportModel> getAllocationDetailReportData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getAllocationDetailReportData(bean);
	}

	// Ang Allocation Detail Report (1-5-2020)
	@Override
	public List<Period> getAllActiveFinYearForAllocationDetailReport() {
		return this.reportRepository.getAllActiveFinYearForAllocationDetailReport();
	}

	@Override
	public List<Allocation_report_3> getAllocation_Report_3_data(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getAllocation_Report_3_data(bean);
	}

	// Fieldstaff download (8-5-2020)
	@Override
	public List<FieldMasterDownload> getFieldMasterDownloadData(ReportBean bean, String comp_code) throws Exception {
		return reportRepository.getFieldMasterDownloadData(bean, comp_code);
	}

	// Product Download
	@Override
	public List<ProductMasterAttrib> getProductMasterDownloadData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getProductMasterDownloadData(bean);
	}

	// SAP Dispatch Download
	@Override
	public List<Sap_Dispatch_Download> getSapDispatchDownloadData(ReportBean bean, String startDate, String endDate)
			throws Exception {
		return reportRepository.getSapDispatchDownloadData(bean, startDate, endDate);
	}

	@Override
	public List<LrcsvDownload> getLrcsvdownload_data(String strdate, String enddate, String tbl_ind, String cfa,
			String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getLrcsvdownload_data(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
				stk_at_cfa_option, temp, deptloc);
	}

	@Override
	public List<LrcsvDownloadReport> get_Lrcsv_data(ReportBean bean, String tabl_ind, String cfa, String fsid,
			String fstype, String deptloc, String temp) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.get_Lrcsv_data(bean, tabl_ind, cfa, fsid, fstype, deptloc, temp);
	}

	@Override
	public List<Csv_File_List> get_allocation_downloadcsvdata(String id) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.get_allocation_downloadcsvdata(id);
	}

	@Override
	public List<Csv_File_Consolidated_List> get_Csv_File_Consolidated_List(String id) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.get_Csv_File_Consolidated_List(id);
	}

	@Override
	public List<Object[]> get_employee_list(long div, int loc, String stdt, String endt, String fstaff_id, int cfaLoc,
			String desg_id, String fstaffId_o, String deleted_inv, String emp_id, String deptloc, String finyearflag,
			String year) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.get_employee_list(div, loc, stdt, endt, fstaff_id, cfaLoc, desg_id, fstaffId_o,
				deleted_inv, emp_id, deptloc, finyearflag, year);
	}

	@Override
	public List<Object[]> getDetailsByFstaffDesg(long div, int loc, String stdt, String endt, int cfaLoc,
			String desg_id, String deleted_inv, String emp_id, String deptloc) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDetailsByFstaffDesg(div, loc, stdt, endt, cfaLoc, desg_id, deleted_inv, emp_id,
				deptloc);
	}

	// Pivot PAcking Slip(23May2020)
	@Override
	public Map<Integer, List<Object>> getPivotData(String div, String loc, String recLoc, String stdt, String endt) {
		// TODO Auto-generated method stub
		return reportRepository.getPivotData(div, loc, recLoc, stdt, endt);
	}

	@Override
	public List<SmpItem> getActiveItemBy_divId(String divid) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getActiveItemBy_divId(divid);
	}

	@Override
	public List<Productwise_batchwise_locationwise_stock> getproductwisebatchwiselocationwisereportdata(ReportBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getproductwisebatchwiselocationwisereportdata(bean);
	}

	@Override
	public List<Annual_sample_plan_view_report> getDataforannualplanview(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDataforannualplanview(bean);
	}

	@Override
	public List<Object> getBrandsForTeam(String empId, List<String> teamId, String planType) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getBrandsForTeam(empId, teamId, planType);
	}

	@Override
	public List<SmpItem> getprodlistbybrands(List<String> teamId, String planType, List<String> brand_Id)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getprodlistbybrands(teamId, planType, brand_Id);
	}

	@Override
	public List<Allocation_download> get_allocation_downloadexceldata(String pidiv_id, String pvuserid,
			String pfin_year, String pperiod_code) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.get_allocation_downloadexceldata(pidiv_id, pvuserid, pfin_year, pperiod_code);
	}

	@Override
	public List<StockAgeingReportView> getstockageingreportData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getstockageingreportData(bean);
	}

	@Override
	public List<StockAgeingReportView> getstockageingreportbyExpirydaysData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getstockageingreportbyExpirydaysData(bean);
	}

	@Override
	public List<DivMaster> getdivforstockageing(String userid) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getdivforstockageing(userid);
	}

	@Override
	public List<Location> getlocationforstockageing() throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getlocationforstockageing();
	}

	@Override
	public List<SG_d_parameters_details> getslabdetails(String para_type) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getslabdetails(para_type);
	}

	@Override
	public List<SmpItem> getprodbydivforageing(String divid) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getprodbydivforageing(divid);
	}

	@Override
	public List<Allocation_report_1> getdatafor_allocation_report1(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getdatafor_allocation_report1(bean);
	}

	@Override
	public List<Allocation_report_2> getdatafor_allocation_report2(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getdatafor_allocation_report2(bean);
	}

	@Override
	public List<Annual_sample_plan_view_report_batchwise> getDataforannualplanviewbatchwise(ReportBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDataforannualplanviewbatchwise(bean);
	}

	@Override
	public List<Annual_sample_plan_view_report_itemwise> getDataforannualplanviewitemwise(ReportBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDataforannualplanviewitemwise(bean);
	}

	@Override
	public List<Annual_sample_plan_view_report_cons> getDataforannualplanviewcon(Character asp_type, String asp_id)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDataforannualplanviewcon(asp_type, asp_id);

	}

	@Override
	public List<Dispatch_alloc_monthwise_report> getDispatch_alloc_monthwise_report_data(ReportBean bean) {
		// TODO Auto-generated method stub
		return reportRepository.getDispatch_alloc_monthwise_report_data(bean);
	}

	@Override
	public List<SmpItem> getStockType(String empId) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getStockType(empId);
	}

	@Override
	public List<Object> getBrandsForTeam(String empId, String divId) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getBrandsForTeam(empId, divId);
	}

	@Override
	public List<Grn_quarantine_prod_summary> getGren_quarantine_summary_data(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getGren_quarantine_summary_data(bean);
	}

	@Override
	public List<GrnquarantineProd_Detail> getGRNQuarantineDetailData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getGRNQuarantineDetailData(bean);
	}

	@Override
	public List<Lr_Expenses_Report> getLrExpensesReportdata(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getLrExpensesReportdata(bean);
	}

	@Override
	public List<Sub_Company> getCompanyName(String sub_comp_id) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getCompanyName(sub_comp_id);
	}

	@Override
	public List<Dispatch_Header> gettransporterlist(String StartDate, String EndDate) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.gettransporterlist(StartDate, EndDate);
	}

	@Override
	public List<LrcsvDownloadReportRevised> get_Lrcsv_revised_data(ReportBean bean, String tabl_ind, String cfa,
			String fsid, String fstype, String deptloc, String temp, String role, String user) throws Exception {
		List<LrcsvDownloadReportRevised> list = null;
		List<LrcsvDownloadReportRevised> filteredlist = new ArrayList<>();

		System.out.println("role :: " + role);
		System.out.println("user :: " + user);

		list = reportRepository.get_Lrcsv_revised_data(bean, tabl_ind, cfa, fsid, fstype, deptloc, temp);
		for (int i = 0; i < list.size(); i++) {
			if (MedicoConstants.ROLE_COUR.equals(role.trim())) {
				if (list.get(i).getMst_transporter().trim().equalsIgnoreCase(user.trim())
						|| list.get(i).getDtl_transporter().trim().equalsIgnoreCase(user.trim())) {

					filteredlist.add(list.get(i));
					System.out.println("in if");
				}
			} else {
				if (bean.getTranptr().trim().equalsIgnoreCase(list.get(i).getMst_transporter().trim())
						|| bean.getTranptr().trim().equalsIgnoreCase(list.get(i).getDtl_transporter().trim())) {
					filteredlist.add(list.get(i));
				}

				if (bean.getTranptr().trim().equalsIgnoreCase("All")) {
					filteredlist.add(list.get(i));
				}
			}

		}

		System.out.println("filteredlist :: " + filteredlist.size());

		return filteredlist;
	}

	@Override
	public List<Lrcsv_RevisedDownload> getLrcsvdownload_Revised_data(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			String role, String username, List<String> loc, List<String> div, List<String> dest,
			String cfa_to_stockist_ind) throws Exception {
		
		
		// TODO Auto-generated method stub
		List<Lrcsv_RevisedDownload> list = null;
		List<Lrcsv_RevisedDownload> filteredlist = new ArrayList<>();
		list = reportRepository.getLrcsvdownload_Revised_data(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
				stk_at_cfa_option, temp, deptloc, loc, div, dest, cfa_to_stockist_ind);

		for (int i = 0; i < list.size(); i++) {

			if (MedicoConstants.ROLE_COUR.equals(role.trim())) {
				if (list.get(i).getMst_transporter().trim().equalsIgnoreCase(username.trim())
						|| list.get(i).getDtl_transporter().trim().equalsIgnoreCase(username.trim())) {
					filteredlist.add(list.get(i));
				}
			} else {
				filteredlist.add(list.get(i));
			}
		}
		return filteredlist;
	}

	
	@Override
	public List<DispatchSummaryDetailReport_Revised> callDisptachSumDetReportRevised(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.callDisptachSumDetReportRevised(bean);
	}

	@Override
	public List<DispatchDetailReportRevised> callDisptachDetReportRevised(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.callDisptachDetReportRevised(bean);
	}

	@Override
	public List<Lrcsv_RevisedDownload> getLrDeliveryRecieptcsvdata(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			String role, String username, List<String> loc, List<String> div, List<String> dest,
			String cfa_to_stockist_ind) throws Exception {
		// TODO Auto-generated method stub
		List<Lrcsv_RevisedDownload> list = null;
		List<Lrcsv_RevisedDownload> filteredlist = new ArrayList<>();
		list = reportRepository.getLrcsvdownload_Revised_data(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
				stk_at_cfa_option, temp, deptloc, loc, div, dest, cfa_to_stockist_ind);

		for (int i = 0; i < list.size(); i++) {

			if (MedicoConstants.ROLE_COUR.equals(role.trim())) {
				if (list.get(i).getMst_transporter().trim().equalsIgnoreCase(username.trim())
						|| list.get(i).getDtl_transporter().trim().equalsIgnoreCase(username.trim())) {

//				if((list.get(i).getMst_recd_by()==null 
//					|| list.get(i).getMst_actual_delivery_date()==null || list.get(i).getDtl_actual_delivery_date()==null||
//					list.get(i).getDtl_recd_by()==null) &&
//					(list.get(i).getMst_lr_dt()!=null || list.get(i).getDtl_lr_dt()!=null ||
//					list.get(i).getMst_lr_no()!=null && !list.get(i).getMst_lr_no().equals("")||
//					list.get(i).getDtl_lr_no()!=null && !list.get(i).getDtl_lr_no().equals(""))) {

					if ((list.get(i).getDtl_actual_delivery_date() == null || list.get(i).getDtl_recd_by() == null)
							&& (list.get(i).getDtl_lr_dt() != null && list.get(i).getDtl_lr_no() != null
									&& !list.get(i).getDtl_lr_no().equals(""))) {

						if (list.get(i).getMst_transporter().trim().equalsIgnoreCase(username.trim())
								&& (list.get(i).getMst_lr_no() != null && !list.get(i).getMst_lr_no().equals("")
										|| list.get(i).getMst_lr_dt() != null)) {
							filteredlist.add(list.get(i));
						} else if (list.get(i).getDtl_transporter().trim().equalsIgnoreCase(username.trim())
								&& (list.get(i).getDtl_lr_no() != null && !list.get(i).getDtl_lr_no().equals("")
										|| list.get(i).getDtl_lr_dt() != null)) {
							filteredlist.add(list.get(i));
						}
					}
				}
			} else {
//			 if((list.get(i).getMst_recd_by()==null 
//						|| list.get(i).getMst_actual_delivery_date()==null || list.get(i).getDtl_actual_delivery_date()==null||
//						list.get(i).getDtl_recd_by()==null) &&
//						(list.get(i).getMst_lr_dt()!=null || list.get(i).getDtl_lr_dt()!=null ||
//						list.get(i).getMst_lr_no()!=null && !list.get(i).getMst_lr_no().equals("")||
//						list.get(i).getDtl_lr_no()!=null && !list.get(i).getDtl_lr_no().equals(""))) {

				if ((list.get(i).getDtl_actual_delivery_date() == null || list.get(i).getDtl_recd_by() == null)
						&& (list.get(i).getDtl_lr_dt() != null && list.get(i).getDtl_lr_no() != null
								&& !list.get(i).getDtl_lr_no().equals(""))) {
					filteredlist.add(list.get(i));
				}
			}
		}

		return filteredlist;
	}

	@Override
	public List<DispatchDetailReportRbmdm> callDisptachDetReportRmdm(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.callDisptachDetReportRmdm(bean);
	}

	@Override
	public List<StockWithdrawalReport> getStockwithdrawalReport(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getStockwithdrawalReport(bean);
	}

	@Override
	public List<Temp_fstaffmas_prx_error> getpforcerxfstafferrordata(Date startdt, Date enddt) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getpforcerxfstafferrordata(startdt, enddt);
	}

	@Override
	public List<Dg_veeva_pos_emp> getDgveevaposempdata(Date startdt, Date enddt) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDgveevaposempdata(startdt, enddt);
	}

	@Override
	public List<Dg_veeva_pos> getDgveevaposdata(Date startdt, Date enddt) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDgveevaposdata(startdt, enddt);
	}

	@Override
	public List<FstaffMas_PrxBean> getPrxFieldStaffStatusdata(Date startdt, Date enddt, Character Ind)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getPrxFieldStaffStatusdata(startdt, enddt, Ind);
	}

	@Override
	public List<Dg_veeva_emp> getpforcerxDgVeevaEmpData(Date startDate, Date endDate) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getpforcerxDgVeevaEmpData(startDate, endDate);
	}

	@Override
	public List<FstaffMas_PrxBean> getPrxFieldTerr_data(Date startdt, Date enddt, Character Ind) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getPrxFieldTerr_data(startdt, enddt, Ind);
	}

	@Override
	public List<Allocation_report_2> getdatafor_allocation_report2_current(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getdatafor_allocation_report2_current(bean);
	}

	@Override
	public List<Allocation_report_2> getdatafor_allocation_report2_previous(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getdatafor_allocation_report2_previous(bean);
	}

	@Override
	public List<Allocation_report_1> getdatafor_allocation_report1_current(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getdatafor_allocation_report1_current(bean);
	}

	@Override
	public List<Allocation_report_1> getdatafor_allocation_report1_previous(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getdatafor_allocation_report1_previous(bean);
	}

	@Override
	public List<IAADetailReport> getIAADetailReportData(String loc_id, String frm_iaa_id, String to_iaa_id,
			String[] strings) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getIAADetailReportData(loc_id, frm_iaa_id, to_iaa_id, strings);
	}

	@Override
	public List<IAADetailReport> getIAADetailReportPreviousData(String loc_id, String startDate, String endDate,
			String[] strings) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getIAADetailReportPreviousData(loc_id, startDate, endDate, strings);
	}

	@Override
	public List<StockAgeingGrnReportView> getstockageinggrnreportData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getstockageinggrnreportData(bean);
	}

	@Override
	public List<dispatch_register_report1> getDispatchRegisterReport1(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDispatchRegisterReport1(bean);
	}

	@Override
	public List<dispatch_register_report2> getDispatchRegisterReport2(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDispatchRegisterReport2(bean);
	}

	@Override
	public List<DivMaster> getAllDivisionList() throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getAllDivisionList();
	}

	@Override
	public List<Period> getAllPeriodList() throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getAllPeriodList();
	}

	@Override
	public List<Dispatch_Header> getcNameList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getcNameList(bean);
	}

	@Override
	public List<SmpItem> getProductList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getProductList(bean);
	}

	@Override
	public List<SG_d_parameters_details> getProductTypeList() throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getProductTypeList();
	}

	@Override
	public List<Dispatch_Header> getTeamList(String divId) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getTeamList(divId);
	}

	@Override
	public List<Dispatch_Header> getDocNameList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDocNameList(bean);
	}

	@Override
	public List<ReportBean> getEmpNameList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getEmpNameList(bean);
	}

	@Override
	public List<ReportBean> getRegNameList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getRegNameList(bean);
	}

	@Override
	public List<ReportBean> getDmNameList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDmNameList(bean);
	}

	@Override
	public List<ReportBean> getTerrCodeList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getTerrCodeList(bean);
	}

	@Override
	public List<Dispatch_Header> getLrNumList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getLrNumList(bean);
	}

	@Override
	public List<Dispatch_Header> getLrDateList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getLrDateList(bean);
	}

	@Override
	public List<Dispatch_Header> getChallanDateList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getChallanDateList(bean);
	}

	@Override
	public List<Dispatch_Header> getChallanNumList(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getChallanNumList(bean);
	}

	@Override
	public List<dispatch_register_report1_with_parameters> getDispatchRegisterRevisedReport1(ReportBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDispatchRegisterRevisedReport1(bean);
	}

	@Override
	public List<dispatch_register_report2_with_parameters> getDispatchRegisterRevisedReport2DoctorSupply(
			ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getDispatchRegisterRevisedReport2DoctorSupply(bean);
	}

	@Override
	public List<DispatchDetailGstReport> callDisptachDetailGstReport(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.callDisptachDetailGstReport(bean);
	}

	@Override
	public List<SummaryChallanReportEInvoice> callSummaryChallanReportEInv(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.callSummaryChallanReportEInv(bean);
	}

	@Override
	public List<BlkChallansGeneratedLog> getBlkChallasnsGeneratedLogReport(String finyr, String periodcd, Long reqId)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getBlkChallasnsGeneratedLogReport(finyr, periodcd, reqId);
	}

	@Override
	public List<DispatchIntimationEmail> getDispatchIntimationEmail(String stdate, String endate) throws Exception {
		// TODO Auto-generated method stub
//		ReportBean bean
		return reportRepository.getDispatchIntimationEmail(stdate, endate);
	}

	@Override
	public List<Object> getBrandsForTeamStmtReport(String empId, String divId) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getBrandsForTeamStmtReport(empId, divId);
	}

	@Override
	public String getSubCompanyNameByLocId(String loc_id) throws Exception {

		return reportRepository.getSubCompanyNameByLocId(loc_id);
	}

	@Override
	public List<FinalApprovalLogBean> getFinalApprovalLog() throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getFinalApprovalLog();
	}

	@Override
	public List<AllocationDetailReportModel> getAllocationDetailReportDataDiv(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getAllocationDetailReportDataDiv(bean);
	}

	@Override
	public List<Article_Stock_Statement> getArticleInventoryReport(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getArticleInventoryReport(bean);
	}

	@Override
	public List<Article_Stock_Statement_Itemwise> getArticleInventoryItemwiseReport(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getArticleInventoryItemwiseReport(bean);
	}
	
	@Override
	public List<SmpItem> getSmpItemList(Long locId) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getSmpItemList(locId);
	}

	@Override
	public List<Scheme_Summary> getSchemeSummary(List<String> schemeIds ,String finyear, String finyear_flag) throws Exception {
		// TODO Auto-generated method stub
		String formattedString = "(" + schemeIds.stream()
				// .map(item -> "'" + item + "'")
				.collect(Collectors.joining(", ")) + ")";
		System.out.println("formattedString::" + formattedString);
		List<Scheme_Summary> list = reportRepository.generateSchemeSummary(formattedString,finyear,finyear_flag);
		List<trd_scheme_mst_hdr> valid_extended_list = this.getExtendedValidities(schemeIds);

		// Create a map for expiry dates
		Map<Long, Date> extMap = new HashMap<>();
		for (trd_scheme_mst_hdr ext : valid_extended_list) {
			extMap.put(ext.getTrd_sch_slno(), ext.getValid_to_dt());
		}
		// Set expiry dates in itemList
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if(list != null) {
		for (Scheme_Summary scheme : list) {
			Date extDate = extMap.get(scheme.getTrd_sch_slno());
			
			if (extDate != null) {
				//swap valid to to the extended dates
				scheme.setValidity_extended_date(sdf.parse(scheme.getValid_to_dt()));
				scheme.setValid_to_dt(sdf.format(extDate));
			}
		}
		

		Map<Long, Long> sumMap = new HashMap<>();
		Map<Long, BigDecimal> sumValMap = new HashMap<>();
		Map<Long, Integer> countMap = new HashMap<>();

		// Calculate sum of ARTICLE_REQ_QTY and count occurrences
		for (Scheme_Summary scheme : list) {
			Long slNo = scheme.getTrd_sch_slno();
			Long articleQty = scheme.getArticle_req_qty();
			BigDecimal artVal = scheme.getArticle_req_item_value();
			// Sum ARTICLE_REQ_QTY
			sumMap.put(slNo, sumMap.getOrDefault(slNo, 0L) + articleQty);
			// Sum Art Req Val
			sumValMap.put(slNo, sumValMap.getOrDefault(slNo, BigDecimal.ZERO).add(artVal));
			// Count occurrences
			countMap.put(slNo, countMap.getOrDefault(slNo, 0) + 1);
		}

		// Update ARTICLE_REQ_QTY for schemes with multiple lines
		for (Scheme_Summary scheme : list) {
			Long slNo = scheme.getTrd_sch_slno();
			if (countMap.get(slNo) > 1) {
				scheme.setArticle_req_qty(sumMap.get(slNo));
				scheme.setArticle_req_item_value(sumValMap.get(slNo));
				Long newReqPendingQty = scheme.getArticle_req_qty() - scheme.getArt_dsp_qty();
				scheme.setReq_pending_qty(newReqPendingQty);
			}
			scheme.setAvailable_stock(scheme.getClosing_stock().longValue() - scheme.getReq_pending_qty());
		}
		}
		return list;
	}

	@Override
	public List<ArticleSchemeExceptionReport> articleExceptionReport(ReportBean bean) throws Exception {
		return reportRepository.articleExceptionReport(bean);
	}

	@Override
	public List<articleScheme> getarticleScheme() {
		return reportRepository.getarticleScheme();

	}

	@Override
	public List<trd_scheme_mst_hdr> getExtendedValidities(List<String> schemeIds) throws Exception {
		return reportRepository.getExtendedValidities(schemeIds);
	}

	@Override
	public List<ArticleSchemeSummaryReportCfa> articleSchemeSummaryReportCfa(ReportBean bean) throws Exception {
//		String formattedString = "("
//		+ schemeIds.stream()
//		.collect(Collectors.joining(", ")) + ")";
//System.out.println("formattedString::"+formattedString);

		List<Location> loc_list = this.locationService.getAllLocations();
		String formattedStringLoc = "("
				+ loc_list.stream().map(obj -> obj.getLoc_id().toString()).collect(Collectors.joining(", ")) + ")";

		String resultTrd_sch_slno = bean.getTrd_sch_slno().toString().replaceAll("[\\[\\]]", "");
		System.out.println("resultTrd_sch_slno :" + resultTrd_sch_slno);
		
		String formattedSaleProd = bean.getSaleprod().toString().replace("[", "(").replace("]",")");

		System.out.println("formattedSaleprod: " + formattedSaleProd);

		List<ArticleSchemeSummaryReportCfa> data = reportRepository.articleSchemeSummaryReportCfa(bean,
				formattedStringLoc, resultTrd_sch_slno,formattedSaleProd);
		
		
//Map<Long, Long> sumMap = new HashMap<>();
//Map<Long, BigDecimal> sumValMap = new HashMap<>();
//Map<Long, Integer> countMap = new HashMap<>();
/////calculate all the totals for art req qty and
//// Calculate sum of ARTICLE_REQ_QTY and count occurrences
//		for (ArticleSchemeSummaryReportCfa scheme : data) {
//			Long slNo = scheme.getTrd_sch_slno();
//			Long articleQty = scheme.getArticle_req_qty();
//			BigDecimal artVal = scheme.getArticle_req_item_value();
//			// Sum ARTICLE_REQ_QTY
//			sumMap.put(slNo, sumMap.getOrDefault(slNo, 0L) + articleQty);
//			// Sum Art Req Val
//			sumValMap.put(slNo, sumValMap.getOrDefault(slNo, BigDecimal.ZERO).add(artVal));
//			// Count occurrences
//			countMap.put(slNo, countMap.getOrDefault(slNo, 0) + 1);
//		}
//
//		// Update ARTICLE_REQ_QTY for schemes with multiple lines
//		for (ArticleSchemeSummaryReportCfa scheme : data) {
//			Long slNo = scheme.getTrd_sch_slno();
//			if (countMap.get(slNo) > 1) {
//				scheme.setArticle_req_qty(sumMap.get(slNo));
//				scheme.setArticle_req_item_value(sumValMap.get(slNo));
//				Long newReqPendingQty = scheme.getArticle_req_qty() - scheme.getArt_dsp_qty().longValue();
//				scheme.setReq_pending_qty(newReqPendingQty);
//			}
//			scheme.setAvailable_stock(scheme.getClosing_stock().longValue() - scheme.getReq_pending_qty());
//		}
		
		Long old_loc_id=0L;
		Long tot_Article_req_qty=0L;
		Long tot_Art_dsp_qty=0L;
		int same_loc_index=0;
		
//		for (ArticleSchemeSummaryReportCfa scheme : data) {
			
			for (int i=0;i< data.size();i++) {
			
			if(old_loc_id.equals(data.get(i).getLoc_id())) {
				
				data.get(i).setClosing_stock(new BigDecimal(0));
				data.get(i).setArt_dsp_qty(new BigDecimal(0));
				data.get(i).setArt_dsp_value(new BigDecimal(0));
				data.get(i).setReq_pending_qty(0L);
				
				tot_Article_req_qty+=data.get(i).getArticle_req_qty();
				tot_Art_dsp_qty+=data.get(i).getArt_dsp_qty().longValue();
				
				same_loc_index++;

			}else {
				
				
				
				
				if(i!=0) {
					data.get(i-same_loc_index-1).setReq_pending_qty(tot_Article_req_qty-tot_Art_dsp_qty);
					System.err.println(tot_Article_req_qty-tot_Art_dsp_qty);
					tot_Article_req_qty=0L;
					tot_Art_dsp_qty=0L;
					same_loc_index=0;
				}
				
				tot_Article_req_qty+=data.get(i).getArticle_req_qty();
				tot_Art_dsp_qty+=data.get(i).getArt_dsp_qty().longValue();
			}
	
			
	
//			scheme.setReq_pending_qty(scheme.getArticle_req_qty() - scheme.getArt_dsp_qty().longValue());
			

//			data.get(i).setAvailable_stock(data.get(i).getClosing_stock().longValue() - data.get(i).getReq_pending_qty());
			old_loc_id=data.get(i).getLoc_id();
			
		}

		
				data.get(data.size()-same_loc_index-1).setReq_pending_qty(tot_Article_req_qty-tot_Art_dsp_qty);
				System.err.println(tot_Article_req_qty-tot_Art_dsp_qty);
				tot_Article_req_qty=0L;
				tot_Art_dsp_qty=0L;
				same_loc_index=0;
			
		return data;
	}

	@Override
	public List<Lrcsv_RevisedDownload_SG> getLrcsvdownload_Revised_data_sg(String strdate, String enddate,
			String tbl_ind, String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp,
			String deptloc, String role, String username, List<String> loc, List<String> div, List<String> dest,
			String cfa_to_stk_ind) throws Exception {
	
		
		// TODO Auto-generated method stub
				List<Lrcsv_RevisedDownload_SG> list = null;
				List<Lrcsv_RevisedDownload_SG> filteredlist = new ArrayList<>();
				list = reportRepository.getLrcsvdownload_Revised_data_SG(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
						stk_at_cfa_option, temp, deptloc, loc, div, dest, cfa_to_stk_ind);

				for (int i = 0; i < list.size(); i++) {

					if (MedicoConstants.ROLE_COUR.equals(role.trim())) {
						if (list.get(i).getMst_transporter().trim().equalsIgnoreCase(username.trim())
								|| list.get(i).getDtl_transporter().trim().equalsIgnoreCase(username.trim())) {
							filteredlist.add(list.get(i));
						}
					} else {
						filteredlist.add(list.get(i));
					}
				}
				return filteredlist;
	}

	@Override
	public List<Lrcsv_RevisedDownload_SG> getLrDeliveryRecieptcsvdata_SG(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			String role, String username, List<String> loc, List<String> div, List<String> dest,
			String cfa_to_stockist_ind) throws Exception {
		// TODO Auto-generated method stub
		List<Lrcsv_RevisedDownload_SG> list = new ArrayList<>();
		List<Lrcsv_RevisedDownload_SG> filteredlist = new ArrayList<>();
		list = reportRepository.getLrcsvdownload_Revised_data_SG(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
				stk_at_cfa_option, temp, deptloc, loc, div, dest, cfa_to_stockist_ind);

		for (int i = 0; i < list.size(); i++) {

			if (MedicoConstants.ROLE_COUR.equals(role.trim())) {
				if (list.get(i).getMst_transporter().trim().equalsIgnoreCase(username.trim())
						|| list.get(i).getDtl_transporter().trim().equalsIgnoreCase(username.trim())) {

//				if((list.get(i).getMst_recd_by()==null 
//					|| list.get(i).getMst_actual_delivery_date()==null || list.get(i).getDtl_actual_delivery_date()==null||
//					list.get(i).getDtl_recd_by()==null) &&
//					(list.get(i).getMst_lr_dt()!=null || list.get(i).getDtl_lr_dt()!=null ||
//					list.get(i).getMst_lr_no()!=null && !list.get(i).getMst_lr_no().equals("")||
//					list.get(i).getDtl_lr_no()!=null && !list.get(i).getDtl_lr_no().equals(""))) {

					if ((list.get(i).getDtl_actual_delivery_date() == null || list.get(i).getDtl_recd_by() == null)
							&& (list.get(i).getDtl_lr_dt() != null && list.get(i).getDtl_lr_no() != null
									&& !list.get(i).getDtl_lr_no().equals(""))) {

						if (list.get(i).getMst_transporter().trim().equalsIgnoreCase(username.trim())
								&& (list.get(i).getMst_lr_no() != null && !list.get(i).getMst_lr_no().equals("")
										|| list.get(i).getMst_lr_dt() != null)) {
							filteredlist.add(list.get(i));
						} else if (list.get(i).getDtl_transporter().trim().equalsIgnoreCase(username.trim())
								&& (list.get(i).getDtl_lr_no() != null && !list.get(i).getDtl_lr_no().equals("")
										|| list.get(i).getDtl_lr_dt() != null)) {
							filteredlist.add(list.get(i));
						}
					}
				}
			} else {
//			 if((list.get(i).getMst_recd_by()==null 
//						|| list.get(i).getMst_actual_delivery_date()==null || list.get(i).getDtl_actual_delivery_date()==null||
//						list.get(i).getDtl_recd_by()==null) &&
//						(list.get(i).getMst_lr_dt()!=null || list.get(i).getDtl_lr_dt()!=null ||
//						list.get(i).getMst_lr_no()!=null && !list.get(i).getMst_lr_no().equals("")||
//						list.get(i).getDtl_lr_no()!=null && !list.get(i).getDtl_lr_no().equals(""))) {

				if ((list.get(i).getDtl_actual_delivery_date() == null || list.get(i).getDtl_recd_by() == null)
						&& (list.get(i).getDtl_lr_dt() != null && list.get(i).getDtl_lr_no() != null
								&& !list.get(i).getDtl_lr_no().equals(""))) {
					filteredlist.add(list.get(i));
				}
			}
		}

		return filteredlist;
	}
	
	@Override
	public List<IAADetailReport_SG> getIAADetailReportData_for_sg(String locId, String startDate, String endDate) throws Exception
			 {
		// TODO Auto-generated method stub
		return reportRepository.getIAADetailReportData_for_sg(locId, startDate, endDate);
	}

	@Override
	public List<IAADetailReport_SG> getIAADetailReportPreviousData_for_sg(String loc_id, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return reportRepository.getIAADetailReportPreviousData_for_sg(loc_id, startDate, endDate);

	}

	@Override
	public List<DispatchDetailReportRevised_SG> callDisptachDetReportRevised_SG(ReportBean bean) throws Exception {
	
		
		return reportRepository.callDisptachDetReportRevised_SG(bean);
	}
	
	@Override
	public List<BatchwiseStockStmtReport_SG> getBatchwiseStockStmtData_SG(ReportBean bean, String stk_to_cfa_ind) {
		// TODO Auto-generated method stub
		return reportRepository.getBatchwiseStockStmtData_SG(bean, stk_to_cfa_ind);
	}
	
	

	@Override
	public List<DispatchDetailReportRevisedMdmNo> callDisptachDetReportRevisedMdmNo(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.callDisptachDetReportRevisedMdmNo(bean);
	}

	@Override
	public Boolean check_StateNameRequired_or_not() {
		return reportRepository.check_StateNameRequired_or_not();
		
	}

	@Override
	public List<Csv_File_List_Without_state_name> get_Csv_File_Consolidated_List_Without_State_Name(String id) {
	
		
		return reportRepository.get_Csv_File_Consolidated_List_Without_State_Name(id);
	}

	@Override
	public List<DispatchDetailReportRevised_VET> callDisptachDetReportRevised_VET(ReportBean bean) {
		// TODO Auto-generated method stub
		return reportRepository.callDisptachDetReportRevised_VET(bean);
	}

	@Override
	public List<ViewGRNSummary_GST_VET> getGRNSummaryReportData_VET(@Valid ReportBean bean) {
		// TODO Auto-generated method stub
		return reportRepository.getGRNSummaryReportData_VET(bean);
	}

	@Override
	public List<ViewGRNSummary_VET> getGRNSummaryData_VET(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getGRNSummaryData_VET(bean);
	}

	@Override
	public List<Stock_Statement_Non_Sale_VET> getStockStatmentNonsalable_VET(ReportBean bean) {
		
		return reportRepository.getDataStkNonsalable_VET(bean);
	}

	@Override
	public List<Scheme_Summary> getSchemeSummary_drill_down_list(List<String> schemeIds, String finyear,
			String finyear_flag) throws Exception {
		// TODO Auto-generated method stub
		String formattedString = "(" + schemeIds.stream()
				// .map(item -> "'" + item + "'")
				.collect(Collectors.joining(", ")) + ")";
		System.out.println("formattedString::" + formattedString);
		List<Scheme_Summary> list = reportRepository.generateSchemeSummary_drill_down(formattedString,finyear,finyear_flag);
		List<trd_scheme_mst_hdr> valid_extended_list = this.getExtendedValidities(schemeIds);

		// Create a map for expiry dates
		Map<Long, Date> extMap = new HashMap<>();
		for (trd_scheme_mst_hdr ext : valid_extended_list) {
			extMap.put(ext.getTrd_sch_slno(), ext.getValid_to_dt());
		}
		// Set expiry dates in itemList
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if(list != null) {
		for (Scheme_Summary scheme : list) {
			Date extDate = extMap.get(scheme.getTrd_sch_slno());
			
			if (extDate != null) {
				//swap valid to to the extended dates
				scheme.setValidity_extended_date(sdf.parse(scheme.getValid_to_dt()));
				scheme.setValid_to_dt(sdf.format(extDate));
			}
		}
		

		Map<Long, Long> sumMap = new HashMap<>();
		Map<Long, BigDecimal> sumValMap = new HashMap<>();
		Map<Long, Integer> countMap = new HashMap<>();

		// Calculate sum of ARTICLE_REQ_QTY and count occurrences
		for (Scheme_Summary scheme : list) {
			Long slNo = scheme.getTrd_sch_slno();
			Long articleQty = scheme.getArticle_req_qty();
			BigDecimal artVal = scheme.getArticle_req_item_value();
			// Sum ARTICLE_REQ_QTY
			sumMap.put(slNo, sumMap.getOrDefault(slNo, 0L) + articleQty);
			// Sum Art Req Val
			sumValMap.put(slNo, sumValMap.getOrDefault(slNo, BigDecimal.ZERO).add(artVal));
			// Count occurrences
			countMap.put(slNo, countMap.getOrDefault(slNo, 0) + 1);
		}

		// Update ARTICLE_REQ_QTY for schemes with multiple lines
		for (Scheme_Summary scheme : list) {
			Long slNo = scheme.getTrd_sch_slno();
			if (countMap.get(slNo) > 1) {
				scheme.setArticle_req_qty(sumMap.get(slNo));
				scheme.setArticle_req_item_value(sumValMap.get(slNo));
				Long newReqPendingQty = scheme.getArticle_req_qty() - scheme.getArt_dsp_qty();
				scheme.setReq_pending_qty(newReqPendingQty);
			}
			scheme.setAvailable_stock(scheme.getClosing_stock().longValue() - scheme.getReq_pending_qty());
		}
		}
		return list;
	}

	@Override
	public List<Ter_mst_hierarchy_report_bean> download_ter_hyrarchy_Report(String div_id) throws Exception {
	
		
		return reportRepository.download_ter_hyrarchy_Report(div_id);
	}

	@Override
	public List<FieldStaffCsvDownload> getFstaffDownloadCsvData(String divId) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getFstaffDownloadCsvData(divId);
	}
	
	@Override
	public List<String> getCsvFileList() throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getCsvFileList();
	}

	@Override
	public List<Fieldstaff_After_mobile_No_Update_CheckList> getUpdatedFstaffMobileNo(String csvFileName)
			throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getUpdatedFstaffMobileNo(csvFileName);
	}
	
	@Override
	public List<FieldStaffBean> getFieldstaffMobileOdsdCount(Date date) throws Exception {
		// TODO Auto-generated method stub
		return reportRepository.getFieldstaffMobileOdsdCount(date);
	}

}
