package com.excel.service;

import java.util.List;

import com.excel.model.Erp_Batch_stk_reco;
import com.excel.model.Erp_Batch_stk_reco_quarantine;

public interface Erp_batchstockreco_report_service {

	String generatebatch_stk_reco_report(List<Erp_Batch_stk_reco>list,String companyname,String empId)throws Exception;

	String generate_stockRecoQuarantine_Report(List<Erp_Batch_stk_reco_quarantine> list, String companyname,String empId)
			throws Exception;
}
