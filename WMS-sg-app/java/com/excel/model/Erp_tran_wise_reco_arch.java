package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRAN_WISE_RECO_ARCH")
public class Erp_tran_wise_reco_arch {

	@Id
	@Column(name = "SLNO")
	private Long Slno;
	
	@Column(name = "RECO_FROM")
	private String reco_from;
	
	@Column(name = "RECO_TO")
	private String reco_to;
	
	@Column(name = "ORGANIZATION")
	private String organization;
	
	@Column(name = "TRAN_ID")
	private Long tran_id;
	
	@Column(name = "TRAN_TYPE")
	private String tran_type;
	
	@Column(name = "TRANSACTION_NO")
	private String transaction_no;
	
	@Column(name = "TRAN_DATE")
	private String tran_date;
	
	@Column(name = "PROD_CODE")
	private String prod_code;
	
	@Column(name = "PRODUCT_NAME")
	private String product_name;
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "IN_STOCK_TYPE")
	private String in_stock_type;
	
	@Column(name = "IN_QTY")
	private BigDecimal in_qty;
	
	@Column(name = "OUT_STOCK_TYPE")
	private String out_stock_type;
	
	@Column(name = "OUT_QTY")
	private BigDecimal out_qty;
	
	@Column(name = "MED_TRAN_ID")
	private Long med_tran_id;
	
	@Column(name = "MED_TRAN_TYPE")
	private String med_tran_type;
	
	@Column(name = "MED_TRANSACTION_NO")
	private String med_transaction_no;
	
	@Column(name = "MED_TRAN_DATE")
	private String med_tran_date;
	
	@Column(name = "MED_PROD_CODE")
	private String med_prod_code;
	
	@Column(name = "MED_BATCH_NO")
	private String med_batch_no;
	
	@Column(name = "MED_IN_STK_TYPE")
	private String med_in_stk_type;
	
	@Column(name = "MED_IN_QTY")
	private BigDecimal med_in_qty;
	
	@Column(name = "MED_OUT_STK_TYPE")
	private String med_out_stk_type;
	
	@Column(name = "MED_OUT_QTY")
	private BigDecimal med_out_qty;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;

	public Long getSlno() {
		return Slno;
	}

	public void setSlno(Long slno) {
		Slno = slno;
	}

	public String getReco_from() {
		return reco_from;
	}

	public void setReco_from(String reco_from) {
		this.reco_from = reco_from;
	}

	public String getReco_to() {
		return reco_to;
	}

	public void setReco_to(String reco_to) {
		this.reco_to = reco_to;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Long getTran_id() {
		return tran_id;
	}

	public void setTran_id(Long tran_id) {
		this.tran_id = tran_id;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public String getTransaction_no() {
		return transaction_no;
	}

	public void setTransaction_no(String transaction_no) {
		this.transaction_no = transaction_no;
	}

	public String getTran_date() {
		return tran_date;
	}

	public void setTran_date(String tran_date) {
		this.tran_date = tran_date;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getIn_stock_type() {
		return in_stock_type;
	}

	public void setIn_stock_type(String in_stock_type) {
		this.in_stock_type = in_stock_type;
	}

	public BigDecimal getIn_qty() {
		return in_qty;
	}

	public void setIn_qty(BigDecimal in_qty) {
		this.in_qty = in_qty;
	}

	public String getOut_stock_type() {
		return out_stock_type;
	}

	public void setOut_stock_type(String out_stock_type) {
		this.out_stock_type = out_stock_type;
	}

	public BigDecimal getOut_qty() {
		return out_qty;
	}

	public void setOut_qty(BigDecimal out_qty) {
		this.out_qty = out_qty;
	}

	public Long getMed_tran_id() {
		return med_tran_id;
	}

	public void setMed_tran_id(Long med_tran_id) {
		this.med_tran_id = med_tran_id;
	}

	public String getMed_tran_type() {
		return med_tran_type;
	}

	public void setMed_tran_type(String med_tran_type) {
		this.med_tran_type = med_tran_type;
	}

	public String getMed_transaction_no() {
		return med_transaction_no;
	}

	public void setMed_transaction_no(String med_transaction_no) {
		this.med_transaction_no = med_transaction_no;
	}

	public String getMed_tran_date() {
		return med_tran_date;
	}

	public void setMed_tran_date(String med_tran_date) {
		this.med_tran_date = med_tran_date;
	}

	public String getMed_prod_code() {
		return med_prod_code;
	}

	public void setMed_prod_code(String med_prod_code) {
		this.med_prod_code = med_prod_code;
	}

	public String getMed_batch_no() {
		return med_batch_no;
	}

	public void setMed_batch_no(String med_batch_no) {
		this.med_batch_no = med_batch_no;
	}

	public String getMed_in_stk_type() {
		return med_in_stk_type;
	}

	public void setMed_in_stk_type(String med_in_stk_type) {
		this.med_in_stk_type = med_in_stk_type;
	}

	public BigDecimal getMed_in_qty() {
		return med_in_qty;
	}

	public void setMed_in_qty(BigDecimal med_in_qty) {
		this.med_in_qty = med_in_qty;
	}

	public String getMed_out_stk_type() {
		return med_out_stk_type;
	}

	public void setMed_out_stk_type(String med_out_stk_type) {
		this.med_out_stk_type = med_out_stk_type;
	}

	public BigDecimal getMed_out_qty() {
		return med_out_qty;
	}

	public void setMed_out_qty(BigDecimal med_out_qty) {
		this.med_out_qty = med_out_qty;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}
	
	
}
