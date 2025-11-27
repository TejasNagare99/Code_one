package com.excel.bean;

public class SkuQtySalesProductBean {

	
	public SkuQtySalesProductBean(String id, String sales_prodId, String billed, String free, String article_Qty,
			String sales_product_code, String sales_product_erpcode) {
		super();
		this.id = id;
		this.sales_prodId = sales_prodId;
		this.billed = billed;
		this.free = free;
		this.article_Qty = article_Qty;
		this.sales_product_code = sales_product_code;
		this.sales_product_erpcode = sales_product_erpcode;
	}
	
	
	@Override
	public String toString() {
		return "SkuQtySalesProductBean [id=" + id + ", sales_prodId=" + sales_prodId + ", billed=" + billed + ", free="
				+ free + ", article_Qty=" + article_Qty + "]";
	}
	String id;
	String sales_prodId;
	//String sales_product_code;
	String billed;
	String free;
	String article_Qty;
	
	String  sales_product_code;
	String  sales_product_erpcode;
	
	
	public String getSales_product_code() {
		return sales_product_code;
	}
	public void setSales_product_code(String sales_product_code) {
		this.sales_product_code = sales_product_code;
	}
	public String getSales_product_erpcode() {
		return sales_product_erpcode;
	}
	public void setSales_product_erpcode(String sales_product_erpcode) {
		this.sales_product_erpcode = sales_product_erpcode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSales_prodId() {
		return sales_prodId;
	}
	public void setSales_prodId(String sales_prodId) {
		this.sales_prodId = sales_prodId;
	}
//	public String getSales_product_code() {
//		return sales_product_code;
//	}
//	public void setSales_product_code(String sales_product_code) {
//		this.sales_product_code = sales_product_code;
//	}
	public String getBilled() {
		return billed;
	}
	public void setBilled(String billed) {
		this.billed = billed;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getArticle_Qty() {
		return article_Qty;
	}
	public void setArticle_Qty(String article_Qty) {
		this.article_Qty = article_Qty;
	}
	public SkuQtySalesProductBean(String id, String sales_prodId, String billed, String free, String article_Qty) {
		super();
		this.id = id;
		this.sales_prodId = sales_prodId;
		this.billed = billed;
		this.free = free;
		this.article_Qty = article_Qty;
	}
	public SkuQtySalesProductBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
