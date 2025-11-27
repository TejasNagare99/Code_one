package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DG_VEEVA_POS_EMP")
public class Dg_veeva_pos_emp {
	
	@Id
	@Column(name = "SLNO")
	private Long slno;
	
	@Column(name = "COUNTRY_CD")
	private String country_cd;
	
	@Column(name = "SOURCE")
	private String source;
	
	@Column(name = "SRC_SYS_ID")
	private String src_sys_id;
	
	@Column(name = "POSN_SRC_SYS_ID")
	private String posn_src_sys_id;
	
	@Column(name = "EMPL_SRC_SYS_ID")
	private String empl_src_sys_id;
	
	@Column(name = "POSN_TYPE_CD")
	private String posn_type_cd;
	
	@Column(name = "POSN_TYPE_DESC")
	private String posn_type_desc;
	
	@Column(name = "EFF_DT")
	private String eff_dt;
	
	@Column(name = "END_DT")
	private Date end_dt;
	
	@Column(name = "CREATE_TS")
	private String create_ts ;
	
	@Column(name = "LAST_UPD_TS")
	private String last_upd_ts;
	
	@Column(name = "UPDATED_BY")
	private String updated_by;
	
	@Column(name = "ACTIVE")
	private String active;
	
	@Column(name = "DEL_TS")
	private String del_ts;
	
	@Column(name = "EMPL_SOURCE")
	private String empl_source;
	
	@Column(name = "PRX_ZIP_FILE_NAME")
	private String prx_zip_file_name;
	
	@Column(name = "PRX_FILE_NAME")
	private String prx_file_name;
	
	@Column(name = "DATE_TIME")
	private Date date_time;
	
	@Column(name = "DATE_TIME_RECD")
	private Date date_time_recd;

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public String getCountry_cd() {
		return country_cd;
	}

	public void setCountry_cd(String country_cd) {
		this.country_cd = country_cd;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSrc_sys_id() {
		return src_sys_id;
	}

	public void setSrc_sys_id(String src_sys_id) {
		this.src_sys_id = src_sys_id;
	}

	public String getPosn_src_sys_id() {
		return posn_src_sys_id;
	}

	public void setPosn_src_sys_id(String posn_src_sys_id) {
		this.posn_src_sys_id = posn_src_sys_id;
	}

	public String getEmpl_src_sys_id() {
		return empl_src_sys_id;
	}

	public void setEmpl_src_sys_id(String empl_src_sys_id) {
		this.empl_src_sys_id = empl_src_sys_id;
	}

	public String getPosn_type_cd() {
		return posn_type_cd;
	}

	public void setPosn_type_cd(String posn_type_cd) {
		this.posn_type_cd = posn_type_cd;
	}

	public String getPosn_type_desc() {
		return posn_type_desc;
	}

	public void setPosn_type_desc(String posn_type_desc) {
		this.posn_type_desc = posn_type_desc;
	}


	public String getEff_dt() {
		return eff_dt;
	}

	public void setEff_dt(String eff_dt) {
		this.eff_dt = eff_dt;
	}

	public Date getEnd_dt() {
		return end_dt;
	}

	public void setEnd_dt(Date end_dt) {
		this.end_dt = end_dt;
	}


	public String getCreate_ts() {
		return create_ts;
	}

	public void setCreate_ts(String create_ts) {
		this.create_ts = create_ts;
	}

	public String getLast_upd_ts() {
		return last_upd_ts;
	}

	public void setLast_upd_ts(String last_upd_ts) {
		this.last_upd_ts = last_upd_ts;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDel_ts() {
		return del_ts;
	}

	public void setDel_ts(String del_ts) {
		this.del_ts = del_ts;
	}

	public String getEmpl_source() {
		return empl_source;
	}

	public void setEmpl_source(String empl_source) {
		this.empl_source = empl_source;
	}

	public String getPrx_zip_file_name() {
		return prx_zip_file_name;
	}

	public void setPrx_zip_file_name(String prx_zip_file_name) {
		this.prx_zip_file_name = prx_zip_file_name;
	}

	public String getPrx_file_name() {
		return prx_file_name;
	}

	public void setPrx_file_name(String prx_file_name) {
		this.prx_file_name = prx_file_name;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public Date getDate_time_recd() {
		return date_time_recd;
	}

	public void setDate_time_recd(Date date_time_recd) {
		this.date_time_recd = date_time_recd;
	}
	
	


}
