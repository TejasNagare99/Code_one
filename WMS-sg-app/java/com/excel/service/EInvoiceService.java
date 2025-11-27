package com.excel.service;
import java.io.IOException;
import java.util.List;
import com.excel.bean.EInvoiceByIrnData;
import com.excel.bean.EInvoiceByIrnDataSuperTax;
import com.excel.bean.EInvoiceByIrnResponse;
import com.excel.bean.EInvoiceByIrnResponseSuperTax;
import com.excel.bean.EInvoiceCancelData;
import com.excel.bean.EInvoiceCancelDataSuperTax;
import com.excel.bean.EInvoiceResponse;
import com.excel.bean.EInvoiceResponseSuperTaxWrapper;
import com.excel.bean.EInvoiceTransaction;
import com.excel.bean.EInvoiceTransactionSuperTax;
import com.excel.bean.EwayBillCancelData;
import com.excel.bean.EwayBillCancelResponse;
import com.excel.bean.EwayBillCancelResponseSuperTax;
import com.excel.bean.EwayBillPrintData;
import com.excel.bean.IrnCancelResponse;
import com.excel.bean.IrnCancelResponseSuperTax;
import com.excel.model.Dispatch_Header;
import com.excel.model.DivMaster;
import com.excel.model.EInvoiceHeader;
import com.excel.model.EInvoiceHeaderStockist;
import com.excel.model.E_invoice_report;
import com.excel.model.Period;
import com.excel.model.Sum_Disp_Header;
public interface EInvoiceService {
	public List<EInvoiceTransaction> generateEInvoice(String finYearFlag,String finYearId,String eway_bill,String divId,String from,String to);
	public  String generateEinvoice(String targetURL, String urlParameters,String gstNo,String ownerId,String authToken,String methodType) ;
	public void saveEInvoiceResponse(List<EInvoiceResponse> list,String comp_cd,String divId,String finYear,String finYearflag) throws Exception;
	public List<DivMaster> getteams(String emp_id) throws Exception;
	public List<Period> getperiod() throws Exception;
	public Period getstartdate() throws Exception;
	public List<EInvoiceByIrnData> generateEInvoiceByIrn(String finYearFlag,String finYearId,String eway_bill,String divId,String from,String to);
	public void saveEInvoiceResponseIrn(List<EInvoiceByIrnResponse> list,String comp_cd,String divId) throws Exception;
	public List<EInvoiceCancelData> setEInvoiceCancelData(List<String> irns) throws Exception;
	public void cancelIrn(List<IrnCancelResponse> data,String comp_cd) throws Exception;
	 public List<Object> getIrnCancelData(String fromTrnId,String toTrnId,String divId,String finyearflag) throws Exception;
	 public EwayBillCancelData setEwayBillCancelData(String ewbNo) throws Exception;
	 public  String cancelEwayBill(String targetURL, String urlParameters,String gstNo,String ownerId,String authToken);
	 public void cancelEwayBill(EwayBillCancelResponse data,String comp_cd) throws Exception;
	 public List<Sum_Disp_Header> getChallanDetails(String finYear,String divId,String finyrflag,Long loc_id)throws Exception;
	 public List<EInvoiceHeader> getIrnChallansWithoutEway(String finYear,String divId,String finYearFlag,Long loc_id) throws Exception;
	 
	 public List<EInvoiceHeader> getIrnGencancelData(String finYear,String divId,Long loc_id) throws Exception;
	 
	 public List<EInvoiceHeader> getEwayBillCancel(String finYear,String divId,String finYearFlag,Long loc_id);
	 
	 public List<EInvoiceHeader> getIrnDownloadData(String finYear,String divId,String finYearFlag,Long loc_id) throws Exception;
	 
	 public String downloadFileIrn(String saveDir,String targetURL, String urlParameters,String gstNo,String ownerId,String authToken,int irnCount)
	            throws IOException;
	 
	 public List<Object> getEwayBillDownloadNumbers(String finYear,String divId,String fromTrn,String toTrn,String finYearFlag)throws Exception;

	 public EwayBillPrintData setEwayBillDownloadData(List<String> ewbNos) throws Exception;
	 public String downloadFile(String saveDir,String targetURL, String urlParameters,String gstNo,String ownerId,String authToken,int ewbCount)
	            throws IOException;
	 
	 //for super tax
	 public EInvoiceTransactionSuperTax generateEInvoiceSuperTax(String finYearFlag,String finYearId,String eway_bill,String divId,String from,String to);
	 public  String generateEinvoiceSuperTax(String targetURL, String urlParameters,String gstNo,String ownerId,String authToken,String methodType) ;
	 public void saveEInvoiceResponseSuperTax(EInvoiceResponseSuperTaxWrapper wrapperObj,String comp_cd,String divId,String finYear,String finYearflag) throws Exception;
	 public void cancelIrnSuperTax(IrnCancelResponseSuperTax data,String comp_cd) throws Exception;
	 public void EwayBillCancelSuperTax(EwayBillCancelResponseSuperTax data,String comp_cd) throws Exception;
	 public String downloadFileForSuperTax(String saveDir,String targetURL, String urlParameters,String gstNo,String ownerId,String authToken,int ewbCount)
	            throws IOException;
	 public List<EInvoiceCancelDataSuperTax> setEInvoiceCancelDataSuperTax(List<String> irns) throws Exception;
	 public  String generateEwaybillSuperTax(String targetURL, String urlParameters,String gstNo,String ownerId,String authToken,String methodType) ;
	 public void saveEWayBillResponseIrn(EInvoiceByIrnResponseSuperTax list,String comp_cd,String divId) throws Exception;
	 public List<EInvoiceByIrnDataSuperTax> generateEWayBillData(String finYearFlag,String finYearId,String eway_bill,String divId,String from,String to);
	 public  String cancelEwayBillSuperTax(String targetURL, String urlParameters,String gstNo,String ownerId,String authToken);
	
	 //for excellon
	 public List<EInvoiceTransaction> generateEInvoiceStockist(String finYearFlag, String finYearId, String eway_bill,
				String divId, String from, String to,String locid);
	 public List<EInvoiceByIrnData> generateEInvoiceByIrnStockist(Long loc_id,String finYearFlag,String finYearId,String eway_bill,String divId,String from,String to);
	 public List<Object> getIrnCancelDataStockist(Long loc_id,String fromTrnId,String toTrnId,
			 String divId,String finyearflag,String fin_year) throws Exception;
	 //for stockist data
	 public List<Dispatch_Header> getChallanDetailsStockist(String finYear, String divId, String finyearflag, Long loc_id)
				throws Exception;
	 public List<EInvoiceHeaderStockist> getIrnChallansWithoutEwayStockist(String finYear,String divId,String finYearFlag,Long loc_id) throws Exception;
	 public List<EInvoiceHeaderStockist> getEwayBillCancelStockist(String finYear,String divId,String finYearFlag,Long loc_id);
	 public List<EInvoiceHeaderStockist> getIrnGencancelDataStockist(String finYear,String divId,Long loc_id) throws Exception;
	public List<E_invoice_report> getE_invoiceReportData(String string, String string2, String string3) throws Exception;
	public String genarateExcel(List<E_invoice_report> invoicelist);

}
