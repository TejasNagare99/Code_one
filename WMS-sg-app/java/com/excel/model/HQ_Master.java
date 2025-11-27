package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "HQMASTER")
public class HQ_Master {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HQ_ID")
	private Long hq_id;
	@Column(name = "HQ_CODE")
	private String hq_code;
	@Column(name = "HQ_NAME")
	private String hq_name;
	@Column(name = "HQ_CITY_ID")
	private Long hq_city_id;
	@Column(name = "HQ_STATE_ID")
	private Long hq_state_id;
	@Column(name = "HQ_MAP_CODE")
	private String hq_map_code;
	@Column(name="HQ_ZONE_ID")
	private Long hq_zone_id;
	@Column(name="HQ_STATUS")
	private String hq_status;
	@Column(name="HQ_DIV_ID")
	private Long hq_div_id;	
	@Column(name="HQ_ins_dt")
	private Date hq_ins_dt;
	@Column(name="HQ_mod_dt")
	private Date hq_mod_dt;
	@Column(name="HQ_ins_ip_add")
	private String hq_ins_ip_add;
	@Column(name="HQ_mod_ip_add")
	private String hq_mod_ip_add;
	@Column(name="HQ_ins_usr_id")
	private String hq_ins_usr_id;
	@Column(name="HQ_mod_usr_id")
	private String hq_mod_usr_id;
	@Column(name="HQ_POOL_IND")
	private String hq_pool_ind;
	@Column(name="HQ_NO_OF_REPS")
	private Long hq_no_of_reps;
	@Column(name="HQ_PMP_PERF")
	private BigDecimal hq_pmp_perf;
	@Column(name="HQ_TEAM_CODE")
	private String hq_team_code;
	
	
	public String getHq_team_code() {
		return hq_team_code;
	}
	public void setHq_team_code(String hq_team_code) {
		this.hq_team_code = hq_team_code;
	}
	public String getHq_pool_ind() {
		return hq_pool_ind;
	}
	public void setHq_pool_ind(String hq_pool_ind) {
		this.hq_pool_ind = hq_pool_ind;
	}
	public Long getHq_no_of_reps() {
		return hq_no_of_reps;
	}
	public void setHq_no_of_reps(Long hq_no_of_reps) {
		this.hq_no_of_reps = hq_no_of_reps;
	}
	
	public BigDecimal getHq_pmp_perf() {
		return hq_pmp_perf;
	}
	public void setHq_pmp_perf(BigDecimal hq_pmp_perf) {
		this.hq_pmp_perf = hq_pmp_perf;
	}
	public String getHq_ins_usr_id() {
		return hq_ins_usr_id;
	}
	public void setHq_ins_usr_id(String hq_ins_usr_id) {
		this.hq_ins_usr_id = hq_ins_usr_id;
	}
	public String getHq_mod_usr_id() {
		return hq_mod_usr_id;
	}
	public void setHq_mod_usr_id(String hq_mod_usr_id) {
		this.hq_mod_usr_id = hq_mod_usr_id;
	}
	public Date getHq_ins_dt() {
		return hq_ins_dt;
	}
	public void setHq_ins_dt(Date hq_ins_dt) {
		this.hq_ins_dt = hq_ins_dt;
	}
	public Date getHq_mod_dt() {
		return hq_mod_dt;
	}
	public void setHq_mod_dt(Date hq_mod_dt) {
		this.hq_mod_dt = hq_mod_dt;
	}
	public String getHq_ins_ip_add() {
		return hq_ins_ip_add;
	}
	public void setHq_ins_ip_add(String hq_ins_ip_add) {
		this.hq_ins_ip_add = hq_ins_ip_add;
	}
	public String getHq_mod_ip_add() {
		return hq_mod_ip_add;
	}
	public void setHq_mod_ip_add(String hq_mod_ip_add) {
		this.hq_mod_ip_add = hq_mod_ip_add;
	}
	public String getHq_status() {
		return hq_status;
	}
	public void setHq_status(String hq_status) {
		this.hq_status = hq_status;
	}
	public Long getHq_id() {
		return hq_id;
	}
	public void setHq_id(Long hq_id) {
		this.hq_id = hq_id;
	}
	public String getHq_code() {
		return hq_code;
	}
	public void setHq_code(String hq_code) {
		this.hq_code = hq_code;
	}
	public String getHq_name() {
		return hq_name;
	}
	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
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
	public String getHq_map_code() {
		return hq_map_code;
	}
	public void setHq_map_code(String hq_map_code) {
		this.hq_map_code = hq_map_code;
	}
	public Long getHq_zone_id() {
		return hq_zone_id;
	}
	public void setHq_zone_id(Long hq_zone_id) {
		this.hq_zone_id = hq_zone_id;
	}
	public Long getHq_div_id() {
		return hq_div_id;
	}
	public void setHq_div_id(Long hq_div_id) {
		this.hq_div_id = hq_div_id;
	}
	
	
}

