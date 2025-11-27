package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class DoctorBulkUploadBean {

	private Long divId;
	private String divnm;
	private String reqNo;
	
	private String transactionMode;
	private Long reqIdForModify;
	private String productType;
	private Long selectedFieldStaff;
	private String company;
	private String blk_usr_id;
	private String empId;
	private String blk_ip_add;
	private String blk_status;
	private String blk_csv_name;
	private Date blk_csv_gen_date;
	private String blk_csvgen_usr_id;
	private List<DoctorDetails> doctorDetails;
	//private DoctorDetails docDtl;
	private List<ProdDetails> prodqtyModel;
	private Long noOfDoctors;
	private String docMasterToBeUsed;
	
	public static class ProdDetails {
		private Long prodId;
		private Long qty;
		private String prodname;
		private String prodcode;
		
		
		public String getProdcode() {
			return prodcode;
		}
		public void setProdcode(String prodcode) {
			this.prodcode = prodcode;
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
		public String getProdname() {
			return prodname;
		}
		public void setProdname(String prodname) {
			this.prodname = prodname;
		}
	}
	
	public static class DoctorDetails{
		
		private String hcp_name;
		
		private String hcp_unique_id;
		
		private String srt_number;
		
		private Date srt_date;
		
		private String srt_remarks;
		
		private Long req_id;
		
		private String doc_status;
		
		private String req_code;
		
		private String req_name;
		
		private String allow_adress_change;
		
		private String mcl_no;
		
		private String fstaff_employee_no;
		
		
		public String getFstaff_employee_no() {
			return fstaff_employee_no;
		}

		public void setFstaff_employee_no(String fstaff_employee_no) {
			this.fstaff_employee_no = fstaff_employee_no;
		}

	
		public String getMcl_no() {
			return mcl_no;
		}

		public void setMcl_no(String mcl_no) {
			this.mcl_no = mcl_no;
		}

		

		public String getAllow_adress_change() {
			return allow_adress_change;
		}

		public void setAllow_adress_change(String allow_adress_change) {
			this.allow_adress_change = allow_adress_change;
		}

		public String getReq_code() {
			return req_code;
		}

		public void setReq_code(String req_code) {
			this.req_code = req_code;
		}

		public String getReq_name() {
			return req_name;
		}

		public void setReq_name(String req_name) {
			this.req_name = req_name;
		}

		public String getHcp_name() {
			return hcp_name;
		}

		public void setHcp_name(String hcp_name) {
			this.hcp_name = hcp_name;
		}

		public String getHcp_unique_id() {
			return hcp_unique_id;
		}

		public void setHcp_unique_id(String hcp_unique_id) {
			this.hcp_unique_id = hcp_unique_id;
		}

		public String getSrt_number() {
			return srt_number;
		}

		public void setSrt_number(String srt_number) {
			this.srt_number = srt_number;
		}

		public Date getSrt_date() {
			return srt_date;
		}

		public void setSrt_date(Date srt_date) {
			this.srt_date = srt_date;
		}

		public String getSrt_remarks() {
			return srt_remarks;
		}

		public void setSrt_remarks(String srt_remarks) {
			this.srt_remarks = srt_remarks;
		}

		public Long getReq_id() {
			return req_id;
		}

		public void setReq_id(Long req_id) {
			this.req_id = req_id;
		}

		public String getDoc_status() {
			return doc_status;
		}

		public void setDoc_status(String doc_status) {
			this.doc_status = doc_status;
		}
		
		
	}
	
	

	public String getDocMasterToBeUsed() {
		return docMasterToBeUsed;
	}

	public void setDocMasterToBeUsed(String docMasterToBeUsed) {
		this.docMasterToBeUsed = docMasterToBeUsed;
	}

	public Long getNoOfDoctors() {
		return noOfDoctors;
	}

	public void setNoOfDoctors(Long noOfDoctors) {
		this.noOfDoctors = noOfDoctors;
	}

	public String getDivnm() {
		return divnm;
	}

	public void setDivnm(String divnm) {
		this.divnm = divnm;
	}

	public Long getDivId() {
		return divId;
	}

	public void setDivId(Long divId) {
		this.divId = divId;
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

	public Long getSelectedFieldStaff() {
		return selectedFieldStaff;
	}

	public void setSelectedFieldStaff(Long selectedFieldStaff) {
		this.selectedFieldStaff = selectedFieldStaff;
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

	public List<DoctorDetails> getDoctorDetails() {
		return doctorDetails;
	}

	public void setDoctorDetails(List<DoctorDetails> doctorDetails) {
		this.doctorDetails = doctorDetails;
	}



	public List<ProdDetails> getProdqtyModel() {
		return prodqtyModel;
	}

	public void setProdqtyModel(List<ProdDetails> prodqtyModel) {
		this.prodqtyModel = prodqtyModel;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}


	
	
}
