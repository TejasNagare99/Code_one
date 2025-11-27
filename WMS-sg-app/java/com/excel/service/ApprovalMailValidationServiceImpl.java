package com.excel.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.ApprovalMailValidation;
import com.excel.repository.ApprovalMailValidationRepo;
import com.excel.repository.TransactionalRepository;

@Service
public class ApprovalMailValidationServiceImpl implements ApprovalMailValidationService {
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired ApprovalMailValidationRepo appr_mail_vali_repo;
	
	@Override
	public boolean checkIfTransactionAlreadyProcessed(Long tran_ref_id,Long task_id) {
		return appr_mail_vali_repo.checkIfTransactionAlreadyProcessed(tran_ref_id,task_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveAfterMailSend(Long task_id,Long tran_ref_id, String tran_type, Long process_id, String email_id, String user_id,String inititator_id,String ipaddr) throws Exception{
		ApprovalMailValidation appr_mail_vali_obj = new ApprovalMailValidation();
		appr_mail_vali_obj.setTranrefid(tran_ref_id);
		appr_mail_vali_obj.setTrantype(tran_type);
		appr_mail_vali_obj.setProcessid(process_id);
		appr_mail_vali_obj.setEmailid_of_approver(email_id);
		appr_mail_vali_obj.setApprover_user_id(user_id);
		appr_mail_vali_obj.setStatus("N");
		appr_mail_vali_obj.setAmv_status("A");
		appr_mail_vali_obj.setApproval_datetime(new Date());
		appr_mail_vali_obj.setAmv_ins_usr_id(inititator_id);
		appr_mail_vali_obj.setAmv_ins_ip_add(ipaddr);
		appr_mail_vali_obj.setAmv_ins_dt(new Date());
		appr_mail_vali_obj.setTask_id(task_id);
		this.transactionalRepository.persist(appr_mail_vali_obj);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAfterProcessing(Long tran_ref_id, Long task_id, String user_id, String ip_addr,String status) throws Exception {
		ApprovalMailValidation appr_mail_vali_obj = this.appr_mail_vali_repo.getByTranRefAndTaskId(tran_ref_id, task_id, user_id);
		appr_mail_vali_obj.setStatus(status);
		appr_mail_vali_obj.setAmv_mod_dt(new Date());
		appr_mail_vali_obj.setAmv_mod_ip_add(ip_addr);
		appr_mail_vali_obj.setAmv_mod_usr_id(user_id);
		this.transactionalRepository.update(appr_mail_vali_obj);
	}

	
	
}
