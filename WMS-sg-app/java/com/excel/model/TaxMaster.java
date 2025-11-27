package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "TAX_MASTER")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class TaxMaster  implements Serializable{

	private static final long serialVersionUID = 5920031955433614711L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TAX_ID")
	private Long tax_id;
	
	@Column(name="TAX_CD")
	private String tax_cd;
	
	@Column(name="STATE_ID")
	private Long state_id;
	
	@Column(name="IC_CHGS")
	private BigDecimal ic_chgs;
	
	@Column(name="ST_VAT")
	private BigDecimal st_vat;
	
	@Column(name="CST_RT")
	private BigDecimal cst_rt;
	
	@Column(name="ADD_TAX")
	private BigDecimal add_tax;
	
	@Column(name="SURCH")
	private BigDecimal surch;
	
	@Column(name="ADD_SURCH")
	private BigDecimal add_surch;
	
	@Column(name="RELEASE_TAX")
	private BigDecimal release_tax;
	
	@Column(name="CESS")
	private BigDecimal cess;
	
	@Column(name="TO_TAX")
	private BigDecimal to_tax;
	
	@Column(name="PURCH_IC_CHG")
	private BigDecimal purch_ic_chg;
	
	@Column(name="PURCH_ST_VAT")
	private BigDecimal purch_st_vat;
	
	@Column(name="PURCH_CST_RT")
	private BigDecimal purch_cst_rt;
	
	@Column(name="PURCH_ADDTAX")
	private BigDecimal purch_addtax;
	
	@Column(name="PURCH_SURCH")
	private BigDecimal purch_surch;
	
	@Column(name="PURCH_ADDSURCH")
	private BigDecimal purch_addsurch;
	
	@Column(name="PURCH_RESALETAX")
	private BigDecimal purch_resaletax;
	
	@Column(name="PURCH_CESS")
	private BigDecimal purch_cess;
	
	@Column(name="PURCH_TOTAX")
	private BigDecimal purch_totax;
	
	@Column(name="EFFECT_DT")
	private Date effect_dt;
	
	@Column(name="VALID_UPTO_DT")
	private Date valid_upto_dt;
	
	@Column(name="OUTPUT_TAX_PARAM")
	private String output_tax_param;
	
	@Column(name="INPUT_TAX_PARAM")
	private String input_tax_param;
	
	@Column(name="OLD_TAX_CD")
	private String old_tax_cd;

	@Column(name="DELETED")
	private String deleted;
	
	@Column(name="TAX_DESCR")
	private String tax_descr;
	
	@Column(name="LAST_MOD_BY")
	private String last_mod_by;
	
	@Column(name="LAST_MOD_DATE")
	private Date last_mod_date;
	
	@Column(name="COMPANY_CD")
	private String company_cd;
	
	@Column(name="ERP_TAX_CD")
	private String erp_tax_cd;
	
	@Column(name="ERP_VAT_CD")
	private String erp_vat_cd;
	
	@Column(name="ERP_CST_CD")
	private String erp_cst_cd;
	
	@Column(name="SGST")
	private BigDecimal sgst;
	
	@Column(name="CGST")
	private BigDecimal cgst;
	
	@Column(name="IGST")
	private BigDecimal igst;
	
	@Column(name="HSN_CODE")
	private String hsn_code;

	public Long getTax_id() {
		return tax_id;
	}

	public void setTax_id(Long tax_id) {
		this.tax_id = tax_id;
	}

	public String getTax_cd() {
		return tax_cd;
	}

	public void setTax_cd(String tax_cd) {
		this.tax_cd = tax_cd;
	}

	public Long getState_id() {
		return state_id;
	}

	public void setState_id(Long state_id) {
		this.state_id = state_id;
	}

	public BigDecimal getIc_chgs() {
		return ic_chgs;
	}

	public void setIc_chgs(BigDecimal ic_chgs) {
		this.ic_chgs = ic_chgs;
	}

	public BigDecimal getSt_vat() {
		return st_vat;
	}

	public void setSt_vat(BigDecimal st_vat) {
		this.st_vat = st_vat;
	}

	public BigDecimal getCst_rt() {
		return cst_rt;
	}

	public void setCst_rt(BigDecimal cst_rt) {
		this.cst_rt = cst_rt;
	}

	public BigDecimal getAdd_tax() {
		return add_tax;
	}

	public void setAdd_tax(BigDecimal add_tax) {
		this.add_tax = add_tax;
	}

	public BigDecimal getSurch() {
		return surch;
	}

	public void setSurch(BigDecimal surch) {
		this.surch = surch;
	}

	public BigDecimal getAdd_surch() {
		return add_surch;
	}

	public void setAdd_surch(BigDecimal add_surch) {
		this.add_surch = add_surch;
	}

	public BigDecimal getRelease_tax() {
		return release_tax;
	}

	public void setRelease_tax(BigDecimal release_tax) {
		this.release_tax = release_tax;
	}

	public BigDecimal getCess() {
		return cess;
	}

	public void setCess(BigDecimal cess) {
		this.cess = cess;
	}

	public BigDecimal getTo_tax() {
		return to_tax;
	}

	public void setTo_tax(BigDecimal to_tax) {
		this.to_tax = to_tax;
	}

	public BigDecimal getPurch_ic_chg() {
		return purch_ic_chg;
	}

	public void setPurch_ic_chg(BigDecimal purch_ic_chg) {
		this.purch_ic_chg = purch_ic_chg;
	}

	public BigDecimal getPurch_st_vat() {
		return purch_st_vat;
	}

	public void setPurch_st_vat(BigDecimal purch_st_vat) {
		this.purch_st_vat = purch_st_vat;
	}

	public BigDecimal getPurch_cst_rt() {
		return purch_cst_rt;
	}

	public void setPurch_cst_rt(BigDecimal purch_cst_rt) {
		this.purch_cst_rt = purch_cst_rt;
	}

	public BigDecimal getPurch_addtax() {
		return purch_addtax;
	}

	public void setPurch_addtax(BigDecimal purch_addtax) {
		this.purch_addtax = purch_addtax;
	}

	public BigDecimal getPurch_surch() {
		return purch_surch;
	}

	public void setPurch_surch(BigDecimal purch_surch) {
		this.purch_surch = purch_surch;
	}

	public BigDecimal getPurch_addsurch() {
		return purch_addsurch;
	}

	public void setPurch_addsurch(BigDecimal purch_addsurch) {
		this.purch_addsurch = purch_addsurch;
	}

	public BigDecimal getPurch_resaletax() {
		return purch_resaletax;
	}

	public void setPurch_resaletax(BigDecimal purch_resaletax) {
		this.purch_resaletax = purch_resaletax;
	}

	public BigDecimal getPurch_cess() {
		return purch_cess;
	}

	public void setPurch_cess(BigDecimal purch_cess) {
		this.purch_cess = purch_cess;
	}

	public BigDecimal getPurch_totax() {
		return purch_totax;
	}

	public void setPurch_totax(BigDecimal purch_totax) {
		this.purch_totax = purch_totax;
	}

	public Date getEffect_dt() {
		return effect_dt;
	}

	public void setEffect_dt(Date effect_dt) {
		this.effect_dt = effect_dt;
	}

	public Date getValid_upto_dt() {
		return valid_upto_dt;
	}

	public void setValid_upto_dt(Date valid_upto_dt) {
		this.valid_upto_dt = valid_upto_dt;
	}

	public String getOutput_tax_param() {
		return output_tax_param;
	}

	public void setOutput_tax_param(String output_tax_param) {
		this.output_tax_param = output_tax_param;
	}

	public String getInput_tax_param() {
		return input_tax_param;
	}

	public void setInput_tax_param(String input_tax_param) {
		this.input_tax_param = input_tax_param;
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

	public String getTax_descr() {
		return tax_descr;
	}

	public void setTax_descr(String tax_descr) {
		this.tax_descr = tax_descr;
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

	public String getErp_tax_cd() {
		return erp_tax_cd;
	}

	public void setErp_tax_cd(String erp_tax_cd) {
		this.erp_tax_cd = erp_tax_cd;
	}

	public String getErp_vat_cd() {
		return erp_vat_cd;
	}

	public void setErp_vat_cd(String erp_vat_cd) {
		this.erp_vat_cd = erp_vat_cd;
	}

	public String getErp_cst_cd() {
		return erp_cst_cd;
	}

	public void setErp_cst_cd(String erp_cst_cd) {
		this.erp_cst_cd = erp_cst_cd;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getIgst() {
		return igst;
	}

	public void setIgst(BigDecimal igst) {
		this.igst = igst;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}
	

}
