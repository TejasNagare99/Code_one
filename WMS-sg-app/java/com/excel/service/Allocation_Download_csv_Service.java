package com.excel.service;

import java.util.List;

import com.excel.model.Csv_File_Consolidated_List;
import com.excel.model.Csv_File_List;
import com.excel.model.Csv_File_List_Without_state_name;

public interface Allocation_Download_csv_Service {
	
	String Generate_Allocation_Download_csv(List<Csv_File_List>list,String id,String divid,String filename
			,String empId,String issrt)throws Exception;

	String Generate_Allocation_Consolidated_Download_csv(List<Csv_File_Consolidated_List> list, String id, String divid,
			String filename,String empId) throws Exception;

	String Generate_Allocation_Download_csv_Without_State_Name(
			List<Csv_File_List_Without_state_name> list_Without_state_name, String id, String divid, String filename,
			String empId, String issrt) throws Exception;


}
