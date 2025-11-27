package com.excel.restcontroller;

import java.util.ArrayList;
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

import com.excel.bean.HQBean;
import com.excel.model.DivMaster;
import com.excel.model.HQ_Master;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sub_Company;
import com.excel.model.V_HQ_Master;
import com.excel.service.DivisionMasterService;
import com.excel.service.HQMasterService;
import com.excel.service.ParameterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class HQRestController implements MedicoConstants{
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired ParameterService parameterService;
	@Autowired HQMasterService hqMasterService;
	
	@GetMapping("/getDivisionForHqMaster")
	public Object getDivisionForHqMaster(HttpSession session) {
		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		Map<String,Object> map =new HashMap<>();
		List<DivMaster> divisionList=new ArrayList<>();
		List<SG_d_parameters_details> stateList=new ArrayList<>();
		try {
			divisionList=this.divisionMasterService.getAllStandardDivisionList();
			stateList=this.parameterService.getState();
			System.out.println(divisionList.size());
			map.put("divisionList", divisionList);
			map.put("compCode", compCode);
			map.put("stateList", stateList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/checkUniqueHQName")
	public Object checkUniqueHQName(@RequestParam String hqName,@RequestParam String divId,HttpSession session) {
		Boolean data = null;
		Map<String,Object> map =new HashMap<>();
		try {
			data=this.hqMasterService.checkUniqueHQName(hqName, divId);
			map.put("data", data);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/checkUniqueHQNameForModify")
	public Object checkUniqueHQNameForModify(@RequestParam String hqName,@RequestParam String divId,@RequestParam String hqId) {
		Boolean data = null;
		Map<String,Object> map =new HashMap<>();
		try {
			data=this.hqMasterService.checkUniqueHQNameForModify(hqName, divId,hqId);
			map.put("data", data);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@PostMapping("/saveHqMaster")
	public Object saveHqMaster(@RequestBody HQBean hqBean,@RequestParam String empId,HttpServletRequest request) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		Object hqCode=this.hqMasterService.saveHQDetail(hqBean, empId, request);
		System.out.println(hqCode);
		map.put("hqCode", hqCode);
		return map;
	}
	@GetMapping("/getAllActiveHqMasterDetail")
	public Object getAllActiveHqMasterDetail() {
		Map<String,Object> map =new HashMap<>();
		List<V_HQ_Master> activeHQList=new ArrayList<V_HQ_Master>();
		
		try {
			activeHQList=this.hqMasterService.getAllActiveHqMasterDetail();	
			map.put("activeHQList", activeHQList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/getHqTextParameterDetail")
	public Object getHqTextParameterDetail(@RequestParam String name,@RequestParam String parameter,@RequestParam String text) {
		Map<String,Object> map =new HashMap<>();
		List<V_HQ_Master> activeHQList=new ArrayList<V_HQ_Master>();
		try {
			activeHQList=this.hqMasterService.getHqTextParameterDetail(name,parameter,text);	
			map.put("activeHQList", activeHQList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/getHqDetailById")
	public Object getHqDetailById(@RequestParam String hqId) {
		Map<String,Object> map =new HashMap<>();
		List<HQ_Master> HQDetailList=new ArrayList<HQ_Master>();
		try {
			HQDetailList=this.hqMasterService.getHqDetailById(hqId);	
			map.put("HQDetailList", HQDetailList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@PostMapping("/updateHQMaster")
	public Object updateHQMaster(@RequestParam String hqId,@RequestBody HQBean hb,@RequestParam String empId,HttpServletRequest request) {
		Map<String,Object> map =new HashMap<>();
		try {
			Object hqCode=this.hqMasterService.updateHQMaster(hqId,hb,empId,request);	
			map.put("hqCode", hqCode);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
}
