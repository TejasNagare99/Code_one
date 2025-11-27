package com.excel.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Stock_Transfer_Header.class)
public class Stock_Transfer_Header_ {
	
	public static volatile SingularAttribute<Stock_Transfer_Header, Long> trf_id;	
	public static volatile SingularAttribute<Stock_Transfer_Header, String> trf_no;
	public static volatile SingularAttribute<Stock_Transfer_Header, Long> loc_id;	
	public static volatile SingularAttribute<Stock_Transfer_Header, Date> trf_dt;
	public static volatile SingularAttribute<Stock_Transfer_Header, Long> party_id;	
	public static volatile SingularAttribute<Stock_Transfer_Header, String> lr_no;
	public static volatile SingularAttribute<Stock_Transfer_Header, Date> lr_dt;
	public static volatile SingularAttribute<Stock_Transfer_Header, String> lorry_no;
	public static volatile SingularAttribute<Stock_Transfer_Header, String> accntg_co_cd;
	public static volatile SingularAttribute<Stock_Transfer_Header, Long> rec_loc_id;	
	public static volatile SingularAttribute<Stock_Transfer_Header, String> vat_yn;
	public static volatile SingularAttribute<Stock_Transfer_Header, String> trf_type;
	public static volatile SingularAttribute<Stock_Transfer_Header, String> road_permit;
	public static volatile SingularAttribute<Stock_Transfer_Header, String> f_form_no;
	public static volatile SingularAttribute<Stock_Transfer_Header, BigDecimal> full_shippers;
	public static volatile SingularAttribute<Stock_Transfer_Header, BigDecimal> loose_shippers;
	public static volatile SingularAttribute<Stock_Transfer_Header, BigDecimal> weight;
	public static volatile SingularAttribute<Stock_Transfer_Header, Long> fin_year_id;	
	public static volatile SingularAttribute<Stock_Transfer_Header, String> comp_cd;
	public static volatile SingularAttribute<Stock_Transfer_Header, String> cancelled;
	public static volatile SingularAttribute<Stock_Transfer_Header, String> nilgst_separate_challan;
	public static volatile SingularAttribute<Stock_Transfer_Header, String> stock_sa_or_ns;

}
