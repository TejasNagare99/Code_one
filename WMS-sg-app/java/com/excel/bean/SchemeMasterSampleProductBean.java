package com.excel.bean;

import java.math.BigDecimal;

public class SchemeMasterSampleProductBean {

	private String  SMP_PROD_ID;
 	 private String SMP_PROD_NAME;
 	 private String SMP_ERP_PROD_CD;
 	 private BigDecimal SMP_RATE;
 	 
 	 
 	 
 	 
	public BigDecimal getSMP_RATE() {
		return SMP_RATE;
	}
	public void setSMP_RATE(BigDecimal sMP_RATE) {
		SMP_RATE = sMP_RATE;
	}
	public String getSMP_ERP_PROD_CD() {
		return SMP_ERP_PROD_CD;
	}
	public void setSMP_ERP_PROD_CD(String sMP_ERP_PROD_CD) {
		SMP_ERP_PROD_CD = sMP_ERP_PROD_CD;
	}
	public String getSMP_PROD_ID() {
		return SMP_PROD_ID;
	}
	public void setSMP_PROD_ID(String sMP_PROD_ID) {
		SMP_PROD_ID = sMP_PROD_ID;
	}
	public String getSMP_PROD_NAME() {
		return SMP_PROD_NAME;
	}
	public void setSMP_PROD_NAME(String sMP_PROD_NAME) {
		SMP_PROD_NAME = sMP_PROD_NAME;
	}
	@Override
	public String toString() {
		return "SchemeMasterSampleProductBean [SMP_PROD_ID=" + SMP_PROD_ID + ", SMP_PROD_NAME=" + SMP_PROD_NAME
				+ ", SMP_ERP_PROD_CD=" + SMP_ERP_PROD_CD + "]";
	}
	
 	 
}
