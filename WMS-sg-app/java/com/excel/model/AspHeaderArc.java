package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="ASP_HEADER_ARC")
@DynamicUpdate(value=true)
public class AspHeaderArc implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ASP_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long asp_id;
	
	@Column(name="ASP_FIN_YEAR")
	private String asp_fin_year;
	
	@Column(name="ASP_USERID")
	private Long asp_userId;
	
	@Column(name="ASP_BRAND_MGRID")
	private Long asp_brand_mgrId;
	
	@Column(name="ASP_COMPANY_CODE")
	private String asp_company_code;
	
	@Column(name="ASP_ins_usr_id")
	private String asp_ins_user_id;
	
	@Column(name="ASP_mod_usr_id")
	private String ASP_mod_user_id;
	
	@Column(name="ASP_ins_dt")
	private Date asp_ins_dt;
	
	@Column(name="ASP_mod_dt")
	private Date asp_mod_dt;
	
	@Column(name="ASP_ins_ip_add")
	private String asp_ins_ip_add;
	
	@Column(name="ASP_mod_ip_add")
	private String asp_mod_ip_add;
	
	@Column(name="ASP_status")
	private String asp_status;
	
	@Column(name="ASP_APPR_STATUS")
	private String asp_appr_status;
	



	public Date getAsp_ins_dt() {
		return asp_ins_dt;
	}

	public void setAsp_ins_dt(Date asp_ins_dt) {
		this.asp_ins_dt = asp_ins_dt;
	}


	public String getAsp_ins_ip_add() {
		return asp_ins_ip_add;
	}

	public void setAsp_ins_ip_add(String asp_ins_ip_add) {
		this.asp_ins_ip_add = asp_ins_ip_add;
	}

	public String getAsp_mod_ip_add() {
		return asp_mod_ip_add;
	}

	public void setAsp_mod_ip_add(String asp_mod_ip_add) {
		this.asp_mod_ip_add = asp_mod_ip_add;
	}

	public String getAsp_status() {
		return asp_status;
	}

	public void setAsp_status(String asp_status) {
		this.asp_status = asp_status;
	}

	public String getAsp_appr_status() {
		return asp_appr_status;
	}

	public void setAsp_appr_status(String asp_appr_status) {
		this.asp_appr_status = asp_appr_status;
	}

	public Long getAsp_id() {
		return asp_id;
	}

	public void setAsp_id(Long asp_id) {
		this.asp_id = asp_id;
	}

	public String getAsp_fin_year() {
		return asp_fin_year;
	}

	public void setAsp_fin_year(String asp_fin_year) {
		this.asp_fin_year = asp_fin_year;
	}

	public Long getAsp_userId() {
		return asp_userId;
	}

	public void setAsp_userId(Long asp_userId) {
		this.asp_userId = asp_userId;
	}

	public String getAsp_company_code() {
		return asp_company_code;
	}

	public void setAsp_company_code(String asp_company_code) {
		this.asp_company_code = asp_company_code;
	}

	public Long getAsp_brand_mgrId() {
		return asp_brand_mgrId;
	}

	public void setAsp_brand_mgrId(Long asp_brand_mgrId) {
		this.asp_brand_mgrId = asp_brand_mgrId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getAsp_mod_dt() {
		return asp_mod_dt;
	}

	public void setAsp_mod_dt(Date asp_mod_dt) {
		this.asp_mod_dt = asp_mod_dt;
	}

	public String getASP_mod_user_id() {
		return ASP_mod_user_id;
	}

	public void setASP_mod_user_id(String aSP_mod_user_id) {
		ASP_mod_user_id = aSP_mod_user_id;
	}

	public String getAsp_ins_user_id() {
		return asp_ins_user_id;
	}

	public void setAsp_ins_user_id(String asp_ins_user_id) {
		this.asp_ins_user_id = asp_ins_user_id;
	}

	
	
	
}
