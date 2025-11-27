package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "BLK_STK_REQ_PRODUCTS")
public class BlkStkReqProducts {

	
	@Column(name = "BLK_STK_REQ_ID")
	private Long blk_stk_req_id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "BLK_STK_REQ_DATE")
	private Date blk_stk_req_date;
	
	@Column(name = "BLK_STK_REQ_NO")
	private String blk_stk_req_no;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BLK_PRD_DTL_ID")
	private Long blk_prd_dtl_id;
	
	@Column(name = "PROD_ID")
	private Long prod_id;
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	@Column(name = "ERP_PROD_CD")
	private String erp_prod_cd;
	@Column(name = "PROD_NAME")
	private String prod_name;
	
	@Column(name = "STD_QTY")
	private BigDecimal std_qty;
	
	@Column(name = "PRD_ins_usr_id")
	private String prd_ins_usr_id;
	
	@Column(name = "PRD_mod_usr_id")
	private String prd_mod_usr_id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "PRD_ins_dt")
	private Date prd_ins_dt;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "PRD_mod_dt")
	private Date prd_mod_dt;
	
	@Column(name = "PRD_ins_ip_add")
	private String prd_ins_ip_add;
	
	@Column(name = "PRD_mod_ip_add")
	private String prd_mod_ip_add;
	
	@Column(name = "PRD_status")
	private String prd_status;
	
	
	@Transient private String smp_prod_type;
	@Transient private String sa_group_name;
	@Transient private Long smp_prod_type_id;
	@Transient private Long stk_qty;
	
	

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSa_group_name() {
		return sa_group_name;
	}

	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}

	public Long getSmp_prod_type_id() {
		return smp_prod_type_id;
	}

	public void setSmp_prod_type_id(Long smp_prod_type_id) {
		this.smp_prod_type_id = smp_prod_type_id;
	}

	public Long getStk_qty() {
		return stk_qty;
	}

	public void setStk_qty(Long stk_qty) {
		this.stk_qty = stk_qty;
	}

	public Long getBlk_stk_req_id() {
		return blk_stk_req_id;
	}

	public void setBlk_stk_req_id(Long blk_stk_req_id) {
		this.blk_stk_req_id = blk_stk_req_id;
	}

	public Date getBlk_stk_req_date() {
		return blk_stk_req_date;
	}

	public void setBlk_stk_req_date(Date blk_stk_req_date) {
		this.blk_stk_req_date = blk_stk_req_date;
	}

	public String getBlk_stk_req_no() {
		return blk_stk_req_no;
	}

	public void setBlk_stk_req_no(String blk_stk_req_no) {
		this.blk_stk_req_no = blk_stk_req_no;
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

	public Long getBlk_prd_dtl_id() {
		return blk_prd_dtl_id;
	}

	public void setBlk_prd_dtl_id(Long blk_prd_dtl_id) {
		this.blk_prd_dtl_id = blk_prd_dtl_id;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getErp_prod_cd() {
		return erp_prod_cd;
	}

	public void setErp_prod_cd(String erp_prod_cd) {
		this.erp_prod_cd = erp_prod_cd;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public BigDecimal getStd_qty() {
		return std_qty;
	}

	public void setStd_qty(BigDecimal std_qty) {
		this.std_qty = std_qty;
	}

	public String getPrd_ins_usr_id() {
		return prd_ins_usr_id;
	}

	public void setPrd_ins_usr_id(String prd_ins_usr_id) {
		this.prd_ins_usr_id = prd_ins_usr_id;
	}

	public String getPrd_mod_usr_id() {
		return prd_mod_usr_id;
	}

	public void setPrd_mod_usr_id(String prd_mod_usr_id) {
		this.prd_mod_usr_id = prd_mod_usr_id;
	}

	public Date getPrd_ins_dt() {
		return prd_ins_dt;
	}

	public void setPrd_ins_dt(Date prd_ins_dt) {
		this.prd_ins_dt = prd_ins_dt;
	}

	public Date getPrd_mod_dt() {
		return prd_mod_dt;
	}

	public void setPrd_mod_dt(Date prd_mod_dt) {
		this.prd_mod_dt = prd_mod_dt;
	}

	public String getPrd_ins_ip_add() {
		return prd_ins_ip_add;
	}

	public void setPrd_ins_ip_add(String prd_ins_ip_add) {
		this.prd_ins_ip_add = prd_ins_ip_add;
	}

	public String getPrd_mod_ip_add() {
		return prd_mod_ip_add;
	}

	public void setPrd_mod_ip_add(String prd_mod_ip_add) {
		this.prd_mod_ip_add = prd_mod_ip_add;
	}

	public String getPrd_status() {
		return prd_status;
	}

	public void setPrd_status(String prd_status) {
		this.prd_status = prd_status;
	}
	
	
}
