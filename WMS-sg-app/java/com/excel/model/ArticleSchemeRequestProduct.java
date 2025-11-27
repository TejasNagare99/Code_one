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
@Table(name = "ARTICLE_SCHREQ_PROD")
public class ArticleSchemeRequestProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ARTICLE_REQ_PRD_ID")
	private Long article_req_prd_id;
	
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
	
	@Column(name = "TRD_SCHEME_DTL_ID")
	private Long trd_scheme_dtl_id;
	
	@Column(name = "SALE_PROD_CODE_SG")
	private String sale_prod_code_sg;

	@Column(name = "SALE_PROD_QTY_BILLED")
	private Long sale_prod_qty_billed;

	@Column(name = "SALE_PROD_QTY_FREE")
	private Long sale_prod_qty_free;

	@Column(name = "SALE_PROD_QTY_TOT")
	private Long sale_prod_qty_tot;
	
	@Column(name = "SALE_PROD_QTY_TOT_A")
	private Long sale_prod_qty_tot_a;
	
	@Column(name = "ARTICLE_QTY")
	private Long article_qty;
	
	@Column(name = "SALE_BILLED_VALUE")
	private BigDecimal sale_billed_value;
	
	@Column(name = "ARTPRD_ins_user_id")
	private String artprd_ins_user_id;

	@Column(name = "ARTPRD_mod_user_id")
	private String artprd_mod_user_id;

	@Column(name = "ARTPRD_ins_dt")
	private Date artprd_ins_dt;

	@Column(name = "ARTPRD_mod_dt")
	private Date artprd_mod_dt;

	@Column(name = "ARTPRD_ins_ip_add")
	private String artprd_ins_ip_add;

	@Column(name = "ARTPRD_mod_ip_add")
	private String artprd_mod_ip_add;

	@Column(name = "ARTSCH_PRD_status")
	private String artsch_prd_status;
	
	@Column(name = "SALE_PROD_ID_SG")
	private Long sale_prod_id_sg;
	
	public Long getSale_prod_id_sg() {
		return sale_prod_id_sg;
	}

	public void setSale_prod_id_sg(Long sale_prod_id_sg) {
		this.sale_prod_id_sg = sale_prod_id_sg;
	}

	public Long getArticle_req_prd_id() {
		return article_req_prd_id;
	}

	public void setArticle_req_prd_id(Long article_req_prd_id) {
		this.article_req_prd_id = article_req_prd_id;
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

	public Long getTrd_scheme_dtl_id() {
		return trd_scheme_dtl_id;
	}

	public void setTrd_scheme_dtl_id(Long trd_scheme_dtl_id) {
		this.trd_scheme_dtl_id = trd_scheme_dtl_id;
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

	public Long getSale_prod_qty_tot_a() {
		return sale_prod_qty_tot_a;
	}

	public void setSale_prod_qty_tot_a(Long sale_prod_qty_tot_a) {
		this.sale_prod_qty_tot_a = sale_prod_qty_tot_a;
	}

	public Long getArticle_qty() {
		return article_qty;
	}

	public void setArticle_qty(Long article_qty) {
		this.article_qty = article_qty;
	}

	public BigDecimal getSale_billed_value() {
		return sale_billed_value;
	}

	public void setSale_billed_value(BigDecimal sale_billed_value) {
		this.sale_billed_value = sale_billed_value;
	}

	public String getArtprd_ins_user_id() {
		return artprd_ins_user_id;
	}

	public void setArtprd_ins_user_id(String artprd_ins_user_id) {
		this.artprd_ins_user_id = artprd_ins_user_id;
	}

	public String getArtprd_mod_user_id() {
		return artprd_mod_user_id;
	}

	public void setArtprd_mod_user_id(String artprd_mod_user_id) {
		this.artprd_mod_user_id = artprd_mod_user_id;
	}

	public Date getArtprd_ins_dt() {
		return artprd_ins_dt;
	}

	public void setArtprd_ins_dt(Date artprd_ins_dt) {
		this.artprd_ins_dt = artprd_ins_dt;
	}

	public Date getArtprd_mod_dt() {
		return artprd_mod_dt;
	}

	public void setArtprd_mod_dt(Date artprd_mod_dt) {
		this.artprd_mod_dt = artprd_mod_dt;
	}

	public String getArtprd_ins_ip_add() {
		return artprd_ins_ip_add;
	}

	public void setArtprd_ins_ip_add(String artprd_ins_ip_add) {
		this.artprd_ins_ip_add = artprd_ins_ip_add;
	}

	public String getArtprd_mod_ip_add() {
		return artprd_mod_ip_add;
	}

	public void setArtprd_mod_ip_add(String artprd_mod_ip_add) {
		this.artprd_mod_ip_add = artprd_mod_ip_add;
	}

	public String getArtsch_prd_status() {
		return artsch_prd_status;
	}

	public void setArtsch_prd_status(String artsch_prd_status) {
		this.artsch_prd_status = artsch_prd_status;
	}

}
