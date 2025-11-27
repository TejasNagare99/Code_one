package com.excel.repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.validation.Valid;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.AllocationBean;
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
import com.excel.model.DIV_TEAM_BRAND;
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
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@Repository
public class ReportRepositoryImpl implements ReportRepository, MedicoConstants {

	@Autowired
	private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DispatchSummaryDetailReport> callDisptachSumDetReport(ReportBean bean) throws Exception {
		List<DispatchSummaryDetailReport> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callDispatchSummaryDetailReport");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "S");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			callDisptachSumDetReport.setParameter("field_level", "0");
			callDisptachSumDetReport.setParameter("prod_id", "0");
			callDisptachSumDetReport.setParameter("dsp_status", "A");
			callDisptachSumDetReport.setParameter("cfa", "0");
			callDisptachSumDetReport.setParameter("fsid", "0");
			callDisptachSumDetReport.setParameter("desig", "Y");
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());
			list = callDisptachSumDetReport.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<DispatchDetailReport> callDisptachDetReport(ReportBean bean) throws Exception {
		List<DispatchDetailReport> list = null;
		EntityManager em = null;
		try {

			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag" + bean.getFinyear());
			System.out.println("fin_year_id" + bean.getFinyearflag());

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callDispatchDetailReport");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());
			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of dispatch details ::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<SummaryChallanReport> callSummaryChallanReport(ReportBean bean) throws Exception {
		List<SummaryChallanReport> list = null;
		EntityManager em = null;
		try {
			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("field_level" + bean.getFstaff_id_o());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag" + bean.getFinyear());
			System.out.println("fin_year_id" + bean.getFinyearflag());

			em = emf.createEntityManager();

			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callSummaryChallanReport");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());

			/*
			 * callDisptachSumDetReport.setParameter("div_id", "0");
			 * callDisptachSumDetReport.setParameter("startdate", "01-01-2019");
			 * callDisptachSumDetReport.setParameter("endDate", "04-12-2019");
			 * callDisptachSumDetReport.setParameter("reptype", "D");
			 * callDisptachSumDetReport.setParameter("subcomp", "0");
			 * callDisptachSumDetReport.setParameter("orderby", "N");
			 * callDisptachSumDetReport.setParameter("tabl_ind", "A");
			 * callDisptachSumDetReport.setParameter("user_id", "E00040");
			 * callDisptachSumDetReport.setParameter("field_level", "0");
			 * callDisptachSumDetReport.setParameter("prod_id", "0");
			 * callDisptachSumDetReport.setParameter("dsp_status", "A");
			 * callDisptachSumDetReport.setParameter("cfa", "0");
			 * callDisptachSumDetReport.setParameter("fsid", "0");
			 * callDisptachSumDetReport.setParameter("desig", "N");
			 */

//			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
//			callDisptachSumDetReport.setParameter("startdate", MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
//			callDisptachSumDetReport.setParameter("endDate", MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
//			callDisptachSumDetReport.setParameter("reptype", "D");
//			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
//			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
//			callDisptachSumDetReport.setParameter("tabl_ind", "A");
//			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
//			callDisptachSumDetReport.setParameter("field_level", "0");
//			callDisptachSumDetReport.setParameter("prod_id", "0");
//			callDisptachSumDetReport.setParameter("dsp_status", "A");
//			callDisptachSumDetReport.setParameter("cfa", "0");
//			callDisptachSumDetReport.setParameter("fsid", "0");
//			callDisptachSumDetReport.setParameter("desig", "Y");
			list = callDisptachSumDetReport.getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewGRNSummary_GST> getGRNSummaryReportData(ReportBean bean) throws Exception {
		List<ViewGRNSummary_GST> list = null;
		EntityManager em = null;
		try {
			/*
			 * Div id :- 0 from loc id :-0 To Location :- 0 frm_date :- 01-10-2019 to_date
			 * :- 19-11-2019 user id: :- E00040
			 */
			System.out.println("In call	");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em.createNamedStoredProcedureQuery("GrnSummaryReport_GST");
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("SEND_LOC", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("RECV_LOC", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("DIVID", bean.getDivId());
			callMonthlyActivityReport.setParameter("USER_ID", bean.getUserId());
			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("fin_year_id", bean.getFinyearflag());

			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of the GRN summary Report ::::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewGRNDetail_GST> getGRNDetailReportData(ReportBean bean) throws Exception {
		List<ViewGRNDetail_GST> list = null;
		EntityManager em = null;
		try {
			/*
			 * frm_date :-01-10-2019 to_date :- 19-11-2019 frm_loc_id :- 0 to_loc_id :- 0
			 * div_id :- 0 userid :- E00040
			 */
			em = emf.createEntityManager();

			StoredProcedureQuery callMonthlyActivityReport = null;
			if (bean.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				callMonthlyActivityReport = em.createNamedStoredProcedureQuery("GrnDetailReport_GST");
				callMonthlyActivityReport.setParameter("prod_id",
						bean.getProducts().toString().replace("[", "(").replace("]", ")"));
			} else {

				callMonthlyActivityReport = em.createNamedStoredProcedureQuery("GrnDetailReport_GST_SG");
			}
			
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("SEND_LOC", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("RECV_LOC", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("DIVID", bean.getDivId());
			callMonthlyActivityReport.setParameter("USER_ID", bean.getUserId());
			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("fin_year_id", bean.getFinyearflag());
			callMonthlyActivityReport.setParameter("SA_NS", bean.getSa_ns());

			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of the GRN Details Report ::::-" + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewGRNSummary> getGRNSummaryData(ReportBean bean) throws Exception {
		List<ViewGRNSummary> list = null;
		EntityManager em = null;
		try {

			System.out.println("inside the View grn summary");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em.createNamedStoredProcedureQuery("GrnSummaryReport");
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("SEND_LOC", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("RECV_LOC", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("DIVID", bean.getDivId());
			callMonthlyActivityReport.setParameter("USER_ID", bean.getUserId());
			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("fin_year_id", bean.getFinyearflag());

			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of the GRN summary Report ::::-" + list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewGRNDetail> getGRNDetailData(ReportBean bean) throws Exception {
		List<ViewGRNDetail> list = null;
		EntityManager em = null;
		try {
			/*
			 * Div id 0 from loc id 0 To Location 0 frm_date 29-11-2019 to_date 29-11-2019
			 * user id: E00040
			 */
			System.out.println("In call	");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em.createNamedStoredProcedureQuery("GrnDetailReport");
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("SEND_LOC", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("RECV_LOC", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("DIVID", bean.getDivId());
			callMonthlyActivityReport.setParameter("USER_ID", bean.getUserId());
			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("fin_year_id", bean.getFinyearflag());

			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of the GRN detail Report ::::-" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;

		// TODO Auto-generated method stub
	}

	@Override
	public List<BatchwiseStockStmtReport> getBatchwiseStockStmtData(ReportBean bean, String stk_to_cfa_ind)
			throws Exception {
		List<BatchwiseStockStmtReport> list = null;
		StoredProcedureQuery callDisptachSumDetReport = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();

			if ("Y".equalsIgnoreCase(stk_to_cfa_ind)) {

				callDisptachSumDetReport = em
						.createNamedStoredProcedureQuery("callBatchwiseStockStmtReportFor_stockist_to_cfa");
			}
			System.out.println("piloc_id" + bean.getLocId());
			System.out.println("pidiv_id" + bean.getDivId());
			System.out.println("pvfrmdt" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("pvtodt" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("pvuserid" + bean.getUserId());
			System.out.println("pvChkzero" + bean.getZeroStock());
			System.out.println("exp_ind" + bean.getNearExp());
			System.out.println("nilstock" + bean.getZeroStock());
			System.out.println("FIN_YEAR_FLAG" + bean.getFinyear());
			System.out.println("fin_year_id" + bean.getFinyearflag());
			System.out.println("rate " + bean.getRate());

			callDisptachSumDetReport.setParameter("piloc_id", bean.getLocId());
			callDisptachSumDetReport.setParameter("pidiv_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("pvfrmdt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("pvtodt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("pvuserid", bean.getUserId());
			callDisptachSumDetReport.setParameter("pvChkzero", bean.getZeroStock());
			callDisptachSumDetReport.setParameter("exp_ind", bean.getNearExp());
			callDisptachSumDetReport.setParameter("nilstock", bean.getZeroStock());
			callDisptachSumDetReport.setParameter("FINYEAR", bean.getFinyearflag());
			callDisptachSumDetReport.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
			callDisptachSumDetReport.setParameter("RATE_OPTION", bean.getRate());
			list = callDisptachSumDetReport.getResultList();

			System.out.println("list : " + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<StkTrfSummaryRepotModel> getStkTrfSummaryReportData(ReportBean bean) throws Exception {
		List<StkTrfSummaryRepotModel> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em
					.createNamedStoredProcedureQuery("STOCK_TRANSFER_SUMMARY_REPORT");
			/*
			 * callMonthlyActivityReport.setParameter("loc_id", "1");
			 * callMonthlyActivityReport.setParameter("cfa", "0");
			 * callMonthlyActivityReport.setParameter("startdate", "26-11-2019");
			 * callMonthlyActivityReport.setParameter("endDate", "26-11-2019");
			 * callMonthlyActivityReport.setParameter("user_id", "E00040");
			 */

			callMonthlyActivityReport.setParameter("loc_id", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("cfa", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("user_id", bean.getUserId());
			callMonthlyActivityReport.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());
			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of Stock transfer Summary::::-" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<StkTrfDetailRepotModel> getStktrfDetailReportData(ReportBean bean) throws Exception {
		List<StkTrfDetailRepotModel> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em
					.createNamedStoredProcedureQuery("STOCK_TRANSFER_DETAIL_REPORT");
			callMonthlyActivityReport.setParameter("loc_id", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("cfa", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("user_id", bean.getUserId());
			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("fin_year_id", bean.getFinyearflag());
			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of the Stock Transfer Detail Report ::::-" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stock_statement_pfz> getstockStatmentData(ReportBean bean) throws Exception {
		List<Stock_statement_pfz> list = null;
		EntityManager em = null;
		try {
			String brand = "(" + String.join(",", bean.getBrandlist()) + ")";
			System.out.println("brand list::::" + bean.getBrandlist() + ":::::" + brand);
			em = emf.createEntityManager();
			StoredProcedureQuery stockstatmentdata = em.createNamedStoredProcedureQuery("STOCK_STATEMENT_FCODE");
			stockstatmentdata.setParameter("div_id", bean.getDivId());
			stockstatmentdata.setParameter("loc_id", bean.getLocId());
			stockstatmentdata.setParameter("nilstock", bean.getZeroStock());
			stockstatmentdata.setParameter("exp_ind", bean.getNearExp());
			stockstatmentdata.setParameter("USER_ID", bean.getUserId());
			stockstatmentdata.setParameter("brand_id", brand);
			stockstatmentdata.setParameter("smp_prod_type", bean.getProductType());
			stockstatmentdata.setParameter("RATE_OPTION", bean.getRate());
			list = stockstatmentdata.getResultList();
			System.out.println("stock statment data List size ::::" + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<Stock_Statement_Non_Sale> getDataStkNonsalable(ReportBean bean) throws Exception {
		List<Stock_Statement_Non_Sale> list = null;
		EntityManager em = null;
		try {
			// STOCK_STATEMENT_NONSALEABLE
			// '01-11-2019','30-11-2020','0','0','N','0','0','0','E00040'

			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("div_id" + bean.getDivId());
			System.out.println("loc_id" + bean.getLocId());
			System.out.println("nilstock" + bean.getZero_stock_id());
			System.out.println("ProdTypeId" + bean.getProd_type_id());
			System.out.println("ProdId" + bean.getProd_id());
			System.out.println("StockType" + bean.getStock_type_id());
			System.out.println("USER_ID" + bean.getUserId());

			System.out.println("fin_year_flag" + bean.getFinyear());
			System.out.println("fin_year_id" + bean.getFinyearflag());

			em = emf.createEntityManager();
			StoredProcedureQuery stockstatmentdata = em.createNamedStoredProcedureQuery("STOCK_STATEMENT_NONSALEABLE");
			/*
			 * stockstatmentdata.setParameter("startdate", "01-11-2019");
			 * stockstatmentdata.setParameter("endDate", "30-11-2020");
			 * stockstatmentdata.setParameter("div_id", "0");
			 * stockstatmentdata.setParameter("loc_id", "0");
			 * stockstatmentdata.setParameter("nilstock", "N");
			 * stockstatmentdata.setParameter("ProdTypeId", "0");
			 * stockstatmentdata.setParameter("ProdId", "0");
			 * stockstatmentdata.setParameter("StockType", "0");
			 * stockstatmentdata.setParameter("USER_ID", "E00040");
			 */

			stockstatmentdata.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			stockstatmentdata.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			stockstatmentdata.setParameter("div_id", bean.getDivId());
			stockstatmentdata.setParameter("loc_id", bean.getLocId());
			stockstatmentdata.setParameter("nilstock", bean.getZero_stock_id());
			stockstatmentdata.setParameter("ProdTypeId", bean.getProd_type_id());
			stockstatmentdata.setParameter("ProdId", bean.getProd_id());
			stockstatmentdata.setParameter("StockType", bean.getStock_type_id());
			stockstatmentdata.setParameter("USER_ID", bean.getUserId());

			stockstatmentdata.setParameter("fin_year_flag", bean.getFinyear());
			stockstatmentdata.setParameter("fin_year_id", bean.getFinyearflag());

			list = stockstatmentdata.getResultList();
			System.out.println("stock statment Nonssalable List size ::::-" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;

		// TODO Auto-generated method stub
	}

	@Override
	public List<Stock_Reco_With_sfa> getStockrecowithsfa() {
		List<Stock_Reco_With_sfa> list = new ArrayList<>();
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery callStkRecotReport = em.createNamedStoredProcedureQuery("Call_STOCK_RECO_WITH_SFA");
			list = callStkRecotReport.getResultList();
			System.out.println(list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;

		}
		return list;
	}

	@Override
	public List<ItemWiseStockLedgerModel> getItemWise_StockledgerData(ReportBean bean) throws Exception {

		List<ItemWiseStockLedgerModel> lst = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery stockstatmentdata = em.createNamedStoredProcedureQuery("ItemWiseStockLedger");

			stockstatmentdata.setParameter("piloc_id", Integer.valueOf(bean.getLocId()));
			stockstatmentdata.setParameter("pidiv_id", Integer.valueOf(bean.getDivId()));
			stockstatmentdata.setParameter("pvfrmdt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())); // changed
			stockstatmentdata.setParameter("pvtodt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			stockstatmentdata.setParameter("piprod_id", Integer.valueOf(bean.getProd_id()));
			stockstatmentdata.setParameter("pvuserid", bean.getUserId());

			stockstatmentdata.setParameter("fin_year_flag", bean.getFinyear());
			stockstatmentdata.setParameter("fin_year_id", bean.getFinyearflag());

			lst = stockstatmentdata.getResultList();

			System.out.println("List ::" + lst.size());
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (lst != null && !lst.isEmpty()) {
			return lst;
		}

		return lst;
	}

	@Override
	public List<BatchWiseStockLedgerModel> getBatchwise_StockledgerData(ReportBean bean) throws Exception {

		List<BatchWiseStockLedgerModel> lst = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("BatchWiseStockLedger");

			query.setParameter("piloc_id", Integer.valueOf(bean.getLocId()));
			query.setParameter("pidiv_id", Integer.valueOf(bean.getDivId()));
			query.setParameter("pvfrmdt", MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			query.setParameter("pvtodt", MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			query.setParameter("piprod_id", Integer.valueOf(bean.getProd_id()));
			query.setParameter("pibatch_id", Integer.valueOf(bean.getBatch_id()));
			query.setParameter("pvuserid", bean.getUserId());
			query.setParameter("fin_year_flag", bean.getFinyear());
			query.setParameter("fin_year_id", bean.getFinyearflag());

			lst = query.getResultList();

			System.out.println("List ::" + lst.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return lst;
	}

	@Override
	public List<AllocationDetailReportModel> getAllocationDetailReportData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<AllocationDetailReportModel> lst = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("EmptyCsvMktgView");
			query.setParameter("PER_CD", bean.getPeriod_code());
			query.setParameter("FINYR", bean.getFin_year());
			query.setParameter("DIVID", bean.getDivId());

			lst = query.getResultList();

			System.out.println("List ::" + lst.size());
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (lst != null && !lst.isEmpty()) {
			return lst;
		}
		return null;
	}

	@Override
	public List<Period> getAllActiveFinYearForAllocationDetailReport() {
		EntityManager em = null;
		List<Period> list = new ArrayList();
		try {
			em = emf.createEntityManager();
			String q = "Select DISTINCT Period_fin_year AS  Period_fin_year from Period where period_status='A' ORDER BY Period_fin_year";
			Query query = em.createNativeQuery(q, Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Period period = new Period();
					period.setPeriod_fin_year(t.get("PERIOD_FIN_YEAR", String.class));
					list.add(period);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Allocation_report_3> getAllocation_Report_3_data(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<Allocation_report_3> lst = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("Allocation_report_3");
			query.setParameter("pdiv_id", bean.getPdiv_id());
			query.setParameter("pfin_year", bean.getPfin_year());
			query.setParameter("pperiod_code", bean.getPperiod_code());
			query.setParameter("psub_comp_id", bean.getPsub_comp_id());
			query.setParameter("pprod_sub_type_id", bean.getPprod_sub_type_id());

			lst = query.getResultList();

			System.out.println("List ::" + lst.size());
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (lst != null && !lst.isEmpty()) {
			return lst;
		}
		return null;
	}

	// Fieldstaff Download(8-5-2020)
	@Override
	public List<FieldMasterDownload> getFieldMasterDownloadData(ReportBean bean, String comp_code) throws Exception {
		List<FieldMasterDownload> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					"SELECT FS.FSTAFF_ID,FS.FSTAFF_DIV_ID,DIV_DISP_NM,FS.FSTAFF_LEVEL_CODE,FS.FSTAFF_NAME,FS.FSTAFF_MAP_CODE1,");
			sb.append(" HQ_NAME,RM.FSTAFF_NAME RM,AM.FSTAFF_NAME AM,");
			sb.append(
					" FS.FSTAFF_ADDR1,FS.FSTAFF_ADDR2,FS.FSTAFF_ADDR3,FS.FSTAFF_ADDR4,FS.FSTAFF_MOBILE,FS.FSTAFF_DESTINATION,");
			sb.append(
					" FS.FSTAFF_MAP_CODE2,FS.FSTAFF_DISPLAY_NAME, FS.FSTAFF_DESIG, FS.FSTAFF_ZONENAME, FS.FSTAFF_EMPLOYEE_NO, FS.FSTAFF_EMAIL");
			sb.append(" FROM FIELDSTAFF FS");
			sb.append(" LEFT JOIN HQMASTER ON FSTAFF_HQ_ID=HQ_ID");
			sb.append(" LEFT JOIN FIELDSTAFF RM ON RM.FSTAFF_ID=FS.FSTAFF_MGR2_ID");
			sb.append(" LEFT JOIN FIELDSTAFF AM ON AM.FSTAFF_ID=FS.FSTAFF_MGR1_ID");
			sb.append(" JOIN DIV_MASTER ON FS.FSTAFF_DIV_ID=DIV_ID");
			sb.append(" WHERE FS.FSTAFF_STATUS = 'A' AND ( FS.FSTAFF_SAMP_DISP_IND='Y'");
				if(!bean.getLevelCode().equalsIgnoreCase("0"))            //temporary value level_code
				{
			sb.append(" AND FS.FSTAFF_LEVEL_CODE='" + bean.getLevelCode() + "'");
				}
			System.out.println("company code::" + comp_code);
			if (comp_code.equalsIgnoreCase("PFZ")) {
				sb.append(" AND DIV_ID NOT IN  ( 11,12,19,35,37)");
			}
			sb.append(")  ORDER BY FS.FSTAFF_DIV_ID,FS.FSTAFF_LEVEL_CODE,fs.fstaff_map_code1");

			Query query = em.createNativeQuery(sb.toString(), FieldMasterDownload.class);
			list = query.getResultList();
			System.out.println("List :: " + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return list;

	}

	// Product Download
	@Override
	public List<ProductMasterAttrib> getProductMasterDownloadData(ReportBean bean) throws Exception {
		List<ProductMasterAttrib> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			// String q=" SELECT DIV.DIV_DISP_NM , ITEM.* FROM SMPITEM ITEM JOIN DIV_MASTER
			// DIV ON DIV_ID = SMP_STD_DIV_ID WHERE SMP_status='A' ORDER BY DIV.DIV_DISP_NM
			// ";
			sb.append("SELECT   DIV.DIV_DISP_NM,SMP_PROD_ID,SMP_PROD_CD,SMP_COMPANY,SMP_PROD_NAME,SMP_PACK_ID,");
			sb.append("SMP_ALTER_NAME,SMP_LAUNCH_DT,SMP_DISCONT_DT,SMP_INV_GRP_ID,SMP_UOM_ID,SMP_FORM_ID,");
			sb.append("SMP_SCH_IND,SMP_DIV_RQ_IND,SMP_EXPIRY_RQ_IND,SMP_BATCH_RQ_IND,SMP_ALLOC_MULTIPLES,");
			sb.append("SMP_ABC_IND,SMP_STOCK_DAYS,SMP_PROD_TYPE,SMP_SAMPLING_TYPE,SMP_SHELF_LIFE,SMP_MFG_LOC_ID,");
			sb.append("SMP_SHORT_EXPIRY_DAYS,SMP_PROD_BATCH_SIZE,SMP_ERP_PROD_CD,SMP_SA_PROD_GROUP,");
			sb.append("SMP_THP_GRP_ID,SMP_THP_SGRP_ID,SMP_MOLE_GRP_ID,SMP_MOLE_SGRP_ID,SMP_PMT_GRP_ID,");
			sb.append("SMP_PMT_SGRP_ID,SMP_QTY_SHIPPER,SMP_QTY_BOX,SMP_QTY_STRIP,");
			sb.append("SMP_MAX_ALLOC_QTY,SMP_MIN_ALLOC_QTY,SMP_STD_ALLOC_QTY,SMP_SHIPPER_VOL,");
			sb.append("SMP_SHIPPER_NWT,SMP_SHIPPER_GWT,SMP_EXCLUDE_LOC,SMP_EXCLUDE_PARTY,SMP_SPEC_DIV_IND,");
			sb.append("SMP_COG_RATE,SMP_COG_EXE_RATE,SMP_RATE,SMP_COSTING_RATE,SMP_MKTG_RATE,");
			sb.append("SMP_NRV,SMP_DISPLAY_RATE,SMP_STD_DIV_ID,SMP_ins_usr_id,SMP_mod_usr_id,SMP_ins_dt,");
			sb.append("SMP_mod_dt,SMP_ins_ip_add,SMP_mod_ip_add,SMP_status,SMP_PROD_NAME_OLD,");
			sb.append("SMP_SubComp_id,INV_GRP_ID,STORAGE_TYPE,STORAGE_TYPE_ID,SMP_PROD_TYPE_ID,");
			sb.append("HSN_CODE,MARGIN_PERC,PROMO_EXPIRY_ACCEPT_IND,PROD_SUB_TYPE_ID,");
			sb.append("smp_supply_type_id,PROD_THRESHOLD,PROD_HIGHVALUE,PROD_SENSITIVE ");
			sb.append("  FROM SMPITEM ITEM");
			sb.append("  JOIN DIV_MASTER DIV ON DIV.DIV_ID = SMP_STD_DIV_ID");
			sb.append("  WHERE SMP_status='A' ORDER BY DIV.DIV_DISP_NM");
			/* Query query = em.createNativeQuery(q,Tuple.class); */
			Query query = em.createNativeQuery(sb.toString(), ProductMasterAttrib.class);
			list = query.getResultList();
			System.out.println("List :: " + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return list;

	}

	// SAP Dispatch Download
	@Override
	public List<Sap_Dispatch_Download> getSapDispatchDownloadData(ReportBean rb, String startDate, String endDate)
			throws Exception {
		List<Sap_Dispatch_Download> list = null;
		EntityManager em = null;
		try {

			em = emf.createEntityManager();
			StoredProcedureQuery sapdispatchdata = em.createNamedStoredProcedureQuery("callSap_Dispatch_Download");
			sapdispatchdata.setParameter("pvfrmdt", startDate);
			sapdispatchdata.setParameter("pvtodt", endDate);

			list = sapdispatchdata.getResultList();
			System.out.println("sap dispatch download List size ::::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return list;

		// TODO Auto-generated method stub
	}

	@Override
	public List<LrcsvDownload> getLrcsvdownload_data(String strdate, String enddate, String tbl_ind, String cfa,
			String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc)
			throws Exception {

		List<LrcsvDownload> list = null;
		String procedureToCall = "";
		EntityManager em = null;

		try {

			tbl_ind = "A";
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			System.out.println("stock_at_cfa_ind::" + stock_at_cfa_ind);
			System.out.println("start" + strdate);
			System.out.println("start" + strdate);
//				 strdate = "2020/05/01";
//				 enddate = "2020/05/10";
			if (stk_at_cfa_option == null) {
				stk_at_cfa_option = "N";
			}
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			if (stk_at_cfa_option.equalsIgnoreCase("Y") && stock_at_cfa_ind.equalsIgnoreCase("Y")) {
				procedureToCall = "call_LR_csv_Download_stock_cfa";
			} else {
				procedureToCall = "call_LR_csv_Download";
			}

			System.out.println("tbl_ind::::" + tbl_ind + "::::strdate::::" + strdate + "::::enddate::::" + enddate
					+ "::::cfa::::" + cfa + "::::fsid::::" + fsid);

			em = emf.createEntityManager();

			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery(procedureToCall);
			procedurecall.setParameter("startdt", strdate);
			procedurecall.setParameter("enddt", enddate);
			procedurecall.setParameter("tabl_ind", tbl_ind);

			if (!deptloc.trim().equals("0") && cfa.equals("0")) {
				// System.out.println("INSIDE
				// IFFFFFFFFFFFF::::::::::::::"+temp.toString());
				procedurecall.setParameter("cfa", temp.toString());
			} else {

				if (deptloc.trim().equals("0") && cfa.equals("0")) {
					// System.out.println("INSIDE ELSEEEEEEEEEEE:::::::::::::"
					// + "(" + cfa + ")");
					procedurecall.setParameter("cfa", cfa);
				} else {
					procedurecall.setParameter("cfa", "(" + cfa + ")");
				}
			}
			procedurecall.setParameter("fsid", fsid);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<LrcsvDownloadReport> get_Lrcsv_data(ReportBean bean, String tabl_ind, String cfa, String fsid,
			String fstype, String deptloc, String temp) throws Exception {
		EntityManager em = null;
		List<LrcsvDownloadReport> list = null;
		try {
			em = emf.createEntityManager();

			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("call_LR_csv_upload_proc_report");
			procedurecall.setParameter("startdt",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate()));
			procedurecall.setParameter("enddt", MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate()));
			procedurecall.setParameter("tabl_ind", tabl_ind);
			if (!deptloc.trim().equals("0") && cfa.equals("0")) {
				procedurecall.setParameter("cfa", temp.toString());
			} else {
				if (deptloc.trim().equals("0") && cfa.equals("0")) {
					procedurecall.setParameter("cfa", cfa);
				} else {
					procedurecall.setParameter("cfa", "(" + cfa + ")");
				}
			}
			procedurecall.setParameter("fsid", fsid);
			procedurecall.setParameter("fstype", fstype);

			System.out.println("startdt " + MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate()));
			System.out.println("enddt " + MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate()));
			System.out.println("tabl_ind " + tabl_ind);
			System.out.println("cfa " + cfa);
			System.out.println("fsid " + fsid);
			System.out.println("fstype " + fstype);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<Csv_File_List> get_allocation_downloadcsvdata(String id) throws Exception {

		List<Csv_File_List> list = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("callALLOC_CSV_DOWNLOAD");
			procedurecall.setParameter("gen_id", id);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<Csv_File_Consolidated_List> get_Csv_File_Consolidated_List(String id) throws Exception {
		System.out.println("ID " + id);

		List<Csv_File_Consolidated_List> list = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("callALLOC_Consolidated_CSV_DOWNLOAD");
			procedurecall.setParameter("palloc_con_id", id);
			list = procedurecall.getResultList();
			System.out.println("list " + list.size());
			int j = 1;
			for (Csv_File_Consolidated_List file_List : list) {
				System.out.println("no " + j + " AllocGenId " + file_List.getAlloc_gen_id() + "Staff Name "
						+ file_List.getFs_name() + " prod name " + file_List.getSmp_prod_name() + "Id "
						+ file_List.getSmp_prod_id());
				j++;
			}

		} catch (Exception e) {
			throw e;
		}

		return list;
	}

	@Override
	public List<Object[]> get_employee_list(long div, int loc, String stdt, String endt, String fstaff_id, int cfaLoc,
			String desg_id, String fstaffId_o, String deleted_inv, String emp_id1, String deptloc, String finyearflag,
			String year) throws Exception {

		List<Object[]> list = null;
		StringBuffer temp = new StringBuffer();
		EntityManager em = null;
		try {

			System.out.println("finyearflag :: " + finyearflag + "  year :: " + year);
			System.out.println("stdt :: " + stdt + "  endt :: " + endt);

			em = emf.createEntityManager();
			if (desg_id.equalsIgnoreCase("Y")) {

				if (div == 0) {
					StringBuffer qryString = null;
					if (finyearflag.equals("CURRENT")) {
						System.out.println("Inside If");
						qryString = new StringBuffer(
								"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
								.append(" from Dispatch_header dh , fieldstaff f, location l")
								.append(" where dh.DSP_DIV_ID in ")
								.append("(select div_id from  DIV_MASTER where div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id= '")
								.append(emp_id1);
					} else {
						qryString = new StringBuffer(
								"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
								.append(" from Dispatch_header_arc dh , fieldstaff f, location l")
								.append(" where dh.DSP_DIV_ID in ")
								.append("(select div_id from  DIV_MASTER where div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id= '")
								.append(emp_id1);
					}
					qryString.append("')) and dh.DSP_status = '").append(deleted_inv)
							.append("' and dh.dsp_fin_year = '").append(year).append("' ")
							.append(" and dh.DSP_FSTAFF_ID = f.FSTAFF_ID").append(" and f.FSTAFF_DESIG =CASE WHEN '")
							.append(fstaffId_o).append("'='0' THEN FSTAFF_DESIG ELSE '").append(fstaffId_o)
							.append("' END ").append(" and CONVERT(date,DH.DSP_DT,105) >=CONVERT(date,'").append(stdt)
							.append("',105) ").append(" and CONVERT(date,DH.DSP_DT,105) <=CONVERT(date,'").append(endt)
							.append("',105) ");

					if (!deptloc.trim().equals("0")) {
						for (int i = 0; i < deptloc.length(); i = i + 4) {
							temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
						}
						if (temp.length() > 0) {
							temp.deleteCharAt(temp.length() - 1);
						}
						if (cfaLoc == 0) {
							qryString.append(" and dh.DSP_RECVG_LOC_ID in (").append(temp.toString()).append(")");

						} else {
							qryString.append(" and dh.DSP_RECVG_LOC_ID =").append(cfaLoc);
						}
					} else {
						qryString.append(" and dh.DSP_RECVG_LOC_ID = CASE WHEN ").append(cfaLoc)
								.append("=0 THEN dh.DSP_RECVG_LOC_ID ELSE ").append(cfaLoc).append(" END");
					}

					qryString.append(" and dh.DSP_LOC_ID = l.loc_id ");
					qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(loc)
							.append("=0 THEN l.loc_SubComp_id ELSE ").append(loc).append(" END")
							.append(" group by dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
							.append(" order by dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME");
					System.out.println("query getDetailsByPso : " + qryString);
					Query query = em.createNativeQuery(qryString.toString());
					list = query.getResultList();

					System.out.println("list :: " + list.size());

				} else {

					System.out.println("Inside  Else");
					StringBuffer qryString = null;
					if (finyearflag.equals("CURRENT")) {
						qryString = new StringBuffer(
								"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
								.append(" from Dispatch_header dh , fieldstaff f, location l")
								.append(" where dh.DSP_DIV_ID = ").append(div);
					} else {
						qryString = new StringBuffer(
								"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
								.append(" from Dispatch_header_arc dh , fieldstaff f, location l")
								.append(" where dh.DSP_DIV_ID = ").append(div);
					}
					qryString.append(" and dh.DSP_status = '").append(deleted_inv).append("' and dh.dsp_fin_year = '")
							.append(year).append("' ").append(" and dh.DSP_FSTAFF_ID = f.FSTAFF_ID")
							.append(" and f.FSTAFF_DESIG =CASE WHEN '").append(fstaffId_o)
							.append("'='0' THEN FSTAFF_DESIG ELSE '").append(fstaffId_o).append("' END ")

							.append(" and CONVERT(date,DH.DSP_DT,105) >=CONVERT(date,'").append(stdt).append("',105) ")
							.append(" and CONVERT(date,DH.DSP_DT,105) <=CONVERT(date,'").append(endt).append("',105) ");

					if (!deptloc.trim().equals("0")) {
						for (int i = 0; i < deptloc.length(); i = i + 4) {
							temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
						}
						if (temp.length() > 0) {
							temp.deleteCharAt(temp.length() - 1);
						}
						if (cfaLoc == 0) {
							qryString.append(" and dh.DSP_RECVG_LOC_ID in (").append(temp.toString()).append(")");

						} else {
							qryString.append(" and dh.DSP_RECVG_LOC_ID =").append(cfaLoc);
						}
					} else {
						qryString.append(" and dh.DSP_RECVG_LOC_ID = CASE WHEN ").append(cfaLoc)
								.append("=0 THEN dh.DSP_RECVG_LOC_ID ELSE ").append(cfaLoc).append(" END");
					}
					qryString.append(" and dh.DSP_LOC_ID = l.loc_id ");
					qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(loc)
							.append("=0 THEN l.loc_SubComp_id ELSE ").append(loc).append(" END")

							.append(" group by dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
							.append(" order by dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME");
					System.out.println("query getDetailsByPso : " + qryString);
					Query query = em.createNativeQuery(qryString.toString());
					list = query.getResultList();

					System.out.println("list :: " + list.size());

				}

			} else {
				if (div == 0) {
					StringBuffer qryString = null;
					if (finyearflag.equals("CURRENT")) {
						qryString = new StringBuffer(
								"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
								.append(" from Dispatch_header dh , fieldstaff f, location l")
								.append(" where dh.DSP_DIV_ID in ")
								.append("(select div_id from  DIV_MASTER where div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id= '")
								.append(emp_id1);
					} else {
						qryString = new StringBuffer(
								"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
								.append(" from Dispatch_header_arc dh , fieldstaff f, location l")
								.append(" where dh.DSP_DIV_ID in ")
								.append("(select div_id from  DIV_MASTER where div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id= '")
								.append(emp_id1);
					}
					qryString.append("')) and dh.DSP_status = '").append(deleted_inv)
							.append("' and dh.dsp_fin_year = '").append(year).append("' ")
							.append(" and dh.DSP_FSTAFF_ID = f.FSTAFF_ID")
							.append(" and f.FSTAFF_LEVEL_CODE =CASE WHEN ").append(fstaff_id)
							.append("=0 THEN FSTAFF_LEVEL_CODE ELSE ").append(fstaff_id).append(" END ")

							.append(" and CONVERT(date,DH.DSP_DT,105) >=CONVERT(date,'").append(stdt).append("',105) ")
							.append(" and CONVERT(date,DH.DSP_DT,105) <=CONVERT(date,'").append(endt).append("',105) ");

					if (!deptloc.trim().equals("0")) {
						for (int i = 0; i < deptloc.length(); i = i + 4) {
							temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
						}
						if (temp.length() > 0) {
							temp.deleteCharAt(temp.length() - 1);
						}
						if (cfaLoc == 0) {
							qryString.append(" and dh.DSP_RECVG_LOC_ID in (").append(temp.toString()).append(")");

						} else {
							qryString.append(" and dh.DSP_RECVG_LOC_ID =").append(cfaLoc);
						}
					} else {
						qryString.append(" and dh.DSP_RECVG_LOC_ID = CASE WHEN ").append(cfaLoc)
								.append("=0 THEN dh.DSP_RECVG_LOC_ID ELSE ").append(cfaLoc).append(" END");
					}
					qryString.append(" and dh.DSP_LOC_ID = l.loc_id ");
					qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(loc)
							.append("=0 THEN l.loc_SubComp_id ELSE ").append(loc).append(" END")

							.append(" group by dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
							.append(" order by dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME");
					System.out.println("query getDetailsByPso : " + qryString);
					Query query = em.createNativeQuery(qryString.toString());
					list = query.getResultList();

					System.out.println("list :: " + list.size());

				} else {
					StringBuffer qryString = null;
					if (finyearflag.equals("CURRENT")) {
						qryString = new StringBuffer(
								"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
								.append(" from Dispatch_header dh , fieldstaff f, location l")
								.append(" where dh.DSP_DIV_ID = ").append(div);
					} else {
						qryString = new StringBuffer(
								"select dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
								.append(" from Dispatch_header_arc dh , fieldstaff f, location l")
								.append(" where dh.DSP_DIV_ID = ").append(div);
					}
					qryString.append(" and dh.DSP_status = '").append(deleted_inv).append("' and dh.dsp_fin_year = '")
							.append(year).append("' ").append(" and dh.DSP_FSTAFF_ID = f.FSTAFF_ID")
							.append(" and f.FSTAFF_LEVEL_CODE =CASE WHEN ").append(fstaff_id)
							.append("=0 THEN FSTAFF_LEVEL_CODE ELSE ").append(fstaff_id).append(" END ")

							.append(" and CONVERT(date,DH.DSP_DT,105) >=CONVERT(date,'").append(stdt).append("',105) ")
							.append(" and CONVERT(date,DH.DSP_DT,105) <=CONVERT(date,'").append(endt).append("',105) ");

					if (!deptloc.trim().equals("0")) {
						for (int i = 0; i < deptloc.length(); i = i + 4) {
							temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
						}
						if (temp.length() > 0) {
							temp.deleteCharAt(temp.length() - 1);
						}
						if (cfaLoc == 0) {
							qryString.append(" and dh.DSP_RECVG_LOC_ID in (").append(temp.toString()).append(")");

						} else {
							qryString.append(" and dh.DSP_RECVG_LOC_ID =").append(cfaLoc);
						}
					} else {
						qryString.append(" and dh.DSP_RECVG_LOC_ID = CASE WHEN ").append(cfaLoc)
								.append("=0 THEN dh.DSP_RECVG_LOC_ID ELSE ").append(cfaLoc).append(" END");
					}
					qryString.append(" and dh.DSP_LOC_ID = l.loc_id ");
					qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(loc)
							.append("=0 THEN l.loc_SubComp_id ELSE ").append(loc).append(" END")

							.append(" group by dh.DSP_FSTAFF_ID,dh.DSPFSTAFF_EMPLOYEENO,dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME")
							.append(" order by dh.DSPFSTAFF_NAME,dh.DSPFSTAFF_DISPLAYNAME");
					System.out.println("query getDetailsByPso : " + qryString);
					Query query = em.createNativeQuery(qryString.toString());
					list = query.getResultList();

					System.out.println("list :: " + list.size());
				}

			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<Object[]> getDetailsByFstaffDesg(long div, int loc, String stdt, String endt, int cfaLoc,
			String desg_id, String deleted_inv, String emp_id, String deptloc) throws Exception {

		List<Object[]> list = null;
		StringBuffer temp = new StringBuffer();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();

			System.out.println("stdt :: " + stdt + "  endt :: " + endt);

			if (div == 0) {

				StringBuffer qryString = new StringBuffer("select f.fstaff_level_code,f.fstaff_desig")
						.append(" from Dispatch_header dh , fieldstaff f, location l where dh.DSP_DIV_ID in ")
						.append("(select ediv_div_id from am_m_emp_div_access where ediv_emp_id= '").append(emp_id)

						.append("') and dh.DSP_status = '").append(deleted_inv)
						.append("' and dh.DSP_FSTAFF_ID = f.FSTAFF_ID ")

						.append(" and CONVERT(date,DH.DSP_DT,105) >=CONVERT(date,'").append(stdt).append("',105) ")
						.append(" and CONVERT(date,DH.DSP_DT,105) <=CONVERT(date,'").append(endt).append("',105) ");

				if (!deptloc.trim().equals("0")) {
					for (int i = 0; i < deptloc.length(); i = i + 4) {
						temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
					}
					if (temp.length() > 0) {
						temp.deleteCharAt(temp.length() - 1);
					}
					if (cfaLoc == 0) {
						qryString.append(" and dh.DSP_RECVG_LOC_ID in (").append(temp.toString()).append(")");

					} else {
						qryString.append(" and dh.DSP_RECVG_LOC_ID =").append(cfaLoc);
					}
				} else {
					qryString.append(" and dh.DSP_RECVG_LOC_ID = CASE WHEN ").append(cfaLoc)
							.append("=0 THEN dh.DSP_RECVG_LOC_ID ELSE ").append(cfaLoc).append(" END");
				}

				qryString.append(" and dh.DSP_LOC_ID = l.loc_id ");
				qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(loc);
				qryString.append("=0 THEN l.loc_SubComp_id ELSE ").append(loc).append(" END");

				qryString.append(" group by f.fstaff_level_code,f.fstaff_desig ");
				qryString.append(" order by f.fstaff_level_code,f.fstaff_desig  ");

				System.out.println("query getDetailsByFstaffDesg : " + qryString.toString());
				Query query = em.createNativeQuery(qryString.toString());
				list = query.getResultList();

				System.out.println("list :: " + list.size());

			} else {

				StringBuffer qryString = new StringBuffer("select f.fstaff_level_code,f.fstaff_desig")
						.append(" from Dispatch_header dh , fieldstaff f, location l where dh.DSP_DIV_ID = ")
						.append(div)

						.append(" and dh.DSP_status = '").append(deleted_inv)
						.append("' and dh.DSP_FSTAFF_ID = f.FSTAFF_ID ")

						/*
						 * .append(" and f.FSTAFF_LEVEL_CODE =CASE WHEN " ).append (fstaff_id).append(
						 * "=0 THEN FSTAFF_LEVEL_CODE ELSE ") .append(fstaff_id) .append(" END")
						 */
						.append(" and CONVERT(date,DH.DSP_DT,105) >=CONVERT(date,'").append(stdt).append("',105) ")
						.append(" and CONVERT(date,DH.DSP_DT,105) <=CONVERT(date,'").append(endt).append("',105) ");

				if (!deptloc.trim().equals("0")) {
					for (int i = 0; i < deptloc.length(); i = i + 4) {
						temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
					}
					if (temp.length() > 0) {
						temp.deleteCharAt(temp.length() - 1);
					}
					if (cfaLoc == 0) {
						qryString.append(" and dh.DSP_RECVG_LOC_ID in (").append(temp.toString()).append(")");

					} else {
						qryString.append(" and dh.DSP_RECVG_LOC_ID =").append(cfaLoc);
					}
				} else {
					qryString.append(" and dh.DSP_RECVG_LOC_ID = CASE WHEN ").append(cfaLoc)
							.append("=0 THEN dh.DSP_RECVG_LOC_ID ELSE ").append(cfaLoc).append(" END");
				}
				qryString.append(" and dh.DSP_LOC_ID = l.loc_id ");
				qryString.append(" and l.loc_SubComp_id = CASE WHEN ").append(loc)
						.append("=0 THEN l.loc_SubComp_id ELSE ").append(loc).append(" END");
				qryString

						.append(" group by f.fstaff_level_code,f.fstaff_desig ")
						.append(" order by f.fstaff_level_code,f.fstaff_desig  ");
				System.out.println("query getDetailsByFstaffDesg : " + qryString);
				Query query = em.createNativeQuery(qryString.toString());
				list = query.getResultList();
				System.out.println("list :: " + list.size());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	// Pivot Pack Slip (23may2020)
	@Override
	public Map<Integer, List<Object>> getPivotData(String div, String loc, String recloc, String stdt, String endt) {

		EntityManager em = null;

		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		List<Object> countResult = new ArrayList<>();

		em = emf.createEntityManager();
		Session session = em.unwrap(Session.class);
		try {
			session.doWork(connection -> {
				try (

						CallableStatement stmt = connection.prepareCall("EXEC dg_pivot_packing_slip ?,?,?,?,?");) {
					String recLoc = recloc;
					if (recLoc.indexOf('0') >= 0) {
						recLoc = "0";
					}
					try {

						stmt.setString(1, div);
						stmt.setString(2, loc);
						stmt.setString(3, recLoc);
						stmt.setString(4, stdt);
						stmt.setString(5, endt);

						/*
						 * stmt.setLong(1, 49); stmt.setLong(2, 1); stmt.setString(3, "1,2,3");
						 * stmt.setString(4, "11-09-2017"); stmt.setString(5, "11-09-2019");
						 */

						boolean isResultSet = stmt.execute();
						if (isResultSet) {
							ResultSet res = stmt.getResultSet();

							while (res.next()) {
								countResult.add(res.getObject(1));
							}
						}
						map.put(1, countResult);
						isResultSet = stmt.getMoreResults();
						ResultSetMetaData rsmd = null;
						if (isResultSet) {
							ResultSet rs = stmt.getResultSet();
							rsmd = rs.getMetaData();
							List<Object> result = new ArrayList<>();
							if (rs != null) {
								result = new ArrayList<Object>();
								Object[] colName = new Object[rsmd.getColumnCount()];
								for (int i = 1, j = 0; i <= rsmd.getColumnCount(); i++, j++) {
									colName[j] = rsmd.getColumnName(i);
								}
								result.add(colName);
								while (rs.next()) {
									Object[] obj = new Object[rsmd.getColumnCount()];
									for (int i = 1, j = 0; i <= rsmd.getColumnCount(); i++, j++) {
										obj[j] = rs.getObject(i);
									}
									result.add(obj);
								}
								map.put(2, result);
							}
						}
						List<Object> result2 = new ArrayList<>();
						isResultSet = stmt.getMoreResults();
						if (isResultSet) {
							ResultSet rs2 = stmt.getResultSet();

							rsmd = rs2.getMetaData();
							result2 = new ArrayList<Object>();
							while (rs2.next()) {
								Object[] obj = new Object[rsmd.getColumnCount()];
								for (int i = 1, j = 0; i <= rsmd.getColumnCount(); i++, j++) {
									obj[j] = rs2.getObject(i);
								}
								result2.add(obj);
							}
						}
						map.put(3, result2);
					} finally {
						connection.close();
					}
				}
			});

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (session != null) {
				session.close();
			}
			if (em != null) {
				em.close();
			}

		}
		return map;

	}

	@Override
	public List<SmpItem> getActiveItemBy_divId(String divid) throws Exception {
		EntityManager em = null;
		List<SmpItem> itemList = new ArrayList<SmpItem>();
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select smp_prod_id,smp_prod_name from SMPITEM where SMP_status = 'A' and SMP_STD_DIV_ID =:divid order by smp_prod_name");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divid", divid);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SmpItem item = new SmpItem();

					item.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class).toString()));
					item.setSmp_prod_name(t.get("smp_prod_name", String.class));

					itemList.add(item);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return itemList;
	}

	@Override
	public List<Productwise_batchwise_locationwise_stock> getproductwisebatchwiselocationwisereportdata(ReportBean bean)
			throws Exception {

		EntityManager em = null;
		List<Productwise_batchwise_locationwise_stock> list = null;
		try {
			em = emf.createEntityManager();

			System.out.println("bean.getProduct() :: " + bean.getProduct());

			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("call_PROD_BATCH_LOC_STOCK");
			procedurecall.setParameter("prod_id", bean.getProduct());

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<Annual_sample_plan_view_report> getDataforannualplanview(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Annual_sample_plan_view_report> list = null;

		try {

			StringBuffer teaminfo = new StringBuffer();
			for (String a : bean.getTeaminfo()) {
				teaminfo.append(a + ",");
			}

			System.out.println("teaminfo :: " + teaminfo.toString());

			String team = teaminfo.toString().substring(0, teaminfo.toString().length() - 1);
			System.out.println("teaminfo :: " + team);

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("call_annual_sample_plan_view_report");

			procedurecall.setParameter("FINYEAR", bean.getFinyear());
			procedurecall.setParameter("ASP_DIVID", team);
			procedurecall.setParameter("ASP_BRAND_ID", "");
			procedurecall.setParameter("ASP_PROD_ID", "");
			procedurecall.setParameter("PEMP_ID", bean.getEmpid());

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getBrandsForTeam(String empId, List<String> teamId, String planType) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		System.out.println("EMPID" + empId + " teamId" + teamId + " planType" + planType);
		try {

			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME ");
			sb.append(" from am_m_login_detail lg , USER_SKU_MAP us , SAPRODGRP sg , SMPITEM P ");
			sb.append(
					" where lg.ld_emp_cb_id =:empId and p.PROD_SUB_TYPE_ID=:prod_sub_type_id and lg.ld_id = us.USERID ");
			sb.append(" and us.SMP_SA_PROD_GROUP = sg.SA_PROD_GROUP AND us.SMP_SA_PROD_GROUP = P.SMP_SA_PROD_GROUP ");
			sb.append(
					" AND (P.SMP_STD_DIV_ID IN (select prod_div_id from divmap where base_div_id in (:teamId) ) OR P.SMP_STD_DIV_ID in (:teamId)) group by us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME order by sg.SA_GROUP_NAME");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", empId);
			query.setParameter("teamId", teamId);
			query.setParameter("prod_sub_type_id", planType);
			List<Tuple> tuples = query.getResultList();

			System.out.println("List:: " + tuples.size());

			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_sa_prod_group(Long.valueOf(t.get("SMP_SA_PROD_GROUP", Integer.class)));
					object.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<SmpItem> getprodlistbybrands(List<String> teamId, String planType, List<String> brand_Id)
			throws Exception {

		EntityManager em = null;
		List<SmpItem> list = new ArrayList<>();
		SmpItem object = null;
		// System.out.println("EMPID"+empId+" teamId"+teamId+" planType"+planType);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT smp_prod_id,SMP_PROD_NAME from smpitem where ");
			sb.append("SMP_SA_PROD_GROUP in (:brand_id)");
			sb.append("AND SMP_STD_DIV_ID IN (:team_id)");
			sb.append("AND PROD_SUB_TYPE_ID=:sub_type");
			sb.append(" AND SMP_STATUS='A'");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("brand_id", brand_Id);
			query.setParameter("team_id", teamId);
			query.setParameter("sub_type", planType);
			List<Tuple> tuples = query.getResultList();

			System.out.println("List:: " + tuples.size());

			if (tuples != null && !tuples.isEmpty()) {

				for (Tuple t : tuples) {
					object = new SmpItem();
					object.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					object.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					list.add(object);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<Allocation_download> get_allocation_downloadexceldata(String pidiv_id, String pvuserid,
			String pfin_year, String pperiod_code) {

		List<Allocation_download> list = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("callALLOC_MTHLY_REPORT");
			procedurecall.setParameter("pidiv_id", Integer.parseInt(pidiv_id));
			procedurecall.setParameter("pvuserid", pvuserid);
			procedurecall.setParameter("pfin_year", pfin_year);
			procedurecall.setParameter("pperiod_code", pperiod_code);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		}

		return list;
	}

	@Override
	public List<StockAgeingReportView> getstockageingreportData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<StockAgeingReportView> list = new ArrayList<>();

		try {

			System.out.println("bean.getLocId() : " + bean.getLocId());
			System.out.println("bean.getDivId() : " + bean.getDivId());
			System.out.println("startdt " + MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()));
			System.out.println("enddt " + MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			System.out.println("bean.getUserId() : " + bean.getUserId());
			System.out.println("bean.getProd_id() : " + bean.getProd_id());

			System.out.println("bean.getSlab1a() : " + bean.getSlab1a());
			System.out.println("bean.getSlab1b() : " + bean.getSlab1b());
			System.out.println("bean.getSlab2a() : " + bean.getSlab2a());
			System.out.println("bean.getSlab2b() : " + bean.getSlab2b());
			System.out.println("bean.getSlab3a() : " + bean.getSlab3a());
			System.out.println("bean.getSlab3b() : " + bean.getSlab3b());
			System.out.println("bean.getSlab4a() : " + bean.getSlab4a());
			System.out.println("bean.getSlab4b() : " + bean.getSlab4b());
			System.out.println("bean.getSlab5a() : " + bean.getSlab5a());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("call_STOCK_AGEING_REPORT");

			procedurecall.setParameter("piloc_id", bean.getLocId());
			procedurecall.setParameter("pidiv_id", bean.getDivId());
			procedurecall.setParameter("pvfrmdt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()));
			procedurecall.setParameter("pvtodt", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			procedurecall.setParameter("pvuserid", bean.getUserId());
			procedurecall.setParameter("piProdId", bean.getProd_id());

			procedurecall.setParameter("slab1a", bean.getSlab1a());
			procedurecall.setParameter("slab1b", bean.getSlab1b());
			procedurecall.setParameter("slab2a", bean.getSlab2a());
			procedurecall.setParameter("slab2b", bean.getSlab2b());
			procedurecall.setParameter("slab3a", bean.getSlab3a());
			procedurecall.setParameter("slab3b", bean.getSlab3b());
			procedurecall.setParameter("slab4a", bean.getSlab4a());
			procedurecall.setParameter("slab4b", bean.getSlab4b());
			procedurecall.setParameter("slab5a", bean.getSlab5a());

			list = procedurecall.getResultList();

			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<StockAgeingReportView> getstockageingreportbyExpirydaysData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub

		EntityManager em = null;
		List<StockAgeingReportView> list = new ArrayList<>();

		try {

			System.out.println("bean.getLocId() : " + bean.getLocId());
			System.out.println("bean.getDivId() : " + bean.getDivId());
			System.out.println("startdt " + MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()));
			System.out.println("enddt " + MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			System.out.println("bean.getUserId() : " + bean.getUserId());
			System.out.println("bean.getProd_id() : " + bean.getProd_id());
			System.out.println("near Expiry : " + bean.getSlab1b());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("call_STOCK_AGEING_REPORT_EXPIRY_DAYS");

			procedurecall.setParameter("piloc_id", bean.getLocId());
			procedurecall.setParameter("pidiv_id", bean.getDivId());
			procedurecall.setParameter("pvfrmdt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()));
			procedurecall.setParameter("pvtodt", MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			procedurecall.setParameter("pvuserid", bean.getUserId());
			procedurecall.setParameter("piProdId", bean.getProd_id());

			procedurecall.setParameter("slab1a", "0");
			procedurecall.setParameter("slab1b", bean.getSlab1b());
			procedurecall.setParameter("slab2a", "0");
			procedurecall.setParameter("slab2b", "0");
			procedurecall.setParameter("slab3a", "0");
			procedurecall.setParameter("slab3b", "0");
			procedurecall.setParameter("slab4a", "0");
			procedurecall.setParameter("slab4b", "0");
			procedurecall.setParameter("slab5a", "0");

			list = procedurecall.getResultList();

			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<DivMaster> getdivforstockageing(String userid) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<DivMaster> list = new ArrayList<>();
		DivMaster div = null;

		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select div_code,div_disp_nm,div_id,div_code_nm,div_status from  DIV_MASTER");
			sb.append(
					" where div_id in (select ediv_div_id from am_m_emp_div_access where ediv_emp_id=:userId  and ediv_status='A') ");
			sb.append("AND DIV_status = 'A' "); // changed
			sb.append(" order by div_disp_nm ");

			em = emf.createEntityManager();
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("userId", userid);

			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {

				for (Tuple t : tuples) {
					div = new DivMaster();
					div.setDiv_code(t.get("div_code", String.class));
					div.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					div.setDiv_id(Long.valueOf(t.get("div_id", Integer.class).toString()));
					div.setDiv_code_nm(t.get("div_code_nm", String.class));
					div.setDiv_status(t.get("div_status", Character.class).toString());
					list.add(div);
				}
			}

			System.out.println("Divlist :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<Location> getlocationforstockageing() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Location> list = new ArrayList<>();
		Location loc = null;

		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select loc_id,loc_nm from  location");
			sb.append(" where loc_status='A' ");
			sb.append(" order by loc_nm ");

			em = emf.createEntityManager();
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {

				for (Tuple t : tuples) {
					loc = new Location();
					loc.setLoc_id(Long.valueOf(t.get("loc_id", Integer.class)));
					loc.setLoc_nm(t.get("loc_nm", String.class));
					list.add(loc);
				}
			}

			System.out.println("loclist :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<SG_d_parameters_details> getslabdetails(String para_type) throws Exception {
		// TODO Auto-generated method stub

		EntityManager em = null;
		List<SG_d_parameters_details> list = new ArrayList<>();
		SG_d_parameters_details dtl = new SG_d_parameters_details();
		Location loc = null;

		try {

			StringBuffer sb = new StringBuffer();
			sb.append("select SGprmdet_id, SGprmdet_Text1, SGprmdet_Text2,");
			sb.append(" SGprmdet_Num1 , SGprmdet_short_nm , SGprmdet_nm, ");
			sb.append(" SGprmdet_disp_nm  from SG_d_parameters_details  where SGprmdet_status='A'");
			sb.append(" and SGprmdet_SGprm_id = (select SGprm_id  from SG_m_parameters where SGprm_nm=:paratype)");
			sb.append(" order by SGprmdet_disp_nm asc");

			em = emf.createEntityManager();
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("paratype", para_type);
			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {

				for (Tuple t : tuples) {
					dtl = new SG_d_parameters_details();
					// SGprmdet_id
					dtl.setSgprmdet_text1((t.get("SGprmdet_Text1", String.class)));
					dtl.setSgprmdet_text2((t.get("SGprmdet_Text2", String.class)));
					dtl.setSgprmdet_short_nm((t.get("SGprmdet_short_nm", String.class)));
					dtl.setSgprmdet_nm((t.get("SGprmdet_nm", String.class)));
					dtl.setSgprmdet_disp_nm((t.get("SGprmdet_nm", String.class)));

					list.add(dtl);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<SmpItem> getprodbydivforageing(String divid) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SmpItem> list = new ArrayList<>();
		SmpItem item = null;

		System.out.println("divid :: " + divid);

		StringBuffer sb = new StringBuffer();
		sb.append(" select smp_prod_name,smp_prod_id from smpitem");
		sb.append(" where smp_status='A'");

		if (!divid.equalsIgnoreCase("0")) {
			sb.append(" and smp_std_div_id=:divid ");
		}
		sb.append(" order by smp_prod_name asc");

		try {
			em = emf.createEntityManager();
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);

			if (!divid.equalsIgnoreCase("0")) {
				query.setParameter("divid", divid);
			}

			List<Tuple> tuples = query.getResultList();
			System.out.println("List :: " + tuples.size());

			if (tuples != null && !tuples.isEmpty()) {

				for (Tuple t : tuples) {
					item = new SmpItem();
					// SGprmdet_id
					item.setSmp_prod_name((t.get("smp_prod_name", String.class)));
					item.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class).toString()));

					list.add(item);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<DispatchIntimationEmail> getDispatchIntimationEmail(String stdate, String endate) {
		List<DispatchIntimationEmail> list = null;
		EntityManager em = null;
		System.out.println("stdateDA0--->" + stdate);
		System.out.println("endateDA0--->" + endate);
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery callDispatchintimation = em
					.createNamedStoredProcedureQuery("callDISPATCH_INTIMATION_EMAIL_REPORT");
			callDispatchintimation.setParameter("dspmod_from_dt", stdate);
			callDispatchintimation.setParameter("dspmod_to_dt", endate);
			list = callDispatchintimation.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return list;
	}

	@Override
	public List<Allocation_report_1> getdatafor_allocation_report1(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<Allocation_report_1> lst = null;
		EntityManager em = null;
		try {

			System.out.println("bean.getDivId() :: " + bean.getDivId());
			System.out.println("bean.getFin_year() :: " + bean.getFin_year());
			System.out.println("bean.getPperiod_code() :: " + bean.getPperiod_code());
			System.out.println("bean.getSubCompId() :: " + bean.getSubCompId());
			System.out.println("bean.getPprod_sub_type_id() :: " + bean.getPprod_sub_type_id());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("Allocation_report_1");

			procedurecall.setParameter("pdiv_id", bean.getDivId());
			procedurecall.setParameter("pfin_year", bean.getFin_year());
			procedurecall.setParameter("pperiod_code", bean.getPperiod_code());
			procedurecall.setParameter("psub_comp_id", bean.getSubCompId());
			procedurecall.setParameter("pprod_sub_type_id", bean.getPprod_sub_type_id());

			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<Allocation_report_2> getdatafor_allocation_report2(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<Allocation_report_2> lst = null;
		EntityManager em = null;
		try {

			System.out.println("bean.getDivId() :: " + bean.getDivId());
			System.out.println("bean.getFin_year() :: " + bean.getFin_year());
			System.out.println("bean.getPperiod_code() :: " + bean.getPperiod_code());
			System.out.println("bean.getSubCompId() :: " + bean.getSubCompId());
			System.out.println("bean.getPprod_sub_type_id() :: " + bean.getPprod_sub_type_id());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("Allocation_report_2");

			procedurecall.setParameter("pdiv_id", bean.getDivId());
			procedurecall.setParameter("pfin_year", bean.getFin_year());
			procedurecall.setParameter("pperiod_code", bean.getPperiod_code());
			procedurecall.setParameter("psub_comp_id", bean.getSubCompId());
			procedurecall.setParameter("pprod_sub_type_id", bean.getPprod_sub_type_id());

			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<Annual_sample_plan_view_report_batchwise> getDataforannualplanviewbatchwise(ReportBean bean)
			throws Exception {
		EntityManager em = null;
		List<Annual_sample_plan_view_report_batchwise> list = null;

		try {

			StringBuffer teaminfo = new StringBuffer();
			for (String a : bean.getTeaminfo()) {
				teaminfo.append(a + ",");
			}

			System.out.println("teaminfo :: " + teaminfo.toString());

			String team = teaminfo.toString().substring(0, teaminfo.toString().length() - 1);
			System.out.println("teaminfo :: " + team);

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("call_annual_sample_plan_view_report_batchwise");

			procedurecall.setParameter("FINYEAR", bean.getFinyear());
			procedurecall.setParameter("ASP_DIVID", team);
			procedurecall.setParameter("ASP_BRAND_ID", "");
			procedurecall.setParameter("ASP_PROD_ID", "");
			procedurecall.setParameter("PEMP_ID", bean.getEmpid());

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Annual_sample_plan_view_report_itemwise> getDataforannualplanviewitemwise(ReportBean bean)
			throws Exception {
		EntityManager em = null;
		List<Annual_sample_plan_view_report_itemwise> list = null;

		try {

			StringBuffer teaminfo = new StringBuffer();
			for (String a : bean.getTeaminfo()) {
				teaminfo.append(a + ",");
			}

			System.out.println("teaminfo :: " + teaminfo.toString());

			String team = teaminfo.toString().substring(0, teaminfo.toString().length() - 1);
			System.out.println("teaminfo :: " + team);

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("call_annual_sample_plan_view_report_itemwise");

			procedurecall.setParameter("FINYEAR", bean.getFinyear());
			procedurecall.setParameter("ASP_DIVID", team);
			procedurecall.setParameter("ASP_BRAND_ID", "");
			procedurecall.setParameter("ASP_PROD_ID", "");
			procedurecall.setParameter("PEMP_ID", bean.getEmpid());

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Annual_sample_plan_view_report_cons> getDataforannualplanviewcon(Character asp_type, String asp_id)
			throws Exception {
		EntityManager em = null;
		List<Annual_sample_plan_view_report_cons> list = null;
		try {
			StoredProcedureQuery procedurecall = entityManager
					.createNamedStoredProcedureQuery("call_annual_sample_plan_view_report_cons");

			procedurecall.setParameter("ASP_CONS_TYPE", asp_type);
			procedurecall.setParameter("ASP_ID", asp_id);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_alloc_monthwise_report> getDispatch_alloc_monthwise_report_data(ReportBean bean) {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Dispatch_alloc_monthwise_report> list = null;

		try {
			System.out.println("flg :: " + bean.getCurryearflg());
			System.out.println("yr :: " + bean.getFin_year());
			System.out.println("id :: " + bean.getEmp_id());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("calldispatch_alloc_month_wise_report");
			procedurecall.setParameter("curryearflg", bean.getCurryearflg());
			procedurecall.setParameter("finyear", bean.getFin_year());
			procedurecall.setParameter("pemp_id", bean.getEmp_id());
			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<SmpItem> getStockType(String empId) throws Exception {
		EntityManager em = null;
		List<SmpItem> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct smp_prod_type from smpitem ");
			// sb.append(" where smp_prod_type <> 'Gift' ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SmpItem smp = null;
				for (Tuple t : tuples) {
					smp = new SmpItem();
					smp.setSmp_prod_type(t.get("smp_prod_type", String.class));
					list.add(smp);
				}
				System.out.println("getStockType" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Object> getBrandsForTeam(String empId, String divId) throws Exception {
		EntityManager em = null;
		List<Object> list = null;

		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME ");
			sb.append(" from am_m_login_detail lg , USER_SKU_MAP us , SAPRODGRP sg , SMPITEM P ");
			sb.append(" where lg.ld_emp_cb_id =:empId  and lg.ld_id = us.USERID ");
			sb.append(" and us.SMP_SA_PROD_GROUP = sg.SA_PROD_GROUP AND us.SMP_SA_PROD_GROUP = P.SMP_SA_PROD_GROUP ");
			if (divId.equalsIgnoreCase("0")) {
				sb.append(" AND (P.SMP_STD_DIV_ID IN (select prod_div_id from divmap ) ");
				sb.append(
						" OR P.SMP_STD_DIV_ID= P.SMP_STD_DIV_ID) group by us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME order by sg.SA_GROUP_NAME ");
			} else {
				sb.append(" AND (P.SMP_STD_DIV_ID IN (select prod_div_id from divmap where base_div_id=:teamId ) ");
				sb.append(
						" OR P.SMP_STD_DIV_ID=:teamId) group by us.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME order by sg.SA_GROUP_NAME");
			}
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", empId);
			if (!divId.equalsIgnoreCase("0")) {
				query.setParameter("teamId", divId);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_sa_prod_group(Long.valueOf(t.get("SMP_SA_PROD_GROUP", Integer.class)));
					object.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Grn_quarantine_prod_summary> getGren_quarantine_summary_data(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Grn_quarantine_prod_summary> list = null;
		try {

			System.out.println("bean.getStartDate() :: " + bean.getStartDate());
			System.out.println("bean.getEndDate() :: " + bean.getEndDate());
			System.out.println("bean.getSendLocId() :: " + bean.getSendLocId());
			System.out.println("bean.getRecLocId() :: " + bean.getRecLocId());
			System.out.println("bean.getDivId() :: " + bean.getDivId());
			System.out.println("bean.getEmp_id() :: " + bean.getEmp_id());
			System.out.println("bean.getFinyearflg() :: " + bean.getFinyear());
			System.out.println("bean.getFinyearflag() :: " + bean.getFinyearflag());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("callgrndetailreport_quarantine_prod_summary");
			procedurecall.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()));
			procedurecall.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			procedurecall.setParameter("SEND_LOC", bean.getSendLocId());
			procedurecall.setParameter("RECV_LOC", bean.getRecLocId());
			procedurecall.setParameter("DIVID", bean.getDivId());
			procedurecall.setParameter("USER_ID", bean.getEmp_id());
			procedurecall.setParameter("fin_year_flag", bean.getFinyear());
			procedurecall.setParameter("FIN_YEAR_ID", bean.getFinyearflag());

//			 procedurecall.setParameter("startdate","01-03-2020");
//			 procedurecall.setParameter("endDate","31-12-2020");
//			 procedurecall.setParameter("SEND_LOC","0");
//			 procedurecall.setParameter("RECV_LOC","0");
//			 procedurecall.setParameter("DIVID","0");
//			 procedurecall.setParameter("USER_ID","E00040");
//			 procedurecall.setParameter("fin_year_flag","CURRENT");
//			 procedurecall.setParameter("FIN_YEAR_ID","2020");

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrnquarantineProd_Detail> getGRNQuarantineDetailData(ReportBean bean) throws Exception {
		List<GrnquarantineProd_Detail> list = null;
		EntityManager em = null;
		try {
			System.out.println("In call	");
			em = emf.createEntityManager();

			System.out.println("bean.getStartDate() :: " + bean.getStartDate());
			System.out.println("bean.getEndDate() :: " + bean.getEndDate());
			System.out.println("bean.getSendLocId() :: " + bean.getSendLocId());
			System.out.println("bean.getRecLocId() :: " + bean.getRecLocId());
			System.out.println("bean.getDivId() :: " + bean.getDivId());
			System.out.println("bean.getEmp_id() :: " + bean.getEmp_id());
			System.out.println("bean.getFinyearflg() :: " + bean.getFinyear());
			System.out.println("bean.getFinyearflag() :: " + bean.getFinyearflag());

			StoredProcedureQuery callMonthlyActivityReport = em
					.createNamedStoredProcedureQuery("GrnDetailReport_QUARANTINE");
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("SEND_LOC", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("RECV_LOC", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("DIVID", bean.getDivId());
			callMonthlyActivityReport.setParameter("USER_ID", bean.getEmp_id());
			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());

//			callMonthlyActivityReport.setParameter("startdate","01-03-2020");
//			callMonthlyActivityReport.setParameter("endDate", "31-12-2020");
//			callMonthlyActivityReport.setParameter("SEND_LOC", "0");
//			callMonthlyActivityReport.setParameter("RECV_LOC", "0");
//			callMonthlyActivityReport.setParameter("DIVID", "0");
//			callMonthlyActivityReport.setParameter("USER_ID", "E00040");
//			callMonthlyActivityReport.setParameter("fin_year_flag","CURRENT");
//			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", "2020");

			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of the GRNQuarantine_Detail Report list::::-" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;

		// TODO Auto-generated method stub
	}

	@Override
	public List<Lr_Expenses_Report> getLrExpensesReportdata(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<Lr_Expenses_Report> list = null;
		EntityManager em = null;
		try {

			StringBuffer divid = new StringBuffer();
			for (String a : bean.getDiv_id()) {
				divid.append(a + ",");
			}
			String div = divid.toString().substring(0, divid.toString().length() - 1);
			System.out.println("divid :: " + div);

			// StringBuffer locid = new StringBuffer();
			// for(String a: bean.getLoc_id()) {
			// locid.append(a+",");
			// }
			// String loc = locid.toString().substring(0,locid.toString().length()-1);
			// System.out.println("loc :: "+loc);

			StringBuffer trnptr = new StringBuffer();
			for (String a : bean.getTransporter()) {
				trnptr.append(a + ",");
			}
			String transptr = trnptr.toString().substring(0, trnptr.toString().length() - 1);
			System.out.println("transptr :: " + transptr);

			em = emf.createEntityManager();
			StoredProcedureQuery callprocedure = em.createNamedStoredProcedureQuery("call_LR_expenses_report");
			callprocedure.setParameter("piloc_id", bean.getLoc_id());
			callprocedure.setParameter("pidiv_id", div);
			callprocedure.setParameter("pvTransporter", transptr);
			callprocedure.setParameter("pvfrmdt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callprocedure.setParameter("pvtodt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));

			list = callprocedure.getResultList();
			System.out.println("List ::::-" + list.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public List<Sub_Company> getCompanyName(String sub_comp_id) throws Exception {
		// TODO Auto-generated method stub
		List<Sub_Company> list = null;
		EntityManager em = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select subcomp_nm from sub_company where subcomp_id =:subcom_id");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("subcom_id", sub_comp_id);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Sub_Company object = null;
				for (Tuple t : tuples) {
					object = new Sub_Company();

					object.setSubcomp_nm(t.get("subcomp_nm", String.class));
					list.add(object);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> gettransporterlist(String StartDate, String EndDate) throws Exception {
		// TODO Auto-generated method stub
		List<Dispatch_Header> list = null;
		try {

			System.out.println("Startdate :: " + StartDate);
			System.out.println("EndDate :: " + EndDate);

			StringBuffer sb = new StringBuffer();
			sb.append("select distinct dsp_transporter from dispatch_header ");
			sb.append("where dsp_lr_dt>=:startdate and dsp_lr_dt<=:enddate ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("startdate", StartDate);
			query.setParameter("enddate", EndDate);

			List<Tuple> tuples = query.getResultList();
			System.out.println("list :: " + tuples.size());
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Dispatch_Header object = null;
				for (Tuple t : tuples) {
					object = new Dispatch_Header();

					object.setDsp_transporter(t.get("dsp_transporter", String.class));
					list.add(object);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public List<LrcsvDownloadReportRevised> get_Lrcsv_revised_data(ReportBean bean, String tabl_ind, String cfa,
			String fsid, String fstype, String deptloc, String temp) throws Exception {
		EntityManager em = null;
		List<LrcsvDownloadReportRevised> list = null;
		try {
			em = emf.createEntityManager();

			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("call_LR_csv_upload_proc_report_revised");
			procedurecall.setParameter("startdt",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate()));
			procedurecall.setParameter("enddt", MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate()));
			procedurecall.setParameter("tabl_ind", tabl_ind);
			if (!deptloc.trim().equals("0") && cfa.equals("0")) {
				procedurecall.setParameter("cfa", temp.toString());
			} else {
				if (deptloc.trim().equals("0") && cfa.equals("0")) {
					procedurecall.setParameter("cfa", cfa);
				} else {
					procedurecall.setParameter("cfa", "(" + cfa + ")");
				}
			}
			procedurecall.setParameter("fsid", fsid);
			procedurecall.setParameter("fstype", fstype);

			System.out.println("startdt " + MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate()));
			System.out.println("enddt " + MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate()));
			System.out.println("tabl_ind " + tabl_ind);
			System.out.println("cfa " + cfa);
			System.out.println("fsid " + fsid);
			System.out.println("fstype " + fstype);

			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<Lrcsv_RevisedDownload> getLrcsvdownload_Revised_data(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			List<String> loc, List<String> div, List<String> dest, String cfa_to_stockist_ind) throws Exception {

		List<Lrcsv_RevisedDownload> list = null;
		String procedureToCall = "";
		EntityManager em = null;

		try {
			System.out.println("loc :: " + loc.size());
			System.out.println("div :: " + div.size());
			System.out.println("dest :: " + dest.size());

			StringBuffer locations = new StringBuffer();
			for (String a : loc) {
				locations.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String location = locations.toString().substring(0, locations.toString().length() - 1);
			System.out.println("locations :: " + location);

			StringBuffer divisions = new StringBuffer();
			for (String a : div) {
				divisions.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String division = divisions.toString().substring(0, divisions.toString().length() - 1);
			System.out.println("divisions :: " + division);

			StringBuffer destinations = new StringBuffer();
			for (String a : dest) {
				destinations.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String destination = destinations.toString().substring(0, destinations.toString().length() - 1);
			System.out.println("destination :: " + destination);

			tbl_ind = "A";
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			System.out.println("stock_at_cfa_ind::" + stock_at_cfa_ind);
			System.out.println("start" + strdate);
			System.out.println("start" + strdate);
//			 strdate = "2020/05/01";
//			 enddate = "2020/05/10";
			if (stk_at_cfa_option == null) {
				stk_at_cfa_option = "N";
			}
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			if (stk_at_cfa_option.equalsIgnoreCase("Y") && stock_at_cfa_ind.equalsIgnoreCase("Y")) {
				procedureToCall = "call_LR_csv_download_revised_stock_cfa";
			} else if (cfa_to_stockist_ind.equalsIgnoreCase("Y")) {
				procedureToCall = "call_LR_csv_revised_download_stockist";
			} else {
				procedureToCall = "call_LR_csv_revised_download";
			}

			System.out.println("tbl_ind::::" + tbl_ind + "::::strdate::::" + strdate + "::::enddate::::" + enddate
					+ "::::cfa::::" + cfa + "::::fsid::::" + fsid);

			em = emf.createEntityManager();

			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery(procedureToCall);
			procedurecall.setParameter("startdt", strdate);
			procedurecall.setParameter("enddt", enddate);
			procedurecall.setParameter("tabl_ind", tbl_ind);

			if (!deptloc.trim().equals("0") && cfa.equals("0")) {
				// System.out.println("INSIDE
				// IFFFFFFFFFFFF::::::::::::::"+temp.toString());
				procedurecall.setParameter("cfa", temp.toString());
			} else {

				if (deptloc.trim().equals("0") && cfa.equals("0")) {
					// System.out.println("INSIDE ELSEEEEEEEEEEE:::::::::::::"
					// + "(" + cfa + ")");
					procedurecall.setParameter("cfa", cfa);
				} else {
					procedurecall.setParameter("cfa", "(" + cfa + ")");
				}
			}
			procedurecall.setParameter("fsid", fsid);

			procedurecall.setParameter("div_id", division);
			procedurecall.setParameter("sumdisp_id", destination);
			procedurecall.setParameter("sumdsp_loc_id", location);
			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<DispatchSummaryDetailReport_Revised> callDisptachSumDetReportRevised(ReportBean bean) throws Exception {
		List<DispatchSummaryDetailReport_Revised> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
//			System.out.println();
//			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
//			callDisptachSumDetReport.setParameter("startdate", MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
//			callDisptachSumDetReport.setParameter("endDate", MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
//			callDisptachSumDetReport.setParameter("reptype", "S");
//			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
//			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
//			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
//			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
//			callDisptachSumDetReport.setParameter("field_level", "0");
//			callDisptachSumDetReport.setParameter("prod_id", "0");
//			callDisptachSumDetReport.setParameter("dsp_status", "A");
//			callDisptachSumDetReport.setParameter("cfa", "0");
//			callDisptachSumDetReport.setParameter("fsid", "0");
//			callDisptachSumDetReport.setParameter("desig", "N");
//			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
//			callDisptachSumDetReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());

			System.out.println("div_id " + bean.getDivId());
			System.out.println("startdate " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype " + "S");
			System.out.println("subcomp " + bean.getLocId());
			System.out.println("orderby " + bean.getOrderBy());
			System.out.println("tabl_ind " + bean.getReportFrom());
			System.out.println("user_id " + bean.getUserId());
			System.out.println("field_level " + "0");
			System.out.println("prod_id " + "0");
			System.out.println("dsp_status " + "A");
			System.out.println("cfa " + "0");
			System.out.println("fsid " + "0");
			System.out.println("desig " + "N");
			System.out.println("fin_year_flag " + bean.getFinyear());
			System.out.println("FIN_YEAR_ID " + bean.getFinyearflag());
			System.out.println("subTeamCode " + bean.getSub_team_code());
			System.out.println("lrno " + bean.getLrNum());
			System.out.println("lrdt " + bean.getLrDate());

			if (bean.getCompcd().trim().equals(PFZ)) {
				System.out.println("HH");
				StoredProcedureQuery callDisptachSumDetReport = em
						.createNamedStoredProcedureQuery("callDispatchSummaryDetailReportRevised");
				callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
				callDisptachSumDetReport.setParameter("startdate",
						MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
				callDisptachSumDetReport.setParameter("endDate",
						MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
				callDisptachSumDetReport.setParameter("reptype", "S");
				callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
				callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
				callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
				callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
				callDisptachSumDetReport.setParameter("field_level", "0");
				callDisptachSumDetReport.setParameter("prod_id", "0");
				callDisptachSumDetReport.setParameter("dsp_status", "A");
				callDisptachSumDetReport.setParameter("cfa", "0");
				callDisptachSumDetReport.setParameter("fsid", "0");
				callDisptachSumDetReport.setParameter("desig", "N");
				callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
				callDisptachSumDetReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());
				callDisptachSumDetReport.setParameter("TEAM_CODE", bean.getSub_team_code());
				// callDisptachSumDetReport.setParameter("lrno", bean.getLrNum());
				// callDisptachSumDetReport.setParameter("lrdt", bean.getLrDate());
				list = callDisptachSumDetReport.getResultList();
			}
			else {
				System.out.println("FFF");
				StoredProcedureQuery callDisptachSumDetReport = em
						.createNamedStoredProcedureQuery("callDispatchSummaryDetailReportRevisedPAL");
				callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
				callDisptachSumDetReport.setParameter("startdate",
						MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
				callDisptachSumDetReport.setParameter("endDate",
						MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
				callDisptachSumDetReport.setParameter("reptype", "S");
				callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
				callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
				callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
				callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
				callDisptachSumDetReport.setParameter("field_level", "0");
				callDisptachSumDetReport.setParameter("prod_id", "0");
				callDisptachSumDetReport.setParameter("dsp_status", "A");
				callDisptachSumDetReport.setParameter("cfa", "0");
				callDisptachSumDetReport.setParameter("fsid", "0");
				callDisptachSumDetReport.setParameter("desig", "N");
				callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
				callDisptachSumDetReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());
				callDisptachSumDetReport.setParameter("TEAM_CODE", bean.getSub_team_code());
				list = callDisptachSumDetReport.getResultList();
			}
			System.out.println("list size::::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<DispatchDetailReportRevised> callDisptachDetReportRevised(ReportBean bean) throws Exception {
		List<DispatchDetailReportRevised> list = null;
		EntityManager em = null;
		try {

			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag " + bean.getFinyear());
			System.out.println("fin_year_id " + bean.getFinyearflag());
			System.out.println("subTeamCode " + bean.getSub_team_code());
			System.out.println("Rate  " + bean.getRate());

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callDispatchDetailsrevised");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());
			callDisptachSumDetReport.setParameter("TEAM_CODE", bean.getSub_team_code());
			callDisptachSumDetReport.setParameter("RATE_OPTION", bean.getRate());
			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of dispatch details ::" + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<DispatchDetailReportRbmdm> callDisptachDetReportRmdm(ReportBean bean) throws Exception {
		List<DispatchDetailReportRbmdm> list = null;
		EntityManager em = null;
		try {

			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag" + bean.getFinyear());
			System.out.println("fin_year_id" + bean.getFinyearflag());
			System.out.println("field_level_O " + bean.getFstaff_id_o());
			System.out.println("rbm//dm " + bean.getRbmdm());
			System.out.println("rmdmfstaff_id " + bean.getRmdmfstaff_id());
			System.out.println("isself " + bean.getIsself());

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callDispatchDetailReportRbmdm");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());
			callDisptachSumDetReport.setParameter("rmdmmsr_flag", bean.getRbmdm());
			callDisptachSumDetReport.setParameter("rmdmmsr_fstaff_id", bean.getRmdmfstaff_id().toString());
			callDisptachSumDetReport.setParameter("isself", bean.getIsself());

			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of dispatch details ::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<DIV_TEAM_BRAND> getDivDataForTeamBrand(String userId, String divId) throws Exception {
		// TODO Auto-generated method stub
		List<DIV_TEAM_BRAND> list = null;
		try {
//			StringBuffer sb = new StringBuffer();
//			sb.append("select sa_prod_group_name from DIV_TEAM_BRAND ");
//			sb.append("where SA_ins_usr_id=:userId AND DIV_ID=:divId ");
//			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
//			query.setParameter("userId", userId);
//			query.setParameter("divId", divId);
//			
//			List<Tuple> tuples = query.getResultList();
//			System.out.println("list :: "+tuples.size());
//			if (tuples != null && !tuples.isEmpty()) {
//				list = new ArrayList<>();
//				DIV_TEAM_BRAND object = null;
//				for (Tuple t : tuples) {
//					object = new DIV_TEAM_BRAND();
//					
//					list.add(object);
//				}
//			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public void getDeleteDivDataForTeamBrand(String TeamId) {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from DIV_TEAM_BRAND where TEAM_ID=:TeamId");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("TeamId", TeamId);
			// query.setParameter("divId", divId);
			// query.setParameter("del_id", del_id);
			// System.out.println("deleteEmpDivAccess: "+buffer);
			query.executeUpdate();

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<StockWithdrawalReport> getStockwithdrawalReport(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<StockWithdrawalReport> list = null;
		EntityManager em = null;
		try {
			System.out.println("locid" + bean.getLoc_id());
			System.out
					.println("start date " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate " + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("rpt_typ " + bean.getReport_type());

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callStockWithdrawalReport");
			callDisptachSumDetReport.setParameter("loc_id", bean.getLoc_id());
			callDisptachSumDetReport.setParameter("frm_challan_date",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("to_challan_date",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("rep_type", bean.getReport_type());

			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of Stockwithdrawal  ::" + list.size());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;
	}

	@Override
	public List<Temp_fstaffmas_prx_error> getpforcerxfstafferrordata(Date startdt, Date enddt) throws Exception {
		// TODO Auto-generated method stub
		List<Temp_fstaffmas_prx_error> lst = null;
		EntityManager em = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			em = emf.createEntityManager();
			String stdt = sdf.format(startdt);
			String endt = sdf.format(enddt);

			System.out.println("stdt :: " + stdt);
			System.out.println("endt :: " + endt);

			StringBuffer sb = new StringBuffer();
			sb.append(
					"select convert( date,error_date ) error_date,prx_div_name , fstaff_level_code,fstaff_nt_id,date_time,");
			sb.append(" t.terr_code as terr_code,fstaff_name,fstaff_display_name,fstaff_employee_no,");
			sb.append(
					" fstaff_addr1,fstaff_addr2,fstaff_addr3,fstaff_addr4,fstaff_destination,fstaff_pincode,fstaff_mobile2,fstaff_pan_no,");
			sb.append(
					" fstaff_tel_no,fstaff_mobile,fstaff_email,fstaff_transporter,fstaff_resigned,fstaff_joining_date,fstaff_leaving_date,");
			sb.append(
					" fstaff_samp_disp_ind,mgr1_terr_code,mgr2_terr_code,mgr3_terr_code,mgr4_terr_code,mgr5_terr_code,");
			sb.append(" mgr6_terr_code,error_msg,PRX_ZIP_FILE_NAME,PRX_FILE_NAME");
			sb.append(" FROM Temp_fstaffmas_prx_error t");
			sb.append(" where convert( date , error_date ) >=:stdt");
			sb.append(" and convert( date , error_date ) <=:endt");
			sb.append(" order by prx_div_name,fstaff_level_code,fstaff_nt_id");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("stdt", startdt);
			query.setParameter("endt", enddt);

			List<Tuple> list = query.getResultList();

			if (list != null && !list.isEmpty()) {
				lst = new ArrayList<>();
				Temp_fstaffmas_prx_error object = null;
				for (Tuple t : list) {
					object = new Temp_fstaffmas_prx_error();

					object.setError_date(t.get("error_date", Date.class));
					object.setPrx_div_name(t.get("prx_div_name", String.class));
					object.setFstaff_level_code(t.get("fstaff_level_code", String.class));
					object.setFstaff_nt_id(t.get("fstaff_nt_id", String.class));
					object.setDate_time(t.get("date_time", Date.class));
					object.setTerr_code(t.get("terr_code", String.class));
					object.setFstaff_name(t.get("fstaff_name", String.class));
					object.setFstaff_display_name(t.get("fstaff_display_name", String.class));
					object.setFstaff_employee_no(t.get("fstaff_employee_no", String.class));
					object.setFstaff_addr1(t.get("fstaff_addr1", String.class));
					object.setFstaff_addr2(t.get("fstaff_addr2", String.class));
					object.setFstaff_addr3(t.get("fstaff_addr3", String.class));
					object.setFstaff_addr4(t.get("fstaff_addr4", String.class));
					object.setFstaff_destination(t.get("fstaff_destination", String.class));
					object.setFstaff_pincode(t.get("fstaff_pincode", String.class));
					object.setFstaff_mobile2(t.get("fstaff_mobile2", String.class));
					object.setFstaff_pan_no(t.get("fstaff_pan_no", String.class));
					object.setFstaff_tel_no(t.get("fstaff_tel_no", String.class));
					object.setFstaff_mobile(t.get("fstaff_mobile", String.class));
					object.setFstaff_email(t.get("fstaff_email", String.class));
					object.setFstaff_transporter(t.get("fstaff_transporter", String.class));
					object.setFstaff_resigned(t.get("fstaff_resigned", Character.class).toString());
					object.setFstaff_joining_date(t.get("fstaff_joining_date", Date.class));
					object.setFstaff_leaving_date(t.get("fstaff_leaving_date", Date.class));
					object.setFstaff_samp_disp_ind(t.get("fstaff_samp_disp_ind", Character.class).toString());
					object.setMgr1_terr_code(t.get("mgr1_terr_code", String.class));
					object.setMgr2_terr_code(t.get("mgr2_terr_code", String.class));
					object.setMgr3_terr_code(t.get("mgr3_terr_code", String.class));
					object.setMgr4_terr_code(t.get("mgr4_terr_code", String.class));
					object.setMgr5_terr_code(t.get("mgr5_terr_code", String.class));
					object.setMgr6_terr_code(t.get("mgr6_terr_code", String.class));
					object.setError_msg(t.get("error_msg", String.class));
					object.setPrx_zip_file_name(t.get("PRX_ZIP_FILE_NAME", String.class));
					object.setPrx_file_name(t.get("PRX_FILE_NAME", String.class));

					lst.add(object);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return lst;
	}

	@Override
	public List<Dg_veeva_pos_emp> getDgveevaposempdata(Date startdt, Date enddt) throws Exception {
		// TODO Auto-generated method stub
		List<Dg_veeva_pos_emp> lst = null;
		EntityManager em = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			em = emf.createEntityManager();
			String stdt = sdf.format(startdt);
			String endt = sdf.format(enddt);

			StringBuffer sb = new StringBuffer();
			sb.append(
					"select slno,country_cd,source,src_sys_id,posn_src_sys_id,empl_src_sys_id,posn_type_cd,posn_type_desc,");
			sb.append(
					" eff_dt,create_ts,last_upd_ts,updated_by,active,del_ts,empl_source,prx_zip_file_name,prx_file_name,date_time,date_time_recd from Dg_veeva_pos_emp");
			sb.append(" where convert( date , date_time ) >=:stdt");
			sb.append(" and convert( date , date_time ) <=:endt");
			sb.append(" order by country_cd");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("stdt", stdt);
			query.setParameter("endt", endt);

			List<Tuple> list = query.getResultList();

			if (list != null && !list.isEmpty()) {
				lst = new ArrayList<>();
				Dg_veeva_pos_emp object = null;
				for (Tuple t : list) {
					object = new Dg_veeva_pos_emp();

					object.setSlno(Long.valueOf(t.get("slno", BigDecimal.class).toString()));
					object.setCountry_cd(t.get("country_cd", String.class));
					object.setSource(t.get("source", String.class));
					object.setSrc_sys_id(t.get("src_sys_id", String.class));
					object.setPosn_src_sys_id(t.get("posn_src_sys_id", String.class));
					object.setEmpl_src_sys_id(t.get("empl_src_sys_id", String.class));
					object.setPosn_type_cd(t.get("posn_type_cd", String.class));
					object.setEff_dt(t.get("eff_dt", String.class));
					object.setCreate_ts(t.get("create_ts", String.class));
					object.setLast_upd_ts(t.get("last_upd_ts", String.class));
					object.setUpdated_by(t.get("updated_by", String.class));
					object.setActive(t.get("active", String.class));
					object.setDel_ts(t.get("del_ts", String.class));
					object.setEmpl_source(t.get("empl_source", String.class));
					object.setPrx_zip_file_name(t.get("prx_zip_file_name", String.class));
					object.setPrx_file_name(t.get("prx_file_name", String.class));
					object.setDate_time(t.get("date_time", Date.class));
					object.setDate_time_recd(t.get("date_time_recd", Date.class));

					lst.add(object);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return lst;
	}

	@Override
	public List<Dg_veeva_pos> getDgveevaposdata(Date startdt, Date enddt) throws Exception {
		List<Dg_veeva_pos> lst = null;
		EntityManager em = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			em = emf.createEntityManager();
			String stdt = sdf.format(startdt);
			String endt = sdf.format(enddt);

			StringBuffer sb = new StringBuffer();
			sb.append(
					"select slno,country_cd,source,src_sys_id,posn_type_cd,posn_cd,posn_nm,posn_desc,posn_desc_short,");
			sb.append(" slfc_src_sys_id,slfc_type_cd,slfc_cd,slfc_nm,bus_unit_cd,bus_unit_nm,parent_src_sys_id,");
			sb.append(
					" hier_type,node_type,eff_date,end_date,create_ts,last_upd_ts,updated_by,active,del_ts,prx_zip_file_name, ");
			sb.append(" prx_file_name,date_time,date_time_recd from Dg_veeva_pos");
			sb.append(" where convert( date , date_time ) >=:stdt");
			sb.append(" and convert( date , date_time ) <=:endt");
			sb.append(" order by country_cd");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("stdt", stdt);
			query.setParameter("endt", endt);

			List<Tuple> list = query.getResultList();

			if (list != null && !list.isEmpty()) {
				lst = new ArrayList<>();
				Dg_veeva_pos object = null;
				for (Tuple t : list) {
					object = new Dg_veeva_pos();

					object.setSlno(Long.valueOf(t.get("slno", BigDecimal.class).toString()));
					object.setCountry_cd(t.get("country_cd", String.class));
					object.setSource(t.get("source", String.class));
					object.setSrc_sys_id(t.get("src_sys_id", String.class));
					object.setPosn_type_cd(t.get("posn_type_cd", String.class));
					object.setPosn_cd(t.get("posn_cd", String.class));
					object.setPosn_nm(t.get("posn_nm", String.class));
					object.setPosn_desc(t.get("posn_desc", String.class));
					object.setPosn_desc_short(t.get("posn_desc_short", String.class));
					object.setSlfc_src_sys_id(t.get("slfc_src_sys_id", String.class));
					object.setSlfc_type_cd(t.get("slfc_type_cd", String.class));
					object.setSlfc_cd(t.get("slfc_cd", String.class));
					object.setSlfc_nm(t.get("slfc_nm", String.class));
					object.setBus_unit_cd(t.get("bus_unit_cd", String.class));
					object.setBus_unit_nm(t.get("bus_unit_nm", String.class));
					object.setParent_src_sys_id(t.get("parent_src_sys_id", String.class));
					object.setHier_type(t.get("hier_type", String.class));
					object.setNode_type(t.get("node_type", String.class));
					object.setEff_date(t.get("eff_date", String.class));
					object.setEnd_date(t.get("end_date", String.class));
					object.setCreate_ts(t.get("create_ts", String.class));
					object.setLast_upd_ts(t.get("last_upd_ts", String.class));
					object.setUpdated_by(t.get("updated_by", String.class));
					object.setActive(t.get("active", String.class));
					object.setDel_ts(t.get("del_ts", String.class));
					object.setPrx_zip_file_name(t.get("prx_zip_file_name", String.class));
					object.setPrx_file_name(t.get("prx_file_name", String.class));
					object.setDate_time(t.get("date_time", Date.class));
					object.setDate_time_recd(t.get("date_time_recd", Date.class));

					lst.add(object);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return lst;
	}

	@Override
	public List<FstaffMas_PrxBean> getPrxFieldStaffStatusdata(Date startdt, Date enddt, Character Ind)
			throws Exception {
		// TODO Auto-generated method stub
		List<FstaffMas_PrxBean> lst = null;
		EntityManager em = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			em = emf.createEntityManager();
			String stdt = sdf.format(startdt);
			String endt = sdf.format(enddt);

			System.out.println("stdt :: " + stdt);
			System.out.println("endt :: " + endt);
			System.out.println("Ind  :: " + Ind);

			StringBuffer sb = new StringBuffer();
			sb.append(
					"select FSPR.TYPE_OF_CHANGE_DESCR,fs.fstaff_id,div_disp_nm,FS.FSTAFF_TERR_ID,FS.FSTAFF_TERR_CODE,fs.fstaff_code,fs.fstaff_name FSTAFF_NAME,");
			sb.append(" FS.FSTAFF_EMPLOYEE_NO ,FS.FSTAFF_MAP_CODE1,");
			sb.append(" fs.fstaff_display_name FSTAFF_DISPLAY_NAME,");
			sb.append(" fs.FSTAFF_MGR1_ID,am.fstaff_name ABM,am.fstaff_display_name ABMname,");
			sb.append(" fs.FSTAFF_MGR2_ID,rm.fstaff_name RBM,rm.fstaff_display_name RBMname,");
			sb.append(
					" FS.FSTAFF_ADDR1,FS.FSTAFF_ADDR2,FS.FSTAFF_ADDR3,FS.FSTAFF_ADDR4,FS.FSTAFF_DESTINATION, FS.FSTAFF_PINCODE,");
			sb.append(
					" FS.FSTAFF_MOBILE,FS.FSTAFF_JOINING_DATE,FS.FSTAFF_LEAVING_DATE,FS.FSTAFF_SAMP_DISP_IND,FS.FSTAFF_STATUS,");
			sb.append(
					" FS.FSTAFF_DESIG, FS.FSTAFF_ZONENAME,FS.FS_CATEGORY,hq.hq_name,FS.FSTAFF_ins_dt,FS.FSTAFF_mod_dt,FSPR.TERR_ID,FSPR.TERR_CODE,TR.TERR_ID");
			sb.append(" TR_TERR_ID,TR.TERR_CODE TR_TERR_CODE,FSPR.INTEG_TYPE_DESC,");
			sb.append(
					" FSPR.DATE_TIME_RECD,Main_MAST_UPDATED,DATE_TIME_UPDATED,FSPR.FS_NEW_CHANGED,FSPR.TEAM_CODE,FSPR.PRX_ZIP_FILE_NAME,FSPR.PRX_FILE_NAME");
			sb.append(" from TEMP_FSTAFFMAS_PRX FSPR");
			sb.append(" LEFT JOIN  fieldstaff fs  ON FSPR.FSTAFF_ID = FS.FSTAFF_ID");
			sb.append(" LEFT join hqmaster hq on hq.hq_id=fs.FSTAFF_HQ_ID");
			sb.append(" LEFT join div_master dv on fs.fstaff_div_id=div_id");
			sb.append(" LEFT join fieldstaff am on fs.FSTAFF_MGR1_ID=am.fstaff_id");
			sb.append(" LEFT join fieldstaff rm on fs.FSTAFF_MGR2_ID=rm.fstaff_id");
			sb.append(" LEFT JOIN TERR_MASTER TR ON TR.TERR_CODE=FSPR.TERR_CODE");
			sb.append(" where");
			sb.append(" fspr.FS_NEW_CHANGED=:ind");
			sb.append(
					" AND ( (TR.TERR_ID IS NOT NULL AND TR.TERR_DIV_ID=DV.DIV_ID AND CONVERT(DATE,FSPR.DATE_TIME_RECD)>=:stdt");
			sb.append(" AND CONVERT(DATE, FSPR.DATE_TIME_RECD)<=:endt)");
			sb.append(
					" OR (FSPR.INTEG_TYPE_DESC='EMP PROCESS - ADDRESS CHANGE' AND CONVERT(DATE,FSPR.DATE_TIME_RECD)>=:stdt");
			sb.append(" AND CONVERT(DATE, FSPR.DATE_TIME_RECD)<=:endt))");
			sb.append(" order by fs.fstaff_div_id,FS.FSTAFF_TERR_CODE,fs.fstaff_name");
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("stdt", stdt);
			query.setParameter("endt", endt);
			query.setParameter("ind", Ind);

			List<Tuple> list = query.getResultList();
			System.out.println("Lst :: " + list.size());
			if (list != null && !list.isEmpty()) {
				lst = new ArrayList<>();
				FstaffMas_PrxBean obj = null;
				for (Tuple t : list) {
					obj = new FstaffMas_PrxBean();
					obj.setType_of_change_descr(t.get("TYPE_OF_CHANGE_DESCR", String.class));
					obj.setFstaff_id(t.get("fstaff_id", Integer.class) == null ? 0
							: Long.valueOf(t.get("fstaff_id", Integer.class).toString()));
					obj.setDiv_disp_nm(t.get("div_disp_nm", String.class));
					obj.setFstaff_terr_id(t.get("FSTAFF_TERR_ID", Integer.class) == null ? 0
							: Long.valueOf(t.get("FSTAFF_TERR_ID", Integer.class).toString()));
					obj.setFstaff_terr_code(t.get("FSTAFF_TERR_CODE", String.class));
					obj.setFstaff_code(t.get("fstaff_code", String.class));
					obj.setFstaff_name(t.get("FSTAFF_NAME", String.class));
					obj.setFstaff_employee_no(t.get("FSTAFF_EMPLOYEE_NO", String.class));
					obj.setFstaff_map_code1(t.get("FSTAFF_MAP_CODE1", String.class));
					obj.setFstaff_display_name(t.get("FSTAFF_DISPLAY_NAME", String.class));
					obj.setFstaff_mgr1_id(t.get("FSTAFF_MGR1_ID", Integer.class) == null ? 0
							: Long.valueOf(t.get("FSTAFF_MGR1_ID", Integer.class).toString()));
					obj.setAbm(t.get("ABM", String.class));
					obj.setAbm_name(t.get("ABMname", String.class));
					obj.setFstaff_mgr2_id(t.get("FSTAFF_MGR2_ID", Integer.class) == null ? 0
							: Long.valueOf(t.get("FSTAFF_MGR2_ID", Integer.class).toString()));
					obj.setRbm(t.get("RBM", String.class));
					obj.setRbm_name(t.get("RBMname", String.class));
					obj.setFstaff_addr1(t.get("FSTAFF_ADDR1", String.class));
					obj.setFstaff_addr2(t.get("FSTAFF_ADDR2", String.class));
					obj.setFstaff_addr3(t.get("FSTAFF_ADDR3", String.class));
					obj.setFstaff_addr4(t.get("FSTAFF_ADDR4", String.class));
					obj.setFstaff_destination(t.get("FSTAFF_DESTINATION", String.class));
					obj.setFstaff_pincode(t.get("FSTAFF_PINCODE", String.class));
					obj.setFstaff_mobile(t.get("FSTAFF_MOBILE", String.class));
					obj.setFstaff_joining_date(t.get("FSTAFF_JOINING_DATE", Date.class));
					obj.setFstaff_leaving_date(t.get("FSTAFF_LEAVING_DATE", Date.class));
					obj.setFstaff_samp_disp_ind(t.get("FSTAFF_SAMP_DISP_IND", Character.class) == null ? ""
							: t.get("FSTAFF_SAMP_DISP_IND", Character.class).toString());
					obj.setFstaff_status(t.get("FSTAFF_STATUS", Character.class) == null ? ""
							: t.get("FSTAFF_STATUS", Character.class).toString());
					obj.setFstaff_Desig(t.get("FSTAFF_DESIG", String.class));
					obj.setFstaff_ZoneName(t.get("FSTAFF_ZONENAME", String.class));
					obj.setFs_Category(t.get("FS_CATEGORY", Character.class) == null ? ""
							: t.get("FS_CATEGORY", Character.class).toString());
					obj.setHq_name(t.get("hq_name", String.class));
					obj.setFstaff_ins_dt(t.get("FSTAFF_ins_dt", Date.class));
					obj.setFstaff_mod_dt(t.get("FSTAFF_mod_dt", Date.class));
					obj.setInt_Type_Desc(t.get("INTEG_TYPE_DESC", String.class));
					obj.setDate_Time_Recd(t.get("DATE_TIME_RECD", Date.class));
					obj.setMain_mast_updated(t.get("Main_MAST_UPDATED", Character.class) == null ? ""
							: t.get("Main_MAST_UPDATED", Character.class).toString());
					obj.setDate_time_Updated(t.get("DATE_TIME_UPDATED", Date.class));
					obj.setFs_new_changed(t.get("FS_NEW_CHANGED", Character.class) == null ? ""
							: t.get("FS_NEW_CHANGED", Character.class).toString());
					obj.setTeam_code(t.get("TEAM_CODE", String.class) == null ? "0" : t.get("TEAM_CODE", String.class));
					obj.setPrx_zip_file_name(t.get("PRX_ZIP_FILE_NAME", String.class) == null ? ""
							: t.get("PRX_ZIP_FILE_NAME", String.class).toString());
					obj.setPrx_file_name(t.get("PRX_FILE_NAME", String.class) == null ? ""
							: t.get("PRX_FILE_NAME", String.class).toString());

					lst.add(obj);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return lst;
	}

	@Override
	public List<Dg_veeva_emp> getpforcerxDgVeevaEmpData(Date startdt, Date enddt) throws Exception {
		// TODO Auto-generated method stub
		List<Dg_veeva_emp> lst = null;
		EntityManager em = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			em = emf.createEntityManager();
			String stdt = sdf.format(startdt);
			String endt = sdf.format(enddt);
			System.out.println("start date::" + stdt + ":::end date::" + endt);
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select slno,country_cd,source,src_sys_id,pfz_empl_id,login_id,othr_id,othr_id_name,login_domain_nm,crm_role,");
			sb.append(
					" crm_profile,frst_nm,frst_nm_2,mdl_nm,last_nm,last_nm_2,maiden_nm,maiden_nm_2,status,employ_type,job_title,");
			sb.append(" pfz_empl_flg,bkng_agnt,part_time,avg_hrs_per_day,");
			sb.append(
					" eff_dt,end_dt,addr_lbl,addr_lbl_2,addr_lbl_long,addr_lbl_long_2,addr_ext_lbl,country_lbl,area_lbl,");
			sb.append(
					" lg_post_cd,district_cd,postal_city,postal_city_2,instreet_num,region_cd,county_cd,canton_cd,city_cd,region,");
			sb.append(
					" county,canton,city,city_2,work_phone,mobile_phone,pager,fax,home_phone,voice_mail,email,time_zone,lang_cd,");
			sb.append(" create_ts,last_upd_ts,updated_by,active,del_ts,termination_date,gender,");
			sb.append(
					" employment_type_code,prx_zip_file_name,prx_file_name,date_time,date_time_recd from Dg_veeva_emp");
			sb.append(" where convert( date , date_time ) >=:stdt");
			sb.append(" and convert( date , date_time ) <=:endt");
			sb.append(" order by country_cd");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("stdt", stdt);
			query.setParameter("endt", endt);

			List<Tuple> list = query.getResultList();

			System.out.println("list::::" + list.size());

			if (list != null && !list.isEmpty()) {
				lst = new ArrayList<>();
				Dg_veeva_emp object = null;
				for (Tuple t : list) {
					object = new Dg_veeva_emp();
					object.setCountry_cd(t.get("country_cd", String.class));
					object.setSource(t.get("source", String.class));
					object.setSrc_sys_id(t.get("src_sys_id", String.class));

					object.setPfz_empl_id(t.get("pfz_empl_id", String.class));
					object.setLogin_id(t.get("login_id", String.class));
					object.setOthr_id(t.get("othr_id", String.class));
					object.setOthr_id_name(t.get("othr_id_name", String.class));
					object.setLogin_domain_nm(t.get("login_domain_nm", String.class));
					object.setCrm_role(t.get("crm_role", String.class));
					object.setCrm_profile(t.get("crm_profile", String.class));
					object.setFrst_nm(t.get("frst_nm", String.class));
					object.setFrst_nm_2(t.get("frst_nm_2", String.class));
					object.setMdl_nm(t.get("mdl_nm", String.class));
					object.setLast_nm(t.get("last_nm", String.class));
					object.setLast_nm_2(t.get("last_nm_2", String.class));
					object.setMaiden_nm(t.get("maiden_nm", String.class));
					object.setMaiden_nm_2(t.get("maiden_nm_2", String.class));
					object.setStatus(t.get("status", String.class));
					object.setEmploy_type(t.get("employ_type", String.class));
					object.setJob_title(t.get("job_title", String.class));
					object.setPfz_empl_flg(t.get("pfz_empl_flg", String.class));
					object.setBkng_agnt(t.get("bkng_agnt", String.class));
					object.setPart_time(t.get("part_time", String.class));
					object.setAvg_hrs_per_day(t.get("avg_hrs_per_day", String.class));

					object.setEff_dt(t.get("eff_dt", String.class));
					object.setEnd_dt(t.get("end_dt", String.class));

					object.setAddr_lbl(t.get("addr_lbl", String.class));
					object.setAddr_lbl_2(t.get("addr_lbl_2", String.class));
					object.setAddr_lbl_long(t.get("addr_lbl_long", String.class));
					object.setAddr_lbl_long_2(t.get("addr_lbl_long_2", String.class));
					object.setAddr_ext_lbl(t.get("addr_ext_lbl", String.class));
					object.setCountry_lbl(t.get("country_lbl", String.class));
					object.setArea_lbl(t.get("area_lbl", String.class));
					object.setLg_post_cd(t.get("lg_post_cd", String.class));
					object.setDistrict_cd(t.get("district_cd", String.class));
					object.setPostal_city(t.get("postal_city", String.class));
					object.setPostal_city_2(t.get("postal_city_2", String.class));
					object.setInstreet_num(t.get("instreet_num", String.class));
					object.setRegion_cd(t.get("region_cd", String.class));
					object.setCounty_cd(t.get("county_cd", String.class));
					object.setCanton_cd(t.get("canton_cd", String.class));
					object.setCity_cd(t.get("city_cd", String.class));
					object.setRegion(t.get("region", String.class));
					object.setCounty(t.get("county", String.class));
					object.setCanton(t.get("canton", String.class));
					object.setCity(t.get("city", String.class));
					object.setCity_2(t.get("city_2", String.class));
					object.setWork_phone(t.get("work_phone", String.class));
					object.setMobile_phone(t.get("mobile_phone", String.class));
					object.setPager(t.get("pager", String.class));
					object.setFax(t.get("fax", String.class));
					object.setHome_phone(t.get("home_phone", String.class));
					object.setVoice_mail(t.get("voice_mail", String.class));
					object.setEmail(t.get("email", String.class));
					object.setTime_zone(t.get("time_zone", String.class));
					object.setLang_cd(t.get("lang_cd", String.class));

					object.setCreate_ts(t.get("create_ts", String.class));
					object.setLast_upd_ts(t.get("last_upd_ts", String.class));
					object.setUpdated_by(t.get("updated_by", String.class));
					object.setActive(t.get("active", String.class));
					object.setDel_ts(t.get("del_ts", String.class));
					object.setTermination_date(t.get("termination_date", String.class));
					object.setGender(t.get("gender", String.class));
					object.setEmployment_type_code(t.get("employment_type_code", String.class));
					object.setPrx_zip_file_name(t.get("prx_zip_file_name", String.class));
					object.setPrx_file_name(t.get("prx_file_name", String.class));
					object.setDate_time(t.get("date_time", Date.class));
					object.setDate_time_recd(t.get("date_time_recd", Date.class));

					lst.add(object);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return lst;
	}

	@Override
	public List<FstaffMas_PrxBean> getPrxFieldTerr_data(Date startdt, Date enddt, Character Ind) throws Exception {
		// TODO Auto-generated method stub
		List<FstaffMas_PrxBean> lst = null;
		EntityManager em = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			em = emf.createEntityManager();
			String stdt = sdf.format(startdt);
			String endt = sdf.format(enddt);

			System.out.println("stdt :: " + stdt);
			System.out.println("endt :: " + endt);
			System.out.println("Ind  :: " + Ind);

			StringBuffer sb = new StringBuffer();
			sb.append(
					"SELECT FSPR.TYPE_OF_CHANGE_DESCR,FSPR.INTEG_TYPE_DESC,FSPR.PRX_DIV_NAME,FSPR.TERR_CODE,FSPR.FSTAFF_NAME PRX_TERR_NAME,FSPR.FSTAFF_DISPLAY_NAME PRX_FSTAFF_DISPLAY_NAME,");
			sb.append(" SUBSTRING(FSPR.FSTAFF_EMPLOYEE_NO,1,8) PRX_EMPLOYEE_NO ,");
			sb.append(
					" FSPR.DATE_TIME_RECD,Main_MAST_UPDATED,DATE_TIME_UPDATED,FSPR.FS_NEW_CHANGED,FSPR.TEAM_CODE,FSPR.PRX_ZIP_FILE_NAME,FSPR.PRX_FILE_NAME ");
			sb.append(" from TEMP_FSTAFFMAS_PRX  FSPR");
			sb.append(" LEFT JOIN TERR_MASTER TR ON TR.TERR_CODE=FSPR.TERR_CODE AND TR.TERR_STATUS='A'");
			sb.append(" where fspr.FS_NEW_CHANGED=:ind");
			sb.append(" AND TR.TERR_ID IS  NULL");
			sb.append(" AND CONVERT(DATE,FSPR.DATE_TIME_RECD)>=:stdt AND CONVERT(DATE, FSPR.DATE_TIME_RECD)<=:endt");
			sb.append(" order by FSPR.INTEG_TYPE_DESC,FSPR.TERR_CODE");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("stdt", stdt);
			query.setParameter("endt", endt);
			query.setParameter("ind", Ind);

			List<Tuple> list = query.getResultList();

			if (list != null && !list.isEmpty()) {
				System.out.println("List :: " + list.size());
				lst = new ArrayList<>();
				FstaffMas_PrxBean obj = null;
				for (Tuple t : list) {
					obj = new FstaffMas_PrxBean();
					obj.setType_of_change_descr(t.get("TYPE_OF_CHANGE_DESCR", String.class));
					obj.setInt_Type_Desc(t.get("INTEG_TYPE_DESC", String.class));
					obj.setPrxDivName(t.get("PRX_DIV_NAME", String.class));
					obj.setTerr_code(t.get("TERR_CODE", String.class));
					obj.setPrx_terr_name(t.get("PRX_TERR_NAME", String.class));
					obj.setFstaff_display_name(t.get("PRX_FSTAFF_DISPLAY_NAME", String.class));
					obj.setFstaff_employee_no(t.get("PRX_EMPLOYEE_NO", String.class));
					obj.setDate_Time_Recd(t.get("DATE_TIME_RECD", Date.class));
					obj.setMain_mast_updated(t.get("Main_MAST_UPDATED", Character.class) == null ? ""
							: t.get("Main_MAST_UPDATED", Character.class).toString());
					obj.setDate_time_Updated(t.get("DATE_TIME_UPDATED", Date.class));
					obj.setFs_new_changed(t.get("FS_NEW_CHANGED", Character.class) == null ? ""
							: t.get("FS_NEW_CHANGED", Character.class).toString());
					obj.setTeam_code(t.get("TEAM_CODE", String.class));
					obj.setPrx_zip_file_name(t.get("PRX_ZIP_FILE_NAME", String.class) == null ? ""
							: t.get("PRX_ZIP_FILE_NAME", String.class).toString());
					obj.setPrx_file_name(t.get("PRX_FILE_NAME", String.class) == null ? ""
							: t.get("PRX_FILE_NAME", String.class).toString());

					lst.add(obj);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return lst;
	}

	@Override
	public List<Allocation_report_2> getdatafor_allocation_report2_current(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<Allocation_report_2> lst = null;
		EntityManager em = null;
		try {

			System.out.println("bean.getDivId() :: " + bean.getDivId());
			System.out.println("bean.getFin_year() :: " + bean.getFin_year());
			System.out.println("bean.getPperiod_code() :: " + bean.getPperiod_code());
			System.out.println("bean.getSubCompId() :: " + bean.getSubCompId());
			System.out.println("bean.getPprod_sub_type_id() :: " + bean.getPprod_sub_type_id());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("Allocation_report_2_current");

			procedurecall.setParameter("pdiv_id", bean.getDivId());
			procedurecall.setParameter("pfin_year", bean.getFin_year());
			procedurecall.setParameter("pperiod_code", bean.getPperiod_code());
			procedurecall.setParameter("psub_comp_id", bean.getSubCompId());
			procedurecall.setParameter("pprod_sub_type_id", bean.getPprod_sub_type_id());

			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<Allocation_report_2> getdatafor_allocation_report2_previous(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<Allocation_report_2> lst = null;
		EntityManager em = null;
		try {

			System.out.println("bean.getDivId() :: " + bean.getDivId());
			System.out.println("bean.getFin_year() :: " + bean.getFin_year());
			System.out.println("bean.getPperiod_code() :: " + bean.getPperiod_code());
			System.out.println("bean.getSubCompId() :: " + bean.getSubCompId());
			System.out.println("bean.getPprod_sub_type_id() :: " + bean.getPprod_sub_type_id());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("Allocation_report_2_previous");

			procedurecall.setParameter("pdiv_id", bean.getDivId());
			procedurecall.setParameter("pfin_year", bean.getFin_year());
			procedurecall.setParameter("pperiod_code", bean.getPperiod_code());
			procedurecall.setParameter("psub_comp_id", bean.getSubCompId());
			procedurecall.setParameter("pprod_sub_type_id", bean.getPprod_sub_type_id());

			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<Allocation_report_1> getdatafor_allocation_report1_current(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<Allocation_report_1> lst = null;
		EntityManager em = null;
		try {

			System.out.println("bean.getDivId() :: " + bean.getDivId());
			System.out.println("bean.getFin_year() :: " + bean.getFin_year());
			System.out.println("bean.getPperiod_code() :: " + bean.getPperiod_code());
			System.out.println("bean.getSubCompId() :: " + bean.getSubCompId());
			System.out.println("bean.getPprod_sub_type_id() :: " + bean.getPprod_sub_type_id());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("Allocation_report_1_current");

			procedurecall.setParameter("pdiv_id", bean.getDivId());
			procedurecall.setParameter("pfin_year", bean.getFin_year());
			procedurecall.setParameter("pperiod_code", bean.getPperiod_code());
			procedurecall.setParameter("psub_comp_id", bean.getSubCompId());
			procedurecall.setParameter("pprod_sub_type_id", bean.getPprod_sub_type_id());

			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<Allocation_report_1> getdatafor_allocation_report1_previous(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<Allocation_report_1> lst = null;
		EntityManager em = null;
		try {

			System.out.println("bean.getDivId() :: " + bean.getDivId());
			System.out.println("bean.getFin_year() :: " + bean.getFin_year());
			System.out.println("bean.getPperiod_code() :: " + bean.getPperiod_code());
			System.out.println("bean.getSubCompId() :: " + bean.getSubCompId());
			System.out.println("bean.getPprod_sub_type_id() :: " + bean.getPprod_sub_type_id());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("Allocation_report_1_previous");

			procedurecall.setParameter("pdiv_id", bean.getDivId());
			procedurecall.setParameter("pfin_year", bean.getFin_year());
			procedurecall.setParameter("pperiod_code", bean.getPperiod_code());
			procedurecall.setParameter("psub_comp_id", bean.getSubCompId());
			procedurecall.setParameter("pprod_sub_type_id", bean.getPprod_sub_type_id());

			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<IAADetailReport> getIAADetailReportData(String loc_id, String startDate, String endDate,
			String[] prod_id) throws Exception {
		// TODO Auto-generated method stub
		List<IAADetailReport> lst = null;
		EntityManager em = null;
		try {
			String prod = "(".concat(String.join(",", prod_id)).concat(")");

			System.out.println("loc_id :: " + loc_id);
			System.out.println("startdate :: " + startDate);
			System.out.println("endDate :: " + endDate);
			System.out.println("prod_id :: " + prod);

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("IAADetailReport_Revised");

			procedurecall.setParameter("loc_id", loc_id);
			procedurecall.setParameter("startdate", startDate);
			procedurecall.setParameter("endDate", endDate);
			procedurecall.setParameter("prod_id", prod);

			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<IAADetailReport> getIAADetailReportPreviousData(String loc_id, String startDate, String endDate,
			String[] prod_id) throws Exception {
		// TODO Auto-generated method stub
		List<IAADetailReport> lst = null;
		EntityManager em = null;
		try {
			String prod = "(".concat(String.join(",", prod_id)).concat(")");

			System.out.println("loc_id :: " + loc_id);
			System.out.println("startdate :: " + startDate);
			System.out.println("endDate :: " + endDate);
			System.out.println("prod_id :: " + prod);

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("IAADetailReport_Revised_Previous");

			procedurecall.setParameter("loc_id", loc_id);
			procedurecall.setParameter("startdate", startDate);
			procedurecall.setParameter("endDate", endDate);
			procedurecall.setParameter("prod_id", prod);
			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<StockAgeingGrnReportView> getstockageinggrnreportData(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<StockAgeingGrnReportView> list = new ArrayList<>();

		try {

			System.out.println("bean.getLocId() : " + bean.getLocId());
			System.out.println("bean.getDivId() : " + bean.getDivId());
			System.out.println("endDate " + MedicoResources.convertUtilDateToString(bean.getEndDate()));
			// System.out.println("enddt
			// "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()));
			System.out.println("bean.getUserId() : " + bean.getUserId());
			System.out.println("bean.getProd_id() : " + bean.getProd_id());
			System.out.println("DOCTYPE " + bean.getDoctype());

			System.out.println("bean.getSlab1a() : " + bean.getSlab1a());
			System.out.println("bean.getSlab1b() : " + bean.getSlab1b());
			System.out.println("bean.getSlab2a() : " + bean.getSlab2a());
			System.out.println("bean.getSlab2b() : " + bean.getSlab2b());
			System.out.println("bean.getSlab3a() : " + bean.getSlab3a());
			System.out.println("bean.getSlab3b() : " + bean.getSlab3b());
			System.out.println("bean.getSlab4a() : " + bean.getSlab4a());
			System.out.println("bean.getSlab4b() : " + bean.getSlab4b());
			System.out.println("bean.getSlab5a() : " + bean.getSlab5a());
			System.out.println("bean.getSlab5b() : " + bean.getSlab5b());
			System.out.println("bean.getSlab6a() : " + bean.getSlab6a());

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("call_STOCK_AGEING_GRN_REPORT");

			procedurecall.setParameter("LOCID", bean.getLocId());
			procedurecall.setParameter("DIVID", bean.getDivId());

			procedurecall.setParameter("SLAB1", bean.getSlab1a());
			procedurecall.setParameter("SLAB2", bean.getSlab1b());
			procedurecall.setParameter("SLAB3", bean.getSlab2a());
			procedurecall.setParameter("SLAB4", bean.getSlab2b());
			procedurecall.setParameter("SLAB5", bean.getSlab3a());
			procedurecall.setParameter("SLAB6", bean.getSlab3b());
			procedurecall.setParameter("SLAB7", bean.getSlab4a());
			procedurecall.setParameter("SLAB8", bean.getSlab4b());
			procedurecall.setParameter("SLAB9", bean.getSlab5a());
			procedurecall.setParameter("SLAB10", bean.getSlab5b());
			procedurecall.setParameter("SLAB11", bean.getSlab6a());
			procedurecall.setParameter("DOCTYPE", bean.getDoctype());
			procedurecall.setParameter("START_DT", MedicoResources.convertUtilDateToString(bean.getEndDate()));
			list = procedurecall.getResultList();

			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<dispatch_register_report1> getDispatchRegisterReport1(ReportBean bean) throws Exception {
		List<dispatch_register_report1> list = null;
		EntityManager em = null;
		try {

			System.out.println("inside the dispatch_register_report1");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em
					.createNamedStoredProcedureQuery("dispatch_register_report1");
//			callMonthlyActivityReport.setParameter("fin_year_flag", "CURRENT");
//			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", "2022");
//			callMonthlyActivityReport.setParameter("dsptype", "A");
//			callMonthlyActivityReport.setParameter("startdate", "2021-12-01");
//			callMonthlyActivityReport.setParameter("endDate", "2022-03-31");

			System.out.println("fin_year_flag::::" + bean.getFinyear());
			System.out.println("FIN_YEAR_ID::::" + bean.getFinyearflag());
			System.out.println("disp type::::" + bean.getDispatchType());
			System.out.println(
					"startdate::::" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate::::" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));

			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());
//			callMonthlyActivityReport.setParameter("dsptype", bean.getDsptype());
			callMonthlyActivityReport.setParameter("dsptype", bean.getDispatchType());
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate()));
			do {
				list = callMonthlyActivityReport.getResultList();
				System.out.println("List size of the dispatch_register_report1 ::::::::" + list.size());
			} while (callMonthlyActivityReport.hasMoreResults());
			System.out.println("List of outer loop ::::::::" + list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<dispatch_register_report2> getDispatchRegisterReport2(ReportBean bean) throws Exception {
		List<dispatch_register_report2> list = null;
		EntityManager em = null;
		try {

			System.out.println("inside the dispatch_register_report2");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em
					.createNamedStoredProcedureQuery("dispatch_register_report2");
//			callMonthlyActivityReport.setParameter("fin_year_flag", "CURRENT");
//			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", "2022");
//			callMonthlyActivityReport.setParameter("dsptype", "D");
//			callMonthlyActivityReport.setParameter("startdate", "2021-12-01");
//			callMonthlyActivityReport.setParameter("endDate", "2022-03-31");

			System.out.println("fin_year_flag::::" + bean.getFinyear());
			System.out.println("FIN_YEAR_ID::::" + bean.getFinyearflag());
			System.out.println(
					"startdate::::" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate::::" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));

			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());
			// callMonthlyActivityReport.setParameter("dsptype", bean.getDsptype());
			callMonthlyActivityReport.setParameter("dsptype", "D");
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate()));
			do {
				list = callMonthlyActivityReport.getResultList();
				System.out.println("List size of the dispatch_register_report2 ::::::::" + list.size());
			} while (callMonthlyActivityReport.hasMoreResults());
			System.out.println("List of outer loop ::::::::" + list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<DivMaster> getAllDivisionList() throws Exception {
		EntityManager em = null;
		List<DivMaster> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT DIV_ID,DIV_DISP_NM FROM DIV_MASTER WHERE DIV_ID IN ( SELECT DISTINCT DSP_DIV_ID FROM Dispatch_header ) ");
			sb.append(" ORDER BY DIV_DISP_NM ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				DivMaster smp = null;
				for (Tuple t : tuples) {
					smp = new DivMaster();
					smp.setDiv_id(Long.valueOf(t.get("DIV_ID", Integer.class).toString()));
					smp.setDiv_disp_nm(t.get("DIV_DISP_NM", String.class));
					list.add(smp);
				}
				System.out.println("div list:::::" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Period> getAllPeriodList() throws Exception {
		EntityManager em = null;
		List<Period> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT PERIOD_ID,PERIOD_CODE,PERIOD_NAME FROM PERIOD where PERIOD_ID IN ( SELECT DISTINCT DSP_PERIOD_ID FROM Dispatch_header )	 ");
			sb.append(" ORDER BY PERIOD_ID	 ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Period smp = null;
				for (Tuple t : tuples) {
					smp = new Period();
					smp.setPeriod_id(Long.valueOf(t.get("PERIOD_ID", Short.class).toString()));
					smp.setPeriod_code(t.get("PERIOD_CODE", String.class));
					smp.setPeriod_name(t.get("PERIOD_NAME", String.class));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> getcNameList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<Dispatch_Header> list = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			System.out.println("startDate::" + sdf.format(bean.getStartDate()));
			System.out.println("endDate::" + sdf.format(bean.getEndDate()));
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT DSP_TRANSPORTER  FROM DISPATCH_HEADER ");
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				sb.append(" WHERE");
			} else {
				sb.append(" WHERE DSP_DIV_ID =:divId AND");
			}
			sb.append("  dsp_dt >=:startDate");
			sb.append(" and  dsp_dt <=:endDate");
			sb.append(" and DSP_TRANSPORTER != '' ORDER BY DSP_TRANSPORTER");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			} else {
				query.setParameter("divId", bean.getDivId());
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Dispatch_Header smp = null;
				for (Tuple t : tuples) {
					smp = new Dispatch_Header();
					// smp.setDsp_id(Long.valueOf(t.get("DSP_ID", Integer.class).toString()));
					smp.setDsp_transporter(t.get("DSP_TRANSPORTER", String.class));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<SmpItem> getProductList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<SmpItem> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			System.out.println("divId::" + bean.getDivId());
			System.out.println("prodType::" + bean.getProdType());
			System.out.println("start date:::" + sdf.format(bean.getStartDate()) + ":::" + "end date:::"
					+ sdf.format(bean.getEndDate()));
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT SMP_PROD_ID,SMP_PROD_NAME,SMP_PROD_TYPE FROM SMPITEM WHERE SMP_PROD_ID IN ");
			sb.append(
					" ( SELECT DISTINCT DSPDTL_PROD_ID FROM Dispatch_detail dd , dispatch_header dh where dspdtl_dt >=:startDate and ");
			sb.append("  dspdtl_dt <=:endDate and dh.DSP_ID = dd.dspdtl_dsp_id and DH.DSP_APPR_STATUS = 'A' ");
//		if(!bean.getPeriod().equalsIgnoreCase("0")) {
//			sb.append(" and dh.DSP_PERIOD_ID =:monthOfUse)");
//		}
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				if (bean.getProdType().trim().equalsIgnoreCase("0") || bean.getProdType().trim().equalsIgnoreCase("")) {
					sb.append(" )");
				} else {
					sb.append(" ) AND SMP_PROD_TYPE_ID =:prodType ");
				}

			} else {
				if (bean.getProdType().trim().equalsIgnoreCase("0") || bean.getProdType().trim().equalsIgnoreCase("")) {
					sb.append(" ) AND SMP_STD_DIV_ID =:divId");
				} else {
					sb.append(" ) AND SMP_STD_DIV_ID =:divId");
					sb.append(" AND SMP_PROD_TYPE_ID =:prodType ");
				}
			}
			sb.append(" ORDER BY SMP_PROD_NAME ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				if (bean.getProdType().trim().equalsIgnoreCase("0") || bean.getProdType().trim().equalsIgnoreCase("")) {

				} else {
					query.setParameter("prodType", bean.getProdType());
				}

			} else {
				if (bean.getProdType().trim().equalsIgnoreCase("0") || bean.getProdType().trim().equalsIgnoreCase("")) {
					query.setParameter("divId", bean.getDivId());
				} else {
					query.setParameter("divId", bean.getDivId());
					query.setParameter("prodType", bean.getProdType());
				}

			}
//		if(!bean.getPeriod().equalsIgnoreCase("0")) {
//			query.setParameter("monthOfUse",bean.getPeriod());
//		}
			query.setParameter("startDate", sdf.format(bean.getStartDate()));
			query.setParameter("endDate", sdf.format(bean.getEndDate()));

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SmpItem smp = null;
				for (Tuple t : tuples) {
					smp = new SmpItem();
					smp.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID", Integer.class).toString()));
					smp.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					smp.setSmp_prod_type(t.get("SMP_PROD_TYPE", String.class));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;

			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<SG_d_parameters_details> getProductTypeList() throws Exception {
		EntityManager em = null;
		List<SG_d_parameters_details> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT SGprmdet_id,SGprmdet_nm FROM SG_d_parameters_details D WHERE D.SGprmdet_SGprm_id = 29 ");
			sb.append(" ORDER BY D.SGprmdet_disp_nm");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				SG_d_parameters_details smp = null;
				for (Tuple t : tuples) {
					smp = new SG_d_parameters_details();
					smp.setSg_prmdet_id(Long.valueOf(t.get("SGprmdet_id", Integer.class).toString()));
					smp.setSgprmdet_nm(t.get("SGprmdet_nm", String.class));
					list.add(smp);
				}
				System.out.println("product type list:::" + list.size() + "::::" + list.get(0).getSg_prmdet_id()
						+ "::::" + list.get(0).getSgprmdet_nm());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> getTeamList(String divId) throws Exception {
		EntityManager em = null;
		List<Dispatch_Header> list = null;
		try {
			System.out.println("divId::" + divId);
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT TEAM_CODE  FROM DISPATCH_HEADER ");
			if (divId.trim().equalsIgnoreCase("0") || divId.trim().equalsIgnoreCase("")) {
				sb.append(" WHERE ( TEAM_CODE IS NOT NULL AND TEAM_CODE != '0' )");
				sb.append(" ORDER BY TEAM_CODE");
			} else {
				sb.append(" WHERE DSP_DIV_ID =:divId ");
				sb.append(" AND ( TEAM_CODE IS NOT NULL AND TEAM_CODE != '0' )");
				sb.append(" ORDER BY TEAM_CODE");
			}
//		sb.append(" AND ( TEAM_CODE IS NOT NULL AND TEAM_CODE != '0' )");
//		sb.append(" ORDER BY TEAM_CODE");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (divId.trim().equalsIgnoreCase("0") || divId.trim().equalsIgnoreCase("")) {

			} else {
				query.setParameter("divId", divId);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Dispatch_Header smp = null;
				for (Tuple t : tuples) {
					smp = new Dispatch_Header();
					// smp.setDsp_id(Long.valueOf(t.get("DSP_ID", Integer.class).toString()));
					smp.setTeam_code(t.get("TEAM_CODE", String.class));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> getDocNameList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<Dispatch_Header> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT DSP_FSTAFF_ID,DSPFSTAFF_NAME FROM Dispatch_header  ");
			if (bean.getDivId().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				sb.append(" WHERE DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'D' )");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY DSPFSTAFF_NAME");
			} else {
				sb.append(" WHERE DSP_DIV_ID =:divId ");
				sb.append(" AND DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'D' )");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY DSPFSTAFF_NAME");
			}
//		sb.append(" AND DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'D' )");
//		sb.append(" ORDER BY DSPFSTAFF_NAME");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			} else {
				query.setParameter("divId", bean.getDivId());
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Dispatch_Header smp = null;
				for (Tuple t : tuples) {
					smp = new Dispatch_Header();
					smp.setDsp_fstaff_id(Long.valueOf(t.get("DSP_FSTAFF_ID", Integer.class).toString()));
					smp.setDspfstaff_name(t.get("DSPFSTAFF_NAME", String.class));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<ReportBean> getEmpNameList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<ReportBean> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			System.out.println("parameters:::" + bean.getDispatchType() + ":::" + bean.getDivId() + "::"
					+ sdf.format(bean.getStartDate()) + ":::" + sdf.format(bean.getEndDate()));
			StringBuffer sb = new StringBuffer();
			if (bean.getDispatchType().trim().equalsIgnoreCase("A")) {
				System.out.println("inside the Employee type condition::;");
				sb.append(
						" SELECT DISTINCT DSP_FSTAFF_ID,DSPFSTAFF_DISPLAYNAME,F.FSTAFF_EMPLOYEE_NO FROM Dispatch_header DH , FIELDSTAFF F ");
				if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
					sb.append(" WHERE DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )	");
					sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID	");
					sb.append(" AND dsp_dt >=:startDate");
					sb.append(" and  dsp_dt <=:endDate");
					sb.append(" ORDER BY DSPFSTAFF_DISPLAYNAME	");
				} else {
					sb.append(" WHERE DSP_DIV_ID =:divId ");
					sb.append(" AND DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )	");
					sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID	");
					sb.append(" AND dsp_dt >=:startDate");
					sb.append(" and  dsp_dt <=:endDate");
					sb.append(" ORDER BY DSPFSTAFF_DISPLAYNAME	");
				}
			} else {
				System.out.println("inside the Doctor type condition::;");
				sb.append(
						" SELECT DISTINCT F.FSTAFF_ID DSP_FSTAFF_ID,F.FSTAFF_NAME DSPFSTAFF_DISPLAYNAME,F.FSTAFF_EMPLOYEE_NO FROM ");
				sb.append(" Dispatch_header DH , FIELDSTAFF F , Alloc_header AH , ALLOC_REQ_HDR RQ");
				if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
					sb.append(
							" WHERE DH.DSP_ALLOC_ID = AH.ALLOC_ID AND RQ.FIN_YEAR = AH.ALLOC_FIN_YEAR AND RTRIM( AH.ALLOC_NO) = RTRIM( RQ.REQUEST_NO )	");
					sb.append(" AND F.FSTAFF_ID = RQ.REQUESTOR_ID	");
					sb.append(" AND dsp_dt >=:startDate");
					sb.append(" and  dsp_dt <=:endDate");
					sb.append(" ORDER BY F.FSTAFF_NAME	");
				} else {
					sb.append(" WHERE DH.DSP_DIV_ID=:divId ");
					sb.append(
							" AND DH.DSP_ALLOC_ID = AH.ALLOC_ID AND RQ.FIN_YEAR = AH.ALLOC_FIN_YEAR AND RTRIM( AH.ALLOC_NO) = RTRIM( RQ.REQUEST_NO )	");
					sb.append(" AND F.FSTAFF_ID = RQ.REQUESTOR_ID	");
					sb.append(" AND dsp_dt >=:startDate");
					sb.append(" and  dsp_dt <=:endDate");
					sb.append(" ORDER BY F.FSTAFF_NAME	");
				}
			}
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			} else {
				query.setParameter("divId", bean.getDivId());
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ReportBean smp = null;
				for (Tuple t : tuples) {
					smp = new ReportBean();
					smp.setDsp_fstaff_id(Long.valueOf(t.get("DSP_FSTAFF_ID", Integer.class).toString()));
					smp.setDspfstaff_name(t.get("DSPFSTAFF_DISPLAYNAME", String.class));
					smp.setFstaff_employee_no(t.get("FSTAFF_EMPLOYEE_NO", String.class));
					list.add(smp);
				}

				System.out.println("list : " + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<ReportBean> getRegNameList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<ReportBean> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT DISTINCT R.FSTAFF_ID,R.FSTAFF_NAME FROM Dispatch_header DH , FIELDSTAFF F , FIELDSTAFF R	");
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				System.out.println("inside if for courier name:::");
				sb.append(" WHERE DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )");
				sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID AND F.FSTAFF_MGR2_ID = R.FSTAFF_ID	");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY R.FSTAFF_NAME	");
			} else {
				sb.append(" WHERE DSP_DIV_ID =:divId ");
				sb.append(" AND DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )");
				sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID AND F.FSTAFF_MGR2_ID = R.FSTAFF_ID	");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY R.FSTAFF_NAME	");
			}
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			} else {
				query.setParameter("divId", bean.getDivId());
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ReportBean smp = null;
				for (Tuple t : tuples) {
					smp = new ReportBean();
					smp.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID", Integer.class).toString()));
					smp.setFstaff_name(t.get("FSTAFF_NAME", String.class));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<ReportBean> getDmNameList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<ReportBean> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			System.out.println("divId::" + bean.getDivId());
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT DISTINCT R.FSTAFF_ID,R.FSTAFF_NAME FROM Dispatch_header DH , FIELDSTAFF F , FIELDSTAFF R	 ");
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				System.out.println("inside if for courier name:::");
				sb.append(" WHERE DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )		");
				sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID AND F.FSTAFF_MGR1_ID = R.FSTAFF_ID	");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY R.FSTAFF_NAME	");
			} else {
				sb.append(" WHERE DSP_DIV_ID =:divId ");
				sb.append(" AND DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )		");
				sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID AND F.FSTAFF_MGR1_ID = R.FSTAFF_ID	");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY R.FSTAFF_NAME	");
			}
//		sb.append(" AND DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )		");
//		sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID AND F.FSTAFF_MGR1_ID = R.FSTAFF_ID	");
//		sb.append(" ORDER BY R.FSTAFF_NAME	");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			} else {
				query.setParameter("divId", bean.getDivId());
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ReportBean smp = null;
				for (Tuple t : tuples) {
					smp = new ReportBean();
					smp.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID", Integer.class).toString()));
					smp.setFstaff_name(t.get("FSTAFF_NAME", String.class));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<ReportBean> getTerrCodeList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<ReportBean> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT F.FSTAFF_TERR_CODE FROM Dispatch_header DH , FIELDSTAFF F	 ");
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				sb.append(" WHERE DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )");
				sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID	");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY  F.FSTAFF_TERR_CODE ");
			} else {
				sb.append(" WHERE DSP_DIV_ID =:divId ");
				sb.append(" AND DSP_ALLOC_ID IN ( SELECT ALLOC_ID FROM Alloc_header WHERE ALLOC_TYPE = 'A' )");
				sb.append(" AND DH.DSP_FSTAFF_ID = F.FSTAFF_ID	");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY  F.FSTAFF_TERR_CODE ");
			}
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			} else {
				query.setParameter("divId", bean.getDivId());
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ReportBean smp = null;
				for (Tuple t : tuples) {
					smp = new ReportBean();
					// smp.setFstaff_id(Long.valueOf(t.get("FSTAFF_ID", Integer.class).toString()));
					smp.setFstaff_terr_code(t.get("FSTAFF_TERR_CODE", String.class));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> getLrNumList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<Dispatch_Header> list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT DSP_LR_NO,CONVERT( VARCHAR , DSP_LR_DT , 105 ) LR_DT  FROM DISPATCH_HEADER  ");
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				sb.append(" WHERE DSP_LR_DT IS NOT NULL");
				sb.append(" AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY DSP_LR_NO	");
			} else {
				sb.append(" WHERE DSP_DIV_ID =:divId ");
				sb.append(" AND DSP_LR_DT IS NOT NULL");
				sb.append("  AND dsp_dt >=:startDate");
				sb.append(" and  dsp_dt <=:endDate");
				sb.append(" ORDER BY DSP_LR_NO	");
			}
//		sb.append(" AND DSP_LR_DT IS NOT NULL");
//		sb.append(" ORDER BY DSP_LR_NO	");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			} else {
				query.setParameter("divId", bean.getDivId());
				query.setParameter("startDate", sdf.format(bean.getStartDate()));
				query.setParameter("endDate", sdf.format(bean.getEndDate()));
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Dispatch_Header smp = null;
				for (Tuple t : tuples) {
					smp = new Dispatch_Header();
					smp.setDsp_lr_no(t.get("DSP_LR_NO", String.class));

					smp.setDsp_lr_dtString(t.get("LR_DT", String.class).trim());
					list.add(smp);
				}
				System.out.println("Lr numlist::::" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> getLrDateList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<Dispatch_Header> list = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT LR_DT FROM(SELECT DISTINCT CONVERT( VARCHAR ,  DSP_LR_DT , 105 ) LR_DT , DSP_LR_DT FROM DISPATCH_HEADER ");
			sb.append(" WHERE dsp_dt >=:startDate");
			sb.append(" and  dsp_dt <=:endDate");
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				sb.append(" AND DSP_LR_DT IS NOT NULL ) L");
			} else {
				sb.append(" AND DSP_DIV_ID =:divId ");
				sb.append(" AND DSP_LR_DT IS NOT NULL ) L");
			}

			sb.append(" ORDER BY DSP_LR_DT	");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
			} else {
				query.setParameter("divId", bean.getDivId());
			}
			query.setParameter("startDate", sdf.format(bean.getStartDate()));
			query.setParameter("endDate", sdf.format(bean.getEndDate()));
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Dispatch_Header smp = null;
				for (Tuple t : tuples) {
					smp = new Dispatch_Header();
					smp.setDsp_lr_dt(sdf.parse(t.get("LR_DT", String.class)));
					list.add(smp);
				}
				System.out.println("Lr date list ::::" + list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> getChallanDateList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<Dispatch_Header> list = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT CHALLAN_DT FROM( SELECT DISTINCT CONVERT( VARCHAR ,  DSP_CHALLAN_DT , 105 ) CHALLAN_DT  FROM DISPATCH_HEADER ");
			sb.append(" WHERE dsp_dt >=:startDate");
			sb.append(" and  dsp_dt <=:endDate");
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				sb.append(" AND DSP_CHALLAN_DT IS NOT NULL ) DD	 ");
				sb.append(" ORDER BY CHALLAN_DT	");
			} else {
				sb.append(" AND DSP_DIV_ID =:divId ");
				sb.append(" AND DSP_CHALLAN_DT IS NOT NULL ) DD	 ");
				sb.append(" ORDER BY CHALLAN_DT	");
			}

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {

			} else {
				query.setParameter("divId", bean.getDivId());
			}
			query.setParameter("startDate", sdf.format(bean.getStartDate()));
			query.setParameter("endDate", sdf.format(bean.getEndDate()));
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Dispatch_Header smp = null;
				for (Tuple t : tuples) {
					smp = new Dispatch_Header();
					smp.setDsp_challan_dt(sdf.parse(t.get("CHALLAN_DT", String.class)));
					list.add(smp);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
			System.out.println("challan date list::::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<Dispatch_Header> getChallanNumList(ReportBean bean) throws Exception {
		EntityManager em = null;
		List<Dispatch_Header> list = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer sb = new StringBuffer();
			sb.append(
					" SELECT DISTINCT DSP_CHALLAN_NO,CONVERT( VARCHAR , DSP_CHALLAN_DT  , 105 ) DSP_CHALLAN_DT FROM DISPATCH_HEADER  ");
			sb.append(" Where dsp_dt >=:startDate and  dsp_dt <=:endDate");
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				if (bean.getProdName().equalsIgnoreCase("0") || bean.getProdName().equalsIgnoreCase("")) {
					sb.append(" ORDER BY DSP_CHALLAN_NO	");
				} else {
					sb.append(" AND DSP_ID IN ( SELECT DSP_ID FROM DISPATCH_DETAIL WHERE DSP_PROD_ID=:prodName ) ");
					sb.append(" ORDER BY DSP_CHALLAN_NO	");
				}
			} else {
				if (bean.getProdName().equalsIgnoreCase("0") || bean.getProdName().equalsIgnoreCase("")) {
					sb.append(" AND DSP_DIV_ID =:divId ");
					sb.append(" ORDER BY DSP_CHALLAN_NO	");
				} else {
					sb.append(" AND DSP_DIV_ID =:divId ");
					sb.append(" AND DSP_ID IN ( SELECT DSP_ID FROM DISPATCH_DETAIL WHERE DSP_PROD_ID=:prodName ) ");
					sb.append(" ORDER BY DSP_CHALLAN_NO	");
				}
			}
//		sb.append(" AND DSP_ID IN ( SELECT DSP_ID FROM DISPATCH_DETAIL WHERE DSP_PROD_ID:prodName ) ");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			if (bean.getDivId().trim().equalsIgnoreCase("0") || bean.getDivId().trim().equalsIgnoreCase("")) {
				if (bean.getProdName().equalsIgnoreCase("0") || bean.getProdName().equalsIgnoreCase("")) {

				} else {
					query.setParameter("prodName", bean.getProdName());
				}
			} else {
				if (bean.getProdName().equalsIgnoreCase("0") || bean.getProdName().equalsIgnoreCase("")) {
					query.setParameter("divId", bean.getDivId());
				} else {
					query.setParameter("divId", bean.getDivId());
					query.setParameter("prodName", bean.getProdName());
				}
			}
			query.setParameter("startDate", sdf.format(bean.getStartDate()));
			query.setParameter("endDate", sdf.format(bean.getEndDate()));
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				Dispatch_Header smp = null;
				for (Tuple t : tuples) {
					smp = new Dispatch_Header();
					smp.setDspChallanNo(t.get("DSP_CHALLAN_NO", String.class));
					smp.setDsp_challan_dtString(t.get("DSP_CHALLAN_DT", String.class).trim());
					list.add(smp);
				}
				// System.out.println("challan number list:::"+list.size());
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<dispatch_register_report1_with_parameters> getDispatchRegisterRevisedReport1(ReportBean bean)
			throws Exception {
		List<dispatch_register_report1_with_parameters> list = null;
		EntityManager em = null;
		try {

			System.out.println("inside the new dispatch_register_report1");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em
					.createNamedStoredProcedureQuery("dispatch_register_report1_with_parameters");
			System.out.println("fin_year_flag::::" + bean.getFinyear());
			System.out.println("FIN_YEAR_ID::::" + bean.getFinyearflag());
			System.out.println("disp type::::" + bean.getDispatchType());
			System.out
					.println("startdate::::" + MedicoResources.convertUtilDateToString_YYYY_MM_DD(bean.getStartDate()));
			System.out.println("endDate::::" + MedicoResources.convertUtilDateToString_YYYY_MM_DD(bean.getEndDate()));
			System.out.println("prod_type_id::::" + bean.getProdType());
			System.out.println("prod_id::::" + bean.getProdName());
			System.out.println("month_use_per_id::::" + bean.getPeriod());
			System.out.println("div_id::::" + bean.getDivId());
			System.out.println("transporter_name::::" + bean.getcName());
			System.out.println("delivery_status::::" + bean.getDeliStatus());
			System.out.println("sub_team_code::::" + bean.gettName());
			System.out.println("chlno::::" + bean.getChallanNum());
			System.out.println("doc_id::::" + bean.getDocName());
			System.out.println("fs_id::::" + bean.getEmpName());
			System.out.println("rm_id::::" + bean.getRegName());
			System.out.println("terr_id::::" + bean.getTerrCode());
			System.out.println("dm_id::::" + bean.getDmName());
			System.out.println("chldt::::" + bean.getChallanDate()); // need to change
			System.out.println("lrdt::::" + bean.getLrDate()); // need to change
			System.out.println("doc_resp::::" + bean.getDocResponse());
			System.out.println("lrno::::" + bean.getLrNum());
			System.out.println("rm_id::::" + bean.getRegName());
			System.out.println("terr_id::::" + bean.getTerrCode());
			System.out.println("dm_id::::" + bean.getDmName());

			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag());
			callMonthlyActivityReport.setParameter("dsptype", bean.getDispatchType());
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("prod_type_id", bean.getProdType());
			callMonthlyActivityReport.setParameter("prod_id", bean.getProdName());
			callMonthlyActivityReport.setParameter("month_use_per_id", bean.getPeriod());
			callMonthlyActivityReport.setParameter("div_id", bean.getDivId());
			callMonthlyActivityReport.setParameter("transporter_name", bean.getcName());
			callMonthlyActivityReport.setParameter("delivery_status", bean.getDeliStatus());
			callMonthlyActivityReport.setParameter("sub_team_code", bean.gettName());
			callMonthlyActivityReport.setParameter("chlno", bean.getChallanNum());
			if (bean.getDocName() == null) {
				callMonthlyActivityReport.setParameter("doc_id", "0");
			} else {
				callMonthlyActivityReport.setParameter("doc_id", bean.getDocName());
			}
			if (bean.getRegName() == null || bean.getDmName() == null) {
				callMonthlyActivityReport.setParameter("rm_id", "0");
				callMonthlyActivityReport.setParameter("dm_id", "0");
			} else {
				callMonthlyActivityReport.setParameter("rm_id", bean.getRegName());
				callMonthlyActivityReport.setParameter("dm_id", bean.getDmName());
			}
			// if(bean.getDispatchType().equalsIgnoreCase("A")) {
			if (bean.getEmpName() == null) {
				callMonthlyActivityReport.setParameter("fs_id", "0");
			} else {
				callMonthlyActivityReport.setParameter("fs_id", bean.getEmpName());
			}
			callMonthlyActivityReport.setParameter("terr_id", bean.getTerrCode());
			// }else {
//			callMonthlyActivityReport.setParameter("fs_id","0");
//			callMonthlyActivityReport.setParameter("rm_id","0");
//			callMonthlyActivityReport.setParameter("terr_id","0");
//			callMonthlyActivityReport.setParameter("dm_id","0");
			// }
			callMonthlyActivityReport.setParameter("chldt", bean.getChallanDate()); // need to change
			if (bean.getDocResponse() == null || bean.getDocResponse().equalsIgnoreCase("0")) {
				callMonthlyActivityReport.setParameter("doc_resp", "0");
			} else {
				callMonthlyActivityReport.setParameter("doc_resp", bean.getDocResponse());
			}
			callMonthlyActivityReport.setParameter("lrno", bean.getLrNum());
			callMonthlyActivityReport.setParameter("lrdt", bean.getLrDate()); // need to change

			do {
				list = callMonthlyActivityReport.getResultList();
				System.out.println("List size of the dispatch_register_report1_with_parameters ::::::::" + list.size());
			} while (callMonthlyActivityReport.hasMoreResults());
			System.out.println("List of outer loop ::::::::" + list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<dispatch_register_report2_with_parameters> getDispatchRegisterRevisedReport2DoctorSupply(
			ReportBean bean) throws Exception {
		List<dispatch_register_report2_with_parameters> list = new ArrayList<>();
		EntityManager em = null;
		try {

			System.out.println("inside the new dispatch_register_report2");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em
					.createNamedStoredProcedureQuery("call_dispatch_register_report2_with_parameters");
			System.out.println("fin_year_flag::::" + bean.getFinyear());
			System.out.println("FIN_YEAR_ID::::" + bean.getFinyearflag());
			System.out.println("disp type::::" + bean.getDispatchType());
			System.out.println(
					"startdate::::" + MedicoResources.convertUtilDateToString_YYYY_MM_DD_NEW_(bean.getStartDate()));
			System.out.println(
					"endDate::::" + MedicoResources.convertUtilDateToString_YYYY_MM_DD_NEW_(bean.getEndDate()));
			System.out.println("prod_type_id::::" + bean.getProdType());
			System.out.println("prod_id::::" + bean.getProdName());
			System.out.println("div_id::::" + bean.getDivId());
			System.out.println("fs_id::::" + bean.getEmpName());
			System.out.println("transporter_name::::" + bean.getcName());
			System.out.println("delivery_status::::" + bean.getDeliStatus());
			System.out.println("chlno::::" + bean.getChallanNum());
			if (bean.getDispatchType().equalsIgnoreCase("D")) {
				System.out.println("doc_id::::" + bean.getDocName());
			} else {
				System.out.println("doc_id::::" + "0");
			}
			System.out.println("fs_id::::" + bean.getEmpNumber());
			System.out.println("rm_id::::" + bean.getRegName());
			System.out.println("terr_id::::" + bean.getTerrCode());
			if (!bean.getChallanDate().equalsIgnoreCase("0") || !bean.getChallanDate().trim().equalsIgnoreCase("")) {
				System.out.println("chldt::::" + bean.getChallanDate());
			} else {
				System.out.println("chldt::::" + 0);
			}
			System.out.println("doc_resp::::" + bean.getDocResponse());
			System.out.println("lrno::::" + bean.getLrNum());

			if (!bean.getLrDate().equalsIgnoreCase("0") || !bean.getLrDate().trim().equalsIgnoreCase("")) {
				System.out.println("lrdt::::" + bean.getLrDate());
			} else {
				System.out.println("lrdt::::" + 0);
			}
			System.out.println("dm_id::::" + bean.getDmName());

			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear().trim());
			callMonthlyActivityReport.setParameter("FIN_YEAR_ID", bean.getFinyearflag().trim());
			callMonthlyActivityReport.setParameter("dsptype", "D");
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD_NEW_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_YYYY_MM_DD_NEW_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("prod_type_id", bean.getProdType());
			callMonthlyActivityReport.setParameter("prod_id", bean.getProdName());
			callMonthlyActivityReport.setParameter("div_id", bean.getDivId());
			callMonthlyActivityReport.setParameter("transporter_name", bean.getcName());
			callMonthlyActivityReport.setParameter("delivery_status", bean.getDeliStatus());
			callMonthlyActivityReport.setParameter("chlno", bean.getChallanNum());
			if (bean.getDocName() == null) {
				callMonthlyActivityReport.setParameter("doc_id", "0");
			} else {
				callMonthlyActivityReport.setParameter("doc_id", bean.getDocName());
			}
			if (bean.getRegName() == null || bean.getDmName() == null) {
				callMonthlyActivityReport.setParameter("rm_id", "0");
				callMonthlyActivityReport.setParameter("dm_id", "0");
			} else {
				callMonthlyActivityReport.setParameter("rm_id", bean.getRegName());
				callMonthlyActivityReport.setParameter("dm_id", bean.getDmName());
			}
			if (bean.getEmpName() == null) {
				callMonthlyActivityReport.setParameter("fs_id", "0");
			} else {
				callMonthlyActivityReport.setParameter("fs_id", bean.getEmpName());
			}

			callMonthlyActivityReport.setParameter("terr_id", bean.getTerrCode());
			if (bean.getChallanDate().equalsIgnoreCase("0") || bean.getChallanDate().trim().equalsIgnoreCase("")
					|| bean.getChallanDate().trim().equalsIgnoreCase(" ")) {
				callMonthlyActivityReport.setParameter("chldt", "0");
			} else {
				callMonthlyActivityReport.setParameter("chldt", bean.getChallanDate());
			}
			if (bean.getDocResponse() == null || bean.getDocResponse().equalsIgnoreCase("0")) {
				callMonthlyActivityReport.setParameter("doc_resp", "0");
			} else {
				callMonthlyActivityReport.setParameter("doc_resp", bean.getDocResponse());
			}
			callMonthlyActivityReport.setParameter("lrno", bean.getLrNum());
			if (bean.getLrDate().equalsIgnoreCase("0") || bean.getLrDate().trim().equalsIgnoreCase("")
					|| bean.getLrDate().trim().equalsIgnoreCase(" ")
					|| bean.getLrDate().trim().equalsIgnoreCase(null)) {
				callMonthlyActivityReport.setParameter("lrdt", "0");
			} else {
				callMonthlyActivityReport.setParameter("lrdt", bean.getLrDate());
			}

			do {
				list = callMonthlyActivityReport.getResultList();
			} while (callMonthlyActivityReport.hasMoreResults());
			System.out.println("List size of the dispatch_register_report2_with_parameters ::::::::" + list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<DispatchDetailGstReport> callDisptachDetailGstReport(ReportBean bean) throws Exception {
		List<DispatchDetailGstReport> list = null;
		EntityManager em = null;
		try {

			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag " + bean.getFinyear());
			System.out.println("fin_year_id " + bean.getFinyearflag());
			System.out.println("subTeamCode " + bean.getSub_team_code());
			System.out.println("Rate  " + bean.getRate());

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callDispatchDetailsgstreport");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());
			callDisptachSumDetReport.setParameter("TEAM_CODE", bean.getSub_team_code());
			callDisptachSumDetReport.setParameter("RATE_OPTION", bean.getRate());
			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of dispatch details ::" + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<SummaryChallanReportEInvoice> callSummaryChallanReportEInv(ReportBean bean) throws Exception {
		List<SummaryChallanReportEInvoice> list = null;
		EntityManager em = null;
		try {
			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("field_level" + bean.getFstaff_id_o());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag" + bean.getFinyear());
			System.out.println("fin_year_id" + bean.getFinyearflag());

			em = emf.createEntityManager();

			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callSummaryChallanReportEinv");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());

			list = callDisptachSumDetReport.getResultList();

			System.out.println("list : " + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<BlkChallansGeneratedLog> getBlkChallasnsGeneratedLogReport(String finyr, String periodcd, Long reqId)
			throws Exception {
		List<BlkChallansGeneratedLog> list = null;
		EntityManager em = null;

		System.out.println("getBlkChallasnsGeneratedLogReport ");

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();

//		sb.append("select ROW_NUMBER() OVER(ORDER BY dsp_fin_year,PR.PERIOD_NAME,DIV_DISP_NM) AS Row,");
//		sb.append(" bl.dsp_fin_year,PR.PERIOD_NAME AS dsp_period_code,");
//		sb.append(" dv.div_disp_nm,bl.blk_hcp_req_no,bl.total_requests,");
//		sb.append(" bl.prod_type,bl.disp_to_type,bl.from_challan_no,bl.to_challan_no,bl.total_challans,");
//		sb.append(" ah.blk_hcp_req_no");
//		sb.append(" from");
//		sb.append(" ( select blk_hcp_req_no,count(*) totreq from alloc_header ah");
//		sb.append(" where ah.alloc_fin_year=:finyr and ah.alloc_period_code=:periodcd");
//		sb.append(" and ( ah.blk_hcp_req_no is not null and ah.blk_hcp_req_no <>'')");
//		sb.append(" group by  blk_hcp_req_no");
//		sb.append("	)ah");
//		sb.append("	left join BLK_CHALLANS_GENERATED_LOG bl on bl.blk_hcp_req_NO=ah.blk_hcp_req_NO");
//		sb.append(" left join div_master dv on dv.div_id=bl.dsp_div_id");
//		sb.append(" JOIN PERIOD PR ON RTRIM(PR.PERIOD_FIN_YEAR)+RTRIM(PR.PERIOD_CODE) = RTRIM(BL.DSP_FIN_YEAR)+RTRIM(BL.DSP_PERIOD_CODE) AND  PR.PERIOD_status='A'");
//		sb.append(" where BL.DSP_FIN_YEAR=:finyr AND BL.DSP_PERIOD_CODE=:periodcd");

			sb.append("select ROW_NUMBER() OVER(ORDER BY dsp_fin_year,PR.PERIOD_NAME,DIV_DISP_NM) AS Row,");
			sb.append(" bl.dsp_fin_year,PR.PERIOD_NAME AS dsp_period_code,");
			sb.append(" dv.div_disp_nm,bl.blk_hcp_req_no,bl.total_requests,");
			sb.append(" bl.prod_type,bl.disp_to_type,bl.from_challan_no,bl.to_challan_no,bl.total_challans,");
			sb.append(" ah.blk_hcp_req_no");
			sb.append(" from");
			sb.append(" ( select blk_hcp_req_no,count(*) totreq from alloc_header ah");
			sb.append(" where ah.alloc_fin_year=:finyr and ah.alloc_period_code=:periodcd");
			sb.append(" and ( ah.blk_hcp_req_no is not null and ah.blk_hcp_req_no <>'')");
			sb.append(" group by  blk_hcp_req_no");
			sb.append(" )ah");
			sb.append(" left join BLK_CHALLANS_GENERATED_LOG bl on bl.blk_hcp_req_NO=ah.blk_hcp_req_NO");
			sb.append(" left join div_master dv on dv.div_id=bl.dsp_div_id,");
			sb.append(" v_dispatch_period pr");
			sb.append(" where BL.DSP_FIN_YEAR=:finyr AND BL.DSP_PERIOD_CODE=:periodcd");

			if (reqId.compareTo(0l) != 0) {
				sb.append(" and blk_hcp_req_id=:reqId");
			}
			sb.append(" ORDER BY dsp_fin_year,dsp_period_code,DIV_DISP_NM");

			/* Query query = em.createNativeQuery(q,Tuple.class); */
			Query query = em.createNativeQuery(sb.toString(), BlkChallansGeneratedLog.class);
			query.setParameter("finyr", finyr);
			query.setParameter("periodcd", periodcd);
			if (reqId.compareTo(0l) != 0) {
				query.setParameter("reqId", reqId);
			}
			list = query.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return list;

	}

	@Override
	public List<Object> getBrandsForTeamStmtReport(String empId, String divId) throws Exception {
		EntityManager em = null;
		List<Object> list = null;

		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select P.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME ");
			sb.append(" from am_m_login_detail lg , SAPRODGRP sg , SMPITEM P ");
			sb.append(" where lg.ld_emp_cb_id =:empId ");
			sb.append(" and P.SMP_SA_PROD_GROUP = sg.SA_PROD_GROUP ");
			if (divId.equalsIgnoreCase("0")) {
				sb.append(" AND (P.SMP_STD_DIV_ID IN (select prod_div_id from divmap ) ");
				sb.append(
						" OR P.SMP_STD_DIV_ID= P.SMP_STD_DIV_ID) group by P.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME order by sg.SA_GROUP_NAME ");
			} else {
				sb.append(" AND (P.SMP_STD_DIV_ID IN (select prod_div_id from divmap where base_div_id=:teamId ) ");
				sb.append(
						" OR P.SMP_STD_DIV_ID=:teamId) group by P.SMP_SA_PROD_GROUP,sg.SA_GROUP_NAME order by sg.SA_GROUP_NAME");
			}
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("empId", empId);
			if (!divId.equalsIgnoreCase("0")) {
				query.setParameter("teamId", divId);
			}
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				AllocationBean object = null;
				for (Tuple t : tuples) {
					object = new AllocationBean();
					object.setSmp_sa_prod_group(Long.valueOf(t.get("SMP_SA_PROD_GROUP", Integer.class)));
					object.setSa_group_name(t.get("SA_GROUP_NAME", String.class));
					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public String getSubCompanyNameByLocId(String loc_id) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		String companyName = null;

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT sc.subcomp_nm");
			sb.append(" from location lc");
			sb.append(" join Sub_Company sc on sc.SubComp_id = lc.loc_SubComp_id");
			sb.append(" where lc.loc_id=:loc_id");

			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("loc_id", loc_id);
			companyName = (String) query.getSingleResult();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return companyName;
	}

	@Override
	public List<FinalApprovalLogBean> getFinalApprovalLog() throws Exception {
		EntityManager em = null;
		List<FinalApprovalLogBean> list = null;

		try {
			StringBuffer sb = new StringBuffer();
//			sb.append(" SELECT case when a.ACFIL_TRAN_TYPE='11' then 'Spl. Allocation' END TRAN_TYPE,");
//			sb.append(" TM.TASK_NAME APPROVAL_TYPE,isnull(ARH.request_no,'') ALLOC_REQUEST_NO, ARH.alloc_req_date ALLOC_REQ_DATE,");
//			sb.append(" case when tm.fs_id=1 then 'SAMPLE' WHEN TM.FS_ID=2 THEN 'PI' ELSE 'NONE' END PRODUCT_TYPE,");
//			sb.append(" isnull(hr.emp_id ,'') EMPLOYEE_ID, isnull(hr.emp_fnm,'')  EMPLOYEE_NAME,");			
//			sb.append(" isnull(v.EmailId_of_Approver,'No Email Approval') EMAILID_OF_APPROVER,");
//			sb.append(" isnull(V.STATUS,'') APPROVAL_STATUS ,");
//			sb.append(" isnull(V.AMV_MOD_IP_ADD,'') APPR_RECD_FROM_IP,");
//			sb.append(" V.APPROVER_USER_ID APPROVER_USER_ID,");			
//			sb.append(" V.APPROVAL_DATETIME  APPROVAL_EMAIL_SEND_DATETIME,");
//			sb.append(" V.AMV_MOD_DT  APPROVER_MADE_APPROVAL_DATE_TIME,");
//			sb.append(" isnull(A.ACFIL_TRAN_REF_ID,0) TRANSACTION_REF");
//			sb.append(" FROM appr_mail_vali  V");			
//			sb.append(" FULL JOIN ACTIVITI_FINALAPPR_LOG A ON A.ACFIL_TRAN_REF_ID=V.TRANREFID");
//			sb.append(" FULL join alloc_req_hdr arh on (arh.ALLOC_REQ_ID = v.TranRefId OR ARH.ALLOC_REQ_ID= A.ACFIL_TRAN_REF_ID)");
//			sb.append(" left join hr_m_employee hrARH on hrARH.emp_id=ARH.REQ_INS_USER_ID");
//			sb.append(" left join hr_m_employee hr on hr.emp_id=v.approver_user_id");		
//			sb.append(" LEFT JOIN TASKS_MASTER TM ON TM.TRAN_TYPE = A.ACFIL_TRAN_TYPE");
//			sb.append(" LEFT JOIN TASK_MASTER_DTL TD ON TD.TASK_ID=TM.TASK_MASTER_ID");
//			sb.append(" LEFT join hr_m_employee acti_val on rtrim(acti_val.emp_id)=rtrim(td.IDENTITYLINKTYPE_VAL)");
//			sb.append(" LEFT JOIN BLK_HCP_REQ_HEADER BH ON BH.BLK_HCP_REQ_ID = A.ACFIL_TRAN_REF_ID");			
//			sb.append(" WHERE");
//			sb.append(" TM.TRAN_TYPE IN ('11','12')");
//			sb.append(" and");
//			sb.append(" acti_val.emp_id=a.approver");			
//			sb.append(" UNION ALL");
//			sb.append(" SELECT");
//			sb.append(" case when a.ACFIL_TRAN_TYPE='21' THEN 'Bulk Doctor Alloc'  END TRAN_TYPE,");
//			sb.append(" TM.TASK_NAME APPROVAL_TYPE,isnull(bh.BLK_HCP_REQ_NO,'') ALLOC_REQUEST_NO, bh.BLK_HCP_REQ_DATE ALLOC_REQ_DATE,");			
//			sb.append(" case when tm.fs_id=1 then 'SAMPLE' WHEN TM.FS_ID=2 THEN 'PI' ELSE 'NONE' END PRODUCT_TYPE,");
//			sb.append(" isnull(acti_val.emp_id ,'') EMPLOYEE_ID, isnull(acti_val.emp_fnm,'') EMPLOYEE_NAME,");
//			sb.append(" isnull(v.EmailId_of_Approver,'No Email Approval') EMAILID_OF_APPROVER,");
//			sb.append(" isnull(V.STATUS,'Y') APPROVAL_STATUS ,");		
//			sb.append(" isnull(ACFIL_ins_ip_add,'') APPR_RECD_FROM_IP,");
//			sb.append(" A.ACFIL_ins_usr_id  APPROVER_USER_ID,");
//			sb.append(" NULL   APPROVAL_EMAIL_SEND_DATETIME,");
//			sb.append(" ACFIL_ins_dt  APPROVER_MADE_APPROVAL_DATE_TIME,");			
//			sb.append(" isnull(A.ACFIL_TRAN_REF_ID,0) TRANSACTION_REF");
//			sb.append(" FROM appr_mail_vali  V");
//			sb.append(" FULL JOIN ACTIVITI_FINALAPPR_LOG A ON A.ACFIL_TRAN_REF_ID=V.TRANREFID");
//			sb.append(" full JOIN BLK_HCP_REQ_HEADER BH ON BH.BLK_HCP_REQ_ID = A.ACFIL_TRAN_REF_ID");			
//			sb.append(" left join hr_m_employee hrARH on hrARH.emp_id=bh.blk_INS_USR_ID");
//			sb.append(" left join hr_m_employee hr on hr.emp_id=v.approver_user_id");
//			sb.append(" LEFT JOIN TASKS_MASTER TM ON TM.TRAN_TYPE = A.ACFIL_TRAN_TYPE");
//			sb.append(" LEFT JOIN TASK_MASTER_DTL TD ON TD.TASK_ID=TM.TASK_MASTER_ID");			
//			sb.append(" LEFT join hr_m_employee acti_val on rtrim(acti_val.emp_id)=rtrim(td.IDENTITYLINKTYPE_VAL)");
//			sb.append(" WHERE");
//			sb.append(" TM.TRAN_TYPE IN ('21','22')");
//			sb.append(" and");			
//			sb.append(" acti_val.emp_id=a.approver");
//			sb.append(" order by TRAN_TYPE DESC,alloc_req_date");			

			sb.append(" SELECT");
			sb.append(" case when a.ACFIL_TRAN_TYPE='11' then 'Spl. Allocation' END TRAN_TYPE,");
			sb.append(
					" TM.TASK_NAME APPROVAL_TYPE,isnull(ARH.request_no,'') ALLOC_REQUEST_NO, convert(date,ARH.alloc_req_date)  ALLOC_REQ_DATE,");
			sb.append(" case when tm.fs_id=1 then 'SAMPLE' WHEN TM.FS_ID=2 THEN 'PI' ELSE 'NONE' END PRODUCT_TYPE,");
			sb.append(" isnull(hr.emp_id ,'') EMPLOYEE_ID, isnull(hr.emp_fnm,'')  EMPLOYEE_NAME,");
			sb.append(" isnull(v.EmailId_of_Approver,'No Email Approval') EMAILID_OF_APPROVER,");
			sb.append(" isnull(V.STATUS,ARH.ALLOC_APPR_STATUS) APPROVAL_STATUS_EMAIL ,");
			sb.append(" isnull(A.ACFIL_ins_ip_add,'') APPR_RECD_FROM_IP,");
			sb.append(" A.ACFIL_ins_usr_id  APPROVER_USER_ID,");
			sb.append(" V.APPROVAL_DATETIME  APPROVAL_EMAIL_SEND_DATETIME,");
			sb.append(" A.ACFIL_ins_dt  APPROVER_MADE_APPROVAL_DATE_TIME,");
			sb.append(" isnull(A.ACFIL_TRAN_REF_ID,0) TRANSACTION_REF");
			sb.append(" FROM appr_mail_vali  V");
			sb.append(" FULL JOIN ACTIVITI_FINALAPPR_LOG A ON A.ACFIL_TRAN_REF_ID=V.TRANREFID");
			sb.append(
					" FULL join alloc_req_hdr arh on (arh.ALLOC_REQ_ID = v.TranRefId OR ARH.ALLOC_REQ_ID= A.ACFIL_TRAN_REF_ID)");
			sb.append("  left join hr_m_employee hrARH on hrARH.emp_id=ARH.REQ_INS_USER_ID");
			sb.append("  left join hr_m_employee hr on hr.emp_id=v.approver_user_id");
			sb.append("  LEFT JOIN TASKS_MASTER TM ON TM.TRAN_TYPE = A.ACFIL_TRAN_TYPE");
			sb.append("  LEFT JOIN TASK_MASTER_DTL TD ON TD.TASK_ID=TM.TASK_MASTER_ID");
			sb.append("  LEFT join hr_m_employee acti_val on rtrim(acti_val.emp_id)=rtrim(td.IDENTITYLINKTYPE_VAL)");
			sb.append("  LEFT JOIN BLK_HCP_REQ_HEADER BH ON BH.BLK_HCP_REQ_ID = A.ACFIL_TRAN_REF_ID");
			sb.append("  WHERE");
			sb.append("  TM.TRAN_TYPE IN ('11','12')");
			sb.append("   and");
			sb.append("   acti_val.emp_id=a.approver");
			sb.append("   UNION ALL");
			sb.append(" SELECT  ");
			sb.append(" case when a.ACFIL_TRAN_TYPE='21' THEN 'Bulk Doctor Alloc'  END TRAN_TYPE,");
			sb.append(
					" TM.TASK_NAME APPROVAL_TYPE,isnull(bh.BLK_HCP_REQ_NO,'') ALLOC_REQUEST_NO, convert(date,bh.BLK_HCP_REQ_DATE) ALLOC_REQ_DATE,");
			sb.append(" case when tm.fs_id=1 then 'SAMPLE' WHEN TM.FS_ID=2 THEN 'PI' ELSE 'NONE' END PRODUCT_TYPE,");
			sb.append(" isnull(acti_val.emp_id ,'') EMPLOYEE_ID, isnull(acti_val.emp_fnm,'') EMPLOYEE_NAME,");
			sb.append(" isnull(v.EmailId_of_Approver,'No Email Approval') EMAILID_OF_APPROVER,");
			sb.append(" '-' APPROVAL_STATUS_EMAIL ,");
			sb.append(" isnull(ACFIL_ins_ip_add,'') APPR_RECD_FROM_IP,");
			sb.append(" A.ACFIL_ins_usr_id  APPROVER_USER_ID,");
			sb.append(" NULL   APPROVAL_EMAIL_SEND_DATETIME,");
			sb.append(" ACFIL_ins_dt  APPROVER_MADE_APPROVAL_DATE_TIME,");
			sb.append(" isnull(A.ACFIL_TRAN_REF_ID,0) TRANSACTION_REF");
			sb.append(" FROM appr_mail_vali  V");
			sb.append(" FULL JOIN ACTIVITI_FINALAPPR_LOG A ON A.ACFIL_TRAN_REF_ID=V.TRANREFID");
			sb.append(" full JOIN BLK_HCP_REQ_HEADER BH ON BH.BLK_HCP_REQ_ID = A.ACFIL_TRAN_REF_ID");
			sb.append("  left join hr_m_employee hrARH on hrARH.emp_id=bh.blk_INS_USR_ID");
			sb.append("  left join hr_m_employee hr on hr.emp_id=v.approver_user_id");
			sb.append("  LEFT JOIN TASKS_MASTER TM ON TM.TRAN_TYPE = A.ACFIL_TRAN_TYPE");
			sb.append("  LEFT JOIN TASK_MASTER_DTL TD ON TD.TASK_ID=TM.TASK_MASTER_ID");
			sb.append("  LEFT join hr_m_employee acti_val on rtrim(acti_val.emp_id)=rtrim(td.IDENTITYLINKTYPE_VAL)");
			sb.append("  WHERE");
			sb.append("  TM.TRAN_TYPE IN ('21','22')");
			sb.append("   and");
			sb.append("   acti_val.emp_id=a.approver");
			sb.append(" order by TRAN_TYPE DESC,alloc_req_date");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				FinalApprovalLogBean object = null;
				for (Tuple t : tuples) {
					object = new FinalApprovalLogBean();
					object.setTran_type(t.get("TRAN_TYPE", String.class));
					object.setApproval_type(t.get("APPROVAL_TYPE", String.class));
					object.setAlloc_request_no(t.get("ALLOC_REQUEST_NO", String.class));
					object.setAlloc_request_date(t.get("ALLOC_REQ_DATE", Date.class));
					object.setProduct_type(t.get("PRODUCT_TYPE", String.class));
					object.setEmployee_id(t.get("EMPLOYEE_ID", String.class));
					object.setEmployee_name(t.get("EMPLOYEE_NAME", String.class));
					object.setEmailid_of_approver(t.get("EMAILID_OF_APPROVER", String.class));
					object.setApproval_status(t.get("APPROVAL_STATUS_EMAIL", String.class));
					object.setAppr_recd_from_ip(t.get("APPR_RECD_FROM_IP", String.class));
					object.setApprover_user_id(t.get("APPROVER_USER_ID", String.class));
					object.setApproval_email_send_datetime(t.get("APPROVAL_EMAIL_SEND_DATETIME", Date.class));
					object.setApprover_made_approval_date_time(t.get("APPROVER_MADE_APPROVAL_DATE_TIME", Date.class));
					object.setTransaction_ref(Long.valueOf(t.get("TRANSACTION_REF", Integer.class)));

					list.add(object);
				}
				if (!list.isEmpty() && list.size() > 0)
					return list;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	public List<AllocationDetailReportModel> getAllocationDetailReportDataDiv(ReportBean bean) {
		// TODO Auto-generated method stub
		List<AllocationDetailReportModel> lst = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("EmptyCsvMktgViewWithTeam");
			query.setParameter("PER_CD", bean.getPeriod_code());
			query.setParameter("FINYR", bean.getFin_year());
			query.setParameter("DIVID", bean.getDivId());
			query.setParameter("USER_ID", bean.getEmp_id());
			lst = query.getResultList();

			System.out.println("List ::" + (lst == null ? "null" : lst.size()));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (lst != null && !lst.isEmpty()) {
			return lst;
		}
		return null;
	}

	@Override
	public List<SmpItem> getSmpItemList(Long locId) throws Exception {
		// TODO Auto-generated method stub
		List<SmpItem> lst = null;
		EntityManager em = null;
		try {

			em = emf.createEntityManager();
			System.out.println("locId :: " + locId);

			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT L.loc_godwadd1,");
			sb.append(
					" SI.SMP_PROD_NAME,SI.SMP_PROD_CD ,sum(AD.ALLOCDTL_CURR_ALLOC_QTY - AD.ALLOCDTL_SUPPLY_QTY ) AUTODISP_PENDING_QTY");
			sb.append(" FROM ALLOC_DETAIL  AD");
			sb.append(" JOIN ALLOC_HEADER AH ON AH.ALLOC_ID=AD.ALLOCDTL_ALLOC_QTY");
			sb.append(" JOIN ARTICLE_SCHREQ_HDR ASH ON ASH.ARTICLE_REQ_ID =  AH.ALLOC_REQ_ID");
			sb.append(" JOIN LOCATION L ON L.LOC_ID = ASH.LOC_ID");
			sb.append(" JOIN SMPITEM SI ON SI.SMP_PROD_ID = AD.ALLOCDTL_PROD_ID");
			sb.append(" JOIN CUSTOMER_MASTER CU ON CU.CUST_ID=ASH.CUST_ID");
			sb.append(" WHERE (ALLOCDTL_CURR_ALLOC_QTY - ALLOCDTL_SUPPLY_QTY ) >0");
			if (locId != 0l) {
				sb.append(" AND L.LOC_ID=:locId");
			}
			sb.append(" GROUP BY L.loc_godwadd1,si.smp_prod_name,SI.SMP_PROD_CD");
			sb.append(" ORDER BY L.loc_godwadd1,si.smp_prod_name,SI.SMP_PROD_CD");

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			if (locId != 0l) {
				query.setParameter("locId", locId);
			}

			List<Tuple> list = query.getResultList();

			if (list != null && !list.isEmpty()) {
				System.out.println("List :: " + list.size());
				lst = new ArrayList<>();
				SmpItem obj = null;
				for (Tuple t : list) {
					obj = new SmpItem();
					obj.setLoc_godwadd1(t.get("loc_godwadd1", String.class));
					obj.setSmp_prod_name(t.get("SMP_PROD_NAME", String.class));
					obj.setSmp_prod_cd(t.get("SMP_PROD_CD", String.class));
					obj.setAutodisp_pending_qty(t.get("AUTODISP_PENDING_QTY", BigDecimal.class));

					lst.add(obj);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article_Stock_Statement> getArticleInventoryReport(ReportBean bean) throws Exception {
		List<Article_Stock_Statement> list = null;
		EntityManager em = null;

		System.out.println("div_id " + "0");
		System.out.println("loc_id " + bean.getLocId());
		System.out.println("nilstock " + bean.getZeroStock());
		System.out.println("exp_ind " + bean.getNearExp());
		System.out.println("USER_ID " + bean.getEmp_id());

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery stockstatmentdata = em.createNamedStoredProcedureQuery("callARTICLE_STOCK_STATEMENT");
			stockstatmentdata.setParameter("div_id", "33");
			stockstatmentdata.setParameter("loc_id", bean.getLocId());
			stockstatmentdata.setParameter("nilstock", bean.getZeroStock());
			stockstatmentdata.setParameter("exp_ind", bean.getNearExp());
			stockstatmentdata.setParameter("USER_ID", bean.getEmp_id());
			stockstatmentdata.setParameter("fin_year_flag", bean.getFinyearflag());
			stockstatmentdata.setParameter("fin_year_id", bean.getFinYear_id());
			list = stockstatmentdata.getResultList();
			System.out.println("article inventory data List size ::::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<Article_Stock_Statement_Itemwise> getArticleInventoryItemwiseReport(ReportBean bean) throws Exception {
		List<Article_Stock_Statement_Itemwise> list = null;
		EntityManager em = null;

		System.out.println("div_id " + "0");
		System.out.println("loc_id " + bean.getLocId());
		System.out.println("nilstock " + bean.getZeroStock());
		System.out.println("exp_ind " + bean.getNearExp());
		System.out.println("USER_ID " + bean.getEmp_id());

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery stockstatmentdata = em
					.createNamedStoredProcedureQuery("callARTICLE_STOCK_STATEMENT_ITEMWISE");
			stockstatmentdata.setParameter("div_id", "33");
			stockstatmentdata.setParameter("loc_id", bean.getLocId());
			stockstatmentdata.setParameter("nilstock", bean.getZeroStock());
			stockstatmentdata.setParameter("exp_ind", bean.getNearExp());
			stockstatmentdata.setParameter("USER_ID", bean.getEmp_id());
			stockstatmentdata.setParameter("fin_year_flag", bean.getFinyearflag());
			stockstatmentdata.setParameter("fin_year_id", bean.getFinYear_id());
			list = stockstatmentdata.getResultList();
			System.out.println("article inventory data List size ::::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<Scheme_Summary> generateSchemeSummary(String schemeIds, String finyear, String finyear_flag)
			throws Exception {
		List<Scheme_Summary> list = null;
		EntityManager em = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("callSchemeSummary");
			procedureQuery.setParameter("TRD_SCH_SLNO", schemeIds);
			procedureQuery.setParameter("fin_year_flag", finyear_flag);
			procedureQuery.setParameter("fin_year_id", finyear);
			list = procedureQuery.getResultList();
			System.out.println("List ::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<ArticleSchemeExceptionReport> articleExceptionReport(ReportBean bean) throws Exception {
		// TODO Auto-generated method stub
		List<ArticleSchemeExceptionReport> list = null;
		EntityManager em = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedureQuery = em
					.createNamedStoredProcedureQuery("callARTICLE_SCHEME_EXCEPTION_REPORTT");

			procedureQuery.setParameter("subcompid", bean.getSubCompany());
			procedureQuery.setParameter("frdt", bean.getFromDate());
			procedureQuery.setParameter("todt", bean.getToDate());
			procedureQuery.setParameter("LOCID", bean.getLocList().toString().replace("[", "(").replace("]", ")"));
			procedureQuery.setParameter("FIN_YEAR_FLAG", bean.getFinyearflag());
			procedureQuery.setParameter("FIN_YEAR_ID", bean.getFinYear_id());
			list = procedureQuery.getResultList();

			System.out.println("List ::" + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}

		return null;

	}

	@Override
	public List<articleScheme> getarticleScheme() {

		List<articleScheme> data = null;

		List<Object[]> list = Collections.emptyList();
		try {
			StringBuffer sBuffer = new StringBuffer();

			sBuffer.append(
					" SELECT AH.EXP_ID,AH.ERR_MSG,  AD.TRD_SCH_SLNO,AH.ARTICLE_REQ_ID,AD.ARTICLE_REQ_DTL_ID,AD.ARTICLE_SCHM_NAME,AD.ARTICLE_NAME,AD.ARTREQ_SALE_PROD_CD ART_SALE_PROD_CD, ");
			sBuffer.append(
					" AD.ARTREQ_SALE_PROD_NAME  ART_SALE_PROD_NAME,AD.ARTREQ_SALE_QTY_BILLED ART_BILLED_QTY,AD.ARTREQ_QTY ART_QTY,AH.INVOICE_NO INVOICE_NO, ");
			sBuffer.append(
					" AD.AP_PERIOD AP,AD.SAP_PLANT_CD,AD.COMPANY_CD,AD.CFA_LOCATION,AD.SAP_INV_NO,AD.SAP_INV_DT,AD.SAP_CUST_CD,AD.CUSTOMER_NAME,AD.CITY, ");
			sBuffer.append(
					" AD.SAP_SALE_PROD_CD,AD.SAP_SALE_PROD_NAME,AD.Billed_Qty,AD.FREE_QTY,AD.BILLING_RATE,AD.BILLING_VALUE,AD.ARTREQ_SALE_QTY_BILLED ARTICLE_SALE_BILL_QTY_ENTERED, ");
			sBuffer.append(" AD.ARTICLE_RATE,AD.ARTICLE_VALUE,AD.DIFF_ARTICLE_QTY ERR_BILL_QTY ");
			sBuffer.append(
					" FROM ARTICLE_EXCEPTION_HEADER AH LEFT OUTER JOIN ARTICLE_EXCEPTION_DETAIL AD ON AD.EXP_ID = AH.EXP_ID ");
			sBuffer.append(" ORDER BY AH.EXP_ID ");

			Query query = entityManager.createNativeQuery(sBuffer.toString(), Tuple.class);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				data = new ArrayList<>();
				articleScheme object = null;
				for (Tuple t : tuples) {
					object = new articleScheme();
					object.setExp_id(Long.valueOf(t.get("EXP_ID", BigDecimal.class).toString()));
					object.setErr_msg(t.get("ERR_MSG", String.class));
					object.setTrd_sch_slno(t.get("TRD_SCH_SLNO", BigDecimal.class) != null
							? t.get("TRD_SCH_SLNO", BigDecimal.class).toString()
							: null);
					object.setArticle_req_id(Long.valueOf(t.get("ARTICLE_REQ_ID", BigDecimal.class).toString()));
					object.setArticle_req_dtl_id(t.get("ARTICLE_REQ_DTL_ID", BigDecimal.class) != null
							? Long.valueOf(t.get("ARTICLE_REQ_DTL_ID", BigDecimal.class).toString())
							: 0l);
					object.setArticle_schm_name(t.get("ARTICLE_SCHM_NAME", String.class));
					object.setArticle_name(t.get("ARTICLE_NAME", String.class));
					object.setArt_sale_prod_cd(t.get("ART_SALE_PROD_CD", String.class));
					object.setArt_sale_prod_name(t.get("ART_SALE_PROD_NAME", String.class));
					object.setArt_billed_qty(t.get("ART_BILLED_QTY", BigDecimal.class));
					object.setArt_qty(t.get("ART_QTY", BigDecimal.class));
					object.setInvoice_no(t.get("INVOICE_NO", String.class));
					object.setAp(
							t.get("AP", BigDecimal.class) != null ? t.get("AP", BigDecimal.class).toString() : null);
					object.setSap_plant_cd(t.get("SAP_PLANT_CD", String.class));
					object.setCompany_cd(t.get("COMPANY_CD", String.class));
					object.setCfa_location(t.get("CFA_LOCATION", String.class));
					object.setSap_inv_no(t.get("SAP_INV_NO", String.class));
					object.setSap_inv_dt(t.get("SAP_INV_DT", Date.class));
					object.setSap_cust_cd(t.get("SAP_CUST_CD", String.class));
					object.setCustomer_name(t.get("CUSTOMER_NAME", String.class));
					object.setCity(t.get("CITY", String.class));
					object.setSap_sale_prod_cd(t.get("SAP_SALE_PROD_CD", String.class));
					object.setSap_sale_prod_name(t.get("SAP_SALE_PROD_NAME", String.class));
					object.setBilled_qty(t.get("Billed_Qty", BigDecimal.class));
					object.setFree_qty(t.get("FREE_QTY", BigDecimal.class));
					object.setBilling_rate(t.get("BILLING_RATE", BigDecimal.class));
					object.setBilling_value(t.get("BILLING_VALUE", BigDecimal.class));
					object.setArticle_sale_bill_qty_entered(t.get("ARTICLE_SALE_BILL_QTY_ENTERED", BigDecimal.class));
					object.setArticle_rate(t.get("ARTICLE_RATE", BigDecimal.class));
					object.setArticle_value(t.get("ARTICLE_VALUE", BigDecimal.class));
					object.setErr_bill_qty(t.get("ERR_BILL_QTY", BigDecimal.class));

					data.add(object);
				}
				if (!data.isEmpty() && data.size() > 0)
					return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(data);
		return data;
	}

	@Override
	public List<trd_scheme_mst_hdr> getExtendedValidities(List<String> schemeIds) throws Exception {
		List<trd_scheme_mst_hdr> returnList = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" select trd.TRD_SCH_SLNO, CASE when trd.cycle_no > 1");
		sb.append(" then arc.VALID_TO_DT else NULL END AS VALID_TO_DATE");
		sb.append(" from TRD_SCHEME_MST_HDR trd left join TRD_SCHEME_MST_HDR_ARCHIVE arc on");
		sb.append(" trd.TRD_SCH_SLNO = arc.TRD_SCH_SLNO and arc.cycle_no = 1");
		sb.append(" where trd.TRD_SCH_SLNO in (:schemeIds)");

		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("schemeIds", schemeIds);

		List<Tuple> tup_list = query.getResultList();
		if (tup_list != null && tup_list.size() > 0) {
			returnList = new ArrayList<trd_scheme_mst_hdr>();
			for (Tuple t : tup_list) {
				trd_scheme_mst_hdr hdr = new trd_scheme_mst_hdr();
				hdr.setTrd_sch_slno(t.get("TRD_SCH_SLNO", Integer.class).longValue());
				hdr.setValid_to_dt(t.get("VALID_TO_DATE") != null ? t.get("VALID_TO_DATE", Date.class) : null);
				returnList.add(hdr);
			}
		}
		return returnList;
	}

	@Override
	public List<ArticleSchemeSummaryReportCfa> articleSchemeSummaryReportCfa(ReportBean bean, String formattedStringLoc,
			String resultTrd_sch_slno, String formattedSaleprod) throws Exception {
		// TODO Auto-generated method stub
		List<ArticleSchemeSummaryReportCfa> list = null;
		EntityManager em = null;
		HashMap<String, Object> map = new HashMap<>();

		System.out.println("bean.getTrd_sch_slno() " + bean.getTrd_sch_slno().size());

		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedureQuery = em
					.createNamedStoredProcedureQuery("callARTICLE_SCHEME_SUMMARY_REPORT_CFA");
			procedureQuery.setParameter("TRD_SCH_SLNO", "(" + resultTrd_sch_slno + ")");
			procedureQuery.setParameter("CFA", formattedStringLoc);
			procedureQuery.setParameter("SALEPROD", formattedSaleprod);
			procedureQuery.setParameter("fin_year_flag","");
			procedureQuery.setParameter("fin_year_id", "");
			list = procedureQuery.getResultList();

			System.out.println("List ::" + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}

		return null;

	}

//	@Override
//	public List<Lrcsv_RevisedDownload_SG> getLrcsvdownload_Revised_data_SG(String strdate, String enddate,
//			String tbl_ind, String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp,
//			String deptloc, List<String> loc, List<String> div, List<String> dest, String cfa_to_stk_ind) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
	@Override
	public List<Lrcsv_RevisedDownload_SG> getLrcsvdownload_Revised_data_SG(String strdate, String enddate,
			String tbl_ind, String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp,
			String deptloc, List<String> loc, List<String> div, List<String> dest, String cfa_to_stockist_ind)
			throws Exception {

		List<Lrcsv_RevisedDownload_SG> list = null;
		String procedureToCall = "";
		EntityManager em = null;

		try {
			System.out.println("loc :: " + loc.size());
			System.out.println("div :: " + div.size());
			System.out.println("dest :: " + dest.size());

			StringBuffer locations = new StringBuffer();
			for (String a : loc) {
				locations.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String location = locations.toString().substring(0, locations.toString().length() - 1);
			System.out.println("locations :: " + location);

			StringBuffer divisions = new StringBuffer();
			for (String a : div) {
				divisions.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String division = divisions.toString().substring(0, divisions.toString().length() - 1);
			System.out.println("divisions :: " + division);

			StringBuffer destinations = new StringBuffer();
			for (String a : dest) {
				destinations.append(a + ",");
			}
			// System.out.println("locations :: "+locations.toString());
			String destination = destinations.toString().substring(0, destinations.toString().length() - 1);
			System.out.println("destination :: " + destination);

			tbl_ind = "A";
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			System.out.println("stock_at_cfa_ind::" + stock_at_cfa_ind);
			System.out.println("start" + strdate);
			System.out.println("start" + strdate);
//			 strdate = "2020/05/01";
//			 enddate = "2020/05/10";
			if (stk_at_cfa_option == null) {
				stk_at_cfa_option = "N";
			}
			System.out.println("stk_at_cfa_option::" + stk_at_cfa_option);
			if (stk_at_cfa_option.equalsIgnoreCase("Y") && stock_at_cfa_ind.equalsIgnoreCase("Y")) {
				procedureToCall = "call_LR_csv_download_revised_stock_cfa";
			} else if (cfa_to_stockist_ind.equalsIgnoreCase("Y")) {
				procedureToCall = "call_LR_csv_revised_download_stockist";
			} else {
				procedureToCall = "call_LR_csv_revised_download_sg";
			}

			System.out.println("tbl_ind::::" + tbl_ind + "::::strdate::::" + strdate + "::::enddate::::" + enddate
					+ "::::cfa::::" + cfa + "::::fsid::::" + fsid);

			em = emf.createEntityManager();

			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery(procedureToCall);
			procedurecall.setParameter("startdt", strdate);
			procedurecall.setParameter("enddt", enddate);
			procedurecall.setParameter("tabl_ind", tbl_ind);

			if (!deptloc.trim().equals("0") && cfa.equals("0")) {
				// System.out.println("INSIDE
				// IFFFFFFFFFFFF::::::::::::::"+temp.toString());
				procedurecall.setParameter("cfa", temp.toString());
			} else {

				if (deptloc.trim().equals("0") && cfa.equals("0")) {
					// System.out.println("INSIDE ELSEEEEEEEEEEE:::::::::::::"
					// + "(" + cfa + ")");
					procedurecall.setParameter("cfa", cfa);
				} else {
					procedurecall.setParameter("cfa", "(" + cfa + ")");
				}
			}
			procedurecall.setParameter("fsid", fsid);

			procedurecall.setParameter("div_id", division);
			procedurecall.setParameter("sumdisp_id", destination);
			procedurecall.setParameter("sumdsp_loc_id", location);
			
			
			
			
			
			list = procedurecall.getResultList();
			System.out.println("List :: " + list.size());

		} catch (Exception e) {
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return list;
	}

	@Override
	public List<IAADetailReport_SG> getIAADetailReportData_for_sg(String loc_id, String startDate, String endDate)
			throws Exception {
		List<IAADetailReport_SG> lst = null;
		EntityManager em = null;
		try {

			System.out.println("loc_id :: " + loc_id);
			System.out.println("startdate :: " + startDate);
			System.out.println("endDate :: " + endDate);

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("IAADetailReport_Revised_SG");

			procedurecall.setParameter("loc_id", loc_id);
			procedurecall.setParameter("startdate", startDate);
			procedurecall.setParameter("endDate", endDate);

			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<IAADetailReport_SG> getIAADetailReportPreviousData_for_sg(String locId, String startDate,
			String endDate) {
		List<IAADetailReport_SG> lst = null;
		EntityManager em = null;
		try {

			System.out.println("loc_id :: " + locId);
			System.out.println("startdate :: " + startDate);
			System.out.println("endDate :: " + endDate);

			em = emf.createEntityManager();
			StoredProcedureQuery procedurecall = em
					.createNamedStoredProcedureQuery("IAADetailReport_Revised_Previous_SG");

			procedurecall.setParameter("loc_id", locId);
			procedurecall.setParameter("startdate", startDate);
			procedurecall.setParameter("endDate", endDate);
			lst = procedurecall.getResultList();

			System.out.println("list :: " + lst.size());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return lst;
	}

	@Override
	public List<DispatchDetailReportRevised_SG> callDisptachDetReportRevised_SG(ReportBean bean) throws Exception {
		List<DispatchDetailReportRevised_SG> list = null;
		EntityManager em = null;
		try {

			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag " + bean.getFinyear());
			System.out.println("fin_year_id " + bean.getFinyearflag());
			System.out.println("subTeamCode " + bean.getSub_team_code());
			System.out.println("Rate  " + bean.getRate());

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callDispatchDetailsrevised_SG");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());
			callDisptachSumDetReport.setParameter("TEAM_CODE", bean.getSub_team_code());
			callDisptachSumDetReport.setParameter("RATE_OPTION", bean.getRate());
			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of dispatch details ::" + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<BatchwiseStockStmtReport_SG> getBatchwiseStockStmtData_SG(ReportBean bean, String stk_to_cfa_ind) {
		List<BatchwiseStockStmtReport_SG> list_SG = null;
		StoredProcedureQuery callDisptachSumDetReport = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();

			if ("N".equalsIgnoreCase(stk_to_cfa_ind)) {
				callDisptachSumDetReport = em.createNamedStoredProcedureQuery("callBatchwiseStockStmtReport");
			}
			System.out.println("piloc_id" + bean.getLocId());
			System.out.println("pidiv_id" + bean.getDivId());
			System.out.println("pvfrmdt" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("pvtodt" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("pvuserid" + bean.getUserId());
			System.out.println("pvChkzero" + bean.getZeroStock());
			System.out.println("exp_ind" + bean.getNearExp());
			System.out.println("nilstock" + bean.getZeroStock());
			System.out.println("FIN_YEAR_FLAG" + bean.getFinyear());
			System.out.println("fin_year_id" + bean.getFinyearflag());
			System.out.println("rate " + bean.getRate());

			callDisptachSumDetReport.setParameter("piloc_id", bean.getLocId());
			callDisptachSumDetReport.setParameter("pidiv_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("pvfrmdt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("pvtodt",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("pvuserid", bean.getUserId());
			callDisptachSumDetReport.setParameter("pvChkzero", bean.getZeroStock());
			callDisptachSumDetReport.setParameter("exp_ind", bean.getNearExp());
			callDisptachSumDetReport.setParameter("nilstock", bean.getZeroStock());
			callDisptachSumDetReport.setParameter("FINYEAR", bean.getFinyearflag());
			callDisptachSumDetReport.setParameter("FIN_YEAR_FLAG", bean.getFinyear());
			callDisptachSumDetReport.setParameter("RATE_OPTION", bean.getRate());
			list_SG = callDisptachSumDetReport.getResultList();

			System.out.println("list : " + list_SG.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list_SG != null && !list_SG.isEmpty()) {
			return list_SG;
		}
		return null;
	}

	@Override
	public List<DispatchDetailReportRevisedMdmNo> callDisptachDetReportRevisedMdmNo(ReportBean bean) throws Exception {
		List<DispatchDetailReportRevisedMdmNo> list = null;
		EntityManager em = null;
		try {

			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag " + bean.getFinyear());
			System.out.println("fin_year_id " + bean.getFinyearflag());
			System.out.println("subTeamCode " + bean.getSub_team_code());
			System.out.println("Rate  " + bean.getRate());

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callDispatchDetailsRevisedMdmNo");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
//			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("desig", ""); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());
			callDisptachSumDetReport.setParameter("TEAM_CODE", bean.getSub_team_code());
			callDisptachSumDetReport.setParameter("RATE_OPTION", bean.getRate());
			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of dispatch details ::" + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public Boolean check_StateNameRequired_or_not() {
		EntityManager em = null;
		String sgprmdet_text1 = null;
		boolean status=false;

		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("  select SGPRMDET_TEXT1 from SG_d_parameters_details sgd ");
			sb.append(" join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id ");
			sb.append(" where UPPER(sgm.SGprm_disp_nm) = 'ALLOC_SHEET_STATENAME_REQ_IND'");
			
			Query query = em.createNativeQuery(sb.toString());

			sgprmdet_text1 = (String) query.getSingleResult();
			
			if(sgprmdet_text1.equalsIgnoreCase("Y")) {
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return status;
	
	}

	@Override
	public List<Csv_File_List_Without_state_name> get_Csv_File_Consolidated_List_Without_State_Name(String id) {
		// TODO Auto-generated method stub
			List<Csv_File_List_Without_state_name> list = null;
			EntityManager em = null;

			try {
				em = emf.createEntityManager();
				StoredProcedureQuery procedurecall = em.createNamedStoredProcedureQuery("callALLOC_CSV_DOWNLOAD_without_state_name");
				procedurecall.setParameter("gen_id", id);

				list = procedurecall.getResultList();
				System.out.println("List :: " + list.size());

			} catch (Exception e) {
				throw e;
			} finally {
				if (em != null) {
					em.close();
				}
			}

			return list;
		

	}

	@Override
	public List<DispatchDetailReportRevised_VET> callDisptachDetReportRevised_VET(ReportBean bean) {
		List<DispatchDetailReportRevised_VET> list = null;
		EntityManager em = null;
		try {

			System.out.println("div_id" + bean.getDivId());
			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("reptype" + "D");
			System.out.println("subcomp" + bean.getLocId());
			System.out.println("orderby" + bean.getOrderBy());
			System.out.println("tabl_ind" + bean.getReportFrom());
			System.out.println("user_id" + bean.getUserId());
			System.out.println("field_level" + bean.getFsType());
			System.out.println("prod_id" + bean.getProduct());
			System.out.println("dsp_status" + bean.getDeletedInvoice());
			System.out.println("cfa" + bean.getCfaLocId());
			System.out.println("fsid" + bean.getEmpNo());
			System.out.println("desig" + bean.getDesgWise());
			System.out.println("fin_year_flag " + bean.getFinyear());
			System.out.println("fin_year_id " + bean.getFinyearflag());
			System.out.println("subTeamCode " + bean.getSub_team_code());
			System.out.println("Rate  " + bean.getRate());

			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport = em
					.createNamedStoredProcedureQuery("callDispatchDetailsrevised_VET");
			callDisptachSumDetReport.setParameter("div_id", bean.getDivId());
			callDisptachSumDetReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callDisptachSumDetReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callDisptachSumDetReport.setParameter("reptype", "D");
			callDisptachSumDetReport.setParameter("subcomp", bean.getLocId());
			callDisptachSumDetReport.setParameter("orderby", bean.getOrderBy());
			callDisptachSumDetReport.setParameter("tabl_ind", bean.getReportFrom());
			callDisptachSumDetReport.setParameter("user_id", bean.getUserId());
			if (bean.getDesgWise().equalsIgnoreCase("Y")) {
				callDisptachSumDetReport.setParameter("field_level", bean.getFstaff_id_o());
			} else {
				callDisptachSumDetReport.setParameter("field_level", bean.getFsType());
			}
			callDisptachSumDetReport.setParameter("prod_id", bean.getProduct()); // 0
			callDisptachSumDetReport.setParameter("dsp_status", bean.getDeletedInvoice());
			if (!bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
				callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
			} else {
				if (bean.getDeptloc().trim().equals("0") && bean.getCfaLocId().equals("0")) {
					callDisptachSumDetReport.setParameter("cfa", bean.getCfaLocId()); // 0
				} else {
					callDisptachSumDetReport.setParameter("cfa", "(" + bean.getCfaLocId() + ")");
				}
			}
			callDisptachSumDetReport.setParameter("fsid", bean.getEmpNo());
			callDisptachSumDetReport.setParameter("desig", bean.getDesgWise()); // Y
			callDisptachSumDetReport.setParameter("fin_year_flag", bean.getFinyear());
			callDisptachSumDetReport.setParameter("fin_year_id", bean.getFinyearflag());
			callDisptachSumDetReport.setParameter("TEAM_CODE", bean.getSub_team_code());
			callDisptachSumDetReport.setParameter("RATE_OPTION", bean.getRate());
			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of dispatch details ::" + list.size());

		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<ViewGRNSummary_GST_VET> getGRNSummaryReportData_VET(@Valid ReportBean bean) {
		List<ViewGRNSummary_GST_VET> list = null;
		EntityManager em = null;
		try {
			/*
			 * Div id :- 0 from loc id :-0 To Location :- 0 frm_date :- 01-10-2019 to_date
			 * :- 19-11-2019 user id: :- E00040
			 */
			System.out.println("In call	");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em.createNamedStoredProcedureQuery("GrnSummaryReport_GST_VET");
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("SEND_LOC", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("RECV_LOC", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("DIVID", bean.getDivId());
			callMonthlyActivityReport.setParameter("USER_ID", bean.getUserId());
			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("fin_year_id", bean.getFinyearflag());

			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of the GRN summary Report ::::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<ViewGRNSummary_VET> getGRNSummaryData_VET(ReportBean bean) throws Exception  {
		List<ViewGRNSummary_VET> list = null;
		EntityManager em = null;
		try {

			System.out.println("inside the View grn summary");
			em = emf.createEntityManager();
			StoredProcedureQuery callMonthlyActivityReport = em.createNamedStoredProcedureQuery("GrnSummaryReport_VET");
			callMonthlyActivityReport.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			callMonthlyActivityReport.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			callMonthlyActivityReport.setParameter("SEND_LOC", bean.getSendLocId());
			callMonthlyActivityReport.setParameter("RECV_LOC", bean.getRecLocId());
			callMonthlyActivityReport.setParameter("DIVID", bean.getDivId());
			callMonthlyActivityReport.setParameter("USER_ID", bean.getUserId());
			callMonthlyActivityReport.setParameter("fin_year_flag", bean.getFinyear());
			callMonthlyActivityReport.setParameter("fin_year_id", bean.getFinyearflag());

			list = callMonthlyActivityReport.getResultList();
			System.out.println("List size of the GRN summary Report ::::-" + list.size());
		} catch (Exception e) {
			
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<Stock_Statement_Non_Sale_VET> getDataStkNonsalable_VET(ReportBean bean) {
		List<Stock_Statement_Non_Sale_VET> list = null;
		EntityManager em = null;
		try {
			// STOCK_STATEMENT_NONSALEABLE
			// '01-11-2019','30-11-2020','0','0','N','0','0','0','E00040'

			System.out.println("startdate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			System.out.println("endDate" + MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			System.out.println("div_id" + bean.getDivId());
			System.out.println("loc_id" + bean.getLocId());
			System.out.println("nilstock" + bean.getZero_stock_id());
			System.out.println("ProdTypeId" + bean.getProd_type_id());
			System.out.println("ProdId" + bean.getProd_id());
			System.out.println("StockType" + bean.getStock_type_id());
			System.out.println("USER_ID" + bean.getUserId());

			System.out.println("fin_year_flag" + bean.getFinyear());
			System.out.println("fin_year_id" + bean.getFinyearflag());

			em = emf.createEntityManager();
			StoredProcedureQuery stockstatmentdata = em.createNamedStoredProcedureQuery("STOCK_STATEMENT_NONSALEABLE_VET");
			/*
			 * stockstatmentdata.setParameter("startdate", "01-11-2019");
			 * stockstatmentdata.setParameter("endDate", "30-11-2020");
			 * stockstatmentdata.setParameter("div_id", "0");
			 * stockstatmentdata.setParameter("loc_id", "0");
			 * stockstatmentdata.setParameter("nilstock", "N");
			 * stockstatmentdata.setParameter("ProdTypeId", "0");
			 * stockstatmentdata.setParameter("ProdId", "0");
			 * stockstatmentdata.setParameter("StockType", "0");
			 * stockstatmentdata.setParameter("USER_ID", "E00040");
			 */

			stockstatmentdata.setParameter("startdate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
			stockstatmentdata.setParameter("endDate",
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
			stockstatmentdata.setParameter("div_id", bean.getDivId());
			stockstatmentdata.setParameter("loc_id", bean.getLocId());
			stockstatmentdata.setParameter("nilstock", bean.getZero_stock_id());
			stockstatmentdata.setParameter("ProdTypeId", bean.getProd_type_id());
			stockstatmentdata.setParameter("ProdId", bean.getProd_id());
			stockstatmentdata.setParameter("StockType", bean.getStock_type_id());
			stockstatmentdata.setParameter("USER_ID", bean.getUserId());

			stockstatmentdata.setParameter("fin_year_flag", bean.getFinyear());
			stockstatmentdata.setParameter("fin_year_id", bean.getFinyearflag());

			list = stockstatmentdata.getResultList();
			System.out.println("stock statment Nonssalable List size ::::-" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;

		// TODO Auto-generated method stub
	}

	@Override
	public List<Scheme_Summary> getSchemeSummary_drill_down_list(List<String> schemeIds) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public List<Scheme_Summary> generateSchemeSummary_drill_down(String scheme_ids, String finyear, String finyear_flag) {
		List<Scheme_Summary> list = null;
		EntityManager em = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("callSchemeSummary_drill_down");
			procedureQuery.setParameter("TRD_SCH_SLNO", scheme_ids);
			procedureQuery.setParameter("fin_year_flag", "");
			procedureQuery.setParameter("fin_year_id","");
			list = procedureQuery.getResultList();
			System.out.println("List ::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<Ter_mst_hierarchy_report_bean> download_ter_hyrarchy_Report(String div_id) throws Exception {
		List<Ter_mst_hierarchy_report_bean> list = null;
		EntityManager em = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("dg_terr_master_hierarchy_list");
			procedureQuery.setParameter("M_DIV_ID", div_id);

			list = procedureQuery.getResultList();
			System.out.println("List ::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<FieldStaffCsvDownload> getFstaffDownloadCsvData(String divId) throws Exception {
		List<FieldStaffCsvDownload> list = null;
		EntityManager em = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("callDG_FIELDSTAFF_MOBILE_NO_UPDATE");
			procedureQuery.setParameter("div_id", divId);

			list = procedureQuery.getResultList();
			System.out.println("List ::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<String> getCsvFileList() throws Exception {
		
		EntityManager em = null;
		List<String> csvFileName = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT distinct csv_file_name from FIELDSTAFF_MOBILENO_UPDATE_LOG ");
			sb.append(" order by csv_file_name DESC ");
						
			Query query = em.createNativeQuery(sb.toString());
			csvFileName = query.getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if(em != null) { em.close(); }
		}
		return csvFileName;
	}

	@Override
	public List<Fieldstaff_After_mobile_No_Update_CheckList> getUpdatedFstaffMobileNo(String csvFileName)
			throws Exception {
		List<Fieldstaff_After_mobile_No_Update_CheckList> list = null;
		EntityManager em = null;
		HashMap<String, Object> map = new HashMap<>();
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("callDG_FIELDSTAFF_AFTER_MOBILE_NO_UPDATE_CHECKLIST");
			procedureQuery.setParameter("CSV_FL_NAME", csvFileName);
			
			list = procedureQuery.getResultList();
			System.out.println("List ::" + list.size());
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	
	@Override
	public List<FieldStaffBean> getFieldstaffMobileOdsdCount(Date date) throws Exception {
		EntityManager em = null;
		List<FieldStaffBean> list = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			String q = "SELECT " +
                    "  COUNT(*) AS ALL_COUNT, " +
                    "  COUNT(CASE WHEN RTRIM(FSTAFF_MOBILE) = '' THEN 1 END) AS MOB_EMPTY_CNT, " +
                    "  COUNT(CASE WHEN RTRIM(FSTAFF_MOBILE) <> '' THEN 1 END) AS MOB_NON_EMPTY_CNT " +
                    "FROM fieldstaff FS " +
                    "WHERE FS_CATEGORY = 'F' " +
                    "  AND GOAPTIVE_IND = 'P' " +
                    "  AND FSTAFF_mod_usr_id like '%AUTOUP%' " +
                    "  AND (CONVERT(DATE, FS.FSTAFF_MOD_DT) = :Date " +
                    "       OR CONVERT(DATE, FS.FSTAFF_INS_DT) = :Date)";
			
	        Query query = em.createNativeQuery(q);
	        query.setParameter("Date",  MedicoResources.convertUtilDateToString_YYYY_MM_DD(date));      
	        System.out.println("::::conversion "+MedicoResources.convertUtilDateToString_YYYY_MM_DD(date));
	        
			Object[] row = (Object[]) query.getSingleResult();

			if (row != null && row.length == 3) {
				FieldStaffBean f = new FieldStaffBean();
				f.setAllCount(((Number) row[0]).longValue());
				f.setMobileEmptyCount(((Number) row[1]).longValue());
				f.setMobileNonEmptyCount(((Number) row[2]).longValue());
				list.add(f);
			}
			
		}finally {
			if (em != null) {
				em.close();
			}
		}
		
		
		return list;
	}

}

