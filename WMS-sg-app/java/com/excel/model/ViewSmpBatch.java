package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="v_SMPBatch")
public class ViewSmpBatch implements Serializable{

	private static final long serialVersionUID = 5282224091872207299L;
	
	@Id
	@Column(name="BATCH_ID")
	private Long batch_id;
	
	@Column(name="BATCH_PROD_ID")
	private Long batch_prod_id;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name="BATCH_status")
	private String batch_status;
	
	@Column(name="loc_nm")
	private String loc_nm;
	
	@Column(name="BATCH_NO")
	private String batch_no;

	public Long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(Long batch_id) {
		this.batch_id = batch_id;
	}

	public Long getBatch_prod_id() {
		return batch_prod_id;
	}

	public void setBatch_prod_id(Long batch_prod_id) {
		this.batch_prod_id = batch_prod_id;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getBatch_status() {
		return batch_status;
	}

	public void setBatch_status(String batch_status) {
		this.batch_status = batch_status;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

}
