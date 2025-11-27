package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACTIVITI_FINALAPPR_LOG")
public class Activiti_FinalAppr_log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "APPROVER")
	private String approver;
	
	@Column(name = "PROCESS_ID")
	private String process_id;
	
	@Column(name = "IP_ADDR")
	private String ip_addr;
	
	@Column(name = "INS_DT")
	private Date ins_dt;

	@Column(name = "ACFIL_ins_usr_id")
	private String acfil_ins_usr_id;
	
	@Column(name = "ACFIL_mod_usr_id")
	private String acfil_mod_usr_id;
	
	@Column(name = "ACFIL_ins_dt")
	private Date acfil_ins_dt;

	@Column(name = "ACFIL_mod_dt")
	private Date acfil_mod_dt;
	
	@Column(name = "ACFIL_ins_ip_add")
	private String acfil_ins_ip_add;
	
	@Column(name = "ACFIL_mod_ip_add")
	private String acfil_mod_ip_add;
	
	@Column(name = "ACFIL_TRAN_REF_ID")
	private Long acfil_tran_ref_id;
	
	public Long getAcfil_tran_ref_id() {
		return acfil_tran_ref_id;
	}

	public void setAcfil_tran_ref_id(Long acfil_tran_ref_id) {
		this.acfil_tran_ref_id = acfil_tran_ref_id;
	}

	public String getAcfil_ins_usr_id() {
		return acfil_ins_usr_id;
	}

	public void setAcfil_ins_usr_id(String acfil_ins_usr_id) {
		this.acfil_ins_usr_id = acfil_ins_usr_id;
	}

	public String getAcfil_mod_usr_id() {
		return acfil_mod_usr_id;
	}

	public void setAcfil_mod_usr_id(String acfil_mod_usr_id) {
		this.acfil_mod_usr_id = acfil_mod_usr_id;
	}

	public Date getAcfil_ins_dt() {
		return acfil_ins_dt;
	}

	public void setAcfil_ins_dt(Date acfil_ins_dt) {
		this.acfil_ins_dt = acfil_ins_dt;
	}

	public Date getAcfil_mod_dt() {
		return acfil_mod_dt;
	}

	public void setAcfil_mod_dt(Date acfil_mod_dt) {
		this.acfil_mod_dt = acfil_mod_dt;
	}

	public String getAcfil_ins_ip_add() {
		return acfil_ins_ip_add;
	}

	public void setAcfil_ins_ip_add(String acfil_ins_ip_add) {
		this.acfil_ins_ip_add = acfil_ins_ip_add;
	}

	public String getAcfil_mod_ip_add() {
		return acfil_mod_ip_add;
	}

	public void setAcfil_mod_ip_add(String acfil_mod_ip_add) {
		this.acfil_mod_ip_add = acfil_mod_ip_add;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getProcess_id() {
		return process_id;
	}

	public void setProcess_id(String process_id) {
		this.process_id = process_id;
	}

	public String getIp_addr() {
		return ip_addr;
	}

	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}

	public Date getIns_dt() {
		return ins_dt;
	}

	public void setIns_dt(Date ins_dt) {
		this.ins_dt = ins_dt;
	}
	
	

}
