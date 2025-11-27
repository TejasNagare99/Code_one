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
@Table(name = "DispatchDetailGstReport")
@NamedStoredProcedureQuery(name = "callDispatchDetailsgstreport", 
//procedureName = "dispatch_details_revised",
procedureName = "dispatch_details_GST_REPORT",
parameters = {
		@StoredProcedureParameter(name = "div_id" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "startdate" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "reptype" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "subcomp" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "orderby" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "tabl_ind" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "user_id" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "field_level" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "prod_id" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "dsp_status" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "cfa" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fsid" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "desig" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "TEAM_CODE" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "RATE_OPTION" , mode = ParameterMode.IN, type = String.class)
}, resultClasses = DispatchDetailGstReport.class)
public class DispatchDetailGstReport {

	@Column(name = "DSP_ID")
	private Long dsp_id;
	
	@Column(name = "DIV_DISP_NM")
	private String div_disp_nm;
	
	@Column(name = "DIV_MAP_CD")
	private String div_map_cd;
	
	@Column(name = "DSP_CHALLAN_NO")
	private String dsp_challan_no;
	
	@Column(name = "SUMDSP_CHALLAN_DT")
	private String sumdsp_challan_dt;
	
	@Column(name = "SUMDSP_DESTINATION")
	private String sumdsp_destination;
	
	@Column(name = "DSP_TOTAL_GOODS_VAL")
	private BigDecimal dsp_total_goods_val;
	
	@Column(name = "DSP_TRANSPORTER")
	private String dsp_transporter;
	
	@Column(name = "DSP_LR_NO")
	private String dsp_lr_no;
	
	@Column(name = "SUMDSP_LR_DT")
	private String sumdsp_lr_dt;
	
	@Column(name = "DSP_CASES")
	private String dsp_cases;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "BATCH_EXPIRY_DT")
	private String batch_expiry_dt;
	
	@Column(name = "DSPDTL_DISP_QTY")
	private BigDecimal dsp_dtldisp_qty;
	
	@Column(name = "DSPDTL_RATE")
	private BigDecimal dspdtl_rate;
	
	@Column(name = "VALUE")
	private BigDecimal value;
	
	@Column(name = "WAREHOUSE")
	private String warehouse;
	
	@Column(name = "DSGST_RATE")
	private BigDecimal dsgst_rate;
	
	@Column(name = "DSGST_BILL_AMT")
	private BigDecimal dsgst_bill_amt;
	
	@Column(name = "DCGST_RATE")
	private BigDecimal dcgst_rate;
	
	@Column(name = "DCGST_BILL_AMT")
	private BigDecimal dcgst_bill_amt;
	
	@Column(name = "DIGST_RATE")
	private BigDecimal digst_rate;
	
	@Column(name = "DIGST_BILL_AMT")
	private BigDecimal digst_bill_amt;
	
	@Column(name = "HSN_CODE")
	private String hsn_code;
	
	@Column(name = "GST_REG_NO")
	private String gst_reg_no;
	
	@Column(name = "STATENAME")
	private String statename;
	
	@Column(name = "STATE_CODE")
	private String state_code;
	
	@Column(name = "GST_DOC_TYPE")
	private String gst_doc_type;
	
	@Id
	@Column(name = "ROW")
	private String row;

	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}



	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getDiv_map_cd() {
		return div_map_cd;
	}

	public void setDiv_map_cd(String div_map_cd) {
		this.div_map_cd = div_map_cd;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public String getSumdsp_challan_dt() {
		return sumdsp_challan_dt;
	}

	public void setSumdsp_challan_dt(String sumdsp_challan_dt) {
		this.sumdsp_challan_dt = sumdsp_challan_dt;
	}

	public String getSumdsp_destination() {
		return sumdsp_destination;
	}

	public void setSumdsp_destination(String sumdsp_destination) {
		this.sumdsp_destination = sumdsp_destination;
	}

	public BigDecimal getDsp_total_goods_val() {
		return dsp_total_goods_val;
	}

	public void setDsp_total_goods_val(BigDecimal dsp_total_goods_val) {
		this.dsp_total_goods_val = dsp_total_goods_val;
	}

	public String getDsp_transporter() {
		return dsp_transporter;
	}

	public void setDsp_transporter(String dsp_transporter) {
		this.dsp_transporter = dsp_transporter;
	}

	public String getDsp_lr_no() {
		return dsp_lr_no;
	}

	public void setDsp_lr_no(String dsp_lr_no) {
		this.dsp_lr_no = dsp_lr_no;
	}

	public String getSumdsp_lr_dt() {
		return sumdsp_lr_dt;
	}

	public void setSumdsp_lr_dt(String sumdsp_lr_dt) {
		this.sumdsp_lr_dt = sumdsp_lr_dt;
	}

	public String getDsp_cases() {
		return dsp_cases;
	}

	public void setDsp_cases(String dsp_cases) {
		this.dsp_cases = dsp_cases;
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

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_expiry_dt() {
		return batch_expiry_dt;
	}

	public void setBatch_expiry_dt(String batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}

	public BigDecimal getDsp_dtldisp_qty() {
		return dsp_dtldisp_qty;
	}

	public void setDsp_dtldisp_qty(BigDecimal dsp_dtldisp_qty) {
		this.dsp_dtldisp_qty = dsp_dtldisp_qty;
	}

	public BigDecimal getDspdtl_rate() {
		return dspdtl_rate;
	}

	public void setDspdtl_rate(BigDecimal dspdtl_rate) {
		this.dspdtl_rate = dspdtl_rate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public BigDecimal getDsgst_rate() {
		return dsgst_rate;
	}

	public void setDsgst_rate(BigDecimal dsgst_rate) {
		this.dsgst_rate = dsgst_rate;
	}

	public BigDecimal getDsgst_bill_amt() {
		return dsgst_bill_amt;
	}

	public void setDsgst_bill_amt(BigDecimal dsgst_bill_amt) {
		this.dsgst_bill_amt = dsgst_bill_amt;
	}

	public BigDecimal getDcgst_rate() {
		return dcgst_rate;
	}

	public void setDcgst_rate(BigDecimal dcgst_rate) {
		this.dcgst_rate = dcgst_rate;
	}

	public BigDecimal getDcgst_bill_amt() {
		return dcgst_bill_amt;
	}

	public void setDcgst_bill_amt(BigDecimal dcgst_bill_amt) {
		this.dcgst_bill_amt = dcgst_bill_amt;
	}

	public BigDecimal getDigst_rate() {
		return digst_rate;
	}

	public void setDigst_rate(BigDecimal digst_rate) {
		this.digst_rate = digst_rate;
	}

	public BigDecimal getDigst_bill_amt() {
		return digst_bill_amt;
	}

	public void setDigst_bill_amt(BigDecimal digst_bill_amt) {
		this.digst_bill_amt = digst_bill_amt;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}

	public String getGst_reg_no() {
		return gst_reg_no;
	}

	public void setGst_reg_no(String gst_reg_no) {
		this.gst_reg_no = gst_reg_no;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getGst_doc_type() {
		return gst_doc_type;
	}

	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}
	
	
}
