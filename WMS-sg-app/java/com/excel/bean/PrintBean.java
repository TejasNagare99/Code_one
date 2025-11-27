package com.excel.bean;

import java.util.Date;

public class PrintBean {
	
	//IAA Doc Print
	private String fromIAA;
	private String toIAA;
	
	//Labels Print
	private String locationId;
	private String vendorId;
	private Date startDate;
	private Date endDate;
	private String fromGRN;
	private String toGRN;
	private String binChecked;
	private String prodType;
	//GST delivery chaallan print
	String teamDivision;
	String financialYear;
	String forPeriod;
	String location;
	String reportOption;
	String dispatchType;
	String cnfLocation;
	String fromChallan;
	String toChallan;
	String dotMatrixLaser;
	String showAmount;
	String pageIndQuery;
	String pageInd;
	
	//dispatch chaLLAN
	private String sumdsp_challan_no;
	private String sumdsp_challan_dt;
	private String cfa;
	private String team;
	private String dptdestination;
	private String sumdsp_lr_no;
	private String transporter;
	private Integer cases;
	//Hq master audit trail
	private String divid;
	//stock transfer invoice
	private String frm_dt;
	private String to_dt;
		//UI
		private String slId;
		private String rlId;
		private String fromTrf;
		private String toTrf;
		
		
		private String finyearflag;
		private String finyearflg;
		private String printExcel;

		private String finyear;
		private String companyCode;
		
		
	
	public String getCompanyCode() {
			return companyCode;
		}
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
	public String getFinyear() {
			return finyear;
		}
		public void setFinyear(String finyear) {
			this.finyear = finyear;
		}
	public String getFinyearflag() {
			return finyearflag;
		}
		public void setFinyearflag(String finyearflag) {
			this.finyearflag = finyearflag;
		}
		public String getFinyearflg() {
			return finyearflg;
		}
		public void setFinyearflg(String finyearflg) {
			this.finyearflg = finyearflg;
		}
		public String getPrintExcel() {
			return printExcel;
		}
		public void setPrintExcel(String printExcel) {
			this.printExcel = printExcel;
		}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getFromGRN() {
		return fromGRN;
	}
	public void setFromGRN(String fromGRN) {
		this.fromGRN = fromGRN;
	}
	public String getToGRN() {
		return toGRN;
	}
	public void setToGRN(String toGRN) {
		this.toGRN = toGRN;
	}
	public String getBinChecked() {
		return binChecked;
	}
	public void setBinChecked(String binChecked) {
		this.binChecked = binChecked;
	}
	public String getTeamDivision() {
		return teamDivision;
	}
	public void setTeamDivision(String teamDivision) {
		this.teamDivision = teamDivision;
	}
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public String getForPeriod() {
		return forPeriod;
	}
	public void setForPeriod(String forPeriod) {
		this.forPeriod = forPeriod;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getReportOption() {
		return reportOption;
	}
	public void setReportOption(String reportOption) {
		this.reportOption = reportOption;
	}
	public String getDispatchType() {
		return dispatchType;
	}
	public void setDispatchType(String dispatchType) {
		this.dispatchType = dispatchType;
	}
	public String getCnfLocation() {
		return cnfLocation;
	}
	public void setCnfLocation(String cnfLocation) {
		this.cnfLocation = cnfLocation;
	}
	public String getFromChallan() {
		return fromChallan;
	}
	public void setFromChallan(String fromChallan) {
		this.fromChallan = fromChallan;
	}
	public String getToChallan() {
		return toChallan;
	}
	public void setToChallan(String toChallan) {
		this.toChallan = toChallan;
	}
	public String getDotMatrixLaser() {
		return dotMatrixLaser;
	}
	public void setDotMatrixLaser(String dotMatrixLaser) {
		this.dotMatrixLaser = dotMatrixLaser;
	}
	public String getShowAmount() {
		return showAmount;
	}
	public void setShowAmount(String showAmount) {
		this.showAmount = showAmount;
	}
	public String getPageIndQuery() {
		return pageIndQuery;
	}
	public void setPageIndQuery(String pageIndQuery) {
		this.pageIndQuery = pageIndQuery;
	}
	public String getPageInd() {
		return pageInd;
	}
	public void setPageInd(String pageInd) {
		this.pageInd = pageInd;
	}
	public String getSumdsp_challan_no() {
		return sumdsp_challan_no;
	}
	public void setSumdsp_challan_no(String sumdsp_challan_no) {
		this.sumdsp_challan_no = sumdsp_challan_no;
	}
	public String getSumdsp_challan_dt() {
		return sumdsp_challan_dt;
	}
	public void setSumdsp_challan_dt(String sumdsp_challan_dt) {
		this.sumdsp_challan_dt = sumdsp_challan_dt;
	}
	public String getCfa() {
		return cfa;
	}
	public void setCfa(String cfa) {
		this.cfa = cfa;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getDptdestination() {
		return dptdestination;
	}
	public void setDptdestination(String dptdestination) {
		this.dptdestination = dptdestination;
	}
	public String getSumdsp_lr_no() {
		return sumdsp_lr_no;
	}
	public void setSumdsp_lr_no(String sumdsp_lr_no) {
		this.sumdsp_lr_no = sumdsp_lr_no;
	}
	public String getTransporter() {
		return transporter;
	}
	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}
	public Integer getCases() {
		return cases;
	}
	public void setCases(Integer cases) {
		this.cases = cases;
	}
	

	//IAA Doc Print
	
	public String getFromIAA() {
		return fromIAA;
	}
	public void setFromIAA(String fromIAA) {
		this.fromIAA = fromIAA;
	}
	public String getToIAA() {
		return toIAA;
	}
	public void setToIAA(String toIAA) {
		this.toIAA = toIAA;
	}
	public String getDivid() {
		return divid;
	}
	public void setDivid(String divid) {
		this.divid = divid;
	}
	//stock transfer invoice
	public String getFrm_dt() {
		return frm_dt;
	}
	public void setFrm_dt(String frm_dt) {
		this.frm_dt = frm_dt;
	}
	public String getTo_dt() {
		return to_dt;
	}
	public void setTo_dt(String to_dt) {
		this.to_dt = to_dt;
	}
	//UI
	public String getSlId() {
		return slId;
	}
	public void setSlId(String slId) {
		this.slId = slId;
	}
	public String getRlId() {
		return rlId;
	}
	public void setRlId(String rlId) {
		this.rlId = rlId;
	}
	public String getFromTrf() {
		return fromTrf;
	}
	public void setFromTrf(String fromTrf) {
		this.fromTrf = fromTrf;
	}
	public String getToTrf() {
		return toTrf;
	}
	public void setToTrf(String toTrf) {
		this.toTrf = toTrf;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	


}
