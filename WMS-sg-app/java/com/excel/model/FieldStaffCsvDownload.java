package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
@Entity
@Table(name = "FieldStaffCsvDownload")
@NamedStoredProcedureQuery(
		name="callDG_FIELDSTAFF_MOBILE_NO_UPDATE",
		procedureName = "DG_FIELDSTAFF_MOBILE_NO_UPDATE",
		resultClasses = FieldStaffCsvDownload.class,
		parameters = {
				@StoredProcedureParameter(name="div_id", mode = ParameterMode.IN, type = String.class),
		}
)
public class FieldStaffCsvDownload {
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "FSTAFF_LEVEL_CODE")
	private String fstaff_level_code;

	@Column(name = "DESIG")
	private String desig;

	@Column(name = "fstaff_id")
	private String fstaff_id;

	@Column(name = "fstaff_employee_no")
	private String fstaff_employee_no;

	@Column(name = "fstaff_name")
	private String fstaff_name;

	@Column(name = "fstaff_display_name")
	private String fstaff_display_name;

	@Column(name = "fstaff_leaving_date")
	private Date fstaff_leaving_date;

	@Column(name = "fstaff_samp_disp_ind")
	private String fstaff_samp_disp_ind;

	@Column(name = "fstaff_terr_id")
	private String fstaff_terr_id;

	@Column(name = "fstaff_terr_code")
	private String fstaff_terr_code;

	@Column(name = "fstaff_mobile")
	private String fstaff_mobile;
	
	@Column(name = "fstaff_ins_dt")
	private Date  fstaff_ins_dt;
	
	@Column(name = "fstaff_mod_dt")
	private Date  fstaff_mod_dt;

	public Date getFstaff_ins_dt() {
		return fstaff_ins_dt;
	}

	public void setFstaff_ins_dt(Date fstaff_ins_dt) {
		this.fstaff_ins_dt = fstaff_ins_dt;
	}

	public Date getFstaff_mod_dt() {
		return fstaff_mod_dt;
	}

	public void setFstaff_mod_dt(Date fstaff_mod_dt) {
		this.fstaff_mod_dt = fstaff_mod_dt;
	}

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

	public String getFstaff_name() {
		return fstaff_name;
	}

	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public Date getFstaff_leaving_date() {
		return fstaff_leaving_date;
	}

	public String getFstaff_samp_disp_ind() {
		return fstaff_samp_disp_ind;
	}

	public String getFstaff_terr_id() {
		return fstaff_terr_id;
	}

	public String getFstaff_terr_code() {
		return fstaff_terr_code;
	}

	public String getFstaff_mobile() {
		return fstaff_mobile;
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

	public void setFstaff_name(String fstaff_name) {
		this.fstaff_name = fstaff_name;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public void setFstaff_leaving_date(Date fstaff_leaving_date) {
		this.fstaff_leaving_date = fstaff_leaving_date;
	}

	public void setFstaff_samp_disp_ind(String fstaff_samp_disp_ind) {
		this.fstaff_samp_disp_ind = fstaff_samp_disp_ind;
	}

	public void setFstaff_terr_id(String fstaff_terr_id) {
		this.fstaff_terr_id = fstaff_terr_id;
	}

	public void setFstaff_terr_code(String fstaff_terr_code) {
		this.fstaff_terr_code = fstaff_terr_code;
	}

	public void setFstaff_mobile(String fstaff_mobile) {
		this.fstaff_mobile = fstaff_mobile;
	}

	
	
	

}
