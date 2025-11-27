package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_MGR_ASST")
public class UserMgrAsst {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SLRNO")
	private Long slrno;
 
	@Column(name = "ASST_USER_ID")
	private Long alloc_gen_id;

	@Column(name = "ASST_LD_EMP_CB_ID")
	private String asst_ld_emp_cb_id;

	@Column(name = "ASST_LD_LGNID")
	private String asst_ld_lgnid;

	@Column(name = "MGR_USER_ID")
	private Long mgr_user_id;

	@Column(name = "MGR_LD_EMP_CB_ID")
	private String mgr_ld_emp_cb_id;

	@Column(name = "MGR_LD_LGNID")
	private String mgr_ld_lgnid;

	@Column(name = "USER_ins_usr_id")
	private String user_ins_usr_id;

	@Column(name = "USER_mod_usr_id")
	private String  user_mod_usr_id;

	@Column(name = "USER_ins_dt")
	private Date user_ins_dt;

	@Column(name = "USER_mod_dt")
	private Date user_mod_dt;

	@Column(name = "USER_ins_ip_add")
	private String user_ins_ip_add;

	@Column(name = "USER_mod_ip_add")
	private String user_mod_ip_add;

	@Column(name = "USER_status")
	private String user_status;

	public Long getSlrno() {
		return slrno;
	}

	public void setSlrno(Long slrno) {
		this.slrno = slrno;
	}

	public Long getAlloc_gen_id() {
		return alloc_gen_id;
	}

	public void setAlloc_gen_id(Long alloc_gen_id) {
		this.alloc_gen_id = alloc_gen_id;
	}

	public String getAsst_ld_emp_cb_id() {
		return asst_ld_emp_cb_id;
	}

	public void setAsst_ld_emp_cb_id(String asst_ld_emp_cb_id) {
		this.asst_ld_emp_cb_id = asst_ld_emp_cb_id;
	}

	public String getAsst_ld_lgnid() {
		return asst_ld_lgnid;
	}

	public void setAsst_ld_lgnid(String asst_ld_lgnid) {
		this.asst_ld_lgnid = asst_ld_lgnid;
	}

	public Long getMgr_user_id() {
		return mgr_user_id;
	}

	public void setMgr_user_id(Long mgr_user_id) {
		this.mgr_user_id = mgr_user_id;
	}

	public String getMgr_ld_emp_cb_id() {
		return mgr_ld_emp_cb_id;
	}

	public void setMgr_ld_emp_cb_id(String mgr_ld_emp_cb_id) {
		this.mgr_ld_emp_cb_id = mgr_ld_emp_cb_id;
	}

	public String getMgr_ld_lgnid() {
		return mgr_ld_lgnid;
	}

	public void setMgr_ld_lgnid(String mgr_ld_lgnid) {
		this.mgr_ld_lgnid = mgr_ld_lgnid;
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



	public Date getUser_mod_dt() {
		return user_mod_dt;
	}

	public void setUser_mod_dt(Date user_mod_dt) {
		this.user_mod_dt = user_mod_dt;
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

	public Date getUser_ins_dt() {
		return user_ins_dt;
	}

	public void setUser_ins_dt(Date user_ins_dt) {
		this.user_ins_dt = user_ins_dt;
	}
	
	public UserMgrAsst(Long mgr_user_id,String mgr_ld_emp_cb_id) {
		this.mgr_user_id=mgr_user_id;
		this.mgr_ld_emp_cb_id=mgr_ld_emp_cb_id;
	}
	

}
