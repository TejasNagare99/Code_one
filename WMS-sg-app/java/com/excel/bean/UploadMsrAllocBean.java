package com.excel.bean;

import java.util.List;

public class UploadMsrAllocBean {

	private Long year;
	private Long fstaff_id;
	private Long division;
	private String division_cd;
	private String period;
	private String fs_code;
	private String fs_name;
	private String company;
	private String created_by;
	private String created_by_ip;
	private char upload_status;
	private int cycle;
	private int upload_cycle;
	private int alloc_header_id;
	private int alloc_xgen_header_id;
	private String fileUploadFileName;
	private int level;
	private int input_type;
	private String team_code;
	private String srtno;
	private String srtdate;
	private String srtremark;
	
	
	public String getSrtremark() {
		return srtremark;
	}
	public void setSrtremark(String srtremark) {
		this.srtremark = srtremark;
	}
	public String getSrtno() {
		return srtno;
	}
	public void setSrtno(String srtno) {
		this.srtno = srtno;
	}
	public String getSrtdate() {
		return srtdate;
	}
	public void setSrtdate(String srtdate) {
		this.srtdate = srtdate;
	}
	private List<Prod_MasterBean> product_Masters;
	
	public long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	public Long getDivision() {
		return division;
	}
	public void setDivision(Long division) {
		this.division = division;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getFs_code() {
		return fs_code;
	}
	public void setFs_code(String fs_code) {
		this.fs_code = fs_code;
	}
	public String getFs_name() {
		return fs_name;
	}
	public void setFs_name(String fs_name) {
		this.fs_name = fs_name;
	}
	public List<Prod_MasterBean> getProduct_Masters() {
		return product_Masters;
	}
	public void setProduct_Masters(List<Prod_MasterBean> product_Masters) {
		this.product_Masters = product_Masters;
	}
	public void setFstaff_id(long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}
	public long getFstaff_id() {
		return fstaff_id;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompany() {
		return company;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by_ip(String created_by_ip) {
		this.created_by_ip = created_by_ip;
	}
	public String getCreated_by_ip() {
		return created_by_ip;
	}
	public void setDivision_cd(String division_cd) {
		this.division_cd = division_cd;
	}
	public String getDivision_cd() {
		return division_cd;
	}
	public char getUpload_status() {
		return upload_status;
	}
	public void setUpload_status(char upload_status) {
		this.upload_status = upload_status;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public int getUpload_cycle() {
		return upload_cycle;
	}
	public void setUpload_cycle(int upload_cycle) {
		this.upload_cycle = upload_cycle;
	}
	public int getAlloc_header_id() {
		return alloc_header_id;
	}
	public void setAlloc_header_id(int alloc_header_id) {
		this.alloc_header_id = alloc_header_id;
	}
	public String getFileUploadFileName() {
		return fileUploadFileName;
	}
	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}
	public int getAlloc_xgen_header_id() {
		return alloc_xgen_header_id;
	}
	public void setAlloc_xgen_header_id(int alloc_xgen_header_id) {
		this.alloc_xgen_header_id = alloc_xgen_header_id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
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
	
}
