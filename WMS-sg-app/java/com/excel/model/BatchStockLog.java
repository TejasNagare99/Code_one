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

@Entity
@Table(name="BATCH_STOCK_LOG")
@DynamicUpdate(value=true)
public class BatchStockLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303520735414486209L;

	@Id
	@Column(name="BTCHSTKLG_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long btchstklg_id;
	
	@Column(name="BTCHSTKLG_FIN_YEAR")
	private String btchstklg_fin_year;
	
	@Column(name="BTCHSTKLG_DATE")
	private Date btchstklg_date;

	@Column(name="BTCHSTKLG_LOC_ID")
	private Long btchstklg_loc_id;

	@Column(name="BTCHSTKLG_PROD_ID")
	private Long btchstklg_prod_id;

	@Column(name="BTCHSTKLG_BATCH_ID")
	private Long btchstklg_batch_id;

	@Column(name="BTCHSTKLG_DIV_ID")
	private Long btchstklg_div_id;

	@Column(name="BTCHSTKLG_TRAN_TYPE")
	private String btchstklg_tran_type;

	@Column(name="BTCHSTKLG_QTY")
	private BigDecimal btchstklg_qty;

	@Column(name="BTCHSTKLG_VALUE")
	private BigDecimal btchstklg_value;

	@Column(name="BTCHSTKLG_ins_usr_id")
	private String btchstklg_ins_usr_id;

	@Column(name="BTCHSTKLG_mod_usr_id")
	private String btchstklg_mod_usr_id;

	@Column(name="BTCHSTKLG_ins_dt")
	private Date btchstklg_ins_dt;

	@Column(name="BTCHSTKLG_mod_dt")
	private Date btchstklg_mod_dt;

	@Column(name="BTCHSTKLG_ins_ip_add")
	private String btchstklg_ins_ip_add;

	@Column(name="BTCHSTKLG_mod_ip_add")
	private String btchstklg_mod_ip_add;

	@Column(name="BTCHSTKLG_status")
	private String btchstklg_status;

	public Long getBtchstklg_id() {
		return btchstklg_id;
	}

	public void setBtchstklg_id(Long btchstklg_id) {
		this.btchstklg_id = btchstklg_id;
	}

	public String getBtchstklg_fin_year() {
		return btchstklg_fin_year;
	}

	public void setBtchstklg_fin_year(String btchstklg_fin_year) {
		this.btchstklg_fin_year = btchstklg_fin_year;
	}

	public Date getBtchstklg_date() {
		return btchstklg_date;
	}

	public void setBtchstklg_date(Date btchstklg_date) {
		this.btchstklg_date = btchstklg_date;
	}

	public Long getBtchstklg_loc_id() {
		return btchstklg_loc_id;
	}

	public void setBtchstklg_loc_id(Long btchstklg_loc_id) {
		this.btchstklg_loc_id = btchstklg_loc_id;
	}

	public Long getBtchstklg_prod_id() {
		return btchstklg_prod_id;
	}

	public void setBtchstklg_prod_id(Long btchstklg_prod_id) {
		this.btchstklg_prod_id = btchstklg_prod_id;
	}

	public Long getBtchstklg_batch_id() {
		return btchstklg_batch_id;
	}

	public void setBtchstklg_batch_id(Long btchstklg_batch_id) {
		this.btchstklg_batch_id = btchstklg_batch_id;
	}

	public Long getBtchstklg_div_id() {
		return btchstklg_div_id;
	}

	public void setBtchstklg_div_id(Long btchstklg_div_id) {
		this.btchstklg_div_id = btchstklg_div_id;
	}

	public String getBtchstklg_tran_type() {
		return btchstklg_tran_type;
	}

	public void setBtchstklg_tran_type(String btchstklg_tran_type) {
		this.btchstklg_tran_type = btchstklg_tran_type;
	}

	public BigDecimal getBtchstklg_qty() {
		return btchstklg_qty;
	}

	public void setBtchstklg_qty(BigDecimal btchstklg_qty) {
		this.btchstklg_qty = btchstklg_qty;
	}

	public BigDecimal getBtchstklg_value() {
		return btchstklg_value;
	}

	public void setBtchstklg_value(BigDecimal btchstklg_value) {
		this.btchstklg_value = btchstklg_value;
	}

	public String getBtchstklg_ins_usr_id() {
		return btchstklg_ins_usr_id;
	}

	public void setBtchstklg_ins_usr_id(String btchstklg_ins_usr_id) {
		this.btchstklg_ins_usr_id = btchstklg_ins_usr_id;
	}

	public String getBtchstklg_mod_usr_id() {
		return btchstklg_mod_usr_id;
	}

	public void setBtchstklg_mod_usr_id(String btchstklg_mod_usr_id) {
		this.btchstklg_mod_usr_id = btchstklg_mod_usr_id;
	}

	public Date getBtchstklg_ins_dt() {
		return btchstklg_ins_dt;
	}

	public void setBtchstklg_ins_dt(Date btchstklg_ins_dt) {
		this.btchstklg_ins_dt = btchstklg_ins_dt;
	}

	public Date getBtchstklg_mod_dt() {
		return btchstklg_mod_dt;
	}

	public void setBtchstklg_mod_dt(Date btchstklg_mod_dt) {
		this.btchstklg_mod_dt = btchstklg_mod_dt;
	}

	public String getBtchstklg_ins_ip_add() {
		return btchstklg_ins_ip_add;
	}

	public void setBtchstklg_ins_ip_add(String btchstklg_ins_ip_add) {
		this.btchstklg_ins_ip_add = btchstklg_ins_ip_add;
	}

	public String getBtchstklg_mod_ip_add() {
		return btchstklg_mod_ip_add;
	}

	public void setBtchstklg_mod_ip_add(String btchstklg_mod_ip_add) {
		this.btchstklg_mod_ip_add = btchstklg_mod_ip_add;
	}

	public String getBtchstklg_status() {
		return btchstklg_status;
	}

	public void setBtchstklg_status(String btchstklg_status) {
		this.btchstklg_status = btchstklg_status;
	}

}
