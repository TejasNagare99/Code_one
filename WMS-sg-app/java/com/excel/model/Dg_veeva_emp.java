package com.excel.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DG_VEEVA_EMP")
public class Dg_veeva_emp {
	
	@Id
	@Column(name="SLNO")
	private Long slno;
	
	@Column(name="COUNTRY_CD")
	private String country_cd;
	
	@Column(name="SOURCE")
	private String source;
	
	@Column(name="SRC_SYS_ID")
	private String src_sys_id;
	
	@Column(name="PFZ_EMPL_ID")
	private String pfz_empl_id;
	
	@Column(name="LOGIN_ID")
	private String login_id;
	
	@Column(name="OTHR_ID")
	private String othr_id;
	
	@Column(name="OTHR_ID_NAME")
	private String othr_id_name;
	
	@Column(name="LOGIN_DOMAIN_NM")
	private String login_domain_nm;
	
	@Column(name="CRM_ROLE")
	private String crm_role;
	
	@Column(name="CRM_PROFILE")
	private String crm_profile;
	
	@Column(name="FRST_NM")
	private String frst_nm;
	
	@Column(name="FRST_NM_2")
	private String frst_nm_2;
	
	@Column(name="MDL_NM")
	private String mdl_nm;
	
	@Column(name="LAST_NM")
	private String last_nm;
	
	@Column(name="LAST_NM_2")
	private String last_nm_2;
	
	@Column(name="MAIDEN_NM")
	private String maiden_nm;
	
	@Column(name="MAIDEN_NM_2")
	private String maiden_nm_2;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="EMPLOY_TYPE")
	private String employ_type;
	
	@Column(name="JOB_TITLE")
	private String job_title;
	
	@Column(name="PFZ_EMPL_FLG")
	private String pfz_empl_flg;
	
	@Column(name="BKNG_AGNT")
	private String bkng_agnt;
	
	@Column(name="PART_TIME")
	private String part_time;
	
	@Column(name="AVG_HRS_PER_DAY")
	private String avg_hrs_per_day;
	
	@Column(name="EFF_DT")
	private String eff_dt;
	
	@Column(name="END_DT")
	private String end_dt;
	
	@Column(name="ADDR_LBL")
	private String addr_lbl;
	
	@Column(name="ADDR_LBL_2")
	private String addr_lbl_2;
	
	@Column(name="ADDR_LBL_LONG")
	private String addr_lbl_long;
	
	@Column(name="ADDR_LBL_LONG_2")
	private String addr_lbl_long_2;
	
	@Column(name="ADDR_EXT_LBL")
	private String addr_ext_lbl;
	
	@Column(name="COUNTRY_LBL")
	private String country_lbl;
	
	@Column(name="AREA_LBL")
	private String area_lbl;
	
	@Column(name="LG_POST_CD")
	private String lg_post_cd;
	
	@Column(name="DISTRICT_CD")
	private String district_cd;
	
	@Column(name="POSTAL_CITY")
	private String postal_city;
	
	@Column(name="POSTAL_CITY_2")
	private String postal_city_2;
	
	@Column(name="INSTREET_NUM")
	private String instreet_num;
	
	@Column(name="REGION_CD")
	private String region_cd;
	
	@Column(name="COUNTY_CD")
	private String county_cd;
	
	@Column(name="CANTON_CD")
	private String canton_cd;
	
	@Column(name="CITY_CD")
	private String city_cd;
	
	@Column(name="REGION")
	private String region;
	
	@Column(name="COUNTY")
	private String county;
	
	@Column(name="CANTON")
	private String canton;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="CITY_2")
	private String city_2;
	
	@Column(name="WORK_PHONE")
	private String work_phone;
	
	@Column(name="MOBILE_PHONE")
	private String mobile_phone;
	
	@Column(name="PAGER")
	private String pager;
	
	@Column(name="FAX")
	private String fax;
	
	@Column(name="HOME_PHONE")
	private String home_phone;
	
	@Column(name="VOICE_MAIL")
	private String voice_mail;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="TIME_ZONE")
	private String time_zone;
	
	@Column(name="LANG_CD")
	private String lang_cd;
	
	@Column(name="CREATE_TS")
	private String create_ts;
	
	@Column(name="LAST_UPD_TS")
	private String last_upd_ts;
	
	@Column(name="UPDATED_BY")
	private String updated_by;
	
	@Column(name="ACTIVE")
	private String active;
	
	@Column(name="DEL_TS")
	private String del_ts;
	
	@Column(name="TERMINATION_DATE")
	private String termination_date;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="EMPLOYMENT_TYPE_CODE")
	private String employment_type_code;
	
	@Column(name="PRX_ZIP_FILE_NAME")
	private String prx_zip_file_name;
	
	@Column(name="PRX_FILE_NAME")
	private String prx_file_name;
	
	@Column(name="DATE_TIME")
	private Date date_time;
	
	@Column(name="DATE_TIME_RECD")
	private Date date_time_recd;

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

	public String getPfz_empl_id() {
		return pfz_empl_id;
	}

	public void setPfz_empl_id(String pfz_empl_id) {
		this.pfz_empl_id = pfz_empl_id;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getOthr_id() {
		return othr_id;
	}

	public void setOthr_id(String othr_id) {
		this.othr_id = othr_id;
	}

	public String getOthr_id_name() {
		return othr_id_name;
	}

	public void setOthr_id_name(String othr_id_name) {
		this.othr_id_name = othr_id_name;
	}

	public String getLogin_domain_nm() {
		return login_domain_nm;
	}

	public void setLogin_domain_nm(String login_domain_nm) {
		this.login_domain_nm = login_domain_nm;
	}

	public String getCrm_role() {
		return crm_role;
	}

	public void setCrm_role(String crm_role) {
		this.crm_role = crm_role;
	}

	public String getCrm_profile() {
		return crm_profile;
	}

	public void setCrm_profile(String crm_profile) {
		this.crm_profile = crm_profile;
	}

	public String getFrst_nm() {
		return frst_nm;
	}

	public void setFrst_nm(String frst_nm) {
		this.frst_nm = frst_nm;
	}

	public String getFrst_nm_2() {
		return frst_nm_2;
	}

	public void setFrst_nm_2(String frst_nm_2) {
		this.frst_nm_2 = frst_nm_2;
	}

	public String getMdl_nm() {
		return mdl_nm;
	}

	public void setMdl_nm(String mdl_nm) {
		this.mdl_nm = mdl_nm;
	}

	public String getLast_nm() {
		return last_nm;
	}

	public void setLast_nm(String last_nm) {
		this.last_nm = last_nm;
	}

	public String getLast_nm_2() {
		return last_nm_2;
	}

	public void setLast_nm_2(String last_nm_2) {
		this.last_nm_2 = last_nm_2;
	}

	public String getMaiden_nm() {
		return maiden_nm;
	}

	public void setMaiden_nm(String maiden_nm) {
		this.maiden_nm = maiden_nm;
	}

	public String getMaiden_nm_2() {
		return maiden_nm_2;
	}

	public void setMaiden_nm_2(String maiden_nm_2) {
		this.maiden_nm_2 = maiden_nm_2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmploy_type() {
		return employ_type;
	}

	public void setEmploy_type(String employ_type) {
		this.employ_type = employ_type;
	}

	public String getJob_title() {
		return job_title;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public String getPfz_empl_flg() {
		return pfz_empl_flg;
	}

	public void setPfz_empl_flg(String pfz_empl_flg) {
		this.pfz_empl_flg = pfz_empl_flg;
	}

	public String getBkng_agnt() {
		return bkng_agnt;
	}

	public void setBkng_agnt(String bkng_agnt) {
		this.bkng_agnt = bkng_agnt;
	}

	public String getPart_time() {
		return part_time;
	}

	public void setPart_time(String part_time) {
		this.part_time = part_time;
	}

	public String getAvg_hrs_per_day() {
		return avg_hrs_per_day;
	}

	public void setAvg_hrs_per_day(String avg_hrs_per_day) {
		this.avg_hrs_per_day = avg_hrs_per_day;
	}

	public String getEff_dt() {
		return eff_dt;
	}

	public void setEff_dt(String eff_dt) {
		this.eff_dt = eff_dt;
	}

	public String getEnd_dt() {
		return end_dt;
	}

	public void setEnd_dt(String end_dt) {
		this.end_dt = end_dt;
	}

	public String getAddr_lbl() {
		return addr_lbl;
	}

	public void setAddr_lbl(String addr_lbl) {
		this.addr_lbl = addr_lbl;
	}

	public String getAddr_lbl_2() {
		return addr_lbl_2;
	}

	public void setAddr_lbl_2(String addr_lbl_2) {
		this.addr_lbl_2 = addr_lbl_2;
	}

	public String getAddr_lbl_long() {
		return addr_lbl_long;
	}

	public void setAddr_lbl_long(String addr_lbl_long) {
		this.addr_lbl_long = addr_lbl_long;
	}

	public String getAddr_lbl_long_2() {
		return addr_lbl_long_2;
	}

	public void setAddr_lbl_long_2(String addr_lbl_long_2) {
		this.addr_lbl_long_2 = addr_lbl_long_2;
	}

	public String getAddr_ext_lbl() {
		return addr_ext_lbl;
	}

	public void setAddr_ext_lbl(String addr_ext_lbl) {
		this.addr_ext_lbl = addr_ext_lbl;
	}

	public String getCountry_lbl() {
		return country_lbl;
	}

	public void setCountry_lbl(String country_lbl) {
		this.country_lbl = country_lbl;
	}

	public String getArea_lbl() {
		return area_lbl;
	}

	public void setArea_lbl(String area_lbl) {
		this.area_lbl = area_lbl;
	}

	public String getLg_post_cd() {
		return lg_post_cd;
	}

	public void setLg_post_cd(String lg_post_cd) {
		this.lg_post_cd = lg_post_cd;
	}

	public String getDistrict_cd() {
		return district_cd;
	}

	public void setDistrict_cd(String district_cd) {
		this.district_cd = district_cd;
	}

	public String getPostal_city() {
		return postal_city;
	}

	public void setPostal_city(String postal_city) {
		this.postal_city = postal_city;
	}

	public String getPostal_city_2() {
		return postal_city_2;
	}

	public void setPostal_city_2(String postal_city_2) {
		this.postal_city_2 = postal_city_2;
	}

	public String getInstreet_num() {
		return instreet_num;
	}

	public void setInstreet_num(String instreet_num) {
		this.instreet_num = instreet_num;
	}

	public String getRegion_cd() {
		return region_cd;
	}

	public void setRegion_cd(String region_cd) {
		this.region_cd = region_cd;
	}

	public String getCounty_cd() {
		return county_cd;
	}

	public void setCounty_cd(String county_cd) {
		this.county_cd = county_cd;
	}

	public String getCanton_cd() {
		return canton_cd;
	}

	public void setCanton_cd(String canton_cd) {
		this.canton_cd = canton_cd;
	}

	public String getCity_cd() {
		return city_cd;
	}

	public void setCity_cd(String city_cd) {
		this.city_cd = city_cd;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity_2() {
		return city_2;
	}

	public void setCity_2(String city_2) {
		this.city_2 = city_2;
	}

	public String getWork_phone() {
		return work_phone;
	}

	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHome_phone() {
		return home_phone;
	}

	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}

	public String getVoice_mail() {
		return voice_mail;
	}

	public void setVoice_mail(String voice_mail) {
		this.voice_mail = voice_mail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public String getLang_cd() {
		return lang_cd;
	}

	public void setLang_cd(String lang_cd) {
		this.lang_cd = lang_cd;
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

	public String getTermination_date() {
		return termination_date;
	}

	public void setTermination_date(String termination_date) {
		this.termination_date = termination_date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmployment_type_code() {
		return employment_type_code;
	}

	public void setEmployment_type_code(String employment_type_code) {
		this.employment_type_code = employment_type_code;
	}

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


	
	

}
