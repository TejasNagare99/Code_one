package com.excel.restcontroller;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.client.RestTemplate;

import com.excel.bean.ErpBatch_stk_recobean;
import com.excel.bean.ErpDispatchConfirmationBean;
import com.excel.bean.Erp_Batch_stk_reco_quarantinebean;
import com.excel.bean.Erp_tran_stock_Quarantine_recobean;
import com.excel.bean.Erp_tran_stock_recobean;
import com.excel.configuration.JwtProvider;
import com.excel.model.Company;
import com.excel.model.Erp_Batch_stk_reco;
import com.excel.model.Erp_Batch_stk_reco_quarantine;
import com.excel.model.Period;
import com.excel.service.DispatchService;
import com.excel.service.DivisionMasterService;
import com.excel.service.ErpService;
import com.excel.service.Erp_batchstockreco_report_service;
import com.excel.service.PeriodMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.google.gson.Gson;

@RestController
@RequestMapping("/rest")
public class ErpController implements MedicoConstants{
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;
	@Autowired ErpService erpService;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired RestTemplate restTemplate;
	@Autowired Gson gson;
	@Autowired DivisionMasterService divisionMasterService;
	@Autowired DispatchService dispatchService;
	@Autowired Erp_batchstockreco_report_service erp_batchstockreco_report_service;
	
	public static String wmsMedicoSysytemId="E00040";
//	@GetMapping("/getproductdetails")
//	public Map<String, Object> getproductDetails(HttpSession session) {
//		Map<String, Object> map=null;
//		try {
//	        String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//		    Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//		    this.erpService.getproductDetails(period.getPeriod_fin_year());
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
//	
//	@GetMapping("/getbatchdetails")
//	public Map<String, Object> getbatchDetails(HttpSession session) {
//		Map<String, Object> map=null;
//		JSONObject finalObject=new JSONObject();
//		try {
//	        String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//		    Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//		    this.erpService.getbatchDetails(period.getPeriod_fin_year());
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
//	@GetMapping("/getDispatchDetails")//IBD
//	public Map<String, Object> getDispatchDetails(HttpSession session) {
//		Map<String, Object> map=null;
//		JSONObject finalObject=new JSONObject();
//		try {
//			
//	        String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			this.erpService.getDispatchDetails(period.getPeriod_fin_year());
//			
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
//	
//	@GetMapping("/getdispatchCanceldetails")
//	public Map<String, Object> getdispatchCancelDetails(HttpSession session) {
//		Map<String, Object> map=null;
//		JSONObject finalObject=new JSONObject();
//		try {
//	        String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			this.erpService.getdispatchCancelDetails(period.getPeriod_fin_year());
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			map.put(RESPONSE_MESSAGE, "error");
//		}
//		return map;
//	}
//	
	@PostMapping("/confirmDispatch")
	public  Map<String, Object>  confirmDispatch(@RequestBody Map<String,
			List<ErpDispatchConfirmationBean>> confirmationBean, HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		try {
			List<ErpDispatchConfirmationBean> bean= confirmationBean.get("confirmDispatch");
			List<Long> failedDispatch=new ArrayList<>();
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			for(int i=0;i<bean.size();i++) {
				try {
					this.dispatchService.finalDispatchApproval(Long.valueOf(bean.get(i).getDispatch_id()),this.wmsMedicoSysytemId, companyCode, bean.get(i).getStatus(),  bean.get(i).getIpAddress());
				}
				catch (Exception e) {
					failedDispatch.add(Long.valueOf(bean.get(i).getDispatch_id()));
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
			}
			map.put(RESPONSE_MESSAGE, "Data recieved successfully");
			map.put("faledDispatches",failedDispatch);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, "Failed To recieve data");
		}
		return map;
	}
	@GetMapping("/confirmDispatchManually")
	public  Map<String, Object>  confirmDispatchManually(@RequestParam("dspId") Long dspId) {
		Map<String, Object> map=new HashMap<>();
		try {
			List<Long> failedDispatch=new ArrayList<>();
			String companyCode = "PFZ";
			this.dispatchService.finalDispatchApproval(dspId,this.wmsMedicoSysytemId, companyCode,"A","0");
			
			map.put(RESPONSE_MESSAGE, "Data recieved successfully");
			map.put("faledDispatches",failedDispatch);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			map.put(RESPONSE_MESSAGE, "Failed To recieve data");
		}
		return map;
	}
//	@GetMapping("/getgrndetails")//IBD
//	public Map<String, Object> getGrnDetails(HttpSession session) {
//		Map<String, Object> map=null;
//		JSONObject finalObject=new JSONObject();
//		try {
//			
//	        String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			this.erpService.getGrnDetails(period.getPeriod_fin_year());
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
//	
//	
//
//
//	@GetMapping("/getgrnconfirmationdetails")
//	public Map<String, Object> getGrnconfirmationDetails(HttpSession session) {
//		Map<String, Object> map=null;
//		JSONObject finalObject=new JSONObject();
//		try {
//			
//	        String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			this.erpService.getGrnconfirmationDetails(period.getPeriod_fin_year());
//			
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
//	
//	
//	
//	@GetMapping("/getIaaGrndetails")
//	public Map<String, Object> getIaaconfirmationDetails(HttpSession session) {
//		Map<String, Object> map=null;
//		JSONObject finalObject=new JSONObject();
//		try {
//	        String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			this.erpService.getIaaGrnDetails(period.getPeriod_fin_year());
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
//	
//	
//	///from here
//	@GetMapping("/geterp-stockwithdrawal")
//	public Map<String, Object> getErpstockwithdrawal(@RequestParam("username")String username,@RequestParam("password")String password,HttpSession session) {
//		Map<String, Object> map=null;
//		try {
//			
//		//	if(username.equals(MedicoConstants.USERNAME) && password.equals(MedicoConstants.PASSWORD)) {
//			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			
//			map = this.erpService.getErpstockwithdrawaldata(period.getPeriod_fin_year());
//			map.put("Success", "Success");
//		//	}
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			map.put("Error", "Error");
//		}
//		return map;
//	}
//	
//	@PostMapping("/wmsIaa")
//	public  Map<String, Object>  iaaFromWms(@RequestBody Map<String,List<ErpIaaBean>> iaaBean, HttpSession session) {
//		Map<String, Object> map=new HashMap<>();
//		try {
//			List<ErpIaaBean> bean= iaaBean.get("iaa");
//			List<String> failedIaa=new ArrayList<>();
//			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//			Date today = new Date();
//			Date taskDate = MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(period.getPeriod_end_date());
//			String trnDate="";
//			Long receipt_datetime=0l;
//			if (today.compareTo(taskDate) > 0) {
//				trnDate=dateFormat.format(taskDate);
//				receipt_datetime = taskDate.getTime();
//			} else {
//				trnDate=dateFormat.format(today);
//				receipt_datetime = today.getTime();
//			}
//			
//			for(int i=0;i<bean.size();i++) {
//				try {
//					bean.get(i).setTranDate(trnDate);
//					bean.get(i).setCompany(companyCode);
//					bean.get(i).setFinYearId(period.getPeriod_fin_year());
//					bean.get(i).setPeriodCode(period.getPeriod_code());
//					bean.get(i).setUserName(this.wmsMedicoSysytemId);
//					this.erpService.saveIaaEntry(bean.get(i));
//				}
//				catch (Exception e) {
//					failedIaa.add(bean.get(i).getWms_iaa_number());
//					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//				}
//			}
//			map.put(RESPONSE_MESSAGE, "Data recieved successfully");
//			map.put("failedIaa",failedIaa);
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			map.put(RESPONSE_MESSAGE, "Failed To recieve data");
//		}
//		return map;
//	}
//	
//	
//	@GetMapping("/geterp-normalIaa")
//	public Map<String, Object> getErpNormalIAA(HttpSession session) {
//		Map<String, Object> map=new HashMap<>();
//		try {
//			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			this.erpService.getErp_NormalIaadata(period.getPeriod_fin_year());;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//	
//		return map;
//	}
//	
//	
//	@GetMapping("/geterp-wms-Iaa-confirmation")
//	public Map<String, Object> getErpWmsIaaConfirmation(HttpSession session) {
//		Map<String, Object> map=new HashMap<>();;
//		List<Erp_Wms_Iaa_confirmationbean> list = null;
//		try {
//			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
//			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
//			this.erpService.getErp_Wms_Iaa_Confirmationdata(period.getPeriod_fin_year());
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//		
//	}
	@PostMapping("/stockReco")
	public Map<String, Object> getErpBatchstk_recodata(@RequestBody Map<String,List<ErpBatch_stk_recobean>> iaaBean, HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		try {
			List<ErpBatch_stk_recobean> bean= iaaBean.get("stockReco");
			System.out.println(bean.size());
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
		    Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			bean.get(0).setFin_year(period.getPeriod_fin_year());
			this.erpService.getErp_Batch_stk_recodata(bean);
			map.put("status", "success");
		}
		catch (Exception e) {
			e.printStackTrace();
			map.put("status", "error");
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
		
	}
	
	@PostMapping("/stockRecoQuarantine")
	public Map<String, Object> getErpBatchstk_reco_quarantinedata(@RequestBody Map<String,List<Erp_Batch_stk_reco_quarantinebean>> iaaBean, HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		
		try {
			List<Erp_Batch_stk_reco_quarantinebean> bean= iaaBean.get("stockRecoQuarantine");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String currentdate = sdf.format(new Date());
			bean.get(0).setReco_date(currentdate);
			this.erpService.getErp_Batch_stk_reco_quarantine_data(bean);
			map.put("status", "success");
		}
		catch (Exception e) {
			e.printStackTrace();
			map.put("status", "error");
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
	}
	
	@PostMapping("/tranReco")
	public Map<String, Object> getErpTranwisedata(@RequestBody Map<String,List<Erp_tran_stock_recobean>> stockRecoBean, HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		
		try {
			List<Erp_tran_stock_recobean> bean= stockRecoBean.get("tranReco");
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			System.out.println("year:::"+period.getPeriod_fin_year());
			String curr_year=period.getPeriod_fin_year();
			map = this.erpService.getErp_Tran_wise_data(bean,curr_year);
			map.put("Success", "Success");
		}
		catch (Exception e) {
			e.printStackTrace();
			map.put("status", "error");
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
	}
	
	@PostMapping("/tranReco_Quarantine")
	public Map<String, Object> getErpTranwiseQuarantinedata(@RequestBody Map<String,List<Erp_tran_stock_Quarantine_recobean>> stockRecoBean, HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		
		try {
			List<Erp_tran_stock_Quarantine_recobean> bean= stockRecoBean.get("tranReco_Quarantine");
			System.out.println("bean ist:::"+bean.size());
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			System.out.println("year:::"+period.getPeriod_fin_year());
			String curr_year=period.getPeriod_fin_year();
			
			map = this.erpService.getErp_Tran_wise_Quarantine_data(bean,curr_year);
			map.put("status", "success");
		}
		catch (Exception e) {
			map.put("status", "error");
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
	}
	
	
	@PostMapping("/get-batch-stk-reco-report")
	public Map<String, Object> getBatchstkrecoreport(HttpSession session,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<>();
		List<Erp_Batch_stk_reco>list = new ArrayList<>();
		String filename=null ;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			list = erpService.getdataforstockreco();
			if(list.size()!=0) {
			filename = erp_batchstockreco_report_service.generatebatch_stk_reco_report(list,companyname,empId);
			map.put("filename",SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX+"/"+filename);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/stockRecoQuarantine_report")
	public Map<String, Object> getErpBatchstk_reco_quarantine_report_data( HttpSession session,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<>();
		List<Erp_Batch_stk_reco_quarantine> list = null;
		String filename;
		try {
			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
			String companyname = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getCompany_name();
			list = erpService.getstockRecoQuarantinedata();
			System.out.println("list size:::"+list.size());
			if(list.size()!=0) {
				map.put("list", list);
				filename = erp_batchstockreco_report_service.generate_stockRecoQuarantine_Report(list, companyname,empId);		
				map.put("filename",SPRING_RESOURCE_HANDLER_REPORT_FILE_PATH_PREFIX+"/"+filename);		
			}
		}
		
	 catch (Exception e) {
			map.put("status", "error");
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
		return map;
	}
	
	
	@PostMapping("/ManualDispatchApproval")
	public Map<String, Object> getErpSOSDispatchApproval(@RequestParam("dspId")String dspId,@RequestParam("status")String status,@RequestParam("ipaddr")String addr, HttpSession session) {
		Map<String, Object> map=new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			this.dispatchService.finalDispatchApproval(Long.valueOf(dspId),this.wmsMedicoSysytemId, companyCode, status, addr);
			map.put("Status", "SUCCESS");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

}


