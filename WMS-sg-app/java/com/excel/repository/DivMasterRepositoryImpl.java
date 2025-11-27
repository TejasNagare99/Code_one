package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.DivMaster;
import com.excel.model.DivMaster_;
import com.excel.model.MonthList;
import com.excel.model.V_Emp_Div_Access;
import com.excel.model.V_Emp_Div_Access_;

@Repository
public class DivMasterRepositoryImpl implements DivMasterRepository {
	
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Override
	public List<V_Emp_Div_Access> getActiveDivListByEmpId(String emp_id, String comp_cd)throws Exception{
		List<V_Emp_Div_Access> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			
			CriteriaQuery<V_Emp_Div_Access> criteriaQuery = builder.createQuery(V_Emp_Div_Access.class);
			Root<V_Emp_Div_Access> root = criteriaQuery.from(V_Emp_Div_Access.class);
			criteriaQuery.select(root).where(
					builder.and(
							builder.equal(root.get(V_Emp_Div_Access_.empId), emp_id),
							builder.equal(root.get(V_Emp_Div_Access_.div_status), "A")
							)
					).orderBy(builder.asc(root.get(V_Emp_Div_Access_.div_disp_nm)));
			list = em.createQuery(criteriaQuery).getResultList();

			return list;
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	@Override
	public List<V_Emp_Div_Access> getDivListByEmpId(String emp_id, String comp_cd)throws Exception{
		List<V_Emp_Div_Access> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			
			CriteriaQuery<V_Emp_Div_Access> criteriaQuery = builder.createQuery(V_Emp_Div_Access.class);
			Root<V_Emp_Div_Access> root = criteriaQuery.from(V_Emp_Div_Access.class);
			criteriaQuery.select(root).where(
					builder.and(
							builder.equal(root.get(V_Emp_Div_Access_.empId), emp_id)
							)
					).orderBy(builder.asc(root.get(V_Emp_Div_Access_.div_disp_nm)));
			list = em.createQuery(criteriaQuery).getResultList();

			return list;
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MonthList> getMonthList(String companyCode,String year) throws Exception {
		EntityManager em = null;
		List<MonthList> list= null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" PERIOD_status = 'A' and PERIOD_COMPANY = '"+companyCode+"' ");
			sb.append(" and PERIOD_FIN_YEAR= '"+year+"' ");
			sb.append(" and convert(date,'01 ' + PERIOD_NAME ) >=convert(date,dateadd(mm,datediff(mm,0,getutcdate()),0))  ");
			System.out.println("Sb "+sb.toString());
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callMonthList");
			query.setParameter("vstablename", "PERIOD");
			query.setParameter("vsfieldlist", "PERIOD_ID, PERIOD_NAME");
			query.setParameter("vssearch", sb.toString());
			query.setParameter("vsorderby", " PERIOD_NAME asc ");
			list = query.getResultList();
			
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MonthList> getMonthListAutoDispatch(String companyCode,String year) throws Exception {
		EntityManager em = null;
		List<MonthList> list= null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select top 5 period_id,period_name  ");
			sb.append("from period ");
			sb.append("where period_id >= (select period_id from period where period_current='Y' and period_status='A') ");
			sb.append("order by period_id ");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				MonthList object = null;
				for (Tuple t : tuples) {
					object = new MonthList();
					object.setPeriod_id((Long.valueOf(t.get("period_id", Short.class))));
					object.setPeriod_name(t.get("period_name", String.class));
					list.add(object);
				}
				System.out.println("getTeamWithCount"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
			
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public Long getDivIDbyCode(String div_code_nm) {
		Long divId = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<DivMaster> root = criteriaQuery.from(DivMaster.class);
			criteriaQuery.multiselect(root.get(DivMaster_.div_id)).where(
					builder.and(builder.equal(root.get(DivMaster_.div_code), div_code_nm))
					);
			divId = em.createQuery(criteriaQuery).getSingleResult();

			return divId;
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public List<DivMaster> getEntireDivMasterList() throws Exception {
		List<DivMaster> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			
			CriteriaQuery<DivMaster> criteriaQuery = builder.createQuery(DivMaster.class);
			Root<DivMaster> root = criteriaQuery.from(DivMaster.class);
			criteriaQuery.select(root).orderBy(builder.asc(root.get(DivMaster_.div_disp_nm)));
			list = em.createQuery(criteriaQuery).getResultList();

			return list;
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public DivMaster getById(Long divId) throws Exception {
		return this.entityManager.find(DivMaster.class, divId);
	}

	
}
