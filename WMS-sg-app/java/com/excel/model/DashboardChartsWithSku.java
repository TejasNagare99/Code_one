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
@Table(name = "DashboardChartsWithSku")
@NamedStoredProcedureQuery(name="callAnnMonPlanDataWithSku",procedureName = "DBOARD_GRAPH2_DISPLAY_REVISED_with_sku",resultClasses = DashboardChartsWithSku.class,
parameters = {
		@StoredProcedureParameter(name="pLoc_id", mode = ParameterMode.IN, type = Long.class),
		@StoredProcedureParameter(name="puserid", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="pfinyear", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="pperiod_code", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="pdiv_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="pbrand_id", mode = ParameterMode.IN, type = String.class),
})
public class DashboardChartsWithSku {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ROW")
	private Long row;
	
	@Column(name = "FIN_YEAR")
	private String fin_year;
	
	@Column(name = "PERIOD_CODE")
	private String period_code;
	
	@Column(name = "SA_GROUP_NAME")
	private String sa_group_name;
	
	@Column(name = "SMP_SA_PROD_GROUP")
	private Long smp_sa_prod_group;
	
	@Column(name = "PLANVALUE")
	private BigDecimal planvalue;
	
	@Column(name = "ALLOCVALUE")
	private BigDecimal allocvalue;
	
	@Column(name = "STOCKVALUE")
	private BigDecimal stockvalue;
	
	@Column(name = "PERCENTAGE")
	private BigDecimal percentage;
	
	@Column(name = "DIVISION")
	private String division;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public String getSa_group_name() {
		return sa_group_name;
	}

	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}

	public Long getSmp_sa_prod_group() {
		return smp_sa_prod_group;
	}

	public void setSmp_sa_prod_group(Long smp_sa_prod_group) {
		this.smp_sa_prod_group = smp_sa_prod_group;
	}

	public BigDecimal getPlanvalue() {
		return planvalue;
	}

	public void setPlanvalue(BigDecimal planvalue) {
		this.planvalue = planvalue;
	}

	public BigDecimal getAllocvalue() {
		return allocvalue;
	}

	public void setAllocvalue(BigDecimal allocvalue) {
		this.allocvalue = allocvalue;
	}

	public BigDecimal getStockvalue() {
		return stockvalue;
	}

	public void setStockvalue(BigDecimal stockvalue) {
		this.stockvalue = stockvalue;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	
	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}
	
	
	

}
