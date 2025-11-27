package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Supplier.class)
public class Supplier_ {
	public static volatile SingularAttribute<Supplier, Long> state_id;
	public static volatile SingularAttribute<Supplier, Long> sup_id;
	public static volatile SingularAttribute<Supplier, String> gst_reg_no;
	public static volatile SingularAttribute<Supplier, String> gst_req_ind;
}
