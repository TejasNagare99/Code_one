package com.excel.bean;

import java.math.BigDecimal;
import java.util.List;

public class SpecialAllocationBean {

	private String empId;
	private Long requestorId;
	private String requestType;
	private Long territoryId;
	private String territoryCodeName;
	private String requestNumber;
	private List<ProductDetails> ProductDetails;
	private Long alloc_req_id;
	private String alloc_req_date;
	//Recipient Details
	private Long recipientId;
	private String recipientStringId;
	private String recipientName;
	private String recipient_type;
	private Long div_id;
	private String div_name;
	private String mclNo;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String requestorName;
	private String territory_code;
	private String city;
	private String pincode;
	private String phone;
	private String email;
	private BigDecimal totalValue;;
	private String doc_letter;
	private String recipient_phone;
	//other then UI
	private String companyCode;
	private String ipAddress;
	private String finYear;
	private String periodCode;
	private String userId;
	private String mode;
	private Long alloc_cycle;
	private String filePath;
	private String fileName;
	private String allocType;
	//other then UI
	private String meetingDate;
	private String supplyDate;
	private String meetingVenue;
	private String meetingAddress;
	private String frieghtType;;
	private BigDecimal frieghtValue;
	//********************Used In Query******************
	//Product Details
	private Long brand_id;
	private String brand_name;
	private String product_code;
	private String swh_code;
	private String erp_prod_code;
	private Long prod_id;
	private String prod_name;
	private String pack_name;
	private String pack;
	private String min_alloc_qty;
	private BigDecimal smp_rate;
	private String threshold;
	private String hinghvalue;
	private String sensitive;
	private String prod_type;
	private String prod_sub_type;
	private String coldChain;
	private BigDecimal allocQty;
	private BigDecimal allocValue;
	private Long detailId;
	private List<Object> productList;
	
	//FieldStaffDetails
	private String fieldStaffName;
	private Long fieldStaffId;
	private String staffTerritoryCode;
	private String staffTerritoryName;
	private Long staffTerritoryId;
	private String dsmName;
	private String dsmEmpId;
	private String rmName;
	private String rmEmpId;
	private String zmName;
	private String zmEmpId;
	private String smName;
	private String smEmpId;
	private String wareHouse;
	private String wareEmpId;
	private String gco;
	private String gcoEmpId;
	private String approvalLevels;
	private String recipientType;
	private String address;
	private String attachedFile;
	
	private String emailReqInd;
	private String srtNo;
	private String srtDate;
	private String srtremark;
	private String fstaff_desig;
	
	public String getFstaff_desig() {
		return fstaff_desig;
	}
	public void setFstaff_desig(String fstaff_desig) {
		this.fstaff_desig = fstaff_desig;
	}
	public String getSrtremark() {
		return srtremark;
	}
	public void setSrtremark(String srtremark) {
		this.srtremark = srtremark;
	}
	private List<String> mailList;
	
	
	public String getEmailReqInd() {
		return emailReqInd;
	}
	public void setEmailReqInd(String emailReqInd) {
		this.emailReqInd = emailReqInd;
	}
	public String getSrtNo() {
		return srtNo;
	}
	public void setSrtNo(String srtNo) {
		this.srtNo = srtNo;
	}
	public String getSrtDate() {
		return srtDate;
	}
	public void setSrtDate(String srtDate) {
		this.srtDate = srtDate;
	}
	public List<String> getMailList() {
		return mailList;
	}
	public void setMailList(List<String> mailList) {
		this.mailList = mailList;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	//level
	private String levelcode;
	private String levelName;
	
	private String serviceNo;
	public static class ProductDetails {
		private Long productIds;
		private String productCodes;
		private String brandNames;
		private String coldChainYesNos;
		private String sensitives;
		private String packs;
		private BigDecimal allocQtys;
		private BigDecimal rates;
		private BigDecimal values;
		private String thresholds;
		private String prodType;
		private String prodSubType;
		private Long alloc_req_detail_id;
		
		//for queries
		private String productNames;
	
		public Long getProductIds() {
			return productIds;
		}
		public void setProductIds(Long productIds) {
			this.productIds = productIds;
		}
		public String getProductCodes() {
			return productCodes;
		}
		public void setProductCodes(String productCodes) {
			this.productCodes = productCodes;
		}
		public String getBrandNames() {
			return brandNames;
		}
		public void setBrandNames(String brandNames) {
			this.brandNames = brandNames;
		}
		public String getColdChainYesNos() {
			return coldChainYesNos;
		}
		public void setColdChainYesNos(String coldChainYesNos) {
			this.coldChainYesNos = coldChainYesNos;
		}
		public String getSensitives() {
			return sensitives;
		}
		public void setSensitives(String sensitives) {
			this.sensitives = sensitives;
		}
		public String getPacks() {
			return packs;
		}
		public void setPacks(String packs) {
			this.packs = packs;
		}
		public BigDecimal getAllocQtys() {
			return allocQtys;
		}
		public void setAllocQtys(BigDecimal allocQtys) {
			this.allocQtys = allocQtys;
		}
		public BigDecimal getRates() {
			return rates;
		}
		public void setRates(BigDecimal rates) {
			this.rates = rates;
		}
		public BigDecimal getValues() {
			return values;
		}
		public void setValues(BigDecimal values) {
			this.values = values;
		}
		public String getThresholds() {
			return thresholds;
		}
		public void setThresholds(String thresholds) {
			this.thresholds = thresholds;
		}
		public String getProdType() {
			return prodType;
		}
		public void setProdType(String prodType) {
			this.prodType = prodType;
		}
		public String getProdSubType() {
			return prodSubType;
		}
		public void setProdSubType(String prodSubType) {
			this.prodSubType = prodSubType;
		}
		public Long getAlloc_req_detail_id() {
			return alloc_req_detail_id;
		}
		public void setAlloc_req_detail_id(Long alloc_req_detail_id) {
			this.alloc_req_detail_id = alloc_req_detail_id;
		}
		public String getProductNames() {
			return productNames;
		}
		public void setProductNames(String productNames) {
			this.productNames = productNames;
		}
		
		
	}
	
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getMin_alloc_qty() {
		return min_alloc_qty;
	}
	public void setMin_alloc_qty(String min_alloc_qty) {
		this.min_alloc_qty = min_alloc_qty;
	}
	public BigDecimal getSmp_rate() {
		return smp_rate;
	}
	public void setSmp_rate(BigDecimal smp_rate) {
		this.smp_rate = smp_rate;
	}
	
	public Long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getErp_prod_code() {
		return erp_prod_code;
	}
	public void setErp_prod_code(String erp_prod_code) {
		this.erp_prod_code = erp_prod_code;
	}
	public Long getProd_id() {
		return prod_id;
	}
	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public Long getDiv_id() {
		return div_id;
	}
	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}
	public String getDiv_name() {
		return div_name;
	}
	public void setDiv_name(String div_name) {
		this.div_name = div_name;
	}
	public String getMclNo() {
		return mclNo;
	}
	public void setMclNo(String mclNo) {
		this.mclNo = mclNo;
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
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public String getTerritory_code() {
		return territory_code;
	}
	public void setTerritory_code(String territory_code) {
		this.territory_code = territory_code;
	}
	public List<Object> getProductList() {
		return productList;
	}
	public void setProductList(List<Object> productList) {
		this.productList = productList;
	}
	public String getSwh_code() {
		return swh_code;
	}
	public void setSwh_code(String swh_code) {
		this.swh_code = swh_code;
	}
	public List<ProductDetails> getProductDetails() {
		return ProductDetails;
	}
	public void setProductDetails(List<ProductDetails> productDetails) {
		ProductDetails = productDetails;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Long getAlloc_req_id() {
		return alloc_req_id;
	}
	public void setAlloc_req_id(Long alloc_req_id) {
		this.alloc_req_id = alloc_req_id;
	}
	public String getAlloc_req_date() {
		return alloc_req_date;
	}
	public void setAlloc_req_date(String alloc_req_date) {
		this.alloc_req_date = alloc_req_date;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getPeriodCode() {
		return periodCode;
	}
	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getAlloc_cycle() {
		return alloc_cycle;
	}
	public void setAlloc_cycle(Long alloc_cycle) {
		this.alloc_cycle = alloc_cycle;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getHinghvalue() {
		return hinghvalue;
	}
	public void setHinghvalue(String hinghvalue) {
		this.hinghvalue = hinghvalue;
	}
	public String getSensitive() {
		return sensitive;
	}
	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}
	public String getProd_type() {
		return prod_type;
	}
	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}
	public String getProd_sub_type() {
		return prod_sub_type;
	}
	public void setProd_sub_type(String prod_sub_type) {
		this.prod_sub_type = prod_sub_type;
	}
	public String getColdChain() {
		return coldChain;
	}
	public void setColdChain(String coldChain) {
		this.coldChain = coldChain;
	}
	public String getFieldStaffName() {
		return fieldStaffName;
	}
	public void setFieldStaffName(String fieldStaffName) {
		this.fieldStaffName = fieldStaffName;
	}
	public Long getFieldStaffId() {
		return fieldStaffId;
	}
	public void setFieldStaffId(Long fieldStaffId) {
		this.fieldStaffId = fieldStaffId;
	}
	public String getStaffTerritoryCode() {
		return staffTerritoryCode;
	}
	public void setStaffTerritoryCode(String staffTerritoryCode) {
		this.staffTerritoryCode = staffTerritoryCode;
	}
	public Long getStaffTerritoryId() {
		return staffTerritoryId;
	}
	public void setStaffTerritoryId(Long staffTerritoryId) {
		this.staffTerritoryId = staffTerritoryId;
	}
	public String getDsmName() {
		return dsmName;
	}
	public void setDsmName(String dsmName) {
		this.dsmName = dsmName;
	}
	public String getRmName() {
		return rmName;
	}
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}
	public String getSmName() {
		return smName;
	}
	public void setSmName(String smName) {
		this.smName = smName;
	}
	public String getWareHouse() {
		return wareHouse;
	}
	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}
	public String getGco() {
		return gco;
	}
	public void setGco(String gco) {
		this.gco = gco;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Long getRequestorId() {
		return requestorId;
	}
	public void setRequestorId(Long requestorId) {
		this.requestorId = requestorId;
	}
	public Long getTerritoryId() {
		return territoryId;
	}
	public void setTerritoryId(Long territoryId) {
		this.territoryId = territoryId;
	}
	public String getZmName() {
		return zmName;
	}
	public void setZmName(String zmName) {
		this.zmName = zmName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getPack_name() {
		return pack_name;
	}
	public void setPack_name(String pack_name) {
		this.pack_name = pack_name;
	}
	public Long getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}
	public String getStaffTerritoryName() {
		return staffTerritoryName;
	}
	public void setStaffTerritoryName(String staffTerritoryName) {
		this.staffTerritoryName = staffTerritoryName;
	}
	public String getTerritoryCodeName() {
		return territoryCodeName;
	}
	public void setTerritoryCodeName(String territoryCodeName) {
		this.territoryCodeName = territoryCodeName;
	}
	public String getAllocType() {
		return allocType;
	}
	public void setAllocType(String allocType) {
		this.allocType = allocType;
	}
	public String getRecipient_type() {
		return recipient_type;
	}
	public void setRecipient_type(String recipient_type) {
		this.recipient_type = recipient_type;
	}
	public BigDecimal getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
	public BigDecimal getAllocQty() {
		return allocQty;
	}
	public void setAllocQty(BigDecimal allocQty) {
		this.allocQty = allocQty;
	}
	public BigDecimal getAllocValue() {
		return allocValue;
	}
	public void setAllocValue(BigDecimal allocValue) {
		this.allocValue = allocValue;
	}
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getRecipientType() {
		return recipientType;
	}
	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}
	public String getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}
	public String getSupplyDate() {
		return supplyDate;
	}
	public void setSupplyDate(String supplyDate) {
		this.supplyDate = supplyDate;
	}
	public String getMeetingVenue() {
		return meetingVenue;
	}
	public void setMeetingVenue(String meetingVenue) {
		this.meetingVenue = meetingVenue;
	}
	public String getMeetingAddress() {
		return meetingAddress;
	}
	public void setMeetingAddress(String meetingAddress) {
		this.meetingAddress = meetingAddress;
	}
	public String getLevelcode() {
		return levelcode;
	}
	public void setLevelcode(String levelcode) {
		this.levelcode = levelcode;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFrieghtType() {
		return frieghtType;
	}
	public void setFrieghtType(String frieghtType) {
		this.frieghtType = frieghtType;
	}
	public BigDecimal getFrieghtValue() {
		return frieghtValue;
	}
	public void setFrieghtValue(BigDecimal frieghtValue) {
		this.frieghtValue = frieghtValue;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDsmEmpId() {
		return dsmEmpId;
	}
	public void setDsmEmpId(String dsmEmpId) {
		this.dsmEmpId = dsmEmpId;
	}
	public String getRmEmpId() {
		return rmEmpId;
	}
	public void setRmEmpId(String rmEmpId) {
		this.rmEmpId = rmEmpId;
	}
	public String getZmEmpId() {
		return zmEmpId;
	}
	public void setZmEmpId(String zmEmpId) {
		this.zmEmpId = zmEmpId;
	}
	public String getSmEmpId() {
		return smEmpId;
	}
	public void setSmEmpId(String smEmpId) {
		this.smEmpId = smEmpId;
	}
	public String getWareEmpId() {
		return wareEmpId;
	}
	public void setWareEmpId(String wareEmpId) {
		this.wareEmpId = wareEmpId;
	}
	public String getGcoEmpId() {
		return gcoEmpId;
	}
	public void setGcoEmpId(String gcoEmpId) {
		this.gcoEmpId = gcoEmpId;
	}
	public String getApprovalLevels() {
		return approvalLevels;
	}
	public void setApprovalLevels(String approvalLevels) {
		this.approvalLevels = approvalLevels;
	}
	public String getDoc_letter() {
		return doc_letter;
	}
	public void setDoc_letter(String doc_letter) {
		this.doc_letter = doc_letter;
	}
	public String getAttachedFile() {
		return attachedFile;
	}
	public void setAttachedFile(String attachedFile) {
		this.attachedFile = attachedFile;
	}
	public String getRecipient_phone() {
		return recipient_phone;
	}
	public void setRecipient_phone(String recipient_phone) {
		this.recipient_phone = recipient_phone;
	}
	public String getRecipientStringId() {
		return recipientStringId;
	}
	public void setRecipientStringId(String recipientStringId) {
		this.recipientStringId = recipientStringId;
	}

	
}
