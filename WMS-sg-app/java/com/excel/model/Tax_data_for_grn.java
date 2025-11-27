package com.excel.model;

import java.math.BigDecimal;

public class Tax_data_for_grn {

	private BigDecimal grn_total_value;
	private BigDecimal gen_total_value_pur;
	private BigDecimal sgst_total;
	private BigDecimal cgst_total;
	private BigDecimal igst_total;
	
	
	public BigDecimal getGrn_total_value() {
		return grn_total_value;
	}
	public void setGrn_total_value(BigDecimal grn_total_value) {
		this.grn_total_value = grn_total_value;
	}
	public BigDecimal getGen_total_value_pur() {
		return gen_total_value_pur;
	}
	public void setGen_total_value_pur(BigDecimal gen_total_value_pur) {
		this.gen_total_value_pur = gen_total_value_pur;
	}
	public BigDecimal getSgst_total() {
		return sgst_total;
	}
	public void setSgst_total(BigDecimal sgst_total) {
		this.sgst_total = sgst_total;
	}
	public BigDecimal getCgst_total() {
		return cgst_total;
	}
	public void setCgst_total(BigDecimal cgst_total) {
		this.cgst_total = cgst_total;
	}
	public BigDecimal getIgst_total() {
		return igst_total;
	}
	public void setIgst_total(BigDecimal igst_total) {
		this.igst_total = igst_total;
	}
	@Override
	public String toString() {
		return "Tax_data_for_grn [grn_total_value=" + grn_total_value + ", gen_total_value_pur=" + gen_total_value_pur
				+ ", sgst_total=" + sgst_total + ", cgst_total=" + cgst_total + ", igst_total=" + igst_total + "]";
	}
	
	
	
}
