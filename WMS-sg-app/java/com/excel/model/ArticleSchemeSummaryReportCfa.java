package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ARTICLE_SCHEME_SUMMARY_REPORT_CFA")
@NamedStoredProcedureQuery(name = "callARTICLE_SCHEME_SUMMARY_REPORT_CFA", 
	//procedureName = "STOCK_STATEMENT_FCODE",
//	procedureName = "ARTICLE_SCHEME_SUMMARY_REPORT_CFA",
	procedureName = "ARTICLE_SCHEME_SUMMARY_REPORT_CFA_WITHOUTFYR",
	parameters = {
			@StoredProcedureParameter(name = "TRD_SCH_SLNO", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "CFA", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "SALEPROD" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class),
	}, resultClasses = ArticleSchemeSummaryReportCfa.class)
public class ArticleSchemeSummaryReportCfa {

	@Id
	@Column(name="ROW")
	private Long row;

	@Column(name="TRD_SCH_SLNO")
	private Long trd_sch_slno;

	@Column(name="TRD_SCHEME_CODE")
	private String trd_scheme_code;

	@Column(name="TRD_SCHEME_NAME")
	private String trd_scheme_name;

	@Column(name="VALID_FROM_DT")
	private String valid_from_dt;

	@Column(name="VALID_TO_DT")
	private String valid_to_dt;

	@Column(name="LOC_ID")
	private Long loc_id;

	@Column(name="CFA_LOCATION")
	private String cfa_location;

	@Column(name="SMP_PROD_ID")
	private Long smp_prod_id;

	@Column(name="ARTICLE_NAME")
	private String article_name;

	@Column(name="BRAND")
	private String brand;

	@Column(name="SALE_PROD_ID")
	private Long sale_prod_id;

	@Column(name="SALE_PROD_NAME")
	private String sale_prod_name;

	@Column(name="SALE_QTY")
	private Long sale_qty;

	@Column(name="SALE_VALUE")
	private BigDecimal sale_value;

	@Column(name="ART_DSP_QTY")
	private BigDecimal art_dsp_qty;

	@Column(name="ART_DSP_VALUE")
	private BigDecimal art_dsp_value;

	@Column(name="ART_DSP_RATE")
	private BigDecimal art_dsp_rate;

	@Column(name="ARTICLE_REQ_QTY")
	private Long article_req_qty;

	@Column(name="ARTICLE_REQ_ITEM_VALUE")
	private BigDecimal article_req_item_value;

	@Column(name="CLOSING_STOCK")
	private BigDecimal closing_stock;
	
	@Transient private Long req_pending_qty;
	@Transient private Long available_stock;
	
	public Long getAvailable_stock() {
		return available_stock;
	}

	public void setAvailable_stock(Long available_stock) {
		this.available_stock = available_stock;
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

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getCfa_location() {
		return cfa_location;
	}

	public void setCfa_location(String cfa_location) {
		this.cfa_location = cfa_location;
	}

	public Long getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getArticle_name() {
		return article_name;
	}

	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getSale_prod_id() {
		return sale_prod_id;
	}

	public void setSale_prod_id(Long sale_prod_id) {
		this.sale_prod_id = sale_prod_id;
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

	public BigDecimal getArt_dsp_qty() {
		return art_dsp_qty;
	}

	public void setArt_dsp_qty(BigDecimal art_dsp_qty) {
		this.art_dsp_qty = art_dsp_qty;
	}

	public BigDecimal getArt_dsp_value() {
		return art_dsp_value;
	}

	public void setArt_dsp_value(BigDecimal art_dsp_value) {
		this.art_dsp_value = art_dsp_value;
	}

	public BigDecimal getArt_dsp_rate() {
		return art_dsp_rate;
	}

	public void setArt_dsp_rate(BigDecimal art_dsp_rate) {
		this.art_dsp_rate = art_dsp_rate;
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

	public BigDecimal getClosing_stock() {
		return closing_stock;
	}

	public void setClosing_stock(BigDecimal closing_stock) {
		this.closing_stock = closing_stock;
	}

	@Override
	public String toString() {
		return "ArticleSchemeSummaryReportCfa [row=" + row + ", trd_sch_slno=" + trd_sch_slno + ", trd_scheme_code="
				+ trd_scheme_code + ", trd_scheme_name=" + trd_scheme_name + ", valid_from_dt=" + valid_from_dt
				+ ", valid_to_dt=" + valid_to_dt + ", loc_id=" + loc_id + ", cfa_location=" + cfa_location
				+ ", smp_prod_id=" + smp_prod_id + ", article_name=" + article_name + ", brand=" + brand
				+ ", sale_prod_id=" + sale_prod_id + ", sale_prod_name=" + sale_prod_name + ", sale_qty=" + sale_qty
				+ ", sale_value=" + sale_value + ", art_dsp_qty=" + art_dsp_qty + ", art_dsp_value=" + art_dsp_value
				+ ", art_dsp_rate=" + art_dsp_rate + ", article_req_qty=" + article_req_qty
				+ ", article_req_item_value=" + article_req_item_value + ", closing_stock=" + closing_stock + "]";
	}
	
	
}