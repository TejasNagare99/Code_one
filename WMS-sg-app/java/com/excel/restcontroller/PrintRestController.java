package com.excel.restcontroller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.PrintBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.Company;
import com.excel.model.Dispatch_Header;
import com.excel.model.DivMaster;
import com.excel.model.HqMasterAuditTrailModel;
import com.excel.model.Location;
import com.excel.model.NOV_BillOfSupply_Challan;
import com.excel.model.Period;
import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.model.PrePrintedDetailChallan_withgstPG;
import com.excel.model.PrePrintedDetailChallan_withgst_pal;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SWV_Header;
import com.excel.model.SWV_Header_arc;
import com.excel.model.StockTransferNotePrint;
import com.excel.model.Stock_Transfer_Header;
import com.excel.model.Supplier;
import com.excel.model.ViewGrnGSTVoucherPrinting;
import com.excel.model.ViewGrnLabelPrinting_java;
import com.excel.model.ViewIAAVoucherPrinting;
import com.excel.model.ViewPrePrintedDetailChallan;
import com.excel.model.ViewPrePrintedSummaryChallan_GST;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoice;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoiceStockist;
import com.excel.model.ViewStockWithdrawalVoucherPrint;
import com.excel.model.trd_scheme_mst_hdr;
import com.excel.service.Article_Scheme_master_Service;
import com.excel.service.DelChallanPrintPDFService;
import com.excel.service.DivisionMasterService;
import com.excel.service.GRNService;
import com.excel.service.GrnVoucherPrintGstService;
import com.excel.service.HqMasteraudit_trail_PrintPdf_Service;
import com.excel.service.IAAPrintPDFService;
import com.excel.service.LocationService;
import com.excel.service.NOV_BillOfSupply_ChallanPrinting;
import com.excel.service.PackSlipPrintPDF;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.PrePrintedDetailChallanPalsonService;
import com.excel.service.PrePrintedSummaryChallanEInvoiceService;
import com.excel.service.PrePrintedSummaryChallanEInvoiceService_forPfizer;
import com.excel.service.PrePrintedSummaryChallanService;
import com.excel.service.PreprintedDetailedChallanService;
import com.excel.service.PrintService;
import com.excel.service.ReportService;
import com.excel.service.StockTransferNotePrint_Service;
import com.excel.service.StockWithdrawalPdfService;
import com.excel.service.SupplierService;
import com.excel.service.Unichem_Challan_Printing_GST_Service;
import com.excel.service.UserMasterService;
import com.excel.service.generateLabelPdfService;
import com.excel.utility.Challan_Print;
import com.excel.utility.Challan_Summary_Print;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/rest")
public class PrintRestController implements MedicoConstants {
	@Autowired
	private JwtProvider tokenProvider;
	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	LocationService locationService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	GRNService grnService;
	@Autowired
	private EntityManagerFactory emf;
	@Autowired
	PrintService printService;
	@Autowired
	PrePrintedSummaryChallanEInvoiceService_forPfizer prePrintedSummaryChallanEInvoiceService_forPfizer;
	@Autowired
	PrePrintedSummaryChallanEInvoiceService prePrintedSummaryChallanEInvoiceService;
	@Autowired
	GrnVoucherPrintGstService grnVoucherPrintGstService;
	@Autowired
	PreprintedDetailedChallanService preprinteddetailedchallanservice;
	@Autowired
	Unichem_Challan_Printing_GST_Service unichem_challan_printing_gst_service;
	@Autowired
	DivisionMasterService divisionMasterService;
	@Autowired
	PrePrintedSummaryChallanService preprintedsummarychallanservice;
	@Autowired
	generateLabelPdfService generatelabelpdfservice;
	@Autowired
	DelChallanPrintPDFService delchallanprintpdfservice;
	@Autowired
	PackSlipPrintPDF packslipprintpdf;
	@Autowired
	IAAPrintPDFService iaaprintpdf;
	@Autowired
	HqMasteraudit_trail_PrintPdf_Service hqmasteraudit_trail_printpdf_service;
	@Autowired
	StockTransferNotePrint_Service stocktransfernoteprint_service;
	@Autowired
	StockWithdrawalPdfService stockwithdrawalpdfservice;
	@Autowired
	PeriodMasterService periodmasterservice;
	@Autowired
	PrePrintedDetailChallanPalsonService prePrintedDetailChallanPalsonService;
	@Autowired
	ReportService reportservice;
	@Autowired
	NOV_BillOfSupply_ChallanPrinting nov_billofsupply_challanprinting;
	@Autowired
	ParameterService parameterService;
	@Autowired
	private Article_Scheme_master_Service art_sch_mst_service;

	@PostMapping("/get-Declaration-Print")
	public Map<String, Object> getDeclarationPrint(@RequestBody PrintBean pb, HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ViewPrePrintedDetailChallan> list = null;
		String filename = "";

		String prodtype = " ";

		String finId = "2022";

		try {

			list = this.printService.getDeclarationForm(Integer.valueOf(pb.getTeamDivision()),
					Integer.valueOf(pb.getLocation()), pb.getFromChallan(), pb.getToChallan(), pb.getDispatchType(),
					prodtype, pb.getPageIndQuery(), pb.getFinyear(), pb.getFinyearflag());

			System.out.println("list " + list.size());

			if (list.isEmpty() != true && list != null) {
				filename = this.preprinteddetailedchallanservice.PrePrintedDetailedChallanDeclaration(
						Integer.valueOf(pb.getTeamDivision()), Integer.valueOf(pb.getLocation()), pb.getFromChallan(),
						pb.getToChallan(), pb.getDispatchType(), prodtype, list, session);
			}

			map.put("message", filename);
			if (filename.length() > 0) {
				map.put("isData", true);
			} else {
				map.put("isData", false);
			}
		} catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --; //
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	@GetMapping("/getLocationForGrnVoucherPrint")
	public Map<String, Object> getLocationForGrnVoucherPrint(HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		try {
			locList = locationService.getAllLocations();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("locList", locList);
		return map;
	}

	@GetMapping("/getVendorForGrnVoucherPrint")
	public Map<String, Object> getVendorForGrnVoucherPrint(@RequestParam String subComp, HttpSession session)
			throws Exception {
		System.out.println(subComp);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> supList = supplierService.getAllActiveSupplier(subComp);
		map.put("supList", supList);
		return map;
	}

	@GetMapping("/getGrnNoList")
	public Object getGrnNoList(@RequestParam String StartDate, @RequestParam String EndDate, @RequestParam String SupId,
			@RequestParam String LocId, @RequestParam String finyr, @RequestParam String finyrflg)
			throws ParseException {
		Map<String, Object> map = new HashMap<>();
		List<Object> list = new ArrayList<Object>();
		System.out.println("Selected supplier id is : " + SupId);

//		SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH);
//		Date date1 = sdf3.parse(StartDate);
//		System.out.println("ist to date is: " + date1);
//		String sdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
//
//		Date date2 = sdf3.parse(EndDate);
//		String edate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);

//		System.out.println("String dates are :" + sdate + ".." + edate);

		list = this.grnService.getGrnDetailForGrnVoucherPrint(StartDate, EndDate, SupId, LocId, finyr, finyrflg);
		System.out.println("Grn no list is:" + list);
		map.put("list", list);
		return map;
	}

	@GetMapping("/getDataForGRNLabelPrint")
	public Map<String, Object> getDataForGRNLabelPrint() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		try {
			locList = locationService.getAllLocations();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("locList", locList);
		return map;
	}

	@GetMapping("/getVendorForGrnLabelsPrint")
	public Map<String, Object> getVendorForGrnLabelsPrint(@RequestParam String subComp) throws Exception {
		System.out.println(subComp);
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> supList = null;
		try {
			supList = supplierService.getAllActiveSupplier(subComp);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("supList", supList);
		return map;
	}

	@GetMapping("/getDetailForGstChallanPrint")
	public Map<String, Object> getLocationForGstChallanPrint(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		List<DivMaster> divList = null;
		List<Period> finYearList = null;
		List<trd_scheme_mst_hdr> schemeList = null;
		try {

			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			locList = locationService.getAllLocations();
			// divList=divisionMasterService.getDivAccessedByUser(empId);
			divList = divisionMasterService.getAllDivAccessedByUser(empId);
			finYearList = printService.getAllFinYearForGSTChallanPrint();

			schemeList = this.art_sch_mst_service.get_all_scheme();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("divList", divList);
		map.put("locList", locList);
		map.put("finYearList", finYearList);
		map.put("schemeList", schemeList);
		return map;
	}

	@GetMapping("/getAllLocationsBySubCompany")
	public Map<String, Object> getAllLocationsBySubCompany(@RequestParam Long subCompanyId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		try {
			locList = locationService.getAllLocationsBySubCompany(subCompanyId);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("locList", locList);
		return map;
	}

	@GetMapping("/getPeriodListByFinYear")
	public Map<String, Object> getPeriodListByFinYear(@RequestParam String finYear) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Period> periodList = null;
		try {
			periodList = printService.getListOfPeriodByFinYear(finYear);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("periodList", periodList);
		return map;
	}

	@GetMapping("/getChallanListForGSTChallanPrint")
	public Map<String, Object> getChallanListForGSTChallanPrint(@RequestParam String divId,
			@RequestParam String periodCode, String finYear, @RequestParam String location,
			@RequestParam String finyrflg) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> challanList = null;
		try {

			challanList = printService.getChallanListForGSTChallanPrint(divId, periodCode, finYear, location, finyrflg);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("challanList", challanList);
		return map;
	}

	@GetMapping("/getChallanListForPackSlipPrint")
	public Map<String, Object> getChallanListForPackSlipPrint(@RequestParam String ro, @RequestParam String divId,
			@RequestParam String periodCode, @RequestParam String finYear, @RequestParam String location,
			@RequestParam String finyrflg) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> challanList = null;
		try {
			challanList = printService.getChallanListForPickPackSlipPrint(ro, divId, periodCode, finYear, location,
					finyrflg);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("challanList", challanList);
		return map;
	}

	@GetMapping("/getIAAListForDocumentPrint")
	public Map<String, Object> getIAAListForDocumentPrint(@RequestParam String LocId, @RequestParam String StartDate,
			@RequestParam String EndDate, @RequestParam String finyrflg, @RequestParam String finyr) {
		System.out.println("Dates in repository :" + StartDate + "..." + EndDate);
		Map<String, Object> map = new HashMap<>();
		List<Object> iaaNoList = null;

		try {
			iaaNoList = printService.getIAANoListForDocumentPrint(LocId, StartDate, EndDate, finyr, finyrflg);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("iaaNoList", iaaNoList);
		return map;
	}

	@GetMapping("/getChallanListForGSTSummaryChallanPrint")
	public Map<String, Object> getChallanListForGSTSummaryChallanPrint(@RequestParam String reportOption,
			@RequestParam String divId, @RequestParam String periodCode, String finYear, @RequestParam String location,
			@RequestParam String finyearflg) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> challanList = null;
		try {
			if (finyearflg.equals("CURRENT")) {
				challanList = printService.getChallanListForGSTSummaryChallanPrint(reportOption, divId, periodCode,
						finYear, location);
			} else {
				challanList = printService.getChallanListForGSTSummaryChallanPrintPrevious(reportOption, divId,
						periodCode, finYear, location);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("challanList", challanList);
		return map;
	}

	@GetMapping("/generatePDF")
	public void generatePDF() throws FileNotFoundException, DocumentException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream("D:/vruti/newPDF.pdf"));
		document.open();
		Paragraph title1 = new Paragraph("Hello...............");
		document.addCreator("Vruti");
		document.addCreationDate();
		document.addAuthor("Excel");
		document.add(title1);
		document.close();
	}

	@PostMapping("/generate-grn-voucher-gst-print")
	public Map<String, Object> getGrnVoucherPrintGSTPrint(HttpSession session, @RequestBody PrintBean bean,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ViewGrnGSTVoucherPrinting> list = null;
		try {
			SG_d_parameters_details pur_rate_param = this.parameterService
					.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN");
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			list = this.printService.getGrnPrintVoucherGstData(bean);
			if (list != null && !list.isEmpty()) {
				String filename = this.grnVoucherPrintGstService.getGrnVocuherGstPrint(
						Integer.parseInt(bean.getLocationId()), Integer.parseInt(bean.getVendorId()), bean.getFromGRN(),
						bean.getToGRN(), list, Boolean.valueOf(bean.getBinChecked()), companyCode, companyName, session,
						request);
				map.put("filename", SPRING_RESOURCE_HANDLER_PDF_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

//	@GetMapping("/generate-pre-printed-detail-challan-gst-print")
//	public Map<String,Object> getPrePrintDetaileChallanGstPrint(HttpSession session)throws Exception{
//		Map<String,Object> map=new HashMap<String, Object>();
//		List<PrePrintedDetailChallan_withgst> list=null;
//		
//		try {
//			list = this.printService.getPreprintedDetailedChallanData();
//			System.out.println("List Size ::"+list.size());
//			if(list != null && !list.isEmpty()) {
//				String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//				System.out.println("CompanyCOde"+companyCode);
//				//String filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(new Integer(48),new Integer(9), "PIP  1916290", "PIP  1916290", "dtp", "", "tcfa", list, "D", "D",companyCode);
//				String filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(division, loc, frm_challan, to_challan, dispatchType, prodtype, printtype, challans, show_amount, footer_signature_ind, companyCode, companyName);//getPreprintedDetailedChallanPrint(48, 9, "PIP  1916290", "PIP  1916290", "dtp", "", "P", list, "Y", "Y", companyCode);
//				map.put("filename", SPRING_RESOURCE_HANDLER_PDF_FILE_PATH_PREFIX + "/" + filename);	
//			}
//			map.put("list", list);
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}

	@PostMapping("/getPrePrintDetailChallanGstPrint")
	public Map<String, Object> getPrePrintDetailChallanGstPrint(HttpSession session, @RequestBody PrintBean bean,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String filename = null;
		String footer_signature_ind = "";
		try {
			footer_signature_ind = this.printService.getSpValueForFooterSignature().getSp_value();
			System.out.println("Footer Signature:" + footer_signature_ind);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String locId = bean.getLocation();
			System.out.println("location id:::" + bean.getLocation());
			List<Location> location = locationService.getLocationNarrationBylocId(locId);
			// String show_amount = "Y";
//		String dotMatOrPdf = "P"; 
//		String message="";
//		String printtype = "tcfa";
//		String division = "48";
//		String loc = "9";
//		String frm_challan = "PIP  1904420";
//		String to_challan = "PIP  1904420";
//		String prodtype =  "";
//		String rep_type="D";
//		String dispatchType = "dtp";

			String show_amount = bean.getShowAmount();
			System.out.println("laser print :" + bean.getDotMatrixLaser());
			String dotMatOrPdf = bean.getDotMatrixLaser();
			String message = "";
			String printtype = bean.getReportOption();
			// String printtype = bean.getReportOption();
			String division = bean.getTeamDivision();
			String loc = bean.getLocation();
			String frm_challan = bean.getFromChallan();
			String to_challan = bean.getToChallan();
			String prodtype = "";
			String rep_type = bean.getReportOption();
			// String rep_type="D";
			String dispatchType = bean.getDispatchType();
			bean.setCompanyCode(companyCode);

			System.out.println("CompanyName:" + companyName);
			System.out.println("CompanyCode:" + companyCode);
			if (companyCode.equalsIgnoreCase(NIL) || companyCode.equalsIgnoreCase(NHP))
				show_amount = "Y";
			System.out.println("Print Type " + printtype);
			if (printtype.equalsIgnoreCase("tcfa")) {

				if (dotMatOrPdf.equalsIgnoreCase("D")) {
					List<ViewPrePrintedDetailChallan> list1 = null;
					list1 = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan, to_challan,
							prodtype, rep_type, bean);
					message = new Challan_Summary_Print().callMain(Integer.parseInt(division), Integer.parseInt(loc),
							frm_challan, to_challan, prodtype, rep_type, list1);
				} else {

					try {
						List<ViewPrePrintedSummaryChallan_GST> list1 = null;
						List<PrePrintedDetailChallan_withgst> list = null;
						List<PrePrintedDetailChallan_withgstPG> Pglist = null;
						List<ViewPrePrintedDetailChallan> list_nogst = null;
						if (printtype.equalsIgnoreCase("tcfa")) {// Summary
							list1 = this.printService.PrePrintedSummaryChallan_GSTdata(division, loc, frm_challan,
									to_challan, prodtype, rep_type, bean);
							System.out.println("List: " + list1.size());
						} else {// Detail

							if (companyCode.trim().equals("SER") || companyCode.trim().equals(VET)
									|| companyCode.trim().equals("MDL") || companyCode.trim().equals("SNK")) {

								list_nogst = this.printService.getViewPrePrintedDetailChallanData(division, loc,
										frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
							} else if (companyCode.trim().equals(PG)) {
								Pglist = this.printService.getPreprintedDetailedChallanDataPG(division, loc,
										frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
							} else {
								list = this.printService.getPreprintedDetailedChallanData(division, loc, frm_challan,
										to_challan, dispatchType, prodtype, rep_type, bean);
							}
						}
						// same code and format for summary and detail printing.
						if (list != null && list.size() > 0 || list1 != null && list1.size() > 0) {
							System.out.println("Comp code received " + companyCode);
							map.put("isData", true);
							if (companyCode.equalsIgnoreCase("UCM")) {
								map.put("message",
										this.unichem_challan_printing_gst_service.getPdf(Integer.parseInt(division),
												Long.parseLong(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode,
												companyName, session, request));
							} else if (companyCode.trim().equalsIgnoreCase("PFZ")) {
								filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(
										Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
										dispatchType, prodtype, printtype, list, show_amount, footer_signature_ind,
										companyCode, companyName, session, request);
								map.put("message", filename);
							}
//						else if(companyCode.trim().equalsIgnoreCase("VET")){
//								//To-DO
//							}
							else {

								if (companyCode.trim().equals("SER") || companyCode.trim().equals(VET) ||
										companyCode.trim().equals("MDL") || companyCode.trim().equals("SNK") ) {
									
									filename = this.preprinteddetailedchallanservice
											.getPreprintedDetailedChallanPrintNoGst(Integer.parseInt(division),
													Integer.parseInt(loc), frm_challan, to_challan, dispatchType,
													prodtype, printtype, list_nogst, show_amount, footer_signature_ind,
													companyCode, companyName, session,
													location.get(0).getLoc_narrationfooter(), request);
									
									
									
									map.put("message", filename);
								} else {
									filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(
											Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
											dispatchType, prodtype, printtype, list, show_amount, footer_signature_ind,
											companyCode, companyName, session, request);
									map.put("message", filename);
								}
							}
						} else if (Pglist != null && Pglist.size() > 0) {
							filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrintPG(
									Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
									dispatchType, prodtype, printtype, Pglist, show_amount, footer_signature_ind,
									companyCode, companyName, session, request);
							System.out.println("FileName2 :: " + filename);
							map.put("message", filename);
						} else {
							map.put("isData", false);
							map.put("message", "");
						}
						map.put("isError", false);
					} catch (Exception e) {
						try {
							map.put("isError", true);
						} catch (Exception e1) {
							// ignore
						}
						e.printStackTrace();
						System.out.println("Error Occurred :" + e.getMessage());// uncomment
																				// asneeded --
																				// System.out.println("Error Occurred :"
																				// +e.getMessage());// uncomment
																				// asneeded --;
					}
				}

			} else if (printtype.equalsIgnoreCase("dtp")) {
				List<ViewPrePrintedDetailChallan> list_nogst = null;
				if (dotMatOrPdf.equalsIgnoreCase("D")) {
					List<ViewPrePrintedDetailChallan> list = this.printService.getViewPrePrintedDetailChallanData(
							division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
					message = new Challan_Print().callMain(Integer.parseInt(division), Integer.parseInt(loc),
							frm_challan, to_challan, dispatchType, prodtype, rep_type, list, companyName);
				} else {
					try {
						List<PrePrintedDetailChallan_withgstPG> Pglist = null;
						List<ViewPrePrintedDetailChallan> list1 = null;
						List<PrePrintedDetailChallan_withgst> list = null;
						List<PrePrintedDetailChallan_withgst_pal> list2 = null;
						if (printtype.equalsIgnoreCase("tcfa")) {// Summary
							list1 = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan,
									to_challan, prodtype, rep_type, bean);
						} else {// Detail
							if (companyCode.trim().equals("SER") || companyCode.trim().equals(VET)
									|| companyCode.trim().equals("MDL") || companyCode.trim().equals("SNK")) {
								list_nogst = this.printService.getViewPrePrintedDetailChallanData(division, loc,
										frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
							} else if (companyCode.trim().equals("PAL")) {
								System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!! ");
								list2 = this.printService.getPreprintedDetailedChallanDatapal(division, loc,
										frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
							} else if (companyCode.trim().equals(PG)) {
								Pglist = this.printService.getPreprintedDetailedChallanDataPG(division, loc,
										frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
							} else {
								list = this.printService.getPreprintedDetailedChallanData(division, loc, frm_challan,
										to_challan, dispatchType, prodtype, rep_type, bean);
							}
						}
						if (list != null && list.size() > 0 || list_nogst != null && list_nogst.size() > 0
								|| list2 != null && list2.size() > 0) {
							map.put("isData", true);
							if (companyCode.equalsIgnoreCase("UCM")) {
								map.put("message",
										this.unichem_challan_printing_gst_service.getPdf(Integer.parseInt(division),
												Long.parseLong(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode,
												companyName, session, request));
							} else if (companyCode.trim().equals("SER") || companyCode.trim().equals(VET)) {
								
								
								filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrintNoGst(
										Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
										dispatchType, prodtype, printtype, list_nogst, show_amount,
										footer_signature_ind, companyCode, companyName, session,
										location.get(0).getLoc_narrationfooter(), request);
								map.put("message", filename);
								
								
								
							}else if( companyCode.trim().equals("MDL") || companyCode.trim().equals("SNK")) {
								filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrintNoGst_MDL(
										Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
										dispatchType, prodtype, printtype, list_nogst, show_amount,
										footer_signature_ind, companyCode, companyName, session,
										location.get(0).getLoc_narrationfooter(), request);
								map.put("message", filename);
								
							} else if (companyCode.trim().equalsIgnoreCase("PAL")) {
								filename = this.prePrintedDetailChallanPalsonService
										.getPreprintedDetailedChallanPrintPalson(Integer.parseInt(division),
												Integer.parseInt(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list2, show_amount, footer_signature_ind, companyCode,
												companyName, session);
								map.put("message", filename);
							} else {
								filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(
										Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
										dispatchType, prodtype, printtype, list, show_amount, footer_signature_ind,
										companyCode.trim(), companyName.trim(), session, request);
								map.put("message", filename);
							}
						} else if (Pglist != null && Pglist.size() > 0) {
							map.put("isData", true);
							filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrintPG(
									Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
									dispatchType, prodtype, printtype, Pglist, show_amount, footer_signature_ind,
									companyCode.trim(), companyName, session, request);
							System.out.println("FileName1 :: " + filename);
							map.put("message", filename);
						} else {
							map.put("isData", false);
							map.put("message", "");
						}
						map.put("isError", false);
					} catch (Exception e) {
						e.printStackTrace();
						try {
							map.put("isError", true);
						} catch (Exception e1) {
							
							
							// ignore
						}
						e.printStackTrace();
						System.out.println("Error Occurred :" + e.getMessage());// uncomment
																				// asneeded --
																				// System.out.println("Error Occurred :"
																				// +e.getMessage());// uncomment
																				// asneeded --;
					}
				}
			} else {// Declaration form
				try {
					List<ViewPrePrintedDetailChallan> list = this.printService.getViewPrePrintedDetailChallanData(
							division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);

					if (list != null && list.size() > 0) {
						map.put("isData", true);
						map.put("message",
								new Challan_Print().callMainForPdf(Integer.parseInt(division), Integer.parseInt(loc),
										frm_challan, to_challan, dispatchType, prodtype, list, companyName));
					} else {
						map.put("isData", false);
						map.put("message", "");
					}
					map.put("isError", false);
				} catch (Exception e) {
					try {
						map.put("isError", true);
					} catch (Exception e1) {
						// ignore
					}
					e.printStackTrace();
					System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																			// System.out.println("Error Occurred :"
																			// +e.getMessage());// uncomment asneeded
																			// --;
				}
			}
//			System.out.println("List Size ::"+list.size());
//			if(list != null && !list.isEmpty()) {
//								System.out.println("CompanyCOde"+companyCode);
//				//String filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(new Integer(48),new Integer(9), "PIP  1916290", "PIP  1916290", "dtp", "", "tcfa", list, "D", "D",companyCode);
//				String filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(48, 9, "PIP  1916290", "PIP  1916290", "dtp", "", "P", list, "Y", "Y", companyCode,companyName);
//				map.put("filename", SPRING_RESOURCE_HANDLER_PDF_FILE_PATH_PREFIX + "/" + filename);	
//			}
//			map.put("list", list);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getPrePrintSummaryChallanGstPrint")
	public Map<String, Object> getPrePrintSummaryChallanGstPrint(HttpSession session, @RequestBody PrintBean bean,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String footer_signature_ind = "";
		try {
			footer_signature_ind = this.printService.getSpValueForFooterSignature().getSp_value();
			System.out.println("Footer Signature:" + footer_signature_ind);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
//		String show_amount = "Y";
//		String dotMatOrPdf = "P"; 
//		String message="";
//		String printtype = "tcfa";
//		String division = "48";
//		String loc = "9";
//		String frm_challan = "PIP  1904420";
//		String to_challan = "PIP  1904420";
//		String prodtype =  "";
//		String rep_type="D";
//		String dispatchType = "dtp";

			String show_amount = bean.getShowAmount();
			System.out.println("laser print :" + bean.getDotMatrixLaser());
			String dotMatOrPdf = bean.getDotMatrixLaser();
			String message = "";
			// String printtype = bean.getDispatchType();
			String printtype = bean.getReportOption();
			String division = bean.getTeamDivision();
			String loc = bean.getLocation();
			String frm_challan = bean.getFromChallan();
			String to_challan = bean.getToChallan();
			String prodtype = "";
			// String rep_type=bean.getReportOption();
			String rep_type = "D";
			String dispatchType = bean.getDispatchType();

			System.out.println("CompanyName:" + companyName);
			System.out.println("CompanyCode:" + companyCode);
			if (companyCode.equalsIgnoreCase(NIL) || companyCode.equalsIgnoreCase(NHP))
				show_amount = "Y";
			System.out.println("Print Type " + printtype);
			if (printtype.equalsIgnoreCase("tcfa")) {

				if (dotMatOrPdf.equalsIgnoreCase("D")) {
					List<ViewPrePrintedDetailChallan> list1 = null;
					list1 = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan, to_challan,
							prodtype, rep_type, bean);
					message = new Challan_Summary_Print().callMain(Integer.parseInt(division), Integer.parseInt(loc),
							frm_challan, to_challan, prodtype, rep_type, list1);
				} else {

					try {
						List<ViewPrePrintedSummaryChallan_GST> list1 = null;
						List<PrePrintedDetailChallan_withgst> list = null;
						if (printtype.equalsIgnoreCase("tcfa")) {// Summary
							list1 = this.printService.PrePrintedSummaryChallan_GSTdata(division, loc, frm_challan,
									to_challan, prodtype, rep_type, bean);
							System.out.println("List: " + list1.size());
						} else {// Detail
							list = this.printService.getPreprintedDetailedChallanData(division, loc, frm_challan,
									to_challan, dispatchType, prodtype, rep_type, bean);
						}
						// same code and format for summary and detail printing.
						if (list != null && list.size() > 0 || list1 != null && list1.size() > 0) {
							System.out.println("Comp code received " + companyCode);
							map.put("isData", true);
							if (companyCode.equalsIgnoreCase("UCM")) {
								map.put("message",
										this.unichem_challan_printing_gst_service.getPdf(Integer.parseInt(division),
												Long.parseLong(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode,
												companyName, session, request));
							}
//						else if(companyCode.trim().equalsIgnoreCase("PFZ") || companyCode.trim().equalsIgnoreCase("SER")) {
//						//	String filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(Integer.parseInt(division),Integer.parseInt(loc), frm_challan, to_challan, dispatchType,prodtype, printtype, list, show_amount, footer_signature_ind, companyCode,companyName);
//						//	map.put("message", filename);
//							map.put("message", this.preprintedsummarychallanservice.PrePrintedSummaryChallanPrint(Integer.parseInt(division),Integer.parseInt(loc), frm_challan, to_challan, dispatchType, prodtype, printtype, list1, show_amount, footer_signature_ind, companyCode, companyName,session));						
//							}
//						else if(companyCode.trim().equalsIgnoreCase("VET")){
//								//To-DO
//							}
							else {
								String filename = this.preprintedsummarychallanservice.PrePrintedSummaryChallanPrint(
										Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
										dispatchType, prodtype, printtype, list1, show_amount, footer_signature_ind,
										companyCode, companyName, session, request);
								map.put("message", filename);
							}
						} else {
							map.put("isData", false);
							map.put("message", "");
						}
						map.put("isError", false);
					} catch (Exception e) {
						try {
							map.put("isError", true);
						} catch (Exception e1) {
							// ignore
						}
						e.printStackTrace();
						System.out.println("Error Occurred :" + e.getMessage());// uncomment
																				// asneeded --
																				// System.out.println("Error Occurred :"
																				// +e.getMessage());// uncomment
																				// asneeded --;
					}
				}

			} else if (printtype.equalsIgnoreCase("dtp")) {

				if (dotMatOrPdf.equalsIgnoreCase("D")) {
					List<ViewPrePrintedDetailChallan> list = this.printService.getViewPrePrintedDetailChallanData(
							division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
					message = new Challan_Print().callMain(Integer.parseInt(division), Integer.parseInt(loc),
							frm_challan, to_challan, dispatchType, prodtype, rep_type, list, companyName);
				} else {
					try {
						List<ViewPrePrintedDetailChallan> list1 = null;
						List<PrePrintedDetailChallan_withgst> list = null;
						if (printtype.equalsIgnoreCase("tcfa")) {// Summary
							list1 = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan,
									to_challan, prodtype, rep_type, bean);
						} else {// Detail

							list = this.printService.getPreprintedDetailedChallanData(division, loc, frm_challan,
									to_challan, dispatchType, prodtype, rep_type, bean);
							System.out.println("getPreprintedDetailedChallanData List Size:" + list.size());
						}
						if (list != null && list.size() > 0) {
							map.put("isData", true);
							if (companyCode.equalsIgnoreCase("UCM")) {
								map.put("message",
										this.unichem_challan_printing_gst_service.getPdf(Integer.parseInt(division),
												Long.parseLong(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode,
												companyName, session, request));
							}

							else {
								String filename = this.preprinteddetailedchallanservice
										.getPreprintedDetailedChallanPrint(Integer.parseInt(division),
												Integer.parseInt(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode.trim(),
												companyName.trim(), session, request);
								map.put("message", filename);
							}
						} else {
							map.put("isData", false);
							map.put("message", "");
						}
						map.put("isError", false);
					} catch (Exception e) {
						try {
							map.put("isError", true);
						} catch (Exception e1) {
							// ignore
						}
						e.printStackTrace();
						System.out.println("Error Occurred :" + e.getMessage());// uncomment
																				// asneeded --
																				// System.out.println("Error Occurred :"
																				// +e.getMessage());// uncomment
																				// asneeded --;
					}
				}
			} else {// Declaration form
				try {
					List<ViewPrePrintedDetailChallan> list = this.printService.getViewPrePrintedDetailChallanData(
							division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);

					if (list != null && list.size() > 0) {
						map.put("isData", true);
						map.put("message",
								new Challan_Print().callMainForPdf(Integer.parseInt(division), Integer.parseInt(loc),
										frm_challan, to_challan, dispatchType, prodtype, list, companyName));
					} else {
						map.put("isData", false);
						map.put("message", "");
					}
					map.put("isError", false);
				} catch (Exception e) {
					try {
						map.put("isError", true);
					} catch (Exception e1) {
						// ignore
					}
					e.printStackTrace();
					System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																			// System.out.println("Error Occurred :"
																			// +e.getMessage());// uncomment asneeded
																			// --;
				}
			}
//			System.out.println("List Size ::"+list.size());
//			if(list != null && !list.isEmpty()) {
//								System.out.println("CompanyCOde"+companyCode);
//				//String filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(new Integer(48),new Integer(9), "PIP  1916290", "PIP  1916290", "dtp", "", "tcfa", list, "D", "D",companyCode);
//				String filename = this.preprinteddetailedchallanservice.getPreprintedDetailedChallanPrint(48, 9, "PIP  1916290", "PIP  1916290", "dtp", "", "P", list, "Y", "Y", companyCode,companyName);
//				map.put("filename", SPRING_RESOURCE_HANDLER_PDF_FILE_PATH_PREFIX + "/" + filename);	
//			}
//			map.put("list", list);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/getPrePrintSummaryChallanGstPrintEInvoive")
	public Map<String, Object> getPrePrintSummaryChallanGstPrintEInvoive(HttpSession session,
			@RequestBody PrintBean bean, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String footer_signature_ind = "";
		try {
			footer_signature_ind = this.printService.getSpValueForFooterSignature().getSp_value();
			System.out.println("Footer Signature:" + footer_signature_ind);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			String show_amount = bean.getShowAmount();
			System.out.println("laser print :" + bean.getDotMatrixLaser());
			String dotMatOrPdf = bean.getDotMatrixLaser();
			String message = "";
			// String printtype = bean.getDispatchType();
			String printtype = bean.getReportOption();
			String division = bean.getTeamDivision();
			String loc = bean.getLocation();
			String frm_challan = bean.getFromChallan();
			String to_challan = bean.getToChallan();
			String prodtype = "";
			// String rep_type=bean.getReportOption();
			String rep_type = "D";
			String dispatchType = bean.getDispatchType();

			System.out.println("CompanyName:" + companyName);
			System.out.println("CompanyCode:" + companyCode);
			if (companyCode.equalsIgnoreCase(NIL) || companyCode.equalsIgnoreCase(NHP))
				show_amount = "Y";
			System.out.println("Print Type " + printtype);
			if (printtype.equalsIgnoreCase("tcfa")) {

				if (dotMatOrPdf.equalsIgnoreCase("D")) {
					List<ViewPrePrintedDetailChallan> list1 = null;
					list1 = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan, to_challan,
							prodtype, rep_type, bean);
					message = new Challan_Summary_Print().callMain(Integer.parseInt(division), Integer.parseInt(loc),
							frm_challan, to_challan, prodtype, rep_type, list1);
				} else {

					try {
						List<ViewPrePrintedSummaryChallan_GST_EInvoice> list1 = null;
						List<PrePrintedDetailChallan_withgst> list = null;
						if (printtype.equalsIgnoreCase("tcfa")) {// Summary
							list1 = this.printService.PrePrintedSummaryChallan_GST_EInvoicedata(division, loc,
									frm_challan, to_challan, prodtype, rep_type, bean);
							System.out.println("List: " + list1.size());
						} else {// Detail
							list = this.printService.getPreprintedDetailedChallanData(division, loc, frm_challan,
									to_challan, dispatchType, prodtype, rep_type, bean);
						}
						// same code and format for summary and detail printing.
						if (list != null && list.size() > 0 || list1 != null && list1.size() > 0) {
							System.out.println("Comp code received " + companyCode);
							map.put("isData", true);
							if (companyCode.equalsIgnoreCase("UCM")) {
								map.put("message",
										this.unichem_challan_printing_gst_service.getPdf(Integer.parseInt(division),
												Long.parseLong(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode,
												companyName, session, request));
							} else if (companyCode.trim().equalsIgnoreCase("PFZ")) {
//								String filename = this.prePrintedSummaryChallanEInvoiceService_forPfizer.PrePrintedSummaryChallanEInvoicePrint_forPfizer(
//										Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
//										dispatchType, prodtype, printtype, list1, show_amount, footer_signature_ind,
//										companyCode, companyName, session,request);
//								map.put("message", filename);
							} else {
								String filename = this.prePrintedSummaryChallanEInvoiceService
										.PrePrintedSummaryChallanEInvoicePrint(Integer.parseInt(division),
												Integer.parseInt(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list1, show_amount, footer_signature_ind, companyCode,
												companyName, session, request);
								map.put("message", filename);
							}
						} else {
							map.put("isData", false);
							map.put("message", "");
						}
						map.put("isError", false);
					} catch (Exception e) {
						try {
							map.put("isError", true);
						} catch (Exception e1) {
							// ignore
						}
						System.out.println("Error Occurred :" + e.getMessage());// uncomment
																				// asneeded --
																				// System.out.println("Error Occurred :"
																				// +e.getMessage());// uncomment
																				// asneeded --;
					}
				}

			} else if (printtype.equalsIgnoreCase("dtp")) {

				if (dotMatOrPdf.equalsIgnoreCase("D")) {
					List<ViewPrePrintedDetailChallan> list = this.printService.getViewPrePrintedDetailChallanData(
							division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
					message = new Challan_Print().callMain(Integer.parseInt(division), Integer.parseInt(loc),
							frm_challan, to_challan, dispatchType, prodtype, rep_type, list, companyName);
				} else {
					try {
						List<ViewPrePrintedDetailChallan> list1 = null;
						List<PrePrintedDetailChallan_withgst> list = null;
						if (printtype.equalsIgnoreCase("tcfa")) {// Summary
							list1 = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan,
									to_challan, prodtype, rep_type, bean);
						} else {// Detail

							list = this.printService.getPreprintedDetailedChallanData(division, loc, frm_challan,
									to_challan, dispatchType, prodtype, rep_type, bean);
							System.out.println("getPreprintedDetailedChallanData List Size:" + list.size());
						}
						if (list != null && list.size() > 0) {
							map.put("isData", true);
							if (companyCode.equalsIgnoreCase("UCM")) {
								map.put("message",
										this.unichem_challan_printing_gst_service.getPdf(Integer.parseInt(division),
												Long.parseLong(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode,
												companyName, session, request));
							}

							else {
								String filename = this.preprinteddetailedchallanservice
										.getPreprintedDetailedChallanPrint(Integer.parseInt(division),
												Integer.parseInt(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode.trim(),
												companyName.trim(), session, request);
								map.put("message", filename);
							}
						} else {
							map.put("isData", false);
							map.put("message", "");
						}
						map.put("isError", false);
					} catch (Exception e) {
						try {
							map.put("isError", true);
						} catch (Exception e1) {
							// ignore
						}
						System.out.println("Error Occurred :" + e.getMessage());// uncomment
																				// asneeded --
																				// System.out.println("Error Occurred :"
																				// +e.getMessage());// uncomment
																				// asneeded --;
					}
				}
			} else {// Declaration form
				try {
					List<ViewPrePrintedDetailChallan> list = this.printService.getViewPrePrintedDetailChallanData(
							division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);

					if (list != null && list.size() > 0) {
						map.put("isData", true);
						map.put("message",
								new Challan_Print().callMainForPdf(Integer.parseInt(division), Integer.parseInt(loc),
										frm_challan, to_challan, dispatchType, prodtype, list, companyName));
					} else {
						map.put("isData", false);
						map.put("message", "");
					}
					map.put("isError", false);
				} catch (Exception e) {
					try {
						map.put("isError", true);
					} catch (Exception e1) {
						// ignore
					}
					System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																			// System.out.println("Error Occurred :"
																			// +e.getMessage());// uncomment asneeded
																			// --;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// GRN Label Print
	@PostMapping("/GRNlabelprinting")
	public Map<String, Object> GRNlabelprinting(@RequestBody PrintBean pb, HttpSession session,
			@RequestParam String userType, HttpServletRequest request, @RequestParam String userId) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> loc_list = null;
		List<Supplier> venderList = null;
		String user_type;
		String curr_user_id = null;
		String ip_addr = null;

//		String loc_id = "1";
//		String frmgrn = "2269";
//		String togrn = "2269";
//		String vender_id = "0";
//		String bin_no_id = "Y";
		String remarks = null;

		String loc_id = pb.getLocationId();
		String frmgrn = pb.getFromGRN();
		String togrn = pb.getToGRN();
		String vender_id = pb.getVendorId();
		String bin_no_id = pb.getBinChecked();
		System.out.println("bin yes or no " + bin_no_id);

		EntityManager em = emf.createEntityManager();
		try {
			user_type = userType;
			// user_type = "S";//((Usermaster)
			// session.getServletContext().getAttribute(APPROVAL_TYPE_USER)).getUser_type();
			System.out.println("logged in user is:" + user_type);

//			ip_addr = ServletContext.getRequest().getRemoteAddr();
//			curr_user_id = ((Usermaster) session.getServletContext().getAttribute(APPROVAL_TYPE_USER)).getLd_id().toString();

			ip_addr = request.getRemoteAddr();
			curr_user_id = userId;
			System.out.println("ip add and user id is:" + ip_addr + ".." + curr_user_id);

			List<ViewGrnLabelPrinting_java> list = this.printService.getGrnLabelPrintData(loc_id, vender_id, frmgrn,
					togrn, user_type, pb);

			if (list != null && list.size() > 0) {

				em.getTransaction().begin();
				map.put("isData", true);
				map.put("path", this.generatelabelpdfservice.generateLabelPdf(list, bin_no_id, session, request));

				Set<Long> challanset = new LinkedHashSet<Long>();
				for (ViewGrnLabelPrinting_java obj : list) {
					challanset.add(obj.getGrn_id());
				}
				/*
				 * ///Update Need to be done Later
				 * this.printService.updateGrnLblPrintRemarks(em, "Y", remarks, curr_user_id,
				 * ip_addr, challanset); em.getTransaction().commit();
				 */
			} else {
				map.put("isData", false);
				map.put("path", "");
			}
			map.put("isError", false);
		} catch (Exception e) {
			em.getTransaction().rollback();
			try {
				map.put("isError", true);
			} catch (Exception e1) {

			}
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}

		return map;
	}

	// dispatch label print
	@PostMapping("/detailLabelprint")
	public Map<String, Object> detailLabelprint(@RequestBody PrintBean pb, HttpSession session,
			@RequestParam String userType, @RequestParam String userId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String fileName;

//			String frmChallan = "77500";
//			String toChallan = "77694";
//			String user_type="N";//((Usermaster) session.getServletContext().getAttribute(APPROVAL_TYPE_USER)).getUser_type();
//			String ip_addr = null;
//			String curr_user_id;
//			String remarks = null;
//			curr_user_id ="";

		String frmChallan = pb.getFromChallan();
		String toChallan = pb.getToChallan();
		String user_type = userType;
		String ip_addr = null;
		String curr_user_id = userId;
		String remarks = null;
		curr_user_id = "";

		System.out.println("user type and id is :" + user_type + ".." + curr_user_id);

		EntityManager em = emf.createEntityManager();
		List<PrintBean> list = null;
		try {
			list = printService.getdetailLabelData(frmChallan, toChallan, user_type, pb.getFinyearflg());
			if (list != null && list.size() > 0) {
				System.out.println("Generating PDF!");
				// em.getTransaction().begin();
				fileName = this.delchallanprintpdfservice.getPdf(list, session, request);
				System.out.println("fileName::" + fileName);
				map.put("message", fileName);
				map.put("isData", true);
				// if(fileName!=null && fileName!=""){
				// update print_flag in dispatch header
				// String ip_addr = ServletActionContext.getRequest().getRemoteAddr();

				/*
				 * /////Update Need to be done Later
				 * this.printService.updateDispatchLblPrintRemark(em, "Y", remarks,
				 * curr_user_id, ip_addr, challanset); em.getTransaction().commit();
				 */
				// }
			}
			System.out.println("Success!");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("isData", false);
			em.getTransaction().rollback();

			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// Pick and Packslip
	@PostMapping("/printPackingSlip")
	public Map<String, Object> printPackingSlip(@RequestBody PrintBean pb, HttpSession session,
			HttpServletRequest request) throws Exception {
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		// Temporary Parameters
//			 String rep_type="P";
//			 String footer_signature_ind="";
//			 String printtype="dtp";
//			 String division="48";
//			 String loc="9";
//			 String prodtype="";
//			 String dispatchType="dtp";

		// Temporary For Packing slip
//			 String frm_challan="PIP  1916290";
//			 String to_challan="PIP  1916290";

		// Temporary for pick Slip
		// String frm_challan="PIP 1904420";
		// String to_challan="PIP 1904420";

		String rep_type = "P";
		String footer_signature_ind = "";
		String printtype = pb.getReportOption();
		String division = pb.getTeamDivision();
		String loc = pb.getLocation();
		String prodtype = "";
		String dispatchType = pb.getDispatchType();

		String frm_challan = pb.getFromChallan();
		String to_challan = pb.getToChallan();

		Map<String, Object> map = new HashMap<String, Object>();
		footer_signature_ind = this.printService.getSpValueForFooterSignature().getSp_value();
		System.out.println("footer_signature_ind :: " + footer_signature_ind);

		if (printtype.equalsIgnoreCase("tcfa")) {
			System.out.println("SummaryChallan.printPackingSlip() SUMMARY");
			try {
				List<ViewPrePrintedDetailChallan> list = null;
				list = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan, to_challan,
						prodtype, rep_type, pb);

				if (list != null && list.size() > 0) {
					map.put("isData", true);
					map.put("message",
							this.packslipprintpdf.getPdf(Integer.parseInt(division), Integer.parseInt(loc), frm_challan,
									to_challan, dispatchType, prodtype, printtype, list, footer_signature_ind,
									companyName, companyCode, session, request));
				} else {
					map.put("isData", false);
					map.put("message", "");
				}
				map.put("isError", false);
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																		// System.out.println("Error Occurred :"
																		// +e.getMessage());// uncomment asneeded --;
			}
		} else if (printtype.equalsIgnoreCase("dtp")) {
			System.out.println("SummaryChallan.printPackingSlip() DETAIL ");
			try {
				List<ViewPrePrintedDetailChallan> list = null;

				list = this.printService.getViewPrePrintedDetailChallanData(division, loc, frm_challan, to_challan,
						dispatchType, prodtype, rep_type, pb);

				if (list != null && list.size() > 0) {
					map.put("isData", true);
					map.put("message",
							this.packslipprintpdf.getPdf(Integer.parseInt(division), Integer.parseInt(loc), frm_challan,
									to_challan, dispatchType, prodtype, printtype, list, footer_signature_ind,
									companyName, companyCode, session, request));

				} else {
					map.put("isData", false);
					map.put("message", "");
				}
				map.put("isError", false);
			} catch (Exception e) {
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																		// System.out.println("Error Occurred :"
																		// +e.getMessage());// uncomment asneeded --;
			}
		}
		return map;
	}

	// print Iaa Voucher pdf
	@PostMapping("/printIaaPdf")
	public Map<String, Object> printIaaPdf(@RequestBody PrintBean pb, HttpSession session, HttpServletRequest request)
			throws Exception {
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

		Map<String, Object> map = new HashMap<String, Object>();
//			String locId="1";
//			String frmStkno="1541";
//			String toStkno="1546";
		String uname = this.tokenProvider.getUsernameFromRequest(request);
		String empId = this.userMasterService.getEmpIdByUsername(uname);
		String locId = pb.getLocationId();
		String frmStkno = pb.getFromIAA();
		String toStkno = pb.getToIAA();

		try {
			List<ViewIAAVoucherPrinting> list = this.printService.getViewIAAVoucherPrintingdata(locId, frmStkno,
					toStkno, pb);
			if (list != null && list.size() > 0) {

				if (pb.getPrintExcel().equals("P")) {
					map.put("isData", true);
					map.put("path",
							this.iaaprintpdf.generateIaaPrint(list, companyName, companyCode, session, request));
				}

				if (pb.getPrintExcel().equals("E")) {

					map.put("isData", true);
					map.put("path", this.iaaprintpdf.generateIaaExcel(list, companyName, companyCode, empId));

				}
			} else {
				map.put("isData", false);
				map.put("path", "");
			}
			map.put("isError", false);
		} catch (Exception e) {
			try {
				map.put("isError", true);
			} catch (Exception e1) {
				// ignore
			}
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// HQ Master Audit Trail
	@GetMapping("/Hq-audit-trail-Pdf")
	public Map<String, Object> PrintHqaudit_trail_pdf(HttpSession session, HttpServletRequest request) {

		List<HqMasterAuditTrailModel> list = null;
		Map<String, Object> map = new HashMap<>();

		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String username = "PFZ-DIST";

			PrintBean bean = new PrintBean();

			bean.setDivid("0");

			list = printService.getHqmasteraudittraildata(bean);

			if (list != null && list.size() > 0) {
				map.put("isData", true);
				map.put("filename", this.hqmasteraudit_trail_printpdf_service
						.generateHq_Master_audit_trail_print_pdf(list, username, companyName, session, request));

			} else {
				map.put("isData", false);
				map.put("path", "");
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	// StkTrfPrint(21May2020)
	@GetMapping("/getLocationForPrint")
	public Map<String, Object> getLocationForPrint(HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Location> locList = null;
		try {
			locList = locationService.getAllLocations();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("locList", locList);
		return map;
	}

	@GetMapping("/getTrfNoList")
	public Map<String, Object> getTrfNoList(@RequestParam String location, @RequestParam String rl,
			@RequestParam String StartDate, @RequestParam String EndDate,@RequestParam String fin_year_flag) throws Exception {
		String startDate = null, endDate = null;
		try {
//				SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH);
//				Date date1 = sdf3.parse(StartDate);
//				System.out.println("ist to date is: " + date1);
//				
//				Date date2 = sdf3.parse(EndDate);
//				System.out.println("ist to date is: " + date2);
//				
//				startDate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
//				endDate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);

			startDate = StartDate;
			endDate = EndDate;
			System.out.println("date converted are :" + startDate + ".." + endDate);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		List<Stock_Transfer_Header> fromTrfNoList = null;
		try {
			fromTrfNoList = printService.getTrfNoList(location, rl, startDate, endDate,fin_year_flag);
			System.out.println("list size " + fromTrfNoList.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("fromTrfNoList", fromTrfNoList);
		return map;
	}

	@PostMapping("/stock-transfer-invoice")
	public Map<String, Object> StockTransferInvoice(HttpSession session, @RequestBody PrintBean pb,
			HttpServletRequest request) {

		String startDate = null, endDate = null;
		try {
			SimpleDateFormat sdf3 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			Date date1 = sdf3.parse(pb.getStartDate().toString());
//				System.out.println("ist to date is: " + date1);

			Date date2 = sdf3.parse(pb.getEndDate().toString());
//				System.out.println("ist to date is: " + date2);

			startDate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
			endDate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);

//				System.out.println("date converted are :"+startDate+".."+endDate);

			Date initDate = new SimpleDateFormat("dd/mm/yyyy").parse(startDate);
			Date initDate1 = new SimpleDateFormat("dd/mm/yyyy").parse(endDate);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
			startDate = formatter.format(initDate);
			endDate = formatter.format(initDate1);
			// System.out.println("date converted are :"+startDate+".."+endDate);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}

		System.out.println(pb.getSlId().toString());
		System.out.println(pb.getRlId().toString());
		System.out.println(pb.getFromTrf().toString());
		System.out.println(pb.getToTrf().toString());
		System.out.println(startDate);
		System.out.println(endDate);

		List<StockTransferNotePrint> list = null;
		Map<String, Object> map = new HashMap<>();
		String footer_signature_ind = null;
		String filename = null;
		try {
			PrintBean bean = new PrintBean();

			bean.setLocationId(pb.getSlId().toString());
			bean.setCfa(pb.getRlId().toString());
			bean.setFromChallan(pb.getFromTrf().toString());
			bean.setToChallan(pb.getToTrf().toString());
			bean.setFrm_dt(startDate);
			bean.setTo_dt(endDate);

//				bean.setLocationId("1");
//				bean.setCfa("0");
//				bean.setFromChallan("BHITX2000020");
//				bean.setToChallan("BHNTX2000011");
//				bean.setFrm_dt("01-11-2019");
//				bean.setTo_dt("18-05-2020");
			bean.setReportOption("D");
			footer_signature_ind = printService.getSpValueForFooterSignature().getSp_value();

			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			list = printService.getStocktransfernoteData(bean);
			System.out.println("footer_signature_ind :: " + footer_signature_ind);
			map.put("list", list);
			if (list != null && list.size() > 0) {
				// jb.put("message", new
				// StockTransferNotePrinting().getPdf(0,locId.intValue(),from,to,"","","",list,"Y",footer_signature_ind));
				// //Old For refrence
				if (PIPL_LOC.contains(list.get(0).getLoc_id())) {
					companyName = "For Pfizer Products India Private Limited.";
				}
				String uname = this.tokenProvider.getUsernameFromRequest(request);
				String empId = this.userMasterService.getEmpIdByUsername(uname);

				filename = stocktransfernoteprint_service.generate_StockTransferPrint(0,
						Integer.parseInt(bean.getLocationId()), bean.getFromChallan(), bean.getToChallan(), "", "", "",
						list, "Y", footer_signature_ind, companyCode, companyName, empId);
				map.put("filename", filename);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}

		return map;

	}

	@PostMapping("/stock-withdrawal-voucher-print")
	public Map<String, Object> StockWithdrawalVoucherPrint(HttpSession session, @RequestBody PrintBean pb,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<ViewStockWithdrawalVoucherPrint> list = null;
		try {

			list = printService.getstockwithdrawaldata(pb);
			String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			if (list != null && list.size() > 0) {
				map.put("isData", true);
				if (pb.getReportOption().equalsIgnoreCase("D"))
					map.put("filename", stockwithdrawalpdfservice.generateStockwithdrawalpdf(pb, list, companyName));
				else
					map.put("filename", stockwithdrawalpdfservice.generateStockWithVoucherPickPdf(pb, list, companyName,
							session, request));
			} else {
				map.put("isData", false);
				map.put("filename", " ");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}

		return map;

	}

	@GetMapping("/getdataforstockwithdrawalprint")
	public Map<String, Object> getstockwithdrawalprintdata() throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Location> locList = null;
		List<Period> finYearList = null;
		Period prd = null;
		try {
			locList = locationService.getAllLocations();
			finYearList = printService.getAllFinYearForGSTChallanPrint();
			prd = periodmasterservice.getCurrentPeriod();
			map.put("loclist", locList);
			map.put("finyearlist", finYearList);
			map.put("prd", prd);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getPeriodByfinyear")
	public Map<String, Object> getperiodbyfinyear(@RequestParam("finyr") String finyr) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Period> list = null;
		try {
			list = printService.getperiodsbyfinyear(finyr);

			map.put("periodlist", list);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getchallansforstockwithdrawal")
	public Map<String, Object> getstockwithdrawalchallans(@RequestParam("loc_id") Long loc_id,
			@RequestParam("period_code") String period_code, @RequestParam("year") String year,
			@RequestParam("page_ind") String page_ind, @RequestParam("finyearflag") String finyearflag)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<SWV_Header> list = null;
		List<SWV_Header_arc> arc_list = null;
		try {

			if (finyearflag.equals("CURRENT")) {
				list = printService.getstockwithdrawalpdfchallans(loc_id, period_code, year, page_ind);
				map.put("challanlist", list);
			} else {
				arc_list = printService.getstockwithdrawalpdfchallansforpreviousyear(loc_id, period_code, year,
						page_ind);
				map.put("challanlist", arc_list);
			}
			;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@SuppressWarnings("unused")
	@GetMapping("/getTeamListfromDivision")
	public Map<String, Object> getTeamListfromDivision(@RequestParam("teamId") String teamId,
			@RequestParam("team_reqd_ind") String team_req_ind) {
		Map<String, Object> map = new HashMap<>();
		System.out.println(" team_req_ind " + team_req_ind + " teamId::" + teamId + "::::");
		try {
			if (team_req_ind.trim().equals("Y")) {
				System.out.println("If");
				map.put("subTeamList", printService.getTeamList(teamId));
				// map.put("subTeamList", printService.getTeamList(Long.valueOf(teamId)));
			} else {
				System.out.println("else");
				map.put("subTeamList", null);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getChallanListForGSTChallanPrintByTeamCode")
	public Map<String, Object> getChallanListForGSTChallanPrintByTeamCode(@RequestParam String divId,
			@RequestParam String periodCode, String finYear, @RequestParam String location,
			@RequestParam String finyrflg, String teamCode) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> challanList = null;
		try {

			challanList = printService.getChallanListForGSTChallanPrintByTeamCode(divId, periodCode, finYear, location,
					finyrflg, teamCode);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("challanList", challanList);
		return map;
	}

	@GetMapping("/getDeliveryChallanprintOnload")
	public Map<String, Object> getDeliveryOnload(@RequestParam("userid") String userid, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<DivMaster> List = null;
		List<Period> finlist = null;
		List<Location> loclist = null;
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			List = this.divisionMasterService.getDivreport(userid);
			finlist = printService.getAllFinYearForGSTChallanPrint();
			loclist = reportservice.getlocationforstockageing();

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}

		map.put("DivList", List);
		map.put("finYearList", finlist);
		map.put("loclist", loclist);
		return map;
	}

	@GetMapping("/getChallanListDeliveryChallan")
	public Map<String, Object> getChallanListForDeliveryChallan(@RequestParam String pageIndQuery,
			@RequestParam String ro, @RequestParam String divId, @RequestParam String periodCode,
			@RequestParam String finYear, @RequestParam String location, @RequestParam String cnfLocation,
			@RequestParam String finyrflg, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> challanList = null;
		try {
			System.out.println(pageIndQuery);
			System.out.println(ro);
			System.out.println(divId);
			System.out.println(periodCode);
			System.out.println(finYear);
			System.out.println(location);
			System.out.println(cnfLocation);
			challanList = printService.getChallanBtwStdtEndDtPkgList(pageIndQuery, ro, divId, periodCode, finYear,
					location, finyrflg, cnfLocation, session);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("challanList", challanList);
		return map;
	}

	@PostMapping("/getChallanPrint")
	public Map<String, Object> getPdfgetPdf(@RequestBody PrintBean pb, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<NOV_BillOfSupply_Challan> list = null;
		String footer_signature_ind = "";
		System.out.println("Printing" + pb.toString());
		try {

			list = printService.getChallanBtwStdtEndDtPkgList(pb);
			System.out.println("list size :; " + list.size());
			footer_signature_ind = this.printService.getSpValueForFooterSignature().getSp_value();

			String filename = nov_billofsupply_challanprinting.getPdfgetPdf(pb.getTeamDivision(), pb.getLocation(),
					pb.getFromChallan(), pb.getToChallan(), pb.getReportOption(),
					pb.getProdType() == null ? "" : pb.getProdType(), pb.getPageIndQuery(), list, "Y", session,
					footer_signature_ind);
			System.out.println(filename);

			map.put("message", filename);
			if (filename.length() > 0) {
				map.put("isData", true);
			} else {
				map.put("isData", false);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// for stockist

	@PostMapping("/getPrePrintSummaryChallanGstPrintEInvoiveStockist")
	public Map<String, Object> getPrePrintSummaryChallanGstPrintEInvoiveStockist(HttpSession session,
			@RequestBody PrintBean bean, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		String footer_signature_ind = "";
		try {
			footer_signature_ind = this.printService.getSpValueForFooterSignature().getSp_value();
			System.out.println("Footer Signature:" + footer_signature_ind);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();

			String show_amount = bean.getShowAmount();
			System.out.println("laser print :" + bean.getDotMatrixLaser());
			String dotMatOrPdf = bean.getDotMatrixLaser();
			String message = "";
			// String printtype = bean.getDispatchType();
			String printtype = bean.getReportOption();
			String division = bean.getTeamDivision();
			String loc = bean.getLocation();
			String frm_challan = bean.getFromChallan();
			String to_challan = bean.getToChallan();
			String prodtype = "";
			// String rep_type=bean.getReportOption();
			String rep_type = "D";
			String dispatchType = bean.getDispatchType();

			System.out.println("CompanyName:" + companyName);
			System.out.println("CompanyCode:" + companyCode);
			if (companyCode.equalsIgnoreCase(NIL) || companyCode.equalsIgnoreCase(NHP))
				show_amount = "Y";
			System.out.println("Print Type " + printtype);
			if (printtype.equalsIgnoreCase("tcfa")) {

				if (dotMatOrPdf.equalsIgnoreCase("D")) {
					List<ViewPrePrintedDetailChallan> list1 = null;
					list1 = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan, to_challan,
							prodtype, rep_type, bean);
					message = new Challan_Summary_Print().callMain(Integer.parseInt(division), Integer.parseInt(loc),
							frm_challan, to_challan, prodtype, rep_type, list1);
				} else {

					try {
						List<ViewPrePrintedSummaryChallan_GST_EInvoiceStockist> list1 = null;
						List<PrePrintedDetailChallan_withgst> list = null;
						if (printtype.equalsIgnoreCase("tcfa")) {// Summary
							list1 = this.printService.PrePrintedSummaryChallan_GST_EInvoicedataStockist(division, loc,
									frm_challan, to_challan, prodtype, rep_type, bean);
							System.out.println("List: " + list1.size());
						} else {// Detail
							list = this.printService.getPreprintedDetailedChallanData(division, loc, frm_challan,
									to_challan, dispatchType, prodtype, rep_type, bean);
						}
						// same code and format for summary and detail printing.
						if (list != null && list.size() > 0 || list1 != null && list1.size() > 0) {
							System.out.println("Comp code received " + companyCode);
							map.put("isData", true);
							if (companyCode.equalsIgnoreCase("UCM")) {
								map.put("message",
										this.unichem_challan_printing_gst_service.getPdf(Integer.parseInt(division),
												Long.parseLong(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode,
												companyName, session, request));
							} else if (companyCode.trim().equalsIgnoreCase("PFZ")) {
								String filename = this.prePrintedSummaryChallanEInvoiceService_forPfizer
										.PrePrintedSummaryChallanEInvoicePrint_forPfizer(Integer.parseInt(division),
												Integer.parseInt(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list1, show_amount, footer_signature_ind, companyCode,
												companyName, session, request);
								map.put("message", filename);
							} else {
//								String filename = this.prePrintedSummaryChallanEInvoiceService.PrePrintedSummaryChallanEInvoicePrint(
//										Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
//										dispatchType, prodtype, printtype, list1, show_amount, footer_signature_ind,
//										companyCode, companyName, session,request);
//								map.put("message", filename);
							}
						} else {
							map.put("isData", false);
							map.put("message", "");
						}
						map.put("isError", false);
					} catch (Exception e) {
						try {
							map.put("isError", true);
						} catch (Exception e1) {
							// ignore
						}
						System.out.println("Error Occurred :" + e.getMessage());// uncomment
																				// asneeded --
																				// System.out.println("Error Occurred :"
																				// +e.getMessage());// uncomment
																				// asneeded --;
					}
				}

			} else if (printtype.equalsIgnoreCase("dtp")) {

				if (dotMatOrPdf.equalsIgnoreCase("D")) {
					List<ViewPrePrintedDetailChallan> list = this.printService.getViewPrePrintedDetailChallanData(
							division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);
					message = new Challan_Print().callMain(Integer.parseInt(division), Integer.parseInt(loc),
							frm_challan, to_challan, dispatchType, prodtype, rep_type, list, companyName);
				} else {
					try {
						List<ViewPrePrintedDetailChallan> list1 = null;
						List<PrePrintedDetailChallan_withgst> list = null;
						if (printtype.equalsIgnoreCase("tcfa")) {// Summary
							list1 = this.printService.getPreprintedSummaryChallanData(division, loc, frm_challan,
									to_challan, prodtype, rep_type, bean);
						} else {// Detail

							list = this.printService.getPreprintedDetailedChallanData(division, loc, frm_challan,
									to_challan, dispatchType, prodtype, rep_type, bean);
							System.out.println("getPreprintedDetailedChallanData List Size:" + list.size());
						}
						if (list != null && list.size() > 0) {
							map.put("isData", true);
							if (companyCode.equalsIgnoreCase("UCM")) {
								map.put("message",
										this.unichem_challan_printing_gst_service.getPdf(Integer.parseInt(division),
												Long.parseLong(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode,
												companyName, session, request));
							}

							else {
								String filename = this.preprinteddetailedchallanservice
										.getPreprintedDetailedChallanPrint(Integer.parseInt(division),
												Integer.parseInt(loc), frm_challan, to_challan, dispatchType, prodtype,
												printtype, list, show_amount, footer_signature_ind, companyCode.trim(),
												companyName.trim(), session, request);
								map.put("message", filename);
							}
						} else {
							map.put("isData", false);
							map.put("message", "");
						}
						map.put("isError", false);
					} catch (Exception e) {
						try {
							map.put("isError", true);
						} catch (Exception e1) {
							// ignore
						}
						System.out.println("Error Occurred :" + e.getMessage());// uncomment
																				// asneeded --
																				// System.out.println("Error Occurred :"
																				// +e.getMessage());// uncomment
																				// asneeded --;
					}
				}
			} else {// Declaration form
				try {
					List<ViewPrePrintedDetailChallan> list = this.printService.getViewPrePrintedDetailChallanData(
							division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type, bean);

					if (list != null && list.size() > 0) {
						map.put("isData", true);
						map.put("message",
								new Challan_Print().callMainForPdf(Integer.parseInt(division), Integer.parseInt(loc),
										frm_challan, to_challan, dispatchType, prodtype, list, companyName));
					} else {
						map.put("isData", false);
						map.put("message", "");
					}
					map.put("isError", false);
				} catch (Exception e) {
					try {
						map.put("isError", true);
					} catch (Exception e1) {
						// ignore
					}
					System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																			// System.out.println("Error Occurred :"
																			// +e.getMessage());// uncomment asneeded
																			// --;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getChallanListForGSTSummaryChallanPrintStockist")
	public Map<String, Object> getChallanListForGSTSummaryChallanPrintStockist(@RequestParam String reportOption,
			@RequestParam String divId, @RequestParam String periodCode, String finYear, @RequestParam String location,
			@RequestParam String finyearflg) {
		Map<String, Object> map = new HashMap<>();
		List<Dispatch_Header> challanList = null;
		try {
			if (finyearflg.equals("CURRENT")) {
				challanList = printService.getChallanListForGSTSummaryChallanPrintStockist(reportOption, divId,
						periodCode, finYear, location);
			} else {
				challanList = printService.getChallanListForGSTSummaryChallanPrintPreviousStockist(reportOption, divId,
						periodCode, finYear, location);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --
																	// System.out.println("Error Occurred :"
																	// +e.getMessage());// uncomment asneeded --;
		}
		map.put("challanList", challanList);
		return map;
	}

}
