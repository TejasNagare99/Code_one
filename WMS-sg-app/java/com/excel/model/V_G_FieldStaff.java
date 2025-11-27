package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class V_G_FieldStaff {
	@Id
	@Column(name = "FSTAFF_ID")
	private Long fstaff_id;

	@Column(name = "FSTAFF_CODE_DISP")
	private String fstaff_code_disp;

	@Column(name = "FSTAFF_NAME")
	private String fstaff_name;

	@Column(name = "FSTAFF_DISPLAY_NAME")
	private String fstaff_display_name;

	@Column(name = "DIV_DISP_NM")
	private String div_disp_nm;

	@Column(name = "DPTLOC_NAME")
	private String dptloc_name;

	@Column(name = "FSTAFF_LEVEL_CODE")
	private String fstaff_level_code;

	@Column(name = "FSTAFF_LEAVING_DATE")
	private Date fstaff_leaving_date;

	@Column(name = "NonCoreCore")
	private String noncorecore;
	
	@Column(name = "FSTAFF_status")
	private String fstaff_status;
	
	@Column(name = "FSTAFF_MGR1_ID")
	private Long fstaff_mgr1_id;

	@Column(name = "MGR1_NAME")
	private String mgr1_name;
	
	@Column(name = "FSTAFF_MGR2_ID")
	private Long fstaff_mgr2_id;

	@Column(name = "MGR2_NAME")
	private String mgr2_name;
	
	@Column(name = "FSTAFF_MGR3_ID")
	private Long fstaff_mgr3_id;

	@Column(name = "MGR3_NAME")
	private String mgr3_name;
	
	@Column(name = "FSTAFF_MGR4_ID")
	private Long fstaff_mgr4_id;

	@Column(name = "MGR4_NAME")
	private String mgr4_name;
	
	@Column(name = "FSTAFF_MGR5_ID")
	private Long fstaff_mgr5_id;

	@Column(name = "MGR5_NAME")
	private String mgr5_name;
	
	@Column(name = "FSTAFF_MGR6_ID")
	private Long fstaff_mgr6_id;

	@Column(name = "MGR6_NAME")
	private String mgr6_name;
	
	@Column(name = "FSTAFF_EMPLOYEE_NO")
	private String fstaff_employee_no;

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getFstaff_code_disp() {
		return fstaff_code_disp;
	}

	public void setFstaff_code_disp(String fstaff_code_disp) {
		this.fstaff_code_disp = fstaff_code_disp;
	}

	public String getFstaff_name() {
		return fstaff_name;
	}

	public void setFstaff_name(String fstaff_name) {
		this.fstaff_name = fstaff_name;
	}

	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getDptloc_name() {
		return dptloc_name;
	}

	public void setDptloc_name(String dptloc_name) {
		this.dptloc_name = dptloc_name;
	}

	public String getFstaff_level_code() {
		return fstaff_level_code;
	}

	public void setFstaff_level_code(String fstaff_level_code) {
		this.fstaff_level_code = fstaff_level_code;
	}

	public Date getFstaff_leaving_date() {
		return fstaff_leaving_date;
	}

	public void setFstaff_leaving_date(Date fstaff_leaving_date) {
		this.fstaff_leaving_date = fstaff_leaving_date;
	}

	public String getNoncorecore() {
		return noncorecore;
	}

	public void setNoncorecore(String noncorecore) {
		this.noncorecore = noncorecore;
	}

	public String getFstaff_status() {
		return fstaff_status;
	}

	public void setFstaff_status(String fstaff_status) {
		this.fstaff_status = fstaff_status;
	}

	public Long getFstaff_mgr1_id() {
		return fstaff_mgr1_id;
	}

	public void setFstaff_mgr1_id(Long fstaff_mgr1_id) {
		this.fstaff_mgr1_id = fstaff_mgr1_id;
	}

	public String getMgr1_name() {
		return mgr1_name;
	}

	public void setMgr1_name(String mgr1_name) {
		this.mgr1_name = mgr1_name;
	}

	public Long getFstaff_mgr2_id() {
		return fstaff_mgr2_id;
	}

	public void setFstaff_mgr2_id(Long fstaff_mgr2_id) {
		this.fstaff_mgr2_id = fstaff_mgr2_id;
	}

	public String getMgr2_name() {
		return mgr2_name;
	}

	public void setMgr2_name(String mgr2_name) {
		this.mgr2_name = mgr2_name;
	}

	public Long getFstaff_mgr3_id() {
		return fstaff_mgr3_id;
	}

	public void setFstaff_mgr3_id(Long fstaff_mgr3_id) {
		this.fstaff_mgr3_id = fstaff_mgr3_id;
	}

	public String getMgr3_name() {
		return mgr3_name;
	}

	public void setMgr3_name(String mgr3_name) {
		this.mgr3_name = mgr3_name;
	}

	public Long getFstaff_mgr4_id() {
		return fstaff_mgr4_id;
	}

	public void setFstaff_mgr4_id(Long fstaff_mgr4_id) {
		this.fstaff_mgr4_id = fstaff_mgr4_id;
	}

	public String getMgr4_name() {
		return mgr4_name;
	}

	public void setMgr4_name(String mgr4_name) {
		this.mgr4_name = mgr4_name;
	}

	public Long getFstaff_mgr5_id() {
		return fstaff_mgr5_id;
	}

	public void setFstaff_mgr5_id(Long fstaff_mgr5_id) {
		this.fstaff_mgr5_id = fstaff_mgr5_id;
	}

	public String getMgr5_name() {
		return mgr5_name;
	}

	public void setMgr5_name(String mgr5_name) {
		this.mgr5_name = mgr5_name;
	}

	public Long getFstaff_mgr6_id() {
		return fstaff_mgr6_id;
	}

	public void setFstaff_mgr6_id(Long fstaff_mgr6_id) {
		this.fstaff_mgr6_id = fstaff_mgr6_id;
	}

	public String getMgr6_name() {
		return mgr6_name;
	}

	public void setMgr6_name(String mgr6_name) {
		this.mgr6_name = mgr6_name;
	}

	public String getFstaff_employee_no() {
		return fstaff_employee_no;
	}

	public void setFstaff_employee_no(String fstaff_employee_no) {
		this.fstaff_employee_no = fstaff_employee_no;
	}
	
	
}
