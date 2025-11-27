package com.excel.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ArticleSchemeDetailsBean {
	private Integer trd_scheme_id;
	private String trd_scheme_code;
	private String trd_scheme_name;
	private String trd_scheme_descr;
	private Date valid_from_dt;
	private Date valid_to_dt;
	private String apply_to;
	private BigDecimal billed_value;
	private Integer article_prod_id;
	private String article_prod_cd;
	private String article_prod_descr;
	private BigDecimal article_prod_rate;
	private Integer article_qty_per_tot_val;
	private List<ArticleSchemeTrdDetails> detailList;
	private Date closure_date;
	
	
	//fields that are zero while entry
	private BigDecimal tot_billed_value_entered;
	private Integer article_prod_qty_tot;
	private BigDecimal article_prod_value;
	private Date apply_inv_from;
	
	
	private Integer sale_prod_id_for_dupl_check;
	public Integer getSale_prod_id_for_dupl_check() {
		return sale_prod_id_for_dupl_check;
	}

	public void setSale_prod_id_for_dupl_check(Integer sale_prod_id_for_dupl_check) {
		this.sale_prod_id_for_dupl_check = sale_prod_id_for_dupl_check;
	}

	public static class ArticleSchemeTrdDetails{
		private Integer trd_scheme_dtl_id;
		private Integer sale_prod_id;
		private String sale_prod_name;
		private String sale_prod_code_erp;
		private Integer per_sale_qty_billed;
		private Integer per_sale_qty_free;
		private Integer per_article_prod_qty;
		//fields that are zero while entry
		private Integer per_sale_qty_billed_entered;
		private BigDecimal per_billed_value_entered;
		private Integer per_article_prod_qty_updated;
		
		public Integer getPer_sale_qty_billed_entered() {
			return per_sale_qty_billed_entered;
		}
		public void setPer_sale_qty_billed_entered(Integer per_sale_qty_billed_entered) {
			this.per_sale_qty_billed_entered = per_sale_qty_billed_entered;
		}
		public BigDecimal getPer_billed_value_entered() {
			return per_billed_value_entered;
		}
		public void setPer_billed_value_entered(BigDecimal per_billed_value_entered) {
			this.per_billed_value_entered = per_billed_value_entered;
		}
		public Integer getPer_article_prod_qty_updated() {
			return per_article_prod_qty_updated;
		}
		public void setPer_article_prod_qty_updated(Integer per_article_prod_qty_updated) {
			this.per_article_prod_qty_updated = per_article_prod_qty_updated;
		}
		public Integer getTrd_scheme_dtl_id() {
			return trd_scheme_dtl_id;
		}
		public void setTrd_scheme_dtl_id(Integer trd_scheme_dtl_id) {
			this.trd_scheme_dtl_id = trd_scheme_dtl_id;
		}
		public Integer getSale_prod_id() {
			return sale_prod_id;
		}
		public void setSale_prod_id(Integer sale_prod_id) {
			this.sale_prod_id = sale_prod_id;
		}
		public String getSale_prod_name() {
			return sale_prod_name;
		}
		public void setSale_prod_name(String sale_prod_name) {
			this.sale_prod_name = sale_prod_name;
		}
		public String getSale_prod_code_erp() {
			return sale_prod_code_erp;
		}
		public void setSale_prod_code_erp(String sale_prod_code_erp) {
			this.sale_prod_code_erp = sale_prod_code_erp;
		}
		public Integer getPer_sale_qty_billed() {
			return per_sale_qty_billed;
		}
		public void setPer_sale_qty_billed(Integer per_sale_qty_billed) {
			this.per_sale_qty_billed = per_sale_qty_billed;
		}
		public Integer getPer_sale_qty_free() {
			return per_sale_qty_free;
		}
		public void setPer_sale_qty_free(Integer per_sale_qty_free) {
			this.per_sale_qty_free = per_sale_qty_free;
		}
		public Integer getPer_article_prod_qty() {
			return per_article_prod_qty;
		}
		public void setPer_article_prod_qty(Integer per_article_prod_qty) {
			this.per_article_prod_qty = per_article_prod_qty;
		}
		@Override
		public String toString() {
			return "ArticleSchemeTrdDetails [trd_scheme_dtl_id=" + trd_scheme_dtl_id + ", sale_prod_id=" + sale_prod_id
					+ ", sale_prod_name=" + sale_prod_name + ", sale_prod_code_erp=" + sale_prod_code_erp
					+ ", per_sale_qty_billed=" + per_sale_qty_billed + ", per_sale_qty_free=" + per_sale_qty_free
					+ ", per_article_prod_qty=" + per_article_prod_qty + ", per_sale_qty_billed_entered="
					+ per_sale_qty_billed_entered + ", per_billed_value_entered=" + per_billed_value_entered
					+ ", per_article_prod_qty_updated=" + per_article_prod_qty_updated + "]";
		}
	}
	
	
	public Date getClosure_date() {
		return closure_date;
	}

	public void setClosure_date(Date closure_date) {
		this.closure_date = closure_date;
	}

	public Date getApply_inv_from() {
		return apply_inv_from;
	}

	public void setApply_inv_from(Date apply_inv_from) {
		this.apply_inv_from = apply_inv_from;
	}

	public Integer getTrd_scheme_id() {
		return trd_scheme_id;
	}

	public void setTrd_scheme_id(Integer trd_scheme_id) {
		this.trd_scheme_id = trd_scheme_id;
	}

	public String getTrd_scheme_code() {
		return trd_scheme_code;
	}

	public void setTrd_scheme_code(String trd_scheme_code) {
		this.trd_scheme_code = trd_scheme_code;
	}

	public String getTrd_scheme_name() {
		return trd_scheme_name;
	}

	public void setTrd_scheme_name(String trd_scheme_name) {
		this.trd_scheme_name = trd_scheme_name;
	}

	public String getTrd_scheme_descr() {
		return trd_scheme_descr;
	}

	public void setTrd_scheme_descr(String trd_scheme_descr) {
		this.trd_scheme_descr = trd_scheme_descr;
	}

	public Date getValid_from_dt() {
		return valid_from_dt;
	}

	public void setValid_from_dt(Date valid_from_dt) {
		this.valid_from_dt = valid_from_dt;
	}

	public Date getValid_to_dt() {
		return valid_to_dt;
	}

	public void setValid_to_dt(Date valid_to_dt) {
		this.valid_to_dt = valid_to_dt;
	}

	public String getApply_to() {
		return apply_to;
	}

	public void setApply_to(String apply_to) {
		this.apply_to = apply_to;
	}

	public BigDecimal getBilled_value() {
		return billed_value;
	}

	public void setBilled_value(BigDecimal billed_value) {
		this.billed_value = billed_value;
	}

	public BigDecimal getTot_billed_value_entered() {
		return tot_billed_value_entered;
	}

	public void setTot_billed_value_entered(BigDecimal tot_billed_value_entered) {
		this.tot_billed_value_entered = tot_billed_value_entered;
	}

	public Integer getArticle_prod_id() {
		return article_prod_id;
	}

	public void setArticle_prod_id(Integer article_prod_id) {
		this.article_prod_id = article_prod_id;
	}

	public String getArticle_prod_cd() {
		return article_prod_cd;
	}

	public void setArticle_prod_cd(String article_prod_cd) {
		this.article_prod_cd = article_prod_cd;
	}

	public String getArticle_prod_descr() {
		return article_prod_descr;
	}

	public void setArticle_prod_descr(String article_prod_descr) {
		this.article_prod_descr = article_prod_descr;
	}

	public Integer getArticle_prod_qty_tot() {
		return article_prod_qty_tot;
	}

	public void setArticle_prod_qty_tot(Integer article_prod_qty_tot) {
		this.article_prod_qty_tot = article_prod_qty_tot;
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

	public Integer getArticle_qty_per_tot_val() {
		return article_qty_per_tot_val;
	}

	public void setArticle_qty_per_tot_val(Integer article_qty_per_tot_val) {
		this.article_qty_per_tot_val = article_qty_per_tot_val;
	}

	public List<ArticleSchemeTrdDetails> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ArticleSchemeTrdDetails> detailList) {
		this.detailList = detailList;
	}

	@Override
	public String toString() {
		return "ArticleSchemeDetailsBean [trd_scheme_id=" + trd_scheme_id + ", trd_scheme_code=" + trd_scheme_code
				+ ", trd_scheme_name=" + trd_scheme_name + ", trd_scheme_descr=" + trd_scheme_descr + ", valid_from_dt="
				+ valid_from_dt + ", valid_to_dt=" + valid_to_dt + ", apply_to=" + apply_to + ", billed_value="
				+ billed_value + ", tot_billed_value_entered=" + tot_billed_value_entered + ", article_prod_id="
				+ article_prod_id + ", article_prod_cd=" + article_prod_cd + ", article_prod_descr="
				+ article_prod_descr + ", article_prod_qty_tot=" + article_prod_qty_tot + ", article_prod_rate="
				+ article_prod_rate + ", article_prod_value=" + article_prod_value + ", article_qty_per_tot_val="
				+ article_qty_per_tot_val + ", detailList=" + detailList.toString() + "]";
	}
	
	
	
	

}
