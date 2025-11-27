package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "TERR_MASTER_UPLOAD_TEMPLATE")
public class TERR_MASTER_UPLOAD_TEMPLATE {
	

	
	
	@Column(name = "TERR_ID")
	private Long terr_id;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Id
	@Column(name = "TERR_CODE")
	private String terr_code;
	
	@Column(name = "TERR_DIV_ID")
	private Long terr_div_id;
	
	
    @Pattern(regexp = "^(001|002|003|004)$", message = "TERR_LEVEL_CODE must be '001', '002', '003', or '004'")
	@Column(name = "TERR_LEVEL_CODE")
	private String terr_level_code;
	
	@Column(name = "TERR_TEAM_ID")
	private Long terr_team_id;
	
	
	
	@Column(name = "TERR_STATE_ID")
	 @Min(value = 1, message = "State ID cannot be empty")
	private Long terr_state_id;
	
	
	@NotEmpty(message = "Terr_name can not be empty")
	@Column(name = "TERR_NAME")
	private String terr_name;
	
	@Column(name = "TERR_POOL_IND")
	private String terr_pool_ind;
	
	@Column(name = "TERR_HQ_ID")
	private Long terr_hq_id;
	
	@Column(name = "TERR_MGR1_ID")
	private Long terr_mgr1_id;
	
	@Column(name = "TERR_MGR2_ID")
	private Long terr_mgr2_id;
	
	
	@Column(name = "TERR_MGR3_ID")
	private Long terr_mgr3_id;
	
	@Column(name = "TERR_MGR4_ID")
	private Long terr_mgr4_id;
	
	@Column(name = "TERR_MGR5_ID")
	private Long terr_mgr5_id;
	
	@Column(name = "TERR_MGR6_ID")
	private Long terr_mgr6_id;
	
	@Column(name = "TERR_DOM_EXP")
	private String terr_dom_exp;
	
	@Column(name = "TERR_REGION_ID")
	private Long terr_region_id;
	
	@Column(name = "TERR_ZONE_ID")
	private Long terr_zone_id;
	
	@Column(name = "TERR_SBU_ID")
	private Long terr_sbu_id;
	
	@Column(name = "TERR_REGIONCD")
	private String terr_regioncd;
	
	@Column(name = "TERR_DISTRICTCD")
	private String terr_districtcd;
	
	@Column(name = "TERR_DESIGNATION")
	private String terr_designation;
	
	
	@NotEmpty(message = "TERR_CFA_LOC_PFZ can not be empty")
	@Column(name = "TERR_CFA_LOC_PFZ")
	private String terr_cfa_loc_pfz;
	
	@Column(name = "TERR_CFA_LOC2")
	private String terr_cfa_loc2;
	
	
	@NotEmpty(message = "TERR_CFA3_LOC3_PIPL can not be empty")
	@Column(name = "TERR_CFA3_LOC3_PIPL")
	private String terr_cfa3_loc3_pipl;
	
	@Column(name = "TERR_TRAINING")
	private String terr_training;
	
	@Column(name = "TERR_ins_usr_id")
	private String terr_ins_usr_id;
	
	@Column(name = "TERR_mod_usr_id")
	private String terr_mod_usr_id;
	
	@Column(name = "TERR_ins_dt")
	private Date terr_ins_dt;
	
	@Column(name = "TERR_mod_dt")
	private Date terr_mod_dt;
	
	@Column(name = "TERR_ins_ip_add")
	private String terr_ins_ip_add;
	

	
	@Column(name = "TEAM_CODE")
	private String team_code;
	
	
	@Column(name = "TERR_STATUS")
	private String terr_status;
	
	@Column(name = "CFAPFAID")
	private Long cfapfaid;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "CFAPIPLID")
	private Long cfapiplid;
	
	
	@Column(name = "FILENAME")
	private String filename;

	public Long getTerr_id() {
		return terr_id;
	}

	public void setTerr_id(Long terr_id) {
		this.terr_id = terr_id;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTerr_code() {
		return terr_code;
	}

	public void setTerr_code(String terr_code) {
		this.terr_code = terr_code;
	}

	public Long getTerr_div_id() {
		return terr_div_id;
	}

	public void setTerr_div_id(Long terr_div_id) {
		this.terr_div_id = terr_div_id;
	}

	public String getTerr_level_code() {
		return terr_level_code;
	}

	public void setTerr_level_code(String terr_level_code) {
		this.terr_level_code = terr_level_code;
	}

	public Long getTerr_team_id() {
		return terr_team_id;
	}

	public void setTerr_team_id(Long terr_team_id) {
		this.terr_team_id = terr_team_id;
	}

	public Long getTerr_state_id() {
		return terr_state_id;
	}

	public void setTerr_state_id(Long terr_state_id) {
		this.terr_state_id = terr_state_id;
	}

	public String getTerr_name() {
		return terr_name;
	}

	public void setTerr_name(String terr_name) {
		this.terr_name = terr_name;
	}

	public String getTerr_pool_ind() {
		return terr_pool_ind;
	}

	public void setTerr_pool_ind(String terr_pool_ind) {
		this.terr_pool_ind = terr_pool_ind;
	}

	public Long getTerr_hq_id() {
		return terr_hq_id;
	}

	public void setTerr_hq_id(Long terr_hq_id) {
		this.terr_hq_id = terr_hq_id;
	}

	public Long getTerr_mgr1_id() {
		return terr_mgr1_id;
	}

	public void setTerr_mgr1_id(Long terr_mgr1_id) {
		this.terr_mgr1_id = terr_mgr1_id;
	}

	public Long getTerr_mgr2_id() {
		return terr_mgr2_id;
	}

	public void setTerr_mgr2_id(Long terr_mgr2_id) {
		this.terr_mgr2_id = terr_mgr2_id;
	}

	public Long getTerr_mgr3_id() {
		return terr_mgr3_id;
	}

	public void setTerr_mgr3_id(Long terr_mgr3_id) {
		this.terr_mgr3_id = terr_mgr3_id;
	}

	public Long getTerr_mgr4_id() {
		return terr_mgr4_id;
	}

	public void setTerr_mgr4_id(Long terr_mgr4_id) {
		this.terr_mgr4_id = terr_mgr4_id;
	}

	public Long getTerr_mgr5_id() {
		return terr_mgr5_id;
	}

	public void setTerr_mgr5_id(Long terr_mgr5_id) {
		this.terr_mgr5_id = terr_mgr5_id;
	}

	public Long getTerr_mgr6_id() {
		return terr_mgr6_id;
	}

	public void setTerr_mgr6_id(Long terr_mgr6_id) {
		this.terr_mgr6_id = terr_mgr6_id;
	}

	public String getTerr_dom_exp() {
		return terr_dom_exp;
	}

	public void setTerr_dom_exp(String terr_dom_exp) {
		this.terr_dom_exp = terr_dom_exp;
	}

	public Long getTerr_region_id() {
		return terr_region_id;
	}

	public void setTerr_region_id(Long terr_region_id) {
		this.terr_region_id = terr_region_id;
	}

	public Long getTerr_zone_id() {
		return terr_zone_id;
	}

	public void setTerr_zone_id(Long terr_zone_id) {
		this.terr_zone_id = terr_zone_id;
	}

	public Long getTerr_sbu_id() {
		return terr_sbu_id;
	}

	public void setTerr_sbu_id(Long terr_sbu_id) {
		this.terr_sbu_id = terr_sbu_id;
	}

	public String getTerr_regioncd() {
		return terr_regioncd;
	}

	public void setTerr_regioncd(String terr_regioncd) {
		this.terr_regioncd = terr_regioncd;
	}

	public String getTerr_districtcd() {
		return terr_districtcd;
	}

	public void setTerr_districtcd(String terr_districtcd) {
		this.terr_districtcd = terr_districtcd;
	}

	public String getTerr_designation() {
		return terr_designation;
	}

	public void setTerr_designation(String terr_designation) {
		this.terr_designation = terr_designation;
	}

	public String getTerr_cfa_loc_pfz() {
		return terr_cfa_loc_pfz;
	}

	public void setTerr_cfa_loc_pfz(String terr_cfa_loc_pfz) {
		this.terr_cfa_loc_pfz = terr_cfa_loc_pfz;
	}

	public String getTerr_cfa_loc2() {
		return terr_cfa_loc2;
	}

	public void setTerr_cfa_loc2(String terr_cfa_loc2) {
		this.terr_cfa_loc2 = terr_cfa_loc2;
	}

	public String getTerr_cfa3_loc3_pipl() {
		return terr_cfa3_loc3_pipl;
	}

	public void setTerr_cfa3_loc3_pipl(String terr_cfa3_loc3_pipl) {
		this.terr_cfa3_loc3_pipl = terr_cfa3_loc3_pipl;
	}

	public String getTerr_training() {
		return terr_training;
	}

	public void setTerr_training(String terr_training) {
		this.terr_training = terr_training;
	}

	public String getTerr_ins_usr_id() {
		return terr_ins_usr_id;
	}

	public void setTerr_ins_usr_id(String terr_ins_usr_id) {
		this.terr_ins_usr_id = terr_ins_usr_id;
	}

	public String getTerr_mod_usr_id() {
		return terr_mod_usr_id;
	}

	public void setTerr_mod_usr_id(String terr_mod_usr_id) {
		this.terr_mod_usr_id = terr_mod_usr_id;
	}

	public Date getTerr_ins_dt() {
		return terr_ins_dt;
	}

	public void setTerr_ins_dt(Date terr_ins_dt) {
		this.terr_ins_dt = terr_ins_dt;
	}

	public Date getTerr_mod_dt() {
		return terr_mod_dt;
	}

	public void setTerr_mod_dt(Date terr_mod_dt) {
		this.terr_mod_dt = terr_mod_dt;
	}

	public String getTerr_ins_ip_add() {
		return terr_ins_ip_add;
	}

	public void setTerr_ins_ip_add(String terr_ins_ip_add) {
		this.terr_ins_ip_add = terr_ins_ip_add;
	}



	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getTerr_status() {
		return terr_status;
	}

	public void setTerr_status(String terr_status) {
		this.terr_status = terr_status;
	}

	public Long getCfapfaid() {
		return cfapfaid;
	}

	public void setCfapfaid(Long cfapfaid) {
		this.cfapfaid = cfapfaid;
	}

	public Long getCfapiplid() {
		return cfapiplid;
	}

	public void setCfapiplid(Long cfapiplid) {
		this.cfapiplid = cfapiplid;
	}

	@Override
	public String toString() {
		return "TERR_MASTER_UPLOAD_TEMPLATE [terr_id=" + terr_id + ", fin_year_id=" + fin_year_id + ", company="
				+ company + ", terr_code=" + terr_code + ", terr_div_id=" + terr_div_id + ", terr_level_code="
				+ terr_level_code + ", terr_team_id=" + terr_team_id + ", terr_state_id=" + terr_state_id
				+ ", terr_name=" + terr_name + ", terr_pool_ind=" + terr_pool_ind + ", terr_hq_id=" + terr_hq_id
				+ ", terr_mgr1_id=" + terr_mgr1_id + ", terr_mgr2_id=" + terr_mgr2_id + ", terr_mgr3_id=" + terr_mgr3_id
				+ ", terr_mgr4_id=" + terr_mgr4_id + ", terr_mgr5_id=" + terr_mgr5_id + ", terr_mgr6_id=" + terr_mgr6_id
				+ ", terr_dom_exp=" + terr_dom_exp + ", terr_region_id=" + terr_region_id + ", terr_zone_id="
				+ terr_zone_id + ", terr_sbu_id=" + terr_sbu_id + ", terr_regioncd=" + terr_regioncd
				+ ", terr_districtcd=" + terr_districtcd + ", terr_designation=" + terr_designation
				+ ", terr_cfa_loc_pfz=" + terr_cfa_loc_pfz + ", terr_cfa_loc2=" + terr_cfa_loc2
				+ ", terr_cfa3_loc3_pipl=" + terr_cfa3_loc3_pipl + ", terr_training=" + terr_training
				+ ", terr_ins_usr_id=" + terr_ins_usr_id + ", terr_mod_usr_id=" + terr_mod_usr_id + ", terr_ins_dt="
				+ terr_ins_dt + ", terr_mod_dt=" + terr_mod_dt + ", terr_ins_ip_add=" + terr_ins_ip_add
				+ ", team_code=" + team_code + ", terr_status=" + terr_status
				+ ", cfapfaid=" + cfapfaid + ", cfapiplid=" + cfapiplid + "]";
	}
	
	
	
	
	
	
	
}
