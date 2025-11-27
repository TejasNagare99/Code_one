package com.excel.restcontroller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.DashboardBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.ActivityApproval;
import com.excel.model.ActivityNotification;
import com.excel.model.ApprovalTrackingData;
import com.excel.model.Company;
import com.excel.model.DashboardCharts;
import com.excel.model.DashboardCharts2;
import com.excel.model.DashboardChartsWithSku;
import com.excel.model.FieldStaff;
import com.excel.model.FsDispatchLandingPage;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.model.Scheme_Summary;
import com.excel.model.SmpItem;
import com.excel.model.V_GRN_Header;
import com.excel.model.trd_scheme_mst_hdr;
import com.excel.repository.DivMasterRepository;
import com.excel.service.Article_Scheme_master_Service;
import com.excel.service.DashBoardService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class DashBoardController implements MedicoConstants {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private DashBoardService dashBoardService;

	@Autowired
	private DivMasterRepository divMasterRepository;

	@Autowired
	private LocationService locationService;

	@Autowired
	private ParameterService parameterService;
	@Autowired
	private Article_Scheme_master_Service article_Scheme_master_Service;

	@Autowired
	private JwtProvider tokenProvider;
	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	private PeriodMasterService perMastService;

	@GetMapping("/get-details-for-approval")
	public Map<String, Object> getGrnApprovalHeaderDetailForApproval(HttpServletRequest request, HttpSession session)
			throws Exception {
		System.out.println("In Approval");
		Map<String, Object> map = new HashMap<>();
		List<V_GRN_Header> list = new ArrayList<>();
		List<String> ids = new ArrayList<String>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			String wmsInd = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getWms_is_live();
			int limit = 500;
			int counter = 0;
			TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(empId);
			System.out.println("taskQuery.list()::::" + taskQuery.list().size());
//			for(int i = taskQuery.list().size()-1;i>=taskQuery.list().size()-50;  i--) {
//				if((wmsInd.equals("N")&& !taskQuery.list().get(i).getName().contains("DSP")) || (wmsInd.equals("Y") && !taskQuery.list().get(i).getName().contains("DSP"))) {
//					if (limit == counter) {s
//						break;
//					}
//					
//					
//					ids.add(taskQuery.list().get(i).getId());
//				}
//					
//				counter++;
//			}
			for (Task task : taskQuery.list()) {
//				System.out.println("counter:::"+counter+"  task::::" + task.getName()+"  id::"+task.getId());
				//if(task.getId().trim().equalsIgnoreCase("4288838") )
				if(wmsInd.equals("N") || (wmsInd.equals("Y") && !task.getName().contains("DSP"))) {
					if (limit == counter) {
						break;
					}
					ids.add(task.getId());
				}
					
				counter++;
			}
//			System.out.println("Emp Id " + empId + " ids " + ids.size());
			List<ActivityNotification> notifications = dashBoardService.getNotification(empId);
			map.put("notification", notifications);

			if (ids.size() > 0) {
				// System.out.println("ApprovalController.getAdvanceAdjustments() ids1
				// "+Arrays.asList(ids).toString());
				List<ActivityApproval> tasks = dashBoardService.getTaskList(ids, IdentityLinkType.ASSIGNEE, "0");

				if (wmsInd.equals("Y")) {
					tasks = tasks.stream().filter(e -> !e.getFull_tran_name().equals("DSP"))
							.collect(Collectors.toList());
					
					
				}

				map.put("mytasklist", tasks);
				System.out.println("Approval List ::: " + tasks.size());
			}
			map.put("purchase_rate_entry_indicator",
					this.parameterService.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN"));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-dashboard-graph-data")
	public Map<String, Object> getDashBoardGraphData(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();
		List<DashboardCharts> listPI = null;
		List<BigDecimal> piData = null;
		List<String> piLabel = null;
		List<DashboardCharts> listSample = null;
		List<BigDecimal> sampleData = null;
		List<String> sampleLabel = null;
		List<Object[]> grnDataForPI = null;
		List<Object[]> grnDataForSample = null;
		List<DashboardCharts2> list2 = null;
		List<String> prodName = null;
		List<BigDecimal> annPlan = null;
		List<BigDecimal> monthPlan = null;
		List<BigDecimal> swhInv = null;
		List<BigDecimal> monthAvl = null;
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			// get current fin year and per cd from period obj
			Period per_obj = this.perMastService.getCurrentPeriod();
			String finyr = per_obj.getPeriod_fin_year();
			String periodCd = per_obj.getPeriod_code();
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
//			listPI = this.dashBoardService.getPIDashboardGraphData(location.getLoc_id(), empId);
			listSample = this.dashBoardService.getSampleDashboardGraphData(location.getLoc_id(), empId);
//			if (listPI != null) {
//				piData = new ArrayList<BigDecimal>();
//				piLabel = new ArrayList<String>();
//				piData = listPI.stream().filter(obj -> !obj.getLabel().equalsIgnoreCase("ALL"))
//						.map(obj -> obj.getStock_value()).collect(Collectors.toList());
//				piLabel = listPI.stream().filter(obj -> !obj.getLabel().equalsIgnoreCase("ALL"))
//						.map(obj -> obj.getLabel()).collect(Collectors.toList());
//			}
			if (listSample != null) {
				sampleData = new ArrayList<BigDecimal>();
				sampleLabel = new ArrayList<String>();
				sampleData = listSample.stream().filter(obj -> !obj.getLabel().equalsIgnoreCase("ALL"))
						.map(obj -> obj.getStock_value()).collect(Collectors.toList());
				sampleLabel = listSample.stream().filter(obj -> !obj.getLabel().equalsIgnoreCase("ALL"))
						.map(obj -> obj.getLabel()).collect(Collectors.toList());
			}
			grnDataForPI = this.dashBoardService.getDashboardPIDataForGRN(location.getLoc_id(), 7, empId);
			grnDataForSample = this.dashBoardService.getDashboardSampleDataForGRN(location.getLoc_id(), 7, empId);

			list2 = this.dashBoardService.getDashboardPlanData(location.getLoc_id(), empId, finyr, periodCd, "0");

			if (list2 != null) {
				prodName = new ArrayList<String>();
				annPlan = new ArrayList<BigDecimal>();
				monthPlan = new ArrayList<BigDecimal>();
				swhInv = new ArrayList<BigDecimal>();
				monthAvl = new ArrayList<BigDecimal>();

				prodName = list2.stream().map(value -> value.getSa_group_name()).collect(Collectors.toList());
				annPlan = list2.stream().map(value -> value.getPlanvalue()).collect(Collectors.toList());
				monthPlan = list2.stream().map(value -> value.getAllocvalue()).collect(Collectors.toList());
				swhInv = list2.stream().map(value -> value.getStockvalue()).collect(Collectors.toList());
				monthAvl = list2.stream().map(value -> value.getPercentage()).collect(Collectors.toList());
			}

			List<DashboardBean> apprPeriods = dashBoardService.getApprovalTrackerPeriods(finyr);
			List<ApprovalTrackingData> apprData = dashBoardService.getApprovalTrackingData(finyr,
					apprPeriods.get(0).getSdate(), apprPeriods.get(0).getEdate(), String.valueOf(location.getLoc_id()),
					empId);
//			List<Scheme_Summary> article_Scheme_Summary = this.article_Scheme_master_Service
//					.get_article_Scheme_Summary_Home(periodCd);

			map.put("sdate", apprPeriods.get(0).getEdate());
			map.put("edate", apprPeriods.get(0).getSdate());
			map.put("minApprDate", apprPeriods.get(apprPeriods.size() - 1).getSdate());
			map.put("apprData", apprData);
			map.put("grnDataForSample", grnDataForSample);
			map.put("grnDataForPI", grnDataForPI);
			map.put("sampleLabel", sampleLabel);
			map.put("sampleData", sampleData);
//			map.put("piLabel", piLabel);
//			map.put("piData", piData);
			map.put("listPI", listPI);
			map.put("list2", list2);
			map.put("prodName", prodName);
			map.put("annPlan", annPlan);
			map.put("monthPlan", monthPlan);
			map.put("swhInv", swhInv);
			map.put("monthAvl", monthAvl);
			// map.put("article_Scheme_Summary", article_Scheme_Summary);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-dashboard-article-scheme-data")
	@Cacheable(value = "ArtSchmSummHomepage")
	public Map<String, Object> getDashBoardArticleSchemeData(HttpServletRequest request,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			Period per_obj = this.perMastService.getCurrentPeriod();
			String periodCd = per_obj.getPeriod_code();
			Company company=(Company) session.getServletContext().getAttribute(COMPANY_DATA);
			
			if(company.getCfa_to_stk_ind().equals("Y")) {
				 List<trd_scheme_mst_hdr> list=this.article_Scheme_master_Service.get_all_scheme();
				 List<String> slno = list.stream()
	                     .map(f -> f.getTrd_sch_slno().toString())  // Assuming getTrd_sch_slno() returns a Long
	                     .collect(Collectors.toList());
				 
					String formattedString = "(" + slno.stream()
					// .map(item -> "'" + item + "'")
					.collect(Collectors.joining(", ")) + ")";
			System.out.println("formattedString::" + formattedString);
				System.err.println(slno);
				
				List<Scheme_Summary> article_Scheme_Summary = this.article_Scheme_master_Service
						.get_article_Scheme_Summary_Home(formattedString);
				map.put("article_Scheme_Summary", article_Scheme_Summary);
			}
			
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@GetMapping("/get-dashboard-pi-sample-detail")
	public Map<String, Object> getDashBoardPidetailData(	HttpServletRequest request, @RequestParam String finyr,
			@RequestParam String periodCd, @RequestParam String type, String po_no, String product_code,
			String brand_name) {
		Map<String, Object> map = new HashMap<>();
		List<Object[]> details = null;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			if (type.equals("PI")) {
				details = this.dashBoardService.getDashboardPIDetailDataForGRN(location.getLoc_id(), 7, empId, po_no,
						product_code, brand_name);
			} else {
				details = this.dashBoardService.getDashboardSampleDetailDataForGRN(location.getLoc_id(), 7, empId,
						po_no, product_code, brand_name);
			}

			map.put("list", details);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-details-with-sku")
	public Map<String, Object> getDetailsWithSku(HttpServletRequest request, @RequestParam String finyr,
			@RequestParam String periodCd, @RequestParam Long brandId) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("Brand Id " + brandId);
		List<DashboardChartsWithSku> list2 = null;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			Location location = this.locationService.getLocationNameByEmployeeId(empId);

			list2 = this.dashBoardService.getDashboardPlanDataWithSku(location.getLoc_id(), empId, finyr, periodCd, "0",
					brandId);
			map.put("skuDetaills", list2);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-fs-dashboard-data")
	public Map<String, Object> getFsdashboardData(	HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			List<DashboardBean> userData = dashBoardService.getFsDetailsByEmpId(empId);
			DashboardBean user = userData.get(0);
			if (user.getLevel_code().equalsIgnoreCase("003")) {
				String level = "00" + (Integer.parseInt(user.getLevel_code()) - 1);
				System.out.println("level::" + level);
				List<FieldStaff> fsList = dashBoardService.getFsdetailsByMgrId(String.valueOf(user.getFstaff_id()),
						level);
				List<Long> allDmIds = fsList.stream().map(value -> value.getFstaff_id()).collect(Collectors.toList());
				// List<Period> periodList=dashBoardService.getPeriodList("2020");
				// List<SmpItem> productlist = dashBoardService.getproductbyempid(empId);
				map.put("user", user);
				map.put("fsList", fsList);
				// map.put("productlist", productlist);
				// map.put("periodList", periodList);
				map.put("allDmIds", allDmIds);
			} else if (user.getLevel_code().equalsIgnoreCase("002")) {
				String level = "00" + (Integer.parseInt(user.getLevel_code()) - 1);
				System.out.println("level::" + level);
				List<FieldStaff> fsList = dashBoardService.getFsdetailsByMgrId(String.valueOf(user.getFstaff_id()),
						level);
				List<Long> allPsoIds = fsList.stream().map(value -> value.getFstaff_id()).collect(Collectors.toList());
				// List<Period> periodList=dashBoardService.getPeriodList("2020");
				List<DashboardBean> mgrdata = dashBoardService.getFsMgrDetails(user.getFstaff_id());
				// List<SmpItem> productlist = dashBoardService.getproductbyempid(empId);
				map.put("user", user);
				map.put("fsList", fsList);
				// map.put("periodList", periodList);
				// map.put("productlist", productlist);
				map.put("allPsoIds", allPsoIds);
				map.put("mgrId", mgrdata.get(0).getMgr1Id());
				map.put("mgrName", mgrdata.get(0).getMgr1name());
			} else if (user.getLevel_code().equalsIgnoreCase("001")) {
				// List<Period> periodList=dashBoardService.getPeriodList("2020");
				List<DashboardBean> mgrdata = dashBoardService.getFsMgrDetails(user.getFstaff_id());
				// List<SmpItem> productlist = dashBoardService.getproductbyempid(empId);
				map.put("user", user);
				// map.put("periodList", periodList);
				// map.put("productlist", productlist);
				map.put("mgr1Id", mgrdata.get(0).getMgr1Id());
				map.put("mgr1Name", mgrdata.get(0).getMgr1name());
				map.put("mgr2Id", mgrdata.get(0).getMgr2Id());
				map.put("mgr2Name", mgrdata.get(0).getMgr2name());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-pso-from-dms")
	public Map<String, Object> getPsoFromDms(@RequestParam List<String> dms, @RequestParam String level,
			@RequestParam List<String> allDm) {
		Map<String, Object> map = new HashMap<>();
		try {
			String dmIds = "";
			if (dms.contains("0")) {
				dmIds = String.join(",", allDm);
			} else {
				dmIds = String.join(",", dms);
			}
			List<FieldStaff> fsList = dashBoardService.getFsdetailsByMgrId(dmIds, level);
			map.put("fsList", fsList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-fs-dispatch-data")
	public Map<String, Object> getFsDispatchDetails(@RequestParam String divId, @RequestParam String frmPrd,
			@RequestParam String toPrd, @RequestParam String finyr, @RequestParam String reptype,
			@RequestParam String fsId, @RequestParam List<String> prodId, @RequestParam List<String> dm,
			@RequestParam List<String> pso, @RequestParam String self, @RequestParam String excel) {
		Map<String, Object> map = new HashMap<>();
		List<FsDispatchLandingPage> fsData = new ArrayList<>();
		String filename = null;
		try {
			// fsData=this.dashBoardService.getFsLandingPageData("(66)", "09", "10", "2020",
			// "003", "9129", "0", "(5142)", "(8089)", "N");
			divId = "(" + divId + ")";
			String prodIds = "0";
			if (!prodId.contains("0")) {
				prodIds = "(" + String.join(",", prodId) + ")";
			}

			String dmIds = "0";
			if (!dm.contains("0")) {
				dmIds = "(" + String.join(",", dm) + ")";
			}

			String psoIds = "0";
			if (!pso.contains("0")) {
				psoIds = "(" + String.join(",", pso) + ")";
			}

			fsData = this.dashBoardService.getFsLandingPageData(divId, frmPrd, toPrd, finyr, reptype, fsId, prodIds,
					dmIds, psoIds, self);
			map.put("fsDispatchData", fsData);

			if (excel.equalsIgnoreCase("Y")) {
				if (fsData != null && fsData.size() != 0) {
					filename = dashBoardService.Generate_Fsdashboard_Report(fsData);
					map.put("isData", true);
					map.put("filename", filename);
				} else {
					map.put("isData", false);
					map.put("filename", null);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-appr-tracker-data")
	public Map<String, Object> getApprTrackerData(@RequestParam String finyr, @RequestParam String sdate,
			@RequestParam String edate, @RequestParam String loc, @RequestParam String excel,	HttpServletRequest request
			
		) {
		System.out.println("finyr:" + finyr);
		System.out.println("sdate:" + sdate);
		System.out.println("edate:" + edate);
		System.out.println("loc:" + loc);
		Map<String, Object> map = new HashMap<>();
		String filename = null;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			List<ApprovalTrackingData> apprData = dashBoardService.getApprovalTrackingData(finyr, sdate, edate, loc,
					empId);
			map.put("apprData", apprData);
			if (excel.equalsIgnoreCase("Y")) {
				if (apprData != null && apprData.size() != 0) {
					filename = dashBoardService.genearetApprTrackinExcel(apprData);
					map.put("isData", true);
					map.put("filename", filename);
				} else {
					map.put("isData", false);
					map.put("filename", null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-period-month")
	public Map<String, Object> getperiodbyfinyr(@RequestParam String finyr) {
		Map<String, Object> map = new HashMap<>();

		try {
			List<Period> periodList = dashBoardService.getPeriodList(finyr);
			map.put("periodList", periodList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;

	}

	@GetMapping("/getProduct-fordashboard")
	public Map<String, Object> getproductfordashboard(@RequestParam("log_user_fstaff_id") String log_user_fstaff_id,
			@RequestParam("fin_yr") String fin_yr, @RequestParam("frm_month") String frm_month,
			@RequestParam("to_month") String to_month, @RequestParam("levelcode") String levelcode) {

		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("log_user_fstaff_id :: " + log_user_fstaff_id);
			System.out.println("fin_yr :: " + fin_yr);
			System.out.println("frm_month :: " + frm_month);
			System.out.println("to_month :: " + to_month);
			System.out.println("levelcode :: " + levelcode);

			List<SmpItem> productlist = dashBoardService.getproductbyempid(log_user_fstaff_id, fin_yr, frm_month,
					to_month, levelcode);
			map.put("productlist", productlist);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return map;

	}

	@GetMapping("/getTransporter-Details")
	public Map<String, Object> getTransporterDetails() {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("transporters", dashBoardService.getTransporterDetails());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-dashboard-graph-data-Pi-Data")
	public Map<String, Object> getDashPIGraphData(HttpServletRequest request) {
		List<DashboardCharts> listPI = null;
		List<BigDecimal> piData = null;
		List<String> piLabel = null;
		Map<String, Object> map = new HashMap<>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			listPI = this.dashBoardService.getPIDashboardGraphData(location.getLoc_id(), empId);

			if (listPI != null) {
				piData = new ArrayList<BigDecimal>();
				piLabel = new ArrayList<String>();
				piData = listPI.stream().filter(obj -> !obj.getLabel().equalsIgnoreCase("ALL"))
						.map(obj -> obj.getStock_value()).collect(Collectors.toList());
				piLabel = listPI.stream().filter(obj -> !obj.getLabel().equalsIgnoreCase("ALL"))
						.map(obj -> obj.getLabel()).collect(Collectors.toList());
			}

			map.put("piLabel", piLabel);
			map.put("piData", piData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

}
