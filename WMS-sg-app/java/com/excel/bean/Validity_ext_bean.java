package com.excel.bean;

import java.util.Date;

public class Validity_ext_bean {
	Long schemeId;
	Date valid_ext_dt;
	String remarks;
	Long docToRemove;
	String empId;
	String vld_ext_no_for_modify;
	String entryModify;
	
	public String getVld_ext_no_for_modify() {
		return vld_ext_no_for_modify;
	}
	public void setVld_ext_no_for_modify(String vld_ext_no_for_modify) {
		this.vld_ext_no_for_modify = vld_ext_no_for_modify;
	}
	public String getEntryModify() {
		return entryModify;
	}
	public void setEntryModify(String entryModify) {
		this.entryModify = entryModify;
	}
	public Long getDocToRemove() {
		return docToRemove;
	}
	public void setDocToRemove(Long docToRemove) {
		this.docToRemove = docToRemove;
	}
	public Long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	public Date getValid_ext_dt() {
		return valid_ext_dt;
	}
	public void setValid_ext_dt(Date valid_ext_dt) {
		this.valid_ext_dt = valid_ext_dt;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	@Override
	public String toString() {
		return "Validity_ext_bean [schemeId=" + schemeId + ", valid_ext_dt=" + valid_ext_dt + ", remarks=" + remarks
				+ ", docToRemove=" + docToRemove + ", empId=" + empId + "]";
	}
	
	
}
