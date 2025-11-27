package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "DispatchAuditTrailBean")
@NamedStoredProcedureQuery(
		name="callDispatchAuditTrailBean",
		procedureName = "DOWNLOAD_DSP_AUDIT_TRAIL",
		resultClasses = DispatchAuditTrailBean.class,
		parameters = {
				@StoredProcedureParameter(name="startdate", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="endDate", mode = ParameterMode.IN, type = String.class)
		}
)

@NamedStoredProcedureQuery(
		name="callDispatchAuditTrailBeanprevious",
		procedureName = "DOWNLOAD_DSP_AUDIT_TRAIL_PREVIOUS",
		resultClasses = DispatchAuditTrailBean.class,
		parameters = {
				@StoredProcedureParameter(name="startdate", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="endDate", mode = ParameterMode.IN, type = String.class)
		}
)


public class DispatchAuditTrailBean {
	
	@Id
	@Column(name="ROWNUM")
	private String ROWNUM;
	
	@Column(name="LOC_NM")
	private String LOC_NM;
	
	@Column(name="DSP_ID")
	private String DSP_ID;
	
	@Column(name="DSP_NO")
	private String DSP_NO;
	
	@Column(name="DSP_DT")
	private String DSP_DT;
	
	@Column(name="FSTAFF_NAME")
	private String FSTAFF_NAME;
	
	@Column(name="DSP_LR_NO")
	private String DSP_LR_NO;	
	
	@Column(name="DSP_LR_DT")
	private String DSP_LR_DT;
	
	@Column(name="DSP_APPR_STATUS")
	private String DSP_APPR_STATUS;
	
	@Column(name="DSP_CHALLAN_NO")
	private String DSP_CHALLAN_NO;	
	
	@Column(name="DSP_CHALLAN_DT")
	private String DSP_CHALLAN_DT;	
	
	@Column(name="DSPDTL_PROD_ID")
	private String DSPDTL_PROD_ID;
	
	@Column(name="SMP_PROD_CD")
	private String SMP_PROD_CD;
	
	@Column(name="SMP_PROD_NAME")
	private String SMP_PROD_NAME;
	
	@Column(name="BATCH_NO")
	private String BATCH_NO;
	
	@Column(name="DSPDTL_DISP_QTY")
	private BigDecimal DSPDTL_DISP_QTY;	
	
	@Column(name="DSPDTL_RATE")
	private BigDecimal DSPDTL_RATE;
	
	@Column(name="DSP_VALUE")
	private BigDecimal DSP_VALUE;
	
	@Column(name="APPR_REMARKS")
	private String APPR_REMARKS;
	
	@Column(name="APPR_LEVEL")
	private String APPR_LEVEL;
	
	@Column(name="APPR_LEVEL_CYCLE")
	private String APPR_LEVEL_CYCLE;
	
	@Column(name="APPR_USER_NAME")
	private String APPR_USER_NAME;	
	
	@Column(name="APPR_DATE")
	private String APPR_DATE;	
	
	@Column(name="STATUS")
	private String STATUS;
	
	@Column(name="REMARKS")
	private String REMARKS	;
	
	@Column(name="DSP_INS_USR_ID")
	private String DSP_INS_USR_ID;
	
	@Column(name="INS_NAME")
	private String INS_NAME;
	
	@Column(name="DSP_INS_DT")
	private String DSP_INS_DT;
	
	@Column(name="DSP_INS_IP_ADD")
	private String DSP_INS_IP_ADD;	
	
	@Column(name="DSP_MOD_USR_ID")
	private String DSP_MOD_USR_ID;
	
	@Column(name="MOD_NAME")
	private String MOD_NAME;
	
	@Column(name="DSP_MOD_DT")
	private String DSP_MOD_DT;
	
	@Column(name="DSP_MOD_IP_ADD")
	private String DSP_MOD_IP_ADD;
	
	@Column(name="BATCH_EXPIRY_DT")
	private String BATCH_EXPIRY_DT;

	
	public String getROWNUM() {
		return ROWNUM;
	}

	public void setROWNUM(String rOWNUM) {
		ROWNUM = rOWNUM;
	}

	public String getLOC_NM() {
		return LOC_NM;
	}

	public void setLOC_NM(String lOC_NM) {
		LOC_NM = lOC_NM;
	}

	public String getDSP_ID() {
		return DSP_ID;
	}

	public void setDSP_ID(String dSP_ID) {
		DSP_ID = dSP_ID;
	}

	public String getDSP_NO() {
		return DSP_NO;
	}

	public void setDSP_NO(String dSP_NO) {
		DSP_NO = dSP_NO;
	}

	public String getDSP_DT() {
		return DSP_DT;
	}

	public void setDSP_DT(String dSP_DT) {
		DSP_DT = dSP_DT;
	}

	public String getFSTAFF_NAME() {
		return FSTAFF_NAME;
	}

	public void setFSTAFF_NAME(String fSTAFF_NAME) {
		FSTAFF_NAME = fSTAFF_NAME;
	}

	public String getDSP_LR_NO() {
		return DSP_LR_NO;
	}

	public void setDSP_LR_NO(String dSP_LR_NO) {
		DSP_LR_NO = dSP_LR_NO;
	}

	public String getDSP_LR_DT() {
		return DSP_LR_DT;
	}

	public void setDSP_LR_DT(String dSP_LR_DT) {
		DSP_LR_DT = dSP_LR_DT;
	}

	public String getDSP_APPR_STATUS() {
		return DSP_APPR_STATUS;
	}

	public void setDSP_APPR_STATUS(String dSP_APPR_STATUS) {
		DSP_APPR_STATUS = dSP_APPR_STATUS;
	}

	public String getDSP_CHALLAN_NO() {
		return DSP_CHALLAN_NO;
	}

	public void setDSP_CHALLAN_NO(String dSP_CHALLAN_NO) {
		DSP_CHALLAN_NO = dSP_CHALLAN_NO;
	}

	public String getDSP_CHALLAN_DT() {
		return DSP_CHALLAN_DT;
	}

	public void setDSP_CHALLAN_DT(String dSP_CHALLAN_DT) {
		DSP_CHALLAN_DT = dSP_CHALLAN_DT;
	}

	public String getDSPDTL_PROD_ID() {
		return DSPDTL_PROD_ID;
	}

	public void setDSPDTL_PROD_ID(String dSPDTL_PROD_ID) {
		DSPDTL_PROD_ID = dSPDTL_PROD_ID;
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

	public String getBATCH_NO() {
		return BATCH_NO;
	}

	public void setBATCH_NO(String bATCH_NO) {
		BATCH_NO = bATCH_NO;
	}

	

	public BigDecimal getDSPDTL_DISP_QTY() {
		return DSPDTL_DISP_QTY;
	}

	public void setDSPDTL_DISP_QTY(BigDecimal dSPDTL_DISP_QTY) {
		DSPDTL_DISP_QTY = dSPDTL_DISP_QTY;
	}

	public BigDecimal getDSPDTL_RATE() {
		return DSPDTL_RATE;
	}

	public void setDSPDTL_RATE(BigDecimal dSPDTL_RATE) {
		DSPDTL_RATE = dSPDTL_RATE;
	}

	public BigDecimal getDSP_VALUE() {
		return DSP_VALUE;
	}

	public void setDSP_VALUE(BigDecimal dSP_VALUE) {
		DSP_VALUE = dSP_VALUE;
	}

	public String getAPPR_REMARKS() {
		return APPR_REMARKS;
	}

	public void setAPPR_REMARKS(String aPPR_REMARKS) {
		APPR_REMARKS = aPPR_REMARKS;
	}

	public String getAPPR_LEVEL() {
		return APPR_LEVEL;
	}

	public void setAPPR_LEVEL(String aPPR_LEVEL) {
		APPR_LEVEL = aPPR_LEVEL;
	}

	public String getAPPR_LEVEL_CYCLE() {
		return APPR_LEVEL_CYCLE;
	}

	public void setAPPR_LEVEL_CYCLE(String aPPR_LEVEL_CYCLE) {
		APPR_LEVEL_CYCLE = aPPR_LEVEL_CYCLE;
	}

	public String getAPPR_USER_NAME() {
		return APPR_USER_NAME;
	}

	public void setAPPR_USER_NAME(String aPPR_USER_NAME) {
		APPR_USER_NAME = aPPR_USER_NAME;
	}

	public String getAPPR_DATE() {
		return APPR_DATE;
	}

	public void setAPPR_DATE(String aPPR_DATE) {
		APPR_DATE = aPPR_DATE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getDSP_INS_USR_ID() {
		return DSP_INS_USR_ID;
	}

	public void setDSP_INS_USR_ID(String dSP_INS_USR_ID) {
		DSP_INS_USR_ID = dSP_INS_USR_ID;
	}

	public String getINS_NAME() {
		return INS_NAME;
	}

	public void setINS_NAME(String iNS_NAME) {
		INS_NAME = iNS_NAME;
	}

	public String getDSP_INS_DT() {
		return DSP_INS_DT;
	}

	public void setDSP_INS_DT(String dSP_INS_DT) {
		DSP_INS_DT = dSP_INS_DT;
	}

	public String getDSP_INS_IP_ADD() {
		return DSP_INS_IP_ADD;
	}

	public void setDSP_INS_IP_ADD(String dSP_INS_IP_ADD) {
		DSP_INS_IP_ADD = dSP_INS_IP_ADD;
	}

	public String getDSP_MOD_USR_ID() {
		return DSP_MOD_USR_ID;
	}

	public void setDSP_MOD_USR_ID(String dSP_MOD_USR_ID) {
		DSP_MOD_USR_ID = dSP_MOD_USR_ID;
	}

	public String getMOD_NAME() {
		return MOD_NAME;
	}

	public void setMOD_NAME(String mOD_NAME) {
		MOD_NAME = mOD_NAME;
	}

	public String getDSP_MOD_DT() {
		return DSP_MOD_DT;
	}

	public void setDSP_MOD_DT(String dSP_MOD_DT) {
		DSP_MOD_DT = dSP_MOD_DT;
	}

	public String getDSP_MOD_IP_ADD() {
		return DSP_MOD_IP_ADD;
	}

	public void setDSP_MOD_IP_ADD(String dSP_MOD_IP_ADD) {
		DSP_MOD_IP_ADD = dSP_MOD_IP_ADD;
	}

	public String getBATCH_EXPIRY_DT() {
		return BATCH_EXPIRY_DT;
	}

	public void setBATCH_EXPIRY_DT(String bATCH_EXPIRY_DT) {
		BATCH_EXPIRY_DT = bATCH_EXPIRY_DT;
	}	
	
	

}