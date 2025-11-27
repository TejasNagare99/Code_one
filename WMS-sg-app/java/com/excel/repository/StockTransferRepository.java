package com.excel.repository;

import java.util.Date;
import java.util.List;

import com.excel.bean.StockTransferBean;
import com.excel.model.GrnHeader;
import com.excel.model.Stock_Transfer_Details;
import com.excel.model.Stock_Transfer_Header;

public interface StockTransferRepository {

	List<StockTransferBean> getProductListBySendrecLoc(Long sendLocId,Long sendSubCompId,Long sendStateId,String nilGstInd,String stockType) throws Exception;
	List<Object> getbatchByProdAndLoc(Long sendLocId,Long prodId,String tranType,String stockType) throws Exception;
	Stock_Transfer_Header getObjectById(Long headerId) throws Exception;
	List<StockTransferBean> getSTProdDetails(Long header_id,Long sendLocId) throws Exception;
	List<Stock_Transfer_Header> getDtlByDateAndLoc(Long send_loc_id, Long rec_loc_id, Date date,String stkTrfType) throws Exception;
	List<StockTransferBean> getAddProdListForStkMod(Long sendLocId,String trf_id,Long subCompId,String nilGstInd,Long sendStateId,String stockType) throws Exception;
	List<Object> getStockTransferDtForTrfIDAndProdID(Long trfId,Long prodId) throws Exception;
	Stock_Transfer_Details getDetailById(Long detailId) throws Exception;
	List<Long> getDistProdForTrfId(Long header_id) throws Exception;
	List<Stock_Transfer_Details> deleteStockTransferDtForTrfIDAndProdID(Long trf_id, Long prod_id) throws Exception;
	List<StockTransferBean> getSTProdDetailsForDelete(Long header_id,Long sendLocId) throws Exception;
	List<Object> getStockTransferHdForLREntry(Long rec_loc_id, Long send_loc_id, String year_flag,
			Long fin_year_id, String comp_cd) throws Exception;
	Integer grnInTransitStkTrf(StockTransferBean bean) throws Exception;
	List<Object> getSgstIgst(Long dptLocId, Long prodId);

}
