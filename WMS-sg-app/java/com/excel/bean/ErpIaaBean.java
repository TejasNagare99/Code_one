package com.excel.bean;

import java.util.List;

public class ErpIaaBean {

	public String wms_iaa_number;
	private String iaa_flag;
	public String location;
	private String userName;
	public String description;
	private String company;
	public List<IaaDetails> details;
	
	//To save
	private String finYearId;
	private String periodCode;
	private Long headerId;
	private String stkAdjNo;
	private Long wh_tran_id;
	private boolean stock_cfa;
	private String action;
	private String batchInd;
	private String expInd;
	private String trans_no;
	private String appr_req;
	private Long adjDtlId;
	private String tranDate;
	public static class IaaDetails{

		private String reasonCode;
		private String out_prod_id;
		private String out_prod_qty;
		private String out_batch_id;
		private String out_stock_type;
		private String in_prod_id;
		private String in_prod_qty;
		private String in_batch_id;
		private String in_stock_type;
		public String getOut_prod_id() {
			return out_prod_id;
		}
		public void setOut_prod_id(String out_prod_id) {
			this.out_prod_id = out_prod_id;
		}
		public String getOut_prod_qty() {
			return out_prod_qty;
		}
		public void setOut_prod_qty(String out_prod_qty) {
			this.out_prod_qty = out_prod_qty;
		}
		public String getOut_batch_id() {
			return out_batch_id;
		}
		public void setOut_batch_id(String out_batch_id) {
			this.out_batch_id = out_batch_id;
		}
		public String getOut_stock_type() {
			return out_stock_type;
		}
		public void setOut_stock_type(String out_stock_type) {
			this.out_stock_type = out_stock_type;
		}
		public String getIn_prod_id() {
			return in_prod_id;
		}
		public void setIn_prod_id(String in_prod_id) {
			this.in_prod_id = in_prod_id;
		}
		public String getIn_prod_qty() {
			return in_prod_qty;
		}
		public void setIn_prod_qty(String in_prod_qty) {
			this.in_prod_qty = in_prod_qty;
		}
		public String getIn_batch_id() {
			return in_batch_id;
		}
		public void setIn_batch_id(String in_batch_id) {
			this.in_batch_id = in_batch_id;
		}
		public String getIn_stock_type() {
			return in_stock_type;
		}
		public void setIn_stock_type(String in_stock_type) {
			this.in_stock_type = in_stock_type;
		}
		public String getReasonCode() {
			return reasonCode;
		}
		public void setReasonCode(String reasonCode) {
			this.reasonCode = reasonCode;
		}
		
		
		
	}
	
	public String getWms_iaa_number() {
		return wms_iaa_number;
	}



	public void setWms_iaa_number(String wms_iaa_number) {
		this.wms_iaa_number = wms_iaa_number;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}


	public List<IaaDetails> getDetails() {
		return details;
	}



	public void setDetails(List<IaaDetails> details) {
		this.details = details;
	}



	public String getIaa_flag() {
		return iaa_flag;
	}



	public void setIaa_flag(String iaa_flag) {
		this.iaa_flag = iaa_flag;
	}



	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}



	public String getFinYearId() {
		return finYearId;
	}



	public void setFinYearId(String finYearId) {
		this.finYearId = finYearId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}




	public String getStkAdjNo() {
		return stkAdjNo;
	}



	public void setStkAdjNo(String stkAdjNo) {
		this.stkAdjNo = stkAdjNo;
	}



	public boolean isStock_cfa() {
		return stock_cfa;
	}



	public void setStock_cfa(boolean stock_cfa) {
		this.stock_cfa = stock_cfa;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public String getBatchInd() {
		return batchInd;
	}



	public void setBatchInd(String batchInd) {
		this.batchInd = batchInd;
	}



	public String getExpInd() {
		return expInd;
	}



	public void setExpInd(String expInd) {
		this.expInd = expInd;
	}



	public Long getHeaderId() {
		return headerId;
	}



	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}



	public String getTrans_no() {
		return trans_no;
	}



	public void setTrans_no(String trans_no) {
		this.trans_no = trans_no;
	}



	public Long getWh_tran_id() {
		return wh_tran_id;
	}



	public void setWh_tran_id(Long wh_tran_id) {
		this.wh_tran_id = wh_tran_id;
	}



	public String getAppr_req() {
		return appr_req;
	}



	public void setAppr_req(String appr_req) {
		this.appr_req = appr_req;
	}



	public Long getAdjDtlId() {
		return adjDtlId;
	}



	public void setAdjDtlId(Long adjDtlId) {
		this.adjDtlId = adjDtlId;
	}



	public String getPeriodCode() {
		return periodCode;
	}



	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}



	public String getTranDate() {
		return tranDate;
	}



	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}



}
