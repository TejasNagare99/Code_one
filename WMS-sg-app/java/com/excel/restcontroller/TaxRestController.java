package com.excel.restcontroller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.TaxBean;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.TaxMaster;
import com.excel.service.ParameterService;
import com.excel.service.TaxService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class TaxRestController implements MedicoConstants{
	@Autowired ParameterService parameterService;
	@Autowired TaxService taxService;
	@Autowired private UserMasterService userMasterService;
	
	@GetMapping("/getDataForTaxEntry")
	public Object getDataForTaxEntry(HttpSession session) {
		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		Map<String, Object> map=new HashMap<>();
		List<SG_d_parameters_details> StateList = null;
		try {
			StateList=this.parameterService.getState();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("compCode", compCode);
		map.put("StateList", StateList);
		return map;
	}
	@GetMapping("/checkUniqueDesc")
	public Object checkUniqueDesc(@RequestParam String stateId,@RequestParam String hsnCode) {
		Map<String, Object> map=new HashMap<>();
		Boolean dataAvailable = null;
		try {
			dataAvailable=this.taxService.checkUniqueDesc(stateId, hsnCode);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("dataAvailable", dataAvailable);
		return map;
	}
//	@PostMapping("/saveTax")
//	public Object saveTax(@RequestBody TaxBean tb) {
//		Map<String, Object> map=new HashMap<>();
//		Boolean dataSaved;
//		try {
//			this.taxService.saveTax(tb);
//			dataSaved=true;
//		}
//		catch (Exception e) {
//			dataSaved=false;
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		map.put("dataSaved", dataSaved);
//		return map;
//	}
	
	@PostMapping("/saveTax")
	public Object saveTax(HttpServletRequest request,@RequestParam List<Integer> taxIdList,@RequestBody TaxBean tb,@RequestParam List<Integer> stateIdList) {
		Map<String, Object> map=new HashMap<>();
		Boolean dataSaved=false;
		Boolean Exist=null;
		
		
		
		System.err.println("stateIdList:::"+stateIdList);
		System.err.println("taxIdList:::"+taxIdList);
		
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			tb.setEmpId(empId);
		
			if(!this.taxService.hsn_Exist(tb.getHsnCode())) {
				this.taxService.saveTax(taxIdList,tb,stateIdList);
				dataSaved=true;
				Exist=false;
			}else {
				Exist=true;
			}
		}
		catch (Exception e) {
			dataSaved=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("dataSaved", dataSaved);
		map.put("Exist", Exist);
		
		return map;
	}
	
	
	@GetMapping("/getHsnCodeListByStateId")
	public Object getHsnCodeListByStateId(@RequestParam String stateId) {
		Map<String, Object> map=new HashMap<>();
		List<TaxMaster> HsnCodeList = null;
		try {
			HsnCodeList=this.taxService.getHsnCodeListByStateId(stateId);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("HsnCodeList", HsnCodeList);
		return map;
	}
	
	@GetMapping("/getAllTaxDetail")
	public Object getAllTaxDetail() {
		Map<String, Object> map=new HashMap<>();
		List<TaxMaster> allTaxList = null;
		try {
			allTaxList=this.taxService.getAllTaxDetail();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("allTaxList", allTaxList);
		return map;
	}
	@PostMapping("/updateTax")
	public Object updateTax(	HttpServletRequest request,@RequestParam List<Integer> taxIdList,@RequestBody TaxBean tb,@RequestParam List<Integer> stateIdList) {
		Map<String, Object> map=new HashMap<>();
		//System.out.println("list of tax id :"+taxIdList);
		Boolean dataSaved=false;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			tb.setEmpId(empId);
			this.taxService.updateTax(taxIdList,tb,stateIdList);
			dataSaved=true;
		}
		catch (Exception e) {
			dataSaved=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("dataSaved", dataSaved);
		return map;
	}
}
