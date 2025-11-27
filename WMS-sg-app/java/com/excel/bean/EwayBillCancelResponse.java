package com.excel.bean;

import java.util.List;

public class EwayBillCancelResponse {

	private String ownerId;
	private String  gstin;
	private String irn;
	private String ewbNumber;
	private String ewbStatus;
	private ErrorDetails errorDetails;
	public class ErrorDetails{
		private String error_code;
		private String error_message;
		private String error_source;
		public String getError_code() {
			return error_code;
		}
		public void setError_code(String error_code) {
			this.error_code = error_code;
		}
		public String getError_message() {
			return error_message;
		}
		public void setError_message(String error_message) {
			this.error_message = error_message;
		}
		public String getError_source() {
			return error_source;
		}
		public void setError_source(String error_source) {
			this.error_source = error_source;
		}
		
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	public String getIrn() {
		return irn;
	}
	public void setIrn(String irn) {
		this.irn = irn;
	}
	public String getEwbNumber() {
		return ewbNumber;
	}
	public void setEwbNumber(String ewbNumber) {
		this.ewbNumber = ewbNumber;
	}
	public String getEwbStatus() {
		return ewbStatus;
	}
	public void setEwbStatus(String ewbStatus) {
		this.ewbStatus = ewbStatus;
	}
	public ErrorDetails getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(ErrorDetails errorDetails) {
		this.errorDetails = errorDetails;
	}
	
	
}
