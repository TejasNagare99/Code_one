package com.excel.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.excel.bean.StockTransferBean;
import com.excel.model.BatchStockLog;
import com.excel.model.BatchStockLogNS;
import com.excel.model.SmpBatch;
import com.excel.model.SmpBatchNS;
import com.excel.model.SmpItem;
import com.excel.model.Stock_Transfer_Details;
import com.excel.model.Stock_Transfer_Header;

public interface StockTransferService {
	
	List<StockTransferBean> getProductListBySendrecLoc(Long sendLocId,Long sendSubCompId,Long sendStateId,String nilGstInd,String stockType) throws Exception;
	List<Object> getbatchByProdAndLoc(Long sendLocId,Long prodId,String tranType,String stockType) throws Exception;
	Map<String, Object> saveStkTrf(StockTransferBean stockBean) throws Exception;
	Stock_Transfer_Header saveStkHeader(StockTransferBean stockBean) throws Exception;
	void createbatch(SmpBatch sendBatch, String tran_date, Long loc_id,String user_id, String ip_addr) throws Exception;
	void batchUpdate(SmpBatch obatch, BigDecimal sold_qty) throws Exception;
	void setDateWiseStockSaleable(BatchStockLog dtStock, SmpBatch sendBatch, SmpItem product_Master,
			String fin_year, Date trf_dt, Long send_loc_id, BigDecimal billed_qty, BigDecimal rate,
			String tran_type, String comp_cd,String user_id, String ip_addr) throws Exception;
	
	void createbatchNS(SmpBatchNS sendBatch, String tran_date, Long loc_id,String user_id, String ip_addr) throws Exception;
	void batchUpdateNS(SmpBatchNS obatch, BigDecimal sold_qty) throws Exception;
	void setDateWiseStockSaleableNS(BatchStockLogNS dtStock, SmpBatchNS sendBatch, SmpItem product_Master,
			String fin_year, Date trf_dt, Long send_loc_id, BigDecimal billed_qty, BigDecimal rate,
			String tran_type, String comp_cd,String user_id, String ip_addr) throws Exception;
	List<StockTransferBean> getSTProdDetails(Long header_id,Long sendLocId) throws Exception;
	List<Stock_Transfer_Header> getDtlByDateAndLoc(Long send_loc_id, Long rec_loc_id, Date date,String stkTrfType) throws Exception;
	List<StockTransferBean> getAddProdListForStkMod(Long sendLocId,String trf_id,Long subCompId,String nilGstInd,Long sendStateId,String stockType) throws Exception;
	List<Object> getStockTransferDtForTrfIDAndProdID(Long trfId,Long prodId) throws Exception;
	void modifyStock(StockTransferBean stockBean) throws Exception;
	void deleteStockTrf(StockTransferBean stockBean) throws Exception;
	void deleteStockTransferDtForProd(Long trf_id,Long prod_id,String fullDelete,Stock_Transfer_Header ostock,StockTransferBean stockBean) throws Exception;
	List<StockTransferBean> getSTProdDetailsForDelete(Long header_id,Long sendLocId) throws Exception;
	List<Object> getStockTransferHdForLREntry(Long rec_loc_id, Long send_loc_id, String year_flag,
			Long fin_year_id, String comp_cd) throws Exception;
	void saveStkTrfLr(StockTransferBean stockBean) throws Exception;
	List<Object>getSgstIgst(Long dptLocId,Long prodId)throws Exception;
}
