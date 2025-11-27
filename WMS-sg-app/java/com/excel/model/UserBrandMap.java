package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "USER_SKU_MAP")
@DynamicUpdate(value=true)
public class UserBrandMap {	

	/**
	 * 
	 */
	private static final long serialVersionUID = -326035211647419703L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="USERID")
	private Long userid;
	
	@Column(name="SMP_SA_PROD_GROUP")
	private Long smp_sa_prod_group;
	
	@Column(name="SMP_PROD_ID")
	private Long smp_prod_id;

	@Column(name="COMPANY_CODE")
	private String company_code;
	
	@Column(name="USER_INS_USR_ID")
	private String user_ins_usr_id;
	
	@Column(name="USER_MOD_USR_ID")
	private String user_mod_usr_id;
	
	@Column(name="USER_INS_DT")
	private Date user_ins_date;
	
	@Column(name="USER_MOD_DT")
	private Date user_mod_date;
	
	@Column(name="USER_INS_IP_ADD")
	private String user_ins_ip_add;
	
	@Column(name="USER_MOD_IP_ADD")
	private String user_mod_ip_add;
	
	@Column(name="USER_STATUS")
	private String user_status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getSmp_sa_prod_group() {
		return smp_sa_prod_group;
	}

	public void setSmp_sa_prod_group(Long smp_sa_prod_group) {
		this.smp_sa_prod_group = smp_sa_prod_group;
	}

	public Long getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getUser_ins_usr_id() {
		return user_ins_usr_id;
	}

	public void setUser_ins_usr_id(String user_ins_usr_id) {
		this.user_ins_usr_id = user_ins_usr_id;
	}

	public String getUser_mod_usr_id() {
		return user_mod_usr_id;
	}

	public void setUser_mod_usr_id(String user_mod_usr_id) {
		this.user_mod_usr_id = user_mod_usr_id;
	}

	public Date getUser_ins_date() {
		return user_ins_date;
	}

	public void setUser_ins_date(Date user_ins_date) {
		this.user_ins_date = user_ins_date;
	}

	public Date getUser_mod_date() {
		return user_mod_date;
	}

	public void setUser_mod_date(Date user_mod_date) {
		this.user_mod_date = user_mod_date;
	}

	public String getUser_ins_ip_add() {
		return user_ins_ip_add;
	}

	public void setUser_ins_ip_add(String user_ins_ip_add) {
		this.user_ins_ip_add = user_ins_ip_add;
	}

	public String getUser_mod_ip_add() {
		return user_mod_ip_add;
	}

	public void setUser_mod_ip_add(String user_mod_ip_add) {
		this.user_mod_ip_add = user_mod_ip_add;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
