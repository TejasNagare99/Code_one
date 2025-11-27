package com.excel.bean;

import java.util.List;

public class DoctorAllocBulkUpload {
	private List<prodDetail> prodqtyModel;
	private prodDetail dtl ;
	
	private String company;
	private String empId;
	private String ip_adress;
	
	public static class prodDetail{
		private Long prodId;
		private Long qty;
		private String prodname;
		
		public String getProdname() {
			return prodname;
		}
		public void setProdname(String prodname) {
			this.prodname = prodname;
		}
		public Long getProdId() {
			return prodId;
		}
		public void setProdId(Long prodId) {
			this.prodId = prodId;
		}
		public Long getQty() {
			return qty;
		}
		public void setQty(Long qty) {
			this.qty = qty;
		}
		
		
	}

	public String getIp_adress() {
		return ip_adress;
	}

	public void setIp_adress(String ip_adress) {
		this.ip_adress = ip_adress;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<prodDetail> getProdqtyModel() {
		return prodqtyModel;
	}

	public void setProdqtyModel(List<prodDetail> prodqtyModel) {
		this.prodqtyModel = prodqtyModel;
	}

	public DoctorAllocBulkUpload() {
		setDtl(new prodDetail());
	}

	public prodDetail getDtl() {
		return dtl;
	}

	public void setDtl(prodDetail dtl) {
		this.dtl = dtl;
	}


	
	
}
