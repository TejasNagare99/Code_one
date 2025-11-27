package com.excel.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.excel.model.Tds_crm_Images;

public class CrmTdsBean {

	private String custtype;
	private String hq;
	private String se;
	private String custname;
	private String custid;
	//Detail: this.fb.group({
	private Date trandate;
	private String transactionno;
	private String expensedetail;
	private String qty;
	private String rate;
	private String val;
	private String tdspaid;
	private String intrestpaid;
	private String penaltypaid;
	private String addr1;
	private String addr2;
	private String addr3;
	private String addr4;
	private String city;
	private String pincode;
	private String telno;
	private String email;
	private String panno;
	private String hcpno;
	private String finyear;
	private String crm_id;
	private String username;
	private Map<String, Object> imagefiles;
	private List<Tds_crm_Images>updateimagefiles;
	
	private String grossval;
	private String grossvalind;
	private String grossupperc;
	private String subcomp;
	
	private Long crmgendtlid;
	private Long crmgenid;
	private Long divid;
	
	private String company;
	private String originalfilename;
	
	
	
	public String getOriginalfilename() {
		return originalfilename;
	}
	public void setOriginalfilename(String originalfilename) {
		this.originalfilename = originalfilename;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Long getDivid() {
		return divid;
	}
	public void setDivid(Long divid) {
		this.divid = divid;
	}
	public Long getCrmgendtlid() {
		return crmgendtlid;
	}
	public void setCrmgendtlid(Long crmgendtlid) {
		this.crmgendtlid = crmgendtlid;
	}
	public Long getCrmgenid() {
		return crmgenid;
	}
	public void setCrmgenid(Long crmgenid) {
		this.crmgenid = crmgenid;
	}
	public String getSubcomp() {
		return subcomp;
	}
	public void setSubcomp(String subcomp) {
		this.subcomp = subcomp;
	}
	public String getGrossupperc() {
		return grossupperc;
	}
	public void setGrossupperc(String grossupperc) {
		this.grossupperc = grossupperc;
	}
	public String getGrossval() {
		return grossval;
	}
	public void setGrossval(String grossval) {
		this.grossval = grossval;
	}
	public String getGrossvalind() {
		return grossvalind;
	}
	public void setGrossvalind(String grossvalind) {
		this.grossvalind = grossvalind;
	}
	public String getIntrestpaid() {
		return intrestpaid;
	}
	public void setIntrestpaid(String intrestpaid) {
		this.intrestpaid = intrestpaid;
	}
	public String getPenaltypaid() {
		return penaltypaid;
	}
	public void setPenaltypaid(String penaltypaid) {
		this.penaltypaid = penaltypaid;
	}
	public List<Tds_crm_Images> getUpdateimagefiles() {
		return updateimagefiles;
	}
	public void setUpdateimagefiles(List<Tds_crm_Images> updateimagefiles) {
		this.updateimagefiles = updateimagefiles;
	}
	public Map<String, Object> getImagefiles() {
		return imagefiles;
	}
	public void setImagefiles(Map<String, Object> imagefiles) {
		this.imagefiles = imagefiles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCrm_id() {
		return crm_id;
	}
	public void setCrm_id(String crm_id) {
		this.crm_id = crm_id;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getFinyear() {
		return finyear;
	}
	public void setFinyear(String finyear) {
		this.finyear = finyear;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public String getHq() {
		return hq;
	}
	public void setHq(String hq) {
		this.hq = hq;
	}
	public String getSe() {
		return se;
	}
	public void setSe(String se) {
		this.se = se;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public Date getTrandate() {
		return trandate;
	}
	public void setTrandate(Date trandate) {
		this.trandate = trandate;
	}
	public String getTransactionno() {
		return transactionno;
	}
	public void setTransactionno(String transactionno) {
		this.transactionno = transactionno;
	}
	public String getExpensedetail() {
		return expensedetail;
	}
	public void setExpensedetail(String expensedetail) {
		this.expensedetail = expensedetail;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getTdspaid() {
		return tdspaid;
	}
	public void setTdspaid(String tdspaid) {
		this.tdspaid = tdspaid;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr3() {
		return addr3;
	}
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}
	public String getAddr4() {
		return addr4;
	}
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPanno() {
		return panno;
	}
	public void setPanno(String panno) {
		this.panno = panno;
	}
	public String getHcpno() {
		return hcpno;
	}
	public void setHcpno(String hcpno) {
		this.hcpno = hcpno;
	}
	
	
}
