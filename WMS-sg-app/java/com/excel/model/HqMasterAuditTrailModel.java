package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "HqMasterAuditTrailModel")
@NamedStoredProcedureQuery(name = "HqMasterAuditTrail",
procedureName = "DOWNLOAD_HQMASTER_AUDITTRAIL",
parameters= {
		@StoredProcedureParameter(name = "DIVID" ,mode = ParameterMode.IN,type=String.class),
		
},resultClasses=HqMasterAuditTrailModel.class)

public class HqMasterAuditTrailModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROW")
	private Long row;

	@Column(name = "HQ_ID")
	private Long hq_id;

	@Column(name = "HQ_CODE")
	private String hq_code;

	@Column(name = "HQ_NAME")
	private String hq_name;

	@Column(name = "HQ_DIV_ID")
	private String hq_div_id;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "HQ_STATE_ID")
	private String hq_state_id;

	@Column(name = "STATE")
	private String state;

	@Column(name = "HQ_POOL_IND")
	private String hq_pool_ind;

	@Column(name = "HQ_NO_OF_REPS")
	private String hq_no_of_reps;

	@Column(name = "HQ_PMP_PERF")
	private String hq_pmp_perf;

	@Column(name = "HQ_MAP_CODE")
	private String hq_map_code;

	@Column(name = "HQ_ins_usr_id")
	private String hq_ins_usr_id;

	@Column(name = "INS_USER_NAME")
	private String ins_user_name;

	@Column(name = "INSERT_DATE")
	private String insert_date;

	@Column(name = "HQ_ins_ip_add")
	private String hq_ins_ip_add;

	@Column(name = "MOD_USER_NAME")
	private String mod_user_name;

	@Column(name = "MODIFY_DATE")
	private String modify_date;

	@Column(name = "HQ_mod_ip_add")
	private String hq_mod_ip_add;

	@Column(name = "HQ_status")
	private String hq_status;

	@Column(name = "CURR_STATUS")
	private String curr_status;

	@Column(name = "IND")
	private String ind;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getHq_id() {
		return hq_id;
	}

	public void setHq_id(Long hq_id) {
		this.hq_id = hq_id;
	}

	public String getHq_code() {
		return hq_code;
	}

	public void setHq_code(String hq_code) {
		this.hq_code = hq_code;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}

	public String getHq_div_id() {
		return hq_div_id;
	}

	public void setHq_div_id(String hq_div_id) {
		this.hq_div_id = hq_div_id;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getHq_state_id() {
		return hq_state_id;
	}

	public void setHq_state_id(String hq_state_id) {
		this.hq_state_id = hq_state_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getHq_pool_ind() {
		return hq_pool_ind;
	}

	public void setHq_pool_ind(String hq_pool_ind) {
		this.hq_pool_ind = hq_pool_ind;
	}

	public String getHq_no_of_reps() {
		return hq_no_of_reps;
	}

	public void setHq_no_of_reps(String hq_no_of_reps) {
		this.hq_no_of_reps = hq_no_of_reps;
	}

	public String getHq_pmp_perf() {
		return hq_pmp_perf;
	}

	public void setHq_pmp_perf(String hq_pmp_perf) {
		this.hq_pmp_perf = hq_pmp_perf;
	}

	public String getHq_map_code() {
		return hq_map_code;
	}

	public void setHq_map_code(String hq_map_code) {
		this.hq_map_code = hq_map_code;
	}

	public String getHq_ins_usr_id() {
		return hq_ins_usr_id;
	}

	public void setHq_ins_usr_id(String hq_ins_usr_id) {
		this.hq_ins_usr_id = hq_ins_usr_id;
	}

	public String getIns_user_name() {
		return ins_user_name;
	}

	public void setIns_user_name(String ins_user_name) {
		this.ins_user_name = ins_user_name;
	}

	public String getInsert_date() {
		return insert_date;
	}

	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}

	public String getHq_ins_ip_add() {
		return hq_ins_ip_add;
	}

	public void setHq_ins_ip_add(String hq_ins_ip_add) {
		this.hq_ins_ip_add = hq_ins_ip_add;
	}

	public String getMod_user_name() {
		return mod_user_name;
	}

	public void setMod_user_name(String mod_user_name) {
		this.mod_user_name = mod_user_name;
	}

	public String getModify_date() {
		return modify_date;
	}

	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}

	public String getHq_mod_ip_add() {
		return hq_mod_ip_add;
	}

	public void setHq_mod_ip_add(String hq_mod_ip_add) {
		this.hq_mod_ip_add = hq_mod_ip_add;
	}

	public String getHq_status() {
		return hq_status;
	}

	public void setHq_status(String hq_status) {
		this.hq_status = hq_status;
	}

	public String getCurr_status() {
		return curr_status;
	}

	public void setCurr_status(String curr_status) {
		this.curr_status = curr_status;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}
	
	

}
