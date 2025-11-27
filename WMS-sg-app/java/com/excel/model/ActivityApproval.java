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
@Table(name = "ActivityApproval")
@NamedStoredProcedureQuery(
		name="callActivityApproval",
		procedureName = "Activity_Approval",
		resultClasses = ActivityApproval.class,
		parameters = {
				@StoredProcedureParameter(name="pcTaskId", mode = ParameterMode.IN, type = String.class)
		}
)
public class ActivityApproval {

	@Id
	@Column(name = "ROWNUM")
	private Long ROWNUM;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "loc_nm")
	private String loc_nm;
	
	@Column(name = "tran_type")
	private String tran_type;
	
	@Column(name = "tran_ref_id")
	private Long tran_ref_id;
	
	@Column(name = "tran_no")
	private String tran_no;
	
	@Column(name = "approval_type")
	private String approval_type;
	
	@Column(name = "task_id")
	private String task_id;
	
	@Column(name = "tran_type_description")
	private String tran_type_description;
	
	@Column(name = "fin_year_id")
	private Long fin_year_id;
	
	@Column(name = "stock_point_id")
	private Long stock_point_id;
	
	@Column(name = "loc_id")
	private Long loc_id;
	
	@Column(name = "doc_type")
	private String doc_type;
	
	@Column(name = "process_instance_id")
	private Long process_instance_id;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "group_")
	private String group;
	
	@Column(name = "received_on")
	private String recieved_on;
	
	@Column(name = "initiator")
	private String initiator;
	
	@Column(name = "alternate_name")
	private String alternate_name;
	
	@Column(name = "document_no")
	private String document_no;
	
	@Column(name = "document_date")
	private Date document_date;
	
	@Column(name = "lr_no")
	private String lr_no;
	
	@Column(name = "lr_dt")
	private Date lr_dt;
	
	@Column(name = "stn_or_challan_no")
	private String stn_or_challan_no;
	
	@Column(name = "stn_or_challan_dt")
	private Date stn_or_challan_dt;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "INS_USER_ID")
	private String ins_user_id;
	
	@Column(name = "entered_by")
	private String entered_by;
	
	@Column(name = "TRAN_TYPE_NAME")
	private String tran_type_name;
	
	@Column(name = "FULL_TRAN_NAME")
	private String full_tran_name;
	 
	@Column(name = "LAST_APPROVED_BY")
	private String last_approved_by;
	
	@Column(name = "STATE")
	private String state;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getROWNUM() {
		return ROWNUM;
	}

	public void setROWNUM(Long rOWNUM) {
		ROWNUM = rOWNUM;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Long getTran_ref_id() {
		return tran_ref_id;
	}

	public void setTran_ref_id(Long tran_ref_id) {
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

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
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

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
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

	public String getAlternate_name() {
		return alternate_name;
	}

	public void setAlternate_name(String alternate_name) {
		this.alternate_name = alternate_name;
	}

	public String getDocument_no() {
		return document_no;
	}

	public void setDocument_no(String document_no) {
		this.document_no = document_no;
	}

	public Date getDocument_date() {
		return document_date;
	}

	public void setDocument_date(Date document_date) {
		this.document_date = document_date;
	}

	public String getLr_no() {
		return lr_no;
	}

	public void setLr_no(String lr_no) {
		this.lr_no = lr_no;
	}

	public Date getLr_dt() {
		return lr_dt;
	}

	public void setLr_dt(Date lr_dt) {
		this.lr_dt = lr_dt;
	}

	public String getStn_or_challan_no() {
		return stn_or_challan_no;
	}

	public void setStn_or_challan_no(String stn_or_challan_no) {
		this.stn_or_challan_no = stn_or_challan_no;
	}

	public Date getStn_or_challan_dt() {
		return stn_or_challan_dt;
	}

	public void setStn_or_challan_dt(Date stn_or_challan_dt) {
		this.stn_or_challan_dt = stn_or_challan_dt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIns_user_id() {
		return ins_user_id;
	}

	public void setIns_user_id(String ins_user_id) {
		this.ins_user_id = ins_user_id;
	}

	public String getEntered_by() {
		return entered_by;
	}

	public void setEntered_by(String entered_by) {
		this.entered_by = entered_by;
	}

	public String getTran_type_name() {
		return tran_type_name;
	}

	public void setTran_type_name(String tran_type_name) {
		this.tran_type_name = tran_type_name;
	}

	public String getFull_tran_name() {
		return full_tran_name;
	}

	public void setFull_tran_name(String full_tran_name) {
		this.full_tran_name = full_tran_name;
	}

	public String getLast_approved_by() {
		return last_approved_by;
	}

	public void setLast_approved_by(String last_approved_by) {
		this.last_approved_by = last_approved_by;
	}

	@Override
	public String toString() {
		return "ActivityApproval [ROWNUM=" + ROWNUM + ", name=" + name + ", loc_nm=" + loc_nm + ", tran_type="
				+ tran_type + ", tran_ref_id=" + tran_ref_id + ", tran_no=" + tran_no + ", approval_type="
				+ approval_type + ", task_id=" + task_id + ", tran_type_description=" + tran_type_description
				+ ", fin_year_id=" + fin_year_id + ", stock_point_id=" + stock_point_id + ", loc_id=" + loc_id
				+ ", doc_type=" + doc_type + ", process_instance_id=" + process_instance_id + ", amount=" + amount
				+ ", group=" + group + ", recieved_on=" + recieved_on + ", initiator=" + initiator + ", alternate_name="
				+ alternate_name + ", document_no=" + document_no + ", document_date=" + document_date + ", lr_no="
				+ lr_no + ", lr_dt=" + lr_dt + ", stn_or_challan_no=" + stn_or_challan_no + ", stn_or_challan_dt="
				+ stn_or_challan_dt + ", type=" + type + ", ins_user_id=" + ins_user_id + ", entered_by=" + entered_by
				+ ", tran_type_name=" + tran_type_name + ", full_tran_name=" + full_tran_name + ", last_approved_by="
				+ last_approved_by + ", state=" + state + "]";
	}
	

}
