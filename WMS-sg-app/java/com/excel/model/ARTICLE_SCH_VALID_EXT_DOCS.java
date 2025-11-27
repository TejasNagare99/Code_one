package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "ARTICLE_SCH_VALID_EXT_DOCS")
public class ARTICLE_SCH_VALID_EXT_DOCS {

	@Id
	@Column(name = "SLNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String slno;
	
	@Column(name = "SCHEME_EXC_ID")
	private String scheme_exc_id;
	
	@Column(name = "SCHEME_SLNO")
	private String scheme_slno;
	
	@Column(name = "FILENAME")
	private String filename;
	
	@Column(name = "FILETYPE")
	private String filetype;
	
	@Column(name = "FILE_PATH")
	private String file_path;
	
	@Column(name = "DOC_ins_usr_id")
	private String doc_ins_usr_id;
	
	@Column(name = "DOC_ins_dt")
	private Date doc_ins_dt;
	
	@Column(name = "DOC_mod_usr_id")
	private String doc_mod_usr_id;
	
	@Column(name = "DOC_mod_usr_dt")
	private Date doc_mod_usr_dt;
	
	@Column(name = "INS_ip_addr")
	private String ins_ip_addr;
	
	
	public String getIns_ip_addr() {
		return ins_ip_addr;
	}

	public void setIns_ip_addr(String ins_ip_addr) {
		this.ins_ip_addr = ins_ip_addr;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getScheme_exc_id() {
		return scheme_exc_id;
	}

	public void setScheme_exc_id(String scheme_exc_id) {
		this.scheme_exc_id = scheme_exc_id;
	}

	public String getScheme_slno() {
		return scheme_slno;
	}

	public void setScheme_slno(String scheme_slno) {
		this.scheme_slno = scheme_slno;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getDoc_ins_usr_id() {
		return doc_ins_usr_id;
	}

	public void setDoc_ins_usr_id(String doc_ins_usr_id) {
		this.doc_ins_usr_id = doc_ins_usr_id;
	}

	public Date getDoc_ins_dt() {
		return doc_ins_dt;
	}

	public void setDoc_ins_dt(Date doc_ins_dt) {
		this.doc_ins_dt = doc_ins_dt;
	}

	public String getDoc_mod_usr_id() {
		return doc_mod_usr_id;
	}

	public void setDoc_mod_usr_id(String doc_mod_usr_id) {
		this.doc_mod_usr_id = doc_mod_usr_id;
	}

	public Date getDoc_mod_usr_dt() {
		return doc_mod_usr_dt;
	}

	public void setDoc_mod_usr_dt(Date doc_mod_usr_dt) {
		this.doc_mod_usr_dt = doc_mod_usr_dt;
	}

	@Override
	public String toString() {
		return "ARTICLE_SCH_VALID_EXT_DOCS [slno=" + slno + ", scheme_exc_id=" + scheme_exc_id + ", scheme_slno="
				+ scheme_slno + ", filename=" + filename + ", filetype=" + filetype + ", file_path=" + file_path
				+ ", doc_ins_usr_id=" + doc_ins_usr_id + ", doc_ins_dt=" + doc_ins_dt + ", doc_mod_usr_id="
				+ doc_mod_usr_id + ", doc_mod_usr_dt=" + doc_mod_usr_dt + "]";
	}
	
	
	
	
}
