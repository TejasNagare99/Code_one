package com.excel.service;

import java.util.List;

import com.excel.model.EmailIdTranwiseHelp;

public interface EmailTranwiseHelpService {
	List<EmailIdTranwiseHelp> getEmailsForHelp(String tranType,String searchString) throws Exception;
	void saveMailsToHelpTable(List<String> mailsList,String tranType,String finYear) throws Exception;
	boolean mailExists(String emailId,String tranType) throws Exception;
}
