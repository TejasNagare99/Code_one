package com.excel.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="TEAM")
public class Team implements Serializable{
	private static final long serialVersionUID = -2141829248573637383L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TEAM_ID")
	private Long team_id;
	@Column(name="TEAM_CODE")
	private String team_code;
	@Column(name="TEAM_NAME")
	private String team_name;
	@Column(name="TEAM_SHORTNAME")
	private String team_shortname;
	@Column(name="DIV_ID")
	private Long div_id;
	@Column(name="TEAM_STATUS")
	private String team_status;
	public Long getTeam_id() {
		return team_id;
	}
	public void setTeam_id(Long team_id) {
		this.team_id = team_id;
	}
	public String getTeam_code() {
		return team_code;
	}
	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTeam_shortname() {
		return team_shortname;
	}
	public void setTeam_shortname(String team_shortname) {
		this.team_shortname = team_shortname;
	}
	public Long getDiv_id() {
		return div_id;
	}
	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}
	public String getTeam_status() {
		return team_status;
	}
	public void setTeam_status(String team_status) {
		this.team_status = team_status;
	}
	
}
