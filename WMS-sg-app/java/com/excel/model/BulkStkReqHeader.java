package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "BLK_STK_REQ_HEADER")
public class BulkStkReqHeader {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "BLK_STK_REQ_ID")
	private Long blk_stk_req_id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "BLK_STK_REQ_DATE")
	private Date blk_stk_req_date;
	
	@Column(name = "BLK_STK_REQ_NO")
	private String blk_stk_req_no;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "STK_MST_TYPE")
	private String stk_mst_type;
	
	@Column(name = "NO_OF_STOCKISTS")
	private Long no_of_stockists;
	
	@Column(name = "BLKSTK_CSV_NAME")
	private String blkstk_csv_name;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "BLKSTK_CSV_GEN_DATE")
	private Date blkstk_csv_gen_date;
	
	@Column(name = "BLKSTK_CSVGEN_usr_id")
	private String blkstk_csvgen_usr_id;
	
	@Column(name = "BLKSTK_APPR_IND")
	private String blkstk_appr_ind;
	@Column(name = "FILE_DOWNLOAD")
	private String file_download;
	@Column(name = "FILE_UPLOAD")
	private String file_upload;
	@Column(name = "BLKSTK_CONFIRM_IND")
	private String blkstk_confirm_ind;
	@Column(name = "BLKSTK_ins_usr_id")
	private String blkstk_ins_usr_id;
	@Column(name = "BLKSTK_mod_usr_id")
	private String blkstk_mod_usr_id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "BLKSTK_ins_dt")
	private Date blkstk_ins_dt;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "BLKSTK_mod_dt")
	private Date blkstk_mod_dt;
	
	@Column(name = "BLKSTK_ins_ip_add")
	private String blkstk_ins_ip_add;
	@Column(name = "BLKSTK_mod_ip_add")
	private String blkstk_mod_ip_add;
	@Column(name = "BLKSTK_status")
	private String blkstk_status;
	
	public Long getLoc_id() {
		return loc_id;
	}
	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}
	public Long getBlk_stk_req_id() {
		return blk_stk_req_id;
	}
	public void setBlk_stk_req_id(Long blk_stk_req_id) {
		this.blk_stk_req_id = blk_stk_req_id;
	}
	public Date getBlk_stk_req_date() {
		return blk_stk_req_date;
	}
	public void setBlk_stk_req_date(Date blk_stk_req_date) {
		this.blk_stk_req_date = blk_stk_req_date;
	}
	public String getBlk_stk_req_no() {
		return blk_stk_req_no;
	}
	public void setBlk_stk_req_no(String blk_stk_req_no) {
		this.blk_stk_req_no = blk_stk_req_no;
	}
	public Long getFin_year_id() {
		return fin_year_id;
	}
	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStk_mst_type() {
		return stk_mst_type;
	}
	public void setStk_mst_type(String stk_mst_type) {
		this.stk_mst_type = stk_mst_type;
	}
	public Long getNo_of_stockists() {
		return no_of_stockists;
	}
	public void setNo_of_stockists(Long no_of_stockists) {
		this.no_of_stockists = no_of_stockists;
	}
	public String getBlkstk_csv_name() {
		return blkstk_csv_name;
	}
	public void setBlkstk_csv_name(String blkstk_csv_name) {
		this.blkstk_csv_name = blkstk_csv_name;
	}
	public Date getBlkstk_csv_gen_date() {
		return blkstk_csv_gen_date;
	}
	public void setBlkstk_csv_gen_date(Date blkstk_csv_gen_date) {
		this.blkstk_csv_gen_date = blkstk_csv_gen_date;
	}
	public String getBlkstk_csvgen_usr_id() {
		return blkstk_csvgen_usr_id;
	}
	public void setBlkstk_csvgen_usr_id(String blkstk_csvgen_usr_id) {
		this.blkstk_csvgen_usr_id = blkstk_csvgen_usr_id;
	}
	public String getBlkstk_appr_ind() {
		return blkstk_appr_ind;
	}
	public void setBlkstk_appr_ind(String blkstk_appr_ind) {
		this.blkstk_appr_ind = blkstk_appr_ind;
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
	public String getBlkstk_confirm_ind() {
		return blkstk_confirm_ind;
	}
	public void setBlkstk_confirm_ind(String blkstk_confirm_ind) {
		this.blkstk_confirm_ind = blkstk_confirm_ind;
	}
	public String getBlkstk_ins_usr_id() {
		return blkstk_ins_usr_id;
	}
	public void setBlkstk_ins_usr_id(String blkstk_ins_usr_id) {
		this.blkstk_ins_usr_id = blkstk_ins_usr_id;
	}
	public String getBlkstk_mod_usr_id() {
		return blkstk_mod_usr_id;
	}
	public void setBlkstk_mod_usr_id(String blkstk_mod_usr_id) {
		this.blkstk_mod_usr_id = blkstk_mod_usr_id;
	}
	public Date getBlkstk_ins_dt() {
		return blkstk_ins_dt;
	}
	public void setBlkstk_ins_dt(Date blkstk_ins_dt) {
		this.blkstk_ins_dt = blkstk_ins_dt;
	}
	public Date getBlkstk_mod_dt() {
		return blkstk_mod_dt;
	}
	public void setBlkstk_mod_dt(Date blkstk_mod_dt) {
		this.blkstk_mod_dt = blkstk_mod_dt;
	}
	public String getBlkstk_ins_ip_add() {
		return blkstk_ins_ip_add;
	}
	public void setBlkstk_ins_ip_add(String blkstk_ins_ip_add) {
		this.blkstk_ins_ip_add = blkstk_ins_ip_add;
	}
	public String getBlkstk_mod_ip_add() {
		return blkstk_mod_ip_add;
	}
	public void setBlkstk_mod_ip_add(String blkstk_mod_ip_add) {
		this.blkstk_mod_ip_add = blkstk_mod_ip_add;
	}
	public String getBlkstk_status() {
		return blkstk_status;
	}
	public void setBlkstk_status(String blkstk_status) {
		this.blkstk_status = blkstk_status;
	}
	
	@Override
	public String toString() {
		return "BulkStkReqHeader [blk_stk_req_id=" + blk_stk_req_id + ", blk_stk_req_date=" + blk_stk_req_date
				+ ", blk_stk_req_no=" + blk_stk_req_no + ", fin_year_id=" + fin_year_id + ", company=" + company
				+ ", stk_mst_type=" + stk_mst_type + ", no_of_stockists=" + no_of_stockists + ", blkstk_csv_name="
				+ blkstk_csv_name + ", blkstk_csv_gen_date=" + blkstk_csv_gen_date + ", blkstk_csvgen_usr_id="
				+ blkstk_csvgen_usr_id + ", blkstk_appr_ind=" + blkstk_appr_ind + ", file_download=" + file_download
				+ ", file_upload=" + file_upload + ", blkstk_confirm_ind=" + blkstk_confirm_ind + ", blkstk_ins_usr_id="
				+ blkstk_ins_usr_id + ", blkstk_mod_usr_id=" + blkstk_mod_usr_id + ", blkstk_ins_dt=" + blkstk_ins_dt
				+ ", blkstk_mod_dt=" + blkstk_mod_dt + ", blkstk_ins_ip_add=" + blkstk_ins_ip_add
				+ ", blkstk_mod_ip_add=" + blkstk_mod_ip_add + ", blkstk_status=" + blkstk_status + "]";
	}
}
