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
@Table(name="ARTICLE_SCHEME_REQ_DETAILS")

@NamedStoredProcedureQuery(name = "callartereq_to_dispatch_direct_to_stockist_report_with_paramNewHeader",
procedureName = "ARTICLE_SCHEME_REQ_DETAILS",
	parameters= {
			@StoredProcedureParameter(name = "piLOC_id" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "startdate" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "enddate" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MCustomer_CD" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MSAP_Invoice" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MLR_No" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MArticle_SchemeID" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MArticle_PROD_CD" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MSales_Product" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "FIN_YEAR_FLAG" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "FIN_YEAR_ID" ,mode = ParameterMode.IN,type=String.class),
			
		
	}, resultClasses = New_Stockist_wiseScheme_request_model.class)

public class New_Stockist_wiseScheme_request_model {

	@Id
	@Column(name="ROW")
	private Long ROW;
	
	@Column(name="LOC_NM")
	private String loc_nm;
	
	@Column(name="ARTICLE_REQ_ID")
	private Long article_req_id;
	
	@Column(name="INVOICE_NO")
	private String invoice_no;
	
	@Column(name="INVOICE_DATE")
	private String invoice_date;
	
	@Column(name="CUST_ID")
	private String cust_id;
	
	@Column(name="CUST_NAME_BILLTO")
	private String cust_name_billto;
	
	@Column(name="CUST_NAME_SHIPTO")
	private String cust_name_shipto;
	
	@Column(name="DESTINATION")
	private String destination;
	
	@Column(name="SALE_PROD_CODE_SG")
	private String sale_prod_code_sg;
	
	@Column(name="SALE_PROD_NAME")
	private String sale_prod_name;
	
	@Column(name="SALE_PROD_QTY_BILLED")
	private Long sale_prod_qty_billed;
	
	@Column(name="SALE_PROD_QTY_FREE")
	private Long sale_prod_qty_free;
	
	@Column(name="SALE_BILLED_VALUE")
	private BigDecimal sale_billed_value;
	
	@Column(name="TRD_SCHEME_NAME")
	private String trd_scheme_name;
	
	@Column(name="ARTICLE_PROD_ID")
	private String article_prod_id;
	
	@Column(name="ARTICLE_PROD_CD")
	private String article_prod_cd;
	
	@Column(name="ARTICLE_NAME")
	private String article_name;
	
	@Column(name="QTY_REQD")
	private Long qty_reqd;
	
	@Column(name="ERP_CUST_CD")
	private String erp_cust_cd;
	
	@Column(name="CUST_CODE_SHIPTO")
	private String cust_code_shipto;
	
	@Column(name="LR_NUMBER")
	private String lr_number;
	
	@Column(name="SHIPTO_STATE_NAME")
	private String shipto_state_name;
	
	@Column(name="DESTINATION_BILLTO")
	private String destination_billto;
	
	@Column(name="REQUEST_NO")
	private String request_no;
	
	@Column(name="ARTICLE_REQ_DATE")
	private String article_req_date;
	
	@Column(name="TRD_SCHEME_CODE")
	private String trd_scheme_code;
	
	@Column(name="ARTICLE_REQ_DTL_ID")
	private String article_req_dtl_id;
	
	@Column(name="ALLOC_ID")
	private String alloc_id;
	
	@Column(name="ALLOCDTL_ID")
	private String allocdtl_id;
	
	

	public Long getROW() {
		return ROW;
	}

	public void setROW(Long rOW) {
		ROW = rOW;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public Long getArticle_req_id() {
		return article_req_id;
	}

	public void setArticle_req_id(Long article_req_id) {
		this.article_req_id = article_req_id;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getCust_name_billto() {
		return cust_name_billto;
	}

	public void setCust_name_billto(String cust_name_billto) {
		this.cust_name_billto = cust_name_billto;
	}

	public String getCust_name_shipto() {
		return cust_name_shipto;
	}

	public void setCust_name_shipto(String cust_name_shipto) {
		this.cust_name_shipto = cust_name_shipto;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
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

	public BigDecimal getSale_billed_value() {
		return sale_billed_value;
	}

	public void setSale_billed_value(BigDecimal sale_billed_value) {
		this.sale_billed_value = sale_billed_value;
	}

	public String getTrd_scheme_name() {
		return trd_scheme_name;
	}

	public void setTrd_scheme_name(String trd_scheme_name) {
		this.trd_scheme_name = trd_scheme_name;
	}

	public String getArticle_prod_id() {
		return article_prod_id;
	}

	public void setArticle_prod_id(String article_prod_id) {
		this.article_prod_id = article_prod_id;
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

	public Long getQty_reqd() {
		return qty_reqd;
	}

	public void setQty_reqd(Long qty_reqd) {
		this.qty_reqd = qty_reqd;
	}

	public String getErp_cust_cd() {
		return erp_cust_cd;
	}

	public void setErp_cust_cd(String erp_cust_cd) {
		this.erp_cust_cd = erp_cust_cd;
	}

	public String getCust_code_shipto() {
		return cust_code_shipto;
	}

	public void setCust_code_shipto(String cust_code_shipto) {
		this.cust_code_shipto = cust_code_shipto;
	}

	public String getLr_number() {
		return lr_number;
	}

	public void setLr_number(String lr_number) {
		this.lr_number = lr_number;
	}

	public String getShipto_state_name() {
		return shipto_state_name;
	}

	public void setShipto_state_name(String shipto_state_name) {
		this.shipto_state_name = shipto_state_name;
	}

	public String getDestination_billto() {
		return destination_billto;
	}

	public void setDestination_billto(String destination_billto) {
		this.destination_billto = destination_billto;
	}

	public String getRequest_no() {
		return request_no;
	}

	public void setRequest_no(String request_no) {
		this.request_no = request_no;
	}

	public String getArticle_req_date() {
		return article_req_date;
	}

	public void setArticle_req_date(String article_req_date) {
		this.article_req_date = article_req_date;
	}

	public String getTrd_scheme_code() {
		return trd_scheme_code;
	}

	public void setTrd_scheme_code(String trd_scheme_code) {
		this.trd_scheme_code = trd_scheme_code;
	}

	public String getArticle_req_dtl_id() {
		return article_req_dtl_id;
	}

	public void setArticle_req_dtl_id(String article_req_dtl_id) {
		this.article_req_dtl_id = article_req_dtl_id;
	}



	public String getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(String alloc_id) {
		this.alloc_id = alloc_id;
	}

	public String getAllocdtl_id() {
		return allocdtl_id;
	}

	public void setAllocdtl_id(String allocdtl_id) {
		this.allocdtl_id = allocdtl_id;
	}

	@Override
	public String toString() {
		return "New_Stockist_wiseScheme_request_model [ROW=" + ROW + ", loc_nm=" + loc_nm + ", article_req_id="
				+ article_req_id + ", invoice_no=" + invoice_no + ", invoice_date=" + invoice_date + ", cust_id="
				+ cust_id + ", cust_name_billto=" + cust_name_billto + ", cust_name_shipto=" + cust_name_shipto
				+ ", destination=" + destination + ", sale_prod_code_sg=" + sale_prod_code_sg + ", sale_prod_name="
				+ sale_prod_name + ", sale_prod_qty_billed=" + sale_prod_qty_billed + ", sale_prod_qty_free="
				+ sale_prod_qty_free + ", sale_billed_value=" + sale_billed_value + ", trd_scheme_name="
				+ trd_scheme_name + ", article_prod_id=" + article_prod_id + ", article_prod_cd=" + article_prod_cd
				+ ", article_name=" + article_name + ", qty_reqd=" + qty_reqd + ", erp_cust_cd=" + erp_cust_cd
				+ ", cust_code_shipto=" + cust_code_shipto + ", lr_number=" + lr_number + ", shipto_state_name="
				+ shipto_state_name + ", destination_billto=" + destination_billto + ", request_no=" + request_no
				+ ", article_req_date=" + article_req_date + ", trd_scheme_code=" + trd_scheme_code
				+ ", article_req_dtl_id=" + article_req_dtl_id + ", alloc_id=" + alloc_id + ", allocdtl_id="
				+ allocdtl_id + "]";
	}
	
	
	
	
	
	
}
