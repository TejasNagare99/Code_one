package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.ProductMasterAttrib;

public interface ProductMasterDownloadService {

	String GenerateProductMasterReport(ReportBean bean,List<ProductMasterAttrib> list) throws Exception;
}
