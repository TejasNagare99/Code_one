package com.excel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.excel.model.ViewGrnGSTVoucherPrinting;


public interface GrnVoucherPrintGstService {
	
	String getGrnVocuherGstPrint(int loc_id, int vender_id, String frmgrn, String togrn,
		    List<ViewGrnGSTVoucherPrinting> challans, boolean checkMe,String company_code,String companyName,HttpSession session,HttpServletRequest request) throws Exception;

}
