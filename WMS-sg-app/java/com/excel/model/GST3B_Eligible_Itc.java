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
@Table(name = "GST3B_Eligible_Itc")
@NamedStoredProcedureQuery(name = "callGSTR_03B_4", 
procedureName = "GSTR_03B_4",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = GST3B_Eligible_Itc.class)
public class GST3B_Eligible_Itc implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ROWNUM")
	private String rownum;
	
	@Column(name="IND")
	private String ind;
	
	@Column(name="DETAILS")
	private String details;
	
	
	@Column(name="IGST")
	private BigDecimal igst;
	
	@Column(name="CGST")
	private BigDecimal cgst;
	
	@Column(name="SGST")
	private BigDecimal sgst;
	
	@Column(name="CESS")
	private BigDecimal cess;

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public BigDecimal getIgst() {
		return igst;
	}

	public void setIgst(BigDecimal igst) {
		this.igst = igst;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getCess() {
		return cess;
	}

	public void setCess(BigDecimal cess) {
		this.cess = cess;
	}
	
	

}
