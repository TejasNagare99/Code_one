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

import com.excel.bean.ProductwiseTaxMasterBean;
import com.excel.model.DivMaster;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sub_Company;
import com.excel.service.CompanyMasterService;
import com.excel.service.DivisionMasterService;
import com.excel.service.ParameterService;
import com.excel.service.ProductMasterService;
import com.excel.service.ProductwiseTaxMasterService;
import com.excel.service.TaxService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class ProductwiseTaxRestController implements MedicoConstants{
	@Autowired ParameterService parameterService;
	@Autowired CompanyMasterService companyMasterService;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired ProductMasterService productMasterService;
	@Autowired TaxService taxService;
	@Autowired ProductwiseTaxMasterService productwiseTaxMasterService;
	@Autowired private UserMasterService userMasterService;
	@GetMapping("/getDataForProductwiseTaxMaster")
	public Object getDataForProductwiseTaxMaster(HttpSession session,HttpServletRequest request) {
		
		
		
		
		
		String empId="";
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		List<Sub_Company> subCompanyList = null;
		List<DivMaster> divList = null;
		List<SG_d_parameters_details> productTypeList = null;
		Map<String, Object> map=new HashMap<>();
		try {
			subCompanyList=this.companyMasterService.getSubCompanyList(empId);
			divList=this.divisionMasterService.getAllStandardDivisionList();
			
			productTypeList=this.parameterService.getProductTypes();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("compCode", compCode);
		map.put("divList", divList);
		map.put("subCompanyList", subCompanyList);
		map.put("productTypeList", productTypeList);
		return map;
		
	}
	@GetMapping("/getProdctNameList")
	public Object getProdctNameList(@RequestParam String divId,@RequestParam String prodTypeId,
			@RequestParam String subCompId,@RequestParam String hsnCode) {
		Map<String, Object> map=new HashMap<>();
		List<Object> productNameList = null;
		try {
			productNameList=this.productMasterService.getProductDetailForProductwiseTaxMaster(divId, prodTypeId, subCompId, hsnCode);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("productNameList", productNameList);
		return map;
	}
	@GetMapping("/getHSNCode")
	public Object getHSNCode(@RequestParam String prodCode,@RequestParam String Company) {
		Map<String, Object> map=new HashMap<>();
		List<Object> hsnCodeList = null;
		try {
			hsnCodeList=this.productMasterService.getHSNCode(prodCode, Company);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("hsnCodeList", hsnCodeList);
		return map;
	}
	@GetMapping("/getTaxList")
	public Object getTaxList(@RequestParam String prodCode,@RequestParam String compCode,
			@RequestParam String hsnCode,HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		List<Object> taxList = null;
		try {
			compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
			taxList=this.productMasterService.getTaxDetailForProductwiseTaxMaster(prodCode, compCode, hsnCode);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("taxList", taxList);
		return map;
	}
	@GetMapping("/getTaxDescr")
	public Object getTaxDescr(@RequestParam String taxId,@RequestParam String hsnCode) {
		Map<String, Object> map=new HashMap<>();
		List<Object> taxDescr = null;
		try {
			taxDescr=this.taxService.getTaxDesc(taxId, hsnCode);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("taxDescr", taxDescr);
		return map;
	}
	@GetMapping("/checkDetail")
	public Object checkDetail(@RequestParam String prodCode,@RequestParam String Company) {
		
		Map<String, Object> map=new HashMap<>();
		Boolean checkDetail = null;
		try {
			checkDetail=this.productwiseTaxMasterService.checkDetail(prodCode, Company);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("checkDetail", checkDetail);
		return map;
	}
	
	@PostMapping("/saveProductWiseTaxMaster")
	public Object saveProductWiseTaxMaster(HttpServletRequest request,@RequestBody ProductwiseTaxMasterBean pwt,HttpSession session,@RequestParam List<Object> stateIdList,@RequestParam List<Object> taxCodeList) {
		System.out.println("state is list size "+stateIdList);
		System.out.println("tax code l;ist "+taxCodeList);
		
//		for(int i=0;i<stateIdList.size();i++) {
//			System.out.println("state id 1 detail is "+stateIdList.get(i));
//			System.out.println("tax code 1 detail is "+taxCodeList.get(i));
//		}
		Map<String, Object> map=new HashMap<>();
		Object dataSaved = null;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			pwt.setEmpId(empId);
			dataSaved=this.productwiseTaxMasterService.saveProduct(pwt, session,stateIdList,taxCodeList);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("dataSaved", dataSaved);
		return map;
	}
}

