package com.excel.restcontroller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.excel.bean.DispatchMailBean;
import com.excel.bean.SpecialAllocationBean;
import com.excel.exception.MedicoException;
import com.excel.model.EmployeeDetails;
import com.excel.model.FieldStaff;
import com.excel.model.Period;
import com.excel.repository.DispatchRepository;
import com.excel.repository.DivMasterRepository;
import com.excel.service.AllocationService;
import com.excel.service.DivisionMasterService;
import com.excel.service.DoctorMasterService;
import com.excel.service.EmailTranwiseHelpService;
import com.excel.service.FieldStaffService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.ProductMasterService;
import com.excel.service.SpecialAllocationService;
import com.excel.service.TaskMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.SendMail;

@RestController
@RequestMapping("/rest")
public class SpecialAllocationController implements MedicoConstants {
	@Autowired EmailTranwiseHelpService emailTranwiseHelpService;
	@Autowired SpecialAllocationService  specialAllocationService;
	@Autowired DoctorMasterService doctorMasterService;
	@Autowired ProductMasterService productMasterService;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired AllocationService allocationService;
	@Autowired ParameterService parameterService;
	@Autowired FieldStaffService fieldStaffService;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired DispatchRepository dispatchRepository;
	@Autowired SendMail sendMail;
	@Autowired TaskMasterService taskMasterService;
	@Autowired UserMasterService userMasterService;
	@Autowired DivMasterRepository divMasterRepository;

	@GetMapping("/pageload-data-specialAllocation-doctor")
	public Map<String, Object> getFieldStaffDetails(@RequestParam("empId") String empId,@RequestParam("userDiv") Long userDiv,@RequestParam("userRole") String userRole,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<SpecialAllocationBean> bean = new ArrayList<>();
		try {
			System.out.println( "userDiv "+userDiv);
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(comp_code);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date today = new Date();
			Date taskDate = MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(period.getPeriod_end_date());
			String allocationDate = "";
			if (today.compareTo(taskDate) > 0) {
				allocationDate = dateFormat.format(taskDate);
			} else {
				allocationDate = dateFormat.format(today);
			}
			Period allocationPeriod= this.allocationService.getAllocationMonthofUse();
			EmployeeDetails user=this.userMasterService.getEmployeeDetails(empId);
			map.put("allocationDate", allocationDate);
			map.put("periodCode",allocationPeriod.getPeriod_code());
			map.put("finYear",allocationPeriod.getPeriod_fin_year());
			map.put("monthOfUse", this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));
			map.put("alloc_cycle","1");
			map.put("requestTypeList",this.parameterService.getParameterIdName("REQUEST_TYPE","D"));
			map.put("divisionList",this.divisionMasterService.getStandardDivisionList());
			map.put("isSrt",this.parameterService.getSrtAndEmailReqInd("SRT_NUMBER_REQD"));
			map.put("existingSpecialAllocation", this.specialAllocationService.getExistingSpecialAllocation("D"));
			if(userRole.equalsIgnoreCase(ROLE_MSR)) {
				SpecialAllocationBean b = new SpecialAllocationBean();
				b.setProductList(this.productMasterService.getSampleProductList(userDiv,comp_code));
				bean.add(b);
				map.put("productList", bean);
				map.put("fieldstaffDetail", this.specialAllocationService.getFieldStaffDetails(null,user.getFstaff_id()));//changed
				map.put("doctors", this.doctorMasterService.getDoctorsForFieldStaff(user.getFstaff_id().toString()));
			}
			try {
				String emailReqInd = this.parameterService.getSrtAndEmailReqInd("TRANWISE_EMAIL_REQD");
				map.put("EmailReqInd",emailReqInd);
				map.put("DefaultMailList", this.parameterService.getDefaultParameterEmailsByTranType("SPLALLOC_EMAIL_IDS"));
			}
			catch(Exception e) {
				map.put("EmailReqInd","N");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/get-fieldstaff-details-doctors")
	public Map<String, Object> getFieldStaffDoctorDetails(@RequestParam("fsId") Long fsId,@RequestParam("levelcode") String levelcode) {
	    Map<String, Object> map = new HashMap<>();
		try {
			map.put("fieldstaffDetail", this.specialAllocationService.getFieldStaffDetails(null,fsId));//changed
			map.put("doctors", this.doctorMasterService.getDoctorsForFieldStaff(fsId.toString()));
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-recipient-detail")
	public Map<String, Object> getRecipientDetails(@RequestParam("recipientId") Long docId,
			@RequestParam("requestorId") Long requestorId,@RequestParam("finYear") Long finYear,
			@RequestParam("requestType") String requestType,@RequestParam("recipientStringId") String recipientStringId) { 
		Map<String, Object> map = new HashMap<>();
		try {
			List<SpecialAllocationBean> list=this.specialAllocationService.specialAllocationDetails(docId, requestorId, finYear, "E",requestType,recipientStringId);
			if(list!=null && list.size()>0) {
				map.put("isExist", true);
				map.put("recipientDetail", list);
			}else {
				map.put("isExist", false);
				if(requestType!=null && requestType.equals("D"))
					map.put("recipientDetail", this.doctorMasterService.getDoctorsDetails(docId));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-product-detail")
	public Map<String, Object> getProductDetails(@RequestParam("divId")Long divId,
			@RequestParam("productId")Long prodId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("div id "+divId);
			System.out.println("prodId "+prodId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map.put("productDetails", this.specialAllocationService.getProductDetails(divId,prodId,companyCode));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-special-allocation-doctor")
	public Map<String, Object> saveSpecialAllocation(@RequestBody  SpecialAllocationBean bean, HttpSession session, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("Size"+bean.getProductDetails().get(0).getBrandNames());
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode);
			bean.setIpAddress(request.getRemoteAddr());
			Map<String, Object> allocMap=this.specialAllocationService.saveSpecialAllocation(bean);
			
			map.put("requestNumber",allocMap.get("requestNumber"));
			map.put("allocReqId", allocMap.get("allocReqId"));
			map.put("detailIds", allocMap.get("detailIds"));
			map.put(DATA_SAVED,true);
		    map.put(RESPONSE_MESSAGE, allocMap.get("RESPONSE_MESSAGE"));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
		
	}
	
	@PostMapping("/uploadSpecialAllocImage")
	public  Map<String, Object>  uploadSpecialAllocImage(@RequestParam MultipartFile file) throws Exception {
		System.out.println("In image Upload");
		Map<String, Object> map = new HashMap<>();
		try {
			map = this.specialAllocationService.uploadSpecialAllocImage(file);
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/delete-product")
	public Map<String, Object> deleteProduct(@RequestParam("detailId") String detailId,@RequestParam("year") Long year) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("Allocdetail "+detailId);
			this.specialAllocationService.deleteProduct(Long.valueOf(detailId),Long.valueOf(year));

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/specialAllocationApproval")
	public void specialAllocationApproval(@RequestParam("empId") String user,@RequestParam("alloc_req_id") Long alloc_req_id,
			@RequestParam("approvalLevels") String approvalLevels, @RequestParam("allocType") String allocType, HttpServletRequest request,HttpSession session)
			throws Exception {
		try {
			System.out.println("approvalLevels "+approvalLevels);
//			if(allocType.equals("D")) {
//				if(alloc_req_id!=null && alloc_req_id!=0L) {
//				 this.taskMasterService.deletePreviousTaskMasterDetail(alloc_req_id);
//				 this.taskMasterService.deletePreviousTaskMaster(alloc_req_id);
//				}
//				this.taskMasterService.createApprovals(alloc_req_id, approvalLevels,user);
//			}
			
			
			this.specialAllocationService.specialAllocationApproval(alloc_req_id, user, allocType, approvalLevels, request,session,false);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
	
	}
	
	
	
	@GetMapping("/specialAllocationApproval-manual")
	public void specialAllocationApproval( HttpServletRequest request,HttpSession session)
			throws Exception {
		try {
//			System.out.println("approvalLevels "+approvalLevels);
//			if(allocType.equals("D")) {
//				if(alloc_req_id!=null && alloc_req_id!=0L) {
//				 this.taskMasterService.deletePreviousTaskMasterDetail(alloc_req_id);
//				 this.taskMasterService.deletePreviousTaskMaster(alloc_req_id);
//				}
//				this.taskMasterService.createApprovals(alloc_req_id, approvalLevels,user);
//			}
			this.specialAllocationService.specialAllocationApproval(543L, "E00550", "M", "1", request,session,false);
			
			
//			this.specialAllocationService.specialAllocationApproval(alloc_req_id, user, allocType, approvalLevels, request,session,false);
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
	
	}
	
	
	@GetMapping("/pageload-data-specialAllocation-meeting")
	public Map<String, Object> getPageloadDataForMeeting(@RequestParam("empId") String empId,@RequestParam("userDiv") Long userDiv, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<SpecialAllocationBean> bean = new ArrayList<>();
		System.out.println("userDiv "+userDiv);
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(comp_code);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date today = new Date();
			Date taskDate = MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(period.getPeriod_end_date());
			String allocationDate = "";
			if (today.compareTo(taskDate) > 0) {
				allocationDate = dateFormat.format(taskDate);
			} else {
				allocationDate = dateFormat.format(today);
			}
			Period allocationPeriod= this.allocationService.getAllocationMonthofUse();
			
			map.put("allocationDate", allocationDate);
			if(userDiv!=0) {
				map.put("fieldstaffDetail", this.specialAllocationService.getFieldStaffDetails(empId,null));
				SpecialAllocationBean b = new SpecialAllocationBean();
				b.setProductList(this.productMasterService.getSampleProductListEmergency(userDiv,comp_code));
				bean.add(b);
				map.put("productList", bean);
			}
			map.put("recipientList",this.parameterService.getParameterIdName("RECIPIENT_TYPE",null));
			map.put("requestTypeList",this.parameterService.getParameterIdName("REQUEST_TYPE","M"));
			//map.put("divisionList",this.divisionMasterService.getStandardDivisionList());
			map.put("divisionList",divMasterRepository.getActiveDivListByEmpId(empId, comp_code));
			map.put("levels",this.divisionMasterService.getStandardDivisionList());
			map.put("periodCode",allocationPeriod.getPeriod_code());
			map.put("finYear",allocationPeriod.getPeriod_fin_year());
			map.put("monthOfUse", this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));
			map.put("alloc_cycle","1");
			map.put("isSrt",this.parameterService.getSrtAndEmailReqInd("SRT_NUMBER_REQD"));
			map.put("existingSpecialAllocation", this.specialAllocationService.getExistingSpecialAllocation("M"));
			try {
				String emailReqInd = this.parameterService.getSrtAndEmailReqInd("TRANWISE_EMAIL_REQD");
				map.put("EmailReqInd",emailReqInd);
				map.put("DefaultMailList", this.parameterService.getDefaultParameterEmailsByTranType("SPLALLOC_EMAIL_IDS"));
				
			}
			catch(Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				map.put("EmailReqInd","N");
			}
		
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-fieldstaff-levels")
	public Map<String, Object> getFieldstaffLevels(@RequestParam("divId") Long divId,HttpSession session) {
	    Map<String, Object> map = new HashMap<>();
		List<SpecialAllocationBean> bean = new ArrayList<>();
	    try {
	    	String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
	    	System.out.println("divId:::"+divId);
	    	map.put("levels", this.specialAllocationService.getLevels(divId));
	    	SpecialAllocationBean b = new SpecialAllocationBean();
	    	b.setProductList(this.productMasterService.getSampleProductListEmergency(divId,comp_code));
			bean.add(b);
			map.put("productList", bean);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	    
	}
	@GetMapping("/get-fieldstaff-list")
	public Map<String, Object> getFieldStaffList(@RequestParam("divId") Long divId,
			@RequestParam("levelcode") String levelcode,HttpSession session) {
	    Map<String, Object> map = new HashMap<>();
	    List<SpecialAllocationBean> bean = new ArrayList<>();
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			System.out.println("divId "+divId);
			System.out.println("levelcode "+levelcode);
			map.put("requestorList", this.fieldStaffService.getFieldStaffByDivAndLevelcode(divId, levelcode, "F"));
			SpecialAllocationBean b = new SpecialAllocationBean();
	    	b.setProductList(this.productMasterService.getSampleProductList(divId,comp_code));
			bean.add(b);
			map.put("productList", bean);
			
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/get-fieldstaff-details")
	public Map<String, Object> getFieldStaffDetails(@RequestParam("fsId") Long fsId,@RequestParam("levelcode") String levelcode) {
	    Map<String, Object> map = new HashMap<>();
		try {
			map.put("fieldstaffDetail", this.specialAllocationService.getFieldStaffDetails(null,fsId));
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	


	
	@GetMapping("/special-allocation-detail-by-id")
	 public Map<String, Object> specialAllocationDetailById(@RequestParam("alloc_req_id") Long alloc_req_id,@RequestParam("finYear") Long finYear,@RequestParam("status") String status) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("allocationDetails",this.specialAllocationService.specialAllocationDetailsById(alloc_req_id, finYear,status));
			map.put("freightTypeList",this.parameterService.getParameterIdName("FREIGHT_TYPE",null));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	 }
	
	@GetMapping("/getFieldstaffAddress")
	 public Map<String, Object> getFieldstaffAddress(@RequestParam("fsatff_id") Long fsatff_id) throws Exception {
		Map<String, Object> map = new HashMap<>();
		SpecialAllocationBean object = new SpecialAllocationBean(); 
		try {
			FieldStaff detail=this.fieldStaffService.getObjectByStaffId(fsatff_id);
			object.setAddress1(detail.getFstaff_addr1());
			object.setAddress2(detail.getFstaff_addr2());
			object.setAddress3(detail.getFstaff_addr3());
			object.setAddress4(detail.getFstaff_addr4());
			object.setCity(detail.getFstaff_addr4());
			object.setPincode(detail.getFstaff_zip());
			object.setPhone(detail.getFstaff_mobile());
			object.setEmail(detail.getEmail());
			map.put("fieldStaffDetails",object);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	 }
	@GetMapping("/demo")
	 public Map<String, Object> demo() throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			this.specialAllocationService.finalApproval(258L,"","","A","") ;
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	 }

	@GetMapping("/testing")
	 public void finalSpecialApproval(@RequestParam("allocationId") Long allocationId,@RequestParam("b")  String last_approved_by,@RequestParam("c")  String comp_cd,@RequestParam("d")  String isapproved,@RequestParam("e") String motivation) throws Exception {
		try {
			List<DispatchMailBean> bean = dispatchRepository.getDispatchedMailData(0L);
    		//sendMail.sendMailForDispatch(bean, Arrays.asList("sachin.lokhande@excelsof.com"),"F", "E00040","Dispatch","","","","");
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	 }
	
	@GetMapping("/get-service-number-list")
	public Map<String, Object> getTeamCodeDataForTeamMaster(@RequestParam("serviceNo") String serviceNo,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String serviceNum=null;
		try {
			System.out.println("serviceNo:::"+serviceNo);
			serviceNum=specialAllocationService.getServiceNumList(serviceNo);
			map.put("serviceNum", serviceNum);
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-mailIds-by-tran-type")
	public Map<String, Object> getMailsByTranType(@RequestParam("tranType") String tranType,
			@RequestParam("searchString") String searchString){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			List<String> dataList = this.emailTranwiseHelpService.getEmailsForHelp(tranType, searchString).stream()
					.map(e->e.getEmail_id()).collect(Collectors.toList());
			if(dataList!=null && dataList.size()>0)
			map.put("dataList", dataList);
			else throw new MedicoException("NDF");
		}
		catch(MedicoException me) {
			map.put("NDF", me.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put("INV", MedicoConstants.INV);
		}
		return map;
	}
}
