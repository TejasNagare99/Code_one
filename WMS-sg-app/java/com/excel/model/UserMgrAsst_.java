package com.excel.model;

import javax.persistence.Column;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserMgrAsst.class)
public class UserMgrAsst_ {

	public static volatile SingularAttribute<UserMgrAsst, Long> asst_user_id;
	public static volatile SingularAttribute<UserMgrAsst, String> asst_ld_emp_cb_id;
	public static volatile SingularAttribute<UserMgrAsst, Long> mgr_user_id;
	public static volatile SingularAttribute<UserMgrAsst, String> mgr_ld_emp_cb_id;

}
