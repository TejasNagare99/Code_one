package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "BLK_STK_REQ_STOCKISTS")
public class BulkStkReqStockists {

	
	@Column(name = "BLK_STK_REQ_ID")
	private Long blk_stk_req_id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "BLK_STK_REQ_DATE")
	private Date blk_stk_req_date;
	
	@Column(name = "BLK_STK_REQ_NO")
	private String blk_stk_req_no;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BLK_STK_DTL_ID")
	private Long blk_stk_dtl_id;
	
	@Column(name = "CUST_ID")
	private Long cust_id;
	
	@Column(name = "ERP_CUST_CD")
	private String erp_cust_cd;
	
	@Column(name = "CUST_NAME")
	private String cust_name;
	
	@Column(name = "SAP_INV_NO")
	private String sap_inv_no;
	
	@Column(name = "SAP_INV_DATE")
	private String sap_inv_date;
	
	@Column(name = "SERV_REQ_NO")
	private String serv_req_no;
	
	@Column(name = "REQUESTOR_ID")
	private Long requestor_id;
	
	@Column(name = "STK_ins_usr_id")
	private String stk_ins_usr_id;
	
	
	@Column(name = "STK_mod_usr_id")
	private String stk_mod_usr_id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "STK_ins_dt")
	private Date stk_ins_dt;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "STK_mod_dt")
	private Date stk_mod_dt;
	
	@Column(name = "STK_ins_ip_add")
	private String stk_ins_ip_add;
	
	@Column(name = "STK_mod_ip_add")
	private String stk_mod_ip_add;
	
	@Column(name = "STK_status")
	private String stk_status;
	
	@Column(name = "SAP_SCHM_REMARKS")
	private String sap_schm_remarks;

	public Long getBlk_stk_req_id() {
		return blk_stk_req_id;
	}

	public void setBlk_stk_req_id(Long blk_stk_req_id) {
		this.blk_stk_req_id = blk_stk_req_id;
	}

	public Date getBlk_stk_req_date() {
		return blk_stk_req_date;
	}

	public void setBlk_stk_req_date(Date blk_stk_req_date) {
		this.blk_stk_req_date = blk_stk_req_date;
	}

	public String getBlk_stk_req_no() {
		return blk_stk_req_no;
	}

	public void setBlk_stk_req_no(String blk_stk_req_no) {
		this.blk_stk_req_no = blk_stk_req_no;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Long getBlk_stk_dtl_id() {
		return blk_stk_dtl_id;
	}

	public void setBlk_stk_dtl_id(Long blk_stk_dtl_id) {
		this.blk_stk_dtl_id = blk_stk_dtl_id;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public String getErp_cust_cd() {
		return erp_cust_cd;
	}

	public void setErp_cust_cd(String erp_cust_cd) {
		this.erp_cust_cd = erp_cust_cd;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getSap_inv_no() {
		return sap_inv_no;
	}

	public void setSap_inv_no(String sap_inv_no) {
		this.sap_inv_no = sap_inv_no;
	}

	public String getSap_inv_date() {
		return sap_inv_date;
	}

	public void setSap_inv_date(String sap_inv_date) {
		this.sap_inv_date = sap_inv_date;
	}

	public String getServ_req_no() {
		return serv_req_no;
	}

	public void setServ_req_no(String serv_req_no) {
		this.serv_req_no = serv_req_no;
	}

	public Long getRequestor_id() {
		return requestor_id;
	}

	public void setRequestor_id(Long requestor_id) {
		this.requestor_id = requestor_id;
	}

	public String getStk_ins_usr_id() {
		return stk_ins_usr_id;
	}

	public void setStk_ins_usr_id(String stk_ins_usr_id) {
		this.stk_ins_usr_id = stk_ins_usr_id;
	}

	public String getStk_mod_usr_id() {
		return stk_mod_usr_id;
	}

	public void setStk_mod_usr_id(String stk_mod_usr_id) {
		this.stk_mod_usr_id = stk_mod_usr_id;
	}

	public Date getStk_ins_dt() {
		return stk_ins_dt;
	}

	public void setStk_ins_dt(Date stk_ins_dt) {
		this.stk_ins_dt = stk_ins_dt;
	}

	public Date getStk_mod_dt() {
		return stk_mod_dt;
	}

	public void setStk_mod_dt(Date stk_mod_dt) {
		this.stk_mod_dt = stk_mod_dt;
	}

	public String getStk_ins_ip_add() {
		return stk_ins_ip_add;
	}

	public void setStk_ins_ip_add(String stk_ins_ip_add) {
		this.stk_ins_ip_add = stk_ins_ip_add;
	}

	public String getStk_mod_ip_add() {
		return stk_mod_ip_add;
	}

	public void setStk_mod_ip_add(String stk_mod_ip_add) {
		this.stk_mod_ip_add = stk_mod_ip_add;
	}

	public String getStk_status() {
		return stk_status;
	}

	public void setStk_status(String stk_status) {
		this.stk_status = stk_status;
	}

	public String getSap_schm_remarks() {
		return sap_schm_remarks;
	}

	public void setSap_schm_remarks(String sap_schm_remarks) {
		this.sap_schm_remarks = sap_schm_remarks;
	}

	@Override
	public String toString() {
		return "BulkStkReqStockists [blk_stk_req_id=" + blk_stk_req_id + ", blk_stk_req_date=" + blk_stk_req_date
				+ ", blk_stk_req_no=" + blk_stk_req_no + ", fin_year_id=" + fin_year_id + ", company=" + company
				+ ", blk_stk_dtl_id=" + blk_stk_dtl_id + ", cust_id=" + cust_id + ", erp_cust_cd=" + erp_cust_cd
				+ ", cust_name=" + cust_name + ", sap_inv_no=" + sap_inv_no + ", sap_inv_date=" + sap_inv_date
				+ ", serv_req_no=" + serv_req_no + ", requestor_id=" + requestor_id + ", stk_ins_usr_id="
				+ stk_ins_usr_id + ", stk_mod_usr_id=" + stk_mod_usr_id + ", stk_ins_dt=" + stk_ins_dt + ", stk_mod_dt="
				+ stk_mod_dt + ", stk_ins_ip_add=" + stk_ins_ip_add + ", stk_mod_ip_add=" + stk_mod_ip_add
				+ ", stk_status=" + stk_status + ", sap_schm_remarks=" + sap_schm_remarks + "]";
	}



}
