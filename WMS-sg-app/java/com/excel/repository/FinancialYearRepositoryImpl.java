package com.excel.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.exception.MedicoException;
import com.excel.model.FinYear;
import com.excel.model.FinYear_;
import com.excel.model.Period;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class FinancialYearRepositoryImpl implements FinancialYearRepository, MedicoConstants {

	@Autowired
	private EntityManagerFactory emf;

	@Override
	public List<FinYear> getFinancialYears(String companyCode) throws Exception {
		EntityManager em = null;
		List<FinYear> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<FinYear> query = builder.createQuery(FinYear.class);
			Root<FinYear> root = query.from(FinYear.class);
			query.select(root)
					.where(builder.and(builder.equal(root.get(FinYear_.fin_current), Y),
							builder.equal(root.get(FinYear_.fin_company), companyCode)))
					.orderBy(builder.desc(root.get(FinYear_.fin_year)));
			list = em.createQuery(query).getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<FinYear> getAllFinancialYears(String companyCode) throws Exception {
		EntityManager em = null;
		List<FinYear> list = null;
		try {
			System.out.println("companyCode:::" + companyCode);
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<FinYear> query = builder.createQuery(FinYear.class);
			Root<FinYear> root = query.from(FinYear.class);
			query.select(root).where(builder.equal(root.get(FinYear_.fin_company), companyCode))
					.orderBy(builder.desc(root.get(FinYear_.fin_year)));
			list = em.createQuery(query).getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public FinYear getFinancialYearById(Long finYearId) throws Exception {
		FinYear f = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			f = em.find(FinYear.class, finYearId);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return f;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getFinYearIdByDate(Date date) throws Exception {
		EntityManager em = null;
		Long finYearId = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT f.fin_year AS fin_year_id FROM FinYear AS f ");
			sb.append(" WHERE f.fin_start_date <= :date AND f.fin_end_date >= :date ");
			Query query = em.createQuery(sb.toString(), Tuple.class);
			query.setParameter("date", date);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				Tuple t = tuples.get(0);
				finYearId = Long.parseLong(String.valueOf(t.get("fin_year_id")));
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (finYearId == null) {
			throw new MedicoException("Invalid Date");
		}
		return finYearId;
	}

	@Override
	public FinYear getCurrentFinancialYear(String companyCode) throws Exception {
		EntityManager em = null;
		FinYear financialYear = null;
		try {
			em = this.emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<FinYear> criteriaQuery = builder.createQuery(FinYear.class);
			Root<FinYear> root = criteriaQuery.from(FinYear.class);
			criteriaQuery.select(root).where(builder.and(builder.equal(root.get(FinYear_.fin_company), companyCode),
					builder.equal(root.get(FinYear_.fin_current), Y)));
			List<FinYear> list = em.createQuery(criteriaQuery).getResultList();
			if (list != null && !list.isEmpty()) {
				financialYear = list.get(0);
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return financialYear;
	}

	@Override
	public List<FinYear> getcurrentfinyearlist(String companyCode) throws Exception {
		EntityManager em = null;
		List<FinYear> financialYear = null;
		try {
			em = this.emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<FinYear> criteriaQuery = builder.createQuery(FinYear.class);
			Root<FinYear> root = criteriaQuery.from(FinYear.class);
			criteriaQuery.select(root).where(builder.and(builder.equal(root.get(FinYear_.fin_company), companyCode),
					builder.equal(root.get(FinYear_.fin_current), Y)));
			financialYear = em.createQuery(criteriaQuery).getResultList();

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return financialYear;
	}

	@Override
	public List<FinYear> getFinyearbycurrorprev(String companyCode, String fincurr) throws Exception {
		EntityManager em = null;
		List<FinYear> financialYear = new ArrayList<>();
		FinYear finyear = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select fin_year,fin_start_date,fin_end_date from finyear where fin_current =:fincurr and fin_company=:compcode order by fin_year desc");

			em = this.emf.createEntityManager();

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("fincurr", fincurr);
			query.setParameter("compcode", companyCode);

			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					finyear = new FinYear();

					finyear.setFin_year(t.get("fin_year", String.class));
					finyear.setFin_start_date(t.get("fin_start_date", Date.class));
					finyear.setFin_end_date(t.get("fin_end_date", Date.class));

					financialYear.add(finyear);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return financialYear;
	}
	@Override
	public FinYear getCurrentFinancialYearForVet(String companyCode) throws Exception {
		FinYear financialYear1 = null;
		List<FinYear> financialYear = new ArrayList<>();
		EntityManager em = null;

		FinYear finyear = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select fin_year,fin_start_date,fin_end_date from FINYEAR_TDS where fin_current =:fincurr and fin_company=:compcode ");

			em = this.emf.createEntityManager();

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("fincurr", 'Y');
			query.setParameter("compcode", companyCode);
			List<Tuple> tuples = query.getResultList();
			System.out.println();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					finyear = new FinYear();

					finyear.setFin_year(t.get("fin_year", String.class));
					finyear.setFin_start_date(t.get("fin_start_date", Date.class));
					finyear.setFin_end_date(t.get("fin_end_date", Date.class));

					financialYear.add(finyear);
				}

			}
			financialYear1 = financialYear.get(0);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return financialYear1;

	}
	
	@Override
	public List<Period> getcurrent_Period(String companyCode) throws Exception {
		EntityManager em = null;
		List<Period> period_list = new ArrayList<>();
		Period period = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select PERIOD_START_DATE,PERIOD_END_DATE from PERIOD where  PERIOD_CURRENT='Y' and PERIOD_COMPANY=:companyCode");

			em = this.emf.createEntityManager();

			Query query = em.createNativeQuery(sb.toString(), Tuple.class);

			query.setParameter("companyCode", companyCode);

			List<Tuple> tuples = query.getResultList();

			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					period = new Period();

					
					period.setPeriod_start_date((t.get("PERIOD_START_DATE", Date.class).toString()))  ;
					period.setPeriod_end_date((t.get("PERIOD_END_DATE", Date.class).toString()));
					
					period_list.add(period);
	
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}

		return period_list;
	}

}
