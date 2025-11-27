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
@Table(name="Allocation_report_1")
@NamedStoredProcedureQuery(
		name="Allocation_report_1",
				procedureName = "ANG_ALLOC_REPORT1_WITHPARM",
				parameters = {
						@StoredProcedureParameter(name = "pdiv_id", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pfin_year", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pperiod_code" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "psub_comp_id" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pprod_sub_type_id" , mode = ParameterMode.IN, type = String.class),

				},
		resultClasses = Allocation_report_1.class
)
@NamedStoredProcedureQuery(
		name="Allocation_report_1_current",
				procedureName = "ANG_ALLOC_REPORT1_WITHPARM_CURRENT",
				parameters = {
						@StoredProcedureParameter(name = "pdiv_id", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pfin_year", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pperiod_code" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "psub_comp_id" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pprod_sub_type_id" , mode = ParameterMode.IN, type = String.class),

				},
		resultClasses = Allocation_report_1.class
)
@NamedStoredProcedureQuery(
		name="Allocation_report_1_previous",
				procedureName = "ANG_ALLOC_REPORT1_WITHPARM_ARCHIEVE",
				parameters = {
						@StoredProcedureParameter(name = "pdiv_id", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pfin_year", mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pperiod_code" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "psub_comp_id" , mode = ParameterMode.IN, type = String.class),
						@StoredProcedureParameter(name = "pprod_sub_type_id" , mode = ParameterMode.IN, type = String.class),

				},
		resultClasses = Allocation_report_1.class
)
public class Allocation_report_1 {

	@Id
	@Column(name="ROWNUM")
	private Long rownum;
	
	@Column(name = "BU")
	private String bu;
	
	@Column(name = "TEAM_NAME")
	private String team_name;
	
	@Column(name = "REQUEST_BY")
	private String request_by;
	
	@Column(name = "REQ_DATE")
	private String req_date;
	
	@Column(name = "EMPL_NO")
	private String empl_no;
	
	
	@Column(name = "NAME_OF_COLLEG")
	private String name_of_colleg;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "MEETING_DATE")
	private String meeting_date;
	
	@Column(name = "MEETING_NAME")
	private String meeting_name;
	
	@Column(name = "PROD_ID")
	private String prod_id;
	
	@Column(name = "PROD_CODE")
	private String prod_code;
	
	@Column(name = "ITEM_DISPATCHED")
	private String item_dispatched;
	
	@Column(name = "REQUEST_QTY")
	private BigDecimal request_qty;
	
	@Column(name = "ALLOC_QTY")
	private BigDecimal alloc_qty;
	
	@Column(name = "DISPATCH_QTY")
	private BigDecimal dispatch_qty;
	
	@Column(name = "CHALLAN_NO")
	private String challan_no;
	
	@Column(name = "CHALLAN_DATE")
	private String challan_date;
	
	@Column(name = "NO_OF_CASES")
	private Long no_of_cases;
	
	@Column(name = "DISPATCH_STATUS")
	private String dispatch_status;
	
	@Column(name = "TIME_LINE")
	private String time_line;
	
	@Column(name = "COURIER_NAME")
	private String courier_name;
	
	@Column(name = "MODE")
	private String mode;
	
	@Column(name = "LR_NO")
	private String lr_no;
	
	@Column(name = "LR_DATE")
	private String lr_date;
	
	@Column(name = "DELIVERY_DATE")
	private String delivery_date;
	
	@Column(name = "POD_DETAIL")
	private String pod_detail;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "REQ_APPR_STATUS")
	private String req_appr_status;

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getRequest_by() {
		return request_by;
	}

	public void setRequest_by(String request_by) {
		this.request_by = request_by;
	}

	public String getReq_date() {
		return req_date;
	}

	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}

	public String getEmpl_no() {
		return empl_no;
	}

	public void setEmpl_no(String empl_no) {
		this.empl_no = empl_no;
	}

	public String getName_of_colleg() {
		return name_of_colleg;
	}

	public void setName_of_colleg(String name_of_colleg) {
		this.name_of_colleg = name_of_colleg;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getMeeting_date() {
		return meeting_date;
	}

	public void setMeeting_date(String meeting_date) {
		this.meeting_date = meeting_date;
	}

	public String getMeeting_name() {
		return meeting_name;
	}

	public void setMeeting_name(String meeting_name) {
		this.meeting_name = meeting_name;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	public String getItem_dispatched() {
		return item_dispatched;
	}

	public void setItem_dispatched(String item_dispatched) {
		this.item_dispatched = item_dispatched;
	}

	public BigDecimal getRequest_qty() {
		return request_qty;
	}

	public void setRequest_qty(BigDecimal request_qty) {
		this.request_qty = request_qty;
	}

	public BigDecimal getAlloc_qty() {
		return alloc_qty;
	}

	public void setAlloc_qty(BigDecimal alloc_qty) {
		this.alloc_qty = alloc_qty;
	}

	public BigDecimal getDispatch_qty() {
		return dispatch_qty;
	}

	public void setDispatch_qty(BigDecimal dispatch_qty) {
		this.dispatch_qty = dispatch_qty;
	}

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

	

	public Long getNo_of_cases() {
		return no_of_cases;
	}

	public void setNo_of_cases(Long no_of_cases) {
		this.no_of_cases = no_of_cases;
	}

	public String getDispatch_status() {
		return dispatch_status;
	}

	public void setDispatch_status(String dispatch_status) {
		this.dispatch_status = dispatch_status;
	}

	public String getTime_line() {
		return time_line;
	}

	public void setTime_line(String time_line) {
		this.time_line = time_line;
	}

	public String getCourier_name() {
		return courier_name;
	}

	public void setCourier_name(String courier_name) {
		this.courier_name = courier_name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getLr_no() {
		return lr_no;
	}

	public void setLr_no(String lr_no) {
		this.lr_no = lr_no;
	}

	public String getLr_date() {
		return lr_date;
	}

	public void setLr_date(String lr_date) {
		this.lr_date = lr_date;
	}

	public String getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getPod_detail() {
		return pod_detail;
	}

	public void setPod_detail(String pod_detail) {
		this.pod_detail = pod_detail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReq_appr_status() {
		return req_appr_status;
	}

	public void setReq_appr_status(String req_appr_status) {
		this.req_appr_status = req_appr_status;
	}

	public Long getRownum() {
		return rownum;
	}

	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}
	
	

}
