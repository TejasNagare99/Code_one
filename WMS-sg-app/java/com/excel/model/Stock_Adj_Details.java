package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STOCK_ADJ_DETAILS")
public class Stock_Adj_Details implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ADJDTL_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long adjdtl_id;
	
	@Column(name="ADJDTL_STKADJ_ID")
	private Long adjdtl_stkadj_id;
	
	@Column(name="ADJDTL_COMPANY")
	private String adjdtl_company;
	
	@Column(name="ADJDTL_FIN_YEAR")
	private String adjdtl_fin_year;
	
	@Column(name="ADJDTL_PERIOD_CODE")
	private String adjdtl_period_code;
	
	@Column(name="ADJDTL_LOC_ID")
	private Long adjdtl_loc_id;
	
	@Column(name="ADJDTL_REASON_ID")
	private Long adjdtl_reason_id;
	
	@Column(name="ADJDTL_OUT_ITEM_ID")
	private Long adjdtl_out_item_id;
	
	@Column(name="ADJDTL_OUT_BATCH_ID")
	private Long adjdtl_out_batch_id;
	
	@Column(name="ADJDTL_OUT_QTY")
	private Long adjdtl_out_qty;
	
	@Column(name="ADJDTL_OUT_STK_TYPE")
	private String adjdtl_out_stk_type;
	
	@Column(name="ADJDTL_OUT_RATE")
	private BigDecimal adjdtl_out_rate;
	
	@Column(name="ADJDTL_IN_ITEM_ID")
	private Long adjdtl_in_item_id;
	
	@Column(name="ADJDTL_IN_BATCH_ID")
	private Long adjdtl_in_batch_id;
	
	@Column(name="ADJDTL_IN_QTY")
	private Long adjdtl_in_qty;
	
	@Column(name="ADJDTL_IN_STK_TYPE")
	private String adjdtl_in_stk_type;
	
	@Column(name="ADJDTL_IN_RATE")
	private BigDecimal adjdtl_in_rate;
	
	
	@Column(name="ADJDTL_REMARKS")
	private String adjdtl_remarks;
	
	@Column(name="ADJDTL_ins_usr_id")
	private String adjdtl_ins_usr_id;
	
	@Column(name="ADJDTL_mod_usr_id")
	private String adjdtl_mod_usr_id;
	
	@Column(name="ADJDTL_ins_dt")
	private Date adjdtl_ins_dt;
	
	@Column(name="ADJDTL_mod_dt")
	private Date adjdtl_mod_dt;
	
	@Column(name="ADJDTL_ins_ip_add")
	private String adjdtl_ins_ip_add;
	
	@Column(name="ADJDTL_mod_ip_add")
	private String adjdtl_mod_ip_add;
	
	@Column(name="ADJDTL_status")
	private String adjdtl_status;
	
	@Column(name="STKADJ_APPR_REQ")
	private Integer stkadj_appr_req;
	
	@Column(name="STKADJ_APPR_ACQ")
	private Integer stkadj_appr_acq;
	
	@Column(name="STKADJ_APPR_STATUS")
	private String stkadj_appr_status;

	public Long getAdjdtl_id() {
		return adjdtl_id;
	}

	public void setAdjdtl_id(Long adjdtl_id) {
		this.adjdtl_id = adjdtl_id;
	}

	public Long getAdjdtl_stkadj_id() {
		return adjdtl_stkadj_id;
	}

	public void setAdjdtl_stkadj_id(Long adjdtl_stkadj_id) {
		this.adjdtl_stkadj_id = adjdtl_stkadj_id;
	}

	public String getAdjdtl_company() {
		return adjdtl_company;
	}

	public void setAdjdtl_company(String adjdtl_company) {
		this.adjdtl_company = adjdtl_company;
	}

	public String getAdjdtl_fin_year() {
		return adjdtl_fin_year;
	}

	public void setAdjdtl_fin_year(String adjdtl_fin_year) {
		this.adjdtl_fin_year = adjdtl_fin_year;
	}

	public String getAdjdtl_period_code() {
		return adjdtl_period_code;
	}

	public void setAdjdtl_period_code(String adjdtl_period_code) {
		this.adjdtl_period_code = adjdtl_period_code;
	}

	public Long getAdjdtl_loc_id() {
		return adjdtl_loc_id;
	}

	public void setAdjdtl_loc_id(Long adjdtl_loc_id) {
		this.adjdtl_loc_id = adjdtl_loc_id;
	}

	public Long getAdjdtl_reason_id() {
		return adjdtl_reason_id;
	}

	public void setAdjdtl_reason_id(Long adjdtl_reason_id) {
		this.adjdtl_reason_id = adjdtl_reason_id;
	}

	public Long getAdjdtl_out_item_id() {
		return adjdtl_out_item_id;
	}

	public void setAdjdtl_out_item_id(Long adjdtl_out_item_id) {
		this.adjdtl_out_item_id = adjdtl_out_item_id;
	}

	public Long getAdjdtl_out_batch_id() {
		return adjdtl_out_batch_id;
	}

	public void setAdjdtl_out_batch_id(Long adjdtl_out_batch_id) {
		this.adjdtl_out_batch_id = adjdtl_out_batch_id;
	}

	public Long getAdjdtl_out_qty() {
		return adjdtl_out_qty;
	}

	public void setAdjdtl_out_qty(Long adjdtl_out_qty) {
		this.adjdtl_out_qty = adjdtl_out_qty;
	}

	public String getAdjdtl_out_stk_type() {
		return adjdtl_out_stk_type;
	}

	public void setAdjdtl_out_stk_type(String adjdtl_out_stk_type) {
		this.adjdtl_out_stk_type = adjdtl_out_stk_type;
	}

	public BigDecimal getAdjdtl_out_rate() {
		return adjdtl_out_rate;
	}

	public void setAdjdtl_out_rate(BigDecimal adjdtl_out_rate) {
		this.adjdtl_out_rate = adjdtl_out_rate;
	}

	public Long getAdjdtl_in_item_id() {
		return adjdtl_in_item_id;
	}

	public void setAdjdtl_in_item_id(Long adjdtl_in_item_id) {
		this.adjdtl_in_item_id = adjdtl_in_item_id;
	}

	public Long getAdjdtl_in_batch_id() {
		return adjdtl_in_batch_id;
	}

	public void setAdjdtl_in_batch_id(Long adjdtl_in_batch_id) {
		this.adjdtl_in_batch_id = adjdtl_in_batch_id;
	}

	public Long getAdjdtl_in_qty() {
		return adjdtl_in_qty;
	}

	public void setAdjdtl_in_qty(Long adjdtl_in_qty) {
		this.adjdtl_in_qty = adjdtl_in_qty;
	}

	public String getAdjdtl_in_stk_type() {
		return adjdtl_in_stk_type;
	}

	public void setAdjdtl_in_stk_type(String adjdtl_in_stk_type) {
		this.adjdtl_in_stk_type = adjdtl_in_stk_type;
	}

	public BigDecimal getAdjdtl_in_rate() {
		return adjdtl_in_rate;
	}

	public void setAdjdtl_in_rate(BigDecimal adjdtl_in_rate) {
		this.adjdtl_in_rate = adjdtl_in_rate;
	}

	public String getAdjdtl_remarks() {
		return adjdtl_remarks;
	}

	public void setAdjdtl_remarks(String adjdtl_remarks) {
		this.adjdtl_remarks = adjdtl_remarks;
	}

	public String getAdjdtl_ins_usr_id() {
		return adjdtl_ins_usr_id;
	}

	public void setAdjdtl_ins_usr_id(String adjdtl_ins_usr_id) {
		this.adjdtl_ins_usr_id = adjdtl_ins_usr_id;
	}

	public String getAdjdtl_mod_usr_id() {
		return adjdtl_mod_usr_id;
	}

	public void setAdjdtl_mod_usr_id(String adjdtl_mod_usr_id) {
		this.adjdtl_mod_usr_id = adjdtl_mod_usr_id;
	}

	public Date getAdjdtl_ins_dt() {
		return adjdtl_ins_dt;
	}

	public void setAdjdtl_ins_dt(Date adjdtl_ins_dt) {
		this.adjdtl_ins_dt = adjdtl_ins_dt;
	}

	public Date getAdjdtl_mod_dt() {
		return adjdtl_mod_dt;
	}

	public void setAdjdtl_mod_dt(Date adjdtl_mod_dt) {
		this.adjdtl_mod_dt = adjdtl_mod_dt;
	}

	public String getAdjdtl_ins_ip_add() {
		return adjdtl_ins_ip_add;
	}

	public void setAdjdtl_ins_ip_add(String adjdtl_ins_ip_add) {
		this.adjdtl_ins_ip_add = adjdtl_ins_ip_add;
	}

	public String getAdjdtl_mod_ip_add() {
		return adjdtl_mod_ip_add;
	}

	public void setAdjdtl_mod_ip_add(String adjdtl_mod_ip_add) {
		this.adjdtl_mod_ip_add = adjdtl_mod_ip_add;
	}

	public String getAdjdtl_status() {
		return adjdtl_status;
	}

	public void setAdjdtl_status(String adjdtl_status) {
		this.adjdtl_status = adjdtl_status;
	}

	public Integer getStkadj_appr_req() {
		return stkadj_appr_req;
	}

	public void setStkadj_appr_req(Integer stkadj_appr_req) {
		this.stkadj_appr_req = stkadj_appr_req;
	}

	public Integer getStkadj_appr_acq() {
		return stkadj_appr_acq;
	}

	public void setStkadj_appr_acq(Integer stkadj_appr_acq) {
		this.stkadj_appr_acq = stkadj_appr_acq;
	}

	public String getStkadj_appr_status() {
		return stkadj_appr_status;
	}

	public void setStkadj_appr_status(String stkadj_appr_status) {
		this.stkadj_appr_status = stkadj_appr_status;
	}
	
	
	
	
	
	
}
