package com.excel.service;

import java.util.List;
import java.util.Map;

import com.excel.bean.TaxBean;
import com.excel.model.TaxMaster;

public interface TaxService {
	
	
	public void saveTax(List<Integer> taxIdList, TaxBean tb, List<Integer> stateIdList);

	Boolean checkUniqueDesc(String stateId, String hsnCode);

	List<Object> getTaxDesc(String taxId, String hsnCode);

	public List<TaxMaster> getHsnCodeListByStateId(String stateId);

	public List<TaxMaster> getAllTaxDetail();

	void updateTax(List<Integer> taxIdList, TaxBean tb, List<Integer> stateIdList);

	Map<String, List<Object>> getAll_States_And_Tax_code(String string) throws Exception;

	public List<Object> getAll_tax_code(String hsnCode);

	public Boolean hsn_Exist(String hsnCode);

}
