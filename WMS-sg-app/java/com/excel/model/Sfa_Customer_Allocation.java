package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SFA_CUSTOMER_ALLOCATION")
public class Sfa_Customer_Allocation implements Serializable{

	private static final long serialVersionUID = -9029475411070682864L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROW_ID")
	private Long row_id;
	
	@Column(name = "CUST_ID")
	private Long cust_id;
	
	@Column(name = "ALLOC_ID")
	private Long alloc_id;
	
	@Column(name = "CUST_CD")
	private String cust_cd;
	
	@Column(name = "COMPANY_CD")
	private String company_cd;
	
	@Column(name = "DIV_ID")
	private Long div_id;
	
	@Column(name = "CUST_TYPE_ID")
	private Long cust_type_id;
	
	@Column(name = "APPNT_DT")
	private Date appnt_dt;
	
	@Column(name = "CURR_STATUS")
	private String curr_status;
	
	@Column(name = "DISCONT_DT")
	private Date discont_dt;
	
	@Column(name = "ALLOC_VALID_FROM_DT")
	private Date alloc_valid_from_dt;
	
	@Column(name = "ALLOC_VALID_TO_DT")
	private Date alloc_valid_to_dt;
	
	@Column(name = "OLD_ALLOC_ID")
	private Long old_alloc_id;
	
	@Column(name = "ERP_PARTY_CD")
	private String erp_party_cd;
	
	@Column(name = "SALES_REP_ID")
	private Long sales_rep_id;
	
	@Column(name = "HQ_ID")
	private Long hq_id;
	
	@Column(name = "POOL_IND")
	private String pool_ind;
	
	@Column(name = "CREDIT_LIMIT")
	private Long credit_limit;
	
	@Column(name = "STOP_BILLING_DAYS")
	private Long stop_billing_days;
	
	@Column(name = "CREDIT_DAYS")
	private Long credit_days;
	
	@Column(name = "GRACE_DAYS")
	private Long grace_days;
	
	@Column(name = "DOC_TYPE_ID")
	private Long doc_type_id;
	
	@Column(name = "CASH_DISC")
	private Long cash_disc;
	
	@Column(name = "CASH_DISC_DAYS")
	private Long cash_disc_days;
	
	@Column(name = "ADD_DISC")
	private Long add_disc;
	
	@Column(name = "TRADE_DISC")
	private Long trade_disc;
	
	@Column(name = "RATE_TYPE_ID")
	private Long rate_type_id;
	
	@Column(name = "DELETED")
	private String deleted;
	
	@Column(name = "INVOICING_LIMIT")
	private Long invoicing_limit;
	
	@Column(name = "SUBAC")
	private String subac;
	
	@Column(name = "MFR_PARTY")
	private String mfr_party;
	
	@Column(name = "NSUBAC")
	private String nsubac;
	
	@Column(name = "ALT_DIV_ID")
	private Long alt_div_id;
	
	@Column(name = "INV_GROUP")
	private Long inv_group;
	
	@Column(name = "ROWID")
	private String rowid;
	
	@Column(name = "FS_MAP_CODE")
	private String fs_map_code;

	public Long getRow_id() {
		return row_id;
	}

	public void setRow_id(Long row_id) {
		this.row_id = row_id;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public Long getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(Long alloc_id) {
		this.alloc_id = alloc_id;
	}

	public String getCust_cd() {
		return cust_cd;
	}

	public void setCust_cd(String cust_cd) {
		this.cust_cd = cust_cd;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public Long getCust_type_id() {
		return cust_type_id;
	}

	public void setCust_type_id(Long cust_type_id) {
		this.cust_type_id = cust_type_id;
	}

	public Date getAppnt_dt() {
		return appnt_dt;
	}

	public void setAppnt_dt(Date appnt_dt) {
		this.appnt_dt = appnt_dt;
	}

	public String getCurr_status() {
		return curr_status;
	}

	public void setCurr_status(String curr_status) {
		this.curr_status = curr_status;
	}

	public Date getDiscont_dt() {
		return discont_dt;
	}

	public void setDiscont_dt(Date discont_dt) {
		this.discont_dt = discont_dt;
	}

	public Date getAlloc_valid_from_dt() {
		return alloc_valid_from_dt;
	}

	public void setAlloc_valid_from_dt(Date alloc_valid_from_dt) {
		this.alloc_valid_from_dt = alloc_valid_from_dt;
	}

	public Date getAlloc_valid_to_dt() {
		return alloc_valid_to_dt;
	}

	public void setAlloc_valid_to_dt(Date alloc_valid_to_dt) {
		this.alloc_valid_to_dt = alloc_valid_to_dt;
	}

	public Long getOld_alloc_id() {
		return old_alloc_id;
	}

	public void setOld_alloc_id(Long old_alloc_id) {
		this.old_alloc_id = old_alloc_id;
	}

	public String getErp_party_cd() {
		return erp_party_cd;
	}

	public void setErp_party_cd(String erp_party_cd) {
		this.erp_party_cd = erp_party_cd;
	}

	public Long getSales_rep_id() {
		return sales_rep_id;
	}

	public void setSales_rep_id(Long sales_rep_id) {
		this.sales_rep_id = sales_rep_id;
	}

	public Long getHq_id() {
		return hq_id;
	}

	public void setHq_id(Long hq_id) {
		this.hq_id = hq_id;
	}

	public String getPool_ind() {
		return pool_ind;
	}

	public void setPool_ind(String pool_ind) {
		this.pool_ind = pool_ind;
	}

	public Long getCredit_limit() {
		return credit_limit;
	}

	public void setCredit_limit(Long credit_limit) {
		this.credit_limit = credit_limit;
	}

	public Long getStop_billing_days() {
		return stop_billing_days;
	}

	public void setStop_billing_days(Long stop_billing_days) {
		this.stop_billing_days = stop_billing_days;
	}

	public Long getCredit_days() {
		return credit_days;
	}

	public void setCredit_days(Long credit_days) {
		this.credit_days = credit_days;
	}

	public Long getGrace_days() {
		return grace_days;
	}

	public void setGrace_days(Long grace_days) {
		this.grace_days = grace_days;
	}

	public Long getDoc_type_id() {
		return doc_type_id;
	}

	public void setDoc_type_id(Long doc_type_id) {
		this.doc_type_id = doc_type_id;
	}

	public Long getCash_disc() {
		return cash_disc;
	}

	public void setCash_disc(Long cash_disc) {
		this.cash_disc = cash_disc;
	}

	public Long getCash_disc_days() {
		return cash_disc_days;
	}

	public void setCash_disc_days(Long cash_disc_days) {
		this.cash_disc_days = cash_disc_days;
	}

	public Long getAdd_disc() {
		return add_disc;
	}

	public void setAdd_disc(Long add_disc) {
		this.add_disc = add_disc;
	}

	public Long getTrade_disc() {
		return trade_disc;
	}

	public void setTrade_disc(Long trade_disc) {
		this.trade_disc = trade_disc;
	}

	public Long getRate_type_id() {
		return rate_type_id;
	}

	public void setRate_type_id(Long rate_type_id) {
		this.rate_type_id = rate_type_id;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Long getInvoicing_limit() {
		return invoicing_limit;
	}

	public void setInvoicing_limit(Long invoicing_limit) {
		this.invoicing_limit = invoicing_limit;
	}

	public String getSubac() {
		return subac;
	}

	public void setSubac(String subac) {
		this.subac = subac;
	}

	public String getMfr_party() {
		return mfr_party;
	}

	public void setMfr_party(String mfr_party) {
		this.mfr_party = mfr_party;
	}

	public String getNsubac() {
		return nsubac;
	}

	public void setNsubac(String nsubac) {
		this.nsubac = nsubac;
	}

	public Long getAlt_div_id() {
		return alt_div_id;
	}

	public void setAlt_div_id(Long alt_div_id) {
		this.alt_div_id = alt_div_id;
	}

	public Long getInv_group() {
		return inv_group;
	}

	public void setInv_group(Long inv_group) {
		this.inv_group = inv_group;
	}


	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getFs_map_code() {
		return fs_map_code;
	}

	public void setFs_map_code(String fs_map_code) {
		this.fs_map_code = fs_map_code;
	}

	public Sfa_Customer_Allocation() {
		super();
	}
	
	

}
