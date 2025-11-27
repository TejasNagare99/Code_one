package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Stock_Reco_With_sfa")
@NamedStoredProcedureQuery(name = "Call_STOCK_RECO_WITH_SFA",
procedureName = "STOCK_RECO_WITH_SFA",resultClasses=Stock_Reco_With_sfa.class)
public class Stock_Reco_With_sfa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Row")
	private Long row;
	
	@Column(name = "DIV_DISP_NM")
	private String div_disp_nm;
	
	@Column(name = "SMP_PROD_ID")
	private Long smp_prod_id;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "SMP_ERP_PROD_CD")
	private String smp_erp_prod_cd;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "BATCH_OPEN_STOCK")
	private BigDecimal batch_open_stock;
	
	@Column(name = "BATCH_IN_STOCK")
	private BigDecimal batch_in_stock;
	
	@Column(name = "BATCH_OUT_STOCK")
	private BigDecimal batch_out_stock;
	
	@Column(name = "BATCH_WITH_HELD_QTY")
	private BigDecimal batch_with_held_qty;
	
	@Column(name = "SG_CL_STOCK_QTY")
	private BigDecimal sg_cl_stock_qty;
	
	@Column(name = "SG_CL_STOCK_VAL")
	private BigDecimal sg_cl_stock_val;
	
	@Column(name = "STGET_CL_STOCK_QTY")
	private BigDecimal stget_cl_stock_qty;
	
	@Column(name = "STGET_CL_STOCK_VAL")
	private BigDecimal stget_cl_stock_val;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getDiv_disp_nm() {
		return div_disp_nm;
	}

	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
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

	public String getSmp_erp_prod_cd() {
		return smp_erp_prod_cd;
	}

	public void setSmp_erp_prod_cd(String smp_erp_prod_cd) {
		this.smp_erp_prod_cd = smp_erp_prod_cd;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
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

	public BigDecimal getBatch_with_held_qty() {
		return batch_with_held_qty;
	}

	public void setBatch_with_held_qty(BigDecimal batch_with_held_qty) {
		this.batch_with_held_qty = batch_with_held_qty;
	}

	public BigDecimal getSg_cl_stock_qty() {
		return sg_cl_stock_qty;
	}

	public void setSg_cl_stock_qty(BigDecimal sg_cl_stock_qty) {
		this.sg_cl_stock_qty = sg_cl_stock_qty;
	}

	public BigDecimal getSg_cl_stock_val() {
		return sg_cl_stock_val;
	}

	public void setSg_cl_stock_val(BigDecimal sg_cl_stock_val) {
		this.sg_cl_stock_val = sg_cl_stock_val;
	}

	public BigDecimal getStget_cl_stock_qty() {
		return stget_cl_stock_qty;
	}

	public void setStget_cl_stock_qty(BigDecimal stget_cl_stock_qty) {
		this.stget_cl_stock_qty = stget_cl_stock_qty;
	}

	public BigDecimal getStget_cl_stock_val() {
		return stget_cl_stock_val;
	}

	public void setStget_cl_stock_val(BigDecimal stget_cl_stock_val) {
		this.stget_cl_stock_val = stget_cl_stock_val;
	}

	@Override
	public String toString() {
		return "Stock_Reco_With_sfa [row=" + row + ", div_disp_nm=" + div_disp_nm + ", smp_prod_id=" + smp_prod_id
				+ ", smp_prod_cd=" + smp_prod_cd + ", smp_erp_prod_cd=" + smp_erp_prod_cd + ", smp_prod_type="
				+ smp_prod_type + ", smp_prod_name=" + smp_prod_name + ", batch_open_stock=" + batch_open_stock
				+ ", batch_in_stock=" + batch_in_stock + ", batch_out_stock=" + batch_out_stock
				+ ", batch_with_held_qty=" + batch_with_held_qty + ", sg_cl_stock_qty=" + sg_cl_stock_qty
				+ ", sg_cl_stock_val=" + sg_cl_stock_val + ", stget_cl_stock_qty=" + stget_cl_stock_qty
				+ ", stget_cl_stock_val=" + stget_cl_stock_val + "]";
	}

	public Stock_Reco_With_sfa() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
