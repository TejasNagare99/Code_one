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
@Table(name = "BatchwiseStockStmtReport")

@NamedStoredProcedureQuery(name = "callBatchwiseStockStmtReport", 
	procedureName = "BATCH_STOCK_DETAIL_REPORT_EXCEL_FCODE_RATEOPTION",
	parameters = {
			@StoredProcedureParameter(name = "piloc_id", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "pidiv_id", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "pvfrmdt" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "pvtodt" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "pvuserid" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "pvChkzero" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "exp_ind" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "nilstock" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "FINYEAR" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "FIN_YEAR_FLAG",mode = ParameterMode.IN,type = String.class),
			@StoredProcedureParameter(name = "RATE_OPTION",mode = ParameterMode.IN,type = String.class)
	}, resultClasses = BatchwiseStockStmtReport_SG.class)

public class BatchwiseStockStmtReport_SG implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "row")
	private String row;
	@Column(name = "Div_Id")
	private Long Div_Id;
	@Column(name = "div_disp_nm")
	private String div_disp_nm;
	@Column(name = "smp_prod_type")
	private String smp_prod_type;
	@Column(name = "smp_prod_cd")
	private String smp_prod_cd;
	@Column(name = "smp_prod_name")
	private String smp_prod_name;
	@Column(name = "batch_no")
	private String batch_no;
	@Column(name = "batch_expiry_dt")
	private String batch_expiry_dt;
	@Column(name = "open_stock")
	private BigDecimal open_stock;
	@Column(name = "grn_receipts")
	private BigDecimal grn_receipts;
	@Column(name = "adj_receipts")
	private BigDecimal adj_receipts;
	@Column(name = "trf_receipts")
	private BigDecimal trf_receipts;
	@Column(name = "in_total")
	private BigDecimal in_total;
	@Column(name = "dsp_issues")
	private BigDecimal dsp_issues;
	@Column(name = "adj_issues")
	private BigDecimal adj_issues;
	
	@Column(name = "trf_issues")
	private BigDecimal trf_issues;
	
	@Column(name = "out_total")
	private BigDecimal out_total;
	
	@Column(name = "closing_stock")
	private BigDecimal closing_stock;
	
	@Column(name = "closing_val")
	private BigDecimal closing_val;
	
	@Column(name="cum_dsp_val")
	private BigDecimal cum_dsp_val;
	
	@Column(name = "grn_inval")
	private BigDecimal grn_inval;
	@Column(name = "trf_inval")
	private BigDecimal trf_inval;
	@Column(name = "adj_inval")
	private BigDecimal adj_inval;
	@Column(name = "adj_outval")
	private BigDecimal adj_outval;
	@Column(name = "dsp_outval")
	private BigDecimal dsp_outval;
	@Column(name = "trf_outval")
	private BigDecimal trf_outval;
	@Column(name = "outstock_val")
	private BigDecimal outstock_val;
	@Column(name = "in_total_val")
	private BigDecimal in_total_val;
	@Column(name = "out_total_val")
	private BigDecimal out_total_val;
	@Column(name = "open_val")
	private BigDecimal open_val;
	@Column(name = "intransit")
	private BigDecimal intransit;
	@Column(name = "intransit_val")
	private BigDecimal intransit_val;
	@Column(name = "cumoutstock")
	private BigDecimal cumoutstock;
	@Column(name = "cumout_val")
	private BigDecimal cumout_val;
	@Column(name = "FCODE")
	private String fcode;
	@Column(name = "STORAGE_TYPE")
	private String storage_type;
	@Column(name = "COG_RATE")
	private BigDecimal cog_rate;
	@Column(name= "BATCH_WITH_HELD_QTY")
	private BigDecimal batch_with_held_qty;
	
	/*
	 * @Column(name= "BATCH_LOC_ID") private String batch_loc_id;
	 * 
	 * @Column(name= "LOC_NM") private String loc_nm;
	 */
	
	
	
	
	
	
	@Override
	public String toString() {
		return "BatchwiseStockStmtReport [row=" + row + ", Div_Id=" + Div_Id + ", div_disp_nm=" + div_disp_nm
				+ ", smp_prod_type=" + smp_prod_type + ", smp_prod_cd=" + smp_prod_cd + ", smp_prod_name="
				+ smp_prod_name + ", batch_no=" + batch_no + ", batch_expiry_dt=" + batch_expiry_dt + ", open_stock="
				+ open_stock + ", grn_receipts=" + grn_receipts + ", adj_receipts=" + adj_receipts + ", trf_receipts="
				+ trf_receipts + ", in_total=" + in_total + ", dsp_issues=" + dsp_issues + ", adj_issues=" + adj_issues
				+ ", trf_issues=" + trf_issues + ", out_total=" + out_total + ", closing_stock=" + closing_stock
				+ ", closing_val=" + closing_val + ", cum_dsp_val=" + cum_dsp_val + ", grn_inval=" + grn_inval
				+ ", trf_inval=" + trf_inval + ", adj_inval=" + adj_inval + ", adj_outval=" + adj_outval
				+ ", dsp_outval=" + dsp_outval + ", trf_outval=" + trf_outval + ", outstock_val=" + outstock_val
				+ ", in_total_val=" + in_total_val + ", out_total_val=" + out_total_val + ", open_val=" + open_val
				+ ", intransit=" + intransit + ", intransit_val=" + intransit_val + ", cumoutstock=" + cumoutstock
				+ ", cumout_val=" + cumout_val + ", fcode=" + fcode + ", storage_type=" + storage_type + ", cog_rate="
				+ cog_rate + ", batch_with_held_qty=" + batch_with_held_qty+ "]";
	}

	/*
	 * public String getBatch_loc_id() { return batch_loc_id; } public void
	 * setBatch_loc_id(String batch_loc_id) { this.batch_loc_id = batch_loc_id; }
	 * public String getLoc_nm() { return loc_nm; } public void setLoc_nm(String
	 * loc_nm) { this.loc_nm = loc_nm; }
	 */
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public Long getDiv_Id() {
		return Div_Id;
	}
	public void setDiv_Id(Long div_Id) {
		Div_Id = div_Id;
	}
	public String getDiv_disp_nm() {
		return div_disp_nm;
	}
	public void setDiv_disp_nm(String div_disp_nm) {
		this.div_disp_nm = div_disp_nm;
	}
	public String getSmp_prod_type() {
		return smp_prod_type;
	}
	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}
	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}
	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}
	public String getSmp_prod_name() {
		return smp_prod_name;
	}
	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getBatch_expiry_dt() {
		return batch_expiry_dt;
	}
	public void setBatch_expiry_dt(String batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}
	public BigDecimal getOpen_stock() {
		return open_stock;
	}
	public void setOpen_stock(BigDecimal open_stock) {
		this.open_stock = open_stock;
	}
	public BigDecimal getGrn_receipts() {
		return grn_receipts;
	}
	public void setGrn_receipts(BigDecimal grn_receipts) {
		this.grn_receipts = grn_receipts;
	}
	public BigDecimal getAdj_receipts() {
		return adj_receipts;
	}
	public void setAdj_receipts(BigDecimal adj_receipts) {
		this.adj_receipts = adj_receipts;
	}
	public BigDecimal getTrf_receipts() {
		return trf_receipts;
	}
	public void setTrf_receipts(BigDecimal trf_receipts) {
		this.trf_receipts = trf_receipts;
	}
	public BigDecimal getIn_total() {
		return in_total;
	}
	public void setIn_total(BigDecimal in_total) {
		this.in_total = in_total;
	}
	public BigDecimal getDsp_issues() {
		return dsp_issues;
	}
	public void setDsp_issues(BigDecimal dsp_issues) {
		this.dsp_issues = dsp_issues;
	}
	public BigDecimal getAdj_issues() {
		return adj_issues;
	}
	public void setAdj_issues(BigDecimal adj_issues) {
		this.adj_issues = adj_issues;
	}
	public BigDecimal getTrf_issues() {
		return trf_issues;
	}
	public void setTrf_issues(BigDecimal trf_issues) {
		this.trf_issues = trf_issues;
	}
	public BigDecimal getOut_total() {
		return out_total;
	}
	public void setOut_total(BigDecimal out_total) {
		this.out_total = out_total;
	}
	public BigDecimal getClosing_stock() {
		return closing_stock;
	}
	public void setClosing_stock(BigDecimal closing_stock) {
		this.closing_stock = closing_stock;
	}
	public BigDecimal getClosing_val() {
		return closing_val;
	}
	public void setClosing_val(BigDecimal closing_val) {
		this.closing_val = closing_val;
	}
	public BigDecimal getCum_dsp_val() {
		return cum_dsp_val;
	}
	public void setCum_dsp_val(BigDecimal cum_dsp_val) {
		this.cum_dsp_val = cum_dsp_val;
	}
	public BigDecimal getGrn_inval() {
		return grn_inval;
	}
	public void setGrn_inval(BigDecimal grn_inval) {
		this.grn_inval = grn_inval;
	}
	public BigDecimal getTrf_inval() {
		return trf_inval;
	}
	public void setTrf_inval(BigDecimal trf_inval) {
		this.trf_inval = trf_inval;
	}
	public BigDecimal getAdj_inval() {
		return adj_inval;
	}
	public void setAdj_inval(BigDecimal adj_inval) {
		this.adj_inval = adj_inval;
	}
	public BigDecimal getAdj_outval() {
		return adj_outval;
	}
	public void setAdj_outval(BigDecimal adj_outval) {
		this.adj_outval = adj_outval;
	}
	public BigDecimal getDsp_outval() {
		return dsp_outval;
	}
	public void setDsp_outval(BigDecimal dsp_outval) {
		this.dsp_outval = dsp_outval;
	}
	public BigDecimal getTrf_outval() {
		return trf_outval;
	}
	public void setTrf_outval(BigDecimal trf_outval) {
		this.trf_outval = trf_outval;
	}
	public BigDecimal getOutstock_val() {
		return outstock_val;
	}
	public void setOutstock_val(BigDecimal outstock_val) {
		this.outstock_val = outstock_val;
	}
	public BigDecimal getIn_total_val() {
		return in_total_val;
	}
	public void setIn_total_val(BigDecimal in_total_val) {
		this.in_total_val = in_total_val;
	}
	public BigDecimal getOut_total_val() {
		return out_total_val;
	}
	public void setOut_total_val(BigDecimal out_total_val) {
		this.out_total_val = out_total_val;
	}
	public BigDecimal getOpen_val() {
		return open_val;
	}
	public void setOpen_val(BigDecimal open_val) {
		this.open_val = open_val;
	}
	public BigDecimal getIntransit() {
		return intransit;
	}
	public void setIntransit(BigDecimal intransit) {
		this.intransit = intransit;
	}
	public BigDecimal getIntransit_val() {
		return intransit_val;
	}
	public void setIntransit_val(BigDecimal intransit_val) {
		this.intransit_val = intransit_val;
	}
	public BigDecimal getCumoutstock() {
		return cumoutstock;
	}
	public void setCumoutstock(BigDecimal cumoutstock) {
		this.cumoutstock = cumoutstock;
	}
	public BigDecimal getCumout_val() {
		return cumout_val;
	}
	public void setCumout_val(BigDecimal cumout_val) {
		this.cumout_val = cumout_val;
	}
	public String getFcode() {
		return fcode;
	}
	public void setFcode(String fcode) {
		this.fcode = fcode;
	}
	public String getStorage_type() {
		return storage_type;
	}
	public void setStorage_type(String storage_type) {
		this.storage_type = storage_type;
	}
	public BigDecimal getCog_rate() {
		return cog_rate;
	}
	public void setCog_rate(BigDecimal cog_rate) {
		this.cog_rate = cog_rate;
	}
	public BigDecimal getBatch_with_held_qty() {
		return batch_with_held_qty;
	}
	public void setBatch_with_held_qty(BigDecimal batch_with_held_qty) {
		this.batch_with_held_qty = batch_with_held_qty;
	}
	
	
}
