package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="ACC_TRAN_TYPE")
public class Acc_Tran_Type {
	
	@Id
	@Column(name = "TRAN_TYPE")
	private String tran_type;
	@Column(name = "TRAN_TYPE_DESCRIPTION")
	private String tran_type_description;
	@Column(name = "OPEN_TRAN_TYPE")
	private String open_tran_type;
	@Column(name = "COMPANY_CD")
	private String company_cd;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "FULL_TRAN_NAME")
	private TranNameEnum full_tran_name;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	@Column(name = "TRAN_TYPE_DISPLAY_NAME")
	private String tran_type_display_name;
	
	public static enum TranNameEnum {
		GRN,
		DSP,
		IAA,
		STW,
		ASR//article scheme request
	}

	public String getTran_type() {
		return tran_type;
	}
	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}
	public String getTran_type_description() {
		return tran_type_description;
	}
	public void setTran_type_description(String tran_type_description) {
		this.tran_type_description = tran_type_description;
	}
	public String getOpen_tran_type() {
		return open_tran_type;
	}
	public void setOpen_tran_type(String open_tran_type) {
		this.open_tran_type = open_tran_type;
	}
	public String getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}
	public TranNameEnum getFull_tran_name() {
		return full_tran_name;
	}
	public void setFull_tran_name(TranNameEnum full_tran_name) {
		this.full_tran_name = full_tran_name;
	}
	public Long getLoc_id() {
		return loc_id;
	}
	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}
	public String getTran_type_display_name() {
		return tran_type_display_name;
	}
	public void setTran_type_display_name(String tran_type_display_name) {
		this.tran_type_display_name = tran_type_display_name;
	}

	
}
