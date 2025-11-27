package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="PICKSLIP_HDR")
public class Pickslip_hdr {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SLNO")
	private Long  sl_no;
	
	@Column(name="PS_TRAN_DATE")
	private Date ps_tran_date;
	
	@Column(name="PS_HDR_STATUS")
	private String ps_hdr_status;
	
	@Column(name="PS_TRAN_NO")
	private String ps_tran_no;
	
	@Column(name="ps_comp_cd")
	private String ps_comp_cd;
	
	@Column(name="fin_year_id")
	private Long fin_year_id;
	
	@Column(name="period_cd")
	private String period_cd;
	
	@Column(name="wh_id")
	private Long wh_id;
	
	@Column(name="SUB_COMPANY")
	private String sub_company;
	
	@Column(name="PS_TOTAL_WEIGHT")
	private BigDecimal ps_total_weight;
	
	@Column(name="PS_TOTAL_VOLUME")
	private BigDecimal ps_total_volume;
	
	@Column(name="INSERT_USER_ID")
	private String insert_user_id;
	
	@Column(name="MOD_USER_ID")
	private String mod_user_id;
	
	@Column(name="MOD_DATE")
	private String mod_date;
	
	@Column(name="PICKSLIP_NO")
	private Long pickslip_no;

	public Long getSl_no() {
		return sl_no;
	}

	public void setSl_no(Long sl_no) {
		this.sl_no = sl_no;
	}

	public Date getPs_tran_date() {
		return ps_tran_date;
	}

	public void setPs_tran_date(Date ps_tran_date) {
		this.ps_tran_date = ps_tran_date;
	}

	public String getPs_hdr_status() {
		return ps_hdr_status;
	}

	public void setPs_hdr_status(String ps_hdr_status) {
		this.ps_hdr_status = ps_hdr_status;
	}

	public String getPs_tran_no() {
		return ps_tran_no;
	}

	public void setPs_tran_no(String ps_tran_no) {
		this.ps_tran_no = ps_tran_no;
	}

	public String getPs_comp_cd() {
		return ps_comp_cd;
	}

	public void setPs_comp_cd(String ps_comp_cd) {
		this.ps_comp_cd = ps_comp_cd;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getPeriod_cd() {
		return period_cd;
	}

	public void setPeriod_cd(String period_cd) {
		this.period_cd = period_cd;
	}

	public Long getWh_id() {
		return wh_id;
	}

	public void setWh_id(Long wh_id) {
		this.wh_id = wh_id;
	}

	public String getSub_company() {
		return sub_company;
	}

	public void setSub_company(String sub_company) {
		this.sub_company = sub_company;
	}

	public BigDecimal getPs_total_weight() {
		return ps_total_weight;
	}

	public void setPs_total_weight(BigDecimal ps_total_weight) {
		this.ps_total_weight = ps_total_weight;
	}

	public BigDecimal getPs_total_volume() {
		return ps_total_volume;
	}

	public void setPs_total_volume(BigDecimal ps_total_volume) {
		this.ps_total_volume = ps_total_volume;
	}

	public String getInsert_user_id() {
		return insert_user_id;
	}

	public void setInsert_user_id(String insert_user_id) {
		this.insert_user_id = insert_user_id;
	}

	public String getMod_user_id() {
		return mod_user_id;
	}

	public void setMod_user_id(String mod_user_id) {
		this.mod_user_id = mod_user_id;
	}

	public String getMod_date() {
		return mod_date;
	}

	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}

	public Long getPickslip_no() {
		return pickslip_no;
	}

	public void setPickslip_no(Long pickslip_no) {
		this.pickslip_no = pickslip_no;
	}

	@Override
	public String toString() {
		return "Pickslip_hdr [sl_no=" + sl_no + ", ps_tran_date=" + ps_tran_date + ", ps_hdr_status=" + ps_hdr_status
				+ ", ps_tran_no=" + ps_tran_no + ", ps_comp_cd=" + ps_comp_cd + ", fin_year_id=" + fin_year_id
				+ ", period_cd=" + period_cd + ", wh_id=" + wh_id + ", sub_company=" + sub_company
				+ ", ps_total_weight=" + ps_total_weight + ", ps_total_volume=" + ps_total_volume + ", insert_user_id="
				+ insert_user_id + ", mod_user_id=" + mod_user_id + ", mod_date=" + mod_date + ", pickslip_no="
				+ pickslip_no + "]";
	}
	
	
	
}
