package com.excel.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class StockWithdrawalBean {
	
	private String batch_no;
	private BigDecimal rate;
	private BigDecimal qty;
	private BigDecimal stock;
	private BigDecimal value;
	private String mfg_dt;
	private String exp_dt;
	private Long batch_id;
	private Long detail_id;
	private Long div_id;
	private Integer cases;
	
	private String ipAddress;
	private String companyCode;
	
    // UI
	
	private Long headerId;
	private String action;
	private Long detailId;
	private Long divId;
	private Long sendSubCompId;
	private Long sendLocId;
	
	private String currPeriodCode;
	private String currFinYear;
	private String empId;
	
	private String stkWithdrawalNo;
	private String period;
	private String stkWithdrawalDate;
	private String stkWithdrawalType;
	
	private Long senderId;
	private String senderName;
	private String address1;
	private String address2;
	private String address3;
	private String destination;
	private String transporter;
	private String lrnumber;
	private String lrdate;
	private Long noOfCases;
	private Long stateId;
	private String displayMsg;
	private String remarks;
	
	private Long productId;
	private String stockType;
	
	private List<Long> batchIds;
	private List<Long> detailIds;
	private List<BigDecimal> batchStocks;
	private List<BigDecimal> rates;
	private List<BigDecimal> batchWthQtys;
	private List<BigDecimal> batchCases;
	private List<BigDecimal> stkQtyAfterWth;
	
	private Long pBatchId;
	private BigDecimal pBatchQty;
	private BigDecimal pBatchRate;
	private BigDecimal pBatchCases;
	private String appr_req;
	
	private String nlrdate;
	private String nstkWithdrawalDate;
	
	private List<Long> headerIds;
	private JSONObject headerRemarksAppr;
	private List<Long> remarkIds;
	private List<String> remarkss;
	private String user_name;
	private String email;
	
	private List<BigDecimal> weights;
	private List<BigDecimal> casess;
	private String lorryNumber;
	private BigDecimal netWeight;
	private BigDecimal totalCases;
	
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getMfg_dt() {
		return mfg_dt;
	}
	public void setMfg_dt(String mfg_dt) {
		this.mfg_dt = mfg_dt;
	}
	public String getExp_dt() {
		return exp_dt;
	}
	public void setExp_dt(String exp_dt) {
		this.exp_dt = exp_dt;
	}
	public Long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(Long batch_id) {
		this.batch_id = batch_id;
	}
	public Long getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}
	public Long getDiv_id() {
		return div_id;
	}
	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}
	public Integer getCases() {
		return cases;
	}
	public void setCases(Integer cases) {
		this.cases = cases;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	public Long getDivId() {
		return divId;
	}
	public void setDivId(Long divId) {
		this.divId = divId;
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
	public String getStkWithdrawalNo() {
		return stkWithdrawalNo;
	}
	public void setStkWithdrawalNo(String stkWithdrawalNo) {
		this.stkWithdrawalNo = stkWithdrawalNo;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getStkWithdrawalDate() {
		return stkWithdrawalDate;
	}
	public void setStkWithdrawalDate(String stkWithdrawalDate) {
		this.stkWithdrawalDate = stkWithdrawalDate;
	}
	public String getStkWithdrawalType() {
		return stkWithdrawalType;
	}
	public void setStkWithdrawalType(String stkWithdrawalType) {
		this.stkWithdrawalType = stkWithdrawalType;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getTransporter() {
		return transporter;
	}
	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
	public String getLrnumber() {
		return lrnumber;
	}
	public void setLrnumber(String lrnumber) {
		this.lrnumber = lrnumber;
	}
	public String getLrdate() {
		return lrdate;
	}
	public void setLrdate(String lrdate) {
		this.lrdate = lrdate;
	}
	public Long getNoOfCases() {
		return noOfCases;
	}
	public void setNoOfCases(Long noOfCases) {
		this.noOfCases = noOfCases;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getDisplayMsg() {
		return displayMsg;
	}
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public List<Long> getBatchIds() {
		return batchIds;
	}
	public void setBatchIds(List<Long> batchIds) {
		this.batchIds = batchIds;
	}
	public List<Long> getDetailIds() {
		return detailIds;
	}
	public void setDetailIds(List<Long> detailIds) {
		this.detailIds = detailIds;
	}
	public List<BigDecimal> getBatchStocks() {
		return batchStocks;
	}
	public void setBatchStocks(List<BigDecimal> batchStocks) {
		this.batchStocks = batchStocks;
	}
	public List<BigDecimal> getRates() {
		return rates;
	}
	public void setRates(List<BigDecimal> rates) {
		this.rates = rates;
	}
	public List<BigDecimal> getBatchWthQtys() {
		return batchWthQtys;
	}
	public void setBatchWthQtys(List<BigDecimal> batchWthQtys) {
		this.batchWthQtys = batchWthQtys;
	}
	public List<BigDecimal> getBatchCases() {
		return batchCases;
	}
	public void setBatchCases(List<BigDecimal> batchCases) {
		this.batchCases = batchCases;
	}
	public List<BigDecimal> getStkQtyAfterWth() {
		return stkQtyAfterWth;
	}
	public void setStkQtyAfterWth(List<BigDecimal> stkQtyAfterWth) {
		this.stkQtyAfterWth = stkQtyAfterWth;
	}
	public Long getpBatchId() {
		return pBatchId;
	}
	public void setpBatchId(Long pBatchId) {
		this.pBatchId = pBatchId;
	}
	public BigDecimal getpBatchQty() {
		return pBatchQty;
	}
	public void setpBatchQty(BigDecimal pBatchQty) {
		this.pBatchQty = pBatchQty;
	}
	public BigDecimal getpBatchRate() {
		return pBatchRate;
	}
	public void setpBatchRate(BigDecimal pBatchRate) {
		this.pBatchRate = pBatchRate;
	}
	public BigDecimal getpBatchCases() {
		return pBatchCases;
	}
	public void setpBatchCases(BigDecimal pBatchCases) {
		this.pBatchCases = pBatchCases;
	}
	public String getAppr_req() {
		return appr_req;
	}
	public void setAppr_req(String appr_req) {
		this.appr_req = appr_req;
	}
	public String getNlrdate() {
		return nlrdate;
	}
	public void setNlrdate(String nlrdate) {
		this.nlrdate = nlrdate;
	}
	public String getNstkWithdrawalDate() {
		return nstkWithdrawalDate;
	}
	public void setNstkWithdrawalDate(String nstkWithdrawalDate) {
		this.nstkWithdrawalDate = nstkWithdrawalDate;
	}
	public List<Long> getHeaderIds() {
		return headerIds;
	}
	public void setHeaderIds(List<Long> headerIds) {
		this.headerIds = headerIds;
	}
	public JSONObject getHeaderRemarksAppr() {
		return headerRemarksAppr;
	}
	public void setHeaderRemarksAppr(JSONObject headerRemarksAppr) {
		this.headerRemarksAppr = headerRemarksAppr;
	}
	public List<Long> getRemarkIds() {
		return remarkIds;
	}
	public void setRemarkIds(List<Long> remarkIds) {
		this.remarkIds = remarkIds;
	}
	public List<String> getRemarkss() {
		return remarkss;
	}
	public void setRemarkss(List<String> remarkss) {
		this.remarkss = remarkss;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<BigDecimal> getWeights() {
		return weights;
	}
	public void setWeights(List<BigDecimal> weights) {
		this.weights = weights;
	}
	public List<BigDecimal> getCasess() {
		return casess;
	}
	public void setCasess(List<BigDecimal> casess) {
		this.casess = casess;
	}
	public String getLorryNumber() {
		return lorryNumber;
	}
	public void setLorryNumber(String lorryNumber) {
		this.lorryNumber = lorryNumber;
	}
	public BigDecimal getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}
	public BigDecimal getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(BigDecimal totalCases) {
		this.totalCases = totalCases;
	}


	
}
