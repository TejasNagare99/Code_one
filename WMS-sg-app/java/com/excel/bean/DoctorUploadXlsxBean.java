package com.excel.bean;

public class DoctorUploadXlsxBean {

	private String fin_year;
	private String period_code;
	private String alloc_uniq_no;
	
	
	private  String custId;
	private static String custId1;
	private String hcpname;
	private String speciality;
	private String address;
	
	private static String hcpnames;
	public static String getCustId1() {
		return custId1;
	}
	public static void setCustId1(String custId1) {
		DoctorUploadXlsxBean.custId1 = custId1;
	}
	public static String getHcpnames() {
		return hcpnames;
	}
	public static void setHcpnames(String hcpnames) {
		DoctorUploadXlsxBean.hcpnames = hcpnames;
	}
	private String city;
	private String pincode;
	private String mobile;
	private String emailid;
	private String cluster;
	private String statename;
	
	
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public String getFin_year() {
		return fin_year;
	}
	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}
	public String getPeriod_code() {
		return period_code;
	}
	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}
	public String getAlloc_uniq_no() {
		return alloc_uniq_no;
	}
	public void setAlloc_uniq_no(String alloc_uniq_no) {
		this.alloc_uniq_no = alloc_uniq_no;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
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
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
}
