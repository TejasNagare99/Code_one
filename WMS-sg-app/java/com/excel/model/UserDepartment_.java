package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserDepartment.class)
public class UserDepartment_ {
	
	public static volatile SingularAttribute<UserDepartment, String> dept_status;
	public static volatile SingularAttribute<UserDepartment, String> dept_nm;

}
