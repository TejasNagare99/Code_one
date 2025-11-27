package com.excel.bean;

import java.math.BigDecimal;

public class HQBean {
	
	String division;
	String company;
	String state;
	String hqName;
	String hqCode;
	String mapCode;
	String poolIndicator;
	String representative;
	BigDecimal performance;
	String status;
	
	
	private String teamcode;
	
	public String getTeamcode() {
		return teamcode;
	}
	public void setTeamcode(String teamcode) {
		this.teamcode = teamcode;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHqName() {
		return hqName;
	}
	public void setHqName(String hqName) {
		this.hqName = hqName;
	}
	public String getHqCode() {
		return hqCode;
	}
	public void setHqCode(String hqCode) {
		this.hqCode = hqCode;
	}
	public String getMapCode() {
		return mapCode;
	}
	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}
	public String getPoolIndicator() {
		return poolIndicator;
	}
	public void setPoolIndicator(String poolIndicator) {
		this.poolIndicator = poolIndicator;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	
	public BigDecimal getPerformance() {
		return performance;
	}
	public void setPerformance(BigDecimal performance) {
		this.performance = performance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
