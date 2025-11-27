package com.excel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.FinYear;
import com.excel.model.Period;
import com.excel.repository.FinancialYearRepository;
import com.excel.utility.MedicoConstants;

@Service
public class FinancialYearServiceImpl implements FinancialYearService ,MedicoConstants {
	
	@Autowired FinancialYearRepository financialYearRepository;

	@Override
	public FinYear getFinancialYearById(Long finYearId) throws Exception {
		return financialYearRepository.getFinancialYearById(finYearId);
	}

	@Override
	public List<FinYear> getFinancialYears(String companyCode) throws Exception {
		return financialYearRepository.getFinancialYears(companyCode);
	}

	@Override
	public List<FinYear> getAllFinancialYears(String companyCode) throws Exception {
		return financialYearRepository.getAllFinancialYears(companyCode);
	}

	@Override
	public Long getFinYearIdByDate(Date date) throws Exception {
		return financialYearRepository.getFinYearIdByDate(date);
	}

	@Override
	public FinYear getCurrentFinancialYear(String companyCode) throws Exception {
		return financialYearRepository.getCurrentFinancialYear(companyCode);
	}
	
	
	@Override
	public List<FinYear> getcurrentfinyearlist(String companyCode) throws Exception {
		// TODO Auto-generated method stub
		return financialYearRepository.getcurrentfinyearlist(companyCode);
	}
	
	@Override
	public FinYear getFinYearForTDSVet(String companyCode) throws Exception {
		return financialYearRepository.getCurrentFinancialYearForVet(companyCode);
	}
	
	@Override
	public List<FinYear> getFinyearbycurrorprev(String companyCode, String fincurr) throws Exception {
		// TODO Auto-generated method stub
		return financialYearRepository.getFinyearbycurrorprev(companyCode, fincurr);
	}


	@Override
	public	List<Period> getcurrent_Period(String compcode) throws Exception {
		// TODO Auto-generated method stub
		return financialYearRepository.getcurrent_Period(compcode);
	}
}
