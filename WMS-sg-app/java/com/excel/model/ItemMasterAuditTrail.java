package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "ItemMasterAuditTrail")
@NamedStoredProcedureQuery(name = "ItemMasterAuditTrail", 
procedureName = "DOWNLOAD_ITEMMASTER_AUDITTRAIL",
parameters = {
		@StoredProcedureParameter(name = "Divid", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "SubCompany", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "Brand" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "SmpProdId" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "SmpProdtype" , mode = ParameterMode.IN, type = String.class),

}, resultClasses = ItemMasterAuditTrail.class)
public class ItemMasterAuditTrail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROW")
	private Long row;

	@Column(name = "SMP_PROD_ID")
	private Long smp_prod_id;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "SMP_COMPANY")
	private String smp_company;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "SMP_PACK_ID")
	private String smp_pack_id;
	
	@Column(name = "PACK_DISP_NM")
	private String pack_disp_nm;
	
	@Column(name = "SMP_ALTER_NAME")
	private String smp_alter_name;

	
	@Column(name = "SMP_LAUNCH_DT_DISP")
	private String smp_launch_dt_disp;
	

	
	@Column(name = "SMP_DISCONT_DT_DISP")
	private String smp_discont_dt_disp;
	
	@Column(name = "SMP_INV_GRP_ID")
	private String smp_inv_grp_id;
	
	@Column(name = "SMP_UOM_ID")
	private String smp_uom_id;
	
	@Column(name = "UOM_DISP_NM")
	private String uom_disp_nm;
	
	@Column(name = "SMP_FORM_ID")
	private String smp_form_id;
	
	@Column(name = "form")
	private String form;
	
	@Column(name = "SMP_SCH_IND")
	private String smp_sch_ind;
	
	@Column(name = "SMP_DIV_RQ_IND")
	private String smp_div_rq_ind;
	
	@Column(name = "SMP_EXPIRY_RQ_IND")
	private String smp_expiry_rq_ind;
	
	@Column(name = "SMP_BATCH_RQ_IND")
	private String smp_batch_rq_ind;
	
	@Column(name = "SMP_ALLOC_MULTIPLES")
	private String smp_alloc_multiples;
	 
	@Column(name = "SMP_ABC_IND")
	private String smp_abc_ind;
	
	@Column(name = "SMP_STOCK_DAYS")
	private String smp_stock_days;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name = "SMP_SAMPLING_TYPE")
	private String smp_sampling_type;
	
	@Column(name = "SMP_SHELF_LIFE")
	private String smp_shelf_life;
	
	@Column(name = "SMP_MFG_LOC_ID")
	private String smp_mfg_loc_id;
	
	@Column(name = "SMP_SHORT_EXPIRY_DAYS")
	private String smp_short_expiry_days;
	
	@Column(name = "SMP_PROD_BATCH_SIZE")
	private String smp_prod_batch_size;
	
	@Column(name = "SMP_ERP_PROD_CD")
	private String smp_erp_prod_cd;
	
	@Column(name = "SMP_SA_PROD_GROUP")
	private String smp_sa_prod_group;
	
	@Column(name = "SA_GROUP_NAME")
	private String sa_group_name;
	
	@Column(name = "SMP_THP_GRP_ID")
	private String smp_thp_grp_id;
	
	@Column(name = "SMP_THP_SGRP_ID")
	private String smp_thp_sgrp_id;
	
	@Column(name = "SMP_MOLE_GRP_ID")
	private String smp_mole_grp_id;
	
	@Column(name = "SMP_MOLE_SGRP_ID")
	private String smp_mole_sgrp_id;
	
	@Column(name = "SMP_PMT_GRP_ID")
	private String smp_pmt_grp_id;
	
	@Column(name = "SMP_PMT_SGRP_ID")
	private String smp_pmt_sgrp_id;
	
	@Column(name = "SMP_QTY_SHIPPER")
	private String smp_qty_shipper;
	
	@Column(name = "SMP_QTY_BOX")
	private String smp_qty_box;
	
	@Column(name = "SMP_QTY_STRIP")
	private String smp_qty_strip;
	
	@Column(name = "SMP_MAX_ALLOC_QTY")
	private BigDecimal smp_max_alloc_qty;
	
	@Column(name = "SMP_MIN_ALLOC_QTY")
	private BigDecimal smp_min_alloc_qty;
	
	@Column(name = "SMP_STD_ALLOC_QTY")
	private BigDecimal smp_std_alloc_qty;
	
	@Column(name = "SMP_SHIPPER_VOL")
	private String smp_shipper_vol;
	
	@Column(name = "SMP_SHIPPER_NWT")
	private String smp_shipper_nwt;
	
	@Column(name = "SMP_SHIPPER_GWT")
	private String smp_shipper_gwt;
	
	@Column(name = "SMP_EXCLUDE_LOC")
	private String smp_exclude_loc;
	
	@Column(name = "SMP_EXCLUDE_PARTY")
	private String smp_exclude_party;
	
	@Column(name = "SMP_SPEC_DIV_IND")
	private String smp_spec_div_ind;
	
	@Column(name = "SMP_COG_RATE")
	private BigDecimal smp_cog_rate;
	
	@Column(name = "SMP_COG_EXE_RATE")
	private BigDecimal smp_cog_exe_rate;
	
	@Column(name = "SMP_RATE")
	private BigDecimal smp_rate;
	
	@Column(name = "SMP_COSTING_RATE")
	private BigDecimal smp_costing_rate;
	
	@Column(name = "SMP_MKTG_RATE")
	private BigDecimal smp_mktg_rate;
	
	@Column(name = "SMP_NRV")
	private BigDecimal smp_nrv;
	
	@Column(name = "SMP_DISPLAY_RATE")
	private BigDecimal smp_display_rate;
	
	@Column(name = "SMP_STD_DIV_ID")
	private String smp_std_div_id;
	
	@Column(name = "division")
	private String division;
	
	@Column(name = "SMP_status")
	private String smp_status;
	
	@Column(name = "SMP_SubComp_id")
	private String smp_subcomp_id;
	
	@Column(name = "SubComp_Nm")
	private String subcomp_nm;
	
	@Column(name = "SMP_INS_USR_ID")
	private String smp_ins_usr_id;
	
	@Column(name = "INS_USER_NAME")
	private String ins_user_name;
	
	@Column(name = "SMP_INS_DT")
	private String smp_ins_dt;
	
	@Column(name = "SMP_MOD_USR_ID")
	private String smp_mod_usr_id;
	
	@Column(name = "MOD_USER_NAME")
	private String mod_user_name;
	
	@Column(name = "SMP_MOD_DT")
	private String smp_mod_dt;
	
	@Column(name = "CURR_STATUS")
	private String curr_status;

	@Column(name = "SMP_ins_ip_add")
	private String smp_ins_ip_add;

	@Column(name = "SMP_mod_ip_add")
	private String smp_mod_ip_add;
	
	@Column(name="PROD_FCODE")
	private String prod_fcode;
	
	@Column(name="GCMA_NUMBER")
	private String gcma_number;
	
	@Column(name="GCMA_EXPIRY_DT")
	private String gcma_expiry_dt;
	
	
	
	public String getProd_fcode() {
		return prod_fcode;
	}

	public void setProd_fcode(String prod_fcode) {
		this.prod_fcode = prod_fcode;
	}

	public String getGcma_number() {
		return gcma_number;
	}

	public void setGcma_number(String gcma_number) {
		this.gcma_number = gcma_number;
	}


	public String getGcma_expiry_dt() {
		return gcma_expiry_dt;
	}

	public void setGcma_expiry_dt(String gcma_expiry_dt) {
		this.gcma_expiry_dt = gcma_expiry_dt;
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

	public String getPack_disp_nm() {
		return pack_disp_nm;
	}

	public void setPack_disp_nm(String pack_disp_nm) {
		this.pack_disp_nm = pack_disp_nm;
	}

	public Long getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(Long smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getSmp_company() {
		return smp_company;
	}

	public void setSmp_company(String smp_company) {
		this.smp_company = smp_company;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getSmp_pack_id() {
		return smp_pack_id;
	}

	public void setSmp_pack_id(String smp_pack_id) {
		this.smp_pack_id = smp_pack_id;
	}

	public String getSmp_alter_name() {
		return smp_alter_name;
	}

	public void setSmp_alter_name(String smp_alter_name) {
		this.smp_alter_name = smp_alter_name;
	}



	public String getSmp_launch_dt_disp() {
		return smp_launch_dt_disp;
	}

	public void setSmp_launch_dt_disp(String smp_launch_dt_disp) {
		this.smp_launch_dt_disp = smp_launch_dt_disp;
	}



	public String getSmp_discont_dt_disp() {
		return smp_discont_dt_disp;
	}

	public void setSmp_discont_dt_disp(String smp_discont_dt_disp) {
		this.smp_discont_dt_disp = smp_discont_dt_disp;
	}

	public String getSmp_inv_grp_id() {
		return smp_inv_grp_id;
	}

	public void setSmp_inv_grp_id(String smp_inv_grp_id) {
		this.smp_inv_grp_id = smp_inv_grp_id;
	}

	public String getSmp_uom_id() {
		return smp_uom_id;
	}

	public void setSmp_uom_id(String smp_uom_id) {
		this.smp_uom_id = smp_uom_id;
	}

	public String getUom_disp_nm() {
		return uom_disp_nm;
	}

	public void setUom_disp_nm(String uom_disp_nm) {
		this.uom_disp_nm = uom_disp_nm;
	}

	public String getSmp_form_id() {
		return smp_form_id;
	}

	public void setSmp_form_id(String smp_form_id) {
		this.smp_form_id = smp_form_id;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getSmp_sch_ind() {
		return smp_sch_ind;
	}

	public void setSmp_sch_ind(String smp_sch_ind) {
		this.smp_sch_ind = smp_sch_ind;
	}

	public String getSmp_div_rq_ind() {
		return smp_div_rq_ind;
	}

	public void setSmp_div_rq_ind(String smp_div_rq_ind) {
		this.smp_div_rq_ind = smp_div_rq_ind;
	}

	public String getSmp_expiry_rq_ind() {
		return smp_expiry_rq_ind;
	}

	public void setSmp_expiry_rq_ind(String smp_expiry_rq_ind) {
		this.smp_expiry_rq_ind = smp_expiry_rq_ind;
	}

	public String getSmp_batch_rq_ind() {
		return smp_batch_rq_ind;
	}

	public void setSmp_batch_rq_ind(String smp_batch_rq_ind) {
		this.smp_batch_rq_ind = smp_batch_rq_ind;
	}

	public String getSmp_alloc_multiples() {
		return smp_alloc_multiples;
	}

	public void setSmp_alloc_multiples(String smp_alloc_multiples) {
		this.smp_alloc_multiples = smp_alloc_multiples;
	}

	public String getSmp_abc_ind() {
		return smp_abc_ind;
	}

	public void setSmp_abc_ind(String smp_abc_ind) {
		this.smp_abc_ind = smp_abc_ind;
	}

	public String getSmp_stock_days() {
		return smp_stock_days;
	}

	public void setSmp_stock_days(String smp_stock_days) {
		this.smp_stock_days = smp_stock_days;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSmp_sampling_type() {
		return smp_sampling_type;
	}

	public void setSmp_sampling_type(String smp_sampling_type) {
		this.smp_sampling_type = smp_sampling_type;
	}

	public String getSmp_shelf_life() {
		return smp_shelf_life;
	}

	public void setSmp_shelf_life(String smp_shelf_life) {
		this.smp_shelf_life = smp_shelf_life;
	}

	public String getSmp_mfg_loc_id() {
		return smp_mfg_loc_id;
	}

	public void setSmp_mfg_loc_id(String smp_mfg_loc_id) {
		this.smp_mfg_loc_id = smp_mfg_loc_id;
	}

	public String getSmp_short_expiry_days() {
		return smp_short_expiry_days;
	}

	public void setSmp_short_expiry_days(String smp_short_expiry_days) {
		this.smp_short_expiry_days = smp_short_expiry_days;
	}

	public String getSmp_prod_batch_size() {
		return smp_prod_batch_size;
	}

	public void setSmp_prod_batch_size(String smp_prod_batch_size) {
		this.smp_prod_batch_size = smp_prod_batch_size;
	}

	public String getSmp_erp_prod_cd() {
		return smp_erp_prod_cd;
	}

	public void setSmp_erp_prod_cd(String smp_erp_prod_cd) {
		this.smp_erp_prod_cd = smp_erp_prod_cd;
	}

	public String getSmp_sa_prod_group() {
		return smp_sa_prod_group;
	}

	public void setSmp_sa_prod_group(String smp_sa_prod_group) {
		this.smp_sa_prod_group = smp_sa_prod_group;
	}

	public String getSa_group_name() {
		return sa_group_name;
	}

	public void setSa_group_name(String sa_group_name) {
		this.sa_group_name = sa_group_name;
	}

	public String getSmp_thp_grp_id() {
		return smp_thp_grp_id;
	}

	public void setSmp_thp_grp_id(String smp_thp_grp_id) {
		this.smp_thp_grp_id = smp_thp_grp_id;
	}

	public String getSmp_thp_sgrp_id() {
		return smp_thp_sgrp_id;
	}

	public void setSmp_thp_sgrp_id(String smp_thp_sgrp_id) {
		this.smp_thp_sgrp_id = smp_thp_sgrp_id;
	}

	public String getSmp_mole_grp_id() {
		return smp_mole_grp_id;
	}

	public void setSmp_mole_grp_id(String smp_mole_grp_id) {
		this.smp_mole_grp_id = smp_mole_grp_id;
	}

	public String getSmp_mole_sgrp_id() {
		return smp_mole_sgrp_id;
	}

	public void setSmp_mole_sgrp_id(String smp_mole_sgrp_id) {
		this.smp_mole_sgrp_id = smp_mole_sgrp_id;
	}

	public String getSmp_pmt_grp_id() {
		return smp_pmt_grp_id;
	}

	public void setSmp_pmt_grp_id(String smp_pmt_grp_id) {
		this.smp_pmt_grp_id = smp_pmt_grp_id;
	}

	public String getSmp_pmt_sgrp_id() {
		return smp_pmt_sgrp_id;
	}

	public void setSmp_pmt_sgrp_id(String smp_pmt_sgrp_id) {
		this.smp_pmt_sgrp_id = smp_pmt_sgrp_id;
	}

	public String getSmp_qty_shipper() {
		return smp_qty_shipper;
	}

	public void setSmp_qty_shipper(String smp_qty_shipper) {
		this.smp_qty_shipper = smp_qty_shipper;
	}

	public String getSmp_qty_box() {
		return smp_qty_box;
	}

	public void setSmp_qty_box(String smp_qty_box) {
		this.smp_qty_box = smp_qty_box;
	}

	public String getSmp_qty_strip() {
		return smp_qty_strip;
	}

	public void setSmp_qty_strip(String smp_qty_strip) {
		this.smp_qty_strip = smp_qty_strip;
	}

	public BigDecimal getSmp_max_alloc_qty() {
		return smp_max_alloc_qty;
	}

	public void setSmp_max_alloc_qty(BigDecimal smp_max_alloc_qty) {
		this.smp_max_alloc_qty = smp_max_alloc_qty;
	}

	public BigDecimal getSmp_min_alloc_qty() {
		return smp_min_alloc_qty;
	}

	public void setSmp_min_alloc_qty(BigDecimal smp_min_alloc_qty) {
		this.smp_min_alloc_qty = smp_min_alloc_qty;
	}

	public BigDecimal getSmp_std_alloc_qty() {
		return smp_std_alloc_qty;
	}

	public void setSmp_std_alloc_qty(BigDecimal smp_std_alloc_qty) {
		this.smp_std_alloc_qty = smp_std_alloc_qty;
	}

	public String getSmp_shipper_vol() {
		return smp_shipper_vol;
	}

	public void setSmp_shipper_vol(String smp_shipper_vol) {
		this.smp_shipper_vol = smp_shipper_vol;
	}

	public String getSmp_shipper_nwt() {
		return smp_shipper_nwt;
	}

	public void setSmp_shipper_nwt(String smp_shipper_nwt) {
		this.smp_shipper_nwt = smp_shipper_nwt;
	}

	public String getSmp_shipper_gwt() {
		return smp_shipper_gwt;
	}

	public void setSmp_shipper_gwt(String smp_shipper_gwt) {
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

	public BigDecimal getSmp_display_rate() {
		return smp_display_rate;
	}

	public void setSmp_display_rate(BigDecimal smp_display_rate) {
		this.smp_display_rate = smp_display_rate;
	}

	public String getSmp_std_div_id() {
		return smp_std_div_id;
	}

	public void setSmp_std_div_id(String smp_std_div_id) {
		this.smp_std_div_id = smp_std_div_id;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSmp_status() {
		return smp_status;
	}

	public void setSmp_status(String smp_status) {
		this.smp_status = smp_status;
	}

	public String getSmp_subcomp_id() {
		return smp_subcomp_id;
	}

	public void setSmp_subcomp_id(String smp_subcomp_id) {
		this.smp_subcomp_id = smp_subcomp_id;
	}

	public String getSubcomp_nm() {
		return subcomp_nm;
	}

	public void setSubcomp_nm(String subcomp_nm) {
		this.subcomp_nm = subcomp_nm;
	}

	public String getSmp_ins_usr_id() {
		return smp_ins_usr_id;
	}

	public void setSmp_ins_usr_id(String smp_ins_usr_id) {
		this.smp_ins_usr_id = smp_ins_usr_id;
	}

	public String getIns_user_name() {
		return ins_user_name;
	}

	public void setIns_user_name(String ins_user_name) {
		this.ins_user_name = ins_user_name;
	}

	public String getSmp_ins_dt() {
		return smp_ins_dt;
	}

	public void setSmp_ins_dt(String smp_ins_dt) {
		this.smp_ins_dt = smp_ins_dt;
	}

	public String getSmp_mod_usr_id() {
		return smp_mod_usr_id;
	}

	public void setSmp_mod_usr_id(String smp_mod_usr_id) {
		this.smp_mod_usr_id = smp_mod_usr_id;
	}

	public String getMod_user_name() {
		return mod_user_name;
	}

	public void setMod_user_name(String mod_user_name) {
		this.mod_user_name = mod_user_name;
	}

	public String getSmp_mod_dt() {
		return smp_mod_dt;
	}

	public void setSmp_mod_dt(String smp_mod_dt) {
		this.smp_mod_dt = smp_mod_dt;
	}

	public String getCurr_status() {
		return curr_status;
	}

	public void setCurr_status(String curr_status) {
		this.curr_status = curr_status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}
	
 

}
