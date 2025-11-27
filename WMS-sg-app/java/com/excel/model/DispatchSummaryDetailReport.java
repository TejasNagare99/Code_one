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
@Table(name = "DispatchSummaryDetailReport")
@NamedStoredProcedureQuery(name = "callDispatchSummaryDetailReport", 
	procedureName = "dispatch_details_revised",
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
			@StoredProcedureParameter(name = "FIN_YEAR_ID" , mode = ParameterMode.IN, type = String.class),
	}, resultClasses = DispatchSummaryDetailReport.class)
public class DispatchSummaryDetailReport implements Serializable {

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
	private BigDecimal sumdsp_totcases;
	
	@Column(name="DSPFSTAFF_EMPLOYEENO")
	private String dspfstaff_employeeno;
	
	@Column(name="DSPFSTAFF_DISPLAYNAME")
	private String dspfstaff_displayname;
	
	@Column(name="DSPFSTAFF_DESG")
	private String dspfstaff_desg;
	
	@Column(name="HQ_CODE")
	private String hq_code;
	
	@Column(name="HQ_NAME")
	private String hq_name;
	
	@Column(name="DSP_CHALLAN_NO")
	private String dsp_challan_no;
	
	@Column(name="DSP_CHALLAN_DT")
	private String dsp_challan_dt;
	
	@Column(name="DSP_TOTAL_GOODS_VAL")
	private BigDecimal dsp_total_goods_val;
	
	@Column(name="DSP_TRANSPORTER")
	private String dsp_transporter;
	
	@Column(name="DSP_LR_NO")
	private String dsp_lr_no;
	
	@Column(name="DSP_LR_DT")
	private String dsp_lr_dt;
	
	@Column(name="DSP_CASES")
	private BigDecimal dsp_cases;
	
	@Column(name="FSTAFF_EMAIL")
	private String fstaff_email;
	
	@Column(name="ALLOCDTL_CYCLE")
	private Long allocdtl_cycle;
	
	@Column(name="ALLOC_MONTH")
	private String alloc_month;

	@Column(name="AM_NAME")
	private String am_name;
	
	@Column(name="RM_NAME")
	private String rm_name;
	
	@Column(name="FSTAFF_ID")
	private Long fstaff_id;
	
	@Column(name="WAREHOUSE")
	private String warehouse;
	
	@Column(name="CFA_LOCATION")
	private String cfa_location;
	
	@Column(name="TERR_CODE")
	private String terr_code;
	
	@Column(name="TERR_NAME")
	private String terr_name;
	
	@Column(name="POSITION")
	private String position;
	
	@Column(name="MOBILE")
	private String mobile;
	
	@Column(name="DSP_ACT_WT")
	private BigDecimal dsp_act_wt;
	
	@Column(name="DSP_BILL_WT")
	private BigDecimal dsp_bill_wt;
	
	@Column(name="SUM_DSP_ACT_WT")
	private BigDecimal sum_dsp_act_wt;
	
	@Column(name="SUM_DSP_BILL_WT")
	private BigDecimal sum_dsp_bill_wt;
	
	@Column(name="STATE_NAME")
	private String state_name;
	
	@Column(name="REGION")
	private String region;
	
	@Column(name="MONTH_OF_USE")
	private String month_of_use;
	
	@Column(name="ACTUAL_DELIVERY_DATE")
	private String actual_delivery_date;
	
	@Column(name="DSP_RECD_BY")
	private String dsp_recd_by;
	
	@Column(name="DSP_REMARK")
	private String dsp_remark;
	
	
	

	public String getActual_delivery_date() {
		return actual_delivery_date;
	}

	public void setActual_delivery_date(String actual_delivery_date) {
		this.actual_delivery_date = actual_delivery_date;
	}

	public String getDsp_recd_by() {
		return dsp_recd_by;
	}

	public void setDsp_recd_by(String dsp_recd_by) {
		this.dsp_recd_by = dsp_recd_by;
	}

	public String getDsp_remark() {
		return dsp_remark;
	}

	public void setDsp_remark(String dsp_remark) {
		this.dsp_remark = dsp_remark;
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

	public BigDecimal getSumdsp_totcases() {
		return sumdsp_totcases;
	}

	public void setSumdsp_totcases(BigDecimal sumdsp_totcases) {
		this.sumdsp_totcases = sumdsp_totcases;
	}

	public String getDspfstaff_employeeno() {
		return dspfstaff_employeeno;
	}

	public void setDspfstaff_employeeno(String dspfstaff_employeeno) {
		this.dspfstaff_employeeno = dspfstaff_employeeno;
	}

	public String getDspfstaff_displayname() {
		return dspfstaff_displayname;
	}

	public void setDspfstaff_displayname(String dspfstaff_displayname) {
		this.dspfstaff_displayname = dspfstaff_displayname;
	}

	public String getDspfstaff_desg() {
		return dspfstaff_desg;
	}

	public void setDspfstaff_desg(String dspfstaff_desg) {
		this.dspfstaff_desg = dspfstaff_desg;
	}

	public String getHq_code() {
		return hq_code;
	}

	public void setHq_code(String hq_code) {
		this.hq_code = hq_code;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public String getDsp_challan_dt() {
		return dsp_challan_dt;
	}

	public void setDsp_challan_dt(String dsp_challan_dt) {
		this.dsp_challan_dt = dsp_challan_dt;
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

	public String getDsp_lr_dt() {
		return dsp_lr_dt;
	}

	public void setDsp_lr_dt(String dsp_lr_dt) {
		this.dsp_lr_dt = dsp_lr_dt;
	}

	public BigDecimal getDsp_cases() {
		return dsp_cases;
	}

	public void setDsp_cases(BigDecimal dsp_cases) {
		this.dsp_cases = dsp_cases;
	}

	public String getFstaff_email() {
		return fstaff_email;
	}

	public void setFstaff_email(String fstaff_email) {
		this.fstaff_email = fstaff_email;
	}

	public Long getAllocdtl_cycle() {
		return allocdtl_cycle;
	}

	public void setAllocdtl_cycle(Long allocdtl_cycle) {
		this.allocdtl_cycle = allocdtl_cycle;
	}

	public String getAlloc_month() {
		return alloc_month;
	}

	public void setAlloc_month(String alloc_month) {
		this.alloc_month = alloc_month;
	}

	public String getAm_name() {
		return am_name;
	}

	public void setAm_name(String am_name) {
		this.am_name = am_name;
	}

	public String getRm_name() {
		return rm_name;
	}

	public void setRm_name(String rm_name) {
		this.rm_name = rm_name;
	}

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getCfa_location() {
		return cfa_location;
	}

	public void setCfa_location(String cfa_location) {
		this.cfa_location = cfa_location;
	}

	public String getTerr_code() {
		return terr_code;
	}

	public void setTerr_code(String terr_code) {
		this.terr_code = terr_code;
	}

	public String getTerr_name() {
		return terr_name;
	}

	public void setTerr_name(String terr_name) {
		this.terr_name = terr_name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getDsp_act_wt() {
		return dsp_act_wt;
	}

	public void setDsp_act_wt(BigDecimal dsp_act_wt) {
		this.dsp_act_wt = dsp_act_wt;
	}

	public BigDecimal getDsp_bill_wt() {
		return dsp_bill_wt;
	}

	public void setDsp_bill_wt(BigDecimal dsp_bill_wt) {
		this.dsp_bill_wt = dsp_bill_wt;
	}

	public BigDecimal getSum_dsp_act_wt() {
		return sum_dsp_act_wt;
	}

	public void setSum_dsp_act_wt(BigDecimal sum_dsp_act_wt) {
		this.sum_dsp_act_wt = sum_dsp_act_wt;
	}

	public BigDecimal getSum_dsp_bill_wt() {
		return sum_dsp_bill_wt;
	}

	public void setSum_dsp_bill_wt(BigDecimal sum_dsp_bill_wt) {
		this.sum_dsp_bill_wt = sum_dsp_bill_wt;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getMonth_of_use() {
		return month_of_use;
	}

	public void setMonth_of_use(String month_of_use) {
		this.month_of_use = month_of_use;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}
	
	
	
	
	
	
}
