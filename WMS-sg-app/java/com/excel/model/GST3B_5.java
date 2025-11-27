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
@Table(name = "GST3B_5")
@NamedStoredProcedureQuery(name = "callGSTR_03B_5", 
procedureName = "GSTR_03B_5",
parameters = {
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FINID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "COMP_CD" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "ST_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "EN_DT" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LOCID" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = GST3B_5.class)
public class GST3B_5 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ROWNUM")
	private String rownum;
	
	@Column(name="IND")
	private String ind;
	
	@Column(name="DETAILS")
	private String details;
	
	@Column(name="InterState_supply")
	private BigDecimal interstate_supply;
	
	@Column(name="IntraState_supply")
	private BigDecimal intrastate_supply;

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

	public BigDecimal getInterstate_supply() {
		return interstate_supply;
	}

	public void setInterstate_supply(BigDecimal interstate_supply) {
		this.interstate_supply = interstate_supply;
	}

	public BigDecimal getIntrastate_supply() {
		return intrastate_supply;
	}

	public void setIntrastate_supply(BigDecimal intrastate_supply) {
		this.intrastate_supply = intrastate_supply;
	}
	
	

}
