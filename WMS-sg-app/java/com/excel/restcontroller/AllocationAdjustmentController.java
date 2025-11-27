package com.excel.restcontroller;

import java.util.HashMap;
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

import com.excel.bean.AllocationAdjustmentBean;
import com.excel.service.AllocationAdjustmentService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class AllocationAdjustmentController implements MedicoConstants{
	
	@Autowired AllocationAdjustmentService allocationAdjustmentService;
	

	
	@GetMapping("/get-allocAdj-allocList")
	public Map<String, Object>  getAllocList(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map.put("allocList", this.allocationAdjustmentService.getAllocList(companyCode));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;

	}
	@GetMapping("/get-allocAdj-productList")
	public Map<String, Object> getProductList(@RequestParam("allocid") String allocid,@RequestParam("divId") String divId, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("productList", this.allocationAdjustmentService.getProdList(allocid, divId));
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-allocAdj-divisionList")
	public Map<String, Object> getDivisionList(@RequestParam("allocid") String allocid,
			@RequestParam("empId")String empId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map.put("divisionList", this.allocationAdjustmentService.getDivForAllocAdj(allocid,companyCode,empId));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-allocAdj-allocBy-productList")
	public Map<String, Object> getSupplierTypesbyGrnType(@RequestParam String allocid,@RequestParam String divId,@RequestParam String prodId,@RequestParam String alMode,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map.put("list", this.allocationAdjustmentService.getFSForAllocAdj(allocid, divId, prodId, alMode, companyCode));
			if(alMode.equals("2"))
				map.put("name","State");
			else if(alMode.equals("3"))
				map.put("name","Region");
			else if(alMode.equals("4"))
				map.put("name","District");
			else if(alMode.equals("5"))
				map.put("name","PSO");
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-alloc-adjustment")
	public 	Map<String,Object> saveAdjusment(@RequestBody AllocationAdjustmentBean bean, HttpSession session, HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		try {
			bean.setIpAddress(request.getRemoteAddr());
			if (bean.getAllocMode().equals("0")) {
				bean.setSaveMode("E");
				bean.setCfalocid("0");
			} else if (bean.getAllocMode().equals("1")) {
				bean.setSaveMode("L");
			} else if (bean.getAllocMode().equals("2")) {
				bean.setSaveMode("S");
			} else if (bean.getAllocMode().equals("3")) {
				bean.setSaveMode("R");
			} else if (bean.getAllocMode().equals("4")) {
				bean.setSaveMode("A");
			} else if (bean.getAllocMode().equals("5")) {
				bean.setSaveMode("M");
			}
			map=this.allocationAdjustmentService.saveAdjustment(bean);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	

}
