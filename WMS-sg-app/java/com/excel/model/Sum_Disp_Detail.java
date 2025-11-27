package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "SUM_DISP_DETAIL")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class Sum_Disp_Detail {
	
	@Id
	@Column(name = "SUMDSPDTL_ID")
	private Long sumdspdtl_id;
	
	@Column(name = "SUMDSPDTL_SUMDSP_ID")
	private Long sumdspdtl_sumdsp_id;
	
	@Column(name = "SUMDSPDTL_PROD_ID")
	private Long sumdspdtl_prod_id;
	
	@Column(name="SGST_RATE")
	private BigDecimal SGST_RATE;
	
	@Column(name="SGST_BILL_AMT")
	private BigDecimal SGST_BILL_AMT;
	
	@Column(name="CGST_RATE")
	private BigDecimal CGST_RATE;
	
	@Column(name="CGST_BILL_AMT")
	private BigDecimal CGST_BILL_AMT;
	
	@Column(name="IGST_RATE")
	private BigDecimal IGST_RATE;
	
	@Column(name="IGST_BILL_AMT")
	private BigDecimal IGST_BILL_AMT;
	
	@Column(name="GST_DOC_TYPE")
	private String gst_doc_type;
	
	@Column(name="SUMDSPDTL_DISP_QTY")
	private BigDecimal sumdspdtl_disp_qty;
	
	@Column(name="SUMDSPDTL_RATE")
	private BigDecimal sumdspdtl_rate;
	

	public Long getSumdspdtl_id() {
		return sumdspdtl_id;
	}

	public void setSumdspdtl_id(Long sumdspdtl_id) {
		this.sumdspdtl_id = sumdspdtl_id;
	}

	public Long getSumdspdtl_sumdsp_id() {
		return sumdspdtl_sumdsp_id;
	}

	public void setSumdspdtl_sumdsp_id(Long sumdspdtl_sumdsp_id) {
		this.sumdspdtl_sumdsp_id = sumdspdtl_sumdsp_id;
	}

	public BigDecimal getSGST_RATE() {
		return SGST_RATE;
	}

	public void setSGST_RATE(BigDecimal sGST_RATE) {
		SGST_RATE = sGST_RATE;
	}

	public BigDecimal getSGST_BILL_AMT() {
		return SGST_BILL_AMT;
	}

	public void setSGST_BILL_AMT(BigDecimal sGST_BILL_AMT) {
		SGST_BILL_AMT = sGST_BILL_AMT;
	}

	public BigDecimal getCGST_RATE() {
		return CGST_RATE;
	}

	public void setCGST_RATE(BigDecimal cGST_RATE) {
		CGST_RATE = cGST_RATE;
	}

	public BigDecimal getCGST_BILL_AMT() {
		return CGST_BILL_AMT;
	}

	public void setCGST_BILL_AMT(BigDecimal cGST_BILL_AMT) {
		CGST_BILL_AMT = cGST_BILL_AMT;
	}

	public BigDecimal getIGST_RATE() {
		return IGST_RATE;
	}

	public void setIGST_RATE(BigDecimal iGST_RATE) {
		IGST_RATE = iGST_RATE;
	}

	public BigDecimal getIGST_BILL_AMT() {
		return IGST_BILL_AMT;
	}

	public void setIGST_BILL_AMT(BigDecimal iGST_BILL_AMT) {
		IGST_BILL_AMT = iGST_BILL_AMT;
	}

	public String getGst_doc_type() {
		return gst_doc_type;
	}

	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}

	public Long getSumdspdtl_prod_id() {
		return sumdspdtl_prod_id;
	}

	public void setSumdspdtl_prod_id(Long sumdspdtl_prod_id) {
		this.sumdspdtl_prod_id = sumdspdtl_prod_id;
	}

	public BigDecimal getSumdspdtl_disp_qty() {
		return sumdspdtl_disp_qty;
	}

	public void setSumdspdtl_disp_qty(BigDecimal sumdspdtl_disp_qty) {
		this.sumdspdtl_disp_qty = sumdspdtl_disp_qty;
	}

	public BigDecimal getSumdspdtl_rate() {
		return sumdspdtl_rate;
	}

	public void setSumdspdtl_rate(BigDecimal sumdspdtl_rate) {
		this.sumdspdtl_rate = sumdspdtl_rate;
	}

}
