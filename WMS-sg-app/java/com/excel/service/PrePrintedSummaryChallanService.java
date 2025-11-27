package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.ViewPrePrintedSummaryChallan_GST;

public interface PrePrintedSummaryChallanService {
	public String PrePrintedSummaryChallanPrint(int division, Integer loc, String frm_challan, String to_challan, String dispatchType,
		    String prodtype, String printtype, List<ViewPrePrintedSummaryChallan_GST> challans, String show_amount,
		    String footer_signature_ind,String Companycode,String CompanyName,HttpSession session,HttpServletRequest request) throws Exception;
}
