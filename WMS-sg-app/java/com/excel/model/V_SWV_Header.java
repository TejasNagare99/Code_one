package com.excel.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_SWV_HEADER")
public class V_SWV_Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SWV_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long swv_id;

	@Column(name = "SWV_FIN_YEAR")
	private String swv_fin_year;
	
	@Column(name = "SWV_COMPANY")
	private String swv_company;

	@Column(name = "SWV_LOC_ID")
	private Long swv_loc_id;

	@Column(name = "SWV_PERIOD_CODE")
	private String swv_period_code;
	
	@Column(name = "SWV_CHALLAN_NO")
	private String swv_challan_no;
	
	@Column(name = "SWV_LR_NO")
	private String swv_lr_no;
	
	@Column(name = "SWV_CASES")
	private Integer swv_cases;
	
	@Column(name = "SWV_TRANSPORTER")
	private String swv_transporter;

	@Column(name = "SWV_SENDER_NAME")
	private String swv_sender_name;

	@Column(name = "SWV_SENDER_ADDRESS1")
	private String swv_sender_address1;

	@Column(name = "SWV_SENDER_ADDRESS2")
	private String swv_sender_address2;

	@Column(name = "SWV_SENDER_ADDRESS3")
	private String swv_sender_address3;

	@Column(name = "SWV_DESTINATION")
	private String swv_destination;

	@Column(name = "SWV_CHALLAN_MSG")
	private String swv_challan_msg;

	@Column(name = "SWV_REMARKS")
	private String swv_remarks;
	
	@Column(name = "SWV_TYPE_NAME")
	private String swv_type_name;
	
	@Column(name = "SWV_STATE_ID")
	private Long swv_state_id;
	
	@Column(name = "State_name")
	private String state_name;
	
	@Column(name = "SWV_CHALLAN_DT")
	private String swv_challan_dt;
	
	@Column(name = "SWV_LR_DT")
	private String swv_lr_dt;
	
	@Column(name = "SWV_status")
	private String swv_status;
	
	@Column(name = "SWV_APPR_STATUS")
	private String swv_appr_status;
	
	@Column(name = "SWV_ins_usr_id")
	private String swv_ins_usr_id;
	
	@Column(name = "SWV_IMG1")
	private String swv_img1;
	
	@Column(name = "SWV_IMG2")
	private String swv_img2;

	public Long getSwv_id() {
		return swv_id;
	}

	public void setSwv_id(Long swv_id) {
		this.swv_id = swv_id;
	}

	public String getSwv_fin_year() {
		return swv_fin_year;
	}

	public void setSwv_fin_year(String swv_fin_year) {
		this.swv_fin_year = swv_fin_year;
	}

	public Long getSwv_loc_id() {
		return swv_loc_id;
	}

	public void setSwv_loc_id(Long swv_loc_id) {
		this.swv_loc_id = swv_loc_id;
	}

	public String getSwv_period_code() {
		return swv_period_code;
	}

	public void setSwv_period_code(String swv_period_code) {
		this.swv_period_code = swv_period_code;
	}

	public String getSwv_challan_no() {
		return swv_challan_no;
	}

	public void setSwv_challan_no(String swv_challan_no) {
		this.swv_challan_no = swv_challan_no;
	}

	public String getSwv_lr_no() {
		return swv_lr_no;
	}

	public void setSwv_lr_no(String swv_lr_no) {
		this.swv_lr_no = swv_lr_no;
	}

	public Integer getSwv_cases() {
		return swv_cases;
	}

	public void setSwv_cases(Integer swv_cases) {
		this.swv_cases = swv_cases;
	}

	public String getSwv_sender_name() {
		return swv_sender_name;
	}

	public void setSwv_sender_name(String swv_sender_name) {
		this.swv_sender_name = swv_sender_name;
	}

	public String getSwv_sender_address1() {
		return swv_sender_address1;
	}

	public void setSwv_sender_address1(String swv_sender_address1) {
		this.swv_sender_address1 = swv_sender_address1;
	}

	public String getSwv_sender_address2() {
		return swv_sender_address2;
	}

	public void setSwv_sender_address2(String swv_sender_address2) {
		this.swv_sender_address2 = swv_sender_address2;
	}

	public String getSwv_sender_address3() {
		return swv_sender_address3;
	}

	public void setSwv_sender_address3(String swv_sender_address3) {
		this.swv_sender_address3 = swv_sender_address3;
	}

	public String getSwv_destination() {
		return swv_destination;
	}

	public void setSwv_destination(String swv_destination) {
		this.swv_destination = swv_destination;
	}

	public String getSwv_challan_msg() {
		return swv_challan_msg;
	}

	public void setSwv_challan_msg(String swv_challan_msg) {
		this.swv_challan_msg = swv_challan_msg;
	}

	public String getSwv_remarks() {
		return swv_remarks;
	}

	public void setSwv_remarks(String swv_remarks) {
		this.swv_remarks = swv_remarks;
	}

	public String getSwv_type_name() {
		return swv_type_name;
	}

	public void setSwv_type_name(String swv_type_name) {
		this.swv_type_name = swv_type_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getSwv_challan_dt() {
		return swv_challan_dt;
	}

	public void setSwv_challan_dt(String swv_challan_dt) {
		this.swv_challan_dt = swv_challan_dt;
	}

	public String getSwv_lr_dt() {
		return swv_lr_dt;
	}

	public void setSwv_lr_dt(String swv_lr_dt) {
		this.swv_lr_dt = swv_lr_dt;
	}

	public String getSwv_status() {
		return swv_status;
	}

	public void setSwv_status(String swv_status) {
		this.swv_status = swv_status;
	}

	public String getSwv_appr_status() {
		return swv_appr_status;
	}

	public void setSwv_appr_status(String swv_appr_status) {
		this.swv_appr_status = swv_appr_status;
	}

	public String getSwv_ins_usr_id() {
		return swv_ins_usr_id;
	}

	public void setSwv_ins_usr_id(String swv_ins_usr_id) {
		this.swv_ins_usr_id = swv_ins_usr_id;
	}

	public String getSwv_company() {
		return swv_company;
	}

	public void setSwv_company(String swv_company) {
		this.swv_company = swv_company;
	}

	public String getSwv_transporter() {
		return swv_transporter;
	}

	public void setSwv_transporter(String swv_transporter) {
		this.swv_transporter = swv_transporter;
	}

	public Long getSwv_state_id() {
		return swv_state_id;
	}

	public void setSwv_state_id(Long swv_state_id) {
		this.swv_state_id = swv_state_id;
	}

	public String getSwv_img1() {
		return swv_img1;
	}

	public void setSwv_img1(String swv_img1) {
		this.swv_img1 = swv_img1;
	}

	public String getSwv_img2() {
		return swv_img2;
	}

	public void setSwv_img2(String swv_img2) {
		this.swv_img2 = swv_img2;
	}

	
	
}
