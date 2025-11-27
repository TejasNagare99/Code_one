package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ApprovalMailValidation.class)
public class ApprovalMailValidation_ {
	
	public static volatile SingularAttribute<ApprovalMailValidation, Long> id;
	public static volatile SingularAttribute<ApprovalMailValidation, Long> tranrefid;
	public static volatile SingularAttribute<ApprovalMailValidation, String> trantype;
	public static volatile SingularAttribute<ApprovalMailValidation, Long> processid;
	public static volatile SingularAttribute<ApprovalMailValidation, String> emailid_of_approver;
	public static volatile SingularAttribute<ApprovalMailValidation, String> approver_user_id;
	public static volatile SingularAttribute<ApprovalMailValidation, String> status;
	public static volatile SingularAttribute<ApprovalMailValidation, Date> approval_datetime;
	public static volatile SingularAttribute<ApprovalMailValidation, String> amv_ins_usr_id;
	public static volatile SingularAttribute<ApprovalMailValidation, String> amv_mod_usr_id;
	public static volatile SingularAttribute<ApprovalMailValidation, Date> amv_ins_dt;
	public static volatile SingularAttribute<ApprovalMailValidation, Date> amv_mod_dt;
	public static volatile SingularAttribute<ApprovalMailValidation, String> amv_ins_ip_add;
	public static volatile SingularAttribute<ApprovalMailValidation, String> amv_mod_ip_add;
	public static volatile SingularAttribute<ApprovalMailValidation, String> amv_status;
	public static volatile SingularAttribute<ApprovalMailValidation, Long> task_id;
}
