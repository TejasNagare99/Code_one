package com.excel.bean;

import java.util.List;

public class UserBrandMapopingBean {
	
	private String empId;
	private String role;
	private String userEmpId;
	private List<String> brandId;
	private String ipAddress;
	private String CompanyCode;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserEmpId() {
		return userEmpId;
	}
	public void setUserEmpId(String userEmpId) {
		this.userEmpId = userEmpId;
	}
	public List<String> getBrandId() {
		return brandId;
	}
	public void setBrandId(List<String> brandId) {
		this.brandId = brandId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCompanyCode() {
		return CompanyCode;
	}
	public void setCompanyCode(String companyCode) {
		CompanyCode = companyCode;
	}
	
	

}
