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
@Table(name = "BATCH_STK_RECO_QUARANTINE")
public class Erp_Batch_stk_reco_quarantine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SLNO")
	private Long Slno;
	
	@Column(name="Reco_Date")
	private Date Reco_date;
	
	@Column(name="FIN_YEAR")
	private String Fin_year;
	
	@Column(name="ORGANIZATION")
	private String Organization;
	
	@Column(name="PRODUCT_CODE")
	private String Product_code;
	
	@Column(name="PRODUCT_NAME")
	private String Product_name;
	
	@Column(name="BATCH_NO")
	private String Batch_no;
	
	@Column(name="STOCK_TYPE")
	private String Stock_type;
	
	@Column(name="Qty")
	private BigDecimal qty;
	
	@Column(name="Medico_ORG")
	private String Medico_org;
	
	@Column(name="Medico_PROD_CODE")
	private String Medico_prod_code;
	
	@Column(name="Medico_PROD_NAME")
	private String Medico_prod_name;
	
	@Column(name="Medico_BATCH_NO")
	private String Medico_batch_no;
	
	@Column(name="Medico_STK_TYPE")
	private String Medico_stk_type;
	
	@Column(name="Medico_Qty")
	private BigDecimal Medico_qty;
	
	@Column(name="Difference")
	private BigDecimal Difference;

	public Long getSlno() {
		return Slno;
	}

	public void setSlno(Long slno) {
		Slno = slno;
	}

	public Date getReco_date() {
		return Reco_date;
	}

	public void setReco_date(Date reco_date) {
		Reco_date = reco_date;
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

	public String getProduct_code() {
		return Product_code;
	}

	public void setProduct_code(String product_code) {
		Product_code = product_code;
	}

	public String getProduct_name() {
		return Product_name;
	}

	public void setProduct_name(String product_name) {
		Product_name = product_name;
	}

	public String getBatch_no() {
		return Batch_no;
	}

	public void setBatch_no(String batch_no) {
		Batch_no = batch_no;
	}

	public String getStock_type() {
		return Stock_type;
	}

	public void setStock_type(String stock_type) {
		Stock_type = stock_type;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getMedico_org() {
		return Medico_org;
	}

	public void setMedico_org(String medico_org) {
		Medico_org = medico_org;
	}

	public String getMedico_prod_code() {
		return Medico_prod_code;
	}

	public void setMedico_prod_code(String medico_prod_code) {
		Medico_prod_code = medico_prod_code;
	}

	public String getMedico_prod_name() {
		return Medico_prod_name;
	}

	public void setMedico_prod_name(String medico_prod_name) {
		Medico_prod_name = medico_prod_name;
	}

	public String getMedico_batch_no() {
		return Medico_batch_no;
	}

	public void setMedico_batch_no(String medico_batch_no) {
		Medico_batch_no = medico_batch_no;
	}

	public String getMedico_stk_type() {
		return Medico_stk_type;
	}

	public void setMedico_stk_type(String medico_stk_type) {
		Medico_stk_type = medico_stk_type;
	}

	public BigDecimal getMedico_qty() {
		return Medico_qty;
	}

	public void setMedico_qty(BigDecimal medico_qty) {
		Medico_qty = medico_qty;
	}

	public BigDecimal getDifference() {
		return Difference;
	}

	public void setDifference(BigDecimal difference) {
		Difference = difference;
	}
	
	
	
}
