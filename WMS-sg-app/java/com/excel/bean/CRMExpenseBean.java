package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.excel.model.CRMReqRequestsImage;

public class CRMExpenseBean {

	private Long div_id;
	private String reqDate;
	private Date scheddate;
	private String finyear;
	private String custtype;
	private Long fsid;
	private Long stk_loc;
	private String addr1;
	private String addr2;
	private String addr3;
	private String addr4;
	private String city;
	private String pincode;
	private String mobile;
	private String email;
	private String panno;
	private String hcpno;
	private Long doctorid;
	private Long custid;
	private String custname;
	private String category;
	private String docclass;
	private String speciality;
	private String docspeciality;
	private String doccategory;
	private BigDecimal expqty;
	private BigDecimal exprate;
	private BigDecimal expvalue;
	private Long retailerid;
	private Long wscustid;
	private Long schemeid;
	private String schemetype;
	private String schemeExpsDetails;
	private String schemeExpsItem;
	private String schemeExpsdesc;
	private String dtclass;
	private String uploadFile;
	private Long territory;
	private Map<String, Object> imagefiles;
	private String compCode;
	private String custtypename;
	private BigDecimal curBiz;
	private BigDecimal expBiz;
	private String username;
	private String savemod;
	private String crm_req_id;
	private List<CRMReqRequestsImage> updateimagefiles;
	private Long period_id;
	private Long seFs_id;

	
	public Long getStk_loc() {
		return stk_loc;
	}

	public void setStk_loc(Long stk_loc) {
		this.stk_loc = stk_loc;
	}

	public Long getPeriod_id() {
		return period_id;
	}

	public void setPeriod_id(Long period_id) {
		this.period_id = period_id;
	}

	public List<CRMReqRequestsImage> getUpdateimagefiles() {
		return updateimagefiles;
	}

	public void setUpdateimagefiles(List<CRMReqRequestsImage> updateimagefiles) {
		this.updateimagefiles = updateimagefiles;
	}

	public String getCrm_req_id() {
		return crm_req_id;
	}

	public void setCrm_req_id(String crm_req_id) {
		this.crm_req_id = crm_req_id;
	}

	public String getSavemod() {
		return savemod;
	}

	public void setSavemod(String savemod) {
		this.savemod = savemod;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public Date getScheddate() {
		return scheddate;
	}

	public void setScheddate(Date scheddate) {
		this.scheddate = scheddate;
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

	public Long getFsid() {
		return fsid;
	}

	public void setFsid(Long fsid) {
		this.fsid = fsid;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Long getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(Long doctorid) {
		this.doctorid = doctorid;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDocclass() {
		return docclass;
	}

	public void setDocclass(String docclass) {
		this.docclass = docclass;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getDocspeciality() {
		return docspeciality;
	}

	public void setDocspeciality(String docspeciality) {
		this.docspeciality = docspeciality;
	}

	public String getDoccategory() {
		return doccategory;
	}

	public void setDoccategory(String doccategory) {
		this.doccategory = doccategory;
	}

	public BigDecimal getExpqty() {
		return expqty;
	}

	public void setExpqty(BigDecimal expqty) {
		this.expqty = expqty;
	}

	public BigDecimal getExprate() {
		return exprate;
	}

	public void setExprate(BigDecimal exprate) {
		this.exprate = exprate;
	}

	public BigDecimal getExpvalue() {
		return expvalue;
	}

	public void setExpvalue(BigDecimal expvalue) {
		this.expvalue = expvalue;
	}

	public Long getRetailerid() {
		return retailerid;
	}

	public void setRetailerid(Long retailerid) {
		this.retailerid = retailerid;
	}

	public Long getWscustid() {
		return wscustid;
	}

	public void setWscustid(Long wscustid) {
		this.wscustid = wscustid;
	}

	public Long getSchemeid() {
		return schemeid;
	}

	public void setSchemeid(Long schemeid) {
		this.schemeid = schemeid;
	}

	public String getSchemetype() {
		return schemetype;
	}

	public void setSchemetype(String schemetype) {
		this.schemetype = schemetype;
	}

	public String getSchemeExpsDetails() {
		return schemeExpsDetails;
	}

	public void setSchemeExpsDetails(String schemeExpsDetails) {
		this.schemeExpsDetails = schemeExpsDetails;
	}

	public String getSchemeExpsItem() {
		return schemeExpsItem;
	}

	public void setSchemeExpsItem(String schemeExpsItem) {
		this.schemeExpsItem = schemeExpsItem;
	}

	public String getSchemeExpsdesc() {
		return schemeExpsdesc;
	}

	public void setSchemeExpsdesc(String schemeExpsdesc) {
		this.schemeExpsdesc = schemeExpsdesc;
	}

	public String getDtclass() {
		return dtclass;
	}

	public void setDtclass(String dtclass) {
		this.dtclass = dtclass;
	}

	public String getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	public Long getTerritory() {
		return territory;
	}

	public void setTerritory(Long territory) {
		this.territory = territory;
	}

	public Map<String, Object> getImagefiles() {
		return imagefiles;
	}

	public void setImagefiles(Map<String, Object> imagefiles) {
		this.imagefiles = imagefiles;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getCusttypename() {
		return custtypename;
	}

	public void setCusttypename(String custtypename) {
		this.custtypename = custtypename;
	}

	public BigDecimal getCurBiz() {
		return curBiz;
	}

	public void setCurBiz(BigDecimal curBiz) {
		this.curBiz = curBiz;
	}

	public BigDecimal getExpBiz() {
		return expBiz;
	}

	public void setExpBiz(BigDecimal expBiz) {
		this.expBiz = expBiz;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public Long getSeFs_id() {
		return seFs_id;
	}

	public void setSeFs_id(Long seFs_id) {
		this.seFs_id = seFs_id;
	}

	@Override
	public String toString() {
		return "CRMExpenseBean [div_id=" + div_id + ", reqDate=" + reqDate + ", scheddate=" + scheddate + ", finyear="
				+ finyear + ", custtype=" + custtype + ", fsid=" + fsid + ", addr1=" + addr1 + ", addr2=" + addr2
				+ ", addr3=" + addr3 + ", addr4=" + addr4 + ", city=" + city + ", pincode=" + pincode + ", mobile="
				+ mobile + ", email=" + email + ", panno=" + panno + ", hcpno=" + hcpno + ", doctorid=" + doctorid
				+ ", custid=" + custid + ", custname=" + custname + ", category=" + category + ", docclass=" + docclass
				+ ", speciality=" + speciality + ", docspeciality=" + docspeciality + ", doccategory=" + doccategory
				+ ", expqty=" + expqty + ", exprate=" + exprate + ", expvalue=" + expvalue + ", retailerid="
				+ retailerid + ", wscustid=" + wscustid + ", schemeid=" + schemeid + ", schemetype=" + schemetype
				+ ", schemeExpsDetails=" + schemeExpsDetails + ", schemeExpsItem=" + schemeExpsItem
				+ ", schemeExpsdesc=" + schemeExpsdesc + ", dtclass=" + dtclass + ", uploadFile=" + uploadFile
				+ ", territory=" + territory + ", imagefiles=" + imagefiles + ", compCode=" + compCode
				+ ", custtypename=" + custtypename + ", curBiz=" + curBiz + ", expBiz=" + expBiz + ", username="
				+ username + "]";
	}

}
