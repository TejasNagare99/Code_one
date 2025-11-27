package com.excel.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.excel.model.Period;
import com.excel.model.Period_Tds;
import com.excel.repository.PeriodMasterRepository;

@Service
public class PeriodMasterServiceImpl implements PeriodMasterService{
	
	@Autowired PeriodMasterRepository periodMasterRepository;

	
	
	@Override
	public List<Period> getStartDateEndDateByPeriodId(String companyCode, String finYearId, String periodId) {
		// TODO Auto-generated method stub
		return periodMasterRepository.getStartDateEndDateByPeriodId(companyCode,finYearId, periodId);
	}
	
	@Override
	public Period getPeriodMasterByCompany(String companyCode) {
		return this.periodMasterRepository.getPeriodMasterByCompany(companyCode);
	}
	
	@Override
	public Period getPeriodByPeriodCode(String periodCode,String finyearId) {
		return periodMasterRepository.getPeriodByPeriodCode(periodCode,finyearId);
	}

	@Override
	public Period getObjectById(Long periodId) throws Exception {
		// TODO Auto-generated method stub
		return periodMasterRepository.getObjectById(periodId);
	}

	@Override
	public List<Period> getPeriodListForLastTwoYears(Long priod_id,String finYear) {
		// TODO Auto-generated method stub
		return periodMasterRepository.getPeriodListForLastTwoYears(priod_id,finYear);
	}

	@Override
	public Period getCurrentPeriod() throws Exception {
		// TODO Auto-generated method stub
		return periodMasterRepository.getCurrentPeriod();
	}

	@Override
	public Integer getPeriodCount(Date startDate, Date endDate,String year) throws Exception {
		// TODO Auto-generated method stub
		return periodMasterRepository.getPeriodCount(startDate, endDate,year);
	}

	@Override
	public List<Period> getallocationPeriod() throws Exception {
		// TODO Auto-generated method stub
		return periodMasterRepository.getallocationPeriod();
	}

	@Override
	public Period_Tds getTdsPeriodByDate(Date trandate) throws Exception {
		// TODO Auto-generated method stub
		return periodMasterRepository.getTdsPeriodByDate(trandate);
	}

	@Override
	@Cacheable(value="AllPeriodsByFinYear",key="#finYear")
	public List<Period> getAllPeriodsByFinYear(String finYear) throws Exception {
		return periodMasterRepository.getAllPeriodsByFinYear(finYear);
	}
	
	@Override
	public Period getPeriodData(String finyear, String periodId) {
		return periodMasterRepository.getPeriodData(finyear,periodId);
	}

	@Override
	public List<Period> getOnlyCurrentPeriodsByFinYear(String finYear) throws Exception {
		return periodMasterRepository.getOnlyCurrentPeriodsByFinYear(finYear);
	}
	

}
