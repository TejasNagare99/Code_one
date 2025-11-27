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
@Table(name = "TDS_CRM_EXPENSE")
public class Tds_Crm_Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CRM_ID")
	private Long crm_id;
	
	@Column(name = "CRM_SUB_COMP_CODE")
	private String crm_sub_comp_code;
	
	@Column(name = "CRM_FINYEAR")
	private String crm_finyear;
	
	@Column(name = "CRM_DATE")
	private Date crm_date;
	
	@Column(name = "CRM_CUST_TYPE")
	private String crm_cust_type;
	
	@Column(name = "CRM_HQ_ID")
	private Long crm_hq_id;
	
	@Column(name = "CRM_FS_ID")
	private Long crm_fs_id;
	
	@Column(name = "CRM_DIV_ID")
	private Long crm_div_id;
	
	@Column(name = "CRM_CUST_ID")
	private String crm_cust_id;
	
	@Column(name = "CRM_CUST_ADDR1")
	private String crm_cust_addr1;
	
	@Column(name = "CRM_CUST_ADDR2")
	private String crm_cust_addr2;
	
	@Column(name = "CRM_CUST_ADDR3")
	private String crm_cust_addr3;
	
	@Column(name = "CRM_CUST_ADDR4")
	private String crm_cust_addr4;
	
	@Column(name = "CRM_CUST_CITY")
	private String crm_cust_city;
	
	@Column(name = "CRM_CUST_PINCODE")
	private String crm_cust_pincode;
	
	@Column(name = "CRM_CUST_MOBILE")
	private String crm_cust_mobile;
	
	@Column(name = "CRM_CUST_EMAIL")
	private String crm_cust_email;
	
	@Column(name = "CRM_CUST_PANNO")
	private String crm_cust_panno;
	
	@Column(name = "CRM_CUST_HCP_NO")
	private String crm_cust_hcp_no;
	
	@Column(name = "CRM_EXPS_DATE")
	private String crm_exps_date;
	
	@Column(name = "CRM_ACC_TRAN_NO")
	private String crm_acc_tran_no;
	
	@Column(name = "CRM_EXPS_DETAILS")
	private String crm_exps_details;
	
	@Column(name = "CRM_QTY")
	private BigDecimal crm_qty;
	
	@Column(name = "CRM_RATE")
	private BigDecimal crm_rate;
	
	@Column(name = "CRM_VALUE")
	private BigDecimal crm_value;
	
	@Column(name = "CRM_TDS_PAID")
	private BigDecimal crm_tds_paid;
	
	@Column(name = "CRM_CUST_NAME")
	private String crm_cust_name;
	
	@Column(name = "CRM_CONFIRM")
	private String crm_confirm;

	@Column(name = "CRM_INS_BY")
	private String crm_ins_by;
	
	@Column(name = "LAST_MOD_DT")
	private Date last_mod_dt;
	
	@Column(name = "LAST_MOD_BY")
	private String last_mod_by;
	
	@Column(name = "CRM_TDS_PENALTY")
	private BigDecimal crm_tds_penalty;
	
	@Column(name = "CRM_TDS_INTEREST")
	private BigDecimal crm_tds_interest;
	
	@Column(name = "GROSSUP_IND")
	private String grossup_ind;
	
	@Column(name = "GROSSUP_CRM_VALUE")
	private BigDecimal grossup_crm_value;
	
	@Column(name = "GROSSUP_PERCENTAGE")
	private BigDecimal grossup_percentage;
	
	@Column(name="CRM_GEN_DTL_ID")
	private Long crm_gen_dtl_id;
	
	@Column(name="CRM_GEN_ID")
	private Long crm_gen_id;
	
	@Transient Long State_id;
	
	
	public Long getCrm_gen_dtl_id() {
		return crm_gen_dtl_id;
	}

	public void setCrm_gen_dtl_id(Long crm_gen_dtl_id) {
		this.crm_gen_dtl_id = crm_gen_dtl_id;
	}

	public Long getCrm_gen_id() {
		return crm_gen_id;
	}

	public void setCrm_gen_id(Long crm_gen_id) {
		this.crm_gen_id = crm_gen_id;
	}

	public String getGrossup_ind() {
		return grossup_ind;
	}

	public void setGrossup_ind(String grossup_ind) {
		this.grossup_ind = grossup_ind;
	}

	public BigDecimal getGrossup_crm_value() {
		return grossup_crm_value;
	}

	public void setGrossup_crm_value(BigDecimal grossup_crm_value) {
		this.grossup_crm_value = grossup_crm_value;
	}

	public BigDecimal getGrossup_percentage() {
		return grossup_percentage;
	}

	public void setGrossup_percentage(BigDecimal grossup_percentage) {
		this.grossup_percentage = grossup_percentage;
	}

	public BigDecimal getCrm_tds_penalty() {
		return crm_tds_penalty;
	}

	public void setCrm_tds_penalty(BigDecimal crm_tds_penalty) {
		this.crm_tds_penalty = crm_tds_penalty;
	}

	public BigDecimal getCrm_tds_interest() {
		return crm_tds_interest;
	}

	public void setCrm_tds_interest(BigDecimal crm_tds_interest) {
		this.crm_tds_interest = crm_tds_interest;
	}

	public String getCrm_ins_by() {
		return crm_ins_by;
	}

	public void setCrm_ins_by(String crm_ins_by) {
		this.crm_ins_by = crm_ins_by;
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

	public String getCrm_confirm() {
		return crm_confirm;
	}

	public void setCrm_confirm(String crm_confirm) {
		this.crm_confirm = crm_confirm;
	}

	public Long getState_id() {
		return State_id;
	}

	public void setState_id(Long state_id) {
		State_id = state_id;
	}

	public String getCrm_cust_name() {
		return crm_cust_name;
	}

	public void setCrm_cust_name(String crm_cust_name) {
		this.crm_cust_name = crm_cust_name;
	}

	public Long getCrm_id() {
		return crm_id;
	}

	public void setCrm_id(Long crm_id) {
		this.crm_id = crm_id;
	}

	public String getCrm_sub_comp_code() {
		return crm_sub_comp_code;
	}

	public void setCrm_sub_comp_code(String crm_sub_comp_code) {
		this.crm_sub_comp_code = crm_sub_comp_code;
	}

	public String getCrm_finyear() {
		return crm_finyear;
	}

	public void setCrm_finyear(String crm_finyear) {
		this.crm_finyear = crm_finyear;
	}

	public Date getCrm_date() {
		return crm_date;
	}

	public void setCrm_date(Date crm_date) {
		this.crm_date = crm_date;
	}

	public String getCrm_cust_type() {
		return crm_cust_type;
	}

	public void setCrm_cust_type(String crm_cust_type) {
		this.crm_cust_type = crm_cust_type;
	}

	public Long getCrm_hq_id() {
		return crm_hq_id;
	}

	public void setCrm_hq_id(Long crm_hq_id) {
		this.crm_hq_id = crm_hq_id;
	}

	public Long getCrm_fs_id() {
		return crm_fs_id;
	}

	public void setCrm_fs_id(Long crm_fs_id) {
		this.crm_fs_id = crm_fs_id;
	}

	public Long getCrm_div_id() {
		return crm_div_id;
	}

	public void setCrm_div_id(Long crm_div_id) {
		this.crm_div_id = crm_div_id;
	}

	public String getCrm_cust_id() {
		return crm_cust_id;
	}

	public void setCrm_cust_id(String crm_cust_id) {
		this.crm_cust_id = crm_cust_id;
	}

	public String getCrm_cust_addr1() {
		return crm_cust_addr1;
	}

	public void setCrm_cust_addr1(String crm_cust_addr1) {
		this.crm_cust_addr1 = crm_cust_addr1;
	}

	public String getCrm_cust_addr2() {
		return crm_cust_addr2;
	}

	public void setCrm_cust_addr2(String crm_cust_addr2) {
		this.crm_cust_addr2 = crm_cust_addr2;
	}

	public String getCrm_cust_addr3() {
		return crm_cust_addr3;
	}

	public void setCrm_cust_addr3(String crm_cust_addr3) {
		this.crm_cust_addr3 = crm_cust_addr3;
	}

	public String getCrm_cust_addr4() {
		return crm_cust_addr4;
	}

	public void setCrm_cust_addr4(String crm_cust_addr4) {
		this.crm_cust_addr4 = crm_cust_addr4;
	}

	public String getCrm_cust_city() {
		return crm_cust_city;
	}

	public void setCrm_cust_city(String crm_cust_city) {
		this.crm_cust_city = crm_cust_city;
	}

	public String getCrm_cust_pincode() {
		return crm_cust_pincode;
	}

	public void setCrm_cust_pincode(String crm_cust_pincode) {
		this.crm_cust_pincode = crm_cust_pincode;
	}

	public String getCrm_cust_mobile() {
		return crm_cust_mobile;
	}

	public void setCrm_cust_mobile(String crm_cust_mobile) {
		this.crm_cust_mobile = crm_cust_mobile;
	}

	public String getCrm_cust_email() {
		return crm_cust_email;
	}

	public void setCrm_cust_email(String crm_cust_email) {
		this.crm_cust_email = crm_cust_email;
	}

	public String getCrm_cust_panno() {
		return crm_cust_panno;
	}

	public void setCrm_cust_panno(String crm_cust_panno) {
		this.crm_cust_panno = crm_cust_panno;
	}

	public String getCrm_cust_hcp_no() {
		return crm_cust_hcp_no;
	}

	public void setCrm_cust_hcp_no(String crm_cust_hcp_no) {
		this.crm_cust_hcp_no = crm_cust_hcp_no;
	}

	public String getCrm_exps_date() {
		return crm_exps_date;
	}

	public void setCrm_exps_date(String crm_exps_date) {
		this.crm_exps_date = crm_exps_date;
	}

	public String getCrm_acc_tran_no() {
		return crm_acc_tran_no;
	}

	public void setCrm_acc_tran_no(String crm_acc_tran_no) {
		this.crm_acc_tran_no = crm_acc_tran_no;
	}

	public String getCrm_exps_details() {
		return crm_exps_details;
	}

	public void setCrm_exps_details(String crm_exps_details) {
		this.crm_exps_details = crm_exps_details;
	}

	public BigDecimal getCrm_qty() {
		return crm_qty;
	}

	public void setCrm_qty(BigDecimal crm_qty) {
		this.crm_qty = crm_qty;
	}

	public BigDecimal getCrm_rate() {
		return crm_rate;
	}

	public void setCrm_rate(BigDecimal crm_rate) {
		this.crm_rate = crm_rate;
	}

	public BigDecimal getCrm_value() {
		return crm_value;
	}

	public void setCrm_value(BigDecimal crm_value) {
		this.crm_value = crm_value;
	}

	public BigDecimal getCrm_tds_paid() {
		return crm_tds_paid;
	}

	public void setCrm_tds_paid(BigDecimal crm_tds_paid) {
		this.crm_tds_paid = crm_tds_paid;
	}
	
	
}
