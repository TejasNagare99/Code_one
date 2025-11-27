package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_EMP_DIV_ACCESS")
public class V_Emp_Div_Access {
	
	@Id
	@Column(name="DIV_ID")
	private Long div_id;
	
	@Column(name="DIV_DISP_NM")
	private String div_disp_nm;
	
	@Column(name="DIV_MAP_CD")
	private String div_map_cd;
	
	@Column(name="DIV_DESC")
	private String div_desc;
	
	@Column(name="EMP_ID")
	private String empId;
	
	@Column(name="DIV_STATUS")
	private String div_status;
	
	@Column(name="TEAM_REQD_IND")
	private String team_reqd_ind;

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

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDiv_status() {
		return div_status;
	}

	public void setDiv_status(String div_status) {
		this.div_status = div_status;
	}

	public String getTeam_reqd_ind() {
		return team_reqd_ind;
	}

	public void setTeam_reqd_ind(String team_reqd_ind) {
		this.team_reqd_ind = team_reqd_ind;
	}
	
}
