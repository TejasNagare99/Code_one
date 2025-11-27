package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SmpBatchNS.class)
public class SmpBatchNS_ {
	public static volatile SingularAttribute<SmpBatchNS, Long> batch_id;
	public static volatile SingularAttribute<SmpBatchNS, Long> batch_prod_id;
	public static volatile SingularAttribute<SmpBatchNS, Long> batch_loc_id;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_no;
	public static volatile SingularAttribute<SmpBatchNS, String> stock_type;
	public static volatile SingularAttribute<SmpBatchNS, Date> batch_mfg_dt;
	public static volatile SingularAttribute<SmpBatchNS, Date> batch_expiry_dt;
	public static volatile SingularAttribute<SmpBatchNS, Long> batch_physical_stock;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_narration;
	public static volatile SingularAttribute<SmpBatchNS, Long> batch_mfg_loc_id;
	public static volatile SingularAttribute<SmpBatchNS, BigDecimal> batch_rate;
	public static volatile SingularAttribute<SmpBatchNS, BigDecimal> batch_costing_rate;
	public static volatile SingularAttribute<SmpBatchNS, BigDecimal> batch_mktg_rate;
	public static volatile SingularAttribute<SmpBatchNS, BigDecimal> batch_nrv;
	public static volatile SingularAttribute<SmpBatchNS, BigDecimal> batch_display_rate;
	public static volatile SingularAttribute<SmpBatchNS, Long> batch_open_stock;
	public static volatile SingularAttribute<SmpBatchNS, BigDecimal> batch_in_stock;
	public static volatile SingularAttribute<SmpBatchNS, BigDecimal> batch_out_stock;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_exclude_loc;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_exclude_PARTY;
	public static volatile SingularAttribute<SmpBatchNS, BigDecimal> batch_with_held_qty;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_erp_batch_cd;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_ins_usr_id;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_mod_usr_id;
	public static volatile SingularAttribute<SmpBatchNS, Date> batch_ins_dt;
	public static volatile SingularAttribute<SmpBatchNS, Date> batch_mod_dt;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_ins_ip_add;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_mod_ip_add;
	public static volatile SingularAttribute<SmpBatchNS, String> batch_status;
	
}