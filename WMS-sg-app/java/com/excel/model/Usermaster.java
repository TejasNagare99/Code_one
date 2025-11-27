package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="am_m_login_detail")
public class Usermaster {

	@Id
	@Column(name="ld_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long ld_id;

	@Column(name="ld_emp_cb_id")
	public String ld_emp_cb_id;

	@Column(name="ld_usr_typ_flg")
	public String ld_usr_typ_flg;

	@Column(name="ld_lgnid")
	public String ld_lgnid;

	@Column(name="ld_pass")
	public String ld_pass;
	
	@Column(name="ld_pass_ang")
	public String ld_pass_ang;

	@Column(name="ld_email")
	public String ld_email;

	@Column(name="ld_ins_usr_id")
	public String ld_ins_usr_id;

	@Column(name="ld_mod_usr_id")
	public String ld_mod_usr_id;

	@Column(name="ld_ins_dt")
	public Date ld_ins_dt;

	@Column(name="ld_mod_dt")
	public Date ld_mod_dt;

	@Column(name="ld_ins_ip_add")
	public String ld_ins_ip_add;

	@Column(name="ld_mod_ip_add")
	public String ld_mod_ip_add;

	@Column(name="ld_status")
	public String ld_status;

	@Column(name="is_temp")
	public String is_temp;

	@Column(name="ld_no_of_attempt_flg")
	public String ld_no_of_attempt_flg;

	@Column(name="user_lock")
	public String user_lock;

	@Column(name="DPTLOC_ID")
	public String dptloc_id;

	@Column(name="allow_batch_create")
	public String allow_batch_create;

	@Column(name="user_type")
	public String user_type;

	@Column(name="pass_mod_dt")
	public Date pass_mod_dt;

	@Column(name="password_lock")
	public String password_lock;

	@Column(name="password_lock_dt")
	public Date password_lock_dt;

	@Column(name="ld_max_login_attempts")
	public Long ld_max_login_attempts;

	@Column(name="ld_hours_locked")
	public Long ld_hours_locked;
	
	@Column(name="ld_exec_asst_ind")
	private String ld_exec_asst_ind;
	
	@Column(name="PWD_EXPIRY_DT")
	public Date pwd_expiry_dt;
	
	@Column(name = "SECRET_QUESTION")
	public String secret_question;
	
	@Column(name="SECRET_ANSWER")
	public String secret_answer;
	
	@Column(name = "PWD_EMAIL_IND")
	public String pwd_email_ind;
	
	@Column(name = "OTP")
	public String otp;
	
	@Column(name = "OTP_DATE_TIME")
	public Date otp_date_time;
	
	@Column(name = "OTP_MAX_ATTEMPT")
	public Integer otp_max_attempt;
	
	//@Column(name = "LAST_IP_ACCESSED")
	@Transient public String last_accessed_ip;
	
	@Column(name = "EXCEL_LOCK_IND")
	public Character excel_lock_ind;
	
	@Column(name = "PDF_LOCK_IND")
	public Character pdf_lock_ind;



	public Character getExcel_lock_ind() {
		return excel_lock_ind;
	}

	public void setExcel_lock_ind(Character excel_lock_ind) {
		this.excel_lock_ind = excel_lock_ind;
	}

	public Character getPdf_lock_ind() {
		return pdf_lock_ind;
	}

	public void setPdf_lock_ind(Character pdf_lock_ind) {
		this.pdf_lock_ind = pdf_lock_ind;
	}

	public Date getOtp_date_time() {
		return otp_date_time;
	}

	public void setOtp_date_time(Date otp_date_time) {
		this.otp_date_time = otp_date_time;
	}

	public Integer getOtp_max_attempt() {
		return otp_max_attempt;
	}

	public void setOtp_max_attempt(Integer otp_max_attempt) {
		this.otp_max_attempt = otp_max_attempt;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getLast_accessed_ip() {
		return last_accessed_ip;
	}

	public void setLast_accessed_ip(String last_accessed_ip) {
		this.last_accessed_ip = last_accessed_ip;
	}
	
	public String getSecret_question() {
		return secret_question;
	}

	public void setSecret_question(String secret_question) {
		this.secret_question = secret_question;
	}

	public String getSecret_answer() {
		return secret_answer;
	}

	public void setSecret_answer(String secret_answer) {
		this.secret_answer = secret_answer;
	}

	public String getPwd_email_ind() {
		return pwd_email_ind;
	}

	public void setPwd_email_ind(String pwd_email_ind) {
		this.pwd_email_ind = pwd_email_ind;
	}

	public Date getPwd_expiry_dt() {
		return pwd_expiry_dt;
	}

	public void setPwd_expiry_dt(Date pwd_expiry_dt) {
		this.pwd_expiry_dt = pwd_expiry_dt;
	}

	public Long getLd_id() {
		return ld_id;
	}

	public void setLd_id(Long ld_id) {
		this.ld_id = ld_id;
	}

	public String getLd_emp_cb_id() {
		return ld_emp_cb_id;
	}

	public void setLd_emp_cb_id(String ld_emp_cb_id) {
		this.ld_emp_cb_id = ld_emp_cb_id;
	}

	public String getLd_usr_typ_flg() {
		return ld_usr_typ_flg;
	}

	public void setLd_usr_typ_flg(String ld_usr_typ_flg) {
		this.ld_usr_typ_flg = ld_usr_typ_flg;
	}

	public String getLd_lgnid() {
		return ld_lgnid;
	}

	public void setLd_lgnid(String ld_lgnid) {
		this.ld_lgnid = ld_lgnid;
	}

	public String getLd_pass() {
		return ld_pass;
	}

	public void setLd_pass(String ld_pass) {
		this.ld_pass = ld_pass;
	}

	public String getLd_pass_ang() {
		return ld_pass_ang;
	}

	public void setLd_pass_ang(String ld_pass_ang) {
		this.ld_pass_ang = ld_pass_ang;
	}

	public String getLd_email() {
		return ld_email;
	}

	public void setLd_email(String ld_email) {
		this.ld_email = ld_email;
	}

	public String getLd_ins_usr_id() {
		return ld_ins_usr_id;
	}

	public void setLd_ins_usr_id(String ld_ins_usr_id) {
		this.ld_ins_usr_id = ld_ins_usr_id;
	}

	public String getLd_mod_usr_id() {
		return ld_mod_usr_id;
	}

	public void setLd_mod_usr_id(String ld_mod_usr_id) {
		this.ld_mod_usr_id = ld_mod_usr_id;
	}

	public Date getLd_ins_dt() {
		return ld_ins_dt;
	}

	public void setLd_ins_dt(Date ld_ins_dt) {
		this.ld_ins_dt = ld_ins_dt;
	}

	public Date getLd_mod_dt() {
		return ld_mod_dt;
	}

	public void setLd_mod_dt(Date ld_mod_dt) {
		this.ld_mod_dt = ld_mod_dt;
	}

	public String getLd_ins_ip_add() {
		return ld_ins_ip_add;
	}

	public void setLd_ins_ip_add(String ld_ins_ip_add) {
		this.ld_ins_ip_add = ld_ins_ip_add;
	}

	public String getLd_mod_ip_add() {
		return ld_mod_ip_add;
	}

	public void setLd_mod_ip_add(String ld_mod_ip_add) {
		this.ld_mod_ip_add = ld_mod_ip_add;
	}

	public String getLd_status() {
		return ld_status;
	}

	public void setLd_status(String ld_status) {
		this.ld_status = ld_status;
	}

	public String getIs_temp() {
		return is_temp;
	}

	public void setIs_temp(String is_temp) {
		this.is_temp = is_temp;
	}

	public String getLd_no_of_attempt_flg() {
		return ld_no_of_attempt_flg;
	}

	public void setLd_no_of_attempt_flg(String ld_no_of_attempt_flg) {
		this.ld_no_of_attempt_flg = ld_no_of_attempt_flg;
	}

	public String getUser_lock() {
		return user_lock;
	}

	public void setUser_lock(String user_lock) {
		this.user_lock = user_lock;
	}

	public String getDptloc_id() {
		return dptloc_id;
	}

	public void setDptloc_id(String dptloc_id) {
		this.dptloc_id = dptloc_id;
	}

	public String getAllow_batch_create() {
		return allow_batch_create;
	}

	public void setAllow_batch_create(String allow_batch_create) {
		this.allow_batch_create = allow_batch_create;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public Date getPass_mod_dt() {
		return pass_mod_dt;
	}

	public void setPass_mod_dt(Date pass_mod_dt) {
		this.pass_mod_dt = pass_mod_dt;
	}

	public String getPassword_lock() {
		return password_lock;
	}

	public void setPassword_lock(String password_lock) {
		this.password_lock = password_lock;
	}

	public Date getPassword_lock_dt() {
		return password_lock_dt;
	}

	public void setPassword_lock_dt(Date password_lock_dt) {
		this.password_lock_dt = password_lock_dt;
	}

	public Long getLd_max_login_attempts() {
		return ld_max_login_attempts;
	}

	public void setLd_max_login_attempts(Long ld_max_login_attempts) {
		this.ld_max_login_attempts = ld_max_login_attempts;
	}

	public Long getLd_hours_locked() {
		return ld_hours_locked;
	}

	public void setLd_hours_locked(Long ld_hours_locked) {
		this.ld_hours_locked = ld_hours_locked;
	}

	public String getLd_exec_asst_ind() {
		return ld_exec_asst_ind;
	}

	public void setLd_exec_asst_ind(String ld_exec_asst_ind) {
		this.ld_exec_asst_ind = ld_exec_asst_ind;
	}
	
	public Usermaster(String ld_pass_ang) {
		super();
		this.ld_pass_ang = ld_pass_ang;
	}

	public Usermaster() {
		super();
		// TODO Auto-generated constructor stub
	}
}
