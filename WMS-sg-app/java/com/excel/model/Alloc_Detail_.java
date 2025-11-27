package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Alloc_Detail.class)
public class Alloc_Detail_ {

	public static volatile SingularAttribute<Alloc_Detail, Long> allocdtl_div_id;
	public static volatile SingularAttribute<Alloc_Detail, String> allocdtl_period_code;
	public static volatile SingularAttribute<Alloc_Detail, String> allocdtl_company;
	public static volatile SingularAttribute<Alloc_Detail, Long> allocdtl_fstaff_id;
	public static volatile SingularAttribute<Alloc_Detail, String> allocdtl_fin_year;
	public static volatile SingularAttribute<Alloc_Detail, Long> allocdtl_prod_id;
	public static volatile SingularAttribute<Alloc_Detail, String> allocdtl_alloc_type;
}
