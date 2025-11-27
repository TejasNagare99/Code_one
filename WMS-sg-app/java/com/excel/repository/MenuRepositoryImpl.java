package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.Menu;
import com.excel.model.Menu_;

@Repository
public class MenuRepositoryImpl implements MenuRepository {

	@Autowired private EntityManagerFactory emf;
	
	@Override
	public List<Menu> getRoles(String companyCode) throws Exception {
		EntityManager em = null;
		List<Menu> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Menu> criteriaQuery = builder.createQuery(Menu.class);
			Root<Menu> root = criteriaQuery.from(Menu.class);
			criteriaQuery.multiselect(
					root.get(Menu_.para_code),
					root.get(Menu_.menu_name)
					).where(
							builder.and(
									builder.equal(root.get(Menu_.menu_index), 0L),
									builder.equal(root.get(Menu_.company_cd), companyCode)
									)
							).orderBy(builder.asc(root.get(Menu_.menu_name)));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

}
