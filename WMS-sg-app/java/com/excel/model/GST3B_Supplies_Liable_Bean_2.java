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
@Table(name = "GST3B_Supplies_Liable_Bean_2")
@NamedStoredProcedureQuery(name = "callGSTR_03B_32", 
procedureName = "GSTR_03B_32",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = GST3B_Supplies_Liable_Bean_2.class)
public class GST3B_Supplies_Liable_Bean_2 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ROWNUM")
	private String rownum;
	
	@Column(name="PLACE_SUPPLY")
	private String place_supply;
	
	@Column(name="UNREG_TAXABLE")
	private BigDecimal unreg_taxable;

	@Column(name="UNREG_IGST")
	private BigDecimal unreg_igst;
	
	@Column(name="REG_TAXABLE")
	private BigDecimal reg_taxable;

	@Column(name="REG_IGST")
	private BigDecimal reg_igst;

	@Column(name="UIN_TAXABLE")
	private BigDecimal uin_taxable;

	@Column(name="UIN_IGST")
	private BigDecimal uin_igst;

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getPlace_supply() {
		return place_supply;
	}

	public void setPlace_supply(String place_supply) {
		this.place_supply = place_supply;
	}

	public BigDecimal getUnreg_taxable() {
		return unreg_taxable;
	}

	public void setUnreg_taxable(BigDecimal unreg_taxable) {
		this.unreg_taxable = unreg_taxable;
	}

	public BigDecimal getUnreg_igst() {
		return unreg_igst;
	}

	public void setUnreg_igst(BigDecimal unreg_igst) {
		this.unreg_igst = unreg_igst;
	}

	public BigDecimal getReg_taxable() {
		return reg_taxable;
	}

	public void setReg_taxable(BigDecimal reg_taxable) {
		this.reg_taxable = reg_taxable;
	}

	public BigDecimal getReg_igst() {
		return reg_igst;
	}

	public void setReg_igst(BigDecimal reg_igst) {
		this.reg_igst = reg_igst;
	}

	public BigDecimal getUin_taxable() {
		return uin_taxable;
	}

	public void setUin_taxable(BigDecimal uin_taxable) {
		this.uin_taxable = uin_taxable;
	}

	public BigDecimal getUin_igst() {
		return uin_igst;
	}

	public void setUin_igst(BigDecimal uin_igst) {
		this.uin_igst = uin_igst;
	}
	
	

}
