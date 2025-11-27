package com.excel.bean;



public class IrnCancelResponse {

	private String custom_fields;
	private String document_status;
	private String errors;
	private govt_response govt_response;
	private String group_id;
	private String gstin;
	private String owner_id;
	private String transaction_id;
	private String transaction_metadata;
	private EInvoiceData transaction;
	public class govt_response{
		private String Success;
		private String AckNo;
		private String AckDt;
		private String Irn;
		private String SignedInvoice;
		private String SignedQRCode;
		private String Status;
		private String EwbNo;
		private String EwbDt;
		private String EwbValidTill;
		public String getSuccess() {
			return Success;
		}
		public void setSuccess(String success) {
			Success = success;
		}
		public String getAckNo() {
			return AckNo;
		}
		public void setAckNo(String ackNo) {
			AckNo = ackNo;
		}
		public String getAckDt() {
			return AckDt;
		}
		public void setAckDt(String ackDt) {
			AckDt = ackDt;
		}
		public String getIrn() {
			return Irn;
		}
		public void setIrn(String irn) {
			Irn = irn;
		}
		public String getSignedInvoice() {
			return SignedInvoice;
		}
		public void setSignedInvoice(String signedInvoice) {
			SignedInvoice = signedInvoice;
		}
		public String getSignedQRCode() {
			return SignedQRCode;
		}
		public void setSignedQRCode(String signedQRCode) {
			SignedQRCode = signedQRCode;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		public String getEwbNo() {
			return EwbNo;
		}
		public void setEwbNo(String ewbNo) {
			EwbNo = ewbNo;
		}
		public String getEwbDt() {
			return EwbDt;
		}
		public void setEwbDt(String ewbDt) {
			EwbDt = ewbDt;
		}
		public String getEwbValidTill() {
			return EwbValidTill;
		}
		public void setEwbValidTill(String ewbValidTill) {
			EwbValidTill = ewbValidTill;
		}
		
    }
	public String getCustom_fields() {
		return custom_fields;
	}
	public void setCustom_fields(String custom_fields) {
		this.custom_fields = custom_fields;
	}
	public String getDocument_status() {
		return document_status;
	}
	public void setDocument_status(String document_status) {
		this.document_status = document_status;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public govt_response getGovt_response() {
		return govt_response;
	}
	public void setGovt_response(govt_response govt_response) {
		this.govt_response = govt_response;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public EInvoiceData getTransaction() {
		return transaction;
	}
	public void setTransaction(EInvoiceData transaction) {
		this.transaction = transaction;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getTransaction_metadata() {
		return transaction_metadata;
	}
	public void setTransaction_metadata(String transaction_metadata) {
		this.transaction_metadata = transaction_metadata;
	}
	
}
