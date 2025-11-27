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
@Table(name = "TDS_CRM_UPLOAD")
public class Tds_Crm_Upload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROWID")
	private Long rowid;
	
	@Column(name = "CustomerType")
	private String customertype;
	
	@Column(name = "CustomerCode")
	private String customercode;
	
	@Column(name = "Customer")
	private String customer;
	
	@Column(name = "ExpenseDate")
	private Date expensedate;
	
	@Column(name = "AccountingTransactionNumber")
	private String accountingtransactionnumber;
	
	@Column(name = "ExpenseDetails")
	private String expensedetails;
	
	@Column(name = "Qty")
	private BigDecimal qty;
	
	@Column(name = "Rate")
	private BigDecimal rate;
	
	@Column(name = "Value")
	private BigDecimal value;

	@Column(name = "GrossUpInd")
	private String grossupind;
	
	@Column(name = "TDS_Percentage")
	private String tds_percentage;
	
	@Column(name = "GrossValue")
	private BigDecimal grossvalue;
	
	@Column(name = "TDSPaid")
	private BigDecimal tdspaid;
	
	@Column(name = "InterestPaid")
	private BigDecimal interestpaid;
	
	@Column(name = "PenaltyPaid")
	private BigDecimal penaltypaid;
	
	@Column(name = "FILENAME")
	private String filename;
	
	@Column(name = "UPLOADED")
	private String uploaded;
	
	@Column(name = "PAN_NO")
	private String pan_no;
	
	
	public String getPan_no() {
		return pan_no;
	}

	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}

	public String getUploaded() {
		return uploaded;
	}

	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}

	public Long getRowid() {
		return rowid;
	}

	public void setRowid(Long rowid) {
		this.rowid = rowid;
	}

	public String getCustomertype() {
		return customertype;
	}

	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}

	public String getCustomercode() {
		return customercode;
	}

	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Date getExpensedate() {
		return expensedate;
	}

	public void setExpensedate(Date expensedate) {
		this.expensedate = expensedate;
	}

	public String getAccountingtransactionnumber() {
		return accountingtransactionnumber;
	}

	public void setAccountingtransactionnumber(String accountingtransactionnumber) {
		this.accountingtransactionnumber = accountingtransactionnumber;
	}

	public String getExpensedetails() {
		return expensedetails;
	}

	public void setExpensedetails(String expensedetails) {
		this.expensedetails = expensedetails;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getGrossupind() {
		return grossupind;
	}

	public void setGrossupind(String grossupind) {
		this.grossupind = grossupind;
	}

	public String getTds_percentage() {
		return tds_percentage;
	}

	public void setTds_percentage(String tds_percentage) {
		this.tds_percentage = tds_percentage;
	}

	public BigDecimal getGrossvalue() {
		return grossvalue;
	}

	public void setGrossvalue(BigDecimal grossvalue) {
		this.grossvalue = grossvalue;
	}

	public BigDecimal getTdspaid() {
		return tdspaid;
	}

	public void setTdspaid(BigDecimal tdspaid) {
		this.tdspaid = tdspaid;
	}

	public BigDecimal getInterestpaid() {
		return interestpaid;
	}

	public void setInterestpaid(BigDecimal interestpaid) {
		this.interestpaid = interestpaid;
	}

	public BigDecimal getPenaltypaid() {
		return penaltypaid;
	}

	public void setPenaltypaid(BigDecimal penaltypaid) {
		this.penaltypaid = penaltypaid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
