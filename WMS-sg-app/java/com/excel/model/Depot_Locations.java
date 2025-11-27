package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DEPOT_LOCATIONS")
public class Depot_Locations {
	
	@Id
	@Column(name="DPTLOC_ID")
	private Long dptLoc_id;
	
	@Column(name="DPTLOC_NAME")
	private String dptLoc_name;
	
	@Column(name="DPTLOC_TYPE")
	private String dptloc_type;
	
	@ManyToOne
	@JoinColumn(name="DPTLOC_SubComp_id")
	private Sub_Company sub_Company;
	
	@Column(name="DPT_status")
	private String dpt_status;
	
	@Column(name="GST_REG_NO")
	private String gst_reg_no;
	
	@Column(name="DPTSTATE_ID")
	private Long dptstate_id;
	
	@Column(name="GST_REQ_IND")
	private String gst_req_ind;
	
	@Column(name="GST_ON_INDV_CHLN")
	private String gst_on_indv_chln;
	
	public Long getDptLoc_id() {
		return dptLoc_id;
	}
	public void setDptLoc_id(Long dptLoc_id) {
		this.dptLoc_id = dptLoc_id;
	}
	public String getDptLoc_name() {
		return dptLoc_name;
	}
	public void setDptLoc_name(String dptLoc_name) {
		this.dptLoc_name = dptLoc_name;
	}
	public Sub_Company getSub_Company() {
		return sub_Company;
	}
	public void setSub_Company(Sub_Company sub_Company) {
		this.sub_Company = sub_Company;
	}
	public String getDpt_status() {
		return dpt_status;
	}
	public void setDpt_status(String dpt_status) {
		this.dpt_status = dpt_status;
	}
	public String getDptloc_type() {
		return dptloc_type;
	}
	public void setDptloc_type(String dptloc_type) {
		this.dptloc_type = dptloc_type;
	}
	public String getGst_reg_no() {
		return gst_reg_no;
	}
	public void setGst_reg_no(String gst_reg_no) {
		this.gst_reg_no = gst_reg_no;
	}
	public Long getDptstate_id() {
		return dptstate_id;
	}
	public void setDptstate_id(Long dptstate_id) {
		this.dptstate_id = dptstate_id;
	}
	public String getGst_req_ind() {
		return gst_req_ind;
	}
	public void setGst_req_ind(String gst_req_ind) {
		this.gst_req_ind = gst_req_ind;
	}
	public String getGst_on_indv_chln() {
		return gst_on_indv_chln;
	}
	public void setGst_on_indv_chln(String gst_on_indv_chln) {
		this.gst_on_indv_chln = gst_on_indv_chln;
	}
}
