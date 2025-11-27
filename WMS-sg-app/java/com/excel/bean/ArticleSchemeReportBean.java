package com.excel.bean;

import java.util.Arrays;

public class ArticleSchemeReportBean {

	private String [] location;	
	private String customer;
	private String invoice;
	private String lrNo;
	private String artScheme;
	private String artName;
	private String salesProduct;
	private String stDt;
	private String enDt;
	private String ind;
	private String pending_qty;
	
	private String finyearflag;
	private String finyer_id;

	
	
	public String getFinyearflag() {
		return finyearflag;
	}
	public void setFinyearflag(String finyearflag) {
		this.finyearflag = finyearflag;
	}
	public String getFinyer_id() {
		return finyer_id;
	}
	public void setFinyer_id(String finyer_id) {
		this.finyer_id = finyer_id;
	}
	public String[] getLocation() {
		return location;
	}
	public void setLocation(String[] location) {
		this.location = location;
	}
	public String getPending_qty() {
		return pending_qty;
	}
	public void setPending_qty(String pending_qty) {
		this.pending_qty = pending_qty;
	}
	public String getInd() {
		return ind;
	}
	public void setInd(String ind) {
		this.ind = ind;
	}

	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getLrNo() {
		return lrNo;
	}
	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}
	public String getArtScheme() {
		return artScheme;
	}
	public void setArtScheme(String artScheme) {
		this.artScheme = artScheme;
	}
	public String getArtName() {
		return artName;
	}
	public void setArtName(String artName) {
		this.artName = artName;
	}
	public String getSalesProduct() {
		return salesProduct;
	}
	public void setSalesProduct(String salesProduct) {
		this.salesProduct = salesProduct;
	}
	public String getStDt() {
		return stDt;
	}
	public void setStDt(String stDt) {
		this.stDt = stDt;
	}
	public String getEnDt() {
		return enDt;
	}
	public void setEnDt(String enDt) {
		this.enDt = enDt;
	}
	
	
	@Override
	public String toString() {
		return "ArticleSchemeReportBean [location=" + Arrays.toString(location) + ", customer=" + customer
				+ ", invoice=" + invoice + ", lrNo=" + lrNo + ", artScheme=" + artScheme + ", artName=" + artName
				+ ", salesProduct=" + salesProduct + ", stDt=" + stDt + ", enDt=" + enDt + ", ind=" + ind
				+ ", pending_qty=" + pending_qty + ", finyearflag=" + finyearflag + ", finyer_id=" + finyer_id + "]";
	}
	
	
	
	
	
	
	
}
