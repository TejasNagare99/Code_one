package com.excel.bean;

import java.math.BigDecimal;

public class ErpStockwithdrawalBean {

	private String header_id;
	private String Organization;
	private String product_type;
	private String Storage_Condition;
	private String Document_Type;
	private String Buisness_Partner;
	private String Ship_To;
	private String Bill_To;
	private String Warehouse;
	private Integer Obd_Header_num;
	private String Ref_no_po;
	private String Order_rec_dateTime;
	private String Expected_Delivery_dateTime;
	private String Description;
	private Integer Obd_Line_Item_num;
	private String Product;
	private BigDecimal Sales_Order_Quantity;
	private String Lot_no;
	private String Exp_Date;
	public String getOrganization() {
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getStorage_Condition() {
		return Storage_Condition;
	}
	public void setStorage_Condition(String storage_Condition) {
		Storage_Condition = storage_Condition;
	}
	public String getDocument_Type() {
		return Document_Type;
	}
	public void setDocument_Type(String document_Type) {
		Document_Type = document_Type;
	}
	public String getBuisness_Partner() {
		return Buisness_Partner;
	}
	public void setBuisness_Partner(String buisness_Partner) {
		Buisness_Partner = buisness_Partner;
	}
	public String getShip_To() {
		return Ship_To;
	}
	public void setShip_To(String ship_To) {
		Ship_To = ship_To;
	}
	public String getBill_To() {
		return Bill_To;
	}
	public void setBill_To(String bill_To) {
		Bill_To = bill_To;
	}
	public String getWarehouse() {
		return Warehouse;
	}
	public void setWarehouse(String warehouse) {
		Warehouse = warehouse;
	}
	
	public Integer getObd_Header_num() {
		return Obd_Header_num;
	}
	public void setObd_Header_num(Integer obd_Header_num) {
		Obd_Header_num = obd_Header_num;
	}
	public String getRef_no_po() {
		return Ref_no_po;
	}
	public void setRef_no_po(String ref_no_po) {
		Ref_no_po = ref_no_po;
	}
	public String getOrder_rec_dateTime() {
		return Order_rec_dateTime;
	}
	public void setOrder_rec_dateTime(String order_rec_dateTime) {
		Order_rec_dateTime = order_rec_dateTime;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	

	public Integer getObd_Line_Item_num() {
		return Obd_Line_Item_num;
	}
	public void setObd_Line_Item_num(Integer obd_Line_Item_num) {
		Obd_Line_Item_num = obd_Line_Item_num;
	}
	public String getProduct() {
		return Product;
	}
	public void setProduct(String product) {
		Product = product;
	}

	public BigDecimal getSales_Order_Quantity() {
		return Sales_Order_Quantity;
	}
	public void setSales_Order_Quantity(BigDecimal sales_Order_Quantity) {
		Sales_Order_Quantity = sales_Order_Quantity;
	}
	public String getLot_no() {
		return Lot_no;
	}
	public void setLot_no(String lot_no) {
		Lot_no = lot_no;
	}
	public String getExpected_Delivery_dateTime() {
		return Expected_Delivery_dateTime;
	}
	public void setExpected_Delivery_dateTime(String expected_Delivery_dateTime) {
		Expected_Delivery_dateTime = expected_Delivery_dateTime;
	}
	public String getExp_Date() {
		return Exp_Date;
	}
	public void setExp_Date(String exp_Date) {
		Exp_Date = exp_Date;
	}
	public String getHeader_id() {
		return header_id;
	}
	public void setHeader_id(String header_id) {
		this.header_id = header_id;
	}
	
	
	
	
}
