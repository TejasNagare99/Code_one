package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@NamedStoredProcedureQuery (name = "call_LR_expenses_report", procedureName = "LR_EXPENSES_REPORT",
parameters = {
		@StoredProcedureParameter(name="piloc_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="pidiv_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="pvTransporter" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="pvfrmdt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="pvtodt" , mode=ParameterMode.IN,type=String.class)
		
},resultClasses=Lr_Expenses_Report.class)

@Entity
@Table(name = "Lr_Expenses_Report")
public class Lr_Expenses_Report {

	
	@Id
	@Column(name="row")
	private Long row;
	
	@Column(name="LOC_NM")
	private String loc_nm;
	
	@Column(name="DIV_DISP_NM")
	private String div_disp_nm;
	
	@Column(name="DSP_TRANSPORTER")
	private String dsp_transporter;
	
	@Column(name="DSP_LR_NO")
	private String dsp_lr_no;
	
	@Column(name="DSP_LR_DT")
	private String dsp_lr_dt;
	
	@Column(name="DSP_CHALLAN_NO")
	private String dsp_challan_no;
	
	@Column(name="DSP_CHALLAN_DT")
	private String dsp_challan_dt;
	
	@Column(name="DSPFSTAFF_DESTINATION")
	private String dspfstaff_destination;
	
	@Column(name="COURIER_EXPENSES")
	private BigDecimal courier_expenses;
	
	@Column(name="TENTATIVE_DELIVERY_DT")
	private String tentative_delivery_dt;
	
	@Column(name="ACTUAL_DELIVERY_DT")
	private String actual_delivery_dt;
	
	@Column(name="DELAY_DAYS")
	private Long delay_days;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getDsp_transporter() {
		return dsp_transporter;
	}

	public void setDsp_transporter(String dsp_transporter) {
		this.dsp_transporter = dsp_transporter;
	}

	public String getDsp_lr_no() {
		return dsp_lr_no;
	}

	public void setDsp_lr_no(String dsp_lr_no) {
		this.dsp_lr_no = dsp_lr_no;
	}

	public String getDsp_lr_dt() {
		return dsp_lr_dt;
	}

	public void setDsp_lr_dt(String dsp_lr_dt) {
		this.dsp_lr_dt = dsp_lr_dt;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public String getDsp_challan_dt() {
		return dsp_challan_dt;
	}

	public void setDsp_challan_dt(String dsp_challan_dt) {
		this.dsp_challan_dt = dsp_challan_dt;
	}

	public String getDspfstaff_destination() {
		return dspfstaff_destination;
	}

	public void setDspfstaff_destination(String dspfstaff_destination) {
		this.dspfstaff_destination = dspfstaff_destination;
	}

	public BigDecimal getCourier_expenses() {
		return courier_expenses;
	}

	public void setCourier_expenses(BigDecimal courier_expenses) {
		this.courier_expenses = courier_expenses;
	}

	public String getTentative_delivery_dt() {
		return tentative_delivery_dt;
	}

	public void setTentative_delivery_dt(String tentative_delivery_dt) {
		this.tentative_delivery_dt = tentative_delivery_dt;
	}

	public String getActual_delivery_dt() {
		return actual_delivery_dt;
	}

	public void setActual_delivery_dt(String actual_delivery_dt) {
		this.actual_delivery_dt = actual_delivery_dt;
	}

	public Long getDelay_days() {
		return delay_days;
	}

	public void setDelay_days(Long delay_days) {
		this.delay_days = delay_days;
	}

	
	
	
	
	
}
