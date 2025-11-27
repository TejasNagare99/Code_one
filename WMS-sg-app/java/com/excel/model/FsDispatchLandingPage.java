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
@Table(name = "FsDispatchLandingPage")
@NamedStoredProcedureQuery(
		name="CallFsDispatchLandingPage",
		procedureName = "dispatch_delivery_Landing_Page",
		resultClasses = FsDispatchLandingPage.class,
		parameters = {
				@StoredProcedureParameter(name="div_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="stprd", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="endprd", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="finyr", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="reptype", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="Log_fsid", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="prodid", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="dmterr", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="psoterr", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="self", mode = ParameterMode.IN, type = String.class)
		}
)
public class FsDispatchLandingPage {
	
	@Id
	@Column(name = "ROWNUM")
	private Long rownum;
	
	@Column(name = "MONTH_USE")
	private String month_use;
	
	@Column(name = "RM_TERR")
	private String rm_terr;
	
	@Column(name = "DM_TERR")
	private String dm_terr;
	
	@Column(name = "PSO_TERR")
	private String pso_terr;
	
	@Column(name = "DSP_CHALLAN_NO")
	private String dsp_challan_no;
	
	@Column(name = "DSP_CHALLAN_DT")
	private String dsp_challan_dt;
	
	@Column(name = "COURIER")
	private String courier;
	
	@Column(name = "DSP_LR_NO")
	private String dsp_lr_no;
	
	@Column(name = "DSP_LR_DT")
	private String dsp_lr_dt;
	
	@Column(name = "DSP_CASES")
	private String dsp_cases;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "BATCH_EXPIRY_DT")
	private String batch_expiry_dt;
	
	@Column(name = "DSPDTL_DISP_QTY")
	private BigDecimal dspdtl_disp_qty;
	
	@Column(name = "DSPDTL_RATE")
	private BigDecimal dspdtl_rate;
	
	@Column(name = "DISP_VALUE")
	private BigDecimal disp_value;
	
	@Column(name = "DSP_RET_QTY")
	private BigDecimal dsp_ret_qty;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "DSP_REMARK")
	private String dsp_remark;
	
	@Column(name="DSP_RECD_BY")
	private String dsp_recd_by;
	
	@Column(name="ACTUAL_DELIVERY_DATE")
	private String actual_delivery_date;
	
	@Column(name="DSP_TENTATIVE_DELIVERY_DATE")
	private String dsp_tentative_delivery_date;
	
	@Column(name="FSTAFF_TERR_CODE")
	private String fstaff_terr_code;
	
	@Column(name="FSTAFF_EMPLOYEE_NO")
	private String fstaff_employee_no;
	
	
	

	public String getFstaff_terr_code() {
		return fstaff_terr_code;
	}

	public void setFstaff_terr_code(String fstaff_terr_code) {
		this.fstaff_terr_code = fstaff_terr_code;
	}

	public String getFstaff_employee_no() {
		return fstaff_employee_no;
	}

	public void setFstaff_employee_no(String fstaff_employee_no) {
		this.fstaff_employee_no = fstaff_employee_no;
	}

	public String getDsp_recd_by() {
		return dsp_recd_by;
	}

	public void setDsp_recd_by(String dsp_recd_by) {
		this.dsp_recd_by = dsp_recd_by;
	}

	public String getActual_delivery_date() {
		return actual_delivery_date;
	}

	public void setActual_delivery_date(String actual_delivery_date) {
		this.actual_delivery_date = actual_delivery_date;
	}

	public String getDsp_tentative_delivery_date() {
		return dsp_tentative_delivery_date;
	}

	public void setDsp_tentative_delivery_date(String dsp_tentative_delivery_date) {
		this.dsp_tentative_delivery_date = dsp_tentative_delivery_date;
	}

	public String getMonth_use() {
		return month_use;
	}

	public void setMonth_use(String month_use) {
		this.month_use = month_use;
	}

	public String getRm_terr() {
		return rm_terr;
	}

	public void setRm_terr(String rm_terr) {
		this.rm_terr = rm_terr;
	}

	public String getDm_terr() {
		return dm_terr;
	}

	public void setDm_terr(String dm_terr) {
		this.dm_terr = dm_terr;
	}

	public String getPso_terr() {
		return pso_terr;
	}

	public void setPso_terr(String pso_terr) {
		this.pso_terr = pso_terr;
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

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
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

	public String getDsp_cases() {
		return dsp_cases;
	}

	public void setDsp_cases(String dsp_cases) {
		this.dsp_cases = dsp_cases;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
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

	public BigDecimal getDisp_value() {
		return disp_value;
	}

	public void setDisp_value(BigDecimal disp_value) {
		this.disp_value = disp_value;
	}

	public BigDecimal getDsp_ret_qty() {
		return dsp_ret_qty;
	}

	public void setDsp_ret_qty(BigDecimal dsp_ret_qty) {
		this.dsp_ret_qty = dsp_ret_qty;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getDsp_remark() {
		return dsp_remark;
	}

	public void setDsp_remark(String dsp_remark) {
		this.dsp_remark = dsp_remark;
	}

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}
	
	
	

	
	

}
