package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "CUSTOMER_MASTER")
public class CustomerMaster implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUST_ID")
	private Long cust_id;
	
	@Column(name = "COMPANY_CD")
	private String company_cd;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "ERP_PLANT_CODE")
	private String erp_plant_code;
	
	@Column(name = "CUST_CD")
	private String cust_cd;
	
	@Column(name = "ERP_CUST_CD")
	private String erp_cust_cd;
	
	@Column(name = "ENT_CUST_CD")
	private String ent_cust_cd;
	
	@Column(name = "CUST_NAME_BILLTO")
	private String cust_name_billto;
	
	@Column(name = "SHORT_NAME_BILLTO")
	private String short_name_billto;
	
	@Column(name = "ADDR1")
	private String addr1;
	
	@Column(name = "ADDR2")
	private String addr2;
	
	@Column(name = "ADDR3")
	private String addr3;
	
	@Column(name = "ADDR4")
	private String addr4;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "PIN_CODE")
	private Long pin_code;
	
	@Column(name = "STATE_CODE")
	private String state_code;
	
	@Column(name = "CUST_NAME_SHIPTO")
	private String cust_name_shipto;
	
	@Column(name = "ADDR1_SHIPTO")
	private String addr1_shipto;
	
	@Column(name = "ADDR2_SHIPTO")
	private String addr2_shipto;
	
	@Column(name = "ADDR3_SHIPTO")
	private String addr3_shipto;
	
	@Column(name = "ADDR4_SHIPTO")
	private String addr4_shipto;
	
	@Column(name = "DESTINATION_SHIPTO")
	private String destination_shipto;
	
	@Column(name = "PIN_CODE_SHIPTO")
	private String pin_code_shipto;
	
	@Column(name = "STATE_CODE_SHIPTO")
	private String state_code_shipto;
	
	@Column(name = "SOLDTOGSTIN_NO")
	private String soldtogstin_no;
	
	@Column(name = "GSTIN_NO")
	private String gstin_no;
	
	@Column(name = "GSTIN_VALID_FROM")
	private Date gstin_valid_from;
	
	@Column(name = "GSTIN_VALID_TO")
	private Date gstin_valid_to;
	
	@Column(name = "DRUG_LIC1")
	private String drug_lic1;
	
	@Column(name = "DRUG_LIC2")
	private String drug_lic2;
	
	@Column(name = "DRUG_LIC3")
	private String drug_lic3;
	
	@Column(name = "DL1_VALID_DT")
	private Date dl1_valid_dt;
	
	@Column(name = "DL2_VALID_DT")
	private Date dl2_valid_dt;
	
	@Column(name = "DL3_VALID_DT")
	private Date dl3_valid_dt;
	
	@Column(name = "PAN_NO")
	private String pan_no;
	
	@Column(name = "TEL_NO")
	private String tel_no;
	
	@Column(name = "MOBILE")
	private String mobile;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "TRANSPORTER")
	private String transporter;
	
	@Column(name = "STD_NARRATION")
	private String std_narration;
	
	@Column(name = "INV_TYPE")
	private String inv_type;
	
	@Column(name = "ALLOW_GRN")
	private String allow_grn;
	
	@Column(name = "CONTACT_PERSON")
	private String contact_person;
	
	@Column(name = "BILLING_STATUS")
	private String billing_status;
	
	@Column(name = "TO_10CR")
	private String to_10cr;
	
	@Column(name = "DISCONTINUED")
	private String discontinued;
	
	@Column(name = "CREATED_BY")
	private String created_by;
	
	@Column(name = "CREATED_DATE")
	private Date created_date;
	
	@Column(name = "LAST_MOD_BY")
	private String last_mod_by;
	
	@Column(name = "LAST_MOD_DATE")
	private Date last_mod_date;
	
	@Column(name = "CUST_CODE_SHIPTO")
	private String cust_code_shipto;
	
	

	public String getCust_code_shipto() {
		return cust_code_shipto;
	}

	public void setCust_code_shipto(String cust_code_shipto) {
		this.cust_code_shipto = cust_code_shipto;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
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

	public String getErp_plant_code() {
		return erp_plant_code;
	}

	public void setErp_plant_code(String erp_plant_code) {
		this.erp_plant_code = erp_plant_code;
	}

	public String getCust_cd() {
		return cust_cd;
	}

	public void setCust_cd(String cust_cd) {
		this.cust_cd = cust_cd;
	}

	public String getErp_cust_cd() {
		return erp_cust_cd;
	}

	public void setErp_cust_cd(String erp_cust_cd) {
		this.erp_cust_cd = erp_cust_cd;
	}

	public String getEnt_cust_cd() {
		return ent_cust_cd;
	}

	public void setEnt_cust_cd(String ent_cust_cd) {
		this.ent_cust_cd = ent_cust_cd;
	}

	public String getCust_name_billto() {
		return cust_name_billto;
	}

	public void setCust_name_billto(String cust_name_billto) {
		this.cust_name_billto = cust_name_billto;
	}

	public String getShort_name_billto() {
		return short_name_billto;
	}

	public void setShort_name_billto(String short_name_billto) {
		this.short_name_billto = short_name_billto;
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Long getPin_code() {
		return pin_code;
	}

	public void setPin_code(Long pin_code) {
		this.pin_code = pin_code;
	}

	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getCust_name_shipto() {
		return cust_name_shipto;
	}

	public void setCust_name_shipto(String cust_name_shipto) {
		this.cust_name_shipto = cust_name_shipto;
	}

	public String getAddr1_shipto() {
		return addr1_shipto;
	}

	public void setAddr1_shipto(String addr1_shipto) {
		this.addr1_shipto = addr1_shipto;
	}

	public String getAddr2_shipto() {
		return addr2_shipto;
	}

	public void setAddr2_shipto(String addr2_shipto) {
		this.addr2_shipto = addr2_shipto;
	}

	public String getAddr3_shipto() {
		return addr3_shipto;
	}

	public void setAddr3_shipto(String addr3_shipto) {
		this.addr3_shipto = addr3_shipto;
	}

	public String getAddr4_shipto() {
		return addr4_shipto;
	}

	public void setAddr4_shipto(String addr4_shipto) {
		this.addr4_shipto = addr4_shipto;
	}

	public String getDestination_shipto() {
		return destination_shipto;
	}

	public void setDestination_shipto(String destination_shipto) {
		this.destination_shipto = destination_shipto;
	}

	public String getPin_code_shipto() {
		return pin_code_shipto;
	}

	public void setPin_code_shipto(String pin_code_shipto) {
		this.pin_code_shipto = pin_code_shipto;
	}

	public String getState_code_shipto() {
		return state_code_shipto;
	}

	public void setState_code_shipto(String state_code_shipto) {
		this.state_code_shipto = state_code_shipto;
	}

	public String getGstin_no() {
		return gstin_no;
	}

	public void setGstin_no(String gstin_no) {
		this.gstin_no = gstin_no;
	}

	public Date getGstin_valid_from() {
		return gstin_valid_from;
	}

	public void setGstin_valid_from(Date gstin_valid_from) {
		this.gstin_valid_from = gstin_valid_from;
	}

	public Date getGstin_valid_to() {
		return gstin_valid_to;
	}

	public void setGstin_valid_to(Date gstin_valid_to) {
		this.gstin_valid_to = gstin_valid_to;
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

	public String getPan_no() {
		return pan_no;
	}

	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}

	public String getTel_no() {
		return tel_no;
	}

	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getStd_narration() {
		return std_narration;
	}

	public void setStd_narration(String std_narration) {
		this.std_narration = std_narration;
	}

	public String getInv_type() {
		return inv_type;
	}

	public void setInv_type(String inv_type) {
		this.inv_type = inv_type;
	}

	public String getAllow_grn() {
		return allow_grn;
	}

	public void setAllow_grn(String allow_grn) {
		this.allow_grn = allow_grn;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getBilling_status() {
		return billing_status;
	}

	public void setBilling_status(String billing_status) {
		this.billing_status = billing_status;
	}

	public String getTo_10cr() {
		return to_10cr;
	}

	public void setTo_10cr(String to_10cr) {
		this.to_10cr = to_10cr;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	

	public String getLast_mod_by() {
		return last_mod_by;
	}

	public void setLast_mod_by(String last_mod_by) {
		this.last_mod_by = last_mod_by;
	}


	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getLast_mod_date() {
		return last_mod_date;
	}

	public void setLast_mod_date(Date last_mod_date) {
		this.last_mod_date = last_mod_date;
	}
	
	public String getSoldtogstin_no() {
		return soldtogstin_no;
	}

	public void setSoldtogstin_no(String soldtogstin_no) {
		this.soldtogstin_no = soldtogstin_no;
	}

	@Override
	public String toString() {
		return "CUSTOMER_MASTER [cust_id=" + cust_id + ", company_cd=" + company_cd + ", loc_id=" + loc_id
				+ ", erp_plant_code=" + erp_plant_code + ", cust_cd=" + cust_cd + ", erp_cust_cd=" + erp_cust_cd
				+ ", ent_cust_cd=" + ent_cust_cd + ", cust_name_billto=" + cust_name_billto + ", short_name_billto="
				+ short_name_billto + ", addr1=" + addr1 + ", addr2=" + addr2 + ", addr3=" + addr3 + ", addr4=" + addr4
				+ ", destination=" + destination + ", pin_code=" + pin_code + ", state_code=" + state_code
				+ ", cust_name_shipto=" + cust_name_shipto + ", addr1_shipto=" + addr1_shipto + ", addr2_shipto="
				+ addr2_shipto + ", addr3_shipto=" + addr3_shipto + ", addr4_shipto=" + addr4_shipto
				+ ", destination_shipto=" + destination_shipto + ", pin_code_shipto=" + pin_code_shipto
				+ ", state_code_shipto=" + state_code_shipto + ", gstin_no=" + gstin_no + ", gstin_valid_from="
				+ gstin_valid_from + ", gstin_valid_to=" + gstin_valid_to + ", drug_lic1=" + drug_lic1 + ", drug_lic2="
				+ drug_lic2 + ", drug_lic3=" + drug_lic3 + ", dl1_valid_dt=" + dl1_valid_dt + ", dl2_valid_dt="
				+ dl2_valid_dt + ", dl3_valid_dt=" + dl3_valid_dt + ", pan_no=" + pan_no + ", tel_no=" + tel_no
				+ ", mobile=" + mobile + ", email=" + email + ", transporter=" + transporter + ", std_narration="
				+ std_narration + ", inv_type=" + inv_type + ", allow_grn=" + allow_grn + ", contact_person="
				+ contact_person + ", billing_status=" + billing_status + ", to_10cr=" + to_10cr + ", discontinued="
				+ discontinued + ", created_by=" + created_by + ", created_date=" + created_date + ", last_mod_by="
				+ last_mod_by + ", last_mod_date=" + last_mod_date + "]";
	}

	public CustomerMaster() {
		super();
	}
	public CustomerMaster(Long cust_id, String cust_name_billto) {
		super();
		this.cust_id = cust_id;
		this.cust_name_billto = cust_name_billto;
	}

	public CustomerMaster(Long cust_id) {
		super();
		this.cust_id = cust_id;
	}

	public CustomerMaster(String cust_name_billto) {
		super();
		this.cust_name_billto = cust_name_billto;
	}

	public CustomerMaster(Long loc_id,Long cust_id, String erp_cust_cd, String cust_name_billto, String cust_name_shipto,
			String destination_shipto, String gstin_no, String cust_code_shipto,String soldtogstin_no) {
		super();
		this.loc_id = loc_id;
		this.cust_id = cust_id;
		this.erp_cust_cd = erp_cust_cd;
		this.cust_name_billto = cust_name_billto;
		this.cust_name_shipto = cust_name_shipto;
		this.destination_shipto = destination_shipto;
		this.gstin_no = gstin_no;
		this.cust_code_shipto = cust_code_shipto;
		this.soldtogstin_no = soldtogstin_no;
	}
	
}
