package com.excel.bean;

public class EInvoiceByIrnDataSuperTax {
	
	private String Irn;
	private DocDtls DocDtls;
	private SellerDtls SellerDtls;
	private EwbDtls EwbDtls;
	
	public class DocDtls {
		private String Typ;
		private String No;
		private String Dt;
		public String getTyp() {
			return Typ;
		}
		public void setTyp(String typ) {
			Typ = typ;
		}
		public String getNo() {
			return No;
		}
		public void setNo(String no) {
			No = no;
		}
		public String getDt() {
			return Dt;
		}
		public void setDt(String dt) {
			Dt = dt;
		}
		
	}
	
	public class SellerDtls {
		private String Gstin;
		
		public String getGstin() {
			return Gstin;
		}
		public void setGstin(String gstin) {
			Gstin = gstin;
		}
		
	}
	
	
	public class EwbDtls{
		  private String TransId;
		  private String TransName;
		  private String Distance;
		  private String TransDocNo;
		  private String TransDocDt;
		  private String VehNo;
		  private String VehType;
		  private String TransMode;
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
		public String getDistance() {
			return Distance;
		}
		public void setDistance(String distance) {
			Distance = distance;
		}
		public String getTransDocNo() {
			return TransDocNo;
		}
		public void setTransDocNo(String transDocNo) {
			TransDocNo = transDocNo;
		}
		public String getTransDocDt() {
			return TransDocDt;
		}
		public void setTransDocDt(String transDocDt) {
			TransDocDt = transDocDt;
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
		public String getTransMode() {
			return TransMode;
		}
		public void setTransMode(String transMode) {
			TransMode = transMode;
		}
		  
		}
	
	


	public EInvoiceByIrnDataSuperTax() {
		super();
		Irn = "";
		DocDtls = new DocDtls();
		SellerDtls = new SellerDtls();
		EwbDtls = new EwbDtls();
	}


	public String getIrn() {
		return Irn;
	}


	public void setIrn(String irn) {
		Irn = irn;
	}


	public DocDtls getDocDtls() {
		return DocDtls;
	}


	public void setDocDtls(DocDtls docDtls) {
		DocDtls = docDtls;
	}


	public SellerDtls getSellerDtls() {
		return SellerDtls;
	}


	public void setSellerDtls(SellerDtls sellerDtls) {
		SellerDtls = sellerDtls;
	}


	public EwbDtls getEwbDtls() {
		return EwbDtls;
	}


	public void setEwbDtls(EwbDtls ewbDtls) {
		EwbDtls = ewbDtls;
	}
	
	

}
