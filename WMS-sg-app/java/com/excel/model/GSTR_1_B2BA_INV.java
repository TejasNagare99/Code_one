package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "GSTR_1_B2BA_INV")
@NamedStoredProcedureQuery(name = "callGSTR_1_B2BA_INV", 
procedureName = "GSTR_1_B2BA_INV",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class)
}, resultClasses = GSTR_1_B2BA_INV.class)


public class GSTR_1_B2BA_INV implements Serializable {
	// FIN_YEAR_FLAG := 'CURRENT';
	// FINID := 11;
	// COMP_CD := 'BOE';
	// ST_DT := '2018-05-01';
	// EN_DT := '2018-05-31';
	// LOCID := '17';

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;

	@Column(name = "LOC_ID")
	private Long loc_id;

	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "GSTIN_UIN_OF_RECIPIENT")
	private String gstin_uin_of_recipient;
	
	@Column(name = "RECEIVER_NAME")
	private String receiver_name;
	
	@Column(name = "ORIGINAL_INVOICE_NUMBER")
	private String original_invoice_number;
	
	@Column(name = "ORIGINAL_INVOICE_DATE")
	private Date original_invoice_date;
	
	@Column(name = "REVISED_INVOICE_NUMBER")
	private String revised_invoice_number;
	
	@Column(name = "REVISED_INVOICE_DATE")
	private Date revised_invoice_date;
	
	@Column(name = "INVOICE_VALUE")
	private BigDecimal invoice_value;
	
	@Column(name = "PLACE_OF_SUPPLY")
	private String place_of_supply;	
	
	@Column(name = "REVERSE_CHARGE")
	private String reverse_charge;	
	
	@Column(name = "INVOICE_TYPE")
	private String invoice_type;	
	
	@Column(name = "E_COMMERCE_GSTIN")
	private String e_commerce_gstin;
	
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

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getOriginal_invoice_number() {
		return original_invoice_number;
	}

	public void setOriginal_invoice_number(String original_invoice_number) {
		this.original_invoice_number = original_invoice_number;
	}

	

	public String getRevised_invoice_number() {
		return revised_invoice_number;
	}

	public void setRevised_invoice_number(String revised_invoice_number) {
		this.revised_invoice_number = revised_invoice_number;
	}

	

	public BigDecimal getInvoice_value() {
		return invoice_value;
	}

	public void setInvoice_value(BigDecimal invoice_value) {
		this.invoice_value = invoice_value;
	}

	public String getPlace_of_supply() {
		return place_of_supply;
	}

	public void setPlace_of_supply(String place_of_supply) {
		this.place_of_supply = place_of_supply;
	}

	public String getReverse_charge() {
		return reverse_charge;
	}

	public void setReverse_charge(String reverse_charge) {
		this.reverse_charge = reverse_charge;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getE_commerce_gstin() {
		return e_commerce_gstin;
	}

	public void setE_commerce_gstin(String e_commerce_gstin) {
		this.e_commerce_gstin = e_commerce_gstin;
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

	public Date getOriginal_invoice_date() {
		return original_invoice_date;
	}

	public void setOriginal_invoice_date(Date original_invoice_date) {
		this.original_invoice_date = original_invoice_date;
	}

	public Date getRevised_invoice_date() {
		return revised_invoice_date;
	}

	public void setRevised_invoice_date(Date revised_invoice_date) {
		this.revised_invoice_date = revised_invoice_date;
	}
	
	
}
