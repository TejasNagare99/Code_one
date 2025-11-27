package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.ProdLock;
import com.excel.model.ProdLock_;

@Repository
public class ProdLockRepoitoryImpl implements ProdLockRepoitory{
	@Autowired EntityManager entityManager;
	@Autowired EntityManagerFactory emf;
	
	@Override
	public boolean getProdLockByProdIdLocIdUserId(Long prodId, Long locId) throws Exception {
		EntityManager em = null;
		List<ProdLock> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ProdLock> criteriaQuery = builder.createQuery(ProdLock.class);
			Root<ProdLock> root = criteriaQuery.from(ProdLock.class);
			criteriaQuery.select(root).where(
				builder.and(
						builder.equal(root.get(ProdLock_.prod_id), prodId),
						builder.equal(root.get(ProdLock_.loc_id), locId)
						)
				);
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty() && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteByProdIdLocIdUserId(Long prodId, Long locId, String userId) throws Exception {
		int i = this.entityManager.createQuery(" DELETE FROM ProdLock AS p WHERE p.prod_id = :prodId AND p.loc_id= :locId AND user_id=:userId ")
		.setParameter("prodId", prodId)
		.setParameter("locId", locId)
		.setParameter("userId", userId)
		.executeUpdate();
		if(i>0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteProdLockByUserId(String userId) throws Exception {
		int i = this.entityManager.createQuery(" DELETE FROM ProdLock AS p WHERE user_id=:userId ")
		.setParameter("userId", userId)
		.executeUpdate();
		if(i>0) {
			return true;
		} else {
			return false;
		}
	}
}
