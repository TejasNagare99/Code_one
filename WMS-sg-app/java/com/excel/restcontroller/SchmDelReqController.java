package com.excel.restcontroller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.ArticleSchemeBeanForSave;
import com.excel.bean.ArticleSchemeDetailsBean;
import com.excel.model.ArticleSchemeRequestHeader;
import com.excel.model.CustomerMaster;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sub_Company;
import com.excel.model.Transporter_master;
import com.excel.repository.CustomerMasterRepo;
import com.excel.repository.LrEntryRepository;
import com.excel.service.AllocationService;
import com.excel.service.ArticleSchemeDeliveryReqService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.UserMasterService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping(path = "/rest/article_scheme", produces = "application/json")
public class SchmDelReqController implements MedicoConstants {

	@Autowired
	private ArticleSchemeDeliveryReqService artSchmDelServi;
	@Autowired
	private AllocationService allocationService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private LrEntryRepository lrEntryRepo;
	@Autowired
	private CustomerMasterRepo custMastRepo;
	@Autowired
	private LocationService locService;
	@Autowired private UserMasterService userMasterService;
	
	private static final Logger logger = LogManager.getLogger(SchmDelReqController.class);

	// method only to be used when testing
//	@Autowired private DispatchService disp_service;
//	@GetMapping(path="/call_dsp_appr_manual")
//	public void approve_disp() throws Exception {
//		//dsp id // supervisor id //comp cd // status
//		this.disp_service.finalDispatchApproval(8L,"E00560", "PFZ", "A", "");
//		//logger.info("Hello World from log file");
//	}

	@GetMapping(path = "/getOnLoadForArtScmDel")
	public ResponseEntity<Map<String, Object>> getSubCompanyById(@RequestParam("sub_comp_id") Long sub_comp_id,
			@RequestParam("loc_id") Long loc_id, @RequestParam("finyear") String finyear) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Sub_Company subComp = this.artSchmDelServi.getSubCompanyById(sub_comp_id);
			if (subComp == null)
				throw new Exception("Invalid Sub Company Code");
			else
				map.put("sub_company", subComp);

			// List<CustomerMaster> custList =
			// this.artSchmDelServi.getCustomersForLocation(loc_id, null);
			List<CustomerMaster> custList = this.artSchmDelServi.getCustomersByShipToCodeAndNameNotInCustLock(false,
					loc_id, null, (sub_comp_id.equals(1L) ? "PFZ" : "PPL"));
			if (custList == null || custList.size() == 0)
				throw new Exception("No Customers Found");
			else
				map.put("cust_list", custList);

			List<ArticleSchemeDetailsBean> sch_list = this.artSchmDelServi
					.getAllSchemeDetailsForEntry(subComp.getSubcomp_code().trim(), loc_id, finyear);
			if (sch_list == null || sch_list.size() == 0)
				throw new Exception("No Schemes Found");
			else
				map.put("scheme_list", sch_list);

			Period allocationPeriod = this.allocationService.getAllocationMonthofUse();
			if (allocationPeriod == null)
				throw new Exception("Allocation Period Not Found");
			else
				map.put("allocationPeriod", allocationPeriod);

			List<Transporter_master> transporters = this.lrEntryRepo.gettransportermasterForLocation(loc_id);
			if (transporters == null) {
				throw new Exception("Transporters Not Found");
			} else {
				map.put("transporterList", transporters);
			}

			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : onload article scheme UI" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/getSchemeDetails")
	public ResponseEntity<List<ArticleSchemeDetailsBean>> getSchemeDetails(
			@RequestParam("sub_comp_code") String sub_comp_cd, @RequestParam("loc_id") Long loc_id,
			@RequestParam("sap_inv_date") String sap_inv_date) {
		try {
			List<ArticleSchemeDetailsBean> list = this.artSchmDelServi
					.getArticleSchemeDetailsForEntry(sub_comp_cd.trim(), sap_inv_date, loc_id);
			if (list == null || list.size() == 0) {
				throw new Exception();
			} else {
				return ResponseEntity.ok(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : fetching scheme details" + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<ArticleSchemeRequestHeader> saveSchemeDeliveryRequest(
			@RequestBody ArticleSchemeBeanForSave articleSchemeBeanForSave, HttpSession session,
			HttpServletRequest request) {
		
		
		
		logger.info("articleSchemeBeanForSave:::" + articleSchemeBeanForSave.toString());
		String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		articleSchemeBeanForSave.setCompanycd(companycode);
		articleSchemeBeanForSave.setIpAddr(request.getRemoteAddr());
		String empId = null;
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		articleSchemeBeanForSave.setUserId(empId);
		
		try {
			System.out.println("articleSchemeBeanForSave::" + articleSchemeBeanForSave.toString());
			ArticleSchemeRequestHeader scmHdr = this.artSchmDelServi
					.saveArticleSchemeDelivery(articleSchemeBeanForSave);
			if (scmHdr == null) {
				throw new Exception("Error while saving record");
			} else {
				return ResponseEntity.status(HttpStatus.CREATED).body(scmHdr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : saving article scheme " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@GetMapping("/get-unconfirmed-schreq-headers")
	public ResponseEntity<List<ArticleSchemeRequestHeader>> getUnConfirmedReqHdrForModify(
			@RequestParam("locId") Long locId,HttpServletRequest request) {
		try {
			
			String userId = this.userMasterService.getEmployeeIdFromRequest(request);
			List<ArticleSchemeRequestHeader> list = this.artSchmDelServi.getUnConfirmedReqHdrs(locId, userId);
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : fetching schemes in draft mode " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@GetMapping("/get-details_for_modify")
	public ResponseEntity<Map<String, Object>> getDetailsForModification(@RequestParam("req_id") Long req_id,
			@RequestParam("sub_comp_cd") String sub_comp_cd, @RequestParam("loc_id") Long loc_id,
			@RequestParam("userid") String userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ArticleSchemeRequestHeader artSchReqHdr = this.artSchmDelServi.getHeaderById(req_id);
			 List<ArticleSchemeDetailsBean> 	beansList = this.artSchmDelServi.get_duplecate_invoice(artSchReqHdr.getArticle_req_id().toString(),	artSchReqHdr.getInvoice_no());	
			
			// get cutname by cust id anduse it in filter
			CustomerMaster cust = this.custMastRepo.getCustomerById(artSchReqHdr.getCust_id());
			List<CustomerMaster> custList = new ArrayList<CustomerMaster>();
			custList.add(cust);
//					this.artSchmDelServi.getCustomersByShipToCodeAndNameNotInCustLock(true,
//					loc_id, cust.getCust_code_shipto().trim());
			boolean isCustFree = this.artSchmDelServi.lockCustForArticleReqEntry(loc_id, artSchReqHdr.getCust_id(),
					userid);
			map.put("isCustFree", isCustFree);
			if (isCustFree) {
				List<ArticleSchemeDetailsBean> list = this.artSchmDelServi.getAllEnteredDetailsForModify(loc_id,
						sub_comp_cd, req_id);
				if (artSchReqHdr == null || list == null || list.size() == 0 || custList == null)
					throw new Exception("No Data Found For Editing");
				else {
					map.put("ART_SHC_HDR", artSchReqHdr);
					map.put("ENTERED_DATA", list);
					map.put("CUSTOMERS", custList);
					map.put("beansList", beansList);
				}

			}
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : fetching schemes details for modify " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<Boolean> deleteDetails(@RequestParam("req_id") Long req_id) {
		try {
			this.artSchmDelServi.delete(req_id);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : scheme deletion " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@GetMapping("/check-duplicate-invoice")
	public ResponseEntity<Map<String, Object>> checkIfInvoiceIsDuplicate(@RequestParam("invoice_no") String invoice_no,
			@RequestParam("loc_id") Long loc_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		 List<ArticleSchemeDetailsBean> scheme_data=new ArrayList<>();
		Boolean exists_in_sap_sales = false;
		try {
			Boolean isValid = this.artSchmDelServi.checkIfInvoiceExists(invoice_no, loc_id);
			// check also if the same person has entered this invoice // bring empid

			List<SG_d_parameters_details> params = this.parameterService
					.getParaDetailsByParaType("VALIDATE_SAP_INVOICE");
			map.put("validateSapInvoice", params.get(0).getSgprmdet_text1().trim());
			map.put("isValid", isValid);
			
			if (isValid && params.get(0).getSgprmdet_text1().trim().equalsIgnoreCase("Y")) {
				// check if sales data has this product
				exists_in_sap_sales = this.artSchmDelServi.checkIfInvoiceExistsInSalesData(loc_id, invoice_no);
				// get the sap sales invoice products and quantities
				map.put("invoiceData", this.artSchmDelServi.getSalesProductDataFromInvoice(invoice_no));
			}
			
			if (!isValid) {
				scheme_data	=this.artSchmDelServi.get_Scheme_detail(invoice_no);
				map.put("scheme_data", scheme_data);
			}
			
			map.put("existsInSapSales", exists_in_sap_sales);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : check duplicate invoices " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@PostMapping("/final-approve-selected")
	public ResponseEntity<Map<String, String>> appproveArticleSchemeReqHdr(
			@RequestParam("req_ids_list") List<Long> req_ids_list, @RequestParam("action") String action,
			@RequestParam("approver_id") String last_approved_by, HttpServletRequest request, HttpSession session) {
		Map<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			String company_code = (String) session.getAttribute(COMPANY_CODE);
			map = this.artSchmDelServi.approveArticelSchemes(req_ids_list, action, last_approved_by, company_code,
					request.getRemoteAddr());
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : approve selected " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@PostMapping("/send-for-approval")
	public ResponseEntity<Map<String, String>> appproveArticleSchemeReqHdr(
			@RequestParam("req_nos_list") List<String> req_nos_list,
			HttpServletRequest request, HttpSession session) {
		Map<String, String> map = null;
		try {
			System.out.println("Selected Req Nos");
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			req_nos_list.forEach(req_no -> System.out.println("\n" + req_no.trim()));
			map = new HashMap<String, String>();
			map = this.artSchmDelServi.sendMultipeForApproval(req_nos_list, empId, "", request, session);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : sending for approval " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@GetMapping("/get-alloc-req-nos-for-auto-dispatch")
	public ResponseEntity<List<ArticleSchemeRequestHeader>> getArticleRequestNosForAutoDispatch(
			@RequestParam("locId") Long locId, @RequestParam("divId") Long divId, 	HttpServletRequest request,
			@RequestParam("finYear") String finYear) {
		try {
			
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			Period allocationPeriod = this.allocationService.getAllocationMonthofUse();
			List<ArticleSchemeRequestHeader> list = this.artSchmDelServi.getArtReqNosForAutoDispatch(finYear,
					allocationPeriod.getPeriod_code(), divId, locId, empId);
			if (list == null || list.size() == 0) {
				throw new Exception("No Data Found");
			} else {
				return ResponseEntity.ok(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : fetch article delivery alloc req nos for auto dispatch for article delivery "
					+ e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@GetMapping("/get-data-for-approvals-view")
	public ResponseEntity<Map<String, Object>> getDataForApprovals(
			@RequestParam("art_sch_req_hdr_id") Long art_sch_req_hdr_id, @RequestParam("finYear") String finYear,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ArticleSchemeRequestHeader art_req_hdr = this.artSchmDelServi.getHeaderById(art_sch_req_hdr_id);
			List<ArticleSchemeDetailsBean> list = this.artSchmDelServi.getDataForApprovals(art_sch_req_hdr_id,
					art_req_hdr.getLoc_id(), art_req_hdr.getSub_comp_cd(), "F");
			CustomerMaster cm = this.artSchmDelServi.getCustomerById(art_req_hdr.getCust_id());
			if (cm == null || list == null || list.size() == 0) {
				throw new Exception("No Data Found");
			}
			map.put("list", list);
			map.put("customer", cm);
			map.put("hdr_obj", art_req_hdr);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : get approval view data for article delivery  " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
	}

	@GetMapping("/release-all")
	public ResponseEntity<Void> releaseAllCustLockByUserId( HttpServletRequest request) {
		String empId="";
		try {
			
			 empId = this.userMasterService.getEmployeeIdFromRequest(request);
			this.artSchmDelServi.unlockAllCustByUserId(empId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			logger.info("Error occurred : release customer locks for user : " + empId + " with error : "
					+ e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@PostMapping("/lock-cust-for-entry")
	public ResponseEntity<Boolean> locCustomerForEntry(@RequestParam("loc_id") Long loc_id,
			@RequestParam("cust_id") Long cust_id,	HttpServletRequest request) {
		String empId="";
		try {
			
			 empId = this.userMasterService.getEmployeeIdFromRequest(request);
			boolean isCustFree = this.artSchmDelServi.lockCustForArticleReqEntry(loc_id, cust_id, empId);
			return ResponseEntity.ok().body(isCustFree);
		} catch (Exception e) {
			logger.info("Error occurred : loc customer for user : " + empId + " with error : " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/search-customers")
	public ResponseEntity<List<CustomerMaster>> getCustomersByLocAndFilter(@RequestParam("isAllLocs") boolean isAllLocs,
			@RequestParam("loc_id") Long loc_id, @RequestParam("filter") String filter) {
		try {
			// get subcompany by location
			Location location = this.locService.getObjectById(loc_id);
			List<CustomerMaster> custList = this.artSchmDelServi.getCustomersByShipToCodeAndNameNotInCustLock(isAllLocs,
					loc_id, filter, (location.getLoc_subcomp_id().equals(1L) ? "PFZ" : "PPL"));
			if (custList == null || custList.size() == 0)
				throw new Exception("No Customers Found");
			else
				return ResponseEntity.ok(custList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : get customers by shiptocode,loc and filter" + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@Scheduled(cron = "0 0 7 * * *") // run every morning at 7 am
	@PostMapping("/push-dispatch-to-194R")
	public ResponseEntity<Void> generate_194R_from_dispatch() {
		try {
			System.out.println("Dispatch Push To 194R started at :::>" + new Date());
			logger.info("Dispatch Push To 194R started at :::>" + new Date());

			this.artSchmDelServi.dispatch_to_194R_push();

			logger.info("Dispatch Push To 194R completed at :::>" + new Date());
			System.out.println("Dispatch Push To 194R completed at :::>" + new Date());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error occurred : push dispatches to 194R" + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@GetMapping("/get-distance-by-chln")
	public ResponseEntity<BigDecimal> getDistByChalnNo(@RequestParam Long dsp_id) {
		try {
			BigDecimal dist = this.artSchmDelServi.getDistanceForChallan(dsp_id);
			return ResponseEntity.ok(dist);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}

	@PostMapping("/update-distance")
	public ResponseEntity<Void> updateDistForEwb(@RequestParam Long dsp_id, @RequestParam BigDecimal entered_distance) {
		try {
			this.artSchmDelServi.updateArtReqDistance(dsp_id, entered_distance);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	
}
