package com.excel.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.excel.repository.PrintRepository;

@Service
public class PrintServiceImpl implements PrintService{
	
	@Autowired PrintRepository printRepository;

	@Override
	public List<ViewGrnGSTVoucherPrinting> getGrnPrintVoucherGstData(PrintBean bean) throws Exception {
		return printRepository.getGrnPrintVoucherGstData(bean);
	}

	@Override
	public List<PrePrintedDetailChallan_withgst> getPreprintedDetailedChallanData() throws Exception {
		//return printRepository.getPreprintedDetailedChallanData();
		return null;
	}

	@Override
	public List<Period> getAllFinYearForGSTChallanPrint() throws Exception {
		return printRepository.getAllFinYearForGSTChallanPrint();
	}
	@Override
	public List<Period> getListOfPeriodByFinYear(String finYear){
		return printRepository.getListOfPeriodByFinYear(finYear);
	}
	@Override
	public List<Dispatch_Header> getChallanListForGSTChallanPrint(String divId,String periodCode,String finYear,String location,String finyrflg){
		return printRepository.getChallanListForGSTChallanPrint(divId, periodCode, finYear,location,finyrflg);
	}
	@Override
	public List<Dispatch_Header> getChallanListForPickPackSlipPrint(String ro,String divId,String periodCode,String finYear,String location,String finyrflg){
		return printRepository.getChallanListForPickPackSlipPrint(ro,divId, periodCode, finYear, location,finyrflg);
	}
	@Override
	public List<Object> getIAANoListForDocumentPrint(String location,String startDate, String endDate,String finyr,String finyrflg){
		return printRepository.getIAANoListForDocumentPrint(location, startDate, endDate,finyr,finyrflg);
	}
	@Override
	public List<Dispatch_Header> getChallanListForGSTSummaryChallanPrint(String ro,String divId,String periodCode,String finYear,String location) throws Exception{
		return printRepository.getChallanListForGSTSummaryChallanPrint(ro, divId, periodCode, finYear, location);
	}
	
	
	
	@Override
	public Am_m_System_Parameter getSpValueForFooterSignature() throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getSpValueForFooterSignature();
	}

	@Override
	public List<ViewPrePrintedDetailChallan> getPreprintedSummaryChallanData(String division, String loc,
			String frm_challan, String to_challan, String prodtype, String rep_type,PrintBean bean) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getPreprintedSummaryChallanData(division, loc, frm_challan, to_challan, prodtype, rep_type,bean);
	}

	@Override
	public List<ViewPrePrintedSummaryChallan_GST> PrePrintedSummaryChallan_GSTdata(String division, String loc,
			String frm_challan, String to_challan, String prodtype, String rep_type,PrintBean bean) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.PrePrintedSummaryChallan_GSTdata(division, loc, frm_challan, to_challan, prodtype, rep_type,bean);
	}

	@Override
	public List<PrePrintedDetailChallan_withgst> getPreprintedDetailedChallanData(String division, String loc,
			String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,PrintBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getPreprintedDetailedChallanData(division, loc, from_challan, to_challan, dispatchtype, product_type, rep_type,bean);
	}

	@Override
	public List<PrePrintedDetailChallan_withgst_pal> getPreprintedDetailedChallanDatapal(String division, String loc,
			String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,PrintBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getPreprintedDetailedChallanDatapal(division, loc, from_challan, to_challan, dispatchtype, product_type, rep_type,bean);
	}
	
	@Override
	public String getLocationNarrationByLocId(Long locId) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getLocationNarrationByLocId(locId);
	}

	@Override
	public List<ViewPrePrintedDetailChallan> getViewPrePrintedDetailChallanData(String division, String loc,
			String frm_challan, String to_challan, String dispatchType, String prodtype, String rep_type,PrintBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getViewPrePrintedDetailChallanData(division, loc, frm_challan, to_challan, dispatchType, prodtype, rep_type,bean);
	}

	@Override
	public List<ViewGrnLabelPrinting_java> getGrnLabelPrintData(String loc_id,String vender_id,String frmgrn,String togrn,String user_type,PrintBean pb) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getGrnLabelPrintData(loc_id, vender_id, frmgrn, togrn, user_type,pb);
	}

	@Override
	public List<PrintBean> getdetailLabelData(String from, String to, String user_type, String finyearflg) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getdetailLabelData(from, to, user_type, finyearflg);
	}

	@Override
	public void updateDispatchLblPrintRemark(EntityManager session, String lbl_print_status, String remarks,
			String mod_user_id, String mod_ip, Set<Long> dsp_id) throws Exception {
		printRepository.updateDispatchLblPrintRemark(session, lbl_print_status, remarks, mod_user_id, mod_ip, dsp_id);

		
	}


	@Override
	public List<ViewIAAVoucherPrinting> getViewIAAVoucherPrintingdata(String locId, String frmStkno, String toStkno,PrintBean pb) throws Exception {
		
		return printRepository.getViewIAAVoucherPrintingdata(locId, frmStkno, toStkno,pb);
	}

	@Override
	public List<HqMasterAuditTrailModel> getHqmasteraudittraildata(PrintBean bean) throws Exception {
		
		return printRepository.getHqmasteraudittraildata(bean);
	}
	//Stock Transfer Print
	@Override
	public List<Stock_Transfer_Header> getTrfNoList(String location,String rl,String startDate, String endDate,String fin_year_flag){
		return printRepository.getTrfNoList(location,rl, startDate, endDate, fin_year_flag);
	}
	
	@Override
	public List<StockTransferNotePrint> getStocktransfernoteData(PrintBean bean) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getStocktransfernoteData(bean);
	}
	@Override
	public List<Period> getperiodsbyfinyear(String finyr) throws Exception{
		// TODO Auto-generated method stub
		return printRepository.getperiodsbyfinyear(finyr);
	}

	@Override
	public List<SWV_Header> getstockwithdrawalpdfchallans(Long loc_id, String period_code, String year,
			String page_ind) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getstockwithdrawalpdfchallans(loc_id, period_code, year, page_ind);
	}
	@Override
	public List<ViewStockWithdrawalVoucherPrint> getstockwithdrawaldata(PrintBean bean) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getstockwithdrawaldata(bean);
	}

	@Override
	public List<Team> getTeamList(String teamId) throws Exception {
		return this.printRepository.getTeamList(teamId);
	}

	@Override
	public List<Dispatch_Header> getChallanListForGSTChallanPrintByTeamCode(String divId, String periodCode,
			String finYear, String location, String finyrflg, String teamCode) throws Exception {
		return printRepository.getChallanListForGSTChallanPrintByTeamCode(divId, periodCode, finYear,location,finyrflg,teamCode);
	}
	
	@Override
	public List<SWV_Header_arc> getstockwithdrawalpdfchallansforpreviousyear(Long loc_id, String period_code, String year,
			String page_ind) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getstockwithdrawalpdfchallansPreviousYear(loc_id, period_code, year, page_ind);
	}
	
	@Override
	public List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintPrevious(String ro, String divId,
			String periodCode, String finYear, String location) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getChallanListForGSTSummaryChallanPrintPrevious(ro, divId, periodCode, finYear, location);
	}

	@Override
	public List<ViewPrePrintedDetailChallan> getDeclarationForm(Integer divId, Integer locid, String frmchallan, String tochallan,
			String dispatchtype, String prodtype, String reptype, String finflag, String finId) {
		// TODO Auto-generated method stub
		return printRepository.getDeclarationPrint( divId,  locid,  frmchallan, tochallan,  dispatchtype,  prodtype,  reptype,   finflag, finId);
	}

	@Override
	public List<PrePrintedDetailChallan_withgstPG> getPreprintedDetailedChallanDataPG(String division, String loc,
			String from_challan, String to_challan, String dispatchtype, String product_type, String rep_type,
			PrintBean bean) throws Exception {
		// TODO Auto-generated method stub
		return printRepository.getPreprintedDetailedChallanDataPG(division, loc, from_challan, to_challan, dispatchtype, product_type, rep_type, bean);
	}
	
	public List<Dispatch_Header> getChallanBtwStdtEndDtPkgList(String pageIndQuery,String ro,String divId,String periodCode,String finYear,String location,String finyrflg,String cnfLocation,HttpSession session){
		return printRepository.getChallanBtwDtPkgList(pageIndQuery,ro,divId, periodCode, finYear, location,finyrflg,cnfLocation,session);

	}
	public List<NOV_BillOfSupply_Challan> getChallanBtwStdtEndDtPkgList(PrintBean pb) throws Exception{
		return printRepository.getDeliveryChallanPrint(pb);
	}

	@Override
	public List<ViewPrePrintedSummaryChallan_GST_EInvoice> PrePrintedSummaryChallan_GST_EInvoicedata(String division,
			String loc, String frm_challan, String to_challan, String prodtype, String rep_type, PrintBean bean)
			throws Exception {
		
		return printRepository.PrePrintedSummaryChallan_GST_EInvoicedata(division, loc, frm_challan, to_challan,
				prodtype, rep_type, bean);
	}

	@Override
	public List<ViewPrePrintedSummaryChallan_GST_EInvoiceStockist> PrePrintedSummaryChallan_GST_EInvoicedataStockist(
			String division, String loc, String frm_challan, String to_challan, String prodtype, String rep_type,
			PrintBean bean) throws Exception {
		return printRepository.PrePrintedSummaryChallan_GST_EInvoicedataStocksit(division, loc, frm_challan,
				to_challan, prodtype, rep_type, bean);
	}

	@Override
	public List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintStockist(String ro, String divId,
			String periodCode, String finYear, String location) throws Exception {
		return printRepository.getChallanListForGSTSummaryChallanPrintSockist(ro, divId, periodCode, finYear, location);
	}

	@Override
	public List<Dispatch_Header> getChallanListForGSTSummaryChallanPrintPreviousStockist(String ro, String divId,
			String periodCode, String finYear, String location) throws Exception {
		return printRepository.getChallanListForGSTSummaryChallanPrintPreviousStockist(ro, divId, periodCode, finYear, location);
	}

	
	
	
}
