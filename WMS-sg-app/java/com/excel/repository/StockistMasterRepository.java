package com.excel.repository;

import java.util.List;

import com.excel.model.CustomerMaster;
import com.excel.model.Sfa_Customer_Allocation;
import com.excel.model.Sfa_Customer_Master;

public interface StockistMasterRepository {

	CustomerMaster getById(Long custId) throws Exception;

	List<CustomerMaster> getcustNameListByCmpCd(String companyCode) throws Exception;

	Sfa_Customer_Master getCustomerMasterById(Long custId) throws Exception;

	Sfa_Customer_Allocation getCustomerAllocationById(Long custId) throws Exception;
	
	List<CustomerMaster> getCustnamesToCheck() throws Exception;

}
