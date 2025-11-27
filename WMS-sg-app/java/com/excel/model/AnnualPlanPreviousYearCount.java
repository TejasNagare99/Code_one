package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "annualAnnualPlanPreviousYearCount")
@NamedStoredProcedureQuery(name = "callANNUAL_PLAN_COUNT", procedureName = "ANNUAL_PLAN_COUNT",
parameters= {
		@StoredProcedureParameter(name="DIVID" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="PRODID" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="YEAR" , mode=ParameterMode.IN,type=String.class)
		},resultClasses=AnnualPlanPreviousYearCount.class) 
public class AnnualPlanPreviousYearCount {
	
	@Id
	@Column(name="ROWNUM")
	private Long rownum;
	
	@Column(name="DSPDTL_PROD_ID")
	private Long dspdtl_prod_id;
	
	@Column(name="SMP_ERP_PROD_CD")
	private String smp_erp_prod_cd;
	
	@Column(name="PRVYRDSP_UNIT")
	private Long prevyrdsp_unit;
	
	@Column(name="PRVYRDSPCNT")
	private Long prevyrdspcnt;
	
	@Column(name="PRVYRDSP_PERPSO")
	private BigDecimal prevyrdsp_perpso;

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public Long getDspdtl_prod_id() {
		return dspdtl_prod_id;
	}

	public void setDspdtl_prod_id(Long dspdtl_prod_id) {
		this.dspdtl_prod_id = dspdtl_prod_id;
	}

	public String getSmp_erp_prod_cd() {
		return smp_erp_prod_cd;
	}

	public void setSmp_erp_prod_cd(String smp_erp_prod_cd) {
		this.smp_erp_prod_cd = smp_erp_prod_cd;
	}

	public Long getPrevyrdspcnt() {
		return prevyrdspcnt;
	}

	public void setPrevyrdspcnt(Long prevyrdspcnt) {
		this.prevyrdspcnt = prevyrdspcnt;
	}

	public BigDecimal getPrevyrdsp_perpso() {
		return prevyrdsp_perpso;
	}

	public void setPrevyrdsp_perpso(BigDecimal prevyrdsp_perpso) {
		this.prevyrdsp_perpso = prevyrdsp_perpso;
	}

	public Long getPrevyrdsp_unit() {
		return prevyrdsp_unit;
	}

	public void setPrevyrdsp_unit(Long prevyrdsp_unit) {
		this.prevyrdsp_unit = prevyrdsp_unit;
	}



}
