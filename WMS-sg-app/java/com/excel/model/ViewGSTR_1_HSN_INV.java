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
@Table(name = "ViewGSTR_1_HSN_INV")
@NamedStoredProcedureQuery(name = "callGSTR_1_HSN_INV", 
procedureName = "GSTR_1_HSN_INV",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = ViewGSTR_1_HSN_INV.class)
public class ViewGSTR_1_HSN_INV implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "HSN")
	private String hsn;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "UQC")
	private String uqc;
	
	@Column(name = "TOTAL_QUANTITY")
	private BigDecimal total_quantity;
	
	@Column(name = "TOTAL_VALUE")
	private BigDecimal total_value;
	
	@Column(name = "TAXABLE_VALUE")
	private BigDecimal taxable_value;
	
	@Column(name = "INTEGRATED_TAX_AMOUNT")
	private BigDecimal integrated_tax_amount;
	
	@Column(name = "CENTRAL_TaX_AMOUNT")
	private BigDecimal central_tax_amount;
	
	@Column(name = "STATE_TAX_AMOUNT")
	private BigDecimal state_tax_amount;
	
	@Column(name = "CESS_AMOUNT")
	private BigDecimal cess_amount;
	
	@Column(name = "GST_RATE")
	private BigDecimal gst_rate;
	
	

	public BigDecimal getGst_rate() {
		return gst_rate;
	}

	public void setGst_rate(BigDecimal gst_rate) {
		this.gst_rate = gst_rate;
	}

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

	public String getHsn() {
		return hsn;
	}

	public void setHsn(String hsn) {
		this.hsn = hsn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUqc() {
		return uqc;
	}

	public void setUqc(String uqc) {
		this.uqc = uqc;
	}

	public BigDecimal getTotal_quantity() {
		return total_quantity;
	}

	public void setTotal_quantity(BigDecimal total_quantity) {
		this.total_quantity = total_quantity;
	}

	public BigDecimal getTotal_value() {
		return total_value;
	}

	public void setTotal_value(BigDecimal total_value) {
		this.total_value = total_value;
	}

	public BigDecimal getTaxable_value() {
		return taxable_value;
	}

	public void setTaxable_value(BigDecimal taxable_value) {
		this.taxable_value = taxable_value;
	}

	public BigDecimal getIntegrated_tax_amount() {
		return integrated_tax_amount;
	}

	public void setIntegrated_tax_amount(BigDecimal integrated_tax_amount) {
		this.integrated_tax_amount = integrated_tax_amount;
	}

	public BigDecimal getCentral_tax_amount() {
		return central_tax_amount;
	}

	public void setCentral_tax_amount(BigDecimal central_tax_amount) {
		this.central_tax_amount = central_tax_amount;
	}

	public BigDecimal getState_tax_amount() {
		return state_tax_amount;
	}

	public void setState_tax_amount(BigDecimal state_tax_amount) {
		this.state_tax_amount = state_tax_amount;
	}

	public BigDecimal getCess_amount() {
		return cess_amount;
	}

	public void setCess_amount(BigDecimal cess_amount) {
		this.cess_amount = cess_amount;
	}
	
	

}
