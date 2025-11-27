package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name="PrePrintedDetailChallan_withgst")
@NamedStoredProcedureQuery(name = "callPrePrintedDetailChallan_withgst", procedureName = "PrePrintedDetailChallan_WITHGST",
parameters= {
		@StoredProcedureParameter(name="div_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="loc_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="frm_challan_no" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="to_challan_no" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="dispatchtype" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="prod_type" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="rep_type" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fin_year_flag" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fin_year_id" , mode=ParameterMode.IN,type=String.class),
		},resultClasses=PrePrintedDetailChallan_withgst.class) 
@NamedStoredProcedureQuery(name = "callPrePrintedDetailChallan_withgst_NOVA", procedureName = "PrePrintedDetailChallan_WITHGST_NOVA",
parameters= {
		@StoredProcedureParameter(name="div_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="loc_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="frm_challan_no" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="to_challan_no" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="dispatchtype" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="prod_type" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="rep_type" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fin_year_flag" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="fin_year_id" , mode=ParameterMode.IN,type=String.class),
		},resultClasses=PrePrintedDetailChallan_withgst.class) 


public class PrePrintedDetailChallan_withgst {

	@Id
	@Column(name="Row")
	Long row;
	
	@Column(name="TEAM")
	String team;
	
	@Column(name="TEAM_CODE")
	String team_code;
	
	@Column(name="DIVISION")
	String division;
	
	@Column(name="DIV_CODE")
	String div_code;
	
	@Column(name="DSP_ID")
	Long dsp_id;
	
	@Column(name="FSTAFF_ID")
	String fstaff_id;
	
	@Column(name="HQ_CODE")
	String hq_code;
	
	@Column(name="DSP_CHALLAN_NO")
	String dsp_challan_no;
	
	@Column(name="DSP_CHALLAN_DT")
	String dsp_challan_dt;
	
	@Column(name="DSPFSTAFF_EMPLOYEENO")
	String dspfstaff_employeeno;
	
	@Column(name="DSPFSTAFF_DISPLAYNAME")
	String dspfstaff_displayname;
	
	@Column(name="DSPFSTAFFADDR1")
	String dspfstaffaddr1;
	
	@Column(name="DSPFSTAFFADDR2")
	String dspfstaffaddr2;
	
	@Column(name="DSPFSTAFFADDR3")
	String dspfstaffaddr3;
	
	@Column(name="DSPFSTAFFADDR4")
	String dspfstaffaddr4;
	
	@Column(name="DPTDESTINATION")
	String dptdestination;
	
	@Column(name="DPTTEL_NO")
	String dpttel_no;
	
	@Column(name="ship_doc_no")
	String ship_doc_no;
	
	@Column(name="ship_doc_date")
	String ship_doc_date;
	
	@Column(name="DSP_LORRY_NO")
	String dsp_lorry_no;
	
	@Column(name="DSP_TRANSPORTER")
	String dsp_transporter;
	
	@Column(name="DSPDTL_PROD_ID")
	String dspdtl_prod_id;
	
	@Column(name ="SMP_ERP_PROD_CD")
	String smp_erp_prod_cd;
	
	@Column(name="prod_name_pack")
	String prod_name_pack;
	
	@Column(name="BATCH_NO")
	String batch_no;
	
	@Column(name="mfg_yr")
	String mfg_yr;
	
	@Column(name="BATCH_EXPIRY_DT")
	String batch_expiry_dt;
	
	@Column(name="DSP_CASES")
	String dsp_cases;
	
	@Column(name="DSPDTL_DISP_QTY")
	BigDecimal dspdtl_disp_qty;
	
	@Column(name="DSPDTL_RATE")
	BigDecimal dspdtl_rate;
	
	@Column(name="disp_value")
	BigDecimal disp_value;
	
	@Column(name="TOT_DSP_CASES")
	BigDecimal tot_dsp_cases;
	
	@Column(name="PERMIT_NO")
	String permit_no;
	
	@Column(name="WEIGH")
	BigDecimal weigh;
	
	@Column(name="DSPFSTAFF_MOBILE")
	String dspfstaff_mobile;
	
	@Column(name="DSPFSTAFF_MOBILE2")
	String dspfstaff_mobile2;				///added
	
	@Column(name="DSPFSTAFF_EMAIL")
	String dspfstaff_email;
	
	@Column(name="DSP_challan_msg")
	String dsp_challan_msg;
	
	@Column(name="DPTCST_NO")
	String dptcst_no;
	
	@Column(name="smp_prod_type")
	String smp_prod_type;
	
	@Column(name="cfa")
	String cfa;
	
	@Column(name="locadd1")
	String locadd1;
	
	@Column(name="locadd2")
	String locadd2;
	
	@Column(name="locadd3")
	String locadd3;
	
	@Column(name="locadd4")
	String locadd4;
	
	@Column(name="LIC1")
	String lic1;
	
	@Column(name="LIC2")
	String lic2;
	
	@Column(name="loc_cst_no")
	String loc_cst_no;
	
	@Column(name="loc_tin_no")
	String loc_tin_no;
	
	@Column(name="DPTPAN_NO")
	String dptpan_no;
	
	@Column(name="loc_telephone_no")
	String loc_telephone_no;
	
	@Column(name="loc_mobile_no")
	String loc_mobile_no;
	
	@Column(name="loc_cinno")
	String loc_cinno;
	
	@Column(name="loc_web_site")
	String loc_web_site;
	
	@Column(name="loc_emailid")
	String loc_emailid;
	
	@Column(name="HQ_NAME")
	String hq_name;
	
	@Column(name="STATE")
	String state;
	
	@Column(name="fr_num")
	String fr_num;
	
	@Column(name="to_num")
	String to_num;
	
	@Column(name="DPTLST_NO")
	String dptlst_no;
	
	@Column(name="DPTDRUG_LIC1")
	String dptdrug_lic1;
	
	@Column(name="DPTDRUG_LIC2")
	String dptdrug_lic2;
	
	@Column(name="DPTPAN_NO1")
	String dptpan_no1;
	
	@Column(name="CF_TEL_NO")
	String cf_tel_no;
	
	@Column(name="cf_mobile_no")
	String cf_mobile_no;
	
	@Column(name="cf_cinno")
	String cf_cinno;
	
	@Column(name="cf_web_site")
	String cf_web_site;
	
	@Column(name="ARN_NO")
	String arn_no;
	
	@Column(name="GST_REG_NO")
	String gst_reg_no;
	
	@Column(name="LOC_STATENAME")
	String loc_statename;
	
	@Column(name="LOC_STATECODE")
	String loc_statecode;
	
	@Column(name="HSN_CODE")
	String hsn_code;
	
	@Column(name="HGST_DOC_TYPE")
	String hgst_doc_type;
	
	@Column(name="HSGST_BILL_AMT")
	BigDecimal hsgst_bill_amt;
	
	@Column(name="BILLTONAME1")
	String billtoname1;
	
	@Column(name="BILLTONAME2")
	String billtoname2;
	
	@Column(name="HCGST_BILL_AMT")
	BigDecimal hcgst_bill_amt;
	
	@Column(name="HIGST_BILL_AMT")
	BigDecimal higst_bill_amt;
	
	@Column(name="DGST_DOC_TYPE")
	String dgst_doc_type;
	
	@Column(name="SGST_RATE")
	BigDecimal sgst_rate;
	
	@Column(name="SGST_BILL_AMT")
	BigDecimal sgst_bill_amt;
	
	@Column(name="CGST_RATE")
	BigDecimal cgst_rate;
	
	@Column(name="CGST_BILL_AMT")
	BigDecimal cgst_bill_amt;
	
	@Column(name="IGST_RATE")
	BigDecimal igst_rate;
	
	@Column(name="IGST_BILL_AMT")
	BigDecimal igst_bill_amt;
	
	@Column(name="GST_DOC_TYPE")
	String gst_doc_type;
	
	@Column(name="ROUNDOFF")
	BigDecimal roundoff;
	
	@Column(name="GST_DESCRIPTION")
	String gst_description;
	
	@Column(name="REPORT")
	String report;
	
	@Column(name="DSPDTL_ALLOC_ID")
	String dspdtl_alloc_id;
	
	@Column(name="BILLTO_STATENAME")
	String billto_statename;
	
	@Column(name="BILLTO_STATECODE")
	String billto_statecode;
	
	@Column(name="ALLOC_REMARK")
	String alloc_remark;
	
	@Column(name="PRI_NO")
	String pri_no;
	
	@Column(name="SMP_PROD_CD")
	String smp_prod_cd;
	
	@Column(name="PACK_DISP_NM")
	String pack_disp_nm;
	
	@Column(name="BILLTO_GST_REG_NO")
	String billto_gst_reg_no;
	
	@Column(name="UOM_DISP_NM")
	String uom_disp_nm;

	@Column(name="STATE_CODE")
	String state_code;
	
	
	@Column(name="FSTAFF_DISPLAY_NAME")
	String fstaff_display_name;
	
	@Column(name="FSTAFF_MOBILE")
	String fstaff_mobile;
	
	public String getFstaff_display_name() {
		return fstaff_display_name;
	}

	public void setFstaff_display_name(String fstaff_display_name) {
		this.fstaff_display_name = fstaff_display_name;
	}

	public String getFstaff_mobile() {
		return fstaff_mobile;
	}

	public void setFstaff_mobile(String fstaff_mobile) {
		this.fstaff_mobile = fstaff_mobile;
	}
	
	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getPack_disp_nm() {
		return pack_disp_nm;
	}

	public void setPack_disp_nm(String pack_disp_nm) {
		this.pack_disp_nm = pack_disp_nm;
	}

	public String getUom_disp_nm() {
		return uom_disp_nm;
	}

	public void setUom_disp_nm(String uom_disp_nm) {
		this.uom_disp_nm = uom_disp_nm;
	}

	public String getDspfstaff_mobile2() {
		return dspfstaff_mobile2;
	}

	public void setDspfstaff_mobile2(String dspfstaff_mobile2) {
		this.dspfstaff_mobile2 = dspfstaff_mobile2;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDiv_code() {
		return div_code;
	}

	public void setDiv_code(String div_code) {
		this.div_code = div_code;
	}

	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}

	public String getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(String fstaff_id) {
		this.fstaff_id = fstaff_id;
	}

	public String getHq_code() {
		return hq_code;
	}

	public void setHq_code(String hq_code) {
		this.hq_code = hq_code;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public String getDsp_challan_dt() {
		return dsp_challan_dt;
	}

	public void setDsp_challan_dt(String dsp_challan_dt) {
		this.dsp_challan_dt = dsp_challan_dt;
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

	public String getDspfstaffaddr1() {
		return dspfstaffaddr1;
	}

	public void setDspfstaffaddr1(String dspfstaffaddr1) {
		this.dspfstaffaddr1 = dspfstaffaddr1;
	}

	public String getDspfstaffaddr2() {
		return dspfstaffaddr2;
	}

	public void setDspfstaffaddr2(String dspfstaffaddr2) {
		this.dspfstaffaddr2 = dspfstaffaddr2;
	}

	public String getDspfstaffaddr3() {
		return dspfstaffaddr3;
	}

	public void setDspfstaffaddr3(String dspfstaffaddr3) {
		this.dspfstaffaddr3 = dspfstaffaddr3;
	}

	public String getDspfstaffaddr4() {
		return dspfstaffaddr4;
	}

	public void setDspfstaffaddr4(String dspfstaffaddr4) {
		this.dspfstaffaddr4 = dspfstaffaddr4;
	}

	public String getDptdestination() {
		return dptdestination;
	}

	public void setDptdestination(String dptdestination) {
		this.dptdestination = dptdestination;
	}

	public String getDpttel_no() {
		return dpttel_no;
	}

	public void setDpttel_no(String dpttel_no) {
		this.dpttel_no = dpttel_no;
	}

	public String getShip_doc_no() {
		return ship_doc_no;
	}

	public void setShip_doc_no(String ship_doc_no) {
		this.ship_doc_no = ship_doc_no;
	}

	public String getShip_doc_date() {
		return ship_doc_date;
	}

	public void setShip_doc_date(String ship_doc_date) {
		this.ship_doc_date = ship_doc_date;
	}

	public String getDsp_lorry_no() {
		return dsp_lorry_no;
	}

	public void setDsp_lorry_no(String dsp_lorry_no) {
		this.dsp_lorry_no = dsp_lorry_no;
	}

	public String getDsp_transporter() {
		return dsp_transporter;
	}

	public void setDsp_transporter(String dsp_transporter) {
		this.dsp_transporter = dsp_transporter;
	}

	public String getDspdtl_prod_id() {
		return dspdtl_prod_id;
	}

	public void setDspdtl_prod_id(String dspdtl_prod_id) {
		this.dspdtl_prod_id = dspdtl_prod_id;
	}

	public String getSmp_erp_prod_cd() {
		return smp_erp_prod_cd;
	}

	public void setSmp_erp_prod_cd(String smp_erp_prod_cd) {
		this.smp_erp_prod_cd = smp_erp_prod_cd;
	}

	public String getProd_name_pack() {
		return prod_name_pack;
	}

	public void setProd_name_pack(String prod_name_pack) {
		this.prod_name_pack = prod_name_pack;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getMfg_yr() {
		return mfg_yr;
	}

	public void setMfg_yr(String mfg_yr) {
		this.mfg_yr = mfg_yr;
	}

	public String getBatch_expiry_dt() {
		return batch_expiry_dt;
	}

	public void setBatch_expiry_dt(String batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}

	public String getDsp_cases() {
		return dsp_cases;
	}

	public void setDsp_cases(String dsp_cases) {
		this.dsp_cases = dsp_cases;
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

	public BigDecimal getDisp_value() {
		return disp_value;
	}

	public void setDisp_value(BigDecimal disp_value) {
		this.disp_value = disp_value;
	}

	public BigDecimal getTot_dsp_cases() {
		return tot_dsp_cases;
	}

	public void setTot_dsp_cases(BigDecimal tot_dsp_cases) {
		this.tot_dsp_cases = tot_dsp_cases;
	}

	public String getPermit_no() {
		return permit_no;
	}

	public void setPermit_no(String permit_no) {
		this.permit_no = permit_no;
	}

	public BigDecimal getWeigh() {
		return weigh;
	}

	public void setWeigh(BigDecimal weigh) {
		this.weigh = weigh;
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

	public String getDsp_challan_msg() {
		return dsp_challan_msg;
	}

	public void setDsp_challan_msg(String dsp_challan_msg) {
		this.dsp_challan_msg = dsp_challan_msg;
	}

	public String getDptcst_no() {
		return dptcst_no;
	}

	public void setDptcst_no(String dptcst_no) {
		this.dptcst_no = dptcst_no;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getCfa() {
		return cfa;
	}

	public void setCfa(String cfa) {
		this.cfa = cfa;
	}

	public String getLocadd1() {
		return locadd1;
	}

	public void setLocadd1(String locadd1) {
		this.locadd1 = locadd1;
	}

	public String getLocadd2() {
		return locadd2;
	}

	public void setLocadd2(String locadd2) {
		this.locadd2 = locadd2;
	}

	public String getLocadd3() {
		return locadd3;
	}

	public void setLocadd3(String locadd3) {
		this.locadd3 = locadd3;
	}

	public String getLocadd4() {
		return locadd4;
	}

	public void setLocadd4(String locadd4) {
		this.locadd4 = locadd4;
	}

	public String getLic1() {
		return lic1;
	}

	public void setLic1(String lic1) {
		this.lic1 = lic1;
	}

	public String getLic2() {
		return lic2;
	}

	public void setLic2(String lic2) {
		this.lic2 = lic2;
	}

	public String getLoc_cst_no() {
		return loc_cst_no;
	}

	public void setLoc_cst_no(String loc_cst_no) {
		this.loc_cst_no = loc_cst_no;
	}

	public String getLoc_tin_no() {
		return loc_tin_no;
	}

	public void setLoc_tin_no(String loc_tin_no) {
		this.loc_tin_no = loc_tin_no;
	}

	public String getDptpan_no() {
		return dptpan_no;
	}

	public void setDptpan_no(String dptpan_no) {
		this.dptpan_no = dptpan_no;
	}

	public String getLoc_telephone_no() {
		return loc_telephone_no;
	}

	public void setLoc_telephone_no(String loc_telephone_no) {
		this.loc_telephone_no = loc_telephone_no;
	}

	public String getLoc_mobile_no() {
		return loc_mobile_no;
	}

	public void setLoc_mobile_no(String loc_mobile_no) {
		this.loc_mobile_no = loc_mobile_no;
	}

	public String getLoc_cinno() {
		return loc_cinno;
	}

	public void setLoc_cinno(String loc_cinno) {
		this.loc_cinno = loc_cinno;
	}

	public String getLoc_web_site() {
		return loc_web_site;
	}

	public void setLoc_web_site(String loc_web_site) {
		this.loc_web_site = loc_web_site;
	}

	public String getLoc_emailid() {
		return loc_emailid;
	}

	public void setLoc_emailid(String loc_emailid) {
		this.loc_emailid = loc_emailid;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFr_num() {
		return fr_num;
	}

	public void setFr_num(String fr_num) {
		this.fr_num = fr_num;
	}

	public String getTo_num() {
		return to_num;
	}

	public void setTo_num(String to_num) {
		this.to_num = to_num;
	}

	public String getDptlst_no() {
		return dptlst_no;
	}

	public void setDptlst_no(String dptlst_no) {
		this.dptlst_no = dptlst_no;
	}

	public String getDptdrug_lic1() {
		return dptdrug_lic1;
	}

	public void setDptdrug_lic1(String dptdrug_lic1) {
		this.dptdrug_lic1 = dptdrug_lic1;
	}

	public String getDptdrug_lic2() {
		return dptdrug_lic2;
	}

	public void setDptdrug_lic2(String dptdrug_lic2) {
		this.dptdrug_lic2 = dptdrug_lic2;
	}

	public String getDptpan_no1() {
		return dptpan_no1;
	}

	public void setDptpan_no1(String dptpan_no1) {
		this.dptpan_no1 = dptpan_no1;
	}

	public String getCf_tel_no() {
		return cf_tel_no;
	}

	public void setCf_tel_no(String cf_tel_no) {
		this.cf_tel_no = cf_tel_no;
	}

	public String getCf_mobile_no() {
		return cf_mobile_no;
	}

	public void setCf_mobile_no(String cf_mobile_no) {
		this.cf_mobile_no = cf_mobile_no;
	}

	public String getCf_cinno() {
		return cf_cinno;
	}

	public void setCf_cinno(String cf_cinno) {
		this.cf_cinno = cf_cinno;
	}

	public String getCf_web_site() {
		return cf_web_site;
	}

	public void setCf_web_site(String cf_web_site) {
		this.cf_web_site = cf_web_site;
	}

	public String getArn_no() {
		return arn_no;
	}

	public void setArn_no(String arn_no) {
		this.arn_no = arn_no;
	}

	public String getGst_reg_no() {
		return gst_reg_no;
	}

	public void setGst_reg_no(String gst_reg_no) {
		this.gst_reg_no = gst_reg_no;
	}

	public String getLoc_statename() {
		return loc_statename;
	}

	public void setLoc_statename(String loc_statename) {
		this.loc_statename = loc_statename;
	}

	public String getLoc_statecode() {
		return loc_statecode;
	}

	public void setLoc_statecode(String loc_statecode) {
		this.loc_statecode = loc_statecode;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}

	public String getHgst_doc_type() {
		return hgst_doc_type;
	}

	public void setHgst_doc_type(String hgst_doc_type) {
		this.hgst_doc_type = hgst_doc_type;
	}

	public BigDecimal getHsgst_bill_amt() {
		return hsgst_bill_amt;
	}

	public void setHsgst_bill_amt(BigDecimal hsgst_bill_amt) {
		this.hsgst_bill_amt = hsgst_bill_amt;
	}

	public BigDecimal getHcgst_bill_amt() {
		return hcgst_bill_amt;
	}

	public void setHcgst_bill_amt(BigDecimal hcgst_bill_amt) {
		this.hcgst_bill_amt = hcgst_bill_amt;
	}

	public BigDecimal getHigst_bill_amt() {
		return higst_bill_amt;
	}

	public void setHigst_bill_amt(BigDecimal higst_bill_amt) {
		this.higst_bill_amt = higst_bill_amt;
	}

	public String getDgst_doc_type() {
		return dgst_doc_type;
	}

	public void setDgst_doc_type(String dgst_doc_type) {
		this.dgst_doc_type = dgst_doc_type;
	}

	public BigDecimal getSgst_rate() {
		return sgst_rate;
	}

	public void setSgst_rate(BigDecimal sgst_rate) {
		this.sgst_rate = sgst_rate;
	}

	public BigDecimal getSgst_bill_amt() {
		return sgst_bill_amt;
	}

	public void setSgst_bill_amt(BigDecimal sgst_bill_amt) {
		this.sgst_bill_amt = sgst_bill_amt;
	}

	public BigDecimal getCgst_rate() {
		return cgst_rate;
	}

	public void setCgst_rate(BigDecimal cgst_rate) {
		this.cgst_rate = cgst_rate;
	}

	public BigDecimal getCgst_bill_amt() {
		return cgst_bill_amt;
	}

	public void setCgst_bill_amt(BigDecimal cgst_bill_amt) {
		this.cgst_bill_amt = cgst_bill_amt;
	}

	public BigDecimal getIgst_rate() {
		return igst_rate;
	}

	public void setIgst_rate(BigDecimal igst_rate) {
		this.igst_rate = igst_rate;
	}

	public BigDecimal getIgst_bill_amt() {
		return igst_bill_amt;
	}

	public void setIgst_bill_amt(BigDecimal igst_bill_amt) {
		this.igst_bill_amt = igst_bill_amt;
	}


	public String getGst_doc_type() {
		return gst_doc_type;
	}

	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}

	public BigDecimal getRoundoff() {
		return roundoff;
	}

	public void setRoundoff(BigDecimal roundoff) {
		this.roundoff = roundoff;
	}

	public String getGst_description() {
		return gst_description;
	}

	public void setGst_description(String gst_description) {
		this.gst_description = gst_description;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getDspdtl_alloc_id() {
		return dspdtl_alloc_id;
	}

	public void setDspdtl_alloc_id(String dspdtl_alloc_id) {
		this.dspdtl_alloc_id = dspdtl_alloc_id;
	}

	public String getAlloc_remark() {
		return alloc_remark;
	}

	public void setAlloc_remark(String alloc_remark) {
		this.alloc_remark = alloc_remark;
	}

	public String getPri_no() {
		return pri_no;
	}

	public void setPri_no(String pri_no) {
		this.pri_no = pri_no;
	}

	public String getBilltoname1() {
		return billtoname1;
	}

	public void setBilltoname1(String billtoname1) {
		this.billtoname1 = billtoname1;
	}

	public String getBilltoname2() {
		return billtoname2;
	}

	public void setBilltoname2(String billtoname2) {
		this.billtoname2 = billtoname2;
	}

	public String getBillto_statename() {
		return billto_statename;
	}

	public void setBillto_statename(String billto_statename) {
		this.billto_statename = billto_statename;
	}

	public String getBillto_statecode() {
		return billto_statecode;
	}

	public void setBillto_statecode(String billto_statecode) {
		this.billto_statecode = billto_statecode;
	}

	public String getBillto_gst_reg_no() {
		return billto_gst_reg_no;
	}

	public void setBillto_gst_reg_no(String billto_gst_reg_no) {
		this.billto_gst_reg_no = billto_gst_reg_no;
	}

	
	
	
}
