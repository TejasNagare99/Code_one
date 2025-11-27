package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery (name = "call_LR_csv_download_revised_filter", procedureName = "LR_CSV_UPLOAD_PROC_JAVA_REVISED_FILTER",
			parameters = {
					@StoredProcedureParameter(name="startdt" , mode=ParameterMode.IN,type=String.class),
					@StoredProcedureParameter(name="enddt" , mode=ParameterMode.IN,type=String.class),
					@StoredProcedureParameter(name="tabl_ind" , mode=ParameterMode.IN,type=String.class),
					@StoredProcedureParameter(name="cfa" , mode=ParameterMode.IN,type=String.class),
					@StoredProcedureParameter(name="fsid" , mode=ParameterMode.IN,type=String.class)
					
			},resultClasses=Lr_csv_upload_java_proc_recised_filter.class),
	@NamedStoredProcedureQuery (name = "call_LR_csv_download_revised_filter_stockist", procedureName = "LR_CSV_UPLOAD_PROC_JAVA_REVISED_FILTER_STOCKIST ",
	parameters = {
			@StoredProcedureParameter(name="startdt" , mode=ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name="enddt" , mode=ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name="tabl_ind" , mode=ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name="cfa" , mode=ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name="fsid" , mode=ParameterMode.IN,type=String.class)
			
	},resultClasses=Lr_csv_upload_java_proc_recised_filter.class)
})



@Entity
@Table(name = "Lr_csv_upload_java_proc_recised_filter")
public class Lr_csv_upload_java_proc_recised_filter {

	@Id
	@Column(name = "sum_hd_id")
	private Long sum_hd_id;
	
	@Column(name = "SUMDSP_DIV_ID")
	private Long sumdsp_div_id;
	
	@Column(name = "TEAM_NAME")
	private String team_name;
	
	@Column(name = "MST_STN_NO")
	private String mst_stn_no;
	
	@Column(name = "MST_DESTINATION")
	private String mst_destination;
	
	@Column(name = "SUMDSP_LOC_ID")
	private String sumdsp_loc_id;
	
	@Column(name = "LOC_NM")
	private String loc_nm;

	public Long getSum_hd_id() {
		return sum_hd_id;
	}

	public void setSum_hd_id(Long sum_hd_id) {
		this.sum_hd_id = sum_hd_id;
	}

	public Long getSumdsp_div_id() {
		return sumdsp_div_id;
	}

	public void setSumdsp_div_id(Long sumdsp_div_id) {
		this.sumdsp_div_id = sumdsp_div_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getMst_stn_no() {
		return mst_stn_no;
	}

	public void setMst_stn_no(String mst_stn_no) {
		this.mst_stn_no = mst_stn_no;
	}

	public String getMst_destination() {
		return mst_destination;
	}

	public void setMst_destination(String mst_destination) {
		this.mst_destination = mst_destination;
	}

	public String getSumdsp_loc_id() {
		return sumdsp_loc_id;
	}

	public void setSumdsp_loc_id(String sumdsp_loc_id) {
		this.sumdsp_loc_id = sumdsp_loc_id;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}
	
	
	
}
