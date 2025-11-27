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

import com.excel.bean.UserBean;
import com.excel.service.BrandService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;


@RestController
@RequestMapping("/rest")
public class BrandRestController implements MedicoConstants{
	
	@Autowired BrandService brandService;
	@Autowired HttpSession session;
	@Autowired HttpServletRequest request;
	@Autowired private UserMasterService userMasterService;;
	@GetMapping("/get-brand-list")
	public Map<String, Object> getBrandList(@RequestParam String prefix) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			List<Object> prodList=brandService.getBrandList(prefix);

			 map.put("prodList", prodList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/saveBrand")
	public Map<String, Object> saveBrand(@RequestParam("name") String name ,	HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
	
		
		
		String empId ="";
		try {
			
			 empId = this.userMasterService.getEmployeeIdFromRequest(request);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			System.out.println("Brand Name " + name);
			System.out.println("ip " +request.getRemoteAddr());
			System.out.println("cmd " +companyCode);
			map=this.brandService.saveBrand(name,empId);
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("dataSaved",false);
			map.put("message", "Error Occured");
		};
		return map;
	}

}
