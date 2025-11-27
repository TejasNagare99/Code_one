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
@Table(name="CRM_REQ_REQUESTS")
public class CRM_Req_Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CRM_REQ_ID")
	private Long crm_req_id;
	
	@Column(name="USER_ID")
	private String user_id;

	@Column(name="CRM_REQ_COMPANY")
	private String crm_req_company;

	@Column(name="CRM_REQ_FINYEAR")
	private String crm_req_finyear;

	@Column(name="CRM_REQ_DATE")
	private Date crm_req_date;

	@Column(name="CRM_SCHED_DATE")
	private Date crm_sched_date;

	@Column(name="CRM_REQ_CUST_TYPE")
	private String crm_req_cust_type;

	@Column(name="CRM_REQ_TERR_ID")
	private Long crm_req_terr_id;

	@Column(name="CRM_REQ_FS_ID")
	private Long crm_req_fs_id;

	@Column(name="CRM_REQ_DIV_ID")
	private Long crm_req_div_id;

	@Column(name="CRM_REQ_UNIQ_HCP_ID")
	private Long crm_req_uniq_hcp_id;

	@Column(name="CRM_REQ_CUST_ID")
	private Long crm_req_cust_id;

	@Column(name="CRM_REQ_RETAILER_ID")
	private Long crm_req_retailer_id;

	@Column(name="CRM_REQ_CRM_NAME")
	private String crm_req_crm_name;

	@Column(name="CRM_REQ_CUST_ADDR1")
	private String crm_req_cust_addr1;

	@Column(name="CRM_REQ_CUST_ADDR2")
	private String crm_req_cust_addr2;

	@Column(name="CRM_REQ_CUST_ADDR3")
	private String crm_req_cust_addr3;

	@Column(name="CRM_REQ_CUST_ADDR4")
	private String crm_req_cust_addr4;

	@Column(name="CRM_REQ_CUST_CITY")
	private String crm_req_cust_city;

	@Column(name="CRM_REQ_CUST_PINCODE")
	private String crm_req_cust_pincode;

	@Column(name="CRM_REQ_CUST_MOBILE")
	private String crm_req_cust_mobile;

	@Column(name="CRM_REQ_CUST_EMAIL")
	private String crm_req_cust_email;

	@Column(name="CRM_REQ_CUST_PANNO")
	private String crm_req_cust_panno;

	@Column(name="CRM_REQ_CUST_HCP_NO")
	private String crm_req_cust_hcp_no;

	@Column(name="CRM_REQ_CURR_BIZ")
	private BigDecimal crm_req_curr_biz;

	@Column(name="CRM_REQ_EXPECT_BIZ")
	private BigDecimal crm_req_expect_biz;

	@Column(name="CRM_REQ_SCHEME_ID")
	private Long crm_req_scheme_id;

	@Column(name="CRM_REQ_SCHEME_TYPE")
	private String crm_req_scheme_type;

	@Column(name="CRM_REQ_EXPS_DETAILS")
	private String crm_req_exps_details;

	@Column(name="CRM_REQ_EXPS_ITEM")
	private String crm_req_exps_item;

	@Column(name="CRM_REQ_QTY")
	private BigDecimal crm_req_qty;

	@Column(name="CRM_REQ_RATE")
	private BigDecimal crm_req_rate;

	@Column(name="CRM_REQ_VALUE")
	private BigDecimal crm_req_value;

	@Column(name="CRM_REQ_QTY_APPROV")
	private BigDecimal crm_req_qty_approv;

	@Column(name="CRM_REQ_RATE_APPROV")
	private BigDecimal crm_req_rate_approv;

	@Column(name="CRM_REQ_VALUE_APPROV")
	private BigDecimal crm_req_value_approv;

	@Column(name="CRM_REQ_CONFIRM")
	private String crm_req_confirm;

	@Column(name="CRM_REQ_INS_BY")
	private String crm_req_ins_by;

	@Column(name="LAST_MOD_DT")
	private Date last_mod_dt;

	@Column(name="LAST_MOD_BY")
	private String last_mod_by;
	
	@Column(name="PERIOD_ID")
	private Long period_id;

	@Column(name="CRM_REQ_NO")
	private String crm_req_no;

	@Column(name="APPR_STATUS")
	private String appr_status; 
	
	@Column(name="LOC_ID")
	private Long loc_id;
	
	
	
	@Transient
	private String crm_scheme_name;
	@Transient
	private String doc_special;
	@Transient
	private String doc_class;
	@Transient
	private String doc_catg;
	@Transient
	private String cust_type;
	
	@Transient
	private String crm_scheme_type;
	@Transient
	private String customer_type;
	@Transient
	private Long approval_logid;
	@Transient
	private String crm_scheme_descr;

	
	
	
	
	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCrm_scheme_descr() {
		return crm_scheme_descr;
	}

	public void setCrm_scheme_descr(String crm_scheme_descr) {
		this.crm_scheme_descr = crm_scheme_descr;
	}

	public String getCrm_scheme_type() {
		return crm_scheme_type;
	}

	public void setCrm_scheme_type(String crm_scheme_type) {
		this.crm_scheme_type = crm_scheme_type;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public Long getApproval_logid() {
		return approval_logid;
	}

	public void setApproval_logid(Long approval_logid) {
		this.approval_logid = approval_logid;
	}

	public String getCrm_req_no() {
		return crm_req_no;
	}

	public void setCrm_req_no(String crm_req_no) {
		this.crm_req_no = crm_req_no;
	}

	public Long getPeriod_id() {
		return period_id;
	}

	public void setPeriod_id(Long period_id) {
		this.period_id = period_id;
	}

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}

	public String getDoc_special() {
		return doc_special;
	}

	public void setDoc_special(String doc_special) {
		this.doc_special = doc_special;
	}

	public String getDoc_class() {
		return doc_class;
	}

	public void setDoc_class(String doc_class) {
		this.doc_class = doc_class;
	}

	public String getDoc_catg() {
		return doc_catg;
	}

	public void setDoc_catg(String doc_catg) {
		this.doc_catg = doc_catg;
	}

	public String getCrm_scheme_name() {
		return crm_scheme_name;
	}

	public void setCrm_scheme_name(String crm_scheme_name) {
		this.crm_scheme_name = crm_scheme_name;
	}

	public Long getCrm_req_id() {
		return crm_req_id;
	}

	public void setCrm_req_id(Long crm_req_id) {
		this.crm_req_id = crm_req_id;
	}

	public String getCrm_req_company() {
		return crm_req_company;
	}

	public void setCrm_req_company(String crm_req_company) {
		this.crm_req_company = crm_req_company;
	}

	public String getCrm_req_finyear() {
		return crm_req_finyear;
	}

	public void setCrm_req_finyear(String crm_req_finyear) {
		this.crm_req_finyear = crm_req_finyear;
	}

	public Date getCrm_req_date() {
		return crm_req_date;
	}

	public void setCrm_req_date(Date crm_req_date) {
		this.crm_req_date = crm_req_date;
	}

	public Date getCrm_sched_date() {
		return crm_sched_date;
	}

	public void setCrm_sched_date(Date crm_sched_date) {
		this.crm_sched_date = crm_sched_date;
	}

	public String getCrm_req_cust_type() {
		return crm_req_cust_type;
	}

	public void setCrm_req_cust_type(String crm_req_cust_type) {
		this.crm_req_cust_type = crm_req_cust_type;
	}

	public Long getCrm_req_terr_id() {
		return crm_req_terr_id;
	}

	public void setCrm_req_terr_id(Long crm_req_terr_id) {
		this.crm_req_terr_id = crm_req_terr_id;
	}

	public Long getCrm_req_fs_id() {
		return crm_req_fs_id;
	}

	public void setCrm_req_fs_id(Long crm_req_fs_id) {
		this.crm_req_fs_id = crm_req_fs_id;
	}

	public Long getCrm_req_div_id() {
		return crm_req_div_id;
	}

	public void setCrm_req_div_id(Long crm_req_div_id) {
		this.crm_req_div_id = crm_req_div_id;
	}

	public Long getCrm_req_uniq_hcp_id() {
		return crm_req_uniq_hcp_id;
	}

	public void setCrm_req_uniq_hcp_id(Long crm_req_uniq_hcp_id) {
		this.crm_req_uniq_hcp_id = crm_req_uniq_hcp_id;
	}

	public Long getCrm_req_cust_id() {
		return crm_req_cust_id;
	}

	public void setCrm_req_cust_id(Long crm_req_cust_id) {
		this.crm_req_cust_id = crm_req_cust_id;
	}

	public Long getCrm_req_retailer_id() {
		return crm_req_retailer_id;
	}

	public void setCrm_req_retailer_id(Long crm_req_retailer_id) {
		this.crm_req_retailer_id = crm_req_retailer_id;
	}

	public String getCrm_req_crm_name() {
		return crm_req_crm_name;
	}

	public void setCrm_req_crm_name(String crm_req_crm_name) {
		this.crm_req_crm_name = crm_req_crm_name;
	}

	public String getCrm_req_cust_addr1() {
		return crm_req_cust_addr1;
	}

	public void setCrm_req_cust_addr1(String crm_req_cust_addr1) {
		this.crm_req_cust_addr1 = crm_req_cust_addr1;
	}

	public String getCrm_req_cust_addr2() {
		return crm_req_cust_addr2;
	}

	public void setCrm_req_cust_addr2(String crm_req_cust_addr2) {
		this.crm_req_cust_addr2 = crm_req_cust_addr2;
	}

	public String getCrm_req_cust_addr3() {
		return crm_req_cust_addr3;
	}

	public void setCrm_req_cust_addr3(String crm_req_cust_addr3) {
		this.crm_req_cust_addr3 = crm_req_cust_addr3;
	}

	public String getCrm_req_cust_addr4() {
		return crm_req_cust_addr4;
	}

	public void setCrm_req_cust_addr4(String crm_req_cust_addr4) {
		this.crm_req_cust_addr4 = crm_req_cust_addr4;
	}

	public String getCrm_req_cust_city() {
		return crm_req_cust_city;
	}

	public void setCrm_req_cust_city(String crm_req_cust_city) {
		this.crm_req_cust_city = crm_req_cust_city;
	}

	public String getCrm_req_cust_pincode() {
		return crm_req_cust_pincode;
	}

	public void setCrm_req_cust_pincode(String crm_req_cust_pincode) {
		this.crm_req_cust_pincode = crm_req_cust_pincode;
	}

	public String getCrm_req_cust_mobile() {
		return crm_req_cust_mobile;
	}

	public void setCrm_req_cust_mobile(String crm_req_cust_mobile) {
		this.crm_req_cust_mobile = crm_req_cust_mobile;
	}

	public String getCrm_req_cust_email() {
		return crm_req_cust_email;
	}

	public void setCrm_req_cust_email(String crm_req_cust_email) {
		this.crm_req_cust_email = crm_req_cust_email;
	}

	public String getCrm_req_cust_panno() {
		return crm_req_cust_panno;
	}

	public void setCrm_req_cust_panno(String crm_req_cust_panno) {
		this.crm_req_cust_panno = crm_req_cust_panno;
	}

	public String getCrm_req_cust_hcp_no() {
		return crm_req_cust_hcp_no;
	}

	public void setCrm_req_cust_hcp_no(String crm_req_cust_hcp_no) {
		this.crm_req_cust_hcp_no = crm_req_cust_hcp_no;
	}

	public BigDecimal getCrm_req_curr_biz() {
		return crm_req_curr_biz;
	}

	public void setCrm_req_curr_biz(BigDecimal crm_req_curr_biz) {
		this.crm_req_curr_biz = crm_req_curr_biz;
	}

	public BigDecimal getCrm_req_expect_biz() {
		return crm_req_expect_biz;
	}

	public void setCrm_req_expect_biz(BigDecimal crm_req_expect_biz) {
		this.crm_req_expect_biz = crm_req_expect_biz;
	}

	public Long getCrm_req_scheme_id() {
		return crm_req_scheme_id;
	}

	public void setCrm_req_scheme_id(Long crm_req_scheme_id) {
		this.crm_req_scheme_id = crm_req_scheme_id;
	}

	public String getCrm_req_scheme_type() {
		return crm_req_scheme_type;
	}

	public void setCrm_req_scheme_type(String crm_req_scheme_type) {
		this.crm_req_scheme_type = crm_req_scheme_type;
	}

	public String getCrm_req_exps_details() {
		return crm_req_exps_details;
	}

	public void setCrm_req_exps_details(String crm_req_exps_details) {
		this.crm_req_exps_details = crm_req_exps_details;
	}

	public String getCrm_req_exps_item() {
		return crm_req_exps_item;
	}

	public void setCrm_req_exps_item(String crm_req_exps_item) {
		this.crm_req_exps_item = crm_req_exps_item;
	}

	public BigDecimal getCrm_req_qty() {
		return crm_req_qty;
	}

	public void setCrm_req_qty(BigDecimal crm_req_qty) {
		this.crm_req_qty = crm_req_qty;
	}

	public BigDecimal getCrm_req_rate() {
		return crm_req_rate;
	}

	public void setCrm_req_rate(BigDecimal crm_req_rate) {
		this.crm_req_rate = crm_req_rate;
	}

	public BigDecimal getCrm_req_value() {
		return crm_req_value;
	}

	public void setCrm_req_value(BigDecimal crm_req_value) {
		this.crm_req_value = crm_req_value;
	}

	public BigDecimal getCrm_req_qty_approv() {
		return crm_req_qty_approv;
	}

	public void setCrm_req_qty_approv(BigDecimal crm_req_qty_approv) {
		this.crm_req_qty_approv = crm_req_qty_approv;
	}

	public BigDecimal getCrm_req_rate_approv() {
		return crm_req_rate_approv;
	}

	public void setCrm_req_rate_approv(BigDecimal crm_req_rate_approv) {
		this.crm_req_rate_approv = crm_req_rate_approv;
	}

	public BigDecimal getCrm_req_value_approv() {
		return crm_req_value_approv;
	}

	public void setCrm_req_value_approv(BigDecimal crm_req_value_approv) {
		this.crm_req_value_approv = crm_req_value_approv;
	}

	public String getCrm_req_confirm() {
		return crm_req_confirm;
	}

	public void setCrm_req_confirm(String crm_req_confirm) {
		this.crm_req_confirm = crm_req_confirm;
	}

	public String getCrm_req_ins_by() {
		return crm_req_ins_by;
	}

	public void setCrm_req_ins_by(String crm_req_ins_by) {
		this.crm_req_ins_by = crm_req_ins_by;
	}

	public Date getLast_mod_dt() {
		return last_mod_dt;
	}

	public void setLast_mod_dt(Date last_mod_dt) {
		this.last_mod_dt = last_mod_dt;
	}

	public String getLast_mod_by() {
		return last_mod_by;
	}

	public void setLast_mod_by(String last_mod_by) {
		this.last_mod_by = last_mod_by;
	}

	public CRM_Req_Request(Long crm_req_id, Date crm_req_date, Date crm_sched_date, String crm_req_crm_name,
			Long crm_req_scheme_id, String crm_req_exps_item, BigDecimal crm_req_qty, BigDecimal crm_req_rate,
			BigDecimal crm_req_value, String crm_req_confirm) {
		super();
		this.crm_req_id = crm_req_id;
		this.crm_req_date = crm_req_date;
		this.crm_sched_date = crm_sched_date;
		this.crm_req_crm_name = crm_req_crm_name;
		this.crm_req_scheme_id = crm_req_scheme_id;
		this.crm_req_exps_item = crm_req_exps_item;
		this.crm_req_qty = crm_req_qty;
		this.crm_req_rate = crm_req_rate;
		this.crm_req_value = crm_req_value;
		this.crm_req_confirm = crm_req_confirm;
	}

	
	public String getAppr_status() {
		return appr_status;
	}

	public void setAppr_status(String appr_status) {
		this.appr_status = appr_status;
	}

	public CRM_Req_Request() {
		super();
	}

	
	
}
