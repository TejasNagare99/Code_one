package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SFA_GEOGRAPHICAL_MASTER")
public class Sfa_Geographical_Master implements Serializable{
	
	private static final long serialVersionUID = 4249203119726683312L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	public Long row_id;

	@Column(name="LOC_ID")
	public Long loc_id;
	
	@Column(name = "LOC_TYPE")
	private String loc_type;

	@Column(name = "LOC_CODE")
	private String loc_code;

	@Column(name = "LOC_NAME")
	private String loc_name;

	@Column(name = "DIV_ID")
	private Long div_id;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "STATE_ID")
	private Long state_id;

	@Column(name = "BRIEF_NAME")
	private String brief_name;

	@Column(name = "DELETED")
	private String deleted;
	
	@Column(name = "ZONE_ID")
	private Long zone_id;
	
	@Column(name = "NO_OF_REPS")
	private Long no_of_reps;
	
	@Column(name="COMPANY_CD")
	private String company_cd;
	
	@Column(name = "MAP_CODE")
	private Long map_code;
	
	@Column(name = "CITY_ID")
	private Long city_id;
	
	@Column(name = "LAST_MOD_DT")
	private Date last_mod_dt;
	
	@Column(name = "MIN_ANN_GROWTH")
	private BigDecimal min_ann_growth;
	
	@Column(name = "MIN_MTH_GROWTH")
	private BigDecimal min_mth_growth;

	public Long getRow_id() {
		return row_id;
	}

	public void setRow_id(Long row_id) {
		this.row_id = row_id;
	}

	public String getLoc_type() {
		return loc_type;
	}

	public void setLoc_type(String loc_type) {
		this.loc_type = loc_type;
	}

	public String getLoc_code() {
		return loc_code;
	}

	public void setLoc_code(String loc_code) {
		this.loc_code = loc_code;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public String getBrief_name() {
		return brief_name;
	}

	public void setBrief_name(String brief_name) {
		this.brief_name = brief_name;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Long getZone_id() {
		return zone_id;
	}

	public void setZone_id(Long zone_id) {
		this.zone_id = zone_id;
	}

	public Long getNo_of_reps() {
		return no_of_reps;
	}

	public void setNo_of_reps(Long no_of_reps) {
		this.no_of_reps = no_of_reps;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public Long getMap_code() {
		return map_code;
	}

	public void setMap_code(Long map_code) {
		this.map_code = map_code;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Date getLast_mod_dt() {
		return last_mod_dt;
	}

	public void setLast_mod_dt(Date last_mod_dt) {
		this.last_mod_dt = last_mod_dt;
	}

	public BigDecimal getMin_ann_growth() {
		return min_ann_growth;
	}

	public void setMin_ann_growth(BigDecimal min_ann_growth) {
		this.min_ann_growth = min_ann_growth;
	}

	public BigDecimal getMin_mth_growth() {
		return min_mth_growth;
	}

	public void setMin_mth_growth(BigDecimal min_mth_growth) {
		this.min_mth_growth = min_mth_growth;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}
	
	
}
