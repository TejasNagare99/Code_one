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
@Table(name = "StockAgeingGrnReportView")
@NamedStoredProcedureQuery(name = "call_STOCK_AGEING_GRN_REPORT",
procedureName = "AGEING_STOCK_GRN_DSP_DT " ,                      
parameters = {
@StoredProcedureParameter(name="LOCID", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="DIVID", mode = ParameterMode.IN, type = String.class),

@StoredProcedureParameter(name="SLAB1", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB2", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB3", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB4", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB5", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB6", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB7", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB8", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB9", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB10", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="SLAB11", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="DOCTYPE", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="START_DT", mode = ParameterMode.IN, type = String.class),
},
resultClasses= StockAgeingGrnReportView.class)
public class StockAgeingGrnReportView {

	@Id
	@Column(name = "ROWNUM")
	private Long rownum;
	
	@Column(name = "DIV_ID")
	private Long div_id;
	
	@Column(name = "DIV_NAME")
	private String div_name;
	
	@Column(name = "PROD_ID")
	private Long prod_id;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "PROD_NAME")
	private String prod_name;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "LOC_NAME")
	private String loc_name;
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "BATCH_ID")
	private Long batch_id;
	
	@Column(name = "EXPIRY_DT")
	private String expiry_dt;
	
	@Column(name = "BATCH_RATE")
	private BigDecimal batch_rate;
	
	@Column(name = "CLOS_QTY")
	private BigDecimal clos_qty;
	
	@Column(name = "CLOS_VAL")
	private BigDecimal clos_val;
	
	@Column(name = "SLAB1_2")
	private BigDecimal slab1_2;
	
	@Column(name = "SLAB3_4")
	private BigDecimal slab3_4;
	
	@Column(name = "SLAB5_6")
	private BigDecimal slab5_6;
	
	@Column(name = "SLAB7_8")
	private BigDecimal slab7_8;
	
	@Column(name = "SLAB9_10")
	private BigDecimal slab9_10;
	
	@Column(name = "SLAB_11")
	private BigDecimal slab_11;
	
	@Column(name = "DOC_DT")
	private String doc_dt;
	
	@Column(name = "DAYS")
	private Long days;

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getDiv_name() {
		return div_name;
	}

	public void setDiv_name(String div_name) {
		this.div_name = div_name;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	

	public Long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(Long batch_id) {
		this.batch_id = batch_id;
	}

	public String getExpiry_dt() {
		return expiry_dt;
	}

	public void setExpiry_dt(String expiry_dt) {
		this.expiry_dt = expiry_dt;
	}

	public BigDecimal getBatch_rate() {
		return batch_rate;
	}

	public void setBatch_rate(BigDecimal batch_rate) {
		this.batch_rate = batch_rate;
	}

	public BigDecimal getClos_qty() {
		return clos_qty;
	}

	public void setClos_qty(BigDecimal clos_qty) {
		this.clos_qty = clos_qty;
	}

	public BigDecimal getClos_val() {
		return clos_val;
	}

	public void setClos_val(BigDecimal clos_val) {
		this.clos_val = clos_val;
	}

	public BigDecimal getSlab1_2() {
		return slab1_2;
	}

	public void setSlab1_2(BigDecimal slab1_2) {
		this.slab1_2 = slab1_2;
	}

	public BigDecimal getSlab3_4() {
		return slab3_4;
	}

	public void setSlab3_4(BigDecimal slab3_4) {
		this.slab3_4 = slab3_4;
	}

	public BigDecimal getSlab5_6() {
		return slab5_6;
	}

	public void setSlab5_6(BigDecimal slab5_6) {
		this.slab5_6 = slab5_6;
	}

	public BigDecimal getSlab7_8() {
		return slab7_8;
	}

	public void setSlab7_8(BigDecimal slab7_8) {
		this.slab7_8 = slab7_8;
	}

	public BigDecimal getSlab9_10() {
		return slab9_10;
	}

	public void setSlab9_10(BigDecimal slab9_10) {
		this.slab9_10 = slab9_10;
	}

	public BigDecimal getSlab_11() {
		return slab_11;
	}

	public void setSlab_11(BigDecimal slab_11) {
		this.slab_11 = slab_11;
	}

	public String getDoc_dt() {
		return doc_dt;
	}

	public void setDoc_dt(String doc_dt) {
		this.doc_dt = doc_dt;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	
	
}
