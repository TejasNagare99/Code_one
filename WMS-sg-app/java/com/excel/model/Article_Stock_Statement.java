package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "Article_Stock_Statement")
@NamedStoredProcedureQuery(name = "callARTICLE_STOCK_STATEMENT",
		// procedureName = "STOCK_STATEMENT_FCODE",
		procedureName = "ARTICLE_STOCK_STATEMENT", parameters = {
				@StoredProcedureParameter(name = "div_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "loc_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "nilstock", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "exp_ind", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "USER_ID", mode = ParameterMode.IN, type = String.class) ,
				@StoredProcedureParameter(name = "fin_year_flag", mode = ParameterMode.IN, type = String.class) ,
				@StoredProcedureParameter(name = "fin_year_id", mode = ParameterMode.IN, type = String.class) },
		resultClasses = Article_Stock_Statement.class)
public class Article_Stock_Statement {

	@Id
	@Column(name = "ROW")
	private Long row;

	@Column(name = "TEAM")
	private String team;

	@Column(name = "LOC_ID")
	private Long loc_id;

	@Column(name = "LOC_NAME")
	private String loc_name;

	@Column(name = "SMP_PROD_ID")
	private Long smp_prod_id;

	@Column(name = "PRODUCT_CODE")
	private String product_code;

	@Column(name = "ARTICLE_NAME")
	private String article_name;

	@Column(name = "BATCH_NO")
	private String batch_no;

	@Column(name = "UNIT_PRICE")
	private BigDecimal unit_price;

	@Column(name = "OPENING_QTY")
	private BigDecimal opening_qty;

	@Column(name = "OPENING_VALUE")
	private BigDecimal opening_value;

	@Column(name = "GRN_QTY")
	private BigDecimal grn_qty;

	@Column(name = "GRN_VALUE")
	private BigDecimal grn_value;

	@Column(name = "DSP_QTY")
	private BigDecimal dsp_qty;

	@Column(name = "DSP_VALUE")
	private BigDecimal dsp_value;

	@Column(name = "CL_STOCK_QTY")
	private BigDecimal cl_stock_qty;

	@Column(name = "CL_STOCK_VAL")
	private BigDecimal cl_stock_val;

//	@Column(name = "LINK_SCHEME_NAME")   
//	private String link_scheme_name;
//
//	@Column(name = "VALID_FROM")   
//	private String valid_from;
//
//	@Column(name = "VALID_TO")
//	private String valid_to;

	@Column(name = "WITH_HELD_QTY")
	private BigDecimal with_held_qty;

	@Column(name = "MFG_DATE")
	private String mfg_date;

	@Column(name = "EXPRY_DATE")
	private String expry_date;

	@Column(name = "PENDING")
	private Long pending;

	@Column(name = "GRNQTY")
	private BigDecimal grnqty;

	@Column(name = "DISP_QTY")
	private BigDecimal disp_qty;

	@Column(name = "ADJDTL_IN_QTY")
	private BigDecimal adjdtl_in_qty;

	@Column(name = "ADJDTL_OUT_QTY")
	private BigDecimal adjdtl_out_qty;

	@Column(name = "TRF_OUT_QTY")
	private BigDecimal trf_out_qty;

	public BigDecimal getTrf_out_qty() {
		return trf_out_qty;
	}

	public void setTrf_out_qty(BigDecimal trf_out_qty) {
		this.trf_out_qty = trf_out_qty;
	}

	public Long getPending() {
		return pending;
	}

	public void setPending(Long pending) {
		this.pending = pending;
	}

	public BigDecimal getGrnqty() {
		return grnqty;
	}

	public void setGrnqty(BigDecimal grnqty) {
		this.grnqty = grnqty;
	}

	public BigDecimal getDisp_qty() {
		return disp_qty;
	}

	public void setDisp_qty(BigDecimal disp_qty) {
		this.disp_qty = disp_qty;
	}

	public BigDecimal getAdjdtl_in_qty() {
		return adjdtl_in_qty;
	}

	public void setAdjdtl_in_qty(BigDecimal adjdtl_in_qty) {
		this.adjdtl_in_qty = adjdtl_in_qty;
	}

	public BigDecimal getAdjdtl_out_qty() {
		return adjdtl_out_qty;
	}

	public void setAdjdtl_out_qty(BigDecimal adjdtl_out_qty) {
		this.adjdtl_out_qty = adjdtl_out_qty;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public Long getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getArticle_name() {
		return article_name;
	}

	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public BigDecimal getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}

	public BigDecimal getOpening_qty() {
		return opening_qty;
	}

	public void setOpening_qty(BigDecimal opening_qty) {
		this.opening_qty = opening_qty;
	}

	public BigDecimal getOpening_value() {
		return opening_value;
	}

	public void setOpening_value(BigDecimal opening_value) {
		this.opening_value = opening_value;
	}

	public BigDecimal getGrn_qty() {
		return grn_qty;
	}

	public void setGrn_qty(BigDecimal grn_qty) {
		this.grn_qty = grn_qty;
	}

	public BigDecimal getGrn_value() {
		return grn_value;
	}

	public void setGrn_value(BigDecimal grn_value) {
		this.grn_value = grn_value;
	}

	public BigDecimal getDsp_qty() {
		return dsp_qty;
	}

	public void setDsp_qty(BigDecimal dsp_qty) {
		this.dsp_qty = dsp_qty;
	}

	public BigDecimal getDsp_value() {
		return dsp_value;
	}

	public void setDsp_value(BigDecimal dsp_value) {
		this.dsp_value = dsp_value;
	}

	public BigDecimal getCl_stock_qty() {
		return cl_stock_qty;
	}

	public void setCl_stock_qty(BigDecimal cl_stock_qty) {
		this.cl_stock_qty = cl_stock_qty;
	}

	public BigDecimal getCl_stock_val() {
		return cl_stock_val;
	}

	public void setCl_stock_val(BigDecimal cl_stock_val) {
		this.cl_stock_val = cl_stock_val;
	}

//	public String getLink_scheme_name() {
//		return link_scheme_name;
//	}
//
//	public void setLink_scheme_name(String link_scheme_name) {
//		this.link_scheme_name = link_scheme_name;
//	}
//
//	public String getValid_from() {
//		return valid_from;
//	}
//
//	public void setValid_from(String valid_from) {
//		this.valid_from = valid_from;
//	}
//
//	public String getValid_to() {
//		return valid_to;
//	}
//
//	public void setValid_to(String valid_to) {
//		this.valid_to = valid_to;
//	}

	public BigDecimal getWith_held_qty() {
		return with_held_qty;
	}

	public void setWith_held_qty(BigDecimal with_held_qty) {
		this.with_held_qty = with_held_qty;
	}

	public String getMfg_date() {
		return mfg_date;
	}

	public void setMfg_date(String mfg_date) {
		this.mfg_date = mfg_date;
	}

	public String getExpry_date() {
		return expry_date;
	}

	public void setExpry_date(String expry_date) {
		this.expry_date = expry_date;
	}

	@Override
	public String toString() {
		return "Article_Stock_Statement [row=" + row + ", team=" + team + ", loc_id=" + loc_id + ", loc_name="
				+ loc_name + ", smp_prod_id=" + smp_prod_id + ", product_code=" + product_code + ", article_name="
				+ article_name + ", batch_no=" + batch_no + ", unit_price=" + unit_price + ", opening_qty="
				+ opening_qty + ", opening_value=" + opening_value + ", grn_qty=" + grn_qty + ", grn_value=" + grn_value
				+ ", dsp_qty=" + dsp_qty + ", dsp_value=" + dsp_value + ", cl_stock_qty=" + cl_stock_qty
				+ ", cl_stock_val=" + cl_stock_val + ", link_scheme_name=" + with_held_qty + ", mfg_date=" + mfg_date
				+ ", expry_date=" + expry_date + ", pending=" + pending + ", grnqty=" + grnqty + ", disp_qty="
				+ disp_qty + ", adjdtl_in_qty=" + adjdtl_in_qty + ", adjdtl_out_qty=" + adjdtl_out_qty + "]";
	}

}
