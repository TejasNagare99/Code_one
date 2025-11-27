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
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name="GRN_DETAILS")
@DynamicUpdate(value=true)
public class GrnDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4193129534055118225L;

	@Id
	@Column(name="GRND_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long grndId;
	
	@Column(name="GRND_GRN_ID")
	private Long grndGrnId;
	
	@Column(name="GRND_SL_NO")
	private Long grnd_sl_no;

	@Column(name="GRND_COMPANY")
	private String grnd_company;

	@Column(name="GRND_LOC_ID")
	private Long grnd_loc_id;

	@Column(name="GRND_FIN_YEAR")
	private String grnd_fin_year;

	@Column(name="GRND_PERIOD_CODE")
	private String grnd_period_code;

	@Column(name="GRND_DIV_ID")
	private Long grnd_div_id;

	@Column(name="GRND_PROD_ID")
	private Long grnd_prod_id;

	@Column(name="GRND_BATCH_ID")
	private Long grnd_batch_id;

	@Column(name="GRND_QTY")
	private BigDecimal grnd_qty;

	@Column(name="GRND_RATE")
	private BigDecimal grnd_rate;

	@Column(name="GRND_ins_usr_id")
	private String grnd_ins_usr_id;

	@Column(name="GRND_mod_usr_id")
	private String grnd_mod_usr_id;

	@Column(name="GRND_ins_dt")
	private Date grnd_ins_dt;

	@Column(name="GRND_mod_dt")
	private Date grnd_mod_dt;

	@Column(name="GRND_ins_ip_add")
	private String grnd_ins_ip_add;

	@Column(name="GRND_mod_ip_add")
	private String grnd_mod_ip_add;

	@Column(name="GRND_status")
	private String grnd_status;

	@Column(name="GRND_ReturnFrom")
	private String grnd_returnfrom;

	@Column(name="GRND_ChallanNo")
	private String grnd_challanno;

	@Column(name="GRND_NOOFBOXES")
	private Long grnd_noofboxes;

	@Column(name="GRND_VALUE")
	private BigDecimal grnd_value;

	@Column(name="GRND_IMAGE_PATH")
	private String grnd_image_path;

	@Column(name="GRND_BIN_ID")
	private Long grnd_bin_id;

	@Column(name="GRND_APPR_STATUS")
	private String grnd_appr_status;

	@Column(name="GRND_IMAGE_MOVED")
	private String grnd_image_moved;

	@Column(name="SGST_RATE")
	private BigDecimal sgst_rate;

	@Column(name="SGST_BILL_AMT")
	private BigDecimal sgst_bill_amt;

	@Column(name="CGST_RATE")
	private BigDecimal cgst_rate;

	@Column(name="cgst_bill_amt")
	private BigDecimal CGST_BILL_AMT;

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
	
	@Column(name="STOCK_TYPE")
	private String stock_type;

	@Column(name="GCMA_NUMBER")
	private String gcma_code;
	
	@Column(name="GCMA_APROVAL_DT")
	private Date gcma_appr_date;
	
	@Column(name="GCMA_EXPIRY_DT")
	private Date gcma_expiry_date;
	
	@Column(name="GCMA_REQ_IND")
	private String gcma_req_ind;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name = "GRND_PUR_RATE")
	private BigDecimal grnd_pur_rate;
	
	@Column(name = "GRND_VALUE_PUR")
	private BigDecimal grnd_value_pur;
	
	@Column(name="STOCK_TYPE_ID")
	private long stock_type_id;
	
	
	
	public long getStock_type_id() {
		return stock_type_id;
	}

	public void setStock_type_id(long stock_type_id) {
		this.stock_type_id = stock_type_id;
	}

	public BigDecimal getGrnd_value_pur() {
		return grnd_value_pur;
	}

	public void setGrnd_value_pur(BigDecimal grnd_value_pur) {
		this.grnd_value_pur = grnd_value_pur;
	}

	public BigDecimal getGrnd_pur_rate() {
		return grnd_pur_rate;
	}

	public void setGrnd_pur_rate(BigDecimal grnd_pur_rate) {
		this.grnd_pur_rate = grnd_pur_rate;
	}

	public Long getGrndId() {
		return grndId;
	}

	public void setGrndId(Long grndId) {
		this.grndId = grndId;
	}

	public Long getGrndGrnId() {
		return grndGrnId;
	}

	public void setGrndGrnId(Long grndGrnId) {
		this.grndGrnId = grndGrnId;
	}

	public Long getGrnd_sl_no() {
		return grnd_sl_no;
	}

	public void setGrnd_sl_no(Long grnd_sl_no) {
		this.grnd_sl_no = grnd_sl_no;
	}

	public String getGrnd_company() {
		return grnd_company;
	}

	public void setGrnd_company(String grnd_company) {
		this.grnd_company = grnd_company;
	}

	public Long getGrnd_loc_id() {
		return grnd_loc_id;
	}

	public void setGrnd_loc_id(Long grnd_loc_id) {
		this.grnd_loc_id = grnd_loc_id;
	}

	public String getGrnd_fin_year() {
		return grnd_fin_year;
	}

	public void setGrnd_fin_year(String grnd_fin_year) {
		this.grnd_fin_year = grnd_fin_year;
	}

	public String getGrnd_period_code() {
		return grnd_period_code;
	}

	public void setGrnd_period_code(String grnd_period_code) {
		this.grnd_period_code = grnd_period_code;
	}

	public Long getGrnd_div_id() {
		return grnd_div_id;
	}

	public void setGrnd_div_id(Long grnd_div_id) {
		this.grnd_div_id = grnd_div_id;
	}

	public Long getGrnd_prod_id() {
		return grnd_prod_id;
	}

	public void setGrnd_prod_id(Long grnd_prod_id) {
		this.grnd_prod_id = grnd_prod_id;
	}

	public Long getGrnd_batch_id() {
		return grnd_batch_id;
	}

	public void setGrnd_batch_id(Long grnd_batch_id) {
		this.grnd_batch_id = grnd_batch_id;
	}

	public BigDecimal getGrnd_qty() {
		return grnd_qty;
	}

	public void setGrnd_qty(BigDecimal grnd_qty) {
		this.grnd_qty = grnd_qty;
	}

	public BigDecimal getGrnd_rate() {
		return grnd_rate;
	}

	public void setGrnd_rate(BigDecimal grnd_rate) {
		this.grnd_rate = grnd_rate;
	}

	public String getGrnd_ins_usr_id() {
		return grnd_ins_usr_id;
	}

	public void setGrnd_ins_usr_id(String grnd_ins_usr_id) {
		this.grnd_ins_usr_id = grnd_ins_usr_id;
	}

	public String getGrnd_mod_usr_id() {
		return grnd_mod_usr_id;
	}

	public void setGrnd_mod_usr_id(String grnd_mod_usr_id) {
		this.grnd_mod_usr_id = grnd_mod_usr_id;
	}

	public Date getGrnd_ins_dt() {
		return grnd_ins_dt;
	}

	public void setGrnd_ins_dt(Date grnd_ins_dt) {
		this.grnd_ins_dt = grnd_ins_dt;
	}

	public Date getGrnd_mod_dt() {
		return grnd_mod_dt;
	}

	public void setGrnd_mod_dt(Date grnd_mod_dt) {
		this.grnd_mod_dt = grnd_mod_dt;
	}

	public String getGrnd_ins_ip_add() {
		return grnd_ins_ip_add;
	}

	public void setGrnd_ins_ip_add(String grnd_ins_ip_add) {
		this.grnd_ins_ip_add = grnd_ins_ip_add;
	}

	public String getGrnd_mod_ip_add() {
		return grnd_mod_ip_add;
	}

	public void setGrnd_mod_ip_add(String grnd_mod_ip_add) {
		this.grnd_mod_ip_add = grnd_mod_ip_add;
	}

	public String getGrnd_status() {
		return grnd_status;
	}

	public void setGrnd_status(String grnd_status) {
		this.grnd_status = grnd_status;
	}

	public String getGrnd_returnfrom() {
		return grnd_returnfrom;
	}

	public void setGrnd_returnfrom(String grnd_returnfrom) {
		this.grnd_returnfrom = grnd_returnfrom;
	}

	public String getGrnd_challanno() {
		return grnd_challanno;
	}

	public void setGrnd_challanno(String grnd_challanno) {
		this.grnd_challanno = grnd_challanno;
	}

	public Long getGrnd_noofboxes() {
		return grnd_noofboxes;
	}

	public void setGrnd_noofboxes(Long grnd_noofboxes) {
		this.grnd_noofboxes = grnd_noofboxes;
	}

	public BigDecimal getGrnd_value() {
		return grnd_value;
	}

	public void setGrnd_value(BigDecimal grnd_value) {
		this.grnd_value = grnd_value;
	}

	public String getGrnd_image_path() {
		return grnd_image_path;
	}

	public void setGrnd_image_path(String grnd_image_path) {
		this.grnd_image_path = grnd_image_path;
	}

	public Long getGrnd_bin_id() {
		return grnd_bin_id;
	}

	public void setGrnd_bin_id(Long grnd_bin_id) {
		this.grnd_bin_id = grnd_bin_id;
	}

	public String getGrnd_appr_status() {
		return grnd_appr_status;
	}

	public void setGrnd_appr_status(String grnd_appr_status) {
		this.grnd_appr_status = grnd_appr_status;
	}

	public String getGrnd_image_moved() {
		return grnd_image_moved;
	}

	public void setGrnd_image_moved(String grnd_image_moved) {
		this.grnd_image_moved = grnd_image_moved;
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

	public BigDecimal getCGST_BILL_AMT() {
		return CGST_BILL_AMT;
	}

	public void setCGST_BILL_AMT(BigDecimal cGST_BILL_AMT) {
		CGST_BILL_AMT = cGST_BILL_AMT;
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

	public GrnDetails() {
		super();
	}

	public GrnDetails(BigDecimal grnd_value) {
		super();
		this.grnd_value = grnd_value;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
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

	public String getGcma_req_ind() {
		return gcma_req_ind;
	}

	public void setGcma_req_ind(String gcma_req_ind) {
		this.gcma_req_ind = gcma_req_ind;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}

