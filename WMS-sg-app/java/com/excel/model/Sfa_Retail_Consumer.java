package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SFA_RETAIL_CONSUMER")
public class Sfa_Retail_Consumer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROW_ID")
	private Long row_id;
	
	@Column(name = "SR_NO")
	private Long sr_no;  //Retail_consumers PK
	
	@Column(name = "CUST_ID")
	private Long cust_id;
	
	@Column(name = "CUST_NAME")
	private String cust_name;
	
	@Column(name = "ADDR1")
	private String addr1;
	
	@Column(name = "ADDR2")
	private String addr2;
	
	@Column(name = "ADDR3")
	private String addr3;
	
	@Column(name = "ADDR4")
	private String addr4;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "PINCODE")
	private String pincode;
	
	@Column(name = "TEL_NO")
	private String tel_no;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PAN_NO")
	private String pan_no;
	
	@Column(name = "HCP_NO")
	private String hcp_no;
	
	@Transient private String fs_name;
	@Transient private String locname;
	@Transient private Long locid;
	@Transient private Long fsid;
	@Transient private Long divid;

	public Long getRow_id() {
		return row_id;
	}

	public void setRow_id(Long row_id) {
		this.row_id = row_id;
	}

	public Long getSr_no() {
		return sr_no;
	}

	public void setSr_no(Long sr_no) {
		this.sr_no = sr_no;
	}

	public Long getFsid() {
		return fsid;
	}

	public void setFsid(Long fsid) {
		this.fsid = fsid;
	}

	public Long getDivid() {
		return divid;
	}

	public void setDivid(Long divid) {
		this.divid = divid;
	}

	public Long getLocid() {
		return locid;
	}

	public void setLocid(Long locid) {
		this.locid = locid;
	}

	public String getLocname() {
		return locname;
	}

	public void setLocname(String locname) {
		this.locname = locname;
	}

	public String getFs_name() {
		return fs_name;
	}

	public void setFs_name(String fs_name) {
		this.fs_name = fs_name;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
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

	public String getTel_no() {
		return tel_no;
	}

	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPan_no() {
		return pan_no;
	}

	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}

	public String getHcp_no() {
		return hcp_no;
	}

	public void setHcp_no(String hcp_no) {
		this.hcp_no = hcp_no;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
