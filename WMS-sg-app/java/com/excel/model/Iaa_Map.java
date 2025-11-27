package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "IAA_map")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Iaa_Map implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Reason_ID")
	private Long reason_id;

	@Column(name = "Reason_nm")
	private String reason_nm;
	
	@Column(name = "Item_change_accept")
	private String item_change_accept;
	
	@Column(name = "Out_stock_type")
	private String out_stock_type;
	
	@Column(name = "In_stock_type")
	private String in_stock_type;
	
	@Column(name = "In_item_accept")
	private String in_item_accept;
	
	@Column(name = "In_batch_accept")
	private String in_batch_accept;
	
	@Column(name = "In_qty_accept")
	private String in_qty_accept;
	
	@Column(name = "New_batch_accept")
	private String new_batch_accept;
	
	@Column(name = "In_rate_accept")
	private String in_rate_accept;
	
	@Column(name = "out_tran_type")
	private String out_tran_type;
	
	@Column(name = "in_tran_type")
	private String in_tran_type;
	
	@Column(name = "reason_mod_dt")
	private Date reason_mod_dt;
	
	@Column(name = "reason_mod_ip_add")
	private String reason_mod_ip_add;
	
	@Column(name = "reason_status")
	private String reason_status;

	public Long getReason_id() {
		return reason_id;
	}

	public void setReason_id(Long reason_id) {
		this.reason_id = reason_id;
	}

	public String getReason_nm() {
		return reason_nm;
	}

	public void setReason_nm(String reason_nm) {
		this.reason_nm = reason_nm;
	}

	public String getItem_change_accept() {
		return item_change_accept;
	}

	public void setItem_change_accept(String item_change_accept) {
		this.item_change_accept = item_change_accept;
	}

	public String getOut_stock_type() {
		return out_stock_type;
	}

	public void setOut_stock_type(String out_stock_type) {
		this.out_stock_type = out_stock_type;
	}

	public String getIn_stock_type() {
		return in_stock_type;
	}

	public void setIn_stock_type(String in_stock_type) {
		this.in_stock_type = in_stock_type;
	}

	public String getIn_item_accept() {
		return in_item_accept;
	}

	public void setIn_item_accept(String in_item_accept) {
		this.in_item_accept = in_item_accept;
	}

	public String getIn_batch_accept() {
		return in_batch_accept;
	}

	public void setIn_batch_accept(String in_batch_accept) {
		this.in_batch_accept = in_batch_accept;
	}

	public String getIn_qty_accept() {
		return in_qty_accept;
	}

	public void setIn_qty_accept(String in_qty_accept) {
		this.in_qty_accept = in_qty_accept;
	}

	public String getNew_batch_accept() {
		return new_batch_accept;
	}

	public void setNew_batch_accept(String new_batch_accept) {
		this.new_batch_accept = new_batch_accept;
	}

	public String getIn_rate_accept() {
		return in_rate_accept;
	}

	public void setIn_rate_accept(String in_rate_accept) {
		this.in_rate_accept = in_rate_accept;
	}

	public String getOut_tran_type() {
		return out_tran_type;
	}

	public void setOut_tran_type(String out_tran_type) {
		this.out_tran_type = out_tran_type;
	}

	public String getIn_tran_type() {
		return in_tran_type;
	}

	public void setIn_tran_type(String in_tran_type) {
		this.in_tran_type = in_tran_type;
	}

	public Date getReason_mod_dt() {
		return reason_mod_dt;
	}

	public void setReason_mod_dt(Date reason_mod_dt) {
		this.reason_mod_dt = reason_mod_dt;
	}

	public String getReason_mod_ip_add() {
		return reason_mod_ip_add;
	}

	public void setReason_mod_ip_add(String reason_mod_ip_add) {
		this.reason_mod_ip_add = reason_mod_ip_add;
	}

	public String getReason_status() {
		return reason_status;
	}

	public void setReason_status(String reason_status) {
		this.reason_status = reason_status;
	}	
	
	
	public Iaa_Map(Long reason_id, String reason_nm) {
		this.reason_id=reason_id;
		this.reason_nm=reason_nm;
	}
}