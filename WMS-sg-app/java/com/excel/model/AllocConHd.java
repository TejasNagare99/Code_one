package com.excel.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "ALLOC_CON_HD")
public class AllocConHd {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_CON_ID")
	private Long alloc_con_id;
	
	@Column(name = "ALLOC_CON_DOCNO")
	private String alloc_con_docno;
	
	@Column(name = "ALLOC_CON_DATE")
	private Date alloc_con_date;
	
	@Column(name = "ALLOC_CON_TYPE")
	private String alloc_con_type;
	
	@Column(name = "alloc_type")
	private String alloc_type;
	
	@Column(name = "FIN_YEAR")
	private String fin_year;
	
	@Column(name = "COMPANY")
	private String  company	;
	
	@Column(name = "DIVISION")
	private String division	;
	
	@Column(name = "DIV_ID")
	private Long div_id;
	
	@Column(name = "ALLOC_MONTH")
	private String alloc_month;
	
	@Column(name = "ALLOC_USE_MONTH")
	private String alloc_use_month;
		
	@Column(name = "EFFECTIVE_DATE")
	private Date effective_date;
	
	@Column(name = "ALLOC_CYCLE")
	private Long alloc_cycle;
	
	@Column(name = "USER_ID")
	private String user_id;
	
	@Column(name = "UPD_DATE")
	private Date upd_date;
	
	@Column(name = "UPD_IP_ADD")
	private String upd_ip_add;
	
	@Column(name = "STATUS")
	private char status;
	
	@Column(name = "FILE_UPLOAD")
	private char file_upload;
	
	@Column(name = "FILE_DOWNLOAD")
	private char file_download;
	
	@Column(name = "DISP_ADVICE")
	private String DISP_ADVICE;
	
	@Column(name = "ALLOC_APPR_STATUS")
	private char alloc_appr_status;
	
	@Column(name = "ASSISTANT_APPR_STATUS")
	private char assistant_appr_status;
	
	@OneToMany(mappedBy = "alloc_con_id",cascade = CascadeType.ALL)
	//@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE})
	private Set<AllocConDt> allocConDt;

	public Long getAlloc_con_id() {
		return alloc_con_id;
	}

	public void setAlloc_con_id(Long alloc_con_id) {
		this.alloc_con_id = alloc_con_id;
	}

	public String getAlloc_con_docno() {
		return alloc_con_docno;
	}

	public void setAlloc_con_docno(String alloc_con_docno) {
		this.alloc_con_docno = alloc_con_docno;
	}

	public Date getAlloc_con_date() {
		return alloc_con_date;
	}

	public void setAlloc_con_date(Date alloc_con_date) {
		this.alloc_con_date = alloc_con_date;
	}

	public String getAlloc_con_type() {
		return alloc_con_type;
	}

	public void setAlloc_con_type(String alloc_con_type) {
		this.alloc_con_type = alloc_con_type;
	}

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getAlloc_month() {
		return alloc_month;
	}

	public void setAlloc_month(String alloc_month) {
		this.alloc_month = alloc_month;
	}

	public String getAlloc_use_month() {
		return alloc_use_month;
	}

	public void setAlloc_use_month(String alloc_use_month) {
		this.alloc_use_month = alloc_use_month;
	}

	public Date getEffective_date() {
		return effective_date;
	}

	public void setEffective_date(Date effective_date) {
		this.effective_date = effective_date;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public char getFile_upload() {
		return file_upload;
	}

	public void setFile_upload(char file_upload) {
		this.file_upload = file_upload;
	}

	public char getFile_download() {
		return file_download;
	}

	public void setFile_download(char file_download) {
		this.file_download = file_download;
	}

	public String getDISP_ADVICE() {
		return DISP_ADVICE;
	}

	public void setDISP_ADVICE(String dISP_ADVICE) {
		DISP_ADVICE = dISP_ADVICE;
	}

	public char getAlloc_appr_status() {
		return alloc_appr_status;
	}

	public void setAlloc_appr_status(char alloc_appr_status) {
		this.alloc_appr_status = alloc_appr_status;
	}

	public char getAssistant_appr_status() {
		return assistant_appr_status;
	}

	public void setAssistant_appr_status(char assistant_appr_status) {
		this.assistant_appr_status = assistant_appr_status;
	}

	public Set<AllocConDt> getAllocConDt() {
		return allocConDt;
	}

	public void setAllocConDt(Set<AllocConDt> allocConDt) {
		this.allocConDt = allocConDt;
	}
	public AllocConHd(Long alloc_con_id,String alloc_doc_no,Long divId) {
		this.alloc_con_id=alloc_con_id;
		this.alloc_con_docno=alloc_doc_no;
		this.div_id=divId;
	}
	public AllocConHd(Long alloc_con_id,String alloc_doc_no,Long divId,String division,String alloc_month,Long alloc_cycle) {
		this.alloc_con_id=alloc_con_id;
		this.alloc_con_docno=alloc_doc_no;
		this.div_id=divId;
		this.division=division;
		this.alloc_month=alloc_month;
		this.alloc_cycle=alloc_cycle;
	}
	
	public AllocConHd() {
	}

	public Long getAlloc_cycle() {
		return alloc_cycle;
	}

	public void setAlloc_cycle(Long alloc_cycle) {
		this.alloc_cycle = alloc_cycle;
	}	

}
