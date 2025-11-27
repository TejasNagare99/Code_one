package com.excel.restcontroller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.ArticleSchemeReportBean;
import com.excel.bean.FieldStaffBean;
import com.excel.bean.FinalApprovalLogBean;
import com.excel.bean.FstaffMas_PrxBean;
import com.excel.bean.ReportBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.AllocConHd;
import com.excel.model.Alloc_gen_hd;
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
import com.excel.model.Blk_Challans_Generated_Log;
import com.excel.model.Company;
import com.excel.model.Csv_File_Consolidated_List;
import com.excel.model.Csv_File_List;
import com.excel.model.Csv_File_List_Without_state_name;
import com.excel.model.Depot_Locations;
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
import com.excel.model.FinYear;
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
import com.excel.model.Scheme_Desription_Bean;
import com.excel.model.Scheme_Summary;
import com.excel.model.SmpBatch;
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
import com.excel.repository.ParameterRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.service.AllocationConsolidationService;
import com.excel.service.AllocationDetailReport_Service;
import com.excel.service.AllocationService;
import com.excel.service.Allocation_Download_csv_Service;
import com.excel.service.Allocation_Download_excel_service;
import com.excel.service.Allocation_Report1_2_Service;
import com.excel.service.Allocation_report3_Service;
import com.excel.service.Annual_sample_plan_view_Service;
import com.excel.service.ArticleSchemeExceptionReportService;
import com.excel.service.ArticleStockStmtReportService;
import com.excel.service.Article_Scheme_master_Service;
import com.excel.service.BatchMasterService;
import com.excel.service.BatchWiseStkStmtReportService;
import com.excel.service.BatchwiseLedgerReport_Service;
import com.excel.service.CompanyMasterService;
import com.excel.service.DepotLocationService;
import com.excel.service.DispatchRegisterReport1;
import com.excel.service.DispatchRegisterReport2;
import com.excel.service.DispatchService;
import com.excel.service.DispatchSummaryDetailReportService;
import com.excel.service.Dispatch_alloc_monthwise_reportService;
import com.excel.service.DivisionMasterService;
import com.excel.service.DoctorBulkAllocUploadService;
import com.excel.service.FieldMasterDownloadService;
import com.excel.service.FinalApprovalLogPdfService;
import com.excel.service.FinancialYearService;
import com.excel.service.GRNReportService;
import com.excel.service.Grn_quarantine_reportService;
import com.excel.service.IAADetailReportService;
import com.excel.service.ItemWise_StockLedger_ReportService;
import com.excel.service.LocationService;
import com.excel.service.LrEntryService;
import com.excel.service.Lr_Csv_Download_Service;
import com.excel.service.Lr_ExpensesReportService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.PforceRxReportGeneration;
import com.excel.service.Pivot_packing_slip_ReportService;
import com.excel.service.PrintService;
import com.excel.service.ProductMasterDownloadService;
import com.excel.service.ProductMasterService;
import com.excel.service.Productwise_batchwise_locationwise_stock_Report_Service;
import com.excel.service.ReportService;
import com.excel.service.SapDispatchDownloadService;
import com.excel.service.SchemeSummaryOfCfaService;
import com.excel.service.SchemeSummaryService;
import com.excel.service.StkTrfReportService;
import com.excel.service.StockAgeing_Report_Service;
import com.excel.service.StockStatementNonSaleablePDFService;
import com.excel.service.StockStmentReportService;
import com.excel.service.StockwithdrawalReportService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/rest")
public class ReportRestController implements MedicoConstants {
	@Autowired
	private JwtProvider tokenProvider;
	
	
	@Autowired
	LrEntryService lrEntryService;

	@Autowired
	private Article_Scheme_master_Service article_Scheme_master_Service;

	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ParameterRepository parameterRepository;

	@Autowired
	private SchemeSummaryService schemeSummaryService;
	@Autowired
	private DivisionMasterService divisionMasterService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private DispatchSummaryDetailReportService dispatchSummaryDetailReportService;
	@Autowired
	private DepotLocationService depotLocationService;
	@Autowired
	private DispatchService dispatchService;
	@Autowired
	private GRNReportService gRNReportService;
	@Autowired
	private BatchWiseStkStmtReportService batchWiseStkStmtReportService;
	@Autowired
	private StkTrfReportService stkTrfReportService;
	@Autowired
	private CompanyMasterService companyMasterService;
	@Autowired
	private StockStmentReportService stockStmentReportService;
	@Autowired
	private FinancialYearService financialYearService;

	@Autowired
	private StockStatementNonSaleablePDFService stockstatementnonsaleablepdfservice;
	@Autowired
	private Allocation_Download_csv_Service allocation_download_csv_service;

	@Autowired
	private ProductMasterService productMasterService;
	@Autowired
	private BatchMasterService batchMasterService;
	@Autowired
	private PrintService printService;
	@Autowired
	private ParameterService parameterService;

	@Autowired
	private ItemWise_StockLedger_ReportService itemwise_stockledger_reportservice;
	@Autowired
	private BatchwiseLedgerReport_Service batchwiseledgerreport_service;
	@Autowired
	private AllocationDetailReport_Service allocationdetailreport_service;
	@Autowired
	private Allocation_report3_Service allocation_report3_service;

	@Autowired
	private FieldMasterDownloadService fieldMasterDownloadService;
	@Autowired
	private ProductMasterDownloadService productMasterDownloadService;
	@Autowired
	private SapDispatchDownloadService sapDispatchDownloadService;
	@Autowired
	private Lr_Csv_Download_Service lr_csv_download_service;
	@Autowired
	private AllocationService allocationService;
	@Autowired
	private Pivot_packing_slip_ReportService pivot_packing_slip_reportservice;

	@Autowired
	private Productwise_batchwise_locationwise_stock_Report_Service productwise_batchwise_locationwise_stock_report_service;
	@Autowired
	private Annual_sample_plan_view_Service annual_sample_plan_view_service;

	@Autowired
	private Allocation_Download_excel_service allocation_Download_excel_service;

	@Autowired
	private StockAgeing_Report_Service stockageing_report_service;
	@Autowired
	private Allocation_Report1_2_Service allocation_report1_service;
	@Autowired
	FinalApprovalLogPdfService finalApprovalLogPdf;
	@Autowired
	private AllocationConsolidationService allocationConsolidationService;
	@Autowired
	private Dispatch_alloc_monthwise_reportService dispatch_alloc_monthwise_reportservice;
	@Autowired
	private Grn_quarantine_reportService grn_quarantine_reportservice;
	@Autowired
	private Lr_ExpensesReportService lr_expensesreportservice;
	@Autowired
	private StockwithdrawalReportService stockwithdrawalreportservice;
	@Autowired
	private PforceRxReportGeneration pforcerxreportgeneration;
	@Autowired
	private IAADetailReportService iAADetailReportService;

	@Autowired
	private PeriodMasterService periodmasterservice;
	@Autowired
	private DispatchRegisterReport1 dispatchRegisterReport1;
	@Autowired
	private DispatchRegisterReport2 dispatchRegisterReport2;
	@Autowired
	ParameterService parameterservice;

	@Autowired
	DoctorBulkAllocUploadService doctorBulkAllocUploadService;

	@Autowired
	private UserMasterRepository userMasterRepository;

	@Autowired
	private ArticleStockStmtReportService articleStockStmtReportService;

	@Autowired
	private ArticleSchemeExceptionReportService articleSchemeExceptionReportService;
	@Autowired
	private SchemeSummaryOfCfaService schemeSummaryOfCfaService;

	// Dispatch Intimation Email on
	@GetMapping("/get-pending-intimation-email")
	public Map<String, Object> getDispatchEmail(String stdate, String endate, HttpSession session) {
		System.out.println("stdate--->" + stdate.replace("/", "-"));
		System.out.println("endate--->" + endate.replace("/", "-"));

		String startDate = stdate.replace("/", "-");
		String endDate = stdate.replace("/", "-");
		Map<String, Object> map = new HashMap<>();
		List<DispatchIntimationEmail> dispatchlist = null;

		String filename = null;
		try {
			// String subCompName =
			// session.getServletContext().getAttribute(COMPANY_CODE).toString();
			dispatchlist = reportService.getDispatchIntimationEmail(stdate, endate);
			System.out.println("dispatchlist.size()-----" + dispatchlist.size());
			if (dispatchlist.size() != 0) {

				filename = dispatchRegisterReport1.generateDispatchIntimationEmail(dispatchlist, stdate, endate);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			} else {
				map.put("filename", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-startDate-endDate-byPeriodCode")
	public Map<String, Object> getStrtdateEnddateByFinyearidPeriodId(String finYear, String periodCd,
			HttpSession session) {
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			System.out.println("Rperiodcdoe------>"+periodCd);
//			System.out.println("RfinYearId------>"+finYear);
//			System.out.println("RcompanyCode------>"+companyCode);
		Map<String, Object> map = new HashMap<>();
		List<Period> period = null;

		try {
			period = periodmasterservice.getStartDateEndDateByPeriodId(companyCode, finYear, periodCd);
			map.put("list", period);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-finyear-list")
	public Map<String, Object> getAllFinYearList(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String subCompName = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		List<FinYear> financialYears = null;
		LinkedHashMap<String, String> finYears = new LinkedHashMap<String, String>();
		String from = "";
		String to = "";
		try {
			financialYears = financialYearService.getAllFinancialYears(subCompName);
			for (FinYear f : financialYears) {
				from = f.getFin_start_date().toString().substring(8, 10) + "-"
						+ f.getFin_start_date().toString().substring(5, 7) + "-"
						+ f.getFin_start_date().toString().substring(0, 4);
				to = f.getFin_end_date().toString().substring(8, 10) + "-"
						+ f.getFin_end_date().toString().substring(5, 7) + "-"
						+ f.getFin_end_date().toString().substring(0, 4);
				finYears.put(f.getFin_year(), from + " To " + to);
			}
			map.put("finYearMap", finYears);
			map.put("financialYears", financialYears);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-desg-list")
	public Map<String, Object> detailsByFstaffDesg(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		String emp_id = bean.getEmp_id(); // "E00040";
		String deptloc = "0";

		// String div_Id = "1";
		// String locfrmId = "0";
		// String strdt = "01-05-2020";
		// String enddt = "29-05-2020";
		// String cfaLoc="0";
		// String desg_id="Y";
		// String deleted_inv="A";

		String div_Id = bean.getDivId();
		String locfrmId = bean.getLocId();
		Date strdt = bean.getStartDate();
		Date enddt = bean.getEndDate();
		String cfaLoc = bean.getCfaLocId();
		String desg_id = bean.getDesgWise();
		String deleted_inv = bean.getDeletedInvoice();

		List<Object[]> resultList = new ArrayList<>();
		try {

			resultList = reportService.getDetailsByFstaffDesg(Long.parseLong(div_Id), Integer.parseInt(locfrmId),
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(strdt),
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(enddt), Integer.parseInt(cfaLoc), desg_id,
					deleted_inv, emp_id, deptloc);

			System.out.println("resultlist :: " + resultList.size());
			String[] arr = { "0", "All" };
			resultList.add(0, arr);
			map.put("list", resultList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@PostMapping("/get-employee-by-pso")
	public Map<String, Object> getemployeeListforDetailedreport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		String emp_id1 = bean.getEmp_id();
		String deptloc = "0";

		String div_Id = bean.getDivId();
		String locfrmId = bean.getLocId();
		Date stdt = bean.getStartDate();
		Date endt = bean.getEndDate();
		String cfaLoc = bean.getCfaLocId();
		String fstaff_id = bean.getFsType();
		String desg_id = bean.getDesgWise();
		String deleted_inv = bean.getDeletedInvoice();
		String fstaffId_o = bean.getFstaff_id_o();

		System.out.println("div_Id : " + div_Id);
		System.out.println("locfrmId : " + locfrmId);
		System.out.println("stdt : " + stdt);
		System.out.println("endt : " + endt);
		System.out.println("cfaLoc : " + cfaLoc);
		System.out.println("fstaff_id : " + fstaff_id);
		System.out.println("desg_id : " + desg_id);
		System.out.println("deleted_inv : " + deleted_inv);
		System.out.println("fstaffId_o : " + fstaffId_o);

		List<Object[]> list = null;

		try {

			if (fstaff_id.equals("") || fstaff_id.equals(null)) {
				fstaff_id = "0";
			}

			if (fstaffId_o.equals("All")) {
				fstaffId_o = "0";
			}

			System.out.println("fstaff_id 2   :  " + fstaff_id);
			System.out.println("fstaffId_o 2   : " + fstaffId_o);

			list = reportService.get_employee_list(Long.parseLong(div_Id), Integer.parseInt(locfrmId),
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(stdt),
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(endt), fstaff_id, Integer.parseInt(cfaLoc),
					desg_id, fstaffId_o, deleted_inv, emp_id1, deptloc, bean.getFinyear(), bean.getFinyearflag());
			System.out.println("List :: " + list.size());

			System.out.println("employee resultlist :: " + list.size());
			String[] arr = { "0", "All", " ", " " };
			list.add(0, arr);
			map.put("employeelist", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;

	}

	@GetMapping("/get-sub-comp-list")
	public Map<String, Object> getSubComList(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String subCompName = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		List<Sub_Company> subCompList = null;
		try {
			subCompList = companyMasterService.getSubCompByCompName(subCompName);
			map.put("subCompList", subCompList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-stock-reconciliation-report")
	public Map<String, Object> getstockreconciliationreport() {
		Map<String, Object> map = new HashMap<>();
		List<Stock_Reco_With_sfa> list = null;
		String filename = null;
		try {
			list = reportService.getStockReconcilation();
			if (list.size() != 0) {
				filename = allocation_report1_service.generate_Stock_Reco_SFA_report(list);

			}
			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-location-by-subcomp")
	public Map<String, Object> getLocBySubComp(@RequestParam String subCompId) {
		System.out.println("subCompId::" + subCompId);
		Map<String, Object> map = new HashMap<>();
		List<Location> locationList = null;
		try {
			locationList = locationService.getAllSubCompanies();
			locationList = locationList.stream()
					.filter(loc -> (loc.getLoc_subcomp_id().compareTo(Long.valueOf(subCompId)) == 0))
					.collect(Collectors.toList());
			Location loc = new Location();
			loc.setLoc_subcomp_id(0l);
			loc.setLoc_nm("All");
			locationList.add(0, loc);
			map.put("locationList", locationList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-location-by-subcomp-id")
	public Map<String, Object> getLocBySubCompId(@RequestParam String subCompId) {
		System.out.println("subCompId::" + subCompId);
		Map<String, Object> map = new HashMap<>();
		List<Location> locationList = null;
		try {
			locationList = locationService.getAllSubCompanies();
			locationList = locationList.stream()
					.filter(loc -> (loc.getLoc_subcomp_id().compareTo(Long.valueOf(subCompId)) == 0))
					.collect(Collectors.toList());
			Location loc = new Location();
			loc.setLoc_id(0l);
			loc.setLoc_nm("All");
			locationList.add(0, loc);
			map.put("locationList", locationList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-div-list")
	public Map<String, Object> getDivListByUserId(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String userId = "E00017"; // get it from session
		List<DivMaster> divList = null;
		try {
			divList = divisionMasterService.getDivAccessedByUser(userId);
			DivMaster dm = new DivMaster();
			dm.setDiv_id(0l);
			dm.setDiv_disp_nm("All");
			divList.add(0, dm);
			map.put("divList", divList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-location-list")
	public Map<String, Object> getAllSubCompanies() {
		Map<String, Object> map = new HashMap<>();
		List<Location> locationList = null;
		try {
			locationList = new ArrayList<Location>();
			Location loc = new Location();
			loc.setLoc_id(0l);
			loc.setLoc_subcomp_id(0l);
			loc.setLoc_nm("All");
			locationList.add(0, loc);
			locationList.addAll(locationService.getAllSubCompanies());
			map.put("locationList", locationList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-location-list-all-companies")
	public Map<String, Object> getAllCompanies() {
		Map<String, Object> map = new HashMap<>();
		List<Location> locationList = null;
		try {
			locationList = new ArrayList<Location>();
			// locationList = locationService.getAllSubCompanies();
			Location loc = new Location();
			loc.setLoc_id(0l);
			loc.setLoc_nm("All");
			locationList.add(0, loc);
			locationList.addAll(locationService.getAllLocations());

			map.put("locationList", locationList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-dispatch-summary-report-data")
	public Map<String, Object> getDispatchSumDetData(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DispatchSummaryDetailReport> list = null;
		String userId = bean.getEmp_id();// "E00040"; // get it from session //changed
		bean.setUserId(userId);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			list = reportService.callDisptachSumDetReport(bean);
			if (list != null && !list.isEmpty()) {
				String filename = this.dispatchSummaryDetailReportService.generateDispatchSumDetReport(list, bean,
						companyname);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-depot-locations-list")
	public Map<String, Object> getDepotLocationsByLocId(@RequestParam("locId") String locId,
			@RequestParam("depotLocations") String depotLocations) {
		Map<String, Object> map = new HashMap<>();
		List<Depot_Locations> dlList = null;
		try {
			dlList = depotLocationService.getDepotLocations(Long.valueOf(locId), depotLocations);
			Depot_Locations dl = new Depot_Locations();
			dl.setDptLoc_id(0l);
			dl.setDptLoc_name("All");
			dlList.add(0, dl);
			map.put("dlList", dlList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-product-for-dispatch")
	public Map<String, Object> getProductListForDsp(@RequestBody ReportBean bean, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<ReportBean> productList = null;
		String userId = bean.getEmp_id();// get it from session//changed
		bean.setEmpId(userId);
		try {
			productList = dispatchService.getProducts(Long.valueOf(bean.getDivId()), Integer.parseInt(bean.getLocId()),
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()),
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()), "0", bean.getDesgWise(),
					bean.getFstaff_id_o(), bean.getDeletedInvoice(), bean.getEmpNo(), bean.getEmpId(),
					bean.getFinyear(), bean.getFinyearflag());
			System.out.println("productList::" + productList.size());
			map.put("productList", productList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-dispatch-detail-report-data")
	public Map<String, Object> getDispatchDetData(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DispatchDetailReport> list = null;
		String userId = bean.getEmp_id();// get it from session //changed
		String deptloc = "0";// get it from session

		bean.setUserId(userId);
		bean.setDeptloc(deptloc);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			list = reportService.callDisptachDetReport(bean);
			if (list != null && !list.isEmpty()) {
				String filename = this.dispatchSummaryDetailReportService.generateDispatchDetReport(list, bean,
						companyname);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-summary-challan-detail-report-data")
	public Map<String, Object> getSummaryChallanReportData(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<SummaryChallanReport> list = null;
		List<SummaryChallanReportEInvoice> elist = null;
		String userId = bean.getEmp_id(); // get it from session //changed
		bean.setUserId(userId);
		bean.setDeptloc("0");
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String companycode = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany();
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			bean.setCompcd(companycode);
			if (bean.getCompcd().trim().equals(VET)) {
				elist = reportService.callSummaryChallanReportEInv(bean);
				if (elist != null && !elist.isEmpty()) {
					String filename = this.dispatchSummaryDetailReportService.generateSummaryChallanReportEInv(elist,
							bean, companyname);
					map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
				}
				map.put("list", elist);
				map.put("comp", companycode.trim());
			} else {
				list = reportService.callSummaryChallanReport(bean);
				if (list != null && !list.isEmpty()) {
					String filename = this.dispatchSummaryDetailReportService.generateSummaryChallanReport(list, bean,
							companyname, empId);
					map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
				}
				map.put("list", list);
				map.put("comp", companycode.trim());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-grn-gst-report-data")
	public Map<String, Object> GRNreport(@Valid @RequestBody ReportBean bean, @RequestParam String sendLocTemp, HttpSession session,
			HttpServletRequest request) {
		System.out.println("/get-grn-report-data");
		Map<String, Object> map = new HashMap<>();

		List<ViewGRNDetail_GST> grnDetail = null;
		bean.setSendLocId(sendLocTemp);
		String userId = bean.getEmp_id();
		// get it from session
		bean.setUserId(userId);
		Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		String companyname = company.getCompany_name();
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		bean.setCompcd(companyCode);
		bean.setCfa_to_stk_ind(company.getCfa_to_stk_ind());
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);

//			if (bean.getReportOption().equalsIgnoreCase("S")) {
//				if(companyCode.equals("PFZ")) {
//					List<ViewGRNSummary_GST> grnSummary  = reportService.getGRNSummaryReportData(bean);
//					if (grnSummary != null && !grnSummary.isEmpty()) {
//						String filename = gRNReportService.generateGRNSummaryReport(grnSummary, bean, companyname, empId);
//						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
//						map.put("list", grnSummary);
//					}
//				}else {
//					
//					
//					List<ViewGRNSummary_GST_VET> grnSummary = reportService.getGRNSummaryReportData_VET(bean);
//					if (grnSummary != null && !grnSummary.isEmpty()) {
//						String filename = gRNReportService.generateGRNSummaryReport_VET(grnSummary, bean, companyname, empId);
//						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
//						map.put("list", grnSummary);
//					}
//					
//				}
//			
//			}
			
			
			if (bean.getReportOption().equalsIgnoreCase("S")) {
				if(companyCode.trim().equals("PFZ")) {
					List<ViewGRNSummary_GST_VET> grnSummary = reportService.getGRNSummaryReportData_VET(bean);
					if (grnSummary != null && !grnSummary.isEmpty()) {
						String filename = gRNReportService.generateGRNSummaryReport_VET(grnSummary, bean, companyname, empId);
						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
						map.put("list", grnSummary);
					}
				}else {
					List<ViewGRNSummary_GST> grnSummary = reportService.getGRNSummaryReportData(bean);
					if (grnSummary != null && !grnSummary.isEmpty()) {
						String filename = gRNReportService.generateGRNSummaryReport_VETO(grnSummary, bean, companyname, empId);
						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
						map.put("list", grnSummary);
					}	
				}
			
			}
			
			
			
			
			
			

			if (bean.getReportOption().equalsIgnoreCase("D")) {
				grnDetail = reportService.getGRNDetailReportData(bean);
				if (grnDetail != null && !grnDetail.isEmpty()) {
					
					if(!company.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
			
						String filename = gRNReportService.generateGRNDetailReport_SG(grnDetail, bean, companyname, empId,
								(company.getCfa_to_stk_ind().equalsIgnoreCase("Y")));
						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
						
					}else {
						String filename = gRNReportService.generateGRNDetailReport(grnDetail, bean, companyname, empId,
								(company.getCfa_to_stk_ind().equalsIgnoreCase("Y")));
						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
					}
					map.put("list", grnDetail);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-batch-stock-stmt-report-data")
	public Map<String, Object> getBatchwiseStockStmtReportData(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Scheme_Desription_Bean> ListOfSchemDescription = null;
		List<BatchwiseStockStmtReport> list = null;
		List<BatchwiseStockStmtReport_SG> list_SG = null;
		String userId = bean.getEmp_id(); // get it from session //Changed
		bean.setUserId(userId);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		bean.setCompcd(companyCode);
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			String stk_to_cfa_ind = ((Company) session.getServletContext().getAttribute(COMPANY_DATA))
					.getCfa_to_stk_ind();

			System.err.println(stk_to_cfa_ind + ":::::stk_to_cfa_ind");
			
			if("Y".equalsIgnoreCase(stk_to_cfa_ind)) {
				list = reportService.getBatchwiseStockStmtData(bean, stk_to_cfa_ind);
				
				ArticleSchemeReportBean article_report_bean = new ArticleSchemeReportBean();
				String[] location = { "0" };

				location[0] = bean.getLoc_id();

				article_report_bean.setLocation(location);
				article_report_bean.setStDt(MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()));
				article_report_bean.setEnDt((MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate())));
				article_report_bean.setCustomer("0");
				article_report_bean.setInvoice("0");
				article_report_bean.setLrNo("0");
				article_report_bean.setArtScheme("0");
				article_report_bean.setArtName("0");
				article_report_bean.setSalesProduct("0");

				ListOfSchemDescription = article_Scheme_master_Service.getSchemeDescription(article_report_bean);

				System.err.println("ListOfSchemDescription::" + ListOfSchemDescription);
			}else {
				list_SG = reportService.getBatchwiseStockStmtData_SG(bean, stk_to_cfa_ind);
			}

			if (list != null && !list.isEmpty()) {

				String filename = this.batchWiseStkStmtReportService.generateBatchwiseStockStmtReportReport(list, bean,
						companyname, empId, ListOfSchemDescription);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
				map.put("list", list);

			}
			
			if (list_SG != null && !list_SG.isEmpty()) {

				String filename = this.batchWiseStkStmtReportService.generateBatchwiseStockStmtReportReport_SG(list_SG, bean,
						companyname, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
				map.put("list", list_SG);

			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-grn-report-data")
	public Map<String, Object> GRNNonGSTreport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) throws Exception {
		System.out.println("/get-grn-report-data");
		Map<String, Object> map = new HashMap<>();

		List<ViewGRNDetail> grnDetail = null;
		String userId = bean.getEmp_id(); // get it from session //Changed
		bean.setUserId(userId);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		Company company = companyMasterService.getCompanyDetails();
		
		bean.setCompcd(company.getCompany());
		
		String cfa_to_stk = company.getCfa_to_stk_ind();
		cfa_to_stk = cfa_to_stk.trim();
		
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			if (bean.getReportOption().equalsIgnoreCase("S")) {
				
				if(companyCode.equalsIgnoreCase("PFZ")) {
					
					List<ViewGRNSummary> grnSummary = reportService.getGRNSummaryData(bean);
					if (grnSummary != null && !grnSummary.isEmpty()) {
						String filename = gRNReportService.generateGRNSummary(grnSummary, bean, cfa_to_stk,companyCode, companyname, empId);
						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
						map.put("list", grnSummary);
					}
				}else {
					List<ViewGRNSummary_VET> grnSummary  = reportService.getGRNSummaryData_VET(bean);
					if (grnSummary != null && !grnSummary.isEmpty()) {
						String filename = gRNReportService.generateGRNSummary_VET(grnSummary, bean, cfa_to_stk,companyCode, companyname, empId);
						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
						map.put("list", grnSummary);
					}
					
				}
				
				
			}

			if (bean.getReportOption().equalsIgnoreCase("D")) {
				grnDetail = reportService.getGRNDetailData(bean);
				if (grnDetail != null && !grnDetail.isEmpty()) {
					String filename = gRNReportService.generateGRNDetail(grnDetail, bean, companyCode, companyname,
							empId);
					map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
					map.put("list", grnDetail);
				}
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-stk-symmary-report-data")
	public Map<String, Object> stkTrfsummaryDownload(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<StkTrfSummaryRepotModel> stkSummary = null;
		String userId = bean.getEmp_id();// get it from session //changed
		bean.setUserId(userId);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			stkSummary = reportService.getStkTrfSummaryReportData(bean);
			if (stkSummary != null && !stkSummary.isEmpty()) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				String filename = stkTrfReportService.generateStkTrfSummaryReport(stkSummary, bean, companyname, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", stkSummary);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-stk-detail-report-data")
	public Map<String, Object> stkTrfDetailDownload(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<StkTrfDetailRepotModel> stkdetail = null;
		// String userId="E00040"; // get it from session
		// bean.setUserId(userId);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			stkdetail = reportService.getStktrfDetailReportData(bean);
			if (stkdetail != null && !stkdetail.isEmpty()) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				String filename = stkTrfReportService.generateStkTrfDetailReport(stkdetail, bean, companyname, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", stkdetail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-stock-stmt-report")
	public Map<String, Object> stockStatment(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Stock_statement_pfz> stkstmt = null;
		try {
			String userId = bean.getEmp_id();// "E00040"; // get it from session //changed
			bean.setUserId(userId);
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			Company company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA));

			stkstmt = reportService.getstockStatmentData(bean);
			if (stkstmt != null && !stkstmt.isEmpty()) {
				String filename = stockStmentReportService.generateStockStatmentReport(stkstmt, companyname,
						companyCode, empId, company.getCfa_to_stk_ind());
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", stkstmt);
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded
			// --;
		}
		return map;
	}

	@PostMapping("/get-stock-statment-Nonsalable")
	public Map<String, Object> stockStatmentNonsalable(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String userId = bean.getEmp_id();// "E00040"; // get it from session //changed
		String print_radio = bean.getPrint_radio();
		String filename;

		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String username = bean.getUsername();
	 String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		bean.setUserId(userId);
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			System.out.println("loc_name :: " + bean.getLoc_name() + " \nProd_name :: " + bean.getProd_name());

			
			
			
			if(companyCode.equalsIgnoreCase("PFZ")){
				
				
				List<Stock_Statement_Non_Sale> stkstmt = reportService.getStockStatmentNonsalable(bean);
				if (stkstmt != null && !stkstmt.isEmpty()) {
					if (print_radio.equals("xls")) {
						
						
						Map<String, Object> maps = stockStmentReportService.stockstatmentNonsal(stkstmt, bean, companyname,
								empId);
						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + maps.get("filename"));
						map.put("list", maps.get("list"));
					}
					

					if (print_radio.equals("pdf")) {
						filename = stockstatementnonsaleablepdfservice.GenerateStockStatementPDF(stkstmt,
								MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()),
								MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()), bean.getLoc_name(),
								Integer.parseInt(bean.getDivId()), Integer.parseInt(bean.getLocId()),
								bean.getZero_stock_id(), bean.getProd_type_id(), bean.getProductName(),
								bean.getStock_type_id(), bean.getUserId(), companyname, username, empId);

						map.put("filename", SPRING_RESOURCE_HANDLER_PDF_FILE_PATH_PREFIX + "/files/" + filename);

					}
					
					
					map.put("list", stkstmt);
					
				}}else {
					List<Stock_Statement_Non_Sale_VET> stkstmt = reportService.getStockStatmentNonsalable_VET(bean);
					if (stkstmt != null && !stkstmt.isEmpty()) {
						if (print_radio.equals("xls")) {
							
							
							Map<String, Object> maps = stockStmentReportService.stockstatmentNonsal_VET(stkstmt, bean, companyname,
									empId);
							map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + maps.get("filename"));
							map.put("list", maps.get("list"));
						}
						

						if (print_radio.equals("pdf")) {
							filename = stockstatementnonsaleablepdfservice.GenerateStockStatementPDF_VET(stkstmt,
									MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()),
									MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()), bean.getLoc_name(),
									Integer.parseInt(bean.getDivId()), Integer.parseInt(bean.getLocId()),
									bean.getZero_stock_id(), bean.getProd_type_id(), bean.getProductName(),
									bean.getStock_type_id(), bean.getUserId(), companyname, username, empId);

							map.put("filename", SPRING_RESOURCE_HANDLER_PDF_FILE_PATH_PREFIX + "/files/" + filename);

						}
						
						map.put("list", stkstmt);
						
					}}
		
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// itemwise stock ledger (28-4-2020)
	@GetMapping("/getDetailForItemwiseStockLedger")
	public Map<String, Object> getDetailForItemwiseStockLedger(@RequestParam String empId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		List<DivMaster> divList = null;
		List<SmpItem> allActiveItemList = null;
		try {
			locList = locationService.getAllLocations();
			divList = divisionMasterService.getAllDivAccessedByUser(empId);
			// allActiveItemList=this.productMasterService.getAllActiveItemList();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("divList", divList);
		map.put("locList", locList);
		map.put("allActiveItemList", allActiveItemList);
		return map;
	}

	@GetMapping("/getItemListByDivId")
	public Map<String, Object> getItemListByDivId(@RequestParam String divId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmpItem> itemList = null;
		try {
			itemList = productMasterService.getItemListByDivId(divId);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("itemList", itemList);
		return map;
	}

	@GetMapping("/getItemByCode")
	public Map<String, Object> getItemByCode(@RequestParam String prodCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmpItem> itemList = null;
		try {
			itemList = productMasterService.getItemByCode(prodCode);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("itemList", itemList);
		return map;
	}

	// batchwise Stock ledger (29-4-2020)
	@GetMapping("/getDetailForBatchWiseStockLedger")
	public Map<String, Object> getDetailForBatchWiseStockLedger(@RequestParam String empId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		List<DivMaster> divList = null;
		List<SmpItem> allActiveItemList = null;
		List<SmpBatch> batchList = null;
		try {
			locList = locationService.getAllLocations();
			divList = divisionMasterService.getAllDivAccessedByUser(empId);
			// allActiveItemList=this.productMasterService.getAllActiveItemList();
			// batchList=batchMasterService.getAllActiveBatchList();
			// System.out.println(batchList.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("divList", divList);
		map.put("locList", locList);
		map.put("allActiveItemList", allActiveItemList);
		map.put("batchList", batchList);
		return map;
	}

	@GetMapping("/getBatchListByProdId")
	public Map<String, Object> getBatchListByProdId(@RequestParam String prodId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmpBatch> batchList = null;
		try {
			batchList = batchMasterService.getBatchListByProdId(prodId);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("batchList", batchList);
		return map;
	}

	// ItemWise Stock ledger
	@PostMapping("/get-itemwise-stock-ledger")
	public Map<String, Object> getitemwisestockledger(HttpSession session, @RequestBody ReportBean rb,
			HttpServletRequest request) {

		System.out.println(rb.getLocation());
		System.out.println(rb.getTeamDivision());
		System.out.println(rb.getItemwise());
		System.out.println(rb.getEmpId());
		String fdate = null, tdate = null;
//			try{
//				SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
//				Date date1 = sdf3.parse(rb.getFromDate());
//				 fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
//				System.out.println(fdate);
//				
//			
//				Date date2 = sdf3.parse(rb.getToDate());
//				 tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);
//				System.out.println(tdate);
//			}catch (Exception e) {
//				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			}

		Map<String, Object> map = new HashMap<>();
		List<ItemWiseStockLedgerModel> lst = null;

		ReportBean bean = null;
		 String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String prodSelectionType = "itemwise"; // It selects Which report to generate
		Long product_by_code = null; // In else statement Value of this is passed to prod_id
		String filename = null;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);

			bean = new ReportBean();
			bean.setLocId(rb.getLocation());
			bean.setDivId(rb.getTeamDivision());
			bean.setStartDate(rb.getStartDate());
			bean.setEndDate(rb.getEndDate());
			bean.setProd_id(rb.getItemwise());
			bean.setUserId(rb.getEmpId());

			bean.setFinyearflag(rb.getFinyearflag());
			bean.setFinyear(rb.getFinyear());
			bean.setCompcd(companyCode.trim());

			lst = reportService.getItemWise_StockledgerData(bean);

//					if(prodSelectionType.equalsIgnoreCase("itemwise")){
//						
//						bean.setLocId("1");
//						bean.setDivId("0");
//						bean.setFrmdt("01-03-2020");
//						bean.setTodt("23-04-2020");
//						bean.setProd_id("0");
//						bean.setUserId("E00040");
//						
//					lst = reportService.getItemWise_StockledgerData(bean);
//					}
//					else{
//						
//						bean.setLocId("1");
//						bean.setDivId("0");
//						bean.setFrmdt("01-03-2020");
//						bean.setTodt("23-04-2020");
//						bean.setProd_id(product_by_code.toString());
//						bean.setUserId("E00040");
//						
//						lst = reportService.getItemWise_StockledgerData(bean);
//					}


			if (lst.size() != 0 && lst != null) {
				filename = itemwise_stockledger_reportservice.generateItemWise_Stockledger_report(lst, bean,
						companyName, empId);
				map.put("filename", filename);
			}
			map.put("lst", lst);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// BatchWise Stock ledger
	@PostMapping("/get-batchwise-stock-ledger")
	public Map<String, Object> getbatchwiseledger(HttpSession session, @RequestBody ReportBean rb,
			HttpServletRequest request) {

		System.out.println(rb.getLocation());
		System.out.println(rb.getTeamDivision());
		System.out.println(rb.getItemwise());
		System.out.println(rb.getEmpId());
		System.out.println(rb.getBatchId());
		String fdate = null, tdate = null;
		try {
//				SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
//				Date date1 = sdf3.parse(rb.getFromDate());
//				 fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
//				System.out.println(fdate);
//				
//			
//				Date date2 = sdf3.parse(rb.getToDate());
//				 tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);
//				System.out.println(tdate);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		Map<String, Object> map = new HashMap<>();
		List<BatchWiseStockLedgerModel> lst = null;

		ReportBean bean = null;
				String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

		String prodSelectionType = "itemwise"; // It selects Which report to generate
		Long product_by_code = null; // In else statement Value of this is passed to prod_id
		String filename = null;
		try {
			bean = new ReportBean();
			bean.setLocId(rb.getLocation());
			bean.setDivId(rb.getTeamDivision());
			bean.setStartDate(rb.getStartDate());
			bean.setEndDate(rb.getEndDate());
			bean.setProd_id(rb.getItemwise());
			bean.setUserId(rb.getEmpId());
			bean.setBatch_id(rb.getBatchId());
			bean.setFinyear(rb.getFinyear());
			bean.setFinyearflag(rb.getFinyearflag());
			bean.setCompcd(companyCode.trim());


			lst = reportService.getBatchwise_StockledgerData(bean);
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
//					if(prodSelectionType.equalsIgnoreCase("itemwise")){
//					
//						bean.setLocId("1");
//						bean.setDivId("0");
//						bean.setFrmdt("01-04-2020");
//						bean.setTodt("23-04-2020");
//						bean.setProd_id("0");
//						bean.setBatch_id("0");
//						bean.setUserId("E00040");
//						
//						lst = reportService.getBatchwise_StockledgerData(bean);
//					}
//					else {
//						bean.setLocId("1");
//						bean.setDivId("0");
//						bean.setFrmdt("01-04-2020");
//						bean.setTodt("23-04-2020");
//						bean.setProd_id(product_by_code.toString());
//						bean.setBatch_id("0");
//						bean.setUserId("E00040");
//						
//						lst = reportService.getBatchwise_StockledgerData(bean);
//					}


			if (lst.size() != 0 && lst != null) {
				filename = batchwiseledgerreport_service.GenerateBatchwiseLedgerReport(lst, bean, companyName, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("lst", lst);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	// Allocation Details
	@PostMapping("/get-AllocationDetail-Report")
	public Map<String, Object> AllocationDetail(HttpSession session, @RequestBody ReportBean rb,
			HttpServletRequest request) {
		System.out.println(rb.getPeriodCode());
		System.out.println(rb.getFinYear());
		System.out.println(rb.getTeamDivision());
		System.out.println(rb.getEmpId());
		System.out.println(session.getAttribute("USER_ROLE"));
//			List<String> userIds = Arrays.asList("E00549",
//					"E00548",
//					"E00547",
//					"E00546",
//					"E00545",
//					"E00544",
//					"E00543",
//					"E00542",
//					"E00541");

		Map<String, Object> map = new HashMap<>();
		List<AllocationDetailReportModel> lst = null;
		String target = "excel";
		ReportBean bean = new ReportBean();
		String filename = null;
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			// String empId = this.userMasterService.getEmpIdByUsername(uname);
			HashMap<String, String> details = this.userMasterRepository.getUserInfo(uname, null);
			String empId = details.get("EMP_ID");
			String role_short_nm = details.get("USER_ROLE");
			// get users role
			bean.setPeriod_code(rb.getPeriodCode());
			bean.setFin_year(rb.getFinYear());
			bean.setDivId(rb.getTeamDivision());
			bean.setEmp_id(empId);
			
//					bean.setPeriod_code("07");
//					bean.setFin_year("2020");
//					bean.setDivId("01");
			if (role_short_nm.equalsIgnoreCase("REPOD")) {
				lst = reportService.getAllocationDetailReportDataDiv(bean);
			} else {
				lst = reportService.getAllocationDetailReportData(bean);
			}

			System.out.println("list " + lst.size());
			map.put("lst", lst);

			if (lst.size() != 0 && lst != null) {
				filename = allocationdetailreport_service.GenerateAllocationDetailReport(bean, lst, companyname,
						companyCode, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// ang allocation report 3(1-5-2020)
	@GetMapping("/getDataForAngAllocationDetailReport3")
	public Map<String, Object> getDataForAngAllocationDetailReport3(@RequestParam("userid") String userid,
			HttpSession session, HttpServletRequest request) throws Exception {
		String companyCode = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		List<DivMaster> divList = null;
		List<Period> finYearList = null;
		List<Sub_Company> subCompList = null;
		List<SG_d_parameters_details> prodSubTypeList = null;
		try {
			divList = divisionMasterService.getDivAccessedByUser(userid);
			finYearList = reportService.getAllActiveFinYearForAllocationDetailReport();
			subCompList = this.companyMasterService.getAllActiveSubCompanyList(companyCode);
			prodSubTypeList = this.parameterService.getActiveProductSubType();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("divList", divList);
		map.put("locList", locList);
		map.put("finYearList", finYearList);
		map.put("subCompList", subCompList);
		map.put("prodSubTypeList", prodSubTypeList);
		return map;
	}

	@GetMapping("/getPeriodCodeByFinYear")
	public Map<String, Object> getPeriodCodeByFinYear(@RequestParam String finYear) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Period> periodList = null;
		try {
			periodList = printService.getListOfPeriodByFinYear(finYear);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("periodList", periodList);
		return map;
	}

	@PostMapping("/Allocation-Report-3")
	public Map<String, Object> getAllocation_report_3(HttpSession session, @RequestBody ReportBean rb,
			HttpServletRequest request) {
		System.out.println(rb.getTeamDivision());
		System.out.println(rb.getFinYear());
		System.out.println(rb.getPeriodCode());
		System.out.println(rb.getSubCompany());
		System.out.println(rb.getProdSubType());

		Map<String, Object> map = new HashMap<>();
		List<Allocation_report_3> list = null;
		String filename = null;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);

			ReportBean bean = new ReportBean();
			bean.setPdiv_id(rb.getTeamDivision());
			bean.setPperiod_code(rb.getPeriodCode());
			bean.setPprod_sub_type_id(rb.getProdSubType());
			bean.setPsub_comp_id(rb.getSubCompany());
			bean.setPfin_year(rb.getFinYear());

			list = reportService.getAllocation_Report_3_data(bean);

			if (list != null) {
				filename = allocation_report3_service.generate_Allocation_Report3_Report(list, empId);
				System.out.println("generated file name is :" + filename);
				map.put("filename", filename);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
//				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("list", list);

		return map;
	}

	// FieldStaff Download(8-5-2020)
	@GetMapping("/getFieldsNameForFieldstaffDownload")
	public Map<String, Object> getFieldsNameForFieldstaffDownload() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Period> fieldList = null;
		try {
			// fieldList=fi
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("fieldList", fieldList);
		return map;
	}

	@PostMapping("/get-fieldmaster-download")
	public Map<String, Object> fieldmasterdownload(HttpSession session, @RequestBody ReportBean rb,
			HttpServletRequest request) {
		System.out.println("level code " + rb.getLevel());
		Map<String, Object> map = new HashMap<>();
		String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
//				String level_code="001";
		String level_code = rb.getLevel();
		String username = "abc";
		List<FieldMasterDownload> list = null;
		try {
			ReportBean bean = new ReportBean();
			bean.setLevelCode(level_code);
			list = reportService.getFieldMasterDownloadData(bean, comp_code);

			if (list != null && !list.isEmpty()) {
				String filename = fieldMasterDownloadService.GenerateFieldMasterDownloadReport(bean, list, companyname,
						username);
				map.put("filename", filename);
			}

			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-productmaster-download")
	public Map<String, Object> productmasterdownload(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String username = "abc";
		List<ProductMasterAttrib> list = null;
		try {
			ReportBean bean = new ReportBean();

			list = reportService.getProductMasterDownloadData(bean);

			if (list != null && !list.isEmpty()) {
				String filename = productMasterDownloadService.GenerateProductMasterReport(bean, list);
				map.put("filename", filename);
			}

			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-sap-dispatch-download")
	public Map<String, Object> sapdispatchdownload(HttpSession session, @RequestBody ReportBean rb,
			HttpServletRequest request) {

		System.out.println("pdf , excel ?" + rb.getPdfExcel());

		String fdate = null, tdate = null;
		try {
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
			Date date1 = sdf3.parse(rb.getFromDate());
			fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
			System.out.println(fdate);

			Date date2 = sdf3.parse(rb.getToDate());
			tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);
			System.out.println(tdate);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		System.out.println("emp id is:" + rb.getEmpId());
		Map<String, Object> map = new HashMap<>();
		List<Sap_Dispatch_Download> list = null;
		/*
		 * String userId="E00040"; // get it from session bean.setUserId(userId);
		 */

		try {
			ReportBean bean = new ReportBean();
			bean.setUserId(rb.getEmpId());
			list = reportService.getSapDispatchDownloadData(bean, fdate, tdate);

			if (list != null && !list.isEmpty()) {
				String filename = sapDispatchDownloadService.GeneratesapReport(bean, list);
				map.put("filename", filename);
			}

			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-employee-numbers")
	public Map<String, Object> getEmployeeNumbers(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("cfaLocId") String cfaLocId,
			@RequestParam("username") String username, @RequestParam("role") String role) {
		Map<String, Object> map = new HashMap<>();
		List<ReportBean> employeeNumber = null;
		// bean.setEmpId("E00040"); //get it from session
		try {
			employeeNumber = dispatchService.getEmpDetails(startDate, endDate, Long.valueOf(cfaLocId), username, role);
			map.put("employeeNumber", employeeNumber);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-pso-lr-detail")
	public Map<String, Object> getLrdownload_csv(HttpSession session, @RequestBody ReportBean bean,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<LrcsvDownload> list = null;
		String filename = "";
		String deptloc = "0";// from session
		String stk_at_cfa_option = "0";// from session
		String strdate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate());
		String enddate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate());
		String fsid = bean.getStaffId().toString();
		String cfa = bean.getCfaLocId();
		System.out.println("deptloc " + deptloc);
		System.out.println("stk_at_cfa_option " + stk_at_cfa_option);
		String tbl_ind = "";
		String stock_at_cfa_ind = "N";
		String tmpOrActl = null;

		try {
			StringBuffer temp = new StringBuffer();

			if (tmpOrActl == null) {
				tmpOrActl = "A";
			}

			if (!deptloc.trim().equals("0")) {
				for (int i = 0; i < deptloc.length(); i = i + 4) {
					temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
				}
				if (temp.length() > 0) {
					temp.deleteCharAt(temp.length() - 1);
				}
				temp.insert(0, "(");
				temp.insert(temp.length(), ")");
			}

			list = reportService.getLrcsvdownload_data(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
					stk_at_cfa_option, temp.toString(), deptloc);

			filename = lr_csv_download_service.Generate_Lr_CSV(list);

			map.put("filename", filename);
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@PostMapping("/lr-report-download")
	public Map<String, Object> getViaCfaLrdownload_csv(HttpSession session, @RequestBody ReportBean bean,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<LrcsvDownloadReport> list = null;
		String filename = "";
		String tabl_ind = null;
		String strdate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate());
		String enddate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate());
		String fsid = bean.getStaffId().toString();
		String cfa = bean.getCfaLocId();
		String fstype = "0";
		String deptloc = bean.getDeptloc();
		StringBuffer temp = new StringBuffer();

		try {

			if (tabl_ind == null) {
				tabl_ind = "A";
			}

			if (!deptloc.trim().equals("0")) {
				for (int i = 0; i < deptloc.length(); i = i + 4) {
					temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
				}
				if (temp.length() > 0) {
					temp.deleteCharAt(temp.length() - 1);
				}
				temp.insert(0, "(");
				temp.insert(temp.length(), ")");
			}
			String compcode = session.getServletContext().getAttribute(COMPANY_CODE).toString();

			list = reportService.get_Lrcsv_data(bean, tabl_ind, cfa, fsid, fstype, deptloc, temp.toString());

			filename = lr_csv_download_service.generateLRcsvReport(list, bean.getFromDate(), bean.getToDate(), tabl_ind,
					cfa, fsid, fstype, compcode);

			map.put("filename", filename);
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@GetMapping("/allocationNumbersByUser")
	public Map<String, Object> allocationNumbersByUser(@RequestParam("empId") String empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReportBean> allocNumbers = new ArrayList<>();
		ReportBean bean = null;
		try {

			List<AllocConHd> list = this.allocationService.getAllocConHdUserId(empId);
			if (list != null && list.size() > 0) {
				for (AllocConHd header : list) {
					bean = new ReportBean();
					bean.setAlloc_id(header.getAlloc_con_id() + "_" + header.getDiv_id());
					bean.setAlloc_number(header.getAlloc_con_docno());
					bean.setDivision(header.getDivision());
					bean.setAlloc_month(header.getAlloc_month());
					bean.setFile_download(header.getFile_download());
					allocNumbers.add(bean);
				}
			}
			map.put("list", allocNumbers);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@GetMapping("/allocationNumbersByWarehouse")
	public Map<String, Object> allocationNumbersByWarehouse(@RequestParam("empId") String empId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReportBean> allocNumbers = new ArrayList<>();
		ReportBean bean = null;
		try {

			List<Alloc_gen_hd> list = this.allocationService.getAllocGenHdUserId(empId);
			if (list != null && list.size() > 0) {
				for (Alloc_gen_hd header : list) {
					bean = new ReportBean();
					bean.setAlloc_id(header.getAlloc_gen_id() + "_" + header.getDiv_id());
					bean.setAlloc_number(header.getAlloc_doc_no());
					bean.setDivision(header.getDivision());
					bean.setAlloc_month(header.getAlloc_month());
					bean.setFile_download(header.getFile_download().charAt(0));
					bean.setSub_team_name(header.getTeam_name());
					allocNumbers.add(bean);
				}
			}
			map.put("list", allocNumbers);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@PostMapping("/download-allocation-csv")
	public Map<String, Object> getdownloadallocationcsv(HttpSession session, @RequestBody ReportBean bean,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Csv_File_List> list = null;
		List<Csv_File_List_Without_state_name> list_Without_state_name = null;

		String filename = bean.getAlloc_number(); // from UI
		String divid = "";
		String id = "";

		try {
			String header_id = bean.getAlloc_id(); // from UI
			String[] data = header_id.split("_");
			id = data[0];
			divid = data[1];
		
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);

			String issrt = this.parameterservice.getSrtAndEmailReqInd("SRT_NUMBER_REQD");

			
		Boolean state_name_required= reportService.check_StateNameRequired_or_not();
		
		if(state_name_required) {
			list = reportService.get_allocation_downloadcsvdata(data[0]);
			filename = allocation_download_csv_service.Generate_Allocation_Download_csv(list, id, divid, filename,
					empId, issrt);
			map.put("list", list);
		}else {
			list_Without_state_name = reportService.get_Csv_File_Consolidated_List_Without_State_Name(data[0]);
			filename = allocation_download_csv_service.Generate_Allocation_Download_csv_Without_State_Name(list_Without_state_name, id, divid, filename,
					empId, issrt);
			
			map.put("list", list_Without_state_name);
		}
					
			
		
		
			// this.allocationService.updateDownload(Long.valueOf(id));
			map.put("filename", filename);
	

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@PostMapping("/download-allocation-consolidated-csv")
	public Map<String, Object> getdownloadallocationConsolidatedCsv(HttpSession session, @RequestBody ReportBean bean,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Csv_File_Consolidated_List> list = null;
		String filename = bean.getAlloc_number(); // from UI
		String divid = "";
		String id = "";
		try {
			System.out.println("Alloc_id " + bean.getAlloc_id());
			System.out.println("Emp " + bean.getEmpId());
			String header_id = bean.getAlloc_id(); // from UI
			String[] data = header_id.split("_");
			id = data[0];
			divid = data[1];
			list = reportService.get_Csv_File_Consolidated_List(data[0]);
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			
			filename = allocation_download_csv_service.Generate_Allocation_Consolidated_Download_csv(list, id, divid,
					filename, empId);
			if ("E00040".equals(bean.getEmpId())) {
				this.allocationConsolidationService.updateDownload(Long.valueOf(id));
			}
			map.put("filename", filename);
			map.put("list", list);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	// Pivot PAcking Slip(23May2020)
	@GetMapping("/getDetailForPivotPackSlip")
	public Map<String, Object> getDetailForPivotPackSlip(@RequestParam String empId, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		List<DivMaster> divList = null;
		try {
			locList = new ArrayList<Location>();
//			if (locList != null && locList.size() > 0) {
				Location all = new Location();
				all.setLoc_nm("ALL");
				all.setLoc_id(0L);
				locList.add(all);
//			}
			locList.addAll(locationService.getAllLocations());
			divList = divisionMasterService.getDivAccessedByUser(empId);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("divList", divList);
		map.put("locList", locList);
		return map;
	}

	@PostMapping("/get-pivot-packing-slip-report")
	public Map<String, Object> getpivotPackingslipreport(@RequestBody ReportBean rb, HttpSession session,
			HttpServletRequest request) {
		System.out.println(rb.getTeamDivision());
		System.out.println(rb.getSlId());
		System.out.println(rb.getRlId());

		String startDate = null, endDate = null;
		try {
			SimpleDateFormat sdf3 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			Date date1 = sdf3.parse(rb.getStartDate().toString());
			System.out.println("ist to date is: " + date1);

			Date date2 = sdf3.parse(rb.getEndDate().toString());
			System.out.println("ist to date is: " + date2);

			startDate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
			endDate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);
			System.out.println("date converted are :" + startDate + ".." + endDate);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		Map<Integer, List<Object>> map = new HashMap<>();
		String filename = null;
		Map<String, Object> map2 = new HashMap<String, Object>();

		String divText = rb.getLoc_name();
		String msg;
		try {
			map = reportService.getPivotData(rb.getTeamDivision(), rb.getSlId(), rb.getRlId(), startDate, endDate);
//					map = reportService.getPivotData("49", "1", "0", "11-09-2019",  "11-09-2019");

			// System.out.println("Map :: "+map);
			int listSize = 0;
			List<Object> list = map.get(2);

			List<Object> summaryList = map.get(3);
			List<Object> headerList = new ArrayList<Object>();
			List<Object> batchList = new ArrayList<Object>();
			List<Object> productList = new ArrayList<Object>();
			int tsoCount = 0;
			List<Object> countList = map.get(1);
			tsoCount = (int) countList.get(0);
			if (tsoCount == 0) {
				msg = "No Data Found !!";
			}
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					Object[] oo = (Object[]) list.get(i);
					headerList = Arrays.asList(oo);
				}
			}

			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			for (int i = 0; i < headerList.size(); i++) {
				if (i != 0) {
					batchList.add(headerList.get(i).toString().split("\\|")[0]);
					System.out.println("DATA:::" + headerList.get(i).toString());
					productList.add(headerList.get(i).toString().split("\\|")[1]);
				}
			}

			filename = pivot_packing_slip_reportservice.Generate_Pivot_packing_slip_Report(list, summaryList,
					headerList, batchList, productList, countList, divText, tsoCount, empId);
			map2.put("filename", filename);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map2;
	}

	@GetMapping("/get-div-list-temp")
	public Map<String, Object> getDivListByUserId(@RequestParam("userid") String userid, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String userId = userid; // "E00040"; // get it from session
		List<DivMaster> divList = null;
		try {
			divList = divisionMasterService.getDivAccessedByUser(userId);
			DivMaster dm = new DivMaster();
			dm.setDiv_id(0l);
			dm.setDiv_disp_nm("All");
			divList.add(0, dm);
			map.put("divList", divList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-data-for-product-inventory-search")
	public Map<String, Object> getdataProductwise_batchwise_locationwise_stock(@RequestParam("empid") String empid) {
		Map<String, Object> map = new HashMap<>();
		List<DivMaster> divList = null;
		try {
			// divList = divisionMasterService.getActiveList();
			divList = divisionMasterService.getDivAccessedByUser(empid);
			map.put("divList", divList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;

	}

	@GetMapping("/get-active-items-by-divid")
	public Map<String, Object> getdataProductwise_batchwise_locationwise_stock(@RequestParam("divid") String divid,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		List<SmpItem> itemlist = null;

		try {
			itemlist = reportService.getActiveItemBy_divId(divid);

			map.put("itemList", itemlist);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;

	}

	@PostMapping("/get-product-inventory-search-report")
	public Map<String, Object> getdataProductwise_batchwise_locationwise_stock(@RequestBody ReportBean bean,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		List<Productwise_batchwise_locationwise_stock> list = null;
		String filename;

		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			list = reportService.getproductwisebatchwiselocationwisereportdata(bean);

			if (list.size() != 0) {

				map.put("list", list);

				filename = productwise_batchwise_locationwise_stock_report_service
						.generateProductwise_batchwise_locationwise_stock_Report(list, companyname, empId);

				map.put("filename", filename);

			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@GetMapping("/get-data-annualsample-plan-report")
	public Map<String, Object> getdataforannualallocationreport(@RequestParam("empId") String empId,
			HttpSession session, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();

		try {
			map.put("teams", divisionMasterService.getDivAccessedByUser(empId)); // change
			// map.put("teams", this.allocationService.getTeamWithCount(empId));
			// map.put("plans", this.allocationService.getPlanningType(empId));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;

	}

	@GetMapping("/get-brands-by-team")
	public Map<String, Object> getbrandsforteam(@RequestParam("empId") String empId,
			@RequestParam("teamId") List<String> teamId, @RequestParam("planType") String planType, HttpSession session,
			HttpServletRequest request) {
		System.out.println("emp_id::" + empId + teamId);
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("brands", this.reportService.getBrandsForTeam(empId, teamId, planType));
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@GetMapping("/get-prod-by-brands")
	public Map<String, Object> getbrandsforteam(@RequestParam("teamId") List<String> teamId,
			@RequestParam("planType") String planType, @RequestParam("brand_Id") List<String> brand_Id,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<SmpItem> list = null;
		try {

			System.out.println("teamId" + teamId + "   planType : " + planType + "  brand_Id :" + brand_Id);
			list = this.reportService.getprodlistbybrands(teamId, planType, brand_Id);

			System.out.println("List : " + list.size());
			map.put("productlist", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@PostMapping("/get-annualsample-plan-report")
	public Map<String, Object> getannualallocationreport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Annual_sample_plan_view_report> list = null;
		String filename = null;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);

			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			// bean = new ReportBean();
			// bean.setFinYear("2020");
			// bean.setDivId("50");
			// bean.setBrand_id("118");
			// bean.setProd_id("12130,5757");
			// bean.setPemp_Id("E00169");

			System.out.println("FINYEAR" + bean.getFinyear());
			System.out.println("ASP_DIVID" + bean.getTeaminfo());
			System.out.println("ASP_BRAND_ID" + bean.getBrandids());
			System.out.println("ASP_PROD_ID" + bean.getProductid());
			System.out.println("PEMP_ID" + bean.getEmpid());

			list = reportService.getDataforannualplanview(bean);

			if (list.size() != 0) {
				filename = annual_sample_plan_view_service.generate_annual_sample_plan_view_report(list, companyname,
						bean.getFinyear(), companyCode, empId);
			}

			map.put("filename", filename);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-allocation-download")
	public Map<String, Object> allocationdownload(HttpSession session, @RequestBody ReportBean rb,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		// String username ="abc";
		System.out.println("emp id is:" + rb.getEmpId());
		System.out.println("div id:" + rb.getTeamDivision());
		System.out.println("fin year id:" + rb.getFinancialYear());
		System.out.println("period id:" + rb.getForPeriod());

		String pidiv_id = rb.getTeamDivision();
		String pvuserid = rb.getEmpId();
		String pfin_year = rb.getFinancialYear();
		String pperiod_code = rb.getForPeriod();
		List<Allocation_download> list = null;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			ReportBean bean = new ReportBean();
			bean.setPdiv_id(rb.getTeamDivision());
			bean.setPperiod_code(rb.getPeriodCode());
			bean.setPfin_year(rb.getFinYear());

			list = reportService.get_allocation_downloadexceldata(pidiv_id, pvuserid, pfin_year, pperiod_code);

			if (list != null && !list.isEmpty()) {
				// String filename
				// =productMasterDownloadService.GenerateProductMasterReport(bean, list);
				String filename = allocation_Download_excel_service.Generate_Allocation_Download_excel(list, bean,
						companyname, empId);
				map.put("filename", filename);
			}

			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-Ageing-of-Stock-based-on-expiry-Report")
	public Map<String, Object> getAgeingofStockbasedonexpiryreport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<StockAgeingReportView> list = null;
		String check = bean.getReport_type();
		String filename = null;
		try {
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			// ReportBean bean = new ReportBean();

			// bean.setLocId("1");
			// bean.setDivId("0");
			// bean.setStartDate("01-11-2019");
			// bean.setEndDate("28-07-2020");
			// bean.setUserId("E00121");
			// bean.setProd_id("0");

			// bean.setSlab1a("0");
			// bean.setSlab1b("30");
			// bean.setSlab2a("31");
			// bean.setSlab2b("60");
			// bean.setSlab3a("61");
			// bean.setSlab3b("90");
			// bean.setSlab4a("91");
			// bean.setSlab4b("180");
			// bean.setSlab5a("181");

			if (check.equals("A")) {
				list = reportService.getstockageingreportData(bean);
			}
			if (check.equals("E")) {
				list = reportService.getstockageingreportbyExpirydaysData(bean);
			}

			if (list.size() != 0) {

				filename = stockageing_report_service.generate_stockAgeingExcel(list,
						MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()),
						Long.valueOf(bean.getSlab1a()), Long.valueOf(bean.getSlab1b()), Long.valueOf(bean.getSlab2a()),
						Long.valueOf(bean.getSlab2b()), Long.valueOf(bean.getSlab3a()), Long.valueOf(bean.getSlab3b()),
						Long.valueOf(bean.getSlab4a()), Long.valueOf(bean.getSlab4b()), Long.valueOf(bean.getSlab5a()),
						check, bean.getSlab1b(), companyname);

				map.put("filename", filename);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-div-for-Ageing-of-Stock")
	public Map<String, Object> getdivforAgeingofStock(@RequestParam("userid") String userid, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<DivMaster> list = null;
		List<Location> location = null;
		List<SG_d_parameters_details> slablist = null;

		try {
			list = reportService.getdivforstockageing(userid);
			location = reportService.getlocationforstockageing();
			slablist = reportService.getslabdetails("STOCK_AGEING_DAYS");
			map.put("divlist", list);
			map.put("loclist", location);
			map.put("slablist", slablist);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-prod-list-for-ageing-by-divid")
	public Map<String, Object> getprodbydividforageing(@RequestParam("divid") String divid, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<SmpItem> list = null;
		try {

			list = reportService.getprodbydivforageing(divid);

			map.put("prod_list", list);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-ang-alloc-report1")
	public Map<String, Object> get_ang_alloc_report1(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Allocation_report_1> list = null;
		String filename = null;
		try {
			// ReportBean bean = new ReportBean();
			// bean.setDivId("0");
			// bean.setFin_year("2020");
			// bean.setPperiod_code("10");
			// bean.setSubCompId("0");
			// bean.setPprod_sub_type_id("0");

			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			if (bean.getFinyear().equalsIgnoreCase("CURRENT")) {
				list = reportService.getdatafor_allocation_report1_current(bean);
			} else {
				list = reportService.getdatafor_allocation_report1_previous(bean);
			}
			// list = reportService.getdatafor_allocation_report1(bean);

			if (list.size() != 0) {

				filename = allocation_report1_service.generate_Angular_allocation1_report(list, companyname, empId);

				map.put("list", list);
				map.put("filename", filename);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-ang-alloc-report2")
	public Map<String, Object> get_ang_alloc_report2(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Allocation_report_2> list = null;
		String filename = null;
		try {
			// ReportBean bean = new ReportBean();
			// bean.setDivId("0");
			// bean.setFin_year("2020");
			// bean.setPperiod_code("10");
			// bean.setSubCompId("0");
			// bean.setPprod_sub_type_id("0");
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);

			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			if (bean.getFinyear().equalsIgnoreCase("CURRENT")) {
				list = reportService.getdatafor_allocation_report2_current(bean);
			} else {
				list = reportService.getdatafor_allocation_report2(bean);
				// list = reportService.getdatafor_allocation_report2_previous(bean);
			}

			if (list.size() != 0) {

				filename = allocation_report1_service.generate_Angular_allocation2_report(list, companyname, empId);
				map.put("list", list);
				map.put("filename", filename);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-annualsample-plan-batchwise-report")
	public Map<String, Object> getannualsamplebatchwisereport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Annual_sample_plan_view_report_batchwise> list = null;
		String filename = null;
		try {
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			System.out.println("FINYEAR" + bean.getFinyear());
			System.out.println("ASP_DIVID" + bean.getTeaminfo());
			System.out.println("ASP_BRAND_ID" + bean.getBrandids());
			System.out.println("ASP_PROD_ID" + bean.getProductid());
			System.out.println("PEMP_ID" + bean.getEmpid());

			list = reportService.getDataforannualplanviewbatchwise(bean);

			if (list.size() != 0) {
				filename = annual_sample_plan_view_service.generate_annual_sample_plan_view_Batchwise_report(list,
						companyname);
			}

			map.put("filename", filename);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-annualsample-plan-itemwise-report")
	public Map<String, Object> getannualsampleitemwisereport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Annual_sample_plan_view_report_itemwise> list = null;
		String filename = null;
		try {
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			System.out.println("FINYEAR" + bean.getFinyear());
			System.out.println("ASP_DIVID" + bean.getTeaminfo());
			System.out.println("ASP_BRAND_ID" + bean.getBrandids());
			System.out.println("ASP_PROD_ID" + bean.getProductid());
			System.out.println("PEMP_ID" + bean.getEmpid());

			list = reportService.getDataforannualplanviewitemwise(bean);

			if (list.size() != 0) {
				filename = annual_sample_plan_view_service.generate_annual_sample_plan_view_Itemwise_report(list,
						companyname, empId);
			}

			map.put("filename", filename);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-annualsample-plan-con-report")
	public Map<String, Object> getannualsampleconreport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Annual_sample_plan_view_report_cons> list = null;
		String filename = null;
		try {
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			Character asp_type = bean.getType().charAt(0);
			String asp_id = bean.getId();
			list = reportService.getDataforannualplanviewcon(asp_type, asp_id);

			if (list.size() != 0) {
				filename = annual_sample_plan_view_service.generate_annual_sample_plan_con_report(list, companyname,
						bean.getFinYear());
			}
			map.put("list", list);
			map.put("filename", filename);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getItemListByDivIdwithall")
	public Map<String, Object> getItemListByDivIdwithall(@RequestParam String divId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmpItem> itemList = null;
		try {
			itemList = productMasterService.getItemListByDivId(divId);

			SmpItem smp = new SmpItem();
			smp.setSmp_prod_name("All");
			smp.setSmp_prod_id(0l);
			itemList.add(0, smp);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("itemList", itemList);
		return map;
	}

	@GetMapping("/getBatchListByProdIdwithall")
	public Map<String, Object> getBatchListByProdIdwithall(@RequestParam String prodId, @RequestParam String emp_loc)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SmpBatch> batchList = null;
		try {
			batchList = batchMasterService.getBatchListByProdId_and_LocId(prodId, emp_loc);
			SmpBatch smb = new SmpBatch();

			smb.setBatch_no("All");
			smb.setBatch_id(0l);

			batchList.add(0, smb);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("batchList", batchList);
		return map;
	}

	@PostMapping("/get-product-for-dispatch-All")
	public Map<String, Object> getProductListForDspAll(@RequestBody ReportBean bean, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<ReportBean> productList = null;
		bean.setEmpId(bean.getEmp_id()); // get it from session
		try {
			
			productList = dispatchService.getProducts(Long.valueOf(bean.getDivId()), Integer.parseInt(bean.getLocId()),
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate()),
					MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()), "0", bean.getDesgWise(),
					bean.getFstaff_id_o(), bean.getDeletedInvoice(), bean.getEmpNo(), bean.getEmpId(),
					bean.getFinyear(), bean.getFinyearflag());
			System.out.println("productList::" + productList.size());

			// added all
			ReportBean list = new ReportBean();
			list.setProductName("All");
			list.setProductId(0l);
			productList.add(0, list);

			// till here
			map.put("productList", productList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-current-finyear")
	public Map<String, Object> getcurrent_finyear(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String subCompName = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		List<FinYear> financialYears = null;
		LinkedHashMap<String, String> finYears = new LinkedHashMap<String, String>();
		String from = "";
		String to = "";
		try {
			financialYears = financialYearService.getcurrentfinyearlist(subCompName.trim());
			for (FinYear f : financialYears) {
				from = f.getFin_start_date().toString().substring(8, 10) + "-"
						+ f.getFin_start_date().toString().substring(5, 7) + "-"
						+ f.getFin_start_date().toString().substring(0, 4);
				to = f.getFin_end_date().toString().substring(8, 10) + "-"
						+ f.getFin_end_date().toString().substring(5, 7) + "-"
						+ f.getFin_end_date().toString().substring(0, 4);
				finYears.put(f.getFin_year(), from + " To " + to);
			}
			map.put("finYearMap", finYears);
			map.put("financialYears", financialYears);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getdispatch_alloc_month_wise_report")
	public Map<String, Object> getBrandList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_alloc_monthwise_report> lst = new ArrayList<>();
		String filename = null;
		try {
			// ReportBean bean = new ReportBean();
			// bean.setCurryearflg("N");
			// bean.setFin_year("2018");

			lst = this.reportService.getDispatch_alloc_monthwise_report_data(bean);

			System.out.println(bean.getFinstartdate());
			System.out.println(bean.getFinenddate());

			if (lst.size() != 0) {
				filename = dispatch_alloc_monthwise_reportservice.generate_dispatch_alloc_monthwise_report(lst,
						bean.getFinstartdate(), bean.getFinenddate());
				map.put("filename", filename);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-finyearbyfincurr")
	public Map<String, Object> getfinyearbyfincurr(@RequestParam("currorprev") String currorprev, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String subCompName = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		List<FinYear> financialYears = null;

		try {
			financialYears = financialYearService.getFinyearbycurrorprev(subCompName.trim(), currorprev);

			map.put("finyear", financialYears);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@GetMapping("/getProductType")
	public Map<String, Object> getStockType(	 HttpSession session,
			HttpServletRequest request) {
		
		
		
		Map<String, Object> map = new HashMap<>();
	

		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			map.put("prodtype", this.reportService.getStockType(empId));

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getBrandList")
	public Map<String, Object> getBrandList(@RequestParam("divId") String divId,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("div_id::" + divId);
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			map.put("BrandList", this.reportService.getBrandsForTeam(empId, divId));

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getBrandList-stmt-report")
	public Map<String, Object> getBrandListStmtReport(
			@RequestParam("divId") String divId, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		System.out.println("div_id::" + divId);
		try {
			
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			System.out.println("emp_id::" + empId);
			// Created as per instructions of Uday Sir, 19 Jun 2023 -- Samruddha
			map.put("BrandList", this.reportService.getBrandsForTeamStmtReport(empId, divId));

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-quarantine-grn-report")
	public Map<String, Object> getquarantinegrn_report(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Grn_quarantine_prod_summary> grn_quarantine_summary_list = null;
		List<GrnquarantineProd_Detail> grn_quarantine_detail_list = null;
		String filename = null;
		try {
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			grn_quarantine_summary_list = reportService.getGren_quarantine_summary_data(bean);
			grn_quarantine_detail_list = reportService.getGRNQuarantineDetailData(bean);

			if (grn_quarantine_summary_list.size() != 0 && grn_quarantine_detail_list.size() != 0) {

				filename = grn_quarantine_reportservice.generateGrn_quarantine_report(grn_quarantine_summary_list,
						grn_quarantine_detail_list,
						MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getStartDate()),
						MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()), companyname, empId);

			}
			map.put("summary", grn_quarantine_summary_list);
			map.put("detail", grn_quarantine_detail_list);
			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-Lr-Expenses_report")
	public Map<String, Object> getLrExpense_report(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Lr_Expenses_Report> lst = null;
		String filename = null;
		try {
			// ReportBean bean = new ReportBean();
			// bean.setLocId("1");
			// bean.setDivId("48,50");
			// bean.setTransporter("BLUE DART");
			// bean.setFromDate("31/08/2020");
			// bean.setToDate("30/09/2020");
			List<Sub_Company> List = null;
			List = this.reportService.getCompanyName(bean.getSubcompid());
			// map.put("companyname", List);

			String company = List.get(0).getSubcomp_nm(); // ((Company)
															// session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			System.out.println("company :: " + company);
			lst = reportService.getLrExpensesReportdata(bean);

			if (lst.size() != 0) {
				filename = lr_expensesreportservice.generate_lr_Expenses_Report(lst, company);
				map.put("filename", filename);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-div-for-report")
	public Map<String, Object> getdivision_report_list(@RequestParam("userid") String userid, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DivMaster> List = null;

		try {
			List = this.divisionMasterService.getDivreport(userid);
			// DivMaster dm=new DivMaster();
			// dm.setDiv_id(0l);
			// dm.setDiv_disp_nm("All");
			// List.add(0, dm);

			map.put("DivList", List);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-pfz-pip-loc")
	public Map<String, Object> getloc_list(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Location> locationList = null;
		try {

			locationList = this.locationService.getlocationforpfzandpip();
			Location loc = new Location();
			// loc.setLoc_id(0l);
			// loc.setLoc_nm("All");
			// loc.setLoc_subcomp_id(0l);
			// locationList.add(0, loc);

			map.put("locationlist", locationList);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-transporterlist")
	public Map<String, Object> getTransporter_list(@RequestParam("startdate") String Startdate,
			@RequestParam("enddate") String EndDate, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> List = null;
		try {

			System.out.println("stdt :: " + Startdate + " endt :: " + EndDate);

			List = this.reportService.gettransporterlist(Startdate, EndDate);
			Dispatch_Header hd = new Dispatch_Header();
			hd.setDsp_transporter("All");
			List.add(0, hd);

			map.put("transportorList", List);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/lr-report-download-revised")
	public Map<String, Object> getViaCfaLrdownload_revised_csv(HttpSession session, @RequestBody ReportBean bean,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<LrcsvDownloadReportRevised> list = null;
		String filename = "";
		String tabl_ind = null;
		String strdate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate());
		String enddate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate());
		String fsid = bean.getStaffId().toString();
		String cfa = bean.getCfaLocId();
		String fstype = "0";
		String deptloc = bean.getDeptloc();
		StringBuffer temp = new StringBuffer();
		String role = bean.getRole();
		String user = bean.getUsername();
		String transporter = bean.getTranptr();
		try {

			if (tabl_ind == null) {
				tabl_ind = "A";
			}

			if (!deptloc.trim().equals("0")) {
				for (int i = 0; i < deptloc.length(); i = i + 4) {
					temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
				}
				if (temp.length() > 0) {
					temp.deleteCharAt(temp.length() - 1);
				}
				temp.insert(0, "(");
				temp.insert(temp.length(), ")");
			}
			String compcode = session.getServletContext().getAttribute(COMPANY_CODE).toString();

			list = reportService.get_Lrcsv_revised_data(bean, tabl_ind, cfa, fsid, fstype, deptloc, temp.toString(),
					role, user);

			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);

			filename = lr_csv_download_service.generateLR_Revised_csvReport(list, bean.getFromDate(), bean.getToDate(),
					tabl_ind, cfa, fsid, fstype, compcode, role, user, transporter, empId);
			map.put("filename", filename);
			map.put("list", list);
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@PostMapping("/get-pso-lr-revised-detail")
	public Map<String, Object> getLrdownload_revised_csv(HttpSession session, @RequestBody ReportBean bean,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
//		List<Lrcsv_RevisedDownload> list = null;
	
		String empId = null;
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bean.setEmp_id(empId);
		
		String filename = "";
		String deptloc = "0";// from session
		String stk_at_cfa_option = "0";// from session
		String compcd = session.getServletContext().getAttribute(COMPANY_CODE).toString();

		Company compMaster = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		
		

		String strdate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate());
		String enddate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate());
		String fsid = bean.getStaffId().toString();
		String cfa = bean.getCfaLocId();
		System.out.println("deptloc " + deptloc);
		System.out.println("stk_at_cfa_option " + stk_at_cfa_option);
		String tbl_ind = "";
		String stock_at_cfa_ind = "N";
		String tmpOrActl = null;
		String role = bean.getRole();
		String username = bean.getUsername();

		List<String> loc = bean.getLocation_new();
		List<String> div = bean.getDivision_new();
		List<String> dest = bean.getDestination();
		try {
			StringBuffer temp = new StringBuffer();

			if (tmpOrActl == null) {
				tmpOrActl = "A";
			}

			if (!deptloc.trim().equals("0")) {
				for (int i = 0; i < deptloc.length(); i = i + 4) {
					temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
				}
				if (temp.length() > 0) {
					temp.deleteCharAt(temp.length() - 1);
				}
				temp.insert(0, "(");
				temp.insert(temp.length(), ")");
			}

			if(compMaster.getCfa_to_stk_ind().equals("Y")) {
				List<Lrcsv_RevisedDownload> list  = reportService.getLrcsvdownload_Revised_data(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
						stk_at_cfa_option, temp.toString(), deptloc, role, username, loc, div, dest,
						compMaster.getCfa_to_stk_ind());
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empIdd = this.userMasterService.getEmpIdByUsername(uname);
				filename = lr_csv_download_service.Generate_Lr_Revised_CSV(list, bean.getUsername(), bean.getRole(), "D",
						compcd, empIdd);
				map.put("list", list);
			}else {
				List<Lrcsv_RevisedDownload_SG> list = reportService.getLrcsvdownload_Revised_data_sg(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
						stk_at_cfa_option, temp.toString(), deptloc, role, username, loc, div, dest,
						compMaster.getCfa_to_stk_ind());
				
				
				
				
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empIdd = this.userMasterService.getEmpIdByUsername(uname);
				
				
				
				filename = lr_csv_download_service.Generate_Lr_Revised_CSV_SG(list, bean.getUsername(), bean.getRole(), "D",
						compcd, empIdd);
				
//				List<LrEntryDataBean> details = lrEntryService.getLrcsvdownload_Revised_data_for_sg(strdate, enddate, "A", depo_loc_id,
//						"0", "N", "N", "0", "0", role, user, loc, div, dest, trans,compMaster.getCfa_to_stk_ind());
//				map.put("lrDetails", details);
				
				map.put("list", list);
				
			}
			

			
			
			
			
//			String uname = this.tokenProvider.getUsernameFromRequest(request);
//			String empIdd = this.userMasterService.getEmpIdByUsername(uname);
//			
//			filename = lr_csv_download_service.Generate_Lr_Revised_CSV(list, bean.getUsername(), bean.getRole(), "D",
//					compcd, empIdd);
			

			map.put("filename", filename);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@GetMapping("/get-currfinyear-date")
	public Map<String, Object> getCurrFinYearStartdtandEnddt(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<FinYear> finyr = null;
		List<Period> period = null;
		try {
			Date strtdt = null;
			Date endt = null;

			String period_start_date = "";
			String period_end_date = "";
			String compcode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			finyr = financialYearService.getFinyearbycurrorprev(compcode, "Y");
			period = financialYearService.getcurrent_Period(compcode);

			for (Period p : period) {

				period_start_date = p.getPeriod_start_date();
				period_end_date = p.getPeriod_end_date();
			}

			for (FinYear f : finyr) {
				if (strtdt == null && endt == null) {
					strtdt = f.getFin_start_date();
					endt = f.getFin_end_date();
				}
			}

			map.put("startDate", strtdt);
			map.put("endDate", endt);
			map.put("period_start_date", period_start_date);
			map.put("period_end_date", period_end_date);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@PostMapping("/get-dispatch-summary-report-data-revised")
	public Map<String, Object> getDispatchSumDetDataRevised(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DispatchSummaryDetailReport_Revised> list = null;
		String userId = bean.getEmp_id();// "E00040"; // get it from session //changed
		bean.setUserId(userId);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String compcode = session.getServletContext().getAttribute(COMPANY_CODE).toString();

		try {
			bean.setCompcd(compcode);
			list = reportService.callDisptachSumDetReportRevised(bean);
			if (list != null && !list.isEmpty()) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				String filename = this.dispatchSummaryDetailReportService.generateDispatchSumDetReportRevised(list,
						bean, companyname, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-dispatch-detail-report-data-revised")
	public Map<String, Object> getDispatchDetDataRevised(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
	
		String empId = null;
		try {
		
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userId = empId;
		
		// get it from session //changed
		String deptloc = "0";
		// get it from session

		bean.setUserId(userId);
		bean.setDeptloc(deptloc);
		Company company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA));
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		bean.setCompcd(companyCode);
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empIdd = this.userMasterService.getEmpIdByUsername(uname);
						
				if(company.getCfa_to_stk_ind().equals("Y")) {
					List<DispatchDetailReportRevised> 	list = reportService.callDisptachDetReportRevised(bean);
					if (list != null && !list.isEmpty()) {
						String filename = this.dispatchSummaryDetailReportService.generateDispatchDetReportRevised(list, bean,
								companyname, empIdd, company.getCfa_to_stk_ind());
						map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
						map.put("list", list.size());
						list = reportService.callDisptachDetReportRevised(bean);
						map.put("list_bean", list);
					} else {
						map.put("list", 0);
					}
				}else {
								
					if(companyCode.trim().equalsIgnoreCase("PFZ")) {
						List<DispatchDetailReportRevised_SG> 	list = reportService.callDisptachDetReportRevised_SG(bean);
						if (list != null && !list.isEmpty()) {
							String filename = this.dispatchSummaryDetailReportService.generateDispatchDetReportRevised_SG(list, bean,
									companyname, empIdd);
							map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
							map.put("list", list.size());
							list = reportService.callDisptachDetReportRevised_SG(bean);
							LocalDate endDate = bean.getEndDate().toInstant()
				                    .atZone(ZoneId.systemDefault())
				                    .toLocalDate();

							LocalDate cutoffDate = LocalDate.of(2024, 12, 1);

							if(bean.getCompcd().trim().equalsIgnoreCase(PFZ) && endDate.isBefore(cutoffDate)) {
								list = list.stream().peek(data -> {
									if ("CITIUS".equalsIgnoreCase(data.getDiv_disp_nm().trim())
											|| "CITIUS  (ALTIUS - NEW)".equalsIgnoreCase(data.getDiv_disp_nm().trim())) {
										data.setDiv_disp_nm("ALTIUS - NEW");
									}
								}).collect(Collectors.toList());

							}
							
							map.put("list_bean", list);
						} else {
							map.put("list", 0);
						}
					}
					else {
						List<DispatchDetailReportRevised_VET> 	list = reportService.callDisptachDetReportRevised_VET(bean);
						if (list != null && !list.isEmpty()) {
							String filename = this.dispatchSummaryDetailReportService.generateDispatchDetReportRevised_VET(list, bean,
									companyname, empIdd);
							map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
							map.put("list", list.size());
							
							list = reportService.callDisptachDetReportRevised_VET(bean);
							
							map.put("list_bean", list);
						} else {
							map.put("list", 0);
						}
					}
				
					
				}
			
	
		

			
	
			map.put("cfa_ind", company.getCfa_to_stk_ind());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-delivery-reciept-download")
	public Map<String, Object> getLrDeliveryRecieptDownloadcsv(HttpSession session, @RequestBody ReportBean bean,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
//		List<Lrcsv_RevisedDownload> list = null;
		String filename = "";
		String deptloc = "0";// from session
		String stk_at_cfa_option = "0";// from session
		String strdate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getStartDate());
		String enddate = MedicoResources.convertUtilDateToString_YYYY_MM_DD_(bean.getEndDate());
		String fsid = bean.getStaffId().toString();
		String cfa = bean.getCfaLocId();
		System.out.println("deptloc " + deptloc);
		System.out.println("stk_at_cfa_option " + stk_at_cfa_option);
		String tbl_ind = "";
		String stock_at_cfa_ind = "N";
		String tmpOrActl = null;
		String role = bean.getRole();
		String username = bean.getUsername();
		String compcd = session.getServletContext().getAttribute(COMPANY_CODE).toString();

		Company compMaster = (Company) session.getServletContext().getAttribute(COMPANY_DATA);

		List<String> loc = bean.getLocation_new();
		List<String> div = bean.getDivision_new();
		List<String> dest = bean.getDestination();
		try {
			StringBuffer temp = new StringBuffer();

			if (tmpOrActl == null) {
				tmpOrActl = "A";
			}

			if (!deptloc.trim().equals("0")) {
				for (int i = 0; i < deptloc.length(); i = i + 4) {
					temp.append(Integer.parseInt(deptloc.substring(i, i + 4))).append(",");
				}
				if (temp.length() > 0) {
					temp.deleteCharAt(temp.length() - 1);
				}
				temp.insert(0, "(");
				temp.insert(temp.length(), ")");
			}

			
			if(compMaster.getCfa_to_stk_ind().equals("Y")) {
				
				List<Lrcsv_RevisedDownload> list = reportService.getLrDeliveryRecieptcsvdata(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
						stk_at_cfa_option, temp.toString(), deptloc, role, username, loc, div, dest,
						compMaster.getCfa_to_stk_ind());
				map.put("list", list);
				
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				filename = lr_csv_download_service.Generate_Lr_Revised_CSV(list, bean.getUsername(), bean.getRole(), "U",
						compcd, empId);

				map.put("filename", filename);
				
			}
			else {
				List<Lrcsv_RevisedDownload_SG> list = reportService.getLrDeliveryRecieptcsvdata_SG(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
						stk_at_cfa_option, temp.toString(), deptloc, role, username, loc, div, dest,
						compMaster.getCfa_to_stk_ind());
				map.put("list", list);
			
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				filename = lr_csv_download_service.Generate_Lr_Revised_CSV_SG(list, bean.getUsername(), bean.getRole(), "U",
						compcd, empId);

				map.put("filename", filename);
			}
			
			
			
			
			
//			String uname = this.tokenProvider.getUsernameFromRequest(request);
//			String empId = this.userMasterService.getEmpIdByUsername(uname);
//			filename = lr_csv_download_service.Generate_Lr_Revised_CSV(list, bean.getUsername(), bean.getRole(), "U",
//					compcd, empId);

//			map.put("filename", filename);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@PostMapping("/get-dispatch-detail-report-data-rmdm")
	public Map<String, Object> getDispatchDetDataRmdm(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DispatchDetailReportRbmdm> list = null;
		String userId = bean.getEmp_id();// get it from session //changed
		String deptloc = "0";// get it from session

		bean.setUserId(userId);
		bean.setDeptloc(deptloc);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			list = reportService.callDisptachDetReportRmdm(bean);
			if (list != null && !list.isEmpty()) {
				String filename = this.dispatchSummaryDetailReportService.generateDispatchDetReportRbmdm(list, bean,
						companyname);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-stock-withdrawal-report")
	public Map<String, Object> getwithdrawalreport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<StockWithdrawalReport> list = null;
		String filename = null;
		try {

			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			list = reportService.getStockwithdrawalReport(bean);
			String company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			if (list != null && !list.isEmpty()) {
				filename = stockwithdrawalreportservice.generateStockwithdrawalReport(bean, list, company, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-stock-withdrawal-report-data")
	public Map<String, Object> getwithdrawalreportdata(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Location> locList = null;
		try {
			locList = locationService.getAllLocations();
			map.put("loclist", locList);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-pforce-rx-fieldstaff-insert-error-report")
	public Map<String, Object> getPforceRxFieldStaffErrorReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Temp_fstaffmas_prx_error> lst = null;
		String filename = null;
		try {
			lst = reportService.getpforcerxfstafferrordata(bean.getStartDate(), bean.getEndDate());
			String company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			if (lst != null && lst.size() > 0) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				filename = pforcerxreportgeneration.GeneratePforceRxFieldstaffInsertErrorReport(lst, bean, company,
						empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			} else {
				map.put("filename", filename);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-dg-veeva-pos-emp-report")
	public Map<String, Object> getDgVeevaPosEmpReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dg_veeva_pos_emp> lst = null;
		String filename = null;
		try {
			lst = reportService.getDgveevaposempdata(bean.getStartDate(), bean.getEndDate());
			String company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			if (lst != null && lst.size() > 0) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				filename = pforcerxreportgeneration.GenerateDgVeevaPosEmpReport(lst, bean, company, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			} else {
				map.put("filename", filename);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-dg-veeva-pos-report")
	public Map<String, Object> getDgVeevaPosReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dg_veeva_pos> lst = null;
		String filename = null;
		try {
			lst = reportService.getDgveevaposdata(bean.getStartDate(), bean.getEndDate());
			String company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			if (lst != null && lst.size() > 0) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				filename = pforcerxreportgeneration.GenerateDgVeevaPosReport(lst, bean, company, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			} else {
				map.put("filename", filename);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-prx-FieldStaff-report")
	public Map<String, Object> getPrxFieldstaffStatusReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<FstaffMas_PrxBean> Newlst = null;
		List<FstaffMas_PrxBean> Updatelst = null;
		List<FstaffMas_PrxBean> terrlst = null;

		String filename = null;
		try {
			Updatelst = new ArrayList<>();
			Newlst = new ArrayList<>();
			terrlst = new ArrayList<>();

			Updatelst = reportService.getPrxFieldStaffStatusdata(bean.getStartDate(), bean.getEndDate(), 'C');
			Newlst = reportService.getPrxFieldStaffStatusdata(bean.getStartDate(), bean.getEndDate(), 'N');
			terrlst = reportService.getPrxFieldTerr_data(bean.getStartDate(), bean.getEndDate(), 'N');
			String company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			if (Updatelst != null && Updatelst.size() > 0 || Newlst != null && Newlst.size() > 0
					|| terrlst != null && terrlst.size() > 0) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				filename = pforcerxreportgeneration.GeneratePrxFieldstaffReport(Newlst, Updatelst, terrlst, bean,
						company, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			} else {
				map.put("filename", filename);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-pforce-rx-dg-veeva-emp-report")
	public Map<String, Object> getPforceRxDgVeevaEmprReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dg_veeva_emp> lst = null;
		String filename = "";
		try {
			lst = reportService.getpforcerxDgVeevaEmpData(bean.getStartDate(), bean.getEndDate());
			String company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			if (lst != null && lst.size() > 0) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				filename = pforcerxreportgeneration.GenerateDgVeevaEmpReport(lst, bean, company, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// Added On 14-08-2021
	@GetMapping("/get-all-div-list")
	public Map<String, Object> getAllDivListByUserId( HttpSession session,
			HttpServletRequest request) {
		
		String empId = null;
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<>();
		String userId = empId; // "E00040"; // get it from session
		List<DivMaster> divList = null;
		try {
			divList = divisionMasterService.getAllDivAccessedByUser(userId);
			DivMaster dm = new DivMaster();
			dm.setDiv_id(0l);
			dm.setDiv_disp_nm("All");
			divList.add(0, dm);
			map.put("divList", divList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-company-data")
	public Map<String, Object> getCompanydata() {
		Map<String, Object> map = new HashMap<>();
		try {
			Company company = companyMasterService.getCompanyDetails();
			map.put("company", company);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-iaa-detail-report")
	public Map<String, Object> getIAADetailReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		// public Map<String, Object> getIAADetailReport(HttpSession
		// session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<IAADetailReport> lst = null;
		List<IAADetailReport_SG> lstSG = null;

		String filename = "";
		// Long finYrId=16l;
		// String StartDate ="01-11-2020";
		// String EndDate="30-11-2021";
		Company companyInd = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();		
		try {
			SimpleDateFormat sdf3 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			Date date1 = sdf3.parse(bean.getStartDate().toString());
			Date date2 = sdf3.parse(bean.getEndDate().toString());
			String startdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
			String enddate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);
			Company comp = companyMasterService.getCompanyDetails();
			String company = comp.getCompany_name();

			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			bean.setCompcd(companyCode.trim());
			if (bean.getFinyear().equalsIgnoreCase("CURRENT")) {
				if(companyInd.getCfa_to_stk_ind().equals("Y")) {
					lst = reportService.getIAADetailReportData(bean.getSlId(), startdate, enddate, bean.getProdId());
					map.put("list", lst);
				}else {
					lstSG = reportService.getIAADetailReportData_for_sg(bean.getSlId(), startdate, enddate);
	                map.put("list", lstSG); 

				}
				
			} else {
				if(companyInd.getCfa_to_stk_ind().equals("Y")) {
					lst = reportService.getIAADetailReportPreviousData(bean.getSlId(), startdate, enddate,
						bean.getProdId());
					map.put("list",lst);
				}else {
					lstSG = reportService.getIAADetailReportPreviousData_for_sg(bean.getSlId(), startdate, enddate);
					
				}
			}
				
			String gprmInd = parameterRepository.getGprmIndicatorForPfz();
//				String company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			if (lst != null && lst.size() > 0) {
				filename = iAADetailReportService.GenerateIAADetailReport(lst, startdate, enddate, company, empId,
						gprmInd);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);

				/*
				 * if (gprmInd.equalsIgnoreCase("Y")) { lst.stream() .filter(i ->
				 * "0.00".equals(i.getIn_qty().toString())) .forEach(i -> {
				 * i.setUnit_price(null); i.setTotal_value(null); }); }
				 */

			}else if(lstSG != null && lstSG.size() >0) {
				filename = iAADetailReportService.GenerateIAADetailReport_SG(lstSG, startdate, enddate, company, empId, gprmInd, bean);
	            map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
                map.put("list", lstSG);

			}

			map.put("gprmInd", gprmInd);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-div-for-Ageing-of-grn-Stock")
	public Map<String, Object> getdivforAgeingofGrnStock(@RequestParam("userid") String userid, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DivMaster> list = null;
		List<Location> location = null;
		List<SG_d_parameters_details> slablist = null;
		try {
			list = reportService.getdivforstockageing(userid);
			location = reportService.getlocationforstockageing();
			// slablist = reportService.getslabdetails("STOCK_AGEING_DAYS");

			map.put("divlist", list);
			map.put("loclist", location);
			// map.put("slablist", slablist);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-Ageing-of-Stock-based-on-grn-expiry-Report")
	public Map<String, Object> getAgeingofStockbasedongrnexpiryreport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<StockAgeingGrnReportView> list = null;
		String check = bean.getReport_type();
		String filename = null;
		try {
			String compcode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			list = reportService.getstockageinggrnreportData(bean);
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			filename = stockageing_report_service.generate_stockAgeingGrnExcel(list,
					MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getEndDate()),
					Long.valueOf(bean.getSlab1a()), Long.valueOf(bean.getSlab1b()), Long.valueOf(bean.getSlab2a()),
					Long.valueOf(bean.getSlab2b()), Long.valueOf(bean.getSlab3a()), Long.valueOf(bean.getSlab3b()),
					Long.valueOf(bean.getSlab4a()), Long.valueOf(bean.getSlab4b()), Long.valueOf(bean.getSlab5a()),
					Long.valueOf(bean.getSlab5b()), Long.valueOf(bean.getSlab6a()), companyname, compcode,
					bean.getDoctype(), bean.getLocName(), empId);

			// map.put("list", list);
			map.put("filename", filename);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-without-all-div-list")
	public Map<String, Object> getwithoutAllDivListByUserId(@RequestParam("userid") String userid, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String userId = userid; // "E00040"; // get it from session
		List<DivMaster> divList = null;
		try {
			divList = divisionMasterService.getAllDivAccessedByUser(userId);
			DivMaster dm = new DivMaster();
			map.put("divList", divList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-period-count")
	public Map<String, Object> getwithoutAllDivListByUserId(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {

			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmp_id(empId);
			System.out.println(bean.getStartDate());
			System.out.println(bean.getEndDate());
			System.out.println(bean.getFinyearflag());
			map.put("count",
					periodmasterservice.getPeriodCount(bean.getStartDate(), bean.getEndDate(), bean.getFinyearflag()));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@PostMapping("/get-Dispatch-Register-Report1")
	public Map<String, Object> getDispatchRegisterReport1(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<dispatch_register_report1> list = null;
		String filename = null;
		Set<String> setproductnames = new HashSet<String>();
		Set<String> setproductTypes = new HashSet<String>();
		Set<String> setmonthofUse = new HashSet<String>();
		Set<String> setdivision = new HashSet<String>();
		Set<String> setdeliStatus = new HashSet<String>();
		Set<String> setcName = new HashSet<String>();
		Set<String> setsubTeam = new HashSet<String>();
		Set<String> setchallanNum = new HashSet<String>();
		Set<String> setdocName = new HashSet<String>();
		Set<String> setemployeeNum = new HashSet<String>();
		Set<String> setchallanDate = new HashSet<String>();
		Set<String> setresponse = new HashSet<String>();
		Set<String> setemployeeName = new HashSet<String>();
		Set<String> setlrNum = new HashSet<String>();
		Set<String> setregName = new HashSet<String>();
		Set<String> setterrCode = new HashSet<String>();
		Set<String> setteam = new HashSet<String>();
		Set<String> setlrDate = new HashSet<String>();
		Set<String> setdmName = new HashSet<String>();

		TreeSet<String> productnames = new TreeSet<String>();
		TreeSet<String> productTypes = new TreeSet<String>();
		TreeSet<String> monthofUse = new TreeSet<String>();
		TreeSet<String> division = new TreeSet<String>();
		TreeSet<String> deliStatus = new TreeSet<String>();
		TreeSet<String> cName = new TreeSet<String>();
		TreeSet<String> subTeam = new TreeSet<String>();
		TreeSet<String> challanNum = new TreeSet<String>();
		TreeSet<String> docName = new TreeSet<String>();
		TreeSet<String> employeeNum = new TreeSet<String>();
		TreeSet<String> challanDate = new TreeSet<String>();
		TreeSet<String> response = new TreeSet<String>();
		TreeSet<String> employeeName = new TreeSet<String>();
		TreeSet<String> lrNum = new TreeSet<String>();
		TreeSet<String> regName = new TreeSet<String>();
		TreeSet<String> terrCode = new TreeSet<String>();
		TreeSet<String> team = new TreeSet<String>();
		TreeSet<String> lrDate = new TreeSet<String>();
		TreeSet<String> dmName = new TreeSet<String>();
		try {
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			list = reportService.getDispatchRegisterReport1(bean);
			if (list != null && list.size() > 0) {

				setproductnames = list.stream().flatMap(e -> Stream.of(e.getProduct_name()))
						.collect(Collectors.toSet());
				// filename=dispatchRegisterReport1.generateDispatchRegisterReport1Excel(list,bean,companyname);
				setproductTypes = list.stream().flatMap(e -> Stream.of(e.getProduct_type()))
						.collect(Collectors.toSet());
				setmonthofUse = list.stream().flatMap(e -> Stream.of(e.getMonth_of_use())).collect(Collectors.toSet());
				setdivision = list.stream().flatMap(e -> Stream.of(e.getTeam_name())).collect(Collectors.toSet()); // need
																													// to
																													// check
				setdeliStatus = list.stream().flatMap(e -> Stream.of(e.getDelivery_status()))
						.collect(Collectors.toSet());
				setcName = list.stream().flatMap(e -> Stream.of(e.getCourier())).collect(Collectors.toSet());
				setsubTeam = list.stream().flatMap(e -> Stream.of(e.getSub_team_name())).collect(Collectors.toSet());
				setchallanNum = list.stream().flatMap(e -> Stream.of(e.getChallan_no())).collect(Collectors.toSet());
				setdocName = list.stream().flatMap(e -> Stream.of(e.getDoctor_name())).collect(Collectors.toSet());
				setemployeeNum = list.stream().flatMap(e -> Stream.of(e.getEmployee_no())).collect(Collectors.toSet());
				setchallanDate = list.stream().flatMap(e -> Stream.of(e.getChallan_date())).collect(Collectors.toSet());
				setresponse = list.stream().flatMap(e -> Stream.of(e.getReceiver_response()))
						.collect(Collectors.toSet());
				setemployeeName = list.stream().flatMap(e -> Stream.of(e.getEmployee_name()))
						.collect(Collectors.toSet());
				setlrNum = list.stream().flatMap(e -> Stream.of(e.getLr_no())).collect(Collectors.toSet());
				setregName = list.stream().flatMap(e -> Stream.of(e.getRegion())).collect(Collectors.toSet());
				setterrCode = list.stream().flatMap(e -> Stream.of(e.getTerritory_code())).collect(Collectors.toSet());
				setteam = list.stream().flatMap(e -> Stream.of(e.getTeam_name())).collect(Collectors.toSet()); // need
																												// to
																												// check
				setlrDate = list.stream().flatMap(e -> Stream.of(e.getLr_date())).collect(Collectors.toSet());
				setdmName = list.stream().flatMap(e -> Stream.of(e.getDm())).collect(Collectors.toSet());

				setproductnames.remove(null);
				setproductTypes.remove(null);
				setmonthofUse.remove(null);
				setdivision.remove(null);
				setdeliStatus.remove(null);
				setcName.remove(null);
				setsubTeam.remove(null);
				setchallanNum.remove(null);
				setdocName.remove(null);
				setemployeeNum.remove(null);
				setchallanDate.remove(null);
				setresponse.remove(null);
				setemployeeName.remove(null);
				setlrNum.remove(null);
				setregName.remove(null);
				setterrCode.remove(null);
				setteam.remove(null);
				setlrDate.remove(null);
				setdmName.remove(null);

				productnames.addAll(setproductnames);
				productTypes.addAll(setproductTypes);
				monthofUse.addAll(setmonthofUse);
				division.addAll(setdivision);
				deliStatus.addAll(setdeliStatus);
				cName.addAll(setcName);
				subTeam.addAll(setsubTeam);
				challanNum.addAll(setchallanNum);
				docName.addAll(setdocName);
				employeeNum.addAll(setemployeeNum);
				challanDate.addAll(setchallanDate);
				response.addAll(setresponse);
				employeeName.addAll(setemployeeName);
				lrNum.addAll(setlrNum);
				regName.addAll(setregName);
				terrCode.addAll(setterrCode);
				team.addAll(setteam);
				lrDate.addAll(setlrDate);
				dmName.addAll(setdmName);

				productnames.remove("");
				productTypes.remove("");
				monthofUse.remove("");
				division.remove("");
				deliStatus.remove("");
				cName.remove("");
				subTeam.remove("");
				challanNum.remove("");
				docName.remove("");
				employeeNum.remove("");
				challanDate.remove("");
				response.remove("");
				employeeName.remove("");
				lrNum.remove("");
				regName.remove("");
				terrCode.remove("");
				team.remove("");
				lrDate.remove("");
				dmName.remove("");

			}
			map.put("list", list);
			map.put("productnames", productnames);
			map.put("productTypes", productTypes);
			map.put("monthofuse", monthofUse);
			map.put("division", division);
			map.put("deliStatus", deliStatus);
			map.put("cName", cName);
			map.put("subTeam", subTeam);
			map.put("challanNum", challanNum);
			map.put("docName", docName);
			map.put("employeeNum", employeeNum);
			map.put("challanDate", challanDate);
			map.put("response", response);
			map.put("employeeName", employeeName);
			map.put("lrNum", lrNum);
			map.put("regName", regName);
			map.put("terrCode", terrCode);
			map.put("team", team);
			map.put("lrDate", lrDate);
			map.put("dmName", dmName);

			// map.put("filename", filename);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-Dispatch-Register-Report1-filter")
	public Map<String, Object> getDispatchRegisterReport1filter(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("prod : " + bean.getProductname());
			String x = bean.getDispatch_register_report1list();
			// JSONObject object = new JSONObject(x);
			final ObjectMapper objectMapper = new ObjectMapper();
			List<dispatch_register_report1> filteredList = objectMapper.readValue(x,
					new TypeReference<List<dispatch_register_report1>>() {
					});
			System.out.println("filteredList :: " + filteredList.size());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-Dispatch-Register-Report2")
	public Map<String, Object> getDispatchRegisterReport2(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<dispatch_register_report2> list = null;
		String filename = null;
		Set<String> productnames = new HashSet<String>();
		Set<String> productTypes = new HashSet<String>();
		Set<String> monthofUse = new HashSet<String>();
		Set<String> division = new HashSet<String>();
		Set<String> deliStatus = new HashSet<String>();
		Set<String> cName = new HashSet<String>();
		Set<String> subTeam = new HashSet<String>();
		Set<String> challanNum = new HashSet<String>();
		Set<String> docName = new HashSet<String>();
		Set<String> employeeNum = new HashSet<String>();
		Set<String> challanDate = new HashSet<String>();
		Set<String> response = new HashSet<String>();
		Set<String> employeeName = new HashSet<String>();
		Set<String> lrNum = new HashSet<String>();
		Set<String> regName = new HashSet<String>();
		Set<String> terrCode = new HashSet<String>();
		Set<String> team = new HashSet<String>();
		Set<String> lrDate = new HashSet<String>();
		Set<String> dmName = new HashSet<String>();
		try {
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			list = reportService.getDispatchRegisterReport2(bean);
			if (list != null && list.size() > 0) {
				productnames = list.stream().flatMap(e -> Stream.of(e.getItem_dispatched()))
						.collect(Collectors.toSet());
				// filename=dispatchRegisterReport2.generateDispatchRegisterReport2Excel(list,bean,companyname);
				productTypes = list.stream().flatMap(e -> Stream.of(e.getProduct_type())).collect(Collectors.toSet());
				// monthofUse = list.stream().flatMap(e ->
				// Stream.of(e.getMonth_of_use())).collect(Collectors.toSet());
				division = list.stream().flatMap(e -> Stream.of(e.getTeam_name())).collect(Collectors.toSet());

				// cName= list.stream().flatMap(e->
				// Stream.of(e.getCourier())).collect(Collectors.toSet());
				// subTeam = list.stream().flatMap(e->
				// Stream.of(e.getSub_team_name())).collect(Collectors.toSet());
				challanNum = list.stream().flatMap(e -> Stream.of(e.getChallan_no())).collect(Collectors.toSet());

				// employeeNum = list.stream().flatMap(e->
				// Stream.of(e.getEmployee_no())).collect(Collectors.toSet());
				challanDate = list.stream().flatMap(e -> Stream.of(e.getChallan_date())).collect(Collectors.toSet());

				employeeName = list.stream().flatMap(e -> Stream.of(e.getRequested_by())).collect(Collectors.toSet());
				lrNum = list.stream().flatMap(e -> Stream.of(e.getLr_no())).collect(Collectors.toSet());
				// team = list.stream().flatMap(e->
				// Stream.of(e.getTeam_name())).collect(Collectors.toSet()); //need to check
				// lrDate = list.stream().flatMap(e->
				// Stream.of(e.getLr_date())).collect(Collectors.toSet());

				terrCode = list.stream().flatMap(e -> Stream.of(e.getTerritory_code())).collect(Collectors.toSet());
				regName = list.stream().flatMap(e -> Stream.of(e.getRegion())).collect(Collectors.toSet());
				dmName = list.stream().flatMap(e -> Stream.of(e.getDm())).collect(Collectors.toSet());
				deliStatus = list.stream().flatMap(e -> Stream.of(e.getDelivery_status())).collect(Collectors.toSet());
				docName = list.stream().flatMap(e -> Stream.of(e.getName_collegue_doctor()))
						.collect(Collectors.toSet());
				response = list.stream().flatMap(e -> Stream.of(e.getDr_ack())).collect(Collectors.toSet());
				cName = list.stream().flatMap(e -> Stream.of(e.getCourier_name())).collect(Collectors.toSet());
				lrDate = list.stream().flatMap(e -> Stream.of(e.getLr_dated())).collect(Collectors.toSet());

				// productnames.remove("");
				productTypes.remove("");
				// monthofUse.remove("");
				division.remove(" ");
				division.remove("");
				division.remove(null);

//				cName.remove("");
//				subTeam.remove("");
				challanNum.remove("");
				// docName.remove("");
				// employeeNum.remove("");
				challanDate.remove("");
				// response.remove("");
				// employeeName.remove("");
				lrNum.remove("");
				// team.remove("");
				// lrDate.remove("");

				terrCode.remove("");
				deliStatus.remove(" ");
				deliStatus.remove("");
				deliStatus.remove(null);
				regName.remove("");
				dmName.remove("");
				docName.remove("");
				response.remove("");
				response.remove(" ");
				cName.remove("");
				lrDate.remove("");
				lrDate.remove(" ");
				lrDate.remove(null);
				productnames.remove(" ");
				employeeName.remove("");

			}
			map.put("list", list);
			map.put("productnames", productnames);
			map.put("productTypes", productTypes);
//				map.put("monthofuse", monthofUse);
			map.put("division", division);

//				map.put("subTeam", subTeam);
			map.put("challanNum", challanNum);
//				map.put("docName", docName);
//				map.put("employeeNum", employeeNum);
			map.put("challanDate", challanDate);
//				map.put("response", response);
			map.put("employeeName", employeeName);
			map.put("lrNum", lrNum);

//				map.put("team", team);

			map.put("regName", regName);
			map.put("terrCode", terrCode);
			map.put("dmName", dmName);
			map.put("deliStatus", deliStatus);
			map.put("cName", cName);
			map.put("lrDate", lrDate);
			map.put("docName", docName);
			map.put("response", response);

			// map.put("filename", filename);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-Dispatch-Register-ExcelReport1")
	public Map<String, Object> getDispatchRegisterExcelReport1(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<dispatch_register_report1> list = null;
		String filename = null;
		try {

			final ObjectMapper objectMapper = new ObjectMapper();
			List<dispatch_register_report1> filteredList = objectMapper.readValue(
					bean.getDispatch_register_report1list(), new TypeReference<List<dispatch_register_report1>>() {
					});
			System.out.println("filteredList :: " + filteredList.size());
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			if (filteredList.size() > 0) {
				filename = dispatchRegisterReport1.generateDispatchRegisterReport1Excel(filteredList, bean, companyname,
						empId);
			}
			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-Dispatch-Register-ExcelReport2")
	public Map<String, Object> getDispatchRegisterExcelReport2(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<dispatch_register_report2> list = null;
		String filename = null;
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			List<dispatch_register_report2> filteredList = objectMapper.readValue(
					bean.getDispatch_register_report2list(), new TypeReference<List<dispatch_register_report2>>() {
					});
			System.out.println("filteredList :: " + filteredList.size());
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			if (filteredList.size() > 0) {
				filename = dispatchRegisterReport2.generateDispatchRegisterReport2Excel(filteredList, bean, companyname,
						empId);
			}
			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-new-division-list")
	public Map<String, Object> getAllDivisionList() {
		Map<String, Object> map = new HashMap<>();
		List<DivMaster> divisionList = null;
		try {
			divisionList = reportService.getAllDivisionList();
//				
			map.put("divisionList", divisionList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-new-period-list")
	public Map<String, Object> getAllPeriodList() {
		Map<String, Object> map = new HashMap<>();
		List<Period> periodList = null;
		try {
			periodList = reportService.getAllPeriodList();

			map.put("periodList", periodList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getcNameList-frm-DivId")
	public Map<String, Object> getcNameList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> cNameList = null;
		try {
			cNameList = reportService.getcNameList(bean);
			map.put("cNameList", cNameList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getProductList-frm-DivId")
	public Map<String, Object> getProductList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<SmpItem> getProductList = null;

		try {
			// productTypes = list.stream().flatMap(e ->
			// Stream.of(e.getProduct_type())).collect(Collectors.toSet());
			getProductList = reportService.getProductList(bean);
			map.put("getProductList", getProductList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-product-type-list")
	public Map<String, Object> getProductTypeList() {
		Map<String, Object> map = new HashMap<>();
		List<SG_d_parameters_details> productTypeList = null;
		try {
			productTypeList = reportService.getProductTypeList();
			System.out.println("product type list:::" + productTypeList.size());
			map.put("productTypeList", productTypeList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getTeamList-frm-DivId")
	public Map<String, Object> getTeamList(@RequestParam("divId") String divId, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> teamList = null;
		try {
			System.out.println("divId:::" + divId);
			teamList = reportService.getTeamList(divId);
			map.put("teamList", teamList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getDocNameList-frm-DivId")
	public Map<String, Object> getDocNameList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> docNameList = null;
		try {
			docNameList = reportService.getDocNameList(bean);
			if (docNameList != null) {
				docNameList.remove(null);
			}
			map.put("docNameList", docNameList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getEmpNameList-frm-DivId")
	public Map<String, Object> getEmpNameList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<ReportBean> empNameList = null;
		try {
			empNameList = reportService.getEmpNameList(bean);
			map.put("empNameList", empNameList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getRegNameList-frm-DivId")
	public Map<String, Object> getRegNameList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<ReportBean> regNameList = null;
		try {
			regNameList = reportService.getRegNameList(bean);
			map.put("regNameList", regNameList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getDmNameList-frm-DivId")
	public Map<String, Object> getDmNameList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<ReportBean> dmNameList = null;
		try {
			dmNameList = reportService.getDmNameList(bean);
			map.put("dmNameList", dmNameList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getTerrCodeList-frm-DivId")
	public Map<String, Object> getTerrCodeList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<ReportBean> terrCodeList = null;
		try {
			terrCodeList = reportService.getTerrCodeList(bean);
			map.put("terrCodeList", terrCodeList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getLrNumList-frm-DivId")
	public Map<String, Object> getLrNumList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> lrNumList = null;
		try {
			lrNumList = reportService.getLrNumList(bean);
			map.put("lrNumList", lrNumList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getLrDateList-frm-DivId")
	public Map<String, Object> getLrDateList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> lrDateList = null;
		try {
			lrDateList = reportService.getLrDateList(bean);
			map.put("lrDateList", lrDateList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getChallanDateList-frm-DivId")
	public Map<String, Object> getChallanDateList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> challanDateList = null;
		try {
			challanDateList = reportService.getChallanDateList(bean);
			map.put("challanDateList", challanDateList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getChallanNumList-frm-DivId")
	public Map<String, Object> getChallanNumList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> challanNumList = null;
		try {
			challanNumList = reportService.getChallanNumList(bean);
			map.put("challanNumList", challanNumList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-dispatch-register-report1-revised")
	public Map<String, Object> getDispatchRegisterRevisedReport1(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<dispatch_register_report1_with_parameters> list = null;
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String filename = null;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			list = reportService.getDispatchRegisterRevisedReport1(bean);
			if (list != null && !list.isEmpty()) {
				filename = dispatchRegisterReport1.generateDispatchRegisterRevisedReport1Excel(list, bean, companyname,
						empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-dispatch-register-Excel-report1-revised")
	public Map<String, Object> getDispatchRegisterRevisedReportExcelData1(@RequestBody ReportBean bean,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<dispatch_register_report1_with_parameters> list = null;
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String filename = null;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			list = reportService.getDispatchRegisterRevisedReport1(bean);
			if (list != null && !list.isEmpty()) {
				filename = dispatchRegisterReport1.generateDispatchRegisterRevisedReport1Excel(list, bean, companyname,
						empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-dispatch-register-report2-revised-doctorSupply")
	public Map<String, Object> getDispatchRegisterRevisedReport2DoctorSupply(@RequestBody ReportBean bean,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<dispatch_register_report2_with_parameters> list = null;
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String filename = null;
		try {

			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			list = reportService.getDispatchRegisterRevisedReport2DoctorSupply(bean);
			if (list != null && !list.isEmpty()) {
				filename = dispatchRegisterReport2.generateDispatchRegisterRevisedReport2ExcelDoctorSupply(list, bean,
						companyname, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-Rate-List")
	public Map<String, Object> getRateList(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("list", parameterService.getRateParameters());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	@PostMapping("/get-dispatch-detail-gst-print")
	public Map<String, Object> getDispatchDetailGstPrint(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DispatchDetailGstReport> list = null;
		String userId = bean.getEmp_id(); // get it from session //changed
		bean.setUserId(userId);
		bean.setDeptloc("0");
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			list = reportService.callDisptachDetailGstReport(bean);
			if (list != null && !list.isEmpty()) {
				String filename = this.dispatchSummaryDetailReportService.generateDispatchDetailGstReport(list, bean,
						companyname, empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/Generate_dispatch_details_disp_reports")
	public Map<String, Object> getDispDetailsdispReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@PostMapping("/blk-challans-generated-log")
	public Map<String, Object> getBlkChallasnsGeneratedLog(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		System.out.println("/blk-challans-generated-log is calling.....");
		Map<String, Object> map = new HashMap<>();
		List<BlkChallansGeneratedLog> list = null;
//		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
//			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
			list = reportService.getBlkChallasnsGeneratedLogReport(bean.getFin_year(), bean.getAlloc_period(),
					Long.valueOf(bean.getSelectedReqid()));

			if (list != null && !list.isEmpty()) {
				String filename = this.dispatchSummaryDetailReportService.getBlkChallasnsGeneratedLogFilename(list);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@PostMapping("/get-bulk-upload-log-detail-list")
	public Map<String, Object> getBulkUploadLogDetailList(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		List<Blk_Challans_Generated_Log> bulkUploadLogList;
		Map<String, Object> map = new HashMap<>();
		try {

			bulkUploadLogList = doctorBulkAllocUploadService.getBulkUploadLogDetailList(bean.getFin_year(),
					bean.getAlloc_period());
			if (bulkUploadLogList != null && !bulkUploadLogList.isEmpty()) {
				String filename = doctorBulkAllocUploadService.getBulkUploadLogDetailExcel(bulkUploadLogList);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			System.out.println("bulkUploadLogList" + bulkUploadLogList.size());
			map.put("list", bulkUploadLogList);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return map;
	}

	@GetMapping("/get-final-approval-log")
	public Map<String, Object> getFinalApprovalLog() {
		Map<String, Object> map = new HashMap<>();
		List<FinalApprovalLogBean> list = null;
		String filename = null;
		String filepathPdf = null;
		try {
			list = reportService.getFinalApprovalLog();
			System.out.println("list : " + list.size());
			map.put("finalApprovalLogList", list);

			if (list.size() != 0) {
				filename = allocation_report1_service.generate_final_approval_log_report(list);
				filepathPdf = finalApprovalLogPdf.generateFinalLogApprovalPdf(list);
			}
			map.put("filepathPdf", filepathPdf);
			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	
	
	
	@PostMapping("/get-article-inventory-report")
	public Map<String, Object> getArticleInventoryReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String filename = null;
		List<Article_Stock_Statement> articleStkStmtList = null;
		List<Article_Stock_Statement_Itemwise> articleStockStatementItemwiseList = null;

		List<SmpItem> lst = null;
		try {

			lst = reportService.getSmpItemList(Long.valueOf(bean.getLocId()));

			articleStkStmtList = reportService.getArticleInventoryReport(bean);
			articleStockStatementItemwiseList = reportService.getArticleInventoryItemwiseReport(bean);
			
			
			System.out.println("articleStkStmtList ::: " + articleStkStmtList.size());

			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			FinYear finyear = this.financialYearService.getCurrentFinancialYear(companyCode);

			if (articleStkStmtList != null && !articleStkStmtList.isEmpty()) {
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);
				filename = articleStockStmtReportService.generateArticleStockStmtReport(lst, articleStkStmtList,articleStockStatementItemwiseList,
						companyname, companyCode, empId, finyear.getFin_start_date());
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", articleStkStmtList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	
	
	
	

	@GetMapping("/article-scheme-summary-report")
//	public Map<String, Object> schemeSummaryReport(@RequestParam String periodcode, @RequestParam String fromDate,
//			@RequestParam String toDate, HttpSession session) 
	public Map<String, Object> schemeSummaryReport(@RequestParam List<String> schemeIds, @RequestParam String finyear,@RequestParam String finyear_flag,  HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Scheme_Summary> listSummary = new ArrayList<>();

		String filename = "";
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

		try {
			listSummary = reportService.getSchemeSummary(schemeIds,finyear,finyear_flag);
			
			if (listSummary.size() != 0) {
				filename = schemeSummaryService.generateSchemeSummaryReport(listSummary, companyname);
			}

			Long oldtrd_sch_slno = 0l;
			Long newtrd_sch_slno = 0l;

			for (Scheme_Summary obj : listSummary) {

				newtrd_sch_slno = obj.getTrd_sch_slno();

				if (oldtrd_sch_slno.compareTo(newtrd_sch_slno) == 0) {
					obj.setTrd_scheme_name("");
					obj.setValid_from_dt("");
					obj.setValid_to_dt("");
					obj.setArticle_name("");
					obj.setSale_prod_name("");
					obj.setSale_qty(0L);
					obj.setSale_value(BigDecimal.ZERO);
					obj.setArt_dsp_rate(BigDecimal.ZERO);
					obj.setArticle_req_qty(0L);
					obj.setArticle_req_item_value(BigDecimal.ZERO);
					obj.setArt_dsp_qty(0L);
					obj.setArt_dsp_value(BigDecimal.ZERO);
					obj.setReq_pending_qty(0L);
					obj.setClosing_stock(BigDecimal.ZERO);
					obj.setAvailable_stock(0L);
				}

				obj.setValid_from_dt(obj.getValid_from_dt() != null && !obj.getValid_from_dt().isEmpty()
						? MedicoResources.convert_DD_MM_YYYY2(obj.getValid_from_dt())
						: "");
				obj.setValid_to_dt(obj.getValid_to_dt() != null && !obj.getValid_to_dt().isEmpty()
						? MedicoResources.convert_DD_MM_YYYY2(obj.getValid_to_dt())
						: "");

				oldtrd_sch_slno = newtrd_sch_slno;
			}
			System.out.println(listSummary);

			map.put("schemeSumamryList", listSummary);

			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@GetMapping("/article-scheme-summary-report-for-drill-down")
//	public Map<String, Object> schemeSummaryReportforDrillDown(@RequestParam String periodcode,
//			@RequestParam String fromDate, @RequestParam String toDate, HttpSession session)
	public Map<String, Object> schemeSummaryReportforDrillDown(@RequestParam List<String> schemeIds, @RequestParam String finyear,@RequestParam String finyear_flag, HttpSession session)
	{
		Map<String, Object> map = new HashMap<>();
		List<Scheme_Summary> listSummary = new ArrayList<>();

		String filename = "";
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

		try {

			listSummary = reportService.getSchemeSummary_drill_down_list(schemeIds,finyear,finyear_flag);
			
			
//			map.put("schemeSumamryList", listSummary);

//			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//			Date fromDateParsed = inputFormat.parse(fromDate);
//			Date toDateParsed = inputFormat.parse(toDate);
//
//			String fromDateFormatted = outputFormat.format(fromDateParsed);
//			String toDateFormatted = outputFormat.format(toDateParsed);

			if(listSummary != null) {
			if (listSummary.size() != 0) {
//				filename = schemeSummaryService.generateSchemeSummaryReport(listSummary, fromDateFormatted,
//						toDateFormatted.toString(), companyname);
				filename = schemeSummaryService.generateSchemeSummaryReport(listSummary, companyname);
			}

			System.out.println(listSummary);
		
			map.put("schemeSumamryList", listSummary);

			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}else {
				map.put("msg", "No Data Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@PostMapping("/article-exception-report")
	public Map<String, Object> articleExceptionReport(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<ArticleSchemeExceptionReport> list = null;

		String filename = "";
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

		try {
			System.out.println("SubCompany : " + bean.getSubCompany());
			System.out.println("From : " + bean.getFromDate());
			System.out.println("To : " + bean.getToDate());

			list = reportService.articleExceptionReport(bean);
			System.out.println("list::"+(list!=null?list.size():null));
			if (list != null && list.size() > 0) {

				if (bean.getOnlyExcep() != null && bean.getOnlyExcep().equalsIgnoreCase("Y")) {
					list = list.stream().parallel()
							.filter(elem -> elem.getArticle_qty_diff().compareTo(BigDecimal.ZERO) != 0
									|| elem.getErr_bill_qty().compareTo(0L) != 0)
							.collect(Collectors.toList());

				}

				if (bean.getIs_correction() != null && bean.getIs_correction().equalsIgnoreCase("C")) {
					list = list.stream().parallel()
							.filter(elem -> elem.getCorr_ind() != null && elem.getCorr_ind().equalsIgnoreCase("Y"))
							.collect(Collectors.toList());
				}

				for (int i = 0; i < list.size(); i++) {
					boolean is_cor_row = (list.get(i).getCorr_ind() != null
							&& list.get(i).getCorr_ind().equalsIgnoreCase("Y")
							&& list.get(i).getCorr_article_qty().compareTo(BigDecimal.ZERO) > 0) ? true : false;
					boolean is_bill_qty_err_only = (list.get(i).getArticle_qty_diff().compareTo(BigDecimal.ZERO) == 0
							&& list.get(i).getErr_bill_qty().compareTo(0L) != 0) ? true : false;
//					if (is_cor_row) {
//						list.set(i, new ArticleSchemeExceptionReport(list.get(i).getDsp_challan_no(),
//								list.get(i).getDsp_dt(), "Shortage Corrected", list.get(i).getCorr_ind(),
//								list.get(i).getCorr_article_qty(), list.get(i).getCorr_article_qty().longValue()));
//					}
					if (is_bill_qty_err_only) {
						System.out.println("bill qty error only:::" + is_bill_qty_err_only);
						ArticleSchemeExceptionReport art_scm_exp = list.get(i);
						art_scm_exp.setDiscrip_msg("Billed Qty Differs , No diff in Article Qty");
						list.set(i, art_scm_exp);
					}
				}

				filename = articleSchemeExceptionReportService.generateArticleExceptionSchemeReport(list, companyname,
						MedicoResources.convert_DDMMYYYY(bean.getFromDate()),
						MedicoResources.convert_DDMMYYYY(bean.getToDate()), bean.getIs_correction());

				map.put("list", list);
			}

			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}
	
	@PostMapping("/article-scheme-summary-report-cfa")
	public Map<String, Object> articleSchemeSummaryReportCfa(@RequestBody ReportBean bean, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<ArticleSchemeSummaryReportCfa> list = null;
		System.out.println("bean@# : "+bean.getTrd_sch_slno());
		String filename = "";
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			
			
			System.err.println(bean.getSales_prod_details().toString());

			
			list = reportService.articleSchemeSummaryReportCfa(bean);
			if (list.size() != 0) {
				filename = schemeSummaryOfCfaService.generateSchemeSummaryCfaLocationWise(list,companyname,bean);
			}
			map.put("list", list);
			
			map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	@PostMapping("/get-dispatch-detail-report-data-revised-mdmno")
	public Map<String, Object> getDispatchDetDataRevisedMdmNo(@RequestBody ReportBean bean,HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DispatchDetailReportRevisedMdmNo> list = null;
//		String userId=bean.getEmp_id();//get it from session //changed
		String deptloc = "0";// get it from session
		
//		bean.setUserId(userId);
		bean.setDeptloc(deptloc);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
			list = reportService.callDisptachDetReportRevisedMdmNo(bean);
			if (list != null && !list.isEmpty()) {
				String filename = this.dispatchSummaryDetailReportService.generateDispatchDetReportRevisedMdmNo(list,bean,companyname,empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
				map.put("list", list.size());
			}
			else {
			map.put("list", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map; 
	}

	
	// abhishek 
	
	
	@GetMapping("/get-ter-hierarchy-report")
	public Map<String, Object> download_ter_hyrarchy_Report(@RequestParam String div_id,HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Ter_mst_hierarchy_report_bean> list = null;
		try {
			
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			
			list = reportService.download_ter_hyrarchy_Report(div_id);
			if (list != null && !list.isEmpty()) {
				String filename = this.dispatchSummaryDetailReportService.genarate_ter_hyrarchy_Report(list,companyname);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
				map.put("list", list.size());
			}
			else {
			map.put("list", 0);
			}
			
			map.put("list", list);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/downloadFstaffCsv")
	public Map<String, Object> getDownloadFstaffCsv(@RequestParam String divId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<FieldStaffCsvDownload> list = null;
		try {
			list = reportService.getFstaffDownloadCsvData(divId);
			if (list != null && !list.isEmpty()) {
				String fileName = this.fieldMasterDownloadService.generateFstaffCsv(list);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + fileName);
				map.put("list", list.size());

			}
			
		}catch(Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			e.printStackTrace();

		}
		return map;

	}
	
	@GetMapping("/get-all-csv-file-list")
	public Map<String, Object> getAllCsvFileList() {
		Map<String, Object> map = new HashMap<>();
		List<String> csvFileList = null;
		try {
			csvFileList = this.reportService.getCsvFileList();
			map.put("csvFileList", csvFileList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/getUpdatedFstaffMobileNo")
	public Map<String, Object> getUpdatedFstaffMobileNo(@RequestParam String csvFileName) {
		Map<String, Object> map = new HashMap<>();
		List<Fieldstaff_After_mobile_No_Update_CheckList> list = new ArrayList<>();
		String filename =null;
		try {
			list=this.reportService.getUpdatedFstaffMobileNo(csvFileName);
			if(list != null) {
				filename = this.fieldMasterDownloadService.generateUpdatedFstaffMobileNoReport(list);
				System.out.println(":::::::filenameForReport:::::::::::"+filename);
			}
			map.put("list", list);
			map.put("filename", filename);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
	@PostMapping("/getFieldstaffMobileOdsdCount")
	public Map<String, Object> getFieldstaffMobileOdsdCount(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<FieldStaffBean> list = new ArrayList<>();
		try {
			System.out.println(bean.getStartDate());  

			list=this.reportService.getFieldstaffMobileOdsdCount(bean.getStartDate());
			map.put("AllCount", list.get(0).getAllCount());
			map.put("MobileEmptyCount", list.get(0).getMobileEmptyCount());
			map.put("MobileNonEmptyCount", list.get(0).getMobileNonEmptyCount());
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}
	
	
	
}