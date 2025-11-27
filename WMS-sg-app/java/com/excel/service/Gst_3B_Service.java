package com.excel.service;

import java.io.File;
import java.util.List;

import com.excel.model.GST3B_5;
import com.excel.model.GST3B_Eligible_Itc;
import com.excel.model.GST3B_Supplies_Liable_Bean;
import com.excel.model.GST3B_Supplies_Liable_Bean_2;

public interface Gst_3B_Service {
	
	List<GST3B_Supplies_Liable_Bean> getDataForSuppliesLiable(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception;
	
	List<GST3B_Supplies_Liable_Bean_2> getDataForSuppliesLiableTable2(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception;
	
	List<GST3B_Eligible_Itc> getDataForEligibleItc(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception;
	
	List<GST3B_5> getDataForExemptInwardSupplies(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception;
	
	File generatePdfForDownload(List<GST3B_Supplies_Liable_Bean> sup_liab_tab31,List<GST3B_Supplies_Liable_Bean_2> table_32,
			List<GST3B_Eligible_Itc> eligible_itc_list,List<GST3B_5> exempt_inward,String period_code,String gst_in,String period_name,String path,String erp_fin_year,String depot_name) throws Exception;
	
	String generateJsonForDownload(List<GST3B_Supplies_Liable_Bean> sup_liab_tab31,List<GST3B_Supplies_Liable_Bean_2> table_32,
			List<GST3B_Eligible_Itc> eligible_itc_list,List<GST3B_5> exempt_inward,String period_code,String gst_in,String period_name,String path,String erp_fin_year) throws Exception;

}
