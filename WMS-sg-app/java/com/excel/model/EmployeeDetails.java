package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hr_m_employee")
public class EmployeeDetails {
	@Id
	@Column(name="emp_id")
	public String emp_id;

	@Column(name="emp_egrp_id")
	public String emp_egrp_id;

	@Column(name="emp_dept_id")
	public String emp_dept_id;

	@Column(name="emp_loc_id")
	public String emp_loc_id;

	@Column(name="emp_COMP_id")
	public String emp_comp_id;

	@Column(name="emp_desg_id")
	public String emp_desg_id;

	@Column(name="emp_sup_emp_id")
	public String emp_sup_emp_id;

	@Column(name="emp_fnm")
	public String emp_fnm;

	@Column(name="emp_mnm")
	public String emp_mnm;

	@Column(name="emp_lnm")
	public String emp_lnm;

	@Column(name="emp_gender")
	public String emp_gender;

	@Column(name="emp_birth_dt")
	public Date emp_birth_dt;

	@Column(name="emp_lgn_valid_frm_dt")
	public Date emp_lgn_valid_frm_dt;

	@Column(name="emp_lgn_valid_to_dt")
	public Date emp_lgn_valid_to_dt;

	@Column(name="emp_join_dt")
	public Date emp_join_dt;

	@Column(name="emp_resign_dt")
	public Date emp_resign_dt;

	@Column(name="emp_sal")
	public BigDecimal emp_sal;

	@Column(name="emp_abstract")
	public String emp_abstract;

	@Column(name="emp_add")
	public String emp_add;

	@Column(name="emp_phno")
	public String emp_phno;

	@Column(name="emp_mob")
	public String emp_mob;

	@Column(name="emp_email")
	public String emp_email;

	@Column(name="emp_lgn_lock")
	public String emp_lgn_lock;

	@Column(name="emp_emg_cont_prsn")
	public String emp_emg_cont_prsn;

	@Column(name="emp_emg_cont_phno")
	public String emp_emg_cont_phno;

	@Column(name="emp_ins_usr_id")
	public String emp_ins_usr_id;

	@Column(name="emp_mod_usr_id")
	public String emp_mod_usr_id;

	@Column(name="emp_ins_dt")
	public String emp_ins_dt;

	@Column(name="emp_mod_dt")
	public String emp_mod_dt;

	@Column(name="emp_ins_ip_add")
	public String emp_ins_ip_add;

	@Column(name="emp_mod_ip_add")
	public String emp_mod_ip_add;

	@Column(name="emp_status")
	public String emp_status;

	@Column(name="emp_index")
	public Long emp_index;
	
	@Column(name="fstaff_id")
	private Long fstaff_id;
	
	@Column(name="RM_EMP_ID")
	private String rm_emp_id;
	
	@Column(name="DM_EMP_ID")
	private String dm_emp_id;
	
	@Column(name="SM_EMP_ID")
	private String sm_emp_id;
	
	@Column(name="EMP_DESG_ANG")
	private String emp_desg_ang;
	
	

	public String getEmp_desg_ang() {
		return emp_desg_ang;
	}

	public void setEmp_desg_ang(String emp_desg_ang) {
		this.emp_desg_ang = emp_desg_ang;
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

	public String getEmp_dept_id() {
		return emp_dept_id;
	}

	public void setEmp_dept_id(String emp_dept_id) {
		this.emp_dept_id = emp_dept_id;
	}

	public String getEmp_loc_id() {
		return emp_loc_id;
	}

	public void setEmp_loc_id(String emp_loc_id) {
		this.emp_loc_id = emp_loc_id;
	}

	public String getEmp_comp_id() {
		return emp_comp_id;
	}

	public void setEmp_comp_id(String emp_comp_id) {
		this.emp_comp_id = emp_comp_id;
	}

	public String getEmp_desg_id() {
		return emp_desg_id;
	}

	public void setEmp_desg_id(String emp_desg_id) {
		this.emp_desg_id = emp_desg_id;
	}

	public String getEmp_sup_emp_id() {
		return emp_sup_emp_id;
	}

	public void setEmp_sup_emp_id(String emp_sup_emp_id) {
		this.emp_sup_emp_id = emp_sup_emp_id;
	}

	public String getEmp_fnm() {
		return emp_fnm;
	}

	public void setEmp_fnm(String emp_fnm) {
		this.emp_fnm = emp_fnm;
	}

	public String getEmp_mnm() {
		return emp_mnm;
	}

	public void setEmp_mnm(String emp_mnm) {
		this.emp_mnm = emp_mnm;
	}

	public String getEmp_lnm() {
		return emp_lnm;
	}

	public void setEmp_lnm(String emp_lnm) {
		this.emp_lnm = emp_lnm;
	}

	public String getEmp_gender() {
		return emp_gender;
	}

	public void setEmp_gender(String emp_gender) {
		this.emp_gender = emp_gender;
	}

	public Date getEmp_birth_dt() {
		return emp_birth_dt;
	}

	public void setEmp_birth_dt(Date emp_birth_dt) {
		this.emp_birth_dt = emp_birth_dt;
	}

	public Date getEmp_lgn_valid_frm_dt() {
		return emp_lgn_valid_frm_dt;
	}

	public void setEmp_lgn_valid_frm_dt(Date emp_lgn_valid_frm_dt) {
		this.emp_lgn_valid_frm_dt = emp_lgn_valid_frm_dt;
	}

	public Date getEmp_lgn_valid_to_dt() {
		return emp_lgn_valid_to_dt;
	}

	public void setEmp_lgn_valid_to_dt(Date emp_lgn_valid_to_dt) {
		this.emp_lgn_valid_to_dt = emp_lgn_valid_to_dt;
	}

	public Date getEmp_join_dt() {
		return emp_join_dt;
	}

	public void setEmp_join_dt(Date emp_join_dt) {
		this.emp_join_dt = emp_join_dt;
	}

	public Date getEmp_resign_dt() {
		return emp_resign_dt;
	}

	public void setEmp_resign_dt(Date emp_resign_dt) {
		this.emp_resign_dt = emp_resign_dt;
	}

	public BigDecimal getEmp_sal() {
		return emp_sal;
	}

	public void setEmp_sal(BigDecimal emp_sal) {
		this.emp_sal = emp_sal;
	}

	public String getEmp_abstract() {
		return emp_abstract;
	}

	public void setEmp_abstract(String emp_abstract) {
		this.emp_abstract = emp_abstract;
	}

	public String getEmp_add() {
		return emp_add;
	}

	public void setEmp_add(String emp_add) {
		this.emp_add = emp_add;
	}

	public String getEmp_phno() {
		return emp_phno;
	}

	public void setEmp_phno(String emp_phno) {
		this.emp_phno = emp_phno;
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

	public String getEmp_lgn_lock() {
		return emp_lgn_lock;
	}

	public void setEmp_lgn_lock(String emp_lgn_lock) {
		this.emp_lgn_lock = emp_lgn_lock;
	}

	public String getEmp_emg_cont_prsn() {
		return emp_emg_cont_prsn;
	}

	public void setEmp_emg_cont_prsn(String emp_emg_cont_prsn) {
		this.emp_emg_cont_prsn = emp_emg_cont_prsn;
	}

	public String getEmp_emg_cont_phno() {
		return emp_emg_cont_phno;
	}

	public void setEmp_emg_cont_phno(String emp_emg_cont_phno) {
		this.emp_emg_cont_phno = emp_emg_cont_phno;
	}

	public String getEmp_ins_usr_id() {
		return emp_ins_usr_id;
	}

	public void setEmp_ins_usr_id(String emp_ins_usr_id) {
		this.emp_ins_usr_id = emp_ins_usr_id;
	}

	public String getEmp_mod_usr_id() {
		return emp_mod_usr_id;
	}

	public void setEmp_mod_usr_id(String emp_mod_usr_id) {
		this.emp_mod_usr_id = emp_mod_usr_id;
	}

	public String getEmp_ins_dt() {
		return emp_ins_dt;
	}

	public void setEmp_ins_dt(String emp_ins_dt) {
		this.emp_ins_dt = emp_ins_dt;
	}

	public String getEmp_mod_dt() {
		return emp_mod_dt;
	}

	public void setEmp_mod_dt(String emp_mod_dt) {
		this.emp_mod_dt = emp_mod_dt;
	}

	public String getEmp_ins_ip_add() {
		return emp_ins_ip_add;
	}

	public void setEmp_ins_ip_add(String emp_ins_ip_add) {
		this.emp_ins_ip_add = emp_ins_ip_add;
	}

	public String getEmp_mod_ip_add() {
		return emp_mod_ip_add;
	}

	public void setEmp_mod_ip_add(String emp_mod_ip_add) {
		this.emp_mod_ip_add = emp_mod_ip_add;
	}

	public String getEmp_status() {
		return emp_status;
	}

	public void setEmp_status(String emp_status) {
		this.emp_status = emp_status;
	}

	public Long getEmp_index() {
		return emp_index;
	}

	public void setEmp_index(Long emp_index) {
		this.emp_index = emp_index;
	}

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getRm_emp_id() {
		return rm_emp_id;
	}

	public void setRm_emp_id(String rm_emp_id) {
		this.rm_emp_id = rm_emp_id;
	}

	public String getDm_emp_id() {
		return dm_emp_id;
	}

	public void setDm_emp_id(String dm_emp_id) {
		this.dm_emp_id = dm_emp_id;
	}

	public String getSm_emp_id() {
		return sm_emp_id;
	}

	public void setSm_emp_id(String sm_emp_id) {
		this.sm_emp_id = sm_emp_id;
	}
	
	


}
