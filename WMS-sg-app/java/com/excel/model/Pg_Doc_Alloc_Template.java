package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="PG_DOC_ALLOC_TEMPLATE")
public class Pg_Doc_Alloc_Template {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SLNO")
	private Long slno;
	
	@Column(name="ALLOC_UNIQ_NO")
	private String alloc_uniq_no;
	
	@Column(name="CUSTID")
	private String custid;
	
	@Column(name="HCPNAME")
	private String hcpname;
	
	@Column(name="SPECIALITY")
	private String speciality;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="PINCODE")
	private String pincode;
	
	@Column(name="MOBILE")
	private String mobile;
	
	@Column(name="EMAILID")
	private String emailid;
	
	@Column(name="CLUSTER")
	private String cluster;
	
	@Column(name="PROD_CODE")
	private String prod_code;
	
	@Column(name="PROD_ID")
	private Long prod_id;
	
	@Column(name="QTY")
	private String qty;
	
	@Column(name="DOC_ID")
	private Long doc_id;
	
	@Column(name="DOC_FS_ID")
	private Long doc_fs_id;
	
	@Column(name="ALLOC_CYCLE")
	private Long alloc_cycle;
	
	@Column(name="PERIOD_CODE")
	private String period_code;
	
	@Column(name="FIN_YEAR")
	private String fin_year;

	
	@Column(name="ALLOC_DATE")
	private Date alloc_date;
	
	@Column(name="PROCESSED")
	private String processed;
	
	@Column(name="PROCESSED_DATE")
	private String processed_date;
	
	@Column(name="ALLOC_ID")
	private String alloc_id;
	
	@Column(name="pri_no")
	private Long pri_no;
	
	@Column(name="STATE_NAME")
	private String state_name;
	
	@Column(name="STATE_ID")
	private Long state_id;
	
	@Column(name="ALLOC_TYPE")
	private String alloc_type;
	
	

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public Long getPri_no() {
		return pri_no;
	}

	public void setPri_no(Long pri_no) {
		this.pri_no = pri_no;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getProcessed_date() {
		return processed_date;
	}

	public void setProcessed_date(String processed_date) {
		this.processed_date = processed_date;
	}

	public String getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(String alloc_id) {
		this.alloc_id = alloc_id;
	}

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public String getAlloc_uniq_no() {
		return alloc_uniq_no;
	}

	public void setAlloc_uniq_no(String alloc_uniq_no) {
		this.alloc_uniq_no = alloc_uniq_no;
	}



	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getHcpname() {
		return hcpname;
	}

	public void setHcpname(String hcpname) {
		this.hcpname = hcpname;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public Long getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(Long doc_id) {
		this.doc_id = doc_id;
	}

	public Long getDoc_fs_id() {
		return doc_fs_id;
	}

	public void setDoc_fs_id(Long doc_fs_id) {
		this.doc_fs_id = doc_fs_id;
	}

	public Long getAlloc_cycle() {
		return alloc_cycle;
	}

	public void setAlloc_cycle(Long alloc_cycle) {
		this.alloc_cycle = alloc_cycle;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public Date getAlloc_date() {
		return alloc_date;
	}

	public void setAlloc_date(Date alloc_date) {
		this.alloc_date = alloc_date;
	}
	
	

}
