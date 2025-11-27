package com.excel.repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import com.excel.bean.DispatchBean;
import com.excel.bean.DispatchMailBean;
import com.excel.bean.ReportBean;
import com.excel.exception.MedicoException;
import com.excel.model.AllocationDetailList;
import com.excel.model.AutoDispatchDropdown;
import com.excel.model.DispatchHeaderData;
import com.excel.model.Dispatch_Detail;
import com.excel.model.Dispatch_Detail_Arc;
import com.excel.model.Dispatch_Header;
import com.excel.model.Dispatch_Header_Addl;
import com.excel.model.Dispatch_Header_arc;
import com.excel.model.GenerateDispatchData;
import com.excel.model.GenerateDispatchDataCfaStock;
import com.excel.model.GenerateDispatchData_AllocType;
import com.excel.model.LrCsvUpload;
import com.excel.model.ManualDispatchItemList;
import com.excel.model.ManualDispatchList;
import com.excel.model.ManulDispatchProdListData;
import com.excel.model.MonthList;
import com.excel.model.P_iu_dispatch_java;
import com.excel.model.P_v_dispatch2_java;
import com.excel.model.Sum_Disp_Detail;
import com.excel.model.Sum_Disp_Detail_Arc;
import com.excel.model.Sum_Disp_Header;
import com.excel.model.Sum_Disp_Header_Addl;
import com.excel.model.Sum_Disp_Header_arc;
import com.excel.model.Transporter_master;

public interface DispatchRepository {
	
	AllocationDetailList getAllocationDetailList(String emp_id) throws Exception;
	public List<MonthList> getMonthList(String tablename, String fieldlist, String search, String orderby) throws Exception;
	List<AutoDispatchDropdown> getAutoDispatchDropdown(String alloc_smp_id, String dispatchToLabel, String divId,
			String allocType,String getAutoDispatchDropdown,String req_Id,boolean isCfa_to_stockist)throws Exception;
	List<GenerateDispatchData> genAutoDispatch(String comp_cd, String fin_year, String period_cd, Long dsp_loc_id,
			Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId, Long pi_msr, String pend_alloc,
			String action, String pvinsusr_id, Long pvinsip, Long pi_div_id, Long sample_rbm, Long sample_abm,
			String sample_flag, String challan_msg, Long chalan_periodId, String req_lvl, Long zone_id,
			String direct_to_pso_ind, boolean stock_at_cfa_ind) throws Exception;
	List<GenerateDispatchDataCfaStock> genAutoDispatchCfaStock(String comp_cd, String fin_year, String period_cd,
			Long dsp_loc_id, Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId, Long pi_msr,
			String pend_alloc, String action, String pvinsusr_id, Long pvinsip, Long pi_div_id, Long sample_rbm,
			Long sample_abm, String sample_flag, String challan_msg, Long chalan_periodId, String req_lvl, Long zone_id,
			String direct_to_pso_ind, boolean stock_at_cfa_ind) throws Exception;
	List<String> getAutoDispatchDetailsUpdated(Long smp_id, Long disp_cycle, Long loc_id, Long div_id,
			boolean stock_at_cfa_ind) throws Exception;
	List<ManualDispatchItemList> getdispatchProd_list(Long staff_id, Long smp_id,String allocType) throws Exception;
	List<ManulDispatchProdListData> getdispatchProdDetails(String comp_cd, String dsp_year, String dsp_period_cd,
			Long dsp_loc, Long smp_id, Long prod_id, Long msr_id, String pend_alloc,String allocType) throws Exception;
	List<P_v_dispatch2_java> getDispatchedProdData(Long dsp_id, Long loc_id) throws Exception;
	List<ManualDispatchList> getManualDispatchList(Long loc_SubComp_id, String user_divisions, String user_id,
			String user_type, long sum_dsp_id) throws Exception;
	List<Sum_Disp_Header> getSummaryChallanListByDiv(Long div_id, String user_id, Long loc_subComp_id, int appr_level)
			throws Exception;
	List<P_iu_dispatch_java> saveManualDispatch(String comp_cd, String dsp_year, String dsp_period_cd, Long dsp_loc,
			Long dsp_alloc_id, Long smp_id, Long fstaff_id, Long div_id, Long recv_loc, Long dsp_state_id,
			String pend_alloc, String emp_id, String ip_addr, String dsp_status, Long dsp_cycle, Long dsp_id,
			Long prod_id, Long dsp_batch_id, BigDecimal disp_qty, BigDecimal disp_rate, Long dsp_dtl_alloc_id,
			Long dsp_alt_divid, Long sumdsp_id, String action, String status_flag, BigDecimal prv_smpqty,
			Long prv_allocid, Long prv_allocdtlid, String challan_msg, Long challan_period_id, String req_lvl,
			String direct_to_pso_ind) throws Exception;
	Dispatch_Header getDispatchHeaderById(Long dsp_id) throws Exception;
	void updateHeaderForApproval(String status, Long dsp_id, String user_name, Date appr_date)
			throws Exception;
	List<ManualDispatchList> getManualDispatchListApproval(String user_id, String user_type, Long sum_dsp_id)
			throws Exception;
	public List<ReportBean> getProducts(long div_Id, int locfrmId, String stdt, String endt, String fstaff_id,
		    String desg_id, String fstaffId_o, String deleted_inv, String emp_id,String emp_id1,String finyrflag,String year) throws Exception;
	DispatchHeaderData getDispatchedHeaderData(Long dsp_id, Long loc_id) throws Exception;
	Dispatch_Header getDispatchHeaderByChallanNo(String dsp_id) throws Exception;
	void updateDispatchHeaderForSelfApproval(String challanNumber, Date date, String ip_address, String empId);
//	Map<String,Object> getDivisionForDispatchApproval();
	
	Object getHeaderForDispatchApproval(String ChallanNo);
	Map<String, Object> getDetailForDispatchApproval(int dspId);
	void updateDispatchHeaderForDispatchSelfDiscard(String empId, List<String> challanNumberList,
			HttpServletRequest request);
	void updateDispatchHeaderForDispatchSelfApproval(String empId, List<String> challanNumberList,HttpServletRequest request);
	List<GenerateDispatchData_AllocType> genAutoDispatchAllocType(String comp_cd, String fin_year, String period_cd, Long dsp_loc_id,
			Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId, Long pi_msr, String pend_alloc,
			String action, String pvinsusr_id, Long pvinsip, Long pi_div_id, Long sample_rbm, Long sample_abm,
			String sample_flag, String challan_msg, Long chalan_periodId, String req_lvl, Long zone_id,
			String direct_to_pso_ind, boolean stock_at_cfa_ind,String allocType,String prodIds,String prodSubTypes,String subCodelist) throws Exception,SQLException,PersistenceException;
	 List<Dispatch_Detail> getDtlListByHdrId(Long dspdtl_dsp_id,String status)throws Exception;
	 void deleteDispatchRecord(Long dsp_id,String emp_id,String ip_add,String status) throws Exception;
	 void deleteDispatchDetail(String comp_cd,String year,String dsp_period_cd,Long dsp_id,Long prod_id,
				String emp_id,String ip_add) throws Exception;
		List<ManulDispatchProdListData> getdispatchProdDetailsEdit(Long dsp_id,Long dspdtl_id,String pend_alloc) throws Exception;
		List<DispatchMailBean> getDispatchedMailData(Long dsp_id) throws Exception;
		List<Object> getFieldstaffDetail(Long fsId,Long locId) throws Exception;
		String getDispatchType(Long dsp_id) throws Exception;
		List<ReportBean> getEmpDetails(String startdt, String endate, Long cfaLoc,String username,String role);

		void uploadlrCsvData(List<Sum_Disp_Header> datasum, List<Dispatch_Header> datadispt,String finyr,DispatchBean bean)
			    throws MedicoException;
		void uploadlrCsvDataInAddl(List<Sum_Disp_Header_Addl> datasum_adl, List<Dispatch_Header_Addl> datadispt_adl) throws MedicoException;
		void truncateLrCSVUpload() throws Exception;
		void saveLrCSVUpload(List<LrCsvUpload> li) throws Exception;
		Sum_Disp_Header getObjectById(Long headerId) throws Exception;
		 List<Sum_Disp_Detail> getSumDtlListByHdrId(Long sum_challan_no)throws Exception;
		 void updateGstVal(Sum_Disp_Detail details, int counter) throws Exception;
		 void updateSum_disp_hdrGstVal(BigDecimal sgst_sum, BigDecimal cgst_sum,
					BigDecimal igst_sum, String gst_doc_type, Long sum_challan_no, BigDecimal roundof_value)  throws Exception;
		 List<Dispatch_Header> getApprStatusBySummChlnId(Long sum_challan_no)throws Exception;
		  List<Dispatch_Header> getDspHeaderForDelete(String finYr,String currentPeriod,Long divId,
					String from_dt, String to_dt,Long locId) throws Exception; 
		 void submitdeleteChallan(DispatchBean bean) throws Exception; 
		 
		 void uploadlrCsvModifydetailData(List<Sum_Disp_Header> datasum, List<Dispatch_Header> datadispt,DispatchBean bean)
				    throws MedicoException;
		Dispatch_Header getDspHeader(Long sum_dsp_id, String challan_no);
		
		Sum_Disp_Header_arc getObjectById_and_finyr(Long headerId,String finyr) throws Exception;
		Dispatch_Header_arc getDspHeader_forarc(Long sum_dsp_id, String challan_no,String finyr) throws Exception;
		void uploadlrCsvData_arc(List<Sum_Disp_Header_arc> datasum, List<Dispatch_Header_arc> datadispt,String finyrflg,DispatchBean bean)
			    throws MedicoException;
		
		 void uploadlrCsvModifydetailData_arc(List<Sum_Disp_Header_arc> datasum, List<Dispatch_Header_arc> datadispt,String finyr,DispatchBean bean)
				    throws MedicoException;

		 Transporter_master getTransporterMaster(String transportername)throws Exception;
		 
		 Dispatch_Header getdspHeaderById(Long dspId) throws Exception;
		 Sum_Disp_Detail sumdispatchDtlById(Long dtlId)throws Exception;
		 
		 ArrayList<Object> getdispatch_details_EmailToMgr(String empId, Long divId, Long locId)throws Exception;
		 Sum_Disp_Detail_Arc sumdispatchArcDtlBysumdspDtlId(Long dtlId,String finyrId)throws Exception;
		 
		 List<GenerateDispatchData_AllocType> genAutoDispatchAllocTypeBulkUpload(String comp_cd, String fin_year, String period_cd, Long dsp_loc_id,
					Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId, Long pi_msr, String pend_alloc,
					String action, String pvinsusr_id, Long pvinsip, Long pi_div_id, Long sample_rbm, Long sample_abm,
					String sample_flag, String challan_msg, Long chalan_periodId, String req_lvl, Long zone_id,
					String direct_to_pso_ind, boolean stock_at_cfa_ind,String allocType,String prodIds,String prodSubTypes,String subCodelist,Long reqId) throws Exception,SQLException,PersistenceException;

		 Sum_Disp_Header getSumDispHeaderbyChallanNo(String challan_no) throws Exception;
		 Sum_Disp_Header_arc sumdispatchArcHdrByChallanNo(String challan_no,String finyrId)throws Exception;
		 List<Long> getDispatchCyclesForBulkHCP(Long bulk_hcp_req_id,Long dspCycle,Long alloc_smp_id) throws Exception;
		 
		 
		 List<GenerateDispatchData_AllocType> genAutoDispatchAllocTypeBulkUploadStockist(String comp_cd, String fin_year, String period_cd, Long dsp_loc_id,
					Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId, Long pi_msr, String pend_alloc,
					String action, String pvinsusr_id, Long pvinsip, Long pi_div_id, Long sample_rbm, Long sample_abm,
					String sample_flag, String challan_msg, Long chalan_periodId, String req_lvl, Long zone_id,
					String direct_to_pso_ind, boolean stock_at_cfa_ind,String allocType,String prodIds,String prodSubTypes,String subCodelist,Long reqId) throws Exception,SQLException,PersistenceException;

		 public List<GenerateDispatchData_AllocType> genAutoDispatchAllocTypeSingleStockists(String comp_cd, String fin_year,
					String period_cd, Long dsp_loc_id, Long smp_id, Long pi_loc_id, Long pi_abm, Long pi_rbm, Long pi_stateId,
					Long pi_msr, String pend_alloc, String action, String pvinsusr_id, Long pvinsip, Long pi_div_id,
					Long sample_rbm, Long sample_abm, String sample_flag, String challan_msg, Long chalan_periodId,
					String req_lvl, Long zone_id, String direct_to_pso_ind, boolean stock_at_cfa_ind, String allocType,
					String prodIds, String prodSubTypes, String subTeamCodeList, String reqId)
					throws Exception, SQLException, PersistenceException;

		 //changes for stockist
		 Dispatch_Detail dispatchDtlById(Long dtlId)throws Exception;
		 Dispatch_Detail_Arc dispatchArcDtlBydspDtlId(Long dtlId,String finyrId)throws Exception;
		 List<Dispatch_Header> getListOfSumDispForEInvoice(Long locid,String date) throws Exception;
}
