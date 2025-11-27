package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "TDS_APPLICABLE_STATEMENT_REPORT")
@NamedStoredProcedureQuery(name = "callTDS_APPLICABLE_STATEMENT_REPORT", procedureName = "TDS_APPLICABLE_STATEMENT_REPORT", parameters = {
		@StoredProcedureParameter(name = "sub_comp", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "custtype", mode = ParameterMode.IN, type = String.class),
		@StoredProcedureParameter(name = "tdsreqd", mode = ParameterMode.IN, type = Character.class)
}, resultClasses = TdsApplicableStatementReport.class)
public class TdsApplicableStatementReport implements Serializable {

	private static final long serialVersionUID = -737856012326986833L;
	
	@Id
	@Column(name = "Unique_HCP_ID")
	private String unique_hcp_id;
	
	@Column(name = "Name_of_HCP")
	private String name_of_hcp;
	
	@Column(name = "HCP_PAN_Number")
	private String hcp_pan_number;
	
	@Column(name = "HCP_Address_1")
	private String hcp_address_1;
	
	@Column(name = "HCP_Address_2")
	private String hcp_address_2;
	
	@Column(name = "HCP_Address_3")
	private String hcp_address_3;
	
	@Column(name = "HCP_Address_4")
	private String hcp_address_4;
	
	@Column(name = "HCP_PINCODE	")
	private String hcp_pincode;
	
	@Column(name = "HCP_State")
	private String hcp_state;
	
	@Column(name = "HCP_CITY")
	private String hcp_city;
	
	@Column(name = "HCP_Mobile_No")
	private String hcp_mobile_no;
	
	@Column(name = "HCP_Email_ID")
	private String hcp_email_id;
	
	@Column(name = "V_MTH1")
	private BigDecimal v_mth1;
	
	@Column(name = "V_MTH2")
	private BigDecimal v_mth2;
	
	@Column(name = "V_MTH3")
	private BigDecimal v_mth3;
	
	@Column(name = "V_MTH4")
	private BigDecimal v_mth4;
	
	@Column(name = "V_MTH5")
	private BigDecimal v_mth5;
	
	@Column(name = "V_MTH6")
	private BigDecimal v_mth6;
	
	@Column(name = "V_MTH7")
	private BigDecimal v_mth7;
	
	@Column(name = "V_MTH8")
	private BigDecimal v_mth8;
	
	@Column(name = "V_MTH9")
	private BigDecimal v_mth9;
	
	@Column(name = "V_MTH10")
	private BigDecimal v_mth10;
	
	@Column(name = "V_MTH11")
	private BigDecimal v_mth11;
	
	@Column(name = "V_MTH12")
	private BigDecimal v_mth12;
	
	@Column(name = "TDS_PAYABLE")
	private BigDecimal tds_payable;
	
	@Column(name = "TDS_PAID")
	private BigDecimal tds_paid;
	
	@Column(name = "TDS_BALANCE")
	private BigDecimal tds_balance;

	public String getUnique_hcp_id() {
		return unique_hcp_id;
	}

	public void setUnique_hcp_id(String unique_hcp_id) {
		this.unique_hcp_id = unique_hcp_id;
	}

	public String getName_of_hcp() {
		return name_of_hcp;
	}

	public void setName_of_hcp(String name_of_hcp) {
		this.name_of_hcp = name_of_hcp;
	}

	public String getHcp_pan_number() {
		return hcp_pan_number;
	}

	public void setHcp_pan_number(String hcp_pan_number) {
		this.hcp_pan_number = hcp_pan_number;
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

	public String getHcp_pincode() {
		return hcp_pincode;
	}

	public void setHcp_pincode(String hcp_pincode) {
		this.hcp_pincode = hcp_pincode;
	}

	public String getHcp_state() {
		return hcp_state;
	}

	public void setHcp_state(String hcp_state) {
		this.hcp_state = hcp_state;
	}

	public String getHcp_mobile_no() {
		return hcp_mobile_no;
	}

	public void setHcp_mobile_no(String hcp_mobile_no) {
		this.hcp_mobile_no = hcp_mobile_no;
	}

	public String getHcp_email_id() {
		return hcp_email_id;
	}

	public void setHcp_email_id(String hcp_email_id) {
		this.hcp_email_id = hcp_email_id;
	}

	
	public String getHcp_city() {
		return hcp_city;
	}

	public void setHcp_city(String hcp_city) {
		this.hcp_city = hcp_city;
	}

	public BigDecimal getV_mth1() {
		return v_mth1;
	}

	public void setV_mth1(BigDecimal v_mth1) {
		this.v_mth1 = v_mth1;
	}

	public BigDecimal getV_mth2() {
		return v_mth2;
	}

	public void setV_mth2(BigDecimal v_mth2) {
		this.v_mth2 = v_mth2;
	}

	public BigDecimal getV_mth3() {
		return v_mth3;
	}

	public void setV_mth3(BigDecimal v_mth3) {
		this.v_mth3 = v_mth3;
	}

	public BigDecimal getV_mth4() {
		return v_mth4;
	}

	public void setV_mth4(BigDecimal v_mth4) {
		this.v_mth4 = v_mth4;
	}

	public BigDecimal getV_mth5() {
		return v_mth5;
	}

	public void setV_mth5(BigDecimal v_mth5) {
		this.v_mth5 = v_mth5;
	}

	public BigDecimal getV_mth6() {
		return v_mth6;
	}

	public void setV_mth6(BigDecimal v_mth6) {
		this.v_mth6 = v_mth6;
	}

	public BigDecimal getV_mth7() {
		return v_mth7;
	}

	public void setV_mth7(BigDecimal v_mth7) {
		this.v_mth7 = v_mth7;
	}

	public BigDecimal getV_mth8() {
		return v_mth8;
	}

	public void setV_mth8(BigDecimal v_mth8) {
		this.v_mth8 = v_mth8;
	}

	public BigDecimal getV_mth9() {
		return v_mth9;
	}

	public void setV_mth9(BigDecimal v_mth9) {
		this.v_mth9 = v_mth9;
	}

	public BigDecimal getV_mth10() {
		return v_mth10;
	}

	public void setV_mth10(BigDecimal v_mth10) {
		this.v_mth10 = v_mth10;
	}

	public BigDecimal getV_mth11() {
		return v_mth11;
	}

	public void setV_mth11(BigDecimal v_mth11) {
		this.v_mth11 = v_mth11;
	}

	public BigDecimal getV_mth12() {
		return v_mth12;
	}

	public void setV_mth12(BigDecimal v_mth12) {
		this.v_mth12 = v_mth12;
	}

	public BigDecimal getTds_payable() {
		return tds_payable;
	}

	public void setTds_payable(BigDecimal tds_payable) {
		this.tds_payable = tds_payable;
	}

	public BigDecimal getTds_paid() {
		return tds_paid;
	}

	public void setTds_paid(BigDecimal tds_paid) {
		this.tds_paid = tds_paid;
	}

	public BigDecimal getTds_balance() {
		return tds_balance;
	}

	public void setTds_balance(BigDecimal tds_balance) {
		this.tds_balance = tds_balance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TdsApplicableStatementReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TdsApplicableStatementReport [unique_hcp_id=" + unique_hcp_id + ", name_of_hcp=" + name_of_hcp
				+ ", hcp_pan_number=" + hcp_pan_number + ", hcp_address_1=" + hcp_address_1 + ", hcp_address_2="
				+ hcp_address_2 + ", hcp_address_3=" + hcp_address_3 + ", hcp_address_4=" + hcp_address_4
				+ ", hcp_pincode=" + hcp_pincode + ", hcp_state=" + hcp_state + ", hcp_city=" + hcp_city
				+ ", hcp_mobile_no=" + hcp_mobile_no + ", hcp_email_id=" + hcp_email_id + ", v_mth1=" + v_mth1
				+ ", v_mth2=" + v_mth2 + ", v_mth3=" + v_mth3 + ", v_mth4=" + v_mth4 + ", v_mth5=" + v_mth5
				+ ", v_mth6=" + v_mth6 + ", v_mth7=" + v_mth7 + ", v_mth8=" + v_mth8 + ", v_mth9=" + v_mth9
				+ ", v_mth10=" + v_mth10 + ", v_mth11=" + v_mth11 + ", v_mth12=" + v_mth12 + ", tds_payable="
				+ tds_payable + ", tds_paid=" + tds_paid + ", tds_balance=" + tds_balance + "]";
	}

	
}
