package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_HQMaster")
public class V_HQ_Master {
	@Id
	@Column(name = "HQ_ID")
	private Long hq_id;
	
	@Column(name = "HQ_NAME")
	private String hq_name;
	
	@Column(name = "HQ_CODE")
	private String hq_code;
	
	@Column(name = "HQ_DIV_ID")
	private Long hq_div_id;
	
	@Column(name = "HQ_CITY_ID")
	private Long hq_city_id;
	
	@Column(name = "HQ_STATE_ID")
	private Long hq_state_id;
	
	@Column(name = "HQ_POOL_IND")
	private String hq_pool_ind;
	
	@Column(name = "HQ_MAP_CODE")
	private String hq_map_code;
	
	@Column(name = "HQ_STATUS")
	private String hq_status;
	
	@Column(name = "DIV_DISP_NM")
	private String div_disp_nm;
	
	@Column(name = "SGprmdet_disp_nm")
	private String sgprmdet_disp_nm;
	
	@Column(name = "HQ_POOL_IND_DISP")
	private String hq_pool_ind_disp;

	public Long getHq_id() {
		return hq_id;
	}

	public void setHq_id(Long hq_id) {
		this.hq_id = hq_id;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}

	public String getHq_code() {
		return hq_code;
	}

	public void setHq_code(String hq_code) {
		this.hq_code = hq_code;
	}

	public Long getHq_div_id() {
		return hq_div_id;
	}

	public void setHq_div_id(Long hq_div_id) {
		this.hq_div_id = hq_div_id;
	}

	public Long getHq_city_id() {
		return hq_city_id;
	}

	public void setHq_city_id(Long hq_city_id) {
		this.hq_city_id = hq_city_id;
	}

	public Long getHq_state_id() {
		return hq_state_id;
	}

	public void setHq_state_id(Long hq_state_id) {
		this.hq_state_id = hq_state_id;
	}

	public String getHq_pool_ind() {
		return hq_pool_ind;
	}

	public void setHq_pool_ind(String hq_pool_ind) {
		this.hq_pool_ind = hq_pool_ind;
	}

	public String getHq_map_code() {
		return hq_map_code;
	}

	public void setHq_map_code(String hq_map_code) {
		this.hq_map_code = hq_map_code;
	}

	public String getHq_status() {
		return hq_status;
	}

	public void setHq_status(String hq_status) {
		this.hq_status = hq_status;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getSgprmdet_disp_nm() {
		return sgprmdet_disp_nm;
	}

	public void setSgprmdet_disp_nm(String sgprmdet_disp_nm) {
		this.sgprmdet_disp_nm = sgprmdet_disp_nm;
	}

	public String getHq_pool_ind_disp() {
		return hq_pool_ind_disp;
	}

	public void setHq_pool_ind_disp(String hq_pool_ind_disp) {
		this.hq_pool_ind_disp = hq_pool_ind_disp;
	}
	
	
}
