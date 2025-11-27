package com.excel.repository;

import java.util.List;

import com.excel.model.CustomerMaster;
import com.excel.model.FieldStaff;

public interface CustomerMasterRepo {

	public List<CustomerMaster> getCustomersForLocation(Long loc_id) throws Exception;

	public CustomerMaster getCustIdByCustCd(String custCd) throws Exception;

	public CustomerMaster getErpCustCdByCustCdAndLocation(String custCd, Long locId) throws Exception;

	List<CustomerMaster> getCustomersForLocationNotInCustLock(Long loc_id, String filter) throws Exception;

	CustomerMaster getCustomerById(Long cust_id) throws Exception;

	List<FieldStaff> getCustomersForLocationAsFieldStaff(Long loc_id) throws Exception;

	public List<CustomerMaster> getCustomersByShipToCodeAndNameNotInCustLock(boolean isAllLoc, Long loc_id,
			String filter, String company_cd) throws Exception;
}
