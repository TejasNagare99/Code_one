package com.excel.repository;

import java.util.Date;
import java.util.List;

import com.excel.model.Period;
import com.excel.model.Period_Tds;

public interface PeriodMasterRepository {
	List<Period> getStartDateEndDateByPeriodId(String companyCode, String finYearId, String periodId);
	Period getRunningPeriodByCompany(String companyCode) throws Exception;
	Long getFinYearByStartDate(Date startDate) throws Exception;
	Long getPeriodIdByStartDateAndFinYearId(Date date, Long finYearId) throws Exception;
	List<Period> getPeriodsForTourPlan(Long finYearId, String companyCode) throws Exception;
	Date getMinDateByFinYearId(Long finYearId) throws Exception;
	Date returnTransactionDateByPeriod(Date systemDate, String companyCode) throws Exception;
	Period getPeriodMasterByFinYearIdAndPeriodId(Long finYearId, Long periodId, String companyCode) throws Exception;
	Period getStartDateEndDateByPeriodId(String companyCode, Long periodId, Long finYearId);
	List<Period> getPeriodMasterByFinYearAndMobileLastSyncDate(Long finYearId, String companyCode, Date lastSyncDate);
	Period getPeriodMasterByCompany(String companyCode);
	Period getPeriodByPeriodCode(String periodCode,String finyearId);
	List<Period> getPeriodListGreaterThanPeriodCode(String periodCode);
	Period getPreviousPeriod(String period_code, long year);
	Period getPreviousPeriod();
	Period getObjectById(Long periodId) throws Exception;;
	List<Period> getPeriodListForLastTwoYears(Long period_id, String finYear);
	Period getCurrentPeriod()throws Exception;
	
	Integer getPeriodCount(Date startDate,Date endDate,String year)throws Exception;
	
	List<Period> getallocationPeriod()throws Exception;
	
	Period_Tds getTdsPeriodByDate(Date trandate)throws Exception;
	
	public List<Period> getAllPeriodsByFinYear(String finYear) throws Exception;
	
	public List<Period> getOnlyCurrentPeriodsByFinYear(String finYear) throws Exception;
	
	Period getPeriodData(String finyear, String periodId);
}

