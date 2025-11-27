package com.excel.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.EInvoiceByIrnData;
import com.excel.bean.EInvoiceByIrnData.ExpShipDtls;
import com.excel.bean.EInvoiceByIrnDataSuperTax;
import com.excel.bean.EInvoiceByIrnResponse;
import com.excel.bean.EInvoiceByIrnResponseSuperTax;
import com.excel.bean.EInvoiceByIrnResponseSuperTax.EInoviceRespSuperTax;
import com.excel.bean.EInvoiceCancelData;
import com.excel.bean.EInvoiceCancelDataSuperTax;
import com.excel.bean.EInvoiceData;
import com.excel.bean.EInvoiceDataSuperTax;
import com.excel.bean.EInvoiceResponse;
import com.excel.bean.EInvoiceResponseSuperTax;
import com.excel.bean.EInvoiceResponseSuperTaxWrapper;
import com.excel.bean.EInvoiceTransaction;
import com.excel.bean.EInvoiceTransactionSuperTax;
import com.excel.bean.EwayBillCancelData;
import com.excel.bean.EwayBillCancelResponse;
import com.excel.bean.EwayBillCancelResponseSuperTax;
import com.excel.bean.EwayBillPrintData;
import com.excel.bean.IrnCancelResponse;
import com.excel.bean.IrnCancelResponseSuperTax;
import com.excel.bean.IrnCancelResponseSuperTax.IrnCancelDataSuperTax;
import com.excel.model.Dispatch_Header;
import com.excel.model.DivMaster;
import com.excel.model.EInvoiceGenerateData;
import com.excel.model.EInvoiceHeader;
import com.excel.model.EInvoiceHeaderStockist;
import com.excel.model.EInvoiceHeader_Arc;
import com.excel.model.E_invoice_report;
import com.excel.model.Period;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sum_Disp_Detail;
import com.excel.model.Sum_Disp_Detail_Arc;
import com.excel.model.Sum_Disp_Header;
import com.excel.model.Sum_Disp_Header_arc;
import com.excel.repository.DispatchRepository;
import com.excel.repository.EInvoiceRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;
import com.google.gson.Gson;

@Service
public class EInvoiceServiceImpl implements EInvoiceService, MedicoConstants {
	@Autowired
	EInvoiceRepository einvoicerepo;
	@Autowired
	DispatchRepository dispatchrepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	ParameterService paramService;

	private static final int BUFFER_SIZE = 4096;
	DecimalFormat df = new DecimalFormat("##.00");

	@Override
	public String generateEinvoice(String targetURL, String urlParameters, String gstNo, String ownerId,
			String authToken, String methodType) {
		HttpURLConnection connection = null;
		System.out.println("urlParameters " + urlParameters);
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(methodType);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setRequestProperty("x-cleartax-auth-token", authToken);
			connection.setRequestProperty("x-cleartax-product", "EInvoice");
			connection.setRequestProperty("owner_id", ownerId);
			connection.setRequestProperty("gstin", gstNo);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			// Send request
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(urlParameters);
			writer.flush();
			writer.close();
			os.close();
			// Get Response
			InputStream is = null;
			int statusCode = connection.getResponseCode();
			if (statusCode >= 200 && statusCode < 400) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println("response " + response.toString());
			return response.toString();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}

		}
	}

	@Override
	public EInvoiceTransactionSuperTax generateEInvoiceSuperTax(String finYearFlag, String finYearId, String eway_bill,
			String divId, String from, String to) {
		
		EInvoiceTransactionSuperTax einv_params = new EInvoiceTransactionSuperTax();
		try {
			String newDocNumber = "new";
			String oldDocNumber = "old";
			List<EInvoiceGenerateData> list = einvoicerepo.getEInvoiceGenerateData(finYearFlag, finYearId, divId, from,
					to);
			List<EInvoiceDataSuperTax> transaction = new ArrayList<EInvoiceDataSuperTax>();

			EInvoiceDataSuperTax details = null;
			boolean isfirst = true;
			boolean isbreak = false;
			int i = 0;
			for (EInvoiceGenerateData data : list) {
				newDocNumber = data.getDocDtls_NO();
				if (!newDocNumber.trim().equals(oldDocNumber)) {
					System.out.println("newDocNumber " + newDocNumber);
					if (isfirst == false) {
						transaction.add(details);
						if (transaction.size() == 25) {
							isbreak = true;
							break;
						}
					}
					i = 0;
					details = new EInvoiceDataSuperTax();
					details.setItemList(new ArrayList<EInvoiceDataSuperTax.ItemList>());
					details.setVersion("1.01");
					// TranDtls
					details.setTranDtls(details.new TranDtls());
					details.getTranDtls().setTaxSch(data.getTaxType());
					details.getTranDtls().setSupTyp(data.getSupTyp());
					details.getTranDtls().setEcmGstin(data.getEcmGstin());
					details.getTranDtls().setIgstOnIntra(data.getIgstOnIntra());
					details.getTranDtls().setRegRev(data.getRegRev());
					// DocDtls
					details.setDocDtls(details.new DocDtls());
					details.getDocDtls().setTyp(data.getDocDtls_TYP());
					details.getDocDtls().setNo(data.getDocDtls_NO());
					details.getDocDtls().setDt(data.getDocDtls_DT());
					// SellerDtls
					details.setSellerDtls(details.new SellerDtls());
					details.getSellerDtls().setGstin(data.getSellerDtls_GSTIN().trim().substring(0, 15));
					details.getSellerDtls().setLglNm(data.getSellerDtlsLglNm());
					details.getSellerDtls().setTrdNm(data.getSellerDtlsLglNm());
					details.getSellerDtls().setAddr1(data.getSellerDtlsAddr1());
					details.getSellerDtls().setAddr2(data.getSellerDtlsAddr2());
					details.getSellerDtls().setLoc(data.getSellerDtlsloc());
					details.getSellerDtls().setPin(data.getSellerDtlsPin());
					details.getSellerDtls().setStcd(data.getSellerDtlsStcd());
					details.getSellerDtls().setPh(data.getSellerDtlsPh());
					details.getSellerDtls().setEm(data.getSellerDtlsEm());
					// BuyerDtls
					details.setBuyerDtls(details.new BuyerDtls());
					details.getBuyerDtls().setGstin(data.getBuyerDtlsGstin().trim().substring(0, 15));
					details.getBuyerDtls().setLglNm(data.getBuyerDtlsLglNm());
					details.getBuyerDtls().setTrdNm(data.getBuyerDtlsTrdNm());
					details.getBuyerDtls().setPos(data.getBuyerDtlsPos());
					details.getBuyerDtls().setAddr1(data.getBuyerDtlsAddr1());
					details.getBuyerDtls().setAddr2(data.getBuyerDtlsAddr2());
					details.getBuyerDtls().setLoc(data.getBuyerDtlsLoc());
					details.getBuyerDtls().setPin(data.getBuyerDtlsPin().trim());
					details.getBuyerDtls().setStcd(data.getBuyerDtlsStcd());
					details.getBuyerDtls()
							.setPh(data.getBuyerDtlsPh() == null || data.getBuyerDtlsPh().isEmpty() ? "1234567"
									: data.getBuyerDtlsPh().replaceAll("[^0-9]", ""));
					details.getBuyerDtls().setEm(data.getBuyerDtlsem());
					// DispDtls
					details.setDispDtls(details.new DispDtls());
					details.getDispDtls().setNm(data.getDispDtlsNm());
					details.getDispDtls().setAddr1(data.getDispDtlsAddr1());
					details.getDispDtls().setAddr2(data.getDispDtlsAddr2());
					details.getDispDtls().setLoc(data.getDispDtlsLoc());
					details.getDispDtls().setPin(data.getDispDtlsPin());
					details.getDispDtls().setStcd(data.getDispDtlsStcd());
					// ShipDtls
					details.setShipDtls(details.new ShipDtls());
					details.getShipDtls().setGstin(data.getShipDtlsGstin().trim().substring(0, 15));
					details.getShipDtls().setLglNm(data.getShipDtlsLglnm());
					details.getShipDtls().setTrdNm(data.getShipDtlsTrdnm());
					details.getShipDtls().setAddr1(data.getShipDtlsAddr1());
					details.getShipDtls().setAddr2(data.getShipDtlsAddr2());
					details.getShipDtls().setLoc(data.getShipDtlsLoc());
					details.getShipDtls().setPin(data.getShipDtlsPin().trim());
					details.getShipDtls().setStcd(data.getShipDtlsStcd());
					// ValDtls
					details.setValDtls(details.new ValDtls());
					details.getValDtls().setAssVal(data.getValDtlsAssVal());
					details.getValDtls().setCgstVal(data.getValDtlsCgstVal());
					details.getValDtls().setSgstVal(data.getValDtlsSgstVal());
					details.getValDtls().setIgstVal(data.getValDtlsIgstVal());
					details.getValDtls().setCesVal(data.getValDtlsCesVal());
					details.getValDtls().setStCesVal(data.getValDtlsStCesVal());
					details.getValDtls().setDiscount(data.getValDtlsDiscount());
					details.getValDtls().setOthChrg(data.getValDtlsOthChrg());
					details.getValDtls().setRndOffAmt(data.getValDtlsRndOffAmt());
					details.getValDtls().setTotInvVal(data.getValDtlsTotInvVal());
					details.getValDtls().setTotInvValFc(data.getValDtlsTotInvValFc());
					// PayDtls
					details.setPayDtls(details.new PayDtls());
					details.getPayDtls().setNm(data.getPayDtlsNm());
					details.getPayDtls().setAccDet(data.getPayDtlsNm());
					details.getPayDtls().setMode(data.getPayDtlsMode());
					details.getPayDtls().setFininsBr(data.getPayDtlsFinInsBr());
					details.getPayDtls().setPayTerm(data.getPayDtlsPayTerm());
					details.getPayDtls().setPayInstr(data.getPayDtlsInStr());
					details.getPayDtls().setCrTrn(data.getPayDtlsCrTrn());
					details.getPayDtls().setDirDr(data.getPayDtlsDirDr());
					details.getPayDtls().setCrDay(data.getPayDtlsCrDay());
					details.getPayDtls().setPaidAmt(data.getPayDtlsPaidAmt());
					details.getPayDtls().setPaymtDue(data.getPayDtlsPaymtDue());
					// RefDtls
					details.setRefDtls(details.new RefDtls());

					// not working in case of super tax
					// ExpDtls
//					details.setExpDtls(details.new ExpDtls());
//					details.getExpDtls().setShipBNo(data.getExpDtlsShipBno());
//					details.getExpDtls().setShipBDt(data.getExpDtlsShipBdt());
//					details.getExpDtls().setPort(data.getExpDtlsPort());
//					//getting error
//					details.getExpDtls().setRefClm(data.getExpDtlsRefClm());
//					details.getExpDtls().setForCur(data.getExpDtlsForCur());
//					details.getExpDtls().setCntCode(data.getExpDtlsCntCode());
					System.out.println("eway_bill :: " + eway_bill);
					// EwbDtls
					if (eway_bill.equals("Y")) {
						System.out.println("In EwayBill Trans " + data.getEwbDtlsTransId());
						details.setEwbDtls(details.new EwbDtls());
						details.getEwbDtls().setTransId(data.getEwbDtlsTransId());
						details.getEwbDtls().setTransName(data.getEwbDtlsTransName());
						details.getEwbDtls().setDistance(data.getEwbDtlsDistance());
						details.getEwbDtls().setTransDocNo(data.getEwbDtlsTransDocNo());
						details.getEwbDtls().setTransDocDt(data.getEwbDtlsTransDocDt());
						details.getEwbDtls().setVehNo(data.getEwbDtlsVehNo());
						details.getEwbDtls().setVehType(data.getEwbDtlsVehType());
						details.getEwbDtls().setTransMode("ROAD");
						details.getEwbDtls().setIsEWayBillIntegrated("true");
					}

				}
				// ItemList
				details.getItemList().add(details.new ItemList());
				details.getItemList().get(i).setSlNo(String.valueOf(i + 1));
				details.getItemList().get(i).setPrdDesc(data.getItemListPrdDesc());
				details.getItemList().get(i).setIsServc(data.getItemListIsServc());
				details.getItemList().get(i).setHsnCd(data.getItemListHsnCd().trim());
				details.getItemList().get(i).setBarcde(data.getItemListBarcde());
				details.getItemList().get(i).setQty(data.getItemListQty());
				details.getItemList().get(i).setFreeQty(data.getItemListFreeQty());
				details.getItemList().get(i).setUnit(data.getItemListUnit());
				details.getItemList().get(i).setUnitPrice(data.getItemListUnitPrice());
				System.out.println(data.getItemListTotamt().trim());
				details.getItemList().get(i)
						.setTotAmt(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListTotamt().trim())));
				details.getItemList().get(i).setDiscount(
						new DecimalFormat("0.00").format(Double.valueOf(data.getItemListDiscount().trim())));
				details.getItemList().get(i).setPreTaxVal(
						new DecimalFormat("0.00").format(Double.valueOf(data.getItemListPreTaxVal().trim())));
				details.getItemList().get(i)
						.setAssAmt(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListAssAmt().trim())));
				details.getItemList().get(i)
						.setGstRt(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListGstRt().trim())));
				details.getItemList().get(i)
						.setIgstAmt(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListIgstAmt().trim())));
				details.getItemList().get(i)
						.setCgstAmt(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListCgstAmt().trim())));
				details.getItemList().get(i)
						.setSgstAmt(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListSgstAmt().trim())));
				details.getItemList().get(i)
						.setCesRt(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListCesRt().trim())));
				details.getItemList().get(i)
						.setCesAmt(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListCesAmt().trim())));
				details.getItemList().get(i).setCesNonAdvlAmt("0.00");// CesNonAdvlAmt
				details.getItemList().get(i).setStateCesRt(
						new DecimalFormat("0.00").format(Double.valueOf(data.getItemListStateCesRt().trim())));
				details.getItemList().get(i).setStateCesAmt(
						new DecimalFormat("0.00").format(Double.valueOf(data.getItemListStateCesAmt().trim())));
				details.getItemList().get(i).setStateCesNonAdvlAmt(
						new DecimalFormat("0.00").format(Double.valueOf(data.getItemListStateCesNonAdvlAmt().trim())));
				details.getItemList().get(i)
						.setOthChrg(new DecimalFormat("0.00").format(Double.valueOf(data.getItemListOthChrg().trim())));
				details.getItemList().get(i).setTotItemVal(
						new DecimalFormat("0.00").format(Double.valueOf(data.getItemListTotItemVal().trim())));
				details.getItemList().get(i).setOrdLineRef(data.getItemListOrdLineRef());
				details.getItemList().get(i).setOrgCntry(data.getItemListOrgCntry());
				details.getItemList().get(i).setPrdSlNo(data.getItemListPrdSlNo().trim());

				details.getItemList().get(i).setBchDtls(details.new ItemList().new BchDtls());
				details.getItemList().get(i).getBchDtls().setNm(data.getBchDtlsNm());
				details.getItemList().get(i).getBchDtls().setExpDt(data.getBchDtlsExpdt());
				details.getItemList().get(i).getBchDtls().setWrDt(data.getBchDtlsWrDt());

//				details.getItemList().get(0).setAttribDtls(details.new ItemList().new AttribDtls());
//				details.getItemList().get(0).getAttribDtls().get(0).setNm("Rice");
//				details.getItemList().get(0).getAttribDtls().get(0).setVal("10000");
				i++;
				isfirst = false;
				oldDocNumber = newDocNumber;
			}
			if (isbreak == false) {
				transaction.add(details);
			}
			einv_params.setInvoices(transaction);

//			for(EInvoiceDataSuperTax data:transaction) {
//				tran=new EInvoiceTransactionSuperTax();
//				tran.setTransaction(data);
//				finalList.add(tran);
//			}
			System.out.println("object " + new Gson().toJson(einv_params));
			// EInvoiceData data = new
			// Gson().fromJson(getIntent().getStringExtra("MANUAL_GRN_BEAN"),
			// EInvoiceData.class);
//			String jsonResponse=this.generateEinvoice(url,new Gson().toJson(finalList).toString());
//			//String jsonResponse=this.executePost(url,d);
//			responseList = new Gson().fromJson(jsonResponse, new TypeToken<List<EInvoiceResponse>>() {}.getType());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());
		}
		// TODO Auto-generated method stub
		return einv_params;
	}

	@Override
	public List<EInvoiceTransaction> generateEInvoice(String finYearFlag, String finYearId, String eway_bill,
			String divId, String from, String to) {
		List<EInvoiceTransaction> finalList = null;
		try {
			String newDocNumber = "new";
			String oldDocNumber = "old";
			List<EInvoiceGenerateData> list = einvoicerepo.getEInvoiceGenerateData(finYearFlag, finYearId, divId, from,
					to);
			List<EInvoiceData> transaction = new ArrayList<EInvoiceData>();
			EInvoiceTransaction tran = null;
			EInvoiceData details = null;
			boolean isfirst = true;
			boolean isbreak = false;
			int i = 0;
			for (EInvoiceGenerateData data : list) {
				newDocNumber = data.getDocDtls_NO();
				if (!newDocNumber.trim().equals(oldDocNumber)) {
					System.out.println("newDocNumber " + newDocNumber);
					if (isfirst == false) {
						transaction.add(details);
						if (transaction.size() == 10) {
							isbreak = true;
							break;
						}
					}
					i = 0;
					details = new EInvoiceData();
					details.setItemList(new ArrayList<EInvoiceData.ItemList>());
					details.setVersion("1.1");
					// TranDtls
					details.setTranDtls(details.new TranDtls());
					details.getTranDtls().setTaxSch(data.getTaxType());
					details.getTranDtls().setSupTyp(data.getSupTyp());
					details.getTranDtls().setEcmGstin(data.getEcmGstin());
					details.getTranDtls().setIgstOnIntra(data.getIgstOnIntra());
					details.getTranDtls().setRegRev(data.getRegRev());
					// DocDtls
					details.setDocDtls(details.new DocDtls());
					details.getDocDtls().setTyp(data.getDocDtls_TYP());
					details.getDocDtls().setNo(data.getDocDtls_NO().replace("\\s+", ""));
					details.getDocDtls().setDt(data.getDocDtls_DT());
					// SellerDtls
					details.setSellerDtls(details.new SellerDtls());
					details.getSellerDtls().setGstin(data.getSellerDtls_GSTIN().trim().substring(0, 15));
					details.getSellerDtls().setLglNm(data.getSellerDtlsLglNm());
					details.getSellerDtls().setTrdNm(data.getSellerDtlsLglNm());
					details.getSellerDtls().setAddr1(data.getSellerDtlsAddr1());
					details.getSellerDtls().setAddr2(data.getSellerDtlsAddr2());
					details.getSellerDtls().setLoc(data.getSellerDtlsloc());
					details.getSellerDtls().setPin(data.getSellerDtlsPin());
					details.getSellerDtls().setStcd(data.getSellerDtlsStcd());
					details.getSellerDtls().setPh(data.getSellerDtlsPh().isEmpty() ? null : data.getSellerDtlsPh());
					details.getSellerDtls().setEm(data.getSellerDtlsEm().isEmpty() ? null : data.getSellerDtlsEm());
					// BuyerDtls
					details.setBuyerDtls(details.new BuyerDtls());
					details.getBuyerDtls()
							.setGstin(data.getBuyerDtlsGstin().trim().length() > 15
									? data.getBuyerDtlsGstin().trim().substring(0, 15)
									: data.getBuyerDtlsGstin().trim());
					details.getBuyerDtls().setLglNm(data.getBuyerDtlsLglNm());
					details.getBuyerDtls().setTrdNm(data.getBuyerDtlsTrdNm());
					details.getBuyerDtls().setPos(data.getBuyerDtlsPos());
					details.getBuyerDtls().setAddr1(data.getBuyerDtlsAddr1());
					details.getBuyerDtls().setAddr2(data.getBuyerDtlsAddr2());
					details.getBuyerDtls().setLoc(data.getBuyerDtlsLoc());
					details.getBuyerDtls().setPin(data.getBuyerDtlsPin().trim());
					details.getBuyerDtls().setStcd(data.getBuyerDtlsStcd());
					// details.getBuyerDtls().setPh(data.getBuyerDtlsPh()==null ||
					// data.getBuyerDtlsPh().isEmpty()?"1234567":data.getBuyerDtlsPh().replaceAll("[^0-9]",
					// ""));
					details.getBuyerDtls().setPh(data.getBuyerDtlsPh().isEmpty() ? null : data.getBuyerDtlsPh());
					details.getBuyerDtls().setEm(data.getBuyerDtlsem().isEmpty() ? null : data.getBuyerDtlsem());
					// DispDtls
					details.setDispDtls(details.new DispDtls());
					details.getDispDtls().setNm(data.getDispDtlsNm());
					details.getDispDtls().setAddr1(data.getDispDtlsAddr1());
					details.getDispDtls().setAddr2(data.getDispDtlsAddr2());
					details.getDispDtls().setLoc(data.getDispDtlsLoc());
					details.getDispDtls().setPin(data.getDispDtlsPin());
					details.getDispDtls().setStcd(data.getDispDtlsStcd());
					// ShipDtls
					details.setShipDtls(details.new ShipDtls());
					details.getShipDtls()
							.setGstin(data.getShipDtlsGstin().trim().length() > 15
									? data.getShipDtlsGstin().trim().substring(0, 15)
									: data.getShipDtlsGstin().trim());
					details.getShipDtls().setLglNm(data.getShipDtlsLglnm());
					details.getShipDtls().setTrdNm(data.getShipDtlsTrdnm());
					details.getShipDtls().setAddr1(data.getShipDtlsAddr1());
					details.getShipDtls().setAddr2(data.getShipDtlsAddr2());
					details.getShipDtls().setLoc(data.getShipDtlsLoc());
					details.getShipDtls().setPin(data.getShipDtlsPin().trim());
					details.getShipDtls().setStcd(data.getShipDtlsStcd());
					// ValDtls
					details.setValDtls(details.new ValDtls());
					details.getValDtls().setAssVal(data.getValDtlsAssVal());
					details.getValDtls().setCgstVal(data.getValDtlsCgstVal());
					details.getValDtls().setSgstVal(data.getValDtlsSgstVal());
					details.getValDtls().setIgstVal(data.getValDtlsIgstVal());
					details.getValDtls().setCesVal(data.getValDtlsCesVal());
					details.getValDtls().setStCesVal(data.getValDtlsStCesVal());
					details.getValDtls().setDiscount(data.getValDtlsDiscount());
					details.getValDtls().setOthChrg(data.getValDtlsOthChrg());
					details.getValDtls().setRndOffAmt(data.getValDtlsRndOffAmt());
					details.getValDtls().setTotInvVal(data.getValDtlsTotInvVal());
					details.getValDtls().setTotInvValFc(data.getValDtlsTotInvValFc());
					// PayDtls
					details.setPayDtls(details.new PayDtls());
					details.getPayDtls().setNm(data.getPayDtlsNm());
					details.getPayDtls().setAccDet(data.getPayDtlsNm());
					details.getPayDtls().setMode(data.getPayDtlsMode());
					details.getPayDtls().setFininsBr(data.getPayDtlsFinInsBr());
					details.getPayDtls().setPayInstr(data.getPayDtlsPayTerm());
					details.getPayDtls().setPayInstr(data.getPayDtlsInStr());
					details.getPayDtls().setCrTrn(data.getPayDtlsCrTrn());
					details.getPayDtls().setDirDr(data.getPayDtlsDirDr());
					details.getPayDtls().setCrDay(data.getPayDtlsCrDay());
					details.getPayDtls().setPaidAmt(data.getPayDtlsPaidAmt());
					details.getPayDtls().setPaymtDue(data.getPayDtlsPaymtDue());
					// RefDtls
					details.setRefDtls(details.new RefDtls());
					// ExpDtls
					details.setExpDtls(details.new ExpDtls());
					details.getExpDtls().setShipBNo(data.getExpDtlsShipBno());
					details.getExpDtls().setShipBDt(data.getExpDtlsShipBdt());
					details.getExpDtls().setPort(data.getExpDtlsPort());
					details.getExpDtls().setRefClm(data.getExpDtlsRefClm());
					details.getExpDtls().setForCur(data.getExpDtlsForCur());
					details.getExpDtls().setCntCode(data.getExpDtlsCntCode());
					System.out.println("eway_bill :: " + eway_bill);
					// EwbDtls
					if (eway_bill.equals("Y")) {
						System.out.println("In EwayBill Trans " + data.getEwbDtlsTransId());
						details.setEwbDtls(details.new EwbDtls());
						details.getEwbDtls().setTransId(data.getEwbDtlsTransId());
						details.getEwbDtls().setTransName(data.getEwbDtlsTransName());
						details.getEwbDtls().setDistance(data.getEwbDtlsDistance());
						details.getEwbDtls().setTransDocNo(data.getEwbDtlsTransDocNo());
						details.getEwbDtls().setTransDocDt(data.getEwbDtlsTransDocDt());
						details.getEwbDtls().setVehNo(data.getEwbDtlsVehNo());
						details.getEwbDtls().setVehType(data.getEwbDtlsVehType());
						details.getEwbDtls().setTransMode(data.getTransMode());
					}

				}
				// ItemList
				details.getItemList().add(details.new ItemList());
				details.getItemList().get(i).setSlNo(String.valueOf(i + 1));
				details.getItemList().get(i).setPrdDesc(data.getItemListPrdDesc());
				details.getItemList().get(i).setIsServc(data.getItemListIsServc());
				details.getItemList().get(i).setHsnCd(data.getItemListHsnCd().trim());
				details.getItemList().get(i).setBarcde(data.getItemListBarcde());
				details.getItemList().get(i).setQty(data.getItemListQty());
				details.getItemList().get(i).setFreeQty(data.getItemListFreeQty());
				// details.getItemList().get(i).setUnit(data.getItemListUnit());
				details.getItemList().get(i).setUnit("OTH");
				details.getItemList().get(i).setUnitPrice(data.getItemListUnitPrice());
				details.getItemList().get(i).setTotAmt(df.format(Double.parseDouble(data.getItemListTotamt())));
				details.getItemList().get(i).setDiscount(data.getItemListDiscount());
				details.getItemList().get(i).setPreTaxVal(df.format(Double.parseDouble(data.getItemListPreTaxVal())));
				details.getItemList().get(i).setAssAmt(df.format(Double.parseDouble(data.getItemListAssAmt())));
				details.getItemList().get(i).setGstRt(data.getItemListGstRt());
				details.getItemList().get(i).setIgstAmt(data.getItemListIgstAmt());
				details.getItemList().get(i).setCgstAmt(data.getItemListCgstAmt());
				details.getItemList().get(i).setSgstAmt(data.getItemListSgstAmt());
				details.getItemList().get(i).setCesRt(data.getItemListCesRt());
				details.getItemList().get(i).setCesAmt(data.getItemListCesAmt());
				details.getItemList().get(i).setCesNonAdvlAmt("0");// CesNonAdvlAmt
				details.getItemList().get(i).setStateCesRt(data.getItemListStateCesRt());
				details.getItemList().get(i).setStateCesAmt(data.getItemListStateCesAmt());
				details.getItemList().get(i).setStateCesNonAdvlAmt(data.getItemListStateCesNonAdvlAmt());
				details.getItemList().get(i).setOthChrg(data.getItemListOthChrg());
				details.getItemList().get(i).setTotItemVal(df.format(Double.parseDouble(data.getItemListTotItemVal())));
				details.getItemList().get(i).setOrdLineRef(data.getItemListOrdLineRef());
				details.getItemList().get(i).setOrgCntry(data.getItemListOrgCntry());
				details.getItemList().get(i).setPrdSlNo(data.getItemListPrdSlNo().trim());

				details.getItemList().get(i).setBchDtls(details.new ItemList().new BchDtls());
				details.getItemList().get(i).getBchDtls().setNm(data.getBchDtlsNm());
				details.getItemList().get(i).getBchDtls().setExpDt(data.getBchDtlsExpdt());
				details.getItemList().get(i).getBchDtls().setWrDt(data.getBchDtlsWrDt());

//				details.getItemList().get(0).setAttribDtls(details.new ItemList().new AttribDtls());
//				details.getItemList().get(0).getAttribDtls().get(0).setNm("Rice");
//				details.getItemList().get(0).getAttribDtls().get(0).setVal("10000");
				i++;
				isfirst = false;
				oldDocNumber = newDocNumber;
			}
			if (isbreak == false) {
				transaction.add(details);
			}
			finalList = new ArrayList<EInvoiceTransaction>();
			for (EInvoiceData data : transaction) {
				tran = new EInvoiceTransaction();
				tran.setTransaction(data);
				finalList.add(tran);
			}
			System.out.println("object " + new Gson().toJson(finalList));
			// EInvoiceData data = new
			// Gson().fromJson(getIntent().getStringExtra("MANUAL_GRN_BEAN"),
			// EInvoiceData.class);
//			String jsonResponse=this.generateEinvoice(url,new Gson().toJson(finalList).toString());
//			//String jsonResponse=this.executePost(url,d);
//			responseList = new Gson().fromJson(jsonResponse, new TypeToken<List<EInvoiceResponse>>() {}.getType());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return finalList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEInvoiceResponse(List<EInvoiceResponse> list, String comp_cd, String divId, String finYear,
			String finyrFlag) throws Exception {
		EInvoiceHeader header = null;
		EInvoiceHeader_Arc headerArc = null;
		for (EInvoiceResponse data : list) {
			System.out.println("Result " + data.getGovt_response().getSuccess());
			if (data.getGovt_response().getSuccess().equals("Y")) {
				String sum_dsp_dtl_id = data.getTransaction().getItemList().get(0).getOrdLineRef();

				if (finyrFlag.equals(MedicoConstants.CURRENT)) {
					Sum_Disp_Detail detail = dispatchrepository.sumdispatchDtlById(Long.valueOf(sum_dsp_dtl_id));// new
																													// Sum_Disp_DetailDao().getSumDispDetail(Long.valueOf(sum_dsp_dtl_id));
					header = einvoicerepo.getObjectByID(detail.getSumdspdtl_sumdsp_id());

					if (header == null) {
						header = new EInvoiceHeader();
						header.setTrn_id(detail.getSumdspdtl_sumdsp_id());
						header.setComp_code(comp_cd);
						header.setFin_year_id(Long.valueOf(finYear));
						header.setTrn_type(data.getTransaction().getDocDtls().getTyp());
						header.setTrn_number(data.getTransaction().getDocDtls().getNo());
						header.setTrn_date(Utility.convertStringtoDate(data.getTransaction().getDocDtls().getDt()));
						header.setTrn_result(data.getGovt_response().getSuccess());
						header.setTrn_ack_date(Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getAckDt()));
						header.setTrn_ack_no(data.getGovt_response().getAckNo());
						header.setTrn_action_type("E");
						System.out.println("sig" + data.getGovt_response().getSignedInvoice().length());
						header.setTrn_inv_sign(data.getGovt_response().getSignedInvoice());
						header.setTrn_irn_number(data.getGovt_response().getIrn());
						header.setTrn_qr_code(data.getGovt_response().getSignedQRCode());
						header.setTrn_status(data.getGovt_response().getStatus());
						header.setTrn_ewaybillno(data.getGovt_response().getEwbNo());
						if (data.getGovt_response().getEwbNo() != null) {
							header.setTrn_ewaybilldt(
									Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
							header.setTrn_ewaybillvalid(
									Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbValidTill()));
						}
						header.setIrncancel("N");
						header.setEwaybillcancel("N");
						header.setEinvoice_tranid(data.getTransaction_id());
						header.setDiv_id(Long.valueOf(divId));
						transactionalRepository.persist(header);
						// hsession.save(header);
					} else {
						header.setTrn_id(detail.getSumdspdtl_sumdsp_id());
						header.setComp_code(comp_cd);
						header.setFin_year_id(Long.valueOf(finYear));
						header.setTrn_type(data.getTransaction().getDocDtls().getTyp());
						header.setTrn_number(data.getTransaction().getDocDtls().getNo());
						header.setTrn_result(data.getGovt_response().getSuccess());
						;
						header.setTrn_ack_no(data.getGovt_response().getAckNo());
						header.setTrn_action_type("E");
						header.setTrn_inv_sign(data.getGovt_response().getSignedInvoice());
						header.setTrn_irn_number(data.getGovt_response().getIrn());
						header.setTrn_qr_code(data.getGovt_response().getSignedQRCode());
						header.setTrn_status(data.getGovt_response().getStatus());
						header.setTrn_ewaybillno(data.getGovt_response().getEwbNo());
						header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
						if (data.getGovt_response().getEwbNo() != null) {
							header.setTrn_ewaybilldt(
									Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
							header.setTrn_ewaybillvalid(
									Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbValidTill()));
						}
						if (data.getGovt_response().getStatus().equalsIgnoreCase("CNL")) {
							header.setIrncancel("Y");
						}
						transactionalRepository.update(header);
						// hsession.update(header);
					}
				} else if (finyrFlag.equals(MedicoConstants.PREVIOUS)) {
					Sum_Disp_Detail_Arc detail = dispatchrepository
							.sumdispatchArcDtlBysumdspDtlId(Long.valueOf(sum_dsp_dtl_id), finYear);// new
																									// Sum_Disp_DetailDao().getSumDispDetail(Long.valueOf(sum_dsp_dtl_id));
					headerArc = einvoicerepo.getObjectByIDAndFinYear(detail.getSumdspdtl_sumdsp_id(), finYear);

					if (headerArc == null) {
						headerArc = new EInvoiceHeader_Arc();
						headerArc.setTrn_id(detail.getSumdspdtl_sumdsp_id());
						headerArc.setComp_code(comp_cd);
						headerArc.setFin_year_id(Long.valueOf(finYear));
						headerArc.setTrn_type(data.getTransaction().getDocDtls().getTyp());
						headerArc.setTrn_number(data.getTransaction().getDocDtls().getNo());
						headerArc.setTrn_date(Utility.convertStringtoDate(data.getTransaction().getDocDtls().getDt()));
						headerArc.setTrn_result(data.getGovt_response().getSuccess());
						headerArc.setTrn_ack_date(Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getAckDt()));
						headerArc.setTrn_ack_no(data.getGovt_response().getAckNo());
						headerArc.setTrn_action_type("E");
						System.out.println("sig" + data.getGovt_response().getSignedInvoice().length());
						headerArc.setTrn_inv_sign(data.getGovt_response().getSignedInvoice());
						headerArc.setTrn_irn_number(data.getGovt_response().getIrn());
						headerArc.setTrn_qr_code(data.getGovt_response().getSignedQRCode());
						headerArc.setTrn_status(data.getGovt_response().getStatus());
						headerArc.setTrn_ewaybillno(data.getGovt_response().getEwbNo());
						if (data.getGovt_response().getEwbNo() != null) {
							headerArc.setTrn_ewaybilldt(
									Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
							headerArc.setTrn_ewaybillvalid(
									Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbValidTill()));
						}
						headerArc.setIrncancel("N");
						headerArc.setEwaybillcancel("N");
						headerArc.setEinvoice_tranid(data.getTransaction_id());
						headerArc.setDiv_id(Long.valueOf(divId));
						headerArc.setSlno(0l);
						transactionalRepository.persist(headerArc);
						// hsession.save(header);

						headerArc.setSlno(headerArc.getArchive_id());
						transactionalRepository.update(headerArc);
					} else {
						headerArc.setTrn_id(detail.getSumdspdtl_sumdsp_id());
						headerArc.setComp_code(comp_cd);
						headerArc.setFin_year_id(Long.valueOf(finYear));
						headerArc.setTrn_type(data.getTransaction().getDocDtls().getTyp());
						headerArc.setTrn_number(data.getTransaction().getDocDtls().getNo());
						headerArc.setTrn_result(data.getGovt_response().getSuccess());
						;
						headerArc.setTrn_ack_no(data.getGovt_response().getAckNo());
						headerArc.setTrn_action_type("E");
						headerArc.setTrn_inv_sign(data.getGovt_response().getSignedInvoice());
						headerArc.setTrn_irn_number(data.getGovt_response().getIrn());
						headerArc.setTrn_qr_code(data.getGovt_response().getSignedQRCode());
						headerArc.setTrn_status(data.getGovt_response().getStatus());
						headerArc.setTrn_ewaybillno(data.getGovt_response().getEwbNo());
						headerArc
								.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
						if (data.getGovt_response().getEwbNo() != null) {
							headerArc.setTrn_ewaybilldt(
									Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
							headerArc.setTrn_ewaybillvalid(
									Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbValidTill()));
						}
						if (data.getGovt_response().getStatus().equalsIgnoreCase("CNL")) {
							headerArc.setIrncancel("Y");
						}
						transactionalRepository.update(headerArc);
						// hsession.update(header);
					}
				}
			}
		}
		// hsession.flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEInvoiceResponseSuperTax(EInvoiceResponseSuperTaxWrapper wrapperObj, String comp_cd, String divId,
			String finYear, String finYearflag) throws Exception {
		EInvoiceHeader header = null;
		EInvoiceHeader_Arc headerArc = null;
		for (EInvoiceResponseSuperTax data : wrapperObj.getInvoices()) {
			System.out.println("Result " + data.getSuccess());
			if (data.getSuccess()) {
				// get sum_dsp_hdr_id by doc no

				// String
				// sum_dsp_dtl_id=data.getTransaction().getItemList().get(0).getOrdLineRef();

				if (finYearflag.equals(MedicoConstants.CURRENT)) {
					Sum_Disp_Header sum_disp_header = dispatchrepository
							.getSumDispHeaderbyChallanNo(data.getDocDtls().getNo());
					// Sum_Disp_Detail detail=
					// dispatchrepository.sumdispatchDtlById(Long.valueOf(sum_dsp_dtl_id));//new
					// Sum_Disp_DetailDao().getSumDispDetail(Long.valueOf(sum_dsp_dtl_id));
					header = einvoicerepo.getObjectByID(sum_disp_header.getSumdsp_id());

					if (header == null) {
						header = new EInvoiceHeader();
						header.setTrn_id(sum_disp_header.getSumdsp_id());
						header.setComp_code(comp_cd.trim());
						header.setFin_year_id(Long.valueOf(finYear));
						header.setTrn_type(data.getDocDtls().getTyp());
						header.setTrn_number(data.getDocDtls().getNo());
						header.setTrn_date(Utility.convertStringtoDate(data.getDocDtls().getDt()));
						header.setTrn_result(data.getSuccess() == true ? "Y" : "N");
						header.setTrn_ack_date(Utility.getDateformatYYYYDDMMHH(data.getAckDt()));
						header.setTrn_ack_no(data.getAckNo());
						header.setTrn_action_type("E");
						header.setTrn_inv_sign(data.getSignedInvoice() == null ? "" : data.getSignedInvoice());
						header.setTrn_irn_number(data.getIrn());
						header.setTrn_qr_code(data.getSignedQRCode());
						header.setTrn_status(data.getStatus());
						header.setTrn_ewaybillno(data.getEwbNo());
						if (data.getEwbNo() != null) {
							header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getEwbDt()));
							header.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(data.getEwbValidTill()));
						}
						header.setIrncancel("N");
						header.setEwaybillcancel("N");
						// header.setEinvoice_tranid(data.getTransaction_id());
						header.setDiv_id(Long.valueOf(divId));
						System.out.println(header.toString());
						transactionalRepository.persist(header);
						// hsession.save(header);
					} else {
						header.setTrn_id(sum_disp_header.getSumdsp_id());
						header.setComp_code(comp_cd.trim());
						header.setFin_year_id(Long.valueOf(finYear));
						header.setTrn_type(data.getDocDtls().getTyp());
						header.setTrn_number(data.getDocDtls().getNo());
						header.setTrn_result(data.getSuccess() == true ? "Y" : "N");
						header.setTrn_ack_no(data.getAckNo());
						header.setTrn_action_type("E");
						header.setTrn_inv_sign(data.getSignedInvoice() == null ? "" : data.getSignedInvoice());
						header.setTrn_irn_number(data.getIrn());
						header.setTrn_qr_code(data.getSignedQRCode());
						header.setTrn_status(data.getStatus());
						header.setTrn_ewaybillno(data.getEwbNo());
						header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getEwbDt()));
						if (data.getEwbNo() != null) {
							header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getEwbDt()));
							header.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(data.getEwbValidTill()));
						}
						if (data.getStatus().equalsIgnoreCase("CANCELLED")) {
							header.setIrncancel("Y");
						}
						transactionalRepository.update(header);
						// hsession.update(header);
					}
				} else if (finYearflag.equals(MedicoConstants.PREVIOUS)) {
					// Sum_Disp_Detail_Arc detail=
					// dispatchrepository.sumdispatchArcDtlBysumdspDtlId(Long.valueOf(sum_dsp_dtl_id),finYear);//new
					// Sum_Disp_DetailDao().getSumDispDetail(Long.valueOf(sum_dsp_dtl_id));
					// headerArc=einvoicerepo.getObjectByIDAndFinYear(Long.valueOf(data.getDocDtls().getNo()),finYear);
					Sum_Disp_Header_arc sum_disp_headerArc = dispatchrepository
							.sumdispatchArcHdrByChallanNo(data.getDocDtls().getNo(), finYear);
					headerArc = einvoicerepo.getObjectByIDAndFinYear(sum_disp_headerArc.getSumdsp_id(), finYear);

					if (headerArc == null) {
						headerArc = new EInvoiceHeader_Arc();
						headerArc.setTrn_id(sum_disp_headerArc.getSumdsp_id());
						headerArc.setComp_code(comp_cd.trim());
						headerArc.setFin_year_id(Long.valueOf(finYear));
						headerArc.setTrn_type(data.getDocDtls().getTyp());
						headerArc.setTrn_number(data.getDocDtls().getNo());
						headerArc.setTrn_date(Utility.convertStringtoDate(data.getDocDtls().getDt()));
						headerArc.setTrn_result(data.getSuccess() == true ? "Y" : "N");
						headerArc.setTrn_ack_date(Utility.getDateformatYYYYDDMMHH(data.getAckDt()));
						headerArc.setTrn_ack_no(data.getAckNo());
						headerArc.setTrn_action_type("E");
						headerArc.setTrn_inv_sign(data.getSignedInvoice() == null ? "" : data.getSignedInvoice());
						headerArc.setTrn_irn_number(data.getIrn());
						headerArc.setTrn_qr_code(data.getSignedQRCode());
						headerArc.setTrn_status(data.getStatus());
						headerArc.setTrn_ewaybillno(data.getEwbNo());
						if (data.getEwbNo() != null) {
							headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getEwbDt()));
							headerArc.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(data.getEwbValidTill()));
						}
						headerArc.setIrncancel("N");
						headerArc.setEwaybillcancel("N");
						headerArc.setDiv_id(Long.valueOf(divId));
						headerArc.setSlno(0l);
						transactionalRepository.persist(headerArc);
						headerArc.setSlno(headerArc.getArchive_id());
						transactionalRepository.update(headerArc);
					} else {
						headerArc.setTrn_id(sum_disp_headerArc.getSumdsp_id());
						headerArc.setComp_code(comp_cd.trim());
						headerArc.setFin_year_id(Long.valueOf(finYear));
						headerArc.setTrn_type(data.getDocDtls().getTyp());
						headerArc.setTrn_number(data.getDocDtls().getNo());
						headerArc.setTrn_result(data.getSuccess() == true ? "Y" : "N");
						;
						headerArc.setTrn_ack_no(data.getAckNo());
						headerArc.setTrn_action_type("E");
						headerArc.setTrn_inv_sign(data.getSignedInvoice() == null ? "" : data.getSignedInvoice());
						headerArc.setTrn_irn_number(data.getIrn());
						headerArc.setTrn_qr_code(data.getSignedQRCode());
						headerArc.setTrn_status(data.getStatus());
						headerArc.setTrn_ewaybillno(data.getEwbNo());
						headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getEwbDt()));
						if (data.getEwbNo() != null) {
							headerArc.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getEwbDt()));
							headerArc.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(data.getEwbValidTill()));
						}
						if (data.getStatus().equalsIgnoreCase("CANCELLED")) {
							headerArc.setIrncancel("Y");
						}
						transactionalRepository.update(headerArc);
					}
				}
			}
		}
		// hsession.flush();
	}

	@Override
	public List<DivMaster> getteams(String emp_id) throws Exception {

		return einvoicerepo.getDivandTeams(emp_id);
	}

	@Override
	public List<Period> getperiod() throws Exception {
		// TODO Auto-generated method stub
		return einvoicerepo.getperiod();
	}

	@Override
	public Period getstartdate() throws Exception {
		// TODO Auto-generated method stub
		return einvoicerepo.getdate();
	}

	@Override
	public List<EInvoiceByIrnData> generateEInvoiceByIrn(String finYearFlag, String finYearId, String eway_bill,
			String divId, String from, String to) {
		List<EInvoiceByIrnData> finalList = null;
		EInvoiceByIrnData data = null;
		Object[] arr = null;

		try {
			List<Object> list = einvoicerepo.getChallanDetailsHavingIrn(from, to, finYearId, divId);
			if (list != null && list.size() > 0) {
				finalList = new ArrayList<EInvoiceByIrnData>();
				for (Object obj : list) {
					arr = (Object[]) obj;
					data = new EInvoiceByIrnData();
					data.setIrn(arr[1].toString());
					data.setDistance(arr[2].toString());
					data.setTransMode(arr[3].toString());//
					data.setTransId(arr[4].toString());
					data.setTransName(arr[5].toString().trim());
					data.setTransDocDt(arr[6].toString());//
					data.setTransDocNo(arr[7].toString().trim());//
					data.setVehNo(arr[8].toString());//
					data.setVehType(arr[9].toString());//

					data.setExpShipDtls(new ExpShipDtls());

					data.getExpShipDtls().setAddr1(arr[10].toString());
					data.getExpShipDtls().setAddr2(arr[11].toString());
					data.getExpShipDtls().setLoc(arr[12].toString());
					data.getExpShipDtls().setPin(arr[13].toString().trim());
					data.getExpShipDtls().setStcd(arr[14].toString());

					data.setChallan_No(arr[15].toString());

					finalList.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return finalList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEInvoiceResponseIrn(List<EInvoiceByIrnResponse> list, String comp_cd, String divId)
			throws Exception {
		EInvoiceHeader header = null;
		System.out.println("********");
		System.out.println("list " + list.get(0).getIrn());
		for (EInvoiceByIrnResponse data : list) {
			header = einvoicerepo.getEInvoiceHeaderByIrn(data.getIrn());
			header.setTrn_ewaybillno(data.getGovt_response().getEwbNo());
			header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
			header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
			transactionalRepository.update(header);
		}
		// hsession.flush();
	}

	@Override
	public List<EInvoiceCancelData> setEInvoiceCancelData(List<String> irns) throws Exception {
		EInvoiceCancelData data = null;
		List<EInvoiceCancelData> cancelList = new ArrayList<EInvoiceCancelData>();
		for (String irn : irns) {
			data = new EInvoiceCancelData();
			data.setIrn(irn);
			data.setCnlRsn("1");
			data.setCnlRem("Wrong entry");
			cancelList.add(data);
		}
		return cancelList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancelIrn(List<IrnCancelResponse> data, String comp_cd) throws Exception {
		// TODO Auto-generated method stub
		EInvoiceHeader header = null;
		for (IrnCancelResponse res : data) {
			if (res.getGovt_response().getSuccess().equals("Y")) {
				header = einvoicerepo.getEInvoiceHeaderByIrn(res.getGovt_response().getIrn());
				header.setIrncancel("Y");
				transactionalRepository.update(header);
				// hsession.update(header);
				// hsession.flush();
			}

		}
	}

	@Override
	public List<Object> getIrnCancelData(String fromTrnId, String toTrnId, String divId, String finYearFlag)
			throws Exception {
		// TODO Auto-generated method stub
		return einvoicerepo.getIrnCancelData(fromTrnId, toTrnId, divId, finYearFlag);
	}

	@Override
	public EwayBillCancelData setEwayBillCancelData(String ewbNo) throws Exception {
		EwayBillCancelData data = new EwayBillCancelData();
		data.setEwbNo(Long.valueOf(ewbNo));
		data.setCancelRsnCode("DATA_ENTRY_MISTAKE");
		data.setCancelRmrk("DATA_ENTRY_MISTAKE");
		return data;
	}

	@Override
	public String cancelEwayBill(String targetURL, String urlParameters, String gstNo, String ownerId,
			String authToken) {
		HttpURLConnection connection = null;
		try {
			// Create connection
			System.out.println("Parameterd " + urlParameters);
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setRequestProperty("x-cleartax-auth-token", authToken);
			connection.setRequestProperty("x-cleartax-product", "EInvoice");
			connection.setRequestProperty("owner_id", ownerId);
			connection.setRequestProperty("gstin", gstNo);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			// Send request
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(urlParameters);
			writer.flush();
			writer.close();
			os.close();
			// Get Response
			InputStream is = null;
			int statusCode = connection.getResponseCode();
			if (statusCode >= 200 && statusCode < 400) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println("response " + response.toString());
			return response.toString();
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancelEwayBill(EwayBillCancelResponse data, String comp_cd) throws Exception {
		// TODO Auto-generated method stub
		EInvoiceHeader header = einvoicerepo.getEInvoiceHeaderByIrn(data.getIrn());
		header.setEwaybillcancel("Y");
		transactionalRepository.update(header);
		// hsession.update(header);
		// hsession.flush();
	}

	@Override
	public List<Sum_Disp_Header> getChallanDetails(String finYear, String divId, String finyrflag, Long loc_id)
			throws Exception {
		// TODO Auto-generated method stub
		return einvoicerepo.getChallanDetails(finYear, divId, finyrflag, loc_id);
	}

	@Override
	public List<EInvoiceHeader> getIrnChallansWithoutEway(String finYear, String divId, String finYearFlag, Long loc_id)
			throws Exception {
		// TODO Auto-generated method stub
		return einvoicerepo.getIrnChallansWithoutEway(finYear, divId, finYearFlag, loc_id);
	}

	@Override
	public List<EInvoiceHeader> getIrnGencancelData(String finYear, String divId, Long loc_id) throws Exception {
		// TODO Auto-generated method stub
		return einvoicerepo.getIrnGencancelData(finYear, divId, loc_id);
	}

	@Override
	public List<EInvoiceHeader> getEwayBillCancel(String finYear, String divId, String finYearFlag, Long loc_id) {
		// TODO Auto-generated method stub
		return einvoicerepo.getEwayBillCancel(finYear, divId, finYearFlag, loc_id);
	}

	@Override
	public String downloadFileIrn(String saveDir, String targetURL, String urlParameters, String gstNo, String ownerId,
			String authToken, int irnCount) throws IOException {
		String fileName = "";
		System.out.println("IRNCount :: " + irnCount);
		URL url = new URL(targetURL + "?irns=" + urlParameters);
		System.out.println("targetURL " + targetURL + "?irns=" + urlParameters);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.setUseCaches(false);
		httpConn.setDoOutput(true);
		httpConn.setRequestProperty("x-cleartax-auth-token", authToken);
		httpConn.setRequestProperty("x-cleartax-product", "EInvoice");
		httpConn.setRequestProperty("owner_id", ownerId);
		httpConn.setRequestProperty("gstin", gstNo);
		httpConn.setRequestProperty("Content-Type", "application/pdf");
		System.out.println(saveDir + " paramers" + urlParameters);
		int responseCode = httpConn.getResponseCode();
		String saveFilePath = null;
		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = "demo12";
			}
			if (irnCount == 1) {
				fileName = "IRN" + new Date().getTime() + ".pdf";
			} else {
				fileName = "IRN" + new Date().getTime() + ".zip";
			}
			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
		return fileName;
	}

	@Override
	public List<EInvoiceHeader> getIrnDownloadData(String finYear, String divId, String finYearFlag, Long loc_id)
			throws Exception {
		// TODO Auto-generated method stub
		return einvoicerepo.getIrnDownloadData(finYear, divId, finYearFlag, loc_id);
	}

	@Override
	public List<Object> getEwayBillDownloadNumbers(String finYear, String divId, String fromTrn, String toTrn,
			String finYearFlag) throws Exception {
		// TODO Auto-generated method stub
		return einvoicerepo.getEwayBillDownloadNumbers(finYear, divId, fromTrn, toTrn, finYearFlag);
	}

	@Override
	public EwayBillPrintData setEwayBillDownloadData(List<String> ewbNos) throws Exception {
		EwayBillPrintData data = new EwayBillPrintData();
		boolean isfirst = true;
		for (String ewbNo : ewbNos) {
			if (isfirst == true) {
				data.setEwb_numbers(new ArrayList<Long>());
			}
			data.getEwb_numbers().add(Long.valueOf(ewbNo));
			data.setPrint_type("BASIC");
			isfirst = false;
		}
		return data;
	}

	@Override
	public String downloadFile(String saveDir, String targetURL, String urlParameters, String gstNo, String ownerId,
			String authToken, int ewbCount) throws IOException {

		String fileName = "";
		URL url = new URL(targetURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("POST");
		httpConn.setUseCaches(false);
		httpConn.setDoOutput(true);
		httpConn.setRequestProperty("x-cleartax-auth-token", authToken);
		httpConn.setRequestProperty("x-cleartax-product", "EInvoice");
		httpConn.setRequestProperty("owner_id", ownerId);
		httpConn.setRequestProperty("gstin", gstNo);
		httpConn.setRequestProperty("Accept", "application/json");
		httpConn.setRequestProperty("Content-Type", "application/json");
		// Send request
		System.out.println("paramers" + urlParameters);
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

		DataOutputStream os = new DataOutputStream(httpConn.getOutputStream());
		os.write(postData);
		int responseCode = httpConn.getResponseCode();
		String saveFilePath = null;

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = "demo12";
			}
			fileName = "EwayBill" + new Date().getTime() + ".zip";
			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
		return fileName;
	}

	@Override
	public String generateEinvoiceSuperTax(String targetURL, String urlParameters, String gstNo, String ownerId,
			String authToken, String methodType) {
		HttpURLConnection connection = null;
		System.out.println("urlParameters " + urlParameters);
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(methodType);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
//		    connection.setRequestProperty("x-cleartax-auth-token",authToken);
//		    connection.setRequestProperty("x-cleartax-product","EInvoice");
//		    connection.setRequestProperty("owner_id",ownerId);
//		    connection.setRequestProperty("gstin",gstNo);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("key", authToken);
			connection.setRequestProperty("Content-Type", "application/json");
			// Send request
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(urlParameters);
			writer.flush();
			writer.close();
			os.close();
			// Get Response
			InputStream is = null;
			int statusCode = connection.getResponseCode();
			if (statusCode >= 200 && statusCode < 400) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println("raw response " + response.toString());
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancelIrnSuperTax(IrnCancelResponseSuperTax data, String comp_cd) throws Exception {
		// TODO Auto-generated method stub
		EInvoiceHeader header = null;
		for (IrnCancelDataSuperTax res : data.getInvoices()) {
			if (res.getSuccess()) {
				header = einvoicerepo.getEInvoiceHeaderByIrn(res.getIrn());
				header.setIrncancel("Y");
				transactionalRepository.update(header);
				// hsession.update(header);
				// hsession.flush();
			}

		}
	}

	@Override
	public void EwayBillCancelSuperTax(EwayBillCancelResponseSuperTax data, String comp_cd) throws Exception {
		// TODO Auto-generated method stub
		// get by ewaybill no
		EInvoiceHeader header = einvoicerepo.getEInvoiceHeaderByEWayBillNo(data.getEwaybill_number());
		header.setEwaybillcancel("Y");
		transactionalRepository.update(header);
		// hsession.update(header);
		// hsession.flush();
	}

	@Override
	public String downloadFileForSuperTax(String saveDir, String targetURL, String urlParameters, String gstNo,
			String ownerId, String authToken, int ewbCount) throws IOException {

		String fileName = "";
		URL url = new URL(targetURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("POST");
		httpConn.setUseCaches(false);
		httpConn.setDoOutput(true);
		httpConn.setRequestProperty("key", authToken);
		httpConn.setRequestProperty("Accept", "application/json");
		httpConn.setRequestProperty("Content-Type", "application/json");
		// Send request
		System.out.println("paramers" + urlParameters);
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

		DataOutputStream os = new DataOutputStream(httpConn.getOutputStream());
		os.write(postData);
		int responseCode = httpConn.getResponseCode();
		String saveFilePath = null;

		InputStream is = null;
		if (responseCode >= 200 && responseCode < 400) {
			is = httpConn.getInputStream();
		} else {
			is = httpConn.getErrorStream();
		}
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}

		rd.close();
		httpConn.disconnect();

		System.out.println("raw response " + response.toString());

//	    EInvoiceWayBillDwnldResponseSuprTax wayBillPdf = new Gson().fromJson(response.toString(),
//				new TypeToken<EInvoiceWayBillDwnldResponseSuprTax>() {
//				}.getType());
//	    
//	    if(file_url.ge)
//	    String file_url = wayBillPdf.getFilename();
//	    
//	    file_url =  file_url.replace("\\/", "/");
//	    System.out.println(file_url);
//	    URL file = new URL(file_url);
////	    BufferedReader in = new BufferedReader(
////	    new InputStreamReader(file.openStream()));
//	    
//	    
//	    fileName="EwayBill"+new Date().getTime()+".pdf";
//	    saveFilePath = saveDir + File.separator + fileName;
//	 // opens an output stream to save into file
//        File fileObj = new File(saveFilePath);
//	    InputStream webIS = url.openStream();
//	    FileOutputStream fo = new FileOutputStream(fileObj);
//	    int c = 0;
//	    do {
//	        c = webIS.read();
//	        System.out.println("==============> " + c);
//	        if (c !=-1) {
//	            fo.write((byte) c);
//	        }
//	    } while(c != -1);
//
//	    webIS.close();
//	    fo.close();

//        // always check HTTP response code first
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//        	
//            fileName="EwayBill"+new Date().getTime()+".pdf";
//            System.out.println("fileName = " + fileName);
// 
//            InputStream inputStream = httpConn.getInputStream();
//            saveFilePath = saveDir + File.separator + fileName;
//           
//            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
// 
//            
//            outputStream.close();
//            inputStream.close();
// 
//            System.out.println("File downloaded");
//        } else {
//            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
//        }

		return response.toString();
	}

	@Override
	public List<EInvoiceCancelDataSuperTax> setEInvoiceCancelDataSuperTax(List<String> irns) throws Exception {
		EInvoiceCancelDataSuperTax data = null;
		List<EInvoiceCancelDataSuperTax> cancelList = new ArrayList<EInvoiceCancelDataSuperTax>();
		for (String irn : irns) {
			data = new EInvoiceCancelDataSuperTax();
			data.setIrn(irn);
			data.setCnlRsn("2");
			data.setCnlRem("Data Entry Mistake");
			cancelList.add(data);
		}
		return cancelList;
	}

	@Override
	public String generateEwaybillSuperTax(String targetURL, String urlParameters, String gstNo, String ownerId,
			String authToken, String methodType) {
		HttpURLConnection connection = null;
		System.out.println("urlParameters " + urlParameters);
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(methodType);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
//		    connection.setRequestProperty("x-cleartax-auth-token",authToken);
//		    connection.setRequestProperty("x-cleartax-product","EInvoice");
//		    connection.setRequestProperty("owner_id",ownerId);
//		    connection.setRequestProperty("gstin",gstNo);
			connection.setRequestProperty("key", authToken);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			// Send request
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(urlParameters);
			writer.flush();
			writer.close();
			os.close();
			// Get Response
			InputStream is = null;
			int statusCode = connection.getResponseCode();
			if (statusCode >= 200 && statusCode < 400) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println("response " + response.toString());
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}

		}
	}

	@Override
	public void saveEWayBillResponseIrn(EInvoiceByIrnResponseSuperTax list, String comp_cd, String divId)
			throws Exception {
		EInvoiceHeader header = null;
		System.out.println("********");
		for (EInoviceRespSuperTax data : list.getEwayBill()) {
			if (data.getSuccess()) {
				header = einvoicerepo.getEInvoiceHeaderByIrn(data.getIrn());
				header.setTrn_ewaybillno(data.getEwbNo());
				header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getEwbDate()));
				header.setTrn_ewaybillvalid(Utility.getDateformatYYYYDDMMHH(data.getValidUpto()));
				transactionalRepository.update(header);
			}
		}
//			for(EInvoiceByIrnResponseSuperTax data :list) {
//				header=einvoicerepo.getEInvoiceHeaderByIrn(data.getIrn());
//				header.setTrn_ewaybillno(data.getGovt_response().getEwbNo());
//				header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
//				header.setTrn_ewaybilldt(Utility.getDateformatYYYYDDMMHH(data.getGovt_response().getEwbDt()));
//				transactionalRepository.update(header);
//			}
	}

	@Override
	public List<EInvoiceByIrnDataSuperTax> generateEWayBillData(String finYearFlag, String finYearId, String eway_bill,
			String divId, String from, String to) {
		List<EInvoiceByIrnDataSuperTax> finalList = null;
		EInvoiceByIrnDataSuperTax data = null;
		Object[] arr = null;

		try {
			List<Object> list = einvoicerepo.getChallanDetailsHavingIrnSuperTax(from, to, finYearId, divId);
			if (list != null && list.size() > 0) {
				finalList = new ArrayList<EInvoiceByIrnDataSuperTax>();
				for (Object obj : list) {
					if (finalList.size() >= 25) {
						break;
					}
					arr = (Object[]) obj;
					data = new EInvoiceByIrnDataSuperTax();
					data.setIrn(arr[1].toString());

					data.getDocDtls().setNo(arr[2].toString());
					data.getDocDtls().setDt(arr[3].toString());
					data.getDocDtls().setTyp(arr[4].toString());

					data.getSellerDtls().setGstin(arr[5].toString());

					data.getEwbDtls().setTransId(arr[6]!=null? arr[6].toString():"");
					data.getEwbDtls().setTransName(arr[7].toString().trim());
					data.getEwbDtls().setTransMode("ROAD");
					// data.getEwbDtls().setTransMode(arr[8].toString());
					data.getEwbDtls().setDistance(arr[9].toString());
					data.getEwbDtls().setTransDocNo(arr[10].toString().trim());
					data.getEwbDtls().setTransDocDt(arr[11].toString());
					// data.getEwbDtls().setTransDocDt("22/12/2023");
					data.getEwbDtls().setVehNo(arr[12].toString());
					data.getEwbDtls().setVehType(arr[13].toString());

					finalList.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return finalList;
	}

	@Override
	public String cancelEwayBillSuperTax(String targetURL, String urlParameters, String gstNo, String ownerId,
			String authToken) {
		HttpURLConnection connection = null;
		try {
			// Create connection
			System.out.println("Parameterd " + urlParameters);
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
//		    connection.setRequestProperty("x-cleartax-auth-token",authToken);
//		    connection.setRequestProperty("x-cleartax-product","EInvoice");
//		    connection.setRequestProperty("owner_id",ownerId);
//		    connection.setRequestProperty("gstin",gstNo);
			connection.setRequestProperty("key", authToken);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			// Send request
			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(urlParameters);
			writer.flush();
			writer.close();
			os.close();
			// Get Response
			InputStream is = null;
			int statusCode = connection.getResponseCode();
			if (statusCode >= 200 && statusCode < 400) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			System.out.println("response " + response.toString());
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	public List<EInvoiceTransaction> generateEInvoiceStockist(String finYearFlag, String finYearId, String eway_bill,
			String divId, String from, String to, String locid) {
		List<EInvoiceTransaction> finalList = null;
		try {
			
			SG_d_parameters_details zero_val_param = paramService.getParaDetailsByParaType(ZERO_VAL_EINV_AND_DEL_CHLN)
					.get(0);
			String str_zero = "0";
			String newDocNumber = "new";
			String oldDocNumber = "old";
			List<EInvoiceGenerateData> list = einvoicerepo.getEInvoiceGenerateDataStockist(finYearFlag, finYearId,
					divId, from, to, locid);
			
			List<EInvoiceData> transaction = new ArrayList<EInvoiceData>();
			EInvoiceTransaction tran = null;
			EInvoiceData details = null;
			boolean isfirst = true;
			boolean isbreak = false;
			int i = 0;
			for (EInvoiceGenerateData data : list) {
				newDocNumber = data.getDocDtls_NO();
				if (!newDocNumber.trim().equals(oldDocNumber)) {
					System.out.println("newDocNumber " + newDocNumber);
					if (isfirst == false) {
						transaction.add(details);
//						if(transaction.size()==10){
//							isbreak=true;
//							break;
//						}
					}
					i = 0;
					details = new EInvoiceData();
					details.setItemList(new ArrayList<EInvoiceData.ItemList>());
					details.setVersion("1.1");
					// TranDtls
					details.setTranDtls(details.new TranDtls());
					details.getTranDtls().setTaxSch(data.getTaxType());
					details.getTranDtls().setSupTyp(data.getSupTyp());
					details.getTranDtls().setEcmGstin(data.getEcmGstin());
					details.getTranDtls().setIgstOnIntra(data.getIgstOnIntra());
					details.getTranDtls().setRegRev(data.getRegRev());
					// DocDtls
					details.setDocDtls(details.new DocDtls());
					details.getDocDtls().setTyp(data.getDocDtls_TYP());
					details.getDocDtls().setNo(data.getDocDtls_NO().replace("\\s+", ""));
					details.getDocDtls().setDt(data.getDocDtls_DT());
					// SellerDtls
					details.setSellerDtls(details.new SellerDtls());
					details.getSellerDtls().setGstin(data.getSellerDtls_GSTIN().trim().substring(0, 15));
					details.getSellerDtls().setLglNm(data.getSellerDtlsLglNm());
					details.getSellerDtls().setTrdNm(data.getSellerDtlsLglNm());
					details.getSellerDtls().setAddr1(data.getSellerDtlsAddr1());
					details.getSellerDtls().setAddr2(data.getSellerDtlsAddr2());
					details.getSellerDtls().setLoc(data.getSellerDtlsloc());
					details.getSellerDtls().setPin(data.getSellerDtlsPin());
					details.getSellerDtls().setStcd(data.getSellerDtlsStcd());
					details.getSellerDtls().setPh("1234567");
					// details.getSellerDtls().setPh(data.getSellerDtlsPh()==null ||
					// data.getSellerDtlsPh().isEmpty()?"1234567":data.getSellerDtlsPh().replaceAll("[^0-9]",
					// ""));
					details.getSellerDtls().setEm(data.getSellerDtlsEm().isEmpty() ? null : data.getSellerDtlsEm());
					// BuyerDtls
					details.setBuyerDtls(details.new BuyerDtls());
					details.getBuyerDtls()
							.setGstin(data.getBuyerDtlsGstin().trim().length() > 15
									? data.getBuyerDtlsGstin().trim().substring(0, 15)
									: data.getBuyerDtlsGstin().trim());
					details.getBuyerDtls().setLglNm(data.getBuyerDtlsLglNm());
					details.getBuyerDtls().setTrdNm(data.getBuyerDtlsTrdNm());
					details.getBuyerDtls().setPos(data.getBuyerDtlsPos());
					details.getBuyerDtls().setAddr1(data.getBuyerDtlsAddr1());
					details.getBuyerDtls().setAddr2(data.getBuyerDtlsAddr2());
					details.getBuyerDtls().setLoc(data.getBuyerDtlsLoc());
					details.getBuyerDtls().setPin(data.getBuyerDtlsPin().trim());
					details.getBuyerDtls().setStcd(data.getBuyerDtlsStcd());
					details.getBuyerDtls().setPh("1234567");
					// details.getBuyerDtls().setPh(data.getBuyerDtlsPh()==null ||
					// data.getBuyerDtlsPh().isEmpty()?"1234567":data.getBuyerDtlsPh().replaceAll("[^0-9]",
					// ""));
					details.getBuyerDtls().setEm(data.getBuyerDtlsem().isEmpty() ? null : data.getBuyerDtlsem());
					// DispDtls
					details.setDispDtls(details.new DispDtls());
					details.getDispDtls().setNm(data.getDispDtlsNm());
					details.getDispDtls().setAddr1(data.getDispDtlsAddr1());
					details.getDispDtls().setAddr2(data.getDispDtlsAddr2());
					details.getDispDtls().setLoc(data.getDispDtlsLoc());
					details.getDispDtls().setPin(data.getDispDtlsPin());
					details.getDispDtls().setStcd(data.getDispDtlsStcd());
					// ShipDtls
					details.setShipDtls(details.new ShipDtls());
					details.getShipDtls()
							.setGstin(data.getShipDtlsGstin().trim().length() > 15
									? data.getShipDtlsGstin().trim().substring(0, 15)
									: data.getShipDtlsGstin().trim());
					details.getShipDtls().setLglNm(data.getShipDtlsLglnm());
					details.getShipDtls().setTrdNm(data.getShipDtlsTrdnm());
					details.getShipDtls().setAddr1(data.getShipDtlsAddr1());
					details.getShipDtls().setAddr2(data.getShipDtlsAddr2());
					details.getShipDtls().setLoc(data.getShipDtlsLoc());
					details.getShipDtls().setPin(data.getShipDtlsPin().trim());
					details.getShipDtls().setStcd(data.getShipDtlsStcd());

					// ValDtls
					details.setValDtls(details.new ValDtls());
					if (zero_val_param != null && zero_val_param.getSgprmdet_text1().equalsIgnoreCase("Y")) {
						details.getValDtls().setAssVal(str_zero);
						details.getValDtls().setCgstVal(str_zero);
						details.getValDtls().setSgstVal(str_zero);
						details.getValDtls().setIgstVal(str_zero);
						details.getValDtls().setCesVal(str_zero);
						details.getValDtls().setStCesVal(str_zero);
						details.getValDtls().setDiscount(str_zero);
						details.getValDtls().setOthChrg(str_zero);
						details.getValDtls().setRndOffAmt(str_zero);
						details.getValDtls().setTotInvVal(str_zero);
						details.getValDtls().setTotInvValFc(str_zero);
					} else {
						// since gst values , rates are to be passed as zero //removed for pfizer
						// articles
						details.getValDtls().setAssVal(data.getValDtlsAssVal());
						details.getValDtls().setCgstVal(data.getValDtlsCgstVal());
						details.getValDtls().setSgstVal(data.getValDtlsSgstVal());
						details.getValDtls().setIgstVal(data.getValDtlsIgstVal());
						details.getValDtls().setCesVal(data.getValDtlsCesVal());
						details.getValDtls().setStCesVal(data.getValDtlsStCesVal());
						details.getValDtls().setDiscount(data.getValDtlsDiscount());
						details.getValDtls().setOthChrg(data.getValDtlsOthChrg());
						details.getValDtls().setRndOffAmt(data.getValDtlsRndOffAmt());
						details.getValDtls().setTotInvVal(data.getValDtlsTotInvVal());
						details.getValDtls().setTotInvValFc(data.getValDtlsTotInvValFc());
					}

					// PayDtls
					details.setPayDtls(details.new PayDtls());
					details.getPayDtls().setNm(data.getPayDtlsNm());
					details.getPayDtls().setAccDet(data.getPayDtlsNm());
					details.getPayDtls().setMode(data.getPayDtlsMode());
					details.getPayDtls().setFininsBr(data.getPayDtlsFinInsBr());
					details.getPayDtls().setPayInstr(data.getPayDtlsPayTerm());
					details.getPayDtls().setPayInstr(data.getPayDtlsInStr());
					details.getPayDtls().setCrTrn(data.getPayDtlsCrTrn());
					details.getPayDtls().setDirDr(data.getPayDtlsDirDr());
					details.getPayDtls().setCrDay(data.getPayDtlsCrDay());
					details.getPayDtls().setPaidAmt(data.getPayDtlsPaidAmt());
					details.getPayDtls().setPaymtDue(data.getPayDtlsPaymtDue());
					// RefDtls
					details.setRefDtls(details.new RefDtls());
					// ExpDtls
					details.setExpDtls(details.new ExpDtls());
					details.getExpDtls().setShipBNo(data.getExpDtlsShipBno());
					details.getExpDtls().setShipBDt(data.getExpDtlsShipBdt());
					details.getExpDtls().setPort(data.getExpDtlsPort());
					details.getExpDtls().setRefClm(data.getExpDtlsRefClm());
					details.getExpDtls().setForCur(data.getExpDtlsForCur());
					details.getExpDtls().setCntCode(data.getExpDtlsCntCode());
					System.out.println("eway_bill :: " + eway_bill);
					// EwbDtls
					if (eway_bill.equals("Y")) {
						System.out.println("In EwayBill Trans " + data.getEwbDtlsTransId());
						// removed for pfizer articles
						details.setEwbDtls(details.new EwbDtls());
						if (data.getEwbDtlsTransId() != null && data.getEwbDtlsTransId().trim().length() > 0) {
							// PART A
							details.getEwbDtls().setDistance(data.getEwbDtlsDistance());
							details.getEwbDtls().setTransId(data.getEwbDtlsTransId().trim());
							details.getEwbDtls().setTransName(data.getEwbDtlsTransName());

							// PART B
							if (data.getEwbDtlsTransDocNo() != null
									&& data.getEwbDtlsTransDocNo().trim().length() > 0) {
								details.getEwbDtls().setTransDocNo(data.getEwbDtlsTransDocNo());
								details.getEwbDtls().setTransDocDt(data.getEwbDtlsTransDocDt());
								details.getEwbDtls().setTransMode(data.getTransMode());
								details.getEwbDtls().setVehNo(data.getEwbDtlsVehNo());//
								details.getEwbDtls().setVehType(data.getEwbDtlsVehType());//
							}
						} else {
							// generate by VehNo
							details.getEwbDtls().setDistance(data.getEwbDtlsDistance());
							details.getEwbDtls().setVehNo(data.getEwbDtlsVehNo());//
							details.getEwbDtls().setVehType(data.getEwbDtlsVehType());//
							details.getEwbDtls().setTransMode(data.getTransMode());//
						}
					}
				}
				// ItemList
				details.getItemList().add(details.new ItemList());

				details.getItemList().get(i).setSlNo(String.valueOf(i + 1));
				details.getItemList().get(i).setPrdDesc(data.getItemListPrdDesc());
				details.getItemList().get(i).setIsServc(data.getItemListIsServc());
				details.getItemList().get(i).setHsnCd(data.getItemListHsnCd().trim());
				details.getItemList().get(i).setBarcde(data.getItemListBarcde());
				details.getItemList().get(i).setQty(data.getItemListQty());
				details.getItemList().get(i).setFreeQty(data.getItemListFreeQty());
				details.getItemList().get(i).setUnit("OTH");
				details.getItemList().get(i).setGstRt(data.getItemListGstRt());
				details.getItemList().get(i).setCesRt(data.getItemListCesRt());
				details.getItemList().get(i).setStateCesRt(data.getItemListStateCesRt());
				details.getItemList().get(i).setOthChrg(data.getItemListOthChrg());
				details.getItemList().get(i).setOrdLineRef(data.getItemListOrdLineRef());
				details.getItemList().get(i).setOrgCntry(data.getItemListOrgCntry());
				details.getItemList().get(i).setPrdSlNo(data.getItemListPrdSlNo().trim());
				details.getItemList().get(i).setBchDtls(details.new ItemList().new BchDtls());
				details.getItemList().get(i).getBchDtls().setNm(data.getBchDtlsNm());
				details.getItemList().get(i).getBchDtls().setExpDt(data.getBchDtlsExpdt());
				details.getItemList().get(i).getBchDtls().setWrDt(data.getBchDtlsWrDt());

				if (zero_val_param != null && zero_val_param.getSgprmdet_text1().equalsIgnoreCase("Y")) {
					// setting all values to zero
					details.getItemList().get(i).setUnitPrice(str_zero);
					details.getItemList().get(i).setTotAmt(str_zero);
					details.getItemList().get(i).setDiscount(str_zero);
					details.getItemList().get(i).setPreTaxVal(str_zero);
					details.getItemList().get(i).setAssAmt(str_zero);
					details.getItemList().get(i).setIgstAmt(str_zero);
					details.getItemList().get(i).setCgstAmt(str_zero);
					details.getItemList().get(i).setSgstAmt(str_zero);
					details.getItemList().get(i).setCesAmt(str_zero);
					details.getItemList().get(i).setCesNonAdvlAmt(str_zero);
					details.getItemList().get(i).setStateCesAmt(str_zero);
					details.getItemList().get(i).setStateCesNonAdvlAmt(str_zero);
					details.getItemList().get(i).setTotItemVal(str_zero);
				} else {
					details.getItemList().get(i).setUnitPrice(data.getItemListUnitPrice());
					details.getItemList().get(i).setTotAmt(df.format(Double.parseDouble(data.getItemListTotamt())));
					details.getItemList().get(i).setDiscount(data.getItemListDiscount());
					details.getItemList().get(i)
							.setPreTaxVal(df.format(Double.parseDouble(data.getItemListPreTaxVal())));
					details.getItemList().get(i).setAssAmt(df.format(Double.parseDouble(data.getItemListAssAmt())));
					details.getItemList().get(i).setIgstAmt(data.getItemListIgstAmt());
					details.getItemList().get(i).setCgstAmt(data.getItemListCgstAmt());
					details.getItemList().get(i).setSgstAmt(data.getItemListSgstAmt());
					details.getItemList().get(i).setCesAmt(data.getItemListCesAmt());
					details.getItemList().get(i).setCesNonAdvlAmt("0");
					details.getItemList().get(i).setStateCesAmt(data.getItemListStateCesAmt());
					details.getItemList().get(i).setStateCesNonAdvlAmt(data.getItemListStateCesNonAdvlAmt());
					details.getItemList().get(i)
							.setTotItemVal(df.format(Double.parseDouble(data.getItemListTotItemVal())));
				}

				i++;
				isfirst = false;
				oldDocNumber = newDocNumber;
			}
			if (isbreak == false) {
				transaction.add(details);
			}
			finalList = new ArrayList<EInvoiceTransaction>();
			for (EInvoiceData data : transaction) {
				tran = new EInvoiceTransaction();
				tran.setTransaction(data);
				finalList.add(tran);
			}
			System.out.println("object " + new Gson().toJson(finalList));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());
		}
		return finalList;
	}

	@Override
	public List<EInvoiceByIrnData> generateEInvoiceByIrnStockist(Long loc_id, String finYearFlag, String finYearId,
			String eway_bill, String divId, String from, String to) {
		List<EInvoiceByIrnData> finalList = null;
		EInvoiceByIrnData data = null;
		Object[] arr = null;

		try {
			List<Object> list = einvoicerepo.getChallanDetailsHavingIrnStockist(loc_id, from, to, finYearId, divId);
			if (list != null && list.size() > 0) {
				finalList = new ArrayList<EInvoiceByIrnData>();
				for (Object obj : list) {
					arr = (Object[]) obj;
					data = new EInvoiceByIrnData();
					data.setIrn(arr[1].toString());

					if (arr[2] != null)
						data.setDistance(new DecimalFormat("#").format(Double.valueOf(arr[2].toString())));
					else
						data.setDistance("0");

					if (arr[4] != null) {
						// transporter id is present
						// update PART A
						data.setTransId(arr[4].toString().trim());
						data.setTransName(arr[5].toString().trim());

						// PART B
						if (arr[7] != null && arr[7].toString().trim().length() > 0) {
							data.setTransMode(arr[3].toString());//
							data.setTransDocDt(arr[6].toString());//
							data.setTransDocNo(arr[7].toString().trim());
							data.setVehNo(arr[8].toString().trim());
							data.setVehType(arr[9].toString().trim());
							data.setTransMode(arr[3].toString().trim());
						}
					} else {
						data.setVehNo(arr[8].toString().trim());
						data.setVehType(arr[9].toString().trim());
						data.setTransMode(arr[3].toString().trim());
					}

					data.setChallan_No(arr[15].toString());
					// ship dtls
					data.setExpShipDtls(new ExpShipDtls());
					data.getExpShipDtls().setAddr1(arr[10].toString());
					data.getExpShipDtls().setAddr2(arr[11].toString());
					data.getExpShipDtls().setLoc(arr[12].toString());
					data.getExpShipDtls().setPin(arr[13].toString().trim());
					data.getExpShipDtls().setStcd(arr[14].toString());

					finalList.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

		return finalList;
	}

	@Override
	public List<Object> getIrnCancelDataStockist(Long loc_id, String fromTrnId, String toTrnId, String divId,
			String finyearflag, String fin_year) throws Exception {
		return einvoicerepo.getIrnCancelDataStockist(loc_id, fromTrnId, toTrnId, divId, finyearflag, fin_year);
	}

	@Override
	public List<Dispatch_Header> getChallanDetailsStockist(String finYear, String divId, String finyearflag,
			Long loc_id) throws Exception {
		return einvoicerepo.getChallanDetailsStockist(finYear, divId, finyearflag, loc_id);
	}

	@Override
	public List<EInvoiceHeaderStockist> getIrnChallansWithoutEwayStockist(String finYear, String divId,
			String finYearFlag, Long loc_id) throws Exception {
		return einvoicerepo.getIrnChallansWithoutEwayStockist(finYear, divId, finYearFlag, loc_id);
	}

	@Override
	public List<EInvoiceHeaderStockist> getEwayBillCancelStockist(String finYear, String divId, String finYearFlag,
			Long loc_id) {
		return einvoicerepo.getEwayBillCancelStockist(finYear, divId, finYearFlag, loc_id);
	}

	@Override
	public List<EInvoiceHeaderStockist> getIrnGencancelDataStockist(String finYear, String divId, Long loc_id)
			throws Exception {
		return einvoicerepo.getIrnGencancelDataStockist(finYear, divId, loc_id);
	}

	@Override
	public List<E_invoice_report> getE_invoiceReportData(String string, String string2, String string3) throws Exception {
		
		
		return einvoicerepo.getE_invoiceReportData( string,  string2,  string3);
	}

	@Override
	public String genarateExcel(List<E_invoice_report> invoicelist) {
		// TODO Auto-generated method stub
		 String fileName;
		 String file = "";
		 String xlsPath;
		Workbook wwbook = null;
		 String filename = null;
		try {
		 String comp_name = "MDL";
		 long l = new Date().getTime();
		 file = "E_Invoice_Report" + l + ".xlsx";
		 fileName = file;
		 
			String filePath = REPORT_FILE_PATH + file;
		 xlsPath = "\\excelDownload\\" + file;
		 System.out.println("Excel");
		 System.out.println("filePath : "+filePath);
		 
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		 	wwbook = new XSSFWorkbook();
		    Sheet wsheet = wwbook.createSheet("E_Invoice");
		    wsheet.createFreezePane(0, 1);
		    ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
		    CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);

		    CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
		    
		    CellStyle Decimal = wwbook.createCellStyle();
		    Decimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
		    
		    XSSFCellStyle right = (XSSFCellStyle) wwbook.createCellStyle();
		    right.setAlignment(HorizontalAlignment.RIGHT);
		    
		    int col = 0, row = 0;
		    
		    String[] heading = {"LocationGstin","LocationName","ReturnPeriod","LiabilityDischargeReturnPeriod","ItcClaimReturnPeriod",
		    		"Purpose","AutoPushOrGenerate","SupplyType","Irn","EwayBillNumber","GeneratedDate","DeliveredDate","ValidUpto","ExtendedTimes",
		    		"DocumentType","TransactionType","TaxpayerType","TransactionNature","TransactionTypeDescription","DocumentNumber","DocumentSeriesCode",
		    		"DocumentDate","BillFromGstin","BillFromLegalName","BillFromTradeName","BillFromVendorCode","BillFromAddress1","BillFromAddress2","BillFromCity",
		    		"BillFromStateCode","BillFromPincode","BillFromPhone","BillFromEmail","DispatchFromGstin","DispatchFromTradeName","DispatchFromVendorCode",
		    		"DispatchFromAddress1","DispatchFromAddress2","DispatchFromCity","DispatchFromStateCode","DispatchFromPincode","BillToGstin","BillToLegalName",
		    		"BillToTradeName","BillToVendorCode","BillToAddress1","BillToAddress2","BillToCity","BillToStateCode","BillToPincode","BillToPhone","BillToEmail",
		    		"ShipToGstin","ShipToLegalName","ShipToTradeName","ShipToVendorCode","ShipToAddress1","ShipToAddress2","ShipToCity","ShipToStateCode","ShipToPincode",
		    		"PaymentType","PaymentMode","PaymentAmount","AdvancePaidAmount","PaymentDate","PaymentRemarks","PaymentTerms","PaymentInstruction","PayeeName",
		    		"PayeeAccountNumber","PaymentAmountDue","Ifsc","CreditTransfer","DirectDebit","CreditDays","CreditAvailedDate","CreditReversalDate","RefDocumentRemarks",
		    		"RefDocumentPeriodStartDate","RefDocumentPeriodEndDate","RefPrecedingDocumentDetails","RefContractDetails","AdditionalSupportingDocumentDetails",
		    		"BillNumber","BillDate","PortCode","DocumentCurrencyCode","DestinationCountry","ExportDuty","Pos","DocumentValue","DocumentDiscount","DocumentOtherCharges",
		    		"DocumentValueInForeignCurrency","RoundOffAmount","DifferentialPercentage","ReverseCharge","ClaimRefund","UnderIgstAct","RefundEligibility","ECommerceGstin",
		    		"TdsGstin","PnrOrUniqueNumber","AvailProvisionalItc","OriginalGstin","OriginalStateCode","OriginalTradeName","OriginalDocumentType","OriginalDocumentNumber",
		    		"OriginalDocumentDate","OriginalReturnPeriod","OriginalTaxableValue","OriginalPortCode","TransportDateTime","TransporterId","TransporterName","TransportMode",
		    		"Distance","TransportDocumentNumber","TransportDocumentDate","VehicleNumber","VehicleType","ToEmailAddresses","ToMobileNumbers","JWOriginalDocumentNumber",
		    		"JWOriginalDocumentDate","JWDocumentNumber","JWDocumentDate","Custom1","Custom2","Custom3","Custom4","Custom5","Custom6","Custom7","Custom8","Custom9","Custom10",
		    		"SerialNumber","IsService","Hsn","ProductCode","ItemName","ItemDescription","NatureOfJWDone","Barcode","Uqc","Quantity","FreeQuantity","LossUnitOfMeasure","LossTotalQuantity",
		    		"Rate","CessRate","StateCessRate","CessNonAdvaloremRate","PricePerQuantity","DiscountAmount","GrossAmount","OtherCharges","TaxableValue","PreTaxValue","IgstAmount",
		    		"CgstAmount","SgstAmount","CessAmount","StateCessAmount","StateCessNonAdvaloremAmount","CessNonAdvaloremAmount","OrderLineReference","OriginCountry","ItemSerialNumber",
		    		"ItemTotal","ItemAttributeDetails","TaxType","BatchNameNumber","BatchExpiryDate","WarrantyDate","ItcEligibility","ItcIgstAmount","ItcCgstAmount","ItcSgstAmount",
		    		"ItcCessAmount","CustomItem1","CustomItem2","CustomItem3","CustomItem4","CustomItem5","CustomItem6","CustomItem7","CustomItem8","CustomItem9","CustomItem10"};
		    
		    
		    
		    
		    XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
		    XSSFCell cell = hrow.createCell(col);

		    
		    hrow = (XSSFRow) wsheet.createRow(row);
		    for(String s:heading) {
		    cell = hrow.createCell(col);
		    cell.setCellValue(s);
		    cell.setCellStyle(columnHeading);
		    col++;
		    }
		    
		    row++;
		    col = 0;
		    
		    for(E_invoice_report s:invoicelist) {
		    	  hrow = (XSSFRow) wsheet.createRow(row);
		    	 
		    	  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getLocationgstin());
				//  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getLocationname());
			//	  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getReturnperiod());
			//	  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getLiabilitydischargereturnperiod());
			//	  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItcclaimreturnperiod());
			//	  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPurpose());
			//	  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getAutopushorgenerate());
			//	  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getSupplytype());
			//	  cell.setCellStyle(columnHeading);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getIrn());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getEwaybillnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getGenerateddate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDelivereddate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getValidupto());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getExtendedtimes());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumenttype());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransactiontype());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTaxpayertype());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransactionnature());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransactiontypedescription());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumentnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumentseriescode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumentdate()!=null?sdf.format(s.getDocumentdate()):"");
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromgstin());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromlegalname());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromtradename());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromvendorcode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromaddress1());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromaddress2());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromcity());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromstatecode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfrompincode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromphone());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillfromemail());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDispatchfromgstin());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDispatchfromtradename());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDispatchfromvendorcode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDispatchfromaddress1());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDispatchfromaddress2());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDispatchfromcity());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDispatchfromstatecode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDispatchfrompincode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltogstin());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltolegalname());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltotradename());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltovendorcode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltoaddress1());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltoaddress2());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltocity());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltostatecode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltopincode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				 
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltophone());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilltoemail());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptogstin());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptolegalname());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptotradename());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptovendorcode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptoaddress1());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptoaddress2());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptocity());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptostatecode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getShiptopincode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPaymenttype());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPaymentmode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPaymentamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getAdvancepaidamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPaymentdate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPaymentremarks());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPaymentterms());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPaymentinstruction());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPayeename());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPayeeaccountnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPaymentamountdue());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getIfsc());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCredittransfer());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDirectdebit());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCreditdays());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCreditavaileddate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCreditreversaldate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getRefdocumentremarks());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getRefdocumentperiodstartdate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getRefdocumentperiodenddate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getRefprecedingdocumentdetails());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getRefcontractdetails());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getAdditionalsupportingdocumentdetails());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBillnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBilldate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPortcode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumentcurrencycode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDestinationcountry());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getExportduty());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPos());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumentvalue().doubleValue());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumentdiscount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumentothercharges());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDocumentvalueinforeigncurrency());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getRoundoffamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDifferentialpercentage());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getReversecharge());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getClaimrefund());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getUnderigstact());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getRefundeligibility());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getEcommercegstin());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTdsgstin());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPnroruniquenumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getAvailprovisionalitc());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOriginalgstin());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOriginalstatecode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOriginaltradename());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOriginaldocumenttype());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOriginaldocumentnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOriginaldocumentdate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOriginalreturnperiod());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOriginaltaxablevalue());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPortcode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransportdatetime());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransporterid());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransportername());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransportmode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDistance());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransportdocumentnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTransportdocumentdate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getVehiclenumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getVehicletype());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getToemailaddresses());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTomobilenumbers());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getJworiginaldocumentnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getJworiginaldocumentdate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getJwdocumentnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getJwdocumentdate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom1());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom2());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom3());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom4());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom5());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom6());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom7());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom8());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom9());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustom10());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getSerialnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getIsservice());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getHsn());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getProductcode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItemname());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItemdescription());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getNatureofjwdone());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBarcode());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getUqc());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getQuantity().doubleValue());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getFreequantity());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getLossunitofmeasure());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getLosstotalquantity());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getRate().doubleValue());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCessrate());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getStatecessrate());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCessnonadvaloremrate());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPriceperquantity().doubleValue());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getDiscountamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getGrossamount().doubleValue());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOthercharges());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTaxablevalue().doubleValue());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getPretaxvalue());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getIgstamount().doubleValue());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCgstamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getSgstamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCessamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getStatecessamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getStatecessnonadvaloremamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCessnonadvaloremamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOrderlinereference());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getOrigincountry());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItemserialnumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItemtotal().doubleValue());
				  cell.setCellStyle(Decimal);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItemattributedetails());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getTaxtype());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBatchnamenumber());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getBatchexpirydate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getWarrantydate());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItceligibility());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItcigstamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItccgstamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItcsgstamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getItccessamount());
				  cell.setCellStyle(right);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem1());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem2());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem3());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem4());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem5());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem6());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem7());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem8());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem9());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  cell = hrow.createCell(col);
		    	  cell.setCellValue(s.getCustomitem10());
		//		  cell.setCellStyle(columnHeading);
				  col++;
				  
				  col=0;
				  row++;
		    }

		    FileOutputStream fileOut = new FileOutputStream(filePath.toString());
		    // lockAll(wsheet, "password");
		    wwbook.write(fileOut);
		    fileOut.close();

		    System.out.println("Excel Created");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		    
		return file;
		
	}

}
