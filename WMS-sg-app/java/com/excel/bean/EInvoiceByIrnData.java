package com.excel.bean;

public class EInvoiceByIrnData {
	private String Irn;
	private String Distance;
	private String TransMode;
	private String TransId;
	private String TransName;
	private String TransDocDt;
	private String TransDocNo;
	private String VehNo;
	private String VehType;
	private ExpShipDtls ExpShipDtls;
	
	private transient String Challan_No;
	public String getChallan_No() {
		return Challan_No;
	}


	public void setChallan_No(String challan_No) {
		Challan_No = challan_No;
	}


	public static class ExpShipDtls{
		private String Addr1;
		private String Addr2;
		private String  Loc;
		private String Pin;
		private String  Stcd;
		public String getAddr1() {
			return Addr1;
		}
		public void setAddr1(String addr1) {
			Addr1 = addr1;
		}
		public String getAddr2() {
			return Addr2;
		}
		public void setAddr2(String addr2) {
			Addr2 = addr2;
		}
		public String getLoc() {
			return Loc;
		}
		public void setLoc(String loc) {
			Loc = loc;
		}
		public String getPin() {
			return Pin;
		}
		public void setPin(String pin) {
			Pin = pin;
		}
		public String getStcd() {
			return Stcd;
		}
		public void setStcd(String stcd) {
			Stcd = stcd;
		}
		
	}


	public String getIrn() {
		return Irn;
	}


	public void setIrn(String irn) {
		Irn = irn;
	}


	public String getDistance() {
		return Distance;
	}


	public void setDistance(String distance) {
		Distance = distance;
	}


	public String getTransMode() {
		return TransMode;
	}


	public void setTransMode(String transMode) {
		TransMode = transMode;
	}


	public String getTransId() {
		return TransId;
	}


	public void setTransId(String transId) {
		TransId = transId;
	}


	public String getTransName() {
		return TransName;
	}


	public void setTransName(String transName) {
		TransName = transName;
	}


	public String getTransDocDt() {
		return TransDocDt;
	}


	public void setTransDocDt(String transDocDt) {
		TransDocDt = transDocDt;
	}


	public String getTransDocNo() {
		return TransDocNo;
	}


	public void setTransDocNo(String transDocNo) {
		TransDocNo = transDocNo;
	}


	public String getVehNo() {
		return VehNo;
	}


	public void setVehNo(String vehNo) {
		VehNo = vehNo;
	}


	public String getVehType() {
		return VehType;
	}


	public void setVehType(String vehType) {
		VehType = vehType;
	}


	public ExpShipDtls getExpShipDtls() {
		return ExpShipDtls;
	}


	public void setExpShipDtls(ExpShipDtls expShipDtls) {
		ExpShipDtls = expShipDtls;
	}

}
