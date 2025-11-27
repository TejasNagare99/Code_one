package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AspHeader.class)
public class AspHeader_ {

	public static volatile SingularAttribute<AspHeader, Long> asp_userId;
	public static volatile SingularAttribute<AspHeader, Long> asp_brand_mgrId;
	public static volatile SingularAttribute<AspHeader, String> asp_fin_year;
	public static volatile SingularAttribute<AspHeader, Long> asp_div_id;
	public static volatile SingularAttribute<AspHeader, String> asp_status;
	public static volatile SingularAttribute<AspHeader, String> asp_appr_status;
	
}
