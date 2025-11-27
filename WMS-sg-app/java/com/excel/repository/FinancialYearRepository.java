package com.excel.repository;

import java.util.Date;
import java.util.List;

import com.excel.model.FinYear;
import com.excel.model.Period;

public interface FinancialYearRepository {
	
	FinYear getFinancialYearById(Long finYearId) throws Exception;
	List<FinYear> getFinancialYears(String companyCode) throws Exception;
	List<FinYear> getAllFinancialYears(String companyCode) throws Exception;
	Long getFinYearIdByDate(Date date) throws Exception;
	FinYear getCurrentFinancialYear(String companyCode) throws Exception;
	List<FinYear> getcurrentfinyearlist(String companyCode) throws Exception;
	FinYear getCurrentFinancialYearForVet(String companyCode) throws Exception;
	List<FinYear> getFinyearbycurrorprev(String companyCode,String fincurr) throws Exception;
	List<Period> getcurrent_Period(String compcode) throws Exception;
}
