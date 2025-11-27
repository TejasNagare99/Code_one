package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "SUM_DISP_HEADER")

public class Sum_Disp_Header implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3301035281602778629L;

	@Id
	@Column(name = "SUMDSP_ID")
	private Long sumdsp_id;
	
	@ManyToOne
	@JoinColumn(name="SUMDSP_LOC_ID")
	private Location sumdsp_loc_id;
	
	@Column(name="SUMDSP_RECVG_LOC_ID")
	private Long sumdsp_recvg_loc_id;
	
	@Column(name="SUMDSP_FIN_YEAR")
	private Integer sumdsp_fin_year;
	
	@Column(name="SUMDSP_PERIOD_CODE")
	private String sumdsp_period_code;
	
	@Column(name="SUMDSP_CHALLAN_DT")
	private Date sumdsp_challan_dt;
	
	@Column(name="SUMDSP_COMPANY")
	private String sumdsp_company;
	
	@Column(name = "SUMDSP_CHALLAN_NO")
	private String sumdsp_challan_no;

	@ManyToOne
	@JoinColumn(name = "SUMDSP_DIV_ID")
	private DivMaster sumdspdiv_id; 
	
	@Column(name="SUMDSP_LR_NO")
	private String sumdsp_lr_no;
	
	@Column(name="SUMDSP_LR_DT")
	private Date sumdsp_lr_dt;
	@Column(name="SUMDSP_TRANSPORTER")
	private String sumdsp_transporter;
	
	@Column(name="SUMDSP_WT")
	private BigDecimal sumdsp_wt;
	
	@Column(name="SUMDSP_TOTWT")
	private BigDecimal sumdsp_totwt;
	
	@Column(name="SUMDSP_CASES")
	private int sumdsp_cases;
	
	@Column(name="SUMDSP_TOTCASES")
	private int sumdsp_totcases;
	
	@Column(name="SUMDSP_DELIVERY_DATE")
	private Date sumdsp_delivery_date;
	
	@Column(name="SUMDSP_RECD_BY")
	private String sumdsp_recd_by;
	
	@Column(name="SUMDSP_REMARK")
	private String sumdsp_remark;
	
	@Column(name="SUMDSP_status")
	private String sumdsp_status;
	
	@Column(name="SUMDSP_ins_usr_id")
	private String sumdsp_ins_usr_id;
	
	@Column(name="SGST_BILL_AMT")
	private BigDecimal SGST_BILL_AMT;

	@Column(name="CGST_BILL_AMT")
	private BigDecimal CGST_BILL_AMT;
	
	@Column(name="IGST_BILL_AMT")
	private BigDecimal IGST_BILL_AMT;
	
	@Column(name="GST_DOC_TYPE")
	private String gst_doc_type;
	
	@Column(name="SUMDSP_TOTAL_GOODS_VAL")
	private BigDecimal sumdsp_total_goods_val;
	
	@Column(name="roundoff")
	private BigDecimal roundoff;
	
	@Column(name="SUMDSP_MOD_DT")
	private Date sumdsp_mod_dt;
	
	@Column(name="COURIER_EXPENSES")
	private BigDecimal courier_expenses;
	
	@Column(name="ACTUAL_DELIVERY_DATE")
	private Date actual_delivery_date;
	
	@Column(name="DELIVERY_DETAIL_MODIFY_DATE")
	private Date delivery_detail_modify_date;
	
	@Column(name="WAY_BILL_NO")
	private String way_bill_no;
	
	@Column(name="SUMDSP_mod_ip_add")
	private String sumdsp_mod_ip_add;
	
	@Column(name="SUMDSP_mod_usr_id")
	private String sumdsp_mod_usr_id;
	
	@Column(name="SUMDSP_LORRY_NO")
	private String sumdsp_lorry_no;
	
	@Column(name="TRANS_GST_REG_NO")
	private String trans_gst_reg_no;
	
	@Column(name="SUMDSP_DESTINATION")
	private String sumdsp_destination;
	
	@Column(name="transport_mode")
	private String transport_mode;
	
	public String getTransport_mode() {
		return transport_mode;
	}

	public void setTransport_mode(String transport_mode) {
		this.transport_mode = transport_mode;
	}
	
	public String getSumdsp_destination() {
		return sumdsp_destination;
	}

	public void setSumdsp_destination(String sumdsp_destination) {
		this.sumdsp_destination = sumdsp_destination;
	}

	public String getSumdsp_lorry_no() {
		return sumdsp_lorry_no;
	}

	public void setSumdsp_lorry_no(String sumdsp_lorry_no) {
		this.sumdsp_lorry_no = sumdsp_lorry_no;
	}

	public String getTrans_gst_reg_no() {
		return trans_gst_reg_no;
	}

	public void setTrans_gst_reg_no(String trans_gst_reg_no) {
		this.trans_gst_reg_no = trans_gst_reg_no;
	}

	public String getSumdsp_mod_usr_id() {
		return sumdsp_mod_usr_id;
	}

	public void setSumdsp_mod_usr_id(String sumdsp_mod_usr_id) {
		this.sumdsp_mod_usr_id = sumdsp_mod_usr_id;
	}

	public String getSumdsp_mod_ip_add() {
		return sumdsp_mod_ip_add;
	}

	public void setSumdsp_mod_ip_add(String sumdsp_mod_ip_add) {
		this.sumdsp_mod_ip_add = sumdsp_mod_ip_add;
	}

	public String getWay_bill_no() {
		return way_bill_no;
	}

	public void setWay_bill_no(String way_bill_no) {
		this.way_bill_no = way_bill_no;
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

	/**
	 * @return the sumdsp_delivery_date
	 */
	public Date getSumdsp_delivery_date() {
		return sumdsp_delivery_date;
	}

	/**
	 * @param sumdsp_delivery_date the sumdsp_delivery_date to set
	 */
	public void setSumdsp_delivery_date(Date sumdsp_delivery_date) {
		this.sumdsp_delivery_date = sumdsp_delivery_date;
	}

	/**
	 * @return the sumdsp_recd_by
	 */
	public String getSumdsp_recd_by() {
		return sumdsp_recd_by;
	}

	/**
	 * @param sumdsp_recd_by the sumdsp_recd_by to set
	 */
	public void setSumdsp_recd_by(String sumdsp_recd_by) {
		this.sumdsp_recd_by = sumdsp_recd_by;
	}

	/**
	 * @return the sumdsp_remark
	 */
	public String getSumdsp_remark() {
		return sumdsp_remark;
	}

	/**
	 * @param sumdsp_remark the sumdsp_remark to set
	 */
	public void setSumdsp_remark(String sumdsp_remark) {
		this.sumdsp_remark = sumdsp_remark;
	}

	public Location getSumdsp_loc_id() {
		return sumdsp_loc_id;
	}

	public void setSumdsp_loc_id(Location sumdsp_loc_id) {
		this.sumdsp_loc_id = sumdsp_loc_id;
	}

	public Integer getSumdsp_fin_year() {
		return sumdsp_fin_year;
	}

	public void setSumdsp_fin_year(Integer sumdsp_fin_year) {
		this.sumdsp_fin_year = sumdsp_fin_year;
	}

	public String getSumdsp_period_code() {
		return sumdsp_period_code;
	}

	public void setSumdsp_period_code(String sumdsp_period_code) {
		this.sumdsp_period_code = sumdsp_period_code;
	}

	public Date getSumdsp_challan_dt() {
		return sumdsp_challan_dt;
	}

	public void setSumdsp_challan_dt(Date sumdsp_challan_dt) {
		this.sumdsp_challan_dt = sumdsp_challan_dt;
	}

	public String getSumdsp_company() {
		return sumdsp_company;
	}

	public void setSumdsp_company(String sumdsp_company) {
		this.sumdsp_company = sumdsp_company;
	}

	public Long getSumdsp_id() {
		return sumdsp_id;
	}

	public void setSumdsp_id(Long sumdsp_id) {
		this.sumdsp_id = sumdsp_id;
	}

	public String getSumdsp_challan_no() {
		return sumdsp_challan_no;
	}

	public void setSumdsp_challan_no(String sumdsp_challan_no) {
		this.sumdsp_challan_no = sumdsp_challan_no;
	}

	public DivMaster getSumdspdiv_id() {
		return sumdspdiv_id;
	}

	public void setSumdspdiv_id(DivMaster sumdspdiv_id) {
		this.sumdspdiv_id = sumdspdiv_id;
	}

	public String getSumdsp_lr_no() {
		return sumdsp_lr_no;
	}

	public void setSumdsp_lr_no(String sumdsp_lr_no) {
		this.sumdsp_lr_no = sumdsp_lr_no;
	}

	public Date getSumdsp_lr_dt() {
		return sumdsp_lr_dt;
	}

	public void setSumdsp_lr_dt(Date sumdsp_lr_dt) {
		this.sumdsp_lr_dt = sumdsp_lr_dt;
	}

	public String getSumdsp_transporter() {
		return sumdsp_transporter;
	}

	public void setSumdsp_transporter(String sumdsp_transporter) {
		this.sumdsp_transporter = sumdsp_transporter;
	}

	public BigDecimal getSumdsp_wt() {
		return sumdsp_wt;
	}

	public void setSumdsp_wt(BigDecimal sumdsp_wt) {
		this.sumdsp_wt = sumdsp_wt;
	}

	public BigDecimal getSumdsp_totwt() {
		return sumdsp_totwt;
	}

	public void setSumdsp_totwt(BigDecimal sumdsp_totwt) {
		this.sumdsp_totwt = sumdsp_totwt;
	}

	public int getSumdsp_cases() {
		return sumdsp_cases;
	}

	public void setSumdsp_cases(int sumdsp_cases) {
		this.sumdsp_cases = sumdsp_cases;
	}

	public int getSumdsp_totcases() {
		return sumdsp_totcases;
	}

	public void setSumdsp_totcases(int sumdsp_totcases) {
		this.sumdsp_totcases = sumdsp_totcases;
	}

	public String getSumdsp_status() {
		return sumdsp_status;
	}

	public void setSumdsp_status(String sumdsp_status) {
		this.sumdsp_status = sumdsp_status;
	}

	public String getSumdsp_ins_usr_id() {
		return sumdsp_ins_usr_id;
	}

	public void setSumdsp_ins_usr_id(String sumdsp_ins_usr_id) {
		this.sumdsp_ins_usr_id = sumdsp_ins_usr_id;
	}

	public BigDecimal getSGST_BILL_AMT() {
		return SGST_BILL_AMT;
	}

	public void setSGST_BILL_AMT(BigDecimal sGST_BILL_AMT) {
		SGST_BILL_AMT = sGST_BILL_AMT;
	}

	public BigDecimal getCGST_BILL_AMT() {
		return CGST_BILL_AMT;
	}

	public void setCGST_BILL_AMT(BigDecimal cGST_BILL_AMT) {
		CGST_BILL_AMT = cGST_BILL_AMT;
	}

	public BigDecimal getIGST_BILL_AMT() {
		return IGST_BILL_AMT;
	}

	public void setIGST_BILL_AMT(BigDecimal iGST_BILL_AMT) {
		IGST_BILL_AMT = iGST_BILL_AMT;
	}

	public String getGst_doc_type() {
		return gst_doc_type;
	}

	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}

	public Long getSumdsp_recvg_loc_id() {
		return sumdsp_recvg_loc_id;
	}

	public void setSumdsp_recvg_loc_id(Long sumdsp_recvg_loc_id) {
		this.sumdsp_recvg_loc_id = sumdsp_recvg_loc_id;
	}

	public BigDecimal getSumdsp_total_goods_val() {
		return sumdsp_total_goods_val;
	}

	public void setSumdsp_total_goods_val(BigDecimal sumdsp_total_goods_val) {
		this.sumdsp_total_goods_val = sumdsp_total_goods_val;
	}

	public BigDecimal getRoundoff() {
		return roundoff;
	}

	public void setRoundoff(BigDecimal roundoff) {
		this.roundoff = roundoff;
	}

	public java.util.Date getSumdsp_mod_dt() {
		return sumdsp_mod_dt;
	}

	public void setSumdsp_mod_dt(java.util.Date sumdsp_mod_dt) {
		this.sumdsp_mod_dt = sumdsp_mod_dt;
	}
	
}
