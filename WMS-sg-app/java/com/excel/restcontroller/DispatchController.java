package com.excel.restcontroller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.ProcessEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.DispatchBean;
import com.excel.bean.DispatchBean.DispatchDetails;
import com.excel.bean.Parameter;
import com.excel.bean.PushAlcToCurrMonth;
import com.excel.bean.ReportBean;
import com.excel.configuration.JwtProvider;
import com.excel.exception.MedicoException;
import com.excel.model.AllocDispatchTracker;
import com.excel.model.AllocationDetailList;
import com.excel.model.Am_m_System_Parameter;
import com.excel.model.ArticleSchemeRequestHeader;
import com.excel.model.AutoDispatchDropdown;
import com.excel.model.Company;
import com.excel.model.DispatchHeaderData;
import com.excel.model.Dispatch_Header;
import com.excel.model.DivMaster;
import com.excel.model.FieldStaffListNew;
import com.excel.model.GenerateDispatchDataCfaStock;
import com.excel.model.GenerateDispatchData_AllocType;
import com.excel.model.Location;
import com.excel.model.LoginDetails;
import com.excel.model.ManualDispatchItemList;
import com.excel.model.ManualDispatchList;
import com.excel.model.ManulDispatchProdListData;
import com.excel.model.MonthList;
import com.excel.model.P_iu_dispatch_java;
import com.excel.model.P_v_dispatch2_java;
import com.excel.model.Period;
import com.excel.model.Sum_Disp_Header;
import com.excel.model.Team;
import com.excel.model.V_Emp_Div_Access;
import com.excel.model.V_Fieldstaff;
import com.excel.repository.AllocDispatchTrackerRepo;
import com.excel.repository.DispatchHeaderRepository;
import com.excel.repository.DispatchRepository;
import com.excel.repository.DivMasterRepository;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.LoginMasterRepository;
import com.excel.service.Allocation_Download_excel_service;
import com.excel.service.ArticleSchemeDeliveryReqService;
import com.excel.service.DispatchService;
import com.excel.service.DivisionMasterService;
import com.excel.service.DoctorBulkAllocUploadService;
import com.excel.service.FieldStaffService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.ProductMasterService;
import com.excel.service.ReportService;
import com.excel.service.SystemParameterService;
import com.excel.service.TeamService;
import com.excel.service.UserMasterService;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@RestController
@RequestMapping("/rest")
public class DispatchController implements MedicoConstants {
	@Autowired
	private JwtProvider tokenProvider;

	@Autowired
	private DispatchService dispatchService;

	@Autowired
	private DivMasterRepository divMasterRepository;

	@Autowired
	private FieldStaffService fieldStaffService;

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	private SystemParameterService systemParameterService;
	@Autowired
	private ReportService reportService;

	@Autowired
	private LoginMasterRepository loginMasterRepository;
	@Autowired
	private LocationService locationService;
	@Autowired
	private DispatchRepository dispatchRepository;
	@Autowired
	private FieldStaffRepository fieldStaffRepository;
	@Autowired
	DivisionMasterService divisionMasterService;
	@Autowired
	ProcessEngine processEngine;
	@Autowired
	ProductMasterService productMasterService;
	@Autowired
	DispatchHeaderRepository dispatchHeaderRepo;
	@Autowired
	EntityManagerFactory emf;
	@Autowired
	PeriodMasterService periodMasterService;
	@Autowired
	ParameterService parameterService;
	@Autowired
	TeamService teamService;
	@Autowired
	DoctorBulkAllocUploadService doctorbulkallocuploadservice;

	@Autowired
	private AllocDispatchTrackerRepo allocDispTracker;
	@Autowired
	private Allocation_Download_excel_service alloc_download_service;
	@Autowired
	private ArticleSchemeDeliveryReqService artSchmDelServi;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfForPrint = new SimpleDateFormat("dd-MM-yyyy");

	private static final Logger logger = LogManager.getLogger(DispatchController.class);

	@GetMapping("/get-data-for-auto-dispatch-entry")
	public Map<String, Object> getDispatchData( HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = null;
		Boolean stock_at_cfa_ind = false;
		HashMap<String, String> dispatchToList = new HashMap<>();
		HashMap<String, String> dispatchTypeList = new HashMap<>();
		String empId="";
		try {
			
			 empId = this.userMasterService.getEmployeeIdFromRequest(request);
			logger.info("Auto Dispatch OnLoad: Started By employee: {}", empId);

			boolean is_cfa_to_stockist = false;
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			Company companyMaster = (Company) session.getServletContext().getAttribute(COMPANY_DATA);

			if (companyMaster.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				is_cfa_to_stockist = true;
			}

			logger.info("Auto Dispatch OnLoad {}: Is CFA to Stockist App : {}", empId, is_cfa_to_stockist);

			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			String s = companyMaster.getStock_at_cfa();
			if (s.equalsIgnoreCase("Y")) {
				stock_at_cfa_ind = true;
			} else {
				stock_at_cfa_ind = false;
			}

			AllocationDetailList obj = dispatchService.getAllocationDetailList(empId);
			List<V_Emp_Div_Access> divList = divMasterRepository.getActiveDivListByEmpId(empId, companyCode);

			dispatchToList.put("E", "Every One");
			dispatchToList.put("L", "Specified Location");
			dispatchToList.put("R", "Specified RBM");
			dispatchToList.put("S", "Specified State");
			dispatchToList.put("Z", "Specified Zone");
			dispatchToList.put("A", "Specified ABM");
			dispatchToList.put("M", "Specified MSR");
			dispatchToList.put("SR", "Sample To RBM");
			dispatchToList.put("SA", "Sample To ABM");

			if (is_cfa_to_stockist) {
				dispatchTypeList.put("dtp", "Direct to Stockist");
			} else {
				dispatchTypeList.put("dtp", "Direct to PSO");
			}

			if (!companyCode.equalsIgnoreCase(NIL) || !companyCode.equalsIgnoreCase(NHP)) {
				dispatchTypeList.put("tcfa", "Through C&F");
			}
			if (stock_at_cfa_ind) {
				dispatchTypeList.put("stkcfa", "Stock At CFA");
			}

			Long loc_id = this.locationService.getLocIdByEmpId(empId);

			boolean locationflag = false;
			if (is_cfa_to_stockist) {
				locationflag = locationService.checkAutoDispatchIsSpecificLocAvail(loc_id);
			} else {
				locationflag = locationService.checkAutoDispatchIsLocAvail();
			}

			logger.info("Auto Dispatch OnLoad {}: Location auto_despinprocess flag : : {}", empId, locationflag);

			if (obj != null) {
				map = new HashMap<>();
				map.put("allocationDetails", obj);
				map.put("divList", divList);
				map.put("monthList",
						divMasterRepository.getMonthListAutoDispatch(companyCode, period.getPeriod_fin_year()));
				map.put("dispatchToList", dispatchToList);
				map.put("dispatchTypeList", dispatchTypeList);
				map.put("ProdSubTypeList", this.parameterService.getProductSubType());
				map.put("locationflag", locationflag);

				if (!locationflag) {
					EntityManager em = null;
					em = emf.createEntityManager();
					em.getTransaction().begin();
//					Location loc=em.find(Location.class, bean.getLocId());
//					loc.setAutodesp_inprocess("Y");
//					em.merge(loc);
					String queryString = null;
					if (is_cfa_to_stockist) {
						logger.info("Auto Dispatch OnLoad {} : Locking for specific location : {}", empId, loc_id);
						queryString = "update Location l set l.autodesp_inprocess='Y' where l.loc_id=:loc_id";
					} else {
						logger.info("Auto Dispatch OnLoad {} : Locking for all locations", empId);
						queryString = "update Location l set l.autodesp_inprocess='Y'";
					}
					Query query = em.createQuery(queryString);
					if (is_cfa_to_stockist) {
						query.setParameter("loc_id", loc_id);
					}
					query.executeUpdate();
					em.getTransaction().commit();
					if (em != null) {
						em.close();
					}
				}

			} else {
				throw new MedicoException("Allocation Detail not found.");
			}

		} catch (MedicoException e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			logger.info("Auto Dispatch OnLoad  {} : Medico Exception  -{}", empId, e.getMessage());
			map = new HashMap<>();
			map.put(RESPONSE_MESSAGE, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Auto Dispatch OnLoad {} : General Exception -{}", empId, e.getMessage());
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@GetMapping("/get-list-for-auto-dispatch")
	public Map<String, Object> getListForAutoDispatch(@RequestParam("alloc_smp_id") String alloc_smp_id,
			@RequestParam("dispatchToLabel") String dispatchToLabel, @RequestParam("divId") String divId,
			@RequestParam("allocType") String allocType, @RequestParam("subTeamCodeList") String subTeamCodeList,
			@RequestParam("AllocReqId") String allocReqId,
			@RequestParam(name = "multipleAllocReqNosList", required = false) List<String> multipleAllocReqNosList,
			HttpSession session, HttpServletRequest request) {
		System.out.println("alloc_smp_id::" + alloc_smp_id);
		System.out.println("dispatchToLabel::" + dispatchToLabel);
		System.out.println("divId::" + divId);
		System.out.println("subTeamCodeList::" + subTeamCodeList.toString());
		Map<String, Object> map = null;
		try {
			Company cmp = (Company) request.getServletContext().getAttribute(COMPANY_DATA);
			boolean isCfaToStocksit = false;
			if (cmp.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				isCfaToStocksit = true;
				// redefine alloc req id in this case
				System.out.println("reqs list::" + multipleAllocReqNosList);
				allocReqId = String.join(",", multipleAllocReqNosList);
				System.out.println("allocReqIds multiple::" + allocReqId);
			}
			if (allocType.equalsIgnoreCase("0")) {
				allocType = "B";
			}
			System.out.println("allocType::" + allocType);
			List<AutoDispatchDropdown> list = dispatchService.getAutoDispatchDropdown(alloc_smp_id, dispatchToLabel,
					divId, allocType, subTeamCodeList, allocReqId, isCfaToStocksit);
			if (list != null) {
				map = new HashMap<>();
				map.put("autoDispatchDropDown", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = new HashMap<>();
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@GetMapping("/get-team-list")
	public Map<String, Object> getTeamList(@RequestParam("divId") Long divId, HttpSession session) {
		System.out.println("divId::" + divId);
		Map<String, Object> map = null;
		List<Team> teamList = null;
		try {
			teamList = teamService.getTeamList(divId);
			if (teamList != null) {
				map = new HashMap<>();
				map.put("sub_team_list", teamList);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@PostMapping("/generate-auto-dispatch")
	public Map<String, Object> generateAutoDispatch(@RequestBody DispatchBean bean, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = null;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode.trim());
			bean.setAction("G");
			bean.setEmpId(empId);
			boolean stock_at_cfa_ind = false;
			String prodTypes = String.join(",", bean.getProductType());
			bean.setProductTypes(prodTypes);

			if (bean.getAllocType().equals("S")) {
				System.out.println("reqs list::" + bean.getMultipleAllocReqNosList());
				String art_reqs = String.join(",", bean.getMultipleAllocReqNosList());
				bean.setMultipleAllocString(art_reqs);
				System.out.println("reqs string::" + bean.getMultipleAllocString());
			}

			if (bean.getDispatchType().equalsIgnoreCase("stkcfa")) {
				stock_at_cfa_ind = true;
			}

			if (stock_at_cfa_ind) {
				List<GenerateDispatchDataCfaStock> dispatchCfaList = dispatchService.generateDispatchDataCfaStock(bean);
				map = new HashMap<>();
				map.put("dispatchList", dispatchCfaList);
			} else {
				bean.setProdIds("");
				if (bean.getAllocType().equals("0")) {
					if (companyCode.trim().equals(PG)) {
						bean.setAllocType(bean.getAlloc_for());
					}
					List<GenerateDispatchData_AllocType> dispatchList = dispatchService
							.generateDispatchDataAllocType(bean);
					map = new HashMap<>();
					map.put("dispatchList", dispatchList);
					BigDecimal totalReqStock = dispatchList.stream()
					        .map(GenerateDispatchData_AllocType::getReq_stock)
					        .filter(Objects::nonNull) // avoid NullPointerException
					        .reduce(BigDecimal.ZERO, BigDecimal::add);
					
					map.put("totalReqStock", totalReqStock);
					
				} else {
					if (companyCode.trim().equals(PG)) {
						bean.setAllocType(bean.getAlloc_for());
					}
					List<GenerateDispatchData_AllocType> dispatchList = dispatchService
							.generateDispatchDataAllocType(bean);
					map = new HashMap<>();
					map.put("dispatchList", dispatchList);
					BigDecimal totalReqStock = dispatchList.stream()
					        .map(GenerateDispatchData_AllocType::getReq_stock)
					        .filter(Objects::nonNull) // avoid NullPointerException
					        .reduce(BigDecimal.ZERO, BigDecimal::add);
					
					map.put("totalReqStock", totalReqStock);
					
				}
			}
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		
		
		return map;
	}

	@PostMapping("/submit-auto-dispatch")
	public Map<String, Object> submitAutoDispatch(@RequestBody DispatchBean bean, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = null;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			Company company = (Company) request.getServletContext().getAttribute(COMPANY_DATA);

			bean.setEmpId(empId);
//			boolean isCfaToStocksit = false;
//			if(company.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
//				isCfaToStocksit = true;
//			}

			// String emp_id=(String)session.getAttribute("EMP_ID");
			
			
			bean.setCompanyCode(companyCode.trim());
			bean.setAction("I");
			boolean stock_at_cfa_ind = false;
			// bean.setProductTypes("");
			String prodTypes = String.join(",", bean.getProductType());
			bean.setProductTypes(prodTypes);
//			Location location=locationService.getObjectById(bean.getLocId());
//			if(location.getAutodesp_inprocess().equalsIgnoreCase("N")) {

//			EntityManager em = null;
//			em = emf.createEntityManager();
//			em.getTransaction().begin();
//			String queryString = "update Location l set l.autodesp_inprocess='Y'";
//			Query query = em.createQuery(queryString);
//			query.executeUpdate();

//			Location loc=em.find(Location.class, bean.getLocId());
//			loc.setAutodesp_inprocess("Y");
//			em.merge(loc);
//			em.getTransaction().commit();
//			if (em != null) { em.close(); }
			Long first_id_from_multiple = 0L;
			if (bean.getAllocType().equals("S")) {
				first_id_from_multiple = Long.valueOf(bean.getMultipleAllocReqNosList().get(0));
				System.out.println("reqs list::" + bean.getMultipleAllocReqNosList());
				String art_reqs = String.join(",", bean.getMultipleAllocReqNosList());
				bean.setMultipleAllocString(art_reqs);
				System.out.println("reqs string::" + bean.getMultipleAllocString());
			}

			if (bean.getDispatchType().equalsIgnoreCase("stkcfa")) {
				stock_at_cfa_ind = true;
			}
			if (stock_at_cfa_ind) {
				List<GenerateDispatchDataCfaStock> dispatchCfaList = dispatchService.generateDispatchDataCfaStock(bean);
				map = new HashMap<>();
				map.put("dispatchList", dispatchCfaList);
			} else {
				bean.setProdIds(String.join(",", bean.getProductIds()));
				if (bean.getAllocType().equals("0")) {
					if (companyCode.trim().equals(PG)) {
						bean.setAllocType(bean.getAlloc_for());
					}
					System.out.println("IN IF");
					List<GenerateDispatchData_AllocType> dispatchList = dispatchService
							.generateDispatchDataAllocType(bean);
					map = new HashMap<>();
					map.put("dispatchList", dispatchList);
				} else {
					if (companyCode.trim().equals(PG)) {
						bean.setAllocType(bean.getAlloc_for());
					}
					System.out.println("IN ELSE");
					List<GenerateDispatchData_AllocType> dispatchList = dispatchService
							.generateDispatchDataAllocType(bean);
					map = new HashMap<>();
					map.put("dispatchList", dispatchList);
				}
			}
			List<List<String>> list = new ArrayList<List<String>>();
			// ------------------------------

			List<Long> dispatchCycles = null;
			if (company.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				dispatchCycles = dispatchService.getDispatchCyclesForBulkHCP(first_id_from_multiple,
						bean.getDispatchCycle(),bean.getAlloc_smp_id());
				System.out.println("dsp_cycles size after auto dispatch:::" + dispatchCycles.size() + ":::for::"
						+ first_id_from_multiple);
			} else {
				dispatchCycles = dispatchService.getDispatchCyclesForBulkHCP(bean.getAllocReqNo(),
						bean.getDispatchCycle(),bean.getAlloc_smp_id());
			}

			List<String> dataList = null;
			for (Long dspCycle : dispatchCycles) {
				dataList = new ArrayList<String>();
				dataList = dispatchService.getAutoDispatchDetailsUpdated(bean.getAlloc_smp_id(), dspCycle,
						bean.getLocId(), bean.getDivision(), stock_at_cfa_ind);
				list.add(dataList);
			}

			// ------------------------------

//			List<String> list = dispatchService.getAutoDispatchDetailsUpdated(bean.getAlloc_smp_id(), bean.getDispatchCycle(), bean.getLocId(),
//					bean.getDivision(), stock_at_cfa_ind);

			map = new HashMap<>();
			map.put("updatedDispatchList", list);
			// map.put("seperatorIndex", list.indexOf("#"));
			map.put("seperatorIndex", list.get(0).indexOf("#"));
			map.put("inProgress", "N");
			map.put(DATA_SAVED, "T");
			System.out.println("Auto dispatch completed");
//			} else {
//				map = new HashMap<>();
//				map.put("inProgress", "Y");
//				System.out.println("Auto dispatch already in progress..");
//			}

			// changes on 13 August
			/*
			 * call sendForDispatchApproval(String empId, List<String> challanNumberList,
			 * HttpServletRequest request) in a seperate thread with try catch
			 */

		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(DATA_SAVED, "F");
			map.put(RESPONSE_MESSAGE, "SQL Exception");
		} catch (PersistenceException e) {
			e.printStackTrace();
			// TODO: handle exception
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(DATA_SAVED, "F");
			map.put(RESPONSE_MESSAGE, e.getCause().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(DATA_SAVED, "F");
			map.put(RESPONSE_MESSAGE, e.getMessage());
		} finally {
//			EntityManager em = null;
//			em = emf.createEntityManager();
//			em.getTransaction().begin();
//			Location loc=em.find(Location.class, bean.getLocId());
//			loc.setAutodesp_inprocess("N");
//			em.merge(loc);
//			em.getTransaction().commit();
			EntityManager em = null;
			em = emf.createEntityManager();
			em.getTransaction().begin();
			// String queryString = "update Location l set l.autodesp_inprocess='N'";
			String queryString = "update Location l set l.autodesp_inprocess='N' where l.loc_id=" + bean.getLocId();
			Query query = em.createQuery(queryString);
			query.executeUpdate();
			em.getTransaction().commit();
			if (em != null) {
				em.close();
			}
		}
		return map;
	}

	@GetMapping("/get-data-for-manual-dispatch-entry")
	public Map<String, Object> getManualDispatchData(@RequestParam String empId, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = null;
		HashMap<String, String> dispatchToList = new HashMap<>();
		HashMap<String, String> dispatchTypeList = new HashMap<>();
		try {
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			AllocationDetailList obj = dispatchService.getAllocationDetailList(empId);
			List<V_Emp_Div_Access> divList = divMasterRepository.getActiveDivListByEmpId(empId, companyCode);

			dispatchTypeList.put("dtp", "Direct to PSO");
			dispatchTypeList.put("tcfa", "Through C&F");
			boolean locationflag = locationService.checkAutoDispatchIsLocAvail();

			if (obj != null) {
				map = new HashMap<>();
				map.put("allocationDetails", obj);

				map.put("divList", divList);
				map.put("monthList", divMasterRepository.getMonthList(companyCode, period.getPeriod_fin_year()));
				map.put("dispatchToList", dispatchToList);
				map.put("dispatchTypeList", dispatchTypeList);
				map.put("locationflag", locationflag);

				EntityManager em = null;
				em = emf.createEntityManager();
				em.getTransaction().begin();
				String queryString = "update Location l set l.autodesp_inprocess='Y'";
				Query query = em.createQuery(queryString);
				query.executeUpdate();

//				Location loc=em.find(Location.class, bean.getLocId());
//				loc.setAutodesp_inprocess("Y");
//				em.merge(loc);
				em.getTransaction().commit();
				if (em != null) {
					em.close();
				}
			} else {
				throw new MedicoException("Allocation Detail not found.");
			}
		} catch (MedicoException e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(RESPONSE_MESSAGE, e.getMessage());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@GetMapping("/get-field-staff-list")
	public Map<String, Object> getFieldStaffList(@RequestParam("smpId") Long smpId, @RequestParam("divId") Long div_id,
			@RequestParam("empId") String empId, @RequestParam("allocType") String allocType, HttpSession session) {
		Map<String, Object> map = null;
		try {
			System.out.println(empId + " allocType " + allocType + " allocType " + allocType);
			// List<FieldStaffList> list = fieldStaffService.getFieldStaffList(empId,
			// div_id);
			List<FieldStaffListNew> list = fieldStaffService.getFieldStaffListNew(empId, div_id, allocType);
			// List<Object> prodList =
			// dispatchService.getdispatchProd_list(Long.parseLong(emp_id), smpId);
			if (list != null) {
				map = new HashMap<>();
				map.put("fieldStaffList", list);
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@GetMapping("/get-dispatch-prod-list")
	public Map<String, Object> getDispatchProductList(@RequestParam("dispatchTo") Long dispatchTo,
			@RequestParam("alloc_smp_id") Long alloc_smp_id, @RequestParam("allocType") String allocType,
			HttpSession session) {
		System.out.println("dispatchTo::" + dispatchTo);
		System.out.println("alloc_smp_id::" + alloc_smp_id);
		Map<String, Object> map = new HashMap<>();
		try {
			List<ManualDispatchItemList> prodList = dispatchService.getdispatchProd_list(dispatchTo, alloc_smp_id,
					allocType);
			V_Fieldstaff obj = fieldStaffService.getDivListByEmpId(Y, dispatchTo);
			if (prodList != null) {
				map.put("prodList", prodList);
				map.put("fieldStaffObj", obj);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@PostMapping("/get-dispatch-prod-details-data")
	public Map<String, Object> getDispatchProductDetailsData(@RequestBody DispatchBean bean, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = null;
		try {
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode.trim());

			List<ManulDispatchProdListData> details = dispatchService.getdispatchProdDetails(bean.getCompanyCode(),
					bean.getAllocSmpYear(), bean.getAllocPeriodCode(), bean.getLocId(), bean.getAlloc_smp_id(),
					bean.getItemName(), Long.parseLong(bean.getDispatchTo()), "N", bean.getAllocationType());
			System.err.println("details::" + details.size());
			if (details != null && details.size() > 0) {
				map = new HashMap<>();
				map.put("details", details);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@PostMapping("/submit-manual-dispatch")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveManualDispatch(@RequestBody DispatchBean bean, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = null;
		String statusFlag = "Y";
		String direct_to_pso_ind = null;
		List<P_iu_dispatch_java> list = null;
		System.out.println("prod id " + bean.getItemName());
		try {
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			String ipAddress = request.getRemoteAddr();
			Am_m_System_Parameter parameter = systemParameterService.getSpValueBySpName("Dsp_APPR_REQ").get(0);
			String dsp_appr_req = parameter.getSp_value();
			if (bean.getAction().equalsIgnoreCase("S")) {
				statusFlag = "Y";
//				bean.setDspId(0l);
//				bean.setSumdsp_id(0l);

			} else {
				statusFlag = "N";
				dispatchService.deleteDispatchDetail(companyCode, bean.getAllocSmpYear(), bean.getAllocPeriodCode(),
						bean.getDspId(), bean.getItemName(), bean.getEmpId(), ipAddress);
			}

			BigDecimal dispatchQty = BigDecimal.ZERO;
			BigDecimal smpRate = BigDecimal.ZERO;
			System.out.println("Long.parseLong(bean.getDispatchTo()) ::" + Long.parseLong(bean.getDispatchTo()));
			System.out.println("bean.getLocId() ::" + bean.getLocId());
			List<Object> fd = dispatchService.getFieldstaffDetail(Long.parseLong(bean.getDispatchTo()),
					bean.getLocId());
			System.out.println("flocId::" + fd.get(0)); // bean.getF_loc_id()
			for (DispatchDetails d : bean.getDispatchDetails()) {
				if (bean.getDispatchType().equalsIgnoreCase("dtp")) {
					direct_to_pso_ind = "Y";
				}
				dispatchQty = d.getDispatchQty() != null ? d.getDispatchQty() : BigDecimal.ZERO;
				smpRate = d.getSmpRate() != null ? d.getSmpRate() : BigDecimal.ZERO;

				if (dispatchQty.compareTo(BigDecimal.ZERO) > 0) {
					list = dispatchService.saveManualDispatch(companyCode.trim(), bean.getAllocSmpYear(),
							bean.getAllocPeriodCode(), bean.getLocId(), bean.getAllocId(), bean.getAlloc_smp_id(),
							Long.parseLong(bean.getDispatchTo()), bean.getDivision(), Long.valueOf((Integer) fd.get(0)),
							bean.getF_state_id(), bean.getPendingAlloc(), bean.getEmpId(), ipAddress, "A",
							bean.getDispatchCycle(), bean.getDspId(), bean.getItemName(), d.getSmpBatchId(),
							dispatchQty, smpRate, bean.getAllocDtlId(), bean.getF_div_id(), bean.getSumdsp_id(), "I",
							statusFlag, BigDecimal.ZERO, 0l, 0l,
							bean.getChallanMsg() == null ? "" : bean.getChallanMsg(), Long.parseLong(bean.getMonth()),
							dsp_appr_req, direct_to_pso_ind);

					System.err.println("list co::" + list.size());
					bean.setDspId(list.get(0).getPiDSP_ID_OUT());
					bean.setSumdsp_id(list.get(0).getPiSUMDSP_ID_OUT());
					bean.setDspChallanNo(list.get(0).getPvDSPchallan_no_out());
				}
			}
			System.out.println("Challan no ::" + bean.getDspChallanNo());
			List<P_v_dispatch2_java> prodList = dispatchService.getDispatchedProdData(bean.getDspId(), bean.getLocId());
			map = new HashMap<>();
			map.put("prodList", prodList);
			map.put("bean", bean);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map = new HashMap<>();
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	// on click of list
	
	@GetMapping("/get-manual-dispatch-list")
	public Map<String, Object> getManualDispatchList(
			@RequestParam("empLevel") String userType, HttpServletRequest request, HttpSession session) {
		Map<String, Object> 
		map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			System.out.println("empId " + empId + " userType" + userType);
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			LoginDetails loginDetails = (LoginDetails) session.getAttribute("LOGIN_DETAILS_SG");
			String user_divisions = loginMasterRepository.getFormatedUserDivision(empId);
			user_divisions = user_divisions.replace("(", "");
			user_divisions = user_divisions.replace(")", "");
			System.out.println("loginDetails " + loginDetails);
			List<Parameter> divList = userMasterService.getDivAccessedByUser(empId);
			/*
			 * List<ManualDispatchList> dispatchList =
			 * dispatchService.getManualDispatchList(loginDetails.getLoc_SubComp_id(),
			 * user_divisions, empId, userType, 0);
			 */
			List<ManualDispatchList> dispatchList = dispatchService.getManualDispatchList(1L, user_divisions, empId,
					userType, 0);
			System.out.println("Dispatch List " + dispatchList.size() + " Div List " + divList.size());
			map.put("divList", divList);
			map.put("dispatchList", dispatchList);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	// On click of div
	@GetMapping("/get-summary-challan-list-by-div")
	public Map<String, Object> getSummaryChallanListByDiv(@RequestParam("divId") Long divId,
			@RequestParam("empId") String empId, HttpServletRequest request, HttpSession session) {
		System.out.println("DIV " + divId + " empId " + empId + " dispatchService" + dispatchService);
		Map<String, Object> map = new HashMap<>();
		try {
			/* String emp_id =(String)session.getAttribute("EMP_ID"); */
			/*
			 * LoginDetails loginDetails = (LoginDetails)
			 * session.getAttribute("LOGIN_DETAILS_SG");
			 */
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			List<Sum_Disp_Header> summChallanList = dispatchService.getSummaryChallanListByDiv(empId, divId,
					location.getLoc_subcomp_id(), 1);
			if (summChallanList != null && summChallanList.size() > 0) {
				map.put("summChallanList", summChallanList);
			}
			System.out.println("summChallanList " + summChallanList.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@GetMapping("/get-details-manual-dispatch")
	public Map<String, Object> loadManualDispatchModify(@RequestParam("empId") String emp_id,
			@RequestParam("compCode") String comp_code, @RequestParam("dspId") Long dspId,
			@RequestParam("locId") Long locId, @RequestParam("allocType") String allocType, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			/// save_mode="U";
			AllocationDetailList allocationDetailList = dispatchRepository.getAllocationDetailList(emp_id);
			String msg = "";
			if (allocationDetailList != null)
				msg = "Allocation Detail not found.";

			StringBuffer sb = null;
			sb = new StringBuffer(" PERIOD_status = 'A' and PERIOD_COMPANY = '")
					.append(comp_code + "' and PERIOD_FIN_YEAR='").append(allocationDetailList.getAlloc_fin_year()
							+ "' and convert(date,'01 ' + PERIOD_NAME ) >=convert(date,dateadd(mm,datediff(mm,0,getutcdate()),0)) ");
			List<MonthList> monthList = dispatchRepository.getMonthList("PERIOD", "PERIOD_ID, PERIOD_NAME",
					sb.toString(), " PERIOD_NAME asc ");
			System.out.println("para:" + dspId + ":" + locId);
			DispatchHeaderData dispatchHeaderData = dispatchRepository.getDispatchedHeaderData(dspId, locId);
			V_Fieldstaff v_fieldStaff = fieldStaffRepository.getDivListByEmpId("Y", dispatchHeaderData.getFstaff_id());
			List<ManualDispatchItemList> prod_list = dispatchRepository.getdispatchProd_list(
					dispatchHeaderData.getFstaff_id(), allocationDetailList.getAlloc_smp_id(), allocType);
			List<P_v_dispatch2_java> mod_prod_list = dispatchRepository.getDispatchedProdData(dspId, locId);

			map.put("monthList", monthList);
			map.put("allocationDetailList", allocationDetailList);
			map.put("DispatchHeaderData", dispatchHeaderData);
			map.put("V_Fieldstaff", v_fieldStaff);
			map.put("prod_list", prod_list);
			map.put("mod_prod_list", mod_prod_list);

		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;

		}
		return map;
	}

	@GetMapping("/getDivisionForDispatchApproval")
	public Object getDivisionForDispatchApproval( HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<DivMaster> divNameList = new ArrayList<>();
		String empId;
		String AUTO_APPR_IND="";
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
			 AUTO_APPR_IND = this.parameterService.dispatchAutoApproval();
			divNameList = this.divisionMasterService.getDivAccessedByUser(empId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("divNameList", divNameList);
		// add auto appr indicator
		map.put("AUTO_APPR_IND", AUTO_APPR_IND);
		return map;
	}

	@GetMapping("/SummaryChallanNoList")
	public Object SummaryChallanNoList( @RequestParam Long div_id, @RequestParam Long empLoc,	HttpServletRequest request)
			throws Exception {
		System.out.println("division id is:" + div_id);
		Map<String, Object> map = new HashMap<>();
		
		String empId = this.userMasterService.getEmployeeIdFromRequest(request);
		List<Sum_Disp_Header> challanList = new ArrayList<>();
		challanList = this.dispatchService.getSummaryChallanListByDiv(empId, div_id, empLoc, 0);
		map.put("ChallanNoList", challanList);
		return map;
	}

	@GetMapping("/getHeaderForDispatchApproval")
	public Object getHeaderForDispatchApproval(@RequestParam List<String> ChallanNo) {
		String challan = String.join(",", ChallanNo);
		return this.dispatchService.getHeaderForDispatchApproval(challan);
	}

	@GetMapping("/getDetailForDispatchApproval")
	public Object getDetailForDispatchApproval(@RequestParam int dspId) {
		return this.dispatchService.getDetailForDispatchApproval(dspId);
	}

	/*
	 * @GetMapping("/updateDispatchHeaderForDispatchSelfApproval") public void
	 * updateDispatchHeaderForDispatchSelfApproval(@RequestParam String
	 * empId, @RequestParam List<String> challanNumberList,HttpServletRequest
	 * request) {
	 * this.dispatchService.updateDispatchHeaderForDispatchSelfApproval(empId,
	 * challanNumberList, request); }
	 */
	@GetMapping("/updateDispatchHeaderForDispatchSelfDiscard")
	public void updateDispatchHeaderForDispatchSelfDiscard(
			@RequestParam List<String> challanNumberList, HttpServletRequest request) {
		
		
		String empId;
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
			this.dispatchService.updateDispatchHeaderForDispatchSelfDiscard(empId, challanNumberList, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@GetMapping("/updateDispatchHeaderForDispatchSelfApproval")
	public void updateDispatchHeaderForDispatchSelfApproval(
			@RequestParam List<String> challanNumberList, HttpServletRequest request) throws Exception {

		
		String empId = this.userMasterService.getEmployeeIdFromRequest(request);
		this.dispatchService.sendForDispatchApproval(empId, challanNumberList, request);

	}

	@GetMapping("/get-dispatch-prod-details-data-edit")
	public Map<String, Object> getDispatchProductDetailsDataEdit(@RequestParam String dsp_id,
			@RequestParam String dspdtl_id, @RequestParam String pend_alloc) {
		System.out.println("dsp_id:" + dsp_id);
		System.out.println("dspdtl_id:" + dspdtl_id);
		System.out.println("pend_alloc:" + pend_alloc);
		Map<String, Object> map = null;
		try {
			
			List<ManulDispatchProdListData> details = dispatchService.getdispatchProdDetailsEdit(Long.valueOf(dsp_id),
					Long.valueOf(dspdtl_id), pend_alloc);
			
			
			System.err.println("details::" + details.size());
			if (details != null && details.size() > 0) {
				map = new HashMap<>();
				map.put("details", details);
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@PostMapping("/dpLrUpload")
	public Map<String, Object> dpLrUpload(@RequestParam MultipartFile file, @RequestParam String empId,
			HttpServletRequest request, HttpSession session) {
		System.out.println("empId::" + empId);
		Map<String, Object> map = new HashMap<>();
		long j = 0l;
		try {
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			Company companyMaster = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			DispatchBean bean = new DispatchBean();
			bean.setDpLrUploadFile(file);
			bean.setStockAtCfa(companyMaster.getStock_at_cfa());
			bean.setCompanyCode(companyCode);
			j = this.dispatchService.dpLrUpload(bean);
			map.put(RESPONSE_MESSAGE, bean.getMsg());
		} catch (MedicoException e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, " Error While Updating Record. ::" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, " Wrong Input. Please Check the Data of Row No.=" + e.getMessage());
		} finally {

		}
		return map;
	}

	@GetMapping("/get-delete-challan-data")
	public Map<String, Object> getDeleteChallanData( HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			List<V_Emp_Div_Access> divList = divMasterRepository.getActiveDivListByEmpId(empId, companyCode);
			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			Date frmDt = MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(period.getPeriod_start_date());
			Date endDt = MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(period.getPeriod_end_date());
			List<Location> locList = locationService.getAllLocations();
			map.put("divList", divList);
			map.put("frmDt", frmDt);
			map.put("endDt", endDt);
			map.put("locList", locList);
		} catch (MedicoException e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@GetMapping("/get-delete-challan-list")
	public Map<String, Object> getChallanListForDelete(@RequestParam String divId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String finYr, @RequestParam String periodCd,
			@RequestParam String locId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Dispatch_Header> list = dispatchService.getDspHeaderForDelete(finYr, periodCd, Long.valueOf(divId),
					fromDate, toDate, Long.valueOf(locId));
			map.put("list", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		}
		return map;
	}

	@PostMapping("/submit-delete-challan")
	public Map<String, Object> submitDeleteChallan(@RequestBody DispatchBean bean, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			
			logger.info("Delete Challans Req Body: \nlocationId:{} \ndspIds: {}", bean.getLocationId(),
					bean.getDspIds());
			boolean is_cfa_to_stockist = false;
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setCompanyCode(companyCode.trim());
			bean.setIpAddress(request.getRemoteAddr());

			Company companyMaster = (Company) session.getServletContext().getAttribute(COMPANY_DATA);

			if (companyMaster.getCfa_to_stk_ind().equalsIgnoreCase("Y")) {
				is_cfa_to_stockist = true;
			}

//			EntityManager em = null;
//			em = emf.createEntityManager();
//			em.getTransaction().begin();
//			Location loc=em.find(Location.class, bean.getLocationId());
			boolean flag = locationService.checkAutoDispatchIsLocAvail();
			if (!flag) {
				if (is_cfa_to_stockist) {
					logger.info(
							"Delete Challan loc aut_disp lock : App is Cfa to stockist so locking for specific location - {}",
							bean.getLocationId());
					locationService.lockUnlockSpecificLocations("Y", bean.getLocationId());
				} else {
					logger.info(
							"Delete Challan loc aut_disp lock : App is Cfa to stockist so locking for all locations");
					locationService.lockUnlockLocations("Y");
				}

//				loc.setAutodesp_inprocess("Y");
//				em.merge(loc);
//				em.getTransaction().commit();
//				if (em != null) { em.close(); }
				dispatchService.submitdeleteChallan(bean);
				map.put("data", "Y");

//				em = emf.createEntityManager();
//				em.getTransaction().begin();
//				loc.setAutodesp_inprocess("N");
//				em.merge(loc);
//				em.getTransaction().commit();
//				if (em != null) { em.close(); }
				logger.info("challans deleted");
				if (is_cfa_to_stockist) {
					logger.info(
							"Delete Challan loc aut_disp lock : App is Cfa to stockist so unlocking for specific location - {}",
							bean.getLocationId());
					locationService.lockUnlockSpecificLocations("N", bean.getLocationId());
				} else {
					logger.info(
							"Delete Challan loc aut_disp lock : App is Cfa to stockist so locking for all locations");
					locationService.lockUnlockLocations("N");
				}
			} else {
				// em.getTransaction().commit();
				// if (em != null) { em.close(); }
				map.put("data", "N");
				System.out.println("Auto dispatch is going on..");
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, e.getMessage());
		} finally {
			EntityManager em = null;
			em = emf.createEntityManager();
			em.getTransaction().begin();
			String queryString = "update Location l set l.autodesp_inprocess='N'";
			Query query = em.createQuery(queryString);
			query.executeUpdate();
			em.getTransaction().commit();
			if (em != null) {
				em.close();
			}
		}
		return map;
	}

	@PostMapping("/dpLrUpload-revised")
	public Map<String, Object> dpLrUploadRevised(@RequestParam MultipartFile file, @RequestParam String empId,
			@RequestParam String username, @RequestParam String role, @RequestParam String finyr,
			HttpServletRequest request, HttpSession session) {
		System.out.println("empId::" + empId);
		System.out.println("username ::" + username);
		System.out.println("role::" + role); //
		Map<String, Object> map = new HashMap<>();
		long j = 0l;
		try {
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			Company companyMaster = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String ipadd = (request.getRemoteAddr());
			DispatchBean bean = new DispatchBean();
			bean.setDpLrUploadFile(file);
			bean.setStockAtCfa(companyMaster.getStock_at_cfa());
			bean.setCompanyCode(companyCode);
			bean.setUsername(username);
			bean.setRole(role);
			bean.setFinyr(finyr);
			bean.setCompanyName(companyMaster.getCompany_name());
			bean.setIpAddress(ipadd);
			bean.setEmpId(empId);

			System.out.println("finyr :: " + bean.getFinyr());

			j = this.dispatchService.dpLrUploadRevised(bean);
			map.put(RESPONSE_MESSAGE, bean.getMsg());
		} catch (MedicoException e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, " Error While Updating Record. ::" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, " Wrong Input. Please Check the Data of Row No.=");// e.getMessage()
		} finally {

		}
		return map;
	}

	@PostMapping("/dpLrUpload-modify-detail-revised")
	public Map<String, Object> dpLrUploadModifydetailRevised(@RequestParam MultipartFile file,
			@RequestParam String empId, @RequestParam String finyr, HttpServletRequest request, HttpSession session) {
		System.out.println("empId::" + empId);

		Map<String, Object> map = new HashMap<>();
		long j = 0l;
		try {
			String companyCode = request.getServletContext().getAttribute(COMPANY_CODE).toString();
			Company companyMaster = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String ip_addr = request.getRemoteAddr(); // to get ip addres
			DispatchBean bean = new DispatchBean();
			bean.setDpLrUploadFile(file);
			bean.setStockAtCfa(companyMaster.getStock_at_cfa());
			bean.setCompanyCode(companyCode);
			bean.setIpAddress(ip_addr);
			bean.setFinyr(finyr);
			bean.setEmpId(empId);

			j = this.dispatchService.dpLrUploadModifyDetailRevised(bean);
			map.put(RESPONSE_MESSAGE, bean.getMsg());
		} catch (MedicoException e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, " Error While Updating Record. ::" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, " Wrong Input. Please Check the Data of Row No.=" + e.getMessage());
		} finally {

		}
		return map;
	}

	@GetMapping("/lockUnlockLocation")
	public Map<String, Object> LockUnlockChallan(@RequestParam("state") String state,
			@RequestParam("loc_id") Long loc_id, @RequestParam("cfa_to_stk_ind") String cfa_to_stk_ind,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.info("Auto Dispatch (un)lock : state - {} and loc - {}", state, loc_id);
			EntityManager em = null;
			em = emf.createEntityManager();
			em.getTransaction().begin();
			String queryString = "update Location l set l.autodesp_inprocess=:state";
			if (cfa_to_stk_ind != null && !cfa_to_stk_ind.isEmpty() && cfa_to_stk_ind.equalsIgnoreCase("Y")) {
				logger.info(
						"Auto Dispatch (un)lock : App is Cfa to stockist so (un)locking - {} for specific locations -{}",
						state, loc_id);
				queryString = queryString.concat(" where l.loc_id=:loc_id");
			} else {
				logger.info("Auto Dispatch (un)lock : App is Normal SG so (un)locking - {} for all locations", state);
			}
			Query query = em.createQuery(queryString);
			query.setParameter("state", state);
			if (cfa_to_stk_ind != null && !cfa_to_stk_ind.isEmpty() && cfa_to_stk_ind.equalsIgnoreCase("Y")) {
				query.setParameter("loc_id", loc_id);
			}
			query.executeUpdate();

//			Location loc=em.find(Location.class, bean.getLocId());
//			loc.setAutodesp_inprocess("Y");
//			em.merge(loc);
			em.getTransaction().commit();
			if (em != null) {
				em.close();
			}
			map.put(RESPONSE_MESSAGE, "Records Updated");
		} catch (Exception e) {
			logger.info("Auto Dispatch (un)lock : Error -{} for locations - {} ", e.getMessage(), loc_id);
			map.put(RESPONSE_MESSAGE, " Error While Updating Record. ::" + e.getMessage());
			throw e;
		}
		return map;
	}

	@PostMapping("/get-locations-dispatch-allocation-tracker")
	public Map<String, Object> getTrackerDataOnLoad(@RequestParam String empId) {
		System.out.println("empId " + empId);
		Map<String, Object> map = null;
		List<Location> locList = null;
		Location loc = null;
		List<DivMaster> list = null;
		try {
			map = new HashMap<String, Object>();
			locList = locationService.getAllLocations();
			loc = locationService.getLocationNameByEmployeeId(empId);
			list = reportService.getdivforstockageing(empId);

			if (locList != null && locList.size() > 0) {
				map.put("locList", locList);
				map.put("loc", loc);
				map.put("divlist", list);
			}
		} catch (Exception e) {
			map = new HashMap<>();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/get-alloc-disp-tracker-print")
	public Map<String, Object> getDispTrackerData(@RequestBody ReportBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = null;
		List<AllocDispatchTracker> returnList = null;
		String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
		String loc_id = bean.getLocId();
		String divId = bean.getDivId();
		System.out.println("bean.getLoc_id()" + bean.getLocId());
		String startDate = sdf.format(bean.getStartDate());
		String endDate = sdf.format(bean.getEndDate());
		String startDateForPrint = sdfForPrint.format(bean.getStartDate());
		String endDateForPrint = sdfForPrint.format(bean.getEndDate());
		String downloadFile;
		String allocType = bean.getAlloc_id();
		try {
			map = new HashMap<String, Object>();
			returnList = allocDispTracker.getAllocDispatchTrackingDataFromProcedure(loc_id, startDate, endDate,
					allocType, divId);
			String uname = this.tokenProvider.getUsernameFromRequest(request);
			String empId = this.userMasterService.getEmpIdByUsername(uname);
			if (returnList != null) {
				if (returnList.size() > 0) {
					downloadFile = this.alloc_download_service.Generate_Dispatch_Allocation_Download_excel(returnList,
							companyname, startDateForPrint, endDateForPrint, empId);
					map.put("downloadFile", downloadFile);
					map.put("returnList", returnList);
				} else {
					map.put("message", "No Data Found");
				}
			} else {
				map.put("message", "Some Error Occured");
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "Some Error Occured");
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	// to push pending allocations to current month
	@GetMapping("/get-onload-data-for-alc-month-push")
	public ResponseEntity<Map<String, Object>> getOnloadDataForAlcMthPush(String finYear) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allPeriods", this.periodMasterService.getOnlyCurrentPeriodsByFinYear(finYear));
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/get-alloc-req-nos-for-alc-month-push")
	public ResponseEntity<List<ArticleSchemeRequestHeader>> getArticleRequestNosForAlcMthPush(
			@RequestParam("locId") Long locId, @RequestParam("divId") Long divId,
			@RequestParam("finYear") String finYear, @RequestParam("empId") String empId,
			@RequestParam("periodId") String period_code) throws Exception {
			return ResponseEntity.ok(this.artSchmDelServi.getArtReqNosForAutoDispatch(finYear,
					period_code, divId, locId, empId));
	}
	
	@PostMapping("/update_alc_month_push")
	public ResponseEntity<Void> push_prev_alc_to_curr_month(@Valid @RequestBody PushAlcToCurrMonth bean) throws Exception{
		this.artSchmDelServi.pushAlcDatFromPrevToCurr(bean);
		return ResponseEntity.ok().build();
	}
	
}
