package com.excel.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.exception.MedicoException;
import com.excel.model.Period;
import com.excel.model.Period_;
import com.excel.model.Period_Tds;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@Repository
public class PeriodMasterRepositoryImpl implements PeriodMasterRepository, MedicoConstants
{
	
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public Period getPeriodMasterByCompany(String companyCode) {
		EntityManager em = null;
		List<Period> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.select(root).where(
			builder.and(
					builder.equal(root.get(Period_.period_current), Y),
					builder.equal(root.get(Period_.period_status), A),
					builder.equal(root.get(Period_.period_company), companyCode)
					)
			).orderBy(builder.asc(root.get(Period_.period_id)));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Period getRunningPeriodByCompany(String companyCode) {
		EntityManager em = null;
		Period p = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select pm.start_date as start_date, pm.end_date as end_date ,pm.period_id as period_id, ");
			sb.append(" pm.fin_year_id as fin_year_id, pm.period_name as period_name ");
			sb.append(" from Period as pm, FinancialYear as fy where pm.fin_year_id = fy.fin_year_id ");
			sb.append(" and pm.period_closed ='N' and fy.fin_year_closed='N' and pm.company_cd = :comp_cd1 ");
			sb.append(" and pm.period_id IN (SELECT MIN(pm.period_id) from Period as pm, FinancialYear as fy ");
			sb.append(" where pm.fin_year_id = fy.fin_year_id and pm.company_cd = :comp_cd2 ");
			sb.append(" and  fy.fin_year_closed='N' and pm.period_closed ='N') ");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("comp_cd1", companyCode);
			query.setParameter("comp_cd2", companyCode);
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {
				p = new Period();
				for(Tuple t : tuples) {
					//p.setStart_date(t.get("start_date", Date.class));
					//p.setEnd_date(t.get("end_date", Date.class));
					//p.setPeriod_id(t.get("period_id", Long.class));
					//p.setFin_year_id(t.get("fin_year_id", Long.class));
					p.setPeriod_name(t.get("period_name", String.class));
				}
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return p;
	}

	@Override
	public Long getFinYearByStartDate(Date startDate) throws Exception {
		EntityManager em = null;
		Long finYearId = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.multiselect(
					//root.get(Period_.fin_year_id)
					).where(
							builder.and(
									//builder.lessThanOrEqualTo(root.get(Period_.start_date), startDate),
									//builder.greaterThanOrEqualTo(root.get(Period_.end_date), startDate)
									)
							);
			List<Long> list = em.createQuery(criteriaQuery).getResultList();
			if (list != null && !list.isEmpty()) {
				finYearId = list.get(0);
			}
		} finally {
			if(em != null) { em.close(); }
		}
		if (finYearId == null) {
			throw new MedicoException("Period Master Not Found!");
		}
		return finYearId;
	}

	@Override
	public Long getPeriodIdByStartDateAndFinYearId(Date date, Long finYearId) throws Exception {
		EntityManager em = null;
		Long periodId = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.multiselect(
					root.get(Period_.period_id)
					).where(
							builder.and(
									//builder.lessThanOrEqualTo(root.get(Period_.start_date), date),
									//builder.greaterThanOrEqualTo(root.get(Period_.end_date), date),
									//builder.equal(root.get(Period_.fin_year_id), finYearId)
									)
							);
			periodId = em.createQuery(criteriaQuery).getSingleResult();
		} finally {
			if(em != null) { em.close(); }
		}
		return periodId;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Period> getPeriodsForTourPlan(Long finYearId, String companyCode) throws Exception {
		EntityManager em = null;
		List<Period> list = new ArrayList<>();
		Period periodMaster = null;		
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" p.period_id as period_id, p.period_name as period_name FROM Period p WHERE FIN_YEAR_ID = (SELECT FIN_YEAR_ID ");
			sb.append(" FROM FinancialYear WHERE START_DT<=ADD_MONTHS(SYSDATE,1) ");
			sb.append(" AND END_DT >= ADD_MONTHS(SYSDATE,1)) AND START_DATE<=ADD_MONTHS(SYSDATE,1) ");
			sb.append(" AND END_DATE >= ADD_MONTHS(SYSDATE,1) OR FIN_YEAR_ID = : finYearId AND COMPANY_CD = :companyCode ");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("finYearId", finYearId);
			query.setParameter("companyCode", companyCode);
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {
				periodMaster = new Period();
				for(Tuple t : tuples) {
					periodMaster.setPeriod_id(t.get("period_id", Long.class));
					periodMaster.setPeriod_name(t.get("period_name", String.class));
					list.add(periodMaster);
				}
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Date getMinDateByFinYearId(Long finYearId) throws Exception {
		EntityManager em = null;
		Date date = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT MIN(p.start_date) AS date FROM Period AS p WHERE p.fin_year_id = :fin_year_id");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("fin_year_id", finYearId);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				date = tuples.get(0).get("date", Date.class);
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return date;
	}

	@Override
	public Date returnTransactionDateByPeriod(Date systemDate, String companyCode) throws Exception {
		Period currentPeriod = getRunningPeriodByCompany(companyCode);
		/*
		 * if (currentPeriod.getEnd_date().compareTo(systemDate) <= 0) { return
		 * currentPeriod.getEnd_date(); } else { return systemDate; }
		 */
		return null;
	}

	@Override
	public Period getPeriodMasterByFinYearIdAndPeriodId(Long finYearId, Long periodId, String companyCode) throws Exception {
		EntityManager em = null;
		Period periodMaster = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.select(root).where(
			builder.and(
					//builder.equal(root.get(Period_.fin_year_id), finYearId),
					builder.equal(root.get(Period_.period_id), periodId)
					//builder.equal(root.get(Period_.company_cd), companyCode)
					)
			).orderBy(builder.asc(root.get(Period_.period_id)));
			List<Period> list = em.createQuery(criteriaQuery).getResultList();
			if (list != null && !list.isEmpty()) {
				periodMaster = list.get(0);
				//periodMaster.setStart_date_time(periodMaster.getStart_date().getTime());
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return periodMaster;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Period getStartDateEndDateByPeriodId(String companyCode, Long periodId, Long finYearId) {
		EntityManager em = null;
		Period p = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select pm.start_date as start_date, pm.end_date as end_date ,pm.period_id as period_id, ");
			sb.append(" pm.fin_year_id as fin_year_id, pm.period_name as period_name ");
			sb.append(" from Period as pm, FinancialYear as fy ");
			sb.append(" where pm.fin_year_id = fy.fin_year_id and pm.company_cd = :companyCode and pm.fin_year_id = :finId and pm.period_id = :periodId ");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("companyCode", companyCode);
			query.setParameter("finId", finYearId);
			query.setParameter("periodId", periodId);
			List<Tuple> tuples = query.getResultList();
			if(tuples != null && !tuples.isEmpty()) {
				p = new Period();
				for(Tuple t : tuples) {
					//p.setStart_date(t.get("start_date", Date.class));
					//p.setEnd_date(t.get("end_date", Date.class));
				}
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return p;
	}
	
	@Override
	public List<Period> getPeriodMasterByFinYearAndMobileLastSyncDate(Long finYearId, String companyCode, Date lastSyncDate) {
		EntityManager em = null;
		List<Period> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.select(root).where(
			builder.and(
					//builder.equal(root.get(Period_.fin_year_id), finYearId),
					//builder.equal(root.get(Period_.company_cd), companyCode),
					//builder.equal(root.get(Period_.last_mode_dt), lastSyncDate)
					)
			).orderBy(builder.asc(root.get(Period_.period_id)));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public Period getPeriodByPeriodCode(String periodCode,String period_fin_year) {
		EntityManager em = null;
		Period period = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.select(root).where(
			builder.and(
					builder.equal(root.get(Period_.period_code),periodCode),
					builder.equal(root.get(Period_.period_fin_year),period_fin_year),
					builder.equal(root.get(Period_.period_status), A)
					)
			).orderBy(builder.asc(root.get(Period_.period_id)));
			period = em.createQuery(criteriaQuery).getResultList().get(0);
		} finally {
			if(em != null) { em.close(); }
		}
		if(period != null) {
			return period;
		}
		return null;
	}
	
	@Override
	public List<Period> getPeriodListGreaterThanPeriodCode(String periodCode) {
		EntityManager em = null;
		List<Period> period = null;
		System.out.println("periodCode "+periodCode);
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.multiselect(root.get(Period_.period_code),root.get(Period_.period_name))
			.where(
			builder.and(
					builder.in(root.get(Period_.period_id)).value(Long.valueOf(periodCode)).value(Long.valueOf(periodCode)+1).value(Long.valueOf(periodCode)+2)
					)
			).orderBy(builder.asc(root.get(Period_.period_code)));
			period = em.createQuery(criteriaQuery).getResultList();
			System.out.println("size periodList "+period.size());
		} finally {
			if(em != null) { em.close(); }
		}
		if(period != null) {
			return period;
		}
		return null;
	}

	@Override
	public Period getPreviousPeriod(String period_code,long year){
		EntityManager em = null;
		List<Period> list = new ArrayList<>();
		Period periodMaster = null;		
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" select  period_code,period_id,period_fin_year from period where period_id=(select max(period_id) from period where");
			sb.append(" period_id<(select period_id from period where");
			sb.append(" period_code='").append(period_code);
			sb.append("' and period_fin_year=").append(year);
			sb.append(") )");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					periodMaster = new Period();
					periodMaster.setPeriod_code(t.get("period_code", String.class));
					periodMaster.setPeriod_id(Long.valueOf(t.get("period_id", Short.class)));
					periodMaster.setPeriod_fin_year(t.get("period_fin_year", String.class));
				}
			}
		} finally {
			if(em != null) { em.close(); }
		}
		return periodMaster;
	 }
	
	@Override
	 public Period getPreviousPeriod(){
		EntityManager em = null;
		Period periodMaster = null;		
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" select period_code,period_id,period_fin_year from period where period_id=(select max(period_id) from period where");
			sb.append(" period_id<(select period_id from period where");
			sb.append(" period_current='Y' ) ) ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					periodMaster = new Period();
					periodMaster.setPeriod_code(t.get("period_code", String.class));
					periodMaster.setPeriod_id(Long.valueOf(t.get("period_id", Short.class)));
					periodMaster.setPeriod_fin_year(t.get("period_fin_year", String.class));
				}
			if(!tuples.isEmpty() && tuples.size() > 0)
				return periodMaster;
	
			}
		
		} finally {
			if(em != null) { em.close(); }
		}
		return null;
	 
	 }
	 
	 @Override
	 public Period getObjectById(Long periodId) throws Exception {
		 return this.entityManager.find(Period.class, periodId);
	 }
	 
	@Override
	public List<Period> getPeriodListForLastTwoYears(Long period_id,String finYear) {
		EntityManager em = null;
		List<Period> period = null;
		System.out.println("finYear "+finYear);
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.multiselect(root.get(Period_.period_fin_year),root.get(Period_.period_code),root.get(Period_.period_name),root.get(Period_.period_id)).where(
//			builder.and(
//					builder.in(root.get(Period_.period_fin_year)).value(finYear).value(String.valueOf(Long.valueOf(finYear)-1))
//					) 
			builder.lessThanOrEqualTo(root.get(Period_.period_id), period_id)
			).orderBy(builder.desc(root.get(Period_.period_id)));
			period = em.createQuery(criteriaQuery).getResultList();
			System.out.println("size periodList "+period.size());
		} finally {
			if(em != null) { em.close(); }
		}
		if(period != null) {
			return period;
		}
		return null;
	}
	
	@Override
	public List<Period> getAllPeriodsByFinYear(String finYear) throws Exception{
		EntityManager em = null;
		List<Period> period = null;
		System.out.println("finYear "+finYear);
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.multiselect(root.get(Period_.period_fin_year),root.get(Period_.period_code),root.get(Period_.period_name),root.get(Period_.period_id),root.get(Period_.period_alt_name)).where(
			builder.equal(root.get(Period_.period_fin_year), finYear)
			).orderBy(builder.desc(root.get(Period_.period_id)));
			period = em.createQuery(criteriaQuery).getResultList();
			System.out.println("size periodList "+period.size());
		} finally {
			if(em != null) { em.close(); }
		}
		if(period != null) {
			return period;
		}
		return null;
	}
	
	

	@Override
	public Period getCurrentPeriod() throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<Period> period = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.select(root).where(
					builder.and(
							builder.equal(root.get(Period_.period_current),Y),
							builder.equal(root.get(Period_.period_status), A)
							));
			period = em.createQuery(criteriaQuery).getResultList();
			System.out.println("size periodList "+period.size());
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return period.get(0);
	}

	@Override
	public Integer getPeriodCount(Date startDate, Date endDate,String year) throws Exception {
		// TODO Auto-generated method stub
		Integer count =0;
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" select count(period_id) cnt from period where PERIOD_FIN_YEAR = :year");
			sb.append(" and convert( date , PERIOD_START_DATE ) >= :startdate");
			sb.append(" and convert( date , PERIOD_start_DATE ) <= :enddate ");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("startdate", MedicoResources.convertUtilDateToString(startDate));
			query.setParameter("enddate", MedicoResources.convertUtilDateToString(endDate));
			query.setParameter("year", year);
			count = (Integer) query.getSingleResult();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return count;
	}

	@Override
	public List<Period> getallocationPeriod() throws Exception {
		// TODO Auto-generated method stub
		List<Period> list = null;
		Period p = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT PERIOD_ID,UPPER(RTRIM(PERIOD_NAME)) PERIOD_NAME,PERIOD_CODE FROM V_ALLOCATION_PERIOD");
			Query query = entityManager.createNativeQuery(sb.toString(),Tuple.class);
			
			List<Tuple> tuple = query.getResultList();
			if(tuple!=null && tuple.size()>0) {
				list = new ArrayList<Period>();
				for(Tuple t : tuple) {
					p = new Period();
					p.setPeriod_id(Long.valueOf(t.get("PERIOD_ID",Short.class).toString()));
					p.setPeriod_name(t.get("PERIOD_NAME",String.class));
					p.setPeriod_code(t.get("PERIOD_CODE",String.class));
					list.add(p);
				}
				
				return list;
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public Period_Tds getTdsPeriodByDate(Date trandate) throws Exception {
		// TODO Auto-generated method stub
		List<Period_Tds> tdsperiod =null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from Period_Tds where convert( date , period_start_date ) <= :date"); 
			sb.append(" and convert( date , period_end_date ) >= :date");
			Query query = entityManager.createQuery(sb.toString(),Period_Tds.class);
			query.setParameter("date", trandate);
			tdsperiod=query.getResultList();
			
			if(tdsperiod!=null && tdsperiod.size()==1) {
				return tdsperiod.get(0);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<Period> getStartDateEndDateByPeriodId(String companyCode, String finYearId, String period_code) {
		EntityManager em = null;
		List<Period> list=new ArrayList<Period>();
//		System.out.println("DAOperiodcdoe------>"+period_code);
//		System.out.println("DAOfinYearId------>"+finYearId);
//		System.out.println("DAOcompanyCode------>"+companyCode);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select pm.period_start_date as period_start_date, pm.period_end_date as period_end_date ,pm.period_code as period_code, ");
			sb.append(" pm.period_fin_year as period_fin_year");
			sb.append(" from Period as pm, FinYear as fy ");
			sb.append(" where pm.period_fin_year = fy.fin_year and pm.period_company = :companyCode and pm.period_fin_year = :finId and pm.period_code = :period_code ");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("companyCode", companyCode);
			query.setParameter("finId", finYearId);
			query.setParameter("period_code", period_code);
			List<Tuple> tuples = query.getResultList();
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			if(tuples != null && !tuples.isEmpty()) {
				Period p=new Period();
				for(Tuple t : tuples) {
					p.setPeriod_start_date(t.get("period_start_date",String.class));
					p.setPeriod_end_date(t.get("period_end_date",String.class));
					list.add(p);
				}

			}
		} finally {
			if(em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public Period getPeriodData(String finyear, String periodId) {
		EntityManager em = null;
		List<Period> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.select(root).where(
			builder.and(
					builder.equal(root.get(Period_.period_fin_year), finyear),
					builder.equal(root.get(Period_.period_code),periodId)
					)
			).orderBy(builder.asc(root.get(Period_.period_id)));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Period> getOnlyCurrentPeriodsByFinYear(String finYear) throws Exception {
		EntityManager em = null;
		List<Period> period = null;
		System.out.println("finYear "+finYear);
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Period> criteriaQuery = builder.createQuery(Period.class);
			Root<Period> root = criteriaQuery.from(Period.class);
			criteriaQuery.multiselect(root.get(Period_.period_fin_year),root.get(Period_.period_code),root.get(Period_.period_name),root.get(Period_.period_id),root.get(Period_.period_alt_name)).where(
			builder.and(builder.equal(root.get(Period_.period_fin_year), finYear),
					builder.notEqual(root.get(Period_.period_current), ""))
			).orderBy(builder.desc(root.get(Period_.period_id)));
			period = em.createQuery(criteriaQuery).getResultList();
			System.out.println("size periodList "+period.size());
		} finally {
			if(em != null) { em.close(); }
		}
		if(period != null) {
			return period;
		}
		return null;
	}
}
