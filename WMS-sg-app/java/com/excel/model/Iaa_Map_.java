package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Iaa_Map.class)

public class Iaa_Map_ {

	public static volatile SingularAttribute<Iaa_Map, Long> reason_id;
	public static volatile SingularAttribute<Iaa_Map, String> reason_nm;
	public static volatile SingularAttribute<Iaa_Map, String> item_change_accept;
	public static volatile SingularAttribute<Iaa_Map, String> out_stock_type;
	public static volatile SingularAttribute<Iaa_Map, String> in_stock_type;
	public static volatile SingularAttribute<Iaa_Map, String> in_item_accept;
	public static volatile SingularAttribute<Iaa_Map, String> in_batch_accept;
	public static volatile SingularAttribute<Iaa_Map, String> in_qty_accept;
	public static volatile SingularAttribute<Iaa_Map, String> new_batch_accept;
	public static volatile SingularAttribute<Iaa_Map, String> in_rate_accept;
	public static volatile SingularAttribute<Iaa_Map, String> out_tran_type;
	public static volatile SingularAttribute<Iaa_Map, String> in_tran_type;
	public static volatile SingularAttribute<Iaa_Map, Date> reason_mod_dt;
	public static volatile SingularAttribute<Iaa_Map, String> reason_mod_ip_add;
	public static volatile SingularAttribute<Iaa_Map, String> reason_status;

}
