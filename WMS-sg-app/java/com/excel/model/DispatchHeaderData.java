package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "DispatchHeaderData")
@NamedStoredProcedureQuery(
		name="callDispatchHeaderData",
		procedureName = "p_v_dispatch1_java",
		resultClasses = AllocationDetailList.class,
		parameters = {
				@StoredProcedureParameter(name="dsp_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="loc_id", mode = ParameterMode.IN, type = String.class),
		}
)
public class DispatchHeaderData {
	@Id
	@Column(name="DSP_ID")
	private Long dsp_id;

	@Column(name="DSP_NO")
	private String dsp_no;

	@Column(name="DSP_COMPANY")
	private String dsp_company;

	@Column(name="DSP_FIN_YEAR")
	private String dsp_fin_year;

	@Column(name="DSP_DT")
	private String dsp_dt;

	@Column(name="DSO_CHALLAN_NO")
	private String dso_challan_no;

	@Column(name="DSP_TRANSPORTER")
	private String dsp_transporter;

	@Column(name="DSP_LORRY_NO")
	private String dsp_lorry_no;

	@Column(name="DSP_LR_DT")
	private Date dsp_lr_dt;

	@Column(name="FSTAFF_ID")
	private Long  fstaff_id;

	@Column(name="FSTAFF_DISPLAY_NAME")
	private String fstaff_display_name;

	@Column(name="DPTLOC_NAME")
	private String dptloc_name;

	@Column(name="ALLOC_PERIOD")
	private Long alloc_period;

	@Column(name="ALLOC_DSP_PERIOD")
	private String alloc_dsp_period;

	@Column(name="DSP_manual_flg")
	private String dsp_manual_flg;

	@Column(name="HQ_NAME")
	private String hq_name;

	@Column(name="DSP_TOTAL_GOODS_VAL")
	private BigDecimal dsp_total_goods_val;

	@Column(name="loc_nm")
	private String loc_nm;

	@Column(name="FSTAFF_DESTINATION")
	private String fstaff_destination;

	@Column(name="PREV_FLG")
	private String prev_flg;

	@Column(name="SUMDSP_DISP_CYCLE")
	private String sumdsp_disp_cycle;

	@Column(name="FSTAFF_DIV_ID")
	private Long fstaff_div_id;

	@Column(name="DSP_challan_msg")
	private String dsp_challan_msg;
	
	@Column(name="DIV_DISP_NM")
	private Long div_disp_nm;

	@Column(name="SUM_DSP_ID")
	private String sum_dsp_id;


	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}

	public String getDsp_no() {
		return dsp_no;
	}

	public void setDsp_no(String dsp_no) {
		this.dsp_no = dsp_no;
	}

	public String getDsp_company() {
		return dsp_company;
	}

	public void setDsp_company(String dsp_company) {
		this.dsp_company = dsp_company;
	}

	public String getDsp_fin_year() {
		return dsp_fin_year;
	}

	public void setDsp_fin_year(String dsp_fin_year) {
		this.dsp_fin_year = dsp_fin_year;
	}

	public String getDsp_dt() {
		return dsp_dt;
	}

	public void setDsp_dt(String dsp_dt) {
		this.dsp_dt = dsp_dt;
	}

	public String getDso_challan_no() {
		return dso_challan_no;
	}

	public void setDso_challan_no(String dso_challan_no) {
		this.dso_challan_no = dso_challan_no;
	}

	public String getDsp_transporter() {
		return dsp_transporter;
	}

	public void setDsp_transporter(String dsp_transporter) {
		this.dsp_transporter = dsp_transporter;
	}

	public String getDsp_lorry_no() {
		return dsp_lorry_no;
	}

	public void setDsp_lorry_no(String dsp_lorry_no) {
		this.dsp_lorry_no = dsp_lorry_no;
	}

	public Date getDsp_lr_dt() {
		return dsp_lr_dt;
	}

	public void setDsp_lr_dt(Date dsp_lr_dt) {
		this.dsp_lr_dt = dsp_lr_dt;
	}

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public String getDptloc_name() {
		return dptloc_name;
	}

	public void setDptloc_name(String dptloc_name) {
		this.dptloc_name = dptloc_name;
	}

	public Long getAlloc_period() {
		return alloc_period;
	}

	public void setAlloc_period(Long alloc_period) {
		this.alloc_period = alloc_period;
	}

	public String getAlloc_dsp_period() {
		return alloc_dsp_period;
	}

	public void setAlloc_dsp_period(String alloc_dsp_period) {
		this.alloc_dsp_period = alloc_dsp_period;
	}

	public String getDsp_manual_flg() {
		return dsp_manual_flg;
	}

	public void setDsp_manual_flg(String dsp_manual_flg) {
		this.dsp_manual_flg = dsp_manual_flg;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}

	public BigDecimal getDsp_total_goods_val() {
		return dsp_total_goods_val;
	}

	public void setDsp_total_goods_val(BigDecimal dsp_total_goods_val) {
		this.dsp_total_goods_val = dsp_total_goods_val;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getFstaff_destination() {
		return fstaff_destination;
	}

	public void setFstaff_destination(String fstaff_destination) {
		this.fstaff_destination = fstaff_destination;
	}

	public String getPrev_flg() {
		return prev_flg;
	}

	public void setPrev_flg(String prev_flg) {
		this.prev_flg = prev_flg;
	}

	public String getSumdsp_disp_cycle() {
		return sumdsp_disp_cycle;
	}

	public void setSumdsp_disp_cycle(String sumdsp_disp_cycle) {
		this.sumdsp_disp_cycle = sumdsp_disp_cycle;
	}

	public Long getFstaff_div_id() {
		return fstaff_div_id;
	}

	public void setFstaff_div_id(Long fstaff_div_id) {
		this.fstaff_div_id = fstaff_div_id;
	}

	public String getDsp_challan_msg() {
		return dsp_challan_msg;
	}

	public void setDsp_challan_msg(String dsp_challan_msg) {
		this.dsp_challan_msg = dsp_challan_msg;
	}

	public Long getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(Long div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getSum_dsp_id() {
		return sum_dsp_id;
	}

	public void setSum_dsp_id(String sum_dsp_id) {
		this.sum_dsp_id = sum_dsp_id;
	}

	
}
