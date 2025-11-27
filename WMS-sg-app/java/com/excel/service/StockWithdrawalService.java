package com.excel.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.StockWithdrawalBean;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SWV_Header;
import com.excel.model.SmpItem;
import com.excel.model.Supplier;
import com.excel.model.V_SWV_Detail;
import com.excel.model.V_SWV_Header;

public interface StockWithdrawalService {

	List<SmpItem> getProdListForSWV(Long loc_id) throws Exception;
	 List<Supplier> getSuppliersBySupplierType(String supplierType,Long subCompId) throws Exception;
	 Supplier supplierAddrById(Long sup_id) throws Exception;
	 List<StockWithdrawalBean> getBatchDetailsByProdStkType(Long loc_id, String stk_type,Long prod_id) throws Exception;
	 Map<String, Object> saveStkWth(StockWithdrawalBean bean) throws Exception;
	 List<V_SWV_Detail> getSWVDetailsBySWVId(Long swv_id)throws Exception; 
	 List<V_SWV_Header> getSWVListForModify(Long loc_id, String comp_cd, String emp_id,String user_type, String from_dt, String to_dt)throws Exception;
	 boolean deleteStkWth(StockWithdrawalBean bean) throws Exception;
	 V_SWV_Header getSWVHeaderDetailsById(Long swv_id) throws Exception;
	 List<StockWithdrawalBean> getModBatchDetailsByProdStkType(Long loc_id, String stk_type,Long prod_id,Long swv_id) throws Exception;
	 List<SmpItem> getModProdListForSWV(Long loc_id,Long swv_id) throws Exception;
	 List<SG_d_parameters_details> getStockTypeListByProd(Long swv_id,Long prod_id) throws Exception;
	 void deleteSWVDetail(StockWithdrawalBean bean) throws Exception;
	 void stkWthSelfApproval(StockWithdrawalBean bean) throws Exception;
	 void finalDispatchApproval(Long headerId, String last_approved_by, String comp_cd, String isapproved,
				String motivation) throws Exception;
	 List<Object> getChallanForLr(Long loc_id) throws Exception;
	 void stkWthLr(StockWithdrawalBean bean) throws Exception;
	 void stkWthFileUpload(List<MultipartFile> file,String headerId) throws Exception;
	 SWV_Header getSWVHeaderById(Long swv_id) throws Exception; 
}
