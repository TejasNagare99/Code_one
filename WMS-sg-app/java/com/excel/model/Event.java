package com.excel.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name ="EVENT")
public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8818954676510190063L;
	
	@Id
	@Column(name = "EVENT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long event_id;
	
	@Column(name = "EVENT_DATE")
	private Date  event_date;
	
	@Transient 
	private String Date;
	
	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "EVENT_TEXT")
	private String event_text;
	
	@Column(name = "COMPANY_CODE")
	private String company_code;
	
	@Column(name = "USERNAME")
	private String username;
	
	
	
	
	
	@Transient private String fs_name;

	public Long getEvent_id() {
		return event_id;
	}

	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
	}

	public Date getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Date event_date) {
		this.event_date = event_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEvent_text() {
		return event_text;
	}

	public void setEvent_text(String event_text) {
		this.event_text = event_text;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFs_name() {
		return fs_name;
	}

	public void setFs_name(String fs_name) {
		this.fs_name = fs_name;
	}

}
