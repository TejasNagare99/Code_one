package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "BATCH_WTHREL_DTL")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class BatchWithholdReleaseDTL implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="TRAN_ID")
	private Long tran_id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TRAN_DTL_ID")
	private Long tran_dtl_id;
	
	@Column(name="LOC_ID")
	private Long loc_id;
	
	@Column(name="FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name="COMPANY_CD")
	private String company_cd;
	
	@Column(name="TRAN_DATE")
	private Date tran_date;
	
	@Column(name="PROD_ID")
	private Long prod_id;
	
	@Column(name="BATCH_ID")
	private Long batch_id;
	
	@Column(name="RATE_NR")
	private BigDecimal rate_nr;
	
	@Column(name="QTY")
	private BigDecimal qty;
	
	@Column(name="REASON_ID")
	private Long reason_id;
	
	@Column(name="TRAN_TYPE")
	private String tran_type;
	
	@Column(name="TRAN_NO")
	private String tran_no;
	
	@Column(name="STOCK_POINT_ID")
	private Long stock_point_id;
	
	@Column(name="REF_TRAN_DTL_ID")
	private Long ref_tran_dtl_id;
	
	@Column(name="BWTHREL_DTL_ins_usr_id")
	private String bwthrel_dtl_ins_usr_id;
	
	@Column(name="BWTHREL_DTL_mod_usr_id")
	private String bwthrel_dtl_mod_usr_id;
	
	@Column(name="BWTHREL_DTL_ins_dt")
	private Date bwthrel_dtl_ins_dt;
	
	@Column(name="BWTHREL_DTL_mod_dt")
	private Date bwthrel_dtl_mod_dt;
	
	@Column(name="BWTHREL_DTL_ins_ip_add")
	private String bwthrel_dtl_ins_ip_add;
	
	@Column(name="BWTHREL_DTL_mod_ip_add")
	private String bwthrel_dtl_mod_ip_add;
	
	@Column(name="BWTHREL_DTL_status")
	private String bwthrel_dtl_status;

	public Long getTran_id() {
		return tran_id;
	}

	public void setTran_id(Long tran_id) {
		this.tran_id = tran_id;
	}

	public Long getTran_dtl_id() {
		return tran_dtl_id;
	}

	public void setTran_dtl_id(Long tran_dtl_id) {
		this.tran_dtl_id = tran_dtl_id;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public Date getTran_date() {
		return tran_date;
	}

	public void setTran_date(Date tran_date) {
		this.tran_date = tran_date;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public Long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(Long batch_id) {
		this.batch_id = batch_id;
	}

	public BigDecimal getRate_nr() {
		return rate_nr;
	}

	public void setRate_nr(BigDecimal rate_nr) {
		this.rate_nr = rate_nr;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public Long getReason_id() {
		return reason_id;
	}

	public void setReason_id(Long reason_id) {
		this.reason_id = reason_id;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public String getTran_no() {
		return tran_no;
	}

	public void setTran_no(String tran_no) {
		this.tran_no = tran_no;
	}

	public Long getStock_point_id() {
		return stock_point_id;
	}

	public void setStock_point_id(Long stock_point_id) {
		this.stock_point_id = stock_point_id;
	}

	public Long getRef_tran_dtl_id() {
		return ref_tran_dtl_id;
	}

	public void setRef_tran_dtl_id(Long ref_tran_dtl_id) {
		this.ref_tran_dtl_id = ref_tran_dtl_id;
	}

	public String getBwthrel_dtl_ins_usr_id() {
		return bwthrel_dtl_ins_usr_id;
	}

	public void setBwthrel_dtl_ins_usr_id(String bwthrel_dtl_ins_usr_id) {
		this.bwthrel_dtl_ins_usr_id = bwthrel_dtl_ins_usr_id;
	}

	public String getBwthrel_dtl_mod_usr_id() {
		return bwthrel_dtl_mod_usr_id;
	}

	public void setBwthrel_dtl_mod_usr_id(String bwthrel_dtl_mod_usr_id) {
		this.bwthrel_dtl_mod_usr_id = bwthrel_dtl_mod_usr_id;
	}

	public Date getBwthrel_dtl_ins_dt() {
		return bwthrel_dtl_ins_dt;
	}

	public void setBwthrel_dtl_ins_dt(Date bwthrel_dtl_ins_dt) {
		this.bwthrel_dtl_ins_dt = bwthrel_dtl_ins_dt;
	}

	public Date getBwthrel_dtl_mod_dt() {
		return bwthrel_dtl_mod_dt;
	}

	public void setBwthrel_dtl_mod_dt(Date bwthrel_dtl_mod_dt) {
		this.bwthrel_dtl_mod_dt = bwthrel_dtl_mod_dt;
	}

	public String getBwthrel_dtl_ins_ip_add() {
		return bwthrel_dtl_ins_ip_add;
	}

	public void setBwthrel_dtl_ins_ip_add(String bwthrel_dtl_ins_ip_add) {
		this.bwthrel_dtl_ins_ip_add = bwthrel_dtl_ins_ip_add;
	}

	public String getBwthrel_dtl_mod_ip_add() {
		return bwthrel_dtl_mod_ip_add;
	}

	public void setBwthrel_dtl_mod_ip_add(String bwthrel_dtl_mod_ip_add) {
		this.bwthrel_dtl_mod_ip_add = bwthrel_dtl_mod_ip_add;
	}

	public String getBwthrel_dtl_status() {
		return bwthrel_dtl_status;
	}

	public void setBwthrel_dtl_status(String bwthrel_dtl_status) {
		this.bwthrel_dtl_status = bwthrel_dtl_status;
	}
	

	
	
}
