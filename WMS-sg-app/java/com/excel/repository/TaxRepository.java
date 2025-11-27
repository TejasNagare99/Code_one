package com.excel.repository;

import java.util.List;
import java.util.Map;

import com.excel.model.TaxMaster;

public interface TaxRepository {

	Boolean checkUniqueDesc(String stateId, String hsnCode);

	List<Object> getTaxDesc(String taxId, String hsnCode);

	List<TaxMaster> getHsnCodeListByStateId(String stateId);

	TaxMaster getObjectById(Long taxId) throws Exception;

	List<TaxMaster> getAllTaxDetail();

	List<Object> getAll_tax_code(String hsnCode);

	Map<String, List<Object>> getAll_States_And_Tax_code(String hsnCode) throws Exception;

	List<TaxMaster> get_TaxMaster_Data(String hsn_code, Long state_id);

	Boolean hsn_Exist_Or_Not(String hsnCode);

}
