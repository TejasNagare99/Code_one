package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "ViewGSTR_1_TRANSFER")
@NamedStoredProcedureQuery(name = "callGSTR_1_TRANSFER", 
procedureName = "GSTR_1_TRANSFER",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = ViewGSTR_1_TRANSFER.class)
public class ViewGSTR_1_TRANSFER implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "LOC_NAME")
	private String loc_name;
	
	@Column(name = "SUPP_GSTIN")
	private String supp_gstin;
	
	@Column(name = "RECEIVING_LOC_ID")
	private Long receiving_loc_id;
	
	@Column(name = "RECEIVER_NAME")
	private String receiver_name;
	
	@Column(name = "SUPPLY_BILL_NO")
	private String supply_bill_no;
	
	@Column(name = "SUPPLY_DATE")
	private String supply_date;
	
	
	@Column(name = "SUP_INV_VALUE")
	private BigDecimal sup_inv_value;
	
	@Column(name = "PLACE_OF_SUPPLY")
	private String place_of_supply;
	
	@Column(name = "REVERSE_CHGS")
	private String reverse_chgs;
	
	@Column(name = "INVOICE_TYPE")
	private String invoice_type;
	
	@Column(name = "E_COMMMERCE_GSTIN")
	private String e_commmerce_gstin;
	
	@Column(name = "RATE")
	private BigDecimal rate;
	
	@Column(name = "TAXABLE_AMT")
	private BigDecimal taxable_amt;
	
	@Column(name = "CESS_AMOUNT")
	private BigDecimal cess_amount;

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getSupp_gstin() {
		return supp_gstin;
	}

	public void setSupp_gstin(String supp_gstin) {
		this.supp_gstin = supp_gstin;
	}

	public Long getReceiving_loc_id() {
		return receiving_loc_id;
	}

	public void setReceiving_loc_id(Long receiving_loc_id) {
		this.receiving_loc_id = receiving_loc_id;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getSupply_bill_no() {
		return supply_bill_no;
	}

	public void setSupply_bill_no(String supply_bill_no) {
		this.supply_bill_no = supply_bill_no;
	}

	public String getSupply_date() {
		return supply_date;
	}

	public void setSupply_date(String supply_date) {
		this.supply_date = supply_date;
	}

	public BigDecimal getSup_inv_value() {
		return sup_inv_value;
	}

	public void setSup_inv_value(BigDecimal sup_inv_value) {
		this.sup_inv_value = sup_inv_value;
	}

	public String getPlace_of_supply() {
		return place_of_supply;
	}

	public void setPlace_of_supply(String place_of_supply) {
		this.place_of_supply = place_of_supply;
	}

	public String getReverse_chgs() {
		return reverse_chgs;
	}

	public void setReverse_chgs(String reverse_chgs) {
		this.reverse_chgs = reverse_chgs;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getE_commmerce_gstin() {
		return e_commmerce_gstin;
	}

	public void setE_commmerce_gstin(String e_commmerce_gstin) {
		this.e_commmerce_gstin = e_commmerce_gstin;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getTaxable_amt() {
		return taxable_amt;
	}

	public void setTaxable_amt(BigDecimal taxable_amt) {
		this.taxable_amt = taxable_amt;
	}

	public BigDecimal getCess_amount() {
		return cess_amount;
	}

	public void setCess_amount(BigDecimal cess_amount) {
		this.cess_amount = cess_amount;
	}
	
	

}
