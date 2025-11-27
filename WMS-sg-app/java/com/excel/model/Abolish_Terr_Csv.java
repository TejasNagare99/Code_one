package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ABOLISH_TERR_CSV")
public class Abolish_Terr_Csv {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "slno")
	private Long slno;

	@Column(name = "Div_Name")
	private String div_name;

	@Column(name = "Div_id")
	private Long div_id;

	@Column(name = "Terr_Code")
	private String terr_code;

	@Column(name = "Remarks")
	private String remarks;

	@Column(name = "abol_ins_usr_id")
	private String abol_ins_usr_id;

	@Column(name = "abol_mod_usr_id")
	private String abol_mod_usr_id;

	@Column(name = "abol_ins_dt")
	private Date abol_ins_dt;

	@Column(name = "abol_mod_dt")
	private Date abol_mod_dt;

	@Column(name = "abol_status")
	private String abol_status;

	@Column(name = "abol_fieldstaff_ind")
	private String abol_fieldstaff_ind;

	@Column(name = "abol_fstaff_id")
	private int abol_fstaff_id;

	@Column(name = "filename")
	private String filename;
	
	@Column(name = "csv_rownum")
	private int csv_rownum;
	
	public int getCsv_rownum() {
		return csv_rownum;
	}

	public void setCsv_rownum(int csv_rownum) {
		this.csv_rownum = csv_rownum;
	}

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public String getDiv_name() {
		return div_name;
	}

	public void setDiv_name(String div_name) {
		this.div_name = div_name;
	}

	

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getTerr_code() {
		return terr_code;
	}

	public void setTerr_code(String terr_code) {
		this.terr_code = terr_code;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAbol_ins_usr_id() {
		return abol_ins_usr_id;
	}

	public void setAbol_ins_usr_id(String abol_ins_usr_id) {
		this.abol_ins_usr_id = abol_ins_usr_id;
	}

	public String getAbol_mod_usr_id() {
		return abol_mod_usr_id;
	}

	public void setAbol_mod_usr_id(String abol_mod_usr_id) {
		this.abol_mod_usr_id = abol_mod_usr_id;
	}

	public Date getAbol_ins_dt() {
		return abol_ins_dt;
	}

	public void setAbol_ins_dt(Date abol_ins_dt) {
		this.abol_ins_dt = abol_ins_dt;
	}

	public Date getAbol_mod_dt() {
		return abol_mod_dt;
	}

	public void setAbol_mod_dt(Date abol_mod_dt) {
		this.abol_mod_dt = abol_mod_dt;
	}

	public String getAbol_status() {
		return abol_status;
	}

	public void setAbol_status(String abol_status) {
		this.abol_status = abol_status;
	}

	public String getAbol_fieldstaff_ind() {
		return abol_fieldstaff_ind;
	}

	public void setAbol_fieldstaff_ind(String abol_fieldstaff_ind) {
		this.abol_fieldstaff_ind = abol_fieldstaff_ind;
	}

	public int getAbol_fstaff_id() {
		return abol_fstaff_id;
	}

	public void setAbol_fstaff_id(int abol_fstaff_id) {
		this.abol_fstaff_id = abol_fstaff_id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
