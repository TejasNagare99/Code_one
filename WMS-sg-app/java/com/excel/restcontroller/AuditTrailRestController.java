package com.excel.restcontroller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.AuditTrailBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.Audit_log;
import com.excel.model.BatchMasterAuditTrailModel;
import com.excel.model.Company;
import com.excel.model.DispatchAuditTrailBean;
import com.excel.model.DivField;
import com.excel.model.DivMaster;
import com.excel.model.FieldMasterAttrib;
import com.excel.model.FinYear;
import com.excel.model.GrnAuditTrailBean;
import com.excel.model.HqMasterAuditTrailModel;
import com.excel.model.ItemMasterAuditTrail;
import com.excel.model.Location;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SamplProdGroup;
import com.excel.model.SmpItem;
import com.excel.model.SupplierMasterModel;
import com.excel.model.ViewDownloadIaaAuditTrail;
import com.excel.service.AuditTrailService;
import com.excel.service.Batch_Master_AuditTrail_PDF_Service;
import com.excel.service.Batch_Master_AuditTrail_Report_Service;
import com.excel.service.DispatchAuditDownloadService;
import com.excel.service.DivisionMasterService;
import com.excel.service.FieldMasterAudit_Report_Service;
import com.excel.service.FieldMasterResignedAudit_Report_Service;
import com.excel.service.FieldStaffService;
import com.excel.service.FinancialYearService;
import com.excel.service.GrnAuditDownloadService;
import com.excel.service.HqMasterAuditTrail_Report_Service;
import com.excel.service.HqMasteraudit_trail_PrintPdf_Service;
import com.excel.service.IaaAuditTrail_Excel_Service;
import com.excel.service.IaaAuditTrialService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.ProductMasterService;
import com.excel.service.Product_Master_audit_trail_PDFService;
import com.excel.service.Product_Master_audit_trail_reportService;
import com.excel.service.SamplProdGroupService;
import com.excel.service.SupplierMasterDownload_PDF_Service;
import com.excel.service.SupplierMasterDownload_Report_Service;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@RestController
@RequestMapping("/rest")
public class AuditTrailRestController implements MedicoConstants {
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;
	@Autowired AuditTrailService auditTrailService;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired ParameterService parameterService;
	@Autowired FieldStaffService fieldStaffService;
	@Autowired LocationService locationService;
	@Autowired SamplProdGroupService samplProdGroupService;
	@Autowired ProductMasterService productMasterService;
	@Autowired FinancialYearService financialYearService;
	
	@Autowired private FieldMasterAudit_Report_Service fieldmasteraudit_report_service;
	@Autowired private Product_Master_audit_trail_reportService product_master_audit_trail_reportservice;
	@Autowired private Product_Master_audit_trail_PDFService product_master_audit_trail_pdfservice;
	@Autowired private SupplierMasterDownload_Report_Service suppliermasterdownload_report_service;
	@Autowired private SupplierMasterDownload_PDF_Service suppliermasterdownload_pdf_service;
	@Autowired private HqMasteraudit_trail_PrintPdf_Service hqmasteraudit_trail_printpdf_service;
	@Autowired private HqMasterAuditTrail_Report_Service hqmasteraudittrail_report_service;
	@Autowired private Batch_Master_AuditTrail_Report_Service batch_master_audittrail_report_service;
	@Autowired private Batch_Master_AuditTrail_PDF_Service batch_master_audittrail_pdf_service;
	@Autowired private GrnAuditDownloadService grnAuditDownloadService;
	@Autowired private DispatchAuditDownloadService dispatchAuditDownloadService;
	@Autowired private IaaAuditTrialService iaaAuditTrialService;
	@Autowired private IaaAuditTrail_Excel_Service iaaaudittrail_excel_service;
	@Autowired private FieldMasterResignedAudit_Report_Service fieldMasterResignedAuditReport_service;

	
	
	@GetMapping("/getDetailForFieldMasterAudit")
	public Map<String, Object> getDetailForFieldMasterAudit(HttpSession session) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<SG_d_parameters_details> fsCategoryList = null;
		List<DivMaster> divList= null;
		List<FinYear> finYearList = null;
		try {
			String subCompName = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			divList=this.divisionMasterService.getAllStandardDivisionList();
			fsCategoryList=this.parameterService.getFsTypeInd();
			
			finYearList=financialYearService.getAllFinancialYears(subCompName);
			String from="";
			String to="";
			LinkedHashMap<String, String> finYears = new LinkedHashMap<String, String>();
			
			for (FinYear f : finYearList) {
				from = f.getFin_start_date().toString().substring(8,10) + "-" + f.getFin_start_date().toString().substring(5,7) + 
						"-" + f.getFin_start_date().toString().substring(0,4);
				to   = f.getFin_end_date().toString().substring(8,10) + "-" + f.getFin_end_date().toString().substring(5,7) + 
						"-" + f.getFin_end_date().toString().substring(0,4);
				finYears.put(f.getFin_year(), from + " To " + to);
			}
			
			map.put("divList", divList);
			map.put("fsCategoryList", fsCategoryList);
			map.put("finYearList", finYearList);
			map.put("finYears", finYears);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	
		return map;
	}
	@GetMapping("/getHierarchyLevel")
	public Map<String,Object> getHierarchyLevel(@RequestParam String divId,@RequestParam String ind){
		System.out.println("ind is :"+ind);
		Map<String,Object> map=new HashMap<String, Object>();
		List<DivField> hLevelList= null;
		try {
			hLevelList= this.fieldStaffService.getLevel(divId,ind+"");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("hLevelList", hLevelList);
		return map;
	}


	//Field Master Audit Trail Download
	@PostMapping("/get-field-master-audit-trail-download")
	public Map<String,Object> getfieldmaster_audit_trail(HttpSession session,HttpServletRequest request,@RequestBody AuditTrailBean atb){
		
		System.out.println(atb.getDivision());
		System.out.println(atb.getHierarchyLevel());
		System.out.println(atb.getFsCategory());
		
		
		Map<String, Object> map = new HashMap<>();
		List<FieldMasterAttrib> lst = null;
		String print_radio =atb.getPdfExcel();					//to decide pdf or excel
		//String print_radio ="xls";					//to decide pdf or excel
		String filename=null;
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String username = atb.getUsername();
		//String fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()),
		//		tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate());
		
		AuditTrailBean bean = new AuditTrailBean();
		
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
			bean.setLevel(atb.getHierarchyLevel());
			bean.setAudit("Y");				//it is set hardcoded in old app
			bean.setFstafftraining(atb.getFsCategory());
			bean.setDivId(atb.getDivision());
			bean.setStartdate(atb.getStartdate());
			bean.setEnddate(atb.getEnddate());
			
			
//			bean.setLevel("0");
//			bean.setAudit("Y");				//it is set hardcoded in old app
//			bean.setFstafftraining("T");
//			bean.setDivId("0");
			
			lst = auditTrailService.getFieldMasterAuditTraildata(bean);
			map.put("lst", lst);
			if(lst != null &&lst.size()!=0) {
				
				if(print_radio.equals("xls")) {
					filename=fieldmasteraudit_report_service.getFieldMasterReport(lst, companyname, username,empId);
					//map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
					map.put("filename", filename);
				}
				
				if(print_radio.equals("pdf")) {
					filename = fieldmasteraudit_report_service.getFieldMasterPrint(lst, companyname, username, bean,session,request);
					//map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
					map.put("filename", filename);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
		
	}
	
	@PostMapping("/get-field-master-resigned-audit-trail-download")
	public Map<String,Object> getFieldMasterResigned_audit_trail(HttpSession session,HttpServletRequest request,@RequestBody AuditTrailBean atb){
		
		System.out.println(atb.getDivision());
		System.out.println(atb.getHierarchyLevel());
		System.out.println(atb.getFsCategory());
		
		
		Map<String, Object> map = new HashMap<>();
		List<FieldMasterAttrib> lst = null;
		String print_radio =atb.getPdfExcel();					//to decide pdf or excel
		//String print_radio ="xls";					//to decide pdf or excel
		String filename=null;
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String username = atb.getUsername();
		
		AuditTrailBean bean = new AuditTrailBean();
		
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
			bean.setLevel(atb.getHierarchyLevel());
			bean.setAudit("Y");				//it is set hardcoded in old app
			bean.setFstafftraining(atb.getFsCategory());
			bean.setDivId(atb.getDivision());
			bean.setStartdate(atb.getStartdate());
			bean.setEnddate(atb.getEnddate());
			
			lst = auditTrailService.getFieldMasterResignedAuditTraildata(bean);
			map.put("lst", lst);
			if(lst != null &&lst.size()!=0) {
				
				if(print_radio.equals("xls")) {
					filename=fieldMasterResignedAuditReport_service.getFieldMasterResignedReport(lst, companyname, username,empId);
					map.put("filename", filename);
				}
				
				if(print_radio.equals("pdf")) {
					filename = fieldMasterResignedAuditReport_service.getFieldMasterResignedPrint(lst, companyname, username, bean,session,request);
					map.put("filename", filename);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
		
	}


//Product Master AUditTrail
	
	@GetMapping("/getDetailForProductMasterAudit")
	public Map<String, Object> getDetailForProductMasterAudit(@RequestParam String empId) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<SG_d_parameters_details> productTypeList = null;
		List<DivMaster> divList= null;
		List<Location> subCompanyList=null;
		List<SamplProdGroup> brandList = null;
		try {
			divList=this.divisionMasterService.getDivAccessedByUser(empId);
			productTypeList=this.parameterService.getProductTypeList();
			subCompanyList=locationService.getAllSubCompanies();
			brandList=this.samplProdGroupService.getAllSamplProducts();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("divList", divList);
		map.put("productTypeList", productTypeList);
		map.put("subCompanyList",subCompanyList);
		map.put("brandList", brandList);
		return map;
	}
	@GetMapping("/getItemListByDivIdForProductAuditTrail")
	public Map<String, Object> getItemListByDivIdForProductAuditTrail(@RequestParam String divId) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<SmpItem> itemList= null;
		try {
			itemList=productMasterService.getItemListByDivId(divId);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("itemList", itemList);
		return map;
	}
	
	@PostMapping("/get-product-master-audit-trail-report")
	public Map<String,Object> getProductAllocation_Report(HttpSession session,@RequestBody AuditTrailBean atb,HttpServletRequest request){
		System.out.println(atb.getDivision());
		System.out.println(atb.getProductType());
		//System.out.println(atb.getSaBrand());
		System.out.println(atb.getSubCompany());
		System.out.println(atb.getProduct());
		System.out.println(atb.getCheckme());
		System.out.println(atb.getPdfExcel());
		
		Map<String,Object>map = new HashMap<>();
		List<ItemMasterAuditTrail> list = null;
		List<String> smp_prd_grp_str = atb.getSaBrand();	//This List is list of Brands eg. (130,102)	
		boolean checkme = true;		//check for printing additional details in pdf
		String username =  atb.getUsername();
		//String print_radio ="pdf";
		String print_radio =atb.getPdfExcel();
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String filename;
		String date=MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date());	//passed current date
		
		try {	
			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
			//smp_prd_grp_str.add("0", "0");//temporary
			AuditTrailBean bean = new AuditTrailBean();
			StringBuilder brand_str =null;
			System.out.println(".........."+smp_prd_grp_str.size());
//			if(print_radio.equals("pdf")) {
				if(smp_prd_grp_str.get(0).equalsIgnoreCase("0")){
					 brand_str = new StringBuilder(" ");
				}else{
					brand_str = new StringBuilder("(");
					for(int i=0;i<smp_prd_grp_str.size();i++){
						brand_str.append(smp_prd_grp_str.get(i)).append(",");
						
					}
					brand_str.deleteCharAt(brand_str.lastIndexOf(","));
					brand_str.append(")");
				}
//			}
			System.out.println("brand string "+brand_str);


//		bean.setDivId("0");
//		bean.setBrand(brand_str.toString());
//		bean.setSubcompany("1");
//		bean.setProd_id("0");
//		bean.setProduct_type("0");
		
		bean.setDivId(atb.getDivision());
		bean.setBrand(brand_str.toString());
		bean.setSubcompany(atb.getSubCompany());
		bean.setProd_id(atb.getProduct());
		bean.setProduct_type(atb.getProductType());
		
			list = auditTrailService.getProductMasterAudit_Trail_Report_Data(bean);
			
			if(list.size()!=0 && list != null) {
				map.put("list", list);
				if(print_radio.equals("xls")) {
					filename = product_master_audit_trail_reportservice.generate_product_Master_audit_trail_report(list, username,Long.valueOf(bean.getProduct_type()),empId);
					//map.put("path", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
					map.put("filename", filename);
				}
				
				if(print_radio.equals("pdf")) {
					filename = product_master_audit_trail_pdfservice.generate_product_Master_audit_trail_pdf(list, companyname, username, Long.valueOf(bean.getDivId()), Long.valueOf(bean.getSubcompany()), brand_str.toString(), Long.valueOf(bean.getProd_id()), Long.valueOf(bean.getProduct_type()), checkme, date,session,request);
					//map.put("path", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
					map.put("filename", filename);
				}
			}
		}catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getDetailForSupplierMasterAudit")
	public Map<String, Object> getDetailForSupplierMasterAudit(HttpSession session) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<SG_d_parameters_details> supplierTypeList = null;
		List<FinYear> finYearList = null;
		try {
			String subCompName = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			supplierTypeList=this.parameterService.getSupplierTypeForSupplierEntry();
		//	finYearList=this.auditTrailService.getConcatedFinYearListForAuditReport();
			finYearList=financialYearService.getAllFinancialYears(subCompName);
			String from="";
			String to="";
			LinkedHashMap<String, String> finYears = new LinkedHashMap<String, String>();
			
			for (FinYear f : finYearList) {
				from = f.getFin_start_date().toString().substring(8,10) + "-" + f.getFin_start_date().toString().substring(5,7) + 
						"-" + f.getFin_start_date().toString().substring(0,4);
				to   = f.getFin_end_date().toString().substring(8,10) + "-" + f.getFin_end_date().toString().substring(5,7) + 
						"-" + f.getFin_end_date().toString().substring(0,4);
				finYears.put(f.getFin_year(), from + " To " + to);
			}
			
			map.put("supplierTypeList", supplierTypeList);
			map.put("finYearList", finYearList);
			map.put("finYears", finYears);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
	}
	
	
	@PostMapping("/Supplier-Master-Download")
	public Map<String,Object> getSupplier_Master_Report(HttpSession session,@RequestBody AuditTrailBean atb,HttpServletRequest request){
		System.out.println(atb.getSupplierType());
		System.out.println(atb.getFinYear());
		System.out.println(atb.getFromDate());
		System.out.println(atb.getToDate());
		
		
		Map<String,Object>map = new HashMap<>();
		List<SupplierMasterModel> list = null;
		String filename;
		String print_radio =atb.getPdfExcel();
		String username = "abc";
		
		String fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()),
				tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate());
		
		System.out.println("from date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()));
		System.out.println("to date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate()));
		
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		
		try {
			AuditTrailBean bean = new AuditTrailBean();
//			bean.setSuptype("0");
//			bean.setFrmdt("01-11-2019");
//			bean.setTodt("30-10-2020");
			bean.setSuptype(atb.getSupplierType());
			bean.setFrmdt(fdate);
			bean.setTodt(tdate);
			
			list = auditTrailService.getSupplierMaster_Reportdata(bean);
			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);

				if(list.size()!=0 && list != null) {
				map.put("list", list);
					if(print_radio.equals("xls")) {
						
						filename = suppliermasterdownload_report_service.GenerateSupplierMasterDowloadReport(list, bean, username,empId);
						//Reportbean passed for fromdate and todate
						map.put("filename", filename);
					}
					
					if(print_radio.equals("pdf")) {
						filename = suppliermasterdownload_pdf_service.GenerateSupplierMasterDowloadPDF(list, bean, username, companyname,session,request);
					map.put("filename",filename);
					}
				}
		}catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
	}
	
	
	

	//HQ Master Audit Trail
		@PostMapping("/Hq-audit-trail-Pdf")
		public Map<String,Object> PrintHqaudit_trail_pdf(HttpSession session,@RequestBody AuditTrailBean atb,HttpServletRequest request){
			System.out.println("div id :"+atb.getDivision());
			
			List<HqMasterAuditTrailModel> list = null;
			Map<String,Object> map = new HashMap<>();
			String print_radio = atb.getPdfExcel();
			
			try {
				String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
				String companyName = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
				String username = "PFZ-DIST";
				String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
				AuditTrailBean bean = new AuditTrailBean();
				
				//bean.setDivId("0");
				bean.setDivId(atb.getDivision());
				
				list = auditTrailService.getHqmasteraudittraildata(bean);
				map.put("list", list);
				
//				if(list!=null && list.size()>0){
//					map.put("isData", true);
//					map.put("filename", this.hqmasteraudit_trail_printpdf_service.generateHq_Master_audit_trail_print_pdf(list, username, companyName));
//				}else {
//					map.put("isData", false);
//					map.put("filename", "");
//				}
				if(list!=null && list.size()>0){
					map.put("list", list);
					if(print_radio.equals("pdf")) {
					map.put("isData", true);
					map.put("filename", this.hqmasteraudit_trail_printpdf_service.generateHq_Master_audit_trail_print_pdf(list, username, companyName,session,request));
					}
					
					if(print_radio.equals("xls")) {
						map.put("isData", true);
						map.put("filename", this.hqmasteraudittrail_report_service.Generate_HqMasterAuditTrail_Report(list, username, companyName,empId));
					}
					
				}else {
					map.put("isData", false);
					map.put("filename", "");
				}
			}
			catch(Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
			
		}
		
		//Batch Master Audit Trail
		@GetMapping("/getDetailForBatchMasterAudit")
		public Map<String, Object> getDetailForBatchMasterAudit(HttpSession session) throws Exception {
			Map<String,Object> map=new HashMap<String, Object>();
			List<SmpItem> itemList = null;
			List<Location> locationList = null;
			List<FinYear> finYearList = null;
			try {
				String subCompName = session.getServletContext().getAttribute(COMPANY_CODE).toString();
				itemList=this.productMasterService.getAllActiveItemList();
				locationList=this.locationService.getAllSubCompanies();
		//		finYearList=this.auditTrailService.getConcatedFinYearListForAuditReport();
				finYearList=financialYearService.getAllFinancialYears(subCompName);
				String from="";
				String to="";
				LinkedHashMap<String, String> finYears = new LinkedHashMap<String, String>();
				
				for (FinYear f : finYearList) {
					from = f.getFin_start_date().toString().substring(8,10) + "-" + f.getFin_start_date().toString().substring(5,7) + 
							"-" + f.getFin_start_date().toString().substring(0,4);
					to   = f.getFin_end_date().toString().substring(8,10) + "-" + f.getFin_end_date().toString().substring(5,7) + 
							"-" + f.getFin_end_date().toString().substring(0,4);
					finYears.put(f.getFin_year(), from + " To " + to);
				}
				map.put("itemList", itemList);
				map.put("locationList", locationList);
				map.put("finYearList", finYearList);
				map.put("finYears", finYears);
			}
			catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		
			return map;
		}

		@PostMapping("/get-BatchMaster-audit-Trail-download")
		public Map<String,Object> getBatch_Master_AuditTrail_Report(HttpSession session,@RequestBody AuditTrailBean atb,HttpServletRequest request){
			System.out.println(atb.getItem());
			System.out.println(atb.getLocation());
			System.out.println(atb.getFinYear());
			
			String fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()),
					tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate());
			
			System.out.println("from date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()));
			System.out.println("to date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate()));
			
			Map<String,Object>map = new HashMap<>();
			List<BatchMasterAuditTrailModel> list = null;
			String print_radio = atb.getPdfExcel();
			String filename;
			String username = atb.getUsername();//added
			
			try {
				String companyname =  ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
				AuditTrailBean bean = new AuditTrailBean();
				bean.setProd_id(atb.getItem());
				bean.setLocId(atb.getLocation());
				bean.setFrmdt(fdate);
				bean.setTodt(tdate);
//				bean.setProd_id("0");
//				bean.setLocId("1");
//				bean.setFrmdt("10-04-2020");
//				bean.setTodt("29-04-2020");
				
				list = auditTrailService.getBatchMasterData(bean);
				
				if(list.size()!=0 && list != null) {
					map.put("list", list);
					
					if(print_radio.equals("xls")) {
						String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
						filename = batch_master_audittrail_report_service.GenerateBatch_Master_Audit_Trail_Report(list, bean.getFrmdt(), bean.getTodt(), username,empId);
						//map.put("path",SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
						map.put("filename",filename);
					}
					
					if(print_radio.equals("pdf")) {
						filename= batch_master_audittrail_pdf_service.GenerateBatch_Master_Audit_Trail_PDF(list, bean.getFrmdt(), bean.getTodt(), username, companyname,session,request);
						//map.put("path",SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
						map.put("filename",filename);
					}
				}
			}
			catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			
			return map;
		}
		
		//GRN Audit Report
		@GetMapping("/getDetailForGrnAudit")
		public Map<String, Object> getDetailForGrnAudit(HttpSession session) throws Exception {
			Map<String,Object> map=new HashMap<String, Object>();
			List<FinYear> finYearList = null;
			try {
				String subCompName = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			//	finYearList=this.auditTrailService.getConcatedFinYearListForAuditReport();
				finYearList=financialYearService.getAllFinancialYears(subCompName);
				String from="";
				String to="";
				LinkedHashMap<String, String> finYears = new LinkedHashMap<String, String>();
				
				for (FinYear f : finYearList) {
					from = f.getFin_start_date().toString().substring(8,10) + "-" + f.getFin_start_date().toString().substring(5,7) + 
							"-" + f.getFin_start_date().toString().substring(0,4);
					to   = f.getFin_end_date().toString().substring(8,10) + "-" + f.getFin_end_date().toString().substring(5,7) + 
							"-" + f.getFin_end_date().toString().substring(0,4);
					finYears.put(f.getFin_year(), from + " To " + to);
				}
				
				map.put("finYearList", finYearList);
				map.put("finYears", finYears);
			}
			catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		
			return map;
		}
		
		
		@PostMapping("/grnAuditTrialDownload")
		public Map<String, Object> grnAuditTrialDownload(HttpSession session,@RequestBody AuditTrailBean atb,HttpServletRequest request) {
			
//			String fdate = null,tdate = null;
//			try{
//				SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
//				Date date1 = sdf3.parse(atb.getFromDate());
//				 fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date1);
//				System.out.println(fdate);
//				
//			
//				Date date2 = sdf3.parse(atb.getToDate());
//				 tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(date2);
//				System.out.println(tdate);
//			}catch (Exception e) {
//				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			}
			String fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()),
					tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate());
			
			System.out.println("from date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()));
			System.out.println("to date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate()));
			
			
			Map<String, Object> map = new HashMap<>();
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String username =atb.getUsername();
//			String print_radio = "xls";
//			String fromdate= "01-11-2019";
//			String todate="30-10-2020";
//			String date="27-04-2020";
			String print_radio = atb.getPdfExcel();
			String fromdate= fdate;
			String todate=tdate;
			String date=new Date().toString();
			
			
			System.out.println("data "+fdate+".."+tdate+".."+date);
			
			
			List<GrnAuditTrailBean> list = null;
	/*		String userId="E00040"; // get it from session
			bean.setUserId(userId);*/
			try {
				AuditTrailBean bean = new AuditTrailBean();
				bean.setUserId(atb.getEmpId());
				bean.setFromDate(atb.getFromDate());
				bean.setToDate(atb.getToDate());
				bean.setApproval(atb.getApproval());
		//		bean.setFinyearflag(atb.getFinyearflag());
				
				if(atb.getFinyearflag().equals("CURRENT")) {
					list=auditTrailService.getgrnAuditDownloadService(bean);
				}
				else {
					list=auditTrailService.getgrnAuditDownloadServicePrevious(bean);
				}
				  if(list != null && !list.isEmpty()) { 
					  String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
					  if(print_radio.equals("xls")) {
					  String filename =grnAuditDownloadService.GenerateAuditDownloadReport(bean, list, companyname, username,fromdate,todate,empId);
					  //map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" +filename);
					  map.put("filename",filename);
					  }
					  else
					  {
						  String filename =grnAuditDownloadService.GenerateAuditDownloadPdf(bean, list, companyname, username, date, fromdate, todate,session,request);
					  //map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" +filename);
					  map.put("filename",filename);
					  }
				}
				 map.put("list", list);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		
		//Dispatch Audit Trail Report

		@PostMapping("/dispatchAuditTrialDownload")
		public Map<String, Object> dispatchAuditTrialDownload(HttpSession session,@RequestBody AuditTrailBean atb,HttpServletRequest request) {
			String fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()),
					tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate());
			
			System.out.println("from date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()));
			System.out.println("to date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate()));
			
			Map<String, Object> map = new HashMap<>();
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String username =atb.getUsername();
//			String print_radio = "xls";
//			String fromdate= "01-11-2019";
//			String todate="30-10-2020";
//			String date="27-04-2020";
			
			String print_radio = atb.getPdfExcel();
			String fromdate= fdate;
			String todate=tdate;
			String date=new Date().toString();
			
			List<DispatchAuditTrailBean> list = null;
		/*		String userId="E00040"; // get it from session
				bean.setUserId(userId);*/
			try {
				AuditTrailBean bean = new AuditTrailBean();
				bean.setUserId(atb.getEmpId());
				bean.setFromDate(atb.getFromDate());
				bean.setToDate(atb.getToDate());
				if(atb.getFinyearflag().equals("CURRENT")) {
					list=auditTrailService.getDispatchAuditDownloadService(bean);
				}
				else {
					list=auditTrailService.getDispatchAuditDownloadServicePrevious(bean);
				}
				  if(list != null && !list.isEmpty()) { 
					  String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
					  if(print_radio.equals("xls")) {
					  String filename =dispatchAuditDownloadService.GenerateDispatchAuditDownload_Report(bean, list, companyname,
							  username, fromdate, todate,empId);
					  map.put("filename",filename);
				  }
				  else
				  {
					  String filename =dispatchAuditDownloadService.GenerateDispatchAuditDownload_Pdf(bean, list, companyname, username, date,fromdate,  todate,session,request);
					  map.put("filename",filename);
				  }
				}
				map.put("list", list);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		

		@PostMapping("/printIaaAuditTrialPdf")
		public Map<String,Object> printIaaAuditTrialPdf(HttpSession session,@RequestBody AuditTrailBean atb,HttpServletRequest request) throws Exception{
			
			
			
//			System.out.println("from date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getFromDate()));
//			System.out.println("to date "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getToDate()));
//			
			String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			String username=atb.getUsername();
			
			String fdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getStartdate());
			String tdate = MedicoResources.convertUtilDateToString_DD_MM_YYYY(atb.getEnddate());
			
			String frm_date=fdate;
			String to_date=tdate;		
			
			String print_radio = atb.getPdfExcel();
			
			Map<String,Object> map=new HashMap<String, Object>();
			/*
			 * String locId=pb.getLocationId();
			 * String frmStkno=pb.getFromIAA(); String
			 * toStkno=pb.getToIAA();
			 */
			
			try{
				List<ViewDownloadIaaAuditTrail> list = this.auditTrailService.getViewDownloadIaaAuditTrialData(atb);
				if(list!=null && list.size()>0){
					  if(print_radio.equals("xls")) {
						  String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
							map.put("isData", true);
							map.put("filename",this.iaaaudittrail_excel_service.generateIaaAuditExcel(list, companyname, username,
									frm_date,to_date,empId));
					  }
					  else
					  {
					map.put("isData", true);
					map.put("filename",this.iaaAuditTrialService.generateIaaAuditTrialPrint(list, companyname, username,frm_date,to_date,session,request));
			
					  }
				}
				else{
					map.put("isData", false);
					map.put("filename", "");
				}
				map.put("list", list);
				map.put("isError", false);
			}catch(Exception e){
				try{
					map.put("isError", true);
				}catch(Exception e1){
					//ignore
				}
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
				return map;
			}
		
		
		@PostMapping("/getAuditLogData")
		public Map<String,Object> getAuditLogData(HttpSession session,
				@RequestParam("startdate") String startDate,
				@RequestParam("endDate") String endDate,
				@RequestParam("finYear") String finYear,
				@RequestParam("finYearFlag") String finYearFlag) throws ParseException{
			Map<String,Object> map = null;
			List<Audit_log> list = null;
			String filename = null;
			//String stDtConverted = sdf.format(sdfparse.parse(startDate));
			//String endDtConverted = sdf.format(sdfparse.parse(endDate));
			try {
				String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
				map = new HashMap<String,Object>();
				System.out.println("startDate::"+startDate+":::::endDate::::"+endDate+":::::finYear::::::"+finYear+":::::finFlag::::"+finYearFlag);
				list = this.auditTrailService.getAuditLogData( startDate, endDate, finYear, finYearFlag);
				if(list!=null && !list.isEmpty()) {
						filename = this.iaaaudittrail_excel_service.generateAuditLogExcel(list, companyname, startDate, endDate);
						if(filename!=null) {
							map.put("filename",filename);
							//map.put("dataList",list);
						}
						else {
							map.put("msg","Error While creating excel");
						}
					}
				else {
					map.put("msg","No Data Found");
				}
			}
			catch(Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				map.put("msg","Unknown Error Occured");
			}
			return map;
		}
}

