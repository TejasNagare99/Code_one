package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appr_mail_vali")
public class ApprovalMailValidation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	@Column(name = "TranRefId")
	private Long tranrefid;
	@Column(name = "TranType")
	private String trantype;
	@Column(name = "ProcessId")
	private Long processid;
	@Column(name = "EmailId_of_Approver")
	private String emailid_of_approver;
	@Column(name = "Approver_User_id")
	private String approver_user_id;
	@Column(name = "Status")
	private String status;
	@Column(name = "Approval_DATETIME")
	private Date approval_datetime;
	@Column(name = "AMV_ins_usr_id")
	private String amv_ins_usr_id;
	@Column(name = "AMV_mod_usr_id")
	private String amv_mod_usr_id;
	@Column(name = "AMV_ins_dt")
	private Date amv_ins_dt;
	@Column(name = "AMV_mod_dt")
	private Date amv_mod_dt;
	@Column(name = "AMV_ins_ip_add")
	private String amv_ins_ip_add;
	@Column(name = "AMV_mod_ip_add")
	private String amv_mod_ip_add;
	@Column(name = "AMV_status")
	private String amv_status;
	@Column(name = "task_id")
	private Long task_id;
	
	
	
	public Long getTask_id() {
		return task_id;
	}
	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTranrefid() {
		return tranrefid;
	}
	public void setTranrefid(Long tranrefid) {
		this.tranrefid = tranrefid;
	}
	public String getTrantype() {
		return trantype;
	}
	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}
	public Long getProcessid() {
		return processid;
	}
	public void setProcessid(Long processid) {
		this.processid = processid;
	}
	public String getEmailid_of_approver() {
		return emailid_of_approver;
	}
	public void setEmailid_of_approver(String emailid_of_approver) {
		this.emailid_of_approver = emailid_of_approver;
	}
	public String getApprover_user_id() {
		return approver_user_id;
	}
	public void setApprover_user_id(String approver_user_id) {
		this.approver_user_id = approver_user_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getApproval_datetime() {
		return approval_datetime;
	}
	public void setApproval_datetime(Date approval_datetime) {
		this.approval_datetime = approval_datetime;
	}
	public String getAmv_ins_usr_id() {
		return amv_ins_usr_id;
	}
	public void setAmv_ins_usr_id(String amv_ins_usr_id) {
		this.amv_ins_usr_id = amv_ins_usr_id;
	}
	public String getAmv_mod_usr_id() {
		return amv_mod_usr_id;
	}
	public void setAmv_mod_usr_id(String amv_mod_usr_id) {
		this.amv_mod_usr_id = amv_mod_usr_id;
	}
	public Date getAmv_ins_dt() {
		return amv_ins_dt;
	}
	public void setAmv_ins_dt(Date amv_ins_dt) {
		this.amv_ins_dt = amv_ins_dt;
	}
	public Date getAmv_mod_dt() {
		return amv_mod_dt;
	}
	public void setAmv_mod_dt(Date amv_mod_dt) {
		this.amv_mod_dt = amv_mod_dt;
	}
	public String getAmv_ins_ip_add() {
		return amv_ins_ip_add;
	}
	public void setAmv_ins_ip_add(String amv_ins_ip_add) {
		this.amv_ins_ip_add = amv_ins_ip_add;
	}
	public String getAmv_mod_ip_add() {
		return amv_mod_ip_add;
	}
	public void setAmv_mod_ip_add(String amv_mod_ip_add) {
		this.amv_mod_ip_add = amv_mod_ip_add;
	}
	public String getAmv_status() {
		return amv_status;
	}
	public void setAmv_status(String amv_status) {
		this.amv_status = amv_status;
	}
	
	
}
