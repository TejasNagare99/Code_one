package com.excel.model;

	import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.Id;
	import javax.persistence.NamedNativeQueries;
	import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;


	@Entity
	@Table(name = "DispatchIntimationEmailReport")
	@NamedStoredProcedureQuery(name = "callDISPATCH_INTIMATION_EMAIL_REPORT", 
		procedureName = "DISPATCH_INTIMATION_EMAIL_REPORT",
		parameters = {
				@StoredProcedureParameter(name = "dspmod_from_dt" , mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name = "dspmod_to_dt" , mode = ParameterMode.IN, type = String.class),
		}, resultClasses = DispatchIntimationEmail.class)
	public class DispatchIntimationEmail {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Id
		@Column(name="ROW")
		private Long row;
		@Column(name="DSP_ID")
		private String dsp_id;
		@Column(name="DSP_CHALLAN_NO")
		private String dsp_challan_no;//
		@Column(name="DSP_DATE")
		private String dsp_date;//
		@Column(name="DSP_FSTAFF_ID")
		private String dsp_fstaff_id;
		@Column(name="DSPFSTAFF_NAME")
		private String dspfstaff_name;//
		@Column(name="DIVISION")
		private String division; 
		@Column(name="HQ_NAME")
		private String hq_name;
		@Column(name="ITEM_NAME")
		private String item_name;
		@Column(name="ITEM_TYPE")
		private String item_type;
		@Column(name="COURIER_NAME")
		private String courier_name;
		@Column(name="DOCKET_NO")
		private String docket_no;
		@Column(name="DOCKET_DATE")
		private String docket_date;
		@Column(name="QTY")
		private String qty;
		@Column(name="FSTAFF_EMAIL")
		private String fstaff_email;
		
		@Column(name="DSPFSTAFF_ADDR1")
		private String dspfstaff_addr1;
		@Column(name="DSPFSTAFF_ADDR2")
		private String dspfstaff_addr2;
		@Column(name="DSPFSTAFF_ADDR3")
		private String dspfstaff_addr3;
		@Column(name="DSPFSTAFF_ADDR4")
		private String dspfstaff_addr4;
		@Column(name="DSPFSTAFF_MOBILE")
		private String dspfstaff_mobile;
		@Column(name="DSPFSTAFF_DESTINATION")
		private String dspfstaff_destination;
		
		@Column(name="BATCH_NO")
		private String batch_no;
		@Column(name="DSPFSTAFF_EMPLOYEENO")
		private String dspfstaff_employeeno;
		@Column(name="TERR_NAME")
		private String terr_name;
		@Column(name="DSP_CASES")
		private String dsp_cases;
		
		@Column(name = "TEAM_NAME")
		private String team_name;
		
		@Column(name = "REQ_EMAIL")
		private String req_email;
		
		@Column(name = "SRT_NUMBER")
		private String srt_number;
		
		@Column(name = "SRT_REMARK")
		private String srt_remark;
		
		@Column(name = "SRT_DATE")
		private String srt_date;
		
		@Column(name = "ALLOC_REQ_ID")
		private Long alloc_req_id;
		
		@Column(name = "ALLOC_ID")
		private Long alloc_id;
		
		@Column(name = "MONTH_OF_USE")
		private String month_of_use;
		
		@Column(name = "DSP_MOD_DT")
		private Date dsp_mod_dt;
		
		
		public Date getDsp_mod_dt() {
			return dsp_mod_dt;
		}
		public void setDsp_mod_dt(Date dsp_mod_dt) {
			this.dsp_mod_dt = dsp_mod_dt;
		}
		public String getMonth_of_use() {
			return month_of_use;
		}
		public void setMonth_of_use(String month_of_use) {
			this.month_of_use = month_of_use;
		}
		public String getSrt_number() {
			return srt_number;
		}
		public void setSrt_number(String srt_number) {
			this.srt_number = srt_number;
		}
		public String getSrt_remark() {
			return srt_remark;
		}
		public void setSrt_remark(String srt_remark) {
			this.srt_remark = srt_remark;
		}
		public String getSrt_date() {
			return srt_date;
		}
		public void setSrt_date(String srt_date) {
			this.srt_date = srt_date;
		}
		public Long getAlloc_req_id() {
			return alloc_req_id;
		}
		public void setAlloc_req_id(Long alloc_req_id) {
			this.alloc_req_id = alloc_req_id;
		}
		public Long getAlloc_id() {
			return alloc_id;
		}
		public void setAlloc_id(Long alloc_id) {
			this.alloc_id = alloc_id;
		}
		public String getTeam_name() {
			return team_name;
		}
		public void setTeam_name(String team_name) {
			this.team_name = team_name;
		}
		public String getDsp_cases() {
			return dsp_cases;
		}
		public void setDsp_cases(String dsp_cases) {
			this.dsp_cases = dsp_cases;
		}
		public String getTerr_name() {
			return terr_name;
		}
		public void setTerr_name(String terr_name) {
			this.terr_name = terr_name;
		}
		public String getDspfstaff_employeeno() {
			return dspfstaff_employeeno;
		}
		public void setDspfstaff_employeeno(String dspfstaff_employeeno) {
			this.dspfstaff_employeeno = dspfstaff_employeeno;
		}
		public String getDspfstaff_addr1() {
			return dspfstaff_addr1;
		}
		public void setDspfstaff_addr1(String dspfstaff_addr1) {
			this.dspfstaff_addr1 = dspfstaff_addr1;
		}
		public String getDspfstaff_addr2() {
			return dspfstaff_addr2;
		}
		public void setDspfstaff_addr2(String dspfstaff_addr2) {
			this.dspfstaff_addr2 = dspfstaff_addr2;
		}
		public String getDspfstaff_addr3() {
			return dspfstaff_addr3;
		}
		public void setDspfstaff_addr3(String dspfstaff_addr3) {
			this.dspfstaff_addr3 = dspfstaff_addr3;
		}
		public String getDspfstaff_addr4() {
			return dspfstaff_addr4;
		}
		public void setDspfstaff_addr4(String dspfstaff_addr4) {
			this.dspfstaff_addr4 = dspfstaff_addr4;
		}
		public String getDspfstaff_mobile() {
			return dspfstaff_mobile;
		}
		public void setDspfstaff_mobile(String dspfstaff_mobile) {
			this.dspfstaff_mobile = dspfstaff_mobile;
		}
		public String getBatch_no() {
			return batch_no;
		}
		public void setBatch_no(String batch_no) {
			this.batch_no = batch_no;
		}
		public Long getRow() {
			return row;
		}
		public void setRow(Long row) {
			this.row = row;
		}
		public String getDsp_id() {
			return dsp_id;
		}
		public void setDsp_id(String dsp_id) {
			this.dsp_id = dsp_id;
		}
		public String getDsp_challan_no() {
			return dsp_challan_no;
		}
		public void setDsp_challan_no(String dsp_challan_no) {
			this.dsp_challan_no = dsp_challan_no;
		}
		public String getDsp_date() {
			return dsp_date;
		}
		public void setDsp_date(String dsp_date) {
			this.dsp_date = dsp_date;
		}
		public String getDsp_fstaff_id() {
			return dsp_fstaff_id;
		}
		public void setDsp_fstaff_id(String dsp_fstaff_id) {
			this.dsp_fstaff_id = dsp_fstaff_id;
		}
		public String getDspfstaff_name() {
			return dspfstaff_name;
		}
		public void setDspfstaff_name(String dspfstaff_name) {
			this.dspfstaff_name = dspfstaff_name;
		}
		public String getDivision() {
			return division;
		}
		public void setDivision(String division) {
			this.division = division;
		}
		public String getHq_name() {
			return hq_name;
		}
		public void setHq_name(String hq_name) {
			this.hq_name = hq_name;
		}
		public String getItem_name() {
			return item_name;
		}
		public void setItem_name(String item_name) {
			this.item_name = item_name;
		}
		public String getItem_type() {
			return item_type;
		}
		public void setItem_type(String item_type) {
			this.item_type = item_type;
		}
		public String getCourier_name() {
			return courier_name;
		}
		public void setCourier_name(String courier_name) {
			this.courier_name = courier_name;
		}
		public String getDocket_no() {
			return docket_no;
		}
		public void setDocket_no(String docket_no) {
			this.docket_no = docket_no;
		}
		public String getDocket_date() {
			return docket_date;
		}
		public void setDocket_date(String docket_date) {
			this.docket_date = docket_date;
		}
		public String getQty() {
			return qty;
		}
		public void setQty(String qty) {
			this.qty = qty;
		}
		public String getFstaff_email() {
			return fstaff_email;
		}
		public void setFstaff_email(String fstaff_email) {
			this.fstaff_email = fstaff_email;
		}
		public String getReq_email() {
			return req_email;
		}
		public void setReq_email(String req_email) {
			this.req_email = req_email;
		}
		public String getDspfstaff_destination() {
			return dspfstaff_destination;
		}
		public void setDspfstaff_destination(String dspfstaff_destination) {
			this.dspfstaff_destination = dspfstaff_destination;
		}
		
		
		

	}


