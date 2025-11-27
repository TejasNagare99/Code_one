package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;



//
//@Entity
//@Table(name = "E_invoice_report")
//@NamedNativeQueries({ @NamedNativeQuery(name = "calle_invoice_data_medley",
//query = "E_INVOICE_DATA_MEDLEY :div_id , :trf_start_dt , :trf_end_dt ",
//resultClass = E_invoice_report.class) })


@Entity
@Table(name = "E_invoice_report")
@NamedStoredProcedureQuery(name = "calle_invoice_data_medley", procedureName = "E_INVOICE_DATA_MEDLEY",
parameters= {
		@StoredProcedureParameter(name="div_id" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="trf_start_dt" , mode=ParameterMode.IN,type=String.class),
		@StoredProcedureParameter(name="trf_end_dt" , mode=ParameterMode.IN,type=String.class)
		},resultClasses=E_invoice_report.class) 

public class E_invoice_report implements Serializable {

private static final long serialVersionUID = -3367751314659898532L;

@Id
@Column(name="ROWNUM")
private Long rownum;

@Column(name="LocationGstin")
private String locationgstin;

@Column(name="LocationName")
private String locationname;

@Column(name="ReturnPeriod")
private String returnperiod;

@Column(name="LiabilityDischargeReturnPeriod")
private String liabilitydischargereturnperiod;

@Column(name="ItcClaimReturnPeriod")
private String itcclaimreturnperiod;

@Column(name="Purpose")
private String purpose;

@Column(name="AutoPushOrGenerate")
private String autopushorgenerate;

@Column(name="SupplyType")
private String supplytype;

@Column(name="Irn")
private String irn;

@Column(name="EwayBillNumber")
private String ewaybillnumber;

@Column(name="GeneratedDate")
private String generateddate;

@Column(name="DeliveredDate")
private String delivereddate;

@Column(name="ValidUpto")
private String validupto;

@Column(name="ExtendedTimes")
private String extendedtimes;

@Column(name="DocumentType")
private String documenttype;

@Column(name="TransactionType")
private String transactiontype;

@Column(name="TaxpayerType")
private String taxpayertype;

@Column(name="TransactionNature")
private String transactionnature;

@Column(name="TransactionTypeDescription")
private String transactiontypedescription;

@Column(name="DocumentNumber")
private String documentnumber;

@Column(name="DocumentSeriesCode")
private String documentseriescode;

@Column(name="DocumentDate")
private Date documentdate;

@Column(name="BillFromGstin")
private String billfromgstin;

@Column(name="BillFromLegalName")
private String billfromlegalname;

@Column(name="BillFromTradeName")
private String billfromtradename;

@Column(name="BillFromVendorCode")
private String billfromvendorcode;

@Column(name="BillFromAddress1")
private String billfromaddress1;

@Column(name="BillFromAddress2")
private String billfromaddress2;

@Column(name="BillFromCity")
private String billfromcity;

@Column(name="BillFromStateCode")
private String billfromstatecode;

@Column(name="BillFromPincode")
private String billfrompincode;

@Column(name="BillFromPhone")
private String billfromphone;

@Column(name="BillFromEmail")
private String billfromemail;

@Column(name="DispatchFromGstin")
private String dispatchfromgstin;

@Column(name="DispatchFromTradeName")
private String dispatchfromtradename;

@Column(name="DispatchFromVendorCode")
private String dispatchfromvendorcode;

@Column(name="DispatchFromAddress1")
private String dispatchfromaddress1;

@Column(name="DispatchFromAddress2")
private String dispatchfromaddress2;

@Column(name="DispatchFromCity")
private String dispatchfromcity;

@Column(name="DispatchFromStateCode")
private String dispatchfromstatecode;

@Column(name="DispatchFromPincode")
private String dispatchfrompincode;

@Column(name="BillToGstin")
private String billtogstin;

@Column(name="BillToLegalName")
private String billtolegalname;

@Column(name="BillToTradeName")
private String billtotradename;

@Column(name="BillToVendorCode")
private String billtovendorcode;

@Column(name="BillToAddress1")
private String billtoaddress1;

@Column(name="BillToAddress2")
private String billtoaddress2;

@Column(name="BillToCity")
private String billtocity;

@Column(name="BillToStateCode")
private String billtostatecode;

@Column(name="BillToPincode")
private String billtopincode;

@Column(name="BillToPhone")
private String billtophone;

@Column(name="BillToEmail")
private String billtoemail;

@Column(name="ShipToGstin")
private String shiptogstin;

@Column(name="ShipToLegalName")
private String shiptolegalname;

@Column(name="ShipToTradeName")
private String shiptotradename;

@Column(name="ShipToVendorCode")
private String shiptovendorcode;

@Column(name="ShipToAddress1")
private String shiptoaddress1;

@Column(name="ShipToAddress2")
private String shiptoaddress2;

@Column(name="ShipToCity")
private String shiptocity;

@Column(name="ShipToStateCode")
private String shiptostatecode;

@Column(name="ShipToPincode")
private String shiptopincode;

@Column(name="PaymentType")
private String paymenttype;

@Column(name="PaymentMode")
private String paymentmode;

@Column(name="PaymentAmount")
private String paymentamount;

@Column(name="AdvancePaidAmount")
private String advancepaidamount;

@Column(name="PaymentDate")
private String paymentdate;

@Column(name="PaymentRemarks")
private String paymentremarks;

@Column(name="PaymentTerms")
private String paymentterms;

@Column(name="PaymentInstruction")
private String paymentinstruction;

@Column(name="PayeeName")
private String payeename;

@Column(name="PayeeAccountNumber")
private String payeeaccountnumber;

@Column(name="PaymentAmountDue")
private String paymentamountdue;

@Column(name="Ifsc")
private String ifsc;

@Column(name="CreditTransfer")
private String credittransfer;

@Column(name="DirectDebit")
private String directdebit;

@Column(name="CreditDays")
private String creditdays;

@Column(name="CreditAvailedDate")
private String creditavaileddate;

@Column(name="CreditReversalDate")
private String creditreversaldate;

@Column(name="RefDocumentRemarks")
private String refdocumentremarks;

@Column(name="RefDocumentPeriodStartDate")
private String refdocumentperiodstartdate;

@Column(name="RefDocumentPeriodEndDate")
private String refdocumentperiodenddate;

@Column(name="RefPrecedingDocumentDetails")
private String refprecedingdocumentdetails;

@Column(name="RefContractDetails")
private String refcontractdetails;

@Column(name="AdditionalSupportingDocumentDetails")
private String additionalsupportingdocumentdetails;

@Column(name="BillNumber")
private String billnumber;

@Column(name="BillDate")
private String billdate;

@Column(name="PortCode")
private String portcode;

@Column(name="DocumentCurrencyCode")
private String documentcurrencycode;

@Column(name="DestinationCountry")
private String destinationcountry;

@Column(name="ExportDuty")
private String exportduty;

@Column(name="Pos")
private String pos;

@Column(name="DocumentValue")
private BigDecimal documentvalue;

@Column(name="DocumentDiscount")
private String documentdiscount;

@Column(name="DocumentOtherCharges")
private String documentothercharges;

@Column(name="DocumentValueInForeignCurrency")
private String documentvalueinforeigncurrency;
@Column(name="RoundOffAmount")
private String roundoffamount;
@Column(name="DifferentialPercentage")
private String differentialpercentage;

@Column(name="ReverseCharge")
private String reversecharge;
@Column(name="ClaimRefund")
private String claimrefund;
@Column(name="UnderIgstAct")
private String underigstact;
@Column(name="RefundEligibility")
private String refundeligibility;
@Column(name="ECommerceGstin")
private String ecommercegstin;
@Column(name="TdsGstin")
private String tdsgstin;
@Column(name="PnrOrUniqueNumber")
private String pnroruniquenumber;

@Column(name="AvailProvisionalItc")
private String availprovisionalitc;
@Column(name="OriginalGstin")
private String originalgstin;
@Column(name="OriginalStateCode")
private String originalstatecode;
@Column(name="OriginalTradeName")
private String originaltradename;
@Column(name="OriginalDocumentType")
private String originaldocumenttype;
@Column(name="OriginalDocumentNumber")
private String originaldocumentnumber;
@Column(name="OriginalDocumentDate")
private String originaldocumentdate;
@Column(name="OriginalReturnPeriod")
private String originalreturnperiod;


@Column(name="OriginalTaxableValue")
private String originaltaxablevalue;

@Column(name="OriginalPortCode")
private String originalportcode;
@Column(name="TransportDateTime")
private String transportdatetime;
@Column(name="TransporterId")
private String transporterid;
@Column(name="TransporterName")
private String transportername;
@Column(name="TransportMode")
private String transportmode;
@Column(name="Distance")
private String distance;
@Column(name="TransportDocumentNumber")
private String transportdocumentnumber;

@Column(name="TransportDocumentDate")
private String transportdocumentdate;

@Column(name="VehicleNumber")
private String vehiclenumber;
@Column(name="VehicleType")
private String vehicletype;
@Column(name="ToEmailAddresses")
private String toemailaddresses;
@Column(name="ToMobileNumbers")
private String tomobilenumbers;
@Column(name="JWOriginalDocumentNumber")
private String jworiginaldocumentnumber;
@Column(name="JWOriginalDocumentDate")
private String jworiginaldocumentdate;
@Column(name="JWDocumentNumber")
private String jwdocumentnumber;
@Column(name="JWDocumentDate")
private String jwdocumentdate;

@Column(name="Custom1")
private String custom1;
@Column(name="Custom2")
private String custom2;
@Column(name="Custom3")
private String custom3;
@Column(name="Custom4")
private String custom4;
@Column(name="Custom5")
private String custom5;
@Column(name="Custom6")
private String custom6;
@Column(name="Custom7")
private String custom7;
@Column(name="Custom8")
private String custom8;
@Column(name="Custom9")
private String custom9;
@Column(name="Custom10")
private String custom10;

@Column(name="SerialNumber")
private String serialnumber;
@Column(name="IsService")
private String isservice;
@Column(name="Hsn")
private String hsn;
@Column(name="ProductCode")
private String productcode;
@Column(name="ItemName")
private String itemname;
@Column(name="ItemDescription")
private String itemdescription;
@Column(name="NatureOfJWDone")
private String natureofjwdone;
@Column(name="Barcode")
private String barcode;
@Column(name="Uqc")
private String uqc;

@Column(name="Quantity")
private BigDecimal quantity;
@Column(name="FreeQuantity")
private String freequantity;
@Column(name="LossUnitOfMeasure")
private String lossunitofmeasure;
@Column(name="LossTotalQuantity")
private String losstotalquantity;
@Column(name="Rate")
private BigDecimal rate;
@Column(name="CessRate")
private String cessrate;
@Column(name="StateCessRate")
private String statecessrate;
@Column(name="CessNonAdvaloremRate")
private String cessnonadvaloremrate;
@Column(name="PricePerQuantity")
private BigDecimal priceperquantity;
@Column(name="DiscountAmount")
private String discountamount;
@Column(name="GrossAmount")
private BigDecimal grossamount;
@Column(name="OtherCharges")
private String othercharges;
@Column(name="TaxableValue")
private BigDecimal taxablevalue;
@Column(name="PreTaxValue")
private String pretaxvalue;
@Column(name="IgstAmount")
private BigDecimal igstamount;
@Column(name="CgstAmount")
private String cgstamount;

@Column(name="SgstAmount")
private String sgstamount;
@Column(name="CessAmount")
private String cessamount;
@Column(name="StateCessAmount")
private String statecessamount;
@Column(name="StateCessNonAdvaloremAmount")
private String statecessnonadvaloremamount;
@Column(name="CessNonAdvaloremAmount")
private String cessnonadvaloremamount;
@Column(name="OrderLineReference")
private String orderlinereference;
@Column(name="OriginCountry")
private String origincountry;
@Column(name="ItemSerialNumber")
private String itemserialnumber;
@Column(name="ItemTotal")
private BigDecimal itemtotal;
@Column(name="ItemAttributeDetails")
private String itemattributedetails;
@Column(name="TaxType")
private String taxtype;
@Column(name="BatchNameNumber")
private String batchnamenumber;

@Column(name="BatchExpiryDate")
private String batchexpirydate;
@Column(name="WarrantyDate")
private String warrantydate;
@Column(name="ItcEligibility")
private String itceligibility;
@Column(name="ItcIgstAmount")
private String itcigstamount;
@Column(name="ItcCgstAmount")
private String itccgstamount;
@Column(name="ItcSgstAmount")
private String itcsgstamount;
@Column(name="ItcCessAmount")
private String itccessamount;

@Column(name="CustomItem1")
private String customitem1;
@Column(name="CustomItem2")
private String customitem2;
@Column(name="CustomItem3")
private String customitem3;
@Column(name="CustomItem4")
private String customitem4;
@Column(name="CustomItem5")
private String customitem5;
@Column(name="CustomItem6")
private String customitem6;
@Column(name="CustomItem7")
private String customitem7;
@Column(name="CustomItem8")
private String customitem8;
@Column(name="CustomItem9")
private String customitem9;
@Column(name="CustomItem10")
private String customitem10;

public Long getRownum() {
return rownum;
}
public void setRownum(Long rownum) {
this.rownum = rownum;
}
public String getLocationgstin() {
return locationgstin;
}
public void setLocationgstin(String locationgstin) {
this.locationgstin = locationgstin;
}
public String getLocationname() {
return locationname;
}
public void setLocationname(String locationname) {
this.locationname = locationname;
}
public String getReturnperiod() {
return returnperiod;
}
public void setReturnperiod(String returnperiod) {
this.returnperiod = returnperiod;
}
public String getLiabilitydischargereturnperiod() {
return liabilitydischargereturnperiod;
}
public void setLiabilitydischargereturnperiod(String liabilitydischargereturnperiod) {
this.liabilitydischargereturnperiod = liabilitydischargereturnperiod;
}
public String getItcclaimreturnperiod() {
return itcclaimreturnperiod;
}
public void setItcclaimreturnperiod(String itcclaimreturnperiod) {
this.itcclaimreturnperiod = itcclaimreturnperiod;
}
public String getPurpose() {
return purpose;
}
public void setPurpose(String purpose) {
this.purpose = purpose;
}
public String getAutopushorgenerate() {
return autopushorgenerate;
}
public void setAutopushorgenerate(String autopushorgenerate) {
this.autopushorgenerate = autopushorgenerate;
}
public String getSupplytype() {
return supplytype;
}
public void setSupplytype(String supplytype) {
this.supplytype = supplytype;
}
public String getIrn() {
return irn;
}
public void setIrn(String irn) {
this.irn = irn;
}
public String getEwaybillnumber() {
return ewaybillnumber;
}
public void setEwaybillnumber(String ewaybillnumber) {
this.ewaybillnumber = ewaybillnumber;
}
public String getGenerateddate() {
return generateddate;
}
public void setGenerateddate(String generateddate) {
this.generateddate = generateddate;
}
public String getDelivereddate() {
return delivereddate;
}
public void setDelivereddate(String delivereddate) {
this.delivereddate = delivereddate;
}
public String getValidupto() {
return validupto;
}
public void setValidupto(String validupto) {
this.validupto = validupto;
}
public String getExtendedtimes() {
return extendedtimes;
}
public void setExtendedtimes(String extendedtimes) {
this.extendedtimes = extendedtimes;
}
public String getDocumenttype() {
return documenttype;
}
public void setDocumenttype(String documenttype) {
this.documenttype = documenttype;
}
public String getTransactiontype() {
return transactiontype;
}
public void setTransactiontype(String transactiontype) {
this.transactiontype = transactiontype;
}
public String getTaxpayertype() {
return taxpayertype;
}
public void setTaxpayertype(String taxpayertype) {
this.taxpayertype = taxpayertype;
}
public String getTransactionnature() {
return transactionnature;
}
public void setTransactionnature(String transactionnature) {
this.transactionnature = transactionnature;
}
public String getTransactiontypedescription() {
return transactiontypedescription;
}
public void setTransactiontypedescription(String transactiontypedescription) {
this.transactiontypedescription = transactiontypedescription;
}
public String getDocumentnumber() {
return documentnumber;
}
public void setDocumentnumber(String documentnumber) {
this.documentnumber = documentnumber;
}
public String getDocumentseriescode() {
return documentseriescode;
}
public void setDocumentseriescode(String documentseriescode) {
this.documentseriescode = documentseriescode;
}
public Date getDocumentdate() {
return documentdate;
}
public void setDocumentdate(Date documentdate) {
this.documentdate = documentdate;
}
public String getBillfromgstin() {
return billfromgstin;
}
public void setBillfromgstin(String billfromgstin) {
this.billfromgstin = billfromgstin;
}
public String getBillfromlegalname() {
return billfromlegalname;
}
public void setBillfromlegalname(String billfromlegalname) {
this.billfromlegalname = billfromlegalname;
}
public String getBillfromtradename() {
return billfromtradename;
}
public void setBillfromtradename(String billfromtradename) {
this.billfromtradename = billfromtradename;
}
public String getBillfromvendorcode() {
return billfromvendorcode;
}
public void setBillfromvendorcode(String billfromvendorcode) {
this.billfromvendorcode = billfromvendorcode;
}
public String getBillfromaddress1() {
return billfromaddress1;
}
public void setBillfromaddress1(String billfromaddress1) {
this.billfromaddress1 = billfromaddress1;
}
public String getBillfromaddress2() {
return billfromaddress2;
}
public void setBillfromaddress2(String billfromaddress2) {
this.billfromaddress2 = billfromaddress2;
}
public String getBillfromcity() {
return billfromcity;
}
public void setBillfromcity(String billfromcity) {
this.billfromcity = billfromcity;
}
public String getBillfromstatecode() {
return billfromstatecode;
}
public void setBillfromstatecode(String billfromstatecode) {
this.billfromstatecode = billfromstatecode;
}
public String getBillfrompincode() {
return billfrompincode;
}
public void setBillfrompincode(String billfrompincode) {
this.billfrompincode = billfrompincode;
}
public String getBillfromphone() {
return billfromphone;
}
public void setBillfromphone(String billfromphone) {
this.billfromphone = billfromphone;
}
public String getBillfromemail() {
return billfromemail;
}
public void setBillfromemail(String billfromemail) {
this.billfromemail = billfromemail;
}
public String getDispatchfromgstin() {
return dispatchfromgstin;
}
public void setDispatchfromgstin(String dispatchfromgstin) {
this.dispatchfromgstin = dispatchfromgstin;
}
public String getDispatchfromtradename() {
return dispatchfromtradename;
}
public void setDispatchfromtradename(String dispatchfromtradename) {
this.dispatchfromtradename = dispatchfromtradename;
}
public String getDispatchfromvendorcode() {
return dispatchfromvendorcode;
}
public void setDispatchfromvendorcode(String dispatchfromvendorcode) {
this.dispatchfromvendorcode = dispatchfromvendorcode;
}
public String getDispatchfromaddress1() {
return dispatchfromaddress1;
}
public void setDispatchfromaddress1(String dispatchfromaddress1) {
this.dispatchfromaddress1 = dispatchfromaddress1;
}
public String getDispatchfromaddress2() {
return dispatchfromaddress2;
}
public void setDispatchfromaddress2(String dispatchfromaddress2) {
this.dispatchfromaddress2 = dispatchfromaddress2;
}
public String getDispatchfromcity() {
return dispatchfromcity;
}
public void setDispatchfromcity(String dispatchfromcity) {
this.dispatchfromcity = dispatchfromcity;
}
public String getDispatchfromstatecode() {
return dispatchfromstatecode;
}
public void setDispatchfromstatecode(String dispatchfromstatecode) {
this.dispatchfromstatecode = dispatchfromstatecode;
}
public String getDispatchfrompincode() {
return dispatchfrompincode;
}
public void setDispatchfrompincode(String dispatchfrompincode) {
this.dispatchfrompincode = dispatchfrompincode;
}
public String getBilltogstin() {
return billtogstin;
}
public void setBilltogstin(String billtogstin) {
this.billtogstin = billtogstin;
}
public String getBilltolegalname() {
return billtolegalname;
}
public void setBilltolegalname(String billtolegalname) {
this.billtolegalname = billtolegalname;
}
public String getBilltotradename() {
return billtotradename;
}
public void setBilltotradename(String billtotradename) {
this.billtotradename = billtotradename;
}
public String getBilltovendorcode() {
return billtovendorcode;
}
public void setBilltovendorcode(String billtovendorcode) {
this.billtovendorcode = billtovendorcode;
}
public String getBilltoaddress1() {
return billtoaddress1;
}
public void setBilltoaddress1(String billtoaddress1) {
this.billtoaddress1 = billtoaddress1;
}
public String getBilltoaddress2() {
return billtoaddress2;
}
public void setBilltoaddress2(String billtoaddress2) {
this.billtoaddress2 = billtoaddress2;
}
public String getBilltocity() {
return billtocity;
}
public void setBilltocity(String billtocity) {
this.billtocity = billtocity;
}
public String getBilltostatecode() {
return billtostatecode;
}
public void setBilltostatecode(String billtostatecode) {
this.billtostatecode = billtostatecode;
}
public String getBilltopincode() {
return billtopincode;
}
public void setBilltopincode(String billtopincode) {
this.billtopincode = billtopincode;
}
public String getBilltophone() {
return billtophone;
}
public void setBilltophone(String billtophone) {
this.billtophone = billtophone;
}
public String getBilltoemail() {
return billtoemail;
}
public void setBilltoemail(String billtoemail) {
this.billtoemail = billtoemail;
}
public String getShiptogstin() {
return shiptogstin;
}
public void setShiptogstin(String shiptogstin) {
this.shiptogstin = shiptogstin;
}
public String getShiptolegalname() {
return shiptolegalname;
}
public void setShiptolegalname(String shiptolegalname) {
this.shiptolegalname = shiptolegalname;
}
public String getShiptotradename() {
return shiptotradename;
}
public void setShiptotradename(String shiptotradename) {
this.shiptotradename = shiptotradename;
}
public String getShiptovendorcode() {
return shiptovendorcode;
}
public void setShiptovendorcode(String shiptovendorcode) {
this.shiptovendorcode = shiptovendorcode;
}
public String getShiptoaddress1() {
return shiptoaddress1;
}
public void setShiptoaddress1(String shiptoaddress1) {
this.shiptoaddress1 = shiptoaddress1;
}
public String getShiptoaddress2() {
return shiptoaddress2;
}
public void setShiptoaddress2(String shiptoaddress2) {
this.shiptoaddress2 = shiptoaddress2;
}
public String getShiptocity() {
return shiptocity;
}
public void setShiptocity(String shiptocity) {
this.shiptocity = shiptocity;
}
public String getShiptostatecode() {
return shiptostatecode;
}
public void setShiptostatecode(String shiptostatecode) {
this.shiptostatecode = shiptostatecode;
}
public String getShiptopincode() {
return shiptopincode;
}
public void setShiptopincode(String shiptopincode) {
this.shiptopincode = shiptopincode;
}
public String getPaymenttype() {
return paymenttype;
}
public void setPaymenttype(String paymenttype) {
this.paymenttype = paymenttype;
}
public String getPaymentmode() {
return paymentmode;
}
public void setPaymentmode(String paymentmode) {
this.paymentmode = paymentmode;
}
public String getPaymentamount() {
return paymentamount;
}
public void setPaymentamount(String paymentamount) {
this.paymentamount = paymentamount;
}
public String getAdvancepaidamount() {
return advancepaidamount;
}
public void setAdvancepaidamount(String advancepaidamount) {
this.advancepaidamount = advancepaidamount;
}
public String getPaymentdate() {
return paymentdate;
}
public void setPaymentdate(String paymentdate) {
this.paymentdate = paymentdate;
}
public String getPaymentremarks() {
return paymentremarks;
}
public void setPaymentremarks(String paymentremarks) {
this.paymentremarks = paymentremarks;
}
public String getPaymentterms() {
return paymentterms;
}
public void setPaymentterms(String paymentterms) {
this.paymentterms = paymentterms;
}
public String getPaymentinstruction() {
return paymentinstruction;
}
public void setPaymentinstruction(String paymentinstruction) {
this.paymentinstruction = paymentinstruction;
}
public String getPayeename() {
return payeename;
}
public void setPayeename(String payeename) {
this.payeename = payeename;
}
public String getPayeeaccountnumber() {
return payeeaccountnumber;
}
public void setPayeeaccountnumber(String payeeaccountnumber) {
this.payeeaccountnumber = payeeaccountnumber;
}
public String getPaymentamountdue() {
return paymentamountdue;
}
public void setPaymentamountdue(String paymentamountdue) {
this.paymentamountdue = paymentamountdue;
}
public String getIfsc() {
return ifsc;
}
public void setIfsc(String ifsc) {
this.ifsc = ifsc;
}
public String getCredittransfer() {
return credittransfer;
}
public void setCredittransfer(String credittransfer) {
this.credittransfer = credittransfer;
}
public String getDirectdebit() {
return directdebit;
}
public void setDirectdebit(String directdebit) {
this.directdebit = directdebit;
}
public String getCreditdays() {
return creditdays;
}
public void setCreditdays(String creditdays) {
this.creditdays = creditdays;
}
public String getCreditavaileddate() {
return creditavaileddate;
}
public void setCreditavaileddate(String creditavaileddate) {
this.creditavaileddate = creditavaileddate;
}
public String getCreditreversaldate() {
return creditreversaldate;
}
public void setCreditreversaldate(String creditreversaldate) {
this.creditreversaldate = creditreversaldate;
}
public String getRefdocumentremarks() {
return refdocumentremarks;
}
public void setRefdocumentremarks(String refdocumentremarks) {
this.refdocumentremarks = refdocumentremarks;
}
public String getRefdocumentperiodstartdate() {
return refdocumentperiodstartdate;
}
public void setRefdocumentperiodstartdate(String refdocumentperiodstartdate) {
this.refdocumentperiodstartdate = refdocumentperiodstartdate;
}
public String getRefdocumentperiodenddate() {
return refdocumentperiodenddate;
}
public void setRefdocumentperiodenddate(String refdocumentperiodenddate) {
this.refdocumentperiodenddate = refdocumentperiodenddate;
}
public String getRefprecedingdocumentdetails() {
return refprecedingdocumentdetails;
}
public void setRefprecedingdocumentdetails(String refprecedingdocumentdetails) {
this.refprecedingdocumentdetails = refprecedingdocumentdetails;
}
public String getRefcontractdetails() {
return refcontractdetails;
}
public void setRefcontractdetails(String refcontractdetails) {
this.refcontractdetails = refcontractdetails;
}
public String getAdditionalsupportingdocumentdetails() {
return additionalsupportingdocumentdetails;
}
public void setAdditionalsupportingdocumentdetails(String additionalsupportingdocumentdetails) {
this.additionalsupportingdocumentdetails = additionalsupportingdocumentdetails;
}
public String getBillnumber() {
return billnumber;
}
public void setBillnumber(String billnumber) {
this.billnumber = billnumber;
}
public String getBilldate() {
return billdate;
}
public void setBilldate(String billdate) {
this.billdate = billdate;
}
public String getPortcode() {
return portcode;
}
public void setPortcode(String portcode) {
this.portcode = portcode;
}
public String getDocumentcurrencycode() {
return documentcurrencycode;
}
public void setDocumentcurrencycode(String documentcurrencycode) {
this.documentcurrencycode = documentcurrencycode;
}
public String getDestinationcountry() {
return destinationcountry;
}
public void setDestinationcountry(String destinationcountry) {
this.destinationcountry = destinationcountry;
}
public String getExportduty() {
return exportduty;
}
public void setExportduty(String exportduty) {
this.exportduty = exportduty;
}
public String getPos() {
return pos;
}
public void setPos(String pos) {
this.pos = pos;
}


public BigDecimal getDocumentvalue() {
return documentvalue;
}
public void setDocumentvalue(BigDecimal documentvalue) {
this.documentvalue = documentvalue;
}
public String getDocumentdiscount() {
return documentdiscount;
}
public void setDocumentdiscount(String documentdiscount) {
this.documentdiscount = documentdiscount;
}
public String getDocumentothercharges() {
return documentothercharges;
}
public void setDocumentothercharges(String documentothercharges) {
this.documentothercharges = documentothercharges;
}
public String getDocumentvalueinforeigncurrency() {
return documentvalueinforeigncurrency;
}
public void setDocumentvalueinforeigncurrency(String documentvalueinforeigncurrency) {
this.documentvalueinforeigncurrency = documentvalueinforeigncurrency;
}
public String getRoundoffamount() {
return roundoffamount;
}
public void setRoundoffamount(String roundoffamount) {
this.roundoffamount = roundoffamount;
}
public String getDifferentialpercentage() {
return differentialpercentage;
}
public void setDifferentialpercentage(String differentialpercentage) {
this.differentialpercentage = differentialpercentage;
}
public String getReversecharge() {
return reversecharge;
}
public void setReversecharge(String reversecharge) {
this.reversecharge = reversecharge;
}
public String getClaimrefund() {
return claimrefund;
}
public void setClaimrefund(String claimrefund) {
this.claimrefund = claimrefund;
}
public String getUnderigstact() {
return underigstact;
}
public void setUnderigstact(String underigstact) {
this.underigstact = underigstact;
}
public String getRefundeligibility() {
return refundeligibility;
}
public void setRefundeligibility(String refundeligibility) {
this.refundeligibility = refundeligibility;
}
public String getEcommercegstin() {
return ecommercegstin;
}
public void setEcommercegstin(String ecommercegstin) {
this.ecommercegstin = ecommercegstin;
}
public String getTdsgstin() {
return tdsgstin;
}
public void setTdsgstin(String tdsgstin) {
this.tdsgstin = tdsgstin;
}
public String getPnroruniquenumber() {
return pnroruniquenumber;
}
public void setPnroruniquenumber(String pnroruniquenumber) {
this.pnroruniquenumber = pnroruniquenumber;
}
public String getAvailprovisionalitc() {
return availprovisionalitc;
}
public void setAvailprovisionalitc(String availprovisionalitc) {
this.availprovisionalitc = availprovisionalitc;
}
public String getOriginalgstin() {
return originalgstin;
}
public void setOriginalgstin(String originalgstin) {
this.originalgstin = originalgstin;
}
public String getOriginalstatecode() {
return originalstatecode;
}
public void setOriginalstatecode(String originalstatecode) {
this.originalstatecode = originalstatecode;
}
public String getOriginaltradename() {
return originaltradename;
}
public void setOriginaltradename(String originaltradename) {
this.originaltradename = originaltradename;
}
public String getOriginaldocumenttype() {
return originaldocumenttype;
}
public void setOriginaldocumenttype(String originaldocumenttype) {
this.originaldocumenttype = originaldocumenttype;
}
public String getOriginaldocumentnumber() {
return originaldocumentnumber;
}
public void setOriginaldocumentnumber(String originaldocumentnumber) {
this.originaldocumentnumber = originaldocumentnumber;
}
public String getOriginaldocumentdate() {
return originaldocumentdate;
}
public void setOriginaldocumentdate(String originaldocumentdate) {
this.originaldocumentdate = originaldocumentdate;
}
public String getOriginalreturnperiod() {
return originalreturnperiod;
}
public void setOriginalreturnperiod(String originalreturnperiod) {
this.originalreturnperiod = originalreturnperiod;
}
public String getOriginaltaxablevalue() {
return originaltaxablevalue;
}
public void setOriginaltaxablevalue(String originaltaxablevalue) {
this.originaltaxablevalue = originaltaxablevalue;
}
public String getOriginalportcode() {
return originalportcode;
}
public void setOriginalportcode(String originalportcode) {
this.originalportcode = originalportcode;
}
public String getTransportdatetime() {
return transportdatetime;
}
public void setTransportdatetime(String transportdatetime) {
this.transportdatetime = transportdatetime;
}
public String getTransporterid() {
return transporterid;
}
public void setTransporterid(String transporterid) {
this.transporterid = transporterid;
}
public String getTransportername() {
return transportername;
}
public void setTransportername(String transportername) {
this.transportername = transportername;
}
public String getTransportmode() {
return transportmode;
}
public void setTransportmode(String transportmode) {
this.transportmode = transportmode;
}
public String getDistance() {
return distance;
}
public void setDistance(String distance) {
this.distance = distance;
}
public String getTransportdocumentnumber() {
return transportdocumentnumber;
}
public void setTransportdocumentnumber(String transportdocumentnumber) {
this.transportdocumentnumber = transportdocumentnumber;
}
public String getTransportdocumentdate() {
return transportdocumentdate;
}
public void setTransportdocumentdate(String transportdocumentdate) {
this.transportdocumentdate = transportdocumentdate;
}
public String getVehiclenumber() {
return vehiclenumber;
}
public void setVehiclenumber(String vehiclenumber) {
this.vehiclenumber = vehiclenumber;
}
public String getVehicletype() {
return vehicletype;
}
public void setVehicletype(String vehicletype) {
this.vehicletype = vehicletype;
}
public String getToemailaddresses() {
return toemailaddresses;
}
public void setToemailaddresses(String toemailaddresses) {
this.toemailaddresses = toemailaddresses;
}
public String getTomobilenumbers() {
return tomobilenumbers;
}
public void setTomobilenumbers(String tomobilenumbers) {
this.tomobilenumbers = tomobilenumbers;
}
public String getJworiginaldocumentnumber() {
return jworiginaldocumentnumber;
}
public void setJworiginaldocumentnumber(String jworiginaldocumentnumber) {
this.jworiginaldocumentnumber = jworiginaldocumentnumber;
}
public String getJworiginaldocumentdate() {
return jworiginaldocumentdate;
}
public void setJworiginaldocumentdate(String jworiginaldocumentdate) {
this.jworiginaldocumentdate = jworiginaldocumentdate;
}
public String getJwdocumentnumber() {
return jwdocumentnumber;
}
public void setJwdocumentnumber(String jwdocumentnumber) {
this.jwdocumentnumber = jwdocumentnumber;
}
public String getJwdocumentdate() {
return jwdocumentdate;
}
public void setJwdocumentdate(String jwdocumentdate) {
this.jwdocumentdate = jwdocumentdate;
}
public String getCustom1() {
return custom1;
}
public void setCustom1(String custom1) {
this.custom1 = custom1;
}
public String getCustom2() {
return custom2;
}
public void setCustom2(String custom2) {
this.custom2 = custom2;
}
public String getCustom3() {
return custom3;
}
public void setCustom3(String custom3) {
this.custom3 = custom3;
}
public String getCustom4() {
return custom4;
}
public void setCustom4(String custom4) {
this.custom4 = custom4;
}
public String getCustom5() {
return custom5;
}
public void setCustom5(String custom5) {
this.custom5 = custom5;
}
public String getCustom6() {
return custom6;
}
public void setCustom6(String custom6) {
this.custom6 = custom6;
}
public String getCustom7() {
return custom7;
}
public void setCustom7(String custom7) {
this.custom7 = custom7;
}
public String getCustom8() {
return custom8;
}
public void setCustom8(String custom8) {
this.custom8 = custom8;
}
public String getCustom9() {
return custom9;
}
public void setCustom9(String custom9) {
this.custom9 = custom9;
}
public String getCustom10() {
return custom10;
}
public void setCustom10(String custom10) {
this.custom10 = custom10;
}
public String getSerialnumber() {
return serialnumber;
}
public void setSerialnumber(String serialnumber) {
this.serialnumber = serialnumber;
}
public String getIsservice() {
return isservice;
}
public void setIsservice(String isservice) {
this.isservice = isservice;
}
public String getHsn() {
return hsn;
}
public void setHsn(String hsn) {
this.hsn = hsn;
}
public String getProductcode() {
return productcode;
}
public void setProductcode(String productcode) {
this.productcode = productcode;
}
public String getItemname() {
return itemname;
}
public void setItemname(String itemname) {
this.itemname = itemname;
}
public String getItemdescription() {
return itemdescription;
}
public void setItemdescription(String itemdescription) {
this.itemdescription = itemdescription;
}
public String getNatureofjwdone() {
return natureofjwdone;
}
public void setNatureofjwdone(String natureofjwdone) {
this.natureofjwdone = natureofjwdone;
}
public String getBarcode() {
return barcode;
}
public void setBarcode(String barcode) {
this.barcode = barcode;
}
public String getUqc() {
return uqc;
}
public void setUqc(String uqc) {
this.uqc = uqc;
}
public BigDecimal getQuantity() {
return quantity;
}
public void setQuantity(BigDecimal quantity) {
this.quantity = quantity;
}
public String getFreequantity() {
return freequantity;
}
public void setFreequantity(String freequantity) {
this.freequantity = freequantity;
}
public String getLossunitofmeasure() {
return lossunitofmeasure;
}
public void setLossunitofmeasure(String lossunitofmeasure) {
this.lossunitofmeasure = lossunitofmeasure;
}
public String getLosstotalquantity() {
return losstotalquantity;
}
public void setLosstotalquantity(String losstotalquantity) {
this.losstotalquantity = losstotalquantity;
}
public BigDecimal getRate() {
return rate;
}
public void setRate(BigDecimal rate) {
this.rate = rate;
}
public String getCessrate() {
return cessrate;
}
public void setCessrate(String cessrate) {
this.cessrate = cessrate;
}
public String getStatecessrate() {
return statecessrate;
}
public void setStatecessrate(String statecessrate) {
this.statecessrate = statecessrate;
}
public String getCessnonadvaloremrate() {
return cessnonadvaloremrate;
}
public void setCessnonadvaloremrate(String cessnonadvaloremrate) {
this.cessnonadvaloremrate = cessnonadvaloremrate;
}
public BigDecimal getPriceperquantity() {
return priceperquantity;
}
public void setPriceperquantity(BigDecimal priceperquantity) {
this.priceperquantity = priceperquantity;
}
public String getDiscountamount() {
return discountamount;
}
public void setDiscountamount(String discountamount) {
this.discountamount = discountamount;
}
public BigDecimal getGrossamount() {
return grossamount;
}
public void setGrossamount(BigDecimal grossamount) {
this.grossamount = grossamount;
}
public String getOthercharges() {
return othercharges;
}
public void setOthercharges(String othercharges) {
this.othercharges = othercharges;
}
public BigDecimal getTaxablevalue() {
return taxablevalue;
}
public void setTaxablevalue(BigDecimal taxablevalue) {
this.taxablevalue = taxablevalue;
}
public String getPretaxvalue() {
return pretaxvalue;
}
public void setPretaxvalue(String pretaxvalue) {
this.pretaxvalue = pretaxvalue;
}
public BigDecimal getIgstamount() {
return igstamount;
}
public void setIgstamount(BigDecimal igstamount) {
this.igstamount = igstamount;
}
public String getCgstamount() {
return cgstamount;
}
public void setCgstamount(String cgstamount) {
this.cgstamount = cgstamount;
}
public String getSgstamount() {
return sgstamount;
}
public void setSgstamount(String sgstamount) {
this.sgstamount = sgstamount;
}
public String getCessamount() {
return cessamount;
}
public void setCessamount(String cessamount) {
this.cessamount = cessamount;
}
public String getStatecessamount() {
return statecessamount;
}
public void setStatecessamount(String statecessamount) {
this.statecessamount = statecessamount;
}
public String getStatecessnonadvaloremamount() {
return statecessnonadvaloremamount;
}
public void setStatecessnonadvaloremamount(String statecessnonadvaloremamount) {
this.statecessnonadvaloremamount = statecessnonadvaloremamount;
}
public String getCessnonadvaloremamount() {
return cessnonadvaloremamount;
}
public void setCessnonadvaloremamount(String cessnonadvaloremamount) {
this.cessnonadvaloremamount = cessnonadvaloremamount;
}
public String getOrderlinereference() {
return orderlinereference;
}
public void setOrderlinereference(String orderlinereference) {
this.orderlinereference = orderlinereference;
}
public String getOrigincountry() {
return origincountry;
}
public void setOrigincountry(String origincountry) {
this.origincountry = origincountry;
}
public String getItemserialnumber() {
return itemserialnumber;
}
public void setItemserialnumber(String itemserialnumber) {
this.itemserialnumber = itemserialnumber;
}
public BigDecimal getItemtotal() {
return itemtotal;
}
public void setItemtotal(BigDecimal itemtotal) {
this.itemtotal = itemtotal;
}
public String getItemattributedetails() {
return itemattributedetails;
}
public void setItemattributedetails(String itemattributedetails) {
this.itemattributedetails = itemattributedetails;
}
public String getTaxtype() {
return taxtype;
}
public void setTaxtype(String taxtype) {
this.taxtype = taxtype;
}
public String getBatchnamenumber() {
return batchnamenumber;
}
public void setBatchnamenumber(String batchnamenumber) {
this.batchnamenumber = batchnamenumber;
}
public String getBatchexpirydate() {
return batchexpirydate;
}
public void setBatchexpirydate(String batchexpirydate) {
this.batchexpirydate = batchexpirydate;
}
public String getWarrantydate() {
return warrantydate;
}
public void setWarrantydate(String warrantydate) {
this.warrantydate = warrantydate;
}
public String getItceligibility() {
return itceligibility;
}
public void setItceligibility(String itceligibility) {
this.itceligibility = itceligibility;
}
public String getItcigstamount() {
return itcigstamount;
}
public void setItcigstamount(String itcigstamount) {
this.itcigstamount = itcigstamount;
}
public String getItccgstamount() {
return itccgstamount;
}
public void setItccgstamount(String itccgstamount) {
this.itccgstamount = itccgstamount;
}
public String getItcsgstamount() {
return itcsgstamount;
}
public void setItcsgstamount(String itcsgstamount) {
this.itcsgstamount = itcsgstamount;
}

public String getItccessamount() {
return itccessamount;
}
public void setItccessamount(String itccessamount) {
this.itccessamount = itccessamount;
}
public String getCustomitem1() {
return customitem1;
}
public void setCustomitem1(String customitem1) {
this.customitem1 = customitem1;
}
public String getCustomitem2() {
return customitem2;
}
public void setCustomitem2(String customitem2) {
this.customitem2 = customitem2;
}
public String getCustomitem3() {
return customitem3;
}
public void setCustomitem3(String customitem3) {
this.customitem3 = customitem3;
}
public String getCustomitem4() {
return customitem4;
}
public void setCustomitem4(String customitem4) {
this.customitem4 = customitem4;
}
public String getCustomitem5() {
return customitem5;
}
public void setCustomitem5(String customitem5) {
this.customitem5 = customitem5;
}
public String getCustomitem6() {
return customitem6;
}
public void setCustomitem6(String customitem6) {
this.customitem6 = customitem6;
}
public String getCustomitem7() {
return customitem7;
}
public void setCustomitem7(String customitem7) {
this.customitem7 = customitem7;
}
public String getCustomitem8() {
return customitem8;
}
public void setCustomitem8(String customitem8) {
this.customitem8 = customitem8;
}
public String getCustomitem9() {
return customitem9;
}
public void setCustomitem9(String customitem9) {
this.customitem9 = customitem9;
}
public String getCustomitem10() {
return customitem10;
}
public void setCustomitem10(String customitem10) {
this.customitem10 = customitem10;
}


}