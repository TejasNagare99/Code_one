package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CustomerMaster.class)
public class CustomerMaster_ {
	public static volatile SingularAttribute<CustomerMaster, Long> loc_id;	
	public static volatile SingularAttribute<CustomerMaster, Long> cust_id;
	public static volatile SingularAttribute<CustomerMaster, String> cust_name_billto;
	public static volatile SingularAttribute<CustomerMaster, String> company_cd;
	public static volatile SingularAttribute<CustomerMaster, String> cust_cd;
	public static volatile SingularAttribute<CustomerMaster, String> erp_cust_cd;
	public static volatile SingularAttribute<CustomerMaster, String> discontinued;
	public static volatile SingularAttribute<CustomerMaster, String> gstin_no;
	public static volatile SingularAttribute<CustomerMaster, String> cust_name_shipto;
	public static volatile SingularAttribute<CustomerMaster, String> cust_code_shipto;
	public static volatile SingularAttribute<CustomerMaster, String> destination;
	public static volatile SingularAttribute<CustomerMaster, String> destination_shipto;
	public static volatile SingularAttribute<CustomerMaster, String> soldtogstin_no;
}
