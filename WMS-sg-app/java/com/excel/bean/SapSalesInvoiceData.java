package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;

public class SapSalesInvoiceData {
	private Long cust_id;
	private Date sap_invoice_date;
	private String lr_number;
	private Date lr_date;
	private Long sale_prod_id;
	private String sale_prod_cd;
	private Long billed_qty;
	private BigDecimal billing_value;
	
	public Date getSap_invoice_date() {
		return sap_invoice_date;
	}
	public void setSap_invoice_date(Date sap_invoice_date) {
		this.sap_invoice_date = sap_invoice_date;
	}
	public String getLr_number() {
		return lr_number;
	}
	public void setLr_number(String lr_number) {
		this.lr_number = lr_number;
	}
	public Date getLr_date() {
		return lr_date;
	}
	public void setLr_date(Date lr_date) {
		this.lr_date = lr_date;
	}
	public Long getSale_prod_id() {
		return sale_prod_id;
	}
	public void setSale_prod_id(Long sale_prod_id) {
		this.sale_prod_id = sale_prod_id;
	}
	public String getSale_prod_cd() {
		return sale_prod_cd;
	}
	public void setSale_prod_cd(String sale_prod_cd) {
		this.sale_prod_cd = sale_prod_cd;
	}
	public Long getBilled_qty() {
		return billed_qty;
	}
	public void setBilled_qty(Long billed_qty) {
		this.billed_qty = billed_qty;
	}
	public BigDecimal getBilling_value() {
		return billing_value;
	}
	public void setBilling_value(BigDecimal billing_value) {
		this.billing_value = billing_value;
	}
	public Long getCust_id() {
		return cust_id;
	}
	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}
	public SapSalesInvoiceData(Long cust_id, Date sap_invoice_date, String lr_number, Date lr_date,
			Long sale_prod_id, String sale_prod_cd, Long billed_qty, BigDecimal billing_value) {
		super();
		this.cust_id = cust_id;
		this.sap_invoice_date = sap_invoice_date;
		this.lr_number = lr_number;
		this.lr_date = lr_date;
		this.sale_prod_id = sale_prod_id;
		this.sale_prod_cd = sale_prod_cd;
		this.billed_qty = billed_qty;
		this.billing_value = billing_value;
	}
	
	
}
