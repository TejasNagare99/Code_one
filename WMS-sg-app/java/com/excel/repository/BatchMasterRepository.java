package com.excel.repository;

import java.util.List;

import com.excel.model.SmpBatch;
import com.excel.model.SmpBatchNS;
import com.excel.model.ViewSmpBatch;

public interface BatchMasterRepository {

	SmpBatch getObjectById(Long batchId) throws Exception;
	SmpBatchNS getNSObjectById(Long batchId) throws Exception;
	boolean getSmpBatchByBatchNumbar(String batchNumber) throws Exception;
	SmpBatchNS getNSObjectByIdAndStocktype(Long batchId, Long prodId, Long locId, String stockType) throws Exception;
	boolean checkIfNSBatchStockIsNegative(Long batchId, Long prodId, Long locId, String stockType) throws Exception;
	boolean checkIfNSBatchStockIsNegativeByBatchIdProdIdLocId(Long batchId, Long prodId, Long locId, String stockType)
			throws Exception;
	boolean checkIfBatchStockIsNegativeByBatchIdProdIdLocId(Long batchId, Long prodId, Long locId) throws Exception;
	boolean checkIfBatchStockIsNegative(Long batchId, Long prodId, Long locId) throws Exception;
	public Boolean checkDuplicateRecordStk(Long rec_loc_id, String batch_no, Long product_id) throws Exception;
	public Boolean checkDuplicateRecordStkNs(Long rec_loc_id, String batch_no, Long product_id,String stockType) throws Exception;
	List<SmpBatch> getBatchListByProdId(String prodId);
	List<ViewSmpBatch> getBatchListByFilter(String search_by,String param_id, String search_text, int loc_id)throws Exception;
	SmpBatch batchByBatchNoProdId(String batch_no, Long product_id) throws Exception;
	SmpBatchNS NsBatchByBatchNoProdId(String batch_no, Long product_id) throws Exception;
	List<SmpBatch> getBatchListByProdId_and_LocId(String prodId, String emp_loc);

}
