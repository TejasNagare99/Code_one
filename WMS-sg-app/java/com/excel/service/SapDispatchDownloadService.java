package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.Sap_Dispatch_Download;

public interface SapDispatchDownloadService {
	String GeneratesapReport(ReportBean bean,List<Sap_Dispatch_Download> list) throws Exception;
}
