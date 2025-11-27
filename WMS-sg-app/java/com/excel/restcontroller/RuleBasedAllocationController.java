package com.excel.restcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.model.Alloc_Remark;
import com.excel.model.Company;
import com.excel.model.Period;
import com.excel.service.AllocTempService;
import com.excel.service.PeriodMasterService;
import com.excel.service.RuleBasedAllocationApprovalService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class RuleBasedAllocationController implements MedicoConstants{
	
	@Autowired RuleBasedAllocationApprovalService ruleBasedAllocationApprovalService;
	@Autowired AllocTempService allocTempService;
	@Autowired PeriodMasterService periodMasterService;
	
	
	@GetMapping("/ruleBasedSelfApprovalData")
	public Map<String, Object> ruleBasedSelfApprovalData(@RequestParam("empId") String empId,@RequestParam("user_role") String user_role,
			@RequestParam("locId") String locId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId);
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map.put("approvalList", this.ruleBasedAllocationApprovalService.approvalListForSelfAppr(empId,user_role,locId,companyCode));

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/ruleBasedDetails")
	public Map<String, Object> ruleBasedDetails(@RequestParam("division") Long division,@RequestParam("period") Long period,
			@RequestParam("up_status") String up_status,@RequestParam("ucycle") String ucycle,
			@RequestParam("allocHdTempId")Long allocHdTempId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> detail = new HashMap<>();
		String remark = null;
		try {
			String stockcheck = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getAlloc_stock_check();
			map.put("allocationDetail", this.ruleBasedAllocationApprovalService.approvalDetails(division, period, up_status, ucycle,allocHdTempId));
			detail=this.ruleBasedAllocationApprovalService.getProductWiseStock(division, period, up_status, ucycle,allocHdTempId);
			map.put("productWiseDetail",detail.get("productWiseDetail") );
			map.put("allowApproval",detail.get("allowApproval") );
			map.put("total_product_qty", detail.get("total_product_qty"));
			remark = ruleBasedAllocationApprovalService.getAllocRemark(allocHdTempId);
			if(stockcheck.equals("Y")) {
				if(detail.get("allowApproval").equals(true)) {
					map.put("allowApproval","Y");
				}
				else {
					map.put("allowApproval","N");
				}
			}
			else {
				map.put("allowApproval","Y");
			}
			
			if(remark!=null) {
				map.put("remark", remark);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/selfApproveRuleBasedAllocation")
	public Map<String, Object> selfApproveRuleBasedAllocation(@RequestParam("allocGenTempId") Long allocGenTempId,
			@RequestParam("status") String status,@RequestParam("empId") String empId,
			@RequestParam("user_role") String user_role,@RequestParam("locId") String locId,
			@RequestParam("remark") String remark,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("allocGenTempId "+allocGenTempId+" status "+status);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			this.ruleBasedAllocationApprovalService.selfApprovalRuleBasedAllocation(allocGenTempId, status,remark);
			map.put("approvalList", this.ruleBasedAllocationApprovalService.approvalListForSelfAppr(empId,user_role,locId,companyCode));
			if(status.equals("F")) {
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE,"Forwarded For Approval");
			}
			else {
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE,"Discarded Successfully");
			}
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE,LOAD_ERROR_MESSAGE );
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/finalApproveRuleBasedAllocation")
	public Map<String, Object> finalApproveRuleBasedAllocation(@RequestParam("alloc_temp_hd_id") Long alloc_temp_hd_id,@RequestParam("division") Long division,@RequestParam("period") String period,@RequestParam("fin_year") String fin_year
															   ,@RequestParam("ucycle") String ucycle,@RequestParam("status") String status,@RequestParam("is_final") String is_final,@RequestParam("up_status") String up_status,@RequestParam("empId") String empId,
															   @RequestParam("user_role") String user_role,@RequestParam("locId") String locId,
															   @RequestParam("mode") String mode, HttpServletRequest request,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String idAdd=request.getRemoteAddr();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period periodMaster=this.periodMasterService.getObjectById(Long.valueOf(period));
			if(mode.equals("j")) {
				this.ruleBasedAllocationApprovalService.saveAndMoveToArchieve(division,periodMaster,status,up_status,ucycle,alloc_temp_hd_id,"", "N",empId,idAdd) ;
			}
			else {
				this.ruleBasedAllocationApprovalService.saveAndMoveToArchieveByProcedure(division,periodMaster,status,up_status,ucycle,alloc_temp_hd_id,"", "N",empId,idAdd) ;
			}
			map.put("approvalList", this.ruleBasedAllocationApprovalService.approvalListForSelfAppr(empId,user_role,locId,companyCode));
			if(status.equals("A")) {
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE,"Approved Successfully");
			}
			else {
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE,"Discarded Successfully");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE,LOAD_ERROR_MESSAGE );
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
}
