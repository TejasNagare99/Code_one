package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Articel_Scheme_Approved_Data_Bean {

	private String TRD_SCH_SLNO;
	private String COMPANY_CD;
	private String SUB_COMP_CD;
	private String SUB_COMP_ID;
	private String SCHEME_APPTYPE;
	private String LOC_ID;
	private String TRD_SCHEME_ID;
	private String TRD_SCHEME_CODE;
	private String TRD_SCHEME_NAME;
	private String TRD_SCHEME_DESCR;
	private Date APPLY_INV_FROM;
	private Date VALID_FROM_DT;
	private Date VALID_TO_DT;
	private String APPLY_TO;
	private BigDecimal BILLED_VALUE;
	private String TRD_SCH_TYPE;
	private String INVOICE_TYPE;
	private String ARTICLE_PROD_CD;
	private String ARTICLE_PROD_ID;
	private Long ARTICLE_QTY_PER_TOT_VAL;
	private BigDecimal ARTICLE_PROD_RATE;

	private String CREATED_BY;
	private Date CREATED_DATE;
	private String LAST_MOD_BY;
	private Date LAST_MOD_DATE;
	private String TRD_SCH_STATUS;
	private String REMARKS;
	private String FINYEAR;
	private String SCHEME_DIV_ID;

	private String SPECIFIC_BRAND_SCHEME;
	private Long cycle_no;

	private String TRD_SCHEME_DTL_ID;
	private String SALE_PROD_ID;
	private String SALE_PROD_CODE_SG;
	private String SALE_PROD_CODE_ERP;
	private Long PER_SALE_QTY_BILLED;
	private Long PER_SALE_QTY_FREE;
	private Long PER_SALE_QTY_TOT;
	private Long ARTICLE_QTY;

	private String FILENAME;
	private String FILEPATH;
	private String FILETYPE;
	private Date DOC_ins_dt;
	private String DOC_ins_usr_id;
	private String DOC_ins_ip_addr;

	@Override
	public String toString() {
		return "Articel_Scheme_Approved_Date_Bean [TRD_SCH_SLNO=" + TRD_SCH_SLNO + ", COMPANY_CD=" + COMPANY_CD
				+ ", SUB_COMP_CD=" + SUB_COMP_CD + ", SUB_COMP_ID=" + SUB_COMP_ID + ", SCHEME_APPTYPE=" + SCHEME_APPTYPE
				+ ", LOC_ID=" + LOC_ID + ", TRD_SCHEME_ID=" + TRD_SCHEME_ID + ", TRD_SCHEME_CODE=" + TRD_SCHEME_CODE
				+ ", TRD_SCHEME_NAME=" + TRD_SCHEME_NAME + ", TRD_SCHEME_DESCR=" + TRD_SCHEME_DESCR
				+ ", APPLY_INV_FROM=" + APPLY_INV_FROM + ", VALID_FROM_DT=" + VALID_FROM_DT + ", VALID_TO_DT="
				+ VALID_TO_DT + ", APPLY_TO=" + APPLY_TO + ", BILLED_VALUE=" + BILLED_VALUE + ", TRD_SCH_TYPE="
				+ TRD_SCH_TYPE + ", INVOICE_TYPE=" + INVOICE_TYPE + ", ARTICLE_PROD_CD=" + ARTICLE_PROD_CD
				+ ", ARTICLE_PROD_ID=" + ARTICLE_PROD_ID + ", ARTICLE_QTY_PER_TOT_VAL=" + ARTICLE_QTY_PER_TOT_VAL
				+ ", ARTICLE_PROD_RATE=" + ARTICLE_PROD_RATE + ", CREATED_BY=" + CREATED_BY + ", CREATED_DATE="
				+ CREATED_DATE + ", LAST_MOD_BY=" + LAST_MOD_BY + ", LAST_MOD_DATE=" + LAST_MOD_DATE
				+ ", TRD_SCH_STATUS=" + TRD_SCH_STATUS + ", REMARKS=" + REMARKS + ", FINYEAR=" + FINYEAR
				+ ", SCHEME_DIV_ID=" + SCHEME_DIV_ID + ", SPECIFIC_BRAND_SCHEME=" + SPECIFIC_BRAND_SCHEME
				+ ", cycle_no=" + cycle_no + ", TRD_SCHEME_DTL_ID=" + TRD_SCHEME_DTL_ID + ", SALE_PROD_ID="
				+ SALE_PROD_ID + ", SALE_PROD_CODE_SG=" + SALE_PROD_CODE_SG + ", SALE_PROD_CODE_ERP="
				+ SALE_PROD_CODE_ERP + ", PER_SALE_QTY_BILLED=" + PER_SALE_QTY_BILLED + ", PER_SALE_QTY_FREE="
				+ PER_SALE_QTY_FREE + ", PER_SALE_QTY_TOT=" + PER_SALE_QTY_TOT + ", ARTICLE_QTY=" + ARTICLE_QTY
				+ ", FILENAME=" + FILENAME + ", FILEPATH=" + FILEPATH + ", FILETYPE=" + FILETYPE + ", DOC_ins_dt="
				+ DOC_ins_dt + ", DOC_ins_usr_id=" + DOC_ins_usr_id + ", DOC_ins_ip_addr=" + DOC_ins_ip_addr + "]";
	}

	public String getTRD_SCH_SLNO() {
		return TRD_SCH_SLNO;
	}

	public void setTRD_SCH_SLNO(String tRD_SCH_SLNO) {
		TRD_SCH_SLNO = tRD_SCH_SLNO;
	}

	public String getCOMPANY_CD() {
		return COMPANY_CD;
	}

	public void setCOMPANY_CD(String cOMPANY_CD) {
		COMPANY_CD = cOMPANY_CD;
	}

	public String getSUB_COMP_CD() {
		return SUB_COMP_CD;
	}

	public void setSUB_COMP_CD(String sUB_COMP_CD) {
		SUB_COMP_CD = sUB_COMP_CD;
	}

	public String getSUB_COMP_ID() {
		return SUB_COMP_ID;
	}

	public void setSUB_COMP_ID(String sUB_COMP_ID) {
		SUB_COMP_ID = sUB_COMP_ID;
	}

	public String getSCHEME_APPTYPE() {
		return SCHEME_APPTYPE;
	}

	public void setSCHEME_APPTYPE(String sCHEME_APPTYPE) {
		SCHEME_APPTYPE = sCHEME_APPTYPE;
	}

	public String getLOC_ID() {
		return LOC_ID;
	}

	public void setLOC_ID(String lOC_ID) {
		LOC_ID = lOC_ID;
	}

	public String getTRD_SCHEME_ID() {
		return TRD_SCHEME_ID;
	}

	public void setTRD_SCHEME_ID(String tRD_SCHEME_ID) {
		TRD_SCHEME_ID = tRD_SCHEME_ID;
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

	public Date getAPPLY_INV_FROM() {
		return APPLY_INV_FROM;
	}

	public void setAPPLY_INV_FROM(Date aPPLY_INV_FROM) {
		APPLY_INV_FROM = aPPLY_INV_FROM;
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

	public BigDecimal getBILLED_VALUE() {
		return BILLED_VALUE;
	}

	public void setBILLED_VALUE(BigDecimal bILLED_VALUE) {
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

	public String getARTICLE_PROD_CD() {
		return ARTICLE_PROD_CD;
	}

	public void setARTICLE_PROD_CD(String aRTICLE_PROD_CD) {
		ARTICLE_PROD_CD = aRTICLE_PROD_CD;
	}

	public String getARTICLE_PROD_ID() {
		return ARTICLE_PROD_ID;
	}

	public void setARTICLE_PROD_ID(String aRTICLE_PROD_ID) {
		ARTICLE_PROD_ID = aRTICLE_PROD_ID;
	}

	public Long getARTICLE_QTY_PER_TOT_VAL() {
		return ARTICLE_QTY_PER_TOT_VAL;
	}

	public void setARTICLE_QTY_PER_TOT_VAL(Long aRTICLE_QTY_PER_TOT_VAL) {
		ARTICLE_QTY_PER_TOT_VAL = aRTICLE_QTY_PER_TOT_VAL;
	}

	public BigDecimal getARTICLE_PROD_RATE() {
		return ARTICLE_PROD_RATE;
	}

	public void setARTICLE_PROD_RATE(BigDecimal aRTICLE_PROD_RATE) {
		ARTICLE_PROD_RATE = aRTICLE_PROD_RATE;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}

	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getLAST_MOD_BY() {
		return LAST_MOD_BY;
	}

	public void setLAST_MOD_BY(String lAST_MOD_BY) {
		LAST_MOD_BY = lAST_MOD_BY;
	}

	public Date getLAST_MOD_DATE() {
		return LAST_MOD_DATE;
	}

	public void setLAST_MOD_DATE(Date lAST_MOD_DATE) {
		LAST_MOD_DATE = lAST_MOD_DATE;
	}

	public String getTRD_SCH_STATUS() {
		return TRD_SCH_STATUS;
	}

	public void setTRD_SCH_STATUS(String tRD_SCH_STATUS) {
		TRD_SCH_STATUS = tRD_SCH_STATUS;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getFINYEAR() {
		return FINYEAR;
	}

	public void setFINYEAR(String fINYEAR) {
		FINYEAR = fINYEAR;
	}

	public String getSCHEME_DIV_ID() {
		return SCHEME_DIV_ID;
	}

	public void setSCHEME_DIV_ID(String sCHEME_DIV_ID) {
		SCHEME_DIV_ID = sCHEME_DIV_ID;
	}

	public String getSPECIFIC_BRAND_SCHEME() {
		return SPECIFIC_BRAND_SCHEME;
	}

	public void setSPECIFIC_BRAND_SCHEME(String sPECIFIC_BRAND_SCHEME) {
		SPECIFIC_BRAND_SCHEME = sPECIFIC_BRAND_SCHEME;
	}

	public Long getCycle_no() {
		return cycle_no;
	}

	public void setCycle_no(Long cycle_no) {
		this.cycle_no = cycle_no;
	}

	public String getTRD_SCHEME_DTL_ID() {
		return TRD_SCHEME_DTL_ID;
	}

	public void setTRD_SCHEME_DTL_ID(String tRD_SCHEME_DTL_ID) {
		TRD_SCHEME_DTL_ID = tRD_SCHEME_DTL_ID;
	}

	public String getSALE_PROD_ID() {
		return SALE_PROD_ID;
	}

	public void setSALE_PROD_ID(String sALE_PROD_ID) {
		SALE_PROD_ID = sALE_PROD_ID;
	}

	public String getSALE_PROD_CODE_SG() {
		return SALE_PROD_CODE_SG;
	}

	public void setSALE_PROD_CODE_SG(String sALE_PROD_CODE_SG) {
		SALE_PROD_CODE_SG = sALE_PROD_CODE_SG;
	}

	public String getSALE_PROD_CODE_ERP() {
		return SALE_PROD_CODE_ERP;
	}

	public void setSALE_PROD_CODE_ERP(String sALE_PROD_CODE_ERP) {
		SALE_PROD_CODE_ERP = sALE_PROD_CODE_ERP;
	}

	public Long getPER_SALE_QTY_BILLED() {
		return PER_SALE_QTY_BILLED;
	}

	public void setPER_SALE_QTY_BILLED(Long pER_SALE_QTY_BILLED) {
		PER_SALE_QTY_BILLED = pER_SALE_QTY_BILLED;
	}

	public Long getPER_SALE_QTY_FREE() {
		return PER_SALE_QTY_FREE;
	}

	public void setPER_SALE_QTY_FREE(Long pER_SALE_QTY_FREE) {
		PER_SALE_QTY_FREE = pER_SALE_QTY_FREE;
	}

	public Long getPER_SALE_QTY_TOT() {
		return PER_SALE_QTY_TOT;
	}

	public void setPER_SALE_QTY_TOT(Long pER_SALE_QTY_TOT) {
		PER_SALE_QTY_TOT = pER_SALE_QTY_TOT;
	}

	public Long getARTICLE_QTY() {
		return ARTICLE_QTY;
	}

	public void setARTICLE_QTY(Long aRTICLE_QTY) {
		ARTICLE_QTY = aRTICLE_QTY;
	}

	public String getFILENAME() {
		return FILENAME;
	}

	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}

	public String getFILEPATH() {
		return FILEPATH;
	}

	public void setFILEPATH(String fILEPATH) {
		FILEPATH = fILEPATH;
	}

	public String getFILETYPE() {
		return FILETYPE;
	}

	public void setFILETYPE(String fILETYPE) {
		FILETYPE = fILETYPE;
	}

	public Date getDOC_ins_dt() {
		return DOC_ins_dt;
	}

	public void setDOC_ins_dt(Date dOC_ins_dt) {
		DOC_ins_dt = dOC_ins_dt;
	}

	public String getDOC_ins_usr_id() {
		return DOC_ins_usr_id;
	}

	public void setDOC_ins_usr_id(String dOC_ins_usr_id) {
		DOC_ins_usr_id = dOC_ins_usr_id;
	}

	public String getDOC_ins_ip_addr() {
		return DOC_ins_ip_addr;
	}

	public void setDOC_ins_ip_addr(String dOC_ins_ip_addr) {
		DOC_ins_ip_addr = dOC_ins_ip_addr;
	}

	
	

}
