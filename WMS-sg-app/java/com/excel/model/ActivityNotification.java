package com.excel.model;

import java.io.Serializable;
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
@Table(name = "ViewAPPROVAL_NOTIFICATION_DISPLAY")
@NamedStoredProcedureQuery(name = "callAPPROVAL_NOTIFICATION_DISPLAY", 
	procedureName = "APPROVAL_NOTIFICATION_DISPLAY",
	parameters = {
			@StoredProcedureParameter(name = "pUserid", mode = ParameterMode.IN, type = String.class)
	}, resultClasses = ActivityNotification.class)
public class ActivityNotification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RowID")
	private Long rowid;
	
	@Column(name = "LOC_NM")
	private String loc_nm;
	
	@Column(name = "TRAN_TYPE")
	private String tran_type;
	
	@Column(name = "TRAN_REF_ID")
	private String tran_ref_id;
	
	@Column(name = "TRAN_NO")
	private String tran_no;
	
	@Column(name = "APPROVAL_TYPE")
	private String approval_type;
	
	@Column(name = "TRAN_TYPE_DESCRIPTION")
	private String tran_type_description;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name = "STOCK_POINT_ID")
	private Long stock_point_id;
	
	@Column(name = "LOC_ID")
	private Long loc_id;
	
	@Column(name = "DOCTYPE")
	private String doctype;
	
	@Column(name = "PROCESS_INSTANCE_ID")
	private Long process_instance_id;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "received_on")
	private String recieved_on;
	
	@Column(name = "INITIATOR")
	private String initiator;
	
	@Column(name = "initiator_name")
	private String initiator_name;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "APPROVER")
	private String approver;
	
	@Column(name = "doc_no")
	private String doc_no;
	
	@Column(name = "DOC_DT")
	private String doc_dt;
	
	@Column(name = "NAME__SUPPLIER_DIVISION_FIELDSTAFF")
	private String name__supplier_division_fieldstaff;


	public String getLoc_nm() {
		return loc_nm;
	}

	public void setLoc_nm(String loc_nm) {
		this.loc_nm = loc_nm;
	}

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public String getTran_ref_id() {
		return tran_ref_id;
	}

	public void setTran_ref_id(String tran_ref_id) {
		this.tran_ref_id = tran_ref_id;
	}

	public String getTran_no() {
		return tran_no;
	}

	public void setTran_no(String tran_no) {
		this.tran_no = tran_no;
	}

	public String getApproval_type() {
		return approval_type;
	}

	public void setApproval_type(String approval_type) {
		this.approval_type = approval_type;
	}

	public String getTran_type_description() {
		return tran_type_description;
	}

	public void setTran_type_description(String tran_type_description) {
		this.tran_type_description = tran_type_description;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public Long getStock_point_id() {
		return stock_point_id;
	}

	public void setStock_point_id(Long stock_point_id) {
		this.stock_point_id = stock_point_id;
	}

	public Long getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}

	public Long getProcess_instance_id() {
		return process_instance_id;
	}

	public void setProcess_instance_id(Long process_instance_id) {
		this.process_instance_id = process_instance_id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRecieved_on() {
		return recieved_on;
	}

	public void setRecieved_on(String recieved_on) {
		this.recieved_on = recieved_on;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getInitiator_name() {
		return initiator_name;
	}

	public void setInitiator_name(String initiator_name) {
		this.initiator_name = initiator_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getDoc_no() {
		return doc_no;
	}

	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}


	public String getDoc_dt() {
		return doc_dt;
	}

	public void setDoc_dt(String doc_dt) {
		this.doc_dt = doc_dt;
	}

	public String getName__supplier_division_fieldstaff() {
		return name__supplier_division_fieldstaff;
	}

	public void setName__supplier_division_fieldstaff(String name__supplier_division_fieldstaff) {
		this.name__supplier_division_fieldstaff = name__supplier_division_fieldstaff;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	
	
}
