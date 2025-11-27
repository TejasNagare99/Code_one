package com.excel.restcontroller;

import java.text.SimpleDateFormat;
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

import com.excel.bean.AllocationConsolidationBean;
import com.excel.bean.AnnualPlanConsolidationBean;
import com.excel.bean.MonthlyAllocConsolidationBean;
import com.excel.model.AllocConHd;
import com.excel.model.Company;
import com.excel.model.Period;
import com.excel.service.AllocationConsolidationService;
import com.excel.service.AllocationService;
import com.excel.service.DivisionMasterService;
import com.excel.service.PeriodMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@RestController
@RequestMapping("/rest")
public class AllocationConsolidationRestController implements MedicoConstants {
	
	@Autowired PeriodMasterService periodMasterService;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired AllocationConsolidationService allocationConsolidationService;
	@Autowired AllocationService allocationService;
	@GetMapping("/getDataForAnnualPlanTeamConsolidation")
	public Map<String, Object> getDataForAnnualPlanTeamConsolidation(@RequestParam String empId,@RequestParam String finYear,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
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
			map.put("allocationDate", allocationDate);
			map.put("team", this.allocationConsolidationService.getDivisionsEnteredForAnnualPlan(finYear,empId));
			map.put("eneteredManagers",this.allocationConsolidationService.getAnnualPlanEnteredManagerByDivision(empId,0L, finYear, "F",comp_code));

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getDataForAnnualPlanEneteredManager")
	public Map<String, Object> getDataForAnnualPlanEneteredManager(@RequestParam String empId,@RequestParam Long divId
			,@RequestParam String finYear,@RequestParam String allocConType,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list=null;
		AllocConHd allocConHd=null;
		try {
			System.out.println("divId "+divId);
			System.out.println("finYear "+finYear);
			System.out.println("allocConType "+allocConType);
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			allocConHd=this.allocationConsolidationService.getAllocConHdForAnnualPlan(divId,finYear,allocConType);
			list=this.allocationConsolidationService.getAnnualPlanEnteredManagerByDivision(empId,divId, finYear, "F",comp_code);
			map.put("eneteredManagers", list);
			if(allocConHd!=null) {
				map.put("alloc_con_docno",allocConHd.getAlloc_con_docno());
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/saveAnnualPlanTeamConsolidation")
	public Map<String, Object> saveAnnualPlanTeamConsolidation(@RequestBody AnnualPlanConsolidationBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list=null;
		Long allocConId=null;
		try {
			Company company=(Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			map=this.allocationConsolidationService.saveAnnualPlanTeamConsolidation(bean);
			map.put("team", this.allocationConsolidationService.getDivisionsEnteredForAnnualPlan(bean.getFinYear(),bean.getEmpId()));
			map.put("eneteredManagers",this.allocationConsolidationService.getAnnualPlanEnteredManagerByDivision(bean.getEmpId(),0L,bean.getFinYear(), "F",companyCode));
			//send for approval
			allocConId=(Long) map.get("allocConId");
			this.allocationConsolidationService.sendAnnualAllocationCompany(allocConId, bean.getEmpId(), null, null,request,session);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Saved Successfully!");
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/getDataForAnnualPlanConsolatedByTeam")
	public Map<String, Object> getDataForAnnualPlanConsolatedByTeam(@RequestParam Long locId,@RequestParam String finYear,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list=null;
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
			list=this.allocationConsolidationService.getConsolatedAnnualPlan(locId, finYear);
			map.put("allocationDate", allocationDate);
			map.put("annualPlanConsolatedByTeam", list);

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/saveAnnualPlanCompanyConsolidation")
	public Map<String, Object> saveAnnualPlanCompanyConsolidation(@RequestBody AnnualPlanConsolidationBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list=null;
		try {
			Company company=(Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			this.allocationConsolidationService.saveAnnualPlanCompanyConsolidation(bean);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Saved Successfully!");
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getDataForMonthlyAllocationTeamConsolidation")
	public Map<String, Object> getDataForMonthlyAllocationTeamConsolidation(@RequestParam String empId,@RequestParam String year,@RequestParam String allocType,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
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
			map.put("allocationMonth",allocationPeriod.getPeriod_name());
			map.put("allocationDate", allocationDate);
			map.put("monthOfUse", this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));
			map.put("team", this.allocationConsolidationService.getDivisionsEnteredForMonthly(allocationPeriod.getPeriod_name(), year, allocType));
			map.put("eneteredManagers",this.allocationConsolidationService.getMonthlyAllocationEnteredManagerByDivision(0L, year,allocationPeriod.getPeriod_name(),allocType,allocationPeriod.getPeriod_code(),A,comp_code));

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getDataForMonthlyAllocEneteredManager")
	public Map<String, Object> getDataForMonthlyAllocEneteredManager(@RequestParam Long divId,@RequestParam String finYear,
			@RequestParam String allocMonth,@RequestParam("monthOfUse") String monthOfUse,@RequestParam String allocType,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list=null;
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			list=this.allocationConsolidationService.getMonthlyAllocationEnteredManagerByDivision(divId, finYear,allocMonth,allocType,monthOfUse,A,comp_code);
			map.put("team", this.allocationConsolidationService.getDivisionsEnteredForMonthly(allocMonth,finYear, allocType));
			map.put("eneteredManagers", list);

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/saveMonyhlyAllocTeamConsolidation")
	public Map<String, Object> saveMonyhlyAllocTeamConsolidation(@RequestBody MonthlyAllocConsolidationBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Object> list=null;
		Long allocConId=null;
		try {
			Company company=(Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			map=this.allocationConsolidationService.saveMonthlyAlloConsolidation(bean);
			map.put("team", this.allocationConsolidationService.getDivisionsEnteredForMonthly(bean.getAllocMonth(), bean.getFinYear(), bean.getAllocType()));
			map.put("eneteredManagers",this.allocationConsolidationService.getMonthlyAllocationEnteredManagerByDivision(0L, bean.getFinYear(),bean.getAllocMonth(),bean.getAllocType(),bean.getMonthOfUsePeriodCode(),A,companyCode));
			//send for approval
			allocConId=(Long) map.get("allocConId");
			//this.allocationConsolidationService.sendMonthlyAllocationTeam(allocConId, bean.getEmpId(), null, null,request,session);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Saved Successfully!");
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/eneteredMonthlyConsolidation")
	public Map<String, Object> eneteredMonthlyConsolidation(@RequestParam("empId") String monthOfUse) {
		Map<String, Object> map = new HashMap<>();
		List<AllocationConsolidationBean> list=null;
		try {
			list=this.allocationConsolidationService.getAllocConHeaderByType(MONTHLY_TEAM_DOC_TYPE);
			map.put("monthlyConsolidationDetail", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
}
