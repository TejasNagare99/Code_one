package com.excel.service;

import java.util.Date;
import java.util.List;

import com.excel.model.Period;
import com.excel.model.Period_Tds;

public interface PeriodMasterService {

	Period getPeriodMasterByCompany(String companyCode);
	Period getPeriodByPeriodCode(String periodCode,String finyearId);
	Period getObjectById(Long periodId) throws Exception;
	List<Period> getPeriodListForLastTwoYears(Long priod_id, String finYear);
	Period getCurrentPeriod()throws Exception;
	
	Integer getPeriodCount(Date startDate,Date endDate,String year)throws Exception;
	
	List<Period> getallocationPeriod()throws Exception;
	
	Period_Tds getTdsPeriodByDate(Date trandate)throws Exception;
	List<Period> getStartDateEndDateByPeriodId(String companyCode,String finYearId, String periodcode);
	
	public List<Period> getAllPeriodsByFinYear(String finYear) throws Exception;
	public List<Period> getOnlyCurrentPeriodsByFinYear(String finYear) throws Exception;
	Period getPeriodData(String finyear, String periodId);
}
