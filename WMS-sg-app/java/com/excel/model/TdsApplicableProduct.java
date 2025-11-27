package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "TDS_APPLICABLE_PRODUCT_REPORT")
@NamedStoredProcedureQuery(name = "callTDS_APPLICABLE_PRODUCT_REPORT", procedureName = "TDS_APPLICABLE_PRODUCT_REPORT", parameters = {
		@StoredProcedureParameter(name = "sub_comp", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "hcp_cust_id", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "finyear", mode = ParameterMode.IN, type = String.class) }, resultClasses = TdsApplicableProduct.class)
public class TdsApplicableProduct implements Serializable{
	
	private static final long serialVersionUID = -737856012326986833L;
	
	@Id
	@Column(name = "ROW")
	private Long row;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "PROD_CODE")
	private String prod_code;
	
	@Column(name = "PROD_NAME")
	private String prod_name;
	
	@Column(name = "Batch_Number")
	private String batch_number;
	
	@Column(name = "DIST_date")
	private String dist_date;
	
	@Column(name = "units")
	private String units;
	
	@Column(name = "dist_rate")
	private String dist_rate;
	
	@Column(name = "dist_value")
	private String dist_value;
	
	@Column(name = "division")
	private String division;
	
	@Column(name = "SE")
	private String se;
	
	@Column(name = "territory")
	private String territory;
	
	@Column(name = "EMP_CODE")
	private String emp_code;
	
	@Column(name = "CHALLAN_NO")
	private String challan_no;
	
	@Column(name = "CHALLAN_DATE")
	private String challan_date;
	
	@Column(name = "E_INVOICE_NO")
	private String e_invoice_no;
	
	@Column(name = "E_INVOICE_DT")
	private String e_invoice_dt;
	
	@Column(name = "EWAY_BILL_NO")
	private String eway_bill_no;
	
	@Column(name = "EWAY_BILL_DT")
	private String eway_bill_dt;
	
	@Column(name = "INVOICE_NO")
	private String invoice_no;
	
	@Column(name = "INVOICE_DATE")
	private String invoice_date;
	
	
	public String getChallan_no() {
		return challan_no;
	}

	public void setChallan_no(String challan_no) {
		this.challan_no = challan_no;
	}

	public String getChallan_date() {
		return challan_date;
	}

	public void setChallan_date(String challan_date) {
		this.challan_date = challan_date;
	}

	public String getE_invoice_no() {
		return e_invoice_no;
	}

	public void setE_invoice_no(String e_invoice_no) {
		this.e_invoice_no = e_invoice_no;
	}

	public String getE_invoice_dt() {
		return e_invoice_dt;
	}

	public void setE_invoice_dt(String e_invoice_dt) {
		this.e_invoice_dt = e_invoice_dt;
	}

	public String getEway_bill_no() {
		return eway_bill_no;
	}

	public void setEway_bill_no(String eway_bill_no) {
		this.eway_bill_no = eway_bill_no;
	}

	public String getEway_bill_dt() {
		return eway_bill_dt;
	}

	public void setEway_bill_dt(String eway_bill_dt) {
		this.eway_bill_dt = eway_bill_dt;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public String getBatch_number() {
		return batch_number;
	}

	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}

	public String getDist_date() {
		return dist_date;
	}

	public void setDist_date(String dist_date) {
		this.dist_date = dist_date;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getDist_rate() {
		return dist_rate;
	}

	public void setDist_rate(String dist_rate) {
		this.dist_rate = dist_rate;
	}

	public String getDist_value() {
		return dist_value;
	}

	public void setDist_value(String dist_value) {
		this.dist_value = dist_value;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSe() {
		return se;
	}

	public void setSe(String se) {
		this.se = se;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
