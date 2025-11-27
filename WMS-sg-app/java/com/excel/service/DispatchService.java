package com.excel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.DispatchBean;
import com.excel.bean.ReportBean;
import com.excel.model.AllocationDetailList;
import com.excel.model.AutoDispatchDropdown;
import com.excel.model.Dispatch_Header;
import com.excel.model.GenerateDispatchData;
import com.excel.model.GenerateDispatchDataCfaStock;
import com.excel.model.GenerateDispatchData_AllocType;
import com.excel.model.ManualDispatchItemList;
import com.excel.model.ManualDispatchList;
import com.excel.model.ManulDispatchProdListData;
import com.excel.model.MonthList;
import com.excel.model.P_iu_dispatch_java;
import com.excel.model.P_v_dispatch2_java;
import com.excel.model.Sum_Disp_Header;

public interface DispatchService {
	
	public AllocationDetailList getAllocationDetailList(String emp_id) throws Exception;
	public List<MonthList> getMonthList(String tablename, String fieldlist, String search, String orderby) throws Exception;
	public List<AutoDispatchDropdown> getAutoDispatchDropdown(String alloc_smp_id, String dispatchToLabel, 
			String divId,String allocType,String subTeamCodeList,String req_Id,boolean isCfaToStockist) throws Exception;
	List<GenerateDispatchData> generateDispatchData(DispatchBean bean) throws Exception;
	List<GenerateDispatchDataCfaStock> generateDispatchDataCfaStock(DispatchBean bean) throws Exception;
	 public List<String> getAutoDispatchDetailsUpdated(Long smp_id, Long disp_cycle, Long loc_id, Long div_id,
			    boolean stock_at_cfa_ind) throws Exception;
	List<ManualDispatchItemList> getdispatchProd_list(Long staff_id, Long smp_id,String allocType) throws Exception;
	public List<ManulDispatchProdListData> getdispatchProdDetails(String comp_cd,String dsp_year,String dsp_period_cd,Long dsp_loc,Long smp_id,
			Long prod_id,Long msr_id,String pend_alloc,String allocType) throws Exception;
	public List<P_v_dispatch2_java> getDispatchedProdData(Long dsp_id,Long loc_id) throws Exception;
	List<ManualDispatchList> getManualDispatchList(Long loc_SubComp_id, String user_divisions, String user_id,
			String user_type, long sum_dsp_id) throws Exception;
	List<Sum_Disp_Header> getSummaryChallanListByDiv(String user_id, Long div_id, Long loc_subComp_id, int appr_level)
			throws Exception;
	List<P_iu_dispatch_java> saveManualDispatch(String comp_cd, String dsp_year, String dsp_period_cd, Long dsp_loc,
			Long dsp_alloc_id, Long smp_id, Long fstaff_id, Long div_id, Long recv_loc, Long dsp_state_id,
			String pend_alloc, String emp_id, String ip_addr, String dsp_status, Long dsp_cycle, Long dsp_id,
			Long prod_id, Long dsp_batch_id, BigDecimal disp_qty, BigDecimal disp_rate, Long dsp_dtl_alloc_id,
			Long dsp_alt_divid, Long sumdsp_id, String action, String status_flag, BigDecimal prv_smpqty,
			Long prv_allocid, Long prv_allocdtlid, String challan_msg, Long challan_period_id, String req_lvl,
			String direct_to_pso_ind) throws Exception;
	Dispatch_Header getDispatchHeaderById(Long dsp_id) throws Exception;
	void updateHeaderForApproval(String status, Long tran_id, String user_name, Date appr_date) throws Exception;
	List<ManualDispatchList> getManualDispatchListApproval(String user_id, String user_type, Long sum_dsp_id)
			throws Exception;
	public List<ReportBean> getProducts(long div_Id, int locfrmId, String stdt, String endt, String fstaff_id,
		    String desg_id, String fstaffId_o, String deleted_inv, String emp_id,String emp_id1,String finyrflag,String year) throws Exception;
	void finalDispatchApproval(Long tran_id, String user_name, String company_cd, String task_status,
			String motivation) throws Exception; 
	
	Object getHeaderForDispatchApproval(String ChallanNo);
	Object getDetailForDispatchApproval(int dspId);
	
	public void updateDispatchHeaderForDispatchSelfApproval(String empId,List<String> challanNumberList,HttpServletRequest request);
	public void updateDispatchHeaderForDispatchSelfDiscard(String empId, List<String> challanNumberList,HttpServletRequest request);
	List<GenerateDispatchData_AllocType> generateDispatchDataAllocType(DispatchBean bean) throws Exception;
	 void deleteDispatchDetail(String comp_cd,String year,String dsp_period_cd,Long dsp_id,Long prod_id,
				String emp_id,String ip_add) throws Exception;
		List<ManulDispatchProdListData> getdispatchProdDetailsEdit(Long dsp_id,Long dspdtl_id,String pend_alloc) throws Exception;
		List<Object> getFieldstaffDetail(Long fsId,Long locId) throws Exception;
		long dpLrUpload(DispatchBean bean) throws Exception;
		String getDispatchType(Long dsp_id) throws Exception;
		List<ReportBean> getEmpDetails(String startdt, String endate, Long cfaLoc,String username,String role);
		 List<Dispatch_Header> getDspHeaderForDelete(String finYr,String currentPeriod,Long divId,
					String from_dt, String to_dt,Long locId) throws Exception; 
		 void submitdeleteChallan(DispatchBean bean) throws Exception; 
		 
		 long dpLrUploadRevised(DispatchBean bean) throws Exception;
		 long dpLrUploadModifyDetailRevised(DispatchBean bean) throws Exception;
		void updateHdrGstVal(BigDecimal sgst_sum, BigDecimal cgst_sum, BigDecimal igst_sum, int counter,
				String gst_doc_type, Long dsp_id, BigDecimal round_of_val, Dispatch_Header obj, String team_code)
				throws Exception;
		void sendForDispatchApproval(String empId, List<String> challanNumberList, HttpServletRequest request) throws Exception;
		public ArrayList<Object> getdispatch_details_EmailToMgr(String empId, Long divId, Long locId,String CompanyName) throws Exception;

		List<Long> getDispatchCyclesForBulkHCP(Long bulk_hcp_req_id,Long dspCycle,Long alloc_smp_id) throws Exception;
		 List<Dispatch_Header> getListOfSumDispForEInvoice(Long locid,String date) throws Exception;
}

