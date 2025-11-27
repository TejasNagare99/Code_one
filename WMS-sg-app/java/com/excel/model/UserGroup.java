package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hr_m_employee_group")
public class UserGroup {
	
	@Id
	@Column(name="egrp_id")
	public String egrp_id;

	@Column(name="egrp_nm")
	public String egrp_nm;

	@Column(name="egrp_short_nm")
	public String egrp_short_nm;

	@Column(name="egrp_abstract")
	public String egrp_abstract;

	@Column(name="egrp_ins_usr_id")
	public String egrp_ins_usr_id;

	@Column(name="egrp_mod_usr_id")
	public String egrp_mod_usr_id;

	@Column(name="egrp_ins_dt")
	public Date egrp_ins_dt;

	@Column(name="egrp_mod_dt")
	public Date egrp_mod_dt;

	@Column(name="egrp_ins_ip_add")
	public String egrp_ins_ip_add;

	@Column(name="egrp_mod_ip_add")
	public String egrp_mod_ip_add;

	@Column(name="egrp_status")
	public String egrp_status;

	public String getEgrp_id() {
		return egrp_id;
	}

	public void setEgrp_id(String egrp_id) {
		this.egrp_id = egrp_id;
	}

	public String getEgrp_nm() {
		return egrp_nm;
	}

	public void setEgrp_nm(String egrp_nm) {
		this.egrp_nm = egrp_nm;
	}

	public String getEgrp_short_nm() {
		return egrp_short_nm;
	}

	public void setEgrp_short_nm(String egrp_short_nm) {
		this.egrp_short_nm = egrp_short_nm;
	}

	public String getEgrp_abstract() {
		return egrp_abstract;
	}

	public void setEgrp_abstract(String egrp_abstract) {
		this.egrp_abstract = egrp_abstract;
	}

	public String getEgrp_ins_usr_id() {
		return egrp_ins_usr_id;
	}

	public void setEgrp_ins_usr_id(String egrp_ins_usr_id) {
		this.egrp_ins_usr_id = egrp_ins_usr_id;
	}

	public String getEgrp_mod_usr_id() {
		return egrp_mod_usr_id;
	}

	public void setEgrp_mod_usr_id(String egrp_mod_usr_id) {
		this.egrp_mod_usr_id = egrp_mod_usr_id;
	}

	public Date getEgrp_ins_dt() {
		return egrp_ins_dt;
	}

	public void setEgrp_ins_dt(Date egrp_ins_dt) {
		this.egrp_ins_dt = egrp_ins_dt;
	}

	public Date getEgrp_mod_dt() {
		return egrp_mod_dt;
	}

	public void setEgrp_mod_dt(Date egrp_mod_dt) {
		this.egrp_mod_dt = egrp_mod_dt;
	}

	public String getEgrp_ins_ip_add() {
		return egrp_ins_ip_add;
	}

	public void setEgrp_ins_ip_add(String egrp_ins_ip_add) {
		this.egrp_ins_ip_add = egrp_ins_ip_add;
	}

	public String getEgrp_mod_ip_add() {
		return egrp_mod_ip_add;
	}

	public void setEgrp_mod_ip_add(String egrp_mod_ip_add) {
		this.egrp_mod_ip_add = egrp_mod_ip_add;
	}

	public String getEgrp_status() {
		return egrp_status;
	}

	public void setEgrp_status(String egrp_status) {
		this.egrp_status = egrp_status;
	}
	
}
