package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.EInvoiceByIrnData;
import com.excel.bean.EInvoiceByIrnResponse;
import com.excel.bean.EInvoiceCancelData;
import com.excel.bean.EInvoiceResponse;
import com.excel.bean.EInvoiceTransaction;
import com.excel.bean.EInvoiceUIBean;
import com.excel.bean.EwayBillCancelData;
import com.excel.bean.EwayBillCancelResponse;
import com.excel.bean.EwayBillPrintData;
import com.excel.bean.IrnCancelResponse;
import com.excel.model.Company;
import com.excel.model.Dispatch_Header;
import com.excel.model.DivMaster;
import com.excel.model.EInvoiceHeader;
import com.excel.model.EInvoiceHeaderStockist;
import com.excel.model.E_invoice_report;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.model.Sum_Disp_Header;
import com.excel.service.DispatchService;
import com.excel.service.EInvoiceService;
import com.excel.service.LocationService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/rest")
public class EInvoiceController implements MedicoConstants
{

	@Autowired EInvoiceService einvoiceservice;
	@Autowired LocationService locationservice;
	@Autowired DispatchService dispatchService;
	@Autowired private UserMasterService userMasterService;
	@PostMapping("/genEInvoice")
	public Map<String,Object> generateEInvoice(@RequestBody EInvoiceUIBean bean,
			HttpSession session) throws JSONException
	{
		JSONObject jb = new JSONObject();
		String host=null;
		String msg = null;
		boolean isError=false;
		String finYearFlag;
		Map<String,Object> map=new HashMap<String, Object>();
		try{
		System.out.println("currfinyrId : "+bean.getCurrentyear());
		System.out.println("FinyrId : "+bean.getFinyearid());
		System.out.println("Ewaybill : "+bean.getGenerate_eway());
		System.out.println("divId : "+bean.getDivisionid());
		System.out.println("locId : "+bean.getLoc_id());
		System.out.println("fromchln : "+bean.getFromchallan());
		System.out.println("toChln : "+bean.getTochallan());
		
		String comp_cd=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		host =((Company)session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
		String url=host+"/v2/eInvoice/generate";
		
		System.out.println("URL : "+url);
		 isError=false;
			if(bean.getFinyearid().equals(bean.getCurrentyear())) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			bean.setFinyearflag(finYearFlag);
			
			List<EInvoiceTransaction> parameters=einvoiceservice.generateEInvoice(finYearFlag, bean.getFinyearid(), bean.getGenerate_eway(),
					bean.getDivisionid(), bean.getFromchallan(), bean.getTochallan());
			if(parameters!=null && parameters.size()>0) {
				Location location=locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));//new Location_dao().getLocationById(loc_id);
				if(location.getAuth_token()!=null) {
					String  authToken = location.getAuth_token();//=location.getAuth_token().trim();
					String ownerId = location.getOwner_id();//=location.getOwner_id().trim();
					String gstNo = location.getGst_reg_no().trim();//=location.getGst_reg_no().trim();
					String jsonResponse=einvoiceservice.generateEinvoice(url,new Gson().toJson(parameters).toString(), gstNo, ownerId, authToken,"PUT");
					List<EInvoiceResponse> response = new Gson().fromJson(jsonResponse, new TypeToken<List<EInvoiceResponse>>() {}.getType());
					einvoiceservice.saveEInvoiceResponse(response, comp_cd, bean.getDivisionid(), bean.getFinyearid(),finYearFlag);
					//	tr.commit();
					if(parameters.size()==1) {
						if(response.get(0).getGovt_response().getSuccess().equals("Y")) {
							msg="EInvoice Generated Successfully";
						}
						else {
							msg=response.get(0).getGovt_response().getErrorDetails().get(0).getError_message();
						}
					}
					else {
						msg="EInvoice Generated Successfully";
					}
				}
				else {
					msg="EInvoice Details Not found";
				}
		
			}
			else {
				msg="Invoice Not found";
			}
			
			map.put("msg", msg);
		}catch(Exception e){
			e.printStackTrace();
			msg="Error Occurred";
			isError=true;
		//	tr.rollback();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return  map;
	
	}
	
	@GetMapping("/getTeamDivison")
	public Map<String, Object>  getTeamDivision( HttpServletRequest request ) {
		Map<String, Object> map = new HashMap<>();
		List<DivMaster> list=null;
		List<Period> periodlist=null;
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			list=einvoiceservice.getteams(empId);
			periodlist=einvoiceservice.getperiod();
		}catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		map.put("teams", list);
		map.put("finyearlist", periodlist);
		return map;		
		
	}
	
	
	@GetMapping("/getChallan")
	public Map<String, Object>  getchallans(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("currentyear") String currentyear,
			@RequestParam("loc_id") Long loc_id) {
		Map<String, Object> map = new HashMap<>();
	//	List<Period> periodlist=null;
	//	Period period;
//		List<>
//		System.out.println("Finyear"+finyear);
		List<Sum_Disp_Header> dsplist = new ArrayList<Sum_Disp_Header>();
		String finYearFlag=null; 
		try {
	//		period=einvoiceservice.getstartdate();
			if(finyear.equals(currentyear)) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			dsplist = einvoiceservice.getChallanDetails(finyear, team,finYearFlag,loc_id);
			map.put("challans", dsplist);
			
		}catch(Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		//map.put("teams", list);
		return map;
	}
	
	@GetMapping("/irnChallansWithoutEway")
	public Map<String, Object>  irnChallansWithoutEway(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("currentyear")String currentyear,
			@RequestParam("loc_id") Long loc_id) {
		Map<String, Object> map = new HashMap<>();
		List<EInvoiceHeader> list = new ArrayList<EInvoiceHeader>();
		String finYearFlag=null; 
		try {
			if(finyear.equals(currentyear)) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			list = einvoiceservice.getIrnChallansWithoutEway(finyear, team,finYearFlag,loc_id);
			map.put("Irnlist", list);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/generateEWayBillByIrn")//With EWAYBILL
	public Map<String,Object> generateEInvoiceByIrn(@RequestBody EInvoiceUIBean bean,
			HttpSession session) throws JSONException{
		//Session hsession = null;
		//Transaction tr = null;
		Map<String,Object>map = new HashMap<String, Object>();
		String msg = "";
		JSONObject jb = new JSONObject();
		boolean isError=false;
		String finYearFlag="";
		try{
			System.out.println("currfinyrId : "+bean.getCurrentyear());
			System.out.println("FinyrId : "+bean.getFinyearid());
			System.out.println("Ewaybill : "+bean.getGenerate_eway());
			System.out.println("divId : "+bean.getDivisionid());
			System.out.println("locId : "+bean.getLoc_id());
			System.out.println("fromchln : "+bean.getFromchallan());
			System.out.println("toChln : "+bean.getTochallan());
		//HttpSession session = ServletActionContext.getRequest().getSession();
		String comp_cd=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		String host =((Company)session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
		//String loc_id ="";//(String) session.getAttribute("EMP_LOC");
		//String url=host+"/einv/v2/eInvoice/ewaybill";
		String url=host+"/v2/eInvoice/ewaybill";
		System.out.println("URL : "+url);
		isError=false;
		if(bean.getFinyearid().equals(bean.getCurrentyear())) {
			finYearFlag="CURRENT";
		}
		else {
			finYearFlag="PRIVIOUS";
		}
			List<EInvoiceByIrnData> parameters=einvoiceservice.generateEInvoiceByIrn(finYearFlag, bean.getFinyearid(), bean.getGenerate_eway(), bean.getDivisionid(), bean.getFromchallan(), bean.getTochallan());//new EInvoiceGeneration().generateEInvoiceByIrn(hsession,finYearFlag,finYearId,generate_eway,divisionid,frmChallan,toChallan);
			if(parameters!=null && parameters.size()>0) {
		//		hsession = HibernateUtil.getSessionFactory().openSession();
			//	tr = hsession.beginTransaction();
				Location location=locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));//new Location_dao().getLocationById(loc_id);
				if(location.getAuth_token()!=null) {
					String  authToken=location.getAuth_token().trim();
					String ownerId=location.getOwner_id().trim();
					String gstNo=location.getGst_reg_no().trim();

					String jsonResponse=einvoiceservice.generateEinvoice(url,new Gson().toJson(parameters).toString(),gstNo,ownerId,authToken,"POST");//new EInvoiceGeneration().generateEinvoice(url,new Gson().toJson(parameters).toString(),gstNo,ownerId,authToken,"POST");
					List<EInvoiceByIrnResponse> response = new Gson().fromJson(jsonResponse, new TypeToken<List<EInvoiceByIrnResponse>>() {}.getType());
					einvoiceservice.saveEInvoiceResponseIrn(response,comp_cd,bean.getDivisionid());
					//tr.commit();
					if(parameters.size()==1) {
						if(response.get(0).getGovt_response().getSuccess().equals("Y")) {
							msg="E-Way Bill Generated Successfully";
						}
						else {
							msg=response.get(0).getGovt_response().getErrorDetails().get(0).getError_message();
						}
					}
					else {
						msg="E-Way Bill Generated Successfully";
					}
				}
				else {
					msg="E-Way Bill Details Not found";
				}
		
			}
			else {
				msg="Invoice Not found";
			}
			map.put("msg", msg);
		}catch(Exception e){
			msg="Error Occurred";
		//	jb.put("errorMsg", errorMsg);
		//	tr.rollback();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}finally{
		//	hsession.close();
		}
		return  map;
	}
	
	
	@GetMapping("/getIrnCancelData")
	public Map<String,Object> irnCancelChallans(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("loc_id") Long loc_id) {
			Map<String,Object>map = new HashMap<String, Object>();
			List<EInvoiceHeader> list = new ArrayList<EInvoiceHeader>();
			try {
				list = einvoiceservice.getIrnGencancelData(finyear, team ,loc_id);
				map.put("challans", list);
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
	}
	
	@PostMapping("/cancelEInvoive")
	public Map<String,Object> cancelEInvoive(@RequestBody EInvoiceUIBean bean,
			HttpSession session) throws JSONException{
	//	System.out.println("from"+frmChallan+" toSlNo"+toChallan);
		boolean isError=false;
	//	HttpSession session = ServletActionContext.getRequest().getSession();
				//loc_id ="";//(String) session.getAttribute("EMP_LOC");
	//	Session hsession = null;
	//	Transaction tr = null;
		String finYearFlag=null;
		String msg="";
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			isError=false;
			System.out.println("currfinyrId : "+bean.getCurrentyear());
			System.out.println("FinyrId : "+bean.getFinyearid());
			System.out.println("Ewaybill : "+bean.getGenerate_eway());
	 		System.out.println("divId : "+bean.getDivisionid());
			System.out.println("locId : "+bean.getLoc_id());
			System.out.println("fromchln : "+bean.getFromchallan());
			System.out.println("toChln : "+bean.getTochallan());
			
			String comp_cd=(String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host =((Company)session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
		//	String url=host+"/einv/v2/eInvoice/cancel";
			String url=host+"/v2/eInvoice/cancel";
			System.out.println("URL : "+url);
			
			if(bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			
			List<Object> list=einvoiceservice.getIrnCancelData(bean.getFromchallan(), bean.getTochallan(), 
					bean.getDivisionid(),finYearFlag);//new E_InvoiceDao().getIrnCancelData(fromChallan, tochallan,divisionid);
			List<String> demo=new ArrayList<String>();
			System.out.println(list.size());
			for (Object objArr : list) {
				Object object[]=(Object[])objArr;
				demo.add(object[0].toString());
			}
		//	hsession = HibernateUtil.getSessionFactory().openSession();
		//	tr = hsession.beginTransaction();
			Location location=locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));//new Location_dao().getLocationById(loc_id);
			String  authToken=location.getAuth_token().trim();
			String ownerId=location.getOwner_id().trim();
			String gstNo=location.getGst_reg_no().trim();
			List<EInvoiceCancelData> parameters=einvoiceservice.setEInvoiceCancelData(demo);
			String jsonResponse=einvoiceservice.generateEinvoice(url,new Gson().toJson(parameters).toString(),gstNo,ownerId,authToken,"PUT");
			List<IrnCancelResponse> response = new Gson().fromJson(jsonResponse, new TypeToken<List<IrnCancelResponse>>() {}.getType());
			einvoiceservice.cancelIrn(response,comp_cd);
		//	tr.commit();
			msg = "EInvoice Cancelled Successfully";
			map.put("msg",msg);
		}catch(Exception e){
			isError=true;
			msg = "EInvoice Cancellation Failed";
			map.put("msg",msg);
		//	tr.rollback();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}finally{
		//	hsession.close();
		}
		return map;
	}
	
	@GetMapping("/ewayBillChallans")
	public Map<String,Object> getEWayBillchallans(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("currentyear")String currentyear,
			@RequestParam("loc_id") Long loc_id,
			HttpSession session) throws JSONException{
		Map<String,Object> map = new HashMap<String, Object>();
		List<EInvoiceHeader> list = new ArrayList<EInvoiceHeader>();
		String finYearFlag = null;
		try {
			if(finyear.trim().equals(currentyear)) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			
			list = einvoiceservice.getEwayBillCancel(finyear, team,finYearFlag,loc_id);
			map.put("challans",list);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
	
	@PostMapping("/cancelEWayBill")
	public Map<String,Object> cancelEWayBill(@RequestBody EInvoiceUIBean bean,
			HttpSession session) throws JSONException{
	///	System.out.println("Eway Bill no "+frmChallan);
	//	HttpSession session = ServletActionContext.getRequest().getSession();
			//loc_id ="";//(String) session.getAttribute("EMP_LOC");
		Map<String,Object>map=new HashMap<String, Object>();
		String msg="";
	//	Session hsession = null;
	//	Transaction tr = null;
		try{
			String comp_cd=(String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host =((Company)session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			//String url=host+"/einv/v2/eInvoice/ewaybill/cancel";
			String url=host+"/v2/eInvoice/ewaybill/cancel";
			System.out.println("url :"+url);
		///	hsession = HibernateUtil.getSessionFactory().openSession();
		//	tr = hsession.beginTransaction();
			Location location=locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
			String  authToken=location.getAuth_token().trim();
			String ownerId=location.getOwner_id().trim();
			String gstNo=location.getGst_reg_no().trim();
			
		    EwayBillCancelData parameters=einvoiceservice.setEwayBillCancelData(bean.getFromchallan());
			String jsonResponse=einvoiceservice.cancelEwayBill(url,new Gson().toJson(parameters).toString(),gstNo,ownerId,authToken);
			 System.out.println("jsonResponse "+jsonResponse);
			EwayBillCancelResponse response = new Gson().fromJson(jsonResponse, new TypeToken<EwayBillCancelResponse>() {}.getType());
			if(response.getEwbStatus()!=null && response.getEwbStatus().equalsIgnoreCase("CANCELLATION_FAILED")) {
				msg=response.getErrorDetails().getError_message();
			}
			else {
				einvoiceservice.cancelEwayBill(response,comp_cd);
				msg="E-Way Bill Cancelled";
			}
			System.out.println("MSG "+msg);
			map.put("msg", msg);
		//	tr.commit();
			
		}catch(Exception e){
		//	tr.rollback();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}finally{
		//	hsession.close();
		}
		return map;
	}
	
	@GetMapping("/irnDwonloadChallans")
	public Map<String,Object> irnDwonloadChallans(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("currentyear")String currentyear,
			@RequestParam("loc_id") Long loc_id,
			HttpSession session) {
		Map<String,Object>map = new HashMap<String, Object>();
		List<EInvoiceHeader> list = new ArrayList<EInvoiceHeader>();
		String finYearFlag = "";
		try {
			if(finyear.equals(currentyear)) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			
			list = einvoiceservice.getIrnDownloadData(finyear, team,finYearFlag,loc_id);
			map.put("challans", list);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	
//	@GetMapping("/ewayBillChallans")
//	public Map<String,Object> ewayBillChallans(@RequestParam("team") String team,
//			@RequestParam("finyear") String finyear,
//			HttpSession session) {
//		Map<String,Object>map = new HashMap<String, Object>();
//		List<EInvoiceHeader> list = new ArrayList<EInvoiceHeader>();
//		try {
//			list=einvoiceservice.getEwayBillCancel(finyear, team);
//			map.put("challans", list);
//		}
//		catch (Exception e) {
//		// TODO: handle exception
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}
	
	@PostMapping("/downloadIrn")
	public Map<String,Object> downloadIrn(@RequestBody EInvoiceUIBean bean,HttpSession session) {
		//System.out.println("Irn"+frmChallan);
		String parameters=null;
		//HttpSession session = ServletActionContext.getRequest().getSession();
		//Session hsession = null;
		//Transaction tr = null;
		String fileName = null;
		String finYearFlag=null;
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			String comp_cd=(String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host =((Company)session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
		//	String loc_id =(String) session.getAttribute("EMP_LOC");
		//	String url=host+"/einv/v2/eInvoice/download";
			String url=host+"/v2/eInvoice/download";
			System.out.println("url : "+url);
			
			if(bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			
			List<Object> list=einvoiceservice.getIrnCancelData(bean.getFromchallan(), bean.getTochallan(),
					bean.getDivisionid(),finYearFlag);
			List<String> irns=new ArrayList<String>();
			System.out.println(list.size());
			for (Object objArr : list) {
				Object object[]=(Object[])objArr;
				irns.add(object[0].toString());
			}
			parameters=irns.toString().replaceAll("\\[", "").replaceAll("\\]","");
			parameters=parameters.replaceAll(",", "&irns=").replaceAll(" ", "");
			System.out.println("parameters : "+parameters);
			String fpath = MedicoConstants.PDF_FILE_PATH+"files//"; //ServletActionContext.getRequest().getSession().getServletContext().getRealPath("")+"\\excelDownload\\";
		//	hsession = HibernateUtil.getSessionFactory().openSession();
	//		tr = hsession.beginTransaction();
			Location location=locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
			String  authToken=location.getAuth_token().trim();
			String ownerId=location.getOwner_id().trim();
			String gstNo=location.getGst_reg_no().trim();
			fileName=einvoiceservice.downloadFileIrn(fpath,url,parameters,gstNo,ownerId,authToken,irns.size());
			System.out.println("file "+fileName);
		//	tr.commit();
			map.put("filename",fileName);
			map.put("isData", true);
		}catch(Exception e){
		//	tr.rollback();
			map.put("isData", false);
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}finally{
		//	hsession.close();
		}
		return map;
	}
	
	@PostMapping("/downloadEwayBill")
	public Map<String,Object> downloadEwayBill(@RequestBody EInvoiceUIBean bean,HttpSession session) {
	//	System.out.println("Eway Bill no "+frmChallan);
		boolean isError=false;
	//	HttpSession session = ServletActionContext.getRequest().getSession();
			String fileName=null;
			String finYearFlag=null;
	//	Session hsession = null;
	//	Transaction tr = null;
		Map<String,Object>map = new HashMap<String, Object>();
		try{
			String comp_cd=(String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host =((Company)session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			String loc_id =(String) session.getAttribute("EMP_LOC");
		//	String url=host+"/einv/v2/eInvoice/ewaybill/print";
			String url=host+"/v2/eInvoice/ewaybill/print";
			System.out.println("url : "+url);
			if(bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			
			List<Object> list=einvoiceservice.getEwayBillDownloadNumbers(bean.getFinyearid(),bean.getDivisionid(),bean.getFromchallan(), bean.getTochallan(),finYearFlag);
			List<String> ewbNumbers=new ArrayList<String>();
			System.out.println(list.size());
			for (Object objArr : list) {
				Object object[]=(Object[])objArr;
				ewbNumbers.add(object[0].toString());
			}
			String fpath = MedicoConstants.PDF_FILE_PATH+"files//"; //ServletActionContext.getRequest().getSession().getServletContext().getRealPath("")+ "\\excelDownload\\";
		//	hsession = HibernateUtil.getSessionFactory().openSession();
		//	tr = hsession.beginTransaction();
			Location location=locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));//new Location_dao().getLocationById(loc_id);
			String  authToken=location.getAuth_token().trim();
			String ownerId=location.getOwner_id().trim();
			String gstNo=location.getGst_reg_no().trim();
			EwayBillPrintData parameters=einvoiceservice.setEwayBillDownloadData(ewbNumbers);
			fileName=einvoiceservice.downloadFile(fpath,url,new Gson().toJson(parameters).toString(),gstNo,ownerId,authToken,ewbNumbers.size());
			System.out.println("file "+fileName);
			System.out.println("Downloaded");
			map.put("filename",fileName);
		//	tr.commit();
			map.put("isData", true);
		}catch(Exception e){
			isError=true;
			map.put("isData", false);
		//	tr.rollback();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}finally{
			//hsession.close();
		}
		return map;
	}
	
	//get stockist data
	
	@GetMapping("/getChallanStockist")
	public Map<String, Object>  getchallanStockist(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("currentyear") String currentyear,
			@RequestParam("loc_id") Long loc_id) {
		Map<String, Object> map = new HashMap<>();

		List<Dispatch_Header> dsplist = new ArrayList<Dispatch_Header>();
		String finYearFlag=null; 
		try {
			if(finyear.equals(currentyear)) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			dsplist = einvoiceservice.getChallanDetailsStockist(finyear, team,finYearFlag,loc_id);
			map.put("challans", dsplist);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/irnChallansWithoutEwayStk")
	public Map<String, Object>  irnChallansWithoutEwayStockist(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("currentyear")String currentyear,
			@RequestParam("loc_id") Long loc_id) {
		Map<String, Object> map = new HashMap<>();
		List<EInvoiceHeaderStockist> list = new ArrayList<EInvoiceHeaderStockist>();
		List<Dispatch_Header> disp_header = null;
		String finYearFlag=null; 
		try {
			if(finyear.equals(currentyear)) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			list = einvoiceservice.getIrnChallansWithoutEwayStockist(finyear, team,finYearFlag,loc_id);
			map.put("Irnlist", list);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getIrnCancelDataStockist")
	public Map<String,Object> irnCancelChallansStocksit(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("loc_id") Long loc_id) {
			Map<String,Object>map = new HashMap<String, Object>();
			List<EInvoiceHeaderStockist> list = new ArrayList<EInvoiceHeaderStockist>();
			try {
				list = einvoiceservice.getIrnGencancelDataStockist(finyear, team ,loc_id);
				map.put("challans", list);
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
	}
	
	@GetMapping("/ewayBillChallansStockist")
	public Map<String,Object> getEWayBillchallansStockist(@RequestParam("team") String team,
			@RequestParam("finyear") String finyear,@RequestParam("currentyear")String currentyear,
			@RequestParam("loc_id") Long loc_id,
			HttpSession session) throws JSONException{
		Map<String,Object> map = new HashMap<String, Object>();
		List<EInvoiceHeaderStockist> list = new ArrayList<EInvoiceHeaderStockist>();
		String finYearFlag = null;
		try {
			if(finyear.trim().equals(currentyear)) {
				finYearFlag=MedicoConstants.CURRENT;
			}
			else {
				finYearFlag=MedicoConstants.PREVIOUS;
			}
			list = einvoiceservice.getEwayBillCancelStockist(finyear, team,finYearFlag,loc_id);
			map.put("challans",list);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/getDateWiseSumChallans")
	public ResponseEntity<List<Dispatch_Header>> getListOFSumChallansForDate(
			@RequestParam Long locid,@RequestParam String date) throws Exception{
		try {
			List<Dispatch_Header> dsp_hdr = this.dispatchService.getListOfSumDispForEInvoice(locid,date);
			return ResponseEntity.ok(dsp_hdr);
		}
		catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	

	
	@GetMapping("/getE_invoiceReportData")
	public Map<String,Object> getE_invoiceReportData(@RequestParam String stdate ,@RequestParam String end_dt ,@RequestParam String div_id ) {
			Map<String,Object>map = new HashMap<String, Object>();
			 List<E_invoice_report> invoicelist;
			try {
	
				invoicelist = einvoiceservice.getE_invoiceReportData(div_id,stdate,end_dt);
				
				if(invoicelist.size()!=0) {
					String fileName = einvoiceservice.genarateExcel(invoicelist);
					System.err.println(fileName);
					map.put("fileName",fileName);
				}
				
						
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
			return map;
	}
	
	
}
