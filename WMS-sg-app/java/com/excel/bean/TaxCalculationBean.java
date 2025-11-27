package com.excel.bean;

import java.math.BigDecimal;

public class TaxCalculationBean {

	private static final long serialVersionUID = 1L;
	//private String octroi_params;
	private BigDecimal goods_value;
	private BigDecimal party_discount;
	private BigDecimal prod_discount;
	private BigDecimal inward_charges;
	//private BigDecimal oct_bef_tax;
	//private BigDecimal excise;
	//private BigDecimal vat_on_billed;
	//private BigDecimal vat_on_free;
	//private BigDecimal vat_on_repl;
	//private BigDecimal cst;
	//private BigDecimal surch_on_billed;
	//private BigDecimal surch_on_free;
	//private BigDecimal surch_on_repl;
	//private BigDecimal cess_on_billed;
	//private BigDecimal cess_on_free;
	//private BigDecimal cess_on_repl;
	private BigDecimal turnover_tax;
	//private BigDecimal octroi_refund;
	//private BigDecimal octroi_charged;
	//private BigDecimal non_refund_st;
	//private BigDecimal cst_reimburse;
	private BigDecimal spl_cash_disc;
	//private Long alloc_id;
	//private BigDecimal curr_ivn_amt;
	private BigDecimal party_dis_rate;
	private BigDecimal prod_dis_rate;
	//private BigDecimal inward_charges_rate;
	//private BigDecimal octroi_flag;
	//private BigDecimal octroi_rate;
	//private BigDecimal vat_rate;
	//private BigDecimal cst_rate;
	private BigDecimal surch_rate;
	private BigDecimal cess_rate;
	private BigDecimal trun_over_tax_rate;
	private BigDecimal freight;
	private BigDecimal roundoff;
	private BigDecimal taxable_amt_billed;
	private BigDecimal taxable_amt_free;
	
	// newly added for GST
	private BigDecimal cgst_rate;
	private BigDecimal cgst_bill_amount;
	private BigDecimal sgst_rate;
	private BigDecimal sgst_bill_amount;
	private BigDecimal igst_rate;
	private BigDecimal igst_bill_amount;
	private String gst_doc_type;
	public BigDecimal getGoods_value() {
		return goods_value;
	}
	public void setGoods_value(BigDecimal goods_value) {
		this.goods_value = goods_value;
	}
	public BigDecimal getParty_discount() {
		return party_discount;
	}
	public void setParty_discount(BigDecimal party_discount) {
		this.party_discount = party_discount;
	}
	public BigDecimal getProd_discount() {
		return prod_discount;
	}
	public void setProd_discount(BigDecimal prod_discount) {
		this.prod_discount = prod_discount;
	}
	public BigDecimal getInward_charges() {
		return inward_charges;
	}
	public void setInward_charges(BigDecimal inward_charges) {
		this.inward_charges = inward_charges;
	}
	public BigDecimal getTurnover_tax() {
		return turnover_tax;
	}
	public void setTurnover_tax(BigDecimal turnover_tax) {
		this.turnover_tax = turnover_tax;
	}
	public BigDecimal getSpl_cash_disc() {
		return spl_cash_disc;
	}
	public void setSpl_cash_disc(BigDecimal spl_cash_disc) {
		this.spl_cash_disc = spl_cash_disc;
	}
	public BigDecimal getParty_dis_rate() {
		return party_dis_rate;
	}
	public void setParty_dis_rate(BigDecimal party_dis_rate) {
		this.party_dis_rate = party_dis_rate;
	}
	public BigDecimal getProd_dis_rate() {
		return prod_dis_rate;
	}
	public void setProd_dis_rate(BigDecimal prod_dis_rate) {
		this.prod_dis_rate = prod_dis_rate;
	}
	public BigDecimal getSurch_rate() {
		return surch_rate;
	}
	public void setSurch_rate(BigDecimal surch_rate) {
		this.surch_rate = surch_rate;
	}
	public BigDecimal getCess_rate() {
		return cess_rate;
	}
	public void setCess_rate(BigDecimal cess_rate) {
		this.cess_rate = cess_rate;
	}
	public BigDecimal getTrun_over_tax_rate() {
		return trun_over_tax_rate;
	}
	public void setTrun_over_tax_rate(BigDecimal trun_over_tax_rate) {
		this.trun_over_tax_rate = trun_over_tax_rate;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public BigDecimal getRoundoff() {
		return roundoff;
	}
	public void setRoundoff(BigDecimal roundoff) {
		this.roundoff = roundoff;
	}
	public BigDecimal getTaxable_amt_billed() {
		return taxable_amt_billed;
	}
	public void setTaxable_amt_billed(BigDecimal taxable_amt_billed) {
		this.taxable_amt_billed = taxable_amt_billed;
	}
	public BigDecimal getTaxable_amt_free() {
		return taxable_amt_free;
	}
	public void setTaxable_amt_free(BigDecimal taxable_amt_free) {
		this.taxable_amt_free = taxable_amt_free;
	}
	public BigDecimal getCgst_rate() {
		return cgst_rate;
	}
	public void setCgst_rate(BigDecimal cgst_rate) {
		this.cgst_rate = cgst_rate;
	}
	public BigDecimal getCgst_bill_amount() {
		return cgst_bill_amount;
	}
	public void setCgst_bill_amount(BigDecimal cgst_bill_amount) {
		this.cgst_bill_amount = cgst_bill_amount;
	}
	public BigDecimal getSgst_rate() {
		return sgst_rate;
	}
	public void setSgst_rate(BigDecimal sgst_rate) {
		this.sgst_rate = sgst_rate;
	}
	public BigDecimal getSgst_bill_amount() {
		return sgst_bill_amount;
	}
	public void setSgst_bill_amount(BigDecimal sgst_bill_amount) {
		this.sgst_bill_amount = sgst_bill_amount;
	}
	public BigDecimal getIgst_rate() {
		return igst_rate;
	}
	public void setIgst_rate(BigDecimal igst_rate) {
		this.igst_rate = igst_rate;
	}
	public BigDecimal getIgst_bill_amount() {
		return igst_bill_amount;
	}
	public void setIgst_bill_amount(BigDecimal igst_bill_amount) {
		this.igst_bill_amount = igst_bill_amount;
	}
	public String getGst_doc_type() {
		return gst_doc_type;
	}
	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}
	
	
	
}
