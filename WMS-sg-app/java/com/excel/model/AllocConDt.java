package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ALLOC_CON_DT")
public class AllocConDt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_CON_DTL_ID")
	private Long alloc_con_dtl_id;
	
	@ManyToOne
	@JoinColumn(name = "ALLOC_CON_ID")
	private AllocConHd alloc_con_id;
	
	@Column(name = "ALLOC_CON_DOCNO")
	private String alloc_con_docno;
	
	@Column(name = "ALLOC_CON_DATE")
	private Date alloc_con_date;
	
	@Column(name = "ALLOC_CON_TYPE")
	private String alloc_con_type;
	
	@Column(name = "ALLOC_TYPE")
	private char alloc_type	;
	
	@Column(name = "FIN_YEAR")
	private String fin_year;
	
	@Column(name = "PERIOD_CODE")
	private String period_code;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "DIVISION")
	private String division;
	
	@Column(name = "DIV_ID")
	private Long div_id;
	
	@Column(name = "ALLOC_MONTH")
	private String alloc_month;
	
	@Column(name = "ALLOC_USE_MONTH")
	private String alloc_use_month;
	
	@Column(name = "ALLOC_GEN_ID")
	private Long alloc_gen_id;
	
	@Column(name = "ALLOC_DOC_NO")
	private String alloc_doc_no;
	
	@Column(name = "ALLOC_CON_ID_T")
	private Long alloc_con_id_t;
	
	@Column(name = "ALLOC_CON_DOCNO_T")
	private String alloc_con_docno_t;
	
	@Column(name = "USER_ID")
	private String user_id;
	
	@Column(name = "UPD_DATE")
	private Date upd_date;
	
	@Column(name = "UPD_IP_ADD")
	private String upd_ip_add;
	
	@Column(name = "STATUS")
	private char status;
	
	@Column(name = "MOD_USER_ID")
	private String mod_user_id;
	
	@Column(name = "MOD_UPD_DATE")
	private Date mod_upd_date;
	
	@Column(name = "MOD_UPD_IP_ADD")
	private String mod_upd_ip_add;

	public Long getAlloc_con_dtl_id() {
		return alloc_con_dtl_id;
	}

	public void setAlloc_con_dtl_id(Long alloc_con_dtl_id) {
		this.alloc_con_dtl_id = alloc_con_dtl_id;
	}

	public String getAlloc_con_docno() {
		return alloc_con_docno;
	}

	public void setAlloc_con_docno(String alloc_con_docno) {
		this.alloc_con_docno = alloc_con_docno;
	}

	public Date getAlloc_con_date() {
		return alloc_con_date;
	}

	public void setAlloc_con_date(Date alloc_con_date) {
		this.alloc_con_date = alloc_con_date;
	}

	public String getAlloc_con_type() {
		return alloc_con_type;
	}

	public void setAlloc_con_type(String alloc_con_type) {
		this.alloc_con_type = alloc_con_type;
	}

	public char getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(char alloc_type) {
		this.alloc_type = alloc_type;
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

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getAlloc_month() {
		return alloc_month;
	}

	public void setAlloc_month(String alloc_month) {
		this.alloc_month = alloc_month;
	}

	public String getAlloc_use_month() {
		return alloc_use_month;
	}

	public void setAlloc_use_month(String alloc_use_month) {
		this.alloc_use_month = alloc_use_month;
	}

	public Long getAlloc_gen_id() {
		return alloc_gen_id;
	}

	public void setAlloc_gen_id(Long alloc_gen_id) {
		this.alloc_gen_id = alloc_gen_id;
	}

	public String getAlloc_doc_no() {
		return alloc_doc_no;
	}

	public void setAlloc_doc_no(String alloc_doc_no) {
		this.alloc_doc_no = alloc_doc_no;
	}

	public Long getAlloc_con_id_t() {
		return alloc_con_id_t;
	}

	public void setAlloc_con_id_t(Long alloc_con_id_t) {
		this.alloc_con_id_t = alloc_con_id_t;
	}

	public String getAlloc_con_docno_t() {
		return alloc_con_docno_t;
	}

	public void setAlloc_con_docno_t(String alloc_con_docno_t) {
		this.alloc_con_docno_t = alloc_con_docno_t;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Date upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_ip_add() {
		return upd_ip_add;
	}

	public void setUpd_ip_add(String upd_ip_add) {
		this.upd_ip_add = upd_ip_add;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getMod_user_id() {
		return mod_user_id;
	}

	public void setMod_user_id(String mod_user_id) {
		this.mod_user_id = mod_user_id;
	}

	public Date getMod_upd_date() {
		return mod_upd_date;
	}

	public void setMod_upd_date(Date mod_upd_date) {
		this.mod_upd_date = mod_upd_date;
	}

	public String getMod_upd_ip_add() {
		return mod_upd_ip_add;
	}

	public void setMod_upd_ip_add(String mod_upd_ip_add) {
		this.mod_upd_ip_add = mod_upd_ip_add;
	}

	public AllocConHd getAlloc_con_id() {
		return alloc_con_id;
	}

	public void setAlloc_con_id(AllocConHd alloc_con_id) {
		this.alloc_con_id = alloc_con_id;
	}
	
	

}
