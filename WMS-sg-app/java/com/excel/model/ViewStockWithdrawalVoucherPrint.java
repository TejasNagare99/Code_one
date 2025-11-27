package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "ViewStockWithdrawalVoucherPrint")
@NamedStoredProcedureQuery(name = "callView_StockWithdrawalVoucherPrint",
procedureName = "StockWithdrawalVoucherPrint",
parameters= {
		@StoredProcedureParameter(name = "loc_id" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "frm_challan_no" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "to_challan_no" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "Withdrawaltype" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "rep_type" ,mode = ParameterMode.IN,type=String.class),
///		@StoredProcedureParameter(name = "fin_year_flag" ,mode = ParameterMode.IN,type=String.class),
//		@StoredProcedureParameter(name = "fin_year_id" ,mode = ParameterMode.IN,type=String.class)
},resultClasses=ViewStockWithdrawalVoucherPrint.class)

@NamedStoredProcedureQuery(name = "callView_StockWithdrawalVoucherPrintPrevious",
procedureName = "StockWithdrawalVoucherPrint_Previous",
parameters= {
		@StoredProcedureParameter(name = "loc_id" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "frm_challan_no" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "to_challan_no" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "Withdrawaltype" ,mode = ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name = "rep_type" ,mode = ParameterMode.IN,type=String.class),
///		@StoredProcedureParameter(name = "fin_year_flag" ,mode = ParameterMode.IN,type=String.class),
//		@StoredProcedureParameter(name = "fin_year_id" ,mode = ParameterMode.IN,type=String.class)
},resultClasses=ViewStockWithdrawalVoucherPrint.class)

public class ViewStockWithdrawalVoucherPrint implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5211691124523859364L;

	@Id
	@Column(name = "Row")
	private String row;

	@Column(name = "LOC_ID")
	private String loc_id;

	@Column(name = "LOC_NM")
	private String loc_nm;

	@Column(name = "SWV_ID")
	private String swv_id;

	@Column(name = "SWV_CHALLAN_NO")
	private String swv_challan_no;

	@Column(name = "CHALLAN_DT")
	private String challan_dt;

	@Column(name = "WITHDRAWAL_TYPE")
	private String withdrawal_type;

	@Column(name = "SWV_SENDER_NAME")
	private String swv_sender_name;

	@Column(name = "SWV_SENDER_ADDRESS1")
	private String swv_sender_address1;

	@Column(name = "SWV_SENDER_ADDRESS2")
	private String swv_sender_address2;

	@Column(name = "SWV_SENDER_ADDRESS3")
	private String swv_sender_address3;

	@Column(name = "SWV_DESTINATION")
	private String swv_destination;

	@Column(name = "SWV_TRANSPORTER")
	private String swv_transporter;

	@Column(name = "SWV_LR_NO")
	private String swv_lr_no;

	@Column(name = "LR_DT")
	private String lr_dt;

	@Column(name = "NO_OF_CASES")
	private String no_of_cases;

	@Column(name = "SWV_STATE_ID")
	private String swv_state_id;

	@Column(name = "STATE")
	private String state;

	@Column(name = "SWV_REMARKS")
	private String swv_remarks;

	@Column(name = "SWVDTL_PROD_ID")
	private String swvdtl_prod_id;

	@Column(name = "SMP_ERP_PROD_CD")
	private String smp_erp_prod_cd;

	@Column(name = "prod_name_pack")
	private String prod_name_pack;

	@Column(name = "STOCK_TYPE")
	private String stock_type;

	@Column(name = "BATCH_NO")
	private String batch_no;

	@Column(name = "BATCH_MFG_DT")
	private String batch_mfg_dt;

	@Column(name = "BATCH_EXPIRY_DT")
	private String batch_expiry_dt;

	@Column(name = "QTY")
	private BigDecimal qty;

	@Column(name = "SWVDTL_RATE")
	private BigDecimal swvdtl_rate;

	@Column(name = "SWV_VALUE")
	private BigDecimal swv_value;

	
	@Column(name = "LOC_DLIC1")
	private String loc_dlic1;

	@Column(name = "LOC_DLIC2")
	private String loc_dlic2;

	@Column(name = "LOC_VATNO")
	private String loc_vatno;

	@Column(name = "LOC_CSTNO")
	private String loc_cstno;

	@Column(name = "loc_cinno")
	private String loc_cinno;

	@Column(name = "loc_web_site")
	private String loc_web_site;

	@Column(name = "loc_emailid")
	private String loc_emailid;
	
	@Column(name = "loc_telephone_no")
	private String loc_telephone_no;
	
	@Column(name = "loc_mobile_no")
	private String loc_mobile_no;
	
	@Column(name = "SWV_TOTWT")
	private String swv_totwt;
	
	@Column(name = "swv_challan_msg")
	private String swv_challan_msg;
	
	@Column(name = "loc_add1")
	public String loc_add1;
	
	@Column(name = "loc_add2")
	public String loc_add2;
	
	@Column(name = "loc_add3")
	public String loc_add3;
	
	@Column(name = "loc_add4")
	public String loc_add4;
	
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

	public String getLoc_dlic1() {
		return loc_dlic1;
	}

	public void setLoc_dlic1(String loc_dlic1) {
		this.loc_dlic1 = loc_dlic1;
	}

	public String getLoc_dlic2() {
		return loc_dlic2;
	}

	public void setLoc_dlic2(String loc_dlic2) {
		this.loc_dlic2 = loc_dlic2;
	}

	public String getLoc_vatno() {
		return loc_vatno;
	}

	public void setLoc_vatno(String loc_vatno) {
		this.loc_vatno = loc_vatno;
	}

	public String getLoc_cstno() {
		return loc_cstno;
	}

	public void setLoc_cstno(String loc_cstno) {
		this.loc_cstno = loc_cstno;
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

	public String getSwv_totwt() {
		return swv_totwt;
	}

	public void setSwv_totwt(String swv_totwt) {
		this.swv_totwt = swv_totwt;
	}

	public String getSwv_challan_msg() {
		return swv_challan_msg;
	}

	public void setSwv_challan_msg(String swv_challan_msg) {
		this.swv_challan_msg = swv_challan_msg;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}

	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getSwv_id() {
		return swv_id;
	}

	public void setSwv_id(String swv_id) {
		this.swv_id = swv_id;
	}

	public String getSwv_challan_no() {
		return swv_challan_no;
	}

	public void setSwv_challan_no(String swv_challan_no) {
		this.swv_challan_no = swv_challan_no;
	}

	public String getChallan_dt() {
		return challan_dt;
	}

	public void setChallan_dt(String challan_dt) {
		this.challan_dt = challan_dt;
	}

	public String getWithdrawal_type() {
		return withdrawal_type;
	}

	public void setWithdrawal_type(String withdrawal_type) {
		this.withdrawal_type = withdrawal_type;
	}

	public String getSwv_sender_name() {
		return swv_sender_name;
	}

	public void setSwv_sender_name(String swv_sender_name) {
		this.swv_sender_name = swv_sender_name;
	}

	public String getSwv_sender_address1() {
		return swv_sender_address1;
	}

	public void setSwv_sender_address1(String swv_sender_address1) {
		this.swv_sender_address1 = swv_sender_address1;
	}

	public String getSwv_sender_address2() {
		return swv_sender_address2;
	}

	public void setSwv_sender_address2(String swv_sender_address2) {
		this.swv_sender_address2 = swv_sender_address2;
	}

	public String getSwv_sender_address3() {
		return swv_sender_address3;
	}

	public void setSwv_sender_address3(String swv_sender_address3) {
		this.swv_sender_address3 = swv_sender_address3;
	}

	public String getSwv_destination() {
		return swv_destination;
	}

	public void setSwv_destination(String swv_destination) {
		this.swv_destination = swv_destination;
	}

	public String getSwv_transporter() {
		return swv_transporter;
	}

	public void setSwv_transporter(String swv_transporter) {
		this.swv_transporter = swv_transporter;
	}

	public String getSwv_lr_no() {
		return swv_lr_no;
	}

	public void setSwv_lr_no(String swv_lr_no) {
		this.swv_lr_no = swv_lr_no;
	}

	public String getLr_dt() {
		return lr_dt;
	}

	public void setLr_dt(String lr_dt) {
		this.lr_dt = lr_dt;
	}

	public String getNo_of_cases() {
		return no_of_cases;
	}

	public void setNo_of_cases(String no_of_cases) {
		this.no_of_cases = no_of_cases;
	}

	public String getSwv_state_id() {
		return swv_state_id;
	}

	public void setSwv_state_id(String swv_state_id) {
		this.swv_state_id = swv_state_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSwv_remarks() {
		return swv_remarks;
	}

	public void setSwv_remarks(String swv_remarks) {
		this.swv_remarks = swv_remarks;
	}

	public String getSwvdtl_prod_id() {
		return swvdtl_prod_id;
	}

	public void setSwvdtl_prod_id(String swvdtl_prod_id) {
		this.swvdtl_prod_id = swvdtl_prod_id;
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

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
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

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getSwvdtl_rate() {
		return swvdtl_rate;
	}

	public void setSwvdtl_rate(BigDecimal swvdtl_rate) {
		this.swvdtl_rate = swvdtl_rate;
	}

	public BigDecimal getSwv_value() {
		return swv_value;
	}

	public void setSwv_value(BigDecimal swv_value) {
		this.swv_value = swv_value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
