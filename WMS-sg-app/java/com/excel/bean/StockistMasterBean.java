package com.excel.bean;

import java.sql.Date;

public class StockistMasterBean {
	
	private PrimaryDetails primary;
	private GstDetails gst;
	private ShippingDetails shipping;
	private String companyCode;
	private String mode;
	private String empId;
	private Long cust_Id;
	
	
	

	public Long getCust_Id() {
		return cust_Id;
	}

	public void setCust_Id(Long cust_Id) {
		this.cust_Id = cust_Id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public GstDetails getGst() {
		return gst;
	}

	public void setGst(GstDetails gst) {
		this.gst = gst;
	}


	public ShippingDetails getShipping() {
		return shipping;
	}



	public void setShipping(ShippingDetails shipping) {
		this.shipping = shipping;
	}



	public PrimaryDetails getPrimary() {
		return primary;
	}



	public void setPrimary(PrimaryDetails primary) {
		this.primary = primary;
	}



	public static class PrimaryDetails {
		private Long cfaLocId;
		private String cust_name;
		private String shortName;
		private String erpcustCode;
		private String phone;
		private String email;
		private String add1;
		private String add2;
		private String add3;
		private String add4;
		private Long pincode;
		private String destination;
		private String stateId;
		private String transporter;
		private String contactPerson;
		private String invType;
		private String entCustCode;
		private String billStatus;
		private String turnOver;
		private String discontinue;
		private String allowGRNInd;
		private String stdNarration;
		private String erpPlantCode;
		
		
		
		public String getErpPlantCode() {
			return erpPlantCode;
		}
		public void setErpPlantCode(String erpPlantCode) {
			this.erpPlantCode = erpPlantCode;
		}
		public Long getCfaLocId() {
			return cfaLocId;
		}
		public void setCfaLocId(Long cfaLocId) {
			this.cfaLocId = cfaLocId;
		}
		public String getCust_name() {
			return cust_name;
		}
		public void setCust_name(String cust_name) {
			this.cust_name = cust_name;
		}
		public String getShortName() {
			return shortName;
		}
		public void setShortName(String shortName) {
			this.shortName = shortName;
		}
		public String getErpcustCode() {
			return erpcustCode;
		}
		public void setErpcustCode(String erpcustCode) {
			this.erpcustCode = erpcustCode;
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
		public String getAdd1() {
			return add1;
		}
		public void setAdd1(String add1) {
			this.add1 = add1;
		}
		public String getAdd2() {
			return add2;
		}
		public void setAdd2(String add2) {
			this.add2 = add2;
		}
		public String getAdd3() {
			return add3;
		}
		public void setAdd3(String add3) {
			this.add3 = add3;
		}
		public String getAdd4() {
			return add4;
		}
		public void setAdd4(String add4) {
			this.add4 = add4;
		}
		public Long getPincode() {
			return pincode;
		}
		public void setPincode(Long pincode) {
			this.pincode = pincode;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public String getStateId() {
			return stateId;
		}
		public void setStateId(String stateId) {
			this.stateId = stateId;
		}
		public String getTransporter() {
			return transporter;
		}
		public void setTransporter(String transporter) {
			this.transporter = transporter;
		}
		public String getContactPerson() {
			return contactPerson;
		}
		public void setContactPerson(String contactPerson) {
			this.contactPerson = contactPerson;
		}
		public String getInvType() {
			return invType;
		}
		public void setInvType(String invType) {
			this.invType = invType;
		}
		public String getEntCustCode() {
			return entCustCode;
		}
		public void setEntCustCode(String entCustCode) {
			this.entCustCode = entCustCode;
		}
		public String getBillStatus() {
			return billStatus;
		}
		public void setBillStatus(String billStatus) {
			this.billStatus = billStatus;
		}
		public String getTurnOver() {
			return turnOver;
		}
		public void setTurnOver(String turnOver) {
			this.turnOver = turnOver;
		}
		public String getDiscontinue() {
			return discontinue;
		}
		public void setDiscontinue(String discontinue) {
			this.discontinue = discontinue;
		}
		public String getAllowGRNInd() {
			return allowGRNInd;
		}
		public void setAllowGRNInd(String allowGRNInd) {
			this.allowGRNInd = allowGRNInd;
		}
		public String getStdNarration() {
			return stdNarration;
		}
		public void setStdNarration(String stdNarration) {
			this.stdNarration = stdNarration;
		}
			
		
	}
	
	public static class GstDetails {
		
		private String gstNo;
		private Date gstValidfrom;
		private Date gstValidTo;
		private String panNo;
		private String drug1;
		private String drug2;
		private String drug3;
		private Date drug1Valid;
		private Date drug2Valid;
		private Date drug3Valid;
		private String telNo;
		public String getGstNo() {
			return gstNo;
		}
		public void setGstNo(String gstNo) {
			this.gstNo = gstNo;
		}
		public Date getGstValidfrom() {
			return gstValidfrom;
		}
		public void setGstValidfrom(Date gstValidfrom) {
			this.gstValidfrom = gstValidfrom;
		}
		public Date getGstValidTo() {
			return gstValidTo;
		}
		public void setGstValidTo(Date gstValidTo) {
			this.gstValidTo = gstValidTo;
		}
		public String getPanNo() {
			return panNo;
		}
		public void setPanNo(String panNo) {
			this.panNo = panNo;
		}
		public String getDrug1() {
			return drug1;
		}
		public void setDrug1(String drug1) {
			this.drug1 = drug1;
		}
		public String getDrug2() {
			return drug2;
		}
		public void setDrug2(String drug2) {
			this.drug2 = drug2;
		}
		public String getDrug3() {
			return drug3;
		}
		public void setDrug3(String drug3) {
			this.drug3 = drug3;
		}
		public Date getDrug1Valid() {
			return drug1Valid;
		}
		public void setDrug1Valid(Date drug1Valid) {
			this.drug1Valid = drug1Valid;
		}
		public Date getDrug2Valid() {
			return drug2Valid;
		}
		public void setDrug2Valid(Date drug2Valid) {
			this.drug2Valid = drug2Valid;
		}
		public Date getDrug3Valid() {
			return drug3Valid;
		}
		public void setDrug3Valid(Date drug3Valid) {
			this.drug3Valid = drug3Valid;
		}
		public String getTelNo() {
			return telNo;
		}
		public void setTelNo(String telNo) {
			this.telNo = telNo;
		}
	
	}
	
	public static class ShippingDetails {
		
		private String shipCustomer;
		private String sadd1;
		private String sadd2;
		private String sadd3;
		private String sadd4;
		private String pinCode;
		private String stateCode;
		private String shipDestination;


		public String getShipCustomer() {
			return shipCustomer;
		}


		public void setShipCustomer(String shipCustomer) {
			this.shipCustomer = shipCustomer;
		}



		public String getSadd1() {
			return sadd1;
		}


		public void setSadd1(String sadd1) {
			this.sadd1 = sadd1;
		}


		public String getSadd2() {
			return sadd2;
		}


		public void setSadd2(String sadd2) {
			this.sadd2 = sadd2;
		}


		public String getSadd3() {
			return sadd3;
		}


		public void setSadd3(String sadd3) {
			this.sadd3 = sadd3;
		}


		public String getSadd4() {
			return sadd4;
		}


		public void setSadd4(String sadd4) {
			this.sadd4 = sadd4;
		}


		public String getPinCode() {
			return pinCode;
		}


		public void setPinCode(String pinCode) {
			this.pinCode = pinCode;
		}


		public String getStateCode() {
			return stateCode;
		}


		public void setStateCode(String stateCode) {
			this.stateCode = stateCode;
		}


		public String getShipDestination() {
			return shipDestination;
		}


		public void setShipDestination(String shipDestination) {
			this.shipDestination = shipDestination;
		}


		
		
		
	}

}
