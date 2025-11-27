package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DivMap.class)
public class DivMap_ {
	
	public static volatile SingularAttribute<DivMap, Long> map_id;
	public static volatile SingularAttribute<DivMap, Long> base_div_id;
	public static volatile SingularAttribute<DivMap, Long> prod_div_id;
	public static volatile SingularAttribute<DivMap, String> core_ind;
	public static volatile SingularAttribute<DivMap, Date> ins_dt;
	public static volatile SingularAttribute<DivMap, String> ins_ip_add;
	public static volatile SingularAttribute<DivMap, Date> mod_dt;
	public static volatile SingularAttribute<DivMap, String> mod_ip_add;
}
