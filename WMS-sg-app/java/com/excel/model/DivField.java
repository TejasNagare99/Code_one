package com.excel.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DIV_FIELD")
public class DivField {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DIVFIELD_ID")
	private Long divfield_id;
	@Column(name = "COMPANY")
	private String company;
	@Column(name = "DIVISION")
	private String division;
	@Column(name = "DIV_ID")
	private Long div_id;
	@Column(name = "LEVEL_CODE")
	private String level_code;
	@Column(name = "LEVEL_NAME")
	private String level_name;
	@Column(name = "LEVEL_BRIEF_NAME")
	private String level_brief_name;
	@Column(name = "USER_ID")
	private Long user_id;
	@Column(name = "UPDATE_DATE")
	private Date update_date;
	@Column(name = "USER_IP_ADD")
	private String upd_ip_add;
	@Column(name = "STATUS")
	private String status;
	@Column(name="TRAININGIND")
	private String trainingind;
	@Column(name="FS_CATEGORY")
	private String fs_category;
	

	public Long getDivfield_id() {
		return divfield_id;
	}

	public void setDivfield_id(Long divfield_id) {
		this.divfield_id = divfield_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getLevel_code() {
		return level_code;
	}

	public void setLevel_code(String level_code) {
		this.level_code = level_code;
	}

	public String getLevel_name() {
		return level_name;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	public String getLevel_brief_name() {
		return level_brief_name;
	}

	public void setLevel_brief_name(String level_brief_name) {
		this.level_brief_name = level_brief_name;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getUpd_ip_add() {
		return upd_ip_add;
	}

	public void setUpd_ip_add(String upd_ip_add) {
		this.upd_ip_add = upd_ip_add;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getTrainingind() {
		return trainingind;
	}

	public void setTrainingind(String trainingind) {
		this.trainingind = trainingind;
	}

	public String getFs_category() {
		return fs_category;
	}

	public void setFs_category(String fs_category) {
		this.fs_category = fs_category;
	}

}

