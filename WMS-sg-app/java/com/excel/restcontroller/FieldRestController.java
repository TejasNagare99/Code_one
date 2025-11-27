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
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.FieldStaffBean;
import com.excel.bean.ProductBean;
import com.excel.bean.UserMasterBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.Depot_Locations;
import com.excel.model.DivField;
import com.excel.model.DivMaster;
import com.excel.model.FieldStaff;
import com.excel.model.FieldStaffList;
import com.excel.model.HQ_Master;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SamplProdGroup;
import com.excel.model.Sub_Company;
import com.excel.model.TerrMaster;
import com.excel.model.Usermaster;
import com.excel.model.V_Fieldstaff_JAVA;
import com.excel.model.V_G_FieldStaff;
import com.excel.repository.UserMasterRepository;
import com.excel.service.CompanyMasterService;
import com.excel.service.DepotLocationService;
import com.excel.service.DivisionMasterService;
import com.excel.service.FieldStaffService;
import com.excel.service.HQMasterService;
import com.excel.service.ParameterService;
import com.excel.service.ProductMasterService;
import com.excel.service.SamplProdGroupService;
import com.excel.service.TerritoryService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class FieldRestController implements MedicoConstants{
	
	@Autowired ParameterService parameterService;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired FieldStaffService fieldStaffService;
	@Autowired HQMasterService hqMasterService;
	@Autowired TerritoryService territoryService;
	@Autowired DepotLocationService depotLocationService;
	@Autowired UserMasterService userMasterService;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterRepository userMasterRepository;
	
	@GetMapping("/getDataForFieldStaffEntry")
	public Object getDataForFieldStaffEntry(HttpSession session,HttpServletRequest request) {
		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		Map<String, Object> map=new HashMap<>();
		List<SG_d_parameters_details> CityList = null;
		List<SG_d_parameters_details> ZoneList = null;
		List<SG_d_parameters_details> FormList = null;
		List<SG_d_parameters_details> FsTypeList = null;
		List<SG_d_parameters_details> ClassList = null;
		List<SG_d_parameters_details> StateList = null;
		List<SG_d_parameters_details> FsTypeIndList = null;
		List<SG_d_parameters_details> ProdSubTypeList = null;
		
		List<DivMaster> divListActiveForSearch=null;
		String compcode =null;
		List<DivMaster> DivList = null;
		try {
			
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			HashMap<String,String> details = this.userMasterRepository.getUserInfo(uname,null);
			String empId = details.get("EMP_ID");
			
			DivList=this.divisionMasterService.getAllStandardDivisionList();
			CityList=this.parameterService.getCity();
			ClassList=this.parameterService.getClassList();
			FormList=this.parameterService.getForm();
			FsTypeList=this.parameterService.getFsType();
			StateList=this.parameterService.getState();
			FsTypeIndList=this.parameterService.getFsTypeInd();
			ProdSubTypeList=this.parameterService.getProductSubType();
			//allTerrCodeList=this.territoryService.getAllTerrCodeList();
			compcode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			
			divListActiveForSearch=divisionMasterService.getDivAccessedByUser(empId);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("compCode", compCode);
		map.put("CityList", CityList);
		map.put("ClassList", ClassList);
		map.put("DivList", DivList);
		map.put("FormList", FormList);
		map.put("FsTypeList", FsTypeList);
		map.put("StateList", StateList);
		map.put("FsTypeIndList", FsTypeIndList);
		map.put("ProdSubTypeList", ProdSubTypeList);
		//map.put("allTerrCodeList", allTerrCodeList);
		map.put("compcode", compcode);
		map.put("divListActiveForSearch", divListActiveForSearch);
		return map;
	}
	@GetMapping("/getAllMgr")
	public Object getAllMgr(@RequestParam String level,@RequestParam String divId,@RequestParam String trainingInd) {
		List<FieldStaff> allMgrList = null;
		Map<String, Object> map=new HashMap<>();
		try {
			allMgrList=this.fieldStaffService.getAllMgr(level,divId,trainingInd);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("allMgrList", allMgrList);
		return map;
	}
	@GetMapping("/getLevel")
	public Object getLevel(@RequestParam String divId,@RequestParam String ind) {
		Map<String, Object> map=new HashMap<>();
		List<DivField> LevelList=this.fieldStaffService.getLevel(divId,ind);
		map.put("LevelList", LevelList);
		return map;
	}
	
	@GetMapping("/getTerrCode")
	public Object getTerrCode(@RequestParam String level,@RequestParam String divId,@RequestParam String trainingInd) {
		System.out.println("details: "+level+".."+divId+".."+trainingInd);
		Map<String, Object> map=new HashMap<>();
		List<TerrMaster> terrCodeList = null;
		try {
			terrCodeList=this.territoryService.getTerrCodeList(level,divId,trainingInd);
			System.out.println(terrCodeList.size());
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("terrCodeList", terrCodeList);
		return map;
	}
	@GetMapping("/getTerrCodeForModify")
	public Object getTerrCodeForModify(@RequestParam String level,@RequestParam String divId,@RequestParam String trainingInd) {
		System.out.println("details: "+level+".."+divId+".."+trainingInd);
		Map<String, Object> map=new HashMap<>();
		List<TerrMaster> terrCodeList = null;
		try {
			terrCodeList=this.territoryService.getTerrCodeListForModify(level,divId,trainingInd);
			System.out.println(terrCodeList.size());
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("terrCodeList", terrCodeList);
		return map;
	}
	@GetMapping("/getTerrDetail")
	public Object getTerrDetail(@RequestParam String mgrId,@RequestParam String level,@RequestParam String divId) {
		Map<String, Object> map=new HashMap<>();
		List<FieldStaffBean> terrMgrOneList = new ArrayList<FieldStaffBean>();
		try {
			System.out.println("mgr is:"+mgrId+" divId"+divId+" level : "+level);
			terrMgrOneList=fieldStaffService.getFstaffDetail(mgrId, level,divId);
			System.out.println("terrMgrOneList:::"+terrMgrOneList.size());
			map.put("terrMgrOneList", terrMgrOneList);

		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/getHQList")
	public Object getHQList(@RequestParam String divId) {
		Map<String, Object> map=new HashMap<>();
		List<HQ_Master> HQlist=new ArrayList<HQ_Master>();
		HQlist=hqMasterService.getHqListForFieldStaffEntry(divId);
		List<SG_d_parameters_details> ZoneList =this.parameterService.getZone(Long.valueOf(divId));
		System.out.println("ZoneList "+ZoneList.size());
		map.put("HQlist", HQlist);
		map.put("ZoneList", ZoneList);
		return map;
	}
//	@GetMapping("/getFSDetail")
//	public Object getFSDetail(@RequestParam String divId,@RequestParam String levelCode) {
//		Map<String, Object> map=new HashMap<>();
//		List<FieldStaff> FSlist=new ArrayList<FieldStaff>();
//		FSlist=fieldStaffService.getFSDetail(divId, levelCode);
//		map.put("FSlist", FSlist);
//		return map;
//	}
//	@GetMapping("/getMgrDetail")
//	public Object getMgrDetail(@RequestParam String vfsId) {
//		Map<String, Object> map=new HashMap<>();
//		List<V_Fieldstaff_JAVA> VFSlist=new ArrayList<V_Fieldstaff_JAVA>();
//		VFSlist=this.fieldStaffService.getMgrDetail(vfsId);
//		map.put("VFSlist", VFSlist);
//		return map;
//	}
	@GetMapping("/getRequestor")
	public Object getRequestor(@RequestParam String divId) {
		Map<String, Object> map=new HashMap<>();
		List<FieldStaff> RFSlist=new ArrayList<FieldStaff>();
		RFSlist=this.fieldStaffService.getRequestor(divId);
		map.put("RFSlist", RFSlist);
		return map;
	}
	@GetMapping("/checkUniqueGenericName")
	public Object checkUniqueGenericName(@RequestParam String genName,@RequestParam String divId) {
		Map<String, Object> map=new HashMap<>();
		Boolean Data;
		Data=this.fieldStaffService.checkUniqueGenericName(genName,divId);
		map.put("Data", Data);
		return map;
	}
	@GetMapping("/getCFALocationsForFieldstaffEntry")
	public Object getCFALocationsForFieldstaffEntry() {
		Map<String, Object> map=new HashMap<>();
		List<Depot_Locations> CFALocationListOne=new ArrayList<Depot_Locations>();
		List<Depot_Locations> CFALocationListTwo=new ArrayList<Depot_Locations>();
		List<Depot_Locations> CFALocationListThree=new ArrayList<Depot_Locations>();
		try {
			CFALocationListOne=this.depotLocationService.getCFALocationsOne();
			CFALocationListTwo=this.depotLocationService.getCFALocationsTwo();
			CFALocationListThree=this.depotLocationService.getCFALocationsThree();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("CFALocationListOne", CFALocationListOne);
		map.put("CFALocationListTwo", CFALocationListTwo);
		map.put("CFALocationListThree", CFALocationListThree);
		
		return map;
	}
	@PostMapping("/saveFieldStaffMaster")
	public Object saveFieldStaffMaster(@RequestBody FieldStaffBean fb,@RequestParam String empId,@RequestParam String locId,HttpSession session,HttpServletRequest request) throws Exception {
		System.out.println("In the save method "+locId);
		Map<String, Object> map=new HashMap<>();
		Boolean checkSave;
		try {
			this.fieldStaffService.saveFieldStaff(fb, empId,locId, session, request);
			checkSave=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			checkSave=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("checkSave", checkSave);
		return map;
	}
	@PostMapping("/updateFieldStaffMaster")
	public Object updateFieldStaffMaster(@RequestParam String fstaffId,@RequestBody FieldStaffBean fb,@RequestParam String empId,@RequestParam String locId,HttpSession session,HttpServletRequest request) throws Exception {
		System.out.println("In the save method "+locId);
		Map<String, Object> map=new HashMap<>();
		Boolean checkSave;
		try {
			this.fieldStaffService.updateFieldStaff(fstaffId,fb, empId,locId, session, request);
			checkSave=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			checkSave=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("checkSave", checkSave);
		return map;
	}
	
	@GetMapping("/getActiveFieldStaffDetailList")
	public Object getActiveFieldStaffDetailList() {
		Map<String, Object> map=new HashMap<>();
		List<V_G_FieldStaff> ActiveFieldStaffDetailList=null;
		try {
			ActiveFieldStaffDetailList=this.fieldStaffService.getAllActiveFieldStaffDetailList();
		
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("ActiveFieldStaffDetailList", ActiveFieldStaffDetailList);
		return map;
	}
	@GetMapping("/getTextParameterFstaffDetail")
	public Object getTextParameterFstaffDetail(@RequestParam String name,@RequestParam String parameter
			,@RequestParam String text,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<>();
		List<V_G_FieldStaff> ActiveFieldStaffDetailList=null;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			HashMap<String,String> details = this.userMasterRepository.getUserInfo(uname,null);
			String empId = details.get("EMP_ID");
			ActiveFieldStaffDetailList=this.fieldStaffService.getTextParameterFstaffDetail(name,parameter,text,empId);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("ActiveFieldStaffDetailList", ActiveFieldStaffDetailList);
		return map;
	}
	
	
	@GetMapping("/getFstaffDetailById")
	public Object getFstaffDetailById(@RequestParam String id) {
		Map<String, Object> map=new HashMap<>();
		List<FieldStaff> selectedFstaffDetail=null;
		try {
			selectedFstaffDetail=this.fieldStaffService.getFstaffDetailById(id);
			List<SG_d_parameters_details> ZoneList =this.parameterService.getZone(selectedFstaffDetail.get(0).getFs_div_id());

			map.put("selectedFstaffDetail", selectedFstaffDetail);
			map.put("ZoneList", ZoneList);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getLockUser")
	public Object getLockUser() {
		Map<String, Object> map=new HashMap<>();
		List<UserMasterBean> list=null;
		try {
			list=this.userMasterService.getLockUser();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("userList", list);
		return map;
	}
	
	@GetMapping("/unlockUser")
	public Object unlockUser(@RequestParam("tran_id") List<String> ids) {
		Map<String, Object> map=new HashMap<>();
		List<UserMasterBean> list=null;
		try {
			this.userMasterService.unlockUserOnRequest(ids,false);
			list=this.userMasterService.getLockUser();
			map.put("isSaved", true);
			map.put("message", "Updated Successfully");
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("isSaved", false);
			map.put("message", "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}
	
	@PostMapping("/updateFstaffMobileNoUpload")
	public Map<String, Object> updateFstaffMobileNoUpload(@RequestParam MultipartFile file, @RequestParam String username, 
			HttpServletRequest request, HttpSession session ) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("file " + file.getOriginalFilename());
			String ipAddress = request.getRemoteAddr();
			if (file.getOriginalFilename().endsWith(".csv")) {
				map = this.fieldStaffService.updateFstaffMobileNo(file, username,ipAddress);

				
	        	map.put("fileName",map.get("fileName"));
	        	
				map.put("FILE_EXIST", map.get("FILE_EXIST"));

			} else {
				map.put("msg", "Please upload files in correct format");
			}
		}

		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			e.printStackTrace();
			map.put("msg", "File Processing Failed");
		}
		return map;
	}
	
	
}
