package com.excel.repository;

import java.util.Date;

import com.excel.model.BatchStockLog;
import com.excel.model.BatchStockLogNS;

public interface BatchStockLogRepository {

	BatchStockLogNS getNSRecordByProdId(Long ProdId, Long batchId, Long locId) throws Exception;

	BatchStockLog getRecordByProdId(Long ProdId, Long batchId, Long locId) throws Exception;

	BatchStockLog getRecordByProdIdAndDate(Long ProdId, Long batchId, Long locId) throws Exception;

	BatchStockLog getRecordByProdIdNew(Long ProdId, Long batchId, Long locId, Date date,String tranType) throws Exception;

	BatchStockLogNS getNSRecordByProdIdNew(Long ProdId, Long batchId, Long locId, Date date, String stockType,String trantype)throws Exception;
	
	BatchStockLog getRecordByProdIdBatchIdLocIdTranType(Long batch_id, Long prod_id,Long loc_id, Date date, String tran_type) throws Exception; 

}
