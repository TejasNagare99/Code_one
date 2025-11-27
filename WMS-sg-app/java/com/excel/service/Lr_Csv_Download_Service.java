package com.excel.service;

import java.util.List;

import com.excel.model.LrcsvDownload;
import com.excel.model.LrcsvDownloadReport;
import com.excel.model.LrcsvDownloadReportRevised;
import com.excel.model.Lrcsv_RevisedDownload;
import com.excel.model.Lrcsv_RevisedDownload_SG;

public interface Lr_Csv_Download_Service {
	
	String Generate_Lr_CSV(List<LrcsvDownload> lst)throws Exception;
	String generateLRcsvReport(List<LrcsvDownloadReport> lst, String strdate, String enddate, String tbl_ind, String cfa_id,
		    String emp_id, String fs_type,String compcode)throws Exception;

	String generateLR_Revised_csvReport(List<LrcsvDownloadReportRevised> lst, String strdate, String enddate, String tbl_ind, String cfa_id,
		    String emp_id, String fs_type,String compcode,String role,String username,String transprt,String empId)throws Exception;

	String Generate_Lr_Revised_CSV(List<Lrcsv_RevisedDownload> lst,String username,String role,
			String indicator,String compcd,String empId)throws Exception;

	String Generate_Lr_Revised_CSV_SG(List<Lrcsv_RevisedDownload_SG> list, String username, String role, String string,
			String compcd, String empIdd) throws Exception;
}
