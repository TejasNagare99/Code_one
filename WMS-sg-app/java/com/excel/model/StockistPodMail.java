package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "STOCKIST_POD_EMAIL")
public class StockistPodMail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FIN_YEAR")
	private Long fin_year;
	
	@Column(name = "DSP_ID")
	private Long dsp_id;
	
	@Column(name = "EMAIL_SEND_TO_DOCTOR_DATE")
	private Date email_send_to_doctor_date;
	
	@Column(name = "RECD_BY_DOCTOR_FLG")
	private String recd_by_doctor_flg;
	
	@Column(name = "RECD_BY_DOCTOR_ACTION_DT")
	private Date recd_by_doctor_action_dt;
	
	@Column(name = "INSERT_DT")
	private Date insert_dt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFin_year() {
		return fin_year;
	}

	public void setFin_year(Long fin_year) {
		this.fin_year = fin_year;
	}

	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}

	public Date getEmail_send_to_doctor_date() {
		return email_send_to_doctor_date;
	}

	public void setEmail_send_to_doctor_date(Date email_send_to_doctor_date) {
		this.email_send_to_doctor_date = email_send_to_doctor_date;
	}

	public String getRecd_by_doctor_flg() {
		return recd_by_doctor_flg;
	}

	public void setRecd_by_doctor_flg(String recd_by_doctor_flg) {
		this.recd_by_doctor_flg = recd_by_doctor_flg;
	}

	public Date getRecd_by_doctor_action_dt() {
		return recd_by_doctor_action_dt;
	}

	public void setRecd_by_doctor_action_dt(Date recd_by_doctor_action_dt) {
		this.recd_by_doctor_action_dt = recd_by_doctor_action_dt;
	}

	public Date getInsert_dt() {
		return insert_dt;
	}

	public void setInsert_dt(Date insert_dt) {
		this.insert_dt = insert_dt;
	}
	
	

}
