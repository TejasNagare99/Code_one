package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ALLOC_REQ_DET")
public class AllocReqDet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_REQ_DTL_ID")
	private Long alloc_req_dtl_id;
	
	@Column(name = "ALLOC_REQ_ID")
	private Long alloc_req_id;
	
	@Column(name = "ALLOC_REQ_DATE")
	private Date alloc_req_date;
	
	@Column(name = "FIN_YEAR")
	private String fin_year;
	
	@Column(name = "PERIOD_CODE")
	private String period_code;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "division")
	private String division;
	
	@Column(name = "FS_ID")
	private Long fs_id;
	
	@Column(name = "TERRITORY_ID")
	private Long territory_id;
	
	@Column(name = "PROD_ID")
	private Long prod_id;
	
	@Column(name = "PROD_TYPE")
	private String prod_type;
	
 	@Column(name = "PROD_SUB_TYPE")
	private String prod_sub_type;
	
	@Column(name = "PROD_THRESHOLD")
	private String prod_threshold;
	
	@Column(name = "PROD_HIGHVALUE")
	private String prod_highvalue;
	
	@Column(name = "PROD_SENSITIVE")
	private String prod_sensitive;
	
	@Column(name = "ALT_DIV_ID")
	private Long alt_div_id;
	
	@Column(name = "ALLOC_CYCLE")
	private Long alloc_cycle;
	
	@Column(name = "ALLOC_RATE")
	private BigDecimal alloc_rate;
	
	@Column(name = "ALLOC_QTY")
	private BigDecimal alloc_qty;
	
	@Column(name = "ALLOC_VALUE")
	private BigDecimal alloc_value;

	@Column(name = "REQDT_ins_user_id")
	private String reqdt_ins_user_id;
	
	@Column(name = "REQDT_mod_user_id")
	private String reqdt_mod_user_id;
	
	@Column(name = "REQDT_ins_dt")
	private Date reqdt_ins_dt;
	
	@Column(name = "REQDT_mod_dt")
	private Date reqdt_mod_dt;
	
	@Column(name = "REQDT_ins_ip_add")
	private String reqdt_ins_ip_add;
	
	@Column(name = "REQDT_mod_ip_add")
	private String reqdt_mod_ip_add;
	
	@Column(name = "REQDT_status")
	private String reqdt_status;

	public Long getAlloc_req_dtl_id() {
		return alloc_req_dtl_id;
	}

	public void setAlloc_req_dtl_id(Long alloc_req_dtl_id) {
		this.alloc_req_dtl_id = alloc_req_dtl_id;
	}

	public Long getAlloc_req_id() {
		return alloc_req_id;
	}

	public void setAlloc_req_id(Long alloc_req_id) {
		this.alloc_req_id = alloc_req_id;
	}

	public Date getAlloc_req_date() {
		return alloc_req_date;
	}

	public void setAlloc_req_date(Date alloc_req_date) {
		this.alloc_req_date = alloc_req_date;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Long getFs_id() {
		return fs_id;
	}

	public void setFs_id(Long fs_id) {
		this.fs_id = fs_id;
	}

	public Long getTerritory_id() {
		return territory_id;
	}

	public void setTerritory_id(Long territory_id) {
		this.territory_id = territory_id;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public String getProd_threshold() {
		return prod_threshold;
	}

	public void setProd_threshold(String prod_threshold) {
		this.prod_threshold = prod_threshold;
	}

	public String getProd_highvalue() {
		return prod_highvalue;
	}

	public void setProd_highvalue(String prod_highvalue) {
		this.prod_highvalue = prod_highvalue;
	}

	public String getProd_sensitive() {
		return prod_sensitive;
	}

	public void setProd_sensitive(String prod_sensitive) {
		this.prod_sensitive = prod_sensitive;
	}

	public Long getAlt_div_id() {
		return alt_div_id;
	}

	public void setAlt_div_id(Long alt_div_id) {
		this.alt_div_id = alt_div_id;
	}

	public Long getAlloc_cycle() {
		return alloc_cycle;
	}

	public void setAlloc_cycle(Long alloc_cycle) {
		this.alloc_cycle = alloc_cycle;
	}

	public BigDecimal getAlloc_rate() {
		return alloc_rate;
	}

	public void setAlloc_rate(BigDecimal alloc_rate) {
		this.alloc_rate = alloc_rate;
	}

	public BigDecimal getAlloc_qty() {
		return alloc_qty;
	}

	public void setAlloc_qty(BigDecimal alloc_qty) {
		this.alloc_qty = alloc_qty;
	}

	public BigDecimal getAlloc_value() {
		return alloc_value;
	}

	public void setAlloc_value(BigDecimal alloc_value) {
		this.alloc_value = alloc_value;
	}

	public String getReqdt_ins_user_id() {
		return reqdt_ins_user_id;
	}

	public void setReqdt_ins_user_id(String reqdt_ins_user_id) {
		this.reqdt_ins_user_id = reqdt_ins_user_id;
	}

	public String getReqdt_mod_user_id() {
		return reqdt_mod_user_id;
	}

	public void setReqdt_mod_user_id(String reqdt_mod_user_id) {
		this.reqdt_mod_user_id = reqdt_mod_user_id;
	}

	public Date getReqdt_ins_dt() {
		return reqdt_ins_dt;
	}

	public void setReqdt_ins_dt(Date reqdt_ins_dt) {
		this.reqdt_ins_dt = reqdt_ins_dt;
	}

	public Date getReqdt_mod_dt() {
		return reqdt_mod_dt;
	}

	public void setReqdt_mod_dt(Date reqdt_mod_dt) {
		this.reqdt_mod_dt = reqdt_mod_dt;
	}

	public String getReqdt_ins_ip_add() {
		return reqdt_ins_ip_add;
	}

	public void setReqdt_ins_ip_add(String reqdt_ins_ip_add) {
		this.reqdt_ins_ip_add = reqdt_ins_ip_add;
	}

	public String getReqdt_mod_ip_add() {
		return reqdt_mod_ip_add;
	}

	public void setReqdt_mod_ip_add(String reqdt_mod_ip_add) {
		this.reqdt_mod_ip_add = reqdt_mod_ip_add;
	}

	public String getReqdt_status() {
		return reqdt_status;
	}

	public void setReqdt_status(String reqdt_status) {
		this.reqdt_status = reqdt_status;
	}

	public String getProd_type() {
		return prod_type;
	}

	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}

	public String getProd_sub_type() {
		return prod_sub_type;
	}

	public void setProd_sub_type(String prod_sub_type) {
		this.prod_sub_type = prod_sub_type;
	}

	@Override
	public String toString() {
		return "AllocReqDet [alloc_req_dtl_id=" + alloc_req_dtl_id + ", alloc_req_id=" + alloc_req_id
				+ ", alloc_req_date=" + alloc_req_date + ", fin_year=" + fin_year + ", period_code=" + period_code
				+ ", company=" + company + ", division=" + division + ", fs_id=" + fs_id + ", territory_id="
				+ territory_id + ", prod_id=" + prod_id + ", prod_type=" + prod_type + ", prod_sub_type="
				+ prod_sub_type + ", prod_threshold=" + prod_threshold + ", prod_highvalue=" + prod_highvalue
				+ ", prod_sensitive=" + prod_sensitive + ", alt_div_id=" + alt_div_id + ", alloc_cycle=" + alloc_cycle
				+ ", alloc_rate=" + alloc_rate + ", alloc_qty=" + alloc_qty + ", alloc_value=" + alloc_value
				+ ", reqdt_ins_user_id=" + reqdt_ins_user_id + ", reqdt_mod_user_id=" + reqdt_mod_user_id
				+ ", reqdt_ins_dt=" + reqdt_ins_dt + ", reqdt_mod_dt=" + reqdt_mod_dt + ", reqdt_ins_ip_add="
				+ reqdt_ins_ip_add + ", reqdt_mod_ip_add=" + reqdt_mod_ip_add + ", reqdt_status=" + reqdt_status + "]";
	}
	
	
}
