package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "FieldMasterDownload")

public class FieldMasterDownload {

	@Id
	@Column(name="FSTAFF_ID")
	private String FSTAFF_ID;
	
	@Column(name="FSTAFF_DIV_ID")
	private String FSTAFF_DIV_ID;
	
	@Column(name="DIV_DISP_NM")
	private String DIV_DISP_NM;
	
	@Column(name="FSTAFF_LEVEL_CODE")
	private String FSTAFF_LEVEL_CODE;
	
	@Column(name="FSTAFF_NAME")
	private String FSTAFF_NAME;
	
	@Column(name="FSTAFF_MAP_CODE1")
	private String FSTAFF_MAP_CODE1;
	
	@Column(name="HQ_NAME")
	private String HQ_NAME;
	
	@Column(name="RM")
	private String RM;
	
	@Column(name="AM")
	private String AM;
	
	@Column(name="FSTAFF_ADDR1")
	private String FSTAFF_ADDR1;
	
	@Column(name="FSTAFF_ADDR2")
	private String FSTAFF_ADDR2;
	
	@Column(name="FSTAFF_ADDR3")
	private String FSTAFF_ADDR3;
	
	@Column(name="FSTAFF_ADDR4")
	private String FSTAFF_ADDR4;
	
	@Column(name="FSTAFF_MOBILE")
	private String FSTAFF_MOBILE;
	
	@Column(name="FSTAFF_DESTINATION")
	private String FSTAFF_DESTINATION;
	
	@Column(name="FSTAFF_MAP_CODE2")
	private String FSTAFF_MAP_CODE2;
	
	@Column(name="FSTAFF_DISPLAY_NAME")
	private String FSTAFF_DISPLAY_NAME;
	
	@Column(name="FSTAFF_DESIG")
	private String FSTAFF_DESIG;
	
	@Column(name="FSTAFF_ZONENAME")
	private String FSTAFF_ZONENAME;
	
	@Column(name="FSTAFF_EMPLOYEE_NO")
	private String FSTAFF_EMPLOYEE_NO;
	
	@Column(name="FSTAFF_EMAIL")
	private String FSTAFF_EMAIL;

	public String getFSTAFF_ID() {
		return FSTAFF_ID;
	}

	public void setFSTAFF_ID(String fSTAFF_ID) {
		FSTAFF_ID = fSTAFF_ID;
	}

	public String getFSTAFF_DIV_ID() {
		return FSTAFF_DIV_ID;
	}

	public void setFSTAFF_DIV_ID(String fSTAFF_DIV_ID) {
		FSTAFF_DIV_ID = fSTAFF_DIV_ID;
	}

	public String getDIV_DISP_NM() {
		return DIV_DISP_NM;
	}

	public void setDIV_DISP_NM(String dIV_DISP_NM) {
		DIV_DISP_NM = dIV_DISP_NM;
	}

	public String getFSTAFF_LEVEL_CODE() {
		return FSTAFF_LEVEL_CODE;
	}

	public void setFSTAFF_LEVEL_CODE(String fSTAFF_LEVEL_CODE) {
		FSTAFF_LEVEL_CODE = fSTAFF_LEVEL_CODE;
	}

	public String getFSTAFF_NAME() {
		return FSTAFF_NAME;
	}

	public void setFSTAFF_NAME(String fSTAFF_NAME) {
		FSTAFF_NAME = fSTAFF_NAME;
	}

	public String getFSTAFF_MAP_CODE1() {
		return FSTAFF_MAP_CODE1;
	}

	public void setFSTAFF_MAP_CODE1(String fSTAFF_MAP_CODE1) {
		FSTAFF_MAP_CODE1 = fSTAFF_MAP_CODE1;
	}

	public String getHQ_NAME() {
		return HQ_NAME;
	}

	public void setHQ_NAME(String hQ_NAME) {
		HQ_NAME = hQ_NAME;
	}

	public String getRM() {
		return RM;
	}

	public void setRM(String rM) {
		RM = rM;
	}

	public String getAM() {
		return AM;
	}

	public void setAM(String aM) {
		AM = aM;
	}

	public String getFSTAFF_ADDR1() {
		return FSTAFF_ADDR1;
	}

	public void setFSTAFF_ADDR1(String fSTAFF_ADDR1) {
		FSTAFF_ADDR1 = fSTAFF_ADDR1;
	}

	public String getFSTAFF_ADDR2() {
		return FSTAFF_ADDR2;
	}

	public void setFSTAFF_ADDR2(String fSTAFF_ADDR2) {
		FSTAFF_ADDR2 = fSTAFF_ADDR2;
	}

	public String getFSTAFF_ADDR3() {
		return FSTAFF_ADDR3;
	}

	public void setFSTAFF_ADDR3(String fSTAFF_ADDR3) {
		FSTAFF_ADDR3 = fSTAFF_ADDR3;
	}

	public String getFSTAFF_ADDR4() {
		return FSTAFF_ADDR4;
	}

	public void setFSTAFF_ADDR4(String fSTAFF_ADDR4) {
		FSTAFF_ADDR4 = fSTAFF_ADDR4;
	}

	public String getFSTAFF_MOBILE() {
		return FSTAFF_MOBILE;
	}

	public void setFSTAFF_MOBILE(String fSTAFF_MOBILE) {
		FSTAFF_MOBILE = fSTAFF_MOBILE;
	}

	public String getFSTAFF_DESTINATION() {
		return FSTAFF_DESTINATION;
	}

	public void setFSTAFF_DESTINATION(String fSTAFF_DESTINATION) {
		FSTAFF_DESTINATION = fSTAFF_DESTINATION;
	}

	public String getFSTAFF_MAP_CODE2() {
		return FSTAFF_MAP_CODE2;
	}

	public void setFSTAFF_MAP_CODE2(String fSTAFF_MAP_CODE2) {
		FSTAFF_MAP_CODE2 = fSTAFF_MAP_CODE2;
	}

	public String getFSTAFF_DISPLAY_NAME() {
		return FSTAFF_DISPLAY_NAME;
	}

	public void setFSTAFF_DISPLAY_NAME(String fSTAFF_DISPLAY_NAME) {
		FSTAFF_DISPLAY_NAME = fSTAFF_DISPLAY_NAME;
	}

	public String getFSTAFF_DESIG() {
		return FSTAFF_DESIG;
	}

	public void setFSTAFF_DESIG(String fSTAFF_DESIG) {
		FSTAFF_DESIG = fSTAFF_DESIG;
	}

	public String getFSTAFF_ZONENAME() {
		return FSTAFF_ZONENAME;
	}

	public void setFSTAFF_ZONENAME(String fSTAFF_ZONENAME) {
		FSTAFF_ZONENAME = fSTAFF_ZONENAME;
	}

	public String getFSTAFF_EMPLOYEE_NO() {
		return FSTAFF_EMPLOYEE_NO;
	}

	public void setFSTAFF_EMPLOYEE_NO(String fSTAFF_EMPLOYEE_NO) {
		FSTAFF_EMPLOYEE_NO = fSTAFF_EMPLOYEE_NO;
	}

	public String getFSTAFF_EMAIL() {
		return FSTAFF_EMAIL;
	}

	public void setFSTAFF_EMAIL(String fSTAFF_EMAIL) {
		FSTAFF_EMAIL = fSTAFF_EMAIL;
	}
	
	
	
}