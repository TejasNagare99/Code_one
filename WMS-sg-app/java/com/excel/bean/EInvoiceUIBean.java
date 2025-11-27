package com.excel.bean;

import org.springframework.web.bind.annotation.RequestParam;

public class EInvoiceUIBean {
	private String currentyear;
	private String finyearid;
	private String loc_id;
	private String divisionid;
	private String generate_eway;
	private String fromchallan;
	private String tochallan;
	
	private String trnid;
	private String trnewaybill;
	
	private String finyearflag;
	
	private String excellon_auth_token;
	private String gst_user_auth_token;
	private String decryptedSEK;
	private String encryptedSEK;
	
	
	
	public String getEncryptedSEK() {
		return encryptedSEK;
	}
	public void setEncryptedSEK(String encryptedSEK) {
		this.encryptedSEK = encryptedSEK;
	}
	public String getExcellon_auth_token() {
		return excellon_auth_token;
	}
	public void setExcellon_auth_token(String excellon_auth_token) {
		this.excellon_auth_token = excellon_auth_token;
	}
	public String getGst_user_auth_token() {
		return gst_user_auth_token;
	}
	public void setGst_user_auth_token(String gst_user_auth_token) {
		this.gst_user_auth_token = gst_user_auth_token;
	}
	public String getDecryptedSEK() {
		return decryptedSEK;
	}
	public void setDecryptedSEK(String decryptedSEK) {
		this.decryptedSEK = decryptedSEK;
	}
	public String getFinyearflag() {
		return finyearflag;
	}
	public void setFinyearflag(String finyearflag) {
		this.finyearflag = finyearflag;
	}
	public String getTrnid() {
		return trnid;
	}
	public void setTrnid(String trnid) {
		this.trnid = trnid;
	}
	public String getTrnewaybill() {
		return trnewaybill;
	}
	public void setTrnewaybill(String trnewaybill) {
		this.trnewaybill = trnewaybill;
	}
	public String getCurrentyear() {
		return currentyear;
	}
	public void setCurrentyear(String currentyear) {
		this.currentyear = currentyear;
	}
	public String getFinyearid() {
		return finyearid;
	}
	public void setFinyearid(String finyearid) {
		this.finyearid = finyearid;
	}
	public String getLoc_id() {
		return loc_id;
	}
	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}
	public String getDivisionid() {
		return divisionid;
	}
	public void setDivisionid(String divisionid) {
		this.divisionid = divisionid;
	}
	public String getGenerate_eway() {
		return generate_eway;
	}
	public void setGenerate_eway(String generate_eway) {
		this.generate_eway = generate_eway;
	}
	public String getFromchallan() {
		return fromchallan;
	}
	public void setFromchallan(String fromchallan) {
		this.fromchallan = fromchallan;
	}
	public String getTochallan() {
		return tochallan;
	}
	public void setTochallan(String tochallan) {
		this.tochallan = tochallan;
	}

}
