package com.excel.bean;


public class SpecificLocationBean {

	
	private String loc_id;
	private String loc_nm;
	public String getLoc_id() {
		return loc_id;
	}
	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}
	public String getLoc_nm() {
		return loc_nm;
	}
	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}
	@Override
	public String toString() {
		return "SpecificLocationBean [loc_id=" + loc_id + ", loc_nm=" + loc_nm + "]";
	}
	
	
}
