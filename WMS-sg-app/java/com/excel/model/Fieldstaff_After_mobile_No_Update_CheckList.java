package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "Fieldstaff_After_mobile_No_Update_CheckList")
@NamedStoredProcedureQuery(name = "callDG_FIELDSTAFF_AFTER_MOBILE_NO_UPDATE_CHECKLIST", procedureName = "DG_FIELDSTAFF_AFTER_MOBILE_NO_UPDATE_CHECKLIST", resultClasses = Fieldstaff_After_mobile_No_Update_CheckList.class, parameters = {
		@StoredProcedureParameter(name = "CSV_FL_NAME", mode = ParameterMode.IN, type = String.class), })
public class Fieldstaff_After_mobile_No_Update_CheckList {
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "FSTAFF_LEVEL_CODE")
	private String fstaff_level_code;

	@Column(name = "DESIG")
	private String desig;

	@Column(name = "FSTAFF_ID")
	private String fstaff_id;

	@Column(name = "FSTAFF_EMPLOYEE_NO")
	private String fstaff_employee_no;

	@Column(name = "FSTAFF_TERR_CODE")
	private String fstaff_terr_code;

	@Column(name = "FSTAFF_DISPLAY_NAME")
	private String fstaff_display_name;

	@Column(name = "FSTAFF_MOBILE_CSV")
	private String fstaff_mobile_csv;

	@Column(name = "FSTAFF_MOBILE")
	private String fstaff_mobile;

	@Column(name = "FSTAFF_STATUS")
	private String fstaff_status;

	@Column(name = "FSTAFF_INS_USR_ID")
	private String fstaff_ins_usr_id;

	@Column(name = "USER_NAME")
	private String user_name;

	@Column(name = "FSTAFF_INS_DT")
	private String fstaff_ins_dt;

	@Column(name = "FSTAFF_INS_IP_ADD")
	private String fstaff_ins_ip_add;

	@Column(name = "CSV_FILE_NAME")
	private String csv_file_name;

	public Long getRownum() {
		return rownum;
	}

	public String getDivision() {
		return division;
	}

	public String getFstaff_level_code() {
		return fstaff_level_code;
	}

	public String getDesig() {
		return desig;
	}

	public String getFstaff_id() {
		return fstaff_id;
	}

	public String getFstaff_employee_no() {
		return fstaff_employee_no;
	}

	public String getFstaff_terr_code() {
		return fstaff_terr_code;
	}

	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public String getFstaff_mobile_csv() {
		return fstaff_mobile_csv;
	}

	public String getFstaff_mobile() {
		return fstaff_mobile;
	}

	public String getFstaff_status() {
		return fstaff_status;
	}

	public String getFstaff_ins_usr_id() {
		return fstaff_ins_usr_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getFstaff_ins_dt() {
		return fstaff_ins_dt;
	}

	public String getFstaff_ins_ip_add() {
		return fstaff_ins_ip_add;
	}

	public String getCsv_file_name() {
		return csv_file_name;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void setFstaff_level_code(String fstaff_level_code) {
		this.fstaff_level_code = fstaff_level_code;
	}

	public void setDesig(String desig) {
		this.desig = desig;
	}

	public void setFstaff_id(String fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public void setFstaff_employee_no(String fstaff_employee_no) {
		this.fstaff_employee_no = fstaff_employee_no;
	}

	public void setFstaff_terr_code(String fstaff_terr_code) {
		this.fstaff_terr_code = fstaff_terr_code;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public void setFstaff_mobile_csv(String fstaff_mobile_csv) {
		this.fstaff_mobile_csv = fstaff_mobile_csv;
	}

	public void setFstaff_mobile(String fstaff_mobile) {
		this.fstaff_mobile = fstaff_mobile;
	}

	public void setFstaff_status(String fstaff_status) {
		this.fstaff_status = fstaff_status;
	}

	public void setFstaff_ins_usr_id(String fstaff_ins_usr_id) {
		this.fstaff_ins_usr_id = fstaff_ins_usr_id;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setFstaff_ins_dt(String fstaff_ins_dt) {
		this.fstaff_ins_dt = fstaff_ins_dt;
	}

	public void setFstaff_ins_ip_add(String fstaff_ins_ip_add) {
		this.fstaff_ins_ip_add = fstaff_ins_ip_add;
	}

	public void setCsv_file_name(String csv_file_name) {
		this.csv_file_name = csv_file_name;
	}
	
	

}
