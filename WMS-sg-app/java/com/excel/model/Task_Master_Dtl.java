package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "TASK_MASTER_DTL")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Task_Master_Dtl implements Serializable{
	
	private static final long serialVersionUID = -4827605030032920482L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TASK_MASTER_DTL_ID")
	private Long task_master_dtl;
	@Column(name = "TASK_ID")
	private Long task_id;
	@Column(name = "COMPANY_CD")
	private String company_cd;
	@Column(name = "IDENTITYLINKTYPE")
	private String identitylinktype;
	@Column(name = "IDENTITYLINKTYPE_VAL")
	private String identitylinktype_val;
	
	// new columns added for approval delegation by Chetan
	@Column(name = "FROM_USERNAME")
	private String from_username;
	@Column(name = "TO_USERNAME")
	private String to_username;
	@Column(name = "FROM_DATE")
	private Date from_date;
	@Column(name = "TO_DATE")
	private Date to_date;
	@Column(name = "LAST_MOD_DATE")
	private Date last_mod_date;
	@Column(name = "STATUS")
	private String status;

	public Long getTask_master_dtl() {
		return task_master_dtl;
	}

	public void setTask_master_dtl(Long task_master_dtl) {
		this.task_master_dtl = task_master_dtl;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getIdentitylinktype() {
		return identitylinktype;
	}

	public void setIdentitylinktype(String identitylinktype) {
		this.identitylinktype = identitylinktype;
	}

	public String getIdentitylinktype_val() {
		return identitylinktype_val;
	}

	public void setIdentitylinktype_val(String identitylinktype_val) {
		this.identitylinktype_val = identitylinktype_val;
	}

	public Long getTask_id() {
		return task_id;
	}
	
	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

	public String getFrom_username() {
		return from_username;
	}

	public void setFrom_username(String from_username) {
		this.from_username = from_username;
	}

	public String getTo_username() {
		return to_username;
	}

	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}

	public Date getFrom_date() {
		return from_date;
	}

	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	public Date getLast_mod_date() {
		return last_mod_date;
	}

	public void setLast_mod_date(Date last_mod_date) {
		this.last_mod_date = last_mod_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
