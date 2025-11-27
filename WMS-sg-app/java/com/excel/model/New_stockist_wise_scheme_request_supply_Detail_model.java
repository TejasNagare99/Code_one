
	
	package com.excel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;


@Entity
@Table(name="ARTICLE_SCHEME_ALLOCID_DSP")

@NamedStoredProcedureQuery(name = "callartereq_to_dispatch_direct_to_stockist_report_with_paramNewDetail",
procedureName = "ARTICLE_SCHEME_ALLOCID_DSP",
	parameters= {
			@StoredProcedureParameter(name = "ALLOCID" ,mode = ParameterMode.IN,type=String.class),
			@StoredProcedureParameter(name = "ART_PRDID" ,mode = ParameterMode.IN,type=String.class),
		
	}, resultClasses = New_stockist_wise_scheme_request_supply_Detail_model.class)
public class New_stockist_wise_scheme_request_supply_Detail_model {

	
	
	@Id
	@Column(name="ROW")
	private Long row;
	
	@Column(name="ALLOCDTL_ALLOC_ID")
	private String allocdtl_alloc_id;
	
	@Column(name="ALLOCDTL_ID")
	private String allocdtl_id;
	
	@Column(name="DSP_ID")
	private String dsp_id;
	
	@Column(name="DSP_CHALLAN_NO")
	private String dsp_challan_no;
	
	@Column(name="DSP_DT")
	private String dsp_dt;
	
	@Column(name="DSP_LR_NO")
	private String dsp_lr_no;
	
	@Column(name="DSP_LR_DT")
	private String dsp_lr_dt;
	
	@Column(name="PROD_ID")
	private String prod_id;
	
	@Column(name="PROD_NAME")
	private String prod_name;
	
	@Column(name="QTY_SUPPLIED")
	private Long qty_supplied;
	
	@Column(name="PENDING_QTY")
	private Long pending_qty;
	
	@Column(name="E_INV_NO")
	private String e_inv_no;
	
	@Column(name="E_INV_DATE")
	private String e_inv_date;
	
	@Column(name="TRN_EWAYBILLNO")
	private String trn_ewaybillno;
	
	@Column(name="TRN_EWAYBILLDT")
	private String trn_ewaybilldt;

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	

	public String getAllocdtl_alloc_id() {
		return allocdtl_alloc_id;
	}



	public String getDsp_id() {
		return dsp_id;
	}

	public void setDsp_id(String dsp_id) {
		this.dsp_id = dsp_id;
	}

	public String getDsp_challan_no() {
		return dsp_challan_no;
	}

	public void setDsp_challan_no(String dsp_challan_no) {
		this.dsp_challan_no = dsp_challan_no;
	}

	public String getDsp_dt() {
		return dsp_dt;
	}

	public void setDsp_dt(String dsp_dt) {
		this.dsp_dt = dsp_dt;
	}

	public String getDsp_lr_no() {
		return dsp_lr_no;
	}

	public String getAllocdtl_id() {
		return allocdtl_id;
	}

	public void setAllocdtl_id(String allocdtl_id) {
		this.allocdtl_id = allocdtl_id;
	}

	public void setAllocdtl_alloc_id(String allocdtl_alloc_id) {
		this.allocdtl_alloc_id = allocdtl_alloc_id;
	}

	public void setDsp_lr_no(String dsp_lr_no) {
		this.dsp_lr_no = dsp_lr_no;
	}

	public String getDsp_lr_dt() {
		return dsp_lr_dt;
	}

	public void setDsp_lr_dt(String dsp_lr_dt) {
		this.dsp_lr_dt = dsp_lr_dt;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public Long getQty_supplied() {
		return qty_supplied;
	}

	public void setQty_supplied(Long qty_supplied) {
		this.qty_supplied = qty_supplied;
	}

	public Long getPending_qty() {
		return pending_qty;
	}

	public void setPending_qty(Long pending_qty) {
		this.pending_qty = pending_qty;
	}

	public String getE_inv_no() {
		return e_inv_no;
	}

	public void setE_inv_no(String e_inv_no) {
		this.e_inv_no = e_inv_no;
	}

	public String getE_inv_date() {
		return e_inv_date;
	}

	public void setE_inv_date(String e_inv_date) {
		this.e_inv_date = e_inv_date;
	}

	public String getTrn_ewaybillno() {
		return trn_ewaybillno;
	}

	public void setTrn_ewaybillno(String trn_ewaybillno) {
		this.trn_ewaybillno = trn_ewaybillno;
	}

	public String getTrn_ewaybilldt() {
		return trn_ewaybilldt;
	}

	public void setTrn_ewaybilldt(String trn_ewaybilldt) {
		this.trn_ewaybilldt = trn_ewaybilldt;
	}

	@Override
	public String toString() {
		return "New_stockist_wise_scheme_request_supply_Detail_model [row=" + row + ", allocdtl_alloc_id="
				+ allocdtl_alloc_id + ", allocdtl_id=" + allocdtl_id + ", dsp_id=" + dsp_id + ", dsp_challan_no="
				+ dsp_challan_no + ", dsp_dt=" + dsp_dt + ", dsp_lr_no=" + dsp_lr_no + ", dsp_lr_dt=" + dsp_lr_dt
				+ ", prod_id=" + prod_id + ", prod_name=" + prod_name + ", qty_supplied=" + qty_supplied
				+ ", pending_qty=" + pending_qty + ", e_inv_no=" + e_inv_no + ", e_inv_date=" + e_inv_date
				+ ", trn_ewaybillno=" + trn_ewaybillno + ", trn_ewaybilldt=" + trn_ewaybilldt + "]";
	}
	
	
}

