package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AspDetail.class)
public class AspDetail_ {

	public static volatile SingularAttribute<AspDetail, Long> asp_div_id;
	public static volatile SingularAttribute<AspDetail, Long> asp_prod_id;
	public static volatile SingularAttribute<AspDetail, String> asp_dtl_fin_year;
	public static volatile SingularAttribute<AspDetail, String> asp_status;	
	
}
