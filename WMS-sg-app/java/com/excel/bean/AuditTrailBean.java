package com.excel.bean;

import java.util.Date;
import java.util.List;

public class AuditTrailBean {
	//FieldMaster 
	private String divId;
	private String level;
	private String audit;
	private String fstafftraining;
		//UI
		private String division;
		private String fsCategory;
		private String hierarchyLevel;
		private String pdfExcel;
		
	//ProductMaster audit trail
	private String prod_id;
	private String subcompany;
	private String brand;
	private String product_type;
		//UI
		private String productType;
		private String subCompany;
		private List<String> saBrand;
		private String product;
		private String checkme;
	
	//Supplier Master
	private String suptype;
	private String Frmdt;
	private String Todt;
		//UI
		private String supplierType;
		private String finYear;
		private Date fromDate;
		private Date toDate;
	
	//Batch
	private String locId;
	private String username;
		//UI
		private String item;
		private String location;
	//GRN
	private String userId;
		//UI
		private String empId;
		
		//UI
		private Date startdate;
		private Date enddate;
		private String approval;
		
		private String finyearflag;
		
		
	
	public String getFinyearflag() {
			return finyearflag;
		}
		public void setFinyearflag(String finyearflag) {
			this.finyearflag = finyearflag;
		}
	public String getApproval() {
			return approval;
		}
		public void setApproval(String approval) {
			this.approval = approval;
		}
	public Date getStartdate() {
			return startdate;
		}
		public void setStartdate(Date startdate) {
			this.startdate = startdate;
		}
		public Date getEnddate() {
			return enddate;
		}
		public void setEnddate(Date enddate) {
			this.enddate = enddate;
		}
	public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
	public String getDivId() {
		return divId;
	}
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public String getFstafftraining() {
		return fstafftraining;
	}
	public void setFstafftraining(String fstafftraining) {
		this.fstafftraining = fstafftraining;
	}
	
	//UI
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getFsCategory() {
		return fsCategory;
	}
	public void setFsCategory(String fsCategory) {
		this.fsCategory = fsCategory;
	}
	public String getHierarchyLevel() {
		return hierarchyLevel;
	}
	public void setHierarchyLevel(String hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}
	public String getPdfExcel() {
		return pdfExcel;
	}
	public void setPdfExcel(String pdfExcel) {
		this.pdfExcel = pdfExcel;
	}
	
	//Product master audit trail 
	
	
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getSubcompany() {
		return subcompany;
	}
	public void setSubcompany(String subcompany) {
		this.subcompany = subcompany;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
//UI beans
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSubCompany() {
		return subCompany;
	}
	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}
	
	public List<String> getSaBrand() {
		return saBrand;
	}
	public void setSaBrand(List<String> saBrand) {
		this.saBrand = saBrand;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCheckme() {
		return checkme;
	}
	public void setCheckme(String checkme) {
		this.checkme = checkme;
	}
	
	//supplier master audit report
	public String getSuptype() {
		return suptype;
	}
	public void setSuptype(String suptype) {
		this.suptype = suptype;
	}
	public String getFrmdt() {
		return Frmdt;
	}
	public void setFrmdt(String frmdt) {
		Frmdt = frmdt;
	}
	public String getTodt() {
		return Todt;
	}
	public void setTodt(String todt) {
		Todt = todt;
	}
	
	
	
	//UI
	public String getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	
	//Batch
	public String getLocId() {
		return locId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}
	//Batch UI
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	//GRN
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	//UI
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
