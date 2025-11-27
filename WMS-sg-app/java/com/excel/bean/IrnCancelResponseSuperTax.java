package com.excel.bean;

import java.util.List;

public class IrnCancelResponseSuperTax {

	private List<IrnCancelDataSuperTax> invoices;

	public List<IrnCancelDataSuperTax> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<IrnCancelDataSuperTax> invoices) {
		this.invoices = invoices;
	}

	public class IrnCancelDataSuperTax {
		private String Record;
		private boolean Success;
		private String Messages;
		private String Status;
		private String Irn;

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

		public String getStatus() {
			return Status;
		}

		public void setStatus(String status) {
			Status = status;
		}

		public String getIrn() {
			return Irn;
		}

		public void setIrn(String irn) {
			Irn = irn;
		}

	}
}
