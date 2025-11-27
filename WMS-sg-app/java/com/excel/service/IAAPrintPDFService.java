package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.ViewIAAVoucherPrinting;

public interface IAAPrintPDFService {

	public String generateIaaPrint(List<ViewIAAVoucherPrinting> lst,String companyname,String companycode,HttpSession session,HttpServletRequest request) throws Exception;
	
	public String generateIaaExcel(List<ViewIAAVoucherPrinting> lst,String companyname,String companycode,String empId)throws Exception;
}
