package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_Terr_Master")
public class V_Terr_Master {
	@Id
	@Column(name = "TERR_ID")
	private Long terr_id;
	
	@Column(name="TERR_CODE")
	private String terr_code;
	
	@Column(name="DIV_DISP_NM")
	private String div_disp_nm;
	
	@Column(name="TERR_NAME")
	private String terr_name;
	
	@Column(name="LEVEL_NAME")
	private String level_name;
	
	@Column(name="COMPANY_NAME")
	private String company_name;
	
	@Column(name="COMPANY")
	private String company;

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

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getTerr_name() {
		return terr_name;
	}

	public void setTerr_name(String terr_name) {
		this.terr_name = terr_name;
	}

	public String getLevel_name() {
		return level_name;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	
	

}
