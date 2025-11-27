package com.excel.repository;

import java.util.List;

import com.excel.model.Dispatch_Header;
import com.excel.model.DivMaster;
import com.excel.model.EInvoiceGenerateData;
import com.excel.model.EInvoiceHeader;
import com.excel.model.EInvoiceHeaderStockist;
import com.excel.model.EInvoiceHeader_Arc;
import com.excel.model.EInvoiceHeader_Arc_Stockist;
import com.excel.model.E_invoice_report;
import com.excel.model.Period;
import com.excel.model.Sum_Disp_Header;

public interface EInvoiceRepository {
	List<DivMaster> getDivandTeams(String empID) throws Exception;

	List<Period> getperiod() throws Exception;

	Period getdate() throws Exception;

	public List<Object> getIrnCancelData(String fromTrnId, String toTrnId, String divId, String finyearflag)
			throws Exception;

	public List<EInvoiceGenerateData> getEInvoiceGenerateData(String finflag, String finyear, String divId,
			String frdspid, String todspid) throws Exception;

	EInvoiceHeader getObjectByID(Long headerId) throws Exception;

	public List<Object> getChallanDetailsHavingIrn(String fromTrnId, String toTrnId, String finYear, String divId)
			throws Exception;

	public List<Sum_Disp_Header> getChallanDetails(String finYear, String divId, String finyearflag, Long loc_id)
			throws Exception;

	public EInvoiceHeader getEInvoiceHeaderByIrn(String irn) throws Exception;

	public List<EInvoiceHeader> getIrnChallansWithoutEway(String finYear, String divId, String finYearFlag, Long loc_id)
			throws Exception;

	public List<EInvoiceHeader> getIrnGencancelData(String finYear, String divId, Long loc_id) throws Exception;

	public List<EInvoiceHeader> getEwayBillCancel(String finYear, String divId, String finYearFlag, Long loc_id);

	public List<EInvoiceHeader> getIrnDownloadData(String finYear, String divId, String finYearFlag, Long loc_id)
			throws Exception;

	public List<Object> getEwayBillDownloadNumbers(String finYear, String divId, String fromTrn, String toTrn,
			String finYearFlag);

	public EInvoiceHeader getEInvoiceHeaderByEWB(String ewbNo) throws Exception;

	EInvoiceHeader_Arc getObjectByIDAndFinYear(Long headerId, String finYear) throws Exception;

	public EInvoiceHeader getEInvoiceHeaderByEWayBillNo(String ewaybill) throws Exception;

	public List<Object> getChallanDetailsHavingIrnSuperTax(String fromTrnId, String toTrnId, String finYear,
			String divId) throws Exception;

	public List<EInvoiceGenerateData> getEInvoiceGenerateDataStockist(String finflag, String finyear, String divId,
			String frdspid, String todspid,String locid) throws Exception;
	
	//for stockist app
	EInvoiceHeaderStockist getObjectByIDStk(Long headerId) throws Exception;
	
	public EInvoiceHeader_Arc_Stockist getObjectByIDAndFinYearStk(Long headerId, String finYear) throws Exception;
	
	public EInvoiceHeaderStockist getEInvoiceHeaderStockistByIrn(String irn) throws Exception;
	
	public EInvoiceHeaderStockist getEInvoiceHeaderStockistByEWB(String ewbNo) throws Exception;
	
	public List<Object> getChallanDetailsHavingIrnStockist(Long loc_id,String fromTrnId, String toTrnId, String finYear, String divId)
			throws Exception;
	
	public List<Object> getIrnCancelDataStockist(Long loc_id,String fromTrnId, String toTrnId, String divId, String finyearflag,String fin_year)
			throws Exception;
	
	public List<Dispatch_Header> getChallanDetailsStockist(String finYear, String divId, String finyearflag, Long loc_id)
			throws Exception;
	
	public List<EInvoiceHeaderStockist> getIrnChallansWithoutEwayStockist(String finYear, String divId, String finYearFlag, Long loc_id)
			throws Exception;
	
	public List<EInvoiceHeaderStockist> getIrnGencancelDataStockist(String finYear, String divId, Long loc_id) throws Exception;

	public List<EInvoiceHeaderStockist> getEwayBillCancelStockist(String finYear, String divId, String finYearFlag, Long loc_id);

	List<E_invoice_report> getE_invoiceReportData(String string, String string2, String string3) throws Exception;


}
