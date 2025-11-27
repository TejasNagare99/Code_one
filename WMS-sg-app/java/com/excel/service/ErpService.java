package com.excel.service;

import java.util.List;
import java.util.Map;

import com.excel.bean.ErpBatchBean;
import com.excel.bean.ErpBatch_stk_recobean;
import com.excel.bean.ErpDispatchBean;
import com.excel.bean.ErpDispatchCancel;
import com.excel.bean.ErpGrnBean;
import com.excel.bean.ErpGrnConfirmationBean;
import com.excel.bean.ErpIaaBean;
import com.excel.bean.ErpIaaGrn_bean;
import com.excel.bean.ErpNormal_Iaabean;
import com.excel.bean.ErpProductBean;
import com.excel.bean.ErpStockAdjBean;
import com.excel.bean.Erp_Batch_stk_reco_quarantinebean;
import com.excel.bean.Erp_Wms_Iaa_confirmationbean;
import com.excel.bean.Erp_tran_stock_Quarantine_recobean;
import com.excel.bean.Erp_tran_stock_recobean;
import com.excel.bean.IAABean;
import com.excel.bean.ProductBean;
import com.excel.model.Erp_Batch_stk_reco;
import com.excel.model.Erp_Batch_stk_reco_quarantine;
import com.excel.model.Stock_Adj_Details;
import com.excel.model.Stock_Adj_Header;

public interface ErpService {
	
    int getproductDetails(String divid) throws Exception;
	
	int getdispatchCancelDetails(String year) throws Exception;

	List<ErpStockAdjBean> getstockAdjbeanDetails(String year) throws Exception;
	
	List<ErpGrnBean> getErpGrnDetails(String year) throws Exception;
	
	List<ErpGrnConfirmationBean> getErpGrnconfirmationDetails(String year) throws Exception;
	
	void update_erpproduct(String divid, List<String> failedProducts) throws Exception;
	
	void update_erpbatch(String divid, List<String> erp_batch) throws Exception;
	
	
	void update_erpstockadj(String year) throws Exception;

	Map<String, Object> getErpstockwithdrawaldata(String year) throws Exception;
	
	int getErp_NormalIaadata(String year) throws Exception;
	
	
	List<ErpIaaGrn_bean> getErp_IaaGrndata(String year) throws Exception;

	
	int getErp_Wms_Iaa_Confirmationdata(String year) throws Exception;
	
	void update_erpWms_Iaa_Confirmationdata(String year) throws Exception;


	void update_erpgrnconfirmation(List<String> list, String year) throws Exception;

	Stock_Adj_Details saveStockAdjDetails(ErpIaaBean bean, int i) throws Exception;

	Stock_Adj_Header saveStockAdjHeader(ErpIaaBean bean) throws Exception;


	Long saveBatchWithHoleHeader(ErpIaaBean bean) throws Exception;


	void saveBatchWithHodlDtl(ErpIaaBean bean, int i) throws Exception;

	Map<String, Object> saveIaaEntry(ErpIaaBean erpIaa) throws Exception;
	
	int getbatchDetails(String year);

	int getDispatchDetails(String finYear);

	Map<String, Object> getErp_Batch_stk_recodata(List<ErpBatch_stk_recobean> stk_reco_list) throws Exception;

	Long saveStockAdjDetailsProceure(ErpIaaBean bean, int i) throws Exception;

	int getGrnDetails(String year);

	int getGrnconfirmationDetails(String year);

	void update_erpgrn(List<String> failed, String year) throws Exception;


	Map<String, Object> getErp_Batch_stk_reco_quarantine_data(List<Erp_Batch_stk_reco_quarantinebean> stk_reco_list)
			throws Exception;
	
	int getIaaGrnDetails(String year);

	Map<String, Object> getErp_Tran_wise_data( List<Erp_tran_stock_recobean> stockRecoBean, String curr_year) throws Exception;

	Map<String, Object> getErp_Tran_wise_Quarantine_data(List<Erp_tran_stock_Quarantine_recobean> bean, String curr_year)throws Exception;

	int getStockWithrawal(String finYear);

	List<Erp_Batch_stk_reco> getdataforstockreco()throws Exception;
	List<Erp_Batch_stk_reco_quarantine> getstockRecoQuarantinedata();
}
