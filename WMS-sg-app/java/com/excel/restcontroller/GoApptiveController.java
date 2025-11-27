package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.GoApptiveFieldstaffBean;
import com.excel.bean.GoapptiveTeamBrandbean;
import com.excel.model.GoApptiveDspDetails;
import com.excel.service.GoApptiveService;
import com.excel.service.TeamService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class GoApptiveController implements MedicoConstants {
	
	@Autowired GoApptiveService goApptiveService;
	@Autowired TeamService teamservice;

	@PostMapping("/goApptiveFieldstaff")
	public  Map<String, Object>  addGoApptiveFieldstaff(@RequestBody Map<String,List<GoApptiveFieldstaffBean>> confirmationBean, HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		Map<String, Object> failedFieldstaff=new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			List<GoApptiveFieldstaffBean> bean= confirmationBean.get("fieldstaff");
			for(int i=0;i<bean.size();i++) {
				try {
					bean.get(i).setCompany_code(companyCode);
					this.goApptiveService.addGoApptiveFieldstaff(bean.get(i));
				}
				catch (Exception e) {
					failedFieldstaff.put(bean.get(i).getFstaff_employee_no(),e.getMessage());
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
					e.printStackTrace();
				}
			}
			//write data in main master
			this.goApptiveService.pushDataToMainFiedldstaff();
			//update indicator
			this.goApptiveService.updateRecords();
			
			map.put(DATA_RECIEVED,true);
			map.put("failedFieldstaff",failedFieldstaff);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(DATA_RECIEVED,false);
		}
		return map;
	}
	

	@GetMapping("/territoryDetailsForGoapptive")
	public Map<String, Object> territoryGoApptive() {
		Map<String, Object> map = new HashMap<>();
		try {
			goApptiveService.getGoaptiveTerrMaster();
		}
		catch (Exception e) {
			// TODO: handle exception
			map.put("STATUS", "ERROR");
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/dispatchDetailsForGoapptive")
	public Map<String, Object> SampledisptogoApptive() {
		Map<String, Object> map = new HashMap<>();
		try {
			goApptiveService.getDispatchDetailsTerrMaster();
		
		}
		catch (Exception e) {
			// TODO: handle exception
			map.put("STATUS", "ERROR");
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/get-Goaptive-teambrand-data")
	public Map<String, Object> getteambranddata(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<GoapptiveTeamBrandbean> list = null;
	try {
		 goApptiveService.getTeamBrandDetails("2021");
		
		map.put("list", list);
	}
	catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
	}
	return map;
	}
	
	
//	@GetMapping("/test")
//	public  Map<String, Object> test1() {
//		Map<String, Object> map = new HashMap<>();
//		try {
//			map.put("A",goApptiveService.getTerrDetailByTerrCode("XA03T"));
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			
//		}
//		return map;
//	}
}
