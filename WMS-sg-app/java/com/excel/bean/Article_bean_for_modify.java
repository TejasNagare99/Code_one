package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Article_bean_for_modify {

	private long TRD_SCH_SLNO;
	private String SCHEME_APPTYPE;
	private String LOCATION;
	private String TRD_SCHEME_CODE;
	private String TRD_SCHEME_NAME;
	private String TRD_SCHEME_DESCR;
	
	private Date VALID_FROM_DT;
	private Date VALID_TO_DT;
	
	private String APPLY_TO;
	private long BILLED_VALUE;
	private String TRD_SCH_TYPE;
	private String INVOICE_TYPE;
	private long ARTICLE_PROD_ID;
	private String ARTICLE_PROD_CD;
	private String ARTICLE_PROD_NAME;
	public String getGrn_qty() {
		return grn_qty;
	}
	public void setGrn_qty(String grn_qty) {
		this.grn_qty = grn_qty;
	}
	private BigDecimal ARTICLE_PROD_RATE;
	private long ARTICLE_QTY_PER_TOT_VAL;
	
	private String grn_qty;
	
	
	private String stringDateFrom;
	private String stringDateTo;
	
	public String getStringDateFrom() {
		return stringDateFrom;
	}
	public void setStringDateFrom(String stringDateFrom) {
		this.stringDateFrom = stringDateFrom;
	}
	public String getStringDateTo() {
		return stringDateTo;
	}
	public void setStringDateTo(String stringDateTo) {
		this.stringDateTo = stringDateTo;
	}
	public long getTRD_SCH_SLNO() {
		return TRD_SCH_SLNO;
	}
	public void setTRD_SCH_SLNO(long tRD_SCH_SLNO) {
		TRD_SCH_SLNO = tRD_SCH_SLNO;
	}
	public String getSCHEME_APPTYPE() {
		return SCHEME_APPTYPE;
	}
	public void setSCHEME_APPTYPE(String sCHEME_APPTYPE) {
		SCHEME_APPTYPE = sCHEME_APPTYPE;
	}
	public String getLOCATION() {
		return LOCATION;
	}
	public void setLOCATION(String lOCATION) {
		LOCATION = lOCATION;
	}
	public String getTRD_SCHEME_CODE() {
		return TRD_SCHEME_CODE;
	}
	public void setTRD_SCHEME_CODE(String tRD_SCHEME_CODE) {
		TRD_SCHEME_CODE = tRD_SCHEME_CODE;
	}
	public String getTRD_SCHEME_NAME() {
		return TRD_SCHEME_NAME;
	}
	public void setTRD_SCHEME_NAME(String tRD_SCHEME_NAME) {
		TRD_SCHEME_NAME = tRD_SCHEME_NAME;
	}
	public String getTRD_SCHEME_DESCR() {
		return TRD_SCHEME_DESCR;
	}
	public void setTRD_SCHEME_DESCR(String tRD_SCHEME_DESCR) {
		TRD_SCHEME_DESCR = tRD_SCHEME_DESCR;
	}
	public Date getVALID_FROM_DT() {
		return VALID_FROM_DT;
	}
	public void setVALID_FROM_DT(Date vALID_FROM_DT) {
		VALID_FROM_DT = vALID_FROM_DT;
	}
	public Date getVALID_TO_DT() {
		return VALID_TO_DT;
	}
	public void setVALID_TO_DT(Date vALID_TO_DT) {
		VALID_TO_DT = vALID_TO_DT;
	}
	public String getAPPLY_TO() {
		return APPLY_TO;
	}
	public void setAPPLY_TO(String aPPLY_TO) {
		APPLY_TO = aPPLY_TO;
	}
	public long getBILLED_VALUE() {
		return BILLED_VALUE;
	}
	public void setBILLED_VALUE(long bILLED_VALUE) {
		BILLED_VALUE = bILLED_VALUE;
	}
	public String getTRD_SCH_TYPE() {
		return TRD_SCH_TYPE;
	}
	public void setTRD_SCH_TYPE(String tRD_SCH_TYPE) {
		TRD_SCH_TYPE = tRD_SCH_TYPE;
	}
	public String getINVOICE_TYPE() {
		return INVOICE_TYPE;
	}
	public void setINVOICE_TYPE(String iNVOICE_TYPE) {
		INVOICE_TYPE = iNVOICE_TYPE;
	}
	public long getARTICLE_PROD_ID() {
		return ARTICLE_PROD_ID;
	}
	public void setARTICLE_PROD_ID(long aRTICLE_PROD_ID) {
		ARTICLE_PROD_ID = aRTICLE_PROD_ID;
	}
	public String getARTICLE_PROD_CD() {
		return ARTICLE_PROD_CD;
	}
	public void setARTICLE_PROD_CD(String aRTICLE_PROD_CD) {
		ARTICLE_PROD_CD = aRTICLE_PROD_CD;
	}
	public String getARTICLE_PROD_NAME() {
		return ARTICLE_PROD_NAME;
	}
	public void setARTICLE_PROD_NAME(String aRTICLE_PROD_NAME) {
		ARTICLE_PROD_NAME = aRTICLE_PROD_NAME;
	}
	public BigDecimal getARTICLE_PROD_RATE() {
		return ARTICLE_PROD_RATE;
	}
	public void setARTICLE_PROD_RATE(BigDecimal aRTICLE_PROD_RATE) {
		ARTICLE_PROD_RATE = aRTICLE_PROD_RATE;
	}
	public long getARTICLE_QTY_PER_TOT_VAL() {
		return ARTICLE_QTY_PER_TOT_VAL;
	}
	public void setARTICLE_QTY_PER_TOT_VAL(long aRTICLE_QTY_PER_TOT_VAL) {
		ARTICLE_QTY_PER_TOT_VAL = aRTICLE_QTY_PER_TOT_VAL;
	}
	@Override
	public String toString() {
		return "Article_bean_for_modify [TRD_SCH_SLNO=" + TRD_SCH_SLNO + ", SCHEME_APPTYPE=" + SCHEME_APPTYPE
				+ ", LOCATION=" + LOCATION + ", TRD_SCHEME_CODE=" + TRD_SCHEME_CODE + ", TRD_SCHEME_NAME="
				+ TRD_SCHEME_NAME + ", TRD_SCHEME_DESCR=" + TRD_SCHEME_DESCR + ", VALID_FROM_DT=" + VALID_FROM_DT
				+ ", VALID_TO_DT=" + VALID_TO_DT + ", APPLY_TO=" + APPLY_TO + ", BILLED_VALUE=" + BILLED_VALUE
				+ ", TRD_SCH_TYPE=" + TRD_SCH_TYPE + ", INVOICE_TYPE=" + INVOICE_TYPE + ", ARTICLE_PROD_ID="
				+ ARTICLE_PROD_ID + ", ARTICLE_PROD_CD=" + ARTICLE_PROD_CD + ", ARTICLE_PROD_NAME=" + ARTICLE_PROD_NAME
				+ ", ARTICLE_PROD_RATE=" + ARTICLE_PROD_RATE + ", ARTICLE_QTY_PER_TOT_VAL=" + ARTICLE_QTY_PER_TOT_VAL
				+ "]";
	}
	
	
	
	
	

}
