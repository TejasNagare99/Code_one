package com.excel.bean;

import java.util.List;

public class HcpBulkUploadErrorBean {
	private int rowNum;
	private List<String> errorList;
	private List<String> warning;
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public List<String> getWarning() {
		return warning;
	}
	public void setWarning(List<String> warning) {
		this.warning = warning;
	}
	
	
}
