package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="GRN_HEADER")
@DynamicUpdate(value=true)
public class GrnHeader implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7164916944324029621L;

	@Id
	@Column(name="GRN_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long grnId;
	
	@Column(name="GRN_NO")
	private String grn_no;
	
	@Column(name="GRND_SL_NO")
	private Long grnd_sl_no;
	
	@Column(name="GRN_COMPANY")
	private String grn_company;
	
	@Column(name="GRN_FIN_YEAR")
	private String grn_fin_year;
	
	@Column(name="GRN_PERIOD_CODE")
	private String grn_period_code;
	
	@Column(name="GRN_LOC_ID")
	private Long grn_loc_id;
	
	@Column(name="GRN_DT")
	private Date grn_dt;
	
	@Column(name="GRN_SUPPLIER_ID")
	private Long grn_supplier_id;
	
	@Column(name="GRN_TRANSFER_NO")
	private String grn_transfer_no;
	
	@Column(name="GRN_TRANSFER_DT")
	private Date grn_transfer_dt;
	
	@Column(name="GRN_LR_NO")
	private String grn_lr_no;
	
	@Column(name="GRN_LR_DT")
	private Date grn_lr_dt;
	
	@Column(name="GRN_LORRY_NO")
	private String grn_lorry_no;
	
	@Column(name="GRN_PURCHASE_ID")
	private Long grn_purchase_id;
	
	@Column(name="GRN_SENDING_LOC_ID")
	private Long grn_sending_loc_id;
	
	@Column(name="GRN_TRF_TYPE")
	private String grn_trf_type;
	
	@Column(name="GRN_TRF_DT")
	private Date grn_trf_dt;
	
	@Column(name="GRN_IN_TRANSIT")
	private String grn_in_transit;
	
	@Column(name="GRN_AUTO_GRN_DT")
	private Date grn_auto_grn_dt;
	
	@Column(name="GRN_AUTO_GRN_AUTH")
	private String grn_auto_grn_auth;
	
	@Column(name="GRN_LOCKED_YN")
	private String grn_locked_yn;
	
	@Column(name="GRN_ERP_GRN_CD")
	private String grn_erp_grn_cd;
	
	@Column(name="GRN_ERP_STKTFR_CD")
	private String grn_erp_stktfr_cd;
	
	@Column(name="GRN_DELIVERY_NO")
	private String grn_delivery_no;
	
	@Column(name="GRN_F_FORM_NO")
	private String grn_f_form_no;
	
	@Column(name="GRN_CONSIGNMENT_TYPE")
	private String grn_consignment_type;
	
	@Column(name="GRN_FULL_SHIPPERS")
	private BigDecimal grn_full_shippers;
	
	@Column(name="GRN_LOOSE_SHIPPERS")
	private BigDecimal grn_loose_shippers;
	
	@Column(name="GRN_WEIGHT")
	private BigDecimal grn_weight;
	
	@Column(name="GRN_VOLUME")
	private BigDecimal grn_volume;
	
	@Column(name="GRN_TRANSPORT_MODE")
	private String grn_transport_mode;
	
	@Column(name="GRN_OCTROI")
	private BigDecimal grn_octroi;
	
	@Column(name="GRN_INS_USR_ID")
	private String grn_ins_usr_id;
	
	@Column(name="GRN_MOD_USR_ID")
	private String grn_mod_usr_id;
	
	@Column(name="GRN_INS_DT")
	private Date grn_ins_dt;
	
	@Column(name="GRN_MOD_DT")
	private Date grn_mod_dt;
	
	@Column(name="GRN_INS_IP_ADD")
	private String grn_ins_ip_add;
	
	@Column(name="GRN_MOD_IP_ADD")
	private String grn_mod_ip_add;
	
	@Column(name="GRN_STATUS")
	private String grn_status;
	
	@Column(name="GRN_DOCTYPE_ID")
	private Long grn_doctype_id;
	
	@Column(name="GRN_APPR_REQ")
	private Long grn_appr_req;
	
	@Column(name="GRN_APPR_ACQ")
	private Long grn_appr_acq;
	
	@Column(name="GRN_APPR_STATUS")
	private String grn_appr_status;
	
	@Column(name="GRN_PO_NO")
	private String grn_po_no;
	
	@Column(name="GRN_PO_DATE")
	private Date grn_po_date;
	
	@Column(name="GRN_TOTAL_VALUE")
	private BigDecimal grn_total_value;
	
	@Column(name="GRN_APPR_CYCLE")
	private String grn_appr_cycle;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="GRN_LBL_PRINT")
	private String grn_lbl_print;
	
	@Column(name="SGST_BILL_AMT")
	private BigDecimal sgst_bill_amt;
	
	@Column(name="CGST_BILL_AMT")
	private BigDecimal cgst_bill_amt;
	
	@Column(name="IGST_BILL_AMT")
	private BigDecimal igst_bill_amt;
	
	@Column(name="GST_REVERSE_CHG")
	private String gst_reverse_chg;
	
	@Column(name="GST_DOC_TYPE")
	private String gst_doc_type;
	
	@Column(name="TEXT1")
	private String text1;
	
	@Column(name="TEXT2")
	private String text2;
	
	@Column(name="VALUE1")
	private BigDecimal value1;
	
	@Column(name="VALUE2")
	private BigDecimal value2;
	
	@Column(name="ROUNDOFF")
	private BigDecimal roundoff;
	
	@Column(name="GCMA_CODE")
	private String gcma_code;
	
	@Column(name="GCMA_APPR_DATE")
	private Date gcma_appr_date;
	
	@Column(name="GCMA_EXPIRY_DATE")
	private Date gcma_expiry_date;
	
	@Column(name="RETURN_FROM_FS_ID")
	private Long return_from_fs_id;
	
	@Column(name="GRN_TYPE_ID")
	private Long grn_type_id;
	
	@Column(name="GRN_STOCK_SA_OR_NS")
	private String grn_stock_sa_or_ns;
	
	@Column(name="GRN_STOCK_TYPE_ID")
	private Long grn_stock_type_id;

	@Column(name="DSP_CHALLAN_NO")
	private String dsp_challan_no;
	
	@Column(name="GRN_CONFIRM")
	private String grn_confirm;
	
	@Column(name = "TRANSPORTER_NAME")
	private String transporter_name;
	
	@Column(name = "GRN_TOTAL_VALUE_PUR")
	private BigDecimal grn_total_value_pur;
	
	@Column(name = "roundoff_PR")
	private BigDecimal roundoff_pr;
	
	@Column(name = "GRN_REF_NO")
	private String grn_ref_no;
	
	@Column(name = "VEHICLE_RECD_TIME")
	private Date vehicle_recd_time;
	
	public Date getVehicle_recd_time() {
		return vehicle_recd_time;
	}

	public void setVehicle_recd_time(Date vehicle_recd_time) {
		this.vehicle_recd_time = vehicle_recd_time;
	}
	
	public String getGrn_ref_no() {
		return grn_ref_no;
	}

	public void setGrn_ref_no(String grn_ref_no) {
		this.grn_ref_no = grn_ref_no;
	}
	
	public BigDecimal getRoundoff_pr() {
		return roundoff_pr;
	}

	public void setRoundoff_pr(BigDecimal roundoff_pr) {
		this.roundoff_pr = roundoff_pr;
	}

	public BigDecimal getGrn_total_value_pur() {
		return grn_total_value_pur;
	}

	public void setGrn_total_value_pur(BigDecimal grn_total_value_pur) {
		this.grn_total_value_pur = grn_total_value_pur;
	}
	
	
	public String getTransporter_name() {
		return transporter_name;
	}

	public void setTransporter_name(String transporter_name) {
		this.transporter_name = transporter_name;
	}

	public Long getGrnId() {
		return grnId;
	}

	public void setGrnId(Long grnId) {
		this.grnId = grnId;
	}

	public String getGrn_no() {
		return grn_no;
	}

	public void setGrn_no(String grn_no) {
		this.grn_no = grn_no;
	}

	public Long getGrnd_sl_no() {
		return grnd_sl_no;
	}

	public void setGrnd_sl_no(Long grnd_sl_no) {
		this.grnd_sl_no = grnd_sl_no;
	}

	public String getGrn_company() {
		return grn_company;
	}

	public void setGrn_company(String grn_company) {
		this.grn_company = grn_company;
	}

	public String getGrn_fin_year() {
		return grn_fin_year;
	}

	public void setGrn_fin_year(String grn_fin_year) {
		this.grn_fin_year = grn_fin_year;
	}

	public String getGrn_period_code() {
		return grn_period_code;
	}

	public void setGrn_period_code(String grn_period_code) {
		this.grn_period_code = grn_period_code;
	}

	public Long getGrn_loc_id() {
		return grn_loc_id;
	}

	public void setGrn_loc_id(Long grn_loc_id) {
		this.grn_loc_id = grn_loc_id;
	}

	public Date getGrn_dt() {
		return grn_dt;
	}

	public void setGrn_dt(Date grn_dt) {
		this.grn_dt = grn_dt;
	}

	public Long getGrn_supplier_id() {
		return grn_supplier_id;
	}

	public void setGrn_supplier_id(Long grn_supplier_id) {
		this.grn_supplier_id = grn_supplier_id;
	}

	public String getGrn_transfer_no() {
		return grn_transfer_no;
	}

	public void setGrn_transfer_no(String grn_transfer_no) {
		this.grn_transfer_no = grn_transfer_no;
	}

	public Date getGrn_transfer_dt() {
		return grn_transfer_dt;
	}

	public void setGrn_transfer_dt(Date grn_transfer_dt) {
		this.grn_transfer_dt = grn_transfer_dt;
	}

	public String getGrn_lr_no() {
		return grn_lr_no;
	}

	public void setGrn_lr_no(String grn_lr_no) {
		this.grn_lr_no = grn_lr_no;
	}

	public Date getGrn_lr_dt() {
		return grn_lr_dt;
	}

	public void setGrn_lr_dt(Date grn_lr_dt) {
		this.grn_lr_dt = grn_lr_dt;
	}

	public String getGrn_lorry_no() {
		return grn_lorry_no;
	}

	public void setGrn_lorry_no(String grn_lorry_no) {
		this.grn_lorry_no = grn_lorry_no;
	}

	public Long getGrn_purchase_id() {
		return grn_purchase_id;
	}

	public void setGrn_purchase_id(Long grn_purchase_id) {
		this.grn_purchase_id = grn_purchase_id;
	}

	public Long getGrn_sending_loc_id() {
		return grn_sending_loc_id;
	}

	public void setGrn_sending_loc_id(Long grn_sending_loc_id) {
		this.grn_sending_loc_id = grn_sending_loc_id;
	}

	public String getGrn_trf_type() {
		return grn_trf_type;
	}

	public void setGrn_trf_type(String grn_trf_type) {
		this.grn_trf_type = grn_trf_type;
	}

	public Date getGrn_trf_dt() {
		return grn_trf_dt;
	}

	public void setGrn_trf_dt(Date grn_trf_dt) {
		this.grn_trf_dt = grn_trf_dt;
	}

	public String getGrn_in_transit() {
		return grn_in_transit;
	}

	public void setGrn_in_transit(String grn_in_transit) {
		this.grn_in_transit = grn_in_transit;
	}

	public Date getGrn_auto_grn_dt() {
		return grn_auto_grn_dt;
	}

	public void setGrn_auto_grn_dt(Date grn_auto_grn_dt) {
		this.grn_auto_grn_dt = grn_auto_grn_dt;
	}

	public String getGrn_auto_grn_auth() {
		return grn_auto_grn_auth;
	}

	public void setGrn_auto_grn_auth(String grn_auto_grn_auth) {
		this.grn_auto_grn_auth = grn_auto_grn_auth;
	}

	public String getGrn_locked_yn() {
		return grn_locked_yn;
	}

	public void setGrn_locked_yn(String grn_locked_yn) {
		this.grn_locked_yn = grn_locked_yn;
	}

	public String getGrn_erp_grn_cd() {
		return grn_erp_grn_cd;
	}

	public void setGrn_erp_grn_cd(String grn_erp_grn_cd) {
		this.grn_erp_grn_cd = grn_erp_grn_cd;
	}

	public String getGrn_erp_stktfr_cd() {
		return grn_erp_stktfr_cd;
	}

	public void setGrn_erp_stktfr_cd(String grn_erp_stktfr_cd) {
		this.grn_erp_stktfr_cd = grn_erp_stktfr_cd;
	}

	public String getGrn_delivery_no() {
		return grn_delivery_no;
	}

	public void setGrn_delivery_no(String grn_delivery_no) {
		this.grn_delivery_no = grn_delivery_no;
	}

	public String getGrn_f_form_no() {
		return grn_f_form_no;
	}

	public void setGrn_f_form_no(String grn_f_form_no) {
		this.grn_f_form_no = grn_f_form_no;
	}

	public String getGrn_consignment_type() {
		return grn_consignment_type;
	}

	public void setGrn_consignment_type(String grn_consignment_type) {
		this.grn_consignment_type = grn_consignment_type;
	}

	public BigDecimal getGrn_full_shippers() {
		return grn_full_shippers;
	}

	public void setGrn_full_shippers(BigDecimal grn_full_shippers) {
		this.grn_full_shippers = grn_full_shippers;
	}

	public BigDecimal getGrn_loose_shippers() {
		return grn_loose_shippers;
	}

	public void setGrn_loose_shippers(BigDecimal grn_loose_shippers) {
		this.grn_loose_shippers = grn_loose_shippers;
	}

	public BigDecimal getGrn_weight() {
		return grn_weight;
	}

	public void setGrn_weight(BigDecimal grn_weight) {
		this.grn_weight = grn_weight;
	}

	public BigDecimal getGrn_volume() {
		return grn_volume;
	}

	public void setGrn_volume(BigDecimal grn_volume) {
		this.grn_volume = grn_volume;
	}

	public String getGrn_transport_mode() {
		return grn_transport_mode;
	}

	public void setGrn_transport_mode(String grn_transport_mode) {
		this.grn_transport_mode = grn_transport_mode;
	}

	public BigDecimal getGrn_octroi() {
		return grn_octroi;
	}

	public void setGrn_octroi(BigDecimal grn_octroi) {
		this.grn_octroi = grn_octroi;
	}

	public String getGrn_ins_usr_id() {
		return grn_ins_usr_id;
	}

	public void setGrn_ins_usr_id(String grn_ins_usr_id) {
		this.grn_ins_usr_id = grn_ins_usr_id;
	}

	public String getGrn_mod_usr_id() {
		return grn_mod_usr_id;
	}

	public void setGrn_mod_usr_id(String grn_mod_usr_id) {
		this.grn_mod_usr_id = grn_mod_usr_id;
	}

	public Date getGrn_ins_dt() {
		return grn_ins_dt;
	}

	public void setGrn_ins_dt(Date grn_ins_dt) {
		this.grn_ins_dt = grn_ins_dt;
	}

	public Date getGrn_mod_dt() {
		return grn_mod_dt;
	}

	public void setGrn_mod_dt(Date grn_mod_dt) {
		this.grn_mod_dt = grn_mod_dt;
	}

	public String getGrn_ins_ip_add() {
		return grn_ins_ip_add;
	}

	public void setGrn_ins_ip_add(String grn_ins_ip_add) {
		this.grn_ins_ip_add = grn_ins_ip_add;
	}

	public String getGrn_mod_ip_add() {
		return grn_mod_ip_add;
	}

	public void setGrn_mod_ip_add(String grn_mod_ip_add) {
		this.grn_mod_ip_add = grn_mod_ip_add;
	}

	public String getGrn_status() {
		return grn_status;
	}

	public void setGrn_status(String grn_status) {
		this.grn_status = grn_status;
	}

	public Long getGrn_doctype_id() {
		return grn_doctype_id;
	}

	public void setGrn_doctype_id(Long grn_doctype_id) {
		this.grn_doctype_id = grn_doctype_id;
	}

	public Long getGrn_appr_req() {
		return grn_appr_req;
	}

	public void setGrn_appr_req(Long grn_appr_req) {
		this.grn_appr_req = grn_appr_req;
	}

	public Long getGrn_appr_acq() {
		return grn_appr_acq;
	}

	public void setGrn_appr_acq(Long grn_appr_acq) {
		this.grn_appr_acq = grn_appr_acq;
	}

	public String getGrn_appr_status() {
		return grn_appr_status;
	}

	public void setGrn_appr_status(String grn_appr_status) {
		this.grn_appr_status = grn_appr_status;
	}

	public String getGrn_po_no() {
		return grn_po_no;
	}

	public void setGrn_po_no(String grn_po_no) {
		this.grn_po_no = grn_po_no;
	}

	public Date getGrn_po_date() {
		return grn_po_date;
	}

	public void setGrn_po_date(Date grn_po_date) {
		this.grn_po_date = grn_po_date;
	}

	public BigDecimal getGrn_total_value() {
		return grn_total_value;
	}

	public void setGrn_total_value(BigDecimal grn_total_value) {
		this.grn_total_value = grn_total_value;
	}

	public String getGrn_appr_cycle() {
		return grn_appr_cycle;
	}

	public void setGrn_appr_cycle(String grn_appr_cycle) {
		this.grn_appr_cycle = grn_appr_cycle;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getGrn_lbl_print() {
		return grn_lbl_print;
	}

	public void setGrn_lbl_print(String grn_lbl_print) {
		this.grn_lbl_print = grn_lbl_print;
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

	public BigDecimal getRoundoff() {
		return roundoff;
	}

	public void setRoundoff(BigDecimal roundoff) {
		this.roundoff = roundoff;
	}

	public String getGcma_code() {
		return gcma_code;
	}

	public void setGcma_code(String gcma_code) {
		this.gcma_code = gcma_code;
	}

	public Date getGcma_appr_date() {
		return gcma_appr_date;
	}

	public void setGcma_appr_date(Date gcma_appr_date) {
		this.gcma_appr_date = gcma_appr_date;
	}

	public Date getGcma_expiry_date() {
		return gcma_expiry_date;
	}

	public void setGcma_expiry_date(Date gcma_expiry_date) {
		this.gcma_expiry_date = gcma_expiry_date;
	}

	public Long getReturn_from_fs_id() {
		return return_from_fs_id;
	}

	public void setReturn_from_fs_id(Long return_from_fs_id) {
		this.return_from_fs_id = return_from_fs_id;
	}

	public Long getGrn_type_id() {
		return grn_type_id;
	}

	public void setGrn_type_id(Long grn_type_id) {
		this.grn_type_id = grn_type_id;
	}

	public String getGrn_stock_sa_or_ns() {
		return grn_stock_sa_or_ns;
	}

	public void setGrn_stock_sa_or_ns(String grn_stock_sa_or_ns) {
		this.grn_stock_sa_or_ns = grn_stock_sa_or_ns;
	}

	public Long getGrn_stock_type_id() {
		return grn_stock_type_id;
	}

	public void setGrn_stock_type_id(Long grn_stock_type_id) {
		this.grn_stock_type_id = grn_stock_type_id;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public String getGrn_confirm() {
		return grn_confirm;
	}

	public void setGrn_confirm(String grn_confirm) {
		this.grn_confirm = grn_confirm;
	}

	@Override
	public String toString() {
		return "GrnHeader [grnId=" + grnId + ", grn_no=" + grn_no + ", grnd_sl_no=" + grnd_sl_no + ", grn_company="
				+ grn_company + ", grn_fin_year=" + grn_fin_year + ", grn_period_code=" + grn_period_code
				+ ", grn_loc_id=" + grn_loc_id + ", grn_dt=" + grn_dt + ", grn_supplier_id=" + grn_supplier_id
				+ ", grn_transfer_no=" + grn_transfer_no + ", grn_transfer_dt=" + grn_transfer_dt + ", grn_lr_no="
				+ grn_lr_no + ", grn_lr_dt=" + grn_lr_dt + ", grn_lorry_no=" + grn_lorry_no + ", grn_purchase_id="
				+ grn_purchase_id + ", grn_sending_loc_id=" + grn_sending_loc_id + ", grn_trf_type=" + grn_trf_type
				+ ", grn_trf_dt=" + grn_trf_dt + ", grn_in_transit=" + grn_in_transit + ", grn_auto_grn_dt="
				+ grn_auto_grn_dt + ", grn_auto_grn_auth=" + grn_auto_grn_auth + ", grn_locked_yn=" + grn_locked_yn
				+ ", grn_erp_grn_cd=" + grn_erp_grn_cd + ", grn_erp_stktfr_cd=" + grn_erp_stktfr_cd
				+ ", grn_delivery_no=" + grn_delivery_no + ", grn_f_form_no=" + grn_f_form_no
				+ ", grn_consignment_type=" + grn_consignment_type + ", grn_full_shippers=" + grn_full_shippers
				+ ", grn_loose_shippers=" + grn_loose_shippers + ", grn_weight=" + grn_weight + ", grn_volume="
				+ grn_volume + ", grn_transport_mode=" + grn_transport_mode + ", grn_octroi=" + grn_octroi
				+ ", grn_ins_usr_id=" + grn_ins_usr_id + ", grn_mod_usr_id=" + grn_mod_usr_id + ", grn_ins_dt="
				+ grn_ins_dt + ", grn_mod_dt=" + grn_mod_dt + ", grn_ins_ip_add=" + grn_ins_ip_add + ", grn_mod_ip_add="
				+ grn_mod_ip_add + ", grn_status=" + grn_status + ", grn_doctype_id=" + grn_doctype_id
				+ ", grn_appr_req=" + grn_appr_req + ", grn_appr_acq=" + grn_appr_acq + ", grn_appr_status="
				+ grn_appr_status + ", grn_po_no=" + grn_po_no + ", grn_po_date=" + grn_po_date + ", grn_total_value="
				+ grn_total_value + ", grn_appr_cycle=" + grn_appr_cycle + ", remarks=" + remarks + ", grn_lbl_print="
				+ grn_lbl_print + ", sgst_bill_amt=" + sgst_bill_amt + ", cgst_bill_amt=" + cgst_bill_amt
				+ ", igst_bill_amt=" + igst_bill_amt + ", gst_reverse_chg=" + gst_reverse_chg + ", gst_doc_type="
				+ gst_doc_type + ", text1=" + text1 + ", text2=" + text2 + ", value1=" + value1 + ", value2=" + value2
				+ ", roundoff=" + roundoff + ", gcma_code=" + gcma_code + ", gcma_appr_date=" + gcma_appr_date
				+ ", gcma_expiry_date=" + gcma_expiry_date + ", return_from_fs_id=" + return_from_fs_id
				+ ", grn_type_id=" + grn_type_id + ", grn_stock_sa_or_ns=" + grn_stock_sa_or_ns + ", grn_stock_type_id="
				+ grn_stock_type_id + ", dsp_challan_no=" + dsp_challan_no + ", grn_confirm=" + grn_confirm
				+ ", transporter_name=" + transporter_name + ", grn_total_value_pur=" + grn_total_value_pur
				+ ", roundoff_pr=" + roundoff_pr + "]";
	}
	
}
