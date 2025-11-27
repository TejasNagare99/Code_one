package com.excel.bean;

import java.util.Date;

public class ProductBean {
	String company;
	String productCode;
	String subCompany;
	String productName;
	String pack;
	String alternateName;
	Date launchDate;
	Date discontinuedDate;
	String umo;
	String umoName;
	String form1;

	String scheduleIndicator;
	String divisionRequiredIndicator;
	String expiryRequiredIndicator;
	String batchRequiredIndicator;
	String allocationMultipleIndicator;
	String abcIndicator;

	String stockDays;
	String productType;
	//String productTypeID;
	String productTypeName;
	
	String samplingType;
	String shelfLife;
	String manufacturingLocation;
	String shortExpiryDays;
	String productBatchSize;
	String erpProductCode;
	String brand;
	String hsnCode;
	String promoInput;

	String therapeuticGroup;
	String therapeuticSubgroup;
	String molecularGroup;
	String molecularSubgroup;
	String pmtGroup;
	String pmtSubgroup;
	String quantityShipper;
	String quantityBox;
	String quantityStrip;

	String allocationQuantityMax;
	String allocationQuantityMin;
	String allocationQuantityStandard;
	String shipperVolume;
	String shipperNWT;
	String shipperGWT;
	String specificDivisionIndicator;
	String costOfGoodsRate;
	String costOfGoodsExciseRate;
	String rate;
	String costingRate;
	String marketingRate;
	String nrv;
	String displayRate;
	String standardDivision;
	String storageTypeId;
	String storageTypeName;
	String productSubType;
	String supplierType;
	String marginPerc;
	//53
	//GCMA fields
	String gcmaRequire;
	String gcmaNumber;
	Date gcmaApproveDate;
	Date gcmaExpiryDate;
	String reasonId;
	
	Long prod_div_id;
	String prod_div_disp_nm;
		
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getSubCompany() {
		return subCompany;
	}
	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getAlternateName() {
		return alternateName;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	public Date getLaunchDate() {
		return launchDate;
	}
	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}
	public Date getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setDiscontinuedDate(Date discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	public String getUmo() {
		return umo;
	}
	public void setUmo(String umo) {
		this.umo = umo;
	}
	
	public String getUmoName() {
		return umoName;
	}
	public void setUmoName(String umoName) {
		this.umoName = umoName;
	}
	
	public String getForm1() {
		return form1;
	}
	public void setForm1(String form1) {
		this.form1 = form1;
	}
	public String getScheduleIndicator() {
		return scheduleIndicator;
	}
	public void setScheduleIndicator(String scheduleIndicator) {
		this.scheduleIndicator = scheduleIndicator;
	}
	public String getDivisionRequiredIndicator() {
		return divisionRequiredIndicator;
	}
	public void setDivisionRequiredIndicator(String divisionRequiredIndicator) {
		this.divisionRequiredIndicator = divisionRequiredIndicator;
	}
	public String getExpiryRequiredIndicator() {
		return expiryRequiredIndicator;
	}
	public void setExpiryRequiredIndicator(String expiryRequiredIndicator) {
		this.expiryRequiredIndicator = expiryRequiredIndicator;
	}
	public String getBatchRequiredIndicator() {
		return batchRequiredIndicator;
	}
	public void setBatchRequiredIndicator(String batchRequiredIndicator) {
		this.batchRequiredIndicator = batchRequiredIndicator;
	}
	public String getAllocationMultipleIndicator() {
		return allocationMultipleIndicator;
	}
	public void setAllocationMultipleIndicator(String allocationMultipleIndicator) {
		this.allocationMultipleIndicator = allocationMultipleIndicator;
	}
	public String getAbcIndicator() {
		return abcIndicator;
	}
	public void setAbcIndicator(String abcIndicator) {
		this.abcIndicator = abcIndicator;
	}
	public String getStockDays() {
		return stockDays;
	}
	public void setStockDays(String stockDays) {
		this.stockDays = stockDays;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	
//	public String getProductTypeID() {
//		return productTypeID;
//	}
//	public void setProductTypeID(String productTypeID) {
//		this.productTypeID = productTypeID;
//	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getSamplingType() {
		return samplingType;
	}
	public void setSamplingType(String samplingType) {
		this.samplingType = samplingType;
	}
	public String getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}
	public String getManufacturingLocation() {
		return manufacturingLocation;
	}
	public void setManufacturingLocation(String manufacturingLocation) {
		this.manufacturingLocation = manufacturingLocation;
	}
	public String getShortExpiryDays() {
		return shortExpiryDays;
	}
	public void setShortExpiryDays(String shortExpiryDays) {
		this.shortExpiryDays = shortExpiryDays;
	}
	public String getProductBatchSize() {
		return productBatchSize;
	}
	public void setProductBatchSize(String productBatchSize) {
		this.productBatchSize = productBatchSize;
	}
	public String getErpProductCode() {
		return erpProductCode;
	}
	public void setErpProductCode(String erpProductCode) {
		this.erpProductCode = erpProductCode;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	public String getPromoInput() {
		return promoInput;
	}
	public void setPromoInput(String promoInput) {
		this.promoInput = promoInput;
	}
	public String getTherapeuticGroup() {
		return therapeuticGroup;
	}
	public void setTherapeuticGroup(String therapeuticGroup) {
		this.therapeuticGroup = therapeuticGroup;
	}
	public String getTherapeuticSubgroup() {
		return therapeuticSubgroup;
	}
	public void setTherapeuticSubgroup(String therapeuticSubgroup) {
		this.therapeuticSubgroup = therapeuticSubgroup;
	}
	public String getMolecularGroup() {
		return molecularGroup;
	}
	public void setMolecularGroup(String molecularGroup) {
		this.molecularGroup = molecularGroup;
	}
	public String getMolecularSubgroup() {
		return molecularSubgroup;
	}
	public void setMolecularSubgroup(String molecularSubgroup) {
		this.molecularSubgroup = molecularSubgroup;
	}
	public String getPmtGroup() {
		return pmtGroup;
	}
	public void setPmtGroup(String pmtGroup) {
		this.pmtGroup = pmtGroup;
	}
	public String getPmtSubgroup() {
		return pmtSubgroup;
	}
	public void setPmtSubgroup(String pmtSubgroup) {
		this.pmtSubgroup = pmtSubgroup;
	}
	public String getQuantityShipper() {
		return quantityShipper;
	}
	public void setQuantityShipper(String quantityShipper) {
		this.quantityShipper = quantityShipper;
	}
	public String getQuantityBox() {
		return quantityBox;
	}
	public void setQuantityBox(String quantityBox) {
		this.quantityBox = quantityBox;
	}
	public String getQuantityStrip() {
		return quantityStrip;
	}
	public void setQuantityStrip(String quantityStrip) {
		this.quantityStrip = quantityStrip;
	}
	public String getAllocationQuantityMax() {
		return allocationQuantityMax;
	}
	public void setAllocationQuantityMax(String allocationQuantityMax) {
		this.allocationQuantityMax = allocationQuantityMax;
	}
	public String getAllocationQuantityMin() {
		return allocationQuantityMin;
	}
	public void setAllocationQuantityMin(String allocationQuantityMin) {
		this.allocationQuantityMin = allocationQuantityMin;
	}
	public String getAllocationQuantityStandard() {
		return allocationQuantityStandard;
	}
	public void setAllocationQuantityStandard(String allocationQuantityStandard) {
		this.allocationQuantityStandard = allocationQuantityStandard;
	}
	public String getShipperVolume() {
		return shipperVolume;
	}
	public void setShipperVolume(String shipperVolume) {
		this.shipperVolume = shipperVolume;
	}
	public String getShipperNWT() {
		return shipperNWT;
	}
	public void setShipperNWT(String shipperNWT) {
		this.shipperNWT = shipperNWT;
	}
	public String getShipperGWT() {
		return shipperGWT;
	}
	public void setShipperGWT(String shipperGWT) {
		this.shipperGWT = shipperGWT;
	}
	public String getSpecificDivisionIndicator() {
		return specificDivisionIndicator;
	}
	public void setSpecificDivisionIndicator(String specificDivisionIndicator) {
		this.specificDivisionIndicator = specificDivisionIndicator;
	}
	public String getCostOfGoodsRate() {
		return costOfGoodsRate;
	}
	public void setCostOfGoodsRate(String costOfGoodsRate) {
		this.costOfGoodsRate = costOfGoodsRate;
	}
	public String getCostOfGoodsExciseRate() {
		return costOfGoodsExciseRate;
	}
	public void setCostOfGoodsExciseRate(String costOfGoodsExciseRate) {
		this.costOfGoodsExciseRate = costOfGoodsExciseRate;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getCostingRate() {
		return costingRate;
	}
	public void setCostingRate(String costingRate) {
		this.costingRate = costingRate;
	}
	public String getMarketingRate() {
		return marketingRate;
	}
	public void setMarketingRate(String marketingRate) {
		this.marketingRate = marketingRate;
	}
	public String getNrv() {
		return nrv;
	}
	public void setNrv(String nrv) {
		this.nrv = nrv;
	}
	public String getDisplayRate() {
		return displayRate;
	}
	public void setDisplayRate(String displayRate) {
		this.displayRate = displayRate;
	}
	public String getStandardDivision() {
		return standardDivision;
	}
	public void setStandardDivision(String standardDivision) {
		this.standardDivision = standardDivision;
	}
	
	public String getStorageTypeId() {
		return storageTypeId;
	}
	public void setStorageTypeId(String storageTypeId) {
		this.storageTypeId = storageTypeId;
	}
	public String getProductSubType() {
		return productSubType;
	}
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}
	public String getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	public String getMarginPerc() {
		return marginPerc;
	}
	public void setMarginPerc(String marginPerc) {
		this.marginPerc = marginPerc;
	}
	public String getStorageTypeName() {
		return storageTypeName;
	}
	public void setStorageTypeName(String storageTypeName) {
		this.storageTypeName = storageTypeName;
	}
	//GCMA
	public String getGcmaNumber() {
		return gcmaNumber;
	}
	public String getGcmaRequire() {
		return gcmaRequire;
	}
	public void setGcmaRequire(String gcmaRequire) {
		this.gcmaRequire = gcmaRequire;
	}
	public void setGcmaNumber(String gcmaNumber) {
		this.gcmaNumber = gcmaNumber;
	}
	public Date getGcmaApproveDate() {
		return gcmaApproveDate;
	}
	public void setGcmaApproveDate(Date gcmaApproveDate) {
		this.gcmaApproveDate = gcmaApproveDate;
	}
	public Date getGcmaExpiryDate() {
		return gcmaExpiryDate;
	}
	public void setGcmaExpiryDate(Date gcmaExpiryDate) {
		this.gcmaExpiryDate = gcmaExpiryDate;
	}
	public Long getProd_div_id() {
		return prod_div_id;
	}
	public void setProd_div_id(Long prod_div_id) {
		this.prod_div_id = prod_div_id;
	}
	public String getProd_div_disp_nm() {
		return prod_div_disp_nm;
	}
	public void setProd_div_disp_nm(String prod_div_disp_nm) {
		this.prod_div_disp_nm = prod_div_disp_nm;
	}
	
	
}
