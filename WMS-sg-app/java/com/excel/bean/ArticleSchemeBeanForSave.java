package com.excel.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ArticleSchemeBeanForSave implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private Long user_loc_id;
	private String subcompanycd;
	private String finYear;
	private String subcompany;
	private String companycd;
	private String salesPeriod;
	private String reqNo;
	private Long articleRequestId;
	private Long selectedCustomer;
	private String sapInvNo;
	private String sapInvDt;
	private String sapPoNo;
	private String sapLrNo;
	private String sapLrDt;
	private List<ArticleSchemeDetailsBean> dataSource;
	private String ipAddr;
	private String transId;
	private String transGstRegNo;
	private BigDecimal distance;
	private String vehicle_no;
	
	public String getVehicle_no() {
		return vehicle_no;
	}

	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public String getSapPoNo() {
		return sapPoNo;
	}

	public void setSapPoNo(String sapPoNo) {
		this.sapPoNo = sapPoNo;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTransGstRegNo() {
		return transGstRegNo;
	}

	public void setTransGstRegNo(String transGstRegNo) {
		this.transGstRegNo = transGstRegNo;
	}

	public Long getUser_loc_id() {
		return user_loc_id;
	}

	public void setUser_loc_id(Long user_loc_id) {
		this.user_loc_id = user_loc_id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public static class schemeProductDetails implements Serializable{

		private static final long serialVersionUID = 1L;
		
		private Long schemeId;
		private Long sale_prod_id;
		private String sale_prod_code_sg;
		private Long sale_prod_code_qty_billed;
		private Long sale_prod_code_qty_free;
		private String article_prod_code;
		private Long article_prod_id;
		private Long article_prod_qty;
		private BigDecimal article_prod_rate;
		private BigDecimal article_prod_value;
		
		public Long getSchemeId() {
			return schemeId;
		}
		public void setSchemeId(Long schemeId) {
			this.schemeId = schemeId;
		}
		public Long getSale_prod_id() {
			return sale_prod_id;
		}
		public void setSale_prod_id(Long sale_prod_id) {
			this.sale_prod_id = sale_prod_id;
		}
		public String getSale_prod_code_sg() {
			return sale_prod_code_sg;
		}
		public void setSale_prod_code_sg(String sale_prod_code_sg) {
			this.sale_prod_code_sg = sale_prod_code_sg;
		}
		public Long getSale_prod_code_qty_billed() {
			return sale_prod_code_qty_billed;
		}
		public void setSale_prod_code_qty_billed(Long sale_prod_code_qty_billed) {
			this.sale_prod_code_qty_billed = sale_prod_code_qty_billed;
		}
		public Long getSale_prod_code_qty_free() {
			return sale_prod_code_qty_free;
		}
		public void setSale_prod_code_qty_free(Long sale_prod_code_qty_free) {
			this.sale_prod_code_qty_free = sale_prod_code_qty_free;
		}
		public String getArticle_prod_code() {
			return article_prod_code;
		}
		public void setArticle_prod_code(String article_prod_code) {
			this.article_prod_code = article_prod_code;
		}
		public Long getArticle_prod_id() {
			return article_prod_id;
		}
		public void setArticle_prod_id(Long article_prod_id) {
			this.article_prod_id = article_prod_id;
		}
		public Long getArticle_prod_qty() {
			return article_prod_qty;
		}
		public void setArticle_prod_qty(Long article_prod_qty) {
			this.article_prod_qty = article_prod_qty;
		}
		public BigDecimal getArticle_prod_rate() {
			return article_prod_rate;
		}
		public void setArticle_prod_rate(BigDecimal article_prod_rate) {
			this.article_prod_rate = article_prod_rate;
		}
		public BigDecimal getArticle_prod_value() {
			return article_prod_value;
		}
		public void setArticle_prod_value(BigDecimal article_prod_value) {
			this.article_prod_value = article_prod_value;
		}
	}

	public String getSubcompanycd() {
		return subcompanycd;
	}

	public void setSubcompanycd(String subcompanycd) {
		this.subcompanycd = subcompanycd;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getSubcompany() {
		return subcompany;
	}

	public void setSubcompany(String subcompany) {
		this.subcompany = subcompany;
	}

	public String getCompanycd() {
		return companycd;
	}

	public void setCompanycd(String companycd) {
		this.companycd = companycd;
	}

	public String getSalesPeriod() {
		return salesPeriod;
	}

	public void setSalesPeriod(String salesPeriod) {
		this.salesPeriod = salesPeriod;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public Long getArticleRequestId() {
		return articleRequestId;
	}

	public void setArticleRequestId(Long articleRequestId) {
		this.articleRequestId = articleRequestId;
	}

	public Long getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(Long selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public String getSapInvNo() {
		return sapInvNo;
	}

	public void setSapInvNo(String sapInvNo) {
		this.sapInvNo = sapInvNo;
	}

	public String getSapInvDt() {
		return sapInvDt;
	}

	public void setSapInvDt(String sapInvDt) {
		this.sapInvDt = sapInvDt;
	}

	public String getSapLrNo() {
		return sapLrNo;
	}

	public void setSapLrNo(String sapLrNo) {
		this.sapLrNo = sapLrNo;
	}

	public String getSapLrDt() {
		return sapLrDt;
	}

	public void setSapLrDt(String sapLrDt) {
		this.sapLrDt = sapLrDt;
	}

	

	public List<ArticleSchemeDetailsBean> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<ArticleSchemeDetailsBean> dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		return "ArticleSchemeBeanForSave [subcompanycd=" + subcompanycd + ", finYear=" + finYear + ", subcompany="
				+ subcompany + ", companycd=" + companycd + ", salesPeriod=" + salesPeriod + ", reqNo=" + reqNo
				+ ", articleRequestId=" + articleRequestId + ", selectedCustomer=" + selectedCustomer + ", sapInvNo="
				+ sapInvNo + ", sapInvDt=" + sapInvDt + ", sapLrNo=" + sapLrNo + ", sapLrDt=" + sapLrDt
				+ ", article_scm_dtls=" + dataSource.toString() + "]";
	}
	
	

}
