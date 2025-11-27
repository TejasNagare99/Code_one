package com.excel.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="STOCK_ADJ_HEADER")
public class Stock_Adj_Header {
	
	@Id
	@Column(name="STKADJ_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long stkadj_id;
	
	@Column(name="STKADJ_NO")
	private String stkadj_no;
	
	@Column(name="STKADJ_COMPANY")
	private String stkadj_company;
	
	@Column(name="STKADJ_FIN_YEAR")
	private String stkadj_fin_year;
	
	@Column(name="STKADJ_PERIOD_CODE")
	private String stkadj_period_code;
	
	@Column(name="STKADJ_LOC_ID")
	private Long stkadj_loc_id;
	
	@Column(name="STKADJ_DATE")
	private Date stkadj_date;
	
	@Column(name="STKADJ_REMARKS")
	private String stkadj_remarks;
	
	@Column(name="STKADJ_ERP_CD")
	private String stkadj_erp_code;
	
	@Column(name="STKADJ_INS_USR_ID")
	private String stkadj_ins_user_id;
	
	@Column(name="STKADJ_INS_DT")
	private Date stkadj_ins_dt;
	
	@Column(name="STKADJ_INS_IP_ADD")
	private String stkadj_ins_ip_add;
	
	@Column(name="STKADJ_MOD_DT")
	private Date stkadj_mod_dt;
	
	@Column(name="STKADJ_MOD_IP_ADD")
	private String stkadj_mod_ip_add;
	
	@Column(name="STKADJ_MOD_USR_ID")
	private String stkadj_mod_user_id;
	
	@Column(name="STKADJ_APPR_REQ")
	private Long stkadj_appr_req;
	
	@Column(name="STKADJ_APPR_ACQ")
	private Long stkadj_appr_acq;
	
	@Column(name="STKADJ_STATUS")
	private String stkadj_status;
	
	@Column(name="STKADJ_APPR_STATUS")
	private String stkadj_appr_status;
	
	@Column(name="IAA_IMG")
	private String iaa_img;
	
	@Column(name="ERP_CREATED")
	private String erp_created;
	
	@Column(name="ERP_IAA_NO")
	private String erp_iaa_no;

	public Date getStkadj_mod_dt() {
		return stkadj_mod_dt;
	}

	public void setStkadj_mod_dt(Date stkadj_mod_dt) {
		this.stkadj_mod_dt = stkadj_mod_dt;
	}

	public String getStkadj_mod_ip_add() {
		return stkadj_mod_ip_add;
	}

	public void setStkadj_mod_ip_add(String stkadj_mod_ip_add) {
		this.stkadj_mod_ip_add = stkadj_mod_ip_add;
	}

	public String getStkadj_mod_user_id() {
		return stkadj_mod_user_id;
	}

	public void setStkadj_mod_user_id(String stkadj_mod_user_id) {
		this.stkadj_mod_user_id = stkadj_mod_user_id;
	}

	public Long getStkadj_id() {
		return stkadj_id;
	}

	public void setStkadj_id(Long stkadj_id) {
		this.stkadj_id = stkadj_id;
	}

	public String getStkadj_no() {
		return stkadj_no;
	}

	public void setStkadj_no(String stkadj_no) {
		this.stkadj_no = stkadj_no;
	}

	public String getStkadj_company() {
		return stkadj_company;
	}

	public void setStkadj_company(String stkadj_company) {
		this.stkadj_company = stkadj_company;
	}

	public String getStkadj_fin_year() {
		return stkadj_fin_year;
	}

	public void setStkadj_fin_year(String stkadj_fin_year) {
		this.stkadj_fin_year = stkadj_fin_year;
	}

	public String getStkadj_period_code() {
		return stkadj_period_code;
	}

	public void setStkadj_period_code(String stkadj_period_code) {
		this.stkadj_period_code = stkadj_period_code;
	}

	public Long getStkadj_loc_id() {
		return stkadj_loc_id;
	}

	public void setStkadj_loc_id(Long stkadj_loc_id) {
		this.stkadj_loc_id = stkadj_loc_id;
	}

	public Date getStkadj_date() {
		return stkadj_date;
	}

	public void setStkadj_date(Date stkadj_date) {
		this.stkadj_date = stkadj_date;
	}

	public String getStkadj_remarks() {
		return stkadj_remarks;
	}

	public void setStkadj_remarks(String stkadj_remarks) {
		this.stkadj_remarks = stkadj_remarks;
	}

	public String getStkadj_erp_code() {
		return stkadj_erp_code;
	}

	public void setStkadj_erp_code(String stkadj_erp_code) {
		this.stkadj_erp_code = stkadj_erp_code;
	}

	public String getStkadj_ins_user_id() {
		return stkadj_ins_user_id;
	}

	public void setStkadj_ins_user_id(String stkadj_ins_user_id) {
		this.stkadj_ins_user_id = stkadj_ins_user_id;
	}

	public Date getStkadj_ins_dt() {
		return stkadj_ins_dt;
	}

	public void setStkadj_ins_dt(Date stkadj_ins_dt) {
		this.stkadj_ins_dt = stkadj_ins_dt;
	}

	public String getStkadj_ins_ip_add() {
		return stkadj_ins_ip_add;
	}

	public void setStkadj_ins_ip_add(String stkadj_ins_ip_add) {
		this.stkadj_ins_ip_add = stkadj_ins_ip_add;
	}

	public Long getStkadj_appr_req() {
		return stkadj_appr_req;
	}

	public void setStkadj_appr_req(Long stkadj_appr_req) {
		this.stkadj_appr_req = stkadj_appr_req;
	}


	public String getStkadj_status() {
		return stkadj_status;
	}

	public void setStkadj_status(String stkadj_status) {
		this.stkadj_status = stkadj_status;
	}

	public String getStkadj_appr_status() {
		return stkadj_appr_status;
	}

	public void setStkadj_appr_status(String stkadj_appr_status) {
		this.stkadj_appr_status = stkadj_appr_status;
	}

	public String getIaa_img() {
		return iaa_img;
	}

	public void setIaa_img(String iaa_img) {
		this.iaa_img = iaa_img;
	}

	public String getErp_created() {
		return erp_created;
	}

	public void setErp_created(String erp_created) {
		this.erp_created = erp_created;
	}

	public String getErp_iaa_no() {
		return erp_iaa_no;
	}

	public void setErp_iaa_no(String erp_iaa_no) {
		this.erp_iaa_no = erp_iaa_no;
	}

	public Long getStkadj_appr_acq() {
		return stkadj_appr_acq;
	}

	public void setStkadj_appr_acq(Long stkadj_appr_acq) {
		this.stkadj_appr_acq = stkadj_appr_acq;
	}
	

	
}
