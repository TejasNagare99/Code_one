package com.excel.bean;

import java.math.BigDecimal;

public class Prod_MasterBean {
	private String product_code;
	private Long product_qty;
	private Long product_id;
	private BigDecimal product_rate;
	private Long alt_div_id;
	private Long inv_grp;
	private BigDecimal min_alloc_qty;
	
	private String product_name;
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public long getProduct_qty() {
		return product_qty;
	}
	public void setProduct_qty(Long product_qty) {
		this.product_qty = product_qty;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public BigDecimal getProduct_rate() {
		return product_rate;
	}
	public void setProduct_rate(BigDecimal product_rate) {
		this.product_rate = product_rate;
	}
	public Long getAlt_div_id() {
		return alt_div_id;
	}
	public void setAlt_div_id(Long alt_div_id) {
		this.alt_div_id = alt_div_id;
	}
	public Long getInv_grp() {
		return inv_grp;
	}
	public void setInv_grp(Long inv_grp) {
		this.inv_grp = inv_grp;
	}
	public void setMin_alloc_qty(BigDecimal min_alloc_qty) {
		this.min_alloc_qty = min_alloc_qty;
	}
	public BigDecimal getMin_alloc_qty() {
		return min_alloc_qty;
	}

	
}
