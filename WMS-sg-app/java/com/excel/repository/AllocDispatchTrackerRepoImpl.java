package com.excel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.model.ActivityApproval;
import com.excel.model.AllocDispatchTracker;

@Repository
public class AllocDispatchTrackerRepoImpl implements AllocDispatchTrackerRepo {

	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<AllocDispatchTracker> getAllocDispatchTrackingDataFromProcedure(String loc_id, String startDate,
			String endDate, String allocType,String divId) {
		EntityManager em = null;
		List<AllocDispatchTracker> list= null;
		try {
			
			System.out.println("loc_id::"+loc_id+":::startDate:::"+startDate+":::endDate:::"+endDate+":::allocType:::"+allocType+":::divId:::"+divId);
			em = emf.createEntityManager();
			StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("callAllocDispatchTracker");
			query.setParameter("loc_id",loc_id);
			query.setParameter("startdate",startDate);
			query.setParameter("endDate",endDate);
			query.setParameter("alloc_type",allocType);
			query.setParameter("div_id",divId);

			list = query.getResultList();
			System.out.println("List Size "+list.size());
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

}
