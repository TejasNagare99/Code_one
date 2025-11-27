package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SUM_DISP_HEADER_ADDL")
public class Sum_Disp_Header_Addl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3301035281602778629L;

	@Id
	@Column(name = "SUMDSP_ADDL_ID")
	private Long sumdsp_addl_id;

	@Column(name = "SUMDSP_ADDL_CHALLAN_NO")
	private String sumdsp_addl_challan_no;
	
	@Column(name = "SUMDSP_ADDL_CHALLAN_DT")
	private Date sumdsp_addl_challan_dt;
	
	
	@Column(name = "SUMDSP_ID")
	private Long sumdsp_id;
	
	@ManyToOne
	@JoinColumn(name="SUMDSP_LOC_ID")
	private Location sumdsp_loc_id;
	
	@Column(name="SUMDSP_FIN_YEAR")
	private int sumdsp_fin_year;
	
	@Column(name="SUMDSP_PERIOD_CODE")
	private String sumdsp_period_code;
	
	@Column(name="SUMDSP_CHALLAN_DT")
	private Date sumdsp_challan_dt;
	
	@Column(name="SUMDSP_COMPANY")
	private String sumdsp_company;
	
	@Column(name = "SUMDSP_CHALLAN_NO")
	private String sumdsp_challan_no;

	@ManyToOne
	@JoinColumn(name = "SUMDSP_DIV_ID")
	private DivMaster sumdspdiv_id; 
	
	@Column(name="SUMDSP_LR_NO")
	private String sumdsp_lr_no;
	
	@Column(name="SUMDSP_LR_DT")
	private Date sumdsp_lr_dt;
	
	@Column(name="SUMDSP_TRANSPORTER")
	private String sumdsp_transporter;
	
	@Column(name="SUMDSP_WT")
	private BigDecimal sumdsp_wt;
	
	@Column(name="SUMDSP_TOTWT")
	private BigDecimal sumdsp_totwt;
	
	@Column(name="SUMDSP_CASES")
	private int sumdsp_cases;
	
	@Column(name="SUMDSP_TOTCASES")
	private int sumdsp_totcases;
	
	
	@Column(name="SUMDSP_DELIVERY_DATE")
	private Date sumdsp_delivery_date;
	
	@Column(name="SUMDSP_RECD_BY")
	private String sumdsp_recd_by;
	
	@Column(name="SUMDSP_REMARK")
	private String sumdsp_remark;
	
	
	
	
	
	/**
	 * @return the sumdsp_delivery_date
	 */
	public Date getSumdsp_delivery_date() {
		return sumdsp_delivery_date;
	}

	/**
	 * @param sumdsp_delivery_date the sumdsp_delivery_date to set
	 */
	public void setSumdsp_delivery_date(Date sumdsp_delivery_date) {
		this.sumdsp_delivery_date = sumdsp_delivery_date;
	}

	/**
	 * @return the sumdsp_recd_by
	 */
	public String getSumdsp_recd_by() {
		return sumdsp_recd_by;
	}

	/**
	 * @param sumdsp_recd_by the sumdsp_recd_by to set
	 */
	public void setSumdsp_recd_by(String sumdsp_recd_by) {
		this.sumdsp_recd_by = sumdsp_recd_by;
	}

	/**
	 * @return the sumdsp_remark
	 */
	public String getSumdsp_remark() {
		return sumdsp_remark;
	}

	/**
	 * @param sumdsp_remark the sumdsp_remark to set
	 */
	public void setSumdsp_remark(String sumdsp_remark) {
		this.sumdsp_remark = sumdsp_remark;
	}

	public Location getSumdsp_loc_id() {
		return sumdsp_loc_id;
	}

	public void setSumdsp_loc_id(Location sumdsp_loc_id) {
		this.sumdsp_loc_id = sumdsp_loc_id;
	}

	public int getSumdsp_fin_year() {
		return sumdsp_fin_year;
	}

	public void setSumdsp_fin_year(int sumdsp_fin_year) {
		this.sumdsp_fin_year = sumdsp_fin_year;
	}

	public String getSumdsp_period_code() {
		return sumdsp_period_code;
	}

	public void setSumdsp_period_code(String sumdsp_period_code) {
		this.sumdsp_period_code = sumdsp_period_code;
	}

	public Date getSumdsp_challan_dt() {
		return sumdsp_challan_dt;
	}

	public void setSumdsp_challan_dt(Date sumdsp_challan_dt) {
		this.sumdsp_challan_dt = sumdsp_challan_dt;
	}

	public String getSumdsp_company() {
		return sumdsp_company;
	}

	public void setSumdsp_company(String sumdsp_company) {
		this.sumdsp_company = sumdsp_company;
	}

	public Long getSumdsp_id() {
		return sumdsp_id;
	}

	public void setSumdsp_id(Long sumdsp_id) {
		this.sumdsp_id = sumdsp_id;
	}

	public String getSumdsp_challan_no() {
		return sumdsp_challan_no;
	}

	public void setSumdsp_challan_no(String sumdsp_challan_no) {
		this.sumdsp_challan_no = sumdsp_challan_no;
	}

	public DivMaster getSumdspdiv_id() {
		return sumdspdiv_id;
	}

	public void setSumdspdiv_id(DivMaster sumdspdiv_id) {
		this.sumdspdiv_id = sumdspdiv_id;
	}

	public String getSumdsp_lr_no() {
		return sumdsp_lr_no;
	}

	public void setSumdsp_lr_no(String sumdsp_lr_no) {
		this.sumdsp_lr_no = sumdsp_lr_no;
	}

	public Date getSumdsp_lr_dt() {
		return sumdsp_lr_dt;
	}

	public void setSumdsp_lr_dt(Date sumdsp_lr_dt) {
		this.sumdsp_lr_dt = sumdsp_lr_dt;
	}

	public String getSumdsp_transporter() {
		return sumdsp_transporter;
	}

	public void setSumdsp_transporter(String sumdsp_transporter) {
		this.sumdsp_transporter = sumdsp_transporter;
	}

	public BigDecimal getSumdsp_wt() {
		return sumdsp_wt;
	}

	public void setSumdsp_wt(BigDecimal sumdsp_wt) {
		this.sumdsp_wt = sumdsp_wt;
	}

	public BigDecimal getSumdsp_totwt() {
		return sumdsp_totwt;
	}

	public void setSumdsp_totwt(BigDecimal sumdsp_totwt) {
		this.sumdsp_totwt = sumdsp_totwt;
	}

	public Long getSumdsp_addl_id() {
		return sumdsp_addl_id;
	}

	public void setSumdsp_addl_id(Long sumdsp_addl_id) {
		this.sumdsp_addl_id = sumdsp_addl_id;
	}

	public String getSumdsp_addl_challan_no() {
		return sumdsp_addl_challan_no;
	}

	public void setSumdsp_addl_challan_no(String sumdsp_addl_challan_no) {
		this.sumdsp_addl_challan_no = sumdsp_addl_challan_no;
	}

	public Date getSumdsp_addl_challan_dt() {
		return sumdsp_addl_challan_dt;
	}

	public void setSumdsp_addl_challan_dt(Date sumdsp_addl_challan_dt) {
		this.sumdsp_addl_challan_dt = sumdsp_addl_challan_dt;
	}

	public int getSumdsp_cases() {
		return sumdsp_cases;
	}

	public void setSumdsp_cases(int sumdsp_cases) {
		this.sumdsp_cases = sumdsp_cases;
	}

	public int getSumdsp_totcases() {
		return sumdsp_totcases;
	}

	public void setSumdsp_totcases(int sumdsp_totcases) {
		this.sumdsp_totcases = sumdsp_totcases;
	}


}
