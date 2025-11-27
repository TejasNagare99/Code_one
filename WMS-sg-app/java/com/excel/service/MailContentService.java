package com.excel.service;

public interface MailContentService {
	
	String mailContentForSendNewPassword(String fs_name, String password) throws Exception;
	String mailContentForSendLockNotification(String fs_name)throws Exception;
	String MailContentForAdminUserUnlock(String fs_name) throws Exception;
	String mailContentForSendNewPasswordNotification(String fs_name,String expirydate) throws Exception;
	public String mailContentForSendNewOTP(String fs_name, String password) throws Exception;
}
