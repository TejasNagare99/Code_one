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
@Table(name = "DispatchDetailReportMdmNo")
@NamedStoredProcedureQuery(name = "callDispatchDetailsRevisedMdmNo", 
	procedureName = "dispatch_details_revised_RATEOPTION_MDMNO",
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
	}, resultClasses = DispatchDetailReportRevisedMdmNo.class)
public class DispatchDetailReportRevisedMdmNo {

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
	
	@Column(name="HQ_NAME")
	private String hq_name;
	
	@Column(name="TER_CODE")
	private String terr_code;
	
	@Column(name="TER_NAME")
	private String terr_name;
	
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
	
	@Column(name="STATE")
	private String state_name;
	
	@Column(name="REGION")
	private String region;
	
	@Column(name="MONTH_OF_USE")
	private String month_of_use;
	
	@Column(name="RETURN_QTY")
	private BigDecimal return_qty;
	
	@Column(name="DM_NAME")
	private String dm_name;
	
	@Column(name="TENTATIVE_DELIVERY_DT")
	private String tentative_delivery_date;
	
	@Column(name="ACTUAL_DELIVERY_DATE")
	private String actual_delivery_date;
	
	@Column(name="RECIEVED_BY")
	private String recieved_by;
	
	@Column(name="COURIER_COMMENT")
	private String courier_comment;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="Emer_DtD_supplied_to")
	private String emer_dtd_supplied_to;
	
	@Column(name="TEAM_CODE")
	private String team_code;
	
	@Column(name="SRT_NUMBER")
	private String srt_number;
	
	@Column(name="SRT_DATE")
	private String srt_date;
	
	@Column(name="SRT_REMARK")
	private String srt_remark;
	
	@Column(name="MDM_NUMBER")
	private String mdm_number;
	
	@Column(name="MDM_NAME")
	private String mdm_name;
	
	public String getMdm_number() {
		return mdm_number;
	}

	public void setMdm_number(String mdm_number) {
		this.mdm_number = mdm_number;
	}

	public String getMdm_name() {
		return mdm_name;
	}

	public void setMdm_name(String mdm_name) {
		this.mdm_name = mdm_name;
	}

	public String getSrt_number() {
		return srt_number;
	}

	public void setSrt_number(String srt_number) {
		this.srt_number = srt_number;
	}

	public String getSrt_date() {
		return srt_date;
	}

	public void setSrt_date(String srt_date) {
		this.srt_date = srt_date;
	}

	public String getSrt_remark() {
		return srt_remark;
	}

	public void setSrt_remark(String srt_remark) {
		this.srt_remark = srt_remark;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getTentative_delivery_date() {
		return tentative_delivery_date;
	}

	public void setTentative_delivery_date(String tentative_delivery_date) {
		this.tentative_delivery_date = tentative_delivery_date;
	}

	public String getActual_delivery_date() {
		return actual_delivery_date;
	}

	public void setActual_delivery_date(String actual_delivery_date) {
		this.actual_delivery_date = actual_delivery_date;
	}

	public String getRecieved_by() {
		return recieved_by;
	}

	public void setRecieved_by(String recieved_by) {
		this.recieved_by = recieved_by;
	}

	public String getCourier_comment() {
		return courier_comment;
	}

	public void setCourier_comment(String courier_comment) {
		this.courier_comment = courier_comment;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEmer_dtd_supplied_to() {
		return emer_dtd_supplied_to;
	}

	public void setEmer_dtd_supplied_to(String emer_dtd_supplied_to) {
		this.emer_dtd_supplied_to = emer_dtd_supplied_to;
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

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
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

	public BigDecimal getReturn_qty() {
		return return_qty;
	}

	public void setReturn_qty(BigDecimal return_qty) {
		this.return_qty = return_qty;
	}

	public String getDm_name() {
		return dm_name;
	}

	public void setDm_name(String dm_name) {
		this.dm_name = dm_name;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

}
