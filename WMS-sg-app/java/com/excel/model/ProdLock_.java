package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ProdLock.class)
public class ProdLock_ {
	public static volatile SingularAttribute<ProdLock, Long> slno;
	public static volatile SingularAttribute<ProdLock, Long> loc_id;
	public static volatile SingularAttribute<ProdLock, Long> prod_id;
	public static volatile SingularAttribute<ProdLock, Long> user_id;
	public static volatile SingularAttribute<ProdLock, Long> log_time;
	

}
