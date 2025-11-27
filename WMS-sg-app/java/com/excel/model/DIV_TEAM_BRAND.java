package com.excel.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="DIV_TEAM_BRAND")
public class DIV_TEAM_BRAND implements Serializable{
	private static final long serialVersionUID = -2141829248573637383L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SLNO")
	private Long sl_no;
	@Column(name="TEAM_ID")
	private Long team_id;
	@Column(name="FIN_YEAR_ID")
	private String fin_year_id;
	@Column(name="COMPANY")
	private String company;

	@Column(name="SA_PROD_GROUP")
	private String sa_prod_group;
	@Column(name="SA_ins_usr_id")
	private String sa_ins_usr_id;
	@Column(name="SA_ins_dt")
	private Date sa_ins_dt;
	@Column(name="SA_mod_dt")
	private String sa_mod_dt;
	@Column(name="SA_ins_ip_add")
	private String sa_ins_ip_add;
	@Column(name="SA_status")
	private String sa_status;
	public Long getTeam_id() {
		return team_id;
	}
	public void setTeam_id(Long team_id) {
		this.team_id = team_id;
	}

	public String getFin_year_id() {
		return fin_year_id;
	}
	public void setFin_year_id(String fin_year_id) {
		this.fin_year_id = fin_year_id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}


	public Long getSl_no() {
		return sl_no;
	}
	public void setSl_no(Long sl_no) {
		this.sl_no = sl_no;
	}
	public String getSa_prod_group() {
		return sa_prod_group;
	}
	public void setSa_prod_group(String sa_prod_group) {
		this.sa_prod_group = sa_prod_group;
	}

	public String getSa_ins_usr_id() {
		return sa_ins_usr_id;
	}
	public void setSa_ins_usr_id(String sa_ins_usr_id) {
		this.sa_ins_usr_id = sa_ins_usr_id;
	}
	public Date getSa_ins_dt() {
		return sa_ins_dt;
	}
	public void setSa_ins_dt(Date sa_ins_dt) {
		this.sa_ins_dt = sa_ins_dt;
	}
	public String getSa_mod_dt() {
		return sa_mod_dt;
	}
	public void setSa_mod_dt(String sa_mod_dt) {
		this.sa_mod_dt = sa_mod_dt;
	}
	public String getSa_ins_ip_add() {
		return sa_ins_ip_add;
	}
	public void setSa_ins_ip_add(String sa_ins_ip_add) {
		this.sa_ins_ip_add = sa_ins_ip_add;
	}
	public String getSa_status() {
		return sa_status;
	}
	public void setSa_status(String sa_status) {
		this.sa_status = sa_status;
	}
	
	
}
