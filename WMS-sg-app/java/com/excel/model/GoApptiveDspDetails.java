package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import com.excel.bean.GoApptiveTerrMasterBean;

@Entity
@Table(name = "GoApptiveDspDetails")
@NamedStoredProcedureQuery(name = "callsampledisptogoaptive",
procedureName = "SAMPLE_DISP_TO_GOAPTIVE" ,                      
parameters = {
		@StoredProcedureParameter(name="pvfrmdt", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="pvtodt", mode = ParameterMode.IN, type = String.class),
},
resultClasses= GoApptiveDspDetails.class)
public class GoApptiveDspDetails {

	@Id
	@Column(name = "ROW")
	private Long row;
	
	@Column(name = "DATE_TIME")
	private Date date_time;
	
	@Column(name = "TERR_CODE")
	private String terr_code;
	
	@Column(name = "TERR_DIVISION")
	private String terr_division;
	
	@Column(name = "TERR_TEAM_CODE")
	private String terr_team_code;
	
	@Column(name = "DSP_ID")
	private Long dsp_id;
	
	@Column(name = "DSP_FIN_YEAR")
	private Long dsp_fin_year;
	
	@Column(name = "DSP_PERIOD_CODE")
	private String dsp_period_code;
	
	@Column(name = "DSP_CHALLAN_NO")
	private String dsp_challan_no;
	
	@Column(name = "DSP_CHALLAN_DT")
	private Date dsp_challan_dt;
	
	@Column(name = "DSP_LR_DT")
	private Date dsp_lr_dt;
	
	@Column(name = "DSP_TRANSPORTER")
	private String dsp_transporter;
	
	@Column(name = "DSPFSTAFF_NAME")
	private String dspfstaff_name;
	
	@Column(name = "DSPFSTAFF_EMPLOYEENO")
	private String dspfstaff_employeeno;
	
	@Column(name = "DSPDTL_PROD_NAME")
	private String dspdtl_prod_name;
	
	@Column(name = "DSPDTL_BATCH_NO")
	private String dspdtl_batch_no;
	
	@Column(name = "DSP_EXPIRY_DT")
	private Date dsp_expiry_dt;
	
	@Column(name = "DSPDTL_DISP_QTY")
	private BigDecimal dspdtl_disp_qty;
	
	@Column(name = "DSPDTL_RATE")
	private BigDecimal dspdtl_rate;
	
	@Column(name = "DSP_WT")
	private BigDecimal dsp_wt;
	
	@Column(name = "DSP_CASES")
	private Long cases;
	
	@Column(name = "DSPFSTAFF_ADDR1")
	private String dspfstaff_addr1;
	
	@Column(name = "DSPFSTAFF_ADDR2")
	private String dspfstaff_addr2;
	
	@Column(name = "DSPFSTAFF_ADDR3")
	private String dspfstaff_addr3;
	
	@Column(name = "DSPFSTAFF_ADDR4")
	private String dspfstaff_addr4;
	
	@Column(name = "DSPFSTAFF_DESTINATION")
	private String dspfstaff_destination;
	
	@Column(name = "DSPFSTAFF_TELNO")
	private String dspfstaff_telno;
	
	@Column(name = "DSPFSTAFF_MOBILE")
	private String dspfstaff_mobile;
	
	@Column(name = "DSPFSTAFF_EMAIL")
	private String dspfstaff_email;
	
	@Column(name = "DSPDTL_ID")
	private Long dspdtl_id;
	
	@Column(name = "DSPDTL_RESENT")
	private String dspdtl_resent;
	
	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;
	

	public static class DispatchResponse{
		private Long DSP_ID;
		private String status;
		private GoApptiveDspDetails errors;
		public Long getDSP_ID() {
			return DSP_ID;
		}
		public void setDSP_ID(Long dSP_ID) {
			DSP_ID = dSP_ID;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public GoApptiveDspDetails getErrors() {
			return errors;
		}
		public void setErrors(GoApptiveDspDetails errors) {
			this.errors = errors;
		}
	
	
		
		
	}
	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public String getTerr_code() {
		return terr_code;
	}

	public void setTerr_code(String terr_code) {
		this.terr_code = terr_code;
	}

	public String getTerr_division() {
		return terr_division;
	}

	public void setTerr_division(String terr_division) {
		this.terr_division = terr_division;
	}

	public String getTerr_team_code() {
		return terr_team_code;
	}

	public void setTerr_team_code(String terr_team_code) {
		this.terr_team_code = terr_team_code;
	}

	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}

	public Long getDsp_fin_year() {
		return dsp_fin_year;
	}

	public void setDsp_fin_year(Long dsp_fin_year) {
		this.dsp_fin_year = dsp_fin_year;
	}

	public String getDsp_period_code() {
		return dsp_period_code;
	}

	public void setDsp_period_code(String dsp_period_code) {
		this.dsp_period_code = dsp_period_code;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public Date getDsp_challan_dt() {
		return dsp_challan_dt;
	}

	public void setDsp_challan_dt(Date dsp_challan_dt) {
		this.dsp_challan_dt = dsp_challan_dt;
	}

	public Date getDsp_lr_dt() {
		return dsp_lr_dt;
	}

	public void setDsp_lr_dt(Date dsp_lr_dt) {
		this.dsp_lr_dt = dsp_lr_dt;
	}

	public String getDsp_transporter() {
		return dsp_transporter;
	}

	public void setDsp_transporter(String dsp_transporter) {
		this.dsp_transporter = dsp_transporter;
	}

	public String getDspfstaff_name() {
		return dspfstaff_name;
	}

	public void setDspfstaff_name(String dspfstaff_name) {
		this.dspfstaff_name = dspfstaff_name;
	}

	public String getDspfstaff_employeeno() {
		return dspfstaff_employeeno;
	}

	public void setDspfstaff_employeeno(String dspfstaff_employeeno) {
		this.dspfstaff_employeeno = dspfstaff_employeeno;
	}

	public String getDspdtl_prod_name() {
		return dspdtl_prod_name;
	}

	public void setDspdtl_prod_name(String dspdtl_prod_name) {
		this.dspdtl_prod_name = dspdtl_prod_name;
	}

	public String getDspdtl_batch_no() {
		return dspdtl_batch_no;
	}

	public void setDspdtl_batch_no(String dspdtl_batch_no) {
		this.dspdtl_batch_no = dspdtl_batch_no;
	}

	public Date getDsp_expiry_dt() {
		return dsp_expiry_dt;
	}

	public void setDsp_expiry_dt(Date dsp_expiry_dt) {
		this.dsp_expiry_dt = dsp_expiry_dt;
	}

	public BigDecimal getDspdtl_disp_qty() {
		return dspdtl_disp_qty;
	}

	public void setDspdtl_disp_qty(BigDecimal dspdtl_disp_qty) {
		this.dspdtl_disp_qty = dspdtl_disp_qty;
	}

	public BigDecimal getDspdtl_rate() {
		return dspdtl_rate;
	}

	public void setDspdtl_rate(BigDecimal dspdtl_rate) {
		this.dspdtl_rate = dspdtl_rate;
	}

	public BigDecimal getDsp_wt() {
		return dsp_wt;
	}

	public void setDsp_wt(BigDecimal dsp_wt) {
		this.dsp_wt = dsp_wt;
	}

	public Long getCases() {
		return cases;
	}

	public void setCases(Long cases) {
		this.cases = cases;
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

	public String getDspfstaff_destination() {
		return dspfstaff_destination;
	}

	public void setDspfstaff_destination(String dspfstaff_destination) {
		this.dspfstaff_destination = dspfstaff_destination;
	}

	public String getDspfstaff_telno() {
		return dspfstaff_telno;
	}

	public void setDspfstaff_telno(String dspfstaff_telno) {
		this.dspfstaff_telno = dspfstaff_telno;
	}

	public String getDspfstaff_mobile() {
		return dspfstaff_mobile;
	}

	public void setDspfstaff_mobile(String dspfstaff_mobile) {
		this.dspfstaff_mobile = dspfstaff_mobile;
	}

	public String getDspfstaff_email() {
		return dspfstaff_email;
	}

	public void setDspfstaff_email(String dspfstaff_email) {
		this.dspfstaff_email = dspfstaff_email;
	}

	public Long getDspdtl_id() {
		return dspdtl_id;
	}

	public void setDspdtl_id(Long dspdtl_id) {
		this.dspdtl_id = dspdtl_id;
	}

	public String getDspdtl_resent() {
		return dspdtl_resent;
	}

	public void setDspdtl_resent(String dspdtl_resent) {
		this.dspdtl_resent = dspdtl_resent;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}
	
		
	
}
