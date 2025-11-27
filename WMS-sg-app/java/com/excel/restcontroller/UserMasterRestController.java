package com.excel.restcontroller;

import java.util.Calendar;
import java.util.Date;
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

import com.excel.bean.Parameter;
import com.excel.bean.TerritoryBean;
import com.excel.bean.UserBean;
import com.excel.bean.UserBrandMapopingBean;
import com.excel.bean.UserMasterBean;
import com.excel.model.Company;
import com.excel.model.Depot_Locations;
import com.excel.model.DivMaster;
import com.excel.model.EmployeeDetails;
import com.excel.model.Location;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SamplProdGroup;
import com.excel.model.UserDepartment;
import com.excel.model.Usermaster;
import com.excel.repository.CompanyMasterRepository;
import com.excel.repository.UserDepartmentRepository;
import com.excel.security.PasswordManager;
import com.excel.service.DepotLocationService;
import com.excel.service.DivMasterService;
import com.excel.service.DivisionMasterService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.UserDepartmentService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@RestController
@RequestMapping("/rest")
public class UserMasterRestController implements MedicoConstants{

	@Autowired UserDepartmentService userDepartmentService;
	@Autowired UserMasterService userMasterService;
	@Autowired CompanyMasterRepository companyMasterRepository;
	@Autowired LocationService locationService;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired ParameterService parameterService;
	@Autowired DepotLocationService depotLocationService;
	@Autowired UserDepartmentRepository userDepartmentRepository;
	
	@GetMapping("/dataForUserMasterEntry")
	public Object dataForUserMasterEntry(@RequestParam("saveInd") String saveInd) {
		Map<String, Object> map=new HashMap<String, Object>();
		Boolean DataSaved=false;
		List<UserDepartment> deptList=null;
		List<Parameter> desgList=null;
		List<Parameter> mgrList=null;
		List<Parameter> groupList=null;
		List<Company> compList=null;
		List<Location> locList=null;
		List<DivMaster> divList=null;
		List<SG_d_parameters_details> prodTypeList=null;
		List<Depot_Locations> depotLocations=null;
		List<UserMasterBean> userList=null;
		String login_valid_from=null;
		String login_valid_to=null;
		System.out.println("Save Ind"+saveInd);
		try {
			deptList = userDepartmentService.getDepartments();
			desgList = userDepartmentService.getActiveDesignation();
			mgrList = userDepartmentService.getActiveUserList();
			groupList = userDepartmentService.getActiveUserDtl();
			compList =companyMasterRepository.getCompanyList();
			locList = locationService.getAllLocations();
			prodTypeList = parameterService.getProductTypes();
			divList = divisionMasterService.getAllStandardDivisionList();
			depotLocations = depotLocationService.getDepotLocations(0L, "0");
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			cal.add(Calendar.YEAR, 1); // to get previous year add -1
			Date nextYear = cal.getTime();
			login_valid_from = Utility.convertDatetoString(today);
			login_valid_to = Utility.convertDatetoString(nextYear);
			if(saveInd.equals("M")) {
				userList=userMasterService.getActiveUserList();
				map.put("userList", userList);
			}
			map.put("deptList", deptList);
			map.put("desgList", desgList);
			map.put("mgrList", mgrList);
			map.put("groupList", groupList);
			map.put("compList", compList);
			map.put("locList", locList);
			map.put("prodTypeList", prodTypeList);
			map.put("divList", divList);
			map.put("depotLocations", depotLocations);
			map.put("login_valid_from", login_valid_from);
			map.put("login_valid_to", login_valid_to);
			
		}
		catch (Exception e) {
			DataSaved=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		};
		return map;
	}
	
	
	@GetMapping("/checkUniqueUsername")
	public Map<String, Object> checkUniqueUsername(@RequestParam("userName") String userName) {
		Map<String, Object> map=new HashMap<String, Object>();
		Usermaster user=null;
		try {
			user=this.userMasterService.getUserByUsername(userName);
			if(user!= null) {
				map.put("isPresent", true);
			}else {
				map.put("isPresent", false);
			}
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		};
		return map;
	}
	
	@PostMapping("/saveUserMaster")
	public Object saveUserMaster(@RequestBody UserBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		Boolean dataSaved=true;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			map=this.userMasterService.saveUserMaster(bean);
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("dataSaved",false);
			map.put("message", "Error Occured");
		};
		return map;
	}
	
	@GetMapping("/dataForModifyUser")
	public Object saveUserMaster(@RequestParam("mod_empId") String userId) {
		Map<String, Object> map=new HashMap<String, Object>();
		EmployeeDetails empDtl = null;
		Usermaster userDtl = null;
		List<Long> locAccess = null;
		List<Long> divAccess =  null;
		List<Long> divRptAccess =  null;
		List<Long> prodType = null;
		System.out.println("userId "+userId);
		try {
			if(userId!=null && ("").equalsIgnoreCase("")){
				empDtl = this.userMasterService.getEmployeeDetails(userId);
				String emp_id = empDtl.getEmp_id();
				System.out.println("EMP_ID:: "+emp_id);
				userDtl = this.userMasterService.getUserByEmpId(emp_id);
				locAccess = this.userDepartmentRepository.getEmpLocAccess(emp_id);
				divAccess = this.userDepartmentRepository.getEmpDivAccess( emp_id);
				divRptAccess = this.userDepartmentRepository.getEmpDivReportAccess(emp_id);
				prodType = this.userDepartmentRepository.getEmpProdTypeAccess(emp_id);
			}
			
			map.put("empDtl", empDtl);
			map.put("userDtl", userDtl);
			map.put("locAccess", locAccess);
			map.put("divAccess", divAccess);
			map.put("divRptAccess", divRptAccess);
			map.put("prodType", prodType);
			map.put("password", "");//PasswordManager.decrypt(PasswordManager.decrypt(userDtl.getLd_pass_ang())));
			//System.out.println("password "+PasswordManager.decrypt(PasswordManager.decrypt(userDtl.getLd_pass_ang())));
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("message", "Error Occured");
		};
		return map;
	}
	
	@PostMapping("/updateUserMaster")
	public Object updateUserMaster(@RequestBody UserBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			map=this.userMasterService.updateUserMaster(bean);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("dataSaved",false);
			map.put("message", "Error Occured");
		};
		return map;
	}
	
	
	@GetMapping("/getUserRole")
	public Object getUserRole() {
		Map<String, Object> map=new HashMap<String, Object>();
		List<Parameter> roles=null;
		try {
			roles = userDepartmentService.getActiveUserDtl();
			map.put("roles", roles);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		};
		return map;
	}
	
	@GetMapping("/getUserByRole")
	public Object getUserByRole(@RequestParam String role) {
		Map<String, Object> map=new HashMap<String, Object>();
		List<Parameter> users=null;
		try {
			users = userDepartmentService.getUserByRole(role);
			map.put("users", users);
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		};
		return map;
	}
	
	@GetMapping("/getUserExistingBrand")
	public Object getUserExistingBrand(HttpServletRequest request,@RequestParam String empId) {
		Map<String, Object> map=new HashMap<String, Object>();
		List<Long> userExistingBrand=null;
		try {
			
//			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			userExistingBrand = userDepartmentService.getUserExistingBrand(empId);
			map.put("userExistingBrand", userExistingBrand);
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		};
		return map;
	}
	
	@GetMapping("/brandsByDivisionOfUser")
	public Object getBrandsByDivisionOfUser(	HttpServletRequest request ) {
		Map<String, Object> map=new HashMap<String, Object>();
		List<SamplProdGroup> brandsByUserDivision=null;
		try {
			
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			brandsByUserDivision = userDepartmentService.getBrandsByDivisionOfUser(empId);
			map.put("brandsByUserDivision", brandsByUserDivision);
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		};
		return map;
	}
	
	@PostMapping("/saveBrandsForUser")
	public Object saveBrandsForUser(@RequestBody UserBrandMapopingBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		Boolean dataSaved=true;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			map=this.userMasterService.saveUserBrandMapping(bean);
			
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("dataSaved",false);
			map.put("message", "Error Occured");
		};
		return map;
	}
	
	@GetMapping("/dispatchLockinRelease")
	public Object getDispatchLockinRelease() {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			System.out.println("inside api");
		    this.userDepartmentRepository.getDispatchLockinRelease();
		    map.put("dataSaved",true);
			map.put("message", "Dispatch LockIn Released successfully");
			System.out.println("inside api completed::;");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("dataSaved",false);
			map.put("message", "Error Occured");
		}
		return map;
	}
	
	@GetMapping("/get-all-locations")
	public  List<Location>  getLocation() {
		
		
		try {
			return locationService.getAllLocations();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@GetMapping("/dispatch-specific-LockinRelease")
	public Object getDispatch_specific_LockinRelease(@RequestParam("locId") List<Long> locId) {
		Map<String, Object> map=new HashMap<String, Object>();
		
		System.err.println(locId);
		try {
		    this.userDepartmentRepository.getDispatch_specific_LockinRelease(locId);
		    map.put("dataSaved",true);
			map.put("message", "Dispatch LockIn Released successfully");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("dataSaved",false);
			map.put("message", "Error Occured");
		}
		return map;
	}
	
}
