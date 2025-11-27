package com.excel.restcontroller;

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

import com.excel.bean.UsageInstructionBean;
import com.excel.model.Period;
import com.excel.model.SmpItem;
import com.excel.model.Usage_Instruction;
import com.excel.service.AllocationService;
import com.excel.service.PeriodMasterService;
import com.excel.service.Product_Usage_InstructionService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class ProductUsageInstructionRestController implements MedicoConstants{
	
	@Autowired
	AllocationService allocationService;

	@Autowired
	PeriodMasterService periodMasterService;	
	
	@Autowired 
	Product_Usage_InstructionService product_usage_instructionservice;
	
	@GetMapping("/getmonthofuse")
	public Map<String, Object> getmonthofuse(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		Period period = this.periodMasterService.getPeriodMasterByCompany(comp_code);
		
		map.put("monthofuselist", this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));

		return map;
	}
	
	
	@GetMapping("/getactivedivisions")
	public Map<String, Object> getActiveDivision(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			map.put("divlist", this.product_usage_instructionservice.getActivedivisionlist());
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
		
		
	}
	
	@GetMapping("/getproductsbydivid")
	public Map<String, Object> getProductbyDivision(@RequestParam("divid") String divid,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			List<SmpItem> itemlist = null;
			
			try {
				itemlist = product_usage_instructionservice.getproductsbydiv(divid);
				
				map.put("itemList", itemlist);
				}
			catch(Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/Check-usage-instruction")
	public Map<String, Object> getUsageInstruction(@RequestParam("prod_id") String prod_id,@RequestParam("monthofuse") String monthofuse,@RequestParam("finyr") String finyr,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		
		Usage_Instruction usi = null;
		boolean checkusage = false;
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		
			System.out.println("prod_id  "+prod_id);
			System.out.println("monthofuse  "+monthofuse);
			System.out.println("finyr  "+finyr);
			System.out.println("comp_code  "+comp_code);
			
			usi = product_usage_instructionservice.checkusageinstruction(prod_id, monthofuse, finyr, comp_code);
			
			if(usi != null) {
				checkusage = true;
				map.put("usage_instruction", usi.getUsage_instruction());
				map.put("checkusage", checkusage);
				map.put("usage_id", usi.getUsage_id());
			}
			else {
				
				checkusage = false;
				map.put("usage_instruction", " ");
				map.put("checkusage", checkusage);
			}
			
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
		
	}
	
	
	@PostMapping("/Save-usage-instruction")
	public Map<String,Object> Saveusageinstruction(@RequestBody UsageInstructionBean bean,HttpSession session,HttpServletRequest request){
		
		Map<String, Object> map = new HashMap<>();
		String msg;
		
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String ip_add = request.getRemoteAddr();
			
			bean.setCompanycode(comp_code);
			bean.setIp_addr(ip_add);
			
			
			msg = product_usage_instructionservice.SaveUsageinstruction(bean);
			
			if(bean.isCheckusage()==false) {
				map.put("Status", false);
			}
			if(bean.isCheckusage()==true){
				map.put("Status", true);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
		
	}
	
	
}
