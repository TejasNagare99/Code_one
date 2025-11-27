package com.excel.restcontroller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.GRNBean;
import com.excel.model.Company;
import com.excel.model.FieldStaff;
import com.excel.model.Location;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SamplProdGroup;
import com.excel.model.V_Grn_Details;
import com.excel.repository.CustomerMasterRepo;
import com.excel.repository.GrnRepository;
import com.excel.service.BatchMasterService;
import com.excel.service.BinMasterService;
import com.excel.service.DivisionMasterService;
import com.excel.service.EmailTranWiseService;
import com.excel.service.FieldStaffService;
import com.excel.service.GRNService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.ProductMasterService;
import com.excel.service.SamplProdGroupService;
import com.excel.service.SupplierService;
import com.excel.service.UserMasterService;
import com.excel.service.V_GRN_HeaderService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class GrnRestController implements MedicoConstants {
	
	@Autowired LocationService locationService;
	@Autowired DivisionMasterService divisionmasterService;
	@Autowired SamplProdGroupService samplprodgroupservice;
	@Autowired BinMasterService binMasterService;
	@Autowired UserMasterService userMasterService;
	@Autowired ParameterService parameterService;
	@Autowired SupplierService supplierService;
	@Autowired ProductMasterService productMasterService;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired GRNService grnService;
	@Autowired FieldStaffService fieldStaffService;
	@Autowired BatchMasterService batchMasterService;
	@Autowired V_GRN_HeaderService v_GRN_HeaderService;
	@Autowired GrnRepository grnRepository;
	@Autowired EmailTranWiseService emailtranwiseservice;
	@Autowired CustomerMasterRepo custMasterRepo;
	

	@GetMapping("/get-data-for-grn-entry")
	public Map<String, Object> getDataForGrnEntry(
			@RequestParam String trantype,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			//String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			map.put("purchase_rate_entry_indicator",this.parameterService.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN"));
			map.put("recLocName", location.getLoc_nm());
			map.put("subCompId", location.getLoc_subcomp_id());
			map.put("locId", location.getLoc_id());
			map.put("divistionList", this.divisionmasterService.getStandardDivisionList());
			
			map.put("binMasterList", this.binMasterService.getBinListByLocId(location.getLoc_id()));
			map.put("allowInd", this.userMasterService.getAllowBatchCreateInd(empId));
			String emailReqInd = this.parameterService.getSrtAndEmailReqInd("TRANWISE_EMAIL_REQD");
			map.put("EmailReqInd",emailReqInd);
			map.put("DefaultMailList", this.parameterService.getDefaultParameterEmailsByTranType(trantype));
			Date today = new Date();
			map.put("GrnRefNoInd",this.parameterService.getGrnRefNoInd());
			/*
			Long grnDate = 0L;
			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date taskDate = MedicoResources.convert_DD_MM_YYYY_toDate(period.getPeriod_end_date());
			System.out.println("today"+ today);
			System.out.println("taskDate"+ taskDate);
			if (today.compareTo(taskDate) > 0) {
				grnDate = taskDate.getTime();
			    System.out.println("INSIDE IFFFF::::::" + grnDate);
			} else {
				grnDate = today.getTime();
			    System.out.println("INSIDE ELSEEEE::::::" + grnDate);
			}*/
			
			Company company=(Company) session.getServletContext().getAttribute(COMPANY_DATA);
			if(company.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				//grnTypes
				List<SG_d_parameters_details> grn_types = this.parameterService.getGrnTypes();
				//01	Normal GRN
				//02	Returns from Stockist
				//06	Returns from Transporter/Courier
				grn_types = grn_types.stream().filter(elem -> elem.getSgprmdet_nm().equalsIgnoreCase("01")
						|| elem.getSgprmdet_nm().equalsIgnoreCase("02") || elem.getSgprmdet_nm().equalsIgnoreCase("06"))
						.collect(Collectors.toList());
				map.put("grnTypes", grn_types);
				
				//filter the stockTypes
				List<SG_d_parameters_details> stock_types = this.parameterService.getStockTypes();
				//01	In Dispatchable condition(Billable)
				stock_types = stock_types.stream().filter(elem -> elem.getSgprmdet_nm().equals("01"))
						.collect(Collectors.toList());
				map.put("stockTypes", stock_types);
				
				//filter the sampleProductList 
				List<SamplProdGroup> sampleProductList = this.samplprodgroupservice.getAllSamplProducts();
				//sa_prod_group-257	Article
				sampleProductList = sampleProductList.stream().filter(elem-> elem.getSa_prod_group().equals(257L))
						.collect(Collectors.toList());
				map.put("sampleProductList",sampleProductList);
			}
			else {
				map.put("grnTypes", this.parameterService.getGrnTypes());
				map.put("stockTypes", this.parameterService.getStockTypes());
				map.put("sampleProductList", this.samplprodgroupservice.getAllSamplProducts());
				map.put("VehicleReceivedTime", this.parameterService.getVehicleRecdTime());
			}
			
			
			map.put("grnDate", today.getTime());
			map.put("grnMaxDate", today.getTime());
			map.put("lrDate", today.getTime());
			map.put("PODate", today.getTime());
			
			map.put("productTypes", this.parameterService.getProductTypeList());
			

		} catch (Exception e) {
			
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-supplier-types-by-grn-type")
	public Map<String, Object> getSupplierTypesbyGrnType(@RequestParam String grnType, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("supplierTypes", this.parameterService.getSupplierTypeByGrnType(grnType));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-suppliers-by-supplier-type")
	public Map<String, Object> getSupplierBySupplierTypes(@RequestParam String supplierType, @RequestParam Long locId, @RequestParam Long subCompId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("supplierType::"+supplierType);
		System.out.println("locId::"+locId);
		System.out.println("subCompId::"+subCompId);
		try {
			map.put("suppliers", this.supplierService.getSuppliersBySupplierType(supplierType, locId, subCompId));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-product-list-by-prod-type")
	public Map<String, Object> getProductListForGRN(@RequestParam Long subCompId, @RequestParam Long prodType, @RequestParam Long prodGroup, @RequestParam Long divId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			System.out.println( subCompId+ prodType+ prodGroup+ divId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			
//			if(companyCode.trim().equals("MDL")) {
//				map.put("products", this.productMasterService.getProductListForGRN_for_medley(companyCode.trim(), subCompId, prodType, prodGroup, divId));
//			}else {
				map.put("products", this.productMasterService.getProductListForGRN(companyCode.trim(), subCompId, prodType, prodGroup, divId));
//			}
				
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}	

	@GetMapping("/get-product-details-by-id")
	public Map<String, Object> getProductDetailsByProductId(
			@RequestParam Long prodId,
			@RequestParam Long locId,
			@RequestParam String stkType,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("stkType : "+stkType);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map.put("batchList", this.productMasterService.getProductDetailByProdId(companyCode, locId, prodId,stkType));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-grn")
	public Map<String, Object> saveGRN(@RequestBody GRNBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			java.util.Date utilDate = new java.util.Date();
	        System.out.println("Util date in Java : " + utilDate);
	 
	        // contains only date information without time
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        System.out.println("SQL date in Java : " + sqlDate);
			
			bean.setIpAddress(request.getRemoteAddr());
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode);
			Map<String, Object> grnMap = grnService.saveGRN(bean);
			map.put(DATA_SAVED, true);
			
			
			if(bean.isModifyHeader())
				map.put(RESPONSE_MESSAGE, "GRN updated Successfully! - "+ grnMap.get("grnNumber"));
			else if(bean.isModifyDetails())
				map.put(RESPONSE_MESSAGE, "GRN updated Successfully!-"+ grnMap.get("grnNumber"));
			else if(bean.getGrnId() != null && bean.getGrnId().compareTo(0L) > 0)
				map.put(RESPONSE_MESSAGE, "Record Added Successfully!-"+ grnMap.get("grnNumber"));
			else
				map.put(RESPONSE_MESSAGE, "GRN Saved Successfully! -" + grnMap.get("grnNumber"));
			map.put("headerId", grnMap.get("headerId"));
			map.put("grnDetailId", grnMap.get("grnDetailId"));
			map.put("grnNumber", grnMap.get("grnNumber"));
			map.put("grnValue", grnMap.get("grnValue"));
			if(bean.getSaveMode().equals(MODIFY_MODE))
				map.put("grnListForModify", this.v_GRN_HeaderService.getObjectById(bean.getGrnId()));

			//map.put("grnId", bean.getDcrId());
			//map.put("DCR_DETAIL_ID", bean.getRunningDcrDetailId());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}
	
	@GetMapping("/get-field-staff-by-div-id")
	public Map<String, Object> getFieldStaffByDivId(@RequestParam Long divId, @RequestParam boolean hasResigned, 
			@RequestParam Long loc_id,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<FieldStaff> fstaff_list = null;
			Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			if(company.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				fstaff_list = this.custMasterRepo.getCustomersForLocationAsFieldStaff(loc_id);
			}
			else {
				fstaff_list = this.fieldStaffService.getFieldStaffsByEmpIdAndHasResigned(divId, hasResigned, companyCode);
			}
			map.put("fieldList",fstaff_list );
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-grn-view-by-div-id")
	public Map<String, Object> getGrnViewByDivId(@RequestParam Long grnId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<V_Grn_Details> list =this.grnService.getGrnDetailViewByGrnId(grnId);//17597L
			map.put("grnList", list);
			map.put("prodGrpIds", this.productMasterService.getProdListByProdIds(list != null && list.size() > 0 ? list.stream().map(e -> new Long(e.getGrnd_prod_id())).collect(Collectors.toList()) : new ArrayList<>()));//17597L
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}	

	@GetMapping("/check-if-batch-exist-in-grn-detail")
	public Map<String, Object> checkIfBatchExistInGrndetails(@RequestParam Long grnId, @RequestParam Long prodId, Long batchId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("isBatchPresentInGrndetail", this.grnService.checkIfBatchExistsInGrnDetail(grnId, prodId, batchId));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/check-if-batch-number-exist")
	public Map<String, Object> getBatchByBatchNumber(@RequestParam String batchNumber,@RequestParam String locId,@RequestParam String prodId) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("batchNumber:"+batchNumber);
		System.out.println("locId:"+locId);
		System.out.println("prodId:"+prodId);
		try {
			//map.put("isBatchPresent", this.batchMasterService.getSmpBatchByBatchNumbar(batchNumber));
			map.put("isBatchPresent", batchMasterService.checkDuplicateRecordStk(Long.valueOf(locId), batchNumber, Long.valueOf(prodId)));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/delete-grn-detail-by-detail-id")
	public Map<String, Object> deleteGrnDetailById(@RequestParam Long grnDetailId, HttpSession session,	HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {	
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			map.put("grnValue", this.grnService.deleteGrnDetailById(grnDetailId, empId));
			map.put(RESPONSE_MESSAGE, "Record Deleted Successfully.");
		} catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}
	
	@GetMapping("/get-grn-list-for-modify")
	public Map<String, Object> getGrnListForModify(	HttpServletRequest request ,@RequestParam String fromDate, @RequestParam String toDate, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			map.put("grnListForModify", this.v_GRN_HeaderService.getGrnListByLocId(location.getLoc_id(), companyCode, empId, fromDate, toDate));
		} catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-user-div-by-fs-id")
	public Long getUSerDivByFsId(@RequestParam Long fsId, HttpSession session) {
		try {
			return this.fieldStaffService.getDivIdByFsId(fsId);
		} catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return null;
	}
	
	

	@GetMapping("/get-grn-approval-header-detail")
	public Map<String, Object> getGrnApprovalHeaderDetail( @RequestParam String empLoc
			, @RequestParam String userRole,HttpSession session, 
			HttpServletRequest request){
		Map<String, Object> map=new HashMap<>();
		try {
			
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map=grnService.getGrnApprovalHeaderDetail(empId, empLoc,userRole,companyCode);
			map.put("purchase_rate_entry_indicator",this.parameterService.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN"));
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;	
	}

	@GetMapping("/get-grn-approval-selected-detail")
	public Map<String,Object> getGrnApprovalSelectedDetail(@RequestParam long grnId){
		Map<String, Object> map=new HashMap<>();
		try {
			map=this.grnService.getGrnApprovalSelectedDetail(grnId);
			List<V_Grn_Details> list=(List<V_Grn_Details>) map.get("list");
			List<Long> detailIds=list.stream().map(value -> value.getGrndId()).collect(Collectors.toList());
			List<BigDecimal> detailQtys=list.stream().map(value -> value.getGrnd_qty()).collect(Collectors.toList());
			
			String remarks=this.grnService.getHeaderRemarks(grnId);
			map.put("detailIds", detailIds);
			map.put("detailQtys", detailQtys);
			map.put("remarks", remarks);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;	
	}
	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam MultipartFile file) throws Exception {
		String newFilePath = grnService.uploadFile(file);
		return newFilePath;
	}

	/*
	 * @GetMapping("/updateDetailOnSelfApproveOfGRN") public void
	 * updateDetailOnSelfApproveOfGRN(@RequestParam String empId,@RequestParam long
	 * grnId,@RequestParam String remark,HttpServletRequest request) { try {
	 * grnService.updateDetailOnSelfApproveOfGRN(empId, grnId, remark, request);; }
	 * catch (Exception e) { System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --; } }
	 */
	@GetMapping("/updateDetailOnSelfDiscardOfGRN")
	public void updateDetailOnSelfDiscardOfGRN(@RequestParam long grnId,
			HttpServletRequest request,HttpSession session) {
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			grnService.updateDetailOnSelfDiscardOfGRN("", grnId, "", request,companyCode);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
	
	
	@GetMapping("/viewGrnApprovalFile")
	public Object viewFile(@RequestParam long grnId) throws Exception {
		return this.grnService.viewFile(grnId);
	}

	@GetMapping("/confirm-grn")
	public  Map<String,Object> confirmGrn(@RequestParam Long grnId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			grnService.confirmGrn(grnId,companyCode);
			map.put(RESPONSE_MESSAGE, "GRN confirmed Successfully.");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE,"Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}
	    
	
	@GetMapping("/delete-grn-whole")
	public Map<String, Object> WholeGRNDelete(@RequestParam("grnId")Long grnId,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			map.put("GRN",grnService.DeleteWholeGrn(grnId,empId));
			map.put(RESPONSE_MESSAGE, "GRN Deleted Successfully");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put(RESPONSE_MESSAGE,"Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	} 
	
	@GetMapping("/getEnteredEmaillistForTran")
	public Map<String, Object> getDefaultEmaillistForTran(@RequestParam("trantype")String trantype,
			@RequestParam("finyear")String finyear,
			@RequestParam("tranId")String tranId,
			HttpSession session, HttpServletRequest request) {
			List<String> list = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
		 try {
			 
			 System.out.println("trantype : "+trantype);
			 System.out.println("finyear : "+finyear);
			 System.out.println("tranId : "+tranId);
			 
			list = emailtranwiseservice.getEmailListByTranType(trantype, finyear,Long.valueOf(tranId));
			map.put("EmailList",list);
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		 }
		return map;
	}
	
//	@GetMapping("/getDefaultEmaillistForTran")
//	public Map<String, Object> getDefaultEmaillistForTran(@RequestParam("trantype")String trantype,
//			HttpSession session, HttpServletRequest request) {
//		 try {
//			 
//		 }
//		 catch (Exception e) {
//			// TODO: handle exception
//			 System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		 }
//		return null;
//	}
	
	
	
	
	//changess---abhishekg
	
	//<-------------changesss start------------>
	
	
	@PostMapping("/stk-trf-file-upload")
	public Map<String, Object> stockTransferUploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("auth") String auth, @RequestParam("currentLocation") String currentLocation,
			HttpServletRequest request, @RequestParam("username") String username, @RequestParam("finyr") String finyr,
			@RequestParam("CURR_PERIOD") String CURR_PERIOD, @RequestParam("company") String company,
			@RequestParam("user_id") String user_id) throws IOException {

		String ip_Address = request.getRemoteAddr();

		Map<String, Object> map = new HashMap<String, Object>();

		if (file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {

			map = grnService.validationForFileUpload(ip_Address, user_id, currentLocation, CURR_PERIOD, finyr, company,
					file, auth.trim(), username);
		} else {
			map.put("Result", new StringBuffer("Uploaded  File Is Invalid "));
		}
		return map;
	}


	
	@GetMapping("/search-authcode-for-auto-grn")
	public Map<String, Object> search_auth_code_for_auto_grn(@RequestParam("finyr") String finyr,
			@RequestParam("currentLocation") String currentLocation, @RequestParam("company") String company
			, @RequestParam("CURR_PERIOD") String CURR_PERIOD,
			@RequestParam("auth") String auth, HttpServletRequest request) {
		String ip_Address = request.getRemoteAddr();
		String authenticationCode =auth.trim();
		Map<String, Object> map=new HashMap<>();
		
	
		
		try{
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			System.out.println("ip_Address::" + ip_Address + " user_id " + empId + " currentLocation " + currentLocation
					+ " CURR_PERIOD " + CURR_PERIOD + " finyr " + finyr + " company  " + company +" :::"+auth);
			
			
		Boolean validAuthCode=14==authenticationCode.length();
		if(validAuthCode) {
			map = grnService.search_auth_Code(authenticationCode);
			Boolean  CSPTRF_Stat = (Boolean) map.get("CSPTRF_SLNO");
			
			if(CSPTRF_Stat) {
				String CSPTRF_SLNO = map.get("SLNO").toString();		
				map=grnRepository.checkErrMessages_fro_the_trf_number(authenticationCode);
				Boolean error=(Boolean) map.get("error");
				map=grnRepository.checkSavedOrNot(authenticationCode);
				
				Boolean saved=(Boolean) map.get("Saved");
				
				if(!error&&!saved) {
					  map=grnService.callProcudeureForAutoGrn(ip_Address, empId  , currentLocation, CURR_PERIOD , finyr , company,CSPTRF_SLNO,map); 
					  
				}else {
					map.put("Result", new StringBuffer("Auto Grn Already Completed"));
					return map;
					
				}
			}else {
				map.put("Result", new StringBuffer("Invalid Authurization code "));
				return map;
			}
			
		}else {
			map.put("Result", new StringBuffer("Invalid Authurization code "));
			return map;
		}
		
		}catch (Exception e) {
			map=new HashMap<>();
			e.printStackTrace();
			String exeption=e.toString();	
			if("java.lang.NullPointerException".equals(exeption)) {	
			map.put("Exeption", true);
			map.put("Result", new StringBuffer("Auto Grn Already Completed"));	
			
			
		}else {
			map.put("Result", new StringBuffer("Erors Occured"));
		}
		}
		
		return map;
	}
		//<-------------changesss start------------>
	
	
	
	
}
