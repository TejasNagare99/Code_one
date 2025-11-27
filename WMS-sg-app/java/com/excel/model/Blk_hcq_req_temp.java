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
@Table(name = "BLK_HCP_REQ_TEMP")
public class Blk_hcq_req_temp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BLK_HCP_REQ_TEMP_ID")
	private Long blk_hcp_req_temp_id;

	@Column(name = "BLK_HCP_REQ_ID")
	private Long blk_hcp_req_id;

	@Column(name = "BLK_HCP_REQ_DATE")
	private Date blk_hcp_req_date;

	@Column(name = "BLK_CSV_NAME")
	private String blk_csv_name;

	@Column(name = "BLK_HCP_REQ_NO")
	private String blk_hcp_req_no;

	@Column(name = "BLK_UPL_DATE")
	private Date blk_upl_date;

	@Column(name = "BLK_UPL_USER")
	private String blk_upl_user;

	@Column(name = "REQUESTOR_ID")
	private Long requestor_id;

	@Column(name = "HCP_UNIQUE_ID")
	private String hcp_unique_id;

	@Column(name = "HCP_ID_OLD")
	private String hcp_id_old;

	@Column(name = "ALLOC_REQ_HDR_ID")
	private Long alloc_req_hdr_id;

	@Column(name = "MCL_NUMBER")
	private String mcl_number;

	@Column(name = "HCP_NAME")
	private String hcp_name;

	@Column(name = "MDM_ADDRESS_CHG")
	private String mdm_address_chg;

	@Column(name = "SRT_NUMBER")
	private String srt_number;

	@Column(name = "SRT_DATE")
	private Date srt_date;

	@Column(name = "SRT_REMARKS")
	private String srt_remarks;

	@Column(name = "REQ_REMARKS")
	private String req_remarks;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "DIV_ID")
	private Long div_id;

	@Column(name = "REQUEST_NO")
	private String request_no;

	@Column(name = "REQUEST_DATE")
	private Date request_date;

	@Column(name = "FS_CODE")
	private String fs_code;

	@Column(name = "FS_ID")
	private Long fs_id;

	@Column(name = "EMAIL1")
	private String email1;

	@Column(name = "EMAIL2")
	private String email2;

	@Column(name = "EMAIL3")
	private String email3;

	@Column(name = "EMAIL4")
	private String email4;

	@Column(name = "EMAIL5")
	private String email5;

	@Column(name = "TERRITORY_ID")
	private Long territory_id;

	@Column(name = "HCP_ADDRESS_1")
	private String hcp_address_1;

	@Column(name = "HCP_ADDRESS_2")
	private String hcp_address_2;

	@Column(name = "HCP_ADDRESS_3")
	private String hcp_address_3;

	@Column(name = "HCP_ADDRESS_4")
	private String hcp_address_4;

	@Column(name = "HCP_CITY")
	private String hcp_city;

	@Column(name = "HCP_PIN_CODE")
	private String hcp_pin_code;

	@Column(name = "HCP_MOBILE")
	private String hcp_mobile;

	@Column(name = "HCP_EMAIL")
	private String hcp_email;

	@Column(name = "PROD_ID1")
	private Long prod_id1;

	@Column(name = "PROD1_QTY")
	private BigDecimal prod1_qty;

	@Column(name = "PROD_ID2")
	private Long prod_id2;

	@Column(name = "PROD2_QTY")
	private BigDecimal prod2_qty;

	@Column(name = "PROD_ID3")
	private Long prod_id3;

	@Column(name = "PROD3_QTY")
	private BigDecimal prod3_qty;

	@Column(name = "PROD_ID4")
	private Long prod_id4;

	@Column(name = "PROD4_QTY")
	private BigDecimal prod4_qty;

	@Column(name = "PROD_ID5")
	private Long prod_id5;

	@Column(name = "PROD5_QTY")
	private BigDecimal prod5_qty;

	@Column(name = "PROD_ID6")
	private Long prod_id6;

	@Column(name = "PROD6_QTY")
	private BigDecimal prod6_qty;

	@Column(name = "PROD_ID7")
	private Long prod_id7;

	@Column(name = "PROD7_QTY")
	private BigDecimal prod7_qty;

	@Column(name = "PROD_ID8")
	private Long prod_id8;

	@Column(name = "PROD8_QTY")
	private BigDecimal prod8_qty;

	@Column(name = "PROD_ID9")
	private Long prod_id9;

	@Column(name = "PROD9_QTY")
	private BigDecimal prod9_qty;

	@Column(name = "PROD_ID10")
	private Long prod_id10;

	@Column(name = "PROD10_QTY")
	private BigDecimal prod10_qty;

	@Column(name = "PROD_ID11")
	private Long prod_id11;

	@Column(name = "PROD11_QTY")
	private BigDecimal prod11_qty;

	@Column(name = "PROD_ID12")
	private Long prod_id12;

	@Column(name = "PROD12_QTY")
	private BigDecimal prod12_qty;

	@Column(name = "PROD_ID13")
	private Long prod_id13;

	@Column(name = "PROD13_QTY")
	private BigDecimal prod13_qty;

	@Column(name = "PROD_ID14")
	private Long prod_id14;

	@Column(name = "PROD14_QTY")
	private BigDecimal prod14_qty;

	@Column(name = "PROD_ID15")
	private Long prod_id15;

	@Column(name = "PROD15_QTY")
	private BigDecimal prod15_qty;

	@Column(name = "PROD_ID16")
	private Long prod_id16;

	@Column(name = "PROD16_QTY")
	private BigDecimal prod16_qty;

	@Column(name = "PROD_ID17")
	private Long prod_id17;

	@Column(name = "PROD17_QTY")
	private BigDecimal prod17_qty;

	@Column(name = "PROD_ID18")
	private Long prod_id18;

	@Column(name = "PROD18_QTY")
	private BigDecimal prod18_qty;

	@Column(name = "PROD_ID19")
	private Long prod_id19;

	@Column(name = "PROD19_QTY")
	private BigDecimal prod19_qty;

	@Column(name = "PROD_ID20")
	private Long prod_id20;

	@Column(name = "PROD20_QTY")
	private BigDecimal prod20_qty;
	
	@Column(name="BLK_REQ_EMPLOYEE_NO")
	private String blk_req_employee_no;
	
	@Column(name="TERR_CODE")
	private String terr_code;
	
	@Column(name="BLK_REQ_MDM_EMPLOYEE_NO")
	private String blk_req_mdm_employee_no;
	
	
	public String getBlk_req_mdm_employee_no() {
		return blk_req_mdm_employee_no;
	}

	public void setBlk_req_mdm_employee_no(String blk_req_mdm_employee_no) {
		this.blk_req_mdm_employee_no = blk_req_mdm_employee_no;
	}
	
	public String getTerr_code() {
		return terr_code;
	}

	public void setTerr_code(String terr_code) {
		this.terr_code = terr_code;
	}
	
	public String getBlk_req_employee_no() {
		return blk_req_employee_no;
	}

	public void setBlk_req_employee_no(String blk_req_employee_no) {
		this.blk_req_employee_no = blk_req_employee_no;
	}

	public Long getBlk_hcp_req_temp_id() {
		return blk_hcp_req_temp_id;
	}

	public void setBlk_hcp_req_temp_id(Long blk_hcp_req_temp_id) {
		this.blk_hcp_req_temp_id = blk_hcp_req_temp_id;
	}

	public Long getBlk_hcp_req_id() {
		return blk_hcp_req_id;
	}

	public void setBlk_hcp_req_id(Long blk_hcp_req_id) {
		this.blk_hcp_req_id = blk_hcp_req_id;
	}

	public Date getBlk_hcp_req_date() {
		return blk_hcp_req_date;
	}

	public void setBlk_hcp_req_date(Date blk_hcp_req_date) {
		this.blk_hcp_req_date = blk_hcp_req_date;
	}

	public String getBlk_csv_name() {
		return blk_csv_name;
	}

	public void setBlk_csv_name(String blk_csv_name) {
		this.blk_csv_name = blk_csv_name;
	}

	public String getBlk_hcp_req_no() {
		return blk_hcp_req_no;
	}

	public void setBlk_hcp_req_no(String blk_hcp_req_no) {
		this.blk_hcp_req_no = blk_hcp_req_no;
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

	public String getHcp_unique_id() {
		return hcp_unique_id;
	}

	public void setHcp_unique_id(String hcp_unique_id) {
		this.hcp_unique_id = hcp_unique_id;
	}

	public String getHcp_id_old() {
		return hcp_id_old;
	}

	public void setHcp_id_old(String hcp_id_old) {
		this.hcp_id_old = hcp_id_old;
	}

	public Long getAlloc_req_hdr_id() {
		return alloc_req_hdr_id;
	}

	public void setAlloc_req_hdr_id(Long alloc_req_hdr_id) {
		this.alloc_req_hdr_id = alloc_req_hdr_id;
	}

	public String getMcl_number() {
		return mcl_number;
	}

	public void setMcl_number(String mcl_number) {
		this.mcl_number = mcl_number;
	}

	public String getHcp_name() {
		return hcp_name;
	}

	public void setHcp_name(String hcp_name) {
		this.hcp_name = hcp_name;
	}

	public String getMdm_address_chg() {
		return mdm_address_chg;
	}

	public void setMdm_address_chg(String mdm_address_chg) {
		this.mdm_address_chg = mdm_address_chg;
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

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
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

	public String getFs_code() {
		return fs_code;
	}

	public void setFs_code(String fs_code) {
		this.fs_code = fs_code;
	}

	public Long getFs_id() {
		return fs_id;
	}

	public void setFs_id(Long fs_id) {
		this.fs_id = fs_id;
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

	public Long getTerritory_id() {
		return territory_id;
	}

	public void setTerritory_id(Long territory_id) {
		this.territory_id = territory_id;
	}

	public String getHcp_address_1() {
		return hcp_address_1;
	}

	public void setHcp_address_1(String hcp_address_1) {
		this.hcp_address_1 = hcp_address_1;
	}

	public String getHcp_address_2() {
		return hcp_address_2;
	}

	public void setHcp_address_2(String hcp_address_2) {
		this.hcp_address_2 = hcp_address_2;
	}

	public String getHcp_address_3() {
		return hcp_address_3;
	}

	public void setHcp_address_3(String hcp_address_3) {
		this.hcp_address_3 = hcp_address_3;
	}

	public String getHcp_address_4() {
		return hcp_address_4;
	}

	public void setHcp_address_4(String hcp_address_4) {
		this.hcp_address_4 = hcp_address_4;
	}

	public String getHcp_city() {
		return hcp_city;
	}

	public void setHcp_city(String hcp_city) {
		this.hcp_city = hcp_city;
	}

	public String getHcp_pin_code() {
		return hcp_pin_code;
	}

	public void setHcp_pin_code(String hcp_pin_code) {
		this.hcp_pin_code = hcp_pin_code;
	}

	public String getHcp_mobile() {
		return hcp_mobile;
	}

	public void setHcp_mobile(String hcp_mobile) {
		this.hcp_mobile = hcp_mobile;
	}

	public String getHcp_email() {
		return hcp_email;
	}

	public void setHcp_email(String hcp_email) {
		this.hcp_email = hcp_email;
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

	public Long getProd_id11() {
		return prod_id11;
	}

	public void setProd_id11(Long prod_id11) {
		this.prod_id11 = prod_id11;
	}

	public BigDecimal getProd11_qty() {
		return prod11_qty;
	}

	public void setProd11_qty(BigDecimal prod11_qty) {
		this.prod11_qty = prod11_qty;
	}

	public Long getProd_id12() {
		return prod_id12;
	}

	public void setProd_id12(Long prod_id12) {
		this.prod_id12 = prod_id12;
	}

	public BigDecimal getProd12_qty() {
		return prod12_qty;
	}

	public void setProd12_qty(BigDecimal prod12_qty) {
		this.prod12_qty = prod12_qty;
	}

	public Long getProd_id13() {
		return prod_id13;
	}

	public void setProd_id13(Long prod_id13) {
		this.prod_id13 = prod_id13;
	}

	public BigDecimal getProd13_qty() {
		return prod13_qty;
	}

	public void setProd13_qty(BigDecimal prod13_qty) {
		this.prod13_qty = prod13_qty;
	}

	public Long getProd_id14() {
		return prod_id14;
	}

	public void setProd_id14(Long prod_id14) {
		this.prod_id14 = prod_id14;
	}

	public BigDecimal getProd14_qty() {
		return prod14_qty;
	}

	public void setProd14_qty(BigDecimal prod14_qty) {
		this.prod14_qty = prod14_qty;
	}

	public Long getProd_id15() {
		return prod_id15;
	}

	public void setProd_id15(Long prod_id15) {
		this.prod_id15 = prod_id15;
	}

	public BigDecimal getProd15_qty() {
		return prod15_qty;
	}

	public void setProd15_qty(BigDecimal prod15_qty) {
		this.prod15_qty = prod15_qty;
	}

	public Long getProd_id16() {
		return prod_id16;
	}

	public void setProd_id16(Long prod_id16) {
		this.prod_id16 = prod_id16;
	}

	public BigDecimal getProd16_qty() {
		return prod16_qty;
	}

	public void setProd16_qty(BigDecimal prod16_qty) {
		this.prod16_qty = prod16_qty;
	}

	public Long getProd_id17() {
		return prod_id17;
	}

	public void setProd_id17(Long prod_id17) {
		this.prod_id17 = prod_id17;
	}

	public BigDecimal getProd17_qty() {
		return prod17_qty;
	}

	public void setProd17_qty(BigDecimal prod17_qty) {
		this.prod17_qty = prod17_qty;
	}

	public Long getProd_id18() {
		return prod_id18;
	}

	public void setProd_id18(Long prod_id18) {
		this.prod_id18 = prod_id18;
	}

	public BigDecimal getProd18_qty() {
		return prod18_qty;
	}

	public void setProd18_qty(BigDecimal prod18_qty) {
		this.prod18_qty = prod18_qty;
	}

	public Long getProd_id19() {
		return prod_id19;
	}

	public void setProd_id19(Long prod_id19) {
		this.prod_id19 = prod_id19;
	}

	public BigDecimal getProd19_qty() {
		return prod19_qty;
	}

	public void setProd19_qty(BigDecimal prod19_qty) {
		this.prod19_qty = prod19_qty;
	}

	public Long getProd_id20() {
		return prod_id20;
	}

	public void setProd_id20(Long prod_id20) {
		this.prod_id20 = prod_id20;
	}

	public BigDecimal getProd20_qty() {
		return prod20_qty;
	}

	public void setProd20_qty(BigDecimal prod20_qty) {
		this.prod20_qty = prod20_qty;
	}

	@Override
	public String toString() {
		return "Blk_hcq_req_temp [blk_hcp_req_temp_id=" + blk_hcp_req_temp_id + ", blk_hcp_req_id=" + blk_hcp_req_id
				+ ", blk_hcp_req_date=" + blk_hcp_req_date + ", blk_csv_name=" + blk_csv_name + ", blk_hcp_req_no="
				+ blk_hcp_req_no + ", blk_upl_date=" + blk_upl_date + ", blk_upl_user=" + blk_upl_user
				+ ", requestor_id=" + requestor_id + ", hcp_unique_id=" + hcp_unique_id + ", hcp_id_old=" + hcp_id_old
				+ ", alloc_req_hdr_id=" + alloc_req_hdr_id + ", mcl_number=" + mcl_number + ", hcp_name=" + hcp_name
				+ ", mdm_address_chg=" + mdm_address_chg + ", srt_number=" + srt_number + ", srt_date=" + srt_date
				+ ", srt_remarks=" + srt_remarks + ", req_remarks=" + req_remarks + ", company=" + company + ", div_id="
				+ div_id + ", request_no=" + request_no + ", request_date=" + request_date + ", fs_code=" + fs_code
				+ ", fs_id=" + fs_id + ", email1=" + email1 + ", email2=" + email2 + ", email3=" + email3 + ", email4="
				+ email4 + ", email5=" + email5 + ", territory_id=" + territory_id + ", hcp_address_1=" + hcp_address_1
				+ ", hcp_address_2=" + hcp_address_2 + ", hcp_address_3=" + hcp_address_3 + ", hcp_address_4="
				+ hcp_address_4 + ", hcp_city=" + hcp_city + ", hcp_pin_code=" + hcp_pin_code + ", hcp_mobile="
				+ hcp_mobile + ", hcp_email=" + hcp_email + ", prod_id1=" + prod_id1 + ", prod1_qty=" + prod1_qty
				+ ", prod_id2=" + prod_id2 + ", prod2_qty=" + prod2_qty + ", prod_id3=" + prod_id3 + ", prod3_qty="
				+ prod3_qty + ", prod_id4=" + prod_id4 + ", prod4_qty=" + prod4_qty + ", prod_id5=" + prod_id5
				+ ", prod5_qty=" + prod5_qty + ", prod_id6=" + prod_id6 + ", prod6_qty=" + prod6_qty + ", prod_id7="
				+ prod_id7 + ", prod7_qty=" + prod7_qty + ", prod_id8=" + prod_id8 + ", prod8_qty=" + prod8_qty
				+ ", prod_id9=" + prod_id9 + ", prod9_qty=" + prod9_qty + ", prod_id10=" + prod_id10 + ", prod10_qty="
				+ prod10_qty + ", prod_id11=" + prod_id11 + ", prod11_qty=" + prod11_qty + ", prod_id12=" + prod_id12
				+ ", prod12_qty=" + prod12_qty + ", prod_id13=" + prod_id13 + ", prod13_qty=" + prod13_qty
				+ ", prod_id14=" + prod_id14 + ", prod14_qty=" + prod14_qty + ", prod_id15=" + prod_id15
				+ ", prod15_qty=" + prod15_qty + ", prod_id16=" + prod_id16 + ", prod16_qty=" + prod16_qty
				+ ", prod_id17=" + prod_id17 + ", prod17_qty=" + prod17_qty + ", prod_id18=" + prod_id18
				+ ", prod18_qty=" + prod18_qty + ", prod_id19=" + prod_id19 + ", prod19_qty=" + prod19_qty
				+ ", prod_id20=" + prod_id20 + ", prod20_qty=" + prod20_qty + "]";
	}
	
	
}
