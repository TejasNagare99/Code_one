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
@Table(name = "ViewGSTR_1_B2CL_INV")
@NamedStoredProcedureQuery(name = "callGSTR_1_B2CL_INV", 
procedureName = "GSTR_1_B2CL_INV",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = ViewGSTR_1_B2CL_INV.class)
public class ViewGSTR_1_B2CL_INV implements Serializable{

	private static final long serialVersionUID = -7046092144924409770L;
	
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;

	@Column(name = "LOC_ID")
	private Long loc_id;

	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "INVOICE_NUMBER")
	private String invoice_number;
	
	@Column(name = "INVOICE_DATE")
	private String invoice_date;
	
	@Column(name = "INVOICE_VALUE")
	private BigDecimal invoice_value;
	
	@Column(name = "PLACE_OF_SUPPLY")
	private String place_of_supply;
	
	@Column(name = "RATE")
	private BigDecimal rate;
	
	@Column(name = "TAXABLE_VALUE")
	private BigDecimal taxable_value;
	
	@Column(name = "CESS_AMOUNT")
	private BigDecimal cess_amount;
	
	@Column(name = "E_COMMERCE_GSTIN")
	private String e_commerce_gstin;

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

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
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

	public String getE_commerce_gstin() {
		return e_commerce_gstin;
	}

	public void setE_commerce_gstin(String e_commerce_gstin) {
		this.e_commerce_gstin = e_commerce_gstin;
	}
	
	


}
