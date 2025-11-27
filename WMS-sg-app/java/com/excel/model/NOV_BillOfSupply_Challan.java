package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;


@Entity
@Table(name = "PrePrintedDetailChallan_WITHOUTGST")
@NamedStoredProcedureQuery(name = "callPrePrintedDetailChallan_WITHOUTGST", procedureName = "PrePrintedDetailChallan_WITHOUTGST",
			parameters= {
					@StoredProcedureParameter(name="div_id" , mode=ParameterMode.IN,type=String.class),
					@StoredProcedureParameter(name="loc_id" , mode=ParameterMode.IN,type=String.class),
					@StoredProcedureParameter(name="frm_challan_no" , mode=ParameterMode.IN,type=String.class),
					@StoredProcedureParameter(name="to_challan_no" , mode=ParameterMode.IN,type=String.class),
					@StoredProcedureParameter(name = "dispatchtype" , mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name = "prod_type" , mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name = "rep_type" , mode = ParameterMode.IN, type = String.class)
					},resultClasses=NOV_BillOfSupply_Challan.class) 
public class NOV_BillOfSupply_Challan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -737856012326986833L;
	@Id
	@Column(name = "ROW")
	private Long row;
	@Column(name = "TEAM")
	private String div_name;
	@Column(name = "TEAM_CODE")
	private String team_code;
	@Column(name = "DIVISION")
	private String division;
	@Column(name = "DIV_CODE")
	private String div_code;
	@Column(name = "DSP_ID")
	private Long dsp_id;
	@Column(name = "DSP_CHALLAN_NO")
	public String doc_no;
	@Column(name = "DSP_CHALLAN_DT")
	public String date;
	@Column(name = "SMP_ERP_PROD_CD")
	public String code;
	@Column(name = "prod_name_pack")
	public String product_desc;
	@Column(name = "BATCH_NO")
	public String batch_no;
	@Column(name = "mfg_yr")
	public Long mfg_yr;
	@Column(name = "BATCH_EXPIRY_DT")
	public String expiry_date;
	@Column(name = "DSP_CASES")
	public BigDecimal cases;
	@Column(name = "DSPDTL_DISP_QTY")
	public BigDecimal total_qty;
	@Column(name = "DSPDTL_RATE")
	public BigDecimal rate;
	@Column(name = "disp_value")
	public BigDecimal value;
	@Column(name = "DSPFSTAFF_EMPLOYEENO")
	public String staff_emp_no;
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
	public String ship_doc_date;
	@Column(name = "DSP_LORRY_NO")
	public String dsp_lorry_no;
	@Column(name = "DSP_TRANSPORTER")
	public String dsp_transporter;
	@Column(name = "TOT_DSP_CASES")
	public BigDecimal tot_dsp_cases;
	@Column(name = "PERMIT_NO")
	private String permit_no;
	@Column(name = "WEIGH")
	private String weigh;
	@Column(name = "LIC1")
	private String lic1;
	@Column(name = "LIC2")
	private String lic2;
	@Column(name = "loc_cst_no")
	private String loc_cst_no;
	@Column(name = "loc_tin_no")
	private String loc_tin_no;
	@Column(name = "DSPFSTAFF_MOBILE")
	private String dspstaff_mobile;
	@Column(name = "DSPFSTAFF_EMAIL")
	private String dspstaff_email;
	@Column(name = "HQ_CODE")
	private String hq_code;
	@Column(name = "DPTPAN_NO")
	private String dptpan_no;
	@Column(name = "DSP_challan_msg")
	private String dsp_challan_msg;
	@Column(name = "smp_prod_type")
	private String product_type;
	@Column(name = "hq_name")
	private String hq_name;
	@Column(name = "state")
	private String state;
	@Column(name = "DPTDRUG_LIC1")
	private String dptdrug_lic1;
	@Column(name = "DPTDRUG_LIC2")
	private String dptdrug_lic2;
	@Column(name = "DPTCST_NO")
	private String dptcst_no;
	
	@Column(name = "DPTLST_NO")
	private String dptlst_no;
	
	@Column(name = "FR_NUM")
	private String fr_num;
	
	@Column(name = "TO_NUM")
	private String to_num;
	
	@Column(name = "LOC_TELEPHONE_NO")
	private String loc_telephone_no;
	
	@Column(name = "LOC_MOBILE_NO")
	private String loc_mobile_no;
	
	@Column(name = "LOC_CINNO")
	private String loc_cinno;
	
	@Column(name = "LOC_WEB_SITE")
	private String loc_web_site;
	
	@Column(name = "CF_TEL_NO")
	private String cf_tel_no;
	
	@Column(name = "CF_MOBILE_NO")
	private String cf_mobile_no;
	
	@Column(name = "CF_CINNO")
	private String cf_cinno;
	
	@Column(name = "CF_WEB_SITE")
	private String cf_web_site;
	
	@Column(name = "LOC_EMAILID")
	private String loc_emailid;
	
	@Column(name = "ARN_NO")
	private String arn_no;
	
	@Column(name = "GST_REG_NO")
	private String gst_no;
	@Column(name = "HSN_CODE")
	private String hsn_code;
	
	@Column(name = "LOC_STATENAME")
	private String loc_state;
	
	@Column(name = "LOC_STATECODE")
	private String loc_code;
	
	@Column(name = "FSTAFF_ID")
	private String fstaff_id;
	 
	@Column(name= "DSPDTL_ALLOC_ID")
	private String dspdtl_alloc_id ;
	
	@Column(name= "ALLOC_REMARK")
	private String alloc_remark;
	
	@Column(name= "VAT_NO")
	private String vat_no;
	@Column(name= "CST_NO")
	private String cst_no;
	
	public String getVat_no() {
	    return vat_no;
	}
	public void setVat_no(String vat_no) {
	    this.vat_no = vat_no;
	}
	public String getCst_no() {
	    return cst_no;
	}
	public void setCst_no(String cst_no) {
	    this.cst_no = cst_no;
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
	public String getFstaff_id() {
		return fstaff_id;
	}
	public void setFstaff_id(String fstaff_id) {
		this.fstaff_id = fstaff_id;
	}
	public String getLoc_emailid() {
		return loc_emailid;
	}
	public void setLoc_emailid(String loc_emailid) {
		this.loc_emailid = loc_emailid;
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
	public String getDptcst_no() {
		return dptcst_no;
	}
	public void setDptcst_no(String dptcst_no) {
		this.dptcst_no = dptcst_no;
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

	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
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

	public Long getMfg_yr() {
		return mfg_yr;
	}

	public void setMfg_yr(Long mfg_yr) {
		this.mfg_yr = mfg_yr;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	public BigDecimal getCases() {
		return cases;
	}

	public void setCases(BigDecimal cases) {
		this.cases = cases;
	}

	public BigDecimal getTotal_qty() {
		return total_qty;
	}

	public void setTotal_qty(BigDecimal total_qty) {
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

	public String getStaff_emp_no() {
		return staff_emp_no;
	}

	public void setStaff_emp_no(String staff_emp_no) {
		this.staff_emp_no = staff_emp_no;
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

	public String getWeigh() {
		return weigh;
	}

	public void setWeigh(String weigh) {
		this.weigh = weigh;
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

	public String getDptpan_no() {
		return dptpan_no;
	}

	public void setDptpan_no(String dptpan_no) {
		this.dptpan_no = dptpan_no;
	}

	public String getDsp_challan_msg() {
		return dsp_challan_msg;
	}

	public void setDsp_challan_msg(String dsp_challan_msg) {
		this.dsp_challan_msg = dsp_challan_msg;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getArn_no() {
		return arn_no;
	}
	public void setArn_no(String arn_no) {
		this.arn_no = arn_no;
	}
	public String getGst_no() {
		return gst_no;
	}
	public void setGst_no(String gst_no) {
		this.gst_no = gst_no;
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
	public String getHsn_code() {
		return hsn_code;
	}
	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}

}
