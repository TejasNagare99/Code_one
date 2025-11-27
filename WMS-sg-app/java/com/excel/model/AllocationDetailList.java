package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "AllocationDetailList")
@NamedStoredProcedureQuery(
		name="callAllocationDetailList",
		procedureName = "p_v_auto_dispatch_header_java",
		resultClasses = AllocationDetailList.class,
		parameters = {
				@StoredProcedureParameter(name="pvemp_id", mode = ParameterMode.IN, type = String.class)
		}
)
public class AllocationDetailList {
	
//	@Id
//	@Column(name="rownum")
//	private Long rownum;
	
	@Id
	@Column(name="loc_id")
	private Long loc_id;

	@Column(name="loc_nm")
	private String loc_nm;

	@Column(name="ALLOC_PERIOD_ID")
	private Long alloc_period_id;

	@Column(name="ALLOC_PERIOD_COMPAMY")
	private String alloc_period_compamy;

	@Column(name="ALLOC_PERIOD_FIN_YEAR")
	private String alloc_period_fin_year;

	@Column(name="ALLOC_PERIOD_CODE")
	private String alloc_period_code;

	@Column(name="alloc_period")
	private String alloc_period;

	@Column(name="ALLOC_PERIOD_ALT_NAME")
	private String alloc_period_alt_name;

	@Column(name="ALLOC_PERIOD_START_DATE")
	private String alloc_period_start_date;

	@Column(name="ALLOC_PERIOD_END_DATE")
	private String alloc_period_end_date;

	@Column(name="ALLOC_PERIOD_CURRENT")
	private String alloc_period_current;

	@Column(name="ALLOC_PERIOD_status")
	private String alloc_period_status;

	@Column(name="DISP_PERIOD_ID")
	private Long disp_period_id;

	@Column(name="DISP_PERIOD_COMPAMY")
	private String disp_period_compamy;

	@Column(name="DISP_PERIOD_FIN_YEAR")
	private String disp_period_fin_year;

	@Column(name="DISP_PERIOD_CODE")
	private String disp_period_code;

	@Column(name="DISPATCH_period")
	private String dispatch_period;

	@Column(name="DISP_PERIOD_ALT_NAME")
	private String disp_period_alt_name;

	@Column(name="DISP_PERIOD_START_DATE")
	private String disp_period_start_date;

	@Column(name="DISP_PERIOD_END_DATE")
	private String disp_period_end_date;

	@Column(name="DISP_PERIOD_CURRENT")
	private String disp_period_current;

	@Column(name="DISP_PERIOD_status")
	private String disp_period_status;

	@Column(name="Dispatch_Dt")
	private String dispatch_dt;

	@Column(name="ALLOC_SMP_ID")
	private Long alloc_smp_id;

	@Column(name="ALLOC_FIN_YEAR")
	private String alloc_fin_year;

	@Column(name="Dispatch_Cycle")
	private Long dispatch_cycle;

	@Column(name="CHL_MSG")
	private String chl_msg;

	@Transient
	private String dsp_id;
	
	@Transient
	private String sumdsp_id;
	
//	public Long getRownum() {
//		return rownum;
//	}
//
//	public void setRownum(Long rownum) {
//		this.rownum = rownum;
//	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}


	public String getAlloc_period_compamy() {
		return alloc_period_compamy;
	}

	public void setAlloc_period_compamy(String alloc_period_compamy) {
		this.alloc_period_compamy = alloc_period_compamy;
	}

	public String getAlloc_period_fin_year() {
		return alloc_period_fin_year;
	}

	public void setAlloc_period_fin_year(String alloc_period_fin_year) {
		this.alloc_period_fin_year = alloc_period_fin_year;
	}

	public String getAlloc_period_code() {
		return alloc_period_code;
	}

	public void setAlloc_period_code(String alloc_period_code) {
		this.alloc_period_code = alloc_period_code;
	}

	public String getAlloc_period() {
		return alloc_period;
	}

	public void setAlloc_period(String alloc_period) {
		this.alloc_period = alloc_period;
	}

	public String getAlloc_period_alt_name() {
		return alloc_period_alt_name;
	}

	public void setAlloc_period_alt_name(String alloc_period_alt_name) {
		this.alloc_period_alt_name = alloc_period_alt_name;
	}

	public String getAlloc_period_start_date() {
		return alloc_period_start_date;
	}

	public void setAlloc_period_start_date(String alloc_period_start_date) {
		this.alloc_period_start_date = alloc_period_start_date;
	}

	public String getAlloc_period_end_date() {
		return alloc_period_end_date;
	}

	public void setAlloc_period_end_date(String alloc_period_end_date) {
		this.alloc_period_end_date = alloc_period_end_date;
	}

	public String getAlloc_period_current() {
		return alloc_period_current;
	}

	public void setAlloc_period_current(String alloc_period_current) {
		this.alloc_period_current = alloc_period_current;
	}

	public String getAlloc_period_status() {
		return alloc_period_status;
	}

	public void setAlloc_period_status(String alloc_period_status) {
		this.alloc_period_status = alloc_period_status;
	}

	public String getDisp_period_compamy() {
		return disp_period_compamy;
	}

	public void setDisp_period_compamy(String disp_period_compamy) {
		this.disp_period_compamy = disp_period_compamy;
	}

	public String getDisp_period_fin_year() {
		return disp_period_fin_year;
	}

	public void setDisp_period_fin_year(String disp_period_fin_year) {
		this.disp_period_fin_year = disp_period_fin_year;
	}

	public String getDisp_period_code() {
		return disp_period_code;
	}

	public void setDisp_period_code(String disp_period_code) {
		this.disp_period_code = disp_period_code;
	}

	public String getDispatch_period() {
		return dispatch_period;
	}

	public void setDispatch_period(String dispatch_period) {
		this.dispatch_period = dispatch_period;
	}

	public String getDisp_period_alt_name() {
		return disp_period_alt_name;
	}

	public void setDisp_period_alt_name(String disp_period_alt_name) {
		this.disp_period_alt_name = disp_period_alt_name;
	}

	public String getDisp_period_start_date() {
		return disp_period_start_date;
	}

	public void setDisp_period_start_date(String disp_period_start_date) {
		this.disp_period_start_date = disp_period_start_date;
	}

	public String getDisp_period_end_date() {
		return disp_period_end_date;
	}

	public void setDisp_period_end_date(String disp_period_end_date) {
		this.disp_period_end_date = disp_period_end_date;
	}

	public String getDisp_period_current() {
		return disp_period_current;
	}

	public void setDisp_period_current(String disp_period_current) {
		this.disp_period_current = disp_period_current;
	}

	public String getDisp_period_status() {
		return disp_period_status;
	}

	public void setDisp_period_status(String disp_period_status) {
		this.disp_period_status = disp_period_status;
	}

	public String getDispatch_dt() {
		return dispatch_dt;
	}

	public void setDispatch_dt(String dispatch_dt) {
		this.dispatch_dt = dispatch_dt;
	}

	public Long getAlloc_smp_id() {
		return alloc_smp_id;
	}

	public void setAlloc_smp_id(Long alloc_smp_id) {
		this.alloc_smp_id = alloc_smp_id;
	}

	public String getAlloc_fin_year() {
		return alloc_fin_year;
	}

	public void setAlloc_fin_year(String alloc_fin_year) {
		this.alloc_fin_year = alloc_fin_year;
	}

	public Long getDispatch_cycle() {
		return dispatch_cycle;
	}

	public void setDispatch_cycle(Long dispatch_cycle) {
		this.dispatch_cycle = dispatch_cycle;
	}

	public String getChl_msg() {
		return chl_msg;
	}

	public void setChl_msg(String chl_msg) {
		this.chl_msg = chl_msg;
	}

	public Long getAlloc_period_id() {
		return alloc_period_id;
	}

	public void setAlloc_period_id(Long alloc_period_id) {
		this.alloc_period_id = alloc_period_id;
	}

	public Long getDisp_period_id() {
		return disp_period_id;
	}

	public void setDisp_period_id(Long disp_period_id) {
		this.disp_period_id = disp_period_id;
	}

	public String getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(String dsp_id) {
		this.dsp_id = dsp_id;
	}

	public String getSumdsp_id() {
		return sumdsp_id;
	}

	public void setSumdsp_id(String sumdsp_id) {
		this.sumdsp_id = sumdsp_id;
	}
	
	
}
