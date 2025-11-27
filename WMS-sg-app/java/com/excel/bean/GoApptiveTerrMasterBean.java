package com.excel.bean;

import java.util.List;

public class GoApptiveTerrMasterBean {

	private String terr_code;
	private Long terr_id;
	private Long fin_year_id;
	private String company;
	private Long terr_div_id;
	private String terr_level_code;
	private String terr_team_code;
	private String terr_state_code;
	private String terr_name;
	private String terr_pool_ind;
	private String terr_hq_code;
	private String terr_hq_name;
	private String terr_mgr1_terrcode;
	private String terr_mgr2_terrcode;
	private String terr_mgr3_terrcode;
	private String terr_mgr4_terrcode;
	private String terr_mgr5_terrcode;
	private String terr_mgr6_terrcode;
	private String terr_dom_exp;
	private String terr_zonecd;
	private String terr_regioncd;
	private String terr_districtcd;
	private String terr_designation;
	private String terr_cfa_loc_pfz;
	private String terr_cfa_loc_pipl;
	private String terr_training;
	private String thirdparty_div;
	private String main_mast_updated;
	private String date_time_updated;
	
	private Long stateid;
	private Long terr_cfa_loc_pfz_id;
	private Long terr_cfa_loc_pipl_id;
	private Long zoneid;
	private Long hq_id;
	private Long terr_team_id;
	
	
	public static class TerritoryResponse{
		private String TERR_CODE;
		private String status;
		//private List<GoApptiveTerrMasterBean> errors;
		public String getTERR_CODE() {
			return TERR_CODE;
		}
		public void setTERR_CODE(String tERR_CODE) {
			TERR_CODE = tERR_CODE;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
//		public List<GoApptiveTerrMasterBean> getErrors() {
//			return errors;
//		}
//		public void setErrors(List<GoApptiveTerrMasterBean> errors) {
//			this.errors = errors;
//		}
//		
		
	}
	
	public String getTerr_code() {
		return terr_code;
	}
	public void setTerr_code(String terr_code) {
		this.terr_code = terr_code;
	}
	
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
	public String getTerr_team_code() {
		return terr_team_code;
	}
	public void setTerr_team_code(String terr_team_code) {
		this.terr_team_code = terr_team_code;
	}
	public String getTerr_state_code() {
		return terr_state_code;
	}
	public void setTerr_state_code(String terr_state_code) {
		this.terr_state_code = terr_state_code;
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
	public String getTerr_hq_code() {
		return terr_hq_code;
	}
	public void setTerr_hq_code(String terr_hq_code) {
		this.terr_hq_code = terr_hq_code;
	}
	public String getTerr_hq_name() {
		return terr_hq_name;
	}
	public void setTerr_hq_name(String terr_hq_name) {
		this.terr_hq_name = terr_hq_name;
	}
	public String getTerr_mgr1_terrcode() {
		return terr_mgr1_terrcode;
	}
	public void setTerr_mgr1_terrcode(String terr_mgr1_terrcode) {
		this.terr_mgr1_terrcode = terr_mgr1_terrcode;
	}
	public String getTerr_mgr2_terrcode() {
		return terr_mgr2_terrcode;
	}
	public void setTerr_mgr2_terrcode(String terr_mgr2_terrcode) {
		this.terr_mgr2_terrcode = terr_mgr2_terrcode;
	}
	public String getTerr_mgr3_terrcode() {
		return terr_mgr3_terrcode;
	}
	public void setTerr_mgr3_terrcode(String terr_mgr3_terrcode) {
		this.terr_mgr3_terrcode = terr_mgr3_terrcode;
	}
	public String getTerr_mgr4_terrcode() {
		return terr_mgr4_terrcode;
	}
	public void setTerr_mgr4_terrcode(String terr_mgr4_terrcode) {
		this.terr_mgr4_terrcode = terr_mgr4_terrcode;
	}
	public String getTerr_mgr5_terrcode() {
		return terr_mgr5_terrcode;
	}
	public void setTerr_mgr5_terrcode(String terr_mgr5_terrcode) {
		this.terr_mgr5_terrcode = terr_mgr5_terrcode;
	}
	public String getTerr_mgr6_terrcode() {
		return terr_mgr6_terrcode;
	}
	public void setTerr_mgr6_terrcode(String terr_mgr6_terrcode) {
		this.terr_mgr6_terrcode = terr_mgr6_terrcode;
	}
	public String getTerr_dom_exp() {
		return terr_dom_exp;
	}
	public void setTerr_dom_exp(String terr_dom_exp) {
		this.terr_dom_exp = terr_dom_exp;
	}
	public String getTerr_zonecd() {
		return terr_zonecd;
	}
	public void setTerr_zonecd(String terr_zonecd) {
		this.terr_zonecd = terr_zonecd;
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
	public String getTerr_cfa_loc_pipl() {
		return terr_cfa_loc_pipl;
	}
	public void setTerr_cfa_loc_pipl(String terr_cfa_loc_pipl) {
		this.terr_cfa_loc_pipl = terr_cfa_loc_pipl;
	}
	public String getTerr_training() {
		return terr_training;
	}
	public void setTerr_training(String terr_training) {
		this.terr_training = terr_training;
	}
	public String getThirdparty_div() {
		return thirdparty_div;
	}
	public void setThirdparty_div(String thirdparty_div) {
		this.thirdparty_div = thirdparty_div;
	}
	public String getMain_mast_updated() {
		return main_mast_updated;
	}
	public void setMain_mast_updated(String main_mast_updated) {
		this.main_mast_updated = main_mast_updated;
	}
	public String getDate_time_updated() {
		return date_time_updated;
	}
	public void setDate_time_updated(String date_time_updated) {
		this.date_time_updated = date_time_updated;
	}
	public Long getStateid() {
		return stateid;
	}
	public void setStateid(Long stateid) {
		this.stateid = stateid;
	}
	public Long getTerr_cfa_loc_pfz_id() {
		return terr_cfa_loc_pfz_id;
	}
	public void setTerr_cfa_loc_pfz_id(Long terr_cfa_loc_pfz_id) {
		this.terr_cfa_loc_pfz_id = terr_cfa_loc_pfz_id;
	}
	public Long getTerr_cfa_loc_pipl_id() {
		return terr_cfa_loc_pipl_id;
	}
	public void setTerr_cfa_loc_pipl_id(Long terr_cfa_loc_pipl_id) {
		this.terr_cfa_loc_pipl_id = terr_cfa_loc_pipl_id;
	}
	public Long getZoneid() {
		return zoneid;
	}
	public void setZoneid(Long zoneid) {
		this.zoneid = zoneid;
	}
	public Long getHq_id() {
		return hq_id;
	}
	public void setHq_id(Long hq_id) {
		this.hq_id = hq_id;
	}
	public Long getTerr_team_id() {
		return terr_team_id;
	}
	public void setTerr_team_id(Long terr_team_id) {
		this.terr_team_id = terr_team_id;
	}
	
	
}
