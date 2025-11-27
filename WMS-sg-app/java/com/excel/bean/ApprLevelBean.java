package com.excel.bean;

import java.util.List;

public class ApprLevelBean {

	private String approverName;
	private Long taskId;
	private String division;
	private String empId;
	private String apprLevel;
	private List<ApprLevelBean> approvers;
	private List<String> approverIds;
	private String transactionType;
	private String companyCode;
	private String ipAddress;
	
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public List<ApprLevelBean> getApprovers() {
		return approvers;
	}
	public void setApprovers(List<ApprLevelBean> approvers) {
		this.approvers = approvers;
	}
	public String getApprLevel() {
		return apprLevel;
	}
	public void setApprLevel(String apprLevel) {
		this.apprLevel = apprLevel;
	}
	public List<String> getApproverIds() {
		return approverIds;
	}
	public void setApproverIds(List<String> approverIds) {
		this.approverIds = approverIds;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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
	
	
}
