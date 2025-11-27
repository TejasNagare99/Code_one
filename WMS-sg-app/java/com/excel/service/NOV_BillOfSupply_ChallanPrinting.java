package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.excel.model.NOV_BillOfSupply_Challan;


public interface NOV_BillOfSupply_ChallanPrinting {
	
	 public String getPdfgetPdf(String division, String loc, String frm_challan, String to_challan, String dispatchType,
			 String prodtype, String printtype, List<NOV_BillOfSupply_Challan> challans, String show_amount,HttpSession session,
			    String footer_signature_ind) throws Exception ;
}
