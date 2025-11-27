package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DIV_MASTER")
public class DivMaster {
	@Id
	@Column(name="DIV_ID")
	public Long div_id;

	@Column(name="DIV_DISP_NM")
	public String div_disp_nm;

	@Column(name="DIV_MAP_CD")
	public String div_map_cd;

	@Column(name="DIV_desc")
	public String div_desc;

	@Column(name="DIV_ins_usr_id")
	public String div_ins_usr_id;

	@Column(name="DIV_mod_usr_id")
	public String div_mod_usr_id;

	@Column(name="DIV_ins_dt")
	public Date div_ins_dt;

	@Column(name="DIV_mod_dt")
	public Date div_mod_dt;

	@Column(name="DIV_ins_ip_add")
	public String div_ins_ip_add;

	@Column(name="DIV_mod_ip_add")
	public String div_mod_ip_add;

	@Column(name="DIV_status")
	public String div_status;

	@Column(name="DIV_CODE")
	public String div_code;

	@Column(name="DIV_CODE_NM")
	public String div_code_nm;

	@Column(name="DIV_SHORT_NM")
	public String div_short_nm;
	
	@Column(name="TEAM_REQD_IND")
	public String team_reqd_ind;
	
	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getDiv_map_cd() {
		return div_map_cd;
	}

	public void setDiv_map_cd(String div_map_cd) {
		this.div_map_cd = div_map_cd;
	}

	public String getDiv_desc() {
		return div_desc;
	}

	public void setDiv_desc(String div_desc) {
		this.div_desc = div_desc;
	}

	public String getDiv_ins_usr_id() {
		return div_ins_usr_id;
	}

	public void setDiv_ins_usr_id(String div_ins_usr_id) {
		this.div_ins_usr_id = div_ins_usr_id;
	}

	public String getDiv_mod_usr_id() {
		return div_mod_usr_id;
	}

	public void setDiv_mod_usr_id(String div_mod_usr_id) {
		this.div_mod_usr_id = div_mod_usr_id;
	}

	public Date getDiv_ins_dt() {
		return div_ins_dt;
	}

	public void setDiv_ins_dt(Date div_ins_dt) {
		this.div_ins_dt = div_ins_dt;
	}

	public Date getDiv_mod_dt() {
		return div_mod_dt;
	}

	public void setDiv_mod_dt(Date div_mod_dt) {
		this.div_mod_dt = div_mod_dt;
	}

	public String getDiv_ins_ip_add() {
		return div_ins_ip_add;
	}

	public void setDiv_ins_ip_add(String div_ins_ip_add) {
		this.div_ins_ip_add = div_ins_ip_add;
	}

	public String getDiv_mod_ip_add() {
		return div_mod_ip_add;
	}

	public void setDiv_mod_ip_add(String div_mod_ip_add) {
		this.div_mod_ip_add = div_mod_ip_add;
	}

	public String getDiv_status() {
		return div_status;
	}

	public void setDiv_status(String div_status) {
		this.div_status = div_status;
	}

	public String getDiv_code() {
		return div_code;
	}

	public void setDiv_code(String div_code) {
		this.div_code = div_code;
	}

	public String getDiv_code_nm() {
		return div_code_nm;
	}

	public void setDiv_code_nm(String div_code_nm) {
		this.div_code_nm = div_code_nm;
	}
	

	public DivMaster() {
		
	}
	public DivMaster(Long div_id, String div_disp_nm) {
		super();
		this.div_id = div_id;
		this.div_disp_nm = div_disp_nm;
	}
	
	public DivMaster( String div_disp_nm) {
		super();
		this.div_disp_nm = div_disp_nm;
	}
	public DivMaster(Long div_id) {
		super();
		this.div_id = div_id;
	}

	public String getDiv_short_nm() {
		return div_short_nm;
	}

	public void setDiv_short_nm(String div_short_nm) {
		this.div_short_nm = div_short_nm;
	}

	public String getTeam_reqd_ind() {
		return team_reqd_ind;
	}

	public void setTeam_reqd_ind(String team_reqd_ind) {
		this.team_reqd_ind = team_reqd_ind;
	}

}
