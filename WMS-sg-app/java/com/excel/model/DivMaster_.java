package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DivMaster.class)
public class DivMaster_ {
	
	public static volatile SingularAttribute<DivMaster, String> loc_id;
	
	public static volatile SingularAttribute<DivMaster, Long> div_id;
	public static volatile SingularAttribute<DivMaster, String> div_disp_nm;
	public static volatile SingularAttribute<DivMaster, String> div_map_cd;
	public static volatile SingularAttribute<DivMaster, String> div_desc;
	public static volatile SingularAttribute<DivMaster, String> div_ins_usr_id;
	public static volatile SingularAttribute<DivMaster, String> div_mod_usr_id;
	public static volatile SingularAttribute<DivMaster, Date> div_ins_dt;
	public static volatile SingularAttribute<DivMaster, Date> div_mod_dt;
	public static volatile SingularAttribute<DivMaster, String> div_ins_ip_add;
	public static volatile SingularAttribute<DivMaster, String> div_mod_ip_add;
	public static volatile SingularAttribute<DivMaster, String> div_status;
	public static volatile SingularAttribute<DivMaster, String> div_code;
	public static volatile SingularAttribute<DivMaster, String> div_code_nm;

}
