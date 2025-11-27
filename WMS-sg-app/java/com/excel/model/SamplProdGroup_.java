package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SamplProdGroup.class)
public class SamplProdGroup_ {
	
	public static volatile SingularAttribute<SamplProdGroup, String> loc_id;
	
	public static volatile SingularAttribute<SamplProdGroup, Long> sa_prod_group;
	public static volatile SingularAttribute<SamplProdGroup,  String> sa_group_name;
	public static volatile SingularAttribute<SamplProdGroup,  String> mpg_code;
	public static volatile SingularAttribute<SamplProdGroup,  String> ins_usr_id;
	public static volatile SingularAttribute<SamplProdGroup,  Date> ins_dt;
	public static volatile SingularAttribute<SamplProdGroup,  String> ins_ip_add;
	public static volatile SingularAttribute<SamplProdGroup,  String> status;

}
