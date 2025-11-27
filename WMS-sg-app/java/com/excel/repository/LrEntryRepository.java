package com.excel.repository;

import java.util.List;

import javax.persistence.Tuple;

import com.excel.bean.LrEntryBean;
import com.excel.bean.Lr_download_Execl_Bean;
import com.excel.bean.TRF_BEAN_stocktransfer_download_trdata;
import com.excel.bean.TransporterBean;
import com.excel.model.Location;
import com.excel.model.Lr_csv_upload_java_proc_recised_filter;
import com.excel.model.Lrcsv_RevisedDownload;
import com.excel.model.Lrcsv_RevisedDownload_SG;
import com.excel.model.Transporter_master;

public interface LrEntryRepository {
	List<Lrcsv_RevisedDownload> getLrcsvdownload_Revised_data(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			List<String> loc, List<String> div, List<String> dest,String cfa_to_stk_ind) throws Exception;

	List<Transporter_master> gettransportermaster() throws Exception;

	List<Transporter_master> gettransportermaster_new() throws Exception;

	Transporter_master getByObjectId(Long trans_id) throws Exception;

	List<Tuple> getlistsize(TransporterBean transBean) throws Exception;

	List<Lr_csv_upload_java_proc_recised_filter> getLrcsvdownload_Revised_filter(String strdate, String enddate,
			String tbl_ind, String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp,
			String deptloc, String cfa_to_stk_ind) throws Exception;

	Location getLocationNameByEmployeeId(String eMP_ID);

	List<Lr_download_Execl_Bean> getDataForGenExel(List<String> trf_id);

	List<Tuple> getRecevingLocations(String startDate, String endDate, String location_id, String ind, String finyear_flage);

	List<Tuple> getDataForStockkTransferLrEntry(String startDate, String endDate, StringBuffer reclocations,
			String sendLocation, String finyear_flage);

	List<TRF_BEAN_stocktransfer_download_trdata> getStockTransferList(String startDate, String endDate,
			String locationId, List<String> destinationlist);

	int saveDataForStockTransferLr(LrEntryBean bean) throws Exception;

	String getDpLocId(String loc_id) throws Exception;
	
	List<Transporter_master> gettransportermasterForLocation(Long loc_id) throws Exception;
	
	public Long getLocIdByUsername(String username) throws Exception;

	List<Lrcsv_RevisedDownload_SG> getLrcsvdownload_Revised_data_for_sg(String strdate, String enddate, String tbl_ind, String cfa,
			String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			List<String> loc, List<String> div, List<String> dest, String cfa_to_stk_ind);

}
