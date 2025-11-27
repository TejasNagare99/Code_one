package com.excel.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.TaxCalculationBean;
import com.excel.repository.CalculateGstRepository;

@Service
public class CalculateGstServiceImpl implements CalculateGstService{
	
	@Autowired CalculateGstRepository calculateGstRepository;

	
	@Override
	public TaxCalculationBean generateGstValues(String docType, String gstType,
			BigDecimal billedQty, BigDecimal freeQty, BigDecimal replQty,
			BigDecimal blgRate, BigDecimal partyDiscount, String Out_Tax_Param,
			String Input_Tax_Param, BigDecimal cgst_rate, BigDecimal igst_rate,
			BigDecimal sgst_rate, BigDecimal Surch_Rate, BigDecimal Cess_Rate,
			BigDecimal TOT_Rate, BigDecimal Prod_Disc, BigDecimal Octroi_Rate,
			String Cust_Type, BigDecimal mrp_diff, BigDecimal trade_disc,
			String company_cd, BigDecimal MRP_Rate, BigDecimal SS_Rate, BigDecimal inward_charges) throws Exception{

		TaxCalculationBean taxBean = new TaxCalculationBean();
		BigDecimal scheme_Disc = Prod_Disc;
		BigDecimal GVAL_BILLED = BigDecimal.ZERO;
		BigDecimal GVAL_FREE = BigDecimal.ZERO;
		BigDecimal TAXABLE_BILLED = BigDecimal.ZERO;
		BigDecimal TAXABLE_FREE = BigDecimal.ZERO;
		BigDecimal CGST_VAL = BigDecimal.ZERO;
		BigDecimal SGST_VAL = BigDecimal.ZERO;
		BigDecimal IGST_VAL = BigDecimal.ZERO;
		BigDecimal PROD_DISC = BigDecimal.ZERO;
		BigDecimal PARTY_DISC = BigDecimal.ZERO;
		BigDecimal discPrc = BigDecimal.ZERO;
		BigDecimal HUNDRED = new BigDecimal(100);
		BigDecimal CGST_RATE = cgst_rate;
		BigDecimal SGST_RATE = sgst_rate;
		BigDecimal IGST_RATE = igst_rate;
		String taxParams = null;
		taxParams = Out_Tax_Param;
		String tax_cal_rate = null;
		Boolean tax_on_free = false;
		Boolean reduce_discount = false;
	
		if (taxParams.charAt(1) == 'S')
			tax_cal_rate = "S";
		else if (taxParams.charAt(1) == 'M')
			tax_cal_rate = "M";
		else if (taxParams.charAt(1) == 'P')
			tax_cal_rate = "P";
		else if (taxParams.charAt(1) == 'A')
			tax_cal_rate = "A"; // END

		if (taxParams.charAt(2) == 'Y')
			tax_on_free = true;
		else
			tax_on_free = false; // END

		if (taxParams.charAt(3) == 'Y')
			reduce_discount = true;
		else
			reduce_discount = false; // END

		// Handling Saleable and Breakage Expiry Credit Notes
		if (docType.equalsIgnoreCase("SA") || docType.equalsIgnoreCase("NS")) {
			// For Credit note Saleable and Breakage Expiry document types
			if (billedQty.compareTo(BigDecimal.ZERO) > 0) {
				GVAL_BILLED = billedQty.multiply(blgRate);
			} else {
				GVAL_BILLED = BigDecimal.ZERO;
			}
		} else { // For all other documents
			GVAL_BILLED = billedQty.multiply(blgRate);
			GVAL_FREE = freeQty.multiply(blgRate);
		}
		
		if (Cust_Type.equalsIgnoreCase("010")) { // For Nepal / subcontinent customers
			partyDiscount = trade_disc;
			if (partyDiscount.compareTo(BigDecimal.ZERO) > 0) {
				blgRate = (partyDiscount.compareTo(BigDecimal.ZERO) > 0) ? blgRate
						.multiply(BigDecimal.ONE.subtract((partyDiscount
								.divide(HUNDRED, MathContext.DECIMAL64))))
						: blgRate;

				taxBean.setParty_dis_rate(partyDiscount.setScale(2,
						RoundingMode.HALF_UP));
				// Since party discount is already applied to the rate, force party discount to zero now.
				PARTY_DISC = BigDecimal.ZERO;
			}

		}else {
			taxBean.setParty_dis_rate(partyDiscount.setScale(2,RoundingMode.HALF_UP));
			PARTY_DISC = GVAL_BILLED.multiply(partyDiscount).divide(HUNDRED, MathContext.DECIMAL64);
			taxBean.setParty_discount(PARTY_DISC.setScale(2,RoundingMode.HALF_UP));
		}

		// Handling Rate Difference Credit and Debit Notes
		if (docType.equalsIgnoreCase("CR") || docType.equalsIgnoreCase("DR")) {
			// For Credit note and Debit Note Rate Difference document types
			//MRP = mrp_diff;
			//MRP_BILLED = billedQty.multiply(MRP);
			//MRP_FREE = freeQty.multiply(MRP);
			//commented and keep outside of if else condition as discussed with Uday sir on 26/05/2017 5pm
			taxBean.setGoods_value(GVAL_BILLED.setScale(2, RoundingMode.HALF_UP));
		} else {
			mrp_diff = BigDecimal.ZERO;
		}
		
		taxBean.setGoods_value(GVAL_BILLED.setScale(2, RoundingMode.HALF_UP));

		// Calculating Product Discount (either from product master or from scheme)
		if (Prod_Disc.compareTo(BigDecimal.ZERO) > 0
				|| scheme_Disc.compareTo(BigDecimal.ZERO) > 0) {
			if (Cust_Type.equalsIgnoreCase("005")) { // Customer type is
														// Institution
				taxBean.setProd_dis_rate(BigDecimal.ZERO);
				PROD_DISC = BigDecimal.ZERO;
			}
			if (scheme_Disc.compareTo(BigDecimal.ZERO) > 0) {
				discPrc = scheme_Disc;
			} else {
				discPrc = Prod_Disc;
			}
		} else {
			taxBean.setProd_dis_rate(BigDecimal.ZERO);
		}
		PROD_DISC = (GVAL_BILLED.multiply(discPrc)).divide(HUNDRED,MathContext.DECIMAL64);
		taxBean.setProd_discount(PROD_DISC.setScale(2, RoundingMode.HALF_UP));
		
		// Calculating Taxable amounts
		if (tax_cal_rate == "S") {
			if (reduce_discount) {
				TAXABLE_BILLED = GVAL_BILLED.subtract(PARTY_DISC).subtract(PROD_DISC);
			} else {
				TAXABLE_BILLED = GVAL_BILLED;
			}
			if (tax_on_free) {
				TAXABLE_FREE = GVAL_FREE;
			}
			
			//added inward charges for GRN- custom_duty, discount
			TAXABLE_BILLED = TAXABLE_BILLED.add(inward_charges);
			
			// Inter state or IGST Parameter is passed to this method by Comparing the location state and the customer state
			if (gstType.equalsIgnoreCase("I")) {
				IGST_VAL = (TAXABLE_BILLED.multiply(IGST_RATE)).divide(HUNDRED,	MathContext.DECIMAL64);
				CGST_RATE = BigDecimal.ZERO;
				SGST_RATE = BigDecimal.ZERO;
			} else if (gstType.equalsIgnoreCase("G")){
				SGST_VAL = (TAXABLE_BILLED.multiply(SGST_RATE)).divide(HUNDRED,	MathContext.DECIMAL64);
				CGST_VAL = (TAXABLE_BILLED.multiply(CGST_RATE)).divide(HUNDRED,	MathContext.DECIMAL64);
				IGST_RATE = BigDecimal.ZERO;
			}
		}
		taxBean.setTaxable_amt_billed(TAXABLE_BILLED.setScale(2,RoundingMode.HALF_UP));
		taxBean.setTaxable_amt_free(TAXABLE_FREE.setScale(2, RoundingMode.HALF_UP));
		taxBean.setSgst_rate(SGST_RATE);
		taxBean.setSgst_bill_amount(SGST_VAL);
		taxBean.setCgst_rate(CGST_RATE);
		taxBean.setCgst_bill_amount(CGST_VAL);
		taxBean.setIgst_bill_amount(IGST_VAL);
		taxBean.setIgst_rate(IGST_RATE);
		return taxBean;
	}

	@Override
	public String getGST_Doc_type_Para_code(String tran_type, String gst_doc_type) throws Exception {
		return calculateGstRepository.getGST_Doc_type_Para_code(tran_type, gst_doc_type);
	}

}
