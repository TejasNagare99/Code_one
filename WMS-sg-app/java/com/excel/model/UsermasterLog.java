package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="am_m_login_attempts_log")
public class UsermasterLog {

	@Id
	@Column(name="ld_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long ld_id;

	@Column(name="login_id")
	public String login_id;

	@Column(name="login_status")
	public String login_status;

	@Column(name="login_attempts")
	public String login_attempts;

	@Column(name="mac_address")
	public String mac_address;
	
	@Column(name="ip_address")
	public String ip_address;

	@Column(name="attempt_date")
	public Date attempt_date;

	public Long getLd_id() {
		return ld_id;
	}

	public void setLd_id(Long ld_id) {
		this.ld_id = ld_id;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getLogin_status() {
		return login_status;
	}

	public void setLogin_status(String login_status) {
		this.login_status = login_status;
	}

	public String getLogin_attempts() {
		return login_attempts;
	}

	public void setLogin_attempts(String login_attempts) {
		this.login_attempts = login_attempts;
	}

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public Date getAttempt_date() {
		return attempt_date;
	}

	public void setAttempt_date(Date attempt_date) {
		this.attempt_date = attempt_date;
	}

	
}
