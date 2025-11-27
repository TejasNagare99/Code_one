package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Sub_Company.class)
public class Sub_Company_ {
	
	public static volatile SingularAttribute<Sub_Company, Long> subcomp_id;
	public static volatile SingularAttribute<Sub_Company, String> subcomp_nm;
	public static volatile SingularAttribute<Sub_Company, String> subcomp_status;
	public static volatile SingularAttribute<Sub_Company, String>subcomp_code;
}
