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
@Table(name = "AllocDispatchTracker")
@NamedStoredProcedureQuery(name = "callAllocDispatchTracker", procedureName = "ALLOCATION_DISPATCH_TRACKER", resultClasses = AllocDispatchTracker.class, parameters = {
		@StoredProcedureParameter(name = "loc_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "div_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "alloc_type", mode = ParameterMode.IN, type = String.class) })

public class AllocDispatchTracker {

	@Id
	@Column(name = "ROWNUM")
	private String rownum;
	@Column(name = "loc_nm")
	private String loc_nm;
	@Column(name = "TERRITORY")
	private String territory;
	@Column(name = "FSTAFF_EMPLOYEE_NO")
	private Long fstaff_employee_no;
	@Column(name = "ALLOC_FIN_YEAR")
	private Long alloc_fin_year;
	@Column(name = "ALLOC_PERIOD_CODE")
	private String alloc_period_code;
	@Column(name = "MONTH")
	private String month;
	@Column(name = "DIVISION")
	private String division;
	@Column(name = "DESIGNATION")
	private String designation;
	@Column(name = "FSTAFF_CODE")
	private String fstaff_code;
	@Column(name = "FSTAFF_ID")
	private Long fstaff_id;
	@Column(name = "FSTAFF_DISPLAY_NAME")
	private String fstaff_display_name;
	@Column(name = "FSTAFF_DESTINATION")
	private String fstaff_destination;
	@Column(name = "ALLOC_TYPE")
	private String alloc_type;
	@Column(name = "TEAM_CODE")
	private String team_code;
	@Column(name = "TEAM")
	private String team;
	@Column(name = "UPLOADED_DATE")
	private String uploaded_date;
	@Column(name = "DSP_ID")
	private Long dsp_id;
	@Column(name = "DSP_CHALLAN_NO")
	private String dsp_challan_no;
	@Column(name = "DSP_CHALLAN_DT")
	private String dsp_challan_dt;
	@Column(name = "DSP_DT")
	private String dsp_dt;
	@Column(name = "DSP_LR_NO")
	private String dsp_lr_no;
	@Column(name = "DSP_LR_DT")
	private String dsp_lr_dt;
	@Column(name = "COURIER_NAME")
	private String courier_name;
	@Column(name = "DSP_CASES")
	private Long dsp_cases;
	@Column(name = "ALLOCDTL_PROD_ID")
	private Long allocdtl_prod_id;
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	@Column(name = "ALLOC_QTY")
	private Long alloc_qty;
	@Column(name = "APPROVED_ALLOC_QTY")
	private BigDecimal approved_alloc_qty;
	@Column(name = "DISP_QTY")
	private BigDecimal disp_qty;
	@Column(name="APPROVAL_DATE")
	private String approval_date;
	@Column(name="ALLOC_NO")
	private String alloc_no;
	@Column(name="CSV_FILE_NAME")
	private String csv_file_name;
	@Column(name="ALLOC_GEN_HD_ID")
	private Long alloc_gen_hd_id;
	@Column(name="ACTUAL_DELIVERY_DATE")
	private String actual_delivery_date;
	
	public String getActual_delivery_date() {
		return actual_delivery_date;
	}
	public void setActual_delivery_date(String actual_delivery_date) {
		this.actual_delivery_date = actual_delivery_date;
	}	
	
	public String getCsv_file_name() {
		return csv_file_name;
	}
	public void setCsv_file_name(String csv_file_name) {
		this.csv_file_name = csv_file_name;
	}
	public Long getAlloc_gen_hd_id() {
		return alloc_gen_hd_id;
	}
	public void setAlloc_gen_hd_id(Long alloc_gen_hd_id) {
		this.alloc_gen_hd_id = alloc_gen_hd_id;
	}
	public String getApproval_date() {
		return approval_date;
	}
	public void setApproval_date(String approval_date) {
		this.approval_date = approval_date;
	}
	public String getAlloc_no() {
		return alloc_no;
	}
	public void setAlloc_no(String alloc_no) {
		this.alloc_no = alloc_no;
	}
	public String getLoc_nm() {
		return loc_nm;
	}
	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}
	public String getTerritory() {
		return territory;
	}
	public void setTerritory(String territory) {
		this.territory = territory;
	}
	public Long getFstaff_employee_no() {
		return fstaff_employee_no;
	}
	public void setFstaff_employee_no(Long fstaff_employee_no) {
		this.fstaff_employee_no = fstaff_employee_no;
	}
	public Long getAlloc_fin_year() {
		return alloc_fin_year;
	}
	public void setAlloc_fin_year(Long alloc_fin_year) {
		this.alloc_fin_year = alloc_fin_year;
	}
	public String getAlloc_period_code() {
		return alloc_period_code;
	}
	public void setAlloc_period_code(String alloc_period_code) {
		this.alloc_period_code = alloc_period_code;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getFstaff_code() {
		return fstaff_code;
	}
	public void setFstaff_code(String fstaff_code) {
		this.fstaff_code = fstaff_code;
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
	public String getFstaff_destination() {
		return fstaff_destination;
	}
	public void setFstaff_destination(String fstaff_destination) {
		this.fstaff_destination = fstaff_destination;
	}
	public String getAlloc_type() {
		return alloc_type;
	}
	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}
	public String getTeam_code() {
		return team_code;
	}
	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getUploaded_date() {
		return uploaded_date;
	}
	public void setUploaded_date(String uploaded_date) {
		this.uploaded_date = uploaded_date;
	}
	public Long getDsp_id() {
		return dsp_id;
	}
	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
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
	public String getDsp_dt() {
		return dsp_dt;
	}
	public void setDsp_dt(String dsp_dt) {
		this.dsp_dt = dsp_dt;
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
	public String getCourier_name() {
		return courier_name;
	}
	public void setCourier_name(String courier_name) {
		this.courier_name = courier_name;
	}
	public Long getDsp_cases() {
		return dsp_cases;
	}
	public void setDsp_cases(Long dsp_cases) {
		this.dsp_cases = dsp_cases;
	}
	public Long getAllocdtl_prod_id() {
		return allocdtl_prod_id;
	}
	public void setAllocdtl_prod_id(Long allocdtl_prod_id) {
		this.allocdtl_prod_id = allocdtl_prod_id;
	}
	public String getSmp_prod_name() {
		return smp_prod_name;
	}
	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}
	public Long getAlloc_qty() {
		return alloc_qty;
	}
	public void setAlloc_qty(Long alloc_qty) {
		this.alloc_qty = alloc_qty;
	}
	public BigDecimal getApproved_alloc_qty() {
		return approved_alloc_qty;
	}
	public void setApproved_alloc_qty(BigDecimal approved_alloc_qty) {
		this.approved_alloc_qty = approved_alloc_qty;
	}
	public BigDecimal getDisp_qty() {
		return disp_qty;
	}
	public void setDisp_qty(BigDecimal disp_qty) {
		this.disp_qty = disp_qty;
	}
	
	

}
