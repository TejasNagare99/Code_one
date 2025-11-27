package com.excel.bean;
public class Stk_Grn_upload_err {
	
	private String STAT;
	private String sendloc;
	private String recloc;
	private String CSPTRF_TRF_NO;
	private String CSPTRF_TRF_DATE_DISP;
	private String CSPTRF_ERR_MSG;
	private String CSPTRF_MFGDT_DISP;
	private String CSPTRF_EXPIRY_DT_DISP;
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	public String getSendloc() {
		return sendloc;
	}
	public void setSendloc(String sendloc) {
		this.sendloc = sendloc;
	}
	public String getRecloc() {
		return recloc;
	}
	public void setRecloc(String recloc) {
		this.recloc = recloc;
	}
	public String getCSPTRF_TRF_NO() {
		return CSPTRF_TRF_NO;
	}
	public void setCSPTRF_TRF_NO(String cSPTRF_TRF_NO) {
		CSPTRF_TRF_NO = cSPTRF_TRF_NO;
	}
	public String getCSPTRF_TRF_DATE_DISP() {
		return CSPTRF_TRF_DATE_DISP;
	}
	public void setCSPTRF_TRF_DATE_DISP(String cSPTRF_TRF_DATE_DISP) {
		CSPTRF_TRF_DATE_DISP = cSPTRF_TRF_DATE_DISP;
	}
	public String getCSPTRF_ERR_MSG() {
		return CSPTRF_ERR_MSG;
	}
	public void setCSPTRF_ERR_MSG(String cSPTRF_ERR_MSG) {
		CSPTRF_ERR_MSG = cSPTRF_ERR_MSG;
	}
	public String getCSPTRF_MFGDT_DISP() {
		return CSPTRF_MFGDT_DISP;
	}
	public void setCSPTRF_MFGDT_DISP(String cSPTRF_MFGDT_DISP) {
		CSPTRF_MFGDT_DISP = cSPTRF_MFGDT_DISP;
	}
	public String getCSPTRF_EXPIRY_DT_DISP() {
		return CSPTRF_EXPIRY_DT_DISP;
	}
	public void setCSPTRF_EXPIRY_DT_DISP(String cSPTRF_EXPIRY_DT_DISP) {
		CSPTRF_EXPIRY_DT_DISP = cSPTRF_EXPIRY_DT_DISP;
	}
	@Override
	public String toString() {
		return "Stk_Grn_upload_err [STAT=" + STAT + ", sendloc=" + sendloc + ", recloc=" + recloc + ", CSPTRF_TRF_NO="
				+ CSPTRF_TRF_NO + ", CSPTRF_TRF_DATE_DISP=" + CSPTRF_TRF_DATE_DISP + ", CSPTRF_ERR_MSG="
				+ CSPTRF_ERR_MSG + ", CSPTRF_MFGDT_DISP=" + CSPTRF_MFGDT_DISP + ", CSPTRF_EXPIRY_DT_DISP="
				+ CSPTRF_EXPIRY_DT_DISP + "]";
	}
	

}
