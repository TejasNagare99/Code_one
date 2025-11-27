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
@Table(name = "ViewGRNDetail_GST")
@NamedStoredProcedureQuery(name = "GrnDetailReport_GST", 
	procedureName = "GrnDetailReport_GST",
	parameters = {
			@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "SEND_LOC" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "RECV_LOC" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "DIVID" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "USER_ID" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "SA_NS" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "prod_id" , mode = ParameterMode.IN, type = String.class),
	}, resultClasses = ViewGRNDetail_GST.class)



@NamedStoredProcedureQuery(name = "GrnDetailReport_GST_SG", 
procedureName = "GrnDetailReport_GST",
parameters = {
		@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "SEND_LOC" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "RECV_LOC" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "DIVID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "USER_ID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "SA_NS" , mode = ParameterMode.IN, type = String.class),
}, resultClasses = ViewGRNDetail_GST.class)




public class ViewGRNDetail_GST implements Serializable{

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

	@Column(name="SMP_PROD_NAME")
	private String product_name;//description
	
	@Column(name="GRN_VALUE")
	private BigDecimal value;
	
	@Column(name="DIVISION")
	private String division_name;
	
	@Column(name="SUPP_CODE")
	private String suppliercode;
	
	@Column(name="BATCH_NO")
	private String batch_no;
	
	@Column(name="BATCH_EXPIRY_DT")
	private String batch_expiry_date;
	
	@Column(name="GRND_QTY")
	private BigDecimal grn_qty;
	
	@Column(name="GRND_RATE")
	private BigDecimal grn_rate;
	
	@Column(name="SMP_PROD_TYPE")
	private String product_type;
	
	@Column(name="SMP_PROD_CD")
	private String product_code;
	
	@Column(name="BATCH_MFG_DT")
	private String mfg_dt;
	
	@Column(name="GRN_LR_NO")
	private String lr_no;
	
	@Column(name="GRN_LR_DT")
	private String lr_date;
	
	@Column(name="PUR_ORD_NO")
	private String po_no;
	
	@Column(name="PUR_ORD_DATE")
	private String po_date;
	
	@Column(name="TRANSPORTER")
	private String transporter_Name;
	
	@Column(name="WAREHOUSE_CD")
	private String warehouse_cd;
	
	@Column(name="PACKING")
	private String packing;
	
	@Column(name="BIN_NUMBER")
	private String bin_number;
	
	@Column(name="SGST_RATE")
	private BigDecimal sgst_rate;
	
	@Column(name="SGST_BILL_AMT")
	private BigDecimal sgst_bill_amt;
	
	@Column(name="CGST_RATE")
	private BigDecimal cgst_rate;
	
	@Column(name="CGST_BILL_AMT")
	private BigDecimal cgst_bill_amt;
	
	@Column(name="IGST_RATE")
	private BigDecimal igst_rate;
	
	@Column(name="IGST_BILL_AMT")
	private BigDecimal igst_bill_amt;
	
	@Column(name="ADDL_CHRG")
	private String text1;
	
	@Column(name="AMOUNT1")
	private BigDecimal value1;
	
	@Column(name="DISCOUNT")
	private String text2;
	
	@Column(name="AMOUNT2")
	private BigDecimal value2;
	
	@Column(name="HGST_REG_NO")
	private String hgst_reg_no;
	
	@Column(name="HSTATE_NAME")
	private String hstate_name;
	
	@Column(name="HSTATE_CODE")
	private String hstate_code;
	
	@Column(name="SUPPLY_DOC_TYPE")
	private String supply_doc_type;
	
	@Column(name="HSNCODE")
	private String hsncode;
	
	@Column(name = "GRN_TYPE")
	private String grn_type;
	
	@Column(name = "FCODE")
	private String fcode;
	
	@Column(name = "GCMA_REQ_IND")
	private String gcma_req_ind;
	
	@Column(name = "GCMA_NUMBER")
	private String gcma_number;
	
	@Column(name = "GCMA_EXPIRY_DT")
	private String gcma_expiry_dt;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name="SHORT_QTY")
	private BigDecimal short_qty;
	
	@Column(name="DAMAGE_QTY")
	private BigDecimal damage_qty;
	
	@Column(name="TOTAL_GRN_QTY")
	private BigDecimal total_grn_qty;
	
	@Column(name="RETURN_FROM_FSTAFF_NAME")
	private String return_from_fstaff_name;
	
	@Column(name="SALABLE_NONSALEABLE")
	private String salable_nonsaleable;
	
	
	public String getSalable_nonsaleable() {
		return salable_nonsaleable;
	}

	public void setSalable_nonsaleable(String salable_nonsaleable) {
		this.salable_nonsaleable = salable_nonsaleable;
	}

	public String getReturn_from_fstaff_name() {
		return return_from_fstaff_name;
	}

	public void setReturn_from_fstaff_name(String return_from_fstaff_name) {
		this.return_from_fstaff_name = return_from_fstaff_name;
	}

	public String getGrn_type() {
		return grn_type;
	}

	public void setGrn_type(String grn_type) {
		this.grn_type = grn_type;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	public String getGcma_req_ind() {
		return gcma_req_ind;
	}

	public void setGcma_req_ind(String gcma_req_ind) {
		this.gcma_req_ind = gcma_req_ind;
	}

	public String getGcma_number() {
		return gcma_number;
	}

	public void setGcma_number(String gcma_number) {
		this.gcma_number = gcma_number;
	}

	public String getGcma_expiry_dt() {
		return gcma_expiry_dt;
	}

	public void setGcma_expiry_dt(String gcma_expiry_dt) {
		this.gcma_expiry_dt = gcma_expiry_dt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_expiry_date() {
		return batch_expiry_date;
	}

	public void setBatch_expiry_date(String batch_expiry_date) {
		this.batch_expiry_date = batch_expiry_date;
	}

	public BigDecimal getGrn_qty() {
		return grn_qty;
	}

	public void setGrn_qty(BigDecimal grn_qty) {
		this.grn_qty = grn_qty;
	}

	public BigDecimal getGrn_rate() {
		return grn_rate;
	}

	public void setGrn_rate(BigDecimal grn_rate) {
		this.grn_rate = grn_rate;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getMfg_dt() {
		return mfg_dt;
	}

	public void setMfg_dt(String mfg_dt) {
		this.mfg_dt = mfg_dt;
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

	public String getTransporter_Name() {
		return transporter_Name;
	}

	public void setTransporter_Name(String transporter_Name) {
		this.transporter_Name = transporter_Name;
	}

	public String getWarehouse_cd() {
		return warehouse_cd;
	}

	public void setWarehouse_cd(String warehouse_cd) {
		this.warehouse_cd = warehouse_cd;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getBin_number() {
		return bin_number;
	}

	public void setBin_number(String bin_number) {
		this.bin_number = bin_number;
	}

	public BigDecimal getSgst_rate() {
		return sgst_rate;
	}

	public void setSgst_rate(BigDecimal sgst_rate) {
		this.sgst_rate = sgst_rate;
	}

	public BigDecimal getSgst_bill_amt() {
		return sgst_bill_amt;
	}

	public void setSgst_bill_amt(BigDecimal sgst_bill_amt) {
		this.sgst_bill_amt = sgst_bill_amt;
	}

	public BigDecimal getCgst_rate() {
		return cgst_rate;
	}

	public void setCgst_rate(BigDecimal cgst_rate) {
		this.cgst_rate = cgst_rate;
	}

	public BigDecimal getCgst_bill_amt() {
		return cgst_bill_amt;
	}

	public void setCgst_bill_amt(BigDecimal cgst_bill_amt) {
		this.cgst_bill_amt = cgst_bill_amt;
	}

	public BigDecimal getIgst_rate() {
		return igst_rate;
	}

	public void setIgst_rate(BigDecimal igst_rate) {
		this.igst_rate = igst_rate;
	}

	public BigDecimal getIgst_bill_amt() {
		return igst_bill_amt;
	}

	public void setIgst_bill_amt(BigDecimal igst_bill_amt) {
		this.igst_bill_amt = igst_bill_amt;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public BigDecimal getValue1() {
		return value1;
	}

	public void setValue1(BigDecimal value1) {
		this.value1 = value1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public BigDecimal getValue2() {
		return value2;
	}

	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
	}

	public String getHgst_reg_no() {
		return hgst_reg_no;
	}

	public void setHgst_reg_no(String hgst_reg_no) {
		this.hgst_reg_no = hgst_reg_no;
	}

	public String getHstate_name() {
		return hstate_name;
	}

	public void setHstate_name(String hstate_name) {
		this.hstate_name = hstate_name;
	}

	public String getHstate_code() {
		return hstate_code;
	}

	public void setHstate_code(String hstate_code) {
		this.hstate_code = hstate_code;
	}

	public String getSupply_doc_type() {
		return supply_doc_type;
	}

	public void setSupply_doc_type(String supply_doc_type) {
		this.supply_doc_type = supply_doc_type;
	}

	public String getHsncode() {
		return hsncode;
	}

	public void setHsncode(String hsncode) {
		this.hsncode = hsncode;
	}

	public BigDecimal getShort_qty() {
		return short_qty;
	}

	public void setShort_qty(BigDecimal short_qty) {
		this.short_qty = short_qty;
	}

	public BigDecimal getDamage_qty() {
		return damage_qty;
	}

	public void setDamage_qty(BigDecimal damage_qty) {
		this.damage_qty = damage_qty;
	}

	public BigDecimal getTotal_grn_qty() {
		return total_grn_qty;
	}

	public void setTotal_grn_qty(BigDecimal total_grn_qty) {
		this.total_grn_qty = total_grn_qty;
	}
	

	
}
