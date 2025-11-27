package com.excel.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import com.excel.bean.PrintBean;
import com.excel.model.Am_m_System_Parameter;
import com.excel.model.Dispatch_Header;
import com.excel.model.HqMasterAuditTrailModel;
import com.excel.model.NOV_BillOfSupply_Challan;
import com.excel.model.Period;
import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.model.PrePrintedDetailChallan_withgstPG;
import com.excel.model.PrePrintedDetailChallan_withgst_pal;
import com.excel.model.SWV_Header;
import com.excel.model.SWV_Header_arc;
import com.excel.model.StockTransferNotePrint;
import com.excel.model.Stock_Transfer_Header;
import com.excel.model.Team;
import com.excel.model.ViewGrnGSTVoucherPrinting;
import com.excel.model.ViewGrnLabelPrinting_java;
import com.excel.model.ViewIAAVoucherPrinting;
import com.excel.model.ViewPrePrintedDetailChallan;
import com.excel.model.ViewPrePrintedSummaryChallan_GST;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoice;
import com.excel.model.ViewPrePrintedSummaryChallan_GST_EInvoiceStockist;
import com.excel.model.ViewStockWithdrawalVoucherPrint;


public interface PrintRepository {
	
	List<ViewGrnGSTVoucherPrinting> getGrnPrintVoucherGstData(PrintBean bean) throws Exception;
	//List<PrePrintedDetailChallan_withgst> getPreprintedDetailedChallanData() throws Exception;
	List<Period> getAllFinYearForGSTChallanPrint() throws Exception;
	
	List<Period> getListOfPeriodByFinYear(String finYear);
	 List<ViewPrePrintedDetailChallan> getDeclarationPrint(Integer divId, Integer locid, String frmchallan,
			String tochallan, String dispatchtype, String prodtype, String reptype, String finyearflag,
			String finId);
	Am_m_System_Parameter getSpValueForFooterSignature();
	//List<ViewPrePrintedDetailChallan> getPreprintedSummaryChallanData(String division,String loc,String frm_challan,String to_challan,String prodtype,String rep_type)throws Exception;
	//List<ViewPrePrintedSummaryChallan_GST> PrePrintedSummaryChallan_GSTdata(String division,String loc,String frm_challan,String to_challan,String prodtype,String rep_type)throws Exception;
	//List<PrePrintedDetailChallan_withgst> getPreprintedDetailedChallanData(String division,String loc,String from_challan,String to_challan,String dispatchtype,String product_type,String rep_type) throws Exception;
	String getLocationNarrationByLocId(Long locId)throws Exception;
	//List<ViewPrePrintedDetailChallan> getViewPrePrintedDetailChallanData(String division,String loc,String frm_challan,String to_challan,String dispatchType,String prodtype,String rep_type)throws Exception;
	
	List<Dispatch_Header> getChallanListForGSTChallanPrint(String divId, String periodCode, String finYear,String location,String finyrflg);
	List<Dispatch_Header>  getChallanBtwDtPkgList(String pageIndQuery,String ro,String divId, String periodCode, String finYear,String location,String finyrflg,String cnfLocation,HttpSession session);
	List<ViewGrnLabelPrinting_java> getGrnLabelPrintData(String loc_id,String vender_id,String frmgrn,String togrn,String user_type,PrintBean pb)throws Exception;
	List<NOV_BillOfSupply_Challan> getDeliveryChallanPrint(PrintBean pb)throws Exception;
	List<PrintBean> getdetailLabelData(String from,String to,String user_type, String finyearflg)throws Exception;
	void updateDispatchLblPrintRemark(EntityManager session, String lbl_print_status, String remarks,String mod_user_id, String mod_ip, Set<Long> dsp_id)throws Exception;
	
	List<Dispatch_Header> getChallanListForPickPackSlipPrint(String ro,String divId, String periodCode, String finYear,String location,String finyrflg);
	List<Object> getIAANoListForDocumentPrint(String location, String startDate, String endDate,String finyr,String finyrflg);

	List<ViewIAAVoucherPrinting> getViewIAAVoucherPrintingdata(String locId, String frmStkno, String toStkno,PrintBean pb)throws Exception;
	List<Dispatch_Header> getChallanListForGSTSummaryChallanPrint(String ro, String divId, String periodCode,
			String finYear, String location) throws Exception;
	//Hq Master Audit Trail
	List<HqMasterAuditTrailModel> getHqmasteraudittraildata(PrintBean bean)throws Exception;
	List<Stock_Transfer_Header> getTrfNoList(String location, String rl, String startDate, String endDate, String fin_year_flag);
	List<StockTransferNotePrint> getStocktransfernoteData(PrintBean bean) throws Exception;
	List<ViewPrePrintedSummaryChallan_GST> PrePrintedSummaryChallan_GSTdata(String division, String loc,
			String frm_challan, String to_challan, String prodtype, String rep_type, PrintBean bean) throws Exception;
	List<PrePrintedDetailChallan_withgst> getPreprintedDetailedChallanData(String division, String loc,
			String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,
			PrintBean bean) throws Exception;
	
	List<PrePrintedDetailChallan_withgst_pal> getPreprintedDetailedChallanDatapal(String division, String loc,
			String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,
			PrintBean bean) throws Exception;
	
	List<ViewPrePrintedDetailChallan> getPreprintedSummaryChallanData(String division, String loc, String frm_challan,
			String to_challan, String prodtype, String rep_type, PrintBean bean) throws Exception;
	List<ViewPrePrintedDetailChallan> getViewPrePrintedDetailChallanData(String division, String loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, String rep_type,
			PrintBean bean) throws Exception;
	
	List<ViewStockWithdrawalVoucherPrint> getstockwithdrawaldata(PrintBean bean) throws Exception;
	List<Period> getperiodsbyfinyear(String finyr)throws Exception;
	List<SWV_Header> getstockwithdrawalpdfchallans(Long loc_id, String period_code, String year,String page_ind)throws Exception;
	List<Team> getTeamList(String teamId) throws Exception;
	List<Dispatch_Header> getChallanListForGSTChallanPrintByTeamCode(String divId, String periodCode, String finYear,
			String location, String finyrflg, String teamCode)  throws Exception;
	
	List<SWV_Header_arc> getstockwithdrawalpdfchallansPreviousYear(Long loc_id, String period_code, String year,String page_ind)throws Exception;
	
	List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintPrevious(String ro, String divId, String periodCode,
			String finYear, String location) throws Exception;
	
	public List<PrePrintedDetailChallan_withgstPG> getPreprintedDetailedChallanDataPG(String division, String loc,
			String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,
			PrintBean bean) throws Exception;
	
	public List<ViewPrePrintedSummaryChallan_GST_EInvoice> PrePrintedSummaryChallan_GST_EInvoicedata(String division, String loc,
			String frm_challan, String to_challan, String prodtype, String rep_type,PrintBean bean) throws Exception;
	
	//for stockist
	public List<ViewPrePrintedSummaryChallan_GST_EInvoiceStockist> PrePrintedSummaryChallan_GST_EInvoicedataStocksit(String division, String loc,
			String frm_challan, String to_challan, String prodtype, String rep_type,PrintBean bean) throws Exception;

	List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintSockist(String ro, String divId, String periodCode,
			String finYear, String location) throws Exception;
	
	List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintPreviousStockist(String ro, String divId, String periodCode,
			String finYear, String location) throws Exception;
}
