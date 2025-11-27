package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DG_VEEVA_POS")
public class Dg_veeva_pos {

	@Id
	@Column(name = "SLNO")
	private Long slno;
	
	@Column(name = "COUNTRY_CD")
	private String country_cd;
	
	@Column(name = "SOURCE")
	private String source ;
	
	@Column(name = "SRC_SYS_ID")
	private String src_sys_id ;
	
	@Column(name = "POSN_TYPE_CD")
	private String posn_type_cd ;
	
	@Column(name = "POSN_CD")
	private String posn_cd  ;
	
	@Column(name = "POSN_NM")
	private String posn_nm  ;
	
	@Column(name = "POSN_DESC")
	private String posn_desc ;
	
	@Column(name = "POSN_DESC_SHORT")
	private String posn_desc_short ;
	
	@Column(name = "SLFC_SRC_SYS_ID")
	private String slfc_src_sys_id ;
	
	@Column(name = "SLFC_TYPE_CD")
	private String slfc_type_cd ;
	
	@Column(name = "SLFC_CD")
	private String slfc_cd ;
	
	@Column(name = "SLFC_NM")
	private String slfc_nm ;
	
	@Column(name = "BUS_UNIT_CD")
	private String bus_unit_cd ;
	
	@Column(name = "BUS_UNIT_NM")
	private String bus_unit_nm ;
	
	@Column(name = "PARENT_SRC_SYS_ID")
	private String parent_src_sys_id ;
	
	@Column(name = "HIER_TYPE")
	private String hier_type ;
	
	@Column(name = "NODE_TYPE")
	private String node_type ;
	
	@Column(name = "EFF_DATE")
	private String eff_date ;
	
	@Column(name = "END_DATE")
	private String end_date ;
	
	@Column(name = "CREATE_TS")
	private String create_ts ;
	
	@Column(name = "LAST_UPD_TS")
	private String last_upd_ts ;
	
	@Column(name = "UPDATED_BY")
	private String updated_by ;
	
	@Column(name = "ACTIVE")
	private String active  ;
	
	@Column(name = "DEL_TS")
	private String del_ts ;
	
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

	public String getPosn_type_cd() {
		return posn_type_cd;
	}

	public void setPosn_type_cd(String posn_type_cd) {
		this.posn_type_cd = posn_type_cd;
	}

	public String getPosn_cd() {
		return posn_cd;
	}

	public void setPosn_cd(String posn_cd) {
		this.posn_cd = posn_cd;
	}

	public String getPosn_nm() {
		return posn_nm;
	}

	public void setPosn_nm(String posn_nm) {
		this.posn_nm = posn_nm;
	}

	public String getPosn_desc() {
		return posn_desc;
	}

	public void setPosn_desc(String posn_desc) {
		this.posn_desc = posn_desc;
	}

	public String getPosn_desc_short() {
		return posn_desc_short;
	}

	public void setPosn_desc_short(String posn_desc_short) {
		this.posn_desc_short = posn_desc_short;
	}

	public String getSlfc_src_sys_id() {
		return slfc_src_sys_id;
	}

	public void setSlfc_src_sys_id(String slfc_src_sys_id) {
		this.slfc_src_sys_id = slfc_src_sys_id;
	}

	public String getSlfc_type_cd() {
		return slfc_type_cd;
	}

	public void setSlfc_type_cd(String slfc_type_cd) {
		this.slfc_type_cd = slfc_type_cd;
	}

	public String getSlfc_cd() {
		return slfc_cd;
	}

	public void setSlfc_cd(String slfc_cd) {
		this.slfc_cd = slfc_cd;
	}

	public String getSlfc_nm() {
		return slfc_nm;
	}

	public void setSlfc_nm(String slfc_nm) {
		this.slfc_nm = slfc_nm;
	}

	public String getBus_unit_cd() {
		return bus_unit_cd;
	}

	public void setBus_unit_cd(String bus_unit_cd) {
		this.bus_unit_cd = bus_unit_cd;
	}

	public String getBus_unit_nm() {
		return bus_unit_nm;
	}

	public void setBus_unit_nm(String bus_unit_nm) {
		this.bus_unit_nm = bus_unit_nm;
	}

	public String getParent_src_sys_id() {
		return parent_src_sys_id;
	}

	public void setParent_src_sys_id(String parent_src_sys_id) {
		this.parent_src_sys_id = parent_src_sys_id;
	}

	public String getHier_type() {
		return hier_type;
	}

	public void setHier_type(String hier_type) {
		this.hier_type = hier_type;
	}

	public String getNode_type() {
		return node_type;
	}

	public void setNode_type(String node_type) {
		this.node_type = node_type;
	}

	public String getEff_date() {
		return eff_date;
	}

	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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
	
	
}
