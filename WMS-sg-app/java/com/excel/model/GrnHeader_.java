package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(GrnHeader.class)
public class GrnHeader_ {
	
	public static volatile SingularAttribute<GrnHeader, Long> grnId;
	public static volatile SingularAttribute<GrnHeader, String> grn_no;
	public static volatile SingularAttribute<GrnHeader, Long> grnd_sl_no;
	public static volatile SingularAttribute<GrnHeader, String> grn_company;
	public static volatile SingularAttribute<GrnHeader, String> grn_fin_year;
	public static volatile SingularAttribute<GrnHeader, String> grn_period_code;
	public static volatile SingularAttribute<GrnHeader, Long> grn_loc_id;
	public static volatile SingularAttribute<GrnHeader, Date> grn_dt;
	public static volatile SingularAttribute<GrnHeader, Long> grn_supplier_id;
	public static volatile SingularAttribute<GrnHeader, String> grn_transfer_no;
	public static volatile SingularAttribute<GrnHeader, Date> grn_transfer_dt;
	public static volatile SingularAttribute<GrnHeader, String> grn_lr_no;
	public static volatile SingularAttribute<GrnHeader, Date> grn_lr_dt;
	public static volatile SingularAttribute<GrnHeader, String> grn_lorry_no;
	public static volatile SingularAttribute<GrnHeader, Long> grn_purchase_id;
	public static volatile SingularAttribute<GrnHeader, Long> grn_sending_loc_id;
	public static volatile SingularAttribute<GrnHeader, String> grn_trf_type;
	public static volatile SingularAttribute<GrnHeader, Date> grn_trf_dt;
	public static volatile SingularAttribute<GrnHeader, String> grn_in_transit;
	public static volatile SingularAttribute<GrnHeader, Date> grn_auto_grn_dt;
	public static volatile SingularAttribute<GrnHeader, String> grn_auto_grn_auth;
	public static volatile SingularAttribute<GrnHeader, String> grn_locked_yn;
	public static volatile SingularAttribute<GrnHeader, String> grn_erp_grn_cd;
	public static volatile SingularAttribute<GrnHeader, String> grn_erp_stktfr_cd;
	public static volatile SingularAttribute<GrnHeader, String> grn_delivery_no;
	public static volatile SingularAttribute<GrnHeader, String> grn_f_form_no;
	public static volatile SingularAttribute<GrnHeader, String> grn_consignment_type;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> grn_full_shippers;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> grn_loose_shippers;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> grn_weight;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> grn_volume;
	public static volatile SingularAttribute<GrnHeader, String> grn_transport_mode;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> grn_octroi;
	public static volatile SingularAttribute<GrnHeader, String> grn_ins_usr_id;
	public static volatile SingularAttribute<GrnHeader, String> grn_mod_usr_id;
	public static volatile SingularAttribute<GrnHeader, Date> grn_ins_dt;
	public static volatile SingularAttribute<GrnHeader, Date> grn_mod_dt;
	public static volatile SingularAttribute<GrnHeader, String> grn_ins_ip_add;
	public static volatile SingularAttribute<GrnHeader, String> grn_mod_ip_add;
	public static volatile SingularAttribute<GrnHeader, String> grn_status;
	public static volatile SingularAttribute<GrnHeader, Long> grn_doctype_id;
	public static volatile SingularAttribute<GrnHeader, Long> grn_appr_req;
	public static volatile SingularAttribute<GrnHeader, Long> grn_appr_acq;
	public static volatile SingularAttribute<GrnHeader, String> grn_appr_status;
	public static volatile SingularAttribute<GrnHeader, String> grn_po_no;
	public static volatile SingularAttribute<GrnHeader, Date> grn_po_date;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> grn_total_value;
	public static volatile SingularAttribute<GrnHeader, String> grn_appr_cycle;
	public static volatile SingularAttribute<GrnHeader, String> remarks;
	public static volatile SingularAttribute<GrnHeader, String> grn_lbl_print;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> sgst_bill_amt;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> cgst_bill_amt;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> igst_bill_amt;
	public static volatile SingularAttribute<GrnHeader, String> gst_reverse_chg;
	public static volatile SingularAttribute<GrnHeader, String> gst_doc_type;
	public static volatile SingularAttribute<GrnHeader, String> text1;
	public static volatile SingularAttribute<GrnHeader, String> text2;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> value1;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> value2;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> roundoff;
	public static volatile SingularAttribute<GrnHeader, String> gcma_code;
	public static volatile SingularAttribute<GrnHeader, Date> gcma_appr_date;
	public static volatile SingularAttribute<GrnHeader, Date> gcma_expiry_date;
	public static volatile SingularAttribute<GrnHeader, Long> return_from_fs_id;
	public static volatile SingularAttribute<GrnHeader, Long> grn_type_id;
	public static volatile SingularAttribute<GrnHeader, String> grn_stock_sa_or_ns;
	public static volatile SingularAttribute<GrnHeader, Long> grn_stock_type_id;

}
