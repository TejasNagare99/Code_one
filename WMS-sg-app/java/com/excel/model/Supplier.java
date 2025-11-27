package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name="supplier")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Supplier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -422340929580401803L;

	@Id
	@Column(name="sup_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sup_id;
	
	@Column(name="sup_type")
	private String sup_type;
	
	@Column(name="sup_nm")
	private String sup_nm;
	
	@Column(name="sup_tin_no")
	private String sup_tin_no;
	
	@Column(name="sup_cst_no")
	private String sup_cst_no;
	
	@Column(name="sup_tan_no")
	private String sup_tan_no;
	
	@Column(name="sup_pan_no")
	private String sup_pan_no;
	
	@Column(name="sup_map_code")
	private String sup_map_code;
	
	@Column(name="sup_ins_usr_id")
	private String sup_ins_usr_id;
	
	@Column(name="sup_mod_usr_id")
	private String sup_mod_usr_id;
	
	@Column(name="sup_ins_dt")
	private Date sup_ins_dt;
	
	@Column(name="sup_mod_dt")
	private Date sup_mod_dt;
	
	@Column(name="sup_ins_ip_add")
	private String sup_ins_ip_add;
	
	@Column(name="sup_mod_ip_add")
	private String sup_mod_ip_add;
	
	@Column(name="sup_status")
	private String sup_status;
	
	@Column(name="sup_name_old")
	private String sup_name_old;
	
	@Column(name="sup_SubComp_id")
	private Long sup_SubComp_id;
	
//	@Column(name="SubComp_id")
//	private Long subcomp_id;
	
	@Column(name="SUP_ADDRESS1")
	private String sup_address1;
	
	@Column(name="SUP_ADDRESS2")
	private String sup_address2;
	
	@Column(name="SUP_ADDRESS3")
	private String sup_address3;
	
	@Column(name="GST_REG_NO")
	private String gst_reg_no;
	
	@Column(name="STATE_ID")
	private Long state_id;
	
	@Column(name="GST_REQ_IND")
	private String gst_req_ind;
	
	public Long getSup_id() {
		return sup_id;
	}

	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	public String getSup_type() {
		return sup_type;
	}

	public void setSup_type(String sup_type) {
		this.sup_type = sup_type;
	}

	public String getSup_nm() {
		return sup_nm;
	}

	public void setSup_nm(String sup_nm) {
		this.sup_nm = sup_nm;
	}

	public String getSup_tin_no() {
		return sup_tin_no;
	}

	public void setSup_tin_no(String sup_tin_no) {
		this.sup_tin_no = sup_tin_no;
	}

	public String getSup_cst_no() {
		return sup_cst_no;
	}

	public void setSup_cst_no(String sup_cst_no) {
		this.sup_cst_no = sup_cst_no;
	}

	public String getSup_tan_no() {
		return sup_tan_no;
	}

	public void setSup_tan_no(String sup_tan_no) {
		this.sup_tan_no = sup_tan_no;
	}

	public String getSup_pan_no() {
		return sup_pan_no;
	}

	public void setSup_pan_no(String sup_pan_no) {
		this.sup_pan_no = sup_pan_no;
	}

	public String getSup_map_code() {
		return sup_map_code;
	}

	public void setSup_map_code(String sup_map_code) {
		this.sup_map_code = sup_map_code;
	}

	public String getSup_ins_usr_id() {
		return sup_ins_usr_id;
	}

	public void setSup_ins_usr_id(String sup_ins_usr_id) {
		this.sup_ins_usr_id = sup_ins_usr_id;
	}

	public String getSup_mod_usr_id() {
		return sup_mod_usr_id;
	}

	public void setSup_mod_usr_id(String sup_mod_usr_id) {
		this.sup_mod_usr_id = sup_mod_usr_id;
	}

	public Date getSup_ins_dt() {
		return sup_ins_dt;
	}

	public void setSup_ins_dt(Date sup_ins_dt) {
		this.sup_ins_dt = sup_ins_dt;
	}

	public Date getSup_mod_dt() {
		return sup_mod_dt;
	}

	public void setSup_mod_dt(Date sup_mod_dt) {
		this.sup_mod_dt = sup_mod_dt;
	}

	public String getSup_ins_ip_add() {
		return sup_ins_ip_add;
	}

	public void setSup_ins_ip_add(String sup_ins_ip_add) {
		this.sup_ins_ip_add = sup_ins_ip_add;
	}

	public String getSup_mod_ip_add() {
		return sup_mod_ip_add;
	}

	public void setSup_mod_ip_add(String sup_mod_ip_add) {
		this.sup_mod_ip_add = sup_mod_ip_add;
	}

	public String getSup_status() {
		return sup_status;
	}

	public void setSup_status(String sup_status) {
		this.sup_status = sup_status;
	}

	public String getSup_name_old() {
		return sup_name_old;
	}

	public void setSup_name_old(String sup_name_old) {
		this.sup_name_old = sup_name_old;
	}

//	public Long getSubcomp_id() {
//		return subcomp_id;
//	}
//
//	public void setSubcomp_id(Long subcomp_id) {
//		this.subcomp_id = subcomp_id;
//	}

	public String getSup_address1() {
		return sup_address1;
	}

	public void setSup_address1(String sup_address1) {
		this.sup_address1 = sup_address1;
	}

	public String getSup_address2() {
		return sup_address2;
	}

	public void setSup_address2(String sup_address2) {
		this.sup_address2 = sup_address2;
	}

	public String getSup_address3() {
		return sup_address3;
	}

	public void setSup_address3(String sup_address3) {
		this.sup_address3 = sup_address3;
	}

	public String getGst_reg_no() {
		return gst_reg_no;
	}

	public void setGst_reg_no(String gst_reg_no) {
		this.gst_reg_no = gst_reg_no;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public String getGst_req_ind() {
		return gst_req_ind;
	}

	public void setGst_req_ind(String gst_req_ind) {
		this.gst_req_ind = gst_req_ind;
	}

	public Long getSup_SubComp_id() {
		return sup_SubComp_id;
	}

	public void setSup_SubComp_id(Long sup_SubComp_id) {
		this.sup_SubComp_id = sup_SubComp_id;
	}

	public Supplier(Long state_id,String gst_reg_no,String gst_req_ind) {
		this.state_id=state_id;
		this.gst_reg_no=gst_reg_no;
		this.gst_req_ind=gst_req_ind;
	}

	public Supplier() {
		// TODO Auto-generated constructor stub
	}
}

