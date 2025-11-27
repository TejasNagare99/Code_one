package com.excel.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SCM_MST_PROD_LOCK")
public class SCM_MST_PROD_LOCK {

	@Id
	@Column(name="SLNO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String slno;
	
	@Column(name="PROD_ID")
	private String prod_id;
	
	@Column(name="USER_ID")
	private String user_id;
	
	@Column(name="LOG_TIME")
	private java.util.Date log_time;

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public java.util.Date getLog_time() {
		return log_time;
	}

	public void setLog_time(java.util.Date date) {
		this.log_time = date;
	}

	@Override
	public String toString() {
		return "SCM_MST_PROD_LOCK [slno=" + slno + ", prod_id=" + prod_id + ", user_id=" + user_id + ", log_time="
				+ log_time + "]";
	}
	
	
	
}
