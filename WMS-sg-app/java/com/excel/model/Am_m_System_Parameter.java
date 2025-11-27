package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="am_m_system_parameter")
public class Am_m_System_Parameter {
	
	@Id
	@Column(name="sp_id")
	private String sp_id;
	
	@Column(name="sp_detail")
	private String sp_detail;
	
	@Column(name="sp_name")
	private String sp_name;
	
	@Column(name="sp_value")
	private String sp_value;
	
	@Column(name="sp_abstract")
	private String sp_abstract;
	
	@Column(name="sp_ins_usr_id")
	private String sp_ins_usr_id;
	
	@Column(name="sp_mod_usr_id")
	private String sp_mod_usr_id;
	
	@Column(name="sp_ins_dt")
	private String sp_ins_dt;
	
	@Column(name="sp_mod_dt")
	private String sp_mod_dt;
	
	@Column(name="sp_ins_ip_add")
	private String sp_ins_ip_add;
	
	@Column(name="sp_mod_ip_add")
	private String sp_mod_ip_add;
	
	@Column(name="sp_status")
	private String sp_status;

	public String getSp_id() {
		return sp_id;
	}

	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}

	public String getSp_detail() {
		return sp_detail;
	}

	public void setSp_detail(String sp_detail) {
		this.sp_detail = sp_detail;
	}

	public String getSp_name() {
		return sp_name;
	}

	public void setSp_name(String sp_name) {
		this.sp_name = sp_name;
	}

	public String getSp_value() {
		return sp_value;
	}

	public void setSp_value(String sp_value) {
		this.sp_value = sp_value;
	}

	public String getSp_abstract() {
		return sp_abstract;
	}

	public void setSp_abstract(String sp_abstract) {
		this.sp_abstract = sp_abstract;
	}

	public String getSp_ins_usr_id() {
		return sp_ins_usr_id;
	}

	public void setSp_ins_usr_id(String sp_ins_usr_id) {
		this.sp_ins_usr_id = sp_ins_usr_id;
	}

	public String getSp_mod_usr_id() {
		return sp_mod_usr_id;
	}

	public void setSp_mod_usr_id(String sp_mod_usr_id) {
		this.sp_mod_usr_id = sp_mod_usr_id;
	}

	public String getSp_ins_dt() {
		return sp_ins_dt;
	}

	public void setSp_ins_dt(String sp_ins_dt) {
		this.sp_ins_dt = sp_ins_dt;
	}

	public String getSp_mod_dt() {
		return sp_mod_dt;
	}

	public void setSp_mod_dt(String sp_mod_dt) {
		this.sp_mod_dt = sp_mod_dt;
	}

	public String getSp_ins_ip_add() {
		return sp_ins_ip_add;
	}

	public void setSp_ins_ip_add(String sp_ins_ip_add) {
		this.sp_ins_ip_add = sp_ins_ip_add;
	}

	public String getSp_mod_ip_add() {
		return sp_mod_ip_add;
	}

	public void setSp_mod_ip_add(String sp_mod_ip_add) {
		this.sp_mod_ip_add = sp_mod_ip_add;
	}

	public String getSp_status() {
		return sp_status;
	}

	public void setSp_status(String sp_status) {
		this.sp_status = sp_status;
	}
	
}
