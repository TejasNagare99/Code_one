package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.model.PrePrintedDetailChallan_withgst_pal;

public interface PrePrintedDetailChallanPalsonService {
	
	String getPreprintedDetailedChallanPrintPalson(Integer division, Integer loc, String frm_challan, String to_challan, String dispatchType,
		    String prodtype, String printtype, List<PrePrintedDetailChallan_withgst_pal> challans, String show_amount,
		    String footer_signature_ind,String companyCode,String companyName,HttpSession session) throws Exception;

}
