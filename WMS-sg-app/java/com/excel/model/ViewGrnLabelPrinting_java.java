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

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "ViewGrnLabelPrinting_java")
@NamedStoredProcedureQuery(
		name = "callViewGrnLabelPrinting_java",
procedureName = "GrnLabelPrinting_java",
parameters = {
		@StoredProcedureParameter(name = "loc_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "supp_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "frm_Grn_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "to_Grn_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "User_type", mode = ParameterMode.IN, type = String.class),
		
		@StoredProcedureParameter(name = "fin_year_flag", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_id", mode = ParameterMode.IN, type = String.class),
		
},resultClasses = ViewGrnLabelPrinting_java.class)
public class ViewGrnLabelPrinting_java implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROW")
	private Long row;

	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "LOC_NM")
	private String loc_nm;
	
	@Column(name = "LOC_ADD1")
	private String loc_add1;
	
	@Column(name = "LOC_ADD2")
	private String loc_add2;
	
	@Column(name = "LOC_ADD3")
	private String loc_add3;
	
	@Column(name = "LOC_ADD4")
	private String loc_add4;
	
	@Column(name = "CITY_NAME")
	private String city_name;
	
	@Column(name = "GRN_NO")
	private String grn_no;
	
	@Column(name = "GRN_DT")
	private String grn_dt;
	
	@Column(name = "GRN_TYPE")
	private String grn_type;
	
	@Column(name = "sup_nm")
	private String sup_nm;
	
	@Column(name = "GRN_TRANSFER_NO")
	private String grn_transfer_no;
	
	@Column(name = "GRN_TRANSFER_DT")
	private String grn_transfer_dt;
	
	@Column(name = "TRANSPORTER")
	private String transporter;
	
	@Column(name = "GRN_LR_NO")
	private String grn_lr_no;
	
	@Column(name = "GRN_LR_DT")
	private String grn_lr_dt;
	
	@Column(name = "PO_NUM")
	private String po_num;
	
	@Column(name = "PO_DATE")
	private String po_date;
	
	@Column(name = "DIVISION")
	private String division;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "BATCH_MFG_DT")
	private String batch_mfg_dt;
	
	@Column(name = "BATCH_EXPIRY_DT")
	private String batch_expiry_dt;
	
	@Column(name = "PACKING")
	private String packing;
	
	@Column(name = "SMP_TYPE")
	private String smp_type;
	
	@Column(name = "RECVD_QTY")
	private BigDecimal recvd_qty;
	
	@Column(name = "TRANSFER_RATE")
	private BigDecimal transfer_rate;
	
	@Column(name = "GRN_VALUE")
	private BigDecimal grn_value;
	
	@Column(name = "GRND_NOOFBOXES")
	private Integer grnd_noofboxes;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;

	@Column(name = "GRN_TOTAL_VALUE")
	private BigDecimal grn_total_value;

	@Column(name = "BIN_NUMBER")
	private String bin_number;
	
	@Column(name = "GRN_ID")
	private Long grn_id;
	
	
	@Column(name = "BATCH_NARRATION")
	private String batch_narration;
	
	
	public String getBatch_narration() {
		return batch_narration;
	}

	public void setBatch_narration(String batch_narration) {
		this.batch_narration = batch_narration;
	}

	public String getBin_number() {
		return bin_number;
	}

	public void setBin_number(String bin_number) {
		this.bin_number = bin_number;
	}

	public BigDecimal getGrn_total_value() {
		return grn_total_value;
	}

	public void setGrn_total_value(BigDecimal grn_total_value) {
		this.grn_total_value = grn_total_value;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getLoc_add1() {
		return loc_add1;
	}

	public void setLoc_add1(String loc_add1) {
		this.loc_add1 = loc_add1;
	}

	public String getLoc_add2() {
		return loc_add2;
	}

	public void setLoc_add2(String loc_add2) {
		this.loc_add2 = loc_add2;
	}

	public String getLoc_add3() {
		return loc_add3;
	}

	public void setLoc_add3(String loc_add3) {
		this.loc_add3 = loc_add3;
	}

	public String getLoc_add4() {
		return loc_add4;
	}

	public void setLoc_add4(String loc_add4) {
		this.loc_add4 = loc_add4;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getGrn_no() {
		return grn_no;
	}

	public void setGrn_no(String grn_no) {
		this.grn_no = grn_no;
	}

	public String getGrn_dt() {
		return grn_dt;
	}

	public void setGrn_dt(String grn_dt) {
		this.grn_dt = grn_dt;
	}

	public String getGrn_type() {
		return grn_type;
	}

	public void setGrn_type(String grn_type) {
		this.grn_type = grn_type;
	}

	public String getSup_nm() {
		return sup_nm;
	}

	public void setSup_nm(String sup_nm) {
		this.sup_nm = sup_nm;
	}

	public String getGrn_transfer_no() {
		return grn_transfer_no;
	}

	public void setGrn_transfer_no(String grn_transfer_no) {
		this.grn_transfer_no = grn_transfer_no;
	}

	public String getGrn_transfer_dt() {
		return grn_transfer_dt;
	}

	public void setGrn_transfer_dt(String grn_transfer_dt) {
		this.grn_transfer_dt = grn_transfer_dt;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
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

	public String getPo_num() {
		return po_num;
	}

	public void setPo_num(String po_num) {
		this.po_num = po_num;
	}

	public String getPo_date() {
		return po_date;
	}

	public void setPo_date(String po_date) {
		this.po_date = po_date;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
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

	public String getBatch_expiry_dt() {
		return batch_expiry_dt;
	}

	public void setBatch_expiry_dt(String batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getSmp_type() {
		return smp_type;
	}

	public void setSmp_type(String smp_type) {
		this.smp_type = smp_type;
	}

	public BigDecimal getRecvd_qty() {
		return recvd_qty;
	}

	public void setRecvd_qty(BigDecimal recvd_qty) {
		this.recvd_qty = recvd_qty;
	}

	public BigDecimal getTransfer_rate() {
		return transfer_rate;
	}

	public void setTransfer_rate(BigDecimal transfer_rate) {
		this.transfer_rate = transfer_rate;
	}

	public BigDecimal getGrn_value() {
		return grn_value;
	}

	public void setGrn_value(BigDecimal grn_value) {
		this.grn_value = grn_value;
	}

	public Integer getGrnd_noofboxes() {
		return grnd_noofboxes;
	}

	public void setGrnd_noofboxes(Integer grnd_noofboxes) {
		this.grnd_noofboxes = grnd_noofboxes;
	}

	public Long getGrn_id() {
		return grn_id;
	}

	public void setGrn_id(Long grn_id) {
		this.grn_id = grn_id;
	}
	
	
}

