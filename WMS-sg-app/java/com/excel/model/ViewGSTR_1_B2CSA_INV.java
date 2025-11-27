package com.excel.model;
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
@Table(name = "GSTR_1_B2CSA_INV")
@NamedStoredProcedureQuery(name = "callGSTR_1_B2CSA_INV", 
procedureName = "GSTR_1_B2CSA_INV",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class)
}, resultClasses = ViewGSTR_1_B2CSA_INV.class)



public class ViewGSTR_1_B2CSA_INV {
	
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;

	@Column(name = "LOC_ID")
	private Long loc_id;

	@Column(name = "LOC_NAME")
	private String loc_name;
	
	@Column(name = "FIN_YEAR")
	private Long fin_year;
	
	@Column(name = "ORG_MONTH")
	private String org_month;
	
	@Column(name = "ORG_PLACE_OF_SUPPLY")
	private String org_place_of_supply;
	
	@Column(name = "REV_PLACE_OF_SUPPLY")
	private String rev_place_of_supply;
	
	@Column(name = "TYPE")
	private String type;
	
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

	public Long getFin_year() {
		return fin_year;
	}

	public void setFin_year(Long fin_year) {
		this.fin_year = fin_year;
	}

	public String getOrg_month() {
		return org_month;
	}

	public void setOrg_month(String org_month) {
		this.org_month = org_month;
	}

	public String getOrg_place_of_supply() {
		return org_place_of_supply;
	}

	public void setOrg_place_of_supply(String org_place_of_supply) {
		this.org_place_of_supply = org_place_of_supply;
	}

	public String getRev_place_of_supply() {
		return rev_place_of_supply;
	}

	public void setRev_place_of_supply(String rev_place_of_supply) {
		this.rev_place_of_supply = rev_place_of_supply;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
