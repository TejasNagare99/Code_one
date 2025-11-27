package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ALLOC_REMARK")
public class Alloc_Remark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_REM_ID")
	private Long alloc_rem_id;
	
	@Column(name = "ALLOC_GEN_HD_ID")
	private Long alloc_gen_hd_id;
	
	@Column(name = "ALLOC_ID")
	private Long alloc_id;
	
	@Column(name = "ALLOC_REMARK")
	private String alloc_remark;

	public Long getAlloc_rem_id() {
		return alloc_rem_id;
	}

	public void setAlloc_rem_id(Long alloc_rem_id) {
		this.alloc_rem_id = alloc_rem_id;
	}

	public Long getAlloc_gen_hd_id() {
		return alloc_gen_hd_id;
	}

	public void setAlloc_gen_hd_id(Long alloc_gen_hd_id) {
		this.alloc_gen_hd_id = alloc_gen_hd_id;
	}

	public Long getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(Long alloc_id) {
		this.alloc_id = alloc_id;
	}

	public String getAlloc_remark() {
		return alloc_remark;
	}

	public void setAlloc_remark(String alloc_remark) {
		this.alloc_remark = alloc_remark;
	}
	
	
}
