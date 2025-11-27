package com.excel.service;

import java.math.BigDecimal;

import com.excel.bean.TaxCalculationBean;

public interface CalculateGstService {

	TaxCalculationBean generateGstValues(String docType, String gstType, BigDecimal billedQty, BigDecimal freeQty,
			BigDecimal replQty, BigDecimal blgRate, BigDecimal partyDiscount, String Out_Tax_Param,
			String Input_Tax_Param, BigDecimal cgst_rate, BigDecimal igst_rate, BigDecimal sgst_rate,
			BigDecimal Surch_Rate, BigDecimal Cess_Rate, BigDecimal TOT_Rate, BigDecimal Prod_Disc,
			BigDecimal Octroi_Rate, String Cust_Type, BigDecimal mrp_diff, BigDecimal trade_disc, String company_cd,
			BigDecimal MRP_Rate, BigDecimal SS_Rate, BigDecimal inward_charges) throws Exception;
	String getGST_Doc_type_Para_code(String tran_type, String gst_doc_type) throws Exception;

}
