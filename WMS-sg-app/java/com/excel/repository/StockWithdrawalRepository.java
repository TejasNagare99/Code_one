package com.excel.repository;

import java.util.List;

import com.excel.bean.StockWithdrawalBean;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SWV_Header;
import com.excel.model.SmpItem;
import com.excel.model.Supplier;
import com.excel.model.V_SWV_Detail;
import com.excel.model.V_SWV_Header;

public interface StockWithdrawalRepository {

	 List<SmpItem> getProdListForSWV(Long loc_id) throws Exception;
	 List<Supplier> getSuppliersBySupplierType(String supplierType,Long subCompId) throws Exception;
	 Supplier supplierAddrById(Long sup_id) throws Exception;
	 List<StockWithdrawalBean> getBatchDetailsByProdStkType(Long loc_id, String stk_type,Long prod_id) throws Exception;
	 StockWithdrawalBean saveSWV(StockWithdrawalBean bean) throws Exception;
	 List<V_SWV_Detail> getSWVDetailsBySWVId(Long swv_id)throws Exception;
	 List<V_SWV_Header> getSWVListForModify(Long loc_id, String comp_cd, String emp_id,String user_type, String from_dt, String to_dt)throws Exception;
	 SWV_Header getSWVHeaderById(Long swv_id) throws Exception;
	 void deleteSWVDetail(String comp_cd, String fin_year,String period_cd, Long swv_id, Long prod_id, Long batch_id,
				String stk_type, String user_id, String ip_addr)throws Exception;
	 V_SWV_Header getSWVHeaderDetailsById(Long swv_id) throws Exception;
	 List<StockWithdrawalBean> getModBatchDetailsByProdStkType(Long loc_id, String stk_type,Long prod_id,Long swv_id) throws Exception;
	 List<SmpItem> getModProdListForSWV(Long loc_id,Long swv_id) throws Exception;
	 List<SG_d_parameters_details> getStockTypeListByProd(Long swv_id,Long prod_id) throws Exception;
	 List<Object> getChallanForLr(Long loc_id) throws Exception;
}
