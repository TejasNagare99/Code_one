package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ERP_STATUS_LOG")
public class Erp_status_log {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SLNO")
	public Long slno;
	
	@Column(name="SEND_DATE")
	public Date send_date;
	
	@Column(name="CYCLE")
	public int cycle;
	
	@Column(name="START_TIME")
	public String start_time;
	
	@Column(name="END_TIME")
	private String end_time;
	
	@Column(name="GRN")
	public int grn;
	
	@Column(name="GRN_CONFIRM")
	public int grn_confirm;
	
	@Column(name="DSP")
	public int dsp;
	
	@Column(name="DSP_CANCEL")
	public int dsp_cancel;
	
	@Column(name="IAA_NORMAL")
	public int iaa_normal;
	
	@Column(name="IAA_GRN")
	public int iaa_grn;
	
	@Column(name="IAA_WMS")
	public int iaa_wms;
	
	@Column(name="PRODUCT")
	public int product;
	
	@Column(name="BATCH")
	public int batch;

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public Date getSend_date() {
		return send_date;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public int getGrn() {
		return grn;
	}

	public void setGrn(int grn) {
		this.grn = grn;
	}

	public int getGrn_confirm() {
		return grn_confirm;
	}

	public void setGrn_confirm(int grn_confirm) {
		this.grn_confirm = grn_confirm;
	}

	public int getDsp() {
		return dsp;
	}

	public void setDsp(int dsp) {
		this.dsp = dsp;
	}

	public int getDsp_cancel() {
		return dsp_cancel;
	}

	public void setDsp_cancel(int dsp_cancel) {
		this.dsp_cancel = dsp_cancel;
	}

	public int getIaa_normal() {
		return iaa_normal;
	}

	public void setIaa_normal(int iaa_normal) {
		this.iaa_normal = iaa_normal;
	}

	public int getIaa_grn() {
		return iaa_grn;
	}

	public void setIaa_grn(int iaa_grn) {
		this.iaa_grn = iaa_grn;
	}

	public int getIaa_wms() {
		return iaa_wms;
	}

	public void setIaa_wms(int iaa_wms) {
		this.iaa_wms = iaa_wms;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	

}
