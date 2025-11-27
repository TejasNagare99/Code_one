package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "dispatch_register_report2")
@NamedStoredProcedureQuery(name = "dispatch_register_report2", 
procedureName = "dispatch_register_report2",
parameters = {
		@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FIN_YEAR_ID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "dsptype", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
}, resultClasses = dispatch_register_report2.class)

public class dispatch_register_report2 implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ROW")
	private Long row;
	@Column(name="BU")
	private String bu;
	@Column(name="Team_Name")
	private String team_name;
	@Column(name="Team_Code")
	private String team_code;
	@Column(name="Requested_By")
	private String requested_by;
	@Column(name="Request_Date")
	private String request_date;
	@Column(name="Allocated_to_MCLNO")
	private String allocated_to_mclno;
	@Column(name="Name_collegue_Doctor")
	private String name_collegue_doctor;
	@Column(name="Destination")
	private String destination;
	@Column(name="Product_Code")
	private String product_code;
	@Column(name="Product_Type")
	private String product_type;
	@Column(name="Item_Dispatched")
	private String item_dispatched;
	@Column(name="Requested_Qty")
	private String requested_qty;
	@Column(name="Approved_Qty")
	private String approved_qty;
	@Column(name="Dispatched_Qty")
	private String dispatched_qty;
	@Column(name="Batch_No")
	private String batch_no;
	@Column(name="Exp_Date")
	private String exp_date;
	@Column(name="Challan_no")
	private String challan_no;
	@Column(name="Challan_date")
	private String challan_date;
	@Column(name="Dr_Request_letter")
	private String dr_request_letter;
	@Column(name="Courier_Name")
	private String courier_name;
	@Column(name="LR_No")
	private String lr_no;
	@Column(name="LR_Dated")
	private String lr_dated;
	@Column(name="Delivery_Date")
	private String delivery_date;
	@Column(name="POD_Details")
	private String pod_details;
	@Column(name="Recdby")
	private String recdby;
	@Column(name="Dr_Ack")
	private String dr_ack;
	@Column(name="Dr_Ack_Date")
	private String dr_ack_date;
	@Column(name="Email_HCP")
	private String email_hcp;
	@Column(name="Contact_Number_HCP")
	private String contact_number_hcp;
	@Column(name="Address1")
	private String address1;
	@Column(name="Address2")
	private String address2;
	@Column(name="Address3")
	private String address3;
	@Column(name="Address4")
	private String address4;
	@Column(name="Remarks")
	private String remarks;
	@Column(name="TERR_CODE")
	private String territory_code;
	@Column(name="RM")
	private String region;
	@Column(name="DM")
	private String dm;
	@Column(name="delivery_status")
	private String delivery_status;
	
	
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTeam_code() {
		return team_code;
	}
	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}
	public String getRequested_by() {
		return requested_by;
	}
	public void setRequested_by(String requested_by) {
		this.requested_by = requested_by;
	}
	public String getRequest_date() {
		return request_date;
	}
	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}
	public String getAllocated_to_mclno() {
		return allocated_to_mclno;
	}
	public void setAllocated_to_mclno(String allocated_to_mclno) {
		this.allocated_to_mclno = allocated_to_mclno;
	}
	public String getName_collegue_doctor() {
		return name_collegue_doctor;
	}
	public void setName_collegue_doctor(String name_collegue_doctor) {
		this.name_collegue_doctor = name_collegue_doctor;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getItem_dispatched() {
		return item_dispatched;
	}
	public void setItem_dispatched(String item_dispatched) {
		this.item_dispatched = item_dispatched;
	}
	public String getRequested_qty() {
		return requested_qty;
	}
	public void setRequested_qty(String requested_qty) {
		this.requested_qty = requested_qty;
	}
	public String getApproved_qty() {
		return approved_qty;
	}
	public void setApproved_qty(String approved_qty) {
		this.approved_qty = approved_qty;
	}
	public String getDispatched_qty() {
		return dispatched_qty;
	}
	public void setDispatched_qty(String dispatched_qty) {
		this.dispatched_qty = dispatched_qty;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getExp_date() {
		return exp_date;
	}
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getChallan_no() {
		return challan_no;
	}
	public void setChallan_no(String challan_no) {
		this.challan_no = challan_no;
	}
	public String getChallan_date() {
		return challan_date;
	}
	public void setChallan_date(String challan_date) {
		this.challan_date = challan_date;
	}
	public String getDr_request_letter() {
		return dr_request_letter;
	}
	public void setDr_request_letter(String dr_request_letter) {
		this.dr_request_letter = dr_request_letter;
	}
	public String getCourier_name() {
		return courier_name;
	}
	public void setCourier_name(String courier_name) {
		this.courier_name = courier_name;
	}
	public String getLr_no() {
		return lr_no;
	}
	public void setLr_no(String lr_no) {
		this.lr_no = lr_no;
	}
	public String getLr_dated() {
		return lr_dated;
	}
	public void setLr_dated(String lr_dated) {
		this.lr_dated = lr_dated;
	}
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getPod_details() {
		return pod_details;
	}
	public void setPod_details(String pod_details) {
		this.pod_details = pod_details;
	}
	public String getRecdby() {
		return recdby;
	}
	public void setRecdby(String recdby) {
		this.recdby = recdby;
	}
	public String getDr_ack() {
		return dr_ack;
	}
	public void setDr_ack(String dr_ack) {
		this.dr_ack = dr_ack;
	}
	public String getDr_ack_date() {
		return dr_ack_date;
	}
	public void setDr_ack_date(String dr_ack_date) {
		this.dr_ack_date = dr_ack_date;
	}
	public String getEmail_hcp() {
		return email_hcp;
	}
	public void setEmail_hcp(String email_hcp) {
		this.email_hcp = email_hcp;
	}
	public String getContact_number_hcp() {
		return contact_number_hcp;
	}
	public void setContact_number_hcp(String contact_number_hcp) {
		this.contact_number_hcp = contact_number_hcp;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTerritory_code() {
		return territory_code;
	}
	public void setTerritory_code(String territory_code) {
		this.territory_code = territory_code;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getDelivery_status() {
		return delivery_status;
	}
	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}
	
	
}
