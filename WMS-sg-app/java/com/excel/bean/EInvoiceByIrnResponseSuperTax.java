package com.excel.bean;

import java.util.List;

import com.excel.bean.EInvoiceByIrnDataSuperTax.DocDtls;
import com.excel.bean.EInvoiceByIrnDataSuperTax.EwbDtls;
import com.excel.bean.EInvoiceByIrnDataSuperTax.SellerDtls;

public class EInvoiceByIrnResponseSuperTax {
	
	private List<EInoviceRespSuperTax> ewayBill;
	
	public class EInoviceRespSuperTax{
		private DocDtls DocDtls;
		private SellerDtls SellerDtls;
		private EwbDtls EwbDtls;
		private boolean Success;
		private String EwbNo;
		private String EwbDate;
		private String ValidUpto;
		private String Record;
		private String Irn;
		public DocDtls getDocDtls() {
			return DocDtls;
		}
		public void setDocDtls(DocDtls docDtls) {
			DocDtls = docDtls;
		}
		public SellerDtls getSellerDtls() {
			return SellerDtls;
		}
		public void setSellerDtls(SellerDtls sellerDtls) {
			SellerDtls = sellerDtls;
		}
		public EwbDtls getEwbDtls() {
			return EwbDtls;
		}
		public void setEwbDtls(EwbDtls ewbDtls) {
			EwbDtls = ewbDtls;
		}
		public boolean getSuccess() {
			return Success;
		}
		public void setSuccess(boolean success) {
			Success = success;
		}
		public String getEwbNo() {
			return EwbNo;
		}
		public void setEwbNo(String ewbNo) {
			EwbNo = ewbNo;
		}
		public String getEwbDate() {
			return EwbDate;
		}
		public void setEwbDate(String ewbDate) {
			EwbDate = ewbDate;
		}
		public String getValidUpto() {
			return ValidUpto;
		}
		public void setValidUpto(String validUpto) {
			ValidUpto = validUpto;
		}
		public String getRecord() {
			return Record;
		}
		public void setRecord(String record) {
			Record = record;
		}
		public String getIrn() {
			return Irn;
		}
		public void setIrn(String irn) {
			Irn = irn;
		}
	}

	public List<EInoviceRespSuperTax> getEwayBill() {
		return ewayBill;
	}

	public void setEwayBill(List<EInoviceRespSuperTax> ewayBill) {
		this.ewayBill = ewayBill;
	}
	
	
	
	
	
}
