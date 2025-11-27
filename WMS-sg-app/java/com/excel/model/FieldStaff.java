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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "FIELDSTAFF")
@DynamicUpdate(value=true)
public class FieldStaff implements Serializable{	

	/**
	 * 
	 */
	private static final long serialVersionUID = -326035211647419703L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="FSTAFF_ID")
	private Long fstaff_id;
	
	@Column(name="FSTAFF_LEVEL_CODE")
	private String level_code;
	
	@Column(name="FSTAFF_COMPANY")
	private String company;
	
	@Column(name="FSTAFF_NAME")
	private String fstaff_name;

	@Column(name="FSTAFF_MAP_CODE1")
	private String fstaff_map_code1;
	
	@Column(name="FSTAFF_DIV_ID")
	private Long fs_div_id;
	
	@Column(name="FSTAFF_SAMP_DISP_IND")
	private String fstaff_samp_disp_ind;
	
	@Column(name="FSTAFF_ADDR1")
	private String fstaff_addr1;
	
	@Column(name="FSTAFF_ADDR2")
	private String fstaff_addr2;
	
	@Column(name="FSTAFF_ADDR3")
	private String fstaff_addr3;
	
	@Column(name="FSTAFF_ADDR4")
	private String fstaff_addr4;
	
	@Column(name="FSTAFF_POOL_IND")
	private String fstaff_pool_ind;
	
	@Column(name="FSTAFF_DOM_EXP")
	private String fstaff_dom_exp;
	
	@Column(name="FSTAFF_ZONE_ID")
	private Long fstaff_zone_id;
	
	@Column(name="FSTAFF_STATE_ID")
	private Long fstaff_state_id;
	
	@Column(name="FSTAFF_MGR1_ID")
	private Long fstaff_mgr1_id;
	
	@Column(name="FSTAFF_MGR2_ID")
	private Long fstaff_mgr2_id;
	
	@Column(name="FSTAFF_MGR3_ID")
	private Long fstaff_mgr3_id;
	
	@Column(name="FSTAFF_MGR4_ID")
	private Long fstaff_mgr4_id;
	
	@Column(name="FSTAFF_SAMP_SUP_LOC")
	private Long fstaff_samp_sup_loc;
	
	@Column(name="FSTAFF_JOINING_DATE")
	private Date fstaff_joining_date;
	
	@Column(name="FSTAFF_MGR5_ID")
	private Long fstaff_mgr5_id;
	
	@Column(name="FSTAFF_MGR6_ID")
	private Long fstaff_mgr6_id;
	
	@Column(name="FSTAFF_DISPLAY_NAME")
	private String fstaff_display_name;
	
	@Column(name="FSTAFF_LOC_ID")
	private Long fstaff_loc_id;
	
	@Column(name="FSTAFF_DISP_CYCLE")
	private Long fstaff_disp_cycle;
	
	@Column(name="FSTAFF_ADDR_UPD_CYCLE")
	private Long fstaff_addr_upd_cycle;
	
	@Column(name="FSTAFF_EMAIL")
	private String email;
	
	@Column(name="FSTAFF_TRANSPORTER")
	private String fstaff_transporter;
	
	@Column(name="FSTAFF_TRAINING")
	private String fstaff_training;
	
	@Column(name="FS_CATEGORY")
	private String fs_category;
	
	@Column(name="FSTAFF_EMPLOYEE_NO")
	private String fstaff_emp_num;
	
	@Column(name="FSTAFF_ALT_NAME")
	private String fstaff_alt_name;
	
	@Column(name="FSTAFF_ins_usr_id")
	private String fstaff_ins_usr_id;	
	
	@Column(name="FSTAFF_PINCODE")
	private String fstaff_zip;
	
	@Column(name="FSTAFF_MOBILE")
	private String fstaff_mobile;
	
	@Column(name="FSTAFF_DESTINATION")
	private String fstaff_destination;
	
	@Column(name="FSTAFF_TEL_NO")
	private String fstaff_tel_no;
	
	@Column(name="FSTAFF_PAN_NO")
	private String fstaff_pan_no;
	
	@Column(name="FSTAFF_ERP_CODE")
	private String fstaff_erp_code;
	
	@Column(name="FSTAFF_ERP_LOC_CODE")
	private String fstaff_erp_loc_code;
	
	@Column(name="FSTAFF_CITY_ID")
	private Long city_id;
	
	@Column(name="FSTAFF_HQ_ID")
	private Long hq_id;
	
	@Column(name="CF_IDPFZ")
	private Long cf_idpfz;
	
	@Column(name="FSTAFF_CLASS_ID")
	private Long class_id;
	
	@Column(name="FSTAFF_TYPE_ID")
	private Long type_id;
	
	@Column(name="FSTAFF_status")
	private String status;	
	
	@Column(name="FSTAFF_ins_dt")
	private Date fstaff_ins_dt;
	
	@Column(name="FSTAFF_MAP_CODE2")
	private String fstaff_map_code2;
	
	@Column(name="FSTAFF_PMP_PERF")
	private BigDecimal fstaff_pmp_perf;
	
	@Column(name="FSTAFF_LEAVING_DATE")	
	private Date leaving_date;	
	
	@Column(name="FSTAFF_mod_dt")
	private Date fstaff_mod_dt;
	
	@Column(name="FSTAFF_TERRNAME")
	private String fstaff_terrname;
	
	@Column(name="FSTAFF_ZONENAME")
	private String fstaff_zonename;
	
	@Column(name="FSTAFF_REGIONCD")
	private String region_code;
	
	@Column(name="FSTAFF_DISTRICTCD")
	private String dist_code;	
	
	@Column(name="FSTAFF_CODE")
	private String fstaff_code;
	
	@Column(name="CF_IDWYTH")
	private Long cf_idwyth;	
	
	@Column(name="CF_IDPIPL")
	private Long cf_idpipl;	
	
	@Column(name="FSTAFF_mod_usr_id")
	private String fstaff_mod_usr_id;
	
	@Column(name="FSTAFF_ins_ip_add")
	private String fstaff_ins_ip_add;
	
	@Column(name="FSTAFF_mod_ip_add")
	private String fstaff_mod_ip_add;	
	
	@Column(name="FSTAFF_DESIG")
	private String designation;
	
	@Column(name="FSTAFF_TYPE_IND_ID")
	private Long fstaff_type_ind_id;
	
	@Column(name="FSTAFF_MCL_NO")
	private String fstaff_mcl_no;
	
	@Column(name="FSTAFF_REQUESTOR_FSTAFF_ID")
	private Long fstaff_requestor_fstaff_id;
	
	@Column(name="FSTAFF_PROD_SUB_TYPE_ALLOWED")
	private String fstaff_prod_sub_type_allowed;
	
	@Column(name="FSTAFF_TERR_ID")
	private String fstaff_terr_id;
	
	@Column(name="FSTAFF_TERR_CODE")
	private String fstaff_terr_code;
	
	@Column(name="FSTAFF_MOBILE2")
	private String fstaff_mobile2;
	
	@Column(name="ADDR_CHG_REMARKS")
	private String addr_chg_remarks;
	
	@Column(name="TEAM_CODE")
	private String team_code;

	@Transient 
	private String fullAddr;
	
	
	
	public String getFullAddr() {
		return fullAddr;
	}

	public void setFullAddr(String fullAddr) {
		this.fullAddr = fullAddr;
	}

	public Long getFstaff_mgr3_id() {
		return fstaff_mgr3_id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setFstaff_mgr3_id(Long fstaff_mgr3_id) {
		this.fstaff_mgr3_id = fstaff_mgr3_id;
	}

	public Long getFstaff_mgr4_id() {
		return fstaff_mgr4_id;
	}

	public void setFstaff_mgr4_id(Long fstaff_mgr4_id) {
		this.fstaff_mgr4_id = fstaff_mgr4_id;
	}

	public Long getFstaff_mgr5_id() {
		return fstaff_mgr5_id;
	}

	public void setFstaff_mgr5_id(Long fstaff_mgr5_id) {
		this.fstaff_mgr5_id = fstaff_mgr5_id;
	}

	public Long getFstaff_mgr6_id() {
		return fstaff_mgr6_id;
	}

	public void setFstaff_mgr6_id(Long fstaff_mgr6_id) {
		this.fstaff_mgr6_id = fstaff_mgr6_id;
	}

	public String getRegion_code() {
		return region_code;
	}

	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	public String getDist_code() {
		return dist_code;
	}

	public void setDist_code(String dist_code) {
		this.dist_code = dist_code;
	}

	public String getFstaff_code() {
		return fstaff_code;
	}

	public void setFstaff_code(String fstaff_code) {
		this.fstaff_code = fstaff_code;
	}

	public String getFstaff_ins_usr_id() {
		return fstaff_ins_usr_id;
	}

	public void setFstaff_ins_usr_id(String fstaff_ins_usr_id) {
		this.fstaff_ins_usr_id = fstaff_ins_usr_id;
	}

	public String getFstaff_mod_usr_id() {
		return fstaff_mod_usr_id;
	}

	public void setFstaff_mod_usr_id(String fstaff_mod_usr_id) {
		this.fstaff_mod_usr_id = fstaff_mod_usr_id;
	}

	public String getFstaff_ins_ip_add() {
		return fstaff_ins_ip_add;
	}

	public void setFstaff_ins_ip_add(String fstaff_ins_ip_add) {
		this.fstaff_ins_ip_add = fstaff_ins_ip_add;
	}

	public String getFstaff_mod_ip_add() {
		return fstaff_mod_ip_add;
	}

	public void setFstaff_mod_ip_add(String fstaff_mod_ip_add) {
		this.fstaff_mod_ip_add = fstaff_mod_ip_add;
	}

	public String getFstaff_terrname() {
		return fstaff_terrname;
	}

	public void setFstaff_terrname(String fstaff_terrname) {
		this.fstaff_terrname = fstaff_terrname;
	}

	public Date getFstaff_mod_dt() {
		return fstaff_mod_dt;
	}

	public void setFstaff_mod_dt(Date fstaff_mod_dt) {
		this.fstaff_mod_dt = fstaff_mod_dt;
	}

	public Date getLeaving_date() {
		return leaving_date;
	}

	public void setLeaving_date(Date leaving_date) {
		this.leaving_date = leaving_date;
	}

	public Date getFstaff_ins_dt() {
		return fstaff_ins_dt;
	}

	public void setFstaff_ins_dt(Date fstaff_ins_dt) {
		this.fstaff_ins_dt = fstaff_ins_dt;
	}

	public String getFstaff_map_code2() {
		return fstaff_map_code2;
	}

	public void setFstaff_map_code2(String fstaff_map_code2) {
		this.fstaff_map_code2 = fstaff_map_code2;
	}

	public String getFstaff_zip() {
		return fstaff_zip;
	}

	public void setFstaff_zip(String fstaff_zip) {
		this.fstaff_zip = fstaff_zip;
	}

	public String getFstaff_mobile() {
		return fstaff_mobile;
	}

	public void setFstaff_mobile(String fstaff_mobile) {
		this.fstaff_mobile = fstaff_mobile;
	}

	public String getFstaff_destination() {
		return fstaff_destination;
	}

	public void setFstaff_destination(String fstaff_destination) {
		this.fstaff_destination = fstaff_destination;
	}

	public String getFstaff_erp_code() {
		return fstaff_erp_code;
	}

	public void setFstaff_erp_code(String fstaff_erp_code) {
		this.fstaff_erp_code = fstaff_erp_code;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Long getHq_id() {
		return hq_id;
	}

	public void setHq_id(Long hq_id) {
		this.hq_id = hq_id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Long getFstaff_loc_id() {
		return fstaff_loc_id;
	}

	public void setFstaff_loc_id(Long fstaff_loc_id) {
		this.fstaff_loc_id = fstaff_loc_id;
	}

	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public Long getFstaff_mgr1_id() {
		return fstaff_mgr1_id;
	}

	public void setFstaff_mgr1_id(Long fstaff_mgr1_id) {
		this.fstaff_mgr1_id = fstaff_mgr1_id;
	}

	public Long getFstaff_mgr2_id() {
		return fstaff_mgr2_id;
	}

	public void setFstaff_mgr2_id(Long fstaff_mgr2_id) {
		this.fstaff_mgr2_id = fstaff_mgr2_id;
	}

	public Long getFstaff_zone_id() {
		return fstaff_zone_id;
	}

	public void setFstaff_zone_id(Long fstaff_zone_id) {
		this.fstaff_zone_id = fstaff_zone_id;
	}

	public Long getFstaff_state_id() {
		return fstaff_state_id;
	}

	public void setFstaff_state_id(Long fstaff_state_id) {
		this.fstaff_state_id = fstaff_state_id;
	}

	public String getFstaff_dom_exp() {
		return fstaff_dom_exp;
	}

	public void setFstaff_dom_exp(String fstaff_dom_exp) {
		this.fstaff_dom_exp = fstaff_dom_exp;
	}

	public String getFstaff_samp_disp_ind() {
		return fstaff_samp_disp_ind;
	}

	public void setFstaff_samp_disp_ind(String fstaff_samp_disp_ind) {
		this.fstaff_samp_disp_ind = fstaff_samp_disp_ind;
	}

	public Long getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(Long fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getLevel_code() {
		return level_code;
	}

	public void setLevel_code(String level_code) {
		this.level_code = level_code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFstaff_name() {
		return fstaff_name;
	}

	public void setFstaff_name(String fstaff_name) {
		this.fstaff_name = fstaff_name;
	}

	public void setFstaff_map_code1(String fstaff_map_code1) {
		this.fstaff_map_code1 = fstaff_map_code1;
	}

	public String getFstaff_map_code1() {
		return fstaff_map_code1;
	}

	public void setFs_div_id(Long fs_div_id) {
		this.fs_div_id = fs_div_id;
	}

	public Long getFs_div_id() {
		return fs_div_id;
	}

	public String getFstaff_training() {
		return fstaff_training;
	}

	public void setFstaff_training(String fstaff_training) {
		this.fstaff_training = fstaff_training;
	}

	public String getFstaff_emp_num() {
		return fstaff_emp_num;
	}
	

	public String getFs_category() {
		return fs_category;
	}

	public void setFs_category(String fs_category) {
		this.fs_category = fs_category;
	}

	public void setFstaff_emp_num(String fstaff_emp_num) {
		this.fstaff_emp_num = fstaff_emp_num;
	}

	public String getFstaff_alt_name() {
		return fstaff_alt_name;
	}

	public void setFstaff_alt_name(String fstaff_alt_name) {
		this.fstaff_alt_name = fstaff_alt_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getFstaff_pmp_perf() {
		return fstaff_pmp_perf;
	}

	public void setFstaff_pmp_perf(BigDecimal fstaff_pmp_perf) {
		this.fstaff_pmp_perf = fstaff_pmp_perf;
	}

	public Long getFstaff_disp_cycle() {
		return fstaff_disp_cycle;
	}

	public void setFstaff_disp_cycle(Long fstaff_disp_cycle) {
		this.fstaff_disp_cycle = fstaff_disp_cycle;
	}

	public Long getFstaff_addr_upd_cycle() {
		return fstaff_addr_upd_cycle;
	}

	public void setFstaff_addr_upd_cycle(Long fstaff_addr_upd_cycle) {
		this.fstaff_addr_upd_cycle = fstaff_addr_upd_cycle;
	}

	public String getFstaff_pan_no() {
		return fstaff_pan_no;
	}

	public void setFstaff_pan_no(String fstaff_pan_no) {
		this.fstaff_pan_no = fstaff_pan_no;
	}

	public String getFstaff_transporter() {
		return fstaff_transporter;
	}

	public void setFstaff_transporter(String fstaff_transporter) {
		this.fstaff_transporter = fstaff_transporter;
	}

	public String getFstaff_erp_loc_code() {
		return fstaff_erp_loc_code;
	}

	public void setFstaff_erp_loc_code(String fstaff_erp_loc_code) {
		this.fstaff_erp_loc_code = fstaff_erp_loc_code;
	}

	public Date getFstaff_joining_date() {
		return fstaff_joining_date;
	}

	public void setFstaff_joining_date(Date fstaff_joining_date) {
		this.fstaff_joining_date = fstaff_joining_date;
	}

	public Long getCf_idpfz() {
		return cf_idpfz;
	}

	public void setCf_idpfz(Long cf_idpfz) {
		this.cf_idpfz = cf_idpfz;
	}

	public String getFstaff_tel_no() {
		return fstaff_tel_no;
	}

	public void setFstaff_tel_no(String fstaff_tel_no) {
		this.fstaff_tel_no = fstaff_tel_no;
	}

	public String getFstaff_pool_ind() {
		return fstaff_pool_ind;
	}

	public void setFstaff_pool_ind(String fstaff_pool_ind) {
		this.fstaff_pool_ind = fstaff_pool_ind;
	}

	public Long getCf_idwyth() {
		return cf_idwyth;
	}

	public void setCf_idwyth(Long cf_idwyth) {
		this.cf_idwyth = cf_idwyth;
	}

	public Long getCf_idpipl() {
		return cf_idpipl;
	}

	public void setCf_idpipl(Long cf_idpipl) {
		this.cf_idpipl = cf_idpipl;
	}

	public String getFstaff_zonename() {
		return fstaff_zonename;
	}

	public void setFstaff_zonename(String fstaff_zonename) {
		this.fstaff_zonename = fstaff_zonename;
	}

	public Long getFstaff_samp_sup_loc() {
		return fstaff_samp_sup_loc;
	}

	public void setFstaff_samp_sup_loc(Long fstaff_samp_sup_loc) {
		this.fstaff_samp_sup_loc = fstaff_samp_sup_loc;
	}

	public Long getFstaff_type_ind_id() {
		return fstaff_type_ind_id;
	}

	public void setFstaff_type_ind_id(Long fstaff_type_ind_id) {
		this.fstaff_type_ind_id = fstaff_type_ind_id;
	}

	public String getFstaff_mcl_no() {
		return fstaff_mcl_no;
	}

	public void setFstaff_mcl_no(String fstaff_mcl_no) {
		this.fstaff_mcl_no = fstaff_mcl_no;
	}

	public Long getFstaff_requestor_fstaff_id() {
		return fstaff_requestor_fstaff_id;
	}

	public void setFstaff_requestor_fstaff_id(Long fstaff_requestor_fstaff_id) {
		this.fstaff_requestor_fstaff_id = fstaff_requestor_fstaff_id;
	}

	public String getFstaff_prod_sub_type_allowed() {
		return fstaff_prod_sub_type_allowed;
	}

	public void setFstaff_prod_sub_type_allowed(String fstaff_prod_sub_type_allowed) {
		this.fstaff_prod_sub_type_allowed = fstaff_prod_sub_type_allowed;
	}

	public String getFstaff_terr_id() {
		return fstaff_terr_id;
	}

	public void setFstaff_terr_id(String fstaff_terr_id) {
		this.fstaff_terr_id = fstaff_terr_id;
	}

	public String getFstaff_terr_code() {
		return fstaff_terr_code;
	}

	public void setFstaff_terr_code(String fstaff_terr_code) {
		this.fstaff_terr_code = fstaff_terr_code;
	}
	public FieldStaff() {
		
	}
	public FieldStaff(Long fstaff_id,String fstaff_display_name,String fstaff_name,String fstaff_terrname) {
		this.fstaff_id=fstaff_id;
		this.fstaff_display_name=fstaff_display_name;
		this.fstaff_name=fstaff_name;
		this.fstaff_terrname=fstaff_terrname;
	}
	
	public FieldStaff(Long fstaff_id,String fstaff_display_name) {
		this.fstaff_id=fstaff_id;
		this.fstaff_display_name=fstaff_display_name;
	}

	public String getFstaff_mobile2() {
		return fstaff_mobile2;
	}

	public void setFstaff_mobile2(String fstaff_mobile2) {
		this.fstaff_mobile2 = fstaff_mobile2;
	}

	public String getAddr_chg_remarks() {
		return addr_chg_remarks;
	}

	public void setAddr_chg_remarks(String addr_chg_remarks) {
		this.addr_chg_remarks = addr_chg_remarks;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public FieldStaff(Long fstaff_id) {
		super();
		this.fstaff_id = fstaff_id;
	}

	@Override
	public String toString() {
		return "FieldStaff [fstaff_id=" + fstaff_id + ", level_code=" + level_code + ", company=" + company
				+ ", fstaff_name=" + fstaff_name + ", fstaff_map_code1=" + fstaff_map_code1 + ", fs_div_id=" + fs_div_id
				+ ", fstaff_samp_disp_ind=" + fstaff_samp_disp_ind + ", fstaff_addr1=" + fstaff_addr1
				+ ", fstaff_addr2=" + fstaff_addr2 + ", fstaff_addr3=" + fstaff_addr3 + ", fstaff_addr4=" + fstaff_addr4
				+ ", fstaff_pool_ind=" + fstaff_pool_ind + ", fstaff_dom_exp=" + fstaff_dom_exp + ", fstaff_zone_id="
				+ fstaff_zone_id + ", fstaff_state_id=" + fstaff_state_id + ", fstaff_mgr1_id=" + fstaff_mgr1_id
				+ ", fstaff_mgr2_id=" + fstaff_mgr2_id + ", fstaff_mgr3_id=" + fstaff_mgr3_id + ", fstaff_mgr4_id="
				+ fstaff_mgr4_id + ", fstaff_samp_sup_loc=" + fstaff_samp_sup_loc + ", fstaff_joining_date="
				+ fstaff_joining_date + ", fstaff_mgr5_id=" + fstaff_mgr5_id + ", fstaff_mgr6_id=" + fstaff_mgr6_id
				+ ", fstaff_display_name=" + fstaff_display_name + ", fstaff_loc_id=" + fstaff_loc_id
				+ ", fstaff_disp_cycle=" + fstaff_disp_cycle + ", fstaff_addr_upd_cycle=" + fstaff_addr_upd_cycle
				+ ", email=" + email + ", fstaff_transporter=" + fstaff_transporter + ", fstaff_training="
				+ fstaff_training + ", fs_category=" + fs_category + ", fstaff_emp_num=" + fstaff_emp_num
				+ ", fstaff_alt_name=" + fstaff_alt_name + ", fstaff_ins_usr_id=" + fstaff_ins_usr_id + ", fstaff_zip="
				+ fstaff_zip + ", fstaff_mobile=" + fstaff_mobile + ", fstaff_destination=" + fstaff_destination
				+ ", fstaff_tel_no=" + fstaff_tel_no + ", fstaff_pan_no=" + fstaff_pan_no + ", fstaff_erp_code="
				+ fstaff_erp_code + ", fstaff_erp_loc_code=" + fstaff_erp_loc_code + ", city_id=" + city_id + ", hq_id="
				+ hq_id + ", cf_idpfz=" + cf_idpfz + ", class_id=" + class_id + ", type_id=" + type_id + ", status="
				+ status + ", fstaff_ins_dt=" + fstaff_ins_dt + ", fstaff_map_code2=" + fstaff_map_code2
				+ ", fstaff_pmp_perf=" + fstaff_pmp_perf + ", leaving_date=" + leaving_date + ", fstaff_mod_dt="
				+ fstaff_mod_dt + ", fstaff_terrname=" + fstaff_terrname + ", fstaff_zonename=" + fstaff_zonename
				+ ", region_code=" + region_code + ", dist_code=" + dist_code + ", fstaff_code=" + fstaff_code
				+ ", cf_idwyth=" + cf_idwyth + ", cf_idpipl=" + cf_idpipl + ", fstaff_mod_usr_id=" + fstaff_mod_usr_id
				+ ", fstaff_ins_ip_add=" + fstaff_ins_ip_add + ", fstaff_mod_ip_add=" + fstaff_mod_ip_add
				+ ", designation=" + designation + ", fstaff_type_ind_id=" + fstaff_type_ind_id + ", fstaff_mcl_no="
				+ fstaff_mcl_no + ", fstaff_requestor_fstaff_id=" + fstaff_requestor_fstaff_id
				+ ", fstaff_prod_sub_type_allowed=" + fstaff_prod_sub_type_allowed + ", fstaff_terr_id="
				+ fstaff_terr_id + ", fstaff_terr_code=" + fstaff_terr_code + ", fstaff_mobile2=" + fstaff_mobile2
				+ ", addr_chg_remarks=" + addr_chg_remarks + ", team_code=" + team_code + ", fullAddr=" + fullAddr
				+ "]";
	}
	
	
	
}
