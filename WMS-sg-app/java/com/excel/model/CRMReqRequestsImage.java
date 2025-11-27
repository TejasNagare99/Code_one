package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CRM_REQ_REQUESTS_IMAGE")
public class CRMReqRequestsImage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMAGE_ID")
	private Long image_id;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;

	@Column(name = "CRM_ID")
	private Long crm_id;

	@Column(name = "IMAGE_PATH")
	private String image_path;

	@Column(name = "VIEW_PATH")
	private String view_path;

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public Long getCrm_id() {
		return crm_id;
	}

	public void setCrm_id(Long crm_id) {
		this.crm_id = crm_id;
	}

	public Long getImage_id() {
		return image_id;
	}

	public void setImage_id(Long image_id) {
		this.image_id = image_id;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getView_path() {
		return view_path;
	}

	public void setView_path(String view_path) {
		this.view_path = view_path;
	}
	
	
}
