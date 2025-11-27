package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
@Entity
@Table(name = "DISPATCH_DETAIL_ARC")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class Dispatch_Detail_Arc {

	//private static final long serialVersionUID = 1031280349017393131L;

	@Id
	@Column(name = "DSPDTL_ID")
	private Long dspdtlId;
	
	@Column(name = "DSPDTL_DSP_ID")
	private Long dspdtlDspId;
	
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
	
	@Column(name ="DSPDTL_PROD_ID")
	private Long dspdtl_prod_id;
	
	@Column(name="DSPDTL_BATCH_ID")
	private long dspdtl_batch_id;
	
	@Column(name="DSPDTL_DISP_QTY")
	private BigDecimal dspdtl_disp_qty;
	
	@Column(name="DSPDTL_RATE")
	private BigDecimal dspdtl_rate;
	
	@Column(name="DSPDTL_STATUS")
	private String dspdtl_status;
	
	@Column(name="DSPDTL_FIN_YEAR")
	private String dspdtl_fin_year;
	
	

	public String getDspdtl_fin_year() {
		return dspdtl_fin_year;
	}

	public void setDspdtl_fin_year(String dspdtl_fin_year) {
		this.dspdtl_fin_year = dspdtl_fin_year;
	}

	public BigDecimal getDspdtl_disp_qty() {
		return dspdtl_disp_qty;
	}

	public void setDspdtl_disp_qty(BigDecimal dspdtl_disp_qty) {
		this.dspdtl_disp_qty = dspdtl_disp_qty;
	}

	public BigDecimal getDspdtl_rate() {
		return dspdtl_rate;
	}

	public void setDspdtl_rate(BigDecimal dspdtl_rate) {
		this.dspdtl_rate = dspdtl_rate;
	}

	

	

	public Long getDspdtlId() {
		return dspdtlId;
	}

	public void setDspdtlId(Long dspdtlId) {
		this.dspdtlId = dspdtlId;
	}

	public Long getDspdtlDspId() {
		return dspdtlDspId;
	}

	public void setDspdtlDspId(Long dspdtlDspId) {
		this.dspdtlDspId = dspdtlDspId;
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

	public Long getDspdtl_prod_id() {
		return dspdtl_prod_id;
	}

	public void setDspdtl_prod_id(Long dspdtl_prod_id) {
		this.dspdtl_prod_id = dspdtl_prod_id;
	}
	
	

	public long getDspdtl_batch_id() {
		return dspdtl_batch_id;
	}

	public void setDspdtl_batch_id(long dspdtl_batch_id) {
		this.dspdtl_batch_id = dspdtl_batch_id;
	}

	public String getDspdtl_status() {
		return dspdtl_status;
	}

	public void setDspdtl_status(String dspdtl_status) {
		this.dspdtl_status = dspdtl_status;
	}

}
