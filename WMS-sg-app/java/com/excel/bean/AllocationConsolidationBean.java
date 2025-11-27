package com.excel.bean;

import java.util.List;

public class AllocationConsolidationBean {

	private Long aspId;
	private String documentNumber;
	private String managerName;
	private String allocationDate;
	private String planType;
	private String brandsCovered;
	
	private Long allocConId;
	private String division;
	private Long divId;
	private String status;
	private char file_download;
	private char file_upload;
	
	private Long allocGenId;
	
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String  planType) {
		this.planType = planType;
	}
	public String getBrandsCovered() {
		return brandsCovered;
	}
	public void setBrandsCovered(String brandsCovered) {
		this.brandsCovered = brandsCovered;
	}
	public Long getAspId() {
		return aspId;
	}
	public void setAspId(Long aspId) {
		this.aspId = aspId;
	}
	public Long getAllocConId() {
		return allocConId;
	}
	public void setAllocConId(Long allocConId) {
		this.allocConId = allocConId;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public Long getAllocGenId() {
		return allocGenId;
	}
	public void setAllocGenId(Long allocGenId) {
		this.allocGenId = allocGenId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getDivId() {
		return divId;
	}
	public void setDivId(Long divId) {
		this.divId = divId;
	}
	public char getFile_download() {
		return file_download;
	}
	public void setFile_download(char file_download) {
		this.file_download = file_download;
	}
	public char getFile_upload() {
		return file_upload;
	}
	public void setFile_upload(char file_upload) {
		this.file_upload = file_upload;
	}

}
