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
@Table(name = "GrnquarantineProd_Detail")
@NamedStoredProcedureQuery(name = "GrnDetailReport_QUARANTINE", 
procedureName = "GrnDetailReport_QUARANTINE",

parameters = {
		@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "SEND_LOC" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "RECV_LOC" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "DIVID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "USER_ID" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FIN_YEAR_ID" , mode = ParameterMode.IN, type = String.class),
	}, resultClasses = GrnquarantineProd_Detail.class)
public class GrnquarantineProd_Detail implements Serializable{

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
	private String loc_nm;
	
	@Column(name="sup_nm")
	private String sup_nm;
	
	@Column(name="GRN_DT")
	private String grn_date;
	
	@Column(name="GRN_TRANSFER_NO")
	private String transfer_no;
	
	@Column(name="GRN_TRANSFER_DT")
	private String grn_transfer_dt;
	
	@Column(name="GRN_LR_NO")
	private String grn_lr_no;
	
	@Column(name="GRN_LR_DT")
	private String grn_lr_dt;
	
	@Column(name="SMP_PROD_CD")
	private String smp_prod_cd;

	@Column(name="SMP_PROD_NAME")
	private String product_name;//description
	
//	@Column(name="GRN_VALUE")
//	private BigDecimal value;
//	
//	@Column(name="DIVISION")
//	private String division_name;
//	
//	@Column(name="SUPP_CODE")
//	private String suppliercode;
	
	@Column(name="BATCH_NO")
	private String batch_no;
	
	@Column(name="BATCH_MFG_DT")
	private String batch_mfg_dt;
	
	@Column(name="BATCH_EXPIRY_DT")
	private String batch_expiry_date;
	
	@Column(name="GRND_QTY")
	private BigDecimal grn_qty;
	
	@Column(name="GRND_RATE")
	private BigDecimal grn_rate;
	
	@Column(name="GRN_VALUE")
	private BigDecimal value;
	
	@Column(name="TRANSPORTER")
	private String transporter;
	
	@Column(name="DIVISION")
	private String division_name;
	
	@Column(name="SUPP_CODE")
	private String suppliercode;
	
	@Column(name="PUR_ORD_NO")
	private String po_no;
	
	@Column(name="PUR_ORD_DATE")
	private String po_date;
	
	@Column(name="SMP_PROD_TYPE")
	private String product_type;
	

	@Column(name="WAREHOUSE_CD")
	private String warehouse_cd;
	
	@Column(name="PACKING")
	private String packing;
	
	@Column(name="BIN_NUMBER")
	private Long bin_number;

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

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getSup_nm() {
		return sup_nm;
	}

	public void setSup_nm(String sup_nm) {
		this.sup_nm = sup_nm;
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

	public String getGrn_transfer_dt() {
		return grn_transfer_dt;
	}

	public void setGrn_transfer_dt(String grn_transfer_dt) {
		this.grn_transfer_dt = grn_transfer_dt;
	}

	public String getGrn_lr_no() {
		return grn_lr_no;
	}

	public void setGrn_lr_no(String grn_lr_no) {
		this.grn_lr_no = grn_lr_no;
	}

	public String getGrn_lr_dt() {
		return grn_lr_dt;
	}

	public void setGrn_lr_dt(String grn_lr_dt) {
		this.grn_lr_dt = grn_lr_dt;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_mfg_dt() {
		return batch_mfg_dt;
	}

	public void setBatch_mfg_dt(String batch_mfg_dt) {
		this.batch_mfg_dt = batch_mfg_dt;
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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
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

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
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

	public Long getBin_number() {
		return bin_number;
	}

	public void setBin_number(Long bin_number) {
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
	
	
	
//	@Column(name="SMP_PROD_CD")
//	private String product_code;
//	
//	@Column(name="BATCH_MFG_DT")
//	private String mfg_dt;
//	
//	@Column(name="GRN_LR_NO")
//	private String lr_no;
//	
//	@Column(name="GRN_LR_DT")
//	private String lr_date;
//	
//	@Column(name="SHORT_QTY")
//	private BigDecimal short_qty;
//	
//	@Column(name="DAMAGE_QTY")
//	private BigDecimal damage_qty;
//	
//	@Column(name="TOTAL_GRN_QTY")
//	private BigDecimal total_grn_qty;
	
	


	
}

