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

import com.excel.bean.TransporterBean;
import com.excel.model.Company;
import com.excel.model.Sub_Company;
import com.excel.model.Transporter_master;
import com.excel.repository.CompanyMasterRepository;
import com.excel.service.TransporterMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class TransporterRestController implements MedicoConstants{
	@Autowired TransporterMasterService transporterMasterService;
	@Autowired CompanyMasterRepository compMasterRepo;
	@Autowired private UserMasterService userMasterService;
	
	@GetMapping("/get-transporter-id")
	public Map<String, Object> gettransporter(@RequestParam Long loc_id,@RequestParam Long sub_comp_id,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Transporter_master> list=null;
		try {
			Sub_Company sb = compMasterRepo.getSubCompanyObjectById(sub_comp_id);
			list =transporterMasterService.gettransportermasterForLocation(loc_id);
			
			map.put("transporter", list);
			map.put("company", sb.getSubcomp_nm());
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@PostMapping("/saveTransporterMaster")
	public Map<String,Object> saveHqMaster(@RequestBody TransporterBean transBean,HttpServletRequest request,HttpSession session) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		
		
		String empId = this.userMasterService.getEmployeeIdFromRequest(request);
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		String msg = null;
		msg = this.transporterMasterService.saveTransDetail(transBean, empId, request,companyCode);
		
		map.put("msg", msg);
		//System.out.println(trans_code);
		//map.put("trans_code", trans_code);
		return map;
	}

	
}
