package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Msr_Stock.class)
public class Msr_Stock_ {

	public static volatile SingularAttribute<Msr_Stock, Long> fs_id;
	public static volatile SingularAttribute<Msr_Stock, String> period_code;
	public static volatile SingularAttribute<Msr_Stock, Long> prod_id;
	public static volatile SingularAttribute<Msr_Stock, String> year;

}
