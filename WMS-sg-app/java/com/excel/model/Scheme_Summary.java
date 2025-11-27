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
import javax.persistence.Transient;

//ARTICLE_Scheme_Summary_Report

@Entity
@Table(name = "Scheme_Summary")
@NamedStoredProcedureQuery(name = "callSchemeSummary", procedureName = "ARTICLE_Scheme_Summary_Report", parameters = {
		@StoredProcedureParameter(name = "TRD_SCH_SLNO", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_flag", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_id", mode = ParameterMode.IN, type = String.class) 
		}
, resultClasses = Scheme_Summary.class)



@NamedStoredProcedureQuery(name = "callSchemeSummary_drill_down", procedureName = "ARTICLE_SCHEME_SUMMARY_REPORT_WITHOUTFYR", parameters = {
		@StoredProcedureParameter(name = "TRD_SCH_SLNO", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_flag", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_id", mode = ParameterMode.IN, type = String.class) 
		}
, resultClasses = Scheme_Summary.class)

public class Scheme_Summary {

	@Id
	@Column(name = "ROW")
	private Long row;

	@Column(name = "TRD_SCH_SLNO")
	private Long trd_sch_slno;

	@Column(name = "TRD_SCHEME_NAME")
	private String trd_scheme_name;

	@Column(name = "VALID_FROM_DT")
	private String valid_from_dt;

	@Column(name = "VALID_TO_DT")
	private String valid_to_dt;
	
	
	@Column(name = "SALE_BLLED_QTY")
	private String sale_blled_qty;
	
	@Column(name = "ARTICLE_QTY")
	private String article_qty;
	

	@Column(name = "ARTICLE_NAME")
	private String article_name;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "SALE_PROD_ID")
	private Long sale_prod_id;

	@Column(name = "SALE_PROD_NAME")
	private String sale_prod_name;

	@Column(name = "SALE_QTY")
	private Long sale_qty;

	@Column(name = "SALE_VALUE")
	private BigDecimal sale_value;

	@Column(name = "ART_DSP_QTY")
	private Long art_dsp_qty;

	@Column(name = "ART_DSP_VALUE")
	private BigDecimal art_dsp_value;
	
	@Column(name = "ART_DSP_RATE")
	private BigDecimal art_dsp_rate;

	@Column(name = "ARTICLE_REQ_QTY")
	private Long article_req_qty;

	@Column(name = "ARTICLE_REQ_ITEM_VALUE")
	private BigDecimal  article_req_item_value;

	@Column(name = "REQ_PENDING_QTY")
	private Long req_pending_qty ;
	
	@Column(name = "TRD_SCHEME_CODE")
	private String trd_scheme_code;
	
	@Column(name = "CLOSING_STOCK")
	private BigDecimal closing_stock;
	
	
	

	@Transient private Long available_stock;
	
	@Transient private Date validity_extended_date;

	public Date getValidity_extended_date() {
		return validity_extended_date;
	}

	public void setValidity_extended_date(Date validity_extended_date) {
		this.validity_extended_date = validity_extended_date;
	}

	public Long getSale_prod_id() {
		return sale_prod_id;
	}

	public void setSale_prod_id(Long sale_prod_id) {
		this.sale_prod_id = sale_prod_id;
	}

	public BigDecimal getClosing_stock() {
		return closing_stock;
	}

	public void setClosing_stock(BigDecimal closing_stock) {
		this.closing_stock = closing_stock;
	}

	public Long getAvailable_stock() {
		return available_stock;
	}

	public void setAvailable_stock(Long available_stock) {
		this.available_stock = available_stock;
	}

	public String getTrd_scheme_code() {
		return trd_scheme_code;
	}

	public void setTrd_scheme_code(String trd_scheme_code) {
		this.trd_scheme_code = trd_scheme_code;
	}

	public Long getArticle_req_qty() {
		return article_req_qty;
	}

	public void setArticle_req_qty(Long article_req_qty) {
		this.article_req_qty = article_req_qty;
	}

	public BigDecimal getArticle_req_item_value() {
		return article_req_item_value;
	}

	public void setArticle_req_item_value(BigDecimal article_req_item_value) {
		this.article_req_item_value = article_req_item_value;
	}

	public Long getReq_pending_qty() {
		return req_pending_qty;
	}

	public void setReq_pending_qty(Long req_pending_qty) {
		this.req_pending_qty = req_pending_qty;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getTrd_sch_slno() {
		return trd_sch_slno;
	}

	public void setTrd_sch_slno(Long trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
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

	public String getArticle_name() {
		return article_name;
	}

	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}

	public String getSale_prod_name() {
		return sale_prod_name;
	}

	public void setSale_prod_name(String sale_prod_name) {
		this.sale_prod_name = sale_prod_name;
	}

	public Long getSale_qty() {
		return sale_qty;
	}

	public void setSale_qty(Long sale_qty) {
		this.sale_qty = sale_qty;
	}

	public BigDecimal getSale_value() {
		return sale_value;
	}

	public void setSale_value(BigDecimal sale_value) {
		this.sale_value = sale_value;
	}



	public Long getArt_dsp_qty() {
		return art_dsp_qty;
	}

	public void setArt_dsp_qty(Long art_dsp_qty) {
		this.art_dsp_qty = art_dsp_qty;
	}

	public BigDecimal getArt_dsp_value() {
		return art_dsp_value;
	}

	public void setArt_dsp_value(BigDecimal art_dsp_value) {
		this.art_dsp_value = art_dsp_value;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BigDecimal getArt_dsp_rate() {
		return art_dsp_rate;
	}

	public void setArt_dsp_rate(BigDecimal art_dsp_rate) {
		this.art_dsp_rate = art_dsp_rate;
	}

	
	
	public String getSale_blled_qty() {
		return sale_blled_qty;
	}

	public void setSale_blled_qty(String sale_blled_qty) {
		this.sale_blled_qty = sale_blled_qty;
	}

	public String getArticle_qty() {
		return article_qty;
	}

	public void setArticle_qty(String article_qty) {
		this.article_qty = article_qty;
	}

	@Override
	public String toString() {
		return "Scheme_Summary [row=" + row + ", trd_sch_slno=" + trd_sch_slno + ", trd_scheme_name=" + trd_scheme_name
				+ ", valid_from_dt=" + valid_from_dt + ", valid_to_dt=" + valid_to_dt + ", sale_blled_qty="
				+ sale_blled_qty + ", article_qty=" + article_qty + ", article_name=" + article_name + ", brand="
				+ brand + ", sale_prod_id=" + sale_prod_id + ", sale_prod_name=" + sale_prod_name + ", sale_qty="
				+ sale_qty + ", sale_value=" + sale_value + ", art_dsp_qty=" + art_dsp_qty + ", art_dsp_value="
				+ art_dsp_value + ", art_dsp_rate=" + art_dsp_rate + ", article_req_qty=" + article_req_qty
				+ ", article_req_item_value=" + article_req_item_value + ", req_pending_qty=" + req_pending_qty
				+ ", trd_scheme_code=" + trd_scheme_code + ", closing_stock=" + closing_stock + ", available_stock="
				+ available_stock + ", validity_extended_date=" + validity_extended_date + "]";
	}

	
	

	
	


	
	


}
