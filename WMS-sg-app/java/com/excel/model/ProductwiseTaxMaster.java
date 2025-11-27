package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name="PRODUCTWISE_TAX_MASTER")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
@IdClass(ProductWiseTax_Master_Embed.class)
public class ProductwiseTaxMaster {

	private static final long serialVersionUID = -6643009535738018376L;

	@Id
	@AttributeOverrides({
	@AttributeOverride(name = "prod_code",
	column = @Column(name="PROD_CODE")),
	@AttributeOverride(name = "state_id",
	column = @Column(name="STATE_ID"))
	})
	private String  prod_code;
	private Long state_id;
	
	
	@Column(name="TAX_CD")
	private String tax_cd;
	
	@Column(name="OCTROI_INPUT_PARAM")
	private String octroi_input_param;
	
	@Column(name="OCTROI_OUTPUT_PARAM")
	private String octroi_output_param;
	
	@Column(name="OLD_TAX_CD")
	private String old_tax_cd;
	
	@Column(name="DELETED")
	private String deleted;
	
	@Column(name="PROD_DISC")
	private BigDecimal prod_disc;
	
	@Column(name="TAX_DATE")
	private Date tax_date;
	
	@Column(name="TAX_CLASS_ID")
	private Integer  tax_class_id;
	
	
	@Column(name="LAST_MOD_BY")
	private String last_mod_by;
	
	
	@Column(name="LAST_MOD_DATE")
	private Date last_mod_date;
	
	
	@Column(name="COMPANY_CD")
	private String company_cd;


	public String getProd_code() {
		return prod_code;
	}


	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}


	public Long getState_id() {
		return state_id;
	}


	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}


	public String getTax_cd() {
		return tax_cd;
	}


	public void setTax_cd(String tax_cd) {
		this.tax_cd = tax_cd;
	}


	public String getOctroi_input_param() {
		return octroi_input_param;
	}


	public void setOctroi_input_param(String octroi_input_param) {
		this.octroi_input_param = octroi_input_param;
	}


	public String getOctroi_output_param() {
		return octroi_output_param;
	}


	public void setOctroi_output_param(String octroi_output_param) {
		this.octroi_output_param = octroi_output_param;
	}


	public String getOld_tax_cd() {
		return old_tax_cd;
	}


	public void setOld_tax_cd(String old_tax_cd) {
		this.old_tax_cd = old_tax_cd;
	}


	public String getDeleted() {
		return deleted;
	}


	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}


	public BigDecimal getProd_disc() {
		return prod_disc;
	}


	public void setProd_disc(BigDecimal prod_disc) {
		this.prod_disc = prod_disc;
	}


	public Date getTax_date() {
		return tax_date;
	}


	public void setTax_date(Date tax_date) {
		this.tax_date = tax_date;
	}


	public Integer getTax_class_id() {
		return tax_class_id;
	}


	public void setTax_class_id(Integer tax_class_id) {
		this.tax_class_id = tax_class_id;
	}


	public String getLast_mod_by() {
		return last_mod_by;
	}


	public void setLast_mod_by(String last_mod_by) {
		this.last_mod_by = last_mod_by;
	}


	public Date getLast_mod_date() {
		return last_mod_date;
	}


	public void setLast_mod_date(Date last_mod_date) {
		this.last_mod_date = last_mod_date;
	}


	public String getCompany_cd() {
		return company_cd;
	}


	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}

	
	
}
