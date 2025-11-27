package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;


@NamedStoredProcedureQuery(name = "dg_terr_master_hierarchy_list", procedureName = "DG_TERR_MASTER_HIERARCHY_LIST", parameters = {
		@StoredProcedureParameter(name = "M_DIV_ID", mode = ParameterMode.IN, type = String.class),
		 }, resultClasses = Ter_mst_hierarchy_report_bean.class)

@Entity
@Table(name="DG_TERR_MASTER_HIERARCHY_LIST")
public class Ter_mst_hierarchy_report_bean {

	@Column(name = "TERR_DIV_NAME")
	private String terr_div_name;
	

	
	@Id
	@Column(name = "TERR_ID")
	private String terr_id;
	
	@Column(name = "TERR_CODE")
	private String terr_code;
	
	@Column(name = "TERR_LEVEL_CODE")
	private String terr_level_code;
	
	
	@Column(name = "LEVEL_NAME")
	private String level_name;
	
	
	public String getLevel_name() {
		return level_name;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	@Column(name = "TERR_NAME")
	private String terr_name;
	
	@Column(name = "ABM_TERR_NAME")
	private String abm_terr_name;
	
	@Column(name = "RBM_TERR_NAME")
	private String rbm_terr_name;
	
	@Column(name = "ZBM_TERR_NAME")
	private String zbm_terr_name;
	
	@Column(name = "STATE_ID")
	private String state_id;
	
	@Column(name = "STATE_NAME")
	private String state_name;
	
	@Column(name = "ZONE_NAME")
	private String zone_name;
	
	@Column(name = "HQ_NAME")
	private String hq_name;
	
	@Column(name = "CFA_1")
	private String cfa_1;
	
	@Column(name = "CFA_2")
	private String cfa_2;

	public String getTerr_div_name() {
		return terr_div_name;
	}

	public void setTerr_div_name(String team_code) {
		this.terr_div_name = team_code;
	}

	public String getTerr_id() {
		return terr_id;
	}

	public void setTerr_id(String terr_id) {
		this.terr_id = terr_id;
	}

	public String getTerr_code() {
		return terr_code;
	}

	public void setTerr_code(String terr_code) {
		this.terr_code = terr_code;
	}

	public String getTerr_level_code() {
		return terr_level_code;
	}

	public void setTerr_level_code(String terr_level_code) {
		this.terr_level_code = terr_level_code;
	}

	public String getTerr_name() {
		return terr_name;
	}

	public void setTerr_name(String terr_name) {
		this.terr_name = terr_name;
	}

	public String getAbm_terr_name() {
		return abm_terr_name;
	}

	public void setAbm_terr_name(String abm_terr_name) {
		this.abm_terr_name = abm_terr_name;
	}

	public String getRbm_terr_name() {
		return rbm_terr_name;
	}

	public void setRbm_terr_name(String rbm_terr_name) {
		this.rbm_terr_name = rbm_terr_name;
	}

	public String getZbm_terr_name() {
		return zbm_terr_name;
	}

	public void setZbm_terr_name(String zbm_terr_name) {
		this.zbm_terr_name = zbm_terr_name;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getZone_name() {
		return zone_name;
	}

	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}

	public String getCfa_1() {
		return cfa_1;
	}

	public void setCfa_1(String cfa_1) {
		this.cfa_1 = cfa_1;
	}

	public String getCfa_2() {
		return cfa_2;
	}

	public void setCfa_2(String cfa_2) {
		this.cfa_2 = cfa_2;
	}
	
	
}
