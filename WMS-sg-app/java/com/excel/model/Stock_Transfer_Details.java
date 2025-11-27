package com.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "STOCK_TRANSFER_DETAILS")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Stock_Transfer_Details implements Serializable{

	private static final long serialVersionUID = -6285137380139127163L;



	//added for GST to be set default zero values
	public Stock_Transfer_Details() {
		this.goods_value = BigDecimal.ZERO;
		this.trd_disc_amt = BigDecimal.ZERO;
		this.schm_disc_amt = BigDecimal.ZERO;
		this.pre_tax_amt = BigDecimal.ZERO;
		this.tax_amt_billed = BigDecimal.ZERO;
		this.tax_amt_free = BigDecimal.ZERO;
		this.add_tax_rate = BigDecimal.ZERO;
		this.add_tax_amt = BigDecimal.ZERO;
		this.cess_rate = BigDecimal.ZERO;
		this.cess_bill_amt = BigDecimal.ZERO;
		this.tot_rate = BigDecimal.ZERO;
		this.tot_amt = BigDecimal.ZERO;
		this.surchg_rate = BigDecimal.ZERO;
		this.surchg_bill_amt = BigDecimal.ZERO;
		this.party_disc_rate = BigDecimal.ZERO;
		this.party_disc_amt = BigDecimal.ZERO;
		this.prod_disc_rate = BigDecimal.ZERO;
		this.prod_disc_amt = BigDecimal.ZERO;
		this.sgst_rate = BigDecimal.ZERO;
		this.sgst_bill_amt = BigDecimal.ZERO;
		this.cgst_rate = BigDecimal.ZERO;
		this.cgst_bill_amt = BigDecimal.ZERO;
		this.igst_rate = BigDecimal.ZERO;
		this.igst_bill_amt = BigDecimal.ZERO;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trf_sl_no;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRF_ID")
	private Stock_Transfer_Header stockHeader;
	
	@Column(name = "DIV_ID")
	private Long div_id;
	
	@Column(name = "BATCH_ID")
	private Long  batch_id;
	
	@Column(name = "PROD_ID")
	private Long prod_id;
	
	@Column(name = "SOLD_QTY")
	private BigDecimal sold_qty;
	
	@Column(name = "FREE_QTY")
	private BigDecimal free_qty;
	
	@Column(name = "REPL_QTY")
	private BigDecimal repl_qty;
	
	@Column(name = "RATE")
	private BigDecimal rate;
	
	@Column(name = "CASES")
	private BigDecimal cases;
	
	@Column(name= "RATE_ID")
	private Long rate_id;
	
	@Column(name= "RATE_DT_ID")
	private Long rate_dt_id;
	
	@Column(name= "TAX_ID")
	private Long tax_id;
	
	@Column(name = "FULL_SHIPPERS")
	private BigDecimal full_shippers;
	
	@Column(name = "LOOSE_SHIPPERS")
	private BigDecimal loose_shippers;
	
	@Column(name = "WEIGHT")
	private BigDecimal weight;
	
	@Column(name = "VOLUME")
	private BigDecimal volume;
	
	@Column(name = "FIN_YEAR_ID")
	private Long fin_year_id;
	
	@Column(name = "COMPANY_CD")
	private String comp_cd;
	
	@Column(name = "PROD_DISC")
	private Integer prod_disc;
	
	@Column(name = "RAT_AUTH_YN")
	private String rat_auth_yn;
	
	@Column(name = "OCTROI")
	private BigDecimal octroi;
	
	@Column(name = "TAXABLE_AMT")
	private BigDecimal taxable_amt;
	
	@Column(name = "SCHM_DTL_ID")
	private Long schm_dtl_id;

	@Column(name = "STOCK_TRF_ORDDTLID")
	private Long stock_trf_orddtlid;
	
	@Column(name = "GOODS_VALUE")
	private BigDecimal goods_value;
	
	@Column(name = "TRD_DISC_AMT")
	private BigDecimal trd_disc_amt;
	
	@Column(name = "SCHM_DISC_AMT")
	private BigDecimal schm_disc_amt;
	
	@Column(name = "PRE_TAX_AMT")
	private BigDecimal pre_tax_amt;
	
	@Column(name = "VAT_CST_IND")
	private String vat_cst_ind;
	
	@Column(name = "TAX_AMT_BILLED")
	private BigDecimal tax_amt_billed;
	
	@Column(name = "TAX_AMT_FREE")
	private BigDecimal tax_amt_free;
	
	@Column(name = "TAX_PARAM")
	private String tax_param;
	
	@Column(name = "ADD_TAX_RATE")
	private BigDecimal add_tax_rate;
	
	@Column(name = "ADD_TAX_AMT")
	private BigDecimal add_tax_amt;
	
	@Column(name = "CESS_RATE")
	private BigDecimal cess_rate;
	
	@Column(name = "CESS_BILL_AMT")
	private BigDecimal cess_bill_amt;
	
	@Column(name = "TOT_RATE")
	private BigDecimal tot_rate;
	
	@Column(name = "TOT_AMT")
	private BigDecimal tot_amt;
	
	@Column(name = "SURCHG_RATE")
	private BigDecimal surchg_rate;
	
	@Column(name = "SURCHG_BILL_AMT")
	private BigDecimal surchg_bill_amt;
	
	@Column(name = "PARTY_DISC_RATE")
	private BigDecimal party_disc_rate;
	
	@Column(name = "PARTY_DISC_AMT")
	private BigDecimal party_disc_amt;
	
	@Column(name = "PROD_DISC_RATE")
	private BigDecimal prod_disc_rate;
	
	@Column(name = "PROD_DISC_AMT")
	private BigDecimal prod_disc_amt;
	
	@Column(name = "SGST_RATE")
	private BigDecimal sgst_rate;
	
	@Column(name = "SGST_BILL_AMT")
	private BigDecimal sgst_bill_amt;
	
	@Column(name = "CGST_RATE")
	private BigDecimal cgst_rate;
	
	@Column(name = "CGST_BILL_AMT")
	private BigDecimal cgst_bill_amt;
	
	@Column(name = "IGST_RATE")
	private BigDecimal igst_rate;
	
	@Column(name = "IGST_BILL_AMT")
	private BigDecimal igst_bill_amt;
	
	@Column(name = "GST_REVERSE_CHG")
	private String gst_reverse_chg;
	
	@Column(name = "GST_DOC_TYPE")
	private String gst_doc_type;
	
	@Column(name = "TRFDTL_ins_usr_id")
	private String TRFDTL_ins_usr_id;
	
	@Column(name = "TRFDTL_mod_usr_id")
	private String TRFDTL_mod_usr_id;
	
	@Column(name = "TRFDTL_ins_dt")
	private Date TRFDTL_ins_dt;
	
	@Column(name = "TRFDTL_mod_dt")
	private Date TRFDTL_mod_dt;
	
	@Column(name = "TRFDTL_ins_ip_add")
	private String TRFDTL_ins_ip_add;
	
	@Column(name = "TRFDTL_mod_ip_add")
	private String TRFDTL_mod_ip_add;
	
	@Column(name = "TRFDTL_status")
	private String TRFDTL_status;
	
	@Column(name = "HSN_CODE")
	private String hsn_code;
	
	@Column(name = "STOCK_TYPE_ID")
	private Long stock_type_id;
	
	@Column(name = "STOCK_TYPE")
	private String stock_type;
	
	
	public Long getTrf_sl_no() {
		return trf_sl_no;
	}

	public void setTrf_sl_no(Long trf_sl_no) {
		this.trf_sl_no = trf_sl_no;
	}

	public Stock_Transfer_Header getStockHeader() {
		return stockHeader;
	}

	public void setStockHeader(Stock_Transfer_Header stockHeader) {
		this.stockHeader = stockHeader;
	}

	public Long getDiv_id() {
		return div_id;
	}

	public void setDiv_id(Long div_id) {
		this.div_id = div_id;
	}

	public Long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(Long batch_id) {
		this.batch_id = batch_id;
	}

	public Long getProd_id() {
		return prod_id;
	}

	public void setProd_id(Long prod_id) {
		this.prod_id = prod_id;
	}

	public BigDecimal getSold_qty() {
		return sold_qty;
	}

	public void setSold_qty(BigDecimal sold_qty) {
		this.sold_qty = sold_qty;
	}

	public BigDecimal getFree_qty() {
		return free_qty;
	}

	public void setFree_qty(BigDecimal free_qty) {
		this.free_qty = free_qty;
	}

	public BigDecimal getRepl_qty() {
		return repl_qty;
	}

	public void setRepl_qty(BigDecimal repl_qty) {
		this.repl_qty = repl_qty;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getCases() {
		return cases;
	}

	public void setCases(BigDecimal cases) {
		this.cases = cases;
	}

	public Long getRate_id() {
		return rate_id;
	}

	public void setRate_id(Long rate_id) {
		this.rate_id = rate_id;
	}

	public Long getRate_dt_id() {
		return rate_dt_id;
	}

	public void setRate_dt_id(Long rate_dt_id) {
		this.rate_dt_id = rate_dt_id;
	}

	public Long getTax_id() {
		return tax_id;
	}

	public void setTax_id(Long tax_id) {
		this.tax_id = tax_id;
	}

	public BigDecimal getFull_shippers() {
		return full_shippers;
	}

	public void setFull_shippers(BigDecimal full_shippers) {
		this.full_shippers = full_shippers;
	}

	public BigDecimal getLoose_shippers() {
		return loose_shippers;
	}

	public void setLoose_shippers(BigDecimal loose_shippers) {
		this.loose_shippers = loose_shippers;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Long getFin_year_id() {
		return fin_year_id;
	}

	public void setFin_year_id(Long fin_year_id) {
		this.fin_year_id = fin_year_id;
	}

	public String getComp_cd() {
		return comp_cd;
	}

	public void setComp_cd(String comp_cd) {
		this.comp_cd = comp_cd;
	}

	public Integer getProd_disc() {
		return prod_disc;
	}

	public void setProd_disc(Integer prod_disc) {
		this.prod_disc = prod_disc;
	}

	public String getRat_auth_yn() {
		return rat_auth_yn;
	}

	public void setRat_auth_yn(String rat_auth_yn) {
		this.rat_auth_yn = rat_auth_yn;
	}

	public BigDecimal getOctroi() {
		return octroi;
	}

	public void setOctroi(BigDecimal octroi) {
		this.octroi = octroi;
	}

	public BigDecimal getTaxable_amt() {
		return taxable_amt;
	}

	public void setTaxable_amt(BigDecimal taxable_amt) {
		this.taxable_amt = taxable_amt;
	}

	public Long getSchm_dtl_id() {
		return schm_dtl_id;
	}

	public void setSchm_dtl_id(Long schm_dtl_id) {
		this.schm_dtl_id = schm_dtl_id;
	}

	public Long getStock_trf_orddtlid() {
		return stock_trf_orddtlid;
	}

	public void setStock_trf_orddtlid(Long stock_trf_orddtlid) {
		this.stock_trf_orddtlid = stock_trf_orddtlid;
	}

	public BigDecimal getGoods_value() {
		return goods_value;
	}

	public void setGoods_value(BigDecimal goods_value) {
		this.goods_value = goods_value;
	}

	public BigDecimal getTrd_disc_amt() {
		return trd_disc_amt;
	}

	public void setTrd_disc_amt(BigDecimal trd_disc_amt) {
		this.trd_disc_amt = trd_disc_amt;
	}

	public BigDecimal getSchm_disc_amt() {
		return schm_disc_amt;
	}

	public void setSchm_disc_amt(BigDecimal schm_disc_amt) {
		this.schm_disc_amt = schm_disc_amt;
	}

	public BigDecimal getPre_tax_amt() {
		return pre_tax_amt;
	}

	public void setPre_tax_amt(BigDecimal pre_tax_amt) {
		this.pre_tax_amt = pre_tax_amt;
	}

	public String getVat_cst_ind() {
		return vat_cst_ind;
	}

	public void setVat_cst_ind(String vat_cst_ind) {
		this.vat_cst_ind = vat_cst_ind;
	}

	public BigDecimal getTax_amt_billed() {
		return tax_amt_billed;
	}

	public void setTax_amt_billed(BigDecimal tax_amt_billed) {
		this.tax_amt_billed = tax_amt_billed;
	}

	public BigDecimal getTax_amt_free() {
		return tax_amt_free;
	}

	public void setTax_amt_free(BigDecimal tax_amt_free) {
		this.tax_amt_free = tax_amt_free;
	}

	public String getTax_param() {
		return tax_param;
	}

	public void setTax_param(String tax_param) {
		this.tax_param = tax_param;
	}

	public BigDecimal getAdd_tax_rate() {
		return add_tax_rate;
	}

	public void setAdd_tax_rate(BigDecimal add_tax_rate) {
		this.add_tax_rate = add_tax_rate;
	}

	public BigDecimal getAdd_tax_amt() {
		return add_tax_amt;
	}

	public void setAdd_tax_amt(BigDecimal add_tax_amt) {
		this.add_tax_amt = add_tax_amt;
	}

	public BigDecimal getCess_rate() {
		return cess_rate;
	}

	public void setCess_rate(BigDecimal cess_rate) {
		this.cess_rate = cess_rate;
	}

	public BigDecimal getCess_bill_amt() {
		return cess_bill_amt;
	}

	public void setCess_bill_amt(BigDecimal cess_bill_amt) {
		this.cess_bill_amt = cess_bill_amt;
	}

	public BigDecimal getTot_rate() {
		return tot_rate;
	}

	public void setTot_rate(BigDecimal tot_rate) {
		this.tot_rate = tot_rate;
	}

	public BigDecimal getTot_amt() {
		return tot_amt;
	}

	public void setTot_amt(BigDecimal tot_amt) {
		this.tot_amt = tot_amt;
	}

	public BigDecimal getSurchg_rate() {
		return surchg_rate;
	}

	public void setSurchg_rate(BigDecimal surchg_rate) {
		this.surchg_rate = surchg_rate;
	}

	public BigDecimal getSurchg_bill_amt() {
		return surchg_bill_amt;
	}

	public void setSurchg_bill_amt(BigDecimal surchg_bill_amt) {
		this.surchg_bill_amt = surchg_bill_amt;
	}

	public BigDecimal getParty_disc_rate() {
		return party_disc_rate;
	}

	public void setParty_disc_rate(BigDecimal party_disc_rate) {
		this.party_disc_rate = party_disc_rate;
	}

	public BigDecimal getParty_disc_amt() {
		return party_disc_amt;
	}

	public void setParty_disc_amt(BigDecimal party_disc_amt) {
		this.party_disc_amt = party_disc_amt;
	}

	public BigDecimal getProd_disc_rate() {
		return prod_disc_rate;
	}

	public void setProd_disc_rate(BigDecimal prod_disc_rate) {
		this.prod_disc_rate = prod_disc_rate;
	}

	public BigDecimal getProd_disc_amt() {
		return prod_disc_amt;
	}

	public void setProd_disc_amt(BigDecimal prod_disc_amt) {
		this.prod_disc_amt = prod_disc_amt;
	}

	public BigDecimal getSgst_rate() {
		return sgst_rate;
	}

	public void setSgst_rate(BigDecimal sgst_rate) {
		this.sgst_rate = sgst_rate;
	}

	public BigDecimal getSgst_bill_amt() {
		return sgst_bill_amt;
	}

	public void setSgst_bill_amt(BigDecimal sgst_bill_amt) {
		this.sgst_bill_amt = sgst_bill_amt;
	}

	public BigDecimal getCgst_rate() {
		return cgst_rate;
	}

	public void setCgst_rate(BigDecimal cgst_rate) {
		this.cgst_rate = cgst_rate;
	}

	public BigDecimal getCgst_bill_amt() {
		return cgst_bill_amt;
	}

	public void setCgst_bill_amt(BigDecimal cgst_bill_amt) {
		this.cgst_bill_amt = cgst_bill_amt;
	}

	public BigDecimal getIgst_rate() {
		return igst_rate;
	}

	public void setIgst_rate(BigDecimal igst_rate) {
		this.igst_rate = igst_rate;
	}

	public BigDecimal getIgst_bill_amt() {
		return igst_bill_amt;
	}

	public void setIgst_bill_amt(BigDecimal igst_bill_amt) {
		this.igst_bill_amt = igst_bill_amt;
	}

	public String getGst_reverse_chg() {
		return gst_reverse_chg;
	}

	public void setGst_reverse_chg(String gst_reverse_chg) {
		this.gst_reverse_chg = gst_reverse_chg;
	}

	public String getGst_doc_type() {
		return gst_doc_type;
	}

	public void setGst_doc_type(String gst_doc_type) {
		this.gst_doc_type = gst_doc_type;
	}

	public String getTRFDTL_ins_usr_id() {
		return TRFDTL_ins_usr_id;
	}

	public void setTRFDTL_ins_usr_id(String tRFDTL_ins_usr_id) {
		TRFDTL_ins_usr_id = tRFDTL_ins_usr_id;
	}

	public String getTRFDTL_mod_usr_id() {
		return TRFDTL_mod_usr_id;
	}

	public void setTRFDTL_mod_usr_id(String tRFDTL_mod_usr_id) {
		TRFDTL_mod_usr_id = tRFDTL_mod_usr_id;
	}

	public Date getTRFDTL_ins_dt() {
		return TRFDTL_ins_dt;
	}

	public void setTRFDTL_ins_dt(Date tRFDTL_ins_dt) {
		TRFDTL_ins_dt = tRFDTL_ins_dt;
	}

	public Date getTRFDTL_mod_dt() {
		return TRFDTL_mod_dt;
	}

	public void setTRFDTL_mod_dt(Date tRFDTL_mod_dt) {
		TRFDTL_mod_dt = tRFDTL_mod_dt;
	}

	public String getTRFDTL_ins_ip_add() {
		return TRFDTL_ins_ip_add;
	}

	public void setTRFDTL_ins_ip_add(String tRFDTL_ins_ip_add) {
		TRFDTL_ins_ip_add = tRFDTL_ins_ip_add;
	}

	public String getTRFDTL_mod_ip_add() {
		return TRFDTL_mod_ip_add;
	}

	public void setTRFDTL_mod_ip_add(String tRFDTL_mod_ip_add) {
		TRFDTL_mod_ip_add = tRFDTL_mod_ip_add;
	}

	public String getTRFDTL_status() {
		return TRFDTL_status;
	}

	public void setTRFDTL_status(String tRFDTL_status) {
		TRFDTL_status = tRFDTL_status;
	}

	public String getHsn_code() {
		return hsn_code;
	}

	public void setHsn_code(String hsn_code) {
		this.hsn_code = hsn_code;
	}

	public Long getStock_type_id() {
		return stock_type_id;
	}

	public void setStock_type_id(Long stock_type_id) {
		this.stock_type_id = stock_type_id;
	}

	public String getStock_type() {
		return stock_type;
	}

	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}
	
	public Stock_Transfer_Details(Long prod_id) {
		this.prod_id=prod_id;
	}
}

