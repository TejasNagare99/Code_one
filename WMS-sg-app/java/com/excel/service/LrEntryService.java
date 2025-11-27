package com.excel.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.persistence.Tuple;

import com.excel.bean.LrEntryBean;
import com.excel.bean.LrEntryDataBean;
import com.excel.bean.TRF_BEAN_stocktransfer_download_trdata;
import com.excel.model.Location;
import com.excel.model.Lr_csv_upload_java_proc_recised_filter;
import com.excel.model.Transporter_master;

public interface LrEntryService {

	List<LrEntryDataBean> getLrcsvdownload_Revised_data(String strdate, String enddate, String tbl_ind, String cfa,
			String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc, String role,
			String suer, List<String> loc, List<String> div, List<String> dest, String trans, String cfa_to_stk_ind,String companyCode)
			throws Exception;

	void saveLrEntry(LrEntryBean bean) throws Exception;

	List<Transporter_master> gettransportermaster() throws Exception;

	List<LrEntryDataBean> getConfirmationofdispatch_data(String strdate, String enddate, String tbl_ind, String cfa,
			String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			List<String> loc, List<String> div, List<String> dest,String cfa_to_stk_ind) throws Exception;

	void updateConfirmationdispatch(LrEntryBean bean) throws Exception;

	List<LrEntryDataBean> getLrcsvdownload_Revised_Lrbooking(String strdate, String enddate, String tbl_ind, String cfa,
			String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc, String role,
			String user, List<String> loc, List<String> div, List<String> dest, String trans,String cfa_to_stk_ind) throws Exception;

	List<Lr_csv_upload_java_proc_recised_filter> getLrcsvdownload_Revised_filter(String strdate, String enddate,
			String tbl_ind, String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp,
			String deptloc, String cfa_to_stk_ind) throws Exception;

	Location getLocationNameByEmployeeId(String eMP_ID);

	String getDataForGenExel(List<String> trf_id) throws IOException, ParseException;

	List<Tuple> getRecevingLocations(String startDate, String endDate, String location_id, String lrInd, String finyear_flage);

	List<Tuple> getgetDataForStockkTransferLrEntry(String startDate, String endDate, List<String> rec_loc,
			String sendLocation, String finyear_flage);

	List<TRF_BEAN_stocktransfer_download_trdata> getStockTransferList(String startDate, String endDate,
			String locationId, List<String> destinationlist);

	int saveDataForStockTransferLr(LrEntryBean bean) throws Exception;

	String getDpLocId(String loc_id) throws Exception;

	Long getLocIdByUsername(String username) throws Exception;

	List<Transporter_master> gettransportermasterForLocation(Long loc_id) throws Exception;


	List<LrEntryDataBean> getLrcsvdownload_Revised_data_for_sg(String strdate, String enddate, String tbl_ind, String cfa,
			String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc, String role,
			String suer, List<String> loc, List<String> div, List<String> dest, String trans, String cfa_to_stk_ind,String companyCode)
			throws Exception;
	
	List<LrEntryDataBean> getConfirmationofdispatch_data_for_sg(String strdate, String enddate, String tbl_ind, String cfa,
			String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			List<String> loc, List<String> div, List<String> dest,String cfa_to_stk_ind) throws Exception;

}
