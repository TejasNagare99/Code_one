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
@Table(name = "ALLOC_REQ_HDR")
public class AllocReqHeader {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_REQ_ID")
	private Long alloc_req_id;
	
	@Column(name = "ALLOC_REQ_DATE")
	private Date alloc_req_date;
	
	@Column(name = "FIN_YEAR")
	private String fin_year;
	
	@Column(name = "PERIOD_CODE")
	private String period_code;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "division")
	private String division;
	
	@Column(name = "REQUESTOR_ID")
	private Long requestor_id;
	
	@Column(name = "FS_ID")
	private Long fs_id;
	
	@Column(name = "TERRITORY_ID")
	private Long territory_id;
	
	@Column(name = "REQUEST_NO")
	private String request_no;
	
	@Column(name = "ALLOC_TYPE")
	private String alloc_type;
	
	@Column(name = "REQUEST_TYPE")
	private String request_type;
	
	@Column(name = "SUPPLY_DATE")
	private Date supply_date;
	
	@Column(name = "MEETING_DATE")
	private Date meeting_date;
	
	@Column(name = "FREIGHT_TYPE")
	private String freight_type;
	
	@Column(name = "FREIGHT_VALUE")
	private BigDecimal freight_value;
	
	@Column(name = "RECIPIENT_TYPE")
	private String recipient_type;
	
	@Column(name = "RECIPIENT_NAME")
	private String recipient_name;
	
	@Column(name = "MCL_NUMBER")
	private String mcl_number;
	
	@Column(name = "RECIPIENT_ADDRESS1")
	private String recipient_address1;
	
	@Column(name = "RECIPIENT_ADDRESS2")
	private String recipient_address2;
	
	@Column(name = "RECIPIENT_ADDRESS3")
	private String recipient_address3;
	
	@Column(name = "RECIPIENT_ADDRESS4")
	private String recipient_address4;
	
	@Column(name = "RECIPIENT_CITY")
	private String recipient_city;
	
	@Column(name = "RECIPIENT_PIN")
	private String recipient_pin;
	
	@Column(name = "RECIPIENT_PHONE")
	private String recipient_phone;
	
	@Column(name = "RECIPIENT_EMAIL")
	private String recipient_email;
	
	@Column(name = "MEETING_VENUE")
	private String meeting_venue;
	
	@Column(name = "MEETING_ADDRESS")
	private String meeting_address;
	
	@Column(name = "REQ_REMARKS")
	private String req_remarks;
	
	@Column(name = "REQ_TOTAL_VALUE")
	private BigDecimal req_total_value;
	
	@Column(name = "REQ_ins_user_id")
	private String req_ins_user_id;
	
	@Column(name = "REQ_mod_user_id")
	private String req_mod_user_id;
	
	@Column(name = "REQ_ins_dt")
	private Date req_ins_dt;
	
 	@Column(name = "REQ_mod_dt")
	private Date req_mod_dt;
	
	@Column(name = "REQ_ins_ip_add")
	private String req_ins_ip_add;
	
	@Column(name = "REQ_mod_ip_add")
	private String req_mod_ip_add;
	
	@Column(name = "ALLOC_APPR_STATUS")
	private char alloc_appr_status;
	
	@Column(name = "REQ_STATUS")
	private char req_status;

	@Column(name = "service_req_no")
	private String service_req_no;
	
	@Column(name = "DOC_LETTER")
	private String doc_letter;
	
	@Column(name = "ALLOC_REQ_IMAGE_PATH")
	private String alloc_req_image_path;
	
	@Column(name = "SRT_NUMBER")
	private String srt_number;
	
	@Column(name = "SRT_DATE")
	private Date srt_date;
	
	@Column(name = "SRT_REMARK")
	private String srt_remark;
	
	@Column(name = "BLK_HCP_REQ_ID")
	private Long blk_hcp_req_id;
	
	@Column(name = "BLK_CSV_NAME")
	private String blk_csv_name;
	
	@Column(name = "BLK_HCP_REQ_NO")
	private String blk_hcp_req_no;
	
	@Column(name = "HCP_UNIQUE_ID")
	private String hcp_unique_id;
	
	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "ADDRESS3")
	private String address3;

	@Column(name = "ADDRESS4")
	private String address4;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "HCP_EMAIL")
	private String hcp_email;
	
	@Column(name = "BULK_UPLOAD_DATE")
	private Date bulk_upload_date;

	@Column(name = "HCP_NAME")
	private String hcp_name;
	
	@Column(name = "ADDRESS_CHG")
	private String address_chg;
	
	@Column(name = "ENTRY_TYPE")
	private String entry_type;
	public Long getBlk_hcp_req_id() {
		return blk_hcp_req_id;
	}

	public void setBlk_hcp_req_id(Long blk_hcp_req_id) {
		this.blk_hcp_req_id = blk_hcp_req_id;
	}

	public String getBlk_csv_name() {
		return blk_csv_name;
	}

	public void setBlk_csv_name(String blk_csv_name) {
		this.blk_csv_name = blk_csv_name;
	}

	public String getBlk_hcp_req_no() {
		return blk_hcp_req_no;
	}

	public void setBlk_hcp_req_no(String blk_hcp_req_no) {
		this.blk_hcp_req_no = blk_hcp_req_no;
	}

	public String getHcp_unique_id() {
		return hcp_unique_id;
	}

	public void setHcp_unique_id(String hcp_unique_id) {
		this.hcp_unique_id = hcp_unique_id;
	}
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHcp_email() {
		return hcp_email;
	}

	public void setHcp_email(String hcp_email) {
		this.hcp_email = hcp_email;
	}

	public Date getBulk_upload_date() {
		return bulk_upload_date;
	}

	public void setBulk_upload_date(Date bulk_upload_date) {
		this.bulk_upload_date = bulk_upload_date;
	}

	public String getHcp_name() {
		return hcp_name;
	}

	public void setHcp_name(String hcp_name) {
		this.hcp_name = hcp_name;
	}

	public String getAddress_chg() {
		return address_chg;
	}

	public void setAddress_chg(String address_chg) {
		this.address_chg = address_chg;
	}

	public String getEntry_type() {
		return entry_type;
	}

	public void setEntry_type(String entry_type) {
		this.entry_type = entry_type;
	}

	public String getSrt_number() {
		return srt_number;
	}

	public void setSrt_number(String srt_number) {
		this.srt_number = srt_number;
	}

	public Date getSrt_date() {
		return srt_date;
	}

	public void setSrt_date(Date srt_date) {
		this.srt_date = srt_date;
	}

	public String getSrt_remark() {
		return srt_remark;
	}

	public void setSrt_remark(String srt_remark) {
		this.srt_remark = srt_remark;
	}

	public Long getAlloc_req_id() {
		return alloc_req_id;
	}

	public void setAlloc_req_id(Long alloc_id) {
		this.alloc_req_id = alloc_id;
	}

	public Date getAlloc_req_date() {
		return alloc_req_date;
	}

	public void setAlloc_req_date(Date alloc_req_date) {
		this.alloc_req_date = alloc_req_date;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Long getRequestor_id() {
		return requestor_id;
	}

	public void setRequestor_id(Long requestor_id) {
		this.requestor_id = requestor_id;
	}

	public Long getFs_id() {
		return fs_id;
	}

	public void setFs_id(Long fs_id) {
		this.fs_id = fs_id;
	}

	public String getRequest_no() {
		return request_no;
	}

	public void setRequest_no(String request_no) {
		this.request_no = request_no;
	}

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public Date getSupply_date() {
		return supply_date;
	}

	public void setSupply_date(Date supply_date) {
		this.supply_date = supply_date;
	}

	public Date getMeeting_date() {
		return meeting_date;
	}

	public void setMeeting_date(Date meeting_date) {
		this.meeting_date = meeting_date;
	}

	public String getFreight_type() {
		return freight_type;
	}

	public void setFreight_type(String freight_type) {
		this.freight_type = freight_type;
	}

	public String getRecipient_type() {
		return recipient_type;
	}

	public void setRecipient_type(String recipient_type) {
		this.recipient_type = recipient_type;
	}

	public String getRecipient_name() {
		return recipient_name;
	}

	public void setRecipient_name(String recipient_name) {
		this.recipient_name = recipient_name;
	}

	public String getMcl_number() {
		return mcl_number;
	}

	public void setMcl_number(String mcl_number) {
		this.mcl_number = mcl_number;
	}

	public String getRecipient_address1() {
		return recipient_address1;
	}

	public void setRecipient_address1(String recipient_address1) {
		this.recipient_address1 = recipient_address1;
	}

	public String getRecipient_address2() {
		return recipient_address2;
	}

	public void setRecipient_address2(String recipient_address2) {
		this.recipient_address2 = recipient_address2;
	}

	public String getRecipient_address3() {
		return recipient_address3;
	}

	public void setRecipient_address3(String recipient_address3) {
		this.recipient_address3 = recipient_address3;
	}

	public String getRecipient_address4() {
		return recipient_address4;
	}

	public void setRecipient_address4(String recipient_address4) {
		this.recipient_address4 = recipient_address4;
	}

	public String getRecipient_city() {
		return recipient_city;
	}

	public void setRecipient_city(String recipient_city) {
		this.recipient_city = recipient_city;
	}

	public String getRecipient_pin() {
		return recipient_pin;
	}

	public void setRecipient_pin(String recipient_pin) {
		this.recipient_pin = recipient_pin;
	}

	public String getRecipient_phone() {
		return recipient_phone;
	}

	public void setRecipient_phone(String recipient_phone) {
		this.recipient_phone = recipient_phone;
	}

	public String getRecipient_email() {
		return recipient_email;
	}

	public void setRecipient_email(String recipient_email) {
		this.recipient_email = recipient_email;
	}

	public String getMeeting_address() {
		return meeting_address;
	}

	public void setMeeting_address(String meeting_address) {
		this.meeting_address = meeting_address;
	}

	public String getReq_remarks() {
		return req_remarks;
	}

	public void setReq_remarks(String req_remarks) {
		this.req_remarks = req_remarks;
	}


	public String getReq_ins_user_id() {
		return req_ins_user_id;
	}

	public void setReq_ins_user_id(String req_ins_user_id) {
		this.req_ins_user_id = req_ins_user_id;
	}

	public String getReq_mod_user_id() {
		return req_mod_user_id;
	}

	public void setReq_mod_user_id(String req_mod_user_id) {
		this.req_mod_user_id = req_mod_user_id;
	}

	public Date getReq_ins_dt() {
		return req_ins_dt;
	}

	public void setReq_ins_dt(Date req_ins_dt) {
		this.req_ins_dt = req_ins_dt;
	}

	public Date getReq_mod_dt() {
		return req_mod_dt;
	}

	public void setReq_mod_dt(Date req_mod_dt) {
		this.req_mod_dt = req_mod_dt;
	}

	public String getReq_ins_ip_add() {
		return req_ins_ip_add;
	}

	public void setReq_ins_ip_add(String req_ins_ip_add) {
		this.req_ins_ip_add = req_ins_ip_add;
	}

	public String getReq_mod_ip_add() {
		return req_mod_ip_add;
	}

	public void setReq_mod_ip_add(String req_mod_ip_add) {
		this.req_mod_ip_add = req_mod_ip_add;
	}

	public Long getTerritory_id() {
		return territory_id;
	}

	public void setTerritory_id(Long territory_id) {
		this.territory_id = territory_id;
	}

	public BigDecimal getReq_total_value() {
		return req_total_value;
	}

	public void setReq_total_value(BigDecimal req_total_value) {
		this.req_total_value = req_total_value;
	}

	public String getMeeting_venue() {
		return meeting_venue;
	}

	public void setMeeting_venue(String meeting_venue) {
		this.meeting_venue = meeting_venue;
	}

	public char getAlloc_appr_status() {
		return alloc_appr_status;
	}

	public void setAlloc_appr_status(char alloc_appr_status) {
		this.alloc_appr_status = alloc_appr_status;
	}

	public char getReq_status() {
		return req_status;
	}

	public void setReq_status(char req_status) {
		this.req_status = req_status;
	}

	public BigDecimal getFreight_value() {
		return freight_value;
	}

	public void setFreight_value(BigDecimal freight_value) {
		this.freight_value = freight_value;
	}

	public String getService_req_no() {
		return service_req_no;
	}

	public void setService_req_no(String service_req_no) {
		this.service_req_no = service_req_no;
	}

	public String getDoc_letter() {
		return doc_letter;
	}

	public void setDoc_letter(String doc_letter) {
		this.doc_letter = doc_letter;
	}

	@Override
	public String toString() {
		return "AllocReqHeader [alloc_req_id=" + alloc_req_id + ", alloc_req_date=" + alloc_req_date + ", fin_year="
				+ fin_year + ", period_code=" + period_code + ", company=" + company + ", division=" + division
				+ ", requestor_id=" + requestor_id + ", fs_id=" + fs_id + ", territory_id=" + territory_id
				+ ", request_no=" + request_no + ", alloc_type=" + alloc_type + ", request_type=" + request_type
				+ ", supply_date=" + supply_date + ", meeting_date=" + meeting_date + ", freight_type=" + freight_type
				+ ", freight_value=" + freight_value + ", recipient_type=" + recipient_type + ", recipient_name="
				+ recipient_name + ", mcl_number=" + mcl_number + ", recipient_address1=" + recipient_address1
				+ ", recipient_address2=" + recipient_address2 + ", recipient_address3=" + recipient_address3
				+ ", recipient_address4=" + recipient_address4 + ", recipient_city=" + recipient_city
				+ ", recipient_pin=" + recipient_pin + ", recipient_phone=" + recipient_phone + ", recipient_email="
				+ recipient_email + ", meeting_venue=" + meeting_venue + ", meeting_address=" + meeting_address
				+ ", req_remarks=" + req_remarks + ", req_total_value=" + req_total_value + ", req_ins_user_id="
				+ req_ins_user_id + ", req_mod_user_id=" + req_mod_user_id + ", req_ins_dt=" + req_ins_dt
				+ ", req_mod_dt=" + req_mod_dt + ", req_ins_ip_add=" + req_ins_ip_add + ", req_mod_ip_add="
				+ req_mod_ip_add + ", alloc_appr_status=" + alloc_appr_status + ", req_status=" + req_status
				+ ", service_req_no=" + service_req_no + ", doc_letter=" + doc_letter + ", srt_number=" + srt_number
				+ ", srt_date=" + srt_date + ", srt_remark=" + srt_remark 
				+ ", blk_hcp_req_id=" + blk_hcp_req_id
				+ ", blk_csv_name=" + blk_csv_name + ", blk_hcp_req_no=" + blk_hcp_req_no + ", hcp_unique_id="
				+ hcp_unique_id 
				+ ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3
				+ ", address4=" + address4 + ", city=" + city + ", phone=" + phone + ", hcp_email=" + hcp_email
				+ ", bulk_upload_date=" + bulk_upload_date + ", hcp_name=" + hcp_name + ", address_chg=" + address_chg
				+ ", entry_type=" + entry_type + "]";
	}

	public String getAlloc_req_image_path() {
		return alloc_req_image_path;
	}

	public void setAlloc_req_image_path(String alloc_req_image_path) {
		this.alloc_req_image_path = alloc_req_image_path;
	}

	
}
