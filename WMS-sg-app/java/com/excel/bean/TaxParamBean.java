package com.excel.bean;

import java.math.BigDecimal;

public class TaxParamBean {

	private String prod_code;
	private String output_tax_param;
	private BigDecimal st_vat;
	private BigDecimal cst_rt;
	private BigDecimal surch;
	private BigDecimal ic_chgs;
	private BigDecimal cess;
	private BigDecimal to_tax;
	private BigDecimal prod_disc;
	private BigDecimal cgst;
	private BigDecimal igst;
	private BigDecimal sgst;
	private Long tax_id;
	public String getProd_code() {
		return prod_code;
	}
	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}
	public String getOutput_tax_param() {
		return output_tax_param;
	}
	public void setOutput_tax_param(String output_tax_param) {
		this.output_tax_param = output_tax_param;
	}
	public BigDecimal getSt_vat() {
		return st_vat;
	}
	public void setSt_vat(BigDecimal st_vat) {
		this.st_vat = st_vat;
	}
	public BigDecimal getCst_rt() {
		return cst_rt;
	}
	public void setCst_rt(BigDecimal cst_rt) {
		this.cst_rt = cst_rt;
	}
	public BigDecimal getSurch() {
		return surch;
	}
	public void setSurch(BigDecimal surch) {
		this.surch = surch;
	}
	public BigDecimal getIc_chgs() {
		return ic_chgs;
	}
	public void setIc_chgs(BigDecimal ic_chgs) {
		this.ic_chgs = ic_chgs;
	}
	public BigDecimal getCess() {
		return cess;
	}
	public void setCess(BigDecimal cess) {
		this.cess = cess;
	}
	public BigDecimal getTo_tax() {
		return to_tax;
	}
	public void setTo_tax(BigDecimal to_tax) {
		this.to_tax = to_tax;
	}
	public BigDecimal getProd_disc() {
		return prod_disc;
	}
	public void setProd_disc(BigDecimal prod_disc) {
		this.prod_disc = prod_disc;
	}
	public BigDecimal getCgst() {
		return cgst;
	}
	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}
	public BigDecimal getIgst() {
		return igst;
	}
	public void setIgst(BigDecimal igst) {
		this.igst = igst;
	}
	public BigDecimal getSgst() {
		return sgst;
	}
	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}
	public Long getTax_id() {
		return tax_id;
	}
	public void setTax_id(Long tax_id) {
		this.tax_id = tax_id;
	}
	
}