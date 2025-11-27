package com.excel.bean;

public class EwayBillCancelResponseSuperTax {

	private int record;
	private String ewaybill_number;
	private String ewaybill_cancel_reason;
	private String ewaybill_cancel_remark;
	private boolean status;
	private String message;
	public int getRecord() {
		return record;
	}
	public void setRecord(int record) {
		this.record = record;
	}
	public String getEwaybill_number() {
		return ewaybill_number;
	}
	public void setEwaybill_number(String ewaybill_number) {
		this.ewaybill_number = ewaybill_number;
	}
	public String getEwaybill_cancel_reason() {
		return ewaybill_cancel_reason;
	}
	public void setEwaybill_cancel_reason(String ewaybill_cancel_reason) {
		this.ewaybill_cancel_reason = ewaybill_cancel_reason;
	}
	public String getEwaybill_cancel_remark() {
		return ewaybill_cancel_remark;
	}
	public void setEwaybill_cancel_remark(String ewaybill_cancel_remark) {
		this.ewaybill_cancel_remark = ewaybill_cancel_remark;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
