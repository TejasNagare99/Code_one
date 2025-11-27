package com.excel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.EmailIdTranwiseHelp;
import com.excel.repository.EmailTranwiseHelpRepository;
import com.excel.repository.TransactionalRepository;
@Service
public class EmailTranwiseHelpServiceImpl implements EmailTranwiseHelpService {
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired EmailTranwiseHelpRepository emailTranwiseHelpRepo;
	@Override
	public List<EmailIdTranwiseHelp> getEmailsForHelp(String tranType, String searchString) throws Exception {
		return this.emailTranwiseHelpRepo.getEmailsForHelp(tranType, searchString);
	}
	@Override
	public void saveMailsToHelpTable(List<String> mailsList, String tranType, String finYear) throws Exception {
		EmailIdTranwiseHelp emailHelp=null;
		try {
			if(mailsList!=null && !mailsList.isEmpty()) {
			for(String mailId:mailsList) {
				if(!this.mailExists(mailId, tranType)) {
					emailHelp = new EmailIdTranwiseHelp();
					emailHelp.setEmail_id(mailId);
					emailHelp.setFin_year(finYear);
					emailHelp.setTran_type(tranType);
					emailHelp.setEmail_srno(0);
					//save email
					this.transactionalRepository.persist(emailHelp);
				}
			}
		}
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	@Override
	public boolean mailExists(String emailId, String tranType) throws Exception {
		return this.emailTranwiseHelpRepo.mailExists(emailId, tranType);
	}

}
