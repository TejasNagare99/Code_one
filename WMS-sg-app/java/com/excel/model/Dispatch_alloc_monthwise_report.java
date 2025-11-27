
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
@Table(name = "Dispatch_alloc_monthwise_report")

@NamedStoredProcedureQuery(name = "calldispatch_alloc_month_wise_report", procedureName = "DISPATCH_ALLOC_MONTH_WISE_REPORT", parameters = {
		@StoredProcedureParameter(name = "curryearflg", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "finyear", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "pemp_id", mode = ParameterMode.IN, type = String.class),

}, resultClasses = Dispatch_alloc_monthwise_report.class)
public class Dispatch_alloc_monthwise_report {

	@Id
	@Column(name = "ROW")
	private String row;

	@Column(name = "DSP_YEAR")
	private String dsp_year;

	@Column(name = "DSP_MTH")
	private String dsp_mth;

	@Column(name = "DSP_MTHNM")
	private String dsp_mthnm;

	@Column(name = "DIV_DISP_NM")
	private String div_disp_nm;

	@Column(name = "SA_GROUP_NAME")
	private String sa_group_name;

	@Column(name = "SMP_PROD_ID")
	private String smp_prod_id;

	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;

	@Column(name = "DISP_QTY")
	private BigDecimal disp_qty;

	public String getDsp_year() {
		return dsp_year;
	}

	public void setDsp_year(String dsp_year) {
		this.dsp_year = dsp_year;
	}

	public String getDsp_mth() {
		return dsp_mth;
	}

	public void setDsp_mth(String dsp_mth) {
		this.dsp_mth = dsp_mth;
	}

	public String getDsp_mthnm() {
		return dsp_mthnm;
	}

	public void setDsp_mthnm(String dsp_mthnm) {
		this.dsp_mthnm = dsp_mthnm;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getSa_group_name() {
		return sa_group_name;
	}

	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}

	public String getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(String smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public BigDecimal getDisp_qty() {
		return disp_qty;
	}

	public void setDisp_qty(BigDecimal disp_qty) {
		this.disp_qty = disp_qty;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

}
