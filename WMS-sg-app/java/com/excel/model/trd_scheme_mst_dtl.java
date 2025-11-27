package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRD_SCHEME_MST_DTL")
public class trd_scheme_mst_dtl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRD_SCHEME_DTL_ID")
	private long trd_scheme_dtl_id;
	
	@Column(name = "COMPANY_CD")
	private String company_cd;
	
	
	@Column(name = "SCHEME_APPTYPE")
	private String scheme_apptype;
	
	
	@Column(name = "TRD_SCH_SLNO")
	private long trd_sch_slno;
	
	
	@Column(name = "TRD_SCHEME_CODE")
	private String trd_scheme_code;
	
	
	@Column(name = "VALID_FROM_DT")
	private Date valid_from_dt;
	
	@Column(name = "VALID_TO_DT")
	private Date valid_to_dt;
	
	@Column(name = "APPLY_TO")
	private String apply_to;
	
	@Column(name = "SALE_PROD_ID")
	private long sale_prod_id;
	
	
	@Column(name = "SALE_PROD_CODE_SG")
	private String sale_prod_code_sg;
	
	
	@Column(name = "SALE_PROD_CODE_ERP")
	private String sale_prod_code_erp;
	
	
	@Column(name = "PER_SALE_QTY_BILLED")
	private long per_sale_qty_billed;
	
	@Column(name = "PER_SALE_QTY_FREE")
	private long per_sale_qty_free;
	
	@Column(name = "PER_SALE_QTY_TOT")
	private long per_sale_qty_tot;
	
	
	@Column(name = "ARTICLE_QTY")
	private long article_qty;
	
	
	@Column(name = "CREATED_BY")
	private String created_by;
	
	@Column(name = "CREATED_DATE")
	private Date created_date;
	
	@Column(name = "LAST_MOD_BY")
	private String last_mod_by;
	
	
	@Column(name = "LAST_MOD_DATE")
	private Date last_mod_date;


	@Override
	public String toString() {
		return "trd_scheme_mst_dtl [trd_scheme_dtl_id=" + trd_scheme_dtl_id + ", company_cd=" + company_cd
				+ ", scheme_apptype=" + scheme_apptype + ", trd_sch_slno=" + trd_sch_slno + ", trd_scheme_code="
				+ trd_scheme_code + ", valid_from_dt=" + valid_from_dt + ", valid_to_dt=" + valid_to_dt + ", apply_to="
				+ apply_to + ", sale_prod_id=" + sale_prod_id + ", sale_prod_code_sg=" + sale_prod_code_sg
				+ ", sale_prod_code_erp=" + sale_prod_code_erp + ", per_sale_qty_billed=" + per_sale_qty_billed
				+ ", per_sale_qty_free=" + per_sale_qty_free + ", per_sale_qty_tot=" + per_sale_qty_tot
				+ ", article_qty=" + article_qty + ", created_by=" + created_by + ", created_date=" + created_date
				+ ", last_mod_by=" + last_mod_by + ", last_mod_date=" + last_mod_date + "]";
	}


	public long getTrd_scheme_dtl_id() {
		return trd_scheme_dtl_id;
	}


	public void setTrd_scheme_dtl_id(long trd_scheme_dtl_id) {
		this.trd_scheme_dtl_id = trd_scheme_dtl_id;
	}


	public String getCompany_cd() {
		return company_cd;
	}


	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}


	public String getScheme_apptype() {
		return scheme_apptype;
	}


	public void setScheme_apptype(String scheme_apptype) {
		this.scheme_apptype = scheme_apptype;
	}


	public long getTrd_sch_slno() {
		return trd_sch_slno;
	}


	public void setTrd_sch_slno(long trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
	}


	public String getTrd_scheme_code() {
		return trd_scheme_code;
	}


	public void setTrd_scheme_code(String trd_scheme_code) {
		this.trd_scheme_code = trd_scheme_code;
	}


	public Date getValid_from_dt() {
		return valid_from_dt;
	}


	public void setValid_from_dt(Date valid_from_dt) {
		this.valid_from_dt = valid_from_dt;
	}


	public Date getValid_to_dt() {
		return valid_to_dt;
	}


	public void setValid_to_dt(Date valid_to_dt) {
		this.valid_to_dt = valid_to_dt;
	}


	public String getApply_to() {
		return apply_to;
	}


	public void setApply_to(String apply_to) {
		this.apply_to = apply_to;
	}


	public long getSale_prod_id() {
		return sale_prod_id;
	}


	public void setSale_prod_id(long sale_prod_id) {
		this.sale_prod_id = sale_prod_id;
	}


	public String getSale_prod_code_sg() {
		return sale_prod_code_sg;
	}


	public void setSale_prod_code_sg(String sale_prod_code_sg) {
		this.sale_prod_code_sg = sale_prod_code_sg;
	}


	public String getSale_prod_code_erp() {
		return sale_prod_code_erp;
	}


	public void setSale_prod_code_erp(String sale_prod_code_erp) {
		this.sale_prod_code_erp = sale_prod_code_erp;
	}


	public long getPer_sale_qty_billed() {
		return per_sale_qty_billed;
	}


	public void setPer_sale_qty_billed(long per_sale_qty_billed) {
		this.per_sale_qty_billed = per_sale_qty_billed;
	}


	public long getPer_sale_qty_free() {
		return per_sale_qty_free;
	}


	public void setPer_sale_qty_free(long per_sale_qty_free) {
		this.per_sale_qty_free = per_sale_qty_free;
	}


	public long getPer_sale_qty_tot() {
		return per_sale_qty_tot;
	}


	public void setPer_sale_qty_tot(long per_sale_qty_tot) {
		this.per_sale_qty_tot = per_sale_qty_tot;
	}


	public long getArticle_qty() {
		return article_qty;
	}


	public void setArticle_qty(long article_qty) {
		this.article_qty = article_qty;
	}


	public String getCreated_by() {
		return created_by;
	}


	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}


	public Date getCreated_date() {
		return created_date;
	}


	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}


	public String getLast_mod_by() {
		return last_mod_by;
	}


	public void setLast_mod_by(String last_mod_by) {
		this.last_mod_by = last_mod_by;
	}


	public Date getLast_mod_date() {
		return last_mod_date;
	}


	public void setLast_mod_date(Date last_mod_date) {
		this.last_mod_date = last_mod_date;
	}
	
	
	

}
