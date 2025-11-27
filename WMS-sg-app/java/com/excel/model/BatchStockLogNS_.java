package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BatchStockLogNS.class)
public class BatchStockLogNS_ {
		
	public static volatile SingularAttribute<BatchStockLogNS, Long> btchstklg_id;
	public static volatile SingularAttribute<BatchStockLogNS, String> btchstklg_fin_year;
	public static volatile SingularAttribute<BatchStockLogNS, Date> btchstklg_date;
	public static volatile SingularAttribute<BatchStockLogNS, Long> btchstklg_loc_id;
	public static volatile SingularAttribute<BatchStockLogNS, Long> btchstklg_prod_id;
	public static volatile SingularAttribute<BatchStockLogNS, Long> btchstklg_batch_id;
	public static volatile SingularAttribute<BatchStockLogNS, String> btchstklg_tran_type;
	public static volatile SingularAttribute<BatchStockLogNS, BigDecimal> btchstklg_qty;
	public static volatile SingularAttribute<BatchStockLogNS, BigDecimal> btchstklg_value;
	public static volatile SingularAttribute<BatchStockLogNS, String> btchstklg_ins_usr_id;
	public static volatile SingularAttribute<BatchStockLogNS, String> btchstklg_mod_usr_id;
	public static volatile SingularAttribute<BatchStockLogNS, Date> btchstklg_ins_dt;
	public static volatile SingularAttribute<BatchStockLogNS, Date> btchstklg_mod_dt;
	public static volatile SingularAttribute<BatchStockLogNS, String> btchstklg_ins_ip_add;
	public static volatile SingularAttribute<BatchStockLogNS, String> btchstklg_mod_ip_add;
	public static volatile SingularAttribute<BatchStockLogNS, String> btchstklg_status;


}
