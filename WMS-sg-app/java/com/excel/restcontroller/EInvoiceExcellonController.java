package com.excel.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.EInvoiceByIrnData;
import com.excel.bean.EInvoiceCancelData;
import com.excel.bean.EInvoiceData;
import com.excel.bean.EInvoiceTransaction;
import com.excel.bean.EInvoiceUIBean;
import com.excel.bean.PrintBean;
import com.excel.configuration.HttpSessionInterceptor;
import com.excel.model.Company;
import com.excel.model.Dispatch_Header;
import com.excel.model.Location;
import com.excel.model.Sum_Disp_Header;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoice;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoiceStockist;
import com.excel.repository.DispatchRepository;
import com.excel.service.EInvoiceService;
import com.excel.service.ExcellonEInvoicePrintingService;
import com.excel.service.ExcellonEInvoiceService;
import com.excel.service.LocationService;
import com.excel.service.PrintService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.google.gson.Gson;

@RestController
@RequestMapping("/rest/excellon")
public class EInvoiceExcellonController implements MedicoConstants {

	@Autowired
	private ExcellonEInvoiceService excellonEInvService;
	@Autowired
	private ExcellonEInvoicePrintingService excellonEInvPrintService;
	@Autowired
	EInvoiceService einvoiceservice;
	@Autowired
	LocationService locationservice;
	@Autowired
	PrintService printService;
	@Autowired
	private DispatchRepository disp_repo;

	private static final Logger logger = LogManager.getLogger(EInvoiceExcellonController.class);

	// ---------------------------------constants |
	// URIs-------------------------------------------//
	private static final String GET_IRN = "/eicore/v1.03/Invoice";
	private static final String CANCEL_IRN = "/eicore/v1.03/Invoice/Cancel";
	private static final String GET_EWAYBILL_BY_IRN = "/eiewb/v1.03/ewaybill";
	private static final String CANCEL_EWAYBILL = "/ewaybillapi/v1.03/ewayapi/CancelEWB";

//	private static final String GET_IRN_DETAILS_BY_DOC_DTLS = "/eicore/v1.03/Invoice/irnbydocdetails";
//	private static final String GET_EWB_BY_IRN= "/eiewb/v1.03/ewaybill/";

	private static final String HOST_FOR_EWB = "https://demoapiewb.exactgst.com";
	private static final String GET_EWB_DETAILS_FOR_PRINT = "/ewaybillapi/v1.03/ewayapi/GetEwayBill";

	// UTIL CLASS FOR ERRORS
	private static class ErrorsMapForEInvoice {
		private String invNo;
		private String statusMessage;
		private boolean status;

		public String getInvNo() {
			return invNo;
		}

		public void setInvNo(String invNo) {
			this.invNo = invNo;
		}

		public String getStatusMessage() {
			return statusMessage;
		}

		public void setStatusMessage(String statusMessage) {
			this.statusMessage = statusMessage;
		}

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}
	}

	// -------------------------------------------APIs---------------------------------------------//

	// CALL THE AUTHORIZE AND AUTHENTICATE WHEN FIRST LOGGING IN TO THE
	// EINVOICE MENU OPTIONS FROM FRONT END IN CASE OF EXCELLON

	@GetMapping("/auth")
	public Map<String, Object> getAuthDetailsForEInvoice(@RequestParam Long locId,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.info("Einv auth API called by location: {}", locId);
			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			// TODO get excellon_username,excellon_password,gstin from location
			Location location = locationservice.getObjectById(locId);
			String gstNo = location.getGst_reg_no().trim();
			String username = location.getEinv_uname().trim();
			String password = location.getEinv_password().trim();
			Optional<String> excellon_auth_token = ExcellonEInvoiceService.authenticate_excellon_user(host);
			
			
			if (excellon_auth_token.isPresent()) {
				map = ExcellonEInvoiceService.gen_auth_token_gst_user(host,excellon_auth_token.get(), gstNo, username,
						password);
			} else {
				throw new Exception("Failed to authenticate.Please contact admin");
			}

			logger.info("Einv auth API success for location: {}", locId);

		} catch (Exception e) {
			map.put("DATA_SAVED", false);
			e.printStackTrace();
			logger.info("Error Occured in einvoice auth API : {}", e.getMessage());
		}
		return map;
	}

	@GetMapping("/auth-ewb")
	public Map<String, Object> getAuthDetailsForEWayBill(@RequestParam Long locId) {
		Map<String, Object> map = null;
		try {
			logger.info("EWB auth API called by location: {}", locId);
			// TODO get excellon_username,excellon_password,gstin from location
			Location location = locationservice.getObjectById(locId);
			String gstNo = location.getGst_reg_no().trim();
			String username = location.getEinv_uname().trim();
			String password = location.getEinv_password().trim();
			Optional<String> excellon_auth_token = ExcellonEInvoiceService.authenticate_excellon_userForEwayBill();
			if (excellon_auth_token.isPresent()) {
				map = ExcellonEInvoiceService.gen_auth_token_gst_user_for_ewb(excellon_auth_token.get(), gstNo,
						username, password);
			} else {
				throw new Exception("Failed to authenticate.Please contact admin");
			}
			logger.info("EWB auth API success for location: {}", locId);

		} catch (Exception e) {
			if (map == null)
				map = new HashMap<String, Object>();
			map.put("DATA_SAVED", false);
			e.printStackTrace();
			logger.info("Error Occured in EWB auth API : {}", e.getMessage());
		}
		return map;
	}

	// GENERATE EINVOICE -----------------------

//	@PostMapping("/genEInvoice")
//	public Map<String, Object> generateEInvoice(@RequestBody EInvoiceUIBean bean, HttpSession session)
//			throws JSONException {
//		JSONObject jb = new JSONObject();
//		String host = null;
//		String msg = null;
//		boolean isError = false;
//		String finYearFlag;
//		Map<String, Object> map = new HashMap<String, Object>();
//		ErrorsMapForEInvoice[] statusMap = null;
//		Map<String, String> msg_map = null;
//		try {
//			msg_map = new HashMap<String, String>();
//
//			logger.info("currfinyrId : " + bean.getCurrentyear());
//			logger.info("FinyrId : " + bean.getFinyearid());
//			logger.info("Ewaybill : " + bean.getGenerate_eway());
//			logger.info("divId : " + bean.getDivisionid());
//			logger.info("locId : " + bean.getLoc_id());
//			logger.info("fromchln : " + bean.getFromchallan());
//			logger.info("toChln : " + bean.getTochallan());
//
//			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
//			host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
//			String url = host + GET_IRN;
//
//			logger.info("URL : " + url);
//			isError = false;
//			if (bean.getFinyearid().equals(bean.getCurrentyear())) {
//				finYearFlag = MedicoConstants.CURRENT;
//			} else {
//				finYearFlag = MedicoConstants.PREVIOUS;
//			}
//			bean.setFinyearflag(finYearFlag);
//
//			List<EInvoiceTransaction> parameters = einvoiceservice.generateEInvoice(finYearFlag, bean.getFinyearid(),
//					bean.getGenerate_eway(), bean.getDivisionid(), bean.getFromchallan(), bean.getTochallan());
//			if (parameters != null && parameters.size() > 0) {
//				Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
//				String gstNo = location.getGst_reg_no().trim();
//				String username = location.getEinv_uname().trim();
//
//				String getEwayBill = bean.getGenerate_eway();
//				for (int i = 0; i < parameters.size(); i++) {
//					try {
//						String IRN = excellonEInvService.generateEInvoiceExcellon(url, gstNo, username,
//								bean.getExcellon_auth_token(), bean.getGst_user_auth_token(), bean.getDecryptedSEK(),
//								parameters.get(i).getTransaction(), comp_cd, bean.getDivisionid(), bean.getFinyearid(),
//								finYearFlag, getEwayBill);
//						msg_map.put(parameters.get(i).getTransaction().getDocDtls().getNo(), "IRN No:" + IRN);
//
//					} catch (Exception e) {
//						e.printStackTrace();
//						msg_map.put(parameters.get(i).getTransaction().getDocDtls().getNo(),
//								"Failed to generate IRN . Please try again");
//					}
//				}
//				map.put("msg_map", msg_map);
//				map.put("multiple", true);
//			} else {
//				msg = "Invoice Not found";
//			}
//			map.put("msg", msg);
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg = "Error Occurred";
//			isError = true;
//			map.put("msg", msg);
//			logger.info("Error Occurred :" + e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//
//	}
//
//	/// GENERATE EWAYBILL BY IRN ----------------------
//
//	@PostMapping("/generateEWayBillByIrn")
//	public Map<String, Object> generateEWayBillByIrn(@RequestBody EInvoiceUIBean bean, HttpSession session)
//			throws JSONException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		String msg = "";
//		String finYearFlag = "";
//		try {
//			logger.info("currfinyrId : " + bean.getCurrentyear());
//			logger.info("FinyrId : " + bean.getFinyearid());
//			logger.info("Ewaybill : " + bean.getGenerate_eway());
//			logger.info("divId : " + bean.getDivisionid());
//			logger.info("locId : " + bean.getLoc_id());
//			logger.info("fromchln : " + bean.getFromchallan());
//			logger.info("toChln : " + bean.getTochallan());
//
//			Map<String, String> msg_map = new HashMap<String, String>();
//
//			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
//
//			String url = host + GET_EWAYBILL_BY_IRN;
//
//			logger.info("URL : " + url);
//
//			if (bean.getFinyearid().equals(bean.getCurrentyear())) {
//				finYearFlag = "CURRENT";
//			} else {
//				finYearFlag = "PREVIOUS";
//			}
//
//			List<EInvoiceByIrnData> parameters = einvoiceservice.generateEInvoiceByIrn(finYearFlag, bean.getFinyearid(),
//					bean.getGenerate_eway(), bean.getDivisionid(), bean.getFromchallan(), bean.getTochallan());
//
//			if (parameters != null && parameters.size() > 0) {
//				Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
//				String gstNo = location.getGst_reg_no().trim();
//				String username = location.getEinv_uname().trim();
//
//				for (int i = 0; i < parameters.size(); i++) {
//					try {
//						String _ewb_Message = excellonEInvService.generateEWayBillByIrnExcellon(url, gstNo, username,
//								bean.getExcellon_auth_token(), bean.getGst_user_auth_token(), bean.getDecryptedSEK(),
//								parameters.get(i));
//						msg_map.put(parameters.get(i).getChallan_No(), "E-WayBill Generated No . : " + _ewb_Message);
//					} catch (Exception e) {
//						e.printStackTrace();
//						msg_map.put(parameters.get(i).getChallan_No(), "Failed to generate E-WayBill.");
//					}
//				}
//				map.put("msg_map", msg_map);
//				map.put("multiple", true);
//			} else {
//				msg = "Invoice Not found";
//			}
//
//			map.put("msg", msg);
//		} catch (Exception e) {
//			msg = "Error Occurred";
//			map.put("msg", msg);
//			logger.info("Error Occurred :" + e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}

	/// CANCEL IRN -------------------------

//	@PostMapping("/cancelEInvoive")
//	public Map<String, Object> cancelEInvoive(@RequestBody EInvoiceUIBean bean, HttpSession session)
//			throws JSONException {
//		boolean isError = false;
//		String finYearFlag = null;
//		String msg = "";
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			Map<String, String> msg_map = new HashMap<String, String>();
//			isError = false;
//			logger.info("currfinyrId : " + bean.getCurrentyear());
//			logger.info("FinyrId : " + bean.getFinyearid());
//			logger.info("Ewaybill : " + bean.getGenerate_eway());
//			logger.info("divId : " + bean.getDivisionid());
//			logger.info("locId : " + bean.getLoc_id());
//			logger.info("fromchln : " + bean.getFromchallan());
//			logger.info("toChln : " + bean.getTochallan());
//
//			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
//
//			String url = host + CANCEL_IRN;
//
//			logger.info("URL : " + url);
//
//			if (bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
//				finYearFlag = MedicoConstants.CURRENT;
//			} else {
//				finYearFlag = MedicoConstants.PREVIOUS;
//			}
//
//			List<Object> list = einvoiceservice.getIrnCancelData(bean.getFromchallan(), bean.getTochallan(),
//					bean.getDivisionid(), finYearFlag);
//			List<String> demo = new ArrayList<String>();
//			logger.info("irn cancel" + list.size());
//
//			for (Object objArr : list) {
//				Object object[] = (Object[]) objArr;
//				demo.add(object[0].toString());
//			}
//			List<EInvoiceCancelData> parameters = einvoiceservice.setEInvoiceCancelData(demo);
//
//			if (parameters != null && parameters.size() > 0) {
//				Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
//				String gstNo = location.getGst_reg_no().trim();
//				String username = location.getEinv_uname().trim();
//
//				for (int i = 0; i < parameters.size(); i++) {
//					Object object[] = (Object[]) list.get(i);
//					try {
//						String cancelIrnMessage = this.excellonEInvService.cancelIRN(url, gstNo, username,
//								bean.getExcellon_auth_token(), bean.getGst_user_auth_token(), bean.getDecryptedSEK(),
//								parameters.get(i));
//						msg_map.put(object[1].toString(), cancelIrnMessage);
//
//					} catch (Exception e) {
//						e.printStackTrace();
//						msg_map.put(object[1].toString(), "Failed to cancel IRN.");
//					}
//				}
//				map.put("msg_map", msg_map);
//				map.put("multiple", true);
//			} else {
//				msg = "Invoice Not found";
//			}
//			map.put("msg", msg);
//
//		} catch (Exception e) {
//			msg = "EInvoice Cancellation Failed";
//			map.put("msg", msg);
//			e.printStackTrace();
//			logger.info("Error Occurred :" + e.getMessage());// uncomment asneeded --;
//		}
//		return map;
//	}

	// CANCEL EWAYBILL ------

//	@PostMapping("/cancelEWayBill")
//	public Map<String, Object> cancelEWayBill(@RequestBody EInvoiceUIBean bean, HttpSession session)
//			throws JSONException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		String msg = "";
//		try {
//			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
//
//			String url = host + CANCEL_EWAYBILL;
//			logger.info("url :" + url);
//
//			Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
//			String gstNo = location.getGst_reg_no().trim();
//			String username = location.getEinv_uname().trim();
//
//			String response = excellonEInvService.cancelEWayBill(url, gstNo, username, bean.getExcellon_auth_token(),
//					bean.getGst_user_auth_token(), bean.getDecryptedSEK(), bean.getFromchallan());
//			logger.info("MSG " + response);
//			map.put("msg", response);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("Error Occurred :" + e.getMessage());// uncomment asneeded --;
//			map.put("msg", "Failed to cancel EWB");
//		}
//		return map;
//	}

	// GET-EWAYBILL-DETAILS-AND-PRINT
	@PostMapping("/downloadEwayBill")
	public Map<String, Object> downloadEwayBill(@RequestBody EInvoiceUIBean bean, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String finYearFlag = null;
		String filename = null;
		try {
			logger.info("from challan :: " + bean.getFromchallan());
			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();

			String url = HOST_FOR_EWB + GET_EWB_DETAILS_FOR_PRINT;
			logger.info("url :" + url);

			Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
			String gstNo = location.getGst_reg_no().trim();
			String username = location.getEinv_uname().trim();

			if (bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
				finYearFlag = MedicoConstants.CURRENT;
			} else {
				finYearFlag = MedicoConstants.PREVIOUS;
			}
			// get cum disp challan no by challan id
			Sum_Disp_Header sum_dsp_hdr = this.disp_repo.getObjectById(Long.valueOf(bean.getFromchallan()));

			PrintBean pb = new PrintBean();
			pb.setFinyearflg(finYearFlag);
			pb.setFinyearflag(bean.getFinyearid());
			// 24 may
			List<ViewPrePrintedSummaryChallan_GST_EInvoice> list1 = this.printService
					.PrePrintedSummaryChallan_GST_EInvoicedata(bean.getDivisionid(), bean.getLoc_id(),
							sum_dsp_hdr.getSumdsp_challan_no(), sum_dsp_hdr.getSumdsp_challan_no(), "", "D", pb);
			logger.info("list1 size:::::::" + list1.size());

			// filename = this.excellonEInvPrintService.getPdf(list1);
			logger.info("filename:::::::" + filename);
			// 24 may

//			String filename = this.excellonEInvService.PrePrintedSummaryChallanEInvoicePrint(
//					Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
//					dispatchType, prodtype, printtype, list1, show_amount, footer_signature_ind,
//					companyCode, companyName, session,request);

//			List<Object> list=einvoiceservice.getEwayBillDownloadNumbers(bean.getFinyearid(),bean.getDivisionid(),bean.getFromchallan(), bean.getFromchallan(),finYearFlag);
//			List<String> ewbNumbers=new ArrayList<String>();
//			logger.infolist.size());
//			for (Object objArr : list) {
//				Object object[]=(Object[])objArr;
//				ewbNumbers.add(object[0].toString());
//			}
//			
//			String response = this.excellonEInvService.getEwayBillDetailsForPrint(url, gstNo,
//					bean.getExcellon_auth_token(), bean.getGst_user_auth_token(), bean.getDecryptedSEK(),
//					ewbNumbers.get(0));

			map.put("isData", true);
			map.put("filename", filename);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put("isData", false);
			map.put("msg", "Failed to Create EWB Print.Please Contact Admin");
		}
		return map;
	}

	// ==========================stockist EINV
	// queries===============================

	@PostMapping("/genEInvoiceStk")
	public Map<String, Object> generateEInvoiceStockist(@RequestBody EInvoiceUIBean bean, HttpSession session)
			throws JSONException {
		
		JSONObject jb = new JSONObject();
		String host = null;
		String msg = null;
		boolean isError = false;
		String finYearFlag;
		Map<String, Object> map = new HashMap<String, Object>();
		ErrorsMapForEInvoice[] statusMap = null;
		// Map<String, String> msg_map = null;
		try {

			logger.info("EInv gen API called for location: {}", bean.getLoc_id());

			// msg_map = new HashMap<String, String>();

			logger.info("currfinyrId : " + bean.getCurrentyear());
			logger.info("FinyrId : " + bean.getFinyearid());
			logger.info("Ewaybill : " + bean.getGenerate_eway());
			logger.info("divId : " + bean.getDivisionid());
			logger.info("locId : " + bean.getLoc_id());
			logger.info("fromchln : " + bean.getFromchallan());
			logger.info("toChln : " + bean.getTochallan());

			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			String url = host + GET_IRN;
			logger.info("URL : " + url);
			isError = false;
			
			if (bean.getFinyearid().equals(bean.getCurrentyear())) {
				finYearFlag = MedicoConstants.CURRENT;
			} else {
				finYearFlag = MedicoConstants.PREVIOUS;
			}
			bean.setFinyearflag(finYearFlag);

			List<EInvoiceTransaction> parameters = einvoiceservice.generateEInvoiceStockist(finYearFlag,
					bean.getFinyearid(), bean.getGenerate_eway(), bean.getDivisionid(), bean.getFromchallan(),
					bean.getTochallan(), bean.getLoc_id());
			
			
			if (parameters != null && parameters.size() > 0) {
				//this message is only shown when all is successful
				msg = "E-Invoice Generated Successfully.";
				Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
				String gstNo = location.getGst_reg_no().trim();
				String username = location.getEinv_uname().trim();
				Map<String, String> msg_map = new HashMap<String, String>();
				String getEwayBill = bean.getGenerate_eway();
				long startTime = System.currentTimeMillis();
				if(parameters!=null && parameters.size() > 0) {
					for (int i = 0; i < parameters.size(); i++) {
						try {
							// old logic changed for issue of 
//							String IRN = excellonEInvService.getIrnDetailsByDocDtlsOrSaveNew(host,url, gstNo, username,
//									bean.getExcellon_auth_token(), bean.getGst_user_auth_token(), bean.getDecryptedSEK(),
//									parameters.get(i).getTransaction(), comp_cd, bean.getDivisionid(), bean.getFinyearid(),
//									finYearFlag, getEwayBill);
							
							
							String IRN = excellonEInvService.generateEInvoiceExcellonStockist(host,url, gstNo, username,
									bean.getExcellon_auth_token(), bean.getGst_user_auth_token(), bean.getDecryptedSEK(),
									parameters.get(i).getTransaction(), comp_cd, bean.getDivisionid(), bean.getFinyearid(),
									finYearFlag, getEwayBill);
							if(IRN != null) {
								map.put("multiple", true);
								msg_map.put(parameters.get(i).getTransaction().getDocDtls().getNo(),IRN);
							}
						} catch (Exception e) {
							e.printStackTrace();
							map.put("multiple", true);
							msg_map.put(parameters.get(i).getTransaction().getDocDtls().getNo(),
									"Failed to generate IRN . Please try again");
						}
					}
				}
				else {
					msg_map.put("Message","No Data Found . Please refresh the list and try again.");
				}
				
				long endTime = System.currentTimeMillis();
				long elapsedTime = endTime - startTime;
				System.out.println("Elapsed time in milliseconds: " + elapsedTime);
				map.put("msg_map", msg_map);
			} else {
				msg = "Invoice Not found";
			}
			map.put("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error Occurred";
			isError = true;
			map.put("msg", msg);
			logger.info("Error Occurred while trying EINV gen for loc: {} . Error is : {}", bean.getLoc_id(),
					e.getMessage());
		}
		return map;

	}

	@PostMapping("/generateEWBByIrnStockist")
	public Map<String, Object> generateEWayBillByIrnStockist(@RequestBody EInvoiceUIBean bean, HttpSession session)
			throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String finYearFlag = "";
		try {

			logger.info("EWB gen by IRN API called for chalan: {} by location : {}", bean.getFromchallan(),
					bean.getLoc_id());

			logger.info("currfinyrId : " + bean.getCurrentyear());
			logger.info("FinyrId : " + bean.getFinyearid());
			logger.info("Ewaybill : " + bean.getGenerate_eway());
			logger.info("divId : " + bean.getDivisionid());
			logger.info("locId : " + bean.getLoc_id());
			logger.info("fromchln : " + bean.getFromchallan());
			logger.info("toChln : " + bean.getTochallan());

			Map<String, String> msg_map = new HashMap<String, String>();

			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			String url = host + GET_EWAYBILL_BY_IRN;
			logger.info("URL : " + url);
			if (bean.getFinyearid().equals(bean.getCurrentyear())) {
				finYearFlag = "CURRENT";
			} else {
				finYearFlag = "PREVIOUS";
			}
			List<EInvoiceByIrnData> parameters = einvoiceservice.generateEInvoiceByIrnStockist(
					Long.valueOf(bean.getLoc_id()), finYearFlag, bean.getFinyearid(), bean.getGenerate_eway(),
					bean.getDivisionid(), bean.getFromchallan(), bean.getTochallan());

			if (parameters != null && parameters.size() > 0) {
				Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
				String gstNo = location.getGst_reg_no().trim();
				String username = location.getEinv_uname().trim();
				msg = "E-WayBill Generated Successfully."; 
				for (int i = 0; i < parameters.size(); i++) {
					try {
//						String _ewb_Message = excellonEInvService.updateExistingOrCreateNewEWBbyIrn(host,url, gstNo,
//								username, bean.getExcellon_auth_token(), bean.getGst_user_auth_token(),
//								bean.getDecryptedSEK(), parameters.get(i));
						///new logic
						String _ewb_Message = excellonEInvService.generateEWayBillByIrnStockistExcellon(host,url, gstNo,
								username, bean.getExcellon_auth_token(), bean.getGst_user_auth_token(),
								bean.getDecryptedSEK(), parameters.get(i));
						if(_ewb_Message!=null) {
							map.put("multiple", true);
							msg_map.put(parameters.get(i).getChallan_No(),_ewb_Message);
						}
					} catch (Exception e) {
						e.printStackTrace();
						map.put("multiple", true);
						msg_map.put(parameters.get(i).getChallan_No(), "Failed to generate E-WayBill.");
					}
				}
				map.put("msg_map", msg_map);
				//map.put("msg", msg);
				//map.put("multiple", true);
			} else {
				msg = "Invoice Not found";
			}
			map.put("msg", msg);
		} catch (Exception e) {
			msg = "Error Occurred";
			map.put("msg", msg);
			logger.info("Error Occurred while trying EWB gen for chln: {} . Error is : {}", bean.getFromchallan(),
					e.getMessage());
		}
		return map;
	}

	@PostMapping("/cancelEINVStockist")
	public Map<String, Object> cancelEInvoiceStockist(@RequestBody EInvoiceUIBean bean, HttpSession session)
			throws JSONException {
		boolean isError = false;
		String finYearFlag = null;
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.info("EINV cncl API called for chalan: {} by location : {}", bean.getFromchallan(),
					bean.getLoc_id());

			Map<String, String> msg_map = new HashMap<String, String>();
			isError = false;
			logger.info("currfinyrId : " + bean.getCurrentyear());
			logger.info("FinyrId : " + bean.getFinyearid());
			logger.info("Ewaybill : " + bean.getGenerate_eway());
			logger.info("divId : " + bean.getDivisionid());
			logger.info("locId : " + bean.getLoc_id());
			logger.info("fromchln : " + bean.getFromchallan());
			logger.info("toChln : " + bean.getTochallan());

			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			String url = host + CANCEL_IRN;

			logger.info("URL : " + url);

			if (bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
				finYearFlag = MedicoConstants.CURRENT;
			} else {
				finYearFlag = MedicoConstants.PREVIOUS;
			}

			List<Object> list = einvoiceservice.getIrnCancelDataStockist(Long.valueOf(bean.getLoc_id()),
					bean.getFromchallan(), bean.getTochallan(), bean.getDivisionid(), finYearFlag, bean.getFinyearid());
			List<String> demo = new ArrayList<String>();
			logger.info("IRN cancel" + list.size());

			for (Object objArr : list) {
				Object object[] = (Object[]) objArr;
				demo.add(object[0].toString());
			}
			List<EInvoiceCancelData> parameters = einvoiceservice.setEInvoiceCancelData(demo);

			if (parameters != null && parameters.size() > 0) {
				Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
				String gstNo = location.getGst_reg_no().trim();
				String username = location.getEinv_uname().trim();

				for (int i = 0; i < parameters.size(); i++) {
					Object object[] = (Object[]) list.get(i);
					try {
						String cancelIrnMessage = this.excellonEInvService.cancelIRNStockist(url, gstNo, username,
								bean.getExcellon_auth_token(), bean.getGst_user_auth_token(), bean.getDecryptedSEK(),
								parameters.get(i));
						msg_map.put(object[1].toString(), cancelIrnMessage);

					} catch (Exception e) {
						e.printStackTrace();
						msg_map.put(object[1].toString(), "Failed to cancel IRN.");
					}
				}
				map.put("msg_map", msg_map);
				map.put("multiple", true);
			} else {
				msg = "Invoice Not found";
			}
			map.put("msg", msg);

		} catch (Exception e) {
			msg = "EInvoice Cancellation Failed";
			map.put("msg", msg);
			e.printStackTrace();
			logger.info("Error Occurred while trying EINV cncl gen for chln: {} . Error is : {}", bean.getFromchallan(),
					e.getMessage());
		}
		return map;
	}

	@PostMapping("/cancelEWBStockist")
	public Map<String, Object> cancelEWayBillStockist(@RequestBody EInvoiceUIBean bean, HttpSession session)
			throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		try {
			logger.info("EWB cncl API called for chalan: {} by location : {}", bean.getFromchallan(), bean.getLoc_id());

			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			String url = host + CANCEL_EWAYBILL;
			logger.info("url :" + url);

			Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
			String gstNo = location.getGst_reg_no().trim();
			String username = location.getEinv_uname().trim();

			String response = excellonEInvService.cancelEWBStockist(url, gstNo, username, bean.getExcellon_auth_token(),
					bean.getGst_user_auth_token(), bean.getDecryptedSEK(), bean.getFromchallan());
			logger.info("MSG " + response);
			map.put("msg", response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error Occurred while trying EINV cncl gen for chln: {} . Error is : {}", bean.getFromchallan(),
					e.getMessage());
			map.put("msg", "Failed to cancel EWB");
		}
		return map;
	}

	@PostMapping("/downloadEwayBillStockist")
	public Map<String, Object> downloadEwayBillStockist(@RequestBody EInvoiceUIBean bean, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		String finYearFlag = null;
		String filename = null;
		try {
			logger.info("from challan :: " + bean.getFromchallan());
			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();

			String url = HOST_FOR_EWB + GET_EWB_DETAILS_FOR_PRINT;
			logger.info("url :" + url);

			Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
			String gstNo = location.getGst_reg_no().trim();
			String username = location.getEinv_uname().trim();

			if (bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
				finYearFlag = MedicoConstants.CURRENT;
			} else {
				finYearFlag = MedicoConstants.PREVIOUS;
			}
			// get cum disp challan no by challan id
			Dispatch_Header dsp_hdr = this.disp_repo.getdspHeaderById(Long.valueOf(bean.getFromchallan()));

			PrintBean pb = new PrintBean();
			pb.setFinyearflg(finYearFlag);
			pb.setFinyearflag(bean.getFinyearid());
			// 24 may
			List<ViewPrePrintedSummaryChallan_GST_EInvoiceStockist> list1 = this.printService
					.PrePrintedSummaryChallan_GST_EInvoicedataStockist(bean.getDivisionid(), bean.getLoc_id(),
							dsp_hdr.getDspChallanNo(), dsp_hdr.getDspChallanNo(), "", "D", pb);
			logger.info("list1 for ewaybill print for stockist api :::::::" + list1.size());

			filename = this.excellonEInvPrintService.getPdf(list1);
			logger.info("filename:::::::" + filename);
			// 24 may

//			String filename = this.excellonEInvService.PrePrintedSummaryChallanEInvoicePrint(
//					Integer.parseInt(division), Integer.parseInt(loc), frm_challan, to_challan,
//					dispatchType, prodtype, printtype, list1, show_amount, footer_signature_ind,
//					companyCode, companyName, session,request);

//			List<Object> list=einvoiceservice.getEwayBillDownloadNumbers(bean.getFinyearid(),bean.getDivisionid(),bean.getFromchallan(), bean.getFromchallan(),finYearFlag);
//			List<String> ewbNumbers=new ArrayList<String>();
//			logger.infolist.size());
//			for (Object objArr : list) {
//				Object object[]=(Object[])objArr;
//				ewbNumbers.add(object[0].toString());
//			}
//			
//			String response = this.excellonEInvService.getEwayBillDetailsForPrint(url, gstNo,
//					bean.getExcellon_auth_token(), bean.getGst_user_auth_token(), bean.getDecryptedSEK(),
//					ewbNumbers.get(0));

			map.put("isData", true);
			map.put("filename", filename);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			map.put("isData", false);
			map.put("msg", "Failed to Create EWB Print.Please Contact Admin");
		}
		return map;
	}

}
