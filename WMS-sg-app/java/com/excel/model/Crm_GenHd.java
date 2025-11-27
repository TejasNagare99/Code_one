package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CRM_GEN_HD")
public class Crm_GenHd {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "CRM_GEN_ID")
	private Long crm_gen_id;
	
	@Column(name = "CRM_GEN_DATE")
	private Date crm_gen_date;
	
	@Column(name="CRM_SUB_COMP_CODE")
	private String crm_sub_comp_code;
	
	@Column(name="CRM_CUST_TYPE")
	private String crm_cust_type;
	
	@Column(name="CRM_FINYEAR")
	private String crm_finyear;
	
	@Column(name="CRM_MONTH")
	private String crm_month;
	
	@Column(name="CRM_CYCLE")
	private Long crm_cycle;
	
	@Column(name="USER_ID")
	private String user_id;
	
	@Column(name="UPD_DATE")
	private Date upd_date;
	
	@Column(name="UPD_IP_ADD")
	private String upd_ip_add;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="FILE_UPLOAD")
	private String file_upload;
	
	@Column(name="FILE_DOWNLOAD")
	private String file_download;
	
	@Column(name="CRM_DOC_NO")
	private String crm_doc_no;
	
	@Column(name="CRM_ADVICE")
	private String crm_advice;
	
	@Column(name="CRM_APPR_STATUS")
	private String crm_appr_status;

	public Long getCrm_gen_id() {
		return crm_gen_id;
	}

	public void setCrm_gen_id(Long crm_gen_id) {
		this.crm_gen_id = crm_gen_id;
	}

	public Date getCrm_gen_date() {
		return crm_gen_date;
	}

	public void setCrm_gen_date(Date crm_gen_date) {
		this.crm_gen_date = crm_gen_date;
	}

	public String getCrm_sub_comp_code() {
		return crm_sub_comp_code;
	}

	public void setCrm_sub_comp_code(String crm_sub_comp_code) {
		this.crm_sub_comp_code = crm_sub_comp_code;
	}

	public String getCrm_cust_type() {
		return crm_cust_type;
	}

	public void setCrm_cust_type(String crm_cust_type) {
		this.crm_cust_type = crm_cust_type;
	}

	public String getCrm_finyear() {
		return crm_finyear;
	}

	public void setCrm_finyear(String crm_finyear) {
		this.crm_finyear = crm_finyear;
	}

	public String getCrm_month() {
		return crm_month;
	}

	public void setCrm_month(String crm_month) {
		this.crm_month = crm_month;
	}

	public Long getCrm_cycle() {
		return crm_cycle;
	}

	public void setCrm_cycle(Long crm_cycle) {
		this.crm_cycle = crm_cycle;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Date upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_ip_add() {
		return upd_ip_add;
	}

	public void setUpd_ip_add(String upd_ip_add) {
		this.upd_ip_add = upd_ip_add;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFile_upload() {
		return file_upload;
	}

	public void setFile_upload(String file_upload) {
		this.file_upload = file_upload;
	}

	public String getFile_download() {
		return file_download;
	}

	public void setFile_download(String file_download) {
		this.file_download = file_download;
	}

	public String getCrm_doc_no() {
		return crm_doc_no;
	}

	public void setCrm_doc_no(String crm_doc_no) {
		this.crm_doc_no = crm_doc_no;
	}

	public String getCrm_advice() {
		return crm_advice;
	}

	public void setCrm_advice(String crm_advice) {
		this.crm_advice = crm_advice;
	}

	public String getCrm_appr_status() {
		return crm_appr_status;
	}

	public void setCrm_appr_status(String crm_appr_status) {
		this.crm_appr_status = crm_appr_status;
	}
	
	
}
