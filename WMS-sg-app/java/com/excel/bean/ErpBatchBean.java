package com.excel.bean;

public class ErpBatchBean {

	private String org;
	private String sku;
	private String lot;
	private String manufacturingDate;
	private String expiryDate;
	
	private Long shelf_life_in_days;
	private String status;
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getLot() {
		return lot;
	}
	public void setLot(String lot) {
		this.lot = lot;
	}
	public String getManufacturingDate() {
		return manufacturingDate;
	}
	public void setManufacturingDate(String manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Long getShelf_life_in_days() {
		return shelf_life_in_days;
	}
	public void setShelf_life_in_days(Long shelf_life_in_days) {
		this.shelf_life_in_days = shelf_life_in_days;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	

}
