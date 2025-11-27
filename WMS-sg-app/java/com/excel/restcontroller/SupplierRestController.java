package com.excel.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.SupplierBean;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sub_Company;
import com.excel.model.Supplier;
import com.excel.service.CompanyMasterService;
import com.excel.service.LocationService;
import com.excel.service.SupplierService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class SupplierRestController implements MedicoConstants {
	@Autowired private UserMasterService userMasterService;
	@Autowired SupplierService supplierService;
	@Autowired LocationService locationService;
	@Autowired CompanyMasterService companyMasterService;
	
	@GetMapping("/getTypeForSupplierEntry")
	public Object getTypeForSupplierEntry(HttpSession session) throws Exception {
		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		Map<String,Object> map=new HashMap<String, Object>();
		List<SG_d_parameters_details> supTypeList=supplierService.getSupplierTypeForSupplierEntry();
		map.put("supTypeList", supTypeList);
		map.put("compCode", compCode);
		return map;
	}
	@GetMapping("/getSubCompanySupplierEntry")
	public Object getSubCompanySupplierEntry(HttpServletRequest request) throws Exception {
		String empId = this.userMasterService.getEmployeeIdFromRequest(request);
		
		
		
	
		System.out.println("emp id is "+empId);
		Map<String,Object> map=new HashMap<String, Object>();
		List<Sub_Company> subCompList=this.companyMasterService.getSubCompanyList(empId);
		map.put("subCompList", subCompList);
		return map;
		
	}
	@GetMapping("/getStateForSupplierEntry")
	public Object getStateForSupplierEntry() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<SG_d_parameters_details> stateList=supplierService.getStateForSupplierEntry();
		map.put("stateList", stateList);
		return map;
	}
	@GetMapping("/checkUniqueSupplier")
	public Object checkUniqueSupplier(@RequestParam String SupName,@RequestParam String SupType,@RequestParam String SubCompany){
		System.out.println("sup name is :"+SupName+".."+SupType+".."+SubCompany);
		Map<String, Object> map=new HashedMap<>();
		Boolean check=this.supplierService.checkUniqueSupplier(SupName, SupType, SubCompany);
		map.put("check", check);
		System.out.println("check data avilable or not "+check);
		return map;
	}
	@GetMapping("/checkUniqueSupplierNameForModify")
	public Object checkUniqueSupplierNameForModify(@RequestParam String SupName,@RequestParam String SupType,@RequestParam String SubCompany,@RequestParam String SupId){
		System.out.println("sup name is :"+SupName+".."+SupType+".."+SubCompany+".."+SupId);
		Map<String, Object> map=new HashedMap<>();
		Boolean check=this.supplierService.checkUniqueSupplierNameForModify(SupName, SupType, SubCompany,SupId);
		map.put("check", check);
		System.out.println("check data avilable or not "+check);
		return map;
	}
	@GetMapping("/checkUniqueGST")
	public Object checkUniqueGST(@RequestParam String gst){
		this.supplierService.checkUniqueGST(gst);
		return gst;
		
	}
	@PostMapping("/saveSupplier")
	public Object saveSupplier(@RequestBody SupplierBean supplierBean,HttpServletRequest request,@RequestParam String gst) {
		Map<String, Object> map=new HashedMap<>();
		Boolean checkSave;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			this.supplierService.saveSupplier(supplierBean, request,empId,gst);
			checkSave=true;
		} 
		catch (Exception e) {
			checkSave=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("checkSave", checkSave);
		return map;
	}
	@GetMapping("/getAllActiveSupplierList")
	public Object getAllActiveSupplierList(@RequestParam String status) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<Supplier> allActiveSupplierList=this.supplierService.getAllActiveSupplierList(status);
		map.put("allActiveSupplierList", allActiveSupplierList);
		return map;
	}
	@GetMapping("/getTextParameterSupplierList")
	public Object getTextParameterSupplierList(@RequestParam String status,@RequestParam String name,@RequestParam String parameter, @RequestParam String text) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<Supplier> allActiveSupplierList=this.supplierService.getTextParameterSupplierList(status,name,parameter,text);
		map.put("allActiveSupplierList", allActiveSupplierList);
		return map;
	}
	@GetMapping("/getSupplierDetailById")
	public Object getSupplierDetailById(@RequestParam String supId) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<Supplier> selectedSupplierDetail=this.supplierService.getSupplierDetailById(supId);
		map.put("selectedSupplierDetail", selectedSupplierDetail);
		return map;
	}
	@PostMapping("/updateSupplier")
	public Object updateSupplier(@RequestParam String supId,@RequestBody SupplierBean supplierBean,HttpServletRequest request,@RequestParam String empId) {
		Map<String, Object> map=new HashedMap<>();
		Boolean checkSave;
		try {
			this.supplierService.updateSupplier(supId,supplierBean, request,empId);
			checkSave=true;
		} 
		catch (Exception e) {
			checkSave=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("checkSave", checkSave);
		return map;
	}
}
