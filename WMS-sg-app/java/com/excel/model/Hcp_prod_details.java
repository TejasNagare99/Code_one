package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;



@Table(name = "dg_blk_hcp_alloc_stock_showing")
@Entity
@NamedStoredProcedureQuery(name = "call_dg_blk_hcp_alloc_stock_showing", procedureName = "DG_BLK_HCP_ALLOC_STOCK_SHOWING", parameters = {
		@StoredProcedureParameter(name = "MBLK_HCP_REQ_ID", mode = ParameterMode.IN, type = Long.class)

}, resultClasses = Hcp_prod_details.class)
public class Hcp_prod_details {

	
	@Id
	@Column(name = "ROW")
	private String row;
	
	@Column(name = "BLK_HCP_REQ_ID")
	private String blk_hcp_req_id;
	
	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name = "PROD_ID1")
	private String prod_id1;
	
	@Column(name = "TOTALLOCQTY")
	private String totallocqty;
	
	@Column(name = "stock")
	private String stock;
	
	@Column(name = "DIFFQTY")
	private String diffqty;
	
	@Column(name = "WITH_HELD_QTY")
	private String with_held_qty;

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getBlk_hcp_req_id() {
		return blk_hcp_req_id;
	}

	public void setBlk_hcp_req_id(String blk_hcp_req_id) {
		this.blk_hcp_req_id = blk_hcp_req_id;
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

	public String getProd_id1() {
		return prod_id1;
	}

	public void setProd_id1(String prod_id1) {
		this.prod_id1 = prod_id1;
	}

	public String getTotallocqty() {
		return totallocqty;
	}

	public void setTotallocqty(String totallocqty) {
		this.totallocqty = totallocqty;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getDiffqty() {
		return diffqty;
	}

	public void setDiffqty(String diffqty) {
		this.diffqty = diffqty;
	}

	public String getWith_held_qty() {
		return with_held_qty;
	}

	public void setWith_held_qty(String with_held_qty) {
		this.with_held_qty = with_held_qty;
	}
	
	
	
}
