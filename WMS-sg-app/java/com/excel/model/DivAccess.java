package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="am_m_emp_div_access")
public class DivAccess {
	
	@Id
	@Column(name="ediv_emp_id")
	public String ediv_emp_id;

	@Column(name="ediv_div_id")
	public Long ediv_div_id;

	@Column(name="ediv_ins_usr_id")
	public String ediv_ins_usr_id;

	@Column(name="ediv_mod_usr_id")
	public String ediv_mod_usr_id;

	@Column(name="ediv_ins_dt")
	public Date ediv_ins_dt;

	@Column(name="ediv_mod_dt")
	public Date ediv_mod_dt;

	@Column(name="ediv_ins_ip_add")
	public String ediv_ins_ip_add;

	@Column(name="ediv_mod_ip_add")
	public String ediv_mod_ip_add;

	@Column(name="ediv_status")
	public String ediv_status;

	public String getEdiv_emp_id() {
		return ediv_emp_id;
	}

	public void setEdiv_emp_id(String ediv_emp_id) {
		this.ediv_emp_id = ediv_emp_id;
	}

	public Long getEdiv_div_id() {
		return ediv_div_id;
	}

	public void setEdiv_div_id(Long ediv_div_id) {
		this.ediv_div_id = ediv_div_id;
	}

	public String getEdiv_ins_usr_id() {
		return ediv_ins_usr_id;
	}

	public void setEdiv_ins_usr_id(String ediv_ins_usr_id) {
		this.ediv_ins_usr_id = ediv_ins_usr_id;
	}

	public String getEdiv_mod_usr_id() {
		return ediv_mod_usr_id;
	}

	public void setEdiv_mod_usr_id(String ediv_mod_usr_id) {
		this.ediv_mod_usr_id = ediv_mod_usr_id;
	}

	public Date getEdiv_ins_dt() {
		return ediv_ins_dt;
	}

	public void setEdiv_ins_dt(Date ediv_ins_dt) {
		this.ediv_ins_dt = ediv_ins_dt;
	}

	public Date getEdiv_mod_dt() {
		return ediv_mod_dt;
	}

	public void setEdiv_mod_dt(Date ediv_mod_dt) {
		this.ediv_mod_dt = ediv_mod_dt;
	}

	public String getEdiv_ins_ip_add() {
		return ediv_ins_ip_add;
	}

	public void setEdiv_ins_ip_add(String ediv_ins_ip_add) {
		this.ediv_ins_ip_add = ediv_ins_ip_add;
	}

	public String getEdiv_mod_ip_add() {
		return ediv_mod_ip_add;
	}

	public void setEdiv_mod_ip_add(String ediv_mod_ip_add) {
		this.ediv_mod_ip_add = ediv_mod_ip_add;
	}

	public String getEdiv_status() {
		return ediv_status;
	}

	public void setEdiv_status(String ediv_status) {
		this.ediv_status = ediv_status;
	}

}
