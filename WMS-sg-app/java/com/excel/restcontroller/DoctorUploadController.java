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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.AllocationUploadBean;
import com.excel.bean.DoctorUploadXlsxBean;
import com.excel.exception.MedicoException;
import com.excel.model.Company;
import com.excel.model.Pg_Doc_Alloc_Template;
import com.excel.model.Pg_Doc_Alloc_Template_Error;
import com.excel.service.DoctorUploadService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;
@RestController
@RequestMapping("/rest")
public class DoctorUploadController implements MedicoConstants{

	@Autowired DoctorUploadService doctorUploadService;
	@Autowired SendMail sendMail;
	
	@PostMapping("/uploadDoctor")
	public Map<String, Object>  uploadDoctor(@RequestParam MultipartFile file,@RequestParam String finyearid,@RequestParam String alloc_mode,@RequestParam String inputType,
			                                     @RequestParam String division_id,@RequestParam String period_id,@RequestParam String userid,@RequestParam String includestock,@RequestParam String  csvuploaded,HttpSession session, 
			                                     @RequestParam String role,@RequestParam String sub_team_code,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Pg_Doc_Alloc_Template> list = new ArrayList<Pg_Doc_Alloc_Template>();
		List<Pg_Doc_Alloc_Template_Error> errorList = new ArrayList<Pg_Doc_Alloc_Template_Error>();
		Map<String, Object> vmap = new HashMap<>();
		String err = null;
		try {
		 	Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			AllocationUploadBean bean=new AllocationUploadBean();
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
			
			map =this.doctorUploadService.validateDoctorallocation(bean);
			
			if((boolean) map.get("Errflag")) {	
//				err = map.get(ERRORMSG).toString()+" MCL :"+map.get("MCLNO").toString()
//						+" HCP NAME : "+map.get("HCPNAME").toString()+map.get("ADDR").toString();
//				map.put(RESPONSE_MESSAGE, err);
				new MedicoException(err);
			}
			
		    map=this.doctorUploadService.saveuploadalDoctorAllocationXlsx(bean);
		   
		    if(!(boolean) map.get(DATA_SAVED)) {	
		    	new MedicoException(map.get(RESPONSE_MESSAGE).toString());
		    }
		    
			System.out.println("After Save");
		    if(map.get(UNIQUE_NUMBER)!=null) {
		    	String compcode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		    	bean.setComp_code(compcode);
		    	this.doctorUploadService.createAllocation(bean, map.get(UNIQUE_NUMBER).toString());
		    }
		    map.put(DATA_SAVED, true);
		 
   
		//	map.put(RESPONSE_MESSAGE,map.get(RESPONSE_MESSAGE));
			map.put(UNIQUE_NUMBER, map.get(UNIQUE_NUMBER));
			
		}
		catch (Exception e) {
		
			System.out.println("In Catch");
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
			
			try {
		//		this.doctorUploadService.deleteDoctorUploadByUniqueNumber(Long.valueOf(map.get(UNIQUE_NUMBER).toString()));
			} catch (Exception e1) {
				
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		finally {
			try {
				Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
				String subject = "Doctor Uploads";
				String[] userMail = {"samruddha@excelsof.com","milind.datar@excelsof.com"};
				if(map.get(UNIQUE_NUMBER)!=null) {
					list = doctorUploadService.getListofPgDocAllocTemp(map.get(UNIQUE_NUMBER).toString());
					errorList = doctorUploadService.getListofPgDocAllocTempErrorList(map.get(UNIQUE_NUMBER).toString());
					String filepath=doctorUploadService.generateDoctorUploadReport(list, errorList, company.getCompany());
					sendMail.uploadDoctorMail(list,errorList, subject, userMail,company.getCompany(),filepath);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		return map;
	}
	
	@GetMapping("/doctorUploadApproval")
	public Map<String, Object> ruleBasedSelfApprovalData(@RequestParam("empId") String empId,@RequestParam("user_role") String user_role,@RequestParam("locId") String locId) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp_id::" + empId);
		try {
			map.put("approvalList", this.doctorUploadService.approvalListForSelfAppr(empId,user_role,locId));

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/doctorUploadDetails")
	public Map<String, Object> doctorUploadDetails(@RequestParam("division") Long division,@RequestParam("period") Long period,
			@RequestParam("up_status") String up_status,@RequestParam("ucycle") String ucycle,@RequestParam("allocHdTempId")Long allocHdTempId) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> detail = new HashMap<>();
		try {
			map.put("allocationDetail", this.doctorUploadService.approvalDetails( period, allocHdTempId));
			detail=this.doctorUploadService.getProductWiseStock(division, period, up_status, ucycle,allocHdTempId);
			map.put("productWiseDetail",detail.get("productWiseDetail") );
			map.put("allowApproval",detail.get("allowApproval") );
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/approvaDoctorUpload")
	public Map<String, Object> selfApproveRuleBasedAllocation(@RequestParam("allocGenTempId") Long allocGenTempId,@RequestParam("status") String status,@RequestParam("empId") String empId,@RequestParam("user_role") String user_role,@RequestParam("locId") String locId) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("allocGenTempId "+allocGenTempId+" status "+status);
			this.doctorUploadService.updateApprovalStatus(allocGenTempId, status);
			map.put("approvalList", this.doctorUploadService.approvalListForSelfAppr(empId,user_role,locId));
			if(status.equals("F")) {
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE,"Forwarded For Approval");
			}else if(status.equals("A")) {
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE,"Allocation Approved Successfully");
			}
			else {
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE,"Discarded Successfully");
			}
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE,LOAD_ERROR_MESSAGE );
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
}
