
package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.DashboardBean;
import com.excel.bean.DoctorBean;
import com.excel.bean.HQBean;
import com.excel.model.DivMaster;
import com.excel.model.DoctorMaster;
import com.excel.model.EmployeeDetails;
import com.excel.model.FieldStaff;
import com.excel.model.HQ_Master;
import com.excel.model.Period;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.V_HQ_Master;
import com.excel.service.DashBoardService;
import com.excel.service.DivisionMasterService;
import com.excel.service.DoctorMasterService;
import com.excel.service.HQMasterService;
import com.excel.service.ParameterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class DoctorMasterController implements MedicoConstants {
	
      	@Autowired DoctorMasterService doctorMasterService;
		@Autowired DivisionMasterService divisionMasterService;
		@Autowired ParameterService parameterService;
		@Autowired HQMasterService hqMasterService;
		@Autowired DashBoardService dashBoardService;
		
		@GetMapping("/getDivisionForDoctorMaster")
		public Object getDivisionForDoctorMaster(HttpSession session) {
			String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
			Map<String,Object> map =new HashMap<>();
			List<DivMaster> divisionList=new ArrayList<>();
			//List<SG_d_parameters_details> stateList=new ArrayList<>();
			try {
				divisionList=this.doctorMasterService.getStandardDivisionList();
				//stateList=this.parameterService.getState();
				System.out.println(divisionList.size());
				map.put("divisionList", divisionList);
				map.put("compCode", compCode);
				//map.put("stateList", stateList);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		
		
		@GetMapping("/getDoctorList")
		public Object getDoctorList(@RequestParam("divId") String divId) {
			Map<String, Object> map=new HashMap<>();
			List<FieldStaff> dlist=new ArrayList<FieldStaff>();
			try {
				dlist=doctorMasterService.getDoctorsForFieldStaffEntry(divId);
			} catch (Exception e) {
				
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			
			  List<SG_d_parameters_details> ZoneList=this.parameterService.getZone(Long.valueOf(divId));
			  System.out.println("ZoneList "+ZoneList.size());
			 
			map.put("fieldstafflist", dlist);
			map.put("ZoneList", ZoneList);
			return map;
		}
	
		@PostMapping("/save-DoctorMaster")
		public Map<String,Object> saveDoctorMaster(@RequestBody DoctorBean docBean,HttpServletRequest request)  {
			Map<String, Object> map=new HashMap<String, Object>();
			List<EmployeeDetails> lst;
			try {
				String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
				String ip_addr = request.getRemoteAddr(); //to get ip addres
				 docBean.setCompanyCode(companyCode);
				docBean.setIpaddress(ip_addr);
				docBean.setFullName(docBean.getFirstName()+" "+docBean.getLastName());
				System.out.println("EmpId :: "+docBean.getEmpId());
				EmployeeDetails user=this.doctorMasterService.getEmployeeDetails(docBean.getFieldstaffcategory());
				//System.out.println("user :: "+user.getEmp_id());
				
				//lst = this.doctorMasterService.getEmpIdByFStaffId(Long.valueOf(docBean.getFieldstaffcategory()));
			//	System.out.println("list  : "+lst.size());
//				if(lst.size() != 0) {
//				System.out.println("str : "+lst.get(0).getEmp_id());
//				docBean.setFstaff_emp_num(lst.get(0).getEmp_id());
//				}
				docBean.setFstaff_emp_num(docBean.getFieldstaffcategory());
				docBean.setFstaff_id(docBean.getFieldstaffcategory());
				doctorMasterService.savDocDetail(docBean);
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE, "Doctor Saved Successfully.");
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				map.put(DATA_SAVED, false);
				map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			}
			return map;
		}
		
		@GetMapping("/getAllActiveDoctorMasterDetail")
		public Object getAllActiveDoctorMasterDetail() {
			Map<String,Object> map =new HashMap<>();
			List<DoctorMaster> activeDoctorList=new ArrayList<DoctorMaster>();
			
			try {
				activeDoctorList=this.doctorMasterService.getAllActiveDoctorMasterDetail();	
				map.put("activeDoctorList", activeDoctorList);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		
		@GetMapping("/getDoctorTextParameterDetail")
		public Object getHqTextParameterDetail(@RequestParam("name") String name,@RequestParam("parameter") String parameter,@RequestParam("text") String text,@RequestParam("empId") String empId) {
			Map<String,Object> map =new HashMap<>();
			List<DoctorMaster> activeDoctorList=new ArrayList<DoctorMaster>();

			try {
				activeDoctorList=this.doctorMasterService.getDoctorTextParameterDetail(name,parameter,text,empId);	
				map.put("activeDoctorList", activeDoctorList);
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		
		@GetMapping("/getDoctorDetailById")
		public Object getDoctorDetailById(@RequestParam String doc_id) {
			Map<String,Object> map =new HashMap<>();
			DoctorMaster DoctorDetailList=null;
			FieldStaff fs = new FieldStaff();
			DivMaster divm=new DivMaster();
			Map<Long,String> fsnmandid = new HashMap<>() ;
			
			try {
				System.out.println("doc_id:::"+doc_id);
				DoctorDetailList=this.doctorMasterService.getDoctorDetailById(doc_id);	
				System.out.println("fstaff id:::"+DoctorDetailList.getFstaff_id());
				if(DoctorDetailList.getFstaff_id()!=0) {
				fs = this.doctorMasterService.getObjectfieldstaffbyId(DoctorDetailList.getFstaff_id());
				System.out.println("fstaff Div id:::"+fs.getFs_div_id());
				divm = this.doctorMasterService.getObjectDivmasterbyId(fs.getFs_div_id());
				fsnmandid.put(fs.getFstaff_id(), fs.getFstaff_emp_num());
				//[fs.getFstaff_id(),fs.getFstaff_emp_num()]
				}
				else {
					divm.setDiv_id(0l);
				}
				map.put("DoctorDetailList", DoctorDetailList);
				map.put("fieldstaff", fsnmandid);
				map.put("divmaster", divm.getDiv_id());
				
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		@PostMapping("/updateDoctorMaster")
		public Object updateDoctorMaster(@RequestParam String doc_id,@RequestBody DoctorBean docBean,@RequestParam String empId,HttpServletRequest request) {
			Map<String,Object> map =new HashMap<>();
			List<EmployeeDetails> lst ;
			try {
				String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
				String ip_addr = request.getRemoteAddr(); //to get ip addres
				 docBean.setCompanyCode(companyCode);
				docBean.setIpaddress(ip_addr);
				docBean.setFstaff_emp_num(docBean.getFieldstaffcategory());

				this.doctorMasterService.updateDoctorMaster(doc_id,docBean,empId,request);
				map.put("message", "SUCCESS");
			} catch (Exception e) {
				map.put("message", "ERROR");
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		
		@GetMapping("/get-fs-data-doctor-entry")
		public Map<String, Object> getFsData(@RequestParam String empId) {
			Map<String, Object> map = new HashMap<>();
			try {
				List<DashboardBean> userData=dashBoardService.getFsDetailsByEmpId(empId);
				map.put("user", userData.get(0));
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
		}
		
}
