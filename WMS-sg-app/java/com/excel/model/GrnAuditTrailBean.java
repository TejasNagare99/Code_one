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
@Table(name = "GrnAuditTrailBean")
@NamedStoredProcedureQuery(
		name="callGrnAuditTrailBean",
		procedureName = "DOWNLOAD_GRN_AUDIT_TRAIL",
		resultClasses = GrnAuditTrailBean.class,
		parameters = {
				@StoredProcedureParameter(name="startdate", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="endDate", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="apprLevel", mode = ParameterMode.IN, type = String.class)
		}
)
@NamedStoredProcedureQuery(
		name="callGrnAuditTrailBeanprevious",
		procedureName = "DOWNLOAD_GRN_AUDIT_TRAIL_PREVIOUS",
		resultClasses = GrnAuditTrailBean.class,
		parameters = {
				@StoredProcedureParameter(name="startdate", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="endDate", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="apprLevel", mode = ParameterMode.IN, type = String.class)
		}
)
public class GrnAuditTrailBean {
	
	@Id
	@Column(name="ROWNUM")
	private String ROWNUM;
	
	@Column(name="LOC_NM")
	private String LOC_NM;	
	
	@Column(name="GRN_ID")
	private String GRN_ID;
		
	@Column(name="GRN_NO")
	private String GRN_NO;
	
	@Column(name="GRN_DT")
	private String GRN_DT;
	
	@Column(name="SUP_NM")
	private String SUP_NM;
	
	@Column(name="GRN_LR_NO")
	private String GRN_LR_NO;
	
	@Column(name="GRN_LR_DT")
	private String GRN_LR_DT;
	
	@Column(name="GRN_APPR_STATUS")
	private String GRN_APPR_STATUS;
	
	@Column(name="GRN_TRANSFER_NO")
	private String GRN_TRANSFER_NO;
	
	@Column(name="GRN_TRANSFER_DT")
	private String GRN_TRANSFER_DT;
	
	@Column(name="GRND_PROD_ID")
	private String GRND_PROD_ID;
	
	@Column(name="SMP_PROD_CD")
	private String SMP_PROD_CD;
	
	@Column(name="SMP_PROD_NAME")
	private String SMP_PROD_NAME;
	
	@Column(name="BATCH_NO")
	private String BATCH_NO;
	
	@Column(name="GRND_QTY")
	private BigDecimal GRND_QTY	;
	
	@Column(name="GRND_RATE")
	private BigDecimal GRND_RATE ;
	
	@Column(name="GRND_VALUE")
	private BigDecimal GRND_VALUE ;
	
	@Column(name="APPR_REMARKS")
	private String APPR_REMARKS;
	
	@Column(name="APPR_LEVEL")
	private String APPR_LEVEL	;
	
	@Column(name="APPR_LEVEL_CYCLE")
	private String APPR_LEVEL_CYCLE;
	
	@Column(name="APPR_USER_NAME")
	private String APPR_USER_NAME	;
	
	@Column(name="APPR_DATE")
	private String APPR_DATE;
	
	@Column(name="STATUS")
	private String STATUS;
	
	@Column(name="REMARKS")
	private String REMARKS;	
	
	@Column(name="GRN_ins_usr_id")
	private String GRN_ins_usr_id;
	
	@Column(name="INS_NAME")
	private String INS_NAME;
	
	@Column(name="GRN_INS_DT")
	private String GRN_INS_DT;
	
	@Column(name="GRN_ins_ip_add")
	private String GRN_ins_ip_add;
	
	@Column(name="GRN_MOD_usr_id")
	private String GRN_MOD_usr_id;
	
	@Column(name="MOD_NAME")
	private String MOD_NAME;
	
	@Column(name="GRN_MOD_DT")
	private String GRN_MOD_DT;	
	
	@Column(name="GRN_MOD_ip_add")
	private String GRN_MOD_ip_add;
	
	@Column(name="BATCH_EXPIRY_DT")
	private String BATCH_EXPIRY_DT	;
	
	@Column(name="GRN_APPR_ACQ")
	private String GRN_APPR_ACQ;

	
	
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

	public String getGRN_ID() {
		return GRN_ID;
	}

	public void setGRN_ID(String gRN_ID) {
		GRN_ID = gRN_ID;
	}

	public String getGRN_NO() {
		return GRN_NO;
	}

	public void setGRN_NO(String gRN_NO) {
		GRN_NO = gRN_NO;
	}

	public String getGRN_DT() {
		return GRN_DT;
	}

	public void setGRN_DT(String gRN_DT) {
		GRN_DT = gRN_DT;
	}

	public String getSUP_NM() {
		return SUP_NM;
	}

	public void setSUP_NM(String sUP_NM) {
		SUP_NM = sUP_NM;
	}

	public String getGRN_LR_NO() {
		return GRN_LR_NO;
	}

	public void setGRN_LR_NO(String gRN_LR_NO) {
		GRN_LR_NO = gRN_LR_NO;
	}

	public String getGRN_LR_DT() {
		return GRN_LR_DT;
	}

	public void setGRN_LR_DT(String gRN_LR_DT) {
		GRN_LR_DT = gRN_LR_DT;
	}

	public String getGRN_APPR_STATUS() {
		return GRN_APPR_STATUS;
	}

	public void setGRN_APPR_STATUS(String gRN_APPR_STATUS) {
		GRN_APPR_STATUS = gRN_APPR_STATUS;
	}

	public String getGRN_TRANSFER_NO() {
		return GRN_TRANSFER_NO;
	}

	public void setGRN_TRANSFER_NO(String gRN_TRANSFER_NO) {
		GRN_TRANSFER_NO = gRN_TRANSFER_NO;
	}

	public String getGRN_TRANSFER_DT() {
		return GRN_TRANSFER_DT;
	}

	public void setGRN_TRANSFER_DT(String gRN_TRANSFER_DT) {
		GRN_TRANSFER_DT = gRN_TRANSFER_DT;
	}

	public String getGRND_PROD_ID() {
		return GRND_PROD_ID;
	}

	public void setGRND_PROD_ID(String gRND_PROD_ID) {
		GRND_PROD_ID = gRND_PROD_ID;
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

	public BigDecimal getGRND_QTY() {
		return GRND_QTY;
	}

	public void setGRND_QTY(BigDecimal gRND_QTY) {
		GRND_QTY = gRND_QTY;
	}

	public BigDecimal getGRND_RATE() {
		return GRND_RATE;
	}

	public void setGRND_RATE(BigDecimal gRND_RATE) {
		GRND_RATE = gRND_RATE;
	}

	public BigDecimal getGRND_VALUE() {
		return GRND_VALUE;
	}

	public void setGRND_VALUE(BigDecimal gRND_VALUE) {
		GRND_VALUE = gRND_VALUE;
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

	public String getGRN_ins_usr_id() {
		return GRN_ins_usr_id;
	}

	public void setGRN_ins_usr_id(String gRN_ins_usr_id) {
		GRN_ins_usr_id = gRN_ins_usr_id;
	}

	public String getINS_NAME() {
		return INS_NAME;
	}

	public void setINS_NAME(String iNS_NAME) {
		INS_NAME = iNS_NAME;
	}

	public String getGRN_INS_DT() {
		return GRN_INS_DT;
	}

	public void setGRN_INS_DT(String gRN_INS_DT) {
		GRN_INS_DT = gRN_INS_DT;
	}

	public String getGRN_ins_ip_add() {
		return GRN_ins_ip_add;
	}

	public void setGRN_ins_ip_add(String gRN_ins_ip_add) {
		GRN_ins_ip_add = gRN_ins_ip_add;
	}

	public String getGRN_MOD_usr_id() {
		return GRN_MOD_usr_id;
	}

	public void setGRN_MOD_usr_id(String gRN_MOD_usr_id) {
		GRN_MOD_usr_id = gRN_MOD_usr_id;
	}

	public String getMOD_NAME() {
		return MOD_NAME;
	}

	public void setMOD_NAME(String mOD_NAME) {
		MOD_NAME = mOD_NAME;
	}

	public String getGRN_MOD_DT() {
		return GRN_MOD_DT;
	}

	public void setGRN_MOD_DT(String gRN_MOD_DT) {
		GRN_MOD_DT = gRN_MOD_DT;
	}

	public String getGRN_MOD_ip_add() {
		return GRN_MOD_ip_add;
	}

	public void setGRN_MOD_ip_add(String gRN_MOD_ip_add) {
		GRN_MOD_ip_add = gRN_MOD_ip_add;
	}

	public String getBATCH_EXPIRY_DT() {
		return BATCH_EXPIRY_DT;
	}

	public void setBATCH_EXPIRY_DT(String bATCH_EXPIRY_DT) {
		BATCH_EXPIRY_DT = bATCH_EXPIRY_DT;
	}

	public String getGRN_APPR_ACQ() {
		return GRN_APPR_ACQ;
	}

	public void setGRN_APPR_ACQ(String gRN_APPR_ACQ) {
		GRN_APPR_ACQ = gRN_APPR_ACQ;
	}

	@Override
	public String toString() {
		return "GrnAuditTrailBean [GRN_NO=" + GRN_NO + "]";
	}	
	
	
	

}