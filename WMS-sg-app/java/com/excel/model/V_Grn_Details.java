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

@Entity
@Table(name = "V_GRN_DETAILS")
public class V_Grn_Details implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GRND_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long grndId;

	@Column(name = "GRND_GRN_ID")
	private Long grndGrnId;

	@Column(name = "GRND_PROD_ID")
	private Long grnd_prod_id;

	@Column(name = "GRND_BATCH_ID")
	private Long grnd_batch_id;

	@Column(name = "GRND_QTY")
	private BigDecimal grnd_qty;

	@Column(name = "GRND_RATE")
	private BigDecimal grnd_rate;

	@Column(name = "BATCH_NO")
	private String batch_no;

	@Column(name = "BATCH_NARRATION")
	private String batch_narration;

	@Column(name = "BATCH_MFG_DT_DISP")
	private String batch_mfg_dt_disp;

	@Column(name = "SMP_BATCH_RQ_IND")
	private String smp_batch_req_ind;
	
	@Column(name = "BATCH_EXPIRY_DT_DISP")
	private String batch_expiry_dt_disp;

	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;

	@Column(name = "SMP_PROD_CD")
	private String smp_prod_cd;

	@Column(name = "VALUE")
	private BigDecimal value;

	@Column(name = "GRND_NOOFBOXES")
	private Long grnd_noofboxes;

	@Column(name = "GRND_ReturnFrom")
	private String grnd_returnfrom;

	@Column(name = "GRND_ChallanNo")
	private String grnd_challanno;

	@Column(name = "SMP_PROD_TYPE_ID")
	private Long smp_prod_type_id;

	@Column(name = "SMP_PROD_TYPE")
	private String smp_prod_type;

	@Column(name = "prod_type_short_nm")
	private String prod_type_short_nm;
	
	@Column(name = "GRND_IMAGE_PATH")
	private String grnd_image_path;
	
	@Column(name="GRND_APPR_STATUS")
	private String grnd_appr_status;
	
	@Column(name="GRND_Bin_id")
	private String grnd_bin_id;
	
	@Column(name = "TEXT1")
	private String text1;
	
	@Column(name = "TEXT2")
	private String text2;
	
	@Column(name = "VALUE1")
	private BigDecimal value1;
	
	@Column(name = "VALUE2")
	private BigDecimal value2;
	
	@Column(name = "GCMA_NUMBER")
	private String gcma_number;
	
	@Column(name = "GCMA_APROVAL_DT")
	private Date gcma_aproval_dt;
	
	@Column(name = "GCMA_EXPIRY_DT")
	private Date gcma_expiry_dt;
	
	@Column(name = "GCMA_REQ_IND")
	private String gcma_req_ind;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "SHORT")
	private BigDecimal shortQty;
	
	@Column(name = "DAMAGE")
	private BigDecimal damage;
	
	@Column(name = "GRND_PUR_RATE")
	private BigDecimal grnd_pur_rate;
	
	@Column(name = "Value_PUR")
	private BigDecimal value_pur;
	
	
	public BigDecimal getGrnd_pur_rate() {
		return grnd_pur_rate;
	}

	public void setGrnd_pur_rate(BigDecimal grnd_pur_rate) {
		this.grnd_pur_rate = grnd_pur_rate;
	}

	public BigDecimal getValue_pur() {
		return value_pur;
	}

	public void setValue_pur(BigDecimal value_pur) {
		this.value_pur = value_pur;
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

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_narration() {
		return batch_narration;
	}

	public void setBatch_narration(String batch_narration) {
		this.batch_narration = batch_narration;
	}

	public String getBatch_mfg_dt_disp() {
		return batch_mfg_dt_disp;
	}

	public void setBatch_mfg_dt_disp(String batch_mfg_dt_disp) {
		this.batch_mfg_dt_disp = batch_mfg_dt_disp;
	}

	public String getBatch_expiry_dt_disp() {
		return batch_expiry_dt_disp;
	}

	public void setBatch_expiry_dt_disp(String batch_expiry_dt_disp) {
		this.batch_expiry_dt_disp = batch_expiry_dt_disp;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}

	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getGrnd_noofboxes() {
		return grnd_noofboxes;
	}

	public void setGrnd_noofboxes(Long grnd_noofboxes) {
		this.grnd_noofboxes = grnd_noofboxes;
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

	public Long getSmp_prod_type_id() {
		return smp_prod_type_id;
	}

	public void setSmp_prod_type_id(Long smp_prod_type_id) {
		this.smp_prod_type_id = smp_prod_type_id;
	}

	public String getSmp_prod_type() {
		return smp_prod_type;
	}

	public void setSmp_prod_type(String smp_prod_type) {
		this.smp_prod_type = smp_prod_type;
	}

	public String getProd_type_short_nm() {
		return prod_type_short_nm;
	}

	public void setProd_type_short_nm(String prod_type_short_nm) {
		this.prod_type_short_nm = prod_type_short_nm;
	}

	public String getGrnd_image_path() {
		return grnd_image_path;
	}

	public void setGrnd_image_path(String grnd_image_path) {
		this.grnd_image_path = grnd_image_path;
	}

	public String getGrnd_appr_status() {
		return grnd_appr_status;
	}

	public void setGrnd_appr_status(String grnd_appr_status) {
		this.grnd_appr_status = grnd_appr_status;
	}

	public String getGrnd_bin_id() {
		return grnd_bin_id;
	}

	public void setGrnd_bin_id(String grnd_bin_id) {
		this.grnd_bin_id = grnd_bin_id;
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

	public String getSmp_batch_req_ind() {
	    return smp_batch_req_ind;
	}

	public void setSmp_batch_req_ind(String smp_batch_req_ind) {
	    this.smp_batch_req_ind = smp_batch_req_ind;
	}

	public String getGcma_number() {
		return gcma_number;
	}

	public void setGcma_number(String gcma_number) {
		this.gcma_number = gcma_number;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getGcma_aproval_dt() {
		return gcma_aproval_dt;
	}

	public void setGcma_aproval_dt(Date gcma_aproval_dt) {
		this.gcma_aproval_dt = gcma_aproval_dt;
	}

	public Date getGcma_expiry_dt() {
		return gcma_expiry_dt;
	}

	public void setGcma_expiry_dt(Date gcma_expiry_dt) {
		this.gcma_expiry_dt = gcma_expiry_dt;
	}

	public BigDecimal getShortQty() {
		return shortQty;
	}

	public void setShortQty(BigDecimal shortQty) {
		this.shortQty = shortQty;
	}

	public BigDecimal getDamage() {
		return damage;
	}

	public void setDamage(BigDecimal damage) {
		this.damage = damage;
	}

}

