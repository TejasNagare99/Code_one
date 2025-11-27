package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLE_SCHREQ_DTL")
public class ArticleSchemeRequestDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ARTICLE_REQ_DTL_ID")
	private Long article_req_dtl_id;

	@Column(name = "ARTICLE_REQ_ID")
	private Long article_req_id;

	@Column(name = "ARTICLE_REQ_DATE")
	private Date article_req_date;

	@Column(name = "FIN_YEAR")
	private String fin_year;

	@Column(name = "PERIOD_CODE")
	private String period_code;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "LOC_ID")
	private Long loc_id;

	@Column(name = "FS_ID")
	private Long fs_id;

	@Column(name = "CUST_ID")
	private Long cust_id;

	@Column(name = "TRD_SCH_SLNO")
	private Long trd_sch_slno;
	
	@Column(name = "SCHEME_APPLIED_TO")
	private String scheme_applied_to;
	
	@Column(name = "BILLING_VALUE")
	private BigDecimal billing_value;

	@Column(name="SALE_PROD_ID")
	private Long sale_prod_id;

	@Column(name = "SALE_PROD_CODE_SG")
	private String sale_prod_code_sg;

	@Column(name = "SALE_PROD_QTY_BILLED")
	private Long sale_prod_qty_billed;

	@Column(name = "SALE_PROD_QTY_FREE")
	private Long sale_prod_qty_free;

	@Column(name = "SALE_PROD_QTY_TOT")
	private Long sale_prod_qty_tot;
	
	@Column(name="ARTICLE_PROD_ID")
	private Long article_prod_id;

	@Column(name = "ARTICLE_PROD_CD")
	private String article_prod_cd;

	@Column(name = "ARTICLE_PROD_QTY")
	private Long article_prod_qty;

	@Column(name = "ARTICLE_PROD_RATE")
	private BigDecimal article_prod_rate;

	@Column(name = "ARTICLE_PROD_VALUE")
	private BigDecimal article_prod_value;

	@Column(name = "ARTSCH_ins_user_id")
	private String artsch_ins_user_id;

	@Column(name = "ARTSCH_mod_user_id")
	private String artsch_mod_user_id;

	@Column(name = "ARTSCH_ins_dt")
	private Date artsch_ins_dt;

	@Column(name = "ARTSCH_mod_dt")
	private Date artsch_mod_dt;

	@Column(name = "ARTSCH_ins_ip_add")
	private String artsch_ins_ip_add;

	@Column(name = "ARTSCH_mod_ip_add")
	private String artsch_mod_ip_add;

	@Column(name = "ARTSCH_DT_status")
	private String artsch_dt_status;

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

	public Date getArticle_req_date() {
		return article_req_date;
	}

	public void setArticle_req_date(Date article_req_date) {
		this.article_req_date = article_req_date;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public Long getFs_id() {
		return fs_id;
	}

	public void setFs_id(Long fs_id) {
		this.fs_id = fs_id;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public Long getTrd_sch_slno() {
		return trd_sch_slno;
	}

	public void setTrd_sch_slno(Long trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
	}

	public Long getSale_prod_id() {
		return sale_prod_id;
	}

	public void setSale_prod_id(Long sale_prod_id) {
		this.sale_prod_id = sale_prod_id;
	}

	public String getSale_prod_code_sg() {
		return sale_prod_code_sg;
	}

	public void setSale_prod_code_sg(String sale_prod_code_sg) {
		this.sale_prod_code_sg = sale_prod_code_sg;
	}

	public Long getSale_prod_qty_billed() {
		return sale_prod_qty_billed;
	}

	public void setSale_prod_qty_billed(Long sale_prod_qty_billed) {
		this.sale_prod_qty_billed = sale_prod_qty_billed;
	}

	public Long getSale_prod_qty_free() {
		return sale_prod_qty_free;
	}

	public void setSale_prod_qty_free(Long sale_prod_qty_free) {
		this.sale_prod_qty_free = sale_prod_qty_free;
	}

	public Long getSale_prod_qty_tot() {
		return sale_prod_qty_tot;
	}

	public void setSale_prod_qty_tot(Long sale_prod_qty_tot) {
		this.sale_prod_qty_tot = sale_prod_qty_tot;
	}

	public Long getArticle_prod_id() {
		return article_prod_id;
	}

	public void setArticle_prod_id(Long article_prod_id) {
		this.article_prod_id = article_prod_id;
	}

	public String getArticle_prod_cd() {
		return article_prod_cd;
	}

	public void setArticle_prod_cd(String article_prod_cd) {
		this.article_prod_cd = article_prod_cd;
	}

	public Long getArticle_prod_qty() {
		return article_prod_qty;
	}

	public void setArticle_prod_qty(Long article_prod_qty) {
		this.article_prod_qty = article_prod_qty;
	}

	public BigDecimal getArticle_prod_rate() {
		return article_prod_rate;
	}

	public void setArticle_prod_rate(BigDecimal article_prod_rate) {
		this.article_prod_rate = article_prod_rate;
	}

	public BigDecimal getArticle_prod_value() {
		return article_prod_value;
	}

	public void setArticle_prod_value(BigDecimal article_prod_value) {
		this.article_prod_value = article_prod_value;
	}

	public String getArtsch_ins_user_id() {
		return artsch_ins_user_id;
	}

	public void setArtsch_ins_user_id(String artsch_ins_user_id) {
		this.artsch_ins_user_id = artsch_ins_user_id;
	}

	public String getArtsch_mod_user_id() {
		return artsch_mod_user_id;
	}

	public void setArtsch_mod_user_id(String artsch_mod_user_id) {
		this.artsch_mod_user_id = artsch_mod_user_id;
	}

	public Date getArtsch_ins_dt() {
		return artsch_ins_dt;
	}

	public void setArtsch_ins_dt(Date artsch_ins_dt) {
		this.artsch_ins_dt = artsch_ins_dt;
	}

	public Date getArtsch_mod_dt() {
		return artsch_mod_dt;
	}

	public void setArtsch_mod_dt(Date artsch_mod_dt) {
		this.artsch_mod_dt = artsch_mod_dt;
	}

	public String getArtsch_ins_ip_add() {
		return artsch_ins_ip_add;
	}

	public void setArtsch_ins_ip_add(String artsch_ins_ip_add) {
		this.artsch_ins_ip_add = artsch_ins_ip_add;
	}

	public String getArtsch_mod_ip_add() {
		return artsch_mod_ip_add;
	}

	public void setArtsch_mod_ip_add(String artsch_mod_ip_add) {
		this.artsch_mod_ip_add = artsch_mod_ip_add;
	}

	public String getArtsch_dt_status() {
		return artsch_dt_status;
	}

	public void setArtsch_dt_status(String artsch_dt_status) {
		this.artsch_dt_status = artsch_dt_status;
	}

	public String getScheme_applied_to() {
		return scheme_applied_to;
	}

	public void setScheme_applied_to(String scheme_applied_to) {
		this.scheme_applied_to = scheme_applied_to;
	}

	public BigDecimal getBilling_value() {
		return billing_value;
	}

	public void setBilling_value(BigDecimal billing_value) {
		this.billing_value = billing_value;
	}
}
