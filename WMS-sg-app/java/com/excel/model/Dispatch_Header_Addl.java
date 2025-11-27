package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Dispatch_header_ADDL")
public class Dispatch_Header_Addl implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DSP_ADDL_ID")
	private Long dsp_addl_id;

	@Column(name = "DSP_SUM_DSP_ADDL_ID")
	private Long dsp_sum_dsp_addl_id;

	@Column(name = "DSP_SUM_CHALLAN_ADDL_NO")
	private String dsp_sum_challan_addl_no;
	
	@Column(name = "DSP_ID")
	private Long dsp_id;
	
	
	
	@Column(name = "DSP_NO")
	private String dsp_no;

	@Column(name = "DSP_COMPANY")
	private String dsp_company;

	@Column(name = "DSP_FIN_YEAR")
	private String dsp_fin_year;

	@Column(name = "DSP_PERIOD_CODE")
	private String dsp_period_code;

	@Column(name = "DSP_CHALLAN_DT")
	private Date dsp_challan_dt;

	@Column(name = "DSP_CHALLAN_NO")
	private String dsp_challan_no;

	@Column(name="DSP_TRANSPORTER")
	private String dsp_transporter;
	
	@ManyToOne
	@JoinColumn(name = "DSP_RECVG_LOC_ID")
	private Depot_Locations dsp_recvg_loc;
	
	@Column(name="DSP_LR_NO")
	private String dsp_lr_no;
	
	@Column(name="DSP_LR_DT")
	private Date dsp_lr_dt;
	@Column(name="DSP_WT")
	private BigDecimal dsp_wt; 
	@Column(name="DSP_SUM_DSP_ID")
	private long dsp_sum_dsp_id;
	
	@Column(name="DSP_CASES")
	private int dsp_cases;
	@Column(name="DSP_BILLABLE_WT")
	private BigDecimal dsp_billable_wt;
	//@ManyToOne
	//@JoinColumn(name = "DSP_LOC_ID")
	private Long dsp_loc_id;

	//@ManyToOne
	//@JoinColumn(name = "DSP_DIV_ID")
	private Long dsp_div_id;
	

	@Column(name = "DSP_DELIVERY_DATE")
	private java.util.Date	dsp_delivery_date;
	
	@Column(name = "DSP_RECD_BY")
	private String dsp_recd_by;
	
	@Column(name = "DSP_REMARK")
	private String dsp_remark;
	
	@Column(name = "WAY_BILL_NO")
	private String way_bill_no;
	
	
	/**
	 * @return the dsp_delivery_date
	 */
	public java.util.Date getDsp_delivery_date() {
		return dsp_delivery_date;
	}

	/**
	 * @param dsp_delivery_date the dsp_delivery_date to set
	 */
	public void setDsp_delivery_date(java.util.Date dsp_delivery_date) {
		this.dsp_delivery_date = dsp_delivery_date;
	}

	/**
	 * @return the dsp_recd_by
	 */
	public String getDsp_recd_by() {
		return dsp_recd_by;
	}

	/**
	 * @param dsp_recd_by the dsp_recd_by to set
	 */
	public void setDsp_recd_by(String dsp_recd_by) {
		this.dsp_recd_by = dsp_recd_by;
	}

	/**
	 * @return the dsp_remark
	 */
	public String getDsp_remark() {
		return dsp_remark;
	}

	/**
	 * @param dsp_remark the dsp_remark to set
	 */
	public void setDsp_remark(String dsp_remark) {
		this.dsp_remark = dsp_remark;
	}

	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}

	public String getDsp_no() {
		return dsp_no;
	}

	public void setDsp_no(String dsp_no) {
		this.dsp_no = dsp_no;
	}

	public String getDsp_company() {
		return dsp_company;
	}

	public void setDsp_company(String dsp_company) {
		this.dsp_company = dsp_company;
	}

	public String getDsp_fin_year() {
		return dsp_fin_year;
	}

	public void setDsp_fin_year(String dsp_fin_year) {
		this.dsp_fin_year = dsp_fin_year;
	}

	public String getDsp_period_code() {
		return dsp_period_code;
	}

	public void setDsp_period_code(String dsp_period_code) {
		this.dsp_period_code = dsp_period_code;
	}

	public Date getDsp_challan_dt() {
		return dsp_challan_dt;
	}

	public void setDsp_challan_dt(Date dsp_challan_dt) {
		this.dsp_challan_dt = dsp_challan_dt;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public Long getDsp_loc_id() {
		return dsp_loc_id;
	}

	public void setDsp_loc_id(Long dsp_loc_id) {
		this.dsp_loc_id = dsp_loc_id;
	}

	public Long getDsp_div_id() {
		return dsp_div_id;
	}

	public void setDsp_div_id(Long dsp_div_id) {
		this.dsp_div_id = dsp_div_id;
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

	public long getDsp_sum_dsp_id() {
		return dsp_sum_dsp_id;
	}

	public void setDsp_sum_dsp_id(long dsp_sum_dsp_id) {
		this.dsp_sum_dsp_id = dsp_sum_dsp_id;
	}

	public Date getDsp_lr_dt() {
		return dsp_lr_dt;
	}

	public void setDsp_lr_dt(Date dsp_lr_dt) {
		this.dsp_lr_dt = dsp_lr_dt;
	}

	public BigDecimal getDsp_wt() {
		return dsp_wt;
	}

	public void setDsp_wt(BigDecimal dsp_wt) {
		this.dsp_wt = dsp_wt;
	}

	public Long getDsp_addl_id() {
		return dsp_addl_id;
	}

	public void setDsp_addl_id(Long dsp_addl_id) {
		this.dsp_addl_id = dsp_addl_id;
	}

	public Long getDsp_sum_dsp_addl_id() {
		return dsp_sum_dsp_addl_id;
	}

	public void setDsp_sum_dsp_addl_id(Long dsp_sum_dsp_addl_id) {
		this.dsp_sum_dsp_addl_id = dsp_sum_dsp_addl_id;
	}

	public String getDsp_sum_challan_addl_no() {
		return dsp_sum_challan_addl_no;
	}

	public void setDsp_sum_challan_addl_no(String dsp_sum_challan_addl_no) {
		this.dsp_sum_challan_addl_no = dsp_sum_challan_addl_no;
	}

	public int getDsp_cases() {
		return dsp_cases;
	}

	public void setDsp_cases(int dsp_cases) {
		this.dsp_cases = dsp_cases;
	}

	public BigDecimal getDsp_billable_wt() {
		return dsp_billable_wt;
	}

	public void setDsp_billable_wt(BigDecimal dsp_billable_wt) {
		this.dsp_billable_wt = dsp_billable_wt;
	}

	public Depot_Locations getDsp_recvg_loc() {
		return dsp_recvg_loc;
	}

	public void setDsp_recvg_loc(Depot_Locations dsp_recvg_loc) {
		this.dsp_recvg_loc = dsp_recvg_loc;
	}

	public String getWay_bill_no() {
		return way_bill_no;
	}

	public void setWay_bill_no(String way_bill_no) {
		this.way_bill_no = way_bill_no;
	}

}
