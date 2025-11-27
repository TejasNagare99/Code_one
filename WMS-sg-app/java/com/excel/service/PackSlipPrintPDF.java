package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.ViewPrePrintedDetailChallan;

public interface PackSlipPrintPDF {
	public String getPdf(Integer division, Integer loc, String frm_challan, String to_challan, String dispatchType,
		    String prodtype, String printtype, List<ViewPrePrintedDetailChallan> challans, String footer_signature_ind,String companyname,String companycode, HttpSession session,HttpServletRequest request)
		    throws Exception ;
 
	 
	 
}
