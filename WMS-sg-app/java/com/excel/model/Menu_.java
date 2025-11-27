package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Menu.class)
public class Menu_ {
	
	public static volatile SingularAttribute<Menu, Long> menu_id;
	public static volatile SingularAttribute<Menu, String> menu_name;
	public static volatile SingularAttribute<Menu, Long> menu_index;
	public static volatile SingularAttribute<Menu, Long> parent_id;
	public static volatile SingularAttribute<Menu, String> action;
	public static volatile SingularAttribute<Menu, Long> menu_level;
	public static volatile SingularAttribute<Menu, String> css_class;
	public static volatile SingularAttribute<Menu, String> level_code;
	public static volatile SingularAttribute<Menu, String> new_ent;
	public static volatile SingularAttribute<Menu, String> para_code;
	public static volatile SingularAttribute<Menu, String> company_cd;

}
