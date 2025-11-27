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

@Entity
@Table(name = "ViewPrePrintedSummaryChallan_GST_EInvoiceStockist")

@NamedStoredProcedureQuery(name = "callPrePrintedSummaryChallan_GST_EINV_Stk",
procedureName = "PrePrintedSummaryChallan_GST_QRCODE_STOCKIST",
parameters= {
		@StoredProcedureParameter(name = "div_id" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "loc_id" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "frm_challan_no" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "to_challan_no" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "prod_type" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "rep_type" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "fin_year_flag" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "fin_year_id" ,mode = ParameterMode.IN,type=String.class)
},resultClasses=ViewPrePrintedSummaryChallan_GST_EInvoiceStockist.class)

public class ViewPrePrintedSummaryChallan_GST_EInvoiceStockist {

	private static final long serialVersionUID = -737856012326986833L;
	@Id
	@Column(name = "ROW")
	private Long row;
	@Column(name = "TEAM")
	private String div_name;

	@Column(name = "DIVISION")
	private String division;
	@Column(name = "DIV_CODE")
	private String div_code;
	@Column(name = "DSP_ID")
	private String dsp_id;
	@Column(name = "DSP_CHALLAN_NO")
	public String doc_no;
	@Column(name = "DSP_CHALLAN_DT")
	public String date;
	@Column(name = "DSPFSTAFF_DISPLAYNAME")
	public String staff_name;
	@Column(name = "DSPFSTAFFADDR1")
	public String staff_addr1;
	@Column(name = "DSPFSTAFFADDR2")
	public String staff_addr2;
	@Column(name = "DSPFSTAFFADDR3")
	public String staff_addr3;
	@Column(name = "DSPFSTAFFADDR4")
	public String staff_addr4;
	@Column(name = "DPTDESTINATION")
	public String dptdestination;
	@Column(name = "DPTTEL_NO")
	public String dpttel_no;

	@Column(name = "cfa")
	public String cfa_name;
	@Column(name = "locadd1")
	public String loc_add1;
	@Column(name = "locadd2")
	public String loc_add2;
	@Column(name = "locadd3")
	public String loc_add3;
	@Column(name = "locadd4")
	public String loc_add4;

	@Column(name = "ship_doc_no")
	public String ship_doc_no;
	@Column(name = "ship_doc_date")
	public Date ship_doc_date;
	@Column(name = "DSP_LORRY_NO")
	public String dsp_lorry_no;
	@Column(name = "DSP_TRANSPORTER")
	public String dsp_transporter;
	@Column(name = "DSPDTL_PROD_ID")
	public String dsp_prod_id;

	@Column(name = "SMP_ERP_PROD_CD")
	public String code;
	@Column(name = "prod_name_pack")
	public String product_desc;
	@Column(name = "BATCH_NO")
	public String batch_no;
	@Column(name = "mfg_yr")
	public String mfg_yr;
	@Column(name = "BATCH_EXPIRY_DT")
	public String expiry_date;
	@Column(name = "DSP_CASES")
	public String cases;
	@Column(name = "DSPDTL_DISP_QTY")
	public String total_qty;
	@Column(name = "DSPDTL_RATE")
	public BigDecimal rate;
	@Column(name = "disp_value")
	public BigDecimal value;

	@Column(name = "TOT_DSP_CASES")
	public String tot_dsp_cases;
	@Column(name = "PERMIT_NO")
	private String permit_no;
	@Column(name = "WEIGH")
	private BigDecimal weigh;
	@Column(name = "DPTDRUG_LIC1")
	private String dptdrug_lic1;
	@Column(name = "DPTDRUG_LIC2")
	private String dptdrug_lic2;
	@Column(name = "DPTPAN_NO")
	private String dptpan_no;
	@Column(name = "DPTCST_NO")
	private String dptcst_no;
	@Column(name = "LIC1")
	private String lic1;
	@Column(name = "LIC2")
	private String lic2;

	@Column(name = "loc_cst_no")
	private String loc_cst_no;
	@Column(name = "loc_tin_no")
	private String loc_tin_no;

	@Column(name = "fr_num")
	private String fr_num;

	@Column(name = "to_num")
	private String to_num;

	@Column(name = "DPTLST_NO")
	private String dptlst_no;

	@Column(name = "smp_prod_type")
	private String product_type;
	@Column(name = "STATE")
	private String state;
	@Column(name = "loc_telephone_no")
	private String loc_telephone_no;

	@Column(name = "loc_mobile_no")
	private String loc_mobile_no;

	@Column(name = "loc_cinno")
	private String loc_cinno;

	@Column(name = "loc_web_site")
	private String loc_web_site;
	@Column(name = "loc_emailid")
	private String loc_emailid;
	@Column(name = "cf_tel_no")
	private String cf_tel_no;

	@Column(name = "cf_mobile_no")
	private String cf_mobile_no;

	@Column(name = "cf_cinno")
	private String cf_cinno;

	@Column(name = "cf_web_site")
	private String cf_web_site;

	@Column(name = "DSPFSTAFF_MOBILE")
	private String dspstaff_mobile;
	@Column(name = "DSPFSTAFF_EMAIL")
	private String dspstaff_email;

	@Column(name = "DSP_challan_msg")
	private String dsp_challan_msg;
	@Column(name = "DPTPAN_NO1")
	private String dptpan_no1;

	@Column(name = "HQ_NAME")
	private String hq_name;
	@Column(name = "TEAM_CODE")
	private String team_code;

	@Column(name = "HQ_CODE")
	private String hq_code;

	@Column(name = "DSPFSTAFF_EMPLOYEENO")
	public String staff_emp_no;

	@Column(name = "FSTAFF_ID")
	private String fstaff_id;

	@Column(name = "HSGST_BILL_AMT")
	private BigDecimal hsgst_bill_amt;
	@Column(name = "HCGST_BILL_AMT")
	private BigDecimal hcgst_bill_amt;
	@Column(name = "HIGST_BILL_AMT")
	private BigDecimal higst_bill_amt;

	@Column(name = "HGST_DOC_TYPE")
	private String hgst_doc_type;

	@Column(name = "SGST_RATE")
	private BigDecimal sgst_rate;
	@Column(name = "SGST_BILL_AMT")
	private BigDecimal sgst_bill_amt;
	@Column(name = "CGST_RATE")
	private BigDecimal cgst_rate;
	@Column(name = "CGST_BILL_AMT")
	private BigDecimal cgst_bill_amt;

	@Column(name = "IGST_RATE")
	private BigDecimal igst_rate;
	@Column(name = "IGST_BILL_AMT")
	private BigDecimal igst_bill_amt;

	@Column(name = "GST_DOC_TYPE")
	private String gst_doc_type;

	@Column(name = "ROUNDOFF")
	private BigDecimal roundoff;

	@Column(name = "CFSTATE_CODE")
	private String cfstate_code;

	@Column(name = "LGST_REG_NO")
	private String lgst_reg_no;

	@Column(name = "CFGST_REG_NO")
	private String cfgst_reg_no;

	@Column(name = "LOC_STATE")
	private String loc_state;

	@Column(name = "LOC_STATE_CODE")
	private String loc_code;

	@Column(name = "HSN_CODE")
	private String hsn_code;

	@Column(name = "REPORT")
	private String report;

	@Column(name = "DSPDTL_ALLOC_ID")
	private String dspdtl_alloc_id;

	@Column(name = "ALLOC_REMARK")
	private String alloc_remark;

	@Column(name = "GST_DESCRIPTION") // needs to be uncommented for VETO
	private String gst_description;

	@Column(name = "TRN_IRN_NUMBER")
	private String trn_irn_number;

	@Column(name = "TRN_QR_CODE")
	private String trn_qr_code;

	@Column(name = "TRN_EWAYBILLNO")
	private String trn_ewaybill;

	@Column(name = "E_Inv_Gen_Dt")
	private String e_inv_gen_dt;

	@Column(name = "E_WayBill_Valid_Upto")
	private String e_waybill_valid_upto;

	@Column(name = "Transporter_GSTIN")
	private String transporter_gstin;

	@Column(name = "LR_No")
	private String lr_no;

	@Column(name = "LR_DATE")
	private String lr_date;

	@Column(name = "Sending_Loc_City")
	private String sending_loc_city;

	@Column(name = "SRT_NUMBER")
	private String srt_number;

	@Column(name = "SRT_DATE")
	private String srt_date;

	@Column(name = "TRD_SCHEME_CODE")
	private String trd_scheme_code;

	@Column(name = "TRD_SCHEME_NAME")
	private String trd_scheme_name;

	// added for stockists
	@Column(name = "DSP_BILLTO_NAME")
	private String dsp_billto_name;
	@Column(name = "DSPADDR1_BILLTO")
	private String dspaddr1_billto;
	@Column(name = "DSPADDR2_BILLTO")
	private String dspaddr2_billto;
	@Column(name = "DSPADDR3_BILLTO")
	private String dspaddr3_billto;
	@Column(name = "DSPADDR4_BILLTO")
	private String dspaddr4_billto;
	@Column(name = "DSPDESTINATION_BILLTO")
	private String dspdestination_billto;
	@Column(name = "DSPPAN_NO_BILLTO")
	private String dsppan_no_billto;
	@Column(name = "DSPTEL_NO_BILLTO")
	private String dsptel_no_billto;
	@Column(name = "DSPEMAIL_BILLTO")
	private String dspemail_billto;
	@Column(name = "DSPMOBILE_BILLTO")
	private String dspmobile_billto;
	@Column(name = "GSTIN_NO_BILLTO")
	private String gstin_no_billto;

	//changes for stockist app print
	@Column(name = "GSTIN_NO_SHIPTO")
	private String gstin_no_shipto;
	@Column(name = "HCP_UNIQUE_ID")
	private String hcp_unique_id;
	@Column(name = "HCP_UNIQUE_ID_SHIPTO")
	private String hcp_unique_id_shipto;
	
	@Column(name = "EMAIL_ID_SHIPTO")
	private String email_id_shipto;
	@Column(name = "PHONE_SHIPTO")
	private String phone_shipto;
	@Column(name = "EMAIL_ID_BILLTO")
	private String email_id_billto;
	@Column(name = "PHONE_BILLTO")
	private String phone_billto;
	
	@Column(name = "SUBCOMP_NM")
	private String subcomp_nm;
	
	

	public String getSubcomp_nm() {
		return subcomp_nm;
	}

	public void setSubcomp_nm(String subcomp_nm) {
		this.subcomp_nm = subcomp_nm;
	}

	public String getEmail_id_shipto() {
		return email_id_shipto;
	}

	public void setEmail_id_shipto(String email_id_shipto) {
		this.email_id_shipto = email_id_shipto;
	}

	public String getPhone_shipto() {
		return phone_shipto;
	}

	public void setPhone_shipto(String phone_shipto) {
		this.phone_shipto = phone_shipto;
	}

	public String getEmail_id_billto() {
		return email_id_billto;
	}

	public void setEmail_id_billto(String email_id_billto) {
		this.email_id_billto = email_id_billto;
	}

	public String getPhone_billto() {
		return phone_billto;
	}

	public void setPhone_billto(String phone_billto) {
		this.phone_billto = phone_billto;
	}

	public String getGstin_no_shipto() {
		return gstin_no_shipto;
	}

	public void setGstin_no_shipto(String gstin_no_shipto) {
		this.gstin_no_shipto = gstin_no_shipto;
	}

	public String getHcp_unique_id() {
		return hcp_unique_id;
	}

	public void setHcp_unique_id(String hcp_unique_id) {
		this.hcp_unique_id = hcp_unique_id;
	}

	public String getHcp_unique_id_shipto() {
		return hcp_unique_id_shipto;
	}

	public void setHcp_unique_id_shipto(String hcp_unique_id_shipto) {
		this.hcp_unique_id_shipto = hcp_unique_id_shipto;
	}

	public String getDsp_billto_name() {
		return dsp_billto_name;
	}

	public void setDsp_billto_name(String dsp_billto_name) {
		this.dsp_billto_name = dsp_billto_name;
	}

	public String getDspaddr1_billto() {
		return dspaddr1_billto;
	}

	public void setDspaddr1_billto(String dspaddr1_billto) {
		this.dspaddr1_billto = dspaddr1_billto;
	}

	public String getDspaddr2_billto() {
		return dspaddr2_billto;
	}

	public void setDspaddr2_billto(String dspaddr2_billto) {
		this.dspaddr2_billto = dspaddr2_billto;
	}

	public String getDspaddr3_billto() {
		return dspaddr3_billto;
	}

	public void setDspaddr3_billto(String dspaddr3_billto) {
		this.dspaddr3_billto = dspaddr3_billto;
	}

	public String getDspaddr4_billto() {
		return dspaddr4_billto;
	}

	public void setDspaddr4_billto(String dspaddr4_billto) {
		this.dspaddr4_billto = dspaddr4_billto;
	}

	public String getDspdestination_billto() {
		return dspdestination_billto;
	}

	public void setDspdestination_billto(String dspdestination_billto) {
		this.dspdestination_billto = dspdestination_billto;
	}

	public String getDsppan_no_billto() {
		return dsppan_no_billto;
	}

	public void setDsppan_no_billto(String dsppan_no_billto) {
		this.dsppan_no_billto = dsppan_no_billto;
	}

	public String getDsptel_no_billto() {
		return dsptel_no_billto;
	}

	public void setDsptel_no_billto(String dsptel_no_billto) {
		this.dsptel_no_billto = dsptel_no_billto;
	}

	public String getDspemail_billto() {
		return dspemail_billto;
	}

	public void setDspemail_billto(String dspemail_billto) {
		this.dspemail_billto = dspemail_billto;
	}

	public String getDspmobile_billto() {
		return dspmobile_billto;
	}

	public void setDspmobile_billto(String dspmobile_billto) {
		this.dspmobile_billto = dspmobile_billto;
	}

	public String getGstin_no_billto() {
		return gstin_no_billto;
	}

	public void setGstin_no_billto(String gstin_no_billto) {
		this.gstin_no_billto = gstin_no_billto;
	}

	public String getTrd_scheme_code() {
		return trd_scheme_code;
	}

	public void setTrd_scheme_code(String trd_scheme_code) {
		this.trd_scheme_code = trd_scheme_code;
	}

	public String getTrd_scheme_name() {
		return trd_scheme_name;
	}

	public void setTrd_scheme_name(String trd_scheme_name) {
		this.trd_scheme_name = trd_scheme_name;
	}

	public String getSrt_number() {
		return srt_number;
	}

	public void setSrt_number(String srt_number) {
		this.srt_number = srt_number;
	}

	public String getSrt_date() {
		return srt_date;
	}

	public void setSrt_date(String srt_date) {
		this.srt_date = srt_date;
	}

	public String getE_inv_gen_dt() {
		return e_inv_gen_dt;
	}

	public void setE_inv_gen_dt(String e_inv_gen_dt) {
		this.e_inv_gen_dt = e_inv_gen_dt;
	}

	public String getE_waybill_valid_upto() {
		return e_waybill_valid_upto;
	}

	public void setE_waybill_valid_upto(String e_waybill_valid_upto) {
		this.e_waybill_valid_upto = e_waybill_valid_upto;
	}

	public String getTransporter_gstin() {
		return transporter_gstin;
	}

	public void setTransporter_gstin(String transporter_gstin) {
		this.transporter_gstin = transporter_gstin;
	}

	public String getLr_no() {
		return lr_no;
	}

	public void setLr_no(String lr_no) {
		this.lr_no = lr_no;
	}

	public String getLr_date() {
		return lr_date;
	}

	public void setLr_date(String lr_date) {
		this.lr_date = lr_date;
	}

	public String getSending_loc_city() {
		return sending_loc_city;
	}

	public void setSending_loc_city(String sending_loc_city) {
		this.sending_loc_city = sending_loc_city;
	}

	public String getGst_description() {
		return gst_description;
	}

	public void setGst_description(String gst_description) {
		this.gst_description = gst_description;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getDiv_name() {
		return div_name;
	}

	public void setDiv_name(String div_name) {
		this.div_name = div_name;
	}

	public String getDivision() {
		return division;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
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

	public String getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(String dsp_id) {
		this.dsp_id = dsp_id;
	}

	public String getDoc_no() {
		return doc_no;
	}

	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getStaff_addr1() {
		return staff_addr1;
	}

	public void setStaff_addr1(String staff_addr1) {
		this.staff_addr1 = staff_addr1;
	}

	public String getStaff_addr2() {
		return staff_addr2;
	}

	public void setStaff_addr2(String staff_addr2) {
		this.staff_addr2 = staff_addr2;
	}

	public String getStaff_addr3() {
		return staff_addr3;
	}

	public void setStaff_addr3(String staff_addr3) {
		this.staff_addr3 = staff_addr3;
	}

	public String getStaff_addr4() {
		return staff_addr4;
	}

	public void setStaff_addr4(String staff_addr4) {
		this.staff_addr4 = staff_addr4;
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

	public String getCfa_name() {
		return cfa_name;
	}

	public void setCfa_name(String cfa_name) {
		this.cfa_name = cfa_name;
	}

	public String getLoc_add1() {
		return loc_add1;
	}

	public void setLoc_add1(String loc_add1) {
		this.loc_add1 = loc_add1;
	}

	public String getLoc_add2() {
		return loc_add2;
	}

	public void setLoc_add2(String loc_add2) {
		this.loc_add2 = loc_add2;
	}

	public String getLoc_add3() {
		return loc_add3;
	}

	public void setLoc_add3(String loc_add3) {
		this.loc_add3 = loc_add3;
	}

	public String getLoc_add4() {
		return loc_add4;
	}

	public void setLoc_add4(String loc_add4) {
		this.loc_add4 = loc_add4;
	}

	public String getShip_doc_no() {
		return ship_doc_no;
	}

	public void setShip_doc_no(String ship_doc_no) {
		this.ship_doc_no = ship_doc_no;
	}

	public Date getShip_doc_date() {
		return ship_doc_date;
	}

	public void setShip_doc_date(Date ship_doc_date) {
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

	public String getDsp_prod_id() {
		return dsp_prod_id;
	}

	public void setDsp_prod_id(String dsp_prod_id) {
		this.dsp_prod_id = dsp_prod_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProduct_desc() {
		return product_desc;
	}

	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
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

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getCases() {
		return cases;
	}

	public void setCases(String cases) {
		this.cases = cases;
	}

	public String getTotal_qty() {
		return total_qty;
	}

	public void setTotal_qty(String total_qty) {
		this.total_qty = total_qty;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getTot_dsp_cases() {
		return tot_dsp_cases;
	}

	public void setTot_dsp_cases(String tot_dsp_cases) {
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

	public String getDptpan_no() {
		return dptpan_no;
	}

	public void setDptpan_no(String dptpan_no) {
		this.dptpan_no = dptpan_no;
	}

	public String getDptcst_no() {
		return dptcst_no;
	}

	public void setDptcst_no(String dptcst_no) {
		this.dptcst_no = dptcst_no;
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

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getDspstaff_mobile() {
		return dspstaff_mobile;
	}

	public void setDspstaff_mobile(String dspstaff_mobile) {
		this.dspstaff_mobile = dspstaff_mobile;
	}

	public String getDspstaff_email() {
		return dspstaff_email;
	}

	public void setDspstaff_email(String dspstaff_email) {
		this.dspstaff_email = dspstaff_email;
	}

	public String getDsp_challan_msg() {
		return dsp_challan_msg;
	}

	public void setDsp_challan_msg(String dsp_challan_msg) {
		this.dsp_challan_msg = dsp_challan_msg;
	}

	public String getDptpan_no1() {
		return dptpan_no1;
	}

	public void setDptpan_no1(String dptpan_no1) {
		this.dptpan_no1 = dptpan_no1;
	}

	public String getHq_name() {
		return hq_name;
	}

	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getHq_code() {
		return hq_code;
	}

	public void setHq_code(String hq_code) {
		this.hq_code = hq_code;
	}

	public String getStaff_emp_no() {
		return staff_emp_no;
	}

	public void setStaff_emp_no(String staff_emp_no) {
		this.staff_emp_no = staff_emp_no;
	}

	public String getFstaff_id() {
		return fstaff_id;
	}

	public void setFstaff_id(String fstaff_id) {
		this.fstaff_id = fstaff_id;
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

	public String getHgst_doc_type() {
		return hgst_doc_type;
	}

	public void setHgst_doc_type(String hgst_doc_type) {
		this.hgst_doc_type = hgst_doc_type;
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

	public String getCfstate_code() {
		return cfstate_code;
	}

	public void setCfstate_code(String cfstate_code) {
		this.cfstate_code = cfstate_code;
	}

	public String getLgst_reg_no() {
		return lgst_reg_no;
	}

	public void setLgst_reg_no(String lgst_reg_no) {
		this.lgst_reg_no = lgst_reg_no;
	}

	public String getCfgst_reg_no() {
		return cfgst_reg_no;
	}

	public void setCfgst_reg_no(String cfgst_reg_no) {
		this.cfgst_reg_no = cfgst_reg_no;
	}

	public String getLoc_state() {
		return loc_state;
	}

	public void setLoc_state(String loc_state) {
		this.loc_state = loc_state;
	}

	public String getLoc_code() {
		return loc_code;
	}

	public void setLoc_code(String loc_code) {
		this.loc_code = loc_code;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTrn_irn_number() {
		return trn_irn_number;
	}

	public void setTrn_irn_number(String trn_irn_number) {
		this.trn_irn_number = trn_irn_number;
	}

	public String getTrn_qr_code() {
		return trn_qr_code;
	}

	public void setTrn_qr_code(String trn_qr_code) {
		this.trn_qr_code = trn_qr_code;
	}

	public String getTrn_ewaybill() {
		return trn_ewaybill;
	}

	public void setTrn_ewaybill(String trn_ewaybill) {
		this.trn_ewaybill = trn_ewaybill;
	}

}
