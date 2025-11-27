package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "ViewGrnGSTVoucherPrinting")
@NamedStoredProcedureQuery(name = "callGrnVoucherPrinting_java_GST", procedureName = "GrnVoucherPrinting_java_GST",
parameters= {
		@StoredProcedureParameter(name="loc_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="supp_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="frm_Grn_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="to_Grn_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "fin_year_flag" , mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "fin_year_id" , mode = ParameterMode.IN, type = String.class)
		},resultClasses=ViewGrnGSTVoucherPrinting.class) 
public class ViewGrnGSTVoucherPrinting implements Serializable {

	private static final long serialVersionUID = 5643939516828216717L;
	
	@Id
	@Column(name="Row")
	private Long row;
	
	@Column(name="LOC_ID")
	private Long loc_id;
	
	@Column(name="LOC_NM")
	private String loc_nm;
	
	@Column(name="LOC_ADD1")
	private String loc_add1;
	
	@Column(name="LOC_ADD2")
	private String loc_add2;
	
	@Column(name="LOC_ADD3")
	private String loc_add3;
	
	@Column(name="LOC_ADD4")
	private String loc_add4;
	
	@Column(name="CITY_NAME")
	private String city_name;
	
	@Column(name="GRN_NO")
	private String grn_no;
	
	@Column(name="GRN_DT")
	private String grn_dt;
	
	@Column(name="GRN_TYPE")
	private String grn_type;
	
	@Column(name="sup_nm")
	private String sup_nm;
	
	@Column(name="GRN_TRANSFER_NO")
	private String grn_transfer_no;
	
	@Column(name="GRN_TRANSFER_DT")
	private String grn_transfer_dt;
	
	@Column(name="TRANSPORTER")
	private String transporter;
	
	@Column(name="GRN_LR_NO")
	private String grn_lr_no;
	
	@Column(name="GRN_LR_DT")
	private String grn_lr_dt;
	
	@Column(name="PO_NUM")
	private String po_num;
	
	@Column(name="PO_DATE")
	private String po_date;
	
	@Column(name="DIVISION")
	private String division;
	
	@Column(name="SMP_PROD_CD")
	private String smp_prod_cd;
	
	@Column(name="SMP_PROD_NAME")
	private String smp_prod_name;
	
	@Column(name="BATCH_NO")
	private String batch_no;

	@Column(name="BATCH_MFG_DT")
	private String batch_mfg_dt;
	
	@Column(name="BATCH_EXPIRY_DT")
	private String batch_expiry_dt;
	
	@Column(name="PACKING")
	private String packing;
	
	@Column(name="SMP_TYPE")
	private String smp_type;
	
	@Column(name="smp_prod_type")
	private String smp_prod_type;
	
	@Column(name="RECVD_QTY")
	private BigDecimal recvd_qty;
	
	@Column(name="TRANSFER_RATE")
	private BigDecimal transfer_rate;
	
	@Column(name="GRN_VALUE")
	private BigDecimal grn_value;
	
	@Column(name="GRND_NOOFBOXES")
	private Long grnd_noofboxes;
	
	@Column(name="GRN_TOTAL_VALUE")
	private BigDecimal grn_total_value;
	
	@Column(name="BIN_NUMBER")
	private String bin_number;
	
	@Column(name="BATCH_NARRATION")
	private String batch_narration;
	
	@Column(name="HSGST_BILL_AMT")
	private BigDecimal hsgst_bill_amt;
	
	@Column(name="HCGST_BILL_AMT")
	private BigDecimal hcgst_bill_amt;
	
	@Column(name="HIGST_BILL_AMT")
	private BigDecimal higst_bill_amt;
	
	@Column(name="HGST_REVERSE_CHG")
	private String hgst_reverse_chg;
	
	@Column(name="HGST_DOC_TYPE")
	private String hgst_doc_type;
	
	@Column(name="Htext1")
	private String htext1;
	
	@Column(name="Htext2")
	private String htext2;
	
	@Column(name="Hvalue1")
	private BigDecimal hvalue1;
	
	@Column(name="Hvalue2")
	private BigDecimal hvalue2;
	
	@Column(name="SGST_RATE")
	private BigDecimal sgst_rate;
	
	@Column(name="SGST_BILL_AMT")
	private BigDecimal sgst_bill_amt;
	
	@Column(name="CGST_RATE")
	private BigDecimal cgst_rate;
	
	@Column(name="CGST_BILL_AMT")
	private BigDecimal cgst_bill_amt;
	
	@Column(name="IGST_RATE")
	private BigDecimal igst_rate;
	
	@Column(name="IGST_BILL_AMT")
	private BigDecimal igst_bill_amt;
	
	@Column(name="GST_REVERSE_CHG")
	private String gst_reverse_chg;
	
	@Column(name="GST_DOC_TYPE")
	private String gst_doc_type;
	
	@Column(name="text1")
	private String text1;
	
	@Column(name="text2")
	private String text2;
	
	@Column(name="value1")
	private BigDecimal value1;
	
	@Column(name="value2")
	private BigDecimal value2;
	
	@Column(name="LGST_REG_NO")
	private String lgst_reg_no;
	
	@Column(name="LState_name")
	private String lstate_name;
	
	@Column(name="LState_code")
	private String lstate_code;
	
	@Column(name="SGST_REG_NO")
	private String sgst_reg_no;
	
	@Column(name="SState_name")
	private String sstate_name;
	
	@Column(name="SState_code")
	private String sstate_code;
	
	@Column(name="HSN_CODE")
	private String hsn_code;
	
	@Column(name="HSUP_ADDRESS1")
	private String hsup_address1;
	
	@Column(name="HSUP_ADDRESS2")
	private String hsup_address2;
	
	@Column(name="HSUP_ADDRESS3")
	private String hsup_address3;
	
	@Column(name="ROUNDOFF")
	private BigDecimal roundoff;
	
	@Column(name="GRND_PUR_RATE")
	private BigDecimal grnd_pur_rate;
	
	@Column(name="GRND_VALUE_PUR")
	private BigDecimal grnd_value_pur;
	
	@Column(name="GRN_HEADER_TOTAL_VALUE_PUR")
	private BigDecimal grn_header_total_value_pur;
	
	@Column(name="ROUNDOFF_PR")
	private BigDecimal roundoff_pr;
	
	@Column(name="GRN_REF_NO ")
	private String grn_ref_no;
	
	@Column(name="REMARKS")
	private String remarks;
	
	public String getGrn_ref_no() {
		return grn_ref_no;
	}

	public void setGrn_ref_no(String grn_ref_no) {
		this.grn_ref_no = grn_ref_no;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getRoundoff_pr() {
		return roundoff_pr;
	}

	public void setRoundoff_pr(BigDecimal roundoff_pr) {
		this.roundoff_pr = roundoff_pr;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
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

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getGrn_no() {
		return grn_no;
	}

	public void setGrn_no(String grn_no) {
		this.grn_no = grn_no;
	}

	public String getGrn_dt() {
		return grn_dt;
	}

	public void setGrn_dt(String grn_dt) {
		this.grn_dt = grn_dt;
	}

	public String getGrn_type() {
		return grn_type;
	}

	public void setGrn_type(String grn_type) {
		this.grn_type = grn_type;
	}

	public String getSup_nm() {
		return sup_nm;
	}

	public void setSup_nm(String sup_nm) {
		this.sup_nm = sup_nm;
	}

	public String getGrn_transfer_no() {
		return grn_transfer_no;
	}

	public void setGrn_transfer_no(String grn_transfer_no) {
		this.grn_transfer_no = grn_transfer_no;
	}

	public String getGrn_transfer_dt() {
		return grn_transfer_dt;
	}

	public void setGrn_transfer_dt(String grn_transfer_dt) {
		this.grn_transfer_dt = grn_transfer_dt;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getGrn_lr_no() {
		return grn_lr_no;
	}

	public void setGrn_lr_no(String grn_lr_no) {
		this.grn_lr_no = grn_lr_no;
	}

	public String getGrn_lr_dt() {
		return grn_lr_dt;
	}

	public void setGrn_lr_dt(String grn_lr_dt) {
		this.grn_lr_dt = grn_lr_dt;
	}

	public String getPo_num() {
		return po_num;
	}

	public void setPo_num(String po_num) {
		this.po_num = po_num;
	}

	public String getPo_date() {
		return po_date;
	}

	public void setPo_date(String po_date) {
		this.po_date = po_date;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_mfg_dt() {
		return batch_mfg_dt;
	}

	public void setBatch_mfg_dt(String batch_mfg_dt) {
		this.batch_mfg_dt = batch_mfg_dt;
	}

	public String getBatch_expiry_dt() {
		return batch_expiry_dt;
	}

	public void setBatch_expiry_dt(String batch_expiry_dt) {
		this.batch_expiry_dt = batch_expiry_dt;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getSmp_type() {
		return smp_type;
	}

	public void setSmp_type(String smp_type) {
		this.smp_type = smp_type;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public BigDecimal getRecvd_qty() {
		return recvd_qty;
	}

	public void setRecvd_qty(BigDecimal recvd_qty) {
		this.recvd_qty = recvd_qty;
	}

	public BigDecimal getTransfer_rate() {
		return transfer_rate;
	}

	public void setTransfer_rate(BigDecimal transfer_rate) {
		this.transfer_rate = transfer_rate;
	}

	public BigDecimal getGrn_value() {
		return grn_value;
	}

	public void setGrn_value(BigDecimal grn_value) {
		this.grn_value = grn_value;
	}

	public Long getGrnd_noofboxes() {
		return grnd_noofboxes;
	}

	public void setGrnd_noofboxes(Long grnd_noofboxes) {
		this.grnd_noofboxes = grnd_noofboxes;
	}

	public BigDecimal getGrn_total_value() {
		return grn_total_value;
	}

	public void setGrn_total_value(BigDecimal grn_total_value) {
		this.grn_total_value = grn_total_value;
	}

	public String getBin_number() {
		return bin_number;
	}

	public void setBin_number(String bin_number) {
		this.bin_number = bin_number;
	}

	public String getBatch_narration() {
		return batch_narration;
	}

	public void setBatch_narration(String batch_narration) {
		this.batch_narration = batch_narration;
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

	public String getHgst_reverse_chg() {
		return hgst_reverse_chg;
	}

	public void setHgst_reverse_chg(String hgst_reverse_chg) {
		this.hgst_reverse_chg = hgst_reverse_chg;
	}

	public String getHgst_doc_type() {
		return hgst_doc_type;
	}

	public void setHgst_doc_type(String hgst_doc_type) {
		this.hgst_doc_type = hgst_doc_type;
	}

	public String getHtext1() {
		return htext1;
	}

	public void setHtext1(String htext1) {
		this.htext1 = htext1;
	}

	public String getHtext2() {
		return htext2;
	}

	public void setHtext2(String htext2) {
		this.htext2 = htext2;
	}

	public BigDecimal getHvalue1() {
		return hvalue1;
	}

	public void setHvalue1(BigDecimal hvalue1) {
		this.hvalue1 = hvalue1;
	}

	public BigDecimal getHvalue2() {
		return hvalue2;
	}

	public void setHvalue2(BigDecimal hvalue2) {
		this.hvalue2 = hvalue2;
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

	public String getGst_reverse_chg() {
		return gst_reverse_chg;
	}

	public void setGst_reverse_chg(String gst_reverse_chg) {
		this.gst_reverse_chg = gst_reverse_chg;
	}

	public String getGst_doc_type() {
		return gst_doc_type;
	}

	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public BigDecimal getValue1() {
		return value1;
	}

	public void setValue1(BigDecimal value1) {
		this.value1 = value1;
	}

	public BigDecimal getValue2() {
		return value2;
	}

	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
	}

	public String getLgst_reg_no() {
		return lgst_reg_no;
	}

	public void setLgst_reg_no(String lgst_reg_no) {
		this.lgst_reg_no = lgst_reg_no;
	}

	public String getLstate_name() {
		return lstate_name;
	}

	public void setLstate_name(String lstate_name) {
		this.lstate_name = lstate_name;
	}

	public String getLstate_code() {
		return lstate_code;
	}

	public void setLstate_code(String lstate_code) {
		this.lstate_code = lstate_code;
	}

	public String getSgst_reg_no() {
		return sgst_reg_no;
	}

	public void setSgst_reg_no(String sgst_reg_no) {
		this.sgst_reg_no = sgst_reg_no;
	}

	public String getSstate_name() {
		return sstate_name;
	}

	public void setSstate_name(String sstate_name) {
		this.sstate_name = sstate_name;
	}

	public String getSstate_code() {
		return sstate_code;
	}

	public void setSstate_code(String sstate_code) {
		this.sstate_code = sstate_code;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}

	public String getHsup_address1() {
		return hsup_address1;
	}

	public void setHsup_address1(String hsup_address1) {
		this.hsup_address1 = hsup_address1;
	}

	public String getHsup_address2() {
		return hsup_address2;
	}

	public void setHsup_address2(String hsup_address2) {
		this.hsup_address2 = hsup_address2;
	}

	public String getHsup_address3() {
		return hsup_address3;
	}

	public void setHsup_address3(String hsup_address3) {
		this.hsup_address3 = hsup_address3;
	}

	public BigDecimal getRoundoff() {
		return roundoff;
	}

	public void setRoundoff(BigDecimal roundoff) {
		this.roundoff = roundoff;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getGrnd_pur_rate() {
		return grnd_pur_rate;
	}

	public void setGrnd_pur_rate(BigDecimal grnd_pur_rate) {
		this.grnd_pur_rate = grnd_pur_rate;
	}

	public BigDecimal getGrnd_value_pur() {
		return grnd_value_pur;
	}

	public void setGrnd_value_pur(BigDecimal grnd_value_pur) {
		this.grnd_value_pur = grnd_value_pur;
	}

	public BigDecimal getGrn_header_total_value_pur() {
		return grn_header_total_value_pur;
	}

	public void setGrn_header_total_value_pur(BigDecimal grn_header_total_value_pur) {
		this.grn_header_total_value_pur = grn_header_total_value_pur;
	}
	
	
	
}
