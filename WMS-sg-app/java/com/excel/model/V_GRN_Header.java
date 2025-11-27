package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_GRN_HEADER")
public class V_GRN_Header  {

	@Id
	@Column(name="GRN_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long grn_id;
	
	@Column(name="GRN_DT_DISP")
	private String grn_dt_disp;
	
	@Column(name="GRN_NO")
	private String grn_no;
	
	@Column(name="GRN_LR_NO")
	private String grn_lr_no;
	
	@Column(name="GRN_LR_DT_DISP")
	private String grn_lr_dt_disp;
	
	@Column(name="trantype")
	private String trantype;
	
	@Column(name="GRN_LOC_ID")
	private Long grn_loc_id;
	
	@Column(name="GRN_status")
	private String grn_status;
	
	@Column(name="GRN_COMPANY")
	private String grn_company;
	
	@Column(name="GRN_TRANSFER_DT_DISP")
	private String grn_transfer_dt_disp;
	
	@Column(name="suptype_nm")
	private String suptype_nm;
	
	@Column(name="sup_id")
	private Long sup_id;
	
	@Column(name="GRN_TRANSFER_NO")
	private String grn_transfer_no;
	
	@Column(name="GRN_LORRY_NO")
	private String grn_lorry_no;
	
	@Column(name="sup_type")
	private String sup_type;
	
	@Column(name="GRN_DocType_id")
	private Long grn_doctype_id;
	
	@Column(name="sup_nm")
	private String sup_nm;
	
	@Column(name="GRN_OCTROI")
	private BigDecimal grn_octroi;
	
	@Column(name="GRN_ins_usr_id")
	private String grnInsUsrId;
	
	@Column(name="GRN_APPR_STATUS")
	private String grn_appr_status;
	
	@Column(name="GRN_APPR_REQ")
	private Long grn_appr_req;
	
	@Column(name="GRN_APPR_ACQ")
	private Long grn_appr_acq;
	
	@Column(name="GRN_PO_NO")
	private String grn_po_no;
	
	@Column(name="GRN_PO_DATE")
	private String grn_po_date;
	
	@Column(name="GRN_DT")
	private Date grn_dt;
	
	@Column(name="GRN_APPR_CYCLE")
	private Long grn_appr_cycle;
	
	@Column(name="USER_NAME")
	private String user_name;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="GRN_PERIOD_CODE")
	private String grn_period_code;
	
	@Column(name="GRN_FIN_YEAR")
	private String grn_fin_year;
	
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
	
	@Column(name="GRN_TOTAL_VALUE")
	private BigDecimal grn_total_value;
	
	@Column(name="GRN_STOCK_SA_OR_NS")
	private String grn_stock_sa_or_ns;
	
	@Column(name="GRN_STOCK_TYPE_ID")
	private Long grn_stock_type_id;
	
	@Column(name="GRN_CONFIRM")
	private String grn_confirm;
	
	@Column(name = "TRANSPORTER_NAME")
	private String transporter_name;
	
	@Column(name="GRN_TOTAL_VALUE_PUR")
	private BigDecimal grn_total_value_pur;
	
	@Column(name = "GRN_REF_NO")
	private String grn_ref_no;
	
	@Column(name = "VEHICLE_RECD_TIME")
	private String vehicle_recd_time;
	
	@Column(name = "GRN_ins_dt")
	private String grn_ins_dt;
	
	public String getGrn_ins_dt() {
		return grn_ins_dt;
	}

	public void setGrn_ins_dt(String grn_ins_dt) {
		this.grn_ins_dt = grn_ins_dt;
	}

	public String getVehicle_recd_time() {
		return vehicle_recd_time;
	}

	public void setVehicle_recd_time(String vehicle_recd_time) {
		this.vehicle_recd_time = vehicle_recd_time;
	}
	
	public String getGrn_ref_no() {
		return grn_ref_no;
	}

	public void setGrn_ref_no(String grn_ref_no) {
		this.grn_ref_no = grn_ref_no;
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

	public Long getGrn_id() {
		return grn_id;
	}

	public void setGrn_id(Long grn_id) {
		this.grn_id = grn_id;
	}

	public String getGrn_dt_disp() {
		return grn_dt_disp;
	}

	public void setGrn_dt_disp(String grn_dt_disp) {
		this.grn_dt_disp = grn_dt_disp;
	}

	public String getGrn_no() {
		return grn_no;
	}

	public void setGrn_no(String grn_no) {
		this.grn_no = grn_no;
	}

	public String getGrn_lr_no() {
		return grn_lr_no;
	}

	public void setGrn_lr_no(String grn_lr_no) {
		this.grn_lr_no = grn_lr_no;
	}

	public String getGrn_lr_dt_disp() {
		return grn_lr_dt_disp;
	}

	public void setGrn_lr_dt_disp(String grn_lr_dt_disp) {
		this.grn_lr_dt_disp = grn_lr_dt_disp;
	}

	public String getTrantype() {
		return trantype;
	}

	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

	public Long getGrn_loc_id() {
		return grn_loc_id;
	}

	public void setGrn_loc_id(Long grn_loc_id) {
		this.grn_loc_id = grn_loc_id;
	}

	public String getGrn_status() {
		return grn_status;
	}

	public void setGrn_status(String grn_status) {
		this.grn_status = grn_status;
	}

	public String getGrn_company() {
		return grn_company;
	}

	public void setGrn_company(String grn_company) {
		this.grn_company = grn_company;
	}

	public String getGrn_transfer_dt_disp() {
		return grn_transfer_dt_disp;
	}

	public void setGrn_transfer_dt_disp(String grn_transfer_dt_disp) {
		this.grn_transfer_dt_disp = grn_transfer_dt_disp;
	}

	public String getSuptype_nm() {
		return suptype_nm;
	}

	public void setSuptype_nm(String suptype_nm) {
		this.suptype_nm = suptype_nm;
	}

	public Long getSup_id() {
		return sup_id;
	}

	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	public String getGrn_transfer_no() {
		return grn_transfer_no;
	}

	public void setGrn_transfer_no(String grn_transfer_no) {
		this.grn_transfer_no = grn_transfer_no;
	}

	public String getGrn_lorry_no() {
		return grn_lorry_no;
	}

	public void setGrn_lorry_no(String grn_lorry_no) {
		this.grn_lorry_no = grn_lorry_no;
	}

	public String getSup_type() {
		return sup_type;
	}

	public void setSup_type(String sup_type) {
		this.sup_type = sup_type;
	}

	public Long getGrn_doctype_id() {
		return grn_doctype_id;
	}

	public void setGrn_doctype_id(Long grn_doctype_id) {
		this.grn_doctype_id = grn_doctype_id;
	}

	public String getSup_nm() {
		return sup_nm;
	}

	public void setSup_nm(String sup_nm) {
		this.sup_nm = sup_nm;
	}

	public BigDecimal getGrn_octroi() {
		return grn_octroi;
	}

	public void setGrn_octroi(BigDecimal grn_octroi) {
		this.grn_octroi = grn_octroi;
	}

	

	public String getGrnInsUsrId() {
		return grnInsUsrId;
	}

	public void setGrnInsUsrId(String grnInsUsrId) {
		this.grnInsUsrId = grnInsUsrId;
	}

	public String getGrn_appr_status() {
		return grn_appr_status;
	}

	public void setGrn_appr_status(String grn_appr_status) {
		this.grn_appr_status = grn_appr_status;
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

	public String getGrn_po_no() {
		return grn_po_no;
	}

	public void setGrn_po_no(String grn_po_no) {
		this.grn_po_no = grn_po_no;
	}

	public String getGrn_po_date() {
		return grn_po_date;
	}

	public void setGrn_po_date(String grn_po_date) {
		this.grn_po_date = grn_po_date;
	}

	public Date getGrn_dt() {
		return grn_dt;
	}

	public void setGrn_dt(Date grn_dt) {
		this.grn_dt = grn_dt;
	}

	public Long getGrn_appr_cycle() {
		return grn_appr_cycle;
	}

	public void setGrn_appr_cycle(Long grn_appr_cycle) {
		this.grn_appr_cycle = grn_appr_cycle;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getGrn_period_code() {
		return grn_period_code;
	}

	public void setGrn_period_code(String grn_period_code) {
		this.grn_period_code = grn_period_code;
	}

	public String getGrn_fin_year() {
		return grn_fin_year;
	}

	public void setGrn_fin_year(String grn_fin_year) {
		this.grn_fin_year = grn_fin_year;
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

	public BigDecimal getGrn_total_value() {
		return grn_total_value;
	}

	public void setGrn_total_value(BigDecimal grn_total_value) {
		this.grn_total_value = grn_total_value;
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

	public String getGrn_confirm() {
		return grn_confirm;
	}

	public void setGrn_confirm(String grn_confirm) {
		this.grn_confirm = grn_confirm;
	}
	

}
