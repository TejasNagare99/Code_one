package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="V_Fieldstaff")
public class V_Fieldstaff implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="FSTAFF_ID")
	private Long fstaff_id;
	@Column(name="FSTAFF_DIV_ID")
	private Long fstaff_div_id;
	@Column(name="FSTAFF_LOC_ID")
	private Long fstaff_loc_id;
	@Column(name="FSTAFF_STATE_ID")
	private Long fstaff_state_id;
	@Column(name="FSTAFF_SAMP_DISP_IND")
	private String fstaff_samp_disp_ind;
	
	public Long getFstaff_id() {
		return fstaff_id;
	}
	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}
	public Long getFstaff_div_id() {
		return fstaff_div_id;
	}
	public void setFstaff_div_id(Long fstaff_div_id) {
		this.fstaff_div_id = fstaff_div_id;
	}
	public Long getFstaff_loc_id() {
		return fstaff_loc_id;
	}
	public void setFstaff_loc_id(Long fstaff_loc_id) {
		this.fstaff_loc_id = fstaff_loc_id;
	}
	public Long getFstaff_state_id() {
		return fstaff_state_id;
	}
	public void setFstaff_state_id(Long fstaff_state_id) {
		this.fstaff_state_id = fstaff_state_id;
	}
	public String getFstaff_samp_disp_ind() {
		return fstaff_samp_disp_ind;
	}
	public void setFstaff_samp_disp_ind(String fstaff_samp_disp_ind) {
		this.fstaff_samp_disp_ind = fstaff_samp_disp_ind;
	}
	
}
