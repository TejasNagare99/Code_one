package com.excel.bean;

import java.math.BigDecimal;

public class ErpIaaGrn_bean {

	private Long Adjustment_No;
	private String Wms_Reference_No;
	private String Iaa_Confirmation_flag;
	private String organization;
	private String Description;
	private String ReasonCode;
	private String Out_Product;
	private String Out_Batch;
	private BigDecimal Out_Qty;
	private String Out_Stock_type;
	private String out_storage_condition;
	private String In_Product;
	private String In_Batch;
	private BigDecimal In_Qty;
	private String In_Stock_type;
	private String in_storage_condition;
	private String stk_adj_no;
	private Long grn_id;
	public Long getAdjustment_No() {
		return Adjustment_No;
	}
	public void setAdjustment_No(Long adjustment_No) {
		Adjustment_No = adjustment_No;
	}
	
	public String getIaa_Confirmation_flag() {
		return Iaa_Confirmation_flag;
	}
	public void setIaa_Confirmation_flag(String iaa_Confirmation_flag) {
		Iaa_Confirmation_flag = iaa_Confirmation_flag;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getReasonCode() {
		return ReasonCode;
	}
	public void setReasonCode(String reasonCode) {
		ReasonCode = reasonCode;
	}
	public String getOut_Product() {
		return Out_Product;
	}
	public void setOut_Product(String out_Product) {
		Out_Product = out_Product;
	}
	public String getOut_Batch() {
		return Out_Batch;
	}
	public void setOut_Batch(String out_Batch) {
		Out_Batch = out_Batch;
	}
	public BigDecimal getOut_Qty() {
		return Out_Qty;
	}
	public void setOut_Qty(BigDecimal out_Qty) {
		Out_Qty = out_Qty;
	}
	public String getOut_Stock_type() {
		return Out_Stock_type;
	}
	public void setOut_Stock_type(String out_Stock_type) {
		Out_Stock_type = out_Stock_type;
	}
	public String getIn_Product() {
		return In_Product;
	}
	public void setIn_Product(String in_Product) {
		In_Product = in_Product;
	}
	public String getIn_Batch() {
		return In_Batch;
	}
	public void setIn_Batch(String in_Batch) {
		In_Batch = in_Batch;
	}
	public BigDecimal getIn_Qty() {
		return In_Qty;
	}
	public void setIn_Qty(BigDecimal in_Qty) {
		In_Qty = in_Qty;
	}
	public String getIn_Stock_type() {
		return In_Stock_type;
	}
	public void setIn_Stock_type(String in_Stock_type) {
		In_Stock_type = in_Stock_type;
	}
	public String getStk_adj_no() {
		return stk_adj_no;
	}
	public void setStk_adj_no(String stk_adj_no) {
		this.stk_adj_no = stk_adj_no;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getWms_Reference_No() {
		return Wms_Reference_No;
	}
	public void setWms_Reference_No(String wms_Reference_No) {
		Wms_Reference_No = wms_Reference_No;
	}
	public Long getGrn_id() {
		return grn_id;
	}
	public void setGrn_id(Long grn_id) {
		this.grn_id = grn_id;
	}
	public String getOut_storage_condition() {
		return out_storage_condition;
	}
	public void setOut_storage_condition(String out_storage_condition) {
		this.out_storage_condition = out_storage_condition;
	}
	public String getIn_storage_condition() {
		return in_storage_condition;
	}
	public void setIn_storage_condition(String in_storage_condition) {
		this.in_storage_condition = in_storage_condition;
	}
	
	
}
