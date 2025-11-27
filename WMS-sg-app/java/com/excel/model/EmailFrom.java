package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "emailfrom")
@Entity
public class EmailFrom {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TRAN_TYPE")
	private String tran_type;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "MAIL_PASSWORD")
	private String mail_password;
	
	@Column(name = "PORT")
	private String port;
	
	@Column(name = "HOST")
	private String host;
	
	@Column(name = "TLS_SSL")
	private String tls_sls;
	
	@Column(name = "AUTH")
	private String auth;

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

	public String getMail_password() {
		return mail_password;
	}

	public void setMail_password(String mail_password) {
		this.mail_password = mail_password;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String isTls_sls() {
		return tls_sls;
	}

	public void setTls_sls(String tls_sls) {
		this.tls_sls = tls_sls;
	}

	public String isAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	
	
}
