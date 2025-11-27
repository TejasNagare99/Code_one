package com.excel.bean;

import java.math.BigDecimal;

public class ErpGrnBean {
	private String Organization;
	private String prodtype;
	private String storageCondition;
	private String documentType;
	private String warehouse;
	private String poRefNo;
	private String refNo;
	private String lineNo;
	private String sku;
	private BigDecimal qty;
	private String lotNo;
	private String expiryDate;
	private String lotPrintingName;
	private String manfacturingDate;
	private String complete;
	
	private String document_no;
	private String document_date;
	private String vendor_name;
	public String getOrganization() {
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}
	public String getProdtype() {
		return prodtype;
	}
	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}
	public String getStorageCondition() {
		return storageCondition;
	}
	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getPoRefNo() {
		return poRefNo;
	}
	public void setPoRefNo(String poRefNo) {
		this.poRefNo = poRefNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getLotPrintingName() {
		return lotPrintingName;
	}
	public void setLotPrintingName(String lotPrintingName) {
		this.lotPrintingName = lotPrintingName;
	}
	public String getManfacturingDate() {
		return manfacturingDate;
	}
	public void setManfacturingDate(String manfacturingDate) {
		this.manfacturingDate = manfacturingDate;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	public String getDocument_no() {
		return document_no;
	}
	public void setDocument_no(String document_no) {
		this.document_no = document_no;
	}
	public String getDocument_date() {
		return document_date;
	}
	public void setDocument_date(String document_date) {
		this.document_date = document_date;
	}
	public String getVendor_name() {
		return vendor_name;
	}
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}


}
