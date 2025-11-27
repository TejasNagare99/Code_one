package com.excel.service;

public interface ApprovalMailValidationService {
	boolean checkIfTransactionAlreadyProcessed(Long tran_ref_id,Long task_id);
	
	void saveAfterMailSend(Long task_id,Long tran_ref_id,String tran_type,Long process_id,String email_id, String user_id,String inititator_id,String ipaddr) throws Exception;

	void updateAfterProcessing(Long tran_ref_id,Long task_id,String user_id,String ip_addr,String status) throws Exception;
	
}
