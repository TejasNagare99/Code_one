package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SmpBatch.class)
public class SmpBatch_ {
	public static volatile SingularAttribute<SmpBatch, Long> batch_id;
	public static volatile SingularAttribute<SmpBatch, Long> batch_prod_id;
	public static volatile SingularAttribute<SmpBatch, Long> batch_loc_id;
	public static volatile SingularAttribute<SmpBatch, String> batch_no;
	public static volatile SingularAttribute<SmpBatch, Date> batch_mfg_dt;
	public static volatile SingularAttribute<SmpBatch, Date> batch_expiry_dt;
	public static volatile SingularAttribute<SmpBatch, Long> batch_physical_stock;
	public static volatile SingularAttribute<SmpBatch, String> batch_narration;
	public static volatile SingularAttribute<SmpBatch, Long> batch_mfg_loc_id;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> batch_rate;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> batch_costing_rate;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> batch_mktg_rate;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> batch_nrv;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> batch_display_rate;
	public static volatile SingularAttribute<SmpBatch, Long> batch_open_stock;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> batch_in_stock;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> batch_out_stock;
	public static volatile SingularAttribute<SmpBatch, String> batch_exclude_loc;
	public static volatile SingularAttribute<SmpBatch, String> batch_exclude_PARTY;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> batch_with_held_qty;
	public static volatile SingularAttribute<SmpBatch, String> batch_erp_batch_cd;
	public static volatile SingularAttribute<SmpBatch, String> batch_ins_usr_id;
	public static volatile SingularAttribute<SmpBatch, String> batch_mod_usr_id;
	public static volatile SingularAttribute<SmpBatch, Date> batch_ins_dt;
	public static volatile SingularAttribute<SmpBatch, Date> batch_mod_dt;
	public static volatile SingularAttribute<SmpBatch, String> batch_ins_ip_add;
	public static volatile SingularAttribute<SmpBatch, String> batch_mod_ip_add;
	public static volatile SingularAttribute<SmpBatch, String> batch_status;
	public static volatile SingularAttribute<SmpBatch, BigDecimal> quanrantine;
	

}
