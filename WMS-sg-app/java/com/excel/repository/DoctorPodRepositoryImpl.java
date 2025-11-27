package com.excel.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.DivMaster;
import com.excel.model.DivMaster_;
import com.excel.model.DoctorPodMail;
import com.excel.model.StockistPodMail;

@Repository
public class DoctorPodRepositoryImpl implements DoctorPodRepository{
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;

	@Override
	public DoctorPodMail getDoctorPodByDspId(Long dspId)throws Exception {
		// TODO Auto-generated method stub
		List<DoctorPodMail> list = new ArrayList<DoctorPodMail>();
		try {
			;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<DoctorPodMail> criteriaQuery = builder.createQuery(DoctorPodMail.class);
			Root<DoctorPodMail> root = criteriaQuery.from(DoctorPodMail.class);
			criteriaQuery.select(root).where(builder.equal(root.get("dsp_id"), dspId));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			
			if(list!=null && list.size()>0) {
				return list.get(0);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	@Override
	public StockistPodMail getStockistPodByDspId(Long dspId) throws Exception {
		// TODO Auto-generated method stub
		List<StockistPodMail> list = new ArrayList<StockistPodMail>();
		try {
			;
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<StockistPodMail> criteriaQuery = builder.createQuery(StockistPodMail.class);
			Root<StockistPodMail> root = criteriaQuery.from(StockistPodMail.class);
			criteriaQuery.select(root).where(builder.equal(root.get("dsp_id"), dspId));
			list = entityManager.createQuery(criteriaQuery).getResultList();
			
			if(list!=null && list.size()>0) {
				return list.get(0);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}
	
}
