package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(V_GRN_Header.class)
public class V_GRN_Header_ {
	
	public static volatile SingularAttribute<V_GRN_Header, Long> grn_id;	
	public static volatile SingularAttribute<V_GRN_Header, String> grn_dt_disp;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_no;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_lr_no;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_lr_dt_disp;
	public static volatile SingularAttribute<V_GRN_Header, String> trantype;
	public static volatile SingularAttribute<V_GRN_Header, Long> grn_loc_id;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_status;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_company;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_transfer_dt_disp;
	public static volatile SingularAttribute<V_GRN_Header, String> suptype_nm;
	public static volatile SingularAttribute<V_GRN_Header, Long> sup_id;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_transfer_no;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_lorry_no;
	public static volatile SingularAttribute<V_GRN_Header, String> sup_type;
	public static volatile SingularAttribute<V_GRN_Header, Long> grn_doctype_id;
	public static volatile SingularAttribute<V_GRN_Header, String> sup_nm;
	public static volatile SingularAttribute<V_GRN_Header, BigDecimal> grn_octroi;
	public static volatile SingularAttribute<V_GRN_Header, String> grnInsUsrId;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_appr_status;
	public static volatile SingularAttribute<V_GRN_Header, Long> grn_appr_req;
	public static volatile SingularAttribute<V_GRN_Header, Long> grn_appr_acq;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_po_no;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_po_date;
	public static volatile SingularAttribute<V_GRN_Header, Date> grn_dt;
	public static volatile SingularAttribute<V_GRN_Header, Long> grn_appr_cycle;
	public static volatile SingularAttribute<V_GRN_Header, String> user_name;
	public static volatile SingularAttribute<V_GRN_Header, String> remarks;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_period_code;
	public static volatile SingularAttribute<V_GRN_Header, String> grn_fin_year;
	public static volatile SingularAttribute<GrnHeader, String> gcma_code;
	public static volatile SingularAttribute<GrnHeader, Date> gcma_appr_date;
	public static volatile SingularAttribute<GrnHeader, Date> gcma_expiry_date;
	public static volatile SingularAttribute<GrnHeader, Long> return_from_fs_id;
	public static volatile SingularAttribute<GrnHeader, Long> grn_type_id;
	public static volatile SingularAttribute<GrnHeader, BigDecimal> grn_total_value;

}
