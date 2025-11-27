package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.Alloc_Detail;
import com.excel.model.Alloc_Detail_;
import com.excel.model.Alloc_Header;
import com.excel.model.Alloc_Header_;

@Repository
public class AllocDetailRepositioryImpl implements AllocDetailRepository {
	
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Autowired TransactionalRepository transactionalRepository;

	
	@Override
	public Alloc_Detail getAlloc_DetailObj(Long division_id, String period_code,
			String company, String fin_year,Long  fstaff_id,Long  prod_id,String alloc_type) {
		System.out.println("AllocDetailDao.getAlloc_DetailObj()"+division_id+" "+period_code+" "+company+" "+ fstaff_id+" "+ fin_year+" "+ prod_id);
		EntityManager em = null;
		List<Alloc_Detail> list = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_Detail> criteriaQuery = builder.createQuery(Alloc_Detail.class);
			Root<Alloc_Detail> root = criteriaQuery.from(Alloc_Detail.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get(Alloc_Detail_.allocdtl_div_id), division_id),builder.equal(root.get(Alloc_Detail_.allocdtl_period_code), period_code)
				,builder.equal(root.get(Alloc_Detail_.allocdtl_company), company),builder.equal(root.get(Alloc_Detail_.allocdtl_fstaff_id), fstaff_id)
				,builder.equal(root.get(Alloc_Detail_.allocdtl_fin_year), fin_year),builder.equal(root.get(Alloc_Detail_.allocdtl_prod_id), prod_id)
				,builder.equal(root.get(Alloc_Detail_.allocdtl_alloc_type), alloc_type));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list.size() > 0) {
				return  list.get(0);
			}	
		} finally {
			if (em != null) { em.close(); }
		}
		return null;
	
	}
	
	@Override
	public Long  getCycle(Long division_id, String period_code,String company, String fin_year)throws Exception {
		EntityManager em = null;
		List<Alloc_Detail> list = null;
		Long cycle=null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_Detail> criteriaQuery = builder.createQuery(Alloc_Detail.class);
			Root<Alloc_Detail> root = criteriaQuery.from(Alloc_Detail.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get(Alloc_Detail_.allocdtl_div_id), division_id),builder.equal(root.get(Alloc_Detail_.allocdtl_period_code), period_code)
				,builder.equal(root.get(Alloc_Detail_.allocdtl_company), company),builder.equal(root.get(Alloc_Detail_.allocdtl_fin_year), fin_year));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if (list.size() > 0) {
				cycle= Long.valueOf(list.get(0).getAllocdtl_cycle());
			}
			else {
				cycle=0L;
			}
		}
		catch (Exception e) {
			throw e;
		}finally {
			if (em != null) { em.close(); }
		}
		return cycle;
	}
	
	@Override
	public Long getExistCount(Long division_id, String period_code,String company, String fin_year) {
		EntityManager em = null;
		Long count = 0L;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<Alloc_Detail> root = criteriaQuery.from(Alloc_Detail.class);
			criteriaQuery.select(builder.count(criteriaQuery.from(Alloc_Detail.class)))
			.where(builder.equal(root.get(Alloc_Detail_.allocdtl_div_id), division_id),builder.equal(root.get(Alloc_Detail_.allocdtl_period_code), period_code)
				,builder.equal(root.get(Alloc_Detail_.allocdtl_company), company),builder.equal(root.get(Alloc_Detail_.allocdtl_fin_year), fin_year));
			count = entityManager.createQuery(criteriaQuery).getSingleResult();
			if (count !=null) {
				return count;
			}	
		} 
		catch (Exception e) {
			throw e;
		}finally {
			if (em != null) { em.close(); }
		}
		return 0L;

	}
}
