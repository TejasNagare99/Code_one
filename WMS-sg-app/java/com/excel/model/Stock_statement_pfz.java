package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "Stock_statement_pfz")
@NamedStoredProcedureQuery(name = "STOCK_STATEMENT_FCODE", 
	//procedureName = "STOCK_STATEMENT_FCODE",
	procedureName = "STOCK_STATEMENT_FCODE_RATEOPTION",
	parameters = {
			@StoredProcedureParameter(name = "div_id", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "loc_id", mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "nilstock" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "exp_ind" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "USER_ID" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "brand_id" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "smp_prod_type" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "RATE_OPTION" , mode = ParameterMode.IN, type = String.class)
	}, resultClasses = Stock_statement_pfz.class)
public class Stock_statement_pfz {
	
	@Id
	@Column(name = "row")
	private String row;
	
	@Column(name = "team")
	private String division;
	
	@Column(name = "loc_name")
	private String warehouse;
	
	@Column(name = "Brand")
	private String brand;
	
	@Column(name = "Samples_Pl")
	private String sample_PI;
	
	@Column(name = "PYC_Code")
	private String pyc_code;
	
	@Column(name = "Product_code")
	private String product_code;
	
	@Column(name = "Product_name")
	private String product_name;
	
	@Column(name = "Stock_qty")
	private BigDecimal stock_qty;
	
	@Column(name = "Batch_No")
	private String batch_no;
	
	@Column(name = "Receipt_Date")
	private String reciept_date;
	
	@Column(name = "Expry_Date")
	private String expry_date;
	
	@Column(name = "Unit_Price")
	private BigDecimal unit_price;
	
	@Column(name = "Stock_value")
	private BigDecimal stock_value;
	
	@Column(name = "Latest_allocated")
	private String latest_allocated;
	
	@Column(name = "Avg_monthly_use")
	private String avg_monthly_use;
	
	@Column(name = "Prod_type")
	private String prod_type;
	
	@Column(name = "With_held_qty")
	private BigDecimal with_held_qty;
	
	@Column(name="near_exp_ind")
	private String near_exp_ind;
	
	@Column(name="HSN_CODE")
	private String hsn_code;
	
	@Column(name="SGST")
	private BigDecimal sgst;
	
	@Column(name="CGST")
	private BigDecimal cgst;
	
	@Column(name="IGST")
	private BigDecimal igst;
	
	@Column(name="FCODE")
	private String fcode;
	
	@Column(name="TYPE_OF_STORAGE")
	private String type_of_storage;
	
	@Column(name="GCMA_NUMBER")
	private String gcma_number;
	
	@Column(name="GCMA_EXPIRY_DT")
	private String gcma_expiry_dt;
	
	@Column(name="MONO_SIZE")
	private Long mono_size;
	
	@Column(name="SHIPPER_SIZE")
	private Long shipper_size;
	
	

	public Long getShipper_size() {
		return shipper_size;
	}

	public void setShipper_size(Long shipper_size) {
		this.shipper_size = shipper_size;
	}

	public Long getMono_size() {
		return mono_size;
	}

	public void setMono_size(Long mono_size) {
		this.mono_size = mono_size;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSample_PI() {
		return sample_PI;
	}

	public void setSample_PI(String sample_PI) {
		this.sample_PI = sample_PI;
	}

	public String getPyc_code() {
		return pyc_code;
	}

	public void setPyc_code(String pyc_code) {
		this.pyc_code = pyc_code;
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

	public BigDecimal getStock_qty() {
		return stock_qty;
	}

	public void setStock_qty(BigDecimal stock_qty) {
		this.stock_qty = stock_qty;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getReciept_date() {
		return reciept_date;
	}

	public void setReciept_date(String reciept_date) {
		this.reciept_date = reciept_date;
	}

	public String getExpry_date() {
		return expry_date;
	}

	public void setExpry_date(String expry_date) {
		this.expry_date = expry_date;
	}

	public BigDecimal getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}

	public BigDecimal getStock_value() {
		return stock_value;
	}

	public void setStock_value(BigDecimal stock_value) {
		this.stock_value = stock_value;
	}

	public String getLatest_allocated() {
		return latest_allocated;
	}

	public void setLatest_allocated(String latest_allocated) {
		this.latest_allocated = latest_allocated;
	}

	public String getAvg_monthly_use() {
		return avg_monthly_use;
	}

	public void setAvg_monthly_use(String avg_monthly_use) {
		this.avg_monthly_use = avg_monthly_use;
	}

	public String getProd_type() {
		return prod_type;
	}

	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}

	public BigDecimal getWith_held_qty() {
		return with_held_qty;
	}

	public void setWith_held_qty(BigDecimal with_held_qty) {
		this.with_held_qty = with_held_qty;
	}

	public String getNear_exp_ind() {
		return near_exp_ind;
	}

	public void setNear_exp_ind(String near_exp_ind) {
		this.near_exp_ind = near_exp_ind;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getIgst() {
		return igst;
	}

	public void setIgst(BigDecimal igst) {
		this.igst = igst;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}


	public String getType_of_storage() {
		return type_of_storage;
	}

	public void setType_of_storage(String type_of_storage) {
		this.type_of_storage = type_of_storage;
	}

	public String getGcma_number() {
		return gcma_number;
	}

	public void setGcma_number(String gcma_number) {
		this.gcma_number = gcma_number;
	}

	public String getGcma_expiry_dt() {
		return gcma_expiry_dt;
	}

	public void setGcma_expiry_dt(String gcma_expiry_dt) {
		this.gcma_expiry_dt = gcma_expiry_dt;
	}

	
}
