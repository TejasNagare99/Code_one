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
@Table(name="Productwise_batchwise_locationwise_stock")
@NamedStoredProcedureQuery (name = "call_PROD_BATCH_LOC_STOCK",procedureName = "PROD_BATCH_LOC_STOCK",
parameters = {
		@StoredProcedureParameter(name="prod_id" , mode=ParameterMode.IN,type=String.class)
		
},resultClasses=Productwise_batchwise_locationwise_stock.class)
public class Productwise_batchwise_locationwise_stock {
	
	@Id
	@Column(name = "ROWNUM")
	private long rownum;
	
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name="SMP_PROD_ID")
	private String smp_prod_id;
	
	@Column(name="SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name="BATCH_NO")
	private String batch_no;
	
	@Column(name="EXPIRY_DATE")
	private String expiry_date;
	
	@Column(name="loc_id")
	private String loc_id;
	
	@Column(name="loc_nm")
	private String loc_nm;
	
	@Column(name="STOCK")
	private BigDecimal stock;
	
	@Column(name="WITH_HELD_QTY")
	private BigDecimal with_held_qty;

	public long getRownum() {
		return rownum;
	}

	public void setRownum(long rownum) {
		this.rownum = rownum;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(String smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public BigDecimal getWith_held_qty() {
		return with_held_qty;
	}

	public void setWith_held_qty(BigDecimal with_held_qty) {
		this.with_held_qty = with_held_qty;
	}


	
	
	
	
}
