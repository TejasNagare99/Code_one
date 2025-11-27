package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="PICKSLIP_DTL")
public class Pickslip_dtl {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SLNO")
	private Long sl_no;
	
	@Column(name="PS_HDR_SLNO")
	private Long ps_hdr_slno;
	
	@Column(name="SD_TRAN_HDR_ID")
	private Long sd_tran_hdr_id;
	
	@Column(name="SD_TRAN_HDR_NO")
	private String sd_tran_hdr_no;
	
	@Column(name="PARTY_NAME")
	private String party_name;
	
	@Column(name="PS_DTL_STATUS")
	private String ps_dtl_status;
	
	@Column(name="INSERT_USER_ID")
	private String insert_user_id;
	
	@Column(name="MOD_USER_ID")
	private String mod_user_id;
	
	@Column(name="MOD_DATE")
	private Date mod_date;

	public Long getSl_no() {
		return sl_no;
	}

	public void setSl_no(Long sl_no) {
		this.sl_no = sl_no;
	}

	public Long getPs_hdr_slno() {
		return ps_hdr_slno;
	}

	public void setPs_hdr_slno(Long ps_hdr_slno) {
		this.ps_hdr_slno = ps_hdr_slno;
	}

	public Long getSd_tran_hdr_id() {
		return sd_tran_hdr_id;
	}

	public void setSd_tran_hdr_id(Long sd_tran_hdr_id) {
		this.sd_tran_hdr_id = sd_tran_hdr_id;
	}

	public String getSd_tran_hdr_no() {
		return sd_tran_hdr_no;
	}

	public void setSd_tran_hdr_no(String sd_tran_hdr_no) {
		this.sd_tran_hdr_no = sd_tran_hdr_no;
	}

	public String getParty_name() {
		return party_name;
	}

	public void setParty_name(String party_name) {
		this.party_name = party_name;
	}

	public String getPs_dtl_status() {
		return ps_dtl_status;
	}

	public void setPs_dtl_status(String ps_dtl_status) {
		this.ps_dtl_status = ps_dtl_status;
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

	public Date getMod_date() {
		return mod_date;
	}

	public void setMod_date(Date mod_date) {
		this.mod_date = mod_date;
	}

	
	@Override
	public String toString() {
		return "Pickslip_dtl [sl_no=" + sl_no + ", ps_hdr_slno=" + ps_hdr_slno + ", sd_tran_hdr_id=" + sd_tran_hdr_id
				+ ", sd_tran_hdr_no=" + sd_tran_hdr_no + ", party_name=" + party_name + ", ps_dtl_status="
				+ ps_dtl_status + ", insert_user_id=" + insert_user_id + ", mod_user_id=" + mod_user_id + ", mod_date="
				+ mod_date + "]";
	}
	
	
	
}
