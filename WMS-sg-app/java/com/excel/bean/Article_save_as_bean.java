package com.excel.bean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article_save_as_bean {

	
	private String sub_comp_cd;
	
	private String subcompany;
	private String sale_product_id;
	private String sales_product_division;
	private long Scheme_division;
	
	public String getSales_product_division() {
		return sales_product_division;
	}

	public void setSales_product_division(String sales_product_division) {
		this.sales_product_division = sales_product_division;
	}

	public long getScheme_division() {
		return Scheme_division;
	}

	public void setScheme_division(long scheme_division) {
		Scheme_division = scheme_division;
	}

	private String article_id;
	private String trd_sch_slno;
	private String remarks;
	private String finalyear;
	private String value_article;


	private String company_code;
	private String scheme_code;
	private String date;
	private String scheme_Name;
	private String scheme_Description;
	
	private Date valid_From;
	private Date valid_To;
	private Date apply_invoice_from;
	private String [] location_Specific;
	
	
	
	
	
	




	public Date getApply_invoice_from() {
		return apply_invoice_from;
	}

	public void setApply_invoice_from(Date apply_invoice_from) {
		this.apply_invoice_from = apply_invoice_from;
	}

	public String[] getLocation_Specific() {
		return location_Specific;
	}

	public void setLocation_Specific(String[] location_Specific) {
		this.location_Specific = location_Specific;
	}

	private String apply_to_Individual_Invoice;
	private String article_Type;
	private String specific;
	private String sale_product_code;
	private String article_code;
	
	private long billed;
	private long free;
	private long qty;
	
	private long grn_qty;
	
	public long getGrn_qty() {
		return grn_qty;
	}

	public void setGrn_qty(long grn_qty) {
		this.grn_qty = grn_qty;
	}

	private BigDecimal rate;
	private BigDecimal total_value;
	private String created_by;
	private Date created_date;
	private SalesProdDetails [] sales_prod_details;
	
//	private List<MultipartFile>files;

	public static class SalesProdDetails{
		private Long sale_PROD_ID;
		private Long billed;
		private Long free;
		private Long article_Qty;
		private String sales_product_code;
		private String sales_product_erpcode;
		public Long getSale_PROD_ID() {
			return sale_PROD_ID;
		}
		public void setSale_PROD_ID(Long sale_PROD_ID) {
			this.sale_PROD_ID = sale_PROD_ID;
		}
		public Long getBilled() {
			return billed;
		}
		public void setBilled(Long billed) {
			this.billed = billed;
		}
		public Long getFree() {
			return free;
		}
		public void setFree(Long free) {
			this.free = free;
		}
		public Long getArticle_Qty() {
			return article_Qty;
		}
		public void setArticle_Qty(Long article_Qty) {
			this.article_Qty = article_Qty;
		}
		public String getSales_product_code() {
			return sales_product_code;
		}
		public void setSales_product_code(String sales_product_code) {
			this.sales_product_code = sales_product_code;
		}
		public String getSales_product_erpcode() {
			return sales_product_erpcode;
		}
		public void setSales_product_erpcode(String sales_product_erpcode) {
			this.sales_product_erpcode = sales_product_erpcode;
		}
		
		
	}

	
	
	public SalesProdDetails[] getSales_prod_details() {
		return sales_prod_details;
	}

	public void setSales_prod_details(SalesProdDetails[] sales_prod_details) {
		this.sales_prod_details = sales_prod_details;
	}

	public String getValue_article() {
		return value_article;
	}

	public void setValue_article(String value_article) {
		this.value_article = value_article;
	}

	private String Apply_Scheme_to;
	
	public String getApply_Scheme_to() {
		return Apply_Scheme_to;
	}

	public void setApply_Scheme_to(String apply_Scheme_to) {
		Apply_Scheme_to = apply_Scheme_to;
	}

	private List<SkuQtySalesProductBean> arrayOfSalesProdbeans;




	public String getFinalyear() {
		return finalyear;
	}

	public void setFinalyear(String finalyear) {
		this.finalyear = finalyear;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTrd_sch_slno() {
		return trd_sch_slno;
	}

	public void setTrd_sch_slno(String trd_sch_slno) {
		this.trd_sch_slno = trd_sch_slno;
	}
//
//	public List<MultipartFile> getFiles() {
//		return files;
//	}
//
//	public void setFiles(List<MultipartFile> files) {
//		this.files = files;
//	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getScheme_code() {
		return scheme_code;
	}

	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getScheme_Name() {
		return scheme_Name;
	}

	public void setScheme_Name(String scheme_Name) {
		this.scheme_Name = scheme_Name;
	}

	public String getScheme_Description() {
		return scheme_Description;
	}

	public void setScheme_Description(String scheme_Description) {
		this.scheme_Description = scheme_Description;
	}

	public Date getValid_From() {
		return valid_From;
	}

	public void setValid_From(Date valid_From) {
		this.valid_From = valid_From;
	}

	public Date getValid_To() {
		return valid_To;
	}

	public void setValid_To(Date valid_To) {
		this.valid_To = valid_To;
	}



//	public String[] getLocation_Specific() {
//		return location_Specific;
//	}
//
//	public void setLocation_Specific(String[] location_Specific) {
//		this.location_Specific = location_Specific;
//	}

	public String getApply_to_Individual_Invoice() {
		return apply_to_Individual_Invoice;
	}

	public void setApply_to_Individual_Invoice(String apply_to_Individual_Invoice) {
		this.apply_to_Individual_Invoice = apply_to_Individual_Invoice;
	}

	public String getArticle_Type() {
		return article_Type;
	}

	public void setArticle_Type(String article_Type) {
		this.article_Type = article_Type;
	}

	public String getSpecific() {
		return specific;
	}

	public void setSpecific(String specific) {
		this.specific = specific;
	}

	public String getSale_product_code() {
		return sale_product_code;
	}

	public void setSale_product_code(String sale_product_code) {
		this.sale_product_code = sale_product_code;
	}

	public String getArticle_code() {
		return article_code;
	}

	public void setArticle_code(String article_code) {
		this.article_code = article_code;
	}

	public long getBilled() {
		return billed;
	}

	public void setBilled(long billed) {
		this.billed = billed;
	}

	public long getFree() {
		return free;
	}

	public void setFree(long free) {
		this.free = free;
	}

	public long getQty() {
		return qty;
	}

	public void setQty(long qty) {
		this.qty = qty;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getTotal_value() {
		return total_value;
	}

	public void setTotal_value(BigDecimal total_value) {
		this.total_value = total_value;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}



	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getSub_comp_cd() {
		return sub_comp_cd;
	}

	public void setSub_comp_cd(String sub_comp_cd) {
		this.sub_comp_cd = sub_comp_cd;
	}

	public String getSubcompany() {
		return subcompany;
	}

	public void setSubcompany(String subcompany) {
		this.subcompany = subcompany;
	}

	public String getSale_product_id() {
		return sale_product_id;
	}

	public void setSale_product_id(String sale_product_id) {
		this.sale_product_id = sale_product_id;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	@Override
	public String toString() {
		return "Article_save_as_bean [sub_comp_cd=" + sub_comp_cd + ", subcompany=" + subcompany + ", sale_product_id="
				+ sale_product_id + ", article_id=" + article_id + ", trd_sch_slno=" + trd_sch_slno + ", remarks="
				+ remarks + ", finalyear=" + finalyear + ", value_article=" + value_article + ", company_code="
				+ company_code + ", scheme_code=" + scheme_code + ", date=" + date + ", scheme_Name=" + scheme_Name
				+ ", scheme_Description=" + scheme_Description + ", valid_From=" + valid_From + ", valid_To=" + valid_To
				+ ", apply_invoice_from=" + apply_invoice_from + ", location_Specific="
				+ Arrays.toString(location_Specific) + ", apply_to_Individual_Invoice=" + apply_to_Individual_Invoice
				+ ", article_Type=" + article_Type + ", specific=" + specific + ", sale_product_code="
				+ sale_product_code + ", article_code=" + article_code + ", billed=" + billed + ", free=" + free
				+ ", qty=" + qty + ", rate=" + rate + ", total_value=" + total_value + ", created_by=" + created_by
				+ ", created_date=" + created_date + ", sales_prod_details=" + Arrays.toString(sales_prod_details)
				+ ", Apply_Scheme_to=" + Apply_Scheme_to + ", arrayOfSalesProdbeans=" + arrayOfSalesProdbeans + "]";
	}





}
