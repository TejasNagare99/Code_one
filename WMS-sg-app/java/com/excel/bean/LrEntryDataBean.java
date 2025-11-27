package com.excel.bean;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class LrEntryDataBean {
	private String sum_dsp_hd_no;
	private boolean master;
	private String id;
	private String master_stn_no;
	private String date_of_stn;
	private String team_name;
	private String team_code;
	private String destination;
	private String transporter;
	private String location;
	private String lr_no;
	private String lr_date;
	private String exp_delivery_date;
	private String weight;
	private String billable_wt;
	private String total_no_cases;
	private String lr_charges;
	private String challan_value;
	private String challan_rec_date;
	private String challan_rec_by;
	private String way_bill_no;
	private String remark;

	// confirmation dispatch
	private long srno;

	// transporter gst
	private String trans_gst_reg_no;
	private String vehNo;
	
	private String transport_mode;
	
	private String dtl_transport_mode;
			
	public String getDtl_transport_mode() {
		return dtl_transport_mode;
	}
	public void setDtl_transport_mode(String dtl_transport_mode) {
		this.dtl_transport_mode = dtl_transport_mode;
	}
	public String getTransport_mode() {
		return transport_mode;
	}
	public void setTransport_mode(String transport_mode) {
		this.transport_mode = transport_mode;
	}
	public String getVehNo() {
		return vehNo;
	}
	public void setVehNo(String vehNo) {
		this.vehNo = vehNo;
	}

	public String getTrans_gst_reg_no() {
		return trans_gst_reg_no;
	}

	public void setTrans_gst_reg_no(String trans_gst_reg_no) {
		this.trans_gst_reg_no = trans_gst_reg_no;
	}

	public long getSrno() {
		return srno;
	}

	public void setSrno(long srno) {
		this.srno = srno;
	}

	public String getMaster_stn_no() {
		return master_stn_no;
	}

	public void setMaster_stn_no(String master_stn_no) {
		this.master_stn_no = master_stn_no;
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getBillable_wt() {
		return billable_wt;
	}

	public void setBillable_wt(String billable_wt) {
		this.billable_wt = billable_wt;
	}

	public String getTotal_no_cases() {
		return total_no_cases;
	}

	public void setTotal_no_cases(String total_no_cases) {
		this.total_no_cases = total_no_cases;
	}

	public String getLr_charges() {
		return lr_charges;
	}

	public void setLr_charges(String lr_charges) {
		this.lr_charges = lr_charges;
	}

	public String getChallan_value() {
		return challan_value;
	}

	public void setChallan_value(String challan_value) {
		this.challan_value = challan_value;
	}

	public String getChallan_rec_date() {
		return challan_rec_date;
	}

	public void setChallan_rec_date(String challan_rec_date) {
		this.challan_rec_date = challan_rec_date;
	}

	public String getWay_bill_no() {
		return way_bill_no;
	}

	public void setWay_bill_no(String way_bill_no) {
		this.way_bill_no = way_bill_no;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDate_of_stn() {
		return date_of_stn;
	}

	public void setDate_of_stn(String date_of_stn) {
		this.date_of_stn = date_of_stn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getExp_delivery_date() {
		return exp_delivery_date;
	}

	public void setExp_delivery_date(String exp_delivery_date) {
		this.exp_delivery_date = exp_delivery_date;
	}

	public String getChallan_rec_by() {
		return challan_rec_by;
	}

	public void setChallan_rec_by(String challan_rec_by) {
		this.challan_rec_by = challan_rec_by;
	}

	public String getSum_dsp_hd_no() {
		return sum_dsp_hd_no;
	}

	public void setSum_dsp_hd_no(String sum_dsp_hd_no) {
		this.sum_dsp_hd_no = sum_dsp_hd_no;
	}

}
