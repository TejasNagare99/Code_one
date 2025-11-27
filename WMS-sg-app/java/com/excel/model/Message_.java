package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Message.class)
public class Message_ {
	
	public static volatile SingularAttribute<Message, Long> sl_no;
	public static volatile SingularAttribute<Message, String> level_code;
	public static volatile SingularAttribute<Message, String> message;
	public static volatile SingularAttribute<Message, String> company_cd;
	public static volatile SingularAttribute<Message, Date> validfrom;
	public static volatile SingularAttribute<Message, Date> validtill;
	public static volatile SingularAttribute<Message, String> path;
	public static volatile SingularAttribute<Message, String> filetype;
	public static volatile SingularAttribute<Message, Long> fs_id;
}
