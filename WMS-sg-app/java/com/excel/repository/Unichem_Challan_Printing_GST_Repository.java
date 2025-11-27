package com.excel.repository;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.PrePrintedDetailChallan_withgst;

public interface Unichem_Challan_Printing_GST_Repository {

	public String getPdf(int division, Long loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String printtype, List<PrePrintedDetailChallan_withgst> challans, String show_amount,
			String footer_signature_ind,String Companycode,String Companyname,HttpSession session,HttpServletRequest request) throws Exception;
}