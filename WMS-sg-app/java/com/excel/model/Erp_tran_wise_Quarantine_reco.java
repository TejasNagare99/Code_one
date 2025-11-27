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
@Table(name = "TRAN_WISE_QUARANTINE_RECO")
public class Erp_tran_wise_Quarantine_reco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SLNO")
	private Long slno;
	
	@Column(name = "RECO_FROM")
	private Date Reco_from;
	
	@Column(name = "RECO_TO")
	private Date Reco_to;
	
	@Column(name = "FIN_YEAR")
	private String Fin_year;
	
	@Column(name = "ORGANIZATION")
	private String Organization;
	
	@Column(name = "TRAN_ID")
	private String Tran_id;
	
	@Column(name = "TRAN_TYPE")
	private String Tran_type;
	
	@Column(name = "TRANSACTION_NO")
	private String Transaction_no;
	
	@Column(name = "TRAN_DATE")
	private Date Tran_date;
	
	@Column(name = "IN_PROD_CODE")
	private String In_prod_code;
	
	@Column(name = "IN_PRODUCT_NAME")
	private String In_prod_name;
	
	@Column(name = "IN_BATCH_NO")
	private String In_batch_no;
	
	@Column(name = "IN_STOCK_TYPE")
	private String In_stock_type;
	
	@Column(name = "IN_QTY")
	private Long qty;
	
	@Column(name = "OUT_PROD_CODE")
	private String Out_prod_code;
	
	
	@Column(name = "OUT_PRODUCT_NAME")
	private String Out_product_name;
	
	@Column(name = "OUT_BATCH_NO")
	private String Out_batch_no;
	
	@Column(name = "OUT_STOCK_TYPE")
	private String Out_stk_type;
	
	@Column(name = "OUT_QTY")
	private Long Out_qty;
	
	@Column(name = "MED_TRAN_ID")
	private long Med_tran_id;
	
	@Column(name = "MED_TRAN_TYPE")
	private long Med_tran_type;
	
	@Column(name = "MED_TRANSACTION_NO")
	private long Med_transaction_no;
	
	@Column(name = "MED_TRAN_DATE")
	private Date Med_tran_date;
	
	@Column(name = "MED_IN_PROD_CODE")
	private String Med_in_prod_code;
	
	@Column(name = "MED_IN_BATCH_NO")
	private String Med_in_batch_no;
	
	@Column(name = "MED_IN_STK_TYPE")
	private long Med_in_stk_type;
	
	@Column(name = "MED_IN_QTY")
	private BigDecimal Med_in_qty;
	
	@Column(name = "MED_OUT_PROD_CODE")
	private String Med_out_prod_code;
	
	@Column(name = "MED_OUT_BATCH_NO")
	private long Med_out_batch_no;
	
	@Column(name = "MED_OUT_STK_TYPE")
	private long Med_out_stk_type;
	
	@Column(name = "MED_OUT_QTY")
	private BigDecimal Med_out_qty;

	public Long getSlno() {
		return slno;
	}

	public void setSlno(Long slno) {
		this.slno = slno;
	}

	public Date getReco_from() {
		return Reco_from;
	}

	public void setReco_from(Date reco_from) {
		Reco_from = reco_from;
	}

	public Date getReco_to() {
		return Reco_to;
	}

	public void setReco_to(Date reco_to) {
		Reco_to = reco_to;
	}

	public String getFin_year() {
		return Fin_year;
	}

	public void setFin_year(String fin_year) {
		Fin_year = fin_year;
	}

	public String getOrganization() {
		return Organization;
	}

	public void setOrganization(String organization) {
		Organization = organization;
	}

	public String getTran_id() {
		return Tran_id;
	}

	public void setTran_id(String tran_id) {
		Tran_id = tran_id;
	}

	public String getTran_type() {
		return Tran_type;
	}

	public void setTran_type(String tran_type) {
		Tran_type = tran_type;
	}

	public String getTransaction_no() {
		return Transaction_no;
	}

	public void setTransaction_no(String transaction_no) {
		Transaction_no = transaction_no;
	}

	public Date getTran_date() {
		return Tran_date;
	}

	public void setTran_date(Date tran_date) {
		Tran_date = tran_date;
	}

	public String getIn_prod_code() {
		return In_prod_code;
	}

	public void setIn_prod_code(String in_prod_code) {
		In_prod_code = in_prod_code;
	}

	public String getIn_prod_name() {
		return In_prod_name;
	}

	public void setIn_prod_name(String in_prod_name) {
		In_prod_name = in_prod_name;
	}

	public String getIn_batch_no() {
		return In_batch_no;
	}

	public void setIn_batch_no(String in_batch_no) {
		In_batch_no = in_batch_no;
	}

	public String getIn_stock_type() {
		return In_stock_type;
	}

	public void setIn_stock_type(String in_stock_type) {
		In_stock_type = in_stock_type;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public String getOut_prod_code() {
		return Out_prod_code;
	}

	public void setOut_prod_code(String out_prod_code) {
		Out_prod_code = out_prod_code;
	}

	public String getOut_product_name() {
		return Out_product_name;
	}

	public void setOut_product_name(String out_product_name) {
		Out_product_name = out_product_name;
	}

	public String getOut_batch_no() {
		return Out_batch_no;
	}

	public void setOut_batch_no(String out_batch_no) {
		Out_batch_no = out_batch_no;
	}

	public String getOut_stk_type() {
		return Out_stk_type;
	}

	public void setOut_stk_type(String out_stk_type) {
		Out_stk_type = out_stk_type;
	}

	public Long getOut_qty() {
		return Out_qty;
	}

	public void setOut_qty(Long out_qty) {
		Out_qty = out_qty;
	}

	public long getMed_tran_id() {
		return Med_tran_id;
	}

	public void setMed_tran_id(long med_tran_id) {
		Med_tran_id = med_tran_id;
	}

	public long getMed_tran_type() {
		return Med_tran_type;
	}

	public void setMed_tran_type(long med_tran_type) {
		Med_tran_type = med_tran_type;
	}

	public long getMed_transaction_no() {
		return Med_transaction_no;
	}

	public void setMed_transaction_no(long med_transaction_no) {
		Med_transaction_no = med_transaction_no;
	}

	public Date getMed_tran_date() {
		return Med_tran_date;
	}

	public void setMed_tran_date(Date med_tran_date) {
		Med_tran_date = med_tran_date;
	}

	public String getMed_in_prod_code() {
		return Med_in_prod_code;
	}

	public void setMed_in_prod_code(String med_in_prod_code) {
		Med_in_prod_code = med_in_prod_code;
	}

	public String getMed_in_batch_no() {
		return Med_in_batch_no;
	}

	public void setMed_in_batch_no(String med_in_batch_no) {
		Med_in_batch_no = med_in_batch_no;
	}

	public long getMed_in_stk_type() {
		return Med_in_stk_type;
	}

	public void setMed_in_stk_type(long med_in_stk_type) {
		Med_in_stk_type = med_in_stk_type;
	}

	public BigDecimal getMed_in_qty() {
		return Med_in_qty;
	}

	public void setMed_in_qty(BigDecimal med_in_qty) {
		Med_in_qty = med_in_qty;
	}

	public String getMed_out_prod_code() {
		return Med_out_prod_code;
	}

	public void setMed_out_prod_code(String med_out_prod_code) {
		Med_out_prod_code = med_out_prod_code;
	}

	public long getMed_out_batch_no() {
		return Med_out_batch_no;
	}

	public void setMed_out_batch_no(long med_out_batch_no) {
		Med_out_batch_no = med_out_batch_no;
	}

	public long getMed_out_stk_type() {
		return Med_out_stk_type;
	}

	public void setMed_out_stk_type(long med_out_stk_type) {
		Med_out_stk_type = med_out_stk_type;
	}

	public BigDecimal getMed_out_qty() {
		return Med_out_qty;
	}

	public void setMed_out_qty(BigDecimal med_out_qty) {
		Med_out_qty = med_out_qty;
	}

		
}
