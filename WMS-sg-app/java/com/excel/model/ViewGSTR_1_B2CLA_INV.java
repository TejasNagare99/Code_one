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
@Table(name = "ViewGSTR_1_B2CLA_INV")
@NamedStoredProcedureQuery(name = "callGSTR_1_B2CLA_INV", 
procedureName = "GSTR_1_B2CLA_INV",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = ViewGSTR_1_B2CLA_INV.class)
public class ViewGSTR_1_B2CLA_INV implements Serializable{

	private static final long serialVersionUID = 2177376574506905414L;
	
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "ORIGINAL_INVOICE_NUMBER")
	private String original_invoice_number;
	
	@Column(name = "ORIGINAL_INVOICE_DATE")
	private String original_invoice_date;
	
	@Column(name = "ORG_PLACE_OF_SUPPLY")
	private String org_place_of_supply;
	
	@Column(name = "REVISED_INVOICE_NUMBER")
	private String revised_invoice_number;
	
	@Column(name = "REVISED_INVOICE_DATE")
	private String revised_invoice_date;
	
	@Column(name = "INVOICE_VALUE")
	private BigDecimal invoice_value;
	
	@Column(name = "RATE")
	private Long rate;
	
	@Column(name = "TAXABLE_VALUE")
	private BigDecimal taxable_value;
	
	@Column(name = "CESS_AMOUNT")
	private BigDecimal cess_amount;
	
	@Column(name = "E_COM_GSTIN")
	private String e_com_gstin;

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

	public String getOriginal_invoice_number() {
		return original_invoice_number;
	}

	public void setOriginal_invoice_number(String original_invoice_number) {
		this.original_invoice_number = original_invoice_number;
	}

	public String getOriginal_invoice_date() {
		return original_invoice_date;
	}

	public void setOriginal_invoice_date(String original_invoice_date) {
		this.original_invoice_date = original_invoice_date;
	}

	public String getOrg_place_of_supply() {
		return org_place_of_supply;
	}

	public void setOrg_place_of_supply(String org_place_of_supply) {
		this.org_place_of_supply = org_place_of_supply;
	}

	public String getRevised_invoice_number() {
		return revised_invoice_number;
	}

	public void setRevised_invoice_number(String revised_invoice_number) {
		this.revised_invoice_number = revised_invoice_number;
	}

	public String getRevised_invoice_date() {
		return revised_invoice_date;
	}

	public void setRevised_invoice_date(String revised_invoice_date) {
		this.revised_invoice_date = revised_invoice_date;
	}

	public BigDecimal getInvoice_value() {
		return invoice_value;
	}

	public void setInvoice_value(BigDecimal invoice_value) {
		this.invoice_value = invoice_value;
	}

	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
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

	public String getE_com_gstin() {
		return e_com_gstin;
	}

	public void setE_com_gstin(String e_com_gstin) {
		this.e_com_gstin = e_com_gstin;
	}
	
	

}
