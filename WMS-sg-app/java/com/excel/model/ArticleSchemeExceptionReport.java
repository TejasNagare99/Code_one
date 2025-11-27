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
@Table(name = "ARTICLE_SCHEME_EXCEPTION_REPORT")
@NamedStoredProcedureQuery(name = "callARTICLE_SCHEME_EXCEPTION_REPORTT", 
	//procedureName = "STOCK_STATEMENT_FCODE",
	procedureName = "ARTICLE_SCHEME_EXCEPTION_REPORT",
	parameters = {
			@StoredProcedureParameter(name = "subcompid", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "frdt", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "todt" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "LOCID" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "FIN_YEAR_FLAG" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "FIN_YEAR_ID" ,mode = ParameterMode.IN,type=String.class),
	}, resultClasses = ArticleSchemeExceptionReport.class)
public class ArticleSchemeExceptionReport {
	
	@Id
	@Column(name="ROWNUM")
	private Long rownum;

	@Column(name="TRD_SCH_SLNO")
	private Long trd_sch_slno;

	@Column(name="ARTICLE_REQ_ID")
	private Long article_req_id;

	@Column(name="ARTICLE_REQ_DTL_ID")
	private Long article_req_dtl_id;

	@Column(name="ARTICLE_SCHM_NAME")
	private String article_schm_name;

	@Column(name="ARTICLE_NAME")
	private String article_name;

	@Column(name="ART_SALE_PROD_CD")
	private String art_sale_prod_cd;

	@Column(name="ART_SALE_PROD_NAME")
	private String art_sale_prod_name;

	@Column(name="ART_BILLED_QTY")
	private Long art_billed_qty;

	@Column(name="ART_QTY")
	private Long art_qty;

	@Column(name="INVOICE_NO")
	private String invoice_no;
	
	@Column(name="INVOICE_DATE")
	private String invoice_date;

	@Column(name="AP")
	private String ap;

	@Column(name="SAP_PLANT_CD")
	private String sap_plant_cd;

	@Column(name="COMPANY_CD")
	private String company_cd;

	@Column(name="CFA_LOCATION")
	private String cfa_location;

	@Column(name="SAP_INV_NO")
	private String sap_inv_no;

	@Column(name="SAP_INV_DT")
	private String sap_inv_dt;

	@Column(name="SAP_CUST_CD")
	private String sap_cust_cd;

	@Column(name="CUSTOMER_NAME")
	private String customer_name;

	@Column(name="CITY")
	private String city;

	@Column(name="SAP_SALE_PROD_CD")
	private String sap_sale_prod_cd;

	@Column(name="SAP_SALE_PROD_NME")
	private String sap_sale_prod_nme;

	@Column(name="BILLED_QTY")
	private Long billed_qty;

	@Column(name="FREE_QTY")
	private Long free_qty;

	@Column(name="BILLING_RATE")
	private BigDecimal billing_rate;

	@Column(name="BILLING_VALUE")
	private BigDecimal billing_value;

	@Column(name="ARTICLE_SALE_BILL_QTY_ENTERED")
	private Long article_sale_bill_qty_entered;

	@Column(name="ARTICLE_RATE")
	private BigDecimal article_rate;

	@Column(name="ARTICLE_VALUE")
	private BigDecimal article_value;

	@Column(name="ERR_BILL_QTY")
	private Long err_bill_qty;
	
	@Column(name="REQ_ARTICLE_QTY")
	private Long req_article_qty;
	
	@Column(name="DSP_CHALLAN_NO")
	private String dsp_challan_no;
	
	@Column(name="CALC_REQUEST_QTY")
	private Long calc_request_qty;
	
	@Column(name="ARTICLE_QTY_DIFF")
	private BigDecimal article_qty_diff;
	
	@Column(name="DSP_DT")
	private String dsp_dt;
	
	@Column(name="DISCRIP_MSG")
	private String discrip_msg;

	@Column(name="CORR_IND")
	private String corr_ind;
	
	@Column(name="CORR_ARTICLE_QTY")
	private BigDecimal corr_article_qty;
	
	@Column(name="TRN_ACK_NO")
	private String trn_ack_no;

	@Column(name="TRN_ACK_DATE")
	private String trn_ack_date;
	
	@Column(name="TRN_IRN_NUMBER")
	private String trn_irn_number;

	@Column(name="TRN_EWAYBILLNO")
	private String trn_ewaybillno;

	@Column(name="TRN_EWAYBILLDT")
	private String trn_ewaybilldt;
	
	@Column(name="ARTICLE_REQ_DATE")
	private String article_req_date;

	public String getArticle_req_date() {
		return article_req_date;
	}

	public void setArticle_req_date(String article_req_date) {
		this.article_req_date = article_req_date;
	}

	public String getTrn_ack_no() {
		return trn_ack_no;
	}

	public void setTrn_ack_no(String trn_ack_no) {
		this.trn_ack_no = trn_ack_no;
	}

	public String getTrn_ack_date() {
		return trn_ack_date;
	}

	public void setTrn_ack_date(String trn_ack_date) {
		this.trn_ack_date = trn_ack_date;
	}

	public String getTrn_irn_number() {
		return trn_irn_number;
	}

	public void setTrn_irn_number(String trn_irn_number) {
		this.trn_irn_number = trn_irn_number;
	}

	public String getTrn_ewaybillno() {
		return trn_ewaybillno;
	}

	public void setTrn_ewaybillno(String trn_ewaybillno) {
		this.trn_ewaybillno = trn_ewaybillno;
	}

	public String getTrn_ewaybilldt() {
		return trn_ewaybilldt;
	}

	public void setTrn_ewaybilldt(String trn_ewaybilldt) {
		this.trn_ewaybilldt = trn_ewaybilldt;
	}

	public BigDecimal getCorr_article_qty() {
		return corr_article_qty;
	}

	public void setCorr_article_qty(BigDecimal corr_article_qty) {
		this.corr_article_qty = corr_article_qty;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getCorr_ind() {
		return corr_ind;
	}

	public void setCorr_ind(String corr_ind) {
		this.corr_ind = corr_ind;
	}

	public String getDsp_dt() {
		return dsp_dt;
	}

	public void setDsp_dt(String dsp_dt) {
		this.dsp_dt = dsp_dt;
	}

	public String getDiscrip_msg() {
		return discrip_msg;
	}

	public void setDiscrip_msg(String discrip_msg) {
		this.discrip_msg = discrip_msg;
	}

	public Long getCalc_request_qty() {
		return calc_request_qty;
	}

	public void setCalc_request_qty(Long calc_request_qty) {
		this.calc_request_qty = calc_request_qty;
	}

	public BigDecimal getArticle_qty_diff() {
		return article_qty_diff;
	}

	public void setArticle_qty_diff(BigDecimal article_qty_diff) {
		this.article_qty_diff = article_qty_diff;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public Long getReq_article_qty() {
		return req_article_qty;
	}

	public void setReq_article_qty(Long req_article_qty) {
		this.req_article_qty = req_article_qty;
	}

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}

	public Long getTrd_sch_slno() {
		return trd_sch_slno;
	}

	public void setTrd_sch_slno(Long trd_sch_slno) {
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

	public Long getArt_billed_qty() {
		return art_billed_qty;
	}

	public void setArt_billed_qty(Long art_billed_qty) {
		this.art_billed_qty = art_billed_qty;
	}

	public Long getArt_qty() {
		return art_qty;
	}

	public void setArt_qty(Long art_qty) {
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

	public String getSap_inv_dt() {
		return sap_inv_dt;
	}

	public void setSap_inv_dt(String sap_inv_dt) {
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

	public String getSap_sale_prod_nme() {
		return sap_sale_prod_nme;
	}

	public void setSap_sale_prod_nme(String sap_sale_prod_nme) {
		this.sap_sale_prod_nme = sap_sale_prod_nme;
	}

	public Long getBilled_qty() {
		return billed_qty;
	}

	public void setBilled_qty(Long billed_qty) {
		this.billed_qty = billed_qty;
	}

	public Long getFree_qty() {
		return free_qty;
	}

	public void setFree_qty(Long free_qty) {
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

	public Long getArticle_sale_bill_qty_entered() {
		return article_sale_bill_qty_entered;
	}

	public void setArticle_sale_bill_qty_entered(Long article_sale_bill_qty_entered) {
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

	public Long getErr_bill_qty() {
		return err_bill_qty;
	}

	public void setErr_bill_qty(Long err_bill_qty) {
		this.err_bill_qty = err_bill_qty;
	}

	

	

	@Override
	public String toString() {
		return "ArticleSchemeExceptionReport [rownum=" + rownum + ", trd_sch_slno=" + trd_sch_slno + ", article_req_id="
				+ article_req_id + ", article_req_dtl_id=" + article_req_dtl_id + ", article_schm_name="
				+ article_schm_name + ", article_name=" + article_name + ", art_sale_prod_cd=" + art_sale_prod_cd
				+ ", art_sale_prod_name=" + art_sale_prod_name + ", art_billed_qty=" + art_billed_qty + ", art_qty="
				+ art_qty + ", invoice_no=" + invoice_no + ", invoice_date=" + invoice_date + ", ap=" + ap
				+ ", sap_plant_cd=" + sap_plant_cd + ", company_cd=" + company_cd + ", cfa_location=" + cfa_location
				+ ", sap_inv_no=" + sap_inv_no + ", sap_inv_dt=" + sap_inv_dt + ", sap_cust_cd=" + sap_cust_cd
				+ ", customer_name=" + customer_name + ", city=" + city + ", sap_sale_prod_cd=" + sap_sale_prod_cd
				+ ", sap_sale_prod_nme=" + sap_sale_prod_nme + ", billed_qty=" + billed_qty + ", free_qty=" + free_qty
				+ ", billing_rate=" + billing_rate + ", billing_value=" + billing_value
				+ ", article_sale_bill_qty_entered=" + article_sale_bill_qty_entered + ", article_rate=" + article_rate
				+ ", article_value=" + article_value + ", err_bill_qty=" + err_bill_qty + ", req_article_qty="
				+ req_article_qty + ", dsp_challan_no=" + dsp_challan_no + ", calc_request_qty=" + calc_request_qty
				+ ", article_qty_diff=" + article_qty_diff + ", dsp_dt=" + dsp_dt + ", discrip_msg=" + discrip_msg
				+ ", corr_ind=" + corr_ind + ", corr_article_qty=" + corr_article_qty + ", trn_ack_no=" + trn_ack_no
				+ ", trn_ack_date=" + trn_ack_date + ", trn_irn_number=" + trn_irn_number + ", trn_ewaybillno="
				+ trn_ewaybillno + ", trn_ewaybilldt=" + trn_ewaybilldt + ", article_req_date=" + article_req_date
				+ ", getArticle_req_date()=" + getArticle_req_date() + ", getTrn_ack_no()=" + getTrn_ack_no()
				+ ", getTrn_ack_date()=" + getTrn_ack_date() + ", getTrn_irn_number()=" + getTrn_irn_number()
				+ ", getTrn_ewaybillno()=" + getTrn_ewaybillno() + ", getTrn_ewaybilldt()=" + getTrn_ewaybilldt()
				+ ", getCorr_article_qty()=" + getCorr_article_qty() + ", getInvoice_date()=" + getInvoice_date()
				+ ", getCorr_ind()=" + getCorr_ind() + ", getDsp_dt()=" + getDsp_dt() + ", getDiscrip_msg()="
				+ getDiscrip_msg() + ", getCalc_request_qty()=" + getCalc_request_qty() + ", getArticle_qty_diff()="
				+ getArticle_qty_diff() + ", getDsp_challan_no()=" + getDsp_challan_no() + ", getReq_article_qty()="
				+ getReq_article_qty() + ", getRownum()=" + getRownum() + ", getTrd_sch_slno()=" + getTrd_sch_slno()
				+ ", getArticle_req_id()=" + getArticle_req_id() + ", getArticle_req_dtl_id()="
				+ getArticle_req_dtl_id() + ", getArticle_schm_name()=" + getArticle_schm_name()
				+ ", getArticle_name()=" + getArticle_name() + ", getArt_sale_prod_cd()=" + getArt_sale_prod_cd()
				+ ", getArt_sale_prod_name()=" + getArt_sale_prod_name() + ", getArt_billed_qty()="
				+ getArt_billed_qty() + ", getArt_qty()=" + getArt_qty() + ", getInvoice_no()=" + getInvoice_no()
				+ ", getAp()=" + getAp() + ", getSap_plant_cd()=" + getSap_plant_cd() + ", getCompany_cd()="
				+ getCompany_cd() + ", getCfa_location()=" + getCfa_location() + ", getSap_inv_no()=" + getSap_inv_no()
				+ ", getSap_inv_dt()=" + getSap_inv_dt() + ", getSap_cust_cd()=" + getSap_cust_cd()
				+ ", getCustomer_name()=" + getCustomer_name() + ", getCity()=" + getCity() + ", getSap_sale_prod_cd()="
				+ getSap_sale_prod_cd() + ", getSap_sale_prod_nme()=" + getSap_sale_prod_nme() + ", getBilled_qty()="
				+ getBilled_qty() + ", getFree_qty()=" + getFree_qty() + ", getBilling_rate()=" + getBilling_rate()
				+ ", getBilling_value()=" + getBilling_value() + ", getArticle_sale_bill_qty_entered()="
				+ getArticle_sale_bill_qty_entered() + ", getArticle_rate()=" + getArticle_rate()
				+ ", getArticle_value()=" + getArticle_value() + ", getErr_bill_qty()=" + getErr_bill_qty()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public ArticleSchemeExceptionReport(String dsp_challan_no, String dsp_dt, String discrip_msg, String corr_ind,
			BigDecimal corr_article_qty,Long req_article_qty) {
		super();
		this.dsp_challan_no = dsp_challan_no;
		this.dsp_dt = dsp_dt;
		this.discrip_msg = discrip_msg;
		this.corr_ind = corr_ind;
		this.corr_article_qty = corr_article_qty;
		this.req_article_qty = req_article_qty;
	}

	public ArticleSchemeExceptionReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	

}
