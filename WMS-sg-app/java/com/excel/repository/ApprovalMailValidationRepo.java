package com.excel.repository;

import com.excel.model.ApprovalMailValidation;

public interface ApprovalMailValidationRepo {

	boolean checkIfTransactionAlreadyProcessed(Long tran_ref_id,Long task_id);
	
	void updateAfterProcessing(Long tran_ref_id,Long task_id,String user_id) throws Exception;
	
	ApprovalMailValidation getByTranRefAndTaskId(Long tran_ref_id,Long task_id,String user_id) throws Exception;
}
