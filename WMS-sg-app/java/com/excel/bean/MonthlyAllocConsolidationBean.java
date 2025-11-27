package com.excel.bean;

import java.util.List;

public class MonthlyAllocConsolidationBean {

    private Long teamId;
    private String documentNumber;
	private String consolidationType;
	private String finYear; 
	private String empId;
	private String companyCode;
	private String ipAddress;
	private List<Long> allocGenIds;//for team consolation
	private String allocType;
	private String allocMonth;
	private String monthOfUsePeriodCode;
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
	public String getConsolidationType() {
		return consolidationType;
	}
	public void setConsolidationType(String consolidationType) {
		this.consolidationType = consolidationType;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
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
	public List<Long> getAllocGenIds() {
		return allocGenIds;
	}
	public void setAllocGenIds(List<Long> allocGenIds) {
		this.allocGenIds = allocGenIds;
	}
	public String getAllocType() {
		return allocType;
	}
	public void setAllocType(String allocType) {
		this.allocType = allocType;
	}
	public String getAllocMonth() {
		return allocMonth;
	}
	public void setAllocMonth(String allocMonth) {
		this.allocMonth = allocMonth;
	}
	public String getMonthOfUsePeriodCode() {
		return monthOfUsePeriodCode;
	}
	public void setMonthOfUsePeriodCode(String monthOfUsePeriodCode) {
		this.monthOfUsePeriodCode = monthOfUsePeriodCode;
	}
    
    
    
}
