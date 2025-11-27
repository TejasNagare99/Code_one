package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Usermaster.class)
public class Usermaster_ {
	
	public static volatile SingularAttribute<Usermaster, Long> ld_id;
	public static volatile SingularAttribute<Usermaster, String> ld_emp_cb_id;
	public static volatile SingularAttribute<Usermaster, String> ld_usr_typ_flg;
	public static volatile SingularAttribute<Usermaster, String> ld_lgnid;
	public static volatile SingularAttribute<Usermaster, String> ld_pass;
	public static volatile SingularAttribute<Usermaster, String> ld_email;
	public static volatile SingularAttribute<Usermaster, String> ld_ins_usr_id;
	public static volatile SingularAttribute<Usermaster, String> ld_mod_usr_id;
	
	public static volatile SingularAttribute<Usermaster, String> ld_ins_dt;
	public static volatile SingularAttribute<Usermaster, String> ld_mod_dt;
	public static volatile SingularAttribute<Usermaster, String> ld_ins_ip_add;
	public static volatile SingularAttribute<Usermaster, String> ld_mod_ip_add;
	public static volatile SingularAttribute<Usermaster, String> ld_status;
	public static volatile SingularAttribute<Usermaster, String> is_temp;
	public static volatile SingularAttribute<Usermaster, String> ld_no_of_attempt_flg;
	
	public static volatile SingularAttribute<Usermaster, String> user_lock;
	public static volatile SingularAttribute<Usermaster, String> dptloc_id;
	public static volatile SingularAttribute<Usermaster, String> allow_batch_create;
	public static volatile SingularAttribute<Usermaster, String> user_type;
	
	public static volatile SingularAttribute<Usermaster, String> pass_mod_dt;
	public static volatile SingularAttribute<Usermaster, String> password_lock;
	public static volatile SingularAttribute<Usermaster, String> password_lock_dt;
	public static volatile SingularAttribute<Usermaster, Long> ld_max_login_attempts;
	public static volatile SingularAttribute<Usermaster, Long> ld_hours_locked;
	
	public static volatile SingularAttribute<Usermaster, String> ld_pass_ang;
}
