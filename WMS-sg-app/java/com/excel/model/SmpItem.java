package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import java.util.Date;

import java.io.Serializable;

@Entity
@Table(name="SMPITEM")
@DynamicUpdate(value=true)
public class SmpItem implements Serializable {
	
	private static final long serialVersionUID = -6851981530966264585L;

	@Id
	@Column(name="SMP_PROD_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long smp_prod_id;
	
	@Column(name="SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name="SMP_COMPANY")
	private String smp_company;
	
	@Column(name="SMP_SubComp_id")
	private String smp_subcomp_id;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name="SMP_PACK_ID")
	private Long smp_pack_id;
	
	@Column(name="SMP_ALTER_NAME")
	private String alter_name;
	
	@Column(name="SMP_DISCONT_DT")
	private Date smp_discont_dt;
	
	@Column(name="SMP_LAUNCH_DT")
	private Date launch_dt;
	
	@Column(name="SMP_INV_GRP_ID")
	private Long inv_grp_id;
	
	@Column(name="SMP_UOM_ID")
	private Long uom_id;
	
	@Column(name="SMP_FORM_ID")
	private Long form_id;
	
	@Column(name="SMP_SCH_IND")
	private String smp_sch_ind;
	
	@Column(name="SMP_DIV_RQ_IND")
	private String div_rq_ind;
	
	@Column(name="SMP_EXPIRY_RQ_IND")
	private String expiry_rq_ind;
	
	@Column(name="SMP_BATCH_RQ_IND")
	private String batch_rq_ind;
	
	@Column(name="SMP_ALLOC_MULTIPLES")
	private String alloc_multiples;
	
	@Column(name="SMP_ABC_IND")
	private String smp_abc_ind;
	
	@Column(name="SMP_STOCK_DAYS")
	private Long smp_stock_days;
	
	@Column(name="SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name="SMP_SAMPLING_TYPE")
	private String sampling_type;
	
	@Column(name="SMP_SHELF_LIFE")
	private Long smp_shelf_life;
	
	@Column(name="SMP_MFG_LOC_ID")
	private Long smp_mfg_loc_id;
	
	@Column(name="SMP_SHORT_EXPIRY_DAYS")
	private Long smp_short_expiry_days;
	
	@Column(name="SMP_PROD_BATCH_SIZE")
	private BigDecimal smp_prod_batch_size;
	
	@Column(name="SMP_ERP_PROD_CD")
	private String erp_prod_cd;

	@Column(name="SMP_SA_PROD_GROUP")
	private Long smp_sa_prod_group;
	
	@Column(name="SMP_THP_GRP_ID")
	private Long smp_thp_grp_id;
	
	@Column(name="SMP_THP_SGRP_ID")
	private Long smp_thp_sgrp_id;
	
	@Column(name="SMP_MOLE_GRP_ID")
	private Long smp_mole_grp_id;
	
	@Column(name="SMP_MOLE_SGRP_ID")
	private Long smp_mole_sgrp_id;
	
	@Column(name="SMP_PMT_GRP_ID")
	private Long smp_pmt_grp_id;
	
	@Column(name="SMP_PMT_SGRP_ID")
	private Long smp_pmt_sgrp_id;
	
	@Column(name="SMP_QTY_SHIPPER")
	private BigDecimal shipper_qty;
	
	@Column(name="SMP_QTY_BOX")
	private BigDecimal smp_qty_box;
	
	@Column(name="SMP_QTY_STRIP")
	private BigDecimal smp_qty_strip;
	
	@Column(name="SMP_MAX_ALLOC_QTY")
	private BigDecimal max_alloc_qty;
	
	@Column(name="SMP_MIN_ALLOC_QTY")
	private BigDecimal min_alloc_qty;
	
	@Column(name="SMP_STD_ALLOC_QTY")
	private BigDecimal std_alloc_qty;
	
	@Column(name="SMP_SHIPPER_VOL")
	private BigDecimal smp_shipper_vol;
	
	@Column(name="SMP_SHIPPER_NWT")
	private BigDecimal smp_shipper_nwt;
	
	@Column(name="SMP_SHIPPER_GWT")
	private BigDecimal smp_shipper_gwt;
	
	@Column(name="SMP_EXCLUDE_LOC")
	private String smp_exclude_loc;
	
	@Column(name="SMP_EXCLUDE_PARTY")
	private String smp_exclude_party;
	
	@Column(name="SMP_SPEC_DIV_IND")
	private String smp_spec_div_ind;
	
	@Column(name="SMP_COG_RATE")
	private BigDecimal smp_cog_rate;
	
	@Column(name="SMP_COG_EXE_RATE")
	private BigDecimal smp_cog_exe_rate;
	
	@Column(name="SMP_RATE")
	private BigDecimal smp_rate;
	
	@Column(name="SMP_COSTING_RATE")
	private BigDecimal smp_costing_rate;
	
	@Column(name="SMP_MKTG_RATE")
	private BigDecimal smp_mktg_rate;
	
	@Column(name="SMP_NRV")
	private BigDecimal smp_nrv;
	
	@Column(name="SMP_DISPLAY_RATE")
	private BigDecimal display_rate;
	
	@Column(name="SMP_STD_DIV_ID")
	private Long smp_std_div_id;
	
	@Column(name="SMP_ins_usr_id")
	private String smp_ins_usr_id;
	
	@Column(name="SMP_mod_usr_id")
	private String smp_mod_usr_id;
	
	@Column(name="SMP_ins_dt")
	private Date smp_ins_dt;
	
	@Column(name="SMP_mod_dt")
	private Date smp_mod_dt;
	
	@Column(name="SMP_ins_ip_add")
	private String smp_ins_ip_add;
	
	@Column(name="SMP_mod_ip_add")
	private String smp_mod_ip_add;
	
	@Column(name="SMP_status")
	private String smp_status;
	
	@Column(name="SMP_PROD_NAME_OLD")
	private String old_prod_name;
	
//	@ManyToOne
//	@JoinColumn(name="SMP_SubComp_id")
//	private Sub_Company subCompany;
	
//	private String subCompany;
	
	
	
//	@Column(name="SMP_SUBCOMP_ID")
//	private Long smp_subcomp_id;
	
	@Column(name="INV_GRP_ID")
	private String invoice_grp_id;
	
	@Column(name="STORAGE_TYPE")
	private String storage_type;
	
	@Column(name="STORAGE_TYPE_ID")
	private Long storage_type_id;
	
	@Column(name="SMP_PROD_TYPE_ID")
	private Long smp_prod_type_id;

	@Column(name="HSN_CODE")
	private String hsn_code;
	
	@Column(name="MARGIN_PERC")
	private BigDecimal margin_perc;
	
	@Column(name="PROMO_EXPIRY_ACCEPT_IND")
	private String promo_expiry_accept_ind;
	
	@Column(name="PROD_SUB_TYPE_ID")
	private int prod_sub_type_id;
	
	@Column(name="smp_supply_type_id")
	private int smp_supply_type_id;
	
	//GCMA Detail
	@Column(name="GCMA_REQ_IND")
	private String gcma_req_ind;
	
	@Column(name="GCMA_NUMBER")
	private String gcma_number;
	
	@Column(name="GCMA_APROVAL_DT")
	private Date gcma_aproval_dt;
	
	@Column(name="GCMA_EXPIRY_DT")
	private Date gcma_expiry_dt;
	
	@Column(name="GCMA_NOT_REQ_REASON")
	private Long gcma_not_req_reason;
	
	@Transient private String sa_group_name;
	@Transient private Long stockQty;

	@Transient private String loc_godwadd1;
	@Transient private BigDecimal autodisp_pending_qty;
	
	
	
	public String getLoc_godwadd1() {
		return loc_godwadd1;
	}

	public void setLoc_godwadd1(String loc_godwadd1) {
		this.loc_godwadd1 = loc_godwadd1;
	}

	public BigDecimal getAutodisp_pending_qty() {
		return autodisp_pending_qty;
	}

	public void setAutodisp_pending_qty(BigDecimal autodisp_pending_qty) {
		this.autodisp_pending_qty = autodisp_pending_qty;
	}

	public String getSa_group_name() {
		return sa_group_name;
	}

	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}

	public Long getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getSmp_company() {
		return smp_company;
	}

	public void setSmp_company(String smp_company) {
		this.smp_company = smp_company;
	}

	public String getSmp_subcomp_id() {
		return smp_subcomp_id;
	}

	public void setSmp_subcomp_id(String smp_subcomp_id) {
		this.smp_subcomp_id = smp_subcomp_id;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public Long getSmp_pack_id() {
		return smp_pack_id;
	}

	public void setSmp_pack_id(Long smp_pack_id) {
		this.smp_pack_id = smp_pack_id;
	}

	public String getAlter_name() {
		return alter_name;
	}

	public void setAlter_name(String alter_name) {
		this.alter_name = alter_name;
	}

	public Date getSmp_discont_dt() {
		return smp_discont_dt;
	}

	public void setSmp_discont_dt(Date smp_discont_dt) {
		this.smp_discont_dt = smp_discont_dt;
	}

	public Date getLaunch_dt() {
		return launch_dt;
	}

	public void setLaunch_dt(Date launch_dt) {
		this.launch_dt = launch_dt;
	}

	public Long getInv_grp_id() {
		return inv_grp_id;
	}

	public void setInv_grp_id(Long inv_grp_id) {
		this.inv_grp_id = inv_grp_id;
	}

	public Long getUom_id() {
		return uom_id;
	}

	public void setUom_id(Long uom_id) {
		this.uom_id = uom_id;
	}

	public Long getForm_id() {
		return form_id;
	}

	public void setForm_id(Long form_id) {
		this.form_id = form_id;
	}

	public String getSmp_sch_ind() {
		return smp_sch_ind;
	}

	public void setSmp_sch_ind(String smp_sch_ind) {
		this.smp_sch_ind = smp_sch_ind;
	}

	public String getDiv_rq_ind() {
		return div_rq_ind;
	}

	public void setDiv_rq_ind(String div_rq_ind) {
		this.div_rq_ind = div_rq_ind;
	}

	public String getExpiry_rq_ind() {
		return expiry_rq_ind;
	}

	public void setExpiry_rq_ind(String expiry_rq_ind) {
		this.expiry_rq_ind = expiry_rq_ind;
	}

	public String getBatch_rq_ind() {
		return batch_rq_ind;
	}

	public void setBatch_rq_ind(String batch_rq_ind) {
		this.batch_rq_ind = batch_rq_ind;
	}

	public String getAlloc_multiples() {
		return alloc_multiples;
	}

	public void setAlloc_multiples(String alloc_multiples) {
		this.alloc_multiples = alloc_multiples;
	}

	public String getSmp_abc_ind() {
		return smp_abc_ind;
	}

	public void setSmp_abc_ind(String smp_abc_ind) {
		this.smp_abc_ind = smp_abc_ind;
	}

	public Long getSmp_stock_days() {
		return smp_stock_days;
	}

	public void setSmp_stock_days(Long smp_stock_days) {
		this.smp_stock_days = smp_stock_days;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSampling_type() {
		return sampling_type;
	}

	public void setSampling_type(String sampling_type) {
		this.sampling_type = sampling_type;
	}

	public Long getSmp_shelf_life() {
		return smp_shelf_life;
	}

	public void setSmp_shelf_life(Long smp_shelf_life) {
		this.smp_shelf_life = smp_shelf_life;
	}

	public Long getSmp_mfg_loc_id() {
		return smp_mfg_loc_id;
	}

	public void setSmp_mfg_loc_id(Long smp_mfg_loc_id) {
		this.smp_mfg_loc_id = smp_mfg_loc_id;
	}

	public Long getSmp_short_expiry_days() {
		return smp_short_expiry_days;
	}

	public void setSmp_short_expiry_days(Long smp_short_expiry_days) {
		this.smp_short_expiry_days = smp_short_expiry_days;
	}

	public BigDecimal getSmp_prod_batch_size() {
		return smp_prod_batch_size;
	}

	public void setSmp_prod_batch_size(BigDecimal smp_prod_batch_size) {
		this.smp_prod_batch_size = smp_prod_batch_size;
	}

	public Long getSmp_sa_prod_group() {
		return smp_sa_prod_group;
	}

	public void setSmp_sa_prod_group(Long smp_sa_prod_group) {
		this.smp_sa_prod_group = smp_sa_prod_group;
	}

	public Long getSmp_thp_grp_id() {
		return smp_thp_grp_id;
	}

	public void setSmp_thp_grp_id(Long smp_thp_grp_id) {
		this.smp_thp_grp_id = smp_thp_grp_id;
	}

	public Long getSmp_mole_grp_id() {
		return smp_mole_grp_id;
	}

	public void setSmp_mole_grp_id(Long smp_mole_grp_id) {
		this.smp_mole_grp_id = smp_mole_grp_id;
	}

	public Long getSmp_mole_sgrp_id() {
		return smp_mole_sgrp_id;
	}

	public void setSmp_mole_sgrp_id(Long smp_mole_sgrp_id) {
		this.smp_mole_sgrp_id = smp_mole_sgrp_id;
	}



	public Long getSmp_pmt_grp_id() {
		return smp_pmt_grp_id;
	}

	public void setSmp_pmt_grp_id(Long smp_pmt_grp_id) {
		this.smp_pmt_grp_id = smp_pmt_grp_id;
	}

	public Long getSmp_pmt_sgrp_id() {
		return smp_pmt_sgrp_id;
	}

	public void setSmp_pmt_sgrp_id(Long smp_pmt_sgrp_id) {
		this.smp_pmt_sgrp_id = smp_pmt_sgrp_id;
	}

	public BigDecimal getShipper_qty() {
		return shipper_qty;
	}

	public void setShipper_qty(BigDecimal shipper_qty) {
		this.shipper_qty = shipper_qty;
	}

	public BigDecimal getSmp_qty_box() {
		return smp_qty_box;
	}

	public void setSmp_qty_box(BigDecimal smp_qty_box) {
		this.smp_qty_box = smp_qty_box;
	}

	public BigDecimal getSmp_qty_strip() {
		return smp_qty_strip;
	}

	public void setSmp_qty_strip(BigDecimal smp_qty_strip) {
		this.smp_qty_strip = smp_qty_strip;
	}

	public BigDecimal getMax_alloc_qty() {
		return max_alloc_qty;
	}

	public void setMax_alloc_qty(BigDecimal max_alloc_qty) {
		this.max_alloc_qty = max_alloc_qty;
	}

	public BigDecimal getMin_alloc_qty() {
		return min_alloc_qty;
	}

	public void setMin_alloc_qty(BigDecimal min_alloc_qty) {
		this.min_alloc_qty = min_alloc_qty;
	}

	public BigDecimal getStd_alloc_qty() {
		return std_alloc_qty;
	}

	public void setStd_alloc_qty(BigDecimal std_alloc_qty) {
		this.std_alloc_qty = std_alloc_qty;
	}

	public BigDecimal getSmp_shipper_vol() {
		return smp_shipper_vol;
	}

	public void setSmp_shipper_vol(BigDecimal smp_shipper_vol) {
		this.smp_shipper_vol = smp_shipper_vol;
	}

	public BigDecimal getSmp_shipper_nwt() {
		return smp_shipper_nwt;
	}

	public void setSmp_shipper_nwt(BigDecimal smp_shipper_nwt) {
		this.smp_shipper_nwt = smp_shipper_nwt;
	}

	public BigDecimal getSmp_shipper_gwt() {
		return smp_shipper_gwt;
	}

	public void setSmp_shipper_gwt(BigDecimal smp_shipper_gwt) {
		this.smp_shipper_gwt = smp_shipper_gwt;
	}

	public String getSmp_exclude_loc() {
		return smp_exclude_loc;
	}

	public void setSmp_exclude_loc(String smp_exclude_loc) {
		this.smp_exclude_loc = smp_exclude_loc;
	}

	public String getSmp_exclude_party() {
		return smp_exclude_party;
	}

	public void setSmp_exclude_party(String smp_exclude_party) {
		this.smp_exclude_party = smp_exclude_party;
	}

	public String getSmp_spec_div_ind() {
		return smp_spec_div_ind;
	}

	public void setSmp_spec_div_ind(String smp_spec_div_ind) {
		this.smp_spec_div_ind = smp_spec_div_ind;
	}

	public BigDecimal getSmp_cog_rate() {
		return smp_cog_rate;
	}

	public void setSmp_cog_rate(BigDecimal smp_cog_rate) {
		this.smp_cog_rate = smp_cog_rate;
	}

	public BigDecimal getSmp_cog_exe_rate() {
		return smp_cog_exe_rate;
	}

	public void setSmp_cog_exe_rate(BigDecimal smp_cog_exe_rate) {
		this.smp_cog_exe_rate = smp_cog_exe_rate;
	}

	public BigDecimal getSmp_rate() {
		return smp_rate;
	}

	public void setSmp_rate(BigDecimal smp_rate) {
		this.smp_rate = smp_rate;
	}

	public BigDecimal getSmp_costing_rate() {
		return smp_costing_rate;
	}

	public void setSmp_costing_rate(BigDecimal smp_costing_rate) {
		this.smp_costing_rate = smp_costing_rate;
	}

	public BigDecimal getSmp_mktg_rate() {
		return smp_mktg_rate;
	}

	public void setSmp_mktg_rate(BigDecimal smp_mktg_rate) {
		this.smp_mktg_rate = smp_mktg_rate;
	}

	public BigDecimal getSmp_nrv() {
		return smp_nrv;
	}

	public void setSmp_nrv(BigDecimal smp_nrv) {
		this.smp_nrv = smp_nrv;
	}

	public BigDecimal getDisplay_rate() {
		return display_rate;
	}

	public void setDisplay_rate(BigDecimal display_rate) {
		this.display_rate = display_rate;
	}

	public Long getSmp_std_div_id() {
		return smp_std_div_id;
	}

	public void setSmp_std_div_id(Long smp_std_div_id) {
		this.smp_std_div_id = smp_std_div_id;
	}

	public String getSmp_ins_usr_id() {
		return smp_ins_usr_id;
	}

	public void setSmp_ins_usr_id(String smp_ins_usr_id) {
		this.smp_ins_usr_id = smp_ins_usr_id;
	}

	public String getSmp_mod_usr_id() {
		return smp_mod_usr_id;
	}

	public void setSmp_mod_usr_id(String smp_mod_usr_id) {
		this.smp_mod_usr_id = smp_mod_usr_id;
	}

	public Date getSmp_ins_dt() {
		return smp_ins_dt;
	}

	public void setSmp_ins_dt(Date smp_ins_dt) {
		this.smp_ins_dt = smp_ins_dt;
	}

	public Date getSmp_mod_dt() {
		return smp_mod_dt;
	}

	public void setSmp_mod_dt(Date smp_mod_dt) {
		this.smp_mod_dt = smp_mod_dt;
	}

	public String getSmp_ins_ip_add() {
		return smp_ins_ip_add;
	}

	public void setSmp_ins_ip_add(String smp_ins_ip_add) {
		this.smp_ins_ip_add = smp_ins_ip_add;
	}

	public String getSmp_mod_ip_add() {
		return smp_mod_ip_add;
	}

	public void setSmp_mod_ip_add(String smp_mod_ip_add) {
		this.smp_mod_ip_add = smp_mod_ip_add;
	}

	public String getSmp_status() {
		return smp_status;
	}

	public void setSmp_status(String smp_status) {
		this.smp_status = smp_status;
	}

	public String getOld_prod_name() {
		return old_prod_name;
	}

	public void setOld_prod_name(String old_prod_name) {
		this.old_prod_name = old_prod_name;
	}

//	public Sub_Company getSubCompany() {
//		return subCompany;
//	}
//
//	public void setSubCompany(Sub_Company subCompany) {
//		this.subCompany = subCompany;
//	}
	
	

	public String getInvoice_grp_id() {
		return invoice_grp_id;
	}

//	public String getSubCompany() {
//		return subCompany;
//	}
//
//	public void setSubCompany(String subCompany) {
//		this.subCompany = subCompany;
//	}

	public void setInvoice_grp_id(String invoice_grp_id) {
		this.invoice_grp_id = invoice_grp_id;
	}

	public String getStorage_type() {
		return storage_type;
	}

	public void setStorage_type(String storage_type) {
		this.storage_type = storage_type;
	}

	public Long getStorage_type_id() {
		return storage_type_id;
	}

	public void setStorage_type_id(Long storage_type_id) {
		this.storage_type_id = storage_type_id;
	}

	public Long getSmp_thp_sgrp_id() {
		return smp_thp_sgrp_id;
	}

	public void setSmp_thp_sgrp_id(Long smp_thp_sgrp_id) {
		this.smp_thp_sgrp_id = smp_thp_sgrp_id;
	}

	public String getErp_prod_cd() {
		return erp_prod_cd;
	}

	public void setErp_prod_cd(String erp_prod_cd) {
		this.erp_prod_cd = erp_prod_cd;
	}

	public Long getSmp_prod_type_id() {
		return smp_prod_type_id;
	}

	public void setSmp_prod_type_id(Long smp_prod_type_id) {
		this.smp_prod_type_id = smp_prod_type_id;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}

	public BigDecimal getMargin_perc() {
		return margin_perc;
	}

	public void setMargin_perc(BigDecimal margin_perc) {
		this.margin_perc = margin_perc;
	}

	public String getPromo_expiry_accept_ind() {
	    return promo_expiry_accept_ind;
	}

	public void setPromo_expiry_accept_ind(String promo_expiry_accept_ind) {
	    this.promo_expiry_accept_ind = promo_expiry_accept_ind;
	}
	
	
	
	public int getProd_sub_type_id() {
		return prod_sub_type_id;
	}

	public void setProd_sub_type_id(int prod_sub_type_id) {
		this.prod_sub_type_id = prod_sub_type_id;
	}

	public int getSmp_supply_type_id() {
		return smp_supply_type_id;
	}

	public void setSmp_supply_type_id(int smp_supply_type_id) {
		this.smp_supply_type_id = smp_supply_type_id;
	}
	//GCMA Detail
	public String getGcma_req_ind() {
		return gcma_req_ind;
	}

	public void setGcma_req_ind(String gcma_req_ind) {
		this.gcma_req_ind = gcma_req_ind;
	}

	public String getGcma_number() {
		return gcma_number;
	}

	public void setGcma_number(String gcma_number) {
		this.gcma_number = gcma_number;
	}
	public Date getGcma_aproval_dt() {
		return gcma_aproval_dt;
	}

	public void setGcma_aproval_dt(Date gcma_aproval_dt) {
		this.gcma_aproval_dt = gcma_aproval_dt;
	}

	public Date getGcma_expiry_dt() {
		return gcma_expiry_dt;
	}

	public void setGcma_expiry_dt(Date gcma_expiry_dt) {
		this.gcma_expiry_dt = gcma_expiry_dt;
	}
	
	
	

	public SmpItem(Long smp_std_div_id, BigDecimal display_rate) {
		super();
		this.display_rate = display_rate;
		this.smp_std_div_id = smp_std_div_id;
	}

	public SmpItem(Long smp_prod_id, Long smp_sa_prod_group) {
		super();
		this.smp_prod_id = smp_prod_id;
		this.smp_sa_prod_group = smp_sa_prod_group;
	}

	public SmpItem(Long smp_std_div_id, Long smp_prod_id,String smp_prod_cd,
			String erp_prod_cd,String smp_prod_name,String smp_prod_type,Long smp_prod_type_id,Long smp_sa_prod_group) {
		super();
		this.smp_std_div_id = smp_std_div_id;
		this.smp_prod_id = smp_prod_id;
		this.smp_prod_cd = smp_prod_cd;
		this.erp_prod_cd = erp_prod_cd;
		this.smp_prod_name = smp_prod_name;
		this.smp_prod_type = smp_prod_type;
		this.smp_prod_type_id = smp_prod_type_id;
		this.smp_sa_prod_group = smp_sa_prod_group;
	}
	
	public SmpItem() {
		super();
	}
	
	

	public SmpItem(Long smp_prod_id) {
		super();
		this.smp_prod_id = smp_prod_id;
	}

	public Long getGcma_not_req_reason() {
		return gcma_not_req_reason;
	}

	public void setGcma_not_req_reason(Long gcma_not_req_reason) {
		this.gcma_not_req_reason = gcma_not_req_reason;
	}

	public Long getStockQty() {
		return stockQty;
	}

	public void setStockQty(Long stockQty) {
		this.stockQty = stockQty;
	}
	
}
