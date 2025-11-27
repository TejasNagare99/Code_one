package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "StockTransferNotePrint")

	@NamedStoredProcedureQuery(
			name="callSTOCK_TRANSFER_PRINT_WITHGST",
			procedureName = "STOCK_TRANSFER_PRINT_WITHGST",
			resultClasses = StockTransferNotePrint.class,
			parameters = {
					@StoredProcedureParameter(name="loc_id", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name="cfa", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name="frm_challan_no", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name="to_challan_no", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name="rep_type", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name="frm_challan_dt", mode = ParameterMode.IN, type = String.class),
					@StoredProcedureParameter(name="to_challan_dt", mode = ParameterMode.IN, type = String.class)
			}
	)
public class StockTransferNotePrint {
    
    @Id
    @Column(name="Row")
    private Long row;

    @Column(name="LOC_ID")
    public Long loc_id;

    @Column(name="LOC_NM")
    public String loc_nm;

    @Column(name="LOC_ADR1")
    public String loc_adr1;

    @Column(name="LOC_ADR2")
    public String loc_adr2;

    @Column(name="LOC_ADR3")
    public String loc_adr3;

    @Column(name="LOC_ADR4")
    public String loc_adr4;

    @Column(name="LOC_GST_REG_NO")
    public String loc_gst_reg_no;

    @Column(name="LOC_STATE_ID")
    public Long loc_state_id;

    @Column(name="LOC_STATE_NAME")
    public String loc_state_name;

    @Column(name="TRF_NO")
    public String trf_no;

    @Column(name="TRF_DATE")
    public String trf_date;

    @Column(name="DPTLOC_ID")
    public Long dptloc_id;

    @Column(name="DPTLOC_NAME")
    public String dptloc_name;

    @Column(name="DPT_ADR1")
    public String dpt_adr1;

    @Column(name="DPT_ADR2")
    public String dpt_adr2;

    @Column(name="DPT_ADR3")
    public String dpt_adr3;

    @Column(name="DPT_ADR4")
    public String dpt_adr4;

    @Column(name="DPTDESTINATION")
    public String dptdestination;

    @Column(name="DPT_GST_REG_NO")
    public String dpt_gst_reg_no;

    @Column(name="DPTSTATE_ID")
    public Long dptstate_id;

    @Column(name="DPT_STATE_NAME")
    public String dpt_state_name;

    @Column(name="SMP_PROD_CD")
    public String smp_prod_cd;

    @Column(name="SMP_ERP_PROD_CD")
    public String smp_erp_prod_cd;

    @Column(name="prod_name_pack")
    public String prod_name_pack;

    @Column(name="HSN_CODE")
    public String hsn_code;

    @Column(name="BATCH_NO")
    public String batch_no;

    @Column(name="BATCH_MFG_DT")
    public String batch_mfg_dt;

    @Column(name="BATCH_EXPIRY_DT")
    public String batch_expiry_dt;

    @Column(name="SOLD_QTY")
    public Long sold_qty;

    @Column(name="FREE_QTY")
    public Long free_qty;

    @Column(name="RATE")
    public BigDecimal rate;

    @Column(name="GOODS_VALUE")
    public BigDecimal goods_value;

    @Column(name="TAXABLE_AMT")
    public BigDecimal taxable_amt;

    @Column(name="GST_DOC_TYPE")
    public String gst_doc_type;

    @Column(name="GST_DESCRIPTION")
    public String gst_description;

    @Column(name="SGST_RATE")
    public Long sgst_rate;

    @Column(name="SGST_BILL_AMT")
    public BigDecimal sgst_bill_amt;

    @Column(name="CGST_RATE")
    public Long cgst_rate;

    @Column(name="CGST_BILL_AMT")
    public BigDecimal cgst_bill_amt;

    @Column(name="IGST_RATE")
    public Long igst_rate;

    @Column(name="IGST_BILL_AMT")
    public BigDecimal igst_bill_amt;

    @Column(name="TOT_AMT")
    public BigDecimal tot_amt;

    @Column(name="LR_NO")
    public String lr_no;

    @Column(name="LR_DATE")
    public String lr_date;

    @Column(name="LORRY_NO")
    public String lorry_no;

    @Column(name="TRANSPORTER_NAME")
    public String transporter_name;

    @Column(name="WEIGHT")
    public Long weight;

    @Column(name="FULL_SHIPPERS")
    public Long full_shippers;

    @Column(name="LOOSE_SHIPPERS")
    public Long loose_shippers;
    
    @Column(name="LOC_LICENSE1")
    public String loc_license1;
    
    @Column(name="LOC_LICENSE2")
    public String loc_license2;
    
    @Column(name="LOC_CINNO")
    public String loc_cinno;
    
    @Column(name="LOC_WEB_SITE")
    public String loc_web_site;
    
    @Column(name="LOC_TELEPHONE_NO")
    public String loc_telephone_no;
    
    @Column(name="LOC_EMAILID")
    public String loc_emailid;
    
    @Column(name="DPT_EMAILID")
    public String dpt_emailid;
    
    @Column(name="DPT_MOBILE_NO")
    public String dpt_mobile_no;
    
    @Column(name="HSGST_BILL_AMT")
    public BigDecimal hsgst_bill_amt;
    
    @Column(name="HCGST_BILL_AMT")
    public BigDecimal hcgst_bill_amt;
    
    
    @Column(name="HIGST_BILL_AMT")
    public BigDecimal higst_bill_amt;
    
    
    @Column(name="ROUNDOFF")
    public BigDecimal roundoff;
    
    
    @Column(name="LOC_STATECODE")
    public String loc_statecode;
    
    
    @Column(name="DPT_STATECODE")
    public String dpt_statecode;
    
    
    @Column(name="DPT_CINNO")
    public String dpt_cinno;
    
    
    @Column(name="DPT_WEB_SITE")
    public String dpt_web_site;
    
    
    
    
    

    public BigDecimal getHsgst_bill_amt() {
        return hsgst_bill_amt;
    }

    public void setHsgst_bill_amt(BigDecimal hsgst_bill_amt) {
        this.hsgst_bill_amt = hsgst_bill_amt;
    }

    public BigDecimal getHcgst_bill_amt() {
        return hcgst_bill_amt;
    }

    public void setHcgst_bill_amt(BigDecimal hcgst_bill_amt) {
        this.hcgst_bill_amt = hcgst_bill_amt;
    }

    public BigDecimal getHigst_bill_amt() {
        return higst_bill_amt;
    }

    public void setHigst_bill_amt(BigDecimal higst_bill_amt) {
        this.higst_bill_amt = higst_bill_amt;
    }

    public BigDecimal getRoundoff() {
        return roundoff;
    }

    public void setRoundoff(BigDecimal roundoff) {
        this.roundoff = roundoff;
    }

    public String getLoc_statecode() {
        return loc_statecode;
    }

    public void setLoc_statecode(String loc_statecode) {
        this.loc_statecode = loc_statecode;
    }

    public String getDpt_statecode() {
        return dpt_statecode;
    }

    public void setDpt_statecode(String dpt_statecode) {
        this.dpt_statecode = dpt_statecode;
    }

    public String getDpt_cinno() {
        return dpt_cinno;
    }

    public void setDpt_cinno(String dpt_cinno) {
        this.dpt_cinno = dpt_cinno;
    }

    public String getDpt_web_site() {
        return dpt_web_site;
    }

    public void setDpt_web_site(String dpt_web_site) {
        this.dpt_web_site = dpt_web_site;
    }

    public Long getRow() {
        return row;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Long getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(Long loc_id) {
        this.loc_id = loc_id;
    }

    public String getLoc_nm() {
        return loc_nm;
    }

    public void setLoc_nm(String loc_nm) {
        this.loc_nm = loc_nm;
    }

    public String getLoc_adr1() {
        return loc_adr1;
    }

    public void setLoc_adr1(String loc_adr1) {
        this.loc_adr1 = loc_adr1;
    }

    public String getLoc_adr2() {
        return loc_adr2;
    }

    public void setLoc_adr2(String loc_adr2) {
        this.loc_adr2 = loc_adr2;
    }

    public String getLoc_adr3() {
        return loc_adr3;
    }

    public void setLoc_adr3(String loc_adr3) {
        this.loc_adr3 = loc_adr3;
    }

    public String getLoc_adr4() {
        return loc_adr4;
    }

    public void setLoc_adr4(String loc_adr4) {
        this.loc_adr4 = loc_adr4;
    }


    public Long getLoc_state_id() {
        return loc_state_id;
    }

    public void setLoc_state_id(Long loc_state_id) {
        this.loc_state_id = loc_state_id;
    }

    public String getLoc_state_name() {
        return loc_state_name;
    }

    public void setLoc_state_name(String loc_state_name) {
        this.loc_state_name = loc_state_name;
    }

    public String getTrf_no() {
        return trf_no;
    }

    public void setTrf_no(String trf_no) {
        this.trf_no = trf_no;
    }


    public Long getDptloc_id() {
        return dptloc_id;
    }

    public void setDptloc_id(Long dptloc_id) {
        this.dptloc_id = dptloc_id;
    }

    public String getDptloc_name() {
        return dptloc_name;
    }

    public void setDptloc_name(String dptloc_name) {
        this.dptloc_name = dptloc_name;
    }

    public String getDpt_adr1() {
        return dpt_adr1;
    }

    public void setDpt_adr1(String dpt_adr1) {
        this.dpt_adr1 = dpt_adr1;
    }

    public String getDpt_adr2() {
        return dpt_adr2;
    }

    public void setDpt_adr2(String dpt_adr2) {
        this.dpt_adr2 = dpt_adr2;
    }

    public String getDpt_adr3() {
        return dpt_adr3;
    }

    public void setDpt_adr3(String dpt_adr3) {
        this.dpt_adr3 = dpt_adr3;
    }

    public String getDpt_adr4() {
        return dpt_adr4;
    }

    public void setDpt_adr4(String dpt_adr4) {
        this.dpt_adr4 = dpt_adr4;
    }

    public String getDptdestination() {
        return dptdestination;
    }

    public void setDptdestination(String dptdestination) {
        this.dptdestination = dptdestination;
    }


    public Long getDptstate_id() {
        return dptstate_id;
    }

    public void setDptstate_id(Long dptstate_id) {
        this.dptstate_id = dptstate_id;
    }

    public String getDpt_state_name() {
        return dpt_state_name;
    }

    public void setDpt_state_name(String dpt_state_name) {
        this.dpt_state_name = dpt_state_name;
    }

    public String getSmp_prod_cd() {
        return smp_prod_cd;
    }

    public void setSmp_prod_cd(String smp_prod_cd) {
        this.smp_prod_cd = smp_prod_cd;
    }

    public String getSmp_erp_prod_cd() {
        return smp_erp_prod_cd;
    }

    public void setSmp_erp_prod_cd(String smp_erp_prod_cd) {
        this.smp_erp_prod_cd = smp_erp_prod_cd;
    }

    public String getProd_name_pack() {
        return prod_name_pack;
    }

    public void setProd_name_pack(String prod_name_pack) {
        this.prod_name_pack = prod_name_pack;
    }

    public String getHsn_code() {
        return hsn_code;
    }

    public void setHsn_code(String hsn_code) {
        this.hsn_code = hsn_code;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }


    public Long getSold_qty() {
        return sold_qty;
    }

    public void setSold_qty(Long sold_qty) {
        this.sold_qty = sold_qty;
    }

    public Long getFree_qty() {
        return free_qty;
    }

    public void setFree_qty(Long free_qty) {
        this.free_qty = free_qty;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getGoods_value() {
        return goods_value;
    }

    public void setGoods_value(BigDecimal goods_value) {
        this.goods_value = goods_value;
    }

    public BigDecimal getTaxable_amt() {
        return taxable_amt;
    }

    public void setTaxable_amt(BigDecimal taxable_amt) {
        this.taxable_amt = taxable_amt;
    }

    public String getGst_doc_type() {
        return gst_doc_type;
    }

    public void setGst_doc_type(String gst_doc_type) {
        this.gst_doc_type = gst_doc_type;
    }

    public String getGst_description() {
        return gst_description;
    }

    public void setGst_description(String gst_description) {
        this.gst_description = gst_description;
    }

    public Long getSgst_rate() {
        return sgst_rate;
    }

    public void setSgst_rate(Long sgst_rate) {
        this.sgst_rate = sgst_rate;
    }

    public BigDecimal getSgst_bill_amt() {
        return sgst_bill_amt;
    }

    public void setSgst_bill_amt(BigDecimal sgst_bill_amt) {
        this.sgst_bill_amt = sgst_bill_amt;
    }

    public Long getCgst_rate() {
        return cgst_rate;
    }

    public void setCgst_rate(Long cgst_rate) {
        this.cgst_rate = cgst_rate;
    }

    public BigDecimal getCgst_bill_amt() {
        return cgst_bill_amt;
    }

    public void setCgst_bill_amt(BigDecimal cgst_bill_amt) {
        this.cgst_bill_amt = cgst_bill_amt;
    }

    public Long getIgst_rate() {
        return igst_rate;
    }

    public void setIgst_rate(Long igst_rate) {
        this.igst_rate = igst_rate;
    }

    public BigDecimal getIgst_bill_amt() {
        return igst_bill_amt;
    }

    public void setIgst_bill_amt(BigDecimal igst_bill_amt) {
        this.igst_bill_amt = igst_bill_amt;
    }

    public BigDecimal getTot_amt() {
        return tot_amt;
    }

    public void setTot_amt(BigDecimal tot_amt) {
        this.tot_amt = tot_amt;
    }

    public String getLr_no() {
        return lr_no;
    }

    public void setLr_no(String lr_no) {
        this.lr_no = lr_no;
    }


    public String getLorry_no() {
        return lorry_no;
    }

    public void setLorry_no(String lorry_no) {
        this.lorry_no = lorry_no;
    }

    public String getTransporter_name() {
        return transporter_name;
    }

    public void setTransporter_name(String transporter_name) {
        this.transporter_name = transporter_name;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getFull_shippers() {
        return full_shippers;
    }

    public void setFull_shippers(Long full_shippers) {
        this.full_shippers = full_shippers;
    }

    public Long getLoose_shippers() {
        return loose_shippers;
    }

    public void setLoose_shippers(Long loose_shippers) {
        this.loose_shippers = loose_shippers;
    }

    public String getLoc_gst_reg_no() {
        return loc_gst_reg_no;
    }

    public void setLoc_gst_reg_no(String loc_gst_reg_no) {
        this.loc_gst_reg_no = loc_gst_reg_no;
    }

    public String getDpt_gst_reg_no() {
        return dpt_gst_reg_no;
    }

    public void setDpt_gst_reg_no(String dpt_gst_reg_no) {
        this.dpt_gst_reg_no = dpt_gst_reg_no;
    }

    public String getLoc_license1() {
        return loc_license1;
    }

    public void setLoc_license1(String loc_license1) {
        this.loc_license1 = loc_license1;
    }

    public String getLoc_license2() {
        return loc_license2;
    }

    public void setLoc_license2(String loc_license2) {
        this.loc_license2 = loc_license2;
    }

    public String getLoc_cinno() {
        return loc_cinno;
    }

    public void setLoc_cinno(String loc_cinno) {
        this.loc_cinno = loc_cinno;
    }

    public String getLoc_web_site() {
        return loc_web_site;
    }

    public void setLoc_web_site(String loc_web_site) {
        this.loc_web_site = loc_web_site;
    }

    public String getLoc_telephone_no() {
        return loc_telephone_no;
    }

    public void setLoc_telephone_no(String loc_telephone_no) {
        this.loc_telephone_no = loc_telephone_no;
    }

    public String getLoc_emailid() {
        return loc_emailid;
    }

    public void setLoc_emailid(String loc_emailid) {
        this.loc_emailid = loc_emailid;
    }

    public String getDpt_emailid() {
        return dpt_emailid;
    }

    public void setDpt_emailid(String dpt_emailid) {
        this.dpt_emailid = dpt_emailid;
    }

    public String getDpt_mobile_no() {
        return dpt_mobile_no;
    }

    public void setDpt_mobile_no(String dpt_mobile_no) {
        this.dpt_mobile_no = dpt_mobile_no;
    }

    public String getTrf_date() {
        return trf_date;
    }

    public void setTrf_date(String trf_date) {
        this.trf_date = trf_date;
    }

    public String getBatch_mfg_dt() {
        return batch_mfg_dt;
    }

    public void setBatch_mfg_dt(String batch_mfg_dt) {
        this.batch_mfg_dt = batch_mfg_dt;
    }

    public String getBatch_expiry_dt() {
        return batch_expiry_dt;
    }

    public void setBatch_expiry_dt(String batch_expiry_dt) {
        this.batch_expiry_dt = batch_expiry_dt;
    }

    public String getLr_date() {
        return lr_date;
    }

    public void setLr_date(String lr_date) {
        this.lr_date = lr_date;
    }
    
    
    
    
}