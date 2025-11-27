package com.excel.service;

import java.util.List;

import com.excel.bean.ReportBean;
import com.excel.model.ItemWiseStockLedgerModel;

public interface ItemWise_StockLedger_ReportService {

	String generateItemWise_Stockledger_report(List<ItemWiseStockLedgerModel> lst,ReportBean bean,String companyName,String empId);
}
