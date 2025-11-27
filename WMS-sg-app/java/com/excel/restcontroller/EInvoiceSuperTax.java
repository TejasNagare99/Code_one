package com.excel.restcontroller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excel.bean.EInvoiceByIrnDataSuperTax;
import com.excel.bean.EInvoiceByIrnDataSuperTaxWrapper;
import com.excel.bean.EInvoiceByIrnResponseSuperTax;
import com.excel.bean.EInvoiceCancelDataSuperTax;
import com.excel.bean.EInvoiceCancelDataWrapperSuperTax;
import com.excel.bean.EInvoiceResponseSuperTax;
import com.excel.bean.EInvoiceResponseSuperTaxWrapper;
import com.excel.bean.EInvoiceTransactionSuperTax;
import com.excel.bean.EInvoiceUIBean;
import com.excel.bean.EInvoiceWayBillDwnldResponseSuprTax;
import com.excel.bean.EWayBillCancelDataSuperTax;
import com.excel.bean.EWayBillCancelDataSuperTax.EwayBillCancelDataInner;
import com.excel.bean.EwayBillCancelResponseSuperTax;
import com.excel.bean.EwayBillPrintDataSuperTax;
import com.excel.bean.IrnCancelResponseSuperTax;
import com.excel.model.Company;
import com.excel.model.Location;
import com.excel.service.EInvoiceService;
import com.excel.service.LocationService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/rest")
public class EInvoiceSuperTax implements MedicoConstants {

	@Autowired
	EInvoiceService einvoiceservice;
	@Autowired
	LocationService locationservice;
	
	// live old
//	private static final String HOST_FOR_EWB = "https://eway.supertax.in/api";
//	private static final String AUTH_TOKEN_EWB =
//	 "62a7e8e203d4dcfec83465c6bebfebf4d46eff075e37a60223a1f7ec899e988e";
//	private static final String HOST = "https://vetoquinol.supertax.in/api/integration";
//	private static final String AUTH_TOKEN_SUPER_TAX = "7PQKo5PvpsUfdLuE-8DOQlzSXVkE64ltGgL0YjZPyf7P7lOUIofjE3EHbek-9NLo";
	
	
	// test old//change these auth tokens in db
//	private static final String HOST = "https://vetoquinol.supertaxuat.in/api/integration";
//	private static final String AUTH_TOKEN_SUPER_TAX = "IoOYpKoXUs1BwJpH8ITktQjEivOgr-D8nxuP0LV5okmYcVVlor-NlpfebLlRLqrD";
//	private static final String HOST_FOR_EWB = "https://supertaxuat.in/api/integration";
//	private static final String AUTH_TOKEN_EWB = "IoOYpKoXUs1BwJpH8ITktQjEivOgr-D8nxuP0LV5okmYcVVlor-NlpfebLlRLqrD";

	
	
	private static final String HOST_FOR_EWB = "https://eway.supertax.in/api";
	 private static final String AUTH_TOKEN_EWB =
 "62a7e8e203d4dcfec83465c6bebfebf4d46eff075e37a60223a1f7ec899e988e";
	private static final String HOST = "https://vetoquinol.einvoice.supertax.in/api/integration";
	private static final String AUTH_TOKEN_SUPER_TAX = "7PQKo5PvpsUfdLuE-8DOQlzSXVkE64ltGgL0YjZPyf7P7lOUIofjE3EHbek-9NLo";
	
	
	

	@PostMapping("/gen-supertax-EInvoice")
	public Map<String, Object> generateEInvoiceSuprTax(@RequestBody EInvoiceUIBean bean, HttpSession session)
			throws JSONException {
		JSONObject jb = new JSONObject();
		String host = null;
		String msg = null;
		boolean isError = false;
		String finYearFlag;
		Map<String, Object> map = new HashMap<String, Object>();
		boolean multiple = false;
		try {
			System.out.println("currfinyrId : " + bean.getCurrentyear());
			System.out.println("FinyrId : " + bean.getFinyearid());
			System.out.println("Ewaybill : " + bean.getGenerate_eway());
			System.out.println("divId : " + bean.getDivisionid());
			System.out.println("locId : " + bean.getLoc_id());
			System.out.println("fromchln : " + bean.getFromchallan());
			System.out.println("toChln : " + bean.getTochallan());

			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			// String url=host+"/einv/v2/eInvoice/generate";
			// String url=host+"/v2/eInvoice/generate";

			String url = host + "/einvoices/v1.01/sales/save";

			System.out.println("URL : " + url);
			isError = false;
			if (bean.getFinyearid().equals(bean.getCurrentyear())) {
				finYearFlag = MedicoConstants.CURRENT;
			} else {
				finYearFlag = MedicoConstants.PREVIOUS;
			}
			bean.setFinyearflag(finYearFlag);

			EInvoiceTransactionSuperTax parameters = einvoiceservice.generateEInvoiceSuperTax(finYearFlag,
					bean.getFinyearid(), bean.getGenerate_eway(), bean.getDivisionid(), bean.getFromchallan(),
					bean.getTochallan());
			if (parameters != null &&  parameters.getInvoices() != null && parameters.getInvoices().size() > 0) {
				Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));// new
																									// Location_dao().getLocationById(loc_id);
				if (location.getAuth_token() != null) {
					String authToken = AUTH_TOKEN_SUPER_TAX;// =location.getAuth_token().trim();
					String ownerId = location.getOwner_id();// =location.getOwner_id().trim();
					String gstNo = location.getGst_reg_no().trim();// =location.getGst_reg_no().trim();

					// Gson gson = new
					// GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					String jsonResponse = einvoiceservice.generateEinvoiceSuperTax(url,
							new Gson().toJson(parameters).toString(), gstNo, ownerId, authToken, "POST");
					System.out.println("response:::::::::::" + jsonResponse);
					EInvoiceResponseSuperTaxWrapper response = new Gson().fromJson(jsonResponse,
							new TypeToken<EInvoiceResponseSuperTaxWrapper>() {
							}.getType());
					einvoiceservice.saveEInvoiceResponseSuperTax(response, comp_cd, bean.getDivisionid(),
							bean.getFinyearid(), finYearFlag);
					if (parameters.getInvoices().size() == 1) {
						EInvoiceResponseSuperTax resp = response.getInvoices().get(0);
						if (resp.getSuccess() ||
								(resp.getSuccess() == false
								&& resp.getMessages() != null && resp.getMessages().equalsIgnoreCase("Duplicate IRN")
								&& resp.getStatus()!=null && resp.getStatus().equalsIgnoreCase("ERROR"))) {
							msg = "EInvoice Generated Successfully";
						} else {
							msg = "EInvoice Failed";
						}
					} else {
						Map<String, String> msg_map = new HashMap<String, String>();
						response.getInvoices().forEach((res) -> {
							if (res.getSuccess() ||
									(res.getSuccess() == false
									&& res.getMessages() != null && res.getMessages().equalsIgnoreCase("Duplicate IRN")
									&& res.getStatus()!=null && res.getStatus().equalsIgnoreCase("ERROR")))
								msg_map.put(res.getDocDtls().getNo(), "EInvoice Generated Successfully");
							else
								msg_map.put(res.getDocDtls().getNo(), "EInvoice Failed");
						});
						multiple = true;
						map.put("msg_map", msg_map);
					}
				} else {
					msg = "EInvoice Details Not found";
				}
			} else {
				msg = "Invoice Not found";
			}
			map.put("multiple", multiple);
			map.put("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error Occurred";
			isError = true;
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		}
		return map;

	}

	@PostMapping("/generate-supertax-EWayBillByIrn") // With EWAYBILL
	public Map<String, Object> generateEInvoiceByIrnSuprTax(@RequestBody EInvoiceUIBean bean, HttpSession session)
			throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		JSONObject jb = new JSONObject();
		boolean isError = false;
		String finYearFlag = "";
		boolean multiple = false;
		try {
			System.out.println("currfinyrId : " + bean.getCurrentyear());
			System.out.println("FinyrId : " + bean.getFinyearid());
			System.out.println("Ewaybill : " + bean.getGenerate_eway());
			System.out.println("divId : " + bean.getDivisionid());
			System.out.println("locId : " + bean.getLoc_id());
			System.out.println("fromchln : " + bean.getFromchallan());
			System.out.println("toChln : " + bean.getTochallan());
			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			// String url=host+"/einv/v2/eInvoice/ewaybill";
			// String url=host+"/v2/eInvoice/ewaybill";
			String url = HOST_FOR_EWB + "/ewaybill/v1.02/save";
			System.out.println("URL : " + url);
			isError = false;
			if (bean.getFinyearid().equals(bean.getCurrentyear())) {
				finYearFlag = "CURRENT";
			} else {
				finYearFlag = "PRIVIOUS";
			}

			EInvoiceByIrnDataSuperTaxWrapper superTaxJson = new EInvoiceByIrnDataSuperTaxWrapper();
			String authToken = AUTH_TOKEN_EWB;
//			List<EInvoiceByIrnData> parameters = einvoiceservice.generateEInvoiceByIrn(finYearFlag, bean.getFinyearid(),
//					bean.getGenerate_eway(), bean.getDivisionid(), bean.getFromchallan(), bean.getTochallan());// new
			List<EInvoiceByIrnDataSuperTax> parameters = einvoiceservice.generateEWayBillData(finYearFlag,
					bean.getFinyearid(), bean.getGenerate_eway(), bean.getDivisionid(), bean.getFromchallan(),
					bean.getTochallan());
			if (parameters != null && parameters.size() > 0) {

				superTaxJson.setEwaybills(parameters);
				Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));// new
																									// Location_dao().getLocationById(loc_id);
				if (location.getAuth_token() != null) {
					// String authToken = location.getAuth_token().trim();
					String ownerId = location.getOwner_id().trim();
					String gstNo = location.getGst_reg_no().trim();

					String jsonResponse = einvoiceservice.generateEwaybillSuperTax(url,
							new Gson().toJson(superTaxJson).toString(), gstNo, ownerId, authToken, "POST");// new
																											// EInvoiceGeneration().generateEinvoice(url,new
																											// Gson().toJson(parameters).toString(),gstNo,ownerId,authToken,"POST");
					System.out.println("jsonResponse:::::::::::" + jsonResponse);
					EInvoiceByIrnResponseSuperTax response = new Gson().fromJson(jsonResponse,
							new TypeToken<EInvoiceByIrnResponseSuperTax>() {
							}.getType());
					einvoiceservice.saveEWayBillResponseIrn(response, comp_cd, bean.getDivisionid());
					if (parameters.size() == 1) {
						if (response.getEwayBill().get(0).getSuccess()) {
							msg = "E-WayBill Generated Successfully";
						} else {
							msg = "E-WayBill Failed";
						}
					} else {
						Map<String, String> msg_map = new HashMap<String, String>();
						response.getEwayBill().forEach((res) -> {
							if (res.getSuccess())
								msg_map.put(res.getDocDtls().getNo(), "E-WayBill Generated Successfully");
							else
								msg_map.put(res.getDocDtls().getNo(), "E-WayBill Failed");
						});
						multiple = true;
						map.put("msg_map", msg_map);
					}
				} else {
					msg = "E-Way Bill Details Not found";
				}
			} else {
				msg = "Invoice Not found";
			}
			map.put("multiple", multiple);
			map.put("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error Occurred";
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		} finally {
		}
		return map;
	}

	@PostMapping("/cancel-supertax-EInvoive")
	public Map<String, Object> cancelEInvoiveSuprTax(@RequestBody EInvoiceUIBean bean, HttpSession session)
			throws JSONException {

		boolean isError = false;

		String finYearFlag = null;
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		boolean multiple = false;
		try {
			isError = false;
			System.out.println("currfinyrId : " + bean.getCurrentyear());
			System.out.println("FinyrId : " + bean.getFinyearid());
			System.out.println("Ewaybill : " + bean.getGenerate_eway());
			System.out.println("divId : " + bean.getDivisionid());
			System.out.println("locId : " + bean.getLoc_id());
			System.out.println("fromchln : " + bean.getFromchallan());
			System.out.println("toChln : " + bean.getTochallan());

			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			// String url=host+"/einv/v2/eInvoice/cancel";
			// String url=host+"/v2/eInvoice/cancel";
			String url = host + "/einvoices/v1.01/sales/cancel";
			System.out.println("URL : " + url);

			if (bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
				finYearFlag = MedicoConstants.CURRENT;
			} else {
				finYearFlag = MedicoConstants.PREVIOUS;
			}

			List<Object> list = einvoiceservice.getIrnCancelData(bean.getFromchallan(), bean.getTochallan(),
					bean.getDivisionid(), finYearFlag);// new E_InvoiceDao().getIrnCancelData(fromChallan,
														// tochallan,divisionid);
			List<String> demo = new ArrayList<String>();
			System.out.println(list.size());
			for (Object objArr : list) {
				Object object[] = (Object[]) objArr;
				demo.add(object[0].toString());

			}
			// hsession = HibernateUtil.getSessionFactory().openSession();
			// tr = hsession.beginTransaction();
			Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));// new
																								// Location_dao().getLocationById(loc_id);
			String authToken = location.getAuth_token().trim();
			String ownerId = location.getOwner_id().trim();
			String gstNo = location.getGst_reg_no().trim();
			List<EInvoiceCancelDataSuperTax> irnListData = einvoiceservice.setEInvoiceCancelDataSuperTax(demo);
			EInvoiceCancelDataWrapperSuperTax parameters = new EInvoiceCancelDataWrapperSuperTax();
			parameters.setInvoices(irnListData);

			String jsonResponse = einvoiceservice.generateEinvoiceSuperTax(url,
					new Gson().toJson(parameters).toString(), gstNo, ownerId, authToken, "POST");
			IrnCancelResponseSuperTax response = new Gson().fromJson(jsonResponse,
					new TypeToken<IrnCancelResponseSuperTax>() {
					}.getType());
			einvoiceservice.cancelIrnSuperTax(response, comp_cd);
			if (response != null && response.getInvoices() != null) {
				if (response.getInvoices().size() == 1) {
					if (response.getInvoices().get(0).getSuccess()) {
						msg = "EInvoice Cancelled Successfully";
					} else {
						msg = "Failed to Cancel";
					}
				} else {

					Map<String, String> msg_map = new HashMap<String, String>();
					response.getInvoices().forEach((res) -> {
						// get chln no by irn
						String chlnNo = "";
						for (Object objArr : list) {
							chlnNo = "";
							Object object[] = (Object[]) objArr;
							if (object[0].toString().equals(res.getIrn())) {
								chlnNo = object[1].toString();
							}
						}
						if (res.getSuccess())
							msg_map.put(chlnNo, "Success");
						else
							msg_map.put(chlnNo, "Transaction failed");
					});
					multiple = true;
					map.put("msg_map", msg_map);
				}
			} else {
				msg = "Failed to Cancel";
			}
			// tr.commit();
			map.put("multiple", multiple);
			map.put("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			isError = true;
			msg = "EInvoice Cancellation Failed";
			map.put("msg", msg);
			// tr.rollback();
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		} finally {
			// hsession.close();
		}
		return map;
	}

	@PostMapping("/cancel-supertax-EWayBill")
	public Map<String, Object> cancelEWayBillSuprTax(@RequestBody EInvoiceUIBean bean, HttpSession session)
			throws JSONException {

		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";

		try {
			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();

			// String url = "https://eway.supertax.in/api/invoices/cancelewaybill";
			String url = HOST_FOR_EWB + "/invoices/cancelewaybill";
			System.out.println("url :" + url);

			Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));
			String authToken = AUTH_TOKEN_EWB;
			String ownerId = location.getOwner_id().trim();
			String gstNo = location.getGst_reg_no().trim();

			EWayBillCancelDataSuperTax parameters = new EWayBillCancelDataSuperTax();
			EwayBillCancelDataInner inner = parameters.new EwayBillCancelDataInner();
			inner.setEwaybill_cancel_reason("3");
			inner.setEwaybill_cancel_remark("Data Entry mistake");
			inner.setEwaybill_no(bean.getFromchallan());
			parameters.setEwaybills(new ArrayList<EwayBillCancelDataInner>());
			parameters.getEwaybills().add(inner);

			String jsonResponse = einvoiceservice.cancelEwayBillSuperTax(url, new Gson().toJson(parameters).toString(),
					gstNo, ownerId, authToken);
			System.out.println("jsonResponse " + jsonResponse);
			List<EwayBillCancelResponseSuperTax> response = new Gson().fromJson(jsonResponse,
					new TypeToken<List<EwayBillCancelResponseSuperTax>>() {
					}.getType());

			if (response != null && response.get(0) != null) {
				if (!response.get(0).isStatus()) {

					msg = "Failed to cancel this E-WayBill";
				} else {
					einvoiceservice.EwayBillCancelSuperTax(response.get(0), comp_cd);
					msg = "Transaction Successful";
				}
			} else {
				msg = "Failed to cancel this E-WayBill";
			}

			System.out.println("MSG " + msg);
			map.put("msg", msg);
			// tr.commit();

		} catch (Exception e) {
			// tr.rollback();
			e.printStackTrace();
			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		} finally {
			// hsession.close();
		}
		return map;
	}

	@PostMapping("/download-supertax-EwayBill")
	public Map<String, Object> downloadEwayBillSuprTax(@RequestBody EInvoiceUIBean bean, HttpSession session) {

		boolean isError = false;

		String fileName = null;
		String finYearFlag = null;
		String response = "";
		String saveFilePath = "";

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String comp_cd = (String) session.getServletContext().getAttribute(COMPANY_CODE);
			String host = ((Company) session.getServletContext().getAttribute(COMPANY_DATA)).getEinvoice_host();
			String loc_id = (String) session.getAttribute("EMP_LOC");
			String url = HOST_FOR_EWB + "/invoices/getewaybillpdf";
			// String url = "https://eway.supertax.in/api/invoices/getewaybillpdf";
			System.out.println("url : " + url);
			if (bean.getFinyearid().trim().equals(bean.getCurrentyear())) {
				finYearFlag = MedicoConstants.CURRENT;
			} else {
				finYearFlag = MedicoConstants.PREVIOUS;
			}

			List<Object> list = einvoiceservice.getEwayBillDownloadNumbers(bean.getFinyearid(), bean.getDivisionid(),
					bean.getFromchallan(), bean.getFromchallan(), finYearFlag);
			List<String> ewbNumbers = new ArrayList<String>();
			System.out.println(list.size());
			List<EwayBillPrintDataSuperTax> parameters = new ArrayList<EwayBillPrintDataSuperTax>();
			EwayBillPrintDataSuperTax ewayBillobj = null;
			for (Object objArr : list) {
				Object object[] = (Object[]) objArr;
				ewayBillobj = new EwayBillPrintDataSuperTax();
				ewayBillobj.setEwaybill_no(object[0].toString());

				parameters.add(ewayBillobj);
			}
			String fpath = MedicoConstants.PDF_FILE_PATH + "files//";

			Location location = locationservice.getObjectById(Long.valueOf(bean.getLoc_id()));

			String authToken = AUTH_TOKEN_EWB;
			String ownerId = location.getOwner_id().trim();
			String gstNo = location.getGst_reg_no().trim();

			response = einvoiceservice.downloadFileForSuperTax(fpath, url,
					new Gson().toJson(parameters.get(0)).toString(), gstNo, ownerId, authToken, ewbNumbers.size());

			if (response != null) {
				EInvoiceWayBillDwnldResponseSuprTax wayBillPdf = new Gson().fromJson(response.toString(),
						new TypeToken<EInvoiceWayBillDwnldResponseSuprTax>() {
						}.getType());

				if (wayBillPdf.getStatus()) {
					String file_url = wayBillPdf.getFilename();
					file_url = file_url.replace("\\/", "/");
					System.out.println(file_url);
					URL file = new URL(file_url);
					fileName = "EwayBill" + new Date().getTime() + ".pdf";
					saveFilePath = fpath + File.separator + fileName;
					File fileObj = new File(saveFilePath);
					InputStream webIS = file.openStream();
					FileOutputStream fo = new FileOutputStream(fileObj);
					int c = 0;
					do {
						c = webIS.read();
						System.out.println("==============> " + c);
						if (c != -1) {
							fo.write((byte) c);
						}
					} while (c != -1);

					webIS.close();
					fo.close();

					System.out.println("file " + fileName);
					System.out.println("Downloaded");
					map.put("filename", fileName);
					map.put("isData", true);
				} else {
					map.put("isData", false);
				}
			} else {
				map.put("isData", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			isError = true;
			map.put("isData", false);

			System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(e));// uncomment asneeded --;
		} finally {

		}
		return map;

	}

}
