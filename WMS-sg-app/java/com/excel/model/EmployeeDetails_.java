package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EmployeeDetails.class)
public class EmployeeDetails_ {

	public static volatile SingularAttribute<EmployeeDetails, String> emp_id;
	public static volatile SingularAttribute<EmployeeDetails, Long> fstaff_id;
	public static volatile SingularAttribute<EmployeeDetails, String> emp_loc_id;
}
