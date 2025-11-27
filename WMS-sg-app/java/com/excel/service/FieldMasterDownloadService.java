package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.FieldMasterDownload;
import com.excel.model.FieldStaffCsvDownload;
import com.excel.model.Fieldstaff_After_mobile_No_Update_CheckList;

public interface FieldMasterDownloadService {

	String GenerateFieldMasterDownloadReport(ReportBean bean,List<FieldMasterDownload> list, String companyname, String username) throws Exception;

	String generateFstaffCsv(List<FieldStaffCsvDownload> list)throws Exception;
	
	String generateUpdatedFstaffMobileNoReport(List<Fieldstaff_After_mobile_No_Update_CheckList> list)throws Exception;

}