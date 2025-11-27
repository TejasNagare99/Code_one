package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_LOG_REPORT")
@NamedStoredProcedureQuery(name = "callAUDIT_LOG_REPORT", procedureName = "AUDIT_LOG_REPORT", resultClasses = Audit_log.class,
parameters = {
		@StoredProcedureParameter(name="startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="endDate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="FIN_YEAR_FLAG", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="FIN_YEAR", mode = ParameterMode.IN, type = String.class)
}

)
public class Audit_log {
	@Id
	@Column(name = "Row")
	private Long row;
	@Column(name = "Doc_type")
	private String doc_type;
	@Column(name = "Alloc_type")
	private String alloc_type;
	@Column(name = "DOC_NO")
	private String doc_no;
	@Column(name = "DOC_DATE")
	private String doc_date;
	@Column(name = "Recd_from")
	private String recd_from;
	@Column(name = "Issued_to")
	private String issued_to;
	@Column(name = "ref_no")
	private String ref_no;
	@Column(name = "ref_date")
	private String ref_date;
	@Column(name = "Item_div")
	private String item_div;
	@Column(name = "smp_prod_cd")
	private String smp_prod_cd;
	@Column(name = "SMP_PROD_NAME")
	private String smp_prod_name;
	@Column(name = "ALLOC_QTY")
	private BigDecimal alloc_qty;
	@Column(name = "ALLOC_RATE")
	private BigDecimal alloc_rate;
	@Column(name = "ITEM_VALUE")
	private BigDecimal item_value;
	@Column(name = "issue_by")
	private String issue_by;
	@Column(name = "issue_date")
	private String issue_date;
	@Column(name = "Approved_by")
	private String approved_by;
	@Column(name = "Approved_date")
	private String approved_date;
	
	
	
	public String getDoc_date() {
		return doc_date;
	}
	public void setDoc_date(String doc_date) {
		this.doc_date = doc_date;
	}
	public String getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public String getAlloc_type() {
		return alloc_type;
	}
	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}
	
	public String getRecd_from() {
		return recd_from;
	}
	public void setRecd_from(String recd_from) {
		this.recd_from = recd_from;
	}
	public String getIssued_to() {
		return issued_to;
	}
	public void setIssued_to(String issued_to) {
		this.issued_to = issued_to;
	}
	public String getRef_no() {
		return ref_no;
	}
	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}
	public String getRef_date() {
		return ref_date;
	}
	public void setRef_date(String ref_date) {
		this.ref_date = ref_date;
	}
	public String getItem_div() {
		return item_div;
	}
	public void setItem_div(String item_div) {
		this.item_div = item_div;
	}
	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}
	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}
	public String getSmp_prod_name() {
		return smp_prod_name;
	}
	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}
	public BigDecimal getAlloc_qty() {
		return alloc_qty;
	}
	public void setAlloc_qty(BigDecimal alloc_qty) {
		this.alloc_qty = alloc_qty;
	}
	public BigDecimal getAlloc_rate() {
		return alloc_rate;
	}
	public void setAlloc_rate(BigDecimal alloc_rate) {
		this.alloc_rate = alloc_rate;
	}
	public BigDecimal getItem_value() {
		return item_value;
	}
	public void setItem_value(BigDecimal item_value) {
		this.item_value = item_value;
	}
	public String getIssue_by() {
		return issue_by;
	}
	public void setIssue_by(String issue_by) {
		this.issue_by = issue_by;
	}
	
	public String getApproved_by() {
		return approved_by;
	}
	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}
	public String getApproved_date() {
		return approved_date;
	}
	public void setApproved_date(String approved_date) {
		this.approved_date = approved_date;
	}
	@Override
	public String toString() {
		return "Audit_log [row=" + row + ", doc_type=" + doc_type + ", alloc_type=" + alloc_type + ", doc_no=" + doc_no
				+ ", doc_date=" + doc_date + ", recd_from=" + recd_from + ", issued_to=" + issued_to + ", ref_no="
				+ ref_no + ", ref_date=" + ref_date + ", item_div=" + item_div + ", smp_prod_cd=" + smp_prod_cd
				+ ", smp_prod_name=" + smp_prod_name + ", alloc_qty=" + alloc_qty + ", alloc_rate=" + alloc_rate
				+ ", item_value=" + item_value + ", issue_by=" + issue_by + ", issue_date=" + issue_date
				+ ", approved_by=" + approved_by + ", approved_date=" + approved_date + "]";
	}
	public Audit_log() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
