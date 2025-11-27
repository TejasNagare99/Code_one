package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(V_Grn_Details.class)
public class V_Grn_Details_ {
	
	public static volatile SingularAttribute<V_Grn_Details, Long> grndId;
	public static volatile SingularAttribute<V_Grn_Details, Long> grndGrnId;
	public static volatile SingularAttribute<V_Grn_Details, Long> grnd_prod_id;
	public static volatile SingularAttribute<V_Grn_Details, Long> grnd_batch_id;
	public static volatile SingularAttribute<V_Grn_Details, BigDecimal> grnd_qty;
	public static volatile SingularAttribute<V_Grn_Details, BigDecimal> grnd_rate;
	public static volatile SingularAttribute<V_Grn_Details, String> batch_no;
	public static volatile SingularAttribute<V_Grn_Details, String> batch_narration;
	public static volatile SingularAttribute<V_Grn_Details, String> batch_mfg_dt_disp;
	public static volatile SingularAttribute<V_Grn_Details, String> smp_batch_req_ind;
	public static volatile SingularAttribute<V_Grn_Details, String> batch_expiry_dt_disp;
	public static volatile SingularAttribute<V_Grn_Details, String> smp_prod_name;
	public static volatile SingularAttribute<V_Grn_Details, String> smp_prod_cd;
	public static volatile SingularAttribute<V_Grn_Details, BigDecimal> value;
	public static volatile SingularAttribute<V_Grn_Details, Long> grnd_noofboxes;
	public static volatile SingularAttribute<V_Grn_Details, String> grnd_returnfrom;
	public static volatile SingularAttribute<V_Grn_Details, String> grnd_challanno;
	public static volatile SingularAttribute<V_Grn_Details, Long> smp_prod_type_id;
	public static volatile SingularAttribute<V_Grn_Details, String> smp_prod_type;
	public static volatile SingularAttribute<V_Grn_Details, String> prod_type_short_nm;
	public static volatile SingularAttribute<V_Grn_Details, String> grnd_image_path;
	public static volatile SingularAttribute<V_Grn_Details, String> grnd_appr_status;
	public static volatile SingularAttribute<V_Grn_Details, String> grnd_bin_id;
	public static volatile SingularAttribute<V_Grn_Details, String> text1;
	public static volatile SingularAttribute<V_Grn_Details, String> text2;
	public static volatile SingularAttribute<V_Grn_Details, BigDecimal> value1;
	public static volatile SingularAttribute<V_Grn_Details, BigDecimal> value2;

}
