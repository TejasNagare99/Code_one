package com.excel.repository;

import java.util.List;

import com.excel.model.EmailIdTranwiseHelp;

public interface EmailTranwiseHelpRepository {
	List<EmailIdTranwiseHelp> getEmailsForHelp(String tranType,String searchString) throws Exception;
	boolean mailExists(String emailId,String tranType) throws Exception;
}
