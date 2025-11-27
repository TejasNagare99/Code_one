package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK_TRANSFER_SUMMARY_TABLE")
@NamedStoredProcedureQuery(name = "STOCK_TRANSFER_SUMMARY_REPORT", 
procedureName = "STOCK_TRANSFER_SUMMARY_REPORT",
parameters = {
		@StoredProcedureParameter(name = "loc_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "cfa", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "startdate" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "user_id" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FIN_YEAR_FLAG" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "FIN_YEAR_ID" , mode = ParameterMode.IN, type = String.class)
}, resultClasses = StkTrfSummaryRepotModel.class)
public class StkTrfSummaryRepotModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Row")
	private Long row;
	
	@Column(name="LOC_NM")
	private String loc_nm;
	
	@Column(name="LOC_GST_REG_NO")
	private String loc_gst_reg_no;
	
	@Column(name="LOC_STATE_NAME")
	private String loc_state_name;
	
	@Column(name="TRF_NO")
	private String trf_no;
	
	@Column(name="TRF_DATE")
	private Date trf_date;
	
	@Column(name="CFA_NAME")
	private String cfa_name;
	
	@Column(name="CFA_DESTINATION")
	private String cfa_destination;
	
	@Column(name="CFA_GST_REG_NO")
	private String cfa_gst_reg_no;
	
	@Column(name="CFA_STATE_NAME")
	private String cfa_state_name;
	
	@Column(name="GOODS_VALUE")
	private BigDecimal goods_value;
	
	@Column(name="TAXABLE_AMT")
	private BigDecimal taxable_amt;
	
	@Column(name="GST_DESCRIPTION")
	private String gst_description;
	
	@Column(name="SGST_BILL_AMT")
	private BigDecimal sgst_bill_amt;
	
	@Column(name="CGST_BILL_AMT")
	private BigDecimal cgst_bill_amt;
	
	@Column(name="IGST_BILL_AMT")
	private BigDecimal igst_bill_amt;
	
	@Column(name="TOT_AMT")
	private BigDecimal	tot_amt;
	
	@Column(name="LR_NO")
	private String lr_no;
	
	@Column(name="LR_DATE")
	private Date lr_date;
	
	@Column(name="LORRY_NO")
	private String lorry_no;
	
	@Column(name="TRANSPORTER_NAME")
	private String transporter_name;
	
	@Column(name="WEIGHT")
	private BigDecimal weight;
	
	@Column(name="FULL_SHIPPERS")
    private Long full_shippers;
	
	@Column(name="LOOSE_SHIPPERS")
	private Long loose_shippers;

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getLoc_gst_reg_no() {
		return loc_gst_reg_no;
	}

	public void setLoc_gst_reg_no(String loc_gst_reg_no) {
		this.loc_gst_reg_no = loc_gst_reg_no;
	}

	public String getLoc_state_name() {
		return loc_state_name;
	}

	public void setLoc_state_name(String loc_state_name) {
		this.loc_state_name = loc_state_name;
	}

	public String getTrf_no() {
		return trf_no;
	}

	public void setTrf_no(String trf_no) {
		this.trf_no = trf_no;
	}

	public Date getTrf_date() {
		return trf_date;
	}

	public void setTrf_date(Date trf_date) {
		this.trf_date = trf_date;
	}

	public String getCfa_name() {
		return cfa_name;
	}

	public void setCfa_name(String cfa_name) {
		this.cfa_name = cfa_name;
	}

	public String getCfa_destination() {
		return cfa_destination;
	}

	public void setCfa_destination(String cfa_destination) {
		this.cfa_destination = cfa_destination;
	}

	public String getCfa_gst_reg_no() {
		return cfa_gst_reg_no;
	}

	public void setCfa_gst_reg_no(String cfa_gst_reg_no) {
		this.cfa_gst_reg_no = cfa_gst_reg_no;
	}

	public String getCfa_state_name() {
		return cfa_state_name;
	}

	public void setCfa_state_name(String cfa_state_name) {
		this.cfa_state_name = cfa_state_name;
	}

	public BigDecimal getGoods_value() {
		return goods_value;
	}

	public void setGoods_value(BigDecimal goods_value) {
		this.goods_value = goods_value;
	}

	public BigDecimal getTaxable_amt() {
		return taxable_amt;
	}

	public void setTaxable_amt(BigDecimal taxable_amt) {
		this.taxable_amt = taxable_amt;
	}

	public String getGst_description() {
		return gst_description;
	}

	public void setGst_description(String gst_description) {
		this.gst_description = gst_description;
	}

	public BigDecimal getSgst_bill_amt() {
		return sgst_bill_amt;
	}

	public void setSgst_bill_amt(BigDecimal sgst_bill_amt) {
		this.sgst_bill_amt = sgst_bill_amt;
	}

	public BigDecimal getCgst_bill_amt() {
		return cgst_bill_amt;
	}

	public void setCgst_bill_amt(BigDecimal cgst_bill_amt) {
		this.cgst_bill_amt = cgst_bill_amt;
	}

	public BigDecimal getIgst_bill_amt() {
		return igst_bill_amt;
	}

	public void setIgst_bill_amt(BigDecimal igst_bill_amt) {
		this.igst_bill_amt = igst_bill_amt;
	}

	public BigDecimal getTot_amt() {
		return tot_amt;
	}

	public void setTot_amt(BigDecimal tot_amt) {
		this.tot_amt = tot_amt;
	}

	public String getLr_no() {
		return lr_no;
	}

	public void setLr_no(String lr_no) {
		this.lr_no = lr_no;
	}

	public Date getLr_date() {
		return lr_date;
	}

	public void setLr_date(Date lr_date) {
		this.lr_date = lr_date;
	}

	public String getLorry_no() {
		return lorry_no;
	}

	public void setLorry_no(String lorry_no) {
		this.lorry_no = lorry_no;
	}

	public String getTransporter_name() {
		return transporter_name;
	}

	public void setTransporter_name(String transporter_name) {
		this.transporter_name = transporter_name;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Long getFull_shippers() {
		return full_shippers;
	}

	public void setFull_shippers(Long full_shippers) {
		this.full_shippers = full_shippers;
	}

	public Long getLoose_shippers() {
		return loose_shippers;
	}

	public void setLoose_shippers(Long loose_shippers) {
		this.loose_shippers = loose_shippers;
	}


}
