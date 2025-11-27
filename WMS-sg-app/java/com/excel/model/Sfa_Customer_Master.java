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
@Table(name = "SFA_CUSTOMER_MASTER")
public class Sfa_Customer_Master implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROW_ID")
	private Long row_id;
	
	@Column(name = "CUST_ID")
	private Long cust_id;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "CITY_ID")
	private Long city_id;
	
	@Column(name = "TRANSPORTER_ID")
	private Long transporter_id;
	
	@Column(name = "STATE_ID")
	private Long state_id;
	
	@Column(name = "TIN_VALID_DT")
	private Date tin_valid_dt;
	
	@Column(name = "CST_VALID_DT")
	private Date cst_valid_dt;
	
	@Column(name = "DL1_VALID_DT")
	private Date dl1_valid_dt;
	
	@Column(name = "DL2_VALID_DT")
	private Date dl2_valid_dt;
	
	@Column(name = "DL3_VALID_DT")
	private Date dl3_valid_dt;
	
	@Column(name = "CST_PERC")
	private Long cst_perc;
	
	@Column(name = "INT_PERC")
	private Long int_perc;
	
	@Column(name = "FORM_TYPE_ID")
	private Long form_type_id;
	
	@Column(name = "TOT_CREDIT_LMT")
	private Long tot_credit_lmt;
	
	@Column(name = "DELETED")
	private String deleted;
	
	@Column(name = "CUST_CD")
	private String cust_cd;
	
	@Column(name = "CUST_NAME")
	private String cust_name;
	
	@Column(name = "SHORT_NAME")
	private String short_name;
	
	@Column(name = "ADDR1")
	private String addr1;
	
	@Column(name = "ADDR2")
	private String addr2;
	
	@Column(name = "ADDR3")
	private String addr3;
	
	@Column(name = "ADDR4")
	private String addr4;
	
	@Column(name = "INV_TYPE_ID")
	private Long inv_type_id;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "TIN_NO")
	private String tin_no;
	
	@Column(name = "CST_NO")
	private String cst_no;
	
	@Column(name = "DRUG_LIC1")
	private String drug_lic1;
	
	@Column(name = "DRUG_LIC2")
	private String drug_lic2;
	
	@Column(name = "DRUG_LIC3")
	private String drug_lic3;
	
	@Column(name = "PAN_NO")
	private String pan_no;
	
	@Column(name = "BANK_NAME")
	private String bank_name;
	
	@Column(name = "BANK_ADDR1")
	private String bank_addr1;
	
	@Column(name = "BANK_ADDR2")
	private String bank_addr2;
	
	@Column(name = "BANK_ADDR3")
	private String bank_addr3;
	
	@Column(name = "TEL_NO")
	private String tel_no;
	
	@Column(name = "FAX")
	private String fax;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "TRANSPORTER")
	private String transporter;
	
	@Column(name = "GL_ACCOUNT")
	private String gl_account;
	
	@Column(name = "ERP_CUST_CD")
	private String erp_cust_cd;
	
	@Column(name = "MFR_PARTY")
	private String mfr_party;
	
	@Column(name = "CST_RIM_IND")
	private String cst_rim_ind;
	
	@Column(name = "PAY_MODE")
	private String pay_mode;
	
	@Column(name = "AIOCD")
	private String aiocd;
	
	@Column(name = "NARRATION")
	private String narration;
	
	@Column(name = "COMPANY_CD")
	private String company_cd;
	
	@Column(name = "ROWID")
	private String rowid;
	
	@Column(name = "LAST_MOD_DT")
	private Date last_mod_dt;

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

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Long getTransporter_id() {
		return transporter_id;
	}

	public void setTransporter_id(Long transporter_id) {
		this.transporter_id = transporter_id;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public Date getTin_valid_dt() {
		return tin_valid_dt;
	}

	public void setTin_valid_dt(Date tin_valid_dt) {
		this.tin_valid_dt = tin_valid_dt;
	}

	public Date getCst_valid_dt() {
		return cst_valid_dt;
	}

	public void setCst_valid_dt(Date cst_valid_dt) {
		this.cst_valid_dt = cst_valid_dt;
	}

	public Date getDl1_valid_dt() {
		return dl1_valid_dt;
	}

	public void setDl1_valid_dt(Date dl1_valid_dt) {
		this.dl1_valid_dt = dl1_valid_dt;
	}

	public Date getDl2_valid_dt() {
		return dl2_valid_dt;
	}

	public void setDl2_valid_dt(Date dl2_valid_dt) {
		this.dl2_valid_dt = dl2_valid_dt;
	}

	public Date getDl3_valid_dt() {
		return dl3_valid_dt;
	}

	public void setDl3_valid_dt(Date dl3_valid_dt) {
		this.dl3_valid_dt = dl3_valid_dt;
	}

	public Long getCst_perc() {
		return cst_perc;
	}

	public void setCst_perc(Long cst_perc) {
		this.cst_perc = cst_perc;
	}

	public Long getInt_perc() {
		return int_perc;
	}

	public void setInt_perc(Long int_perc) {
		this.int_perc = int_perc;
	}

	public Long getForm_type_id() {
		return form_type_id;
	}

	public void setForm_type_id(Long form_type_id) {
		this.form_type_id = form_type_id;
	}

	public Long getTot_credit_lmt() {
		return tot_credit_lmt;
	}

	public void setTot_credit_lmt(Long tot_credit_lmt) {
		this.tot_credit_lmt = tot_credit_lmt;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCust_cd() {
		return cust_cd;
	}

	public void setCust_cd(String cust_cd) {
		this.cust_cd = cust_cd;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getAddr3() {
		return addr3;
	}

	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}

	public String getAddr4() {
		return addr4;
	}

	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}

	public Long getInv_type_id() {
		return inv_type_id;
	}

	public void setInv_type_id(Long inv_type_id) {
		this.inv_type_id = inv_type_id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTin_no() {
		return tin_no;
	}

	public void setTin_no(String tin_no) {
		this.tin_no = tin_no;
	}

	public String getCst_no() {
		return cst_no;
	}

	public void setCst_no(String cst_no) {
		this.cst_no = cst_no;
	}

	public String getDrug_lic1() {
		return drug_lic1;
	}

	public void setDrug_lic1(String drug_lic1) {
		this.drug_lic1 = drug_lic1;
	}

	public String getDrug_lic2() {
		return drug_lic2;
	}

	public void setDrug_lic2(String drug_lic2) {
		this.drug_lic2 = drug_lic2;
	}

	public String getDrug_lic3() {
		return drug_lic3;
	}

	public void setDrug_lic3(String drug_lic3) {
		this.drug_lic3 = drug_lic3;
	}

	public String getPan_no() {
		return pan_no;
	}

	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_addr1() {
		return bank_addr1;
	}

	public void setBank_addr1(String bank_addr1) {
		this.bank_addr1 = bank_addr1;
	}

	public String getBank_addr2() {
		return bank_addr2;
	}

	public void setBank_addr2(String bank_addr2) {
		this.bank_addr2 = bank_addr2;
	}

	public String getBank_addr3() {
		return bank_addr3;
	}

	public void setBank_addr3(String bank_addr3) {
		this.bank_addr3 = bank_addr3;
	}

	public String getTel_no() {
		return tel_no;
	}

	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getGl_account() {
		return gl_account;
	}

	public void setGl_account(String gl_account) {
		this.gl_account = gl_account;
	}

	public String getErp_cust_cd() {
		return erp_cust_cd;
	}

	public void setErp_cust_cd(String erp_cust_cd) {
		this.erp_cust_cd = erp_cust_cd;
	}

	public String getMfr_party() {
		return mfr_party;
	}

	public void setMfr_party(String mfr_party) {
		this.mfr_party = mfr_party;
	}

	public String getCst_rim_ind() {
		return cst_rim_ind;
	}

	public void setCst_rim_ind(String cst_rim_ind) {
		this.cst_rim_ind = cst_rim_ind;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public String getAiocd() {
		return aiocd;
	}

	public void setAiocd(String aiocd) {
		this.aiocd = aiocd;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public Date getLast_mod_dt() {
		return last_mod_dt;
	}

	public void setLast_mod_dt(Date last_mod_dt) {
		this.last_mod_dt = last_mod_dt;
	}

	public Sfa_Customer_Master() {
		super();
	}
	
	

}
