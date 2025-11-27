package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DoctorMaster.class)
public class DoctorMaster_ {
	
	public static volatile SingularAttribute<DoctorMaster, Long> doc_id;	
	public static volatile SingularAttribute<DoctorMaster, String> doc_name;
	public static volatile SingularAttribute<DoctorMaster, String> company;
	public static volatile SingularAttribute<DoctorMaster, Long> fstaff_id;	
	public static volatile SingularAttribute<DoctorMaster, String> doc_status;
	public static volatile SingularAttribute<DoctorMaster, String> fstaff_emp_id;
	public static volatile SingularAttribute<DoctorMaster, String> mcl_no;
	public static volatile SingularAttribute<DoctorMaster, String> doc_phone;
	public static volatile SingularAttribute<DoctorMaster, String> doc_prefix;
}
