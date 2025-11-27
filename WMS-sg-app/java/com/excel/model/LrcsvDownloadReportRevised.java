package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@NamedStoredProcedureQuery(name = "call_LR_csv_upload_proc_report_revised", 
//procedureName = "LR_CSV_UPLOAD_PROC_REPORT",
  procedureName = "LR_CSV_UPLOAD_PROC_REPORT_REVISED", 
//procedureName = "LR_CSV_UPLOAD_PROC_REPORT_REVISED_PIN",
parameters= {
		@StoredProcedureParameter(name="startdt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="enddt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="tabl_ind" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="cfa" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fsid" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fstype" , mode=ParameterMode.IN,type=String.class)
		},resultClasses=LrcsvDownloadReportRevised.class) 
@Entity
@Table(name = "LrcsvDownloadReportRevised")
public class LrcsvDownloadReportRevised {

	@Id
	@Column(name = "SR_NO")
	private long sr_no;
	
	@Column(name = "TEAM_NAME")
	private String team_name;
	
	@Column(name = "TEAM_CODE")
	private String team_code;
	
	@Column(name = "MST_STN_NO")
	private String mst_stn_no;
	
	@Column(name = "MST_STN_DT")
	private String mst_stn_dt;
	
	@Column(name = "sum_hd_id")
	private String sum_hd_id;
	
	@Column(name = "MST_DESTINATION")
	private String mst_destination;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "STN_VALUE")
	private String stn_value;
	
	@Column(name = "MST_LR_NO")
	private String mst_lr_no;
	
	@Column(name = "MST_LR_DT")
	private String mst_lr_dt;
	
	@Column(name = "MST_TENT_DELIVERY_DATE")	//Added In Revised
	private String mst_tent_delivery_date;
	
	@Column(name = "MST_COURIER_EXPENSES")		//Added In Revised
	private BigDecimal mst_courier_expenses;
	
	@Column(name = "MST_TRANSPORTER")
	private String mst_transporter;
	
	@Column(name = "MST_GRS_WGHT")
	private String mst_grs_wght;
	
	@Column(name = "MST_BILLWGHT")
	private String mst_billwght;
	
	@Column(name = "MST_TOT_CASE")
	private String mst_tot_case;
	
	@Column(name = "DSPFSTAFF_EMPLOYEENO")
	private String dspfstaff_employeeno;
	
	@Column(name = "DSPFSTAFF_DISPLAYNAME")
	private String dspfstaff_displayname;
	
	@Column(name = "DTL_CHLN_NO")
	private String dtl_chln_no;
	
	@Column(name = "DTL_CHLN_DT")
	private String dtl_chln_dt;
	
	@Column(name = "DTL_CITY")
	private String dtl_city;
	
	@Column(name = "DTL_TRANSPORTER")
	private String dtl_transporter;
	
	@Column(name = "DTL_LR_NO")
	private String dtl_lr_no;
	
	@Column(name = "DTL_LR_DT")
	private String dtl_lr_dt;
	
	@Column(name = "DTL_TENT_DELIVERY_DATE")	//Added In Revised
	private String dtl_tent_delivery_date;
	
	@Column(name = "DTL_COURIER_EXPENSES")		//Added In Revised
	private BigDecimal dtl_courier_expenses;
	
	@Column(name = "DTL_STN_GRSWGHT")
	private String dtl_stn_grswght;
	
	@Column(name = "DTL_STN_BILLWGHT")
	private String dtl_stn_billwght;
	
	@Column(name = "DTL_TOT_CASE")
	private String dtl_tot_case;
	
	@Column(name = "MST_ACTUAL_DELIVERY_DATE")		//Added In Revised
	private Date mst_actual_delivery_date;
	
	@Column(name = "MST_RECD_BY")			
	private String mst_recd_by;
	
	@Column(name = "MST_COURIER_REMARK")		//Added In Revised
	private String mst_courier_remark;
	
	@Column(name = "DTL_ACTUAL_DELIVERY_DATE")		//Added In Revised
	private String dtl_actual_delivery_date;
	
	@Column(name = "DTL_RECD_BY")
	private String dtl_recd_by;
	
	@Column(name = "WAY_BILL_NO")
	private String way_bill_no;
	
	@Column(name = "DTL_COURIER_REMARK")		//Added In Revised
	private String dtl_courier_remark;
	
	@Column(name = "ADDR1")
	private String addr1;
	
	@Column(name = "ADDR2")
	private String addr2;
	
	@Column(name = "ADDR3")
	private String addr3;
	
	@Column(name = "ADDR4")
	private String addr4;
	
	@Column(name = "ADDR5")
	private String addr5;
	
	@Column(name = "MOBILE")
	private String mobile;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PSO_REMARK")
	private String pso_remark;
	
	@Column(name = "PSO_RTN_DETAILS")
	private String pso_rtn_details;
	
	@Column(name = "MST_WAY_BILL_NO")
	private String mst_way_bill_no;
	
	
	@Column(name = "DSP_FIN_YEAR")
	private String dsp_fin_year;
	
	@Column(name = "PINCODE")         //added  in pin proc
	private String pincode;

	@Column(name="DTL_VALUE")
	private BigDecimal dtl_value;
	
	@Column(name="DSP_PROD_TYPE")
	private String dsp_prod_type;
	
	@Column(name="PSO_RESG_STKRETU")
	private String pso_resg_stkretu;
	
	@Column(name="MST_TRANSPORT_MODE")
	private String mst_transport_mode;
	
	@Column(name="DTL_TRANSPORT_MODE")
	private String dtl_transport_mode;
	
	public String getMst_transport_mode() {
		return mst_transport_mode;
	}

	public void setMst_transport_mode(String mst_transport_mode) {
		this.mst_transport_mode = mst_transport_mode;
	}

	public String getDtl_transport_mode() {
		return dtl_transport_mode;
	}

	public void setDtl_transport_mode(String dtl_transport_mode) {
		this.dtl_transport_mode = dtl_transport_mode;
	}
	
	public String getPso_resg_stkretu() {
		return pso_resg_stkretu;
	}

	public void setPso_resg_stkretu(String pso_resg_stkretu) {
		this.pso_resg_stkretu = pso_resg_stkretu;
	}
	
	public String getDsp_prod_type() {
		return dsp_prod_type;
	}

	public void setDsp_prod_type(String dsp_prod_type) {
		this.dsp_prod_type = dsp_prod_type;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
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

	public long getSr_no() {
		return sr_no;
	}

	public void setSr_no(long sr_no) {
		this.sr_no = sr_no;
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

	public String getMst_stn_dt() {
		return mst_stn_dt;
	}

	public void setMst_stn_dt(String mst_stn_dt) {
		this.mst_stn_dt = mst_stn_dt;
	}

	public String getSum_hd_id() {
		return sum_hd_id;
	}

	public void setSum_hd_id(String sum_hd_id) {
		this.sum_hd_id = sum_hd_id;
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

	public String getMst_lr_dt() {
		return mst_lr_dt;
	}

	public void setMst_lr_dt(String mst_lr_dt) {
		this.mst_lr_dt = mst_lr_dt;
	}

	public String getMst_tent_delivery_date() {
		return mst_tent_delivery_date;
	}

	public void setMst_tent_delivery_date(String mst_tent_delivery_date) {
		this.mst_tent_delivery_date = mst_tent_delivery_date;
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

	public String getDtl_chln_dt() {
		return dtl_chln_dt;
	}

	public void setDtl_chln_dt(String dtl_chln_dt) {
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

	public String getDtl_lr_dt() {
		return dtl_lr_dt;
	}

	public void setDtl_lr_dt(String dtl_lr_dt) {
		this.dtl_lr_dt = dtl_lr_dt;
	}

	public String getDtl_tent_delivery_date() {
		return dtl_tent_delivery_date;
	}

	public void setDtl_tent_delivery_date(String dtl_tent_delivery_date) {
		this.dtl_tent_delivery_date = dtl_tent_delivery_date;
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

	public String getDtl_actual_delivery_date() {
		return dtl_actual_delivery_date;
	}

	public void setDtl_actual_delivery_date(String dtl_actual_delivery_date) {
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

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getAddr3() {
		return addr3;
	}

	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}

	public String getAddr4() {
		return addr4;
	}

	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}

	public String getAddr5() {
		return addr5;
	}

	public void setAddr5(String addr5) {
		this.addr5 = addr5;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPso_remark() {
		return pso_remark;
	}

	public void setPso_remark(String pso_remark) {
		this.pso_remark = pso_remark;
	}

	public String getPso_rtn_details() {
		return pso_rtn_details;
	}

	public void setPso_rtn_details(String pso_rtn_details) {
		this.pso_rtn_details = pso_rtn_details;
	}

	public String getWay_bill_no() {
		return way_bill_no;
	}

	public void setWay_bill_no(String way_bill_no) {
		this.way_bill_no = way_bill_no;
	}

	public BigDecimal getMst_courier_expenses() {
		return mst_courier_expenses;
	}

	public void setMst_courier_expenses(BigDecimal mst_courier_expenses) {
		this.mst_courier_expenses = mst_courier_expenses;
	}

	public BigDecimal getDtl_courier_expenses() {
		return dtl_courier_expenses;
	}

	public void setDtl_courier_expenses(BigDecimal dtl_courier_expenses) {
		this.dtl_courier_expenses = dtl_courier_expenses;
	}

	public BigDecimal getDtl_value() {
		return dtl_value;
	}

	public void setDtl_value(BigDecimal dtl_value) {
		this.dtl_value = dtl_value;
	}

	
	
	

}
