package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Location.class)
public class Location_ {
	
	public static volatile SingularAttribute<Location, Long> loc_id;
	public static volatile SingularAttribute<Location, String> loc_nm;
	public static volatile SingularAttribute<Location, String> loc_prefix;
	public static volatile SingularAttribute<Location, String> loc_dsppreix;
	public static volatile SingularAttribute<Location, String> loc_sup_id;
	public static volatile SingularAttribute<Location, String> loc_type;
	public static volatile SingularAttribute<Location, String> loc_add1;
	public static volatile SingularAttribute<Location, String> loc_add2;
	public static volatile SingularAttribute<Location, String> loc_add3;
	public static volatile SingularAttribute<Location, String> loc_add4;
	public static volatile SingularAttribute<Location, Long> loc_city_id;
	public static volatile SingularAttribute<Location, Long> loc_state_id;
	public static volatile SingularAttribute<Location, String> loc_zone;
	public static volatile SingularAttribute<Location, String> loc_license1;
	public static volatile SingularAttribute<Location, String> loc_license2;
	public static volatile SingularAttribute<Location, String> loc_license3;
	public static volatile SingularAttribute<Location, String> loc_tin_no;
	public static volatile SingularAttribute<Location, String> loc_godwadd1;
	public static volatile SingularAttribute<Location, String> loc_godwadd2;
	public static volatile SingularAttribute<Location, String> loc_godwadd3;
	public static volatile SingularAttribute<Location, String> loc_godwadd4;
	public static volatile SingularAttribute<Location, String> loc_narration;
	public static volatile SingularAttribute<Location, String> loc_map_cd;
	public static volatile SingularAttribute<Location, String> loc_narrationfooter;
	public static volatile SingularAttribute<Location, String> loc_shownarration;
	public static volatile SingularAttribute<Location, String> loc_narrationheader;
	public static volatile SingularAttribute<Location, Long> loc_ins_usr_id;
	public static volatile SingularAttribute<Location, Long> loc_mod_usr_id;
	public static volatile SingularAttribute<Location, Date> loc_ins_dt;
	public static volatile SingularAttribute<Location, Date> loc_mod_dt;
	public static volatile SingularAttribute<Location, String> loc_ins_ip_add;
	public static volatile SingularAttribute<Location, String> loc_mod_ip_add;
	public static volatile SingularAttribute<Location, String> loc_status;
	public static volatile SingularAttribute<Location, Long> loc_subcomp_id;
	public static volatile SingularAttribute<Location, String> loc_report_status;
	public static volatile SingularAttribute<Location, String> loc_telephone_no;
	public static volatile SingularAttribute<Location, String> loc_mobile_no;
	public static volatile SingularAttribute<Location, String> loc_cinno;
	public static volatile SingularAttribute<Location, String> loc_web_site;
	public static volatile SingularAttribute<Location, String> loc_emailid;
	public static volatile SingularAttribute<Location, String> GST_REG_NO;
	public static volatile SingularAttribute<Location, String> GST_REQ_IND;
	public static volatile SingularAttribute<Location, String> GST_ON_INDV_CHLN;
	public static volatile SingularAttribute<Location, String> PAN_NO;
	public static volatile SingularAttribute<Location, Long> LOC_LINK_DPTLOC_ID;
	public static volatile SingularAttribute<Location, String> BILL_OF_SUPPLY_PREFIX;
	public static volatile SingularAttribute<Location, String> BILL_OF_SUPPLY_PREFIX_SUM;
	public static volatile SingularAttribute<Location, String> autodesp_inprocess;
}
