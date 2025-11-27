package com.excel.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class EInvoiceData {
	private String Version;
	private TranDtls TranDtls;
	private DocDtls DocDtls;
	private SellerDtls SellerDtls;
	private BuyerDtls BuyerDtls;
	private DispDtls DispDtls;
	private ShipDtls ShipDtls;
	private List<ItemList> ItemList;
	private ValDtls ValDtls;
	private PayDtls PayDtls;
	private RefDtls RefDtls;
	private List<AddlDocDtls> AddlDocDtls;
	private ExpDtls ExpDtls;
	private EwbDtls EwbDtls;
	private custom_fields custom_fields;
	
	public class TranDtls {
		private String TaxSch;
		private String SupTyp;
		private String RegRev;
		private String EcmGstin;
		private String IgstOnIntra;
		public String getTaxSch() {
			return TaxSch;
		}
		public void setTaxSch(String taxSch) {
			TaxSch = taxSch;
		}
		public String getSupTyp() {
			return SupTyp;
		}
		public void setSupTyp(String supTyp) {
			SupTyp = supTyp;
		}
		public String getRegRev() {
			return RegRev;
		}
		public void setRegRev(String regRev) {
			RegRev = regRev;
		}
		public String getEcmGstin() {
			return EcmGstin;
		}
		public void setEcmGstin(String ecmGstin) {
			EcmGstin = ecmGstin;
		}
		public String getIgstOnIntra() {
			return IgstOnIntra;
		}
		public void setIgstOnIntra(String igstOnIntra) {
			IgstOnIntra = igstOnIntra;
		}
		
	}
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
		private String LglNm;
		private String TrdNm;
		private String Addr1;
		private transient String Addr2;
		private String Loc;
		private String Pin;
		private String Stcd;
		private transient String Ph;
		private transient String Em;
		public String getGstin() {
			return Gstin;
		}
		public void setGstin(String gstin) {
			Gstin = gstin;
		}
		public String getLglNm() {
			return LglNm;
		}
		public void setLglNm(String lglNm) {
			LglNm = lglNm;
		}
		public String getTrdNm() {
			return TrdNm;
		}
		public void setTrdNm(String trdNm) {
			TrdNm = trdNm;
		}
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
		public String getPh() {
			return Ph;
		}
		public void setPh(String ph) {
			Ph = ph;
		}
		public String getEm() {
			return Em;
		}
		public void setEm(String em) {
			Em = em;
		}
		
	}
	public class BuyerDtls {
		private String Gstin;
		private String LglNm;
		private String TrdNm;
		private String Pos;
		private String Addr1;
		private transient String Addr2;
		private String Loc;
		private String Pin;
		private String Stcd;
		private transient String Ph;
		private transient String Em;
		public String getGstin() {
			return Gstin;
		}
		public void setGstin(String gstin) {
			Gstin = gstin;
		}
		public String getLglNm() {
			return LglNm;
		}
		public void setLglNm(String lglNm) {
			LglNm = lglNm;
		}
		public String getTrdNm() {
			return TrdNm;
		}
		public void setTrdNm(String trdNm) {
			TrdNm = trdNm;
		}
		public String getPos() {
			return Pos;
		}
		public void setPos(String pos) {
			Pos = pos;
		}
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
		public String getPh() {
			return Ph;
		}
		public void setPh(String ph) {
			Ph = ph;
		}
		public String getEm() {
			return Em;
		}
		public void setEm(String em) {
			Em = em;
		}
		
	}
	public class DispDtls {
		private String Nm;
		private String Addr1;
		private transient String Addr2;
		private String Loc;
		private String Pin;
		private String Stcd;
		public String getNm() {
			return Nm;
		}
		public void setNm(String nm) {
			Nm = nm;
		}
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
	public class ShipDtls {
		private String Gstin;
		private String LglNm;
		private String TrdNm;
		private String Addr1;
		private transient String Addr2;
		private String Loc;
		private String Pin;
		private String Stcd;
		public String getGstin() {
			return Gstin;
		}
		public void setGstin(String gstin) {
			Gstin = gstin;
		}
		public String getLglNm() {
			return LglNm;
		}
		public void setLglNm(String lglNm) {
			LglNm = lglNm;
		}
		public String getTrdNm() {
			return TrdNm;
		}
		public void setTrdNm(String trdNm) {
			TrdNm = trdNm;
		}
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
	public class ItemList {
		private String SlNo;
		private String PrdDesc;
		private String IsServc;
		private String HsnCd;
		private String Barcde;
		private String Qty;
		private String FreeQty;
		private String Unit;
		private String UnitPrice;
		private String TotAmt;
		private String Discount;
		private String PreTaxVal;
		private String AssAmt;
		private String GstRt;
		private String IgstAmt;
		private String CgstAmt;
		private String SgstAmt;
		private String CesRt;
		private String CesAmt;
		private String CesNonAdvlAmt;
		private String StateCesRt;
		private String StateCesAmt;
		private String StateCesNonAdvlAmt;
		private String OthChrg;
		private String TotItemVal;
		private String OrdLineRef;
		private String OrgCntry;
		private String PrdSlNo;
		private BchDtls BchDtls;
		private List<AttribDtls> AttribDtls;
		public class BchDtls{
			private String Nm;
			private String ExpDt;
			private String WrDt;
			public String getNm() {
				return Nm;
			}
			public void setNm(String nm) {
				Nm = nm;
			}
			public String getExpDt() {
				return ExpDt;
			}
			public void setExpDt(String expDt) {
				ExpDt = expDt;
			}
			public String getWrDt() {
				return WrDt;
			}
			public void setWrDt(String wrDt) {
				WrDt = wrDt;
			}
			
         }
        public class AttribDtls{
        	private String Nm;
        	private String Val;
			public String getNm() {
				return Nm;
			}
			public void setNm(String nm) {
				Nm = nm;
			}
			public String getVal() {
				return Val;
			}
			public void setVal(String val) {
				Val = val;
			}
        	
         }
		public String getSlNo() {
			return SlNo;
		}
		public void setSlNo(String slNo) {
			SlNo = slNo;
		}
		public String getPrdDesc() {
			return PrdDesc;
		}
		public void setPrdDesc(String prdDesc) {
			PrdDesc = prdDesc;
		}
		public String getIsServc() {
			return IsServc;
		}
		public void setIsServc(String isServc) {
			IsServc = isServc;
		}
		public String getHsnCd() {
			return HsnCd;
		}
		public void setHsnCd(String hsnCd) {
			HsnCd = hsnCd;
		}
		public String getBarcde() {
			return Barcde;
		}
		public void setBarcde(String barcde) {
			Barcde = barcde;
		}
		public String getQty() {
			return Qty;
		}
		public void setQty(String qty) {
			Qty = qty;
		}
		public String getFreeQty() {
			return FreeQty;
		}
		public void setFreeQty(String freeQty) {
			FreeQty = freeQty;
		}
		public String getUnit() {
			return Unit;
		}
		public void setUnit(String unit) {
			Unit = unit;
		}
		public String getUnitPrice() {
			return UnitPrice;
		}
		public void setUnitPrice(String unitPrice) {
			UnitPrice = unitPrice;
		}
		public String getTotAmt() {
			return TotAmt;
		}
		public void setTotAmt(String totAmt) {
			TotAmt = totAmt;
		}
		public String getDiscount() {
			return Discount;
		}
		public void setDiscount(String discount) {
			Discount = discount;
		}
		public String getPreTaxVal() {
			return PreTaxVal;
		}
		public void setPreTaxVal(String preTaxVal) {
			PreTaxVal = preTaxVal;
		}
		public String getAssAmt() {
			return AssAmt;
		}
		public void setAssAmt(String assAmt) {
			AssAmt = assAmt;
		}
		public String getGstRt() {
			return GstRt;
		}
		public void setGstRt(String gstRt) {
			GstRt = gstRt;
		}
		public String getIgstAmt() {
			return IgstAmt;
		}
		public void setIgstAmt(String igstAmt) {
			IgstAmt = igstAmt;
		}
		public String getCgstAmt() {
			return CgstAmt;
		}
		public void setCgstAmt(String cgstAmt) {
			CgstAmt = cgstAmt;
		}
		public String getSgstAmt() {
			return SgstAmt;
		}
		public void setSgstAmt(String sgstAmt) {
			SgstAmt = sgstAmt;
		}
		public String getCesRt() {
			return CesRt;
		}
		public void setCesRt(String cesRt) {
			CesRt = cesRt;
		}
		public String getCesAmt() {
			return CesAmt;
		}
		public void setCesAmt(String cesAmt) {
			CesAmt = cesAmt;
		}
		public String getCesNonAdvlAmt() {
			return CesNonAdvlAmt;
		}
		public void setCesNonAdvlAmt(String cesNonAdvlAmt) {
			CesNonAdvlAmt = cesNonAdvlAmt;
		}
		public String getStateCesRt() {
			return StateCesRt;
		}
		public void setStateCesRt(String stateCesRt) {
			StateCesRt = stateCesRt;
		}
		public String getStateCesAmt() {
			return StateCesAmt;
		}
		public void setStateCesAmt(String stateCesAmt) {
			StateCesAmt = stateCesAmt;
		}
		public String getStateCesNonAdvlAmt() {
			return StateCesNonAdvlAmt;
		}
		public void setStateCesNonAdvlAmt(String stateCesNonAdvlAmt) {
			StateCesNonAdvlAmt = stateCesNonAdvlAmt;
		}
		public String getOthChrg() {
			return OthChrg;
		}
		public void setOthChrg(String othChrg) {
			OthChrg = othChrg;
		}
		public String getTotItemVal() {
			return TotItemVal;
		}
		public void setTotItemVal(String totItemVal) {
			TotItemVal = totItemVal;
		}
		public String getOrdLineRef() {
			return OrdLineRef;
		}
		public void setOrdLineRef(String ordLineRef) {
			OrdLineRef = ordLineRef;
		}
		public String getOrgCntry() {
			return OrgCntry;
		}
		public void setOrgCntry(String orgCntry) {
			OrgCntry = orgCntry;
		}
		public String getPrdSlNo() {
			return PrdSlNo;
		}
		public void setPrdSlNo(String prdSlNo) {
			PrdSlNo = prdSlNo;
		}
		public BchDtls getBchDtls() {
			return BchDtls;
		}
		public void setBchDtls(BchDtls bchDtls) {
			BchDtls = bchDtls;
		}
		public List<AttribDtls> getAttribDtls() {
			return AttribDtls;
		}
		public void setAttribDtls(List<AttribDtls> attribDtls) {
			AttribDtls = attribDtls;
		}
      
	}
	
	public class ValDtls{
		private String AssVal;
		private String CgstVal;
		private String SgstVal;
		private String IgstVal;
		private String CesVal;
		private String StCesVal;
		private String Discount;
		private String OthChrg;
		private String RndOffAmt;
		private String TotInvVal;
		private String TotInvValFc;
		public String getAssVal() {
			return AssVal;
		}
		public void setAssVal(String assVal) {
			AssVal = assVal;
		}
		public String getCgstVal() {
			return CgstVal;
		}
		public void setCgstVal(String cgstVal) {
			CgstVal = cgstVal;
		}
		public String getSgstVal() {
			return SgstVal;
		}
		public void setSgstVal(String sgstVal) {
			SgstVal = sgstVal;
		}
		public String getIgstVal() {
			return IgstVal;
		}
		public void setIgstVal(String igstVal) {
			IgstVal = igstVal;
		}
		public String getCesVal() {
			return CesVal;
		}
		public void setCesVal(String cesVal) {
			CesVal = cesVal;
		}
		public String getStCesVal() {
			return StCesVal;
		}
		public void setStCesVal(String stCesVal) {
			StCesVal = stCesVal;
		}
		public String getDiscount() {
			return Discount;
		}
		public void setDiscount(String discount) {
			Discount = discount;
		}
		public String getOthChrg() {
			return OthChrg;
		}
		public void setOthChrg(String othChrg) {
			OthChrg = othChrg;
		}
		public String getRndOffAmt() {
			return RndOffAmt;
		}
		public void setRndOffAmt(String rndOffAmt) {
			RndOffAmt = rndOffAmt;
		}
		public String getTotInvVal() {
			return TotInvVal;
		}
		public void setTotInvVal(String totInvVal) {
			TotInvVal = totInvVal;
		}
		public String getTotInvValFc() {
			return TotInvValFc;
		}
		public void setTotInvValFc(String totInvValFc) {
			TotInvValFc = totInvValFc;
		}
		
     }
	
	public class PayDtls{
		private String Nm;
		private String AccDet;
		private String Mode;
		private String FininsBr;
		private String PayTerm;
		private String PayInstr;
		private String CrTrn;
		private String DirDr;
		private String CrDay;
		private String PaidAmt;
		private String PaymtDue;
		public String getNm() {
			return Nm;
		}
		public void setNm(String nm) {
			Nm = nm;
		}
		public String getAccDet() {
			return AccDet;
		}
		public void setAccDet(String accDet) {
			AccDet = accDet;
		}
		public String getMode() {
			return Mode;
		}
		public void setMode(String mode) {
			Mode = mode;
		}
		public String getFininsBr() {
			return FininsBr;
		}
		public void setFininsBr(String fininsBr) {
			FininsBr = fininsBr;
		}
		public String getPayTerm() {
			return PayTerm;
		}
		public void setPayTerm(String payTerm) {
			PayTerm = payTerm;
		}
		public String getPayInstr() {
			return PayInstr;
		}
		public void setPayInstr(String payInstr) {
			PayInstr = payInstr;
		}
		public String getCrTrn() {
			return CrTrn;
		}
		public void setCrTrn(String crTrn) {
			CrTrn = crTrn;
		}
		public String getDirDr() {
			return DirDr;
		}
		public void setDirDr(String dirDr) {
			DirDr = dirDr;
		}
		public String getCrDay() {
			return CrDay;
		}
		public void setCrDay(String crDay) {
			CrDay = crDay;
		}
		public String getPaidAmt() {
			return PaidAmt;
		}
		public void setPaidAmt(String paidAmt) {
			PaidAmt = paidAmt;
		}
		public String getPaymtDue() {
			return PaymtDue;
		}
		public void setPaymtDue(String paymtDue) {
			PaymtDue = paymtDue;
		}
		
	}
	public class RefDtls{
		private String InvRm;
		private DocPerdDtls DocPerdDtls;
		private List<PrecDocDtls> PrecDocDtls;
		private List<ContrDtls> ContrDtls;
		private class DocPerdDtls{
			private String InvStDt;
			private String InvEndDt;
			public String getInvStDt() {
				return InvStDt;
			}
			public void setInvStDt(String invStDt) {
				InvStDt = invStDt;
			}
			public String getInvEndDt() {
				return InvEndDt;
			}
			public void setInvEndDt(String invEndDt) {
				InvEndDt = invEndDt;
			}
			
        }
		private class PrecDocDtls{
        	   private String InvNo;
        	   private String InvDt;
        	   private String OthRefNo;
			public String getInvNo() {
				return InvNo;
			}
			public void setInvNo(String invNo) {
				InvNo = invNo;
			}
			public String getInvDt() {
				return InvDt;
			}
			public void setInvDt(String invDt) {
				InvDt = invDt;
			}
			public String getOthRefNo() {
				return OthRefNo;
			}
			public void setOthRefNo(String othRefNo) {
				OthRefNo = othRefNo;
			}
        	   
           }
		private class ContrDtls{
        	   private String RecAdvRefr;
        	   private String RecAdvDt;
        	   private String Tendrefr;
        	   private String Contrrefr;
        	   private String Extrefr;
        	   private String Projrefr;
        	   private String Porefr;
              private String PoRefDt;
			public String getRecAdvRefr() {
				return RecAdvRefr;
			}
			public void setRecAdvRefr(String recAdvRefr) {
				RecAdvRefr = recAdvRefr;
			}
			public String getRecAdvDt() {
				return RecAdvDt;
			}
			public void setRecAdvDt(String recAdvDt) {
				RecAdvDt = recAdvDt;
			}
			public String getTendrefr() {
				return Tendrefr;
			}
			public void setTendrefr(String tendrefr) {
				Tendrefr = tendrefr;
			}
			public String getContrrefr() {
				return Contrrefr;
			}
			public void setContrrefr(String contrrefr) {
				Contrrefr = contrrefr;
			}
			public String getExtrefr() {
				return Extrefr;
			}
			public void setExtrefr(String extrefr) {
				Extrefr = extrefr;
			}
			public String getProjrefr() {
				return Projrefr;
			}
			public void setProjrefr(String projrefr) {
				Projrefr = projrefr;
			}
			public String getPorefr() {
				return Porefr;
			}
			public void setPorefr(String porefr) {
				Porefr = porefr;
			}
			public String getPoRefDt() {
				return PoRefDt;
			}
			public void setPoRefDt(String poRefDt) {
				PoRefDt = poRefDt;
			}
              
           }
		public String getInvRm() {
			return InvRm;
		}
		public void setInvRm(String invRm) {
			InvRm = invRm;
		}
		public DocPerdDtls getDocPerdDtls() {
			return DocPerdDtls;
		}
		public void setDocPerdDtls(DocPerdDtls docPerdDtls) {
			DocPerdDtls = docPerdDtls;
		}
		public List<PrecDocDtls> getPrecDocDtls() {
			return PrecDocDtls;
		}
		public void setPrecDocDtls(List<PrecDocDtls> precDocDtls) {
			PrecDocDtls = precDocDtls;
		}
		public List<ContrDtls> getContrDtls() {
			return ContrDtls;
		}
		public void setContrDtls(List<ContrDtls> contrDtls) {
			ContrDtls = contrDtls;
		}
		
     }
	
	public class AddlDocDtls{
		private String Url;
		private String Docs;
		private String Info;
		public String getUrl() {
			return Url;
		}
		public void setUrl(String url) {
			Url = url;
		}
		public String getDocs() {
			return Docs;
		}
		public void setDocs(String docs) {
			Docs = docs;
		}
		public String getInfo() {
			return Info;
		}
		public void setInfo(String info) {
			Info = info;
		}
		
    }
	public class ExpDtls{
		private String ShipBNo;
		private String ShipBDt;
		private String Port;
		private String RefClm;
		private String ForCur;
		private String CntCode;
		public String getShipBNo() {
			return ShipBNo;
		}
		public void setShipBNo(String shipBNo) {
			ShipBNo = shipBNo;
		}
		public String getShipBDt() {
			return ShipBDt;
		}
		public void setShipBDt(String shipBDt) {
			ShipBDt = shipBDt;
		}
		public String getPort() {
			return Port;
		}
		public void setPort(String port) {
			Port = port;
		}
		public String getRefClm() {
			return RefClm;
		}
		public void setRefClm(String refClm) {
			RefClm = refClm;
		}
		public String getForCur() {
			return ForCur;
		}
		public void setForCur(String forCur) {
			ForCur = forCur;
		}
		public String getCntCode() {
			return CntCode;
		}
		public void setCntCode(String cntCode) {
			CntCode = cntCode;
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
	public class custom_fields{
	 private String customfieldLable1;
	 private String customfieldLable2;
	 private String customfieldLable3;
	public String getCustomfieldLable1() {
		return customfieldLable1;
	}
	public void setCustomfieldLable1(String customfieldLable1) {
		this.customfieldLable1 = customfieldLable1;
	}
	public String getCustomfieldLable2() {
		return customfieldLable2;
	}
	public void setCustomfieldLable2(String customfieldLable2) {
		this.customfieldLable2 = customfieldLable2;
	}
	public String getCustomfieldLable3() {
		return customfieldLable3;
	}
	public void setCustomfieldLable3(String customfieldLable3) {
		this.customfieldLable3 = customfieldLable3;
	}
	 
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public TranDtls getTranDtls() {
		return TranDtls;
	}
	public void setTranDtls(TranDtls tranDtls) {
		this.TranDtls = tranDtls;
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
	public BuyerDtls getBuyerDtls() {
		return BuyerDtls;
	}
	public void setBuyerDtls(BuyerDtls buyerDtls) {
		BuyerDtls = buyerDtls;
	}
	public DispDtls getDispDtls() {
		return DispDtls;
	}
	public void setDispDtls(DispDtls dispDtls) {
		DispDtls = dispDtls;
	}
	public ShipDtls getShipDtls() {
		return ShipDtls;
	}
	public void setShipDtls(ShipDtls shipDtls) {
		ShipDtls = shipDtls;
	}
	public List<ItemList> getItemList() {
		return ItemList;
	}
	public void setItemList(List<ItemList> itemList) {
		ItemList = itemList;
	}
	public ValDtls getValDtls() {
		return ValDtls;
	}
	public void setValDtls(ValDtls valDtls) {
		ValDtls = valDtls;
	}
	public PayDtls getPayDtls() {
		return PayDtls;
	}
	public void setPayDtls(PayDtls payDtls) {
		PayDtls = payDtls;
	}
	public RefDtls getRefDtls() {
		return RefDtls;
	}
	public void setRefDtls(RefDtls refDtls) {
		RefDtls = refDtls;
	}
	public List<AddlDocDtls> getAddlDocDtls() {
		return AddlDocDtls;
	}
	public void setAddlDocDtls(List<AddlDocDtls> addlDocDtls) {
		AddlDocDtls = addlDocDtls;
	}
	public ExpDtls getExpDtls() {
		return ExpDtls;
	}
	public void setExpDtls(ExpDtls expDtls) {
		ExpDtls = expDtls;
	}
	public EwbDtls getEwbDtls() {
		return EwbDtls;
	}
	public void setEwbDtls(EwbDtls ewbDtls) {
		EwbDtls = ewbDtls;
	}
	public custom_fields getCustom_fields() {
		return custom_fields;
	}
	public void setCustom_fields(custom_fields custom_fields) {
		this.custom_fields = custom_fields;
	}
	 
}
