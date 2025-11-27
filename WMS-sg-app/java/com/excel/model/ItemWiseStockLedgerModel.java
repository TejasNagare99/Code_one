package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name="ItemWiseStockLedgerModel")
@NamedStoredProcedureQuery(name = "ItemWiseStockLedger", 
procedureName = "p_v_ITEMSTOCK_LEDGER_RPT",
parameters = {
		@StoredProcedureParameter(name = "piloc_id", mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "pidiv_id", mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "pvfrmdt" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "pvtodt" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "piprod_id" , mode = ParameterMode.IN, type = Integer.class),
		@StoredProcedureParameter(name = "pvuserid" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class)
}, resultClasses = ItemWiseStockLedgerModel.class)
public class ItemWiseStockLedgerModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SRNO")
	private long srno;

	@Column(name = "DIV_DISP_NM")
	private String div_disp_nm;

	@Column(name = "PROD_NAME")
	private String prod_name;

	@Column(name = "PACK_DISP_NM")
	private String pack_disp_nm;

	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;

	@Column(name = "SMP_PROD_ID")
	private String smp_prod_id;

	@Column(name = "TRN_TYPE")
	private String trn_type;

	@Column(name = "TRN_NUM")
	private String trn_num;

	@Column(name = "TRN_DT")
	private String trn_dt;

	@Column(name = "CHALLAN_NO")
	private String challan_no;

	@Column(name = "CHALLAN_DT")
	private String challan_dt;

	@Column(name = "REFERENCE_NO")
	private String reference_no;

	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "HQ")
	private String hq;

	@Column(name = "OPEN_STOCK")
	private BigDecimal open_stock;

	@Column(name = "OPEN_VAL")
	private BigDecimal open_val;

	@Column(name = "GRN_QTY")
	private BigDecimal grn_qty;

	@Column(name = "GRN_VAL")
	private BigDecimal grn_val;

	@Column(name = "INADJ_QTY")
	private BigDecimal inadj_qty;

	@Column(name = "INADJ_VAL")
	private BigDecimal inadj_val;

	@Column(name = "DESP_QTY")
	private BigDecimal desp_qty;

	@Column(name = "DESP_VAL")
	private BigDecimal desp_val;

	@Column(name = "OUTADJ_QTY")
	private BigDecimal outadj_qty;

	@Column(name = "OUTADJ_VAL")
	private BigDecimal outadj_val;

	@Column(name = "SORT_NO")
	private long sort_no;

	@Column(name = "BAL_QTY")
	private BigDecimal bal_qty;

	public long getSrno() {
		return srno;
	}

	public void setSrno(long srno) {
		this.srno = srno;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getPack_disp_nm() {
		return pack_disp_nm;
	}

	public void setPack_disp_nm(String pack_disp_nm) {
		this.pack_disp_nm = pack_disp_nm;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getSmp_prod_id() {
		return smp_prod_id;
	}

	public void setSmp_prod_id(String smp_prod_id) {
		this.smp_prod_id = smp_prod_id;
	}

	public String getTrn_type() {
		return trn_type;
	}

	public void setTrn_type(String trn_type) {
		this.trn_type = trn_type;
	}

	public String getTrn_num() {
		return trn_num;
	}

	public void setTrn_num(String trn_num) {
		this.trn_num = trn_num;
	}

	public String getTrn_dt() {
		return trn_dt;
	}

	public void setTrn_dt(String trn_dt) {
		this.trn_dt = trn_dt;
	}

	public String getChallan_no() {
		return challan_no;
	}

	public void setChallan_no(String challan_no) {
		this.challan_no = challan_no;
	}

	public String getChallan_dt() {
		return challan_dt;
	}

	public void setChallan_dt(String challan_dt) {
		this.challan_dt = challan_dt;
	}

	public String getReference_no() {
		return reference_no;
	}

	public void setReference_no(String reference_no) {
		this.reference_no = reference_no;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getHq() {
		return hq;
	}

	public void setHq(String hq) {
		this.hq = hq;
	}

	public BigDecimal getOpen_stock() {
		return open_stock;
	}

	public void setOpen_stock(BigDecimal open_stock) {
		this.open_stock = open_stock;
	}

	public BigDecimal getOpen_val() {
		return open_val;
	}

	public void setOpen_val(BigDecimal open_val) {
		this.open_val = open_val;
	}

	public BigDecimal getGrn_qty() {
		return grn_qty;
	}

	public void setGrn_qty(BigDecimal grn_qty) {
		this.grn_qty = grn_qty;
	}

	public BigDecimal getGrn_val() {
		return grn_val;
	}

	public void setGrn_val(BigDecimal grn_val) {
		this.grn_val = grn_val;
	}

	public BigDecimal getInadj_qty() {
		return inadj_qty;
	}

	public void setInadj_qty(BigDecimal inadj_qty) {
		this.inadj_qty = inadj_qty;
	}

	public BigDecimal getInadj_val() {
		return inadj_val;
	}

	public void setInadj_val(BigDecimal inadj_val) {
		this.inadj_val = inadj_val;
	}

	public BigDecimal getDesp_qty() {
		return desp_qty;
	}

	public void setDesp_qty(BigDecimal desp_qty) {
		this.desp_qty = desp_qty;
	}

	public BigDecimal getDesp_val() {
		return desp_val;
	}

	public void setDesp_val(BigDecimal desp_val) {
		this.desp_val = desp_val;
	}

	public BigDecimal getOutadj_qty() {
		return outadj_qty;
	}

	public void setOutadj_qty(BigDecimal outadj_qty) {
		this.outadj_qty = outadj_qty;
	}

	public BigDecimal getOutadj_val() {
		return outadj_val;
	}

	public void setOutadj_val(BigDecimal outadj_val) {
		this.outadj_val = outadj_val;
	}

	public long getSort_no() {
		return sort_no;
	}

	public void setSort_no(long sort_no) {
		this.sort_no = sort_no;
	}

	public BigDecimal getBal_qty() {
		return bal_qty;
	}

	public void setBal_qty(BigDecimal bal_qty) {
		this.bal_qty = bal_qty;
	}
	
	
}
