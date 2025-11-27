package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CRM_SCHEME_MST_HDR")
public class CrmSchemeMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "CRM_SCH_ID")
	private Long crm_sch_id;
	
	@Column(name = "COMPANY_CD")
	private String company_cd;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "CRM_SCHEME_ID")
	private Long crm_scheme_id;
		
	@Column(name = "CRM_SCHEME_CODE")
	private String crm_scheme_code;
	
	@Column(name = "CRM_SCHEME_NAME")
	private String crm_scheme_name;
	
	@Column(name = "CRM_SCHEME_DESCR")
	private String crm_scheme_descr;
	
	@Column(name = "VALID_FROM_DT")
	private Date valid_from_dt;
	
	@Column(name = "VALID_TO_DT")
	private Date valid_to_dt;
	
	@Column(name = "CRM_SCH_TYPE")
	private String crm_sch_type;
	
	@Column(name = "CUSTOMER_TYPE")
	private String customer_type;
	
	@Column(name = "DOCTOR_TYPE")
	private String doctor_type;
	
	@Column(name = "DOCTOR_SPECIALITY")
	private String doctor_speciality;
	
	@Column(name = "DOCTOR_CATEGORY")
	private String doctor_category;
	
	@Column(name = "DOC_CURRENT_BIZ_FROM")
	private Long doc_current_biz_from;
	
	@Column(name = "DOC_CURRENT_BIZ_TO")
	private Long doc_current_biz_to;
	
	@Column(name = "DOC_EXPECTED_BIZ_FROM")
	private Long doc_expected_biz_from;
	
	@Column(name = "DOC_EXPECTED_BIZ_TO")
	private Long doc_expected_biz_to;
	
	@Column(name = "ROUTE_TYPE")
	private String route_type;
	
	@Column(name = "CUST_CURRENT_BIZ_FROM")
	private Long cust_current_biz_from;
	
	@Column(name = "CUST_CURRENT_BIZ_TO")
	private Long cust_current_biz_to;
	
	@Column(name = "CUST_EXPECTED_BIZ_FROM")
	private Long cust_expected_biz_from;
	
	@Column(name = "CUST_EXPECTED_BIZ_TO")
	private Long cust_expected_biz_to;
	
	@Column(name = "SCHEME_EXPE_ITEM")
	private String scheme_expe_item;
	
	@Column(name = "SCHEME_EXP_ITEM_QTY")
	private BigDecimal scheme_exp_item_qty;
	
	@Column(name = "SCHEME_EXP_ITEM_RATE")
	private BigDecimal scheme_exp_item_rate;
	
	@Column(name = "SCHEME_EXP_VALUE")
	private BigDecimal scheme_exp_value;
	
	@Column(name = "TOLERANCE_PERC")
	private BigDecimal tolerance_perc;
	
	@Column(name = "DELETED")
	private String deleted;
	
	@Column(name = "LAST_MOD_BY")
	private String last_mod_by;
	
	@Column(name = "LAST_MOD_DATE")
	private Date last_mod_date;
	
	@Column(name = "CANCELLED")
	private String cancelled;
	
	@Column(name = "APPROVAL_STATUS")
	private String approval_status;
	
	
	

	

	public String getApproval_status() {
		return approval_status;
	}

	public void setApproval_status(String approval_status) {
		this.approval_status = approval_status;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}



	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public Long getCrm_sch_id() {
		return crm_sch_id;
	}

	public void setCrm_sch_id(Long crm_sch_id) {
		this.crm_sch_id = crm_sch_id;
	}

	public String getCancelled() {
		return cancelled;
	}

	public void setCancelled(String cancelled) {
		this.cancelled = cancelled;
	}

	public String getCrm_scheme_code() {
		return crm_scheme_code;
	}

	public void setCrm_scheme_code(String crm_scheme_code) {
		this.crm_scheme_code = crm_scheme_code;
	}

	public String getCrm_scheme_name() {
		return crm_scheme_name;
	}

	public void setCrm_scheme_name(String crm_scheme_name) {
		this.crm_scheme_name = crm_scheme_name;
	}

	public String getCrm_scheme_descr() {
		return crm_scheme_descr;
	}

	public void setCrm_scheme_descr(String crm_scheme_descr) {
		this.crm_scheme_descr = crm_scheme_descr;
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

	public String getCrm_sch_type() {
		return crm_sch_type;
	}

	public void setCrm_sch_type(String crm_sch_type) {
		this.crm_sch_type = crm_sch_type;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getDoctor_type() {
		return doctor_type;
	}

	public void setDoctor_type(String doctor_type) {
		this.doctor_type = doctor_type;
	}

	public String getDoctor_speciality() {
		return doctor_speciality;
	}

	public void setDoctor_speciality(String doctor_speciality) {
		this.doctor_speciality = doctor_speciality;
	}

	public String getDoctor_category() {
		return doctor_category;
	}

	public void setDoctor_category(String doctor_category) {
		this.doctor_category = doctor_category;
	}



	public String getRoute_type() {
		return route_type;
	}

	public void setRoute_type(String route_type) {
		this.route_type = route_type;
	}

	

	public Long getDoc_current_biz_from() {
		return doc_current_biz_from;
	}

	public void setDoc_current_biz_from(Long doc_current_biz_from) {
		this.doc_current_biz_from = doc_current_biz_from;
	}

	public Long getDoc_current_biz_to() {
		return doc_current_biz_to;
	}

	public void setDoc_current_biz_to(Long doc_current_biz_to) {
		this.doc_current_biz_to = doc_current_biz_to;
	}

	public Long getDoc_expected_biz_from() {
		return doc_expected_biz_from;
	}

	public void setDoc_expected_biz_from(Long doc_expected_biz_from) {
		this.doc_expected_biz_from = doc_expected_biz_from;
	}

	public Long getDoc_expected_biz_to() {
		return doc_expected_biz_to;
	}

	public void setDoc_expected_biz_to(Long doc_expected_biz_to) {
		this.doc_expected_biz_to = doc_expected_biz_to;
	}

	public Long getCust_current_biz_from() {
		return cust_current_biz_from;
	}

	public void setCust_current_biz_from(Long cust_current_biz_from) {
		this.cust_current_biz_from = cust_current_biz_from;
	}

	public Long getCust_current_biz_to() {
		return cust_current_biz_to;
	}

	public void setCust_current_biz_to(Long cust_current_biz_to) {
		this.cust_current_biz_to = cust_current_biz_to;
	}

	public Long getCust_expected_biz_from() {
		return cust_expected_biz_from;
	}

	public void setCust_expected_biz_from(Long cust_expected_biz_from) {
		this.cust_expected_biz_from = cust_expected_biz_from;
	}

	public Long getCust_expected_biz_to() {
		return cust_expected_biz_to;
	}

	public void setCust_expected_biz_to(Long cust_expected_biz_to) {
		this.cust_expected_biz_to = cust_expected_biz_to;
	}

	public String getScheme_expe_item() {
		return scheme_expe_item;
	}

	public void setScheme_expe_item(String scheme_expe_item) {
		this.scheme_expe_item = scheme_expe_item;
	}


	public BigDecimal getScheme_exp_item_qty() {
		return scheme_exp_item_qty;
	}

	public void setScheme_exp_item_qty(BigDecimal scheme_exp_item_qty) {
		this.scheme_exp_item_qty = scheme_exp_item_qty;
	}

	public BigDecimal getScheme_exp_item_rate() {
		return scheme_exp_item_rate;
	}

	public void setScheme_exp_item_rate(BigDecimal scheme_exp_item_rate) {
		this.scheme_exp_item_rate = scheme_exp_item_rate;
	}

	public BigDecimal getScheme_exp_value() {
		return scheme_exp_value;
	}

	public void setScheme_exp_value(BigDecimal scheme_exp_value) {
		this.scheme_exp_value = scheme_exp_value;
	}

	public BigDecimal getTolerance_perc() {
		return tolerance_perc;
	}

	public void setTolerance_perc(BigDecimal tolerance_perc) {
		this.tolerance_perc = tolerance_perc;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
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

	

	public Long getCrm_scheme_id() {
		return crm_scheme_id;
	}

	public void setCrm_scheme_id(Long crm_scheme_id) {
		this.crm_scheme_id = crm_scheme_id;
	}

	@Override
	public String toString() {
		return "CrmSchemeMaster [crm_sch_id=" + crm_sch_id + ", company_cd=" + company_cd + ", loc_id=" + loc_id
				+ "]";

	
	}

	public CrmSchemeMaster(String crm_scheme_code) {
		super();
		this.crm_scheme_code = crm_scheme_code;
	}

	public CrmSchemeMaster() {
		super();
	}
	
	
	

}
