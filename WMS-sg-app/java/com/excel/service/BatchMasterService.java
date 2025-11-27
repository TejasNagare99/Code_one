package com.excel.service;

import java.util.List;

import com.excel.bean.BatchMasterBean;
import com.excel.model.SmpBatch;
import com.excel.model.ViewSmpBatch;

public interface BatchMasterService {
	
	boolean getSmpBatchByBatchNumbar(String batchNumber) throws Exception;
	SmpBatch getObjectById(Long batchId) throws Exception;
	List<SmpBatch> getBatchListByProdId(String prodId);
	void saveBatch(BatchMasterBean bean) throws Exception;
	Boolean checkDuplicateRecordStk(Long rec_loc_id, String batch_no, Long product_id) throws Exception;
	List<ViewSmpBatch> getBatchListByFilter(String search_by,String param_id, String search_text, int loc_id)throws Exception;
	void modifyBatch(BatchMasterBean bean) throws Exception;
	List<SmpBatch> getBatchListByProdId_and_LocId(String prodId, String emp_loc);
}
