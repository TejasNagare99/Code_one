package com.excel.bean;

import java.math.BigDecimal;

public class ErpGrnConfirmationBean {

	private String Organization;
	private String reason_code;
	private String ibd_no;
	private String description;
	private String product;
	private String batch;
	private BigDecimal quantity;
	private String stockType;
	private String reference_no;
	private String storage_condition;
	private String confirmationFlag;
	public String getOrganization() {
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}
	public String getReason_code() {
		return reason_code;
	}
	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}
	public String getIbd_no() {
		return ibd_no;
	}
	public void setIbd_no(String ibd_no) {
		this.ibd_no = ibd_no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public String getReference_no() {
		return reference_no;
	}
	public void setReference_no(String reference_no) {
		this.reference_no = reference_no;
	}
	public String getStorage_condition() {
		return storage_condition;
	}
	public void setStorage_condition(String storage_condition) {
		this.storage_condition = storage_condition;
	}
	public String getConfirmationFlag() {
		return confirmationFlag;
	}
	public void setConfirmationFlag(String confirmationFlag) {
		this.confirmationFlag = confirmationFlag;
	}
	
}
