package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "BATCH_WTHREL_HDR")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class BatchWithholdReleaseHDR implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TRAN_ID")
	private Long tran_id;
	
	@Column(name="LOC_ID")
	private Long loc_id;
	
	@Column(name="FIN_YEAR_ID")
	private String fin_year_id;
	
	@Column(name="COMPANY_CD")
	private String company_cd;
	
	@Column(name="TRAN_NO")
	private String tran_no;
	
	@Column(name="TRAN_DATE")
	private Date tran_date;
	
	@Column(name="TRAN_TYPE")
	private String tran_type;
	
	@Column(name="STOCK_POINT_ID")
	private Long stock_point_id;
	
	@Column(name="GRN_QUARANTINE")
	private String grn_quarantine;
	
	@Column(name="REF_TRAN_ID")
	private Long ref_tran_id;
	
	@Column(name="REF_TRAN_NO")
	private String ref_tran_no;
	
	@Column(name="REF_TRAN_TYPE")
	private String ref_tran_type;
	
	@Column(name="BWTHREL_HDR_ins_usr_id")
	private String bwthrel_hdr_ins_usr_id;
	
	@Column(name="BWTHREL_HDR_mod_usr_id")
	private String bwthrel_hdr_mod_usr_id;
	
	@Column(name="BWTHREL_HDR_ins_dt")
	private Date bwthrel_hdr_ins_dt;
	
	@Column(name="BWTHREL_HDR_mod_dt")
	private Date bwthrel_hdr_mod_dt;
	
	@Column(name="BWTHREL_HDR_ins_ip_add")
	private String bwthrel_hdr_ins_ip_add;
	
	@Column(name="BWTHREL_HDR_mod_ip_add")
	private String bwthrel_hdr_mod_ip_add;
	
	@Column(name="BWTHREL_HDR_status")
	private String bwthrel_hdr_status;

	public Long getTran_id() {
		return tran_id;
	}

	public void setTran_id(Long tran_id) {
		this.tran_id = tran_id;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(String fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getTran_no() {
		return tran_no;
	}

	public void setTran_no(String tran_no) {
		this.tran_no = tran_no;
	}

	public Date getTran_date() {
		return tran_date;
	}

	public void setTran_date(Date tran_date) {
		this.tran_date = tran_date;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public Long getStock_point_id() {
		return stock_point_id;
	}

	public void setStock_point_id(Long stock_point_id) {
		this.stock_point_id = stock_point_id;
	}

	public String getGrn_quarantine() {
		return grn_quarantine;
	}

	public void setGrn_quarantine(String grn_quarantine) {
		this.grn_quarantine = grn_quarantine;
	}

	public Long getRef_tran_id() {
		return ref_tran_id;
	}

	public void setRef_tran_id(Long ref_tran_id) {
		this.ref_tran_id = ref_tran_id;
	}

	public String getRef_tran_no() {
		return ref_tran_no;
	}

	public void setRef_tran_no(String ref_tran_no) {
		this.ref_tran_no = ref_tran_no;
	}

	public String getRef_tran_type() {
		return ref_tran_type;
	}

	public void setRef_tran_type(String ref_tran_type) {
		this.ref_tran_type = ref_tran_type;
	}

	public String getBwthrel_hdr_ins_usr_id() {
		return bwthrel_hdr_ins_usr_id;
	}

	public void setBwthrel_hdr_ins_usr_id(String bwthrel_hdr_ins_usr_id) {
		this.bwthrel_hdr_ins_usr_id = bwthrel_hdr_ins_usr_id;
	}

	public String getBwthrel_hdr_mod_usr_id() {
		return bwthrel_hdr_mod_usr_id;
	}

	public void setBwthrel_hdr_mod_usr_id(String bwthrel_hdr_mod_usr_id) {
		this.bwthrel_hdr_mod_usr_id = bwthrel_hdr_mod_usr_id;
	}

	public Date getBwthrel_hdr_ins_dt() {
		return bwthrel_hdr_ins_dt;
	}

	public void setBwthrel_hdr_ins_dt(Date bwthrel_hdr_ins_dt) {
		this.bwthrel_hdr_ins_dt = bwthrel_hdr_ins_dt;
	}

	public Date getBwthrel_hdr_mod_dt() {
		return bwthrel_hdr_mod_dt;
	}

	public void setBwthrel_hdr_mod_dt(Date bwthrel_hdr_mod_dt) {
		this.bwthrel_hdr_mod_dt = bwthrel_hdr_mod_dt;
	}

	public String getBwthrel_hdr_ins_ip_add() {
		return bwthrel_hdr_ins_ip_add;
	}

	public void setBwthrel_hdr_ins_ip_add(String bwthrel_hdr_ins_ip_add) {
		this.bwthrel_hdr_ins_ip_add = bwthrel_hdr_ins_ip_add;
	}

	public String getBwthrel_hdr_mod_ip_add() {
		return bwthrel_hdr_mod_ip_add;
	}

	public void setBwthrel_hdr_mod_ip_add(String bwthrel_hdr_mod_ip_add) {
		this.bwthrel_hdr_mod_ip_add = bwthrel_hdr_mod_ip_add;
	}

	public String getBwthrel_hdr_status() {
		return bwthrel_hdr_status;
	}

	public void setBwthrel_hdr_status(String bwthrel_hdr_status) {
		this.bwthrel_hdr_status = bwthrel_hdr_status;
	}
	
	
	
}
