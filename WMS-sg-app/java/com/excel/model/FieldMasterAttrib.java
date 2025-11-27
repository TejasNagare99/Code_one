package com.excel.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name="FieldMasterAttrib")
@NamedStoredProcedureQueries({
@NamedStoredProcedureQuery(name = "FieldMaster_Download", procedureName = "DOWNLOAD_FIELDMASTER_AUDITTRAIL",
parameters= {
		@StoredProcedureParameter(name="LEVEL" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="AUDIT" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="FSTAFFTRAINING" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="DIVID" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="STARTDT" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="ENDDT" , mode=ParameterMode.IN,type=String.class),
		},resultClasses=FieldMasterAttrib.class),
@NamedStoredProcedureQuery(name = "FieldMasterResigned_Download", procedureName = "DOWNLOAD_FIELDMASTER_RESIGNED",
parameters= {
		@StoredProcedureParameter(name="LEVEL" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="AUDIT" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="FSTAFFTRAINING" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="DIVID" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="STARTDT" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="ENDDT" , mode=ParameterMode.IN,type=String.class),
		},resultClasses=FieldMasterAttrib.class)
})
public class FieldMasterAttrib implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name="Row")
	private Long row;
	
	@Column(name="FSTAFF_ID")
	private Long fstaff_id;
	
	@Column(name="FSTAFF_DIV_ID")
	private Long fstaff_div_id;
	
	@Column(name="DIVISION")
	private String division;
	
	@Column(name="FSTAFF_LEVEL_CODE")
	private String fstaff_level_code;
	
	@Column(name="FSTAFF_NAME")
	private String fstaff_name;
	
	@Column(name="FSTAFF_MAP_CODE1")
	private String fstaff_map_code1;
	
	@Column(name="HQ_NAME")
	private String hq_name;
	
	@Column(name="RM")
	private String rm;
	
	@Column(name="AM")
	private String am;
	
	@Column(name="FSTAFF_ADDR1")
	private String fstaff_addr1;
	
	@Column(name="FSTAFF_ADDR2")
	private String fstaff_addr2;
	
	@Column(name="FSTAFF_ADDR3")
	private String fstaff_addr3;
	
	@Column(name="FSTAFF_ADDR4")
	private String fstaff_addr4;
	
	@Column(name="FSTAFF_MOBILE")
	private String fstaff_mobile;
	
	@Column(name="FSTAFF_DESTINATION")
	private String fstaff_destination;
	
	@Column(name="FSTAFF_MAP_CODE2")
	private String fstaff_map_code2;
	
	@Column(name="FSTAFF_DISPLAY_NAME")
	private String fstaff_display_name;
	
	@Column(name="FSTAFF_DESIG")
	private String fstaff_desig;
	
	@Column(name="FSTAFF_ZONENAME")
	private String fstaff_zonename;
	
	@Column(name="FSTAFF_EMPLOYEE_NO")
	private String fstaff_employee_no;
	
	@Column(name="FSTAFF_STATUS")
	private String fstaff_status;
	
	@Column(name="FSTAFF_ins_usr_id")
	private String fstaff_ins_usr_id;
	
	@Column(name="INS_USER_NAME")
	private String ins_user_name;
	
	@Column(name="FSTAFF_INS_dt")
	private String fstaff_ins_dt;
	
	@Column(name="FSTAFF_MOD_usr_id")
	private String fstaff_mod_usr_id;
	
	@Column(name="MOD_USER_NAME")
	private String mod_user_name;
	
	@Column(name="FSTAFF_MOD_dt")
	private String fstaff_mod_dt;
	
	@Column(name="CURR_STATUS")
	private String curr_status;
	
	@Column(name="IND")
	private String ind;
	
	@Column(name="FSTAFF_INS_ip_add")
	private String fstaff_ins_ip_add;
	
	@Column(name="FSTAFF_mod_ip_add")
	private String fstaff_mod_ip_add;
	
	
	@Column(name="REMARKS")
	private String remarks;
	
	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public Long getFstaff_div_id() {
		return fstaff_div_id;
	}

	public void setFstaff_div_id(Long fstaff_div_id) {
		this.fstaff_div_id = fstaff_div_id;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getFstaff_level_code() {
		return fstaff_level_code;
	}

	public void setFstaff_level_code(String fstaff_level_code) {
		this.fstaff_level_code = fstaff_level_code;
	}

	public String getFstaff_name() {
		return fstaff_name;
	}

	public void setFstaff_name(String fstaff_name) {
		this.fstaff_name = fstaff_name;
	}

	public String getFstaff_map_code1() {
		return fstaff_map_code1;
	}

	public void setFstaff_map_code1(String fstaff_map_code1) {
		this.fstaff_map_code1 = fstaff_map_code1;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	public String getFstaff_addr1() {
		return fstaff_addr1;
	}

	public void setFstaff_addr1(String fstaff_addr1) {
		this.fstaff_addr1 = fstaff_addr1;
	}

	public String getFstaff_addr2() {
		return fstaff_addr2;
	}

	public void setFstaff_addr2(String fstaff_addr2) {
		this.fstaff_addr2 = fstaff_addr2;
	}

	public String getFstaff_addr3() {
		return fstaff_addr3;
	}

	public void setFstaff_addr3(String fstaff_addr3) {
		this.fstaff_addr3 = fstaff_addr3;
	}

	public String getFstaff_addr4() {
		return fstaff_addr4;
	}

	public void setFstaff_addr4(String fstaff_addr4) {
		this.fstaff_addr4 = fstaff_addr4;
	}

	public String getFstaff_mobile() {
		return fstaff_mobile;
	}

	public void setFstaff_mobile(String fstaff_mobile) {
		this.fstaff_mobile = fstaff_mobile;
	}

	public String getFstaff_destination() {
		return fstaff_destination;
	}

	public void setFstaff_destination(String fstaff_destination) {
		this.fstaff_destination = fstaff_destination;
	}

	public String getFstaff_map_code2() {
		return fstaff_map_code2;
	}

	public void setFstaff_map_code2(String fstaff_map_code2) {
		this.fstaff_map_code2 = fstaff_map_code2;
	}

	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public String getFstaff_desig() {
		return fstaff_desig;
	}

	public void setFstaff_desig(String fstaff_desig) {
		this.fstaff_desig = fstaff_desig;
	}

	public String getFstaff_zonename() {
		return fstaff_zonename;
	}

	public void setFstaff_zonename(String fstaff_zonename) {
		this.fstaff_zonename = fstaff_zonename;
	}

	public String getFstaff_employee_no() {
		return fstaff_employee_no;
	}

	public void setFstaff_employee_no(String fstaff_employee_no) {
		this.fstaff_employee_no = fstaff_employee_no;
	}

	public String getFstaff_status() {
		return fstaff_status;
	}

	public void setFstaff_status(String fstaff_status) {
		this.fstaff_status = fstaff_status;
	}

	public String getFstaff_ins_usr_id() {
		return fstaff_ins_usr_id;
	}

	public void setFstaff_ins_usr_id(String fstaff_ins_usr_id) {
		this.fstaff_ins_usr_id = fstaff_ins_usr_id;
	}

	public String getIns_user_name() {
		return ins_user_name;
	}

	public void setIns_user_name(String ins_user_name) {
		this.ins_user_name = ins_user_name;
	}

	public String getFstaff_ins_dt() {
		return fstaff_ins_dt;
	}

	public void setFstaff_ins_dt(String fstaff_ins_dt) {
		this.fstaff_ins_dt = fstaff_ins_dt;
	}

	public String getFstaff_mod_usr_id() {
		return fstaff_mod_usr_id;
	}

	public void setFstaff_mod_usr_id(String fstaff_mod_usr_id) {
		this.fstaff_mod_usr_id = fstaff_mod_usr_id;
	}

	public String getMod_user_name() {
		return mod_user_name;
	}

	public void setMod_user_name(String mod_user_name) {
		this.mod_user_name = mod_user_name;
	}

	public String getFstaff_mod_dt() {
		return fstaff_mod_dt;
	}

	public void setFstaff_mod_dt(String fstaff_mod_dt) {
		this.fstaff_mod_dt = fstaff_mod_dt;
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

	public String getFstaff_ins_ip_add() {
		return fstaff_ins_ip_add;
	}

	public void setFstaff_ins_ip_add(String fstaff_ins_ip_add) {
		this.fstaff_ins_ip_add = fstaff_ins_ip_add;
	}

	public String getFstaff_mod_ip_add() {
		return fstaff_mod_ip_add;
	}

	public void setFstaff_mod_ip_add(String fstaff_mod_ip_add) {
		this.fstaff_mod_ip_add = fstaff_mod_ip_add;
	}


	
}
