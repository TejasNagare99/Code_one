package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.SamplProdGroup;
import com.excel.model.SamplProdGroup_;
import com.excel.utility.MedicoConstants;

@Repository
public class SampleProdGroupRepositoryImpl implements SampleProdGroupRepository, MedicoConstants{
	
	@Autowired EntityManagerFactory emf;
	
	@Override
	public List<SamplProdGroup> getAllSamplProducts() throws Exception {
		EntityManager em = null;
		List<SamplProdGroup> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SamplProdGroup> criteriaQuery = builder.createQuery(SamplProdGroup.class);
			Root<SamplProdGroup> root = criteriaQuery.from(SamplProdGroup.class);
			criteriaQuery.multiselect(
					root.get(SamplProdGroup_.sa_prod_group),
					root.get(SamplProdGroup_.sa_group_name)
					).where(builder.equal(root.get(SamplProdGroup_.status), A))
			.orderBy(builder.asc(root.get(SamplProdGroup_.sa_group_name)));
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
