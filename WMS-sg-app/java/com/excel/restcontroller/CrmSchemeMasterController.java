package com.excel.restcontroller;

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

import com.excel.bean.CrmSchemeMasterBean;
import com.excel.model.CrmSchemeMaster;
import com.excel.model.CustomerMaster;
import com.excel.model.Location;
import com.excel.model.SG_d_parameters_details;
import com.excel.service.CrmSchemeMasterService;
import com.excel.service.LocationService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class CrmSchemeMasterController implements MedicoConstants {

	@Autowired
	LocationService locationService;
	@Autowired
	CrmSchemeMasterService crmschememasterservice;

	@GetMapping("/getOnLoadCRMData")
	public Map<String, Object> getOnLoadCRMData(@RequestParam("emp_id") String emp_id) {
		Map<String, Object> map = new HashMap<>();
		List<SG_d_parameters_details> docClass = null;
		List<SG_d_parameters_details> schemeType = null;
		List<CrmSchemeMaster> scheme_code=null;
		try {
			List<Location> location = this.locationService.getAllLocations();
			docClass = crmschememasterservice.getDocClass();
			schemeType = crmschememasterservice.getSchemeType();
			scheme_code = crmschememasterservice.getScheme_Code();
			map.put("location", location);
			map.put("docClass", docClass);
			map.put("schemeType", schemeType);
			map.put("scheme_code", scheme_code);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@PostMapping("/onSubmitCrmMaster")
	public Map<String, Object> saveCrm(@RequestBody CrmSchemeMasterBean crmmast, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		CrmSchemeMaster data = null;
		List<CrmSchemeMaster> scheme_code=null;
		String msg;
		try {
			System.out.println("crm Master " + crmmast);
			data = crmschememasterservice.SaveCRMMaster(crmmast, session);
			msg = "Saved Successfully";
			scheme_code = crmschememasterservice.getScheme_Code();

			// map.put("crm_scheme_id", crm_scheme_id);
//			System.out.println("crm_scheme_id " + data.getCrm_scheme_id());
//			map.put("crm_scheme_id", data.getCrm_sch_id());
			map.put("msg", msg);
			map.put("scheme_code", scheme_code);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();// uncomment asneeded --;
		}

		return map;
	}

}
