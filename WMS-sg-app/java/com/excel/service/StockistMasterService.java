package com.excel.service;

import java.util.List;

import com.excel.bean.StockistMasterBean;
import com.excel.model.CustomerMaster;

public interface StockistMasterService {
	
	void save(StockistMasterBean bean) throws Exception;
	
	CustomerMaster getById(Long custId) throws Exception;
	List<CustomerMaster> getcustNameListByCmpCd(String companyCode) throws Exception;
	List<CustomerMaster> getCustnamesToCheck() throws Exception;

}
