package com.excel.bean;

import java.util.List;

public class AnnualPlanConsolidationBean {

    private Long teamId;
    private String documentNumber;
	private String consolidationType;
	private String finYear; 
	private String empId;
	private String companyCode;
	private String ipAddress;
	private List<Long> aspIds;//for team consolation
	private String allocType;
	private List<Long> allocConIds;//for company consolation
    
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getConsolidationType() {
		return consolidationType;
	}
	public void setConsolidationType(String consolidationType) {
		this.consolidationType = consolidationType;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public List<Long> getAspIds() {
		return aspIds;
	}
	public void setAspIds(List<Long> aspIds) {
		this.aspIds = aspIds;
	}
	public String getAllocType() {
		return allocType;
	}
	public void setAllocType(String allocType) {
		this.allocType = allocType;
	}
	public List<Long> getAllocConIds() {
		return allocConIds;
	}
	public void setAllocConIds(List<Long> allocConIds) {
		this.allocConIds = allocConIds;
	}
    
    
}
