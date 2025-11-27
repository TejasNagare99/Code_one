package com.excel.bean;

import java.math.BigDecimal;

public class ErpStockAdjBean {

	private String adjustmnet_no;
	private String wms_reference_no;
	private String iaa_confirmation_flag;
	private String location;
	private String description;
	private String reasoncode;
	private String out_product;
	private String out_batch;
	private BigDecimal out_qty;
	private String out_stock_type;
	private String in_product;
	private String in_batch;
	private BigDecimal in_qty;
	private String in_stock_type;
	private Long br_stkajid;
	public String getAdjustmnet_no() {
		return adjustmnet_no;
	}
	public void setAdjustmnet_no(String adjustmnet_no) {
		this.adjustmnet_no = adjustmnet_no;
	}
	public String getWms_reference_no() {
		return wms_reference_no;
	}
	public void setWms_reference_no(String wms_reference_no) {
		this.wms_reference_no = wms_reference_no;
	}
	public String getIaa_confirmation_flag() {
		return iaa_confirmation_flag;
	}
	public void setIaa_confirmation_flag(String iaa_confirmation_flag) {
		this.iaa_confirmation_flag = iaa_confirmation_flag;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReasoncode() {
		return reasoncode;
	}
	public void setReasoncode(String reasoncode) {
		this.reasoncode = reasoncode;
	}
	public String getOut_product() {
		return out_product;
	}
	public void setOut_product(String out_product) {
		this.out_product = out_product;
	}
	public String getOut_batch() {
		return out_batch;
	}
	public void setOut_batch(String out_batch) {
		this.out_batch = out_batch;
	}
	public BigDecimal getOut_qty() {
		return out_qty;
	}
	public void setOut_qty(BigDecimal out_qty) {
		this.out_qty = out_qty;
	}
	public String getOut_stock_type() {
		return out_stock_type;
	}
	public void setOut_stock_type(String out_stock_type) {
		this.out_stock_type = out_stock_type;
	}
	public String getIn_product() {
		return in_product;
	}
	public void setIn_product(String in_product) {
		this.in_product = in_product;
	}
	public String getIn_batch() {
		return in_batch;
	}
	public void setIn_batch(String in_batch) {
		this.in_batch = in_batch;
	}
	public BigDecimal getIn_qty() {
		return in_qty;
	}
	public void setIn_qty(BigDecimal in_qty) {
		this.in_qty = in_qty;
	}
	public String getIn_stock_type() {
		return in_stock_type;
	}
	public void setIn_stock_type(String in_stock_type) {
		this.in_stock_type = in_stock_type;
	}
	public Long getBr_stkajid() {
		return br_stkajid;
	}
	public void setBr_stkajid(Long br_stkajid) {
		this.br_stkajid = br_stkajid;
	}
	
}
