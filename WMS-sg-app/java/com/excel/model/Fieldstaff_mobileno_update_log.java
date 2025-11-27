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
@Table(name = "FIELDSTAFF_MOBILENO_UPDATE_LOG")
public class Fieldstaff_mobileno_update_log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SLNO")
	private Long slno;

	@Column(name = "FSTAFF_DIV_NAME")
	private String fstaff_div_name;

	@Column(name = "FSTAFF_LEVEL_CODE")
	private String fstaff_level_code;

	@Column(name = "FSTAFF_DESIG")
	private String fstaff_desig;

	@Column(name = "FSTAFF_ID")
	private Long fstaff_id;

	@Column(name = "FSTAFF_EMPLOYEE_NO")
	private String fstaff_employee_no;

	@Column(name = "FSTAFF_TERR_CODE")
	private String fstaff_terr_code;

	@Column(name = "FSTAFF_DISPLAY_NAME")
	private String fstaff_display_name;

	@Column(name = "FSTAFF_MOBILE")
	private String fstaff_mobile;

	@Column(name = "FSTAFF_ins_usr_id")
	private String fstaff_ins_usr_id;

	@Column(name = "FSTAFF_mod_usr_id")
	private String fstaff_mod_usr_id;

	@Column(name = "FSTAFF_ins_dt")
	private Date fstaff_ins_dt;

	@Column(name = "FSTAFF_mod_dt")
	private Date fstaff_mod_dt;

	@Column(name = "FSTAFF_ins_ip_add")
	private String fstaff_ins_ip_add;

	@Column(name = "FSTAFF_mod_ip_add")
	private String fstaff_mod_ip_add;

	@Column(name = "FSTAFF_status")
	private String fstaff_status;

	@Column(name = "CSV_FILE_NAME")
	private String csv_file_name;
	
	@Transient
	private String csv_ins_date;
	
	@Transient
	private String csv_mod_dt;

	public String getCsv_ins_date() {
		return csv_ins_date;
	}

	public String getCsv_mod_dt() {
		return csv_mod_dt;
	}

	public void setCsv_ins_date(String csv_ins_date) {
		this.csv_ins_date = csv_ins_date;
	}

	public void setCsv_mod_dt(String csv_mod_dt) {
		this.csv_mod_dt = csv_mod_dt;
	}

	public Long getSlno() {
		return slno;
	}

	public String getFstaff_div_name() {
		return fstaff_div_name;
	}

	public String getFstaff_level_code() {
		return fstaff_level_code;
	}

	public String getFstaff_desig() {
		return fstaff_desig;
	}

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public String getFstaff_employee_no() {
		return fstaff_employee_no;
	}

	public String getFstaff_terr_code() {
		return fstaff_terr_code;
	}

	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public String getFstaff_mobile() {
		return fstaff_mobile;
	}

	public String getFstaff_ins_usr_id() {
		return fstaff_ins_usr_id;
	}

	public String getFstaff_mod_usr_id() {
		return fstaff_mod_usr_id;
	}

	public Date getFstaff_ins_dt() {
		return fstaff_ins_dt;
	}

	public Date getFstaff_mod_dt() {
		return fstaff_mod_dt;
	}

	public String getFstaff_ins_ip_add() {
		return fstaff_ins_ip_add;
	}

	public String getFstaff_mod_ip_add() {
		return fstaff_mod_ip_add;
	}

	public String getFstaff_status() {
		return fstaff_status;
	}

	public String getCsv_file_name() {
		return csv_file_name;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public void setFstaff_div_name(String fstaff_div_name) {
		this.fstaff_div_name = fstaff_div_name;
	}

	public void setFstaff_level_code(String fstaff_level_code) {
		this.fstaff_level_code = fstaff_level_code;
	}

	public void setFstaff_desig(String fstaff_desig) {
		this.fstaff_desig = fstaff_desig;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public void setFstaff_employee_no(String fstaff_employee_no) {
		this.fstaff_employee_no = fstaff_employee_no;
	}

	public void setFstaff_terr_code(String fstaff_terr_code) {
		this.fstaff_terr_code = fstaff_terr_code;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public void setFstaff_mobile(String fstaff_mobile) {
		this.fstaff_mobile = fstaff_mobile;
	}

	public void setFstaff_ins_usr_id(String fstaff_ins_usr_id) {
		this.fstaff_ins_usr_id = fstaff_ins_usr_id;
	}

	public void setFstaff_mod_usr_id(String fstaff_mod_usr_id) {
		this.fstaff_mod_usr_id = fstaff_mod_usr_id;
	}

	public void setFstaff_ins_dt(Date fstaff_ins_dt) {
		this.fstaff_ins_dt = fstaff_ins_dt;
	}

	public void setFstaff_mod_dt(Date fstaff_mod_dt) {
		this.fstaff_mod_dt = fstaff_mod_dt;
	}

	public void setFstaff_ins_ip_add(String fstaff_ins_ip_add) {
		this.fstaff_ins_ip_add = fstaff_ins_ip_add;
	}

	public void setFstaff_mod_ip_add(String fstaff_mod_ip_add) {
		this.fstaff_mod_ip_add = fstaff_mod_ip_add;
	}

	public void setFstaff_status(String fstaff_status) {
		this.fstaff_status = fstaff_status;
	}

	public void setCsv_file_name(String csv_file_name) {
		this.csv_file_name = csv_file_name;
	}

	
}
