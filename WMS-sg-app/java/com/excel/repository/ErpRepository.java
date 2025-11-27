package com.excel.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.excel.bean.ErpBatchBean;
import com.excel.bean.ErpDispatchBean;
import com.excel.bean.ErpDispatchCancel;
import com.excel.bean.ErpProductBean;
import com.excel.bean.ErpStockAdjBean;
import com.excel.bean.ErpStockwithdrawalBean;
import com.excel.bean.Erp_Wms_Iaa_confirmationbean;
import com.excel.model.Erp_Batch_stk_reco;
import com.excel.model.Erp_Batch_stk_reco_quarantine;
import com.excel.bean.ErpGrnBean;
import com.excel.bean.ErpGrnConfirmationBean;
import com.excel.bean.ErpIaaGrn_bean;
import com.excel.bean.ErpNormal_Iaabean;

@Repository
public interface ErpRepository {

	List<ErpProductBean> getProducts(String divid);

	List<ErpBatchBean> getBatch();

	List<ErpGrnBean> getGrn(String year);

	List<ErpGrnConfirmationBean> getGrnConfirmation(String year);

	List<ErpDispatchBean> getDispatch(String year);

	List<ErpDispatchCancel> getDispatchCancel(String year);

	List<ErpStockAdjBean> getStockAdj(String year);
	
	void updateErpProduct(String divid, List<String> failedProducts) throws Exception;

	void updateErpBatch( List<String> failedBatch) throws Exception;
	
	void updateErpDispatch(String year, List<String> failed) throws Exception;

	void updateErpStockAdj(String year) throws Exception;


	List<ErpStockwithdrawalBean> getErpstockwithdrawaldata(String year) throws Exception;
	
	List<ErpNormal_Iaabean> getErp_NormalIaadata(String year) throws Exception;
	
	List<ErpIaaGrn_bean> getErp_IaaGrndata(String year) throws Exception;
	
	List<Erp_Wms_Iaa_confirmationbean> getErp_Wms_Iaa_Confirmationdata(String year) throws Exception;
	
	void updateErpGrnComfirm(List<String> list, String year) throws Exception;
	
	void Push_Batch_stk_reco_data_to_arc() throws Exception;
	
	void Truncate_BatchstkReco() throws Exception;
	
	void call_batch_stk_reco_pfz_proc(String date) throws Exception;

	void updateErpGrn(List<String> failed, String year) throws Exception;

	void Truncate_Batch_stk_reco_Quarantine() throws Exception;

	void call_batch_stk_reco_quarantine_proc(String date) throws Exception;

	void push_Batch_stk_reco_quarantine_data_to_arc() throws Exception;

	void update_erpIaaGrndata(List<String> list, String year) throws Exception;
	
	void push_Tran_wise_data_to_arc()throws Exception;
	void Truncate_Tran_wise_reco()throws Exception;
	void call_Erp_Tran_wise_data(String from_dt, String to_dt, String fin_year)throws Exception;

	void update_erpNormalIaadata(List<String> list, String year) throws Exception;
	
	void push_Tran_wise_Quarantine_data_to_arc()throws Exception;
	void Truncate_Tran_wise_Quarantine_reco()throws Exception;
	void call_Erp_Tran_wise_Quarantine_data(String from_dt, String to_dt, String fin_year)throws Exception;

	void update_erpWms_Iaa_Confirmationdata(List<String> list, String year) throws Exception;

	Long getCycleNumber(Date date) throws Exception;

	void updateErpDispatchCancel(List<String> list, String year) throws Exception;

	void update_erpstockwithdrawal(List<String> list, String year) throws Exception;

	List<Erp_Batch_stk_reco> getdataforstockreco()throws Exception;
	List<Erp_Batch_stk_reco_quarantine> getstockRecoQuarantinereportdata();

	void deleteErpProduct(String product_code) throws Exception;
}
