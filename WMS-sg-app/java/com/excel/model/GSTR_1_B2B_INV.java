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

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "GSTR_1_B2B_INV")
@NamedStoredProcedureQuery(name = "callGSTR_1_B2BIN_INV", 
procedureName = "GSTR_1_B2B_INV",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class)
}, resultClasses = GSTR_1_B2B_INV.class)


public class GSTR_1_B2B_INV implements Serializable {
	// FIN_YEAR_FLAG := 'CURRENT';
	// FINID := 11;
	// COMP_CD := 'BOE';
	// ST_DT := '2018-05-01';
	// EN_DT := '2018-05-31';
	// LOCID := '17';

	private static final long serialVersionUID = 11313444654654L;
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;

	@Column(name = "LOC_ID")
	private Long loc_id;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "GSTIN_UIN_OF_RECIPIENT")
	private String gstin_uin_of_recipient;

	@Column(name = "CUST_ID")
	private Long cust_id;
	
	@Column(name = "RECEIVER_NAME")
	private String receiver_name;

	@Column(name = "INVOICE_NUMBER")
	private String invoice_number;

	@Column(name = "invoice_value")
	private BigDecimal INVOICE_VALUE;

	@Column(name = "INVOICE_DATE")
	private String invoice_date;
	
	@Column(name = "PLACE_OF_SUPPLY")
	private String place_of_supply;
	
	@Column(name = "INVOICE_TYPE")
	private String invoice_type;
	
	@Column(name = "REVERSE_CHARGE")
	private String reverse_charge;
	
	@Column(name = "E_COMMMERCE_GSTIN")
	private String e_commmerce_gstin;
	
	@Column(name = "RATE")
	private BigDecimal rate;
	
	@Column(name = "TAXABLE_VALUE")
	private BigDecimal taxable_value;
	
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGstin_uin_of_recipient() {
		return gstin_uin_of_recipient;
	}

	public void setGstin_uin_of_recipient(String gstin_uin_of_recipient) {
		this.gstin_uin_of_recipient = gstin_uin_of_recipient;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public BigDecimal getINVOICE_VALUE() {
		return INVOICE_VALUE;
	}

	public void setINVOICE_VALUE(BigDecimal iNVOICE_VALUE) {
		INVOICE_VALUE = iNVOICE_VALUE;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getPlace_of_supply() {
		return place_of_supply;
	}

	public void setPlace_of_supply(String place_of_supply) {
		this.place_of_supply = place_of_supply;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getReverse_charge() {
		return reverse_charge;
	}

	public void setReverse_charge(String reverse_charge) {
		this.reverse_charge = reverse_charge;
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

	public BigDecimal getTaxable_value() {
		return taxable_value;
	}

	public void setTaxable_value(BigDecimal taxable_value) {
		this.taxable_value = taxable_value;
	}
	
	public BigDecimal getCess_amount() {
		return cess_amount;
	}

	public void setCess_amount(BigDecimal cess_amount) {
		this.cess_amount = cess_amount;
	}
}
