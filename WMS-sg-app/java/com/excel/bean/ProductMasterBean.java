package com.excel.bean;

import java.math.BigDecimal;

public class ProductMasterBean {
	
	private Long product_id;
	private String product_name;
	private String prod_cd;
	private String batch_no;
	private BigDecimal rate;
	private BigDecimal qty;
	private BigDecimal stock;
	private BigDecimal value;
	private Long mfg_dt;
	private Long exp_dt;
	private String promo_expiry_ind, batch_ind ,exp_ind;
	private Long batch_id;
	private Long detail_id;
	private Long div_id;
	private String narration;
	private Long cases;
	
	private String gcmaCode;
	private String gcmaInd;
	private Long gcmaApprDate;
	private Long gcmaExpDate;
	
	private Long pack_id;
	private String pack_disp_nm;
	
	private BigDecimal batch_costing_rate;
	
	
	public BigDecimal getBatch_costing_rate() {
		return batch_costing_rate;
	}
	public void setBatch_costing_rate(BigDecimal batch_costing_rate) {
		this.batch_costing_rate = batch_costing_rate;
	}
	
	public Long getPack_id() {
		return pack_id;
	}
	public void setPack_id(Long pack_id) {
		this.pack_id = pack_id;
	}
	public String getPack_disp_nm() {
		return pack_disp_nm;
	}
	public void setPack_disp_nm(String pack_disp_nm) {
		this.pack_disp_nm = pack_disp_nm;
	}
	public Long getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProd_cd() {
		return prod_cd;
	}
	public void setProd_cd(String prod_cd) {
		this.prod_cd = prod_cd;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Long getMfg_dt() {
		return mfg_dt;
	}
	public void setMfg_dt(Long mfg_dt) {
		this.mfg_dt = mfg_dt;
	}
	public Long getExp_dt() {
		return exp_dt;
	}
	public void setExp_dt(Long exp_dt) {
		this.exp_dt = exp_dt;
	}
	public String getPromo_expiry_ind() {
		return promo_expiry_ind;
	}
	public void setPromo_expiry_ind(String promo_expiry_ind) {
		this.promo_expiry_ind = promo_expiry_ind;
	}
	public String getBatch_ind() {
		return batch_ind;
	}
	public void setBatch_ind(String batch_ind) {
		this.batch_ind = batch_ind;
	}
	public String getExp_ind() {
		return exp_ind;
	}
	public void setExp_ind(String exp_ind) {
		this.exp_ind = exp_ind;
	}
	public Long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(Long batch_id) {
		this.batch_id = batch_id;
	}
	public Long getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}
	public Long getDiv_id() {
		return div_id;
	}
	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public Long getCases() {
		return cases;
	}
	public void setCases(Long cases) {
		this.cases = cases;
	}
	public String getGcmaCode() {
		return gcmaCode;
	}
	public void setGcmaCode(String gcmaCode) {
		this.gcmaCode = gcmaCode;
	}
	public String getGcmaInd() {
		return gcmaInd;
	}
	public void setGcmaInd(String gcmaInd) {
		this.gcmaInd = gcmaInd;
	}
	public Long getGcmaApprDate() {
		return gcmaApprDate;
	}
	public void setGcmaApprDate(Long gcmaApprDate) {
		this.gcmaApprDate = gcmaApprDate;
	}
	public Long getGcmaExpDate() {
		return gcmaExpDate;
	}
	public void setGcmaExpDate(Long gcmaExpDate) {
		this.gcmaExpDate = gcmaExpDate;
	}


}
