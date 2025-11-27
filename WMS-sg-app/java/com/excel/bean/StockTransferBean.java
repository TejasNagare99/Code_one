package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class StockTransferBean {

	private Long prodId;
	private String prodName;
	private Long batch_id; 
	private Long prod_id;
	private String batch_no; 
	private String expiry_date; 
	private Long loc_id; 
	private BigDecimal trfRate; 
	private BigDecimal open_stock; 
	private BigDecimal in_stock; 
	private BigDecimal out_stock; 
	private BigDecimal witheld_qty; 
	private BigDecimal stock; 
	private BigDecimal sold_qty; 
	private BigDecimal free_qty;
	private String ipAddress;
	private String companyCode;
	private String gst_doc_type;
	private String gst_reverse_chg;
	private String vatYN;
	private Long sendState;
	private Long recState;
	private String gstTypeInd;
	private String nilGstStn;
	private String freeGoodsStn;
	private Boolean applyZeroGST;
	private String trans_no;
	private String stk_trf_tran_type;
	private Long detailId;
	private BigDecimal tot_val;
	//UI DATA
	private Long headerId;
	private Long sendSubCompId;
	private Long sendLocId;
	private Long sendLocStateId;
	private String tranType;
	private String sendLocation;
	private Long recLocation;
	private String trfType;
	private String stktrfNo;
	private String trfDate;
	private String nilGstInd;
	private Long productId;
	private String stockTypeId;
	private Long stockTypeDetId;
	private BigDecimal billedQty;
	private BigDecimal freeQty;
	private BigDecimal totalStock;
	private BigDecimal wthStkQty;
	private BigDecimal billableStk;
	private BigDecimal billedStkValue;
	private BigDecimal totalBilledQty;
	private BigDecimal totalFreeQty;
	private BigDecimal totalStkTrfvalue;
	private String currPeriodCode;
	private String currFinYear;
	private String empId;
	private String gstInd;
	private List<Long> batchIds;
	private List<BigDecimal> batchBilledQtys;
	private List<BigDecimal> batchFreeQtys;
	private List<BigDecimal> batchTrfRates;
	private String saveMode;
	private List<Long> detailIds;
	private Date trfDateModify;
	//check details
	
	private String stockTrfNo;
	private String batchNo;
	private String productName;
	private BigDecimal soldQty;
	private BigDecimal rate;
	private BigDecimal freeQtyCd;
	
	private Long currPeriodId;
	private List<Long> headerIds;
	private List<BigDecimal> fullShippers;
	private List<BigDecimal> loosePacks;
	private List<BigDecimal> weights;
	
	private String finYear;
	private String lrnumber;
	private String lrdate;
	private String roadPermitNo;
	private String transporter;
	private String lorryNo;
	private Long mot;
	private Long consType;
	private String driverName;
	private String driverPhoneNo;
	private BigDecimal fullShipper;
	private BigDecimal loosePack;
	private BigDecimal weight;
	private Boolean stock_at_cfa;
	private String mfg_date; 
	private BigDecimal taxRate;
	
	
	
	public String getMfg_date() {
		return mfg_date;
	}
	public void setMfg_date(String mfg_date) {
		this.mfg_date = mfg_date;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public Long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(Long batch_id) {
		this.batch_id = batch_id;
	}
	public Long getProd_id() {
		return prod_id;
	}
	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public Long getLoc_id() {
		return loc_id;
	}
	public void setLoc_id(Long loc_id) {
		this.loc_id = loc_id;
	}
	public BigDecimal getTrfRate() {
		return trfRate;
	}
	public void setTrfRate(BigDecimal trfRate) {
		this.trfRate = trfRate;
	}
	public BigDecimal getOpen_stock() {
		return open_stock;
	}
	public void setOpen_stock(BigDecimal open_stock) {
		this.open_stock = open_stock;
	}
	public BigDecimal getIn_stock() {
		return in_stock;
	}
	public void setIn_stock(BigDecimal in_stock) {
		this.in_stock = in_stock;
	}
	public BigDecimal getOut_stock() {
		return out_stock;
	}
	public void setOut_stock(BigDecimal out_stock) {
		this.out_stock = out_stock;
	}
	public BigDecimal getWitheld_qty() {
		return witheld_qty;
	}
	public void setWitheld_qty(BigDecimal witheld_qty) {
		this.witheld_qty = witheld_qty;
	}
	public BigDecimal getStock() {
		return stock;
	}
	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}
	public BigDecimal getSold_qty() {
		return sold_qty;
	}
	public void setSold_qty(BigDecimal sold_qty) {
		this.sold_qty = sold_qty;
	}
	public BigDecimal getFree_qty() {
		return free_qty;
	}
	public void setFree_qty(BigDecimal free_qty) {
		this.free_qty = free_qty;
	}
	public Long getHeaderId() {
		return headerId;
	}
	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
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
	public Long getSendLocStateId() {
		return sendLocStateId;
	}
	public void setSendLocStateId(Long sendLocStateId) {
		this.sendLocStateId = sendLocStateId;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getSendLocation() {
		return sendLocation;
	}
	public void setSendLocation(String sendLocation) {
		this.sendLocation = sendLocation;
	}
	public Long getRecLocation() {
		return recLocation;
	}
	public void setRecLocation(Long recLocation) {
		this.recLocation = recLocation;
	}
	public String getTrfType() {
		return trfType;
	}
	public void setTrfType(String trfType) {
		this.trfType = trfType;
	}
	public String getStktrfNo() {
		return stktrfNo;
	}
	public void setStktrfNo(String stktrfNo) {
		this.stktrfNo = stktrfNo;
	}
	public String getTrfDate() {
		return trfDate;
	}
	public void setTrfDate(String trfDate) {
		this.trfDate = trfDate;
	}
	public String getNilGstInd() {
		return nilGstInd;
	}
	public void setNilGstInd(String nilGstInd) {
		this.nilGstInd = nilGstInd;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public BigDecimal getBilledQty() {
		return billedQty;
	}
	public void setBilledQty(BigDecimal billedQty) {
		this.billedQty = billedQty;
	}
	public BigDecimal getFreeQty() {
		return freeQty;
	}
	public void setFreeQty(BigDecimal freeQty) {
		this.freeQty = freeQty;
	}
	public BigDecimal getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(BigDecimal totalStock) {
		this.totalStock = totalStock;
	}
	public BigDecimal getWthStkQty() {
		return wthStkQty;
	}
	public void setWthStkQty(BigDecimal wthStkQty) {
		this.wthStkQty = wthStkQty;
	}
	public BigDecimal getBillableStk() {
		return billableStk;
	}
	public void setBillableStk(BigDecimal billableStk) {
		this.billableStk = billableStk;
	}
	public BigDecimal getBilledStkValue() {
		return billedStkValue;
	}
	public void setBilledStkValue(BigDecimal billedStkValue) {
		this.billedStkValue = billedStkValue;
	}
	public BigDecimal getTotalBilledQty() {
		return totalBilledQty;
	}
	public void setTotalBilledQty(BigDecimal totalBilledQty) {
		this.totalBilledQty = totalBilledQty;
	}
	public BigDecimal getTotalFreeQty() {
		return totalFreeQty;
	}
	public void setTotalFreeQty(BigDecimal totalFreeQty) {
		this.totalFreeQty = totalFreeQty;
	}
	public BigDecimal getTotalStkTrfvalue() {
		return totalStkTrfvalue;
	}
	public void setTotalStkTrfvalue(BigDecimal totalStkTrfvalue) {
		this.totalStkTrfvalue = totalStkTrfvalue;
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
	public String getGstInd() {
		return gstInd;
	}
	public void setGstInd(String gstInd) {
		this.gstInd = gstInd;
	}
	public List<Long> getBatchIds() {
		return batchIds;
	}
	public void setBatchIds(List<Long> batchIds) {
		this.batchIds = batchIds;
	}
	public List<BigDecimal> getBatchBilledQtys() {
		return batchBilledQtys;
	}
	public void setBatchBilledQtys(List<BigDecimal> batchBilledQtys) {
		this.batchBilledQtys = batchBilledQtys;
	}
	public List<BigDecimal> getBatchFreeQtys() {
		return batchFreeQtys;
	}
	public void setBatchFreeQtys(List<BigDecimal> batchFreeQtys) {
		this.batchFreeQtys = batchFreeQtys;
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
	public String getGst_doc_type() {
		return gst_doc_type;
	}
	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}
	public String getGst_reverse_chg() {
		return gst_reverse_chg;
	}
	public void setGst_reverse_chg(String gst_reverse_chg) {
		this.gst_reverse_chg = gst_reverse_chg;
	}
	public String getVatYN() {
		return vatYN;
	}
	public void setVatYN(String vatYN) {
		this.vatYN = vatYN;
	}
	public Long getSendState() {
		return sendState;
	}
	public void setSendState(Long sendState) {
		this.sendState = sendState;
	}
	public Long getRecState() {
		return recState;
	}
	public void setRecState(Long recState) {
		this.recState = recState;
	}
	public String getGstTypeInd() {
		return gstTypeInd;
	}
	public void setGstTypeInd(String gstTypeInd) {
		this.gstTypeInd = gstTypeInd;
	}
	public String getNilGstStn() {
		return nilGstStn;
	}
	public void setNilGstStn(String nilGstStn) {
		this.nilGstStn = nilGstStn;
	}
	public String getFreeGoodsStn() {
		return freeGoodsStn;
	}
	public void setFreeGoodsStn(String freeGoodsStn) {
		this.freeGoodsStn = freeGoodsStn;
	}
	public Boolean getApplyZeroGST() {
		return applyZeroGST;
	}
	public void setApplyZeroGST(Boolean applyZeroGST) {
		this.applyZeroGST = applyZeroGST;
	}
	public String getTrans_no() {
		return trans_no;
	}
	public void setTrans_no(String trans_no) {
		this.trans_no = trans_no;
	}
	public List<BigDecimal> getBatchTrfRates() {
		return batchTrfRates;
	}
	public void setBatchTrfRates(List<BigDecimal> batchTrfRates) {
		this.batchTrfRates = batchTrfRates;
	}
	public String getStk_trf_tran_type() {
		return stk_trf_tran_type;
	}
	public void setStk_trf_tran_type(String stk_trf_tran_type) {
		this.stk_trf_tran_type = stk_trf_tran_type;
	}
	public String getStockTrfNo() {
		return stockTrfNo;
	}
	public void setStockTrfNo(String stockTrfNo) {
		this.stockTrfNo = stockTrfNo;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getSoldQty() {
		return soldQty;
	}
	public void setSoldQty(BigDecimal soldQty) {
		this.soldQty = soldQty;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getFreeQtyCd() {
		return freeQtyCd;
	}
	public void setFreeQtyCd(BigDecimal freeQtyCd) {
		this.freeQtyCd = freeQtyCd;
	}
	public Long getStockTypeDetId() {
		return stockTypeDetId;
	}
	public void setStockTypeDetId(Long stockTypeDetId) {
		this.stockTypeDetId = stockTypeDetId;
	}
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	public BigDecimal getTot_val() {
		return tot_val;
	}
	public void setTot_val(BigDecimal tot_val) {
		this.tot_val = tot_val;
	}
	public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public List<Long> getDetailIds() {
		return detailIds;
	}
	public void setDetailIds(List<Long> detailIds) {
		this.detailIds = detailIds;
	}
	public Date getTrfDateModify() {
		return trfDateModify;
	}
	public void setTrfDateModify(Date trfDateModify) {
		this.trfDateModify = trfDateModify;
	}
	public Long getCurrPeriodId() {
		return currPeriodId;
	}
	public void setCurrPeriodId(Long currPeriodId) {
		this.currPeriodId = currPeriodId;
	}
	public List<Long> getHeaderIds() {
		return headerIds;
	}
	public void setHeaderIds(List<Long> headerIds) {
		this.headerIds = headerIds;
	}
	public List<BigDecimal> getFullShippers() {
		return fullShippers;
	}
	public void setFullShippers(List<BigDecimal> fullShippers) {
		this.fullShippers = fullShippers;
	}
	public List<BigDecimal> getLoosePacks() {
		return loosePacks;
	}
	public void setLoosePacks(List<BigDecimal> loosePacks) {
		this.loosePacks = loosePacks;
	}
	public List<BigDecimal> getWeights() {
		return weights;
	}
	public void setWeights(List<BigDecimal> weights) {
		this.weights = weights;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
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
	public String getRoadPermitNo() {
		return roadPermitNo;
	}
	public void setRoadPermitNo(String roadPermitNo) {
		this.roadPermitNo = roadPermitNo;
	}
	public String getTransporter() {
		return transporter;
	}
	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
	public String getLorryNo() {
		return lorryNo;
	}
	public void setLorryNo(String lorryNo) {
		this.lorryNo = lorryNo;
	}
	public Long getMot() {
		return mot;
	}
	public void setMot(Long mot) {
		this.mot = mot;
	}
	public Long getConsType() {
		return consType;
	}
	public void setConsType(Long consType) {
		this.consType = consType;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhoneNo() {
		return driverPhoneNo;
	}
	public void setDriverPhoneNo(String driverPhoneNo) {
		this.driverPhoneNo = driverPhoneNo;
	}
	public BigDecimal getFullShipper() {
		return fullShipper;
	}
	public void setFullShipper(BigDecimal fullShipper) {
		this.fullShipper = fullShipper;
	}
	public BigDecimal getLoosePack() {
		return loosePack;
	}
	public void setLoosePack(BigDecimal loosePack) {
		this.loosePack = loosePack;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public Boolean getStock_at_cfa() {
		return stock_at_cfa;
	}
	public void setStock_at_cfa(Boolean stock_at_cfa) {
		this.stock_at_cfa = stock_at_cfa;
	}
	
	
	
	
	
	
	
}
