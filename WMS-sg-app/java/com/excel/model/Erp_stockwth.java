package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ERP_STOCKWTH")
public class Erp_stockwth {

					
	
	@Id
	@Column(name = "SWV_ID")
	private Long SWV_ID;
	
	@Column(name = "SWV_NO")
	private String swv_no;
	
	@Column(name = "SWV_DT")
	private String swv_dt;
	
	@Column(name = "SWV_COMPANY")
	private String swv_company;
	
	@Column(name = "SWV_FIN_YEAR")
	private String swv_fin_year;

	public Long getSWV_ID() {
		return SWV_ID;
	}

	public void setSWV_ID(Long sWV_ID) {
		SWV_ID = sWV_ID;
	}

	public String getSwv_no() {
		return swv_no;
	}

	public void setSwv_no(String swv_no) {
		this.swv_no = swv_no;
	}

	public String getSwv_dt() {
		return swv_dt;
	}

	public void setSwv_dt(String swv_dt) {
		this.swv_dt = swv_dt;
	}

	public String getSwv_company() {
		return swv_company;
	}

	public void setSwv_company(String swv_company) {
		this.swv_company = swv_company;
	}

	public String getSwv_fin_year() {
		return swv_fin_year;
	}

	public void setSwv_fin_year(String swv_fin_year) {
		this.swv_fin_year = swv_fin_year;
	}
	
	
}
	
