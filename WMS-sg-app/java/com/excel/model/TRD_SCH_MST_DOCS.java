package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TRD_SCH_MST_DOCS")
public class TRD_SCH_MST_DOCS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SLNO")
	private long SLNO;

	@Column(name = "TRD_SCH_SLNO")
	private long trd_sch_slno;
	
	@Column(name = "FILENAME")
	private String filename;
	
	@Column(name = "FILEPATH")
	private String filepath;
	
	@Column(name = "FILETYPE")
	private String filetype;
	
	@Column(name = "DOC_ins_dt")
	private Date doc_ins_dt;
	
	@Column(name = "DOC_ins_usr_id")
	private String doc_ins_usr_id;
	
	@Column(name = "DOC_ins_ip_addr")
	private String doc_ins_ip_addr;
	
	
	@Column(name = "cycle_no")
	private String cycle_no;

	
	
	public String getCycle_no() {
		return cycle_no;
	}

	public void setCycle_no(String cycle_no) {
		this.cycle_no = cycle_no;
	}

	@Override
	public String toString() {
		return "TRD_SCH_MST_DOCS [SLNO=" + SLNO + ", trd_sch_slno=" + trd_sch_slno + ", filename=" + filename
				+ ", filepath=" + filepath + ", filetype=" + filetype + ", doc_ins_dt=" + doc_ins_dt
				+ ", doc_ins_usr_id=" + doc_ins_usr_id + ", doc_ins_ip_addr=" + doc_ins_ip_addr + ", cycle_no="
				+ cycle_no + "]";
	}

	public long getSLNO() {
		return SLNO;
	}

	public void setSLNO(long sLNO) {
		SLNO = sLNO;
	}

	public long getTrd_sch_slno() {
		return trd_sch_slno;
	}

	public void setTrd_sch_slno(long trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public Date getDoc_ins_dt() {
		return doc_ins_dt;
	}

	public void setDoc_ins_dt(Date doc_ins_dt) {
		this.doc_ins_dt = doc_ins_dt;
	}

	public String getDoc_ins_usr_id() {
		return doc_ins_usr_id;
	}

	public void setDoc_ins_usr_id(String doc_ins_usr_id) {
		this.doc_ins_usr_id = doc_ins_usr_id;
	}

	public String getDoc_ins_ip_addr() {
		return doc_ins_ip_addr;
	}

	public void setDoc_ins_ip_addr(String doc_ins_ip_addr) {
		this.doc_ins_ip_addr = doc_ins_ip_addr;
	}


	
	

	

}
