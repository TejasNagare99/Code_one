package com.excel.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.excel.bean.DoctorBulkUploadBean.ProdDetails;

public class StockistBulkUploadBean {

	private Long divId;
	private String divnm;
	private String reqNo;
	
	private Long locationId;
	
	private String transactionMode;
	private Long reqIdForModify;
	private String productType;
	private Long fieldStaffId;
	private String company;
	private String blk_usr_id;
	private String empId;
	private String blk_ip_add;
	private String blk_status;
	private String blk_csv_name;
	private Date blk_csv_gen_date;
	private String blk_csvgen_usr_id;
	
	private List<stockistDetails> stkDetails;
	private List<ProdDetails> prodqtyModel;
	private Long noOfStockist;
	private String stkMasterToBeUsed;
	
	public static class stockistDetails{
		private Long cust_id;
		private String stk_name;
		private String allow_adress_change;
		private String erp_cust_cd;
		private String org_ent_code;
		private String sap_inv_number;
		private String sap_inv_date;
		private String sap_inv_remarks;
		public Long getCust_id() {
			return cust_id;
		}
		public void setCust_id(Long cust_id) {
			this.cust_id = cust_id;
		}
		public String getStk_name() {
			return stk_name;
		}
		public void setStk_name(String stk_name) {
			this.stk_name = stk_name;
		}
		public String getAllow_adress_change() {
			return allow_adress_change;
		}
		public void setAllow_adress_change(String allow_adress_change) {
			this.allow_adress_change = allow_adress_change;
		}
		public String getErp_cust_cd() {
			return erp_cust_cd;
		}
		public void setErp_cust_cd(String erp_cust_cd) {
			this.erp_cust_cd = erp_cust_cd;
		}
		public String getOrg_ent_code() {
			return org_ent_code;
		}
		public void setOrg_ent_code(String org_ent_code) {
			this.org_ent_code = org_ent_code;
		}
		public String getSap_inv_number() {
			return sap_inv_number;
		}
		public void setSap_inv_number(String sap_inv_number) {
			this.sap_inv_number = sap_inv_number;
		}
		public String getSap_inv_date() {
			return sap_inv_date;
		}
		public void setSap_inv_date(String sap_inv_date) {
			this.sap_inv_date = sap_inv_date;
		}
		public String getSap_inv_remarks() {
			return sap_inv_remarks;
		}
		public void setSap_inv_remarks(String sap_inv_remarks) {
			this.sap_inv_remarks = sap_inv_remarks;
		}
	}

	public Long getDivId() {
		return divId;
	}

	public void setDivId(Long divId) {
		this.divId = divId;
	}

	public String getDivnm() {
		return divnm;
	}

	public void setDivnm(String divnm) {
		this.divnm = divnm;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
	}

	public Long getReqIdForModify() {
		return reqIdForModify;
	}

	public void setReqIdForModify(Long reqIdForModify) {
		this.reqIdForModify = reqIdForModify;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getFieldStaffId() {
		return fieldStaffId;
	}

	public void setFieldStaffId(Long fieldStaffId) {
		this.fieldStaffId = fieldStaffId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBlk_usr_id() {
		return blk_usr_id;
	}

	public void setBlk_usr_id(String blk_usr_id) {
		this.blk_usr_id = blk_usr_id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getBlk_ip_add() {
		return blk_ip_add;
	}

	public void setBlk_ip_add(String blk_ip_add) {
		this.blk_ip_add = blk_ip_add;
	}

	public String getBlk_status() {
		return blk_status;
	}

	public void setBlk_status(String blk_status) {
		this.blk_status = blk_status;
	}

	public String getBlk_csv_name() {
		return blk_csv_name;
	}

	public void setBlk_csv_name(String blk_csv_name) {
		this.blk_csv_name = blk_csv_name;
	}

	public Date getBlk_csv_gen_date() {
		return blk_csv_gen_date;
	}

	public void setBlk_csv_gen_date(Date blk_csv_gen_date) {
		this.blk_csv_gen_date = blk_csv_gen_date;
	}

	public String getBlk_csvgen_usr_id() {
		return blk_csvgen_usr_id;
	}

	public void setBlk_csvgen_usr_id(String blk_csvgen_usr_id) {
		this.blk_csvgen_usr_id = blk_csvgen_usr_id;
	}

	public List<stockistDetails> getStkDetails() {
		return stkDetails;
	}

	public void setStkDetails(List<stockistDetails> doctorDetails) {
		this.stkDetails = doctorDetails;
	}

	public List<ProdDetails> getProdqtyModel() {
		return prodqtyModel;
	}

	public void setProdqtyModel(List<ProdDetails> prodqtyModel) {
		this.prodqtyModel = prodqtyModel;
	}

	public Long getNoOfStockist() {
		return noOfStockist;
	}

	public void setNoOfStockist(Long noOfStockist) {
		this.noOfStockist = noOfStockist;
	}

	public String getStkMasterToBeUsed() {
		return stkMasterToBeUsed;
	}

	public void setStkMasterToBeUsed(String stkMasterToBeUsed) {
		this.stkMasterToBeUsed = stkMasterToBeUsed;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	
}
