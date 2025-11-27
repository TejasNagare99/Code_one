package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.BinMaster;
import com.excel.model.BinMaster_;

@Repository
public class BinMasterRepositoryImpl implements BinMasterRepository{
	
	@Autowired EntityManagerFactory emf;
	
	@Override
	public List<BinMaster> getBinListByLocId(Long locId) throws Exception {
		EntityManager em = null;
		List<BinMaster> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<BinMaster> criteriaQuery = builder.createQuery(BinMaster.class);
			Root<BinMaster> root = criteriaQuery.from(BinMaster.class);
			criteriaQuery.multiselect(
					root.get(BinMaster_.bin_id),
					root.get(BinMaster_.bin_code)
					).where(builder.equal(root.get(BinMaster_.bin_loc_id), locId));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	

}
