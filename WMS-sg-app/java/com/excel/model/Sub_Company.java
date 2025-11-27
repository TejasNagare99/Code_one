package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sub_company")
public class Sub_Company {

	@Id
	@Column(name = "SubComp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subcomp_id;
	@Column(name = "SubComp_code")
	private String subcomp_code;
	@Column(name = "SubComp_Nm")
	private String subcomp_nm;
	@Column(name = "SubComp_status")
	private String subcomp_status;

	public Sub_Company(Long subcomp_id,String subcomp_nm) {
		this.subcomp_id=subcomp_id;
		this.subcomp_nm=subcomp_nm;
	}

	public Long getSubcomp_id() {
		return subcomp_id;
	}

	public void setSubcomp_id(Long subcomp_id) {
		this.subcomp_id = subcomp_id;
	}

	public String getSubcomp_code() {
		return subcomp_code;
	}

	public void setSubcomp_code(String subcomp_code) {
		this.subcomp_code = subcomp_code;
	}

	public String getSubcomp_nm() {
		return subcomp_nm;
	}

	public void setSubcomp_nm(String subcomp_nm) {
		this.subcomp_nm = subcomp_nm;
	}

	public String getSubcomp_status() {
		return subcomp_status;
	}

	public void setSubcomp_status(String subcomp_status) {
		this.subcomp_status = subcomp_status;
	}

	public Sub_Company() {
		
	}

}

