package com.excel.bean;

import java.util.List;

public class ProductwiseTaxMasterBean {
	String company;
	String prodCode;
	String stateId;
	String taxCode;
	String empId;
	Object stateList;
	
	public Object getStateList() {
		return stateList;
	}
	public void setStateList(Object stateList) {
		this.stateList = stateList;
	}
	
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	
}
