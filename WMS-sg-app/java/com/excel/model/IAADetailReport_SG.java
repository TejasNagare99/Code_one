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
@Table(name="IAADetailReport")
@NamedStoredProcedureQuery(
		name="IAADetailReport_Revised_SG",
				procedureName = "IAADETAIL_REVISED",
				parameters = {
						@StoredProcedureParameter(name = "loc_id", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "startdate", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "endDate" , mode = ParameterMode.IN, type = String.class),
				},
		resultClasses = IAADetailReport_SG.class
)
@NamedStoredProcedureQuery(
		name="IAADetailReport_Revised_Previous_SG",
				procedureName = "IAADETAIL_REVISED_PRV_YRS",
				parameters = {
						@StoredProcedureParameter(name = "loc_id" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "startdate" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "endDate" , mode = ParameterMode.IN, type = String.class),
				},
		resultClasses = IAADetailReport_SG.class
)
public class IAADetailReport_SG {

	@Id
	@Column(name="ROW")
	private Long row;
	
	@Column(name = "STKADJ_ID")
	private Long stkadj_id;
	
	@Column(name = "STKADJ_NO")
	private String stkadj_no;
	
	@Column(name = "loc_nm")
	private String loc_nm;
	
	@Column(name = "SADJ_DT")
	private String sadj_dt;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "PACKING")
	private String packing;
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "BATCH_EXPIRY_DT")
	private String batch_expiry_dt;
	
	@Column(name = "REASON")
	private String reason;
	
	@Column(name = "STOCK_TYPE")
	private String stock_type;
	
	@Column(name = "STOCK_TYPE_DESC")
	private String stock_type_desc;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "OUT_QTY")
	private BigDecimal out_qty;
	
	@Column(name = "IN_QTY")
	private BigDecimal in_qty;
	
	@Column(name = "ADJDTL_ID")
	private String adjdtl_id;
	
	@Column(name = "UNIT_PRICE")
	private BigDecimal unit_price;
	
	@Column(name = "TOTAL_VALUE")
	private BigDecimal total_value;
	
	@Column(name = "TEAM_NAME")
	private String team_name;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getStkadj_id() {
		return stkadj_id;
	}

	public void setStkadj_id(Long stkadj_id) {
		this.stkadj_id = stkadj_id;
	}

	public String getStkadj_no() {
		return stkadj_no;
	}

	public void setStkadj_no(String stkadj_no) {
		this.stkadj_no = stkadj_no;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getSadj_dt() {
		return sadj_dt;
	}

	public void setSadj_dt(String sadj_dt) {
		this.sadj_dt = sadj_dt;
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

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}

	public String getStock_type_desc() {
		return stock_type_desc;
	}

	public void setStock_type_desc(String stock_type_desc) {
		this.stock_type_desc = stock_type_desc;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getOut_qty() {
		return out_qty;
	}

	public void setOut_qty(BigDecimal out_qty) {
		this.out_qty = out_qty;
	}

	public BigDecimal getIn_qty() {
		return in_qty;
	}

	public void setIn_qty(BigDecimal in_qty) {
		this.in_qty = in_qty;
	}

	public String getAdjdtl_id() {
		return adjdtl_id;
	}

	public void setAdjdtl_id(String adjdtl_id) {
		this.adjdtl_id = adjdtl_id;
	}

	public BigDecimal getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}

	public BigDecimal getTotal_value() {
		return total_value;
	}

	public void setTotal_value(BigDecimal total_value) {
		this.total_value = total_value;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	
	
}
