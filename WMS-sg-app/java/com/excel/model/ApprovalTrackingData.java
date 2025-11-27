package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "ApprovalTrackingData")
@NamedStoredProcedureQuery(
		name="callApprovalTracking",
		procedureName = "APPROVAL_TRACKING",
		resultClasses = ApprovalTrackingData.class,
		parameters = {
				@StoredProcedureParameter(name="FINYEAR", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="STARTDT", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="ENDDT", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="LOCID", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="USRID", mode = ParameterMode.IN, type = String.class)
		}
)
public class ApprovalTrackingData {
	
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;
	
	@Column(name = "DOCUMENT_TYPE")
	private String transaction_type;
	
	@Column(name = "DOC_NUMBER")
	private String doc_number;
	
	@Column(name = "MONTH_OF_USE")
	private String month_of_use;
	
	@Column(name = "DIVISION")
	private String division;
	
	@Column(name = "CREATOR")
	private String creator;
	
	@Column(name = "SUBMISSION")
	private String submission;
	
	@Column(name = "APPROVAL_LEVEL")
	private String approval_level;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "USER_NAME")
	private String user_name;
	
	@Column(name = "APPROVED")
	private String approved;
	
	@Column(name = "DATE_OF_APPROVAL")
	private String date_of_approval;
	
	@Column(name = "TRAN_REF_ID")
	private String tran_ref_id;
	
	@Column(name = "TRAN_TYPE_NAME")
	private String tran_type_name;
	
	@Column(name = "REMARKS")
	private String remarks;

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getDoc_number() {
		return doc_number;
	}

	public void setDoc_number(String doc_number) {
		this.doc_number = doc_number;
	}

	public String getMonth_of_use() {
		return month_of_use;
	}

	public void setMonth_of_use(String month_of_use) {
		this.month_of_use = month_of_use;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getSubmission() {
		return submission;
	}

	public void setSubmission(String submission) {
		this.submission = submission;
	}

	public String getApproval_level() {
		return approval_level;
	}

	public void setApproval_level(String approval_level) {
		this.approval_level = approval_level;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getDate_of_approval() {
		return date_of_approval;
	}

	public void setDate_of_approval(String date_of_approval) {
		this.date_of_approval = date_of_approval;
	}

	public String getTran_ref_id() {
		return tran_ref_id;
	}

	public void setTran_ref_id(String tran_ref_id) {
		this.tran_ref_id = tran_ref_id;
	}

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public String getTran_type_name() {
		return tran_type_name;
	}

	public void setTran_type_name(String tran_type_name) {
		this.tran_type_name = tran_type_name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	


}
