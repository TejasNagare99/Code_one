package com.excel.bean;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class StkBlkCsvUpldBean {

	@NotNull(message = "STK BLK ID cannot be null")
	@Min(value = 0, message = "STK BLK ID cannot be zero")
	private Long stk_bulk_id;
	
	@NotNull(message = "CUST CD not valid")
	@Min(value = 0, message = "CUST CD not valid")
	private Long custId;
	
	@NotNull(message = "Request No cannot be null")
	@NotBlank(message = "Request No cannot be blank")
	private String blk_stk_req_no;
	
	@NotNull(message = "Request Date cannot be null")
	@NotBlank(message = "Request Date cannot be blank")
	@Pattern(regexp = "\\d{2}\\/\\d{2}\\/\\d{4}" , message = "Invalid date format for STK_REQ_DATE")
	private String blk_stk_req_date;
	
	@NotNull(message = "SG_CUST_CD cannot be null")
	@NotBlank(message = "SG_CUST_CD cannot be blank")
	private String sg_cust_cd;
	
	@NotNull(message = "ERP_CUST_CD cannot be null")
	@NotBlank(message = "ERP_CUST_CD cannot be blank")
	private String erp_cust_cd;
	
	@NotNull(message = "NAME_BILLTO cannot be null")
	@NotBlank(message = "NAME_BILLTO cannot be blank")
	private String cust_name_billto;
	
	@NotNull(message = "ENT_CUST_CD cannot be null")
	@NotBlank(message = "ENT_CUST_CD cannot be blank")
	private String ent_cust_cd;
	
	@NotNull(message = "ADDRESS_1_SHIPTO cannot be null")
	@NotBlank(message = "ADDRESS_1_SHIPTO cannot be blank")
	private String address_1_shipto;
	
	
	private String address_2_shipto;
	
	private String address_3_shipto;
	
	private String address_4_shipto;
	
	@NotNull(message = "DESTINATION_SHIPTO cannot be null")
	@NotBlank(message = "DESTINATION_SHIPTO cannot be blank")
	private String destination_shipto;
	
	@NotNull(message = "PIN_CODE cannot be null")
	@NotBlank(message = "PIN_CODE cannot be blank")
	private String stk_pin_code;
	
	@NotNull(message = "MOBILE cannot be null")
	@NotBlank(message = "MOBILE cannot be blank")
	private String stk_mobile;
	
	@Email(message = "STK_EMAIL should be valid")
	@NotBlank(message = "STK_EMAIL cannot be blank")
	private String stk_email;
	
	private String observer1_email;
	
	private String observer2_email;
	
	private String observer3_email;
	
	private String observer4_email;
	
	private String observer5_email;
	
	@NotNull(message = "SERV_REQ_NO cannot be null")
	@NotBlank(message = "SERV_REQ_NO cannot be blank")
	private String serv_req_no;
	
	@NotNull(message = "SAP_INV_NO cannot be null")
	@NotBlank(message = "SAP_INV_NO cannot be blank")
	private String sap_inv_no;
	
	@NotNull(message = "SAP_INV_DATE cannot be null")
	@NotBlank(message = "SAP_INV_DATE cannot be blank")
	@Pattern(regexp = "\\d{2}\\/\\d{2}\\/\\d{4}" , message = "Invalid date format for SAP_INV_DATE")
	private String sap_inv_date;
	
	@NotNull(message = "SAP_SCHM_REMARKS cannot be null")
	@NotBlank(message = "SAP_SCHM_REMARKS cannot be blank")
	private String sap_schm_remarks;
	
	
	private Long prod_id1;private BigDecimal prod_qty1;
	private Long prod_id2;private BigDecimal prod_qty2;
	private Long prod_id3;private BigDecimal prod_qty3;
	private Long prod_id4;private BigDecimal prod_qty4;
	private Long prod_id5;private BigDecimal prod_qty5;
	private Long prod_id6;private BigDecimal prod_qty6;
	private Long prod_id7;private BigDecimal prod_qty7;
	private Long prod_id8;private BigDecimal prod_qty8;
	private Long prod_id9;private BigDecimal prod_qty9;
	private Long prod_id10;private BigDecimal prod_qty10;
	

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getProd_id1() {
		return prod_id1;
	}

	public void setProd_id1(Long prod_id1) {
		this.prod_id1 = prod_id1;
	}

	public BigDecimal getProd_qty1() {
		return prod_qty1;
	}

	public void setProd_qty1(BigDecimal prod_qty1) {
		this.prod_qty1 = prod_qty1;
	}

	public Long getProd_id2() {
		return prod_id2;
	}

	public void setProd_id2(Long prod_id2) {
		this.prod_id2 = prod_id2;
	}

	public BigDecimal getProd_qty2() {
		return prod_qty2;
	}

	public void setProd_qty2(BigDecimal prod_qty2) {
		this.prod_qty2 = prod_qty2;
	}

	public Long getProd_id3() {
		return prod_id3;
	}

	public void setProd_id3(Long prod_id3) {
		this.prod_id3 = prod_id3;
	}

	public BigDecimal getProd_qty3() {
		return prod_qty3;
	}

	public void setProd_qty3(BigDecimal prod_qty3) {
		this.prod_qty3 = prod_qty3;
	}

	public Long getProd_id4() {
		return prod_id4;
	}

	public void setProd_id4(Long prod_id4) {
		this.prod_id4 = prod_id4;
	}

	public BigDecimal getProd_qty4() {
		return prod_qty4;
	}

	public void setProd_qty4(BigDecimal prod_qty4) {
		this.prod_qty4 = prod_qty4;
	}

	public Long getProd_id5() {
		return prod_id5;
	}

	public void setProd_id5(Long prod_id5) {
		this.prod_id5 = prod_id5;
	}

	public BigDecimal getProd_qty5() {
		return prod_qty5;
	}

	public void setProd_qty5(BigDecimal prod_qty5) {
		this.prod_qty5 = prod_qty5;
	}

	public Long getProd_id6() {
		return prod_id6;
	}

	public void setProd_id6(Long prod_id6) {
		this.prod_id6 = prod_id6;
	}

	public BigDecimal getProd_qty6() {
		return prod_qty6;
	}

	public void setProd_qty6(BigDecimal prod_qty6) {
		this.prod_qty6 = prod_qty6;
	}

	public Long getProd_id7() {
		return prod_id7;
	}

	public void setProd_id7(Long prod_id7) {
		this.prod_id7 = prod_id7;
	}

	public BigDecimal getProd_qty7() {
		return prod_qty7;
	}

	public void setProd_qty7(BigDecimal prod_qty7) {
		this.prod_qty7 = prod_qty7;
	}

	public Long getProd_id8() {
		return prod_id8;
	}

	public void setProd_id8(Long prod_id8) {
		this.prod_id8 = prod_id8;
	}

	public BigDecimal getProd_qty8() {
		return prod_qty8;
	}

	public void setProd_qty8(BigDecimal prod_qty8) {
		this.prod_qty8 = prod_qty8;
	}

	public Long getProd_id9() {
		return prod_id9;
	}

	public void setProd_id9(Long prod_id9) {
		this.prod_id9 = prod_id9;
	}

	public BigDecimal getProd_qty9() {
		return prod_qty9;
	}

	public void setProd_qty9(BigDecimal prod_qty9) {
		this.prod_qty9 = prod_qty9;
	}

	public Long getProd_id10() {
		return prod_id10;
	}

	public void setProd_id10(Long prod_id10) {
		this.prod_id10 = prod_id10;
	}

	public BigDecimal getProd_qty10() {
		return prod_qty10;
	}

	public void setProd_qty10(BigDecimal prod_qty10) {
		this.prod_qty10 = prod_qty10;
	}

	public Long getStk_bulk_id() {
		return stk_bulk_id;
	}

	public void setStk_bulk_id(Long stk_bulk_id) {
		this.stk_bulk_id = stk_bulk_id;
	}

	public String getBlk_stk_req_no() {
		return blk_stk_req_no;
	}

	public void setBlk_stk_req_no(String blk_stk_req_no) {
		this.blk_stk_req_no = blk_stk_req_no;
	}

	public String getBlk_stk_req_date() {
		return blk_stk_req_date;
	}

	public void setBlk_stk_req_date(String blk_stk_req_date) {
		this.blk_stk_req_date = blk_stk_req_date;
	}

	public String getSg_cust_cd() {
		return sg_cust_cd;
	}

	public void setSg_cust_cd(String sg_cust_cd) {
		this.sg_cust_cd = sg_cust_cd;
	}

	public String getErp_cust_cd() {
		return erp_cust_cd;
	}

	public void setErp_cust_cd(String erp_cust_cd) {
		this.erp_cust_cd = erp_cust_cd;
	}

	public String getCust_name_billto() {
		return cust_name_billto;
	}

	public void setCust_name_billto(String cust_name_billto) {
		this.cust_name_billto = cust_name_billto;
	}

	public String getEnt_cust_cd() {
		return ent_cust_cd;
	}

	public void setEnt_cust_cd(String ent_cust_cd) {
		this.ent_cust_cd = ent_cust_cd;
	}

	public String getAddress_1_shipto() {
		return address_1_shipto;
	}

	public void setAddress_1_shipto(String address_1_shipto) {
		this.address_1_shipto = address_1_shipto;
	}

	public String getAddress_2_shipto() {
		return address_2_shipto;
	}

	public void setAddress_2_shipto(String address_2_shipto) {
		this.address_2_shipto = address_2_shipto;
	}

	public String getAddress_3_shipto() {
		return address_3_shipto;
	}

	public void setAddress_3_shipto(String address_3_shipto) {
		this.address_3_shipto = address_3_shipto;
	}

	public String getAddress_4_shipto() {
		return address_4_shipto;
	}

	public void setAddress_4_shipto(String address_4_shipto) {
		this.address_4_shipto = address_4_shipto;
	}

	public String getDestination_shipto() {
		return destination_shipto;
	}

	public void setDestination_shipto(String destination_shipto) {
		this.destination_shipto = destination_shipto;
	}

	public String getStk_pin_code() {
		return stk_pin_code;
	}

	public void setStk_pin_code(String stk_pin_code) {
		this.stk_pin_code = stk_pin_code;
	}

	public String getStk_mobile() {
		return stk_mobile;
	}

	public void setStk_mobile(String stk_mobile) {
		this.stk_mobile = stk_mobile;
	}

	public String getStk_email() {
		return stk_email;
	}

	public void setStk_email(String stk_email) {
		this.stk_email = stk_email;
	}

	public String getObserver1_email() {
		return observer1_email;
	}

	public void setObserver1_email(String observer1_email) {
		this.observer1_email = observer1_email;
	}

	public String getObserver2_email() {
		return observer2_email;
	}

	public void setObserver2_email(String observer2_email) {
		this.observer2_email = observer2_email;
	}

	public String getObserver3_email() {
		return observer3_email;
	}

	public void setObserver3_email(String observer3_email) {
		this.observer3_email = observer3_email;
	}

	public String getObserver4_email() {
		return observer4_email;
	}

	public void setObserver4_email(String observer4_email) {
		this.observer4_email = observer4_email;
	}

	public String getObserver5_email() {
		return observer5_email;
	}

	public void setObserver5_email(String observer5_email) {
		this.observer5_email = observer5_email;
	}

	public String getServ_req_no() {
		return serv_req_no;
	}

	public void setServ_req_no(String serv_req_no) {
		this.serv_req_no = serv_req_no;
	}

	public String getSap_inv_no() {
		return sap_inv_no;
	}

	public void setSap_inv_no(String sap_inv_no) {
		this.sap_inv_no = sap_inv_no;
	}

	public String getSap_inv_date() {
		return sap_inv_date;
	}

	public void setSap_inv_date(String sap_inv_date) {
		this.sap_inv_date = sap_inv_date;
	}

	public String getSap_schm_remarks() {
		return sap_schm_remarks;
	}

	public void setSap_schm_remarks(String sap_schm_remarks) {
		this.sap_schm_remarks = sap_schm_remarks;
	}

	@Override
	public String toString() {
		return "StkBlkCsvUpldBean [stk_bulk_id=" + stk_bulk_id + ", blk_stk_req_no=" + blk_stk_req_no
				+ ", blk_stk_req_date=" + blk_stk_req_date + ", sg_cust_cd=" + sg_cust_cd + ", erp_cust_cd="
				+ erp_cust_cd + ", cust_name_billto=" + cust_name_billto + ", ent_cust_cd=" + ent_cust_cd
				+ ", address_1_shipto=" + address_1_shipto + ", address_2_shipto=" + address_2_shipto
				+ ", address_3_shipto=" + address_3_shipto + ", address_4_shipto=" + address_4_shipto
				+ ", destination_shipto=" + destination_shipto + ", stk_pin_code=" + stk_pin_code + ", stk_mobile="
				+ stk_mobile + ", stk_email=" + stk_email + ", observer1_email=" + observer1_email
				+ ", observer2_email=" + observer2_email + ", observer3_email=" + observer3_email + ", observer4_email="
				+ observer4_email + ", observer5_email=" + observer5_email + ", serv_req_no=" + serv_req_no
				+ ", sap_inv_no=" + sap_inv_no + ", sap_inv_date=" + sap_inv_date + ", sap_schm_remarks="
				+ sap_schm_remarks + ", prod_id1=" + prod_id1 + ", prod_qty1=" + prod_qty1 + ", prod_id2=" + prod_id2
				+ ", prod_qty2=" + prod_qty2 + ", prod_id3=" + prod_id3 + ", prod_qty3=" + prod_qty3 + ", prod_id4="
				+ prod_id4 + ", prod_qty4=" + prod_qty4 + ", prod_id5=" + prod_id5 + ", prod_qty5=" + prod_qty5
				+ ", prod_id6=" + prod_id6 + ", prod_qty6=" + prod_qty6 + ", prod_id7=" + prod_id7 + ", prod_qty7="
				+ prod_qty7 + ", prod_id8=" + prod_id8 + ", prod_qty8=" + prod_qty8 + ", prod_id9=" + prod_id9
				+ ", prod_qty9=" + prod_qty9 + ", prod_id10=" + prod_id10 + ", prod_qty10=" + prod_qty10 + "]";
	}	
}
