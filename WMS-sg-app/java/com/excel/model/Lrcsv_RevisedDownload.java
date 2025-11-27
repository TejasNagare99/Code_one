package com.excel.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
@NamedStoredProcedureQuery(name = "call_LR_csv_revised_download", //procedureName = "LR_CSV_UPLOAD_PROC_JAVA_REVISED",
procedureName = "LR_CSV_UPLOAD_PROC_JAVA_REVISED",
parameters= {
		@StoredProcedureParameter(name="startdt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="enddt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="tabl_ind" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="cfa" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fsid" , mode=ParameterMode.IN,type=String.class),
		
		@StoredProcedureParameter(name="div_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="sumdisp_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="sumdsp_loc_id" , mode=ParameterMode.IN,type=String.class),
		},resultClasses=Lrcsv_RevisedDownload.class) 


@NamedStoredProcedureQuery(name = "call_LR_csv_revised_download_stockist",
procedureName = "LR_CSV_UPLOAD_PROC_JAVA_REVISED_STOCKIST",
parameters= {
		@StoredProcedureParameter(name="startdt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="enddt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="tabl_ind" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="cfa" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fsid" , mode=ParameterMode.IN,type=String.class),
		
		@StoredProcedureParameter(name="div_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="sumdisp_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="sumdsp_loc_id" , mode=ParameterMode.IN,type=String.class),
		},resultClasses=Lrcsv_RevisedDownload.class) 

@NamedStoredProcedureQuery (name = "call_LR_csv_download_revised_stock_cfa", 
procedureName = "LR_CSV_UPLOAD_PROC_JAVA_STOCK_CFA",
parameters = {
		@StoredProcedureParameter(name="startdt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="enddt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="tbl_ind" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="cfa" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fsid" , mode=ParameterMode.IN,type=String.class)
		
},resultClasses=Lrcsv_RevisedDownload.class)

@Entity
@Table(name = "Lrcsv_RevisedDownload")
public class Lrcsv_RevisedDownload {
	@Id
	@Column(name = "SR_NO")
	private Long sr_no;
	@Column(name = "sum_hd_id")
	private String sum_hd_id;
	@Column(name = "TEAM_NAME")
	private String 	team_name;
	@Column(name = "TEAM_CODE")
	private String 	team_code;
	@Column(name = "MST_STN_NO")
	private String 	mst_stn_no;
	@Column(name = "MST_STN_DT")
	private Date 	mst_stn_dt;
	@Column(name = "MST_DESTINATION")
	private String 	mst_destination;
	@Column(name = "LOCATION")
	private String 	location;
	@Column(name = "STN_VALUE")
	private String 	stn_value;
	@Column(name = "MST_LR_NO")
	private String 	mst_lr_no;
	@Column(name = "MST_LR_DT")
	private Date 	mst_lr_dt;
	
	@Column(name = "MST_TENT_DELIVERY_DATE")
	private Date mst_tent_delivery_date;					//added
	
	@Column(name = "MST_COURIER_EXPENSES")
	private String 	mst_courier_expenses;				//added
	
	@Column(name = "MST_TRANSPORTER")
	private String 	mst_transporter;
	@Column(name = "MST_GRS_WGHT")
	private String 	mst_grs_wght;
	@Column(name = "MST_BILLWGHT")
	private String 	mst_billwght;
	@Column(name = "MST_TOT_CASE")
	private String 	mst_tot_case;
	@Column(name = "DSPFSTAFF_EMPLOYEENO")
	private String 	dspfstaff_employeeno;
	@Column(name = "DSPFSTAFF_DISPLAYNAME")
	private String 	dspfstaff_displayname;
	@Column(name = "DTL_CHLN_NO")
	private String 	dtl_chln_no;
	@Column(name = "DTL_CHLN_DT")
	private Date dtl_chln_dt;
	@Column(name = "DTL_CITY")
	private String 	dtl_city;
	@Column(name = "DTL_TRANSPORTER")
	private String 	dtl_transporter;
	@Column(name = "DTL_LR_NO")
	private String 	dtl_lr_no;
	@Column(name = "DTL_LR_DT")
	private Date 	dtl_lr_dt;
	
	@Column(name = "DTL_TENT_DELIVERY_DATE")
	private Date dtl_tent_delivery_date;					//added
	
	@Column(name = "DTL_COURIER_EXPENSES")
	private String 	dtl_courier_expenses;				//added
	
	@Column(name = "DTL_STN_GRSWGHT")
	private String 	dtl_stn_grswght;
	@Column(name = "DTL_STN_BILLWGHT")
	private String 	dtl_stn_billwght;
	@Column(name = "DTL_TOT_CASE")
	private String 	dtl_tot_case;
	
	@Column(name="MST_ACTUAL_DELIVERY_DATE")
	private Date mst_actual_delivery_date;					//changes
	
	@Column(name="MST_RECD_BY")
	private String mst_recd_by;
	
	@Column(name="MST_COURIER_REMARK")
	private String mst_courier_remark;						//changes
	
	@Column(name="DTL_ACTUAL_DELIVERY_DATE")					//changes
	private Date dtl_actual_delivery_date;
	
	@Column(name="DTL_RECD_BY")
	private String dtl_recd_by;
	
	@Column(name="DTL_COURIER_REMARK")				//changes
	private String dtl_courier_remark;
	
	@Column(name="WAY_BILL_NO")
	private String way_bill_no;
	
	@Column(name="MST_WAY_BILL_NO")
	private String mst_way_bill_no;
	
	@Column(name="DSP_FIN_YEAR")
	private String dsp_fin_year;
	
	@Column(name="DSP_PROD_TYPE")
	private String dsp_prod_type;
	
	@Column(name="DSP_TRANS_GST_REG_NO")
	private String dsp_trans_gst_reg_no;
	
	@Column(name="DSP_LORRY_NO")
	private String dsp_lorry_no;

	@Column(name = "MST_TRANSPORT_MODE")
	private String mst_transport_mode;
	
	@Column(name = "DTL_TRANSPORT_MODE")
	private String dtl_transport_mode;
	
	public String getDtl_transport_mode() {
		return dtl_transport_mode;
	}
	public void setDtl_transport_mode(String dtl_transport_mode) {
		this.dtl_transport_mode = dtl_transport_mode;
	}
	public String getMst_transport_mode() {
		return mst_transport_mode;
	}
	public void setMst_transport_mode(String mst_transport_mode) {
		this.mst_transport_mode = mst_transport_mode;
	}
	
	public String getDsp_lorry_no() {
		return dsp_lorry_no;
	}
	public void setDsp_lorry_no(String dsp_lorry_no) {
		this.dsp_lorry_no = dsp_lorry_no;
	}
	public String getDsp_trans_gst_reg_no() {
		return dsp_trans_gst_reg_no;
	}
	public void setDsp_trans_gst_reg_no(String dsp_trans_gst_reg_no) {
		this.dsp_trans_gst_reg_no = dsp_trans_gst_reg_no;
	}
	public String getDsp_fin_year() {
		return dsp_fin_year;
	}
	public void setDsp_fin_year(String dsp_fin_year) {
		this.dsp_fin_year = dsp_fin_year;
	}
	public String getMst_way_bill_no() {
		return mst_way_bill_no;
	}
	public void setMst_way_bill_no(String mst_way_bill_no) {
		this.mst_way_bill_no = mst_way_bill_no;
	}
	public Long getSr_no() {
		return sr_no;
	}
	public void setSr_no(Long sr_no) {
		this.sr_no = sr_no;
	}
	public String getSum_hd_id() {
		return sum_hd_id;
	}
	public void setSum_hd_id(String sum_hd_id) {
		this.sum_hd_id = sum_hd_id;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTeam_code() {
		return team_code;
	}
	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}
	public String getMst_stn_no() {
		return mst_stn_no;
	}
	public void setMst_stn_no(String mst_stn_no) {
		this.mst_stn_no = mst_stn_no;
	}
	public Date getMst_stn_dt() {
		return mst_stn_dt;
	}
	public void setMst_stn_dt(Date mst_stn_dt) {
		this.mst_stn_dt = mst_stn_dt;
	}
	public String getMst_destination() {
		return mst_destination;
	}
	public void setMst_destination(String mst_destination) {
		this.mst_destination = mst_destination;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStn_value() {
		return stn_value;
	}
	public void setStn_value(String stn_value) {
		this.stn_value = stn_value;
	}
	public String getMst_lr_no() {
		return mst_lr_no;
	}
	public void setMst_lr_no(String mst_lr_no) {
		this.mst_lr_no = mst_lr_no;
	}
	public Date getMst_lr_dt() {
		return mst_lr_dt;
	}
	public void setMst_lr_dt(Date mst_lr_dt) {
		this.mst_lr_dt = mst_lr_dt;
	}
	public Date getMst_tent_delivery_date() {
		return mst_tent_delivery_date;
	}
	public void setMst_tent_delivery_date(Date mst_tent_delivery_date) {
		this.mst_tent_delivery_date = mst_tent_delivery_date;
	}
	public String getMst_courier_expenses() {
		return mst_courier_expenses;
	}
	public void setMst_courier_expenses(String mst_courier_expenses) {
		this.mst_courier_expenses = mst_courier_expenses;
	}
	public String getMst_transporter() {
		return mst_transporter;
	}
	public void setMst_transporter(String mst_transporter) {
		this.mst_transporter = mst_transporter;
	}
	public String getMst_grs_wght() {
		return mst_grs_wght;
	}
	public void setMst_grs_wght(String mst_grs_wght) {
		this.mst_grs_wght = mst_grs_wght;
	}
	public String getMst_billwght() {
		return mst_billwght;
	}
	public void setMst_billwght(String mst_billwght) {
		this.mst_billwght = mst_billwght;
	}
	public String getMst_tot_case() {
		return mst_tot_case;
	}
	public void setMst_tot_case(String mst_tot_case) {
		this.mst_tot_case = mst_tot_case;
	}
	public String getDspfstaff_employeeno() {
		return dspfstaff_employeeno;
	}
	public void setDspfstaff_employeeno(String dspfstaff_employeeno) {
		this.dspfstaff_employeeno = dspfstaff_employeeno;
	}
	public String getDspfstaff_displayname() {
		return dspfstaff_displayname;
	}
	public void setDspfstaff_displayname(String dspfstaff_displayname) {
		this.dspfstaff_displayname = dspfstaff_displayname;
	}
	public String getDtl_chln_no() {
		return dtl_chln_no;
	}
	public void setDtl_chln_no(String dtl_chln_no) {
		this.dtl_chln_no = dtl_chln_no;
	}
	public Date getDtl_chln_dt() {
		return dtl_chln_dt;
	}
	public void setDtl_chln_dt(Date dtl_chln_dt) {
		this.dtl_chln_dt = dtl_chln_dt;
	}
	public String getDtl_city() {
		return dtl_city;
	}
	public void setDtl_city(String dtl_city) {
		this.dtl_city = dtl_city;
	}
	public String getDtl_transporter() {
		return dtl_transporter;
	}
	public void setDtl_transporter(String dtl_transporter) {
		this.dtl_transporter = dtl_transporter;
	}
	public String getDtl_lr_no() {
		return dtl_lr_no;
	}
	public void setDtl_lr_no(String dtl_lr_no) {
		this.dtl_lr_no = dtl_lr_no;
	}
	public Date getDtl_lr_dt() {
		return dtl_lr_dt;
	}
	public void setDtl_lr_dt(Date dtl_lr_dt) {
		this.dtl_lr_dt = dtl_lr_dt;
	}
	public Date getDtl_tent_delivery_date() {
		return dtl_tent_delivery_date;
	}
	public void setDtl_tent_delivery_date(Date dtl_tent_delivery_date) {
		this.dtl_tent_delivery_date = dtl_tent_delivery_date;
	}
	public String getDtl_courier_expenses() {
		return dtl_courier_expenses;
	}
	public void setDtl_courier_expenses(String dtl_courier_expenses) {
		this.dtl_courier_expenses = dtl_courier_expenses;
	}
	public String getDtl_stn_grswght() {
		return dtl_stn_grswght;
	}
	public void setDtl_stn_grswght(String dtl_stn_grswght) {
		this.dtl_stn_grswght = dtl_stn_grswght;
	}
	public String getDtl_stn_billwght() {
		return dtl_stn_billwght;
	}
	public void setDtl_stn_billwght(String dtl_stn_billwght) {
		this.dtl_stn_billwght = dtl_stn_billwght;
	}
	public String getDtl_tot_case() {
		return dtl_tot_case;
	}
	public void setDtl_tot_case(String dtl_tot_case) {
		this.dtl_tot_case = dtl_tot_case;
	}
	public Date getMst_actual_delivery_date() {
		return mst_actual_delivery_date;
	}
	public void setMst_actual_delivery_date(Date mst_actual_delivery_date) {
		this.mst_actual_delivery_date = mst_actual_delivery_date;
	}
	public String getMst_recd_by() {
		return mst_recd_by;
	}
	public void setMst_recd_by(String mst_recd_by) {
		this.mst_recd_by = mst_recd_by;
	}
	public String getMst_courier_remark() {
		return mst_courier_remark;
	}
	public void setMst_courier_remark(String mst_courier_remark) {
		this.mst_courier_remark = mst_courier_remark;
	}
	public Date getDtl_actual_delivery_date() {
		return dtl_actual_delivery_date;
	}
	public void setDtl_actual_delivery_date(Date dtl_actual_delivery_date) {
		this.dtl_actual_delivery_date = dtl_actual_delivery_date;
	}
	public String getDtl_recd_by() {
		return dtl_recd_by;
	}
	public void setDtl_recd_by(String dtl_recd_by) {
		this.dtl_recd_by = dtl_recd_by;
	}
	public String getDtl_courier_remark() {
		return dtl_courier_remark;
	}
	public void setDtl_courier_remark(String dtl_courier_remark) {
		this.dtl_courier_remark = dtl_courier_remark;
	}
	public String getWay_bill_no() {
		return way_bill_no;
	}
	public void setWay_bill_no(String way_bill_no) {
		this.way_bill_no = way_bill_no;
	}
	public String getDsp_prod_type() {
		return dsp_prod_type;
	}
	public void setDsp_prod_type(String dsp_prod_type) {
		this.dsp_prod_type = dsp_prod_type;
	}
	
	
	
}
