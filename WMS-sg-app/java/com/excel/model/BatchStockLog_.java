package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BatchStockLog.class)
public class BatchStockLog_ {
		
	public static volatile SingularAttribute<BatchStockLog, Long> btchstklg_id;
	public static volatile SingularAttribute<BatchStockLog, String> btchstklg_fin_year;
	public static volatile SingularAttribute<BatchStockLog, Date> btchstklg_date;
	public static volatile SingularAttribute<BatchStockLog, Long> btchstklg_loc_id;
	public static volatile SingularAttribute<BatchStockLog, Long> btchstklg_prod_id;
	public static volatile SingularAttribute<BatchStockLog, Long> btchstklg_batch_id;
	public static volatile SingularAttribute<BatchStockLog, String> btchstklg_div_id;
	public static volatile SingularAttribute<BatchStockLog, String> btchstklg_tran_type;
	public static volatile SingularAttribute<BatchStockLog, BigDecimal> btchstklg_qty;
	public static volatile SingularAttribute<BatchStockLog, BigDecimal> btchstklg_value;
	public static volatile SingularAttribute<BatchStockLog, String> btchstklg_ins_usr_id;
	public static volatile SingularAttribute<BatchStockLog, String> btchstklg_mod_usr_id;
	public static volatile SingularAttribute<BatchStockLog, Date> btchstklg_ins_dt;
	public static volatile SingularAttribute<BatchStockLog, Date> btchstklg_mod_dt;
	public static volatile SingularAttribute<BatchStockLog, String> btchstklg_ins_ip_add;
	public static volatile SingularAttribute<BatchStockLog, String> btchstklg_mod_ip_add;
	public static volatile SingularAttribute<BatchStockLog, String> btchstklg_status;


}
