package com.excel.restcontroller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.AllocationUploadBean;
import com.excel.bean.DoctorUploadXlsxBean;
import com.excel.bean.FieldStaffAllocationUploadBean;
import com.excel.exception.MedicoException;
import com.excel.model.Company;
import com.excel.model.FieldStaff;
import com.excel.model.Fieldstaff_mobileno_update_log;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.service.DoctorUploadService;
import com.excel.service.FieldStaffAllocationUploadService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class FieldStaffRestController implements MedicoConstants{
	@Autowired DoctorUploadService doctorUploadService;
	@Autowired FieldStaffAllocationUploadService fieldstaffallocationuploadservice;
	@Autowired private FieldStaffRepository fieldStaffRepository;
	@Autowired TransactionalRepository transactionalRepository;
	
	@PostMapping("/upload-Fieldstaff-upload")
	public  Map<String, Object>  UploadFieldStaffUpload(@RequestParam MultipartFile file,@RequestParam String finyearid,@RequestParam String alloc_mode,@RequestParam String inputType,
            @RequestParam String division_id,@RequestParam String period_id,@RequestParam String userid,@RequestParam String includestock,@RequestParam String  csvuploaded,HttpSession session, 
            @RequestParam String role,@RequestParam String sub_team_code,HttpServletRequest request) {
			Map<String,Object> map = new HashMap<String, Object>();
		try {
			Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			FieldStaffAllocationUploadBean bean = new FieldStaffAllocationUploadBean();
			
			bean.setFileUpload(file);
			bean.setDivision_id(division_id);
			bean.setUserid(userid);
			bean.setComp_code(companyCode);
			bean.setPeriod_id(period_id);
			bean.setFinYear(finyearid);
			bean.setInputType(inputType);
			bean.setCsvuploaded(csvuploaded);
			bean.setFileUploadFileName(file.getOriginalFilename());
			bean.setIncludestock(includestock);
			bean.setIp_address(request.getRemoteAddr());
			bean.setEmp_id(userid);
			bean.setAlloc_mode(alloc_mode.charAt(0));
			bean.setSamp_disp_ind(company.getSamp_disp_ind());
			bean.setRole(role);	
			bean.setSub_team_code(sub_team_code);
			
		    map=this.fieldstaffallocationuploadservice.saveuploadalFeldstaffAllocationXlsx(bean);
			   
		    if(!(boolean) map.get(DATA_SAVED)) {	
		    	new MedicoException(map.get(RESPONSE_MESSAGE).toString());
		    }
		    
			System.out.println("After Save");
		    if(map.get(UNIQUE_NUMBER)!=null) {
		    	String compcode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		    	AllocationUploadBean allocbean=new AllocationUploadBean();
		    	allocbean.setComp_code(compcode);
		    	allocbean.setFinYear(bean.getFinYear());
		    	allocbean.setPeriod_id(bean.getPeriod_id());
		    	allocbean.setUserid(bean.getUserid());
		    	allocbean.setIp_address(bean.getIp_address());
		    	this.doctorUploadService.createAllocation(allocbean, map.get(UNIQUE_NUMBER).toString());
		    }
		    map.put(DATA_SAVED, true);
		 
   
		//	map.put(RESPONSE_MESSAGE,map.get(RESPONSE_MESSAGE));
			map.put(UNIQUE_NUMBER, map.get(UNIQUE_NUMBER));
		}
		catch (Exception e) {
			// TODO: handle exception
			map.put(DATA_SAVED, false);
			DoctorUploadXlsxBean dux = new DoctorUploadXlsxBean();
			String notUpload ="File Not Uploaded Successfully";
			map.put(RESPONSE_MESSAGE,notUpload);
			String cust = dux.getCustId1();
			System.out.println("cust"+cust);
			map.put("CUSTID",cust);
			
			String docName = dux.getHcpnames();
			System.out.println("docName"+docName);
			map.put("DCNAME",docName);
			
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateFstaffMobile(List<Fieldstaff_mobileno_update_log> fstaffMobileList, String fileName,
			String username, String ipAddress) throws Exception {

		for (Fieldstaff_mobileno_update_log data : fstaffMobileList) {
			FieldStaff fs =fieldStaffRepository.updateMobileNoById(data.getFstaff_mobile(), data.getFstaff_id());
			
			fs.setFstaff_mobile(data.getFstaff_mobile());
			fs.setFstaff_mod_dt(new Date());
			fs.setFstaff_mod_ip_add(ipAddress);
			fs.setFstaff_mod_usr_id(username);
			this.transactionalRepository.update(fs);

			this.transactionalRepository.persist(data);

		}
		

	}
}