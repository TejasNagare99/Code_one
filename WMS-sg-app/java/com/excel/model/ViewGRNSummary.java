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

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "GrnSummaryReport")
@NamedStoredProcedureQuery(name = "GrnSummaryReport", 
procedureName = "GrnSummaryReport",
parameters = {
		@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "SEND_LOC" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "RECV_LOC" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "DIVID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "USER_ID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class)
}, resultClasses = ViewGRNSummary.class)

public class ViewGRNSummary implements Serializable {
	
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
	private BigDecimal value;
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
	@Column(name="GRN_REF_NO")
	private String grn_ref_no;
	@Column(name="REMARKS")
	private String remarks;
	
	
	@Column(name="GRN_ENTERED_BY", insertable = false, updatable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String grn_entered_by;
	 
	
	
	
	public String getGrn_entered_by() {
		return grn_entered_by;
	}
	public void setGrn_entered_by(String grn_entered_by) {
		this.grn_entered_by = grn_entered_by;
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
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
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
	
	
	

}
