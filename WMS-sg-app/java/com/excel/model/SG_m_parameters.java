package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SG_m_parameters")
public class SG_m_parameters implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3455858980582721685L;
	@Id
	@Column(name="SGprm_id")
	private Long sgprm_id;
	
	@Column(name="SGprm_nm")
	private String sgprm_nm;
	
	@Column(name="SGprm_disp_nm")
	private String sgprm_disp_nm;
	
	@Column(name="SGprm_depend_flg")
	private String sgprm_depend_flg;
	
	@Column(name="SGprm_desc")
	private String sgprm_desc;
	
	@Column(name="SGprm_ins_usr_id")
	private String sgprm_ins_usr_id;
	
	@Column(name="SGprm_mod_usr_id")
	private String sgprm_mod_usr_id;
	
	@Column(name="SGprm_ins_dt")
	private Date sgprm_ins_dt;
	
	@Column(name="SGprm_mod_dt")
	private Date sgprm_mod_dt;
	
	@Column(name="SGprm_ins_ip_add")
	private String sgprm_ins_ip_add;
	
	@Column(name="SGprm_mod_ip_add")
	private String sgprm_mod_ip_add;
	
	@Column(name="SGprm_status")
	private String sgprm_status;

	public Long getSgprm_id() {
		return sgprm_id;
	}

	public void setSgprm_id(Long sgprm_id) {
		this.sgprm_id = sgprm_id;
	}

	public String getSgprm_nm() {
		return sgprm_nm;
	}

	public void setSgprm_nm(String sgprm_nm) {
		this.sgprm_nm = sgprm_nm;
	}

	public String getSgprm_disp_nm() {
		return sgprm_disp_nm;
	}

	public void setSgprm_disp_nm(String sgprm_disp_nm) {
		this.sgprm_disp_nm = sgprm_disp_nm;
	}

	public String getSgprm_depend_flg() {
		return sgprm_depend_flg;
	}

	public void setSgprm_depend_flg(String sgprm_depend_flg) {
		this.sgprm_depend_flg = sgprm_depend_flg;
	}

	public String getSgprm_desc() {
		return sgprm_desc;
	}

	public void setSgprm_desc(String sgprm_desc) {
		this.sgprm_desc = sgprm_desc;
	}

	public String getSgprm_ins_usr_id() {
		return sgprm_ins_usr_id;
	}

	public void setSgprm_ins_usr_id(String sgprm_ins_usr_id) {
		this.sgprm_ins_usr_id = sgprm_ins_usr_id;
	}

	public String getSgprm_mod_usr_id() {
		return sgprm_mod_usr_id;
	}

	public void setSgprm_mod_usr_id(String sgprm_mod_usr_id) {
		this.sgprm_mod_usr_id = sgprm_mod_usr_id;
	}

	public Date getSgprm_ins_dt() {
		return sgprm_ins_dt;
	}

	public void setSgprm_ins_dt(Date sgprm_ins_dt) {
		this.sgprm_ins_dt = sgprm_ins_dt;
	}

	public Date getSgprm_mod_dt() {
		return sgprm_mod_dt;
	}

	public void setSgprm_mod_dt(Date sgprm_mod_dt) {
		this.sgprm_mod_dt = sgprm_mod_dt;
	}

	public String getSgprm_ins_ip_add() {
		return sgprm_ins_ip_add;
	}

	public void setSgprm_ins_ip_add(String sgprm_ins_ip_add) {
		this.sgprm_ins_ip_add = sgprm_ins_ip_add;
	}

	public String getSgprm_mod_ip_add() {
		return sgprm_mod_ip_add;
	}

	public void setSgprm_mod_ip_add(String sgprm_mod_ip_add) {
		this.sgprm_mod_ip_add = sgprm_mod_ip_add;
	}

	public String getSgprm_status() {
		return sgprm_status;
	}

	public void setSgprm_status(String sgprm_status) {
		this.sgprm_status = sgprm_status;
	}

}
