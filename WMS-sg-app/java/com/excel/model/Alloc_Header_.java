package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Alloc_Header.class)
public class Alloc_Header_ {

	
	public static volatile SingularAttribute<Alloc_Header, String> alloc_period_code;
	public static volatile SingularAttribute<Alloc_Header, String> alloc_company;
	public static volatile SingularAttribute<Alloc_Header, Long> alloc_fstaff_id;
	public static volatile SingularAttribute<Alloc_Header, String> alloc_fin_year;
	public static volatile SingularAttribute<Alloc_Header, String> alloc_type;
	
}
