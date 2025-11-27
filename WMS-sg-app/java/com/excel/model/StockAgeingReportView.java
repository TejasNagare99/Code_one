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
@Table(name = "StockAgeingReportView")
//@NamedNativeQueries({ 
//	@NamedNativeQuery(name = "call_STOCK_AGEING_REPORT", query = "EXEC STOCK_AGEING_REPORT :loc_id,:div_id,:from_dt,:end_dt,:user_id,:prod_id,:slab1a,:slab1b,:slab2a,:slab2b,:slab3a,:slab3b,:slab4a,:slab4b,:slab5a", resultClass = StockAgeingReportView.class) ,
//	 @NamedNativeQuery(name = "call_STOCK_AGEING_REPORT_EXPIRY_DAYS", query = "EXEC STOCK_AGEING_REPORT_EXPIRY_DAYS :loc_id,:div_id,:from_dt,:end_dt,:user_id,:prod_id,:slab1a,:slab1b,:slab2a,:slab2b,:slab3a,:slab3b,:slab4a,:slab4b,:slab5a", resultClass = StockAgeingReportView.class) , 
//})

@NamedStoredProcedureQuery(name = "call_STOCK_AGEING_REPORT",
procedureName = "STOCK_AGEING_REPORT" ,                      
parameters = {
@StoredProcedureParameter(name="piloc_id", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="pidiv_id", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="pvfrmdt", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="pvtodt", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="pvuserid", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="piProdId", mode = ParameterMode.IN, type = String.class),

@StoredProcedureParameter(name="slab1a", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab1b", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab2a", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab2b", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab3a", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab3b", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab4a", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab4b", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab5a", mode = ParameterMode.IN, type = String.class)},
resultClasses= StockAgeingReportView.class)


@NamedStoredProcedureQuery(name = "call_STOCK_AGEING_REPORT_EXPIRY_DAYS",
procedureName = "STOCK_AGEING_REPORT_EXPIRY_DAYS" ,                      
parameters = {
@StoredProcedureParameter(name="piloc_id", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="pidiv_id", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="pvfrmdt", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="pvtodt", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="pvuserid", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="piProdId", mode = ParameterMode.IN, type = String.class),

@StoredProcedureParameter(name="slab1a", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab1b", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab2a", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab2b", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab3a", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab3b", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab4a", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab4b", mode = ParameterMode.IN, type = String.class),
@StoredProcedureParameter(name="slab5a", mode = ParameterMode.IN, type = String.class)},
resultClasses= StockAgeingReportView.class)

public class StockAgeingReportView {
	
	@Id
	@Column(name = "row")
	private Long row;

	@Column(name = "Div_Id")
	private Long div_id;

	@Column(name = "DIV_DISP_NM")
	private String div_disp_nm;

	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;

	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;

	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;

	@Column(name = "SMP_PROD_ID")
	private Long smp_prod_id;

	@Column(name = "BATCH_NO")
	private String batch_no;

	@Column(name = "BATCH_EXPIRY_DT")
	private String batch_expiry_dt;

	@Column(name = "CLOSING_STOCK")
	private BigDecimal closing_stock;

	@Column(name = "CLOSING_VAL")
	private BigDecimal closing_val;

	@Column(name = "no_of_expiry_days")
	private Long no_of_expiry_days;

	@Column(name = "SLAB1")
	private BigDecimal slab1;

	@Column(name = "slab2")
	private BigDecimal slab2;

	@Column(name = "slab3")
	private BigDecimal slab3;

	@Column(name = "slab4")
	private BigDecimal slab4;

	@Column(name = "slab5")
	private BigDecimal slab5;

	@Column(name = "BATCH_MFG_DT")
	private String batch_mfg_dt;

	@Column(name = "BATCH_RATE")
	private BigDecimal batch_rate;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public Long getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_expiry_dt() {
		return batch_expiry_dt;
	}

	public void setBatch_expiry_dt(String batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}

	public BigDecimal getClosing_stock() {
		return closing_stock;
	}

	public void setClosing_stock(BigDecimal closing_stock) {
		this.closing_stock = closing_stock;
	}

	public BigDecimal getClosing_val() {
		return closing_val;
	}

	public void setClosing_val(BigDecimal closing_val) {
		this.closing_val = closing_val;
	}

	public Long getNo_of_expiry_days() {
		return no_of_expiry_days;
	}

	public void setNo_of_expiry_days(Long no_of_expiry_days) {
		this.no_of_expiry_days = no_of_expiry_days;
	}

	public BigDecimal getSlab1() {
		return slab1;
	}

	public void setSlab1(BigDecimal slab1) {
		this.slab1 = slab1;
	}

	public BigDecimal getSlab2() {
		return slab2;
	}

	public void setSlab2(BigDecimal slab2) {
		this.slab2 = slab2;
	}

	public BigDecimal getSlab3() {
		return slab3;
	}

	public void setSlab3(BigDecimal slab3) {
		this.slab3 = slab3;
	}

	public BigDecimal getSlab4() {
		return slab4;
	}

	public void setSlab4(BigDecimal slab4) {
		this.slab4 = slab4;
	}

	public BigDecimal getSlab5() {
		return slab5;
	}

	public void setSlab5(BigDecimal slab5) {
		this.slab5 = slab5;
	}

	public String getBatch_mfg_dt() {
		return batch_mfg_dt;
	}

	public void setBatch_mfg_dt(String batch_mfg_dt) {
		this.batch_mfg_dt = batch_mfg_dt;
	}

	public BigDecimal getBatch_rate() {
		return batch_rate;
	}

	public void setBatch_rate(BigDecimal batch_rate) {
		this.batch_rate = batch_rate;
	}

	
	

}
