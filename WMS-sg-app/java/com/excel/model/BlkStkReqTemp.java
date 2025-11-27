package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BLK_STK_REQ_TEMP")
public class BlkStkReqTemp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BLK_STK_REQ_TEMP_ID")
	private Long blk_stk_req_temp_id;
	@Column(name = "BLK_STK_REQ_ID")
	private Long blk_stk_req_id;
	@Column(name = "BLK_STK_REQ_DATE")
	private Date blk_stk_req_date;
	@Column(name = "FIN_YEAR_ID")
	private String fin_year_id;
	@Column(name = "COMPANY")
	private String company;
	@Column(name = "BLK_CSV_NAME")
	private String blk_csv_name;
	@Column(name = "BLK_STK_REQ_NO")
	private String blk_stk_req_no;
	@Column(name = "BLK_UPL_DATE")
	private Date blk_upl_date;
	@Column(name = "BLK_UPL_USER")
	private String blk_upl_user;
	@Column(name = "REQUESTOR_ID")
	private Long requestor_id;
	@Column(name = "CUST_ID")
	private Long cust_id;
	@Column(name = "CUST_CD")
	private String cust_cd;
	@Column(name = "ERP_CUST_CD")
	private String erp_cust_cd;
	@Column(name = "ALLOC_REQ_HDR_ID")
	private Long alloc_req_hdr_id;
	@Column(name = "CUST_NAME")
	private String cust_name;
	@Column(name = "SAP_INV_NO")
	private String sap_inv_no;
	@Column(name = "SAP_INV_DATE")
	private Date sap_inv_date;
	@Column(name = "SAP_SCHM_REMARKS")
	private String sap_schm_remarks;
	@Column(name = "REQ_REMARKS")
	private String req_remarks;
	@Column(name = "REQUEST_NO")
	private String request_no;
	@Column(name = "Request_Date")
	private Date request_date;
	@Column(name = "Email1")
	private String email1;
	@Column(name = "Email2")
	private String email2;
	@Column(name = "Email3")
	private String email3;
	@Column(name = "Email4")
	private String email4;
	@Column(name = "Email5")
	private String email5;
	@Column(name = "Prod_ID1")
	private Long prod_id1;
	@Column(name = "PROD1_QTY")
	private BigDecimal prod1_qty;
	@Column(name = "Prod_ID2")
	private Long prod_id2;
	@Column(name = "PROD2_QTY")
	private BigDecimal prod2_qty;
	@Column(name = "Prod_ID3")
	private Long prod_id3;
	@Column(name = "PROD3_QTY")
	private BigDecimal prod3_qty;
	@Column(name = "Prod_ID4")
	private Long prod_id4;
	@Column(name = "PROD4_QTY")
	private BigDecimal prod4_qty;
	@Column(name = "Prod_ID5")
	private Long prod_id5;
	@Column(name = "PROD5_QTY")
	private BigDecimal prod5_qty;
	@Column(name = "Prod_ID6")
	private Long prod_id6;
	@Column(name = "PROD6_QTY")
	private BigDecimal prod6_qty;
	@Column(name = "Prod_ID7")
	private Long prod_id7;
	@Column(name = "PROD7_QTY")
	private BigDecimal prod7_qty;
	@Column(name = "Prod_ID8")
	private Long prod_id8;
	@Column(name = "PROD8_QTY")
	private BigDecimal prod8_qty;
	@Column(name = "Prod_ID9")
	private Long prod_id9;
	@Column(name = "PROD9_QTY")
	private BigDecimal prod9_qty;
	@Column(name = "Prod_ID10")
	private Long prod_id10;
	@Column(name = "PROD10_QTY")
	private BigDecimal prod10_qty;
	@Column(name = "TMP_ins_usr_id")
	private String tmp_ins_usr_id;
	@Column(name = "TMP_mod_usr_id")
	private String tmp_mod_usr_id;
	@Column(name = "TMP_ins_dt")
	private Date tmp_ins_dt;
	@Column(name = "TMP_mod_dt")
	private Date tmp_mod_dt;
	@Column(name = "TMP_ins_ip_add")
	private String tmp_ins_ip_add;
	@Column(name = "TMP_mod_ip_add")
	private String tmp_mod_ip_add;
	@Column(name = "TMP_status")
	private String tmp_status;

	@Column(name = "Address1")
	private String address1;
	@Column(name = "Address2")
	private String address2;
	@Column(name = "Address3")
	private String address3;
	@Column(name = "Address4")
	private String address4;
	@Column(name = "TERRITORY_ID")
	private Long territory_id;
	@Column(name = "Cust_Phone_no")
	private String cust_phone_no;
	@Column(name = "Cust_Email")
	private String cust_email;
	@Column(name = "Cust_Pin_Code")
	private String cust_pin_code;
	
	

	public String getCust_pin_code() {
		return cust_pin_code;
	}

	public void setCust_pin_code(String cust_pin_code) {
		this.cust_pin_code = cust_pin_code;
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

	public Long getTerritory_id() {
		return territory_id;
	}

	public void setTerritory_id(Long territory_id) {
		this.territory_id = territory_id;
	}

	public String getCust_phone_no() {
		return cust_phone_no;
	}

	public void setCust_phone_no(String cust_phone_no) {
		this.cust_phone_no = cust_phone_no;
	}

	public String getCust_email() {
		return cust_email;
	}

	public void setCust_email(String cust_email) {
		this.cust_email = cust_email;
	}

	public Long getBlk_stk_req_temp_id() {
		return blk_stk_req_temp_id;
	}

	public void setBlk_stk_req_temp_id(Long blk_stk_req_temp_id) {
		this.blk_stk_req_temp_id = blk_stk_req_temp_id;
	}

	public Long getBlk_stk_req_id() {
		return blk_stk_req_id;
	}

	public void setBlk_stk_req_id(Long blk_stk_req_id) {
		this.blk_stk_req_id = blk_stk_req_id;
	}

	public Date getBlk_stk_req_date() {
		return blk_stk_req_date;
	}

	public void setBlk_stk_req_date(Date blk_stk_req_date) {
		this.blk_stk_req_date = blk_stk_req_date;
	}

	public String getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(String fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBlk_csv_name() {
		return blk_csv_name;
	}

	public void setBlk_csv_name(String blk_csv_name) {
		this.blk_csv_name = blk_csv_name;
	}

	public String getBlk_stk_req_no() {
		return blk_stk_req_no;
	}

	public void setBlk_stk_req_no(String blk_stk_req_no) {
		this.blk_stk_req_no = blk_stk_req_no;
	}

	public Date getBlk_upl_date() {
		return blk_upl_date;
	}

	public void setBlk_upl_date(Date blk_upl_date) {
		this.blk_upl_date = blk_upl_date;
	}

	public String getBlk_upl_user() {
		return blk_upl_user;
	}

	public void setBlk_upl_user(String blk_upl_user) {
		this.blk_upl_user = blk_upl_user;
	}

	public Long getRequestor_id() {
		return requestor_id;
	}

	public void setRequestor_id(Long requestor_id) {
		this.requestor_id = requestor_id;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public String getCust_cd() {
		return cust_cd;
	}

	public void setCust_cd(String cust_cd) {
		this.cust_cd = cust_cd;
	}

	public String getErp_cust_cd() {
		return erp_cust_cd;
	}

	public void setErp_cust_cd(String erp_cust_cd) {
		this.erp_cust_cd = erp_cust_cd;
	}

	public Long getAlloc_req_hdr_id() {
		return alloc_req_hdr_id;
	}

	public void setAlloc_req_hdr_id(Long alloc_req_hdr_id) {
		this.alloc_req_hdr_id = alloc_req_hdr_id;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getSap_inv_no() {
		return sap_inv_no;
	}

	public void setSap_inv_no(String sap_inv_no) {
		this.sap_inv_no = sap_inv_no;
	}

	public Date getSap_inv_date() {
		return sap_inv_date;
	}

	public void setSap_inv_date(Date sap_inv_date) {
		this.sap_inv_date = sap_inv_date;
	}

	public String getSap_schm_remarks() {
		return sap_schm_remarks;
	}

	public void setSap_schm_remarks(String sap_schm_remarks) {
		this.sap_schm_remarks = sap_schm_remarks;
	}

	public String getReq_remarks() {
		return req_remarks;
	}

	public void setReq_remarks(String req_remarks) {
		this.req_remarks = req_remarks;
	}

	public String getRequest_no() {
		return request_no;
	}

	public void setRequest_no(String request_no) {
		this.request_no = request_no;
	}

	public Date getRequest_date() {
		return request_date;
	}

	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getEmail4() {
		return email4;
	}

	public void setEmail4(String email4) {
		this.email4 = email4;
	}

	public String getEmail5() {
		return email5;
	}

	public void setEmail5(String email5) {
		this.email5 = email5;
	}

	public Long getProd_id1() {
		return prod_id1;
	}

	public void setProd_id1(Long prod_id1) {
		this.prod_id1 = prod_id1;
	}

	public BigDecimal getProd1_qty() {
		return prod1_qty;
	}

	public void setProd1_qty(BigDecimal prod1_qty) {
		this.prod1_qty = prod1_qty;
	}

	public Long getProd_id2() {
		return prod_id2;
	}

	public void setProd_id2(Long prod_id2) {
		this.prod_id2 = prod_id2;
	}

	public BigDecimal getProd2_qty() {
		return prod2_qty;
	}

	public void setProd2_qty(BigDecimal prod2_qty) {
		this.prod2_qty = prod2_qty;
	}

	public Long getProd_id3() {
		return prod_id3;
	}

	public void setProd_id3(Long prod_id3) {
		this.prod_id3 = prod_id3;
	}

	public BigDecimal getProd3_qty() {
		return prod3_qty;
	}

	public void setProd3_qty(BigDecimal prod3_qty) {
		this.prod3_qty = prod3_qty;
	}

	public Long getProd_id4() {
		return prod_id4;
	}

	public void setProd_id4(Long prod_id4) {
		this.prod_id4 = prod_id4;
	}

	public BigDecimal getProd4_qty() {
		return prod4_qty;
	}

	public void setProd4_qty(BigDecimal prod4_qty) {
		this.prod4_qty = prod4_qty;
	}

	public Long getProd_id5() {
		return prod_id5;
	}

	public void setProd_id5(Long prod_id5) {
		this.prod_id5 = prod_id5;
	}

	public BigDecimal getProd5_qty() {
		return prod5_qty;
	}

	public void setProd5_qty(BigDecimal prod5_qty) {
		this.prod5_qty = prod5_qty;
	}

	public Long getProd_id6() {
		return prod_id6;
	}

	public void setProd_id6(Long prod_id6) {
		this.prod_id6 = prod_id6;
	}

	public BigDecimal getProd6_qty() {
		return prod6_qty;
	}

	public void setProd6_qty(BigDecimal prod6_qty) {
		this.prod6_qty = prod6_qty;
	}

	public Long getProd_id7() {
		return prod_id7;
	}

	public void setProd_id7(Long prod_id7) {
		this.prod_id7 = prod_id7;
	}

	public BigDecimal getProd7_qty() {
		return prod7_qty;
	}

	public void setProd7_qty(BigDecimal prod7_qty) {
		this.prod7_qty = prod7_qty;
	}

	public Long getProd_id8() {
		return prod_id8;
	}

	public void setProd_id8(Long prod_id8) {
		this.prod_id8 = prod_id8;
	}

	public BigDecimal getProd8_qty() {
		return prod8_qty;
	}

	public void setProd8_qty(BigDecimal prod8_qty) {
		this.prod8_qty = prod8_qty;
	}

	public Long getProd_id9() {
		return prod_id9;
	}

	public void setProd_id9(Long prod_id9) {
		this.prod_id9 = prod_id9;
	}

	public BigDecimal getProd9_qty() {
		return prod9_qty;
	}

	public void setProd9_qty(BigDecimal prod9_qty) {
		this.prod9_qty = prod9_qty;
	}

	public Long getProd_id10() {
		return prod_id10;
	}

	public void setProd_id10(Long prod_id10) {
		this.prod_id10 = prod_id10;
	}

	public BigDecimal getProd10_qty() {
		return prod10_qty;
	}

	public void setProd10_qty(BigDecimal prod10_qty) {
		this.prod10_qty = prod10_qty;
	}

	public String getTmp_ins_usr_id() {
		return tmp_ins_usr_id;
	}

	public void setTmp_ins_usr_id(String tmp_ins_usr_id) {
		this.tmp_ins_usr_id = tmp_ins_usr_id;
	}

	public String getTmp_mod_usr_id() {
		return tmp_mod_usr_id;
	}

	public void setTmp_mod_usr_id(String tmp_mod_usr_id) {
		this.tmp_mod_usr_id = tmp_mod_usr_id;
	}

	public Date getTmp_ins_dt() {
		return tmp_ins_dt;
	}

	public void setTmp_ins_dt(Date tmp_ins_dt) {
		this.tmp_ins_dt = tmp_ins_dt;
	}

	public Date getTmp_mod_dt() {
		return tmp_mod_dt;
	}

	public void setTmp_mod_dt(Date tmp_mod_dt) {
		this.tmp_mod_dt = tmp_mod_dt;
	}

	public String getTmp_ins_ip_add() {
		return tmp_ins_ip_add;
	}

	public void setTmp_ins_ip_add(String tmp_ins_ip_add) {
		this.tmp_ins_ip_add = tmp_ins_ip_add;
	}

	public String getTmp_mod_ip_add() {
		return tmp_mod_ip_add;
	}

	public void setTmp_mod_ip_add(String tmp_mod_ip_add) {
		this.tmp_mod_ip_add = tmp_mod_ip_add;
	}

	public String getTmp_status() {
		return tmp_status;
	}

	public void setTmp_status(String tmp_status) {
		this.tmp_status = tmp_status;
	}

}
