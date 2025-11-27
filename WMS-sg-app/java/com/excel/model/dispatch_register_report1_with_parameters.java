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
@Table(name = "dispatch_register_report1_with_parameters")
@NamedStoredProcedureQuery(name = "dispatch_register_report1_with_parameters", 
procedureName = "dispatch_register_report1_with_parameters",
parameters = {
		@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FIN_YEAR_ID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "dsptype", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "prod_type_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "prod_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "month_use_per_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "div_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "transporter_name", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "delivery_status", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "sub_team_code", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "chlno", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "doc_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fs_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "chldt", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "doc_resp", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "lrno", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "rm_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "terr_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "lrdt", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "dm_id", mode = ParameterMode.IN, type = String.class),
}, resultClasses = dispatch_register_report1_with_parameters.class)

public class dispatch_register_report1_with_parameters implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ROW")
	private Long row;
	@Column(name="TEAM_NAME")
	private String team_name;
	@Column(name="SUB_TEAM_NAME")
	private String sub_team_name;
	@Column(name="MONTH_OF_USE")
	private String month_of_use;
	@Column(name="EMPLOYEE_NO")
	private String employee_no;
	@Column(name="EMPLOYEE_NAME")
	private String employee_name;
	@Column(name="REGION")
	private String region;
	@Column(name="DESIGNATION")
	private String designation;
	@Column(name="MOBILE")
	private String mobile;
	@Column(name="DM")
	private String dm;
	@Column(name="TERRITORY_CODE")
	private String territory_code;
	@Column(name="TERRITORY_NAME")
	private String territory_name;
	@Column(name="DOCTOR_NAME")
	private String doctor_name;
	@Column(name="DISPATCH_TYPE")
	private String dispatch_type;
	@Column(name="CHALLAN_NO")
	private String challan_no;
	@Column(name="CHALLAN_DATE")
	private String challan_date;
	@Column(name="COURIER")
	private String courier;
	@Column(name="LR_NO")
	private String lr_no;
	@Column(name="LR_DATE")
	private String lr_date;
	@Column(name="CASES")
	private String cases;
	@Column(name="ACT_WT")
	private String act_wt;
	@Column(name="PRODUCT_TYPE")
	private String product_type;
	@Column(name="PRODUCT_NAME")
	private String product_name;
	@Column(name="PRODUCT_CODE")
	private String product_code;
	@Column(name="BATCH")
	private String batch;
	@Column(name="EXPIRY")
	private String expiry;
	@Column(name="DISPATCH_QUANTITY")
	private String dispatch_quantity;
	@Column(name="DELIVERY_DATE")
	private String delivery_date;
	@Column(name="DELIVERY_STATUS")
	private String delivery_status;
	@Column(name="RECIEVED_BY")
	private String received_by;
	@Column(name="COURIER_COMMENTS")
	private String courier_comments;
	@Column(name="REMARKS")
	private String remarks;
	@Column(name="RECEIVER_RESPONSE")
	private String receiver_response;
	@Column(name="EMERGENCY_DTD_REQUESTOR_NAME")
	private String emergency_dtd_requestor_name;
	@Column(name="TEAM_CODE")
	private String team_code;
	@Column(name="ADD1")
	private String add1;
	@Column(name="ADD2")
	private String add2;
	@Column(name="ADD3")
	private String add3;
	@Column(name="ADD4")
	private String add4;
	@Column(name="PIN")
	private String pin;
	@Column(name="DESTINATION")
	private String destination;
	@Column(name="EMAIL_ID")
	private String email_id;
	@Column(name="DSP_REMARKS")
	private String dsp_remarks;
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getSub_team_name() {
		return sub_team_name;
	}
	public void setSub_team_name(String sub_team_name) {
		this.sub_team_name = sub_team_name;
	}
	public String getMonth_of_use() {
		return month_of_use;
	}
	public void setMonth_of_use(String month_of_use) {
		this.month_of_use = month_of_use;
	}
	public String getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getTerritory_code() {
		return territory_code;
	}
	public void setTerritory_code(String territory_code) {
		this.territory_code = territory_code;
	}
	public String getTerritory_name() {
		return territory_name;
	}
	public void setTerritory_name(String territory_name) {
		this.territory_name = territory_name;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getDispatch_type() {
		return dispatch_type;
	}
	public void setDispatch_type(String dispatch_type) {
		this.dispatch_type = dispatch_type;
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
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public String getLr_no() {
		return lr_no;
	}
	public void setLr_no(String lr_no) {
		this.lr_no = lr_no;
	}
	public String getLr_date() {
		return lr_date;
	}
	public void setLr_date(String lr_date) {
		this.lr_date = lr_date;
	}
	public String getCases() {
		return cases;
	}
	public void setCases(String cases) {
		this.cases = cases;
	}
	public String getAct_wt() {
		return act_wt;
	}
	public void setAct_wt(String act_wt) {
		this.act_wt = act_wt;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public String getDispatch_quantity() {
		return dispatch_quantity;
	}
	public void setDispatch_quantity(String dispatch_quantity) {
		this.dispatch_quantity = dispatch_quantity;
	}
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getDelivery_status() {
		return delivery_status;
	}
	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}
	public String getReceived_by() {
		return received_by;
	}
	public void setReceived_by(String received_by) {
		this.received_by = received_by;
	}
	public String getCourier_comments() {
		return courier_comments;
	}
	public void setCourier_comments(String courier_comments) {
		this.courier_comments = courier_comments;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReceiver_response() {
		return receiver_response;
	}
	public void setReceiver_response(String receiver_response) {
		this.receiver_response = receiver_response;
	}
	public String getEmergency_dtd_requestor_name() {
		return emergency_dtd_requestor_name;
	}
	public void setEmergency_dtd_requestor_name(String emergency_dtd_requestor_name) {
		this.emergency_dtd_requestor_name = emergency_dtd_requestor_name;
	}
	public String getTeam_code() {
		return team_code;
	}
	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public String getAdd3() {
		return add3;
	}
	public void setAdd3(String add3) {
		this.add3 = add3;
	}
	public String getAdd4() {
		return add4;
	}
	public void setAdd4(String add4) {
		this.add4 = add4;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getDsp_remarks() {
		return dsp_remarks;
	}
	public void setDsp_remarks(String dsp_remarks) {
		this.dsp_remarks = dsp_remarks;
	}
	

}
