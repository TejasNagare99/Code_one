package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_SWV_DETAIL")
public class V_SWV_Detail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SWVDTL_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long swvdtl_id;

	@Column(name = "SWVDTL_SWV_ID")
	private Long swvdtl_swv_id;

	@Column(name = "SWVDTL_LOC_ID")
	private Long swvdtl_loc_id;
	
	@Column(name = "SWVDTL_COMPANY")
	private String swvdtl_company;
	
	@Column(name = "SWVDTL_FIN_YEAR")
	private String swvdtl_fin_year;
	
	@Column(name = "SWVDTL_PERIOD_CODE")
	private String swvdtl_period_code;

	@Column(name = "SWVDTL_DIV_ID")
	private Long swvdtl_div_id;
	
	@Column(name = "SWVDTL_PROD_ID")
	private Long swvdtl_prod_id;
	
	@Column(name = "SWVDTL_BATCH_ID")
	private Long swvdtl_batch_id;
	
	@Column(name = "SWVDTL_DISP_QTY")
	private BigDecimal swvdtl_disp_qty;

	@Column(name = "SWVDTL_RATE")
	private BigDecimal swvdtl_rate;

	@Column(name = "SWVDTL_STOCK_TYPE")
	private String swvdtl_stock_type;

	@Column(name = "SWVDTL_CASES")
	private Integer swvdtl_cases;

	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;

	@Column(name = "BATCH_NO")
	private String batch_no;

	@Column(name = "DIVISION_NAME")
	private String division_name;

	@Column(name = "STOCK_TYPE_NAME")
	private String stock_type_name;

	public Long getSwvdtl_id() {
		return swvdtl_id;
	}

	public void setSwvdtl_id(Long swvdtl_id) {
		this.swvdtl_id = swvdtl_id;
	}

	public Long getSwvdtl_swv_id() {
		return swvdtl_swv_id;
	}

	public void setSwvdtl_swv_id(Long swvdtl_swv_id) {
		this.swvdtl_swv_id = swvdtl_swv_id;
	}

	public Long getSwvdtl_loc_id() {
		return swvdtl_loc_id;
	}

	public void setSwvdtl_loc_id(Long swvdtl_loc_id) {
		this.swvdtl_loc_id = swvdtl_loc_id;
	}

	public Long getSwvdtl_div_id() {
		return swvdtl_div_id;
	}

	public void setSwvdtl_div_id(Long swvdtl_div_id) {
		this.swvdtl_div_id = swvdtl_div_id;
	}

	public Long getSwvdtl_prod_id() {
		return swvdtl_prod_id;
	}

	public void setSwvdtl_prod_id(Long swvdtl_prod_id) {
		this.swvdtl_prod_id = swvdtl_prod_id;
	}

	public Long getSwvdtl_batch_id() {
		return swvdtl_batch_id;
	}

	public void setSwvdtl_batch_id(Long swvdtl_batch_id) {
		this.swvdtl_batch_id = swvdtl_batch_id;
	}

	public BigDecimal getSwvdtl_disp_qty() {
		return swvdtl_disp_qty;
	}

	public void setSwvdtl_disp_qty(BigDecimal swvdtl_disp_qty) {
		this.swvdtl_disp_qty = swvdtl_disp_qty;
	}

	public BigDecimal getSwvdtl_rate() {
		return swvdtl_rate;
	}

	public void setSwvdtl_rate(BigDecimal swvdtl_rate) {
		this.swvdtl_rate = swvdtl_rate;
	}

	public String getSwvdtl_stock_type() {
		return swvdtl_stock_type;
	}

	public void setSwvdtl_stock_type(String swvdtl_stock_type) {
		this.swvdtl_stock_type = swvdtl_stock_type;
	}

	public Integer getSwvdtl_cases() {
		return swvdtl_cases;
	}

	public void setSwvdtl_cases(Integer swvdtl_cases) {
		this.swvdtl_cases = swvdtl_cases;
	}

	public String getSmp_prod_name() {
		return smp_prod_name;
	}

	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getDivision_name() {
		return division_name;
	}

	public void setDivision_name(String division_name) {
		this.division_name = division_name;
	}

	public String getStock_type_name() {
		return stock_type_name;
	}

	public void setStock_type_name(String stock_type_name) {
		this.stock_type_name = stock_type_name;
	}

	public String getSwvdtl_company() {
		return swvdtl_company;
	}

	public void setSwvdtl_company(String swvdtl_company) {
		this.swvdtl_company = swvdtl_company;
	}

	public String getSwvdtl_period_code() {
		return swvdtl_period_code;
	}

	public void setSwvdtl_period_code(String swvdtl_period_code) {
		this.swvdtl_period_code = swvdtl_period_code;
	}

	public String getSwvdtl_fin_year() {
		return swvdtl_fin_year;
	}

	public void setSwvdtl_fin_year(String swvdtl_fin_year) {
		this.swvdtl_fin_year = swvdtl_fin_year;
	}

	
}
