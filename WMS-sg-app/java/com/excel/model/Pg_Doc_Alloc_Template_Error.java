package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="PG_DOC_ALLOC_TEMPLATE_ERROR")
public class Pg_Doc_Alloc_Template_Error {
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
	
	@Column(name="PG_SLNO")
	private Long pg_slno;
	
	@Column(name="ERROR_MSG")
	private String error_msg;
	
	@Column(name="ERROR_DATE")
	private String error_date;
	
	@Column(name="ALLOC_DATE")
	private Date alloc_date;

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

	public Long getPg_slno() {
		return pg_slno;
	}

	public void setPg_slno(Long pg_slno) {
		this.pg_slno = pg_slno;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getError_date() {
		return error_date;
	}

	public void setError_date(String error_date) {
		this.error_date = error_date;
	}

	public Date getAlloc_date() {
		return alloc_date;
	}

	public void setAlloc_date(Date alloc_date) {
		this.alloc_date = alloc_date;
	}
	
	
}

	