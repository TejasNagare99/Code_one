package com.excel.bean;

import java.util.List;

public class EWayBillCancelDataSuperTax {

	private List<EwayBillCancelDataInner> ewaybills;

	public List<EwayBillCancelDataInner> getEwaybills() {
		return ewaybills;
	}

	public void setEwaybills(List<EwayBillCancelDataInner> ewaybills) {
		this.ewaybills = ewaybills;
	}

	public class EwayBillCancelDataInner {
		private String ewaybill_no;
		private String ewaybill_cancel_reason;
		private String ewaybill_cancel_remark;
		
		public EwayBillCancelDataInner() {
		}

		public EwayBillCancelDataInner(String ewaybill_no) {
			super();
			this.ewaybill_no = ewaybill_no;
		}

		public String getEwaybill_no() {
			return ewaybill_no;
		}

		public void setEwaybill_no(String ewaybill_no) {
			this.ewaybill_no = ewaybill_no;
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

	}

}
