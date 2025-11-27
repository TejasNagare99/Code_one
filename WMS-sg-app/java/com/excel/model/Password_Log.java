package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="am_m_login_old_passwords_log")
@DynamicUpdate(value=true)
public class Password_Log {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name="emp_id")
	private String emp_id;
	
	@Column(name="password_change_dt")
	private Date password_change_dt;
	
	@Column(name="ld_pass_ang")
	private String id_pass_ang;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}



	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public Date getPassword_change_dt() {
		return password_change_dt;
	}

	public void setPassword_change_dt(Date password_change_dt) {
		this.password_change_dt = password_change_dt;
	}

	public String getId_pass_ang() {
		return id_pass_ang;
	}

	public void setId_pass_ang(String id_pass_ang) {
		this.id_pass_ang = id_pass_ang;
	}
	
	
}
