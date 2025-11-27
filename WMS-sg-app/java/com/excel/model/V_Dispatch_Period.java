package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_DISPATCH_PERIOD")
public class V_Dispatch_Period {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PERIOD_ID")
	private Long period_id;
	
	@Column(name = "PERIOD_NAME")
	private String period_name;

	@Column(name = "PERIOD_ALT_NAME")
	private String period_alt_name;
	
	@Column(name="PERIOD_CODE")
	private String period_code;
	
	@Column(name="PERIOD_FIN_YEAR")
	private String period_fin_year;
	
	@Column(name="PERIOD_CURRENT")
	private String period_current;
	
	@Column(name="PERIOD_STATUS")
	private String period_status;
	
	@Column(name="PERIOD_COMPANY")
	private String period_company;
	
	

	public String getPeriod_status() {
		return period_status;
	}

	public void setPeriod_status(String period_status) {
		this.period_status = period_status;
	}

	public String getPeriod_alt_name() {
		return period_alt_name;
	}

	public void setPeriod_alt_name(String period_alt_name) {
		this.period_alt_name = period_alt_name;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public Long getPeriod_id() {
		return period_id;
	}

	public void setPeriod_id(Long period_id) {
		this.period_id = period_id;
	}

	public String getPeriod_name() {
		return period_name;
	}

	public void setPeriod_name(String period_name) {
		this.period_name = period_name;
	}

	public String getPeriod_fin_year() {
		return period_fin_year;
	}

	public void setPeriod_fin_year(String period_fin_year) {
		this.period_fin_year = period_fin_year;
	}

	public String getPeriod_current() {
		return period_current;
	}

	public void setPeriod_current(String period_current) {
		this.period_current = period_current;
	}

	public String getPeriod_company() {
		return period_company;
	}

	public void setPeriod_company(String period_company) {
		this.period_company = period_company;
	}
	
	
	
	 
	
}
