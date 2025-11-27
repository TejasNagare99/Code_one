package com.excel.bean;

public class UserMasterBean {
	
	private String username;
	private String password;
	private String role;
	private Long division;
	private String fsType;
	private Long fsName;
	private String status;
	private String companyCode;
	private String saveMode;
	private Long userId;
	private String field_staff_name;
	
	//for user unlock
	private String empId;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getDivision() {
		return division;
	}
	public void setDivision(Long division) {
		this.division = division;
	}
	public String getFsType() {
		return fsType;
	}
	public void setFsType(String fsType) {
		this.fsType = fsType;
	}
	public Long getFsName() {
		return fsName;
	}
	public void setFsName(Long fsName) {
		this.fsName = fsName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getField_staff_name() {
		return field_staff_name;
	}
	public void setField_staff_name(String field_staff_name) {
		this.field_staff_name = field_staff_name;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
}
