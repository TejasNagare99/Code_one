package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "TASKS_MASTER")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class TaskMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TASK_MASTER_ID")
	private Long task_id;

	@Column(name = "TASK_NAME")
	private String task_name;

	@Column(name = "TASK_DESCR")
	private String task_descr;

	@Column(name = "APPROVAL_REQ")
	private String approval_req;

	@Column(name = "COMPANY_CD")
	private String company_cd;

	@Column(name = "COMPLETION_TIME")
	private Integer completion_time;

	@Column(name = "CREATED_BY")
	private String created_by;

	@Column(name = "CREATED_DATE")
	private Date created_date;

	@Column(name = "DIV_ID")
	private Long div_id;

	@Column(name = "ESCALATE_AFTER")
	private String escalate_after;

	@Column(name = "ESCALATE_TO")
	private String escalate_to;

	@Column(name = "ESCALATE_TO_TYPE")
	private String escalate_to_type;

	@Column(name = "LAST_MODIFIED_BY")
	private String last_mod_by;

	@Column(name = "LAST_MODIFIED_DATE")
	private Date last_mod_date;

	@Column(name = "REMINDER_TIME")
	private Integer reminder_time;

	@Column(name = "APPROVAL_TYPE")
	private String approval_type;
	
	@Column(name = "INV_GRP_ID")
	private Long inv_grp_id;
	
	@Column(name = "APPR_LEVEL")
	private Long appr_level;
	
	@Column(name = "VALUE_RANGE_FROM")
	private BigDecimal value_range_from;
	
	@Column(name = "VALUE_RANGE_TO")
	private BigDecimal value_range_to;
	
	@Column(name = "TRAN_TYPE")
	private String tran_type;
	
	@Column(name = "PERCENTAGE_RANGE_FROM")
	private BigDecimal percentage_range_from;
	
	@Column(name = "PERCENTAGE_RANGE_TO")
	private BigDecimal percentage_range_to;
	
	@Column(name = "FS_ID")
	private Long fs_id;
	
	//only transient below this line
	@Transient
	private BigDecimal consider_limit;
	@Transient
	private BigDecimal perc1_to;
	
	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getTask_descr() {
		return task_descr;
	}

	public void setTask_descr(String task_descr) {
		this.task_descr = task_descr;
	}

	public String getApproval_req() {
		return approval_req;
	}

	public void setApproval_req(String approval_req) {
		this.approval_req = approval_req;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public Integer getCompletion_time() {
		return completion_time;
	}

	public void setCompletion_time(Integer completion_time) {
		this.completion_time = completion_time;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getEscalate_after() {
		return escalate_after;
	}

	public void setEscalate_after(String escalate_after) {
		this.escalate_after = escalate_after;
	}

	public String getEscalate_to() {
		return escalate_to;
	}

	public void setEscalate_to(String escalate_to) {
		this.escalate_to = escalate_to;
	}

	public String getEscalate_to_type() {
		return escalate_to_type;
	}

	public void setEscalate_to_type(String escalate_to_type) {
		this.escalate_to_type = escalate_to_type;
	}

	public String getLast_mod_by() {
		return last_mod_by;
	}

	public void setLast_mod_by(String last_mod_by) {
		this.last_mod_by = last_mod_by;
	}

	public Date getLast_mod_date() {
		return last_mod_date;
	}

	public void setLast_mod_date(Date last_mod_date) {
		this.last_mod_date = last_mod_date;
	}

	public Integer getReminder_time() {
		return reminder_time;
	}

	public void setReminder_time(Integer reminder_time) {
		this.reminder_time = reminder_time;
	}

	public Long getAppr_level() {
		return appr_level;
	}

	public void setAppr_level(Long appr_level) {
		this.appr_level = appr_level;
	}

	public String getApproval_type() {
		return approval_type;
	}

	public void setApproval_type(String approval_type) {
		this.approval_type = approval_type;
	}

	public Long getInv_grp_id() {
		return inv_grp_id;
	}

	public void setInv_grp_id(Long inv_grp_id) {
		this.inv_grp_id = inv_grp_id;
	}

	public BigDecimal getValue_range_from() {
		return value_range_from;
	}

	public void setValue_range_from(BigDecimal value_range_from) {
		this.value_range_from = value_range_from;
	}

	public BigDecimal getValue_range_to() {
		return value_range_to;
	}

	public void setValue_range_to(BigDecimal value_range_to) {
		this.value_range_to = value_range_to;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public BigDecimal getPercentage_range_from() {
		return percentage_range_from;
	}

	public void setPercentage_range_from(BigDecimal percentage_range_from) {
		this.percentage_range_from = percentage_range_from;
	}

	public BigDecimal getPercentage_range_to() {
		return percentage_range_to;
	}

	public void setPercentage_range_to(BigDecimal percentage_range_to) {
		this.percentage_range_to = percentage_range_to;
	}

	public BigDecimal getConsider_limit() {
		return consider_limit;
	}

	public void setConsider_limit(BigDecimal consider_limit) {
		this.consider_limit = consider_limit;
	}

	public BigDecimal getPerc1_to() {
		return perc1_to;
	}

	public void setPerc1_to(BigDecimal perc1_to) {
		this.perc1_to = perc1_to;
	}

	public Long getFs_id() {
		return fs_id;
	}

	public void setFs_id(Long fs_id) {
		this.fs_id = fs_id;
	}
	
	
}
