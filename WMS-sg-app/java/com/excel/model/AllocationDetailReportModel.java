package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name="AllocationDetailReportModel")
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "EmptyCsvMktgView", 
			//procedureName = "EMPTY_CSV_MKTG",
			procedureName = "EMPTY_CSV_MKTG_WITH_TEAM",
			parameters = {
					@StoredProcedureParameter(name = "PER_CD", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name = "FINYR", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name = "DIVID" , mode = ParameterMode.IN, type = String.class),

			}, resultClasses = AllocationDetailReportModel.class),
	@NamedStoredProcedureQuery(name = "EmptyCsvMktgViewWithTeam", 
	//procedureName = "EMPTY_CSV_MKTG",
	procedureName = "EMPTY_CSV_MKTG_WITH_TEAM18dec23div",
	parameters = {
			@StoredProcedureParameter(name = "PER_CD", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "FINYR", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "DIVID" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "USER_ID" , mode = ParameterMode.IN, type = String.class),

	}, resultClasses = AllocationDetailReportModel.class)
})

public class AllocationDetailReportModel {

	private static final long serialVersionUID = 4249203119726683312L;

	@Id
	@Column(name = "ROW")
	private String row;
	
	@Column(name = "RM_NAME")
	private String RM_NAME;

	@Column(name = "AM_NAME")
	private String AM_NAME;

	@Column(name = "FSTAFF_EMPLOYEE_NO")
	private String FSTAFF_EMPLOYEE_NO;

	@Column(name = "Territory")
	private String Territory;

	@Column(name = "YEAR")
	private String YEAR;

	@Column(name = "PER_CODE")
	private String PER_CODE;

	@Column(name = "DIVISION")
	private String DIVISION;

	@Column(name = "staff_code")
	private String staff_code;

	@Column(name = "staff_name")
	private String staff_name;

	@Column(name="level_code")
	private String level_code;
	
	@Column(name="PER_CD")
	private String PER_CD;
	
	@Column(name="SUBCOMP_NM")
	private String SUBCOMP_NM;
	
	@Column(name="LOC_NAME")
	private String LOC_NAME;
	
	@Column(name="FSTAFF_ID")
	private Long FSTAFF_ID;
	
	@Column(name="LEVEL_BRIEF_NAME")
	private String LEVEL_BRIEF_NAME;
	
	//(25May2020)
		@Column(name="FSTAFF_ADDR1")
		private String fstaff_addr1;
		
		@Column(name="FSTAFF_ADDR2")
		private String fstaff_addr2;
		
		@Column(name="FSTAFF_ADDR3")
		private String fstaff_addr3;
		
		@Column(name="FSTAFF_ADDR4")
		private String fstaff_addr4;
		
		@Column(name="DESTINATION")
		private String destination;
		
		@Column(name="FSTAFF_PINCODE")
		private String fstaff_pincode;
		
		@Column(name="STATE_NAME")
		private String state_name;
		
		@Column(name="FSTAFF_EMAIL")
		private String fstaff_email;
		
		@Column(name="FSTAFF_MOBILE")
		private String fstaff_mobile;
		
		@Column(name="FSTAFF_MOBILE2")
		private String fstaff_mobile2; //added
		
		@Column(name="CFA_PFIZER")
		private String cfa_pfizer;
		
		@Column(name="CFA_PIPL")
		private String cfa_pipl;
		
		
		@Column(name="REMARK")
		private String remark;
		
		
		@Column(name="TEAM_CODE")
		private String team_code;
		
		
		

	public String getTeam_code() {
			return team_code;
		}

		public void setTeam_code(String team_code) {
			this.team_code = team_code;
		}


	public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

	public String getFstaff_mobile2() {
			return fstaff_mobile2;
		}

		public void setFstaff_mobile2(String fstaff_mobile2) {
			this.fstaff_mobile2 = fstaff_mobile2;
		}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getRM_NAME() {
		return RM_NAME;
	}

	public void setRM_NAME(String rM_NAME) {
		RM_NAME = rM_NAME;
	}

	public String getAM_NAME() {
		return AM_NAME;
	}

	public void setAM_NAME(String aM_NAME) {
		AM_NAME = aM_NAME;
	}

	public String getFSTAFF_EMPLOYEE_NO() {
		return FSTAFF_EMPLOYEE_NO;
	}

	public void setFSTAFF_EMPLOYEE_NO(String fSTAFF_EMPLOYEE_NO) {
		FSTAFF_EMPLOYEE_NO = fSTAFF_EMPLOYEE_NO;
	}

	public String getTerritory() {
		return Territory;
	}

	public void setTerritory(String territory) {
		Territory = territory;
	}

	public String getYEAR() {
		return YEAR;
	}

	public void setYEAR(String yEAR) {
		YEAR = yEAR;
	}

	public String getPER_CODE() {
		return PER_CODE;
	}

	public void setPER_CODE(String pER_CODE) {
		PER_CODE = pER_CODE;
	}

	public String getDIVISION() {
		return DIVISION;
	}

	public void setDIVISION(String dIVISION) {
		DIVISION = dIVISION;
	}

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getLevel_code() {
		return level_code;
	}

	public void setLevel_code(String level_code) {
		this.level_code = level_code;
	}

	public String getPER_CD() {
		return PER_CD;
	}

	public void setPER_CD(String pER_CD) {
		PER_CD = pER_CD;
	}

	public String getSUBCOMP_NM() {
		return SUBCOMP_NM;
	}

	public void setSUBCOMP_NM(String sUBCOMP_NM) {
		SUBCOMP_NM = sUBCOMP_NM;
	}

	public String getLOC_NAME() {
		return LOC_NAME;
	}

	public void setLOC_NAME(String lOC_NAME) {
		LOC_NAME = lOC_NAME;
	}

	public Long getFSTAFF_ID() {
		return FSTAFF_ID;
	}

	public void setFSTAFF_ID(Long fSTAFF_ID) {
		FSTAFF_ID = fSTAFF_ID;
	}

	public String getLEVEL_BRIEF_NAME() {
		return LEVEL_BRIEF_NAME;
	}

	public void setLEVEL_BRIEF_NAME(String lEVEL_BRIEF_NAME) {
		LEVEL_BRIEF_NAME = lEVEL_BRIEF_NAME;
	}
	
	//25MAY2020

		public String getFstaff_addr1() {
			return fstaff_addr1;
		}

		public void setFstaff_addr1(String fstaff_addr1) {
			this.fstaff_addr1 = fstaff_addr1;
		}

		public String getFstaff_addr2() {
			return fstaff_addr2;
		}

		public void setFstaff_addr2(String fstaff_addr2) {
			this.fstaff_addr2 = fstaff_addr2;
		}

		public String getFstaff_addr3() {
			return fstaff_addr3;
		}

		public void setFstaff_addr3(String fstaff_addr3) {
			this.fstaff_addr3 = fstaff_addr3;
		}

		public String getFstaff_addr4() {
			return fstaff_addr4;
		}

		public void setFstaff_addr4(String fstaff_addr4) {
			this.fstaff_addr4 = fstaff_addr4;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getFstaff_pincode() {
			return fstaff_pincode;
		}

		public void setFstaff_pincode(String fstaff_pincode) {
			this.fstaff_pincode = fstaff_pincode;
		}

		public String getState_name() {
			return state_name;
		}

		public void setState_name(String state_name) {
			this.state_name = state_name;
		}

		public String getFstaff_email() {
			return fstaff_email;
		}

		public void setFstaff_email(String fstaff_email) {
			this.fstaff_email = fstaff_email;
		}

		public String getFstaff_mobile() {
			return fstaff_mobile;
		}

		public void setFstaff_mobile(String fstaff_mobile) {
			this.fstaff_mobile = fstaff_mobile;
		}

		public String getCfa_pfizer() {
			return cfa_pfizer;
		}

		public void setCfa_pfizer(String cfa_pfizer) {
			this.cfa_pfizer = cfa_pfizer;
		}

		public String getCfa_pipl() {
			return cfa_pipl;
		}

		public void setCfa_pipl(String cfa_pipl) {
			this.cfa_pipl = cfa_pipl;
		}

}
