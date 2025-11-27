package com.excel.bean;

public class NotificationRequestBean {
	
	private Long fsId;
	private String levelCode;
	private Long periodId;
	private Long finYearId;
	private String companyCode;
	private String username;
	private Long userId;
	
	public Long getFsId() {
		return fsId;
	}
	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public Long getFinYearId() {
		return finYearId;
	}
	public void setFinYearId(Long finYearId) {
		this.finYearId = finYearId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
