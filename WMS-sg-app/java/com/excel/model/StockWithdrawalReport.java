package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "StockWithdrawalReport")
@NamedStoredProcedureQuery(name = "callStockWithdrawalReport",
procedureName = "StockWithdrawalReport",
parameters= {
		@StoredProcedureParameter(name = "loc_id" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "frm_challan_date" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "to_challan_date" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "rep_type" ,mode = ParameterMode.IN,type=String.class),
},resultClasses=StockWithdrawalReport.class)
public class StockWithdrawalReport {
	
	
	@Id
	@Column(name="row")
	private Long row;
	
	@Column(name="SWV_CHALLAN_NO")
	private String swv_challan_no;
	
	@Column(name="SWV_CHALLAN_DT")
	private Date swv_challan_dt;

	@Column(name="SWV_LR_NO")
	private String swv_lr_no;
	
	@Column(name="SWV_LR_DT")
	private Date swv_lr_dt;
	
	@Column(name="SWV_TRANSPORTER")
	private String swv_transporter;
	
	@Column(name="SWV_CASES")
	private Long swv_cases;
	
	@Column(name="SWV_WT")
	private BigDecimal swv_wt;
	
	@Column(name="SWV_SENDER_NAME")
	private String swv_sender_name;
	
	@Column(name="SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name="BATCH_NO")
	private String batch_no;
	
	@Column(name="BATCH_EXPIRY_DT")
	private Date batch_expiry_dt;
	
	@Column(name="STOCK_TYPE")
	private String stock_type;
	
	@Column(name="SWVDTL_DISP_QTY")
	private BigDecimal swvdtl_disp_qty;
	
	@Column(name="SWVDTL_RATE")
	private BigDecimal swvdtl_rate;
	
	@Column(name="VALUE")
	private BigDecimal value;
	
	

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getSwv_challan_no() {
		return swv_challan_no;
	}

	public void setSwv_challan_no(String swv_challan_no) {
		this.swv_challan_no = swv_challan_no;
	}

	public Date getSwv_challan_dt() {
		return swv_challan_dt;
	}

	public void setSwv_challan_dt(Date swv_challan_dt) {
		this.swv_challan_dt = swv_challan_dt;
	}

	public String getSwv_lr_no() {
		return swv_lr_no;
	}

	public void setSwv_lr_no(String swv_lr_no) {
		this.swv_lr_no = swv_lr_no;
	}

	public Date getSwv_lr_dt() {
		return swv_lr_dt;
	}

	public void setSwv_lr_dt(Date swv_lr_dt) {
		this.swv_lr_dt = swv_lr_dt;
	}

	public String getSwv_transporter() {
		return swv_transporter;
	}

	public void setSwv_transporter(String swv_transporter) {
		this.swv_transporter = swv_transporter;
	}

	public Long getSwv_cases() {
		return swv_cases;
	}

	public void setSwv_cases(Long swv_cases) {
		this.swv_cases = swv_cases;
	}

	public BigDecimal getSwv_wt() {
		return swv_wt;
	}

	public void setSwv_wt(BigDecimal swv_wt) {
		this.swv_wt = swv_wt;
	}

	public String getSwv_sender_name() {
		return swv_sender_name;
	}

	public void setSwv_sender_name(String swv_sender_name) {
		this.swv_sender_name = swv_sender_name;
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

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public Date getBatch_expiry_dt() {
		return batch_expiry_dt;
	}

	public void setBatch_expiry_dt(Date batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}

	public BigDecimal getSwvdtl_disp_qty() {
		return swvdtl_disp_qty;
	}

	public void setSwvdtl_disp_qty(BigDecimal swvdtl_disp_qty) {
		this.swvdtl_disp_qty = swvdtl_disp_qty;
	}

	public BigDecimal getSwvdtl_rate() {
		return swvdtl_rate;
	}

	public void setSwvdtl_rate(BigDecimal swvdtl_rate) {
		this.swvdtl_rate = swvdtl_rate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	
	
}
