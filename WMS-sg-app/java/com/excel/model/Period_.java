package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Period.class)
public class Period_ {
	
	public static volatile SingularAttribute<Period, Long> period_id;
	public static volatile SingularAttribute<Period, String> period_company;
	public static volatile SingularAttribute<Period, String> period_fin_year;
	public static volatile SingularAttribute<Period, String> period_code;
	public static volatile SingularAttribute<Period, String> period_name;
	public static volatile SingularAttribute<Period, String> period_alt_name;
	public static volatile SingularAttribute<Period, String> period_start_date;
	public static volatile SingularAttribute<Period, String> period_end_date;
	public static volatile SingularAttribute<Period, String> period_current;
	public static volatile SingularAttribute<Period, String> period_map_cd;
	public static volatile SingularAttribute<Period, String> period_monthclose;
	public static volatile SingularAttribute<Period, String> period_ins_usr_id;
	public static volatile SingularAttribute<Period, String> period_mod_usr_id;
	public static volatile SingularAttribute<Period, String> period_ins_dt;
	public static volatile SingularAttribute<Period, String> period_mod_dt;
	public static volatile SingularAttribute<Period, String> period_ins_ip_add;
	public static volatile SingularAttribute<Period, String> period_mod_ip_add;
	public static volatile SingularAttribute<Period, String> period_status;
}
