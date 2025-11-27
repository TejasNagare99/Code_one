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
@Table(name = "ViewGSTR_1_B2CS_INV")
@NamedStoredProcedureQuery(name = "callGSTR_1_B2CS_INV", 
procedureName = "GSTR_1_B2CS_INV",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = ViewGSTR_1_B2CS_INV.class)
public class ViewGSTR_1_B2CS_INV implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;

	@Column(name = "LOC_ID")
	private Long loc_id;

	@Column(name = "LOC_NAME")
	private String loc_name;

	@Column(name = "TYPE")
	private String type;

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

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
