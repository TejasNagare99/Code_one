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
@Table(name = "SFA_FIELD_MASTER")
public class Sfa_Field_Master implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROW_ID")
	private Long row_id;
	
	@Column(name = "FS_ID")
	private Long fs_id;

	@Column(name = "FS_CODE")
	private String fs_code;

	@Column(name = "LEVEL_CODE")
	private String level_code;

	@Column(name = "DIV_ID")
	private Long div_id;

	@Column(name = "POOL_IND")
	private String pool_ind;

	@Column(name = "FS_NAME")
	private String fs_name;

	@Column(name = "FS_NAME2")
	private String fs_name2;

	@Column(name = "BRIEF_NAME")
	private String brief_name;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "ADDRESS3")
	private String address3;

	@Column(name = "ADDRESS4")
	private String address4;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "STATE_ID")
	private Long state_id;

	@Column(name = "SAMP_SUP_LOC")
	private Long samp_sup_loc;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "JOINING_DATE")
	private Date joining_date;

	@Column(name = "LEAVING_DATE")
	private Date leaving_date;

	@Column(name = "EMP_CODE")
	private String emp_code;

	@Column(name = "TRANSPORTER_ID")
	private Long transporter_id;

	@Column(name = "DIV_OFF_CITY")
	private String div_off_city;

	@Column(name = "SAMP_DISP_IND")
	private String samp_disp_ind;

	@Column(name = "CLASS_ID")
	private Long class_id;

	@Column(name = "TYPE_ID")
	private Long type_id;

	@Column(name = "DOM_EXP")
	private String dom_exp;

	@Column(name = "MGR_LEVEL1")
	private Long mgr_level1;

	@Column(name = "MGR_LEVEL2")
	private Long mgr_level2;

	@Column(name = "MGR_LEVEL3")
	private Long mgr_level3;

	@Column(name = "MGR_LEVEL4")
	private Long mgr_level4;

	@Column(name = "GEOG_LVL1_HQ")
	private Long geog_lvl1_hq;

	@Column(name = "GEOG_LEVEL2")
	private Long geog_level2;

	@Column(name = "GEOG_LEVEL3")
	private Long geog_level3;

	@Column(name = "GEOG_LEVEL4")
	private Long geog_level4;

	@Column(name = "COMPANY_CD")
	private String company_cd;

	@Column(name = "DELETED")
	private String deleted;

	@Column(name = "MGR_LEVEL5")
	private Long mgr_level5;

	@Column(name = "GEOG_LEVEL5")
	private Long geog_level5;

	@Column(name = "TRANSPORTER")
	private String transporter;

	@Column(name = "ZONE_ID")
	private Long zone_id;

	@Column(name = "CITY_ID")
	private Long city_id;

	@Column(name = "FARE_TYPE")
	private String fare_type;

	@Column(name = "LEAVE_ELIG_FROM")
	private Date leave_elig_from;
	
	@Column(name = "NO_OF_DOC")
	private Long no_of_doctors;
	
	@Column(name = "SYS_FS_CODE")
	private String sys_fs_code;
	
	@Column(name = "MAP_CODE")
	private String map_code;
	
	@Column(name = "LAST_MODE_DT")
	private Date last_mode_date;
	
	@Column(name = "TEAM")
	private String team;
	
	@Column(name = "POOL_FS_CODE")
	private String pool_fs_code;
	
	@Column(name="DATE_OF_BIRTH")
	private Date date_of_birth;

	public Long getRow_id() {
		return row_id;
	}

	public void setRow_id(Long row_id) {
		this.row_id = row_id;
	}

	public Long getFs_id() {
		return fs_id;
	}

	public void setFs_id(Long fs_id) {
		this.fs_id = fs_id;
	}

	public String getFs_code() {
		return fs_code;
	}

	public void setFs_code(String fs_code) {
		this.fs_code = fs_code;
	}

	public String getLevel_code() {
		return level_code;
	}

	public void setLevel_code(String level_code) {
		this.level_code = level_code;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getPool_ind() {
		return pool_ind;
	}

	public void setPool_ind(String pool_ind) {
		this.pool_ind = pool_ind;
	}

	public String getFs_name() {
		return fs_name;
	}

	public void setFs_name(String fs_name) {
		this.fs_name = fs_name;
	}

	public String getFs_name2() {
		return fs_name2;
	}

	public void setFs_name2(String fs_name2) {
		this.fs_name2 = fs_name2;
	}

	public String getBrief_name() {
		return brief_name;
	}

	public void setBrief_name(String brief_name) {
		this.brief_name = brief_name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public Long getSamp_sup_loc() {
		return samp_sup_loc;
	}

	public void setSamp_sup_loc(Long samp_sup_loc) {
		this.samp_sup_loc = samp_sup_loc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getJoining_date() {
		return joining_date;
	}

	public void setJoining_date(Date joining_date) {
		this.joining_date = joining_date;
	}

	public Date getLeaving_date() {
		return leaving_date;
	}

	public void setLeaving_date(Date leaving_date) {
		this.leaving_date = leaving_date;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public Long getTransporter_id() {
		return transporter_id;
	}

	public void setTransporter_id(Long transporter_id) {
		this.transporter_id = transporter_id;
	}

	public String getDiv_off_city() {
		return div_off_city;
	}

	public void setDiv_off_city(String div_off_city) {
		this.div_off_city = div_off_city;
	}

	public String getSamp_disp_ind() {
		return samp_disp_ind;
	}

	public void setSamp_disp_ind(String samp_disp_ind) {
		this.samp_disp_ind = samp_disp_ind;
	}

	public Long getClass_id() {
		return class_id;
	}

	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}

	public Long getType_id() {
		return type_id;
	}

	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}

	public String getDom_exp() {
		return dom_exp;
	}

	public void setDom_exp(String dom_exp) {
		this.dom_exp = dom_exp;
	}

	public Long getMgr_level1() {
		return mgr_level1;
	}

	public void setMgr_level1(Long mgr_level1) {
		this.mgr_level1 = mgr_level1;
	}

	public Long getMgr_level2() {
		return mgr_level2;
	}

	public void setMgr_level2(Long mgr_level2) {
		this.mgr_level2 = mgr_level2;
	}

	public Long getMgr_level3() {
		return mgr_level3;
	}

	public void setMgr_level3(Long mgr_level3) {
		this.mgr_level3 = mgr_level3;
	}

	public Long getMgr_level4() {
		return mgr_level4;
	}

	public void setMgr_level4(Long mgr_level4) {
		this.mgr_level4 = mgr_level4;
	}

	public Long getGeog_lvl1_hq() {
		return geog_lvl1_hq;
	}

	public void setGeog_lvl1_hq(Long geog_lvl1_hq) {
		this.geog_lvl1_hq = geog_lvl1_hq;
	}

	public Long getGeog_level2() {
		return geog_level2;
	}

	public void setGeog_level2(Long geog_level2) {
		this.geog_level2 = geog_level2;
	}

	public Long getGeog_level3() {
		return geog_level3;
	}

	public void setGeog_level3(Long geog_level3) {
		this.geog_level3 = geog_level3;
	}

	public Long getGeog_level4() {
		return geog_level4;
	}

	public void setGeog_level4(Long geog_level4) {
		this.geog_level4 = geog_level4;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Long getMgr_level5() {
		return mgr_level5;
	}

	public void setMgr_level5(Long mgr_level5) {
		this.mgr_level5 = mgr_level5;
	}

	public Long getGeog_level5() {
		return geog_level5;
	}

	public void setGeog_level5(Long geog_level5) {
		this.geog_level5 = geog_level5;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public Long getZone_id() {
		return zone_id;
	}

	public void setZone_id(Long zone_id) {
		this.zone_id = zone_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public String getFare_type() {
		return fare_type;
	}

	public void setFare_type(String fare_type) {
		this.fare_type = fare_type;
	}

	public Date getLeave_elig_from() {
		return leave_elig_from;
	}

	public void setLeave_elig_from(Date leave_elig_from) {
		this.leave_elig_from = leave_elig_from;
	}

	public Long getNo_of_doctors() {
		return no_of_doctors;
	}

	public void setNo_of_doctors(Long no_of_doctors) {
		this.no_of_doctors = no_of_doctors;
	}

	public String getSys_fs_code() {
		return sys_fs_code;
	}

	public void setSys_fs_code(String sys_fs_code) {
		this.sys_fs_code = sys_fs_code;
	}

	public String getMap_code() {
		return map_code;
	}

	public void setMap_code(String map_code) {
		this.map_code = map_code;
	}

	public Date getLast_mode_date() {
		return last_mode_date;
	}

	public void setLast_mode_date(Date last_mode_date) {
		this.last_mode_date = last_mode_date;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPool_fs_code() {
		return pool_fs_code;
	}

	public void setPool_fs_code(String pool_fs_code) {
		this.pool_fs_code = pool_fs_code;
	}

	public Date getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Sfa_Field_Master(Long fs_id, String fs_code, String fs_name, Long geog_lvl1_hq) {
		super();
		this.fs_id = fs_id;
		this.fs_code = fs_code;
		this.fs_name = fs_name;
		this.geog_lvl1_hq = geog_lvl1_hq;
	}

	public Sfa_Field_Master() {
		super();
	}
	
	
}
