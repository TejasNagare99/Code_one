package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "Dispatch_Header")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Dispatch_Header {
	@Id
	@Column(name="DSP_ID")
	public Long dsp_id;

	@Column(name="DSP_NO")
	public String dsp_no;

	@Column(name="DSP_COMPANY")
	public String dsp_company;

	@Column(name="DSP_FIN_YEAR")
	public String dsp_fin_year;

	@Column(name="DSP_PERIOD_CODE")
	public String dsp_period_code;

	@Column(name="DSP_LOC_ID")
	public Long dsp_loc_id;

	@Column(name="DSP_ALLOC_ID")
	public Long dsp_alloc_id;

	@Column(name="DSP_SMP_ID")
	public Long dsp_smp_id;

	@Column(name="DSP_FSTAFF_ID")
	public Long dsp_fstaff_id;

	@Column(name="DSP_DIV_ID")
	public Long dsp_div_id;

	@Column(name="DSP_RECVG_LOC_ID")
	public Long dsp_recvg_loc_id;

	@Column(name="DSP_STATE_ID")
	public Long dsp_state_id;

	@Column(name="DSP_DT")
	public Date dsp_dt;

	@Column(name="DSP_CHALLAN_DT")
	public Date dsp_challan_dt;

	@Column(name="DSP_CHALLAN_NO")
	public String dspChallanNo;

	@Column(name="DSP_LR_NO")
	public String dsp_lr_no;

	@Column(name="DSP_LR_DT")
	public Date dsp_lr_dt;

	@Column(name="DSP_LORRY_NO")
	public String dsp_lorry_no;

	@Column(name="DSP_PRINTED")
	public String dsp_printed;

	@Column(name="DSP_TRANSPORTER")
	public String dsp_transporter;

	@Column(name="DSP_TOTAL_GOODS_VAL")
	public BigDecimal dsp_total_goods_val;

	@Column(name="DSP_OCTROI")
	public BigDecimal dsp_octroi;

	@Column(name="DSP_ERP_DEL_NUM")
	public String dsp_erp_del_num;

	@Column(name="DSP_ERR_DTL")
	public String dsp_err_dtl;

	@Column(name="DSP_SUM_DSP_ID")
	public Long dsp_sum_dsp_id;

	@Column(name="DSP_SUM_CHALLAN_NO")
	public String dspSumChallanNo;

	@Column(name="DSP_prev_alloc_flg")
	public String dsp_prev_alloc_flg;

	@Column(name="DSP_prev_alloc_id")
	public Long dsp_prev_alloc_id;

	@Column(name="DSP_prevSMP_ID")
	public Long dsp_prevsmp_id;

	@Column(name="DSP_manual_flg")
	public String dsp_manual_flg;

	@Column(name="DSP_cycle")
	public Long dsp_cycle;

	@Column(name="DSP_WT")
	public BigDecimal dsp_wt;

	@Column(name="DSP_CASES")
	public int dsp_cases;

	@Column(name="DSP_challan_msg")
	public String dsp_challan_msg;

	@Column(name="DSPFSTAFF_STATEID")
	public Long dspfstaff_stateid;

	@Column(name="DSPFSTAFF_NAME")
	public String dspfstaff_name;

	@Column(name="DSPFSTAFF_DISPLAYNAME")
	public String dspfstaff_displayname;

	@Column(name="DSPFSTAFF_EMPLOYEENO")
	public String dspfstaff_employeeno;

	@Column(name="DSPFSTAFF_ADDR1")
	public String dspfstaff_addr1;

	@Column(name="DSPFSTAFF_ADDR2")
	public String dspfstaff_addr2;

	@Column(name="DSPFSTAFF_ADDR3")
	public String dspfstaff_addr3;

	@Column(name="DSPFSTAFF_ADDR4")
	public String dspfstaff_addr4;

	@Column(name="DSPFSTAFF_DESTINATION")
	public String dspfstaff_destination;

	@Column(name="DSPFSTAFF_PANNO")
	public String dspfstaff_panno;

	@Column(name="DSPFSTAFF_TELNO")
	public String dspfstaff_telno;

	@Column(name="DSPFSTAFF_MOBILE")
	public String dspfstaff_mobile;

	@Column(name="DSPFSTAFF_EMAIL")
	public String dspfstaff_email;

	@Column(name="DSPFSTAFF_TRANSPORTER")
	public String dspfstaff_transporter;

	@Column(name="DSPFSTAFF_DESG")
	public String dspfstaff_desg;

	@Column(name="DSP_ins_usr_id")
	public String dsp_ins_usr_id;

	@Column(name="DSP_mod_usr_id")
	public String dsp_mod_usr_id;

	@Column(name="DSP_ins_dt")
	public Date dsp_ins_dt;

	@Column(name="DSP_mod_dt")
	public Date dsp_mod_dt;

	@Column(name="DSP_ins_ip_add")
	public String dsp_ins_ip_add;

	@Column(name="DSP_mod_ip_add")
	public String dsp_mod_ip_add;

	@Column(name="DSP_status")
	public String dsp_status;

	@Column(name="DSP_PERIOD_ID")
	public Long dsp_period_id;

	@Column(name="DSP_BILLABLE_WT")
	public BigDecimal dsp_billable_wt;

	@Column(name="DSP_DELIVERY_DATE")
	public Date dsp_delivery_date;

	@Column(name="DSP_RECD_BY")
	public String dsp_recd_by;

	@Column(name="DSP_REMARK")
	public String dsp_remark;

	@Column(name="DSP_APPR_REQ")
	public Long dsp_appr_req;

	@Column(name="DSP_APPR_ACQ")
	public Long dsp_appr_acq;

	@Column(name="DSP_APPR_STATUS")
	public String dsp_appr_status;

	@Column(name="WAY_BILL_NO")
	public String way_bill_no;

	@Column(name="REMARKS")
	public String remarks;

	@Column(name="DSP_LBL_PRINT")
	public String dsp_lbl_print;

	@Column(name="SGST_BILL_AMT")
	public BigDecimal sgst_bill_amt;

	@Column(name="CGST_BILL_AMT")
	public BigDecimal cgst_bill_amt;

	@Column(name="IGST_BILL_AMT")
	public BigDecimal igst_bill_amt;

	@Column(name="GST_DOC_TYPE")
	public String gst_doc_type;

	@Column(name="ROUNDOFF")
	public BigDecimal roundoff;

	@Column(name="direct_to_pso_ind")
	public String direct_to_pso_ind;

//	@Column(name="STOCK_AT_CFA")
//	public String stock_at_cfa;

//	@Column(name="CONF_BY_CFA_FLG")
//	public String conf_by_cfa_flg;

//	@Column(name="CONF_BY_CFA_DATE")
//	public String conf_by_cfa_date;

//	@Column(name="EXPT_DELI_DT")
//	public String expt_deli_dt;

	@Column(name="UPLOAD_SAP")
	public String upload_sap;
	
	@Column(name="COURIER_EXPENSES")
	public BigDecimal courier_expenses;
	
	@Column(name="ACTUAL_DELIVERY_DATE")
	public Date actual_delivery_date;
	
	@Column(name="DELIVERY_DETAIL_MODIFY_DATE")
	public Date delivery_detail_modify_date;
	
	@Column(name="TEAM_CODE")
	public String team_code;

	@Column(name="SMP_PROD_TYPE_ID")
	private String smp_prod_type_id;
	
	@Column(name="EMAIL_SEND_TO_DOCTOR_DATE")
	private Date email_send_to_doctor_date;
	
	@Column(name="RECD_BY_DOCTOR_FLG")
	private String recd_by_doctor_flg;
	
	@Column(name="RECD_BY_DOCTOR_ACTION_DT")
	private Date recd_by_doctor_action_dt;
	
	@Column(name="DSP_TRANS_GST_REG_NO")
	private String dsp_trans_gst_reg_no;
	
	@Column(name="BLK_HCP_REQ_ID")
	private Long blk_hcp_req_id;
	
	@Transient private String dsp_challan_dtString;
	@Transient private String dsp_lr_dtString;
	
	@Transient private boolean isBlank;
	
	@Column(name="transport_mode")
	private String transport_mode;
	
	public String getTransport_mode() {
		return transport_mode;
	}

	public void setTransport_mode(String transport_mode) {
		this.transport_mode = transport_mode;
	}
	
	public boolean isBlank() {
		return isBlank;
	}

	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}

	public String getDsp_trans_gst_reg_no() {
		return dsp_trans_gst_reg_no;
	}

	public void setDsp_trans_gst_reg_no(String dsp_trans_gst_reg_no) {
		this.dsp_trans_gst_reg_no = dsp_trans_gst_reg_no;
	}

	public String getDsp_lr_dtString() {
		return dsp_lr_dtString;
	}

	public void setDsp_lr_dtString(String dsp_lr_dtString) {
		this.dsp_lr_dtString = dsp_lr_dtString;
	}

	public String getDsp_challan_dtString() {
		return dsp_challan_dtString;
	}

	public void setDsp_challan_dtString(String dsp_challan_dtString) {
		this.dsp_challan_dtString = dsp_challan_dtString;
	}

	public Date getEmail_send_to_doctor_date() {
		return email_send_to_doctor_date;
	}

	public void setEmail_send_to_doctor_date(Date email_send_to_doctor_date) {
		this.email_send_to_doctor_date = email_send_to_doctor_date;
	}

	public String getRecd_by_doctor_flg() {
		return recd_by_doctor_flg;
	}

	public void setRecd_by_doctor_flg(String recd_by_doctor_flg) {
		this.recd_by_doctor_flg = recd_by_doctor_flg;
	}

	public Date getRecd_by_doctor_action_dt() {
		return recd_by_doctor_action_dt;
	}

	public void setRecd_by_doctor_action_dt(Date recd_by_doctor_action_dt) {
		this.recd_by_doctor_action_dt = recd_by_doctor_action_dt;
	}

	public BigDecimal getCourier_expenses() {
		return courier_expenses;
	}

	public void setCourier_expenses(BigDecimal courier_expenses) {
		this.courier_expenses = courier_expenses;
	}

	public Date getActual_delivery_date() {
		return actual_delivery_date;
	}

	public void setActual_delivery_date(Date actual_delivery_date) {
		this.actual_delivery_date = actual_delivery_date;
	}

	public Date getDelivery_detail_modify_date() {
		return delivery_detail_modify_date;
	}

	public void setDelivery_detail_modify_date(Date delivery_detail_modify_date) {
		this.delivery_detail_modify_date = delivery_detail_modify_date;
	}

	public Long getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}

	public String getDsp_no() {
		return dsp_no;
	}

	public void setDsp_no(String dsp_no) {
		this.dsp_no = dsp_no;
	}

	public String getDsp_company() {
		return dsp_company;
	}

	public void setDsp_company(String dsp_company) {
		this.dsp_company = dsp_company;
	}

	public String getDsp_fin_year() {
		return dsp_fin_year;
	}

	public void setDsp_fin_year(String dsp_fin_year) {
		this.dsp_fin_year = dsp_fin_year;
	}

	public String getDsp_period_code() {
		return dsp_period_code;
	}

	public void setDsp_period_code(String dsp_period_code) {
		this.dsp_period_code = dsp_period_code;
	}

	public Long getDsp_loc_id() {
		return dsp_loc_id;
	}

	public void setDsp_loc_id(Long dsp_loc_id) {
		this.dsp_loc_id = dsp_loc_id;
	}

	public Long getDsp_alloc_id() {
		return dsp_alloc_id;
	}

	public void setDsp_alloc_id(Long dsp_alloc_id) {
		this.dsp_alloc_id = dsp_alloc_id;
	}

	public Long getDsp_smp_id() {
		return dsp_smp_id;
	}

	public void setDsp_smp_id(Long dsp_smp_id) {
		this.dsp_smp_id = dsp_smp_id;
	}

	public Long getDsp_fstaff_id() {
		return dsp_fstaff_id;
	}

	public void setDsp_fstaff_id(Long dsp_fstaff_id) {
		this.dsp_fstaff_id = dsp_fstaff_id;
	}

	public Long getDsp_div_id() {
		return dsp_div_id;
	}

	public void setDsp_div_id(Long dsp_div_id) {
		this.dsp_div_id = dsp_div_id;
	}

	public Long getDsp_recvg_loc_id() {
		return dsp_recvg_loc_id;
	}

	public void setDsp_recvg_loc_id(Long dsp_recvg_loc_id) {
		this.dsp_recvg_loc_id = dsp_recvg_loc_id;
	}

	public Long getDsp_state_id() {
		return dsp_state_id;
	}

	public void setDsp_state_id(Long dsp_state_id) {
		this.dsp_state_id = dsp_state_id;
	}

	public Date getDsp_dt() {
		return dsp_dt;
	}

	public void setDsp_dt(Date dsp_dt) {
		this.dsp_dt = dsp_dt;
	}

	public Date getDsp_challan_dt() {
		return dsp_challan_dt;
	}

	public void setDsp_challan_dt(Date dsp_challan_dt) {
		this.dsp_challan_dt = dsp_challan_dt;
	}


	public String getDspChallanNo() {
		return dspChallanNo;
	}

	public void setDspChallanNo(String dspChallanNo) {
		this.dspChallanNo = dspChallanNo;
	}

	public String getDsp_lr_no() {
		return dsp_lr_no;
	}

	public void setDsp_lr_no(String dsp_lr_no) {
		this.dsp_lr_no = dsp_lr_no;
	}


	public String getDsp_lorry_no() {
		return dsp_lorry_no;
	}

	public void setDsp_lorry_no(String dsp_lorry_no) {
		this.dsp_lorry_no = dsp_lorry_no;
	}

	public String getDsp_printed() {
		return dsp_printed;
	}

	public void setDsp_printed(String dsp_printed) {
		this.dsp_printed = dsp_printed;
	}

	public String getDsp_transporter() {
		return dsp_transporter;
	}

	public void setDsp_transporter(String dsp_transporter) {
		this.dsp_transporter = dsp_transporter;
	}

	public BigDecimal getDsp_total_goods_val() {
		return dsp_total_goods_val;
	}

	public void setDsp_total_goods_val(BigDecimal dsp_total_goods_val) {
		this.dsp_total_goods_val = dsp_total_goods_val;
	}

	public BigDecimal getDsp_octroi() {
		return dsp_octroi;
	}

	public void setDsp_octroi(BigDecimal dsp_octroi) {
		this.dsp_octroi = dsp_octroi;
	}

	public String getDsp_erp_del_num() {
		return dsp_erp_del_num;
	}

	public void setDsp_erp_del_num(String dsp_erp_del_num) {
		this.dsp_erp_del_num = dsp_erp_del_num;
	}

	public String getDsp_err_dtl() {
		return dsp_err_dtl;
	}

	public void setDsp_err_dtl(String dsp_err_dtl) {
		this.dsp_err_dtl = dsp_err_dtl;
	}

	public Long getDsp_sum_dsp_id() {
		return dsp_sum_dsp_id;
	}

	public void setDsp_sum_dsp_id(Long dsp_sum_dsp_id) {
		this.dsp_sum_dsp_id = dsp_sum_dsp_id;
	}
	
	public String getDspSumChallanNo() {
		return dspSumChallanNo;
	}

	public void setDspSumChallanNo(String dspSumChallanNo) {
		this.dspSumChallanNo = dspSumChallanNo;
	}

	public String getDsp_prev_alloc_flg() {
		return dsp_prev_alloc_flg;
	}

	public void setDsp_prev_alloc_flg(String dsp_prev_alloc_flg) {
		this.dsp_prev_alloc_flg = dsp_prev_alloc_flg;
	}

	public Long getDsp_prev_alloc_id() {
		return dsp_prev_alloc_id;
	}

	public void setDsp_prev_alloc_id(Long dsp_prev_alloc_id) {
		this.dsp_prev_alloc_id = dsp_prev_alloc_id;
	}

	public Long getDsp_prevsmp_id() {
		return dsp_prevsmp_id;
	}

	public void setDsp_prevsmp_id(Long dsp_prevsmp_id) {
		this.dsp_prevsmp_id = dsp_prevsmp_id;
	}

	public String getDsp_manual_flg() {
		return dsp_manual_flg;
	}

	public void setDsp_manual_flg(String dsp_manual_flg) {
		this.dsp_manual_flg = dsp_manual_flg;
	}

	public Long getDsp_cycle() {
		return dsp_cycle;
	}

	public void setDsp_cycle(Long dsp_cycle) {
		this.dsp_cycle = dsp_cycle;
	}

	public BigDecimal getDsp_wt() {
		return dsp_wt;
	}

	public void setDsp_wt(BigDecimal dsp_wt) {
		this.dsp_wt = dsp_wt;
	}


	public String getDsp_challan_msg() {
		return dsp_challan_msg;
	}

	public void setDsp_challan_msg(String dsp_challan_msg) {
		this.dsp_challan_msg = dsp_challan_msg;
	}

	public Long getDspfstaff_stateid() {
		return dspfstaff_stateid;
	}

	public void setDspfstaff_stateid(Long dspfstaff_stateid) {
		this.dspfstaff_stateid = dspfstaff_stateid;
	}

	public String getDspfstaff_name() {
		return dspfstaff_name;
	}

	public void setDspfstaff_name(String dspfstaff_name) {
		this.dspfstaff_name = dspfstaff_name;
	}

	public String getDspfstaff_displayname() {
		return dspfstaff_displayname;
	}

	public void setDspfstaff_displayname(String dspfstaff_displayname) {
		this.dspfstaff_displayname = dspfstaff_displayname;
	}

	public String getDspfstaff_employeeno() {
		return dspfstaff_employeeno;
	}

	public void setDspfstaff_employeeno(String dspfstaff_employeeno) {
		this.dspfstaff_employeeno = dspfstaff_employeeno;
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

	public String getDspfstaff_panno() {
		return dspfstaff_panno;
	}

	public void setDspfstaff_panno(String dspfstaff_panno) {
		this.dspfstaff_panno = dspfstaff_panno;
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

	public String getDspfstaff_transporter() {
		return dspfstaff_transporter;
	}

	public void setDspfstaff_transporter(String dspfstaff_transporter) {
		this.dspfstaff_transporter = dspfstaff_transporter;
	}

	public String getDspfstaff_desg() {
		return dspfstaff_desg;
	}

	public void setDspfstaff_desg(String dspfstaff_desg) {
		this.dspfstaff_desg = dspfstaff_desg;
	}

	public String getDsp_ins_usr_id() {
		return dsp_ins_usr_id;
	}

	public void setDsp_ins_usr_id(String dsp_ins_usr_id) {
		this.dsp_ins_usr_id = dsp_ins_usr_id;
	}

	public String getDsp_mod_usr_id() {
		return dsp_mod_usr_id;
	}

	public void setDsp_mod_usr_id(String dsp_mod_usr_id) {
		this.dsp_mod_usr_id = dsp_mod_usr_id;
	}

	public Date getDsp_ins_dt() {
		return dsp_ins_dt;
	}

	public void setDsp_ins_dt(Date dsp_ins_dt) {
		this.dsp_ins_dt = dsp_ins_dt;
	}

	public Date getDsp_mod_dt() {
		return dsp_mod_dt;
	}

	public void setDsp_mod_dt(Date dsp_mod_dt) {
		this.dsp_mod_dt = dsp_mod_dt;
	}

	public String getDsp_ins_ip_add() {
		return dsp_ins_ip_add;
	}

	public void setDsp_ins_ip_add(String dsp_ins_ip_add) {
		this.dsp_ins_ip_add = dsp_ins_ip_add;
	}

	public String getDsp_mod_ip_add() {
		return dsp_mod_ip_add;
	}

	public void setDsp_mod_ip_add(String dsp_mod_ip_add) {
		this.dsp_mod_ip_add = dsp_mod_ip_add;
	}

	public String getDsp_status() {
		return dsp_status;
	}

	public void setDsp_status(String dsp_status) {
		this.dsp_status = dsp_status;
	}

	public Long getDsp_period_id() {
		return dsp_period_id;
	}

	public void setDsp_period_id(Long dsp_period_id) {
		this.dsp_period_id = dsp_period_id;
	}

	public BigDecimal getDsp_billable_wt() {
		return dsp_billable_wt;
	}

	public void setDsp_billable_wt(BigDecimal dsp_billable_wt) {
		this.dsp_billable_wt = dsp_billable_wt;
	}

	public Date getDsp_delivery_date() {
		return dsp_delivery_date;
	}

	public void setDsp_delivery_date(Date dsp_delivery_date) {
		this.dsp_delivery_date = dsp_delivery_date;
	}

	public String getDsp_recd_by() {
		return dsp_recd_by;
	}

	public void setDsp_recd_by(String dsp_recd_by) {
		this.dsp_recd_by = dsp_recd_by;
	}

	public String getDsp_remark() {
		return dsp_remark;
	}

	public void setDsp_remark(String dsp_remark) {
		this.dsp_remark = dsp_remark;
	}

	public Long getDsp_appr_req() {
		return dsp_appr_req;
	}

	public void setDsp_appr_req(Long dsp_appr_req) {
		this.dsp_appr_req = dsp_appr_req;
	}

	public Long getDsp_appr_acq() {
		return dsp_appr_acq;
	}

	public void setDsp_appr_acq(Long dsp_appr_acq) {
		this.dsp_appr_acq = dsp_appr_acq;
	}

	public String getDsp_appr_status() {
		return dsp_appr_status;
	}

	public void setDsp_appr_status(String dsp_appr_status) {
		this.dsp_appr_status = dsp_appr_status;
	}

	public String getWay_bill_no() {
		return way_bill_no;
	}

	public void setWay_bill_no(String way_bill_no) {
		this.way_bill_no = way_bill_no;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDsp_lbl_print() {
		return dsp_lbl_print;
	}

	public void setDsp_lbl_print(String dsp_lbl_print) {
		this.dsp_lbl_print = dsp_lbl_print;
	}

	public BigDecimal getSgst_bill_amt() {
		return sgst_bill_amt;
	}

	public void setSgst_bill_amt(BigDecimal sgst_bill_amt) {
		this.sgst_bill_amt = sgst_bill_amt;
	}

	public BigDecimal getCgst_bill_amt() {
		return cgst_bill_amt;
	}

	public void setCgst_bill_amt(BigDecimal cgst_bill_amt) {
		this.cgst_bill_amt = cgst_bill_amt;
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

	public String getDirect_to_pso_ind() {
		return direct_to_pso_ind;
	}

	public void setDirect_to_pso_ind(String direct_to_pso_ind) {
		this.direct_to_pso_ind = direct_to_pso_ind;
	}

//	public String getStock_at_cfa() {
//		return stock_at_cfa;
//	}
//
//	public void setStock_at_cfa(String stock_at_cfa) {
//		this.stock_at_cfa = stock_at_cfa;
//	}

//	public String getConf_by_cfa_flg() {
//		return conf_by_cfa_flg;
//	}
//
//	public void setConf_by_cfa_flg(String conf_by_cfa_flg) {
//		this.conf_by_cfa_flg = conf_by_cfa_flg;
//	}

//	public String getConf_by_cfa_date() {
//		return conf_by_cfa_date;
//	}
//
//	public void setConf_by_cfa_date(String conf_by_cfa_date) {
//		this.conf_by_cfa_date = conf_by_cfa_date;
//	}

//	public String getExpt_deli_dt() {
//		return expt_deli_dt;
//	}
//
//	public void setExpt_deli_dt(String expt_deli_dt) {
//		this.expt_deli_dt = expt_deli_dt;
//	}

	public String getUpload_sap() {
		return upload_sap;
	}

	public void setUpload_sap(String upload_sap) {
		this.upload_sap = upload_sap;
	}

	public Date getDsp_lr_dt() {
		return dsp_lr_dt;
	}

	public void setDsp_lr_dt(Date dsp_lr_dt) {
		this.dsp_lr_dt = dsp_lr_dt;
	}

	public int getDsp_cases() {
		return dsp_cases;
	}

	public void setDsp_cases(int dsp_cases) {
		this.dsp_cases = dsp_cases;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getSmp_prod_type_id() {
		return smp_prod_type_id;
	}

	public void setSmp_prod_type_id(String smp_prod_type_id) {
		this.smp_prod_type_id = smp_prod_type_id;
	}

	public Long getBlk_hcp_req_id() {
		return blk_hcp_req_id;
	}

	public void setBlk_hcp_req_id(Long blk_hcp_req_id) {
		this.blk_hcp_req_id = blk_hcp_req_id;
	}
	
	
}
