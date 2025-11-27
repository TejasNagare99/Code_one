package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "ARTREQ_TO_DISPATCH_SCHME_USED")

@NamedStoredProcedureQuery(name = "callARTREQ_TO_DISPATCH_SCHME_USED", procedureName = "ARTREQ_TO_DISPATCH_SCHME_USED", parameters = {
		@StoredProcedureParameter(name = "piLOC_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "enddate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "MCustomer_CD", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "MSAP_Invoice", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "MLR_No", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "MArticle_Scheme", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "MArticle_PROD_CD", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "MSales_Product", mode = ParameterMode.IN, type = String.class),

}, resultClasses = Scheme_Desription_Bean.class)

public class Scheme_Desription_Bean {

//	@Id
//	@Column(name = "ROW")
//	private String row;

	
	@Column(name = "LOC_NM")
	private String loc_nm;

	@Column(name = "SUB_COMP_CD")
	private String sub_comp_cd;

	@Id
	@Column(name = "TRD_SCHEME_CODE")
	private String trd_scheme_code;

	@Column(name = "TRD_SCHEME_NAME")
	private String trd_scheme_name;

	@Column(name = "VALID_FROM_DT")
	private String valid_from_dt;

	@Column(name = "VALID_TO_DT")
	private String valid_to_dt;

	@Column(name = "ARTICLE_PROD_CD")
	private String article_prod_cd;

	@Column(name = "ARTICLE_NAME")
	private String article_name;

	@Column(name = "SALE_PROD_CODE_SG")
	private String sale_prod_code_sg;

	@Column(name = "SALE_PROD_NAME")
	private String sale_prod_name;

	@Column(name = "APPLY_TO")
	private String apply_to;

	@Column(name = "BILLED_VALUE")
	private BigDecimal billed_value;

	@Column(name = "ARTICLE_QTY_PER_TOT_VAL")
	private BigDecimal article_qty_per_tot_val;

	@Column(name = "PER_SALE_QTY_TOT")
	private BigDecimal per_sale_qty_tot;

	@Column(name = "ARTICLE_QTY")
	private BigDecimal article_qty;

//	public String getRow() {
//		return row;
//	}
//
//	public void setRow(String row) {
//		this.row = row;
//	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getSub_comp_cd() {
		return sub_comp_cd;
	}

	public void setSub_comp_cd(String sub_comp_cd) {
		this.sub_comp_cd = sub_comp_cd;
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

	public String getValid_from_dt() {
		return valid_from_dt;
	}

	public void setValid_from_dt(String valid_from_dt) {
		this.valid_from_dt = valid_from_dt;
	}

	public String getValid_to_dt() {
		return valid_to_dt;
	}

	public void setValid_to_dt(String valid_to_dt) {
		this.valid_to_dt = valid_to_dt;
	}

	public String getArticle_prod_cd() {
		return article_prod_cd;
	}

	public void setArticle_prod_cd(String article_prod_cd) {
		this.article_prod_cd = article_prod_cd;
	}

	public String getArticle_name() {
		return article_name;
	}

	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}

	public String getSale_prod_code_sg() {
		return sale_prod_code_sg;
	}

	public void setSale_prod_code_sg(String sale_prod_code_sg) {
		this.sale_prod_code_sg = sale_prod_code_sg;
	}

	public String getSale_prod_name() {
		return sale_prod_name;
	}

	public void setSale_prod_name(String sale_prod_name) {
		this.sale_prod_name = sale_prod_name;
	}

	public String getApply_to() {
		return apply_to;
	}

	public void setApply_to(String apply_to) {
		this.apply_to = apply_to;
	}

	public BigDecimal getBilled_value() {
		return billed_value;
	}

	public void setBilled_value(BigDecimal billed_value) {
		this.billed_value = billed_value;
	}

	public BigDecimal getArticle_qty_per_tot_val() {
		return article_qty_per_tot_val;
	}

	public void setArticle_qty_per_tot_val(BigDecimal article_qty_per_tot_val) {
		this.article_qty_per_tot_val = article_qty_per_tot_val;
	}

	public BigDecimal getPer_sale_qty_tot() {
		return per_sale_qty_tot;
	}

	public void setPer_sale_qty_tot(BigDecimal per_sale_qty_tot) {
		this.per_sale_qty_tot = per_sale_qty_tot;
	}

	public BigDecimal getArticle_qty() {
		return article_qty;
	}

	public void setArticle_qty(BigDecimal article_qty) {
		this.article_qty = article_qty;
	}

	@Override
	public String toString() {
		return "Scheme_Desription_Bean [loc_nm=" + loc_nm + ", sub_comp_cd=" + sub_comp_cd
				+ ", trd_scheme_code=" + trd_scheme_code + ", trd_scheme_name=" + trd_scheme_name + ", valid_from_dt="
				+ valid_from_dt + ", valid_to_dt=" + valid_to_dt + ", article_prod_cd=" + article_prod_cd
				+ ", article_name=" + article_name + ", sale_prod_code_sg=" + sale_prod_code_sg + ", sale_prod_name="
				+ sale_prod_name + ", apply_to=" + apply_to + ", billed_value=" + billed_value
				+ ", article_qty_per_tot_val=" + article_qty_per_tot_val + ", per_sale_qty_tot=" + per_sale_qty_tot
				+ ", article_qty=" + article_qty + "]";
	}

	
}
