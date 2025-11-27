package com.excel.bean;

public class Sales_prod_details_bean {

	
	  String  billed;
      String  brand;
      String  article_Qty;
      String  sale_prod_name;
      
      
      
	@Override
	public String toString() {
		return "Sales_prod_details_bean [billed=" + billed + ", brand=" + brand + ", article_Qty=" + article_Qty
				+ ", sale_prod_name=" + sale_prod_name + "]";
	}
	public String getBilled() {
		return billed;
	}
	public void setBilled(String billed) {
		this.billed = billed;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getArticle_Qty() {
		return article_Qty;
	}
	public void setArticle_Qty(String article_Qtyl) {
		this.article_Qty = article_Qtyl;
	}
	public String getSale_prod_name() {
		return sale_prod_name;
	}
	public void setSale_prod_name(String sale_prod_name) {
		this.sale_prod_name = sale_prod_name;
	}
      
      
}

