//package com.excel.model;
//
//import java.math.BigDecimal;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.NamedStoredProcedureQuery;
//import javax.persistence.ParameterMode;
//import javax.persistence.StoredProcedureParameter;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "p_u_split_manualdispatch_java")
//@NamedStoredProcedureQuery(name = "callManulDispatchProdListDataEdit", 
//procedureName = "p_u_split_manualdispatch_java",
//parameters = {
//		@StoredProcedureParameter(name = "pidsp_id" , mode = ParameterMode.IN, type = Long.class),
//		@StoredProcedureParameter(name = "pidspdtl_id" , mode = ParameterMode.IN, type = Long.class),
//		@StoredProcedureParameter(name = "pcpendalloc" , mode = ParameterMode.IN, type = String.class),
//		
//}, resultClasses = ManulDispatchProdListData_SG.class)
//
//public class ManulDispatchProdListData_SG {
//	/*
//	 * @Id
//	 * 
//	 * @Column(name="Row") private Long row;
//	 */
////	@Id
////	@Column(name="ROW_ID")
////	private Long row_id;
//
//	@Column(name="smp_prod_id")
//	private Long smp_prod_id;
//
//	@Column(name="smp_prod_nm")
//	private String smp_prod_nm;
//
//	@Column(name="PROD_STOCK")
//	private Long prod_stock;
//
//	@Column(name="smp_batch_no")
//	private String smp_batch_no;
//
//	@Column(name="smp_batch_id")
//	private Long smp_batch_id;
//
//	@Column(name="SMP_EXPIRY_DT")
//	private String smp_expiry_dt;
//
//	@Column(name="smp_Rate")
//	private BigDecimal smp_rate;
//
//	@Column(name="smp_loc_id")
//	private Long smp_loc_id;
//
//	@Column(name="smp_id")
//	private Long smp_id;
//
//	@Column(name="smp_allocqty")
//	private Long smp_allocqty;
//
//	@Column(name="smp_batch_stock")
//	private Long smp_batch_stock;
//
//	@Column(name="PROD_DIV_ID")
//	private Long prod_div_id;
//
//	@Column(name="smp_dispatch_qty")
//	private Long smp_dispatch_qty;
//
//	@Column(name="alloc_id")
//	private Long alloc_id;
//
//	@Column(name="allocdtl_id")
//	private Long allocdtl_id;
//
//	@Column(name="PRV_ALLOCID")
//	private Long prv_allocid;
//
//	@Column(name="PRV_ALLOCDTL_ID")
//	private Long prv_allocdtl_id;
//
//	@Column(name="smp_prvqty")
//	private Long smp_prvqty;
//
//	@Column(name="smp_currqty")
//	private Long smp_currqty;
//
//	
//
//	public Long getSmp_prod_id() {
//		return smp_prod_id;
//	}
//
//	public void setSmp_prod_id(Long smp_prod_id) {
//		this.smp_prod_id = smp_prod_id;
//	}
//
//	public String getSmp_prod_nm() {
//		return smp_prod_nm;
//	}
//
//	public void setSmp_prod_nm(String smp_prod_nm) {
//		this.smp_prod_nm = smp_prod_nm;
//	}
//
//	public Long getProd_stock() {
//		return prod_stock;
//	}
//
//	public void setProd_stock(Long prod_stock) {
//		this.prod_stock = prod_stock;
//	}
//
//	public String getSmp_batch_no() {
//		return smp_batch_no;
//	}
//
//	public void setSmp_batch_no(String smp_batch_no) {
//		this.smp_batch_no = smp_batch_no;
//	}
//
//	public Long getSmp_batch_id() {
//		return smp_batch_id;
//	}
//
//	public void setSmp_batch_id(Long smp_batch_id) {
//		this.smp_batch_id = smp_batch_id;
//	}
//
//	public String getSmp_expiry_dt() {
//		return smp_expiry_dt;
//	}
//
//	public void setSmp_expiry_dt(String smp_expiry_dt) {
//		this.smp_expiry_dt = smp_expiry_dt;
//	}
//
//	public BigDecimal getSmp_rate() {
//		return smp_rate;
//	}
//
//	public void setSmp_rate(BigDecimal smp_rate) {
//		this.smp_rate = smp_rate;
//	}
//
//	public Long getSmp_loc_id() {
//		return smp_loc_id;
//	}
//
//	public void setSmp_loc_id(Long smp_loc_id) {
//		this.smp_loc_id = smp_loc_id;
//	}
//
//	public Long getSmp_id() {
//		return smp_id;
//	}
//
//	public void setSmp_id(Long smp_id) {
//		this.smp_id = smp_id;
//	}
//
//	public Long getSmp_allocqty() {
//		return smp_allocqty;
//	}
//
//	public void setSmp_allocqty(Long smp_allocqty) {
//		this.smp_allocqty = smp_allocqty;
//	}
//
//	public Long getSmp_batch_stock() {
//		return smp_batch_stock;
//	}
//
//	public void setSmp_batch_stock(Long smp_batch_stock) {
//		this.smp_batch_stock = smp_batch_stock;
//	}
//
//	public Long getProd_div_id() {
//		return prod_div_id;
//	}
//
//	public void setProd_div_id(Long prod_div_id) {
//		this.prod_div_id = prod_div_id;
//	}
//
//	public Long getSmp_dispatch_qty() {
//		return smp_dispatch_qty;
//	}
//
//	public void setSmp_dispatch_qty(Long smp_dispatch_qty) {
//		this.smp_dispatch_qty = smp_dispatch_qty;
//	}
//
//	public Long getAlloc_id() {
//		return alloc_id;
//	}
//
//	public void setAlloc_id(Long alloc_id) {
//		this.alloc_id = alloc_id;
//	}
//
//	public Long getAllocdtl_id() {
//		return allocdtl_id;
//	}
//
//	public void setAllocdtl_id(Long allocdtl_id) {
//		this.allocdtl_id = allocdtl_id;
//	}
//
//	public Long getPrv_allocid() {
//		return prv_allocid;
//	}
//
//	public void setPrv_allocid(Long prv_allocid) {
//		this.prv_allocid = prv_allocid;
//	}
//
//	public Long getPrv_allocdtl_id() {
//		return prv_allocdtl_id;
//	}
//
//	public void setPrv_allocdtl_id(Long prv_allocdtl_id) {
//		this.prv_allocdtl_id = prv_allocdtl_id;
//	}
//
//	public Long getSmp_prvqty() {
//		return smp_prvqty;
//	}
//
//	public void setSmp_prvqty(Long smp_prvqty) {
//		this.smp_prvqty = smp_prvqty;
//	}
//
//	public Long getSmp_currqty() {
//		return smp_currqty;
//	}
//
//	public void setSmp_currqty(Long smp_currqty) {
//		this.smp_currqty = smp_currqty;
//	}
//
////	public Long getRow_id() {
////		return row_id;
////	}
////
////	public void setRow_id(Long row_id) {
////		this.row_id = row_id;
////	}
//
//	
//	
//}
