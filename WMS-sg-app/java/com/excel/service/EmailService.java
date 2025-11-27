package com.excel.service;

import org.springframework.stereotype.Service;

import com.excel.model.EmailFrom;
@Service
public interface EmailService {

	EmailFrom getEmailObject(String trantype) throws Exception;


}
