package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ALLOC_REQ_IMAGES")
public class AllocReqImages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_REQ_IMAGE_ID")
	private Long alloc_req_image_id;
	
	@Column(name = "ALLOC_REQ_ID")
	private Long alloc_req_id;
	
	@Column(name = "ALLOC_REQ_DATE")
	private Date alloc_req_date;
	
	@Column(name = "FIN_YEAR")
	private String fin_year;
	
	@Column(name = "PERIOD_CODE")
	private String period_code;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "division")
	private String division; 
	
	@Column(name = "IMAGE_FOLDER")
	private String image_folder;
	
	@Column(name = "IMAGE_NAME")
	private String image_name;

	public Long getAlloc_req_id() {
		return alloc_req_id;
	}

	public void setAlloc_req_id(Long alloc_req_id) {
		this.alloc_req_id = alloc_req_id;
	}

	public Date getAlloc_req_date() {
		return alloc_req_date;
	}

	public void setAlloc_req_date(Date alloc_req_date) {
		this.alloc_req_date = alloc_req_date;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public String getPeriod_code() {
		return period_code;
	}

	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String getImage_folder() {
		return image_folder;
	}

	public void setImage_folder(String image_folder) {
		this.image_folder = image_folder;
	}

	public Long getAlloc_req_image_id() {
		return alloc_req_image_id;
	}

	public void setAlloc_req_image_id(Long alloc_req_image_id) {
		this.alloc_req_image_id = alloc_req_image_id;
	}
	
}
