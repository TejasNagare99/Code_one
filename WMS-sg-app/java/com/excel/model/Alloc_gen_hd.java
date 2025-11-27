package com.excel.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "Alloc_gen_hd")
public class Alloc_gen_hd {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALLOC_GEN_ID")
	private Long alloc_gen_id;

	@Column(name = "ALLOC_GEN_DATE")
	private Date alloc_gen_date;

	@Column(name = "FIN_YEAR")
	private String fin_year;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "DIV_ID")
	private Long div_id;

	@Column(name = "ALLOC_MONTH")
	private String alloc_month;

	@Column(name = "ALLOC_USE_MONTH")
	private String alloc_use_month;

	@Column(name = "EFFECTIVE_DATE")
	private Date Effective_date;

	@Column(name = "ALLOC_CYCLE")
	private Long alloc_cycle;

	@Column(name = "USER_ID")
	private String user_id;

	@Column(name = "UPD_DATE")
	private Date upd_date;

	@Column(name = "UPD_IP_ADD")
	private String upd_ip_add;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "ALLOC_TYPE")
	private String alloc_type;
	
	@Column(name = "ALLOC_MODE")
	private String alloc_mode;
	
	@Column(name = "FILE_UPLOAD")
	private String file_upload;
	
	@Column(name = "FILE_DOWNLOAD")
	private String file_download;
	
	@Column(name = "INPUT_TYPE")
	private String inputType;
	
	@Column(name = "ALLOC_DOC_NO")
	private String alloc_doc_no;
	
	@Column(name = "DISP_ADVICE")
	private String disp_advice;
	
	@Column(name = "MGR_ID")
	private String mgr_id;
	
	@Column(name = "ALLOC_APPR_STATUS")
	private String alloc_approval_status;
	
	@Column(name = "ASSISTANT_APPR_STATUS")
	private String assistant_appr_status;
	
	@Column(name = "ALLOC_CON_ID")
	private Long alloc_con_id;
	
	@Column(name = "ALLOC_CON_DTL_ID")
	private Long alloc_con_dtl_id;
	
	@Column(name = "alloc_con_docno")
	private String alloc_con_docno;
	
	@Column(name = "ALLOC_CON_DATE")
	private Date alloc_con_date;
	
	@Column(name = "TEAM_CODE")
	private String team_code;
	
	@Column(name = "TEAM_NAME")
	private String team_name;
	
	 @OneToMany(mappedBy = "alloc_gen_id")
	 @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE})
	 private Set<Alloc_gen_ent> alloc_gen_ets;
	 
	
	 @Column(name = "USER_ROLE")
	 private String user_role;
	 @Column(name="SRT_NUMBER")
		private String srt_number;
		
		@Column(name="SRT_DATE")
		private Date srt_date;
		
		@Column(name="SRT_REMARK")
		private String srt_remark;
		
		
	public String getSrt_remark() {
			return srt_remark;
		}

		public void setSrt_remark(String srt_remark) {
			this.srt_remark = srt_remark;
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

	public Long getAlloc_gen_id() {
		return alloc_gen_id;
	}

	public void setAlloc_gen_id(Long alloc_gen_id) {
		this.alloc_gen_id = alloc_gen_id;
	}

	public Date getAlloc_gen_date() {
		return alloc_gen_date;
	}

	public void setAlloc_gen_date(Date alloc_gen_date) {
		this.alloc_gen_date = alloc_gen_date;
	}

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getAlloc_month() {
		return alloc_month;
	}

	public void setAlloc_month(String alloc_month) {
		this.alloc_month = alloc_month;
	}

	public String getAlloc_use_month() {
		return alloc_use_month;
	}

	public void setAlloc_use_month(String alloc_use_month) {
		this.alloc_use_month = alloc_use_month;
	}

	public Long getAlloc_cycle() {
		return alloc_cycle;
	}

	public void setAlloc_cycle(Long alloc_cycle) {
		this.alloc_cycle = alloc_cycle;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Date upd_date) {
		this.upd_date = upd_date;
	}

	public String getUpd_ip_add() {
		return upd_ip_add;
	}

	public void setUpd_ip_add(String upd_ip_add) {
		this.upd_ip_add = upd_ip_add;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public String getFile_upload() {
		return file_upload;
	}

	public void setFile_upload(String file_upload) {
		this.file_upload = file_upload;
	}

	public Date getEffective_date() {
		return Effective_date;
	}

	public void setEffective_date(Date effective_date) {
		Effective_date = effective_date;
	}


	public Set<Alloc_gen_ent> getAlloc_gen_ets() {
		return alloc_gen_ets;
	}

	public void setAlloc_gen_ets(Set<Alloc_gen_ent> alloc_gen_ets) {
		this.alloc_gen_ets = alloc_gen_ets;
	}

	public String getFile_download() {
		return file_download;
	}

	public void setFile_download(String file_download) {
		this.file_download = file_download;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getAlloc_doc_no() {
		return alloc_doc_no;
	}

	public void setAlloc_doc_no(String alloc_doc_no) {
		this.alloc_doc_no = alloc_doc_no;
	}

	public String getDisp_advice() {
		return disp_advice;
	}

	public void setDisp_advice(String disp_advice) {
		this.disp_advice = disp_advice;
	}

	public String getMgr_id() {
		return mgr_id;
	}

	public void setMgr_id(String mgr_id) {
		this.mgr_id = mgr_id;
	}

	public String getAlloc_mode() {
		return alloc_mode;
	}

	public void setAlloc_mode(String alloc_mode) {
		this.alloc_mode = alloc_mode;
	}

	public Alloc_gen_hd() {
		
	}
	
	public Alloc_gen_hd(String allocMode,Long alloc_gen_id,String alloc_doc_no,String alloc_type) {
		this.alloc_mode=allocMode;
		this.alloc_gen_id=alloc_gen_id;
		this.alloc_doc_no=alloc_doc_no;
		this.alloc_type=alloc_type;
	}
	
	public Alloc_gen_hd(Long alloc_gen_id,String  alloc_doc_no,Long alloc_cycle,Long div_id) {
		this.alloc_gen_id=alloc_gen_id;
		this.alloc_doc_no=alloc_doc_no;
		this.alloc_cycle=alloc_cycle;
		this.div_id=div_id;
	}


	public String getAlloc_approval_status() {
		return alloc_approval_status;
	}

	public void setAlloc_approval_status(String alloc_approval_status) {
		this.alloc_approval_status = alloc_approval_status;
	}

	public String getAssistant_appr_status() {
		return assistant_appr_status;
	}

	public void setAssistant_appr_status(String assistant_appr_status) {
		this.assistant_appr_status = assistant_appr_status;
	}

	public Long getAlloc_con_id() {
		return alloc_con_id;
	}

	public void setAlloc_con_id(Long alloc_con_id) {
		this.alloc_con_id = alloc_con_id;
	}

	public Long getAlloc_con_dtl_id() {
		return alloc_con_dtl_id;
	}

	public void setAlloc_con_dtl_id(Long alloc_con_dtl_id) {
		this.alloc_con_dtl_id = alloc_con_dtl_id;
	}

	public String getAlloc_con_docno() {
		return alloc_con_docno;
	}

	public void setAlloc_con_docno(String alloc_con_docno) {
		this.alloc_con_docno = alloc_con_docno;
	}

	public Date getAlloc_con_date() {
		return alloc_con_date;
	}

	public void setAlloc_con_date(Date alloc_con_date) {
		this.alloc_con_date = alloc_con_date;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
}
