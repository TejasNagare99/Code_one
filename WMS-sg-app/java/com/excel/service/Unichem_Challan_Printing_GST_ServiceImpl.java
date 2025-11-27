package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.repository.Unichem_Challan_Printing_GST_Repository;
@Service
public class Unichem_Challan_Printing_GST_ServiceImpl implements Unichem_Challan_Printing_GST_Service{
		
	@Autowired Unichem_Challan_Printing_GST_Repository unichem_challan_printing_gst_repository;
	
	@Override
	public String getPdf(int division, Long loc, String frm_challan, String to_challan, String dispatchType,
			String prodtype, String printtype, List<PrePrintedDetailChallan_withgst> challans, String show_amount,
			String footer_signature_ind, String Companycode, String Companyname,HttpSession session,HttpServletRequest request) throws Exception {
		
		return unichem_challan_printing_gst_repository.getPdf(division, loc, frm_challan, to_challan, dispatchType, prodtype, printtype, challans, show_amount, footer_signature_ind, Companycode, Companyname,session,request);
	}

	
}