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
@Table(name = "SummaryChallanReportEInvoice")
@NamedStoredProcedureQuery(name = "callSummaryChallanReportEinv", 
	procedureName = "dispatch_details_SUMMARY_DETAIL_GST_EINV ",
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
			
	}, resultClasses = SummaryChallanReportEInvoice.class)
public class SummaryChallanReportEInvoice {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Row")
	private Long row;

	@Column(name="SUMDSP_ID")
	private Long sumdsp_id;
	
	@Column(name="DIV_DISP_NM")
	private String div_disp_nm;
	
	@Column(name="DIV_MAP_CD")
	private String div_map_cd;
	
	@Column(name="SUMDSP_CHALLAN_NO")
	private String sumdsp_challan_no;
	
	@Column(name="SUMDSP_CHALLAN_DT")
	private String sumdsp_challan_dt;
	
	@Column(name="SUMDSP_DESTINATION")
	private String sumdsp_destination;
	
	@Column(name="SUMDSP_TOTAL_GOODS_VAL")
	private BigDecimal sumdsp_total_goods_val;
	
	@Column(name="SUMDSP_TRANSPORTER")
	private String sumdsp_transporter;
	
	@Column(name="SUMDSP_LR_NO")
	private String sumdsp_lr_no;
	
	@Column(name="SUMDSP_LR_DT")
	private String sumdsp_lr_dt;
	
	@Column(name="SUMDSP_TOTCASES")
	private Long sumdsp_totcases;
	
	@Column(name="SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name="SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name="BATCH_NO")
	private String batch_no;
	
	@Column(name="BATCH_EXPIRY_DT")
	private String batch_expiry_dt;
	
	@Column(name="DSPDTL_DISP_QTY")
	private BigDecimal dspdtl_disp_qty;
	
	@Column(name="DSPDTL_RATE")
	private BigDecimal dspdtl_rate;
	
	@Column(name="VALUE")
	private BigDecimal value;
	
	@Column(name="WAREHOUSE")
	private String warehouse;
	
	@Column(name="DSGST_RATE")
	private BigDecimal dsgst_rate;
	
	@Column(name="DSGST_BILL_AMT")
	private BigDecimal dsgst_bill_amt;
	
	@Column(name="DCGST_RATE")
	private BigDecimal dcgst_rate;
	
	@Column(name="DCGST_BILL_AMT")
	private BigDecimal dcgst_bill_amt;
	
	@Column(name="DIGST_RATE")
	private BigDecimal digst_rate;
	
	@Column(name="DIGST_BILL_AMT")
	private BigDecimal digst_bill_amt;
	
	@Column(name="HSN_CODE")
	private String hsn_code;
	
	@Column(name="GST_REG_NO")
	private String gst_reg_no;
	
	@Column(name="STATENAME")
	private String statename;
	
	@Column(name="STATE_CODE")
	private String state_code;
	
	@Column(name="TRN_IRN_NUMBER")
	private String trn_irn_number;
	
	@Column(name="TRN_EWAYBILLNO")
	private String trn_ewaybillno;

	
	
	public String getTrn_irn_number() {
		return trn_irn_number;
	}

	public void setTrn_irn_number(String trn_irn_number) {
		this.trn_irn_number = trn_irn_number;
	}

	public String getTrn_ewaybillno() {
		return trn_ewaybillno;
	}

	public void setTrn_ewaybillno(String trn_ewaybillno) {
		this.trn_ewaybillno = trn_ewaybillno;
	}

	public Long getSumdsp_id() {
		return sumdsp_id;
	}

	public void setSumdsp_id(Long sumdsp_id) {
		this.sumdsp_id = sumdsp_id;
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

	public String getSumdsp_challan_no() {
		return sumdsp_challan_no;
	}

	public void setSumdsp_challan_no(String sumdsp_challan_no) {
		this.sumdsp_challan_no = sumdsp_challan_no;
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

	public BigDecimal getSumdsp_total_goods_val() {
		return sumdsp_total_goods_val;
	}

	public void setSumdsp_total_goods_val(BigDecimal sumdsp_total_goods_val) {
		this.sumdsp_total_goods_val = sumdsp_total_goods_val;
	}

	public String getSumdsp_transporter() {
		return sumdsp_transporter;
	}

	public void setSumdsp_transporter(String sumdsp_transporter) {
		this.sumdsp_transporter = sumdsp_transporter;
	}

	public String getSumdsp_lr_no() {
		return sumdsp_lr_no;
	}

	public void setSumdsp_lr_no(String sumdsp_lr_no) {
		this.sumdsp_lr_no = sumdsp_lr_no;
	}

	public String getSumdsp_lr_dt() {
		return sumdsp_lr_dt;
	}

	public void setSumdsp_lr_dt(String sumdsp_lr_dt) {
		this.sumdsp_lr_dt = sumdsp_lr_dt;
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

	public Long getSumdsp_totcases() {
		return sumdsp_totcases;
	}

	public void setSumdsp_totcases(Long sumdsp_totcases) {
		this.sumdsp_totcases = sumdsp_totcases;
	}
}
