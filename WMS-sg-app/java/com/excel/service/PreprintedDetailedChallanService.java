package com.excel.service;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.PrePrintedDetailChallan_withgst;
import com.excel.model.PrePrintedDetailChallan_withgstPG;
import com.excel.model.ViewPrePrintedDetailChallan;

public interface PreprintedDetailedChallanService {

	String getPreprintedDetailedChallanPrint(Integer division, Integer loc, String frm_challan, String to_challan, String dispatchType,
		    String prodtype, String printtype, List<PrePrintedDetailChallan_withgst> challans, String show_amount,
		    String footer_signature_ind,String companyCode,String companyName,HttpSession session,HttpServletRequest request) throws Exception;

	String getPreprintedDetailedChallanPrintNoGst(Integer division, Integer loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String printtype, List<ViewPrePrintedDetailChallan> challans,
			String show_amount, String footer_signature_ind, String companyCode, String companyName,
			HttpSession session,String loc_narration,HttpServletRequest request) throws Exception;
	String  PrePrintedDetailedChallanDeclaration(Integer division, Integer loc, String frm_challan, String to_challan, String dispatchType,
		    String prodtype,List<ViewPrePrintedDetailChallan> challans,HttpSession session) throws Exception;
	
	
	String getPreprintedDetailedChallanPrintPG(Integer division, Integer loc, String frm_challan, String to_challan, String dispatchType,
		    String prodtype, String printtype, List<PrePrintedDetailChallan_withgstPG> challans, String show_amount,
		    String footer_signature_ind,String companyCode,String companyName,HttpSession session,HttpServletRequest request) throws Exception;

	String getPreprintedDetailedChallanPrintNoGst_MDL(Integer division, Integer loc, String frm_challan, String to_challan,
			String dispatchType, String prodtype, String printtype, List<ViewPrePrintedDetailChallan> challans,
			String show_amount, String footer_signature_ind, String companyCode, String companyName,
			HttpSession session,String loc_narration,HttpServletRequest request) throws FileNotFoundException, Exception;
	
}