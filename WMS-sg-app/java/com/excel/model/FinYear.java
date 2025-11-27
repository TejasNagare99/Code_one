package com.excel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.excel.utility.MedicoResources;

@Entity
@Table(name="FINYEAR")
public class FinYear implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FIN_YEAR")
	private String fin_year;
	
	@Column(name="FIN_YEAR_NAME")
	private String fin_year_name;

	@Column(name="FIN_COMPANY")
	private String fin_company;
	
	@Column(name="FIN_START_DATE")
	private Date fin_start_date;

	@Column(name="FIN_END_DATE")
	private Date fin_end_date;
	
	@Column(name="FIN_CURRENT")
	private String fin_current;
	
	@Column(name="FIN_status")
	private String fin_status;
	
	@Formula(value = "concat(FIN_START_DATE, FIN_END_DATE)")
	private String fullDate;
	
	@Column(name="GST_IND")
	private String gst_ind;
	
	@Column(name="GST_CURR_YEAR")
	private String gst_curr_year;
	
	@Column(name="GST_START_DATE")
	private Date gst_start_date;
	
	@Column(name="VAT_TRANSITION_END")
	private Date vat_transition_end;	
	
	
	
	
	public String getFin_year_name() {
		return fin_year_name;
	}

	public void setFin_year_name(String fin_year_name) {
		this.fin_year_name = fin_year_name;
	}

	public String getFin_company() {
		return fin_company;
	}

	public void setFin_company(String fin_company) {
		this.fin_company = fin_company;
	}

	public String getFullDate() {
		fullDate=MedicoResources.getRptDateFormat(fin_start_date.toString())
				+" To "+
				MedicoResources.getRptDateFormat(fin_end_date.toString());
		return fullDate;
	}

	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public Date getFin_start_date() {
		return fin_start_date;
	}

	public void setFin_start_date(Date fin_start_date) {
		this.fin_start_date = fin_start_date;
	}

	public Date getFin_end_date() {
		return fin_end_date;
	}

	public void setFin_end_date(Date fin_end_date) {
		this.fin_end_date = fin_end_date;
	}

	public String getFin_current() {
		return fin_current;
	}

	public void setFin_current(String fin_current) {
		this.fin_current = fin_current;
	}

	public String getFin_status() {
		return fin_status;
	}

	public void setFin_status(String fin_status) {
		this.fin_status = fin_status;
	}

	public String getGst_ind() {
		return gst_ind;
	}

	public void setGst_ind(String gst_ind) {
		this.gst_ind = gst_ind;
	}

	public String getGst_curr_year() {
		return gst_curr_year;
	}

	public void setGst_curr_year(String gst_curr_year) {
		this.gst_curr_year = gst_curr_year;
	}

	public Date getGst_start_date() {
		return gst_start_date;
	}

	public void setGst_start_date(Date gst_start_date) {
		this.gst_start_date = gst_start_date;
	}

	public Date getVat_transition_end() {
		return vat_transition_end;
	}

	public void setVat_transition_end(Date vat_transition_end) {
		this.vat_transition_end = vat_transition_end;
	}
	
}

