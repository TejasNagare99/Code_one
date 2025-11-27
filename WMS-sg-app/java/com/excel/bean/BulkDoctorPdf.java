package com.excel.bean;

import java.util.List;

public class BulkDoctorPdf {

	
	private String bulk_unique_id;
	private String requester_id;
	private String sg_employee_no;
	private String address_change;
	private String hcp_unique_id;
	private String hcp_name;
	private String mcl_number;
	private String srt_number;
	private String srt_date;
	private String srt_remarks;
	private String req_remarks;
	private String company;
	private String division;
	private String request_no;
	private String request_date;
	private String fs_code;
	private String fs_name;
	private String Observer1_email;
	private String Observer2_email;
	private String Observer3_email;
	private String Observer4_email;
	private String Observer5_email;
	private String sg_territory_id;
	private String territory_name;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String city;
	private String pin_code;
	private String mobile;
	private String hcp_email;	
	private String mdm_employee_no;
	private String sg_terr_code;
	private String mdm_terr_code;
	
	private List<ProductDetails> productDetails;
	
	
	public static class ProductDetails{
		
		private String product_name;
		private String qty;
		public String getProduct_name() {
			return product_name;
		}
		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}
		public String getQty() {
			return qty;
		}
		public void setQty(String qty) {
			this.qty = qty;
		}
		@Override
		public String toString() {
			return ""
					+ "\n ProductDetails [product_name=" + product_name + ", qty=" + qty + "]";
		}
		
		
	}
	
	
	public String getBulk_unique_id() {
		return bulk_unique_id;
	}
	public void setBulk_unique_id(String bulk_unique_id) {
		this.bulk_unique_id = bulk_unique_id;
	}
	public String getRequester_id() {
		return requester_id;
	}
	public void setRequester_id(String requester_id) {
		this.requester_id = requester_id;
	}
	public String getSg_employee_no() {
		return sg_employee_no;
	}
	public void setSg_employee_no(String sg_employee_no) {
		this.sg_employee_no = sg_employee_no;
	}
	public String getAddress_change() {
		return address_change;
	}
	public void setAddress_change(String address_change) {
		this.address_change = address_change;
	}
	public String getHcp_unique_id() {
		return hcp_unique_id;
	}
	public void setHcp_unique_id(String hcp_unique_id) {
		this.hcp_unique_id = hcp_unique_id;
	}
	public String getHcp_name() {
		return hcp_name;
	}
	public void setHcp_name(String hcp_name) {
		this.hcp_name = hcp_name;
	}
	public String getMcl_number() {
		return mcl_number;
	}
	public void setMcl_number(String mcl_number) {
		this.mcl_number = mcl_number;
	}
	public String getSrt_number() {
		return srt_number;
	}
	public void setSrt_number(String srt_number) {
		this.srt_number = srt_number;
	}
	public String getSrt_date() {
		return srt_date;
	}
	public void setSrt_date(String srt_date) {
		this.srt_date = srt_date;
	}
	public String getSrt_remarks() {
		return srt_remarks;
	}
	public void setSrt_remarks(String srt_remarks) {
		this.srt_remarks = srt_remarks;
	}
	public String getReq_remarks() {
		return req_remarks;
	}
	public void setReq_remarks(String req_remarks) {
		this.req_remarks = req_remarks;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getRequest_no() {
		return request_no;
	}
	public void setRequest_no(String request_no) {
		this.request_no = request_no;
	}
	public String getRequest_date() {
		return request_date;
	}
	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}
	public String getFs_code() {
		return fs_code;
	}
	public void setFs_code(String fs_code) {
		this.fs_code = fs_code;
	}
	public String getFs_name() {
		return fs_name;
	}
	public void setFs_name(String fs_name) {
		this.fs_name = fs_name;
	}
	public String getObserver1_email() {
		return Observer1_email;
	}
	public void setObserver1_email(String observer1_email) {
		Observer1_email = observer1_email;
	}
	public String getObserver2_email() {
		return Observer2_email;
	}
	public void setObserver2_email(String observer2_email) {
		Observer2_email = observer2_email;
	}
	public String getObserver3_email() {
		return Observer3_email;
	}
	public void setObserver3_email(String observer3_email) {
		Observer3_email = observer3_email;
	}
	public String getObserver4_email() {
		return Observer4_email;
	}
	public void setObserver4_email(String observer4_email) {
		Observer4_email = observer4_email;
	}
	public String getObserver5_email() {
		return Observer5_email;
	}
	public void setObserver5_email(String observer5_email) {
		Observer5_email = observer5_email;
	}
	public String getSg_territory_id() {
		return sg_territory_id;
	}
	public void setSg_territory_id(String sg_territory_id) {
		this.sg_territory_id = sg_territory_id;
	}
	public String getTerritory_name() {
		return territory_name;
	}
	public void setTerritory_name(String territory_name) {
		this.territory_name = territory_name;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPin_code() {
		return pin_code;
	}
	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHcp_email() {
		return hcp_email;
	}
	public void setHcp_email(String hcp_email) {
		this.hcp_email = hcp_email;
	}
	public String getMdm_employee_no() {
		return mdm_employee_no;
	}
	public void setMdm_employee_no(String mdm_employee_no) {
		this.mdm_employee_no = mdm_employee_no;
	}
	public String getSg_terr_code() {
		return sg_terr_code;
	}
	public void setSg_terr_code(String sg_terr_code) {
		this.sg_terr_code = sg_terr_code;
	}
	public String getMdm_terr_code() {
		return mdm_terr_code;
	}
	public void setMdm_terr_code(String mdm_terr_code) {
		this.mdm_terr_code = mdm_terr_code;
	}
	
	
	
	public List<ProductDetails> getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(List<ProductDetails> productDetails) {
		this.productDetails = productDetails;
	}
	@Override
	public String toString() {
		return "BulkDoctorPdf [bulk_unique_id=" + bulk_unique_id + ", requester_id=" + requester_id
				+ ", sg_employee_no=" + sg_employee_no + ", address_change=" + address_change + ", hcp_unique_id="
				+ hcp_unique_id + ", hcp_name=" + hcp_name + ", mcl_number=" + mcl_number + ", srt_number=" + srt_number
				+ ", srt_date=" + srt_date + ", srt_remarks=" + srt_remarks + ", req_remarks=" + req_remarks
				+ ", company=" + company + ", division=" + division + ", request_no=" + request_no + ", request_date="
				+ request_date + ", fs_code=" + fs_code + ", fs_name=" + fs_name + ", Observer1_email="
				+ Observer1_email + ", Observer2_email=" + Observer2_email + ", Observer3_email=" + Observer3_email
				+ ", Observer4_email=" + Observer4_email + ", Observer5_email=" + Observer5_email + ", sg_territory_id="
				+ sg_territory_id + ", territory_name=" + territory_name + ", address1=" + address1 + ", address2="
				+ address2 + ", address3=" + address3 + ", address4=" + address4 + ", city=" + city + ", pin_code="
				+ pin_code + ", mobile=" + mobile + ", hcp_email=" + hcp_email + ", mdm_employee_no=" + mdm_employee_no
				+ ", sg_terr_code=" + sg_terr_code + ", mdm_terr_code=" + mdm_terr_code + ", productDetails="
				+ productDetails.toString() + "]";
	}
	
	
	
	
	
	
}
