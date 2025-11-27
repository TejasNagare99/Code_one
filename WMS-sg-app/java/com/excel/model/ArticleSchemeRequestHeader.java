package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ARTICLE_SCHREQ_HDR")
public class ArticleSchemeRequestHeader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(name = "SUB_COMP_CD")
	private String sub_comp_cd;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "FS_ID")
	private Long fs_id;
	
	@Column(name = "REQUEST_NO")
	private String request_no;
	
	@Column(name = "ALLOC_TYPE")
	private String alloc_type;
	
	@Column(name = "REQUEST_TYPE")
	private String request_type;
	
	@Column(name = "SUPPLY_DATE")
	private Date supply_date;
	
	@Column(name = "FREIGHT_TYPE")
	private String freight_type;
	
	@Column(name = "FREIGHT_VALUE")
	private BigDecimal freight_value;
	
	@Column(name = "CUST_ID")
	private Long cust_id;
	
	@Column(name = "SAP_PO_NO")
	private String sap_po_no;
	
	@Column(name = "INVOICE_NO")
	private String invoice_no;
	
	@Column(name = "INVOICE_DATE")
	private Date invoice_date;
	
	@Column(name = "LR_NUMBER")
	private String lr_number;
	
	@Column(name = "LR_DATE")
	private Date lr_date;
	
	@Column(name = "TRANSPORTER")
	private String transporter;
	
	@Column(name = "TRANS_GST_REG_NO")
	private String trans_gst_reg_no;
	
	@Column(name = "DISTANCE")
	private BigDecimal distance;
	
	@Column(name = "VEHICLE_NO")
	private String vehicle_no;
	
	@Column(name = "REQ_REMARKS")
	private String req_remarks;
	
	@Column(name = "ARTSCH_TOTAL_VALUE")
	private BigDecimal artsch_total_value;
	
	@Column(name = "ARTSCH_ins_user_id")
	private String artsch_ins_user_id;
	
	@Column(name = "ARTSCH_mod_user_id")
	private String artsch_mod_user_id;
	
	@Column(name = "ARTSCH_ins_dt")
	private Date artsch_ins_dt;
	
	@Column(name = "ARTSCH_mod_dt")
	private Date artsch_mod_dt;
	
	@Column(name = "ARTSCH_ins_ip_add")
	private String artsch_ins_ip_add;
	
	@Column(name = "ARTSCH_mod_ip_add")
	private String artsch_mod_ip_add;
	
	@Column(name = "ARTSCH_status")
	private String artsch_status;
	
	@Column(name = "ARTSCH_APPR_STATUS")
	private String artsch_appr_status;
	
	
	@Transient private String customer_name;
	@Transient private String destination_ship_to;
	@Transient private String corr_ind;
	
	public String getCorr_ind() {
		return corr_ind;
	}
	public void setCorr_ind(String corr_ind) {
		this.corr_ind = corr_ind;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	
	public String getDestination_ship_to() {
		return destination_ship_to;
	}
	public void setDestination_ship_to(String destination_ship_to) {
		this.destination_ship_to = destination_ship_to;
	}
	
	
	
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public BigDecimal getDistance() {
		return distance;
	}
	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}
	public String getSap_po_no() {
		return sap_po_no;
	}
	public void setSap_po_no(String sap_po_no) {
		this.sap_po_no = sap_po_no;
	}
	public String getTransporter() {
		return transporter;
	}
	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
	public String getTrans_gst_reg_no() {
		return trans_gst_reg_no;
	}
	public void setTrans_gst_reg_no(String trans_gst_reg_no) {
		this.trans_gst_reg_no = trans_gst_reg_no;
	}
	public String getSub_comp_cd() {
		return sub_comp_cd;
	}
	public void setSub_comp_cd(String sub_comp_cd) {
		this.sub_comp_cd = sub_comp_cd;
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
	public String getRequest_no() {
		return request_no;
	}
	public void setRequest_no(String request_no) {
		this.request_no = request_no;
	}
	public String getAlloc_type() {
		return alloc_type;
	}
	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}
	public String getRequest_type() {
		return request_type;
	}
	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public String getFreight_type() {
		return freight_type;
	}
	public void setFreight_type(String freight_type) {
		this.freight_type = freight_type;
	}
	public BigDecimal getFreight_value() {
		return freight_value;
	}
	public void setFreight_value(BigDecimal freight_value) {
		this.freight_value = freight_value;
	}
	public Long getCust_id() {
		return cust_id;
	}
	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getLr_number() {
		return lr_number;
	}
	public void setLr_number(String lr_number) {
		this.lr_number = lr_number;
	}

	public String getReq_remarks() {
		return req_remarks;
	}
	public void setReq_remarks(String req_remarks) {
		this.req_remarks = req_remarks;
	}
	public BigDecimal getArtsch_total_value() {
		return artsch_total_value;
	}
	public void setArtsch_total_value(BigDecimal artsch_total_value) {
		this.artsch_total_value = artsch_total_value;
	}
	public String getArtsch_ins_user_id() {
		return artsch_ins_user_id;
	}
	public void setArtsch_ins_user_id(String artsch_ins_user_id) {
		this.artsch_ins_user_id = artsch_ins_user_id;
	}
	public String getArtsch_mod_user_id() {
		return artsch_mod_user_id;
	}
	public void setArtsch_mod_user_id(String artsch_mod_user_id) {
		this.artsch_mod_user_id = artsch_mod_user_id;
	}
	public String getArtsch_ins_ip_add() {
		return artsch_ins_ip_add;
	}
	public void setArtsch_ins_ip_add(String artsch_ins_ip_add) {
		this.artsch_ins_ip_add = artsch_ins_ip_add;
	}
	public String getArtsch_mod_ip_add() {
		return artsch_mod_ip_add;
	}
	public void setArtsch_mod_ip_add(String artsch_mod_ip_add) {
		this.artsch_mod_ip_add = artsch_mod_ip_add;
	}
	public String getArtsch_status() {
		return artsch_status;
	}
	public void setArtsch_status(String artsch_status) {
		this.artsch_status = artsch_status;
	}
	public String getArtsch_appr_status() {
		return artsch_appr_status;
	}
	public void setArtsch_appr_status(String artsch_appr_status) {
		this.artsch_appr_status = artsch_appr_status;
	}
	public Date getSupply_date() {
		return supply_date;
	}
	public void setSupply_date(Date supply_date) {
		this.supply_date = supply_date;
	}
	public Date getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}
	public Date getLr_date() {
		return lr_date;
	}
	public void setLr_date(Date lr_date) {
		this.lr_date = lr_date;
	}
	public Date getArtsch_ins_dt() {
		return artsch_ins_dt;
	}
	public void setArtsch_ins_dt(Date artsch_ins_dt) {
		this.artsch_ins_dt = artsch_ins_dt;
	}
	public Date getArtsch_mod_dt() {
		return artsch_mod_dt;
	}
	public void setArtsch_mod_dt(Date artsch_mod_dt) {
		this.artsch_mod_dt = artsch_mod_dt;
	}
	@Override
	public String toString() {
		return "ArticleSchemeRequestHeader [article_req_id=" + article_req_id + ", article_req_date=" + article_req_date
				+ ", fin_year=" + fin_year + ", period_code=" + period_code + ", company=" + company + ", sub_comp_cd="
				+ sub_comp_cd + ", loc_id=" + loc_id + ", fs_id=" + fs_id + ", request_no=" + request_no
				+ ", alloc_type=" + alloc_type + ", request_type=" + request_type + ", supply_date=" + supply_date
				+ ", freight_type=" + freight_type + ", freight_value=" + freight_value + ", cust_id=" + cust_id
				+ ", invoice_no=" + invoice_no + ", invoice_date=" + invoice_date + ", lr_number=" + lr_number
				+ ", lr_date=" + lr_date + ", req_remarks=" + req_remarks + ", artsch_total_value=" + artsch_total_value
				+ ", artsch_ins_user_id=" + artsch_ins_user_id + ", artsch_mod_user_id=" + artsch_mod_user_id
				+ ", artsch_ins_dt=" + artsch_ins_dt + ", artsch_mod_dt=" + artsch_mod_dt + ", artsch_ins_ip_add="
				+ artsch_ins_ip_add + ", artsch_mod_ip_add=" + artsch_mod_ip_add + ", artsch_status=" + artsch_status
				+ ", artsch_appr_status=" + artsch_appr_status + "]";
	}
	
	public ArticleSchemeRequestHeader() {
		super();
	}
	
	public ArticleSchemeRequestHeader(Long article_req_id, String request_no) {
		super();
		this.article_req_id = article_req_id;
		this.request_no = request_no;
	}
	
	
	
}
