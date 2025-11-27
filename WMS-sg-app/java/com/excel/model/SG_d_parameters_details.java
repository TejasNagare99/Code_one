package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SG_d_parameters_details")
public class SG_d_parameters_details {
	
	@Id
	@Column(name="SGprmdet_id")
	private Long sg_prmdet_id;
	
	@Column(name="SGprmdet_SGprm_id")
	private Long sgprmdet_sgprm_id;
	
	@Column(name="SGprmdet_nm")
	private String sgprmdet_nm;
	
	@Column(name="SGprmdet_disp_nm")
	private String sgprmdet_disp_nm;
	
	@Column(name="SGprmdet_short_nm")
	private String sgprmdet_short_nm;
	
	@Column(name="SGprmdet_status")
	private String sgprmdet_status;
	
	@Column(name="SGprmdet_Text1")
	private String sgprmdet_text1;
	
	@Column(name="SGprmdet_Text2")
	private String sgprmdet_text2;
	
	@Column(name="SGprmdet_Num1")
	private Long sgprmdet_num1;

	public Long getSg_prmdet_id() {
		return sg_prmdet_id;
	}

	public void setSg_prmdet_id(Long sg_prmdet_id) {
		this.sg_prmdet_id = sg_prmdet_id;
	}

	public Long getSgprmdet_sgprm_id() {
		return sgprmdet_sgprm_id;
	}

	public void setSgprmdet_sgprm_id(Long sgprmdet_sgprm_id) {
		this.sgprmdet_sgprm_id = sgprmdet_sgprm_id;
	}

	public String getSgprmdet_nm() {
		return sgprmdet_nm;
	}

	public void setSgprmdet_nm(String sgprmdet_nm) {
		this.sgprmdet_nm = sgprmdet_nm;
	}

	public String getSgprmdet_disp_nm() {
		return sgprmdet_disp_nm;
	}

	public void setSgprmdet_disp_nm(String sgprmdet_disp_nm) {
		this.sgprmdet_disp_nm = sgprmdet_disp_nm;
	}


	public String getSgprmdet_short_nm() {
		return sgprmdet_short_nm;
	}

	public void setSgprmdet_short_nm(String sgprmdet_short_nm) {
		this.sgprmdet_short_nm = sgprmdet_short_nm;
	}

	public String getSgprmdet_status() {
		return sgprmdet_status;
	}

	public void setSgprmdet_status(String sgprmdet_status) {
		this.sgprmdet_status = sgprmdet_status;
	}

	public String getSgprmdet_text1() {
		return sgprmdet_text1;
	}

	public void setSgprmdet_text1(String sgprmdet_text1) {
		this.sgprmdet_text1 = sgprmdet_text1;
	}

	public String getSgprmdet_text2() {
		return sgprmdet_text2;
	}

	public void setSgprmdet_text2(String sgprmdet_text2) {
		this.sgprmdet_text2 = sgprmdet_text2;
	}

	public Long getSgprmdet_num1() {
		return sgprmdet_num1;
	}

	public void setSgprmdet_num1(Long sgprmdet_num1) {
		this.sgprmdet_num1 = sgprmdet_num1;
	}
}
