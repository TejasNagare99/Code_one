package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "IaaDetailForApprovalMail")
@NamedStoredProcedureQuery(
		name="calliaadetail_aprvmail",
		procedureName = "IAADETAIL_APRVMAIL",
		resultClasses = IaaDetailForApprovalMail.class,
		parameters = {
				@StoredProcedureParameter(name="loc_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="frm_Iaa_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="to_Iaa_id", mode = ParameterMode.IN, type = String.class)
		}
)
public class IaaDetailForApprovalMail {

	@Id
	@Column(name="ROW")
	private Long row;
	
	@Column(name = "STKADJ_ID")
	private Long stkadj_id;
	
	@Column(name = "STKADJ_NO")
	private String stkadj_no;
	
	@Column(name = "loc_nm")
	private String loc_nm;
	
	@Column(name = "SADJ_DT")
	private String sadj_dt;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "PACKING")
	private String packing;
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "REASON")
	private String reason;
	
	@Column(name = "STOCK_TYPE")
	private String stock_type;
	
	@Column(name = "STOCK_TYPE_DESC")
	private String stock_type_desc;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "OUT_QTY")
	private BigDecimal out_qty;
	
	@Column(name = "IN_QTY")
	private BigDecimal in_qty;
	
	@Column(name = "ADJDTL_ID")
	private Long adjdtl_id;
	
	@Column(name = "STKADJ_APPR_STATUS")
	private String stkadj_appr_status;
	
	@Column(name = "BATCH_EXPIRY_DT")
	private String batch_expiry_dt;
	
	@Column(name = "DIV_DISP_NM")
	private String div_disp_nm;
	
	public String getBatch_expiry_dt() {
		return batch_expiry_dt;
	}

	public void setBatch_expiry_dt(String batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getStkadj_appr_status() {
		return stkadj_appr_status;
	}

	public void setStkadj_appr_status(String stkadj_appr_status) {
		this.stkadj_appr_status = stkadj_appr_status;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getStkadj_id() {
		return stkadj_id;
	}

	public void setStkadj_id(Long stkadj_id) {
		this.stkadj_id = stkadj_id;
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

	public String getRemarks() {
		return remarks;
	}

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

	public Long getAdjdtl_id() {
		return adjdtl_id;
	}

	public void setAdjdtl_id(Long adjdtl_id) {
		this.adjdtl_id = adjdtl_id;
	}
	
	
}
