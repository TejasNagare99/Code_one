package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TRD_SCHEME_MST_HDR")
public class trd_scheme_mst_hdr {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRD_SCH_SLNO")
	private Long trd_sch_slno;

	@Column(name = "COMPANY_CD")
	private String company_cd;

	@Column(name = "SUB_COMP_CD")
	private String sub_comp_cd;

	@Column(name = "SUB_COMP_ID")
	private long sub_comp_id;

	@Column(name = "SCHEME_APPTYPE")
	private String scheme_apptype;

	@Column(name = "LOC_ID")
	private long loc_id;

	@Column(name = "TRD_SCHEME_ID")
	private String trd_scheme_id;

	@Column(name = "TRD_SCHEME_CODE")
	private String trd_scheme_code;

	@Column(name = "TRD_SCHEME_NAME")
	private String trd_scheme_name;

	@Column(name = "TRD_SCHEME_DESCR")
	private String trd_scheme_descr;

	@Column(name = "VALID_FROM_DT")
	private Date valid_from_dt;

	@Column(name = "VALID_TO_DT")
	private Date valid_to_dt;

	@Column(name = "APPLY_TO")
	private String apply_to;

	@Column(name = "BILLED_VALUE")
	private long billed_value;

	@Column(name = "TRD_SCH_TYPE")
	private String trd_sch_type;

	@Column(name = "INVOICE_TYPE")
	private String invoice_type;

	@Column(name = "ARTICLE_PROD_CD")
	private String article_prod_cd;

	@Column(name = "ARTICLE_PROD_ID")
	private long article_prod_id;

	@Column(name = "ARTICLE_QTY_PER_TOT_VAL")
	private long article_qty_per_tot_val;

	@Column(name = "ARTICLE_PROD_RATE")
	private BigDecimal article_prod_rate;

	@Column(name = "CREATED_BY")
	private String created_by;

	@Column(name = "CREATED_DATE")
	private Date created_date;

	@Column(name = "LAST_MOD_BY")
	private String last_mod_by;

	@Column(name = "LAST_MOD_DATE")
	private Date last_mod_date;

	@Column(name = "TRD_SCH_STATUS")
	private String trd_sch_status;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "FINYEAR")
	private String finyear;

	@Column(name = "APPLY_INV_FROM")
	private Date apply_inv_from;

	@Column(name = "SPECIFIC_BRAND_SCHEME")
	private String specific_brand_scheme;

	@Column(name = "SCHEME_DIV_ID")
	private long scheme_div_id;
	
	@Column(name = "cycle_no")
	private long cycle_no;
	
	
	@Column(name = "GRN_TOT_QTY")
	private long grn__tot_qty;

	@Column(name = "CLOSURE_DATE")
	private Date closure_date;

	public Date getClosure_date() {
		return closure_date;
	}

	public void setClosure_date(Date closure_date) {
		this.closure_date = closure_date;
	}

	public long getCycle_no() {
		return cycle_no;
	}

	public long getGrn__tot_qty() {
		return grn__tot_qty;
	}

	public void setGrn__tot_qty(long grn__tot_qty) {
		this.grn__tot_qty = grn__tot_qty;
	}

	public void setCycle_no(long cycle_no) {
		this.cycle_no = cycle_no;
	}

	public String getSpecific_brand_scheme() {
		return specific_brand_scheme;
	}

	public void setSpecific_brand_scheme(String specific_brand_scheme) {
		this.specific_brand_scheme = specific_brand_scheme;
	}

	public long getScheme_div_id() {
		return scheme_div_id;
	}

	public void setScheme_div_id(long scheme_div_id) {
		this.scheme_div_id = scheme_div_id;
	}

	public Date getApply_inv_from() {
		return apply_inv_from;
	}

	public void setApply_inv_from(Date apply_inv_from) {
		this.apply_inv_from = apply_inv_from;
	}

	@Transient
	private String loc_nm;
	@Transient
	private String SALE_PROD_NAME;
	@Transient
	private String SMP_PROD_NAM;

	public Long getTrd_sch_slno() {
		return trd_sch_slno;
	}

	public void setTrd_sch_slno(Long trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getSub_comp_cd() {
		return sub_comp_cd;
	}

	public void setSub_comp_cd(String sub_comp_cd) {
		this.sub_comp_cd = sub_comp_cd;
	}

	public long getSub_comp_id() {
		return sub_comp_id;
	}

	public void setSub_comp_id(long sub_comp_id) {
		this.sub_comp_id = sub_comp_id;
	}

	public String getScheme_apptype() {
		return scheme_apptype;
	}

	public void setScheme_apptype(String scheme_apptype) {
		this.scheme_apptype = scheme_apptype;
	}

	public long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(long loc_id) {
		this.loc_id = loc_id;
	}

	public String getTrd_scheme_id() {
		return trd_scheme_id;
	}

	public void setTrd_scheme_id(String trd_scheme_id) {
		this.trd_scheme_id = trd_scheme_id;
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

	public String getTrd_scheme_descr() {
		return trd_scheme_descr;
	}

	public void setTrd_scheme_descr(String trd_scheme_descr) {
		this.trd_scheme_descr = trd_scheme_descr;
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

	public long getBilled_value() {
		return billed_value;
	}

	public void setBilled_value(long billed_value) {
		this.billed_value = billed_value;
	}

	public String getTrd_sch_type() {
		return trd_sch_type;
	}

	public void setTrd_sch_type(String trd_sch_type) {
		this.trd_sch_type = trd_sch_type;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getArticle_prod_cd() {
		return article_prod_cd;
	}

	public void setArticle_prod_cd(String article_prod_cd) {
		this.article_prod_cd = article_prod_cd;
	}

	public long getArticle_prod_id() {
		return article_prod_id;
	}

	public void setArticle_prod_id(long article_prod_id) {
		this.article_prod_id = article_prod_id;
	}

	public long getArticle_qty_per_tot_val() {
		return article_qty_per_tot_val;
	}

	public void setArticle_qty_per_tot_val(long article_qty_per_tot_val) {
		this.article_qty_per_tot_val = article_qty_per_tot_val;
	}

	public BigDecimal getArticle_prod_rate() {
		return article_prod_rate;
	}

	public void setArticle_prod_rate(BigDecimal article_prod_rate) {
		this.article_prod_rate = article_prod_rate;
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

	public String getTrd_sch_status() {
		return trd_sch_status;
	}

	public void setTrd_sch_status(String trd_sch_status) {
		this.trd_sch_status = trd_sch_status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFinyear() {
		return finyear;
	}

	public void setFinyear(String finyear) {
		this.finyear = finyear;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getSALE_PROD_NAME() {
		return SALE_PROD_NAME;
	}

	public void setSALE_PROD_NAME(String sALE_PROD_NAME) {
		SALE_PROD_NAME = sALE_PROD_NAME;
	}

	public String getSMP_PROD_NAM() {
		return SMP_PROD_NAM;
	}

	public void setSMP_PROD_NAM(String sMP_PROD_NAM) {
		SMP_PROD_NAM = sMP_PROD_NAM;
	}

	@Override
	public String toString() {
		return "trd_scheme_mst_hdr [trd_sch_slno=" + trd_sch_slno + ", company_cd=" + company_cd + ", sub_comp_cd="
				+ sub_comp_cd + ", sub_comp_id=" + sub_comp_id + ", scheme_apptype=" + scheme_apptype + ", loc_id="
				+ loc_id + ", trd_scheme_id=" + trd_scheme_id + ", trd_scheme_code=" + trd_scheme_code
				+ ", trd_scheme_name=" + trd_scheme_name + ", trd_scheme_descr=" + trd_scheme_descr + ", valid_from_dt="
				+ valid_from_dt + ", valid_to_dt=" + valid_to_dt + ", apply_to=" + apply_to + ", billed_value="
				+ billed_value + ", trd_sch_type=" + trd_sch_type + ", invoice_type=" + invoice_type
				+ ", article_prod_cd=" + article_prod_cd + ", article_prod_id=" + article_prod_id
				+ ", article_qty_per_tot_val=" + article_qty_per_tot_val + ", article_prod_rate=" + article_prod_rate
				+ ", created_by=" + created_by + ", created_date=" + created_date + ", last_mod_by=" + last_mod_by
				+ ", last_mod_date=" + last_mod_date + ", trd_sch_status=" + trd_sch_status + ", remarks=" + remarks
				+ ", finyear=" + finyear + ", loc_nm=" + loc_nm + ", SALE_PROD_NAME=" + SALE_PROD_NAME
				+ ", SMP_PROD_NAM=" + SMP_PROD_NAM + "]";
	}

}
