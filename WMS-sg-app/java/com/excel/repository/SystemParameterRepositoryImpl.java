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

import com.excel.model.Am_m_System_Parameter;
import com.excel.model.Am_m_System_Parameter_;

@Repository
public class SystemParameterRepositoryImpl implements SystemParameterRepository {
	
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<Am_m_System_Parameter> getSpValueBySpName(String spName)throws Exception{
		List<Am_m_System_Parameter> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Am_m_System_Parameter> criteriaQuery = builder.createQuery(Am_m_System_Parameter.class);
			Root<Am_m_System_Parameter> root = criteriaQuery.from(Am_m_System_Parameter.class);
			criteriaQuery.select(root).where(
					builder.and(
							builder.equal(root.get(Am_m_System_Parameter_.sp_name), spName),
							builder.equal(root.get(Am_m_System_Parameter_.sp_status), "A")
							)
					);
			list = entityManager.createQuery(criteriaQuery).getResultList();
			return list;
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
