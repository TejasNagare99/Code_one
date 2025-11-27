package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "EMAIL")
public class Email implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4944986829703474939L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "Tran_Type")
	private String tran_type;
	@Column(name = "COMPANY")
	private String company;
	@Column(name = "email")
	private String email;
	@Column(name = "allow_div_map_cd")
	private String allow_div_map_cd;


	public String getAllow_div_map_cd() {
	    return allow_div_map_cd;
	}

	public void setAllow_div_map_cd(String allow_div_map_cd) {
	    this.allow_div_map_cd = allow_div_map_cd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
