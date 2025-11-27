package com.excel.bean;

import java.math.BigDecimal;
import java.util.List;

public class LrEntryBean {
	private String trf_Id;

	public String getTrf_Id() {
		return trf_Id;
	}

	public void setTrf_Id(String trf_Id) {
		this.trf_Id = trf_Id;
	}

	private String firnYearFlag;
	private String sum_dsp_header_no;
	private String challanNumber;
	private String dateOfChallan;
	private String destination;
	private String team;
	private String lrNumber;
	private String lrDate;
	private String expectedDate;
	private String caseNo;
	private BigDecimal weight;
	private BigDecimal billableWeight;
	private BigDecimal lrCharges;
	private String challanValue;
	private String wayBillNumber;
	private String challanRecDate;
	private String challanRecBy;
	private String remark;
	private String isMaster;
	private String transportMode;

	// confirmation dispatch
	private List<String> sumheaderids;
	private List<String> ismaster;
	private List<String> challannos;
	private String transporter;
	private String finYearFlag;
	private String finYear;

	private String ipaddress;
	private String userid;
	
	private String companyCode;
	
	// added 2 July 2024
	private String trans_gst_reg_no;
	private String vehNo;

	public String getVehNo() {
		return vehNo;
	}
	public void setVehNo(String vehNo) {
		this.vehNo = vehNo;
	}

	public String getTrans_gst_reg_no() {
		return trans_gst_reg_no;
	}
	public void setTrans_gst_reg_no(String trans_gst_reg_no) {
		this.trans_gst_reg_no = trans_gst_reg_no;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<String> getSumheaderids() {
		return sumheaderids;
	}

	public void setSumheaderids(List<String> sumheaderids) {
		this.sumheaderids = sumheaderids;
	}

	public List<String> getIsmaster() {
		return ismaster;
	}

	public void setIsmaster(List<String> ismaster) {
		this.ismaster = ismaster;
	}

	public List<String> getChallannos() {
		return challannos;
	}

	public void setChallannos(List<String> challannos) {
		this.challannos = challannos;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getFirnYearFlag() {
		return firnYearFlag;
	}

	public void setFirnYearFlag(String firnYearFlag) {
		this.firnYearFlag = firnYearFlag;
	}

	public String getSum_dsp_header_no() {
		return sum_dsp_header_no;
	}

	public void setSum_dsp_header_no(String sum_dsp_header_no) {
		this.sum_dsp_header_no = sum_dsp_header_no;
	}

	public String getChallanNumber() {
		return challanNumber;
	}

	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}

	public String getDateOfChallan() {
		return dateOfChallan;
	}

	public void setDateOfChallan(String dateOfChallan) {
		this.dateOfChallan = dateOfChallan;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getLrNumber() {
		return lrNumber;
	}

	public void setLrNumber(String lrNumber) {
		this.lrNumber = lrNumber;
	}

	public String getLrDate() {
		return lrDate;
	}

	public void setLrDate(String lrDate) {
		this.lrDate = lrDate;
	}

	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getBillableWeight() {
		return billableWeight;
	}

	public void setBillableWeight(BigDecimal billableWeight) {
		this.billableWeight = billableWeight;
	}

	public BigDecimal getLrCharges() {
		return lrCharges;
	}

	public void setLrCharges(BigDecimal lrCharges) {
		this.lrCharges = lrCharges;
	}

	public String getChallanValue() {
		return challanValue;
	}

	public void setChallanValue(String challanValue) {
		this.challanValue = challanValue;
	}

	public String getWayBillNumber() {
		return wayBillNumber;
	}

	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}

	public String getChallanRecDate() {
		return challanRecDate;
	}

	public void setChallanRecDate(String challanRecDate) {
		this.challanRecDate = challanRecDate;
	}

	public String getChallanRecBy() {
		return challanRecBy;
	}

	public void setChallanRecBy(String challanRecBy) {
		this.challanRecBy = challanRecBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}

	public String getFinYearFlag() {
		return finYearFlag;
	}

	public void setFinYearFlag(String finYearFlag) {
		this.finYearFlag = finYearFlag;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	

}
