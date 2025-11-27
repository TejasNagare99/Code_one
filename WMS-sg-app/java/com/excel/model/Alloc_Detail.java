package com.excel.model;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "ALLOC_DETAIL")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Alloc_Detail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long allocdtl_id;

	//@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne
	@JoinColumn(name = "ALLOCDTL_ALLOC_ID")
	private Alloc_Header alloc_Header;
	@Column(name = "ALLOCDTL_SMPD_ID")
	private Long allocdtl_smpd_id;
	@Column(name = "ALLOCDTL_FIN_YEAR")
	private String allocdtl_fin_year;
	@Column(name = "ALLOCDTL_PERIOD_CODE")      
	private String allocdtl_period_code;
	@Column(name = "ALLOCDTL_COMPANY")
	private String allocdtl_company;
	@Column(name = "ALLOCDTL_FSTAFF_ID")
	private Long allocdtl_fstaff_id;
	@Column(name = "ALLOCDTL_DIV_ID")
	private Long allocdtl_div_id;
	@Column(name = "ALLOCDTL_PROD_ID")
	private Long allocdtl_prod_id;
	@Column(name = "ALLOCDTL_ALLOC_TYPE")
	private String allocdtl_alloc_type;
	@Column(name = "ALLOCDTL_CYCLE")
	private Integer allocdtl_cycle;
	@Column(name = "ALLOCDTL_RATE")
	private BigDecimal allocdtl_rate;
	@Column(name = "ALLOCDTL_ALLOC_QTY")
	private BigDecimal allocdtl_alloc_qty;
	@Column(name = "ALLOCDTL_CURR_ALLOC_QTY")
	private BigDecimal allocdtl_curr_alloc_qty;
	@Column(name = "ALLOCDTL_SUPPLY_QTY")
	private BigDecimal allocdtl_supply_qty;
	@Column(name = "ALLOCDTL_ERR_DTL")
	private String allocdtl_err_dtl;
	@Column(name = "ALLOCDTL_ALT_DIV_ID")
	private Long allocdtl_alt_div_id;
	@Column(name = "ALLOCDTL_INV_GRP")
	private Long allocdtl_inv_grp;
	@Column(name = "ALLOCDTL_BLK_FLG")
	private String allocdtl_blk_flg;
	@Column(name = "ALLOCDTL_PHY_QTY")
	private BigDecimal allocdtl_phy_qty;
	@Column(name = "ALLOCDTL_INS_USR_ID")
	private String allocdtl_ins_usr_id;
	@Column(name = "ALLOCDTL_MOD_USR_ID")
	private String allocdtl_mod_usr_id;
	@Column(name = "ALLOCDTL_MOD_DT")
	private Date allocdtl_mod_dt;
	@Column(name = "ALLOCDTL_INS_IP_ADD")
	private String allocdtl_ins_ip_add;
	@Column(name = "ALLOCDTL_MOD_IP_ADD")
	private String allocdtl_mod_ip_add;
	@Column(name = "ALLOCDTL_STATUS")
	private Character allocdtl_status;
	@Column(name = "ALLOCDTL_APPR_STATUS")
	private Character allocdtl_appr_status;

	public Long getAllocdtl_id() {
		return allocdtl_id;
	}

	public void setAllocdtl_id(Long allocdtl_id) {
		this.allocdtl_id = allocdtl_id;
	}

	/*
	 * public Long getAllocdtl_alloc_id() { return allocdtl_alloc_id; } public
	 * void setAllocdtl_alloc_id(Long allocdtl_alloc_id) {
	 * this.allocdtl_alloc_id = allocdtl_alloc_id; }
	 */
	public Long getAllocdtl_smpd_id() {
		return allocdtl_smpd_id;
	}

	public void setAllocdtl_smpd_id(Long allocdtl_smpd_id) {
		this.allocdtl_smpd_id = allocdtl_smpd_id;
	}

	public String getAllocdtl_fin_year() {
		return allocdtl_fin_year;
	}

	public void setAllocdtl_fin_year(String allocdtl_fin_year) {
		this.allocdtl_fin_year = allocdtl_fin_year;
	}

	public String getAllocdtl_period_code() {
		return allocdtl_period_code;
	}

	public void setAllocdtl_period_code(String allocdtl_period_code) {
		this.allocdtl_period_code = allocdtl_period_code;
	}

	public String getAllocdtl_company() {
		return allocdtl_company;
	}

	public void setAllocdtl_company(String allocdtl_company) {
		this.allocdtl_company = allocdtl_company;
	}

	public Long getAllocdtl_fstaff_id() {
		return allocdtl_fstaff_id;
	}

	public void setAllocdtl_fstaff_id(Long allocdtl_fstaff_id) {
		this.allocdtl_fstaff_id = allocdtl_fstaff_id;
	}

	public Long getAllocdtl_div_id() {
		return allocdtl_div_id;
	}

	public void setAllocdtl_div_id(Long allocdtl_div_id) {
		this.allocdtl_div_id = allocdtl_div_id;
	}

	public Long getAllocdtl_prod_id() {
		return allocdtl_prod_id;
	}

	public void setAllocdtl_prod_id(Long allocdtl_prod_id) {
		this.allocdtl_prod_id = allocdtl_prod_id;
	}

	public String getAllocdtl_alloc_type() {
		return allocdtl_alloc_type;
	}

	public void setAllocdtl_alloc_type(String allocdtl_alloc_type) {
		this.allocdtl_alloc_type = allocdtl_alloc_type;
	}

	

	public Integer getAllocdtl_cycle() {
		return allocdtl_cycle;
	}

	public void setAllocdtl_cycle(Integer allocdtl_cycle) {
		this.allocdtl_cycle = allocdtl_cycle;
	}

	public BigDecimal getAllocdtl_rate() {
		return allocdtl_rate;
	}

	public void setAllocdtl_rate(BigDecimal allocdtl_rate) {
		this.allocdtl_rate = allocdtl_rate;
	}

	public BigDecimal getAllocdtl_alloc_qty() {
		return allocdtl_alloc_qty;
	}

	public void setAllocdtl_alloc_qty(BigDecimal allocdtl_alloc_qty) {
		this.allocdtl_alloc_qty = allocdtl_alloc_qty;
	}

	public BigDecimal getAllocdtl_curr_alloc_qty() {
		return allocdtl_curr_alloc_qty;
	}

	public void setAllocdtl_curr_alloc_qty(BigDecimal allocdtl_curr_alloc_qty) {
		this.allocdtl_curr_alloc_qty = allocdtl_curr_alloc_qty;
	}

	public BigDecimal getAllocdtl_supply_qty() {
		return allocdtl_supply_qty;
	}

	public void setAllocdtl_supply_qty(BigDecimal allocdtl_supply_qty) {
		this.allocdtl_supply_qty = allocdtl_supply_qty;
	}

	public String getAllocdtl_err_dtl() {
		return allocdtl_err_dtl;
	}

	public void setAllocdtl_err_dtl(String allocdtl_err_dtl) {
		this.allocdtl_err_dtl = allocdtl_err_dtl;
	}

	public Long getAllocdtl_alt_div_id() {
		return allocdtl_alt_div_id;
	}

	public void setAllocdtl_alt_div_id(Long allocdtl_alt_div_id) {
		this.allocdtl_alt_div_id = allocdtl_alt_div_id;
	}

	public Long getAllocdtl_inv_grp() {
		return allocdtl_inv_grp;
	}

	public void setAllocdtl_inv_grp(Long allocdtl_inv_grp) {
		this.allocdtl_inv_grp = allocdtl_inv_grp;
	}

	public String getAllocdtl_blk_flg() {
		return allocdtl_blk_flg;
	}

	public void setAllocdtl_blk_flg(String allocdtl_blk_flg) {
		this.allocdtl_blk_flg = allocdtl_blk_flg;
	}

	public BigDecimal getAllocdtl_phy_qty() {
		return allocdtl_phy_qty;
	}

	public void setAllocdtl_phy_qty(BigDecimal allocdtl_phy_qty) {
		this.allocdtl_phy_qty = allocdtl_phy_qty;
	}

	public String getAllocdtl_ins_usr_id() {
		return allocdtl_ins_usr_id;
	}

	public void setAllocdtl_ins_usr_id(String allocdtl_ins_usr_id) {
		this.allocdtl_ins_usr_id = allocdtl_ins_usr_id;
	}

	public String getAllocdtl_mod_usr_id() {
		return allocdtl_mod_usr_id;
	}

	public void setAllocdtl_mod_usr_id(String allocdtl_mod_usr_id) {
		this.allocdtl_mod_usr_id = allocdtl_mod_usr_id;
	}

	public Date getAllocdtl_mod_dt() {
		return allocdtl_mod_dt;
	}

	public void setAllocdtl_mod_dt(Date allocdtl_mod_dt) {
		this.allocdtl_mod_dt = allocdtl_mod_dt;
	}

	public String getAllocdtl_ins_ip_add() {
		return allocdtl_ins_ip_add;
	}

	public void setAllocdtl_ins_ip_add(String allocdtl_ins_ip_add) {
		this.allocdtl_ins_ip_add = allocdtl_ins_ip_add;
	}

	public String getAllocdtl_mod_ip_add() {
		return allocdtl_mod_ip_add;
	}

	public void setAllocdtl_mod_ip_add(String allocdtl_mod_ip_add) {
		this.allocdtl_mod_ip_add = allocdtl_mod_ip_add;
	}

	public Character getAllocdtl_status() {
		return allocdtl_status;
	}

	public void setAllocdtl_status(Character allocdtl_status) {
		this.allocdtl_status = allocdtl_status;
	}

	public void setAlloc_Header(Alloc_Header alloc_Header) {
		this.alloc_Header = alloc_Header;
	}

	public Alloc_Header getAlloc_Header() {
		return alloc_Header;
	}

	public Character getAllocdtl_appr_status() {
		return allocdtl_appr_status;
	}

	public void setAllocdtl_appr_status(Character allocdtl_appr_status) {
		this.allocdtl_appr_status = allocdtl_appr_status;
	}

}
