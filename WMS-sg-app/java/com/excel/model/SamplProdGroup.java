package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="SAPRODGRP")
public class SamplProdGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SA_PROD_GROUP")
	private Long sa_prod_group;
	
	@Column(name="SA_GROUP_NAME")
	private String sa_group_name;
	
	@Column(name="SA_MPG_CODE")
	private String mpg_code;
	
	@Column(name="SA_INS_USR_ID")
	private String ins_usr_id;
	
	@Column(name="SA_INS_DT")
	private Date ins_dt;
	
	@Column(name="SA_INS_IP_ADD")
	private String ins_ip_add;
	
	@Column(name="SA_STATUS")
	private String status;
		
	public String getIns_usr_id() {
		return ins_usr_id;
	}
	public void setIns_usr_id(String ins_usr_id) {
		this.ins_usr_id = ins_usr_id;
	}
	public Date getIns_dt() {
		return ins_dt;
	}
	public void setIns_dt(Date ins_dt) {
		this.ins_dt = ins_dt;
	}
	public String getIns_ip_add() {
		return ins_ip_add;
	}
	public void setIns_ip_add(String ins_ip_add) {
		this.ins_ip_add = ins_ip_add;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMpg_code() {
		return mpg_code;
	}
	public void setMpg_code(String mpg_code) {
		this.mpg_code = mpg_code;
	}
	public Long getSa_prod_group() {
		return sa_prod_group;
	}
	public void setSa_prod_group(Long sa_prod_group) {
		this.sa_prod_group = sa_prod_group;
	}
	public String getSa_group_name() {
		return sa_group_name;
	}
	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}
	public SamplProdGroup(Long sa_prod_group, String sa_group_name) {
		super();
		this.sa_prod_group = sa_prod_group;
		this.sa_group_name = sa_group_name;
	}
	public SamplProdGroup() {

	}
	
}

