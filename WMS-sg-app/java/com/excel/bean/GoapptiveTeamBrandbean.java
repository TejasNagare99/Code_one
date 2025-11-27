package com.excel.bean;

import java.util.List;

public class GoapptiveTeamBrandbean {
	
	private Long fin_year_id;
	private String company;
	private Long divid;
	private String team_code;
	private String teamid;
	private String sa_prod_groupname;
	private Long slno;
	private String tt_div_team_brand;
	
	public static class TeamBrandResponse{
		private String SLNO;
		private String status;
		private List<GoapptiveTeamBrandbean> errors;
		
		public List<GoapptiveTeamBrandbean> getErrors() {
			return errors;
		}
		public void setErrors(List<GoapptiveTeamBrandbean> errors) {
			this.errors = errors;
		}
		public String getSLNO() {
			return SLNO;
		}
		public void setSLNO(String sLNO) {
			SLNO = sLNO;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
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
	public Long getDivid() {
		return divid;
	}
	public void setDivid(Long divid) {
		this.divid = divid;
	}
	public String getTeamid() {
		return teamid;
	}
	public void setTeamid(String teamid) {
		this.teamid = teamid;
	}
	public String getSa_prod_groupname() {
		return sa_prod_groupname;
	}
	public void setSa_prod_groupname(String sa_prod_groupname) {
		this.sa_prod_groupname = sa_prod_groupname;
	}
	public Long getSlno() {
		return slno;
	}
	public void setSlno(Long slno) {
		this.slno = slno;
	}
	public String getTt_div_team_brand() {
		return tt_div_team_brand;
	}
	public void setTt_div_team_brand(String tt_div_team_brand) {
		this.tt_div_team_brand = tt_div_team_brand;
	}
	public String getTeam_code() {
		return team_code;
	}
	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}
	
	
	
}
