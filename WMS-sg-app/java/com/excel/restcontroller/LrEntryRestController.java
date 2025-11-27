package com.excel.restcontroller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.LrEntryBean;
import com.excel.bean.LrEntryDataBean;
import com.excel.bean.Rec_Bean;
import com.excel.bean.TRF_BEAN_stocktransfer_download_trdata;
import com.excel.bean.dataForStkLrEntry;
import com.excel.configuration.HttpSessionInterceptor;
import com.excel.configuration.JwtProvider;
import com.excel.model.Company;
import com.excel.model.Location;
import com.excel.model.Lr_csv_upload_java_proc_recised_filter;
import com.excel.model.Period;
import com.excel.model.Transporter_master;
import com.excel.service.LrEntryService;
import com.excel.service.PeriodMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest")
public class LrEntryRestController implements MedicoConstants {

	@Autowired
	LrEntryService lrEntryService;
	@Autowired
	PeriodMasterService periodMasterService;
	@Autowired private JwtProvider tokenProvider;
	private static final Logger logger = LogManager.getLogger(HttpSessionInterceptor.class);

	@Autowired private UserMasterService userMasterService;
	
	
//	@GetMapping("/getDataForLrEntry")
//	public Map<String, Object> getDataForLrEntry(@RequestParam("empId") String empId,
//			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
//			@RequestParam("role") String role, @RequestParam("user") String user, @RequestParam("loc") List<String> loc,
//			@RequestParam("div") List<String> div, @RequestParam("dest") List<String> dest,
//			@RequestParam("trans") String trans, HttpSession session) {
//
//		logger.info("empId::::" + empId);
//
//		Map<String, Object> map = new HashMap<>();
//		System.out.println("startDate " + startDate);
//		System.out.println("endDate " + endDate);
//		System.out.println("user " + user);
//		System.out.println("role " + role);
//
//		Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
//		try {
//			List<LrEntryDataBean> details = lrEntryService.getLrcsvdownload_Revised_data(startDate, endDate, "A", "0",
//					"0", "N", "N", "0", "0", role, user, loc, div, dest, trans, company.getCfa_to_stk_ind());
//			map.put("lrDetails", details);
//		} catch (Exception e) {
//
//			logger.info("Error Occurred  during getDataForLrEntry  :" + e.getMessage());
//			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
//		}
//		return map;
//	}

	
	
	@GetMapping("/getDataForLrEntry")
	public Map<String, Object> getDataForLrEntry(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("role") String role, @RequestParam("user") String user, @RequestParam("loc") List<String> loc,
			@RequestParam("div") List<String> div, @RequestParam("dest") List<String> dest,
			@RequestParam("trans") String trans, 		@RequestParam("depo_loc_id") String depo_loc_id, HttpSession session,HttpServletRequest request) {

		
		String empId="";
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("empId::::" + empId);

		Map<String, Object> map = new HashMap<>();
		System.out.println("startDate " + startDate);
		System.out.println("endDate " + endDate);
		System.out.println("user " + user);
		System.out.println("role " + role);

		Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString().trim();
		try {
			
			if(company.getCfa_to_stk_ind().equals("Y")) {
			List<LrEntryDataBean> details = lrEntryService.getLrcsvdownload_Revised_data(startDate, endDate, "A", depo_loc_id,
					"0", "N", "N", "0", "0", role, user, loc, div, dest, trans, company.getCfa_to_stk_ind(), companyCode);
			map.put("lrDetails", details);
			}else {
				
				List<LrEntryDataBean> details = lrEntryService.getLrcsvdownload_Revised_data_for_sg(startDate, endDate, "A", depo_loc_id,
						"0", "N", "N", "0", "0", role, user, loc, div, dest, trans, company.getCfa_to_stk_ind(), companyCode);
				map.put("lrDetails", details);
			}
			
			
		
			
		} catch (Exception e) {

			logger.info("Error Occurred  during getDataForLrEntry  :" + e.getMessage());
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}
	
	
	@PostMapping("/saveLrEntry")
	public Map<String, Object> save(@RequestBody LrEntryBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		logger.info(" LrEntryBean :" + bean);
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString().trim();
			bean.setCompanyCode(companyCode);

			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setUserid(empId);
			bean.setIpaddress(request.getRemoteAddr());
			lrEntryService.saveLrEntry(bean);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Saved Succesfully");
		} catch (Exception e) {

			logger.info(" Error Occurred at saving LrEntryBean :" + e.getMessage());
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-transporters")
	public Map<String, Object> gettransporter(HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<Transporter_master> list = null;
		try {
			String jwt = request.getHeader("Authorization").replace("Bearer ", "");
			System.out.println("jwt:::"+jwt);
			String username = this.tokenProvider.getUsernameFromJwtToken(jwt);
			System.out.println("username::"+username);
			
			//get loc_id by username
			
			Long loc_id = null;
			try {
				loc_id = this.lrEntryService.getLocIdByUsername(username.trim());
				System.out.println("loc_id:::"+loc_id);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			if(loc_id==null || loc_id.equals(0L)) {
				list = lrEntryService.gettransportermaster();
			}
			else {
				list = lrEntryService.gettransportermasterForLocation(loc_id);
			}


			map.put("transporter", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(" Error Occurred : getting  transporters list");
			// TODO: handle exception
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getDataForConfirmationDispatch")
	public Map<String, Object> getDataForconfirmationdispatch(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("loc") List<String> loc, @RequestParam("div") List<String> div,
			@RequestParam("dest") List<String> dest, HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("startDate " + startDate);
		System.out.println("endDate " + endDate);

		
		String empId="";
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info(" empId :::::" + empId);
		Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		try {
			if(company.getCfa_to_stk_ind().equals("Y")) {
				List<LrEntryDataBean> details = lrEntryService.getConfirmationofdispatch_data(startDate, endDate, "A", "0",
						"0", "N", "N", "0", "0", loc, div, dest, company.getCfa_to_stk_ind());
				System.out.println("Filtered list : " + details.size());
				map.put("lrDetails", details);				
			}else {
				List<LrEntryDataBean> details = lrEntryService.getConfirmationofdispatch_data_for_sg(startDate, endDate, "A", "0",
						"0", "N", "N", "0", "0", loc, div, dest, company.getCfa_to_stk_ind());
				System.out.println("Filtered list : " + details.size());
				map.put("lrDetails", details);
				
			}
		}catch (Exception e) {

			logger.info("Error Occurred during get Data Fo rConfirmation Dispatch :" + e.getMessage());
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}

	@PostMapping("/updateConfirmationdispatch")
	public Map<String, Object> updateConfirmationdispatch(@RequestBody LrEntryBean bean, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		logger.info(" LrEntrybean :::::" + bean);

		try {
			lrEntryService.updateConfirmationdispatch(bean);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Saved Succesfully");
		} catch (Exception e) {

			logger.info("Error Occured During  update Confirmation dispatch :" + e.getMessage());
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getDataForLrbooking")
	public Map<String, Object> getDataForLrBooking(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("role") String role, @RequestParam("user") String user, @RequestParam("loc") List<String> loc,
			@RequestParam("div") List<String> div, @RequestParam("dest") List<String> dest,	HttpServletRequest request,
			@RequestParam("trans") String trans, HttpSession session) {
		
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<>();
		System.out.println("startDate " + startDate);
		System.out.println("endDate " + endDate);
		System.out.println("user " + user);
		System.out.println("role " + role);
		Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		try {
			List<LrEntryDataBean> details = lrEntryService.getLrcsvdownload_Revised_Lrbooking(startDate, endDate, "A",
					"0", "0", "N", "N", "0", "0", role, user, loc, div, dest, trans, company.getCfa_to_stk_ind());
			map.put("lrDetails", details);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error Occured During Get Data for LR Booking :" + e.getMessage());
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getLrRevisedfilterdata")
	public Map<String, Object> getLrRevisedfilterdata(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("dp_loc_id") String dp_loc_id, HttpSession session) {
		Map<String, Object> map = new HashMap<>();

		logger.info("Request from Depo_location :" + dp_loc_id);
		System.out.println("startDate " + startDate);
		System.out.println("endDate " + endDate);
		Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);
		try {

			List<Lr_csv_upload_java_proc_recised_filter> details = lrEntryService.getLrcsvdownload_Revised_filter(
					startDate, endDate, "A", dp_loc_id, "0", "N", "N", "0", "0", company.getCfa_to_stk_ind());
			map.put("filterdatalist", details);

		} catch (Exception e) {

			logger.info("Error Occured During Getting  For Lr entry Revisedfilterdata  :" + e.getMessage());
//			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getcurrentperiod")
	public Map<String, Object> getcurrentperiod(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {

			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(comp_code);

			map.put("startdate", period.getPeriod_start_date());
			map.put("endate", period.getPeriod_end_date());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}

	// changesss--------------------------------------------->

	@GetMapping("/getSendingLoction")
	public Location getSendingLoction(	HttpServletRequest request  ) {
		
		String empId="";
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.lrEntryService.getLocationNameByEmployeeId(empId);

	}

	@GetMapping("/getDataForGenExel")
	public Map<String, Object> getDataForGenExel(@RequestParam("trf_id") List<String> trf_id)
			throws IOException, ParseException {

		String fileName = lrEntryService.getDataForGenExel(trf_id);
		System.err.println(fileName);

		Map<String, Object> map = new HashMap<>();

		map.put("fileName", SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX + "/" + fileName);
		return map;

	}

	@GetMapping("/getRecevingLoction")
	public Map<String, List<Rec_Bean>> getRecevingLoction(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("loc") String location_id,
			@RequestParam("lrInd") String lrInd,@RequestParam ("finyear_flage") String finyear_flage) throws Exception {

		System.err.println(location_id);

		List<Tuple> list = lrEntryService.getRecevingLocations(startDate, endDate, location_id, lrInd,finyear_flage);

		List<Rec_Bean> lists = new ArrayList<>();

		list.forEach(l -> {

			Rec_Bean bean = new Rec_Bean();
			bean.setLoc_id(l.get(0).toString());
			bean.setLoc_nm(l.get(1).toString());
			lists.add(bean);
		});

		Map<String, List<Rec_Bean>> map = new HashMap<>();

		map.put("rec_loc", lists);
		return map;
	}

	
	
	
	
	
	@GetMapping("/getDataForStockkTransferLrEntry")
	public Map<String, Object> getDataForStockkTransferLrEntry(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("role") String role, @RequestParam("user") String user,
			@RequestParam("rec_loc") List<String> rec_loc, @RequestParam("sendLocation") String sendLocation,
			@RequestParam ("finyear_flage") String finyear_flage,
			HttpSession session) {

		
		
		Map<String, Object> map = new HashMap<>();
		System.out.println("startDate " + startDate);
		System.out.println("endDate " + endDate);
		System.out.println("user " + user);
		System.out.println("role " + role);
		System.out.println("rec loc " + rec_loc);
		System.out.println("sendLocation " + sendLocation);
		try {
//			List<LrEntryDataBean> details=lrEntryService.getLrcsvdownload_Revised_data(startDate,endDate, "A","0", "0", "N", "N", "0", "0",role,user
//					,loc,v,dest,trans);

			List<dataForStkLrEntry> listData = new ArrayList<>();
			List<Tuple> data = lrEntryService.getgetDataForStockkTransferLrEntry(startDate, endDate, rec_loc,
					sendLocation,finyear_flage);

			data.forEach(d -> {

				dataForStkLrEntry stklrData = new dataForStkLrEntry();
				stklrData.setTrf_id(d.get(0).toString());
				stklrData.setTrf_no(d.get(1).toString());

				String TrfDate = d.get(2).toString().substring(0, 10);
				String formatedTrfDate = "";
				try {
					String start_dt = TrfDate;
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Date date = (Date) formatter.parse(start_dt);
					SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
					formatedTrfDate = newFormat.format(date);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				stklrData.setTRF_DATE(formatedTrfDate.toString());
				stklrData.setLR_no(d.get(3).toString());
				stklrData.setLR_DATE(d.get(4).toString());
				stklrData.setTRANSPORTER_NAME(d.get(5).toString());

				listData.add(stklrData);
			});

			if (listData.size() > 0) {

				map.put("lrDetails", listData);
				map.put("stat", true);
			} else {
				map.put("stat", false);

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}

		System.out.println(map.get("stat"));
		return map;
	}

	@PostMapping("/saveDataForStockTransferLr")
	public Map<String, Object> saveDataForStockTransferLr(@RequestBody LrEntryBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println(bean);
			bean.setIpaddress(request.getRemoteAddr());
			int saved = lrEntryService.saveDataForStockTransferLr(bean);
			if (saved > 0) {
				map.put(DATA_SAVED, true);
				map.put(RESPONSE_MESSAGE, "Stock Transfer Lr Entry Saved Successfully");
			} else {
				map.put(DATA_SAVED, false);
				map.put(RESPONSE_MESSAGE, "Error Occurred  ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = new HashMap<>();
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : " + new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/getStockTransferList")
	public Map<String, Object> getStockTransferList(HttpSession session, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("locationId") String locationId,
			@RequestParam("destinationlist") List<String> destinationlist) {

		List<TRF_BEAN_stocktransfer_download_trdata> listOfTrnData = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		try {

//			System.err.println(startDate+"::::"+endDate+"::::::"+locationId+"::::::"+destinationlist);

			listOfTrnData = lrEntryService.getStockTransferList(startDate, endDate, locationId, destinationlist);
			map.put("listOfTrnData", listOfTrnData);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
			map.put("listOfTrnData", listOfTrnData);
		}
		return map;
	}

	// changessss----------------------------------------->

	@GetMapping("/getDp-loc-id")
	public String getDpLocId(@RequestParam("loc_id") String loc_id) throws IOException, ParseException {

		String dp_loc_id = "";

		try {

			dp_loc_id = this.lrEntryService.getDpLocId(loc_id);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dp_loc_id;

	}

}
