package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "ViewGRNSummary_GST")
@NamedStoredProcedureQuery(name = "GrnSummaryReport_GST_VET", 
	procedureName = "GrnSummaryReport_GST",
	parameters = {
			@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "SEND_LOC" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "RECV_LOC" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "DIVID" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "USER_ID" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class)
	}, resultClasses = ViewGRNSummary_GST_VET.class)
public class ViewGRNSummary_GST_VET implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ROW")
	private Long row;
	
	@Column(name="GRN_NO")
	private String grn_no;
	
	@Column(name="loc_nm")
	private String sending_Location;
	
	@Column(name="sup_nm")
	private String supplier;
	
	@Column(name="GRN_DT")
	private String grn_date;
	
	@Column(name="GRN_TRANSFER_NO")
	private String transfer_no;
	
	@Column(name="GRN_TRANSFER_DT")
	private String transfer_date;
	
	@Column(name="GRN_LR_NO")
	private String lr_no;
	
	@Column(name="GRN_LR_DT")
	private String lr_date;
	
	@Column(name="GRN_VALUE")
	private BigDecimal grnvalue;
	
	@Column(name="TRANSPORTER")
	private String transporter_Name;
	
	@Column(name="DIVISION")
	private String division_name;
	
	@Column(name="SUPP_CODE")
	private String suppliercode;
	
	@Column(name="PUR_ORD_NO")
	private String po_no;
	
	@Column(name="PUR_ORD_DATE")
	private String po_date;
	
	@Column(name="HTEXT1")
	private String htext1;
	
	@Column(name="HVALUE1")
	private BigDecimal hvalue1;
	
	@Column(name="HTEXT2")
	private String htext2;
	
	@Column(name="HVALUE2")
	private BigDecimal hvalue2;
	
	@Column(name="HSGST_BILL_AMT")
	private BigDecimal hsgst_bill_amt;
	
	@Column(name="HCGST_BILL_AMT")
	private BigDecimal hcgst_bill_amt;
	
	@Column(name="HIGST_BILL_AMT")
	private BigDecimal higst_bill_amt;
	
	@Column(name="HROUNDOFF")
	private BigDecimal hroundoff;
	
	@Column(name="HGST_REG_NO")
	private String gst_no;
	@Column(name="HSTATE_NAME")
	private String state_name;
	@Column(name="HSTATE_CODE")
	private String state_code;
	
	@Column(name="GRN_REF_NO")
	private String grn_ref_no;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="VEHICLE_RECD_TIME")
	private String vehicle_recd_time;
	
	public String getVehicle_recd_time() {
		return vehicle_recd_time;
	}
	public void setVehicle_recd_time(String vehicle_recd_time) {
		this.vehicle_recd_time = vehicle_recd_time;
	}
	
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public String getGrn_no() {
		return grn_no;
	}
	public void setGrn_no(String grn_no) {
		this.grn_no = grn_no;
	}
	public String getSending_Location() {
		return sending_Location;
	}
	public void setSending_Location(String sending_Location) {
		this.sending_Location = sending_Location;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getGrn_date() {
		return grn_date;
	}
	public void setGrn_date(String grn_date) {
		this.grn_date = grn_date;
	}
	public String getTransfer_no() {
		return transfer_no;
	}
	public void setTransfer_no(String transfer_no) {
		this.transfer_no = transfer_no;
	}
	public String getTransfer_date() {
		return transfer_date;
	}
	public void setTransfer_date(String transfer_date) {
		this.transfer_date = transfer_date;
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
	public BigDecimal getGrnvalue() {
		return grnvalue;
	}
	public void setGrnvalue(BigDecimal grnvalue) {
		this.grnvalue = grnvalue;
	}
	public String getTransporter_Name() {
		return transporter_Name;
	}
	public void setTransporter_Name(String transporter_Name) {
		this.transporter_Name = transporter_Name;
	}
	public String getDivision_name() {
		return division_name;
	}
	public void setDivision_name(String division_name) {
		this.division_name = division_name;
	}
	public String getSuppliercode() {
		return suppliercode;
	}
	public void setSuppliercode(String suppliercode) {
		this.suppliercode = suppliercode;
	}
	public String getPo_no() {
		return po_no;
	}
	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	public String getPo_date() {
		return po_date;
	}
	public void setPo_date(String po_date) {
		this.po_date = po_date;
	}
	public String getHtext1() {
		return htext1;
	}
	public void setHtext1(String htext1) {
		this.htext1 = htext1;
	}
	public BigDecimal getHvalue1() {
		return hvalue1;
	}
	public void setHvalue1(BigDecimal hvalue1) {
		this.hvalue1 = hvalue1;
	}
	public String getHtext2() {
		return htext2;
	}
	public void setHtext2(String htext2) {
		this.htext2 = htext2;
	}
	public BigDecimal getHvalue2() {
		return hvalue2;
	}
	public void setHvalue2(BigDecimal hvalue2) {
		this.hvalue2 = hvalue2;
	}
	public BigDecimal getHsgst_bill_amt() {
		return hsgst_bill_amt;
	}
	public void setHsgst_bill_amt(BigDecimal hsgst_bill_amt) {
		this.hsgst_bill_amt = hsgst_bill_amt;
	}
	public BigDecimal getHcgst_bill_amt() {
		return hcgst_bill_amt;
	}
	public void setHcgst_bill_amt(BigDecimal hcgst_bill_amt) {
		this.hcgst_bill_amt = hcgst_bill_amt;
	}
	public BigDecimal getHigst_bill_amt() {
		return higst_bill_amt;
	}
	public void setHigst_bill_amt(BigDecimal higst_bill_amt) {
		this.higst_bill_amt = higst_bill_amt;
	}
	public BigDecimal getHroundoff() {
		return hroundoff;
	}
	public void setHroundoff(BigDecimal hroundoff) {
		this.hroundoff = hroundoff;
	}
	public String getGst_no() {
		return gst_no;
	}
	public void setGst_no(String gst_no) {
		this.gst_no = gst_no;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getState_code() {
		return state_code;
	}
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getGrn_ref_no() {
		return grn_ref_no;
	}
	public void setGrn_ref_no(String grn_ref_no) {
		this.grn_ref_no = grn_ref_no;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}
