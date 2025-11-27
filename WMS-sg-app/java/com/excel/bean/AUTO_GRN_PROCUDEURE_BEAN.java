package com.excel.bean;

public class AUTO_GRN_PROCUDEURE_BEAN {

	private String GRN_NO;
	private String grn_company;
	private String rec_loc_id;
	private String fin_year;
	private String period_code;
	private String div_id;
	public String getQty() {
		return Qty;
	}
	public void setQty(String qty) {
		Qty = qty;
	}
	public String getRate() {
		return Rate;
	}
	public void setRate(String rate) {
		Rate = rate;
	}
	private String smp_prod_cd;
	private String smp_prod_name;
	private String Batch_No;
	private String Qty;
	private String  Rate;
	private String user_id;
	private String grn_ins_time;
	private String user_ip;
	private String status;
	public String getGRN_NO() {
		return GRN_NO;
	}
	public void setGRN_NO(String gRN_NO) {
		GRN_NO = gRN_NO;
	}
	public String getGrn_company() {
		return grn_company;
	}
	public void setGrn_company(String grn_company) {
		this.grn_company = grn_company;
	}
	public String getRec_loc_id() {
		return rec_loc_id;
	}
	public void setRec_loc_id(String rec_loc_id) {
		this.rec_loc_id = rec_loc_id;
	}
	public String getFin_year() {
		return fin_year;
	}
	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}
	public String getPeriod_code() {
		return period_code;
	}
	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}
	public String getDiv_id() {
		return div_id;
	}
	public void setDiv_id(String div_id) {
		this.div_id = div_id;
	}
	public String getSmp_prod_cd() {
		return smp_prod_cd;
	}
	public void setSmp_prod_cd(String smp_prod_cd) {
		this.smp_prod_cd = smp_prod_cd;
	}
	public String getSmp_prod_name() {
		return smp_prod_name;
	}
	public void setSmp_prod_name(String smp_prod_name) {
		this.smp_prod_name = smp_prod_name;
	}
	public String getBatch_No() {
		return Batch_No;
	}
	public void setBatch_No(String batch_No) {
		Batch_No = batch_No;
	}

	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getGrn_ins_time() {
		return grn_ins_time;
	}
	public void setGrn_ins_time(String grn_ins_time) {
		this.grn_ins_time = grn_ins_time;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "AUTO_GRN_PROCUDEURE_BEAN [GRN_NO=" + GRN_NO + ", grn_company=" + grn_company + ", rec_loc_id="
				+ rec_loc_id + ", fin_year=" + fin_year + ", period_code=" + period_code + ", div_id=" + div_id
				+ ", smp_prod_cd=" + smp_prod_cd + ", smp_prod_name=" + smp_prod_name + ", Batch_No=" + Batch_No
				+ ", Qty=" + Qty + ", Rate=" + Rate + ", user_id=" + user_id + ", grn_ins_time=" + grn_ins_time
				+ ", user_ip=" + user_ip + ", status=" + status + "]";
	}
	
	
	
}
