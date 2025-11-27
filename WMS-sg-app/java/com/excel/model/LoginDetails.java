package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
@Entity
@Table(name = "LoginDetails")
//@NamedNativeQueries({ @NamedNativeQuery(name = "callLogin_details", query = "EXEC p_g_login_details  :login_id", resultClass = LoginDetails.class) })
@NamedStoredProcedureQuery(
		name="callLoginDetails",
		procedureName = "p_g_login_details",
		resultClasses = LoginDetails.class,
		parameters = {
				@StoredProcedureParameter(name="pvld_lgnid", mode = ParameterMode.IN, type = String.class)
		}
)
public class LoginDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ld_lgnid")
	private String ld_lgnid;
	@Column(name = "ld_usr_typ_flg")
	private String ld_usr_typ_flg;
	@Column(name = "emp_id")
	private String emp_id;
	@Column(name = "emp_egrp_id")
	private String emp_egrp_id;
	@Column(name = "ld_emp_cb_id")
	private String ld_emp_cb_id;
	@Column(name = "emp_name")
	private String emp_name;
	@Column(name = "emp_lgn_valid_frm_dt")
	private String emp_lgn_valid_frm_dt;
	@Column(name = "emp_lgn_valid_to_dt")
	private String emp_lgn_valid_to_dt;
	@Column(name = "emp_join_dt")
	private String emp_join_dt;
	@Column(name = "emp_lgn_lock")
	private String emp_lgn_lock;
	@Column(name = "egrp_nm")
	private String egrp_nm;
	@Column(name = "emp_status")
	private String emp_status;
	@Column(name = "emp_resign_dt")
	private String emp_resign_dt;
	@Column(name = "emp_mob")
	private String emp_mob;
	@Column(name = "emp_email")
	private String emp_email;
	@Column(name = "emp_add")
	private String emp_add;
	@Column(name = "Cust_name")
	private String cust_name;
	@Column(name = "emp_COMP_id")
	private String emp_COMP_id;
	@Column(name = "emp_loc_id")
	private Long emp_loc_id;
	@Column(name = "loc_SubComp_id")
	private Long loc_SubComp_id;
	@Column(name = "ld_no_of_attempt_flg")
	private String ld_no_of_attempt_flg;
	@Column(name = "Cnt_admn")
	private String cnt_admn;

	public String getLd_lgnid() {
		return ld_lgnid;
	}
	public void setLd_lgnid(String ld_lgnid) {
		this.ld_lgnid = ld_lgnid;
	}
	public String getLd_usr_typ_flg() {
		return ld_usr_typ_flg;
	}
	public void setLd_usr_typ_flg(String ld_usr_typ_flg) {
		this.ld_usr_typ_flg = ld_usr_typ_flg;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_egrp_id() {
		return emp_egrp_id;
	}
	public void setEmp_egrp_id(String emp_egrp_id) {
		this.emp_egrp_id = emp_egrp_id;
	}
	public String getLd_emp_cb_id() {
		return ld_emp_cb_id;
	}
	public void setLd_emp_cb_id(String ld_emp_cb_id) {
		this.ld_emp_cb_id = ld_emp_cb_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getEmp_lgn_valid_frm_dt() {
		return emp_lgn_valid_frm_dt;
	}
	public void setEmp_lgn_valid_frm_dt(String emp_lgn_valid_frm_dt) {
		this.emp_lgn_valid_frm_dt = emp_lgn_valid_frm_dt;
	}
	public String getEmp_lgn_valid_to_dt() {
		return emp_lgn_valid_to_dt;
	}
	public void setEmp_lgn_valid_to_dt(String emp_lgn_valid_to_dt) {
		this.emp_lgn_valid_to_dt = emp_lgn_valid_to_dt;
	}
	public String getEmp_join_dt() {
		return emp_join_dt;
	}
	public void setEmp_join_dt(String emp_join_dt) {
		this.emp_join_dt = emp_join_dt;
	}
	public String getEmp_lgn_lock() {
		return emp_lgn_lock;
	}
	public void setEmp_lgn_lock(String emp_lgn_lock) {
		this.emp_lgn_lock = emp_lgn_lock;
	}
	public String getEgrp_nm() {
		return egrp_nm;
	}
	public void setEgrp_nm(String egrp_nm) {
		this.egrp_nm = egrp_nm;
	}
	public String getEmp_status() {
		return emp_status;
	}
	public void setEmp_status(String emp_status) {
		this.emp_status = emp_status;
	}
	public String getEmp_resign_dt() {
		return emp_resign_dt;
	}
	public void setEmp_resign_dt(String emp_resign_dt) {
		this.emp_resign_dt = emp_resign_dt;
	}
	public String getEmp_mob() {
		return emp_mob;
	}
	public void setEmp_mob(String emp_mob) {
		this.emp_mob = emp_mob;
	}
	public String getEmp_email() {
		return emp_email;
	}
	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}
	public String getEmp_add() {
		return emp_add;
	}
	public void setEmp_add(String emp_add) {
		this.emp_add = emp_add;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getEmp_COMP_id() {
		return emp_COMP_id;
	}
	public void setEmp_COMP_id(String emp_COMP_id) {
		this.emp_COMP_id = emp_COMP_id;
	}
	public Long getEmp_loc_id() {
		return emp_loc_id;
	}
	public void setEmp_loc_id(Long emp_loc_id) {
		this.emp_loc_id = emp_loc_id;
	}
	public Long getLoc_SubComp_id() {
		return loc_SubComp_id;
	}
	public void setLoc_SubComp_id(Long loc_SubComp_id) {
		this.loc_SubComp_id = loc_SubComp_id;
	}
	public String getLd_no_of_attempt_flg() {
		return ld_no_of_attempt_flg;
	}
	public void setLd_no_of_attempt_flg(String ld_no_of_attempt_flg) {
		this.ld_no_of_attempt_flg = ld_no_of_attempt_flg;
	}
	public String getCnt_admn() {
		return cnt_admn;
	}
	public void setCnt_admn(String cnt_admn) {
		this.cnt_admn = cnt_admn;
	}
}
