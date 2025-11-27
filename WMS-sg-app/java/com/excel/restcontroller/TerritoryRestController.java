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

import com.excel.bean.TerritoryBean;
import com.excel.model.Depot_Locations;
import com.excel.model.DivField;
import com.excel.model.DivMaster;
import com.excel.model.FieldStaff;
import com.excel.model.HQ_Master;
import com.excel.model.Location;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Team;
import com.excel.model.TerrMaster;
import com.excel.model.V_Terr_Master;
import com.excel.service.DepotLocationService;
import com.excel.service.DivisionMasterService;
import com.excel.service.FieldStaffService;
import com.excel.service.HQMasterService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.TeamService;
import com.excel.service.TerritoryService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class TerritoryRestController implements MedicoConstants{
	
	@Autowired ParameterService parameterService;
	@Autowired HQMasterService hqMasterService;
	@Autowired TeamService teamService;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired FieldStaffService fieldStaffService;
	@Autowired TerritoryService territoryService;
	@Autowired LocationService locationService;
	@Autowired DepotLocationService depotLocationService;
	
	@GetMapping("/getDataForTerritoryEntry")
	public Object getDataForTerritoryEntry(HttpSession session) {
		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		Map<String, Object> map=new HashMap<>();
		List<SG_d_parameters_details> stateList=null;
		List<DivMaster> divList = null;
		try {
			stateList=parameterService.getState();
			divList=this.divisionMasterService.getAllStandardDivisionList();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("compCode", compCode);
		map.put("stateList", stateList);
		map.put("divList", divList);
		return map;
	}
	@GetMapping("/getHqList")
	public Object getHqList(@RequestParam Long divId) {
		Map<String, Object> map=new HashMap<>();
		List<HQ_Master> hqList=null;
		List<SG_d_parameters_details> zoneList=null;
		try {
			hqList=hqMasterService.getHqListForFieldStaffEntry(divId.toString());
			zoneList=parameterService.getZone(divId);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("hqList", hqList);
		map.put("zoneList", zoneList);
		return map;
	}
	
	@GetMapping("/getTeam")
	public Object getTeam(@RequestParam Long divId) {
		Map<String, Object> map=new HashMap<>();
		List<Team> teamList=null;
		String teamReqInd = "";
		try {
			teamReqInd= teamService.getTeamReqInd(divId);
			
//			if(teamReqInd=="Y") {
			if(teamReqInd.trim().equals("Y")) {
				teamList=teamService.getTeamList(divId);				
			}
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("teamReqInd", teamReqInd);
		map.put("teamList", teamList);
		return map;
	}
	@GetMapping("/getLevelForTerritory")
	public Object getLevelForTerritory(@RequestParam String divId) {
		Map<String, Object> map=new HashMap<>();
		List<DivField> levelList=this.territoryService.getLevelForTerritory(divId);
		map.put("levelList", levelList);
		return map;
	}
	@GetMapping("/getLevelForTerritoryModify")
	public Object getLevelForTerritoryModify(@RequestParam String divId,@RequestParam String fsCategory) {
		Map<String, Object> map=new HashMap<>();
		List<DivField> levelList=this.territoryService.getLevelForTerritoryModify(divId,fsCategory);
		map.put("levelList", levelList);
		return map;
	}
	@GetMapping("/checkUniqueTerrCode")
	public Object checkUniqueTerrCode(@RequestParam String divId,@RequestParam String terrCode) {
		Map<String, Object> map=new HashMap<>();
		Boolean data;
		data=this.territoryService.checkUniqueTerrCode(divId, terrCode);
		map.put("data", data);
		return map;
	}
	
	@GetMapping("/getMgr")
	public Object getMgr(@RequestParam String levelCode,@RequestParam String trainingInd,@RequestParam String divId){
		int level=Integer.parseInt(levelCode)+1;
		String newLevel= Integer.toString(level);
		String level_code=String.format("%03d",level);
		System.out.println(level_code);
		System.out.println(trainingInd);
		System.out.println(divId);
		Map<String, Object> map=new HashMap<>();
		List<TerrMaster> terrNameList = null;
		try {
			terrNameList=this.territoryService.getMgr(level_code,trainingInd,divId);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("terrNameList", terrNameList);
		return map;
	}
	@GetMapping("/getMgrTwo")
	public Object getMgrTwo(@RequestParam String mgrId) {
		System.out.println("mgr is :"+mgrId);
		Map<String, Object> map=new HashMap<>();
		List<TerrMaster> terrNameList = null;
		try {
			terrNameList=this.territoryService.getMgrTwo(mgrId);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("terrNameList", terrNameList);
		return map;
	}
	@GetMapping("/getCFALocations")
	public Object getCFALocations() {
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
	@PostMapping("/saveTerritory")
	public Object saveTerritory(@RequestBody TerritoryBean tb,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		Boolean DataSaved=false;
		try {
			this.territoryService.saveTerritory(tb, request);
			DataSaved=true;
		}
		catch (Exception e) {
			DataSaved=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("DataSaved", DataSaved);
		System.out.println("Data saved?"+map);
		return map;
	}
	
	@GetMapping("/getAllTerrDetailList")
	public Object getAllTerrDetailList(@RequestParam String company) {
		Map<String, Object> map=new HashMap<>();
		List<V_Terr_Master> AllTerrDetailList = null;
		try {
			AllTerrDetailList=this.territoryService.getAllTerrCodeList(company);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("AllTerrDetailList", AllTerrDetailList);
		return map;
	}
	@GetMapping("/getTextParameterTerrDetail")
	public Object getTextParameterTerrDetail(@RequestParam String company,@RequestParam String name,@RequestParam String parameter,@RequestParam String text) {
		Map<String, Object> map=new HashMap<>();
		List<V_Terr_Master> AllTerrDetailList = null;
		try {
			AllTerrDetailList=this.territoryService.getTextParameterTerrDetail(company,name,parameter,text);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("AllTerrDetailList", AllTerrDetailList);
		return map;
	}
	@GetMapping("/getTerrDetailById")
	public Object getTerrDetailById(@RequestParam String terrId) {
		Map<String, Object> map=new HashMap<>();
		List<TerrMaster> TerrDetailById = null;
		List<SG_d_parameters_details> zoneList=null;
		try {
			TerrDetailById=this.territoryService.getTerrDetailById(terrId);
			zoneList=parameterService.getZone(TerrDetailById.get(0).getTerr_div_id());
			map.put("zoneList", zoneList);
			map.put("TerrDetailById", TerrDetailById);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/updateTerritory")
	public Object updateTerritory(@RequestParam String terrId,@RequestBody TerritoryBean tb,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		Boolean DataSaved=false;
		try {
			System.out.println("TerrId : "+terrId);
			
			this.territoryService.updateTerritory(terrId,tb, request);
			DataSaved=true;
		}
		catch (Exception e) {
			DataSaved=false;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("DataSaved", DataSaved);
		System.out.println("Data saved?"+map);
		return map;
	}
	
	@GetMapping("/getTeamCodebyTeamId")
	public Object getTeamByTeamId(@RequestParam("teamId") String teamId) {
		Map<String, Object> map=new HashMap<String, Object>();
		Team team = null;
		try {
			team = territoryService.getTeamCodeByTeamId(teamId);
			map.put("team", team);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/TeamIdbyFsId")
	public Object getTeamByFsId(@RequestParam("fsId") String fsId) {
		Map<String, Object> map=new HashMap<String, Object>();
		Long teamid = null;
		try {
			teamid = territoryService.getTeamIdByFsId(fsId);
			map.put("teamid", teamid.toString());
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/TeamIdbyHqId")
	public Object getTeamByHqId(@RequestParam("hqId") String hqId) {
		Map<String, Object> map=new HashMap<String, Object>();
		Long hqid = null;
		try {
			hqid=territoryService.getTeamIdByHqId(hqId);
			map.put("hqid", hqid.toString());
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/validateAbolishTerritoryCodeUpload")
	public Map<String, Object> validateFileBeforeSaving(@RequestParam MultipartFile file, @RequestParam String username, 
			HttpServletRequest request, HttpSession session ) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("file " + file.getOriginalFilename());
			if (file.getOriginalFilename().endsWith(".csv")) {
				map = this.territoryService.validateCsvFile(file, username);

				map.put("csvCount", map.get("csvCount"));
	        	map.put("errCount", map.get("errCount"));
	        	map.put("terrCount", map.get("terrCount"));
	        	map.put("terrFieldCount", map.get("terrFieldCount"));
	        	map.put("terrWithoutFieldCount", map.get("terrWithoutFieldCount"));
	        	map.put("fileName",map.get("fileName"));
	        	
				map.put("FILE_EXIST", map.get("FILE_EXIST"));
				map.put("msg",map.get("message"));

			} else {
				map.put("msg", "Please upload files in correct format");
			}
		}

		catch (Exception e) {
			map.put("msg", "File Processing Failed");
			e.printStackTrace();
		}
		return map;
	}
	
	
	

	
	@PostMapping("/Territory-master-entry-csv")
	public Map<String, Object> terry_master_entry_csv(@RequestParam MultipartFile file, @RequestParam String username,
			HttpServletRequest request, HttpSession session, @RequestParam String divId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean result=false;
		try {	
			System.out.println("file " + file.getOriginalFilename());
			System.out.println("username " + username);
	
			String ip_addres=request.getRemoteAddr();
			
			if (file.getOriginalFilename().endsWith(".csv")) {
			Boolean status=this.territoryService.checkFileExist_on_terr_master_upload_template_table(file.getOriginalFilename());
			if(!status) {
				map	=this.territoryService.saveTerritoy_master_csv(file,username,divId,ip_addres);
				map.put("File_exist", false);
			}else {
				map.put("File_exist", true);
			}
			
			}else {
				map.put("msg", "Please upload files in correct format");
			}
		}
		catch (Exception e) {
			map.put("result", false);
			map.put("File_exist", false);
			e.printStackTrace();
		}
		return map;
	}
	
	
	
}
