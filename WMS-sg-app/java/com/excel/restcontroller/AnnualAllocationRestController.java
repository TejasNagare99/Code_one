package com.excel.restcontroller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.hc.core5.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.AllocationBean;
import com.excel.model.AnnualPlanPreviousYearCount;
import com.excel.model.AspDetail;
import com.excel.model.AspHeader;
import com.excel.model.Period;
import com.excel.repository.AllocationRepository;
import com.excel.service.AllocationService;
import com.excel.service.PeriodMasterService;
import com.excel.service.TeamService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@RestController
@RequestMapping("/rest")
public class AnnualAllocationRestController implements MedicoConstants {

	@Autowired AllocationService allocationService;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired AllocationRepository allocationRepository;
	@Autowired TeamService teamService;
	@Autowired UserMasterService  userMasterService;
	

	@GetMapping("/getTeamPlans")
	public Map<String, Object> getTeam( HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		
		
	
		
		

		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
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
			System.out.println("allocationDate" + allocationDate);
			map.put("allocationDate", allocationDate);
			map.put("team", this.allocationService.getTeamWithCount(empId));
			map.put("plans", this.allocationService.getPlanningType(empId));
			map.put("managerList", this.allocationService.getManagerListForAssistant(empId));

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/checkedTeamExist")
	public Map<String, Object> checkedTeamExist(@RequestParam("mgrEmpId") String mgrEmpId,@RequestParam("userId") Long userId,@RequestParam("teamId") Long teamId,@RequestParam("fin_year") String fin_year,@RequestParam("sub_team_code") String sub_team_code) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("userId::" + userId +"Team Id "+ teamId);
		try {
			AspHeader header=this.allocationService.getAspHeaderByManagerAndUserId(userId, teamId, fin_year,sub_team_code);
			List<AllocationBean> bean= this.allocationService.getTeamWithPriviousYearCount(mgrEmpId,teamId.toString(),fin_year);
			if(bean!=null) {
				map.put("privious_actual_team_size",bean.get(0).getActual_team_size());
			}else {
				map.put("privious_actual_team_size",0);
			}
			if(header!=null) {
				map.put("isExist", true);
				map.put("aspHeader", header);
			}
			else {
				map.put("isExist", false);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getTeamBrands")
	public Map<String, Object> getTeamBrands(@RequestParam("empId") String empId,
											 @RequestParam("teamId") String teamId,
											 @RequestParam("planType") String planType,
											 @RequestParam("sub_team_code") String sub_team_code) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId + teamId);
		try {
			map.put("brands", this.allocationService.getBrandsForTeam(empId, teamId,planType,sub_team_code));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getProductList")
	public Map<String, Object> getProductList(@RequestParam("smp_std_div_id") String smp_std_div_id,
			@RequestParam("smp_sa_prod_group") List<String> smp_sa_prod_group,
			@RequestParam("smp_prod_type_id") String smp_prod_type_id,@RequestParam("allocation_number") String allocation_number) {
		System.out.println("smp_std_div_id " + smp_std_div_id);
		System.out.println("smp_sa_prod_group " + smp_sa_prod_group);
		System.out.println("smp_prod_type_id " + smp_prod_type_id);
		System.out.println("allocation_number " + allocation_number);
		Map<String, Object> map = new HashMap<>();
		try {
			List<AllocationBean> bean = new ArrayList<>();
			AllocationBean b = new AllocationBean();
			//b.setProductList(this.allocationService.getSampleProductList(smp_std_div_id, smp_sa_prod_group, smp_prod_type_id));
			b.setProductList(this.allocationService.getSampleProductListForAnnualPlan(smp_std_div_id, smp_sa_prod_group, smp_prod_type_id,allocation_number));
			bean.add(b);
			map.put("productList", bean);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getPreviousYearDetails")
	public Map<String, Object> getPreviousYearDetails(@RequestParam("prodId") String prodId,
			@RequestParam("smp_std_div_id") String smp_std_div_id,
			@RequestParam("smp_sa_prod_group") String smp_sa_prod_group,
			@RequestParam("smp_prod_type_id") String smp_prod_type_id,@RequestParam("year") String year) {
		System.out.println("prodId " + prodId);
		System.out.println("smp_std_div_id " + smp_std_div_id);
		System.out.println("smp_sa_prod_group " + smp_sa_prod_group);
		System.out.println("smp_prod_type_id " + smp_prod_type_id);
		Map<String, Object> map = new HashMap<>();
		try {
			boolean productExist=allocationService.checkProductPresent(Long.valueOf(prodId),Long.valueOf(smp_std_div_id), year);
			if(!productExist) {
				
				List<AllocationBean> productDetails = this.allocationService.getProductDetails(Long.valueOf(prodId),year);
			    map.put("presentInIndia", productDetails.get(0).getPresentInIndia());
			    map.put("supplyType", productDetails.get(0).getSupplyType()); 
			    map.put("cog",productDetails.get(0).getSmp_cog_rate());
			    map.put("smp_erp_prod_cd", productDetails.get(0).getSmp_erp_prod_cd());
			    map.put("brandId", productDetails.get(0).getSmp_sa_prod_group());
			  //map.put("previousYearPlan", this.allocationService.getPreviousYearSamplePlan(userId, mgrId, fin_year));
			    map.put("previouisYearDetails", this.allocationService.getPreviousYearDataAnnualPlan(smp_std_div_id, prodId, year));
			    List<AnnualPlanPreviousYearCount> prevYearCount = this.allocationService.getPreviousYearCount(Long.valueOf(prodId),Long.valueOf(smp_std_div_id),year);
			   if(prevYearCount!=null) {
				   map.put("prvyrdsp_unit", prevYearCount.get(0).getPrevyrdsp_unit());
				   map.put("prvyrdspcnt", prevYearCount.get(0).getPrevyrdspcnt());
				   map.put("prvdsp_perpso", prevYearCount.get(0).getPrevyrdsp_perpso());
			   }
			}
			else {
				map.put(RESPONSE_MESSAGE, "Sample Plan is Already Entered");
			}
			    map.put("producExist", productExist);
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/saveAnnualAllocation")
	public Map<String, Object> saveAnnualAllocation(@RequestBody  AllocationBean bean, HttpSession session, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode);
			bean.setIpAddress(request.getRemoteAddr());
			Map<String, Object> allocationMap=allocationService.saveAnnualAllocation(bean);
			map.put(DATA_SAVED, allocationMap.get(DATA_SAVED));
			map.put(RESPONSE_MESSAGE, allocationMap.get(RESPONSE_MESSAGE));
			map.put("aspHeader", allocationMap.get("aspHeader"));
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
		
	}
	
	
	
	@GetMapping("/getAnnualPlanEnteredProductDetail")
	public Map<String, Object> getAnnualPlanEnteredProductDetail(@RequestParam("divId") Long divId,@RequestParam("brandId") Long brandId,
			                                                     @RequestParam("year") String year,@RequestParam("userId") String userId) throws Exception{
		Map<String, Object> map = new HashMap<>();

		try {
			
			List<AllocationBean> annualEnteredData=allocationService.getAnnualPlanEnteredProductDetail(divId, brandId, year, userId);
			map.put("annualEnteredData", annualEnteredData);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}
		
	@GetMapping("/getProdDataForModify")
	public Map<String, Object> getProdDataForModify(@RequestParam("aspDetailId") Long aspDetailId,@RequestParam("empId") String empId) throws Exception{
		Map<String, Object> map = new HashMap<>();
		try {
			AspDetail detail=this.allocationService.getObjectAspDetail(aspDetailId);
			List<AllocationBean> bean = new ArrayList<>();
			AllocationBean b = new AllocationBean();
			b.setProductList(this.allocationService.getSampleProductList(detail.getAsp_div_id().toString(), Arrays.asList(detail.getAsp_brand_id().toString()),detail.getAsp_prod_type().toString()));
			bean.add(b);
			map.put("productList", bean);
			List<AllocationBean> productDetails = this.allocationService.getProductDetails(detail.getAsp_prod_id(),detail.getAsp_dtl_fin_year());
		    map.put("presentInIndia", productDetails.get(0).getPresentInIndia());
		    map.put("supplyType", productDetails.get(0).getSupplyType()); 
		    map.put("cog",productDetails.get(0).getSmp_cog_rate());
		    map.put("smp_erp_prod_cd", productDetails.get(0).getSmp_erp_prod_cd());
		    map.put("brandId", productDetails.get(0).getSmp_sa_prod_group());
		    map.put("previouisYearDetails",this.allocationService.getPreviousYearDataAnnualPlan(detail.getAsp_div_id().toString(), detail.getAsp_prod_id().toString(), detail.getAsp_dtl_fin_year()));
		    map.put("aspDetail", detail);
		    map.put("brands", this.allocationService.getBrandsForTeam(empId, detail.getAsp_div_id().toString(),detail.getAsp_prod_type().toString(),"0"));
		    List<AnnualPlanPreviousYearCount> prevYearCount = this.allocationService.getPreviousYearCount(detail.getAsp_prod_id(),detail.getAsp_div_id(),detail.getAsp_dtl_fin_year());
		    if(prevYearCount!=null) {
		        map.put("prvyrdsp_unit", prevYearCount.get(0).getPrevyrdsp_unit());
			   map.put("prvyrdspcnt", prevYearCount.get(0).getPrevyrdspcnt());
			   map.put("prvdsp_perpso", prevYearCount.get(0).getPrevyrdsp_perpso());
		    }
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
    @GetMapping("/annualAllocationSelfApproval")
    public Map<String, Object>  sendForApproval(@RequestParam("aspId") Long aspId,@RequestParam("empId") String user, @RequestParam("empEmail") String email,@RequestParam("remark") String remark,HttpServletRequest request,HttpSession session) {
    	Map<String, Object> map = new HashMap<>();
    	try {
    		this.allocationService.sendAnnualAllocationForApproval(aspId, user, email, remark,request,session);
    		map.put(DATA_SAVED, true);
    	}
    	catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
    	return map;
    }
	
    
    @GetMapping("/summaryDataAnnualPlan")
    public Map<String, Object> getAnnualPlanSummaryDetails(@RequestParam("tran_id") Long tran_id,@RequestParam("finYear") String finYear, @RequestParam("docType") String docType) {
    	Map<String, Object> map = new HashMap<>();
    	List<AllocationBean> list=null;
    	System.out.println("docType "+docType);
    	try {
    		if(docType.charAt(0)==ANNUAL_ALLOCATION.charAt(0)) {
    			list=this.allocationService.getSummaryDetailsAnnualPlan(tran_id, finYear, docType);
    		}
    		else if(docType.equals(ANNUAL_TEAM_DOC_TYPE)) {
    			list=this.allocationService.getSummaryDetailsAnnualPlanByDivision(tran_id, finYear, docType);
    		}
    		
    		map.put("annualSummaryData", list);
    	}
    	catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
    	
    	return map;
    }
	
    @GetMapping("/discardAnnualAllocation")
    public Map<String, Object> discardAnnualAllocatiom(@RequestParam("aspId") Long aspId) {
    	Map<String, Object> map = new HashMap<>();
    	try {
    		this.allocationService.discardAnnualAllocatio(aspId);
    		map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, RESPONSE_DISCARD_MESSAGE);
    	}
    	catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
    	
    	return map;
    }
    
    @GetMapping("/deleteProductFromAnnualPlan")
    public Map<String, Object> deleteProductFromAnnualPlan(@RequestParam("alloc_detail_id") Long alloc_detail_id) {
    	Map<String, Object> map = new HashMap<>();
    	try {
    		this.allocationService.deleteProductFromAnnualPlan(alloc_detail_id);;
			map.put(RESPONSE_MESSAGE, "Product Deleted Successfully");
    	}
    	catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
    	
    	return map;
    }
    
    @GetMapping("/getSubTeamList")
	public Map<String, Object> getAllocHeaderIfPresent(@RequestParam("teamId") String teamId) {
		Map<String, Object> map = new HashMap<>();
		try {
		    map.put("subTeamList", teamService.getTeamList(Long.valueOf(teamId)));
			
		} 
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
}
