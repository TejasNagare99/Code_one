package com.excel.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FinYear.class)
public class FinYear_ {

	public static volatile SingularAttribute<FinYear, Long> fin_year;
	public static volatile SingularAttribute<FinYear, String> vat_transition_end;
	public static volatile SingularAttribute<FinYear, Date> fin_start_date;
	public static volatile SingularAttribute<FinYear, Date> fin_end_date;
	public static volatile SingularAttribute<FinYear, String> fin_current;
	public static volatile SingularAttribute<FinYear, String> fin_status;
	public static volatile SingularAttribute<FinYear, String> fullDate;
	public static volatile SingularAttribute<FinYear, String> gst_ind;
	public static volatile SingularAttribute<FinYear, String> gst_curr_year;
	public static volatile SingularAttribute<FinYear, Date> gst_start_date;
	public static volatile SingularAttribute<FinYear, String> fin_company;

	
}
