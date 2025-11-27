package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Stock_Transfer_Details.class)
public class Stock_Transfer_Details_ {
	
	public static volatile SingularAttribute<Stock_Transfer_Details, Stock_Transfer_Header> stockHeader;
	public static volatile SingularAttribute<Stock_Transfer_Details, Long> prod_id;	


}
