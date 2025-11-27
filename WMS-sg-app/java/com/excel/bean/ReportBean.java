package com.excel.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.excel.validations.StrictDateDeserializer;
import com.excel.validations.ValidHrmEmpId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Sachin
 *
 */
public class ReportBean {
	
	private List<String> locList;
	
	 private String cfa_to_stk_ind;
	 
	 
	
	public String getCfa_to_stk_ind() {
		return cfa_to_stk_ind;
	}
	public void setCfa_to_stk_ind(String cfa_to_stk_ind) {
		this.cfa_to_stk_ind = cfa_to_stk_ind;
	}
	public List<String> getLocList() {
		return locList;
	}
	public void setLocList(List<String> locList) {
		this.locList = locList;
	}
	private String divId;
	private String locId;
	
	@JsonDeserialize(using=StrictDateDeserializer.class)
	private Date startDate;
	
	@JsonDeserialize(using=StrictDateDeserializer.class)
	private Date endDate;
	
	private String reportFrom;
	private String orderBy;
	private String userId;
	private String depotLocations;
	private String cfaLocId;
	private String deletedInvoice;
	private String desgWise;
	private String fsType;
	private String empNo;
	private String product;
	private List<String> products;
	
	
	private String empId;
	
	private Long productId;
	private String productName;
	private String sendLocId;
	private String recLocId;
	private String reportOption;
	
	private String zeroStock;
	private String nearExp;
	
	private String subCompId;
	private String locName;
	
	
	//Non Saleble Stock Statement
	private String prod_type_id;
	private String stock_type_id;
	private String zero_stock_id;
	private String print_radio;
	private String loc_name;
	private String prod_name;
	
	private String tranptr;
	private String sub_team_code;
	
	//Detail Register Report1 Filters
	
	private String dispatch_register_report1list ;
	private String dispatch_register_report2list ;
	private String monthofuse;
	private String productname;
	private String deliStatus;
	private String cName;
	private String subTeam;
	private String challanNum;
	private String docName;
	private String employeeNum;
	private String lrNum;
	private String regName;
	private String terrCode;
	private String team;
	private String lrDate;
	private String dmName;
	private String challanDate;
	private String response;
	private String employeeName;
	private String dispatchType;
	//frm here
	private Long dsp_fstaff_id;
	private String dspfstaff_name;
	private String fstaff_employee_no;
	private Long fstaff_id;
	private String fstaff_name;
	private String fstaff_terr_code;
    private String prodType;
    private String period;
    private String prodName;
    private String tName;
    private String empNumber;
    private String docResponse;
    private String empName;

    private String compcd;
    private String selectedReqid;
    private String alloc_period;
    private String sa_ns;
    
    private String onlyExcep;
    
    private String [] prodId;
    private Sales_prod_details_bean [] sales_prod_details;
    
    private List<Long> trd_sch_slno;
    private List<Long> saleprod;
    
    private String trd_scheme_name;
    private String valid_from_dt;
    private String valid_to_dt;
    private String article_name;
    private String sale_prod_name;
    private String brand;
    private String sale_blled_qty;
    private String article_qty;
    private String finYear_id;

    
    
    
	public Sales_prod_details_bean[] getSales_prod_details() {
		return sales_prod_details;
	}
	public void setSales_prod_details(Sales_prod_details_bean[] sales_prod_details) {
		this.sales_prod_details = sales_prod_details;
	}
	public String getFinYear_id() {
		return finYear_id;
	}
	public void setFinYear_id(String finYear_id) {
		this.finYear_id = finYear_id;
	}
	@Override
	public String toString() {
		return "ReportBean [locList=" + locList + ", divId=" + divId + ", locId=" + locId + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", reportFrom=" + reportFrom + ", orderBy=" + orderBy + ", userId=" + userId
				+ ", depotLocations=" + depotLocations + ", cfaLocId=" + cfaLocId + ", deletedInvoice=" + deletedInvoice
				+ ", desgWise=" + desgWise + ", fsType=" + fsType + ", empNo=" + empNo + ", product=" + product
				+ ", products=" + products + ", empId=" + empId + ", productId=" + productId + ", productName="
				+ productName + ", sendLocId=" + sendLocId + ", recLocId=" + recLocId + ", reportOption=" + reportOption
				+ ", zeroStock=" + zeroStock + ", nearExp=" + nearExp + ", subCompId=" + subCompId + ", locName="
				+ locName + ", prod_type_id=" + prod_type_id + ", stock_type_id=" + stock_type_id + ", zero_stock_id="
				+ zero_stock_id + ", print_radio=" + print_radio + ", loc_name=" + loc_name + ", prod_name=" + prod_name
				+ ", tranptr=" + tranptr + ", sub_team_code=" + sub_team_code + ", dispatch_register_report1list="
				+ dispatch_register_report1list + ", dispatch_register_report2list=" + dispatch_register_report2list
				+ ", monthofuse=" + monthofuse + ", productname=" + productname + ", deliStatus=" + deliStatus
				+ ", cName=" + cName + ", subTeam=" + subTeam + ", challanNum=" + challanNum + ", docName=" + docName
				+ ", employeeNum=" + employeeNum + ", lrNum=" + lrNum + ", regName=" + regName + ", terrCode="
				+ terrCode + ", team=" + team + ", lrDate=" + lrDate + ", dmName=" + dmName + ", challanDate="
				+ challanDate + ", response=" + response + ", employeeName=" + employeeName + ", dispatchType="
				+ dispatchType + ", dsp_fstaff_id=" + dsp_fstaff_id + ", dspfstaff_name=" + dspfstaff_name
				+ ", fstaff_employee_no=" + fstaff_employee_no + ", fstaff_id=" + fstaff_id + ", fstaff_name="
				+ fstaff_name + ", fstaff_terr_code=" + fstaff_terr_code + ", prodType=" + prodType + ", period="
				+ period + ", prodName=" + prodName + ", tName=" + tName + ", empNumber=" + empNumber + ", docResponse="
				+ docResponse + ", empName=" + empName + ", compcd=" + compcd + ", selectedReqid=" + selectedReqid
				+ ", alloc_period=" + alloc_period + ", sa_ns=" + sa_ns + ", onlyExcep=" + onlyExcep + ", prodId="
				+ Arrays.toString(prodId) + ", trd_sch_slno=" + trd_sch_slno + ", saleprod=" + saleprod
				+ ", trd_scheme_name=" + trd_scheme_name + ", valid_from_dt=" + valid_from_dt + ", valid_to_dt="
				+ valid_to_dt + ", article_name=" + article_name + ", sale_prod_name=" + sale_prod_name + ", brand="
				+ brand + ", sale_blled_qty=" + sale_blled_qty + ", article_qty=" + article_qty + ", is_correction="
				+ is_correction + ", itemId=" + itemId + ", frmdt=" + frmdt + ", todt=" + todt + ", prod_id=" + prod_id
				+ ", batch_id=" + batch_id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", teamDivision="
				+ teamDivision + ", location=" + location + ", itemwise=" + itemwise + ", batchId=" + batchId
				+ ", period_code=" + period_code + ", fin_year=" + fin_year + ", finYear=" + finYear + ", periodCode="
				+ periodCode + ", pdiv_id=" + pdiv_id + ", pfin_year=" + pfin_year + ", pperiod_code=" + pperiod_code
				+ ", psub_comp_id=" + psub_comp_id + ", pprod_sub_type_id=" + pprod_sub_type_id + ", subCompany="
				+ subCompany + ", ProdSubType=" + ProdSubType + ", levelCode=" + levelCode + ", level=" + level
				+ ", pdfExcel=" + pdfExcel + ", staffId=" + staffId + ", staffName=" + staffName + ", stock_at_cfa_ind="
				+ stock_at_cfa_ind + ", deptloc=" + deptloc + ", alloc_id=" + alloc_id + ", alloc_number="
				+ alloc_number + ", division=" + division + ", alloc_month=" + alloc_month + ", file_download="
				+ file_download + ", fstaff_id_o=" + fstaff_id_o + ", sub_team_name=" + sub_team_name + ", slId=" + slId
				+ ", rlId=" + rlId + ", brandids=" + brandids + ", empid=" + empid + ", teaminfo=" + teaminfo
				+ ", productid=" + productid + ", finyear=" + finyear + ", financialYear=" + financialYear
				+ ", forPeriod=" + forPeriod + ", slab1a=" + slab1a + ", slab1b=" + slab1b + ", slab2a=" + slab2a
				+ ", slab2b=" + slab2b + ", slab3a=" + slab3a + ", slab3b=" + slab3b + ", slab4a=" + slab4a
				+ ", slab4b=" + slab4b + ", slab5a=" + slab5a + ", slab5b=" + slab5b + ", slab6a=" + slab6a
				+ ", report_type=" + report_type + ", id=" + id + ", type=" + type + ", doctype=" + doctype
				+ ", emp_id=" + emp_id + ", username=" + username + ", curryearflg=" + curryearflg + ", finstartdate="
				+ finstartdate + ", finenddate=" + finenddate + ", brandlist=" + brandlist + ", productType="
				+ productType + ", finyearflag=" + finyearflag + ", finyearflg=" + finyearflg + ", div_id=" + div_id
				+ ", loc_id=" + loc_id + ", transporter=" + transporter + ", subcompid=" + subcompid + ", role=" + role
				+ ", location_new=" + location_new + ", division_new=" + division_new + ", destination=" + destination
				+ ", rbmdm=" + rbmdm + ", rmdmfstaff_id=" + rmdmfstaff_id + ", isself=" + isself + ", rate=" + rate
				+ "]";
	}
	public String getSale_blled_qty() {
		return sale_blled_qty;
	}
	public void setSale_blled_qty(String sale_blled_qty) {
		this.sale_blled_qty = sale_blled_qty;
	}
	public String getArticle_qty() {
		return article_qty;
	}
	public void setArticle_qty(String article_qty) {
		this.article_qty = article_qty;
	}
	public String getTrd_scheme_name() {
		return trd_scheme_name;
	}
	public void setTrd_scheme_name(String trd_scheme_name) {
		this.trd_scheme_name = trd_scheme_name;
	}
	public String getValid_from_dt() {
		return valid_from_dt;
	}
	public void setValid_from_dt(String valid_from_dt) {
		this.valid_from_dt = valid_from_dt;
	}
	public String getValid_to_dt() {
		return valid_to_dt;
	}
	public void setValid_to_dt(String valid_to_dt) {
		this.valid_to_dt = valid_to_dt;
	}
	public String getArticle_name() {
		return article_name;
	}
	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}
	public String getSale_prod_name() {
		return sale_prod_name;
	}
	public void setSale_prod_name(String sale_prod_name) {
		this.sale_prod_name = sale_prod_name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public List<Long> getTrd_sch_slno() {
		return trd_sch_slno;
	}
	public void setTrd_sch_slno(List<Long> trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
	}
	public List<Long> getSaleprod() {
		return saleprod;
	}
	public void setSaleprod(List<Long> saleprod) {
		this.saleprod = saleprod;
	}
	private String is_correction;
    
	
    
	public List<String> getProducts() {
		return products;
	}
	public void setProducts(List<String> products) {
		this.products = products;
	}
	public String[] getProdId() {
		return prodId;
	}
	public void setProdId(String[] prodId) {
		this.prodId = prodId;
	}
	public String getIs_correction() {
		return is_correction;
	}
	public void setIs_correction(String is_correction) {
		this.is_correction = is_correction;
	}
	public String getOnlyExcep() {
		return onlyExcep;
	}
	public void setOnlyExcep(String onlyExcep) {
		this.onlyExcep = onlyExcep;
	}
	public String getSa_ns() {
		return sa_ns;
	}
	public void setSa_ns(String sa_ns) {
		this.sa_ns = sa_ns;
	}
	public String getSelectedReqid() {
		return selectedReqid;
	}
	public void setSelectedReqid(String selectedReqid) {
		this.selectedReqid = selectedReqid;
	}
	public String getAlloc_period() {
		return alloc_period;
	}
	public void setAlloc_period(String alloc_period) {
		this.alloc_period = alloc_period;
	}
	public String getCompcd() {
		return compcd;
	}
	public void setCompcd(String compcd) {
		this.compcd = compcd;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDispatch_register_report1list() {
		return dispatch_register_report1list;
	}
	public void setDispatch_register_report1list(String dispatch_register_report1list) {
		this.dispatch_register_report1list = dispatch_register_report1list;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getMonthofuse() {
		return monthofuse;
	}
	public void setMonthofuse(String monthofuse) {
		this.monthofuse = monthofuse;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public String getSub_team_code() {
		return sub_team_code;
	}
	public void setSub_team_code(String sub_team_code) {
		this.sub_team_code = sub_team_code;
	}
	public String getTranptr() {
		return tranptr;
	}
	public void setTranptr(String tranptr) {
		this.tranptr = tranptr;
	}
		//itemwise
		private Integer itemId;
		private String frmdt;
		private String todt;
		private String prod_id;
		
	//batchwise
		private String batch_id;
		
	//ui bean
		private String fromDate;
		private String toDate;
		private String teamDivision;
		private String location;
		private String itemwise;
	    //itemCodeWise
		//private String empId;
		private String batchId;
		
	//allocation detail
		private String period_code;
		private String fin_year;
		//UI
		private String finYear;
		private String periodCode;
		
//ang allocation report 3
		private String pdiv_id;
		private String pfin_year;
		private String pperiod_code;
		private String psub_comp_id;
		private String pprod_sub_type_id;
		
		//UI
		private String subCompany;
		private String ProdSubType;
		
		//field download
		private String levelCode;
		//UI
		private String level;
		private String pdfExcel;
		private Long staffId;
		private String staffName;
		private String stock_at_cfa_ind;
		private String deptloc;
		//allocGen UI
		private String alloc_id;
		private String alloc_number;
		private String division;
		private String alloc_month;
		private char file_download;	
		private String fstaff_id_o;
		private String sub_team_name;
		//Pivot packing slip UI
		private String slId;
		private String rlId;
		
		
		//annual sample plan view report
		private List<String> brandids;
		private String empid;
		private List<String> teaminfo;
		private List<String> productid;
		private String finyear;
		private String financialYear;
		private String forPeriod;
		
		/////////////////ageing Report
		private String slab1a;
		private String slab1b;
		private String slab2a;
		private String slab2b;
		private String slab3a;
		private String slab3b;
		private String slab4a;
		private String slab4b;
		private String slab5a;
		private String slab5b;
		private String slab6a;
		private String report_type;
		private String id;
		private String type;
		
		private String doctype;
		
		//session userid
		@ValidHrmEmpId
		private String emp_id;
		
		
		private String username;
		
		//Dispatch_alloc_monthwise_report
		private String curryearflg;
		
		private String finstartdate;
		private String finenddate;
		private  List<String> brandlist;
		private String productType;
		
		private String finyearflag;
		private String finyearflg;
		
		private List<String> div_id;
		private String loc_id;
		private List<String> transporter;
		private String subcompid;

		private String role;
		
		private List<String> location_new;
		private List<String> division_new;
		private List<String> destination;
		
		private String rbmdm;
		private Long rmdmfstaff_id;
		private String isself;
		private String rate;
		
		
	public String getRate() {
			return rate;
		}
		public void setRate(String rate) {
			this.rate = rate;
		}
	public String getSlab5b() {
			return slab5b;
		}
		public void setSlab5b(String slab5b) {
			this.slab5b = slab5b;
		}
		public String getSlab6a() {
			return slab6a;
		}
		public void setSlab6a(String slab6a) {
			this.slab6a = slab6a;
		}
	public String getDoctype() {
			return doctype;
		}
		public void setDoctype(String doctype) {
			this.doctype = doctype;
		}
	public String getRbmdm() {
			return rbmdm;
		}
		public void setRbmdm(String rbmdm) {
			this.rbmdm = rbmdm;
		}
		public Long getRmdmfstaff_id() {
			return rmdmfstaff_id;
		}
		public void setRmdmfstaff_id(Long rmdmfstaff_id) {
			this.rmdmfstaff_id = rmdmfstaff_id;
		}
		public String getIsself() {
			return isself;
		}
		public void setIsself(String isself) {
			this.isself = isself;
		}
	public List<String> getLocation_new() {
			return location_new;
		}
		public void setLocation_new(List<String> location_new) {
			this.location_new = location_new;
		}
		public List<String> getDivision_new() {
			return division_new;
		}
		public void setDivision_new(List<String> division_new) {
			this.division_new = division_new;
		}
		public List<String> getDestination() {
			return destination;
		}
		public void setDestination(List<String> destination) {
			this.destination = destination;
		}
	public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
	public List<String> getDiv_id() {
			return div_id;
		}
		public void setDiv_id(List<String> div_id) {
			this.div_id = div_id;
		}
		public String getLoc_id() {
			return loc_id;
		}
		public void setLoc_id(String loc_id) {
			this.loc_id = loc_id;
		}
		public List<String> getTransporter() {
			return transporter;
		}
		public void setTransporter(List<String> transporter) {
			this.transporter = transporter;
		}
		public String getSubcompid() {
			return subcompid;
		}
		public void setSubcompid(String subcompid) {
			this.subcompid = subcompid;
		}
	public String getFinyearflag() {
			return finyearflag;
		}
		public void setFinyearflag(String finyearflag) {
			this.finyearflag = finyearflag;
		}
		public String getFinyearflg() {
			return finyearflg;
		}
		public void setFinyearflg(String finyearflg) {
			this.finyearflg = finyearflg;
		}
	public List<String> getBrandlist() {
			return brandlist;
		}
		public void setBrandlist(List<String> brandlist) {
			this.brandlist = brandlist;
		}
		public String getProductType() {
			return productType;
		}
		public void setProductType(String productType) {
			this.productType = productType;
		}
	public String getCurryearflg() {
			return curryearflg;
		}
		public void setCurryearflg(String curryearflg) {
			this.curryearflg = curryearflg;
		}
		public String getFinstartdate() {
			return finstartdate;
		}
		public void setFinstartdate(String finstartdate) {
			this.finstartdate = finstartdate;
		}
		public String getFinenddate() {
			return finenddate;
		}
		public void setFinenddate(String finenddate) {
			this.finenddate = finenddate;
		}
	public String getEmp_id() {
			return emp_id;
		}
		public void setEmp_id(String emp_id) {
			this.emp_id = emp_id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
	public String getSlab1a() {
			return slab1a;
		}
		public void setSlab1a(String slab1a) {
			this.slab1a = slab1a;
		}
		public String getSlab1b() {
			return slab1b;
		}
		public void setSlab1b(String slab1b) {
			this.slab1b = slab1b;
		}
		public String getSlab2a() {
			return slab2a;
		}
		public void setSlab2a(String slab2a) {
			this.slab2a = slab2a;
		}
		public String getSlab2b() {
			return slab2b;
		}
		public void setSlab2b(String slab2b) {
			this.slab2b = slab2b;
		}
		public String getSlab3a() {
			return slab3a;
		}
		public void setSlab3a(String slab3a) {
			this.slab3a = slab3a;
		}
		public String getSlab3b() {
			return slab3b;
		}
		public void setSlab3b(String slab3b) {
			this.slab3b = slab3b;
		}
		public String getSlab4a() {
			return slab4a;
		}
		public void setSlab4a(String slab4a) {
			this.slab4a = slab4a;
		}
		public String getSlab4b() {
			return slab4b;
		}
		public void setSlab4b(String slab4b) {
			this.slab4b = slab4b;
		}
		public String getSlab5a() {
			return slab5a;
		}
		public void setSlab5a(String slab5a) {
			this.slab5a = slab5a;
		}
		public String getReport_type() {
			return report_type;
		}
		public void setReport_type(String report_type) {
			this.report_type = report_type;
		}
	public List<String> getBrandids() {
			return brandids;
		}
		public void setBrandids(List<String> brandids) {
			this.brandids = brandids;
		}
		public String getEmpid() {
			return empid;
		}
		public void setEmpid(String empid) {
			this.empid = empid;
		}
		public List<String> getTeaminfo() {
			return teaminfo;
		}
		public void setTeaminfo(List<String> teaminfo) {
			this.teaminfo = teaminfo;
		}
		public List<String> getProductid() {
			return productid;
		}
		public void setProductid(List<String> productid) {
			this.productid = productid;
		}
		public String getFinyear() {
			return finyear;
		}
		public void setFinyear(String finyear) {
			this.finyear = finyear;
		}
	public String getDivId() {
		return divId;
	}
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public String getLocId() {
		return locId;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getReportFrom() {
		return reportFrom;
	}
	public void setReportFrom(String reportFrom) {
		this.reportFrom = reportFrom;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDepotLocations() {
		return depotLocations;
	}
	public void setDepotLocations(String depotLocations) {
		this.depotLocations = depotLocations;
	}
	public String getCfaLocId() {
		return cfaLocId;
	}
	public void setCfaLocId(String cfaLocId) {
		this.cfaLocId = cfaLocId;
	}
	public String getDeletedInvoice() {
		return deletedInvoice;
	}
	public void setDeletedInvoice(String deletedInvoice) {
		this.deletedInvoice = deletedInvoice;
	}
	public String getDesgWise() {
		return desgWise;
	}
	public void setDesgWise(String desgWise) {
		this.desgWise = desgWise;
	}
	public String getFsType() {
		return fsType;
	}
	public void setFsType(String fsType) {
		this.fsType = fsType;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSendLocId() {
		return sendLocId;
	}
	public void setSendLocId(String sendLocId) {
		this.sendLocId = sendLocId;
	}
	public String getRecLocId() {
		return recLocId;
	}
	public void setRecLocId(String recLocId) {
		this.recLocId = recLocId;
	}
	public String getReportOption() {
		return reportOption;
	}
	public void setReportOption(String reportOption) {
		this.reportOption = reportOption;
	}
	public String getZeroStock() {
		return zeroStock;
	}
	public void setZeroStock(String zeroStock) {
		this.zeroStock = zeroStock;
	}
	public String getNearExp() {
		return nearExp;
	}
	public void setNearExp(String nearExp) {
		this.nearExp = nearExp;
	}
	public String getSubCompId() {
		return subCompId;
	}
	public void setSubCompId(String subCompId) {
		this.subCompId = subCompId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getFrmdt() {
		return frmdt;
	}
	public void setFrmdt(String frmdt) {
		this.frmdt = frmdt;
	}
	public String getTodt() {
		return todt;
	}
	public void setTodt(String todt) {
		this.todt = todt;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getPeriod_code() {
		return period_code;
	}
	public void setPeriod_code(String period_code) {
		this.period_code = period_code;
	}
	public String getFin_year() {
		return fin_year;
	}
	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}
	
	public String getProd_type_id() {
		return prod_type_id;
	}
	public void setProd_type_id(String prod_type_id) {
		this.prod_type_id = prod_type_id;
	}
	public String getStock_type_id() {
		return stock_type_id;
	}
	public void setStock_type_id(String stock_type_id) {
		this.stock_type_id = stock_type_id;
	}
	public String getZero_stock_id() {
		return zero_stock_id;
	}
	public void setZero_stock_id(String zero_stock_id) {
		this.zero_stock_id = zero_stock_id;
	}
	public String getPrint_radio() {
		return print_radio;
	}
	public void setPrint_radio(String print_radio) {
		this.print_radio = print_radio;
	}
	public String getLoc_name() {
		return loc_name;
	}
	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getTeamDivision() {
		return teamDivision;
	}
	public void setTeamDivision(String teamDivision) {
		this.teamDivision = teamDivision;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getItemwise() {
		return itemwise;
	}
	public void setItemwise(String itemwise) {
		this.itemwise = itemwise;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getPeriodCode() {
		return periodCode;
	}
	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}
	public String getPdiv_id() {
		return pdiv_id;
	}
	public void setPdiv_id(String pdiv_id) {
		this.pdiv_id = pdiv_id;
	}
	public String getPfin_year() {
		return pfin_year;
	}
	public void setPfin_year(String pfin_year) {
		this.pfin_year = pfin_year;
	}
	public String getPperiod_code() {
		return pperiod_code;
	}
	public void setPperiod_code(String pperiod_code) {
		this.pperiod_code = pperiod_code;
	}
	public String getPsub_comp_id() {
		return psub_comp_id;
	}
	public void setPsub_comp_id(String psub_comp_id) {
		this.psub_comp_id = psub_comp_id;
	}
	public String getPprod_sub_type_id() {
		return pprod_sub_type_id;
	}
	public void setPprod_sub_type_id(String pprod_sub_type_id) {
		this.pprod_sub_type_id = pprod_sub_type_id;
	}
	public String getSubCompany() {
		return subCompany;
	}
	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}
	public String getProdSubType() {
		return ProdSubType;
	}
	public void setProdSubType(String prodSubType) {
		ProdSubType = prodSubType;
	}
	
	//Field download
		public String getLevelCode() {
			return levelCode;
		}
		public void setLevelCode(String levelCode) {
			this.levelCode = levelCode;
		}
		//UI (field download)
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public String getPdfExcel() {
			return pdfExcel;
		}
		public void setPdfExcel(String pdfExcel) {
			this.pdfExcel = pdfExcel;
		}
		public Long getStaffId() {
			return staffId;
		}
		public void setStaffId(Long staffId) {
			this.staffId = staffId;
		}
		public String getStaffName() {
			return staffName;
		}
		public void setStaffName(String staffName) {
			this.staffName = staffName;
		}
		public String getStock_at_cfa_ind() {
			return stock_at_cfa_ind;
		}
		public void setStock_at_cfa_ind(String stock_at_cfa_ind) {
			this.stock_at_cfa_ind = stock_at_cfa_ind;
		}
		public String getDeptloc() {
			return deptloc;
		}
		public void setDeptloc(String deptloc) {
			this.deptloc = deptloc;
		}
		public String getAlloc_id() {
			return alloc_id;
		}
		public void setAlloc_id(String alloc_id) {
			this.alloc_id = alloc_id;
		}
		public String getAlloc_number() {
			return alloc_number;
		}
		public void setAlloc_number(String alloc_number) {
			this.alloc_number = alloc_number;
		}
		public String getFstaff_id_o() {
			return fstaff_id_o;
		}
		public void setFstaff_id_o(String fstaff_id_o) {
			this.fstaff_id_o = fstaff_id_o;
		}
		//Pivot packing slip UI
		public String getSlId() {
			return slId;
		}
		public void setSlId(String slId) {
			this.slId = slId;
		}
		public String getRlId() {
			return rlId;
		}
		public void setRlId(String rlId) {
			this.rlId = rlId;
		}
		public String getFinancialYear() {
			return financialYear;
		}
		public void setFinancialYear(String financialYear) {
			this.financialYear = financialYear;
		}
		public String getForPeriod() {
			return forPeriod;
		}
		public void setForPeriod(String forPeriod) {
			this.forPeriod = forPeriod;
		}
		public String getDivision() {
			return division;
		}
		public void setDivision(String division) {
			this.division = division;
		}
		public String getAlloc_month() {
			return alloc_month;
		}
		public void setAlloc_month(String alloc_month) {
			this.alloc_month = alloc_month;
		}
		public char getFile_download() {
			return file_download;
		}
		public void setFile_download(char file_download) {
			this.file_download = file_download;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getSub_team_name() {
			return sub_team_name;
		}
		public void setSub_team_name(String sub_team_name) {
			this.sub_team_name = sub_team_name;
		}
		public String getDeliStatus() {
			return deliStatus;
		}
		public void setDeliStatus(String deliStatus) {
			this.deliStatus = deliStatus;
		}
		public String getcName() {
			return cName;
		}
		public void setcName(String cName) {
			this.cName = cName;
		}
		public String getSubTeam() {
			return subTeam;
		}
		public void setSubTeam(String subTeam) {
			this.subTeam = subTeam;
		}
		public String getChallanNum() {
			return challanNum;
		}
		public void setChallanNum(String challanNum) {
			this.challanNum = challanNum;
		}
		public String getDocName() {
			return docName;
		}
		public void setDocName(String docName) {
			this.docName = docName;
		}
		public String getEmployeeNum() {
			return employeeNum;
		}
		public void setEmployeeNum(String employeeNum) {
			this.employeeNum = employeeNum;
		}
		public String getLrNum() {
			return lrNum;
		}
		public void setLrNum(String lrNum) {
			this.lrNum = lrNum;
		}
		public String getRegName() {
			return regName;
		}
		public void setRegName(String regName) {
			this.regName = regName;
		}
		public String getTerrCode() {
			return terrCode;
		}
		public void setTerrCode(String terrCode) {
			this.terrCode = terrCode;
		}
		public String getTeam() {
			return team;
		}
		public void setTeam(String team) {
			this.team = team;
		}
		public String getLrDate() {
			return lrDate;
		}
		public void setLrDate(String lrDate) {
			this.lrDate = lrDate;
		}
		public String getDmName() {
			return dmName;
		}
		public void setDmName(String dmName) {
			this.dmName = dmName;
		}
		public String getChallanDate() {
			return challanDate;
		}
		public void setChallanDate(String challanDate) {
			this.challanDate = challanDate;
		}
		public String getResponse() {
			return response;
		}
		public void setResponse(String response) {
			this.response = response;
		}
		public String getDispatchType() {
			return dispatchType;
		}
		public void setDispatchType(String dispatchType) {
			this.dispatchType = dispatchType;
		}
		public String getDispatch_register_report2list() {
			return dispatch_register_report2list;
		}
		public void setDispatch_register_report2list(String dispatch_register_report2list) {
			this.dispatch_register_report2list = dispatch_register_report2list;
		}
		public Long getDsp_fstaff_id() {
			return dsp_fstaff_id;
		}
		public void setDsp_fstaff_id(Long dsp_fstaff_id) {
			this.dsp_fstaff_id = dsp_fstaff_id;
		}
		public String getDspfstaff_name() {
			return dspfstaff_name;
		}
		public void setDspfstaff_name(String dspfstaff_name) {
			this.dspfstaff_name = dspfstaff_name;
		}
		public String getFstaff_employee_no() {
			return fstaff_employee_no;
		}
		public void setFstaff_employee_no(String fstaff_employee_no) {
			this.fstaff_employee_no = fstaff_employee_no;
		}
		public Long getFstaff_id() {
			return fstaff_id;
		}
		public void setFstaff_id(Long fstaff_id) {
			this.fstaff_id = fstaff_id;
		}
		public String getFstaff_name() {
			return fstaff_name;
		}
		public void setFstaff_name(String fstaff_name) {
			this.fstaff_name = fstaff_name;
		}
		public String getFstaff_terr_code() {
			return fstaff_terr_code;
		}
		public void setFstaff_terr_code(String fstaff_terr_code) {
			this.fstaff_terr_code = fstaff_terr_code;
		}
		public String getProdType() {
			return prodType;
		}
		public void setProdType(String prodType) {
			this.prodType = prodType;
		}
		public String getPeriod() {
			return period;
		}
		public void setPeriod(String period) {
			this.period = period;
		}
		public String getProdName() {
			return prodName;
		}
		public void setProdName(String prodName) {
			this.prodName = prodName;
		}
		public String gettName() {
			return tName;
		}
		public void settName(String tName) {
			this.tName = tName;
		}
		public String getEmpNumber() {
			return empNumber;
		}
		public void setEmpNumber(String empNumber) {
			this.empNumber = empNumber;
		}
		public String getDocResponse() {
			return docResponse;
		}
		public void setDocResponse(String docResponse) {
			this.docResponse = docResponse;
		}

		
		
}
