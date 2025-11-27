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
@Table(name="Artreq_to_dispatch_report_with_param")

@NamedStoredProcedureQuery(name = "callartereq_to_dispatch_report_with_param",
						   procedureName = "ARTREQ_TO_DISPATCH_REPORT_WITH_PARAM",
							parameters= {
									@StoredProcedureParameter(name = "piLOC_id" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "startdate" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "enddate" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "MCustomer_CD" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "MSAP_Invoice" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "MLR_No" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "MArticle_Scheme" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "MArticle_PROD_CD" ,mode = ParameterMode.IN,type=String.class),
									@StoredProcedureParameter(name = "MSales_Product" ,mode = ParameterMode.IN,type=String.class),
								
							}, resultClasses = Artreq_To_Dispatch_Report_With_Param.class)


@NamedStoredProcedureQuery(name = "callartereq_to_dispatch_direct_to_stockist_report_with_param",
procedureName = "ARTREQ_TO_DISPATCH_REPORT_WITH_PARAM_DSP_HDR",
	parameters= {
			@StoredProcedureParameter(name = "piLOC_id" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "startdate" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "enddate" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MCustomer_CD" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MSAP_Invoice" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MLR_No" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MArticle_Scheme" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MArticle_PROD_CD" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "MSales_Product" ,mode = ParameterMode.IN,type=String.class),
		
	}, resultClasses = Artreq_To_Dispatch_Report_With_Param.class)



public class Artreq_To_Dispatch_Report_With_Param {

	@Id
	@Column(name="ROW")
	private Long row;

	@Column(name="LOC_NM")
	private String  loc_nm;

	@Column(name="ARTICLE_REQ_ID")
	private String  article_req_id;

	@Column(name="INVOICE_NO")
	private String  invoice_no;

	@Column(name="INVOICE_DATE")
	private String  invoice_date;

	@Column(name="CUST_NAME_BILLTO")
	private String  cust_name_billto;

	@Column(name="CUST_NAME_SHIPTO")
	private String  cust_name_shipto;

	@Column(name="DESTINATION")
	private String  destination;

	@Column(name="SALE_PROD_CODE_SG")
	private String  sale_prod_code_sg;

	@Column(name="SALE_PROD_NAME")
	private String  sale_prod_name;

	@Column(name="SALE_PROD_QTY_BILLED")
	private String  sale_prod_qty_billed;

	@Column(name="SALE_PROD_QTY_FREE")
	private String  sale_prod_qty_free;

	@Column(name="SALE_BILLED_VALUE")
	private String  sale_billed_value;

	@Column(name="TRD_SCHEME_NAME")
	private String  trd_scheme_name;

	@Column(name="ARTICLE_PROD_CD")
	private String  article_prod_cd;

	@Column(name="ARTICLE_NAME")
	private String  article_name;

	@Column(name="QTY_REQD")
	private String  qty_reqd;

	@Column(name="QTY_SUPPLIED")
	private String  qty_supplied;
	
	@Column(name="PENDING_QTY")
	private String  pending_qty;
	
	
	@Column(name="DSP_CHALLAN_NO")
	private String  dsp_challan_no;

	@Column(name="DSP_DT")
	private String  dsp_dt;
	
	
	@Column(name="DSP_LR_NO")
	private String  dsp_lr_no;

	@Column(name="DSP_LR_DT")
	private String  dsp_lr_dt;
	
	
	@Column(name="E_INV_NO")
	private String  e_inv_no;

	@Column(name="E_INV_DATE")
	private String  e_inv_date;
	
	
	@Column(name="TRN_EWAYBILLNO")
	private String  trn_ewaybillno;

	@Column(name="TRN_EWAYBILLDT")
	private String  trn_ewaybilldt;
	
	
	@Column(name="LR_NUMBER")
	private String  lr_number;

	@Column(name="ERP_CUST_CD")
	private String  erp_cust_cd;
	
	@Column(name="CUST_CODE_SHIPTO")
	private String  cust_code_shipto;
	
	
	@Column(name="SHIPTO_STATE_NAME")
	private String  shipto_state_name;
	
	@Column(name="DESTINATION_BILLTO")
	private String  destination_billto;
	
	@Column(name="REQUEST_NO")
	private String  request_no;
	
	@Column(name="ARTICLE_REQ_DATE")
	private String  article_req_date;
	
	@Column(name="TRD_SCHEME_CODE")
	private String  trd_scheme_code;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getArticle_req_id() {
		return article_req_id;
	}

	public void setArticle_req_id(String article_req_id) {
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

	public String getSale_prod_qty_billed() {
		return sale_prod_qty_billed;
	}

	public void setSale_prod_qty_billed(String sale_prod_qty_billed) {
		this.sale_prod_qty_billed = sale_prod_qty_billed;
	}

	public String getSale_prod_qty_free() {
		return sale_prod_qty_free;
	}

	public void setSale_prod_qty_free(String sale_prod_qty_free) {
		this.sale_prod_qty_free = sale_prod_qty_free;
	}

	public String getSale_billed_value() {
		return sale_billed_value;
	}

	public void setSale_billed_value(String sale_billed_value) {
		this.sale_billed_value = sale_billed_value;
	}

	public String getTrd_scheme_name() {
		return trd_scheme_name;
	}

	public void setTrd_scheme_name(String trd_scheme_name) {
		this.trd_scheme_name = trd_scheme_name;
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

	public String getQty_reqd() {
		return qty_reqd;
	}

	public void setQty_reqd(String qty_reqd) {
		this.qty_reqd = qty_reqd;
	}

	public String getQty_supplied() {
		return qty_supplied;
	}

	public void setQty_supplied(String qty_supplied) {
		this.qty_supplied = qty_supplied;
	}

	public String getPending_qty() {
		return pending_qty;
	}

	public void setPending_qty(String pending_qty) {
		this.pending_qty = pending_qty;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public String getDsp_dt() {
		return dsp_dt;
	}

	public void setDsp_dt(String dsp_dt) {
		this.dsp_dt = dsp_dt;
	}

	public String getDsp_lr_no() {
		return dsp_lr_no;
	}

	public void setDsp_lr_no(String dsp_lr_no) {
		this.dsp_lr_no = dsp_lr_no;
	}

	public String getDsp_lr_dt() {
		return dsp_lr_dt;
	}

	public void setDsp_lr_dt(String dsp_lr_dt) {
		this.dsp_lr_dt = dsp_lr_dt;
	}

	public String getE_inv_no() {
		return e_inv_no;
	}

	public void setE_inv_no(String e_inv_no) {
		this.e_inv_no = e_inv_no;
	}

	public String getE_inv_date() {
		return e_inv_date;
	}

	public void setE_inv_date(String e_inv_date) {
		this.e_inv_date = e_inv_date;
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

	public String getLr_number() {
		return lr_number;
	}

	public void setLr_number(String lr_number) {
		this.lr_number = lr_number;
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

	@Override
	public String toString() {
		return "Artreq_To_Dispatch_Report_With_Param [row=" + row + ", loc_nm=" + loc_nm + ", article_req_id="
				+ article_req_id + ", invoice_no=" + invoice_no + ", invoice_date=" + invoice_date
				+ ", cust_name_billto=" + cust_name_billto + ", cust_name_shipto=" + cust_name_shipto + ", destination="
				+ destination + ", sale_prod_code_sg=" + sale_prod_code_sg + ", sale_prod_name=" + sale_prod_name
				+ ", sale_prod_qty_billed=" + sale_prod_qty_billed + ", sale_prod_qty_free=" + sale_prod_qty_free
				+ ", sale_billed_value=" + sale_billed_value + ", trd_scheme_name=" + trd_scheme_name
				+ ", article_prod_cd=" + article_prod_cd + ", article_name=" + article_name + ", qty_reqd=" + qty_reqd
				+ ", qty_supplied=" + qty_supplied + ", pending_qty=" + pending_qty + ", dsp_challan_no="
				+ dsp_challan_no + ", dsp_dt=" + dsp_dt + ", dsp_lr_no=" + dsp_lr_no + ", dsp_lr_dt=" + dsp_lr_dt
				+ ", e_inv_no=" + e_inv_no + ", e_inv_date=" + e_inv_date + ", trn_ewaybillno=" + trn_ewaybillno
				+ ", trn_ewaybilldt=" + trn_ewaybilldt + ", lr_number=" + lr_number + ", erp_cust_cd=" + erp_cust_cd
				+ ", cust_code_shipto=" + cust_code_shipto + ", shipto_state_name=" + shipto_state_name
				+ ", destination_billto=" + destination_billto + ", request_no=" + request_no + ", article_req_date="
				+ article_req_date + ", trd_scheme_code=" + trd_scheme_code + "]";
	}
	
	
	
	
	
	
	

}
