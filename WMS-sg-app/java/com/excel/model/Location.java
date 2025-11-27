package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="location")
public class Location implements Serializable{

	private static final long serialVersionUID = 8290962260356320220L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loc_id")
	private Long loc_id;
	
	@Column(name = "loc_sup_id")
	private Long loc_sup_id;

	@Column(name="loc_prefix")
	private String loc_prefix;

	@Column(name = "loc_nm")
	private String loc_nm;
	
	@Column(name = "loc_status")
	private char loc_status;
	
	@Column(name="LOC_SUBCOMP_ID")
	private Long loc_subcomp_id;
	
	@Column(name="loc_narration")
	private String loc_narration;
	
	@Column(name="loc_narrationfooter")
	private String loc_narrationfooter;
	
	@Column(name="loc_state_id")
	private Long loc_state_id;
	
	@Column(name="GST_REG_NO")
	private String gst_reg_no;
	
	@Column(name="GST_REQ_IND")
	private String gst_req_ind;
	
	@Column(name="GST_ON_INDV_CHLN")
	private String gst_on_indv_chln;
	
	@Column(name="LOC_LINK_DPTLOC_ID")
	private Long loc_link_dptloc_id;
	
	@Column(name="AUTODESP_INPROCESS")
	private String autodesp_inprocess;
	
	@Column(name = "AUTH_TOKEN")
	private String auth_token;
	
	@Column(name = "OWNER_ID")
	private String owner_id;
	
	@Column(name = "LOC_MAP_CD")
	private String loc_map_cd;
	
	
	
	@Column(name = "EINV_UNAME")
	private String einv_uname;
	
	@Column(name = "EINV_PASSWORD")
	private String einv_password;
	
	
	
	@Column(name = "requestor_fstaff_id")
	private Long requestor_fstaff_id;
	
	

	public String getLoc_prefix() {
		return loc_prefix;
	}

	public void setLoc_prefix(String loc_prefix) {
		this.loc_prefix = loc_prefix;
	}

	public Long getRequestor_fstaff_id() {
		return requestor_fstaff_id;
	}

	public void setRequestor_fstaff_id(Long requestor_fstaff_id) {
		this.requestor_fstaff_id = requestor_fstaff_id;
	}

	public String getEinv_uname() {
		return einv_uname;
	}

	public void setEinv_uname(String einv_uname) {
		this.einv_uname = einv_uname;
	}

	public String getEinv_password() {
		return einv_password;
	}

	public void setEinv_password(String einv_password) {
		this.einv_password = einv_password;
	}

	public String getLoc_map_cd() {
		return loc_map_cd;
	}

	public void setLoc_map_cd(String loc_map_cd) {
		this.loc_map_cd = loc_map_cd;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public Long getLoc_state_id() {
		return loc_state_id;
	}

	public void setLoc_state_id(Long loc_state_id) {
		this.loc_state_id = loc_state_id;
	}
	

	public String getGst_reg_no() {
		return gst_reg_no;
	}

	public void setGst_reg_no(String gst_reg_no) {
		this.gst_reg_no = gst_reg_no;
	}

	public String getLoc_narrationfooter() {
		return loc_narrationfooter;
	}

	public void setLoc_narrationfooter(String loc_narrationfooter) {
		this.loc_narrationfooter = loc_narrationfooter;
	}

	public String getLoc_narration() {
		return loc_narration;
	}

	public void setLoc_narration(String loc_narration) {
		this.loc_narration = loc_narration;
	}

	public Long getLoc_subcomp_id() {
		return loc_subcomp_id;
	}

	public void setLoc_subcomp_id(Long loc_subcomp_id) {
		this.loc_subcomp_id = loc_subcomp_id;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public char getLoc_status() {
		return loc_status;
	}

	public void setLoc_status(char loc_status) {
		this.loc_status = loc_status;
	}

	public String getGst_req_ind() {
		return gst_req_ind;
	}

	public void setGst_req_ind(String gst_req_ind) {
		this.gst_req_ind = gst_req_ind;
	}

	public String getGst_on_indv_chln() {
		return gst_on_indv_chln;
	}

	public void setGst_on_indv_chln(String gst_on_indv_chln) {
		this.gst_on_indv_chln = gst_on_indv_chln;
	}

	public Long getLoc_link_dptloc_id() {
		return loc_link_dptloc_id;
	}

	public void setLoc_link_dptloc_id(Long loc_link_dptloc_id) {
		this.loc_link_dptloc_id = loc_link_dptloc_id;
	}
	
	

	public Location(String loc_nm,Long loc_id) {
		super();
		this.loc_id = loc_id;
		this.loc_nm = loc_nm;
	}

	public Location(Long loc_subcomp_id,String loc_nm) {
		super();
		this.loc_nm = loc_nm;
		this.loc_subcomp_id = loc_subcomp_id;
	}
	
	public Location(Long loc_subcomp_id,String loc_nm,Long loc_id) {
		super();
		this.loc_nm = loc_nm;
		this.loc_subcomp_id = loc_subcomp_id;
		this.loc_id=loc_id;
	}

	public Location() {
	}
	
	public String getAutodesp_inprocess() {
		return autodesp_inprocess;
	}

	public void setAutodesp_inprocess(String autodesp_inprocess) {
		this.autodesp_inprocess = autodesp_inprocess;
	}

	public Long getLoc_sup_id() {
		return loc_sup_id;
	}

	public void setLoc_sup_id(Long loc_sup_id) {
		this.loc_sup_id = loc_sup_id;
	}
	
}
