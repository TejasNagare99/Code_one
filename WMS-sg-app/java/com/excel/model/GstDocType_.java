package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(GstDocType.class)
public class GstDocType_ {

	public static volatile SingularAttribute<GstDocType, String> para_code;
	public static volatile SingularAttribute<GstDocType, String> tran_type;
	public static volatile SingularAttribute<GstDocType, String> gst_type;	
}
