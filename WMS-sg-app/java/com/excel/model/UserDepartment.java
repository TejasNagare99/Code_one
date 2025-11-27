package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "hr_m_department")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class UserDepartment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "dept_id")
	private String dept_id;
	
	@Column(name = "dept_nm")
	private String dept_nm;
	
	@Column(name = "dept_short_nm")
	private String dept_short_nm;
	
	@Column(name = "dept_abstract")
	private String dept_abstract;
	
	@Column(name = "dept_ins_usr_id")
	private String dept_ins_usr_id;
	
	@Column(name = "dept_mod_usr_id")
	private String dept_mod_usr_id;
	
	@Column(name = "dept_ins_dt")
	private Date dept_ins_dt;
	
	@Column(name = "dept_mod_dt")
	private Date dept_mod_dt;
	
	@Column(name = "dept_ins_ip_add")
	private String dept_ins_ip_add;
	
	@Column(name = "dept_mod_ip_add")
	private String dept_mod_ip_add;
	
	@Column(name = "dept_status")
	private String dept_status;

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_nm() {
		return dept_nm;
	}

	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}

	public String getDept_short_nm() {
		return dept_short_nm;
	}

	public void setDept_short_nm(String dept_short_nm) {
		this.dept_short_nm = dept_short_nm;
	}

	public String getDept_abstract() {
		return dept_abstract;
	}

	public void setDept_abstract(String dept_abstract) {
		this.dept_abstract = dept_abstract;
	}

	public String getDept_ins_usr_id() {
		return dept_ins_usr_id;
	}

	public void setDept_ins_usr_id(String dept_ins_usr_id) {
		this.dept_ins_usr_id = dept_ins_usr_id;
	}

	public String getDept_mod_usr_id() {
		return dept_mod_usr_id;
	}

	public void setDept_mod_usr_id(String dept_mod_usr_id) {
		this.dept_mod_usr_id = dept_mod_usr_id;
	}

	public Date getDept_ins_dt() {
		return dept_ins_dt;
	}

	public void setDept_ins_dt(Date dept_ins_dt) {
		this.dept_ins_dt = dept_ins_dt;
	}

	public Date getDept_mod_dt() {
		return dept_mod_dt;
	}

	public void setDept_mod_dt(Date dept_mod_dt) {
		this.dept_mod_dt = dept_mod_dt;
	}

	public String getDept_ins_ip_add() {
		return dept_ins_ip_add;
	}

	public void setDept_ins_ip_add(String dept_ins_ip_add) {
		this.dept_ins_ip_add = dept_ins_ip_add;
	}

	public String getDept_mod_ip_add() {
		return dept_mod_ip_add;
	}

	public void setDept_mod_ip_add(String dept_mod_ip_add) {
		this.dept_mod_ip_add = dept_mod_ip_add;
	}

	public String getDept_status() {
		return dept_status;
	}

	public void setDept_status(String dept_status) {
		this.dept_status = dept_status;
	}
	
}

