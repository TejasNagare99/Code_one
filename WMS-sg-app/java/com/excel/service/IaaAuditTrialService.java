package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.ViewDownloadIaaAuditTrail;

public interface IaaAuditTrialService {

	public String generateIaaAuditTrialPrint(List<ViewDownloadIaaAuditTrail> lst,String companyname,String usernname,String frm_date,String to_date,HttpSession session,HttpServletRequest request) throws Exception;
}