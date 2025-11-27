package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="COMPANY")
public class Company {
	
	@Id
	@Column(name="COMPANY")
	public String company;

	@Column(name="COMPANY_NAME")
	public String company_name;

	@Column(name="COMPANY_GROUP_ID")
	public String company_group_id;

	@Column(name="COMPANY_PERF_PARAMETER")
	public String company_perf_parameter;

	@Column(name="COMPANY_SALEPERFALL_FLG")
	public String company_saleperfall_flg;

	@Column(name="COMPANY_MAP_CD")
	public String company_map_cd;

	@Column(name="COMPANY_ins_usr_id")
	public String company_ins_usr_id;

	@Column(name="COMPANY_mod_usr_id")
	public String company_mod_usr_id;

	@Column(name="COMPANY_ins_dt")
	public String company_ins_dt;

	@Column(name="COMPANY_mod_dt")
	public String company_mod_dt;

	@Column(name="COMPANY_ins_ip_add")
	public String company_ins_ip_add;

	@Column(name="COMPANY_mod_ip_add")
	public String company_mod_ip_add;

	@Column(name="COMPANY_status")
	public String company_status;

	@Column(name="COMPANY_LOGO")
	public String company_logo;

	@Column(name="SAMP_DISP_IND")
	public String samp_disp_ind;

	@Column(name="ALLOC_STOCK_CHECK")
	public String alloc_stock_check;

	@Column(name="PENDING_DISP_IND")
	public String pending_disp_ind;

	@Column(name="ADDRESS1")
	public String address1;

	@Column(name="ADDRESS2")
	public String address2;

	@Column(name="ADDRESS3")
	public String address3;

	@Column(name="ADDRESS4")
	public String address4;

	@Column(name="WEBSITE")
	public String website;

	@Column(name="CINNO")
	public String cinno;

	@Column(name="TEAM_REQD")
	public String team_reqd;

	@Column(name="WEB_SITE")
	public String web_site;

	@Column(name="AUDIT_XLS_IND")
	public String audit_xls_ind;

	@Column(name="MARGIN_REQD")
	public String margin_reqd;

	@Column(name="PAN_NO")
	public String pan_no;

	@Column(name="STOCK_AT_CFA")
	public String stock_at_cfa;

	@Column(name="GST_REG_NO")
	public String gst_reg_no;

	@Column(name="NIL_GST_STN")
	public String nil_gst_stn;

	@Column(name="FREE_GOODS_STN")
	public String free_goods_stn;
	
	@Column(name="WMS_IS_LIVE")
	private String wms_is_live;
	
//	@Column(name="E_INV_REQ_IND")
	@Transient
	private String e_inv_req_ind;
	
//	@Column(name="TAXWISE_INV_REQ_IND")
	@Transient
	private String taxwise_inv_req_ind;
	
//	@Column(name="EINVOICE_HOST")
	@Transient
	private String einvoice_host;
	
//	@Column(name = "SEPARATE_PREFIX")
	@Transient
	private String separate_prefix;
	
//	@Column(name="EINV_VENDOR")
	@Transient
	private String einv_vendor;
	
//	@Column(name="CFA_TO_STK_IND")
	@Transient
	private String cfa_to_stk_ind;
	

	public String getCfa_to_stk_ind() {
		return cfa_to_stk_ind;
	}

	public void setCfa_to_stk_ind(String cfa_to_stk_ind) {
		this.cfa_to_stk_ind = cfa_to_stk_ind;
	}

	public String getEinv_vendor() {
		return einv_vendor;
	}

	public void setEinv_vendor(String einv_vendor) {
		this.einv_vendor = einv_vendor;
	}

	public String getE_inv_req_ind() {
		return e_inv_req_ind;
	}

	public void setE_inv_req_ind(String e_inv_req_ind) {
		this.e_inv_req_ind = e_inv_req_ind;
	}

	public String getTaxwise_inv_req_ind() {
		return taxwise_inv_req_ind;
	}

	public void setTaxwise_inv_req_ind(String taxwise_inv_req_ind) {
		this.taxwise_inv_req_ind = taxwise_inv_req_ind;
	}

	public String getEinvoice_host() {
		return einvoice_host;
	}

	public void setEinvoice_host(String einvoice_host) {
		this.einvoice_host = einvoice_host;
	}

	public String getSeparate_prefix() {
		return separate_prefix;
	}

	public void setSeparate_prefix(String separate_prefix) {
		this.separate_prefix = separate_prefix;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_group_id() {
		return company_group_id;
	}

	public void setCompany_group_id(String company_group_id) {
		this.company_group_id = company_group_id;
	}

	public String getCompany_perf_parameter() {
		return company_perf_parameter;
	}

	public void setCompany_perf_parameter(String company_perf_parameter) {
		this.company_perf_parameter = company_perf_parameter;
	}

	public String getCompany_saleperfall_flg() {
		return company_saleperfall_flg;
	}

	public void setCompany_saleperfall_flg(String company_saleperfall_flg) {
		this.company_saleperfall_flg = company_saleperfall_flg;
	}

	public String getCompany_map_cd() {
		return company_map_cd;
	}

	public void setCompany_map_cd(String company_map_cd) {
		this.company_map_cd = company_map_cd;
	}

	public String getCompany_ins_usr_id() {
		return company_ins_usr_id;
	}

	public void setCompany_ins_usr_id(String company_ins_usr_id) {
		this.company_ins_usr_id = company_ins_usr_id;
	}

	public String getCompany_mod_usr_id() {
		return company_mod_usr_id;
	}

	public void setCompany_mod_usr_id(String company_mod_usr_id) {
		this.company_mod_usr_id = company_mod_usr_id;
	}

	public String getCompany_ins_dt() {
		return company_ins_dt;
	}

	public void setCompany_ins_dt(String company_ins_dt) {
		this.company_ins_dt = company_ins_dt;
	}

	public String getCompany_mod_dt() {
		return company_mod_dt;
	}

	public void setCompany_mod_dt(String company_mod_dt) {
		this.company_mod_dt = company_mod_dt;
	}

	public String getCompany_ins_ip_add() {
		return company_ins_ip_add;
	}

	public void setCompany_ins_ip_add(String company_ins_ip_add) {
		this.company_ins_ip_add = company_ins_ip_add;
	}

	public String getCompany_mod_ip_add() {
		return company_mod_ip_add;
	}

	public void setCompany_mod_ip_add(String company_mod_ip_add) {
		this.company_mod_ip_add = company_mod_ip_add;
	}

	public String getCompany_status() {
		return company_status;
	}

	public void setCompany_status(String company_status) {
		this.company_status = company_status;
	}

	public String getCompany_logo() {
		return company_logo;
	}

	public void setCompany_logo(String company_logo) {
		this.company_logo = company_logo;
	}

	public String getSamp_disp_ind() {
		return samp_disp_ind;
	}

	public void setSamp_disp_ind(String samp_disp_ind) {
		this.samp_disp_ind = samp_disp_ind;
	}

	public String getAlloc_stock_check() {
		return alloc_stock_check;
	}

	public void setAlloc_stock_check(String alloc_stock_check) {
		this.alloc_stock_check = alloc_stock_check;
	}

	public String getPending_disp_ind() {
		return pending_disp_ind;
	}

	public void setPending_disp_ind(String pending_disp_ind) {
		this.pending_disp_ind = pending_disp_ind;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCinno() {
		return cinno;
	}

	public void setCinno(String cinno) {
		this.cinno = cinno;
	}

	public String getTeam_reqd() {
		return team_reqd;
	}

	public void setTeam_reqd(String team_reqd) {
		this.team_reqd = team_reqd;
	}

	public String getWeb_site() {
		return web_site;
	}

	public void setWeb_site(String web_site) {
		this.web_site = web_site;
	}

	public String getAudit_xls_ind() {
		return audit_xls_ind;
	}

	public void setAudit_xls_ind(String audit_xls_ind) {
		this.audit_xls_ind = audit_xls_ind;
	}

	public String getMargin_reqd() {
		return margin_reqd;
	}

	public void setMargin_reqd(String margin_reqd) {
		this.margin_reqd = margin_reqd;
	}

	public String getPan_no() {
		return pan_no;
	}

	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}

	public String getStock_at_cfa() {
		return stock_at_cfa;
	}

	public void setStock_at_cfa(String stock_at_cfa) {
		this.stock_at_cfa = stock_at_cfa;
	}

	public String getGst_reg_no() {
		return gst_reg_no;
	}

	public void setGst_reg_no(String gst_reg_no) {
		this.gst_reg_no = gst_reg_no;
	}

	public String getNil_gst_stn() {
		return nil_gst_stn;
	}

	public void setNil_gst_stn(String nil_gst_stn) {
		this.nil_gst_stn = nil_gst_stn;
	}

	public String getFree_goods_stn() {
		return free_goods_stn;
	}

	public void setFree_goods_stn(String free_goods_stn) {
		this.free_goods_stn = free_goods_stn;
	}

	public String getWms_is_live() {
		return wms_is_live;
	}

	public void setWms_is_live(String wms_is_live) {
		this.wms_is_live = wms_is_live;
	}
	
	
}
