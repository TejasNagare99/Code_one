package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ArtSchemeBeanForApprNewLogic {
	private Long article_req_id;
	private Date article_req_date;
	private String request_no;
	private String invoice_no;
	private Date invoice_date;
	private String lr_number;
	private Date lr_date;
	private BigDecimal artsch_total_value;
	private String erp_cust_cd;
	private String cust_name_billto;
	private String addr1_shipto;
	private String addr2_shipto;
	private String addr3_shipto;
	private String addr4_shipto;
	private String destination_shipto;
	private String scheme_applied_to;
	private Long article_req_dtl_id;
	private Long trd_sch_slno;
	private String trd_scheme_code;
	private String trd_scheme_name;
	private String smp_prod_name;
	private BigDecimal billed_value;
	private BigDecimal billed_val_entered_tot;
	private Long articles_to_give;
	private Long trd_scheme_dtl_id;
	private String sale_prod_name;
	private Long per_sale_qty_billed;
	private Long per_sale_qty_free;
	private Long article_qty;
	private Long article_req_prd_id;
	private BigDecimal per_sale_billed_val;
	private Long per_sale_prod_qty_entered;
	private String sale_prod_code;
	private String smp_erp_prod_cd;
	
	public String getSmp_erp_prod_cd() {
		return smp_erp_prod_cd;
	}
	public void setSmp_erp_prod_cd(String smp_erp_prod_cd) {
		this.smp_erp_prod_cd = smp_erp_prod_cd;
	}
	public String getSale_prod_code() {
		return sale_prod_code;
	}
	public void setSale_prod_code(String sale_prod_code) {
		this.sale_prod_code = sale_prod_code;
	}
	public Long getArticle_req_dtl_id() {
		return article_req_dtl_id;
	}
	public void setArticle_req_dtl_id(Long article_req_dtl_id) {
		this.article_req_dtl_id = article_req_dtl_id;
	}
	public Long getArticle_req_id() {
		return article_req_id;
	}
	public void setArticle_req_id(Long article_req_id) {
		this.article_req_id = article_req_id;
	}
	
	public String getRequest_no() {
		return request_no;
	}
	public void setRequest_no(String request_no) {
		this.request_no = request_no;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	
	public String getLr_number() {
		return lr_number;
	}
	public void setLr_number(String lr_number) {
		this.lr_number = lr_number;
	}

	public Date getArticle_req_date() {
		return article_req_date;
	}
	public void setArticle_req_date(Date article_req_date) {
		this.article_req_date = article_req_date;
	}
	public Date getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}
	public Date getLr_date() {
		return lr_date;
	}
	public void setLr_date(Date lr_date) {
		this.lr_date = lr_date;
	}
	public BigDecimal getArtsch_total_value() {
		return artsch_total_value;
	}
	public void setArtsch_total_value(BigDecimal artsch_total_value) {
		this.artsch_total_value = artsch_total_value;
	}
	public String getErp_cust_cd() {
		return erp_cust_cd;
	}
	public void setErp_cust_cd(String erp_cust_cd) {
		this.erp_cust_cd = erp_cust_cd;
	}
	public String getCust_name_billto() {
		return cust_name_billto;
	}
	public void setCust_name_billto(String cust_name_billto) {
		this.cust_name_billto = cust_name_billto;
	}
	public String getAddr1_shipto() {
		return addr1_shipto;
	}
	public void setAddr1_shipto(String addr1_shipto) {
		this.addr1_shipto = addr1_shipto;
	}
	public String getAddr2_shipto() {
		return addr2_shipto;
	}
	public void setAddr2_shipto(String addr2_shipto) {
		this.addr2_shipto = addr2_shipto;
	}
	public String getAddr3_shipto() {
		return addr3_shipto;
	}
	public void setAddr3_shipto(String addr3_shipto) {
		this.addr3_shipto = addr3_shipto;
	}
	public String getAddr4_shipto() {
		return addr4_shipto;
	}
	public void setAddr4_shipto(String addr4_shipto) {
		this.addr4_shipto = addr4_shipto;
	}
	public String getDestination_shipto() {
		return destination_shipto;
	}
	public void setDestination_shipto(String destination_shipto) {
		this.destination_shipto = destination_shipto;
	}
	public String getScheme_applied_to() {
		return scheme_applied_to;
	}
	public void setScheme_applied_to(String scheme_applied_to) {
		this.scheme_applied_to = scheme_applied_to;
	}
	public Long getTrd_sch_slno() {
		return trd_sch_slno;
	}
	public void setTrd_sch_slno(Long trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
	}
	public String getTrd_scheme_code() {
		return trd_scheme_code;
	}
	public void setTrd_scheme_code(String trd_scheme_code) {
		this.trd_scheme_code = trd_scheme_code;
	}
	public String getTrd_scheme_name() {
		return trd_scheme_name;
	}
	public void setTrd_scheme_name(String trd_scheme_name) {
		this.trd_scheme_name = trd_scheme_name;
	}
	public String getSmp_prod_name() {
		return smp_prod_name;
	}
	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}
	public BigDecimal getBilled_value() {
		return billed_value;
	}
	public void setBilled_value(BigDecimal billed_value) {
		this.billed_value = billed_value;
	}
	public BigDecimal getBilled_val_entered_tot() {
		return billed_val_entered_tot;
	}
	public void setBilled_val_entered_tot(BigDecimal billed_val_entered_tot) {
		this.billed_val_entered_tot = billed_val_entered_tot;
	}
	public Long getArticles_to_give() {
		return articles_to_give;
	}
	public void setArticles_to_give(Long articles_to_give) {
		this.articles_to_give = articles_to_give;
	}
	public Long getTrd_scheme_dtl_id() {
		return trd_scheme_dtl_id;
	}
	public void setTrd_scheme_dtl_id(Long trd_scheme_dtl_id) {
		this.trd_scheme_dtl_id = trd_scheme_dtl_id;
	}
	public String getSale_prod_name() {
		return sale_prod_name;
	}
	public void setSale_prod_name(String sale_prod_name) {
		this.sale_prod_name = sale_prod_name;
	}
	public Long getPer_sale_qty_billed() {
		return per_sale_qty_billed;
	}
	public void setPer_sale_qty_billed(Long per_sale_qty_billed) {
		this.per_sale_qty_billed = per_sale_qty_billed;
	}
	public Long getPer_sale_qty_free() {
		return per_sale_qty_free;
	}
	public void setPer_sale_qty_free(Long per_sale_qty_free) {
		this.per_sale_qty_free = per_sale_qty_free;
	}
	public Long getArticle_qty() {
		return article_qty;
	}
	public void setArticle_qty(Long article_qty) {
		this.article_qty = article_qty;
	}
	public Long getArticle_req_prd_id() {
		return article_req_prd_id;
	}
	public void setArticle_req_prd_id(Long article_req_prd_id) {
		this.article_req_prd_id = article_req_prd_id;
	}
	public BigDecimal getPer_sale_billed_val() {
		return per_sale_billed_val;
	}
	public void setPer_sale_billed_val(BigDecimal per_sale_billed_val) {
		this.per_sale_billed_val = per_sale_billed_val;
	}
	public Long getPer_sale_prod_qty_entered() {
		return per_sale_prod_qty_entered;
	}
	public void setPer_sale_prod_qty_entered(Long per_sale_prod_qty_entered) {
		this.per_sale_prod_qty_entered = per_sale_prod_qty_entered;
	}

}
