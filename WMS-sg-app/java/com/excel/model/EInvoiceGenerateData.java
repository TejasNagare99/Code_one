package com.excel.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "EInvoiceGenerateData")
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "callE_INVOICING_SUMCHL", 
			procedureName = "E_INVOICING_SUMCHL",
			parameters = {
					@StoredProcedureParameter(name = "finflag" , mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name = "finyear" , mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name = "divid" , mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name = "frdspid" , mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name = "todspid" , mode = ParameterMode.IN, type = String.class),
			}, resultClasses = EInvoiceGenerateData.class),
	
	@NamedStoredProcedureQuery(name = "callE_INVOICING_SUMCHL_STOCKIST", 
	procedureName = "E_INVOICING_SUMCHL_STOCKIST",
	parameters = {
			@StoredProcedureParameter(name = "finflag" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "finyear" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "divid" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "frdspid" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "todspid" , mode = ParameterMode.IN, type = String.class),
			@StoredProcedureParameter(name = "locid" , mode = ParameterMode.IN, type = String.class),
	}, resultClasses = EInvoiceGenerateData.class)
})
public class EInvoiceGenerateData implements Serializable {
	
	@Id
	@Column(name = "ROW")
	private Long Row;
	
	@Column(name = "VER")
	private String ver;
	
	@Column(name = "TAXTYPE")
	private String taxType;
	
	@Column(name = "SupTyp")
	private String supTyp;
	
	@Column(name = "RegRev")
	private String regRev;
	
	@Column(name = "EcmGstin")
	private String ecmGstin;
	
	@Column(name = "IgstOnIntra")
	private String igstOnIntra;
	
	@Column(name = "DocDtls_TYP")
	private String docDtls_TYP;
	
	@Column(name = "DocDtls_NO")
	private String docDtls_NO;
	
	@Column(name = "DocDtls_DT")
	private String docDtls_DT;
	
	@Column(name = "SellerDtls_GSTIN")
	private String sellerDtls_GSTIN;
	
	@Column(name = "SellerDtlsLglNm")
	private String sellerDtlsLglNm;
	
	@Column(name = "SellerDtlsTrdNm")
	private String sellerDtlsTrdNm;
	
	@Column(name = "SellerDtlsAddr1")
	private String sellerDtlsAddr1;
	
	@Column(name = "SellerDtlsAddr2")
	private String sellerDtlsAddr2;
	
	@Column(name = "SellerDtlsloc")
	private String sellerDtlsloc;
	
	@Column(name = "SellerDtlsPin")
	private String sellerDtlsPin;
	
	@Column(name = "SellerDtlsStcd")
	private String sellerDtlsStcd;
	
	@Column(name = "SellerDtlsPh")
	private String sellerDtlsPh;
	
	@Column(name = "SellerDtlsEm")
	private String sellerDtlsEm;
	
	@Column(name = "BuyerDtlsGstin")
	private String buyerDtlsGstin;
	
	@Column(name = "BuyerDtlsLglNm")
	private String buyerDtlsLglNm;
	
	@Column(name = "BuyerDtlsTrdNm")
	private String buyerDtlsTrdNm;
	
	@Column(name = "BuyerDtlsPos")
	private String buyerDtlsPos;
	
	@Column(name = "BuyerDtlsAddr1")
	private String buyerDtlsAddr1;
	
	@Column(name = "BuyerDtlsAddr2")
	private String buyerDtlsAddr2;
	
	@Column(name = "BuyerDtlsLoc")
	private String buyerDtlsLoc;
	
	@Column(name = "BuyerDtlsPin")
	private String buyerDtlsPin;
	
	@Column(name = "BuyerDtlsStcd")
	private String buyerDtlsStcd;
	
	@Column(name = "BuyerDtlsPh")
	private String buyerDtlsPh;
	
	@Column(name = "BuyerDtlsem")
	private String buyerDtlsem;
	
	@Column(name = "DispDtlsNm")
	private String dispDtlsNm;
	
	@Column(name = "DispDtlsAddr1")
	private String dispDtlsAddr1;
	
	@Column(name = "DispDtlsAddr2")
	private String dispDtlsAddr2;
	
	@Column(name = "DispDtlsLoc")
	private String dispDtlsLoc;
	
	@Column(name = "DispDtlsPin")
	private String dispDtlsPin;
	
	@Column(name = "DispDtlsStcd")
	private String dispDtlsStcd;
	
	@Column(name = "ShipDtlsGstin")
	private String shipDtlsGstin;
	
	@Column(name = "ShipDtlsLglnm")
	private String shipDtlsLglnm;
	
	@Column(name = "ShipDtlsTrdnm")
	private String shipDtlsTrdnm;
	
	@Column(name = "ShipDtlsAddr1")
	private String shipDtlsAddr1;
	
	@Column(name = "ShipDtlsAddr2")
	private String shipDtlsAddr2;
	
	@Column(name = "ShipDtlsLoc")
	private String shipDtlsLoc;
	
	@Column(name = "ShipDtlsPin")
	private String shipDtlsPin;
	
	@Column(name = "ShipDtlsStcd")
	private String shipDtlsStcd;
	
	@Column(name = "ItemListSlno")
	private String itemListSlno;
	
	@Column(name = "ItemListPrdDesc")
	private String itemListPrdDesc;
	
	@Column(name = "ItemListIsServc")
	private String itemListIsServc;
	
	@Column(name = "ItemListHsnCd")
	private String itemListHsnCd;
	
	@Column(name = "ItemListBarcde")
	private String itemListBarcde;
	
	@Column(name = "ItemListQty")
	private String itemListQty;
	
	@Column(name = "ItemListFreeQty")
	private String itemListFreeQty;
	
	@Column(name = "ItemListUnit")
	private String itemListUnit;
	
	@Column(name = "ItemListUnitPrice")
	private String itemListUnitPrice;
	
	@Column(name = "ItemListTotamt")
	private String itemListTotamt;
	
	@Column(name = "ItemListDiscount")
	private String itemListDiscount;
	
	@Column(name = "ItemListPreTaxVal")
	private String itemListPreTaxVal;
	
	@Column(name = "ItemListAssAmt")
	private String itemListAssAmt;
	
	@Column(name = "ItemListGstRt")
	private String itemListGstRt;
	
	@Column(name = "ItemListIgstAmt")
	private String itemListIgstAmt;
	
	@Column(name = "ItemListCgstAmt")
	private String itemListCgstAmt;
	
	@Column(name = "ItemListSgstAmt")
	private String itemListSgstAmt;
	
	@Column(name = "ItemListCesRt")
	private String itemListCesRt;
	
	@Column(name = "ItemListCesAmt")
	private String itemListCesAmt;
	
	@Column(name = "ItemListStateCesRt")
	private String itemListStateCesRt;
	
	@Column(name = "ItemListStateCesAmt")
	private String itemListStateCesAmt;
	
	@Column(name = "ItemListStateCesNonAdvlAmt")
	private String itemListStateCesNonAdvlAmt;
	
	@Column(name = "ItemListOthChrg")
	private String itemListOthChrg;
	
	@Column(name = "ItemListTotItemVal")
	private String itemListTotItemVal;
	
	@Column(name = "ItemListOrdLineRef")
	private String itemListOrdLineRef;
	
	@Column(name = "ItemListOrgCntry")
	private String itemListOrgCntry;
	
	@Column(name = "ItemListPrdSlNo")
	private String itemListPrdSlNo;
	
	@Column(name = "BchDtlsNm")
	private String bchDtlsNm;
	
	@Column(name = "BchDtlsExpdt")
	private String bchDtlsExpdt;
	
	@Column(name = "BchDtlsWrDt")
	private String bchDtlsWrDt;
	
	@Column(name = "AttribDtlsNm")
	private String attribDtlsNm;
	
	@Column(name = "AttribDtlsVal")
	private String attribDtlsVal;
	
	@Column(name = "ValDtlsAssVal")
	private String valDtlsAssVal;
	
	@Column(name = "ValDtlsCgstVal")
	private String valDtlsCgstVal;
	
	@Column(name = "ValDtlsSgstVal")
	private String valDtlsSgstVal;
	
	@Column(name = "ValDtlsIgstVal")
	private String valDtlsIgstVal;
	
	@Column(name = "ValDtlsCesVal")
	private String valDtlsCesVal;
	
	@Column(name = "ValDtlsStCesVal")
	private String valDtlsStCesVal;
	
	@Column(name = "ValDtlsDiscount")
	private String valDtlsDiscount;
	
	@Column(name = "ValDtlsOthChrg")
	private String valDtlsOthChrg;
	
	@Column(name = "ValDtlsRndOffAmt")
	private String valDtlsRndOffAmt;
	
	@Column(name = "ValDtlsTotInvVal")
	private String valDtlsTotInvVal;
	
	@Column(name = "ValDtlsTotInvValFc")
	private String valDtlsTotInvValFc;
	
	@Column(name = "PayDtlsNm")
	private String payDtlsNm;
	
	@Column(name = "PayDtlsAccdet")
	private String payDtlsAccdet;
	
	@Column(name = "PayDtlsMode")
	private String payDtlsMode;
	
	@Column(name = "PayDtlsFinInsBr")
	private String payDtlsFinInsBr;
	
	@Column(name = "PayDtlsPayTerm")
	private String payDtlsPayTerm;
	
	@Column(name = "PayDtlsInStr")
	private String payDtlsInStr;
	
	@Column(name = "PayDtlsCrTrn")
	private String payDtlsCrTrn;
	
	@Column(name = "PayDtlsDirDr")
	private String payDtlsDirDr;
	
	@Column(name = "PayDtlsCrDay")
	private String payDtlsCrDay;
	
	@Column(name = "PayDtlsPaidAmt")
	private String payDtlsPaidAmt;
	
	@Column(name = "PayDtlsPaymtDue")
	private String payDtlsPaymtDue;
	
	@Column(name = "RefDtlsInvRm")
	private String refDtlsInvRm;
	
	@Column(name = "DocPerdDtlsInvStdt")
	private String docPerdDtlsInvStdt;
	
	@Column(name = "DocPerdDtlsInvEndDt")
	private String docPerdDtlsInvEndDt;
	
	@Column(name = "PrecDocDtlsInvno")
	private String precDocDtlsInvno;
	
	@Column(name = "PrecDocDtlsInvDt")
	private String precDocDtlsInvDt;
	
	@Column(name = "PrecDocDtlsOthRefno")
	private String precDocDtlsOthRefno;
	
	@Column(name = "ContrDtlsRecAdvRefr")
	private String contrDtlsRecAdvRefr;
	
	@Column(name = "ContrDtlsRecAdvdt")
	private String contrDtlsRecAdvdt;
	
	@Column(name = "ContrDtlsTendRefr")
	private String contrDtlsTendRefr;
	
	@Column(name = "ContrDtlsContrRefr")
	private String contrDtlsContrRefr;
	
	@Column(name = "ContrDtlsExtRefr")
	private String contrDtlsExtRefr;
	
	@Column(name = "ContrDtlsProjRefr")
	private String contrDtlsProjRefr;
	
	@Column(name = "ContrDtlsPoRefr")
	private String contrDtlsPoRefr;
	
	@Column(name = "ContrDtlsPoRefdt")
	private String contrDtlsPoRefdt;
	
	@Column(name = "AddlDocDtlsUrl")
	private String addlDocDtlsUrl;
	
	@Column(name = "AddlDocDtlsDocs")
	private String addlDocDtlsDocs;
	
	@Column(name = "AddlDocDtlsInfo")
	private String addlDocDtlsInfo;
	
	@Column(name = "ExpDtlsShipBno")
	private String expDtlsShipBno;
	
	@Column(name = "ExpDtlsShipBdt")
	private String expDtlsShipBdt;
	
	@Column(name = "ExpDtlsPort")
	private String expDtlsPort;
	
	@Column(name = "ExpDtlsRefClm")
	private String expDtlsRefClm;
	
	@Column(name = "ExpDtlsForCur")
	private String expDtlsForCur;
	
	@Column(name = "ExpDtlsCntCode")
	private String expDtlsCntCode;
	
	@Column(name = "EwbDtlsTransId")
	private String ewbDtlsTransId;
	
	@Column(name = "EwbDtlsTransName")
	private String ewbDtlsTransName;
	
	@Column(name = "EwbDtlsDistance")
	private String ewbDtlsDistance;
	
	@Column(name = "EwbDtlsTransDocNo")
	private String ewbDtlsTransDocNo;
	
	@Column(name = "EwbDtlsTransDocDt")
	private String ewbDtlsTransDocDt;
	
	@Column(name = "EwbDtlsVehNo")
	private String ewbDtlsVehNo;
	
	@Column(name = "EwbDtlsVehType")
	private String ewbDtlsVehType;
	
	@Column(name = "TransMode")
	private String transMode;

	public Long getRow() {
		return Row;
	}

	public void setRow(Long row) {
		Row = row;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getSupTyp() {
		return supTyp;
	}

	public void setSupTyp(String supTyp) {
		this.supTyp = supTyp;
	}

	public String getRegRev() {
		return regRev;
	}

	public void setRegRev(String regRev) {
		this.regRev = regRev;
	}

	public String getEcmGstin() {
		return ecmGstin;
	}

	public void setEcmGstin(String ecmGstin) {
		this.ecmGstin = ecmGstin;
	}

	public String getIgstOnIntra() {
		return igstOnIntra;
	}

	public void setIgstOnIntra(String igstOnIntra) {
		this.igstOnIntra = igstOnIntra;
	}

	public String getDocDtls_TYP() {
		return docDtls_TYP;
	}

	public void setDocDtls_TYP(String docDtls_TYP) {
		this.docDtls_TYP = docDtls_TYP;
	}

	public String getDocDtls_NO() {
		return docDtls_NO;
	}

	public void setDocDtls_NO(String docDtls_NO) {
		this.docDtls_NO = docDtls_NO;
	}

	public String getDocDtls_DT() {
		return docDtls_DT;
	}

	public void setDocDtls_DT(String docDtls_DT) {
		this.docDtls_DT = docDtls_DT;
	}

	public String getSellerDtls_GSTIN() {
		return sellerDtls_GSTIN;
	}

	public void setSellerDtls_GSTIN(String sellerDtls_GSTIN) {
		this.sellerDtls_GSTIN = sellerDtls_GSTIN;
	}

	public String getSellerDtlsLglNm() {
		return sellerDtlsLglNm;
	}

	public void setSellerDtlsLglNm(String sellerDtlsLglNm) {
		this.sellerDtlsLglNm = sellerDtlsLglNm;
	}

	public String getSellerDtlsTrdNm() {
		return sellerDtlsTrdNm;
	}

	public void setSellerDtlsTrdNm(String sellerDtlsTrdNm) {
		this.sellerDtlsTrdNm = sellerDtlsTrdNm;
	}

	public String getSellerDtlsAddr1() {
		return sellerDtlsAddr1;
	}

	public void setSellerDtlsAddr1(String sellerDtlsAddr1) {
		this.sellerDtlsAddr1 = sellerDtlsAddr1;
	}

	public String getSellerDtlsAddr2() {
		return sellerDtlsAddr2;
	}

	public void setSellerDtlsAddr2(String sellerDtlsAddr2) {
		this.sellerDtlsAddr2 = sellerDtlsAddr2;
	}

	public String getSellerDtlsloc() {
		return sellerDtlsloc;
	}

	public void setSellerDtlsloc(String sellerDtlsloc) {
		this.sellerDtlsloc = sellerDtlsloc;
	}

	public String getSellerDtlsPin() {
		return sellerDtlsPin;
	}

	public void setSellerDtlsPin(String sellerDtlsPin) {
		this.sellerDtlsPin = sellerDtlsPin;
	}

	public String getSellerDtlsStcd() {
		return sellerDtlsStcd;
	}

	public void setSellerDtlsStcd(String sellerDtlsStcd) {
		this.sellerDtlsStcd = sellerDtlsStcd;
	}

	public String getSellerDtlsPh() {
		return sellerDtlsPh;
	}

	public void setSellerDtlsPh(String sellerDtlsPh) {
		this.sellerDtlsPh = sellerDtlsPh;
	}

	public String getSellerDtlsEm() {
		return sellerDtlsEm;
	}

	public void setSellerDtlsEm(String sellerDtlsEm) {
		this.sellerDtlsEm = sellerDtlsEm;
	}

	public String getBuyerDtlsGstin() {
		return buyerDtlsGstin;
	}

	public void setBuyerDtlsGstin(String buyerDtlsGstin) {
		this.buyerDtlsGstin = buyerDtlsGstin;
	}

	public String getBuyerDtlsLglNm() {
		return buyerDtlsLglNm;
	}

	public void setBuyerDtlsLglNm(String buyerDtlsLglNm) {
		this.buyerDtlsLglNm = buyerDtlsLglNm;
	}

	public String getBuyerDtlsTrdNm() {
		return buyerDtlsTrdNm;
	}

	public void setBuyerDtlsTrdNm(String buyerDtlsTrdNm) {
		this.buyerDtlsTrdNm = buyerDtlsTrdNm;
	}

	public String getBuyerDtlsPos() {
		return buyerDtlsPos;
	}

	public void setBuyerDtlsPos(String buyerDtlsPos) {
		this.buyerDtlsPos = buyerDtlsPos;
	}

	public String getBuyerDtlsAddr1() {
		return buyerDtlsAddr1;
	}

	public void setBuyerDtlsAddr1(String buyerDtlsAddr1) {
		this.buyerDtlsAddr1 = buyerDtlsAddr1;
	}

	public String getBuyerDtlsAddr2() {
		return buyerDtlsAddr2;
	}

	public void setBuyerDtlsAddr2(String buyerDtlsAddr2) {
		this.buyerDtlsAddr2 = buyerDtlsAddr2;
	}

	public String getBuyerDtlsLoc() {
		return buyerDtlsLoc;
	}

	public void setBuyerDtlsLoc(String buyerDtlsLoc) {
		this.buyerDtlsLoc = buyerDtlsLoc;
	}

	public String getBuyerDtlsPin() {
		return buyerDtlsPin;
	}

	public void setBuyerDtlsPin(String buyerDtlsPin) {
		this.buyerDtlsPin = buyerDtlsPin;
	}

	public String getBuyerDtlsStcd() {
		return buyerDtlsStcd;
	}

	public void setBuyerDtlsStcd(String buyerDtlsStcd) {
		this.buyerDtlsStcd = buyerDtlsStcd;
	}

	public String getBuyerDtlsPh() {
		return buyerDtlsPh;
	}

	public void setBuyerDtlsPh(String buyerDtlsPh) {
		this.buyerDtlsPh = buyerDtlsPh;
	}

	public String getBuyerDtlsem() {
		return buyerDtlsem;
	}

	public void setBuyerDtlsem(String buyerDtlsem) {
		this.buyerDtlsem = buyerDtlsem;
	}

	public String getDispDtlsNm() {
		return dispDtlsNm;
	}

	public void setDispDtlsNm(String dispDtlsNm) {
		this.dispDtlsNm = dispDtlsNm;
	}

	public String getDispDtlsAddr1() {
		return dispDtlsAddr1;
	}

	public void setDispDtlsAddr1(String dispDtlsAddr1) {
		this.dispDtlsAddr1 = dispDtlsAddr1;
	}

	public String getDispDtlsAddr2() {
		return dispDtlsAddr2;
	}

	public void setDispDtlsAddr2(String dispDtlsAddr2) {
		this.dispDtlsAddr2 = dispDtlsAddr2;
	}

	public String getDispDtlsLoc() {
		return dispDtlsLoc;
	}

	public void setDispDtlsLoc(String dispDtlsLoc) {
		this.dispDtlsLoc = dispDtlsLoc;
	}

	public String getDispDtlsPin() {
		return dispDtlsPin;
	}

	public void setDispDtlsPin(String dispDtlsPin) {
		this.dispDtlsPin = dispDtlsPin;
	}

	public String getDispDtlsStcd() {
		return dispDtlsStcd;
	}

	public void setDispDtlsStcd(String dispDtlsStcd) {
		this.dispDtlsStcd = dispDtlsStcd;
	}

	public String getShipDtlsGstin() {
		return shipDtlsGstin;
	}

	public void setShipDtlsGstin(String shipDtlsGstin) {
		this.shipDtlsGstin = shipDtlsGstin;
	}

	public String getShipDtlsLglnm() {
		return shipDtlsLglnm;
	}

	public void setShipDtlsLglnm(String shipDtlsLglnm) {
		this.shipDtlsLglnm = shipDtlsLglnm;
	}

	public String getShipDtlsTrdnm() {
		return shipDtlsTrdnm;
	}

	public void setShipDtlsTrdnm(String shipDtlsTrdnm) {
		this.shipDtlsTrdnm = shipDtlsTrdnm;
	}

	public String getShipDtlsAddr1() {
		return shipDtlsAddr1;
	}

	public void setShipDtlsAddr1(String shipDtlsAddr1) {
		this.shipDtlsAddr1 = shipDtlsAddr1;
	}

	public String getShipDtlsAddr2() {
		return shipDtlsAddr2;
	}

	public void setShipDtlsAddr2(String shipDtlsAddr2) {
		this.shipDtlsAddr2 = shipDtlsAddr2;
	}

	public String getShipDtlsLoc() {
		return shipDtlsLoc;
	}

	public void setShipDtlsLoc(String shipDtlsLoc) {
		this.shipDtlsLoc = shipDtlsLoc;
	}

	public String getShipDtlsPin() {
		return shipDtlsPin;
	}

	public void setShipDtlsPin(String shipDtlsPin) {
		this.shipDtlsPin = shipDtlsPin;
	}

	public String getShipDtlsStcd() {
		return shipDtlsStcd;
	}

	public void setShipDtlsStcd(String shipDtlsStcd) {
		this.shipDtlsStcd = shipDtlsStcd;
	}

	public String getItemListSlno() {
		return itemListSlno;
	}

	public void setItemListSlno(String itemListSlno) {
		this.itemListSlno = itemListSlno;
	}

	public String getItemListPrdDesc() {
		return itemListPrdDesc;
	}

	public void setItemListPrdDesc(String itemListPrdDesc) {
		this.itemListPrdDesc = itemListPrdDesc;
	}

	public String getItemListIsServc() {
		return itemListIsServc;
	}

	public void setItemListIsServc(String itemListIsServc) {
		this.itemListIsServc = itemListIsServc;
	}

	public String getItemListHsnCd() {
		return itemListHsnCd;
	}

	public void setItemListHsnCd(String itemListHsnCd) {
		this.itemListHsnCd = itemListHsnCd;
	}

	public String getItemListBarcde() {
		return itemListBarcde;
	}

	public void setItemListBarcde(String itemListBarcde) {
		this.itemListBarcde = itemListBarcde;
	}

	public String getItemListQty() {
		return itemListQty;
	}

	public void setItemListQty(String itemListQty) {
		this.itemListQty = itemListQty;
	}

	public String getItemListFreeQty() {
		return itemListFreeQty;
	}

	public void setItemListFreeQty(String itemListFreeQty) {
		this.itemListFreeQty = itemListFreeQty;
	}

	public String getItemListUnit() {
		return itemListUnit;
	}

	public void setItemListUnit(String itemListUnit) {
		this.itemListUnit = itemListUnit;
	}

	public String getItemListUnitPrice() {
		return itemListUnitPrice;
	}

	public void setItemListUnitPrice(String itemListUnitPrice) {
		this.itemListUnitPrice = itemListUnitPrice;
	}

	public String getItemListTotamt() {
		return itemListTotamt;
	}

	public void setItemListTotamt(String itemListTotamt) {
		this.itemListTotamt = itemListTotamt;
	}

	public String getItemListDiscount() {
		return itemListDiscount;
	}

	public void setItemListDiscount(String itemListDiscount) {
		this.itemListDiscount = itemListDiscount;
	}

	public String getItemListPreTaxVal() {
		return itemListPreTaxVal;
	}

	public void setItemListPreTaxVal(String itemListPreTaxVal) {
		this.itemListPreTaxVal = itemListPreTaxVal;
	}

	public String getItemListAssAmt() {
		return itemListAssAmt;
	}

	public void setItemListAssAmt(String itemListAssAmt) {
		this.itemListAssAmt = itemListAssAmt;
	}

	public String getItemListGstRt() {
		return itemListGstRt;
	}

	public void setItemListGstRt(String itemListGstRt) {
		this.itemListGstRt = itemListGstRt;
	}

	public String getItemListIgstAmt() {
		return itemListIgstAmt;
	}

	public void setItemListIgstAmt(String itemListIgstAmt) {
		this.itemListIgstAmt = itemListIgstAmt;
	}

	public String getItemListCgstAmt() {
		return itemListCgstAmt;
	}

	public void setItemListCgstAmt(String itemListCgstAmt) {
		this.itemListCgstAmt = itemListCgstAmt;
	}

	public String getItemListSgstAmt() {
		return itemListSgstAmt;
	}

	public void setItemListSgstAmt(String itemListSgstAmt) {
		this.itemListSgstAmt = itemListSgstAmt;
	}

	public String getItemListCesRt() {
		return itemListCesRt;
	}

	public void setItemListCesRt(String itemListCesRt) {
		this.itemListCesRt = itemListCesRt;
	}

	public String getItemListCesAmt() {
		return itemListCesAmt;
	}

	public void setItemListCesAmt(String itemListCesAmt) {
		this.itemListCesAmt = itemListCesAmt;
	}

	public String getItemListStateCesRt() {
		return itemListStateCesRt;
	}

	public void setItemListStateCesRt(String itemListStateCesRt) {
		this.itemListStateCesRt = itemListStateCesRt;
	}

	public String getItemListStateCesAmt() {
		return itemListStateCesAmt;
	}

	public void setItemListStateCesAmt(String itemListStateCesAmt) {
		this.itemListStateCesAmt = itemListStateCesAmt;
	}

	public String getItemListStateCesNonAdvlAmt() {
		return itemListStateCesNonAdvlAmt;
	}

	public void setItemListStateCesNonAdvlAmt(String itemListStateCesNonAdvlAmt) {
		this.itemListStateCesNonAdvlAmt = itemListStateCesNonAdvlAmt;
	}

	public String getItemListOthChrg() {
		return itemListOthChrg;
	}

	public void setItemListOthChrg(String itemListOthChrg) {
		this.itemListOthChrg = itemListOthChrg;
	}

	public String getItemListTotItemVal() {
		return itemListTotItemVal;
	}

	public void setItemListTotItemVal(String itemListTotItemVal) {
		this.itemListTotItemVal = itemListTotItemVal;
	}

	public String getItemListOrdLineRef() {
		return itemListOrdLineRef;
	}

	public void setItemListOrdLineRef(String itemListOrdLineRef) {
		this.itemListOrdLineRef = itemListOrdLineRef;
	}

	public String getItemListOrgCntry() {
		return itemListOrgCntry;
	}

	public void setItemListOrgCntry(String itemListOrgCntry) {
		this.itemListOrgCntry = itemListOrgCntry;
	}

	public String getItemListPrdSlNo() {
		return itemListPrdSlNo;
	}

	public void setItemListPrdSlNo(String itemListPrdSlNo) {
		this.itemListPrdSlNo = itemListPrdSlNo;
	}

	public String getBchDtlsNm() {
		return bchDtlsNm;
	}

	public void setBchDtlsNm(String bchDtlsNm) {
		this.bchDtlsNm = bchDtlsNm;
	}

	public String getBchDtlsExpdt() {
		return bchDtlsExpdt;
	}

	public void setBchDtlsExpdt(String bchDtlsExpdt) {
		this.bchDtlsExpdt = bchDtlsExpdt;
	}

	public String getBchDtlsWrDt() {
		return bchDtlsWrDt;
	}

	public void setBchDtlsWrDt(String bchDtlsWrDt) {
		this.bchDtlsWrDt = bchDtlsWrDt;
	}

	public String getAttribDtlsNm() {
		return attribDtlsNm;
	}

	public void setAttribDtlsNm(String attribDtlsNm) {
		this.attribDtlsNm = attribDtlsNm;
	}

	public String getAttribDtlsVal() {
		return attribDtlsVal;
	}

	public void setAttribDtlsVal(String attribDtlsVal) {
		this.attribDtlsVal = attribDtlsVal;
	}

	public String getValDtlsAssVal() {
		return valDtlsAssVal;
	}

	public void setValDtlsAssVal(String valDtlsAssVal) {
		this.valDtlsAssVal = valDtlsAssVal;
	}

	public String getValDtlsCgstVal() {
		return valDtlsCgstVal;
	}

	public void setValDtlsCgstVal(String valDtlsCgstVal) {
		this.valDtlsCgstVal = valDtlsCgstVal;
	}

	public String getValDtlsSgstVal() {
		return valDtlsSgstVal;
	}

	public void setValDtlsSgstVal(String valDtlsSgstVal) {
		this.valDtlsSgstVal = valDtlsSgstVal;
	}

	public String getValDtlsIgstVal() {
		return valDtlsIgstVal;
	}

	public void setValDtlsIgstVal(String valDtlsIgstVal) {
		this.valDtlsIgstVal = valDtlsIgstVal;
	}

	public String getValDtlsCesVal() {
		return valDtlsCesVal;
	}

	public void setValDtlsCesVal(String valDtlsCesVal) {
		this.valDtlsCesVal = valDtlsCesVal;
	}

	public String getValDtlsStCesVal() {
		return valDtlsStCesVal;
	}

	public void setValDtlsStCesVal(String valDtlsStCesVal) {
		this.valDtlsStCesVal = valDtlsStCesVal;
	}

	public String getValDtlsDiscount() {
		return valDtlsDiscount;
	}

	public void setValDtlsDiscount(String valDtlsDiscount) {
		this.valDtlsDiscount = valDtlsDiscount;
	}

	public String getValDtlsOthChrg() {
		return valDtlsOthChrg;
	}

	public void setValDtlsOthChrg(String valDtlsOthChrg) {
		this.valDtlsOthChrg = valDtlsOthChrg;
	}

	public String getValDtlsRndOffAmt() {
		return valDtlsRndOffAmt;
	}

	public void setValDtlsRndOffAmt(String valDtlsRndOffAmt) {
		this.valDtlsRndOffAmt = valDtlsRndOffAmt;
	}

	public String getValDtlsTotInvVal() {
		return valDtlsTotInvVal;
	}

	public void setValDtlsTotInvVal(String valDtlsTotInvVal) {
		this.valDtlsTotInvVal = valDtlsTotInvVal;
	}

	public String getValDtlsTotInvValFc() {
		return valDtlsTotInvValFc;
	}

	public void setValDtlsTotInvValFc(String valDtlsTotInvValFc) {
		this.valDtlsTotInvValFc = valDtlsTotInvValFc;
	}

	public String getPayDtlsNm() {
		return payDtlsNm;
	}

	public void setPayDtlsNm(String payDtlsNm) {
		this.payDtlsNm = payDtlsNm;
	}

	public String getPayDtlsAccdet() {
		return payDtlsAccdet;
	}

	public void setPayDtlsAccdet(String payDtlsAccdet) {
		this.payDtlsAccdet = payDtlsAccdet;
	}

	public String getPayDtlsMode() {
		return payDtlsMode;
	}

	public void setPayDtlsMode(String payDtlsMode) {
		this.payDtlsMode = payDtlsMode;
	}

	public String getPayDtlsFinInsBr() {
		return payDtlsFinInsBr;
	}

	public void setPayDtlsFinInsBr(String payDtlsFinInsBr) {
		this.payDtlsFinInsBr = payDtlsFinInsBr;
	}

	public String getPayDtlsPayTerm() {
		return payDtlsPayTerm;
	}

	public void setPayDtlsPayTerm(String payDtlsPayTerm) {
		this.payDtlsPayTerm = payDtlsPayTerm;
	}

	public String getPayDtlsInStr() {
		return payDtlsInStr;
	}

	public void setPayDtlsInStr(String payDtlsInStr) {
		this.payDtlsInStr = payDtlsInStr;
	}

	public String getPayDtlsCrTrn() {
		return payDtlsCrTrn;
	}

	public void setPayDtlsCrTrn(String payDtlsCrTrn) {
		this.payDtlsCrTrn = payDtlsCrTrn;
	}

	public String getPayDtlsDirDr() {
		return payDtlsDirDr;
	}

	public void setPayDtlsDirDr(String payDtlsDirDr) {
		this.payDtlsDirDr = payDtlsDirDr;
	}

	public String getPayDtlsCrDay() {
		return payDtlsCrDay;
	}

	public void setPayDtlsCrDay(String payDtlsCrDay) {
		this.payDtlsCrDay = payDtlsCrDay;
	}

	public String getPayDtlsPaidAmt() {
		return payDtlsPaidAmt;
	}

	public void setPayDtlsPaidAmt(String payDtlsPaidAmt) {
		this.payDtlsPaidAmt = payDtlsPaidAmt;
	}

	public String getPayDtlsPaymtDue() {
		return payDtlsPaymtDue;
	}

	public void setPayDtlsPaymtDue(String payDtlsPaymtDue) {
		this.payDtlsPaymtDue = payDtlsPaymtDue;
	}

	public String getRefDtlsInvRm() {
		return refDtlsInvRm;
	}

	public void setRefDtlsInvRm(String refDtlsInvRm) {
		this.refDtlsInvRm = refDtlsInvRm;
	}

	public String getDocPerdDtlsInvStdt() {
		return docPerdDtlsInvStdt;
	}

	public void setDocPerdDtlsInvStdt(String docPerdDtlsInvStdt) {
		this.docPerdDtlsInvStdt = docPerdDtlsInvStdt;
	}

	public String getDocPerdDtlsInvEndDt() {
		return docPerdDtlsInvEndDt;
	}

	public void setDocPerdDtlsInvEndDt(String docPerdDtlsInvEndDt) {
		this.docPerdDtlsInvEndDt = docPerdDtlsInvEndDt;
	}

	public String getPrecDocDtlsInvno() {
		return precDocDtlsInvno;
	}

	public void setPrecDocDtlsInvno(String precDocDtlsInvno) {
		this.precDocDtlsInvno = precDocDtlsInvno;
	}

	public String getPrecDocDtlsInvDt() {
		return precDocDtlsInvDt;
	}

	public void setPrecDocDtlsInvDt(String precDocDtlsInvDt) {
		this.precDocDtlsInvDt = precDocDtlsInvDt;
	}

	public String getPrecDocDtlsOthRefno() {
		return precDocDtlsOthRefno;
	}

	public void setPrecDocDtlsOthRefno(String precDocDtlsOthRefno) {
		this.precDocDtlsOthRefno = precDocDtlsOthRefno;
	}

	public String getContrDtlsRecAdvRefr() {
		return contrDtlsRecAdvRefr;
	}

	public void setContrDtlsRecAdvRefr(String contrDtlsRecAdvRefr) {
		this.contrDtlsRecAdvRefr = contrDtlsRecAdvRefr;
	}

	public String getContrDtlsRecAdvdt() {
		return contrDtlsRecAdvdt;
	}

	public void setContrDtlsRecAdvdt(String contrDtlsRecAdvdt) {
		this.contrDtlsRecAdvdt = contrDtlsRecAdvdt;
	}

	public String getContrDtlsTendRefr() {
		return contrDtlsTendRefr;
	}

	public void setContrDtlsTendRefr(String contrDtlsTendRefr) {
		this.contrDtlsTendRefr = contrDtlsTendRefr;
	}

	public String getContrDtlsContrRefr() {
		return contrDtlsContrRefr;
	}

	public void setContrDtlsContrRefr(String contrDtlsContrRefr) {
		this.contrDtlsContrRefr = contrDtlsContrRefr;
	}

	public String getContrDtlsExtRefr() {
		return contrDtlsExtRefr;
	}

	public void setContrDtlsExtRefr(String contrDtlsExtRefr) {
		this.contrDtlsExtRefr = contrDtlsExtRefr;
	}

	public String getContrDtlsProjRefr() {
		return contrDtlsProjRefr;
	}

	public void setContrDtlsProjRefr(String contrDtlsProjRefr) {
		this.contrDtlsProjRefr = contrDtlsProjRefr;
	}

	public String getContrDtlsPoRefr() {
		return contrDtlsPoRefr;
	}

	public void setContrDtlsPoRefr(String contrDtlsPoRefr) {
		this.contrDtlsPoRefr = contrDtlsPoRefr;
	}

	public String getContrDtlsPoRefdt() {
		return contrDtlsPoRefdt;
	}

	public void setContrDtlsPoRefdt(String contrDtlsPoRefdt) {
		this.contrDtlsPoRefdt = contrDtlsPoRefdt;
	}

	public String getAddlDocDtlsUrl() {
		return addlDocDtlsUrl;
	}

	public void setAddlDocDtlsUrl(String addlDocDtlsUrl) {
		this.addlDocDtlsUrl = addlDocDtlsUrl;
	}

	public String getAddlDocDtlsDocs() {
		return addlDocDtlsDocs;
	}

	public void setAddlDocDtlsDocs(String addlDocDtlsDocs) {
		this.addlDocDtlsDocs = addlDocDtlsDocs;
	}

	public String getAddlDocDtlsInfo() {
		return addlDocDtlsInfo;
	}

	public void setAddlDocDtlsInfo(String addlDocDtlsInfo) {
		this.addlDocDtlsInfo = addlDocDtlsInfo;
	}

	public String getExpDtlsShipBno() {
		return expDtlsShipBno;
	}

	public void setExpDtlsShipBno(String expDtlsShipBno) {
		this.expDtlsShipBno = expDtlsShipBno;
	}

	public String getExpDtlsShipBdt() {
		return expDtlsShipBdt;
	}

	public void setExpDtlsShipBdt(String expDtlsShipBdt) {
		this.expDtlsShipBdt = expDtlsShipBdt;
	}

	public String getExpDtlsPort() {
		return expDtlsPort;
	}

	public void setExpDtlsPort(String expDtlsPort) {
		this.expDtlsPort = expDtlsPort;
	}

	public String getExpDtlsRefClm() {
		return expDtlsRefClm;
	}

	public void setExpDtlsRefClm(String expDtlsRefClm) {
		this.expDtlsRefClm = expDtlsRefClm;
	}

	public String getExpDtlsForCur() {
		return expDtlsForCur;
	}

	public void setExpDtlsForCur(String expDtlsForCur) {
		this.expDtlsForCur = expDtlsForCur;
	}

	public String getExpDtlsCntCode() {
		return expDtlsCntCode;
	}

	public void setExpDtlsCntCode(String expDtlsCntCode) {
		this.expDtlsCntCode = expDtlsCntCode;
	}

	public String getEwbDtlsTransId() {
		return ewbDtlsTransId;
	}

	public void setEwbDtlsTransId(String ewbDtlsTransId) {
		this.ewbDtlsTransId = ewbDtlsTransId;
	}

	public String getEwbDtlsTransName() {
		return ewbDtlsTransName;
	}

	public void setEwbDtlsTransName(String ewbDtlsTransName) {
		this.ewbDtlsTransName = ewbDtlsTransName;
	}

	public String getEwbDtlsDistance() {
		return ewbDtlsDistance;
	}

	public void setEwbDtlsDistance(String ewbDtlsDistance) {
		this.ewbDtlsDistance = ewbDtlsDistance;
	}

	public String getEwbDtlsTransDocNo() {
		return ewbDtlsTransDocNo;
	}

	public void setEwbDtlsTransDocNo(String ewbDtlsTransDocNo) {
		this.ewbDtlsTransDocNo = ewbDtlsTransDocNo;
	}

	public String getEwbDtlsTransDocDt() {
		return ewbDtlsTransDocDt;
	}

	public void setEwbDtlsTransDocDt(String ewbDtlsTransDocDt) {
		this.ewbDtlsTransDocDt = ewbDtlsTransDocDt;
	}

	public String getEwbDtlsVehNo() {
		return ewbDtlsVehNo;
	}

	public void setEwbDtlsVehNo(String ewbDtlsVehNo) {
		this.ewbDtlsVehNo = ewbDtlsVehNo;
	}

	public String getEwbDtlsVehType() {
		return ewbDtlsVehType;
	}

	public void setEwbDtlsVehType(String ewbDtlsVehType) {
		this.ewbDtlsVehType = ewbDtlsVehType;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}


}