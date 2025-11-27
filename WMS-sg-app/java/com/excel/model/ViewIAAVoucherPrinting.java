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
@Table(name = "ViewIAAVoucherPrinting")
@NamedStoredProcedureQuery(name = "callViewIAAVoucherPrinting",
                         procedureName = "IAAVoucherPrinting" ,                      
parameters = {
		@StoredProcedureParameter(name="loc_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="frm_Iaa_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="to_Iaa_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="fin_year_flag", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="fin_year_id", mode = ParameterMode.IN, type = String.class)},
resultClasses= ViewIAAVoucherPrinting.class)
public class ViewIAAVoucherPrinting implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROW")
	private Long row;
	
	@Column(name="STKADJ_ID")
	private Integer stkadj_id;
 	
	
	@Column(name="STKADJ_NO")
	private String stkadj_no;
	
	@Column(name="LOC_NM")
	private String loc_nm;
	
	@Column(name="SADJ_DT")
	private String sadj_dt;
	
	@Column(name="SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name="SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	
	@Column(name="PACKING")
	private String packing;
	
	@Column(name="BATCH_NO")
	private String batch_no;
	
	@Column(name="ADJDTL_REASON_ID")
	private String adjdtl_reason_id;
	
	@Column(name="REASON")
	private String reason;
	
	@Column(name="STOCK_TYPE")
	private String stock_type;
	
	@Column(name="STOCK_TYPE_DESC")
	private String stock_type_desc;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="OUT_QTY")
	private BigDecimal out_qty;
 	
	@Column(name="IN_QTY")
	private BigDecimal in_qty;
	
	@Column(name="ADJDTL_ID")
	private Integer adjdtl_id;
	
	

	public Integer getAdjdtl_id() {
		return adjdtl_id;
	}

	public void setAdjdtl_id(Integer adjdtl_id) {
		this.adjdtl_id = adjdtl_id;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getStkadj_no() {
		return stkadj_no;
	}

	public void setStkadj_no(String stkadj_no) {
		this.stkadj_no = stkadj_no;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getSadj_dt() {
		return sadj_dt;
	}

	public void setSadj_dt(String sadj_dt) {
		this.sadj_dt = sadj_dt;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
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

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getAdjdtl_reason_id() {
		return adjdtl_reason_id;
	}

	public void setAdjdtl_reason_id(String adjdtl_reason_id) {
		this.adjdtl_reason_id = adjdtl_reason_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}

	public String getStock_type_desc() {
		return stock_type_desc;
	}

	public void setStock_type_desc(String stock_type_desc) {
		this.stock_type_desc = stock_type_desc;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getOut_qty() {
		return out_qty;
	}

	public void setOut_qty(BigDecimal out_qty) {
		this.out_qty = out_qty;
	}

	public BigDecimal getIn_qty() {
		return in_qty;
	}

	public void setIn_qty(BigDecimal in_qty) {
		this.in_qty = in_qty;
	}

	/**
	 * @return the stkadj_id
	 */
	public Integer getStkadj_id() {
		return stkadj_id;
	}

	/**
	 * @param stkadj_id the stkadj_id to set
	 */
	public void setStkadj_id(Integer stkadj_id) {
		this.stkadj_id = stkadj_id;
	}

	
	
}

