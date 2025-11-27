package com.excel.bean;

import java.util.Date;

public class Articele_Scheme_Approved_Data {


	private String APPR_LEVEL;
	private Date NEW_MOD_DATE;
	private String APPR_NM;
	
	private String new_mod_datestring ;
	
	
	public String getNew_mod_datestring() {
		return new_mod_datestring;
	}
	public void setNew_mod_datestring(String new_mod_datestring) {
		this.new_mod_datestring = new_mod_datestring;
	}
	public Date getNEW_MOD_DATE() {
		return NEW_MOD_DATE;
	}
	public void setNEW_MOD_DATE(Date nEW_MOD_DATE) {
		NEW_MOD_DATE = nEW_MOD_DATE;
	}
	public String getAPPR_LEVEL() {
		return APPR_LEVEL;
	}
	public void setAPPR_LEVEL(String aPPR_LEVEL) {
		APPR_LEVEL = aPPR_LEVEL;
	}

	public String getAPPR_NM() {
		return APPR_NM;
	}
	public void setAPPR_NM(String aPPR_NM) {
		APPR_NM = aPPR_NM;
	}
	@Override
	public String toString() {
		return "Articele_Scheme_Approved_Data [APPR_LEVEL=" + APPR_LEVEL + ", NEW_MOD_DATE=" + NEW_MOD_DATE
				+ ", APPR_NM=" + APPR_NM + "]";
	}
	
	
	

}
