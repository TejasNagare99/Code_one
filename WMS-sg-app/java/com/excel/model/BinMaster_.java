package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BinMaster.class)
public class BinMaster_ {
		
	public static volatile SingularAttribute<BinMaster, Long> bin_id;
	public static volatile SingularAttribute<BinMaster, String> bin_comp_id;
	public static volatile SingularAttribute<BinMaster, Long> bin_loc_id;
	public static volatile SingularAttribute<BinMaster, String> bin_warehouse_code;
	public static volatile SingularAttribute<BinMaster, String> bin_storage_type;
	public static volatile SingularAttribute<BinMaster, String> bin_floor_level;
	public static volatile SingularAttribute<BinMaster, String> bin_storage_area;
	public static volatile SingularAttribute<BinMaster, Long> bin_rack_no;
	public static volatile SingularAttribute<BinMaster, Long> bin_shelf;
	public static volatile SingularAttribute<BinMaster, Long> bin_bin_no;
	public static volatile SingularAttribute<BinMaster, String> bin_code;
	public static volatile SingularAttribute<BinMaster, String> bin_barcode;
	public static volatile SingularAttribute<BinMaster, String> bin_descr;
	public static volatile SingularAttribute<BinMaster, String> bin_status;
	public static volatile SingularAttribute<BinMaster, String> bin_ins_usr_id;
	public static volatile SingularAttribute<BinMaster, String> bin_mod_usr_id;
	public static volatile SingularAttribute<BinMaster, Date> bin_ins_dt;
	public static volatile SingularAttribute<BinMaster, Date> bin_mod_dt;
	public static volatile SingularAttribute<BinMaster, String> bin_ins_ip_add;
	public static volatile SingularAttribute<BinMaster, String> bin_mod_ip_add;
	public static volatile SingularAttribute<BinMaster, String> bin_in_use;
	public static volatile SingularAttribute<BinMaster, String> way_bill_no;

}
