package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;

public class SchemeDetailDataBean {

	private Long  TRD_SCH_SLNO;
	private String LOCATION;
	private String TRD_SCHEME_CODE;
	private Date  VALID_FROM_DT;
	private Date VALID_TO_DT;
	private String APPLY_TO;
	private String ARTICLE_PROD_CD;
	private String SMP_PROD_NAME;
	private BigDecimal BILLED_VALUE;
	private BigDecimal ARTICLE_QTY_PER_TOT_VAL;
	private BigDecimal ARTICLE_PROD_RATE;
	private String ARTICLE_PROD_NAME;
	private Long TRD_SCHEME_DTL_ID;
	private String SALE_PROD_CODE_ERP;
	private String SALE_PROD_NAME;
	private BigDecimal PER_SALE_QTY_BILLED;
	private BigDecimal PER_SALE_QTY_FREE;
	private BigDecimal ARTICLE_QTY;
	private String SCHEME_NAME;
	private Date  APPLY_INV_FROM;
	private String SCHEME_DIV_ID ;
	private String remarks;
	private String createdBy;
	
	
	
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSCHEME_DIV_ID() {
		return SCHEME_DIV_ID;
	}
	public void setSCHEME_DIV_ID(String sCHEME_DIV_ID) {
		SCHEME_DIV_ID = sCHEME_DIV_ID;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	private String ValidFromDateString;
	private String ValidToDateString;
	private String apply_inv_fromstring;
	
	
	
	/**
	 * @return the apply_inv_fromstring
	 */
	public String getApply_inv_fromstring() {
		return apply_inv_fromstring;
	}
	/**
	 * @param apply_inv_fromstring the apply_inv_fromstring to set
	 */
	public void setApply_inv_fromstring(String apply_inv_fromstring) {
		this.apply_inv_fromstring = apply_inv_fromstring;
	}
	/**
	 * @return the aPPLY_INV_FROM
	 */
	public Date getAPPLY_INV_FROM() {
		return APPLY_INV_FROM;
	}
	/**
	 * @param aPPLY_INV_FROM the aPPLY_INV_FROM to set
	 */
	public void setAPPLY_INV_FROM(Date aPPLY_INV_FROM) {
		APPLY_INV_FROM = aPPLY_INV_FROM;
	}
	public String getSCHEME_NAME() {
		return SCHEME_NAME;
	}
	public void setSCHEME_NAME(String sCHEME_NAME) {
		SCHEME_NAME = sCHEME_NAME;
	}

	
	
	public String getValidFromDateString() {
		return ValidFromDateString;
	}
	public void setValidFromDateString(String validFromDateString) {
		ValidFromDateString = validFromDateString;
	}
	public String getValidToDateString() {
		return ValidToDateString;
	}
	public void setValidToDateString(String validToDateString) {
		ValidToDateString = validToDateString;
	}
	public Long getTRD_SCH_SLNO() {
		return TRD_SCH_SLNO;
	}
	public void setTRD_SCH_SLNO(Long tRD_SCH_SLNO) {
		TRD_SCH_SLNO = tRD_SCH_SLNO;
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
	public String getARTICLE_PROD_CD() {
		return ARTICLE_PROD_CD;
	}
	public void setARTICLE_PROD_CD(String aRTICLE_PROD_CD) {
		ARTICLE_PROD_CD = aRTICLE_PROD_CD;
	}
	public String getSMP_PROD_NAME() {
		return SMP_PROD_NAME;
	}
	public void setSMP_PROD_NAME(String sMP_PROD_NAME) {
		SMP_PROD_NAME = sMP_PROD_NAME;
	}
	public BigDecimal getBILLED_VALUE() {
		return BILLED_VALUE;
	}
	public void setBILLED_VALUE(BigDecimal bILLED_VALUE) {
		BILLED_VALUE = bILLED_VALUE;
	}
	public BigDecimal getARTICLE_QTY_PER_TOT_VAL() {
		return ARTICLE_QTY_PER_TOT_VAL;
	}
	public void setARTICLE_QTY_PER_TOT_VAL(BigDecimal aRTICLE_QTY_PER_TOT_VAL) {
		ARTICLE_QTY_PER_TOT_VAL = aRTICLE_QTY_PER_TOT_VAL;
	}
	public BigDecimal getARTICLE_PROD_RATE() {
		return ARTICLE_PROD_RATE;
	}
	public void setARTICLE_PROD_RATE(BigDecimal aRTICLE_PROD_RATE) {
		ARTICLE_PROD_RATE = aRTICLE_PROD_RATE;
	}
	public String getARTICLE_PROD_NAME() {
		return ARTICLE_PROD_NAME;
	}
	public void setARTICLE_PROD_NAME(String aRTICLE_PROD_NAME) {
		ARTICLE_PROD_NAME = aRTICLE_PROD_NAME;
	}
	public Long getTRD_SCHEME_DTL_ID() {
		return TRD_SCHEME_DTL_ID;
	}
	public void setTRD_SCHEME_DTL_ID(Long tRD_SCHEME_DTL_ID) {
		TRD_SCHEME_DTL_ID = tRD_SCHEME_DTL_ID;
	}
	public String getSALE_PROD_CODE_ERP() {
		return SALE_PROD_CODE_ERP;
	}
	public void setSALE_PROD_CODE_ERP(String sALE_PROD_CODE_ERP) {
		SALE_PROD_CODE_ERP = sALE_PROD_CODE_ERP;
	}
	public String getSALE_PROD_NAME() {
		return SALE_PROD_NAME;
	}
	public void setSALE_PROD_NAME(String sALE_PROD_NAME) {
		SALE_PROD_NAME = sALE_PROD_NAME;
	}
	public BigDecimal getPER_SALE_QTY_BILLED() {
		return PER_SALE_QTY_BILLED;
	}
	public void setPER_SALE_QTY_BILLED(BigDecimal pER_SALE_QTY_BILLED) {
		PER_SALE_QTY_BILLED = pER_SALE_QTY_BILLED;
	}
	public BigDecimal getPER_SALE_QTY_FREE() {
		return PER_SALE_QTY_FREE;
	}
	public void setPER_SALE_QTY_FREE(BigDecimal pER_SALE_QTY_FREE) {
		PER_SALE_QTY_FREE = pER_SALE_QTY_FREE;
	}
	public BigDecimal getARTICLE_QTY() {
		return ARTICLE_QTY;
	}
	public void setARTICLE_QTY(BigDecimal aRTICLE_QTY) {
		ARTICLE_QTY = aRTICLE_QTY;
	}
	@Override
	public String toString() {
		return "SchemeDetailDataBean [TRD_SCH_SLNO=" + TRD_SCH_SLNO + ", LOCATION=" + LOCATION + ", TRD_SCHEME_CODE="
				+ TRD_SCHEME_CODE + ", VALID_FROM_DT=" + VALID_FROM_DT + ", VALID_TO_DT=" + VALID_TO_DT + ", APPLY_TO="
				+ APPLY_TO + ", ARTICLE_PROD_CD=" + ARTICLE_PROD_CD + ", SMP_PROD_NAME=" + SMP_PROD_NAME
				+ ", BILLED_VALUE=" + BILLED_VALUE + ", ARTICLE_QTY_PER_TOT_VAL=" + ARTICLE_QTY_PER_TOT_VAL
				+ ", ARTICLE_PROD_RATE=" + ARTICLE_PROD_RATE + ", ARTICLE_PROD_NAME=" + ARTICLE_PROD_NAME
				+ ", TRD_SCHEME_DTL_ID=" + TRD_SCHEME_DTL_ID + ", SALE_PROD_CODE_ERP=" + SALE_PROD_CODE_ERP
				+ ", SALE_PROD_NAME=" + SALE_PROD_NAME + ", PER_SALE_QTY_BILLED=" + PER_SALE_QTY_BILLED
				+ ", PER_SALE_QTY_FREE=" + PER_SALE_QTY_FREE + ", ARTICLE_QTY=" + ARTICLE_QTY + ", SCHEME_NAME="
				+ SCHEME_NAME + ", ValidFromDateString=" + ValidFromDateString + ", ValidToDateString="
				+ ValidToDateString + "]";
	}
	
	
	

	
}
