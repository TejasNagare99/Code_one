package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_ID_TRANWISE")
public class EmailId_Tranwise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SLNO_")
	private Long slno;
	
	@Column(name = "TRAN_TYPE")
	private String tran_type;
	
	@Column(name = "EMAIL_SRNO")
	private Integer email_srno;
	
	@Column(name = "EMAIL_ID")
	private String email_id;
	
	@Column(name="FIN_YEAR")
	private String fin_year;
	
	@Column(name="TRAN_ID")
	private Long tran_id;

	
	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public Long getTran_id() {
		return tran_id;
	}

	public void setTran_id(Long tran_id) {
		this.tran_id = tran_id;
	}

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public Integer getEmail_srno() {
		return email_srno;
	}

	public void setEmail_srno(Integer email_srno) {
		this.email_srno = email_srno;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	
	
}
