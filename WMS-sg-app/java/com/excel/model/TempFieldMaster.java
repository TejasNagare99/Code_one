package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEMP_FSTAFFMAS")
public class TempFieldMaster {

	@Id
	@Column(name="SLNO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long slno;
	
	@Column(name="DATE_TIME")
	private Date date_time;
	
	@Column(name="DATE_TIME_RECD")
	private Date date_time_recd;
	
	@Column(name="TERR_CODE")
	private String terr_code;
	
	@Column(name="TERR_ID")
	private Long terr_id;
	
	@Column(name="FS_NEW_CHANGED")
	private String fs_new_changed;
	
	@Column(name="FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name="COMPANY")
	private String company;
	
	@Column(name="FSTAFF_ID")
	private Long fstaff_id;
	
	@Column(name="FSTAFF_NAME")
	private String fstaff_name;
	
	@Column(name="FSTAFF_DISPLAY_NAME")
	private String fstaff_display_name;
	
	@Column(name="FSTAFF_EMPLOYEE_NO")
	private String fstaff_employee_no;
	
	@Column(name="FSTAFF_ADDR1")
	private String fstaff_addr1;
	
	@Column(name="FSTAFF_ADDR2")
	private String fstaff_addr2;
	
	@Column(name="FSTAFF_ADDR3")
	private String fstaff_addr3;
	
	@Column(name="FSTAFF_ADDR4")
	private String fstaff_addr4;
	
	@Column(name="FSTAFF_DESTINATION")
	private String fstaff_destination;
	
	@Column(name="FSTAFF_PINCODE")
	private String fstaff_pincode;
	
	@Column(name="FSTAFF_MOBILE2")
	private String fstaff_mobile2;
	
	@Column(name="FSTAFF_PAN_NO")
	private String fstaff_pan_no;
	
	@Column(name="FSTAFF_TEL_NO")
	private String fstaff_tel_no;
	
	@Column(name="FSTAFF_MOBILE")
	private String fstaff_mobile;
	
	@Column(name="FSTAFF_EMAIL")
	private String fstaff_email;
	
	@Column(name="FSTAFF_TRANSPORTER")
	private String fstaff_transporter;
	
	@Column(name="FSTAFF_RESIGNED")
	private String fstaff_resigned;
	
	@Column(name="FSTAFF_JOINING_DATE")
	private Date fstaff_joining_date;
	
	@Column(name="FSTAFF_LEAVING_DATE")
	private Date fstaff_leaving_date;
	
	@Column(name="THIRDPARTY_DIV")
	private Long thirdparty_div;
		
	@Column(name="FSTAFF_SAMP_DISP_IND")
	private String fstaff_samp_disp_ind;
	
	@Column(name="Main_MAST_UPDATED")
	private String main_mast_updated;
	
	@Column(name="DATE_TIME_UPDATED")
	private Date date_time_updated;
	
	@Column(name="FSTAFF_DIV_ID")
	private Long fstaff_div_id;
	
	@Column(name="MGR1_ID")
	private Long mgr1_id;
	
	@Column(name="MGR2_ID")
	private Long mgr2_id;
	
	@Column(name="MGR3_ID")
	private Long mgr3_id;

	@Column(name="STATE_ID")
	private Long state_id;
	
	@Column(name="CFA_LOC_PFZ_ID")
	private Long cfa_loc_pfz_id;
	
	@Column(name="CFA_LOC_PIPL_ID")
	private Long cfa_loc_pipl_id;

	@Column(name="ZONE_ID")
	private Long zone_id;

	@Column(name="HQ_ID")
	private Long hq_id;

	@Column(name="TEAM_CODE")
	private String team_code;
	
	@Column(name="MGR1_TERR_CODE")
	private String mgr1_terr_code;
	
	@Column(name="MGR2_TERR_CODE")
	private String mgr2_terr_code;
	
	@Column(name="MGR3_TERR_CODE")
	private String mgr3_terr_code;
	
	@Column(name="MGR4_TERR_CODE")
	private String mgr4_terr_code;
	
	@Column(name="MGR5_TERR_CODE")
	private String mgr5_terr_code;
	
	@Column(name="MGR6_TERR_CODE")
	private String mgr6_terr_code;
	
	@Column(name="FSTAFF_LEVEL_CODE")
	private String fstaff_level_code;
	
	
	
	

	public String getMgr1_terr_code() {
		return mgr1_terr_code;
	}

	public void setMgr1_terr_code(String mgr1_terr_code) {
		this.mgr1_terr_code = mgr1_terr_code;
	}

	public String getMgr2_terr_code() {
		return mgr2_terr_code;
	}

	public void setMgr2_terr_code(String mgr2_terr_code) {
		this.mgr2_terr_code = mgr2_terr_code;
	}

	public String getMgr3_terr_code() {
		return mgr3_terr_code;
	}

	public void setMgr3_terr_code(String mgr3_terr_code) {
		this.mgr3_terr_code = mgr3_terr_code;
	}

	public String getMgr4_terr_code() {
		return mgr4_terr_code;
	}

	public void setMgr4_terr_code(String mgr4_terr_code) {
		this.mgr4_terr_code = mgr4_terr_code;
	}

	public String getMgr5_terr_code() {
		return mgr5_terr_code;
	}

	public void setMgr5_terr_code(String mgr5_terr_code) {
		this.mgr5_terr_code = mgr5_terr_code;
	}

	public String getMgr6_terr_code() {
		return mgr6_terr_code;
	}

	public void setMgr6_terr_code(String mgr6_terr_code) {
		this.mgr6_terr_code = mgr6_terr_code;
	}

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
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

	public String getTerr_code() {
		return terr_code;
	}

	public void setTerr_code(String terr_code) {
		this.terr_code = terr_code;
	}

	public Long getTerr_id() {
		return terr_id;
	}

	public void setTerr_id(Long terr_id) {
		this.terr_id = terr_id;
	}

	public String getFs_new_changed() {
		return fs_new_changed;
	}

	public void setFs_new_changed(String fs_new_changed) {
		this.fs_new_changed = fs_new_changed;
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

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getFstaff_name() {
		return fstaff_name;
	}

	public void setFstaff_name(String fstaff_name) {
		this.fstaff_name = fstaff_name;
	}

	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public String getFstaff_employee_no() {
		return fstaff_employee_no;
	}

	public void setFstaff_employee_no(String fstaff_employee_no) {
		this.fstaff_employee_no = fstaff_employee_no;
	}

	public String getFstaff_addr1() {
		return fstaff_addr1;
	}

	public void setFstaff_addr1(String fstaff_addr1) {
		this.fstaff_addr1 = fstaff_addr1;
	}

	public String getFstaff_addr2() {
		return fstaff_addr2;
	}

	public void setFstaff_addr2(String fstaff_addr2) {
		this.fstaff_addr2 = fstaff_addr2;
	}

	public String getFstaff_addr3() {
		return fstaff_addr3;
	}

	public void setFstaff_addr3(String fstaff_addr3) {
		this.fstaff_addr3 = fstaff_addr3;
	}

	public String getFstaff_addr4() {
		return fstaff_addr4;
	}

	public void setFstaff_addr4(String fstaff_addr4) {
		this.fstaff_addr4 = fstaff_addr4;
	}

	public String getFstaff_destination() {
		return fstaff_destination;
	}

	public void setFstaff_destination(String fstaff_destination) {
		this.fstaff_destination = fstaff_destination;
	}

	public String getFstaff_pincode() {
		return fstaff_pincode;
	}

	public void setFstaff_pincode(String fstaff_pincode) {
		this.fstaff_pincode = fstaff_pincode;
	}

	public String getFstaff_mobile2() {
		return fstaff_mobile2;
	}

	public void setFstaff_mobile2(String fstaff_mobile2) {
		this.fstaff_mobile2 = fstaff_mobile2;
	}

	public String getFstaff_pan_no() {
		return fstaff_pan_no;
	}

	public void setFstaff_pan_no(String fstaff_pan_no) {
		this.fstaff_pan_no = fstaff_pan_no;
	}

	public String getFstaff_tel_no() {
		return fstaff_tel_no;
	}

	public void setFstaff_tel_no(String fstaff_tel_no) {
		this.fstaff_tel_no = fstaff_tel_no;
	}

	public String getFstaff_mobile() {
		return fstaff_mobile;
	}

	public void setFstaff_mobile(String fstaff_mobile) {
		this.fstaff_mobile = fstaff_mobile;
	}

	public String getFstaff_email() {
		return fstaff_email;
	}

	public void setFstaff_email(String fstaff_email) {
		this.fstaff_email = fstaff_email;
	}

	public String getFstaff_transporter() {
		return fstaff_transporter;
	}

	public void setFstaff_transporter(String fstaff_transporter) {
		this.fstaff_transporter = fstaff_transporter;
	}

	public String getFstaff_resigned() {
		return fstaff_resigned;
	}

	public void setFstaff_resigned(String fstaff_resigned) {
		this.fstaff_resigned = fstaff_resigned;
	}

	public Date getFstaff_joining_date() {
		return fstaff_joining_date;
	}

	public void setFstaff_joining_date(Date fstaff_joining_date) {
		this.fstaff_joining_date = fstaff_joining_date;
	}

	public Date getFstaff_leaving_date() {
		return fstaff_leaving_date;
	}

	public void setFstaff_leaving_date(Date fstaff_leaving_date) {
		this.fstaff_leaving_date = fstaff_leaving_date;
	}

	public String getFstaff_samp_disp_ind() {
		return fstaff_samp_disp_ind;
	}

	public void setFstaff_samp_disp_ind(String fstaff_samp_disp_ind) {
		this.fstaff_samp_disp_ind = fstaff_samp_disp_ind;
	}

	public String getMain_mast_updated() {
		return main_mast_updated;
	}

	public void setMain_mast_updated(String main_mast_updated) {
		this.main_mast_updated = main_mast_updated;
	}

	public Date getDate_time_updated() {
		return date_time_updated;
	}

	public void setDate_time_updated(Date date_time_updated) {
		this.date_time_updated = date_time_updated;
	}

	public Long getFstaff_div_id() {
		return fstaff_div_id;
	}

	public void setFstaff_div_id(Long fstaff_div_id) {
		this.fstaff_div_id = fstaff_div_id;
	}

	public Long getMgr1_id() {
		return mgr1_id;
	}

	public void setMgr1_id(Long mgr1_id) {
		this.mgr1_id = mgr1_id;
	}

	public Long getMgr2_id() {
		return mgr2_id;
	}

	public void setMgr2_id(Long mgr2_id) {
		this.mgr2_id = mgr2_id;
	}

	public Long getMgr3_id() {
		return mgr3_id;
	}

	public void setMgr3_id(Long mgr3_id) {
		this.mgr3_id = mgr3_id;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public Long getCfa_loc_pfz_id() {
		return cfa_loc_pfz_id;
	}

	public void setCfa_loc_pfz_id(Long cfa_loc_pfz_id) {
		this.cfa_loc_pfz_id = cfa_loc_pfz_id;
	}

	public Long getCfa_loc_pipl_id() {
		return cfa_loc_pipl_id;
	}

	public void setCfa_loc_pipl_id(Long cfa_loc_pipl_id) {
		this.cfa_loc_pipl_id = cfa_loc_pipl_id;
	}

	public Long getZone_id() {
		return zone_id;
	}

	public void setZone_id(Long zone_id) {
		this.zone_id = zone_id;
	}

	public Long getHq_id() {
		return hq_id;
	}

	public void setHq_id(Long hq_id) {
		this.hq_id = hq_id;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public Long getThirdparty_div() {
		return thirdparty_div;
	}

	public void setThirdparty_div(Long thirdparty_div) {
		this.thirdparty_div = thirdparty_div;
	}

	public String getFstaff_level_code() {
		return fstaff_level_code;
	}

	public void setFstaff_level_code(String fstaff_level_code) {
		this.fstaff_level_code = fstaff_level_code;
	}

	
	

}
