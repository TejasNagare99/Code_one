package com.excel.restcontroller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.AllocationBean;
import com.excel.bean.AllocationUploadBean;
import com.excel.bean.MailBean;
import com.excel.bean.Parameter;
import com.excel.model.Alloc_Temp_Header;
import com.excel.model.Alloc_gen_hd;
import com.excel.model.AspHeader;
import com.excel.model.Company;
import com.excel.model.FieldStaff;
import com.excel.model.Period;
import com.excel.repository.AllocationGenHeaderRepository;
import com.excel.repository.AllocationRepository;
import com.excel.repository.DepotLocationRepository;
import com.excel.repository.DivMasterRepository;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.ReportRepository;
import com.excel.service.AllocationService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.ProductMasterService;
import com.excel.service.ReportService;
import com.excel.service.TeamService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.SendMail;

@RestController
@RequestMapping("/rest")
public class MonthlyAllocationRestController implements MedicoConstants {

	@Autowired AllocationService allocationService;
	@Autowired AllocationRepository allocationRepository;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired FieldStaffRepository fieldStaffRepository;
	@Autowired DepotLocationRepository depotLocationRepository;
	@Autowired SendMail sendMail;
	@Autowired UserMasterService userMasterService;
	@Autowired ProductMasterService productMasterService;
	@Autowired DivMasterRepository divMasterRepository;
	@Autowired TeamService teamService;
	@Autowired ParameterService parameterservice;
	@Autowired ReportService reportService;

	@GetMapping("/getTeamPlansMonthly")
	public Map<String, Object> getTeam(@RequestParam("empId") String empId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId);

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


			System.out.println("allocationDate" + allocationDate);
			
			map.put("allocationDate", allocationDate);
			map.put("team", divMasterRepository.getActiveDivListByEmpId(empId, comp_code));
			map.put("plans", this.allocationService.getPlanningTypeMonthly());
			map.put("allocationMonth",allocationPeriod.getPeriod_name());
			map.put("allocationMonthPeriodCode",allocationPeriod.getPeriod_code());
			map.put("allocationPeriodCode",allocationPeriod.getPeriod_alt_name());
			map.put("monthOfUse", this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));
			map.put("finYearId",allocationPeriod.getPeriod_fin_year());
			map.put("managerList", this.allocationService.getManagerListForAssistant(empId));
			map.put("periodList", this.periodMasterService.getPeriodListForLastTwoYears(period.getPeriod_id(),period.getPeriod_fin_year()));
			
			map.put("IsSRT",this.parameterservice.getSrtAndEmailReqInd("SRT_NUMBER_REQD"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getTeamForManager")
	public Map<String, Object> getTeamForManager(@RequestParam("empId") String empId) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId);
		try {

			map.put("team", this.allocationService.getTeamWithCount(empId));

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@SuppressWarnings("unused")
	@GetMapping("/getAllocHeaderIfPresent")
	public Map<String, Object> getAllocHeaderIfPresent(@RequestParam("empId") String empId,@RequestParam("teamId") String teamId,
		@RequestParam("allocationMonth") String month,@RequestParam("year") String year,@RequestParam("allocType") String allocType,@RequestParam("team_req_ind") String team_req_ind) {
		Map<String, Object> map = new HashMap<>();
		List<FieldStaff> list=null;
		System.out.println("emp_id::" + empId+" team_req_ind "+team_req_ind +" teamId::"+teamId+year+" month::"+month);
		try {
			list=this.fieldStaffRepository.getNotCfaLinkFieldstaff(Long.valueOf(teamId));
			if(team_req_ind.trim().equals("Y")) {
				System.out.println("If");
				map.put("subTeamList", teamService.getTeamList(Long.valueOf(teamId)));
			}else {
				System.out.println("else");
				map.put("subTeamList",null);
			}
			if(list==null) {
				map.put("allowAllocation", false);
				map.put("allocationCycle",this.allocationService.getAllocationCycleNumber(teamId, month, year,empId));
				Alloc_gen_hd agh=this.allocationRepository.getAllocationHeader(teamId, month,year,allocType,empId);
				if(agh!=null) {
					map.put("alloc_approval_status", agh.getAlloc_approval_status());
					map.put("alloc_gen_id",agh.getAlloc_gen_id());
					map.put("alloc_doc_no",agh.getAlloc_doc_no());
					map.put("alloc_type",agh.getAlloc_type());
					map.put("monthOfUse",agh.getAlloc_use_month());
					map.put("allocationCycle",agh.getAlloc_cycle());
					map.put("approvalStatus",agh.getAlloc_approval_status());
				}
			}
			else {
				map.put("fieldstaffList", list);
				map.put("allowAllocation", true);
			}
		} 
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}


	@GetMapping("/getTeamBrandsAllocCycle")
	public Map<String, Object> getTeamBrands(@RequestParam("empId") String empId,@RequestParam("teamId") String teamId,@RequestParam("planType") String planType,@RequestParam("allocationMonth") String month,
			@RequestParam("year") String year,@RequestParam("frequency") String frequency,@RequestParam("sub_team_code") String sub_team_code) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId + teamId+year+month);
		try {
			map.put("brands", this.allocationService.getBrandsForTeam(empId, teamId,planType,sub_team_code));
			//map.put("allocationCycle","1");
			Alloc_gen_hd agh=this.allocationRepository.getAllocationHeader(teamId, month,year,frequency,empId);
			if(agh!=null) {
				map.put("alloc_approval_status", agh.getAlloc_approval_status());
				map.put("alloc_gen_id",agh.getAlloc_gen_id());
				map.put("alloc_doc_no",agh.getAlloc_doc_no());
				map.put("alloc_type",agh.getAlloc_type());
				map.put("monthOfUse",agh.getAlloc_use_month());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/getProductListMonthly")
	public Map<String, Object> getTeam(@RequestParam("planType") String planType,@RequestParam("selectedBrandId") List<String> brandId,@RequestParam("exludeDiscProd") String exludeDiscProd,
									@RequestParam("zeroStockInd") String zeroStockInd,@RequestParam("teamId") String teamId,@RequestParam("pmtTeam") String pmtTeam,@RequestParam("allocationMonthPeriodCode") String allocationMonthPeriodCode,
									@RequestParam("year") String year,@RequestParam("coreTeamId") String coreTeamId,@RequestParam("alloc_gen_id") Long alloc_gen_id,@RequestParam("isPrivious") String isPrivious,@RequestParam("selectedPeriodCode") String selectedPeriodCode,
									@RequestParam("selectedYear") String selectedYear,@RequestParam("allocType") String allocType,@RequestParam("empId") String empId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("planType::" + planType+"  brandId "+brandId);

		try {
			//map.put("productList", this.allocationService.getProductListForMonthlyAllocation(planType,brandId));
//			boolean isBrandEntered=this.allocationService.checkEnetedBrandByProductType(planType, brandId, year, alloc_gen_id);
//			if(!isBrandEntered) {
				this.productMasterService.deleteLockProduct(empId);
				map.put("productList", this.allocationService.getProductListForMonthlyAllocation(planType, brandId, exludeDiscProd, zeroStockInd, teamId, pmtTeam, allocationMonthPeriodCode, coreTeamId, year,isPrivious,selectedPeriodCode,selectedYear,allocType,empId));
//			}
//			map.put("isBrandEntered",isBrandEntered);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getPriviousMonthAllocation")
	public Map<String, Object> getPriviousMonthAllocation(@RequestParam("planType") String planType,@RequestParam("selectedBrandId") List<String> brandId,@RequestParam("exludeDiscProd") String exludeDiscProd,
			@RequestParam("zeroStockInd") String zeroStockInd,@RequestParam("teamId") String teamId,@RequestParam("pmtTeam") String pmtTeam,@RequestParam("allocationMonthPeriodCode") String allocationMonthPeriodCode,
			@RequestParam("year") String year,@RequestParam("coreTeamId") String coreTeamId,@RequestParam("alloc_gen_id") Long alloc_gen_id,@RequestParam("isPrivious") String isPrivious,@RequestParam("selectedPeriodCode")String selectedPeriodCode,
			@RequestParam("selectedYear") String selectedYear,@RequestParam("allocType") String allocType,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("productList", this.allocationService.getProductListForMonthlyAllocation(planType, brandId, exludeDiscProd, zeroStockInd, teamId, pmtTeam, allocationMonthPeriodCode, coreTeamId, year,isPrivious,selectedPeriodCode,selectedYear,allocType,null));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/getStaffCount")
	public Map<String, Object> getStaffCount(@RequestParam("divId") String divId,@RequestParam("sub_team_code") String sub_team_code) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("divId::" + divId);

		try {
			map.put("staffCount", this.allocationService.getStaffCount(divId,sub_team_code));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}



	@GetMapping("/getAllocationByList")
	public Map<String, Object> getAllocationByList(@RequestParam("divId") String divId,@RequestParam("allocationMonthPeriodCode") String allocationMonthPeriodCode,@RequestParam("saveMode") String saveMode,
			@RequestParam("planTypeDesc") String planTypeDesc,@RequestParam("allocationId") String allocationId,@RequestParam("allocationCycle") String allocationCycle,
			@RequestParam("allocatioinbProductType") String allocatioinbProductType,@RequestParam("year") String year,@RequestParam("allocationMode") String allocationMode,
			@RequestParam("brands")  List<String> brands,@RequestParam("sub_team_code") String sub_team_code,HttpSession session ) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("divId::" + divId+" allocationMode "+allocationMode);
		//allocationId="0";//to get all zone and cfa
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			if(allocationMode.equalsIgnoreCase("0")) {
				map.put("allocationByList", this.depotLocationRepository.getLocationsByCFA(divId,  allocationMonthPeriodCode, year,companyCode, saveMode, planTypeDesc,allocationId, allocationCycle,allocatioinbProductType,brands));
				map.put("allocationByName","CFA");
			}
			else if (allocationMode.equalsIgnoreCase("1")) {
				map.put("allocationByList", this.fieldStaffRepository.getZone(divId, "A", allocationMonthPeriodCode, year,companyCode, saveMode, planTypeDesc,allocationId, allocationCycle,allocatioinbProductType,brands,sub_team_code));
				map.put("allocationByName","Zone");
			}
			else if (allocationMode.equalsIgnoreCase("2")) {
				map.put("allocationByList", this.fieldStaffRepository.getState(divId, "A", allocationMonthPeriodCode, year,companyCode, saveMode, planTypeDesc,allocationId, allocationCycle,allocatioinbProductType,brands,sub_team_code));
				map.put("allocationByName","State");
			}
			else{
				map.put("allocationByList", this.fieldStaffRepository.getStaffDetails(divId,"A","00"+allocationMode, saveMode,"A",allocationId,
						allocationCycle,allocationMonthPeriodCode, year,companyCode, planTypeDesc.split("_")[1],"F",brands,sub_team_code));
				if(allocationMode.equalsIgnoreCase("3"))
					map.put("allocationByName","RM");
				else if(allocationMode.equalsIgnoreCase("4"))
					map.put("allocationByName","DM");
				else if(allocationMode.equalsIgnoreCase("5"))
					map.put("allocationByName","PSO");
			}


		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getAllocationByStaffCount")
	public Map<String, Object> getAllocationByStaffCount(@RequestParam("divId") String divId,@RequestParam("allocationMonthPeriodCode") String allocationMonthPeriodCode,@RequestParam("saveMode") String saveMode,
			@RequestParam("planTypeDesc") String planTypeDesc,@RequestParam("allocationId") String allocationId,@RequestParam("allocationCycle") String allocationCycle,
			@RequestParam("allocatioinProductType") String allocatioinbProductType,@RequestParam("year") String year,@RequestParam("allocationMode") String allocationMode ,
			@RequestParam("fieldtype")  String fieldType,@RequestParam("coreTeamId")  String coreTeamId,@RequestParam("sub_team_code")String sub_team_code) {
		Map<String, Object> map = new HashMap<>();
		try {
			map=this.allocationService.byUserStaffNames(divId,allocationMonthPeriodCode,saveMode,planTypeDesc,allocationId,
					allocationCycle,allocatioinbProductType, year,allocationMode,fieldType,coreTeamId,sub_team_code);

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/getEnteredAllocationByIds")
	public Map<String, Object> getEnteredAllocationById(@RequestParam("allocationId") String allocationId,@RequestParam("allocationMode") String allocationMode,@RequestParam("selectedBrandId") String brandId ) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("enteredAllocationByIds",this.allocationService.getEnteredAllocationById(allocationId, allocationMode,brandId));

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@PostMapping("/saveMonthlyAllocation")
	public Map<String, Object> saveMonthlyAllocation(@RequestBody  AllocationBean bean, HttpSession session, HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode);
			bean.setIpAddress(request.getRemoteAddr());
			Map<String,Object> allocDetails=allocationService.save(bean);

			map.put("allo_gen_id",allocDetails.get("allocId"));
			map.put("allocDocNo",allocDetails.get("allocDocNo"));
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE,"Saved Succesfully");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;

	}

	@GetMapping("/getEntTableList")
	public Map<String, Object> getEntTableList(@RequestParam("allocationId") Long allocationId){
		Map<String, Object> map = new HashMap<>();
		try {
			Map<String,Object> entMap=this.allocationService.getAllocationEnt(allocationId);
			map.put("agEnt",entMap.get("entList"));
			map.put("enteredBrands",entMap.get("enteredBrands"));
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@GetMapping("/sendForSelfApprovalMonthly")
	public Map<String, Object> sendForApproval(@RequestParam("allocationId") String allocationId,
			@RequestParam("empId") String user, @RequestParam("empEmail") String email,@RequestParam("remark") String remark) {
		Map<String, Object> map = new HashMap<>();
		try {
			this.allocationService.sendForApproval(Long.valueOf(allocationId), user, email, remark);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE,APPROVAL_MESSAGE);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE,"Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}

	@GetMapping("/getDataForSelfApprovalMonthly")
	public Map<String, Object> getDataForSelfApprovalMonthly(@RequestParam("userId") String userId,@RequestParam("assisatatInd") String ind) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			System.out.println("assisatatInd "+ind);
			map.put("selfApprovalData", this.allocationService.getSelfApprovalDataMonthly(userId,ind));
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getMonthlyHeaderDetails")
	public Map<String, Object> getMonthlyHeaderDetails(@RequestParam("alloc_gen_id") String alloc_gen_id) {
		Map<String, Object> map = new HashMap<>();

		try {
			Alloc_gen_hd header=this.allocationService.getObjectById(Long.valueOf(alloc_gen_id));
			map.put("teamId", header.getDiv_id());
			map.put("allocType", header.getAlloc_type());
			map.put("allocationMonthName", header.getAlloc_month());
			map.put("allocationDate", header.getAlloc_gen_date());

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getHeaderDetailsBeforeApprove")
	public Map<String, Object> getHeaderDetailsBeforeApprove(@RequestParam("allocId") String allocId) {
		Map<String, Object> map = new HashMap<>();

		try {
			Alloc_Temp_Header header=this.allocationService.getHeaderDetailsBeforeApprove(Long.valueOf(allocId));
			map.put("teamId", header.getDiv_id());
			map.put("allocType", header.getAlloc_type());
//			map.put("allocationMonthName", header.getAlloc_month());
//			map.put("allocationDate", header.getAlloc_gen_date());

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getAllocEnteredBrands")
	public Map<String, Object> getAllocEnteredBrands(@RequestParam("alloc_gen_id") String alloc_gen_id)  {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("enteredBrands", this.allocationService.getAllocEnteredBrands(alloc_gen_id));

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getAllocEnteredProducts")
	public Map<String, Object> getAllocEnteredProducts(@RequestParam("alloc_gen_id") String alloc_gen_id,@RequestParam("prodType") String prodType) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("prodType "+prodType);
			map.put("enteredProducts", 	this.allocationService.getAllocEnteredProducts(alloc_gen_id,prodType));

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	
	@GetMapping("/getAllocEnteredProducts_before_approve")
	public Map<String, Object> getAllocEnteredProducts_before_approve(@RequestParam("allocId") String allocId,@RequestParam("prodType") String prodType) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("prodType "+prodType);
			map.put("enteredProducts", 	this.allocationService.getAllocEnteredProducts_before_approve(allocId,prodType));

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}


	
	
	@GetMapping("/getTeamPlansModify")
	public Map<String, Object> getTeamPlansModify(@RequestParam("empId") String empId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId);

		try {

			map.put("team", this.allocationService.getTeamWithCount(empId));
			map.put("plans", this.allocationService.getPlanningTypeMonthly());
			map.put("allocEnteredDetails", this.allocationService.getAllocGenratedDetails(empId));
			map.put("managerList", this.allocationService.getManagerListForAssistant(empId));
			map.put("IsSRT",this.parameterservice.getSrtAndEmailReqInd("SRT_NUMBER_REQD"));
			
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getTeamPlans_upload_Modify")
	public Map<String, Object> getTeamPlans_upload_Modify(@RequestParam("empId") String empId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId);

		try {

			map.put("team", this.allocationService.getTeamWithCount(empId));
			map.put("plans", this.allocationService.getPlanningTypeMonthly());
			
			///
			map.put("allocEnteredDetails", this.allocationService.getallocgenrateddetails_upload_modify(empId));
			
			map.put("managerList", this.allocationService.getManagerListForAssistant(empId));
			map.put("IsSRT",this.parameterservice.getSrtAndEmailReqInd("SRT_NUMBER_REQD"));
			
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	

	@GetMapping("/getEnteredStaffDetails")
	public Map<String, Object> getEnteredStaffDetails(@RequestParam("prodId") List<String> prodId,@RequestParam("allocationId") String allocationId) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("staffDetails",this.allocationService.getEnteredStaffDetails(prodId,allocationId));

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	
	@GetMapping("/getEnteredStaffDetailsforModify")
	public Map<String, Object> getEnteredStaffDetails_for_modify(@RequestParam("prodId") String prodId,@RequestParam("allocId") String allocationId) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("staffDetails",this.allocationService.getEnteredStaffDetails_for_modify(prodId,allocationId));

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	
	@PostMapping("/updateStaffDetails")
	public Map<String, Object> updateStaffDetails(@RequestBody  AllocationBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode);
			bean.setIpAddress(request.getRemoteAddr());
			allocationService.updateStaffDetailsMonthly(bean);

			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE,"Saved Succesfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;

	}

	
	
	@PostMapping("/updateStaffDetailsBeforeApproval")
	public Map<String, Object> updateStaffDetailsBeforeApproval(@RequestBody  AllocationBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Boolean saved=false;
		String filename="";
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode);
			bean.setIpAddress(request.getRemoteAddr());
			saved=allocationService.updateStaffDetailsBeforeApproval(bean);

			if(saved) {
				List<AllocationBean> list	=this.allocationService.getAllocation_modified_Log(Long.valueOf(bean.getAllocationId()));
				
				filename=this.allocationService.genarateAllocation_modified_Log_report(list);
				System.err.println(filename);
				map.put("filename", filename);
	

			}
			map.put(DATA_SAVED, saved);
			map.put(RESPONSE_MESSAGE,"Saved Succesfully");
		}
		catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;

	}
	
	
	@GetMapping("/discardMonthlyAllocation")
	public Map<String, Object> discardMonthlyAllocation(@RequestParam("allocationId") Long alloc_gen_id,@RequestParam("year") String year) {
		Map<String, Object> map = new HashMap<>();
		try {
			allocationService.discardMonthlyAllocaton(alloc_gen_id,year);

			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE,"Discarded Succesfully");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;

	}
	@PostMapping("/discardMonthlyAllocationByBrand")
	public Map<String, Object> discardMonthlyAllocationBy(@RequestBody  AllocationBean bean) {
		Map<String, Object> map = new HashMap<>();
		try {
			allocationService.discardMonthlyAllocatonByBrand(Long.valueOf(bean.getAllocationId()),bean.getBrandId(),bean.getFinYearId());

			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE,"Discarded Succesfully");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;

	}

	@GetMapping("/getAllocationDetail")
	public Map<String, Object> getAllocationDetail(@RequestParam("allocationId") Long alloc_gen_id,@RequestParam("year") String year) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			List<AllocationBean> deyailList=allocationService.getAllocationDetail(alloc_gen_id,year);
			map.put("allocationDetail", deyailList);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@GetMapping("/sendApprovalToManager")
	public Map<String, Object>  sendApprovalToManager(@RequestParam("tranId") Long tranId,@RequestParam("year") String year,
			@RequestParam("assi_id") String assi_id,@RequestParam("mgr_id") String mgr_id,@RequestParam("type") String type) {
		Map<String, Object> map = new HashMap<>();
		try {
			String approveLink=null;
			String discardLink=null;
			String mailId=null;
			String sendBy=null;
			List<MailBean> assistant=this.userMasterService.getEmpDetailForMail(assi_id);
			sendBy=assistant.get(0).getStarted_by_name();
			List<MailBean> manager=this.userMasterService.getEmpDetailForMail(mgr_id);
			mailId=manager.get(0).getEmp_email();
			System.out.println("sendBy Id "+sendBy);
			System.out.println("Mgr_id "+mgr_id);
			System.out.println("Assi_id "+assi_id);
			if(type.equals("annualPlan")) {
				List<AllocationBean>   details = this.allocationService.getSummaryDetailsAnnualPlan(tranId, year.toString(),null);
				approveLink="rest/managerDecision?tranId="+tranId+"&year="+year+"&assi_id="+assi_id+"&mgr_id="+mgr_id+"&type="+type+"&decision=approve";
				discardLink="rest/managerDecision?tranId="+tranId+"&year="+year+"&assi_id="+assi_id+"&mgr_id="+mgr_id+"&type="+type+"&decision=discard";
				details = this.allocationRepository.getSummaryDetailsAnnualPlan(tranId, year.toString(),null);
				this.allocationService.changeStatusForAssistant(tranId, "F");
				this.sendMail.sendApprovalConfirmationMailForAnnualPlan(details,Arrays.asList(mailId),approveLink,"F","assistant","Annual Sample Plan For Confirmation",sendBy,discardLink);
			}
			else if(type.equals("monthlyPlan")) {
				this.allocationRepository.changeApprovalStatusForAssistand(tranId,year,"F");
				List<AllocationBean> details = allocationService.getAllocationDetail(tranId,year);
				approveLink="rest/managerDecision?tranId="+tranId+"&year="+year+"&assi_id="+assi_id+"&mgr_id="+mgr_id+"&remark="+mgr_id+"&type="+type+"&decision=approve";
				discardLink="rest/managerDecision?tranId="+tranId+"&year="+year+"&assi_id="+assi_id+"&mgr_id="+mgr_id+"&remark="+mgr_id+"&type="+type+"&decision=discard";
				this.sendMail.sendApprovalConfirmationOfMonthlyMail(details,Arrays.asList(mailId),approveLink,"F","assistant","Monthly Allocation For Confirmation",sendBy,discardLink);
			}
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE,"Forwarded to Manager");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE,"Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}
	@GetMapping("/managerDecision")
	public void getManagerDecision(@RequestParam("tranId") Long tranId,@RequestParam("year")  String year, @RequestParam("assi_id") String assi_id,@RequestParam("mgr_id") String mgr_id,@RequestParam("type") String type,@RequestParam("decision") String decision,HttpServletRequest request,HttpSession session) {
		try {
			String link=null;
			String mailId=null;
			String sendBy=null;
			String status=null;
			if(decision.equals("approve")) {
				status="A";
			}
			else {
				status="D";
			}
			List<MailBean> assistant=this.userMasterService.getEmpDetailForMail(assi_id);
			mailId=assistant.get(0).getEmp_email();
			List<MailBean> manager=this.userMasterService.getEmpDetailForMail(mgr_id);
			sendBy=manager.get(0).getStarted_by_name();
			System.out.println("Mail Id "+mailId);
			System.out.println("Mgr_id "+mgr_id);
			System.out.println("assi_id "+assi_id);
			if(type.equals("annualPlan")) {
				List<AllocationBean>   details = this.allocationService.getSummaryDetailsAnnualPlan(tranId, year.toString(),null);
				details = this.allocationService.getSummaryDetailsAnnualPlan(tranId, year.toString(),null);
				if(status.equals("A")) {
					this.allocationService.sendAnnualAllocationForApproval(tranId, mgr_id, "", "",request,session);
					this.allocationService.changeStatusForAssistant(tranId, "A");
				}
				else {
					this.allocationService.changeStatusForAssistant(tranId, "E");
				}
				this.sendMail.sendApprovalConfirmationMailForAnnualPlan(details,Arrays.asList(mailId),link,status,"assistant","Annual Sample Plan Status",sendBy,"");
			}
			else if(type.equals("monthlyPlan")) {
				List<AllocationBean> details = allocationService.getAllocationDetail(tranId,year);
				if(status.equals("A")) {
					this.allocationRepository.changeApprovalStatusForAssistand(tranId,year,"A");
					this.allocationService.sendForApproval(tranId, mgr_id, "", "");
				}
				else {
					this.allocationRepository.changeApprovalStatusForAssistand(tranId,year,"E");
				}
				this.sendMail.sendApprovalConfirmationOfMonthlyMail(details,Arrays.asList(mailId),link,status,"assistant","Monthly Allocation Status",sendBy,"");
			}
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
	
	@GetMapping("/pageLoadDataUpload")
	public Map<String, Object> pageLoadDataUpload(@RequestParam("empId") String empId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId);
	  try {
		  	String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		  	Period period = this.periodMasterService.getPeriodMasterByCompany(comp_code);
		  
			map.put("team", this.divMasterRepository.getActiveDivListByEmpId(empId,comp_code));
			map.put("plans", this.allocationService.getPlanningTypeMonthly());
			map.put("monthOfUse", this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));
			map.put("allocmonth", this.periodMasterService.getallocationPeriod());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@PostMapping("/uploadAllocation")
	public Map<String, Object>  uploadAllocation(@RequestParam MultipartFile file,@RequestParam String finyearid,@RequestParam String alloc_mode,@RequestParam String inputType,
			                                     @RequestParam String division_id,@RequestParam String period_id,@RequestParam String userid,@RequestParam String includestock,@RequestParam String  csvuploaded,HttpSession session, 
			                                     @RequestParam String role,@RequestParam String sub_team_code,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
		 	Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		 	
			AllocationUploadBean bean=new AllocationUploadBean();
			bean.setFileUpload(file);
			bean.setDivision_id(division_id);
			bean.setUserid(userid);
			bean.setCompcode(company.getCompany());
			bean.setPeriod_id(period_id);
			bean.setFinYear(finyearid);
			bean.setInputType(inputType);
			bean.setCsvuploaded(csvuploaded);
			bean.setFileUploadFileName(file.getOriginalFilename());
			bean.setIncludestock(includestock);
			bean.setIp_address(request.getRemoteAddr());
			bean.setEmp_id(userid);
			bean.setAlloc_mode(alloc_mode.charAt(0));
			bean.setSamp_disp_ind(company.getSamp_disp_ind());
			bean.setRole(role);
			bean.setSub_team_code(sub_team_code);
			
			String issrt = this.parameterservice.getSrtAndEmailReqInd("SRT_NUMBER_REQD");
			
		
			
			if(issrt.equals("N")) {
				map=this.allocationService.saveuploadallocationWithoutSRT(bean);
			}
			else {
			 map=this.allocationService.saveuploadallocation(bean);
			}
		   
		    
		    
		    map.put(DATA_SAVED, map.get(DATA_SAVED));
			map.put(RESPONSE_MESSAGE,map.get(RESPONSE_MESSAGE));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/regenerate-allocation")
	public Map<String, Object> regenerateAllocation(@RequestParam("allocationId") String allocationId) {
		Map<String, Object> map = new HashMap<>();
		List<FieldStaff> list=null;
		System.out.println("allocationId::" +allocationId);
		try {
			Alloc_gen_hd agh=this.allocationService.regenerateAllocation(Long.valueOf(allocationId));
			if(agh!=null) {
				map.put("alloc_approval_status", agh.getAlloc_approval_status());
				map.put("alloc_gen_id",agh.getAlloc_gen_id());
				map.put("alloc_doc_no",agh.getAlloc_doc_no());
				map.put("alloc_type",agh.getAlloc_type());
				map.put("monthOfUse",agh.getAlloc_use_month());
				map.put("allocationCycle",agh.getAlloc_cycle());
				map.put("division",agh.getDiv_id());
			}
		} 
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
}
