package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ARTICLE_SCH_VALID_EXT")
public class Art_Sch_Vld_Ext {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SLNO")
	private Long slno;
	@Column(name = "SCHEME_SLNO")
	private Long scheme_slno;
	@Column(name = "VALIDITY_EXT_DT")
	private Date validity_ext_dt;
	@Column(name = "DOC_TO_REPLACE")
	private Long doc_to_replace;
	@Column(name = "FILENAME")
	private String filename;
	@Column(name = "FILE_PATH")
	private String file_path;
	@Column(name = "FILETYPE")
	private String file_type;
	@Column(name = "REMARKS")
	private String remarks;
	@Column(name = "COMPANY_CD")
	private String company_cd;
	@Column(name = "FIN_YEAR")
	private String fin_year;
	@Column(name = "SCHEME_DIV_ID")
	private Long scheme_div_id;
	@Column(name = "EXT_STATUS")
	private String ext_status;
	@Column(name = "EXT_APPR_STATUS")
	private String ext_appr_status;
	@Column(name = "EXT_ins_usr_id")
	private String ext_ins_usr_id;
	@Column(name = "EXT_ins_dt")
	private Date ext_ins_dt;
	@Column(name = "EXT_mod_usr_id")
	private String ext_mod_usr_id;
	@Column(name = "EXT_mod_usr_dt")
	private Date ext_mod_usr_dt;
	
	@Transient private String scheme_name;
	@Transient private Date apply_invoice_from;
	@Transient private Date valid_from;
	@Transient private Date valid_to;
	
	public Date getApply_invoice_from() {
		return apply_invoice_from;
	}
	public void setApply_invoice_from(Date apply_invoice_from) {
		this.apply_invoice_from = apply_invoice_from;
	}
	public Date getValid_from() {
		return valid_from;
	}
	public void setValid_from(Date valid_from) {
		this.valid_from = valid_from;
	}
	public Date getValid_to() {
		return valid_to;
	}
	public void setValid_to(Date valid_to) {
		this.valid_to = valid_to;
	}
	public String getScheme_name() {
		return scheme_name;
	}
	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}

	public Long getDoc_to_replace() {
		return doc_to_replace;
	}

	public void setDoc_to_replace(Long doc_to_replace) {
		this.doc_to_replace = doc_to_replace;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public Long getScheme_slno() {
		return scheme_slno;
	}

	public void setScheme_slno(Long scheme_slno) {
		this.scheme_slno = scheme_slno;
	}

	public Date getValidity_ext_dt() {
		return validity_ext_dt;
	}

	public void setValidity_ext_dt(Date validity_ext_dt) {
		this.validity_ext_dt = validity_ext_dt;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getExt_status() {
		return ext_status;
	}

	public void setExt_status(String ext_status) {
		this.ext_status = ext_status;
	}

	public String getExt_appr_status() {
		return ext_appr_status;
	}

	public void setExt_appr_status(String ext_appr_status) {
		this.ext_appr_status = ext_appr_status;
	}

	public String getExt_ins_usr_id() {
		return ext_ins_usr_id;
	}

	public void setExt_ins_usr_id(String ext_ins_usr_id) {
		this.ext_ins_usr_id = ext_ins_usr_id;
	}

	public Date getExt_ins_dt() {
		return ext_ins_dt;
	}

	public void setExt_ins_dt(Date ext_ins_dt) {
		this.ext_ins_dt = ext_ins_dt;
	}

	public String getExt_mod_usr_id() {
		return ext_mod_usr_id;
	}

	public void setExt_mod_usr_id(String ext_mod_usr_id) {
		this.ext_mod_usr_id = ext_mod_usr_id;
	}

	public Date getExt_mod_usr_dt() {
		return ext_mod_usr_dt;
	}

	public void setExt_mod_usr_dt(Date ext_mod_usr_dt) {
		this.ext_mod_usr_dt = ext_mod_usr_dt;
	}

	public String getCompany_cd() {
		return company_cd;
	}

	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public Long getScheme_div_id() {
		return scheme_div_id;
	}

	public void setScheme_div_id(Long scheme_div_id) {
		this.scheme_div_id = scheme_div_id;
	}

}
