package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Transporter_Master")
public class Transporter_master {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TRANS_ID")
	private long trans_id;
	
	@Column(name="TRANS_COMPANY")
	private String trans_company;
	
	@Column(name="TRANS_CODE")
	private String trans_code;
	
	@Column(name="TRANSPORTER_NAME")
	private String transporter_name;
	
	@Column(name="TRANSPORTER_ALTER_NAME")
	private String transporter_alter_name;
	
	@Column(name="trans_ins_usr_id")
	private String trans_ins_usr_id;
	
	@Column(name="trans_mod_usr_id")
	private String trans_mod_usr_id	;
	
	@Column(name="trans_ins_dt")
	private Date trans_ins_dt;
	
	@Column(name="trans_mod_dt")
	private Date trans_mod_dt;
	
	@Column(name="trans_ins_ip_add")
	private String trans_ins_ip_add;
	
	@Column(name="trans_mod_ip_add")
	private String trans_mod_ip_add;
	
	@Column(name="trans_status")
	private String trans_status;
	
	@Column(name="trans_pers_resp1")
	private String trans_pers_resp1;
	
	@Column(name="trans_pers_resp2")
	private String trans_pers_resp2;
	
	@Column(name="trans_email1")
	private String trans_email1;
	
	@Column(name="trans_email2")
	private String trans_email2;
	
	@Column(name="trans_contact_no1")
	private String trans_contact_no1;
	
	@Column(name="trans_contact_no2")
	private String trans_contact_no2;
	
	@Column(name="trans_website_name")
	private String trans_website_name;
	
	
	
	@Column(name="TRANS_GST_REG_NO")
	private String trans_gst_reg_no;
	
	@Column(name="LOC_ID")
	private String loc_id;
	
	
	
	
	
	public String getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}

	public String getTrans_gst_reg_no() {
		return trans_gst_reg_no;
	}

	public void setTrans_gst_reg_no(String trans_gst_reg_no) {
		this.trans_gst_reg_no = trans_gst_reg_no;
	}

	public String getTrans_pers_resp1() {
		return trans_pers_resp1;
	}

	public void setTrans_pers_resp1(String trans_pers_resp1) {
		this.trans_pers_resp1 = trans_pers_resp1;
	}

	public String getTrans_pers_resp2() {
		return trans_pers_resp2;
	}

	public void setTrans_pers_resp2(String trans_pers_resp2) {
		this.trans_pers_resp2 = trans_pers_resp2;
	}

	public String getTrans_email1() {
		return trans_email1;
	}

	public void setTrans_email1(String trans_email1) {
		this.trans_email1 = trans_email1;
	}

	public String getTrans_email2() {
		return trans_email2;
	}

	public void setTrans_email2(String trans_email2) {
		this.trans_email2 = trans_email2;
	}

	public String getTrans_contact_no1() {
		return trans_contact_no1;
	}

	public void setTrans_contact_no1(String trans_contact_no1) {
		this.trans_contact_no1 = trans_contact_no1;
	}

	public String getTrans_contact_no2() {
		return trans_contact_no2;
	}

	public void setTrans_contact_no2(String trans_contact_no2) {
		this.trans_contact_no2 = trans_contact_no2;
	}

	public String getTrans_website_name() {
		return trans_website_name;
	}

	public void setTrans_website_name(String trans_website_name) {
		this.trans_website_name = trans_website_name;
	}

	public Transporter_master() {
		super();
	}

	public Transporter_master(String transporter_name) {
		super();
		this.transporter_name = transporter_name;
	}


	public long getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(long trans_id) {
		this.trans_id = trans_id;
	}

	public String getTrans_company() {
		return trans_company;
	}

	public void setTrans_company(String trans_company) {
		this.trans_company = trans_company;
	}

	public String getTrans_code() {
		return trans_code;
	}

	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}

	public String getTransporter_name() {
		return transporter_name;
	}

	public void setTransporter_name(String transporter_name) {
		this.transporter_name = transporter_name;
	}

	public String getTransporter_alter_name() {
		return transporter_alter_name;
	}

	public void setTransporter_alter_name(String transporter_alter_name) {
		this.transporter_alter_name = transporter_alter_name;
	}

	public String getTrans_ins_usr_id() {
		return trans_ins_usr_id;
	}

	public void setTrans_ins_usr_id(String trans_ins_usr_id) {
		this.trans_ins_usr_id = trans_ins_usr_id;
	}

	public String getTrans_mod_usr_id() {
		return trans_mod_usr_id;
	}

	public void setTrans_mod_usr_id(String trans_mod_usr_id) {
		this.trans_mod_usr_id = trans_mod_usr_id;
	}

	public Date getTrans_ins_dt() {
		return trans_ins_dt;
	}

	public void setTrans_ins_dt(Date date) {
		this.trans_ins_dt = date;
	}
	

	public Date getTrans_mod_dt() {
		return trans_mod_dt;
	}

	public void setTrans_mod_dt(Date trans_mod_dt) {
		this.trans_mod_dt = trans_mod_dt;
	}

	public String getTrans_ins_ip_add() {
		return trans_ins_ip_add;
	}

	public void setTrans_ins_ip_add(String trans_ins_ip_add) {
		this.trans_ins_ip_add = trans_ins_ip_add;
	}

	public String getTrans_mod_ip_add() {
		return trans_mod_ip_add;
	}

	public void setTrans_mod_ip_add(String trans_mod_ip_add) {
		this.trans_mod_ip_add = trans_mod_ip_add;
	}

	public String getTrans_status() {
		return trans_status;
	}

	public void setTrans_status(String trans_status) {
		this.trans_status = trans_status;
	}


	
	
}
