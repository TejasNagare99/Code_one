package com.excel.bean;

public class MailBean {
	
	private String emp_email;
	private String act_taskid;
	private String assignee_;
	private String assignee_name;
	private String started_by_name;
	private String started_by_id;
	private String status;
	private String completed_by;
	private String link;
	private String approvedByName;
	private String approvedBydesignation;	
	private String remarks;
	private String descision;
	//added for new mail approval tracking on 29NOV
			private String emp_id;
	public String getEmp_email() {
		return emp_email;
	}
	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}
	public String getAct_taskid() {
		return act_taskid;
	}
	public void setAct_taskid(String act_taskid) {
		this.act_taskid = act_taskid;
	}
	public String getAssignee_() {
		return assignee_;
	}
	public void setAssignee_(String assignee_) {
		this.assignee_ = assignee_;
	}
	public String getStarted_by_name() {
		return started_by_name;
	}
	public void setStarted_by_name(String started_by_name) {
		this.started_by_name = started_by_name;
	}
	public String getStarted_by_id() {
		return started_by_id;
	}
	public void setStarted_by_id(String started_by_id) {
		this.started_by_id = started_by_id;
	}
	public String getAssignee_name() {
		return assignee_name;
	}
	public void setAssignee_name(String assignee_name) {
		this.assignee_name = assignee_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompleted_by() {
		return completed_by;
	}
	public void setCompleted_by(String completed_by) {
		this.completed_by = completed_by;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getApprovedByName() {
		return approvedByName;
	}
	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}
	public String getApprovedBydesignation() {
		return approvedBydesignation;
	}
	public void setApprovedBydesignation(String approvedBydesignation) {
		this.approvedBydesignation = approvedBydesignation;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDescision() {
		return descision;
	}
	public void setDescision(String descision) {
		this.descision = descision;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	

}
