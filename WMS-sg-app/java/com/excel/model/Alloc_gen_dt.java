package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Alloc_gen_dt")
public class Alloc_gen_dt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_GEN_DTL_ID")
	private Long alloc_gen_dtl_id;

	@ManyToOne
	@JoinColumn(name = "ALLOC_GEN_ID")
	private Alloc_gen_hd alloc_gen_id;
	
	@ManyToOne
	@JoinColumn(name = "ALLOC_ENT_ID")
	private Alloc_gen_ent alloc_ent_id;

	@Column(name = "FIN_YEAR")
	private String fin_year;

	@Column(name = "ALLOC_GEN_DATE")
	private Date alloc_gen_date;

	@Column(name = "PERIOD_CODE")
	private String period_code;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "DIVISION")
	String division;

	@Column(name = "DIV_ID")
	Long div_id;

	@Column(name = "CFA_DESTINATION_ID")
	Long cfa_destination_id;

	@Column(name = "ZONE_ID")
	Long zone_id;

	@Column(name = "STATE_ID")
	Long state_id;

	@Column(name = "RBM_ID")
	Long rbm_id;

	@Column(name = "ABM_ID")
	Long abm_id;

	@Column(name = "MSR_ID")
	Long msr_id;

	@Column(name = "PROD_ID")
	Long prod_id;

	@Column(name = "ALT_DIV_ID")
	Long alt_div_id;

	@Column(name = "ALLOC_CYCLE")
	Long alloc_cycle;

	@Column(name = "ALLOC_RATE")
	Long alloc_rate;

	@Column(name = "ALLOC_QTY_MSR")
	Integer alloc_qty_msr;

	@Column(name = "ALLOC_QTY_ABM")
	Integer alloc_qty_abm;

	@Column(name = "ALLOC_QTY_RBM")
	Integer alloc_qty_rbm;

	@Column(name = "USER_ID")
	private String user_id;

	@Column(name = "UPD_DATE")
	Date upd_date;

	@Column(name = "UPD_IP_ADD")
	String upd_ip_add;

	@Column(name = "STATUS")
	String status;

	@Column(name="ALLOC_MODE")
	private String alloc_mode;
	
	
	@Column(name="FSTAFF_TRAINING")
	private String fstaff_training;

	@Column(name = "PRODUCT_TYPE")
	private String product_type;
	
	@Column(name = "MOD_UPD_DATE")
	private Date mod_upd_date;
	
	@Column(name = "MOD_USER_ID")
	private String  mod_user_id;
	
	@Column(name = "MOD_UPD_IP_ADD")
	private String mod_upd_ip_add;
	
	@Column(name = "ALLOC_CON_ID")
	private Long alloc_con_id;
	
	@Column(name = "ALLOC_CON_DTL_ID")
	private Long alloc_con_dtl_id;
	
	@Column(name = "alloc_con_docno")
	private String alloc_con_docno;
	
	@Column(name = "ALLOC_CON_DATE")
	private Date alloc_con_date;
	
	public Long getAlloc_con_id() {
		return alloc_con_id;
	}

	public void setAlloc_con_id(Long alloc_con_id) {
		this.alloc_con_id = alloc_con_id;
	}

	public Long getAlloc_con_dtl_id() {
		return alloc_con_dtl_id;
	}

	public void setAlloc_con_dtl_id(Long alloc_con_dtl_id) {
		this.alloc_con_dtl_id = alloc_con_dtl_id;
	}

	public String getAlloc_con_docno() {
		return alloc_con_docno;
	}

	public void setAlloc_con_docno(String alloc_con_docno) {
		this.alloc_con_docno = alloc_con_docno;
	}

	public Date getAlloc_con_date() {
		return alloc_con_date;
	}

	public void setAlloc_con_date(Date alloc_con_date) {
		this.alloc_con_date = alloc_con_date;
	}

	public Long getAlloc_gen_dtl_id() {
		return alloc_gen_dtl_id;
	}

	public void setAlloc_gen_dtl_id(Long alloc_gen_dtl_id) {
		this.alloc_gen_dtl_id = alloc_gen_dtl_id;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public Long getCfa_destination_id() {
		return cfa_destination_id;
	}

	public void setCfa_destination_id(Long cfa_destination_id) {
		this.cfa_destination_id = cfa_destination_id;
	}

	public Long getZone_id() {
		return zone_id;
	}

	public void setZone_id(Long zone_id) {
		this.zone_id = zone_id;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public Long getRbm_id() {
		return rbm_id;
	}

	public void setRbm_id(Long rbm_id) {
		this.rbm_id = rbm_id;
	}

	public Long getAbm_id() {
		return abm_id;
	}

	public void setAbm_id(Long abm_id) {
		this.abm_id = abm_id;
	}

	public Long getMsr_id() {
		return msr_id;
	}

	public void setMsr_id(Long msr_id) {
		this.msr_id = msr_id;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public Long getAlt_div_id() {
		return alt_div_id;
	}

	public void setAlt_div_id(Long alt_div_id) {
		this.alt_div_id = alt_div_id;
	}

	public Long getAlloc_cycle() {
		return alloc_cycle;
	}

	public void setAlloc_cycle(Long alloc_cycle) {
		this.alloc_cycle = alloc_cycle;
	}

	public Long getAlloc_rate() {
		return alloc_rate;
	}

	public void setAlloc_rate(Long alloc_rate) {
		this.alloc_rate = alloc_rate;
	}

	public Integer getAlloc_qty_msr() {
		return alloc_qty_msr;
	}

	public void setAlloc_qty_msr(Integer alloc_qty_msr) {
		this.alloc_qty_msr = alloc_qty_msr;
	}

	public Integer getAlloc_qty_abm() {
		return alloc_qty_abm;
	}

	public void setAlloc_qty_abm(Integer alloc_qty_abm) {
		this.alloc_qty_abm = alloc_qty_abm;
	}

	public Integer getAlloc_qty_rbm() {
		return alloc_qty_rbm;
	}

	public void setAlloc_qty_rbm(Integer alloc_qty_rbm) {
		this.alloc_qty_rbm = alloc_qty_rbm;
	}

	public Date getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Date upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_ip_add() {
		return upd_ip_add;
	}

	public void setUpd_ip_add(String upd_ip_add) {
		this.upd_ip_add = upd_ip_add;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getAlloc_gen_date() {
		return alloc_gen_date;
	}

	public void setAlloc_gen_date(Date alloc_gen_date) {
		this.alloc_gen_date = alloc_gen_date;
	}

	public Alloc_gen_hd getAlloc_gen_id() {
		return alloc_gen_id;
	}

	public void setAlloc_gen_id(Alloc_gen_hd alloc_gen_id) {
		this.alloc_gen_id = alloc_gen_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getAlloc_mode() {
		return alloc_mode;
	}

	public void setAlloc_mode(String alloc_mode) {
		this.alloc_mode = alloc_mode;
	}

	public String getFstaff_training() {
		return fstaff_training;
	}

	public void setFstaff_training(String fstaff_training) {
		this.fstaff_training = fstaff_training;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public Alloc_gen_ent getAlloc_ent_id() {
		return alloc_ent_id;
	}

	public void setAlloc_ent_id(Alloc_gen_ent alloc_ent_id) {
		this.alloc_ent_id = alloc_ent_id;
	}

	public Date getMod_upd_date() {
		return mod_upd_date;
	}

	public void setMod_upd_date(Date mod_upd_date) {
		this.mod_upd_date = mod_upd_date;
	}

	public String getMod_user_id() {
		return mod_user_id;
	}

	public void setMod_user_id(String mod_user_id) {
		this.mod_user_id = mod_user_id;
	}

	public String getMod_upd_ip_add() {
		return mod_upd_ip_add;
	}

	public void setMod_upd_ip_add(String mod_upd_ip_add) {
		this.mod_upd_ip_add = mod_upd_ip_add;
	}
	
}
