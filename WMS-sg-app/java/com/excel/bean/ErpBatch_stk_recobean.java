package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ErpBatch_stk_recobean {
	
	private String reco_date;
	
	private String organization;
	
	private String product_code;
	
	private String product_name;
	
	private String batch_no;
	
	private String stock_type;
	
	private BigDecimal qty;
	
	
	//from medico
	private String fin_year;
	

	public String getReco_date() {
		return reco_date;
	}

	public void setReco_date(String reco_date) {
		this.reco_date = reco_date;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}



	

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}
	
	
}
