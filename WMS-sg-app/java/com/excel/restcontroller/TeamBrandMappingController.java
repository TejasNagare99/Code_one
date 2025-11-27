package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.ProductBean;
import com.excel.bean.ReportBean;
import com.excel.bean.TeamBrandMappingBean;
import com.excel.configuration.JwtProvider;
import com.excel.exception.MedicoException;
import com.excel.model.Company;
import com.excel.model.DIV_TEAM_BRAND;
import com.excel.model.DivMaster;
import com.excel.model.Location;
import com.excel.model.Lr_Expenses_Report;
import com.excel.model.Sub_Company;
import com.excel.model.Team;
import com.excel.service.DivisionMasterService;
import com.excel.service.ReportService;
import com.excel.service.TeamBrandMappingService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class TeamBrandMappingController implements MedicoConstants{
	
	@Autowired private ReportService reportService;
	@Autowired private DivisionMasterService divisionMasterService;
	@Autowired private TeamBrandMappingService teambrandmappingservice;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;
	@PostMapping("/save-team-data-mapping")
	public Map<String, Object> getTeamData(@RequestBody TeamBrandMappingBean bean,HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		DIV_TEAM_BRAND team = null;
		String userId=bean.getEmp_id();
		bean.setUserId(userId);
		String ip_addr = request.getRemoteAddr();
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		try {
			team=teambrandmappingservice.saveTeamData(bean,companyCode,userId,ip_addr);		
			map.put("list", team);
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}
	
	@GetMapping("/get-div-list-tempBrandMapping")
	public Map<String, Object> getDivListByUserIdtempBrandmapping(@RequestParam("userid") String userid,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String userId= userid; //"E00040"; // get it from session
		List<DivMaster> divList=null;
		try {
			divList=teambrandmappingservice.getDivIdList();
			DivMaster dm=new DivMaster();
		//	dm.setDiv_id(0l);
		//	dm.setDiv_disp_nm("All");
		//	divList.add(0, dm);
			map.put("divList", divList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	@GetMapping("/get-team-code-data")
	public Map<String, Object> getTeamCodeData(@RequestParam("divId") String divId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Team> team=null;
		try {
			System.out.println("divId:::"+divId);
			team=teambrandmappingservice.getTeamCodeData(divId);
			map.put("team", team);
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-selected-brand-list")
	public Object selectedBrandList(@RequestParam("empId") String empId,@RequestParam("divId") String divId,@RequestParam("teamCode") String teamId,
			HttpSession session) {
		Map<String, Object> map=new HashMap<String, Object>();
		List<Long> selectedBrand =  null;
		try {
			selectedBrand = teambrandmappingservice.getSelectedBrandList( empId,divId,teamId);
			map.put("selectedBrand", selectedBrand);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put("message", "Error Occured");
		};
		return map;
	}
	
	@GetMapping("/get-selected-team-id")
	public Map<String, Object> getSelectedTeamId(@RequestParam("divId") String divId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Team> selectedTeam=null;
		try {
			TeamBrandMappingBean bean =new TeamBrandMappingBean();
			System.out.println("divId:::"+divId);
			String team_code=bean.getTeam_code();
			System.out.println("team code seleted:::::"+bean.getTeam_code());
			selectedTeam=teambrandmappingservice.getSelectedTeamId(divId);
			map.put("selectedTeam", selectedTeam);
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/get-team-brand-mapping-report")
	public Map<String, Object> getTeamBrand_report(@RequestBody TeamBrandMappingBean bean,HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<TeamBrandMappingBean> lst = null;
		String filename = null;
		try {

String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
			String company = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			lst = teambrandmappingservice.getTeamBrandReportdata(bean);
			System.out.println("list:::"+lst.size());
			if(lst != null && !lst.isEmpty()) {
				filename = teambrandmappingservice.generate_team_brand_Report(lst,company,empId);
				map.put("filename", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + filename);
			}
			map.put("list", lst);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-division-list-team-mapping")
	public Map<String, Object> getDivListByUserId(@RequestParam("userid") String userid,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String userId= userid; //"E00040"; // get it from session
		List<DivMaster> divList=null;
		try {
			divList=divisionMasterService.getDivIdList();
//			DivMaster dm=new DivMaster();
//			dm.setDiv_id(0l);
//			dm.setDiv_disp_nm("All");
//			divList.add(0, dm);
			map.put("divList", divList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-team-code-data-team-brand-mapping")
	public Map<String, Object> getTeamCodeDataForTeamBrandMapping(@RequestParam("divId") String divId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<TeamBrandMappingBean> team=null;
		try {
			System.out.println("divId:::"+divId);
			team=teambrandmappingservice.getTeamCodeDataForTeamBrandMapping(divId);
			map.put("team", team);
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getTeamCodebyTeamIdData")
	public Object getTeamByTeamId(@RequestParam("teamId") String teamId) {
		Map<String, Object> map=new HashMap<String, Object>();
		Team team = null;
		try {
			System.out.println("teamId::::"+teamId);
			team = teambrandmappingservice.getTeamCodeByTeamId(teamId);
			map.put("team", team);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getTeamCodeDataForTeamMaster")
	public Map<String, Object> getTeamCodeDataForTeamMaster(@RequestParam("divId") String divId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String team=null;
		try {
			System.out.println("divId:::"+divId);
			team=teambrandmappingservice.getTeamCodeDataForTeamMaster(divId);
			map.put("team", team);
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getTeamNamesForTeamMaster")
	public Map<String, Object> getTeamNamesForTeamMaster(@RequestParam("divId") String divId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Team> team=null;
		try {
			System.out.println("divId:::"+divId);
			team=teambrandmappingservice.getTeamNamesForTeamMaster(divId);
			map.put("team", team);
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-Team-Master")
	public Map<String, Object> saveTeamMaster(@RequestBody TeamBrandMappingBean bean,HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		
		String msg = null;
		try {
			msg=teambrandmappingservice.saveTeamMasterData(bean);	
			map.put("msg", msg);
			map.put(DATA_SAVED, true);
		} 
		catch (MedicoException e) {
			// TODO: handle exception
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
		}
		return map;
	}
	
	@GetMapping("/getduplicateTeamName")
	public Map<String, Object> getgetduplicateTeamName(@RequestParam("divId") String divId,@RequestParam("team_name") String team_name,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Tuple> team=null;
		
		try {
			System.out.println("team_name:::"+team_name);
			team=teambrandmappingservice.getDuplicateTeamName(divId,team_name);
			if(team!=null &&  team.size()!=0) {
					map.put("Indicator", true);
			}else {
				map.put("Indicator", false);
			}
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getduplicateTeamShortName")
	public Map<String, Object> getgetduplicateTeamShortName(@RequestParam("divId") String divId,@RequestParam("shortTeamName") String shortTeamName,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Tuple> team=null;
		
		try {
			System.out.println("shortTeamName:::"+shortTeamName);
			team=teambrandmappingservice.getDuplicateTeamShortName(divId,shortTeamName);
			if(team!=null &&  team.size()!=0) {
					map.put("Indicator", true);
			}else {
				map.put("Indicator", false);
			}
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getduplicateTeamCode")
	public Map<String, Object> getgetduplicateTeamCode(@RequestParam("divId") String divId,@RequestParam("team_code") String team_code,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<Tuple> team=null;
		
		try {
			System.out.println("divId:::"+divId);
			team=teambrandmappingservice.getDuplicateTeamCode(divId,team_code);
			if(team!=null &&  team.size()!=0) {
					map.put("Indicator", true);
			}else {
				map.put("Indicator", false);
			}
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getBrandForTeamBrandMapping")
	public Map<String, Object> getBrandsForTeamBrandMapping(@RequestParam("team_code") String team_code,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<TeamBrandMappingBean> list =new ArrayList<>();
		try {
			list = teambrandmappingservice.getBrandsForTeamBrandMapping(team_code);
			
			map.put("BrandList", list);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getProductListProductMaster")
	public Map<String, Object> getProductListProductMaster(@RequestParam("divId") String divId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		List<ProductBean> productsList=null;
		try {
			System.out.println("divId:::"+divId);
			productsList=teambrandmappingservice.getProductListProductMaster(divId);
			map.put("productsList", productsList);
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-div-list-productDivMapping")
	public Map<String, Object> getDivListByUserIdproductDivMapping(@RequestParam("userid") String userid,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String userId= userid; //"E00040"; // get it from session
		List<DivMaster> divList=null;
		try {
			divList=teambrandmappingservice.getDivIdListproductDivMapping();
			DivMaster dm=new DivMaster();
		//	dm.setDiv_id(0l);
		//	dm.setDiv_disp_nm("All");
		//	divList.add(0, dm);
			map.put("divList", divList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
}
