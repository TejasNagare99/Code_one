package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(GrnDetails.class)
public class GrnDetails_ {
	
	public static volatile SingularAttribute<GrnDetails, Long> grndId;
	public static volatile SingularAttribute<GrnDetails, Long> grndGrnId;
	public static volatile SingularAttribute<GrnDetails, Long> grnd_sl_no;
	public static volatile SingularAttribute<GrnDetails, String> grnd_company;
	public static volatile SingularAttribute<GrnDetails, Long> grnd_loc_id;
	public static volatile SingularAttribute<GrnDetails, String> grnd_fin_year;
	public static volatile SingularAttribute<GrnDetails, String> grnd_period_code;
	public static volatile SingularAttribute<GrnDetails, Long> grnd_div_id;
	public static volatile SingularAttribute<GrnDetails, Long> grnd_prod_id;
	public static volatile SingularAttribute<GrnDetails, Long> grnd_batch_id;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> grnd_qty;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> grnd_rate;
	public static volatile SingularAttribute<GrnDetails, String> grnd_ins_usr_id;
	public static volatile SingularAttribute<GrnDetails, String> grnd_mod_usr_id;
	public static volatile SingularAttribute<GrnDetails, Date> grnd_ins_dt;
	public static volatile SingularAttribute<GrnDetails, Date> grnd_mod_dt;
	public static volatile SingularAttribute<GrnDetails, String> grnd_ins_ip_add;
	public static volatile SingularAttribute<GrnDetails, String> grnd_mod_ip_add;
	public static volatile SingularAttribute<GrnDetails, String> grnd_status;
	public static volatile SingularAttribute<GrnDetails, String> grnd_returnfrom;
	public static volatile SingularAttribute<GrnDetails, String> grnd_challanno;
	public static volatile SingularAttribute<GrnDetails, Long> grnd_noofboxes;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> grnd_value;
	public static volatile SingularAttribute<GrnDetails, String> grnd_image_path;
	public static volatile SingularAttribute<GrnDetails, Long> grnd_bin_id;
	public static volatile SingularAttribute<GrnDetails, String> grnd_appr_status;
	public static volatile SingularAttribute<GrnDetails, String> grnd_image_moved;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> sgst_rate;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> sgst_bill_amt;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> cgst_rate;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> CGST_BILL_AMT;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> igst_rate;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> igst_bill_amt;
	public static volatile SingularAttribute<GrnDetails, String> gst_reverse_chg;
	public static volatile SingularAttribute<GrnDetails, String> gst_doc_type;
	public static volatile SingularAttribute<GrnDetails, String> text1;
	public static volatile SingularAttribute<GrnDetails, String> text2;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> value1;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> value2;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> grnd_pur_rate;
	public static volatile SingularAttribute<GrnDetails, BigDecimal> grnd_value_pur;
}
