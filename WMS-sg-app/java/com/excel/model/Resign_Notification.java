package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Resign_Notification")
@NamedStoredProcedureQuery(
		name="callDG_DISP_TO_RESIGN_NOTIFI",
		procedureName = "DG_DISP_TO_RESIGN_NOTIFI",
		resultClasses = Resign_Notification.class,
		parameters = {
				@StoredProcedureParameter(name="pNo_of_days", mode = ParameterMode.IN, type = Integer.class),
		}
)
public class Resign_Notification {
	@Id
	@Column(name = "Row")
	private long Row;
	
	@Column(name = "EMPLOYEE_NO")
	private String employee_no;
	
	@Column(name = "DSP_FSTAFF_NAME")
	private String dsp_fstaff_name;
	
	@Column(name = "FS_FSTAFF_NAME")
	private String fs_fstaff_name;
	
	@Column(name = "FSTAFF_DESIG")
	private String fstaff_desig;
	
	@Column(name = "CHALLAN_DATE")
	private String challan_date;
	
	@Column(name = "CHALLAN_NO")
	private String challan_no;
	
	@Column(name = "LEAVING_DATE")
	private String leaving_date;
	
	@Column(name = "NO_OF_DAYS")
	private String no_of_days;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "APPROVAL_STATUS")
	private String approval_status;
	
	@Column(name = "LR_NUMBER")
	private String lr_number;
	
	@Column(name = "LR_DATE")
	private String lr_date;
	
	@Column(name = "TRANSPORTER")
	private String transporter;
	
	@Column(name = "FSTAFF_DSP_NAME")
	private String fstaff_dsp_name;
	
	@Column(name = "TEAM_CODE")
	private String team_code;
	
	@Column(name = "DIVISION")
	private String division;
	
	//Discard GRN
	@Transient private String Doc_type;
	@Transient private Long grn_id;
	@Transient private String Date;
	@Transient private String Doc_number;
	@Transient private String Name;
	@Transient private String Status;
	@Transient private String grn_ins_usr_id;
	@Transient private String creator;
	@Transient private String created_on;
	@Transient private String grn_mod_usr_id;
	@Transient private String last_Approved_by;
	@Transient private String modified_on;
	@Transient private String remarks;
	
	
	
	public String getDoc_type() {
		return Doc_type;
	}

	public void setDoc_type(String doc_type) {
		Doc_type = doc_type;
	}

	public Long getGrn_id() {
		return grn_id;
	}

	public void setGrn_id(Long grn_id) {
		this.grn_id = grn_id;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getDoc_number() {
		return Doc_number;
	}

	public void setDoc_number(String doc_number) {
		Doc_number = doc_number;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getGrn_ins_usr_id() {
		return grn_ins_usr_id;
	}

	public void setGrn_ins_usr_id(String grn_ins_usr_id) {
		this.grn_ins_usr_id = grn_ins_usr_id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getGrn_mod_usr_id() {
		return grn_mod_usr_id;
	}

	public void setGrn_mod_usr_id(String grn_mod_usr_id) {
		this.grn_mod_usr_id = grn_mod_usr_id;
	}

	public String getLast_Approved_by() {
		return last_Approved_by;
	}

	public void setLast_Approved_by(String last_Approved_by) {
		this.last_Approved_by = last_Approved_by;
	}

	public String getModified_on() {
		return modified_on;
	}

	public void setModified_on(String modified_on) {
		this.modified_on = modified_on;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getRow() {
		return Row;
	}

	public String getFstaff_dsp_name() {
		return fstaff_dsp_name;
	}

	public void setFstaff_dsp_name(String fstaff_dsp_name) {
		this.fstaff_dsp_name = fstaff_dsp_name;
	}

	public void setRow(long row) {
		Row = row;
	}

	public String getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}

	public String getDsp_fstaff_name() {
		return dsp_fstaff_name;
	}

	public void setDsp_fstaff_name(String dsp_fstaff_name) {
		this.dsp_fstaff_name = dsp_fstaff_name;
	}

	public String getFs_fstaff_name() {
		return fs_fstaff_name;
	}

	public void setFs_fstaff_name(String fs_fstaff_name) {
		this.fs_fstaff_name = fs_fstaff_name;
	}

	public String getFstaff_desig() {
		return fstaff_desig;
	}

	public void setFstaff_desig(String fstaff_desig) {
		this.fstaff_desig = fstaff_desig;
	}

	public String getChallan_date() {
		return challan_date;
	}

	public void setChallan_date(String challan_date) {
		this.challan_date = challan_date;
	}

	public String getChallan_no() {
		return challan_no;
	}

	public void setChallan_no(String challan_no) {
		this.challan_no = challan_no;
	}

	public String getLeaving_date() {
		return leaving_date;
	}

	public void setLeaving_date(String leaving_date) {
		this.leaving_date = leaving_date;
	}

	public String getNo_of_days() {
		return no_of_days;
	}

	public void setNo_of_days(String no_of_days) {
		this.no_of_days = no_of_days;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproval_status() {
		return approval_status;
	}

	public void setApproval_status(String approval_status) {
		this.approval_status = approval_status;
	}

	public String getLr_number() {
		return lr_number;
	}

	public void setLr_number(String lr_number) {
		this.lr_number = lr_number;
	}

	public String getLr_date() {
		return lr_date;
	}

	public void setLr_date(String lr_date) {
		this.lr_date = lr_date;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}


}
