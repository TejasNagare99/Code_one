package com.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.EmailFrom;
import com.excel.repository.EmailRepository;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private EmailRepository emailRepository;

	@Override
	public EmailFrom getEmailObject(String trantype) throws Exception {
		 return emailRepository.getEmailObject(trantype);
	}
}
