package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="BLK_HCP_REQ_HEADER")
@DynamicUpdate(value=true)
public class Blk_hcp_req_hdr {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BLK_HCP_REQ_ID")
	private Long blk_hcp_req_id;

	@Column(name="BLK_HCP_REQ_DATE")
	private Date blk_hcp_req_date;

	@Column(name="BLK_HCP_REQ_NO")
	private String blk_hcp_req_no;

	@Column(name="FIN_YEAR_ID")
	private String fin_year_id;

	@Column(name="COMPANY")
	private String company;

	@Column(name="DIV_ID")
	private Long div_id;

	@Column(name="NO_OF_DOCTORS")
	private Long no_of_doctors;

	@Column(name="BLK_INS_USR_ID")
	private String blk_ins_usr_id;

	@Column(name="BLK_MOD_USR_ID")
	private String blk_mod_usr_id;

	@Column(name="BLK_INS_DT")
	private Date blk_ins_dt;

	@Column(name="BLK_MOD_DT")
	private Date blk_mod_dt;

	@Column(name="BLK_INS_IP_ADD")
	private String blk_ins_ip_add;

	@Column(name="BLK_MOD_IP_ADD")
	private String blk_mod_ip_add;

	@Column(name="BLK_STATUS")
	private String 	blk_status;

	@Column(name="BLK_CSV_NAME")
	private String 	blk_csv_name;

	@Column(name="BLK_CSV_GEN_DATE")
	private Date blk_csv_gen_date;

	@Column(name="BLK_CSVGEN_USR_ID")
	private String blk_csvgen_usr_id;
	
	@Column(name="BLK_APPR_IND")
	private String blk_appr_ind;
	
	@Column(name="DOC_MST_TYPE")
	private String doc_mst_type;
	
	@Column(name="FILE_DOWNLOAD")
	private String file_download;
	
	@Column(name="FILE_UPLOAD")
	private String file_upload;
	
	@Column(name="BLK_CONFIRM_IND")
	private String blk_confirm_ind;
	
	@Column(name="DIVISION")
	private String division;

	
	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getBlk_confirm_ind() {
		return blk_confirm_ind;
	}

	public void setBlk_confirm_ind(String blk_confirm_ind) {
		this.blk_confirm_ind = blk_confirm_ind;
	}

	
	public String getBlk_appr_ind() {
		return blk_appr_ind;
	}

	public void setBlk_appr_ind(String blk_appr_ind) {
		this.blk_appr_ind = blk_appr_ind;
	}

	public String getDoc_mst_type() {
		return doc_mst_type;
	}

	public void setDoc_mst_type(String doc_mst_type) {
		this.doc_mst_type = doc_mst_type;
	}

	public String getFile_download() {
		return file_download;
	}

	public void setFile_download(String file_download) {
		this.file_download = file_download;
	}

	public String getFile_upload() {
		return file_upload;
	}

	public void setFile_upload(String file_upload) {
		this.file_upload = file_upload;
	}

	public Long getBlk_hcp_req_id() {
		return blk_hcp_req_id;
	}

	public void setBlk_hcp_req_id(Long blk_hcp_req_id) {
		this.blk_hcp_req_id = blk_hcp_req_id;
	}

	public Date getBlk_hcp_req_date() {
		return blk_hcp_req_date;
	}

	public void setBlk_hcp_req_date(Date blk_hcp_req_date) {
		this.blk_hcp_req_date = blk_hcp_req_date;
	}

	public String getBlk_hcp_req_no() {
		return blk_hcp_req_no;
	}

	public void setBlk_hcp_req_no(String blk_hcp_req_no) {
		this.blk_hcp_req_no = blk_hcp_req_no;
	}

	public String getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(String fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public Long getNo_of_doctors() {
		return no_of_doctors;
	}

	public void setNo_of_doctors(Long no_of_doctors) {
		this.no_of_doctors = no_of_doctors;
	}

	public String getBlk_ins_usr_id() {
		return blk_ins_usr_id;
	}

	public void setBlk_ins_usr_id(String blk_ins_usr_id) {
		this.blk_ins_usr_id = blk_ins_usr_id;
	}

	public String getBlk_mod_usr_id() {
		return blk_mod_usr_id;
	}

	public void setBlk_mod_usr_id(String blk_mod_usr_id) {
		this.blk_mod_usr_id = blk_mod_usr_id;
	}

	public Date getBlk_ins_dt() {
		return blk_ins_dt;
	}

	public void setBlk_ins_dt(Date blk_ins_dt) {
		this.blk_ins_dt = blk_ins_dt;
	}

	public Date getBlk_mod_dt() {
		return blk_mod_dt;
	}

	public void setBlk_mod_dt(Date blk_mod_dt) {
		this.blk_mod_dt = blk_mod_dt;
	}

	public String getBlk_ins_ip_add() {
		return blk_ins_ip_add;
	}

	public void setBlk_ins_ip_add(String blk_ins_ip_add) {
		this.blk_ins_ip_add = blk_ins_ip_add;
	}

	public String getBlk_mod_ip_add() {
		return blk_mod_ip_add;
	}

	public void setBlk_mod_ip_add(String blk_mod_ip_add) {
		this.blk_mod_ip_add = blk_mod_ip_add;
	}

	public String getBlk_status() {
		return blk_status;
	}

	public void setBlk_status(String blk_status) {
		this.blk_status = blk_status;
	}

	public String getBlk_csv_name() {
		return blk_csv_name;
	}

	public void setBlk_csv_name(String blk_csv_name) {
		this.blk_csv_name = blk_csv_name;
	}

	public Date getBlk_csv_gen_date() {
		return blk_csv_gen_date;
	}

	public void setBlk_csv_gen_date(Date blk_csv_gen_date) {
		this.blk_csv_gen_date = blk_csv_gen_date;
	}

	public String getBlk_csvgen_usr_id() {
		return blk_csvgen_usr_id;
	}

	public void setBlk_csvgen_usr_id(String blk_csvgen_usr_id) {
		this.blk_csvgen_usr_id = blk_csvgen_usr_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	

}
