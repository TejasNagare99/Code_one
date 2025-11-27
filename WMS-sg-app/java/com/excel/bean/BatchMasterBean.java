package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BatchMasterBean {

	private String companyCode;
	private String ipAddress;
	
	private Long productId;
	private String locationName;
	private Long locationId;
	
	private String currPeriodCode;
	private String currFinYear;
	private String empId;
	
	private List<String> batchNos;
	private List<Date> mfgDates;
	private List<Date> expDates;
	private List<Long> mfgLocs;
	private List<BigDecimal> rates;
	private List<BigDecimal> displayRates;
	private List<BigDecimal> opnStks;
	private List<BigDecimal> wthQtys;
	private List<BigDecimal> phStks;
	private List<BigDecimal> cosRates;
	private List<BigDecimal> mkgRates;
	private List<BigDecimal> nrvs;
	private List<String> narrations;
	
	private String batchNo;
	private Date mfgDate;
	private Date expDate;
	private Long mfgLoc;
	private BigDecimal rate;
	private BigDecimal displayRate;
	private BigDecimal opnStk;
	private BigDecimal wthQty;
	private BigDecimal phStk;
	private BigDecimal cosRate;
	private BigDecimal mkgRate;
	private BigDecimal nrv;
	private String narration;
	private Long batchId;
	
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
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
	public List<String> getBatchNos() {
		return batchNos;
	}
	public void setBatchNos(List<String> batchNos) {
		this.batchNos = batchNos;
	}
	
	public List<Long> getMfgLocs() {
		return mfgLocs;
	}
	public void setMfgLocs(List<Long> mfgLocs) {
		this.mfgLocs = mfgLocs;
	}
	public List<BigDecimal> getRates() {
		return rates;
	}
	public void setRates(List<BigDecimal> rates) {
		this.rates = rates;
	}
	public List<BigDecimal> getDisplayRates() {
		return displayRates;
	}
	public void setDisplayRates(List<BigDecimal> displayRates) {
		this.displayRates = displayRates;
	}
	public List<BigDecimal> getOpnStks() {
		return opnStks;
	}
	public void setOpnStks(List<BigDecimal> opnStks) {
		this.opnStks = opnStks;
	}
	public List<BigDecimal> getWthQtys() {
		return wthQtys;
	}
	public void setWthQtys(List<BigDecimal> wthQtys) {
		this.wthQtys = wthQtys;
	}
	public List<BigDecimal> getPhStks() {
		return phStks;
	}
	public void setPhStks(List<BigDecimal> phStks) {
		this.phStks = phStks;
	}
	public List<BigDecimal> getCosRates() {
		return cosRates;
	}
	public void setCosRates(List<BigDecimal> cosRates) {
		this.cosRates = cosRates;
	}
	public List<BigDecimal> getMkgRates() {
		return mkgRates;
	}
	public void setMkgRates(List<BigDecimal> mkgRates) {
		this.mkgRates = mkgRates;
	}
	public List<BigDecimal> getNrvs() {
		return nrvs;
	}
	public void setNrvs(List<BigDecimal> nrvs) {
		this.nrvs = nrvs;
	}
	public List<String> getNarrations() {
		return narrations;
	}
	public void setNarrations(List<String> narrations) {
		this.narrations = narrations;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public List<Date> getMfgDates() {
		return mfgDates;
	}
	public void setMfgDates(List<Date> mfgDates) {
		this.mfgDates = mfgDates;
	}
	public List<Date> getExpDates() {
		return expDates;
	}
	public void setExpDates(List<Date> expDates) {
		this.expDates = expDates;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Date getMfgDate() {
		return mfgDate;
	}
	public void setMfgDate(Date mfgDate) {
		this.mfgDate = mfgDate;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public Long getMfgLoc() {
		return mfgLoc;
	}
	public void setMfgLoc(Long mfgLoc) {
		this.mfgLoc = mfgLoc;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getDisplayRate() {
		return displayRate;
	}
	public void setDisplayRate(BigDecimal displayRate) {
		this.displayRate = displayRate;
	}
	public BigDecimal getOpnStk() {
		return opnStk;
	}
	public void setOpnStk(BigDecimal opnStk) {
		this.opnStk = opnStk;
	}
	public BigDecimal getWthQty() {
		return wthQty;
	}
	public void setWthQty(BigDecimal wthQty) {
		this.wthQty = wthQty;
	}
	public BigDecimal getPhStk() {
		return phStk;
	}
	public void setPhStk(BigDecimal phStk) {
		this.phStk = phStk;
	}
	public BigDecimal getCosRate() {
		return cosRate;
	}
	public void setCosRate(BigDecimal cosRate) {
		this.cosRate = cosRate;
	}
	public BigDecimal getMkgRate() {
		return mkgRate;
	}
	public void setMkgRate(BigDecimal mkgRate) {
		this.mkgRate = mkgRate;
	}
	public BigDecimal getNrv() {
		return nrv;
	}
	public void setNrv(BigDecimal nrv) {
		this.nrv = nrv;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	
	
	
}
