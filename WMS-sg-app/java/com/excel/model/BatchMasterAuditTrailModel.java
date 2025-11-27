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
@Table(name = "BatchMasterAuditTrail")
@NamedStoredProcedureQuery(name = "BatchMasterAuditTrail", 
procedureName = "DOWNLOAD_BATCHMASTER_AUDITTRAIL",
parameters = {
		@StoredProcedureParameter(name = "SmpProdId", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "LocId", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "startdate" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "endDate" , mode = ParameterMode.IN, type = String.class),
		
}, resultClasses = BatchMasterAuditTrailModel.class)
public class BatchMasterAuditTrailModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROW")
	private Long row;

	@Column(name = "BATCH_ID")
	private Long batch_id;

	@Column(name = "BATCH_PROD_ID")
	private String batch_prod_id;

	@Column(name = "PRODUCT")
	private String product;

	@Column(name = "BATCH_LOC_ID")
	private String batch_loc_id;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "BATCH_NO")
	private String batch_no;

	@Column(name = "BATCH_MFG_DT")
	private String batch_mfg_dt;

	@Column(name = "BATCH_EXPIRY_DT")
	private String batch_expiry_dt;

	@Column(name = "BATCH_PHYSICAL_STOCK")
	private String batch_physical_stock;

	@Column(name = "BATCH_Narration")
	private String batch_narration;

	@Column(name = "BATCH_MFG_LOC_ID")
	private String batch_mfg_loc_id;

	@Column(name = "MFG_LOCATION")
	private String mfg_location;

	@Column(name = "BATCH_RATE")
	private BigDecimal batch_rate;

	@Column(name = "BATCH_COSTING_RATE")
	private BigDecimal batch_costing_rate;

	@Column(name = "BATCH_MKTG_RATE")
	private BigDecimal batch_mktg_rate;

	@Column(name = "BATCH_NRV")
	private BigDecimal batch_nrv;

	@Column(name = "BATCH_DISPLAY_RATE")
	private BigDecimal batch_display_rate;

	@Column(name = "BATCH_OPEN_STOCK")
	private BigDecimal batch_open_stock;

	@Column(name = "BATCH_IN_STOCK")
	private BigDecimal batch_in_stock;
	
	
	@Column(name = "BATCH_OUT_STOCK")
	private BigDecimal batch_out_stock;
	
	@Column(name = "BATCH_EXCLUDE_LOC")
	private String batch_exclude_loc;
	
	@Column(name = "BATCH_EXCLUDE_PARTY")
	private String batch_exclude_party;
	
	@Column(name = "BATCH_WITH_HELD_QTY")
	private BigDecimal batch_with_held_qty;
	
	@Column(name = "BATCH_ERP_BATCH_CD")
	private String batch_erp_batch_cd;
	
	@Column(name = "BATCH_ins_usr_id")
	private String batch_ins_usr_id;
	
	@Column(name = "INS_USER_NAME")
	private String ins_user_name;
	
	@Column(name = "BATCH_ins_dt")
	private String batch_ins_dt;
	
	@Column(name = "BATCH_ins_ip_add")
	private String batch_ins_ip_add;
	
	@Column(name = "BATCH_mod_usr_id")
	private String batch_mod_usr_id;
	
	@Column(name = "MOD_USER_NAME")
	private String mod_user_name;
	
	@Column(name = "BATCH_mod_dt")
	private String batch_mod_dt;
	
	@Column(name = "BATCH_mod_ip_add")
	private String batch_mod_ip_add;
	
	@Column(name = "BATCH_status")
	private String batch_status;
	
	@Column(name = "CURR_STATUS")
	private String curr_status;

	@Column(name = "IND")
	private String ind;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(Long batch_id) {
		this.batch_id = batch_id;
	}

	public String getBatch_prod_id() {
		return batch_prod_id;
	}

	public void setBatch_prod_id(String batch_prod_id) {
		this.batch_prod_id = batch_prod_id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getBatch_loc_id() {
		return batch_loc_id;
	}

	public void setBatch_loc_id(String batch_loc_id) {
		this.batch_loc_id = batch_loc_id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_mfg_dt() {
		return batch_mfg_dt;
	}

	public void setBatch_mfg_dt(String batch_mfg_dt) {
		this.batch_mfg_dt = batch_mfg_dt;
	}

	public String getBatch_expiry_dt() {
		return batch_expiry_dt;
	}

	public void setBatch_expiry_dt(String batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}

	public String getBatch_physical_stock() {
		return batch_physical_stock;
	}

	public void setBatch_physical_stock(String batch_physical_stock) {
		this.batch_physical_stock = batch_physical_stock;
	}

	public String getBatch_narration() {
		return batch_narration;
	}

	public void setBatch_narration(String batch_narration) {
		this.batch_narration = batch_narration;
	}

	public String getBatch_mfg_loc_id() {
		return batch_mfg_loc_id;
	}

	public void setBatch_mfg_loc_id(String batch_mfg_loc_id) {
		this.batch_mfg_loc_id = batch_mfg_loc_id;
	}

	public String getMfg_location() {
		return mfg_location;
	}

	public void setMfg_location(String mfg_location) {
		this.mfg_location = mfg_location;
	}

	public BigDecimal getBatch_rate() {
		return batch_rate;
	}

	public void setBatch_rate(BigDecimal batch_rate) {
		this.batch_rate = batch_rate;
	}

	public BigDecimal getBatch_costing_rate() {
		return batch_costing_rate;
	}

	public void setBatch_costing_rate(BigDecimal batch_costing_rate) {
		this.batch_costing_rate = batch_costing_rate;
	}

	public BigDecimal getBatch_mktg_rate() {
		return batch_mktg_rate;
	}

	public void setBatch_mktg_rate(BigDecimal batch_mktg_rate) {
		this.batch_mktg_rate = batch_mktg_rate;
	}

	public BigDecimal getBatch_nrv() {
		return batch_nrv;
	}

	public void setBatch_nrv(BigDecimal batch_nrv) {
		this.batch_nrv = batch_nrv;
	}

	public BigDecimal getBatch_display_rate() {
		return batch_display_rate;
	}

	public void setBatch_display_rate(BigDecimal batch_display_rate) {
		this.batch_display_rate = batch_display_rate;
	}

	public BigDecimal getBatch_open_stock() {
		return batch_open_stock;
	}

	public void setBatch_open_stock(BigDecimal batch_open_stock) {
		this.batch_open_stock = batch_open_stock;
	}

	public BigDecimal getBatch_in_stock() {
		return batch_in_stock;
	}

	public void setBatch_in_stock(BigDecimal batch_in_stock) {
		this.batch_in_stock = batch_in_stock;
	}

	public BigDecimal getBatch_out_stock() {
		return batch_out_stock;
	}

	public void setBatch_out_stock(BigDecimal batch_out_stock) {
		this.batch_out_stock = batch_out_stock;
	}

	public String getBatch_exclude_loc() {
		return batch_exclude_loc;
	}

	public void setBatch_exclude_loc(String batch_exclude_loc) {
		this.batch_exclude_loc = batch_exclude_loc;
	}

	public String getBatch_exclude_party() {
		return batch_exclude_party;
	}

	public void setBatch_exclude_party(String batch_exclude_party) {
		this.batch_exclude_party = batch_exclude_party;
	}

	public BigDecimal getBatch_with_held_qty() {
		return batch_with_held_qty;
	}

	public void setBatch_with_held_qty(BigDecimal batch_with_held_qty) {
		this.batch_with_held_qty = batch_with_held_qty;
	}

	public String getBatch_erp_batch_cd() {
		return batch_erp_batch_cd;
	}

	public void setBatch_erp_batch_cd(String batch_erp_batch_cd) {
		this.batch_erp_batch_cd = batch_erp_batch_cd;
	}

	public String getBatch_ins_usr_id() {
		return batch_ins_usr_id;
	}

	public void setBatch_ins_usr_id(String batch_ins_usr_id) {
		this.batch_ins_usr_id = batch_ins_usr_id;
	}

	public String getIns_user_name() {
		return ins_user_name;
	}

	public void setIns_user_name(String ins_user_name) {
		this.ins_user_name = ins_user_name;
	}

	public String getBatch_ins_dt() {
		return batch_ins_dt;
	}

	public void setBatch_ins_dt(String batch_ins_dt) {
		this.batch_ins_dt = batch_ins_dt;
	}

	public String getBatch_ins_ip_add() {
		return batch_ins_ip_add;
	}

	public void setBatch_ins_ip_add(String batch_ins_ip_add) {
		this.batch_ins_ip_add = batch_ins_ip_add;
	}

	public String getBatch_mod_usr_id() {
		return batch_mod_usr_id;
	}

	public void setBatch_mod_usr_id(String batch_mod_usr_id) {
		this.batch_mod_usr_id = batch_mod_usr_id;
	}

	public String getMod_user_name() {
		return mod_user_name;
	}

	public void setMod_user_name(String mod_user_name) {
		this.mod_user_name = mod_user_name;
	}

	public String getBatch_mod_dt() {
		return batch_mod_dt;
	}

	public void setBatch_mod_dt(String batch_mod_dt) {
		this.batch_mod_dt = batch_mod_dt;
	}

	public String getBatch_mod_ip_add() {
		return batch_mod_ip_add;
	}

	public void setBatch_mod_ip_add(String batch_mod_ip_add) {
		this.batch_mod_ip_add = batch_mod_ip_add;
	}

	public String getBatch_status() {
		return batch_status;
	}

	public void setBatch_status(String batch_status) {
		this.batch_status = batch_status;
	}

	public String getCurr_status() {
		return curr_status;
	}

	public void setCurr_status(String curr_status) {
		this.curr_status = curr_status;
	}

	public String getInd() {
		return ind;
	}

	public void setInd(String ind) {
		this.ind = ind;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
