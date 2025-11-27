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
@Table(name="Grn_quarantine_prod_summary")

@NamedStoredProcedureQuery(name = "callgrndetailreport_quarantine_prod_summary",
						    procedureName = "GrnDetailReport_QUARANTINE_PROD_SUMMARY",
							parameters= {
									@StoredProcedureParameter(name = "startdate" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "endDate" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "SEND_LOC" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "RECV_LOC" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "DIVID" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "USER_ID" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "fin_year_flag" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "FIN_YEAR_ID" ,mode = ParameterMode.IN,type=String.class),
								
							}, resultClasses = Grn_quarantine_prod_summary.class)
public class Grn_quarantine_prod_summary {
	
	@Id
	@Column(name = "Row")
	private Long row;
	
	@Column(name = "DIVISION")
	private String division;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "GRND_QTY")
	private BigDecimal grnd_qty;
	
	@Column(name = "GRN_VALUE")
	private BigDecimal grn_value;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public BigDecimal getGrnd_qty() {
		return grnd_qty;
	}

	public void setGrnd_qty(BigDecimal grnd_qty) {
		this.grnd_qty = grnd_qty;
	}

	public BigDecimal getGrn_value() {
		return grn_value;
	}

	public void setGrn_value(BigDecimal grn_value) {
		this.grn_value = grn_value;
	}

	
	
	
	
}
 