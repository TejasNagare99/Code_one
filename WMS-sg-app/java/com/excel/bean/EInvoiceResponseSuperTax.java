package com.excel.bean;

public class EInvoiceResponseSuperTax {
	
	
	private String AckNo;
	private String AckDate;
	private String Irn;
	private String CustDocNo;
	private String Record;
	private boolean Success;
	private String LineItem;
	private String Messages;
	private String Fy;
	private String SignedInvoice;
	private String SignedQRCode;
	private String RawQRCode;
	private String Status;
	private String EwbNo;
	private String EwbDt;
	private String EwbValidTill;
	private SellerDtls SellerDtls;
	private DocDtls DocDtls;
	
	
	public class SellerDtls{
		private String Gstin;

		public String getGstin() {
			return Gstin;
		}

		public void setGstin(String gstin) {
			Gstin = gstin;
		}
	
		
	}
	public class DocDtls {
		private String Typ;
		private String No;
		private String Dt;
		public String getTyp() {
			return Typ;
		}
		public void setTyp(String typ) {
			Typ = typ;
		}
		public String getNo() {
			return No;
		}
		public void setNo(String no) {
			No = no;
		}
		public String getDt() {
			return Dt;
		}
		public void setDt(String dt) {
			Dt = dt;
		}
		
	}
	public String getAckNo() {
		return AckNo;
	}
	public void setAckNo(String ackNo) {
		AckNo = ackNo;
	}
	public String getAckDt() {
		return AckDate;
	}
	public void setAckDt(String ackDt) {
		AckDate = ackDt;
	}
	public String getIrn() {
		return Irn;
	}
	public void setIrn(String irn) {
		Irn = irn;
	}
	public String getCustDocNo() {
		return CustDocNo;
	}
	public void setCustDocNo(String custDocNo) {
		CustDocNo = custDocNo;
	}
	public String getRecord() {
		return Record;
	}
	public void setRecord(String record) {
		Record = record;
	}
	public boolean getSuccess() {
		return Success;
	}
	public void setSuccess(boolean success) {
		Success = success;
	}
	public String getMessages() {
		return Messages;
	}
	public void setMessages(String messages) {
		Messages = messages;
	}
	public String getFy() {
		return Fy;
	}
	public void setFy(String fy) {
		Fy = fy;
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
	public String getRawQRCode() {
		return RawQRCode;
	}
	public void setRawQRCode(String rawQRCode) {
		RawQRCode = rawQRCode;
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
	public SellerDtls getSellerDtls() {
		return SellerDtls;
	}
	public void setSellerDtls(SellerDtls sellerDtls) {
		SellerDtls = sellerDtls;
	}
	public DocDtls getDocDtls() {
		return DocDtls;
	}
	public void setDocDtls(DocDtls docDtls) {
		DocDtls = docDtls;
	}
	public String getLineItem() {
		return LineItem;
	}
	public void setLineItem(String lineItem) {
		LineItem = lineItem;
	}
	
	
	@Override
	public String toString() {
		return "EInvoiceResponseSuperTax [AckNo=" + AckNo + ", AckDt=" + AckDate + ", Irn=" + Irn + ", CustDocNo="
				+ CustDocNo + ", Record=" + Record + ", Success=" + Success + ", LineItem=" + LineItem + ", Messages="
				+ Messages + ", Fy=" + Fy + ", SignedInvoice=" + SignedInvoice + ", SignedQRCode=" + SignedQRCode
				+ ", RawQRCode=" + RawQRCode + ", Status=" + Status + ", EwbNo=" + EwbNo + ", EwbDt=" + EwbDt
				+ ", EwbValidTill=" + EwbValidTill + ", SellerDtls=" + SellerDtls + ", DocDtls=" + DocDtls + "]";
	}
	
	
	
	
	

}
