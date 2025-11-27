package com.excel.service;

import java.util.List;

import com.excel.model.Grn_quarantine_prod_summary;
import com.excel.model.GrnquarantineProd_Detail;

public interface Grn_quarantine_reportService {
	String generateGrn_quarantine_report(List<Grn_quarantine_prod_summary> summarylst,List<GrnquarantineProd_Detail> detail_lst,String Startdt,String Enddt,String Companyname,String empId)throws Exception;
}
