package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.BeanProperty.Bogus;

@Entity
@Table(name = "BATCH_STK_RECO_ARCH")
public class Erp_batch_stk_reco_arch implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Fin_YEAR")
	private String fin_year;
	
	@Column(name="Reco_Date")
    private Date reco_date;
	
	@Column(name = "ORGANIZATION")
	private String organization;
	
	@Column(name = "PRODUCT_CODE")
	private String product_code;
	
	@Column(name = "PRODUCT_NAME")                        
	private String product_name;                     
	
	@Column(name = "BATCH_NO")
	private String batch_no;
	
	@Column(name = "STOCK_TYPE")                            
	private String stock_type;
	
	@Column(name = "Qty")
	private Long qty;
	
	@Column(name = "Medico_ORG")
	private String medico_org;
	
	@Column(name = "Medico_PROD_CODE")                     
	private String medico_prod_code;
	
	@Column(name = "Medico_PROD_NAME")
	private String medico_prod_name;
	
	@Column(name = "Medico_BATCH_NO")
	private String medico_batch_no;
	
	@Column(name = "Medico_STK_TYPE")
	private String medico_stk_type;
	
	@Column(name = "Medico_Qty")
	private Long medico_qty;
	 
	@Column(name = "Difference")
	private Long difference;

	public String getFin_year() {
		return fin_year;
	}

	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}

	public Date getReco_date() {
		return reco_date;
	}

	public void setReco_date(Date reco_date) {
		this.reco_date = reco_date;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
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

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public String getMedico_org() {
		return medico_org;
	}

	public void setMedico_org(String medico_org) {
		this.medico_org = medico_org;
	}

	public String getMedico_prod_code() {
		return medico_prod_code;
	}

	public void setMedico_prod_code(String medico_prod_code) {
		this.medico_prod_code = medico_prod_code;
	}

	public String getMedico_prod_name() {
		return medico_prod_name;
	}

	public void setMedico_prod_name(String medico_prod_name) {
		this.medico_prod_name = medico_prod_name;
	}

	public String getMedico_batch_no() {
		return medico_batch_no;
	}

	public void setMedico_batch_no(String medico_batch_no) {
		this.medico_batch_no = medico_batch_no;
	}

	public String getMedico_stk_type() {
		return medico_stk_type;
	}

	public void setMedico_stk_type(String medico_stk_type) {
		this.medico_stk_type = medico_stk_type;
	}

	public Long getMedico_qty() {
		return medico_qty;
	}

	public void setMedico_qty(Long medico_qty) {
		this.medico_qty = medico_qty;
	}

	public Long getDifference() {
		return difference;
	}

	public void setDifference(Long difference) {
		this.difference = difference;
	}



}
