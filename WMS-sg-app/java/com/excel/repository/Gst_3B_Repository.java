package com.excel.repository;

import java.util.List;

import com.excel.model.GST3B_5;
import com.excel.model.GST3B_Eligible_Itc;
import com.excel.model.GST3B_Supplies_Liable_Bean;
import com.excel.model.GST3B_Supplies_Liable_Bean_2;

public interface Gst_3B_Repository {
	
	List<GST3B_Supplies_Liable_Bean> getDataForSuppliesLiable(String fin_year_flag,String fin_id,String comp_cd,String st_dt,String end_dt,String loc_id)
			throws Exception;
	
	List<GST3B_Supplies_Liable_Bean_2> getDataForSuppliesLiableTable2(String fin_year_flag,String fin_id,String comp_cd,String st_dt,String end_dt,String loc_id)
			throws Exception;
	
	List<GST3B_Eligible_Itc> getDataForEligibleItc(String fin_year_flag,String fin_id,String comp_cd,String st_dt,String end_dt,String loc_id)
			throws Exception;
	
	List<GST3B_5> getDataForExemptInwardSupplies(String fin_year_flag,String fin_id,String comp_cd,String st_dt,String end_dt,String loc_id)
			throws Exception;

}
