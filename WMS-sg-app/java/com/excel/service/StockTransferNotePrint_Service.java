package com.excel.service;

import java.util.List;

import com.excel.model.StockTransferNotePrint;

public interface StockTransferNotePrint_Service {
	
	String generate_StockTransferPrint(int division, Integer loc, String frm_challan, String to_challan, String dispatchType,
		    String prodtype, String printtype, List<StockTransferNotePrint> challans, String show_amount,
		    String footer_signature_ind,String companycode,String companyname,String userid)throws Exception;
	
}
