package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ALLOC_TEMP_HEADER")
public class Alloc_Temp_Header implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_TEMP_HD_ID")
	private Long alloc_temp_hd_id;
	
	@Column(name = "FIN_YEAR")
	private long fin_year;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "PERIOD_CODE")
	private String period_code;
	
	@Column(name = "DIV_ID")
	private Long div_id;
	
	@Column(name = "ALLOC_TYPE")
	private char alloc_type;
	
	@Column(name = "ALLOC_CYCLE")
	private int cycle;
	
	@Column(name = "UPLOAD_TYPE")
	private String upload_type;
	
	@Column(name = "APPROVAL_STATUS")
	private char approval_status;
	
	@Column(name = "UPLOAD_CYCLE")
	private Integer upload_cycle;
	
	@Column(name = "Alloc_xgen_hd_id")
	private Long alloc_xgenheader_id;
	
	@Column(name = "Alloc_gen_hd_id")
	private Long alloc_header_id;
	
	@Column(name = "csv_file_name")
	private String csv_file_name;
	
	@Column(name = "ins_usr_id")
	private String ins_usr_id;
	
	@Column(name = "ins_ip_add")
	private String ins_ip_add;
	
	@Column(name = "App_acquired")
	private int app_acquired;
	
	@Column(name = "App_required")
	private int app_required;
	
	@Column(name = "APPROVAL_REMARK")
	private String approval_remark;
	
	@Column(name = "input_type")
	private int input_type;
	
	@Column(name = "TEAM_CODE")
	private String team_code;
	
	@Column(name = "ins_dt")
	private Date ins_dt;
	@Column(name = "SRT_NUMBER")
	private String srt_number;

	@Column(name = "SRT_DATE")
	private Date srt_date;
	
	@Column(name = "SRT_REMARK")
	private String srt_remark;
	
	
	public String getSrt_remark() {
		return srt_remark;
	}

	public void setSrt_remark(String srt_remark) {
		this.srt_remark = srt_remark;
	}

	public String getSrt_number() {
		return srt_number;
	}

	public void setSrt_number(String srt_number) {
		this.srt_number = srt_number;
	}

	public Date getSrt_date() {
		return srt_date;
	}

	public void setSrt_date(Date srt_date) {
		this.srt_date = srt_date;
	}

	public Date getIns_dt() {
		return ins_dt;
	}

	public void setIns_dt(Date ins_dt) {
		this.ins_dt = ins_dt;
	}

	public Long getAlloc_temp_hd_id() {
		return alloc_temp_hd_id;
	}

	public void setAlloc_temp_hd_id(Long alloc_temp_hd_id) {
		this.alloc_temp_hd_id = alloc_temp_hd_id;
	}

	public long getFin_year() {
		return fin_year;
	}

	public void setFin_year(long fin_year) {
		this.fin_year = fin_year;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public char getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(char alloc_type) {
		this.alloc_type = alloc_type;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public String getUpload_type() {
		return upload_type;
	}

	public void setUpload_type(String upload_type) {
		this.upload_type = upload_type;
	}

	public char getApproval_status() {
		return approval_status;
	}

	public void setApproval_status(char approval_status) {
		this.approval_status = approval_status;
	}

	public Integer getUpload_cycle() {
		return upload_cycle;
	}

	public void setUpload_cycle(Integer upload_cycle) {
		this.upload_cycle = upload_cycle;
	}

	public Long getAlloc_xgenheader_id() {
		return alloc_xgenheader_id;
	}

	public void setAlloc_xgenheader_id(Long alloc_xgenheader_id) {
		this.alloc_xgenheader_id = alloc_xgenheader_id;
	}

	public Long getAlloc_header_id() {
		return alloc_header_id;
	}

	public void setAlloc_header_id(Long alloc_header_id) {
		this.alloc_header_id = alloc_header_id;
	}

	public String getCsv_file_name() {
		return csv_file_name;
	}

	public void setCsv_file_name(String csv_file_name) {
		this.csv_file_name = csv_file_name;
	}

	public String getIns_usr_id() {
		return ins_usr_id;
	}

	public void setIns_usr_id(String ins_usr_id) {
		this.ins_usr_id = ins_usr_id;
	}

	public String getIns_ip_add() {
		return ins_ip_add;
	}

	public void setIns_ip_add(String ins_ip_add) {
		this.ins_ip_add = ins_ip_add;
	}

	public int getApp_acquired() {
		return app_acquired;
	}

	public void setApp_acquired(int app_acquired) {
		this.app_acquired = app_acquired;
	}

	public int getApp_required() {
		return app_required;
	}

	public void setApp_required(int app_required) {
		this.app_required = app_required;
	}

	public String getApproval_remark() {
		return approval_remark;
	}

	public void setApproval_remark(String approval_remark) {
		this.approval_remark = approval_remark;
	}

	public int getInput_type() {
		return input_type;
	}

	public void setInput_type(int input_type) {
		this.input_type = input_type;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}
	
	
	public Alloc_Temp_Header(String csv_file_name,Long alloc_header_id,Long alloc_temp_hd_id,char alloc_type) {
		this.csv_file_name=csv_file_name;
		this.alloc_header_id=alloc_header_id;
		this.alloc_temp_hd_id=alloc_temp_hd_id;
		this.alloc_type=alloc_type;
	}
	
	public Alloc_Temp_Header() {
		
	}
	

}
