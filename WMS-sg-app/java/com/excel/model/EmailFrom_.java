package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EmailFrom.class)
public class EmailFrom_ {

	public static volatile SingularAttribute<EmailFrom, String> company;
	public static volatile SingularAttribute<EmailFrom, String> tran_type;
}
