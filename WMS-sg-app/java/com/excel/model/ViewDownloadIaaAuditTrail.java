package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "ViewDownloadIaaAuditTrail")
@NamedStoredProcedureQuery(
name="callViewDownloadIaaAuditTrail",
procedureName = "DOWNLOAD_IAA_AUDIT_TRAIL",
resultClasses = ViewDownloadIaaAuditTrail.class,
parameters = {
		@StoredProcedureParameter(name="startdate", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name="endDate", mode = ParameterMode.IN, type = String.class)
}
)
public class ViewDownloadIaaAuditTrail implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ROW")
	private Long Row;
	
	@Column(name="LOC_NM")
	private String LOC_NM;
	
	@Column(name="STKADJ_ID")
	private Long STKADJ_ID;
	
	@Column(name="STKADJ_NO")
	private String STKADJ_NO;
	
	@Column(name="STKADJ_DT")
	private String STKADJ_DT;
	
	@Column(name="STKADJ_REMARKS")
	private String STKADJ_REMARKS;
	
	@Column(name="STKADJ_APPR_STATUS")
	private String STKADJ_APPR_STATUS;
	
	@Column(name="STKADJ_INS_USR_ID")
	private String STKADJ_INS_USR_ID;
	
	@Column(name="INS_NAME")
	private String INS_NAME;
	
	@Column(name="STKADJ_INS_DT")
	private String STKADJ_INS_DT;
	
	@Column(name="STKADJ_INS_IP_ADD")
	private String STKADJ_INS_IP_ADD;
	
	@Column(name="STKADJ_MOD_USR_ID")
	private String STKADJ_MOD_USR_ID;
	
	@Column(name="MOD_NAME")
	private String MOD_NAME;
	
	@Column(name="STKADJ_MOD_DT")
	private String STKADJ_MOD_DT;
	
	@Column(name="STKADJ_MOD_IP_ADD")
	private String STKADJ_MOD_IP_ADD;
	
	@Column(name="MOVE_TYPE")
	private String MOVE_TYPE;
	
	@Column(name="PROD_ID")
	private Long PROD_ID;
	
	@Column(name="SMP_PROD_CD")
	private String SMP_PROD_CD;
	
	@Column(name="SMP_PROD_NAME")
	private String SMP_PROD_NAME;
	
	@Column(name="SMP_STD_DIV_ID")
	private Long SMP_STD_DIV_ID;
	
	@Column(name="DIV_DISP_NM")
	private String DIV_DISP_NM;
	
	@Column(name="SMP_PACK_ID")
	private Long SMP_PACK_ID;
	
	@Column(name="PACK_DISP_NM")
	private String PACK_DISP_NM;
	
	@Column(name="BATCH_ID")
	private Long BATCH_ID;
	
	@Column(name="BATCH_NO")
	private String BATCH_NO;
	
	@Column(name="BATCH_EXPIRY_DT")
	private String BATCH_EXPIRY_DT;
	
	@Column(name="STOCK_TYPE")
	private String STOCK_TYPE;
	
	@Column(name="STOCK_TYPE_DESC")
	private String STOCK_TYPE_DESC;
	
	@Column(name="ADJ_QTY")
	private BigDecimal ADJ_QTY;
	
	@Column(name="RATE")
	private BigDecimal RATE;
	
	@Column(name="STKADJ_VALUE")
	private BigDecimal STKADJ_VALUE;
	
	@Column(name="ADJDTL_ID")
	private Long ADJDTL_ID;
	
	@Column(name="IND")
	private String IND;

	public Long getRow() {
		return Row;
	}

	public void setRow(Long row) {
		Row = row;
	}

	public String getLOC_NM() {
		return LOC_NM;
	}

	public void setLOC_NM(String lOC_NM) {
		LOC_NM = lOC_NM;
	}

	public Long getSTKADJ_ID() {
		return STKADJ_ID;
	}

	public void setSTKADJ_ID(Long sTKADJ_ID) {
		STKADJ_ID = sTKADJ_ID;
	}

	public String getSTKADJ_NO() {
		return STKADJ_NO;
	}

	public void setSTKADJ_NO(String sTKADJ_NO) {
		STKADJ_NO = sTKADJ_NO;
	}

	public String getSTKADJ_DT() {
		return STKADJ_DT;
	}

	public void setSTKADJ_DT(String sTKADJ_DT) {
		STKADJ_DT = sTKADJ_DT;
	}

	public String getSTKADJ_REMARKS() {
		return STKADJ_REMARKS;
	}

	public void setSTKADJ_REMARKS(String sTKADJ_REMARKS) {
		STKADJ_REMARKS = sTKADJ_REMARKS;
	}

	public String getSTKADJ_APPR_STATUS() {
		return STKADJ_APPR_STATUS;
	}

	public void setSTKADJ_APPR_STATUS(String sTKADJ_APPR_STATUS) {
		STKADJ_APPR_STATUS = sTKADJ_APPR_STATUS;
	}

	public String getSTKADJ_INS_USR_ID() {
		return STKADJ_INS_USR_ID;
	}

	public void setSTKADJ_INS_USR_ID(String sTKADJ_INS_USR_ID) {
		STKADJ_INS_USR_ID = sTKADJ_INS_USR_ID;
	}

	public String getINS_NAME() {
		return INS_NAME;
	}

	public void setINS_NAME(String iNS_NAME) {
		INS_NAME = iNS_NAME;
	}

	public String getSTKADJ_INS_DT() {
		return STKADJ_INS_DT;
	}

	public void setSTKADJ_INS_DT(String sTKADJ_INS_DT) {
		STKADJ_INS_DT = sTKADJ_INS_DT;
	}

	public String getSTKADJ_INS_IP_ADD() {
		return STKADJ_INS_IP_ADD;
	}

	public void setSTKADJ_INS_IP_ADD(String sTKADJ_INS_IP_ADD) {
		STKADJ_INS_IP_ADD = sTKADJ_INS_IP_ADD;
	}

	public String getSTKADJ_MOD_USR_ID() {
		return STKADJ_MOD_USR_ID;
	}

	public void setSTKADJ_MOD_USR_ID(String sTKADJ_MOD_USR_ID) {
		STKADJ_MOD_USR_ID = sTKADJ_MOD_USR_ID;
	}

	public String getMOD_NAME() {
		return MOD_NAME;
	}

	public void setMOD_NAME(String mOD_NAME) {
		MOD_NAME = mOD_NAME;
	}

	public String getSTKADJ_MOD_DT() {
		return STKADJ_MOD_DT;
	}

	public void setSTKADJ_MOD_DT(String sTKADJ_MOD_DT) {
		STKADJ_MOD_DT = sTKADJ_MOD_DT;
	}

	public String getSTKADJ_MOD_IP_ADD() {
		return STKADJ_MOD_IP_ADD;
	}

	public void setSTKADJ_MOD_IP_ADD(String sTKADJ_MOD_IP_ADD) {
		STKADJ_MOD_IP_ADD = sTKADJ_MOD_IP_ADD;
	}

	public String getMOVE_TYPE() {
		return MOVE_TYPE;
	}

	public void setMOVE_TYPE(String mOVE_TYPE) {
		MOVE_TYPE = mOVE_TYPE;
	}

	public Long getPROD_ID() {
		return PROD_ID;
	}

	public void setPROD_ID(Long pROD_ID) {
		PROD_ID = pROD_ID;
	}

	public String getSMP_PROD_CD() {
		return SMP_PROD_CD;
	}

	public void setSMP_PROD_CD(String sMP_PROD_CD) {
		SMP_PROD_CD = sMP_PROD_CD;
	}

	public String getSMP_PROD_NAME() {
		return SMP_PROD_NAME;
	}

	public void setSMP_PROD_NAME(String sMP_PROD_NAME) {
		SMP_PROD_NAME = sMP_PROD_NAME;
	}

	public Long getSMP_STD_DIV_ID() {
		return SMP_STD_DIV_ID;
	}

	public void setSMP_STD_DIV_ID(Long sMP_STD_DIV_ID) {
		SMP_STD_DIV_ID = sMP_STD_DIV_ID;
	}

	public String getDIV_DISP_NM() {
		return DIV_DISP_NM;
	}

	public void setDIV_DISP_NM(String dIV_DISP_NM) {
		DIV_DISP_NM = dIV_DISP_NM;
	}

	public Long getSMP_PACK_ID() {
		return SMP_PACK_ID;
	}

	public void setSMP_PACK_ID(Long sMP_PACK_ID) {
		SMP_PACK_ID = sMP_PACK_ID;
	}

	public String getPACK_DISP_NM() {
		return PACK_DISP_NM;
	}

	public void setPACK_DISP_NM(String pACK_DISP_NM) {
		PACK_DISP_NM = pACK_DISP_NM;
	}

	public Long getBATCH_ID() {
		return BATCH_ID;
	}

	public void setBATCH_ID(Long bATCH_ID) {
		BATCH_ID = bATCH_ID;
	}

	public String getBATCH_NO() {
		return BATCH_NO;
	}

	public void setBATCH_NO(String bATCH_NO) {
		BATCH_NO = bATCH_NO;
	}

	public String getBATCH_EXPIRY_DT() {
		return BATCH_EXPIRY_DT;
	}

	public void setBATCH_EXPIRY_DT(String bATCH_EXPIRY_DT) {
		BATCH_EXPIRY_DT = bATCH_EXPIRY_DT;
	}

	public String getSTOCK_TYPE() {
		return STOCK_TYPE;
	}

	public void setSTOCK_TYPE(String sTOCK_TYPE) {
		STOCK_TYPE = sTOCK_TYPE;
	}

	public String getSTOCK_TYPE_DESC() {
		return STOCK_TYPE_DESC;
	}

	public void setSTOCK_TYPE_DESC(String sTOCK_TYPE_DESC) {
		STOCK_TYPE_DESC = sTOCK_TYPE_DESC;
	}

	public BigDecimal getADJ_QTY() {
		return ADJ_QTY;
	}

	public void setADJ_QTY(BigDecimal aDJ_QTY) {
		ADJ_QTY = aDJ_QTY;
	}

	public BigDecimal getRATE() {
		return RATE;
	}

	public void setRATE(BigDecimal rATE) {
		RATE = rATE;
	}

	public BigDecimal getSTKADJ_VALUE() {
		return STKADJ_VALUE;
	}

	public void setSTKADJ_VALUE(BigDecimal sTKADJ_VALUE) {
		STKADJ_VALUE = sTKADJ_VALUE;
	}

	public Long getADJDTL_ID() {
		return ADJDTL_ID;
	}

	public void setADJDTL_ID(Long aDJDTL_ID) {
		ADJDTL_ID = aDJDTL_ID;
	}

	public String getIND() {
		return IND;
	}

	public void setIND(String iND) {
		IND = iND;
	}
	
	
}