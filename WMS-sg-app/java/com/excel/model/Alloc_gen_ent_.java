package com.excel.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.hibernate.annotations.Cascade;

@StaticMetamodel(Alloc_gen_ent.class)
public class Alloc_gen_ent_ {
	
	public static volatile SingularAttribute<Alloc_gen_ent, Long> alloc_gen_ent_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> alloc_gen_dtl_id;
	public static volatile SingularAttribute<Alloc_gen_ent, String> fin_year;
	public static volatile SingularAttribute<Alloc_gen_ent, Date> alloc_gen_date;
	public static volatile SingularAttribute<Alloc_gen_ent, String> period_code;
	public static volatile SingularAttribute<Alloc_gen_ent, String> company;
	public static volatile SingularAttribute<Alloc_gen_ent, String> division;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> div_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> cfa_destination_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> zone_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> state_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> rbm_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> abm_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> msr_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> prod_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> alt_div_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> alloc_cycle;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> alloc_rate;
	public static volatile SingularAttribute<Alloc_gen_ent, Integer> alloc_qty_msr;
	public static volatile SingularAttribute<Alloc_gen_ent, Integer> alloc_qty_abm;
	public static volatile SingularAttribute<Alloc_gen_ent, Integer> alloc_qty_rbm;
	public static volatile SingularAttribute<Alloc_gen_ent, Integer> alloc_qty_tm;
	public static volatile SingularAttribute<Alloc_gen_ent, Integer> alloc_qty_cm;
	public static volatile SingularAttribute<Alloc_gen_ent, String> user_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Date> upd_date;
	public static volatile SingularAttribute<Alloc_gen_ent, String> upd_ip_add;
	public static volatile SingularAttribute<Alloc_gen_ent, String> status;
	public static volatile SingularAttribute<Alloc_gen_ent, String> alloc_mode;
	public static volatile SingularAttribute<Alloc_gen_ent, String> product_type;
	public static volatile SingularAttribute<Alloc_gen_ent, Integer> alloc_qty_total;
	public static volatile SingularAttribute<Alloc_gen_ent, String> fstaff_training;
	public static volatile SingularAttribute<Alloc_gen_ent, Alloc_gen_hd> alloc_gen_id;
	public static volatile SingularAttribute<Alloc_gen_ent, Long> alloc_smp_sa_prod_group_id;
	

}
