package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "ALLOC_HEADER")
public class Alloc_Header {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_ID")
	private Long alloc_id;
	@Column(name = "ALLOC_SMP_ID")
	private Long alloc_smp_id;
	@Column(name = "ALLOC_NO")
	private String alloc_no;
	@Column(name = "ALLOC_COMPANY")
	private String alloc_company;
	@Column(name = "ALLOC_FSTAFF_ID")
	private Long alloc_fstaff_id;
	@Column(name = "ALLOC_FIN_YEAR")
	private String alloc_fin_year;
	@Column(name = "ALLOC_PERIOD_CODE")
	private String alloc_period_code;
	@Column(name = "ALLOC_ERR_DTL")
	private String alloc_err_dtl;
	@Column(name = "ALLOC_INS_USR_ID")
	private String alloc_ins_usr_id;
	@Column(name = "ALLOC_MOD_USR_ID")
	private String alloc_mod_usr_id;
	@Column(name = "ALLOC_MOD_DT")
	private Date alloc_mod_dt;
	@Column(name = "ALLOC_INS_IP_ADD")
	private String alloc_ins_ip_add;
	@Column(name = "ALLOC_MOD_IP_ADD")
	private String alloc_mod_ip_add;
	@Column(name = "ALLOC_STATUS")
	private char alloc_status;
	@Column(name = "STOCK_AT_CFA")
	private String stock_at_cfa;
	@Column(name = "ALLOC_APPR_STATUS")
	private char alloc_appr_status;
	
	@Column(name = "ALLOC_TYPE")
	private String alloc_type;
	
	@Column(name = "SRT_NUMBER")
	private String srt_number;
	
	@Column(name = "SRT_DATE")
	private Date srt_date;
	
	@Column(name = "SRT_REMARK")
	private String srt_remark;
	
	
	public String getSrt_number() {
		return srt_number;
	}

	public void setSrt_number(String srt_number) {
		this.srt_number = srt_number;
	}

	public Date getSrt_date() {
		return srt_date;
	}

	public void setSrt_date(Date srt_date) {
		this.srt_date = srt_date;
	}

	public String getSrt_remark() {
		return srt_remark;
	}

	public void setSrt_remark(String srt_remark) {
		this.srt_remark = srt_remark;
	}

	public Long getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(Long alloc_id) {
		this.alloc_id = alloc_id;
	}

	public Long getAlloc_smp_id() {
		return alloc_smp_id;
	}

	public void setAlloc_smp_id(Long alloc_smp_id) {
		this.alloc_smp_id = alloc_smp_id;
	}

	public String getAlloc_no() {
		return alloc_no;
	}

	public void setAlloc_no(String alloc_no) {
		this.alloc_no = alloc_no;
	}

	public String getAlloc_company() {
		return alloc_company;
	}

	public void setAlloc_company(String alloc_company) {
		this.alloc_company = alloc_company;
	}

	public Long getAlloc_fstaff_id() {
		return alloc_fstaff_id;
	}

	public void setAlloc_fstaff_id(Long alloc_fstaff_id) {
		this.alloc_fstaff_id = alloc_fstaff_id;
	}

	public String getAlloc_fin_year() {
		return alloc_fin_year;
	}

	public void setAlloc_fin_year(String alloc_fin_year) {
		this.alloc_fin_year = alloc_fin_year;
	}

	public String getAlloc_period_code() {
		return alloc_period_code;
	}

	public void setAlloc_period_code(String alloc_period_code) {
		this.alloc_period_code = alloc_period_code;
	}

	public String getAlloc_err_dtl() {
		return alloc_err_dtl;
	}

	public void setAlloc_err_dtl(String alloc_err_dtl) {
		this.alloc_err_dtl = alloc_err_dtl;
	}

	public String getAlloc_ins_usr_id() {
		return alloc_ins_usr_id;
	}

	public void setAlloc_ins_usr_id(String alloc_ins_usr_id) {
		this.alloc_ins_usr_id = alloc_ins_usr_id;
	}

	public String getAlloc_mod_usr_id() {
		return alloc_mod_usr_id;
	}

	public void setAlloc_mod_usr_id(String alloc_mod_usr_id) {
		this.alloc_mod_usr_id = alloc_mod_usr_id;
	}

	public Date getAlloc_mod_dt() {
		return alloc_mod_dt;
	}

	public void setAlloc_mod_dt(Date alloc_mod_dt) {
		this.alloc_mod_dt = alloc_mod_dt;
	}

	public String getAlloc_ins_ip_add() {
		return alloc_ins_ip_add;
	}

	public void setAlloc_ins_ip_add(String alloc_ins_ip_add) {
		this.alloc_ins_ip_add = alloc_ins_ip_add;
	}

	public String getAlloc_mod_ip_add() {
		return alloc_mod_ip_add;
	}

	public void setAlloc_mod_ip_add(String alloc_mod_ip_add) {
		this.alloc_mod_ip_add = alloc_mod_ip_add;
	}

	public void setAlloc_status(char alloc_status) {
		this.alloc_status = alloc_status;
	}

	public char getAlloc_status() {
		return alloc_status;
	}

	public String getStock_at_cfa() {
	    return stock_at_cfa;
	}

	public void setStock_at_cfa(String stock_at_cfa) {
	    this.stock_at_cfa = stock_at_cfa;
	}

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public char getAlloc_appr_status() {
		return alloc_appr_status;
	}

	public void setAlloc_appr_status(char alloc_appr_status) {
		this.alloc_appr_status = alloc_appr_status;
	}
	
}

