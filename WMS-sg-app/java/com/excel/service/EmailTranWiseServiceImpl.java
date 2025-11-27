package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.EmailId_Tranwise;
import com.excel.repository.EmailTranWiseRepository;
import com.excel.repository.TransactionalRepository;
@Service
public class EmailTranWiseServiceImpl implements EmailTranWiseService{

	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired EmailTranWiseRepository emailtranwiserepository;
	
	@Override
	public void saveEmailTranWise(List<String> emails, String trantype, Long tranId,String finyr) throws Exception {
		// TODO Auto-generated method stub
		EmailId_Tranwise emailtranwise = null;
		try {
			for(int i=0;i<emails.size();i++) {
				emailtranwise = new EmailId_Tranwise();
				
				emailtranwise.setTran_type(trantype);
				emailtranwise.setFin_year(finyr);
				emailtranwise.setTran_id(tranId);
				emailtranwise.setEmail_srno(i);
				emailtranwise.setEmail_id(emails.get(i));
				transactionalRepository.persist(emailtranwise);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void DeleteEmailTranwiseByTranId(String trantype, Long tran_id, String finyr) throws Exception {
		// TODO Auto-generated method stub
		this.emailtranwiserepository.DeleteEmailTranwiseByTranId(trantype, tran_id, finyr);
	}
	
	@Override
	public List<String> getEmailListByTranType(String trantype, String finyrId, Long tranId)
			throws Exception {
		// TODO Auto-generated method stub
		return emailtranwiserepository.getEmailListByTranType(trantype, finyrId, tranId);
	}
}
