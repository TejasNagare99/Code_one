package com.excel.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.DivMapBean;
import com.excel.model.DivMap;
import com.excel.model.DivMap_;
import com.excel.utility.MedicoConstants;

@Repository
public class DivMapRepositoryImpl implements DivMapRepository, MedicoConstants {
		
	@Autowired private EntityManagerFactory emf;
	
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<Long> getDivMapByFsDivision(Long fsDivId) throws Exception {
		List<Long> list = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<DivMap> root = criteriaQuery.from(DivMap.class);
			criteriaQuery.multiselect(root.get(DivMap_.prod_div_id)).where(
					builder.and(
							builder.equal(root.get(DivMap_.base_div_id), fsDivId),
							builder.equal(root.get(DivMap_.core_ind), A)
							));
			
			list = em.createQuery(criteriaQuery).getResultList();

			return list;
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCoreIndToN(DivMapBean bean, HttpServletRequest request, String coreInd) throws Exception {
	
		EntityManager em = null;
		try {
			StringBuffer buffer=new StringBuffer();
			buffer.append(" update DIVMAP set CORE_IND=:coreInd, MOD_DT=:date, MOD_IP_ADD=:ip  ");
			buffer.append(" where PROD_DIV_ID in(:inActiveDiv)  and BASE_DIV_ID =:fieldDivision");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("fieldDivision", bean.getFieldDivision());
			query.setParameter("date", new Date());
			query.setParameter("ip", request.getRemoteAddr());
			query.setParameter("inActiveDiv", bean.getInActiveDiv());
			query.setParameter("coreInd", coreInd);
			query.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(em != null) { em.close(); }
		}
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCoreIndToA(DivMapBean bean, Long prodDivId, HttpServletRequest request, String coreInd) throws Exception {
	
		EntityManager em = null;
		try {
			StringBuffer buffer=new StringBuffer();
			buffer.append(" update DIVMAP set CORE_IND=:coreInd, MOD_DT=:date, MOD_IP_ADD=:ip  ");
			buffer.append(" where PROD_DIV_ID=:prodDivId  and BASE_DIV_ID =:fieldDivision");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("fieldDivision", bean.getFieldDivision());
			query.setParameter("date", new Date());
			query.setParameter("ip", request.getRemoteAddr());
			query.setParameter("prodDivId", prodDivId);
			query.setParameter("coreInd", coreInd);
			query.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(em != null) { em.close(); }
		}
	}

	@Override
	public String coreInd(Long fsDivId, Long prodDivId) throws Exception {
		String coreInd = null;

		try {
		
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<DivMap> criteriaQuery = builder.createQuery(DivMap.class);
			Root<DivMap> root = criteriaQuery.from(DivMap.class);
			
			criteriaQuery.multiselect(root.get(DivMap_.core_ind)).where(
					builder.and(
							builder.equal(root.get(DivMap_.base_div_id), fsDivId),
							builder.equal(root.get(DivMap_.prod_div_id), prodDivId)
							));
			
			coreInd = entityManager.createQuery(criteriaQuery).getSingleResult().getCore_ind();

			return coreInd;
		} catch (Exception e) {
			
		} 
		return null;
	}

}
