package com.excel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="BLK_HCP_REQ_DOCTORS")
@DynamicUpdate(value=true)
public class Blk_hcp_req_doctors {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BLK_HCP_DTL_ID")
	private Long blk_hcp_dtl_id;
	
	
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

	@Column(name="HCP_UNIQUE_ID")
	private String hcp_unique_id;

	@Column(name="HCP_NAME")
	private String hcp_name;

	@Column(name="SRT_NUMBER")
	private String srt_number;

	@Column(name="SRT_DATE")
	private Date srt_date;

	@Column(name="SRT_REMARKS")
	private String srt_remarks;

	@Column(name="SERV_REQ_NO")
	private String serv_req_no;

	@Column(name="REQUESTOR_ID")
	private Long requestor_id;

	@Column(name="DOC_INS_USR_ID")
	private String doc_ins_usr_id;

	@Column(name="DOC_MOD_USR_ID")
	private String doc_mod_usr_id;

	@Column(name="DOC_INS_DT")
	private Date doc_ins_dt;

	@Column(name="DOC_MOD_DT")
	private Date doc_mod_dt;

	@Column(name="DOC_INS_IP_ADD")
	private String doc_ins_ip_add;

	@Column(name="DOC_MOD_IP_ADD")
	private String doc_mod_ip_add;

	@Column(name="DOC_STATUS")
	private String doc_status;
	
	@Column(name="MCL_NO")
	private String mcl_no;

	@Column(name="BLK_REQ_EMPLOYEE_NO")
	private String blk_req_employee_no;
	
	@Column(name="BLK_MDM_ADDR_CHG")
	private String blk_mdm_addr_chg;

	@Transient private String fsname;
	
	
	
	public String getBlk_mdm_addr_chg() {
		return blk_mdm_addr_chg;
	}

	public void setBlk_mdm_addr_chg(String blk_mdm_addr_chg) {
		this.blk_mdm_addr_chg = blk_mdm_addr_chg;
	}

	public String getBlk_req_employee_no() {
		return blk_req_employee_no;
	}

	public void setBlk_req_employee_no(String blk_req_employee_no) {
		this.blk_req_employee_no = blk_req_employee_no;
	}
	
	
	
	public String getMcl_no() {
		return mcl_no;
	}

	public void setMcl_no(String mcl_no) {
		this.mcl_no = mcl_no;
	}

	public String getFsname() {
		return fsname;
	}

	public void setFsname(String fsname) {
		this.fsname = fsname;
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

	public Long getBlk_hcp_dtl_id() {
		return blk_hcp_dtl_id;
	}

	public void setBlk_hcp_dtl_id(Long blk_hcp_dtl_id) {
		this.blk_hcp_dtl_id = blk_hcp_dtl_id;
	}

	public String getHcp_unique_id() {
		return hcp_unique_id;
	}

	public void setHcp_unique_id(String hcp_unique_id) {
		this.hcp_unique_id = hcp_unique_id;
	}

	public String getHcp_name() {
		return hcp_name;
	}

	public void setHcp_name(String hcp_name) {
		this.hcp_name = hcp_name;
	}

	public String getSrt_number() {
		return srt_number;
	}

	public void setSrt_number(String srt_number) {
		this.srt_number = srt_number;
	}

	public Date getSrt_date() {
		return srt_date;
	}

	public void setSrt_date(Date srt_date) {
		this.srt_date = srt_date;
	}

	public String getSrt_remarks() {
		return srt_remarks;
	}

	public void setSrt_remarks(String srt_remarks) {
		this.srt_remarks = srt_remarks;
	}

	public String getServ_req_no() {
		return serv_req_no;
	}

	public void setServ_req_no(String serv_req_no) {
		this.serv_req_no = serv_req_no;
	}

	public Long getRequestor_id() {
		return requestor_id;
	}

	public void setRequestor_id(Long requestor_id) {
		this.requestor_id = requestor_id;
	}

	public String getDoc_ins_usr_id() {
		return doc_ins_usr_id;
	}

	public void setDoc_ins_usr_id(String doc_ins_usr_id) {
		this.doc_ins_usr_id = doc_ins_usr_id;
	}

	public String getDoc_mod_usr_id() {
		return doc_mod_usr_id;
	}

	public void setDoc_mod_usr_id(String doc_mod_usr_id) {
		this.doc_mod_usr_id = doc_mod_usr_id;
	}

	public Date getDoc_ins_dt() {
		return doc_ins_dt;
	}

	public void setDoc_ins_dt(Date doc_ins_dt) {
		this.doc_ins_dt = doc_ins_dt;
	}

	public Date getDoc_mod_dt() {
		return doc_mod_dt;
	}

	public void setDoc_mod_dt(Date doc_mod_dt) {
		this.doc_mod_dt = doc_mod_dt;
	}

	public String getDoc_ins_ip_add() {
		return doc_ins_ip_add;
	}

	public void setDoc_ins_ip_add(String doc_ins_ip_add) {
		this.doc_ins_ip_add = doc_ins_ip_add;
	}

	public String getDoc_mod_ip_add() {
		return doc_mod_ip_add;
	}

	public void setDoc_mod_ip_add(String doc_mod_ip_add) {
		this.doc_mod_ip_add = doc_mod_ip_add;
	}

	public String getDoc_status() {
		return doc_status;
	}

	public void setDoc_status(String doc_status) {
		this.doc_status = doc_status;
	}
	
	
}
