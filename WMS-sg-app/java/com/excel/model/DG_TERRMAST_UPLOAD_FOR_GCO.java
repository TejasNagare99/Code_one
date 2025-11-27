package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;



@Entity
@Table(name = "DG_TERRMAST_UPLOAD_FOR_GCO")

@NamedStoredProcedureQuery(name = "CALL_DG_TERRMAST_UPLOAD", 
procedureName = "DG_TERRMAST_UPLOAD_FOR_GCO",
parameters = {
		@StoredProcedureParameter(name = "MTERR_DIV_ID", mode = ParameterMode.IN, type = Integer.class),
		
}, resultClasses = DG_TERRMAST_UPLOAD_FOR_GCO.class)


@NamedStoredProcedureQuery(name = "CALL_DG_TERRMAST_UPLOAD_8char", 
procedureName = "DG_TERRMAST_UPLOAD_FOR_GCO_8CHR",
parameters = {
		@StoredProcedureParameter(name = "MTERR_DIV_ID", mode = ParameterMode.IN, type = Integer.class),
		
}, resultClasses = DG_TERRMAST_UPLOAD_FOR_GCO.class)

public class DG_TERRMAST_UPLOAD_FOR_GCO {

	@Id
	@Column(name = "Row")
	private Long row;
	
	@Column(name = "TERR_DIV_NAME")
	private String terr_div_name;
	
	@Column(name = "TEAM_NAME")
	private String team_name;
	
	@Column(name = "TERR_ID")
	private Long terr_id;
	
	@Column(name = "TERR_CODE")
	private String terr_code;
	
	@Column(name = "TERR_LEVEL_CODE")
	private String terr_level_code;
	
	@Column(name = "TERR_NAME")
	private String terr_name;
	
	@Column(name = "ABM_TERR_NAME")
	private String abm_terr_name;
	
	@Column(name = "RBM_TERR_NAME")
	private String rbm_terr_name;
	
	@Column(name = "ZBM_TERR_NAME")
	private String zbm_terr_name;
	
	@Column(name = "STATE_ID")
	private Long state_id;
	
	@Column(name = "STATE_NAME")
	private String state_name;
	
	@Column(name = "ZONE_NAME")
	private String zone_name;
	
	@Column(name = "HQ_NAME")
	private String hq_name;
	
	@Column(name = "CFA_PFIZER")
	private String cfa_pfizer;

	@Column(name = "CFA_PIPL")
	private String cfa_pipl;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getTerr_div_name() {
		return terr_div_name;
	}

	public void setTerr_div_name(String terr_div_name) {
		this.terr_div_name = terr_div_name;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public Long getTerr_id() {
		return terr_id;
	}

	public void setTerr_id(Long terr_id) {
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

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
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

	public String getCfa_pfizer() {
		return cfa_pfizer;
	}

	public void setCfa_pfizer(String cfa_pfizer) {
		this.cfa_pfizer = cfa_pfizer;
	}

	public String getCfa_pipl() {
		return cfa_pipl;
	}

	public void setCfa_pipl(String cfa_pipl) {
		this.cfa_pipl = cfa_pipl;
	}

	@Override
	public String toString() {
		return "DG_TERRMAST_UPLOAD_FOR_GCO [row=" + row + ", terr_div_name=" + terr_div_name + ", team_name="
				+ team_name + ", terr_id=" + terr_id + ", terr_code=" + terr_code + ", terr_level_code="
				+ terr_level_code + ", terr_name=" + terr_name + ", abm_terr_name=" + abm_terr_name + ", rbm_terr_name="
				+ rbm_terr_name + ", zbm_terr_name=" + zbm_terr_name + ", state_id=" + state_id + ", state_name="
				+ state_name + ", zone_name=" + zone_name + ", hq_name=" + hq_name + ", cfa_pfizer=" + cfa_pfizer
				+ ", cfa_pipl=" + cfa_pipl + "]";
	}
	
	
	
	
	
};
