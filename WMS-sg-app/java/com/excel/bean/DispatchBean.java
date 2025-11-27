package com.excel.bean;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DispatchBean {
	
	private String location;
	private Long locId;
	private Long dispatchCycle;
	private String allocationPeriod;
	private String dispatchPeriod;
	private String dispatchDate;
	private Long division;
	private String dispatchTo;
	private String select;
	private String dispatchType;
	private String month;
	private String companyCode;
	private String companyName;
	private String empId;
	private String allocSmpYear;
	private String allocPeriodCode;
	private Long alloc_smp_id;
	private String action;
	private String challanMsg;
	private String challanMsgInd;
	private Long itemName;
	private Long f_loc_id;
	private Long f_state_id;
	private Long f_div_id;
	private String clubPendAlloc;
	private Long dspId;
	private Long sumdsp_id;
	private String dspChallanNo;
	private Long allocDtlId;
	private Long allocId;
	
	private String dispatchPeriodCode;
	private List<Long> smpDispId;
	List<DispatchDetails> dispatchDetails;
	
	private String allocType;
	private String allocationType;
	private String pendingAlloc;
	
	private MultipartFile dpLrUploadFile;
	private String stockAtCfa;
	private String formname;
	private String tmpOrActl;
	private String msg;
	
	private String prodIds;
	private List<String> productIds;
	
	private String ipAddress;
	private Long dsp_id;
	private List<Long> dspIds;
	private Long locationId;
	private List<String> productType;
	private String sub_team_code;
	private String productTypes;
	
	private String username;
	private String role;
	private String finyr;
	private String alloc_for;
	
	private Long allocReqNo;
	private List<String> multipleAllocReqNosList;
	private String multipleAllocString;
	
	public static class DispatchDetails {
		private BigDecimal dispatchQty;
		private Long smpBatchId;
		private BigDecimal smpRate;
		private BigDecimal smpAllocQty;
		private BigDecimal smpBatchStock;
		
		public BigDecimal getDispatchQty() {
			return dispatchQty;
		}
		public void setDispatchQty(BigDecimal dispatchQty) {
			this.dispatchQty = dispatchQty;
		}
		public Long getSmpBatchId() {
			return smpBatchId;
		}
		public void setSmpBatchId(Long smpBatchId) {
			this.smpBatchId = smpBatchId;
		}
		public BigDecimal getSmpRate() {
			return smpRate;
		}
		public void setSmpRate(BigDecimal smpRate) {
			this.smpRate = smpRate;
		}
		public BigDecimal getSmpAllocQty() {
			return smpAllocQty;
		}
		public void setSmpAllocQty(BigDecimal smpAllocQty) {
			this.smpAllocQty = smpAllocQty;
		}
		public BigDecimal getSmpBatchStock() {
			return smpBatchStock;
		}
		public void setSmpBatchStock(BigDecimal smpBatchStock) {
			this.smpBatchStock = smpBatchStock;
		}
		
	}
	
	public String getMultipleAllocString() {
		return multipleAllocString;
	}
	public void setMultipleAllocString(String multipleAllocString) {
		this.multipleAllocString = multipleAllocString;
	}
	
	public List<String> getMultipleAllocReqNosList() {
		return multipleAllocReqNosList;
	}
	public void setMultipleAllocReqNosList(List<String> multipleAllocReqNosList) {
		this.multipleAllocReqNosList = multipleAllocReqNosList;
	}
	public Long getAllocReqNo() {
		return allocReqNo;
	}
	public void setAllocReqNo(Long allocReqNo) {
		this.allocReqNo = allocReqNo;
	}
	public String getAlloc_for() {
		return alloc_for;
	}
	public void setAlloc_for(String alloc_for) {
		this.alloc_for = alloc_for;
	}
	public String getFinyr() {
		return finyr;
	}
	public void setFinyr(String finyr) {
		this.finyr = finyr;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getLocId() {
		return locId;
	}
	public void setLocId(Long locId) {
		this.locId = locId;
	}
	public Long getDispatchCycle() {
		return dispatchCycle;
	}
	public void setDispatchCycle(Long dispatchCycle) {
		this.dispatchCycle = dispatchCycle;
	}
	public String getAllocationPeriod() {
		return allocationPeriod;
	}
	public void setAllocationPeriod(String allocationPeriod) {
		this.allocationPeriod = allocationPeriod;
	}
	public String getDispatchPeriod() {
		return dispatchPeriod;
	}
	public void setDispatchPeriod(String dispatchPeriod) {
		this.dispatchPeriod = dispatchPeriod;
	}
	public String getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public Long getDivision() {
		return division;
	}
	public void setDivision(Long division) {
		this.division = division;
	}
	public String getDispatchTo() {
		return dispatchTo;
	}
	public void setDispatchTo(String dispatchTo) {
		this.dispatchTo = dispatchTo;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getDispatchType() {
		return dispatchType;
	}
	public void setDispatchType(String dispatchType) {
		this.dispatchType = dispatchType;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getAllocSmpYear() {
		return allocSmpYear;
	}
	public void setAllocSmpYear(String allocSmpYear) {
		this.allocSmpYear = allocSmpYear;
	}
	public String getAllocPeriodCode() {
		return allocPeriodCode;
	}
	public void setAllocPeriodCode(String allocPeriodCode) {
		this.allocPeriodCode = allocPeriodCode;
	}
	public Long getAlloc_smp_id() {
		return alloc_smp_id;
	}
	public void setAlloc_smp_id(Long alloc_smp_id) {
		this.alloc_smp_id = alloc_smp_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getChallanMsg() {
		return challanMsg;
	}
	public void setChallanMsg(String challanMsg) {
		this.challanMsg = challanMsg;
	}
	public String getChallanMsgInd() {
		return challanMsgInd;
	}
	public void setChallanMsgInd(String challanMsgInd) {
		this.challanMsgInd = challanMsgInd;
	}
	public Long getItemName() {
		return itemName;
	}
	public void setItemName(Long itemName) {
		this.itemName = itemName;
	}
	public String getDispatchPeriodCode() {
		return dispatchPeriodCode;
	}
	public void setDispatchPeriodCode(String dispatchPeriodCode) {
		this.dispatchPeriodCode = dispatchPeriodCode;
	}
	public List<Long> getSmpDispId() {
		return smpDispId;
	}
	public void setSmpDispId(List<Long> smpDispId) {
		this.smpDispId = smpDispId;
	}
	public List<DispatchDetails> getDispatchDetails() {
		return dispatchDetails;
	}
	public void setDispatchDetails(List<DispatchDetails> dispatchDetails) {
		this.dispatchDetails = dispatchDetails;
	}
	public Long getF_loc_id() {
		return f_loc_id;
	}
	public void setF_loc_id(Long f_loc_id) {
		this.f_loc_id = f_loc_id;
	}
	public Long getF_state_id() {
		return f_state_id;
	}
	public void setF_state_id(Long f_state_id) {
		this.f_state_id = f_state_id;
	}
	public Long getF_div_id() {
		return f_div_id;
	}
	public void setF_div_id(Long f_div_id) {
		this.f_div_id = f_div_id;
	}
	public String getClubPendAlloc() {
		return clubPendAlloc;
	}
	public void setClubPendAlloc(String clubPendAlloc) {
		this.clubPendAlloc = clubPendAlloc;
	}
	public Long getDspId() {
		return dspId;
	}
	public void setDspId(Long dspId) {
		this.dspId = dspId;
	}
	public Long getSumdsp_id() {
		return sumdsp_id;
	}
	public void setSumdsp_id(Long sumdsp_id) {
		this.sumdsp_id = sumdsp_id;
	}
	public String getDspChallanNo() {
		return dspChallanNo;
	}
	public void setDspChallanNo(String dspChallanNo) {
		this.dspChallanNo = dspChallanNo;
	}
	public Long getAllocDtlId() {
		return allocDtlId;
	}
	public void setAllocDtlId(Long allocDtlId) {
		this.allocDtlId = allocDtlId;
	}
	public Long getAllocId() {
		return allocId;
	}
	public void setAllocId(Long allocId) {
		this.allocId = allocId;
	}
	public String getAllocType() {
		return allocType;
	}
	public void setAllocType(String allocType) {
		this.allocType = allocType;
	}
	public String getAllocationType() {
		return allocationType;
	}
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}
	public String getPendingAlloc() {
		return pendingAlloc;
	}
	public void setPendingAlloc(String pendingAlloc) {
		this.pendingAlloc = pendingAlloc;
	}
	public MultipartFile getDpLrUploadFile() {
		return dpLrUploadFile;
	}
	public void setDpLrUploadFile(MultipartFile dpLrUploadFile) {
		this.dpLrUploadFile = dpLrUploadFile;
	}
	public String getStockAtCfa() {
		return stockAtCfa;
	}
	public void setStockAtCfa(String stockAtCfa) {
		this.stockAtCfa = stockAtCfa;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	public String getTmpOrActl() {
		return tmpOrActl;
	}
	public void setTmpOrActl(String tmpOrActl) {
		this.tmpOrActl = tmpOrActl;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getProdIds() {
		return prodIds;
	}
	public void setProdIds(String prodIds) {
		this.prodIds = prodIds;
	}
	public List<String> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Long getDsp_id() {
		return dsp_id;
	}
	public void setDsp_id(Long dsp_id) {
		this.dsp_id = dsp_id;
	}
	public List<Long> getDspIds() {
		return dspIds;
	}
	public void setDspIds(List<Long> dspIds) {
		this.dspIds = dspIds;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public List<String> getProductType() {
		return productType;
	}
	public void setProductType(List<String> productType) {
		this.productType = productType;
	}
	public String getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}
	public String getSub_team_code() {
		return sub_team_code;
	}
	public void setSub_team_code(String sub_team_code) {
		this.sub_team_code = sub_team_code;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
