package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class IAABean {
	
	private String in_item_accept;
	private String in_batch_accept;
	private String out_stock_type;
	private String in_stock_type;
	private String out_tran_type;
	private String in_tran_type;
	private String new_batch_accept;
	private String in_qty_accept;
	private String in_rate_accept;
	private String item_change_accept;
	private String instk;
	private String outstk;
	
	private String ipAddress;
	private String companyCode;
	private String appr_req;
	private String trans_no;
	private boolean stock_cfa;
	private String action;
	private Long adjDtlId;
	private String batchInd;
	private String expInd;
	
	
	// UI DATA
	
	private Long wh_tran_id;
	private Long headerId;
	private String currPeriodCode;
	private String currFinYear;
	private String empId;
	private Long sendSubCompId;
	private Long sendLocId;
	private String receipt_datetime;
	private String location;
	private String trnDate;
	private Long reasonId;
	private String headerRemark;
	private String stkAdjNo;
	
	private Long setRemoveProdId;
	private Object removeProdId;
	private Long setRemoveBatchId;
	private Object removeBatchId;
	private String removePack;
	private String removeStockType;
	private BigDecimal removeQty;
	private BigDecimal  removeRate;
	private BigDecimal removeItemStkQtyBA;
	private BigDecimal removeItemStkValueBA;
	private BigDecimal removeBatchStkQtyBA;
	private BigDecimal removeBatchStkValueBA;
	private BigDecimal removeItemStkQtyAA;
	private BigDecimal removeItemStkValueAA;
	private BigDecimal removeBatchStkQtyAA;
	private BigDecimal removeBatchStkValueAA;
	
	private Long setAddProdId;
	private Object addProdId;
	private Long setAddBatchId;
	private Object addBatchId;
	private String addPack;
	private String addStockType;
	private BigDecimal addQty;
	private BigDecimal addRate;
	private BigDecimal addItemStkQtyBA;
	private BigDecimal addItemStkValueBA;
	private BigDecimal addBatchStkQtyBA;
	private BigDecimal addBatchStkValueBA;
	private BigDecimal addItemStkQtyAA;
	private BigDecimal addItemStkValueAA;
	private BigDecimal addBatchStkQtyAA;
	private BigDecimal addBatchStkValueAA;
	private String addRemark;
	
	// Approval UI
	
	private Long stkAdjHeaderId;
	private String headerRemarkAppr;
	private String detailRemarkAppr;
	
	private Long detailId;
	private String email;
	
	private List<String> adjDtlStatus;
	private List<String> adjDtlIds;
	private String status;
	private List<String> emailList;
	private String emailreqInd;
	
	public String getEmailreqInd() {
		return emailreqInd;
	}
	public void setEmailreqInd(String emailreqInd) {
		this.emailreqInd = emailreqInd;
	}
	public List<String> getEmailList() {
		return emailList;
	}
	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}
	public String getIn_item_accept() {
		return in_item_accept;
	}
	public void setIn_item_accept(String in_item_accept) {
		this.in_item_accept = in_item_accept;
	}
	public String getIn_batch_accept() {
		return in_batch_accept;
	}
	public void setIn_batch_accept(String in_batch_accept) {
		this.in_batch_accept = in_batch_accept;
	}
	public String getOut_stock_type() {
		return out_stock_type;
	}
	public void setOut_stock_type(String out_stock_type) {
		this.out_stock_type = out_stock_type;
	}
	public String getIn_stock_type() {
		return in_stock_type;
	}
	public void setIn_stock_type(String in_stock_type) {
		this.in_stock_type = in_stock_type;
	}
	public String getOut_tran_type() {
		return out_tran_type;
	}
	public void setOut_tran_type(String out_tran_type) {
		this.out_tran_type = out_tran_type;
	}
	public String getIn_tran_type() {
		return in_tran_type;
	}
	public void setIn_tran_type(String in_tran_type) {
		this.in_tran_type = in_tran_type;
	}
	public String getNew_batch_accept() {
		return new_batch_accept;
	}
	public void setNew_batch_accept(String new_batch_accept) {
		this.new_batch_accept = new_batch_accept;
	}
	public String getIn_qty_accept() {
		return in_qty_accept;
	}
	public void setIn_qty_accept(String in_qty_accept) {
		this.in_qty_accept = in_qty_accept;
	}
	public String getIn_rate_accept() {
		return in_rate_accept;
	}
	public void setIn_rate_accept(String in_rate_accept) {
		this.in_rate_accept = in_rate_accept;
	}
	public String getItem_change_accept() {
		return item_change_accept;
	}
	public void setItem_change_accept(String item_change_accept) {
		this.item_change_accept = item_change_accept;
	}
	public String getInstk() {
		return instk;
	}
	public void setInstk(String instk) {
		this.instk = instk;
	}
	public String getOutstk() {
		return outstk;
	}
	public void setOutstk(String outstk) {
		this.outstk = outstk;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Long getHeaderId() {
		return headerId;
	}
	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}
	public String getCurrPeriodCode() {
		return currPeriodCode;
	}
	public void setCurrPeriodCode(String currPeriodCode) {
		this.currPeriodCode = currPeriodCode;
	}
	public String getCurrFinYear() {
		return currFinYear;
	}
	public void setCurrFinYear(String currFinYear) {
		this.currFinYear = currFinYear;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Long getSendSubCompId() {
		return sendSubCompId;
	}
	public void setSendSubCompId(Long sendSubCompId) {
		this.sendSubCompId = sendSubCompId;
	}
	public Long getSendLocId() {
		return sendLocId;
	}
	public void setSendLocId(Long sendLocId) {
		this.sendLocId = sendLocId;
	}
	public String getReceipt_datetime() {
		return receipt_datetime;
	}
	public void setReceipt_datetime(String receipt_datetime) {
		this.receipt_datetime = receipt_datetime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public Long getReasonId() {
		return reasonId;
	}
	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}
	public String getHeaderRemark() {
		return headerRemark;
	}
	public void setHeaderRemark(String headerRemark) {
		this.headerRemark = headerRemark;
	}
	public String getStkAdjNo() {
		return stkAdjNo;
	}
	public void setStkAdjNo(String stkAdjNo) {
		this.stkAdjNo = stkAdjNo;
	}
	public Long getSetRemoveProdId() {
		return setRemoveProdId;
	}
	public void setSetRemoveProdId(Long setRemoveProdId) {
		this.setRemoveProdId = setRemoveProdId;
	}
	public Object getRemoveProdId() {
		return removeProdId;
	}
	public void setRemoveProdId(Object removeProdId) {
		this.removeProdId = removeProdId;
	}
	public Long getSetRemoveBatchId() {
		return setRemoveBatchId;
	}
	public void setSetRemoveBatchId(Long setRemoveBatchId) {
		this.setRemoveBatchId = setRemoveBatchId;
	}
	public Object getRemoveBatchId() {
		return removeBatchId;
	}
	public void setRemoveBatchId(Object removeBatchId) {
		this.removeBatchId = removeBatchId;
	}
	public String getRemovePack() {
		return removePack;
	}
	public void setRemovePack(String removePack) {
		this.removePack = removePack;
	}
	public String getRemoveStockType() {
		return removeStockType;
	}
	public void setRemoveStockType(String removeStockType) {
		this.removeStockType = removeStockType;
	}
	public BigDecimal getRemoveQty() {
		return removeQty;
	}
	public void setRemoveQty(BigDecimal removeQty) {
		this.removeQty = removeQty;
	}
	public BigDecimal getRemoveRate() {
		return removeRate;
	}
	public void setRemoveRate(BigDecimal removeRate) {
		this.removeRate = removeRate;
	}
	public BigDecimal getRemoveItemStkQtyBA() {
		return removeItemStkQtyBA;
	}
	public void setRemoveItemStkQtyBA(BigDecimal removeItemStkQtyBA) {
		this.removeItemStkQtyBA = removeItemStkQtyBA;
	}
	public BigDecimal getRemoveItemStkValueBA() {
		return removeItemStkValueBA;
	}
	public void setRemoveItemStkValueBA(BigDecimal removeItemStkValueBA) {
		this.removeItemStkValueBA = removeItemStkValueBA;
	}
	public BigDecimal getRemoveBatchStkQtyBA() {
		return removeBatchStkQtyBA;
	}
	public void setRemoveBatchStkQtyBA(BigDecimal removeBatchStkQtyBA) {
		this.removeBatchStkQtyBA = removeBatchStkQtyBA;
	}
	public BigDecimal getRemoveBatchStkValueBA() {
		return removeBatchStkValueBA;
	}
	public void setRemoveBatchStkValueBA(BigDecimal removeBatchStkValueBA) {
		this.removeBatchStkValueBA = removeBatchStkValueBA;
	}
	public BigDecimal getRemoveItemStkQtyAA() {
		return removeItemStkQtyAA;
	}
	public void setRemoveItemStkQtyAA(BigDecimal removeItemStkQtyAA) {
		this.removeItemStkQtyAA = removeItemStkQtyAA;
	}
	public BigDecimal getRemoveItemStkValueAA() {
		return removeItemStkValueAA;
	}
	public void setRemoveItemStkValueAA(BigDecimal removeItemStkValueAA) {
		this.removeItemStkValueAA = removeItemStkValueAA;
	}
	public BigDecimal getRemoveBatchStkQtyAA() {
		return removeBatchStkQtyAA;
	}
	public void setRemoveBatchStkQtyAA(BigDecimal removeBatchStkQtyAA) {
		this.removeBatchStkQtyAA = removeBatchStkQtyAA;
	}
	public BigDecimal getRemoveBatchStkValueAA() {
		return removeBatchStkValueAA;
	}
	public void setRemoveBatchStkValueAA(BigDecimal removeBatchStkValueAA) {
		this.removeBatchStkValueAA = removeBatchStkValueAA;
	}
	public Long getSetAddProdId() {
		return setAddProdId;
	}
	public void setSetAddProdId(Long setAddProdId) {
		this.setAddProdId = setAddProdId;
	}
	public Object getAddProdId() {
		return addProdId;
	}
	public void setAddProdId(Object addProdId) {
		this.addProdId = addProdId;
	}
	public Long getSetAddBatchId() {
		return setAddBatchId;
	}
	public void setSetAddBatchId(Long setAddBatchId) {
		this.setAddBatchId = setAddBatchId;
	}
	public Object getAddBatchId() {
		return addBatchId;
	}
	public void setAddBatchId(Object addBatchId) {
		this.addBatchId = addBatchId;
	}
	public String getAddPack() {
		return addPack;
	}
	public void setAddPack(String addPack) {
		this.addPack = addPack;
	}
	public String getAddStockType() {
		return addStockType;
	}
	public void setAddStockType(String addStockType) {
		this.addStockType = addStockType;
	}
	public BigDecimal getAddQty() {
		return addQty;
	}
	public void setAddQty(BigDecimal addQty) {
		this.addQty = addQty;
	}
	public BigDecimal getAddRate() {
		return addRate;
	}
	public void setAddRate(BigDecimal addRate) {
		this.addRate = addRate;
	}
	public BigDecimal getAddItemStkQtyBA() {
		return addItemStkQtyBA;
	}
	public void setAddItemStkQtyBA(BigDecimal addItemStkQtyBA) {
		this.addItemStkQtyBA = addItemStkQtyBA;
	}
	public BigDecimal getAddItemStkValueBA() {
		return addItemStkValueBA;
	}
	public void setAddItemStkValueBA(BigDecimal addItemStkValueBA) {
		this.addItemStkValueBA = addItemStkValueBA;
	}
	public BigDecimal getAddBatchStkQtyBA() {
		return addBatchStkQtyBA;
	}
	public void setAddBatchStkQtyBA(BigDecimal addBatchStkQtyBA) {
		this.addBatchStkQtyBA = addBatchStkQtyBA;
	}
	public BigDecimal getAddBatchStkValueBA() {
		return addBatchStkValueBA;
	}
	public void setAddBatchStkValueBA(BigDecimal addBatchStkValueBA) {
		this.addBatchStkValueBA = addBatchStkValueBA;
	}
	public BigDecimal getAddItemStkQtyAA() {
		return addItemStkQtyAA;
	}
	public void setAddItemStkQtyAA(BigDecimal addItemStkQtyAA) {
		this.addItemStkQtyAA = addItemStkQtyAA;
	}
	public BigDecimal getAddItemStkValueAA() {
		return addItemStkValueAA;
	}
	public void setAddItemStkValueAA(BigDecimal addItemStkValueAA) {
		this.addItemStkValueAA = addItemStkValueAA;
	}
	public BigDecimal getAddBatchStkQtyAA() {
		return addBatchStkQtyAA;
	}
	public void setAddBatchStkQtyAA(BigDecimal addBatchStkQtyAA) {
		this.addBatchStkQtyAA = addBatchStkQtyAA;
	}
	public BigDecimal getAddBatchStkValueAA() {
		return addBatchStkValueAA;
	}
	public void setAddBatchStkValueAA(BigDecimal addBatchStkValueAA) {
		this.addBatchStkValueAA = addBatchStkValueAA;
	}
	public String getAddRemark() {
		return addRemark;
	}
	public void setAddRemark(String addRemark) {
		this.addRemark = addRemark;
	}
	public String getAppr_req() {
		return appr_req;
	}
	public void setAppr_req(String appr_req) {
		this.appr_req = appr_req;
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
	public Long getAdjDtlId() {
		return adjDtlId;
	}
	public void setAdjDtlId(Long adjDtlId) {
		this.adjDtlId = adjDtlId;
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
	public String getTrnDate() {
		return trnDate;
	}
	public void setTrnDate(String trnDate) {
		this.trnDate = trnDate;
	}
	public Long getStkAdjHeaderId() {
		return stkAdjHeaderId;
	}
	public void setStkAdjHeaderId(Long stkAdjHeaderId) {
		this.stkAdjHeaderId = stkAdjHeaderId;
	}
	public String getHeaderRemarkAppr() {
		return headerRemarkAppr;
	}
	public void setHeaderRemarkAppr(String headerRemarkAppr) {
		this.headerRemarkAppr = headerRemarkAppr;
	}
	public String getDetailRemarkAppr() {
		return detailRemarkAppr;
	}
	public void setDetailRemarkAppr(String detailRemarkAppr) {
		this.detailRemarkAppr = detailRemarkAppr;
	}
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getAdjDtlStatus() {
		return adjDtlStatus;
	}
	public void setAdjDtlStatus(List<String> adjDtlStatus) {
		this.adjDtlStatus = adjDtlStatus;
	}
	public List<String> getAdjDtlIds() {
		return adjDtlIds;
	}
	public void setAdjDtlIds(List<String> adjDtlIds) {
		this.adjDtlIds = adjDtlIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}
