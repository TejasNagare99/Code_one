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
@Table(name = "annualAllocationPreviousYearData")
@NamedStoredProcedureQuery(name = "callANNUAL_PLAN_ENTRY_PRV_YEAR_DATA", procedureName = "ANNUAL_PLAN_ENTRY_PRV_YEAR_DATA_CPYTICK_ARC",
parameters= {
		@StoredProcedureParameter(name="DIVID" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="PRODID" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="PYEAR" , mode=ParameterMode.IN,type=String.class)
		},resultClasses=AnnualAllocationPreviousYearData.class) 
public class AnnualAllocationPreviousYearData  implements Serializable  {

	private static final long serialVersionUID = 5643939516828216717L;
	
	@Id
	@Column(name="Row")
	private Long row;
	
	@Column(name="PERIOD_NAME")
	private String period_name;
	
	@Column(name="PERIOD_CODE")
	private String period_code;
	
	@Column(name="DSPDTL_FIN_YEAR")
	private String dspdtl_fin_year;
	
	@Column(name="SMP_PROD_ID")
	private Long smp_prod_id;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name="DSP_QTY")
	private BigDecimal dsp_qty;
	
	@Column(name="LASTYRPERPSO")
	private BigDecimal lastyrperpso;
	
	@Column(name="LASTYRTOTUNIT")
	private BigDecimal lastyrtotunit;
	
	@Column(name="LASTYRTOTCOST")
	private BigDecimal lastyrtotcost;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getPeriod_name() {
		return period_name;
	}

	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public String getDspdtl_fin_year() {
		return dspdtl_fin_year;
	}

	public void setDspdtl_fin_year(String dspdtl_fin_year) {
		this.dspdtl_fin_year = dspdtl_fin_year;
	}

	public Long getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public BigDecimal getDsp_qty() {
		return dsp_qty;
	}

	public void setDsp_qty(BigDecimal dsp_qty) {
		this.dsp_qty = dsp_qty;
	}

	public BigDecimal getLastyrperpso() {
		return lastyrperpso;
	}

	public void setLastyrperpso(BigDecimal lastyrperpso) {
		this.lastyrperpso = lastyrperpso;
	}

	public BigDecimal getLastyrtotunit() {
		return lastyrtotunit;
	}

	public void setLastyrtotunit(BigDecimal lastyrtotunit) {
		this.lastyrtotunit = lastyrtotunit;
	}

	public BigDecimal getLastyrtotcost() {
		return lastyrtotcost;
	}

	public void setLastyrtotcost(BigDecimal lastyrtotcost) {
		this.lastyrtotcost = lastyrtotcost;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
