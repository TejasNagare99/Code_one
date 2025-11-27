package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;

public class articleScheme {
	private Long exp_id;
	private String err_msg;
	private String trd_sch_slno;
	private Long article_req_id;
	private Long article_req_dtl_id;
	private String article_schm_name;
	private String article_name;
	private String art_sale_prod_cd;
	private String art_sale_prod_name;
	private BigDecimal art_billed_qty;
	private BigDecimal art_qty;
	private String invoice_no;
	private String ap;
	private String sap_plant_cd;
	private String company_cd;
	private String cfa_location;
	private String sap_inv_no;
	private Date sap_inv_dt;
	private String sap_cust_cd;
	private String customer_name;
	private String city;
	private String sap_sale_prod_cd;
	private String sap_sale_prod_name;
	private BigDecimal billed_qty;
	private BigDecimal free_qty;
	private BigDecimal billing_rate;
	private BigDecimal billing_value;
	private BigDecimal article_sale_bill_qty_entered;
	private BigDecimal article_rate;
	private BigDecimal article_value;
	private BigDecimal err_bill_qty;

	public Long getExp_id() {
		return exp_id;
	}

	public void setExp_id(Long exp_id) {
		this.exp_id = exp_id;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getTrd_sch_slno() {
		return trd_sch_slno;
	}

	public void setTrd_sch_slno(String trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
	}

	public Long getArticle_req_id() {
		return article_req_id;
	}

	public void setArticle_req_id(Long article_req_id) {
		this.article_req_id = article_req_id;
	}

	public Long getArticle_req_dtl_id() {
		return article_req_dtl_id;
	}

	public void setArticle_req_dtl_id(Long article_req_dtl_id) {
		this.article_req_dtl_id = article_req_dtl_id;
	}

	public String getArticle_schm_name() {
		return article_schm_name;
	}

	public void setArticle_schm_name(String article_schm_name) {
		this.article_schm_name = article_schm_name;
	}

	public String getArticle_name() {
		return article_name;
	}

	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}

	public String getArt_sale_prod_cd() {
		return art_sale_prod_cd;
	}

	public void setArt_sale_prod_cd(String art_sale_prod_cd) {
		this.art_sale_prod_cd = art_sale_prod_cd;
	}

	public String getArt_sale_prod_name() {
		return art_sale_prod_name;
	}

	public void setArt_sale_prod_name(String art_sale_prod_name) {
		this.art_sale_prod_name = art_sale_prod_name;
	}

	public BigDecimal getArt_billed_qty() {
		return art_billed_qty;
	}

	public void setArt_billed_qty(BigDecimal art_billed_qty) {
		this.art_billed_qty = art_billed_qty;
	}

	public BigDecimal getArt_qty() {
		return art_qty;
	}

	public void setArt_qty(BigDecimal art_qty) {
		this.art_qty = art_qty;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getAp() {
		return ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

	public String getSap_plant_cd() {
		return sap_plant_cd;
	}

	public void setSap_plant_cd(String sap_plant_cd) {
		this.sap_plant_cd = sap_plant_cd;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getCfa_location() {
		return cfa_location;
	}

	public void setCfa_location(String cfa_location) {
		this.cfa_location = cfa_location;
	}

	public String getSap_inv_no() {
		return sap_inv_no;
	}

	public void setSap_inv_no(String sap_inv_no) {
		this.sap_inv_no = sap_inv_no;
	}

	public Date getSap_inv_dt() {
		return sap_inv_dt;
	}

	public void setSap_inv_dt(Date sap_inv_dt) {
		this.sap_inv_dt = sap_inv_dt;
	}

	public String getSap_cust_cd() {
		return sap_cust_cd;
	}

	public void setSap_cust_cd(String sap_cust_cd) {
		this.sap_cust_cd = sap_cust_cd;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSap_sale_prod_cd() {
		return sap_sale_prod_cd;
	}

	public void setSap_sale_prod_cd(String sap_sale_prod_cd) {
		this.sap_sale_prod_cd = sap_sale_prod_cd;
	}

	public String getSap_sale_prod_name() {
		return sap_sale_prod_name;
	}

	public void setSap_sale_prod_name(String sap_sale_prod_name) {
		this.sap_sale_prod_name = sap_sale_prod_name;
	}

	public BigDecimal getBilled_qty() {
		return billed_qty;
	}

	public void setBilled_qty(BigDecimal billed_qty) {
		this.billed_qty = billed_qty;
	}

	public BigDecimal getFree_qty() {
		return free_qty;
	}

	public void setFree_qty(BigDecimal free_qty) {
		this.free_qty = free_qty;
	}

	public BigDecimal getBilling_rate() {
		return billing_rate;
	}

	public void setBilling_rate(BigDecimal billing_rate) {
		this.billing_rate = billing_rate;
	}

	public BigDecimal getBilling_value() {
		return billing_value;
	}

	public void setBilling_value(BigDecimal billing_value) {
		this.billing_value = billing_value;
	}

	public BigDecimal getArticle_sale_bill_qty_entered() {
		return article_sale_bill_qty_entered;
	}

	public void setArticle_sale_bill_qty_entered(BigDecimal article_sale_bill_qty_entered) {
		this.article_sale_bill_qty_entered = article_sale_bill_qty_entered;
	}

	public BigDecimal getArticle_rate() {
		return article_rate;
	}

	public void setArticle_rate(BigDecimal article_rate) {
		this.article_rate = article_rate;
	}

	public BigDecimal getArticle_value() {
		return article_value;
	}

	public void setArticle_value(BigDecimal article_value) {
		this.article_value = article_value;
	}

	public BigDecimal getErr_bill_qty() {
		return err_bill_qty;
	}

	public void setErr_bill_qty(BigDecimal err_bill_qty) {
		this.err_bill_qty = err_bill_qty;
	}

}
